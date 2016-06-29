package main.java.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.java.controller.SistemaIndumentaria;
import main.java.model.Material;
import main.java.util.ItemComboBox;
import main.java.util.UtilConstante;
import main.java.vista.MaterialItemView;
import main.java.vista.MaterialView;

public class AgregarMaterial extends JDialog {

	private static final long serialVersionUID = 1L;

	interface AccionMaterial{
		public void agregarMaterial(MaterialItemView m);
	}
	
	private JPanel contentPane;
	private JTextField cantidadField;	
	private AccionMaterial accion;
	
	private SistemaIndumentaria sistema;	
	private UtilConstante utilConstante;
	private JComboBox<ItemComboBox> comboMaterial;
	
	public AgregarMaterial( AccionMaterial a) {
		this.accion = a; 
		sistema = SistemaIndumentaria.getInstancia();
		utilConstante = UtilConstante.getInstancia();
		
		setModal(true);
		setTitle(utilConstante.getTexto("titulo_alta_pct_agregar_material"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		Vector<ItemComboBox> listaCombo = new Vector<ItemComboBox>(); 				
		for (MaterialView m : sistema.obtenerMaterialesActivos()){
			listaCombo.addElement(new ItemComboBox(m.getCodigoMaterial(), m.getNombreMaterial()));
		}
		comboMaterial = new JComboBox<ItemComboBox>(listaCombo);		
		comboMaterial.setBounds(81, 11, 151, 20);
		contentPane.add(comboMaterial);
		
		cantidadField = new JTextField();
		cantidadField.setBounds(80, 46, 53, 20);
		contentPane.add(cantidadField);
		cantidadField.setColumns(10);
		
		JLabel lblMaterial = new JLabel(utilConstante.getTexto("material_m_mayuscula"));
		lblMaterial.setBounds(10, 14, 46, 14);
		contentPane.add(lblMaterial);
		
		JLabel lblCantidad = new JLabel(utilConstante.getTexto("cantidad_c_mayuscula"));
		lblCantidad.setBounds(10, 49, 60, 14);
		contentPane.add(lblCantidad);
		
		JButton btnAgregarMaterial = new JButton(utilConstante.getTexto("agregar_material_a_t_mayuscula"));
		btnAgregarMaterial.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				ItemComboBox material = (ItemComboBox) comboMaterial.getSelectedItem();
				int cantidad = Integer.parseInt(cantidadField.getText().trim());
				
				Material m = sistema.buscarMaterial(material.getId());
				accion.agregarMaterial(new MaterialItemView(m.getView(), cantidad));
			}
		});
		btnAgregarMaterial.setBounds(204, 216, 113, 23);
		contentPane.add(btnAgregarMaterial);
		
		JButton btnSalir = new JButton(utilConstante.getTexto("salir_mayuscula"));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setBounds(335, 216, 89, 23);
		contentPane.add(btnSalir);		
	}
}
