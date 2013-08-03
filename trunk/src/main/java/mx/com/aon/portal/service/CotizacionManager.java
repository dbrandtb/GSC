package mx.com.aon.portal.service;

import java.io.File;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.utils.ServerConfigurationException;

public interface CotizacionManager {

	public PagedList buscarCotizacionesMasivas (String pv_cdelement,String pv_asegura , String pv_cdramo_i, String pv_cdlayout, String pv_fedesde_i, String pv_fehasta_i ,int start, int limit) throws ApplicationException;

    public String aprobarCotizacion(String pv_cdusuari,String pv_cdunieco, String pv_cdramo, String pv_estado, String pv_nmpoliza, 
    		String pv_nmsituac, String pv_nmsuplem, String pv_cdelement, String pv_cdcia, String pv_cdplan, String pv_cdperpag, String pv_cdperson, String pv_fecha) throws ApplicationException;

    public String borrarCotizacion(String pv_cdusuari, String pv_cdunieco, String pv_cdramo, String pv_estado, String pv_nmpoliza, 
    		String pv_nmsituac, String pv_nmsuplem, String pv_cdelement) throws ApplicationException;
    
    /**
     * Metodo que se utiliza para ejecutar la cotizacion masiva por medio de un {@link File}.
     * 
     * @param codigoElemento Codigo del Elemento relacionado
     * @param codigoProducto Codigo del Producto al que pertenecen las cotizaciones que se van a realizar
     * @param fileName Nombre del Archivo
     * @param file Archivo que contiene la informacion de las cotizaciones
     * @return Mensaje con el codigo de la operacion, si se ejecuto con exito.
     * @throws ApplicationException
     * @throws ServerConfigurationException
     */
    public String cotizarMasiva(String codigoElemento, String codigoProducto, String fileName, File file) throws ApplicationException, ServerConfigurationException;

}
