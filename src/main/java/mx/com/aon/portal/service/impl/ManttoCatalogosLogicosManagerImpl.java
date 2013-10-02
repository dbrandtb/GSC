package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.CatalogoLogicoVO;
import mx.com.aon.portal.service.ManttoCatalogosLogicosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

public class ManttoCatalogosLogicosManagerImpl extends AbstractManager implements ManttoCatalogosLogicosManager{

	@SuppressWarnings("unchecked")
	public String borrarRegistroCatalogoLogico(String cdTabla, String cdRegion,
			String cdIdioma, String codigo) throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", cdTabla);
		map.put("pv_cdregion_i", cdRegion);
		map.put("pv_cdidioma_i", cdIdioma);
		map.put("pv_codigo_i", codigo);
		
		String endpointName = "BORRA_TCATALOG";
		WrapperResultados msg = returnBackBoneInvoke(map, endpointName);
		return msg.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String cdTabla, String cdRegion,
			String cdIdioma, String codigo, String descripcion)
			throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", cdTabla);
		map.put("pv_cdregion_i", cdRegion);
		map.put("pv_cdidioma_i", cdIdioma);
		map.put("pv_codigo_i", codigo);
		map.put("pv_descripc_i", descripcion);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_CATLOG_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Tabla","Region","Idioma","Codigo","Descripcion","Descripcion Larga","Etiqueta"});
		
		return model;
	}

	@SuppressWarnings("unchecked")
	public String insertarOActualizarRegistroCatalogoLogico(String cdTabla,
			String cdRegion, String cdIdioma, String codigo,
			String descripcion, String descripl, String dsLabel)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", cdTabla);
		map.put("pv_cdregion_i", cdRegion);
		map.put("pv_cdidioma_i", cdIdioma);
		map.put("pv_codigo_i", codigo);
		map.put("pv_descripc_i", descripcion);
		map.put("pv_descripl_i", descripl);
		map.put("pv_dslabel_i", dsLabel);
		
		String endpointName = "GUARDA_TCATALOG";
		
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public PagedList obtenerListadoCatalogosLogicos(String cdTabla,
			String cdRegion, String cdIdioma, String codigo, String descripcion,
			int start, int limit)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", cdTabla);
		map.put("pv_cdregion_i", cdRegion);
		map.put("pv_cdidioma_i", cdIdioma);
		map.put("pv_codigo_i", codigo);
		map.put("pv_descripc_i", descripcion);

		
		String endpointName = "OBTIENE_TCATALOG";
		
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}

	@SuppressWarnings("unchecked")
	public CatalogoLogicoVO obtenerRegistroCatalogosLogicos(String cdTabla,
			String cdRegion, String cdIdioma, String codigo, String descripcion)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", cdTabla);
		map.put("pv_cdregion_i", cdRegion);
		map.put("pv_cdidioma_i", cdIdioma);
		map.put("pv_codigo_i", codigo);
		map.put("pv_descripc_i", descripcion);
		
		String endpointName = "OBTIENE_TCATALOG_REG";
		
		return (CatalogoLogicoVO) getBackBoneInvoke(map, endpointName);
	}

}
