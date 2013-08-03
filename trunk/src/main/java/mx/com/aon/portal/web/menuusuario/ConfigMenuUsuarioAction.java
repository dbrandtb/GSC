/**
 * 
 */
package mx.com.aon.portal.web.menuusuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.model.menuusuario.ConfiguracionMenuVO;
import mx.com.aon.portal.model.menuusuario.MenuVO;
import mx.com.aon.portal.model.opcmenuusuario.UsuarioVO;
import mx.com.aon.portal.model.principal.ClienteVO;
import mx.com.aon.portal.model.principal.ConfiguracionVO;
import mx.com.aon.portal.model.principal.RolVO;
import mx.com.aon.portal.model.principal.RolesVO;
import mx.com.aon.portal.model.principal.TipoVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.menuusuario.MenuUsuarioManager;
import mx.com.aon.portal.service.PagedList;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author eflores
 * @date 20/05/2008
 *
 */
public class ConfigMenuUsuarioAction extends ActionSupport implements SessionAware{

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -7629384251752332553L;
    private static final transient Log log= LogFactory.getLog(ConfigMenuUsuarioAction.class);
    @SuppressWarnings("unchecked")//Map control.
    private Map session;
    private ArrayList<RolVO> roles;
    private ArrayList<ClienteVO> clientes;
    private ArrayList<RolesVO> rolesLista;
    private ArrayList<TipoVO> tipos;
    private ArrayList<TipoVO> tiposMenu;
    private ArrayList<TipoVO> estados;
    private ArrayList<UsuarioVO> usuarios;
    private ArrayList<UsuarioVO> usuariosNivelRol;
    private RolVO rolVo;
    private ConfiguracionVO configuracionVo;
    
    private String dsMenu;
    private String cdMenu;
    private String cdElemento;
    private String dsElemento;
    private String rol;
    private String cliente;
    private String seccion;
    private String usuario;
    private String tabla;
    private String success;
    private MenuUsuarioManager menuUsuarioManager;
    private CatalogService catalogManager; 
    
    private List<MenuVO> menuList;
    
    /**
     * parameters to insert.
     */
    

    private String nombre;
    private String dsPerson;
    private String claveCliente;
    private String cdRol;
    private String dsRol;
    private String claveSeccion;
    private String dsUsuario;
    private String cdTitulo;
    private String dsTitulo;
    private String dsEstado;
    private String dsTipoMenu;
    private String tipo;
    private String tipoMenu;
    private String estado;
    private String ramo;
    private String opcionPadre;
    /**
     * parameters to edit.
     */
    
    private String cdConfigura;
    private String dsConfigura;
    private String dsElemen;
    private String dsTipo;
    
    private String pantallaAsociada; 
    private String opcionMenu;
    private MensajesVO mensajes;
    private String mensajeRespuesta;
    
    //atributo de respuesta para saber si hay resultados de busqueda
    private String exito;
    //atributo para la clave del cliente
    private String cdCliente;
  //atributo para la clave del usuario
    private String cdUsuario;
    
    /**
     * Para la paginacion del grid
     * */

	private int start = 0;
	private int limit = 20;
	private int totalCount;
    
    
    
