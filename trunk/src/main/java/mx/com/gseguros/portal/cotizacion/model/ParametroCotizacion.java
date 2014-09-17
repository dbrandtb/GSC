package mx.com.gseguros.portal.cotizacion.model;

public enum ParametroCotizacion
{
	
	RANGO_ANIO_MODELO("RANGOMODELO")
	,MENSAJE_TURNAR("MENSAJETURNAR")
	,MINIMOS_Y_MAXIMOS("MINMAXVALUES")
	;
	
	private String parametro;
	
	private ParametroCotizacion(String parametro)
	{
		this.parametro=parametro;
	}
	
	public String getParametro()
	{
		return this.parametro;
	}
}