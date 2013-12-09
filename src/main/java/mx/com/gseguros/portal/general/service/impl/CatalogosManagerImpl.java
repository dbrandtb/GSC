package mx.com.gseguros.portal.general.service.impl;

import java.util.List;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.portal.general.dao.CatalogosDAO;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.util.Catalogos;

public class CatalogosManagerImpl implements CatalogosManager {
	
	private CatalogosDAO catalogosDAO;
	
	
	@Override
	public List<GenericVO> getTmanteni(Catalogos catalogo) throws Exception {
		return catalogosDAO.obtieneTmanteni(catalogo.getCdTabla());
	}

	
	@Override
	public List<GenericVO> obtieneColonias(String codigoPostal) throws Exception {
		return catalogosDAO.obtieneColonias(codigoPostal);
	}
	
	public void setCatalogosDAO(CatalogosDAO catalogosDAO) {
		this.catalogosDAO = catalogosDAO;
	}

}