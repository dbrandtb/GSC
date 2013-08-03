/**
 * 
 */
package mx.com.aon.flujos.endoso.web;


import java.util.ArrayList;
import java.util.List;

import mx.com.aon.configurador.pantallas.model.ConjuntoPantallaVO;

/**
 * 
 * Clase Action para el control y visualizacion de datos de la pantalla de Datos generales del proceso de endosos
 * 
 * @author aurora.lozada
 * 
 */
public class DatosGeneralesAction extends PrincipalEndosoAction{

    /**
     * 
     */
    private static final long serialVersionUID = -4382962442272483185L;
    
    private boolean success;

    @SuppressWarnings("unchecked")
    private List dataGrid;

    private List<ConjuntoPantallaVO> registroList = new ArrayList<ConjuntoPantallaVO>();

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
        
           logger.debug("### Llegando a validate en DatosGeneralesAction...");
   
        
    }

    /**
     * Metodo que obtiene datos resultado dummy para el grid de la pantalla
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtenerDatosGrid() throws Exception {
        
        boolean isDebugueable = logger.isDebugEnabled();
        if(isDebugueable){
            logger.debug("######################################################");
            logger.debug("### Llegando a metodo obtenerDatosGrid en DatosGeneralesAction...");
            logger.debug("######################################################");
        }
       
        logger.debug("### llenando lista del grid... ");

        ConjuntoPantallaVO item = new ConjuntoPantallaVO();
        item.setCdConjunto("1");
        item.setProceso("Datos 1");
        item.setCliente("Valor dato 1");
        item.setDescripcion("Descripcion del dato 1");
       
        ConjuntoPantallaVO item2 = new ConjuntoPantallaVO();
        item2.setCdConjunto("2");
        item2.setProceso("Datos 2");
        item2.setCliente("Valor dato 2");
        item2.setDescripcion("Descripcion del dato 2");
      
        dataGrid = new ArrayList();

        dataGrid.add(item);
        dataGrid.add(item2);
        success=true;
       return SUCCESS;
    }

    /**
     * Metodo que obtiene datos dummy para la zona de Datos de la pantalla
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtenerDatosRegistro() throws Exception {
        
        boolean isDebugueable = logger.isDebugEnabled();
        if(isDebugueable){
            logger.debug("######################################################");
            logger.debug("### Llegando a metodo obtenerDatosRegistro en DatosGeneralesAction...");
            logger.debug("######################################################");
        }
       
        logger.debug("#######Enterintg into method OBTENER_REGISTRO...");
         ConjuntoPantallaVO conjunto = new ConjuntoPantallaVO();
         logger.debug("Entrando a la Opcion----- Cargar registro....");
         //conjunto = configuradorManager.getConjunto(id);
        
            conjunto.setCdConjunto("12335685");
            conjunto.setCdProceso("3");
            conjunto.setProceso("Endoso");
            conjunto.setCliente("ejemplo");
            conjunto.setNombreConjunto("28/05/2008");
            conjunto.setDescripcion("0.00");
            registroList.add(conjunto);
       
       
        success=true;
       return SUCCESS;
    }

    /**
     * @return the registroList
     */
    public List<ConjuntoPantallaVO> getRegistroList() {
        return registroList;
    }

    /**
     * @param registroList the registroList to set
     */
    public void setRegistroList(List<ConjuntoPantallaVO> registroList) {
        this.registroList = registroList;
    }


}
