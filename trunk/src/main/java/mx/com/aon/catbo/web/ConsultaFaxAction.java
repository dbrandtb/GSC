package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.ArchivosFaxesVO;
import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ConfigurarCompraTiempoVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.TareaVO;
import mx.com.aon.catbo.service.ArchivosFaxesManager;
import mx.com.aon.catbo.service.ConfigurarCompraTiempoManager;
import mx.com.aon.portal.service.PagedList;


import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

//import com.opensymphony.xwork2.ActionSupport;


public class ConsultaFaxAction extends ActionSupport{
	
	private static final long serialVersionUID = 19873215465454L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(NumeracionCasosAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
		
	private transient ArchivosFaxesManager archivosFaxesManager;
	
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans 
	 * con los valores de la consulta.
	 */
		
	private String dsarchivo;
	private String nmfax;
	private String nmpoliex;
	private String nmcaso;
	private String uregistro;
	private String feingreso;
	private int start;
	private int limit;
	private int cdVariable;
	private String flag;


    private List<FaxesVO> MListConsultaFax;
		
	private boolean success;
	
	
	public String cmdborrarFax() throws Exception{
		String messageResult = "";
		try{
			messageResult = archivosFaxesManager.BorrarFax("nmfax");
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
     * Metodo que inserta un nuevo dia calendario y descripcion
     * 
     * @return success
     * 
     *@throws Exception
     */
  /*  public String cmdGuardarClick()throws Exception
    {
		String messageResult = "";
        try
        {        	
        	     	
        	messageResult = configurarCompraTiempoManager.guardarCompraTiempo(tarea,codigoh,tdesde,thasta,codigounidadh,compra);
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
	*/
	
	
   	@SuppressWarnings("unchecked")
	public String cmdbuscarFax()throws Exception
	{
		try
		{
			
			PagedList pagedList = this.archivosFaxesManager.obtenerFaxes(dsarchivo, nmcaso, nmfax, nmpoliex,start ,limit );
			MListConsultaFax = pagedList.getItemsRangeList();
            int totalCount = pagedList.getTotalItems();      
            
			
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
	 * Metodo que obtiene un Mecanismo de Tooltip selccionado en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	/*public String cmdGetCalendarioClick()throws Exception
	{
		try
		{
			mCalendarioList=new ArrayList<CalendarioVO>();
			CalendarioVO calendarioVO=calendarioManager.getDiaMes(codPais, yearCabecera, codMes, dia);
			mCalendarioList.add(calendarioVO);
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
*/
	
   	public String cmdBorrarDetalleFax() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.archivosFaxesManager.BorrarDetalleFax(nmfax, nmcaso);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
          }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	
	public List<FaxesVO> getMListConsultaFax() {
		return MListConsultaFax;
	}

	public void setMListConsultaFax(List<FaxesVO> listConsultaFax) {
		MListConsultaFax = listConsultaFax;
	}

	public boolean isSuccess() {	
			return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	
	public ArchivosFaxesManager getArchivosFaxesManager() {
		return archivosFaxesManager;
	}

	

	public void setArchivosFaxesManager(ArchivosFaxesManager archivosFaxesManager) {
		this.archivosFaxesManager = archivosFaxesManager;
	}

	public String getDsarchivo() {
		return dsarchivo;
	}

	public void setDsarchivo(String cdtipoar) {
		this.dsarchivo = cdtipoar;
	}

	public String getNmfax() {
		return nmfax;
	}

	public void setNmfax(String nmfax) {
		this.nmfax = nmfax;
	}

	public String getNmpoliex() {
		return nmpoliex;
	}

	public void setNmpoliex(String nmpoliex) {
		this.nmpoliex = nmpoliex;
	}

	public String getNmcaso() {
		return nmcaso;
	}

	public void setNmcaso(String nmcaso) {
		this.nmcaso = nmcaso;
	}

	public String getUregistro() {
		return uregistro;
	}

	public void setUregistro(String uregistro) {
		this.uregistro = uregistro;
	}

	public String getFeingreso() {
		return feingreso;
	}

	public void setFeingreso(String feingreso) {
		this.feingreso = feingreso;
	}


	public int getStart() {
		return start;
	}


	public void setStart(int start) {
		this.start = start;
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public int getCdVariable() {
		return cdVariable;
	}


	public void setCdVariable(int cdVariable) {
		this.cdVariable = cdVariable;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}

	



}