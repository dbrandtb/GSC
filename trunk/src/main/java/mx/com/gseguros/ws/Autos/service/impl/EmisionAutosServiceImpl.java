package mx.com.gseguros.ws.Autos.service.impl;

import java.util.HashMap;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.WSException;
import mx.com.gseguros.externo.service.StoredProceduresManager;
import mx.com.gseguros.ws.Autos.client.axis2.CotizacionIndividualWSServiceStub;
import mx.com.gseguros.ws.Autos.client.axis2.CotizacionIndividualWSServiceStub.EmisionPolizaRequest;
import mx.com.gseguros.ws.Autos.client.axis2.CotizacionIndividualWSServiceStub.Inciso;
import mx.com.gseguros.ws.Autos.client.axis2.CotizacionIndividualWSServiceStub.Response;
import mx.com.gseguros.ws.Autos.client.axis2.CotizacionIndividualWSServiceStub.WsEmitirCotizacion;
import mx.com.gseguros.ws.Autos.client.axis2.CotizacionIndividualWSServiceStub.WsEmitirCotizacionE;
import mx.com.gseguros.ws.Autos.client.axis2.CotizacionIndividualWSServiceStub.WsEmitirCotizacionResponseE;
import mx.com.gseguros.ws.Autos.client.axis2.WsEmitirPolizaStub;
import mx.com.gseguros.ws.Autos.client.axis2.WsEmitirPolizaStub.SDTPoliza;
import mx.com.gseguros.ws.Autos.client.axis2.WsEmitirPolizaStub.WsEmitirPolizaEMITIRPOLIZA;
import mx.com.gseguros.ws.Autos.client.axis2.WsEmitirPolizaStub.WsEmitirPolizaEMITIRPOLIZAResponse;
import mx.com.gseguros.ws.Autos.service.EmisionAutosService;
import mx.com.gseguros.ws.model.WrapperResultadosWS;

import org.apache.axis2.AxisFault;

/**
 * Implementaci�n de los m�todos para invocar al WS recibossigs
 * @author Ricardo
 *
 */
