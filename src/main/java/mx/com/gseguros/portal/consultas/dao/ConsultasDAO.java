package mx.com.gseguros.portal.consultas.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
}