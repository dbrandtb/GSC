package mx.com.gseguros.mesacontrol.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.mesacontrol.model.FlujoVO;
import mx.com.gseguros.portal.cotizacion.model.Item;

public interface FlujoMesaControlManager
{
	
	public Map<String,Item> workflow(
			StringBuilder sb
			,String cdsisrol
			)throws Exception;
	
	public void movimientoTtipflumc(
			StringBuilder sb
			,String accion
			,String cdtipflu
			,String dstipflu
			,String cdtiptra
			,String swreqpol
			,String swmultipol
			,String cdtipsup
			)throws Exception;
	
	public void movimientoTflujomc(
			StringBuilder sb
			,String accion
			,String cdtipflu
			,String cdflujomc
			,String dsflujomc
			,String swfinal
			)throws Exception;
	
	public void movimientoCatalogo(
			StringBuilder sb
			,String accion
			,String tipo
			,Map<String,String> params
			)throws Exception;
	
	public String registrarEntidad(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String tipo
			,String clave
			,String webid
			,String xpos
			,String ypos
			)throws Exception;
	
	public void borrarEntidad(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String tipo
			,String clave
			,String webid
			)throws Exception;
	
	public List<Map<String,String>> cargarModelado(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			)throws Exception;
	
	public Map<String,Object> cargarDatosEstado(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdestadomc
			)throws Exception;
	
	public void guardarDatosEstado(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdestadomc
			,String accion
			,String webid
			,String xpos
			,String ypos
			,String timemaxh
			,String timemaxm
			,String timewrn1h
			,String timewrn1m
			,String timewrn2h
			,String timewrn2m
			,String cdtipasig
			,String swescala
			,List<Map<String,String>>list
			)throws Exception;
	
	public String registrarConnection(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String idorigen
			,String iddestin
			)throws Exception;
	
	public Map<String,String> cargarDatosValidacion(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdvalida
			)throws Exception;
	
	public void guardarDatosValidacion(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdvalida
			,String webid
			,String xpos
			,String ypos
			,String dsvalida
			,String cdvalidafk
			,String jsvalida
			,String accion
			)throws Exception;
	
	public void guardarCoordenadas(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,List<Map<String,String>>list
			)throws Exception;
	
	public String ejecutaValidacion(
			StringBuilder sb
			,FlujoVO flujo
			,String cdvalidafk
			)throws Exception;
	
	public Map<String,Object> cargarDatosRevision(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdrevisi
			)throws Exception;
	
	public void guardarDatosRevision(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdrevisi
			,String dsrevisi
			,String accion
			,String webid
			,String xpos
			,String ypos
			,List<Map<String,String>>list
			)throws Exception;
	
	public void movimientoTdocume(
			StringBuilder sb
			,String accion
			,String cddocume
			,String dsdocume
			,String cdtiptra
			)throws Exception;
	
	public void borrarConnection(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdaccion
			)throws Exception;
	
	public Map<String,Object> cargarDatosAccion(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdaccion
			)throws Exception;
	
	public void guardarDatosAccion(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdaccion
			,String dsaccion
			,String accion
			,String idorigen
			,String iddestin
			,String cdvalor
			,String cdicono
			,String swescala
			,String aux
			,List<Map<String,String>>list
			)throws Exception;
	
	public Map<String,Item> debugScreen(StringBuilder sb) throws Exception;
	
	public Map<String,Object> mesaControl(
			StringBuilder sb
			,String cdsisrol
			,String agrupamc
			,String cdusuari
			)throws Exception;
	
	public Map<String,Object> recuperarTramites(
			StringBuilder sb
			,String agrupamc
			,String status
			,String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String cdagente
			,String ntramite
			,String fedesde
			,String fehasta
			,int start
			,int limit
			)throws Exception;
	
	public Map<String,String> recuperarPolizaUnica(
			StringBuilder sb
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public String registrarTramite(
			StringBuilder sb , String cdunieco , String cdramo     , String estado   , String nmpoliza
			,String nmsuplem , String cdsucadm , String cdsucdoc   , String cdtiptra
			,Date ferecepc   , String cdagente , String referencia , String nombre
			,Date festatus   , String status   , String comments   , String nmsolici
			,String cdtipsit , String cdusuari , String cdsisrol   , String swimpres
			,String cdtipflu , String cdflujomc
			,Map<String, String> valores, String cdtipsup
			)throws Exception;
	
	public List<Map<String,String>>cargarAccionesEntidad(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String tipoent
			,String cdentidad
			,String webid
			,String cdsisrol
			)throws Exception;
	
	public void procesoDemo(
			StringBuilder sb
			,FlujoVO flujo
			,String cdusuari
			,String cdsisrol
			)throws Exception;
	
	public List<Map<String,String>> ejecutaRevision(StringBuilder sb, FlujoVO flujo)throws Exception;
	
	public String turnarTramite(
			StringBuilder sb
			,String ntramite
			,String statusOld
			,String cdtipasigOld
			,String statusNew
			,String cdtipasigNew
			,String cdusuariSes
			,String cdsisrolSes
			,String comments
			)throws Exception;
	
	public Map<String,Object> recuperarDatosTramiteValidacionCliente(StringBuilder sb, FlujoVO flujo)throws Exception;
	
	public String turnarDesdeComp(
			StringBuilder sb
			,String cdusuari
			,String cdsisrol
			,String cdtipflu
			,String cdflujomc
			,String ntramite
			,String statusOld
			,String statusNew
			,String swagente
			,String comments
			)throws Exception;
}