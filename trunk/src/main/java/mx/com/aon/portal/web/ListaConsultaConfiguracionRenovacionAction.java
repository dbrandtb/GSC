package mx.com.aon.portal.web;

import java.io.InputStream;
import java.util.List;

import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConsultaConfiguracionRenovacionVO;
import mx.com.aon.portal.service.ConfiguracionRenovacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Configuracion de la renovacion.
 *   
 *   @extends AbstractListAction
 * 
 */

public class ListaConsultaConfiguracionRenovacionAction extends AbstractListAction{

	private static final long serialVersionUID = 1934487945234543L;

	private static Logger logger = Logger.getLogger(ListaConsultaConfiguracionRenovacionAction.class);

	private transient ConfiguracionRenovacionManager configuracionRenovacionManager;

	private List<ConsultaConfiguracionRenovacionVO> confRenovaEstructuraList;

	private String dsElemen;
	private String dsTipoRenova;
	private String dsUniEco;
	private String dsRamo;
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
	 * Metodo que busca y obtiene un conjunto de registros para cargar e grid en la pantalla de 
	 * configuracion de la renovacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = configuracionRenovacionManager.obtenerClientesYTiposDeRenovacion(dsElemen, dsTipoRenova, dsUniEco, dsRamo, start, limit);
            confRenovaEstructuraList = pagedList.getItemsRangeList();
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
	 * Obtiene un conjunto de registros de renovacion y exporta el resultado en Formato PDF, Excel, CSV, etc.
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
			
			filename = "Lista de clientes y tipos de renovacion." + exportFormat.getExtension();
			
			TableModelExport model = configuracionRenovacionManager.getModel(dsElemen, dsTipoRenova, dsUniEco, dsRamo);
			 
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

	public void setConfiguracionRenovacionManager(
			ConfiguracionRenovacionManager configuracionRenovacionManager) {
		this.configuracionRenovacionManager = configuracionRenovacionManager;
	}

	public List<ConsultaConfiguracionRenovacionVO> getConfRenovaEstructuraList() {
		return confRenovaEstructuraList;
	}

	public void setConfRenovaEstructuraList(
			List<ConsultaConfiguracionRenovacionVO> confRenovaEstructuraList) {
		this.confRenovaEstructuraList = confRenovaEstructuraList;
	}

	public String getDsElemen() {
		return dsElemen;
	}

	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}

	public String getDsTipoRenova() {
		return dsTipoRenova;
	}

	public void setDsTipoRenova(String dsTipoRenova) {
		this.dsTipoRenova = dsTipoRenova;
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
}
