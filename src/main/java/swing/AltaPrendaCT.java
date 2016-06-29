package main.java.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import main.java.controller.SistemaIndumentaria;
import main.java.swing.AgregarMaterial.AccionMaterial;
import main.java.util.ItemComboBox;
import main.java.util.UtilConstante;
import main.java.vista.MaterialItemView;
import main.java.vista.TemporadaView;

public class AltaPrendaCT extends JDialog implements AccionMaterial{

	private static final long serialVersionUID = 1L;	
	
	private JPanel contentPane;
	private JComboBox<ItemComboBox> comboBox;
	private JTextPane texto_nombre;
	private JTextPane texto_incremento;
	private JTextPane texto_stock;	
	private List<MaterialItemView> listaMaterial;
	
	private SistemaIndumentaria sistema;	
	private UtilConstante utilConstante;
	
	public AltaPrendaCT() {
		
		sistema = SistemaIndumentaria.getInstancia();
		utilConstante = UtilConstante.getInstancia();
	
		listaMaterial = new ArrayList<>();

		setModal(true);
		setTitle(utilConstante.getTexto("titulo_alta_pct"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		texto_nombre = new JTextPane();
		texto_nombre.setBounds(94, 21, 102, 20);		
		contentPane.add(texto_nombre);
		
		texto_stock = new JTextPane();
		texto_stock.setBounds(94, 52, 102, 20);		
		contentPane.add(texto_stock);
		
		texto_incremento = new JTextPane();
		texto_incremento.setBounds(94, 83, 102, 20);		
		contentPane.add(texto_incremento);		
		
		Vector<ItemComboBox> listaCombo = new Vector<ItemComboBox>(); 				
		for (TemporadaView t : sistema.obtenerTemporadas()){
			listaCombo.addElement(new ItemComboBox(t.getCodigoTemporada(), t.getNombreTemporada()));
		}
		comboBox = new JComboBox<ItemComboBox>(listaCombo);
		comboBox.setBounds(94, 114, 102, 20);
		contentPane.add(comboBox);
		
		JLabel lblNombre = new JLabel(utilConstante.getTexto("nombre_n_mayuscula"));
		lblNombre.setBounds(10, 21, 57, 20);
		contentPane.add(lblNombre);
		
		JLabel lblS = new JLabel(utilConstante.getTexto("stock_s_mayuscula"));
		lblS.setBounds(10, 52, 82, 14);
		contentPane.add(lblS);
		
		JLabel lblincremento = new JLabel(utilConstante.getTexto("incremento_i_mayuscula"));
		lblincremento.setBounds(10, 83, 82, 14);
		contentPane.add(lblincremento);		
		
		JLabel lblTemporada = new JLabel(utilConstante.getTexto("temporada_t_mayuscula"));
		lblTemporada.setBounds(10, 114, 82, 14);
		contentPane.add(lblTemporada);
				
		JButton btnAgregarMaterial = new JButton(utilConstante.getTexto("agregar_material_a_t_mayuscula"));
		btnAgregarMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgregarMaterial mat = new AgregarMaterial(AltaPrendaCT.this);
				mat.setVisible(true);
			}
		});
		btnAgregarMaterial.setBounds(10, 169, 119, 23);
		contentPane.add(btnAgregarMaterial);
		
		JButton btnConfirmar = new JButton(utilConstante.getTexto("confirmar_c_mayuscula"));
		btnConfirmar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ItemComboBox temporada = (ItemComboBox) comboBox.getSelectedItem();
				String nombre = texto_nombre.getText().trim();
				int stock = Integer.parseInt(texto_stock.getText().trim());
				float incremento = Float.parseFloat(texto_incremento.getText().trim());
																								
				sistema.altaPrendaConTemporada(nombre, stock, incremento, temporada.getId());				
				for (MaterialItemView m : listaMaterial){
					sistema.agregarItemPrendaConTemporada(nombre, m.getMaterial().getNombreMaterial(), m.getCantidad());					
				}
				
				dispose();
			}
		});
		btnConfirmar.setBounds(238, 228, 102, 23);
		contentPane.add(btnConfirmar);
		
		JButton btnSalir = new JButton(utilConstante.getTexto("salir_mayuscula"));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setBounds(345, 228, 89, 23);
		contentPane.add(btnSalir);		
	}

	@Override
	public void agregarMaterial(MaterialItemView m) {
		listaMaterial.add(m);
	}
}
