package mx.com.aon.portal.web;

import mx.com.aon.portal.model.TreeViewVO;
import mx.com.aon.portal.service.ConfiguracionAlertasAutomaticoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;


import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Configuracion de alertas automatico.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaConfiguracionAlertasAutomaticoAction extends AbstractListAction{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaConfiguracionAlertasAutomaticoAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient ConfiguracionAlertasAutomaticoManager configuracionAlertasAutomaticoManager;
	private List<ConfiguracionAlertasAutomaticoManager> mConfiguracionAlertasAutomaticoList;
	private String dsUsuario;
	private String dsCliente;
	private String dsProceso;
	private String dsRamo;
	private String dsAseguradora;
	private String dsRol;
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
	 * Almacena la lista de elementos de Variables necesarios en el TreeView
	 */
	private List<TreeViewVO> elementosTreeView;

	/**
	 * Metodo que ejecuta la busqueda de un conjunto de registros para el grid de la pantalla.
	 * 
	 * @return success 
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = this.configuracionAlertasAutomaticoManager.buscarConfiguracionAlertasAutomatico(dsUsuario, dsCliente, dsProceso, dsRamo, dsAseguradora, dsRol, start, limit);
            mConfiguracionAlertasAutomaticoList = pagedList.getItemsRangeList();
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
	 * Obtiene un conjunto de registros de alertas automaticos y exporta el resultado en Formato PDF, Excel, CSV, etc.
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
			
			filename = "ConfAlert." + exportFormat.getExtension();
			
			TableModelExport model = configuracionAlertasAutomaticoManager.getModel(dsUsuario, dsCliente, dsProceso, dsRamo, dsAseguradora, dsRol);
			 
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
	

	public String cmdGetListaVariables () {
		try {
			elementosTreeView = (List<TreeViewVO>)configuracionAlertasAutomaticoManager.obtieneVariables();
			for (TreeViewVO treeViewVO : elementosTreeView) {
				treeViewVO.setType("location");
				treeViewVO.setCls("leaf");
				treeViewVO.setLeaf(true);
				treeViewVO.setId(treeViewVO.getText());
			}
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

	public String getDsCliente() {
		return dsCliente;
	}


	public void setDsCliente(String dsCliente) {
		this.dsCliente = dsCliente;
	}


	public String getDsProceso() {
		return dsProceso;
	}


	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}


	public String getDsRamo() {
		return dsRamo;
	}


	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}


	public String getDsAseguradora() {
		return dsAseguradora;
	}


	public void setDsAseguradora(String dsAseguradora) {
		this.dsAseguradora = dsAseguradora;
	}


	public String getDsRol() {
		return dsRol;
	}


	public void setDsRol(String dsRol) {
		this.dsRol = dsRol;
	}


	public List<ConfiguracionAlertasAutomaticoManager> getMConfiguracionAlertasAutomaticoList() {
		return mConfiguracionAlertasAutomaticoList;
	}


	public void setMConfiguracionAlertasAutomaticoList(
			List<ConfiguracionAlertasAutomaticoManager> configuracionAlertasAutomaticoList) {
		mConfiguracionAlertasAutomaticoList = configuracionAlertasAutomaticoList;
	}

	public String getDsUsuario() {
		return dsUsuario;
	}


	public void setDsUsuario(String dsUsuario) {
		this.dsUsuario = dsUsuario;
	}

	

	public ExportMediator getExportMediator() {
		return exportMediator;
	}


	public void setConfiguracionAlertasAutomaticoManager(
			ConfiguracionAlertasAutomaticoManager configuracionAlertasAutomaticoManager) {
		this.configuracionAlertasAutomaticoManager = configuracionAlertasAutomaticoManager;
	}

	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}	

	public ConfiguracionAlertasAutomaticoManager getConfiguracionAlertasAutomaticoManager() {
			return configuracionAlertasAutomaticoManager;
		}


	public List getElementosTreeView() {
		return elementosTreeView;
	}


	public void setElementosTreeView(List elementosTreeView) {
		this.elementosTreeView = elementosTreeView;
	}
}