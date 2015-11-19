package mx.com.gseguros.portal.documentos.service;

import java.util.Date;
import java.util.Map;

import mx.com.gseguros.portal.documentos.model.Documento;

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
	
	@Deprecated
	public void guardarDocumento(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,Date feinici
			,String cddocume
			,String dsdocume
			,String nmsolici
			,String ntramite
			,String tipmov
			,String swvisible
			,String codidocu
			,String cdtiptra
			,String cdorddoc
			,Documento documento
			)throws Exception;
}