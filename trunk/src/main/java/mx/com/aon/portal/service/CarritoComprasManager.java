package mx.com.aon.portal.service;


import java.util.List;



import mx.com.aon.portal.model.CarritoComprasDireccionOrdenVO;
import mx.com.aon.portal.model.CarritoComprasRolesVO;
import mx.com.aon.portal.model.CarritoComprasGuardarVO;
import mx.com.aon.portal.model.CarritoComprasVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para el Carrito de Compras
 *
 */
public interface CarritoComprasManager  {
	
	/**
	 *  Obtiene configuracion del carrido de compras en base a un parametro de entrada
	 * 
	 *  @param cdConfiguracion
	 *  
	 *  @return Objeto CarritoComprasVO
	 *  
	 *  @throws ApplicationException
	 */
    public CarritoComprasVO getCarritoCompras(String cdConfiguracion) throws ApplicationException;
   
  /**
	 *  Inserta un nuevo carrito de compras
	 * 
	 *  @param carritoComprasVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    public String agregarGuardarCarritoCompras(CarritoComprasVO carritoComprasVO) throws ApplicationException;
    
	/**
	 *  Elimina un carrito de compras
	 * 
	 *  @param cdConfiguracion
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarCarritoCompras(String cdConfiguracion) throws ApplicationException;
	
	/**
	 *  Calcula el descuento a aplicar en el carrito de compras
	 * 
	 *  @param cdCarro
	 *  @param cdUsuari
	 *  @param cdClient
	 *    
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */
	public WrapperResultados calculaDescuentoCarritoCompras(String cdCarro, String cdUsuari, String cdClient) throws ApplicationException;

	/**
	 *  Obtiene un conjunto de elementos del carrito de compras
	 * 
	 *  @param cdCliente
	 *  @param fgSino
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos CarritoComprasVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarCarritoCompras(String cdCliente, String fgSiNo, int start, int limit )throws ApplicationException;
	
	
	/**
	 *  Obtiene un conjunto de roles del carrito de compras
	 * 
	 *  @param cdUniEco
	 *  @param cdRamo
	 *  @param nmPoliza
	 *  @param nmSuplem
	 *  @param cdAsegur
	 *  @param cdRol
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos CarritoComprasRolesVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarRolesCarritoCompras(String cdUniEco, String cdRamo, String nmPoliza, String nmSuplem, String cdAsegur, String cdRol, int start, int limit )throws ApplicationException;
	
	/**
	 *  Obtiene el monto del carrito de compras
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
	public List obtenerMontosCarrito(String cdCarro, String cdUsuari, String cdClient)throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto de producto del carrito de compras
	 * 
	 *  @param cdCarro
	 *  @param cdUsuari
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos CarritoComprasProductosVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList obtenerProductosCarritoCompras(String cdCarro, String cdUsuari, int start, int limit )throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto de montos del carrito de compras
	 * 
	 *  @param cdCarro
	 *  @param start comienzo o limite inferior de la paginacion de la grilla
	 *  @param limit final o limite superior de la paginacion la grilla
	 *  
	 *  @return Conjunto de objetos CarritoComprasProductosVO
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList montosOrdenCarritoCompras(String cdCarro, int start, int limit) throws ApplicationException;
	
	/**
	 *  Obtiene la direccion del contratante del carrito de compras
	 * 
	 *  @param cdCarro
	 *  @param cdTipDom
	 *    
	 *  @return Objeto CarritoComprasDireccionOrdenVO
	 *  
	 *  @throws ApplicationException
	 */
	public CarritoComprasDireccionOrdenVO obtieneDireccionOrdenCarritoCompras(String cdContra, String cdTipDom) throws ApplicationException;
	
	/**
	 *  Guarda lo correspondiente al carrito de compras
	 * 
	 *  @param carritoComprasGuardarVO
	 *    
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String guardarCarrito(CarritoComprasGuardarVO carritoComprasGuardarVO) throws ApplicationException;
	
	/**
	 *  Guarda la forma de pago utilizada en el carrito de compras
	 * 
	 *  @param carritoComprasGuardarVO
	 *    
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String guardarFormaPago(CarritoComprasGuardarVO carritoComprasGuardarVO) throws ApplicationException;
	
	/**
	 *  Obtiene el orden del carrito de compras
	 * 
	 *  @param cdCarro
	 *    
	 *  @return Objeto CarritoComprasRolesVO
	 *  
	 *  @throws ApplicationException
	 */
	public CarritoComprasRolesVO obtieneEncOrdenCarritoCompras(String cdCarro) throws ApplicationException;
	
	/**
	 *  Determina el orden en el carrito de compras
	 * 
	 *  @param cdCarro
	 *  @param cdUsuari
	 *  @param cdPerson
	 *    
	 *  @return Objeto CarritoComprasRolesVO
	 *  
	 *  @throws ApplicationException
	 */
	public CarritoComprasRolesVO detOrdenCarritoCompras(String cdCarro, String cdUsuari, String cdPerson) throws ApplicationException;
	
	/**
	 *  Elimina un registro del carrito de compras
	 * 
	 *  @param cdCarro
	 *  @param cdCarroD
	 *    
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarRegCarritoCompras(String cdCarro, String cdCarroD) throws ApplicationException;
	
}
