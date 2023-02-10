package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import exception.DataEmptyException;
import model.Materia;
import model.Nota;

public class NotaDAOJDBC implements NotaDAO{
	
	private MateriaDAO materiaDAO = new MateriaDAOJDBC();

	@Override
	public void create(Nota nota) {
		Properties prop = ConnectionManager.getProperties();
		try (Connection conn = DriverManager.getConnection(prop.getProperty("connection"), prop.getProperty("username"),
				prop.getProperty("password"));
			
				PreparedStatement statement = conn.prepareStatement(
						"INSERT INTO nota(materia_id, tipo, fecha, valor) " + " VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
			java.sql.Date sqlDate = new java.sql.Date(nota.getFecha().getTime());
			statement.setInt(1, nota.getMateria().getId());
			statement.setString(2, nota.getTipo());
			statement.setDate(3,  sqlDate);
			statement.setFloat(4, nota.getValor());
			
			
			int anduvo = statement.executeUpdate();
			
			if (anduvo > 0) {
				ResultSet rs = statement.getGeneratedKeys();
				rs.next();
			    nota.setId(rs.getInt(1));
	 			System.out.println("Se agrego " + anduvo + " registro/s");

			} else {
				System.out.println("Error al actualizar");
			}
			
		 }
		 catch (SQLException e) {
			System.out.println("Error al procesar consulta: " + e);

		}
		 catch(Exception e)
		{
			 System.out.println("Error al insertar nota " +  e.toString());
		}
		
	}

	@Override
	public Nota find(Integer Id) {
		Properties prop = ConnectionManager.getProperties();
		Nota nota = null;
		
		try (Connection conn = DriverManager.getConnection(prop.getProperty("connection"), prop.getProperty("username"),
				prop.getProperty("password"));
			
				PreparedStatement statement = conn.prepareStatement(
						"SELECT id, materia_id, tipo, fecha, valor FROM nota WHERE id = ?", Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, Id);
			
			ResultSet rs = statement.executeQuery();
			Boolean imprimir = true;
			if(rs.next())
			{ 
				if(imprimir.equals(true))
					System.out.println("Se recupero con exito");
				    imprimir = false;
				
				    nota = reconstruir(rs);
			}

		 }
		 catch (SQLException e) {
			System.out.println("Error al procesar consulta: " + e);

		}
		 catch(Exception e)
		{
			 System.out.println("Error al encontrar nota " +  e.toString());
		}
		return nota;
	}

	@Override
	public ArrayList<Nota> findAll() {
		Properties prop = ConnectionManager.getProperties();
		ArrayList<Nota> nota = new ArrayList<Nota>();
		
		try (Connection conn = DriverManager.getConnection(prop.getProperty("connection"), prop.getProperty("username"),
				prop.getProperty("password"));
			
				PreparedStatement statement = conn.prepareStatement(
						"SELECT id, materia_id, tipo, fecha, valor FROM nota", Statement.RETURN_GENERATED_KEYS)) {
			
			ResultSet rs = statement.executeQuery();
			Boolean imprimir = true;
			while(rs.next())
			{ 
				if(imprimir.equals(true))
					System.out.println("Se recupero con exito");
				    imprimir = false;
				
				    nota.add(reconstruir(rs));
			}

		 }
		 catch (SQLException e) {
			System.out.println("Error al procesar consulta: " + e);

		}
		 catch(Exception e)
		{
			 System.out.println("Error al encontrar notas " +  e.toString());
		}
		return nota;
	}
	@Override
	public void delete(Nota nota)
	{
		Properties prop = ConnectionManager.getProperties();
		try (Connection conn = DriverManager.getConnection(prop.getProperty("connection"), prop.getProperty("username"),
				prop.getProperty("password"));
			
				PreparedStatement statement = conn.prepareStatement(
						"DELETE FROM nota "+ "WHERE id = ?")) {
	
			statement.setInt(1, nota.getId());			
	
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

	@Override
	public void update(Nota nota){
		Properties prop = ConnectionManager.getProperties();
		try (Connection conn = DriverManager.getConnection(prop.getProperty("connection"), prop.getProperty("username"),
				prop.getProperty("password"));
			
				PreparedStatement statement = conn.prepareStatement(
						"UPDATE nota " + "SET tipo = ?, fecha = ?, valor = ? " + "WHERE id = ?", Statement.RETURN_GENERATED_KEYS)) {
			java.sql.Date sqlDate = new java.sql.Date(nota.getFecha().getTime());
			
			statement.setString(1, nota.getTipo());
			statement.setDate(2, sqlDate);
			statement.setFloat(3, nota.getValor());
			
	
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
			 System.out.println("Error al actualizar nota " +  e.toString());
		}
		
	}
	
	private Nota reconstruir(ResultSet rs) throws DataEmptyException
	{
		try {
			Nota nota = new Nota(rs.getFloat("valor"),rs.getString("tipo"),rs.getDate("fecha"));
			nota.setId(rs.getInt("id"));
			nota.setMateria(materiaDAO.find(rs.getInt("materia_id")));
			
			return(nota);
		
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Nota> findByMateria(Integer materiaId) {
		Properties prop = ConnectionManager.getProperties();
		ArrayList<Nota> nota = new ArrayList<Nota>();
		
		try (Connection conn = DriverManager.getConnection(prop.getProperty("connection"), prop.getProperty("username"),
				prop.getProperty("password"));
			
				PreparedStatement statement = conn.prepareStatement(
						"SELECT id, materia_id, tipo, fecha, valor FROM nota "+"WHERE materia_id = ?", Statement.RETURN_GENERATED_KEYS)) {
			
			statement.setInt(1, materiaId);
			
			ResultSet rs = statement.executeQuery();
			Boolean imprimir = true;
			while(rs.next())
			{ 
				if(imprimir.equals(true))
					System.out.println("Se recupero con exito");
				    imprimir = false;
				
				    nota.add(reconstruir(rs));
			}

		 }
		 catch (SQLException e) {
			System.out.println("Error al procesar consulta: " + e);

		}
		 catch(Exception e)
		{
			 System.out.println("Error al encontrar notas " +  e.toString());
		}
		return nota;
	}
	

}
