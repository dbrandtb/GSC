package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.RehabilitacionMasivaManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Implementacion de interface de servicios para Rehabilitacion Masiva
 * 
 * @author Usuario
 *
 */
public class RehabilitacionMasivaManagerImpl extends AbstractManager implements
		RehabilitacionMasivaManager {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(RehabilitacionMasivaManagerImpl.class);

	/**
	 * Obtiene el conjunto de Polizadas Canceladas a Rehabilitar
	 * Usa el sp PKG_CANCELA.P_POLIZAS_CANC_A_REHABILITAR
	 * 
	 * @param dsAsegurado
	 * @param dsAseguradora
	 * @param dsProducto
	 * @param nmPoliza
	 * @param nmInciso
	 * 
	 * @return Objeto PagedList
	 * 
	 */
	@SuppressWarnings("unchecked")
	public PagedList buscarPolizasCanceladasARehabilitar(String dsAsegurado,
			String dsAseguradora, String dsProducto, String nmPoliza,
			String nmInciso, int start, int limit) throws ApplicationException {

		HashMap map = new HashMap();
		map.put("dsAsegurado", dsAsegurado);
		map.put("dsAseguradora", dsAseguradora);
		map.put("dsProducto", dsProducto);
		map.put("nmPoliza", nmPoliza);
		map.put("nmInciso", nmInciso);

		return pagedBackBoneInvoke(map, "OBTENER_POLIZADAS_CANCELADAS_PARA_REHABILITAR", start, limit);
	}

	
	/* SE PASO AL ESQUEMA DAO YA NO SE USA ESTE METODO, ESTA IMPLEMENTADO EN PolizasManager/
	/**
	 * Permite rehabilitar una poliza
	 * Usa el sp PKG_CANCELA.P_REHABILITA_POLIZA
	 * 
	 * @return Mensaje de exito/error
	 */
	@SuppressWarnings("unchecked")
	public String rehabilitarPoliza (String cdUniEco, String cdRamo, String estado, String nmPoliza, String feEfecto, String feVencim, String feCancel,
			String feReInst, String cdRazon, String cdPerson, String cdMoneda, String nmCancel, String comentarios, String nmSuplem) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("estado", estado);
		map.put("nmPoliza", nmPoliza);
		map.put("feEfecto", ConvertUtil.convertToDate(feEfecto));
		map.put("feVencim", ConvertUtil.convertToDate(feVencim));
		map.put("feCancel", ConvertUtil.convertToDate(feCancel));
		map.put("feReInst", ConvertUtil.convertToDate(feReInst));
		map.put("cdRazon", cdRazon);
		map.put("cdPerson", cdPerson);
		map.put("cdMoneda", cdMoneda);
		map.put("nmCancel", nmCancel);
		map.put("comentarios", comentarios);
		map.put("nmSuplem", nmSuplem);

		WrapperResultados res = returnBackBoneInvoke(map, "REHABILITACION_MASIVA_REHABILITAR_POLIZA");

		return res.getMsgText();
	}

	/**
	 * Exporta la grilla de resultados a un formato especifico
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsAsegurado, String dsAseguradora,
			String dsProducto, String nmPoliza, String nmInciso)
			throws ApplicationException {

		TableModelExport model = new TableModelExport();
		List lista = null;
		
		HashMap map = new HashMap();
		map.put("dsAsegurado", dsAsegurado);
		map.put("dsAseguradora", dsAseguradora);
		map.put("dsProducto", dsProducto);
		map.put("nmPoliza", nmPoliza);
		map.put("nmInciso", nmInciso);

		lista = (ArrayList) getExporterAllBackBoneInvoke(map, "POLIZAS_A_REHABILITAR_EXPORTAR");
		model.setInformation(lista);
		model.setColumnName(new String[] {"Asegurado", "Aseguradora", "Producto", "Poliza", "Inciso", "Fecha Cancel", "Razon", "Comentarios"});

		return model;
	}


}