package mx.com.aon.portal.service.impl.principal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.principal.ClienteVO;
import mx.com.aon.portal.model.principal.ConfiguracionVO;
import mx.com.aon.portal.model.principal.PaginaVO;
import mx.com.aon.portal.model.principal.RolVO;
import mx.com.aon.portal.model.principal.RolesVO;
import mx.com.aon.portal.model.principal.SeccionVO;
import mx.com.aon.portal.model.principal.TipoVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.service.principal.PrincipalManager;
import mx.com.aon.portal.util.WrapperResultados;
/**
 * 
 * @author sergio.ramirez
 *
 */
public class PrincipalManagerImpl extends AbstractManager implements PrincipalManager {
	private static Logger logger = Logger.getLogger(PrincipalManagerImpl.class);

	/**
	 * Metodo que regresa la lista de configuraciones existentes en la base de Datos.
	 */
	@SuppressWarnings("unchecked")// Map Control
	//public ArrayList<RolVO> getRoles(String claveNombre, String rol, String cliente, String seccion) throws ApplicationException {
	public PagedList getRoles(String claveNombre, String rol, String cliente, String seccion, int start, int limit) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("nombre", claveNombre);
		params.put("rol", rol);
		params.put("cliente", cliente);
		params.put("seccion", seccion);
		//ArrayList<RolVO> roles = null;
		
		return pagedBackBoneInvoke(params, "PRINCIPAL_OBTIENE_ROLES", start, limit);
		//try {
			//Endpoint endpoint = (Endpoint) endpoints.get("PRINCIPAL_OBTIENE_ROLES");
			//roles = (ArrayList<RolVO>) pagedBackBoneInvoke(params, "PRINCIPAL_OBTIENE_ROLES", 0, 999);
			//roles = (ArrayList<RolVO>) endpoint.invoke(params);
		//} catch (BackboneApplicationException e) {
			//logger.error("Backbone exception", e);
			//throw new ApplicationException("Error al recuperar los datos");

