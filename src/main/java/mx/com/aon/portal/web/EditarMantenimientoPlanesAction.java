package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.portal.model.MantenimientoPlanVO;
import mx.com.aon.portal.service.MantenimientoPlanesManager;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *   Action que atiende las peticiones para editar un mantenimiento de planes.
 * 
 */
public class EditarMantenimientoPlanesAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 8106870262330598802L;
	private String total;
	private String codigoPlan;
	private String codigoRamo;
	private String descripcionPlan;
	private List<MantenimientoPlanVO> mantenimientoPlanVO = new ArrayList<MantenimientoPlanVO>();
	@SuppressWarnings("unused")
	private transient MantenimientoPlanesManager mantenimientoPlanesManager;
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(EditarMantenimientoPlanesAction.class);
	private boolean success;
	@SuppressWarnings({ "unchecked", "unused" })
	private Map session;
	
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		try {
			MantenimientoPlanVO mantenimientoPlanVO = new MantenimientoPlanVO();
			mantenimientoPlanVO.setCodigoPlan(codigoPlan);
			mantenimientoPlanVO.setCodigoRamo(codigoRamo);
			mantenimientoPlanVO.setDescripcionPlan(descripcionPlan);
			//mantenimientoPlanesManager.setPlan(mantenimientoPlanVO);
		}catch (Exception e){
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}

	/**
	 * Agregado al implementar SessionAware para manejo de atributos de session
	 */
	@SuppressWarnings("unchecked")  // Manejo de Map controlado
	public void setSession(Map session) {this.session = session;}
	
	public void setMantenimientoPlanesManager(MantenimientoPlanesManager mantenimientoPlanesManager) {this.mantenimientoPlanesManager = mantenimientoPlanesManager;}

	public void setMantenimientoPlanVO(List<MantenimientoPlanVO> mantenimientoPlanVO) {
		this.mantenimientoPlanVO = mantenimientoPlanVO;
	}

	public List<MantenimientoPlanVO> getMantenimientoPlanVO() {
		return mantenimientoPlanVO;
	}

	public void setTotal(String total) {this.total = total;}
	public String getTotal() {return this.total;}

	public void setCodigoPlan (String codigoPlan) {this.codigoPlan = codigoPlan;}
	public String getCodigoPlan () {return this.codigoPlan;}
	
	public void setCodigoRamo (String codigoRamo) {this.codigoRamo = codigoRamo;}
	public String getCodigoRamo () {return this.codigoRamo;}
	public void setDescripcionPlan (String descripcionPlan) {this.descripcionPlan = descripcionPlan;}
	public String getDescripcionPlan () {return this.getDescripcionPlan();}
}
