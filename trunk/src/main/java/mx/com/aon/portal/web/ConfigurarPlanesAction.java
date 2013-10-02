package mx.com.aon.portal.web;

import mx.com.aon.portal.model.PlanesMPlanProVO;
import mx.com.aon.portal.service.MantenimientoPlanesManager;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import java.util.List;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Configurar Planes.
 * 
 */
@SuppressWarnings("serial")
public class ConfigurarPlanesAction extends AbstractListAction{

	private transient MantenimientoPlanesManager mantenimientoPlanesManager;
	private List<PlanesMPlanProVO> planesMPlanPro;
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaConfiguracionPlanesAction.class);
	private String codigoProducto;
	private String codigoPlan;
	private String tipoSituacion;
	private String codigoGarantia;
	private String codigoObligatorio;
	private boolean success;

	/**
	 * Metodo que obtiene un unico registro de un plan.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String getPlan ()throws Exception {
		try {
			PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO();
			planesMPlanProVO.setCdRamo(codigoProducto);
			planesMPlanProVO.setCdPlan(codigoPlan);
			planesMPlanProVO.setCdTipSit(tipoSituacion);
			planesMPlanProVO.setCdGarant(codigoGarantia);
			planesMPlanProVO = mantenimientoPlanesManager.getPlan(planesMPlanProVO);
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
	 * Metodo que realiza la insercion de un nuevo plan agregado por el usuario. 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String insertarPlan () throws Exception{
		String messageResult = "";
		try {
			PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO();
			planesMPlanProVO.setCdRamo(codigoProducto);
			planesMPlanProVO.setCdPlan(codigoPlan);
			planesMPlanProVO.setCdTipSit(tipoSituacion);
			planesMPlanProVO.setCdGarant(codigoGarantia);
			planesMPlanProVO.setSwOblig(codigoObligatorio);
			messageResult = mantenimientoPlanesManager.insertarPlan(planesMPlanProVO);
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
	 * Metodo que realiza la actualizacion de un plan modificado en pantalla.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String actualizarPlan () throws Exception{
		String messageResult = "";
		try {
			PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO();
			planesMPlanProVO.setCdRamo(codigoProducto);
			planesMPlanProVO.setCdPlan(codigoPlan);
			planesMPlanProVO.setCdTipSit(tipoSituacion);
			planesMPlanProVO.setCdGarant(codigoGarantia);
			planesMPlanProVO.setSwOblig(codigoObligatorio);
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

	public MantenimientoPlanesManager obtenMantenimientoPlanesManager() {
		return mantenimientoPlanesManager;
	}

	public void setMantenimientoPlanesManager(
			MantenimientoPlanesManager mantenimientoPlanesManager) {
		this.mantenimientoPlanesManager = mantenimientoPlanesManager;
	}

	public List<PlanesMPlanProVO> getPlanesMPlanPro() {
		return planesMPlanPro;
	}

	public void setPlanesMPlanPro(List<PlanesMPlanProVO> planesMPlanPro) {
		this.planesMPlanPro = planesMPlanPro;
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

	public String getTipoSituacion() {
		return tipoSituacion;
	}

	public void setTipoSituacion(String tipoSituacion) {
		this.tipoSituacion = tipoSituacion;
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

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
