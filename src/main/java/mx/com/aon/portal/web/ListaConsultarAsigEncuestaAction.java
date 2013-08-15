package mx.com.aon.portal.web;

import mx.com.aon.portal.model.TreeViewVO;
import mx.com.aon.portal.service.ConfiguracionAlertasAutomaticoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;
import mx.com.aon.catbo.service.ConsultarAsigEncuestaManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;

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
public class ListaConsultarAsigEncuestaAction extends AbstractListAction{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaConsultarAsigEncuestaAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient ConsultarAsigEncuestaManager consultarAsigEncuestaManager;
	private List<ConsultarAsigEncuestaManager> mConsularAsignacionEncuestaList;
	private String dsUnieco;
	private String dsRamo;
	private String estado;
	private String nmPoliza;
	private String dsPerson;
	private String dsUsuari;
	private String nombreCliente;
	private String nombreUsuario;
	private String cdUsusari;//cdUsusari
	private String cdPerson;
	private String cdUsuario;
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
            PagedList pagedList = this.consultarAsigEncuestaManager.obtenerAsigEncuesta(nmPoliza, dsPerson, dsUsuari, start, limit);
            mConsularAsignacionEncuestaList = pagedList.getItemsRangeList();
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
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			contentType = Util.getContentType(formato);
			filename = "ConfAlert." + exportFormat.getExtension();
			
			TableModelExport model = consultarAsigEncuestaManager.obtieneArchivoExport(nmPoliza, dsPerson, dsUsuari);
 			 
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
	

	/*public String cmdGetListaVariables () {
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
*/
	

	

	
	


	public List<ConsultarAsigEncuestaManager> getMConsularAsignacionEncuestaList() {
		return mConsularAsignacionEncuestaList;
	}


	public void setMConsularAsignacionEncuestaList(
			List<ConsultarAsigEncuestaManager> configuracionAlertasAutomaticoList) {
		mConsularAsignacionEncuestaList = configuracionAlertasAutomaticoList;
	}

	
	

	public ExportMediator getExportMediator() {
		return exportMediator;
	}


	public void setConsultarAsigEncuestaManager(
			ConsultarAsigEncuestaManager consultarAsigEncuestaManager) {
		this.consultarAsigEncuestaManager = consultarAsigEncuestaManager;
	}

	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}	

	public ConsultarAsigEncuestaManager obtenConsultarAsigEncuestaManager() {
			return consultarAsigEncuestaManager;
		}


	public List getElementosTreeView() {
		return elementosTreeView;
	}


	public void setElementosTreeView(List elementosTreeView) {
		this.elementosTreeView = elementosTreeView;
	}


	public String getDsUnieco() {
		return dsUnieco;
	}


	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
	}


	public String getDsRamo() {
		return dsRamo;
	}


	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}


	
	public String getNmPoliza() {
		return nmPoliza;
	}


	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}


	public String getDsPerson() {
		return dsPerson;
	}


	public void setDsPerson(String dsPerson) {
		this.dsPerson = dsPerson;
	}


	public String getDsUsuari() {
		return dsUsuari;
	}


	public void setDsUsuari(String dsUsuari) {
		this.dsUsuari = dsUsuari;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getNombreCliente() {
		return nombreCliente;
	}


	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}


	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}


	public String getCdUsusari() {
		return cdUsusari;
	}


	public void setCdUsusari(String cdUsusari) {
		this.cdUsusari = cdUsusari;
	}


	public String getCdPerson() {
		return cdPerson;
	}


	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}


	public String getCdUsuario() {
		return cdUsuario;
	}


	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}


	

}