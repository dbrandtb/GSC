package mx.com.aon.portal.web;


import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

import mx.com.aon.portal.service.RazonesCancelacionProductoManager;
import mx.com.aon.core.ApplicationException;


/**
 * Action que atiende las peticiones de abm la pantalla de razones de cancelacion productos.
 * 
 */
public class RazonesCancelacionProductoAction extends ActionSupport {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaRazonesCancelacionProductoAction.class);
	
	private static final long serialVersionUID = 16986544465444444L;

	private String cdRamo;
	private String cdRazon;
	private String cdMetodo;
	
	private transient RazonesCancelacionProductoManager razonesCancelacionProductoManager;

	private boolean success;

	/**
	 * Metodo <code>cmdBorrarClick</code> borra razones de cancelacion de productos seleccionado.
	 * 
	 * @return success
	 *  
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")  
	public String cmdBorrarClick() throws Exception
	{
		String messageResult = "";
		try
		{
			messageResult = razonesCancelacionProductoManager.borrarRazonesCancelacionProducto(cdRazon, cdRamo, cdMetodo);
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
	
	public String cmdIrAgregarCancelacionProductoClick(){
		return "";
	}

    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getCdRazon() {
		return cdRazon;
	}

	public void setCdRazon(String cdRazon) {
		this.cdRazon = cdRazon;
	}

	public void setRazonesCancelacionProductoManager(
			RazonesCancelacionProductoManager razonesCancelacionProductoManager) {
		this.razonesCancelacionProductoManager = razonesCancelacionProductoManager;
	}

	public String getCdMetodo() {
		return cdMetodo;
	}

	public void setCdMetodo(String cdMetodo) {
		this.cdMetodo = cdMetodo;
	}

}