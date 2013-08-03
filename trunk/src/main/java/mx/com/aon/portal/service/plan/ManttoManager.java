package mx.com.aon.portal.service.plan;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.plan.PlanVO;
import mx.com.aon.portal.model.plan.ProductoVO;
/**
 * 
 * @author sergio.ramirez
 *
 */

public interface ManttoManager {
	
	/**
	 * 
	 * @param cdRamoB
	 * @param dsPlanB
	 * @return
	 * @throws ApplicationException
	 */

	List<PlanVO> getPlanes(String cdRamoB, String dsPlanB ) throws ApplicationException;
	/**
	 * 
	 * @return
	 * @throws ApplicationException
	 */

	ArrayList<ProductoVO> getListas()throws ApplicationException;
	/**
	 * 
	 * @param plan
	 * @throws ApplicationException
	 */

	public void insertaPlan (PlanVO plan) throws ApplicationException;
	/**
	 * 
	 * @param planEdit
	 * @throws ApplicationException
	 */
	
	public void editaPlan(PlanVO planEdit) throws ApplicationException;
	/**
	 * 
	 * @param cdPlan
	 * @param cdRamo
	 * @return
	 * @throws ApplicationException
	 */

	public PlanVO getPlan(String cdPlan, String cdRamo)throws ApplicationException;
	/**
	 * 
	 * @param cdRamo
	 * @param dsPlan
	 * @throws ApplicationException
	 */
	public void copiarPlan(String cdRamo, String dsPlan)throws ApplicationException;
	/**
	 * 
	 * @param cdRamo
	 * @param cdPlan
	 * @throws ApplicationException
	 */
	public void borrarPlan(String cdRamo, String cdPlan)throws ApplicationException;

	
	
}
