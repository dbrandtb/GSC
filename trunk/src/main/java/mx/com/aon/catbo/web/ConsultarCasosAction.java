package mx.com.aon.catbo.web;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.service.FormatosDocumentosManager;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class ConsultarCasosAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 13656468489L;
	
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConsultarCasosAction.class);
	
	private boolean success;
	
	private String cdperson;
	private String nmcaso;
	private String cdmatriz;
	private String flag;
	private String cdformatoorden;
	private String cdPerson;
	private String vengoConsClient;
	private String cdElemento;
	
	private String cdunieco;
	private String cdramo;
	private String nmpoliza;
	private String estado;
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AdministracionCasosManager administracionCasosManager;
	
	/**
	 * Metodo que elimina un registro.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = administracionCasosManager.borrarCaso(nmcaso);
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
	
	public String cmdIrAltaCasoClick()throws Exception{
		return "irPantallaAltaCaso";
	}
	
	public String cmdIrConsultaCasoDetalleClick()throws Exception{
		return "irPantallaConsultarCasoDetalle";
	}
	
	public String cmdIrConsultarCasosClick()throws Exception{
		return "irPantallaConsultaCaso";
	}
	
	public String cmdIrConsultaClienteClick()throws Exception{
		return "irPantallaConsultaCliente";
	}

	public String cmdIrReasignarCasoClick()throws Exception{
		return "irReasignarCaso";
	}
	
	public String cmdIrConsultaClientes()throws Exception{
		return "irPantallaConsultaClientes";
	}
		
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setAdministracionCasosManager(
			AdministracionCasosManager administracionCasosManager) {
		this.administracionCasosManager = administracionCasosManager;
	}

	public String getNmcaso() {
		return nmcaso;
	}

	public void setNmcaso(String nmcaso) {
		this.nmcaso = nmcaso;
	}

	public String getCdmatriz() {
		return cdmatriz;
	}

	public void setCdmatriz(String cdmatriz) {
		this.cdmatriz = cdmatriz;
	}

	public String getCdperson() {
		return cdperson;
	}

	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCdformatoorden() {
		return cdformatoorden;
	}

	public void setCdformatoorden(String cdformatoorden) {
		this.cdformatoorden = cdformatoorden;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getVengoConsClient() {
		return vengoConsClient;
	}

	public void setVengoConsClient(String vengoConsClient) {
		this.vengoConsClient = vengoConsClient;
	}

	public String getCdunieco() {
		return cdunieco;
	}

	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}

	public String getCdramo() {
		return cdramo;
	}

	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}

	public String getNmpoliza() {
		return nmpoliza;
	}

	public void setNmpoliza(String nmpoliza) {
		this.nmpoliza = nmpoliza;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	
	
}
