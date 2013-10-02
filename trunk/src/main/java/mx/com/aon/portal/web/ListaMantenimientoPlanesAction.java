package mx.com.aon.portal.web;

import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.PlanesMPlanProVO;
import mx.com.aon.portal.service.MantenimientoPlanesManager;
import mx.com.aon.portal.service.PagedList;
import org.apache.log4j.Logger;
import mx.com.aon.portal.util.Util;
import mx.com.gseguros.exception.ApplicationException;

import java.io.InputStream;
import java.util.List;


/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Mantenimiento de Planes.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaMantenimientoPlanesAction extends AbstractListAction {


	private transient MantenimientoPlanesManager mantenimientoPlanesManager; 
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaMantenimientoPlanesAction.class);
	private String codigoProducto;
	private String codigoPlan;
	private String tipoSituacion;
	private String garantia;
	private String descripcionProducto;
	private String descripcionPlan;
	private List<PlanesMPlanProVO> mMantenimientoPlanVO;
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
	 * Metodo que obtiene un conjunto de registro para mostrar como listado en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String cmdListarMantenimientoPlanes () throws Exception{
		try {
			PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO ();
			planesMPlanProVO.setCdRamo(codigoProducto);
			planesMPlanProVO.setCdPlan(codigoPlan);
			planesMPlanProVO.setCdTipSit(tipoSituacion);
			planesMPlanProVO.setCdGarant(garantia);
			PagedList pagedList = new PagedList();
			pagedList = mantenimientoPlanesManager.buscarPlanes(start, limit, planesMPlanProVO);
            this.mMantenimientoPlanVO = pagedList.getItemsRangeList();
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
	 * Obtiene un conjunto de registros y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportacionClick() throws Exception{
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
			
			TableModelExport model = mantenimientoPlanesManager.getModel( codigoProducto, codigoPlan, tipoSituacion,garantia);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Excepcion en Action Export",e);
		}
		return SUCCESS;
	}
	
	public void setCodigoProducto (String codigoProducto) {this.codigoProducto = codigoProducto;}
	public String  getCodigoProducto () {return this.codigoProducto;}
	public void setCodigoPlan (String codigoPlan) {this.codigoPlan = codigoPlan;}
	public String getCodigoPlan () {return this.codigoPlan;}

	public String getTipoSituacion() {
		return tipoSituacion;
	}
	public void setTipoSituacion(String tipoSituacion) {
		this.tipoSituacion = tipoSituacion;
	}
	public String getGarantia() {
		return garantia;
	}
	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}


    public List<PlanesMPlanProVO> getMMantenimientoPlanVO() {
        return mMantenimientoPlanVO;
    }

    public void setMMantenimientoPlanVO(List<PlanesMPlanProVO> mMantenimientoPlanVO) {
        this.mMantenimientoPlanVO = mMantenimientoPlanVO;
    }

    public void setMantenimientoPlanesManager(MantenimientoPlanesManager mantenimientoPlanesManager) {
        this.mantenimientoPlanesManager = mantenimientoPlanesManager;
    }
    
    public String irBuscarPlanes() {
    	return "listarDetallePlanes";
    }
	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}
	
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
}
