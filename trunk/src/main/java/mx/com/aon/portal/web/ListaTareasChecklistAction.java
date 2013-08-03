package mx.com.aon.portal.web;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.TareaChecklistVO;
import mx.com.aon.portal.service.EstructuraManagerTareasChecklist;
import mx.com.aon.portal.service.PagedList;
import org.apache.log4j.Logger;

import java.util.List;


/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Tareas Checklist.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaTareasChecklistAction extends AbstractListAction {


    /**
     * Logger de la clase para monitoreo y registro de comportamiento
     */
    @SuppressWarnings("unused")
	private static Logger logger =
            Logger.getLogger(ListaTareasChecklistAction.class);


    private String seccion;
    private String tarea;
    private String estado;
    private String codSeccion;
    private String codTarea;


    /**
     * Manager con implementacion de Endpoint para la consulta a BD
     * Este objeto no es serializable
     */
    private transient EstructuraManagerTareasChecklist estructuraManagerTareasChecklist;

	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo TareaChecklistVO
	 * con los valores de la consulta.
	 */
    private List<TareaChecklistVO> mEstructuraList;


	/**
	 * Metodo que realiza la busqueda en base a seccion, tarea, estado de una tarea.
	 * 
	 * @param
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
    @SuppressWarnings("unchecked")
	public String cmdBuscarTareasClick() {

        try {
        	
            PagedList  pagedList =  this.estructuraManagerTareasChecklist.getTareas(seccion,tarea, estado,start,limit);
            this.mEstructuraList = pagedList.getItemsRangeList();
            this.totalCount = pagedList.getTotalItems();
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

	/**
	 * Metodo que realiza la busqueda en base a seccion, tarea, estado de una tarea.
	 * 
	 * @param
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
    @SuppressWarnings("unchecked")
	public String listaTareasCombo() {

        try {
        	
            PagedList  pagedList =  new PagedList();
            pagedList = estructuraManagerTareasChecklist.obtenerListaTareasChecklist(codSeccion,codTarea,start,limit);
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

    
    
    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

   

    public void setEstructuraManagerTareasChecklist(EstructuraManagerTareasChecklist
            estructuraManagerTareasChecklist) {
        this.estructuraManagerTareasChecklist = estructuraManagerTareasChecklist;
    }

    public List<TareaChecklistVO> getMEstructuraList() {
        return mEstructuraList;
    }

    public void setMEstructuraList(List<TareaChecklistVO> estructuraList) {
        mEstructuraList = estructuraList;
    }

	public String getCodTarea() {
		return codTarea;
	}

	public void setCodTarea(String codTarea) {
		this.codTarea = codTarea;
	}

	public String getCodSeccion() {
		return codSeccion;
	}

	public void setCodSeccion(String codSeccion) {
		this.codSeccion = codSeccion;
	}



}