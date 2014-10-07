package mx.com.gseguros.portal.cotizacion.model;


public class ManagerRespuestaVoidVO
{
	private boolean exito           = false;
	private String  respuesta       = null;
	private String  respuestaOculta = null;
	
	public ManagerRespuestaVoidVO()
	{}
	
	public ManagerRespuestaVoidVO(boolean exito)
	{
		this.exito = exito;
	}
	
	public ManagerRespuestaVoidVO(boolean exito,String respuesta,String respuestaOculta)
	{
		this.exito           = exito;
		this.respuesta       = respuesta;
		this.respuestaOculta = respuestaOculta;
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
	
	@Override
	public String toString()
	{
		return new StringBuilder()
		.append("Exito=").append(exito)
		.append("\nRespuesta=").append(respuesta)
		.append("\nRespuestaOculta=").append(respuestaOculta)
		.toString();
	}
	
}