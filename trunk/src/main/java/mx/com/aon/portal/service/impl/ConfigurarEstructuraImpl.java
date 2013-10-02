/**
 * 
 */
package mx.com.aon.portal.service.impl;

/**
 * @author CIMA_USR
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfigurarEstructuraVO;
import mx.com.aon.portal.model.PersonasVO;
import mx.com.aon.portal.service.ConfigurarEstructuraManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

public class ConfigurarEstructuraImpl extends AbstractManager implements ConfigurarEstructuraManager {
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(EstructuraManagerImpl.class);
	

	
	@SuppressWarnings("unchecked")
	public ConfigurarEstructuraVO getConfigurarEstructura(String codigoElemento) throws ApplicationException
	{

			HashMap map = new HashMap();
			map.put("codigoElemento",codigoElemento);
						
            return (ConfigurarEstructuraVO)getBackBoneInvoke(map,"OBTIENE_DESTRUCT_REG");

	}

	
	
	/**
	 * Se consultan las estructuras a partir de una seria de atributos para poder limitar la 
	 * consulta a el tamaño y posición en la tabla.
	 * 
	 * @return Lista con los bean's generados a partir de la consulta
	 * @throws ApplicationException 
	 * @throws ApplicationException Excepcion con la informacion y descripción del problema en la ejecución
	 */
	@SuppressWarnings("unchecked") // Manejo de ArrayList Controlado
	
    public String agregarConfigurarEstructura(ConfigurarEstructuraVO configurarEstructuraVO) throws ApplicationException{
      
		
			// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			map.put("codigoEstructura",configurarEstructuraVO.getCodigoEstructura());
			map.put("nombre",configurarEstructuraVO.getNombre());
			map.put("vinculoPadre",configurarEstructuraVO.getVinculoPadre());
			map.put("tipoNivel",configurarEstructuraVO.getTipoNivel());
			map.put("posicion",configurarEstructuraVO.getPosicion());
			map.put("nomina",configurarEstructuraVO.getNomina());
			map.put("codigoElemento", configurarEstructuraVO.getCodigoElemento());
					
            WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_DESTRUCT");
            return res.getMsgText();

	}
    
    @SuppressWarnings("unchecked")
	public String guardarConfigurarEstructura(ConfigurarEstructuraVO configurarEstructuraVO) throws ApplicationException{

			// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			map.put("codigoEstructura",configurarEstructuraVO.getCodigoEstructura());
			map.put("codigoElemento",configurarEstructuraVO.getCodigoElemento());
			map.put("nombre",configurarEstructuraVO.getNombre());
			map.put("vinculoPadre",configurarEstructuraVO.getVinculoPadre());
			map.put("tipoNivel",configurarEstructuraVO.getTipoNivel());
			map.put("posicion",configurarEstructuraVO.getPosicion());
			map.put("nomina",configurarEstructuraVO.getNomina());
	
            WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_DESTRUCT");
            return res.getMsgText();

    }
    
    @SuppressWarnings("unchecked")
	public String borrarConfigurarEstructura(String codigoEstructura, String codigoElemento) throws ApplicationException{
    	

			// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			map.put("codigoEstructura",codigoEstructura);
			map.put("codigoElemento",codigoElemento);
			
            WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_DESTRUCT");
            return res.getMsgText();

    }
    
	@SuppressWarnings("unchecked")
	public PagedList buscarConfigurarEstructuras(String codigoEstructura,String nombre, String vinculoPadre, String nomina, int start, int limit)throws ApplicationException
	{
			// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			map.put("codigoEstructura",codigoEstructura);
			map.put("nombre",nombre);
			map.put("vinculoPadre",vinculoPadre);
			map.put("nomina",nomina);
			map.put("start",start);
			map.put("limit",limit);
            return pagedBackBoneInvoke(map, "OBTIENE_DESTRUCT", start, limit);

	}
	
	@SuppressWarnings("unchecked")
	public String copiarConfigurarEstructura(String codigoEstructura, String codigoElemento)throws ApplicationException
	{
			
			// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			map.put("codigoEstructura",codigoEstructura);
			map.put("codigoElemento",codigoElemento);
			
            WrapperResultados res =  returnBackBoneInvoke(map,"COPIA_DESTRUCT");
            return res.getMsgText();

	}


	/**
	 * @param endpoints the endpoints to set
	 */
	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}
    

    
	@SuppressWarnings("unchecked")
	public ArrayList<PersonasVO> obtenerClientes()throws ApplicationException
	{

		List<PersonasVO> lista = null;
		
		try {
			// Se extrae el endpoint
			Endpoint manager = endpoints.get("OBTIENE_CLIENTES");
			
			// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();

			// Se hace la petición y dada la transformación se espera una lista de PersonasVO 
			ArrayList<PersonasVO> invoke = (ArrayList<PersonasVO>)manager.invoke( map );
			
			lista = invoke;
			//WrapperResultados i = (WrapperResultados)manager.invoke(map);
	
			//logger.info("Valor de i: " + i.getMsg() + " ===== " + i.getMsgId());
			
			
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke 'Obtener Clientes'",bae);
			throw new ApplicationException("Excepcion al obtener los clientes" + bae.getMessage());
		}
		
		return (ArrayList<PersonasVO>) lista;
				
	}



	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String codigoEstructura, String nombre,String vinculoPadre, String nomina) throws ApplicationException {
		// Se crea el objeto de respuesta
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("codigoEstructura", codigoEstructura);
		map.put("nombre", nombre);
		map.put("vinculoPadre", vinculoPadre);
		map.put("nomina", nomina);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_DESTRUCT_EXPORT");

		model.setInformation(lista);
		// Se agregan los nombre de las columnas del modelo de datos
		model.setColumnName(new String[]{"Nombre","Vinculo-Padre","Tipo de Nivel","Posicion","Nomina"});
		
		return model;
	}


	
	
	

}
