package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.MensajeErrorVO;
import mx.com.aon.portal.service.MensajesErrorManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;


public class MensajesErrorManagerImpl extends AbstractManager implements MensajesErrorManager {
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros de Mensajes de error.
	 * Invoca el Store Procedure PKG_ERROR.P_OBTIENE_MENSAJE.
	 * 
	 * @param cdError
	 * @param dsMensaje
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList buscarMensajes(String cdError, String dsMensaje, int start, int limit) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdError", cdError);
		map.put("dsMensaje", dsMensaje);

		return pagedBackBoneInvoke(map, "MENSAJES_ERROR_BUSCAR", start, limit);
	}
	
	/**
	 * Metodo que busca y obtiene un unico registro de Mensajes de error.
	 * Invoca el Store Procedure PKG_ERROR.P_OBTIENE_ERROR
	 * 
	 * @param cdError
	 * 
	 * @return MensajeErrorVO
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public MensajeErrorVO getMensajeError(String cdError) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdError", cdError);

		return (MensajeErrorVO)getBackBoneInvoke(map, "MENSAJES_ERROR_GET");
	}
	
	/**
	 * Metodo que inserta un nuevo o actualiza un registro de mensaje de error.
	 * Invoca el Store Procedure PKG_ERROR.P_GUARDA_MENSAJE
	 * 
	 * @param cdError
	 * @param dsMensaje
	 * @param cdTipo
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String guardarMensajeError(String cdError, String dsMensaje, String cdTipo) throws ApplicationException{
		HashMap map = new HashMap ();
		map.put("cdError", cdError);
		map.put("dsMensaje", dsMensaje);
		map.put("cdTipo", cdTipo);

		WrapperResultados res = returnBackBoneInvoke(map, "MENSAJES_ERROR_GUARDAR");
		return res.getMsgText();
	}
	
	/**
	 * Obtiene un conjunto de registros de mensajes de error y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * Invoca el Store Procedure PKG_ERROR.P_OBTIENE_MENSAJE
	 * 
	 * @return TableModelExport
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String cdError, String dsMensaje) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		List lista = null;
		
		HashMap map = new HashMap();
		map.put("cdError", cdError);
		map.put("dsMensaje", dsMensaje);

		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "MENSAJES_ERROR_EXPORTAR");
		model.setInformation(lista);
		model.setColumnName(new String[]{"Codigo", "Mensaje", "Tipo"});

		return model;
	}

}
