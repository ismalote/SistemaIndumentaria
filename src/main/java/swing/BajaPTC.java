package main.java.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.java.controller.SistemaIndumentaria;
import main.java.enums.ExitCodes;
import main.java.util.UtilConstante;
import main.java.util.UtilLog;
import main.java.vista.PrendaConTemporadaView;

public class BajaPTC extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField textoCodigo;
	private JTextField textoNombre;
	private JButton btnConfirmar;

	private SistemaIndumentaria sistema;	
	private UtilConstante utilConstante;

	
	public BajaPTC() {
		
		sistema = SistemaIndumentaria.getInstancia();
		utilConstante = UtilConstante.getInstancia();		
		
		setModal(true);
		setTitle(utilConstante.getTexto("titulo_baja_pct"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textoCodigo = new JTextField();
		textoCodigo.setBounds(120, 24, 86, 20);
		contentPane.add(textoCodigo);
		textoCodigo.setColumns(10);
		
		JButton btnBucar = new JButton(utilConstante.getTexto("buscar_b_mayuscula"));
		btnBucar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reiniciarCampos();
				
				PrendaConTemporadaView prenda =	sistema.buscarPrendaConTemporadaView(Integer.parseInt(textoCodigo.getText().trim()));
				if (prenda != null){
					 btnConfirmar.setEnabled(true);					 
					 textoNombre.setText(prenda.getNombrePrenda());					 
				}
			}
		});
		btnBucar.setBounds(232, 23, 89, 23);
		contentPane.add(btnBucar);
		
		JLabel lblCodigo = new JLabel(utilConstante.getTexto("codigo_c_mayuscula"));
		lblCodigo.setBounds(64, 27, 46, 14);
		contentPane.add(lblCodigo);
		
		textoNombre = new JTextField();
		textoNombre.setEnabled(false);
		textoNombre.setBounds(120, 101, 86, 20);
		contentPane.add(textoNombre);
		textoNombre.setColumns(10);
		
		JLabel lblNombre = new JLabel(utilConstante.getTexto("nombre_n_mayuscula"));
		lblNombre.setBounds(46, 104, 64, 14);
		contentPane.add(lblNombre);
		
		btnConfirmar = new JButton(utilConstante.getTexto("confirmar_c_mayuscula"));
		btnConfirmar.setEnabled(false);
		btnConfirmar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
								
				int dialogo = JOptionPane.showConfirmDialog(null
														, utilConstante.getTexto("modal_mensaje_eliminar_ptc")
														, utilConstante.getTexto("modal_titulo_eliminar_ptc")
														, JOptionPane.YES_NO_OPTION
														);
				if (dialogo == JOptionPane.YES_OPTION){					
					int codigo = Integer.parseInt(textoCodigo.getText().trim());					
										
					if (sistema.bajaPrendaConTemporada(codigo) == ExitCodes.OK){
						dispose();
					}else{
						UtilLog.log("ERROR EN LA MODIFICACION DE PRENDA CON TEMPORADA");
					}										
				}														
			}
		});
		btnConfirmar.setBounds(217, 227, 104, 23);
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
	}
}
