package mx.com.gseguros.ws.autosgs.tractocamiones.service.impl;

import mx.com.gseguros.exception.WSException;
import mx.com.gseguros.ws.autosgs.tractocamiones.client.axis2.PolizaTractocamionWSServiceStub;
import mx.com.gseguros.ws.autosgs.tractocamiones.client.axis2.PolizaTractocamionWSServiceStub.RequestValidacionPoliza;
import mx.com.gseguros.ws.autosgs.tractocamiones.client.axis2.PolizaTractocamionWSServiceStub.Response;
import mx.com.gseguros.ws.autosgs.tractocamiones.client.axis2.PolizaTractocamionWSServiceStub.ValidarPoliza;
import mx.com.gseguros.ws.autosgs.tractocamiones.client.axis2.PolizaTractocamionWSServiceStub.ValidarPolizaE;
import mx.com.gseguros.ws.autosgs.tractocamiones.client.axis2.PolizaTractocamionWSServiceStub.ValidarPolizaResponseE;
import mx.com.gseguros.ws.autosgs.tractocamiones.service.TractoCamionService;
import mx.com.gseguros.ws.model.WrapperResultadosWS;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang3.StringUtils;

/**
 * Implementaci�n de los m�todos para invocar al WS recibossigs
 * @author Ricardo
 *
 */
public class TractoCamionServiceImpl implements TractoCamionService {

	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TractoCamionServiceImpl.class);
	
	private static final long WS_TIMEOUT =  20000;
	
	private String endpoint;
	
	
	public boolean validarPolizaTractoCamion(String numeroPoliza, String rfcCliente){
		
		logger.debug(">>>>> Entrando a metodo WS validarPolizaTractoCamion");
		
		boolean polizaValida = false;
		
		if(StringUtils.isNotBlank(numeroPoliza) && StringUtils.isNotBlank(rfcCliente)){
			try{
				WrapperResultadosWS resultWS = this.ejecutavalidarPolizaTractoCamion(numeroPoliza, rfcCliente);
						
				Response resVal = (Response) resultWS.getResultadoWS();
				
				if( resVal != null && resVal.getExito()){
					logger.debug("Respuesta de WS ValidaTractoCamion Codigo(): " + resVal.getExito());
					logger.debug("Respuesta de WS ValidaTractoCamion Mensaje(): " +resVal.getMensaje());
					
					polizaValida = true;
					
				}else{
					logger.error("WS validarPolizaTractoCamion, Poliza NO Valida");
				}
				
			} catch(WSException wse){
				logger.error("Error en WS validarPolizaTractoCamion, xml enviado: " + wse.getPayload(), wse);
			} catch (Exception e){
				logger.error("Error en WS validarPolizaTractoCamion: " + e.getMessage(),e);
			}
		}else{
			logger.error("Error en WS validarPolizaTractoCamion datos nulos");
		}
		
		return polizaValida;
	}
	
	private WrapperResultadosWS ejecutavalidarPolizaTractoCamion(String numeroPoliza, String rfcCliente) throws Exception{
		
		WrapperResultadosWS resultWS = new WrapperResultadosWS();
		PolizaTractocamionWSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new PolizaTractocamionWSServiceStub(endpoint);
			stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: " + e.getMessage());
		}
		
		RequestValidacionPoliza requestValidacionPoliza = new RequestValidacionPoliza();
		requestValidacionPoliza.setNumeroPoliza(numeroPoliza);
		requestValidacionPoliza.setRfcCliente(rfcCliente);
		
		ValidarPoliza validarPoliza = new ValidarPoliza();
		validarPoliza.setArg0(requestValidacionPoliza);
		
		ValidarPolizaE validarPolizaE = new ValidarPolizaE();
		validarPolizaE.setValidarPoliza(validarPoliza);
		
		try {
			ValidarPolizaResponseE validResE = stubGS.validarPoliza(validarPolizaE);
			Response resVal = validResE.getValidarPolizaResponse().get_return();
			
			resultWS.setResultadoWS(resVal);
			resultWS.setXmlIn(stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
			
			logger.debug("Xml enviado para ejecutavalidarPolizaTractoCamion: " + stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
			
		} catch (Exception re) {
			throw new WSException("Error de conexion ejecutavalidarPolizaTractoCamion: " + re.getMessage(), re, stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
		}
		
		return resultWS;
	}
	
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

}