package mx.com.gseguros.portal.general.dao;

import java.util.List;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.DaoException;

public interface CatalogosDAO {
	
	public List<GenericVO> obtieneTmanteni(String cdTabla) throws DaoException;
	
	public List<GenericVO> obtieneAgentes(String claveONombre) throws DaoException;
	
	public List<GenericVO> obtieneAtributosSituacion(String cdAtribu, String cdTipSit ,String otValor) throws DaoException;
	
	public List<GenericVO> obtieneAtributosPoliza(String cdAtribu, String cdRamo, String otValor) throws DaoException;

	public List<GenericVO> obtieneAtributosGarantia(String cdAtribu, String cdTipSit, String cdRamo, String valAnt, String cdGarant) throws DaoException;
	
	public List<GenericVO> obtieneAtributosRol(String cdAtribu, String cdTipSit ,String cdRamo, String valAnt, String cdRol) throws DaoException;
	
	public List<GenericVO> obtieneColonias(String codigoPostal) throws DaoException;
	
	public List<GenericVO> obtieneRolesSistema() throws DaoException;

}
