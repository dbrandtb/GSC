package mx.com.gseguros.portal.emision.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;

public interface EmisionManager
{
	public void setSession(Map<String,Object>session);
	
	public ManagerRespuestaImapVO construirPantallaClausulasPoliza() throws Exception;
	
	public ManagerRespuestaVoidVO guardarClausulasPoliza(List<Map<String,String>>clausulas);
	
	/*
	@Deprecated
	public String insercionDocumentosParametrizados(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String proceso
			)throws Exception;
	*/

	public void getActualizaCuadroComision(Map<String, Object> paramsPoliage)throws Exception;
	
	@Deprecated
	public void actualizaNmsituaextMpolisit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac 
			,String nmsuplem
			,String nmsituaext
			)throws Exception;
	
	@Deprecated
	public void actualizaDatosMpersona(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdestciv
			,String ocup
			)throws Exception;
	
	@Deprecated
	public void validarDocumentoTramite (String ntramite, String cddocume) throws Exception;
	
	@Deprecated
	public String recuperarTramiteCotizacion (String cdunieco, String cdramo, String estado, String nmpoliza) throws Exception;
	
	public boolean revierteEmision(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem);
	
	public String generarLigasDocumentosEmisionLocalesIce (String ntramite) throws Exception;

	public ManagerRespuestaSlistVO procesarCargaMasivaRecupera(String cdramo, String cdtipsit, String respetar, File excel)throws Exception;
	
	
	
}