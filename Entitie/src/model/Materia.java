package model;

import java.util.ArrayList;
import java.util.Date;

import exception.DataEmptyException;
import utilitary.DataHelper;

public class Materia {
	private Integer Id;
	private String nombre;
	private Date fecha;
	private Boolean estado;
	private ArrayList<Nota> misNotas;
	
	public Materia (String nombre) throws DataEmptyException
	{
		if((!nombre.equals("")) && (!isNumeric(nombre)) && (!isNull(nombre)))
		{
			this.nombre = nombre;
		}
		else
		{
			throw new DataEmptyException("El nombre no puede estar vacio y no debe haber caracteres numericos. ");
		}
		
		this.fecha = DataHelper.fechaActual();
		this.estado = true;
		misNotas = new ArrayList<Nota>();
	}
	public Materia(Integer Id, String nombre, Date fecha, Boolean estado, ArrayList<Nota> misNotas) throws DataEmptyException
	{   
		if(!isNull(Id) && !isNull(nombre) && !isNull(fecha) && !isNull(estado))
		{
			if((!nombre.equals("")) && (!isNumeric(nombre)))
			{
			this.Id = Id;
			this.nombre = nombre; 
			this.fecha = fecha;
			this.estado = estado;
			this.misNotas = misNotas;
			}
			else
			{
				throw new DataEmptyException("El nombre no puede estar vacio y no debe haber caracteres numericos. ");
			}
		}
		else
		{
			throw new DataEmptyException("Los datos no pueden ser nulos. ");
		}

	}
	public java.sql.Date getFecha()
	{
		java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
		return(sqlDate);
	}
	public String getNombre()
	{
		return(this.nombre);
	}
	public Boolean getEstado()
	{
		return(this.estado);
	}
	public Integer getId()
	{
		return(this.Id);
	}
	public void setId(Integer Id)
	{
		this.Id = Id; 
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	public void cambiarEstado()
	{
		if(estado.equals(true))
			estado = false;
		else
			estado = true;
	}
	public void agregarNota(Float valor, String tipo)
	{
		Nota nota = new Nota(valor,tipo);
		misNotas.add(nota);
	}
	
	public void agregarNota(Nota nota)
	{
		misNotas.add(nota);
	}
	
	public void agregarNotas(ArrayList<Nota> notas)
	{
		misNotas = notas;
	}
	
	public ArrayList<Nota> listarNotas()
	{
		return(this.misNotas);
	}
	
	public void verNotas()
	{
		for(Nota notas: listarNotas())
		{
			System.out.println(notas.toString());
		}
	}
	
	private Boolean isNull(Object var)
	{
		return(var.equals(null));
	}

    private static boolean isNumeric(String s)
    {
        if (s == null || s.equals("")) {
            return false;
        }
 
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
	
	
}
