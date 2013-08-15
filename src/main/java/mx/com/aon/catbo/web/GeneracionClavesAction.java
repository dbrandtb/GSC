package mx.com.aon.catbo.web;



import mx.com.aon.core.ApplicationException;

import java.util.List;
import java.util.ArrayList;

import mx.com.aon.catbo.service.GeneracionClavesManager;
import mx.com.aon.catbo.model.ClavesVO;
import mx.com.aon.core.ApplicationException;


import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Administracion Tipos de Faxes.
 * 
 */
@SuppressWarnings("serial")
public class GeneracionClavesAction extends ActionSupport {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(GeneracionClavesAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient GeneracionClavesManager generacionClavesManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	private List<ClavesVO> grillaGeneracionClavesList;

	private String idGenerador;
	private String descripcion;
	private String idParam;
	private String valor;
	
	
	private boolean success;

		

	
	/**
	 * Metodo que atualiza un registro existente de las tablas sec_passgen_params y sec_generadores_pass
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */

	public String cmdGuardarClick()throws Exception
	{ 
		String messageResult = "";
        try
        {	
          	messageResult = this.generacionClavesManager.actualizarValores(grillaGeneracionClavesList);
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


	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public GeneracionClavesManager obtenGeneracionClavesManager() {
		return generacionClavesManager;
	}


	public void setGeneracionClavesManager(
			GeneracionClavesManager generacionClavesManager) {
		this.generacionClavesManager = generacionClavesManager;
	}


	public String getIdGenerador() {
		return idGenerador;
	}


	public void setIdGenerador(String idGenerador) {
		this.idGenerador = idGenerador;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getIdParam() {
		return idParam;
	}


	public void setIdParam(String idParam) {
		this.idParam = idParam;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}


	public List<ClavesVO> getGrillaGeneracionClavesList() {
		return grillaGeneracionClavesList;
	}


	public void setGrillaGeneracionClavesList(
			List<ClavesVO> grillaGeneracionClavesList) {
		this.grillaGeneracionClavesList = grillaGeneracionClavesList;
	}
	

}