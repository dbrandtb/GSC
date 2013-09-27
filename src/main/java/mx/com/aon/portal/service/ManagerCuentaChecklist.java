package mx.com.aon.portal.service;

import java.util.ArrayList;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.ConfigurarEstructuraVO;
import mx.com.aon.tmp.Endpoint;

/**
 * Interface de servicios para Cuenta Checklist.
 *
 */
public interface ManagerCuentaChecklist {
	
	/**
	 *  Obtiene un registro de agrupaciones de polizas.
	 * 
	 *  @param cdPersona
	 *  @param dsPersona
	 *  
	 *  @return un objeto PagedLis con un registro.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public  PagedList getConfiguraciones(String cdPersona, String dsPersona, int start,int limit) throws ApplicationException;
	
	/**
	 *  Elimina una cuenta de checklist seleccionada.
	 * 
	 *  @param cdConfig
	 *  
	 *  @return @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public  String borraConfiguracion(
			String cdConfig) throws ApplicationException;
	
	/**
	 *  Realiza la copia de una configuracion de cuenta de checklist seleccionada.
	 * 
	 *  @param codigoConfiguracion
	 *  @param codigoElemento
	 *  @param codigoPersona
	 *  
	 *  @return @return objeto de WrapperResultados con el resultado de la operacion.
	 *  
	 *  @throws ApplicationException
	 */
//	public  WrapperResultados copiarConfiguracionCuenta(String codigoConfiguracion, String codigoElemento, String codigoPersona) throws ApplicationException;
	public String copiarConfiguracionCuenta(String codigoConfiguracion, String codigoElemento, String codigoPersona) throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto de clientes.
	 * 
	 *  @param 
	 *  
	 *  @return un objeto ArrayList con un registro.
	 *  
	 *  @throws ApplicationException
	 */
	public  ArrayList<ConfigurarEstructuraVO> obtieneClientes() throws ApplicationException;
	
	public  void setEndpoints(Map<String, Endpoint> endpoints);

}