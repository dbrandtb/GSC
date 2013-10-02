package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.MetodoCancelacionVO;
import mx.com.aon.portal.service.MetodosCancelacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;


/**
 * Implementacion de la interface de servicios para la metodos de cancelacion.
 *
 */
public class MetodosCancelacionManagerImpl extends AbstractManager implements MetodosCancelacionManager {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(MetodosCancelacionManagerImpl.class);


	/**
	 *  Agrega metodos de cancelacion.
	 *  Usa el store procedure PKG_CANCELA.P_GUARDA_METODO.
	 * 
	 *  @param metodoCancelacionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	@SuppressWarnings("unchecked")
	public String agregarGuardarMetodoCancelacion(MetodoCancelacionVO metodoCancelacionVO)throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("cdMetodo",metodoCancelacionVO.getCdMetodo());
		map.put("dsMetodo",metodoCancelacionVO.getDsMetodo());
		map.put("pv_cdexprespndp_i",metodoCancelacionVO.getPv_cdexprespndp_i());
		map.put("pv_cdexprespndt_i",metodoCancelacionVO.getPv_cdexprespndt_i());
		
		WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_GUARDA_METODO_CANCELACION");
        return res.getMsgText();
	}
	
	/**
	 *  Borra metodos de cancelacion.
	 *  Usa el store procedure PKG_CANCELA.P_BORRA_METODO.
	 * 
	 *  @param metodoCancelacionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	@SuppressWarnings("unchecked")
	public String borrarMetodoCancelacion(MetodoCancelacionVO metodoCancelacionVO)throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdmetodo_i",metodoCancelacionVO.getCdMetodo());
				
		WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_METODO_CANCELACION");
        return res.getMsgText();
	}


	/**
	 *  Obtiene un conjunto de metodos de cancelacion.
	 *  Usa el store procedure PKG_CANCELA.P_GUARDA_METODO.
	 *  
	 *  @param cdMetodo
	 *  @param dsMetodo
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList 
	 */	
	@SuppressWarnings("unchecked")
	public PagedList buscarMetodosCancelacion(String cdMetodo, String dsMetodo,int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
        map.put("cdMetodo",cdMetodo);
		map.put("dsMetodo",dsMetodo);
		
		return pagedBackBoneInvoke(map, "OBTIENE_METODOS_CANCELACION", start, limit);
	}


	/**
	 *  Obtiene metodos de cancelacion.
	 *  Usa el store procedure PKG_CANCELA.P_OBTIENE_METODO.
	 * 
	 *  @param cdMetodo
	 *  
	 *  @return MetodoCancelacionVO
	 */		
	@SuppressWarnings("unchecked")
	public MetodoCancelacionVO getMetodoCancelacion(String cdMetodo)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdMetodo",cdMetodo);
		
		return (MetodoCancelacionVO)getBackBoneInvoke(map,"OBTIENE_METODO_CANCELACION_REG");
	}


	/**
	  * Obtiene un conjunto de metodos de cancelacion y los exporta en Formato PDF, Excel, CSV, etc.
	  * Usa el store procedure PKG_CANCELA.P_OBTIENE_METODOS.
	  *
	  * @param cdMetodo
	  * @param dsMetodo
	  * 
	  * @return TableModelExport
	  */	
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String cdMetodo, String dsMetodo)throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("cdMetodo",cdMetodo);
		map.put("dsMetodo",dsMetodo);
				
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_METODOS_CANCELACION_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Codigo","Descripcion"});
		return model;
	}

}
