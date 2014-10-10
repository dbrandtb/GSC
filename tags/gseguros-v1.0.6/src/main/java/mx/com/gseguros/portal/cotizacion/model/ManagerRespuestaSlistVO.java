package mx.com.gseguros.portal.cotizacion.model;

import java.util.List;
import java.util.Map;

public class ManagerRespuestaSlistVO
{
	private boolean exito                  = false;
	private String  respuesta              = null;
	private String  respuestaOculta        = null;
	private List<Map<String,String>> slist = null;
	
	public ManagerRespuestaSlistVO()
	{}
	
	public ManagerRespuestaSlistVO(boolean exito)
	{
		this.exito = exito;
	}
	
	public ManagerRespuestaSlistVO(boolean exito,String respuesta,String respuestaOculta,List<Map<String,String>>slist)
	{
		this.exito           = exito;
		this.respuesta       = respuesta;
		this.respuestaOculta = respuestaOculta;
		this.slist           = slist;
	}

	/*
	 * Getters y setters
	 */
	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getRespuestaOculta() {
		return respuestaOculta;
	}

	public void setRespuestaOculta(String respuestaOculta) {
		this.respuestaOculta = respuestaOculta;
	}
	
	public List<Map<String, String>> getSlist() {
		return slist;
	}

	public void setSlist(List<Map<String, String>> slist) {
		this.slist = slist;
	}

	@Override
	public String toString()
	{
		return new StringBuilder()
		.append("Exito=").append(exito)
		.append("\nRespuesta=").append(respuesta)
		.append("\nRespuestaOculta=").append(respuestaOculta)
		.append("\nSlist=").append(slist)
		.toString();
	}
	
}