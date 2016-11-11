package mx.com.gseguros.portal.mesacontrol.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.documentos.model.Documento;

public interface MesaControlDAO
{
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String cargarCdagentePorCdusuari(String cdusuari) throws Exception;
	
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
	 * @param swimpres
	 * @param valores
	 * @param cdtipsup TODO
	 * @return
	 * @throws Exception
	 */
	public String movimientoMesaControl(
			String cdunieco  , String cdramo   , String estado     , String nmpoliza
			,String nmsuplem , String cdsucadm , String cdsucdoc   , String cdtiptra
			,Date ferecepc   , String cdagente , String referencia , String nombre
			,Date festatus   , String status   , String comments   , String nmsolici
			,String cdtipsit , String cdusuari , String cdsisrol   , String swimpres
			,String cdtipflu , String cdflujomc
			,Map<String, String> valores, String cdtipsup
			)throws Exception;
	
	/**
	 * 
	 * @param ntramite
	 * @param feinicio
	 * @param cdclausu
	 * @param comments
	 * @param cdusuari
	 * @param cdmotivo
	 * @param cdusuariDest TODO
	 * @param cdsisrolDest TODO
	 * @throws Exception
	 */
	public void movimientoDetalleTramite(String ntramite, Date feinicio,
			String cdclausu, String comments, String cdusuari, String cdmotivo, String cdsisrol,String swagente, String cdusuariDest, String cdsisrolDest)
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
	 * Guarda un documento asociado a una poliza
	 * @param cdunieco Unidad economica de la poliza
	 * @param cdramo   Codigo de Ramo de la poliza
	 * @param estado   Estado de la poliza
	 * @param nmpoliza Numero de poliza
	 * @param nmsuplem Numero de suplemento / imagen de la poliza
	 * @param feinici  Fecha de inicio
	 * @param cddocume Codigo del documento
	 * @param dsdocume Descripcion del documento
	 * @param nmsolici  Numero de solicitud
	 * @param ntramite  Numero de tramite
	 * @param tipmov    Tipo de Movimiento / Tipo de Suplemento
	 * @param swvisible Indica si sera visible en la lista de documentos
	 * @param codidocu Codigo de documento
	 * @param cdtiptra Tipo de tramite
	 * @param cdorddoc TODO
	 * @param cdmoddoc TODO
	 * @throws Exception
	 */
	public void guardarDocumento(String cdunieco, String cdramo, String estado,
			String nmpoliza, String nmsuplem, Date feinici, String cddocume,
			String dsdocume, String nmsolici, String ntramite, String tipmov,
			String swvisible, String codidocu, String cdtiptra, String cdorddoc, Documento documento) throws Exception;
	
	public String turnaPorCargaTrabajo(
			String ntramite
			,String cdsisrol
			,String status
			)throws Exception;
	
	public void validarAntesDeTurnar(
    		String ntramite
    		,String status
    		,String cdusuari
    		,String cdsisrol
    		)throws Exception;
	
	
	/**
	 * Actualiza estatus de un tramite en la Mesa de Control
	 * @param ntramite
	 * @param status
	 * @throws Exception
	 */
	public void actualizaStatusMesaControl(String ntramite, String status) throws Exception;
	
	public void actualizarStatusRemesa(
			String ntramite
			,String status
			)throws Exception;
	
	public List<Map<String,String>> recuperarTramites(
			String cdunieco
			,String ntramite
			,String cdramo
			,String nmpoliza
			,String estado
			,String cdagente
			,String status
			,String cdtipsit
			,Date fedesde
			,Date fehasta
			,String cdsisrol
			,String cdtiptra
			,String contrarecibo
			,String tipoPago
			,String nfactura
			,String cdpresta
			,String cdusuari
			,String cdtipram
			,String lote
			,String tipolote
			,String tipoimpr
			,String cdusuari_busq
			)throws Exception;
	
	public void actualizarHijosRemesa(
			String lote
			,String ntramite
			,String status
			)throws Exception;
	
	public Map<String,Boolean> marcarImpresionOperacion(
			String cdsisrol
			,String ntramite
			,String marcar
			)throws Exception;
	
	public void marcarTramiteVistaPrevia(String ntramite) throws Exception;
	
	public String recuperarSwvispreTramite(String ntramite) throws Exception;
}