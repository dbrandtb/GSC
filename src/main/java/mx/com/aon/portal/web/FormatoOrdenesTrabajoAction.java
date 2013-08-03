package mx.com.aon.portal.web;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.FormatoOrdenesTrabajoVO;
import mx.com.aon.portal.service.FormatoOrdenesTrabajoManager;


import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;


/**
 * Action que atiende las peticiones de abm de la pantalla formato ordenes de trabajo.
 *
 */
@SuppressWarnings("serial")
public class FormatoOrdenesTrabajoAction extends ActionSupport {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(FormatoOrdenesTrabajoAction.class);	

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 */
	private transient FormatoOrdenesTrabajoManager formatoOrdenesTrabajoManager;

	@SuppressWarnings("unchecked")
	private List mEstructuraList;
	
	private String cdFormatoOrden;
	private String dsFormatoOrden;
	private String descripcionEscapedJavaScript;
	
	private boolean success;
	
 /**
	 * Elimina un registro de la grilla previamente seleccionado
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.getFormatoOrdenesTrabajoManager().borrarFormatoOrdenesTrabajo(cdFormatoOrden);
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
   * Metodo que atiende una peticion de insertar o actulizar un formato ordenes trabajo
	 * 
	 * @return success
	 * 
	 * @throws Exception
   */
	public String cmdGuardarClick()throws Exception
	{
		String messageResult = "";
        try
        {          	
        	FormatoOrdenesTrabajoVO formatoOrdenesTrabajoVO = new FormatoOrdenesTrabajoVO();
        	formatoOrdenesTrabajoVO.setCdFormatoOrden(cdFormatoOrden);
        	formatoOrdenesTrabajoVO.setDsFormatoOrden(dsFormatoOrden);
        	
        	messageResult = formatoOrdenesTrabajoManager.guardarFormatoOrdenesTrabajo(formatoOrdenesTrabajoVO);
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
   * Metodo que atiende una peticion de obtener un formato de ordenes de trabajo
	 * 
	 * @return success
	 * 
	 * @throws Exception
   */
	@SuppressWarnings("unchecked")
	public String cmdGetClick()throws Exception
	{      
		try
		{
			mEstructuraList=new ArrayList<FormatoOrdenesTrabajoVO>();
			FormatoOrdenesTrabajoVO formatoOrdenesTrabajoVO=formatoOrdenesTrabajoManager.getFormatoOrdenesTrabajo(cdFormatoOrden);
			mEstructuraList.add(formatoOrdenesTrabajoVO);
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
   * Metodo que atiende una peticion de copiar un formato de ordenes de trabajo
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */
	public String cmdCopiarClick() throws Exception{
		String messageResult = "";
		try{
			
			FormatoOrdenesTrabajoVO formatoOrdenesTrabajoVO = new FormatoOrdenesTrabajoVO();
			formatoOrdenesTrabajoVO.setCdFormatoOrden(cdFormatoOrden);
        	
			messageResult = this.getFormatoOrdenesTrabajoManager().copiarFormatoOrdenesTrabajo(cdFormatoOrden);
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
	 * Metodo que redirecciona a la pantalla Configurar Ordenes de Trabajo
	 * 
	 * @return String
	 */
	public String cmdIrConfigurarOrdenesTrabajo(){
		 return "configurarOrdenesTrabajo";
	 }
	
	@SuppressWarnings("unchecked")
	public List getMEstructuraList() {return mEstructuraList;}
	
    public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
	@SuppressWarnings("unchecked")
	public void setMEstructuraList(List estructuraList) {
		mEstructuraList = estructuraList;
	}

	public String getCdFormatoOrden() {
		return cdFormatoOrden;
	}

	public void setCdFormatoOrden(String cdFormatoOrden) {
		this.cdFormatoOrden = cdFormatoOrden;
	}

	public String getDsFormatoOrden() {
		return dsFormatoOrden;
	}

	public void setDsFormatoOrden(String dsFormatoOrden) {
		this.dsFormatoOrden = dsFormatoOrden;
	}

	public void setFormatoOrdenesTrabajoManager(
			FormatoOrdenesTrabajoManager formatoOrdenesTrabajoManager) {
		this.formatoOrdenesTrabajoManager = formatoOrdenesTrabajoManager;
	}

	public FormatoOrdenesTrabajoManager getFormatoOrdenesTrabajoManager() {
		return formatoOrdenesTrabajoManager;
	}


	public String getDescripcionEscapedJavaScript() {
		return descripcionEscapedJavaScript;
	}


	public void setDescripcionEscapedJavaScript(String descripcionEscapedJavaScript) {
		//this.descripcionEscapedJavaScript = descripcionEscapedJavaScript;
		this.descripcionEscapedJavaScript = StringEscapeUtils.escapeJavaScript(descripcionEscapedJavaScript);
	}
	

}