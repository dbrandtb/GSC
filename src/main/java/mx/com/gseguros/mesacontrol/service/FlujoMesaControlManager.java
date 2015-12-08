package mx.com.gseguros.mesacontrol.service;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Item;

public interface FlujoMesaControlManager
{
	
	public Map<String,Item> workflow(
			StringBuilder sb
			,String cdsisrol
			) throws Exception;
	
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
	
	public void registrarEntidad(
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
	
}