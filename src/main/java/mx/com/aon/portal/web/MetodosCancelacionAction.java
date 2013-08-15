
package mx.com.aon.portal.web;

import mx.com.aon.portal.model.MetodoCancelacionVO;
import mx.com.aon.portal.service.MetodosCancelacionManager;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;


/**
 * Action que atiende las peticiones de abm la pantalla de metodos de cancelacion.
 * 
 */
@SuppressWarnings("serial")
public class MetodosCancelacionAction extends ActionSupport {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(MetodosCancelacionAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient MetodosCancelacionManager metodosCancelacionManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo MetodoCancelacionVO
	 * con los valores de la consulta.
	 */
	@SuppressWarnings("unchecked")
	private List<MetodoCancelacionVO> mMetodoCancelacionList;

	private String cdMetodo;
	private String dsMetodo;
	private String pv_cdexprespndp_i;
	private String pv_cdexprespndt_i;
	
    private boolean success;


    /**
	 * Metodo <code>cmdAgregarGuardarClick</code> Agrega metodos de cancelacion.
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */
    public String cmdAgregarGuardarClick()throws Exception
    {
		String messageResult = "";
       try
        {
    	   MetodoCancelacionVO metodoCancelacionVO = new MetodoCancelacionVO();
    	   
    	   metodoCancelacionVO.setCdMetodo(cdMetodo);
    	   metodoCancelacionVO.setDsMetodo(dsMetodo);
    	   metodoCancelacionVO.setPv_cdexprespndp_i(null);
    	   metodoCancelacionVO.setPv_cdexprespndt_i(null);
    	   
    	   messageResult = metodosCancelacionManager.agregarGuardarMetodoCancelacion(metodoCancelacionVO);
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
	 * Metodo <code>cmdBorrarClick</code> Elimina Metodos de cancelacion .
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			MetodoCancelacionVO metodoCancelacionVO = new MetodoCancelacionVO();
	    	   
	    	metodoCancelacionVO.setCdMetodo(cdMetodo);
	        	
			messageResult = metodosCancelacionManager.borrarMetodoCancelacion(metodoCancelacionVO);
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
	 * Metodo <code>cmdGetClick</code> obtiene metodos de cancelacion.
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */
    public String cmdGetClick()throws Exception
	{

		try
		{
			mMetodoCancelacionList = new ArrayList<MetodoCancelacionVO>();
			MetodoCancelacionVO metodoCancelacionVO=metodosCancelacionManager.getMetodoCancelacion(cdMetodo);
			mMetodoCancelacionList.add(metodoCancelacionVO);
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
	
	
	public boolean getSuccess() {return success;}
	public void setSuccess(boolean success) {this.success = success;}
	
	public String getCdMetodo() {
		return cdMetodo;
	}
	public void setCdMetodo(String cdMetodo) {
		this.cdMetodo = cdMetodo;
	}
	public String getDsMetodo() {
		return dsMetodo;
	}
	public void setDsMetodo(String dsMetodo) {
		this.dsMetodo = dsMetodo;
	}

	public MetodosCancelacionManager obtenMetodosCancelacionManager() {
		return metodosCancelacionManager;
	}
	public void setMetodosCancelacionManager(
			MetodosCancelacionManager metodosCancelacionManager) {
		this.metodosCancelacionManager = metodosCancelacionManager;
	}
	public List<MetodoCancelacionVO> getMMetodoCancelacionList() {
		return mMetodoCancelacionList;
	}
	public void setMMetodoCancelacionList(
			List<MetodoCancelacionVO> metodoCancelacionList) {
		mMetodoCancelacionList = metodoCancelacionList;
	}
	public String getPv_cdexprespndp_i() {
		return pv_cdexprespndp_i;
	}
	public void setPv_cdexprespndp_i(String pv_cdexprespndp_i) {
		this.pv_cdexprespndp_i = pv_cdexprespndp_i;
	}
	public String getPv_cdexprespndt_i() {
		return pv_cdexprespndt_i;
	}
	public void setPv_cdexprespndt_i(String pv_cdexprespndt_i) {
		this.pv_cdexprespndt_i = pv_cdexprespndt_i;
	}
	
}