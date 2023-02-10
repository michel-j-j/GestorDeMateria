package dto;

import java.util.Date;

public class NotaDTO {

	private Integer id;
	private MateriaDTO materia;
	private String tipo;
	private Date fecha;
	private Float valor;
	
	public NotaDTO(Integer id, MateriaDTO materia, String tipo, Date fecha, Float valor) {
		super();
		this.id = id;
		this.materia = materia;
		this.tipo = tipo;
		this.fecha = fecha;
		this.valor = valor;
	}
	
	public Integer getId() {
		return id;
	}
	public MateriaDTO getMateria() {
		return materia;
	}
	public Float notaFinal()
	{
		if(!tipo.equals("Final"))
			return((float)-1);
		else
			return(valor);
	}
	public String getTipo() {
		return tipo;
	}
	public Date getFecha() {
		return fecha;
	}
	public Float getValor() {
		return valor;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setMateria(MateriaDTO materia) {
		this.materia = materia;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public void setValor(Float valor) {
		this.valor = valor;
	}
	
	
}
