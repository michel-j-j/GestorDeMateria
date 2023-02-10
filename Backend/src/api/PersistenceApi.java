package api;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import dto.MateriaDTO;
import dto.NotaDTO;
import exception.DataErrorException;
import model.Materia;
import model.Nota;
import persistence.*;
import utilitary.CSV;

public class PersistenceApi implements IApi{
	private MateriaDAO materiaDAO = new MateriaDAOJDBC();
	private NotaDAO notaDAO = new NotaDAOJDBC();
	
	
	@Override
	public void registrarMateria(String nombre) throws Exception {
		try
		{
		materiaDAO.create(new Materia(nombre));
		msjExito("Materia registrada con exito.");
		}
		catch(RuntimeException  e)
		{
			throw new DataErrorException("Vuelva a intentar.");
		}
	}

	@Override
	public void msjExito(){
        JOptionPane.showMessageDialog(null, "Registrado con exito!");
	}
	
	@Override
	public void msjExito(String msj) {
		
		JLabel label = new JLabel("<html>"+msj);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		JOptionPane.showMessageDialog(null, label,null,JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void modificarMateria(Integer Id) throws Exception {
		Materia materia = materiaDAO.find(Id);
		materia.cambiarEstado();
		materiaDAO.update(materia);
		msjExito();
	}

	@Override
	public void modificarNota(Integer Id, NotaDTO nota) {
		
		
	}

	@Override
	public ArrayList<MateriaDTO> verMaterias() {
		ArrayList<Materia> materias = materiaDAO.findAll();
		ArrayList<MateriaDTO> materiasDTO = new ArrayList<MateriaDTO>();
		
		for(Materia cadena:materias)
		{
			materiasDTO.add(new MateriaDTO(cadena.getId(),cadena.getNombre(),cadena.getFecha(),cadena.getEstado(),verNotas(cadena.getId())));
			
		}
		return(materiasDTO);
	}

	@Override
	public ArrayList<NotaDTO> verNotas(Integer idMateria){
		ArrayList<NotaDTO> notasDTO = new ArrayList<NotaDTO>();
		
		Materia materia = materiaDAO.find(idMateria);
		
		ArrayList<Nota> notas = notaDAO.findAll();
		
		for(Nota cadena: notas)
		{
			if(cadena.getMateria().getId().equals(idMateria))
			{
				MateriaDTO materiaDTO = new MateriaDTO(materia.getId(),materia.getNombre(),materia.getFecha(),materia.getEstado());
				notasDTO.add(new NotaDTO(cadena.getId(),materiaDTO,cadena.getTipo(),cadena.getFecha(),cadena.getValor()));
			}
				
		}
		return(notasDTO);
	}
	@Override
	public void crearArchivoCSV(ArrayList<String[]> registro)
	{
		if(registro.size()!=0)
		{ System.out.println(CSV.CrearArchivo(registro, registro.get(0).length, "Registro Materias "));
		 this.msjExito("Tabla exportada con exito!");
		}
		else
		 this.msjExito("La tabla no contiene valores");
	}
	
	@Override
	public void modificarMateria(Integer Id, MateriaDTO materia) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void verMaterias(ArrayList<MateriaDTO> materias) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void registrarNota(Integer idMateria, String tipo, Float valor, Date fecha) {
		Materia materia = materiaDAO.find(idMateria);
		Nota nota = new Nota(valor,tipo,fecha);
		nota.setMateria(materia);
		
		notaDAO.create(nota);
	}
	@Override
	public ArrayList<MateriaDTO> verMateriasFinalizadas()
	{
		ArrayList<Materia> materias = materiaDAO.findAll();
		ArrayList<MateriaDTO> materiasDTO = new ArrayList<MateriaDTO>();
		
		for(Materia cadena:materias)
		{
			if(cadena.getEstado().equals(false))
				materiasDTO.add(new MateriaDTO(cadena.getId(),cadena.getNombre(),cadena.getFecha(),cadena.getEstado(),verNotas(cadena.getId())));
			
		}
		return(materiasDTO);
	}
	@Override
	public void eliminarNota(NotaDTO nota) {
		notaDAO.delete(notaDAO.find(nota.getId()));
		msjExito("Nota eliminada con exito");
	}

	@Override
	public void registrarNotaFinal(Integer idMateria, String tipo, Float valor, Date fecha) {
		Materia materia = materiaDAO.find(idMateria);
		materia.agregarNotas(notaDAO.findByMateria(materia.getId()));
		Boolean existe = false;
		for(Nota misNotas: materia.listarNotas())
		{
			if(misNotas.getTipo().equals("Final"))
			{
				msjExito("Ya hay una nota final para esta materia.");
				existe = true;
				break;
			}
				
		}

		if(!existe)
		{
		 registrarNota(idMateria, tipo, valor, fecha);
		 msjExito("Se registro la nota con exito. ");
		}
	}

	@Override
	public void eliminarMateria(MateriaDTO materia) {
		materiaDAO.delete(materiaDAO.find(materia.getId()));
		msjExito("Materia eliminada con exito");
		
	}
	
}
