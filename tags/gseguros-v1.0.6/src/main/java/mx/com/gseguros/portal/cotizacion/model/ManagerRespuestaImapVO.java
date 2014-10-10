package mx.com.gseguros.portal.cotizacion.model;

import java.util.Map;

public class ManagerRespuestaImapVO
{
	private boolean exito           = false;
	private String  respuesta       = null;
	private String  respuestaOculta = null;
	private Map<String,Item> imap   = null;
	
	public ManagerRespuestaImapVO()
	{}
	
	public ManagerRespuestaImapVO(boolean exito)
	{
		this.exito = exito;
	}
	
	public ManagerRespuestaImapVO(boolean exito,String respuesta,String respuestaOculta,Map<String,Item>imap)
	{
		this.exito           = exito;
		this.respuesta       = respuesta;
		this.respuestaOculta = respuestaOculta;
		this.imap            = imap;
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

	public Map<String, Item> getImap() {
		return imap;
	}

	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}
	
	@Override
	public String toString()
	{
		return new StringBuilder()
		.append("Exito=").append(exito)
		.append("\nRespuesta=").append(respuesta)
		.append("\nRespuestaOculta=").append(respuestaOculta)
		.append("\nImap=").append(imap)
		.toString();
	}
	
}