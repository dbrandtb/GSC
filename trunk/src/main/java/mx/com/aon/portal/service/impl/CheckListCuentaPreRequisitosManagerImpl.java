package mx.com.aon.portal.service.impl;

import mx.com.aon.portal.model.*;
import mx.com.aon.portal.service.CheckListCuentaPreRequisitosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;


/**
 * Implementa la interface de servicios para la configuracion de los pre-requisitos de configuracion de cuenta.
 *
 */
public class CheckListCuentaPreRequisitosManagerImpl  extends AbstractManager  implements
		CheckListCuentaPreRequisitosManager {

    public static String CONFIGURACION_COMPLETA = "1";

	@SuppressWarnings({ "unused", "unused" })
	private static Logger logger = Logger.getLogger(CheckListCuentaPreRequisitosManagerImpl.class);
	

	/**
	 *  Obtiene el encabezado  a partir de los parametros de entrada.
	 *  Este metodo llama al Store Procedure PKG_AON_CHECKLIST.P_OBTIENE_ENCABEZADO
	 * 
	 *  @param codigoConfiguracion
	 *  @param codigoCliente
	 *  
	 *  @return Objeto Lista
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerEncabezados(String codigoConfiguracion,
			String codigoCliente) throws ApplicationException {

			HashMap map = new HashMap ();
			map.put("codigoconfiguracion", codigoConfiguracion);
			map.put("codigocliente", codigoCliente);

            //return getAllBackBoneInvoke(map,"CHKLISTCTA_OBTIENE_ENCABEZADO");
			return pagedBackBoneInvoke(map, "CHKLISTCTA_OBTIENE_ENCABEZADO", 0, 50);
	}

	
	/**
	 *  Obtiene tareas de secciones a partir de los parametros de entrada.
	 *  Este metodo llama al Store Procedure PKG_AON_CHECKLIST.P_OBTIENE_TAREAS_SECCION
	 * 
	 *  @param codigoConfiguracion
	 *  @param codigoCliente
	 *  
	 *  @return Objeto Lista
	 */
	@SuppressWarnings("unchecked")
	public List obtenerTareasSeccion(String codigoConfiguracion,
			String codigoSeccion) throws ApplicationException {

			HashMap map = new HashMap ();
			map.put("codigoconfiguracion", codigoConfiguracion);
			map.put("codigoseccion", codigoSeccion);

            return getAllBackBoneInvoke(map,"CHKLISTCTA_OBTIENE_TAREAS_SECCION");
    }
	

	/**
	 *  Obtiene secciones a partir del codigo de configuracion.
	 *  Este metodo llama al Store Procedure PKG_AON_CHECKLIST.P_OBTIENE_SECCION 
	 * 
	 *  @param codigoConfiguracion

	 *  @return Objeto Lista
	 */
	@SuppressWarnings("unchecked")
	public List obtenerSecciones(String codigoConfiguracion) throws ApplicationException {

			HashMap map = new HashMap();
			map.put("codigoconfiguracion", codigoConfiguracion);

            return getAllBackBoneInvoke(map,"CHKLISTCTA_OBTIENE_SECCION");
	}


	/**
	 *  Devuelve las tareas disponibles para esa configuracion.
	 *  Este metodo llama al Store Procedure PKG_AON_CHECKLIST.P_CONFIGURACION_COMPLETA. 
	 * 
	 *  @param codigoConfiguracion
	 *  
	 *  @return Objeto Lista
	 */
    @SuppressWarnings("unchecked")
	public boolean isConfiguracionTareasCompleta(String codigoConfiguracion) throws ApplicationException {
        HashMap map = new HashMap ();
        map.put("codigoconfiguracion", codigoConfiguracion);
        WrapperResultados wrapperResultados = returnBackBoneInvoke(map,"CHKLISTCTA_CONFIGURACION_COMPLETA");
        if (wrapperResultados != null && wrapperResultados.getResultado()!= null && wrapperResultados.getResultado().equals(CONFIGURACION_COMPLETA)) {
            return true;
        } else {
            return false;
        }
    }

	/**
	 * Inserta Nueva o actualiza Configuración de CheckList de Cuenta
	 * Este metodo llama al Store Procedure PKG_AON_CHECKLIST.P_GUARDA_TAREA_SECCION_CTA
	 * 
	 * @param codigoConfiguracion
	 * @param codigoCliente
	 * @param codigoSeccion
	 * @param descripcionConf
	 * @param lineaOperacion
	 * @param checkListCuentaPreRequisitosVO
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String guardaPreRequisito(String codigoConfiguracion, String cdPerson,
			String codigoCliente, String codigoSeccion, String descripcionConf,
			String lineaOperacion,
			List<CheckListCuentaPreRequisitosVO>listaTareas)
			throws ApplicationException {

    	if (codigoConfiguracion == null  || codigoConfiguracion.equals("")) {

				for (int j=0; j<listaTareas.size(); j++){
					CheckListCuentaPreRequisitosVO requisitosVO = listaTareas.get(j);
					HashMap map = new HashMap();
					map.put("codigoconfiguracion", codigoConfiguracion);
					map.put("descripcionconfig", descripcionConf);
					map.put("linope", lineaOperacion);
                    map.put("codigocliente", codigoCliente);
                    map.put("cdPerson", cdPerson);
					map.put("codigoseccion", codigoSeccion);
					map.put("codigotarea", requisitosVO.getCdTarea());
					map.put("codigocompletada", requisitosVO.getCdCompletada());
					map.put("codigorequerida", requisitosVO.getNoRequerida());
					map.put("codigopendiente", requisitosVO.getCdPendiente());
					            
					if (codigoConfiguracion == null || codigoConfiguracion.equals("")) {
						WrapperResultados res = returnBackBoneInvoke(map, "CHKLIST_INSERTA_TAREA_SECCION_CTA");
						codigoConfiguracion = res.getResultado(); 
						//return codigoConfiguracion;
					} else {
						voidReturnBackBoneInvoke(map,"CHKLIST_GUARDA_TAREA_SECCION_CTA");
					}
				}
    		
    	} else {
			for (int i=0; i<listaTareas.size(); i++) {
				HashMap map = new HashMap();
				CheckListCuentaPreRequisitosVO requisitosVO = listaTareas.get(i);
				map.put("codigoconfiguracion", codigoConfiguracion);
				map.put("descripcionconfig", descripcionConf);
				map.put("linope", lineaOperacion);
				map.put("codigocliente", codigoCliente);
				map.put("codigoseccion", codigoSeccion);
				map.put("codigotarea", requisitosVO.getCdTarea());
				map.put("codigocompletada", requisitosVO.getCdCompletada());
				map.put("codigorequerida", requisitosVO.getNoRequerida());
				map.put("codigopendiente", requisitosVO.getCdPendiente());
	            voidReturnBackBoneInvoke(map,"CHKLIST_GUARDA_TAREA_SECCION_CTA");
			}
    	}
    	return codigoConfiguracion;
	}
}