package mx.com.aon.catbo.web;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.service.MatrizAsignacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.model.FormatoDocumentoVO;
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.model.TiemposVO;



import org.apache.log4j.Logger;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Notificaciones
 * 
 */
@SuppressWarnings("serial")
public class ListaConfiguraMatrizTareaAction extends AbstractListAction{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaConfiguraMatrizTareaAction.class);

	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient MatrizAsignacionManager matrizAsignacionManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo MatrizAsignacionVO
	 * con los valores de la consulta.
	 */
	private List<MatrizAsignacionVO> mEstructuraList;
	private List<TiemposVO> mEstructuraTiemposList;
	
	private String pv_cdmatriz_i;
	private String pv_cdnivatn_i;
	private String pv_cdusuario_i;
	private String codigoMatriz;
	private String nivAtencion;
	
	private boolean success;

	/**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
		
	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
	
	
	/**
	 * Metodo que realiza la busqueda de niveles de atencion en base a
	 * codigo de matriz
	 * 
	 * @param pv_cdmatriz_i
	 *
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarNivelesAtencionClick() throws Exception{
		try{
            PagedList pagedList = this.matrizAsignacionManager.obtieneNivAtencion(pv_cdmatriz_i, start, limit);
            mEstructuraList = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();                                                    
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

	
	/**
	 * Metodo que realiza la busqueda de responsables en base a
	 * en base a proceso , formato,
	 * elemento, aseguradora, producto
	 * 
	 * @param pv_cdmatriz_i
	 *
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarResponsablesClick() throws Exception{
		try{
            PagedList pagedList = this.matrizAsignacionManager.obtieneResponsables(pv_cdmatriz_i, pv_cdnivatn_i,  start, limit);
            mEstructuraList = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();                                                    
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

	
	
	
	/**
	 * Metodo que busca un conjunto de formatos documentos  
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	/*
	public String cmdExportarFrmDocClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "MatrizAsignacion : " + formato );
		}
		
		try {
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Formatos Documentos." + exportFormat.getExtension();
			TableModelExport model =  this.matrizAsignacionManager.getMatrizAsignacion(codigoMatriz);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
*/

	/**
	 * Metodo que obtiene una notificacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetMatrizAsignacionClick()throws Exception
	{
		try
		{
			mEstructuraList=new ArrayList<MatrizAsignacionVO>();
			MatrizAsignacionVO matrizAsignacionVO = this.matrizAsignacionManager.getMatrizAsignacion(codigoMatriz);
			mEstructuraList.add(matrizAsignacionVO);
			success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}


	/**
	 * Metodo que obtiene una notificacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetTiempoResolucionClick()throws Exception
	{
		try
		{
			mEstructuraTiemposList = new ArrayList<TiemposVO>();
			TiemposVO tiemposVO = this.matrizAsignacionManager.getTiempoResolucion(codigoMatriz, nivAtencion);
			mEstructuraTiemposList.add(tiemposVO);
			success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

	
	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}

	public MatrizAsignacionManager getMatrizAsignacionManager() {
		return matrizAsignacionManager;
	}




	public void setMatrizAsignacionManager(
			MatrizAsignacionManager matrizAsignacionManager) {
		this.matrizAsignacionManager = matrizAsignacionManager;
	}




	public List<MatrizAsignacionVO> getMEstructuraList() {
		return mEstructuraList;
	}




	public void setMEstructuraList(List<MatrizAsignacionVO> estructuraList) {
		mEstructuraList = estructuraList;
	}




	




	



	




	

	public String getFormato() {
		return formato;
	}


	public void setFormato(String formato) {
		this.formato = formato;
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public ExportMediator getExportMediator() {
		return exportMediator;
	}


	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}


	public String getPv_cdmatriz_i() {
		return pv_cdmatriz_i;
	}


	public void setPv_cdmatriz_i(String pv_cdmatriz_i) {
		this.pv_cdmatriz_i = pv_cdmatriz_i;
	}


	public String getPv_cdnivatn_i() {
		return pv_cdnivatn_i;
	}


	public void setPv_cdnivatn_i(String pv_cdnivatn_i) {
		this.pv_cdnivatn_i = pv_cdnivatn_i;
	}


	public String getPv_cdusuario_i() {
		return pv_cdusuario_i;
	}


	public void setPv_cdusuario_i(String pv_cdusuario_i) {
		this.pv_cdusuario_i = pv_cdusuario_i;
	}


	public String getCodigoMatriz() {
		return codigoMatriz;
	}


	public void setCodigoMatriz(String codigoMatriz) {
		this.codigoMatriz = codigoMatriz;
	}


	public String getNivAtencion() {
		return nivAtencion;
	}


	public void setNivAtencion(String nivAtencion) {
		this.nivAtencion = nivAtencion;
	}


	public List<TiemposVO> getMEstructuraTiemposList() {
		return mEstructuraTiemposList;
	}


	public void setMEstructuraTiemposList(List<TiemposVO> estructuraTiemposList) {
		mEstructuraTiemposList = estructuraTiemposList;
	}

	

	
	
}