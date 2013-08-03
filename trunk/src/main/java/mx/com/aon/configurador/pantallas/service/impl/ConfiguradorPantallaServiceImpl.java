package mx.com.aon.configurador.pantallas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.configurador.pantallas.model.BackBoneResultVO;
import mx.com.aon.configurador.pantallas.model.ConjuntoPantallaVO;
//import com.aon.catweb.configurador.pantallas.model.MasterWrapperVO;
import mx.com.aon.configurador.pantallas.model.PantallaVO;

import mx.com.aon.configurador.pantallas.model.master.MasterVO;

import mx.com.aon.configurador.pantallas.service.ConfiguradorPantallaService;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;
import com.biosnet.ice.ext.elements.model.JSONMasterVO;
import com.biosnet.ice.ext.elements.model.MasterWrapperVO;


import org.apache.log4j.Logger;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

/**
 * 
 * Implementacion de la Interfaz Manager para el control y visualizacion de datos
 * 
 * @author  aurora.lozada
 * 
 */

public class ConfiguradorPantallaServiceImpl extends AbstractManager implements ConfiguradorPantallaService {

    private final transient Logger logger = Logger.getLogger(ConfiguradorPantallaServiceImpl.class);
    
    private static final String CD_MASTER_COMPRA = "8"; 

    //private Map<String, Endpoint> endpoints;

    /**
     * @param endpoints the endpoints to set
     */
    //public void setEndpoints(Map<String, Endpoint> endpoints) {
        //this.endpoints = endpoints;
    //}

     @SuppressWarnings("unchecked")
    public List<ConjuntoPantallaVO> buscaConjuntos(Map<String, String> parameters) throws ApplicationException {

        List <ConjuntoPantallaVO> itemList = null;

        try {
            Endpoint endpoint = endpoints.get("BUSCA_CONJUNTOS_PANTALLAS");
            itemList = (ArrayList) endpoint.invoke(parameters);

        } catch (BackboneApplicationException bae) {
            logger.error("Exception in invoke Consulta de conjuntos.. ",bae);
            throw new ApplicationException("Error al buscar conjuntoso");
        }

        return itemList;
    }

     /**
      * Método sobrecargado para soportar el modelo de WrapperResultados
      * @param parameters Map con los datos a enviar al sp
      * @param start
      * @param limit
      * @return
      * @throws ApplicationException
      */
    public PagedList buscaConjuntos (Map<String, String> parameters, int start, int limit) throws ApplicationException {
    	return pagedBackBoneInvoke(parameters, "BUSCA_CONJUNTOS_PANTALLAS", start, limit);
    }


    /*public String guardaConjunto(ConjuntoPantallaVO conjunto) throws ApplicationException {

        String cdConjunto = null;
        ConjuntoPantallaVO conjuntoVO = new ConjuntoPantallaVO();
        Endpoint endpoint = (Endpoint) endpoints.get("GUARDAR_CONJUNTO");
        try {
            conjuntoVO = (ConjuntoPantallaVO) endpoint.invoke(conjunto);
            cdConjunto = conjuntoVO.getCdConjunto();

        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando guardar un conjunto");
        }
        return cdConjunto;
    }
	*/
    
    /*
     * Metodo sobrescrito para soportar manejo de WrapperResultados
     * @param conjunto VO con los datos del conjunto a guardar
     * @return código del conjunto guardado
     * @thows ApplicationException
     */
    public WrapperResultados guardaConjunto (ConjuntoPantallaVO conjunto) throws ApplicationException {
    	//ConjuntoPantallaVO conjuntoVO = new ConjuntoPantallaVO();
    	
    	//conjuntoVO = (ConjuntoPantallaVO)getBackBoneInvoke(conjunto, "GUARDAR_CONJUNTO");
    	
    	WrapperResultados res = returnBackBoneInvoke(conjunto, "GUARDAR_CONJUNTO");

    	return res;
    }

