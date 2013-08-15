package mx.com.aon.catbo.web;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.service.ArchivosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;



import org.apache.log4j.Logger;

import java.io.InputStream;
/**
 *   Action que atiende las peticiones de que vienen de la pantalla Notificaciones
 * 
 */
@SuppressWarnings("serial")
public class ListaAdministracionTiposFaxAction extends AbstractListAction{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaAdministracionTiposFaxAction.class);

	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient ArchivosManager archivosManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo
	 * con los valores de la consulta.
	 */
	private List<FaxesVO> mAtributosFaxList;

	private String cdtipoar;
	private String cdatribu;
	private String dsAtribu;
	private String swFormat;
	private String nmLmax;
	private String nmLmin;
	
	private String swObliga;
	private String otTabVal;
	private String status;
	
	
	private String dsArchivo;
    
	
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
	 * Metodo que realiza la busqueda de notificaciones en base a
	 * en base a codigo notificacion, descripcion notificacion,
	 * descripcion mensaje, codigo formato, codigo metodo Envio 
	 * 
	 * @param dsFormato
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = this.archivosManager.obtieneAtributosFax(cdtipoar, start, limit);
            this.mAtributosFaxList = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();                                                    
            success = true;
            return SUCCESS;
       }catch(ApplicationException e)
		{   success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }catch(Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        	}
		}
	
	public String cmdEditarGuardarClick()throws Exception
	{ 
		String messageResult = "";
        try
        {	 
          	messageResult = archivosManager.editarGuardarAtributosFax(cdtipoar, cdatribu, dsAtribu, swFormat, nmLmax, nmLmin, swObliga, otTabVal, status);
            success = true;
            addActionMessage(messageResult);
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
	
	public String cmdExportarClick() throws Exception{
	/*	if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}*/
		
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );   
		}		
		
		
		try {
			   contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Archivos." + exportFormat.getExtension();
			TableModelExport model =  archivosManager.getModel(cdtipoar);
			
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


	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}


	public String getDsArchivo() {
		return dsArchivo;
	}
	public void setDsArchivo(String dsArchivo) {
		this.dsArchivo = dsArchivo;
	}


	public ArchivosManager obtenArchivosManager() {
		return archivosManager;
	}


	public void setArchivosManager(ArchivosManager archivosManager) {
		this.archivosManager = archivosManager;
	}


	public List<FaxesVO> getMAtributosFaxList() {
		return mAtributosFaxList;
	}


	public String getCdtipoar() {
		return cdtipoar;
	}
	public void setCdtipoar(String cdtipoar) {
		this.cdtipoar = cdtipoar;
	}
	public String getCdatribu() {
		return cdatribu;
	}
	public void setCdatribu(String cdatribu) {
		this.cdatribu = cdatribu;
	}
	public String getDsAtribu() {
		return dsAtribu;
	}
	public void setDsAtribu(String dsAtribu) {
		this.dsAtribu = dsAtribu;
	}
	public String getSwFormat() {
		return swFormat;
	}
	public void setSwFormat(String swFormat) {
		this.swFormat = swFormat;
	}
	public String getNmLmax() {
		return nmLmax;
	}
	public void setNmLmax(String nmLmax) {
		this.nmLmax = nmLmax;
	}
	public String getNmLmin() {
		return nmLmin;
	}
	public void setNmLmin(String nmLmin) {
		this.nmLmin = nmLmin;
	}
	public String getSwObliga() {
		return swObliga;
	}
	public void setSwObliga(String swObliga) {
		this.swObliga = swObliga;
	}
	public String getOtTabVal() {
		return otTabVal;
	}
	public void setOtTabVal(String otTabVal) {
		this.otTabVal = otTabVal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setMAtributosFaxList(List<FaxesVO> atributosFaxList) {
		mAtributosFaxList = atributosFaxList;
	}


	public ExportMediator getExportMediator() {
		return exportMediator;
	}
	
	
}