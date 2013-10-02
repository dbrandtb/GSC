package mx.com.aon.portal.service;

import mx.com.aon.portal.model.DescuentoDetVolumenVO;
import mx.com.aon.portal.model.DescuentoProductoVO;
import mx.com.aon.portal.model.DescuentoVO;
import mx.com.aon.portal.model.DetalleProductoVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;


/**
 * Interface de servicios para la configuracion de descuentos.
 *
 */
public interface DescuentosManager {

	
	/**
	 *  Obtiene un conjunto de descuentos.
	 * 
	 *  @param start
	 *  @param limit
	 *  @param dsDscto
	 *  @param dsDscto
	 *  @param otValor
	 *  @param dsCliente
	 *  
	 *  @return Objeto PagedList 
	 */	
	public PagedList buscarDescuentos(int start, int limit, String dsDscto, String otValor, String dsCliente) throws ApplicationException;
	
	
	/**
	 *  Realiza la copia de descuento seleccionado.
	 * 
	 *  @param cdDscto
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String copiarDescuento(String cdDscto) throws ApplicationException;

		
	/**
	 *  Realiza la baja de descuento .
	 * 
	 *  @param cdDscto
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String borrarDescuento(String cdDscto) throws ApplicationException;
	

	/**
	 *  Obtiene el encabezado de un producto.
	 * 
	 *  @param cdDscto
	 *  
	 *  @return DescuentoProductoVO
	 */		
	public DescuentoProductoVO getEncabezadoProducto(String cdDscto) throws ApplicationException;
	
	
	/**
	 *  Obtiene el detalle de producto de descuento.
	 * 
	 *  @param start
	 *  @param limit
	 *  @param cdDscto
	 *  
	 *  @return Objeto PagedList
	 */		
	public PagedList getDetalleProducto(int start, int limit, String cdDscto) throws ApplicationException;

	
	/**
	 *  Salva la informacion de producto de descuento.
	 * 
	 *  @param descuentoVO
	 *  
	 *  @return WrapperResultados
	 */		
	public WrapperResultados guardarProducto(DescuentoVO descuentoVO) throws ApplicationException;
	
	
	/**
	 *  Salva el detalle del producto de descuento.
	 * 
	 *  @param detalleProductoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String guardarDetalleProducto(DetalleProductoVO detalleProductoVO) throws ApplicationException;
	
	
	/**
	 *  Realiza la baja del detalle del producto de descuento.
	 * 
	 *  @param cdDscto
	 *  @param cdDsctod
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String borrarDetalleProducto(String cdDscto, String cdDsctod) throws ApplicationException;
	
	
	/**
	 *  Obtiene el encabezado del volumen de descuento.
	 * 
	 *  @param cdDscto
	 *  
	 *  @return DescuentoVO
	 */		
	public DescuentoVO getEncabezadoVolumen(String cdDscto) throws ApplicationException;
	
	
	/**
	 *  Obtiene el detalle del volumen de descuento.
	 * 
	 *  @param start
	 *  @param limit
	 *  @param cdDscto
	 *  
	 *  @return Objeto PagedList
	 */		
	public PagedList getDetalleVolumen(int start, int limit, String cdDscto) throws ApplicationException;
	

	
	/**
	 *  Salva el volumen de descuento.
	 * 
	 *  @param descuentoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public WrapperResultados guardarVolumen(DescuentoVO descuentoVO) throws ApplicationException;

	
	/**
	 *  Salva el detalle de volumen de descuento.
	 * 
	 *  @param descuentoDetVolumenVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String guardarDetalleVolumen(DescuentoDetVolumenVO descuentoDetVolumenVO) throws ApplicationException;


	/**
	 *  Realiza la baja del detalle de volumen de descuento.
	 * 
	 *  @param cdDscto
	 *  @param cdDsctod
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String borrarDetalleVolumen(String cdDscto, String cdDsctod) throws ApplicationException;
	

	/**
	  * Obtiene un conjunto de descuento y exporta el resultado en Formato PDF, Excel, CSV, etc.
	  *
	  * @param dsDscto
	  * @param otValor
	  * @param dsCliente
	  * 
	  * @return success
	  */
	public TableModelExport getModel(String dsDscto, String otValor, String dsCliente) throws ApplicationException;
}
