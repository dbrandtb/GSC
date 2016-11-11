package mx.com.gseguros.portal.cotizacion.service;

import java.util.Date;
import java.io.File;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlist2SmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;

public interface CotizacionAutoManager
{
	public void setSession(Map<String,Object>session);
	public ManagerRespuestaImapSmapVO cotizacionAutoIndividual(
			String ntramite
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String cdusuari
			,String cdsisrol
			);
	public ManagerRespuestaSmapVO cargarRetroactividadSuplemento(
			String cdunieco
			,String cdramo
			,String cdtipsup
			,String cdusuari
			,String cdtipsit);
	public ManagerRespuestaSmapVO cargarSumaAseguradaRamo5(
			String cdtipsit
			,String clave
			,String modelo
			,String cdsisrol);
	public ManagerRespuestaImapSmapVO emisionAutoIndividual(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String ntramite
			,String cdusuari
			);
	public ManagerRespuestaSmapVO cargarDatosComplementariosAutoInd(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			);
	public ManagerRespuestaSmapVO cargarValoresSituacion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			);
	public ManagerRespuestaVoidVO movimientoMpoliper(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdrol
			,String cdperson
			,String nmsuplem
			,String status
			,String nmorddom
			,String swreclam
			,String accion
			,String swexiper);
	public ManagerRespuestaVoidVO guardarComplementariosAutoIndividual(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String agenteSec
			,String porpartiSec
			,String feini
			,String fefin
			,Map<String,String>tvalopol
			,Map<String,String>tvalosit
			,String ntramite
			,String cdagente
			);
	public ManagerRespuestaSmapVO cargarConfiguracionCotizacion(
			String cdramo
			,String cdtipsit
			,String cdusuari);
	public ManagerRespuestaVoidVO guardarConfiguracionCotizacion(
			String cdramo
			,String cdtipsit
			,String cdusuari
			,Map<String,String>valores);
	public ManagerRespuestaSlistVO cargarParamerizacionConfiguracionCoberturas(
			String cdtipsit
			,String cdsisrol
			,String negocio
			,String tipoServicio
			,String modelo
			,String tipoPersona
			,String submarca
			,String clavegs
			);
	public ManagerRespuestaImapSmapVO cotizacionAutoFlotilla(
			String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String ntramite
			,String tipoflot
			,boolean endoso
			);
	public ManagerRespuestaSlistSmapVO cotizarAutosFlotilla(
			String cdusuari
			,String cdsisrol
			,String cdelemen
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String feini
			,String fefin
			,String cdagente
			,String cdpersonCli
			,String cdideperCli
			,List<Map<String,String>> tvalosit
			,List<Map<String,String>> baseTvalosit
			,List<Map<String,String>> confTvalosit
			,boolean noTarificar
			,String tipoflot
			,Map<String,String>tvalopol
			);
	public ManagerRespuestaVoidVO cargarValidacionTractocamionRamo5(String poliza,String rfc);
	public ManagerRespuestaSlistVO procesarCargaMasivaFlotilla(String cdramo,String cdtipsit,String respetar,File excel);
	public ManagerRespuestaSlist2SmapVO cargarCotizacionAutoFlotilla(
			String cdramo
			,String nmpoliza
			,String cdusuari
			,String cdsisrol
			);
	public ManagerRespuestaImapSmapVO emisionAutoFlotilla(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String ntramite
			,String cdusuari
			);
	public ManagerRespuestaVoidVO guardarComplementariosAutoFlotilla(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String agenteSec
			,String porpartiSec
			,String feini
			,String fefin
			,Map<String,String>tvalopol
			,List<Map<String,String>>tvalosit
			,String ntramite
			);
	
	public ManagerRespuestaSlistVO recotizarAutoFlotilla(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,boolean notarifica
			,String cdusuari
			,String cdelemen
			,String cdtipsit
			,String cdperpag
			);
	
	public ManagerRespuestaSmapVO cargarObligatorioTractocamionRamo5(String clave);
	
	public ManagerRespuestaSmapVO cargarDetalleNegocioRamo5(String negocio, String cdramo, String cdtipsit, String cdsisrol, String cdusuari);
	
	
	public ManagerRespuestaVoidVO guardarPantallaBeneficiarios(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,List<Map<String,String>>mpoliperMpersona);
	
	public ManagerRespuestaSlistVO cargarParamerizacionConfiguracionCoberturasRol(
			String cdtipsit
			,String cdsisrol
			);
	
	public String obtieneValidacionRetroactividad(
			String numSerie
			,Date feini) throws ApplicationException;
}