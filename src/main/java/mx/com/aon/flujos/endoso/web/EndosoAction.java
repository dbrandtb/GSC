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
import mx.com.aon.flujos.endoso.model.AgrupadoresVO;
import mx.com.aon.flujos.endoso.model.DatosPolizaVO;
import mx.com.aon.flujos.endoso.model.PolizaCancelVO;
import mx.com.aon.flujos.endoso.model.SuplemVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.utils.AONCatwebUtils;
import mx.com.aon.utils.Constantes;
import mx.com.ice.kernel.constants.ConstantsKernel;
import mx.com.ice.services.exception.manager.ExceptionManager;
import mx.com.ice.services.to.Campo;
import mx.com.ice.services.to.ResultadoTransaccion;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.biosnet.ice.ext.elements.form.ComboControl;
import com.biosnet.ice.ext.elements.form.ExtElement;
import com.biosnet.ice.ext.elements.form.SimpleCombo;
import com.biosnet.ice.ext.elements.form.TextFieldControl;
import com.biosnet.ice.ext.elements.utils.ComboBoxlUtils;

/**
 * 
 * Clase Action para el control y visualizacion de la pantalla principal de endosos
 * 
 * @author aurora.lozada
 * 
 */
public class EndosoAction extends PrincipalEndosoAction{

    /**
     * 
     */
    private static final long serialVersionUID = -8191445639828746635L;
    
    private static final String FLUJO_RENOVACION = "ren";
    private static final String FLUJO_ENDOSO = "end";
    
    private boolean success;
    /**
     * Contiene "true" o "false" dependiendo el resultado de la operacion
     */
    private String saved;
    
    private String aseguradora;
    private String producto;
    
    private String cdRol;
    private String cdPerson;
    private String idUser;
    private String cdDomicilio;
    private String dsDomicilio;
    private String cdInstrumentoPago;
    private String cdBanco;
    private String cdSucursal;
    private String cdTipoTarjeta;
    private String numeroTarjeta;
    private String fechaVencimiento;
    private String digitoVerificador;
    private String suplLogico;
    private String suplFisico;
    private String codigoBanco;
    private List<ExtElement> dext;
    private String botonDatVar;
    
    private String vigenciaDesde;
    private String fechaEfectividad;
    private String vigenciaHasta;
    private String fechaRenovacion;
    private String moneda;
    private String formaPago;
    
    private String fecha;
    private String fechasolicitud;
    private String fechaefectividadendoso;
    private String fechaActualiza;
    
    private String poliza;
    private String volver3;
    
    @SuppressWarnings("unchecked")
    private Map<String, String> parameters;
    
    @SuppressWarnings("unchecked")
    private List<BaseObjectVO> bancos;
    @SuppressWarnings("unchecked")
    private List<BaseObjectVO> instrumentosPago;
    @SuppressWarnings("unchecked")
    private List<BaseObjectVO> personasUsuarios;
    @SuppressWarnings("unchecked")
    private List<BaseObjectVO> sucursal;
    @SuppressWarnings("unchecked")
    private List<BaseObjectVO> domicilio;
    @SuppressWarnings("unchecked")
    private List<BaseObjectVO> tipoTarjeta;
    
    @SuppressWarnings("unchecked")
    private List<AgrupadoresVO> agrupadores;
        
    /**
     * Contiene el mensaje de error si no se guardaron bien los datos adicionales u otra operacion
     */
    private String mensajeError;
    
    private String mensajeErrorFechas;
    private boolean errorFechas;

    /**
     * Mensaje que muestra si se puede hacer o no el endoso(si tiene mensaje, no se puede endosar)
     */
    //TODO: quitar y poner en la pantalla anterior (cuando se haga ésta)
    private String msgId;
    
    
    /**
     * Contiene el tipo de procedimiento
     */
    private String proc;
    
	/**
	 * Atributo con el(los) dato(s) que identificará(n) el objeto Ext.data.Record que se seleccionó
	 * desde una pantalla de origen. Una vez que se desee regresar, se utilizará este atributo. 
	 */
	private String idRegresar;
    
