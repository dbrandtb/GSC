package mx.com.aon.portal.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfiguracionClienteVO;
import mx.com.aon.portal.service.ConfiguracionRenovacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que implementa la interfaz ConfiguracionRenovacionManager
 * 
 * @extends AbstractManager
 *
 */
public class ConfiguracionRenovacionManagerImpl extends AbstractManager implements ConfiguracionRenovacionManager {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConfiguracionRenovacionManagerImpl.class);
	
	/**
	 * Metodo que elimina un registro de datos de Configuracion de la Renovacion.
	 * Invoca al Store Procedure PKG_RENOVA.P_ELIMINA_CONFIGURACION.
	 * 
	 * @param cdRenova
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String eliminarConfiguracion(String cdRenova) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdrenova_i",cdRenova);
        WrapperResultados res = returnBackBoneInvoke(map, "ELIMINA_CONFIGURACION");
        return res.getMsgText();
	}
	
	/**
	 * Metodo que busca y obtiene un unico registro con los datos a usar en la 
	 * pantalla de Configuracion de la renovacion.
	 * Invoca al Store Procedure PKG_RENOVA.P_OBTIENE_CONFIGURA_CLIENTE.
	 * 
	 * @param cdRenova
	 * 
	 * @return ConfiguracionClienteVO
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public ConfiguracionClienteVO getConfiguracionCliente(String cdRenova)
			throws ApplicationException {		
		HashMap map = new HashMap();
		map.put("pv_cdrenova_i", cdRenova);
		
		String endpointName = "OBTIENE_CONFIGURA_CLIENTE";
		return (ConfiguracionClienteVO) this.getBackBoneInvoke(map, endpointName);
	}
	
	/**
	 * Metodo que obtiene un conjunto de registros de configuracion de la renovacion
	 *  y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 *  Invoca al Store Procedure PKG_RENOVA.P_OBTIENE_CLIENTE_TIPO.
	 * 
	 * @return TableModelExport
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsElemen, String dsTipoRenova,
			String dsUniEco, String dsRamo) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		
		map.put("pv_dselemen_i",dsElemen);
		map.put("pv_dstiporenova_i",dsTipoRenova);
        map.put("pv_dsunieco_i",dsUniEco);
        map.put("pv_dsramo_i",dsRamo);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_CLIENTE_TIPO_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Cliente","Aseguradora","Producto","Tipo","Dias"});
		
		return model;
	}
	
	/**
	 * Metodo que inserta un nuevo registro o actualiza un registro editado en pantalla.
	 * Invoca al Store Procedure PKG_RENOVA.P_GUARDA_CONFIGURA.
	 * 
	 * @param cdRenova
	 * @param cdPerson
	 * @param cdElemento
	 * @param cdUniEco
	 * @param cdRamo
	 * @param cdTipoRenova
	 * @param cdDiasAnticipacion
	 * @param continuaNum
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String guardaConfiguracion(String cdRenova, String cdPerson,
			String cdElemento, String cdUniEco, String cdRamo,
			String cdTipoRenova, String cdDiasAnticipacion, String continuaNum)
			throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdrenova_i",cdRenova);
		map.put("pv_cdpersona_i",cdPerson);
		map.put("pv_cdelemento_i",cdElemento);
		map.put("pv_cdunieco_i",cdUniEco);
		map.put("pv_cdramo_i",cdRamo);
		map.put("pv_cdtiporenova_i",cdTipoRenova);
		map.put("pv_cddiasanticipacion",cdDiasAnticipacion);
		map.put("pv_swconnum_i", continuaNum);
		
        WrapperResultados res = returnBackBoneInvoke(map, "GUARDA_CONFIGURA");
        return res.getMsgText();
	}
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros con los datos a usar en la 
	 * pantalla de Configuracion de la renovacion.
	 * Invoca al Store Procedure PKG_RENOVA.P_OBTIENE_CLIENTE_TIPO.
	 * 
	 * @param dsElemen
	 * @param dsTipoRenova
	 * @param dsUniEco
	 * @param dsRamo
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerClientesYTiposDeRenovacion(String dsElemen,
			String dsTipoRenova, String dsUniEco, String dsRamo, int start, int limit)
		throws ApplicationException {
		HashMap map = new HashMap();
	
		map.put("pv_dselemen_i",dsElemen);
		map.put("pv_dstiporenova_i",dsTipoRenova);
        map.put("pv_dsunieco_i",dsUniEco);
        map.put("pv_dsramo_i",dsRamo);
        
        String endpoint = "OBTIENE_CLIENTE_TIPO";
        return pagedBackBoneInvoke(map, endpoint, start, limit);
	}
	
	/**
	 * Metodo que valida la existencia de roles en acciones.
	 * Invoca al Store Procedure PKG_RENOVA.P_VALIDA_RENOVA_ROL.
	 * 
	 * @param cdRenova
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String getValidacionRoles(String cdRenova) throws ApplicationException
	{
		HashMap map = new HashMap();		
		map.put("pv_cdrenova_i",cdRenova);
		
        String endpointName = "VALIDA_RENOVA_ROL";
        WrapperResultados res = returnBackBoneInvoke(map, endpointName);
        return res.getResultado();
	}
}
