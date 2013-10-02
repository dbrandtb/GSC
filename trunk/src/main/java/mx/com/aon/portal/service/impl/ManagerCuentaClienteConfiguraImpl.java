package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mx.com.aon.portal.model.ConfigurarEstructuraVO;
import mx.com.aon.portal.service.ManagerCuentaChecklist;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

public class ManagerCuentaClienteConfiguraImpl extends AbstractManager implements ManagerCuentaChecklist  {
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ManagerCuentaClienteConfiguraImpl.class);

	/**
	 * Mapa en el cual se introducen los Manager's para ser extraidos y
	 * utilizados como servicios
	 */
	//private Map<String, Endpoint> endpoints;


	/**
	 *  Obtiene la configuracion cuenta cliente en particular
	 *  Hace uso del Store Procedure PKG_AON_CHECKLIST.P_OBTIENE_CONFIGURACION
	 * 
	 *  @param cdPersona
	 *  @param dsPersona
	 *  
	 *  @return Objeto ConfiguracionVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList getConfiguraciones(String cdPersona,String dsPersona, int start, int limit) throws ApplicationException {

		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		
		
	HashMap map = new HashMap();

		map.put("persona", cdPersona);
		map.put("nombre", dsPersona);
		
		return pagedBackBoneInvoke(map, "P_OBTIENE_CONFIGURACION", start, limit);

		
		/*
		ArrayList<ConfiguracionVO> list;
		try {
			Endpoint endpoint = this.endpoints.get("P_OBTIENE_CONFIGURACION");

			HashMap map = new HashMap();
			map.put("persona", cdPersona);
			map.put("nombre", dsPersona);
						
			list = (ArrayList<ConfiguracionVO>) endpoint.invoke(map);
				ConfiguracionVO configDevuelta  = list.get(0);
	            logger.debug("nombre configuracion devuelta " + configDevuelta.getDsConfig());
	            PagedList pagedList = new PagedList();
	            pagedList.setItemsRangeList(list);
	            pagedList.setTotalItems(list.size());
	            return pagedList;
	            
		} catch (BackboneApplicationException e) {
			logger.error("Se produjo una excepcion al ejecutar obtieneConfiguracion",e);
			throw new ApplicationException(e);
		}*/
	}
	
 /**
	 *  Elimina una configuracion cuenta cliente
	 *  Hace uso del Store Procedure PKG_AON_CHECKLIST.P_BORRA_CONFIGURACION
	 * 
	 *  @param cdConfig
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borraConfiguracion(String cdConfig) throws ApplicationException {

			HashMap map = new HashMap();
			map.put("config", cdConfig);

            WrapperResultados res =  returnBackBoneInvoke(map,"P_BORRA_CONFIGURACION");
            return res.getMsgText();
			

	}

	 /**
	 *  Copia una configuracion cuenta cliente
	 *  Hace uso del Store Procedure PKG_AON_CHECKLIST.P_COPIA_CONFIGURA
	 * 
	 *  @param codigoConfiguracion
	 *  @param codigoElemento
	 *  @param codigoPersona
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
/*	@SuppressWarnings("unchecked")
	public WrapperResultados copiarConfiguracionCuenta(String codigoConfiguracion, String codigoElemento, String codigoPersona) throws ApplicationException {
		try{
			Endpoint endpoint = this.endpoints.get("COPIA_CONFIGURA");
			HashMap map = new HashMap();
			map.put("codigoConfiguracion", codigoConfiguracion);
			map.put("codigoElemento", codigoElemento);
			map.put("codigoPersona", codigoPersona);
			WrapperResultados wrapperResultados = (WrapperResultados) endpoint.invoke(map);
			return wrapperResultados;
		}catch(BackboneApplicationException bae){
			logger.error("Se produjo un error al ejecutarse la busqueda de clientes corporativos",bae);
			throw new ApplicationException(bae);
		}
		
	}
*/
	
	@SuppressWarnings("unchecked")
	public String copiarConfiguracionCuenta(String codigoConfiguracion, String codigoElemento, String codigoPersona) throws ApplicationException {
	
			HashMap map = new HashMap();
			map.put("codigoConfiguracion", codigoConfiguracion);
			map.put("codigoElemento", codigoElemento);
			map.put("codigoPersona", codigoPersona);
	
            WrapperResultados res =  returnBackBoneInvoke(map,"COPIA_CONFIGURA");
            return res.getMsgText();
	}	
	
	
	
	
	/**
	 *  Obtiene un configuracion cuenta cliente particular
	 *  Hace uso del Store Procedure PKG_LISTAS.P_CLIENTES_CORPO
	 * 
	 *  @param cdTramo
	 *  
	 *  @return Objeto ConfigurarEstructuraVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public ArrayList<ConfigurarEstructuraVO> obtieneClientes()throws ApplicationException {

		try{
			Endpoint endpoint = this.endpoints.get("OBTENER_CLIENTES_CORPO");
			ArrayList<ConfigurarEstructuraVO> clientes = null;
			clientes = (ArrayList<ConfigurarEstructuraVO>) endpoint.invoke(null);
			return clientes;
		}catch(BackboneApplicationException bae){
			logger.error("Se produjo un error al ejecutarse la busqueda de clientes corporativos",bae);
			throw new ApplicationException(bae);
		}
	}
	
	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}
}
