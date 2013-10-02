
package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.PeriodoGraciaClienteVO;
import mx.com.aon.portal.service.PeriodosGraciaClienteManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements PeriodosGraciaClienteManager
 * 
 * @extends AbstractManager
 */
public class PeriodosGraciaClienteManagerImpl extends AbstractManager implements PeriodosGraciaClienteManager {

	/**
	 *  Elimina una configuracion de periodo de gracias por cliente
	 *  Hace uso del Store Procedure PKG_CANCELA.P_BORRA_PERIODO_CLIENTE
	 * 
	 *  @param periodoGraciaClienteVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borrarPeriodoGraciaCliente(PeriodoGraciaClienteVO periodoGraciaClienteVO) throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdtramo_i", periodoGraciaClienteVO.getCdTramo());
		map.put("pv_cdelemento_i", periodoGraciaClienteVO.getCdElemento());
		map.put("pv_cdunieco_i", periodoGraciaClienteVO.getCveAseguradora());
		map.put("pv_cdramo_i", periodoGraciaClienteVO.getCveProducto());
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_PERIODO_GRACIA_CLIENTE");
        return res.getMsgText();
	}

	/**
	 *  Obtiene un conjunto de periodo de gracias por cliente
	 *  Hace uso del Store Procedure  PKG_CANCELA.P_PERIODO_GRACIA_CLIENTE
	 * 
	 *  @param dsElemen
	 *  @param dsRamo
	 *  @param dsUniEco
	 *    
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList buscarPeriodosGraciaCliente(String dsElemen, String dsRamo, String dsUniEco,int start, int limit) throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cliente_i", dsElemen);
		map.put("pv_producto_i", dsRamo);
		map.put("pv_aseguradora_i", dsUniEco);
		
		String endpointName = "OBTIENE_PERIODOS_GRACIA_CLIENTE";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
		
	}

	/**
	 *  Inserta o actualiza un periodo de gracias por cliente
	 *  Hace uso del Store Procedure PKG_CANCELA.P_GUARDA_PERIODO_GRACIA_CTE
	 * 
	 *  @param periodoGraciaClienteVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String insertarPeriodoGraciaCliente( PeriodoGraciaClienteVO periodoGraciaClienteVO) throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_tramo_i", periodoGraciaClienteVO.getCdTramo());
		map.put("pv_cliente_i", periodoGraciaClienteVO.getCdPerson());
		map.put("pv_elemento_i", periodoGraciaClienteVO.getCdElemento());
		map.put("pv_producto_i", periodoGraciaClienteVO.getCveProducto());
		map.put("pv_aseguradora_i", periodoGraciaClienteVO.getCveAseguradora());
		
        WrapperResultados res =  returnBackBoneInvoke(map,"INSERTAR_PERIODO_GRACIA_CLIENTE");
        return res.getMsgText();
	}
	
	/**
	 *  Obtiene un conjunto de periodo de gracias por cliente para la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure PKG_CANCELA.P_PERIODO_GRACIA_CLIENTE
	 *  
	 *  @param dsElemen
	 *  @param dsRamo
	 *  @param dsUniEco
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsElemen, String dsRamo, String dsUniEco)throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		List lista = null;
		HashMap map = new HashMap();
		map.put("pv_cliente_i", dsElemen);
		map.put("pv_producto_i", dsRamo);
		map.put("pv_aseguradora_i", dsUniEco);
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_PERIODOS_GRACIA_CLIENTE_EXPORT");
		model.setInformation(lista);
		model.setColumnName(new String[]{"Cliente","Aseguradora","Producto","Recibos","Dias de Gracia","Dias Antes Cancelacion"});
		return model;
		
	}
}
