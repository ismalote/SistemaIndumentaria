package main.java.swing;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.controller.SistemaIndumentaria;
import main.java.util.UtilConstante;
import main.java.vista.PedidoView;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private SistemaIndumentaria sistema;
	private UtilConstante utilConstante;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuPrincipal() {
		
		sistema = SistemaIndumentaria.getInstancia();
		utilConstante = UtilConstante.getInstancia();		
		
		setTitle(utilConstante.getTexto("titulo_menu_principal"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnPrenda = new JMenu(utilConstante.getTexto("prenda_p_mayuscula"));
		menuBar.add(mnPrenda);
		
		JMenu mnConTemporada = new JMenu(utilConstante.getTexto("prenda_con_temporada_c_t_mayuscula"));
		mnPrenda.add(mnConTemporada);
		
		JMenuItem mntmAlta = new JMenuItem(utilConstante.getTexto("alta_a_mayuscula"));
		mntmAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AltaPrendaCT app = new AltaPrendaCT();
				app.setVisible(true);
			}
		});
		
		mnConTemporada.add(mntmAlta);
		
		JMenuItem mntmModificar = new JMenuItem(utilConstante.getTexto("modificar_m_mayuscula"));
		mntmModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ModificarPCT mod = new ModificarPCT();
				mod.setVisible(true);
			}
		});
		mnConTemporada.add(mntmModificar);		
		JMenuItem mntmBaja = new JMenuItem(utilConstante.getTexto("baja_b_mayuscula"));
		mntmBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BajaPTC baj= new BajaPTC();
				baj.setVisible(true);
			}
		});
		mnConTemporada.add(mntmBaja);
		
		JMenu mnSinTemporada = new JMenu("Sin Temporada");
		mnPrenda.add(mnSinTemporada);
		
		JMenuItem mntmAlta_1 = new JMenuItem(utilConstante.getTexto("alta_a_mayuscula"));
		mnSinTemporada.add(mntmAlta_1);
		
		JMenuItem mntmModificar_1 = new JMenuItem(utilConstante.getTexto("modificar_m_mayuscula"));
		mnSinTemporada.add(mntmModificar_1);
		
		JMenuItem mntmBaja_1 = new JMenuItem(utilConstante.getTexto("baja_b_mayuscula"));
		mnSinTemporada.add(mntmBaja_1);
		
		JMenu mnVenta = new JMenu(utilConstante.getTexto("venta_v_mayuscula"));
		menuBar.add(mnVenta);
		
		JMenu mnCompra = new JMenu(utilConstante.getTexto("compra_c_mayuscula"));
		menuBar.add(mnCompra);
		
		JMenu mnOtros = new JMenu(utilConstante.getTexto("otro_menu"));
		menuBar.add(mnOtros);
				
		JMenuItem mntmGenerarPedidoMaterial = new JMenuItem(utilConstante.getTexto("generar_pedido_material"));
		mntmGenerarPedidoMaterial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<PedidoView> pedidos = sistema.generarPedidoCompra();
				// TODO HACER ESTO
				
			}
		});
		mnCompra.add(mntmGenerarPedidoMaterial);
		
		JMenuItem mntmCargarScript = new JMenuItem(utilConstante.getTexto("cargar_script"));
		mntmCargarScript.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sistema.cargarDatosIniciales();
			}
		});
		mnOtros.add(mntmCargarScript);		
				
		JMenuItem mntmGenerarVenta = new JMenuItem(utilConstante.getTexto("venta_generar_venta_g_v_mayuscula"));
		mntmGenerarVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GenerarVenta gv= new GenerarVenta();
				gv.setVisible(true);
			}
		});
		mnVenta.add(mntmGenerarVenta);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton(utilConstante.getTexto("salir_mayuscula"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				sistema.salirAplicativo();				
			}
		});
		btnNewButton.setBounds(299, 188, 104, 23);
		contentPane.add(btnNewButton);
	}
}
