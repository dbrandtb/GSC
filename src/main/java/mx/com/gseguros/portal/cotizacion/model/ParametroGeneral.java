package mx.com.gseguros.portal.cotizacion.model;

public enum ParametroGeneral
{
	DIRECTORIO_REPORTES("DIR-REPORTS"),
	VALIDACION_SIGS_TRAMITE("VALITRASIGS"); // Que tramites se validan con sigs? |EMISION|ENDOSO|RENOVACION|
	
	private String nomparam;
	
	private ParametroGeneral(String nomparam)
	{
		this.nomparam = nomparam;
	}
	
	public String getNomparam()
	{
		return this.nomparam;
	}
}