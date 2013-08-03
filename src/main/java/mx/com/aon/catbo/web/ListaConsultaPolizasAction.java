package mx.com.aon.catbo.web;

import java.util.List;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.portal.service.PagedList;
import org.apache.log4j.Logger;

public class ListaConsultaPolizasAction extends AbstractListAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1137878786546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaConsultaPolizasAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AdministracionCasosManager administracionCasosManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo VO
	 * con los valores de la consulta.
	 */
	private List<CasoVO> mListPolizas;
	
	private String cdPerson;
	private String cdElemento;
	
	/**
	 * Metodo que realiza una búsqueda en base a criterios de búsquedas
	 *  
	 * @param 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String obtenerPolizas() throws Exception{
		try{
            PagedList pagedList = administracionCasosManager.obtienePolizas(cdElemento, cdPerson, start, limit);
            mListPolizas = pagedList.getItemsRangeList();
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

	public List<CasoVO> getMListPolizas() {
		return mListPolizas;
	}

	public void setMListPolizas(List<CasoVO> listPolizas) {
		mListPolizas = listPolizas;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public void setAdministracionCasosManager(
			AdministracionCasosManager administracionCasosManager) {
		this.administracionCasosManager = administracionCasosManager;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
}
