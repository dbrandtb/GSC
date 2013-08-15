package mx.com.aon.portal.web.opcmenuusuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.model.menuusuario.MenuVO;
import mx.com.aon.portal.model.opcmenuusuario.ConfigOpcionMenuVO;
import mx.com.aon.portal.model.opcmenuusuario.NivelMenuVO;
import mx.com.aon.portal.model.opcmenuusuario.OpcionMenuVO;
import mx.com.aon.portal.model.opcmenuusuario.OpcionesVO;
import mx.com.aon.portal.model.opcmenuusuario.ProductoClienteVO;
import mx.com.aon.portal.model.opcmenuusuario.UsuarioVO;
import mx.com.aon.portal.model.principal.ClienteVO;
import mx.com.aon.portal.model.principal.RolVO;
import mx.com.aon.portal.model.principal.RolesVO;
import mx.com.aon.portal.model.principal.SeccionVO;
import mx.com.aon.portal.model.principal.TipoVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.opcmenuusuario.OpcMenuUsuarioManager;
import mx.com.aon.portal.web.AbstractListAction;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author eflores
 * @date 22/05/2008
 *
 */
public class ConfigOpcionMenuUsuarioAction extends AbstractListAction {

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -7629384251752332553L;
    private static final transient Log log= LogFactory.getLog(ConfigOpcionMenuUsuarioAction.class);
    private ArrayList<RolVO> roles;
    private ArrayList<ClienteVO> clientes;
    private ArrayList<SeccionVO> secciones; 
    private ArrayList<RolesVO> rolesLista;
    private ArrayList<TipoVO> tipos;
    private ArrayList<TipoVO> tiposMenu;
    private ArrayList<TipoVO> estados;
    private ArrayList<TipoVO> tipoSituacionList;
    private ArrayList<UsuarioVO> usuarios;
    private ArrayList<ProductoClienteVO> productoCliente;
    private ArrayList<OpcionesVO> opciones;
    private ArrayList<NivelMenuVO> nivelMenu;
    private ConfigOpcionMenuVO configOpcionMenuVo;
    
    private String dsMenu;
    private String cdMenu;
    private String cdElemento;
    private String dsElemento;
    private String rol;
    private String cliente;
    private String seccion;
    private String cdUsuario;
    private String tabla;
    private boolean success;
    private String exito;
    
    private OpcMenuUsuarioManager opcMenuUsuarioManager;
    private CatalogService catalogManager;    
    
    private ArrayList<MenuVO> menuList;
    private List<OpcionMenuVO> opcionMenuList;
    
    /**
     * parameters to insert.
     */    

    private String nombre;
    private String dsPerson;
    private String cdRol;
    private String dsRol;
    private String claveSeccion;
    private String dsUsuario;
    private String cdTitulo;
    private String dsTitulo;
    private String dsTituloCliente;
    private String cdNivel;
    private String dsMenuEst;
    private String tipo;
    private String tipoMenu;
    private String dsEstado;
    private String ramo;
    private String opcionPadre;
    private String cdTituloCliente;
    private String dsTipoSituacion;
    private String dsTipsit;
    private String cdTipoMenu;
    private String dsTipoMenu;
    /**
     * parameters to edit.
     */
    
    private String pantallaAsociada; 
    private String opcionMenu;
    private String comboTest;//TODO quitar
    private MensajesVO mensajes;
    private String mensajeRespuesta;
    private String store;
    private Map<String,String> parametrosBusqueda;
    
    @SuppressWarnings("unchecked")
	public String execute() throws Exception{
        log.debug("-> ConfigOpcionMenuUsuarioAction.execute");
        log.debug("cdMenu-------->" + cdMenu);
        session.put("CD_MENU", cdMenu);
        
        MenuVO menuVO = null;
        menuList = opcMenuUsuarioManager.obtieneMenu(cdMenu);  
        log.debug("menuList-->" + menuList);
        if (menuList != null && !menuList.isEmpty()) {
            menuVO = menuList.get(0);    
        }
                    
        cdElemento = menuVO.getCdElemento();
        session.put("CD_ELEMENTO", cdElemento);
        
        List<ClienteVO> cliente = (List<ClienteVO>) session.get("LISTA_CLIENTES");
        if (cliente != null && !cliente.isEmpty()) {
            for(ClienteVO clien : cliente){
                if(clien.getClaveCliente().equalsIgnoreCase(cdElemento)){
                    dsElemento = clien.getDescripcion();
                }
            }    
        }
                                
        cdRol = menuVO.getCdRol();
        
        cdUsuario = menuVO.getCdUsuario();
        
        List<UsuarioVO> usuarios = obtenerUsuarios();
        
        log.debug("cdUsuario--->" + cdUsuario);
        log.debug("usuarios--->" + usuarios);
        if (usuarios != null && !usuarios.isEmpty()) {
            for(UsuarioVO usuario : usuarios){
                if(usuario.getCdUsuario().equalsIgnoreCase(cdUsuario)){
                    dsUsuario = usuario.getDsUsuario();
                }
            }    
        }
                    
        log.debug("cliente--->"+ dsElemento);
        log.debug("cdRol---->"+ cdRol);
        log.debug("usuario--->"+ dsUsuario);
        
        List<TipoVO> tiposMenu = (List<TipoVO>)session.get("LISTA_TIPOS_MENU");
        if (tiposMenu != null && !tiposMenu.isEmpty()){
        	for(TipoVO tipoMenu : tiposMenu){
        		if ( menuVO.getCdTipoMenu().equalsIgnoreCase( tipoMenu.getClave() ) ){
        			cdTipoMenu = tipoMenu.getClave();
        			dsTipoMenu = tipoMenu.getValor();
        		}
        	}        	
        }
        
        session.put("MENU_TITULO", menuVO.getDsMenu());
        session.put("MENU_CLIENTE", dsElemento);
        session.put("MENU_ROL", cdRol);
        session.put("MENU_USUARIO", dsUsuario);
        session.put("MENU_TIPO", dsTipoMenu);
                
        return INPUT;   
    }
    