    public String execute() throws Exception{
        log.debug("-> ConfigMenuUsuarioAction.execute");
        
        return INPUT;   
    }
    /**
     * Method that load menu usuarios.
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public String menuUsuariosJson()throws Exception{
            log.debug("-> ConfigMenuUsuarioAction.menuUsuariosJson");
            log.debug("dsMenu--->"+ dsMenu);            
            log.debug("cliente--->"+dsElemento);
            log.debug("cdRol---->"+cdRol);
            log.debug("usuario--->"+ dsUsuario);
            //Si hay usuario
            if (StringUtils.isNotBlank(dsUsuario) && !StringUtils.contains(dsUsuario, "Seleccion")) {
                List<UsuarioVO> usuarios = (List<UsuarioVO>) ActionContext.getContext().getSession().get("LISTA_USUARIOS_MENU");
                if (usuarios != null && !usuarios.isEmpty()) {
                    for(UsuarioVO usuario : usuarios){
                        if(usuario.getDsUsuario().equals(dsUsuario)){
                            dsUsuario = usuario.getCdUsuario();
                        }
                    }    
                }    
            } else {
                dsUsuario = null;
            }
             //Si hay Cliente           
            if (StringUtils.isNotBlank(dsElemento) && !StringUtils.contains(dsElemento, "Seleccion")) {
                List<ClienteVO> clientes = (List<ClienteVO>)ActionContext.getContext().getSession().get("LISTA_CLIENTES");
                if (clientes != null && !clientes.isEmpty()) {
                    for(ClienteVO clien : clientes){
                        if(clien.getDescripcion().equals(dsElemento)){
                            dsElemento = clien.getClaveCliente();
                            cdElemento = clien.getClaveCliente();
                        }
                    }    
                }    
            } else {
                dsElemento = null;
            }
            
            
            PagedList pagedList=  menuUsuarioManager.getMenuUsuarios(dsMenu, dsElemento, cdRol, dsUsuario, start,limit);
            
            if(  pagedList != null){
               menuList =pagedList.getItemsRangeList();
               setTotalCount(pagedList.getTotalItems());
            }
            
            log.debug("start: "+getStart());
			log.debug("limit: "+getLimit());
			log.debug("totalCount: "+getTotalCount());
            log.debug("menuList-->"+menuList);
            
            //Si el menu esta vacio mostraremos un mensaje obtenido con el codigo 100013:
            /*
            if(menuList.isEmpty()){
            	
            	try{
            		Map<String, String> params = new HashMap<String, String>();
            		params.put("msg", "100013");
            		params.put("log", "");
            		params.put("cdusuario", "");
            		params.put("dsprograma", "menuUsuario");
            		mensajes = null;
            		mensajes = catalogManager.getMensajes(params, "OBTIENE_MENSAJES");
            		mensajeRespuesta = mensajes.getMsgText();
            		
            		success=null;
            		exito ="false";
            	}catch(Exception e){
            		success=null;
            		log.error("Exception configOpcionMenuVo :"+e);
            	}
                
            }else{
            	success="true";
                exito ="true";
            }
            */
            
            
         
            if( (menuList != null)  && (! menuList.isEmpty())  ){
           	    success="true";
                exito ="true";	
           }else{
        	 //Si el menu esta null o vacio  mostraremos un mensaje obtenido con el codigo 100013:   
         	   try{
            		Map<String, String> params = new HashMap<String, String>();
            		params.put("msg", "100013");
            		params.put("log", "");
            		params.put("cdusuario", "");
            		params.put("dsprograma", "menuUsuario");
            		mensajes = null;
            		mensajes = catalogManager.getMensajes(params, "OBTIENE_MENSAJES");
            		mensajeRespuesta = mensajes.getMsgText();
            		log.debug("mensajeRespuesta menuList => null="  + mensajeRespuesta );
            		
            		success=null;
            		exito ="false";
              }catch(Exception e){
                 success=null;
            	  log.error("Exception configOpcionMenuVo2 :"+e);
              }
           } 	
            
    
            //Exportador Generico
            String [] NOMBRE_COLUMNAS = {"NOMBRE","CLIENTE","ROL","USUARIO","TIPO","ESTADO"};
            Map params = new HashMap<String, String>();
            params.put("nombre", dsMenu);
            params.put("cliente", dsElemento);
            //log.debug("HHH valor de dsElemento-->"+dsElemento);
            params.put("usuario", dsUsuario);
            params.put("rol", cdRol);
            session.put("PARAMETROS_EXPORT", params);
            session.put("NOMBRE_COLUMNAS", NOMBRE_COLUMNAS);
            session.put("ENDPOINT_EXPORT_NAME", "EXPORT_MENU_USUARIO");
            
