package mx.com.aon.catweb.configuracion.producto.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.catweb.configuracion.producto.model.ClavesVO;
import mx.com.aon.catweb.configuracion.producto.model.ListaDeValoresVO;
import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.service.ListaDeValoresManager;
import mx.com.aon.catweb.configuracion.producto.tablaCincoClaves.model.DatosClaveAtributoVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;
import mx.com.aon.utils.Constantes;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author Edgar Perez
 * @version 2.0
 * @since 1.0
 * 
 * Clase que implementa los metodos para agregar y obtener 
 * las los catalogos de la lista de valores asociadas a un producto
 *
 */
public class ListaDeValoresManagerImpl implements ListaDeValoresManager {

	private static Logger logger = Logger.getLogger(ListaDeValoresManagerImpl.class);
	
	/**
	 * Mapa en el cual se introducen los Manager's para ser extraidos y
	 * utilizados como servicios.
	 */
	
	private Map endpoints;
	
	/**
	 * Implementacion que extrae los catalogos de la lista de valores.
	 * 
	 * 
	 * @return catalogo List<ListaDeValoresVO> - Lista con la informacion de los
	 *         catalogos asociadas a la lista de valores.
	 *         
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public List<ListaDeValoresVO> listaDeValoresCatalogo(String valor)
			throws ApplicationException {
		 Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_CATALOGO");
		 //Map params = new HashMap<String, String>();
		  //params.put("valor", valor);
		  
		 
		 List<ListaDeValoresVO> catalogo = null;
	        try {
	        	catalogo = (List<ListaDeValoresVO>) endpoint.invoke(valor);
	        
	        } catch (BackboneApplicationException e) {
	            throw new ApplicationException("Error regresando lista de valores del catalogo");
	        }
	        return catalogo;
	}


	/**
	 * Implementacion que guarda la lista de valores.
	 * 
	 * @return NmTabla numero de la tabla generado despues de guardar la primera vez
	 * 			en la BD la lista de valores
	 *         
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public String  guardaListaValores(ListaDeValoresVO lista)
			throws ApplicationException {
		
		Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_LISTA_DE_VALORES");
        ListaDeValoresVO nmTabla = null;
		try {
            nmTabla=(ListaDeValoresVO)endpoint.invoke(lista);
            if(logger.isDebugEnabled()){
            	logger.debug("NUMERO TABLA----------"+nmTabla.getNumeroTabla());
            }
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando guardar la lista de valores");
        }
        return nmTabla.getNumeroTabla();
		
	}
	
	/**
	 * Implementacion que guarda la clave de la lista de valores.
	 *
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	//insertar objeto clavesVO ***modificado
	public void guardaClaveListaDeValores(ClavesVO clave,String tipoTransaccion,String nmTabla)
			throws ApplicationException {
		Map params = new HashMap<String, Object>();
		params.put("clave", clave);
		params.put("tipoTransaccion", tipoTransaccion);
		params.put("nmTabla", nmTabla);
		Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_CLAVE_LISTA_DE_VALORES");
		try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando guardar la clave de la lista de valores");
        }
		
	}

	/**
	 * Implementacion que guarda la descripcion de la tabla una y cinco claves.
	 *  
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public void guardaDescripcionListaDeValores(List<ListaDeValoresVO> descripcion,String tipoTransaccion ,String nmTabla)
	throws ApplicationException {
		Map params = new HashMap<String, List<ListaDeValoresVO>>();
		params.put("listaDescripcion", descripcion);
		params.put("tipoTransaccion", tipoTransaccion);
		params.put("nmTabla", nmTabla);
		Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_DESCRIPCION_LISTA_DE_VALORES");
		try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando guardar la descripcion de la lista de valores");
        }
		
	}
	
	/**
	 * Implementacion que guarda la descripcion de la tabla una y cinco claves.
	 *
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public List<LlaveValorVO> guardaValoresCargaManual(List<LlaveValorVO> listaGrid, String nmTabla, List<LlaveValorVO> listaClavesDependencia , String cdTabla)
	throws ApplicationException {
		List<LlaveValorVO> clavesNoInsertadas = new ArrayList<LlaveValorVO>();
		boolean eliminar = false;
		if(listaClavesDependencia != null && !listaClavesDependencia.isEmpty()) eliminar = true;
		
		Map params = new HashMap<String, String>();
		params.put("nmTabla", nmTabla);
		
		if(listaGrid!=null && !listaGrid.isEmpty()){
			for(LlaveValorVO claveVO : listaGrid ){
				boolean inserted = true;
				WrapperResultados res = null;
				
				claveVO.setValue(StringEscapeUtils.escapeHtml(claveVO.getValue()));
				params.put("elemento", claveVO);
					
				try {
					Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_LISTA_CARGA_MANUAL");
					res = (WrapperResultados) endpoint.invoke(params);
					
					if(res != null && StringUtils.isNotBlank(res.getMsgId())){
						if(res.getMsgId().equals(Constantes.REGISTRO_DUPLICADO)){
							LlaveValorVO claveRep = new LlaveValorVO();
							claveRep.setKey(claveVO.getKey());
							clavesNoInsertadas.add(claveRep);
							inserted = false;
						}
						
						if(res.getMsgTitle().equals("1")){
							inserted = false;
						}
					}
					
					//Se valida si el registro tenia unId anterior, para esto si ese registro ya fue insertado, si el valor original no esta Vacio
					// y si la lista de elementos a eliminar no esta vacia. Con esos datos se hace el match para eliminar el registro pasado si se encuentra en la lista
					if(StringUtils.isNotBlank(claveVO.getId()) && eliminar ){
						
						for(LlaveValorVO claveEliminar : listaClavesDependencia){
							if(StringUtils.isNotBlank(claveEliminar.getKey()) && claveEliminar.getKey().equals(claveVO.getId())){
								if(inserted){
									eliminaValoresCargaManual( claveEliminar , cdTabla);
								}
								claveEliminar.setKey("");
								
							}
						}
							
						
					}
			
				} catch (BackboneApplicationException e) {
					logger.error("Error intentando guardar la lista de valores de carga manual" + e.getMessage() );
					throw new ApplicationException("Error al guardar la lista de valores de carga manual. Consulte a su soporte");
				}		
			}
		}
		if(eliminar){
			logger.debug("PARA ELIMINAR FINALES: "+ listaClavesDependencia);
			for(LlaveValorVO claveEliminar : listaClavesDependencia){
				if(StringUtils.isNotBlank(claveEliminar.getKey())){
					eliminaValoresCargaManual( claveEliminar , cdTabla);
				}
			}	
		}
		
        return clavesNoInsertadas;
	}
	
	
	/**
	 * Implementacion que guarda la descripcion de la tabla una y cinco claves.
	 * @param LlaveValorVO llave a Eliminar
	 * @param String cdTabla
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public void eliminaValoresCargaManual(LlaveValorVO llaveEliminar, String cdTabla)
	throws ApplicationException {
		//logger.debug("******numero tabla impl"+nmTabla);
		Map params = new HashMap<String, String>();
		params.put("cdTabla", cdTabla);
		params.put("elemento", llaveEliminar);
		Endpoint endpoint = (Endpoint) endpoints.get("ELIMINA_LISTA_CARGA_MANUAL");
		try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando guardar la lista de valores de carga manual");
        }
	}
	//*********************** 5 claves*******************************
	/**
	 * Implementacion que obtiene los valores de la tabla una y cinco claves.
	 * @return objeto ListaDeValoresVO
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public ListaDeValoresVO consultaTabla(String nmTabla)
	throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_TABLA_1Y5_CLAVES");
		 
		 ListaDeValoresVO tabla = null;
	        try {
	        	tabla = (ListaDeValoresVO) endpoint.invoke(nmTabla);
	        
	        } catch (BackboneApplicationException e) {
	            throw new ApplicationException("Error regresando la tabla de 1 y 5 claves");
	        }
	        return tabla;
	}

	/**
	 * Implementacion que obtiene los valores de las claves de la tabla una y cinco claves.
	 * @return listaClaves List<DatosClaveAtributoVO>
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	//regresar objeto tipo ClavesVO ****modificado
	public List<DatosClaveAtributoVO> consultaClaves(String nmTabla)
	throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_CLAVES_1Y5");
		 
		 List<DatosClaveAtributoVO> listaClaves = null;
	        try {
	        	listaClaves = (List<DatosClaveAtributoVO>) endpoint.invoke(nmTabla);
	        
	        } catch (BackboneApplicationException e) {
	            throw new ApplicationException("Error regresando las claves de 1 y 5");
	        }
	        return listaClaves;
	}
	
	/**
	 * Implementacion que obtiene los valores de las descripciones de la tabla una y cinco claves.
	 * @return listaDescripciones List<DatosClaveAtributoVO>
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public List<DatosClaveAtributoVO> consultaDescripciones(String nmTabla)
	throws ApplicationException {
		Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_DESCRIPCIONES_1Y5");
		 
		 List<DatosClaveAtributoVO> listaDescripciones = null;
	        try {
	        	listaDescripciones = (List<DatosClaveAtributoVO>) endpoint.invoke(nmTabla);
	        
	        } catch (BackboneApplicationException e) {
	            throw new ApplicationException("Error regresando las descripciones de 1 y 5");
	        }
	        return listaDescripciones;
	}
	
	/**
	 * Implementacion que obtiene los valores del grid carga manual de la tabla una clave.
	 * @return listaCargaManual List<LlaveValorVO>
	 * @throws ApplicationException -
	 *             Es lanzada en errores de configuracion de aplicacion error en
	 *             las consultas a BD.
	 */
	public PagedList listaValoresCargaManual(String tabla , int start , int limit)
	throws ApplicationException {
		/**
		 * TODO generate Code
		 */
	        return null;
	}
	
