package mx.com.aon.portal.service.impl;

import mx.com.aon.portal.service.RequisitosRehabilitacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.RequisitoRehabilitacionVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements RequisitosRehabilitacionManager
 * 
 * @extends AbstractManager
 */
public class RequisitosRehabilitacionManagerImpl extends AbstractManager implements RequisitosRehabilitacionManager {

	/**
	 *  Inserta o actualiza un requisito de rehabilitacion
	 *  Hace uso del Store Procedure PKG_CANCELA.P_GUARDA_REQUISITO_REHAB
	 * 
	 *  @param requisitoRehabilitacionVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	   
	@SuppressWarnings("unchecked")
	public String agregarGuardarRequisitoRehabilitacion(RequisitoRehabilitacionVO requisitoRehabilitacionVO)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdRequisito",requisitoRehabilitacionVO.getCdRequisito());
		map.put("dsRequisito",requisitoRehabilitacionVO.getDsRequisito());
		map.put("cdElemento",requisitoRehabilitacionVO.getCdElemento());
		map.put("cdUnieco",requisitoRehabilitacionVO.getCdUnieco());
		map.put("cdRamo",requisitoRehabilitacionVO.getCdRamo());
		map.put("cdPerson",requisitoRehabilitacionVO.getCdPerson());
		map.put("cdDocXcta",requisitoRehabilitacionVO.getCdDocXcta());
       
        WrapperResultados res =  returnBackBoneInvoke(map,"AGREGAR_GUARDAR_REQUISITO_REHABILITACION");
        return res.getMsgText();
	}

	/**
	 *  Elimina una configuracion de requisitos de rehabilitacion
	 *  Hace uso del Store Procedure PKG_CANCELA.P_ELIMINA_REQUISITO_REHABILITA
	 * 
	 *  @param cdRequisito
	 *  @param cdUnieco
	 *  @param cdRamo
	 *  @param cdElemento
	 *  @param cdDocXcta
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borrarRequisitoRehabilitacion(String cdRequisito, String cdUnieco, String cdRamo, String cdElemento, String cdDocXcta)throws ApplicationException {
		    HashMap map = new HashMap();
	        map.put("cdRequisito", cdRequisito);
			map.put("cdUnieco",cdUnieco);
			map.put("cdRamo",cdRamo);
			map.put("cdElemento",cdElemento);
			map.put("cdDocXcta",cdDocXcta);

	        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_REQUISITO_REHABILITACION");
	        return res.getMsgText();
	}

	/**
	 *  Obtiene un conjunto de requisitos de rehabilitacion
	 *  Hace uso del Store Procedure PKG_CANCELA.P_REQUISITOS_REHABILITA
	 * 
	 *  @param dsElemen
	 *  @param dsUnieco
	 *  @param dsRamo
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList buscarRequisitosRehabilitacion(String dsElemen, String dsUnieco, String dsRamo, int start, int limit)throws ApplicationException {
		    HashMap map = new HashMap();
			map.put("dsElemen", dsElemen);
			map.put("dsUnieco", dsUnieco);
	        map.put("dsRamo", dsRamo);
	        
	        return pagedBackBoneInvoke(map, "BUSCAR_REQUISITOS_REHABILITACION", start, limit);
	}

	/**
	 *  Obtiene un requisito de rehabilitacion en particular
	 *  Hace uso del Store Procedure PKG_CANCELA.P_EDITA_REQUISITO
	 * 
	 *  @param cdRequisito
	 *  
	 *  @return Objeto RequisitoRehabilitacionVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public RequisitoRehabilitacionVO getRequisitoRehabilitacion(String cdRequisito)throws ApplicationException {
		 HashMap map = new HashMap();
	        map.put("cdRequisito", cdRequisito);
	        return (RequisitoRehabilitacionVO)getBackBoneInvoke(map,"OBTENER_REQUISITO_REHABILITACION");
	}

    /**
	 *  Obtiene un conjunto de personas para la exportacion a un requisito de rehabilitacion
	 *  Hace uso del Store Procedure PKG_CANCELA.P_REQUISITOS_REHABILITA
	 *  
	 *  @param dsElemen
	 *  @param dsUnieco
	 *  @param maximo
	 *  @param dsRamo
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */ 
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsElemen, String dsUnieco, String dsRamo) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		List lista = null;
		HashMap map = new HashMap();
		map.put("dsElemen", dsElemen);
		map.put("dsUnieco", dsUnieco);
        map.put("dsRamo", dsRamo);
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTENER_REQUISITOS_REHABILITACION_EXPORT");
		model.setInformation(lista);
		model.setColumnName(new String[]{"Cliente","Asegurado","Producto","Descripcion"});
		return model;
	}

}
