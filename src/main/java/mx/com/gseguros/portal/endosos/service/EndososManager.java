package mx.com.gseguros.portal.endosos.service;

import java.util.List;
import java.util.Map;

public interface EndososManager
{
    public List<Map<String,String>>  obtenerEndosos(Map<String,String>params)                 throws Exception;
    public Map<String,String>        guardarEndosoNombres(Map<String,Object>params)           throws Exception;
    public Map<String, String>       confirmarEndosoB(Map<String, String> params)             throws Exception;
    public Map<String,String>        guardarEndosoDomicilio(Map<String,Object>params)         throws Exception;
    public List<Map<String, String>> reimprimeDocumentos(Map<String, String> params)          throws Exception;
    public List<Map<String, String>> obtieneCoberturasDisponibles(Map<String, String> params) throws Exception;
    public Map<String,String>        guardarEndosoCoberturas(Map<String,Object>params)        throws Exception;
	public List<Map<String, String>> obtenerAtributosCoberturas(Map<String, String> params)   throws Exception;
	public Map<String,Object>        sigsvalipolEnd(Map<String, String> params)               throws Exception;
	public Map<String,String>        guardarEndosoClausulas(Map<String,Object>params)         throws Exception;
	public Map<String,String>        calcularValorEndoso(Map<String,Object>params)            throws Exception;
	public Map<String,String>        iniciarEndoso(Map<String,String>params)                  throws Exception;
	public Map<String,String>        iniciarEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String fecha
			,String cdelemento
			,String cdusuari
			,String proceso
			,String cdtipsup) throws Exception;
	public void                      insertarTworksupEnd(Map<String,String>params)            throws Exception;
	public void                      insertarTworksupSitTodas(Map<String,String>params)       throws Exception;
	public Map<String, String>       obtieneDatosMpolisit(Map<String, String> params)         throws Exception;
	public Map<String, String>       obtieneDatosMpolisit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)         throws Exception;
	public List<Map<String, String>> obtenerNombreEndosos()                                   throws Exception;
	public void                      actualizarFenacimi(Map<String, String> params)           throws Exception;
	public void                      actualizarSexo(Map<String, String> params)               throws Exception;
	public List<Map<String, String>> obtenerCdpersonMpoliper(Map<String, String> params)      throws Exception;
	public String                    obtenerNtramiteEmision(
			                             String cdunieco,String cdramo,
			                             String estado,String nmpoliza)                       throws Exception;
	public void                      validaEndosoAnterior(Map<String, String> params)         throws Exception;
	public void                      actualizaDeducibleValosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String deducible) throws Exception;
	public void                      actualizaCopagoValosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String deducible) throws Exception;
	public Map<String,String>        pClonarPolizaReexped(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String fecha) throws Exception;
	public List<Map<String, String>> obtenerValositPorNmsuplem(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem) throws Exception;
}