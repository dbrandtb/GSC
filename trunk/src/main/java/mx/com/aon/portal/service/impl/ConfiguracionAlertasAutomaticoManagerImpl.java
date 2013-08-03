
package mx.com.aon.portal.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfiguracionAlertasAutomaticoVO;
import mx.com.aon.portal.service.ConfiguracionAlertasAutomaticoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.util.*;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements ConfiguracionAlertasAutomaticoManager
 * 
 * @extends AbstractManager
 */

public class ConfiguracionAlertasAutomaticoManagerImpl extends AbstractManager implements ConfiguracionAlertasAutomaticoManager {

	/**
	 * Tabla lógica desde la cual se leerán datos para obtener los valores de Variables para
	 * la pantalla de Agregar/Editar
	 */
	private static String TABLA_VARALERTA = "VARALERTA";

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(EstructuraManagerImpl.class);
	
	/**
	 * Metodo que obtiene un unico registro de configuracion de alertas automatico.
	 * Usa el Store Procedure PKG_ALERTAS.P_OBTIENE_ALERTA_REG.
	 * 
	 * @param cdIdUnico
	 * 
	 * @return objeto ConfiguracionAlertasAutomaticoVO
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public ConfiguracionAlertasAutomaticoVO getConfiguracionAlertasAutomatico(String cdIdUnico) throws ApplicationException
	{
			HashMap map = new HashMap();
			map.put("cdIdUnico",cdIdUnico);
			return (ConfiguracionAlertasAutomaticoVO)getBackBoneInvoke(map,"OBTIENE_CONF_ALERTA_AUTO_REG");
	}

	 /**
     * Metodo que realiza la insercion de una configuracion de alertas automatico.
     * Invoca el Store Procedure PKG_ALERTAS.P_GUARDA_CONFALERTA.
     * 
     * @param configuracionAlertasAutomaticoVO
     * 
     * @return Mensaje asociado en respuesta a la ejecucion del servicio.
     * 
     * @throws ApplicationException
     */
	@SuppressWarnings("unchecked")
	public String agregarConfiguracionAlertasAutomatico(ConfiguracionAlertasAutomaticoVO configuracionAlertasAutomaticoVO) throws ApplicationException{

			HashMap map = new HashMap();
			
			map.put("cdIdUnico",configuracionAlertasAutomaticoVO.getCdIdUnico());
			map.put("usuario",configuracionAlertasAutomaticoVO.getDsUsuario());
			map.put("cliente",configuracionAlertasAutomaticoVO.getCdCliente());
			map.put("nombreAlerta",configuracionAlertasAutomaticoVO.getDsAlerta());
			map.put("proceso",configuracionAlertasAutomaticoVO.getCdProceso());
			map.put("temporalidad",configuracionAlertasAutomaticoVO.getCdTemporalidad());
			map.put("mensaje",configuracionAlertasAutomaticoVO.getDsMensaje());
			map.put("frecuencia",configuracionAlertasAutomaticoVO.getNmFrecuencia());
			map.put("fecInic",configuracionAlertasAutomaticoVO.getFeInicio());
			map.put("diasAntic",configuracionAlertasAutomaticoVO.getNmDiasAnt());			
			map.put("duracion",configuracionAlertasAutomaticoVO.getNmDuracion());
			map.put("mandarEmail",configuracionAlertasAutomaticoVO.getFgMandaEmail());		
			map.put("popup",configuracionAlertasAutomaticoVO.getFgMandaPantalla());
			map.put("pantalla",configuracionAlertasAutomaticoVO.getFgPermPantalla());
			map.put("rol",configuracionAlertasAutomaticoVO.getCdRol());
			map.put("ramo",configuracionAlertasAutomaticoVO.getCdTipRam());
			map.put("aseguradora",configuracionAlertasAutomaticoVO.getCdUniEco());
			map.put("producto",configuracionAlertasAutomaticoVO.getCdProducto());
			map.put("region",configuracionAlertasAutomaticoVO.getDsRegion());
			
            WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_CONF_ALERTA_AUTO");
            return res.getMsgText();

	}
    
