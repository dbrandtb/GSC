package mx.com.aon.portal.web;

import java.io.InputStream;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.CatalogoLogicoVO;
import mx.com.aon.portal.service.ManttoCatalogosLogicosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;

import org.apache.log4j.Logger;

public class ListaRegistrosCatalogosLogicosAction extends AbstractListAction {

	private static final long serialVersionUID = 198733334446679879L;

	private static Logger logger = Logger.getLogger(ListaRegistrosCatalogosLogicosAction.class);

	private transient ManttoCatalogosLogicosManager manttoCatalogosLogicosManager;
	
	private List<CatalogoLogicoVO> mListaCatalogosLogicos;
	
	private String cdTabla;
	private String cdRegion;
	private String cdIdioma;
	private String codigo;
	private String descripcion;
	private boolean success;	

	private ExportMediator exportMediator;
	private String formato;
	private String filename;
	private InputStream inputStream;
	

	/**
	 * Metodo que realiza la búsqueda de registros
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws ApplicationException {
		try{
			PagedList pagedList= manttoCatalogosLogicosManager.obtenerListadoCatalogosLogicos(cdTabla, cdRegion, cdIdioma, codigo, descripcion, start, limit);
			mListaCatalogosLogicos = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){
            success = false;
            addActionError(ae.getMessage());
            return SUCCESS;
        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	/**
	 * Obtiene un conjunto de estructuras y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportClick()throws Exception{		
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
			TableModelExport model = manttoCatalogosLogicosManager.getModel(cdTabla, cdRegion, cdIdioma, codigo, descripcion);			
			inputStream = exportFormat.export(model);			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}		
		return SUCCESS;
	}	

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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

	public String getCdTabla() {
		return cdTabla;
	}

	public void setCdTabla(String cdTabla) {
		this.cdTabla = cdTabla;
	}

	public String getCdRegion() {
		return cdRegion;
	}

	public void setCdRegion(String cdRegion) {
		this.cdRegion = cdRegion;
	}

	public String getCdIdioma() {
		return cdIdioma;
	}

	public void setCdIdioma(String cdIdioma) {
		this.cdIdioma = cdIdioma;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setManttoCatalogosLogicosManager(
			ManttoCatalogosLogicosManager manttoCatalogosLogicosManager) {
		this.manttoCatalogosLogicosManager = manttoCatalogosLogicosManager;
	}

	public List<CatalogoLogicoVO> getMListaCatalogosLogicos() {
		return mListaCatalogosLogicos;
	}

	public void setMListaCatalogosLogicos(
			List<CatalogoLogicoVO> listaCatalogosLogicos) {
		mListaCatalogosLogicos = listaCatalogosLogicos;
	}
}
