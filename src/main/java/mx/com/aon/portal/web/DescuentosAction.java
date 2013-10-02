package mx.com.aon.portal.web;


import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.portal.service.DescuentosManager;
import mx.com.gseguros.exception.ApplicationException;

import com.opensymphony.xwork2.ActionSupport;


/**
 * Action que atiende las peticiones de la pantalla de descuento.
 *
 */
@SuppressWarnings("serial")
public class DescuentosAction extends ActionSupport {
	private String cdDscto;
	
	private String dsNombre;
	private String cdTipo;

	@SuppressWarnings("unchecked")
	private List descuentoVO;
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(DescuentosAction.class);
	private transient DescuentosManager descuentosManager;


    public void setDescuentosManager(DescuentosManager descuentosManager) {
        this.descuentosManager = descuentosManager;
    }

 	private boolean success;

	public String irAgregarDescuentoClick()throws Exception
	{
		return "agregarDescuento";
	}
	
	public String irEditarDescuentoClick()throws Exception
	{
		return "editarDescuento";
	}

	/**
	 *  Metodo que realiza el copiado de un descuento seleccionado del grid de la pantalla 
	 *  de descuento.
	 *
	 */
	@SuppressWarnings("unchecked")  
	public String cmdCopiarClick() throws Exception
	{
		try
		{
			String res = descuentosManager.copiarDescuento(cdDscto);
            success = true;
            addActionMessage(res);
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
	 * Metodo que elimina un descuento seleccionado del grid de la pantalla
	 * de descuento.
	 *
	 */
	@SuppressWarnings("unchecked")  
	public String cmdBorrarClick() throws Exception
	{
		try
		{
			descuentosManager.borrarDescuento(cdDscto);
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
	
    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}


    public String getCdDscto() {
        return cdDscto;
    }

    public void setCdDscto(String cdDscto) {
        this.cdDscto = cdDscto;
    }

	@SuppressWarnings("unchecked")
	public List getDescuentoVO() {
		return descuentoVO;
	}

	@SuppressWarnings("unchecked")
	public void setDescuentoVO(List descuentoVO) {
		this.descuentoVO = descuentoVO;
	}

	public String getDsNombre() {
		return dsNombre;
	}

	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}

	public String getCdTipo() {
		return cdTipo;
	}

	public void setCdTipo(String cdTipo) {
		this.cdTipo = cdTipo;
	}

}

