package dto;

import java.util.ArrayList;
import java.util.Date;

import model.Nota;

public class MateriaDTO {

	private Integer Id;
	private String nombre;
	private Date fecha;
	private Boolean estado;
	private ArrayList<NotaDTO> misNotas;
		
	public MateriaDTO(Integer id, String nombre, Date fecha, Boolean estado) {
		this.Id = id;
		this.nombre = nombre;
		this.fecha = fecha;
		this.estado = estado;
	}
	public MateriaDTO(Integer id, String nombre, Date fecha, Boolean estado, ArrayList<NotaDTO> misNotas) {
		this(id,nombre,fecha,estado);
		this.misNotas = misNotas;
	}
	public Integer getId() {
		return Id;
	}
	public String getNombre() {
		return nombre;
	}
	public Date getFecha() {
		return fecha;
	}
	public Boolean getEstado() {
		return estado;
	}
	public ArrayList<NotaDTO> getMisNotas() {
		return misNotas;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
	public void setMisNotas(ArrayList<NotaDTO> misNotas) {
		this.misNotas = misNotas;
	}
	public NotaDTO getNotaFinal()
	{   NotaDTO notaFinal = null;
		for(NotaDTO notas: misNotas)
		{
			if(!notas.notaFinal().equals((float)-1))
				notaFinal = notas;
		}
		return(notaFinal);
	}
}