            success = "true";
            return SUCCESS;
    }
    
    /**
     * @return
     * @throws ApplicationException 
     */
    private List<TipoVO> obtieneTiposMenu() throws ApplicationException {
        log.debug("-> ConfigMenuUsuarioAction.obtieneTiposMenu");
        List<TipoVO> tipoListMenu = (List<TipoVO>) session.get("LISTA_TIPOS_MENU");
        if (tipoListMenu != null && !tipoListMenu.isEmpty()) {
            return tipoListMenu;
        }
        tipoListMenu = menuUsuarioManager.getListaTipo("TTIPOMENU");
        session.put("LISTA_TIPOS_MENU", tipoListMenu);
        
        return tipoListMenu;
    }
    /**
     * @return
     * @throws ApplicationException 
     */
    private List<TipoVO> obtieneEstados() throws ApplicationException {
        log.debug("-> ConfigMenuUsuarioAction.obtieneEstados");
        List<TipoVO> estadoList = (List<TipoVO>) session.get("LISTA_ESTADOS");
        if (estadoList != null && !estadoList.isEmpty()) {
            return estadoList;
        }
        estadoList = menuUsuarioManager.getListaTipo("TACTIVO");
        session.put("LISTA_ESTADOS", estadoList);
        
        return estadoList;
    }
    /**
     * Method that load a new Configuration.    
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")//Map Control.
    public String getComboCliente() throws Exception{
        log.debug("Cargando combo de Clientes");
        clientes = (ArrayList<ClienteVO>) session.get("LISTA_CLIENTES");
        log.debug("clientes session----->" + clientes);
        if (clientes != null && !clientes.isEmpty()) {
            success = "true";
            return SUCCESS;
        } else {
            clientes = menuUsuarioManager.getListaCliente();
            session.put("LISTA_CLIENTES", clientes);
        }
        clientes = menuUsuarioManager.getListaCliente();
        session.put("LISTA_CLIENTES", clientes);
        log.debug("clientes----->" + clientes);
        success = "true";
        return SUCCESS;
    }
    
    /**
     * Method that load a new Configuration.    
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")//Map Control.
    public String getComboUsuarios() throws Exception{
        log.debug("Cargando combo de Usuarios...");       
        usuarios = (ArrayList<UsuarioVO>) session.get("LISTA_USUARIOS_MENU");
        log.debug("usuarios session----->" + usuarios);
        
        if (usuarios != null && !usuarios.isEmpty()) {
            success = "true";
            return SUCCESS;
        } else {
            usuarios = menuUsuarioManager.getUsuarios();
            session.put("LISTA_USUARIOS_MENU", usuarios);
        }
        usuarios = menuUsuarioManager.getUsuarios();
        session.put("LISTA_USUARIOS_MENU", usuarios);
        log.debug("usuarios----->" + usuarios);
        success = "true";
        return SUCCESS;
    }   
        
    /**
     * Método encargado de obtener la lista de usuarios a partir del Rol.    
     * @return String
     * @throws Exception
     */
    @SuppressWarnings("unchecked")//Map Control.
    public String getComboUsuariosNivelRol() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug(">>>>>>> Cargando combo de Usuarios x nivel y rol...");
        }
        
        usuariosNivelRol = menuUsuarioManager.getUsuariosNR(claveCliente, cdRol);
        
        if (log.isDebugEnabled()) {
            log.debug(">>>>>>> Cargando combo de Usuarios");
            log.debug(">>>>>>> usuariosNivelRol----->" + usuariosNivelRol);
        }
        success = "true";
        return SUCCESS;
    }   
    
    
    /**
     * Metodo que obtiene una lista del objeto TipoVO
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String getComboTipo() throws Exception {
        tabla = "CTIPOPAGPRIN";
        log.debug("Cargando combo de tipos");
        tipos = menuUsuarioManager.getListaTipo(tabla);
        log.debug("tipos-->" + tipos);
        session.put("LISTA_TIPOS", tipos);
        success = "true";
        return SUCCESS;

    }
    
    @SuppressWarnings("unchecked")
    public String getComboTipoMenu() throws Exception{
        log.debug("Cargando combo de tipo menu");
        tiposMenu = (ArrayList<TipoVO>) obtieneTiposMenu();
        //tiposMenu = menuUsuarioManager.getListaTipo("TTIPOMENU");
        session.put("LISTA_TIPOS_MENU", tiposMenu);
        success="true";
        return SUCCESS;
        
    }
    
    @SuppressWarnings("unchecked")
    public String getComboEstado() throws Exception{
        log.debug("Cargando combo de estado");
        estados = (ArrayList<TipoVO>) obtieneEstados();
        //estados = menuUsuarioManager.getListaTipo("TACTIVO");
        session.put("LISTA_ESTADOS", estados);
        success="true";
        return SUCCESS;
        
    }
    
    @SuppressWarnings("unchecked")
    public String getComboRol() throws Exception{
        log.debug("Cargando combo de rol");
        rolesLista = menuUsuarioManager.getListaRol();
        session.put("LISTA_ROLES", rolesLista);
        success="true";
        return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String addMenu() throws Exception{
    	
    	try{
        log.debug("->  antresde guardar ConfigMenuUsuarioAction.addMenu"); 
        log.debug("cdElemento-->" + cdElemento);
        log.debug("dsMenu-->"+ dsMenu);
        log.debug("dsPerson-->"+ dsPerson);
        log.debug("cdRol-->" + cdRol);
        log.debug("dsUsuario-->"+ dsUsuario);
        log.debug("opcionMenu-->"+ opcionMenu );
        log.debug("ramo-->" + ramo);
        log.debug("opcionPadre-->" + opcionPadre);
        log.debug("pantallaAsociada-->" + pantallaAsociada);
        log.debug("dsTipoMenu-->" + dsTipoMenu);
        log.debug("dsEstado-->" + dsEstado);
        
        ConfiguracionMenuVO configuracionMenuVo= new ConfiguracionMenuVO();
        configuracionMenuVo.setNombreMenu(dsMenu);
        configuracionMenuVo.setClaveCliente(dsPerson);
        configuracionMenuVo.setClaveUsuario(dsUsuario);
        
/*        List<ClienteVO> cliente = (List<ClienteVO>)ActionContext.getContext().getSession().get("LISTA_CLIENTES");
        for(ClienteVO clien : cliente){
            if(clien.getDescripcion().equals(dsPerson)){
                configuracionMenuVo.setClaveCliente(clien.getClaveCliente());
            }
        }
    
        List<UsuarioVO> usuarios = (List<UsuarioVO>) ActionContext.getContext().getSession().get("LISTA_USUARIOS_MENU");
        for(UsuarioVO usuario : usuarios){
            if(usuario.getDsUsuario().equals(dsUsuario)){
                configuracionMenuVo.setClaveUsuario(usuario.getCdUsuario());
            }
        }*/
        
        configuracionMenuVo.setClaveRol(cdRol);
        configuracionMenuVo.setOpcionMenu(opcionMenu);
        configuracionMenuVo.setRamo(ramo);
        configuracionMenuVo.setOpcionPadre(opcionPadre);
        configuracionMenuVo.setPantallaAsociada(pantallaAsociada);
        configuracionMenuVo.setTipoMenu(dsTipoMenu);
        configuracionMenuVo.setEstado(dsEstado);
