package mx.com.aon.portal.web;

import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.DescuentoVO;
import mx.com.aon.portal.service.DescuentosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;



/**
 * Action que atiende las peticiones de la pantalla descuento.
 *
 */
@SuppressWarnings("serial")
public class ListaDescuentosAction extends AbstractListAction {
	
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaDescuentosAction.class);

	private String dsDescuento;

	private String otValor;

	private String dsCliente;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo DescuentoVO
	 * con los valores de la consulta.
	 */
	private List<DescuentoVO> mDescuentoList;
	
	/**
	 * Manager con la implementacion de Endpoint para la consulta a BD
	 * Este objeto no sera serializado.
	 */
	private transient DescuentosManager descuentosManager;
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
	 *  Obtiene un conjunto de descuento y los mustra en el grid de descuentos.
	 *  
	 * @return success
	 *  
	 * @throws Exception
	 */	
    @SuppressWarnings("unchecked")
	public String execute()throws Exception{
		try
		{
			PagedList pagedList  = descuentosManager.buscarDescuentos(start, limit, dsDescuento, otValor, dsCliente);
			mDescuentoList = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = true;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
    
    
    /**
	 * Exporta el resultado mostrado en el grid de la pantalla descuento en Formato PDF, Excel, etc.
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
			
			filename = "Descuentos." + exportFormat.getExtension();
			
			TableModelExport model = descuentosManager.getModel(dsDescuento, otValor, dsCliente);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
	
	public String getDsDescuento() {
		return dsDescuento;
	}

	public void setDsDescuento(String dsDescuento) {
		this.dsDescuento = dsDescuento;
	}

	public String getOtValor() {
		return otValor;
	}

	public void setOtValor(String otValor) {
		this.otValor = otValor;
	}

	public String getDsCliente() {
		return dsCliente;
	}

	public void setDsCliente(String dsCliente) {
		this.dsCliente = dsCliente;
	}

	public List<DescuentoVO> getMDescuentoList() {
		return mDescuentoList;
	}

	public void setMDescuentoList(List<DescuentoVO> descuentoList) {
		mDescuentoList = descuentoList;
	}

	public void setDescuentosManager(DescuentosManager descuentosManager) {
		this.descuentosManager = descuentosManager;
	}
	
	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}
}