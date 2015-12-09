package mx.com.gseguros.mesacontrol.service;

import java.util.List;
import java.util.Map;

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
			,String cdexpres
			,String accion
			)throws Exception;
	
	public void guardarCoordenadas(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,List<Map<String,String>>list
			)throws Exception;
	
	public String expresion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdexpres
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
			,List<Map<String,String>>list
			)throws Exception;
	
	public Map<String,Item> debugScreen(StringBuilder sb) throws Exception;
}