package mx.com.aon.portal.service.impl;

import java.util.List;

import mx.com.aon.portal.dao.AbstractDAO;
import mx.com.aon.portal.service.ProcessResultManagerJdbcTemplate;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.DaoException;

import org.apache.log4j.Logger;

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
     * Metodo que ejecuta backbone y obtiene un registro dado un conjunto de argumentos
     *
     *
     * @param arg Object a pasar a metodo invoke de backbone
     * @param endpointName nombre del endpoint a invocar
     * @return instancia de WrapperResultados cargado con los id de msgs de respuesta y resultados
     * @throws mx.com.gseguros.exception.ApplicationException
     */
    @SuppressWarnings("unchecked")
    public Object getBackBoneInvoke(Object arg,String endpointName ) throws ApplicationException {
        WrapperResultados res;

        try {

            res = (WrapperResultados) abstractDAO.invoke(endpointName,arg);

        } catch (DaoException e) {
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
     * @throws mx.com.gseguros.exception.ApplicationException
     */
    @SuppressWarnings("unchecked")
    public WrapperResultados returnBackBoneInvoke(Object arg,String endpointName ) throws ApplicationException {
        WrapperResultados res;

        try {

            res = (WrapperResultados) abstractDAO.invoke(endpointName,arg);

        } catch (DaoException e) {
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
     * @throws mx.com.gseguros.exception.ApplicationException
     */
    @SuppressWarnings("unchecked")
    public WrapperResultados returnResult(Object arg,String endpointName ) throws ApplicationException {
        WrapperResultados res;

        try {

            res = (WrapperResultados) abstractDAO.invoke(endpointName,arg);

        } catch (DaoException e) {
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

        } catch (DaoException e) {
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

}
