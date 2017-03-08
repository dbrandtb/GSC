package mx.com.gseguros.portal.general.dao;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.general.model.DetalleReciboVO;
import mx.com.gseguros.portal.general.model.ReciboVO;

public interface RecibosDAO {
	
	/**
	 * Obtiene los recibos de una p&oacute;liza
	 * @param params
	 * @return lista con los recibos asociados
	 * @throws Exception
	 */
	public List<ReciboVO> obtieneRecibos(Map<String, Object> params) throws Exception;
	
	
	/**
	 * Obtiene los detalles de un recibo
	 * @param params
	 * @return lista con el detalle del recibo
	 * @throws Exception
	 */
	public List<DetalleReciboVO> obtieneDetalleRecibo(Map<String, Object> params) throws Exception;

}
