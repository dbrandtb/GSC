package mx.com.aon.portal.web;

import java.util.Map;

import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.ConnectionCallInterceptor;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * PagingAction
 *
 * <pre>
 *   Action que atiende la petición de información para la consulta de datos de
 *   la tabla con mecanismo de paginación.
 * <Pre>
 *
 *
 */
public abstract class AbstractListAction extends ActionSupport implements SessionAware {
	
	protected static ThreadLocal<UserVO> localUser = new ThreadLocal<UserVO>();
	
	UserVO userVO = new UserVO();
	ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();	
	
    /**
     * Atributo agregado como parametro de la petición por struts que indica
     * el inicio de el número de linea en cual iniciar
     */
    protected int start=0;
    
    /**
     * Atributo agregado como parametro de la petición por struts que indica la cantidad
     * de registros a ser consultados.
     * Toma de la sesion la cantidad de registros permitidos a mostrar segun el usuario logueado.
     * En caso de obtenerse null, se asigna 20.
     */    
    protected int limit = (tl!=null&&tl.get()!=null&&tl.get().getTamagnoPaginacionGrid() > 0)?tl.get().getTamagnoPaginacionGrid():20;    
   

    /**
     * Atributo de respuesta interpretado por strust con el número de registros totales
     * que devuelve la consulta.
     */
    protected int totalCount;
	// Respuesta para JSON
	protected boolean success;

    protected String contentType;

    /**
     * Método que actualiza el valor de 'clicBotonRegresar' 
     * del mapa de session con llave 'PARAMETROS_REGRESAR'
     * @param void 
     * @return void 
     */
    @SuppressWarnings("unchecked")
    public void updateParametrosRegresar(){
		if ( session.containsKey("PARAMETROS_REGRESAR") ) {
			Map<String,String> pr = (Map<String,String>)session.get("PARAMETROS_REGRESAR");
			pr.put("clicBotonRegresar", "N");
			session.put("PARAMETROS_REGRESAR", pr);
		}
		return;
    }
    
    @SuppressWarnings("unchecked")
    protected Map session;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean getSuccess() {
        return success;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}

}
