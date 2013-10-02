package mx.com.aon.portal.web;

import java.io.InputStream;
import java.util.List;

import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfiguracionProcesoVO;
import mx.com.aon.portal.service.ConfiguracionProcesoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;
import mx.com.gseguros.exception.ApplicationException;


import org.apache.log4j.Logger;

public class ListaConfiguracionProcesoAction extends AbstractListAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4584329493309958786L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaConfiguracionProcesoAction.class);

	private transient ConfiguracionProcesoManager configuracionProcesoManager;

	private boolean success;

	private List<ConfiguracionProcesoVO> listaProcesos;
	/**
	 * Parametros recibidos desde el cliente
	 */
	private String dsAseguradora;
	private String dsProducto;
	private String dsElemento;
	private String dsProceso;

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

	@SuppressWarnings("unchecked")
	public String cmdBuscarClick () throws ApplicationException {
		try {
			PagedList pagedList = configuracionProcesoManager.buscarProcesos(dsAseguradora, dsProducto, dsElemento, dsProceso, start, limit);
			listaProcesos = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}

	/**
	 * Metodo que busca un conjunto de procesos y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarClick() throws Exception {		
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}		
		try {

            contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 			
			filename = "Procesos." + exportFormat.getExtension();			
			TableModelExport model = configuracionProcesoManager.getModel(dsAseguradora, dsProducto, dsElemento, dsProceso);			
			inputStream = exportFormat.export(model);			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}		
		return SUCCESS;
	}
	//SETTERS y GETTERS
	public List<ConfiguracionProcesoVO> getListaProcesos() {
		return listaProcesos;
	}

	public void setListaProcesos(List<ConfiguracionProcesoVO> listaProcesos) {
		this.listaProcesos = listaProcesos;
	}

	public String getDsAseguradora() {
		return dsAseguradora;
	}

	public void setDsAseguradora(String dsAseguradora) {
		this.dsAseguradora = dsAseguradora;
	}

	public String getDsProducto() {
		return dsProducto;
	}

	public void setDsProducto(String dsProducto) {
		this.dsProducto = dsProducto;
	}

	public String getDsElemento() {
		return dsElemento;
	}

	public void setDsElemento(String dsElemento) {
		this.dsElemento = dsElemento;
	}

	public String getDsProceso() {
		return dsProceso;
	}

	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}

	public void setConfiguracionProcesoManager(
			ConfiguracionProcesoManager configuracionProcesoManager) {
		this.configuracionProcesoManager = configuracionProcesoManager;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}
	
}
