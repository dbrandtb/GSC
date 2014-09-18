package mx.com.gseguros.portal.cotizacion.model;

public enum ParametroCotizacion
{
	
	DEPRECIACION("DEPRECIACION")
	,RANGO_ANIO_MODELO("RANGOMODELO")
	,MENSAJE_TURNAR("MENSAJETURNAR")
	,MINIMOS_Y_MAXIMOS("MINMAXVALUES")
	,NUMERO_PASAJEROS_SERV_PUBL("6NUMPASAJE")
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