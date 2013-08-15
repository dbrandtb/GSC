/**
 * 
 */
package mx.com.aon.flujos.endoso.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.VariableKernel;
import mx.com.aon.flujos.endoso.service.EndosoManager;
import mx.com.aon.kernel.model.MpoliagrVO;
import mx.com.aon.kernel.model.ValoresXDefectoCoberturaVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.procesos.emision.model.DatosAdicionalesVO;
import mx.com.aon.procesos.emision.model.EmisionVO;
import mx.com.aon.procesos.emision.model.FuncionPolizaVO;
import mx.com.aon.procesos.emision.model.ObjetoAsegurableVO;
import mx.com.aon.procesos.emision.model.PolizaDetVO;
import mx.com.aon.procesos.emision.model.RecibosVO;
import mx.com.aon.utils.Constantes;
import mx.com.ice.kernel.constants.ConstantsKernel;
import mx.com.ice.services.exception.manager.ExceptionManager;
import mx.com.ice.services.to.Campo;
import mx.com.ice.services.to.ResultadoTransaccion;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;
import net.sf.json.JSONFunction;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.biosnet.ice.ext.elements.data.StoreVO;
import com.biosnet.ice.ext.elements.form.ComboControl;
import com.biosnet.ice.ext.elements.form.ExtElement;
import com.biosnet.ice.ext.elements.form.SimpleCombo;
import com.biosnet.ice.ext.elements.form.TextFieldControl;
import com.biosnet.ice.ext.elements.utils.ComboBoxlUtils;

/**
 * @author eflores
 * @date 28/08/2008
 *
 */
public class IncisoEndosoAction extends PrincipalEndosoAction {
    private String producto;
    private String poliza;
    private String nombreAsegurado;
    private String aseguradora;
    private String inicioVigencia;
    private String finVigencia;
    private String statusPoliza;
    private String fechaInicioFin;
    private String cdAgrupa;
    private String itemsPantalla;
    private String cdInciso;
    private String nmObjeto;
    private String dsDescripcion;
    
    private String store;
    private Map<String, String> parameters;
    
    private List<EmisionVO> emisiones;
    private List<PolizaDetVO> polizaDet;
    private List<ObjetoAsegurableVO> objAsegurable;
    private List<FuncionPolizaVO> fnPoliza;
    private List<DatosAdicionalesVO> datAdicionales;
    
    private List<StoreVO> storeDatosElements;
    private String beforeRenderDatosItems;
    private ArrayList<ExtElement> extDatosElements;
    private String botonDatVariables = "";
    
    @SuppressWarnings("unchecked")
    private List listProductos;
    
    @SuppressWarnings("unchecked")
    private List listAseguradoras;
    
    @SuppressWarnings("unchecked")
    private List listStatusPoliza;
    
    private boolean success;
    private String saved;
    private String mensajeError;
    protected int totalCountFnPol;
    
    private List<RecibosVO> listRecibos;
    private ArrayList<TextFieldControl> dext;
    private List<MpoliagrVO> listMpoliagr;
    
    private boolean valido;
    
    //Catalogos
    private List<BaseObjectVO> listaDomicilio;
    private String codigoDomicilio;
    private String descripcionDomicilio;
    
    private List<BaseObjectVO> listaIntrumentoPago;
    private String codigoInstrumentoPago;
    private String descripcionIntrumentoPago;
    
    private List<BaseObjectVO> listaBancos;
    private String codigoBanco;
    private String descripcionBanco;
    
    private List<BaseObjectVO> listaSucursal;
    private String codigoSucursal;
    private String descripcionSucursal;
    
    private List<BaseObjectVO> listaPersonasUsuario;    
    private String codigoPersonaUsuario;
    private String descripcionPersonaUsuario;
    
    private List<BaseObjectVO> listaTipoTarjeta;    
    private String codigoTipoTarjeta;
    private String descripcionTipoTarjeta;
    
    private String numeroTarjeta;
    private String fechaVencimiento;
    private String digitoVerificador;
    private String numeroCuenta;
    
    private List<BaseObjectVO> listaPersonasUsuarioMultiSelect;
    private List<String> funcionesPersona;
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date hoy = new Date();
    String fechaActual = dateFormat.format(hoy).toString();
    
    private String fechaefectividadendoso;
    
    /**
     * Contiene el tipo de procedimiento
     */
    private String proc;
	private String codExtCargaCombos;
        
    /**
     * 
     */
    private static final long serialVersionUID = 1839776301724353232L;
    
    public String execute(){
        
        return INPUT;
    }
    
    /**
     * Obtiene parte de la pantalla a pintar
     * @return INPUT
     */
    public String obtienePantalla(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("cdElemento","5034");
        params.put("cdRamo","115");
        params.put("cdTitulo","38");
        
        try{
            itemsPantalla = endosoManager.obtienePantalla(params);
        }
        catch(Exception ex){
            logger.debug("obtienePantalla EXCEPTION:: " + ex);
        }
        
        return INPUT;
    }
    
    /**
     * Obtiene los datod adicionales
     * @return SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtieneDatosAdicionales(){
        Map<String, String> params = new HashMap<String, String>();
        /*params.put("cdUnieco", "1");
        params.put("cdRamo", "2");
        params.put("estado", "W");
        params.put("nmPoliza", "10039");*/
        
        params.put("cdUnieco", cdUnieco);
        params.put("cdRamo", cdRamo);
        params.put("estado", estado);
        params.put("nmPoliza", nmPoliza);
        
