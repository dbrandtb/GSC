/**
 * 
 */
package mx.com.aon.flujos.endoso.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.VariableKernel;
import mx.com.aon.flujos.endoso.model.AccesoriosVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.utils.Constantes;
import mx.com.ice.kernel.constants.ConstantsKernel;
import mx.com.ice.services.exception.manager.ExceptionManager;
import mx.com.ice.services.to.Campo;
import mx.com.ice.services.to.ResultadoTransaccion;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.biosnet.ice.ext.elements.form.ComboControl;
import com.biosnet.ice.ext.elements.form.ExtElement;
import com.biosnet.ice.ext.elements.form.SimpleCombo;
import com.biosnet.ice.ext.elements.form.TextFieldControl;
import com.biosnet.ice.ext.elements.utils.ComboBoxlUtils;

/**
 * @author eflores
 * @date 09/10/2008
 *
 */
public class AccesoriosEndosoAction extends PrincipalEndosoAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private List<AccesoriosVO> listAccesoriosIncisos;
    private boolean success;
    
    private String nmObjeto;
    private String cdTipoObjeto;
    private String dsObjeto;
    private String ptObjeto;
    private List<ExtElement> dextAccesorios;

    private String claveObjeto;
    private String descripcionObjeto;
    private String labelCombo;
    private List<ExtElement> itemLista;
    private boolean valido;
    private boolean itemListaAccValido;
    private List<BaseObjectVO> listaTipo;
    
    private TreeMap<String, String> parameters;
    private String montoCotizar;
    private String descripcionTipo;
    
    private String saved;
    private String mensajeError;
    
    /**
     * Consulta para datos del grid de accesorios
     * @return SUCCESS
     * @author Alejandro Garcia
     * @throws ApplicationException 
     * @throws mx.com.ice.services.exception.ApplicationException 
     */
    @SuppressWarnings("unchecked")
    public String accesoriosGridJson() throws mx.com.ice.services.exception.ApplicationException, 
            ApplicationException{
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        
        cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSituac = obtieneNmSituac(); 
        
        if (logger.isDebugEnabled()){
            logger.debug("---> AccesoriosEndosoAction.accesoriosGridJson");
            logger.debug("cdUnieco     :" + cdUnieco);
            logger.debug("cdRamo       :" + cdRamo);
            logger.debug("estado       :" + estado);
            logger.debug("nmPoliza     :" + nmPoliza);
            logger.debug("nmSituac     :" + nmSituac);
        }
        
        listAccesoriosIncisos = new ArrayList<AccesoriosVO>(); 
        Map <String,String> param = new HashMap<String,String>();
        param.put("cdunieco", cdUnieco);
        param.put("cdramo", cdRamo);
        param.put("estado", estado);
        param.put("nmpoliza", nmPoliza);
        param.put("nmsituac", nmSituac);
        
        try{
            PagedList pagedList = endosoManager.getPagedList(param,"ENDOSOS_GRID_ACCESORIOS_INCISOS", start, limit);
            if (pagedList != null) {
                listAccesoriosIncisos = pagedList.getItemsRangeList();
                totalCount = pagedList.getTotalItems();
            }
        }catch(ApplicationException ex){
            if (logger.isDebugEnabled())
                logger.debug("accesoriosGridJson EXCEPTION:: " + ex);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(".. listAccesoriosIncisos : " + listAccesoriosIncisos);
        }
        success = true;
        return SUCCESS;
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public String editarAccesorio() throws Exception{
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSuplem = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
        nmSituac = obtieneNmSituac(); 
        
        //UserVO usuario = (UserVO)session.get("USUARIO");
        
        if (logger.isDebugEnabled()){
            logger.debug("----> AccesoriosEndosoAction.editarAccesorio");
            logger.debug("cdUnieco          :" + cdUnieco);
            logger.debug("cdRamo            :" + cdRamo);
            logger.debug("estado            :" + estado);
            logger.debug("nmPoliza          :" + nmPoliza);
            logger.debug("nmSituac          :" + nmSituac);
            logger.debug("nmSuplem          :" + nmSuplem);
            logger.debug("nmObjeto          :" + nmObjeto);
            logger.debug("dsObjeto          :" + dsObjeto);
            logger.debug("cdTipoObjeto      :" + cdTipoObjeto);
            logger.debug("ptObjeto          :" + ptObjeto);
            logger.debug("parameters        :" + parameters);
        }
        
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        
        parameters = obtenParametersPantalla(parameters);
        //nmObjeto = getNmObjeto(parameters);
        
        try {
            kernelManager.guardarAccesoriosAtributos(idSesion, globalVarVO, parameters,
                    cdTipoObjeto, ptObjeto, dsObjeto, "U", nmObjeto);
            if (logger.isDebugEnabled()){
                logger.debug(":::::: Se salvaron los bloques b6 y b6b exitosamente :::::: ");
            }
        } catch (mx.com.ice.services.exception.ApplicationException appExc) {
            logger.error("::::::::::::::::::::::: ERROR :::::::::::::::::::::::::::");
            logger.error(appExc);
            mensajeError = appExc.getMessage();
            saved = "false";
        }
        
        success = true;
        return SUCCESS;
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */
    public String borrarAccesorio() throws Exception{
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSuplem = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
        nmSituac = obtieneNmSituac(); 
        
        //UserVO usuario = (UserVO)session.get("USUARIO");
        
        if (logger.isDebugEnabled()){
            logger.debug("----> AccesoriosEndosoAction.borrarAccesorio");
            logger.debug("cdUnieco          :" + cdUnieco);
            logger.debug("cdRamo            :" + cdRamo);
            logger.debug("estado            :" + estado);
            logger.debug("nmPoliza          :" + nmPoliza);
            logger.debug("nmSituac          :" + nmSituac);
            logger.debug("nmSuplem          :" + nmSuplem);
            logger.debug("nmObjeto          :" + nmObjeto);
            logger.debug("cdTipoObjeto      :" + cdTipoObjeto);
        }
        
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        kernelManager.borrarAccesoriosBloques(globalVarVO, nmObjeto, idSesion);
        
        success = true;
        return SUCCESS;
    }
    
    /**
     * 
     * @return SUCCESS
     * @throws Exception
     *             Metodo que guarda un equipo especial.
     */
    public String guardaAccesorio() throws Exception {

        if (logger.isDebugEnabled()){
            logger.debug("----> AccesoriosEndosoAction.guardaAccesorio");
            logger.debug("descripcionTipo   : " + descripcionTipo);
            logger.debug("cdTipoObj session : " + (String) session.get("CLAVE_OBJETO_ESPECIAL"));
            logger.debug("montoCotizar      : " + montoCotizar);
            logger.debug("parameters        : " + parameters);
        }
        
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        String claveTipoObj = (String) session.get("CLAVE_OBJETO_ESPECIAL");
        //String nmObjeto = null;
        
        if (session.containsKey(Constantes.GLOBAL_VARIABLE_CONTAINER)) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
            
            cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
            cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
            estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
            nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
            nmSuplem = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
            nmSituac = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSituacion());
            
            parameters = obtenParametersPantalla(parameters);
            //nmObjeto = getNmObjeto(parameters);
            
            try {
                kernelManager.guardarAccesoriosAtributos(idSesion, globalVarVO, parameters,
                        claveTipoObj, montoCotizar, descripcionTipo, "I", null);
                if (logger.isDebugEnabled()){
                    logger.debug(":::::: Se salvaron los bloques b6 y b6b exitosamente :::::: ");
                }
            } catch (mx.com.ice.services.exception.ApplicationException appExc) {
                logger.error("::::::::::::::::::::::: ERROR :::::::::::::::::::::::::::");
                logger.error(appExc);
                mensajeError = appExc.getMessage();
                saved = "false";
            }
            
        } else {
            success = false;
        }
        return SUCCESS;
    }  

    /**
     * @param parameters2
     * @return
     */
    private TreeMap<String, String> obtenParametersPantalla(TreeMap<String, String> parameters2) {
        if (logger.isDebugEnabled()){
            logger.debug("----> AccesoriosEndosoAction.getParametersPantalla");
            logger.debug(":: parameters2 :: " + parameters2);
        }
        TreeMap<String, String> parametersWithoutCdtipobj = new TreeMap<String, String>();
        
        if (parameters2 == null || parameters2.isEmpty()) {
            return new TreeMap<String, String>();
        }
        
        Iterator<String> itParams = parameters2.keySet().iterator();
        String param = null;
        String[] cves = null;
        while (itParams.hasNext()) {
            param = itParams.next();
            if (logger.isDebugEnabled()){
                logger.debug(":: param :: " + param);
            }
            cves = param.split("_");
            if (cves != null) {
                if (cves.length == 4) {            
                    parametersWithoutCdtipobj.put(
                        new StringBuilder().append(cves[0])
                                .append("_")
                                .append(cves[1])
                                .append("_")
                                .append(cves[3])
                                .toString(), 
                        parameters2.get(param));
                } else if (cves.length == 3) {
                    parametersWithoutCdtipobj.put(
                            param, 
                            parameters2.get(param));
                }
            }
        }
        
        if (logger.isDebugEnabled()){
            logger.debug(":: parametersWithoutCdtipobj :: " + parametersWithoutCdtipobj);
        }
        
        return parametersWithoutCdtipobj;
    }

    /**
     * Consulta de detalles de Accesorios
     * @return SUCCESS
     * @author Alejandro Garcia
     * @throws mx.com.ice.services.exception.ApplicationException 
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public String obtenPantallaAccesorios() throws Exception{
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSuplem = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
        //fechaVencimiento = globalVarVO.getValueVariableGlobal(VariableKernel.FechaVencimiento());
        //TODO quitar hardcode
        //fechaVencimiento = "02/07/2009";
        nmSituac = obtieneNmSituac(); 
        
        //UserVO usuario = (UserVO)session.get("USUARIO");
        
        if (logger.isDebugEnabled()){
            logger.debug("----> AccesoriosEndosoAction.obtenPantallaAccesorios");
            logger.debug("cdUnieco          :" + cdUnieco);
            logger.debug("cdRamo            :" + cdRamo);
            logger.debug("estado            :" + estado);
            logger.debug("nmPoliza          :" + nmPoliza);
            logger.debug("nmSituac          :" + nmSituac);
            logger.debug("nmSuplem          :" + nmSuplem);
            logger.debug("nmObjeto          :" + nmObjeto);
            logger.debug("cdTipoObjeto      :" + cdTipoObjeto);
            logger.debug("dsObjeto          :" + dsObjeto);
            logger.debug("ptObjeto          :" + ptObjeto);
        }
        
        Map <String,String> param = new HashMap<String,String>();
        param.put("cdunieco", cdUnieco);
        param.put("cdramo", cdRamo);
        param.put("estado", estado);
        param.put("nmpoliza", nmPoliza);
        param.put("nmsituac", nmSituac);
        param.put("nmobjeto", nmObjeto);
        param.put("cdtipoobjeto", cdTipoObjeto);
        param.put("cdatribu", "");  
        
        try{
            //dextAccesorios = endosoManager.getDatosRolExt(param, "ENDOSOS_OBTIENE_DATOS_ACCESORIOS");
            dextAccesorios = obtenDextAccesoriosList(param, nmObjeto);
            logger.debug(":::::::: dextAccesorios : " + dextAccesorios);
        }catch(ApplicationException ex){
            if (logger.isDebugEnabled())
                logger.debug("consultaDetalleRecibos EXCEPTION:: " + ex);
        }
        
        success = true;     
        return SUCCESS;
    }
    
    /**
     * @param param
     * @return
     * @throws mx.com.ice.services.exception.ApplicationException 
     * @throws Exception 
     */
    private List<ExtElement> obtenDextAccesoriosList(Map<String, String> param, String nmObjeto) 
            throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("----> AccesoriosEndosoAction.getDextAccesoriosList");
            logger.debug("::::: param       : " + param);
            logger.debug("::::: nmObjeto    : " + nmObjeto);
        }
        List<ExtElement> listExtElements = new ArrayList<ExtElement>();
        
        List<ExtElement> itemListaAcc = endosoManager.getItems(param.get("cdtipoobjeto"), param.get("cdunieco"), 
                param.get("cdramo"), param.get("estado"),
                param.get("nmpoliza"), param.get("nmsituac"), nmObjeto);
        List<ComboControl> comboElements = endosoManager.getCombos(param.get("cdtipoobjeto"), param.get("cdunieco"), 
                param.get("cdramo"), param.get("estado"),
                param.get("nmpoliza"), param.get("nmsituac"), "");
        logger.debug("#######itemListaAcc::#########" + itemListaAcc);
        logger.debug("#######comboElements::#########" + comboElements);
        
        String[] backupTables = new String[comboElements.size()];
        if (comboElements != null && !comboElements.isEmpty()) {
            int store = 0;
            for (ComboControl comboControl : comboElements) {
                logger.debug("####### BackupTable : " + comboControl.getBackupTable());
                backupTables[store++] = comboControl.getBackupTable();
            }
        }
        
        //se obtienen los campos y sus valores
        String[] camposValores = null;
        if (itemListaAcc != null && !itemListaAcc.isEmpty()) {
            camposValores = llenaCamposValoresMap(itemListaAcc, nmObjeto);
        }
        
        if (!itemListaAcc.isEmpty()) {
            valido = true;
            //if (comboElements != null && !comboElements.isEmpty()) {
                ComboBoxlUtils comboUtils = new ComboBoxlUtils();
                
                List<SimpleCombo> storeElements = comboUtils.getDefaultSimpleComboList(comboElements,
                        ServletActionContext.getRequest().getContextPath() +
                        Constantes.URL_ACTION_COMBOS);
                //TODO cambiar la forma de obtener los stores de los combos
                
                logger.debug("##STORE DE LOS COMBOS##" + storeElements);
                //if (storeElements != null && !storeElements.isEmpty()) {
                    
                int numAtributo = 0;
                int numStore = 0;
                String valorAtributo = null;
                String descripcionAtributo = null;
                    SimpleCombo simpleCombo = null;
                    TextFieldControl textField = null;
                    String id = null;
                    
                    for (ExtElement elements : itemListaAcc) {
                        if (elements instanceof SimpleCombo) {
                            simpleCombo = (SimpleCombo) elements;
                            id = simpleCombo.getId();
                            
                            if (storeElements != null && !storeElements.isEmpty()) {
                                for (SimpleCombo scombo : storeElements) {
                                
                                    if (id.equals(scombo.getId())) {
                                        valorAtributo = camposValores[numAtributo];
                                        logger.debug(":: valorAtributo -> " + valorAtributo);
                                        descripcionAtributo = obtenValorAtributo(
                                                backupTables[numStore++], 
                                                valorAtributo);
                                        logger.debug(":: descripcionAtributo -> " + descripcionAtributo);
                                        
                                        scombo.setHiddenValue(valorAtributo);
                                        scombo.setValue(descripcionAtributo);
                                        
                                        if (!scombo.isHidden()) {
                                            listExtElements.add(scombo);
                                        }
                                    }                                    
                                }
                            }
                        } else if (elements instanceof TextFieldControl) {
                            textField = (TextFieldControl) elements; 
                            textField.setValue(camposValores[numAtributo]);
                            
                            if (!textField.isHidden()) {
                                listExtElements.add(textField);
                            }
                        } 
                        
                        numAtributo++;
                    }
                //}
            //}
            logger.debug("Lista Ext Edicion final :::: " + listExtElements);
        } else {
            logger.debug("La session no trae nada");
            valido = false;
        }
        
        return listExtElements;
    }

    /**
     * @param extDatosElem 
     * @return
     * @throws ApplicationException 
     * @throws mx.com.ice.services.exception.ApplicationException 
     */
    private String[] llenaCamposValoresMap(List<ExtElement> extDatosElem, String nmObjeto) 
            throws mx.com.ice.services.exception.ApplicationException, ApplicationException {
        if (logger.isDebugEnabled()){
            logger.debug("------> AccesoriosEndosoAction.llenaCamposValoresMap");
            logger.debug("------> extDatosElem : " + extDatosElem);
            logger.debug("------> nmObjeto     : " + nmObjeto);
        }
        
        String[] camposValores = new String[extDatosElem.size()];
        Campo[] campos = new Campo[extDatosElem.size()];
        ResultadoTransaccion rt = null;
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        String parametro = null;
        String nombreCampo = null;        
        StringTokenizer st = null;
        
        for (int i = 0; i < campos.length; i++) {
            parametro = ((ExtElement)extDatosElem.get(i)).getId();
            st = new StringTokenizer(parametro, "_");
            st.nextToken();
            nombreCampo = new StringBuilder().append(st.nextToken()).append('|').append(nmObjeto).toString();
            logger.debug("   ::::: nombreCampo : " + nombreCampo);
            campos[i] = new Campo(nombreCampo, "");            
        }
        
        if (logger.isDebugEnabled()){
            for (int i = 0; i < campos.length; i++) {
                logger.debug("------> campos : " + campos[i]);  
            }
        }
        
        rt = ExceptionManager.manage(kernelManager.obtenerDatosBloque(
                idSesion, ConstantsKernel.BLOQUE_B6B, campos));
        if (rt != null) {
            for (int index = 0; index < campos.length; index++) {
                camposValores[index] = rt.getCampos()[index].getValor();  
            }
        }
        if (logger.isDebugEnabled()){
            for (int k = 0; k < campos.length; k++) {
                logger.debug(":::::::: camposValores ::: " + campos[k]);  
            }
        }
                
        return camposValores;
    }
    
    /**
     * 
     * @return SUCCESS
     * @throws Exception
     *             Metodo que carga la lista para el combo de tipos.
     * 
     */
    public String obtenTipos() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("----> AccesoriosEndosoAction.obtenTipos");
        }
        if (session.containsKey(Constantes.GLOBAL_VARIABLE_CONTAINER)) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
            String CDRAMO = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
            String CDTIPSIT = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion());

            logger.debug("CDRAMO###tipos" + CDRAMO);
            logger.debug("CDTIPSIT####tipos" + CDTIPSIT);

            listaTipo = endosoManager.getTipos(CDRAMO, CDTIPSIT);

            success = true;
        } else {
            success = false;
        }
        return SUCCESS;
    }
    
    /**
     * 
     * @return
     * @throws Exception
     * Metodo que carga la lista para mostrar atributos variables.
     * 
     */
    @SuppressWarnings("unchecked")
    public String obtenCombos() throws Exception {
        if (logger.isDebugEnabled()){
            logger.debug("----> AccesoriosEndosoAction.obtenCombos");
            logger.debug("###### codigoObjeto : " + claveObjeto);
            logger.debug("###### descripcionObj : " + descripcionObjeto);
        }
        
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSituac = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSituacion());
        
        if (StringUtils.isNotBlank(descripcionObjeto)) {
            labelCombo = descripcionObjeto;
            session.put("DESCRIPCION_OBJETO_ESPECIAL", descripcionObjeto);
        }
        if (StringUtils.isNotBlank(claveObjeto)) {
            session.put("CLAVE_OBJETO_ESPECIAL", claveObjeto);
            logger.debug("########### Entro al metodo obtenCombos ###########");
            if (itemLista == null) {
                itemLista = new ArrayList<ExtElement>();
            }
            List<ExtElement> itemListaAcc = endosoManager.getItems(claveObjeto, cdUnieco, cdRamo, estado,
                    nmPoliza, nmSituac, "");
            List<ComboControl> comboElements = endosoManager.getCombos(claveObjeto, cdUnieco, cdRamo, estado,
                    nmPoliza, nmSituac, "");
            logger.debug("#######itemListaAcc::#########" + itemListaAcc);
            logger.debug("#######comboElements::#########" + comboElements);
            if (!itemListaAcc.isEmpty()) {
                itemListaAccValido = true;
                //if (comboElements != null && !comboElements.isEmpty()) {
                    ComboBoxlUtils comboUtils = new ComboBoxlUtils();
                    
                    List<SimpleCombo> storeElements = comboUtils.getDefaultSimpleComboList(comboElements,
                            ServletActionContext.getRequest().getContextPath() +
                            "/flujocotizacion/obtenerListaComboOttabval.action");
                    
                    logger.debug("##STORE DE LOS COMBOS##" + storeElements);
                    //if (storeElements != null && !storeElements.isEmpty()) {
                        
                        SimpleCombo simpleCombo = null;
                        TextFieldControl textField = null;
                        String id = null;
                        
                        for (ExtElement elements : itemListaAcc) {
                            if (elements instanceof SimpleCombo) {
                                simpleCombo = (SimpleCombo) elements;
                                id = simpleCombo.getId();
                                
                                for (SimpleCombo scombo : storeElements) {
                                
                                    if (id.equals(scombo.getId())) {
                                        if (!scombo.isHidden()) {
                                            itemLista.add(scombo);
                                        }
                                    }                                    
                                }
                            } else if (elements instanceof TextFieldControl) {
                                textField = (TextFieldControl) elements; 
                                
                                if (!textField.isHidden()) {
                                    itemLista.add(textField);
                                }
                            } 
                        }
                    //}
                //}
                logger.debug("Lista Ext final :::: " + itemLista);
            } else {
                logger.debug("La session no trae nada");
                itemListaAccValido = false;
            }
            success = true;
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
     * @return the listAccesoriosIncisos
     */
    public List<AccesoriosVO> getListAccesoriosIncisos() {
        return listAccesoriosIncisos;
    }

    /**
     * @param listAccesoriosIncisos the listAccesoriosIncisos to set
     */
    public void setListAccesoriosIncisos(List<AccesoriosVO> listAccesoriosIncisos) {
        this.listAccesoriosIncisos = listAccesoriosIncisos;
    }

    /**
     * @return the cdTipoObjeto
     */
    public String getCdTipoObjeto() {
        return cdTipoObjeto;
    }

    /**
     * @param cdTipoObjeto the cdTipoObjeto to set
     */
    public void setCdTipoObjeto(String cdTipoObjeto) {
        this.cdTipoObjeto = cdTipoObjeto;
    }

    /**
     * @return the nmObjeto
     */
    public String getNmObjeto() {
        return nmObjeto;
    }

    /**
     * @param nmObjeto the nmObjeto to set
     */
    public void setNmObjeto(String nmObjeto) {
        this.nmObjeto = nmObjeto;
    }

    /**
     * @return the claveObjeto
     */
    public String getClaveObjeto() {
        return claveObjeto;
    }

    /**
     * @param claveObjeto the claveObjeto to set
     */
    public void setClaveObjeto(String claveObjeto) {
        this.claveObjeto = claveObjeto;
    }

    /**
     * @return the descripcionObjeto
     */
    public String getDescripcionObjeto() {
        return descripcionObjeto;
    }

    /**
     * @param descripcionObjeto the descripcionObjeto to set
     */
    public void setDescripcionObjeto(String descripcionObjeto) {
        this.descripcionObjeto = descripcionObjeto;
    }

    /**
     * @return the itemLista
     */
    public List<ExtElement> getItemLista() {
        return itemLista;
    }

    /**
     * @param itemLista the itemLista to set
     */
    public void setItemLista(List<ExtElement> itemLista) {
        this.itemLista = itemLista;
    }

    /**
     * @return the labelCombo
     */
    public String getLabelCombo() {
        return labelCombo;
    }

    /**
     * @param labelCombo the labelCombo to set
     */
    public void setLabelCombo(String labelCombo) {
        this.labelCombo = labelCombo;
    }

    /**
     * @return the valido
     */
    public boolean isValido() {
        return valido;
    }

    /**
     * @param valido the valido to set
     */
    public void setValido(boolean valido) {
        this.valido = valido;
    }

    /**
     * @return the listaTipo
     */
    public List<BaseObjectVO> getListaTipo() {
        return listaTipo;
    }

    /**
     * @param listaTipo the listaTipo to set
     */
    public void setListaTipo(List<BaseObjectVO> listaTipo) {
        this.listaTipo = listaTipo;
    }

    /**
     * @return the montoCotizar
     */
    public String getMontoCotizar() {
        return montoCotizar;
    }

    /**
     * @param montoCotizar the montoCotizar to set
     */
    public void setMontoCotizar(String montoCotizar) {
        this.montoCotizar = montoCotizar;
    }

    /**
     * @return the parameters
     */
    public TreeMap<String, String> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(TreeMap<String, String> parameters) {
        this.parameters = parameters;
    }

    /**
     * @return the descripcionTipo
     */
    public String getDescripcionTipo() {
        return descripcionTipo;
    }

    /**
     * @param descripcionTipo the descripcionTipo to set
     */
    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
    }

    /**
     * @return the mensajeError
     */
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * @param mensajeError the mensajeError to set
     */
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    /**
     * @return the saved
     */
    public String getSaved() {
        return saved;
    }

    /**
     * @param saved the saved to set
     */
    public void setSaved(String saved) {
        this.saved = saved;
    }

    /**
     * @return the dsObjeto
     */
    public String getDsObjeto() {
        return dsObjeto;
    }

    /**
     * @param dsObjeto the dsObjeto to set
     */
    public void setDsObjeto(String dsObjeto) {
        this.dsObjeto = dsObjeto;
    }

    /**
     * @return the ptObjeto
     */
    public String getPtObjeto() {
        return ptObjeto;
    }

    /**
     * @param ptObjeto the ptObjeto to set
     */
    public void setPtObjeto(String ptObjeto) {
        this.ptObjeto = ptObjeto;
    }

    /**
     * @return the dextAccesorios
     */
    public List<ExtElement> getDextAccesorios() {
        return dextAccesorios;
    }

    /**
     * @param dextAccesorios the dextAccesorios to set
     */
    public void setDextAccesorios(List<ExtElement> dextAccesorios) {
        this.dextAccesorios = dextAccesorios;
    }

    /**
     * @return the itemListaAccValido
     */
    public boolean isItemListaAccValido() {
        return itemListaAccValido;
    }

    /**
     * @param itemListaAccValido the itemListaAccValido to set
     */
    public void setItemListaAccValido(boolean itemListaAccValido) {
        this.itemListaAccValido = itemListaAccValido;
    }
}
