package mx.com.gseguros.portal.endosos.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.general.model.RespuestaVO;

public interface EndososManager
{
    public List<Map<String,String>>  obtenerEndosos(Map<String,String>params)                 throws Exception;
    public Map<String,String>        guardarEndosoNombres(Map<String,Object>params)           throws Exception;
    public Map<String, String>       confirmarEndosoB(Map<String, String> params)             throws Exception;
    public Map<String,String>        guardarEndosoDomicilio(Map<String,Object>params)         throws Exception;
    /**
     * PKG_CONSULTA.P_reImp_documentos
     */
    public List<Map<String, String>> reimprimeDocumentos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String tipmov
			,String cdusuari
			)          throws Exception;
    public List<Map<String, String>> obtieneCoberturasDisponibles(Map<String, String> params) throws Exception;
    public Map<String,String>        guardarEndosoCoberturas(Map<String,Object>params)        throws Exception;
	public List<Map<String, String>> obtenerAtributosCoberturas(Map<String, String> params)   throws Exception;
	/**
	 * PKG_COTIZA.P_EJECUTA_SIGSVALIPOL_END
	 */
	public Map<String,Object>        sigsvalipolEnd(Map<String, String> params)               throws Exception;
	/**
	 * PKG_COTIZA.P_EJECUTA_SIGSVALIPOL_END
	 */
	public Map<String,Object>        sigsvalipolEnd(
			String cdusuari
			,String cdelemento
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdtipsit
			,String cdtipsup
			) throws Exception;
	public Map<String,String>        guardarEndosoClausulas(Map<String,Object>params)         throws Exception;
	/**
	 * PKG_ENDOSOS.P_CALC_VALOR_ENDOSO
	 */
	public Map<String,String>        calcularValorEndoso(Map<String,Object>params)            throws Exception;
	/**
	 * PKG_ENDOSOS.P_CALC_VALOR_ENDOSO
	 */
	public Map<String,String>        calcularValorEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,Date   feinival
			,String cdtipsup) throws Exception;
	/**
	 * PKG_ENDOSOS.P_ENDOSO_INICIA
	 */
	public Map<String,String>        iniciarEndoso(Map<String,String>params)                  throws Exception;
	/**
	 * PKG_ENDOSOS.P_ENDOSO_INICIA
	 */
	public Map<String,String>        iniciarEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String fecha
			,String cdelemento
			,String cdusuari
			,String proceso
			,String cdtipsup) throws Exception;
	/**
	 * PKG_SATELITES.P_INSERTA_TWORKSUP_END
	 */
	public void                      insertarTworksupEnd(Map<String,String>params)            throws Exception;
	/**
	 * PKG_SATELITES.P_INSERTA_TWORKSUP_END
	 */
	public void                      insertarTworksupEnd(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String nmsuplem
			,String nmsituac) throws Exception;
	public void                      insertarTworksupSitTodas(Map<String,String>params)       throws Exception;
	public Map<String, String>       obtieneDatosMpolisit(Map<String, String> params)         throws Exception;
	public Map<String, String>       obtieneDatosMpolisit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)         throws Exception;
	public List<Map<String, String>> obtenerNombreEndosos(String cdsisrol)                    throws Exception;
	public void                      actualizarFenacimi(Map<String, String> params)           throws Exception;
	public void                      actualizarSexo(Map<String, String> params)               throws Exception;
	public List<Map<String, String>> obtenerCdpersonMpoliper(Map<String, String> params)      throws Exception;
	
	/**
	 * Obtiene el N&uacute;mero de Tr&aacute;mite de Emisi&oacute;n de una p&oacute;liza
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @return
	 * @throws Exception
	 */
	public String obtenerNtramiteEmision(String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception;
	
	/**
	 * Valida si existe un endoso anterior pendiente
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param cdtipsup
	 * @return
	 */
	public RespuestaVO validaEndosoAnterior(String cdunieco, String cdramo, String estado, String nmpoliza, String cdtipsup);
	
	public void                      actualizaDeducibleValosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String deducible) throws Exception;
	public void                      actualizaCopagoValosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String deducible) throws Exception;
	public Map<String,String>        pClonarPolizaReexped(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String fecha
			,String cdplan) throws Exception;
	public List<Map<String, String>> obtenerValositPorNmsuplem(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem) throws Exception;
	/**
	 * PKG_ENDOSOS.P_INS_NEW_EXTRAPRIMA_TVALOSIT
	 */
	public void                      actualizaExtraprimaValosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String extraprima) throws Exception;
	/**
	 * PKG_ENDOSOS.P_INS_MPOLIZAS_CDPERPAG
	 */
	public void                      insertarPolizaCdperpag(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdperpag) throws Exception;
	/**
	 * PKG_ENDOSOS.P_GET_FEINIVAL_END_FP
	 */
	public Date                      obtenerFechaEndosoFormaPago(
			String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception;
	/**
	 * P_CALC_RECIBOS_SUB_ENDOSO_FP
	 */
	public void calcularRecibosEndosoFormaPago(String cdunieco,String cdramo,
			String estado,String nmpoliza,String nmsuplem) throws Exception;
	/**
	 * P_CALCULA_COMISION_BASE
	 */
	public void calcularComisionBase(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem) throws Exception;
	/**
	 * PKG_CONSULTA.P_GET_AGENTE_POLIZA
	 * @return a.cdunieco,
			a.cdramo,
			a.estado,
			a.nmpoliza,
			a.cdagente,
			a.nmsuplem,
			a.status,
			a.cdtipoag,
			porredau,
			a.porparti,
			nombre,
			cdsucurs,
			nmcuadro
	 */
	public List<Map<String,String>> obtenerAgentesEndosoAgente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem) throws Exception;
	/**
	 * PKG_SATELITES.P_MOV_MPOLIAGE
	 */
	public void pMovMpoliage(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdagente
			,String nmsuplem
			,String status
			,String cdtipoag
			,String porredau
			,String nmcuadro
			,String cdsucurs
			,String accion
			,String ntramite
			,String porparti
			) throws Exception;
	/**
	 * PKG_SATELITES.P_GET_NMSUPLEM_EMISION
	 */
	public String pGetSuplemEmision(
			 String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza) throws Exception;
	
	public String obtieneFechaInicioVigenciaPoliza
	(
		String cdunieco,
		String cdramo,
		String estado,
		String nmpoliza
		) throws Exception;
	
	public boolean validaEndosoSimple
	(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza
			) throws Exception;
	
	public void validaNuevaCobertura(String cdgarant, Date fenacimi) throws Exception;
	
	public void calcularRecibosCambioAgente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdagente) throws Exception;
	
	public List<Map<String,String>> habilitaRecibosSubsecuentes(
			Date fechaDeInicio
			,Date fechaDeFin
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza) throws Exception;
	
	public void validaEstadoCodigoPostal(Map<String,String>params) throws Exception;
}