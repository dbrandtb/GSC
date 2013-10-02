package mx.com.aon.portal.service.impl;

import mx.com.aon.portal.service.AdministrarEquivalenciaCatalogosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.EquivalenciaCatalogosVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.gseguros.exception.ApplicationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements AdministrarEquivalenciaCatalogosManager
 * 
 * @extends AbstractManager
 */
public class AdministrarEquivalenciaCatalogosManagerImpl extends AbstractManagerJdbcTemplateInvoke implements AdministrarEquivalenciaCatalogosManager {
    
    /**
	 *  Obtiene un conjunto de Equivalencia de Catalogos.
	 *  Usa el Store Procedure PKG_EQUIVALENCIA.P_OBTIENE_MEQUVTAB.
	 *  
	 *  @return un objeto PagedLis con un conjunto de registros.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
    public PagedList buscarEquivalenciaCatalogos(String cdPais, String cdSistema, String cdTablaAcw, String cdTablaExt, String indUso, String dsUsoAcw, int start, int limit ) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_country_name_i", cdPais);
        map.put("pv_cdtablaacw_i", cdTablaAcw);
        map.put("pv_cdsistema_i", cdSistema); // NO ES nmtabla??? ojo con que mando de pantalla
        map.put("pv_cdtablaext_i", cdTablaExt);
        map.put("pv_induso_i", indUso);
        map.put("pv_dsusoacw_i", dsUsoAcw);
       
        return pagedBackBoneInvoke(map, "BUSCAR_EQUIVALENCIA_CATALOGOS", start, limit);
    }

    /**
	 *  Inserta una nueva Equivalencia de catalogos.
	 *  Usa el Store Procedure PKG_EQUIVALENCIA.P_GUARDA_MEQUVTAB.
	 * 
	 *  @param equivalenciaCatalogosVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    //@SuppressWarnings("unchecked")
	public String agregarEquivalenciaCatalogo(EquivalenciaCatalogosVO equivalenciaCatalogosVO) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_country_code_i",equivalenciaCatalogosVO.getCountryCode());
        map.put("pv_cdsistema_i",equivalenciaCatalogosVO.getCdSistema());
        map.put("pv_nmtabla_i",ConvertUtil.nvl(equivalenciaCatalogosVO.getNmTabla()));
        map.put("pv_cdtablaacw_i",equivalenciaCatalogosVO.getCdTablaAcw());
        map.put("pv_cdtablaext_i",equivalenciaCatalogosVO.getCdTablaExt());
        map.put("pv_induso_i",equivalenciaCatalogosVO.getIndUso());
        map.put("pv_nmcolumna_i",ConvertUtil.nvl(equivalenciaCatalogosVO.getNmColumna()));
        map.put("pv_dsusoacw_i",equivalenciaCatalogosVO.getDsUsoAcw());

        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_EQUIVALENCIA_CATALOGO");
        return res.getMsgText();
    }
    
    
    /**
	 *  Elimina una Equivalencia de Catalogos seleccionada.
	 *  Usa el Store Procedure PKG_EQUIVALENCIA.P_BORRA_MEQUVTAB.
	 * 
	 *  @param cdPais
	 *  @param cdSistema
	 *  @param nmTabla
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String borrarAgrupacionPoliza(String cdPais, String cdSistema, String nmTabla) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_country_code_i", cdPais);
        map.put("pv_cdsistema_i", cdSistema);
        map.put("pv_nmtabla_i", nmTabla);

        WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_EQUIVALENCIA_CATALOGOS");
        return res.getMsgText();
    }
    
    /**
	 *  Obtiene una Equivalencia de Catalogos seleccionada.
	 *  Usa el Store Procedure .
	 * 
	 *  @param cveAgrupa
	 *  
	 *  @return Objeto EquivalenciaCatalogosVO
	 *  
	 *  @throws ApplicationException
	 */
    //@SuppressWarnings("unchecked")
	/*public EquivalenciaCatalogosVO getEquivalenciaCatalogo(String cveAgrupa) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cve_agrupa_i", cveAgrupa);

        return (AgrupacionPolizaVO)getBackBoneInvoke(map,"OBTIENE_AGRUPACION");
    }*/
    
    /**
	 *  Obtiene una lista de registros de Equivalencia de Catalogos para la exportacion a un formato predeterminado.
	 * 
	 *  @param cdPais
	 *  @param cdSistema
	 *  @param cdTablaAcw
	 *  @param cdTablaExt
	 *  @param indUso
	 *  @param dsUsoAcw
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public TableModelExport getModel(String cdPais, String cdSistema, String cdTablaAcw, String cdTablaExt, String indUso, String dsUsoAcw) throws ApplicationException {
    	// Se crea el objeto de respuesta
		TableModelExport model = new TableModelExport();
		
		List lista = null;
	    HashMap map = new HashMap();
	    map.put("pv_country_name_i", cdPais);
        map.put("pv_cdtablaacw_i", cdTablaAcw);
        map.put("pv_cdsistema_i", cdSistema); // NO ES nmtabla??? ojo con que mando de pantalla
        map.put("pv_cdtablaext_i", cdTablaExt);
        map.put("pv_induso_i", indUso);
        map.put("pv_dsusoacw_i", dsUsoAcw);         
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "BUSCAR_EQUIVALENCIA_CATALOGOS_EXPORT");

		model.setInformation(lista);
		// Se agregan los nombre de las columnas del modelo de datos
		model.setColumnName(new String[]{"Pais","Código de Sistema","Tabla AON Catweb","Sistema Externo","Num Tabla","Uso","Descripción","Columnas"});
		
		return model;
    }
}
