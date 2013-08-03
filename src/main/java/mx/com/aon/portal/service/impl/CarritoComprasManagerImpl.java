
package mx.com.aon.portal.service.impl;



import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.CarritoComprasRolesVO;
import mx.com.aon.portal.model.CarritoComprasDireccionOrdenVO;
import mx.com.aon.portal.model.CarritoComprasGuardarVO;
import mx.com.aon.portal.model.CarritoComprasVO;
import mx.com.aon.portal.service.CarritoComprasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements CarritoComprasManager
 * 
 * @extends AbstractManager
 */

public class CarritoComprasManagerImpl extends AbstractManager implements CarritoComprasManager {

	/**
	 *  Obtiene un uso del carrito de compras
	 *  Hace uso del Store Procedure PKG_CARROCOMPRA.P_OBTIENE_CLIENTES
	 * 
	 *  @param cdConfiguracion
	 *  
	 *  @return Objeto CarritoComprasVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public CarritoComprasVO getCarritoCompras(String cdConfiguracion) throws ApplicationException
	{
			HashMap map = new HashMap();
			map.put("cdConfiguracion",cdConfiguracion);
			return (CarritoComprasVO)getBackBoneInvoke(map,"OBTIENE_CARRITO_COMPRAS_REG");
	}

	/**
	 *  Inserta un uso del carrito de compras
	 *  Hace uso del Store Procedure PKG_CARROCOMPRA.P_GUARDA_CONFIGURA
	 * 
	 *  @param CarritoComprasVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked") // Manejo de ArrayList Controlado
	public String agregarGuardarCarritoCompras(CarritoComprasVO carritoComprasVO) throws ApplicationException{
        	// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			
			map.put("cdConfiguracion",carritoComprasVO.getCdConfiguracion());
            map.put("cliente",carritoComprasVO.getCdCliente());
            map.put("cdelemento",carritoComprasVO.getCdElemento());
			map.put("siNo",carritoComprasVO.getFgSiNo());
			
            WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_GUARDA_CARRITO_COMPRAS");
            return res.getMsgText();

	}
    
	/**
	 *  Elimina una configuracion del carrito de compras
	 *  Hace uso del Store Procedure PKG_CARROCOMPRA.P_BORRA_CONFIGURA
	 * 
	 *  @param cdConfiguracion
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public String borrarCarritoCompras(String cdConfiguracion) throws ApplicationException{
    		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			map.put("cdConfiguracion",cdConfiguracion);
			
            WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_CARRITO_COMPRAS");
            return res.getMsgText();
    }
    
	/**
	 *  Obtiene un conjunto de usos del carrito de compras
	 *  Hace uso del Store Procedure  PKG_CARROCOMPRA.P_OBTIENE_CLIENTES
	 * 
	 *  @param cdCliente
	 *  @param fgSiNo
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
    
   
    
	@SuppressWarnings("unchecked")
	public PagedList buscarCarritoCompras(String cdCliente, String fgSiNo, int start, int limit )throws ApplicationException
	{
			// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
	
			map.put("cdCliente",cdCliente);
			map.put("fgSiNo",fgSiNo);
			
			return pagedBackBoneInvoke(map, "OBTIENE_CARRITO_COMPRAS", start, limit);

	}

	/**
	 *  Obtiene roles del carrito de compras
	 *  Hace uso del Store Procedure PKG_COTIZA.P_ROLES_CARRITO
	 * 
	 *  @param cdUniEco
	 *  @param cdRamo
	 *  @param nmPoliza
	 *  @param nmPoliza
	 *  @param cdAsegur
	 *  @param cdRol
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList buscarRolesCarritoCompras(String cdUniEco, String cdRamo, String nmPoliza, String nmSuplem, String cdAsegur, String cdRol, int start, int limit) throws ApplicationException {

		HashMap map = new HashMap();

		map.put("cdUniEco",cdUniEco);
		map.put("cdRamo",cdRamo);
		map.put("nmPoliza",nmPoliza);
		map.put("nmSuplem",nmSuplem);
		map.put("cdAsegur",cdAsegur);
		map.put("cdRol",cdRol);
		
		return pagedBackBoneInvoke(map, "OBTIENE_ROLES_CARRITO_COMPRAS", start, limit);
	}
	


	/**
	 *  Obtiene productos del carrito de compras
	 *  Hace uso del Store Procedure PKG_COTIZA.P_PRODUCTOS_CARRITO
	 * 
	 *  @param cdCarro
	 *  @param cdUsuari
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList obtenerProductosCarritoCompras(String cdCarro, String cdUsuari,int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdCarro",cdCarro);
		map.put("cdUsuari",cdUsuari);
	
		
		return pagedBackBoneInvoke(map, "OBTIENE_PRODUCTOS_CARRITO_COMPRAS", start, limit);
	}

	/**
	 *  Obtiene el descuento que se aplica en un carrito de compras
	 *  Hace uso del Store Procedure PKG_COTIZA.P_OBTIENE_MONTOS_CARRITO
	 * 
	 *  @param cdCarro
	 *  @param cdUsuari 
	 *     
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public WrapperResultados calculaDescuentoCarritoCompras(String cdCarro, String cdUsuari, String cdClient) throws ApplicationException {
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("cdCarro",cdCarro);
		map.put("cdUsuari",cdUsuari);
		map.put("cdClient",cdClient);
		
		WrapperResultados res =  returnBackBoneInvoke(map,"CALCULA_DESCUENTO_CARRITO_COMPRAS");
        return res;
	}

	/**
	 *  Obtiene un listado de motos de un carrito de compras
	 *  Hace uso del Store Procedure PKG_COTIZA.P_OBTIENE_MONTOS_CARRITO
	 * 
	 *  @param cdCarro
	 *  @param cdUsuari 
	 *  @param cdClient
	 *   
	 *  @return Objeto List
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public List obtenerMontosCarrito(String cdCarro, String cdUsuari,String cdClient) throws ApplicationException {
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("cdCarro",cdCarro);
		map.put("cdUsuari",cdUsuari);
		map.put("cdClient",cdClient);
		
		List res =  getAllBackBoneInvoke(map,"OBTENER_MONTOS_CARRITO_COMPRAS");
        return res;
	}

	/**
	 *  Obtiene una orden del carrito de compras
	 *  Hace uso del Store Procedure PKG_COTIZA.P_ENC_ORDEN
	 * 
	 *  @param cdCarro
	 *  
	 *  @return Objeto CarritoComprasRolesVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public CarritoComprasRolesVO obtieneEncOrdenCarritoCompras(String cdCarro)throws ApplicationException {
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("cdCarro",cdCarro);
		
		return (CarritoComprasRolesVO)getBackBoneInvoke(map,"OBTIENE_ENC_ORDEN_CARRITO_COMPRAS");
		
	}
	
	/**
	 *  Determina una orden del carrito de compras
	 *  Hace uso del Store Procedure PKG_COTIZA.P_DET_ORDEN
	 * 
	 *  @param cdCarro
	 *  @param cdUsuari 
	 *  @param cdPerson
	 *  
	 *  @return Objeto CarritoComprasRolesVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public CarritoComprasRolesVO detOrdenCarritoCompras(String cdCarro, String cdUsuari,String cdPerson) throws ApplicationException {
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("cdCarro",cdCarro);
		map.put("cdUsuari",cdUsuari);
		map.put("cdPerson",cdPerson);
		
		return (CarritoComprasRolesVO)getBackBoneInvoke(map,"DET_ORDEN_CARRITO_COMPRAS");
		
	}

	/**
	 *  Inserta un nuevo carrito de compras
	 *  Hace uso del Store Procedure PKG_COTIZA.P_GUARDA_CARRITO
	 * 
	 *  @param carritoComprasGuardarVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String guardarCarrito(CarritoComprasGuardarVO carritoComprasGuardarVO)
			throws ApplicationException {
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("p_cdcarro_i",carritoComprasGuardarVO.getCdCarro());
		map.put("p_cdusuari",carritoComprasGuardarVO.getCdUsuari());
		map.put("p_feinicio",carritoComprasGuardarVO.getFeInicio());
		map.put("p_nmtarj",carritoComprasGuardarVO.getNmTarj());
		map.put("p_cdcontra",carritoComprasGuardarVO.getCdContra());
		map.put("p_cdasegur",carritoComprasGuardarVO.getCdAsegur());
		map.put("p_nmsubtot",carritoComprasGuardarVO.getNmSubtot());
		map.put("p_nmdsc",carritoComprasGuardarVO.getNmDsc());
		map.put("p_nmtotal",carritoComprasGuardarVO.getNmTotal());
		map.put("p_cdestado",carritoComprasGuardarVO.getCdEstado());
		map.put("p_feestado",carritoComprasGuardarVO.getFeEstado());
		map.put("p_cdtipdom",carritoComprasGuardarVO.getCdTipDom());
		map.put("p_nmorddom",carritoComprasGuardarVO.getNmOrdDom());
		map.put("p_cdclient",carritoComprasGuardarVO.getCdClient());	
		map.put("p_cdunieco",carritoComprasGuardarVO.getCdUniEco());
		map.put("p_cdramo",carritoComprasGuardarVO.getCdRamo());
		map.put("p_nmpoliza",carritoComprasGuardarVO.getNmPoliza());
		map.put("p_nmsuplem",carritoComprasGuardarVO.getNmSuplem());
		map.put("p_mntotalp",carritoComprasGuardarVO.getMnTotalP());
		map.put("p_cdtipsit",carritoComprasGuardarVO.getCdTipSit());
		map.put("p_cdplan",carritoComprasGuardarVO.getCdPlan());
		map.put("p_fgdscapli",carritoComprasGuardarVO.getFgDscapli());
		map.put("p_feingres",carritoComprasGuardarVO.getFeIngres());
		map.put("p_cdestadod",carritoComprasGuardarVO.getCdEstadoD());
		map.put("p_cdforpag",carritoComprasGuardarVO.getCdForPag());
		
				
		WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_CARRITO");
        return res.getMsgText();
	}
	
	/**
	 *  Obtiene un conjunto de montos del carrito de compras
	 *  Hace uso del Store Procedure PKG_COTIZA.P_MONTOS_ORDEN
	 * 
	 *  @param cdCarro
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList montosOrdenCarritoCompras(String cdCarro, int start, int limit)throws ApplicationException {
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("cdCarro",cdCarro);
		
		return pagedBackBoneInvoke(map,"MONTOS_ORDEN_CARRITO_COMPRAS",start, limit);
        
	}
	
	/**
	 *  Obtiene el domicilio de un usuario que usa el carrito de compras
	 *  Hace uso del Store Procedure PKG_COTIZA.P_OBTIENE_DIRECCION_ORDEN
	 * 
	 *  @param cdContra
	 *  @param cdTipDom
	 *  
	 *  @return Objeto CarritoComprasDireccionOrdenVO
	 *  
	 *  @throws ApplicationException
	 */	

