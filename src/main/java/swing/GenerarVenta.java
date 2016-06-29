package main.java.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.java.controller.SistemaIndumentaria;
import main.java.model.Cliente;
import main.java.swing.AgregarItemPrenda.AccionVentaItem;
import main.java.util.UtilConstante;
import main.java.vista.VentaItemView;

public class GenerarVenta extends JDialog implements AccionVentaItem{

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textoCodigo;
	private JButton btnAgregarPrendas;
	private JButton btnConfirmarVenta;
	private JButton btnSalir;
	private List<VentaItemView> listaVenta;

	private SistemaIndumentaria sistema;	
	private UtilConstante utilConstante;
	private Cliente clienteFactura;
	private int codigoVenta = 0;
	
	public GenerarVenta() {

		sistema = SistemaIndumentaria.getInstancia();
		utilConstante = UtilConstante.getInstancia();

		listaVenta = new ArrayList<>();

		setModal(true);
		setTitle(utilConstante.getTexto("titulo_generar_venta"));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
	
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				rechazarVenta();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	
		
		textoCodigo = new JTextField();
		textoCodigo.setBounds(112, 22, 86, 20);
		contentPane.add(textoCodigo);
		textoCodigo.setColumns(10);

		JButton btnBuscar = new JButton(utilConstante.getTexto("buscar_b_mayuscula"));
		btnBuscar.addActionListener(new ActionListener() {						
			
			public void actionPerformed(ActionEvent arg0) {
				reiniciarCampos();

				int codigo = Integer.parseInt(textoCodigo.getText().trim());
				clienteFactura = sistema.buscarCliente(codigo);

				if(clienteFactura != null){
					
					codigoVenta = sistema.generarVenta(codigo);

					btnSalir.setEnabled(false);
					btnAgregarPrendas.setEnabled(true);
					btnConfirmarVenta.setEnabled(true);										
				}								
			}
		});
		btnBuscar.setBounds(280, 21, 89, 23);
		contentPane.add(btnBuscar);

		JLabel lblCodigoCliente = new JLabel(utilConstante.getTexto("codigo_cliente_c_d_mayuscula"));
		lblCodigoCliente.setBounds(10, 25, 94, 14);
		contentPane.add(lblCodigoCliente);

		btnSalir = new JButton(utilConstante.getTexto("salir_mayuscula"));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setBounds(313, 227, 89, 23);
		contentPane.add(btnSalir);

		btnAgregarPrendas = new JButton(utilConstante.getTexto("agregar_prendas_a_p_mayuscula"));		
		btnAgregarPrendas.setEnabled(false);
		btnAgregarPrendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (codigoVenta > 0){
					// COMO LO QUE ES LA ALTA DE LA PRENDA PARA LOS MATERIALES SE HACE LO MISMO ACA
					AgregarItemPrenda  ap = new AgregarItemPrenda(codigoVenta, GenerarVenta.this);
					ap.setVisible(true);	
				}
			}
		});
		btnAgregarPrendas.setBounds(109, 114, 144, 23);
		contentPane.add(btnAgregarPrendas);

		btnConfirmarVenta = new JButton(utilConstante.getTexto("confirmar_venta_c_v_mayuscula"));
		btnConfirmarVenta.setEnabled(false);
		btnConfirmarVenta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TERMINO LA COMPRA Y PROCEDE A GENERAR LA FACTURA
				if(clienteFactura != null && codigoVenta > 0){
					StringBuffer buffer = new StringBuffer();
					int cont = 0;					
					Object[] param;
					
					buffer
						.append("\n")
						.append(utilConstante.getTexto("detalle_d_mayuscula"))
						.append("\n");
					
					for (VentaItemView v : listaVenta){						
						cont += v.getCantidad();
						
						param = new Object[]{
								v.getPrenda().getNombrePrenda(),
								v.getCantidad()
						};
						buffer
							.append(utilConstante.getMensaje("mensaje_generar_factura_detalle", param))
							.append("\n");
					}
					
					float montoTotal = sistema.cerrarVenta(codigoVenta);					
					param = new Object[]{
								cont, 
								montoTotal
					};													
					buffer.insert(0, utilConstante.getMensaje("mensaje_generar_factura", param) );
					

					JOptionPane.showMessageDialog(
									  null
									, buffer
									, utilConstante.getTexto("modal_mensaje_generar_venta")
									, JOptionPane.INFORMATION_MESSAGE
					);									
					
					
					btnSalir.setEnabled(true);					
				}
			}
		});
		btnConfirmarVenta.setBounds(201, 227, 101, 23);
		contentPane.add(btnConfirmarVenta);
	}

	protected void rechazarVenta() {		
		if (codigoVenta != 0){
			int respuesta = JOptionPane.showConfirmDialog(
					this,
					utilConstante.getTexto("dialogo_mensaje_venta"),			    
					utilConstante.getTexto("dialogo_titulo_venta"),
					JOptionPane.YES_NO_OPTION);

			if (respuesta == 0){			
				sistema.rechazarVenta(codigoVenta);
				dispose();
			}		
			
		}else{
			dispose();
		}		
	}

	protected void reiniciarCampos() {
		btnAgregarPrendas.setEnabled(false);
		btnConfirmarVenta.setEnabled(false);
	}

	@Override
	public void agregarVentaItem(VentaItemView item) {
		listaVenta.add(item);
	}	
}