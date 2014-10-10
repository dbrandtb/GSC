package mx.com.gseguros.portal.cotizacion.model;

import java.util.Map;

public class ManagerRespuestaSmapVO
{
	private boolean exito           = false;
	private String  respuesta       = null;
	private String  respuestaOculta = null;
	private Map<String,String> smap = null;
	
	public ManagerRespuestaSmapVO()
	{}
	
	public ManagerRespuestaSmapVO(boolean exito)
	{
		this.exito = exito;
	}
	
	public ManagerRespuestaSmapVO(boolean exito,String respuesta,String respuestaOculta,Map<String,String>smap)
	{
		this.exito           = exito;
		this.respuesta       = respuesta;
		this.respuestaOculta = respuestaOculta;
		this.smap            = smap;
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

	public Map<String, String> getSmap() {
		return smap;
	}

	public void setSmap(Map<String, String> smap) {
		this.smap = smap;
	}
	
	@Override
	public String toString()
	{
		return new StringBuilder()
		.append("Exito=").append(exito)
		.append("\nRespuesta=").append(respuesta)
		.append("\nRespuestaOculta=").append(respuestaOculta)
		.append("\nSmap=").append(smap)
		.toString();
	}
	
}