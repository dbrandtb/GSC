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

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.BackBoneResultVO;
import mx.com.aon.portal.model.MotivoCancelacionVO;
import mx.com.aon.portal.model.RequisitoCancelacionVO;
import mx.com.aon.portal.service.MotivosCancelacionManager;



import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;


/**
 * Action que atiende las peticiones de abm la pantalla Motivos de Cancelacion.
 * 
 */
@SuppressWarnings("serial")
public class MotivosCancelacionAction extends ActionSupport {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(MotivosCancelacionAction.class);
	
	private static final String CANCELACION_BORRADA = "borrar";

	private transient MotivosCancelacionManager motivosCancelacionManager;

	@SuppressWarnings("unchecked")
	private List mEstructuraList;
	
	private String cdRazon;
	private String cdAdvert;
	
	private String codRazon;
    private String resultMessage;
    private String dsRazon;

    private MotivoCancelacionVO motivoCancelacionVO;
	
	private boolean success;
	

	/**
	 * Metodo <code>cmdBorrarClickMc</code> borra motivos de cancelacion elegida en el grid de esta pantalla.
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */
	public String cmdBorrarClickMc() throws Exception{
		try{
			resultMessage = this.getMotivosCancelacionManager().borrarMotivosCancelacion(cdRazon);
			addActionMessage(resultMessage);
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
	 * Metodo <code>cmdBorrarClickRc</code> borra requisitos de cancelacion elegida en el grid de esta pantalla.
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */
	public String cmdBorrarClickRc() throws Exception{
		try{
			resultMessage = this.getMotivosCancelacionManager().borrarRequisitoCancelacion(cdRazon,cdAdvert);
			addActionMessage(resultMessage);
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
	 * Metodo <code>cmdGuardarClick</code> Salva la informacion de la pantalla Motivos de Cancelacion.
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */
	public String cmdGuardarClick()throws Exception
	{
        try
        {          	
        	logger.debug("----> motivoCancelacionVO : " + motivoCancelacionVO);
        	motivoCancelacionVO = obtieneMotivoCancelacionGuardarList(motivoCancelacionVO);
        	logger.debug("----> motivoCancelacionVO final : " + motivoCancelacionVO);
            BackBoneResultVO backBoneResultVO = motivosCancelacionManager.guardarMotivosCancelacion(motivoCancelacionVO);
            codRazon = backBoneResultVO.getOutParam();
            addActionMessage(backBoneResultVO.getMsgText());
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
	 * Se obtiene una lista con las cancelaciones que deben de guardarse
	 * @param motivoCancelacionVO2
	 * @return
	 */
    private MotivoCancelacionVO obtieneMotivoCancelacionGuardarList(
			MotivoCancelacionVO motivoCancelacionVO2) {
    	logger.debug("----> obtieneMotivoCancelacionGuardarList");
    	List<RequisitoCancelacionVO> listRequisitoCancelacionVO = null;
    	
    	if (motivoCancelacionVO2.getRequisitoCancelacionVOs() != null &&
    			!motivoCancelacionVO2.getRequisitoCancelacionVOs().isEmpty()) {
	    	for (RequisitoCancelacionVO requisitoCancelacionVO : motivoCancelacionVO2.getRequisitoCancelacionVOs()) {
	    		if (requisitoCancelacionVO != null) {
		    		if (listRequisitoCancelacionVO == null) {
		    			listRequisitoCancelacionVO = new ArrayList<RequisitoCancelacionVO>();
		    		}
		    		
		    		if (!CANCELACION_BORRADA.equalsIgnoreCase(requisitoCancelacionVO.getCdAdvert())) {
		    			listRequisitoCancelacionVO.add(requisitoCancelacionVO);
		    		}
	    		}
	    	}
	    	
	    	motivoCancelacionVO2.setRequisitoCancelacionVOs(listRequisitoCancelacionVO);
    	}
    	
		return motivoCancelacionVO2;
	}


	/**
	 * Metodo <code>cmdGuardarNuevoClick</code> Agrega nuevo mativos de cancelacion desde Pantalla.
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */
    public String cmdGuardarNuevoClick()throws Exception
    {
        try
        {
         logger.debug("cmdGuardarNuevoClick ----> motivoCancelacionVO : " + motivoCancelacionVO);
         motivoCancelacionVO = obtieneMotivoCancelacionGuardarList(motivoCancelacionVO);
     	 logger.debug("cmdGuardarNuevoClick ----> motivoCancelacionVO final : " + motivoCancelacionVO);
       	 BackBoneResultVO backBoneResultVO = motivosCancelacionManager.insertarMotivosCancelacion(motivoCancelacionVO);
       	 codRazon = backBoneResultVO.getOutParam();
       	 addActionMessage(backBoneResultVO.getMsgText());
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
	 * Metodo <code>cmdGetClick</code> obtiene nuevos motivos de cancelacion.
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetClickMotivoCancelacion()throws Exception
	{      
		try
		{
			mEstructuraList=new ArrayList<MotivoCancelacionVO>();
			MotivoCancelacionVO motivoCancelacionVO=motivosCancelacionManager.getMotivosCancelacion(cdRazon);
			mEstructuraList.add(motivoCancelacionVO);
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
    
	 public String cmdIrConfigurarMotivosCancelacion(){
		 return "codigoRazon";
	 }
	 
	 public String cmdRegresarMotivosCancelacion(){
			return "motivosCancelacion";
	 }
			 
	@SuppressWarnings("unchecked")
	public List getMEstructuraList() {return mEstructuraList;}
	
	@SuppressWarnings("unchecked")
	public void setMEstructuraList(List estructuraList) {
		mEstructuraList = estructuraList;
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public MotivosCancelacionManager getMotivosCancelacionManager() {
		return motivosCancelacionManager;
	}

	public void setMotivosCancelacionManager(
			MotivosCancelacionManager motivosCancelacionManager) {
		this.motivosCancelacionManager = motivosCancelacionManager;
	}

	public String getCdRazon() {
		return cdRazon;
	}

	public void setCdRazon(String cdRazon) {
		this.cdRazon = cdRazon;
	}

	public MotivoCancelacionVO getMotivoCancelacionVO() {
		return motivoCancelacionVO;
	}

	public void setMotivoCancelacionVO(MotivoCancelacionVO motivoCancelacionVO) {
		this.motivoCancelacionVO = motivoCancelacionVO;
	}

	public String getCdAdvert() {
		return cdAdvert;
	}

	public void setCdAdvert(String cdAdvert) {
		this.cdAdvert = cdAdvert;
	}

	public String getCodRazon() {
		return codRazon;
	}

	public void setCodRazon(String codRazon) {
		this.codRazon = codRazon;
	}

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }


	public String getDsRazon() {
		return dsRazon;
	}


	public void setDsRazon(String dsRazon) {
		this.dsRazon = dsRazon;
	}
}