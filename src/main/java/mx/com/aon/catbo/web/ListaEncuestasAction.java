package mx.com.aon.catbo.web;

import java.io.InputStream;
import java.util.List;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.EncuestaVO;
import mx.com.aon.catbo.model.PreguntaEncuestaVO;
import mx.com.aon.catbo.service.ConfigurarEncuestaManager;
import mx.com.aon.portal.service.PagedList;
import org.apache.log4j.Logger;
import mx.com.aon.portal.util.Util;

public class ListaEncuestasAction extends AbstractListAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1137878786546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaEncuestasAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient ConfigurarEncuestaManager configurarEncuestaManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo VO
	 * con los valores de la consulta.
	 */
	private List<EncuestaVO> mEncuestasList;
	
	private List<PreguntaEncuestaVO> mEncuestaPregunta;
	
	private String cdEncuesta;
	private String dsEncuesta;
	
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
	
	
	private String pv_dsencuesta_i;
	
	/**
	 * Metodo que realiza una búsqueda en base a criterios de búsquedas
	 *  
	 * @param 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String obtenerEncuestas() throws Exception{
		try{
            PagedList pagedList = configurarEncuestaManager.obtenerEncuestas(dsEncuesta, start, limit);
            mEncuestasList = pagedList.getItemsRangeList();
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
	 * Metodo que realiza una búsqueda de pregunta de encuesta base a criterios de búsquedas 
	 *  
	 * @param 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String obtenerEncuestaPreguntaClick() throws Exception{
		try{
            PagedList pagedList = configurarEncuestaManager.obtenerEncuestaPregunta(cdEncuesta, start, limit);
            mEncuestaPregunta = pagedList.getItemsRangeList();
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

/*	public String obtenerEncuestaPreguntaClick() throws Exception {
        try {
            mEncuestaPregunta = configurarEncuestaManager.obtenerEncuestaPregunta(cdEncuesta);
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
*/
	
	/**
	 * Metodo que busca un conjunto de formatos documentos  
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarEncuestas() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Encuestas : " + formato );
		}
		
		try {
			   contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Encuestas." + exportFormat.getExtension();
			TableModelExport model =  configurarEncuestaManager.getModel(dsEncuesta);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}

	
	/**
	 * Metodo que busca un conjunto de preguntas de encuesta  
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarEncuestaPregunta() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Pregunta : " + formato );
		}
		
		try {
			contentType = Util.getContentType(formato);
	        if (logger.isDebugEnabled()) {
	           logger.debug( "content-type : " + contentType );
	        }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Pregunta." + exportFormat.getExtension();
			TableModelExport model =  configurarEncuestaManager.getModelEncuestaPregunta(cdEncuesta);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
	
	
	
	public ConfigurarEncuestaManager obtenConfigurarEncuestaManager() {
		return configurarEncuestaManager;
	}

	public void setConfigurarEncuestaManager(
			ConfigurarEncuestaManager configurarEncuestaManager) {
		this.configurarEncuestaManager = configurarEncuestaManager;
	}

	public List<EncuestaVO> getMEncuestasList() {
		return mEncuestasList;
	}

	public void setMEncuestasList(List<EncuestaVO> encuestasList) {
		mEncuestasList = encuestasList;
	}

	public String getPv_dsencuesta_i() {
		return pv_dsencuesta_i;
	}

	public void setPv_dsencuesta_i(String pv_dsencuesta_i) {
		this.pv_dsencuesta_i = pv_dsencuesta_i;
	}
	
	
	public List<PreguntaEncuestaVO> getMEncuestaPregunta() {
		return mEncuestaPregunta;
	}

	public void setMEncuestaPregunta(List<PreguntaEncuestaVO> encuestaPregunta) {
		mEncuestaPregunta = encuestaPregunta;
	}

	public String getCdEncuesta() {
		return cdEncuesta;
	}


	public void setCdEncuesta(String cdEncuesta) {
		this.cdEncuesta = cdEncuesta;
	}


	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}

	public String getDsEncuesta() {
		return dsEncuesta;
	}

	public void setDsEncuesta(String dsEncuesta) {
		this.dsEncuesta = dsEncuesta;
	}

}
