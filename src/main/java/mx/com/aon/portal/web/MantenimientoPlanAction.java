package mx.com.aon.portal.web;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.PlanesMPlanProVO;
import mx.com.aon.portal.service.MantenimientoPlanesManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;

/**
 * Clase Action que atiende las solicitudes de ABM que vienen del cliente
 *  para la pantalla de Mantenimiento de Planes.
 *
 */
@SuppressWarnings("serial")
public class MantenimientoPlanAction extends AbstractListAction {


	private transient MantenimientoPlanesManager mantenimientoPlanesManager; 
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaMantenimientoPlanesAction.class);
    private List<PlanesMPlanProVO> recordList;
	private String cdRamo;
	private String cdPlan;
	private String cdTipSit;
	private String cdGarant;
	private String swOblig;
	/**
	 * Contiene el mensaje devuelto por backbone cuando success = true
	 */
	private String messageResult;

    /**
     * Metodo que obtiene un registro de un plan.
     * 
     * @return success
     * 
     * @throws Exception
     */
	public String cmdGetClick () throws Exception{
        try {
            PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO ();
            planesMPlanProVO.setCdRamo(cdRamo);
            planesMPlanProVO.setCdPlan(cdPlan);
            planesMPlanProVO.setCdTipSit(cdTipSit);
            planesMPlanProVO.setCdGarant(cdGarant);
            planesMPlanProVO =  mantenimientoPlanesManager.getPlan(planesMPlanProVO);
            recordList = new ArrayList<PlanesMPlanProVO>();
            recordList.add(planesMPlanProVO);
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
	 * Metodo que realiza la insercion de un nuevo plan.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
    public String cmdInsertarPlan () throws Exception{
		try {
			PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO ();
            planesMPlanProVO.setCdRamo(cdRamo);
            planesMPlanProVO.setCdPlan(cdPlan);
            planesMPlanProVO.setCdTipSit(cdTipSit);
            planesMPlanProVO.setCdGarant(cdGarant);
            String  _swOblig  = (swOblig!=null && !swOblig.equals("") && swOblig.equals("on"))?"1":"0";
			planesMPlanProVO.setSwOblig(_swOblig);
			messageResult = mantenimientoPlanesManager.insertarPlan(planesMPlanProVO);
			addActionMessage(messageResult);
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
     * Metodo que realiza la actualizacion de un plan modificado en pantalla.
     * 
     * @return success
     * 
     * @throws Exception
     */
	public String cmdActualizarPlan () throws Exception{
		try {
			PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO ();
			planesMPlanProVO.setCdRamo(cdRamo);
			planesMPlanProVO.setCdPlan(cdPlan);
			planesMPlanProVO.setCdTipSit(cdTipSit);
			planesMPlanProVO.setCdGarant(cdGarant);
            String  _swOblig  = (swOblig!=null && !swOblig.equals("") && swOblig.equals("on"))?"1":"0";
			planesMPlanProVO.setSwOblig(_swOblig);
			messageResult = mantenimientoPlanesManager.actualizarPlan(planesMPlanProVO);
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
	 * Metodo que realiza la eliminacion de un plan seleccionado en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarPlan ()throws Exception {
		try {
			PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO ();
			planesMPlanProVO.setCdRamo(cdRamo);
			planesMPlanProVO.setCdPlan(cdPlan);
			planesMPlanProVO.setCdTipSit(cdTipSit);
			planesMPlanProVO.setCdGarant(cdGarant);
			String _swOblig = (swOblig != null && !swOblig.equals("") && swOblig.equals("true"))?"1":"0";
			if (swOblig == null) _swOblig = "0";
			planesMPlanProVO.setSwOblig(_swOblig);
			messageResult = mantenimientoPlanesManager.borrarPlan(planesMPlanProVO);
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

    public void setMantenimientoPlanesManager(MantenimientoPlanesManager mantenimientoPlanesManager) {
        this.mantenimientoPlanesManager = mantenimientoPlanesManager;
    }

    public String getCdRamo() {
        return cdRamo;
    }

    public void setCdRamo(String cdRamo) {
        this.cdRamo = cdRamo;
    }

    public String getCdPlan() {
        return cdPlan;
    }

    public void setCdPlan(String cdPlan) {
        this.cdPlan = cdPlan;
    }

    public String getCdTipSit() {
        return cdTipSit;
    }

    public void setCdTipSit(String cdTipSit) {
        this.cdTipSit = cdTipSit;
    }

    public String getCdGarant() {
        return cdGarant;
    }

    public void setCdGarant(String cdGarant) {
        this.cdGarant = cdGarant;
    }

    public String getSwOblig() {
        return swOblig;
    }

    public void setSwOblig(String swOblig) {
        this.swOblig = swOblig;
    }

    public List<PlanesMPlanProVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<PlanesMPlanProVO> recordList) {
        this.recordList = recordList;
    }


}
