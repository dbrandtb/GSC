package mx.com.aon.catweb.configuracion.producto.service;

import mx.com.aon.catweb.configuracion.producto.sumaAsegurada.model.SumaAseguradaIncisoVO;
import mx.com.gseguros.exception.ApplicationException;


/**
 * 
 * @author ricardo.bautista
 *
 */
public interface SumaAseguradaManagerJdbcTemplate {
	
	/**
	 * Metodo que se utiliza para agregar una suma asegurada.
	 * 
	 * @param sumaAseguradaInciso
	 *            Objeto de tipo {@link SumaAseguradaIncisoVO} que contiene los
	 *            valores de la suma asegurada.
	 * @throws ApplicationException
	 * @see {@link SumaAseguradaIncisoVO}
	 */
	void agregaSumaAseguradaInciso(SumaAseguradaIncisoVO sumaAseguradaInciso) throws ApplicationException;

}
