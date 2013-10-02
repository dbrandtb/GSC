package mx.com.aon.portal.web;


import mx.com.aon.portal.service.RangoRenovacionReporteManager;

import mx.com.aon.portal.service.PagedList;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;
import mx.com.gseguros.exception.ApplicationException;



import org.apache.log4j.Logger;


import java.io.InputStream;
import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Rango de Renovacion.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaRangosRenovacionAction extends AbstractListAction{


	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaRangosRenovacionAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 */
	private transient RangoRenovacionReporteManager rangoRenovacionReporteManager;


	private List<RangoRenovacionReporteManager> mRangoRenovacionReporteManagerList;
	
	private String cdRenova;
	
	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
    /**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Metodo que realiza la busqueda de datos de rangos de renovacion
	 * en base a codigo de renovacion 
	 * 
	 * @param cdRenova
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */ 
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
			
			
            PagedList pagedList = this.rangoRenovacionReporteManager.buscarRangosRenovacion(cdRenova, start, limit);
            mRangoRenovacionReporteManagerList = pagedList.getItemsRangeList();
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
	 * Metodo que busca un conjunto de peridos de gracia  
	 * y exporta el resultado en Formato PDF, Excel, etc.
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

			filename = "RangosRenovacion." + exportFormat.getExtension();

			TableModelExport model = rangoRenovacionReporteManager.getModel(cdRenova);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
	
	/**
	 * Metodo que permite regresar a configurar renovaciones 
	 */
	 public String cmdRegresarConfiguracionRenovacion(){
			return "consultaConfiguracionRenovacion";
	 }
	  		
		
		public String getFormato() {return formato;}
		public void setFormato(String formato) {this.formato = formato;}

		public InputStream getInputStream() {return inputStream;}
		public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

		public String getFilename() {return filename;}
		public void setFilename(String filename) {this.filename = filename;}

		public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}

		public RangoRenovacionReporteManager obtenRangoRenovacionReporteManager() {
			return rangoRenovacionReporteManager;
		}

		public void setRangoRenovacionReporteManager(
				RangoRenovacionReporteManager rangoRenovacionReporteManager) {
			this.rangoRenovacionReporteManager = rangoRenovacionReporteManager;
		}

		public List<RangoRenovacionReporteManager> obtenMRangoRenovacionReporteManagerList() {
			return mRangoRenovacionReporteManagerList;
		}

		public void setMRangoRenovacionReporteManagerList(
				List<RangoRenovacionReporteManager> rangoRenovacionReporteManagerList) {
			mRangoRenovacionReporteManagerList = rangoRenovacionReporteManagerList;
		}

		public String getCdRenova() {
			return cdRenova;
		}

		public void setCdRenova(String cdRenova) {
			this.cdRenova = cdRenova;
		}
		
}