    /*
    public ConjuntoPantallaVO getConjunto(String cdConjunto) throws ApplicationException {

        Map <String, String> parameters = new HashMap<String, String>();
        parameters.put("CD_CONJUNTO", cdConjunto);

        ConjuntoPantallaVO cargaConjunto = new ConjuntoPantallaVO();
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_CONJUNTO_PANTALLA");
            cargaConjunto = (ConjuntoPantallaVO) endpoint.invoke(parameters);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando obtener un conjunto");

        }

        logger.debug("######Conjunto en getConjunto..." + cargaConjunto.getCdConjunto());
        return cargaConjunto;
    }
    */
    public ConjuntoPantallaVO getConjunto (String cdConjunto) throws ApplicationException {
    	Map <String, String> parameters = new HashMap<String, String>();
    	parameters.put("CD_CONJUNTO", cdConjunto);

        ConjuntoPantallaVO cargaConjunto = new ConjuntoPantallaVO();
        
        return (ConjuntoPantallaVO)getBackBoneInvoke(parameters, "OBTIENE_CONJUNTO_PANTALLA");
    }

    
    public List <?> getPantallasConjunto(String cdConjunto) throws ApplicationException {

        List <?> itemList = null;

        Map <String, String> parameters = new HashMap<String, String>();
        parameters.put("CD_CONJUNTO", cdConjunto);

        try {
            Endpoint endpoint = endpoints.get("OBTIENE_PANTALLAS");
            itemList = (ArrayList <?>) endpoint.invoke(parameters);
           } catch (BackboneApplicationException bae) {
            logger.error("Exception in invoke Consulta de pantallas de un conjunto ", bae);
            throw new ApplicationException("Error al consultar pantallas de un conjunto");
        }

        return itemList;
    }

    /**
     * 
     */
    public PagedList getPantallasConjunto (String cdConjunto, int start, int limit) throws ApplicationException {
        Map <String, String> parameters = new HashMap<String, String>();
        parameters.put("CD_CONJUNTO", cdConjunto);

        return pagedBackBoneInvoke(parameters, "OBTIENE_PANTALLAS", start, limit);
    }

    /*
    public String copiarConjunto(ConjuntoPantallaVO conjuntoCopiar) throws ApplicationException {

        String cdConjuntoC = null;
        ConjuntoPantallaVO conjuntoCVO = new ConjuntoPantallaVO();
        Endpoint endpoint = (Endpoint) endpoints.get("COPIAR_CONJUNTO");
        try {
            conjuntoCVO = (ConjuntoPantallaVO) endpoint.invoke(conjuntoCopiar);
            cdConjuntoC = conjuntoCVO.getCdConjunto();
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando copiar un conjunto");
        }
        return cdConjuntoC;

    }*/
    
    public BackBoneResultVO copiarConjunto(ConjuntoPantallaVO conjuntoCopiar) throws ApplicationException {
    	String cdConjuntoC = null;
    	/*ConjuntoPantallaVO conjuntoCVO = new ConjuntoPantallaVO();
    	
    	conjuntoCVO = (ConjuntoPantallaVO)getBackBoneInvoke(conjuntoCopiar, "COPIAR_CONJUNTO");
    	cdConjuntoC = conjuntoCVO.getCdConjunto();*/
    	WrapperResultados res = returnBackBoneInvoke(conjuntoCopiar, "COPIAR_CONJUNTO");
    	BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
    	backBoneResultVO.setMsgText(res.getMsgText());
    	backBoneResultVO.setOutParam(res.getResultado());

    	return backBoneResultVO;
    }

    /*
    @SuppressWarnings("unchecked")
    public void copiarPantalla(String cdConjunto, String [] copiaPantallas) throws ApplicationException {
        
        Map  parameters = new HashMap< String, String[] >();
        parameters.put("CD_CONJUNTO", cdConjunto);
        parameters.put("LISTA_PANTALLAS", copiaPantallas);
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("COPIAR_PANTALLA");
            endpoint.invoke(parameters);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando copiar pantallas");

        }
    }
	*/
    
    public WrapperResultados copiarPantalla(String cdConjunto, String [] copiaPantallas, String cdElemento) throws ApplicationException {
    	WrapperResultados resultado = null;
    	
    	Map  parameters = new HashMap< String, String[] >();
        parameters.put("CD_CONJUNTO", cdConjunto);
        parameters.put("LISTA_PANTALLAS", copiaPantallas);
        parameters.put("CD_ELEMENTO", cdElemento);

        resultado = returnBackBoneInvoke(parameters, "COPIAR_PANTALLA");
        return resultado;
    }

