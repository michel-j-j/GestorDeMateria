package model;
import java.util.Date;

import exception.DataEmptyException;
import exception.DataErrorException;
import utilitary.DataHelper;

public class Nota {
	private Integer Id;
	private String tipo; //Practico, Parcial o Final. 
	private Date fecha;
	private Float valor;
	private Materia materia;
	
	public Nota(Float valor, String tipo)
	{
		if(!isNull(valor)&&!isNull(tipo))
		{
			if(!isNumeric(tipo)&&(!tipo.equals("")))
			{
				if(isValidFloat(valor.toString()))
				{
					this.tipo = tipo;
					this.valor = valor;
				}
				else
				{
					throw new DataErrorException("El valor no corresponde a un valor valido");
				}
			}
			else
			{
				throw new DataErrorException("El tipo no corresponde a un valor valido");
			}

		}
		else
		{
			throw new DataErrorException("Ingrese un valor valido");
		}	
		this.fecha = DataHelper.fechaActual();
	}
	public Nota(Float valor, String tipo, Date fecha)
	{
		this(valor,tipo);
		if(!isNull(fecha))
			this.fecha = fecha;
		else
			throw new DataErrorException("Ingrese un valor valido");
	}
	public Materia getMateria()
	{
		return(this.materia);
	}
	public String getTipo()
	{
		return(this.tipo);
	}
	public Date getFecha() {
		return fecha;
	}
	
	public Float getValor() {
		return valor;
	}
	public Integer getId()
	{
		return Id;
	}
	public void setMateria(Materia materia)
	{
		this.materia = materia;
	}
	public void setId(Integer Id)
	{
		this.Id = Id; 
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public void setValor(Float valor) {
		this.valor = valor;
	}
	
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "Nota [tipo=" + tipo + ", fecha=" + fecha.toString() + ", valor=" + valor + "]";
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
    private static boolean isValidFloat(String s) {
        boolean isValid = true;

        try {
            Float.parseFloat(s);
        } catch(NumberFormatException nfe) {
            isValid = false;
        }

        return isValid;
    }
	
}
