package mx.com.gseguros.portal.general.service;

import java.util.Date;

public interface ServiciosManager
{
	public String reemplazarDocumentoCotizacion(StringBuilder sb, String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception;
	
	public void grabarEvento(
			StringBuilder sb
			,String cdmodulo
			,String cdevento
			,Date fecha
			,String cdusuari
			,String cdsisrol
			,String ntramite
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsolici
			,String cdagente
			,String cdusuariDes
			,String cdsisrolDes
			)throws Exception;
	
	public void recibosSubsecuentes(
			String rutaDocumentosTemporal
			,boolean test
			) throws Exception;
	
	public int generarFlagsTramites () throws Exception;
	
	public int actualizarFlagsTramites () throws Exception;
	
	public void bloquearProceso (String cdproceso, boolean bloquear, String cdusuari, String cdsisrol) throws Exception;
}