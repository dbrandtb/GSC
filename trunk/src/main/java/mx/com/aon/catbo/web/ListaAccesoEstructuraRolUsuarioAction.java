package mx.com.aon.catbo.web;

import java.io.InputStream;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.AccesoEstructuraRolUsuarioVO;
import mx.com.aon.catbo.service.AccesoEstructuraRolUsuarioManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;

import org.apache.log4j.Logger;

public class ListaAccesoEstructuraRolUsuarioAction extends AbstractListAction {

	private static final long serialVersionUID = -792632067915334348L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaAccesoEstructuraRolUsuarioAction.class);

	private transient AccesoEstructuraRolUsuarioManager accesoEstructuraRolUsuarioManager;

	private List<AccesoEstructuraRolUsuarioVO> listaDatos; 
	private String dsNivel;
	private String dsRol;
	private String dsUsuario;
	private String dsEstado;
	private String formato;
	private String filename;
	private InputStream inputStream;
	ExportMediator exportMediator;

	public ExportMediator getExportMediator() {
		return exportMediator;
	}

	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws ApplicationException {
		try {
			PagedList pagedList = accesoEstructuraRolUsuarioManager.buscarAccesos(dsNivel, dsRol, dsUsuario, dsEstado, start, limit);
			listaDatos = pagedList.getItemsRangeList();
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
	
	/**
	 * Exporta el resultado mostrado en el grid de la pantalla en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	public String cmdExportarEstructuraRolUsuarioClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Informe." + exportFormat.getExtension();
			TableModelExport model =  accesoEstructuraRolUsuarioManager.getModel(dsNivel, dsRol, dsUsuario, dsEstado);		
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}

	public List<AccesoEstructuraRolUsuarioVO> getListaDatos() {
		return listaDatos;
	}

	public void setListaDatos(List<AccesoEstructuraRolUsuarioVO> listaDatos) {
		this.listaDatos = listaDatos;
	}

	public String getDsNivel() {
		return dsNivel;
	}

	public void setDsNivel(String dsNivel) {
		this.dsNivel = dsNivel;
	}

	public String getDsRol() {
		return dsRol;
	}

	public void setDsRol(String dsRol) {
		this.dsRol = dsRol;
	}

	public String getDsUsuario() {
		return dsUsuario;
	}

	public void setDsUsuario(String dsUsuario) {
		this.dsUsuario = dsUsuario;
	}

	public String getDsEstado() {
		return dsEstado;
	}

	public void setDsEstado(String dsEstado) {
		this.dsEstado = dsEstado;
	}

	public void setAccesoEstructuraRolUsuarioManager(
			AccesoEstructuraRolUsuarioManager accesoEstructuraRolUsuarioManager) {
		this.accesoEstructuraRolUsuarioManager = accesoEstructuraRolUsuarioManager;
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
}
