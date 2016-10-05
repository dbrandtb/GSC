package mx.com.gseguros.portal.renovacion.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RenovacionDAO
{
	public List<Map<String,String>>buscarPolizasRenovables(String cdunieco,String cdramo,String anio,String mes)throws Exception;
	
	public void marcarPoliza(String anio
			,String mes
			,String cdtipopc
			,String cdtipacc
			,String cdunieco
			,String cdramo
			,String nmpoliza
			,Date   feemisio
			,String swrenova
			,String swaproba
			,String nmsituac
			,String cducreno)throws Exception;
	
	
	/**
	 * Proceso que renueva las polizas indicadas
	 * @param cdusuari
	 * @param anio
	 * @param mes
	 * @param cdtipopc
	 * @param cdsisrol
	 * @return Conjunto de polizas renovadas
	 * @throws Exception
	 */
	public List<Map<String,String>> renovarPolizas(String cdusuari, String anio, String mes, String cdtipopc, String cdsisrol) throws Exception;
	
	public void actualizaRenovacionDocumentos(
			String anio
			,String mes
			,String cdtipopc
			,String cdunieco
			,String cdramo
			,String nmpoliza)throws Exception;
	
	public List<Map<String,String>>cargarDocumentosSubidosPorUsuario(String cdunieco,String cdramo,String estado,String nmpoliza)throws Exception;
	
	public List<Map<String,String>> busquedaRenovacionIndividual(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza)throws Exception;
	
	public List<Map<String,String>> busquedaRenovacionIndividualMasiva(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String cdtipsit,
			String fecini,
			String fecfin,
			String status)throws Exception;
	
	public Map<String,String> renuevaPolizaIndividual(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String estadoNew,
			String usuario)throws Exception;
	
	public List<Map<String,String>> obtenerPolizaCdpersonTramite(String ntramite)throws Exception;
	
	public List<Map<String,String>> confirmarPolizaIndividual(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			String ntramite)throws Exception;
	
	public void generaTcartera(
			String cdunieco,
			String cdramo,
			String nmpoliza,
			String feefecto,
			String feefecto_ant,
			String nmsuplem,
			String cdagente,
			String cdperpag,
			String cdcontra,
			String cdmoneda)throws Exception;
	
	public void actualizaContratanteFormaPago(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String cdperpag,
			String cdcontra)throws Exception;
	
	public Map<String, String> confirmarTramite(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String cdperpag,
			String feefecto)throws Exception;
}