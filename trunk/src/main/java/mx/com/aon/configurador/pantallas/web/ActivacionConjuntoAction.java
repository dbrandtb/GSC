/**
 * 
 */
package mx.com.aon.configurador.pantallas.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import mx.com.aon.configurador.pantallas.service.ConfiguradorPantallaService;
import mx.com.aon.core.ApplicationException;

/**
 * 
 * Clase Action para el control de la seccion de Activacion de conjuntos
 * 
 * @author aurora.lozada
 * 
 */

public class ActivacionConjuntoAction extends PrincipalConfPantallaAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5201495869724981625L;

	public static final String ID = "id";

//	private ConfiguradorPantallaService configuradorManager;

	private boolean success;

	private String cdConjunto;

	private String cdProcesoActivacion;

	private String cdCliente;

	private String cdProductoActivacion;

	private String hactivacion;

//	/**
//	 * @param configuradorManager the configuradorManager to set
//	 */
//	public void setConfiguradorManager(ConfiguradorPantallaService configuradorManager) {
//		this.configuradorManager = configuradorManager;
//	}

	@Override
	public void prepare() throws Exception {
		super.prepare();

		boolean isDebug = logger.isDebugEnabled();

		if (isDebug) {
			logger.debug("### Enterintg to prepare in ActivacionConjunto ...");
		}

	}

	@Override
	public void validate() {
		boolean isDebug = logger.isDebugEnabled();

		if (isDebug) {
			logger.debug("### Enterintg into method validate in ActivacionConjunto ...");
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String execute() throws Exception {
		boolean isDebug = logger.isDebugEnabled();

		if (isDebug) {
			logger.debug("### Enterintg into method execute in ActivacionConjunto ...");
		}

		return SUCCESS;
	}

	/**
	 * Metodo que activa o desactiva un conjunto.
	 * 
	 * @return Cadena SUCCESS
	 * @throws Exception
	 */

	public String activar() throws Exception {
		boolean isDebug = logger.isDebugEnabled();
		if (isDebug) {
			logger.debug("#######Enterintg into method ACTIVAR CONJUNTO...");
		}
		try {
			cdConjunto = (String) session.get(ID);
	
			if(isDebug){
				logger.debug("id del conjunto-----" + cdConjunto);
				logger.debug("Accion solicitada-----" + hactivacion);
				logger.debug("cdProcesoActivacion-----" + cdProcesoActivacion);
				logger.debug("cdCliente-----" + cdCliente);
				logger.debug("cdProductoActivacion-----" + cdProductoActivacion);
			}
	
			if (StringUtils.isBlank(cdConjunto) || StringUtils.isBlank(hactivacion) || StringUtils.isBlank(cdProcesoActivacion)
					|| StringUtils.isBlank(cdCliente) || StringUtils.isBlank(cdProductoActivacion)) {
				addActionError("No hay conjunto para activar");
				success = false;
			} else {
				Map<String, String> parameters = new HashMap<String, String>();
				parameters.put("CD_CONJUNTO", cdConjunto);
				parameters.put("CD_ELEMENTO", cdCliente);
				parameters.put("CD_PROCESO", cdProcesoActivacion);
				parameters.put("CD_RAMO", cdProductoActivacion);
				parameters.put("SW_ACTIVO", hactivacion);
	
				// Metodo que ACTIVA
				addActionMessage(configuradorManager.activarConjunto(parameters));
	
				success = true;
			}
			return SUCCESS;
		} /*catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}*/ catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}

	/**
	 * @return the success
	 */
	public boolean getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the cdConjunto
	 */
	public String getCdConjunto() {
		return cdConjunto;
	}

	/**
	 * @param cdConjunto the cdConjunto to set
	 */
	public void setCdConjunto(String cdConjunto) {
		this.cdConjunto = cdConjunto;
	}

	/**
	 * @return the hactivacion
	 */
	public String getHactivacion() {
		return hactivacion;
	}

	/**
	 * @param hactivacion the hactivacion to set
	 */
	public void setHactivacion(String hactivacion) {
		this.hactivacion = hactivacion;
	}

	/**
	 * @return the cdProcesoActivacion
	 */
	public String getCdProcesoActivacion() {
		return cdProcesoActivacion;
	}

	/**
	 * @param cdProcesoActivacion the cdProcesoActivacion to set
	 */
	public void setCdProcesoActivacion(String cdProcesoActivacion) {
		this.cdProcesoActivacion = cdProcesoActivacion;
	}

	/**
	 * @return the cdProductoActivacion
	 */
	public String getCdProductoActivacion() {
		return cdProductoActivacion;
	}

	/**
	 * @param cdProductoActivacion the cdProductoActivacion to set
	 */
	public void setCdProductoActivacion(String cdProductoActivacion) {
		this.cdProductoActivacion = cdProductoActivacion;
	}

//	/**
//	 * @return the configuradorManager
//	 */
//	public ConfiguradorPantallaService obtenConfiguradorManager() {
//		return configuradorManager;
//	}

	/**
	 * @return the cdCliente
	 */
	public String getCdCliente() {
		return cdCliente;
	}

	/**
	 * @param cdCliente the cdCliente to set
	 */
	public void setCdCliente(String cdCliente) {
		this.cdCliente = cdCliente;
	}

}