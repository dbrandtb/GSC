package mx.com.gseguros.portal.consultas.model;


/**
 * ESPEJO DE PKG_SATELITES2.P_INSERTA_TBASVALSIT		
 * @author jair
 *
 */
public class DocumentoReciboParaMostrarDTO
{
	private String ntramite
	               ,cddocume;
	
	public DocumentoReciboParaMostrarDTO(
			String ntramite
			,String cddocume)
	{
		this.ntramite = ntramite;
		this.cddocume = cddocume;
	}
	
	public String[] indexar()
	{
		return new String[]{
				this.ntramite
				,this.cddocume
		};
	}
	
}