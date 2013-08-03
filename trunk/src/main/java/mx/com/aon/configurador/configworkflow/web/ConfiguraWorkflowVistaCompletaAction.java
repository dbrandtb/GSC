/**
 * 
 */
package mx.com.aon.configurador.configworkflow.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.configworkflow.WorkFlowPs1VO;
import mx.com.aon.portal.model.configworkflow.WorkFlowPs2VO;
import mx.com.aon.portal.service.PagedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;


/**
 * @author eflores
 * @date 08/08/2008
 */
public class ConfiguraWorkflowVistaCompletaAction extends PrincipalConfigWorkflowAction{

	private static final transient Log log= LogFactory.getLog(ConfiguraWorkflowVistaCompletaAction.class);
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
    
    private ArrayList<WorkFlowPs1VO> procesosXctaList;
    private List<WorkFlowPs2VO> pasosXctaList;
    private List<BaseObjectVO> pantallasList;    
    private Map<String,String> parameters;
    
    private boolean success;
    
    @SuppressWarnings("unchecked")
    public String execute() throws Exception{
    	
        if (log.isDebugEnabled()) {
            log.debug("******ConfiguraWorkflowVistaCompletaAction.execute******");
            log.debug("cdprocxcta: " + cdprocxcta);    
        }
        
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("cdprocxcta", cdprocxcta);
        
    	procesosXctaList = (ArrayList<WorkFlowPs1VO>)configWorkFlowManager.getProcesos("OBTIENE_PROCESOSXCTA_REG", parametros);

    	for(WorkFlowPs1VO vo : procesosXctaList){
    		dsproceso = vo.getDsproceso();
    		dsprocesoflujo = vo.getDsprocesoflujo();
    		dselemen = vo.getDselemen();
    		dsramo = vo.getDsramo();
    		dsunieco = vo.getDsunieco();
    	}
    	session.put("PASOS_ID", cdprocxcta);
    	session.put("DSPROCESO", dsproceso);
    	session.put("DSPROCESOFLUJO", dsprocesoflujo);
    	session.put("DSELEMEN", dselemen);
    	session.put("DSRAMO", dsramo);
    	session.put("DSUNIECO", dsunieco);
    	
//		Valores para exportacion
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("cdprocxcta", cdprocxcta);
		String [] NOMBRE_COLUMNAS = {"PASO","PANTALLA","PASOEXITO","PASOFRACASO"};
		session.put("NOMBRE_COLUMNAS", NOMBRE_COLUMNAS);
		session.put("ENDPOINT_EXPORT_NAME", "WF_EXPORT");
		session.put("PARAMETROS_EXPORT", params);
		
		logger.debug("***********");
		logger.debug("parameters:"+session.get("PARAMETROS"));
		
    	return INPUT;
    }
    

    /**
     * consultarJson
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String consultarJson() throws Exception{
        if (logger.isDebugEnabled()) {
            logger.debug("-> ConfiguraWorkflowVistaCompletaAction.consultarJson");
            logger.debug("Entró a la lista Json....");                   
        }
        
       	cdprocxcta = (Long) ActionContext.getContext().getSession().get("PASOS_ID");
       	if (log.isDebugEnabled())
       		log.debug("cdprocxcta :" + cdprocxcta);
       	
        Map<String, String> parametros = new HashMap<String, String>();
        parametros.put("cdprocxcta", cdprocxcta+"");
        
        PagedList pagedList = pagerManager.getPagedData(parametros, "OBTIENE_PASOSXCTA", start, limit);
        pasosXctaList = pagedList.getItemsRangeList();
        this.totalCount = pagedList.getTotalItems();
        
        success = true;        
        return SUCCESS;
    }
        
    public void params() throws Exception{
    	logger.debug("params***");
    	logger.debug("parameters: "+parameters);
    	logger.debug("parameters.dsproceso: "+parameters.get("dsproceso").toString());
    	logger.debug("parameters.dsprocesoflujo: "+parameters.get("dsprocesoflujo").toString());
    	logger.debug("parameters.dselemen: "+parameters.get("dselemen").toString());
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


	/**
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}


	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}


}
