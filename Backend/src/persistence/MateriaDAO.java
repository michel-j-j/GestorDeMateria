package persistence;
import java.util.ArrayList;

import model.Materia;

public interface MateriaDAO {
	
	public void create(Materia materia);
	
	public void update(Materia materia);
	
	public void delete(Materia materia);
	
	public Materia find(Integer Id);
	
	public ArrayList<Materia> findAll();

}
