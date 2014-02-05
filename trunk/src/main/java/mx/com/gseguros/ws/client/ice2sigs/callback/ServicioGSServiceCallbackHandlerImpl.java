package mx.com.gseguros.ws.client.ice2sigs.callback;

import java.util.HashMap;

import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.kernel.service.impl.KernelManagerSustitutoImpl;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.ws.client.Ice2sigsWebServices.Estatus;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceCallbackHandler;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSaludGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSaludRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ReciboGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ReciboRespuesta;

import org.apache.log4j.Logger;

public class ServicioGSServiceCallbackHandlerImpl extends ServicioGSServiceCallbackHandler {

	private Logger logger = Logger.getLogger(ServicioGSServiceCallbackHandlerImpl.class);
	
	KernelManagerSustituto kernelManager;

	public ServicioGSServiceCallbackHandlerImpl() {
		super();
	}

	public ServicioGSServiceCallbackHandlerImpl(Object obj) {
		super(obj);
	}
	
	public void setClientData(Object obj) {
		super.clientData = obj;
	}
	
	
	@Override
	public void receiveErrorclienteSaludGS(Exception e) {
		logger.error("Error en WS clienteSalud: " + e.getMessage() + " Guardando en bitacora el error, getCause: " + e.getCause(),e);

		///////////////////////////
		HashMap<String, Object> params = (HashMap<String, Object>) this.clientData;
		String usuario = (String) params.get("USUARIO");
		try {
			kernelManager.movBitacobro(
					(String) params.get("pv_cdunieco_i"),
					(String) params.get("pv_cdramo_i"),
					(String) params.get("pv_estado_i"),
					(String) params.get("pv_nmpoliza_i"), 
					"ErrWScliCx", 
					"Msg: " + e.getMessage() + " ***Cause: " + e.getCause(),
					 usuario);
		} catch (Exception e1) {
			logger.error("Error en llamado a PL", e1);
		}
		///////////////////////////
		
		//TODO: RBS cambiar el param por PolizaVO
		/*
		ServicioGSServiceCallbackHandlerVO gsServiceCallBackHandlerVO = (ServicioGSServiceCallbackHandlerVO) this.clientData;
		PolizaVO poliza = gsServiceCallBackHandlerVO.getPolizaVO();
		
		try {
			//Se ejecuta el servicio de datos para registrar el error ocurrido:
			kernelManager.movBitacobro(
					poliza.getCdUnieco(), 
					poliza.getCdRamo(), 
					poliza.getEstado(),
					poliza.getNmPoliza(), 
					"ErrWScliCx", 
					"Msg: " + e.getMessage() + " ***Cause: " + e.getCause(), 
					gsServiceCallBackHandlerVO.getUsuario());
		} catch (Exception e1) {
			logger.error("Error en llamado a PL", e1);
		}
		*/
	}

	@Override
	public void receiveResultclienteSaludGS(ClienteSaludGSResponseE result) {
		logger.debug("Comunicacion exitosa WS clienteSalud");
		ClienteSaludRespuesta respuesta = result.getClienteSaludGSResponse().get_return();
		logger.debug("Resultado al ejecutar el WS cliente: " + respuesta.getCodigo() + " - " + respuesta.getMensaje());

		if (Estatus.EXITO.getCodigo() != respuesta.getCodigo() && Estatus.LLAVE_DUPLICADA.getCodigo() != respuesta.getCodigo()) {
			logger.error("Guardando en bitacora el estatus");

			/////////////////////////////
			HashMap<String, Object> params = (HashMap<String, Object>) this.clientData;
			String usuario = (String) params.get("USUARIO");
			try {
				kernelManager.movBitacobro(
						(String) params.get("pv_cdunieco_i"),
						(String) params.get("pv_cdramo_i"),
						(String) params.get("pv_estado_i"),
						(String) params.get("pv_nmpoliza_i"), 
						"ErrWScli",
						respuesta.getCodigo() + " - " + respuesta.getMensaje(),
						usuario);
			} catch (Exception e1) {
				logger.error("Error en llamado a PL", e1);
			}
			/////////////////////////////
			
			//TODO: RBS cambiar el param por PolizaVO
			/*
			ServicioGSServiceCallbackHandlerVO gsServiceCallBackHandlerVO = (ServicioGSServiceCallbackHandlerVO) this.clientData;
			PolizaVO poliza = gsServiceCallBackHandlerVO.getPolizaVO();
			try {
				//Se ejecuta el servicio de datos para registrar el error ocurrido:
				kernelManager.movBitacobro(
						poliza.getCdUnieco(), 
						poliza.getCdRamo(), 
						poliza.getEstado(),
						poliza.getNmPoliza(), 
						"ErrWScli", 
						respuesta.getCodigo() + " - " + respuesta.getMensaje(), 
						gsServiceCallBackHandlerVO.getUsuario());
			} catch (Exception e1) {
				logger.error("Error en llamado a PL", e1);
			}
			*/
		}
	}
	

	@Override
	public void receiveErrorreciboGS(Exception e) {
		logger.error("Error en WS Recibo: " + e.getMessage() + " Guardando en bitacora el error, getCause: " + e.getCause(),e);

		HashMap<String, Object> params = (HashMap<String, Object>) this.clientData;

		KernelManagerSustitutoImpl manager = (KernelManagerSustitutoImpl) params.get("MANAGER");
		String usuario = (String) params.get("USUARIO");
		
		try {
			manager.movBitacobro(
					(String) params.get("pv_cdunieco_i"),
					(String) params.get("pv_cdramo_i"),
					(String) params.get("pv_estado_i"),
					(String) params.get("pv_nmpoliza_i"),
					"ErrWSrecCx",
					"Error en Recibo " + params.get("NumRec")
							+ " Msg: " + e.getMessage() + " ***Cause: "
							+ e.getCause(),
					 usuario);
		} catch (Exception e1) {
			logger.error("Error en llamado a PL", e1);
		}
	}

	@Override
	public void receiveResultreciboGS(ReciboGSResponseE result) {
		logger.debug("Comunicacion exitosa WS Recibo Salud");
		ReciboRespuesta respuesta = result.getReciboGSResponse().get_return();
		HashMap<String, Object> params = (HashMap<String, Object>) this.clientData;
		
		logger.debug("Resultado al ejecutar el WS Recibo: " + params.get("NumRec") + " >>>"
				+ respuesta.getCodigo() + " - " + respuesta.getMensaje());

		if (Estatus.EXITO.getCodigo() != respuesta.getCodigo()) {
			logger.error("Guardando en bitacora el estatus");

			KernelManagerSustitutoImpl manager = (KernelManagerSustitutoImpl) params.get("MANAGER");
			String usuario = (String) params.get("USUARIO");
			
			try {
				manager.movBitacobro((String) params.get("pv_cdunieco_i"),
						(String) params.get("pv_cdramo_i"),
						(String) params.get("pv_estado_i"),
						(String) params.get("pv_nmpoliza_i"), "ErrWSrec",
						"Error en Recibo " + params.get("NumRec")
								+ " >>> " + respuesta.getCodigo() + " - "
								+ respuesta.getMensaje(),
						 usuario);
			} catch (ApplicationException e1) {
				logger.error("Error en llamado a PL", e1);
			}
		}
	}

	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}

}