	//***********************conversion de objetos a lista y viceversa*******************************
	
	 public ClavesVO convierteAClaveVO(List<DatosClaveAtributoVO> dca) throws ApplicationException{
		  ClavesVO clavesVO= new ClavesVO();  
		  if(!dca.isEmpty()){
		   for(DatosClaveAtributoVO dcaVO: dca){
		    //logger.debug("NUMCLAVEIMPL**"+dcaVO.getNumeroClave());
		    //logger.debug("DESCLAVEIMPL**"+dcaVO.getDescripcion());
			   switch(dcaVO.getNumeroClave()){
		    case 0:
		     clavesVO.setClave(dcaVO.getDescripcion());
		     clavesVO.setFormatoClave(dcaVO.getFormato());
		     clavesVO.setMaximoClave(dcaVO.getMaximo());
		     clavesVO.setMinimoClave(dcaVO.getMinimo());
		     break;
		    case 1:
		     clavesVO.setClave2(dcaVO.getDescripcion());
		     clavesVO.setFormatoClave2(dcaVO.getFormato());
		     clavesVO.setMaximoClave2(dcaVO.getMaximo());
		     clavesVO.setMinimoClave2(dcaVO.getMinimo());
		     break; 
		    case 2:
		     clavesVO.setClave3(dcaVO.getDescripcion());
		     clavesVO.setFormatoClave3(dcaVO.getFormato());
		     clavesVO.setMaximoClave3(dcaVO.getMaximo());
		     clavesVO.setMinimoClave3(dcaVO.getMinimo());
		     break;
		    case 3:
		     clavesVO.setClave4(dcaVO.getDescripcion());
		     clavesVO.setFormatoClave4(dcaVO.getFormato());
		     clavesVO.setMaximoClave4(dcaVO.getMaximo());
		     clavesVO.setMinimoClave4(dcaVO.getMinimo());
		     break;
		    case 4:
		     clavesVO.setClave5(dcaVO.getDescripcion());
		     clavesVO.setFormatoClave5(dcaVO.getFormato());
		     clavesVO.setMaximoClave5(dcaVO.getMaximo());
		     clavesVO.setMinimoClave5(dcaVO.getMinimo());
		     break;
		    }
		    
		   }
		  }
		 //logger.debug("CLAVEIMPLEMENTACION----"+clavesVO.getClave());
		 //logger.debug("FORMATOCLAVEIMPLEMENTACION----"+clavesVO.getFormatoClave());
		 //logger.debug("CLAVEIMPLEMENTACION2----"+clavesVO.getClave2());
		 //logger.debug("FORMATOCLAVEIMPLEMENTACION2----"+clavesVO.getFormatoClave2());
		  return clavesVO;
		 }
	
