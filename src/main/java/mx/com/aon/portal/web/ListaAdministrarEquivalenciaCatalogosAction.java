package mx.com.aon.portal.web;

import org.apache.log4j.Logger;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.AdministrarEquivalenciaCatalogosManager;
import mx.com.aon.portal.model.EquivalenciaCatalogosVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;


import java.io.InputStream;
import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Agrupacion de polizas.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaAdministrarEquivalenciaCatalogosAction extends AbstractListAction{

	private static final long serialVersionUID = 1644454515546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaAdministrarEquivalenciaCatalogosAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient AdministrarEquivalenciaCatalogosManager  administrarEquivalenciaCatalogosManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AgrupacionPolizaVO
	 * con los valores de la consulta.
	 */
	private List<EquivalenciaCatalogosVO> listaEquivalenciaCatalogos;
	private String cdPais;
	private String cdSistema;
	private String cdTablaAcw;
	private String cdTablaExt;
	private String indUso;
	private String dsUsoAcw;
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
	 * Metodo que realiza la busqueda de un conjunto de registros de Equivalencia de Catalogos.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
    @SuppressWarnings("unchecked")
	public String cmdBuscarClick () {
		try {
    		PagedList pagedList = administrarEquivalenciaCatalogosManager.buscarEquivalenciaCatalogos(cdPais, cdSistema, cdTablaAcw, cdTablaExt, indUso, dsUsoAcw, start, limit); 
    		listaEquivalenciaCatalogos = pagedList.getItemsRangeList();
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
	 * Obtiene un conjunto de Equivalencia de catalogos y exporta el resultado en Formato PDF, Excel, CSV, etc.
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
			
			filename = "AgrupacionPolizas." + exportFormat.getExtension();
			
			TableModelExport model = administrarEquivalenciaCatalogosManager.getModel(cdPais, cdSistema, cdTablaAcw, cdTablaExt, indUso, dsUsoAcw);
			logger.debug( "inputStream : " + inputStream );
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
	

	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}

	public void setAdministrarEquivalenciaCatalogosManager(
			AdministrarEquivalenciaCatalogosManager administrarEquivalenciaCatalogosManager) {
		this.administrarEquivalenciaCatalogosManager = administrarEquivalenciaCatalogosManager;
	}

	public String getCdPais() {
		return cdPais;
	}

	public void setCdPais(String cdPais) {
		this.cdPais = cdPais;
	}

	public String getCdSistema() {
		return cdSistema;
	}

	public void setCdSistema(String cdSistema) {
		this.cdSistema = cdSistema;
	}

	public String getCdTablaAcw() {
		return cdTablaAcw;
	}

	public void setCdTablaAcw(String cdTablaAcw) {
		this.cdTablaAcw = cdTablaAcw;
	}

	public String getCdTablaExt() {
		return cdTablaExt;
	}

	public void setCdTablaExt(String cdTablaExt) {
		this.cdTablaExt = cdTablaExt;
	}

	public String getIndUso() {
		return indUso;
	}

	public void setIndUso(String indUso) {
		this.indUso = indUso;
	}

	public String getDsUsoAcw() {
		return dsUsoAcw;
	}

	public void setDsUsoAcw(String dsUsoAcw) {
		this.dsUsoAcw = dsUsoAcw;
	}

	public List<EquivalenciaCatalogosVO> getListaEquivalenciaCatalogos() {
		return listaEquivalenciaCatalogos;
	}

	public void setListaEquivalenciaCatalogos(
			List<EquivalenciaCatalogosVO> listaEquivalenciaCatalogos) {
		this.listaEquivalenciaCatalogos = listaEquivalenciaCatalogos;
	}
}
