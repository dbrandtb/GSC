package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.portal.model.MantenimientoPlanVO;
import mx.com.aon.portal.service.MantenimientoPlanesManager;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

@SuppressWarnings("serial")
public class CrearMantenimientoPlanesAction extends ActionSupport implements SessionAware {

	@SuppressWarnings("unused")
	private transient MantenimientoPlanesManager mantenimientoPlanesManager;
	private String codigoPlan;
	private String descripcionPlan;
	private boolean success;
	@SuppressWarnings({ "unchecked", "unused" })
	private Map session;
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(CrearMantenimientoPlanesAction.class);
	
	public String init () {
		return SUCCESS;
	}
	public String execute () {
		try {
			MantenimientoPlanVO mantenimientoPlanVO = new MantenimientoPlanVO ();
			mantenimientoPlanVO.setCodigoPlan(getCodigoPlan());
			mantenimientoPlanVO.setDescripcionPlan(getDescripcionPlan());
		}catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public void setSession (Map session) {this.session = session;}
	
	public void setSuccess (boolean success) {this.success = success;}
	public boolean getSuccess () {return this.success;}
	public void setMantenimientoPlanesManager (MantenimientoPlanesManager mantenimientoPlanesManager) {this.mantenimientoPlanesManager = mantenimientoPlanesManager;}
	public void setCodigoPlan (String codigoPlan) {this.codigoPlan = codigoPlan;}
	public String getCodigoPlan () {return this.codigoPlan;}
	public void setDescripcionPlan (String descripcionPlan) {this.descripcionPlan = descripcionPlan;}
	public String getDescripcionPlan () {return this.descripcionPlan;}
}
