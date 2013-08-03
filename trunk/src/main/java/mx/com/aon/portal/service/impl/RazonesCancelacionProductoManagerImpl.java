package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.RazonesCancelacionProductoManager;
import mx.com.aon.portal.util.WrapperResultados;


/**
 * Implementacion de la interface de servicios para razones de cancelacion de productos.
 *
 */
public class RazonesCancelacionProductoManagerImpl extends AbstractManager implements RazonesCancelacionProductoManager{

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(EstructuraManagerImpl.class);
	

	/**
	 * Obtiene un conjunto de razones de cancelación de productos.
	 * Usa el store procedure PKG_CANCELA.P_OBTIENE_RAZONES_PRODUCTO.
	 *
	 * @param dsRamo 
	 * @param dsRazon
	 * @param dsMetodo 
	 * @param start
	 * @param limit
	 * 
	 * @return Objeto PagedList 
	 *			
	 */
	@SuppressWarnings("unchecked")
	public PagedList obtenerRazonesCancelacionProducto(String dsRamo,
			String dsRazon, String dsMetodo, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("dsramo",dsRamo);
		map.put("dsrazon",dsRazon);
		map.put("dsmetodo",dsMetodo);
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "OBTIENE_RAZONES_PRODUCTO", start, limit);
		
	}
	

	/**
	 *  Da de baja a informacion de razones de cancelación de productos.
	 *  Usa el store procedure PKG_CANCELA.P_BORRA_RAZON_PRODUCTO.
	 *  
	 *  @param cdRazon
	 *	@param cdRamo 
	 *	@param cdMetodo
	 *
	 *	@return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */
	@SuppressWarnings("unchecked")
	public String borrarRazonesCancelacionProducto(String cdRazon, String cdRamo, String cdMetodo)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdrazon",cdRazon);
		map.put("cdramo",cdRamo);
		map.put("cdmetodo",cdMetodo);
        WrapperResultados res = this.returnBackBoneInvoke(map, "BORRA_RAZON_PRODUCTO");
        return res.getMsgText();
	}


	/**
	 * Salva la informacion de razones de cancelación de producto.
	 * Usa el store procedureP_GUARDA_RAZON_PRODUCTO.
	 *  
	 *  @param cdRamo
	 *  @param cdRazon
	 *  @param cdMetodo
	 *  
	 *	@return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */
	@SuppressWarnings("unchecked")
	public String guardarConfiguracionRazonCancelacionProducto(String cdRamo,
			String cdRazon, String cdMetodo) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdramo",cdRamo);
		map.put("cdrazon",cdRazon);
		map.put("cdmetodo",cdMetodo);
        WrapperResultados res = returnBackBoneInvoke(map, "GUARDA_RAZON_PRODUCTO");
        return res.getMsgText();
	}

	
	/**
	 * Obtiene un conjunto de razones de cancelacion y los exporta en Formato PDF, Excel, CSV, etc.
	 * Usa el store procedure PKG_CANCELA PKG_CANCELA.P_OBTIENE_RAZONES_PRODUCTO.
	 *  
	 *  @param dsRamo
	 *  @param dsRazon
	 *  @param dsMetodo
	 *	
	 *	@return TableModelExport
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsRamo, String dsRazon,String dsMetodo) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("dsramo", dsRamo);
		map.put("dsrazon", dsRazon);
		map.put("dsmetodo", dsMetodo);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_RAZONES_CANCELACION_PRODUCTO_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Producto","Razon","Metodo"});
		
		return model;
	}
	
}
