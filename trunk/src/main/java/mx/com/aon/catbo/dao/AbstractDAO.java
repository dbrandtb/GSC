package mx.com.aon.catbo.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.dao.DataAccessException;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;
import com.wittyconsulting.backbone.service.Service;

import java.util.Map;
import java.util.HashMap;

public abstract class AbstractDAO  extends JdbcDaoSupport {

/**
	 * Mapa en el cual se introducen los Manager's para ser extraidos y utilizados como servicios
	 */
	protected Map<String, CustomStoredProcedure> storedProcedures = new HashMap();

    public void setService(Service service) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Service getService() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


	public CustomStoredProcedure getStoredProcedure(String name) {
        return storedProcedures.get(name);
    }

    public void addStoredProcedure(String name, CustomStoredProcedure storedProcedure){
        storedProcedures.put(name,storedProcedure);
    }


    public Object invoke(String storeProcedureName, Object parameters) throws BackboneApplicationException {
          try {
              CustomStoredProcedure storedProcedure = getStoredProcedure(storeProcedureName);
              Map result = storedProcedure.execute((Map)parameters);
              return storedProcedure.mapWrapperResultados(result);              
          } catch (DataAccessException ex) {
              throw new BackboneApplicationException("Error en el acceso a la base de datos",ex);
          } catch (Exception ex) {
              throw new BackboneApplicationException("Error inesperado en el acceso a los datos",ex);
          }
    }
    
}
