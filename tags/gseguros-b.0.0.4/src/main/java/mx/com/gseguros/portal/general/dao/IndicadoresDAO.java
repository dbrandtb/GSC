package mx.com.gseguros.portal.general.dao;

import java.util.List;
import java.util.Map;

public interface IndicadoresDAO {
	
	/**
	 * Obtiene los indicadores principales del dashboard de la Mesa de Control
	 * @param cdunieco
	 * @param lineanegocio
	 * @param cdramo
	 * @param tipotramite
	 * @param cdagente
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> obtieneDashInicial(String cdunieco, String lineanegocio, String cdramo,
			String tipotramite, String cdagente) throws Exception;
	
	
	/**
	 * Obtiene el detalle de los tramites pendientes
	 * @param cdunieco
	 * @param lineanegocio
	 * @param cdramo
	 * @param tipotramite
	 * @param cdagente
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> obtieneDashPendientes(String cdunieco, String lineanegocio, String cdramo,
			String tipotramite, String cdagente) throws Exception;


	/**
	 * Obtiene los tramites por linea de negocio
	 * @param cdetapa
	 * @param cdunieco
	 * @param lineaNegocio
	 * @param cdramo
	 * @param tipoTramite
	 * @param cdagente
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> obtieneTramitesPorLineaNegocio(String cdetapa, String cdunieco, String lineaNegocio,
			String cdramo, String tipoTramite, String cdagente) throws Exception;

	
	/**
	 * Obtiene los tramites por linea de negocio y ramo de acuerdo a los criterios enviados
	 * @param cdetapa
	 * @param cdunieco
	 * @param lineaNegocio
	 * @param cdramo
	 * @param tipoTramite
	 * @param cdagente
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> obtieneTramitesLineaNegocioPorRamo(String cdetapa, String cdunieco, String lineaNegocio,
			String cdramo, String tipoTramite, String cdagente) throws Exception;
	
	
	/**
	 * Obtiene el detalle de las lineas de negocio de acuerdo a los criterios enviados
	 * @param cdetapa
	 * @param cdunieco
	 * @param lineaNegocio
	 * @param cdramo
	 * @param tipoTramite
	 * @param cdagente
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> obtieneDetalleLineaNegocio(String cdetapa, String cdunieco, String lineaNegocio,
			String cdramo, String tipoTramite, String cdagente) throws Exception;
	
	
	/**
	 * Obtiene las lineas de negocio por sucursal de acuerdo a los criterios enviados
	 * @param cdetapa
	 * @param cdunieco
	 * @param lineaNegocio
	 * @param cdramo
	 * @param tipoTramite
	 * @param cdagente
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> obtieneLineaNegocioPorSucursal(String cdetapa, String cdunieco, String lineaNegocio,
			String cdramo, String tipoTramite, String cdagente) throws Exception;
	
	
	/**
	 * Obtiene las lineas de negocio por usuario de acuerdo a los criterios enviados
	 * @param cdetapa
	 * @param cdunieco
	 * @param lineaNegocio
	 * @param cdramo
	 * @param tipoTramite
	 * @param cdagente
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> obtieneLineaNegocioPorUsuario(String cdetapa, String cdunieco, String lineaNegocio,
			String cdramo, String tipoTramite, String cdagente) throws Exception;
	
	
	/**
	 * Obtiene los tramites por tipo de tramite de acuerdo a los criterios enviados
	 * @param cdetapa
	 * @param cdunieco
	 * @param lineaNegocio
	 * @param cdramo
	 * @param tipoTramite
	 * @param cdagente
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> obtieneTramitesPorTipo(String cdetapa, String cdunieco, String lineaNegocio,
			String cdramo, String tipoTramite, String cdagente) throws Exception;
	
	
	/**
	 * Obtiene los tramites pendientes por el numero de dias atrasados
	 * @param cdunieco
	 * @param lineaNegocio
	 * @param cdramo
	 * @param tipoTramite
	 * @param cdagente
	 * @param numDias Numero de dias atrasados
	 * @return tramites obtenidos
	 * @throws Exception
	 */
	public List<Map<String, String>> obtieneTramitesPendientesPorDia(String cdunieco,
			String lineaNegocio, String cdramo, String tipoTramite, String cdagente, String numDias) throws Exception;
	
}