public class EmisionAutosServiceImpl implements EmisionAutosService {

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EmisionAutosServiceImpl.class);
	
	private static final long WS_TIMEOUT =  20000;
	
	private String endpoint;
	private StoredProceduresManager storedProceduresManager;
	
	public SDTPoliza cotizaEmiteAutomovilWS(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String ntramite, UserVO userVO){
		
		logger.debug(">>>>> Entrando a metodo WS Cotiza y Emite para Auto");
		
		SDTPoliza polizaEmiRes = null;
		Inciso inciso = null;
		
		//Se invoca servicio para obtener los datos del auto
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		params.put("pv_ntramite_i", ntramite);
				
//		try {
//			result = kernelManager.obtenDatosAutoWS(params);
//			if(result.getItemList() != null && result.getItemList().size() > 0){
//				inciso = ((ArrayList<Inciso>) result.getItemList()).get(0);
//			}
//		} catch (Exception e1) {
//			logger.error("Error en llamar al PL de obtencion de ejecutaWSclienteGeneral",e1);
//			return null;
//		}	
		inciso =  new Inciso();
		
		if(inciso != null){
			try{
				Inciso[] incisos = new Inciso[1];
				incisos[0] = inciso;
				
				EmisionPolizaRequest datosCotizacionAuto =  new EmisionPolizaRequest();
//				datosCotizacionAuto.setCodigo(codigo);
//				datosCotizacionAuto.setFechaEmision(fechaEmision);
//				datosCotizacionAuto.setIdCliente(idCliente);
//				datosCotizacionAuto.setIdCotizacion(idCotizacion);
				datosCotizacionAuto.setIncisos(incisos);
				

				WrapperResultadosWS resultWSCot = this.ejecutaCotizacionAutosWS(datosCotizacionAuto);
						
				Response cotRes = (Response) resultWSCot.getResultadoWS();
				
				if(cotRes != null && cotRes.getExito()){
					logger.debug("REspuesta de WS Cotizacion cotRes.getCodigo()" +cotRes.getCodigo());
					logger.debug("REspuesta de WS Cotizacion cotRes.getMensaje()" +cotRes.getMensaje());
					logger.debug("REspuesta de WS Cotizacion cotRes.toString()" +cotRes.toString());

					//logger.debug("REspuesta de WS Cotizacion cotRes.toString()" +cotRes.getOMElement(new QName(localPart), null));
					
					long numSolicitud = cotRes.getCodigo();
					WrapperResultadosWS resultWSEmi = this.ejecutaEmisionAutosWS(numSolicitud);
					
					polizaEmiRes = (SDTPoliza)resultWSEmi.getResultadoWS();
					logger.debug("Numero de Poliza de Emision generada por el WS: " + polizaEmiRes.getNumpol());
					
				}else{
					logger.error("Error en la cotizacion de Autos WS, respuesta no exitosa");
				}
				
			} catch(WSException wse){
				logger.error("Error en WS de Autos, xml enviado: " + wse.getPayload(), wse);
			} catch (Exception e){
				logger.error("Error en WS de Autos: " + e.getMessage(),e);
			}
		}else{
			logger.error("Error, No se tienen datos del Auto");
		}
		
		return polizaEmiRes;
	}
	
	private WrapperResultadosWS ejecutaCotizacionAutosWS(EmisionPolizaRequest datosCotizacionAuto) throws Exception{
		
		WrapperResultadosWS resultWS = new WrapperResultadosWS();
		CotizacionIndividualWSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new CotizacionIndividualWSServiceStub(endpoint);
			stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: " + e.getMessage());
		}
		
		WsEmitirCotizacion wsEmitirCotizacion =  new WsEmitirCotizacion();
		wsEmitirCotizacion.setArg0(datosCotizacionAuto);
		
		WsEmitirCotizacionE wsEmitirCotizacionE = new WsEmitirCotizacionE();
		wsEmitirCotizacionE.setWsEmitirCotizacion(wsEmitirCotizacion);
		
		WsEmitirCotizacionResponseE wsEmitirCotizacionResponseE = null;
		Response response = null;
		
		try {
			wsEmitirCotizacionResponseE = stubGS.wsEmitirCotizacion(wsEmitirCotizacionE);
			response = wsEmitirCotizacionResponseE.getWsEmitirCotizacionResponse().get_return();
			resultWS.setResultadoWS(response);
			resultWS.setXmlIn(stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
			
			logger.debug("Xml enviado para obtener Cotizacion de auto: " + stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
			
		} catch (Exception re) {
			throw new WSException("Error de conexion: " + re.getMessage(), re, stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
		}
		
		return resultWS;
	}
	
	private WrapperResultadosWS ejecutaEmisionAutosWS(long numSolicitud) throws Exception{
		
		WrapperResultadosWS resultWS = new WrapperResultadosWS();
		WsEmitirPolizaStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new WsEmitirPolizaStub(endpoint);
			stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: " + e.getMessage());
		}
		
		
		WsEmitirPolizaEMITIRPOLIZA wsEmitirPolizaEMITIRPOLIZA = new WsEmitirPolizaEMITIRPOLIZA();
		wsEmitirPolizaEMITIRPOLIZA.setVnumsolicitud(numSolicitud);
		
		WsEmitirPolizaEMITIRPOLIZAResponse wsEmitirResponse = null;
		SDTPoliza response = null;
		
		try {
			wsEmitirResponse = stubGS.eMITIRPOLIZA(wsEmitirPolizaEMITIRPOLIZA);
			response = wsEmitirResponse.getSdtpoliza();
			resultWS.setResultadoWS(response);
			resultWS.setXmlIn(stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
			
			logger.debug("Xml enviado para emitir poliza de auto: " + stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
			
		} catch (Exception re) {
			throw new WSException("Error de conexion: " + re.getMessage(), re, stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
		}
		
		return resultWS;
	}
	
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public void setStoredProceduresManager(
			StoredProceduresManager storedProceduresManager) {
		this.storedProceduresManager = storedProceduresManager;
	}

}