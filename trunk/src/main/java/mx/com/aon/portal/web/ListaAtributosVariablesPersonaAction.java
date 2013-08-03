package mx.com.aon.portal.web;

import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.AtributosVariablesPersonaVO;
import mx.com.aon.portal.service.AtributosVariablesPersonaManager;
import mx.com.aon.portal.service.PagedList;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Atributos Variables por Persona.
 * 
 */
public class ListaAtributosVariablesPersonaAction extends AbstractListAction {

	private static final long serialVersionUID = 111454687879L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaAtributosVariablesPersonaAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AtributosVariablesPersonaManager atributosVariablesPersonaManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AtributoVaribleAgenteVO
	 * con los valores de la consulta.
	 */
	private List<AtributosVariablesPersonaVO> mListAtribuVarPersona;

	/**
	 * ejecuta la busqueda para el llenado de la grilla
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtenerAtributosVariablesPersona() throws Exception{
		try{

            PagedList pagedList = this.atributosVariablesPersonaManager.buscarAtributosVariablesPersona(start, limit);
            mListAtribuVarPersona = pagedList.getItemsRangeList();
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

	public List<AtributosVariablesPersonaVO> getMListAtribuVarPersona() {
		return mListAtribuVarPersona;
	}

	public void setMListAtribuVarPersona(
			List<AtributosVariablesPersonaVO> listAtribuVarPersona) {
		mListAtribuVarPersona = listAtribuVarPersona;
	}

	public void setAtributosVariablesPersonaManager(
			AtributosVariablesPersonaManager atributosVariablesPersonaManager) {
		this.atributosVariablesPersonaManager = atributosVariablesPersonaManager;
	}
	
	
	
}