    /**
     * Method that load menu usuarios.
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public String menuUsuariosJson()throws Exception {
            log.debug("-> ConfigOpcionMenuUsuarioAction.menuUsuariosJson");
            
            if (StringUtils.isBlank(cdMenu)) {
                cdMenu = (String) session.get("CD_MENU");
            }
            
            log.debug("cdMenu-------->" + cdMenu);
                                                            
            MenuVO menuVO = null;
            menuList = opcMenuUsuarioManager.obtieneMenu(cdMenu);  
            log.debug("menuList-->" + menuList);
            if (menuList != null && !menuList.isEmpty()) {
                menuVO = menuList.get(0);    
            }
                        
            cdElemento = menuVO.getCdElemento();
            session.put("CD_ELEMENTO", cdElemento);
            
            List<ClienteVO> cliente = (List<ClienteVO>)session.get("LISTA_CLIENTES");
            if (cliente != null && !cliente.isEmpty()) {
                for(ClienteVO clien : cliente){
                    if(clien.getClaveCliente().equalsIgnoreCase(cdElemento)){
                        dsElemento = clien.getDescripcion();
                    }
                }    
            }
                                    
            cdRol = menuVO.getCdRol();
            
            List<UsuarioVO> usuarios = obtenerUsuarios();
            if (usuarios != null && !usuarios.isEmpty()) {
                for(UsuarioVO usuario : usuarios){
                    if(usuario.getCdUsuario().equalsIgnoreCase(menuVO.getCdUsuario())){
                        dsUsuario = usuario.getDsUsuario();
                    }
                }    
            }
            
            ///////////////////////////// Lista Opcion Menu
            List<ProductoClienteVO> prodClien = (List<ProductoClienteVO>)session.get("LISTA_PRODUCTO_CLIENTES");
            List<NivelMenuVO> nivMen = obtieneNivelesMenu(cdMenu);
            List<TipoVO> estadoList = (List<TipoVO>)session.get("LISTA_ESTADOS");

            //Parametros de busqueda
            log.debug("store-------->" + store);
            if(store == null){
                if(session.containsKey("parametrosBusqueda")){
                	parametrosBusqueda = (Map<String, String>) session.get("parametrosBusqueda");
                }else{
                    parametrosBusqueda = new HashMap<String,String>();
                	parametrosBusqueda.put("cdMenu", cdMenu);
                }
            }else{
            	parametrosBusqueda.put("cdMenu", cdMenu);
            	session.put("parametrosBusqueda", parametrosBusqueda);
            }
            log.debug("parametrosBusqueda-------->" + parametrosBusqueda);
            
            PagedList pagedList = opcMenuUsuarioManager.obtieneOpcionesMenu(parametrosBusqueda, start, limit);
            opcionMenuList = (List<OpcionMenuVO>)pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
            log.debug("opcionMenuList 1-->" + opcionMenuList);
            
            if (opcionMenuList != null && !opcionMenuList.isEmpty()) {
                for (OpcionMenuVO opcionMenu : opcionMenuList) {
                    opcionMenu.setDsElemento(dsElemento);
                    opcionMenu.setCdRol(cdRol);
                    opcionMenu.setDsUsuario(dsUsuario);
                    
                    ////////////
                    if (prodClien != null && !prodClien.isEmpty()) {
                        for(ProductoClienteVO prodCli : prodClien){
                            if(prodCli.getCdTituloCliente() != null && 
                                    prodCli.getCdTituloCliente().equals(opcionMenu.getCdRamo())){
                                opcionMenu.setCdTituloCliente(opcionMenu.getCdRamo());
                                opcionMenu.setDsTituloCliente(prodCli.getDsTituloCliente());
                            }
                        }    
                    }
                    log.debug("-->nivMen: " + nivMen);
                    if (nivMen != null && !nivMen.isEmpty()) {
                        for(NivelMenuVO nivMe : nivMen){
                            if(nivMe.getCdNivel() != null && 
                                    nivMe.getCdNivel().equals(opcionMenu.getCdNivelPadre())){
                                opcionMenu.setOpcionMenu(nivMe.getDsMenuEst());
                            }
                        }    
                    }
                    
                    if (estadoList != null && !estadoList.isEmpty()) {
                        for(TipoVO edo : estadoList){
                            if(edo.getClave() != null && 
                                    edo.getClave().equals(opcionMenu.getCdEstado())){
                                opcionMenu.setDsEstado(edo.getValor());               
                            }
                        }    
                    }
                }
            }
            
            log.debug("opcionMenuList 2-->" + opcionMenuList);
            
            if(opcionMenuList.isEmpty()){
            	try{
            		Map<String, String> params = new HashMap<String, String>();
            		params.put("msg", "100013");
            		params.put("log", "");
            		params.put("cdusuario", "");
            		params.put("dsprograma", "menuUsuario");
            		mensajes = null;
            		mensajes = catalogManager.getMensajes(params, "OBTIENE_MENSAJES");
            		mensajeRespuesta = mensajes.getMsgText();
            		
            		success=false;
            		exito = "false";
            	}catch(Exception e){
            		success=false;
            		exito = "false";
            		log.error("Exception  :"+e);
            	}
            }else{
            	success=true;
            	exito = "true";
            }
            
            //Exportador Generico
            String [] NOMBRE_COLUMNAS = {"OPCION","OPCION_PADRE","RAMO","TIPO_SITUACION","NOMBRE"};
            session.put("NOMBRE_COLUMNAS", NOMBRE_COLUMNAS);
            session.put("ENDPOINT_EXPORT_NAME", "EXPORT_OPCIONES_MENU");
            session.put("PARAMETROS_EXPORT", parametrosBusqueda);
            
            return SUCCESS;
    }
    
    /**
     * @return
     * @throws ApplicationException 
     */
    @SuppressWarnings("unchecked")
	private List<UsuarioVO> obtenerUsuarios() throws ApplicationException {
        log.debug("-> ConfigOpcionMenuUsuarioAction.execute");
        List<UsuarioVO> theUsuarios = (List<UsuarioVO>) session.get("LISTA_USUARIOS_MENU");
        if (usuarios == null || usuarios.isEmpty()) {
            theUsuarios = opcMenuUsuarioManager.getUsuarios();
            session.put("LISTA_USUARIOS_MENU", theUsuarios);
        }
        return theUsuarios;
    }
    