    public BackBoneResultVO guardaPantalla(PantallaVO pantalla) throws ApplicationException {

        /*String cdPantalla = null;
        PantallaVO pantallaVO = new PantallaVO();
        Endpoint endpoint = (Endpoint) endpoints.get("GUARDAR_PANTALLA");
        try {
            pantallaVO = (PantallaVO) endpoint.invoke(pantalla);
            cdPantalla = pantallaVO.getCdPantalla();
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando guardar una pantalla");
        }
        return cdPantalla;*/
    	WrapperResultados res = returnBackBoneInvoke(pantalla, "GUARDAR_PANTALLA");
    	BackBoneResultVO backBoneResultVO = new BackBoneResultVO ();
    	backBoneResultVO.setMsgText(res.getMsgText());
    	backBoneResultVO.setOutParam(res.getResultado());
    	return backBoneResultVO;
    }

    /*
    public PantallaVO getPantalla(String cdConjunto, String cdPantalla) throws ApplicationException {

        Map <String, String> parameters = new HashMap<String, String>();
        parameters.put("CD_CONJUNTO", cdConjunto);
        parameters.put("CD_PANTALLA", cdPantalla);

        PantallaVO cargaPantalla = new PantallaVO();
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_PANTALLA");
            cargaPantalla = (PantallaVO) endpoint.invoke(parameters);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando obtener una pantalla");

        }
        return cargaPantalla;
    }
    */

    public PantallaVO getPantalla(String cdConjunto, String cdPantalla) throws ApplicationException {
        Map <String, String> parameters = new HashMap<String, String>();
        parameters.put("CD_CONJUNTO", cdConjunto);
        parameters.put("CD_PANTALLA", cdPantalla);

        PantallaVO cargaPantalla = new PantallaVO();

        cargaPantalla = (PantallaVO) getBackBoneInvoke(parameters, "OBTIENE_PANTALLA");
        
        return cargaPantalla;
    }

    /*
    public void eliminarPantalla(String cdConjunto, String cdPantalla) throws ApplicationException {
        
        Map <String, String> parameters = new HashMap<String, String>();
        parameters.put("CD_CONJUNTO", cdConjunto);
        parameters.put("CD_PANTALLA", cdPantalla);
          
        Endpoint endpoint = (Endpoint) endpoints.get("ELIMINAR_PANTALLA");
        try {
            endpoint.invoke(parameters);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando eliminar una pantalla");
        }
    }
	*/
    public String eliminarPantalla(String cdConjunto, String cdPantalla) throws ApplicationException {
        Map <String, String> parameters = new HashMap<String, String>();
        parameters.put("CD_CONJUNTO", cdConjunto);
        parameters.put("CD_PANTALLA", cdPantalla);

        WrapperResultados res = returnBackBoneInvoke(parameters, "ELIMINAR_PANTALLA");

        return res.getMsgText();
    }
    
    
    public List <?> getMasters(String cdProceso) throws ApplicationException {

        List <?> itemList = null;

        Map <String, String> parameters = new HashMap<String, String>();
        parameters.put("CD_PROCESO", cdProceso);

        try {
            Endpoint endpoint = endpoints.get("OBTIENE_MASTERS");
            itemList = (ArrayList <?>) endpoint.invoke(parameters);
           
        } catch (BackboneApplicationException bae) {
            logger.error("Exception in invoke Consulta de masters ", bae);
            throw new ApplicationException("Error al consultar pantallas de masters");
        }

        return itemList;
    }
    
    
    public PagedList getMasters(String cdProceso, int start, int limit) throws ApplicationException {
        Map <String, String> parameters = new HashMap<String, String>();
        parameters.put("CD_PROCESO", cdProceso);

        return pagedBackBoneInvoke(parameters, "OBTIENE_MASTERS", start, limit);
    }

    /*
    public void activarConjunto(Map<String, String> parameters) throws ApplicationException {
      try {
            Endpoint endpoint = endpoints.get("ACTIVA_CONJUNTO");
            endpoint.invoke(parameters);

        } catch (BackboneApplicationException bae) {
            logger.error("Exception in invoke activar de conjuntos.. ",bae);
            throw new ApplicationException("Error al activar conjuntos");
        }

    }
    */

