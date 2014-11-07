package mx.com.gseguros.portal.cotizacion.model;

public enum ParametroCotizacion
{
	
	ATRIBUTO_VARIABLE_TATRIGAR("ATRIVAR_TATRIGAR")//ESTE ATRIBUTO ES EL QUE RECIBE PKG_LISTAS.P_GET_ATRI_GARANTIA (DE TVALOSIT)
	,DEPRECIACION("DEPRECIACION")                 //PARA LA DEPRECIACION DE SERVICIO PUBLICO
	,RANGO_ANIO_MODELO("RANGOMODELO")             //PARA EL MODELO DE SERVICIO PUBLICO
	,MENSAJE_TURNAR("MENSAJETURNAR")              //MENSAJE DE COLECTIVO AL TURNAR TRAMITE
	,MINIMOS_Y_MAXIMOS("MINMAXVALUES")            //MINIMOS Y MAXIMOS PARA AUTOS
	,NUMERO_PASAJEROS_SERV_PUBL("6NUMPASAJE")     //NUMERO DE PASAJEROS PARA AUTOS
	,PROCEDURE_CENSO("PL_CENSO")                  //PROCEDIMIENTO QUE PROCESA LOS EXCEL DE CENSO PARA COLECTIVOS
	,RANGO_COBERTURAS_DEPENDIENTES("5SUMASCOBER") //RANGOS PARA COBERTURAS DEPENDIENTES DE RAMO 5
	,RANGO_VALOR("5RANGOVALOR")                   //RANGO DE EDICION DE SUMA ASEGURADA DE AUTO RAMO 5
	,RANGO_VIGENCIA("RANGOVIGENCIA")              //VIGENCIA MINIMA Y MAXIMA SOPORTADA EN PANTALLA
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