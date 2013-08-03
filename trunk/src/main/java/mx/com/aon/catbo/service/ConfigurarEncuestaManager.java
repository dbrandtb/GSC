package mx.com.aon.catbo.service;

import java.util.HashMap;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.AsignarEncuestaVO;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.CatboTimecVO;
import mx.com.aon.catbo.model.ConfigurarEncuestaVO;
import mx.com.aon.catbo.model.EncuestaPreguntaVO;
import mx.com.aon.catbo.model.EncuestaVO;
import mx.com.aon.catbo.model.ItemVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

public interface ConfigurarEncuestaManager {
	
	public PagedList obtieneEncuesta(String pv_dsunieco_i, String pv_dsramo_i, String pv_dselemento_i ,String pv_dsproceso_i , String pv_dscampan_i, String pv_dsmodulo_i, String pv_dsencuesta_i,  int start, int limit) throws ApplicationException;
	
	//public String guardarConfigurarEncuesta(ConfigurarEncuestaVO configurarEncuestaVO)throws ApplicationException;	
	
	public PagedList obtenerEncuestas(String pv_dsencuesta_i, int start, int limit) throws ApplicationException;
	
	/**
	 *  Obtiene una lista de formatos de documentos para la exportacion a un formato predeterminado.
	 * 
	 *  @param descripcion: parametro con el que se realiza la busqueda.
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModel(String pv_dsencuesta_i) throws ApplicationException;
	
	public String guardaAsignacionEncuesta(AsignarEncuestaVO asignarEncuestaVO)throws ApplicationException;
	
	public PagedList obtenerValorEncuesta(String pv_dsunieco_i, String pv_dsramo_i,String pv_dsencuesta_i,String pv_dscampana_i,String pv_dsmodulo_i,String pv_dsproceso_i,String pv_nmpoliza_i,int start, int limit) throws ApplicationException;

	public ConfigurarEncuestaVO getObtenerEncuestaRegistro(String pv_nmconfig_i, String pv_cdproceso_i, String pv_cdcampan_i, String pv_cdmodulo_i, String pv_cdencuesta_i) throws ApplicationException;
	
	public String borrarEncuestaRegistro(String pv_nmconfig_i, String pv_cdproceso_i, String pv_cdcampan_i, String pv_cdmodulo_i, String pv_cdencuesta_i) throws ApplicationException;
	
	public CatboTimecVO getCatboTimec(String pv_cdcampana_i, String pv_cdunieco_i, String pv_cdramo_i) throws ApplicationException;
	
	public String guardaTiempoEncuesta(ConfigurarEncuestaVO configurarEncuestasVO)throws ApplicationException;
	
	public String borraTiempoEncuesta(String pv_cdencuesta_i )throws ApplicationException;
	
	public String guardaTiempoEncuestaTencuest(ConfigurarEncuestaVO configurarEncuestasVO)throws ApplicationException;
	
	public String guardaTpregunta(ConfigurarEncuestaVO configurarEncuestasVO)throws ApplicationException;
	
	public String borraAsignaEncuesta(String pv_nmconfig_i, String pv_cdunieco_i, String pv_cdramo_i, String pv_estado_i, String pv_nmpoliza_i, String pv_cdperson_i, String pv_cdusuario_i)throws ApplicationException;
	
	public String borrarTvalenct(String pv_nmconfig_i, String pv_cdunieco_i, String pv_cdramo_i, String pv_nmpoliza_i, String pv_estado_i, String pv_cdencuesta_i)throws ApplicationException;
	
	public String borrarTpregunt(String cdPregunta)throws ApplicationException;
	
	public String borraCATBOTiempoEncuesta(String pv_cdcampana_i, String pv_cdunieco_i, String pv_cdramo_i)throws ApplicationException;
	
	public String guardarTvalenct(ConfigurarEncuestaVO configurarEncuestaVO)throws ApplicationException;

	
	/**
	 *  Obtiene una lista de formatos de documentos para la exportacion a un formato predeterminado.
	 * 
	 *  @param descripcion: parametro con el que se realiza la busqueda.
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModelConfigEncuesta(String pv_dsunieco_i, String pv_dsramo_i, String pv_dselemento_i ,String pv_dsproceso_ , String pv_dscampan_i, String pv_dsmodulo_i, String pv_dsencuesta_i) throws ApplicationException;
	
	public PagedList obtenerAsignacionEncuesta(String pv_dsunieco_i,String pv_dsramo_i,String pv_estado_i,String  pv_nmpoliza_i, String pv_dsperson_i, String pv_dsusuario_i, int start, int limit) throws ApplicationException;
	
	public TableModelExport getModelAsignacionEncuesta(String pv_dsunieco_i,String pv_dsramo_i,String pv_estado_i,String  pv_nmpoliza_i, String pv_dsperson_i, String pv_dsusuario_i) throws ApplicationException;
	
	public AsignarEncuestaVO getObtenerAsignacionEncuesta(String pv_nmconfig_i, String pv_cdunieco_i, String pv_estado_i, String pv_cdramo_i, String pv_nmpoliza_i) throws ApplicationException;
	
	public TableModelExport getModelObtenerValoresEncuesta(String pv_dsunieco_i, String pv_dsramo_i,String pv_dsencuesta_i,String pv_dscampana_i,String pv_dsmodulo_i,String pv_dsproceso_i,String pv_nmpoliza_i) throws ApplicationException;

	/**
	 *  Inserta una nueva encuesta con las preguntas.
	 * 
	 *  @param encuestaPreguntaVO
	 *  
	 *  @return BackBoneResultVO
	 */		
	public BackBoneResultVO insertarNuevaEncuesta(EncuestaPreguntaVO encuestaPreguntaVO) throws ApplicationException;
	
