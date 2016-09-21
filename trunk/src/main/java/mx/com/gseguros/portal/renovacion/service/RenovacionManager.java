package mx.com.gseguros.portal.renovacion.service;

import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.UserVO;
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
			,UserVO usuario
			);
	public ManagerRespuestaImapVO pantallaRenovacionIndividual(String cdsisrol);
	
	public ManagerRespuestaSlistVO buscarPolizasRenovacionIndividual(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza
			);
	public ManagerRespuestaSlistVO buscarPolizasRenovacionIndividualMasiva(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String cdtipsit,
			String fecini,
			String fecfin,
			String status
			);
	public String renuevaPolizaIndividual(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza
			);
}