	 public List<DatosClaveAtributoVO> convierteAListaDCAVO(ClavesVO clavesVO){
		  List<DatosClaveAtributoVO> ldcaVO= new ArrayList<DatosClaveAtributoVO>();
		  DatosClaveAtributoVO dcaVO=null;
		  if(clavesVO.getClave()!=null && StringUtils.isNotBlank(clavesVO.getClave())){
		   dcaVO= new DatosClaveAtributoVO();
		   dcaVO.setNumeroClave(1);
		   dcaVO.setDescripcion(clavesVO.getClave());
		   dcaVO.setFormato(clavesVO.getFormatoClave());
		   dcaVO.setMaximo(clavesVO.getMaximoClave());
		   dcaVO.setMinimo(clavesVO.getMinimoClave());
		   ldcaVO.add(dcaVO);
		  }
		  if(clavesVO.getClave2()!=null && StringUtils.isNotBlank(clavesVO.getClave2())){
		   dcaVO= new DatosClaveAtributoVO();
		   dcaVO.setNumeroClave(2);
		   dcaVO.setDescripcion(clavesVO.getClave2());
		   dcaVO.setFormato(clavesVO.getFormatoClave2());
		   dcaVO.setMaximo(clavesVO.getMaximoClave2());
		   dcaVO.setMinimo(clavesVO.getMinimoClave2());
		   ldcaVO.add(dcaVO);
		  }
		  if(clavesVO.getClave3()!=null && StringUtils.isNotBlank(clavesVO.getClave3())){
		   dcaVO= new DatosClaveAtributoVO();
		   dcaVO.setNumeroClave(3);
		   dcaVO.setDescripcion(clavesVO.getClave3());
		   dcaVO.setFormato(clavesVO.getFormatoClave3());
		   dcaVO.setMaximo(clavesVO.getMaximoClave3());
		   dcaVO.setMinimo(clavesVO.getMinimoClave3());
		   ldcaVO.add(dcaVO);
		  }
		  if(clavesVO.getClave4()!=null && StringUtils.isNotBlank(clavesVO.getClave4())){
		   dcaVO= new DatosClaveAtributoVO();
		   dcaVO.setNumeroClave(4);
		   dcaVO.setDescripcion(clavesVO.getClave4());
		   dcaVO.setFormato(clavesVO.getFormatoClave4());
		   dcaVO.setMaximo(clavesVO.getMaximoClave4());
		   dcaVO.setMinimo(clavesVO.getMinimoClave4());
		   ldcaVO.add(dcaVO);
		  }
		  if(clavesVO.getClave5()!=null && StringUtils.isNotBlank(clavesVO.getClave5())){
		   dcaVO= new DatosClaveAtributoVO();
		   dcaVO.setNumeroClave(5);
		   dcaVO.setDescripcion(clavesVO.getClave5());
		   dcaVO.setFormato(clavesVO.getFormatoClave5());
		   dcaVO.setMaximo(clavesVO.getMaximoClave5());
		   dcaVO.setMinimo(clavesVO.getMinimoClave5());
		   ldcaVO.add(dcaVO);
		  }
		  return ldcaVO;
		 }
	 
	 
	 //************** Edicion de lista de valores****************************
	 public ListaDeValoresVO obtieneCabeceraListaDeValores(String nmTabla) throws ApplicationException {
		 Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_CABECERA_LISTA_DE_VALORES");
		 
		 ListaDeValoresVO cabecera = null;
	        try {
	        	cabecera = (ListaDeValoresVO) endpoint.invoke(nmTabla);
	        
	        } catch (BackboneApplicationException e) {
	            throw new ApplicationException("Error regresando la cabecera de lista de valores");
	        }
	        return cabecera;
	 }

