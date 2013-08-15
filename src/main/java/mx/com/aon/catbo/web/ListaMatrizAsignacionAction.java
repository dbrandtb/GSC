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
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.model.NotificacionVO;
import mx.com.aon.catbo.model.ResponsablesVO;
import mx.com.aon.catbo.model.TiemposVO;
import mx.com.aon.portal.util.Util;



import org.apache.log4j.Logger;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Notificaciones
 * 
 */
@SuppressWarnings("serial")
public class ListaMatrizAsignacionAction extends AbstractListAction{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaMatrizAsignacionAction.class);

	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient MatrizAsignacionManager matrizAsignacionManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo MatrizAsignacionVO
	 * con los valores de la consulta.
	 */
	private List<MatrizAsignacionVO> mEstructuraList;
	private List<MatrizAsignacionVO> mEstructuraNivAtencionList;
	private List<ResponsablesVO> mEstructuraResponsablesList;
	
	private List<TiemposVO> mEstructuraTiemposList;
	
	private String pv_dsproceso_i;
	private String pv_dsformatoorden_i;
	private String pv_dselemen_i;
	private String pv_dsunieco_i;
	private String pv_dsramo_i;
	private String codigoMatriz;
	private String pv_cdmatriz_i;
	private String  pv_cdnivatn_i;
	private String pv_cdusuario_i;
	private String nivAtencion;
	private String cdmodulo;
	
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
	 * Metodo que realiza la busqueda de matrices en base a
	 * en base a proceso , formato,
	 * elemento, aseguradora, producto
	 * 
	 * @param pv_dsproceso_i
	 * @param pv_dsformatoorden_i
	 * @param pv_dselemen_i
	 * @param pv_dsunieco_i
	 * @param pv_dsramo_i
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarMatricesClick() throws Exception{
		try{
            PagedList pagedList = this.matrizAsignacionManager.buscarMatrices(pv_dsproceso_i, pv_dsformatoorden_i, pv_dselemen_i, pv_dsunieco_i, pv_dsramo_i, start, limit);
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
	 * Metodo que obtiene una Matriz de Asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetMatrizClick()throws Exception
	{
		try
		{
			mEstructuraList = new ArrayList<MatrizAsignacionVO>();
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
	 * Metodo que obtiene Niveles de Atencion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarNivAtencionClick()throws Exception
	{
		try
		{
			
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
	 * Metodo que obtiene Niveles de Atencion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarResponsablesClick()throws Exception
	{
		try
		{
			
			PagedList pagedList = this.matrizAsignacionManager.obtieneResponsables(pv_cdmatriz_i, pv_cdnivatn_i, start, limit);
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
	 * Metodo que obtiene una Matriz de Asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdTiempoResolucionClick()throws Exception
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
	
	
	/**
	 * Metodo que obtiene una Responsable.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetResponsableClick()throws Exception
	{
		try
		{
			mEstructuraResponsablesList = new ArrayList<ResponsablesVO>();
			ResponsablesVO responsablesVO = this.matrizAsignacionManager.getObtieneResponsables(pv_cdmatriz_i, pv_cdnivatn_i, pv_cdusuario_i);
			mEstructuraResponsablesList.add(responsablesVO);
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
	public String cmdExportarFrmDocClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "MatrizAsignacion : " + formato );
		}
		
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Formatos Documentos." + exportFormat.getExtension();
			TableModelExport model =  this.matrizAsignacionManager.getModelMatrizAsignacion(pv_dsproceso_i, pv_dsformatoorden_i, pv_dselemen_i, pv_dsunieco_i, pv_dsramo_i);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}





	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}

	public MatrizAsignacionManager obtenMatrizAsignacionManager() {
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




	public String getPv_dsproceso_i() {
		return pv_dsproceso_i;
	}




	public void setPv_dsproceso_i(String pv_dsproceso_i) {
		this.pv_dsproceso_i = pv_dsproceso_i;
	}




	public String getPv_dsformatoorden_i() {
		return pv_dsformatoorden_i;
	}




	public void setPv_dsformatoorden_i(String pv_dsformatoorden_i) {
		this.pv_dsformatoorden_i = pv_dsformatoorden_i;
	}




	public String getPv_dselemen_i() {
		return pv_dselemen_i;
	}




	public void setPv_dselemen_i(String pv_dselemen_i) {
		this.pv_dselemen_i = pv_dselemen_i;
	}




	public String getPv_dsunieco_i() {
		return pv_dsunieco_i;
	}




	public void setPv_dsunieco_i(String pv_dsunieco_i) {
		this.pv_dsunieco_i = pv_dsunieco_i;
	}




	public String getPv_dsramo_i() {
		return pv_dsramo_i;
	}




	public void setPv_dsramo_i(String pv_dsramo_i) {
		this.pv_dsramo_i = pv_dsramo_i;
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


	public String getCodigoMatriz() {
		return codigoMatriz;
	}


	public void setCodigoMatriz(String codigoMatriz) {
		this.codigoMatriz = codigoMatriz;
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


	public String getNivAtencion() {
		return nivAtencion;
	}


	public void setNivAtencion(String nivAtencion) {
		this.nivAtencion = nivAtencion;
	}


	public List<MatrizAsignacionVO> getMEstructuraNivAtencionList() {
		return mEstructuraNivAtencionList;
	}


	public void setMEstructuraNivAtencionList(
			List<MatrizAsignacionVO> estructuraNivAtencionList) {
		mEstructuraNivAtencionList = estructuraNivAtencionList;
	}


	public List<TiemposVO> getMEstructuraTiemposList() {
		return mEstructuraTiemposList;
	}


	public void setMEstructuraTiemposList(List<TiemposVO> estructuraTiemposList) {
		mEstructuraTiemposList = estructuraTiemposList;
	}


	public List<ResponsablesVO> getMEstructuraResponsablesList() {
		return mEstructuraResponsablesList;
	}


	public void setMEstructuraResponsablesList(
			List<ResponsablesVO> estructuraResponsablesList) {
		mEstructuraResponsablesList = estructuraResponsablesList;
	}


	public String getCdmodulo() {
		return cdmodulo;
	}


	public void setCdmodulo(String cdmodulo) {
		this.cdmodulo = cdmodulo;
	}

	

	
	
}