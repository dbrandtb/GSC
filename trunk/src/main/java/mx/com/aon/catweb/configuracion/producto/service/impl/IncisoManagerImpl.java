package mx.com.aon.catweb.configuracion.producto.service.impl;

import static mx.com.aon.catweb.configuracion.producto.dao.IncisoDAO.AGREGAR_INCISO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.incisos.model.IncisoVO;
import mx.com.aon.catweb.configuracion.producto.service.IncisoManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;

import org.apache.log4j.Logger;
/**
 * 
 * @author Edgar Perez
 * @version 2.0
 * @since 1.0
 * 
 * Clase que implementa los metodos para agregar, asociar, editar y obtener 
 * las listas de incisos asociados a un producto
 *
 */
public class IncisoManagerImpl extends AbstractManagerJdbcTemplateInvoke implements IncisoManager {

	private static Logger logger = Logger.getLogger(IncisoManagerImpl.class);
	
	/**
	 * Mapa en el cual se introducen los Manager's para ser extraidos y
	 * utilizados como servicios.
	 */
	
	private Map endpoints;
	
	/**
	 * Implementacion que extrae todos los incisos asociados a un producto.
	 * 
	 * 
	 * @return List<IncisoVO> - Lista con la informacion de todos los
	 *         incisos asociados al producto solicitado.
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public List<IncisoVO> incisosDelProducto(String codigoRamo, String codigoTipo) throws ApplicationException {

		 Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_INCISOS_DEL_PRODUCTO");
		 Map params = new HashMap<String, String>();
		  params.put("codigoRamo", codigoRamo);
		  params.put("codigoTipo", codigoTipo);   
		 
		 List<IncisoVO> listaIncisosDelProducto = null;
	        try {
	        	listaIncisosDelProducto = (List<IncisoVO>) endpoint.invoke(params);
	        
	        } catch (BackboneApplicationException e) {
	            throw new ApplicationException("Error regresando lista de incisos del producto");
	        }
	        return listaIncisosDelProducto;
	}

	
	/**
	 * Implementacion que extrae una lista de  incisos para asociar a un producto.
	 * 
	 * 
	 * @return List<IncisoVO> - Lista con la informacion de los
	 *         incisos que se pueden asociar al producto solicitado.
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public List<IncisoVO> incisosJson() throws ApplicationException {

		 Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_INCISOS");
	        List<IncisoVO> listaIncisos = null;
	        try {
	        	listaIncisos = (List<IncisoVO>) endpoint.invoke(null);
	        
	        } catch (BackboneApplicationException e) {
	            throw new ApplicationException("Error regresando lista de incisos");
	        }
	        return listaIncisos;
	}
	
	/**
	 * Implementacion que agrega un inciso al catalogo.
	 * 
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD. 
	 */
	
	//*NOTA: Retorna un valor number de la bd para el MSG_ID pero en este momento no se esta manipulando
	
	@SuppressWarnings("unchecked")
	public WrapperResultados agregarInciso(IncisoVO inciso) throws ApplicationException{
        //Endpoint endpoint = (Endpoint) endpoints.get("AGREGAR_INCISO");
        /*
        	logger.debug("claveIMPL-------->"+inciso.getCdtipsit());
			logger.debug("descripcionIMPL-->"+inciso.getDstipsit());
			logger.debug("subincisoIMPL---->"+inciso.getSwsitdec());
		*/
		//try {
        //    endpoint.invoke(inciso);
        //} catch (BackboneApplicationException e) {
        //    throw new ApplicationException("Error intentando insertar un nuevo inciso");
        //}
		
		//Implementacion usando JDBCTemplates
		Map params = new HashMap();
		params.put("P_CDTIPSIT", inciso.getCdtipsit());
		params.put("P_DSTIPSIT", inciso.getDstipsit());
		params.put("P_SWSITDEC", inciso.getSwsitdec());
		
		WrapperResultados resultado = returnBackBoneInvoke(params, AGREGAR_INCISO);
		
		return resultado;
    }

	/**
	 * Implementacion que asocia un inciso al producto.
	 * 
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.  
	 */
	//*NOTA: Retorna un valor number de la bd para el MSG_ID pero en este momento no se esta manipulando
	public void asociarInciso(IncisoVO incisoAsociado) throws ApplicationException {
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("ASOCIAR_INCISO");
            endpoint.invoke(incisoAsociado);
            //logger.debug("Numero regresado desde la consulta a la base"+numeroSituacion);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando asociar un inciso");
        }
	}
	
	public void eliminarInciso(IncisoVO inciso)throws ApplicationException {
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("ELIMINAR_INCISO");
            endpoint.invoke(inciso);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error al eliminar el inciso");
        }
	}

	public void setEndpoints(Map endpoints) {
		this.endpoints = endpoints;
	}


}
