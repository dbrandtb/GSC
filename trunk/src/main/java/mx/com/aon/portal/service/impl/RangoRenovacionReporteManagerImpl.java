package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.RangoRenovacionReporteVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.RangoRenovacionReporteManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.tmp.Endpoint;

public class RangoRenovacionReporteManagerImpl extends AbstractManager implements RangoRenovacionReporteManager {
	
    /**
	 *  Inserta o actualiza una rango de renocacion
	 *  Hace uso del Store Procedure PKG_RENOVA.P_GUARDA_RANGO
	 * 
	 *  @param configurarAccionRenovacionVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String agregarGuardarRangoRenovacion(RangoRenovacionReporteVO rangoRenovacionReporteVO)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdRenova",rangoRenovacionReporteVO.getCdRenova());
		map.put("cdRango",rangoRenovacionReporteVO.getCdRango());
		map.put("cdInicioRango",rangoRenovacionReporteVO.getCdInicioRango());
		map.put("cdFinRango",rangoRenovacionReporteVO.getCdFinRango());
		map.put("dsRango",rangoRenovacionReporteVO.getDsRango());
		
        WrapperResultados res =  returnBackBoneInvoke(map,"AGREGAR_GUARDAR_RANGO_RENOVACION");
        return res.getMsgText();

	}

	/**
	 *  Elimina una configuracion de rango de renovacion
	 *  Hace uso del Store Procedure PKG_RENOVA.P_BORRA_RANGO
	 * 
	 *  @param cdRenova
	 *  @param cdRango
	 *    
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	 
	@SuppressWarnings("unchecked")
	public String borrarRangoRenovacion(String cdRenova, String cdRango)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdRenova",cdRenova);
		map.put("cdRango",cdRango);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_RANGO_RENOVACION");
        return res.getMsgText();
	}
	
	/**
	 *  Obtiene un conjunto de rangos de renovacion
	 *  Hace uso del Store Procedure PKG_RENOVA.P_OBTIENE_RANGOS
	 * 
	 *  @param cdRenova
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList buscarRangosRenovacion(String cdRenova, int start,int limit) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdRenova",cdRenova);
		map.put("start",start);
		map.put("limit",limit);

        return pagedBackBoneInvoke(map, "BUSCAR_RANGOS_RENOVACION", start, limit);
	}
	
	/**
	 *  Obtiene un encabezado de rango de renovacion en particular
	 *  Hace uso del Store Procedure PKG_RENOVA.P_OBTIENE_ENCABEZADO
	 * 
	 *  @param cdRenova
	 *  
	 *  @return Objeto ConsultaConfiguracionRenovacionVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public RangoRenovacionReporteVO getEncabezadoRangoRenovacion(String cdRenova)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdRenova",cdRenova);
					
        return (RangoRenovacionReporteVO)getBackBoneInvoke(map,"GET_ENCABEZADO_RANGO_RENOVACION");
	}

	/**
	 *  Obtiene rango de renovacion en particular
	 *  Hace uso del Store Procedure PKG_RENOVA.P_OBTIENE_RANGO
	 * 
	 *  @param cdRenova
	 *  @param cdRango
	 *  
	 *  @return Objeto RangoRenovacionReporteVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public RangoRenovacionReporteVO getRangoRenovacion(String cdRenova,String cdRango) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdRenova",cdRenova);
		map.put("cdRango",cdRango);		
        return (RangoRenovacionReporteVO)getBackBoneInvoke(map,"GET_RANGO_RENOVACION");
	}

    /**
	 *  Obtiene un conjunto de rango de renovacion para la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure PKG_RENOVA.P_OBTIENE_RANGOS
	 *  
	 *  @param cdRenova
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String cdRenova)throws ApplicationException {

		TableModelExport model = new TableModelExport();		
		List lista = null;
		HashMap map = new HashMap();
		map.put("cdRenova",cdRenova);
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_RANGOS_RENOVACION_EXPORT");
		model.setInformation(lista);
		model.setColumnName(new String[]{"Rango","Inicio","Fin"});
		return model;
	}
  
	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}

}
