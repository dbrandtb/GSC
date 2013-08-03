package mx.com.aon.portal.service.impl;

import mx.com.aon.portal.service.ConfiguracionAtributosFormatosOrdenTrabajoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.ConfiguracionAtributoFormatoOrdenTrabajoVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;
import java.util.HashMap;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements ConfiguracionAtributosFormatosOrdenTrabajoManager
 * 
 * @extends AbstractManager
 */
public class ConfiguracionAtributosFormatosOrdenTrabajoManagerImpl extends AbstractManager implements ConfiguracionAtributosFormatosOrdenTrabajoManager {


	/**
	 *  Inserta o actualiza una configuracion atributo formato ordenes de trabajo
	 *  Hace uso del Store Procedure PKG_ORDENT.P_GUARDA_ATRIB_SECCION
	 * 
	 *  @param configuracionAtributoFormatoOrdenTrabajoVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	   
	@SuppressWarnings("unchecked")
	public String agregarGuardarConfiguracionAtributoFormatoOrdenTrabajo(ConfiguracionAtributoFormatoOrdenTrabajoVO configuracionAtributoFormatoOrdenTrabajoVO)throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdformatoorden_i",configuracionAtributoFormatoOrdenTrabajoVO.getCdFormatoOrden());
        map.put("pv_cdseccion_i",configuracionAtributoFormatoOrdenTrabajoVO.getCdSeccion());
        map.put("pv_cdatribu_i",configuracionAtributoFormatoOrdenTrabajoVO.getCdAtribu());
        map.put("pv_dsatribu_i",configuracionAtributoFormatoOrdenTrabajoVO.getDsAtribu());
        map.put("pv_cdbloque_i",configuracionAtributoFormatoOrdenTrabajoVO.getCdBloque());
        map.put("pv_cdcampo_i",configuracionAtributoFormatoOrdenTrabajoVO.getCdCampo());
        map.put("pv_nmorden_i",configuracionAtributoFormatoOrdenTrabajoVO.getNmOrden());
        map.put("pv_ottabval_i",configuracionAtributoFormatoOrdenTrabajoVO.getOtTabVal());
        map.put("pv_swformat_i",configuracionAtributoFormatoOrdenTrabajoVO.getSwFormat());
        map.put("pv_nmlmin_i",configuracionAtributoFormatoOrdenTrabajoVO.getNmlMin());
        map.put("pv_nmlmax_i",configuracionAtributoFormatoOrdenTrabajoVO.getNmlMax());
        map.put("pv_cdexpres_i",configuracionAtributoFormatoOrdenTrabajoVO.getCdExpres());
      
        WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_GUARDA_ATRIB_SECCION");
        return res.getMsgText();
	}

	/**
	 *  Elimina una configuracion atributo formato ordenes de trabajo
	 *  Hace uso del Store Procedure PKG_ORDENT.P_BORRA_ATRIBUTO
	 * 
	 *  @param cdFormatoOrden
	 *  @param cdSeccion
	 *  @param cdAtribu
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borrarConfiguracionAtributoFormatoOrdenTrabajo(String cdFormatoOrden, String cdSeccion, String cdAtribu)throws ApplicationException {
		   HashMap map = new HashMap();
	       map.put("pv_cdformatoorden_i", cdFormatoOrden);
	       map.put("pv_cdseccion_i", cdSeccion);
	       map.put("pv_cdatribu_i", cdAtribu);
	      
	       WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_ATRIBUTO");
	       return res.getMsgText();
	}

	/**
	 *  Obtiene un conjunto de configuracion atributo formato ordenes de trabajo
	 *  Hace uso del Store Procedure PKG_ORDENT.P_OBTIENE_ATRIB_SECCION
	 * 
	 *  @param cdFormatoOrden
	 *  @param cdSeccion
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList buscarConfiguracionAtributoFormatoOrdenTrabajo(String cdFormatoOrden, String cdSeccion, int start, int limit)throws ApplicationException {
		  HashMap map = new HashMap();
	      map.put("pv_cdformatoorden_i", cdFormatoOrden);
	      map.put("pv_cdseccion_i", cdSeccion);
	      return pagedBackBoneInvoke(map, "OBTIENE_ATRIB_SECCION", start, limit);
	}
}
