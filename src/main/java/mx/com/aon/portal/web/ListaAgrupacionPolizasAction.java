package mx.com.aon.portal.web;

import org.apache.log4j.Logger;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.AgrupacionPolizasManager;
import mx.com.aon.portal.model.AgrupacionPolizaVO;
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
public class ListaAgrupacionPolizasAction extends AbstractListAction{

	private static final long serialVersionUID = 1644454515546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaConfigurarEstructuraAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient AgrupacionPolizasManager  agrupacionPolizasManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AgrupacionPolizaVO
	 * con los valores de la consulta.
	 */
	private List<AgrupacionPolizaVO> listaPolizas;
	private String cliente;
	private String tipoRamo;
	private String tipoAgrupacion;
	private String aseguradora;
	private String producto;
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
	 * Metodo que realiza la busqueda de un conjunto de registros de agrupacion de polizas.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
    @SuppressWarnings("unchecked")
	public String cmdBuscarClick () {
		try {
    		PagedList pagedList = agrupacionPolizasManager.buscarAgrupacionPolizas(cliente,tipoRamo,tipoAgrupacion,aseguradora,producto,start,limit);
			listaPolizas = pagedList.getItemsRangeList();
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
	 * Obtiene un conjunto de agrupacion de polizas y exporta el resultado en Formato PDF, Excel, CSV, etc.
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
			
			TableModelExport model = agrupacionPolizasManager.getModel(cliente, tipoRamo, tipoAgrupacion, aseguradora, producto);
			
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
	
	public void setAgrupacionPolizasManager(AgrupacionPolizasManager agrupacionPolizasManager) {
        this.agrupacionPolizasManager = agrupacionPolizasManager;
    }
	
    public List<AgrupacionPolizaVO> getListaPolizas() {
        return listaPolizas;
    }

    public void setListaPolizas(List<AgrupacionPolizaVO> listaPolizas) {
        this.listaPolizas = listaPolizas;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTipoRamo() {
        return tipoRamo;
    }

    public void setTipoRamo(String tipoRamo) {
        this.tipoRamo = tipoRamo;
    }

    public String getTipoAgrupacion() {
        return tipoAgrupacion;
    }

    public void setTipoAgrupacion(String tipoAgrupacion) {
        this.tipoAgrupacion = tipoAgrupacion;
    }

    public String getAseguradora() {
        return aseguradora;
    }

    public void setAseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}
}
