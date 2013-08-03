/**
 * 
 */
package mx.com.aon.flujos.endoso.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mx.com.aon.configurador.pantallas.model.ConjuntoPantallaVO;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;


/**
 * 
 * Clase Action para el control y visualizacion de datos de la pantalla de consulta de polizas del proceso de endosos
 * 
 * @author aurora.lozada
 * 
 */
public class BusquedaPolizaAction extends PrincipalEndosoAction {

    /**
     * 
     */
    private static final long serialVersionUID = -8183673412381637675L;

    private boolean success;

    @SuppressWarnings("unchecked")
    private List dataGrid;


    /**
     * @return the success
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the dataGrid
     */
    @SuppressWarnings("unchecked")
    public List getDataGrid() {
        return dataGrid;
    }

    /**
     * @param dataGrid the dataGrid to set
     */
    @SuppressWarnings("unchecked")
    public void setDataGrid(List dataGrid) {
        this.dataGrid = dataGrid;
    }
    
    
    

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.ActionSupport#validate()
     */
    @Override
    public void validate() {
        
           logger.debug("### Llegando a validate en BusquedaPolizaAction...");
   
        
    }
    
    /**
     * Metodo que obtiene los datos dummy del grid en la pantalla
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtenerDatos() throws Exception {
        
        boolean isDebugueable = logger.isDebugEnabled();
        
        int contador = 0;
        
        if(isDebugueable){
            logger.debug("######################################################");
            logger.debug("### Llegando a metodo obtenerDatos en BusquedaPolizaAction...");
            logger.debug("######################################################");
        }
        
        ////////Get elementos del filtro///////////////////////////////////
        HttpServletRequest servletReq = ServletActionContext.getRequest();
        Map params = servletReq.getParameterMap();
        Object ob = null;
         
        for (Object key : params.keySet() ) {
             logger.debug(" key is " + key   +" -- value is " + params.get(key.toString()).getClass() );
             ob = params.get(key);
             
             if (ob instanceof String[]) {
                 logger.debug("Array de Strings");

                 for (String s : (String[]) ob) {
                     logger.debug("@@@@ s is " + s);
                 }

             } else if (ob instanceof String) {
                 logger.debug("Simple String");
             } else {
                 logger.debug("class is " + ob.getClass());
             }
        }
        Map<String,String>  parameters = new HashMap<String,String>();
        String key = null;
        Enumeration<?> enumeration = servletReq.getParameterNames();
         
         while( enumeration.hasMoreElements() ){
             key = enumeration.nextElement().toString();
             logger.debug("### entering key is ---"  +  key);
             logger.debug("### value is ---" + servletReq.getParameter(key));
             parameters.put(key, servletReq.getParameter(key) ); 
             
             ///Contador para saber si todos los elementos del filtro vienen vacíos
             if(StringUtils.isBlank(servletReq.getParameter(key))){
                 contador++;
             }
             
         }
         
         logger.debug("contador is " + contador );
         logger.debug("parameter size is " + parameters.size() );
         
         session.put("BUSQUEDA_POLIZA_INPUT",  parameters );
        
        if(contador == parameters.size()){
            logger.debug("### Entrando caso cuando todos estan vacios... ");
            
            logger.debug("### llenando lista del grid cuando no hay valores... ");
            success=false;
        
        }else{
        logger.debug("### llenando lista del grid cuando si hay valores... ");

        ConjuntoPantallaVO item = new ConjuntoPantallaVO();
        item.setCdConjunto("1");
        item.setProceso("Aseg1");
        item.setCliente("ACEREX");
        item.setDescripcion("Desc1");
        item.setNombreConjunto("Nombre conjunto");

        ConjuntoPantallaVO item2 = new ConjuntoPantallaVO();
        item2.setCdConjunto("2");
        item2.setProceso("Aseg2");
        item2.setCliente("ACEREX");
        item2.setDescripcion("Desc2");
        item2.setNombreConjunto("Nombre conjunto 2");

        dataGrid = new ArrayList();

        dataGrid.add(item);
        dataGrid.add(item2);
        success=true;
        
        }//else
        
        return SUCCESS;
    }

    

}
