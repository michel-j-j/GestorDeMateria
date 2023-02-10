package api;

import java.util.ArrayList;
import java.util.Date;

import dto.*;

public interface IApi {
	
	public void registrarMateria(String nombre) throws Exception;	
	public void modificarMateria(Integer Id) throws Exception;	
	public void registrarNota(Integer idMateria,String tipo, Float valor, Date fecha);
	public void registrarNotaFinal(Integer idMateria,String tipo, Float valor, Date fecha);
	public void verMaterias(ArrayList<MateriaDTO> materias);
	public void crearArchivoCSV(ArrayList<String[]> registro);

	
	
	
	
	public void msjExito();
	public void msjExito(String msj);
	void modificarNota(Integer Id, NotaDTO nota);
	void eliminarNota(NotaDTO nota);
	void eliminarMateria(MateriaDTO nota);
	void modificarMateria(Integer Id, MateriaDTO materia) throws Exception;
	public ArrayList<MateriaDTO> verMaterias();
	public ArrayList<MateriaDTO> verMateriasFinalizadas();
	public ArrayList<NotaDTO> verNotas(Integer idMateria);
	

}
