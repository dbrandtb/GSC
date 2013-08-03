package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfiguracionProcesoVO;
import mx.com.aon.portal.service.ConfiguracionProcesoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

public class ConfiguracionProcesoManagerImpl extends AbstractManager implements
		ConfiguracionProcesoManager {

	@SuppressWarnings("unchecked")
	public String actualizarProceso(String cdUniEco, String cdRamo,
			String cdElemento, String cdProceso, String swEstado)
			throws ApplicationException {

		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("cdElemento", cdElemento);
		map.put("cdProceso", cdProceso);
		map.put("swEstado", swEstado);

		WrapperResultados res = returnBackBoneInvoke(map, "CONFIGURACION_PROCESO_ACTUALIZAR");
		return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public PagedList buscarProcesos(String dsUniEco, String dsRamo,
			String dsElemento, String dsProceso, int start, int limit)
			throws ApplicationException {

		HashMap map = new HashMap();
		map.put("dsUniEco", dsUniEco);
		map.put("dsRamo", dsRamo);
		map.put("dsElemento", dsElemento);
		map.put("dsProceso", dsProceso);

		return pagedBackBoneInvoke(map, "CONFIGURACION_PROCESO_BUSCAR", start, limit);
	}

	@SuppressWarnings("unchecked")
	public String guardarProceso(String cdUniEco, String cdRamo,
			String cdElemento, String cdProceso, String swEstado)
			throws ApplicationException {

		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("cdElemento", cdElemento);
		map.put("cdProceso", cdProceso);
		map.put("swEstado", swEstado);

		WrapperResultados res = returnBackBoneInvoke(map, "CONFIGURACION_PROCESO_AGREGAR");
		return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public ConfiguracionProcesoVO obtenerProceso(String cdUniEco, String cdRamo,
			String cdElemento, String cdProceso) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("cdElemento", cdElemento);
		map.put("cdProceso", cdProceso);

		return (ConfiguracionProcesoVO)getBackBoneInvoke(map, "CONFIGURACION_PROCESO_OBTENER");
	}

	@SuppressWarnings("unchecked")
	public String borrarProceso(String cdUniEco, String cdRamo, String cdElemento, String cdProceso) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("cdProceso", cdProceso);
		map.put("cdElemento", cdElemento);

		WrapperResultados res = returnBackBoneInvoke(map, "CONFIGURACION_PROCESO_BORRAR");
		return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsUniEco, String dsRamo,
			String dsElemento, String dsProceso) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();

		map.put("dsUniEco", dsUniEco);
		map.put("dsRamo", dsRamo);
		map.put("dsElemento", dsElemento);
		map.put("dsProceso", dsProceso);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "CONFIGURACION_PROCESO_EXPORTAR");
        model.setInformation(lista);
		model.setColumnName(new String[]{"Aseguradora", "Producto", "Nivel", "Proceso", "Indicador"});
		
		return model;
		}

}
