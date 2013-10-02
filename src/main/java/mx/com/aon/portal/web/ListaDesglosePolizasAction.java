package mx.com.aon.portal.web;

import mx.com.aon.portal.service.DesglosePolizasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import org.apache.log4j.Logger;
import java.io.InputStream;
import java.util.List;
import mx.com.aon.portal.util.Util;
import mx.com.gseguros.exception.ApplicationException;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Desglose de polizas.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaDesglosePolizasAction extends AbstractListAction{


	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaDesglosePolizasAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient DesglosePolizasManager desglosePolizasManager;
	private List<DesglosePolizasManager> mDesglosePolizasList;
    private String cdPerson;
    private String cdElemento;
    private String cdTipCon;
    private String cdRamo;
    private String dsCliente;
    private String dsConcepto;
    private String dsProducto;
    private String cdEstruct;
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
	 * Metodo que ejecuta la busqueda de un conjunto de registros de polizas el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = this.desglosePolizasManager.buscarDesglosePolizas(dsCliente, dsConcepto, dsProducto, cdEstruct, start, limit);
            mDesglosePolizasList = pagedList.getItemsRangeList();
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
	 * Metodo que busca un conjunto de registro s y exporta el resultado en Formato PDF, Excel, etc.
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
			
			filename = "DesglosePolizas." + exportFormat.getExtension();
			
			TableModelExport model = desglosePolizasManager.getModel(dsCliente, dsConcepto, dsProducto, cdEstruct);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
		public List<DesglosePolizasManager> getMDesglosePolizasList() {
			return mDesglosePolizasList;
		}

		public void setMDesglosePolizasList(
				List<DesglosePolizasManager> desglosePolizasList) {
			mDesglosePolizasList = desglosePolizasList;
		}

		public String getCdPerson() {
			return cdPerson;
		}

		public void setCdPerson(String cdPerson) {
			this.cdPerson = cdPerson;
		}

		public String getCdElemento() {
			return cdElemento;
		}

		public void setCdElemento(String cdElemento) {
			this.cdElemento = cdElemento;
		}

		public String getCdTipCon() {
			return cdTipCon;
		}

		public void setCdTipCon(String cdTipCon) {
			this.cdTipCon = cdTipCon;
		}

		public String getCdRamo() {
			return cdRamo;
		}

		public void setCdRamo(String cdRamo) {
			this.cdRamo = cdRamo;
		}


		public void setDesglosePolizasManager(
				DesglosePolizasManager desglosePolizasManager) {
			this.desglosePolizasManager = desglosePolizasManager;
		}


		public ExportMediator getExportMediator() {
			return exportMediator;
		}
		
		public String getFormato() {return formato;}
		public void setFormato(String formato) {this.formato = formato;}

		public InputStream getInputStream() {return inputStream;}
		public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

		public String getFilename() {return filename;}
		public void setFilename(String filename) {this.filename = filename;}

		public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}


		public String getDsCliente() {
			return dsCliente;
		}


		public void setDsCliente(String dsCliente) {
			this.dsCliente = dsCliente;
		}


		public String getDsConcepto() {
			return dsConcepto;
		}


		public void setDsConcepto(String dsConcepto) {
			this.dsConcepto = dsConcepto;
		}


		public String getDsProducto() {
			return dsProducto;
		}


		public void setDsProducto(String dsProducto) {
			this.dsProducto = dsProducto;
		}


		public DesglosePolizasManager obtenDesglosePolizasManager() {
			return desglosePolizasManager;
		}
}
