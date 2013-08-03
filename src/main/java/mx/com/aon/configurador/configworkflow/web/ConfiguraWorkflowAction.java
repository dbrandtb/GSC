/**
 * 
 */
package mx.com.aon.configurador.configworkflow.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.configurador.pantallas.model.ClienteCorpoVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.configworkflow.WorkFlowPs1VO;
import mx.com.aon.portal.service.PagedList;
import com.opensymphony.xwork2.ActionContext;


/**
 * @author eflores
 * @date 18/07/2008
 *
 */
public class ConfiguraWorkflowAction extends PrincipalConfigWorkflowAction{

    private static final long serialVersionUID = 1L;

    /**
     * Id del Proceso por Cuenta
     */
    private String cdprocxcta;
    /**
     * Id del Proceso
     */
    private String cdproceso;
    /**
     * Id de Aseguradora
     */
    private String cdunieco;
    /**
     * Id del Producto
     */
    private String cdramo;
    /**
     * Id del Cliente
     */
    private String cdelemento;
    /**
     * Id del Person
     */
    private String cdperson;
    /**
     * Descripcion del Nombre
     */
    private String dsproceso;
    /**
     * Descripcion del Proceso
     */
    private String dsprocesoflujo;
    /**
     * Descripcion de la Aseguradora
     */
    private String dsunieco;
    /**
     * Descripcion del Producto
     */
    private String dsramo;
    /**
     * Descripcion del Cliente
     */
    private String dselemen;

    private String outParamText;

    private List<BaseObjectVO> procesosList;
    private List<ClienteCorpoVO> clientesList;
    private List<BaseObjectVO> aseguradorasList;
    private List<BaseObjectVO> productosList;
    private List<WorkFlowPs1VO> procesosXctaList;
    
    private boolean success;

