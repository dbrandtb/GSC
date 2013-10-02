package mx.com.aon.portal.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.DescuentoDetVolumenVO;
import mx.com.aon.portal.model.DescuentoProductoVO;
import mx.com.aon.portal.model.DescuentoVO;
import mx.com.aon.portal.model.DetalleProductoVO;
import mx.com.aon.portal.service.DescuentosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;


/**
 * Implementa la interface de servicios para descuento.
 *
 */
public class DescuentosManagerImpl extends AbstractManager implements DescuentosManager {
	

	/**
	 *  Obtiene un conjunto de descuento.
	 *  Usa el Store Procedure PKG_DSCTO.P_OBTIENE_DESCTOS.
	 * 
	 *  @param start
	 *  @param limit
	 *  dsDscto
	 *  dsDscto
	 *  otValor
	 *  dsCliente
	 *  
	 *  @return Objeto Lista
	 */	
	@SuppressWarnings({ "unchecked", "unchecked" })
	public PagedList buscarDescuentos(int start, int limit, String dsDscto, String otValor, String dsCliente)throws ApplicationException
	{
		HashMap map = new HashMap();
		map.put("dsDscto", dsDscto);
		map.put("otValor", otValor);
		map.put("dsCliente", dsCliente);
		String endpointName = "OBTIENE_DESCTOS";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
    
	
	/**
	 *  Realiza la baja de descuento .
	 *  Usa el Store Procedure PKG_DSCTO.P_ELIMINA_DSCTO.
	 * 
	 *  @param cdDscto
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */			
    @SuppressWarnings("unchecked")
	public String borrarDescuento(String cdDscto) throws ApplicationException
    {
			HashMap map = new HashMap();
			map.put("cdDscto", cdDscto);
            WrapperResultados res =  returnBackBoneInvoke(map,"ELIMINA_DSCTO");
            return res.getMsgText();
    }
	
    
	/**
	 *  Realiza la copia de descuento seleccionado.
	 *  Usa el Store Procedure PKG_DSCTO.P_COPIA_DESCUENTO.
	 *   
	 *  @param cdDscto
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	@SuppressWarnings("unchecked")
	public String copiarDescuento(String cdDscto)throws ApplicationException
	{
        HashMap map = new HashMap();
        map.put("cdDscto",cdDscto);
        WrapperResultados res =  returnBackBoneInvoke(map,"COPIA_DSCTO");
        return res.getMsgText();
	}

	
	/**
	 *  Realiza la baja del detalle del producto de descuento.
	 *  Usa el Store Procedure PKG_DSCTO.P_BORRA_DET_PROD.
	 * 
	 *  @param cdDscto
	 *  @param cdDsctod
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	@SuppressWarnings("unchecked")
	public String borrarDetalleProducto(String cdDscto, String cdDsctod) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdDscto", cdDscto);
		map.put("cdDsctod", cdDsctod);
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_DET_PROD");
        return res.getMsgText();
	}
	
	
	/**
	 *  Realiza la baja del detalle de volumen de descuento.
	 *  Usa el Store Procedure PKG_DSCTO.P_ELIMINA_DET_VOLUMEN.
	 * 
	 *  @param DetalleProductoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	@SuppressWarnings("unchecked")
	public String borrarDetalleVolumen(String cdDscto, String cdDsctod) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdDscto", cdDscto);
		map.put("cdDsctod", cdDsctod);
        WrapperResultados res =  returnBackBoneInvoke(map,"ELIMINA_DET_VOLUMEN");
        return res.getMsgText();
	}

	
	/**
	 *  Obtiene el detalle de producto de descuento.
	 *  Usa el Store Procedure PKG_DSCTO.P_OBTIENE_DETALLE_PROD.
	 * 
	 *  @param start
	 *  @param limit
	 *  @param cdDscto
	 *  
	 *  @return Objeto PagedList
	 */		
	@SuppressWarnings("unchecked")
	public PagedList getDetalleProducto(int start, int limit, String cdDscto) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdDscto",cdDscto);
		return this.pagedBackBoneInvoke(map, "OBTIENE_DETALLE_PROD", start, limit);
	}

	
	/**
	 *  Obtiene el detalle del volumen de descuento.
	 *  Usa el Store Procedure PKG_DSCTO.P_OBTIENE_DET_VOLUMEN.
	 * 
	 *  @param start
	 *  @param limit
	 *  @param cdDscto
	 *  
	 *  @return Objeto PagedList
	 */		
	@SuppressWarnings("unchecked")
	public PagedList getDetalleVolumen(int start, int limit, String cdDscto) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdDscto",cdDscto);
		return this.pagedBackBoneInvoke(map, "OBTIENE_DET_VOLUMEN", start, limit);
	}

	
	/**
	 *  Obtiene el encabezado de un producto.
	 *  Usa el Store Procedure PKG_DSCTO.P_OBTIENE_ENC_PROD.
	 * 
	 *  @param cdDscto
	 *  
	 *  @return DescuentoProductoVO
	 */			
	@SuppressWarnings("unchecked")
	public DescuentoProductoVO getEncabezadoProducto(String cdDscto) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdDscto",cdDscto);
        return (DescuentoProductoVO)getBackBoneInvoke(map,"OBTIENE_ENC_PROD");
	}

	
	/**
	 *  Obtiene el encabezado del volumen de descuento.
	 *  Usa el Store Procedure PKG_DSCTO.P_OBTIENE_ENC_VOLUMEN.
	 * 
	 *  @param cdDscto
	 *  
	 *  @return DescuentoVO
	 */		
	@SuppressWarnings("unchecked")
	public DescuentoVO getEncabezadoVolumen(String cdDscto) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdDscto",cdDscto);
        return (DescuentoVO)getBackBoneInvoke(map,"OBTIENE_ENC_VOLUMEN");
	}

	
	/**
	 *  Salva el detalle del producto de descuento.
	 *  Usa el Store Pprocedure PKG_DSCTO.P_GUARDA_PRODUCTO_DETALLE.
	 * 
	 *  @param detalleProductoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */			
	@SuppressWarnings("unchecked")
	public String guardarDetalleProducto(DetalleProductoVO detalleProductoVO) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cddscto", detalleProductoVO.getCdDscto());
		map.put("cddsctod", detalleProductoVO.getCdDsctod());
		map.put("cdunieco", detalleProductoVO.getCdUniEco());
		map.put("cdramo", detalleProductoVO.getCdRamo());
		map.put("cdtipsit", detalleProductoVO.getCdTipSit());
		map.put("cdplan", detalleProductoVO.getCdPlan());
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_PRODUCTO_DETALLE");
        return res.getMsgText();
	}

	
	/**
	 *  Salva el detalle de volumen de descuento.
	 *  Usa el Store Procedure PKG_DSCTO.P_GUARDA_VOLUMEN_DET.
	 * 
	 *  @param DetalleProductoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */			
	@SuppressWarnings("unchecked")
	public String guardarDetalleVolumen(DescuentoDetVolumenVO descuentoDetVolumenVO)  throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cddscto", descuentoDetVolumenVO.getCdDscto());
		map.put("cddsctod", descuentoDetVolumenVO.getCdDsctod());
		map.put("mnvolini", descuentoDetVolumenVO.getMnVolIni());
		map.put("mnvolfin", descuentoDetVolumenVO.getMnVolFin());
		map.put("prdescto", descuentoDetVolumenVO.getPrDescto());
		map.put("mndescto", descuentoDetVolumenVO.getMnDescto());
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_VOLUMEN_DET");
        return res.getMsgText();
	}

	
	/**
	 *  Salva la informacion de producto de descuento.
	 *  Usa el Store Procedure PKG_DSCTO.P_GUARDA_VOLUMEN_DET.
	 * 
	 *  @param descuentoVO
	 *  
	 *  @return WrapperResultados
	 */		
	@SuppressWarnings("unchecked")
	public WrapperResultados guardarProducto(DescuentoVO descuentoVO) throws ApplicationException {
						
			HashMap map = new HashMap();
			String cdDscto = descuentoVO.getCdDscto();
			map.put("cddscto", cdDscto);
			map.put("dsnombre", descuentoVO.getDsDscto());
			map.put("cdtipo", descuentoVO.getCdTipo());
			map.put("cdelemento", descuentoVO.getCdElemento());
			map.put("cdperson", descuentoVO.getCdPerson());
			map.put("prdescto", descuentoVO.getPrDescto());
			map.put("mndescto", descuentoVO.getMnDescto());
			map.put("fgacumul", descuentoVO.getFgAcumul());
			map.put("cdestado", descuentoVO.getCdEstado());
			
			WrapperResultados res;
			if(cdDscto.equals("") || cdDscto == null){
				res = returnBackBoneInvoke(map,"INSERTA_PRODUCTO");
			}
			else{
				res = returnBackBoneInvoke(map,"GUARDA_PRODUCTO");
			}
			return res;
	}
	
	
	/**
	 *  Salva el volumen de descuento.
	 *  Usa los Store Volumen PKG_DSCTO.P_GUARDA_VOLUMEN y PKG_DSCTO.P_GUARDA_VOLUMEN_DET.
	 * 
	 *  @param DetalleProductoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	@SuppressWarnings("unchecked")
	public WrapperResultados guardarVolumen(DescuentoVO descuentoVO) throws ApplicationException{
			HashMap map = new HashMap();
			String cdDscto = descuentoVO.getCdDscto();
			map.put("cddscto", cdDscto);
			map.put("dsNombre", descuentoVO.getDsNombre());
			map.put("cdtipo", descuentoVO.getCdTipo());
			map.put("cdelemento", descuentoVO.getCdElemento());
			map.put("cdperson", descuentoVO.getCdPerson());
			map.put("fgacumul", descuentoVO.getFgAcumul());
			map.put("cdestado", descuentoVO.getCdEstado());
	        
			WrapperResultados res;
			if(cdDscto.equals("") || cdDscto == null){
				res = returnBackBoneInvoke(map,"INSERTA_VOLUMEN");
			}
			else{
				res=returnBackBoneInvoke(map,"GUARDA_VOLUMEN");
			}
			return res;
       
	}
	

	/**
	  * Obtiene un conjunto de descuento y exporta el resultado en Formato PDF, Excel, CSV, etc.
	  * Usa el Store Procedure PKG_DSCTO.P_OBTIENE_DESCTOS
	  * 
	  * @return success
	  * 
	  * @throws Exception
	  */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsDscto, String otValor,String dsCliente) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("dsDscto", dsDscto);
		map.put("otValor", otValor);
		map.put("dsCliente", dsCliente);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_DESCUENTOS_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Nombre","Tipo","Nivel","Acumula","Activo"});
		
		return model;
	}

	

}

