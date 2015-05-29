package mx.com.gseguros.portal.consultas.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.general.util.ObjetoBD;

public interface ConsultasManager
{

	/**
	 * Obtiene datos ejecutando un objeto de BD
	 * @param objetoBD Objeto de BD a ejecutar
	 * @param params   Parametros que recibe el objeto de BD a ejecutar
	 * @return lista de elementos obtenidos de la consulta
	 * @throws Exception
	 */
	public List<Map<String,String>> consultaDinamica(ObjetoBD objetoBD, LinkedHashMap<String,Object>params) throws Exception;
	
	public void validarDatosObligatoriosPrevex(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception;
	
	public void validarDatosDXN(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem)throws Exception;
	
	@Deprecated
	public Map<String,String>cargarAtributosBaseCotizacion(String cdtipsit)throws Exception;
	
	@Deprecated
	public Map<String,String>cargarInformacionPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdusuari
			)throws Exception;
	
	@Deprecated
	public List<Map<String,String>>cargarMpolizasPorParametrosVariables(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsolici
			,String cdramant
			)throws Exception;

	public List<Map<String,String>> obtieneContratantePoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdrol
			,String cdperson
			)throws Exception;

	
	/**
	 * Indica si un producto es o no de Salud
	 * @param cdramo Ramo del producto a validar
	 * @return true si el producto es de salud, false si no
	 * @throws Exception
	 */
	boolean esProductoSalud(String cdramo) throws Exception;
	
}