    /**
     * @param menu 
     * @return
     * @throws ApplicationException 
     */
    private List<NivelMenuVO> obtieneNivelesMenu(String menu) throws ApplicationException {
        log.debug("-> ConfigOpcionMenuUsuarioAction.obtieneNivelesMenu : " + menu);
         
        return opcMenuUsuarioManager.getNivelMenu(menu);
    }
    /**
     * Method that load a new Configuration.    
     * @return
     * @throws Exception
     *//*
    @SuppressWarnings("unchecked")//Map Control.
    public String obtenComboCliente() throws Exception{
        log.debug("-> ConfigOpcionMenuUsuarioAction.obtenComboCliente");
        clientes = (ArrayList<ClienteVO>) session.get("LISTA_CLIENTES");
        log.debug("clientes session----->" + clientes);
        if (clientes != null && !clientes.isEmpty()) {
            success = true;
            return SUCCESS;
        } else {
            clientes = opcMenuUsuarioManager.getListaCliente();
            session.put("LISTA_CLIENTES", clientes);
        }
        log.debug("clientes----->" + clientes);
        success = true;
        return SUCCESS;
    }
    
    *//**
     * Method that load a new Configuration.    
     * @return
     * @throws Exception
     *//*
    @SuppressWarnings("unchecked")//Map Control.
    public String obtenComboUsuarios() throws Exception{
        log.debug("-> ConfigOpcionMenuUsuarioAction.obtenComboUsuarios");       
        usuarios = (ArrayList<UsuarioVO>) session.get("LISTA_USUARIOS_MENU");
        log.debug("usuarios session----->" + usuarios);
        
        if (usuarios != null && !usuarios.isEmpty()) {
            success = true;
            return SUCCESS;
        } else {
            usuarios = opcMenuUsuarioManager.getUsuarios();
            session.put("LISTA_USUARIOS_MENU", usuarios);
        }
        log.debug("usuarios----->" + usuarios);
        success = true;
        return SUCCESS;
    }*/
    
