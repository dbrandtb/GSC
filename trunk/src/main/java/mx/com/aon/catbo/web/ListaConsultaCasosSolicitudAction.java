package mx.com.aon.catbo.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.service.AdministracionCasosManager2;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.ConnectionCallInterceptor;
import mx.com.aon.portal.util.Util;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;
import mx.com.aon.core.VariableKernel;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

public class ListaConsultaCasosSolicitudAction extends AbstractListAction implements SessionAware{
	protected static ThreadLocal<UserVO> localUser = new ThreadLocal<UserVO>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1137878786546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaConsultaCasosSolicitudAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AdministracionCasosManager administracionCasosManager;
	private transient AdministracionCasosManager2 administracionCasosManager2;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo VO
	 * con los valores de la consulta.
	 */
	private List<CasoVO> mEstructuraCasosList;
	
	
	/**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
		
	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
	@SuppressWarnings("unchecked")
	private List modelControl = new ArrayList();
	
	@SuppressWarnings("unchecked")
	private Map session;

	private String pv_nmcaso_i;
	private String pv_cdorden_i;
    private String pv_dsproceso_i;
    private String pv_feingreso_i;
    private String pv_cdpriord_i;
    private String pv_cdperson_i;
    private String pv_dsperson_i;
    private String pv_cdusuario_i;
    private String cdMatriz;
	private String cdFormatoOrdenTrabajo;
	private String cdElemento;
	private String cdProceso;
	private String cdRamo;
	private String cdperson;
	private String wORIGEN;
	
    
    
	public String getCdMatriz() {
		return cdMatriz;
	}


	public void setCdMatriz(String cdMatriz) {
		this.cdMatriz = cdMatriz;
	}


	public String getCdFormatoOrdenTrabajo() {
		return cdFormatoOrdenTrabajo;
	}


	public void setCdFormatoOrdenTrabajo(String cdFormatoOrdenTrabajo) {
		this.cdFormatoOrdenTrabajo = cdFormatoOrdenTrabajo;
	}

	
	public String entrar(){
    	try{
    	UserVO usuario=(UserVO) session.get("USUARIO");
    	cdElemento=usuario.getEmpresa().getElementoId();
    	
    	if(StringUtils.isNotBlank(cdProceso)){
	    	GlobalVariableContainerVO globalVarVo = new GlobalVariableContainerVO();
	        globalVarVo = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
	        cdRamo = globalVarVo.getValueVariableGlobal(VariableKernel.CodigoRamo());
    	}
    	
    	
    	if(logger.isDebugEnabled()){
    		logger.debug("Seteando los valores para el combo de Aseguradoras relacionadas con el Producto y el Corporativo.");
    		logger.debug("cdProceso: "+cdProceso);
    		logger.debug("cdElemento: "+cdElemento);
    		logger.debug("cdRamo: "+cdRamo);
    	}
    	
    	}catch(Exception e){
    		logger.debug("No se pudieron fijar los parametros al llamar a Orden de solicitud desde cotizacion");
    		
    	}
    	
    	return INPUT;
    }

	/**
	 * Metodo que realiza una búsqueda en base a criterios de búsquedas.
	 * Pantalla CatBo_ConsultarCasos
	 *  
	 * @param 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String btnBuscarCasosClick() throws Exception{
		try{
			logger.debug("entrando al metodo BuscarCasos");
			UserVO userVO = new UserVO();
			ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
            userVO = tl.get();
			
            String cdUsuario = null;
			if(userVO != null && userVO.getUser()!= null){
				logger.debug("seteando en la busqueda del caso con el usuario logueado al sistema: " + userVO.getUser());
				cdUsuario = userVO.getUser();
			}
            PagedList pagedList = administracionCasosManager2.obtenerCasos(pv_nmcaso_i, pv_cdorden_i, pv_dsproceso_i, pv_feingreso_i, pv_cdpriord_i, pv_cdperson_i, pv_dsperson_i, cdUsuario,start, limit);
            mEstructuraCasosList = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();                                                    
            success = true;
            return SUCCESS;            
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	
	/**
	 * Metodo que exporta el listado de la grilla a un Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarListadoCasosClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
            UserVO userVO = (UserVO) session.get("contextUserVO");

			String cdUsuario = null;
			if(userVO != null && userVO.getUser()!= null){
				cdUsuario = userVO.getUser();
			}
			  contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato);
			filename = "Consulta de Casos Solicitudes." + exportFormat.getExtension();
			TableModelExport model =  administracionCasosManager.getModelCasos(pv_nmcaso_i, pv_cdorden_i, pv_dsproceso_i, pv_feingreso_i, pv_cdpriord_i, pv_cdperson_i, pv_dsperson_i, "");		
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}


	/**
	 * Metodo que exporta el listado de la grilla a un Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarListadoCasosClick2() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
            /*UserVO userVO = (UserVO) session.get("contextUserVO");

			String cdUsuario = null;
			if(userVO != null && userVO.getUser()!= null){
				cdUsuario = userVO.getUser();
			}*/
			
