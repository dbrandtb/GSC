package mx.com.aon.portal.web;


import mx.com.aon.portal.model.GrupoPersonaVO;
import mx.com.aon.portal.service.AgrupacionGrupoPersonasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;


import org.apache.log4j.Logger;

import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Agrupacion Grupo Personas.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaAgrupacionGrupoPersonasAction extends AbstractListAction{


	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaAgrupacionGrupoPersonasAction.class);



	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient AgrupacionGrupoPersonasManager agrupacionGrupoPersonasManager;


	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo ReciboVO
	 * con los valores de la consulta.
	 */
	private List<GrupoPersonaVO> mEstructuraList;

	

	private String cdGrupo;
	
	/**
	 * Metodo que realiza la busqueda de un grupo de personas
	 * en base a codigo de grupo 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetClickGp() throws Exception{
		try{

            PagedList pagedList = this.agrupacionGrupoPersonasManager.getGruposPersonas(cdGrupo, start, limit); 
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

	public List<GrupoPersonaVO> getMEstructuraList() {return mEstructuraList;}
	public void setMEstructuraList(List<GrupoPersonaVO> mEstructuraList) {this.mEstructuraList = mEstructuraList;}

	public String getCdGrupo() {
		return cdGrupo;
	}

	public void setCdGrupo(String cdGrupo) {
		this.cdGrupo = cdGrupo;
	}

	public void setAgrupacionGrupoPersonasManager(
			AgrupacionGrupoPersonasManager agrupacionGrupoPersonasManager) {
		this.agrupacionGrupoPersonasManager = agrupacionGrupoPersonasManager;
	}

}
