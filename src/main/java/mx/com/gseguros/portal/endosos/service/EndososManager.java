package mx.com.gseguros.portal.endosos.service;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Tatri;

public interface EndososManager
{
    public List<Map<String,String>>  obtenerEndosos(Map<String,String>params) throws Exception;
    public Map<String,String>        guardarEndosoNombres(Map<String,Object>params) throws Exception;
    public Map<String, String>       confirmarEndosoB(Map<String, String> params) throws Exception;
    public Map<String,String>        guardarEndosoDomicilio(Map<String,Object>params) throws Exception;
    public List<Map<String, String>> reimprimeDocumentos(Map<String, String> params) throws Exception;
    public List<Map<String, String>> obtieneCoberturasDisponibles(Map<String, String> params) throws Exception;
    public Map<String,String>        guardarEndosoCoberturas(Map<String,Object>params) throws Exception;
	public List<Tatri>               obtPantallaAlvaro(Object cduno,Object cddos,Object cdtres,Object cdcuatro,Object cdcinco
			                             ,Object cdseis,Object cdsiete,Object cdocho,Object cdnueve,Object cddiez) throws Exception;
}