	 public ListaDeValoresVO obtieneClaveListaDeValores(String nmTabla) throws ApplicationException {
		 Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_CLAVE_LISTA_DE_VALORES");
		 
		 ListaDeValoresVO clave = null;
	        try {
	        	clave = (ListaDeValoresVO) endpoint.invoke(nmTabla);
	        
	        } catch (BackboneApplicationException e) {
	            throw new ApplicationException("Error regresando la clave de lista de valores");
	        }
	        return clave;
	 }

	 public ListaDeValoresVO obtieneDescripcionListaDeValores(String nmTabla) throws ApplicationException {
		 Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_DESCRIPCION_LISTA_DE_VALORES");
		 
		 ListaDeValoresVO descripcion = null;
	        try {
	        	descripcion = (ListaDeValoresVO) endpoint.invoke(nmTabla);
	        
	        } catch (BackboneApplicationException e) {
	            throw new ApplicationException("Error regresando la descripcion de lista de valores");
	        }
	        return descripcion;
	 }
	 
	 public List<LlaveValorVO> listaClavesDependencias(String codigoTabla,
			 String langCode) throws ApplicationException {
		 	Map params = new HashMap<String, String>();
			params.put("codigoTabla", codigoTabla);
			params.put("langCode", langCode);
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_LISTA_CLAVES_DEPENDENCIAS");
			List<LlaveValorVO> clavesDependencia = null;
			try {
	            clavesDependencia = (List<LlaveValorVO>)endpoint.invoke(params);
	        } catch (BackboneApplicationException e) {
	            throw new ApplicationException("Error intentando cargar la lista de claves de dependencia");
	        }
	        return clavesDependencia;
	 }

	 public void setEndpoints(Map endpoints) {
		this.endpoints = endpoints;
	}
	 
	 
		
	 
	public void eliminarTablaClave(Map<String, String> params) throws ApplicationException {
	   logger.debug("into ListaValoresManagerImpl.eliminarTablaClave()");
	  try{
		
	     Endpoint manager = (Endpoint) endpoints.get("ELIMINAR_TABLA_CLAVE");
		 manager.invoke(params);
		 logger.debug("elimino bien");
	  } catch (BackboneApplicationException bae) {
		  logger.error("Exception in invoke 'ELIMINAR_TABLA_CLAVE'", bae);
		  throw new ApplicationException("Error al eliminar tabla clave ");
	  }
	  
	}
	 
	 
	 

}
