package mx.com.gseguros.portal.cotizacion.model;


public class ManagerRespuestaVoidVO extends ManagerRespuestaBaseVO
{
	
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