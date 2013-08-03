/*
 * AON
 * 
 * Creado el 22/02/2008 06:56:45 p.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.portal.web;

import mx.com.aon.portal.model.MecanismoDeTooltipVO;
import mx.com.aon.portal.service.MecanismoDeTooltipManager;
import mx.com.aon.portal.web.tooltip.ToolTipBean;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import java.util.ArrayList;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Ayuda de Coberturas.
 * 
 */
public class MecanismoDeTooltipAction extends ActionSupport {

	private static final long serialVersionUID = 19873215465454L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(MecanismoDeTooltipAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient MecanismoDeTooltipManager mecanismoDeTooltipManager;
	
	private transient MecanismoDeTooltipManager mecanismoDeTooltipManagerJdbcTemplate;

	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo MecanismoDeTooltipVO
	 * con los valores de la consulta.
	 */
	
	private List<MecanismoDeTooltipVO> mEstructuraList;
	private String idTitulo;
	private String dsTitulo;
	private String idObjeto;
	private String nbObjeto;
	private String fgTipoObjeto;
	private String nbEtiqueta;
	private String dsTooltip;
	private String fgAyuda;
	private String dsAyuda;
	private String lang_Code;
	private String nbCajaIzquieda;
	private String nbCajaDerecha;

	private boolean success;

	/**
	 *  Metodo que genera la copia de Mecanismo de Tooltip seleccionada del geid.
	 *  
	 * @return success
	 *  
	 * @throws Exception
	 */
	public String cmdCopiarClick() throws Exception{
		String messageResult = "";

		try{
			
			MecanismoDeTooltipVO mecanismoDeTooltipVO = new MecanismoDeTooltipVO();

        	mecanismoDeTooltipVO.setIdTitulo(idTitulo);
        	mecanismoDeTooltipVO.setIdObjeto(idObjeto);
        	mecanismoDeTooltipVO.setNbObjeto(nbObjeto);
        	mecanismoDeTooltipVO.setFgTipoObjeto(fgTipoObjeto);        	
        	mecanismoDeTooltipVO.setNbEtiqueta(nbEtiqueta); 
        	mecanismoDeTooltipVO.setDsTooltip(dsTooltip);
        	mecanismoDeTooltipVO.setFgAyuda(fgAyuda);
        	mecanismoDeTooltipVO.setDsAyuda(dsAyuda);
        	mecanismoDeTooltipVO.setLang_Code(lang_Code);
        	mecanismoDeTooltipVO.setNbCajaIzquieda(nbCajaIzquieda);
        	mecanismoDeTooltipVO.setNbCajaDerecha(nbCajaDerecha);
        	
			messageResult = mecanismoDeTooltipManagerJdbcTemplate.copiarMecanismoDeTooltipVO(mecanismoDeTooltipVO);		
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
	 * Metodo que elimina un registro seleccionado del grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = mecanismoDeTooltipManagerJdbcTemplate.borrarMecanismoDeTooltip(idObjeto, lang_Code);	
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
     * Metodo que inserta una nueva ayuda de cobertura.
     * 
     * @return success
     * 
     *@throws Exception
     */
    public String cmdGuardarClick()throws Exception
    {
		String messageResult = "";
        try
        {        	
        	MecanismoDeTooltipVO mecanismoDeTooltipVO = new MecanismoDeTooltipVO();
        	
			if (fgAyuda != null && fgAyuda.equals("on")) {
				fgAyuda="1"; }
	
				else {fgAyuda="0";}
        	
        	mecanismoDeTooltipVO.setIdTitulo(idTitulo);
        	mecanismoDeTooltipVO.setIdObjeto(idObjeto);
        	mecanismoDeTooltipVO.setNbObjeto(nbObjeto);
        	mecanismoDeTooltipVO.setFgTipoObjeto(fgTipoObjeto);
        	mecanismoDeTooltipVO.setNbEtiqueta(nbEtiqueta);
        	mecanismoDeTooltipVO.setDsTooltip(dsTooltip);
        	mecanismoDeTooltipVO.setFgAyuda(fgAyuda);
        	mecanismoDeTooltipVO.setDsAyuda(dsAyuda);
        	mecanismoDeTooltipVO.setLang_Code(lang_Code);
        	mecanismoDeTooltipVO.setNbCajaIzquieda(nbCajaIzquieda);
        	mecanismoDeTooltipVO.setNbCajaDerecha(nbCajaDerecha);
        	
        	messageResult = mecanismoDeTooltipManagerJdbcTemplate.agregarGuardarMecanismoDeTooltip(mecanismoDeTooltipVO);
        	ToolTipBean bean = ((ToolTipBean) ServletActionContext.getServletContext().getAttribute("toolTipName"));
        	if (bean != null) {
        		logger.debug("Removiendo ToolTips");
        		bean.removeKeyMap(idTitulo);
        	}
        	
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
	public String cmdGetClick()throws Exception
	{
		try
		{
			mEstructuraList=new ArrayList<MecanismoDeTooltipVO>();
			MecanismoDeTooltipVO mecanismoDeTooltipVO=mecanismoDeTooltipManagerJdbcTemplate.getMecanismoDeTooltipVO(idObjeto, lang_Code);
			mEstructuraList.add(mecanismoDeTooltipVO);
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
	
	public List<MecanismoDeTooltipVO> getMEstructuraList() {return mEstructuraList;}

    public void setMecanismoDeTooltipManager(MecanismoDeTooltipManager mecanismoDeTooltipManager) {
        this.mecanismoDeTooltipManager = mecanismoDeTooltipManager;
    }

    public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setMEstructuraList(List<MecanismoDeTooltipVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		MecanismoDeTooltipAction.logger = logger;
	}

	public String getIdTitulo() {
		return idTitulo;
	}

	public void setIdTitulo(String idTitulo) {
		this.idTitulo = idTitulo;
	}

	public String getDsTitulo() {
		return dsTitulo;
	}

	public void setDsTitulo(String dsTitulo) {
		this.dsTitulo = dsTitulo;
	}

	public String getIdObjeto() {
		return idObjeto;
	}

	public void setIdObjeto(String idObjeto) {
		this.idObjeto = idObjeto;
	}

	public String getNbObjeto() {
		return nbObjeto;
	}

	public void setNbObjeto(String nbObjeto) {
		this.nbObjeto = nbObjeto;
	}

	public String getFgTipoObjeto() {
		return fgTipoObjeto;
	}

	public void setFgTipoObjeto(String fgTipoObjeto) {
		this.fgTipoObjeto = fgTipoObjeto;
	}

	public String getNbEtiqueta() {
		return nbEtiqueta;
	}

	public void setNbEtiqueta(String nbEtiqueta) {
		this.nbEtiqueta = nbEtiqueta;
	}

	public String getDsTooltip() {
		return dsTooltip;
	}

	public void setDsTooltip(String dsTooltip) {
		this.dsTooltip = dsTooltip;
	}

	public String getFgAyuda() {
		return fgAyuda;
	}

	public void setFgAyuda(String fgAyuda) {
		this.fgAyuda = fgAyuda;
	}

	public String getDsAyuda() {
		return dsAyuda;
	}

	public void setDsAyuda(String dsAyuda) {
		this.dsAyuda = dsAyuda;
	}

	public String getLang_Code() {
		return lang_Code;
	}

	public void setLang_Code(String lang_Code) {
		this.lang_Code = lang_Code;
	}

	public String getNbCajaIzquieda() {
		return nbCajaIzquieda;
	}

	public void setNbCajaIzquieda(String nbCajaIzquieda) {
		this.nbCajaIzquieda = nbCajaIzquieda;
	}

	public String getNbCajaDerecha() {
		return nbCajaDerecha;
	}

	public void setNbCajaDerecha(String nbCajaDerecha) {
		this.nbCajaDerecha = nbCajaDerecha;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setMecanismoDeTooltipManagerJdbcTemplate(
			MecanismoDeTooltipManager mecanismoDeTooltipManagerJdbcTemplate) {
		this.mecanismoDeTooltipManagerJdbcTemplate = mecanismoDeTooltipManagerJdbcTemplate;
	}

}