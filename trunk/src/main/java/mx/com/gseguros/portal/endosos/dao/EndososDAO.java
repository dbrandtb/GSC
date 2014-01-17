package mx.com.gseguros.portal.endosos.dao;

import java.util.List;
import java.util.Map;

public interface EndososDAO
{
    public List<Map<String,String>> obtenerEndosos(Map<String,String>params) throws Exception;
    public Map<String, String>      guardarEndosoNombres(Map<String, Object> params) throws Exception;
    public Map<String, String>      confirmarEndosoB(Map<String, String> params) throws Exception;
    public Map<String, String>      guardarEndosoDomicilio(Map<String, Object> params) throws Exception;
    public List<Map<String,String>> reimprimeDocumentos(Map<String,String>params) throws Exception;
    public List<Map<String,String>> obtieneCoberturasDisponibles(Map<String,String>params) throws Exception;
    public Map<String, String>      guardarEndosoCoberturas(Map<String, Object> params) throws Exception;
	public List<Map<String,String>> obtenerAtributosCoberturas(Map<String, String> params) throws Exception;
	public Map<String,Object>       sigsvalipolEnd(Map<String, String> params) throws Exception;
	public Map<String, String>      guardarEndosoClausulas(Map<String, Object> params) throws Exception;
	public Map<String, String>      calcularValorEndoso(Map<String, Object> params) throws Exception;
	public Map<String, String>      iniciarEndoso(Map<String, String> params) throws Exception;
	public void                     insertarTworksupEnd(Map<String, String> params) throws Exception;
	public Map<String, String>      obtieneDatosMpolisit(Map<String, String> params) throws Exception;
	public List<Map<String,String>> obtenerNombreEndosos() throws Exception;
	public void                     actualizarFenacimi(Map<String, String> params) throws Exception;
	public void                     actualizarSexo(Map<String, String> params) throws Exception;
	public List<Map<String,String>> obtenerCdpersonMpoliper(Map<String, String> params) throws Exception;
}