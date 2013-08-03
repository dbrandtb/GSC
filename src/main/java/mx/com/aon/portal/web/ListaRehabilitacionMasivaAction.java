package mx.com.aon.portal.web;

import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.RehabilitacionMasivaVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.RehabilitacionMasivaManager;
import mx.com.aon.portal.util.Util;


import org.apache.log4j.Logger;

public class ListaRehabilitacionMasivaAction extends AbstractListAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7296834292254752404L;

	/**
	 * Logger para monitoreo de la clase
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaRehabilitacionMasivaAction.class);

	/**
	 * Manager con implementacion de endpoint para la consulta a BD
	 */
	private transient RehabilitacionMasivaManager rehabilitacionMasivaManager;

	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo RehabilitacionMasivaVO
	 * con los valores de la consulta.
	 */
	private List<RehabilitacionMasivaVO> listaRehabilitacionMasiva;

	//private boolean success;

	private String dsAsegurado;

	private String dsAseguradora;
	
	private String dsProducto;

	private String nmPoliza;

	private String nmInciso;

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
	 * Atributo con el(los) dato(s) que identificará(n) el objeto Ext.data.Record que se seleccionó
	 * desde una pantalla de origen. Una vez que se desee regresar, se utilizará este atributo. 
	 */
	private String idRegresar;
	
	/**
	 * Metodo que devuelve el conjunto de Polizas a mostrar en la grilla de busqueda
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarPolizasARehabilitar () throws ApplicationException {
		Map<String,String> map = new HashMap<String,String>();

		map.put("dsAsegurado", dsAsegurado);
		map.put("dsAseguradora", dsAseguradora);
		map.put("dsProducto", dsProducto);
		map.put("nmPoliza", nmPoliza);
		map.put("nmInciso", nmInciso);
		map.put("idRegresar", idRegresar);
		map.put("clicBotonRegresar", "N");
    	
    	logger.debug("parametrosRegresar");
    	logger.debug(map);
    	
    	session.put("PARAMETROS_REGRESAR", map);
		
		try {
			PagedList pagedList = new PagedList();
			pagedList = rehabilitacionMasivaManager.buscarPolizasCanceladasARehabilitar(dsAsegurado, dsAseguradora, dsProducto, nmPoliza, nmInciso, start, limit);
			listaRehabilitacionMasiva = pagedList.getItemsRangeList();
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
	 * Permite exportar los datos en la grilla
	 * 
	 * @return
	 * @throws Exception
	 */
	public String cmdExportarClick () throws Exception {
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato);
			filename = "Polizas_Canceladas_A_Rehabilitar." + exportFormat.getExtension();
			TableModelExport model = rehabilitacionMasivaManager.getModel(dsAsegurado, dsAseguradora, dsProducto, nmPoliza, nmInciso);
			inputStream = exportFormat.export(model);
			success = true;
		} catch (Exception e) {
			success = false;
		}
		return SUCCESS;
	}

	//SETTERS y GETTERS
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDsAsegurado() {
		return dsAsegurado;
	}

	public void setDsAsegurado(String dsAsegurado) {
		this.dsAsegurado = dsAsegurado;
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

	public String getNmPoliza() {
		return nmPoliza;
	}

	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}

	public String getNmInciso() {
		return nmInciso;
	}

	public void setNmInciso(String nmInciso) {
		this.nmInciso = nmInciso;
	}

	public void setRehabilitacionMasivaManager(
			RehabilitacionMasivaManager rehabilitacionMasivaManager) {
		this.rehabilitacionMasivaManager = rehabilitacionMasivaManager;
	}

	public List<RehabilitacionMasivaVO> getListaRehabilitacionMasiva() {
		return listaRehabilitacionMasiva;
	}

	public void setListaRehabilitacionMasiva(
			List<RehabilitacionMasivaVO> listaRehabilitacionMasiva) {
		this.listaRehabilitacionMasiva = listaRehabilitacionMasiva;
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

	public String getIdRegresar() {
		return idRegresar;
	}

	public void setIdRegresar(String idRegresar) {
		this.idRegresar = idRegresar;
	}
}
