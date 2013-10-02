package mx.com.aon.portal.web;



import java.io.InputStream;
import java.util.List;

import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ReporteDeudaVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.ReporteDeudaManager;
import mx.com.aon.portal.util.Util;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;


/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Administracion Tipos de Faxes.
 * 
 */
@SuppressWarnings("serial")
public class ReporteDeudaAction extends AbstractListAction {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ReporteDeudaAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta al Web Service del sistema AA
	 */	
	private transient ReporteDeudaManager reporteDeudaManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	private List<ReporteDeudaVO> mReporteDeudaList;

	private String cdAsegurado;
	private String cdAseguradora;
	private String cdProducto;
	private String nmPoliza;
	private String fechaDesde;
	
	private String descripcion;
	
	
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
	 * Metodo que obtiene el Reporte de Deuda del Sistema AA .
	 * Reporte Deuda
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */

	@SuppressWarnings("unchecked")
	public String cmdBuscarReporte()throws Exception
	{ 
		
        try
        {	PagedList pagedList = this.reporteDeudaManager.buscarReportesDeuda(start, limit, cdAsegurado, cdAseguradora, nmPoliza, fechaDesde);
        	mReporteDeudaList = pagedList.getItemsRangeList();
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
	public String cmdExportarReporteDeuda() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}		
		try {
			   contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Reporte Deuda." + exportFormat.getExtension();
			TableModelExport model =  reporteDeudaManager.getModel(cdAsegurado, cdAseguradora, nmPoliza, fechaDesde);
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


	

	public String getCdAsegurado() {
		return cdAsegurado;
	}


	public void setCdAsegurado(String cdAsegurado) {
		this.cdAsegurado = cdAsegurado;
	}


	public String getCdAseguradora() {
		return cdAseguradora;
	}


	public void setCdAseguradora(String cdAseguradora) {
		this.cdAseguradora = cdAseguradora;
	}


	public String getCdProducto() {
		return cdProducto;
	}


	public void setCdProducto(String cdProducto) {
		this.cdProducto = cdProducto;
	}


	public String getNmPoliza() {
		return nmPoliza;
	}


	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}


	public String getFechaDesde() {
		return fechaDesde;
	}


	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}


	public void setReporteDeudaManager(ReporteDeudaManager reporteDeudaManager) {
		this.reporteDeudaManager = reporteDeudaManager;
	}


	public List<ReporteDeudaVO> getMReporteDeudaList() {
		return mReporteDeudaList;
	}


	public void setMReporteDeudaList(List<ReporteDeudaVO> reporteDeudaList) {
		mReporteDeudaList = reporteDeudaList;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



}