		//}
		//return (ArrayList<RolVO>) roles;
	}
	/**
	 * Metodo que regresa lista de clientes existentes en la base.
	 */
	@SuppressWarnings("unchecked")// Map Control
	public ArrayList<ClienteVO> getListaCliente() throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_CLIENTES");
		ArrayList<ClienteVO> clientes = null;
		try {
			clientes = (ArrayList<ClienteVO>) endpoint.invoke(null);
			if (clientes != null && !clientes.isEmpty()) {
				logger.debug("lista de clientes llena");
			} else {
				logger.debug("lista de clientes vacia");
			}
		} catch (BackboneApplicationException e) {
			throw new ApplicationException("Error regresando lista de Cliente");
		}
		return (ArrayList<ClienteVO>) clientes;
	}
	/**
	 * Metodo que regresa lista de Secciones existentes en la base.
	 */
	@SuppressWarnings("unchecked")// Map Control
	public ArrayList<SeccionVO> getListaSeccion() throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_SECCIONES");
		ArrayList<SeccionVO> secciones = null;
		try {
			secciones = (ArrayList<SeccionVO>) endpoint.invoke(null);
			if (secciones != null && !secciones.isEmpty()) {
				logger.debug("lista de secciones llena");
			} else {
				logger.debug("lista de Secciones vacia");
			}
		} catch (BackboneApplicationException e) {
			throw new ApplicationException(
					"Error regresando lista de Secciones");
		}
		return (ArrayList<SeccionVO>) secciones;
	}

	/**
	 * Metodo que regresa lista de Roles existentes en la base.
	 */
	@SuppressWarnings("unchecked")// Map Control
	public ArrayList<RolesVO> getListaRol() throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("CARGA_ROLES");
		ArrayList<RolesVO> rolesLista = null;
		try {
			rolesLista = (ArrayList<RolesVO>) endpoint.invoke(null);
			if (rolesLista != null && !rolesLista.isEmpty()) {
				logger.debug("Lista de roles Llena");
			} else {
				logger.debug("Lista de roles vacia");
			}
		} catch (BackboneApplicationException e) {
			throw new ApplicationException("Error regresando lista de Roles");
		}
		return (ArrayList<RolesVO>) rolesLista;
	}
	/**
	 *Metodo que regresa lista de tipos existentes en la base de datos.
	 */
	@SuppressWarnings("unchecked")//Map Control
	public ArrayList<TipoVO> getListaTipo(String tabla)throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("tabla", tabla);
		ArrayList<TipoVO> tipos = null;
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_TIPOS_PAG");
			tipos = (ArrayList<TipoVO>) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			logger.debug("Error al recuperar los datos");
			throw new ApplicationException("Error regresando lista Tipos");
		}
		return (ArrayList<TipoVO>) tipos;
	}
	/**
	 * Metodo que se encarga de agregar una nueva configuracion a la base.
	 */
	public void agregarconfiguracion(ConfiguracionVO configuracionVo)
			throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_CONFIGURACION");
		try {
			endpoint.invoke(configuracionVo);
		} catch (BackboneApplicationException e) {
			throw new ApplicationException("Error insertando una nueva configuracion");
		}
	}
	
	@SuppressWarnings("unchecked")
	public String  agregarNuevaConfiguracion(ConfiguracionVO configuracionVo) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("claveConfig",configuracionVo.getClaveConfig());
		map.put("descripcionConfig",configuracionVo.getDescripcionConfig());
		map.put("claveElemento",configuracionVo.getClaveElemento());
		map.put("claveRol",configuracionVo.getClaveRol());
		map.put("claveSeccion",configuracionVo.getClaveSeccion());
		map.put("swHabilitado",configuracionVo.getSwHabilitado());
		map.put("especificacion",configuracionVo.getEspecificacion());
		map.put("contenido",configuracionVo.getContenido());
		map.put("claveTipo",configuracionVo.getClaveTipo());
		map.put("archivoLoad",configuracionVo.getArchivoLoad());
		map.put("contenidoDatoSeg",configuracionVo.getContenidoDatoSeg());
		
		WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_NUEVA_CONFIGURACION");
        return res.getMsgText();
		
	}

	@SuppressWarnings("unchecked")
	public String  editarNuevaConfiguracion(ConfiguracionVO configuracionVo) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("claveConfig",configuracionVo.getClaveConfig());
		map.put("descripcionConfig",configuracionVo.getDescripcionConfig());
		map.put("claveElemento",configuracionVo.getClaveElemento());
		map.put("claveRol",configuracionVo.getClaveRol());
		map.put("claveSeccion",configuracionVo.getClaveSeccion());
		map.put("swHabilitado",configuracionVo.getSwHabilitado());
		map.put("especificacion",configuracionVo.getEspecificacion());
		map.put("contenido",configuracionVo.getContenido());
		map.put("claveTipo",configuracionVo.getClaveTipo());
		map.put("archivoLoad",configuracionVo.getArchivoLoad());
		map.put("contenidoDatoSeg",configuracionVo.getContenidoDatoSeg());
		
		WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_CONFIGURACION");
        return res.getMsgText();	
		
	}

	@SuppressWarnings("unchecked")
	/**
	 * Metodo que se encarga de copiar una configuracion existente en la base.
	 */
	public void copiarConfig(String idCliente, String idRol, String idClienteNvo, String idRolNvo) throws ApplicationException {
		Map params =  new HashMap<String, String>();
		params.put("idCliente", idCliente);
		params.put("idRol", idRol);
		params.put("idClienteNvo",idClienteNvo);
		params.put("idRolNvo", idRolNvo);
		Endpoint endpoint = (Endpoint) endpoints.get("COPIA_CONFIGURACION");
		try{
			endpoint.invoke(params);
		}catch (BackboneApplicationException e) {
			throw new ApplicationException("Error copiando una configuracion");
		}
	}
	/**
	 *Metodo que se encarga de borrar una configuracion existente. 
	 */
	@SuppressWarnings("unchecked")
	public String borrarConfig(String claveDeConfiguracion, String claveDeSeccion) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("claveDeConfiguracion", claveDeConfiguracion);
		params.put("claveDeSeccion", claveDeSeccion);
		
		WrapperResultados res = returnBackBoneInvoke(params, "BORRA_CONFIGURACION");
		return res.getMsgText();
		/*Endpoint endpoint = (Endpoint) endpoints.get("BORRA_CONFIGURACION");
		try{
			endpoint.invoke(params);
		}catch (BackboneApplicationException e) {
			throw new ApplicationException("Error tratando de borrar una configuracion");
		}*/
		
	}
	/**
	 *Metodo que se encarga de cargar la lista de paginas configuradas. 
	 */
	@SuppressWarnings("unchecked")
	public List<PaginaVO> getAllPaginas(String nombrePagina, String rolPagina, String clientePagina) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("nombrePagina", nombrePagina);
		params.put("rolPagina", rolPagina);
		params.put("clientePagina", clientePagina);
		List<PaginaVO> paginasConfiguradas = null;

		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_PAGINAS");
			paginasConfiguradas = (ArrayList<PaginaVO>) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			logger.error("Backbone exception", e);
			throw new ApplicationException("Error al recuperar los datos");

		}
		return (List<PaginaVO>) paginasConfiguradas;
	}
	
	@SuppressWarnings("unchecked")
	public PagedList getAllPaginas(String nombrePagina, String rolPagina, String clientePagina, int start, int limit) throws ApplicationException {
    	
		logger.debug(">>>>>>> PrincipalManagerImpl.getAllPaginas( 5 params)");
		HashMap map = new HashMap();
    	
		map.put("nombrePagina", nombrePagina);
		map.put("rolPagina", rolPagina);
		map.put("clientePagina", clientePagina);
		
		return pagedBackBoneInvoke(map, "OBTIENE_PAGINAS", start, limit);
    }
	
	
	@SuppressWarnings("unchecked")
	public void addPagina(String claveConfiguracion, String paginaNombre, String claveClienteInsert, String claveRolInsert) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("claveConfiguracion", claveConfiguracion);
		params.put("paginaNombre", paginaNombre);
		params.put("claveClienteInsert", claveClienteInsert);
		params.put("claveRolInsert", claveRolInsert);
		Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_PAGINA");
		try{
			endpoint.invoke(params);
		}catch (BackboneApplicationException e) {
			throw new ApplicationException("Error guardando o editando pagina");
		}
	}
	
	@SuppressWarnings("unchecked")
	public String  guardarPagina(String claveConfiguracion, String paginaNombre, String claveClienteInsert, String claveRolInsert) throws ApplicationException {
		Map params = new HashMap<String, String>();
		params.put("claveConfiguracion", claveConfiguracion);
		params.put("paginaNombre", paginaNombre);
		params.put("claveClienteInsert", claveClienteInsert);
		params.put("claveRolInsert", claveRolInsert);
		
		
		WrapperResultados res =  returnBackBoneInvoke(params,"GUARDA_PAGINA");
        return res.getMsgText();
	}
	
	@SuppressWarnings("unchecked")
	public void borrarPagina(String id) throws ApplicationException {
		 Map params = new HashMap<String, String>();
		params.put("id", id);
		Endpoint endpoint = (Endpoint) endpoints.get("BORRA_PAGINA");
		try{
			endpoint.invoke(params);
		}catch (BackboneApplicationException e) {
			throw new ApplicationException("Error tratando de borrar una pagina");
		}
	}
	public String configuracionCompleta(String cdElemento)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
