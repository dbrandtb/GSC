package mx.com.aon.portal.service.impl;

import java.util.HashMap;


import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.DatosMovPolizasVO;
import mx.com.aon.portal.model.DatosVarRolVO;
import mx.com.aon.portal.model.DatosVblesObjAsegurablesVO;
import mx.com.aon.portal.model.EncabezadoPolizaVO;
import mx.com.aon.portal.model.MovTValoPolVO;
import mx.com.aon.portal.model.NuevaCoberturaVO;
import mx.com.aon.portal.service.ConsultaPolizaRenovadaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

public class ConsultaPolizaRenovadaManagerImpl extends AbstractManager implements ConsultaPolizaRenovadaManager {
	
	/**
	 * Metodo que obtiene los datos generales definidos 
	 * a nivel póliza para la pantalla Datos Generales de Consulta de Póliza Renovada.
	 * Usa el Store Procedure PKG_RENOVA.P_OBTIENE_POLIZA_ENC
	 * 
	 * @param cdUniEco: Código de la Aseguradora
	 * @param cdRamo: Código del Producto Asegurado.
	 * @param estado: Estado de la póliza.
	 * @param nmPoliza: Número de la Póliza.
	 * 
	 * @return EncabezadoPolizaVO 
	 */
	@SuppressWarnings("unchecked")
	public EncabezadoPolizaVO getPolizaEncabezado(String cdUniEco,
			String cdRamo, String estado, String nmPoliza) throws ApplicationException{
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_estado_i", estado);
		map.put("pv_nmpoliza_i", nmPoliza);
		
		String endpointName = "OBTIENE_POLIZA_ENCABEZADO";
				
		return (EncabezadoPolizaVO) getBackBoneInvoke(map, endpointName);
	}
	
