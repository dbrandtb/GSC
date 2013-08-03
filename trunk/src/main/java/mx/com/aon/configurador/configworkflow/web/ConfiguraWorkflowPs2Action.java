/**
 * 
 */
package mx.com.aon.configurador.configworkflow.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.configworkflow.WorkFlowPs1VO;
import mx.com.aon.portal.model.configworkflow.WorkFlowPs2VO;
import mx.com.aon.portal.service.PagedList;
import com.opensymphony.xwork2.ActionContext;


/**
 * @author eflores
 * @date 18/07/2008
 *
 */
public class ConfiguraWorkflowPs2Action extends PrincipalConfigWorkflowAction{

	private static final transient Log log= LogFactory.getLog(ConfiguraWorkflowPs2Action.class);
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
    private String swfinal;
    private String outParamText;
    
    private String dspasoEdit;
    
    private ArrayList<WorkFlowPs1VO> procesosXctaList;
    private List<WorkFlowPs2VO> pasosXctaList;
    private List<BaseObjectVO> pantallasList;
    
    private boolean success;
    
    @SuppressWarnings("unchecked")
    public String execute() throws Exception{
    	
        if (log.isDebugEnabled()) {
            log.debug("******ConfiguraWorkflowPs2Action.execute******");
            log.debug("cdprocxcta: "+cdprocxcta);    
        }
        
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("cdprocxcta", cdprocxcta);
        
        try{
        	procesosXctaList = (ArrayList<WorkFlowPs1VO>)configWorkFlowManager.getProcesos("OBTIENE_PROCESOSXCTA_REG", parametros);

        	for(WorkFlowPs1VO vo : procesosXctaList){
        		dsproceso = vo.getDsproceso();
        		dsprocesoflujo = vo.getDsprocesoflujo();
        		dselemen = vo.getDselemen();
        		dsramo = vo.getDsramo();
        		dsunieco = vo.getDsunieco();
        	}
        	session.put("PASOS_PROCESOID", cdprocxcta);
        	session.put("PASOS_DSPROCESO", dsproceso);
        	session.put("PASOS_DSPROCESOFLUJO", dsprocesoflujo);
        	session.put("PASOS_DSELEMEN", dselemen);
        	session.put("PASOS_DSRAMO", dsramo);
        	session.put("PASOS_DSUNIECO", dsunieco);
            
        }catch(Exception e){
        	if(logger.isDebugEnabled())
        		logger.debug("Exception ConfiguraWorkflowPs2Action.execute: " + e);
        }
        
        
    	return SUCCESS;
    }
    

