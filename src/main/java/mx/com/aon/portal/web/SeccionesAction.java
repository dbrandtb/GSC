package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.ArrayList;

import mx.com.aon.portal.service.SeccionesManager;
import mx.com.aon.portal.model.SeccionVO;
import mx.com.aon.core.ApplicationException;

/**
 * Action que atiende las peticiones de abm la pantalla de secciones.
 * 
 */
@SuppressWarnings("serial")
public class SeccionesAction extends ActionSupport {

	@SuppressWarnings("unchecked")
	private List listaSecciones;

    private transient SeccionesManager seccionesManager;
    
    private String cdSeccion;
	private String dsSeccion;
    private String cdBloque;

    private boolean success;


    /**
	 * Metodo <code>cmdAgregarClick</code> Agrega una seccion
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */
	public String cmdAgregarClick()throws Exception
	{
		try
		{
            SeccionVO seccionVO = new SeccionVO();
            seccionVO.setCdSeccion(cdSeccion);
            seccionVO.setDsSeccion(dsSeccion);
            seccionVO.setCdBloque(cdBloque);            

            String messageResult = seccionesManager.agregarGuardarSeccion(seccionVO);

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
	 * Metodo <code>cmdGetClick</code> obtiene una seccion
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetClick()throws Exception
	{
		try
		{
            listaSecciones = new ArrayList<SeccionVO>();
            SeccionVO seccionVO  = seccionesManager.getSeccion(cdSeccion);
            seccionVO.setBloqueEditable(String.valueOf(seccionesManager.isBloqueEditable(cdSeccion,seccionVO.getCdBloque())));
            listaSecciones.add(seccionVO);
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
	 * Metodo <code>cmdBorrarClick</code> borra una seccion elegida en el grid de secicones.
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */
	@SuppressWarnings("unchecked") 
	
	public String cmdBorrarClick() throws Exception
	{
		try
		{
            String messageResult = seccionesManager.borrarSeccion(cdSeccion);
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


	public void setSeccionesManager(SeccionesManager seccionesManager) {
        this.seccionesManager = seccionesManager;
    }

	public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}

	public String getCdSeccion() {
		return cdSeccion;
	}

	public void setCdSeccion(String cdSeccion) {
		this.cdSeccion = cdSeccion;
	}

	public String getDsSeccion() {
		return dsSeccion;
	}

	public void setDsSeccion(String dsSeccion) {
		this.dsSeccion = dsSeccion;
	}

	public String getCdBloque() {
		return cdBloque;
	}

	public void setCdBloque(String cdBloque) {
		this.cdBloque = cdBloque;
	}

    @SuppressWarnings("unchecked")
	public List getListaSecciones() {
        return listaSecciones;
    }

    @SuppressWarnings("unchecked")
	public void setListaSecciones(List listaSecciones) {
        this.listaSecciones = listaSecciones;
    }

}
