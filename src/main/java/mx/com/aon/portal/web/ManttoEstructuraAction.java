package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.portal.model.EstructuraVO;
import mx.com.aon.portal.service.EstructuraManager;
import mx.com.gseguros.exception.ApplicationException;

import java.util.List;
import java.util.ArrayList;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Mantenimiento de Estructuras.
 * 
 */
public class ManttoEstructuraAction extends ActionSupport {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ManttoEstructuraAction.class);
	private static final long serialVersionUID = 16565468787954354L;
	private String codigo;
    private String descripcion;
    private String codigoRegion;
    private String codigoIdioma;
    private String descripcionEscapedJavaScript;
	private List<EstructuraVO> estructuraVO;
	private transient EstructuraManager estructuraManager;
	private boolean success;
	
	/**
	 * Metodo que atiende una peticion de insertar una nueva estructura.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdAgregarClick()throws Exception
	{
		try
		{
			EstructuraVO estructuraVO = new EstructuraVO();
            estructuraVO.setCodigo(codigo);
            estructuraVO.setDescripcion(descripcion);
            estructuraVO.setCodigoRegion(codigoRegion);
            estructuraVO.setCodigoIdioma(codigoIdioma);
            String msg = estructuraManager.saveOrUpdateEstructura(estructuraVO, "INSERTA_ESTRUCT");
            success = true;
            addActionMessage(msg);
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
	 * Metodo que atiende una peticion para obtener una estructura especifica seleccionada en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetClick()throws Exception
	{
		try
		{
            estructuraVO = new ArrayList<EstructuraVO>();
            EstructuraVO elemento = estructuraManager.getEstructura(codigo);
            estructuraVO.add(elemento);
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
	 * Metodo que atiende una peticion para actualizar una estructura modificada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
    public String cmdGuardarClick()throws Exception
    {
        try
        {
            EstructuraVO estructuraVO = new EstructuraVO();
            estructuraVO.setCodigo(codigo);
            estructuraVO.setDescripcion(descripcion);
            estructuraVO.setCodigoRegion(codigoRegion);
            estructuraVO.setCodigoIdioma(codigoIdioma);
            String msg = estructuraManager.saveOrUpdateEstructura(estructuraVO, "GUARDA_ESTRUCT");
            success = true;
            addActionMessage(msg);
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
	 * Metodo que atiende una peticion para copiar una estructura seleccionada en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdCopiarClick() throws Exception
	{
		try
		{
			EstructuraVO estructuraVO = new EstructuraVO();
			estructuraVO.setCodigo(codigo);
			estructuraVO.setDescripcion(descripcion);
			String msg = estructuraManager.copiaEstructura(estructuraVO);
            success = true;
            addActionMessage(msg);
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
	 * Metodo que atiende una peticion para eliminar una estructura seleccionada en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception
	{
		try
		{
			String msg = estructuraManager.borraEstructura((codigo));
            success = true;
            addActionMessage(msg);
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
	
	public void setEstructuraManager(EstructuraManager estructuraManager) {
	        this.estructuraManager = estructuraManager;
	    }
	
    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        this.descripcionEscapedJavaScript = StringEscapeUtils.escapeJavaScript(descripcion);
    }

    
    public String getCodigoRegion() {
        return codigoRegion;
    }

    public void setCodigoRegion(String codigoRegion) {
        this.codigoRegion = codigoRegion;
    }

    public String getCodigoIdioma() {
        return codigoIdioma;
    }

    public void setCodigoIdioma(String codigoIdioma) {
        this.codigoIdioma = codigoIdioma;
    }

    public List<EstructuraVO> getEstructuraVO() {
        return estructuraVO;
    }

    public void setEstructuraVO(List<EstructuraVO> estructuraVO) {
        this.estructuraVO = estructuraVO;
    }

	public String getDescripcionEscapedJavaScript() {
		return descripcionEscapedJavaScript;
	}
    
    
    public void setDescripcionEscapedJavaScript(String descripcionEscapedJavaScript) {
        this.descripcionEscapedJavaScript = descripcionEscapedJavaScript;
    }
    
}