/**
 * 
 */
package mx.com.aon.flujos.endoso.web;

import java.util.HashMap;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.flujos.endoso.service.EndosoManager;
import mx.com.aon.kernel.service.KernelManager;
import mx.com.aon.portal.service.CatalogService;
import mx.com.ice.kernel.constants.ConstantsKernel;
import mx.com.ice.services.exception.manager.ExceptionManager;
import mx.com.ice.services.to.Campo;
import mx.com.ice.services.to.ResultadoTransaccion;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;


/**
 * Clase Action padre de endoso
 * 
 * @author aurora.lozada
 * 
 */
public class PrincipalEndosoAction extends ActionSupport implements  SessionAware, Preparable {

    /**
     * 
     */
    private static final long serialVersionUID = 6935792701037483712L;
    
    protected static final String FECHA_EFECTIVIDAD = "E";

    protected String cdUnieco;
    protected String cdRamo;
    protected String nmPoliza;
    protected String nmSituac;
    protected String estado;
    protected String nmSuplem;
    
    
    /**
     * 
     */
    @SuppressWarnings("unchecked")
    protected Map session;
    
    protected final transient Logger logger = Logger.getLogger(PrincipalEndosoAction.class);
    
    protected CatalogService catalogManager;

    protected EndosoManager endosoManager;
    
    /**
     * Servicio inyectado por spring para llamar a los servicios del kernel
     */
    protected KernelManager kernelManager;
    
    
    protected int totalCount;
    protected int start = 0;
    protected int limit = 20;
    
    protected GlobalVariableContainerVO globalVarVO;
    
    /**
     * Método que actualiza el valor de 'clicBotonRegresar' 
     * del mapa de session con llave 'PARAMETROS_REGRESAR'
     * @param void 
     * @return void 
     */
    @SuppressWarnings("unchecked")
    public void updateParametrosRegresar(){
		if ( session.containsKey("PARAMETROS_REGRESAR") ) {
			Map<String,String> pr = (Map<String,String>)session.get("PARAMETROS_REGRESAR");
			pr.put("clicBotonRegresar", "N");
			session.put("PARAMETROS_REGRESAR", pr);
		}
		return;
    }
    
    /**
     * @return the globalVarVO
     */
    public GlobalVariableContainerVO getGlobalVarVO() {
        return globalVarVO;
    }

