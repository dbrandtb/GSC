package mx.com.gseguros.portal.documentos.service;

import java.util.Map;

public interface DocumentosManager
{
	public final static int PROCESO_EMISION = 1
			                ,PROCESO_ENDOSO = 2;
	
	public Map<String,String> generarDocumentosParametrizados(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,int proceso
			,String ntramite
			,String nmsolici
			)throws Exception;
}