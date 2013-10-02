package mx.com.aon.catweb.configuracion.producto.service;

import java.util.List;

import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.sumaAsegurada.model.SumaAseguradaIncisoVO;
import mx.com.aon.catweb.configuracion.producto.sumaAsegurada.model.SumaAseguradaVO;
import mx.com.gseguros.exception.ApplicationException;

public interface SumaAseguradaManager {

	List<LlaveValorVO> catalogoTipoSumaAsegurada() throws ApplicationException;

	List<LlaveValorVO> catalogoSumaAsegurada(String codigoRamo)
			throws ApplicationException;

	List<LlaveValorVO> catalogoMonedaSumaASegurada()
			throws ApplicationException;

	List<SumaAseguradaVO> listaSumaAsegurada(String codigoRamo,
			String codigoCapital) throws ApplicationException;

	void agregaSumaAseguradaProducto(SumaAseguradaVO sumaAsegurada)
			throws ApplicationException;

	void eliminaSumaAseguradaProducto(String codigoCapital, String codigoRamo)
			throws ApplicationException;

	List<SumaAseguradaIncisoVO> listaSumaAseguradaInciso(String codigoRamo,
			String codigoCobertura, String codigoCapital,
			String codigoTipoSituacion) throws ApplicationException;

	/**
	 * Metodo que se utiliza para agregar una suma asegurada.
	 * 
	 * @param sumaAseguradaInciso
	 *            Objeto de tipo {@link SumaAseguradaIncisoVO} que contiene los
	 *            valores de la suma asegurada.
	 * @throws ApplicationException
	 * @see {@link SumaAseguradaIncisoVO}
	 */
	void agregaSumaAseguradaInciso(SumaAseguradaIncisoVO sumaAseguradaInciso)
			throws ApplicationException;

	void eliminaSumaAseguradaInciso(String codigoCapital, String codigoRamo,
			String codigoTipoSituacion) throws ApplicationException;

}
