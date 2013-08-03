package mx.com.aon.portal.service.impl;

import org.apache.log4j.Logger;
import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.ProcessResultManagerJdbcTemplate;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

import java.util.List;

public abstract class AbstractManagerJdbcTemplateInvoke {

    /**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	protected static Logger logger = Logger.getLogger(AbstractManagerJdbcTemplateInvoke.class);
	/**
	 * Mapa en el cual se introducen los Manager's para ser extraidos y utilizados como servicios
	 */

    private AbstractDAO abstractDAO;

    private ProcessResultManagerJdbcTemplate processResultManager;


    public void setProcessResultManager(ProcessResultManagerJdbcTemplate processResultManager) {
        this.processResultManager = processResultManager;
    }

    public void setAbstractDAO(AbstractDAO abstractDAO) {
        this.abstractDAO = abstractDAO;
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
	 * @throws mx.com.aon.core.ApplicationException
     */
	@SuppressWarnings("unchecked")
	public PagedList pagedBackBoneInvoke(Object arg,String endpointName ,int start,int limit) throws ApplicationException {
		WrapperResultados res;

		try {

            res = (WrapperResultados) abstractDAO.invoke(endpointName,arg);

        } catch (BackboneApplicationException e) {
			logger.error("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName, e);
			throw new ApplicationException("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName,e);
		}
        
        PagedList pagedList = new PagedList();
        pagedList.setItemMap(res.getItemMap());
        
        if (res.getItemList()==null) {
            logger.debug("No se encontraron datos");
            throw new ApplicationException("No se encontraron datos");
        }
        if (res.getItemList().size()==0) {
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
        
        
        res = processResultManager.processResultMessageId(res);

        pagedList.setItemsRangeList(res.getItemList());
        pagedList.setTotalItems(res.getNotPagedTotalItems());
        pagedList.setMessageResult(res.getMsgText());
        

        return pagedList;

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
	 * @throws mx.com.aon.core.ApplicationException
     */
	@SuppressWarnings("unchecked")
	public PagedList returnAllPagedBackBoneInvoke(Object arg,String endpointName ,int start,int limit) throws ApplicationException {
		WrapperResultados res;

		try {

            res = (WrapperResultados) abstractDAO.invoke(endpointName,arg);

        } catch (BackboneApplicationException e) {
			logger.error("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName, e);
			throw new ApplicationException("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName,e);
		}
        
        PagedList pagedList = new PagedList();
        pagedList.setItemMap(res.getItemMap());
        
        if (res.getItemList()==null || res.getItemList().isEmpty()) {
            logger.debug("No se encontraron datos");
            pagedList.setTotalItems(0);
	        pagedList.setMessageResult("No se encontraron datos");
            
        }else {
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
	        
	        
	        res = processResultManager.processResultMessageId(res);
	
	        pagedList.setItemsRangeList(res.getItemList());
	        pagedList.setTotalItems(res.getNotPagedTotalItems());
	        pagedList.setMessageResult(res.getMsgText());
	        
        }
        return pagedList;
	}

	
    /**
     * Metodo que ejecuta backbone y obtiene un registro dado un conjunto de argumentos
     *
     *
     * @param arg Object a pasar a metodo invoke de backbone
     * @param endpointName nombre del endpoint a invocar
     * @return instancia de WrapperResultados cargado con los id de msgs de respuesta y resultados
     * @throws mx.com.aon.core.ApplicationException
     */
    @SuppressWarnings("unchecked")
    public Object getBackBoneInvoke(Object arg,String endpointName ) throws ApplicationException {
        WrapperResultados res;

        try {

            res = (WrapperResultados) abstractDAO.invoke(endpointName,arg);

        } catch (BackboneApplicationException e) {
            logger.error("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName, e);
            throw new ApplicationException("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName,e);
        }

        res = processResultManager.processResultMessageId(res);

        if (res.getItemList()==null) {
            logger.debug("No se encontraron datos");
            throw new ApplicationException("No se encontraron datos");
        }
        if (res.getItemList().size()==0) {
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
     * @throws mx.com.aon.core.ApplicationException
     */
    @SuppressWarnings("unchecked")
    public void voidReturnBackBoneInvoke(Object arg,String endpointName ) throws ApplicationException {
        WrapperResultados res;

        try {

            res = (WrapperResultados) abstractDAO.invoke(endpointName,arg);

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
     * @throws mx.com.aon.core.ApplicationException
     */
    @SuppressWarnings("unchecked")
    public WrapperResultados returnBackBoneInvoke(Object arg,String endpointName ) throws ApplicationException {
        WrapperResultados res;

        try {

            res = (WrapperResultados) abstractDAO.invoke(endpointName,arg);

        } catch (BackboneApplicationException e) {
            logger.error("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName, e);
            throw new ApplicationException("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName,e);
        }

        res = processResultManager.processResultMessageId(res);
        return res;

    }

    /**
     * Metodo que ejecuta backbone y obtiene un registro dado un conjunto de argumentos
     *
     *
     * @param arg Object a pasar a metodo invoke de backbone
     * @param endpointName nombre del endpoint a invocar
     * @return instancia de WrapperResultados cargado con los id de msgs de respuesta y resultados
     * @throws mx.com.aon.core.ApplicationException
     */
    @SuppressWarnings("unchecked")
    public WrapperResultados returnResult(Object arg,String endpointName ) throws ApplicationException {
        WrapperResultados res;

        try {

            res = (WrapperResultados) abstractDAO.invoke(endpointName,arg);

        } catch (BackboneApplicationException e) {
            logger.error("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName, e);
            throw new ApplicationException("No se puedo ejecutar pagedBackBoneInvoke para el endpoint " + endpointName,e);
        }

        //res = processResultManager.processResultMessageId(res);
        return res;

    }
    
    
    @SuppressWarnings("unchecked")
    public List getAllBackBoneInvoke(Object arg,String endpointName) throws ApplicationException {
        WrapperResultados res;

        try {

            res = (WrapperResultados) abstractDAO.invoke(endpointName,arg);

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

        try {

            res = (WrapperResultados) abstractDAO.invoke(endpointName,arg);

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
