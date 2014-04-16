package mx.com.gseguros.ws.ice2sigs.service;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.general.model.RespuestaVO;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Agente;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AgenteRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AgenteSalud;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AgenteSaludRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AseguradoRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Ccomision;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.CcomisionRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Cliente;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAgenteRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAseguradoCoberturaRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAseguradoEndosoRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Poliza;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaMovimiento;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaMovimientoAgente;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaMovimientoAsegurado;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaMovimientoAseguradoCobertura;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaMovimientoAseguradoEndoso;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaRespuesta;

public interface Ice2sigsService {

	public enum Operacion {

		INSERTA(1), ACTUALIZA(2), CONSULTA(3);

		private int codigo;

		private Operacion(int codigo) {
			this.codigo = codigo;
		}

		public int getCodigo() {
			return codigo;
		}

	}

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
	 * Ejecuta el metodo expuesto de cliente del WS de iceToSigs
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param op
	 * @param userVO
	 * @return
	 */
	public boolean ejecutaWSclienteSalud(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem,
			Ice2sigsService.Operacion op, UserVO userVO);
	
	
	/**
	 * Ejecuta el metodo expuesto de recibos del WS iceToSigs
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param rutaPoliza
	 * @param cdtipsitGS
	 * @param sucursal
	 * @param nmsolici
	 * @param ntramite
	 * @param async
	 * @param operacion
	 * @param tipoMov
	 * @param userVO
	 * @return
	 */
	public boolean ejecutaWSrecibos(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem,
			String rutaPoliza, String cdtipsitGS, String sucursal,
			String nmsolici, String ntramite, boolean async,
			String tipoMov, UserVO userVO);

	/**
	 * Metodo que ejecuta el WS para mandar los datos de un siniestro a GS
	 * @param ntramite
	 * @param async
	 * @param userVO
	 * @return
	 */
	public RespuestaVO ejecutaWSreclamosTramite(String ntramite, Ice2sigsService.Operacion op, boolean async, UserVO userVO);
	
}
