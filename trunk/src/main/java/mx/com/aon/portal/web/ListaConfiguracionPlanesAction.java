package mx.com.aon.portal.web;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.PlanesMPlanProVO;
import mx.com.aon.portal.service.MantenimientoPlanesManager;
import mx.com.aon.portal.service.PagedList;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Configuracion de Planes.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaConfiguracionPlanesAction extends AbstractListAction {
	
	private static final long serialVersionUID = 8106543262330598802L;
	private transient MantenimientoPlanesManager mantenimientoPlanesManager;
	private List<PlanesMPlanProVO> mPlanesMPlanPro;
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaConfiguracionPlanesAction.class);
	private String codigoProducto;
	private String codigoPlan;
	private String tipoSituacion;
	private String codigoGarantia;
	private String codigoObligatorio;

	/**
	 * Metodo que ejecuta la busqueda de planes y obtiene un conjunto de registro para listar en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscar ()throws Exception {
		try {
			PlanesMPlanProVO planesMPlanProVO = new PlanesMPlanProVO();
			planesMPlanProVO.setCdRamo(codigoProducto);
			planesMPlanProVO.setCdPlan(codigoPlan);
			planesMPlanProVO.setCdTipSit(tipoSituacion);
			planesMPlanProVO.setCdGarant(codigoGarantia);
			planesMPlanProVO.setSwOblig(codigoObligatorio);
			
			PagedList pagedList = mantenimientoPlanesManager.buscarPlanes(start, limit, planesMPlanProVO);
			mPlanesMPlanPro = pagedList.getItemsRangeList();
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

	public List<PlanesMPlanProVO> getMPlanesMPlanPro() {
		return mPlanesMPlanPro;
	}

	public void setMPlanesMPlanPro(List<PlanesMPlanProVO> planesMPlanPro) {
		mPlanesMPlanPro = planesMPlanPro;
	}

	public MantenimientoPlanesManager obtenMantenimientoPlanesManager() {
		return mantenimientoPlanesManager;
	}

	public void setMantenimientoPlanesManager(
			MantenimientoPlanesManager mantenimientoPlanesManager) {
		this.mantenimientoPlanesManager = mantenimientoPlanesManager;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getCodigoPlan() {
		return codigoPlan;
	}

	public void setCodigoPlan(String codigoPlan) {
		this.codigoPlan = codigoPlan;
	}

	public String getTipoSituacion() {
		return tipoSituacion;
	}

	public void setTipoSituacion(String tipoSituacion) {
		this.tipoSituacion = tipoSituacion;
	}

	public String getCodigoGarantia() {
		return codigoGarantia;
	}

	public void setCodigoGarantia(String codigoGarantia) {
		this.codigoGarantia = codigoGarantia;
	}


	public String getCodigoObligatorio() {
		return codigoObligatorio;
	}


	public void setCodigoObligatorio(String codigoObligatorio) {
		this.codigoObligatorio = codigoObligatorio;
	}

}
