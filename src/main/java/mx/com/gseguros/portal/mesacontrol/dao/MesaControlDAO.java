package mx.com.gseguros.portal.mesacontrol.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MesaControlDAO
{
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String cargarCdagentePorCdusuari(Map<String,String>params) throws Exception;
	
	/**
	 * 
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param cdsucadm
	 * @param cdsucdoc
	 * @param cdtiptra
	 * @param ferecepc
	 * @param cdagente
	 * @param referencia
	 * @param nombre
	 * @param festatus
	 * @param status
	 * @param comments
	 * @param nmsolici
	 * @param cdtipsit
	 * @param valores
	 * @return
	 * @throws Exception
	 */
	public String movimientoMesaControl(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String cdsucadm,
			String cdsucdoc, String cdtiptra, Date ferecepc, String cdagente,
			String referencia, String nombre, Date festatus, String status,
			String comments, String nmsolici, String cdtipsit,
			Map<String, String> valores) throws Exception;
	
	/**
	 * 
	 * @param ntramite
	 * @param feinicio
	 * @param cdclausu
	 * @param comments
	 * @param cdusuari
	 * @param cdmotivo
	 * @throws Exception
	 */
	public void movimientoDetalleTramite(String ntramite, Date feinicio,
			String cdclausu, String comments, String cdusuari, String cdmotivo)
			throws Exception;
	
	/**
	 * 
	 * @param ntramite
	 * @param nmsolici
	 * @throws Exception
	 */
	public void actualizarNmsoliciTramite(String ntramite, String nmsolici) throws Exception;
	
	/**
	 * 
	 * @param ntramite
	 * @param cdramo
	 * @param cdtipsit
	 * @param cdsucadm
	 * @param cdsucdoc
	 * @param comments
	 * @param valores
	 * @throws Exception
	 */
	public void actualizaValoresTramite(String ntramite, String cdramo,
			String cdtipsit, String cdsucadm, String cdsucdoc, String comments,
			Map<String, String> valores) throws Exception;
	
	/**
	 * 
	 * @param cdtiptra
	 * @param ntramite
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param nmsolici
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> cargarTramitesPorParametrosVariables(
			String cdtiptra, String ntramite, String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String nmsolici)
			throws Exception;
	
	/**
	 * 
	 * @param ntramite
	 * @param cdusuari
	 * @throws Exception
	 */
	public void guardarRegistroContrarecibo(String ntramite,String cdusuari) throws Exception;
	
	/**
	 * 
	 * @param ntramite
	 * @param cddocume
	 * @param nuevo
	 * @throws Exception
	 */
	public void actualizarNombreDocumento(String ntramite,String cddocume,String nuevo) throws Exception;
	
	/**
	 * 
	 * @param ntramite
	 * @param cddocume
	 * @throws Exception
	 */
	public void borrarDocumento(String ntramite, String cddocume) throws Exception;
	
	/**
	 * Guardar documento de una poliza
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param feinici
	 * @param cddocume
	 * @param dsdocume
	 * @param nmsolici
	 * @param ntramite
	 * @param tipmov
	 * @param swvisible
	 * @throws Exception
	 */
	public void guardarDocumento(String cdunieco, String cdramo, String estado,
			String nmpoliza, String nmsuplem, Date feinici, String cddocume,
			String dsdocume, String nmsolici, String ntramite, String tipmov,
			String swvisible) throws Exception;
	
}