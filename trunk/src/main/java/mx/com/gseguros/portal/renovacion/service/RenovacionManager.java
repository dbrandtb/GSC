package mx.com.gseguros.portal.renovacion.service;

import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;

public interface RenovacionManager
{
	public ManagerRespuestaImapVO  pantallaRenovacion(String cdsisrol);
	public ManagerRespuestaSlistVO buscarPolizasRenovables(String anio,String mes);
}