/*        List<TipoVO> tipoListMenu = (List<TipoVO>)ActionContext.getContext().getSession().get("LISTA_TIPOS_MENU");
        for(TipoVO tipMenu : tipoListMenu){
            if(tipMenu.getValor().equals(dsTipoMenu)){
                configuracionMenuVo.setTipoMenu(tipMenu.getClave());               
            }
        }
        List<TipoVO> estadoList = (List<TipoVO>)ActionContext.getContext().getSession().get("LISTA_ESTADOS");
        for(TipoVO edo : estadoList){
            if(edo.getValor().equals(dsEstado)){
                configuracionMenuVo.setEstado(edo.getClave());               
            }
        }*/
        
        //log.debug(" HHH -->Guardar configuracionMenuVo : " + configuracionMenuVo);
        
        mensajes = menuUsuarioManager.agregarconfiguracion(configuracionMenuVo);
        if (log.isDebugEnabled())
        	log.debug("msgID configOpcionMenuVo: " + mensajes.getMsgId());
        //UserVO usuario = (UserVO) session.get("USUARIO");
        Map<String, String> params = new HashMap<String, String>();
        params.put("msg", mensajes.getMsgId());
        params.put("log", "");
        params.put("cdusuario", "");
        params.put("dsprograma", "menuUsuario");
        mensajes = null;
        mensajes = catalogManager.getMensajes(params, "OBTIENE_MENSAJES");
        mensajeRespuesta = mensajes.getMsgText();
        if (log.isDebugEnabled())
        	log.debug("msgText configOpcionMenuVo: " + mensajes.getMsgText());        
        success="true";      
    	}catch(Exception e){
    		success=null;
    		log.debug("Exception configOpcionMenuVo :"+e);
    	}
    	
    	return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String editarMenu() throws Exception{
    	
    	try{
        if (log.isDebugEnabled()) {
            log.debug("-> ConfigMenuUsuarioAction.editarMenu");
            log.debug("cdMenu-->"+ cdMenu);
            log.debug("dsMenu-->"+ dsMenu);
            log.debug("dsElemento-->"+ dsElemento);
            log.debug("cdRol-->" + cdRol);
            log.debug("dsUsuario-->"+ dsUsuario);
            log.debug("opcionMenu-->"+ opcionMenu );
            log.debug("ramo-->" + ramo);
            log.debug("opcionPadre-->" + opcionPadre);
            log.debug("pantallaAsociada-->" + pantallaAsociada);
            log.debug("dsTipoMenu-->" + dsTipoMenu);
            log.debug("dsEstado-->" + dsEstado);
        }

        ConfiguracionMenuVO configuracionMenuVo= new ConfiguracionMenuVO();

        configuracionMenuVo.setClaveMenu(cdMenu);
        configuracionMenuVo.setNombreMenu(dsMenu);     
        configuracionMenuVo.setClaveCliente(cdCliente);
        configuracionMenuVo.setClaveUsuario(cdUsuario);
        configuracionMenuVo.setOpcionMenu(opcionMenu);
        configuracionMenuVo.setRamo(ramo);
        configuracionMenuVo.setOpcionPadre(opcionPadre);
        configuracionMenuVo.setPantallaAsociada(pantallaAsociada);

        List<TipoVO> tipoListMenu = (List<TipoVO>)ActionContext.getContext().getSession().get("LISTA_TIPOS_MENU");
        for(TipoVO tipMenu : tipoListMenu){
            if(tipMenu.getValor().equals(dsTipoMenu)){
                configuracionMenuVo.setTipoMenu(tipMenu.getClave());
            }
        }
        List<TipoVO> estadoList = (List<TipoVO>)ActionContext.getContext().getSession().get("LISTA_ESTADOS");
        for(TipoVO edo : estadoList){
            if(edo.getValor().equals(dsEstado)){
                configuracionMenuVo.setEstado(edo.getClave());               
            }
        }

        List<RolesVO> rolesList = (List<RolesVO>)ActionContext.getContext().getSession().get("LISTA_ROLES");
        for(RolesVO rol : rolesList){
        	if(rol.getDsRol().equals(cdRol)){
        		configuracionMenuVo.setClaveRol(rol.getCdRol());
        	}
        }

        if (log.isDebugEnabled()) {
            log.debug(" res Editar configuracionMenuVo : " + configuracionMenuVo);
        }
        mensajes = menuUsuarioManager.agregarconfiguracion(configuracionMenuVo);
        if (log.isDebugEnabled())
        	log.debug("msgID configOpcionMenuVo: " + mensajes.getMsgId());

        Map<String, String> params = new HashMap<String, String>();
        params.put("msg", mensajes.getMsgId());
        params.put("log", "");
        params.put("cdusuario", "");
        params.put("dsprograma", "menuUsuario");
        mensajes = null;
        mensajes = catalogManager.getMensajes(params, "OBTIENE_MENSAJES");
        mensajeRespuesta = mensajes.getMsgText();
        if (log.isDebugEnabled())
        	log.debug("msgText configOpcionMenuVo: " + mensajes.getMsgText());
        success="true";
        
    	}catch(Exception e){
    		success=null;
    		log.debug("Exception configOpcionMenuVo :"+e);
    	}
        return SUCCESS;     
    }
    
    public String getCdConfigura() {
        return cdConfigura;
    }
    
    public void setCdConfigura(String cdConfigura) {
        this.cdConfigura = cdConfigura;
    }
    
    public String getDsConfigura() {
        return dsConfigura;
    }
    
    public void setDsConfigura(String dsConfigura) {
        this.dsConfigura = dsConfigura;
    }
    
    public String getDsElemen() {
        return dsElemen;
    }
    
    public void setDsElemen(String dsElemen) {
        this.dsElemen = dsElemen;
    }
    
    public String getDsRol() {
        return dsRol;
    }
    
    public void setDsRol(String dsRol) {
        this.dsRol = dsRol;
    }
    
    public String getDsTipo() {
        return dsTipo;
    }
    
    public void setDsTipo(String dsTipo) {
        this.dsTipo = dsTipo;
    }
    
    @SuppressWarnings("unchecked")//Map control
    public void setSession(Map session) {
        this.session = session;
    }
    
    public ConfiguracionVO getConfiguracionVo() {
        return configuracionVo;
    }
    public void setConfiguracionVo(ConfiguracionVO configuracionVo) {
        this.configuracionVo = configuracionVo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getClaveSeccion() {
        return claveSeccion;
    }
    public void setClaveSeccion(String claveSeccion) {
        this.claveSeccion = claveSeccion;
    }
    
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getTabla() {
        return tabla;
    }
    public void setTabla(String tabla) {
        this.tabla = tabla;
    }
    public RolVO getRolVo() {
        return rolVo;
    }
    public void setRolVo(RolVO rolVo) {
        this.rolVo = rolVo;
    }
    public ArrayList<RolesVO> getRolesLista() {
        return rolesLista;
    }
    public void setRolesLista(ArrayList<RolesVO> rolesLista) {
        this.rolesLista = rolesLista;
    }
    public ArrayList<ClienteVO> getClientes() {
        return clientes;
    }
    public void setClientes(ArrayList<ClienteVO> clientes) {
        this.clientes = clientes;
    }
    public String getSuccess() {
        return success;
    }
    public void setSuccess(String success) {
        this.success = success;
    }
    public ArrayList<RolVO> getRoles() {
        return roles;
    }
    public void setRoles(ArrayList<RolVO> roles) {
        this.roles = roles;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    public String getSeccion() {
        return seccion;
    }
    
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
    
    /**
     * @param menuUsuarioManager the menuUsuarioManager to set
     */
    public void setMenuUsuarioManager(MenuUsuarioManager menuUsuarioManager) {
        this.menuUsuarioManager = menuUsuarioManager;
    }
    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }
    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    /**
     * @return the menuList
     */
    public List<MenuVO> getMenuList() {
        return menuList;
    }
    /**
     * @param menuList the menuList to set
     */
    public void setMenuList(List<MenuVO> menuList) {
        this.menuList = menuList;
    }
    /**
     * @return the usuarios
     */
    public ArrayList<UsuarioVO> getUsuarios() {
        return usuarios;
    }
    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(ArrayList<UsuarioVO> usuarios) {
        this.usuarios = usuarios;
    }
    /**
     * @return the pantallaAsociada
     */
    public String getPantallaAsociada() {
        return pantallaAsociada;
    }
    /**
     * @param pantallaAsociada the pantallaAsociada to set
     */
    public void setPantallaAsociada(String pantallaAsociada) {
        this.pantallaAsociada = pantallaAsociada;
    }
    /**
     * @return the estados
     */
    public ArrayList<TipoVO> getEstados() {
        return estados;
    }
    /**
     * @param estados the estados to set
     */
    public void setEstados(ArrayList<TipoVO> estados) {
        this.estados = estados;
    }
    /**
     * @return the tiposMenu
     */
    public ArrayList<TipoVO> getTiposMenu() {
        return tiposMenu;
    }
    /**
     * @param tiposMenu the tiposMenu to set
     */
    public void setTiposMenu(ArrayList<TipoVO> tiposMenu) {
        this.tiposMenu = tiposMenu;
    }
    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }
    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    /**
     * @return the tipos
     */
    public ArrayList<TipoVO> getTipos() {
        return tipos;
    }
    /**
     * @param tipos the tipos to set
     */
    public void setTipos(ArrayList<TipoVO> tipos) {
        this.tipos = tipos;
    }
    /**
     * @return the tipoMenu
     */
    public String getTipoMenu() {
        return tipoMenu;
    }
    /**
     * @param tipoMenu the tipoMenu to set
     */
    public void setTipoMenu(String tipoMenu) {
        this.tipoMenu = tipoMenu;
    }
    /**
     * @return the opcionMenu
     */
    public String getOpcionMenu() {
        return opcionMenu;
    }
    /**
     * @param opcionMenu the opcionMenu to set
     */
    public void setOpcionMenu(String opcionMenu) {
        this.opcionMenu = opcionMenu;
    }
    /**
     * @return the opcionPadre
     */
    public String getOpcionPadre() {
        return opcionPadre;
    }
    /**
     * @param opcionPadre the opcionPadre to set
     */
    public void setOpcionPadre(String opcionPadre) {
        this.opcionPadre = opcionPadre;
    }
    /**
     * @return the ramo
     */
    public String getRamo() {
        return ramo;
    }
    /**
     * @param ramo the ramo to set
     */
    public void setRamo(String ramo) {
        this.ramo = ramo;
    }
    /**
     * @return the cdMenu
     */
    public String getCdMenu() {
        return cdMenu;
    }
    /**
     * @param cdMenu the cdMenu to set
     */
    public void setCdMenu(String cdMenu) {
        this.cdMenu = cdMenu;
    }
    /**
     * @return the dsMenu
     */
    public String getDsMenu() {
        return dsMenu;
    }
    /**
     * @param dsMenu the dsMenu to set
     */
    public void setDsMenu(String dsMenu) {
        this.dsMenu = dsMenu;
    }
    /**
     * @return the dsUsuario
     */
    public String getDsUsuario() {
        return dsUsuario;
    }
    /**
     * @param dsUsuario the dsUsuario to set
     */
    public void setDsUsuario(String dsUsuario) {
        this.dsUsuario = dsUsuario;
    }
    /**
     * @return the dsPerson
     */
    public String getDsPerson() {
        return dsPerson;
    }
    /**
     * @param dsPerson the dsPerson to set
     */
    public void setDsPerson(String dsPerson) {
        this.dsPerson = dsPerson;
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
     * @return the dsTitulo
     */
    public String getDsTitulo() {
        return dsTitulo;
    }
    /**
     * @param dsTitulo the dsTitulo to set
     */
    public void setDsTitulo(String dsTitulo) {
        this.dsTitulo = dsTitulo;
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
     * @return the dsElemento
     */
    public String getDsElemento() {
        return dsElemento;
    }
    /**
     * @param dsElemento the dsElemento to set
     */
    public void setDsElemento(String dsElemento) {
        this.dsElemento = dsElemento;
    }
    /**
     * @return the dsEstado
     */
    public String getDsEstado() {
        return dsEstado;
    }
    /**
     * @param dsEstado the dsEstado to set
     */
    public void setDsEstado(String dsEstado) {
        this.dsEstado = dsEstado;
    }
    /**
     * @return the dsTipoMenu
     */
    public String getDsTipoMenu() {
        return dsTipoMenu;
    }
    /**
     * @param dsTipoMenu the dsTipoMenu to set
     */
    public void setDsTipoMenu(String dsTipoMenu) {
        this.dsTipoMenu = dsTipoMenu;
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
	 * @return the mensajes
	 */
	public MensajesVO getMensajes() {
		return mensajes;
	}
	/**
	 * @param mensajes the mensajes to set
	 */
	public void setMensajes(MensajesVO mensajes) {
		this.mensajes = mensajes;
	}
	/**
	 * @return the mensajeRespuesta
	 */
	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}
	/**
	 * @param mensajeRespuesta the mensajeRespuesta to set
	 */
	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}
	/**
	 * @param catalogManager the catalogManager to set
	 */
	public void setCatalogManager(CatalogService catalogManager) {
		this.catalogManager = catalogManager;
	}
	public String getExito() {
		return exito;
	}
	public void setExito(String exito) {
		this.exito = exito;
	}
	public void setCdCliente(String cdCliente) {
		this.cdCliente = cdCliente;
	}
	public String getCdCliente() {
		return cdCliente;
	}
	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}
	public String getCdUsuario() {
		return cdUsuario;
	}
	public String getClaveCliente() {
		return claveCliente;
	}
	public void setClaveCliente(String claveCliente) {
		this.claveCliente = claveCliente;
	}
    /**
     * @return the usuariosNivelRol
     */
    public ArrayList<UsuarioVO> getUsuariosNivelRol() {
        return usuariosNivelRol;
    }
    /**
     * @param usuariosNivelRol the usuariosNivelRol to set
     */
    public void setUsuariosNivelRol(ArrayList<UsuarioVO> usuariosNivelRol) {
        this.usuariosNivelRol = usuariosNivelRol;
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
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}