        try{
            datAdicionales = endosoManager.obtieneDatosAdicionales(params);
        }
        catch(Exception ex){
            logger.debug("obtieneDatosAdicionales EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    /**
     * Obtiene los Productos
     * @return SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtenerProductos(){
        try{
            listProductos = catalogManager.getItemList("OBTIENE_PRODUCTOS_CAT");
        }
        catch(Exception ex){
            logger.debug("obtenerProductos EXCEPTION:: " + ex);
        }
        
        //logger.debug("Productos:: " + itemList);
        
        success = true;
        
        return SUCCESS;
    }
    
    /**
     * Obtiene las Aseguradoras
     * @return SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtenerAseguradoras(){
        try{
            listAseguradoras = catalogManager.getItemList("OBTIENE_ASEGURADORAS_CAT");
        }
        catch(Exception ex){
            logger.debug("obtenerAseguradoras EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    /**
     * Obtiene el status de la poliza
     * @return SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtenerStatusPoliza(){
        try{
            listStatusPoliza = catalogManager.getItemList("OBTIENE_STATUSPOLIZA_CAT");
        }
        catch(Exception ex){
            logger.debug("obtenerStatusPoliza EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    /**
     * Consulta la funcion de poliza
     * @return SUCCESS
     * @throws ApplicationException 
     * @throws mx.com.ice.services.exception.ApplicationException 
     */
    @SuppressWarnings("unchecked")
    public String consultaFuncionPoliza() throws mx.com.ice.services.exception.ApplicationException, 
            ApplicationException{
        //Se obtiene de sesión el objeto globalContainer
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        
        cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSituac = obtieneNmSituac();        
        
        if (logger.isDebugEnabled()){
            logger.debug(".:IncisoEndosoAction->CONSULTA FUNCION EN LA POLIZA:.");
            logger.debug("cdUnieco:: " + cdUnieco);
            logger.debug("cdRamo:: " + cdRamo);
            logger.debug("estado:: " + estado);
            logger.debug("nmPoliza:: " + nmPoliza);
            logger.debug("nmSituac:: " + nmSituac);
        }
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("cdUnieco", cdUnieco);
        params.put("cdRamo", cdRamo);
        params.put("estado", estado);
        params.put("nmPoliza", nmPoliza);
        params.put("nmSituac", nmSituac);
        
        try{
            PagedList pagedList = endosoManager.consultaFuncionPoliza(params, start, limit);
            fnPoliza = pagedList.getItemsRangeList();
            fnPoliza = countRoles(fnPoliza);
            totalCountFnPol = pagedList.getTotalItems();
        }
        catch(ApplicationException ex){
            logger.debug("consultaDetallePoliza EXCEPTION:: " + ex);
        }
        if (logger.isDebugEnabled()){
            logger.debug(":::::: fnPoliza :: " + fnPoliza);
        }
        success = true;
        
        return SUCCESS;
    }

    /**
     * @param fnPoliza2
     * @return
     */
    private List<FuncionPolizaVO> countRoles(List<FuncionPolizaVO> fnPoliza2) {
        if (logger.isDebugEnabled()){
            logger.debug("***** countRoles ******");    
        }
        
        String cdNumeroRol = null;
        String nmaximRol = null;   
        int numeroRoles = 0;
        Map<String, String> mapRoles = new HashMap<String, String>();
        
        for (FuncionPolizaVO polizaVO : fnPoliza2) {
            cdNumeroRol = polizaVO.getCdRol();
            
            if (mapRoles.containsKey(cdNumeroRol)) {
                numeroRoles = Integer.parseInt(mapRoles.get(cdNumeroRol));
                mapRoles.put(cdNumeroRol, String.valueOf(numeroRoles + 1));
            } else {
                mapRoles.put(cdNumeroRol, "1");
            }
            
            nmaximRol = new StringBuilder().append("NMAXIMROL").append(cdNumeroRol).toString();
            
            if (!mapRoles.containsKey(nmaximRol)) {
                mapRoles.put(nmaximRol, polizaVO.getNmaximo());
            }
        }
        
        if (logger.isDebugEnabled()){
            logger.debug("** mapRoles : " + mapRoles);    
        }
        
        for (FuncionPolizaVO polizaVO : fnPoliza2) {
            polizaVO.setTotalPerRol(mapRoles.get(polizaVO.getCdRol()));
        }
        session.put("MAP_ROLES", mapRoles);
        
        return fnPoliza2;
    }

    /**
     * Consulta el objeto Asegurable
     * @return SUCCESS
     */
    public String consultaObjetoAsegurable(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("cdUnieco", cdUnieco);
        params.put("cdRamo", cdRamo);
        params.put("estado", estado);
        params.put("nmPoliza", nmPoliza);
        //params.put("nmSituac", nmSituac);
        params.put("nmSituac", "");
        
        if (logger.isDebugEnabled()){
            logger.debug(".:CONSULTA OBJETO ASEGURABLE:.");
            logger.debug("cdUnieco:: " + cdUnieco);
            logger.debug("cdRamo:: " + cdRamo);
            logger.debug("estado:: " + estado);
            logger.debug("nmPoliza:: " + nmPoliza);
            logger.debug("nmSituac:: " + nmSituac);
        }
        
        try{
            PagedList pagedList = endosoManager.consultaObjetoAsegurable(params, start, limit);
            objAsegurable = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();
        }
        catch(ApplicationException ex){
            logger.debug("consultaDetallePoliza EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    /**
     * Consulta el detalle de poliza (Datos generales de poliza)
     * @return SUCCESS
     */
    public String consultaDetallePoliza(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("cdUnieco", cdUnieco);
        params.put("cdRamo", cdRamo);
        params.put("estado", estado);
        params.put("nmPoliza", nmPoliza);
        
        try{
            polizaDet = endosoManager.consultaPolizaDetalle(params);
        }
        catch(ApplicationException ex){
            logger.debug("consultaDetallePoliza EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    /**
     * Consulta de datos rol
     * @return SUCCESS
     * @author Alejandro Garcia
     */
    @SuppressWarnings("unchecked")
    public String obtenPantallaDatosRol(){
        
        if (logger.isDebugEnabled()){
            logger.debug("***** obtenPantallaDatosRol ******");
            logger.debug("cdUnieco: " + cdUnieco);
            logger.debug("cdRamo: " + cdRamo);
            logger.debug("estado: " + estado);
            logger.debug("nmPoliza: " + nmPoliza);
            logger.debug("nmSituac: " + nmSituac);      
        }
        Map <String,String> param = new HashMap<String,String>();   
        param.put("cdunieco", cdUnieco);
        param.put("cdramo", cdRamo);
        param.put("estado", estado);
        param.put("nmpoliza", nmPoliza);
        param.put("nmsituac", nmSituac);        
        
        try{
            dext = endosoManager.getDatosRolExt(param, "EMISION_OBTIENE_DATOS_ROL_EXT");
            logger.debug("dext: " + dext);
            
            logger.debug("*** CREANDO BOTON : botonDatVariables");

            StringBuilder builder = new StringBuilder();            
            builder.append("{id:'guardarDatosAdicionalesEndosos', text:'Guardar', tooltip:'Guardar datos adicionales de la poliza',");
            if (dext.isEmpty()) {
                builder.append("hidden:true,");
            }

            builder.append("handler:function(){ ");
            /*
             * Si existen elemento Ext y se validaCamposBtnGuardar es verdadero, agregar funcion isValid() para la forma
             */
            if ( !dext.isEmpty() /*&& validaCamposBtnGuardar*/ ) {
            	builder.append("if (Ext.getCmp('datosIncisosForm').form.isValid()) { ");
            }            

            builder.append("Ext.getCmp('datosIncisosForm').form.submit({ waitMsg:'Procesando...', success:function(form,action){ if(Ext.util.JSON.decode(action.response.responseText).saved == \"true\"){ Ext.Msg.alert('Status', \"Datos guardados.\"); }else { Ext.Msg.alert('Error', Ext.util.JSON.decode(action.response.responseText).mensajeError +\". Intente de nuevo.\"); } } }); ");

            /*
             * Si existen elemento Ext y se validaCamposBtnGuardar es verdadero, agregar condicional para mostrar mensaje
             */
            if ( !dext.isEmpty() /*&& validaCamposBtnGuardar*/ ) {
            	builder.append("} else { Ext.MessageBox.alert('Aviso', 'Complete la informaci&oacute;n requerida'); } ");
            }
            
            // Parte final de la definicion del boton 'Guardar'
            builder.append("} }");
            
            botonDatVariables = builder.toString();
            
            logger.debug("**** BOTON ES = " + botonDatVariables);
            
        }catch(ApplicationException ex){
            if (logger.isDebugEnabled())
                logger.debug("consultaDetalleRecibos EXCEPTION:: " + ex);
        } catch (Exception e) {
        	logger.error("**** error en = " + e,e);
        }
        
        success = true;
        
        return SUCCESS;
    }

    /**
     * Consulta de Inciso
     * @return SUCCESS
     * @author Alejandro Garcia
     * @throws mx.com.ice.services.exception.ApplicationException 
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public String obtenPantallaIncisos() throws 
            Exception{
        
    	ResultadoTransaccion rt = null;
    	
        if (globalVarVO == null) {
            //Se obtiene de sesión el objeto globalContainer
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
            globalVarVO.addVariableGlobal(VariableKernel.NumeroSituacion(), nmSituac);
            
            //carga bloques
	        cargaBloques(globalVarVO);
            
            //Para bloque B5
            String idSesion = ServletActionContext.getRequest().getSession().getId();
            Campo[] campos = new Campo[2];
            campos[0] = new Campo("NMSITUAC", "");
            campos[1] = new Campo("CDTIPSIT", "");
            rt = ExceptionManager.manage(kernelManager.obtenerDatosBloque(idSesion, 
                        ConstantsKernel.BLOQUE_B5, campos));
            if (rt != null) {
	             logger.debug("::NMSITUAC= " + rt.getCampos()[0].getValor());
	             logger.debug("::CDTIPSIT= " + rt.getCampos()[1].getValor());
	             
	             globalVarVO.addVariableGlobal(VariableKernel.NumeroSituacion(), rt.getCampos()[0].getValor());
	             globalVarVO.addVariableGlobal(VariableKernel.CodigoTipoSituacion(), rt.getCampos()[1].getValor());
	             
	             session.put(Constantes.GLOBAL_VARIABLE_CONTAINER, globalVarVO);
            }
        }
        
        if (rt != null) {
	        cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
	        cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
	        estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
	        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
	        nmSituac = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSituacion());
	        
	        if (logger.isDebugEnabled()){
	            logger.debug("---> obtenPantallaIncisos");
	            logger.debug("cdunieco     :" + cdUnieco);
	            logger.debug("cdramo       :" + cdRamo);
	            logger.debug("nmpoliza     :" + nmPoliza);
	            logger.debug("nmSituac     :" + nmSituac);
	            logger.debug("cdinciso     :" + cdInciso);
	            logger.debug("dsDescripcion:" + dsDescripcion);
	            logger.debug("aseguradora  :" + aseguradora);
	            logger.debug("producto     :" + producto);
	        }
	        
	        Map <String,String> params = new HashMap<String,String>();
	        params.put("cdunieco", cdUnieco);
	        params.put("cdramo", cdRamo);
	        params.put("estado", estado);
	        params.put("nmpoliza", nmPoliza);
	        params.put("nmsituac", nmSituac);
	
	        try{
	            ArrayList<ExtElement> extDatosElementsCopy = endosoManager.getExtElements(params, "ENDOSOS_DATOS_INCISOS");
	            logger.debug("::::::: extDatosElementsCopy : " + extDatosElementsCopy);
	            
	            extDatosElements = new ArrayList<ExtElement>();
	            
	            //se obtienen los campos y sus valores
	            String[] camposValores = null;
	            if (extDatosElementsCopy != null && !extDatosElementsCopy.isEmpty()) {
	                camposValores = llenaCamposValoresMap(extDatosElementsCopy);
	            }
	            
	            List<ComboControl> comboControlElements = endosoManager.getComboControl(params, "ENDOSOS_DATOS_INCISOS_COMBOS");
	            String[] backupTables = null;
	            if (comboControlElements != null && !comboControlElements.isEmpty()) {
	                int store = 0;
	                backupTables = new String[comboControlElements.size()];
	                for (ComboControl comboControl : comboControlElements) {
	                	if (logger.isDebugEnabled()) {
		                    logger.debug("::::::::::::::: BackupTable : " + comboControl.getBackupTable());
		                    /*//TODO quitar
		                    if ("MARAUCAT".equalsIgnoreCase(comboControl.getBackupTable())) {
		                    	comboControl.setGrouping("1");
		                    }
		                    ///////////////////////
*/		                    logger.debug("::::::::::::::: Grouping    : " + comboControl.getGrouping());
		                    logger.debug("::::::::::::::: GroupingId  : " + comboControl.getGroupingId());
		                    logger.debug(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
	                	}
	                	
	                    backupTables[store++] = comboControl.getBackupTable();
	                }
	            }
	            logger.debug("::::::: comboControlElements : " + comboControlElements);
	            
	            StringTokenizer st = null;
	            String cve = null;
	            ValoresXDefectoCoberturaVO valoresXDefectoCoberturaVO = null;
	            String valorAtributo = null;
	            String descripcionAtributo = null;
	            String tempValorAtributo = null;
	            
	            List<SimpleCombo> simpleComboElements;
	            boolean validaCamposBtnGuardar = false; // Variable que decidirá si la forma 'datosIncisosForm' deberá ser validada
	            
	            Map<String, String> entryDataCombo = null;
	            Map<String,String> storesNames = null;
	            Map<String, String> comboLabelValue = null;
	            
	            /* ***********************************
	             * Validacion si existen elementos Ext
	             *********************************** */
	            if (extDatosElementsCopy != null && !extDatosElementsCopy.isEmpty()) {
	            	
	                if (comboControlElements != null && !comboControlElements.isEmpty()) {
	                    for (ComboControl comboControl : comboControlElements) {
	                        comboControl.setName(StringUtils.replace(comboControl.getName(), "+", "p"));
	                        comboControl.setId(StringUtils.replace(comboControl.getId(), "+", "p"));
	                    }
	                }
	                //SimpleComboUtils comboUtils = new SimpleComboUtils();
	                ComboBoxlUtils comboUtils = new ComboBoxlUtils();
	
	                String url = ServletActionContext.getRequest().getContextPath()
	                	+ "/flujocotizacion/obtenerListaComboOttabval.action";
	                
	                simpleComboElements = comboUtils.getDefaultSimpleComboList(comboControlElements, url);
	                logger.debug("::::::: simpleComboElements : " + simpleComboElements);
	                
	                    int numAtributo = 0;
	                    int numStore = 0;
	                    String id = null;
	                    SimpleCombo simpleCombo = null;
	                    TextFieldControl textField = null;
	                    parameters = new HashMap<String, String>();
	                    
	                    for (ExtElement element : extDatosElementsCopy) {
	                        id = StringUtils.replace(element.getId(), "+", "p");
	                        
	                        /* ******************************************* 
	                         *  Caso: Combo
	                         ********************************************* */
	                        if (element instanceof SimpleCombo) {
	                            simpleCombo = (SimpleCombo) element;
	                            
	                            for (SimpleCombo scombo : simpleComboElements) {
	                                if (id.equals(scombo.getId())) {
	                                	if (logger.isDebugEnabled()){
	                                		logger.debug(":::::::::: scombo.getId() : " + scombo.getId());
	                                	}
	                                    
	                                    if (entryDataCombo == null) {
	                                    	entryDataCombo = new HashMap<String, String>();
	                                    }
	                                    
	                                    if (storesNames == null) {
	                                    	storesNames = new HashMap<String, String>();
	                                    }
	                                    
	                                    if (comboLabelValue == null) {
	                                    	comboLabelValue = new HashMap<String, String>();
	                                    }
	                                    //////////////////////////////////
	                                    valorAtributo = camposValores[numAtributo];
	                                    if (logger.isDebugEnabled()){
	                                    	logger.debug(":: valorAtributo -> " + valorAtributo);
	                                    }
	                                    
	                                    if (StringUtils.isNotBlank(valorAtributo)) {
		                                    descripcionAtributo = obtenValorAtributo(
		                                            backupTables[numStore], 
		                                            valorAtributo,
		                                            entryDataCombo);
		                                    
		                                    storesNames.put(scombo.getId(), backupTables[numStore]);
		                                    
		                                    comboLabelValue.put(scombo.getId(), valorAtributo);
		                                    
		                                    if (logger.isDebugEnabled()){
			                                    logger.debug(":: descripcionAtributo -> " + descripcionAtributo);
			                                    logger.debug(":: entryDataCombo -> " + entryDataCombo);
			                                    logger.debug(":: storesNames -> " + storesNames);
			                                    logger.debug(":: comboLabelValue -> " + comboLabelValue);
		                                    }
		                                    
		                                    scombo.setValue(descripcionAtributo);
		                                    scombo.setHiddenValue(valorAtributo);
		                                    scombo.setName(StringUtils.replace(simpleCombo.getName(), "+", "p"));
		                                    scombo.setHiddenName(StringUtils.replace(simpleCombo.getName(), "+", "p"));
		                                    scombo.setId(id);
		                                    
		                                    ////////////////////////////////////////////////////////////
		                                    for (ComboControl ccontrol : comboControlElements) {
		                                    	if (ccontrol.getId().equals(id)) {
		                                    		if (logger.isDebugEnabled()){
		        	                                    logger.debug(":: id -> " + id);
		        	                                    logger.debug(":: ccontrol -> " + ccontrol);
		        	                                    logger.debug(":: Grouping -> " + ccontrol.getGrouping());
		        	                                    logger.debug(":: GroupingId -> " + ccontrol.getGroupingId());
		                                            }
		                                    		
		                                    		if (StringUtils.isNotBlank(ccontrol.getGrouping())) {
		                                    			if ("1".equals(ccontrol.getGroupingId())) {
		                                    				scombo.setStore(obtenDefaultJsonStore(
		                                    						url,  
		                                    						ccontrol.getBackupTable(),
		                                    						valorAtributo,
		                                    						"0"));	
		                                    				tempValorAtributo = valorAtributo;
		                                    			} else if ("2".equals(ccontrol.getGroupingId())) {
		                                    				scombo.setStore(obtenDefaultJsonStore(
		                                    						url,  
		                                    						ccontrol.getBackupTable(),
		                                    						tempValorAtributo,
		                                    						"0"));	
		                                    				tempValorAtributo = valorAtributo;
		                                    			} else if ("3".equals(ccontrol.getGroupingId())) {
		                                    				scombo.setStore(obtenDefaultJsonStore(
		                                    						url,  
		                                    						ccontrol.getBackupTable(),
		                                    						tempValorAtributo,
		                                    						valorAtributo));
		                                    			}
		                                    		}
		                                    		
		                                    		break;
		                                    	}
		                                    }
	                                    }
		                                    
		                                ////////////////////////////////////////////////////////////
		                                    
		                                /* Si el combo tiene las siguientes propiedades
		                                 * 
		                                 * allowBlank:false
		                                 * disabled:false
		                                 * editable:true
		                                 * hidden:false
		                                 * readOnly:false
		                                 * 
		                                 * Entonces se debe validar este campo antes de realizar submit
		                                 */
		                                if ( !scombo.isAllowBlank() && !scombo.isDisabled() && 
		                                 	scombo.isEditable() && !scombo.isHidden() && 
		                                  		!scombo.isReadOnly() ) {
		                                   	validaCamposBtnGuardar = true;
		                                }
		                                    
		                                if (!scombo.isHidden()) {
		                                    extDatosElements.add(scombo);
		                                }
	                                    
	                                    numStore++;
	                                }
	                            }
	                        } 
	                        /* ******************************************* 
	                         *  Caso: TextField
	                         ********************************************* */                        
	                        else if (element instanceof TextFieldControl) {
	                            textField = (TextFieldControl) element;   
	                            textField.setValue(camposValores[numAtributo]);
	                            textField.setName(StringUtils.replace(textField.getName(), "+", "p"));
	                            textField.setId(id);
	                            
	                            /* Si el textfield tiene las siguientes propiedades
	                             * 
	                             * allowBlank:false
	                             * disabled:false
	                             * hidden:false
	                             * readOnly:false
	                             * 
	                             * Entonces se debe validar este campo antes de realizar submit
	                             */                            
	                            if ( !textField.isAllowBlank() && !textField.isDisabled() && 
	                            		!textField.isHidden() && !textField.isReadOnly() ) {
	                            	validaCamposBtnGuardar = true;                         	
	                            }
	                            
	                            if (!textField.isHidden()) {
	                                extDatosElements.add(textField);
	                            }
	                        }        
	                        
	                        parameters.put(id, camposValores[numAtributo++]);
	                    }
	            }
	            
	            if (logger.isDebugEnabled()){
	            	logger.debug(":: entryDataCombo final -> " + entryDataCombo);
	            	logger.debug(":: storesNames final -> " + storesNames);
	            	logger.debug(":: comboLabelValue final -> " + comboLabelValue);
	            }
	            
	            Map<String, List<String>> nombresTablasStores = generaEntryComboData(entryDataCombo, backupTables, storesNames);
	            
	            if (logger.isDebugEnabled()){
	            	logger.debug(":: nombresTablasStores -> " + nombresTablasStores);
	            }
	            
	            codExtCargaCombos = generaCodExtCargaCombos(nombresTablasStores, 
	            		storesNames,
	            		comboLabelValue);
	            
	            if (logger.isDebugEnabled()){
	            	logger.debug(":::::::::::::::::::::::::::::::::::::::::::::::: ");
	            	logger.debug(":::::: codExtCargaCombos -> ");
	            	logger.debug(":::::: " + codExtCargaCombos);
	            }
	            
	            /* ***********************************
	             * Validacion si existen elementos Ext
	             *********************************** */
	            
	            StringBuilder builder = new StringBuilder();            
	            builder.append("{id:'guardarDatosAdicionalesEndosos', text:'Guardar', tooltip:'Guardar datos adicionales de la poliza',");
	            if (extDatosElements.isEmpty()) {
	                builder.append("hidden:true,");
	            }
	
	            builder.append("handler:function(){ ");
	            /*
	             * Si existen elemento Ext y se validaCamposBtnGuardar es verdadero, agregar funcion isValid() para la forma
	             */
	            if ( !extDatosElements.isEmpty() && validaCamposBtnGuardar ) {
	            	builder.append("if (Ext.getCmp('datosIncisosForm').form.isValid()) { ");
	            }            
	
	            builder.append("Ext.getCmp('datosIncisosForm').form.submit({ waitMsg:'Procesando...', success:function(form,action){ if(Ext.util.JSON.decode(action.response.responseText).saved == \"true\"){ Ext.Msg.alert('Status', \"Datos guardados.\"); }else { Ext.Msg.alert('Error', Ext.util.JSON.decode(action.response.responseText).mensajeError +\". Intente de nuevo.\"); } } }); ");
	
	            /*
	             * Si existen elemento Ext y se validaCamposBtnGuardar es verdadero, agregar condicional para mostrar mensaje
	             */
	            if ( !extDatosElements.isEmpty() && validaCamposBtnGuardar ) {
	            	builder.append("} else { Ext.MessageBox.alert('Aviso', 'Complete la informaci&oacute;n requerida'); } ");
	            }
	            
	            // Parte final de la definicion del boton 'Guardar'
	            builder.append("} }");
	            
	            botonDatVariables = builder.toString();
	            
	            if (extDatosElements.isEmpty()) {
	                TextFieldControl tfHidden = new TextFieldControl();
	                tfHidden.setName("noFields");
	                tfHidden.setId("noFields");
	                tfHidden.setWidth(0);
	                tfHidden.setHidden(true);
	                tfHidden.setAllowBlank(true);
	                extDatosElements.add(tfHidden);
	            }
	            
	            if (logger.isDebugEnabled()){
	                logger.debug(":::: extDatosElements Datos final : " + extDatosElements);
	                logger.debug(":::: botonDatVariables : " + botonDatVariables);
	                logger.debug(":::: parameters : " + parameters);
	            }
	        }
	        catch(ApplicationException ex){
	            logger.debug("obtenPantallaIncisos EXCEPTION:: " + ex);
	        }
        }
        
        success = true;
        
        return SUCCESS;
    }

	private String obtenDefaultJsonStore(String url, String backupTable, String valorAnterior, String valorAntAnt) {
		
		if (logger.isDebugEnabled()){
            logger.debug("-------> getDefaultJsonStore");  
        }
		
		StringBuilder jsonBuilder = new StringBuilder(" new Ext.data.JsonStore({ ");
		
		jsonBuilder.append(" url : '").append(url).append("', ");
		jsonBuilder.append(" remoteSort : true , ");
		jsonBuilder.append(" root : 'itemList', ");
		
		jsonBuilder.append("autoLoad : ").append(true).append(", ");
			
		jsonBuilder.append("listeners : ").append("{ beforeload: ").append(  
				setDefaultBeforeLoadStore(backupTable, valorAnterior, valorAntAnt) )
			.append(" }, ");	

		jsonBuilder.append("sortInfo: { field: 'value', direction: 'DESC'  }, ");
		

		jsonBuilder.append(" fields:[{ name : 'value', type : 'string', mapping : 'value'  } ,")
			.append(" { name : 'label', type : 'string', mapping : 'label'} ] ").
		
		append(" }) ") ;

		return jsonBuilder.toString();
	}

	/**
	 * 
	 * @param backupTable
	 * @param valorAntAnt 
	 * @param valorAnterior 
	 * @return
	 */
	private JSONFunction setDefaultBeforeLoadStore(String backupTable, String valorAnterior, String valorAntAnt ){
		
		StringBuilder functionBody = new StringBuilder();		

		functionBody.append(" this.baseParams['ottabval'] = '").append(backupTable).append("' ; ");		
		functionBody.append(" this.baseParams['valAnterior'] = '")
			.append(valorAnterior)
			.append("'; this.baseParams['valAntAnt'] = '")
			.append(valorAntAnt).append("'; ");

		JSONFunction beforeload = new JSONFunction( functionBody.toString() );

		return beforeload;
	}
	
	private String generaCodExtCargaCombos(
			Map<String, List<String>> nombresTablasStores,
			Map<String, String> storesNames, Map<String, String> comboLabelValue) {
		
		if (logger.isDebugEnabled()){
            logger.debug("-------> generaCodExtCargaCombos");  
        }
		
		//Contemplamos los casos en que varios combos tengan el mismo nombre de tabla y creamos las sentencias para su callback
        HashMap<String, List<BaseObjectVO>> listaMapaCallback = obtenerMapaCallback(storesNames, comboLabelValue);
        logger.debug("listaMapaCallback=" + listaMapaCallback);
        
		Iterator itStores = storesNames.entrySet().iterator();
        StringBuffer codeExtStores = new StringBuffer();
        
        while (itStores.hasNext()) {
        	Map.Entry entryStores = (Map.Entry)itStores.next();
        	String idCombo = entryStores.getKey().toString();
        	String nombreTabla = entryStores.getValue().toString();
        	logger.debug("clave (idCombo)="+ idCombo);
        	logger.debug("valor (nombreTabla)="+ nombreTabla);
        	
        	if(nombresTablasStores != null && nombresTablasStores.containsKey(nombreTabla)){
        		//obtener los parametros valAnterior y valAntAnt
        		List listaParams = new ArrayList<String>();
        		listaParams = (ArrayList<String>)nombresTablasStores.get(nombreTabla);
        		String valorElegidoCombo = comboLabelValue.get( idCombo );
        		codeExtStores.append( "store"+nombreTabla+".baseParams['ottabval'] = '"+nombreTabla+"';" );
        		codeExtStores.append( "store"+nombreTabla+".baseParams['valAnterior'] = '"+listaParams.get(0)+"';" );
        		codeExtStores.append( "store"+nombreTabla+".baseParams['valAntAnt'] = '"+listaParams.get(1)+"';");
        		codeExtStores.append( "store"+nombreTabla+".load({ callback: function(r, options, success){ ");
        		
        		if(!listaMapaCallback.isEmpty() && listaMapaCallback.containsKey(nombreTabla)){
        			List<BaseObjectVO> listaClaveValor = listaMapaCallback.get(nombreTabla);
        			for(BaseObjectVO claveValorVO: listaClaveValor){
        				//Crear sentencias que asignan valores a componentes que utilizan el MISMO store (si se da el caso)
        				codeExtStores.append( "Ext.getCmp('"+claveValorVO.getLabel()+"').setValue('"+claveValorVO.getValue()+"'); " );
        			}
        		}else{
        			codeExtStores.append( "Ext.getCmp('"+idCombo+"').setValue('"+valorElegidoCombo+"'); " );
        		}
        		
        		codeExtStores.append( "} });" );
        	}
        }
		
		return codeExtStores.toString();
	}

    /**
     * Obtener mapa con el nombre de las tablas que se repitan y los valores elegidos previamente para asignarlos en el callback del store.
     * Esto es para evitar que no se asignen correctamente los valores elegidos al 'Regresar'
     * @return mapa con el nombre de las tablas repetidas  y los valores de los combos 
     */
    protected HashMap<String, List<BaseObjectVO>> obtenerMapaCallback(Map storeNames, Map comboLabelValue){
    	
    	HashMap<String, List<BaseObjectVO>> mapaCallBack = new HashMap<String, List<BaseObjectVO>>();
    	 
    	List<String> listaTablasRepetidas = new ArrayList<String>();
    	//Se itera los nombres de los stores para obtener el nombre de la listaTablasRepetidas
    	Iterator itStoreNames = storeNames.entrySet().iterator();
    	Map<String, String> mapaTemporal = new HashMap<String, String>();
    	while (itStoreNames.hasNext()) {
    		Map.Entry entryStoreNames = (Map.Entry)itStoreNames.next();
    		
    		if(mapaTemporal.containsKey(entryStoreNames.getValue())){
    			logger.debug("Tabla repetida:" + entryStoreNames.getValue());
    			if(listaTablasRepetidas.contains((String)entryStoreNames.getValue())){
    				logger.debug("La tabla " + (String)entryStoreNames.getValue() + " ya esta asignada, no agregarla de nuevo a la lista");
    			}
    			listaTablasRepetidas.add((String)entryStoreNames.getValue());
    		}
    		mapaTemporal.put((String)entryStoreNames.getValue(), (String)entryStoreNames.getValue());	
    	}
    	
    	//Llenar lista de sentencias para callback del store cuando los componentes usan el mismo store(si se da el caso)
    	for(String tablaRepetida: listaTablasRepetidas){
    		List<BaseObjectVO> listaValores = new ArrayList<BaseObjectVO>();
    		//iterar sobre los nombres de stores
    		Iterator itStores = storeNames.entrySet().iterator();
    		while (itStores.hasNext()) {
    			Map.Entry entryStores = (Map.Entry)itStores.next();
    			logger.debug("store id==>"+ entryStores.getKey());
	    		logger.debug("store tabla==>"+ entryStores.getValue());
    			if(entryStores.getValue().equals(tablaRepetida)){
    				logger.debug("agregar " + entryStores.getKey() + " - " + entryStores.getValue());
    				BaseObjectVO claveValorVO = new BaseObjectVO();
    				//Asignar id del componente:
    				claveValorVO.setLabel((String)entryStores.getKey());
    				//Obtener el Valor del componente a partir de su id, y asignarlo a claveValorVO
    				claveValorVO.setValue((String)comboLabelValue.get(entryStores.getKey()));
    				listaValores.add(claveValorVO);
    			}
    		}
    		if(!listaValores.isEmpty()){
    			mapaCallBack.put(tablaRepetida, listaValores);
    		}
    	}
    	
    	return mapaCallBack;
    }
    
	private Map<String, List<String>> generaEntryComboData(Map<String, String> entryDataCombo, String[] backupTables, Map<String, String> storesNames) {
		if (logger.isDebugEnabled()){
            logger.debug("-------> generaEntryComboData");  
            logger.debug(":::  entryDataCombo  :  " + entryDataCombo);
            logger.debug(":::  backupTables    :  " + backupTables);
            logger.debug(":::  storesNames     :  " + storesNames);
        }
		
		Map<String, List<String>> mapEntriesCombos = null;
		List<String> params = null;
		
		if (storesNames != null && storesNames.size() == 3) {
			mapEntriesCombos = new HashMap<String, List<String>>();
			
			String tempSecValue = null;
			
			for (int i = 0; i < backupTables.length; i++) {
				if (logger.isDebugEnabled()){
		            logger.debug(":::::  backupTables[i]         :  " + backupTables[i]);
		            logger.debug(":::::  entryDataCombo value    :  " + entryDataCombo.get(backupTables[i]));
		        }
			
				if (storesNames.containsValue(backupTables[i])) {
					params = new ArrayList<String>();
					
					switch (i) {
						case 0 :
							params.add("0");
					        params.add("0");
					        
							break;
							
						case 1 :
							params.add(entryDataCombo.get(backupTables[i]));
					        params.add("0");
					        
							tempSecValue = entryDataCombo.get(backupTables[i]);
							break;
							
						case 2 :
							params.add(entryDataCombo.get(backupTables[i]));
					        params.add(tempSecValue);
					        
							break;
					}
					
					mapEntriesCombos.put(backupTables[i], params);
				}
			}
		}
		
		if (logger.isDebugEnabled()){
            logger.debug(":::::  mapEntriesCombos    :  " + mapEntriesCombos);
        }
		
		return mapEntriesCombos;
	}

	/**
     * Agrega datos adicionales
     * @return SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String agregarDatosAdicionales(){
        
        if (logger.isDebugEnabled()){
            logger.debug("-------> agregarDatosAdicionales");  
            logger.debug("parameters      :" + parameters);
        }
        
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        Map<String, String> parametersPantalla = new HashMap<String, String>();
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        String key = null;
        String newKey = null;
        int divParams = 0;
        int pos = 0;
        
        Iterator<String> itParams = parameters.keySet().iterator();
        while (itParams.hasNext()) {
            key = itParams.next();
            logger.debug("      key      : " + key);
            divParams = StringUtils.countMatches(key, "_");
            logger.debug("      divParams      : " + divParams);
            if (divParams == 2) {
                pos = key.lastIndexOf('_');
            }
            logger.debug("      pos      : " + pos);
            newKey = key.substring(0, pos);
            logger.debug("      newKey      : " + newKey);
            parametersPantalla.put(newKey, parameters.get(key));
        }
        
        if (logger.isDebugEnabled()){
            logger.debug("parametersPantalla      :" + parametersPantalla);
        }
        
        try {
            kernelManager.guardaDatosAdicionalesEndosos(idSesion, globalVarVO, parametersPantalla);
            saved = "true";
            if (logger.isDebugEnabled()){
                logger.debug(":::::: Se salvo el bloque b5b exitosamente :::::: ");
            }
        } catch (mx.com.ice.services.exception.ApplicationException appExc) {
            logger.error("::::::::::::::::::::::: ERROR :::::::::::::::::::::::::::");
            logger.error(appExc);
            mensajeError = appExc.getMessage();
            logger.error("::::::::::::::::::::::: mensajeError = " + mensajeError);
            
            if (StringUtils.isBlank(mensajeError)) {
                mensajeError = "Se presentaron problemas en el proceso de Guardado";
            }
            
            saved = "false";
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    /**
     * @param extDatosElem 
     * @return
     * @throws ApplicationException 
     * @throws mx.com.ice.services.exception.ApplicationException 
     */
    private String[] llenaCamposValoresMap(ArrayList<ExtElement> extDatosElem) 
            throws mx.com.ice.services.exception.ApplicationException, ApplicationException {
        if (logger.isDebugEnabled()){
            logger.debug("--------> llenaCamposValoresMap");
            logger.debug("--------> extDatosElem : " + extDatosElem);
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
            nombreCampo = st.nextToken();
            logger.debug("   ::::: nombreCampo : " + nombreCampo);
            campos[i] = new Campo(nombreCampo, "");            
        }
        
        if (logger.isDebugEnabled()){
            for (int i = 0; i < campos.length; i++) {
                logger.debug("------> campos : " + campos[i]);  
            }
        }
        
        rt = ExceptionManager.manage(kernelManager.obtenerDatosBloque(idSesion, ConstantsKernel.BLOQUE_B5B, campos));
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
     * Método encargado de cargar los bloques B5, B5B, B19, B19B,
     * B18,B6, B6B
     * @param globalVarVO 
     * @throws ApplicationException 
     */
    private void cargaBloques(GlobalVariableContainerVO globalVarVO) throws ApplicationException {
        if (logger.isDebugEnabled()){
            logger.debug("--------> cargaBloques");
        }
        
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        UserVO usuario = (UserVO) session.get("USUARIO");
        
        //Crear la sesion, entidad y TODOS los bloques:
        //kernelManager.crearBloques(idSesion);
        
        //Cargar los bloques referentes a la poliza
        kernelManager.cargarBloquesIncisos(idSesion, globalVarVO, usuario);
    }

    /**
     * Consulta de Inciso
     * @return SUCCESS
     * @author Alejandro Garcia
     */
    @SuppressWarnings("unchecked")
    public String obtenAgrupador(){
//      Se obtiene de sesión el objeto globalContainer
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        
        cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        
        if (logger.isDebugEnabled()){
            logger.debug("***obtenAgrupador***");
            logger.debug("cdunieco     :" + cdUnieco);
            logger.debug("cdramo       :" + cdRamo);
            logger.debug("estado     :" + estado);
            logger.debug("nmpoliza       :" + nmPoliza);
        }
        
        //TODO Modificar con datos reales
        Map <String,String> params = new HashMap<String,String>();
        params.put("CD_UNIECO", cdUnieco);
        params.put("CD_RAMO", cdRamo);
        params.put("ESTADO", estado);
        params.put("NMPOLIZA", nmPoliza);
        params.put("CD_AGRUPA", "");
        params.put("CD_PERSON", "");
        
        try{
            listMpoliagr = endosoManager.getAgrupador(params, "OBTIENE_MPOLIAGR");          
            logger.debug("listMpoliagr Agrupador : " + listMpoliagr);
        }
        catch(ApplicationException ex){
            logger.debug("***** obtenAgrupador EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    /**
     * agregarAgrupador
     * @return String
     * @throws Exception
     */
    public String agregarAgrupador() throws Exception{
        if (logger.isDebugEnabled()) {
            logger.debug("-> editarAgrupador");
            logger.debug("descripcionPersonaUsuario-->" + descripcionPersonaUsuario);
            logger.debug("descripcionDomicilio-->" + descripcionDomicilio);
            logger.debug("descripcionIntrumentoPago-->" + descripcionIntrumentoPago);
            logger.debug("descripcionSucursal-->" + descripcionSucursal);
            logger.debug("descripcionBanco-->" + descripcionBanco);
            logger.debug("descripcionTipoTarjeta-->" + descripcionTipoTarjeta);
            logger.debug("numeroTarjeta-->" + numeroTarjeta);
            logger.debug("fechaVencimiento-->" + fechaVencimiento);
            logger.debug("digitoVerificador-->" + digitoVerificador );
            logger.debug("codigoPersonaUsuario-->" + codigoPersonaUsuario);
            logger.debug("codigoDomicilio-->" + codigoDomicilio);
            logger.debug("codigoInstrumentoPago-->" + codigoInstrumentoPago);
            logger.debug("codigoBanco-->" + codigoBanco);
            logger.debug("codigoSucursal-->" + codigoSucursal );
            logger.debug("codigoTipoTarjeta-->" + codigoTipoTarjeta );
            logger.debug("cdUnieco-->" + cdUnieco );
            logger.debug("cdRamo-->" + cdRamo );
            logger.debug("estado-->" + estado );
            logger.debug("nmPoliza-->" + nmPoliza );
            logger.debug("nmSuplem-->" + nmSuplem );
            logger.debug("cdAgrupa-->" + cdAgrupa );
        }
        
        Map<String, String> params = new HashMap<String, String>();
        //Datos Endoso
        params.put("CD_UNIECO", cdUnieco);
        params.put("CD_RAMO", cdRamo);
        params.put("ESTADO", estado);
        params.put("NMPOLIZA", nmPoliza);
        params.put("NM_SUPLEM", nmSuplem);
        params.put("CDPERSON", codigoPersonaUsuario);
        params.put("CD_DOMICILIO", codigoDomicilio);
        params.put("CD_INSTRUMENTO", codigoInstrumentoPago);
        params.put("CDBANCO", codigoBanco);
        params.put("CD_SUCURSAL", codigoSucursal);
        params.put("NMTARJ", numeroTarjeta);
        params.put("NM_CUENTA", numeroTarjeta);
        params.put("NM_DIGVER", digitoVerificador);
        
        //Datos Tarjeta
        params.put("CDTITARJ", codigoTipoTarjeta);
        params.put("FEVENCE", fechaVencimiento);
        params.put("DEBCRED", "C");
        params.put("ACCION", "I");
        
        endosoManager.editarDatosEndosoTarjeta(params);
        endosoManager.editarDatosEndoso(params);
                
        success = true;
        
        return SUCCESS;     
    }
    
    /**
     * editarAgrupador
     * @return String
     * @throws Exception
     */
    public String editarAgrupador() throws Exception{

    	//try{
    		//logger.debug("tests:"+kernelManager.generaSuplFisico(cdUnieco, cdRamo, estado, nmPoliza, fechaActual));
    		//nmSuplem = kernelManager.generaSuplFisico(cdUnieco, cdRamo, estado, nmPoliza, fechaActual);
    		
    	//GlobalVariableContainerVO globalVarVO = new GlobalVariableContainerVO();    	
    	GlobalVariableContainerVO globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
    	nmSuplem = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
    	
        if (logger.isDebugEnabled()) {
            logger.debug("-> editarAgrupador");
            logger.debug("fechaActual-->" + fechaActual);
            logger.debug("descripcionPersonaUsuario-->" + descripcionPersonaUsuario);
            logger.debug("descripcionDomicilio-->" + descripcionDomicilio);
            logger.debug("descripcionIntrumentoPago-->" + descripcionIntrumentoPago);
            logger.debug("descripcionSucursal-->" + descripcionSucursal);
            logger.debug("descripcionBanco-->" + descripcionBanco);
            logger.debug("descripcionTipoTarjeta-->" + descripcionTipoTarjeta);
            logger.debug("numeroTarjeta-->" + numeroTarjeta);
            logger.debug("numeroCuenta-->" + numeroCuenta);
            logger.debug("fechaVencimiento-->" + fechaVencimiento);
            logger.debug("digitoVerificador-->" + digitoVerificador );
            logger.debug("codigoPersonaUsuario-->" + codigoPersonaUsuario);
            logger.debug("codigoDomicilio-->" + codigoDomicilio);
            logger.debug("codigoInstrumentoPago-->" + codigoInstrumentoPago);
            logger.debug("codigoBanco-->" + codigoBanco);
            logger.debug("codigoSucursal-->" + codigoSucursal );
            logger.debug("codigoTipoTarjeta-->" + codigoTipoTarjeta );
            logger.debug("cdUnieco-->" + cdUnieco );
            logger.debug("cdRamo-->" + cdRamo );
            logger.debug("estado-->" + estado );
            logger.debug("nmPoliza-->" + nmPoliza );
            logger.debug("nmSuplem-->" + nmSuplem );
            logger.debug("cdAgrupa-->" + cdAgrupa );
        }
        
        Map<String, String> params = new HashMap<String, String>();
        //Datos Endoso
        params.put("CD_UNIECO", cdUnieco);
        params.put("CD_RAMO", cdRamo);
        params.put("ESTADO", estado);
        params.put("NMPOLIZA", nmPoliza);
        params.put("NM_SUPLEM", nmSuplem);
        params.put("CDPERSON", codigoPersonaUsuario);
        params.put("CD_DOMICILIO", codigoDomicilio);
        params.put("CD_INSTRUMENTO", codigoInstrumentoPago);
        params.put("CDBANCO", codigoBanco);
        params.put("CD_SUCURSAL", codigoSucursal);
        params.put("NMTARJ", numeroTarjeta);
        params.put("NM_CUENTA", numeroTarjeta);
        params.put("NM_DIGVER", digitoVerificador);
        
        //Datos Tarjeta
        params.put("CDTITARJ", codigoTipoTarjeta);
        params.put("FEVENCE", fechaVencimiento);  
        params.put("DEBCRED", "C");
        params.put("ACCION", "U");
        
        if (logger.isDebugEnabled()) {
        	logger.debug("params	:" + params);
        }
        
        endosoManager.editarDatosEndosoTarjeta(params);
        endosoManager.editarDatosEndoso(params);
                
        success = true;
        
        return SUCCESS;     
    }
    
    public String borrarAgrupador() throws Exception{
        if (logger.isDebugEnabled()) {
            logger.debug("-> editarAgrupador");
            logger.debug("descripcionPersonaUsuario-->" + descripcionPersonaUsuario);
            logger.debug("descripcionDomicilio-->" + descripcionDomicilio);
            logger.debug("descripcionIntrumentoPago-->" + descripcionIntrumentoPago);
            logger.debug("descripcionSucursal-->" + descripcionSucursal);
            logger.debug("descripcionBanco-->" + descripcionBanco);
            logger.debug("descripcionTipoTarjeta-->" + descripcionTipoTarjeta);
            logger.debug("numeroTarjeta-->" + numeroTarjeta);
            logger.debug("fechaVencimiento-->" + fechaVencimiento);
            logger.debug("digitoVerificador-->" + digitoVerificador );
            logger.debug("codigoPersonaUsuario-->" + codigoPersonaUsuario);
            logger.debug("codigoDomicilio-->" + codigoDomicilio);
            logger.debug("codigoInstrumentoPago-->" + codigoInstrumentoPago);
            logger.debug("codigoBanco-->" + codigoBanco);
            logger.debug("codigoSucursal-->" + codigoSucursal );
            logger.debug("codigoTipoTarjeta-->" + codigoTipoTarjeta );
            logger.debug("cdUnieco-->" + cdUnieco );
            logger.debug("cdRamo-->" + cdRamo );
            logger.debug("estado-->" + estado );
            logger.debug("nmPoliza-->" + nmPoliza );
            logger.debug("nmSuplem-->" + nmSuplem );
            logger.debug("cdAgrupa-->" + cdAgrupa );
        }

        Map<String, String> params = new HashMap<String, String>();
        //Datos Endoso
        params.put("CD_UNIECO", cdUnieco);
        params.put("CD_RAMO", cdRamo);
        params.put("ESTADO", estado);
        params.put("NMPOLIZA", nmPoliza);
        params.put("NM_SUPLEM", nmSuplem);
        params.put("CDPERSON", codigoPersonaUsuario);
        params.put("CD_DOMICILIO", codigoDomicilio);
        params.put("CD_INSTRUMENTO", codigoInstrumentoPago);
        params.put("CDBANCO", codigoBanco);
        params.put("CD_SUCURSAL", codigoSucursal);
        params.put("NMTARJ", numeroTarjeta);
        params.put("NM_CUENTA", numeroTarjeta);
        params.put("NM_DIGVER", digitoVerificador);

        //Datos Tarjeta
        params.put("CDTITARJ", codigoTipoTarjeta);
        params.put("FEVENCE", fechaVencimiento);
        params.put("DEBCRED", "C");
        params.put("ACCION", "D");

        endosoManager.editarDatosEndosoTarjeta(params);
        endosoManager.editarDatosEndoso(params);

        success = true;
        return SUCCESS;     
    }
     
    public String domicilioList() throws Exception{
        UserVO usuario = (UserVO) session.get("USUARIO");
        listaDomicilio = catalogManager.getItemList("OBTIENE_DOMICILIO_PERSONA",usuario.getCodigoPersona());
        if(listaDomicilio==null)
            listaDomicilio = new ArrayList<BaseObjectVO>();
        logger.debug(":::::.. listaDomicilio : " + listaDomicilio);
        success = true;
        return SUCCESS;
    }
    
    public String intrumentoPagoList() throws Exception{

        listaIntrumentoPago = catalogManager.getItemList("OBTIENE_INTRUMETOS_PAGO");
        if(listaIntrumentoPago==null)
            listaIntrumentoPago = new ArrayList<BaseObjectVO>();
        logger.debug(":::::.. listaIntrumentoPago : " + listaIntrumentoPago);
        success = true;
        return SUCCESS;
    }
    
    public String bancoList() throws Exception{

        listaBancos = catalogManager.getItemList("CATALOGS_OBTIENE_BANCOS");       
        if(listaBancos==null)
            listaBancos = new ArrayList<BaseObjectVO>();
        logger.debug(":::::.. listaBancos : " + listaBancos);
        success = true;
        return SUCCESS;
    }
    
    public String sucursalList() throws Exception{
        
        listaSucursal = catalogManager.getItemList("OBTIENE_BANCOS_SUCURSAL", codigoBanco);
        if(listaSucursal==null)
            listaSucursal = new ArrayList<BaseObjectVO>();
        logger.debug(":::::.. listaSucursal : " + listaSucursal);
        success = true;
        return SUCCESS;
    }
    
    public String personasUsuarioList() throws Exception{
        UserVO usuario = (UserVO) session.get("USUARIO");
        listaPersonasUsuario = catalogManager.getItemList("OBTIENE_PERSONA_USUARIO", usuario.getUser());
        if(listaPersonasUsuario==null)
            listaPersonasUsuario = new ArrayList<BaseObjectVO>();
        logger.debug(":::::.. listaPersonasUsuario : " + listaPersonasUsuario);
        success = true;
        return SUCCESS;
    }
    
    public String personasUsuarioMultiSelectList() throws Exception{
        UserVO usuario = (UserVO) session.get("USUARIO");
        listaPersonasUsuarioMultiSelect = catalogManager.getItemList("OBTIENE_PERSONA_USUARIO", usuario.getUser());
        if(listaPersonasUsuarioMultiSelect==null)
            listaPersonasUsuarioMultiSelect = new ArrayList<BaseObjectVO>();
        logger.debug(":::::.. listaPersonasUsuarioMultiSelect : " + listaPersonasUsuarioMultiSelect);
        success = true;
        return SUCCESS;
    }
    
    public String tipoTarjetaList() throws Exception{
        listaTipoTarjeta = catalogManager.getItemList("OBTIENE_TIPO_TARJETA");       
        if(listaTipoTarjeta==null)
            listaTipoTarjeta = new ArrayList<BaseObjectVO>();
        logger.debug(":::::.. listaTipoTarjeta : " + listaTipoTarjeta);
        success = true;
        return SUCCESS;
    }
    
    /**
     * @return the producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(String producto) {
        this.producto = producto;
    }

    /**
     * @return the poliza
     */
    public String getPoliza() {
        return poliza;
    }

    /**
     * @param poliza the poliza to set
     */
    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    /**
     * @return the nombreAsegurado
     */
    public String getNombreAsegurado() {
        return nombreAsegurado;
    }

    /**
     * @param nombreAsegurado the nombreAsegurado to set
     */
    public void setNombreAsegurado(String nombreAsegurado) {
        this.nombreAsegurado = nombreAsegurado;
    }

    /**
     * @return the aseguradora
     */
    public String getAseguradora() {
        return aseguradora;
    }

    /**
     * @param aseguradora the aseguradora to set
     */
    public void setAseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }

    /**
     * @return the statusPoliza
     */
    public String getStatusPoliza() {
        return statusPoliza;
    }

    /**
     * @param statusPoliza the statusPoliza to set
     */
    public void setStatusPoliza(String statusPoliza) {
        this.statusPoliza = statusPoliza;
    }

    /**
     * @return the fechaInicioFin
     */
    public String getFechaInicioFin() {
        return fechaInicioFin;
    }

    /**
     * @param fechaInicioFin the fechaInicioFin to set
     */
    public void setFechaInicioFin(String fechaInicioFin) {
        this.fechaInicioFin = fechaInicioFin;
    }

    /**
     * @return the emisiones
     */
    public List<EmisionVO> getEmisiones() {
        return emisiones;
    }

    /**
     * @param emisiones the emisiones to set
     */
    public void setEmisiones(List<EmisionVO> emisiones) {
        this.emisiones = emisiones;
    }

    /**
     * @return the inicioVigencia
     */
    public String getInicioVigencia() {
        return inicioVigencia;
    }

    /**
     * @param inicioVigencia the inicioVigencia to set
     */
    public void setInicioVigencia(String inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    /**
     * @return the finVigencia
     */
    public String getFinVigencia() {
        return finVigencia;
    }

    /**
     * @param finVigencia the finVigencia to set
     */
    public void setFinVigencia(String finVigencia) {
        this.finVigencia = finVigencia;
    }

    /**
     * @return the listProductos
     */
    public List getListProductos() {
        return listProductos;
    }

    /**
     * @param listProductos the listProductos to set
     */
    public void setListProductos(List listProductos) {
        this.listProductos = listProductos;
    }

    /**
     * @return the listAseguradoras
     */
    public List getListAseguradoras() {
        return listAseguradoras;
    }

    /**
     * @param listAseguradoras the listAseguradoras to set
     */
    public void setListAseguradoras(List listAseguradoras) {
        this.listAseguradoras = listAseguradoras;
    }

    /**
     * @return the listStatusPoliza
     */
    public List getListStatusPoliza() {
        return listStatusPoliza;
    }

    /**
     * @param listStatusPoliza the listStatusPoliza to set
     */
    public void setListStatusPoliza(List listStatusPoliza) {
        this.listStatusPoliza = listStatusPoliza;
    }

    /**
     * @return the polizaDet
     */
    public List<PolizaDetVO> getPolizaDet() {
        return polizaDet;
    }

    /**
     * @param polizaDet the polizaDet to set
     */
    public void setPolizaDet(List<PolizaDetVO> polizaDet) {
        this.polizaDet = polizaDet;
    }
    
    /**
     * @return the objAsegurable
     */
    public List<ObjetoAsegurableVO> getObjAsegurable() {
        return objAsegurable;
    }

    /**
     * @param objAsegurable the objAsegurable to set
     */
    public void setObjAsegurable(List<ObjetoAsegurableVO> objAsegurable) {
        this.objAsegurable = objAsegurable;
    }

    /**
     * @return the fnPoliza
     */
    public List<FuncionPolizaVO> getFnPoliza() {
        return fnPoliza;
    }

    /**
     * @param fnPoliza the fnPoliza to set
     */
    public void setFnPoliza(List<FuncionPolizaVO> fnPoliza) {
        this.fnPoliza = fnPoliza;
    }

    /**
     * @return the totalCountFnPol
     */
    public int getTotalCountFnPol() {
        return totalCountFnPol;
    }

    /**
     * @param totalCountFnPol the totalCountFnPol to set
     */
    public void setTotalCountFnPol(int totalCountFnPol) {
        this.totalCountFnPol = totalCountFnPol;
    }

    /**
     * @return the listRecibos
     */
    public List<RecibosVO> getListRecibos() {
        return listRecibos;
    }

    /**
     * @param listRecibos the listRecibos to set
     */
    public void setListRecibos(List<RecibosVO> listRecibos) {
        this.listRecibos = listRecibos;
    }

    /**
     * @return the itemsPantalla
     */
    public String getItemsPantalla() {
        return itemsPantalla;
    }

    /**
     * @param itemsPantalla the itemsPantalla to set
     */
    public void setItemsPantalla(String itemsPantalla) {
        this.itemsPantalla = itemsPantalla;
    }

    /**
     * @return the datAdicionales
     */
    public List<DatosAdicionalesVO> getDatAdicionales() {
        return datAdicionales;
    }

    /**
     * @param datAdicionales the datAdicionales to set
     */
    public void setDatAdicionales(List<DatosAdicionalesVO> datAdicionales) {
        this.datAdicionales = datAdicionales;
    }
    
    /**
     * @return the dext
     */
    public ArrayList<TextFieldControl> getDext() {
        return dext;
    }

    /**
     * @param dext the dext to set
     */
    public void setDext(ArrayList<TextFieldControl> dext) {
        this.dext = dext;
    }

    /**
     * @return the store
     */
    public String getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(String store) {
        this.store = store;
    }

    /**
     * @return the cdInciso
     */
    public String getCdInciso() {
        return cdInciso;
    }

    /**
     * @param cdInciso the cdInciso to set
     */
    public void setCdInciso(String cdInciso) {
        this.cdInciso = cdInciso;
    }

    /**
     * @return the listMpoliagr
     */
    public List<MpoliagrVO> getListMpoliagr() {
        return listMpoliagr;
    }

    /**
     * @param listMpoliagr the listMpoliagr to set
     */
    public void setListMpoliagr(List<MpoliagrVO> listMpoliagr) {
        this.listMpoliagr = listMpoliagr;
    }

    /**
     * @return the codigoBanco
     */
    public String getCodigoBanco() {
        return codigoBanco;
    }

    /**
     * @param codigoBanco the codigoBanco to set
     */
    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    /**
     * @return the codigoDomicilio
     */
    public String getCodigoDomicilio() {
        return codigoDomicilio;
    }

    /**
     * @param codigoDomicilio the codigoDomicilio to set
     */
    public void setCodigoDomicilio(String codigoDomicilio) {
        this.codigoDomicilio = codigoDomicilio;
    }

    /**
     * @return the codigoInstrumentoPago
     */
    public String getCodigoInstrumentoPago() {
        return codigoInstrumentoPago;
    }

    /**
     * @param codigoInstrumentoPago the codigoInstrumentoPago to set
     */
    public void setCodigoInstrumentoPago(String codigoInstrumentoPago) {
        this.codigoInstrumentoPago = codigoInstrumentoPago;
    }

    /**
     * @return the codigoPersonaUsuario
     */
    public String getCodigoPersonaUsuario() {
        return codigoPersonaUsuario;
    }

    /**
     * @param codigoPersonaUsuario the codigoPersonaUsuario to set
     */
    public void setCodigoPersonaUsuario(String codigoPersonaUsuario) {
        this.codigoPersonaUsuario = codigoPersonaUsuario;
    }

    /**
     * @return the codigoSucursal
     */
    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    /**
     * @param codigoSucursal the codigoSucursal to set
     */
    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    /**
     * @return the descripcionBanco
     */
    public String getDescripcionBanco() {
        return descripcionBanco;
    }

    /**
     * @param descripcionBanco the descripcionBanco to set
     */
    public void setDescripcionBanco(String descripcionBanco) {
        this.descripcionBanco = descripcionBanco;
    }

    /**
     * @return the descripcionDomicilio
     */
    public String getDescripcionDomicilio() {
        return descripcionDomicilio;
    }

    /**
     * @param descripcionDomicilio the descripcionDomicilio to set
     */
    public void setDescripcionDomicilio(String descripcionDomicilio) {
        this.descripcionDomicilio = descripcionDomicilio;
    }

    /**
     * @return the descripcionIntrumentoPago
     */
    public String getDescripcionIntrumentoPago() {
        return descripcionIntrumentoPago;
    }

    /**
     * @param descripcionIntrumentoPago the descripcionIntrumentoPago to set
     */
    public void setDescripcionIntrumentoPago(String descripcionIntrumentoPago) {
        this.descripcionIntrumentoPago = descripcionIntrumentoPago;
    }

    /**
     * @return the descripcionPersonaUsuario
     */
    public String getDescripcionPersonaUsuario() {
        return descripcionPersonaUsuario;
    }

    /**
     * @param descripcionPersonaUsuario the descripcionPersonaUsuario to set
     */
    public void setDescripcionPersonaUsuario(String descripcionPersonaUsuario) {
        this.descripcionPersonaUsuario = descripcionPersonaUsuario;
    }

    /**
     * @return the descripcionSucursal
     */
    public String getDescripcionSucursal() {
        return descripcionSucursal;
    }

    /**
     * @param descripcionSucursal the descripcionSucursal to set
     */
    public void setDescripcionSucursal(String descripcionSucursal) {
        this.descripcionSucursal = descripcionSucursal;
    }

    /**
     * @return the funcionesPersona
     */
    public List<String> getFuncionesPersona() {
        return funcionesPersona;
    }

    /**
     * @param funcionesPersona the funcionesPersona to set
     */
    public void setFuncionesPersona(List<String> funcionesPersona) {
        this.funcionesPersona = funcionesPersona;
    }

    /**
     * @return the listaBancos
     */
    public List<BaseObjectVO> getListaBancos() {
        return listaBancos;
    }

    /**
     * @param listaBancos the listaBancos to set
     */
    public void setListaBancos(List<BaseObjectVO> listaBancos) {
        this.listaBancos = listaBancos;
    }

    /**
     * @return the listaDomicilio
     */
    public List<BaseObjectVO> getListaDomicilio() {
        return listaDomicilio;
    }

    /**
     * @param listaDomicilio the listaDomicilio to set
     */
    public void setListaDomicilio(List<BaseObjectVO> listaDomicilio) {
        this.listaDomicilio = listaDomicilio;
    }

    /**
     * @return the listaIntrumentoPago
     */
    public List<BaseObjectVO> getListaIntrumentoPago() {
        return listaIntrumentoPago;
    }

    /**
     * @param listaIntrumentoPago the listaIntrumentoPago to set
     */
    public void setListaIntrumentoPago(List<BaseObjectVO> listaIntrumentoPago) {
        this.listaIntrumentoPago = listaIntrumentoPago;
    }

    /**
     * @return the listaPersonasUsuario
     */
    public List<BaseObjectVO> getListaPersonasUsuario() {
        return listaPersonasUsuario;
    }

    /**
     * @param listaPersonasUsuario the listaPersonasUsuario to set
     */
    public void setListaPersonasUsuario(List<BaseObjectVO> listaPersonasUsuario) {
        this.listaPersonasUsuario = listaPersonasUsuario;
    }

    /**
     * @return the listaPersonasUsuarioMultiSelect
     */
    public List<BaseObjectVO> getListaPersonasUsuarioMultiSelect() {
        return listaPersonasUsuarioMultiSelect;
    }

    /**
     * @param listaPersonasUsuarioMultiSelect the listaPersonasUsuarioMultiSelect to set
     */
    public void setListaPersonasUsuarioMultiSelect(List<BaseObjectVO> listaPersonasUsuarioMultiSelect) {
        this.listaPersonasUsuarioMultiSelect = listaPersonasUsuarioMultiSelect;
    }

    /**
     * @return the listaSucursal
     */
    public List<BaseObjectVO> getListaSucursal() {
        return listaSucursal;
    }

    /**
     * @param listaSucursal the listaSucursal to set
     */
    public void setListaSucursal(List<BaseObjectVO> listaSucursal) {
        this.listaSucursal = listaSucursal;
    }

    /**
     * @return the endosoManager
     */
    public EndosoManager obtenEndosoManager() {
        return endosoManager;
    }

    /**
     * @return the digitoVerificador
     */
    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    /**
     * @param digitoVerificador the digitoVerificador to set
     */
    public void setDigitoVerificador(String digitoVerificador) {
        this.digitoVerificador = digitoVerificador;
    }

    /**
     * @return the fechaVencimiento
     */
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * @param fechaVencimiento the fechaVencimiento to set
     */
    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * @return the numeroTarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * @param numeroTarjeta the numeroTarjeta to set
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * @return the codigoTipoTarjeta
     */
    public String getCodigoTipoTarjeta() {
        return codigoTipoTarjeta;
    }

    /**
     * @param codigoTipoTarjeta the codigoTipoTarjeta to set
     */
    public void setCodigoTipoTarjeta(String codigoTipoTarjeta) {
        this.codigoTipoTarjeta = codigoTipoTarjeta;
    }

    /**
     * @return the descripcionTipoTarjeta
     */
    public String getDescripcionTipoTarjeta() {
        return descripcionTipoTarjeta;
    }

    /**
     * @param descripcionTipoTarjeta the descripcionTipoTarjeta to set
     */
    public void setDescripcionTipoTarjeta(String descripcionTipoTarjeta) {
        this.descripcionTipoTarjeta = descripcionTipoTarjeta;
    }

    /**
     * @return the listaTipoTarjeta
     */
    public List<BaseObjectVO> getListaTipoTarjeta() {
        return listaTipoTarjeta;
    }

    /**
     * @param listaTipoTarjeta the listaTipoTarjeta to set
     */
    public void setListaTipoTarjeta(List<BaseObjectVO> listaTipoTarjeta) {
        this.listaTipoTarjeta = listaTipoTarjeta;
    }

    /**
     * @return the nmSuplem
     */
    public String getNmSuplem() {
        return nmSuplem;
    }

    /**
     * @param nmSuplem the nmSuplem to set
     */
    public void setNmSuplem(String nmSuplem) {
        this.nmSuplem = nmSuplem;
    }

    /**
     * @return the cdAgrupa
     */
    public String getCdAgrupa() {
        return cdAgrupa;
    }

    /**
     * @param cdAgrupa the cdAgrupa to set
     */
    public void setCdAgrupa(String cdAgrupa) {
        this.cdAgrupa = cdAgrupa;
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
     * @return the beforeRenderDatosItems
     */
    public String getBeforeRenderDatosItems() {
        return beforeRenderDatosItems;
    }

    /**
     * @param beforeRenderDatosItems the beforeRenderDatosItems to set
     */
    public void setBeforeRenderDatosItems(String beforeRenderDatosItems) {
        this.beforeRenderDatosItems = beforeRenderDatosItems;
    }

    /**
     * @return the storeDatosElements
     */
    public List<StoreVO> getStoreDatosElements() {
        return storeDatosElements;
    }

    /**
     * @param storeDatosElements the storeDatosElements to set
     */
    public void setStoreDatosElements(List<StoreVO> storeDatosElements) {
        this.storeDatosElements = storeDatosElements;
    }

    /**
     * @return the extDatosElements
     */
    public ArrayList<ExtElement> getExtDatosElements() {
        return extDatosElements;
    }

    /**
     * @param extDatosElements the extDatosElements to set
     */
    public void setExtDatosElements(ArrayList<ExtElement> extDatosElements) {
        this.extDatosElements = extDatosElements;
    }

    /**
     * @return the dsDescripcion
     */
    public String getDsDescripcion() {
        return dsDescripcion;
    }

    /**
     * @param dsDescripcion the dsDescripcion to set
     */
    public void setDsDescripcion(String dsDescripcion) {
        this.dsDescripcion = dsDescripcion;
    }

    /**
     * @param string
     * @return
     */
    private String modificaStore2(String elements) {
        if (logger.isDebugEnabled()) {
            logger.debug("-> modificaStore");
            logger.debug("::: store :: " + store);
        }
        //String storeAdaptado = StringUtils.replace(store, "\", " ");
        String storeAdaptado = StringUtils.remove(elements, "\\");
        return StringUtils.remove(storeAdaptado, ";");
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
	 * @return the numeroCuenta
	 */
	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	/**
	 * @param numeroCuenta the numeroCuenta to set
	 */
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
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
     * @return the botonDatVariables
     */
    public String getBotonDatVariables() {
        return botonDatVariables;
    }

    /**
     * @param botonDatVariables the botonDatVariables to set
     */
    public void setBotonDatVariables(String botonDatVariables) {
        this.botonDatVariables = botonDatVariables;
    }

	public void setProc(String proc) {
		this.proc = proc;
	}

	public String getProc() {
		return proc;
	}

	public void setFechaefectividadendoso(String fechaefectividadendoso) {
		this.fechaefectividadendoso = fechaefectividadendoso;
	}

	public String getFechaefectividadendoso() {
		return fechaefectividadendoso;
	}

	public String getCodExtCargaCombos() {
		return codExtCargaCombos;
	}

	public void setCodExtCargaCombos(String codExtCargaCombos) {
		this.codExtCargaCombos = codExtCargaCombos;
	}
}
