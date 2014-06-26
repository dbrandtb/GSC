package mx.com.gseguros.ws.ice2sigs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.WSException;
import mx.com.gseguros.portal.general.model.RespuestaVO;
import mx.com.gseguros.portal.general.util.TipoSituacion;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGeneral;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGeneralGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGeneralGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGeneralGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGeneralRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteSalud;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteSaludGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteSaludGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteSaludGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteSaludRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Recibo;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReciboGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReciboGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReciboGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReciboRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Reclamo;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReclamoGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReclamoGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReclamoGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReclamoRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.callback.impl.ServicioGSServiceCallbackHandlerImpl;
import mx.com.gseguros.ws.ice2sigs.client.model.ReciboWrapper;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;
import mx.com.gseguros.ws.model.WrapperResultadosWS;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Ice2sigsServiceImpl implements Ice2sigsService {
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(Ice2sigsServiceImpl.class);

	private static final long WS_TIMEOUT =  20000;
	
	private String endpoint;
	
	private String urlImpresionRecibos;
	
	private transient KernelManagerSustituto kernelManager;
	private SiniestrosManager siniestrosManager;
	

	private WrapperResultadosWS ejecutaClienteSaludGS(Operacion operacion,
			ClienteSalud cliente, HashMap<String, Object> params, boolean async) throws Exception {
		
		ClienteSaludRespuesta resultado = null;
		WrapperResultadosWS resWS = new WrapperResultadosWS();
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		ClienteSaludGSResponseE RespuestaGS = null;
		
		ClienteSaludGS clienteS = new ClienteSaludGS();
		clienteS.setArg0(operacion.getCodigo());
		clienteS.setArg1(cliente);
		
		ClienteSaludGSE clienteE = new ClienteSaludGSE();
		clienteE.setClienteSaludGS(clienteS);
		
		try {
			if(async){
				//TODO: RBS Cambiar params por PolizaVO
				//Se genera una nueva instancia en cada llamado, para evitar corrupcion de datos en el handler:
				WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
				ServicioGSServiceCallbackHandlerImpl callback = (ServicioGSServiceCallbackHandlerImpl)context.getBean("servicioGSServiceCallbackHandlerImpl");
				// Se setean los parametros al callback handler:
				params.put("STUB", stubGS);
				callback.setClientData(params);
				
				stubGS.startclienteSaludGS(clienteE, callback);
			} else {
				RespuestaGS = stubGS.clienteSaludGS(clienteE);
				resultado = RespuestaGS.getClienteSaludGSResponse().get_return();
				resWS.setResultadoWS(resultado);
				resWS.setXmlIn(stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
				logger.debug("Resultado ejecucion de WS clienteSaludGS: "+resultado.getCodigo()+" - "+resultado.getMensaje());
			}
		} catch (Exception re) {
			throw new WSException("Error de conexion: " + re.getMessage(), re, stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
		}
		
		return resWS;
	}

	private WrapperResultadosWS ejecutaClienteGeneralGS(Operacion operacion,
			ClienteGeneral cliente, HashMap<String, Object> params, boolean async) throws Exception {
		
		ClienteGeneralRespuesta resultado = null;
		WrapperResultadosWS resWS = new WrapperResultadosWS();
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		ClienteGeneralGSResponseE RespuestaGS = null;
		
		ClienteGeneralGS clienteS = new ClienteGeneralGS();
		clienteS.setArg0(operacion.getCodigo());
		clienteS.setArg1(cliente);
		
		ClienteGeneralGSE clienteE = new ClienteGeneralGSE();
		clienteE.setClienteGeneralGS(clienteS);
		
		try {
			if(async){
				//TODO: RBS Cambiar params por PolizaVO
				//Se genera una nueva instancia en cada llamado, para evitar corrupcion de datos en el handler:
				WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
				ServicioGSServiceCallbackHandlerImpl callback = (ServicioGSServiceCallbackHandlerImpl)context.getBean("servicioGSServiceCallbackHandlerImpl");
				// Se setean los parametros al callback handler:
				params.put("STUB", stubGS);
				callback.setClientData(params);
				
				stubGS.startclienteGeneralGS(clienteE, callback);
			} else {
				RespuestaGS = stubGS.clienteGeneralGS(clienteE);
				resultado = RespuestaGS.getClienteGeneralGSResponse().get_return();
				resWS.setResultadoWS(resultado);
				resWS.setXmlIn(stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
				logger.debug("Resultado ejecucion de WS clienteGeneralGS: "+resultado.getCodigo()+" - "+resultado.getMensaje());
			}
		} catch (Exception re) {
			throw new WSException("Error de conexion: " + re.getMessage(), re, stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
		}
		
		return resWS;
	}
	
	private WrapperResultadosWS ejecutaReciboGS(Operacion operacion,
			Recibo recibo, HashMap<String, Object> params, boolean async) throws Exception {
		
		ReciboRespuesta resultado = null;
		WrapperResultadosWS resWS = new WrapperResultadosWS();
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		ReciboGSResponseE RespuestaGS = null;
		
		ReciboGS reciboS = new ReciboGS();
		reciboS.setArg0(operacion.getCodigo());
		reciboS.setArg1(recibo);
		
		ReciboGSE reciboE = new ReciboGSE();
		reciboE.setReciboGS(reciboS);
		
		try {
			if(async){
				//TODO: RBS Cambiar params por PolizaVO
				//Se genera una nueva instancia en cada llamado, para evitar corrupcion de datos en el handler:
				WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
				ServicioGSServiceCallbackHandlerImpl callback = (ServicioGSServiceCallbackHandlerImpl)context.getBean("servicioGSServiceCallbackHandlerImpl");
				// Se setean los parametros al callback handler:
				params.put("STUB", stubGS);
				callback.setClientData(params);
				
				stubGS.startreciboGS(reciboE, callback);
			} else {
				RespuestaGS = stubGS.reciboGS(reciboE);
				resultado = RespuestaGS.getReciboGSResponse().get_return();
				resWS.setResultadoWS(resultado);
				resWS.setXmlIn(stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
				logger.debug("Resultado ejecucion de WS reciboGS: "+resultado.getCodigo()+" - "+resultado.getMensaje());
			}
		} catch (Exception re) {
			throw new WSException("Error de conexion: " + re.getMessage(), re, stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
		}
		
		return resWS;
	}

	
	private WrapperResultadosWS ejecutaReclamoGS(Operacion operacion,
			Reclamo reclamo, HashMap<String, Object> params, boolean async) throws Exception {
		
		ReclamoRespuesta resultado = null;
		WrapperResultadosWS resWS = new WrapperResultadosWS();
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		ReclamoGSResponseE RespuestaGS = null;
		
		ReclamoGS reclamoS = new ReclamoGS();
		reclamoS.setArg0(operacion.getCodigo());
		reclamoS.setArg1(reclamo);
		
		ReclamoGSE reclamoE = new ReclamoGSE();
		reclamoE.setReclamoGS(reclamoS);
		
		try {
			if(async){
				//TODO: RBS Cambiar params por PolizaVO
				//Se genera una nueva instancia en cada llamado, para evitar corrupcion de datos en el handler:
				WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
				ServicioGSServiceCallbackHandlerImpl callback = (ServicioGSServiceCallbackHandlerImpl)context.getBean("servicioGSServiceCallbackHandlerImpl");
				// Se setean los parametros al callback handler:
				params.put("STUB", stubGS);
				callback.setClientData(params);
				
				stubGS.startreclamoGS(reclamoE, callback);
			} else {
				RespuestaGS = stubGS.reclamoGS(reclamoE);
				resultado = RespuestaGS.getReclamoGSResponse().get_return();
				resWS.setResultadoWS(resultado);
				resWS.setXmlIn(stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
				logger.debug("Resultado ejecucion de WS ejecutaReclamoGS: "+resultado.getCodigo()+" - "+resultado.getMensaje());
			}
		} catch (Exception re) {
			throw new WSException("Error de conexion: " + re.getMessage(), re, stubGS._getServiceClient().getLastOperationContext().getMessageContext("Out").getEnvelope().toString());
		}
		
		return resWS;
	}
	
	
	public boolean ejecutaWSclienteSalud(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String ntramite,
			Ice2sigsService.Operacion op, UserVO userVO) {
		
		logger.debug("********************* Entrando a Ejecuta WSclienteSalud ******************************");
		
		boolean exito = true;
		
		WrapperResultados result = null;
		ClienteSalud cliente =  null;
		
		//Se invoca servicio para obtener los datos del cliente
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		params.put("pv_ntramite_i", ntramite);
		
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
				ejecutaClienteSaludGS(Operacion.INSERTA, null, params, false);
			}catch(Exception e){
				logger.error("Error al ejecutar WS TEST para cliente: " + cliente.getClaveCli(), e);
			}
			try{
				logger.debug(">>>>>>> Enviando el Cliente: " + cliente.getClaveCli());
				ejecutaClienteSaludGS(op, cliente, params, true);
			}catch(Exception e){
				logger.error("Error al insertar el cliente: " + cliente.getClaveCli(), e);
				exito = false;
			}
		}

		return exito;
	}

	public ClienteGeneralRespuesta ejecutaWSclienteGeneral(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String ntramite,
			Ice2sigsService.Operacion op, ClienteGeneral cliente, UserVO userVO, boolean async) {
		
		logger.debug("********************* Entrando a Ejecuta WSclienteGeneral ******************************");
		logger.debug("********************* Operacion a Realizar: " + op);
		
		ClienteGeneralRespuesta clientesRespuesta = null;
		WrapperResultadosWS resultWS = null;
		WrapperResultados result = null;
		
		//Se invoca servicio para obtener los datos del cliente
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		params.put("pv_ntramite_i", ntramite);
		
		
		if(Ice2sigsService.Operacion.CONSULTA_GENERAL.getCodigo() != op.getCodigo()){
			try {
				result = kernelManager.obtenDatosClienteGeneralWS(params);
				if(result.getItemList() != null && result.getItemList().size() > 0){
					cliente = ((ArrayList<ClienteGeneral>) result.getItemList()).get(0);
				}
			} catch (Exception e1) {
				logger.error("Error en llamar al PL de obtencion de ejecutaWSclienteGeneral",e1);
				return null;
			}	
		}
		
		
		if(cliente != null){
			
			String usuario = "SIN USUARIO";
			if(userVO != null){
				usuario = userVO.getUser();
			}
			
			logger.debug(">>>>>>> Ejecutando WS Cliente General Clave Externa: " + cliente.getNumeroExterno());
			logger.debug(">>>>>>> Ejecutando WS Cliente General RFC: " + cliente.getRfcCli());
			try{
				
				if(async){
					params.put("USUARIO", usuario);
					ejecutaClienteGeneralGS(op, cliente, params, async);
				}else{
					resultWS = ejecutaClienteGeneralGS(op, cliente, null, async);
					clientesRespuesta = (ClienteGeneralRespuesta) resultWS.getResultadoWS();
					
					logger.debug("XML de entrada: " + resultWS.getXmlIn());

					if (Ice2sigsService.Operacion.CONSULTA_GENERAL.getCodigo() != op.getCodigo() && Estatus.EXITO.getCodigo() != clientesRespuesta.getCodigo() 
							&& Estatus.LLAVE_DUPLICADA.getCodigo() != clientesRespuesta.getCodigo()) {
						logger.error("Guardando en bitacora el estatus");

						try {
							kernelManager.movBitacobro(
									(String) params.get("pv_cdunieco_i"),
									(String) params.get("pv_cdramo_i"),
									(String) params.get("pv_estado_i"),
									(String) params.get("pv_nmpoliza_i"),
									(String) params.get("pv_nmsuplem_i"), 
									Ice2sigsService.TipoError.ErrWScli.getCodigo(),
									clientesRespuesta.getCodigo() + " - " + clientesRespuesta.getMensaje(),
									usuario, (String) params.get("pv_ntramite_i"), "ws.ice2sigs.url", "clienteGeneralGS",
									resultWS.getXmlIn(), Integer.toString(clientesRespuesta.getCodigo()));
						} catch (Exception e1) {
							logger.error("Error al insertar en bitacora", e1);
						}
					}
				}
			}catch(WSException e){
				logger.error("Error al ejecutar WS Cliente General Clave Externa: " + cliente.getNumeroExterno() + " RFC: " + cliente.getRfcCli(), e);
				logger.error("Imprimpriendo el xml enviado al WS: Payload: " + e.getPayload());
				if (Ice2sigsService.Operacion.CONSULTA_GENERAL.getCodigo() != op.getCodigo()){
					try {
						kernelManager.movBitacobro(
								(String) params.get("pv_cdunieco_i"),
								(String) params.get("pv_cdramo_i"),
								(String) params.get("pv_estado_i"),
								(String) params.get("pv_nmpoliza_i"), 
								(String) params.get("pv_nmsuplem_i"), 
								Ice2sigsService.TipoError.ErrWScliCx.getCodigo(), 
								"Msg: " + e.getMessage() + " ***Cause: " + e.getCause(),
								usuario, (String) params.get("pv_ntramite_i"), "ws.ice2sigs.url", "clienteGeneralGS",
								e.getPayload(), null);
					} catch (Exception e1) {
						logger.error("Error al insertar en bitacora", e1);
					}
				}
				
			}catch (Exception e){
				logger.error("Error al ejecutar WS Cliente General Clave: " + cliente.getNumeroExterno() + " RFC: " + cliente.getRfcCli(), e);
			}
		}else{
			logger.error("Error, No se tienen datos del Cliente");
		}
		
		return clientesRespuesta;
	}

	
	public boolean ejecutaWSrecibos(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem,
			String rutaPoliza, String sucursal,
			String nmsolici, String ntramite, boolean async,
			String tipoMov, UserVO userVO) {
		
		boolean allInserted = true;
		String cdtipsit = null;
		String cdtipsitGS = null;
		
		logger.debug("*** Entrando a metodo Recibos WS ice2sigs, para la poliza: " + nmpoliza + " sucursal: " + sucursal + " tipoMov: " + tipoMov + "***");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		params.put("pv_ntramite_i", ntramite);
		
		WrapperResultados result = null;
		WrapperResultadosWS resultWS = null;
		ArrayList<ReciboWrapper> recibos =  null;
		try {
			result = kernelManager.obtenDatosRecibos(params);
			recibos = (ArrayList<ReciboWrapper>) result.getItemList();
			
			cdtipsit   = kernelManager.obtenCdtipsit(params);
			cdtipsitGS = kernelManager.obtenCdtipsitGS(params);
			
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de RECIBOS",e1);
			return false;
		}

		String usuario = "SIN USUARIO";
		if(userVO != null){
			usuario = userVO.getUser();
		}
		
		if(async){
			params.put("USUARIO", usuario);
		}
		
		Recibo recibo = null;
		for(ReciboWrapper recVO: recibos){
			recibo = recVO.getRecibo();
			Operacion operacion = Operacion.valueOf(recVO.getOperacion());
			
			try{
				
				if(async){
					// Se crea un HashMap por cada invocacion asincrona del WS, para evitar issue (sobreescritura de valores):
					HashMap<String, Object> paramsBitacora = new HashMap<String, Object>();
					paramsBitacora.putAll(params);
					paramsBitacora.put("NumRec", recibo.getNumRec());
					paramsBitacora.put("TipEnd", recibo.getTipEnd());
					paramsBitacora.put("NumEnd", recibo.getNumEnd());
					
					ejecutaReciboGS(operacion, recibo, paramsBitacora, async);
				}else{
					resultWS = ejecutaReciboGS(operacion, recibo, null, async);
					ReciboRespuesta respuesta = (ReciboRespuesta) resultWS.getResultadoWS();
					logger.debug("Resultado al ejecutar el WS Recibo: " + recibo.getNumRec() + " >>>"
							+ respuesta.getCodigo() + " - " + respuesta.getMensaje());
					logger.debug("XML de entrada: " + resultWS.getXmlIn());

					if (Estatus.EXITO.getCodigo() != respuesta.getCodigo()) {
						logger.error("Guardando en bitacora el estatus");

						try {
							kernelManager.movBitacobro((String) params.get("pv_cdunieco_i"),
									(String) params.get("pv_cdramo_i"),
									(String) params.get("pv_estado_i"),
									(String) params.get("pv_nmpoliza_i"),
									(String) params.get("pv_nmsuplem_i"),
									Ice2sigsService.TipoError.ErrWSrec.getCodigo(),
									"Error Recibo " + recibo.getNumRec() + " TipEnd: " + recibo.getTipEnd() + " NumEnd: " + recibo.getNumEnd()
											+ " >>> " + respuesta.getCodigo() + " - "
											+ respuesta.getMensaje(),
									 usuario, (String) params.get("pv_ntramite_i"), "ws.ice2sigs.url", "reciboGS",
									 resultWS.getXmlIn(), Integer.toString(respuesta.getCodigo()));
						} catch (Exception e1) {
							logger.error("Error al insertar en Bitacora", e1);
						}
					}
				}
			}catch(WSException e){
				logger.error("Error al insertar recibo: "+recibo.getNumRec()+" tramite: "+ntramite);
				logger.error("Imprimpriendo el xml enviado al WS: Payload: " + e.getPayload());
				try {
					kernelManager.movBitacobro(
							(String) params.get("pv_cdunieco_i"),
							(String) params.get("pv_cdramo_i"),
							(String) params.get("pv_estado_i"),
							(String) params.get("pv_nmpoliza_i"),
							(String) params.get("pv_nmsuplem_i"),
							Ice2sigsService.TipoError.ErrWSrecCx.getCodigo(),
							"Error Recibo " + recibo.getNumRec() + " TipEnd: " + recibo.getTipEnd() + " NumEnd: " + recibo.getNumEnd()
									+ " Msg: " + e.getMessage() + " ***Cause: "
									+ e.getCause(),
							 usuario, (String) params.get("pv_ntramite_i"), "ws.ice2sigs.url", "reciboGS",
							 e.getPayload(), null);
				} catch (Exception e1) {
					logger.error("Error al insertar en Bitacora", e1);
				}
			}catch (Exception e){
				logger.error("Error Excepcion al insertar recibo: "+recibo.getNumRec()+" tramite: "+ntramite,e);
			}
		}
		
		/**
		 * PARA EL GUARDADO CADA PDF DE RECIBO
		 */
		logger.debug("*** Empieza generacion de URLs para Recibos ***");
		
		String visible = null;
		int contVisible = 0;
		for(ReciboWrapper recVO: recibos){
			
			recibo = recVO.getRecibo();
			
			/**
			 * Si el Recibo Tiene estatus 1 se guarda en tdocupol como documento de la poliza, excepto algunos endosos como el de forma de pago,
			 * donde se generan recibos negativos para cancelar y esos no deben de guardarse, estos casos el estatus es distinto de 1
			 */
			if(!"1".equals(recibo.getStatusr())) continue;
			/**
			 * Por default se permite imprimir solo el primer recibo y los demas se guardan ocultos.
			 */
			contVisible++;
			visible = (1 == contVisible) ? Constantes.SI : Constantes.NO;
			
			try{
				int numEndoso;
				String tipoEndoso;
				// Si es poliza nueva:
				if(("1").equals(tipoMov)) {
					numEndoso = 0;
					tipoEndoso = "";
				} else {
					numEndoso = recibo.getNumEnd();
					tipoEndoso = recibo.getTipEnd();
				}
				
				int modalidad = 0;
				if(StringUtils.isNotBlank(cdtipsit)){
					if(TipoSituacion.SALUD_VITAL.getCdtipsit().equalsIgnoreCase(cdtipsit)){
						modalidad = 1;
					}else if(TipoSituacion.SALUD_NOMINA.getCdtipsit().equalsIgnoreCase(cdtipsit)){
						modalidad = 2;
					}
				}
				
				//Parametro1:  9999: Recibo
				//Parametro2:  Modalidad casi siempre va en 0
				//Parametro3:  Sucursal
				//Parametro4:  Ramo (213 o 214)
				//Parametro5:  Poliza
				//Parametro6:  Tramite(poner 0)
				//Parametro7:  Numero de endoso (Cuando es poliza nueva poner 0)
				//Parametro8:  Tipo de endoso (Si es vacio no enviar nada en otro caso poner A o D segun sea el caso)
				//Parametro9:  Numero de recibo (1,2,3..segun la forma de pago) Para nuestro caso es siempre el 1
				
					String parametros = "?9999,"+modalidad+","+sucursal+","+cdtipsitGS+","+nmpoliza+",0,"+numEndoso+","+tipoEndoso+","+recibo.getNumRec();
					logger.debug("URL Generada para Recibo: "+ urlImpresionRecibos + parametros);
					//HttpRequestUtil.generaReporte(this.getText("recibos.impresion.url")+parametros, rutaPoliza+"/Recibo_"+recibo.getRmdbRn()+"_"+recibo.getNumRec()+".pdf");
					
					HashMap<String, Object> paramsR =  new HashMap<String, Object>();
					paramsR.put("pv_cdunieco_i", cdunieco);
					paramsR.put("pv_cdramo_i", cdramo);
					paramsR.put("pv_estado_i", estado);
					paramsR.put("pv_nmpoliza_i", nmpoliza);
					paramsR.put("pv_nmsuplem_i", nmsuplem);
					paramsR.put("pv_feinici_i", new Date());
					paramsR.put("pv_cddocume_i", urlImpresionRecibos + parametros);
					paramsR.put("pv_dsdocume_i", "Recibo "+recibo.getNumRec());
					paramsR.put("pv_nmsolici_i", nmsolici);
					paramsR.put("pv_ntramite_i", ntramite);
					paramsR.put("pv_tipmov_i", tipoMov);
					paramsR.put("pv_swvisible_i", visible);
					
					kernelManager.guardarArchivo(paramsR);
				//}
			}catch(Exception e){
				logger.error("Error al guardar indexaxion de recibo: " + recibo.getRmdbRn(), e);
			}
		}

		return allInserted;
	}

	public RespuestaVO ejecutaWSreclamosTramite(String ntramite, Ice2sigsService.Operacion op, boolean async, UserVO userVO) {
		
		logger.debug("********************* Entrando a Ejecuta WSreclamo ******************************");
		
		RespuestaVO res =  new RespuestaVO();
		res.setSuccess(true);
		List<Reclamo> resultReclamos = null;
		WrapperResultadosWS resultWS = null;
		
		//Se invoca servicio para obtener los datos del cliente
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_ntramite_i", ntramite);
		
		String usuario = "SIN USUARIO";
		if(userVO != null){
			usuario = userVO.getUser();
		}
		params.put("USUARIO", usuario);
		
		try {
			resultReclamos = siniestrosManager.obtieneDatosReclamoWS(params);
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de obtieneDatosReclamoWS",e1);
			res.setSuccess(false);
			res.setMensaje("Sin datos. No se han guardado correctamente los calculos.");
			return res;
		}
		
		if(resultReclamos != null && resultReclamos.size() > 0){
			
			for(Reclamo reclamo : resultReclamos){
				if(reclamo.getNumPol() == 0){
					logger.debug("Sin datos para ejecutaWSreclamo.");
					res.setSuccess(false);
					res.setMensaje("Alguno de los reclamos no tiene la informaci&oacute;n necesaria para solicitar el pago.");
					return res;
				}
				
				params.put("pv_cdunieco_i", Integer.toString(reclamo.getSucPol()));
				params.put("pv_cdramo_i",   Integer.toString(reclamo.getRamPol()));
				params.put("pv_estado_i",   "M");
				params.put("pv_nmpoliza_i", Integer.toString(reclamo.getNumPol()));
				
				try{
					logger.debug(">>>>>>> Enviando el Reclamo: " + reclamo.getIcodreclamo());
					
					if(async){
						// Se crea un HashMap por cada invocacion asincrona del WS, para evitar issue (sobreescritura de valores):
						HashMap<String, Object> paramsBitacora = new HashMap<String, Object>();
						paramsBitacora.putAll(params);
						paramsBitacora.put("NumSin", reclamo.getIcodreclamo());
						paramsBitacora.put("NumInc", reclamo.getIncPol());
						
						ejecutaReclamoGS(op, reclamo, paramsBitacora, async);
					}else{
						
						resultWS = ejecutaReclamoGS(op, reclamo, params, async);
						ReclamoRespuesta respuesta = (ReclamoRespuesta) resultWS.getResultadoWS();
						logger.debug("XML de entrada: " + resultWS.getXmlIn());
						
						if (Estatus.EXITO.getCodigo() != respuesta.getCodigo()) {
							logger.error("Guardando en bitacora el estatus de Error del WS");

							try {
								kernelManager.movBitacobro((String) params.get("pv_cdunieco_i"),
										(String) params.get("pv_cdramo_i"),
										(String) params.get("pv_estado_i"),
										(String) params.get("pv_nmpoliza_i"),
										(String) params.get("pv_nmsuplem_i"),
										Ice2sigsService.TipoError.ErrWSsin.getCodigo(),
										"Error en ReclamoCod: " + reclamo.getIcodreclamo() + " Inciso: " + reclamo.getIncPol() 
												+ " >>> " + respuesta.getCodigo() + " - "
												+ respuesta.getMensaje(),
										 usuario, (String) params.get("pv_ntramite_i"), "ws.ice2sigs.url", "reclamoGS",
										 resultWS.getXmlIn(), Integer.toString(respuesta.getCodigo()));
							} catch (Exception e1) {
								logger.error("Error al insertar en Bitacora", e1);
							}
							
							res.setSuccess(false);
							res.setMensaje("Error al enviar alg&uacute;n Reclamo para la solicitud de Pago. Intente nuevamente.");
						}
					}
					
				}catch(WSException e){
					logger.error("Error al enviar el Reclamo: " + reclamo.getIcodreclamo(), e);
					res.setSuccess(false);
					res.setMensaje("Error al enviar alg&uacute;n Reclamo para la solicitud de Pago. Intente nuevamente.");
					logger.error("Imprimpriendo el xml enviado al WS: Payload: " + e.getPayload());
					try {
						kernelManager.movBitacobro(
								(String) params.get("pv_cdunieco_i"),
								(String) params.get("pv_cdramo_i"),
								(String) params.get("pv_estado_i"),
								(String) params.get("pv_nmpoliza_i"),
								(String) params.get("pv_nmsuplem_i"),
								Ice2sigsService.TipoError.ErrWSsinCx.getCodigo(),
								"Error en ReclamoCod: " + reclamo.getIcodreclamo() + " Inciso: " + reclamo.getIncPol()
										+ " Msg: " + e.getMessage() + " ***Cause: "
										+ e.getCause(),
								 usuario, (String) params.get("pv_ntramite_i"), "ws.ice2sigs.url", "reclamoGS",
								 e.getPayload(), null);
					} catch (Exception e1) {
						logger.error("Error al insertar en Bitacora", e1);
					}
				}catch (Exception e){
					logger.error("Error al enviar el Reclamo: " + reclamo.getIcodreclamo(), e);
					res.setSuccess(false);
					res.setMensaje("Error al enviar alg&uacute;n Reclamo para la solicitud de Pago. Intente nuevamente.");
				}
			}
		}else {
			logger.debug("Sin datos para ejecutaWSreclamo.");
			res.setSuccess(false);
			res.setMensaje("Sin datos. No se han guardado correctamente los calculos.");
			return res;
		}

		return res;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public String getUrlImpresionRecibos() {
		return urlImpresionRecibos;
	}

	public void setUrlImpresionRecibos(String urlImpresionRecibos) {
		this.urlImpresionRecibos = urlImpresionRecibos;
	}

	
	/**
	 * Setter method
	 * @param kernelManager
	 */
	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}

	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}

}
