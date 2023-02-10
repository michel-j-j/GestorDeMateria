package persistence;
import java.util.ArrayList;

import model.Materia;
import model.Nota;

public interface TipoDAO {
	
	public void create(Nota nota);
	
	public void update(Nota nota);
	
	public void delete(Nota nota);
	
	public Nota find(Integer Id);
	
	public ArrayList<Nota> findAll();

}
