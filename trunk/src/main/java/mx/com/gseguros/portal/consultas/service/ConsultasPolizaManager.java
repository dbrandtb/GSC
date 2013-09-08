package mx.com.gseguros.portal.consultas.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.util.WrapperResultados;

/**Interface de servicios para Consulta de Poliza Renovada
 * 
 * @author HMLT
 *
 */
public interface ConsultasPolizaManager {

	/**
	 * 
	 * @param cdunieco Unidad Economica
	 * @param cdramo   Ramo
	 * @param estado   Estado
	 * @param nmpoliza Poliza
	 * @param idper    Identificador Persona
	 * @param nmclient Numero cliente
	 * @return
	 * @throws ApplicationException
	 */
	public WrapperResultados consultaPoliza(String cdunieco, String cdramo, String estado, String nmpoliza, String idper, String nmclient)throws ApplicationException;

	public WrapperResultados consultaSuplemento(String cdunieco, String cdramo, String estado, String nmpoliza)throws ApplicationException;
	public WrapperResultados consultaSituacion(String cdunieco, String cdramo, String estado, String nmpoliza, String suplemento, String nmsituac)throws ApplicationException;
	public WrapperResultados consultaCoberturas(String cdunieco, String cdramo, String estado, String nmpoliza, String suplemento, String nmsituac)throws ApplicationException;
	
}