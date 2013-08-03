/**
 * 
 */
package mx.com.aon.configurador.configworkflow.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mx.com.aon.portal.model.BaseObjectVO;
import com.opensymphony.xwork2.ActionContext;


/**
 * @author eflores
 * @date 18/07/2008
 *
 */
public class ConfiguraWorkflowPs3Action extends PrincipalConfigWorkflowAction{

	private static final transient Log log= LogFactory.getLog(ConfiguraWorkflowPs3Action.class);
	private static final long serialVersionUID = 1L;
	/**
     * Id del Proceso por Cuenta
     */
    private Long cdprocxcta;
    private String dsproceso;
    private String dsprocesoflujo;
    private String dselemen;
    private String dsramo;
    private String dsunieco;
    
    private Long cdpaso;
    private String dspaso;
    private Long cdestado;
    private Long cdpasoexito;
    private Long cdpasofracaso;
    private Long cdcondici;
    private Long cdtitulo;
    private Long nmorden;
    private String outParamText;
    
    private String dspasoEdit;
    private String dspasoexito;
    private String dspasofracaso;
    private String swfinal;
    
    private List<BaseObjectVO> pasosList;
    
    private boolean success;
    
    @SuppressWarnings("unchecked")
    public String execute() throws Exception{
        if (log.isDebugEnabled()) {
            log.debug("******ConfiguraWorkflowPs3Action.execute******");
            log.debug("cdprocxcta: "+cdprocxcta);
            log.debug("cdpaso: "+cdpaso);
            log.debug("cdtitulo: "+cdtitulo);
            log.debug("dspaso: "+dspaso);
            log.debug("dspasoexito: "+dspasoexito);
            log.debug("dspasofracaso: "+dspasofracaso);
            log.debug("cdpasoexito: "+cdpasoexito);
            log.debug("cdpasofracaso: "+cdpasofracaso);
            log.debug("swfinal: "+swfinal);
        }
        
    	session.put("OPCION_PROCESO_ID",	cdprocxcta);
    	session.put("OPCION_PASO_ID",		cdpaso);
    	session.put("OPCION_PANT_ID",		cdtitulo);
    	session.put("OPCION_DESC_PASO",		dspaso);
    	session.put("OPCION_ID_PASO_EXITO",	cdpasoexito);
    	session.put("OPCION_PASO_EXITO",	dspasoexito);
    	session.put("OPCION_ID_PASO_FRACASO",	cdpasofracaso);
    	session.put("OPCION_PASO_FRACASO",	dspasofracaso);
    	session.put("OPCION_SWFINAL",		swfinal);
    	
    	return SUCCESS;
    }
    

