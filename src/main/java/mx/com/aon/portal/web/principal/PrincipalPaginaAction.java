package mx.com.aon.portal.web.principal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.configmenu.OpcionVO;
import mx.com.aon.portal.model.principal.ClienteVO;
import mx.com.aon.portal.model.principal.PaginaVO;
import mx.com.aon.portal.model.principal.RolesVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.principal.PrincipalManagerImpl;
import mx.com.aon.portal.service.principal.PrincipalManager;
import mx.com.aon.portal.web.AbstractListAction;
import mx.com.aon.portal.web.configmenu.ConfiguraMenuAction;

//import org.apache.avalon.framework.parameters.Parameters;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
/**
 * 
 * @author sergio.ramirez
 *
 */
public class PrincipalPaginaAction extends AbstractListAction implements SessionAware{

	/**
	 *serial version 
	 */
	private static final long serialVersionUID = -7880877798163496203L;
	private static Logger logger = Logger.getLogger(PrincipalManagerImpl.class);
	//private static final transient Log log= LogFactory.getLog(PrincipalPaginaAction.class);
	private boolean success;
	private String nombrePagina;
	private String rolPagina;
	private String clientePagina;
	private PrincipalManager principalManager;
	private List<PaginaVO> paginasConfiguradas;
	@SuppressWarnings("unchecked")//Map control.
	private Map session;
	 
	
	/**
	 *@see
	 *Parameters to insert a Page. 
	 */
	private String paginaNombre;
	private String claveClienteInsert;
	private String claveClientePagina;
	private String claveRolInsert;
	private String claveRolPagina;
	
	/**
	 * @see
	 * Parameters to edit a page.
	 */
	private String claveConfiguracion;
	private String dsConfiguracion;
	private String claveElemento;
	private String claveSistemaRol;
	
	
	/**
	 * @see
	 * Parameters to delete.
	 */
	private String id;
	
	/**
	 * @see
	 * Parameters to Copy.
	 */
	private String dsElemento;
	private String dsSistemaRol;
	private String clienteCopia;
	private String rolCopia; 
	private String idClienteNvo;
	private String idRolNvo; 
	private String idCliente;
	private String idRol;
	private int start = 0;
	private int limit = 20;
	private int totalCount;
	
