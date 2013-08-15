package mx.com.aon.portal.web;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.portal.service.PeriodosGraciaClienteManager;
import mx.com.aon.portal.model.PeriodoGraciaClienteVO;
import mx.com.aon.core.ApplicationException;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Periodos de Gracia Por Clientes
 * 
 */
@SuppressWarnings("serial")
public class PeriodosGraciaClienteAction extends ActionSupport {


	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PeriodosGraciaClienteAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 */
    private transient PeriodosGraciaClienteManager periodosGraciaClienteManager;
   
    private String cdTramo;
    private String cdPerson;
    private String cveProducto;
    private String cdElemento;
    private String cveAseguradora;
    

	private boolean success;

  /**
   * Metodo que atiende una peticion de insertar un nuevo periodo de gracia por cliente
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdAgregarClick()throws Exception
	{
		try
		{
			PeriodoGraciaClienteVO periodoGraciaClienteVO = new PeriodoGraciaClienteVO();
			periodoGraciaClienteVO.setCdTramo(cdTramo);
			periodoGraciaClienteVO.setCdPerson(cdPerson);
			periodoGraciaClienteVO.setCveProducto(cveProducto);
			periodoGraciaClienteVO.setCdElemento(cdElemento);
			periodoGraciaClienteVO.setCveAseguradora(cveAseguradora);
            String messageResult = periodosGraciaClienteManager.insertarPeriodoGraciaCliente(periodoGraciaClienteVO);
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
	 * Elimina un registro de la grilla previamente seleccionado
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")  // Manejo de Map controlado
	public String cmdBorrarClick() throws Exception
	{
		try
		{
			PeriodoGraciaClienteVO periodoGraciaClienteVO = new PeriodoGraciaClienteVO();
			
			periodoGraciaClienteVO.setCdTramo(cdTramo);
			periodoGraciaClienteVO.setCdElemento(cdElemento);
			periodoGraciaClienteVO.setCveProducto(cveProducto);
			periodoGraciaClienteVO.setCveAseguradora(cveAseguradora);
			
			String messageResult = periodosGraciaClienteManager.borrarPeriodoGraciaCliente(periodoGraciaClienteVO);
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


	public PeriodosGraciaClienteManager obtenPeriodosGraciaClienteManager() {
		return periodosGraciaClienteManager;
	}


	public void setPeriodosGraciaClienteManager(
			PeriodosGraciaClienteManager periodosGraciaClienteManager) {
		this.periodosGraciaClienteManager = periodosGraciaClienteManager;
	}


	public String getCdTramo() {
		return cdTramo;
	}


	public void setCdTramo(String cdTramo) {
		this.cdTramo = cdTramo;
	}


	public String getCdPerson() {
		return cdPerson;
	}


	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}


	public String getCveProducto() {
		return cveProducto;
	}


	public void setCveProducto(String cveProducto) {
		this.cveProducto = cveProducto;
	}


	public String getCdElemento() {
		return cdElemento;
	}


	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}


	public String getCveAseguradora() {
		return cveAseguradora;
	}


	public void setCveAseguradora(String cveAseguradora) {
		this.cveAseguradora = cveAseguradora;
	}


    
}
