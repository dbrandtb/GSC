package mx.com.gseguros.portal.general.service;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;

public interface MenuManager {
	
	
	public List<Map<String, String>> obtieneOpcionesLiga(Map<String, String> params) throws ApplicationException;

	public List<Map<String, String>> obtieneMenusPorRol(Map<String, String> params) throws ApplicationException;

	public List<Map<String, String>> obtieneOpcionesMenu(Map<String, String> params) throws ApplicationException;

	public List<Map<String, String>> obtieneOpcionesSubMenu(Map<String, String> params) throws ApplicationException;

	public String guardaOpcionLiga(Map<String, String> params) throws ApplicationException;

	public String guardaMenu(Map<String, String> params) throws ApplicationException;

	public String guardaOpcionMenu(Map<String, String> params) throws ApplicationException;

	public String eliminaOpcionLiga(Map<String, String> params) throws ApplicationException;

	public String eliminaMenu(Map<String, String> params) throws ApplicationException;

	public String eliminaOpcionMenu(Map<String, String> params) throws ApplicationException;


}