			UserVO userVO = new UserVO();
			ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
            userVO = tl.get();
			
            String cdUsuario = null;
			if(userVO != null && userVO.getUser()!= null){
				logger.debug("seteando en la busqueda del caso con el usuario logueado al sistema: " + userVO.getUser());
				cdUsuario = userVO.getUser();
			}
			
			  contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato);
			filename = "Consulta de Casos Solicitudes." + exportFormat.getExtension();
			TableModelExport model =  administracionCasosManager2.getModelObtenerCasos(pv_nmcaso_i, pv_cdorden_i, pv_dsproceso_i, pv_feingreso_i, pv_cdpriord_i, pv_cdperson_i, pv_dsperson_i, cdUsuario);		
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}	
	
	
	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public ExportMediator getExportMediator() {
		return exportMediator;
	}

	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}


	public List getModelControl() {
		return modelControl;
	}


	public void setModelControl(List modelControl) {
		this.modelControl = modelControl;
	}


	public Map getSession() {
		return session;
	}


	public void setSession(Map session) {
		this.session = session;
	}


	public void setAdministracionCasosManager(
			AdministracionCasosManager administracionCasosManager) {
		this.administracionCasosManager = administracionCasosManager;
	}


	


	public void setAdministracionCasosManager2(
			AdministracionCasosManager2 administracionCasosManager2) {
		this.administracionCasosManager2 = administracionCasosManager2;
	}


	public List<CasoVO> getMEstructuraCasosList() {
		return mEstructuraCasosList;
	}


	public void setMEstructuraCasosList(List<CasoVO> estructuraCasosList) {
		mEstructuraCasosList = estructuraCasosList;
	}


	public String getPv_nmcaso_i() {
		return pv_nmcaso_i;
	}


	public void setPv_nmcaso_i(String pv_nmcaso_i) {
		this.pv_nmcaso_i = pv_nmcaso_i;
	}


	public String getPv_cdorden_i() {
		return pv_cdorden_i;
	}


	public void setPv_cdorden_i(String pv_cdorden_i) {
		this.pv_cdorden_i = pv_cdorden_i;
	}


	public String getPv_dsproceso_i() {
		return pv_dsproceso_i;
	}


	public void setPv_dsproceso_i(String pv_dsproceso_i) {
		this.pv_dsproceso_i = pv_dsproceso_i;
	}


	public String getPv_feingreso_i() {
		return pv_feingreso_i;
	}


	public void setPv_feingreso_i(String pv_feingreso_i) {
		this.pv_feingreso_i = pv_feingreso_i;
	}


	public String getPv_cdpriord_i() {
		return pv_cdpriord_i;
	}


	public void setPv_cdpriord_i(String pv_cdpriord_i) {
		this.pv_cdpriord_i = pv_cdpriord_i;
	}


	public String getPv_cdperson_i() {
		return pv_cdperson_i;
	}


	public void setPv_cdperson_i(String pv_cdperson_i) {
		this.pv_cdperson_i = pv_cdperson_i;
	}


	public String getPv_dsperson_i() {
		return pv_dsperson_i;
	}


	public void setPv_dsperson_i(String pv_dsperson_i) {
		this.pv_dsperson_i = pv_dsperson_i;
	}


	public String getPv_cdusuario_i() {
		return pv_cdusuario_i;
	}


	public void setPv_cdusuario_i(String pv_cdusuario_i) {
		this.pv_cdusuario_i = pv_cdusuario_i;
	}


	public String getCdElemento() {
		return cdElemento;
	}


	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}


	public String getCdProceso() {
		return cdProceso;
	}


	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}


	public String getCdRamo() {
		return cdRamo;
	}


	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}


	public String getCdperson() {
		return cdperson;
	}


	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}


	public String getWORIGEN() {
		return wORIGEN;
	}


	public void setWORIGEN(String worigen) {
		wORIGEN = worigen;
	}


}