    /**
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("-> ConfiguraWorkflowAction.execute");
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
            logger.debug("-> ConfiguraWorkflowAction.consultarJson");
            logger.debug("Entró a la lista Json....");
            logger.debug("start      : " + start);
            logger.debug("limit      : " + limit);
            logger.debug("Nombre     : " + dsproceso);
            logger.debug("proceso    : " + dsprocesoflujo);
            logger.debug("cliente    : " + dselemen);
            logger.debug("aseguradora: " + dsunieco);
            logger.debug("producto   : " + dsramo);
        }
        
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("nombre", dsproceso);
            parametros.put("proceso", dsprocesoflujo);
            parametros.put("cliente", dselemen);
            parametros.put("aseguradora", dsunieco);
            parametros.put("producto", dsramo);
            
            try{
	            PagedList pagedList = pagerManager.getPagedData(parametros, "OBTIENE_PROCESOSXCTA", start, limit);
	            procesosXctaList = pagedList.getItemsRangeList();
	            this.totalCount = pagedList.getTotalItems();
            }catch(Exception e){
            	if(logger.isDebugEnabled())
            		logger.debug("Exception ConfiguraWorkflowAction.consultarJson: " + e);
            }
            logger.debug("Finalizo ConfiguraWorkflowAction.consultarJson: ");
        success = true;        
        return SUCCESS;
    }
  
    @SuppressWarnings("unchecked")
    public String getComboProceso() throws Exception{
        
    	try{
			procesosList = catalogManager.getItemList("OBTIENE_PROCESOS");
	        session.put("COMBO_PROCESOS_LIST", procesosList);
    	}catch(Exception e){
        	if(logger.isDebugEnabled())
        		logger.debug("Exception ConfiguraWorkflowAction.getComboProceso: " + e);
    	}
        
    	return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String getComboCliente() throws Exception{
        
    	try{
    		clientesList = catalogManager.getItemList("OBTIENE_CLIENTES_CORPO_CATALOGS");
        	session.put("COMBO_CORPORATIVO_LIST", clientesList);
    	}catch(Exception e){
        	if(logger.isDebugEnabled())
        		logger.debug("Exception ConfiguraWorkflowAction.getComboCliente: " + e);
    	}
        
    	return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String getComboAseguradora() throws Exception{
    	
        if(logger.isDebugEnabled()){
        	logger.debug("******ConfiguraWorkflowAction.getComboAseguradora******");
        	logger.debug("id cliente:" + cdelemento);
        }
        
        try{
        	HashMap params = new HashMap();
    		params.put("cdElemento", cdelemento);
	        aseguradorasList = catalogManager.getItemList("OBTIENE_ASEGURADORAS_CAT2",params);
	        session.put("COMBO_ASEGURADORAS_LIST", procesosList);
        	
        }catch(Exception e){
        	if(logger.isDebugEnabled())
        	logger.debug("Exception ConfiguraWorkflowAction.getComboAseguradora: " + e);
        }

    	return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String getComboProducto() throws Exception{
    	
        if(logger.isDebugEnabled()){
        	logger.debug("******ConfiguraWorkflowAction.getComboProducto******");
            logger.debug("id cliente: " + cdelemento);
        	logger.debug("id aseguradora: " + cdunieco);
        }
    	
        try{
        	logger.debug("*** Inicia opciones ");
        	Map<String, Object> opciones = new HashMap<String, Object>();
	        opciones.put("cdelemento", cdelemento);
	        opciones.put("cdunieco", cdunieco);
	        logger.debug("*** opciones: " + opciones);
	        productosList = catalogManager.getItemList("OBTIENE_PRODUCTOS_CLIENTE",opciones);
	        session.put("COMBO_PRODUCTOS_LIST", procesosList);
        }catch(Exception e){
        	if(logger.isDebugEnabled())
        	logger.debug("Exception ConfiguraWorkflowAction.getComboProducto: " + e);
        }

    	return SUCCESS;
    }

    @SuppressWarnings("unchecked")
    public String insertaProceso() throws Exception{

    	if(logger.isDebugEnabled()){
        	logger.debug("******ConfiguraWorkflowAction.insertaProceso******");
    		logger.debug("cdprocxcta :"+cdprocxcta);
    	}
    
        if(cdprocxcta!=null && !cdprocxcta.equalsIgnoreCase("")){
        	if(logger.isDebugEnabled())
            	logger.debug("******Edita Proceso******");
        	//Proceso
            ArrayList<BaseObjectVO> proc = (ArrayList<BaseObjectVO>)ActionContext.getContext().getSession().get("COMBO_PROCESOS_LIST");
            if (proc != null && !proc.isEmpty()) {
                for(BaseObjectVO pr : proc){
                    if(pr.getLabel().equalsIgnoreCase(dsprocesoflujo)){
                    	cdproceso = pr.getValue();              	
                    	break;
                    }
                }
            }
        	//Cliente
            ArrayList<ClienteCorpoVO> client = (ArrayList<ClienteCorpoVO>)ActionContext.getContext().getSession().get("COMBO_CORPORATIVO_LIST");
            if (client != null && !client.isEmpty()) {
                for(ClienteCorpoVO cl : client){
                    if(cl.getDsCliente().equalsIgnoreCase(dselemen)){
                    	cdelemento = cl.getCdCliente();
                    	cdperson = cl.getCdPerson();
                    	break;
                    }
                }
            }
        	//Aseguradora
            ArrayList<BaseObjectVO> aseg = (ArrayList<BaseObjectVO>)ActionContext.getContext().getSession().get("OBTIENE_ASEGURADORAS");
            if (aseg != null && !aseg.isEmpty()) {
                for(BaseObjectVO as : aseg){
                    if(as.getLabel().equalsIgnoreCase(dsunieco)){
                    	cdunieco = as.getValue();
                    	break;
                    }
                }
            }
        	//Producto
            ArrayList<BaseObjectVO> prod = (ArrayList<BaseObjectVO>)ActionContext.getContext().getSession().get("OBTIENE_PRODUCTOS_CLIENTE");
            if (prod != null && !prod.isEmpty()) {
                for(BaseObjectVO pr : prod){
                    if(pr.getLabel().equalsIgnoreCase(dsramo)){
                    	cdramo = pr.getValue();
                    	break;
                    }
                }
            }
                    	
        }else{
        	if(logger.isDebugEnabled())
            	logger.debug("******Agraga Proceso******");
        	//Cliente
	        ArrayList<ClienteCorpoVO> client = (ArrayList<ClienteCorpoVO>)ActionContext.getContext().getSession().get("COMBO_CORPORATIVO_LIST");
	        if (client != null && !client.isEmpty()) {
	            for(ClienteCorpoVO cl : client){
	                if(cl.getCdCliente().equalsIgnoreCase(cdelemento)){
	                	cdperson = cl.getCdPerson();               	
	                	break;
	                }
	            }
	        }
        }

        if(logger.isDebugEnabled()){
        	logger.debug("cdprocxcta : " + cdprocxcta);            
        	logger.debug("nombre  : " + dsproceso);
        	logger.debug("proceso    : " + cdproceso);
        	logger.debug("cliente    : " + cdelemento);
        	logger.debug("persona    : " + cdperson);
        	logger.debug("aseguradora: " + cdunieco);
        	logger.debug("producto   : " + cdramo);
        	logger.debug("estado     : " + null);
        	logger.debug("dsprocesoflujo: "+dsprocesoflujo);
        	logger.debug("dselemen      : "+dselemen);
        }
        
        try{
        	Map<String, Object> param = new HashMap<String, Object>();
        	param.put("procxcta", cdprocxcta);
        	param.put("nombre", dsproceso);
        	param.put("proceso", cdproceso);
        	param.put("cliente", cdelemento);
        	param.put("persona", cdperson);
        	param.put("aseguradora", cdunieco);
        	param.put("producto", cdramo);
        	param.put("estado",null);
        	
	        outParamText = configWorkFlowManager.getMessageNumber("INSERTA_PROCESOS", param);

	        if(logger.isDebugEnabled())
	            logger.debug("*** outParamText: " + outParamText);
	        
	        if(outParamText.trim().equals("2")){
	        	success = true;
	        }else{
	        	success = false;
	        }
	        
        }catch(Exception e){
        	if(logger.isDebugEnabled())
        	logger.debug("Exception ConfiguraWorkflowAction.insertaProceso: " + e);
        }
    	
    	return SUCCESS;
    	
    }
    
    @SuppressWarnings("unchecked")
    public String borraProceso() throws Exception{
        
        if(logger.isDebugEnabled()){
        	logger.debug("******ConfiguraWorkflowAction.borraProceso******");
        	logger.debug("cdprocxcta : " + cdprocxcta);
        }
        
        try{
        	Map<String, Object> param = new HashMap<String, Object>();
        	param.put("procxcta", cdprocxcta);        	
	        outParamText = configWorkFlowManager.getMessageNumber("BORRA_PROCESOS", param);

	        if(logger.isDebugEnabled())
	            logger.debug("*** borraProceso outParamText: " + outParamText);
	        
	        if(outParamText.trim().equals("2")){
	        	success = true;
	        }else{
	        	success = false;
	        }
	        
        }catch(Exception e){
        	if(logger.isDebugEnabled())
        	logger.debug("Exception ConfiguraWorkflowAction.borraProceso: " + e);
        }
    	
    	return SUCCESS;    	
    }
    
    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the aseguradorasList
     */
    public List<BaseObjectVO> getAseguradorasList() {
        return aseguradorasList;
    }

