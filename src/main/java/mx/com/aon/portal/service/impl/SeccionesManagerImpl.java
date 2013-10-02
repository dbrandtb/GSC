package mx.com.aon.portal.service.impl;

import mx.com.aon.portal.service.SeccionesManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.SeccionVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Implementacion de la interface de servicios para la configuracion de secciones.
 *
 */
public class SeccionesManagerImpl extends AbstractManager implements SeccionesManager {

    /**
     * Logger de la clase para monitoreo y registro de comportamiento
     */
    @SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(SeccionesManagerImpl.class);
    

	/**
	 *  Obtiene un conjunto de seciones.
	 *  Usa el store procedure PKG_ORDENT.P_OBTIENE_SECCIONES.
	 *  
	 *  @param seccion
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList 
	 */	
    @SuppressWarnings("unchecked")
	public PagedList buscarSecciones(String seccion, int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_dsseccion_i", seccion);
        
        return pagedBackBoneInvoke(map, "BUSCA_SECCIONES", start, limit);
    }

    
	/**
	 *  Agrega una nueva seccion.
	 *  Usa el store procedure PKG_ORDENT.P_GUARDA_SECCION_FORMATO.
	 * 
	 *  @param seccionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
    @SuppressWarnings("unchecked")
	public String agregarGuardarSeccion(SeccionVO seccionVO) throws ApplicationException {
        // Se crea un mapa para pasar los parametros de ejecucion al endpoint
       
    	HashMap map = new HashMap();
       
        map.put("pv_cdseccion_i",seccionVO.getCdSeccion());
        map.put("pv_dsseccion_i",seccionVO.getDsSeccion());
        map.put("pv_cdbloque_i",seccionVO.getCdBloque());
       
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_SECCION");
        return res.getMsgText();
    }
    

    /**
	 *  Realiza la baja de una seccion.
	 *  Usa el store procedure PKG_ORDENT.P_BORRA_SECCION. 
	 * 
	 *  @param cdSeccion
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
    @SuppressWarnings("unchecked")
	public String borrarSeccion(String cdSeccion) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdseccion_i", cdSeccion);

        WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_SECCION");
        return res.getMsgText();
    }

    
	/**
	 *  Obtiene el una seccion.
	 *  Usa el store procedure PKG_ORDENT.P_OBTIENE_SECCION.
	 * 
	 *  @param cdSeccion
	 *  
	 *  @return SeccionVO
	 */		
    @SuppressWarnings("unchecked")
	public SeccionVO getSeccion(String cdSeccion) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdseccion_i", cdSeccion);

        return (SeccionVO)getBackBoneInvoke(map,"OBTIENE_SECCION");
    }


	/**
	  * Obtiene un conjunto de seciones y exporta el resultado en Formato PDF, Excel, CSV, etc.
	  * Usa el store procedure PKG_ORDENT.P_OBTIENE_SECCIONES.
	  *
	  * @param seccion
	  * 
	  * @return TableModelExport
	  */
    @SuppressWarnings("unchecked")
	public TableModelExport getModel(String seccion) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		 map.put("pv_cdseccion_i", seccion);
				
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_SECCION_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Nombre","Bloque"});
		return model;
    }


	/**
	 *  Hace editable un bloque en secciones.
	 *  Usa el store procedure PKG_ORDENT.P_VALIDA_BLOQUE.
	 * 
	 *  @param cdSeccion
	 *  @param cdBloque
	 *  
	 *  @return boolean
	 */		
    @SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
	public boolean isBloqueEditable(String cdSeccion, String cdBloque) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdseccion_i", cdSeccion);
        map.put("pv_cdbloque_i", cdBloque);
        try {
            @SuppressWarnings({ "unused", "unused" })
			WrapperResultados res =  returnBackBoneInvoke(map,"VALIDA_BLOQUE");
            return true;
        } catch (ApplicationException ex) {
            return false;
        }
    }
}
