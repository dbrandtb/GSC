package mx.com.aon.portal.web.plan;

import java.util.List;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.plan.PlanVO;
import mx.com.aon.portal.service.plan.ManttoManager;
import mx.com.gseguros.exception.ApplicationException;

/**
 * @author sergio.ramirez
 */
public class ManttoAction extends PrincipalCoreAction {
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -6654001282882848583L;
	private ManttoManager manttoManager;
	
	private boolean success;
	private String cdRamoB;
	private String dsPlanB;
	private List<PlanVO> planes;
	private PlanVO planVo;
	private String cdRamo;
	private String dsPlan;
	private String cdPlan;
	
		/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String test() throws Exception {
		return INPUT;
	}

	/**
	 * Metodo que se encarga de llenar el grid con los planes existentes en la base.
	 * @return
	 * @throws ApplicationException
	 */
	public String planesJson() throws ApplicationException {
		logger.debug("Entro a la lista Json");
		logger.debug("producto-----" + cdRamoB);
		logger.debug("Descripcion-plan---------" + dsPlanB);
		logger.debug("planVO" + planes);
		planes = manttoManager.getPlanes(cdRamoB, dsPlanB);
		success = true;
		
		return SUCCESS;
	}
	/**
	 * Metodo que se encarga de copiar un plan existente seleccionado desde el grid.
	 * @return
	 * @throws ApplicationException
	 */
	public String copiarPlanes() throws ApplicationException{
		logger.debug("Entro al metodo copiar");
		logger.debug("clave-producto->" + cdRamo);
		logger.debug("plan->" + dsPlan);
		manttoManager.copiarPlan(cdRamo, dsPlan);
		success=true;
		return SUCCESS;
	}
	/**
	 * Metodo que borra un plan existente en la base.
	 * @return
	 * @throws ApplicationException
	 */
	public String borrarPlanes() throws ApplicationException{
		logger.debug("Entro al metodo eliminar");
		logger.debug("clave-producto->"+ cdRamo);
		logger.debug("clave-plan->" + cdPlan);
		manttoManager.borrarPlan(cdRamo, cdPlan);
		success=true;
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCdPlan() {
		return cdPlan;
	}
	/**
	 * 
	 * @param cdPlan
	 */
	public void setCdPlan(String cdPlan) {
		this.cdPlan = cdPlan;
	}
	/**
	 * 
	 * @return
	 */
	public String getCdRamo() {
		return cdRamo;
	}
	/**
	 * 
	 * @param cdRamo
	 */
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsPlan() {
		return dsPlan;
	}
	/**
	 * 
	 * @param dsPlan
	 */
	public void setDsPlan(String dsPlan) {
		this.dsPlan = dsPlan;
	}

	/**
	 * getters and setters.
	 * 
	 * @return
	 */

	public PlanVO getPlanVo() {
		return planVo;
	}

	/**
	 * 
	 * @param planVo
	 */

	public void setPlanVo(PlanVO planVo) {
		this.planVo = planVo;
	}

	/**
	 * 
	 * @return
	 */

	public String getCdRamoB() {
		return cdRamoB;
	}

	/**
	 * 
	 * @param cdRamoB
	 */

	public void setCdRamoB(String cdRamoB) {
		this.cdRamoB = cdRamoB;
	}

	/**
	 * 
	 * @return
	 */

	public String getDsPlanB() {
		return dsPlanB;
	}

	/**
	 * 
	 * @param dsPlanB
	 */

	public void setDsPlanB(String dsPlanB) {
		this.dsPlanB = dsPlanB;
	}

	/**
	 * 
	 */

	public String input() throws Exception {
		return INPUT;
	}

	/**
	 * 
	 * @return
	 */

	public boolean isSuccess() {
		return success;
	}

	/**
	 * 
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 
	 * @return
	 */

	public List<PlanVO> getPlanes() {
		return planes;
	}

	/**
	 * 
	 * @param planes
	 */
	public void setPlanes(List<PlanVO> planes) {
		this.planes = planes;
	}

	/**
	 * 
	 * @param manttoManager
	 */

	public void setManttoManager(ManttoManager manttoManager) {
		this.manttoManager = manttoManager;
	}
	/**
	 * Metodo de herencia del padre (sin usar).
	 */
	public void prepare() throws Exception {
		
	}


}
