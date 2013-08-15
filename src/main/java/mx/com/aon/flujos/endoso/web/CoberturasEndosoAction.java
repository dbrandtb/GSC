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

import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.VariableKernel;
import mx.com.aon.kernel.model.ValoresXDefectoCoberturaVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.procesos.emision.model.IncisosVO;
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
 * @date 02/10/2008
 *
 */
public class CoberturasEndosoAction extends PrincipalEndosoAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final String DEDUCIBLE = "B19B_C1";

    private String status;
    private String claveObjeto;
    private String descripcionObjeto;
    private String labelCombo;
    private List<ExtElement> extElementsAgregar;
    private List<ExtElement> extElements;
    
    private boolean validoElementsAgregar;
    private String importe = "0" ;
    private List<IncisosVO> listCoberturasIncisos;
    
    private List<BaseObjectVO> listaCoberturas;    
    private String codigoCoberturas;
    private String descripcionCoberturas;
    private String sumaAsegurada;
    private String dsDeducible;
    private String cobertura;
    private String hcobertura;
    private String cdGarant;
    private String dsGarant;
    private String dsSumaAsegurada;
    private Map<String, String> parameters;
    
    private String saved;
    private String mensajeError;
    private boolean agregarCoberturas;
    
    private String fechaVencimiento;
    
    private boolean success;
    
    /**
     * Consulta para datos del grid de coberturas
     * @return SUCCESS
     * @author Alejandro Garcia
     * @throws ApplicationException 
     * @throws mx.com.ice.services.exception.ApplicationException 
     */
    @SuppressWarnings("unchecked")
    public String coberturasGridJson() throws mx.com.ice.services.exception.ApplicationException,
            ApplicationException{
        if (logger.isDebugEnabled()){
            logger.debug("---> CoberturasEndosoAction.coberturasGridJson");
        }
        listCoberturasIncisos = new ArrayList<IncisosVO>();
        
        try {
            PagedList pagedList = obtenCoberturasIncisos();
            if (pagedList != null) {
                listCoberturasIncisos = pagedList.getItemsRangeList();
                totalCount = pagedList.getTotalItems();
            }        
        } catch(ApplicationException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("coberturasGridJson EXCEPTION:: " + ex);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug(".. listCoberturasIncisos : " + listCoberturasIncisos);
        }
        success = true;
        return SUCCESS;
    }
    
    /**
     * @return
     * @throws ApplicationException 
     * @throws mx.com.ice.services.exception.ApplicationException 
     */
    private PagedList obtenCoberturasIncisos() throws mx.com.ice.services.exception.ApplicationException, ApplicationException {
//      se obtienen los datos
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        
        cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSuplem = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
        nmSituac = obtieneNmSituac();
        
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        String cdPlan = null;
        Campo[] campos = new Campo[1];
        campos[0] = new Campo("CDPLAN", "");
        ResultadoTransaccion rt = ExceptionManager.manage(kernelManager.obtenerDatosBloque(idSesion, 
                ConstantsKernel.BLOQUE_B5, campos));
        cdPlan = rt.getCampos()[0].getValor();
        if (logger.isDebugEnabled()) {
            logger.debug("===CDPLAN= " + cdPlan);
        }
        
        UserVO usuario = (UserVO)session.get("USUARIO");
                
        if (logger.isDebugEnabled()){
            logger.debug("---> getCoberturasIncisos");
            logger.debug("usuario      :" + usuario.getCodigoPersona());
            logger.debug("cdUnieco     :" + cdUnieco);
            logger.debug("cdRamo       :" + cdRamo);
            logger.debug("estado       :" + estado);
            logger.debug("nmPoliza     :" + nmPoliza);
            logger.debug("nmSituac     :" + nmSituac);
            logger.debug("nmsuplem     :" + nmSuplem);
            logger.debug("cdplan       :" + cdPlan);
        }

        Map <String,String> params = new HashMap<String,String>();
        params.put("usuario", usuario.getCodigoPersona());
        params.put("cdunieco", cdUnieco);
        params.put("cdramo", cdRamo);
        params.put("estado", estado);
        params.put("nmpoliza", nmPoliza);
        params.put("nmsituac", nmSituac);
        params.put("nmsuplem", nmSuplem);
        params.put("cdplan", cdPlan);
        
        try{
            return endosoManager.getPagedList(params,"ENDOSOS_GRID_COBERTURA_INCISOS",start, limit);
        }catch(ApplicationException ex){
            if (logger.isDebugEnabled())
                logger.debug("coberturasGridJson EXCEPTION:: " + ex);
        }

        return null;
    }

    /**
     * 
     * @return
     * @throws mx.com.ice.services.exception.ApplicationException 
     * @throws Exception 
     */
    public String agregarCoberturas() throws Exception {
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        
        cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSituac = obtieneNmSituac(); 
        
        if (logger.isDebugEnabled()){
            logger.debug("----> agregarCoberturas");
            logger.debug("cdUnieco     :" + cdUnieco);
            logger.debug("cdRamo       :" + cdRamo);
            logger.debug("estado       :" + estado);
            logger.debug("nmPoliza     :" + nmPoliza);
            logger.debug("nmSituac     :" + nmSituac);
            logger.debug("sumaAsegurada   :" + sumaAsegurada);
            logger.debug("cobertura       :" + cobertura);
            logger.debug("hcobertura       :" + hcobertura);
            logger.debug("parameters      :" + parameters);
        }
        
        boolean save = true;
        PagedList pagedList = obtenCoberturasIncisos();
        if (pagedList != null) {
            listCoberturasIncisos = pagedList.getItemsRangeList();
        } else {
            listCoberturasIncisos = new ArrayList<IncisosVO>();
        }
        //TODO checar si se quita esta validacion
        /*if (listCoberturasIncisos != null && !listCoberturasIncisos.isEmpty()) {
            for (IncisosVO cob : listCoberturasIncisos) {
                logger.debug("----> cob.getDsGarant()  :" + cob.getDsGarant());
                if (hcobertura.equalsIgnoreCase(cob.getDsGarant())) {
                    save = false;
                    saved = "false";
                    mensajeError = "Esta cobertura ya fue seleccionada previamente.";
                    break;
                }
            }
        }*/
        
        if (logger.isDebugEnabled()){
            logger.debug(":::::: save :::::: " + save);
        }
        
        if (save) {
            String idSesion = ServletActionContext.getRequest().getSession().getId();
            //nmSituac = obtieneNmSituac();  
            UserVO usuario = (UserVO)session.get("USUARIO");
            
            if (logger.isDebugEnabled()) {
                logger.debug("CDELEMENTO: " + usuario.getEmpresa().getElementoId());
                logger.debug("CDPERSON: " + usuario.getCodigoPersona()); 
                logger.debug("CDROL: " + usuario.getRolActivo().getObjeto().getValue()); 
            }
            
            String cdGarant = null;
            //List<BaseObjectVO> listCoberturas = coberturaPolizaList();
            if (listCoberturasIncisos != null && !listCoberturasIncisos.isEmpty()) {
                for (IncisosVO cob : listCoberturasIncisos) {
                    logger.debug("----> cob.getLabel()  : " + cob.getDsGarant());
                    if (hcobertura.equalsIgnoreCase(cob.getDsGarant())) {
                        cdGarant = cob.getCdGarant();
                        break;
                    }
                }
            }
            
            if (StringUtils.isBlank(cdGarant)) {
                cdGarant = hcobertura;
            }
            
            if (logger.isDebugEnabled()) {
                logger.debug(":::: cdGarant ::: " + cdGarant); 
            }
            
            parameters = obtieneParametrosCobertura(cdGarant, parameters);
            
            if (logger.isDebugEnabled()) {
                logger.debug("-------------->>>> parameters      :" + parameters);
            }
            
            try {
                kernelManager.guardarCoberturasAtributosEndosos(idSesion, globalVarVO, parameters,
                        cdGarant, usuario.getEmpresa().getElementoId(), sumaAsegurada, Constantes.INSERT_MODE);
                saved = "true";
                if (logger.isDebugEnabled()){
                    logger.debug(":::::: Se salvaron los bloques b18 y b19b exitosamente :::::: ");
                }
            } catch (mx.com.ice.services.exception.ApplicationException appExc) {
                logger.error("::::::::::::::::::::::: ERROR :::::::::::::::::::::::::::");
                logger.error(appExc);
                mensajeError = appExc.getMessage();
                saved = "false";
            }
        }
               
        if (logger.isDebugEnabled()){
            logger.debug(":::::: save final :::::: " + saved);
        }
        success = true;
        return SUCCESS;
    }
    
    /**
     * @param cdGarant2
     * @param parameters2
     * @return
     */
    private Map<String, String> obtieneParametrosCobertura(String cdGarant2, Map<String, String> parameters2) {
        if (logger.isDebugEnabled()){
            logger.debug("----> obtieneParametrosCobertura");
            logger.debug("cdGarant         : " + cdGarant2);
            logger.debug("parameters       : " + parameters2);
        }
        
        Map<String, String> parametersResult = new HashMap<String, String>();
        
        if (parameters2 != null && !parameters2.isEmpty()) {
            String claveAtributo = null;
            Iterator<String> itClaves = parameters2.keySet().iterator();
            
            while (itClaves.hasNext()) {
                claveAtributo = itClaves.next();
                logger.debug("::: claveAtributo ::: " + claveAtributo);
                //clave = obtieneClave(claveAtributo);
                //clave = clave.replaceAll("C", "");
                //logger.debug("::: clave ::: " + clave);
                
                //b19b
                parametersResult.put(new StringBuffer().append(claveAtributo)
                                               .append("_")
                                               .append(cdGarant2)
                                               .toString(), 
                                               parameters2.get(claveAtributo)
                                               );
            }
        }
        
        return parametersResult;
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public String editarCoberturas() throws Exception{
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSituac = obtieneNmSituac(); 
        status = "V";
        
        if (logger.isDebugEnabled()){
            logger.debug("----> editarCoberturas");
            logger.debug("cdUnieco     :" + cdUnieco);
            logger.debug("cdRamo       :" + cdRamo);
            logger.debug("estado       :" + estado);
            logger.debug("nmPoliza     :" + nmPoliza);
            logger.debug("nmSituac     :" + nmSituac);
            logger.debug("status       :" + status);
            logger.debug("cdGarant     :" + cdGarant);
            logger.debug("sumaAsegurada:" + sumaAsegurada);
            logger.debug("parameters   :" + parameters);
        }
        
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        //nmSituac = obtieneNmSituac();  
        UserVO usuario = (UserVO)session.get("USUARIO");
        
        parameters = obtieneParametrosCobertura(cdGarant, parameters);
        
        if (logger.isDebugEnabled()) {
            logger.debug("editar --------->>>> parameters      :" + parameters);
        }
        
        try {
            kernelManager.guardarCoberturasAtributosEndosos(idSesion, globalVarVO, parameters,
                    cdGarant, usuario.getEmpresa().getElementoId(), sumaAsegurada, Constantes.UPDATE_MODE);
            saved = "true";
            if (logger.isDebugEnabled()){
                logger.debug(":::::: Se editaron los bloques b18 y b19b exitosamente :::::: ");
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
    public String borrarCoberturas() throws Exception{
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSuplem = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
        fechaVencimiento = globalVarVO.getValueVariableGlobal(VariableKernel.FechaVencimiento());
        //TODO quitar hardcode
        fechaVencimiento = "02/07/2009";
        nmSituac = obtieneNmSituac(); 
        
        UserVO usuario = (UserVO)session.get("USUARIO");
        
        if (logger.isDebugEnabled()){
            logger.debug("----> borrarCoberturas");
            logger.debug("cdUnieco          :" + cdUnieco);
            logger.debug("cdRamo            :" + cdRamo);
            logger.debug("estado            :" + estado);
            logger.debug("nmPoliza          :" + nmPoliza);
            logger.debug("nmSituac          :" + nmSituac);
            logger.debug("nmSuplem          :" + nmSuplem);
            logger.debug("fechaVencimiento  :" + fechaVencimiento);
            logger.debug("cdGarant          :" + cdGarant);
            logger.debug("sumaAsegurada     :" + sumaAsegurada);
            logger.debug("status            :" + status);
        }
        
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        kernelManager.borrarCoberturasBloques(globalVarVO, cdGarant, idSesion);
        
        success = true;
        return SUCCESS;
    }

    /**
     * Metodo que carga la lista para mostrar atributos variables.
     * @return
     * @throws Exception
     *  
     */
    @SuppressWarnings("unchecked")
    public String obtenCombos() throws Exception {
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        
        cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSituac = obtieneNmSituac(); 
        if (StringUtils.isBlank(status)) {
            status = "V";
        }
        
        if (logger.isDebugEnabled()){
            logger.debug("----> obtenCombos");
            logger.debug("cdUnieco     : " + cdUnieco);
            logger.debug("cdRamo       : " + cdRamo);
            logger.debug("estado       : " + estado);
            logger.debug("nmPoliza     : " + nmPoliza);
            logger.debug("nmSituac     : " + nmSituac);
            logger.debug("status       : " + status);
            logger.debug("###### codigoObjeto   : " + claveObjeto);
            logger.debug("###### descripcionObj : " + descripcionObjeto);
        }

        Map <String,String> param = new HashMap<String,String>();
        param.put("cdunieco", cdUnieco);
        param.put("cdramo", cdRamo);
        param.put("estado", estado);
        param.put("nmpoliza", nmPoliza);
        param.put("nmsituac", nmSituac);
        param.put("cdgarant", claveObjeto);
        param.put("cdatribu", "");
        param.put("status", status); 
        
        if(StringUtils.isNotBlank(descripcionObjeto)){
            labelCombo = descripcionObjeto;
            session.put("DESCRIPCION_OBJETO_COBERTURAS", descripcionObjeto);
        }
     
        if (StringUtils.isNotBlank(claveObjeto)) {
            if (globalVarVO == null) {
                globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
            }
            
            UserVO usuario = (UserVO)session.get("USUARIO");
            
            if (StringUtils.isBlank(cdRamo)) {
                logger.debug("VariableKernel.CodigoRamo: " + globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo()));
                cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
            }
            if (logger.isDebugEnabled()) {
                logger.debug("CDELEMENTO: " + usuario.getEmpresa().getElementoId()); 
                logger.debug("PRODUCTO: " + cdRamo);
                logger.debug("CDGARANT: " + claveObjeto);
            }
            String idSesion = ServletActionContext.getRequest().getSession().getId();
            String cdElemento = usuario.getEmpresa().getElementoId();
            //////////////////////////////////////////////////////////////////////////
            // Se disparan valores por defecto
            Map<String, ValoresXDefectoCoberturaVO> valoresXDefectoMap = 
                kernelManager.valoresXDefectoIncisoCoberturaEndosos(idSesion, nmSituac, globalVarVO, cdRamo, 
                    cdElemento, false, claveObjeto);
            if (logger.isDebugEnabled()){
                logger.debug("::: valoresXDefectoMap     : " + valoresXDefectoMap);
            }
            /////////////////////////////////////////////////////////////////////////
            
            session.put("CLAVE_OBJETO_COBERTURAS", claveObjeto);
            extElementsAgregar = obtenListCoberturasExtElements(param, valoresXDefectoMap);
            /*SimpleCombo simpleCombo = null;
            String idCombo = null;
            String cve = null;
            ValoresXDefectoCoberturaVO valoresXDefectoCoberturaVO = null;
            String valorAtributo = null;
            String descripcionAtributo = null;*/
            
            if (extElementsAgregar != null && !extElementsAgregar.isEmpty() &&
                    valoresXDefectoMap != null && !valoresXDefectoMap.isEmpty()
                    && extElementsAgregar.size() == valoresXDefectoMap.size()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("La sesion trae algo y entro a levantarla");
                }
                
                /*for (ExtElement element : extElementsAgregar) {
                    idCombo = element.getId();
                    if (logger.isDebugEnabled()) {
                        logger.debug(":: idCombo -> " + idCombo);
                    }
                    if (element instanceof SimpleCombo) {
                        simpleCombo = (SimpleCombo) element;
                        
                        StringTokenizer st = new StringTokenizer(idCombo, "_");
                        st.nextToken();
                        cve = st.nextToken();
                        logger.debug(":: cve Atributo -> " + cve);
                        valoresXDefectoCoberturaVO = valoresXDefectoMap.get(cve);
                        valorAtributo = valoresXDefectoCoberturaVO.getValorCob();
                        logger.debug(":: valorAtributo -> " + valorAtributo);
                        descripcionAtributo = obtenValorAtributo("", valorAtributo);
                        logger.debug(":: descripcionAtributo -> " + descripcionAtributo);
                        
                        simpleCombo.setValue(descripcionAtributo);
                    }
                }*/
                
                if (extElementsAgregar != null && !extElementsAgregar.isEmpty()) {
                    validoElementsAgregar = true;
                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("La session no trae nada");
                }
                extElementsAgregar = new ArrayList<ExtElement>();
            }
            
            if (logger.isDebugEnabled()) {
                logger.debug("---> validoElementsAgregar :::: " + validoElementsAgregar);
                logger.debug("---> extElementsAgregar :::: " + extElementsAgregar);
                logger.debug("---> importe :::: " + importe);
            }
            
            success = true;
        }
        
        return SUCCESS;
    }
    
    /**
     * Metodo que carga la lista para mostrar atributos variables.
     * @return
     * @throws Exception
     *  
     */
    @SuppressWarnings("unchecked")
    public String cuentaCoberturas() throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        //se obtienen los datos
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        
        Campo[] campos = new Campo[3];
        campos[0] = new Campo("CDPLAN", "");
        campos[1] = new Campo("NMSITUAC", "");
        campos[2] = new Campo("CDTIPSIT", "");
        ResultadoTransaccion rt = ExceptionManager.manage(kernelManager.obtenerDatosBloque(
                idSesion, ConstantsKernel.BLOQUE_B5, campos));
        if (rt != null) {
            logger.debug(":: CDPLAN= " + rt.getCampos()[0].getValor());
            logger.debug(":: NMSITUAC= " + rt.getCampos()[1].getValor());
            logger.debug(":: CDTIPSIT= " + rt.getCampos()[2].getValor());
        }
        
        UserVO usuario = (UserVO)session.get("USUARIO");
        
        String region = null;
        String pais = null;
        String idioma = null;
        
        if (usuario.getRegion() != null) {
            region = usuario.getRegion().getValue();
        }
        if (usuario.getPais() != null) {
            pais = usuario.getPais().getValue();
        }
        if (usuario.getIdioma() != null) {
            idioma = usuario.getIdioma().getValue();
        }
        
        if (logger.isDebugEnabled()) {
            logger.debug("------> cuentaCoberturas");
            logger.debug("CD_ELEMENTO: " + usuario.getEmpresa().getElementoId()); 
            logger.debug("CD_UNIECO  : " + globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
            logger.debug("CD_RAMO    : " + globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo()));
            logger.debug("CD_ESTADO  : " + globalVarVO.getValueVariableGlobal(VariableKernel.Estado()));
            logger.debug("CD_NMPOLIZA: " + globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
            logger.debug("CD_NMSITUAC: " + globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSituacion()));
            logger.debug("CD_TIPSIT  : " + globalVarVO.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
            logger.debug("CD_PLAN    : " + rt.getCampos()[0].getValor());
            logger.debug("CD_REGION  : " + region);
            logger.debug("CD_PAIS    : " + pais);
            logger.debug("CD_IDIOMA  : " + idioma);
        }
        
        if (StringUtils.isBlank(region)) {
            region = "ME";
        }
        
        if (StringUtils.isBlank(pais)) {
            pais = "0";
        }
        
        if (StringUtils.isBlank(idioma)) {
            idioma = "0";
        }
        
        parameters.put("CD_ELEMENTO", usuario.getEmpresa().getElementoId());
        parameters.put("CD_UNIECO", globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
        parameters.put("CD_RAMO", globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo()));
        parameters.put("CD_ESTADO", globalVarVO.getValueVariableGlobal(VariableKernel.Estado()));
        parameters.put("CD_NMPOLIZA", globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
        parameters.put("CD_NMSITUAC", globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSituacion()));
        parameters.put("CD_TIPSIT", globalVarVO.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
        parameters.put("CD_PLAN", rt.getCampos()[0].getValor());
        parameters.put("CD_REGION", region);
        parameters.put("CD_PAIS", pais);
        parameters.put("CD_IDIOMA", idioma);
                        
        listaCoberturas = catalogManager.getItemList("OBTIENE_LISTA_COBERTURAS_NO_AGREGADAS", parameters);
        if(listaCoberturas == null) {
            listaCoberturas = new ArrayList<BaseObjectVO>();
        }
        
        if (listaCoberturas.size() > 0) {
            agregarCoberturas = true;
        }
        
        if (logger.isDebugEnabled()) {
            logger.debug(":::::............ listaCoberturas : " + listaCoberturas);
            logger.debug(":::::.......... agregarCoberturas : " + agregarCoberturas);
        }
        /////////////////////////////////////////////////////////////
                    
        success = true;
        
        return SUCCESS;
    }
    
    public String coberturaList() throws Exception{        
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        //se obtienen los datos
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        
        Campo[] campos = new Campo[3];
        campos[0] = new Campo("CDPLAN", "");
        campos[1] = new Campo("NMSITUAC", "");
        campos[2] = new Campo("CDTIPSIT", "");
        ResultadoTransaccion rt = ExceptionManager.manage(kernelManager.obtenerDatosBloque(
                idSesion, ConstantsKernel.BLOQUE_B5, campos));
        if (rt != null) {
            logger.debug(":: CDPLAN= " + rt.getCampos()[0].getValor());
            logger.debug(":: NMSITUAC= " + rt.getCampos()[1].getValor());
            logger.debug(":: CDTIPSIT= " + rt.getCampos()[2].getValor());
        }
        
        UserVO usuario = (UserVO)session.get("USUARIO");
        
        String region = null;
        String pais = null;
        String idioma = null;
        
        if (usuario.getRegion() != null) {
            region = usuario.getRegion().getValue();
        }
        if (usuario.getPais() != null) {
            pais = usuario.getPais().getValue();
        }
        if (usuario.getIdioma() != null) {
            idioma = usuario.getIdioma().getValue();
        }
        
        if (logger.isDebugEnabled()) {
            logger.debug("------> coberturaList");
            logger.debug("CD_ELEMENTO: " + usuario.getEmpresa().getElementoId()); 
            logger.debug("CD_UNIECO  : " + globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
            logger.debug("CD_RAMO    : " + globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo()));
            logger.debug("CD_ESTADO  : " + globalVarVO.getValueVariableGlobal(VariableKernel.Estado()));
            logger.debug("CD_NMPOLIZA: " + globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
            logger.debug("CD_NMSITUAC: " + globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSituacion()));
            logger.debug("CD_TIPSIT  : " + globalVarVO.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
            logger.debug("CD_PLAN    : " + rt.getCampos()[0].getValor());
            logger.debug("CD_REGION  : " + region);
            logger.debug("CD_PAIS    : " + pais);
            logger.debug("CD_IDIOMA  : " + idioma);
        }
        
        if (StringUtils.isBlank(region)) {
            region = "ME";
        }
        
        if (StringUtils.isBlank(pais)) {
            pais = "0";
        }
        
        if (StringUtils.isBlank(idioma)) {
            idioma = "0";
        }
        
        parameters.put("CD_ELEMENTO", usuario.getEmpresa().getElementoId());
        parameters.put("CD_UNIECO", globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
        parameters.put("CD_RAMO", globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo()));
        parameters.put("CD_ESTADO", globalVarVO.getValueVariableGlobal(VariableKernel.Estado()));
        parameters.put("CD_NMPOLIZA", globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
        parameters.put("CD_NMSITUAC", globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSituacion()));
        parameters.put("CD_TIPSIT", globalVarVO.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
        parameters.put("CD_PLAN", rt.getCampos()[0].getValor());
        parameters.put("CD_REGION", region);
        parameters.put("CD_PAIS", pais);
        parameters.put("CD_IDIOMA", idioma);
                        
        listaCoberturas = catalogManager.getItemList("OBTIENE_LISTA_COBERTURAS_NO_AGREGADAS", parameters);
        if(listaCoberturas == null) {
            listaCoberturas = new ArrayList<BaseObjectVO>();
        }
        
        if (logger.isDebugEnabled()) {
            logger.debug(":::::.. listaCoberturas : " + listaCoberturas);
        }
        
        /*listaCoberturas = catalogManager.getItemList("OBTIENE_LISTA_COBERTURAS", parameters);
        if(listaCoberturas == null) {
            listaCoberturas = new ArrayList<BaseObjectVO>();
        }
        
        if (logger.isDebugEnabled()) {
            logger.debug(":::::.. listaCoberturas : " + listaCoberturas);
        }*/
        success = true;
        return SUCCESS;
    }
    
    public List<BaseObjectVO> coberturaPolizaList() throws Exception{        
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        //se obtienen los datos
        if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        
        Campo[] campos = new Campo[1];
        campos[0] = new Campo("CDPLAN", "");
        ResultadoTransaccion rt = ExceptionManager.manage(kernelManager.obtenerDatosBloque(idSesion, 
                ConstantsKernel.BLOQUE_B5, campos));
        if (rt != null) {
            logger.debug("CDPLAN= " + rt.getCampos()[0].getValor());
        }
        
        parameters.put("CD_UNIECO", globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica()));
        parameters.put("CD_RAMO", globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo()));
        parameters.put("CD_ESTADO", globalVarVO.getValueVariableGlobal(VariableKernel.Estado()));
        parameters.put("CD_NMPOLIZA", globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza()));
        parameters.put("CD_NMSITUAC", globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSituacion()));
        parameters.put("CD_TIPSIT", globalVarVO.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion()));
        
        List<BaseObjectVO> listaCoberturasPoliza = catalogManager.getItemList(
                "OBTIENE_LISTA_COBERTURAS_NO_AGREGADAS", parameters);
        if(listaCoberturasPoliza == null) {
            listaCoberturasPoliza = new ArrayList<BaseObjectVO>();
        }
        
        if (logger.isDebugEnabled()) {
            logger.debug(":::::.. listaCoberturasPoliza : " + listaCoberturasPoliza);
        }
        //success = true;
        //return SUCCESS;
        return listaCoberturasPoliza;
    }
    
    /**
     * Consulta de detalles de Coberturas
     * @return SUCCESS
     * @author Alejandro Garcia
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public String obtenPantallaCoberturas() throws Exception{
        status = "V";
        
        if (logger.isDebugEnabled()){
            logger.debug("----> obtenPantallaCoberturas");
            logger.debug("cdUnieco          :"+cdUnieco);
            logger.debug("cdRamo            :"+cdRamo);
            logger.debug("estado            :"+estado);
            logger.debug("nmPoliza          :"+nmPoliza);
            logger.debug("nmSituac          :"+nmSituac);
            logger.debug("cdGarant          :"+cdGarant);
            logger.debug("sumaAsegurada     :"+sumaAsegurada);
            logger.debug("dsDeducible       :"+dsDeducible);
            logger.debug("status            :"+status);
        }

        Map <String,String> param = new HashMap<String,String>();
        param.put("cdunieco", cdUnieco);
        param.put("cdramo", cdRamo);
        param.put("estado", estado);
        param.put("nmpoliza", nmPoliza);
        param.put("nmsituac", nmSituac);
        param.put("cdgarant", cdGarant);
        param.put("cdatribu", "");
        param.put("status", status); 
        
        extElements = obtenListCoberturasExtElements(param, dsDeducible);
        
        if (extElements == null || extElements.isEmpty()) {
            TextFieldControl tfHidden = new TextFieldControl();
            tfHidden.setName("noFields");
            tfHidden.setId("noFields");
            tfHidden.setWidth(0);
            tfHidden.setHidden(true);
            tfHidden.setAllowBlank(true);
            
            if (extElements == null) {
                extElements = new ArrayList<ExtElement>();
            }
            
            extElements.add(tfHidden);
        }
        
        if (logger.isDebugEnabled()){
            logger.debug(">>> extElements :" + extElements);
        }
        
        success = true;     
        return SUCCESS;
    }    

    /**
     * @param param
     * @param dsDeducible 
     * @return
     * @throws Exception 
     */
    private List<ExtElement> obtenListCoberturasExtElements(Map<String, String> param, 
            String dsDeducible) throws Exception {
        if (logger.isDebugEnabled()){
            logger.debug("----> obtenListCoberturasExtElements");
            logger.debug("cdUnieco     :" + param.get("cdunieco"));
            logger.debug("cdRamo       :" + param.get("cdramo"));
            logger.debug("estado       :" + param.get("estado"));
            logger.debug("nmPoliza     :" + param.get("nmpoliza"));
            logger.debug("nmSituac     :" + param.get("nmsituac"));
            logger.debug("cdGarant     :" + param.get("cdgarant"));
            logger.debug("status       :" + param.get("status"));
            logger.debug("dsDeducible  :" + dsDeducible);            
        }
        
        List<ExtElement> listExtElements = null;
        List<ExtElement> extDatosElements = null;
        
        try{            
            listExtElements = endosoManager.getExtElements(param, "ENDOSOS_OBTIENE_DATOS_COBERTURAS");
            logger.debug(":::: listExtElements : " + listExtElements);
            
            List<ComboControl> comboControlElements = endosoManager.getComboControl(param, "ENDOSOS_OBTIENE_COMBOS");
            logger.debug(":::: comboControlElements : " + comboControlElements);
            
            //if (comboControlElements != null && !comboControlElements.isEmpty()) {
                ComboBoxlUtils comboUtils = new ComboBoxlUtils();
                //TODO se cambiará a singleton o static..o inyeccion via spring :O
                List<SimpleCombo> storeElements = comboUtils.getDefaultSimpleComboList(comboControlElements, 
                        ServletActionContext.getRequest().getContextPath() + 
                        Constantes.URL_ACTION_COMBOS);
                logger.debug(":::: storeElements : " + storeElements);
                
                //if (storeElements != null && !storeElements.isEmpty()) {
                    SimpleCombo simpleCombo = null;
                    TextFieldControl textField = null;
                    String descripcionDsDeducible = null;
                    String id = null;
                    
                    for (ExtElement element : listExtElements) {
                        if (extDatosElements == null) {
                            extDatosElements = new ArrayList<ExtElement>();
                        }
                        
                        if (element instanceof SimpleCombo) {
                            simpleCombo = (SimpleCombo) element;
                            id = simpleCombo.getId();
                            
                            for (SimpleCombo scombo : storeElements) {
                            
                                if (DEDUCIBLE.equals(scombo.getId())) {
                                    descripcionDsDeducible = obtenValorAtributo(
                                            StringUtils.replace(simpleCombo.getStore(), "store", ""), 
                                                dsDeducible);
                                    logger.debug(":: descripcionAtributo -> " + descripcionDsDeducible);
                                        
                                    scombo.setValue(descripcionDsDeducible);
                                    scombo.setHiddenValue(dsDeducible);                                    
                                }
                                
                                if (!scombo.isHidden()) {
                                    extDatosElements.add(scombo);
                                }
                            }
                        } else if (element instanceof TextFieldControl) {
                            textField = (TextFieldControl) element;  
                            
                            if (!textField.isHidden()) {
                                extDatosElements.add(textField);
                            }
                        } 
                    }
                //}
            //}            
        }catch(ApplicationException ex){
            if (logger.isDebugEnabled()) {
                logger.debug("obtenPantallaCoberturas EXCEPTION:: " + ex);
            }
        }
        
        logger.debug(":::: extDatosElements final : " + extDatosElements);
        return extDatosElements;
    }

    /**
     * @param param
     * @param valoresXDefectoMap 
     * @return
     * @throws Exception 
     */
    private List<ExtElement> obtenListCoberturasExtElements(Map<String, String> param, 
            Map<String, ValoresXDefectoCoberturaVO> valoresXDefectoMap) throws Exception {
        if (logger.isDebugEnabled()){
            logger.debug("----> obtenListCoberturasExtElements");
            logger.debug("cdUnieco     :" + param.get("cdunieco"));
            logger.debug("cdRamo       :" + param.get("cdramo"));
            logger.debug("estado       :" + param.get("estado"));
            logger.debug("nmPoliza     :" + param.get("nmpoliza"));
            logger.debug("nmSituac     :" + param.get("nmsituac"));
            logger.debug("cdGarant     :" + param.get("cdgarant"));
            logger.debug("status       :" + param.get("status"));
            logger.debug("valoresXDefectoMap       :" + valoresXDefectoMap);            
        }
        
        List<ExtElement> listExtElements = null;
        List<ExtElement> extDatosElements = null;
        
        try{            
            listExtElements = endosoManager.getExtElements(param, "ENDOSOS_OBTIENE_DATOS_COBERTURAS");
            logger.debug(":::: listExtElements : " + listExtElements);
            
            List<ComboControl> comboControlElements = endosoManager.getComboControl(param, "ENDOSOS_OBTIENE_COMBOS");
            logger.debug(":::: comboControlElements : " + comboControlElements);
            
            //if (comboControlElements != null && !comboControlElements.isEmpty()) {
                ComboBoxlUtils comboUtils = new ComboBoxlUtils();
                //TODO se cambiará a singleton o static..o inyeccion via spring :O
                List<SimpleCombo> storeElements = comboUtils.getDefaultSimpleComboList(comboControlElements, 
                        ServletActionContext.getRequest().getContextPath() + 
                        "/flujocotizacion/obtenerListaComboOttabval.action");
                logger.debug(":::: storeElements : " + storeElements);
                
                //if (storeElements != null && !storeElements.isEmpty()) {
                    int index = 0;
                    SimpleCombo simpleCombo = null;
                    TextFieldControl textField = null;
                    StringTokenizer st = null;
                    String cve = null;
                    ValoresXDefectoCoberturaVO valoresXDefectoCoberturaVO = null;
                    String valorAtributo = null;
                    String descripcionAtributo = null;
                    String id = null;
                    
                    for (ExtElement element : listExtElements) {
                        if (extDatosElements == null) {
                            extDatosElements = new ArrayList<ExtElement>();
                        }
                        
                        if (element instanceof SimpleCombo) {
                            simpleCombo = (SimpleCombo) element;
                            id = simpleCombo.getId();
                            
                            if (storeElements != null && !storeElements.isEmpty()) {
                                for (SimpleCombo scombo : storeElements) {
                                
                                    if (id.equals(scombo.getId())) {
                                        st = new StringTokenizer(id, "_");
                                        st.nextToken();
                                        cve = st.nextToken();
                                        logger.debug("::::: cve Atributo -> " + cve);
                                        if (valoresXDefectoMap != null) {
                                            valoresXDefectoCoberturaVO = valoresXDefectoMap.get(cve);
                                            valorAtributo = valoresXDefectoCoberturaVO.getValorCob();
                                        
                                            logger.debug("::::: valorAtributo -> " + valorAtributo);
                                            descripcionAtributo = obtenValorAtributo(
                                                    StringUtils.replace(simpleCombo.getStore(), "store", ""), 
                                                    valorAtributo);
                                            logger.debug("::::: descripcionAtributo -> " + descripcionAtributo);
                                            
                                            scombo.setValue(descripcionAtributo);
                                            scombo.setHiddenValue(valorAtributo);
                                        }                                    
                                        //simpleCombo.setStore(modificaStore(storeElements.get(index).toString()));
                                        
                                        //index++;
                                    }
                                    if (!scombo.isHidden()) {
                                        extDatosElements.add(scombo);
                                    }
                                }
                            }
                        } else if (element instanceof TextFieldControl) {
                            textField = (TextFieldControl) element;   
                            
                            /////////////////////
                            id = textField.getId();
                            st = new StringTokenizer(id, "_");
                            st.nextToken();
                            cve = st.nextToken();
                            logger.debug("::::: cve Atributo -> " + cve);
                            if (valoresXDefectoMap != null) {
                                valoresXDefectoCoberturaVO = valoresXDefectoMap.get(cve);
                                valorAtributo = valoresXDefectoCoberturaVO.getValorCob();
                            
                                logger.debug("::::: valorAtributo -> " + valorAtributo);
                            }
                            ////////////////////

                            textField.setValue(valorAtributo);
                            //textField.setName(StringUtils.replace(textField.getName(), "+", "p"));
                            //textField.setId(id);
                            
                            if (!textField.isHidden()) {
                                extDatosElements.add(textField);
                            }
                        } 
                    }
                //}
            //}            
        }catch(ApplicationException ex){
            if (logger.isDebugEnabled()) {
                logger.debug("obtenPantallaCoberturas EXCEPTION:: " + ex);
            }
        }
        
        logger.debug(":::: extDatosElements final : " + extDatosElements);
        return extDatosElements;
    }
    
    /**
     * @return the cdGarant
     */
    public String getCdGarant() {
        return cdGarant;
    }

    /**
     * @param cdGarant the cdGarant to set
     */
    public void setCdGarant(String cdGarant) {
        this.cdGarant = cdGarant;
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
     * @return the cobertura
     */
    public String getCobertura() {
        return cobertura;
    }

    /**
     * @param cobertura the cobertura to set
     */
    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    /**
     * @return the codigoCoberturas
     */
    public String getCodigoCoberturas() {
        return codigoCoberturas;
    }

    /**
     * @param codigoCoberturas the codigoCoberturas to set
     */
    public void setCodigoCoberturas(String codigoCoberturas) {
        this.codigoCoberturas = codigoCoberturas;
    }

    /**
     * @return the descripcionCoberturas
     */
    public String getDescripcionCoberturas() {
        return descripcionCoberturas;
    }

    /**
     * @param descripcionCoberturas the descripcionCoberturas to set
     */
    public void setDescripcionCoberturas(String descripcionCoberturas) {
        this.descripcionCoberturas = descripcionCoberturas;
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
     * @return the dsSumaAsegurada
     */
    public String getDsSumaAsegurada() {
        return dsSumaAsegurada;
    }

    /**
     * @param dsSumaAsegurada the dsSumaAsegurada to set
     */
    public void setDsSumaAsegurada(String dsSumaAsegurada) {
        this.dsSumaAsegurada = dsSumaAsegurada;
    }

    /**
     * @return the extElements
     */
    public List<ExtElement> getExtElements() {
        return extElements;
    }

    /**
     * @param extElements the extElements to set
     */
    public void setExtElements(List<ExtElement> extElements) {
        this.extElements = extElements;
    }

    /**
     * @return the extElementsAgregar
     */
    public List<ExtElement> getExtElementsAgregar() {
        return extElementsAgregar;
    }

    /**
     * @param extElementsAgregar the extElementsAgregar to set
     */
    public void setExtElementsAgregar(List<ExtElement> extElementsAgregar) {
        this.extElementsAgregar = extElementsAgregar;
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
     * @return the hcobertura
     */
    public String getHcobertura() {
        return hcobertura;
    }

    /**
     * @param hcobertura the hcobertura to set
     */
    public void setHcobertura(String hcobertura) {
        this.hcobertura = hcobertura;
    }

    /**
     * @return the importe
     */
    public String getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(String importe) {
        this.importe = importe;
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
     * @return the listaCoberturas
     */
    public List<BaseObjectVO> getListaCoberturas() {
        return listaCoberturas;
    }

    /**
     * @param listaCoberturas the listaCoberturas to set
     */
    public void setListaCoberturas(List<BaseObjectVO> listaCoberturas) {
        this.listaCoberturas = listaCoberturas;
    }

    /**
     * @return the listCoberturasIncisos
     */
    public List<IncisosVO> getListCoberturasIncisos() {
        return listCoberturasIncisos;
    }

    /**
     * @param listCoberturasIncisos the listCoberturasIncisos to set
     */
    public void setListCoberturasIncisos(List<IncisosVO> listCoberturasIncisos) {
        this.listCoberturasIncisos = listCoberturasIncisos;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the sumaAsegurada
     */
    public String getSumaAsegurada() {
        return sumaAsegurada;
    }

    /**
     * @param sumaAsegurada the sumaAsegurada to set
     */
    public void setSumaAsegurada(String sumaAsegurada) {
        this.sumaAsegurada = sumaAsegurada;
    }

    /**
     * @return the validoElementsAgregar
     */
    public boolean isValidoElementsAgregar() {
        return validoElementsAgregar;
    }

    /**
     * @param validoElementsAgregar the validoElementsAgregar to set
     */
    public void setValidoElementsAgregar(boolean validoElementsAgregar) {
        this.validoElementsAgregar = validoElementsAgregar;
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
     * @return the dsGarant
     */
    public String getDsGarant() {
        return dsGarant;
    }

    /**
     * @param dsGarant the dsGarant to set
     */
    public void setDsGarant(String dsGarant) {
        this.dsGarant = dsGarant;
    }

    /**
     * @return the dsDeducible
     */
    public String getDsDeducible() {
        return dsDeducible;
    }

    /**
     * @param dsDeducible the dsDeducible to set
     */
    public void setDsDeducible(String dsDeducible) {
        this.dsDeducible = dsDeducible;
    }

    /**
     * @return the agregarCoberturas
     */
    public boolean isAgregarCoberturas() {
        return agregarCoberturas;
    }

    /**
     * @param agregarCoberturas the agregarCoberturas to set
     */
    public void setAgregarCoberturas(boolean agregarCoberturas) {
        this.agregarCoberturas = agregarCoberturas;
    }

}
