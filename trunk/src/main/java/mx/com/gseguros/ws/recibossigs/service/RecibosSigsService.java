package mx.com.gseguros.ws.recibossigs.service;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.ws.recibossigs.client.axis2.GeneradorReciboDxnWsServiceStub.GeneradorRecibosDxnRespuesta;


public interface RecibosSigsService {

	public enum Estatus {

		EXITO(0), LLAVE_DUPLICADA(1);

		private int codigo;

		private Estatus(int codigo) {
			this.codigo = codigo;
		}

		public int getCodigo() {
			return codigo;
		}

	}
	
	/**
	 * Genera los recibos de descuento por nomina
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param sucursal
	 * @param nmsolici
	 * @param ntramite
	 * @return
	 */
	public boolean generaRecibosDxN(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem,
			String sucursal, String nmsolici, String ntramite, UserVO userVO);
	
	public boolean guardaCalendariosDxnFinaliza(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem, String sucursal, String nmsolici, String ntramite, GeneradorRecibosDxnRespuesta calendarios);

}
