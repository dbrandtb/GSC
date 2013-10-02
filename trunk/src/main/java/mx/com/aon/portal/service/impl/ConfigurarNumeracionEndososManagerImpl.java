package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfiguracionNumeracionEndososVO;
import mx.com.aon.portal.service.ConfigurarNumeracionEndososManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

public class ConfigurarNumeracionEndososManagerImpl extends AbstractManager implements ConfigurarNumeracionEndososManager{
	
	/**
	 * Metodo que elimina un registro de Numeracion de Endosos seleccionado.
	 * Invoca al Store Procedure PKG_ENDOSOS.P_BORRA_TNUMENEX.
	 * 
	 * @param configuracionNumeracionEndososVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String borrarNumeracionEndosos(
			ConfiguracionNumeracionEndososVO configuracionNumeracionEndososVO)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdelemento_i", configuracionNumeracionEndososVO.getCdElemento());
		map.put("pv_cdunieco_i", configuracionNumeracionEndososVO.getCdUniEco());
		map.put("pv_cdramo_i", configuracionNumeracionEndososVO.getCdRamo());
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_TNUMENEX");
        return res.getMsgText();
	}
	
	/**
	 * Obtiene un conjunto de ayudas de cobertura y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * Invoca al Store Procedure PKG_ENDOSOS.P_OBTIENE_TNUMENEX.
	 * 
	 * @param configuracionNumeracionEndososVO
	 * 
	 * @return TableModelExport
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(
			ConfiguracionNumeracionEndososVO configuracionNumeracionEndososVO)
			throws ApplicationException {

		TableModelExport model = new TableModelExport();		
		List lista = null;
		
		HashMap map = new HashMap();
		map.put("pv_dselemento_i", configuracionNumeracionEndososVO.getDsElemen());
		map.put("pv_dsunieco_i", configuracionNumeracionEndososVO.getDsUniEco());
		map.put("pv_dsramo_i", configuracionNumeracionEndososVO.getDsRamo());
		map.put("pv_dsplan_i", configuracionNumeracionEndososVO.getDsPlan());
		map.put("pv_nmpoliex_i", configuracionNumeracionEndososVO.getNmPoliEx());
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_TNUMENEX_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Cuenta/Cliente","Aseguradora","Producto","Plan", "Poliza"});
		
		return model;
	}
	
	/**
	 * Metodo que inserta un nuevo registro o actualiza un registro editado en pantalla de Numeracion de Endosos.
	 * Invoca al Store Procedure PKG_ENDOSOS.P_GUARDA_TNUMENEX.
	 * 
	 * @param configuracionNumeracionEndososVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String guardarOActualizarNumeracionEndosos(
			ConfiguracionNumeracionEndososVO configuracionNumeracionEndososVO)
			throws ApplicationException {

		HashMap map = new HashMap();		
		map.put("pv_cdelemento_i",configuracionNumeracionEndososVO.getCdElemento());
		map.put("pv_cdunieco_i",configuracionNumeracionEndososVO.getCdUniEco());
		map.put("pv_cdramo_i",configuracionNumeracionEndososVO.getCdRamo());
		map.put("pv_cdplan_i",configuracionNumeracionEndososVO.getCdPlan());
		map.put("pv_nmpoliex_i",configuracionNumeracionEndososVO.getNmPoliEx());
		map.put("pv_indcalc_i",configuracionNumeracionEndososVO.getIndCalc());
		map.put("pv_nminicial_i",configuracionNumeracionEndososVO.getNmInicial());
		map.put("pv_nmfinal_i",configuracionNumeracionEndososVO.getNmFinal());
		map.put("pv_nmactual_i",configuracionNumeracionEndososVO.getNmActual());
		map.put("pv_otexpres_i",configuracionNumeracionEndososVO.getOtExpres());
		map.put("pv_accion_i",configuracionNumeracionEndososVO.getAccion());
		
        WrapperResultados res = returnBackBoneInvoke(map, "GUARDA_TNUMENEX");
        return res.getMsgText();
	}
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros de Numeracion de Endosos
	 * Invoca al Store Procedure PKG_ENDOSOS.P_OBTIENE_TNUMENEX.
	 * 
	 * @param start
	 * @param limit
	 * @param configuracionNumeracionEndososVO
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerNumeracionEndosos(int start, int limit,
			ConfiguracionNumeracionEndososVO configuracionNumeracionEndososVO)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_dselemento_i", configuracionNumeracionEndososVO.getDsElemen());
		map.put("pv_dsunieco_i", configuracionNumeracionEndososVO.getDsUniEco());
		map.put("pv_dsramo_i", configuracionNumeracionEndososVO.getDsRamo());
		map.put("pv_dsplan_i", configuracionNumeracionEndososVO.getDsPlan());
		map.put("pv_nmpoliex_i", configuracionNumeracionEndososVO.getNmPoliEx());
		
		String endpointName = "OBTIENE_TNUMENEX";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	/**
     * Metodo que busca y obtiene un unico registro de Numeracion de Endosos
     * Invoca al Store Procedure PKG_ENDOSOS.P_OBTIENE_TNUMENEX_REG.
     * 
     * @param cdElemento
     * @param cdUniEco
     * @param cdRamo
     * 
     * @return ConfiguracionNumeracionEndososVO
     * 
     * @throws ApplicationException
     */
	@SuppressWarnings("unchecked")
	public ConfiguracionNumeracionEndososVO getNumeracionEndosos(String cdElemento,String cdUniEco,String cdRamo)
			throws ApplicationException{		
		HashMap map = new HashMap();
		map.put("pv_cdelemento_i", cdElemento);
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		
		String endpointName = "GET_TNUMENEX";
		return (ConfiguracionNumeracionEndososVO) this.getBackBoneInvoke(map, endpointName);
	}

}
