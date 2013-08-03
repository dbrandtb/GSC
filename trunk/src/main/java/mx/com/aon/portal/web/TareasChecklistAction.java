package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.ObtienetareaVO;
import mx.com.aon.portal.service.EstructuraManagerTareasChecklist;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Tareas Checklist.
 * 
 */
public class TareasChecklistAction extends ActionSupport {


	private static final long serialVersionUID = 1L;

	/**
     * Logger de la clase para monitoreo y registro de comportamiento
     */
	@SuppressWarnings("unused")
    private static Logger logger =
            Logger.getLogger(TareasChecklistAction.class);


	private List<ObtienetareaVO> obtieneTareaVO;

    private String codigoSeccion;
    private String codigoTarea;
    private String descrTarea;
    private String seccion;
    private String tarea;
    private String codigoTareaPadre;
    private String url;
    private String copio;
    private String codigoEstado;
    private String ayuda;
    private String comentario;
    private String resultado;

    private boolean success;


    /**
     * Manager con implementacion de Endpoint para la consulta a BD
     */
    private transient EstructuraManagerTareasChecklist
            estructuraManagerTareasChecklist;



	/**
	 * Metodo que atiende una peticion para obtener una tarea especifica seleccionada en la grilla.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
    
    @SuppressWarnings("unchecked")
	public String cmdGetTareaClick() {
    	try {
    		obtieneTareaVO = new ArrayList<ObtienetareaVO>();
            ObtienetareaVO  tareaObtenida = estructuraManagerTareasChecklist.getTarea(seccion, tarea);
            if (tareaObtenida != null) {
                tareaObtenida.setCdTarea(tarea);
                tareaObtenida.setCdSeccion(seccion);
            }
            obtieneTareaVO.add(tareaObtenida);
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
	 * Metodo que atiende una peticion para saber si se puedo o no borrar una tarea.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
    public String cmdValidaBorraTareaClick() {

        try {
            WrapperResultados validaBorraTarea =
                    estructuraManagerTareasChecklist.validaBorraTarea(codigoSeccion,codigoTarea);
            if (validaBorraTarea!= null && validaBorraTarea.getResultado().equals("1")) {
            	resultado = validaBorraTarea.getResultado();
                success = true;
                addActionMessage(validaBorraTarea.getMsgText());
            } else {
            	/*
            	 * Se cambió esta parte del código para evitar mala interpretación del 
            	 * success. De esta forma, si la llamada al sp se realiza, se devuelve
            	 * success = true y el valor de "resultado".
            	 */
            	resultado = validaBorraTarea.getResultado();
                success = true;
                addActionMessage(validaBorraTarea.getMsgText());
                //addActionError(validaBorraTarea.getMsgText());
            }
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
	 * Metodo que atiende una peticion para eliminar una tarea seleccionada en la grilla.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
    public String cmdBorraTareaClick() {
		String messageResult = "";
        try {
            messageResult = estructuraManagerTareasChecklist.borrarTarea(codigoSeccion,codigoTarea);
            success = true;
            addActionMessage(messageResult);
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
	 * Metodo que atiende una peticion de insertar o actualizar un nuevo tarea checklist.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
    public String cmdGuardaTareaClick() {
		String messageResult = "";
    	try {
            String  _copio  = (copio!=null && !copio.equals("") && copio.equals("on"))?"1":"0";
            if (copio==null || copio.equals("") || copio.equals("off")) {_copio = "0";}
            messageResult = estructuraManagerTareasChecklist.guardarTarea(codigoSeccion, codigoTarea, descrTarea, codigoTareaPadre, codigoEstado,
                    url, _copio, ayuda);
            success = true;
            addActionMessage(messageResult);
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


    public String getCodigoSeccion() {
        return codigoSeccion;
    }

    public void setCodigoSeccion(String codigoSeccion) {
        this.codigoSeccion = codigoSeccion;
    }

    public String getCodigoTarea() {
        return codigoTarea;
    }

    public void setCodigoTarea(String codigoTarea) {
        this.codigoTarea = codigoTarea;
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


    public String getCodigoTareaPadre() {
        return codigoTareaPadre;
    }

    public void setCodigoTareaPadre(String codigoTareaPadre) {
        this.codigoTareaPadre = codigoTareaPadre;
    }

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getAyuda() {
		return ayuda;
	}

	public void setAyuda(String ayuda) {
		this.ayuda = ayuda;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


	public void setEstructuraManagerTareasChecklist(
			EstructuraManagerTareasChecklist estructuraManagerTareasChecklist) {
		this.estructuraManagerTareasChecklist = estructuraManagerTareasChecklist;
	}


    public String getCopio() {
        return copio;
    }

    public void setCopio(String copio) {
        this.copio = copio;
    }

    public String getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }
    
    @SuppressWarnings("unchecked")
	public List getObtieneTareaVO() {
		return obtieneTareaVO;
	}

	@SuppressWarnings("unchecked")
	public void setObtieneTareaVO(List obtieneTareaVO) {
		this.obtieneTareaVO = obtieneTareaVO;
	}

	public String getDescrTarea() {
		return descrTarea;
	}

	public void setDescrTarea(String descrTarea) {
		this.descrTarea = descrTarea;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}
