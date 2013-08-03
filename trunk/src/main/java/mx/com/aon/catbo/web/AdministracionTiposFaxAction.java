package mx.com.aon.catbo.web;



import mx.com.aon.core.ApplicationException;

import java.util.List;
import java.util.ArrayList;

import mx.com.aon.catbo.service.ArchivosManager;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.core.ApplicationException;


import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Administracion Tipos de Faxes.
 * 
 */
@SuppressWarnings("serial")
public class AdministracionTiposFaxAction extends ActionSupport {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AdministracionTiposFaxAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient ArchivosManager archivosManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	private List<FaxesVO> mAtributosFaxList;

	private String cdtipoar;
	private String cdatribu;
	private String dsAtribu;
	private String swFormat;
	private String nmLmax;
	private String nmLmin;
	private String swObliga;
	private String otTabVal;
	private String status;
	
	private boolean success;

		
	/**
	 * Metodo que elimina un registro .
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = archivosManager.borrarAtributosFax(cdtipoar, cdatribu);
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
	 * Metodo que obtiene un registro de la tabla TATRIARC.
	 * Atributos de Archivos
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetClick()throws Exception
	{
		try
		{
			mAtributosFaxList=new ArrayList<FaxesVO>();
			FaxesVO atributosFaxVO=archivosManager.getObtieneAtributosFax(cdtipoar, cdatribu);
			mAtributosFaxList.add(atributosFaxVO);
			success = true;
            return SUCCESS;
      /*  }catch(ApplicationException e){	
			success = false;
            addActionError(e.getMessage());
            return SUCCESS;*/
        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

	/**
	 * Metodo que gurada un registro en la tabla TATRIARC.
	 * Atributos de Archivos
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */

	public String cmdGuardarClick()throws Exception
	{ 
		String messageResult = "";
        try
        {	FaxesVO atributosFaxVO = new FaxesVO(); 
        	/*if(swObliga.equals("") || swObliga == null){
        		swObliga="0";
        	}else{
        		swObliga="1";
        	}*/
			  
	//	  if (cdatribu==)
           	messageResult = this.archivosManager.guardarAtributosFax(cdtipoar, cdatribu, dsAtribu, swFormat, nmLmax, nmLmin, swObliga, otTabVal, status);
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
	
	
	
	public String cmdActualizarClick()throws Exception
	{ 
		String messageResult = "";
        try
        {	//FaxesVO atributosFaxVO = new FaxesVO(); 
        	/*if(swObliga.equals("") || swObliga == null){
        		swObliga="0";
        	}else{
        		swObliga="1";
        	}*/
			  
	//	  if (cdatribu==)
           	messageResult = this.archivosManager.actualizarAtributosFax(cdtipoar, cdatribu, dsAtribu, swFormat, nmLmax, nmLmin, swObliga, otTabVal, status);
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
	 * Metodo que atualiza un registro existente de la tabla TATRIARC.
	 * Atributos de Archivos
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */

	public String cmdEditarGuardarClick()throws Exception
	{ 
		String messageResult = "";
        try
        {	FaxesVO atributosFaxVO = new FaxesVO(); 
          	messageResult = this.archivosManager.editarGuardarAtributosFax(cdtipoar, cdatribu, dsAtribu, swFormat, nmLmax, nmLmin, swObliga, otTabVal, status);
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

	public ArchivosManager getArchivosManager() {
		return archivosManager;
	}

	public void setArchivosManager(ArchivosManager archivosManager) {
		this.archivosManager = archivosManager;
	}

	public List<FaxesVO> getMAtributosFaxList() {
		return mAtributosFaxList;
	}

	public void setMAtributosFaxList(List<FaxesVO> atributosFaxList) {
		mAtributosFaxList = atributosFaxList;
	}



	public String getCdatribu() {
		return cdatribu;
	}

	public void setCdatribu(String cdatribu) {
		this.cdatribu = cdatribu;
	}

	public String getDsAtribu() {
		return dsAtribu;
	}

	public void setDsAtribu(String dsAtribu) {
		this.dsAtribu = dsAtribu;
	}

	public String getSwFormat() {
		return swFormat;
	}

	public void setSwFormat(String swFormat) {
		this.swFormat = swFormat;
	}

	public String getNmLmax() {
		return nmLmax;
	}

	public void setNmLmax(String nmLmax) {
		this.nmLmax = nmLmax;
	}

	public String getNmLmin() {
		return nmLmin;
	}

	public void setNmLmin(String nmLmin) {
		this.nmLmin = nmLmin;
	}

	public String getSwObliga() {
		return swObliga;
	}

	public void setSwObliga(String swObliga) {
		this.swObliga = swObliga;
	}

	public String getOtTabVal() {
		return otTabVal;
	}

	public void setOtTabVal(String otTabVal) {
		this.otTabVal = otTabVal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getCdtipoar() {
		return cdtipoar;
	}


	public void setCdtipoar(String cdtipoar) {
		this.cdtipoar = cdtipoar;
	}
	

}