    @SuppressWarnings("unchecked")
    public String generaSuplFisico(){
        logger.debug("-> generaSuplFisico:: ");
        logger.debug("::suplFisico:: " + suplFisico);
        
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        
        suplFisico = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
        
        if (StringUtils.isBlank(suplFisico)) {
            Map <String,String> params = new HashMap<String,String>();  
            params.put("cdunieco", cdUnieco);
            params.put("cdramo", cdRamo);
            params.put("estado", estado);
            params.put("nmpoliza", nmPoliza);
            params.put("fecha", AONCatwebUtils.getDateNow());
            params.put("hora", "12:00");
            
            try{
                suplFisico = endosoManager.getSuplFisico(params);
                
                //session.put("SUPL_FISICO", suplLogico);
            }
            catch(Exception ex){
                logger.debug("generaSuplFisico EXCEPTION:: " + ex);
            }
        }
        
        logger.debug("***** suplFisico:: " + suplFisico);
        
        success = true;
        
        return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String generaSuplLogico(){
        logger.debug("-> generaSuplLogico:: ");
        
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        
        suplLogico = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplementoLogico());
        
        if (StringUtils.isBlank(suplLogico)) {
            Map <String,String> params = new HashMap<String,String>();  
            params.put("cdunieco", cdUnieco);
            params.put("cdramo", cdRamo);
            params.put("estado", estado);
            params.put("nmpoliza", nmPoliza);
                
            try{
                suplLogico = endosoManager.getSuplLogico(params);
                    
                //session.put("SUPL_LOGICO", suplLogico);
            }
            catch(Exception ex){
                logger.debug("generaSuplLogico EXCEPTION:: " + ex);
            }
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    /**
     * actualizaFechas
     * @return String
     */
    @SuppressWarnings("unchecked")
    public String actualizaFechas() {
        if (logger.isDebugEnabled()) {
            logger.debug("-> actualizaFechas");
            logger.debug(":: fecha : " + fecha);
            logger.debug(":: fechaActualiza : " + fechaActualiza);
        }
        
        //Se obtiene de sesión el objeto globalContainer
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        
        Map <String,String> params = new HashMap<String,String>();  
        params.put("cdunieco", globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
        params.put("cdramo", globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo()));
        params.put("estado", globalVarVO.getValueVariableGlobal(VariableKernel.Estado()));
        params.put("nmpoliza", globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
        params.put("fecha", fecha);
        params.put("nsuplogi", globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplementoLogico()));
        params.put("indfecha", fechaActualiza);
        
        if (logger.isDebugEnabled()) {
            logger.debug(":: params : " + params);
        }
        
        try{
            BaseObjectVO suplem = endosoManager.actualizaFechas(params);
            logger.debug(":: suplem : " + suplem);
            
            if (StringUtils.isNotBlank(suplem.getValue())) {
                mensajeErrorFechas = obtieneMensajeError(suplem.getValue());
                errorFechas = true;
            } else {
                globalVarVO.addVariableGlobal(VariableKernel.NumeroSuplemento(), suplem.getLabel());
            }
        }
        catch(Exception ex){
            logger.debug("actualizaFechas EXCEPTION:: " + ex);
        }
        logger.debug("***** mensajeErrorFechas:: " + mensajeErrorFechas);
        logger.debug("***** errorFechas:: " + errorFechas);
        logger.debug("***** suplLogico:: " + suplLogico);
        
        //Subimos a sesion la Global Variable Container
        session.put(Constantes.GLOBAL_VARIABLE_CONTAINER, globalVarVO);
        
        if (FECHA_EFECTIVIDAD.equals(fechaActualiza)) {
            session.put(FECHA_EFECTIVIDAD, fecha);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    /**
     * @param value
     * @return
     */
    private String obtieneMensajeError(String value) {
        logger.debug("-> obtieneMensajeError");
        logger.debug("value:: " + value);
        
        int mensajeId = Integer.parseInt(value);
        
        switch (mensajeId) {
            case 1: return "Fecha solicitud del endoso menor que fecha de inicio de vigencia de la póliza";
            case 2: return "Fecha solicitud del endoso mayor que fecha final de vigencia de la póliza";
            case 3: return "Fecha del endoso menor que fecha de inicio de vigencia de la póliza";
            case 4: return "Fecha del endoso mayor que fecha final de vigencia de la póliza";             
        }
        
        return null;
    }

    public String guardaAgrupadores(){
        
        GlobalVariableContainerVO globalVarVO = (GlobalVariableContainerVO)session.get("GLOBAL_VARIABLE_CONTAINER");
        
        Map <String,String> params= new HashMap<String,String>();
        params.put("CD_UNIECO", cdUnieco);
        params.put("CD_RAMO", cdRamo);
        params.put("ESTADO", estado);
        params.put("NMPOLIZA", nmPoliza);
        //params.put("NM_SUPLEM", suplFisico);
        params.put("NM_SUPLEM", globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento()));
        params.put("CDPERSON", cdPerson);
        params.put("CD_DOMICILIO", cdDomicilio);
        params.put("CD_INSTRUMENTO", cdInstrumentoPago);
        params.put("CDBANCO", cdBanco);
        params.put("CD_SUCURSAL", cdSucursal);
        params.put("CDTITARJ", cdTipoTarjeta);
        params.put("NMTARJ", numeroTarjeta);
        params.put("NM_CUENTA", numeroTarjeta);
        params.put("FEVENCE", fechaVencimiento);
        params.put("DEBCRED", "C");
        params.put("ACCION", "I");
        params.put("NM_DIGVER", digitoVerificador);
        
        logger.debug("****** GUARDA AGRUPADORES ***** ");
        logger.debug("idUser:: " + idUser);
        logger.debug("cdPerson:: " + cdPerson);
        logger.debug("dsDomicilio:: " + dsDomicilio);
        logger.debug("cdDomicilio:: " + cdDomicilio);
        logger.debug("cdInstrumento:: " + cdInstrumentoPago);
        logger.debug("cdBanco:: " + cdBanco);
        logger.debug("cdSucursal:: " + cdSucursal);
        logger.debug("cdTipoTarjeta:: " + cdTipoTarjeta);
        logger.debug("numeroTarjeta:: " + numeroTarjeta);
        logger.debug("fechaVencimiento:: " + fechaVencimiento);
        logger.debug("digitoVerificador:: " + digitoVerificador);
        
        try{
            endosoManager.guardaAgrupadores(params);
        }
        catch(Exception ex){
            logger.debug("guardaAgrupadores EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String obtieneTipoTarjeta(){
        try{
            tipoTarjeta = catalogManager.getItemList("OBTIENE_TIPO_TARJETA");
        }
        catch(Exception ex){
            logger.debug("obtieneDomicilio EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String obtieneDomicilio(){
        try{
            domicilio = catalogManager.getItemList("OBTIENE_DOMICILIO_PERSONA",idUser);
        }
        catch(Exception ex){
            logger.debug("obtieneDomicilio EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String obtieneSucursal(){
        try{
            sucursal = catalogManager.getItemList("OBTIENE_BANCOS_SUCURSAL", codigoBanco);
        }
        catch(Exception ex){
            logger.debug("obtieneSucursal EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String obtienePersonasUsuarios(){
        try{
            UserVO usuario = (UserVO) session.get("USUARIO");
            personasUsuarios = catalogManager.getItemList("OBTIENE_PERSONA_USUARIO", usuario.getUser());
        }
        catch(Exception ex){
            logger.debug("obtienePersonasUsuarios EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String obtieneInstrumentosPago(){
        try{
            instrumentosPago = catalogManager.getItemList("OBTIENE_INTRUMETOS_PAGO");
        }
        catch(Exception ex){
            logger.debug("obtieneInstrumentosPago EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String obtieneBancos(){
        try{
            bancos = catalogManager.getItemList("CATALOGS_OBTIENE_BANCOS");
        }
        catch(Exception ex){
            logger.debug("obtieneBancos EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String obtieneAgrupadores(){
        Map <String,String> param = new HashMap<String,String>();   
        param.put("cdunieco", cdUnieco);
        param.put("cdramo", cdRamo);
        param.put("estado", estado);
        param.put("nmpoliza", nmPoliza);
        param.put("nmsituac", nmSituac);
        param.put("cdperson", "");
        param.put("cdagrupa", "");
        
        try{
            PagedList pagedList = endosoManager.getPagedList(param, "ENDOSO_OBTIENE_AGRUPADORES", start, limit);
            
            agrupadores = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();
            
        }catch(ApplicationException ex){
            if (logger.isDebugEnabled())
                logger.debug("getAgrupadores EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String getPantallaDatosRol(){
        Map <String,String> param = new HashMap<String,String>();   
        param.put("cdunieco", cdUnieco);
        param.put("cdramo", cdRamo);
        param.put("estado", estado);
        param.put("nmpoliza", nmPoliza);
        param.put("nmsituac", nmSituac);
        param.put("cdrol", cdRol);
        param.put("cdperson", cdPerson);
        param.put("cdatribu", "");
        
        logger.debug("***.:DATOS ROL:.***");
        
        try{
            dext = endosoManager.getDatosRolExt(param);
            logger.debug("***** DEXT: " + dext);
        }catch(ApplicationException ex){
            if (logger.isDebugEnabled())
                logger.debug("getPantallaDatosRol EXCEPTION:: " + ex);
        }
        
        success = true;
        
        return SUCCESS;
    }
    
    /**
     * 
     */
    @SuppressWarnings("unchecked")
    public String recibeParametros() throws Exception {

    	if ( session.containsKey("PARAMETROS_REGRESAR") ) {
    		Map<String,String> pr = (Map<String,String>)session.get("PARAMETROS_REGRESAR");
    		pr.put("idRegresar", idRegresar);
    		pr.put("clicBotonRegresar", "S");
    		session.put("PARAMETROS_REGRESAR", pr);
    	}
    	
        GlobalVariableContainerVO globalVarVO = new GlobalVariableContainerVO();
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        
        UserVO usuario = (UserVO) session.get("USUARIO");
        //TODO: ver si se toma global de sesion o se crea una nueva 
        //GlobalVariableContainerVO globalVarVO = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
        
        globalVarVO.addVariableGlobal(VariableKernel.UnidadEconomica(), ServletActionContext.getRequest().getParameter("cdUnieco"));
        globalVarVO.addVariableGlobal(VariableKernel.CodigoRamo(), ServletActionContext.getRequest().getParameter("cdRamo"));
        globalVarVO.addVariableGlobal(VariableKernel.Estado(), ServletActionContext.getRequest().getParameter("estado"));
        globalVarVO.addVariableGlobal(VariableKernel.NumeroPoliza(), ServletActionContext.getRequest().getParameter("nmPoliza"));
        globalVarVO.addVariableGlobal(VariableKernel.UsuarioBD(), usuario.getUser());
        globalVarVO.addVariableGlobal(VariableKernel.NumeroSituacion(), ServletActionContext.getRequest().getParameter("nmSituac"));
        
        String cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        String cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        String estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        String nmpoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        
        if (logger.isDebugEnabled()) {
            logger.debug(" proc: " + this.proc);
        }
        
        // Servicio generar Suplemento Fisico
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constantes.FORMATO_FECHA);
        
        
        Date hoy = new Date();
        String fechaActual = dateFormat.format(hoy).toString();
        /////////////////////////////////////////////////////////////////////////////////////
        String nombreCliente = usuario.getEmpresa().getElementoId();
        if (logger.isDebugEnabled()) {
            logger.debug(":: nombreCliente = " + nombreCliente);
        }
        
        SuplemVO suplemVO = null;
        
        if(this.proc != null && FLUJO_RENOVACION.equals(this.proc)){
                suplemVO = kernelManager.generaSuplems(cdUnieco, cdRamo, estado, nmpoliza, fechaefectividadendoso,
                    nombreCliente, usuario.getUser(), FLUJO_RENOVACION);
                
//Nota: Para el caso de Renovacion los numeros de Suplemento y Suplemento Logico deben de ser ceros "0", en caso de obtener error para renovacion verificar que el SuplemVO regrese estos datos.
                
        	 	globalVarVO.addVariableGlobal(VariableKernel.NumeroSuplemento(), suplemVO.getNmSuplem());
		        globalVarVO.addVariableGlobal(VariableKernel.NumeroSuplementoLogico(), suplemVO.getNsupLogi());
        	
        } else {
                suplemVO = kernelManager.generaSuplems(cdUnieco, cdRamo, estado, nmpoliza, fechaActual,
                        nombreCliente, usuario.getUser(), FLUJO_ENDOSO);
                if (logger.isDebugEnabled()) {
                    logger.debug(":: suplemVO = " + suplemVO);
                }
		        
		        globalVarVO.addVariableGlobal(VariableKernel.NumeroSuplemento(), suplemVO.getNmSuplem());
		        globalVarVO.addVariableGlobal(VariableKernel.NumeroSuplementoLogico(), suplemVO.getNsupLogi());
        }
        //Crear la sesion, entidad y TODOS los bloques:
        kernelManager.crearBloques(idSesion);
        
        //Cargar los bloques referentes a la poliza
        kernelManager.cargarBloquesPoliza(idSesion, globalVarVO, usuario);
        
        
        //Asignar datos de los bloques a variables del action
        try{
            //Para bloque B1
            Campo[] campos = new Campo[4];
            campos[0] = new Campo("FEEFECTO", "");
            campos[1] = new Campo("FEPROREN", "");
            campos[2] = new Campo("CDPERPAG", "");
            campos[3] = new Campo("CDMONEDA", "");
            //campos[4] = new Campo("FEFECSIT", ""); 
            ResultadoTransaccion rt = null;
            rt = ExceptionManager.manage(kernelManager.obtenerDatosBloque(idSesion, ConstantsKernel.BLOQUE_B1, campos));
            if (rt != null) {
                logger.debug("FEEFECTO= " + rt.getCampos()[0].getValor());
                logger.debug("FEPROREN= " + rt.getCampos()[1].getValor());
                logger.debug("CDPERPAG= " + rt.getCampos()[2].getValor());
                logger.debug("CDMONEDA= " + rt.getCampos()[3].getValor());
                //logger.debug("FEFECSIT= " + rt.getCampos()[4].getValor());
                vigenciaDesde = rt.getCampos()[0].getValor();
                fechaEfectividad = rt.getCampos()[0].getValor();
                //fechaEfectividad = rt.getCampos()[4].getValor();
                vigenciaHasta = rt.getCampos()[1].getValor();
                fechaRenovacion = rt.getCampos()[1].getValor();
                formaPago = rt.getCampos()[2].getValor();
                moneda = rt.getCampos()[3].getValor();
                formaPago = kernelManager.obtieneDescripcion("TPERPAG", formaPago);
                moneda = kernelManager.obtieneDescripcion("TMONEDAS", moneda);
                //aseguradora = kernelManager.obtieneDescripcion("TUNIDECO", ServletActionContext.getRequest().getParameter("cdUnieco"));
                aseguradora = ServletActionContext.getRequest().getParameter("aseguradora");
                producto = kernelManager.obtieneDescripcion("TRAMOS", ServletActionContext.getRequest().getParameter("cdRamo"));
                
                //fechas
                /*String dateNow = AONCatwebUtils.getDateNow();
                if (logger.isDebugEnabled()) {
                    logger.debug("  dateNow = " + dateNow);
                }*/
                
                //fechasolicitud = dateNow;
                //fechaefectividadendoso = dateNow;
                fechasolicitud = suplemVO.getFesolici();
                
                logger.debug(" Fecha de efectividad endoso Seteada:" + fechaefectividadendoso);
                
                if(this.proc == null || !FLUJO_RENOVACION.equals(this.proc)) {
                	fechaefectividadendoso = suplemVO.getFeinival();
                } else {
                	if(fechaefectividadendoso!=null){
                	fechaefectividadendoso=dateFormat.format(dateFormat.parse( this.fechaefectividadendoso)).toString();
                	
                	}
                }
                logger.debug(" Fecha de efectividad endoso final:" + fechaefectividadendoso);
                
                session.put(FECHA_EFECTIVIDAD, fechaefectividadendoso);
            }
            
            
            //Para bloque B1B
            /*campos = new Campo[4];
            campos[0] = new Campo("", "");
            campos[1] = new Campo("", "");
            campos[2] = new Campo("", "");
            campos[3] = new Campo("", "");
            */
            
            //Subimos a sesion la Global Variable Container
            session.put(Constantes.GLOBAL_VARIABLE_CONTAINER, globalVarVO);
        
        } catch (mx.com.ice.services.exception.ApplicationException appExc) {
            logger.error(appExc, appExc);
        }
        
        try{
            
            if (logger.isDebugEnabled()){
                logger.debug("***recibeParametros***");
                logger.debug("cdunieco :"+VariableKernel.UnidadEconomica());
                logger.debug("cdramo   :"+VariableKernel.CodigoRamo());
                logger.debug("estado   :"+VariableKernel.Estado());
                logger.debug("nmpoliza :"+VariableKernel.NumeroPoliza());
            }
            Map<String, String> params = new HashMap<String, String>();
            params.put("cdunieco", globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
            params.put("cdramo",   globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo()));
            params.put("estado",   globalVarVO.getValueVariableGlobal(VariableKernel.Estado()));
            params.put("nmpoliza", globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
            params.put("nmsituac", "");
            params.put("cdgarant", "");
            params.put("cdatribu", "");
            params.put("status", "V");  
            
            List<ExtElement> extDatosElementsCopy = endosoManager.getDatosDetallePolizaExt(params);
            ////Agregar los stores a los combos:
            logger.debug(":::: extDatosElementsCopy : " + extDatosElementsCopy);
            //se obtienen los campos y sus valores
            String[] camposValores = null;
            if (extDatosElementsCopy != null && !extDatosElementsCopy.isEmpty()) {
                camposValores = llenaCamposValoresMap(extDatosElementsCopy);
            }
            
            List<ComboControl> comboControlElements = endosoManager.getComboControl(params, 
                    "ENDOSOS_OBTIENE_DATOS_ADICIONALES_COMBO");
            logger.debug(":::: comboControlElements : " + comboControlElements);
            String[] backupTables = new String[comboControlElements.size()];
            if (comboControlElements != null && !comboControlElements.isEmpty()) {
                int store = 0;
                for (ComboControl comboControl : comboControlElements) {
                    logger.debug("::::::::::::::: BackupTable : " + comboControl.getBackupTable());
                    backupTables[store++] = comboControl.getBackupTable();
                }
            }
            
            ComboBoxlUtils comboUtils = new ComboBoxlUtils();
            List<SimpleCombo> simpleComboElements = comboUtils.getDefaultSimpleComboList(comboControlElements, 
                    ServletActionContext.getRequest().getContextPath()
                    + Constantes.URL_ACTION_COMBOS);
            logger.debug("::::::: simpleComboElements : " + simpleComboElements);
            
            String valorAtributo = null;
            String descripcionAtributo = null;
            int numAtributo = 0;
            int numStore = 0;
            String id = null;
            SimpleCombo simpleCombo = null;
            TextFieldControl textField = null;
            parameters = new HashMap<String, String>();
            
            if (dext == null) {
                dext = new ArrayList<ExtElement>();
            }
            
            if (extDatosElementsCopy != null && !extDatosElementsCopy.isEmpty()) {
                for (ExtElement element : extDatosElementsCopy) {
                    id = StringUtils.replace(element.getId(), "+", "p");
                    
                    if (element instanceof SimpleCombo) {
                        simpleCombo = (SimpleCombo) element;
                        
                        for (SimpleCombo scombo : simpleComboElements) {
                            if (id.equals(scombo.getId())) {
                                logger.debug(":::::::::: scombo.getId() : " + scombo.getId());
                                
                                valorAtributo = camposValores[numAtributo++];
                                logger.debug(":: valorAtributo -> " + valorAtributo);
                                descripcionAtributo = getValorAtributo(
                                        backupTables[numStore++], 
                                        valorAtributo);
                                logger.debug(":: descripcionAtributo -> " + descripcionAtributo);
                                
                                scombo.setValue(descripcionAtributo);
                                scombo.setHiddenValue(valorAtributo);
                                scombo.setName(StringUtils.replace(simpleCombo.getName(), "+", "p"));
                                scombo.setHiddenName(StringUtils.replace(simpleCombo.getName(), "+", "p"));
                                scombo.setId(id);
                                
                                if (StringUtils.isBlank(descripcionAtributo) && !scombo.isAllowBlank()) {
                                    JSONObject listeners = new JSONObject();
                                    listeners.accumulate("render", "function(){this.setValue('');}");
                                    scombo.setListeners(listeners);
                                }
                                
                                if (!scombo.isHidden()) {
                                    dext.add(scombo);
                                }
                            }
                        }
                    } else if (element instanceof TextFieldControl) {
                        textField = (TextFieldControl) element;   
                        textField.setValue(camposValores[numAtributo++]);
                        textField.setName(StringUtils.replace(textField.getName(), "+", "p"));
                        textField.setId(id);
                        
                        if (!textField.isHidden()) {
                            dext.add(textField);    
                        }
                    }   
                }
                
                //parameters.put(id, camposValores[numAtributo++]);
            }            
            
            //Obtener datos de la poliza (numero externo de la poliza)
            List<DatosPolizaVO> datosPolizaList = (List<DatosPolizaVO>)endosoManager.obtieneDatosPoliza(params);
            if(datosPolizaList != null && datosPolizaList.size() >= 1){
                DatosPolizaVO datosPoliza = (DatosPolizaVO)datosPolizaList.get(0);
                poliza = datosPoliza.getNmpoliex();
                if(logger.isDebugEnabled()){
                    logger.debug("CDPERPAG: " + datosPoliza.getCdperpag());
                    logger.debug("CDMONEDA: " + datosPoliza.getCdmoneda());
                    logger.debug("OTTEMPOT: " + datosPoliza.getOttempot());
                    logger.debug("NMRENOVA: " + datosPoliza.getNmrenova());
                    logger.debug("NMPOLIEX: " + datosPoliza.getNmpoliex());
                }
            }
            
            //TODO: pasarlo a la 1era pantalla de busqueda, para que redigira a ella si no se puede hacer el endoso
            //Obtener mensaje de error en la cancelacion
            PolizaCancelVO polizaCancel = null; // TODO revisar validacion: endosoManager.validaPolizaCancel(params);
            msgId = "";
            if(polizaCancel != null){
                msgId = polizaCancel.getMsgId();
                logger.debug("msg_id: " + polizaCancel.getMsgId());
                logger.debug("dsmotanu: " + polizaCancel.getDescMotivoAnulacion());
                logger.debug("fecancel: " + polizaCancel.getFechaCancelacion());
                logger.debug("title: " + polizaCancel.getTitle());
            }
            
            StringBuilder builder = new StringBuilder();            
            builder.append("{id:'guardarDatosAdicionales',text:'Guardar',tooltip:'Guardar datos adicionales de la poliza',");
            if (dext.isEmpty()) {
                builder.append("hidden:true,");
            }
            builder.append("handler:function(){dataAdicionalesPoliza.form.submit({waitMsg:'Procesando...'," +
                    "success:function(form,action){if(Ext.util.JSON.decode(action.response.responseText).saved == \"true\")" +
                    "{Ext.Msg.alert('Status', \"Datos guardados.\");}else {Ext.Msg.alert('Error', Ext.util.JSON.decode(action.response.responseText).mensajeError +\" Intente de nuevo.\");}}});}}");
            botonDatVar = builder.toString();            
            
            if (logger.isDebugEnabled()){
                logger.debug(":::::::::::: dext final  : " + dext);
                logger.debug(":::::::::::: botonDatVar : " + botonDatVar);
                logger.debug(":::::::::::: msg_id      : " + msgId);
            }
        } catch(Exception e){
            if (logger.isDebugEnabled()) {
                logger.debug("Exception EndosoAction.recibeParametros: " + dext);
                logger.error(e);
            }
        }

        return INPUT;
    }
    
    /**
     * @param extDatosElem 
     * @return
     * @throws ApplicationException 
     * @throws mx.com.ice.services.exception.ApplicationException 
     */
    private String[] llenaCamposValoresMap(List<ExtElement> extDatosElem) 
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
        
        rt = ExceptionManager.manage(kernelManager.obtenerDatosBloque(
                idSesion, ConstantsKernel.BLOQUE_B1B, campos));
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
     * Guardar datos adicionales de la poliza
     */
    @SuppressWarnings("unchecked")
    public String guardaDatosAdicionales(){
        if(logger.isDebugEnabled()){
            logger.debug("Entrando a guardarDatosAdicionales");
            logger.debug("::: parameters :::::: " + parameters);
        }
        ResultadoTransaccion rt = null;
        
        Map<String, String> paramsMap = null;
        if (parameters != null && !parameters.values().contains("") && !parameters.values().contains(null)) {
            paramsMap = new HashMap<String, String>();
            
            String key = null;
            Iterator<String> itParams = parameters.keySet().iterator();
            while (itParams.hasNext()) {
                key = itParams.next();
                paramsMap.put(key, parameters.get(key));
                logger.debug(key + " := "+ parameters.get(key).toString());
            }
            
            logger.debug("::::::::::::::::::::: paramsMap :::: " + paramsMap);
        }       
        // TODO:Actualizar en bloques B1B, guardar y lanzar validaciones
        try {
            rt = ExceptionManager.manage(kernelManager.guardaDatosAdicionales(ServletActionContext.getRequest().getSession().getId(), (GlobalVariableContainerVO)session.get("GLOBAL_VARIABLE_CONTAINER"), paramsMap));
            success = true;
            saved = "true"; 
        } catch (Exception e) {
            success = false;
            saved = "false";
            mensajeError = e.toString();
            logger.error("Exception EndosoAction.guardaDatosAdicionales: "+ e);
        }
        
        return SUCCESS;
    }

    public String bajaInciso(){
        if(logger.isDebugEnabled()){
            logger.debug("Entrando a bajaInciso");
        }
        
        BaseObjectVO baseObjectVO = null;
        GlobalVariableContainerVO globalVarVO = (GlobalVariableContainerVO)session.get("GLOBAL_VARIABLE_CONTAINER");
        
        Map <String,String> params = new HashMap<String,String>();
        params.put("cdunieco", globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica()) );
        params.put("cdramo", globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo()) );
        params.put("estado", globalVarVO.getValueVariableGlobal(VariableKernel.Estado()) );
        params.put("nmpoliza", globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza()) );
        params.put("nmsituac", nmSituac );
        params.put("nmsuplem", globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento()) );
        if(logger.isDebugEnabled()){
            logger.debug("params: " + params);
        }
        
        try{
            baseObjectVO = (BaseObjectVO)endosoManager.bajaInciso(params);
            if(logger.isDebugEnabled()){
                logger.debug("baseObjectVO: " + baseObjectVO);
            }
        }catch(ApplicationException ex){
            logger.error("EXCEPTION in bajaInciso(): " + ex);
            saved = "false";
            success = false;
        }
        success = true;
        saved = "true"; 
        return SUCCESS;
    }


    public String irPolizasRenovadasClick() throws Exception{
    	if ( StringUtils.isBlank( idRegresar ) )
    		super.updateParametrosRegresar();
    	return "polizasRenovadas";
    }

    
    public boolean isSuccess() {
        return success;
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
     * @return the cdRol
     */
    public String getCdRol() {
        return cdRol;
    }

    /**
     * @param cdRol the cdRol to set
     */
    public void setCdRol(String cdRol) {
        this.cdRol = cdRol;
    }

    /**
     * @return the cdPerson
     */
    public String getCdPerson() {
        return cdPerson;
    }

    /**
     * @param cdPerson the cdPerson to set
     */
    public void setCdPerson(String cdPerson) {
        this.cdPerson = cdPerson;
    }

    /**
     * @return the dext
     */
    @SuppressWarnings("unchecked")
    public List getDext() {
        return dext;
    }

    /**
     * @param dext the dext to set
     */
    @SuppressWarnings("unchecked")
    public void setDext(List dext) {
        this.dext = dext;
    }

    /**
     * @param agrupadores the agrupadores to set
     */
    public void setAgrupadores(List<AgrupadoresVO> agrupadores) {
        this.agrupadores = agrupadores;
    }
    
    /**
     * @param agrupadores the agrupadores to get
     */
    public List<AgrupadoresVO> getAgrupadores() {
        return agrupadores;
    }

    /**
     * @return the bancos
     */
    public List<BaseObjectVO> getBancos() {
        return bancos;
    }

    /**
     * @param bancos the bancos to set
     */
    public void setBancos(List<BaseObjectVO> bancos) {
        this.bancos = bancos;
    }

    /**
     * @return the instrumentosPago
     */
    public List<BaseObjectVO> getInstrumentosPago() {
        return instrumentosPago;
    }

    /**
     * @param instrumentosPago the instrumentosPago to set
     */
    public void setInstrumentosPago(List<BaseObjectVO> instrumentosPago) {
        this.instrumentosPago = instrumentosPago;
    }

    /**
     * @return the personasUsuarios
     */
    public List<BaseObjectVO> getPersonasUsuarios() {
        return personasUsuarios;
    }

    /**
     * @param personasUsuarios the personasUsuarios to set
     */
    public void setPersonasUsuarios(List<BaseObjectVO> personasUsuarios) {
        this.personasUsuarios = personasUsuarios;
    }

    /**
     * @return the sucursal
     */
    public List<BaseObjectVO> getSucursal() {
        return sucursal;
    }

    /**
     * @param sucursal the sucursal to set
     */
    public void setSucursal(List<BaseObjectVO> sucursal) {
        this.sucursal = sucursal;
    }

    /**
     * @return the idUser
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
     * @return the domicilio
     */
    public List<BaseObjectVO> getDomicilio() {
        return domicilio;
    }

    /**
     * @param domicilio the domicilio to set
     */
    public void setDomicilio(List<BaseObjectVO> domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * @return the cdDomicilio
     */
    public String getCdDomicilio() {
        return cdDomicilio;
    }

    /**
     * @param cdDomicilio the cdDomicilio to set
     */
    public void setCdDomicilio(String cdDomicilio) {
        this.cdDomicilio = cdDomicilio;
    }

    public String getDsDomicilio() {
        return dsDomicilio;
    }

    public void setDsDomicilio(String dsDomicilio) {
        this.dsDomicilio = dsDomicilio;
    }
    
    /**
     * @return the cdInstrumentoPago
     */
    public String getCdInstrumentoPago() {
        return cdInstrumentoPago;
    }

    /**
     * @param cdInstrumentoPago the cdInstrumentoPago to set
     */
    public void setCdInstrumentoPago(String cdInstrumentoPago) {
        this.cdInstrumentoPago = cdInstrumentoPago;
    }

    /**
     * @return the cdBanco
     */
    public String getCdBanco() {
        return cdBanco;
    }

    /**
     * @param cdBanco the cdBanco to set
     */
    public void setCdBanco(String cdBanco) {
        this.cdBanco = cdBanco;
    }

    /**
     * @return the cdSucursal
     */
    public String getCdSucursal() {
        return cdSucursal;
    }

    /**
     * @param cdSucursal the cdSucursal to set
     */
    public void setCdSucursal(String cdSucursal) {
        this.cdSucursal = cdSucursal;
    }

    /**
     * @return the cdTipoTarjeta
     */
    public String getCdTipoTarjeta() {
        return cdTipoTarjeta;
    }

    /**
     * @param cdTipoTarjeta the cdTipoTarjeta to set
     */
    public void setCdTipoTarjeta(String cdTipoTarjeta) {
        this.cdTipoTarjeta = cdTipoTarjeta;
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
     * @return the suplLogico
     */
    public String getSuplLogico() {
        return suplLogico;
    }

    /**
     * @param suplLogico the suplLogico to set
     */
    public void setSuplLogico(String suplLogico) {
        this.suplLogico = suplLogico;
    }

    /**
     * @return the suplFisico
     */
    public String getSuplFisico() {
        return suplFisico;
    }

    /**
     * @param suplFisico the suplFisico to set
     */
    public void setSuplFisico(String suplFisico) {
        this.suplFisico = suplFisico;
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
     * @return the tipoTarjeta
     */
    public List<BaseObjectVO> getTipoTarjeta() {
        return tipoTarjeta;
    }

    /**
     * @param tipoTarjeta the tipoTarjeta to set
     */
    public void setTipoTarjeta(List<BaseObjectVO> tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }



    public String getVigenciaDesde() {
        return vigenciaDesde;
    }

    public void setVigenciaDesde(String vigenciaDesde) {
        this.vigenciaDesde = vigenciaDesde;
    }

    public String getFechaEfectividad() {
        return fechaEfectividad;
    }

    public void setFechaEfectividad(String fechaEfectividad) {
        this.fechaEfectividad = fechaEfectividad;
    }

    public String getVigenciaHasta() {
        return vigenciaHasta;
    }

    public void setVigenciaHasta(String vigenciaHasta) {
        this.vigenciaHasta = vigenciaHasta;
    }

    public String getFechaRenovacion() {
        return fechaRenovacion;
    }

    public void setFechaRenovacion(String fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getSaved() {
        return saved;
    }

    public void setSaved(String saved) {
        this.saved = saved;
    }
    
    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getPoliza() {
        return poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * @return the fechaefectividadendoso
     */
    public String getFechaefectividadendoso() {
        return fechaefectividadendoso;
    }

    /**
     * @param fechaefectividadendoso the fechaefectividadendoso to set
     */
    public void setFechaefectividadendoso(String fechaefectividadendoso) {
        this.fechaefectividadendoso = fechaefectividadendoso;
    }

    /**
     * @return the fechasolicitud
     */
    public String getFechasolicitud() {
        return fechasolicitud;
    }

    /**
     * @param fechasolicitud the fechasolicitud to set
     */
    public void setFechasolicitud(String fechasolicitud) {
        this.fechasolicitud = fechasolicitud;
    }

    /**
     * @return the fechaActualiza
     */
    public String getFechaActualiza() {
        return fechaActualiza;
    }

    /**
     * @param fechaActualiza the fechaActualiza to set
     */
    public void setFechaActualiza(String fechaActualiza) {
        this.fechaActualiza = fechaActualiza;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the mensajeErrorFechas
     */
    public String getMensajeErrorFechas() {
        return mensajeErrorFechas;
    }

    /**
     * @param mensajeErrorFechas the mensajeErrorFechas to set
     */
    public void setMensajeErrorFechas(String mensajeErrorFechas) {
        this.mensajeErrorFechas = mensajeErrorFechas;
    }

    /**
     * @return the errorFechas
     */
    public boolean isErrorFechas() {
        return errorFechas;
    }

    /**
     * @param errorFechas the errorFechas to set
     */
    public void setErrorFechas(boolean errorFechas) {
        this.errorFechas = errorFechas;
    }
    
    public String getVolver3() {
		return volver3;
	}

	public void setVolver3(String volver3) {
		this.volver3 = volver3;
	}

    /**
     * @return the botonDatVar
     */
    public String getBotonDatVar() {
        return botonDatVar;
    }

    /**
     * @param botonDatVar the botonDatVar to set
     */
    public void setBotonDatVar(String botonDatVar) {
        this.botonDatVar = botonDatVar;
    }

	public void setProc(String proc) {
		this.proc = proc;
	}

	public String getProc() {
		return proc;
	}

	public String getIdRegresar() {
		return idRegresar;
	}

	public void setIdRegresar(String idRegresar) {
		this.idRegresar = idRegresar;
	}
}