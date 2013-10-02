package mx.com.aon.portal.web;


import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

import mx.com.aon.portal.service.RazonesCancelacionProductoManager;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Action que atiende las peticiones de abm la pantalla de razones de cancelacion.
 * 
 */
public class RazonesCancelacionProductoConfigurarAction extends ActionSupport {

	private static final long serialVersionUID = 1698657846550444L;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaRazonesCancelacionProductoAction.class);

	private String cdRamo;
	
	private String cdRazon;

	private String cdMetodo;
	
	private transient RazonesCancelacionProductoManager razonesCancelacionProductoManager;

	private boolean success;
	
	public String irRazonesCancelacionProductoClick()throws Exception
	{
		return "razonesCancelacion";
	}
	

    /**
	 * Metodo <code>cmdGuardarClick</code> Agrega razones de cancelacion.
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */
	@SuppressWarnings("unchecked")  
	public String cmdGuardarClick() throws Exception
	{
		String messageResult = "";
		try
		{
			messageResult = razonesCancelacionProductoManager.guardarConfiguracionRazonCancelacionProducto(cdRamo, cdRazon, cdMetodo);
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