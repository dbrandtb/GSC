package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.AlertaUsuarioVO;
import mx.com.aon.portal.model.EjecutivoCuentaVO;

/**
 * Interface de servicios para el action de la pantalla Alertas de Usuarios
 */
public interface EjecutivoManager {

	public String agregarEjecutivo(String cdEjecutivo, String cdPerson,	String fechaInicial, String fechaFinal, String status)throws ApplicationException ;
	public String agregarGuardarEjecutivoCuenta(EjecutivoCuentaVO ejecutivoCuentaVO)throws ApplicationException;
	public String guardarAtributos(String cdEjecutivo, String cdAtribu,String otValor) throws ApplicationException;
}