    /**
     * Metodo para activar un conjunto de pantallas
     * 
     * @param parameters Listado de parametros para la invocacion del servicio en la base de datos
     * @throws ApplicationException
     */
    public String activarConjunto(Map<String, String> parameters) throws ApplicationException {
    	WrapperResultados res = returnBackBoneInvoke(parameters, "ACTIVA_CONJUNTO");
    	
    	return res.getMsgText();
    }
    //TODO: REMOVER ESTE METODO
    @Deprecated
    public MasterVO getAtributosPantalla(String master, String proceso, String tipo, String producto) 
            throws ApplicationException {
        
        MasterVO masterVo = null;
        Map<String, String> params = new HashMap<String, String>();
        params.put("master", master);
        params.put("proceso", proceso);
        params.put("tipo", tipo);
        params.put("producto", producto);
        
        Endpoint endpoint = (Endpoint) endpoints.get("CARGA_ATRIBUTOS_PANTALLA");
        try {
            masterVo = (MasterVO) endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando cargar atributos pantalla");
        }

        return masterVo;
    }
    

    
   
    public MasterWrapperVO getAtributosPropiedades(String master, String proceso, String tipo, String producto) 
            throws ApplicationException {
    	MasterWrapperVO masterVo = null;
        Map<String, String> params = new HashMap<String, String>();
        params.put("master", master);
        params.put("proceso", proceso);
        params.put("tipo", tipo);
        params.put("producto", producto);
        
        Endpoint endpoint = (Endpoint) endpoints.get("CARGA_ATRIBUTOS_PROPIEDADES");
        try {
            masterVo = (MasterWrapperVO) endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando cargar atributos propiedades");
        }

        return masterVo;
    }

	@SuppressWarnings("unchecked")
	public MasterWrapperVO getAtributosPropiedades(Map parameters)
			throws ApplicationException {
		
		MasterWrapperVO masterVo = null;
        
        Endpoint endpoint = (Endpoint) endpoints.get("CARGA_ATRIBUTOS_PROPIEDADES");
        try {
            masterVo = (MasterWrapperVO) endpoint.invoke(parameters);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando cargar atributos propiedades");
        }

        return masterVo;
	}

	public JSONMasterVO getJSONMaster(String cdTipoMaster, String cdProceso,
			String tipoMaster, String cdProducto, String claveSituacion) throws ApplicationException {

		JSONMasterVO jsonMaster = null;
		Endpoint endpoint = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug(". cdTipoMaster : " + cdTipoMaster);
			logger.debug(". cdProceso : " + cdProceso);
		}
		
		if (CD_MASTER_COMPRA.equalsIgnoreCase(cdTipoMaster)) {
			endpoint = (Endpoint) endpoints.get("CARGAR_JSON_PROPERTIES_COMPRA");
		} else {
			endpoint = (Endpoint) endpoints.get("CARGAR_JSON_PROPERTIES");
		}
		
		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put( "cdTipoMaster", cdTipoMaster );
		parameters.put( "cdProceso", cdProceso);
		parameters.put( "tipoMaster", tipoMaster);
		parameters.put( "cdProducto", cdProducto);
		parameters.put( "claveSituacion", claveSituacion);
		
        try {
        	jsonMaster = (JSONMasterVO) endpoint.invoke(parameters);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error intentando cargar atributos propiedades");
        } catch (Exception e) {
        	logger.error("Error: " + e, e);
            throw new ApplicationException("Error intentando cargar atributos propiedades");
        }

		
		
		return jsonMaster;
	}

	public TableModelExport getModel(Map<String, String> parameters) throws ApplicationException {
		try {
			TableModelExport model = new TableModelExport();
			List lista = null;
	
			Endpoint endpoint = (Endpoint) endpoints.get("EXPORTA_CONJUNTOS_PANTALLAS");
			lista = (ArrayList)endpoint.invoke(parameters);
	//		lista = (ArrayList)getExporterAllBackBoneInvoke(map, "PERSONAS_EXPORTAR");
			model.setInformation(lista);
			model.setColumnName(new String[] {"Nombre Conjunto", "Descripción", "Proceso", "Cliente"});
			return model;
		} catch (BackboneApplicationException bae) {
			throw new ApplicationException("Error al exportar");
		}
	}
	
	public PantallaVO getPantallaFinal(Map<String, String> parameters) throws ApplicationException {

		PantallaVO pantallaVO = new PantallaVO();
		try {
			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_PANTALLA_VISTA_PREVIA");
			pantallaVO = (PantallaVO) endpoint.invoke(parameters);
			if(logger.isDebugEnabled()){
				logger.debug("getPantallaFinal()   pantallaVO=" +pantallaVO);
			}
		} catch (BackboneApplicationException bae) {
			logger.error("Exception in invoke getPantallaFinal en cotizacion.. ",bae);
			throw new ApplicationException("Error intentando obtener pantalla final");
		}
		return pantallaVO;
	}

}
