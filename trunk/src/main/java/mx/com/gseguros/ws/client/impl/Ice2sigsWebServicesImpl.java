package mx.com.gseguros.ws.client.impl;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

import mx.com.gseguros.ws.client.Ice2sigsWebServices;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.Agente;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.AgenteGS;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.AgenteGSE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.AgenteGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.AgenteRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.AgenteSalud;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.AgenteSaludGS;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.AgenteSaludGSE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.AgenteSaludGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.AgenteSaludRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.AseguradoRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.Ccomision;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.CcomisionRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.Cliente;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteGS;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteGSE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSalud;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSaludGS;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSaludGSE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSaludGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ClienteSaludRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ComisionReciboAgenteGS;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ComisionReciboAgenteGSE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ComisionReciboAgenteGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAgenteGS;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAgenteGSE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAgenteGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAgenteRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoCoberturaGS;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoCoberturaGSE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoCoberturaGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoCoberturaRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoEndosoGS;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoEndosoGSE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoEndosoGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoEndosoRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoGS;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoGSE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoAseguradoGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoGS;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoGSE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.MovimientoRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.Poliza;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.PolizaGS;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.PolizaGSE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.PolizaGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.PolizaMovimiento;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.PolizaMovimientoAgente;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.PolizaMovimientoAsegurado;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.PolizaMovimientoAseguradoCobertura;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.PolizaMovimientoAseguradoEndoso;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.PolizaRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.Recibo;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ReciboGS;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ReciboGSE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ReciboGSResponseE;
import mx.com.gseguros.ws.client.ice2sigs.ServicioGSServiceStub.ReciboRespuesta;
import mx.com.gseguros.ws.client.ice2sigs.callback.ServicioGSServiceCallbackHandlerImpl;

import org.apache.axis2.AxisFault;

public class Ice2sigsWebServicesImpl implements Ice2sigsWebServices {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(Ice2sigsWebServicesImpl.class);

	private static final long WS_TIMEOUT =  20000;
	
	/*public enum Operacion {

		INSERTA(1), ACTUALIZA(2), CONSULTA(3);

		private int codigo;

		private Operacion(int codigo) {
			this.codigo = codigo;
		}

		public int getCodigo() {
			return codigo;
		}

	}*/

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		for (Operacion op : Operacion.values()) {
//			System.out.println(op.getCodigo() + " " + op.name());
//		}

		Ice2sigsWebServicesImpl serv = new Ice2sigsWebServicesImpl();

		try {
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT); 
			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse("15/06/1986"));
			
			System.out.println("Calendar: "+ cal.getTime());
			
			AgenteSalud agente = new AgenteSalud();
			agente.setClaAge("asd57AJFDG");
			agente.setConPro("1");
			agente.setCveFia("AS86A87");
			agente.setFecIng(cal);
			agente.setFecReg(cal);
			agente.setIniFia(cal);
			agente.setNomFia("nomfia");
			agente.setNumAge(5656);
			agente.setOfiAge(54);
			agente.setProAge(72);
			agente.setRegAge(8);
			agente.setSucAge(17);
			agente.setTerFia(cal);
			agente.setTipAge(2);
			agente.setUsrReg("usrreg");
			
			AgenteSaludRespuesta resp = serv.ejecutaAgenteSaludGS(Operacion.CONSULTA, agente, "http://wasgs.gseguros.com.mx:8080/ice2sigs/servicios");
			
