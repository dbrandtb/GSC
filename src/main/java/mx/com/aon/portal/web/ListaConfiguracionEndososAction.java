package mx.com.aon.portal.web;

import mx.com.aon.portal.model.TipoSuplementoVO;
import mx.com.aon.portal.service.ConfiguracionEndososManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;
import mx.com.gseguros.exception.ApplicationException;


import org.apache.log4j.Logger;

import java.util.List;
import java.io.InputStream;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Configuracion de endosos.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaConfiguracionEndososAction extends AbstractListAction{

	private static final long serialVersionUID = 1934759475234543L;


	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaConfiguracionEndososAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient ConfiguracionEndososManager configuracionEndososManager;

	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans
	 * con los valores de la consulta.
	 */
	private List<TipoSuplementoVO> endososEstructuraList;

	private String cdTipSup;
	private String dsTipSup;
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
	 * ejecuta la busqueda para mostrar en la grilla la lista de los endosos
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = configuracionEndososManager.obtenerTiposSuplementos(start, limit, cdTipSup, dsTipSup);
            endososEstructuraList = pagedList.getItemsRangeList();
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
	 * Exporta la lista de endosos obtenida en Formato PDF, Excel, etc.
	 * @return
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
			filename = "Tipos Endosos." + exportFormat.getExtension();
			TableModelExport model = configuracionEndososManager.getModel(dsTipSup);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}

	
	public List<TipoSuplementoVO> getEndososEstructuraList() {
		return endososEstructuraList;
	}
	public void setEndososEstructuraList(
			List<TipoSuplementoVO> endososEstructuraList) {
		this.endososEstructuraList = endososEstructuraList;
	}
	public String getCdTipSup() {
		return cdTipSup;
	}
	public void setCdTipSup(String cdTipSup) {
		this.cdTipSup = cdTipSup;
	}
	public String getDsTipSup() {
		return dsTipSup;
	}
	public void setDsTipSup(String dsTipSup) {
		this.dsTipSup = dsTipSup;
	}
	public ExportMediator getExportMediator() {
		return exportMediator;
	}
	public void setConfiguracionEndososManager(
			ConfiguracionEndososManager configuracionEndososManager) {
		this.configuracionEndososManager = configuracionEndososManager;
	}
	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}

	
}