    @SuppressWarnings("unchecked")
    public String getComboPasos() throws Exception{
    	
    	
    	cdprocxcta = (Long) ActionContext.getContext().getSession().get("OPCION_PROCESO_ID");
    	
        if(logger.isDebugEnabled()){
        	logger.debug("******ConfiguraWorkflowPs3Action.getComboPasos******");
        	logger.debug("cdprocxcta :"+cdprocxcta);
        	logger.debug("cdpaso :"+cdpaso);
        }
      
        
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("cdprocxcta", cdprocxcta);
    	param.put("cdpaso", cdpaso);
        
        try{
        	pasosList = catalogManager.getItemList("OBTIENE_PASOS_WF", param);
        	logger.debug("pasosList :"+pasosList);
	        session.put("COMBO_PASOS_LIST", pasosList);
        	
        }catch(Exception e){
        	if(logger.isDebugEnabled())
        	logger.debug("Exception ConfiguraWorkflowPs3Action.getComboPasos: " + e);
        }

    	return SUCCESS;
    }
    
    
    @SuppressWarnings("unchecked")
    public String agregarConfig() throws Exception{

    	if(logger.isDebugEnabled())
        	logger.debug("******ConfiguraWorkflowAction.agregarConfig******");


    	cdprocxcta = (Long) ActionContext.getContext().getSession().get("OPCION_PROCESO_ID");
    	cdpaso = (Long) ActionContext.getContext().getSession().get("OPCION_PASO_ID");
    	cdtitulo = (Long) ActionContext.getContext().getSession().get("OPCION_PANT_ID");
    	dspaso = (String) ActionContext.getContext().getSession().get("OPCION_DESC_PASO");
    	
    	if(swfinal!=null && swfinal.equalsIgnoreCase("on")){
    		swfinal = "S";
    	}else{
    		swfinal = "N";
    	}
    	
        if(logger.isDebugEnabled()){
        	logger.debug("cdprocxcta 	: " + cdprocxcta);            
        	logger.debug("cdpaso     	: " + cdpaso);
        	logger.debug("cdtitulo      : " + cdtitulo);
        	logger.debug("dspaso     	: " + dspaso);
        	logger.debug("cdpasoexito   : " + cdpasoexito);
        	logger.debug("cdpasofracaso : " + cdpasofracaso);
        	logger.debug("swfinal       : " + swfinal);
        }

        try{
        	Map<String, Object> param = new HashMap<String, Object>();
        	param.put("cdprocxcta", cdprocxcta);
        	param.put("cdpaso", cdpaso);
        	param.put("cdtitulo", cdtitulo);
        	param.put("dspaso", dspaso);
        	param.put("cdpasoexito", cdpasoexito);
        	param.put("cdpasofracaso", cdpasofracaso);
        	param.put("swfinal", swfinal);

	        outParamText = configWorkFlowManager.getMessageNumber("INSERTA_PASOSXCTA",param,"Pasos");

	        if(logger.isDebugEnabled())
	            logger.debug("*** outParamText: " + outParamText);
	        
	        if(outParamText.trim().equals("2")){
	        	success = true;
	        }else{
	        	success = false;
	        }

        }catch(Exception e){
        	if(logger.isDebugEnabled())
        	logger.debug("Exception ConfiguraWorkflowAction.agregarConfig: " + e);
        }

    	return SUCCESS;

    }
    
    
	/**
	 * @return the success
	 */
	public boolean getSuccess() {
		return success;
	}


	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}


	/**
	 * @return the cdprocxcta
	 */
	public Long getCdprocxcta() {
		return cdprocxcta;
	}


	/**
	 * @param cdprocxcta the cdprocxcta to set
	 */
	public void setCdprocxcta(Long cdprocxcta) {
		this.cdprocxcta = cdprocxcta;
	}


	/**
	 * @return the dsproceso
	 */
	public String getDsproceso() {
		return dsproceso;
	}


	/**
	 * @param dsproceso the dsproceso to set
	 */
	public void setDsproceso(String dsproceso) {
		this.dsproceso = dsproceso;
	}


	/**
	 * @return the dsprocesoflujo
	 */
	public String getDsprocesoflujo() {
		return dsprocesoflujo;
	}

	
	/**
	 * @param dsprocesoflujo the dsprocesoflujo to set
	 */
	public void setDsprocesoflujo(String dsprocesoflujo) {
		this.dsprocesoflujo = dsprocesoflujo;
	}

	
	/**
	 * @return the dselemen
	 */
	public String getDselemen() {
		return dselemen;
	}

	
	/**
	 * @param dselemen the dselemen to set
	 */
	public void setDselemen(String dselemen) {
		this.dselemen = dselemen;
	}

	
	/**
	 * @return the dsramo
	 */
	public String getDsramo() {
		return dsramo;
	}

	
	/**
	 * @param dsramo the dsramo to set
	 */
	public void setDsramo(String dsramo) {
		this.dsramo = dsramo;
	}

	
	/**
	 * @return the dsunieco
	 */
	public String getDsunieco() {
		return dsunieco;
	}

	
	/**
	 * @param dsunieco the dsunieco to set
	 */
	public void setDsunieco(String dsunieco) {
		this.dsunieco = dsunieco;
	}

	
	/**
	 * @return the cdpaso
	 */
	public Long getCdpaso() {
		return cdpaso;
	}

	
	/**
	 * @param cdpaso the cdpaso to set
	 */
	public void setCdpaso(Long cdpaso) {
		this.cdpaso = cdpaso;
	}

	
	/**
	 * @return the dspaso
	 */
	public String getDspaso() {
		return dspaso;
	}

	
	/**
	 * @param dspaso the dspaso to set
	 */
	public void setDspaso(String dspaso) {
		this.dspaso = dspaso;
	}

	
	/**
	 * @return the cdestado
	 */
	public Long getCdestado() {
		return cdestado;
	}

	
	/**
	 * @param cdestado the cdestado to set
	 */
	public void setCdestado(Long cdestado) {
		this.cdestado = cdestado;
	}

	
	/**
	 * @return the cdpasoexito
	 */
	public Long getCdpasoexito() {
		return cdpasoexito;
	}

	
	/**
	 * @param cdpasoexito the cdpasoexito to set
	 */
	public void setCdpasoexito(Long cdpasoexito) {
		this.cdpasoexito = cdpasoexito;
	}

	
	/**
	 * @return the cdpasofracaso
	 */
	public Long getCdpasofracaso() {
		return cdpasofracaso;
	}

	
	/**
	 * @param cdpasofracaso the cdpasofracaso to set
	 */
	public void setCdpasofracaso(Long cdpasofracaso) {
		this.cdpasofracaso = cdpasofracaso;
	}

	
	/**
	 * @return the cdcondici
	 */
	public Long getCdcondici() {
		return cdcondici;
	}

	
	/**
	 * @param cdcondici the cdcondici to set
	 */
	public void setCdcondici(Long cdcondici) {
		this.cdcondici = cdcondici;
	}

	
	/**
	 * @return the cdtitulo
	 */
	public Long getCdtitulo() {
		return cdtitulo;
	}

	
	/**
	 * @param cdtitulo the cdtitulo to set
	 */
	public void setCdtitulo(Long cdtitulo) {
		this.cdtitulo = cdtitulo;
	}

	
	/**
	 * @return the nmorden
	 */
	public Long getNmorden() {
		return nmorden;
	}

	
	/**
	 * @param nmorden the nmorden to set
	 */
	public void setNmorden(Long nmorden) {
		this.nmorden = nmorden;
	}

	
	/**
	 * @return the outParamText
	 */
	public String getOutParamText() {
		return outParamText;
	}


	/**
	 * @param outParamText the outParamText to set
	 */
	public void setOutParamText(String outParamText) {
		this.outParamText = outParamText;
	}


	/**
	 * @return the dspasoEdit
	 */
	public String getDspasoEdit() {
		return dspasoEdit;
	}


	/**
	 * @param dspasoEdit the dspasoEdit to set
	 */
	public void setDspasoEdit(String dspasoEdit) {
		this.dspasoEdit = dspasoEdit;
	}


	/**
	 * @return the pasosList
	 */
	public List<BaseObjectVO> getPasosList() {
		return pasosList;
	}


	/**
	 * @param pasosList the pasosList to set
	 */
	public void setPasosList(List<BaseObjectVO> pasosList) {
		this.pasosList = pasosList;
	}


	/**
	 * @return the dspasoexito
	 */
	public String getDspasoexito() {
		return dspasoexito;
	}


	/**
	 * @param dspasoexito the dspasoexito to set
	 */
	public void setDspasoexito(String dspasoexito) {
		this.dspasoexito = dspasoexito;
	}


	public String getSwfinal() {
		return swfinal;
	}


	public void setSwfinal(String swfinal) {
		this.swfinal = swfinal;
	}


	public String getDspasofracaso() {
		return dspasofracaso;
	}


	public void setDspasofracaso(String dspasofracaso) {
		this.dspasofracaso = dspasofracaso;
	}


}