			System.out.println("Codigo respuesta: "+ resp.getCodigo());
			System.out.println("Mensaje respuesta: "+ resp.getMensaje());
			System.out.println("Entidad respuesta: "+ resp.getAgenteSalud().getFecIng().getTime());
			
//			cliente = new ClienteSalud();
//			cliente.setClaveCli(162);
//			resp = serv.ejecutaClienteSaludGS(Operacion.CONSULTA, cliente);
//			System.out.println("Mensaje respuesta: "+ resp.getMensaje());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public PolizaRespuesta ejecutaPolizaGS(Operacion operacion,
			Poliza poliza, String endpoint) throws Exception {

		PolizaRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;

		try {
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);

		PolizaGSResponseE RespuestaGS = null;

		PolizaGS polizaS = new PolizaGS();
		polizaS.setArg0(operacion.getCodigo());
		polizaS.setArg1(poliza);

		PolizaGSE polizaE = new PolizaGSE();
		polizaE.setPolizaGS(polizaS);

		try {
			RespuestaGS = stubGS.polizaGS(polizaE);
			resultado = RespuestaGS.getPolizaGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	public ReciboRespuesta ejecutaReciboGS(Operacion operacion,
			Recibo recibo, String endpoint, HashMap<String, Object> params, boolean async) throws Exception {
		
		ReciboRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
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
				ServicioGSServiceCallbackHandlerImpl callback = new ServicioGSServiceCallbackHandlerImpl(params);
				stubGS.startreciboGS(reciboE, callback);
			} else {
				RespuestaGS = stubGS.reciboGS(reciboE);
				resultado = RespuestaGS.getReciboGSResponse().get_return();
				logger.debug("Resultado sincrono para primer ejecucion de WS: "+resultado.getCodigo()+" - "+resultado.getMensaje());
			}
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	public AgenteRespuesta ejecutaAgenteGS(Operacion operacion,
			Agente agente, String endpoint) throws Exception {
		
		AgenteRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		AgenteGSResponseE RespuestaGS = null;
		
		AgenteGS agenteS = new AgenteGS();
		agenteS.setArg0(operacion.getCodigo());
		agenteS.setArg1(agente);
		
		AgenteGSE agenteE = new AgenteGSE();
		agenteE.setAgenteGS(agenteS);
		
		try {
			RespuestaGS = stubGS.agenteGS(agenteE);
			resultado = RespuestaGS.getAgenteGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}
	
	public AgenteSaludRespuesta ejecutaAgenteSaludGS(Operacion operacion,
			AgenteSalud agente, String endpoint) throws Exception {
		
		AgenteSaludRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		AgenteSaludGSResponseE RespuestaGS = null;
		
		AgenteSaludGS agenteS = new AgenteSaludGS();
		agenteS.setArg0(operacion.getCodigo());
		agenteS.setArg1(agente);
		
		AgenteSaludGSE agenteE = new AgenteSaludGSE();
		agenteE.setAgenteSaludGS(agenteS);
		
		try {
			RespuestaGS = stubGS.agenteSaludGS(agenteE);
			resultado = RespuestaGS.getAgenteSaludGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	public ClienteRespuesta ejecutaClienteGS(Operacion operacion,
			Cliente cliente, String endpoint) throws Exception {
		
		ClienteRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		ClienteGSResponseE RespuestaGS = null;
		
		ClienteGS clienteS = new ClienteGS();
		clienteS.setArg0(operacion.getCodigo());
		clienteS.setArg1(cliente);
		
		ClienteGSE clienteE = new ClienteGSE();
		clienteE.setClienteGS(clienteS);
		
		try {
			RespuestaGS = stubGS.clienteGS(clienteE);
			resultado = RespuestaGS.getClienteGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}
	
	public ClienteSaludRespuesta ejecutaClienteSaludGS(Operacion operacion,
			ClienteSalud cliente, String endpoint, HashMap<String, Object> params, boolean async) throws Exception {
		
		ClienteSaludRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
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
				ServicioGSServiceCallbackHandlerImpl callback = new ServicioGSServiceCallbackHandlerImpl(params);
				stubGS.startclienteSaludGS(clienteE, callback);
			} else {
				RespuestaGS = stubGS.clienteSaludGS(clienteE);
				resultado = RespuestaGS.getClienteSaludGSResponse().get_return();
				logger.debug("Resultado sincrono para primer ejecucion de WS: "+resultado.getCodigo()+" - "+resultado.getMensaje());
			}
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}
	
	public CcomisionRespuesta ejecutaComisionReciboAgenteGS(Operacion operacion,
			Ccomision comisionReciboAgente, String endpoint) throws Exception {
		
		CcomisionRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		ComisionReciboAgenteGSResponseE RespuestaGS = null;
		
		ComisionReciboAgenteGS comisionReciboAgenteS = new ComisionReciboAgenteGS();
		comisionReciboAgenteS.setArg0(operacion.getCodigo());
		comisionReciboAgenteS.setArg1(comisionReciboAgente);
		
		ComisionReciboAgenteGSE comisionReciboAgenteE = new ComisionReciboAgenteGSE();
		comisionReciboAgenteE.setComisionReciboAgenteGS(comisionReciboAgenteS);
		
		try {
			RespuestaGS = stubGS.comisionReciboAgenteGS(comisionReciboAgenteE);
			resultado = RespuestaGS.getComisionReciboAgenteGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}
	
	
	public MovimientoAgenteRespuesta ejecutaMovimientoAgenteGS(Operacion operacion,
			PolizaMovimientoAgente movimientoAgente, String endpoint) throws Exception {
		
		MovimientoAgenteRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		MovimientoAgenteGSResponseE RespuestaGS = null;
		
		MovimientoAgenteGS movimientoAgenteS = new MovimientoAgenteGS();
		movimientoAgenteS.setArg0(operacion.getCodigo());
		movimientoAgenteS.setArg1(movimientoAgente);
		
		MovimientoAgenteGSE movimientoAgenteE = new MovimientoAgenteGSE();
		movimientoAgenteE.setMovimientoAgenteGS(movimientoAgenteS);
		
		try {
			RespuestaGS = stubGS.movimientoAgenteGS(movimientoAgenteE);
			resultado = RespuestaGS.getMovimientoAgenteGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	public AseguradoRespuesta ejecutaMovimientoAseguradoGS(Operacion operacion,
			PolizaMovimientoAsegurado movimientoAsegurado, String endpoint) throws Exception {
		
		AseguradoRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		MovimientoAseguradoGSResponseE RespuestaGS = null;
		
		MovimientoAseguradoGS movimientoAseguradoS = new MovimientoAseguradoGS();
		movimientoAseguradoS.setArg0(operacion.getCodigo());
		movimientoAseguradoS.setArg1(movimientoAsegurado);
		
		MovimientoAseguradoGSE movimientoAseguradoE = new MovimientoAseguradoGSE();
		movimientoAseguradoE.setMovimientoAseguradoGS(movimientoAseguradoS);
		
		try {
			RespuestaGS = stubGS.movimientoAseguradoGS(movimientoAseguradoE);
			resultado = RespuestaGS.getMovimientoAseguradoGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	public MovimientoAseguradoCoberturaRespuesta ejecutaMovimientoAseguradoCoberturaGS(Operacion operacion,
			PolizaMovimientoAseguradoCobertura movimientoAseguradoCobertura, String endpoint) throws Exception {
		
		MovimientoAseguradoCoberturaRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		MovimientoAseguradoCoberturaGSResponseE RespuestaGS = null;
		
		MovimientoAseguradoCoberturaGS movimientoAseguradoCoberturaS = new MovimientoAseguradoCoberturaGS();
		movimientoAseguradoCoberturaS.setArg0(operacion.getCodigo());
		movimientoAseguradoCoberturaS.setArg1(movimientoAseguradoCobertura);
		
		MovimientoAseguradoCoberturaGSE movimientoAseguradoCoberturaE = new MovimientoAseguradoCoberturaGSE();
		movimientoAseguradoCoberturaE.setMovimientoAseguradoCoberturaGS(movimientoAseguradoCoberturaS);
		
		try {
			RespuestaGS = stubGS.movimientoAseguradoCoberturaGS(movimientoAseguradoCoberturaE);
			resultado = RespuestaGS.getMovimientoAseguradoCoberturaGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	public MovimientoAseguradoEndosoRespuesta ejecutaMovimientoAseguradoEndosoGS(Operacion operacion,
			PolizaMovimientoAseguradoEndoso movimientoAseguradoEndoso, String endpoint) throws Exception {
		
		MovimientoAseguradoEndosoRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		MovimientoAseguradoEndosoGSResponseE RespuestaGS = null;
		
		MovimientoAseguradoEndosoGS movimientoAseguradoEndosoS = new MovimientoAseguradoEndosoGS();
		movimientoAseguradoEndosoS.setArg0(operacion.getCodigo());
		movimientoAseguradoEndosoS.setArg1(movimientoAseguradoEndoso);
		
		MovimientoAseguradoEndosoGSE movimientoAseguradoEndosoE = new MovimientoAseguradoEndosoGSE();
		movimientoAseguradoEndosoE.setMovimientoAseguradoEndosoGS(movimientoAseguradoEndosoS);
		
		try {
			RespuestaGS = stubGS.movimientoAseguradoEndosoGS(movimientoAseguradoEndosoE);
			resultado = RespuestaGS.getMovimientoAseguradoEndosoGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	public MovimientoRespuesta ejecutaMovimientoGS(Operacion operacion,
			PolizaMovimiento movimiento, String endpoint) throws Exception {
		
		MovimientoRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		MovimientoGSResponseE RespuestaGS = null;
		
		MovimientoGS movimientoS = new MovimientoGS();
		movimientoS.setArg0(operacion.getCodigo());
		movimientoS.setArg1(movimiento);
		
		MovimientoGSE movimientoE = new MovimientoGSE();
		movimientoE.setMovimientoGS(movimientoS);
		
		try {
			RespuestaGS = stubGS.movimientoGS(movimientoE);
			resultado = RespuestaGS.getMovimientoGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

}
