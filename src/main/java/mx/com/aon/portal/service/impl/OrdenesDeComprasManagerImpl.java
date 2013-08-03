
package mx.com.aon.portal.service.impl;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import mx.com.aon.core.ApplicationException;


import mx.com.aon.portal.model.OrdenDeCompraEncOrdenVO;
import mx.com.aon.portal.service.OrdenesDeComprasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.UserSQLDateConverter;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.util.ConvertUtil;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.Converter;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements OrdenesDeComprasManager
 * 
 * @extends AbstractManager
 */
public class OrdenesDeComprasManagerImpl extends AbstractManagerJdbcTemplateInvoke implements OrdenesDeComprasManager {

	/**
	 *  Obtiene una orden de compras en particular
	 *  Hace uso del Store Procedure PKG_COTIZA.P_ENC_ORDEN
	 * 
	 *  @param cdCarro
	 *  
	 *  @return Objeto OrdenDeCompraEncOrdenVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public OrdenDeCompraEncOrdenVO getObtenerOrdenesDetallePersonas(String cdCarro) throws ApplicationException
	{
			HashMap map = new HashMap();
			map.put("cdCarro",cdCarro);
            return (OrdenDeCompraEncOrdenVO)getBackBoneInvoke(map,"OBTIENERREG_ORDEN_DE_COMPRAS_PERSONAS");

	}

	/**
	 *  Da por finalizada una orden de compra
	 *  Hace uso del Store Procedure PKG_COTIZA.P_FINALIZA_ORDEN
	 * 
	 *  @param cdCarro
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String finalizarOrdenesDeCompras(String cdCarro)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdCarro",cdCarro);
		       
        WrapperResultados res =  returnBackBoneInvoke(map,"FINALIZAR_ORDEN_DE_COMPRAS");
        return res.getMsgText();
	}
	
	/**
	 *  Obtiene un conjunto de ordenes de compra
	 *  Hace uso del Store Procedure PKG_COTIZA.P_OBTIENE_HISTORDEN
	 * 
	 *  @param dsCarro
	 *  @param feInicioDe
	 *  @param feInicioA
	 *      
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList buscarOrdenesDeCompras(String dsCarro, String feInicioDe, String feInicioA, int start, int limit ) throws ApplicationException
	{
			HashMap map = new HashMap();
			map.put("pv_nmorden_i",dsCarro);
			  Converter converter = new UserSQLDateConverter("dd/MM/yyyy");
			   map.put("pv_feregistro_de_i", converter.convert(java.util.Date.class, feInicioDe));
			   map.put("pv_feregistro_a_i", converter.convert(java.util.Date.class, feInicioA));
			
	
            return pagedBackBoneInvoke(map, "BUSCAR_ORDEN_DE_COMPRAS", start, limit);

	}


}
