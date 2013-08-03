package mx.com.aon.catbo.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.model.ResponsablesVO;
import mx.com.aon.catbo.model.TiemposVO;
import mx.com.aon.portal.service.PagedList;

/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Sep 2, 2008
 * Time: 10:44:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MatrizAsignacionManager {
	
   	public PagedList buscarMatrices(String pv_dsproceso_i, String pv_dsformatoorden_i,
			String pv_dselemen_i, String pv_dsunieco_i, String pv_dsramo_i, int start, int limit) throws ApplicationException ;
   
   	public String borrarMatriz(String pv_cdmatriz_i)throws ApplicationException;
   	
   	public MatrizAsignacionVO getMatrizAsignacion(String codigoMatriz) throws ApplicationException;
   	
	public PagedList obtieneNivAtencion(String pv_cdmatriz_i, int start, int limit) throws ApplicationException;
   	
	public PagedList obtieneResponsables(String pv_cdmatriz_i,String pv_cdnivatn_i,  int start, int limit) throws ApplicationException;
	
	
	public TiemposVO getTiempoResolucion(String codigoMatriz, String nivAtencion) throws ApplicationException;
	
	public BackBoneResultVO guardarMatriz(MatrizAsignacionVO MatrizAsignacionVO) throws ApplicationException;
	
	public String borrarResponsablesMatriz(String pv_cdmatriz_i, String pv_cdnivatn_i, String pv_cdusuario_i)
	throws ApplicationException;
		
	public String borrarTiemposMatriz(String pv_cdmatriz_i, String pv_cdnivatn_i)
	throws ApplicationException;
	
	public String guardarResponsables(ResponsablesVO responsablesVO)throws ApplicationException;
	
	public String guardarTiempos(TiemposVO tiemposVO)throws ApplicationException;
		
	public String guardarSuplente(CasoVO casoVO)throws ApplicationException;
	
	public ResponsablesVO getObtieneResponsables(String pv_cdmatriz_i, String pv_cdnivatn_i, String pv_cdusuario_i)throws ApplicationException;
	
	public String validaResponsable(String pv_cdmatriz_i,String pv_cdnivatn_i, String pv_cdusuari_i ) throws ApplicationException;
		
	
	/**
	 *  Obtiene una lista de formatos de documentos para la exportacion a un formato predeterminado.
	 * 
	 *  @param descripcion: parametro con el que se realiza la busqueda.
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModelMatrizAsignacion(String pv_dsproceso_i, String pv_dsformatoorden_i,
			String pv_dselemen_i, String pv_dsunieco_i, String pv_dsramo_i) throws ApplicationException;
	
	
}
