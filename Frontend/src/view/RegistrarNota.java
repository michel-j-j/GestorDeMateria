package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import api.IApi;
import dto.MateriaDTO;
import utilitary.DataHelper;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.SystemColor;

public class RegistrarNota extends JFrame {

	private JPanel contentPane;
	private JTextField txtValor;
	private JComboBox comboBox;
	private JCalendar fecha;
	
	private ArrayList<MateriaDTO> materias;

	public RegistrarNota(IApi api) {
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
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTipo.setBounds(10, 106, 39, 20);
		contentPane.add(lblTipo);
		
		JLabel lblValor = new JLabel("Valor: ");
		lblValor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblValor.setBounds(10, 159, 44, 27);
		contentPane.add(lblValor);
		
		txtValor = new JTextField();
		txtValor.setVisible(false);
		txtValor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtValor.setColumns(10);
		txtValor.setBounds(280, 159, 144, 27);
		contentPane.add(txtValor);
		
		JLabel lblTitulo = new JLabel("Ingrese una nota");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitulo.setBounds(140, 10, 157, 30);
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
		
		JComboBox comboBoxTipo = new JComboBox();
		comboBoxTipo.setVisible(false);
		
		comboBoxTipo.addItem("Parcial");
		comboBoxTipo.addItem("Practico");
		comboBoxTipo.addItem("Grupal");
		
		comboBoxTipo.setBounds(280, 103, 144, 23);
		contentPane.add(comboBoxTipo);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(5, 38, 425, 331);
		contentPane.add(panel);
		
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtValor.getText().equals(""))
				{
				String tipo = (String)comboBoxTipo.getSelectedItem();
				Integer Id = materias.get(comboBox.getSelectedIndex()).getId();
				MateriaDTO materia = materias.get(comboBox.getSelectedIndex());
				
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
				api.registrarNota(Id, tipo, valorCasteado, fechaActual);
				try {
					api.modificarMateria(Id, materia);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				api.msjExito("Se registro la nota con exito. ");
				volver(api);
				}
				else
				{
					api.msjExito("Ingrese un valor. ");
				}
			}
		});
		
		comboBox.addItemListener(e ->{
			lblTipo.setVisible(true);
			comboBoxTipo.setVisible(true);  
			
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
		for(MateriaDTO materia: api.verMaterias())
		{
			materias.add(materia);
			comboBox.addItem(materia.getNombre());
		}
		
	}
}
