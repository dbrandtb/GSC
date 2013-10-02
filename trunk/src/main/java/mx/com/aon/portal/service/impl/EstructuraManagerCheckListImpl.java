package mx.com.aon.portal.service.impl;


import mx.com.aon.portal.model.ObtienetareaVO;
import mx.com.aon.portal.service.EstructuraManagerTareasChecklist;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

import java.util.HashMap;

/**
 * 
 * Clase que implementa EstructuraManagerTareasChecklist para dar respuestas a solicitudes del action.
 *
 */
public class EstructuraManagerCheckListImpl extends AbstractManager implements EstructuraManagerTareasChecklist {



    public static String EXISTE_TAREA = "1";

    public EstructuraManagerCheckListImpl() {
	}

  
    /**
	 *  Obtiene un conjunto de tareas.
	 *  Hace uso del Store Procedure PKG_AON_CHECKLIST.P_OBTIENE_TAREAS.
	 * 
	 *  @param seccion
	 *  @param tarea
	 *  @param estado
	 *    
	 *  @return Conjunto de objetos Tarea.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList getTareas(String seccion, String tarea, String estado, int start, int limit) throws ApplicationException {


			HashMap map = new HashMap();
			map.put("seccion", seccion);
			map.put("tarea", tarea);
			map.put("estado", estado);

            return pagedBackBoneInvoke(map, "P_OBTIENE_TAREAS", start, limit);

    }

	/**
	 *  Obtiene un conjunto de tareas.
	 *  Hace uso del Store Procedure PKG_AON_CHECKLIST.P_LISTA_TAREAS.
	 * 
	 *  @param seccion
	 *  @param tarea
	 *  
	 *  @return Conjunto de objetos Tarea
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerListaTareasChecklist(String seccion, String tarea, int start, int limit) throws ApplicationException
	{	
		HashMap map = new HashMap();
		map.put("seccion", seccion);
		map.put("tarea", tarea);
		
		return pagedBackBoneInvoke(map, "P_LISTA_TAREAS", start, limit);	
	}

	/**
	 *  Valida si existe una tareas especifica.
	 *  Hace uso del Store Procedure PKG_AON_CHECKLIST.P_VALIDA_BORRA_TAREA.
	 * 
	 *  @param codSeccion
	 *  @param codTarea
	 *  
	 *  @return si existe o no la Tarea
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public boolean existeTarea(String codSeccion, String codTarea) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("seccion", codSeccion);
        map.put("tarea", codTarea);

        WrapperResultados wrapperResultados = returnBackBoneInvoke(map,"P_VALIDA_BORRA_TAREA");
        if (wrapperResultados != null && wrapperResultados.getResultado().equals(EXISTE_TAREA)) {
            return true;
        } else {
            return false;
        }

    }

    /**
	 *
     * @deprecated
	 */
	//PKG_AON_CHECKLIST.P_VALIDA_BORRA_TAREA
	@SuppressWarnings("unchecked")
	public WrapperResultados validaBorraTarea(String codSeccion, String codTarea)
			throws ApplicationException {

			HashMap map = new HashMap();
			map.put("seccion", codSeccion);
			map.put("tarea", codTarea);
			WrapperResultados wrapperResultados = returnBackBoneInvoke(map,"P_VALIDA_BORRA_TAREA");;
            return wrapperResultados;
            //return wrapperResultados.getResultado();

    }
	
	/**
	 *  Borra una tarea especifica.
	 *  Hace uso del Store Procedure PKG_AON_CHECKLIST.P_BORRA_TAREA.
	 * 
	 *  @param codSeccion
	 *  @param codTarea
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borrarTarea(String codSeccion, String codTarea) throws ApplicationException {

			HashMap map = new HashMap();
			map.put("seccion", codSeccion);
			map.put("tarea", codTarea);
            WrapperResultados res =  returnBackBoneInvoke(map,"P_BORRA_TAREA");
            return res.getMsgText();
    }

	/**
	 *  Guarda una tarea especifica.
	 *  Hace uso del Store Procedure PKG_AON_CHECKLIST.P_GUARDA_TAREA.
	 * 
	 *  @param codSeccion
	 *  @param tarea
	 *  @param dsTarea
	 *  @param tareaPadre
	 *  @param estado
	 *  @param url
	 *  @param copia
	 *  @param ayuda
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String guardarTarea(String codSeccion, String tarea, String dsTarea, String tareaPadre, String estado, String url, String copia,String ayuda) throws ApplicationException{

			HashMap map = new HashMap();
			map.put("seccion", codSeccion);
			map.put("tarea", tarea);
			map.put("dstarea", dsTarea);
			map.put("tareaPadre", tareaPadre);
			map.put("estado", estado);
			map.put("url", url);
			map.put("copia", copia);
			map.put("ayuda", ayuda);

            WrapperResultados res =  returnBackBoneInvoke(map,"P_GUARDA_TAREA");
            return res.getMsgText();

	}

	/**
	 *  Obtiene una tarea especifica.
	 *  Hace uso del Store Procedure PKG_AON_CHECKLIST.P_OBTIENE_TAREA.
	 * 
	 *  @param seccion
	 *  @param tarea
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public ObtienetareaVO getTarea(String seccion, String tarea) throws ApplicationException {

			HashMap map = new HashMap();
			map.put("seccion", seccion);
			map.put("tarea", tarea);

            return (ObtienetareaVO)getBackBoneInvoke(map,"P_OBTIENE_TAREA");
	}

	
	

	

}