package persistence;
import java.util.ArrayList;

import model.Materia;
import model.Nota;

public interface NotaDAO {
	
	public void create(Nota nota);
	
	public void update(Nota nota);
	
	public void delete(Nota nota);
	
	public Nota find(Integer Id);
	
	public ArrayList<Nota> findAll();
	
	public ArrayList<Nota> findByMateria(Integer materiaId);

}
