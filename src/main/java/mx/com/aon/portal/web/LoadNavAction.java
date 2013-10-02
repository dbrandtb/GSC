package mx.com.aon.portal.web;

import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.ItemVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.NavigationManager;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class LoadNavAction extends ActionSupport implements SessionAware{

	private static Logger logger = Logger.getLogger(LoadNavAction.class);
	
	/**
	 * UID por defecto
	 */
	private static final long serialVersionUID = -4890700341019112495L;
	
	@SuppressWarnings("unchecked")   // Manejo de Map controlado
	private Map session;
	
	private List<ItemVO> listaMenu;
	
	private NavigationManager navigationManager;
	
	public String execute(){
		
		try {
			
			UserVO userVO = (UserVO)session.get("userVO");
			listaMenu = navigationManager.getMenuNavegacion( userVO.getPerfil() );
			if (logger.isDebugEnabled()) {
				logger.debug("Elementos del menu navegacion:");
				for( ItemVO item : listaMenu ){
					logger.debug(item.getTexto() + " -> " + item.getId());
				}
			}
			
		} catch (ApplicationException e) {
			addActionError(e.getMessage());
			return ERROR;
		}
		
		return SUCCESS;
	}

	// Getters and Setters

	@SuppressWarnings("unchecked")  // Manejo de Map controlado
	public void setSession(Map session) {this.session = session;}

	public void setNavigationManager(NavigationManager navigationManager) {	this.navigationManager = navigationManager;}

	public List<ItemVO> getListaMenu() {return listaMenu;}

	public void setListaMenu(List<ItemVO> listaMenu) {this.listaMenu = listaMenu;}
	
}
