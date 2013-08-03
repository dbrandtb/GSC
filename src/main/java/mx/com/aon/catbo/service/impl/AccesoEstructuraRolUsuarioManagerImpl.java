package mx.com.aon.catbo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.service.AccesoEstructuraRolUsuarioManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;

public class AccesoEstructuraRolUsuarioManagerImpl extends AbstractManager implements
		AccesoEstructuraRolUsuarioManager {

	@SuppressWarnings("unchecked")
	public String borrarAcceso(String cdNivel, String cdUsuario, String cdsisrol)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdNivel", cdNivel);
		map.put("pv_cdsisrol_i", cdsisrol);
		map.put("cdUsuario", cdUsuario);		

		WrapperResultados res = returnBackBoneInvoke(map, "ACCESOESTRUCTURAROLUSUARIO_BORRAR");
		return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public PagedList buscarAccesos(String dsNivel, String dsRol,
			String dsUsuario, String dsEstao, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("dsNivel", dsNivel);
		map.put("dsRol", dsRol);
		map.put("dsUsuario", dsUsuario);
		map.put("dsEstado", dsEstao);

		return pagedBackBoneInvoke(map, "ACCESOESTRUCTURAROLUSUARIO_BUSCAR", start, limit);
	}

	@SuppressWarnings("unchecked")
	public String guardarAcceso(String cdNivel, String cdRol, String cdUsuario,
			String cdEstado) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdNivel", cdNivel);
		map.put("cdRol", cdRol);
		map.put("cdUsuario", cdUsuario);
		map.put("cdEstado", cdEstado);

		WrapperResultados res = returnBackBoneInvoke(map, "ACCESOESTRUCTURAROLUSUARIO_GUARDAR");
		return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsNivel, String dsRol,
			String dsUsuario, String dsEstado) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();

		map.put("dsNivel", dsNivel);
		map.put("dsRol", dsRol);
		map.put("dsUsuario", dsUsuario);
		map.put("dsEstado", dsEstado);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "EXPORT_LIST_ACCESO_ESTRUCTURA_ROL_USUARIO");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Nivel","Rol","Usuario","Estado"});
		
		return model;
	}

}
