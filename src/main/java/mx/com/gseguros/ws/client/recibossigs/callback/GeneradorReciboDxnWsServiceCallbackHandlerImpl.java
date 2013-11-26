package mx.com.gseguros.ws.client.recibossigs.callback;

import java.util.HashMap;

import org.jfree.util.Log;

import mx.com.aon.kernel.service.impl.KernelManagerSustitutoImpl;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.ws.client.Ice2sigsWebServices.Estatus;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceCallbackHandler;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSaludGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSaludRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ReciboGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ReciboRespuesta;
import mx.com.gseguros.ws.client.recibossigs.GeneradorReciboDxnWsServiceCallbackHandler;
import mx.com.gseguros.ws.client.recibossigs.GeneradorReciboDxnWsServiceStub.CalendarioEntidad;
import mx.com.gseguros.ws.client.recibossigs.GeneradorReciboDxnWsServiceStub.GeneraRecDxnResponseE;
import mx.com.gseguros.ws.client.recibossigs.GeneradorReciboDxnWsServiceStub.GeneradorRecibosDxnRespuesta;

public class GeneradorReciboDxnWsServiceCallbackHandlerImpl extends
GeneradorReciboDxnWsServiceCallbackHandler {

	private org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(GeneradorReciboDxnWsServiceCallbackHandlerImpl.class);

	public GeneradorReciboDxnWsServiceCallbackHandlerImpl() {
		super();
	}

	public GeneradorReciboDxnWsServiceCallbackHandlerImpl(Object obj) {
		super(obj);
	}

	@Override
	public void receiveErrorgeneraRecDxn(Exception e) {
		logger.error("Error en WS clienteSalud: " + e.getMessage()
				+ " Guardando en bitacora el error, getCause: " + e.getCause(),e);

		HashMap<String, Object> params = (HashMap<String, Object>) this.clientData;

		KernelManagerSustitutoImpl manager = (KernelManagerSustitutoImpl) params
				.get("MANAGER");
		String usuario = null;
		if(params.containsKey("USUARIO")) usuario = (String) params.get("USUARIO");
		
		try {
			manager.movBitacobro((String) params.get("pv_cdunieco_i"),
					(String) params.get("pv_cdramo_i"),
					(String) params.get("pv_estado_i"),
					(String) params.get("pv_nmpoliza_i"), "ErrWsDXNCx", "Msg: "
							+ e.getMessage() + " ***Cause: " + e.getCause(),
					 usuario);
		} catch (Exception e1) {
			logger.error("Error en llamado a PL", e1);
		}
	}

	@Override
	public void receiveResultgeneraRecDxn(GeneraRecDxnResponseE result) {
		logger.debug("Comunicacion exitosa WS generaRecDxn");
		GeneradorRecibosDxnRespuesta respuesta = result.getGeneraRecDxnResponse().get_return();
		logger.debug("Resultado al ejecutar el WS generaRecDxn: " + respuesta.getCodigo() + " - " + respuesta.getMensaje());

		if (Estatus.EXITO.getCodigo() != respuesta.getCodigo()) {
			logger.error("Guardando en bitacora el estatus");

			HashMap<String, Object> params = (HashMap<String, Object>) this.clientData;
			KernelManagerSustitutoImpl manager = (KernelManagerSustitutoImpl) params.get("MANAGER");
			String usuario = null;
			if(params.containsKey("USUARIO")) usuario = (String) params.get("USUARIO");
			
			try {
				manager.movBitacobro((String) params.get("pv_cdunieco_i"),
						(String) params.get("pv_cdramo_i"),
						(String) params.get("pv_estado_i"),
						(String) params.get("pv_nmpoliza_i"), "ErrWsDXN",
						respuesta.getCodigo() + " - " + respuesta.getMensaje(),
						 usuario);
			} catch (Exception e1) {
				logger.error("Error en llamado a PL", e1);
			}
		}else {
			
			for(CalendarioEntidad cal : respuesta.getCalendariosEntidad()){
				logger.debug(">>>Calendario: "+cal.getPeriodo());	
				logger.debug("Fecha inicio: "+cal.getFechaIncio());	
			}
			
			
		}

	}

}
