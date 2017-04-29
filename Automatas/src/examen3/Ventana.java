import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class Ventana extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCadena;
	private JButton btnValidar;
	private JTextArea textARecorrido;
	private JTextArea txtAEstadosFinales;
	private JLabel lblTablaDeTransicion;
	private JMenuBar menuBar;
	private JMenu mnArchivo;
	private JMenuItem mntmAbrir;
	@SuppressWarnings("unused")
	private File file;
	private JTable table;
	private DefaultTableModel dtm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana frame = new Ventana();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventana() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Ventana.class.getResource("/img/mi logo.png")));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 300);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		mnArchivo = new JMenu("");
		mnArchivo.setIcon(new ImageIcon(Ventana.class.getResource("/img/nueva_cita_32px.png")));
		menuBar.add(mnArchivo);

		mntmAbrir = new JMenuItem("Abrir");
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fl = new JFileChooser();
				FileNameExtensionFilter fltr = new FileNameExtensionFilter(
						"Archivos de texto", new String[] { "txt" });
				fl.setFileFilter(fltr);
				int ok = fl.showOpenDialog(new JFrame());

				if (ok == 0) {
					if (fl.getSelectedFile().getAbsolutePath().endsWith(".txt")) {
						leerArchivo(fl.getSelectedFile());
						file = fl.getSelectedFile();
					}
				}
			}

		});
		mntmAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				InputEvent.CTRL_MASK));
		mnArchivo.add(mntmAbrir);
		
		mntmLimpiar = new JMenuItem("Limpiar");
		mntmLimpiar.setIcon(new ImageIcon(Ventana.class.getResource("/img/limpiar.png")));
		mntmLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dtm = null;
				String[][] datos = { { "Etados", "c1", "c2" } };
				String[] titulos = { "", "", "" };
				dtm = new DefaultTableModel(datos, titulos);
				table.setModel(dtm);
				
				txtAEstadosFinales.setText("");
				txtCadena.setText("");
				textARecorrido.setText("");
				repaint();
			}
		});
		menuBar.add(mntmLimpiar);
		contentPane = new PanelFondo(new ImageIcon(Ventana.class.getResource("/img/fondo.jpg")));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblEstadosFinales = new JLabel("Estados Finales");
		lblEstadosFinales.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblEstadosFinales = new GridBagConstraints();
		gbc_lblEstadosFinales.insets = new Insets(0, 0, 5, 5);
		gbc_lblEstadosFinales.gridx = 0;
		gbc_lblEstadosFinales.gridy = 0;
		contentPane.add(lblEstadosFinales, gbc_lblEstadosFinales);

		lblTablaDeTransicion = new JLabel("Tabla de transicion");
		lblTablaDeTransicion.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblTablaDeTransicion = new GridBagConstraints();
		gbc_lblTablaDeTransicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblTablaDeTransicion.gridx = 1;
		gbc_lblTablaDeTransicion.gridy = 0;
		gbc_lblTablaDeTransicion.gridwidth = 2;
		contentPane.add(lblTablaDeTransicion, gbc_lblTablaDeTransicion);

		JLabel lblCamino = new JLabel("Camino");
		lblCamino.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblCamino = new GridBagConstraints();
		gbc_lblCamino.insets = new Insets(0, 0, 5, 0);
		gbc_lblCamino.gridx = 3;
		gbc_lblCamino.gridy = 0;
		contentPane.add(lblCamino, gbc_lblCamino);

		txtAEstadosFinales = new JTextArea();
		txtAEstadosFinales.setEditable(false);
		txtAEstadosFinales.setColumns(15);
		txtAEstadosFinales.setBorder(new EmptyBorder(10, 10, 10, 10));
		txtAEstadosFinales.setMargin(new Insets(5, 10, 5, 10));
		GridBagConstraints gbc_txtAEstadosFinales = new GridBagConstraints();
		gbc_txtAEstadosFinales.fill = GridBagConstraints.BOTH;
		gbc_txtAEstadosFinales.insets = new Insets(5, 10, 10, 20);
		gbc_txtAEstadosFinales.gridx = 0;
		gbc_txtAEstadosFinales.gridy = 1;
		gbc_txtAEstadosFinales.gridheight = 1;
		gbc_txtAEstadosFinales.gridwidth = 1;
		contentPane.add(txtAEstadosFinales, gbc_txtAEstadosFinales);

		String[][] datos = { { "Etados", "c1", "c2" } };
		String[] titulos = { "", "", "" };
		dtm = new DefaultTableModel(datos, titulos);
		table = new JTable();
		table.setBackground(SystemColor.inactiveCaption);
		table.setFont(new Font("Algerian", Font.PLAIN, 12));
		table.setModel(dtm);
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 5, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 1;
		gbc_table.gridy = 1;
		gbc_table.gridwidth = 2;
		JScrollPane jsp = new JScrollPane(table);
		jsp.setForeground(Color.BLUE);
		jsp.setFont(new Font("Algerian", Font.BOLD, 12));
		contentPane.add(jsp, gbc_table);

		textARecorrido = new JTextArea();
		textARecorrido.setForeground(new Color(25, 25, 112));
		textARecorrido.setEditable(false);
		textARecorrido.setColumns(1);
		GridBagConstraints gbc_textARecorrido = new GridBagConstraints();
		gbc_textARecorrido.insets = new Insets(10, 10, 10, 10);
		gbc_textARecorrido.fill = GridBagConstraints.BOTH;
		gbc_textARecorrido.gridx = 3;
		gbc_textARecorrido.gridy = 1;
		gbc_textARecorrido.gridheight = 1;
		gbc_textARecorrido.gridwidth = 1;
		JScrollPane js = new JScrollPane(textARecorrido);
		contentPane.add(js, gbc_textARecorrido);

		lblCadena = new JLabel("Cadena");
		lblCadena.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblCadena = new GridBagConstraints();
		gbc_lblCadena.insets = new Insets(0, 0, 0, 5);
		gbc_lblCadena.anchor = GridBagConstraints.EAST;
		gbc_lblCadena.gridx = 0;
		gbc_lblCadena.gridy = 2;
		contentPane.add(lblCadena, gbc_lblCadena);

		btnValidar = new JButton("Validar");
		btnValidar.setIcon(new ImageIcon(Ventana.class.getResource("/img/validar.png")));
		btnValidar.addActionListener(this);

		txtCadena = new JTextField();
		GridBagConstraints gbc_txtCadena = new GridBagConstraints();
		gbc_txtCadena.insets = new Insets(0, 0, 0, 5);
		gbc_txtCadena.fill = GridBagConstraints.BOTH;
		gbc_txtCadena.gridx = 1;
		gbc_txtCadena.gridy = 2;
		gbc_txtCadena.gridwidth = 2;
		contentPane.add(txtCadena, gbc_txtCadena);
		txtCadena.setColumns(10);
		btnValidar.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnValidar = new GridBagConstraints();
		gbc_btnValidar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnValidar.gridx = 3;
		gbc_btnValidar.gridy = 2;
		contentPane.add(btnValidar, gbc_btnValidar);
	}

	String tablaTransicion[][];
	Vector<String> v;
	private JLabel lblCadena;
	private JMenuItem mntmLimpiar;

	private void leerArchivo(File file) {
		// TODO Auto-generated method stub
		try {
			v = new Vector<>();
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(file));
			String linea = "";
			while ((linea = br.readLine()) != null) {
				v.add(linea);
				dtm.addRow(linea.split(","));
			}
			this.repaint();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == btnValidar) {
			String tblTran[][] = new String[v.size()][3];
			for (int i = 0; i < tblTran.length; i++) {
				String linea[] = v.get(i).split(",");
				tblTran[i][0] = linea[0];
				tblTran[i][1] = linea[1];
				tblTran[i][2] = linea[2];
			}
			Automata1 a = new Automata1(tblTran, txtCadena.getText());
			txtAEstadosFinales.setText(a.getEstadosFinales());
			if (a.getAceptado()) {
				JOptionPane.showMessageDialog(null, "Cadena Aceptada !!!");
			} else {
				JOptionPane.showMessageDialog(null, "Cadena rechazada");
			}
			
			this.textARecorrido.setText(a.getRecorrido());
			textARecorrido.setEditable(false);
		}
	}
}
