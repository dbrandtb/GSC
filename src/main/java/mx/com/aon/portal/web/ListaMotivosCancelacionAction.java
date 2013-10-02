package mx.com.aon.portal.web;

import mx.com.aon.portal.model.MotivoCancelacionVO;
import mx.com.aon.portal.model.RequisitoCancelacionVO;
import mx.com.aon.portal.service.MotivosCancelacionManager;
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
 *   la tabla con mecanismo de paginacion de la pantalla Motivos de Cancelacion.
 *   
 *   @extends AbstractListAction
 * 
 */@SuppressWarnings("serial")
public class ListaMotivosCancelacionAction extends AbstractListAction{

	private static Logger logger = Logger.getLogger(ListaConfigurarEstructuraAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient MotivosCancelacionManager motivosCancelacionManager;

	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo
	 * con los valores de la consulta.
	 */
	private List<MotivoCancelacionVO> mcEstructuraList;
	private List<RequisitoCancelacionVO> rcEstructuraList;

	/**
	 * los campos de motivos cancelacion
	 */

	private String cdRazon;
	private String dsRazon;
	
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
	 * Ejecuta la busqueda para el llenado del grid del motivos de cancelacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = this.motivosCancelacionManager.buscarMotivosCancelacion(cdRazon, dsRazon, start, limit);
            mcEstructuraList = pagedList.getItemsRangeList();
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
	 * Ejecuta la busqueda para el llenado del grid de requisitos de cancelacion.
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtenBuscarClick() throws Exception{
		try{
            PagedList pagedList = this.motivosCancelacionManager.getRequisitoCancelacion(cdRazon, start, limit);
            rcEstructuraList = pagedList.getItemsRangeList();
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
	 * Exporta el contenido del grid de motivos de cancelacion en Formato PDF, Excel, etc.
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public String cmdExportarMotivosCancelacionClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {

            contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			
			filename = "Motivos Cancelacion." + exportFormat.getExtension();
			
			TableModelExport model = motivosCancelacionManager.getModel(cdRazon, dsRazon);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
	
	public void setMotivosCancelacionManager(
			MotivosCancelacionManager motivosCancelacionManager) {
		this.motivosCancelacionManager = motivosCancelacionManager;
	}
	
	public List<MotivoCancelacionVO> getMcEstructuraList() {
		return mcEstructuraList;
	}

	public void setMcEstructuraList(List<MotivoCancelacionVO> mcEstructuraList) {
		this.mcEstructuraList = mcEstructuraList;
	}

	public List<RequisitoCancelacionVO> getRcEstructuraList() {
		return rcEstructuraList;
	}

	public void setRcEstructuraList(List<RequisitoCancelacionVO> rcEstructuraList) {
		this.rcEstructuraList = rcEstructuraList;
	}

	public String getCdRazon() {
		return cdRazon;
	}

	public void setCdRazon(String cdRazon) {
		this.cdRazon = cdRazon;
	}

	public String getDsRazon() {
		return dsRazon;
	}

	public void setDsRazon(String dsRazon) {
		this.dsRazon = dsRazon;
	}
	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}
}