    /**
     * consultarJson
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String consultarJson() throws Exception{
        if (logger.isDebugEnabled()) {
            logger.debug("-> ConfiguraWorkflowPs2Action.consultarJson");
            logger.debug("Entró a la lista Json....");                   
        }
        
	       	cdprocxcta = (Long) ActionContext.getContext().getSession().get("PASOS_PROCESOID");
	       	if (log.isDebugEnabled())
	       		log.debug("cdprocxcta :" + cdprocxcta);
	       	
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("cdprocxcta", cdprocxcta+"");
            
            try{
            log.debug("pagerManager :" + pagerManager);
	            PagedList pagedList = pagerManager.getPagedData(parametros, "OBTIENE_PASOSXCTA", start, limit);
	            log.debug("pagedList :" + pagedList);
	            pasosXctaList = pagedList.getItemsRangeList();
	            this.totalCount = pagedList.getTotalItems();
            }catch(Exception e){
            	if(logger.isDebugEnabled())
            		logger.debug("Exception ConfiguraWorkflowPs2Action.consultarJson: " + e);
            }
        
        success = true;        
        return SUCCESS;
    }
    

    @SuppressWarnings("unchecked")
    public String getComboPantallas() throws Exception{
    	
        if(logger.isDebugEnabled())
        	logger.debug("******ConfiguraWorkflowAction.getComboPantallas******");

        
        try{
        	pantallasList = catalogManager.getItemList("OBTIENE_PANTALLAS_WF");
        	logger.debug("pantallasList :"+pantallasList);
	        session.put("COMBO_PANTALLAS_LIST", pantallasList);
        	
        }catch(Exception e){
        	if(logger.isDebugEnabled())
        	logger.debug("Exception ConfiguraWorkflowPs2Action.getComboPantallas: " + e);
        }

    	return SUCCESS;
    }
    
    
    @SuppressWarnings("unchecked")
    public String insertaPasos() throws Exception{

    	if(logger.isDebugEnabled())
        	logger.debug("******ConfiguraWorkflowAction.insertaPasos******");
    	
    
    	cdprocxcta = (Long) ActionContext.getContext().getSession().get("PASOS_PROCESOID");
    	
        if(logger.isDebugEnabled()){
        	logger.debug("cdprocxcta : " + cdprocxcta);            
        	logger.debug("cdpaso     : " + cdpaso);
        	logger.debug("dspaso     : " + dspaso);
        	logger.debug("cdtitulo   : " + cdtitulo);
        }
        
        try{
        	Map<String, Object> param = new HashMap<String, Object>();
        	param.put("cdprocxcta", cdprocxcta);
        	param.put("cdpaso", cdpaso);
        	param.put("dspaso", dspaso);
        	param.put("cdtitulo", cdtitulo);
        	
	        outParamText = configWorkFlowManager.getMessageNumber("INSERTA_PASOSXCTA", param,"Pasos");

	        if(logger.isDebugEnabled())
	            logger.debug("*** outParamText: " + outParamText);
	        
	        if(outParamText.trim().equals("2")){
	        	success = true;
	        }else{
	        	success = false;
	        }
	        
        }catch(Exception e){
        	if(logger.isDebugEnabled())
        	logger.debug("Exception ConfiguraWorkflowAction.insertaPasos: " + e);
        }
    	
    	return SUCCESS;
    	
    }
    
    
    @SuppressWarnings("unchecked")
    public String editaPasos() throws Exception{

    	if(logger.isDebugEnabled())
        	logger.debug("******ConfiguraWorkflowAction.editaPasos******");


    	cdprocxcta = (Long) ActionContext.getContext().getSession().get("PASOS_PROCESOID");

        if(logger.isDebugEnabled()){
        	logger.debug("cdprocxcta : " + cdprocxcta);            
        	logger.debug("cdpaso     : " + cdpaso);
        	logger.debug("dspaso     : " + dspaso);
        	logger.debug("cdtitulo   : " + cdtitulo);
        	logger.debug("cdpasoexito   : " + cdpasoexito);
        	logger.debug("cdpasofracaso : " + cdpasofracaso);
        }

        try{
        	Map<String, Object> param = new HashMap<String, Object>();
        	param.put("cdprocxcta", cdprocxcta);
        	param.put("cdpaso", cdpaso);
        	param.put("dspaso", dspaso);
//        	param.put("cdestado", null);
        	param.put("cdpasoexito", cdpasoexito);
        	param.put("cdpasofracaso", cdpasoexito);
//        	param.put("cdcondici", null);
        	param.put("cdtitulo", cdtitulo);
//        	param.put("nmorden", null);

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
        	logger.debug("Exception ConfiguraWorkflowAction.editaPasos: " + e);
        }

    	return SUCCESS;

    }
    
    
    @SuppressWarnings("unchecked")
    public String borraPasos() throws Exception{

    	if(logger.isDebugEnabled())
        	logger.debug("******ConfiguraWorkflowAction.borraPasos******");


    	cdprocxcta = (Long) ActionContext.getContext().getSession().get("PASOS_PROCESOID");

        if(logger.isDebugEnabled()){
        	logger.debug("cdprocxcta : " + cdprocxcta);            
        	logger.debug("cdpaso     : " + cdpaso);
        }

        try{
        	Map<String, Object> param = new HashMap<String, Object>();
        	param.put("cdprocxcta", cdprocxcta);
        	param.put("cdpaso", cdpaso);

	        outParamText = configWorkFlowManager.getMessageNumber("BORRA_PASOSXCTA",param,"Pasos");

	        if(logger.isDebugEnabled())
	            logger.debug("*** outParamText: " + outParamText);
	        
	        if(outParamText.trim().equals("2")){
	        	success = true;
	        }else{
	        	success = false;
	        }

        }catch(Exception e){
        	if(logger.isDebugEnabled())
        	logger.debug("Exception ConfiguraWorkflowAction.borraPasos: " + e);
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
	 * @return the procesosXctaList
	 */
	public ArrayList<WorkFlowPs1VO> getProcesosXctaList() {
		return procesosXctaList;
	}


	/**
	 * @param procesosXctaList the procesosXctaList to set
	 */
	public void setProcesosXctaList(ArrayList<WorkFlowPs1VO> procesosXctaList) {
		this.procesosXctaList = procesosXctaList;
	}


	/**
	 * @return the pasosXctaList
	 */
	public List<WorkFlowPs2VO> getPasosXctaList() {
		return pasosXctaList;
	}


	/**
	 * @param pasosXctaList the pasosXctaList to set
	 */
	public void setPasosXctaList(List<WorkFlowPs2VO> pasosXctaList) {
		this.pasosXctaList = pasosXctaList;
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
	 * @return the pantallasList
	 */
	public List<BaseObjectVO> getPantallasList() {
		return pantallasList;
	}

	/**
	 * @param pantallasList the pantallasList to set
	 */
	public void setPantallasList(List<BaseObjectVO> pantallasList) {
		this.pantallasList = pantallasList;
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


	public String getSwfinal() {
		return swfinal;
	}


	public void setSwfinal(String swfinal) {
		this.swfinal = swfinal;
	}


}
