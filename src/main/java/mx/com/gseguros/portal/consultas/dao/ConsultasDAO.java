package mx.com.gseguros.portal.consultas.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.ParametroGeneral;

public interface ConsultasDAO
{
	public List<Map<String,String>> consultaDinamica(String storedProcedure,LinkedHashMap<String,Object>params) throws Exception;
	
	public List<Map<String,String>>cargarMpolizasPorParametrosVariables(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsolici
			,String cdramant
			)throws Exception;
	
	public List<Map<String,String>>cargarTconvalsit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception;
	
	public List<Map<String,String>>cargarTbasvalsit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception;
	
	public Map<String,String>cargarMpoliperSituac(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac)throws Exception;
	
	public Map<String,String>cargarMpolisitSituac(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac)throws Exception;
	
	public List<Map<String,String>>cargarTvalosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem)throws Exception;

	public List<Map<String,String>>cargarMpoliage(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception;
	
	public void validarDatosCliente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception;

	public void validarDatosObligatoriosPrevex(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception;
	
	public void validarAtributosDXN(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem)throws Exception;
	
	public Map<String,String>cargarUltimoNmsuplemPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception;
	
	public List<Map<String,String>>cargarMpoliperOtrosRolesPorNmsituac(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String rolesPipes)throws Exception;
	
	public List<Map<String,String>>cargarTiposSituacionPorRamo(String cdramo)throws Exception;
	
	public boolean verificarCodigoPostalFronterizo(String cdpostal)throws Exception;
	
	public Map<String,String>cargarAtributosBaseCotizacion(String cdtipsit)throws Exception;
	
	public Map<String,String>cargarInformacionPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdusuari
			)throws Exception;
	
	public String recuperarPorcentajeRecargoPorProducto(String cdramo,String cdperpag)throws Exception;
	
	public List<Map<String,String>>recuperarValoresPantalla(
			String pantalla
			,String cdramo
			,String cdtipsit
			)throws Exception;
	
	public List<Map<String,String>>recuperarValoresAtributosFactores(String cdramo,String cdtipsit)throws Exception;
	
	public List<Map<String,String>>obtieneContratantePoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdrol
			,String cdperson
			)throws Exception;
	
	public List<Map<String,String>>recuperarPolizasEndosables(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmpoliex
			,String ramo
			,String cdagente
			,String statusVig
			,String finicio //Se agrega campo fecha de inicio param No. 9
			,String ffin //Se agrega campo fecha de fin param No. 10
			)throws Exception;
	
	public List<Map<String,String>>recuperarHistoricoPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public List<Map<String,String>>recuperarIncisosPolizaGrupoFamilia(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo
			,String nmfamili
			,String nivel
			)throws Exception;
	
	public String recuperarValorAtributoUnico(
			String cdtipsit
			,String cdatribu
			,String otclave
			)throws Exception;
	
	public List<Map<String,String>>recuperarGruposPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public List<Map<String,String>>recuperarFamiliasPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	/**
	 * Indica si un producto es o no de Salud
	 * @param cdramo Ramo del producto a validar
	 * @return true si el producto es de salud, false si no
	 * @throws Exception
	 */
	public boolean esProductoSalud(String cdramo) throws Exception;
	
	public List<String> recuperarDescripcionAtributosSituacionPorRamo(String cdramo) throws Exception;
	
	public Map<String,String> recuperarFechasLimiteEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdsisrol
			,String cdusuari
			,String cdtipsup
			)throws Exception;
	
	public List<Map<String,String>> recuperarEndososRehabilitables(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public List<Map<String,String>> recuperarEndososCancelables(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public boolean recuperarPermisoDevolucionPrimasUsuario(String cdusuari) throws Exception;
	
	public String recuperarValorMaximoSituacionPorRol(String cdtipsit,String cdsisrol) throws Exception;

	public String obtieneSubramoGS(String cdramo,String cdtipsit) throws Exception;
	
	public String recuperarCdtipsitExtraExcel(
			int fila
			,String proc
			,String param1
			,String param2
			,String param3
			)throws Exception;
	
	public Map<String,String>recuperarCotizacionFlotillas(String cdramo,String nmpoliza,String cdusuari,String cdsisrol) throws Exception;
	
	public Map<String,List<Map<String,String>>> recuperarEstadisticasCotizacionEmision(
			Date feinicio
			,Date fefin
			,String cdunieco
			,String cdramo
			,String cdusuari
			,String cdagente
			) throws Exception;
	
	public Map<String,List<Map<String,String>>> recuperarEstadisticasTareas(
			Date feinicio
			,Date fefin
			,String cdmodulo
			,String cdtarea
			,String cdunieco
			,String cdramo
			,String cdusuari
			,String cdsisrol
			) throws Exception;
	
	public String obtieneConteoSituacionCoberturaAmparada(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdtipsit
			,String cdatribu
			)throws Exception;
	
	public String validacionesSuplemento(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdtipsup
			)throws Exception;
	
	public List<Map<String,String>> recuperarRevisionColectivos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;

	public boolean copiaDocumentosTdocupol(
			 String cduniecoOrig
			,String cdramoOrig
			,String estadoOrig
			,String nmpolizaOrig
			,String ntramiteDestino
			)throws Exception;
	
	public String recuperarDerechosPolizaPorPaqueteRamo1(String paquete) throws Exception;
	
	public boolean validaPagoPolizaRepartido(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public List<Map<String,String>> recuperarAtributosPorRol(String cdtipsit,String cdsisrol) throws Exception;
	
	public boolean validaClientePideNumeroEmpleado(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public List<Map<String,String>>recuperarUsuariosReasignacionTramite(String ntramite) throws Exception;
	
	public boolean validarVentanaDocumentosBloqueada(
			String ntramite
			,String cdtiptra
			,String cdusuari
			,String cdsisrol
			)throws Exception;
	
	public List<Map<String,String>> recuperarMovimientosEndosoAltaBajaAsegurados(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception;
	
	public String recuperarConteoTbloqueo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;

	public Map<String,String> consultaFeNacContratanteAuto(Map<String,String> params)throws Exception;
	
	public List<Map<String,String>> recuperarSubramos(String cdramo) throws Exception;
	
	public String recuperarTparagen(ParametroGeneral paragen) throws Exception;
	
	public List<Map<String,String>> recuperarTiposRamo() throws Exception;
	
	public List<Map<String,String>> recuperarRamosPorTipoRamo(String cdtipram) throws Exception;
	
	public List<Map<String,String>> recuperarSucursalesPorTipoRamo(String cdtipram) throws Exception;
	
	public List<Map<String,String>> recuperarPolizasParaImprimir(
			String cdtipram
			,String cduniecos
			,String cdramo
			,String ramo
			,String nmpoliza
			,Date fecha
			,String cdusuariLike
			,String cdagente
			,String cdusuariSesion
			,String cduniecoSesion
			)throws Exception;
	
	public String recuperarUltimoNmsuplem(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public String recuperarSecuenciaLote() throws Exception;
	
	public String recuperarImpresionesDisponiblesPorTipoRamo(
			String cdtipram
			,String tipolote
			) throws Exception;
	
	public Map<String,String>recuperarDetalleImpresionLote(String lote) throws Exception;
	
	public List<Map<String,String>> recuperarImpresorasPorPapelYSucursal(
			String cdunieco
			,String papel
			,String activo
			)throws Exception;
	
	public List<Map<String,String>> recuperarComboUsuarios(String cadena) throws Exception;
	
	public List<Map<String,String>> recuperarConfigImpresionSucursales(String cdusuari, String cdunieco, String cdtipram) throws Exception;
	
	public List<Map<String,String>> recuperarConfigImpresionAgentes(String cdusuari, String cdunieco, String cdtipram) throws Exception;
	
	public void movPermisoImpresionSucursal(
			String cdusuari
			,String cdunieco
			,String cdtipram
			,String cduniecoPer
			,String swaplica
			,String accion
			)throws Exception;
	
	public void movPermisoImpresionAgente(
			String cdusuari
			,String cdunieco
			,String cdtipram
			,String cduniecoPer
			,String swaplica
			,String accion
			)throws Exception;
	
	public List<Map<String,String>> recuperarRecibosLote(
			String cdtipram
			,String cduniecos
			,Date feinicio
			,Date fefin
			,String cdusuari
			,String cdunieco
			)throws Exception;
	
	public List<Map<String,String>> recuperarDetalleRemesa(String ntramite, String tipolote) throws Exception;
	
	public List<Map<String,String>> recuperarArchivosParaImprimirLote(
			String lote
			,String papel
			,String tipolote
			)throws Exception;
	
	public Map<String,String> recuperarDatosPolizaParaDocumentos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public String recuperarTipoRamoPorCdramo(String cdramo) throws Exception;
	
	public String recuperarTramitePorNmsuplem(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception;
	
	public Map<String,String> recuperarRemesaEmisionEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String ntramite
			)throws Exception;
	
	public String recuperarDstipsupPorCdtipsup(String cdtipsup) throws Exception;
	
	public List<Map<String,String>> recuperarSucursalesPermisoImpresion(
			String cdtipram
			,String cdusuari
			,String cdunieco
			)throws Exception;
	
	public List<Map<String,String>> recuperarConfigImpresionUsuarios(String cdusuari, String cdunieco, String cdtipram) throws Exception;
	
	public void movPermisoImpresionUsuario(
			String cdusuari
			,String cdunieco
			,String cdtipram
			,String cdusuariPer
			,String swaplica
			,String accion
			)throws Exception;
	
	public List<Map<String,String>> recuperarRolesTodos() throws Exception;

	public List<Map<String,String>> obtieneBeneficiariosPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
	)throws Exception;
	
	public Map<String,String> recuperarDatosFlujoEmision(String cdramo, String tipoflot) throws Exception;

	public String recuperarDiasFechaFacturacion(String cdtipsit, String cdsisrol) throws Exception;
	
	public void guardarDatosDemo(String ntramite, Map<String,String> params) throws Exception;
	
	public Map<String,String> cargarDatosDemo(String ntramite) throws Exception;
	
	public String recuperarPermisoBotonEnviarCenso(String cdsisrol) throws Exception;
	
	public int recuperarConteoTbloqueoTramite(String ntramite)throws Exception;

	public List<Map<String,String>> recuperarExclusionTurnados() throws Exception;
	
	public List<Map<String,String>> cargarCotizadoresActivos(String cadena) throws Exception;
	
	public String recuperarCodigoCustom(String cdpantalla, String cdsisrol) throws Exception;
	
	public String recuperarPermisoBotonEmitir(String cdsisrol, String cdusuari, String cdtipsit) throws Exception;
	
	public List<Map<String,String>> recuperarClavesPlanRamo4(String cdramo, String cdusuari, String cdtipsit) throws Exception;
}