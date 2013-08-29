package mx.com.aon.portal.dao;

import java.util.HashMap;
import java.util.Map;

import mx.com.gseguros.exception.DaoException;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public abstract class AbstractDAO  extends JdbcDaoSupport {

	/**
	 * Mapa donde se introducen y extraen los managers para utilizarlos como servicios
	 */
	protected Map<String, CustomStoredProcedure> storedProcedures = new HashMap();

	/**
	 * Obtiene un elemento del mapa de Stored Procedures
	 * @param name Nombre del Stored Procedure
	 * @return Objeto que representa al SP
	 */
    public CustomStoredProcedure getStoredProcedure(String name) {
        return storedProcedures.get(name);
    }

    /**
     * Agrega un elemento al mapa de Stored Procedures
     * @param name Nombre del Stored Procedure
     * @param storedProcedure Objeto que representa al SP
     */
    public void addStoredProcedure(String name, CustomStoredProcedure storedProcedure){
        storedProcedures.put(name,storedProcedure);
    }


    /**
     * Invocaci&oacute;n a un Stored Procedure
     * @param storeProcedureName Nombre del SP a ejecutar
     * @param parameters Par&aacute;metros enviados al SP
     * @return Object con la respuesta del SP
     * @throws DaoException
     */
    public Object invoke(String storeProcedureName, Object parameters) throws DaoException {
		try {
			CustomStoredProcedure storedProcedure = getStoredProcedure(storeProcedureName);
			Map result = storedProcedure.execute((Map) parameters);
			return storedProcedure.mapWrapperResultados(result);
		} catch (Exception ex) {
			throw new DaoException("Error inesperado en el acceso a los datos", ex);
		}
    }
    
}