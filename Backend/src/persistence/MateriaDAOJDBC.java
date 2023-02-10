package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import exception.DataEmptyException;
import exception.DataErrorException;
import model.Materia;

public class MateriaDAOJDBC implements MateriaDAO{

	@Override
	public void create(Materia materia) {
		Properties prop = ConnectionManager.getProperties();
		try (Connection conn = DriverManager.getConnection(prop.getProperty("connection"), prop.getProperty("username"),
				prop.getProperty("password"));
			
				PreparedStatement statement = conn.prepareStatement(
						"INSERT INTO materia(nombre, fecha, estado) " + " VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
			
			statement.setString(1, materia.getNombre());
			statement.setDate(2,  (materia.getFecha()));
			statement.setBoolean(3, materia.getEstado());
			
			
			int anduvo = statement.executeUpdate();
			
			if (anduvo > 0) {
				ResultSet rs = statement.getGeneratedKeys();
				rs.next();
			    materia.setId(rs.getInt(1));
	 			System.out.println("Se agrego " + anduvo + " registros");

			} else {
				System.out.println("Error al actualizar");
			}
			
		 }
		catch(MySQLIntegrityConstraintViolationException e)
		{
		   throw new DataErrorException("La materia ya existe!");
		}
		 catch (SQLException e) {
			System.out.println("Error al procesar consulta: " + e);

		}
		 catch(Exception e)
		{
			 System.out.println("Error! " +  e.toString());
		}
		
	}

	@Override
	public Materia find(Integer Id) {
		Properties prop = ConnectionManager.getProperties();
		Materia materia = null;
		
		try (Connection conn = DriverManager.getConnection(prop.getProperty("connection"), prop.getProperty("username"),
				prop.getProperty("password"));
			
				PreparedStatement statement = conn.prepareStatement(
						"SELECT id, nombre, fecha, estado FROM materia WHERE id = ?", Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, Id);
			
			ResultSet rs = statement.executeQuery();
			Boolean imprimir = true;
			if(rs.next())
			{ 
				if(imprimir.equals(true))
					System.out.println("Se recupero con exito");
				    imprimir = false;
				
				    materia = reconstruir(rs);
			}

		 }
		 catch (SQLException e) {
			System.out.println("Error al procesar consulta: " + e);

		}
		 catch(Exception e)
		{
			 System.out.println("Error! " +  e.toString());
		}
		return materia;
	}

	@Override
	public ArrayList<Materia> findAll() {
		Properties prop = ConnectionManager.getProperties();
		ArrayList<Materia> materias = new ArrayList<Materia>();
		
		try (Connection conn = DriverManager.getConnection(prop.getProperty("connection"), prop.getProperty("username"),
				prop.getProperty("password"));
			
				PreparedStatement statement = conn.prepareStatement(
						"SELECT id, nombre, fecha, estado FROM materia", Statement.RETURN_GENERATED_KEYS)) {
			
			ResultSet rs = statement.executeQuery();
			Boolean imprimir = true;
			while(rs.next())
			{ 
				if(imprimir.equals(true))
					System.out.println("Se recupero con exito");
				    imprimir = false;
				
				materias.add(reconstruir(rs));
			}

		 }
		 catch (SQLException e) {
			System.out.println("Error al procesar consulta: " + e);

		}
		 catch(Exception e)
		{
			 System.out.println("Error! " +  e.toString());
		}
		return materias;
	}

	@Override
	public void update(Materia materia){
		Properties prop = ConnectionManager.getProperties();
		try (Connection conn = DriverManager.getConnection(prop.getProperty("connection"), prop.getProperty("username"),
				prop.getProperty("password"));
			
				PreparedStatement statement = conn.prepareStatement(
						"UPDATE materia " + "SET nombre = ?, estado = ? " + "WHERE id = ?", Statement.RETURN_GENERATED_KEYS)) {
			
			statement.setString(1, materia.getNombre());
			statement.setBoolean(2, materia.getEstado());
			statement.setInt(3, materia.getId());
			
	
			int anduvo = statement.executeUpdate();
			
			if (anduvo > 0) {
				ResultSet rs = statement.getGeneratedKeys();
				rs.next();
	 			System.out.println("Se modifico " + anduvo + " registro/s con exito!");

			} else {
				System.out.println("Error al actualizar");
			}
			
		 }
		 catch (SQLException e) {
			System.out.println("Error al procesar consulta: " + e);

		}
		 catch(Exception e)
		{
			 System.out.println("Error! " +  e.toString());
		}
		
	}
	
	private Materia reconstruir(ResultSet rs)
	{
		try {
			return new Materia(rs.getInt("id"), rs.getString("nombre"),(java.util.Date) (rs.getDate("fecha")), rs.getBoolean("estado"),null);
		
		}
		catch(DataEmptyException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(Materia materia) {
		Properties prop = ConnectionManager.getProperties();
		try (Connection conn = DriverManager.getConnection(prop.getProperty("connection"), prop.getProperty("username"),
				prop.getProperty("password"));
			
				PreparedStatement statement = conn.prepareStatement(
						"DELETE FROM materia "+ "WHERE id = ?")) {
	
			statement.setInt(1, materia.getId());			
	
			int anduvo = statement.executeUpdate();
			
			if (anduvo > 0) {
	 			System.out.println("Se elimino " + anduvo + " registro/s con exito!");

			} else {
				System.out.println("Error al eliminar");
			}
			
		 }
		 catch (SQLException e) {
			System.out.println("Error al procesar consulta: " + e);

		}
		 catch(Exception e)
		{
			 System.out.println("Error al eliminar nota " +  e.toString());
		}
		
	}

}
