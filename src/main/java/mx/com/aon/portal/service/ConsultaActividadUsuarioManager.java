package mx.com.aon.portal.service;

import java.sql.Timestamp;
import mx.com.aon.catbo.model.FormatoTimeStampVO;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.WrapperResultados;


/**
 * Interfaz de servicios que atiende requerimientos de los Action de
 *  agrupacion por papel.
 *
 */
public interface ConsultaActividadUsuarioManager {
	
	/**
	 * Metodo que realiza la busqueda y obtiene un conjunto de registros.
	 * 
	 * @param codConfiguracion
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	public WrapperResultados buscarActividadesUsuario (String pv_dsusuari_i, String pv_url_i,Timestamp pv_fe_ini_i, Timestamp pv_fe_fin_i,/* String sort, String dir,*/int start, int limit) throws ApplicationException;
	
	//public PagedList buscarActividadesUsuario (String pv_dsusuari_i, String pv_url_i,String pv_fe_ini_i, String pv_fe_fin_i, int start, int limit) throws ApplicationException;
	
    public String insertarActividadesUsuario (String pv_reqid_i, String pv_url_i , String pv_reqtype_i , String pv_cdusuario_i, String pv_cdrol_i) throws ApplicationException;

    public TableModelExport getModel(String pv_dsusuari_i, String pv_url_i,Timestamp pv_fe_ini_i, Timestamp pv_fe_fin_i) throws ApplicationException;
    
    public String buscarFechaInicialActividadesUsuario(String pv_dsusuari_i,String pv_url_i, int start, int limit)throws ApplicationException;
    
    public String buscarFechaFinalActividadesUsuario(String pv_dsusuari_i,String pv_url_i, int start, int limit)throws ApplicationException;
}
