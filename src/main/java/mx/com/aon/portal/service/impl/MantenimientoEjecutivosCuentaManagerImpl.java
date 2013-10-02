package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opensymphony.module.sitemesh.Page;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.EjecutivoCuentaVO;
import mx.com.aon.portal.service.MantenimientoEjecutivosCuentaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

public class MantenimientoEjecutivosCuentaManagerImpl extends AbstractManager implements MantenimientoEjecutivosCuentaManager {

	public String borrarEjecutivo(String cdEjecutivo)
			throws ApplicationException {

		HashMap map = new HashMap ();
		map.put("cdEjecutivo", cdEjecutivo);
		
		WrapperResultados res = returnBackBoneInvoke(map, "MANTENIMIENTO_EJECUTIVOS_CUENTA_BORRAR");
		return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public PagedList buscarEjecutivosCuenta(String cdEjecutivo, String cdPerson,
			String fechaInicial, String fechaFinal, String status, int start, int limit)
			throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("cdEjecutivo", cdEjecutivo);
		map.put("dsPerson", cdPerson);
		map.put("fechaInicial", fechaInicial);
		map.put("fechaFinal", fechaFinal);
		map.put("status", status);
		
		return pagedBackBoneInvoke(map, "MANTENIMIENTO_EJECUTIVOS_CUENTA_BUSCAR_EJECUTIVOS", start, limit);
	}

	public String guardarEjecutivo(String cdEjecutivo, String cdPerson,
			String fechaInicial, String fechaFinal, String status)
			throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("cdEjecutivo", cdEjecutivo);
		map.put("cdPerson", cdPerson);
		map.put("fechaInicial", fechaInicial);
		map.put("fechaFinal", fechaFinal);
		map.put("status", status);

		WrapperResultados res = returnBackBoneInvoke(map, "MANTENIMIENTO_EJECUTIVOS_CUENTA_GUARDAR");
		return res.getMsgText();
	}

	public EjecutivoCuentaVO obtenerEjecutivo(String cdEjecutivo)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdEjecutivo", cdEjecutivo);

		return (EjecutivoCuentaVO)getBackBoneInvoke(map, "MANTENIMIENTO_EJECUTIVOS_CUENTA_OBTENER");
	}

	public PagedList obtenerAtributos(String cdEjecutivo, int start, int limit)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdEjecutivo", cdEjecutivo);

		return pagedBackBoneInvoke(map, "MANTENIMIENTO_EJECUTIVOS_CUENTA_OBTENER_ATRIBUTOS", start, limit);
	}

	/*public String guardarAtributos(String cdEjecutivo, String cdAtribu,
			String otValor) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdEjecutivo", cdEjecutivo);
		map.put("cdAtribu", cdAtribu);
		map.put("otValor", otValor);

		WrapperResultados res = returnBackBoneInvoke(map, "MANTENIMIENTO_EJECUTIVOS_CUENTA_BORRAR_ATRIBUTOS");

		return res.getMsgText();
	}*/

	public TableModelExport getModelAtributosEjecutivos(String cdEjecutivo)
			throws ApplicationException {
		TableModelExport model = new TableModelExport();
		List lista = null;
		
		HashMap map = new HashMap();
		map.put("cdEjecutivo", cdEjecutivo);

		lista = (ArrayList)getExporterAllBackBoneInvoke(map, "MANTENIMIENTO_EJECUTIVOS_EXPORTAR_ATRIBUTOS");
		model.setInformation(lista);
		model.setColumnName(new String[] {"Atributo", "Valor"});

		return model;
	}

	public TableModelExport getModelEjecutivos(String cdEjecutivo,
			String cdPerson, String fechaInicial, String fechaFinal, String status)
			throws ApplicationException {
		TableModelExport model = new TableModelExport();
		List lista = null;
		
		HashMap map = new HashMap();
		map.put("cdEjecutivo", cdEjecutivo);
		map.put("dsPerson", cdPerson);
		map.put("fechaInicial", fechaInicial);
		map.put("fechaFinal", fechaFinal);
		map.put("status", status);

		lista = (ArrayList)getExporterAllBackBoneInvoke(map, "MANTENIMIENTO_EJECUTIVOS_EXPORTAR_EJECUTIVOS");
		model.setInformation(lista);
		model.setColumnName(new String[] {"Codigo", "Persona", "Nombre", "Fecha Inicio", "Fecha Fin", "Estado"});

		return model;
	}

}
