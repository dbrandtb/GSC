package mx.com.gseguros.portal.documentos.model;

public enum Documento
{
	REMESA_IMPRESION_LOTE ("59")
	,RECIBO               ("60");
	
	private String cdmoddoc;

	private Documento(String cdmoddoc)
	{
		this.cdmoddoc = cdmoddoc;
	}

	public String getCdmoddoc()
	{
		return cdmoddoc;
	}
}