	/**
	 * Metodo que obtiene los atributos variables definidos 
	 * a nivel póliza para la pantalla Consulta de Póliza Renovada.
	 * Usa el Store Procedure PKG_SATELITES.P_OBTIENE_TVALOPOL.
	 * 
	 * @param cdUniEco: Código de la Aseguradora
	 * @param cdRamo: Código del Producto Asegurado.
	 * @param estado: Estado de la póliza.
	 * @param nmPoliza: Número de la Póliza.
	 * 
	 * @return PagedList 
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerValoresPoliza(String cdUniEco, String cdRamo,
			String estado, String nmPoliza, String cdAtribu, int start, int limit)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco", cdUniEco);
		map.put("pv_cdramo", cdRamo);
		map.put("pv_estado", estado);
		map.put("pv_nmpoliza", nmPoliza);
		map.put("pv_cdatribu", cdAtribu);
		
		String endpointName = "OBTIENE_TVALOPOL";
				
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}

	@SuppressWarnings("unchecked")
	public String guardarDatosGenerales(DatosMovPolizasVO datosMovPolizasVO)
			throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco", datosMovPolizasVO.getCdunieco());
		map.put("pv_cdramo", datosMovPolizasVO.getCdramo());
		map.put("pv_estado", datosMovPolizasVO.getEstado());
		map.put("pv_nmpoliza", datosMovPolizasVO.getNmpoliza());
		map.put("pv_nmsuplem", datosMovPolizasVO.getNmsuplem());
		map.put("pv_status", datosMovPolizasVO.getStatus());
		map.put("pv_swestado", datosMovPolizasVO.getSwestado());
		map.put("pv_nmsolici", datosMovPolizasVO.getNmsolici());
		map.put("pv_feautori", datosMovPolizasVO.getFeautori());
		map.put("pv_cdmotanu", datosMovPolizasVO.getCdmotanu());
		map.put("pv_feanulac", datosMovPolizasVO.getFeanulac());
		map.put("pv_swautori", datosMovPolizasVO.getSwautori());
		map.put("pv_cdmoneda", datosMovPolizasVO.getCdmoneda());
		map.put("pv_feinisus", datosMovPolizasVO.getFeinisus());
		map.put("pv_fefinsus", datosMovPolizasVO.getFefinsus());
		map.put("pv_ottempot", datosMovPolizasVO.getOttempot());
		map.put("pv_feefecto", datosMovPolizasVO.getFeefecto());
		map.put("pv_hhefecto", datosMovPolizasVO.getHhefecto());
		map.put("pv_feproren", datosMovPolizasVO.getFeproren());
		map.put("pv_fevencim", datosMovPolizasVO.getFevencim());
		map.put("pv_nmrenova", datosMovPolizasVO.getNmrenova());
		map.put("pv_ferecibo", datosMovPolizasVO.getFerecibo());
		map.put("pv_feultsin", datosMovPolizasVO.getFeultsin());
		map.put("pv_nmnumsin", datosMovPolizasVO.getNmnumsin());
		map.put("pv_cdtipcoa", datosMovPolizasVO.getCdtipcoa());
		map.put("pv_swtarifi", datosMovPolizasVO.getSwtarifi());
		map.put("pv_swabrido", datosMovPolizasVO.getSwabrido());
		map.put("pv_feemisio", datosMovPolizasVO.getFeemisio());
		map.put("pv_cdperpag", datosMovPolizasVO.getCdperpag());
		map.put("pv_nmpoliex", datosMovPolizasVO.getNmpoliex());
		map.put("pv_nmcuadro", datosMovPolizasVO.getNmcuadro());
		map.put("pv_porredau", datosMovPolizasVO.getPorredau());
		map.put("pv_swconsol", datosMovPolizasVO.getSwconsol());
		map.put("pv_nmpolant", datosMovPolizasVO.getNmpolant());
		map.put("pv_nmpolnva", datosMovPolizasVO.getNmpolnva());
		map.put("pv_fesolici", datosMovPolizasVO.getFesolici());
		map.put("pv_cdramant", datosMovPolizasVO.getCdramant());
		map.put("pv_cdmejred", datosMovPolizasVO.getCdmejred());
		map.put("pv_nmpoldoc", datosMovPolizasVO.getNmpoldoc());
		map.put("pv_nmpoliza2", datosMovPolizasVO.getNmpoliza2());
		map.put("pv_nmrenove", datosMovPolizasVO.getNmrenove());
		map.put("pv_nmsuplee", datosMovPolizasVO.getNmsuplee());
		map.put("pv_ttipcamc", datosMovPolizasVO.getTtipcamv());
		map.put("pv_ttipcamv", datosMovPolizasVO.getTtipcamv());
		map.put("pv_swpatent", datosMovPolizasVO.getSwpatent());
		map.put("pv_accion", datosMovPolizasVO.getAccion());
		
		String endpointName = "MOV_MPOLIZAS";
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public String guardarValoresPoliza(MovTValoPolVO movTValoPolVO)
			throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco", movTValoPolVO.getCdunieco());
		map.put("pv_cdramo", movTValoPolVO.getCdramo());
		map.put("pv_estado", movTValoPolVO.getEstado());
		map.put("pv_nmpoliza", movTValoPolVO.getNmpoliza());
		map.put("pv_nmsuplem", movTValoPolVO.getNmsuplem());
		map.put("pv_status", movTValoPolVO.getStatus());
		map.put("pv_otvalor01", movTValoPolVO.getPi_otvalor01());
		map.put("pi_otvalor02", movTValoPolVO.getPi_otvalor02());
		map.put("pi_otvalor03", movTValoPolVO.getPi_otvalor03());
		map.put("pi_otvalor04", movTValoPolVO.getPi_otvalor04());
		map.put("pi_otvalor05", movTValoPolVO.getPi_otvalor05());
		map.put("pi_otvalor06", movTValoPolVO.getPi_otvalor06());
		map.put("pi_otvalor07", movTValoPolVO.getPi_otvalor07());
		map.put("pi_otvalor08", movTValoPolVO.getPi_otvalor08());
		map.put("pi_otvalor09", movTValoPolVO.getPi_otvalor09());
		map.put("pi_otvalor10", movTValoPolVO.getPi_otvalor10());
		map.put("pi_otvalor11", movTValoPolVO.getPi_otvalor11());
		map.put("pi_otvalor12", movTValoPolVO.getPi_otvalor12());
		map.put("pi_otvalor13", movTValoPolVO.getPi_otvalor13());
		map.put("pi_otvalor14", movTValoPolVO.getPi_otvalor14());
		map.put("pi_otvalor15", movTValoPolVO.getPi_otvalor15());
		map.put("pi_otvalor16", movTValoPolVO.getPi_otvalor16());
		map.put("pi_otvalor17", movTValoPolVO.getPi_otvalor17());
		map.put("pi_otvalor18", movTValoPolVO.getPi_otvalor18());
		map.put("pi_otvalor19", movTValoPolVO.getPi_otvalor19());
		map.put("pi_otvalor20", movTValoPolVO.getPi_otvalor20());
		map.put("pi_otvalor21", movTValoPolVO.getPi_otvalor21());
		map.put("pi_otvalor22", movTValoPolVO.getPi_otvalor22());
		map.put("pi_otvalor23", movTValoPolVO.getPi_otvalor23());
		map.put("pi_otvalor24", movTValoPolVO.getPi_otvalor24());
		map.put("pi_otvalor25", movTValoPolVO.getPi_otvalor25());
		map.put("pi_otvalor26", movTValoPolVO.getPi_otvalor26());
		map.put("pi_otvalor27", movTValoPolVO.getPi_otvalor27());
		map.put("pi_otvalor28", movTValoPolVO.getPi_otvalor28());
		map.put("pi_otvalor29", movTValoPolVO.getPi_otvalor29());
		map.put("pi_otvalor30", movTValoPolVO.getPi_otvalor30());
		map.put("pi_otvalor31", movTValoPolVO.getPi_otvalor31());
		map.put("pi_otvalor32", movTValoPolVO.getPi_otvalor32());
		map.put("pi_otvalor33", movTValoPolVO.getPi_otvalor33());
		map.put("pi_otvalor34", movTValoPolVO.getPi_otvalor34());
		map.put("pi_otvalor35", movTValoPolVO.getPi_otvalor35());
		map.put("pi_otvalor36", movTValoPolVO.getPi_otvalor36());
		map.put("pi_otvalor37", movTValoPolVO.getPi_otvalor37());
		map.put("pi_otvalor38", movTValoPolVO.getPi_otvalor38());
		map.put("pi_otvalor39", movTValoPolVO.getPi_otvalor39());
		map.put("pi_otvalor40", movTValoPolVO.getPi_otvalor40());
		map.put("pi_otvalor41", movTValoPolVO.getPi_otvalor41());
		map.put("pi_otvalor42", movTValoPolVO.getPi_otvalor42());
		map.put("pi_otvalor43", movTValoPolVO.getPi_otvalor43());
		map.put("pi_otvalor44", movTValoPolVO.getPi_otvalor44());
		map.put("pi_otvalor45", movTValoPolVO.getPi_otvalor45());
		map.put("pi_otvalor46", movTValoPolVO.getPi_otvalor46());
		map.put("pi_otvalor47", movTValoPolVO.getPi_otvalor47());
		map.put("pi_otvalor48", movTValoPolVO.getPi_otvalor48());
		map.put("pi_otvalor49", movTValoPolVO.getPi_otvalor49());
		map.put("pi_otvalor50", movTValoPolVO.getPi_otvalor50());
		
		String endpointName = "MOV_TVALOPOL";
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}
	
	/**
	 * Metodo que obtiene los objetos asegurables, personas, para 
	 * la pantalla Consulta de Póliza Renovada.
	 * Usa el SP PKG_RENOVA.P_OBTIENE_OBJETO_ASEGURABLE.
	 * 
	 * @params cdUniEco
	 * @params cdRamo
	 * @params estado
	 * @params nmPoliza
	 * @params int start
	 * @params int limit
	 * 
	 * @return PagedList 
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerObjetosAsegurables(String cdUniEco, String cdRamo,
			String estado, String nmPoliza, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_estado_i", estado);
		map.put("pv_nmpoliza_i", nmPoliza);
		
		String endpointName = "OBTIENE_OBJETO_ASEGURABLE";
		
		return pagedBackBoneInvoke(map, endpointName, start, limit);		
	}
	
	/**
	 * Metodo que obtiene los objetos, funciones, que realizan en la poliza para 
	 * la pantalla Consulta de Póliza Renovada.
	 * Usa el SP PKG_RENOVA.P_OBTIENE_FUNCION.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param cdPerson
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList 
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerFunciones(String cdUniEco, String cdRamo,
			String estado, String nmPoliza, String nmSituac, String cdPerson,
			int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_estado_i", estado);
		map.put("pv_nmpoliza_i", nmPoliza);
		map.put("pv_nmsituac_i", nmSituac);
		map.put("pv_cdperson_i", cdPerson);
		
		String endpointName = "OBTIENE_FUNCION";
		
		return pagedBackBoneInvoke(map, endpointName, start, limit);		
	}
	
	/**
	 * Metodo que obtiene datos variables en funcion de la poliza para 
	 * la pantalla Consulta de Póliza Renovada.
	 * Usa el Store Procedure PKG_SATELITES.P_OBTIENE_TVALOPER 
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param cdRol
	 * @param cdPerson
	 * @param cdAtribu
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList 
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerDatosVblesDeFuncioEnPoliza(String cdUniEco,
			String cdRamo, String estado, String nmPoliza, String nmSituac,
			String cdRol, String cdPerson, String cdAtribu, int start, int limit)
			throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco", cdUniEco);
		map.put("pv_cdramo", cdRamo);
		map.put("pv_estado", estado);
		map.put("pv_nmpoliza", nmPoliza);
		map.put("pv_nmsituac", nmSituac);
		map.put("pv_cdrol", cdRol);
		map.put("pv_cdperson", cdPerson);
		map.put("pv_cdatribu", cdAtribu);
		
		String endpointName = "OBTIENE_TVALOPER";
		
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	/**
	 * Metodo que realiza la eliminacion de un inciso seleccionado en el grid del bloque objeto asegurable de  
	 * la pantalla Consulta de Póliza Renovada.
	 * Usa el Store Procedure PKG_RENOVA.P_ELIMINA_INCISO.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param nmSuplem
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */
	@SuppressWarnings("unchecked")
	public String eliminarIncisoObjetoAsegurable(String cdUniEco,
			String cdRamo, String estado, String nmPoliza, String nmSituac,
			String nmSuplem) throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_estado_i", estado);
		map.put("pv_nmpoliza_i", nmPoliza);
		map.put("pv_nmsituac_i", nmSuplem);
		map.put("pv_nmsuplem_i", nmSituac);
		