    /**
     * @param aseguradorasList the aseguradorasList to set
     */
    public void setAseguradorasList(List<BaseObjectVO> aseguradorasList) {
        this.aseguradorasList = aseguradorasList;
    }

    /**
     * @return the clienteList
     */
    public List<ClienteCorpoVO> getClientesList() {
        return clientesList;
    }

    /**
     * @param clienteList the clienteList to set
     */
    public void setClientesList(List<ClienteCorpoVO> clientesList) {
        this.clientesList = clientesList;
    }

    /**
     * @return the procesosList
     */
    public List<BaseObjectVO> getProcesosList() {
        return procesosList;
    }

	/**
	 * @param procesosList the procesosList to set
	 */
	public void setProcesosList(List<BaseObjectVO> procesosList) {
		this.procesosList = procesosList;
	}

	/**
	 * @return the productosList
	 */
	public List<BaseObjectVO> getProductosList() {
		return productosList;
	}

	/**
	 * @param productosList the productosList to set
	 */
	public void setProductosList(List<BaseObjectVO> productosList) {
		this.productosList = productosList;
	}

	/**
	 * @return the procesosXctaList
	 */
	public List<WorkFlowPs1VO> getProcesosXctaList() {
		return procesosXctaList;
	}

	/**
	 * @param procesosXctaList the procesosXctaList to set
	 */
	public void setProcesosXctaList(List<WorkFlowPs1VO> procesosXctaList) {
		this.procesosXctaList = procesosXctaList;
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
	 * @return the cdprocxcta
	 */
	public String getCdprocxcta() {
		return cdprocxcta;
	}

	/**
	 * @param cdprocxcta the cdprocxcta to set
	 */
	public void setCdprocxcta(String cdprocxcta) {
		this.cdprocxcta = cdprocxcta;
	}

	/**
	 * @return the cdproceso
	 */
	public String getCdproceso() {
		return cdproceso;
	}

	/**
	 * @param cdproceso the cdproceso to set
	 */
	public void setCdproceso(String cdproceso) {
		this.cdproceso = cdproceso;
	}

	/**
	 * @return the cdunieco
	 */
	public String getCdunieco() {
		return cdunieco;
	}

	/**
	 * @param cdunieco the cdunieco to set
	 */
	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}

	/**
	 * @return the cdramo
	 */
	public String getCdramo() {
		return cdramo;
	}

	/**
	 * @param cdramo the cdramo to set
	 */
	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}

	/**
	 * @return the cdelemento
	 */
	public String getCdelemento() {
		return cdelemento;
	}

	/**
	 * @param cdelemento the cdelemento to set
	 */
	public void setCdelemento(String cdelemento) {
		this.cdelemento = cdelemento;
	}
	
	/**
	 * @return the cdperson
	 */
	public String getCdperson() {
		return cdperson;
	}

	/**
	 * @param cdperson the cdperson to set
	 */
	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
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

}
