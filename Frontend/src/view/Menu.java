package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import api.PersistenceApi;
import api.IApi;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Menu extends JFrame {

	private JPanel contentPane;
	private JPanel BarraLateral;
	private JPanel Fondo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new PersistenceApi();
					Menu frame = new Menu(api);
					
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
	public Menu(IApi api) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 640);
		setTitle("Gestor de Materias");
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\Imagenes\\logoMateria.png"));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setLocationRelativeTo(null); 
		this.setResizable(false);
		
		setContentPane(contentPane);
		
		BarraLateral = new JPanel();
		BarraLateral.setBackground(UIManager.getColor("CheckBox.light"));
		BarraLateral.setBounds(0, 0, 170, 603);
		contentPane.add(BarraLateral);
		
		JButton btnRegistrarMateria = new JButton("Registrar Materia");
		btnRegistrarMateria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				RegistroMateria ventana = new RegistroMateria(api);
				ventana.setVisible(true);
			}
		});
		BarraLateral.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
		
		JLabel lblUser = new JLabel();
		setImageLabel(lblUser,"src\\Imagenes\\iconoUsuario2.png",170);

		
		BarraLateral.add(lblUser);
		JButton btnRegistrarNota = new JButton("Registrar Nota");
		btnRegistrarNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				RegistrarNota ventana = new RegistrarNota(api);
				ventana.setVisible(true);
			}
		});
		
		JButton btnListadoDeMateria = new JButton("Listado de Materias");
		btnListadoDeMateria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListadoMateria ventana = new ListadoMateria(api);
				setVisible(false);
				ventana.setVisible(true);
			}
		});
		BarraLateral.add(btnListadoDeMateria);
		BarraLateral.add(btnRegistrarMateria);
		BarraLateral.add(btnRegistrarNota);
		
		
		
		JButton btnRegistrarNotaFinal = new JButton("Registrar Nota Final");
		btnRegistrarNotaFinal.addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				RegistrarNotaFinal ventana = new RegistrarNotaFinal(api);
				ventana.setVisible(true);
			};
			
		}));
		BarraLateral.add(btnRegistrarNotaFinal);
		
		JButton btnCalcularPorcentaje = new JButton("Calcular Materias");
		btnCalcularPorcentaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				PorcentajeDeCarrera ventana = new PorcentajeDeCarrera(api);
				ventana.setVisible(true);
			}
		});
		BarraLateral.add(btnCalcularPorcentaje);


		modificarBotones(BarraLateral,150,30);
		Fondo = new JPanel();
		Fondo.setBackground(SystemColor.activeCaption);
		Fondo.setBounds(170, 0, 416, 603);

		contentPane.add(Fondo);
		Fondo.setLayout(null);
		
		JLabel lblFondo = new JLabel();
		lblFondo.setHorizontalAlignment(SwingConstants.CENTER);
		setImageLabel(lblFondo,"src\\Imagenes\\Fondo.jpg");
		lblFondo.setBounds(0, 0, 416, 603);
		Fondo.add(lblFondo);
	}
	private void setImageLabel(JLabel labelName,String root,Integer largo)
	{
		ImageIcon image = new ImageIcon(root);
		System.out.println();
		Icon icon = new ImageIcon(image.getImage().getScaledInstance(BarraLateral.getWidth(), BarraLateral.getWidth(), Image.SCALE_SMOOTH));
		labelName.setIcon(icon);
		this.repaint();
	}
	private void setImageLabel(JLabel labelName,String root)
	{
		ImageIcon image = new ImageIcon(root);
		
		Icon icon = new ImageIcon(image.getImage().getScaledInstance(image.getIconWidth(), Fondo.getHeight(), Image.SCALE_SMOOTH));
		
		labelName.setIcon(icon);
	
		this.repaint();
	}
	private void modificarBotones(JPanel miPanel, Integer ancho, Integer largo)
	{
		for(Object cadena : miPanel.getComponents())
		{   if(cadena instanceof JButton)
		    {
			((Component) cadena).setPreferredSize(new Dimension(ancho,largo));
		    }
		}
	}
}