		String endpointName = "ELIMINA_INCISO";
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}
	
	/**
	 * Metodo que realiza la eliminacion de una funcion seleccionado en el grid del bloque funcion en la poliza de  
	 * la pantalla Consulta de Póliza Renovada.
	 * Usa el Store Procedure PKG_SATELITES. P_MOV_MPOLIPER.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param nmSuplem
	 * @param cdRol
	 * @param cdPerson
	 * @param cdAtribu
	 * @param status
	 * @param nmOrdDom
	 * @param swReclam
	 * @param accion
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */
	@SuppressWarnings("unchecked")
	public String eliminarFuncionObjetoAsegurable(String cdUniEco,
			String cdRamo, String estado, String nmPoliza, String nmSituac,
			String nmSuplem, String cdRol, String cdPerson, String cdAtribu,
			String status, String nmOrdDom, String swReclam, String accion)
			throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i",cdUniEco);
		map.put("pv_cdramo_i",cdRamo);
		map.put("pv_estado_i",estado);
		map.put("pv_nmpoliza_i",nmPoliza);
		map.put("pv_nmsituac_i",nmSituac);
		map.put("pv_cdrol_i",cdRamo);
		map.put("pv_cdperson_i",cdPerson);
		map.put("pv_nmsuplem_i",nmSuplem);
		map.put("pv_status_i",status);
		map.put("pv_nmorddom_i",nmOrdDom);
		map.put("pv_swreclam_i",swReclam);
		map.put("pv_accion_i",accion);
                
        String endpointName = "MOV_MPOLIPER";
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}
	
	/**
	 * Metodo que guarda los datos variables de la funcion en
	 * la póliza para la pantalla Consulta de Póliza Renovada.
	 * Usa el Store Procedure PKG_RENOVA.P_GUARDA_DATOS_VAR_ROL.
	 * 
	 * @param DatosVarRolVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	@SuppressWarnings("unchecked")
	public String guardarDatosVblesDeFuncionEnPoliza(DatosVarRolVO datosVarRolVO)
			throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco", datosVarRolVO.getPv_cdunieco());
		map.put("pv_cdramo", datosVarRolVO.getPv_cdramo());
		map.put("pv_estado", datosVarRolVO.getPv_estado());
		map.put("pv_nmpoliza", datosVarRolVO.getPv_nmpoliza());
		map.put("pv_cdrol", datosVarRolVO.getPv_nmsuplem());
		map.put("pv_cdperson", datosVarRolVO.getPv_cdperson());
		map.put("pv_cdtipsit", datosVarRolVO.getPv_cdtipsit());
		map.put("pv_nmsuplem", datosVarRolVO.getPv_nmsuplem());
		map.put("pv_status", datosVarRolVO.getPv_status());
		map.put("pv_otvalor01", datosVarRolVO.getPv_otvalor01());
		map.put("pv_otvalor02", datosVarRolVO.getPv_otvalor02());
		map.put("pv_otvalor03", datosVarRolVO.getPv_otvalor03());
		map.put("pv_otvalor04", datosVarRolVO.getPv_otvalor04());
		map.put("pv_otvalor05", datosVarRolVO.getPv_otvalor05());
		map.put("pv_otvalor06", datosVarRolVO.getPv_otvalor06());
		map.put("pv_otvalor07", datosVarRolVO.getPv_otvalor07());
		map.put("pv_otvalor08", datosVarRolVO.getPv_otvalor08());
		map.put("pv_otvalor09", datosVarRolVO.getPv_otvalor09());
		map.put("pv_otvalor10", datosVarRolVO.getPv_otvalor10());
		map.put("pv_otvalor11", datosVarRolVO.getPv_otvalor11());
		map.put("pv_otvalor12", datosVarRolVO.getPv_otvalor12());
		map.put("pv_otvalor13", datosVarRolVO.getPv_otvalor13());
		map.put("pv_otvalor14", datosVarRolVO.getPv_otvalor14());
		map.put("pv_otvalor15", datosVarRolVO.getPv_otvalor15());
		map.put("pv_otvalor16", datosVarRolVO.getPv_otvalor16());
		map.put("pv_otvalor17", datosVarRolVO.getPv_otvalor17());
		map.put("pv_otvalor18", datosVarRolVO.getPv_otvalor18());
		map.put("pv_otvalor19", datosVarRolVO.getPv_otvalor19());
		map.put("pv_otvalor20", datosVarRolVO.getPv_otvalor20());
		map.put("pv_otvalor21", datosVarRolVO.getPv_otvalor21());
		map.put("pv_otvalor22", datosVarRolVO.getPv_otvalor22());
		map.put("pv_otvalor23", datosVarRolVO.getPv_otvalor23());
		map.put("pv_otvalor24", datosVarRolVO.getPv_otvalor24());
		map.put("pv_otvalor25", datosVarRolVO.getPv_otvalor25());
		map.put("pv_otvalor26", datosVarRolVO.getPv_otvalor26());
		map.put("pv_otvalor27", datosVarRolVO.getPv_otvalor27());
		map.put("pv_otvalor28", datosVarRolVO.getPv_otvalor28());
		map.put("pv_otvalor29", datosVarRolVO.getPv_otvalor29());
		map.put("pv_otvalor30", datosVarRolVO.getPv_otvalor30());
		map.put("pv_otvalor31", datosVarRolVO.getPv_otvalor31());
		map.put("pv_otvalor32", datosVarRolVO.getPv_otvalor32());
		map.put("pv_otvalor33", datosVarRolVO.getPv_otvalor33());
		map.put("pv_otvalor34", datosVarRolVO.getPv_otvalor34());
		map.put("pv_otvalor35", datosVarRolVO.getPv_otvalor35());
		map.put("pv_otvalor36", datosVarRolVO.getPv_otvalor36());
		map.put("pv_otvalor37", datosVarRolVO.getPv_otvalor37());
		map.put("pv_otvalor38", datosVarRolVO.getPv_otvalor38());
		map.put("pv_otvalor39", datosVarRolVO.getPv_otvalor39());
		map.put("pv_otvalor40", datosVarRolVO.getPv_otvalor40());
		map.put("pv_otvalor41", datosVarRolVO.getPv_otvalor41());
		map.put("pv_otvalor42", datosVarRolVO.getPv_otvalor42());
		map.put("pv_otvalor43", datosVarRolVO.getPv_otvalor43());
		map.put("pv_otvalor44", datosVarRolVO.getPv_otvalor44());
		map.put("pv_otvalor45", datosVarRolVO.getPv_otvalor45());
		map.put("pv_otvalor46", datosVarRolVO.getPv_otvalor46());
		map.put("pv_otvalor47", datosVarRolVO.getPv_otvalor47());
		map.put("pv_otvalor48", datosVarRolVO.getPv_otvalor48());
		map.put("pv_otvalor49", datosVarRolVO.getPv_otvalor49());
		map.put("pv_otvalor50", datosVarRolVO.getPv_otvalor50());
		
		String endpointName = "GUARDA_DATOS_VAR_ROL";
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}
	
	/**
	 * Metodo que obtiene los datos variables del objetos para 
	 * la pantalla Consulta de Póliza Renovada - Datos Variables del objeto Asegurable.
	 * Usa el Store Procedure PKG_RENOVA.P_OBTIENE_DATOS_OBJETO.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerDatosDelObjeto(String cdUniEco, String cdRamo,
			String estado, String nmPoliza, String nmSituac, int start,
			int limit) throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_estado_i", estado);
		map.put("pv_nmpoliza_i", nmPoliza);
		map.put("pv_nmsituac_i", nmSituac);
		
		String endpointName = "OBTIENE_DATOS_OBJETO";
		
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	/**
	 * Metodo que guarda los incisos de los datos variables de la 
	 * pantalla Datos Objetos Asegurables.
	 * Usa el Store Procedure PKG_SATELITES. P_MOV_TVALOSIT.
	 * 
	 * @param datosVblesObjAsegurablesVO
	 * 
	 * @return String 
	 */
	@SuppressWarnings("unchecked")
	public String guardarDatosVblesObjetoAsegurable(
			DatosVblesObjAsegurablesVO datosVblesObjAsegurablesVO)
			throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco", datosVblesObjAsegurablesVO.getPv_cdunieco());
		map.put("pv_cdramo", datosVblesObjAsegurablesVO.getPv_cdramo());
		map.put("pv_estado", datosVblesObjAsegurablesVO.getPv_estado());
		map.put("pv_nmpoliza", datosVblesObjAsegurablesVO.getPv_nmpoliza());
		map.put("pv_nmsituac", datosVblesObjAsegurablesVO.getPv_nmsituac());
		map.put("pv_nmsuplem", datosVblesObjAsegurablesVO.getPv_nmpoliza());
		map.put("pv_status", datosVblesObjAsegurablesVO.getPv_cdtipsit());
		map.put("pv_cdtipsit", datosVblesObjAsegurablesVO.getPv_nmsuplem());		
		map.put("pv_otvalor01", datosVblesObjAsegurablesVO.getPv_otvalor01());
		map.put("pv_otvalor02", datosVblesObjAsegurablesVO.getPv_otvalor02());
		map.put("pv_otvalor03", datosVblesObjAsegurablesVO.getPv_otvalor03());
		map.put("pv_otvalor04", datosVblesObjAsegurablesVO.getPv_otvalor04());
		map.put("pv_otvalor05", datosVblesObjAsegurablesVO.getPv_otvalor05());
		map.put("pv_otvalor06", datosVblesObjAsegurablesVO.getPv_otvalor06());
		map.put("pv_otvalor07", datosVblesObjAsegurablesVO.getPv_otvalor07());
		map.put("pv_otvalor08", datosVblesObjAsegurablesVO.getPv_otvalor08());
		map.put("pv_otvalor09", datosVblesObjAsegurablesVO.getPv_otvalor09());
		map.put("pv_otvalor10", datosVblesObjAsegurablesVO.getPv_otvalor10());
		map.put("pv_otvalor11", datosVblesObjAsegurablesVO.getPv_otvalor11());
		map.put("pv_otvalor12", datosVblesObjAsegurablesVO.getPv_otvalor12());
		map.put("pv_otvalor13", datosVblesObjAsegurablesVO.getPv_otvalor13());
		map.put("pv_otvalor14", datosVblesObjAsegurablesVO.getPv_otvalor14());
		map.put("pv_otvalor15", datosVblesObjAsegurablesVO.getPv_otvalor15());
		map.put("pv_otvalor16", datosVblesObjAsegurablesVO.getPv_otvalor16());
		map.put("pv_otvalor17", datosVblesObjAsegurablesVO.getPv_otvalor17());
		map.put("pv_otvalor18", datosVblesObjAsegurablesVO.getPv_otvalor18());
		map.put("pv_otvalor19", datosVblesObjAsegurablesVO.getPv_otvalor19());
		map.put("pv_otvalor20", datosVblesObjAsegurablesVO.getPv_otvalor20());
		map.put("pv_otvalor21", datosVblesObjAsegurablesVO.getPv_otvalor21());
		map.put("pv_otvalor22", datosVblesObjAsegurablesVO.getPv_otvalor22());
		map.put("pv_otvalor23", datosVblesObjAsegurablesVO.getPv_otvalor23());
		map.put("pv_otvalor24", datosVblesObjAsegurablesVO.getPv_otvalor24());
		map.put("pv_otvalor25", datosVblesObjAsegurablesVO.getPv_otvalor25());
		map.put("pv_otvalor26", datosVblesObjAsegurablesVO.getPv_otvalor26());
		map.put("pv_otvalor27", datosVblesObjAsegurablesVO.getPv_otvalor27());
		map.put("pv_otvalor28", datosVblesObjAsegurablesVO.getPv_otvalor28());
		map.put("pv_otvalor29", datosVblesObjAsegurablesVO.getPv_otvalor29());
		map.put("pv_otvalor30", datosVblesObjAsegurablesVO.getPv_otvalor30());
		map.put("pv_otvalor31", datosVblesObjAsegurablesVO.getPv_otvalor31());
		map.put("pv_otvalor32", datosVblesObjAsegurablesVO.getPv_otvalor32());
		map.put("pv_otvalor33", datosVblesObjAsegurablesVO.getPv_otvalor33());
		map.put("pv_otvalor34", datosVblesObjAsegurablesVO.getPv_otvalor34());
		map.put("pv_otvalor35", datosVblesObjAsegurablesVO.getPv_otvalor35());
		map.put("pv_otvalor36", datosVblesObjAsegurablesVO.getPv_otvalor36());
		map.put("pv_otvalor37", datosVblesObjAsegurablesVO.getPv_otvalor37());
		map.put("pv_otvalor38", datosVblesObjAsegurablesVO.getPv_otvalor38());
		map.put("pv_otvalor39", datosVblesObjAsegurablesVO.getPv_otvalor39());
		map.put("pv_otvalor40", datosVblesObjAsegurablesVO.getPv_otvalor40());
		map.put("pv_otvalor41", datosVblesObjAsegurablesVO.getPv_otvalor41());
		map.put("pv_otvalor42", datosVblesObjAsegurablesVO.getPv_otvalor42());
		map.put("pv_otvalor43", datosVblesObjAsegurablesVO.getPv_otvalor43());
		map.put("pv_otvalor44", datosVblesObjAsegurablesVO.getPv_otvalor44());
		map.put("pv_otvalor45", datosVblesObjAsegurablesVO.getPv_otvalor45());
		map.put("pv_otvalor46", datosVblesObjAsegurablesVO.getPv_otvalor46());
		map.put("pv_otvalor47", datosVblesObjAsegurablesVO.getPv_otvalor47());
		map.put("pv_otvalor48", datosVblesObjAsegurablesVO.getPv_otvalor48());
		map.put("pv_otvalor49", datosVblesObjAsegurablesVO.getPv_otvalor49());
		map.put("pv_otvalor50", datosVblesObjAsegurablesVO.getPv_otvalor50());
		
		String endpointName = "MOV_TVALOSIT";
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}

	/**Metodo que obtiene las coberturas de la poliza anterior de  
	 * la pantalla Consulta de Póliza Renovada - Coberturas de Polizas a Renovar.
	 * Usa el Store Procedure PKG_RENOVA.P_OBTIENE_COBERTURA_ANT.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param nmpPoliza
	 * @param nmSituac
	 * 
	 * @return objeto PagedList
	 * 
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtieneCoberturaPolizaAnterior(String cdUniEco,
			String cdRamo, String nmPoliza, String nmSituac, int start, int limit)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_nmpoliza_i", nmPoliza);
		map.put("pv_nmsituac_i", nmSituac);
		
		String endpointName = "OBTIENE_COBERTURA_ANT";
		
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	/**Metodo que obtiene las coberturas de la poliza renovada de  
	 * la pantalla Consulta de Póliza Renovada - Coberturas de Polizas a Renovar.
	 * Usa el Store Procedure PKG_RENOVA.P_OBTIENE_COBERTURA_RENOVA.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param nmpPoliza
	 * @param nmSituac
	 * 
	 * @return objeto PagedList
	 * 
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtieneCoberturaPolizaRenovada(String cdUniEco,
			String cdRamo, String nmPoliza, String nmSituac, int start,
			int limit) throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_nmpoliza_i", nmPoliza);
		map.put("pv_nmsituac_i", nmSituac);
		
		String endpointName = "OBTIENE_COBERTURA_RENOVADA";
		
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	/**
	 * Metodo que realiza la eliminacion de una cobertura seleccionado en el grid del inciso en cuestion de  
	 * la pantalla Consulta de Póliza Renovada - Coberturas de Polizas a Renovar.
	 * Usa el Store Procedure PKG_RENOVA.P_BORRA_INCISO_COBERTURA.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param nmPoliza
	 * @param nmSituac
	 * @param cdGarant
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */
	@SuppressWarnings("unchecked")
	public String eliminarIncisoCobertura(String cdUniEco, String cdRamo,
			String nmPoliza, String nmSituac, String cdGarant)
			throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_nmpoliza_i", nmPoliza);
		map.put("pv_nmsituac_i", nmSituac);
		map.put("pv_cdgarant_i", cdGarant);
		
		String endpointName = "BORRA_INCISO_COBERTURA";
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}
	
	/**
	 * Metodo que guarda las coberturas agregadas de la pantalla Agregar Cobertura de 
	 * Consulta de Poliza Renovada.
	 * Usa el Store Procedure PKG_SATELITES. P_MOV_MPOLIGAR.
	 * 
	 * @param nuevaCoberturaVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	@SuppressWarnings("unchecked")
	public String guardarDatosCobertura(NuevaCoberturaVO nuevaCoberturaVO)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdunieco_i",nuevaCoberturaVO.getCdunieco());
        map.put("pv_cdramo_i  ",nuevaCoberturaVO.getCdramo());
        map.put("pv_estado_i  ",nuevaCoberturaVO.getEstado());
        map.put("pv_nmpoliza_i",nuevaCoberturaVO.getNmpoliza());
        map.put("pv_nmsituac_i",nuevaCoberturaVO.getNmsituac());
        map.put("pv_cdgarant_i",nuevaCoberturaVO.getCdgarant());
        map.put("pv_nmsuplem_i",nuevaCoberturaVO.getNmsuplem());
        map.put("pv_cdcapita_i",nuevaCoberturaVO.getCdcapita());
        map.put("pv_status_i  ",nuevaCoberturaVO.getStatus());
        map.put("pv_cdtipbca_i",nuevaCoberturaVO.getCdtipbca());
        map.put("pv_ptvalbas_i",nuevaCoberturaVO.getPtvalbas());
        map.put("pv_pttasa_i  ",nuevaCoberturaVO.getPttasa());
        map.put("pv_cdtipfra_i",nuevaCoberturaVO.getCdtipfra());
        map.put("pv_swmanual_i",nuevaCoberturaVO.getSwmanual());
        map.put("pv_cdplan_i  ",nuevaCoberturaVO.getCdplan());
        map.put("pv_nmcumulo_i",nuevaCoberturaVO.getNmcumulo());
        map.put("pv_cdtipcum_i",nuevaCoberturaVO.getCdtipcum());
        map.put("pv_nmcesion_i",nuevaCoberturaVO.getNmcesion());
        map.put("pv_cdmoneda_i",nuevaCoberturaVO.getCdmoneda());
        map.put("pv_swexcum_i ",nuevaCoberturaVO.getSwexcum());
        map.put("pv_ptimpexc_i",nuevaCoberturaVO.getPtimpexc());
        map.put("pv_ptimpfac_i",nuevaCoberturaVO.getPtimpfac());
        map.put("pv_ptfacefe_i",nuevaCoberturaVO.getPtfacefe());
        map.put("pv_imporrea_i",nuevaCoberturaVO.getImporrea());
        map.put("pv_swincapa_i",nuevaCoberturaVO.getSwincapa());
        map.put("pv_cdgrupo_i ",nuevaCoberturaVO.getCdgrupo());
        map.put("pv_swreas_i  ",nuevaCoberturaVO.getSwreas_());
        map.put("pv_cdagrupa_i",nuevaCoberturaVO.getCdagrupa());
        map.put("pv_fevencim_i",nuevaCoberturaVO.getFevencim());
        map.put("pv_accion    ",nuevaCoberturaVO.getAccion());
		
        String endpointName = "MOV_MPOLIGAR";
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}
	
	/**Metodo que obtiene los datos variables de la cobertura para  
	 * la pantalla Consulta de Póliza Renovada - Datos Cobertura.
	 * Usa el Store Procedure PKG_SATELITES. P_OBTIENE_TVALOGAR.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmpPoliza
	 * @param nmSituac
	 * @param cdGarant
	 * @param cdAtribu
	 * @param status
	 * 
	 * @return objeto PagedList
	 * 
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtieneDatosVblesCobertura(String cdUniEco, String cdRamo,
			String estado, String nmPoliza, String nmSituac, String cdGarant,
			String cdAtribu, String status, int start, int limit)
			throws ApplicationException {
        
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i",cdUniEco);
        map.put("pv_cdramo_i",cdRamo);
        map.put("pv_estado_i",estado);
        map.put("pv_nmpoliza_i",nmPoliza);
        map.put("pv_nmsituac_i",nmSituac);
        map.put("pv_cdgarant_i",cdGarant);
        map.put("pv_cdatribu_i",cdAtribu);
        map.put("pv_status_i",status);
		
        String endpointName = "OBTIENE_TVALOGAR";
		
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	/**
	 * Metodo que guarda los datos de la cobertura de la 
	 * pantalla Datos Coberturas de Consulta de Poliza Renovada.
	 * Usa el Store Procedure PKG_SATELITES.P_MOV_TVALOGAR.
	 * 
	 * @param datosVblesObjAsegurablesVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	@SuppressWarnings("unchecked")
	public String guardarDatosDeCobertura(
			DatosVblesObjAsegurablesVO datosVblesObjAsegurablesVO)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco", datosVblesObjAsegurablesVO.getPv_cdunieco());
		map.put("pv_cdramo", datosVblesObjAsegurablesVO.getPv_cdramo());
		map.put("pv_estado", datosVblesObjAsegurablesVO.getPv_estado());
		map.put("pv_nmpoliza", datosVblesObjAsegurablesVO.getPv_nmpoliza());
		map.put("pv_nmsituac", datosVblesObjAsegurablesVO.getPv_nmsituac());
		map.put("pv_cdgarant", datosVblesObjAsegurablesVO.getPv_cdgarant());
		map.put("pv_nmsuplem", datosVblesObjAsegurablesVO.getPv_nmpoliza());
		map.put("pv_status", datosVblesObjAsegurablesVO.getPv_cdtipsit());
		map.put("pv_cdtipsit", datosVblesObjAsegurablesVO.getPv_nmsuplem());		
		map.put("pv_otvalor01", datosVblesObjAsegurablesVO.getPv_otvalor01());
		map.put("pv_otvalor02", datosVblesObjAsegurablesVO.getPv_otvalor02());
		map.put("pv_otvalor03", datosVblesObjAsegurablesVO.getPv_otvalor03());
		map.put("pv_otvalor04", datosVblesObjAsegurablesVO.getPv_otvalor04());
		map.put("pv_otvalor05", datosVblesObjAsegurablesVO.getPv_otvalor05());
		map.put("pv_otvalor06", datosVblesObjAsegurablesVO.getPv_otvalor06());
		map.put("pv_otvalor07", datosVblesObjAsegurablesVO.getPv_otvalor07());
		map.put("pv_otvalor08", datosVblesObjAsegurablesVO.getPv_otvalor08());
		map.put("pv_otvalor09", datosVblesObjAsegurablesVO.getPv_otvalor09());
		map.put("pv_otvalor10", datosVblesObjAsegurablesVO.getPv_otvalor10());
		map.put("pv_otvalor11", datosVblesObjAsegurablesVO.getPv_otvalor11());
		map.put("pv_otvalor12", datosVblesObjAsegurablesVO.getPv_otvalor12());
		map.put("pv_otvalor13", datosVblesObjAsegurablesVO.getPv_otvalor13());
		map.put("pv_otvalor14", datosVblesObjAsegurablesVO.getPv_otvalor14());
		map.put("pv_otvalor15", datosVblesObjAsegurablesVO.getPv_otvalor15());
		map.put("pv_otvalor16", datosVblesObjAsegurablesVO.getPv_otvalor16());
		map.put("pv_otvalor17", datosVblesObjAsegurablesVO.getPv_otvalor17());
		map.put("pv_otvalor18", datosVblesObjAsegurablesVO.getPv_otvalor18());
		map.put("pv_otvalor19", datosVblesObjAsegurablesVO.getPv_otvalor19());
		map.put("pv_otvalor20", datosVblesObjAsegurablesVO.getPv_otvalor20());
		map.put("pv_otvalor21", datosVblesObjAsegurablesVO.getPv_otvalor21());
		map.put("pv_otvalor22", datosVblesObjAsegurablesVO.getPv_otvalor22());
		map.put("pv_otvalor23", datosVblesObjAsegurablesVO.getPv_otvalor23());
		map.put("pv_otvalor24", datosVblesObjAsegurablesVO.getPv_otvalor24());
		map.put("pv_otvalor25", datosVblesObjAsegurablesVO.getPv_otvalor25());
		map.put("pv_otvalor26", datosVblesObjAsegurablesVO.getPv_otvalor26());
		map.put("pv_otvalor27", datosVblesObjAsegurablesVO.getPv_otvalor27());
		map.put("pv_otvalor28", datosVblesObjAsegurablesVO.getPv_otvalor28());
		map.put("pv_otvalor29", datosVblesObjAsegurablesVO.getPv_otvalor29());
		map.put("pv_otvalor30", datosVblesObjAsegurablesVO.getPv_otvalor30());
		map.put("pv_otvalor31", datosVblesObjAsegurablesVO.getPv_otvalor31());
		map.put("pv_otvalor32", datosVblesObjAsegurablesVO.getPv_otvalor32());
		map.put("pv_otvalor33", datosVblesObjAsegurablesVO.getPv_otvalor33());
		map.put("pv_otvalor34", datosVblesObjAsegurablesVO.getPv_otvalor34());
		map.put("pv_otvalor35", datosVblesObjAsegurablesVO.getPv_otvalor35());
		map.put("pv_otvalor36", datosVblesObjAsegurablesVO.getPv_otvalor36());
		map.put("pv_otvalor37", datosVblesObjAsegurablesVO.getPv_otvalor37());
		map.put("pv_otvalor38", datosVblesObjAsegurablesVO.getPv_otvalor38());
		map.put("pv_otvalor39", datosVblesObjAsegurablesVO.getPv_otvalor39());
		map.put("pv_otvalor40", datosVblesObjAsegurablesVO.getPv_otvalor40());
		map.put("pv_otvalor41", datosVblesObjAsegurablesVO.getPv_otvalor41());
		map.put("pv_otvalor42", datosVblesObjAsegurablesVO.getPv_otvalor42());
		map.put("pv_otvalor43", datosVblesObjAsegurablesVO.getPv_otvalor43());
		map.put("pv_otvalor44", datosVblesObjAsegurablesVO.getPv_otvalor44());
		map.put("pv_otvalor45", datosVblesObjAsegurablesVO.getPv_otvalor45());
		map.put("pv_otvalor46", datosVblesObjAsegurablesVO.getPv_otvalor46());
		map.put("pv_otvalor47", datosVblesObjAsegurablesVO.getPv_otvalor47());
		map.put("pv_otvalor48", datosVblesObjAsegurablesVO.getPv_otvalor48());
		map.put("pv_otvalor49", datosVblesObjAsegurablesVO.getPv_otvalor49());
		map.put("pv_otvalor50", datosVblesObjAsegurablesVO.getPv_otvalor50());
		
		String endpointName = "MOV_TVALOGAR";
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}
	
	/**
	 * Metodo que permite eliminar un accesorio seleccionado.
	 * Usa el Store Procedure PKG_SATELITES. P_MOV_MPOLIOBJ.
	 * 
	 *@param cdUniEco
     *@param cdRamo
     *@param estado
     *@param nmPoliza
     *@param nmSituac
     *@param cdTipObj
     *@param nmSuplem
     *@param status
     *@param nmObjeto
     *@param dsObjeto
     *@param ptObjeto
     *@param cdAgrupa
     *@param accion
     *
     *@return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String eliminarAccesorioEquipoEspecial(String cdUniEco,
			String cdRamo, String estado, String nmPoliza, String nmSituac,
			String cdTipObj, String nmSuplem, String status, String nmObjeto,
			String dsObjeto, String ptObjeto, String cdAgrupa, String accion)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i",cdUniEco);
		map.put("pv_cdramo_i",cdRamo);
		map.put("pv_estado_i",estado);
		map.put("pv_nmpoliza_i",nmPoliza);
		map.put("pv_nmsituac_i",nmSituac);
		map.put("pv_cdtipobj_i",cdTipObj);
		map.put("pv_nmsuplem_i",nmSuplem);
		map.put("pv_status_i",status);
		map.put("pv_nmobjeto_i",nmObjeto);
		map.put("pv_dsobjeto_i",dsObjeto);
		map.put("pv_ptobjeto_i",ptObjeto);
		map.put("pv_cdagrupa_i",cdAgrupa);
		map.put("pv_accion_i",accion);
         		
		String endpointName = "MOV_MPOLIOBJ";
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}
	
	/**
	 * Metodo que permite actualizar o insertar un nuevo accesorio.
	 * Usa el Store Procedure PKG_SATELITES. P_MOV_MPOLIOBJ.
	 * 
	 *@param cdUniEco
     *@param cdRamo
     *@param estado
     *@param nmPoliza
     *@param nmSituac
     *@param cdTipObj
     *@param nmSuplem
     *@param status
     *@param nmObjeto
     *@param dsObjeto
     *@param ptObjeto
     *@param cdAgrupa
     *@param accion
     *
     *@return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String incluirAccesorio(String cdUniEco, String cdRamo,
			String estado, String nmPoliza, String nmSituac, String cdTipObj,
			String nmSuplem, String status, String nmObjeto, String dsObjeto,
			String ptObjeto, String cdAgrupa, String accion)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i",cdUniEco);
		map.put("pv_cdramo_i",cdRamo);
		map.put("pv_estado_i",estado);
		map.put("pv_nmpoliza_i",nmPoliza);
		map.put("pv_nmsituac_i",nmSituac);
		map.put("pv_cdtipobj_i",cdTipObj);
		map.put("pv_nmsuplem_i",nmSuplem);
		map.put("pv_status_i",status);
		map.put("pv_nmobjeto_i",nmObjeto);
		map.put("pv_dsobjeto_i",dsObjeto);
		map.put("pv_ptobjeto_i",ptObjeto);
		map.put("pv_cdagrupa_i",cdAgrupa);
		map.put("pv_accion_i",accion);
		
		String endpointName = "MOV_MPOLIOBJ";
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}
	
	/**
	 * Metodo que obtiene un detalle segun la seleccion de un accesorio.
	 * Usa el Store Procedure PKG_SATELITES.P_OBTIENE_TVALOOBJ.
	 * 
	 *@param cdUniEco
     *@param cdRamo
     *@param estado
     *@param nmPoliza
     *@param nmSituac
     *@param nmObjeto
     *@param cdatribu
     *
     *@return PagedList.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtieneDetalleEquipoEspecial(String cdUniEco,
			String cdRamo, String estado, String nmPoliza, String nmSituac,
			String nmObjeto, String cdatribu, int start, int limit) throws ApplicationException {
		
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco",cdUniEco);
		map.put("pv_cdramo",cdRamo);
		map.put("pv_estado",estado);
		map.put("pv_nmpoliza",nmPoliza);
		map.put("pv_nmsituac",nmSituac);
		map.put("pv_nmobjeto",nmObjeto);
		map.put("pv_cdatribu",cdatribu);
        
		String endpointName = "OBTIENE_TVALOOBJ";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	/**
	 * Metodo que obtiene la lista de equipo especial para la pantalla Accesorios de Consulta de 
	 * Poliza Renovada.
	 * Usa el Store Procedure PKG_SATELITES. P_OBTIENE_MPOLIOBJ.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param nmObjeto
	 *  
	 * @return PagedList 
    */
	@SuppressWarnings("unchecked")
	public PagedList obtieneEquipoEspecial(String cdUniEco, String cdRamo,
			String estado, String nmPoliza, String nmSituac, String nmObjeto,
			int start, int limit) throws ApplicationException {
		
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco",cdUniEco);
		map.put("pv_cdramo",cdRamo);
		map.put("pv_estado",estado);
		map.put("pv_nmpoliza",nmPoliza);
		map.put("pv_nmsituac",nmSituac);
		map.put("pv_nmobjeto",nmObjeto); 
        
		
		String endpointName = "OBTIENE_MPOLIOBJ";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	/**Metodo que obtiene un conjunto de recibos para la pantalla Recibos de Consulta
	 * de Poliza Renovada.
	 * Usa el Store Procedure PKG_RENOVA.P_OBTIENE_RECIBOS
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param nmPoliza
	 * @param 
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtieneRecibos(String cdUniEco, String cdRamo,
			String nmPoliza, int start, int limit) throws ApplicationException {
		
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i",cdUniEco);
		map.put("pv_cdramo_i",cdRamo);
		map.put("pv_nmpoliza_i",nmPoliza);
		
		String endpointName = "OBTIENE_RECIBOS";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}	
	
	/**
	 * Metodo que realiza la guarda de una funcion en la poliza para la pantalla emergente 
	 * Agregar Funcion en la Poliza de Objetos Asegurables de Consulta de Póliza Renovada.
	 * Usa el Store Procedure PKG_SATELITES. P_MOV_MPOLIPER.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param nmSituac
	 * @param nmSuplem
	 * @param cdRol
	 * @param cdPerson
	 * @param cdAtribu
	 * @param status
	 * @param nmOrdDom
	 * @param swReclam
	 * @param accion
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */
	@SuppressWarnings("unchecked")
	public String guardaFuncionEnPoliza(String cdUniEco,
			String cdRamo, String estado, String nmPoliza, String nmSituac,
			String nmSuplem, String cdRol, String cdPerson, String cdAtribu,
			String status, String nmOrdDom, String swReclam, String accion)
			throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdunieco_i",cdUniEco);
		map.put("pv_cdramo_i",cdRamo);
		map.put("pv_estado_i",estado);
		map.put("pv_nmpoliza_i",nmPoliza);
		map.put("pv_nmsituac_i",nmSituac);
		map.put("pv_cdrol_i",cdRamo);
		map.put("pv_cdperson_i",cdPerson);
		map.put("pv_nmsuplem_i",nmSuplem);
		map.put("pv_status_i",status);
		map.put("pv_nmorddom_i",nmOrdDom);
		map.put("pv_swreclam_i",swReclam);
		map.put("pv_accion_i",accion);
                
        String endpointName = "MOV_MPOLIPER";
		WrapperResultados res = returnBackBoneInvoke(map, endpointName);
		
		return res.getMsgText();
	}
	
	/**
	 * Metodo que obtiene datos variables en funcion de la informacion seleccionada en el 
	 * combo Tipo de la pantalla Accesorios de Consulta de Póliza Renovada.
	 * Usa el Store Procedure PKG_WIZARD.P_OBTIENE_TIPO_OBJ_ATRIBUTOS.
	 * 
	 * @param cdTipObj
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList 
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerDatosVblesSegunTipoObjeto(String cdTipObj,
			int start, int limit) throws ApplicationException {
		
		HashMap map = new HashMap();
		
		map.put("pv_cdtipobj_i",cdTipObj);
		
		String endpointName = "OBTIENE_TIPO_OBJ_ATRIBUTOS";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
}