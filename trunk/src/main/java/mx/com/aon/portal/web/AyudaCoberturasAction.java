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

import mx.com.aon.portal.model.AyudaCoberturasVO;
import mx.com.aon.portal.service.AyudaCoberturasManager;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Ayuda de Coberturas.
 * 
 */
public class AyudaCoberturasAction extends ActionSupport {

	private static final long serialVersionUID = 19873215465454L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AyudaCoberturasAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AyudaCoberturasManager ayudaCoberturasManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	private List<AyudaCoberturasVO> mEstructuraList;
	private String cdGarantiaxCia;
	private String cdUnieco;
	private String cdTipram;
	private String cdSubram;
	private String cdRamo;
	private String cdGarant;
	private String langCode;
	private String dsAyuda;
	private boolean success;
	private String codigoProductoH;
	private String langCodeC;
	private String cdSubramId;

	
	

	/**
	 *  Metodo que genera la copia de una ayuda de coberturas seleccionada del geid.
	 *  
	 * @return success
	 *  
	 * @throws Exception
	 */
	public String cmdCopiarClick() throws Exception{
		String messageResult = "";
		try{
			
			AyudaCoberturasVO ayudaCoberturasVO = new AyudaCoberturasVO();
			ayudaCoberturasVO.setCdGarantiaxCia(cdGarantiaxCia);
			ayudaCoberturasVO.setCdUnieco(cdUnieco);
        	ayudaCoberturasVO.setCdSubram(cdSubram);
        	ayudaCoberturasVO.setCdGarant(cdGarant);
        	ayudaCoberturasVO.setCdTipram(cdTipram);        	
        	ayudaCoberturasVO.setCdRamo(cdRamo); 
        	ayudaCoberturasVO.setLangCode(langCode);
			messageResult = ayudaCoberturasManager.copiarAyudaCoberturas(ayudaCoberturasVO);		
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
			messageResult = ayudaCoberturasManager.borrarAyudaCoberturas(getCdGarantiaxCia());	
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
    public String cmdInsertarClick()throws Exception
    {
		String messageResult = "";
        try
        {        	
        	AyudaCoberturasVO ayudaCoberturasVO = new AyudaCoberturasVO();
        	String _cdGarantiaxCia = " ";
        	ayudaCoberturasVO.setCdGarantiaxCia(_cdGarantiaxCia);
        	ayudaCoberturasVO.setCdUnieco(cdUnieco);
        	ayudaCoberturasVO.setCdTipram(cdTipram);
        	ayudaCoberturasVO.setCdSubram(cdSubram);
        	ayudaCoberturasVO.setCdRamo(cdRamo);
        	ayudaCoberturasVO.setCdGarant(cdGarant);
        	ayudaCoberturasVO.setLangCode(langCode);
        	ayudaCoberturasVO.setDsAyuda(dsAyuda);
        	messageResult = ayudaCoberturasManager.insertarAyudaCoberturas(ayudaCoberturasVO);
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
	 * Metodo que atualiza una ayuda de coberturas modificada.
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
        	AyudaCoberturasVO ayudaCoberturasVO = new AyudaCoberturasVO();
        	ayudaCoberturasVO.setCdGarantiaxCia(cdGarantiaxCia);
        	ayudaCoberturasVO.setCdUnieco(cdUnieco);
        	ayudaCoberturasVO.setCdTipram(cdTipram);
        	ayudaCoberturasVO.setCdSubram(cdSubramId);
        	ayudaCoberturasVO.setCdRamo(codigoProductoH);
        	ayudaCoberturasVO.setCdGarant(cdGarant);
        	ayudaCoberturasVO.setLangCode(langCodeC);
        	ayudaCoberturasVO.setDsAyuda(dsAyuda);
        	messageResult = ayudaCoberturasManager.guardarAyudaCoberturas(ayudaCoberturasVO);
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
	 * Metodo que obtiene una ayuda de coberturas selccionada en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetClick()throws Exception
	{
		try
		{
			mEstructuraList=new ArrayList<AyudaCoberturasVO>();
			AyudaCoberturasVO ayudaCoberturasVO=ayudaCoberturasManager.getAyudaCoberturas(cdGarantiaxCia);
			mEstructuraList.add(ayudaCoberturasVO);
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
	
	public List<AyudaCoberturasVO> getMEstructuraList() {return mEstructuraList;}

	public AyudaCoberturasManager getAyudaCoberturasManager() {
		return ayudaCoberturasManager;
	}

    public void setAyudaCoberturasManager(AyudaCoberturasManager ayudaCoberturasManager) {
        this.ayudaCoberturasManager = ayudaCoberturasManager;
    }

    public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setMEstructuraList(List<AyudaCoberturasVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public String getCdGarantiaxCia() {
		return cdGarantiaxCia;
	}


	public void setCdGarantiaxCia(String cdGarantiaxCia) {
		this.cdGarantiaxCia = cdGarantiaxCia;
	}

	public String getCdUnieco() {
		return cdUnieco;
	}

	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}

	public String getCdTipram() {
		return cdTipram;
	}

	public void setCdTipram(String cdTipram) {
		this.cdTipram = cdTipram;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getCdGarant() {
		return cdGarant;
	}

	public void setCdGarant(String cdGarant) {
		this.cdGarant = cdGarant;
	}

	public String getDsAyuda() {
		return dsAyuda;
	}

	public void setDsAyuda(String dsAyuda) {
		this.dsAyuda = dsAyuda;
	}

	public String getCdSubram() {
		return cdSubram;
	}

	public void setCdSubram(String cdSubram) {
		this.cdSubram = cdSubram;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}
	public String getCodigoProductoH() {
		return codigoProductoH;
	}

	public void setCodigoProductoH(String codigoProductoH) {
		this.codigoProductoH = codigoProductoH;
	}
	public String getLangCodeC() {
		return langCodeC;
	}

	public void setLangCodeC(String langCodeC) {
		this.langCodeC = langCodeC;
	}

	public String getCdSubramId() {
		return cdSubramId;
	}

	public void setCdSubramId(String cdSubramId) {
		this.cdSubramId = cdSubramId;
	}

}