	/**
	 * Metodo execute.
	 * 
	 */
	public  String execute() throws Exception{
		return INPUT;
		
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
	
	
	@SuppressWarnings("unchecked")
	/*
	public String cmdGetPaginas() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("Entrando al metodo getPaginas");
		}
		
		try{
			paginasConfiguradas = principalManager.getAllPaginas(nombrePagina, rolPagina, clientePagina);
			session.put("LISTA_PAGINAS", paginasConfiguradas);
		
			logger.debug("paginas "+ paginasConfiguradas);
			success=true;
			
		}catch (Exception e) {
		success = false;
		logger.debug("Configurar Pagina EXCEPTION :" + e);
		}
		
		if(paginasConfiguradas.isEmpty()){
			success=false;
		}
		
		
		
		return SUCCESS;
	}
	*/
	
	public String cmdGetPaginas()throws Exception{
    	try{
    		
    		if(logger.isDebugEnabled()){
    			logger.debug("******* cmdGetPaginas *******");
    			logger.debug("Entrando al metodo getPaginas");
    			logger.debug("start :::"+getStart());
    			logger.debug("limit :::"+getLimit());
    		}
    		
    		PagedList pagedList= this.principalManager.getAllPaginas(nombrePagina, rolPagina, clientePagina, start, limit);    		
    		logger.debug(">>>>>>> pagedList ::: "+pagedList.getTotalItems());
    		
    		paginasConfiguradas = pagedList.getItemsRangeList();
    		logger.debug(">>>>>>> paginasConfiguradas ::: "+paginasConfiguradas.size());
    		
    		totalCount = pagedList.getTotalItems();
    		logger.debug(">>>>>>> totalCount ::: "+totalCount);
    		
    		
    		String [] NOMBRE_COLUMNAS = {"Nombre","Rol", "Nivel"};
            Map params = new HashMap<String, String>();
            params.put("nombrePagina", nombrePagina);
            params.put("rolPagina", rolPagina);
            params.put("clientePagina", clientePagina);
            
            session.put("PARAMETROS_EXPORT", params);
            session.put("NOMBRE_COLUMNAS", NOMBRE_COLUMNAS);
            session.put("ENDPOINT_EXPORT_NAME", "EXPORT_PAGINAS");            
    		
    		
            if(logger.isDebugEnabled()){
    			logger.debug("******* cmdGetPaginas fin *******");
    			logger.debug("Saliendo del metodo cmdGetPaginas   SUCCESS ::: true");
    		}
            
    		success=true;
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
	
	
	
	//************************************************************************************************************************
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String guardaPaginas() throws Exception{
		logger.debug("<-insertando una nueva pagina-> ");
		logger.debug("claveConfiguracion->"+claveConfiguracion);
		logger.debug("paginaNombre->"+paginaNombre);
		logger.debug("claveClientePagina->"+claveClientePagina);
		logger.debug("claveRolPagina->"+claveRolPagina);
		
		List<ClienteVO> clientesPagina = (List<ClienteVO>) session.get("LISTA_CLIENTES");
		for(ClienteVO cliPa: clientesPagina){
			if(cliPa.getDescripcion().equals(claveClientePagina)){
				claveClienteInsert=cliPa.getClaveCliente();
			}else{
				success=false;
			}		
		}
		List<RolesVO> rolesLista= (List<RolesVO>)session.get("LISTA_ROLES");
		for(RolesVO roli: rolesLista){
			if(roli.getDsRol().equals(claveRolPagina)){
				claveRolInsert = roli.getCdRol();
			}else{
				success=false;
			}
		}
		/*principalManager.addPagina(claveConfiguracion, paginaNombre, claveClienteInsert, claveRolInsert);
		success=true;
		return SUCCESS;*/
		String messageResult = "";
		try{
			messageResult = principalManager.guardarPagina(claveConfiguracion, paginaNombre, claveClienteInsert, claveRolInsert);	
            success = true;
            addActionMessage(messageResult);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editaPaginas() throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("Entrando al metodo editaPaginas()");
			logger.debug("claveConfiguracion->"+claveConfiguracion);
			logger.debug("dsConfiguracion->"+dsConfiguracion);
			logger.debug("claveElemento->"+claveElemento);
			logger.debug("claveSistemaRol->"+claveSistemaRol);
		}
			if(StringUtils.isNotBlank(claveConfiguracion) && StringUtils.isNotBlank(claveElemento) && StringUtils.isNotBlank(claveSistemaRol)){
				principalManager.addPagina(claveConfiguracion, dsConfiguracion, claveElemento, claveSistemaRol);
				success=true;
			}else{
				success=false;
				
			}
		return SUCCESS;
		
	}
	
	public String deletePagina() throws Exception{
		logger.debug("id-->" + id);
		if(StringUtils.isNotBlank(id)){
			principalManager.borrarPagina(id);
			success=true;
		}else{
			 success=false;
		}
		success=true;
		return SUCCESS;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String copiaPagina() throws Exception{
		logger.debug("dsElemento->"+dsElemento);
		logger.debug("dsSistemaRol->"+ dsSistemaRol);
		logger.debug("clienteCopia->"+clienteCopia);
		logger.debug("rolCopia->"+rolCopia);
		
		// A estos dos parámetro los setea STRUTS desde la pantalla cuando se hace el post del formulario
		/*List<ClienteVO> cliente =(List<ClienteVO>)session.get("LISTA_CLIENTES");
		for(ClienteVO clien: cliente){
			if(clien.getDescripcion().equals(dsElemento)){
				idCliente = clien.getClaveCliente();
			}else{
				success = false;
			}
		}
		List<RolesVO> role = (List<RolesVO>) session.get("LISTA_ROLES");
		for(RolesVO rol:role){
			if(rol.getDsRol().equals(dsSistemaRol)){
				idRol=rol.getCdRol();
			}else{
				success = false;
			}
		}*/
		List<ClienteVO> clientes =(List<ClienteVO>)session.get("LISTA_CLIENTES");
		for(ClienteVO clien: clientes){
			if(clien.getDescripcion().equals(clienteCopia)){
				idClienteNvo= clien.getClaveCliente();
			}else{
				success = false;
			}
		}		
		List<RolesVO> roles = (List<RolesVO>) session.get("LISTA_ROLES");
		for(RolesVO rol:roles){
			if(rol.getDsRol().equals(rolCopia)){
				idRolNvo=rol.getCdRol();
			}else{
				success=false;
			}
		}		
		principalManager.copiarConfig(idCliente, idRol, idClienteNvo, idRolNvo);
		success=true;
		return SUCCESS;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getDsConfiguracion() {
		return dsConfiguracion;
	}
	/**
	 * 
	 * @param dsConfiguracion
	 */
	public void setDsConfiguracion(String dsConfiguracion) {
		this.dsConfiguracion = dsConfiguracion;
	}
	/**
	 * 
	 * @return
	 */
	public String getClaveElemento() {
		return claveElemento;
	}
	/**
	 * 
	 * @param claveElemento
	 */
	public void setClaveElemento(String claveElemento) {
		this.claveElemento = claveElemento;
	}
	/**
	 * 
	 * @return
	 */
	public String getClaveSistemaRol() {
		return claveSistemaRol;
	}
	/**
	 * 
	 * @param claveSistemaRol
	 */
	public void setClaveSistemaRol(String claveSistemaRol) {
		this.claveSistemaRol = claveSistemaRol;
	}
	/**
	 * 
	 * @return
	 */
	public String getIdCliente() {
		return idCliente;
	}
	/**
	 * 
	 * @param idCliente
	 */
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	/**
	 * 
	 * @return
	 */
	public String getIdRol() {
		return idRol;
	}
	/**
	 * 
	 * @param idRol
	 */
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}
	/**
	 * 
	 * @return
	 */
	public String getIdClienteNvo() {
		return idClienteNvo;
	}
	/**
	 * 
	 * @param idClienteNvo
	 */
	public void setIdClienteNvo(String idClienteNvo) {
		this.idClienteNvo = idClienteNvo;
	}
	/**
	 * 
	 * @return
	 */
	public String getIdRolNvo() {
		return idRolNvo;
	}
	/**
	 * 
	 * @param idRolNvo
	 */
	public void setIdRolNvo(String idRolNvo) {
		this.idRolNvo = idRolNvo;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsElemento() {
		return dsElemento;
	}
	/**
	 * 
	 * @param dsElemento
	 */
	public void setDsElemento(String dsElemento) {
		this.dsElemento = dsElemento;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsSistemaRol() {
		return dsSistemaRol;
	}
	/**
	 * 
	 * @param dsSistemaRol
	 */
	public void setDsSistemaRol(String dsSistemaRol) {
		this.dsSistemaRol = dsSistemaRol;
	}
	/**
	 * 
	 * @return
	 */
	public String getClienteCopia() {
		return clienteCopia;
	}
	/**
	 * 
	 * @param clienteCopia
	 */
	public void setClienteCopia(String clienteCopia) {
		this.clienteCopia = clienteCopia;
	}
	/**
	 * 
	 * @return
	 */
	public String getRolCopia() {
		return rolCopia;
	}
	/**
	 * 
	 * @param rolCopia
	 */
	public void setRolCopia(String rolCopia) {
		this.rolCopia = rolCopia;
	}
	/**
	 * 
	 * @return
	 */
	public List<PaginaVO> getPaginasConfiguradas() {
		return paginasConfiguradas;
	}
	/**
	 * 
	 * @param paginasConfiguradas
	 */
	public void setPaginasConfiguradas(List<PaginaVO> paginasConfiguradas) {
		this.paginasConfiguradas = paginasConfiguradas;
	}
	/**
	 * 
	 * @param principalManager
	 */
	public void setPrincipalManager(PrincipalManager principalManager) {
		this.principalManager = principalManager;
	}
	/**
	 * 
	 * @return
	 */
	public String getNombrePagina() {
		return nombrePagina;
	}
	/**
	 * 
	 * @param nombrePagina
	 */
	public void setNombrePagina(String nombrePagina) {
		this.nombrePagina = nombrePagina;
	}
	/**
	 * 
	 * @return
	 */
	public String getRolPagina() {
		return rolPagina;
	}
	/**
	 * 
	 * @param rolPagina
	 */
	public void setRolPagina(String rolPagina) {
		this.rolPagina = rolPagina;
	}
	/**
	 * 
	 * @return
	 */
	public String getClientePagina() {
		return clientePagina;
	}
	/**
	 * 
	 * @param clientePagina
	 */
	public void setClientePagina(String clientePagina) {
		this.clientePagina = clientePagina;
	}
		
	/**
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * 
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * 
	 * @return
	 */
	public String getPaginaNombre() {
		return paginaNombre;
	}
	/**
	 * 
	 * @param paginaNombre
	 */
	public void setPaginaNombre(String paginaNombre) {
		this.paginaNombre = paginaNombre;
	}
	/**
	 * 
	 * @return
	 */
	public String getClaveClientePagina() {
		return claveClientePagina;
	}
	/**
	 * 
	 * @param claveClientePagina
	 */
	public void setClaveClientePagina(String claveClientePagina) {
		this.claveClientePagina = claveClientePagina;
	}
	/**
	 * 
	 * @return
	 */
	public String getClaveRolPagina() {
		return claveRolPagina;
	}
	/**
	 * 
	 * @param claveRolPagina
	 */
	public void setClaveRolPagina(String claveRolPagina) {
		this.claveRolPagina = claveRolPagina;
	}
	/**
	 * 
	 * @return
	 */
	public String getClaveConfiguracion() {
		return claveConfiguracion;
	}
	/**
	 * 
	 * @param claveConfiguracion
	 */
	public void setClaveConfiguracion(String claveConfiguracion) {
		this.claveConfiguracion = claveConfiguracion;
	}
	/**
	 * 
	 * @return
	 */
	public String getClaveClienteInsert() {
		return claveClienteInsert;
	}
	/**
	 * 
	 * @param claveClienteInsert
	 */
	public void setClaveClienteInsert(String claveClienteInsert) {
		this.claveClienteInsert = claveClienteInsert;
	}
	/**
	 * 
	 * @return
	 */
	public String getClaveRolInsert() {
		return claveRolInsert;
	}
	/**
	 * 
	 * @param claveRolInsert
	 */
	public void setClaveRolInsert(String claveRolInsert) {
		this.claveRolInsert = claveRolInsert;
	}
	/**
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * Metodo del padre (sin usar).
	 */
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")//Map control
    public void setSession(Map session) {
        this.session = session;
    }
	

}
