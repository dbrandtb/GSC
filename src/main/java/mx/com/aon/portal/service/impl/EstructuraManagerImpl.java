/*
 * AON
 *
 * Creado el 22/02/2008 07:14:45 p.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.EstructuraVO;
import mx.com.aon.portal.service.EstructuraManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

/**
 *
 * Clase que implementa EstructuraManager para dar respuestas a solicitudes del action.
 *
 */
public class EstructuraManagerImpl extends AbstractManagerJdbcTemplateInvoke implements EstructuraManager{

	/**
	 *  Obtiene una estructura especifica en base a un parametro de entrada.
	 *  Hace uso del Store Procedure PKG_CONF_CUENTA.P_OBTIENE_ESTRUCT_REG.
	 *
	 *  @param codigo
	 *
	 *  @return Objeto EstructuraVO
	 *
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public EstructuraVO getEstructura(String codigo) throws ApplicationException
	{
			HashMap map = new HashMap();
			map.put("pv_cdestruc_i",codigo);
            return (EstructuraVO)getBackBoneInvoke(map,"OBTIENE_ESTRUCT_REG");
	}

	/**
	 *  Inserta o actualiza una estructura.
	 *  Hace uso del Store Procedure PKG_CONF_CUENTA.P_INSERTA_ESTRUCT o PKG_CONF_CUENTA.P_GUARDA_ESTRUCT.
	 *
	 *  @param estructuraVO
	 *  @param pOpcionEstruct: parametro que define si se insertara o actualizara.
	 *
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String saveOrUpdateEstructura(EstructuraVO estructuraVO, String pOpcionEstruct)throws ApplicationException
    {
			HashMap map = new HashMap();
			map.put("pi_cdestruc", estructuraVO.getCodigo());
			map.put("pi_dsestruc", estructuraVO.getDescripcion());

			map.put("pi_cdregion", estructuraVO.getCodigoRegion());
            map.put("Pi_cdidioma", estructuraVO.getCodigoIdioma());

            WrapperResultados res =  returnBackBoneInvoke(map,pOpcionEstruct);
            return res.getMsgText();
	}



    /**
	 *  Elimina una estructura seleccionada.
	 *  Hace uso del Store Procedure PKG_CONF_CUENTA.P_BORRA_ESTRUCT.
	 *
	 *  @param codigo
	 *
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String borraEstructura(String codigo) throws ApplicationException
    {
			HashMap map = new HashMap();
			map.put("pi_cdestruc", codigo);
            WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_ESTRUCT");
            return res.getMsgText();
    }

    /**
	 *  Obtiene un conjunto de estructuras.
	 *  Hace uso del Store Procedure PKG_CONF_CUENTA.P_OBTIENE_ESTRUCT.
	 *
	 *  @param start, limit: marcan el rango de la lista a obtener para la paginacion del grid.
	 *  @param descripcion
	 *
	 *  @return Conjunto de objetos Estructura.
	 *
	 *  @throws ApplicationException
	 */
	@SuppressWarnings({ "unchecked", "unchecked" })
	public PagedList buscarEstructuras(int start, int limit, String descripcion)throws ApplicationException
	{

		HashMap map = new HashMap();
		map.put("pv_dsestruc_i", descripcion);
		String endpointName = "OBTIENE_ESTRUCT";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}

	/**
	 *  Copia una estructura seleccionada.
	 *  Hace uso del Store Procedure PKG_CONF_CUENTA.P_COPIA_ESTRUCT.
	 *
	 *  @param objeto VO
	 *
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String copiaEstructura(EstructuraVO estructuraVO)throws ApplicationException
	{
        HashMap map = new HashMap();
        map.put("pi_cdestruc",estructuraVO.getCodigo());

        WrapperResultados res =  returnBackBoneInvoke(map,"COPIA_ESTRUCT");
        return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String descripcion) throws ApplicationException {

		TableModelExport model = new TableModelExport();

		List lista = null;
		HashMap map = new HashMap();

        map.put("pv_dsestruc_i", descripcion);
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_ESTRUCT_EXPORT");
        model.setInformation(lista);
		model.setColumnName(new String[]{"Codigo","Descripcion"});

		return model;
	}
}