	/**
	 *  Obtiene un conjunto de preguntas de encuestas.
	 * 
	 *  @param encuestaPreguntaVO
	 *  
	 *  @return BackBoneResultVO
	 */		
	public PagedList obtenerEncuestaPregunta(String cdEncuesta, int start, int limit) throws ApplicationException;
	//public List obtenerEncuestaPregunta(String cdEncuesta) throws ApplicationException;
	
	/**
	 *  Obtiene una configuracion de encuesta especifica en base a un parametro de entrada.
	 * 
	 *  @param cdEncuesta
	 *  
	 *  @return Objeto EncuestaVO
	 *  
	 *  @throws ApplicationException
	 */
	public EncuestaVO getEncuestaPregunta(String cdEncuesta) throws ApplicationException;
	
	/**
	 *  Obtiene una lista de formatos de documentos para la exportacion a un formato predeterminado.
	 * 
	 *  @param cdEncuesta: parametro con el que se realiza la busqueda.
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModelEncuestaPregunta(String cdEncuesta) throws ApplicationException;
	
	/**
	 *  Adiciona una nueva encuesta con las preguntas.
	 * 
	 *  @param encuestaPreguntaVO
	 *  
	 *  @return BackBoneResultVO
	 */		
	public BackBoneResultVO agregarNuevaEncuesta(EncuestaPreguntaVO encuestaPreguntaVO) throws ApplicationException;
	
	public WrapperResultados validaConfiguracionEncuesta(String cdModulo, String cdUnieco, String cdRamo, String cdElemento) throws ApplicationException;
	
	public PagedList getObtenerValorEncuestaRegistro(String pv_nmconfig_i, String pv_cdunieco_i, String pv_cdramo_i, String pv_estado_i, String pv_nmpoliza_i,String pv_cdperson_i, int start, int limit) throws ApplicationException;
	
	public ConfigurarEncuestaVO getObtenerValorEncuestaEncabezado(String pv_nmconfig_i, String pv_cdunieco_i, String pv_cdramo_i, String pv_estado_i, String pv_nmpoliza_i,String pv_cdperson_i) throws ApplicationException;
	
	public HashMap obtenerIngresarEncuestasEditarParaExportar (String nmconfig, String cdunieco, String cdramo, String estado, String nmpoliza,String cdperson, String cdelemento) throws ApplicationException;
	
	public HashMap<String, ItemVO> obtenerIngresarEncuestasEditarVariablesParaExportar (String nmconfig, String cdunieco, String cdramo, String estado, String nmpoliza,String cdperson) throws ApplicationException;
}
