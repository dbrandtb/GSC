/**
 * 
 */
package mx.com.aon.portal.service.menuusuario;

import java.util.ArrayList;
import java.util.Map;

import mx.com.aon.portal.model.MensajesVO;
import mx.com.aon.portal.model.menuusuario.ConfiguracionMenuVO;
import mx.com.aon.portal.model.menuusuario.MenuVO;
import mx.com.aon.portal.model.opcmenuusuario.UsuarioVO;
import mx.com.aon.portal.model.principal.ClienteVO;
import mx.com.aon.portal.model.principal.RolVO;
import mx.com.aon.portal.model.principal.RolesVO;
import mx.com.aon.portal.model.principal.SeccionVO;
import mx.com.aon.portal.model.principal.TipoVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.gseguros.exception.ApplicationException;

/**
 * @author eflores
 * @date 20/05/2008
 *
 */
public interface MenuUsuarioManager {
    /**
     * 
     * @param nombre
     * @param cliente
     * @param rol
     * @param usuario
     * @return
     * @throws ApplicationException
     */
    public PagedList getMenuUsuarios(String nombre, String cliente, String rol, String usuario,int start,int limit) throws ApplicationException;
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
    public ArrayList<UsuarioVO> getUsuarios() throws ApplicationException;
    
    /**
     * @param cdElemento
     * @param cdRol
     * @return
     * @throws ApplicationException
     */
    public ArrayList<UsuarioVO> getUsuariosNR(String cdElemento, String cdRol) throws ApplicationException;
    /**
     * 
     * @return
     * @throws ApplicationException
     */
    public ArrayList<SeccionVO> getListaSeccion() throws ApplicationException;
    /**
     * 
     * @return
     * @throws ApplicationException
     */
    public ArrayList<RolesVO> getListaRol()throws ApplicationException;
    /**
     * 
     * @param tabla
     * @return
     * @throws ApplicationException
     */
    public ArrayList<TipoVO> getListaTipo(String tabla)throws ApplicationException;
    /**
     * 
     * @param configuracionMenuVo
     * @throws ApplicationException
     */
    public MensajesVO agregarconfiguracion(ConfiguracionMenuVO configuracionMenuVo)throws ApplicationException;
    /**
     * 
     * @param ConfiguracionMenuVO configuracionMenuVo
     * @throws ApplicationException
     */
    public MensajesVO copiarConfigMenu(ConfiguracionMenuVO configuracionMenuVo) throws ApplicationException;
    /**
     * 
     * @param id
     * @throws ApplicationException
     */
    public MensajesVO borrarConfigMenu(String id)throws ApplicationException; 
    
    /**
     * 
     * @param params
     * @param EndpointName
     * @param start
     * @param limit
     * @throws ApplicationException
     */
    public PagedList getPagedList(Map<String, String> params, String EndPointName, int start, int limit) throws ApplicationException;

}
