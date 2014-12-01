package mx.com.gseguros.portal.endosos.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.ParametroEndoso;
import mx.com.gseguros.portal.general.model.ComponenteVO;

public interface EndososDAO
{
    public List<Map<String,String>> obtenerEndosos(Map<String,String>params)                   throws Exception;
    public Map<String, String>      guardarEndosoNombres(Map<String, Object> params)           throws Exception;
    @Deprecated
    public Map<String, String>      confirmarEndosoB(Map<String, String> params)               throws Exception;
    public void confirmarEndosoB(
    		String cdunieco
    		,String cdramo
    		,String estado
    		,String nmpoliza
    		,String nmsuplem
    		,String nsuplogi
    		,String cdtipsup
    		,String dscoment
    		)throws Exception;
    public Map<String, String>      guardarEndosoDomicilio(Map<String, Object> params)         throws Exception;
    @Deprecated
    public List<Map<String,String>> reimprimeDocumentos(Map<String,String>params)              throws Exception;
    public List<Map<String,String>> reimprimeDocumentos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsup)throws Exception;
    public List<Map<String,String>> obtieneCoberturasDisponibles (Map<String,String>params)    throws Exception;
    public Map<String, String>      guardarEndosoCoberturas(Map<String, Object> params)        throws Exception;
	public List<Map<String,String>> obtenerAtributosCoberturas(Map<String, String> params)     throws Exception;
	public Map<String,Object>       sigsvalipolEnd(Map<String, String> params)                 throws Exception;
	public Map<String, String>      guardarEndosoClausulas(Map<String, Object> params)         throws Exception;
	public Map<String, String>      calcularValorEndoso(Map<String, Object> params)            throws Exception;
	@Deprecated
	public Map<String, String>      iniciarEndoso(Map<String, String> params)                  throws Exception;
	public Map<String, String>      iniciarEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,Date fecha
			,String cdelemen
			,String cdusuari
			,String proceso
			,String cdtipsup)throws Exception;
	public void                     insertarTworksupEnd(Map<String, String> params)            throws Exception;
	public void                     insertarTworksupSitTodas(Map<String, String> params)       throws Exception;
	public Map<String, String>      obtieneDatosMpolisit(Map<String, String> params)           throws Exception;
	public List<Map<String,String>> obtenerNombreEndosos(String cdsisrol)                      throws Exception;
	public void                     actualizarFenacimi(Map<String, String> params)             throws Exception;
	public void                     actualizarSexo(Map<String, String> params)                 throws Exception;
	public List<Map<String,String>> obtenerCdpersonMpoliper(Map<String, String> params)        throws Exception;
	/**
	 * Obtiene el N&uacute;mero de Tr&aacute;mite de Emisi&oacute;n de una p&oacute;liza
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public List<Map<String,String>> obtenerNtramiteEmision(Map<String, String> params)         throws Exception;
	public String obtenerNtramiteEmision(String cdunieco,String cdramo,String estado,String nmpoliza)throws ApplicationException,Exception;
	@Deprecated
	public void                     validaEndosoAnterior(Map<String, String> params)           throws Exception;
	public void validaEndosoAnterior(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			)throws Exception;
	public void                     actualizaDeducibleValosit(Map<String, String> params)      throws Exception;
	public void                     actualizaCopagoValosit(Map<String, String> params)         throws Exception;
	public Map<String, String>      pClonarPolizaReexped(Map<String, String> params)           throws Exception;
	public List<Map<String,String>> obtenerValositUltimaImagen(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			) throws Exception;
	public void                     actualizaExtraprimaValosit(Map<String, String> params)     throws Exception;
	public void                     insertarPolizaCdperpag(Map<String, String> params)         throws Exception;
	/**
	 * PKG_ENDOSOS.P_GET_FEINIVAL_END_FP
	 */
	public Date                     obtenerFechaEndosoFormaPago(Map<String, String> params)    throws Exception;
	/**
	 * P_CALC_RECIBOS_SUB_ENDOSO_FP
	 */
	public void                     calcularRecibosEndosoFormaPago(Map<String, String> params) throws Exception;
	/**
	 * P_CALCULA_COMISION_BASE
	 */
	public void                     calcularComisionBase(Map<String, String> params)           throws Exception;
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
	public List<Map<String,String>> obtenerAgentesEndosoAgente(Map<String, String> params)     throws Exception;
	/**
	 * PKG_SATELITES.P_MOV_MPOLIAGE
	 */
	public void                     pMovMpoliage(Map<String, String> params)                   throws Exception;
	/**
	 * PKG_SATELITES.P_GET_NMSUPLEM_EMISION
	 */
	public String                   pGetSuplemEmision(Map<String,String>params)                throws Exception;
	
	public String obtieneFechaInicioVigenciaPoliza(String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception;
	
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
	
	public void validaEstadoCodigoPostal(Map<String, String> params) throws Exception;
	
	public void actualizaTvalositCoberturasAdicionales(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdtipsup) throws Exception;
	
	public ComponenteVO obtenerComponenteSituacionCobertura(String cdramo,String cdtipsit,String cdtipsup,String cdgarant)throws ApplicationException,Exception;
	public void actualizaTvalositSitaucionCobertura(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdatribu
			,String otvalor)throws Exception;
	public Map<String,String>obtenerParametrosEndoso(
			ParametroEndoso parametro
			,String cdramo
			,String cdtipsit
			,String cdtipsup
			,String clave5)throws ApplicationException,Exception;
	public void guardarAtributosSituacionGeneral(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,Map<String,String>tvalosit)throws Exception;
	
	/**
	 * 
	 * @param params
	 * @throws Exception
	 */
	public void insertarMpolicap(Map<String, String> params) throws Exception;
	
}