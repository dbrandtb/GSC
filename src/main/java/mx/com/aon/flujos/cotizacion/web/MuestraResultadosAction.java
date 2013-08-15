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
import mx.com.aon.flujos.cotizacion.model.ConsultaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.NameLabelControlVO;
import mx.com.aon.flujos.cotizacion.service.CotizacionService;
import mx.com.aon.portal.model.UserVO;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.biosnet.ice.ext.elements.form.ExtElement;
import com.biosnet.ice.ext.elements.form.TextFieldControl;

/**
 * @author eflores
 * @date 06/11/2008
 *
 */
public class MuestraResultadosAction extends PrincipalCotizacionAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final transient Log log = LogFactory.getLog(MuestraResultadosAction.class);

    private static final String GRID_INCISOS = "GRID_INCISOS";

    private CotizacionService cotizacionManager;

    private String cdElemen;
    private String cdRamo;
    private String cdTitulo;

    private String cdElemento;

    private Map<String, String> parameters;
    private Map<String, String> hparameters;

    private List<Map<String, String>> gridIncisos;
    
    private String item;
    private List<ColumnGridEstandarVO> listColumns;
    private List<RecordVO> listRecords;

    private List<ExtElement> elementsMasterGrid;

    private boolean success;

    /**
     * @throws Exception
     *@return INPUT
     */
    public String execute() throws Exception{
        //Global Variable Container
        UserVO usuario = (UserVO)session.get("USUARIO");

        if (logger.isDebugEnabled()) {
            logger.debug("CD_ELEMENTO: " + usuario.getEmpresa().getElementoId());
            //logger.debug("CD_RAMO    : " + globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo()));
            //logger.debug("CD_TIPSIT  : " + globalVarVO.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
            logger.debug("CDRAMO: " + ServletActionContext.getRequest().getParameter("CDRAMO"));
            logger.debug("CDTIPSIT: " + ServletActionContext.getRequest().getParameter("CDTIPSIT"));
            logger.debug("CD_UNIECO  : " + "36");
            //logger.debug("CD_UNIECO  : " + globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
            logger.debug("CDTITULO: " + ServletActionContext.getRequest().getParameter("CDTITULO"));
        }
        String cdTitulo = ServletActionContext.getRequest().getParameter("CDTITULO");
        
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("CD_ELEMENT", usuario.getEmpresa().getElementoId());
        parameters.put("CD_RAMO",    ServletActionContext.getRequest().getParameter("CDRAMO"));
        parameters.put("CD_TIPSIT",  ServletActionContext.getRequest().getParameter("CDTIPSIT"));
        parameters.put("CD_TITULO",  cdTitulo);
        parameters.put("CD_SISROL",  usuario.getRolActivo().getObjeto().getValue());
            //parameters.put("CD_TITULO", globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
        
        /*cdElemen = "6117";
        cdRamo = "500";
        String cdTipsit = "3";
        parameters.put("CD_ELEMENT", cdElemen);
        parameters.put("CD_RAMO", cdRamo);
        //parameters.put("CD_TITULO", cdTitulo);
        parameters.put("CD_TIPSIT", cdTipsit);*/

        if (log.isDebugEnabled()) {
            log.debug("-> MuestraResultadosAction.execute");
            log.debug(":: parameters : " + parameters);
        }

        PantallaVO pantallaDespliegueCotizacion = cotizacionManager.getPantallaFinal(parameters);

        item = pantallaDespliegueCotizacion.getDsArchivoSec();
        
        if (StringUtils.isBlank(item)) {
            item = "";
        }
        /*if (StringUtils.isBlank(item)) {//TODO Mensaje
            item = "[{xtype:\"form\",title:\"Panel de Formulario\",autoScroll:\"true\",items:[{    " +
                    "html:\"<FONT face=arial color=#ff0000>&nbsp;No existen datos</FONT>\",    " +
                    "border:false,    width:200  }]}]";
        }*/
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
        
        logger.debug("##############  xml generado es : " + xml );
        List<NameLabelControlVO> listFieldNames = obtenLabelNames(xml);
        logger.debug("##############  listFieldNames : " + listFieldNames );
        
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
        logger.debug("##############  listColumns : " + listColumns );
        logger.debug("##############  listColumnNames : " + listRecords );

        return INPUT;
    }

    /**
     * Metodo que se encarga de llenar el grid con los incisos capturados.
     * @return
     * @throws ApplicationException
     */
    public String agregaIncisosJson() throws ApplicationException {
        if (log.isDebugEnabled()) {
		    logger.debug("-> MuestraResultadosAction.agregaIncisosJson");
		    logger.debug("::: parameters :::::: " + parameters);
		    logger.debug("::: hparameters :::::: " + hparameters);
		}

        Map<String, String> paramsMap = null;  
        
		////////////////////////////////////////////////////////////
		if (hparameters != null) {
            String user = (String) session.get("CONTENIDO_USER");
            if (log.isDebugEnabled()) {
                log.debug("#### session user : " + user);
            }
		    
		    Map<String,String> params = new HashMap<String, String>();
		    params.put("CDUSUARIO", user);
		    params.put("ASEGURADORA", hparameters.get("cdcia"));
		    params.put("PRODUCTO", hparameters.get("cdramo"));
		    List<ConsultaCotizacionVO> listaConsultaCotizacion = cotizacionManager.getCotizacion(params);

		    if(listaConsultaCotizacion == null ) {
		        listaConsultaCotizacion = new ArrayList<ConsultaCotizacionVO>();
		    } else {
                log.debug("listaConsultaCotizacion size = " + listaConsultaCotizacion.size());
                for (ConsultaCotizacionVO consultaCotizacion : listaConsultaCotizacion) {
                    paramsMap = new HashMap<String, String>();
                    
                    paramsMap.put("cdcia", consultaCotizacion.getCodigoAseguradora());
                    
                    paramsMap.put("dscia", consultaCotizacion.getDescripcionAseguradora());
                    
                    paramsMap.put("cdramo", consultaCotizacion.getCodigoProducto());
                    
                    paramsMap.put("dsramo", consultaCotizacion.getDescripcionProducto());
                    
                    paramsMap.put("feinival", consultaCotizacion.getFecha());
                    
                    paramsMap.put("dsperpag", consultaCotizacion.getDescripcionFormaPago());
                    
                    paramsMap.put("nmimpfpg", consultaCotizacion.getPrima());
                    
                    paramsMap.put("estado", consultaCotizacion.getEstado());
                    paramsMap.put("nmpoliza", consultaCotizacion.getNmpoliza());
                    paramsMap.put("cdplan", consultaCotizacion.getCdplan());
                    paramsMap.put("dsplan", consultaCotizacion.getDsplan());
                    paramsMap.put("cdcia", consultaCotizacion.getCdcia());
                    paramsMap.put("nmsituac", consultaCotizacion.getNmsituac());
                    paramsMap.put("nmsuplem", consultaCotizacion.getNmsuplem());
                    
                    log.debug(":::::: paramsMap = " + paramsMap);
                    
                    if (gridIncisos == null) {
                        gridIncisos = new ArrayList<Map<String,String>>();
                    }
                    
                    gridIncisos.add(paramsMap);
                }
            }
		    log.debug("listaConsultaCotizacion = " + listaConsultaCotizacion);
            log.debug("gridIncisos = " + gridIncisos);

		    //feinival - Fecha inicial
		    //dsperpag - Forma de pago
		    //nmimpfpg - Prima
		} else {
            gridIncisos = (List<Map<String, String>>) session.get(GRID_INCISOS);
            logger.debug("::: gridIncisos session :::::: " + gridIncisos);
        }
		///////////////////////////////////////////////////////////

		/*gridIncisos = (List<Map<String, String>>) session.get(GRID_INCISOS);
		logger.debug("::: gridIncisos session :::::: " + gridIncisos);

		//if (parameters != null && !parameters.values().contains("") && !parameters.values().contains(null)) {
        if (parameters != null && !parameters.isEmpty()) {
		    paramsMap = new HashMap<String, String>();

		    String key = null;
		    Iterator<String> itParams = parameters.keySet().iterator();
		    while (itParams.hasNext()) {
		        key = itParams.next();
		        if (StringUtils.isNotBlank(parameters.get(key))) {
		            paramsMap.put(key, parameters.get(key));
		        }
		    }

		    if (gridIncisos == null) {
		        gridIncisos = new ArrayList<Map<String,String>>();
		        paramsMap.put("cdElemento", "1");
		    } else {
		        paramsMap.put("cdElemento", settingCdElement(gridIncisos));
		    }

            logger.debug("::::::::::::::::::::: paramsMap :::: " + paramsMap);
	        gridIncisos.add(paramsMap);
	    }*/

	    logger.debug("::: gridIncisos :::: " + gridIncisos);
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
        if (log.isDebugEnabled()) {
            logger.debug("-> MuestraResultadosAction.borrarIncisosJson");
            logger.debug("cdElemento :: " + cdElemento);
        }

        gridIncisos = (List<Map<String, String>>) session.get(GRID_INCISOS);
        logger.debug("::: gridIncisos before :::::: " + gridIncisos);

        if (gridIncisos != null && !gridIncisos.isEmpty()) {
            for (Map<String, String> gridInciso : gridIncisos) {
                if (cdElemento.equals(gridInciso.get("cdElemento"))) {
                    gridIncisos.remove(gridInciso);
                    break;
                }
            }
        }

        logger.debug("::: gridIncisos after :::: " + gridIncisos);
        session.put(GRID_INCISOS, gridIncisos);

        success = true;

        return SUCCESS;
    }

    /**
     * @param gridIncisos2
     * @return
     */
    private String settingCdElement(List<Map<String, String>> gridIncisos2) {
        if (log.isDebugEnabled()) {
            logger.debug("-> MuestraResultadosAction.settingCdElement");
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
    private List<NameLabelControlVO> obtenLabelNames(String xml) {
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
		                    String hiddenValue = null;
		                    List nestedColsHidden = column.getChildren("hidden");
		                    logger.debug("::::::::::::::: nestedColsHidden ::: " + nestedColsHidden);
		                    if (!nestedColsHidden.isEmpty()) {
		                        for (int k = 0; k < nestedColsHidden.size(); k++) {
		                            Element attributeHidden = (Element) nestedColsHidden.get(k);

		                            hiddenValue = attributeHidden.getText();
		                            logger.debug("::::::::::::::: hiddenValue ::: " + hiddenValue);
		                        }
		                    }
		                    //////////////////////////////////

		                    if (nestedColsHidden.isEmpty() || "false".equals(hiddenValue)) {
		                        List nestedCols = column.getChildren("fieldLabel");

		                        for (int k = 0; k < nestedCols.size(); k++) {
		                            Element attribute = (Element) nestedCols.get(k);

		                            String fieldLabel = attribute.getText();
		                            logger.debug("::::::::::::::: fieldLabel ::: " + fieldLabel);

		                            nameLabelControlVO.setFieldName(fieldLabel);
		                        }

		                        nestedCols = column.getChildren("name");

		                        for (int l = 0; l < nestedCols.size(); l++) {
		                            Element attribute = (Element) nestedCols.get(l);

		                            String name = attribute.getText();

		                            StringTokenizer st = new StringTokenizer(name, ".");
		                            st.nextToken();
		                            name = st.nextToken();
		                            logger.debug("::::::::::::::: name ::: " + name);

		                            nameLabelControlVO.setName(name);
		                        }

		                        listaFieldNames.add(nameLabelControlVO);
		                    }
		                }
		            }
		        } catch (Exception e) {
		            logger.error("---------> ERROR al leer el XML :");
		            logger.error(e);
		        }

		        logger.debug("--> listaFieldNames ::: " + listaFieldNames);

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

    /**
     * @return the elementsMasterGrid
     */
    public List<ExtElement> getElementsMasterGrid() {
        return elementsMasterGrid;
    }

    /**
     * @param elementsMasterGrid the elementsMasterGrid to set
     */
    public void setElementsMasterGrid(List<ExtElement> elementsMasterGrid) {
        this.elementsMasterGrid = elementsMasterGrid;
    }

    /**
	 * @return the hparameters
	 */
	public Map<String, String> getHparameters() {
	    return hparameters;
	}

	/**
	 * @param hparameters the hparameters to set
	 */
	public void setHparameters(Map<String, String> hparameters) {
	    this.hparameters = hparameters;
    }
}

