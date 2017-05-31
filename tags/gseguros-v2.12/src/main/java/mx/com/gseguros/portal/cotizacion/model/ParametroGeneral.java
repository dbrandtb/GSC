package mx.com.gseguros.portal.cotizacion.model;

public enum ParametroGeneral
{
	DIRECTORIO_REPORTES("DIR-REPORTS");
	
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