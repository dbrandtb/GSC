/**
 * 
 */
package mx.com.aon.portal.web.menuusuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.model.menuusuario.ConfiguracionMenuVO;
import mx.com.aon.portal.model.opcmenuusuario.UsuarioVO;
import mx.com.aon.portal.model.principal.ClienteVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.menuusuario.MenuUsuarioManager;

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
public class MenuUsuarioAction extends ActionSupport implements SessionAware{

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -6514271768322006648L;
    private static final transient Log log= LogFactory.getLog(MenuUsuarioAction.class);
    @SuppressWarnings({ "unchecked", "unused" })
    private Map session;
    private boolean success;
    
    private String id;
    
    private String cdMenu;
    private String dsElemen;
    private String dsRol;
    private String cliente;
    private String rolCopy;
    private String idCliente;
    private String idRol;
    private String idClienteNvo;
    private String idRolNvo;
    
    private String dsMenuCopia;
    private String dsElementoCopia;
    private String dsUsuarioCopia;
    private String cdRolCopia;
    
    private MensajesVO mensajes;
    private String mensajeRespuesta;
            
    private MenuUsuarioManager menuUsuarioManager;
    private CatalogService catalogManager; 
    
    /**
     * metodo que se encarga de borrar una configuracion de un Menu.
     * @return
     * @throws Exception
     */
    public String borrarMenu() throws Exception{
        log.debug("-> OpcionMenuUsuarioAction.borrarMenu");
        log.debug("cdMenu : " + cdMenu);
        if(StringUtils.isNotBlank(cdMenu)){
        	
            mensajes = menuUsuarioManager.borrarConfigMenu(cdMenu);
            
            //Obtenemos el mensaje que se mostrara al usuario dependiendo lo obtenido en 'mensajes' y lo almacenamos en mensajeRespuesta
            Map<String, String> params = new HashMap<String, String>();
            params.put("msg", mensajes.getMsgId());
            params.put("log", "");
            params.put("cdusuario", "");
            params.put("dsprograma", "menuUsuario");
            mensajes = null;
            mensajes = catalogManager.getMensajes(params, "OBTIENE_MENSAJES");
            log.debug("mensaje deRespuesta=" + mensajes.getMsgText());
            mensajeRespuesta = mensajes.getMsgText();
            
            success=true;
        }else{
            success=false;
        }
        return SUCCESS;
        
    }
    /**
     * metodo que se encarga de copiar una configuracion.
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String copiarMenu() throws Exception{
        
        log.debug("cdMenu-->"+cdMenu);
        log.debug("dsMenuCopia-->"+dsMenuCopia);
        log.debug("dsElementoCopia-->"+ dsElementoCopia);
        log.debug("dsUsuarioCopia-->"+dsUsuarioCopia);
        log.debug("cdRolCopia-->"+ cdRolCopia);
        
        ConfiguracionMenuVO configuracionMenuVo = new ConfiguracionMenuVO();
        
        List<ClienteVO> cliente = (List<ClienteVO>)ActionContext.getContext().getSession().get("LISTA_CLIENTES");
        for(ClienteVO clien : cliente){
            if(clien.getDescripcion().equals(dsElementoCopia)){
                configuracionMenuVo.setClaveCliente(clien.getClaveCliente());
            }
        }
    
        List<UsuarioVO> usuarios = (List<UsuarioVO>) ActionContext.getContext().getSession().get("LISTA_USUARIOS_MENU");
        for(UsuarioVO usuario : usuarios){
            if(usuario.getDsUsuario().equals(dsUsuarioCopia)){
                configuracionMenuVo.setClaveUsuario(usuario.getCdUsuario());
            }
        }
        
        configuracionMenuVo.setClaveMenu(cdMenu);
        configuracionMenuVo.setNombreMenu(dsMenuCopia);
        configuracionMenuVo.setClaveRol(cdRolCopia);
        
        log.debug("configuracionMenuVo --->" + configuracionMenuVo);
        
        mensajes = menuUsuarioManager.copiarConfigMenu(configuracionMenuVo);
        
        //Obtenemos el mensaje que se mostrara al usuario dependiendo lo obtenido en 'mensajes' y lo almacenamos en mensajeRespuesta
        Map<String, String> params = new HashMap<String, String>();
        params.put("msg", mensajes.getMsgId());
        params.put("log", "");
        params.put("cdusuario", "");
        params.put("dsprograma", "menuUsuario");
        mensajes = null;
        mensajes = catalogManager.getMensajes(params, "OBTIENE_MENSAJES");
        log.debug("mensaje deRespuesta=" + mensajes.getMsgText());
        mensajeRespuesta = mensajes.getMsgText();
        
        success=true;
        
        return SUCCESS;     
    }
    
    
    
    /**
     * Metodo utilizado por spring
     * @param catalogManager
     */
    public void setCatalogManager(CatalogService catalogManager) {
		this.catalogManager = catalogManager;
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
    public String getDsElemen() {
        return dsElemen;
    }
    /**
     * 
     * @param dsElemen
     */
    public void setDsElemen(String dsElemen) {
        this.dsElemen = dsElemen;
    }
    /**
     * 
     * @return
     */
    public String getDsRol() {
        return dsRol;
    }
    /**
     * 
     * @param dsRol
     */
    public void setDsRol(String dsRol) {
        this.dsRol = dsRol;
    }
    /**
     * 
     * @return
     */
    public String getCliente() {
        return cliente;
    }
    /**
     * 
     * @param cliente
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    /**
     * 
     * @return
     */
    public String getRolCopy() {
        return rolCopy;
    }
    /**
     * 
     * @param rolCopy
     */
    public void setRolCopy(String rolCopy) {
        this.rolCopy = rolCopy;
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
     * @param session
     */
    @SuppressWarnings("unchecked")
    public void setSession(Map session) {
        this.session = session;
    }
    /**
     * @param menuUsuarioManager the menuUsuarioManager to set
     */
    public void setMenuUsuarioManager(MenuUsuarioManager menuUsuarioManager) {
        this.menuUsuarioManager = menuUsuarioManager;
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
     * @return the cdRolCopia
     */
    public String getCdRolCopia() {
        return cdRolCopia;
    }
    /**
     * @param cdRolCopia the cdRolCopia to set
     */
    public void setCdRolCopia(String cdRolCopia) {
        this.cdRolCopia = cdRolCopia;
    }
    /**
     * @return the dsElementoCopia
     */
    public String getDsElementoCopia() {
        return dsElementoCopia;
    }
    /**
     * @param dsElementoCopia the dsElementoCopia to set
     */
    public void setDsElementoCopia(String dsElementoCopia) {
        this.dsElementoCopia = dsElementoCopia;
    }
    /**
     * @return the dsMenuCopia
     */
    public String getDsMenuCopia() {
        return dsMenuCopia;
    }
    /**
     * @param dsMenuCopia the dsMenuCopia to set
     */
    public void setDsMenuCopia(String dsMenuCopia) {
        this.dsMenuCopia = dsMenuCopia;
    }
    /**
     * @return the dsUsuarioCopia
     */
    public String getDsUsuarioCopia() {
        return dsUsuarioCopia;
    }
    /**
     * @param dsUsuarioCopia the dsUsuarioCopia to set
     */
    public void setDsUsuarioCopia(String dsUsuarioCopia) {
        this.dsUsuarioCopia = dsUsuarioCopia;
    }
	public MensajesVO getMensajes() {
		return mensajes;
	}
	public void setMensajes(MensajesVO mensajes) {
		this.mensajes = mensajes;
	}
	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}
	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}
	
}
