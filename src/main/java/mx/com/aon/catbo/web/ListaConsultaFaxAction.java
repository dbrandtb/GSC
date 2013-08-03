package mx.com.aon.catbo.web;

import java.util.List;
import java.io.InputStream;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.FaxesVO;

import mx.com.aon.catbo.service.ArchivosFaxesManager;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;


import org.apache.log4j.Logger;



/**
 * Action que atiende las peticiones de que vienen de la pantalla Notificaciones
 * 
 */
@SuppressWarnings("serial")
public class ListaConsultaFaxAction extends AbstractListAction {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaConsultaFaxAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	// private transient AdministracionAtributosDeFaxManager
	// administracionAtributosDeFaxManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo
	 * con los valores de la consulta.
	 */
	// private List<AdministraFaxVO> mEstructuraList;
	private String dsNomFormato;
	private String cdtipoar;


	private String dsarchivo;
	private String nmfax;
	private String nmpoliex;
	private String nmcaso;
	private String uregistro;
	private String feingreso;

	private int cdVariable;
	private String flag;
	
	private transient ArchivosFaxesManager archivosFaxesManager;


	private List<FaxesVO> mListConsultaFax;
	
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
	 * Metodo que realiza la busqueda de notificaciones en base a en base a
	 * codigo notificacion, descripcion notificacion, descripcion mensaje,
	 * codigo formato, codigo metodo Envio
	 * 
	 * @param dsFormato
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */

	@SuppressWarnings("unchecked")
	public String cmdbuscarFax() throws Exception {
		try {

			PagedList pagedList = this.archivosFaxesManager.obtenerFaxes(dsarchivo, nmcaso, nmfax, nmpoliex, start, limit);
			mListConsultaFax = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();

			success = true;
			return SUCCESS;
		} catch (ApplicationException e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;

		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}

	public String cmdBorrarFax() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.archivosFaxesManager.BorrarFax(nmfax);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
          }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	
	/**
	 * Metodo que busca un conjunto de formatos documentos y exporta el
	 * resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	
	
	public String cmdExportarFax() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		try {
			
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			contentType = Util.getContentType(formato);
			filename = "Formatosfax." + exportFormat.getExtension();
			
			TableModelExport model = archivosFaxesManager.getModel(dsarchivo, nmcaso, nmfax, nmpoliex);
 			 
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}

	
  public String cmdIrAConsultaFaxClick()throws Exception{
   		return "irPantallaConsultaFax";
   	}

  
  // Ir a la pantalla consulta Casos
  	
  public String cmdIrAConsultaCasosClick()throws Exception{
   		return "irPantallaConsultaCasos";
   	}

	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}	
	
	public String getDsNomFormato() {
		return dsNomFormato;
	}

	public void setDsNomFormato(String dsNomFormato) {
		this.dsNomFormato = dsNomFormato;
	}

	public String getCdtipoar() {
		return cdtipoar;
	}

	public void setCdtipoar(String cdtipoar) {
		this.cdtipoar = cdtipoar;
	}

	public String getNmcaso() {
		return nmcaso;
	}

	public void setNmcaso(String nmcaso) {
		this.nmcaso = nmcaso;
	}

	public String getNmfax() {
		return nmfax;
	}

	public void setNmfax(String nmfax) {
		this.nmfax = nmfax;
	}

	public String getNmpoliex() {
		return nmpoliex;
	}

	public void setNmpoliex(String nmpoliex) {
		this.nmpoliex = nmpoliex;
	}


	public String getDsarchivo() {
		return dsarchivo;
	}

	public void setDsarchivo(String dsarchivo) {
		this.dsarchivo = dsarchivo;
	}

	public String getUregistro() {
		return uregistro;
	}

	public void setUregistro(String uregistro) {
		this.uregistro = uregistro;
	}

	public String getFeingreso() {
		return feingreso;
	}

	public void setFeingreso(String feingreso) {
		this.feingreso = feingreso;
	}

	public void setArchivosFaxesManager(
			ArchivosFaxesManager archivosFaxesManager) {
		this.archivosFaxesManager = archivosFaxesManager;
	}

	public List<FaxesVO> getMListConsultaFax() {
		return mListConsultaFax;
	}

	public void setMListConsultaFax(List<FaxesVO> listConsultaFax) {
		mListConsultaFax = listConsultaFax;
	}

	public int getCdVariable() {
		return cdVariable;
	}

	public void setCdVariable(int cdVariable) {
		this.cdVariable = cdVariable;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	
}