package mx.com.aon.portal.web;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.ConfiguracionEndososManager;

/**
 * Action que atiende las solicitudes de abm de la pantalla Configuracion de Endosos
 * 
 *
 */
public class ConfiguracionEndososAction extends ActionSupport{
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConfiguracionEndososAction.class);
	private static final long serialVersionUID = 16986577787147844L;
	private String cdTipSup;
	private String dsTipSup;
	private String swSuplem;
	private String swTariFi;
	private String cdTipDoc;
	private transient ConfiguracionEndososManager configuracionEndososManager;
	private boolean success;
	
	
	public String irAlgunaPaginaClick()throws Exception
	{
		return "";
	}
	
	/**
	 * Metodo que inserta una nueva Configuracion de Endosos o actualizar una configuracion 
	 * editada en pantalla.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGuardarClick() throws Exception
	{
		String messageResult = "";
		try
		{	
			String _swSiNo = (swTariFi !=null && !swTariFi.equals("") && (swTariFi.equals("S")||swTariFi.equals("Si") || swTariFi.equals("1")  ))?"S":"N";
			messageResult = configuracionEndososManager.guardarOActualizarTipoSuplemento(cdTipSup, dsTipSup, _swSiNo);
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
	 * Metodo que elimina una Configuracion de Endosos seleccionada en el grid de la pantalla.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBorrarClick() throws Exception
	{
		String messageResult = "";
		try
		{
			messageResult = configuracionEndososManager.borrarTipoSuplemento(cdTipSup);
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

	public String getCdTipSup() {
		return cdTipSup;
	}

	public void setCdTipSup(String cdTipSup) {
		this.cdTipSup = cdTipSup;
	}

	public String getDsTipSup() {
		return dsTipSup;
	}

	public void setDsTipSup(String dsTipSup) {
		this.dsTipSup = dsTipSup;
	}

	public String getSwSuplem() {
		return swSuplem;
	}

	public void setSwSuplem(String swSuplem) {
		this.swSuplem = swSuplem;
	}

	public String getSwTariFi() {
		return swTariFi;
	}

	public void setSwTariFi(String swTariFi) {
		this.swTariFi = swTariFi;
	}

	public String getCdTipDoc() {
		return cdTipDoc;
	}

	public void setCdTipDoc(String cdTipDoc) {
		this.cdTipDoc = cdTipDoc;
	}

	public void setConfiguracionEndososManager(
			ConfiguracionEndososManager configuracionEndososManager) {
		this.configuracionEndososManager = configuracionEndososManager;
	}

	
}
