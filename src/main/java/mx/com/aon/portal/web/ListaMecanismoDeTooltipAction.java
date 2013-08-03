package mx.com.aon.portal.web;

import mx.com.aon.portal.model.MecanismoDeTooltipVO;
import mx.com.aon.portal.service.MecanismoDeTooltipManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.MecanismoDeTooltipManagerJdbcTemplateImpl;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Ayuda de Coberturas.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaMecanismoDeTooltipAction extends AbstractListAction{

	private static final long serialVersionUID = 165449872125584L;

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaConfigurarEstructuraAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient MecanismoDeTooltipManager mecanismoDeTooltipManager;

	private transient MecanismoDeTooltipManager mecanismoDeTooltipManagerJdbcTemplate;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo ReciboVO
	 * con los valores de la consulta.
	 */
	private List<MecanismoDeTooltipVO> mecanismoTooltipList;
	private String nbObjeto;
	private String langCode;
	private String dsTitulo;

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
	 * Ejecuta la busqueda para el llenado de la grilla
	 * @return success
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{

            //PagedList pagedList = this.mecanismoDeTooltipManager.buscarMecanismoDeTooltip(nbObjeto, langCode, dsTitulo, start, limit);
			PagedList pagedList = mecanismoDeTooltipManagerJdbcTemplate.buscarMecanismoDeTooltip(nbObjeto, langCode, dsTitulo, start, limit);
            mecanismoTooltipList = pagedList.getItemsRangeList();
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
	 * Obtiene un conjunto de ayudas de cobertura y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			  contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			
			filename = "Recibos." + exportFormat.getExtension();
			
			TableModelExport model = mecanismoDeTooltipManager.getModel(nbObjeto, langCode, dsTitulo);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}


	public static Logger getLogger() {
		return logger;
	}


	public static void setLogger(Logger logger) {
		ListaMecanismoDeTooltipAction.logger = logger;
	}


	public MecanismoDeTooltipManager getMecanismoDeTooltipManager() {
		return mecanismoDeTooltipManager;
	}


	public void setMecanismoDeTooltipManager(
			MecanismoDeTooltipManager mecanismoDeTooltipManager) {
		this.mecanismoDeTooltipManager = mecanismoDeTooltipManager;
	}


	public List<MecanismoDeTooltipVO> getmecanismoTooltipList() {
		return mecanismoTooltipList;
	}


	public void setmecanismoTooltipList(List<MecanismoDeTooltipVO> estructuraList) {
		mecanismoTooltipList = estructuraList;
	}


	public List<MecanismoDeTooltipVO> getMecanismoTooltipList() {
		return mecanismoTooltipList;
	}


	public void setMecanismoTooltipList(
			List<MecanismoDeTooltipVO> mecanismoTooltipList) {
		this.mecanismoTooltipList = mecanismoTooltipList;
	}


	public String getNbObjeto() {
		return nbObjeto;
	}


	public void setNbObjeto(String nbObjeto) {
		this.nbObjeto = nbObjeto;
	}


	public String getLangCode() {
		return langCode;
	}


	public void setLangCode(String langCode) {
		this.langCode = langCode;
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


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public String getDsTitulo() {
		return dsTitulo;
	}


	public void setDsTitulo(String dsTitulo) {
		this.dsTitulo = dsTitulo;
	}


	public void setMecanismoDeTooltipManagerJdbcTemplate(
			MecanismoDeTooltipManager mecanismoDeTooltipManagerJdbcTemplate) {
		this.mecanismoDeTooltipManagerJdbcTemplate = mecanismoDeTooltipManagerJdbcTemplate;
	}
	
}
