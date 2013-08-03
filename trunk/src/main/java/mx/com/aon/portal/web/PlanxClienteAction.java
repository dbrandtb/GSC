package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.portal.model.DetallePlanXClienteVO;
import mx.com.aon.portal.service.DetallePlanXClienteManager;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Plan por Cliente 
 * 
 */
@SuppressWarnings("serial")
public class PlanxClienteAction extends AbstractListAction{

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PlanxClienteAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 * Este objeto no es serializable
	 */
	private transient DetallePlanXClienteManager detallePlanXClienteManager;
	
	private String codigoProducto;
	private String codigoPlan;
	private String codigoCliente;
	private String codigoSituacion;
	private String codigoGarantia;
	private String codigoObligatorio;
	private String codigoAseguradora;
	private String codigoElemento;

	@SuppressWarnings("unchecked")
	private List registro;
	private String messageResult;

 /**
   * Metodo que atiende una peticion de obtener un plan por cliente
	 * 
	 * @return success
	 * 
	 * @throws Exception
   */
	@SuppressWarnings("unchecked")
	public String cmdGetClick () throws Exception{
		try {
            DetallePlanXClienteVO detallePlanXClienteVO = new DetallePlanXClienteVO();
            detallePlanXClienteVO.setCdRamo(getCodigoProducto());
            detallePlanXClienteVO.setCdPlan(getCodigoPlan());
            detallePlanXClienteVO.setCdElemento(codigoElemento);
            detallePlanXClienteVO.setCdTipSit(codigoSituacion);
            detallePlanXClienteVO.setCdGarant(codigoGarantia);
            detallePlanXClienteVO.setCdUniEco(codigoAseguradora);
            detallePlanXClienteVO = detallePlanXClienteManager.getPlanXCliente(detallePlanXClienteVO);
            registro = new ArrayList<DetallePlanXClienteVO>();
            registro.add(detallePlanXClienteVO);
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

  /**
   * Metodo que atiende una peticion de insertar un nuevo plan por cliente
	 * 
	 * @return success
	 * 
	 * @throws Exception
   */
    public String cmdGuardarNuevoClick () throws Exception{
        try {
            DetallePlanXClienteVO detallePlanXClienteVO = new DetallePlanXClienteVO ();
            detallePlanXClienteVO.setCdRamo(codigoProducto);
            detallePlanXClienteVO.setCdPlan(codigoPlan);
            detallePlanXClienteVO.setCdPerson(codigoCliente);
            detallePlanXClienteVO.setCdTipSit(codigoSituacion);
            detallePlanXClienteVO.setCdGarant(codigoGarantia);
            String  _swOblig  = (codigoObligatorio!=null && !codigoObligatorio.equals("") && codigoObligatorio.equals("on"))?"1":"0";
            detallePlanXClienteVO.setSwOblig(_swOblig);
            detallePlanXClienteVO.setCdUniEco(codigoAseguradora);
            detallePlanXClienteVO.setCdElemento(codigoElemento);
            messageResult = detallePlanXClienteManager.addPlanXCliente(detallePlanXClienteVO);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }
    
 /**
   * Metodo que atiende una peticion de actualizar un plan por cliente
	 * 
	 * @return success
	 * 
	 * @throws Exception
   */
    public String cmdGuardarClick () throws Exception{
        try {
            DetallePlanXClienteVO detallePlanXClienteVO = new DetallePlanXClienteVO ();
            detallePlanXClienteVO.setCdRamo(codigoProducto);
            detallePlanXClienteVO.setCdPlan(codigoPlan);
            detallePlanXClienteVO.setCdPerson(codigoCliente);
            detallePlanXClienteVO.setCdTipSit(codigoSituacion);
            detallePlanXClienteVO.setCdGarant(codigoGarantia);
            String  _swOblig  = (codigoObligatorio!=null && !codigoObligatorio.equals("") && codigoObligatorio.equals("on"))?"1":"0";
            detallePlanXClienteVO.setSwOblig(_swOblig);
            detallePlanXClienteVO.setCdUniEco(codigoAseguradora);
            detallePlanXClienteVO.setCdElemento(codigoElemento);
            messageResult = detallePlanXClienteManager.setPlanXCliente(detallePlanXClienteVO);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }
    
 /**
	 * Elimina un registro de la grilla previamente seleccionado
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
   public String cmdBorrarClick () throws Exception {
        try {
            DetallePlanXClienteVO detallePlanXClienteVO = new DetallePlanXClienteVO ();
            detallePlanXClienteVO.setCdRamo(codigoProducto);
            detallePlanXClienteVO.setCdPlan(codigoPlan);
            detallePlanXClienteVO.setCdPerson(codigoCliente);
            detallePlanXClienteVO.setCdTipSit(codigoSituacion);
            detallePlanXClienteVO.setCdGarant(codigoGarantia);
            String  _swOblig  = (codigoObligatorio!=null && !codigoObligatorio.equals("") && codigoObligatorio.equals("on"))?"1":"0";
            detallePlanXClienteVO.setSwOblig(_swOblig);
            detallePlanXClienteVO.setCdUniEco(codigoAseguradora);
            detallePlanXClienteVO.setCdElemento(codigoElemento);
            messageResult = detallePlanXClienteManager.borrarPlanXCliente(detallePlanXClienteVO);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }
 

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getCodigoPlan() {
		return codigoPlan;
	}

	public void setCodigoPlan(String codigoPlan) {
		this.codigoPlan = codigoPlan;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getCodigoSituacion() {
		return codigoSituacion;
	}

	public void setCodigoSituacion(String codigoSituacion) {
		this.codigoSituacion = codigoSituacion;
	}


    public String getCodigoGarantia() {
        return codigoGarantia;
    }

    public void setCodigoGarantia(String codigoGarantia) {
        this.codigoGarantia = codigoGarantia;
    }

    public String getCodigoObligatorio() {
		return codigoObligatorio;
	}

	public void setCodigoObligatorio(String codigoObligatorio) {
		this.codigoObligatorio = codigoObligatorio;
	}


	public void setDetallePlanXClienteManager(
			DetallePlanXClienteManager detallePlanXClienteManager) {
		this.detallePlanXClienteManager = detallePlanXClienteManager;
	}

	public String getCodigoAseguradora() {
		return codigoAseguradora;
	}

	public void setCodigoAseguradora(String codigoAseguradora) {
		this.codigoAseguradora = codigoAseguradora;
	}

	public String getCodigoElemento() {
		return codigoElemento;
	}

	public void setCodigoElemento(String codigoElemento) {
		this.codigoElemento = codigoElemento;
	}

	@SuppressWarnings("unchecked")
	public List getRegistro() {
		return registro;
	}

	@SuppressWarnings("unchecked")
	public void setRegistro(List registro) {
		this.registro = registro;
	}

	public String getMessageResult() {
		return messageResult;
	}

	public void setMessageResult(String messageResult) {
		this.messageResult = messageResult;
	}


}
