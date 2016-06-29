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
import main.java.model.Prenda;
import main.java.util.ItemComboBox;
import main.java.util.UtilConstante;
import main.java.vista.PrendaView;
import main.java.vista.VentaItemView;

public class AgregarItemPrenda extends JDialog {

	private static final long serialVersionUID = 1L;
	
	interface AccionVentaItem{
		 public void agregarVentaItem(VentaItemView item);
	}
	
	private JPanel contentPane;
	private JTextField cantidadField;
	private JButton btnAgregarPrenda;
	private JComboBox<ItemComboBox> comboMaterial;	
	
	private SistemaIndumentaria sistema;	
	private UtilConstante utilConstante;
	private AccionVentaItem accion;
	private int codigoVenta;
	
	public AgregarItemPrenda(int c, AccionVentaItem a) {
		this.accion = a;
		this.codigoVenta = c;

		sistema = SistemaIndumentaria.getInstancia();
		utilConstante = UtilConstante.getInstancia();
				
		setModal(true);
		setTitle(utilConstante.getTexto("titulo_generar_venta_agregar_item"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		
		JLabel lblNombrePrenda = new JLabel(utilConstante.getTexto("nombre_prenda_n_p_mayuscula"));
		lblNombrePrenda.setBounds(10, 24, 95, 14);
		contentPane.add(lblNombrePrenda);
		
		Vector<ItemComboBox> listaCombo = new Vector<ItemComboBox>(); 				
		for (PrendaView p : sistema.obtenerPrendasActivos()){
			listaCombo.addElement(new ItemComboBox(p.getCodigoPrenda(), p.getNombrePrenda()));
		}
		comboMaterial = new JComboBox<ItemComboBox>(listaCombo);		
		comboMaterial.setBounds(108, 21, 150, 20);
		contentPane.add(comboMaterial);
					
		cantidadField = new JTextField();
		cantidadField.setBounds(108, 55, 150, 20);
		contentPane.add(cantidadField);
		cantidadField.setColumns(10);
		
		JLabel lblCantidad = new JLabel(utilConstante.getTexto("cantidad_c_mayuscula"));
		lblCantidad.setBounds(51, 55, 63, 14);
		contentPane.add(lblCantidad);
		
		btnAgregarPrenda = new JButton(utilConstante.getTexto("agregar_prendas_a_p_mayuscula"));
		btnAgregarPrenda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				ItemComboBox prenda= (ItemComboBox) comboMaterial.getSelectedItem();
				int cantidad = Integer.parseInt(cantidadField.getText().trim());
				
				Prenda p = sistema.buscarPrenda(prenda.getId());
				sistema.agregarItemVenta(codigoVenta, p.getNombrePrenda(), cantidad); // GUARDO EN EL SISTEMA
				accion.agregarVentaItem(new VentaItemView(p.getView(), cantidad));
			}
		});
		btnAgregarPrenda.setBounds(108, 106, 150, 23);
		contentPane.add(btnAgregarPrenda);
		
		JButton btnSalir = new JButton(utilConstante.getTexto("salir_mayuscula"));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setBounds(335, 227, 89, 23);
		contentPane.add(btnSalir);
	}

	protected void reiniciarCampos() {
	}
}
