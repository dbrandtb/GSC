
package mx.com.aon.portal.web;

import mx.com.aon.portal.service.AsociarFormatosManager;
import mx.com.gseguros.exception.ApplicationException;



import org.apache.log4j.Logger;


import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Asociar Formatos
 * 
 */
@SuppressWarnings("serial")
public class AsociarFormatosAction extends ActionSupport {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AsociarFormatosAction.class);	

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 */
	private transient AsociarFormatosManager asociarFormatosManager;

	private String cdAsocia;
	
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
			messageResult = obtenAsociarFormatosManager().borrarAsociarFormatos(cdAsocia);	
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
	
	public String cmdIrAsociarFormatosOrdenesTrabajo(){
		 return "codigoAsociado";
	 }
	


	public AsociarFormatosManager obtenAsociarFormatosManager() {
		return asociarFormatosManager;
	}


	public void setAsociarFormatosManager(
			AsociarFormatosManager asociarFormatosManager) {
		this.asociarFormatosManager = asociarFormatosManager;
	}

	public String getCdAsocia() {
		return cdAsocia;
	}


	public void setCdAsocia(String cdAsocia) {
		this.cdAsocia = cdAsocia;
	}


	public boolean getSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}
	

}