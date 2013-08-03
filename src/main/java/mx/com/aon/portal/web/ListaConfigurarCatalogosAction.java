package mx.com.aon.portal.web;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.CampoCatalogoVO;
import mx.com.aon.portal.service.ConfiguradorCatalogosManager;
import mx.com.aon.portal.service.PagedList;

import org.apache.log4j.Logger;

public class ListaConfigurarCatalogosAction extends AbstractListAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5452010771825183226L;

	private static Logger logger = Logger.getLogger(ListaConfigurarCatalogosAction.class);

	private transient ConfiguradorCatalogosManager configuradorCatalogosManager;

	private String cdPantalla;

	private boolean success;
	private List<CampoCatalogoVO> listaCampos;

	@SuppressWarnings("unchecked")
	public void cmdBusquedaCatalogoSimple () throws ApplicationException {
			logger.debug("Obteniendo campos catalogo simple: " + cdPantalla);
			PagedList pagedList = configuradorCatalogosManager.obtenerCamposBusquedaSimple(cdPantalla);
			listaCampos = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
			success = true;
			//return SUCCESS;
	}

	public String cmdConfigurarCatalogoSimple () throws ApplicationException{
		//cmdObtenerCamposBusquedaCatalogoSimple();
		success = true;
		return "configurarCatalogoSimple";
	}
	public String getCdPantalla() {
		return cdPantalla;
	}

	public void setCdPantalla(String cdPantalla) {
		this.cdPantalla = cdPantalla;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<CampoCatalogoVO> getListaCampos() {
		return listaCampos;
	}

	public void setListaCampos(List<CampoCatalogoVO> listaCampos) {
		this.listaCampos = listaCampos;
	}

	public void setConfiguradorCatalogosManager(
			ConfiguradorCatalogosManager configuradorCatalogosManager) {
		this.configuradorCatalogosManager = configuradorCatalogosManager;
	}
}
