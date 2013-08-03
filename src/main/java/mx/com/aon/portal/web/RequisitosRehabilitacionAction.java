package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import mx.com.aon.portal.service.RequisitosRehabilitacionManager;
import mx.com.aon.portal.model.RequisitoRehabilitacionVO;
import mx.com.aon.core.ApplicationException;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Requisitos de rehabilitacion
 * 
 */
@SuppressWarnings("serial")
public class RequisitosRehabilitacionAction extends ActionSupport {

    @SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(RequisitosRehabilitacionAction.class);    
	
		/**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 */
    private transient RequisitosRehabilitacionManager requisitosRehabilitacionManager;
	
	@SuppressWarnings("unchecked")
	private List mEstructuraList;
    private String cdRequisito;
    private String dsRequisito;
    private String cdElemento;
	private String cdUnieco;
    private String cdRamo;
	private String cdPerson;
    private String cdDocXcta;
    private String resultMessage;


	private boolean success;

	/**
	 * Elimina un registro de requisito de rehabilitacion la grilla previamente seleccionado
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked") 
	public String cmdBorrarClick() throws Exception
	{
		try{
			resultMessage = this.getRequisitosRehabilitacionManager().borrarRequisitoRehabilitacion(cdRequisito, cdUnieco, cdRamo, cdElemento, cdDocXcta);
            success = true;
            addActionMessage(resultMessage);
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
     * Metodo que atiende una peticion de insertar o actualizar un requisito de rehabilitacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdAgregarGuardarClick()throws Exception
	{
       try
        {          	
    	    RequisitoRehabilitacionVO requisitoRehabilitacionVO = new RequisitoRehabilitacionVO();
    	    requisitoRehabilitacionVO.setCdRequisito(cdRequisito);
    	    requisitoRehabilitacionVO.setDsRequisito(dsRequisito);
    	    requisitoRehabilitacionVO.setCdElemento(cdElemento);
    	    requisitoRehabilitacionVO.setCdUnieco(cdUnieco);
    	    requisitoRehabilitacionVO.setCdRamo(cdRamo);
    	    requisitoRehabilitacionVO.setCdPerson(cdPerson);
    	    requisitoRehabilitacionVO.setCdDocXcta(cdDocXcta);
        	
    	    resultMessage = requisitosRehabilitacionManager.agregarGuardarRequisitoRehabilitacion(requisitoRehabilitacionVO); 
            success = true;
            addActionMessage(resultMessage);
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
     * Metodo que atiende una peticion de obtener un requisito de rehabilitacion
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
			mEstructuraList=new ArrayList<RequisitoRehabilitacionVO>();
			RequisitoRehabilitacionVO requisitoRehabilitacionVO=requisitosRehabilitacionManager.getRequisitoRehabilitacion(cdRequisito);
			mEstructuraList.add(requisitoRehabilitacionVO);
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


	public RequisitosRehabilitacionManager getRequisitosRehabilitacionManager() {
		return requisitosRehabilitacionManager;
	}


	public void setRequisitosRehabilitacionManager(
			RequisitosRehabilitacionManager requisitosRehabilitacionManager) {
		this.requisitosRehabilitacionManager = requisitosRehabilitacionManager;
	}


	public String getCdRequisito() {
		return cdRequisito;
	}

	public void setCdRequisito(String cdRequisito) {
		this.cdRequisito = cdRequisito;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getDsRequisito() {
		return dsRequisito;
	}

	public void setDsRequisito(String dsRequisito) {
		this.dsRequisito = dsRequisito;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getCdUnieco() {
		return cdUnieco;
	}


	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getCdDocXcta() {
		return cdDocXcta;
	}

	public void setCdDocXcta(String cdDocXcta) {
		this.cdDocXcta = cdDocXcta;
	}

	@SuppressWarnings("unchecked")
	public List getMEstructuraList() {
		return mEstructuraList;
	}

	@SuppressWarnings("unchecked")
	public void setMEstructuraList(List estructuraList) {
		mEstructuraList = estructuraList;
	}

	
}
