package mx.com.aon.portal.web;

import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.DetallePlanXClienteVO;
import mx.com.aon.portal.service.DetallePlanXClienteManager;
import mx.com.aon.portal.service.PagedList;
import org.apache.log4j.Logger;
import mx.com.aon.portal.util.Util;
import mx.com.gseguros.exception.ApplicationException;

import java.io.InputStream;
import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Planes por cliente.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaPlanesXClienteAction extends AbstractListAction{
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaPlanesXClienteAction.class);

	private transient DetallePlanXClienteManager detallePlanXClienteManager; 



	private String codigoCliente;
	private String codigoElemento;
	private String codigoProducto;
	private String codigoPlan;
	private String codigoTipoSituacion;
	private String codigoCobertura; // antes codigoGarantia
	private String codigoAseguradora;
	private String garantia;
	private String aseguradora;
    private String descripcionProducto;
    private String descripcionPlan;
    private List<DetallePlanXClienteVO> mDetallePlanXCliente;
    
 /**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
    /**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
    
    
	/**
	 * Metodo que realiza la busqueda de datos de periodos de gracia por clientes
	 * en base a codigo cliente, codigo elemento, codigo producto, codigo plan,
	 * codigo tipo situacion, garantia, aseguradora 
	 * 
	 * @param codigoCliente
	 * @param codigoElemento
	 * @param codigoProducto
	 * @param codigoPlan
	 * @param codigoTipoSituacion
	 * @param garantia
	 * @param aseguradora
	 *  
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarPlanXCliente() {
		try {
			PagedList pagedList = detallePlanXClienteManager.buscarDetallePlanes (start, limit, codigoElemento/*SI*/, codigoProducto/*SI*/, codigoPlan/*SI*/, codigoTipoSituacion/*SI*/, /*garantia*/codigoCobertura, /*aseguradora*/codigoAseguradora);
			this.mDetallePlanXCliente = pagedList.getItemsRangeList();
			this.totalCount = pagedList.getTotalItems();
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
	 * Metodo que busca un conjunto de periodos de gracia por cliente 
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
   public String cmdExportPlanesClienteClick(){
		
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Recibos." + exportFormat.getExtension();
			TableModelExport model = detallePlanXClienteManager.getModel(codigoElemento, codigoProducto, codigoPlan, codigoTipoSituacion, /*garantia*/codigoCobertura, /*aseguradora*/codigoAseguradora);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}

   /**
    * Redireccion  
    * @return
    */
   public String irBuscarPlanesCliente() {
       return "buscarPlanesCliente";
   }
   
   
   public void setDetallePlanXClienteManager(DetallePlanXClienteManager detallePlanXClienteManager) {
       this.detallePlanXClienteManager = detallePlanXClienteManager;
   }

	public void setCodigoCliente (String codigoCliente) {this.codigoCliente = codigoCliente;}
	public String getCodigoCliente () {return this.codigoCliente;}
	public void setCodigoProducto (String codigoProducto) {this.codigoProducto = codigoProducto;}
	public String getCodigoProducto () {return this.codigoProducto;}
	public void setCodigoPlan (String codigoPlan) {this.codigoPlan = codigoPlan;}
	public String getCodigoPlan () {return this.codigoPlan;}
	public void setCodigoTipoSituacion (String codigoTipoSituacion) {this.codigoTipoSituacion = codigoTipoSituacion;}
	public String getCodigoTipoSituacion () {return this.codigoTipoSituacion;}
	public void setGarantia (String garantia) {this.garantia = garantia;}
	public String getGarantia () {return this.garantia;}
	
	
	public void setMDetallePlanXCliente (List<DetallePlanXClienteVO> mDetallePlanXCliente) {this.mDetallePlanXCliente = mDetallePlanXCliente;}
	public List<DetallePlanXClienteVO> getMDetallePlanXCliente () {return this.mDetallePlanXCliente;}



	public String getCodigoElemento() {
		return codigoElemento;
	}


	public void setCodigoElemento(String codigoElemento) {
		this.codigoElemento = codigoElemento;
	}


	public String getAseguradora() {
		return aseguradora;
	}


	public void setAseguradora(String aseguradora) {
		this.aseguradora = aseguradora;
	}


    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getDescripcionPlan() {
        return descripcionPlan;
    }

    public void setDescripcionPlan(String descripcionPlan) {
        this.descripcionPlan = descripcionPlan;
    }
	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}

	public String getCodigoCobertura() {
		return codigoCobertura;
	}

	public void setCodigoCobertura(String codigoCobertura) {
		this.codigoCobertura = codigoCobertura;
	}

	public String getCodigoAseguradora() {
		return codigoAseguradora;
	}

	public void setCodigoAseguradora(String codigoAseguradora) {
		this.codigoAseguradora = codigoAseguradora;
	}

}