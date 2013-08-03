package mx.com.aon.portal.web.plan;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.plan.PlanVO;
import mx.com.aon.portal.model.plan.ProductoVO;
import mx.com.aon.portal.service.plan.ManttoManager;

import com.opensymphony.xwork2.ActionContext;
/**
 * 
 * @author sergio.ramirez
 *
 */
public class AgregarPlanAction extends  PrincipalCoreAction{

	/**
	 *Serial Version 
	 */
	private static final long serialVersionUID = -6244487101244801030L;
	
	private ManttoManager manttoManager;
	private ArrayList<ProductoVO> productos;
	private PlanVO plan;
	private PlanVO planEdit;
	private PlanVO cargaPlan;

	private String descripcionRamo;
	private String dsPlanLista;
	private boolean success;
	private String cdRamo;
	private String id;
	private String dsPlan;
	private String cdPlan;
	
	/**
	 * Metodo que maneja el tipo de respuesta INPUT de struts.
	 */
	public String execute() throws Exception{
		logger.debug("Entering to INPUT");
		return INPUT; 
	}
	
	/**
	 * Metodo que carga la lista de productos.
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public String obtieneProductos()throws Exception{
		logger.debug("entrando a la lista de productos");
		productos = manttoManager.getListas();
		session.put("PRODUCTOS", productos);
		success=true;
		return SUCCESS;
	}
	/**
	 *Metodo que carga inserta un plan.
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String insertaPlan() throws Exception{
		
		
		logger.debug("insertando un plan--------insertaPlan()");
		logger.debug("descripcionRamo->"+ descripcionRamo);
		logger.debug("dsPlanLista->"+ dsPlanLista);
		plan = new PlanVO();
		List<ProductoVO> productosLista =(List<ProductoVO>)session.get("PRODUCTOS");
		for(ProductoVO pro: productosLista){
			if(pro.getDsRamo().equals(descripcionRamo)){
				plan.setCdRamo(pro.getCdRamoP());
			}
		}
		plan.setDsPlan(dsPlanLista);
		logger.debug("plan.getCdRamo" + plan.getCdRamo());
		logger.debug("plan.getDsPlan" + plan.getDsPlan());
		logger.debug("plan"+ plan);
		manttoManager.insertaPlan(plan);
		success=true;
		return SUCCESS;
		
		
	}
	/**
	 * Metodo que carga una solo objeto tipo PlanVO.
	 * @return
	 * @throws Exception
	 */
	public String obtienePlan()throws Exception{
		logger.debug("cargando un solo Plan--------------obtienePlan()");
		logger.debug("obtienePlan()----id="+id);
		logger.debug("obtienePlan()----cdRamo="+ cdRamo);
		cargaPlan= new PlanVO();
		cargaPlan = manttoManager.getPlan(id, cdRamo);
		logger.debug("cargaPlan"+ cargaPlan);		
		return SUCCESS;
	}
	
	/**
	 * Metodo que edita un plan.
	 * @return
	 * @throws Exception
	 */
	public String editaPlan() throws Exception{
		
		logger.debug("editando un plan-------------editaPlan()");
		logger.debug("cdRamo="+ cdRamo);
		logger.debug("dsPlan="+ dsPlan);		        
		logger.debug("cdPlan" + cdPlan);
		
		
		planEdit= new PlanVO();
		
		planEdit.setCdPlan(cdPlan);
		planEdit.setCdRamo(cdRamo);
		planEdit.setDsPlan(dsPlan);		      		       
		logger.debug("planEdit" + planEdit);
		manttoManager.editaPlan(planEdit);
		success=true;
		
		return SUCCESS;		
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDescripcionRamo() {
		return descripcionRamo;
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
	 * @param descripcionRamo
	 */
	public void setDescripcionRamo(String descripcionRamo) {
		this.descripcionRamo = descripcionRamo;
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
	public PlanVO getCargaPlan() {
		return cargaPlan;
	}
	/**
	 * 
	 * @param cargaPlan
	 */
	public void setCargaPlan(PlanVO cargaPlan) {
		this.cargaPlan = cargaPlan;
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
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 
	 * @return
	 */
	public PlanVO getPlan() {
		return plan;
	}
	/**
	 * 
	 * @param plan
	 */
	public void setPlan(PlanVO plan) {
		this.plan = plan;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsPlanLista() {
		return dsPlanLista;
	}
	/**
	 * 
	 * @param dsPlanLista
	 */
	public void setDsPlanLista(String dsPlanLista) {
		this.dsPlanLista = dsPlanLista;
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<ProductoVO> getProductos() {
		return productos;
	}
	/**
	 * 
	 * @param productos
	 */
	public void setProductos(ArrayList<ProductoVO> productos) {
		this.productos = productos;
	}
	/**
	 * 
	 * @param manttoManager
	 */
	public void setManttoManager(ManttoManager manttoManager) {
		this.manttoManager = manttoManager;
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
	 * Metodo agregado por herencia de padre (sin uso).
	 */
	public void prepare() throws Exception {
		
	}
	
	
	
	
	
	

}
