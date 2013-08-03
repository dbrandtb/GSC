package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import mx.com.aon.portal.service.OrdenesDeComprasManager;
import mx.com.aon.portal.model.OrdenDeCompraEncOrdenVO;
import mx.com.aon.core.ApplicationException;


/**
 *   Action que atiende las peticiones de que vienen de la pantalla Historial Ordenes de Compras
 * 
 */
@SuppressWarnings("serial")
public class OrdenesDeComprasAction extends ActionSupport {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
    @SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(OrdenesDeComprasAction.class);    
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 * 
	 */
	private transient OrdenesDeComprasManager ordenesDeComprasManager;
	
	@SuppressWarnings("unchecked")
	private List mEstructuraList;
	
	
    private String cdCarro;
    private String cdContra;
	private String cdTipDom;
    private String cdUsuari;
	private String cdPerson;
     
    private String resultMessage;

	private boolean success;

    /**
     * Metodo que atiende una peticion de obtener una persona de ordenes de compra
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
  	@SuppressWarnings("unchecked")
	public String cmdGetClickPersona()throws Exception
	{
		try
		{
			mEstructuraList=new ArrayList<OrdenDeCompraEncOrdenVO>();
			OrdenDeCompraEncOrdenVO ordenDeCompraEncOrdenVO=ordenesDeComprasManager.getObtenerOrdenesDetallePersonas(cdCarro);
			mEstructuraList.add(ordenDeCompraEncOrdenVO);
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
     * Metodo que atiende una peticion de finalizar una orden de compra
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGuardarClick()throws Exception
	{
		String messageResult = "";
        try
        {
        	messageResult = ordenesDeComprasManager.finalizarOrdenesDeCompras(cdCarro);
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
  	
  	
  	
    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}


	@SuppressWarnings("unchecked")
	public List getMEstructuraList() {
		return mEstructuraList;
	}

	@SuppressWarnings("unchecked")
	public void setMEstructuraList(List estructuraList) {
		mEstructuraList = estructuraList;
	}

	public String getCdCarro() {
		return cdCarro;
	}

	public void setCdCarro(String cdCarro) {
		this.cdCarro = cdCarro;
	}

	public String getCdContra() {
		return cdContra;
	}

	public void setCdContra(String cdContra) {
		this.cdContra = cdContra;
	}

	public String getCdTipDom() {
		return cdTipDom;
	}

	public void setCdTipDom(String cdTipDom) {
		this.cdTipDom = cdTipDom;
	}

	public String getCdUsuari() {
		return cdUsuari;
	}

	public void setCdUsuari(String cdUsuari) {
		this.cdUsuari = cdUsuari;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public OrdenesDeComprasManager getOrdenesDeComprasManager() {
		return ordenesDeComprasManager;
	}

	public void setOrdenesDeComprasManager(
			OrdenesDeComprasManager ordenesDeComprasManager) {
		this.ordenesDeComprasManager = ordenesDeComprasManager;
	}

}
