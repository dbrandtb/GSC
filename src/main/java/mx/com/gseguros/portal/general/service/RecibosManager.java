package mx.com.gseguros.portal.general.service;

import java.util.List;

import mx.com.gseguros.portal.general.model.DetalleReciboVO;
import mx.com.gseguros.portal.general.model.ReciboVO;

public interface RecibosManager {

	/**
	 * 
	 * @param cdunieco
	 * @param cdramo
	 * @param nmpoliza
	 * @param nmsuplem
	 * @return
	 * @throws Exception
	 */
	public List<ReciboVO> obtieneRecibos(String cdunieco, String cdramo, String nmpoliza, String nmsuplem) throws Exception;

	
	/**
	 * Obtiene los detalles de un recibo
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmrecibo
	 * @return lista del detalle del recibo solicitado
	 * @throws Exception
	 */
	public List<DetalleReciboVO> obtieneDetallesRecibo(String cdunieco, String cdramo, String estado, String nmpoliza, String nmrecibo) throws Exception;
	
}
