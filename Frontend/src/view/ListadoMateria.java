package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import api.IApi;
import dto.MateriaDTO;
import dto.NotaDTO;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;

public class ListadoMateria extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modeloTable;
	private JScrollPane scrollPane;
	private JButton btnVerNotas;
	private JComboBox comboBox;
	
	private ArrayList<MateriaDTO> materias;
	
	private ArrayList<MateriaDTO> materiasCursadas;
	private ArrayList<MateriaDTO> materiasFinalizadas;
	private JComboBox comboBoxCambiarNota;

	public ListadoMateria(IApi api) {
		scrollPane = new JScrollPane();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, -37, 708, 602);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\Imagenes\\logoMateria.png"));
		this.setTitle("Listado de Materias");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		setLocationRelativeTo(null); 
		setContentPane(contentPane);
		setResizable(false);
		
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setBounds(10, 10, 513, 545);
		contentPane.add(scrollPane);
		
		JButton btnAgregarMateria = new JButton("Agregar Materia");
		btnAgregarMateria.setBounds(545, 10, 139, 36);
		contentPane.add(btnAgregarMateria);
		
		JButton btnModificarMateria = new JButton("Cambiar Estado");
		btnModificarMateria.setEnabled(false);
		btnModificarMateria.setBounds(545, 164, 139, 36);
		contentPane.add(btnModificarMateria);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(545, 519, 139, 36);
		contentPane.add(btnVolver);
		
		btnVerNotas = new JButton("Ver Notas");

		btnVerNotas.setEnabled(false);
		btnVerNotas.setBounds(545, 116, 139, 36);
		contentPane.add(btnVerNotas);
		
		comboBox = new JComboBox();
		comboBox.addItem("Parcial");
		comboBox.addItem("Practico");
		comboBox.addItem("Grupal");
		comboBox.addItem("Todo");
		
		comboBox.addItemListener(e->
		{
			vaciarTabla();
			cargarTabla(api);
		});
		comboBox.setBounds(545, 283, 139, 21);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Promediar con:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(545, 251, 139, 25);
		contentPane.add(lblNewLabel);
		
		JButton btnExportar = new JButton("Exportar CSV");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ventanaYesNotCancel = JOptionPane.showConfirmDialog(null, "El CSV se exportara a el archivo ArchivosCSV", "Exportar CSV", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				System.out.println(ventanaYesNotCancel);
				if(ventanaYesNotCancel==0)
				{
				ArrayList<String[]> registro = new ArrayList<String[]>();
				Integer i = 0;
				for(MateriaDTO materia: materias)
				{
				String promedio = (String) modeloTable.getValueAt(i, 1);
				String notaFinal = (String) modeloTable.getValueAt(i, 4);
				notaFinal = formatearCadena(notaFinal);
				promedio = formatearCadena(promedio);
				i++;
				String [] campo = {materia.getNombre(),promedio,materia.getFecha().toString(),obtenerEstado(materia),notaFinal};
				registro.add(campo);
				}
				api.crearArchivoCSV(registro);
				}
				
			}
		});
		btnExportar.setBackground(new Color(144, 238, 144));
		btnExportar.setBounds(545, 443, 139, 36);
		contentPane.add(btnExportar);
		
		JLabel lblCambiarNota = new JLabel("Cambiar tipo de nota:");
		lblCambiarNota.setHorizontalAlignment(SwingConstants.CENTER);
		lblCambiarNota.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCambiarNota.setBounds(545, 337, 139, 25);
		contentPane.add(lblCambiarNota);
		
		comboBoxCambiarNota = new JComboBox();

		comboBoxCambiarNota.setBounds(545, 369, 139, 21);
		comboBoxCambiarNota.addItem("Todo");
		comboBoxCambiarNota.addItem("Finalizado");
		comboBoxCambiarNota.addItem("Cursada");
		contentPane.add(comboBoxCambiarNota);
		
		JButton btnEliminarMateria = new JButton("Eliminar Materia");
		btnEliminarMateria.setEnabled(false);
		btnEliminarMateria.setBounds(545, 66, 139, 36);
		contentPane.add(btnEliminarMateria);
		
		//Listener´s 
		generarTabla(api);
		cargarTabla(api); 
		
		btnModificarMateria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(materias.get(table.getSelectedRow()).getId());
					api.modificarMateria(materias.get(table.getSelectedRow()).getId());
					
					api.msjExito("Moficacion con Exito");
					vaciarTabla();
					cargarTabla(api);
					
					btnModificarMateria.setEnabled(false);
					btnVerNotas.setEnabled(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnVerNotas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ArrayList<MateriaDTO> nuevasMate = definir();
				
				ArrayList<NotaDTO> notasDTO = nuevasMate.get(table.getSelectedRow()).getMisNotas();
				String nombreMateria = nuevasMate.get(table.getSelectedRow()).getNombre();
				
				ListadoNota ventana = new ListadoNota(api,nombreMateria,notasDTO);
				ventana.setVisible(true);
			}
		});
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				
				Menu ventana = new Menu(api);
				ventana.setVisible(true);
				
			}
		});
		
		btnAgregarMateria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				
				RegistroMateria ventana = new RegistroMateria(api);
				ventana.setVisible(true);
			}
		});
		
		btnEliminarMateria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					api.eliminarMateria(materias.get(table.getSelectedRow()));
					vaciarTabla();
					cargarTabla(api);
					
					btnEliminarMateria.setEnabled(false);
					btnVerNotas.setEnabled(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		comboBoxCambiarNota.addItemListener(e->{
			vaciarTabla();

			
			switch((String)comboBoxCambiarNota.getSelectedItem())
			{ 
			   case "Finalizado":
			   {
				   cargarTabla(materiasFinalizadas);
			   }
			   break;
			   case "Cursada":
			   {
				   cargarTabla(materiasCursadas);
			   }
			   break;
			   default:
				   cargarTabla(api);
			   break;
					
			}
			
			
		});
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnModificarMateria.setEnabled(true);
				btnEliminarMateria.setEnabled(true);
				btnVerNotas.setEnabled(true);
			}
		});	
		
		
	}
	
	public void volver(IApi api)
	{
		setVisible(false);
		Menu ventana = new Menu(api);
		ventana.setVisible(true);
	}
	private String obtenerEstado(MateriaDTO materia)
	{
		if(materia.getEstado())
			return("Cursando");
		
		else
			return("Finalizado");
		
	}
	private void cargarTabla(IApi api)
	{  materias = new ArrayList<MateriaDTO>();
	   materiasFinalizadas  = new ArrayList<MateriaDTO>();
	   materiasCursadas = new ArrayList<MateriaDTO>();
	   
		for(MateriaDTO cadena: api.verMaterias())
		{   materias.add(cadena);
		    Float total = (float)0;
		    ArrayList<NotaDTO> notas = cadena.getMisNotas();
		    	
		    if(cadena.getEstado().equals(false))
		    	materiasFinalizadas.add(cadena);
		    else
		    	materiasCursadas.add(cadena);
		    	
		    
		    total = promediar(notas);
		    NotaDTO notaFinal = cadena.getNotaFinal();
		    String notaFinaltxt;
		    if(notaFinal == null)
		    	notaFinaltxt = "No hay nota";
		    else
		    	notaFinaltxt = notaFinal.getValor().toString();
		    if(total.isNaN())
		    {
		    	total = (float)0;
		    }
			Object[] componente = new Object[] {cadena.getNombre(),String.format("%.2f", total),cadena.getFecha().toString(),obtenerEstado(cadena),notaFinaltxt};
			modeloTable.addRow(componente);
		}
	}
	private void cargarTabla(ArrayList<MateriaDTO> materiasNuevas)
	{   
		for(MateriaDTO cadena: materiasNuevas)
		{   
		    Float total = (float)0;
		    ArrayList<NotaDTO> notas = cadena.getMisNotas();
		    	
		    total = promediar(notas);
		    NotaDTO notaFinal = cadena.getNotaFinal();
		    String notaFinaltxt;
		    if(notaFinal == null)
		    	notaFinaltxt = "No hay nota";
		    else
		    	notaFinaltxt = notaFinal.getValor().toString();
		    
		    if(total==null)
		    {
		    	total = (float)0;
		    }
			Object[] componente = new Object[] {cadena.getNombre(),String.format("%.2f", total),cadena.getFecha().toString(),obtenerEstado(cadena),notaFinaltxt};
			modeloTable.addRow(componente);
		}
	}
	private Float promediar(ArrayList<NotaDTO> notas)
	{
		Integer cantNotas = 0;
		Float total = (float)0;
		switch((String)comboBox.getSelectedItem())
		{
		    case "Parcial":
		    {
			    for(NotaDTO notaDTO: notas)
			    {
			    	if(notaDTO.getTipo().equals("Parcial"))
			    	{
			    	 total+=notaDTO.getValor();
			    	 cantNotas++;
			    	}
			    }
		    }
			break;
		    case "Practico":
		    {
			    for(NotaDTO notaDTO: notas)
			    {
			    	if(notaDTO.getTipo().equals("Practico"))
			    	{
			    	 total+=notaDTO.getValor();
			    	 cantNotas++;
			    	}
			    }
		    }
			break;
		    case "Grupal":
		    {
			    for(NotaDTO notaDTO: notas)
			    {
			    	if(notaDTO.getTipo().equals("Grupal"))
			    	{
			    	 total+=notaDTO.getValor();
			    	 cantNotas++;
			    	}
			    }
		    }
			break;
		    case "Todo":
		    {
			    for(NotaDTO notaDTO: notas)
			    {
			    	if(!notaDTO.getTipo().equals("Final"))
			    	{
			    	 total+=notaDTO.getValor();
			    	 cantNotas++;
			    	}
			    }
		    }
		    break;
		  
		}
		total = total/cantNotas;
		return(total);
	}
	private void generarTabla(IApi api) {
		table = new JTable();

		String[] datosPunto = {"Nombre","Promedio Cursada","Fecha de Inicio","Estado","Nota Final"};
		modeloTable = new DefaultTableModel(new Object[][] {}, datosPunto);

		table.setModel(modeloTable);

		scrollPane.setViewportView(table);
	}
	public void vaciarTabla() {
		materias = null;
		while (modeloTable.getRowCount() > 0) {
			modeloTable.removeRow(0);
		}
	}
	public  ArrayList<MateriaDTO> definir()
	{ ArrayList<MateriaDTO> materia;
		switch((String)comboBoxCambiarNota.getSelectedItem())
		{ 
		   case "Finalizado":
		   {
			   materia = materiasFinalizadas;
		   }
		   break;
		   case "Cursada":
		   {
			   materia = materiasCursadas;
		   }
		   break;
		   default:
			   materia = materias;
		   break;
				
		}
		return(materia);
	}
	public String formatearCadena(String cadena)
	{     StringBuilder sb = new StringBuilder(cadena);
		for(int i = 0; i < cadena.length(); i++)
		{
			char a = cadena.charAt(i);
			char coma = ',';
			if(a ==',')
			{
		        sb.setCharAt(i, '.');
		        
			}
		}
		cadena = sb.toString();
		
		return(cadena);
	}
}
