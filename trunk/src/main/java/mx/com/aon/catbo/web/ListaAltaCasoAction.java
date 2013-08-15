package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;

import mx.com.aon.catbo.model.PersonaVO;
import mx.com.aon.portal.model.EmpresaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.ConnectionCallInterceptor;
import mx.com.aon.catbo.service.AltaCasosManager;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

public class ListaAltaCasoAction extends AbstractListAction implements SessionAware {
	protected static ThreadLocal<UserVO> localUser = new ThreadLocal<UserVO>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1137878786546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaAltaCasoAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AltaCasosManager altaCasosManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo VO
	 * con los valores de la consulta.
	 */
	private List<PersonaVO> mPersonaList;
		
	private String pv_cdperson_i;
	
	
	private Map session;
	
	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}


	/**
	 * Metodo que realiza una búsqueda en base a criterios de búsquedas
	 *  
	 * @param 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetPersonaCasoClick()throws Exception
	{
		try
		{
			mPersonaList=new ArrayList<PersonaVO>();
			PersonaVO personaVO = this.altaCasosManager.obtenerPersonCaso(pv_cdperson_i);			
			
			UserVO userVO = (UserVO)session.get("USUARIO");

			if(userVO != null){
                logger.debug("obteniendo el cdElemento del usuario logueado: " + userVO.getEmpresa().getElementoId());
                personaVO.setCdElemento(userVO.getEmpresa().getElementoId());
			}
			
			mPersonaList.add(personaVO);
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
	

	

	
	public AltaCasosManager obtenAltaCasosManager() {
		return altaCasosManager;
	}
	public void setAltaCasosManager(AltaCasosManager altaCasosManager) {
		this.altaCasosManager = altaCasosManager;
	}
	public List<PersonaVO> getMPersonaList() {
		return mPersonaList;
	}
	public void setMPersonaList(List<PersonaVO> personaList) {
		mPersonaList = personaList;
	}
	public String getPv_cdperson_i() {
		return pv_cdperson_i;
	}
	public void setPv_cdperson_i(String pv_cdperson_i) {
		this.pv_cdperson_i = pv_cdperson_i;
	}



	
}
