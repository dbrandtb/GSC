package mx.com.gseguros.portal.endosos.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.cotizacion.model.SlistSmapVO;
import mx.com.gseguros.portal.general.model.RespuestaVO;

public interface EndososAutoManager
{
	public Map<String,Object> construirMarcoEndosos(String cdusuari,String cdsisrol)throws Exception;
	
	public String recuperarColumnasIncisoRamo(String cdramo) throws Exception;
	
	public SlistSmapVO recuperarEndososClasificados(
			String cdramo
			,String nivel
			,String multiple
			,String tipoflot
			,List<Map<String,String>>incisos
			,String cdsisrol
			)throws Exception;
	
	public Map<String,Item>pantallaEndosoValosit(
			String cdtipsup
			,String cdramo
			)throws Exception;
	
	public void guardarTvalositEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String status
			,String cdtipsit
			,String otvalor01,String otvalor02,String otvalor03,String otvalor04,String otvalor05
			,String otvalor06,String otvalor07,String otvalor08,String otvalor09,String otvalor10
			,String otvalor11,String otvalor12,String otvalor13,String otvalor14,String otvalor15
			,String otvalor16,String otvalor17,String otvalor18,String otvalor19,String otvalor20
			,String otvalor21,String otvalor22,String otvalor23,String otvalor24,String otvalor25
			,String otvalor26,String otvalor27,String otvalor28,String otvalor29,String otvalor30
			,String otvalor31,String otvalor32,String otvalor33,String otvalor34,String otvalor35
			,String otvalor36,String otvalor37,String otvalor38,String otvalor39,String otvalor40
			,String otvalor41,String otvalor42,String otvalor43,String otvalor44,String otvalor45
			,String otvalor46,String otvalor47,String otvalor48,String otvalor49,String otvalor50
			,String otvalor51,String otvalor52,String otvalor53,String otvalor54,String otvalor55
			,String otvalor56,String otvalor57,String otvalor58,String otvalor59,String otvalor60
			,String otvalor61,String otvalor62,String otvalor63,String otvalor64,String otvalor65
			,String otvalor66,String otvalor67,String otvalor68,String otvalor69,String otvalor70
			,String otvalor71,String otvalor72,String otvalor73,String otvalor74,String otvalor75
			,String otvalor76,String otvalor77,String otvalor78,String otvalor79,String otvalor80
			,String otvalor81,String otvalor82,String otvalor83,String otvalor84,String otvalor85
			,String otvalor86,String otvalor87,String otvalor88,String otvalor89,String otvalor90
			,String otvalor91,String otvalor92,String otvalor93,String otvalor94,String otvalor95
			,String otvalor96,String otvalor97,String otvalor98,String otvalor99
			,String tstamp)throws Exception;
	
	public void confirmarEndosoTvalositAuto(
			String cdtipsup
			,String tstamp
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdusuari
			,String cdsisrol
			,String cdelemen
			,UserVO usuarioSesion
			)throws Exception;
	
	/**
	 * @return incisoPoliza => Map String String,
	 *         tvalopol     => Map String String,
	 *         tconvalsit   => List Map String String,
	 *         cdperson     => String,
	 *         cdideper     => cdideper
	 */
	public Map<String,Object> recuperarDatosEndosoAltaIncisoAuto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception;
	
	public void confirmarEndosoAltaIncisoAuto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,List<Map<String,String>>incisos
			,String cdusuari
			,String cdelemen
			,String cdtipsup
			,UserVO usuarioSesion
			)throws Exception;
	
	public Map<String,Item> endosoBajaIncisos(
			String cdramo
			)throws Exception;
	
	public void confirmarEndosoBajaIncisos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,List<Map<String,String>>incisos
			,String cdusuari
			,String cdelemen
			,String cdtipsup
			,UserVO usuarioSesion
			)throws Exception;

	public void guardarEndosoAseguradoAlterno(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdelemen
			,String cdusuari
			,String cdtipsup
			,String status
			,String fechaEndoso
			,Date dFechaEndoso
			,Map<String, String> otvalores
			)throws Exception;
	
	public void guardarEndosoVigenciaPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdelemen
			,String cdusuari
			,String cdtipsup
			,String status
			,String fechaEndoso
			,Date dFechaEndoso
			,String feefecto
			,String feproren
			)throws Exception;
	
	public void validarEndosoAnterior(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			)throws Exception;

	public List<Map<String,String>> obtenerRetroactividad(
			String cdsisrol
			,String cdramo
			,String cdtipsup
			,String fechaProceso
			)throws Exception;
}