    /**
     * @param globalVarVO the globalVarVO to set
     */
    public void setGlobalVarVO(GlobalVariableContainerVO globalVarVO) {
        this.globalVarVO = globalVarVO;
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * 
     */
    public void prepare() throws Exception {
        
    }
    
    /*protected List<BaseObjectVO> domicilioList() throws Exception{
        UserVO usuario = (UserVO) session.get("USUARIO");
        listaDomicilio = catalogManager.getItemList("OBTIENE_DOMICILIO_PERSONA",usuario.getCodigoPersona());
        logger.debug("::::::::: listaDomicilio ::::::::  " + listaDomicilio);
        return listaDomicilio;
    }
    
    protected List<BaseObjectVO> intrumentoPagoList() throws Exception{

        listaIntrumentoPago = catalogManager.getItemList("OBTIENE_INTRUMETOS_PAGO");
        logger.debug("::::::::: listaIntrumentoPago ::::::::  " + listaIntrumentoPago);
        return listaIntrumentoPago;
    }
    
    protected List<BaseObjectVO> bancoList() throws Exception{

        listaBancos = catalogManager.getItemList("OBTIENE_BANCOS");
        logger.debug("::::::::: listaBancos ::::::::  " + listaBancos);
        return listaBancos;
    }
    
    protected List<BaseObjectVO> sucursalList() throws Exception{
        
        listaSucursal = catalogManager.getItemList("OBTIENE_BANCOS_SUCURSAL", codigoBanco);
        logger.debug("::::::::: listaSucursal ::::::::  " + listaSucursal);
        return listaSucursal;
    }
    
    protected List<BaseObjectVO> personasUsuarioList() throws Exception{
        UserVO usuario = (UserVO) session.get("USUARIO");
        listaPersonasUsuario = catalogManager.getItemList("OBTIENE_PERSONA_USUARIO", usuario.getUser());
        logger.debug("::::::::: listaPersonasUsuario ::::::::  " + listaPersonasUsuario);
        return listaPersonasUsuario;
    }
    
    protected List<BaseObjectVO> personasUsuarioMultiSelectList() throws Exception{
        UserVO usuario = (UserVO) session.get("USUARIO");
        listaPersonasUsuarioMultiSelect = catalogManager.getItemList("OBTIENE_PERSONA_USUARIO", usuario.getUser());
        logger.debug("::::::::: listaPersonasUsuarioMultiSelect ::::::::  " + listaPersonasUsuarioMultiSelect);
        return listaPersonasUsuarioMultiSelect;
    }*/
    

    /**
     * @return
     * @throws ApplicationException 
     * @throws mx.com.ice.services.exception.ApplicationException 
     */
    protected String obtieneNmSituac() throws mx.com.ice.services.exception.ApplicationException, 
            ApplicationException {
        if (logger.isDebugEnabled()){
            logger.debug("---> obtieneNmSituac");
        }
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        Campo[] campos = new Campo[1];
        campos[0] = new Campo("NMSITUAC", "");
        ResultadoTransaccion rt = ExceptionManager.manage(kernelManager.obtenerDatosBloque(idSesion, 
                ConstantsKernel.BLOQUE_B5, campos));
        if (rt != null) {
            logger.debug("NMSITUAC= " + rt.getCampos()[0].getValor());
        }
        return rt.getCampos()[0].getValor();
    }

    /**
     * @param string
     * @return
     */
    protected String modificaStore(String store) {
        if (logger.isDebugEnabled()) {
            logger.debug("-> modificaStore");
            logger.debug("::: store :: " + store);
        }
        String storeAdaptado = StringUtils.replace(store, "\"", "'");
        return StringUtils.remove(storeAdaptado, ";");
    }
    
    /**
     * 
     * @param tabla
     * @param clave
     * @return
     * @throws Exception
     */
    protected String obtenValorAtributo(String tabla, String clave) throws Exception{   
        if (logger.isDebugEnabled()) {
            logger.debug("---> getValorAtributo ");
            logger.debug("::::.. tabla : " + tabla);
            logger.debug("::::.. clave : " + clave);
        }
        
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        //tabla = "2DSMVADF";
        parameters.put("TABLA", tabla);
        parameters.put("CLAVE", clave);
        
        String atribCobertura = endosoManager.getVariableAtributoCoberturas(tabla, clave);
        
        if (logger.isDebugEnabled()) {
            logger.debug(":::::.. atribCobertura : " + atribCobertura);
        }
        
        return atribCobertura;
    }
    
    /**
     * 
     * @param tabla
     * @param clave
     * @param entryDataCombo 
     * @return
     * @throws Exception
     */
    protected String obtenValorAtributo(String tabla, String clave, Map<String, String> entryDataCombo) throws Exception{   
        if (logger.isDebugEnabled()) {
            logger.debug("---> getValorAtributo ");
            logger.debug("::::.. tabla : " + tabla);
            logger.debug("::::.. clave : " + clave);
        }
        
        //////////////////////////////
        entryDataCombo.put(tabla, clave);
        /////////////////////////////////////////////////////
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        //tabla = "2DSMVADF";
        parameters.put("TABLA", tabla);
        parameters.put("CLAVE", clave);
        
        String atribCobertura = endosoManager.getVariableAtributoCoberturas(tabla, clave);
        
        if (logger.isDebugEnabled()) {
            logger.debug(":::::.. atribCobertura : " + atribCobertura);
        }
        
        return atribCobertura;
    }

    /**
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * @return the session
     */
    @SuppressWarnings("unchecked")
    public Map getSession() {
        return session;
    }

    /**
     * @param catalogManager the catalogManager to set
     */
    public void setCatalogManager(CatalogService catalogManager) {
        this.catalogManager = catalogManager;
    }

	public void setKernelManager(KernelManager kernelManager) {
		this.kernelManager = kernelManager;
	}

	public String getCdUnieco() {
		return cdUnieco;
	}

	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getNmPoliza() {
		return nmPoliza;
	}

	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}

	public String getNmSituac() {
		return nmSituac;
	}

	public void setNmSituac(String nmSituac) {
		this.nmSituac = nmSituac;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

    /**
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @param endosoManager the endosoManager to set
     */
    public void setEndosoManager(EndosoManager endosoManager) {
        this.endosoManager = endosoManager;
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
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
    
}
