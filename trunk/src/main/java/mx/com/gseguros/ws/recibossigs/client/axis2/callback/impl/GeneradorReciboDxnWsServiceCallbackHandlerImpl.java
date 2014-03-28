package mx.com.gseguros.ws.recibossigs.client.axis2.callback.impl;

import java.util.HashMap;

import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService.Estatus;
import mx.com.gseguros.ws.recibossigs.client.axis2.GeneradorReciboDxnWsServiceCallbackHandler;
import mx.com.gseguros.ws.recibossigs.client.axis2.GeneradorReciboDxnWsServiceStub.CalendarioEntidad;
import mx.com.gseguros.ws.recibossigs.client.axis2.GeneradorReciboDxnWsServiceStub.GeneraRecDxnResponseE;
import mx.com.gseguros.ws.recibossigs.client.axis2.GeneradorReciboDxnWsServiceStub.GeneradorRecibosDxnRespuesta;

public class GeneradorReciboDxnWsServiceCallbackHandlerImpl extends GeneradorReciboDxnWsServiceCallbackHandler {

	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GeneradorReciboDxnWsServiceCallbackHandlerImpl.class);
	
	private KernelManagerSustituto kernelManager;

	public GeneradorReciboDxnWsServiceCallbackHandlerImpl() {
		super();
	}

	public GeneradorReciboDxnWsServiceCallbackHandlerImpl(Object obj) {
		super(obj);
	}
	
	/**
	 * Agregamos el setter de clientData 
	 * @param obj
	 */
	public void setClientData(Object obj) {
		super.clientData = obj;
	}

	
	@Override
	public void receiveErrorgeneraRecDxn(Exception e) {
		
		logger.error("Error en WS clienteSalud: " + e.getMessage()
				+ " Guardando en bitacora el error, getCause: " + e.getCause(),e);

		HashMap<String, Object> params = (HashMap<String, Object>) this.clientData;

		String usuario = null;
		if(params.containsKey("USUARIO")) usuario = (String) params.get("USUARIO");
		
		try {
			kernelManager.movBitacobro((String) params.get("pv_cdunieco_i"),
					(String) params.get("pv_cdramo_i"),
					(String) params.get("pv_estado_i"),
					(String) params.get("pv_nmpoliza_i"), "ErrWsDXNCx", "Msg: "
							+ e.getMessage() + " ***Cause: " + e.getCause(),
					 usuario, null);
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
			String usuario = null;
			if(params.containsKey("USUARIO")) usuario = (String) params.get("USUARIO");
			
			try {
				kernelManager.movBitacobro((String) params.get("pv_cdunieco_i"),
						(String) params.get("pv_cdramo_i"),
						(String) params.get("pv_estado_i"),
						(String) params.get("pv_nmpoliza_i"), "ErrWsDXN",
						respuesta.getCodigo() + " - " + respuesta.getMensaje(),
						 usuario, null);
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

	
	/**
	 * kernelManager setter
	 * @param kernelManager
	 */
	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}

}