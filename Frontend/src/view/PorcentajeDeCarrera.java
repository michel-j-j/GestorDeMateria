package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import api.IApi;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PorcentajeDeCarrera extends JFrame {

	private JPanel contentPane;
	private JTextField txtCTM;
	private JTextField txtCMR;

	public PorcentajeDeCarrera(IApi api) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 533, 390);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setLocationRelativeTo(null); 
		setTitle("Calcular Porcentaje de Carrera");
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\Imagenes\\logoMateria.png"));
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(10, 307, 139, 36);
		contentPane.add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				volver(api);
				}
		});
		JLabel lblTitulo = new JLabel("Calculadora de Porcentaje de Materias");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitulo.setBounds(96, 10, 349, 43);
		contentPane.add(lblTitulo);
		
		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.setBounds(372, 307, 139, 36);
		contentPane.add(btnCalcular);
		
		JLabel lblTotal = new JLabel("Cantidad Total de Materias:");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTotal.setBounds(68, 94, 189, 27);
		contentPane.add(lblTotal);
		
		txtCTM = new JTextField();
		txtCTM.setBounds(310, 96, 135, 27);
		contentPane.add(txtCTM);
		txtCTM.setColumns(10);
		
		txtCMR = new JTextField();
		txtCMR.setColumns(10);
		txtCMR.setBounds(310, 182, 135, 27);
		contentPane.add(txtCMR);
		
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtCMR.equals("") && !txtCTM.equals(""))
				{
					float cmr = Integer.parseInt(txtCMR.getText());
					float ctm = Integer.parseInt(txtCTM.getText());
					
					float total = cmr / ctm;
					total*=100;
			
					JOptionPane.showMessageDialog(null, "El porcentaje actual es: %" +String.format("%.2f", total));

				}
			}
		});
		JLabel lblCantidadDeMaterias = new JLabel("Cantidad de Materias Realizadas: ");
		lblCantidadDeMaterias.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCantidadDeMaterias.setBounds(68, 182, 232, 27);
		contentPane.add(lblCantidadDeMaterias);
		
		

		
	}
	public void volver(IApi api)
	{
		setVisible(false);
		Menu ventana = new Menu(api);
		ventana.setVisible(true);
	}
}
