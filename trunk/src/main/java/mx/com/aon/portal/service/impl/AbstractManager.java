package mx.com.aon.portal.service.impl;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.ProcessResultManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;

import org.apache.log4j.Logger;

public abstract class AbstractManager {

    /**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	protected static Logger logger = Logger.getLogger(EstructuraManagerImpl.class);
	/**
	 * Mapa en el cual se introducen los Manager's para ser extraidos y utilizados como servicios
	 */
	protected Map<String, Endpoint> endpoints;

    private ProcessResultManager processResultManager;

    public void setEndpoints(Map<String, Endpoint> endpoints) {this.endpoints = endpoints;}


    public void setProcessResultManager(ProcessResultManager processResultManager) {
        this.processResultManager = processResultManager;
    }

    /**
	 * Metodo que ejecuta backbone y obtiene una lista filtrada de el cursor de salida obtenido
	 * 
	 * Nota: Este metodo existe como alternativa a la carencia de paginacion por parte de backbone (o para acceder a los resultset)
	 * y porque en el proyecto actual solo se invocan stored procedures q no reciben ningun parametro de paginacion.
	 * 
	 * @param arg Object a pasar a metodo invoke de backbone
	 * @param endpointName nombre del endpoint a invocar
	 * @param start  desde , paginacion
	 * @param limit  hasta , paginacion
	 * @return instancia de WrapperResultados cargado con los id de msgs de respuesta y resultados
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList pagedBackBoneInvoke(Object arg,String endpointName ,int start,int limit) throws ApplicationException {
		WrapperResultados res;

		Endpoint endpoint =  endpoints.get(endpointName);
		try {

			res = (WrapperResultados) endpoint.invoke(arg);
			logger.debug("Resultado endpoint invoke :"+ res);
			logger.debug("Resultado msgId :"+ res.getMsgId());
		} catch (BackboneApplicationException e) {
			logger.error("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName, e);
			throw new ApplicationException("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName,e);
		}

        if (res.getItemList()==null) {
            logger.debug("No se encontraron datos");
            throw new ApplicationException("No se encontraron datos");
        }
        //carga el tamanio antes de filtrar la lista
		res.setNotPagedTotalItems( res.getItemList().size() );
		
		
		//Si limit es -1 regresa todos los elementos (esto para el caso en el que se reutilizan los metodos en combos y no en pagedGrids 
		if(limit != -1){
	        if (res.getItemList().size() < (start + limit)) {
	          limit =  res.getItemList().size();
	        }  else {
	          limit = start + limit;
	        }
		
	        //sobrescribe la lista con una sublista
			res.setItemList( res.getItemList().subList(start, limit) );
		}
        
        
        
		//TODO Se agrego esta validacion ya que si el msgId viene vacio,
        //manda una excepcion y no se carga la pantalla de busqueda de Poliza.
		if(res.getMsgId()!=null && !res.getMsgId().equals("")){
        	res = processResultManager.processResultMessageId(res);
        }
        PagedList pagedList = new PagedList();
        pagedList.setItemsRangeList(res.getItemList());
        pagedList.setTotalItems(res.getNotPagedTotalItems());
        pagedList.setMessageResult(res.getMsgText());

        return pagedList;

	}


    /**
     * Metodo que ejecuta backbone y obtiene un registro dado un conjunto de argumentos
     *
     *
     * @param arg Object a pasar a metodo invoke de backbone
     * @param endpointName nombre del endpoint a invocar
     * @return instancia de WrapperResultados cargado con los id de msgs de respuesta y resultados
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
    public Object getBackBoneInvoke(Object arg,String endpointName ) throws ApplicationException {
        WrapperResultados res;

        Endpoint endpoint =  endpoints.get(endpointName);
        try {

            res = (WrapperResultados) endpoint.invoke(arg);

        } catch (BackboneApplicationException e) {
            logger.error("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName, e);
            throw new ApplicationException("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName,e);
        }

        res = processResultManager.processResultMessageId(res);

        if (res.getItemList()==null) {
            logger.debug("No se encontraron datos");
            throw new ApplicationException("No se encontraron datos");
        }

        // Chequeamos que solo se retorne un solo rgistro
        if (res.getItemList() != null && res.getItemList().size()>0 )  {
            return res.getItemList().get(0);
        } else {
            throw new ApplicationException("Registro no encontrado");
        }
    }

    /**
     * Metodo que ejecuta backbone y obtiene un registro dado un conjunto de argumentos
     *
     *
     * @param arg Object a pasar a metodo invoke de backbone
     * @param endpointName nombre del endpoint a invocar
     * @return instancia de WrapperResultados cargado con los id de msgs de respuesta y resultados
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
    public void voidReturnBackBoneInvoke(Object arg,String endpointName ) throws ApplicationException {
        WrapperResultados res;

        Endpoint endpoint =  endpoints.get(endpointName);
        try {

            res = (WrapperResultados) endpoint.invoke(arg);

        } catch (BackboneApplicationException e) {
            logger.error("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName, e);
            throw new ApplicationException("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName,e);
        }

        res = processResultManager.processResultMessageId(res);

    }

    /**
     * Metodo que ejecuta backbone y obtiene un registro dado un conjunto de argumentos
     *
     *
     * @param arg Object a pasar a metodo invoke de backbone
     * @param endpointName nombre del endpoint a invocar
     * @return instancia de WrapperResultados cargado con los id de msgs de respuesta y resultados
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
    public WrapperResultados returnBackBoneInvoke(Object arg,String endpointName ) throws ApplicationException {
        WrapperResultados res;

        Endpoint endpoint =  endpoints.get(endpointName);
        try {

            res = (WrapperResultados) endpoint.invoke(arg);

        } catch (BackboneApplicationException e) {
            logger.error("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName, e);
            throw new ApplicationException("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName,e);
        }

        res = processResultManager.processResultMessageId(res);
        return res;

    }

    @SuppressWarnings("unchecked")
    public List getAllBackBoneInvoke(Object arg,String endpointName) throws ApplicationException {
        WrapperResultados res;

        Endpoint endpoint =  endpoints.get(endpointName);
        try {

            res = (WrapperResultados) endpoint.invoke(arg);

        } catch (BackboneApplicationException e) {
            logger.error("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName, e);
            throw new ApplicationException("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName,e);
        }

        if (res.getItemList()==null) {
            logger.debug("No se encontraron datos");
            throw new ApplicationException("No se encontraron datos");
        }
        

        res = processResultManager.processResultMessageId(res);


        return res.getItemList();

    }

    @SuppressWarnings("unchecked")
    public List getExporterAllBackBoneInvoke(Object arg,String endpointName) throws ApplicationException {
        WrapperResultados res;

        Endpoint endpoint =  endpoints.get(endpointName);
        try {

            res = (WrapperResultados) endpoint.invoke(arg);

        } catch (BackboneApplicationException e) {
            logger.error("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName, e);
            return null;
        }

        if (res.getItemList()==null) {
            logger.debug("No se encontraron datos");
            return null;
        }


        res = processResultManager.processResultMessageId(res);


        return res.getItemList();

    }
    
    /**
     * Metodo que solo procesa el mensaje final apartir de un msgId contenido en un WrapperResultados
     *
     * @param WrapperResultados que tiene en su propiedad msgId el codigo del mensaje a procesar 
     * @return instancia de WrapperResultados cargado con el msgTitle, msgText procesados por el msgId enviado
     * @throws mx.com.aon.core.ApplicationException
     */
    @SuppressWarnings("unchecked")
    public WrapperResultados returnProcessResultMessageId(WrapperResultados res) throws ApplicationException {
        res = processResultManager.processResultMessageId(res);
        return res;

    }


}
