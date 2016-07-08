package mx.com.gseguros.portal.endosos.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.ParametroEndoso;
import mx.com.gseguros.portal.endosos.model.PropiedadesDeEndosoParaWS;
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
	
    public List<Map<String,String>> obtieneCoberturasDisponibles (Map<String,String>params)    throws Exception;
    @Deprecated
    public Map<String, String>      iniciaEndoso(Map<String, Object> params)        throws Exception;
	public List<Map<String,String>> obtenerAtributosCoberturas(Map<String, String> params)     throws Exception;
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
	
	public void movimientoTworksupEnd(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String nmsuplem
			,String nmsituac
			,String accion
			)throws Exception;
	
	public void                     insertarTworksupSitTodas(Map<String, String> params)       throws Exception;
	public Map<String, String>      obtieneDatosMpolisit(Map<String, String> params)           throws Exception;
	public List<Map<String,String>> obtenerNombreEndosos(String cdsisrol, Integer cdramo, String cdtipsit) throws Exception;
	public String                   obtieneDescripcionEndoso(String cdtipsup)                  throws Exception;
	public void                     actualizaNombreCliente(Map<String, String> params)             throws Exception;
	public void                     actualizaRfcCliente(Map<String, String> params)             throws Exception;
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
	
	public void validaEndosoPagados(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			)throws Exception;
	public void                     actualizaDeducibleValosit(Map<String, String> params)      throws Exception;
	
	public void                     actualizaVigenciaPoliza(Map<String, String> params)        throws Exception;
	public void                     insertaTextoLibre(Map<String, String> params)        throws Exception;
	
	public void                     actualizaCopagoValosit(Map<String, String> params)         throws Exception;
	public Map<String, String>      pClonarPolizaReexped(Map<String, String> params)           throws Exception;
	public Map<String, String>      pClonarCotizacionTotal(Map<String, String> params)           throws Exception;

	public boolean clonaGrupoReexp(Map<String, String> params) throws Exception;
	public boolean actualizaGrupoReexp(Map<String, String> params) throws Exception;
	public boolean actualizaTodosGrupoReexp(Map<String, String> params) throws Exception;
	public boolean valoresDefectoGrupoReexp(Map<String, String> params) throws Exception;
	public boolean valoresDefectoGruposReexp(Map<String, String> params) throws Exception;
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
	 * cancela Recibos Cambio Cliente
	 * @param params
	 * @throws Exception
	 */
	public void                     cancelaRecibosCambioCliente(Map<String, String> params) throws Exception;
	
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

	public List<Map<String,String>> obtenerAseguradosPoliza(Map<String, String> params)throws Exception;
	
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
	
	public void validaNuevaCobertura(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdgarant
			) throws Exception;
	
	public void calcularRecibosCambioAgente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem) throws Exception;

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
	
	public List<ComponenteVO> obtenerComponentesSituacionCobertura(String cdramo,String cdtipsit,String cdtipsup,String cdgarant)throws ApplicationException,Exception;
	public void actualizaTvalositSitaucionCobertura(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
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
			,String cdusuari
			,String cdtipsit
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
			,Date   fechaEndoso
			,String cdusuari
			,String cdsisrol
			,String cdelemen
			)throws Exception;
	
	public List<Map<String,String>> obtieneDatosEndVigenciaPol(Map<String, String> params)throws Exception;
	public List<Map<String,String>> obtieneDatosEndPlacasMotor(Map<String, String> params)throws Exception;
	public List<Map<String,String>> obtieneDatosEndTipoServicio(Map<String, String> params)throws Exception;
	public List<Map<String,String>> obtieneDatosEndSerie(Map<String, String> params)throws Exception;
	public List<Map<String,String>> obtieneDatosEndBeneficiario(Map<String, String> params)throws Exception;
	public List<Map<String,String>> obtieneDatosEndAseguradoAlterno(Map<String, String> params)throws Exception;
	public List<Map<String,String>> obtieneDatosEndAdaptacionesRC(Map<String, String> params)throws Exception;
	public List<Map<String,String>> obtieneDatosEndVigencia(Map<String, String> params)throws Exception;
	public List<Map<String,String>> obtieneDatosEndTextoLibre(Map<String, String> params)throws Exception;
	public void actualizaNumeroEndosSigs(Map<String, String> params)throws Exception;
	
	public Map<String,Object> confirmarEndosoAltaIncisoAuto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String tstamp
			,String cdusuari
			,String cdelemen
			,String cdtipsup
			,Date   fechaEfecto
			)throws Exception;
	
	public Map<String,Object> confirmarEndosoBajaIncisos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String tstamp
			,String cdusuari
			,String cdelemen
			,String cdtipsup
			,Date   fechaEfecto
			)throws Exception;

	public Map<String,Object> guardaEndosoDespago(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmrecibo
			,String nmimpres
			,String cdusuari
			,String cdtipsup
			)throws Exception;
	
	public List<Map<String,String>> obtenerRetroactividad(
			String cdsisrol
			,String cdramo
			,String cdtipsup
			,String fechaProceso
			)throws Exception;

	public List<Map<String,String>> obtieneRecibosPagados(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public Map<String,Object> guardarEndosoClaveAuto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,Date feefecto
			,String tstamp
			,String cdusuari
			,String cdelemen
			,String cdtipsup
			)throws Exception;
	
	public List<Map<String,String>> recuperarCoberturasEndosoDevolucionPrimas(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String tstamp
			)throws Exception;
	
	public int recuperarDiasDiferenciaEndosoValidos(String cdramo,String cdtipsup)throws Exception;
	
	public boolean revierteEndosoFallido(String cdunieco,String cdramo,String estado,String nmpoliza, String nsuplogi, String nmsuplem);

	public boolean revierteDomicilio(Map<String, String> params);
	
	public boolean revierteNombrePersona(Map<String, String> params);

	/**
	 * Para reasignar el parentesco cuando se da de baja el titular en el endoso de baja de asegurado
	 * @param params
	 * @return
	 */
	public void reasignaParentescoTitular(Map<String, String> params)throws Exception;
	
	/**
	 * 
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsituac
	 * @param cdclausu
	 * @param nmsuplem
	 * @param status
	 * @param cdtipcla
	 * @param swmodi
	 * @param dslinea
	 * @param accion
	 * @throws Exception
	 */
	public void guardarMpolicot(String cdunieco, String cdramo, String estado,
			String nmpoliza, String nmsituac, String cdclausu, String nmsuplem,
			String status, String cdtipcla, String swmodi, String dslinea,
			String accion) throws Exception;
	
	public Map<String,Object> confirmarEndosoRehabilitacion(
			String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String nsuplogi
			,String cddevcia
			,String cdgestor
			,Date   feemisio
			,Date   feinival
			,Date   fefinval
			,Date   feefecto
			,Date   feproren
			,String cdmonea
			,String nmsuplem
			,String cdelemen
			)throws Exception;
	
	public void insertaRecibosNvaVigencia(Map<String, String> params) throws Exception;
	
	public Map<String,Object> confirmarEndosoCancelacionAuto(
			String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String nsuplogi
			,String cddevcia
			,String cdgestor
			,Date   feemisio
			,Date   feinival
			,Date   fefinval
			,Date   feefecto
			,Date   feproren
			,String cdmonea
			,String nmsuplem
			,String cdelemen
			,Date   feinicio
			)throws Exception;
	
	public Map<String,Object> guardarEndosoDevolucionPrimas(
			String cdusuari
			,String cdsisrol
			,String cdelemen
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String tstamp
			,Date   feefecto
			)throws Exception;
	
	public void validaEndosoCambioVigencia(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public void modificarNmsuplemSatelites(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplemOriginal,
			Date feEfecto,
			Date feproren
			)throws Exception;
	
	public PropiedadesDeEndosoParaWS confirmarEndosoValositFormsAuto(
			String cdusuari
			,String cdsisrol
			,String cdelemen
			,String cdtipsup
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,Date feinival
			,String tstamp
			)throws Exception;
	
	public List<Map<String,String>> obtieneEndososPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception;

	public List<Map<String,String>> obtieneDatosEndososB(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception;
	
	public boolean esMismaPersonaContratante(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			);
	
	public String recuperarCdtipsitInciso1(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public Map<String,String> recuperarNmsuplemNsuplogiEndosoValidando(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			)throws Exception;
	
	public void sacaEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nsuplogi
			,String nmsuplem
			)throws Exception;
	
	public List<Map<String,String>> recuperarCorreoElectronicoSucursal(
			String codigo
			,String cdunieco
			)throws Exception;
	
	public void validaDuplicidadParentesco(String cdunieco, String cdramo, String estado, 
			String nmpoliza, String nmsuplem) throws Exception;
	
	public String obtieneNumeroAtributo(String cdtipsit, String nombreAtributo) throws Exception;
	
	public Map<String,Object> sigsvalipolEnd(
			String cdusuari
			,String cdelemen
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdtipsup
			)throws Exception;
	
	public String regeneraSuplemento(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nsuplogi
			,Date fecha
			)throws Exception;
	
	public List<Map<String,String>> obtieneRecibosDespagados(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public void guardarEndosoNombreRFCFecha(Map<String, Object> params)        throws Exception;
	
	public List<Map<String,String>> obtieneInformacionCliente(String cdunieco,String cdramo,String estado,String nmpoliza,String nmsuplem,String motivo)throws Exception;
	
	public List<Map<String,String>> CambioClientenombreRFCfechaNacimiento(Map<String, String> params)throws Exception;
	
	public void actualizaMpolisitNuevaVigencia(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem, String feefecto) throws Exception;

	public List<Map<String,String>> obtenerInfoFamiliaEndoso(Map<String, String> params)throws Exception;
	
	public String regeneraSuplementoFamiliaEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nsuplogi
			,Date fecha
			)throws Exception;
	
	public void  clonarGarantiaCapitales(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String cdgrupo
			,String cdplan
			,String sexo) throws Exception;
	
	public void actualizaExtraprimaValosit2(Map<String, String> params)     throws Exception;
	
	public List<Map<String,String>> recuperarCotizaciones(
			String cdunieco, 
			String cdramo, 
			String cdtipsit, 
			String estado, 
			String nmpoliza, 
			String ntramite, 
			String status, 
			String fecini, 
			String fecfin, 
			String cdsisrol, 
			String cdusuari,
			String cdagente) throws Exception;
	
	public void procesarCensoClonacion(
			String nombreProcedure,			
			String nombreCenso,
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String cduniecoOrig,
			String cdramoOrig,
			String estadoOrig,
			String nmpolizaOrig)throws Exception;

	public int recuperarNumeroGruposPoliza(String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza
			)throws Exception;
	
}