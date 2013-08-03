package mx.com.aon.portal.web;

import java.io.InputStream;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.EjecutivoCuentaVO;
import mx.com.aon.portal.service.MantenimientoEjecutivosCuentaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;


import org.apache.log4j.Logger;

public class ListaMantenimientoEjecutivosCuentaAction extends AbstractListAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7902499661100480119L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaMantenimientoEjecutivosCuentaAction.class);

	private transient MantenimientoEjecutivosCuentaManager mantenimientoEjecutivosCuentaManager;

	private boolean success;

	private List<EjecutivoCuentaVO> listaEjecutivos;
	private List<EjecutivoCuentaVO> listaAtributosEjecutivos;

	/**
	 * Parametros recibidos desde el cliente
	 */
	private String cdEjecutivo;
	private String cdPerson;
	private String fechaInicial;
	private String fechaFinal;
	private String status;

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
	public String cmdBuscarEjecutivos () throws ApplicationException {
		try {
			PagedList pagedList = mantenimientoEjecutivosCuentaManager.buscarEjecutivosCuenta(cdEjecutivo, cdPerson, fechaInicial, fechaFinal, status, start, limit);
			listaEjecutivos = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}

	public String cmdObtenerAtributos () throws ApplicationException{
		try {
			PagedList pagedList = mantenimientoEjecutivosCuentaManager.obtenerAtributos(cdEjecutivo, start, limit);
			listaAtributosEjecutivos = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}

	public String cmdExportarEjecutivosClick() throws Exception {
		try {
			   contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato);
			filename = "Personas." + exportFormat.getExtension();
			TableModelExport model = mantenimientoEjecutivosCuentaManager.getModelEjecutivos(cdEjecutivo, cdPerson, fechaInicial, fechaFinal, status);
			inputStream = exportFormat.export(model);
			success = true;
		} catch (Exception e) {
			success = false;
		}
		return SUCCESS;
	}

	public String cmdExportarAtributosEjecutivosClick() throws Exception {
		try {
			   contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato);
			filename = "Personas." + exportFormat.getExtension();
			TableModelExport model = mantenimientoEjecutivosCuentaManager.getModelAtributosEjecutivos(cdEjecutivo);
			inputStream = exportFormat.export(model);
			success = true;
		} catch (Exception e) {
			success = false;
		}
		return SUCCESS;
	}
	public String cmdIrMantenimientoAtributos () {
		return "irMantenimientoAtributosEjecutivo";
	}

	public String getCdEjecutivo() {
		return cdEjecutivo;
	}

	public void setCdEjecutivo(String cdEjecutivo) {
		this.cdEjecutivo = cdEjecutivo;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setMantenimientoEjecutivosCuentaManager(
			MantenimientoEjecutivosCuentaManager mantenimientoEjecutivosCuentaManager) {
		this.mantenimientoEjecutivosCuentaManager = mantenimientoEjecutivosCuentaManager;
	}

	public List<EjecutivoCuentaVO> getListaEjecutivos() {
		return listaEjecutivos;
	}

	public void setListaEjecutivos(List<EjecutivoCuentaVO> listaEjecutivos) {
		this.listaEjecutivos = listaEjecutivos;
	}

	public List<EjecutivoCuentaVO> getListaAtributosEjecutivos() {
		return listaAtributosEjecutivos;
	}

	public void setListaAtributosEjecutivos(
			List<EjecutivoCuentaVO> listaAtributosEjecutivos) {
		this.listaAtributosEjecutivos = listaAtributosEjecutivos;
	}

	public String getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public ExportMediator getExportMediator() {
		return exportMediator;
	}

	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
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
}
