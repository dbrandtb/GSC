package mx.com.aon.portal.web;

import org.apache.log4j.Logger;


import mx.com.aon.portal.service.NumeroIncisosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.NumeroIncisoVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;


import java.io.InputStream;
import java.util.List;

/**
 * Action que atiende las peticiones de la pantalla numeros de incisos.
 *
 */
@SuppressWarnings("serial")
public class ListaNumeroIncisosAction extends AbstractListAction{


	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */

	private static Logger logger = Logger.getLogger(ListaNumeroIncisosAction.class);



	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient NumeroIncisosManager  numeroIncisosManager;


	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AgrupacionPolizaVO
	 * con los valores de la consulta.
	 */
	private List<NumeroIncisoVO> listaNumeroIncisos;

	private String dsUniEco;
	private String dsRamo;
	private String dsElemen;
	private String dsIndSitSubSit;
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
	 *  Obtiene un conjunto de numeros de incisos y los muestra en el grid de la pantalla de numeros de incisos.
	 *  
	 * @return success
	 *  
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick () {
		try {
    		PagedList pagedList = numeroIncisosManager.buscarNumerosInciso(dsUniEco, dsRamo, dsElemen, dsIndSitSubSit, start, limit);
    		listaNumeroIncisos = pagedList.getItemsRangeList();
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
	 * Exporta el resultado mostrado en el grid de la pantalla numeros de incisos en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
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
			
			filename = "NumerosInciso." + exportFormat.getExtension();
			
			TableModelExport model = numeroIncisosManager.getModel(dsUniEco, dsRamo, dsElemen, dsIndSitSubSit);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
	
    
    public NumeroIncisosManager obtenNumeroIncisosManager() {
		return numeroIncisosManager;
	}

	public void setNumeroIncisosManager(NumeroIncisosManager numeroIncisosManager) {
		this.numeroIncisosManager = numeroIncisosManager;
	}

	public List<NumeroIncisoVO> getListaNumeroIncisos() {
		return listaNumeroIncisos;
	}

	public void setListaNumeroIncisos(List<NumeroIncisoVO> listaNumeroIncisos) {
		this.listaNumeroIncisos = listaNumeroIncisos;
	}

	public String getDsUniEco() {
		return dsUniEco;
	}

	public void setDsUniEco(String dsUniEco) {
		this.dsUniEco = dsUniEco;
	}

	public String getDsRamo() {
		return dsRamo;
	}

	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}

	public String getDsElemen() {
		return dsElemen;
	}

	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}

	public String getDsIndSitSubSit() {
		return dsIndSitSubSit;
	}

	public void setDsIndSitSubSit(String dsIndSitSubSit) {
		this.dsIndSitSubSit = dsIndSitSubSit;
	}    

	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}
}
