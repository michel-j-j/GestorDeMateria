package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import api.IApi;
import dto.NotaDTO;
import java.awt.SystemColor;

public class ListadoNota extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modeloTable;
	private JScrollPane scrollPane;
	
	private ArrayList<NotaDTO> notas;

	public ListadoNota(IApi api,String nombreMateria ,ArrayList<NotaDTO> notas) {
		this.notas = notas;
		
		scrollPane = new JScrollPane();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 662, 420);
		this.setTitle("Listado de Materias");
		contentPane = new JPanel();
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\Imagenes\\logoMateria.png"));
		setTitle(nombreMateria);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		setLocationRelativeTo(null); 
		setContentPane(contentPane);
		setResizable(false);
		
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setBounds(10, 10, 463, 363);
		contentPane.add(scrollPane);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(SystemColor.menu);
		btnVolver.setBounds(494, 322, 133, 38);
		contentPane.add(btnVolver);
		
		JButton btnEliminarNota = new JButton("Eliminar Nota");
		btnEliminarNota.setEnabled(false);
		btnEliminarNota.setBackground(SystemColor.menu);
		btnEliminarNota.setBounds(494, 73, 133, 38);
		contentPane.add(btnEliminarNota);
		
		JButton btnRegistrarNota = new JButton("Registrar Nota");
		btnRegistrarNota.setBackground(SystemColor.menu);
		btnRegistrarNota.setBounds(494, 25, 133, 38);
		contentPane.add(btnRegistrarNota);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(483, 10, 155, 363);
		contentPane.add(panel);

		
		//Listener´s 
		generarTabla(api);
		cargarTabla(api); 
		
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volver(api);
			}
		});
		btnRegistrarNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				RegistrarNota ventana = new RegistrarNota(api);
				ventana.setVisible(true);
			}
		});
		
		btnEliminarNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NotaDTO nota = notas.get(table.getSelectedRow());
				api.eliminarNota(nota);
				modeloTable.removeRow(table.getSelectedRow());
				
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnEliminarNota.setEnabled(true);

			}
		});
	
	}
	
	public void volver(IApi api)
	{
		setVisible(false);
		ListadoMateria ventana = new ListadoMateria(api);
		ventana.setVisible(true);
	}
	
	private void cargarTabla(IApi api)
	{  
		for(NotaDTO cadena: notas)
		{   
			Object[] componente = new Object[] {cadena.getTipo(),cadena.getValor(),cadena.getFecha()};
			modeloTable.addRow(componente);
		}
	}
	public void vaciarTabla() {
		notas = null;
		while (modeloTable.getRowCount() > 0) {
			modeloTable.removeRow(0);
		}
	}
	private void generarTabla(IApi api) {
		table = new JTable();

		String[] datosPunto = {"Tipo","Valor","Fecha"};
		modeloTable = new DefaultTableModel(new Object[][] {}, datosPunto);

		table.setModel(modeloTable);

		scrollPane.setViewportView(table);
	}
}
