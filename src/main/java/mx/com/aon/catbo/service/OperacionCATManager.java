package mx.com.aon.catbo.service;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.ConfigurarEncuestaVO;
import mx.com.aon.catbo.model.OperacionCATVO;
import mx.com.aon.catbo.model.ScriptObservacionVO;
import mx.com.aon.portal.service.PagedList;


/**
 * Interface de servicios para Guiones.
 *
 */
public interface OperacionCATManager{
	
	/**
	 *  Obtiene un conjunto de Guiones
	 * 
	 * @param dsUniEco
	 * @param dsElemento 
	 * @param dsGuion 
	 * @param dsProceso 
	 * @param dsTipGuion
	 * @param dsRamo
   	 * 
   	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
	public PagedList buscarGuiones(String dsUniEco, String dsElemento, String dsGuion, String dsProceso,String dsTipGuion,String dsRamo, int start, int limit) throws ApplicationException;

	
	
	/**
	 *  Obtiene un conjunto de Guiones Disponibles
	 * 
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
	//public List buscarGuionesDisp() throws ApplicationException;

	/**
	 *  Obtiene un conjunto de Guiones Disponibles
	 * 
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
	public List buscarGuionesDisp(String cdTipGuion, String cdElemento) throws ApplicationException;
	
	
	/**
	 *  Obtiene un conjunto de Dialogos para un guion
	 * 
	 * @param cdUniEco
	 * @param cdRamo 
	 * @param cdElemento 
	 * @param cdProceso 
	 * @param cdGuion
	 * 
   	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
	public List buscarDialogosGuion(String cdGuion) throws ApplicationException;

	
	
	
	/**
	 *  Elimina un Guion.
	 * 
	 * @param cdUniEco
	 * @param cdElemento 
	 * @param cdGuion 
	 * @param cdProceso 
     * @param cdRamo
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarGuion(String cdUniEco, String cdElemento, String cdGuion, String cdProceso, String cdRamo) throws ApplicationException;

	/**
	 *  Elimina un Guion.
	 * 
	 * @param cdUniEco
	 * @param cdRamo 
	 * @param cdElemento 
	 * @param cdProceso 
     * @param cdGuion
     * @param cdDialogo
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	public String borrarDialogo(String cdGuion, String cdDialogo) throws ApplicationException;

	
	/**
	 *  Obtiene una configuracion de guion especifica en base a un parametro de entrada.
	 * 
	 * @param cdUniEco
	 * @param cdElemento 
	 * @param cdGuion 
	 * @param cdProceso 
     * @param cdRamo
	 *  
	 *  @return Objeto OperacionCATVO
	 *  
	 *  @throws ApplicationException
	 *  
	 * 
	 */
	public OperacionCATVO getGuion(String cdUniEco, String cdElemento, String cdGuion, String cdProceso, String cdRamo) throws ApplicationException;
	
	
	/**
	 *  Salva la informacion de Guiones.
	 * 
	 *  @param operacionCATVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String guardarGuion(OperacionCATVO operacionCATVO) throws ApplicationException;
	
	
	/**
	 *  Salva la informacion de Dialogo para un Guion.
	 * 
	 *  @param operacionCATVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String guardarDialogoGuion(OperacionCATVO operacionCATVO) throws ApplicationException;
	
	
	/**
	 *  Obtiene una lista de formatos de documentos para la exportacion a un formato predeterminado.
	 * 
	 *  @param descripcion: parametro con el que se realiza la busqueda.
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModel(String dsUniEco, String dsElemento, String dsGuion, String dsProceso, String dsTipGuion, String dsRamo) throws ApplicationException;
	
	public TableModelExport getModelDialogo(String cdUniEco, String cdElemento, String cdGuion, String cdProceso, String cdTipGuion, String cdRamo) throws ApplicationException;

	public PagedList buscarDialogosGuiones(String cdGuion, int start, int limit)throws ApplicationException;

	public OperacionCATVO getScriptAtencionInicial(String cdTipGui, String indInicial) throws ApplicationException;
	
	/**
	 *  Salva la informacion de Dialogo para un Guion.
	 * 
	 *  @param scriptObservacionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	public String guardarDialogoScriptObservacion(ScriptObservacionVO scriptObservacionVO) throws ApplicationException;

	public ConfigurarEncuestaVO getObtenerEncuestasPendientes(String cdPerson) throws ApplicationException;

}
