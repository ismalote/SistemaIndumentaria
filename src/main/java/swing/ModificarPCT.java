package main.java.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.java.controller.SistemaIndumentaria;
import main.java.enums.ExitCodes;
import main.java.util.UtilConstante;
import main.java.util.UtilLog;
import main.java.vista.PrendaConTemporadaView;

public class ModificarPCT extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField textoCodigo;
	private JTextField textoNombre;
	private JTextField textoIncremento;
	private JTextField textoStock;
	private JButton btnConfirmar;
	
	private SistemaIndumentaria sistema;	
	private UtilConstante utilConstante;
	
	public ModificarPCT() {
		
		sistema = SistemaIndumentaria.getInstancia();
		utilConstante = UtilConstante.getInstancia();
		
		setModal(true);
		setTitle(utilConstante.getTexto("titulo_generar_venta"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textoCodigo = new JTextField();
		textoCodigo.setBounds(94, 27, 86, 20);
		contentPane.add(textoCodigo);
		textoCodigo.setColumns(10);
		
		JLabel lblCodigo = new JLabel(utilConstante.getTexto("codigo_c_mayuscula"));
		lblCodigo.setBounds(38, 30, 46, 14);
		contentPane.add(lblCodigo);
		
		JButton btnBuscar = new JButton(utilConstante.getTexto("buscar_b_mayuscula"));
		btnBuscar.addActionListener(new ActionListener() {
						
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reiniciarCampos();
				
				PrendaConTemporadaView prenda =	sistema.buscarPrendaConTemporadaView(Integer.parseInt(textoCodigo.getText().trim()));
				if (prenda != null){
					 btnConfirmar.setEnabled(true);
					
					 textoIncremento.setEnabled(true);
					 textoIncremento.setText(String.valueOf(prenda.getPorcentajeIncremento()));
					 
					 textoNombre.setEnabled(true);
					 textoNombre.setText(prenda.getNombrePrenda());
					 
					 textoStock.setEnabled(true);
					 textoStock.setText(String.valueOf(prenda.getStock()));
				}
			}
		});
		btnBuscar.setBounds(209, 26, 89, 23);
		contentPane.add(btnBuscar);
		
		textoNombre = new JTextField();
		textoNombre.setEnabled(false);
		textoNombre.setBounds(94, 92, 86, 20);
		contentPane.add(textoNombre);
		textoNombre.setColumns(10);
		
		textoIncremento = new JTextField();
		textoIncremento.setEnabled(false);
		textoIncremento.setBounds(94, 139, 86, 20);
		contentPane.add(textoIncremento);
		textoIncremento.setColumns(10);
		
		textoStock = new JTextField();
		textoStock.setEnabled(false);
		textoStock.setBounds(94, 185, 86, 20);
		contentPane.add(textoStock);
		textoStock.setColumns(10);
		
		JLabel lblNombre = new JLabel(utilConstante.getTexto("nombre_n_mayuscula"));
		lblNombre.setBounds(20, 95, 46, 14);
		contentPane.add(lblNombre);
		
		JLabel lblincremento = new JLabel(utilConstante.getTexto("incremento_i_mayuscula"));
		lblincremento.setBounds(10, 142, 84, 14);
		contentPane.add(lblincremento);
		
		JLabel lblStock = new JLabel(utilConstante.getTexto("stock_s_mayuscula"));
		lblStock.setBounds(10, 189, 84, 14);
		contentPane.add(lblStock);
		
		btnConfirmar = new JButton(utilConstante.getTexto("confirmar_c_mayuscula"));
		btnConfirmar.setEnabled(false);
		btnConfirmar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// GUARDO MODIFICACIONES
				int codigo = Integer.parseInt(textoCodigo.getText().trim());
				String nombre = textoNombre.getText().trim();				
				int stock = Integer.parseInt(textoStock.getText().trim());
				float incremento = Float.parseFloat(textoIncremento.getText().trim());
				
				if (sistema.modificarPrendaConTemporada(codigo, nombre, stock, incremento) == ExitCodes.OK){
					dispose();
				}else{
					UtilLog.log("ERROR EN LA MODIFICACION DE PRENDA CON TEMPORADA");
				}								
			}
		});
		btnConfirmar.setBounds(209, 227, 101, 23);
		contentPane.add(btnConfirmar);
		
		JButton btnSalir = new JButton(utilConstante.getTexto("salir_mayuscula"));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnSalir.setBounds(335, 227, 89, 23);
		contentPane.add(btnSalir);
	}

	protected void reiniciarCampos() {		
		btnConfirmar.setEnabled(false);
		
		textoNombre.setEnabled(false);
		textoNombre.setText("");
		
		textoIncremento.setEnabled(false);
		textoIncremento.setText("");
	}
}
