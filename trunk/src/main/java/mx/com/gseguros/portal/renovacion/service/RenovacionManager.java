package mx.com.gseguros.portal.renovacion.service;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;

public interface RenovacionManager
{
	public ManagerRespuestaImapVO  pantallaRenovacion(String cdsisrol);
	public ManagerRespuestaSlistVO buscarPolizasRenovables(String cdunieco,String cdramo,String anio,String mes);
	public ManagerRespuestaVoidVO  renovarPolizas(
			List<Map<String,String>>polizas
			,String cdusuari
			,String anio
			,String mes
			,String rutaDocumentosPoliza
			,String rutaServidorReports
			,String passServidorReports
			);
}