    /**
     * Method that load a new Configuration.    
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")//Map Control.
    public String obtenComboProductoCliente() throws Exception{
        productoCliente = obtenProductoClienteLista();
        log.debug("productoCliente: " + productoCliente);
        session.put("LISTA_PRODUCTO_CLIENTES", productoCliente);
        success = true;
        return SUCCESS;
    }
    
    /**
     * @return
     * @throws ApplicationException 
     */
    private ArrayList<ProductoClienteVO> obtenProductoClienteLista() throws ApplicationException {
        if (StringUtils.isBlank(cdElemento)) {
            cdElemento = (String) session.get("CD_ELEMENTO");
        }
        log.debug("Cargando combo de Productos : " + cdElemento);
        return opcMenuUsuarioManager.getProductoCliente(cdElemento);
    }
    /**
     * Method that load a new Configuration.    
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")//Map Control.
    public String obtenComboNivelMenu() throws Exception{
        if (StringUtils.isBlank(cdMenu)) {
            cdMenu = (String) session.get("CD_MENU");
        }
        log.debug("Cargando combo de Nivel Menu: " + cdMenu);
        nivelMenu = (ArrayList<NivelMenuVO>) obtieneNivelesMenu(cdMenu);
        log.debug("nivelMenu: " + nivelMenu);
        
        success = true;
        return SUCCESS;
    }
    
    /**
     * Metodo que obtiene una lista del objeto TipoVO
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String obtenComboTipo() throws Exception {
        log.debug("-> ConfigOpcionMenuUsuarioAction.getComboTipo");
        tipos = (ArrayList<TipoVO>) session.get("LISTA_TIPOS");
        if (log.isDebugEnabled()) {
            log.debug("tipos session-->" + tipos);
        }
        if (tipos != null && !tipos.isEmpty()) {
            success = true;
            return SUCCESS;
        }
        tipos = opcMenuUsuarioManager.getListaTipo("CTIPOPAGPRIN");
        session.put("LISTA_TIPOS", tipos);
        if (log.isDebugEnabled()) {
            log.debug("tipos-->" + tipos);
        }
        
        success = true;
        return SUCCESS;  
    }
    
    @SuppressWarnings("unchecked")
    public String obtenComboTipoMenu() throws Exception{
        log.debug("-> ConfigOpcionMenuUsuarioAction.obtenComboTipoMenu");
        tiposMenu = (ArrayList<TipoVO>) session.get("LISTA_TIPOS_MENU");
        if (log.isDebugEnabled()) {
            log.debug("tiposMenu session-->" + tiposMenu);
        }
        if (tiposMenu != null && !tiposMenu.isEmpty()) {
            success=true;
            return SUCCESS;
        }
        tiposMenu = opcMenuUsuarioManager.getListaTipo("TTIPOMENU");
        session.put("LISTA_TIPOS_MENU", tiposMenu);
        if (log.isDebugEnabled()) {
            log.debug("tiposMenu-->" + tiposMenu);
        }
        
        success=true;
        return SUCCESS;        
    }
    
    @SuppressWarnings("unchecked")
    public String obtenComboEstado() throws Exception{
        log.debug("-> ConfigOpcionMenuUsuarioAction.obtenComboEstado");
        estados = (ArrayList<TipoVO>) session.get("LISTA_ESTADOS");
        if (log.isDebugEnabled()) {
            log.debug("estados session-->" + estados);
        }
        if (estados != null && !estados.isEmpty()) {
            success=true;
            return SUCCESS;
        }
        estados = opcMenuUsuarioManager.getListaTipo("TACTIVO");
        session.put("LISTA_ESTADOS", estados);
        if (log.isDebugEnabled()) {
            log.debug("estados-->" + estados);
        }
        
        success=true;
        return SUCCESS;          
    }
    
    @SuppressWarnings("unchecked")
    public String obtenComboOpciones() throws Exception{
        if (log.isDebugEnabled()) {
            log.debug("-> ConfigOpcionMenuUsuarioAction.obtenComboOpciones");
        }
        opciones = (ArrayList<OpcionesVO>) session.get("LISTA_OPCIONES");
        //if (log.isDebugEnabled()) {
            //log.debug("opciones session-->" + opciones);
        //}
        if (opciones != null && !opciones.isEmpty()) {
            success = true;
            return SUCCESS;
        }
        opciones = opcMenuUsuarioManager.getOpciones();
        session.put("LISTA_OPCIONES", opciones);
        //if (log.isDebugEnabled()) {
            //log.debug("opciones-->" + opciones);
        //}
        
        success = true;
        return SUCCESS;          
    }
    
    /**
     * Metodo que obtiene una lista del objeto TipoVO
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String obtenerTipoSituacion() throws Exception {
        log.debug("Cargando Tipos de Situacion");
        log.debug("cdTituloCliente-->"+ cdTituloCliente);
        
        tipoSituacionList = opcMenuUsuarioManager.getListaTipoSituacion(cdTituloCliente);
        log.debug("tipoSituacionList-->" + tipoSituacionList);
        session.put("LISTA_TIPOS_SITUACION", tipoSituacionList);
        success = true;
        return SUCCESS;

    }
    
    @SuppressWarnings("unchecked")
    public String addMenu() throws Exception{
        log.debug("-> ConfigOpcionMenuUsuarioAction.addMenu"); 
        log.debug("cdElemento-->" + cdElemento);
        log.debug("cdMenu-->"+ cdMenu);
        log.debug("dsMenu-->"+ dsMenu);
        log.debug("dsPerson-->"+ dsPerson);
        log.debug("cdRol-->" + cdRol);
        log.debug("dsUsuario-->"+ dsUsuario);
        log.debug("opcionMenu-->"+ opcionMenu );
        log.debug("ramo-->" + dsTituloCliente);
        log.debug("dsTipoSituacion-->" + dsTipoSituacion);
        log.debug("opcionPadre-->" + dsMenuEst);
        log.debug("pantallaAsociada-->" + dsTitulo);
        log.debug("dsEstado-->" + dsEstado);
                
        ConfigOpcionMenuVO configOpcionMenuVo = new ConfigOpcionMenuVO();
        
        if (StringUtils.isBlank(cdMenu)) {
            cdMenu = (String) session.get("CD_MENU");
        }
        configOpcionMenuVo.setClaveMenu(cdMenu);
        configOpcionMenuVo.setNombreMenu(dsMenu);
        
        List<ClienteVO> cliente = (List<ClienteVO>) session.get("LISTA_CLIENTES");
        if (cliente != null && !cliente.isEmpty()) {
            for(ClienteVO clien : cliente){
                if(clien.getDescripcion().equals(dsPerson)){
                    configOpcionMenuVo.setClaveCliente(clien.getClaveCliente());
                }
            }    
        }
            
        List<UsuarioVO> usuarios = (List<UsuarioVO>) session.get("LISTA_USUARIOS_MENU");
        if (usuarios != null && !usuarios.isEmpty()) {
            for(UsuarioVO usuario : usuarios){
                if(usuario.getDsUsuario().equals(dsUsuario)){
                    configOpcionMenuVo.setClaveUsuario(usuario.getCdUsuario());
                }
            }    
        }
                        
        configOpcionMenuVo.setClaveRol(cdRol);
        configOpcionMenuVo.setOpcionMenu(opcionMenu);
        
        //configOpcionMenuVo.setRamo(hcdTituloCliente);        
        List<ProductoClienteVO> prodClien = (List<ProductoClienteVO>) session.get("LISTA_PRODUCTO_CLIENTES");
        if (prodClien != null && !prodClien.isEmpty()) {
            for(ProductoClienteVO prodCli : prodClien){
                if(prodCli.getDsTituloCliente().equals(dsTituloCliente)){
                    configOpcionMenuVo.setRamo(prodCli.getCdTituloCliente());
                }
            }
        }
        
        List<TipoVO> tipoSits = (List<TipoVO>) session.get("LISTA_TIPOS_SITUACION");
        if (tipoSits != null && !tipoSits.isEmpty()) {
            for(TipoVO tipoSit : tipoSits){
                if(tipoSit.getValor().equals(dsTipoSituacion)){
                    configOpcionMenuVo.setSituacion(tipoSit.getClave());
                }
            }
        }
    
        List<NivelMenuVO> nivMen = obtieneNivelesMenu(cdMenu);
        if (nivMen != null && !nivMen.isEmpty()) {
            for(NivelMenuVO nivMe : nivMen){
                if(nivMe.getDsMenuEst().equals(dsMenuEst)){
                    configOpcionMenuVo.setOpcionPadre(nivMe.getCdNivel());
                }
            }
        }
        
        List<OpcionesVO> opcs = (List<OpcionesVO>) session.get("LISTA_OPCIONES");
        if (opcs != null && !opcs.isEmpty()) {
            for(OpcionesVO opc : opcs){
                if(opc.getDsTitulo().equals(dsTitulo)){
                    configOpcionMenuVo.setPantallaAsociada(opc.getCdTitulo());
                }
            }
        }
        
        List<TipoVO> estadoList = (List<TipoVO>) session.get("LISTA_ESTADOS");
        if (estadoList != null && !estadoList.isEmpty()) {
            for(TipoVO edo : estadoList){
                if(edo.getValor().equals(dsEstado)){
                    configOpcionMenuVo.setEstado(edo.getClave());               
                }
            }
        }
        
        if (log.isDebugEnabled()) {
            log.debug("Guardar configOpcionMenuVo: " + configOpcionMenuVo);
        }
        mensajes = opcMenuUsuarioManager.agregarMenu(configOpcionMenuVo);
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
        
        success=true;
        return SUCCESS;
    }
    
    @SuppressWarnings("unchecked")
    public String editarMenu() throws Exception{
        if (log.isDebugEnabled()) {
            log.debug("-> ConfigOpcionMenuUsuarioAction.editarMenu");
            log.debug("cdMenu-->"+ cdMenu);
            log.debug("dsMenu-->"+ dsMenu);
            log.debug("dsPerson-->"+ dsPerson);
            log.debug("cdRol-->" + cdRol);
            log.debug("dsUsuario-->"+ dsUsuario);
            log.debug("dsTituloCliente-->" + dsTituloCliente);
            log.debug("dsTipoSituacion-->" + dsTipoSituacion);
            log.debug("dsTipsit-->" + dsTipsit);
            log.debug("opcionMenu-->"+ dsMenuEst );
            log.debug("cdNivel-->"+ cdNivel);
            log.debug("ramo-->" + dsTituloCliente);
            log.debug("opcionPadre-->" + opcionMenu);
            log.debug("pantallaAsociada-->" + dsTitulo);
            log.debug("estado-->" + dsEstado);
        }
                
        ConfigOpcionMenuVO configOpcionMenuVo= new ConfigOpcionMenuVO();
        
        configOpcionMenuVo.setClaveMenu(cdMenu);
        configOpcionMenuVo.setNombreMenu(dsMenu);
        configOpcionMenuVo.setClaveOpcionMenu(cdNivel);
        
        List<ClienteVO> cliente = (List<ClienteVO>) session.get("LISTA_CLIENTES");
        if (cliente != null && !cliente.isEmpty()) {
            for(ClienteVO clien : cliente){
                if(clien.getDescripcion().equals(dsPerson)){
                    configOpcionMenuVo.setClaveCliente(clien.getClaveCliente());
                }
            }    
        }
            
        List<UsuarioVO> usuarios = (List<UsuarioVO>) session.get("LISTA_USUARIOS_MENU");
        if (usuarios != null && !usuarios.isEmpty()) {
            for(UsuarioVO usuario : usuarios){
                if(usuario.getDsUsuario().equals(dsUsuario)){
                    configOpcionMenuVo.setClaveUsuario(usuario.getCdUsuario());
                }
            }    
        }
                
        configOpcionMenuVo.setClaveRol(cdRol);
        configOpcionMenuVo.setOpcionMenu(dsMenuEst);
        
        //configOpcionMenuVo.setRamo(hcdTituloCliente);        
        List<ProductoClienteVO> prodClien = (List<ProductoClienteVO>) session.get("LISTA_PRODUCTO_CLIENTES");
        if (prodClien != null && !prodClien.isEmpty()) {
            for(ProductoClienteVO prodCli : prodClien){
                if(prodCli.getDsTituloCliente().equals(dsTituloCliente)){
                    configOpcionMenuVo.setRamo(prodCli.getCdTituloCliente());
                }
            }
        }
        
        List<TipoVO> tipoSits = (List<TipoVO>)session.get("LISTA_TIPOS_SITUACION");
        if (tipoSits != null && !tipoSits.isEmpty()) {
            for(TipoVO tipoSit : tipoSits){
                if(tipoSit.getValor().equals(dsTipsit)){
                    configOpcionMenuVo.setSituacion(tipoSit.getClave());
                }
            }
        }
    
        List<NivelMenuVO> nivMen = obtieneNivelesMenu(cdMenu);
        if (log.isDebugEnabled()) {
            log.debug("-> nivMen : " + nivMen);
        }
        if (nivMen != null && !nivMen.isEmpty()) {
            for(NivelMenuVO nivMe : nivMen){
                if(nivMe.getDsMenuEst().equals(opcionMenu)){
                    configOpcionMenuVo.setOpcionPadre(nivMe.getCdNivel());
                }
            }
        }
        
        List<OpcionesVO> opcs = (List<OpcionesVO>) session.get("LISTA_OPCIONES");
        if (opcs != null && !opcs.isEmpty()) {
            for(OpcionesVO opc : opcs){
                if(opc.getDsTitulo().equals(dsTitulo)){
                    configOpcionMenuVo.setPantallaAsociada(opc.getCdTitulo());
                }
            }
        }
        
        List<TipoVO> estadoList = (List<TipoVO>)session.get("LISTA_ESTADOS");
        if (estadoList != null && !estadoList.isEmpty()) {
            for(TipoVO edo : estadoList){
                if(edo.getValor().equals(dsEstado)){
                    configOpcionMenuVo.setEstado(edo.getClave());               
                }
            }
        }
        
        if (log.isDebugEnabled()) {
            log.debug("Editar configOpcionMenuVo : " + configOpcionMenuVo);
        }
        mensajes = opcMenuUsuarioManager.agregarMenu(configOpcionMenuVo);
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
        
        success = true;
        
        return SUCCESS;     
    }
    
    /**
     * configurarMenu
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public String configurarMenu() throws Exception{
        if (log.isDebugEnabled()) {
            log.debug("-> ConfigOpcionMenuUsuarioAction.configurarMenu");
            log.debug("cdMenu-->"+ cdMenu);
            log.debug("dsMenu-->"+ dsMenu);
            log.debug("dsPerson-->"+ dsPerson);
            log.debug("cdRol-->" + cdRol);
            log.debug("dsUsuario-->"+ dsUsuario);
            log.debug("opcionMenu-->"+ opcionMenu );
            log.debug("ramo-->" + ramo);
            log.debug("opcionPadre-->" + opcionPadre);
            log.debug("pantallaAsociada-->" + pantallaAsociada);
            log.debug("estado-->" + dsEstado);
        }
                
        ConfigOpcionMenuVO configOpcionMenuVo= new ConfigOpcionMenuVO();
        
        configOpcionMenuVo.setClaveMenu(cdMenu);
        configOpcionMenuVo.setNombreMenu(dsMenu);
        
        List<ClienteVO> cliente = (List<ClienteVO>)session.get("LISTA_CLIENTES");
        for(ClienteVO clien : cliente){
            if(clien.getDescripcion().equals(dsPerson)){
                configOpcionMenuVo.setClaveCliente(clien.getClaveCliente());
            }
        }
    
        List<UsuarioVO> usuarios = (List<UsuarioVO>) session.get("LISTA_USUARIOS_MENU");
        for(UsuarioVO usuario : usuarios){
            if(usuario.getDsUsuario().equals(dsUsuario)){
                configOpcionMenuVo.setClaveUsuario(usuario.getCdUsuario());
            }
        }
        
        configOpcionMenuVo.setClaveRol(cdRol);
        configOpcionMenuVo.setOpcionMenu(opcionMenu);
        configOpcionMenuVo.setRamo(ramo);
        configOpcionMenuVo.setOpcionPadre(opcionPadre);
        configOpcionMenuVo.setPantallaAsociada(pantallaAsociada);
        
        List<TipoVO> estadoList = (List<TipoVO>)session.get("LISTA_ESTADOS");
        for(TipoVO edo : estadoList){
            if(edo.getValor().equals(dsEstado)){
                configOpcionMenuVo.setEstado(edo.getClave());               
            }
        }
        
        if (log.isDebugEnabled()) {
            log.debug("Editar configOpcionMenuVo : " + configOpcionMenuVo);
        }
        opcMenuUsuarioManager.configurarOpcionMenu(configOpcionMenuVo);
        
        success = true;
        
        return SUCCESS;     
    }
    
    public String getDsRol() {
        return dsRol;
    }
    
    public void setDsRol(String dsRol) {
        this.dsRol = dsRol;
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
    public ArrayList<RolesVO> getRolesLista() {
        return rolesLista;
    }
    public void setRolesLista(ArrayList<RolesVO> rolesLista) {
        this.rolesLista = rolesLista;
    }
    public ArrayList<SeccionVO> getSecciones() {
        return secciones;
    }
    public void setSecciones(ArrayList<SeccionVO> secciones) {
        this.secciones = secciones;
    }
    public ArrayList<ClienteVO> getClientes() {
        return clientes;
    }
    public void setClientes(ArrayList<ClienteVO> clientes) {
        this.clientes = clientes;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
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
     * @param productoCliente the productoCliente to set
     */
    public void setProductoCliente(ArrayList<ProductoClienteVO> productoCliente) {
        this.productoCliente = productoCliente;
    }
    /**
     * @return the configOpcionMenuVo
     */
    public ConfigOpcionMenuVO getConfigOpcionMenuVo() {
        return configOpcionMenuVo;
    }
    /**
     * @param configOpcionMenuVo the configOpcionMenuVo to set
     */
    public void setConfigOpcionMenuVo(ConfigOpcionMenuVO configOpcionMenuVo) {
        this.configOpcionMenuVo = configOpcionMenuVo;
    }
    /**
     * @param opcMenuUsuarioManager the opcMenuUsuarioManager to set
     */
    public void setOpcMenuUsuarioManager(OpcMenuUsuarioManager opcMenuUsuarioManager) {
        this.opcMenuUsuarioManager = opcMenuUsuarioManager;
    }
    /**
     * @return the opcionMenuList
     */
    public List<OpcionMenuVO> getOpcionMenuList() {
        return opcionMenuList;
    }
    /**
     * @param opcionMenuList the opcionMenuList to set
     */
    public void setOpcionMenuList(List<OpcionMenuVO> opcionMenuList) {
        this.opcionMenuList = opcionMenuList;
    }
    /**
     * @return the menuList
     */
    public ArrayList<MenuVO> getMenuList() {
        return menuList;
    }
    /**
     * @param menuList the menuList to set
     */
    public void setMenuList(ArrayList<MenuVO> menuList) {
        this.menuList = menuList;
    }
    /**
     * @return the dsTituloCliente
     */
    public String getDsTituloCliente() {
        return dsTituloCliente;
    }
    /**
     * @param dsTituloCliente the dsTituloCliente to set
     */
    public void setDsTituloCliente(String dsTituloCliente) {
        this.dsTituloCliente = dsTituloCliente;
    }
    /**
     * @return the opciones
     */
    public ArrayList<OpcionesVO> getOpciones() {
        return opciones;
    }
    /**
     * @param opciones the opciones to set
     */
    public void setOpciones(ArrayList<OpcionesVO> opciones) {
        this.opciones = opciones;
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
     * @param nivelMenu the nivelMenu to set
     */
    public void setNivelMenu(ArrayList<NivelMenuVO> nivelMenu) {
        this.nivelMenu = nivelMenu;
    }
    /**
     * @return the dsMenuEst
     */
    public String getDsMenuEst() {
        return dsMenuEst;
    }
    /**
     * @param dsMenuEst the dsMenuEst to set
     */
    public void setDsMenuEst(String dsMenuEst) {
        this.dsMenuEst = dsMenuEst;
    }
    /**
     * @return the cdNivel
     */
    public String getCdNivel() {
        return cdNivel;
    }
    /**
     * @param cdNivel the cdNivelPadre to set
     */
    public void setCdNivel(String cdNivel) {
        this.cdNivel = cdNivel;
    }
    /**
     * @return the productoCliente
     */
    public ArrayList<ProductoClienteVO> getProductoCliente() {
        return productoCliente;
    }
    /**
     * @return the nivelMenu
     */
    public ArrayList<NivelMenuVO> getNivelMenu() {
        return nivelMenu;
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
     * @return the cdUsuario
     */
    public String getCdUsuario() {
        return cdUsuario;
    }
    /**
     * @param cdUsuario the cdUsuario to set
     */
    public void setCdUsuario(String cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    /**
     * @return the tipoSituacionList
     */
    public ArrayList<TipoVO> getTipoSituacionList() {
        return tipoSituacionList;
    }

    /**
     * @param tipoSituacionList the tipoSituacionList to set
     */
    public void setTipoSituacionList(ArrayList<TipoVO> tipoSituacionList) {
        this.tipoSituacionList = tipoSituacionList;
    }

    /**
     * @return the dsTipoSituacion
     */
    public String getDsTipoSituacion() {
        return dsTipoSituacion;
    }

    /**
     * @param dsTipoSituacion the dsTipoSituacion to set
     */
    public void setDsTipoSituacion(String dsTipoSituacion) {
        this.dsTipoSituacion = dsTipoSituacion;
    }

    /**
     * @return the comboTest
     */
    public String getComboTest() {
        return comboTest;
    }

    /**
     * @param comboTest the comboTest to set
     */
    public void setComboTest(String comboTest) {
        this.comboTest = comboTest;
    }

    /**
     * @return the cdTituloCliente
     */
    public String getCdTituloCliente() {
        return cdTituloCliente;
    }

    /**
     * @param cdTituloCliente the cdTituloCliente to set
     */
    public void setCdTituloCliente(String cdTituloCliente) {
        this.cdTituloCliente = cdTituloCliente;
    }

    /**
     * @return the dsTipsit
     */
    public String getDsTipsit() {
        return dsTipsit;
    }

    /**
     * @param dsTipsit the dsTipsit to set
     */
    public void setDsTipsit(String dsTipsit) {
        this.dsTipsit = dsTipsit;
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
	 * @param catalogManager the catalogManager to set
	 */
	public void setCatalogManager(CatalogService catalogManager) {
		this.catalogManager = catalogManager;
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
	 * @return the parametrosBusqueda
	 */
	public Map<String, String> getParametrosBusqueda() {
		return parametrosBusqueda;
	}

	/**
	 * @param parametrosBusqueda the parametrosBusqueda to set
	 */
	public void setParametrosBusqueda(Map<String, String> parametrosBusqueda) {
		this.parametrosBusqueda = parametrosBusqueda;
	}

	public String getExito() {
		return exito;
	}

	public void setExito(String exito) {
		this.exito = exito;
	}

	public String getCdTipoMenu() {
		return cdTipoMenu;
	}

	public void setCdTipoMenu(String cdTipoMenu) {
		this.cdTipoMenu = cdTipoMenu;
	}

	public String getDsTipoMenu() {
		return dsTipoMenu;
	}

	public void setDsTipoMenu(String dsTipoMenu) {
		this.dsTipoMenu = dsTipoMenu;
	}
}