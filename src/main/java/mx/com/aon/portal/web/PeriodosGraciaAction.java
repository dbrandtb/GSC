package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import mx.com.aon.portal.service.PeriodosGraciaManager;
import mx.com.aon.portal.model.PeriodosGraciaVO;
import mx.com.aon.core.ApplicationException;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Periodos de Gracia 
 * 
 */
@SuppressWarnings("serial")
public class PeriodosGraciaAction extends ActionSupport {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PeriodosGraciaAction.class);

	@SuppressWarnings("unchecked")
	private List listaPeriodosGracia;

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 * Este objeto no es serializable
	 */
    private transient PeriodosGraciaManager periodosGraciaManager;
    
	private String cdTramo;
	private String dsTramo;
	private String nmMinimo;
    private String nmMaximo;
    private String diasGrac;
    private String diasCanc;
    private String messageResult;

	private boolean success;


  /**
   * Metodo que atiende una peticion de insertar un nuevo periodo de gracia
	 * 
	 * @return success
	 * 
	 * @throws Exception
   */
	public String cmdAgregarClick()throws Exception
	{
		try
		{
			PeriodosGraciaVO periodoGraciaVO = new PeriodosGraciaVO();
            
			periodoGraciaVO.setCdTramo(cdTramo);
			periodoGraciaVO.setDsTramo(dsTramo);
			periodoGraciaVO.setNmMinimo(nmMinimo);            
			periodoGraciaVO.setNmMaximo(nmMaximo);
			periodoGraciaVO.setDiasGrac(diasGrac);
			periodoGraciaVO.setDiasCanc(diasCanc);            

            String messageResult = periodosGraciaManager.agregarGuardarPeriodosGracia(periodoGraciaVO);

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
     * Metodo que atiende una peticion de obtener un periodo de gracia por cliente determinado
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public String cmdGetClick()throws Exception
	{
		try
		{
			listaPeriodosGracia = new ArrayList<PeriodosGraciaVO>();
			PeriodosGraciaVO periodoGraciaVO  = periodosGraciaManager.getPeriodosGracia(cdTramo);
            listaPeriodosGracia.add(periodoGraciaVO);
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
	 * Elimina un registro de la grilla previamente seleccionado
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")	
	public String cmdBorrarClick() throws Exception
	{
		try
		{
            messageResult = periodosGraciaManager.borrarPeriodosGracia(cdTramo);
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


    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}



	@SuppressWarnings("unchecked")
	public List getListaPeriodosGracia() {
		return listaPeriodosGracia;
	}


	@SuppressWarnings("unchecked")
	public void setListaPeriodosGracia(List listaPeriodosGracia) {
		this.listaPeriodosGracia = listaPeriodosGracia;
	}


	public PeriodosGraciaManager obtenPeriodosGraciaManager() {
		return periodosGraciaManager;
	}


	public void setPeriodosGraciaManager(PeriodosGraciaManager periodosGraciaManager) {
		this.periodosGraciaManager = periodosGraciaManager;
	}


	public String getCdTramo() {
		return cdTramo;
	}

	public void setCdTramo(String cdTramo) {
		this.cdTramo = cdTramo;
	}


	public String getDsTramo() {
		return dsTramo;
	}


	public void setDsTramo(String dsTramo) {
		this.dsTramo = dsTramo;
	}


	public String getNmMinimo() {
		return nmMinimo;
	}


	public void setNmMinimo(String nmMinimo) {
		this.nmMinimo = nmMinimo;
	}


	public String getNmMaximo() {
		return nmMaximo;
	}


	public void setNmMaximo(String nmMaximo) {
		this.nmMaximo = nmMaximo;
	}


	public String getDiasGrac() {
		return diasGrac;
	}


	public void setDiasGrac(String diasGrac) {
		this.diasGrac = diasGrac;
	}


	public String getDiasCanc() {
		return diasCanc;
	}


	public void setDiasCanc(String diasCanc) {
		this.diasCanc = diasCanc;
	}


	public String getMessageResult() {
		return messageResult;
	}


	public void setMessageResult(String messageResult) {
		this.messageResult = messageResult;
	}
	
	public void setperiodosGraciaManager(PeriodosGraciaManager periodosGraciaManager) {
        this.periodosGraciaManager = periodosGraciaManager;
    }

}
