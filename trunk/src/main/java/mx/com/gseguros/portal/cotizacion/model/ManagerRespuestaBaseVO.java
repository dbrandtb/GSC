package mx.com.gseguros.portal.cotizacion.model;

public class ManagerRespuestaBaseVO
{
	protected boolean exito           = false;
	protected String  respuesta       = null;
	protected String  respuestaOculta = null;
	
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
}