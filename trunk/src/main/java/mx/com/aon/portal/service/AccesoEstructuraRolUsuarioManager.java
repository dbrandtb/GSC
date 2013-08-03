package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;

public interface AccesoEstructuraRolUsuarioManager {
	public PagedList buscarAccesos (String dsNivel, String dsRol, String dsUsuario, String dsNombre, String dsEstao, int start, int limit) throws ApplicationException;

	public String guardarAcceso (String cdNivel, String cdRol, String cdUsuario, String cdEstado) throws ApplicationException;

	public String borrarAcceso (String cdNivel, String cdUsuario, String cdsisrol) throws ApplicationException;
	
	public TableModelExport getModel (String dsNivel, String dsRol, String dsUsuario, String dsEstado) throws ApplicationException;
}
