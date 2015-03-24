package mx.com.gseguros.portal.endosos.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.ParametroEndoso;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.PolizaVO;

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
    @Deprecated
    public Map<String, String>      iniciaEndoso(Map<String, Object> params)        throws Exception;
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
	public List<Map<String,String>> obtenerNombreEndosos(String cdsisrol, Integer cdramo, String cdtipsit) throws Exception;
	public String                   obtieneDescripcionEndoso(String cdtipsup)                  throws Exception;
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
	
	public void                     actualizaVigenciaPoliza(Map<String, String> params)        throws Exception;
	
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

	public void calcularRecibosCambioContratante(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem) throws Exception;
	
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
	
	public void movimientoMpoliperBeneficiario(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdrol
			,String cdperson
			,String nmsuplem
			,String status
			,String nmorddom
			,String swreclam
			,String swexiper
			,String cdparent
			,String porbenef
			,String accion)throws Exception;
	
	
	/**
	 * Obtiene la lista de documentos de endosos de una poliza
	 * @param poliza poliza a la que pertenecen los endosos
	 * @return listado de documentos de endosos
	 * @throws Exception
	 */
	public List<Map<String,String>> obtenerListaDocumentosEndosos(PolizaVO poliza) throws Exception;
	
	public void insertarIncisoEvaluacion(
			String stamp
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String cdtipsit
			)throws Exception;
	
	public List<Map<String,String>>recuperarEndososClasificados(
			String stamp
			,String cdramo
			,String nivel
			,String multiple
			,String tipoflot
			,String cdsisrol
			)throws Exception;
	
	public void guardarTvalositEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String status
			,String cdtipsit
			,String otvalor01,String otvalor02,String otvalor03,String otvalor04,String otvalor05
			,String otvalor06,String otvalor07,String otvalor08,String otvalor09,String otvalor10
			,String otvalor11,String otvalor12,String otvalor13,String otvalor14,String otvalor15
			,String otvalor16,String otvalor17,String otvalor18,String otvalor19,String otvalor20
			,String otvalor21,String otvalor22,String otvalor23,String otvalor24,String otvalor25
			,String otvalor26,String otvalor27,String otvalor28,String otvalor29,String otvalor30
			,String otvalor31,String otvalor32,String otvalor33,String otvalor34,String otvalor35
			,String otvalor36,String otvalor37,String otvalor38,String otvalor39,String otvalor40
			,String otvalor41,String otvalor42,String otvalor43,String otvalor44,String otvalor45
			,String otvalor46,String otvalor47,String otvalor48,String otvalor49,String otvalor50
			,String otvalor51,String otvalor52,String otvalor53,String otvalor54,String otvalor55
			,String otvalor56,String otvalor57,String otvalor58,String otvalor59,String otvalor60
			,String otvalor61,String otvalor62,String otvalor63,String otvalor64,String otvalor65
			,String otvalor66,String otvalor67,String otvalor68,String otvalor69,String otvalor70
			,String otvalor71,String otvalor72,String otvalor73,String otvalor74,String otvalor75
			,String otvalor76,String otvalor77,String otvalor78,String otvalor79,String otvalor80
			,String otvalor81,String otvalor82,String otvalor83,String otvalor84,String otvalor85
			,String otvalor86,String otvalor87,String otvalor88,String otvalor89,String otvalor90
			,String otvalor91,String otvalor92,String otvalor93,String otvalor94,String otvalor95
			,String otvalor96,String otvalor97,String otvalor98,String otvalor99
			,String tstamp)throws Exception;
	
	public Map<String,Object> confirmarEndosoTvalositAuto(
			String cdtipsup
			,String tstamp
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdusuari
			,String cdsisrol
			,String cdelemen
			)throws Exception;
	
	public List<Map<String,String>> obtieneDatosEndPlacasMotor(Map<String, String> params)throws Exception;
	public List<Map<String,String>> obtieneDatosEndSerie(Map<String, String> params)throws Exception;
	public List<Map<String,String>> obtieneDatosEndBeneficiario(Map<String, String> params)throws Exception;
	public void actualizaNumeroEndosSigs(Map<String, String> params)throws Exception;
	
	public void confirmarEndosoAltaIncisoAuto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String tstamp
			,String cdusuari
			,String cdelemen
			,String cdtipsup
			)throws Exception;
	
	public void confirmarEndosoBajaIncisos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String tstamp
			,String cdusuari
			,String cdelemen
			,String cdtipsup
			)throws Exception;
}