	/*
	/**
     * Metodo que realiza la actualizacion de una configuracion de alertas automatico editada.
     * Invoca el Store Procedure PKG_ALERTAS.P_GUARDA_CONFALERTA.
     * 
     * @param configuracionAlertasAutomaticoVO
     * 
     * @return Mensaje asociado en respuesta a la ejecucion del servicio.
     * 
     * @throws ApplicationException
     */
    //@SuppressWarnings("unchecked")
	/*public String guardarConfiguracionAlertasAutomatico(ConfiguracionAlertasAutomaticoVO configuracionAlertasAutomaticoVO) throws ApplicationException{

			HashMap map = new HashMap();
			map.put("cdIdUnico", configuracionAlertasAutomaticoVO.getCdIdUnico());
			map.put("nombreAlerta",configuracionAlertasAutomaticoVO.getDsAlerta());
			map.put("proceso",configuracionAlertasAutomaticoVO.getCdProceso());
			map.put("temporalidad",configuracionAlertasAutomaticoVO.getCdTemporalidad());
			map.put("mensaje",configuracionAlertasAutomaticoVO.getDsMensaje());
			map.put("frecuencia",configuracionAlertasAutomaticoVO.getNmFrecuencia());
			map.put("fecInic",ConvertUtil.convertToDate(configuracionAlertasAutomaticoVO.getFeInicio()));  //como es fecha hay que convertirla a DATE
			map.put("diasAntic",configuracionAlertasAutomaticoVO.getNmDiasAnt());			
			map.put("duracion",configuracionAlertasAutomaticoVO.getNmDuracion());
			map.put("mandarEmail",configuracionAlertasAutomaticoVO.getFgMandaEmail());
			map.put("popup",configuracionAlertasAutomaticoVO.getFgMandaPantalla());
			map.put("pantalla",configuracionAlertasAutomaticoVO.getFgPermPantalla());
			map.put("rol_i",configuracionAlertasAutomaticoVO.getCdRol());
			map.put("ramo",configuracionAlertasAutomaticoVO.getCdTipRam());
			map.put("aseguradora",configuracionAlertasAutomaticoVO.getCdUniEco());
			map.put("producto",configuracionAlertasAutomaticoVO.getCdProducto());
			map.put("region",configuracionAlertasAutomaticoVO.getDsRegion());
			map.put("usuario",configuracionAlertasAutomaticoVO.getDsUsuario());
			map.put("cliente",configuracionAlertasAutomaticoVO.getCdCliente());
			
            WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_CONF_ALERTA_AUTO");
            return res.getMsgText();

    }*/
    
    /**
     * Metodo que realiza la eliminacion de una configuracion de alertas automatico seleccionada.
     * Invoca el Store Procedure PKG_ALERTAS.P_ELIMINA_CONF_ALERTA.
     * 
     * @param configuracionAlertasAutomaticoVO
     * 
     * @return Mensaje asociado en respuesta a la ejecucion del servicio.
     * 
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
	public String borrarConfiguracionAlertasAutomatico(String cdIdUnico) throws ApplicationException{
			HashMap map = new HashMap();
			map.put("cdIdUnico",cdIdUnico);
			
            WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_CONF_ALERTA_AUTO");
            return res.getMsgText();
    }
    
    /**
     * Metodo que realiza la busqueda de un conjunto de registros de configuracion de alerta automatico.
     * Invoca el Store Procedure PKG_ALERTAS.P_CONSULTA_CONFALERTAS.
     * 
     * @param configuracionAlertasAutomaticoVO
     * 
     * @return PagedList.
     * 
     * @throws ApplicationException
     */
	@SuppressWarnings("unchecked")
	public PagedList buscarConfiguracionAlertasAutomatico(String dsUsuario, String dsCliente, String dsProceso, String dsRamo, String dsAseguradora, String dsRol, int start, int limit )throws ApplicationException
	{
			HashMap map = new HashMap();
	
			map.put("dsUsuario",dsUsuario);
			map.put("dsCliente",dsCliente);
			map.put("dsProceso",dsProceso);
			map.put("dsRamo",dsRamo);
			map.put("dsAseguradora",dsAseguradora);
			map.put("dsRol",dsRol);
			
			return pagedBackBoneInvoke(map, "OBTIENE_CONF_ALERTA_AUTO", start, limit);

	}
	
	
	/**
	 * Realiza una busqueda y obtiene un conjunto de configuracion de alerta automatico y
	 *  exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @return TableModelExport
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsUsuario,String dsCliente,String dsProceso,String dsRamo,String dsAseguradora,String dsRol) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("dsUsuario",dsUsuario);
		map.put("dsCliente",dsCliente);
		map.put("dsProceso",dsProceso);
		map.put("dsRamo",dsRamo);
		map.put("dsAseguradora",dsAseguradora);
		map.put("dsRol",dsRol);
		map.put("pv_cdestruc_i",null);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_CONF_ALERTA_AUTO_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Nivel","Nombre","Proceso","Usuario","Ramo","Aseguradora","Producto","Rol"});
		
		return model;
	}

	/**
	 * Obtiene las variables a utilizar en el campo de Mensaje de Agregar/Editar
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List obtieneVariables() throws ApplicationException {
		HashMap map = new HashMap();
		map.put("tabla", TABLA_VARALERTA);

		return getAllBackBoneInvoke(map, "OBTIENE_CONF_ALERTA_AUTO_VALORES_VARIABLES");
	}
}
