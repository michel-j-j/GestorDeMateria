package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import api.IApi;
import dto.MateriaDTO;
import utilitary.DataHelper;

public class RegistrarNotaFinal extends JFrame {

	private JPanel contentPane;
	private JTextField txtValor;
	private JComboBox comboBox;
	private JCalendar fecha;
	
	private ArrayList<MateriaDTO> materias;
	public RegistrarNotaFinal(IApi api) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 447, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\Imagenes\\logoMateria.png"));
		setTitle("Registrar Nota");
		contentPane.setLayout(null);
		
		setLocationRelativeTo(null); 
		comboBox = new JComboBox();
		comboBox.setBounds(280, 50, 144, 21);
		contentPane.add(comboBox);
		
		cargarMaterias(api);
		


		fecha = new JCalendar();
		fecha.setVisible(false);
		fecha.setBounds(231, 196, 193, 110);
		contentPane.add(fecha); 
		
		JLabel lblComboBox = new JLabel("Seleccione una materia: ");
		lblComboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblComboBox.setBounds(10, 52, 166, 13);
		contentPane.add(lblComboBox);
		
		JLabel lblValor = new JLabel("Valor: ");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblValor.setBounds(10, 111, 44, 27);
		contentPane.add(lblValor);
		
		txtValor = new JTextField();
	
		txtValor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtValor.setColumns(10);
		txtValor.setBounds(280, 111, 144, 27);
		contentPane.add(txtValor);
		
		JLabel lblTitulo = new JLabel("Ingrese la nota final");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitulo.setBounds(140, 10, 186, 30);
		contentPane.add(lblTitulo);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setEnabled(false);
		btnRegistrar.setBounds(285, 321, 139, 36);
		contentPane.add(btnRegistrar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
				Menu ventana = new Menu(api);
				ventana.setVisible(true);
			}
		});
		btnVolver.setBounds(10, 321, 139, 36);
		contentPane.add(btnVolver);
		
		JLabel lblNewLabel = new JLabel("Fecha: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 221, 62, 21);
		contentPane.add(lblNewLabel);
		
		JRadioButton rdbtnFecha = new JRadioButton();
		rdbtnFecha.setBackground(SystemColor.inactiveCaption);
		rdbtnFecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(rdbtnFecha.isSelected());
				if(!rdbtnFecha.isSelected())
				{
					fecha.setVisible(true);
				}
				else
				{
					fecha.setVisible(false);
				}
			}
		});
		
		rdbtnFecha.setSelected(true);
		rdbtnFecha.setBounds(78, 221, 21, 21);
		contentPane.add(rdbtnFecha);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(5, 38, 425, 331);
		contentPane.add(panel);
		
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtValor.getText().equals(""))
				{
				String tipo = "Final";
				Integer Id = materias.get(comboBox.getSelectedIndex()).getId();
				
				String valor = txtValor.getText();
				float valorCasteado = Float.parseFloat(valor);
				
				Date fechaActual;
				if(!rdbtnFecha.isSelected())
				{
				fechaActual = fecha.getDate();
				}
				else
				{
				fechaActual = DataHelper.fechaActual();
				}
				api.registrarNotaFinal(Id, tipo, valorCasteado, fechaActual);
				volver(api);
				}
				else
				{
					api.msjExito("Ingrese un valor.");
				}
			}
		});
		
		comboBox.addItemListener(e ->{
			
			lblValor.setVisible(true);
			txtValor.setVisible(true);
			
			btnRegistrar.setEnabled(true);
		});
	}
	public void volver(IApi api)
	{
		setVisible(false);
		ListadoMateria ventana = new ListadoMateria(api);
		ventana.setVisible(true);
	}
	private void cargarMaterias(IApi api)
	{   materias = new ArrayList<MateriaDTO>();
		for(MateriaDTO materia: api.verMateriasFinalizadas())
		{
			materias.add(materia);
			comboBox.addItem(materia.getNombre());
		}
		
	}
}


