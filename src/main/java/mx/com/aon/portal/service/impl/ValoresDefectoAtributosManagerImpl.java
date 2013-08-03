package mx.com.aon.portal.service.impl;

import java.util.HashMap;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.ValorDefectoAtributosOrdenVO;
import mx.com.aon.portal.service.ValoresDefectoAtributosManager;
import mx.com.aon.portal.util.WrapperResultados;

/**
 * Clase que implementa los servicios de la interfaz ValoresDefectoAtributosManager.
 * 
 * @extends AbstractManager
 *
 */
public class ValoresDefectoAtributosManagerImpl extends AbstractManager implements ValoresDefectoAtributosManager{
	
	/**
	 * Metodo que realiza la actualizacion de un valor por defecto.
	 * Invoca al Store Procedure PKG_ORDENT.P_GUARDA_VALOR_DEFECTO_ATRI.
	 * 
	 * @param cdFormatoOrden
	 * @param cdSeccion
	 * @param cdAtribu
	 * @param cdExpres
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String guardarValorDefectoAtributosOrden(String cdFormatoOrden,
			String cdSeccion, String cdAtribu, String cdExpres) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdformatoorden", cdFormatoOrden);
		map.put("cdseccion", cdSeccion);
		map.put("cdatribu", cdAtribu);
		map.put("cdexpres", cdExpres);
		WrapperResultados resultado =  returnBackBoneInvoke(map, "GUARDA_VALOR_DEFECTO_ATRI");
        return resultado.getMsgText();
    }
	
	/**
	 * Metodo que obtiene un unico registro valor por defecto para mostrar en el encabezado de la pantalla.
	 * Invoca al Store Procedure PKG_ORDENT.P_VALOR_DEFECTO_ATRIBUTO.
	 * 
	 * @param cdFormatoOrden
	 * @param cdSeccion
	 * @param cdAtribu
	 * 
	 * @return ValorDefectoAtributosOrdenVO
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public ValorDefectoAtributosOrdenVO obtenerValorDefectoAtributosOrden(
			String cdFormatoOrden, String cdSeccion, String cdAtribu) throws ApplicationException {
		
			HashMap map = new HashMap();
			map.put("cdformatoorden",cdFormatoOrden);
			map.put("cdseccion",cdSeccion);
			map.put("cdatribu",cdAtribu);
	        return (ValorDefectoAtributosOrdenVO) getBackBoneInvoke(map, "VALOR_DEFECTO_ATRIBUTO");
	}
}