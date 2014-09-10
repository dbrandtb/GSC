package mx.com.gseguros.ws.autosgs.service;

import mx.com.gseguros.ws.folioserviciopublico.client.axis2.FolioWSServiceStub.EmAdmfolId;

public interface AgentePorFolioService {

	/**
	 * Metodo para obtener los datos del agente segun el numero de folio y sucursal para autos servicio publico
	 * @param numFolio
	 * @param sucursalAdmin
	 * @return
	 */
	public EmAdmfolId obtieneAgentePorFolioSucursal(int numFolio, int sucursalAdmin);
}
