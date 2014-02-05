package mx.com.gseguros.portal.emision.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.portal.emision.service.EmisionManager;
import mx.com.gseguros.ws.client.Ice2sigsWebServices;
import mx.com.gseguros.ws.client.Ice2sigsWebServices.Operacion;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSalud;

public class EmisionManagerImpl implements EmisionManager {
	
	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EmisionManagerImpl.class);

	private transient KernelManagerSustituto kernelManager;
	
	private transient Ice2sigsWebServices ice2sigsService;
	
	
	public boolean ejecutaWSclienteSalud(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem,
			Ice2sigsWebServices.Operacion op, UserVO userVO) {
		
		boolean exito = true;
		
		logger.debug("********************* Entrando a Ejecuta WSclienteSalud ******************************");
		
		WrapperResultados result = null;
		ClienteSalud cliente =  null;
		
		//Se invoca servicio para obtener los datos del cliente
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		try {
			result = kernelManager.obtenDatosClienteWS(params);
			if(result.getItemList() != null && result.getItemList().size() > 0){
				cliente = ((ArrayList<ClienteSalud>) result.getItemList()).get(0);
			}
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de ejecutaWSclienteSalud",e1);
			return false;
		}
		
		
		if(cliente != null){
			
			String usuario = "SIN USUARIO";
			if(userVO != null){
				usuario = userVO.getUser();
			}
			params.put("USUARIO", usuario);
			
			try{
				logger.debug("Ejecutando WS TEST para WS Cliente");
				ice2sigsService.ejecutaClienteSaludGS(Operacion.INSERTA, null, params, false);
			}catch(Exception e){
				logger.error("Error al ejecutar WS TEST para cliente: " + cliente.getClaveCli(), e);
			}
			try{
				logger.debug(">>>>>>> Enviando el Cliente: " + cliente.getClaveCli());
				ice2sigsService.ejecutaClienteSaludGS(op, cliente, params, true);
			}catch(Exception e){
				logger.error("Error al insertar el cliente: " + cliente.getClaveCli(), e);
				exito = false;
			}
		}

		return exito;
	}

	
	/**
	 * Setter method
	 * @param kernelManager
	 */
	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}

	/**
	 * Setter method
	 * @param ice2sigsService
	 */
	public void setIce2sigsService(Ice2sigsWebServices ice2sigsService) {
		this.ice2sigsService = ice2sigsService;
	}
	
	
}
