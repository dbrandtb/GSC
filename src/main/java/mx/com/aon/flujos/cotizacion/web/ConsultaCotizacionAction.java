package mx.com.aon.flujos.cotizacion.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.flujos.cotizacion.model.CoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.ConsultaCotizacionVO;
import mx.com.aon.flujos.cotizacion.service.CotizacionPrincipalManager;
import mx.com.aon.flujos.cotizacion.service.CotizacionService;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.CotizacionMasivaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.PagedList;

import org.apache.log4j.Logger;

import com.biosnet.ice.ext.elements.form.TextFieldControl;


public class ConsultaCotizacionAction extends PrincipalCotizacionAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8294621803111738391L;
	protected final transient Logger log = Logger.getLogger(ConsultaCotizacionAction.class);
    
	private boolean success;
	protected int totalCount;
    protected int start = 0;
    protected int limit = 20;
	private CotizacionService cotizacionManager;
	private CatalogService catalogosManager;
	//private ServiciosGeneralesSistema serviciosGeneralesSistema;
	
	private List<ConsultaCotizacionVO> listaConsultaCotizacion;
	
	private List<CotizacionMasivaVO> listaConsultaTvalositCotiza;
	private List<BaseObjectVO> listaAseguradoras;
	private String codigoCotizacionForm;
	private String codigoAseguradoraForm;
	private String descripcionAseguradoraForm;	
	private List<BaseObjectVO> listaProductos;
	private String codigoProductoForm;
	private String descripcionProductoForm;
	private String cdusuario;
	
	private String cdunieco;
	private String dsunieco;
	private String cdramo;
	private String estado;
	private String nmpoliza;
	private String nmsituac;
	private String nmsuplem;
	private String cdplan;
	private String dsplan;
	private String cdcia;
	private String cdFormaPago;
	private String dsFormaPago;
	private String feefecto;
	private String fevencim;
	private String cdtipsit;
	private String prima;
	
	private String dsatribu;
	private String marca;
	private String modelo;
	private String descripciondsatribut;
	
    private List<TextFieldControl> listaDatosExt;
	private List<CoberturaCotizacionVO> listaCoberturas;

	private CotizacionPrincipalManager cotizacionManagerJdbcTemplate;	

	public String execute() throws Exception {
	    obtieneTVALOSIT3();
		return INPUT;
	}
	
	@SuppressWarnings("unchecked")
	public String consultarCotizacion() throws Exception{
		log.debug("entrando a consultarCotizacion");
		log.debug("codigoAseguradoraForm = " + codigoAseguradoraForm);
		log.debug("codigoProductoForm = " + codigoProductoForm);

		UserVO usuario = (UserVO) session.get("USUARIO");
		if(codigoAseguradoraForm != null && codigoAseguradoraForm.equals("Seleccione Aseguradora..."))
			codigoAseguradoraForm = null;
		if(codigoProductoForm != null && codigoProductoForm.equals("Seleccione Producto..."))
			codigoProductoForm = null;

		Map<String,String> params = new HashMap<String, String>();
		params.put("CDUSUARIO", usuario.getUser());
		params.put("ASEGURADORA", codigoAseguradoraForm);
		params.put("PRODUCTO", codigoProductoForm);
		params.put("COTIZACION", codigoCotizacionForm);
		
		try{
    		PagedList pagedList = cotizacionManager.getCotizacion(params, start, limit);
    		listaConsultaCotizacion = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
		} catch(ApplicationException ex) {
			logger.debug("consultaCotizacion EXCEPTION:: " + ex);
		}

		success = true;

		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String obtieneAseguradoras() throws Exception{
		UserVO usuario = (UserVO) session.get("USUARIO");
		listaAseguradoras = catalogosManager.getItemList("OBTIENE_ASEGURADORAS",usuario.getUser());
		if(listaAseguradoras == null)
		listaAseguradoras = new ArrayList<BaseObjectVO>();
		
		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String obtieneProductos() throws Exception{
		//cdusuario = "SROMAN";
		UserVO usuario = (UserVO) session.get("USUARIO");
		listaProductos = catalogosManager.getItemList("OBTIENE_PRODUCTOS_CATALOGO",usuario.getUser());
		if(listaProductos == null)
		listaProductos = new ArrayList<BaseObjectVO>();
		
		success = true;
		return SUCCESS;
	}
	public String borrarCotizaciones() throws Exception{
		log.debug("into ConsultaCotizacionAction.borrarCotizaciones()");	
		log.debug("listaConsultaCotizacion: " + listaConsultaCotizacion);
		if( listaConsultaCotizacion != null && !listaConsultaCotizacion.isEmpty() ){
			//String cdusuari="SROMAN";
			UserVO usuario = (UserVO) session.get("USUARIO");
			cotizacionManager.deleteCotizacionesConsulta(usuario.getUser(), listaConsultaCotizacion);
		}
		success = true;
		return SUCCESS;
	}
	
	public String obtieneTVALOSIT3() throws Exception{
	    
	    log.debug("entrando a obtieneTVALOSIT3()");
	    
	    Map<String,String> params = new HashMap<String, String>();
	    params.put("CDUNIECO", cdunieco);
        params.put("CDRAMO", cdramo);
        params.put("ESTADO", estado);
        params.put("NMPOLIZA", nmpoliza);
        params.put("NMSITUAC", nmsituac);
        params.put("NMSUPLEM", "");
        log.debug("params= " + params);
        listaDatosExt = cotizacionManager.getTVALOSIT3(params);
        
        log.debug("listaDatosExt: " + listaDatosExt);
        
	    success = true;
        return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
    public String obtieneCoberturas() throws Exception{
        
        log.debug("entrando a obtieneCoberturas()");
        
        UserVO usuario = (UserVO) session.get("USUARIO");
//        GlobalVariableContainerVO globalVarVO = new GlobalVariableContainerVO();
        //, numeroSituacion
        //Se sube a sesion la global variable para que se pueda utilizar en flujocotizacion/comprarCotizaciones
//        globalVarVO.addVariableGlobal(VariableKernel.UnidadEconomica(), cdunieco);
//        globalVarVO.addVariableGlobal(VariableKernel.CodigoRamo(), cdramo);
//        globalVarVO.addVariableGlobal(VariableKernel.Estado(), estado);
//        globalVarVO.addVariableGlobal(VariableKernel.NumeroPoliza(), nmpoliza);
//        //TODO: subir a sesion nmsituac sin que afecte al metodo comprarCotizaciones() de ComprarCotizacionAction
//        //globalVarVO.addVariableGlobal(VariableKernel.NumeroSituacion(), nmsituac);
//        session.put("GLOBAL_VARIABLE_CONTAINER", globalVarVO);
        
        Map<String,String> params = new HashMap<String, String>();
        params.put("USUARIO",  usuario.getUser());
        params.put("CDUNIECO", cdunieco);
        params.put("ESTADO",   estado);
        params.put("NMPOLIZA", nmpoliza);
        params.put("NMSITUAC", nmsituac);
        params.put("NMSUPLEM", nmsuplem);        
        params.put("CDPLAN",   cdplan);
        params.put("CDRAMO",   cdramo);
        params.put("CDCIA",    cdcia);
        params.put("REGION",   "ME");   
        params.put("PAIS",     usuario.getPais().getValue());
        params.put("IDIOMA",   usuario.getIdioma().getValue());
        log.debug("cobertura params= " + params);
        listaCoberturas = (List<CoberturaCotizacionVO>) cotizacionManager.getCoberturas(params);
        
        success = true;
        return SUCCESS;
    }

    /**
     * Metodo que redirecciona a la pantalla de resultado cotizacion
     * 
     * @author augusto.perez
     * @return SUCCESS
     */
	@SuppressWarnings("unchecked")
	public String detalleConsultaCotizacion() throws Exception {
        
		logger.debug("###### METODO detalleConsultaCotizacion...");

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put( "cdusuario", getCdusuario() );
        parameters.put( "cdunieco", getCdunieco() );
        parameters.put( "dsunieco", getDsunieco() );
        parameters.put( "cdramo", getCdramo() );
        parameters.put( "estado", getEstado() );
        parameters.put( "nmpoliza", getNmpoliza() );
        parameters.put( "nmsituac", getNmsituac() );
        parameters.put( "nmsuplem", getNmsuplem() );
        parameters.put( "cdcia", getCdcia() );
        parameters.put( "cdtipsit", getCdtipsit() );
////        GlobalVariableContainerVO globalVarVo = new GlobalVariableContainerVO();
//        UserVO usuario = (UserVO) session.get("USUARIO");
//        if ( session.containsKey("GLOBAL_VARIABLE_CONTAINER") ){
//			globalVarVo = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
//        }
//			
//		globalVarVo.addVariableGlobal( VariableKernel.UnidadEconomica(), getCdunieco() );
//		globalVarVo.addVariableGlobal( VariableKernel.CodigoRamo(), getCdramo() );
//		globalVarVo.addVariableGlobal( VariableKernel.Estado(), getEstado() );
//		globalVarVo.addVariableGlobal( VariableKernel.NumeroSituacion(), getNmsituac() );
//		globalVarVo.addVariableGlobal( VariableKernel.NumeroPoliza(), getNmpoliza() );
//		globalVarVo.addVariableGlobal( VariableKernel.CodigoTipoSituacion(), getCdtipsit() );
//		globalVarVo.addVariableGlobal( VariableKernel.UsuarioBD(), usuario.getUser());
//		globalVarVo.addVariableGlobal( VariableKernel.NumeroSuplemento(), "0");
//		session.put("GLOBAL_VARIABLE_CONTAINER", globalVarVo);
        
        
        /* 
         * Se crea el Map DETALLE_COTIZACION para incluir los datos de la cotización
         * guardada y recuperarlos en ResultadoCotizacionAction.java - Method: entrar()
         */
        session.put( "DETALLE_COTIZACION", parameters );
        if ( session.containsKey( "COTIZAR_ACTION" ) ) {
        	session.remove( "COTIZAR_ACTION" );
        	logger.debug("!!! Se elimino de session COTIZAR_ACTION. Existe? =" + session.containsKey( "COTIZAR_ACTION" ) );
        }

        success = true;
        return SUCCESS;
	}
	
	// metodos hace uso de JdbcTemplate  
	// metodo para la obtencion de la marca,modelo,descripcion 

	public String obtieneTvalositCotiza() throws Exception{
		   
	    log.debug("entrando a obtieneTvalositCotiza()");
        String  prmCdunieco = cdunieco;
        String  prmPoliza   = nmpoliza;
        String  prmCdramo   = cdramo;
        String  prmEstado   = estado;
        prmEstado = prmEstado.toUpperCase();
     
        setListaConsultaTvalositCotiza(obtenCotizacionManagerJdbcTemplate().obtineTvalositCotiza(prmCdunieco,prmPoliza,prmCdramo,prmEstado));
        
        if(getListaConsultaTvalositCotiza() == null ){
        	  setListaConsultaTvalositCotiza(new ArrayList<CotizacionMasivaVO>());
		
        }     
		if(getListaConsultaTvalositCotiza() != null && !getListaConsultaTvalositCotiza().isEmpty()){
		
			for( int i=0; i <  listaConsultaTvalositCotiza.size();i++ ){
				CotizacionMasivaVO  cotizacionMasvVO = new CotizacionMasivaVO();     
				cotizacionMasvVO = listaConsultaTvalositCotiza.get(i);   
			
				if( cotizacionMasvVO.getDsatribu().toUpperCase().equals("MARCA")){
					setMarca(cotizacionMasvVO.getDsvalor());
					log.debug("marca=" + getMarca());
				}
				
				if( cotizacionMasvVO.getDsatribu().toUpperCase().equals("MODELO")){
					setModelo(cotizacionMasvVO.getDsvalor());
					log.debug("modelo=" + getModelo() );
				}
				
				if( cotizacionMasvVO.getDsatribu().toUpperCase().equals("DESCRIPCION")){
					setDescripciondsatribut(cotizacionMasvVO.getDsvalor());
					log.debug("descripcion="+ getDescripciondsatribut());
				}
			}		
				
			success = true;
		
		}
		return SUCCESS;
	}
	
	//setters managers
	public void setCotizacionManager(CotizacionService cotizacionManager) { this.cotizacionManager = cotizacionManager;	}
	public void setCatalogosManager(CatalogService catalogosManager) { this.catalogosManager = catalogosManager; }
	
	//getters && setters
	public boolean isSuccess() { return success; }
	public void setSuccess(boolean success) { this.success = success; }

	public List<ConsultaCotizacionVO> getListaConsultaCotizacion() { return listaConsultaCotizacion; }
	public void setListaConsultaCotizacion( List<ConsultaCotizacionVO> listaConsultaCotizacion) { this.listaConsultaCotizacion = listaConsultaCotizacion; }

	public List<BaseObjectVO> getListaAseguradoras() { return listaAseguradoras; }
	public void setListaAseguradoras(List<BaseObjectVO> listaAseguradoras) { this.listaAseguradoras = listaAseguradoras; }
	public String getCodigoAseguradoraForm() { return codigoAseguradoraForm; }
	public void setCodigoAseguradoraForm(String codigoAseguradoraForm) { this.codigoAseguradoraForm = codigoAseguradoraForm; }
	public String getDescripcionAseguradoraForm() { return descripcionAseguradoraForm; }
	public void setDescripcionAseguradoraForm(String descripcionAseguradoraForm) { this.descripcionAseguradoraForm = descripcionAseguradoraForm; }
	
	public List<BaseObjectVO> getListaProductos() {	return listaProductos; }
	public void setListaProductos(List<BaseObjectVO> listaProductos) { this.listaProductos = listaProductos; }
	public String getCodigoProductoForm() { return codigoProductoForm; }
	public void setCodigoProductoForm(String codigoProductoForm) { this.codigoProductoForm = codigoProductoForm; }
	public String getDescripcionProductoForm() { return descripcionProductoForm; }
	public void setDescripcionProductoForm(String descripcionProductoForm) { this.descripcionProductoForm = descripcionProductoForm; }

	public String getCdusuario() { return cdusuario; }
	public void setCdusuario(String cdusuario) { this.cdusuario = cdusuario; }
	
    public List<TextFieldControl> getListaDatosExt() { return listaDatosExt; }
    public void setListaDatosExt(List<TextFieldControl> listaDatosExt) { this.listaDatosExt = listaDatosExt; }

    public List<CoberturaCotizacionVO> getListaCoberturas() { return listaCoberturas; }
    public void setListaCoberturas(List<CoberturaCotizacionVO> listaCoberturas) { this.listaCoberturas = listaCoberturas; }

    public String getCdunieco() { return cdunieco; }
    public void setCdunieco(String cdunieco) { this.cdunieco = cdunieco; }
    public String getCdramo() { return cdramo; }
    public void setCdramo(String cdramo) { this.cdramo = cdramo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getNmpoliza() { return nmpoliza; }
    public void setNmpoliza(String nmpoliza) { this.nmpoliza = nmpoliza; }
    public String getNmsituac() { return nmsituac; }
    public void setNmsituac(String nmsituac) { this.nmsituac = nmsituac; }
    public String getNmsuplem() { return nmsuplem; }
    public void setNmsuplem(String nmsuplem) { this.nmsuplem = nmsuplem; }
    public String getCdplan() { return cdplan; }
    public void setCdplan(String cdplan) { this.cdplan = cdplan; }
    public String getCdcia() { return cdcia; }
    public void setCdcia(String cdcia) { this.cdcia = cdcia; }

	public void setCotizacionManagerJdbcTemplate(
			CotizacionPrincipalManager cotizacionManagerJdbcTemplate) {
		this.cotizacionManagerJdbcTemplate = cotizacionManagerJdbcTemplate;
	}

	public  CotizacionPrincipalManager obtenCotizacionManagerJdbcTemplate() {
		return cotizacionManagerJdbcTemplate;
	}

	public void setListaConsultaTvalositCotiza(
			List<CotizacionMasivaVO> listaConsultaTvalositCotiza) {
		this.listaConsultaTvalositCotiza = listaConsultaTvalositCotiza;
	}

	public List<CotizacionMasivaVO> getListaConsultaTvalositCotiza() {
		return listaConsultaTvalositCotiza;
	}

	public void setDsatribu(String dsatribu) {
		this.dsatribu = dsatribu;
	}

	public String getDsatribu() {
		return dsatribu;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getMarca() {
		return marca;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setDescripciondsatribut(String descripciondsatribut) {
		this.descripciondsatribut = descripciondsatribut;
	}

	public String getDescripciondsatribut() {
		return descripciondsatribut;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getDsunieco() {
		return dsunieco;
	}

	public void setDsunieco(String dsunieco) {
		this.dsunieco = dsunieco;
	}

	public String getDsplan() {
		return dsplan;
	}

	public void setDsplan(String dsplan) {
		this.dsplan = dsplan;
	}

	public String getCdFormaPago() {
		return cdFormaPago;
	}

	public void setCdFormaPago(String cdFormaPago) {
		this.cdFormaPago = cdFormaPago;
	}

	public String getDsFormaPago() {
		return dsFormaPago;
	}

	public void setDsFormaPago(String dsFormaPago) {
		this.dsFormaPago = dsFormaPago;
	}

	public String getFeefecto() {
		return feefecto;
	}

	public void setFeefecto(String feefecto) {
		this.feefecto = feefecto;
	}

	public String getFevencim() {
		return fevencim;
	}

	public void setFevencim(String fevencim) {
		this.fevencim = fevencim;
	}

	public String getCdtipsit() {
		return cdtipsit;
	}

	public void setCdtipsit(String cdtipsit) {
		this.cdtipsit = cdtipsit;
	}

	public String getPrima() {
		return prima;
	}

	public void setPrima(String prima) {
		this.prima = prima;
	}

//	public ServiciosGeneralesSistema getServiciosGeneralesSistema() {
//		return serviciosGeneralesSistema;
//	}
//
//	public void setServiciosGeneralesSistema(
//			ServiciosGeneralesSistema serviciosGeneralesSistema) {
//		this.serviciosGeneralesSistema = serviciosGeneralesSistema;
//	}

	public String getCodigoCotizacionForm() {
		return codigoCotizacionForm;
	}

	public void setCodigoCotizacionForm(String codigoCotizacionForm) {
		this.codigoCotizacionForm = codigoCotizacionForm;
	}
}

