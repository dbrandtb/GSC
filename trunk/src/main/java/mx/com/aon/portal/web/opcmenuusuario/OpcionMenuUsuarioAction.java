/**
 * 
 */
package mx.com.aon.portal.web.opcmenuusuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.model.principal.ClienteVO;
import mx.com.aon.portal.model.principal.RolesVO;
import mx.com.aon.portal.service.CatalogService;
import mx.com.aon.portal.service.opcmenuusuario.OpcMenuUsuarioManager;

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
public class OpcionMenuUsuarioAction extends ActionSupport implements SessionAware{

    /**
     * Serial Version
     */
    private static final long serialVersionUID = -6514271768322006648L;
    private static final transient Log log= LogFactory.getLog(OpcionMenuUsuarioAction.class);
    @SuppressWarnings({ "unchecked", "unused" })
    private Map session;
    private boolean success;
    
    private String id;
    
    private String cdMenu;
    private String cdNivel;
    private String dsElemen;
    private String dsRol;
    private String cliente;
    private String rolCopy;
    private String idCliente;
    private String idRol;
    private String idClienteNvo;
    private String idRolNvo;
    
    private MensajesVO mensajes;
    private String mensajeRespuesta;
    
    private OpcMenuUsuarioManager opcMenuUsuarioManager;
    private CatalogService catalogManager;
    
    /**
     * metodo que se encarga de borrar una configuracion de un Menu.
     * @return
     * @throws Exception
     */
    public String borrarOpcionMenu() throws Exception{
        log.debug("-> OpcionMenuUsuarioAction.borrarOpcionMenu");
        log.debug(":: cdMenu : " + cdMenu);
        log.debug(":: cdNivel : " + cdNivel);
        if(StringUtils.isNotBlank(cdMenu) && StringUtils.isNotBlank(cdNivel)){
            mensajes = opcMenuUsuarioManager.borrarConfigOpcionMenu(cdMenu, cdNivel);
            
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
    public String copiarRoles() throws Exception{
        
        log.debug("dsElemen-->"+dsElemen);
        log.debug("dsRol-->"+ dsRol);
        log.debug("dsSeccion-->"+cliente);
        log.debug("dsTipo-->"+ rolCopy);
        
        List<ClienteVO> clienteUno = (List<ClienteVO>)ActionContext.getContext().getSession().get("LISTA_CLIENTES");
        for(ClienteVO clien : clienteUno){
            if(clien.getDescripcion().equals(dsElemen)){
                idCliente = clien.getClaveCliente();
            }else{
                success=false;
            }
        }
        List<RolesVO> rolesList = (List<RolesVO>)ActionContext.getContext().getSession().get("LISTA_ROLES"); 
        for(RolesVO ro : rolesList){
            if(ro.getDsRol().equals(dsRol)){
                idRol=ro.getCdRol();
            }else{
                success=false;
            }
        }
        List<ClienteVO> client = (List<ClienteVO>)ActionContext.getContext().getSession().get("LISTA_CLIENTES");
        for(ClienteVO clienVal : client){
            if(clienVal.getDescripcion().equals(cliente)){
                idClienteNvo=clienVal.getClaveCliente();
            }else{
                success=false;
            }
        }
        
        List<RolesVO> rolList = (List<RolesVO>) ActionContext.getContext().getSession().get("LISTA_ROLES");
        for(RolesVO ro : rolList){
            if(ro.getDsRol().equals(rolCopy)){
                idRolNvo=ro.getCdRol();
            }else{
                success=false;
            }
        }
        log.debug("se asignan id para los atributos");
        log.debug("idCliente--" + idCliente );
        log.debug("idRol--" + idRol);
        log.debug("idClienteNvo--"+idClienteNvo);
        log.debug("idRolNvo--" + idRolNvo);
        
        opcMenuUsuarioManager.copiarConfig(idCliente, idRol, idClienteNvo,  idRolNvo);
        
        success=true;
        
        return SUCCESS;     
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
     * @param opcMenuUsuarioManager the opcMenuUsuarioManager to set
     */
    public void setOpcMenuUsuarioManager(OpcMenuUsuarioManager opcMenuUsuarioManager) {
        this.opcMenuUsuarioManager = opcMenuUsuarioManager;
    }
    
    /**
     * Metodo utilizado por spring
     * @param catalogManager
     */
    public void setCatalogManager(CatalogService catalogManager) {
		this.catalogManager = catalogManager;
	}
	/**
     * @return the cdNivel
     */
    public String getCdNivel() {
        return cdNivel;
    }
    /**
     * @param cdNivel the cdNivel to set
     */
    public void setCdNivel(String cdNivel) {
        this.cdNivel = cdNivel;
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
