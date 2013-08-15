package mx.com.aon.catbo.web;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import mx.com.aon.catbo.service.ArchivosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.portal.util.Util;


import mx.com.aon.export.model.TableModelExport;

import java.io.InputStream;
import java.util.List;

public class AtributosArchivoAction extends AbstractListAction {

    private static Logger logger = Logger.getLogger(AtributosArchivoAction.class);

    private transient ArchivosManager archivosManager;

    private String cdTipoar;
    private String dsArchivo;
    private String cdAtribu;
    private String dsAtribu;
    private String swFormat;
    private String nmLmax;
    private String nmLmin;
    private String swObliga;
    private String dsTabla;
    private String otTabval;
    private String status;
    
    private List mAtributoArchivosList;    
    private String Obliga;    

    private ExportMediator exportMediator;
    
    
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
  	 private String filename;
  	 
 	/**
 	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
 	 */
 	private String formato;
 	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
 	
	private InputStream inputStream;
    
    
    

    public List getMAtributoArchivosList() {
		return mAtributoArchivosList;
	}

	public void setMAtributoArchivosList(List atributoArchivosList) {
		mAtributoArchivosList = atributoArchivosList;
	}

	public void setArchivosManager(ArchivosManager archivosManager) {
        this.archivosManager = archivosManager;
    }
    
    public String getCdTipoar() {
		return cdTipoar;
	}

	public void setCdTipoar(String cdTipoar) {
		this.cdTipoar = cdTipoar;
	}

	public String getDsArchivo() {
		return dsArchivo;
	}

	public void setDsArchivo(String dsArchivo) {
		this.dsArchivo = dsArchivo;
	}

	public String getCdAtribu() {
		return cdAtribu;
	}


	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
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

	public String getDsTabla() {
		return dsTabla;
	}

	public void setDsTabla(String dsTabla) {
		this.dsTabla = dsTabla;
	}

	public String getOtTabval() {
		return otTabval;
	}

	public void setOtTabval(String otTabval) {
		this.otTabval = otTabval;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String cmdBuscarAtributosArchivos() throws Exception{
     //   return "";

        try{
            PagedList pagedList = archivosManager.buscarAtributosArchivos(dsArchivo, start, limit) ;
            mAtributoArchivosList = pagedList.getItemsRangeList();
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
	
	public String cmdBorrarAtributosArchivos() throws Exception{
		String messageResult = "";
		try{
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;

          }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	public String cmdExportararchivo() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Archivo : " + formato );   
		}
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
            
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			
			filename = "Atributos Archivos." + exportFormat.getExtension();
			TableModelExport model = archivosManager.getModel(dsArchivo);

			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}

	
	public ExportMediator getExportMediator() {
		return exportMediator;
	}

	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
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

	public ArchivosManager obtenArchivosManager() {
		return archivosManager;
	}

	public String getObliga() {
		return Obliga;
	}

	public void setObliga(String obliga) {
		Obliga = obliga;
	}
	
	
	

}