	@SuppressWarnings("unchecked")
	public CarritoComprasDireccionOrdenVO obtieneDireccionOrdenCarritoCompras(String cdContra,String cdTipDom) throws ApplicationException {
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("cdContra",cdContra);
		map.put("cdTipDom",cdTipDom);
		
		return (CarritoComprasDireccionOrdenVO)getBackBoneInvoke(map,"OBTIENE_DIRECCION_ORDEN_CARRITO_COMPRAS");

	}
	
	/**
	 *  Elimina un carrito de compras
	 *  Hace uso del Store Procedure PKG_COTIZA.P_BORRA_REG_CARRITO
	 * 
	 *  @param cdCarro
	 *  @param cdCarroD
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borrarRegCarritoCompras(String cdCarro, String cdCarroD)	throws ApplicationException {
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("cdCarro",cdCarro);
		map.put("cdCarroD",cdCarroD);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_REG_CARRITO_COMPRAS");
        return res.getMsgText();
	}
	
	/**
	 *  Inserta una forma de pago en el carrito de compras
	 *  Hace uso del Store Procedure PKG_COTIZA.P_GUARDA_MTARJETA
	 * 
	 *  @param carritoComprasGuardarVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String guardarFormaPago(CarritoComprasGuardarVO carritoComprasGuardarVO)	throws ApplicationException {
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("pv_nmtarjeta_i",carritoComprasGuardarVO.getNmTarj());
		map.put("pv_cdtitarj_i",carritoComprasGuardarVO.getCdTiTarj());
		map.put("pv_cdperson_i",carritoComprasGuardarVO.getCdPerson());
		map.put("pv_fevence_i",carritoComprasGuardarVO.getFeVence());
		map.put("pv_cdbanco_i",carritoComprasGuardarVO.getCdBanco());
		map.put("pv_debcred_i",carritoComprasGuardarVO.getDebCred());
		
				
		WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_MTARJETA");
        return res.getMsgText();
	}
	
	
	
}
