package mx.com.gseguros.portal.cotizacion.model;

import java.util.Map;

public class ManagerRespuestaImapSmapVO extends ManagerRespuestaBaseVO
{
	private Map<String,Item> imap   = null;
	private Map<String,String> smap = null;
	
	public ManagerRespuestaImapSmapVO()
	{}
	
	public ManagerRespuestaImapSmapVO(boolean exito)
	{
		this.exito = exito;
	}
	
	public ManagerRespuestaImapSmapVO(boolean exito,String respuesta,String respuestaOculta,Map<String,Item>imap,Map<String,String>smap)
	{
		this.exito           = exito;
		this.respuesta       = respuesta;
		this.respuestaOculta = respuestaOculta;
		this.imap            = imap;
		this.smap            = smap;
	}

	/*
	 * Getters y setters
	 */public Map<String, Item> getImap() {
		return imap;
	}

	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
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
		.append("\nImap=").append(imap.keySet())
		.toString();
	}
	
}