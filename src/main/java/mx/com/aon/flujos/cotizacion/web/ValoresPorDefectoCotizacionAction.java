/**
 * 
 */
package mx.com.aon.flujos.cotizacion.web;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import mx.com.aon.configurador.pantallas.model.PantallaVO;
import mx.com.aon.configurador.pantallas.model.components.ColumnGridEstandarVO;
import mx.com.aon.configurador.pantallas.model.components.RecordVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.VariableKernel;
import mx.com.aon.flujos.cotizacion.model.NameLabelControlVO;
import mx.com.aon.flujos.cotizacion.service.CotizacionService;
import mx.com.aon.kernel.service.KernelManager;
import mx.com.aon.portal.model.UserVO;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * @author eflores
 * @date 11/08/2008
 *
 */
public class ValoresPorDefectoCotizacionAction extends PrincipalCotizacionAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final transient Log log = LogFactory.getLog(ValoresPorDefectoCotizacionAction.class);
    
    private static final String GRID_INCISOS = "GRID_INCISOS"; 
    
    private CotizacionService cotizacionManager;
    
    private String cdElemen;
    private String cdRamo;
    private String cdTitulo;
    
    private String cdElemento;
    
    private Map<String, String> parameters;
    private Map<String, String> hparameters;
    
    private List<Map<String, String>> gridIncisos;
    private Map<String, String> paramsMap;
    
    private String item;
    private List<ColumnGridEstandarVO> listColumns;
    private List<RecordVO> listRecords;
    
    /**
     * Servicio inyectado por spring para llamar a los servicios de valores por defecto del kernel
     */
    private KernelManager kernelManager;
    
    private boolean success;

    /**
     * @throws Exception
     *@return INPUT 
     */
    public String execute() throws Exception{
        UserVO usuario = (UserVO)session.get("USUARIO");
        
        if (logger.isDebugEnabled()) {
        	logger.debug("-> ValoresPorDefectoCotizacionAction.execute");
            logger.debug(".. CD_ELEMENTO: " + usuario.getEmpresa().getElementoId());
            logger.debug(".. RolActivo  : " + usuario.getRolActivo().getObjeto().getValue());
            logger.debug(".. CDRAMO     : " + ServletActionContext.getRequest().getParameter("CDRAMO"));
            logger.debug(".. CDTIPSIT   : " + ServletActionContext.getRequest().getParameter("CDTIPSIT"));
            logger.debug(".. CDTITULO   : " + ServletActionContext.getRequest().getParameter("CDTITULO"));
        }
            
        Map<String, String> parameters = new HashMap<String, String>();
                
        cdElemen = usuario.getEmpresa().getElementoId();
        cdRamo = ServletActionContext.getRequest().getParameter("CDRAMO");
        cdTitulo= ServletActionContext.getRequest().getParameter("CDTITULO");
        String cdTipsit = ServletActionContext.getRequest().getParameter("CDTIPSIT");
        String sisRol = usuario.getRolActivo().getObjeto().getValue();

        parameters.put("CD_ELEMENT", cdElemen);
        parameters.put("CD_RAMO", cdRamo);
        parameters.put("CD_TITULO", cdTitulo);
        parameters.put("CD_TIPSIT", cdTipsit);
        parameters.put("CD_SISROL", sisRol);
        
        if (log.isDebugEnabled()) {
            log.debug(":: parameters : " + parameters);
        }
        
        PantallaVO pantallaDespliegueCotizacion = cotizacionManager.getPantallaFinal(parameters);
        
        if (log.isDebugEnabled()) {
            log.debug(":: pantallaDespliegueCotizacion : " + pantallaDespliegueCotizacion);
        }
        
        item = pantallaDespliegueCotizacion.getDsArchivoSec();
        
        if (log.isDebugEnabled()) {
            log.debug(":::::::::::::::::::  item  ::::::::::::::::::::::: ");
            log.debug(item);
        }
        
        /////////genera xml
        JSONObject json = null;
        try {
            json = JSONObject.fromObject(pantallaDespliegueCotizacion.getDsArchivo());
        } catch (JSONException e) {
            logger.error("----------> ERROR al generar el XML : " + e);            
        }
        
        XMLSerializer xmlSerializado = new XMLSerializer();

        
        String xml = xmlSerializado.write( json, "ISO-8859-1" );
        
        if (logger.isDebugEnabled()) {
        	logger.debug("##############  xml generado es : " + xml );
        }
        List<NameLabelControlVO> listFieldNames = getLabelNames(xml);
        if (logger.isDebugEnabled()) {
        	logger.debug("##############  listFieldNames : " + listFieldNames );
        }
        
        ColumnGridEstandarVO columnGridVO = null;
        RecordVO recordVO = null;
        for (NameLabelControlVO fieldName : listFieldNames) {
            columnGridVO = new ColumnGridEstandarVO(fieldName.getFieldName(), fieldName.getName(), 120, true, null, false);
            
            if (listColumns == null) {
                listColumns = new ArrayList<ColumnGridEstandarVO>();
            }
            listColumns.add(columnGridVO);
            
            //se llena lista de records
            if (listRecords == null) {
                listRecords = new ArrayList<RecordVO>();
                
                recordVO = new RecordVO();
                recordVO.setName("cdElemento");
                recordVO.setMapping("cdElemento");
                recordVO.setType("string");
                
                listRecords.add(recordVO);
            }
            
            recordVO = new RecordVO();
            recordVO.setName(fieldName.getName());
            recordVO.setMapping(fieldName.getName());
            recordVO.setType("string");
            
            listRecords.add(recordVO);
        }
        
        if (logger.isDebugEnabled()) {
	        logger.debug("##############  listColumns : " + listColumns );
	        logger.debug("##############  listColumnNames : " + listRecords );
        }
        
        return INPUT;
    }
    
    /**
     * Metodo que se encarga de llenar el grid con los incisos capturados.
     * @return
     * @throws ApplicationException
     */
    public String agregaIncisosJson() throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("-> ValoresPorDefectoCotizacionAction.agregaIncisosJson");
            logger.debug("::: parameters  :::::: " + parameters);
            logger.debug("::: hparameters :::::: " + hparameters);
        }
        
        gridIncisos = (List<Map<String, String>>) session.get(GRID_INCISOS);
        if (logger.isDebugEnabled()) {
        	logger.debug("::: gridIncisos session :::::: " + gridIncisos);
        }
                
        //if (parameters != null && !parameters.values().contains("") && !parameters.values().contains(null)) {
        if (parameters != null || hparameters != null) {
            paramsMap = new HashMap<String, String>();
            
            String key = null;
            Iterator<String> itParams = null;
            if (parameters != null) {
	            itParams = parameters.keySet().iterator();
	            while (itParams.hasNext()) {
	                key = itParams.next();
	                paramsMap.put(key, parameters.get(key));
	            }
            }
            
            String hkey = null;
            Iterator<String> hitParams = null;
            if (hparameters != null) {
	            hitParams = hparameters.keySet().iterator();
	            while (hitParams.hasNext()) {
	                hkey = hitParams.next();
	                paramsMap.put(hkey, hparameters.get(hkey));
	            }
            }
            
            if (gridIncisos == null) {
                gridIncisos = new ArrayList<Map<String,String>>();
                paramsMap.put("cdElemento", "1");
            } else {
                paramsMap.put("cdElemento", settingCdElement(gridIncisos));
            }
            
            if (logger.isDebugEnabled()) {
            	logger.debug("::::::::::::::::::::: paramsMap :::: " + paramsMap);
            }
            gridIncisos.add(paramsMap);
        }
          
        if (logger.isDebugEnabled()) {
        	logger.debug("::: gridIncisos :::: " + gridIncisos);
        }
        session.put(GRID_INCISOS, gridIncisos);
        
        success = true;
        
        return SUCCESS;
    }
    
    /**
     * Metodo que se encarga de borrar los elementos seleccionados del grid.
     * @return
     * @throws ApplicationException
     */
    public String borrarIncisosJson() throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("-> ValoresPorDefectoCotizacionAction.borrarIncisosJson");
            logger.debug("cdElemento :: " + cdElemento);
        }
        
        gridIncisos = (List<Map<String, String>>) session.get(GRID_INCISOS);
        if (logger.isDebugEnabled()) {
        	logger.debug("::: gridIncisos before :::::: " + gridIncisos);
        }
             
        if (gridIncisos != null && !gridIncisos.isEmpty()) {
            for (Map<String, String> gridInciso : gridIncisos) {
                if (cdElemento.equals(gridInciso.get("cdElemento"))) {
                    gridIncisos.remove(gridInciso);
                    break;
                }
            }
        }
           
        if (logger.isDebugEnabled()) {
        	logger.debug("::: gridIncisos after :::: " + gridIncisos);
        }
        session.put(GRID_INCISOS, gridIncisos);
        
        success = true;
        
        return SUCCESS;
    }
    
    /**
     * Metodo que se encarga de cotizar los elementos seleccionados del grid.
     * @return
     * @throws ApplicationException
     */
    public String cotizarElementoJson() throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("-> ValoresPorDefectoCotizacionAction.cotizarElementoJson");
            logger.debug("cdElemento :: " + cdElemento);
        }
        
        Iterator keys = null;
        Map<String, String> gridIncisoParameters = null;
        String key = null;
        gridIncisos = (List<Map<String, String>>) session.get(GRID_INCISOS);
             
        if (gridIncisos != null && !gridIncisos.isEmpty()) {
            for (Map<String, String> gridInciso : gridIncisos) {
                if (cdElemento.equals(gridInciso.get("cdElemento"))) {
                    if (logger.isDebugEnabled()) {
                    	logger.debug("::: gridInciso seleccionado :::::: " + gridInciso);
                    }
                    
                    keys = gridInciso.keySet().iterator();
                    gridIncisoParameters = new HashMap<String, String>();
                    
                    while (keys.hasNext()) {
                    	key = (String) keys.next();
                    	
                    	if (!"cdElemento".equals(key)) {
	                    	gridIncisoParameters.put(
	                    			new StringBuilder().append("parameters.").append(key).toString(),
	                    			gridInciso.get(key));
                    	}
                    }
                    
                    if (logger.isDebugEnabled()) {
                    	logger.debug("::: COTIZACION_INPUT :::::: " + gridIncisoParameters);
                    }
                    session.put( "COTIZACION_INPUT", gridIncisoParameters );
                    
                    session.put("COTIZAR_ACTION", 'S');
                    logger.debug("COTIZAR_ACTION=" + session.get("COTIZAR_ACTION"));
                    if ( session.containsKey( "DETALLE_COTIZACION" ) ) {
                    	session.remove( "DETALLE_COTIZACION" );
                    	logger.debug("!!! Se elimino de session DETALLE_COTIZACION. Existe? = " + session.containsKey( "DETALLE_COTIZACION" ) );
                    }
                    break;
                }
            }
        }
        
        if (logger.isDebugEnabled()) {
        	logger.debug("::: setting GlobalVariableContainerVO :::::: ");
        }
        //TODO quitar
        String tipsit = "1";
    	/*if (ServletActionContext.getRequest().getParameter("CDTIPSIT").equals("A ")){
    		tipsit = "A+";
    	} else {
    		tipsit = ServletActionContext.getRequest().getParameter("CDTIPSIT");
    	}	*/
    	
    	UserVO usuario = (UserVO)session.get("USUARIO");
    	
    	GlobalVariableContainerVO globalVarVo = new GlobalVariableContainerVO();
    	globalVarVo.addVariableGlobal(VariableKernel.UnidadEconomica(), "1");
    	//TODO quitar
    	globalVarVo.addVariableGlobal(VariableKernel.CodigoRamo(), "500");
    	globalVarVo.addVariableGlobal(VariableKernel.Estado(), "W");
    	globalVarVo.addVariableGlobal(VariableKernel.NumeroSuplemento(), "0");
    	globalVarVo.addVariableGlobal(VariableKernel.UsuarioBD(), usuario.getUser());
    	globalVarVo.addVariableGlobal(VariableKernel.CodigoTipoSituacion(), tipsit);
    	globalVarVo.addVariableGlobal(VariableKernel.NumeroSituacion(),"1");
    	globalVarVo = kernelManager.cargaValoresPorDefecto(ServletActionContext.getRequest().getSession().getId(), usuario, globalVarVo, "90");
    	
    	//Subimos a sesion la Global Variable Container
    	session.put("GLOBAL_VARIABLE_CONTAINER", globalVarVo);
                   
        success = true;
        
        return SUCCESS;
    }

    /**
     * @param gridIncisos2
     * @return
     */
    private String settingCdElement(List<Map<String, String>> gridIncisos2) {
        if (logger.isDebugEnabled()) {
            logger.debug("-> ValoresPorDefectoCotizacionAction.settingCdElement");
            logger.debug("::: gridIncisos2 :::::: " + gridIncisos2);
        }
        int maxKey = 1;
        int valIntIncisos = 0;
        for (Map<String, String> gridIncisosVO : gridIncisos2) {
            valIntIncisos = Integer.valueOf(gridIncisosVO.get("cdElemento"));
            if (valIntIncisos > maxKey) {
                maxKey = valIntIncisos;
            }
        }
        maxKey++;
        
        return String.valueOf(maxKey);
    }
    
    /**
     * Método encargado de obtener los nombres de los stores
     * utilizados por los combos
     * 
     * @param xml
     * @return lista de nombre de los controles
     */
    private List<NameLabelControlVO> getLabelNames(String xml) {
        if (logger.isDebugEnabled()) {
            logger.debug("-> DatosPantallaAction.getLabelNames");    
        }
        
        List<NameLabelControlVO> listaFieldNames = null;
        NameLabelControlVO nameLabelControlVO = null;
                
        try {
            SAXBuilder saxBuilder = new SAXBuilder();   
            
            Document document = saxBuilder.build(new ByteArrayInputStream(xml.getBytes()));
            Element root = document.getRootElement();
            List rows = root.getChildren("items");
            
            if (listaFieldNames == null) {
                listaFieldNames = new ArrayList<NameLabelControlVO>();
            }
            
            for (int i = 0; i < rows.size(); i++) {
                Element row = (Element) rows.get(i);
                List columns = row.getChildren("e");
                
                for (int j = 0; j < columns.size(); j++) {
                    nameLabelControlVO = new NameLabelControlVO();
                    
                    Element column = (Element) columns.get(j);
                    List nestedCols = column.getChildren("fieldLabel");
                    
                    for (int k = 0; k < nestedCols.size(); k++) {
                        Element attribute = (Element) nestedCols.get(k);
                        
                        String fieldLabel = attribute.getText();
                        if (logger.isDebugEnabled()) {
                        	logger.debug("::::::::::::::: fieldLabel ::: " + fieldLabel);
                        }
                        
                        nameLabelControlVO.setFieldName(fieldLabel);
                    }
                    
                    nestedCols = column.getChildren("name");
                    
                    for (int l = 0; l < nestedCols.size(); l++) {
                        Element attribute = (Element) nestedCols.get(l);
                        
                        String name = attribute.getText();
                                
                        StringTokenizer st = new StringTokenizer(name, ".");
                        st.nextToken();
                        name = st.nextToken();
                        if (logger.isDebugEnabled()) {
                        	logger.debug("::::::::::::::: name ::: " + name);
                        }
                        
                        nameLabelControlVO.setName(name);
                    }
                    
                    listaFieldNames.add(nameLabelControlVO);
                }                
            }
        } catch (Exception e) {
            logger.error("---------> ERROR al leer el XML :");
            logger.error(e);
        }
        
        if (logger.isDebugEnabled()) {
        	logger.debug("--> listaFieldNames ::: " + listaFieldNames);
        }
        
        return listaFieldNames;
    }

    /**
     * @param cotizacionManager the cotizacionManager to set
     */
    public void setCotizacionManager(CotizacionService cotizacionManager) {
        this.cotizacionManager = cotizacionManager;
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
     * @return the cdElemento
     */
    public String getCdElemento() {
        return cdElemento;
    }

    /**
     * @param cdElemento the cdElemento to set
     */
    public void setCdElemento(String cdElemento) {
        this.cdElemento = cdElemento;
    }

    /**
     * @return the cdRamo
     */
    public String getCdRamo() {
        return cdRamo;
    }

    /**
     * @param cdRamo the cdRamo to set
     */
    public void setCdRamo(String cdRamo) {
        this.cdRamo = cdRamo;
    }

    /**
     * @return the cdTitulo
     */
    public String getCdTitulo() {
        return cdTitulo;
    }

    /**
     * @param cdTitulo the cdTitulo to set
     */
    public void setCdTitulo(String cdTitulo) {
        this.cdTitulo = cdTitulo;
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

    /**
     * @return the cdElemen
     */
    public String getCdElemen() {
        return cdElemen;
    }

    /**
     * @param cdElemen the cdElemen to set
     */
    public void setCdElemen(String cdElemen) {
        this.cdElemen = cdElemen;
    }

    /**
     * @return the item
     */
    public String getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * @return the listRecords
     */
    public List<RecordVO> getListRecords() {
        return listRecords;
    }

    /**
     * @param listRecords the listRecords to set
     */
    public void setListRecords(List<RecordVO> listRecords) {
        this.listRecords = listRecords;
    }

    /**
     * @return the paramsMap
     */
    public Map<String, String> getParamsMap() {
        return paramsMap;
    }

    /**
     * @param paramsMap the paramsMap to set
     */
    public void setParamsMap(Map<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }

    /**
     * @return the gridIncisos
     */
    public List<Map<String, String>> getGridIncisos() {
        return gridIncisos;
    }

    /**
     * @param gridIncisos the gridIncisos to set
     */
    public void setGridIncisos(List<Map<String, String>> gridIncisos) {
        this.gridIncisos = gridIncisos;
    }

    /**
     * @return the listColumns
     */
    public List<ColumnGridEstandarVO> getListColumns() {
        return listColumns;
    }

    /**
     * @param listColumns the listColumns to set
     */
    public void setListColumns(List<ColumnGridEstandarVO> listColumns) {
        this.listColumns = listColumns;
    }

	public Map<String, String> getHparameters() {
		return hparameters;
	}

	public void setHparameters(Map<String, String> hparameters) {
		this.hparameters = hparameters;
	}

	public void setKernelManager(KernelManager kernelManager) {
		this.kernelManager = kernelManager;
	}
}
