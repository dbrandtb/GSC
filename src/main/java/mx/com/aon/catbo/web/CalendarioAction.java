package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.service.CalendarioManager;
import mx.com.aon.catbo.model.CalendarioVO;


import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

//import com.opensymphony.xwork2.ActionSupport;


public class CalendarioAction extends ActionSupport{
	
	private static final long serialVersionUID = 19873215465454L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(NumeracionCasosAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
		
	private transient CalendarioManager calendarioManager;
	
	private List<CalendarioVO> MCalendarioList;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans 
	 * con los valores de la consulta.
	 */
		
	private String codPais;
	private String yearCabecera;
	private String codMes;
	private String dia;
	private String descripcionDia;
	
	private boolean success;
	
	/**
	 * Metodo que elimina un registro seleccionado del grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarCalendarioClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = calendarioManager.borrarCalendario(codPais, yearCabecera, codMes, dia);
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
    public String cmdGuardarCalendarioClick()throws Exception
    {
		String messageResult = "";
        try
        {        	
        	CalendarioVO calendarioVO = new CalendarioVO();
        	
        	calendarioVO.setCodigoPais(codPais);
        	calendarioVO.setAnio(yearCabecera);
        	calendarioVO.setMes(codMes);
        	calendarioVO.setDia(dia);
        	calendarioVO.setDescripcionDia(descripcionDia);
        	
        	messageResult = calendarioManager.guardarCalendario(calendarioVO);
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
	
    
    public String cmdEditarCalendarioClick()throws Exception
    {
		String messageResult = "";
        try
        {        	
        	CalendarioVO calendarioVO = new CalendarioVO();
        	
        	calendarioVO.setCodigoPais(codPais);
        	calendarioVO.setAnio(yearCabecera);
        	calendarioVO.setMes(codMes);
        	calendarioVO.setDia(dia);
        	calendarioVO.setDescripcionDia(descripcionDia);
        	
        	messageResult = calendarioManager.editarCalendario(calendarioVO);
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
	 * Metodo que obtiene un Mecanismo de Tooltip selccionado en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetCalendarioClick()throws Exception
	{
		try
		{
			MCalendarioList=new ArrayList<CalendarioVO>();
			CalendarioVO calendarioVO=calendarioManager.getDiaMes(codPais, yearCabecera, codMes, dia);
			MCalendarioList.add(calendarioVO);
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

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		CalendarioAction.logger = logger;
	}

	/*public CalendarioManager getCalendarioManager() {
		return calendarioManager;
	}*/

	public void setCalendarioManager(CalendarioManager calendarioManager) {
		this.calendarioManager = calendarioManager;
	}

	public List<CalendarioVO> getMCalendarioList() {
		return MCalendarioList;
	}

	public void setMCalendarioList(List<CalendarioVO> calendarioList) {
		MCalendarioList = calendarioList;
	}


	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getDescripcionDia() {
		return descripcionDia;
	}

	public void setDescripcionDia(String descripcionDia) {
		this.descripcionDia = descripcionDia;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCodPais() {
		return codPais;
	}

	public void setCodPais(String codPais) {
		this.codPais = codPais;
	}

	public String getYearCabecera() {
		return yearCabecera;
	}

	public void setYearCabecera(String yearCabecera) {
		this.yearCabecera = yearCabecera;
	}

	public String getCodMes() {
		return codMes;
	}

	public void setCodMes(String codMes) {
		this.codMes = codMes;
	}

}