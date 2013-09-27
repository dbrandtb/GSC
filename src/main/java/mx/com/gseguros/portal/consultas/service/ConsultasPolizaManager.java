package mx.com.gseguros.portal.consultas.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.util.WrapperResultados;

/**
 * Interface de servicios para Consulta de Poliza
 * 
 * @author HMLT
 *
 */
public interface ConsultasPolizaManager {
	
	
	/**
	 * Consulta los datos de una poliza
	 * @param cdunieco Unidad Economica
	 * @param cdramo   Ramo
	 * @param estado   Estado
	 * @param nmpoliza Poliza
	 * @param idper    Identificador Persona
	 * @param nmclient Numero cliente
	 * @return         Wrapper con los datos de la poliza solicitada
	 * @throws         ApplicationException
	 */
	public WrapperResultados consultaPoliza(String cdunieco, String cdramo, String estado, String nmpoliza, String idper, String nmclient)throws ApplicationException;
	
	
	/**
	 * Obtiene los suplementos (historico de movimientos) de una poliza de acuerdo a su numero externo
	 * @param nmpoliex Numero de poliza externo
	 * @return         Wrapper con los suplementos (historico de movimientos) de la poliza solicitada
	 * @throws         ApplicationException
	 */
	public WrapperResultados consultaSuplemento(String nmpoliex) throws ApplicationException;
	
	
	/**
	 * Obtiene una situacion de una poliza de acuerdo a su numero
	 * @param cdunieco Unidad economica
	 * @param cdramo   Ramo del producto
	 * @param estado   Estado de la poliza
	 * @param nmpoliza Numero de poliza
	 * @param nmsuplem Numero de suplemento
	 * @param nmsituac Numero de situacion
	 * @return         Wrapper con las situaciones de la poliza solicitada
	 * @throws         ApplicationException
	 */
	public WrapperResultados consultaSituacion(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem, String nmsituac)throws ApplicationException;
	
	
	/**
	 * Obtiene las coberturas de una poliza
	 * @param cdunieco Unidad economica
	 * @param cdramo   Ramo del producto
	 * @param estado   Estado de la poliza
	 * @param nmpoliza Numero de poliza
	 * @param nmsuplem Numero de suplemento
	 * @param nmsituac Numero de situacion
	 * @return         Wrapper con las coberturas de la poliza solicitada
	 * @throws         ApplicationException
	 */
	public WrapperResultados consultaCoberturas(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem, String nmsituac)throws ApplicationException;
	
	/**
	 * Obtiene las polizas de un asegurado de acuerdo a su RFC 
	 * @param rfc RFC del asegurado
	 * @return    Wrapper con las polizas del asegurado solicitado
	 * @throws    ApplicationException
	 */
	public WrapperResultados obtienePolizasAsegurado(String rfc)throws ApplicationException;
	
	
	/**
	 * Obtiene los datos de la tarifa de una poliza
	 * @param cdunieco Unidad economica
	 * @param cdramo   Ramo del producto
	 * @param estado   Estado de la poliza
	 * @param nmpoliza Numero de poliza
	 * @param nmsuplem Numero de suplemento
	 * @return         Wrapper con los datos de la tarifa de la poliza solicitada
	 * @throws ApplicationException
	 */
	public WrapperResultados consultaDatosTarifa(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem)throws ApplicationException;
	
	
	/**
	 * Obtiene las polizas de un agente 
	 * @param cdagente Codigo de agente
	 * @return         Wrapper con las polizas del agente solicitado
	 * @throws ApplicationException
	 */
	public WrapperResultados consultaPolizasAgente(String cdagente)throws ApplicationException;
	
	
	/**
	 * Obtiene los recibos de un agente de acuerdo a los datos de la poliza
	 * @param cdunieco Unidad economica
	 * @param cdramo   Ramo del producto
	 * @param estado   Estado de la poliza
	 * @param nmpoliza Numero de poliza
	 * @return         Wrapper con los recibos del agente solicitado
	 * @throws         ApplicationException	
	 */
	public WrapperResultados consultaRecibosAgente(String cdunieco, String cdramo, String estado, String nmpoliza)throws ApplicationException;
	
	
	/**
	 * Obtiene los datos de un agente de acuerdo a su codigo de agente
	 * @param cdagente Codigo de agente a buscar
	 * @return         Wrapper con los datos del agente solicitado
	 * @throws         ApplicationException
	 */
	public WrapperResultados consultaAgente(String cdagente)throws ApplicationException;
	
	
	/**
	 * Obtiene los datos de un asegurado de acuerdo a los datos de la poliza 
	 * @param cdunieco Unidad economica
	 * @param cdramo   Ramo del producto
	 * @param estado   Estado de la poliza
	 * @param nmpoliza Numero de poliza
	 * @param nmsuplem Numero de suplemento
	 * @return         Wrapper con los datos del asegurado solicitado
	 * @throws         ApplicationException
	 */
	public WrapperResultados consultaDatosAsegurado(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem) throws ApplicationException;
	
	
}