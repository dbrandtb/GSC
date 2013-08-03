/**
 * 
 */
package mx.com.aon.portal.service.opcmenuusuario;

import java.util.ArrayList;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.model.menuusuario.MenuVO;
import mx.com.aon.portal.model.opcmenuusuario.ConfigOpcionMenuVO;
import mx.com.aon.portal.model.opcmenuusuario.NivelMenuVO;
import mx.com.aon.portal.model.opcmenuusuario.OpcionesVO;
import mx.com.aon.portal.model.opcmenuusuario.ProductoClienteVO;
import mx.com.aon.portal.model.opcmenuusuario.UsuarioVO;
import mx.com.aon.portal.model.principal.ClienteVO;
import mx.com.aon.portal.model.principal.RolVO;
import mx.com.aon.portal.model.principal.TipoVO;
import mx.com.aon.portal.service.PagedList;

/**
 * @author eflores
 * @date 22/05/2008
 *
 */
public interface OpcMenuUsuarioManager {
    /**
     * 
     * @param nombre
     * @param cliente
     * @param rol
     * @param usuario
     * @return
     * @throws ApplicationException
     */
    public ArrayList<MenuVO> obtieneMenu(String cdMenu) throws ApplicationException;
    /**
     * 
     * @param nombre
     * @param cliente
     * @param rol
     * @param usuario
     * @return Lista paginada con opciones del Menu
     * @throws ApplicationException
     */
    public PagedList obtieneOpcionesMenu(Map<String,String> params, int start, int limit) throws ApplicationException;
    /**
     * 
     * @param rol
     * @param cliente
     * @param seccion
     * @return
     * @throws ApplicationException
     */
    public ArrayList<RolVO> getRoles(String rol, String cliente, String seccion) throws ApplicationException;
    /**
     * 
     * @return
     * @throws ApplicationException
     */
    public ArrayList<ClienteVO> getListaCliente() throws ApplicationException;
    
    /**
     * 
     * @return
     * @throws ApplicationException
     */
    public ArrayList<OpcionesVO> getOpciones() throws ApplicationException;
    
    /**
     * 
     * @return
     * @throws ApplicationException
     */
    public ArrayList<NivelMenuVO> getNivelMenu(String cdMenu) throws ApplicationException;
    
    /**
     * 
     * @return
     * @throws ApplicationException
     */
    public ArrayList<UsuarioVO> getUsuarios() throws ApplicationException;
    
    /**
     * 
     * @return
     * @throws ApplicationException
     */
    public ArrayList<ProductoClienteVO> getProductoCliente(String cdElemento) throws ApplicationException;
        
    /**
     * 
     * @param tabla
     * @return
     * @throws ApplicationException
     */
    public ArrayList<TipoVO> getListaTipo(String tabla)throws ApplicationException;
    
    /**
     * 
     * @param tabla
     * @return
     * @throws ApplicationException
     */
    public ArrayList<TipoVO> getListaTipoSituacion(String idRamo)throws ApplicationException;
    /**
     * 
     * @param configuracionMenuVo
     * @throws ApplicationException
     */
    public MensajesVO agregarMenu(ConfigOpcionMenuVO configOpcionMenuVO)throws ApplicationException;
    /**
     * 
     * @param configuracionMenuVo
     * @throws ApplicationException
     */
    public void configurarOpcionMenu(ConfigOpcionMenuVO configOpcionMenuVO)throws ApplicationException;
    /**
     * 
     * @param idCliente
     * @param idRol
     * @param idClienteNvo
     * @param idRolNvo
     * @throws ApplicationException
     */
    public void copiarConfig(String idCliente,  String idRol, String idClienteNvo, String idRolNvo) throws ApplicationException;
    /**
     * 
     * @param cdMenu
     * @param cdNivel
     * @throws ApplicationException
     */
    public MensajesVO borrarConfigOpcionMenu(String cdMenu, String cdNivel)throws ApplicationException; 
 
}

