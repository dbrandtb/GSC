package mx.com.aon.portal.web;


import mx.com.aon.portal.model.AtributoVaribleAgenteVO;
import mx.com.aon.portal.service.AtributosVariablesAgenteManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Ayuda de Coberturas.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaAtributosVariablesAgenteAction extends AbstractListAction{

	private static final long serialVersionUID = 165440006125584L;

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaAtributosVariablesAgenteAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient AtributosVariablesAgenteManager atributosVariablesAgenteManager;

	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AtributoVaribleAgenteVO
	 * con los valores de la consulta.
	 */
	private List<AtributoVaribleAgenteVO> mEstructuraList;
	private String dsAtribu;

	/**
	 * ejecuta la busqueda para el llenado de la grilla
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{

            PagedList pagedList = this.atributosVariablesAgenteManager.buscarAtributosVariablesAgente(start, limit);
            mEstructuraList = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();                                                    
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

	public List<AtributoVaribleAgenteVO> getMEstructuraList() {
		return mEstructuraList;
	}

	public void setMEstructuraList(List<AtributoVaribleAgenteVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public String getDsAtribu() {
		return dsAtribu;
	}

	public void setDsAtribu(String dsAtribu) {
		this.dsAtribu = dsAtribu;
	}

	public void setAtributosVariablesAgenteManager(
			AtributosVariablesAgenteManager atributosVariablesAgenteManager) {
		this.atributosVariablesAgenteManager = atributosVariablesAgenteManager;
	}	

}
