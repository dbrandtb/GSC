package mx.com.aon.portal.service.impl;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.PlanesMPlanProVO;
import mx.com.aon.portal.service.MantenimientoPlanesManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MantenimientoPlanesManagerImpl extends AbstractManager implements MantenimientoPlanesManager{
	
	/**
	 * Logger de la clase para monitoreo
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(MantenimientoPlanesManagerImpl.class);
	
	/**
	 * Busca planes a cargar a grilla paginada.
	 * Usa el Store Procedure PKG_CONFG_CUENTA.P_OBTIENE_PLANCIA
	 * 
	 * @param String codigoRamo, String codigoPlan
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList getPlanes (int start, int limit, String codigoRamo, String codigoPlan) throws ApplicationException {

        HashMap map = new HashMap();
        map.put("codigoplan", codigoPlan);
        map.put("codigoramo", codigoRamo);

        return pagedBackBoneInvoke(map, "P_OBTIENE_PLANCIA", start, limit);

	}
	

	/**
	 * Version sobrecargada, debido a que hay dos listados sobre planes,
	 * cada uno con parámetros de búsqueda distintos.
	 * Usa el Store Procedure PKG_CONFG_CUENTA.P_OBTIENE_PLANPRO.
	 * 
	 * @param PlanesMPlanProVO planesMPlanProVO
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList buscarPlanes(int start, int limit,PlanesMPlanProVO planesMPlanProVO) throws ApplicationException {

			HashMap map = new HashMap();
			map.put("cdramo", planesMPlanProVO.getCdRamo());
			map.put("cdplan", planesMPlanProVO.getCdPlan());
			map.put("cdtipsit", planesMPlanProVO.getCdTipSit());
			map.put("cdgarant", planesMPlanProVO.getCdGarant());

            return pagedBackBoneInvoke(map, "OBTENER_CONFIGURAR_PLAN", start, limit);
	}

	
	/**
	 * Inserta un nuevo plan. Usa el Store Procedure PKG_CONFG_CUENTA.P_INSERTA_PLANPRO.
	 * 
	 * @param PlanesMPlanProVO planesMPlanProVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String insertarPlan(PlanesMPlanProVO planesMPlanProVO) throws ApplicationException {

			HashMap map = new HashMap ();
			map.put("cdramo", planesMPlanProVO.getCdRamo());
			map.put("cdplan", planesMPlanProVO.getCdPlan());
			map.put("cdtipsit", planesMPlanProVO.getCdTipSit());
			map.put("cdgarant", planesMPlanProVO.getCdGarant());
			map.put("swoblig", planesMPlanProVO.getSwOblig());

            WrapperResultados res =  returnBackBoneInvoke(map,"INSERTAR_CONFIGURAR_PLAN");
            return res.getMsgText();

	}
	
	/**
	 * Metodo que realiza la actualizacion de plan existente que se ha sido modificado.
	 * Usa el Store Procedure PKG_CONFG_CUENTA.P_GUARDA_PLANPRO.
	 * 
	 * @param  PlanesMPlanProVO planesMPlanProVO.
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String actualizarPlan(PlanesMPlanProVO planesMPlanProVO) throws ApplicationException {

			HashMap map = new HashMap ();
			map.put("cdramo", planesMPlanProVO.getCdRamo());
			map.put("cdplan", planesMPlanProVO.getCdPlan());
			map.put("cdtipsit", planesMPlanProVO.getCdTipSit());
			map.put("cdgarant", planesMPlanProVO.getCdGarant());
			map.put("swoblig", planesMPlanProVO.getSwOblig());

            WrapperResultados res =  returnBackBoneInvoke(map,"ACTUALIZAR_CONFIGURAR_PLAN");
            return res.getMsgText();

	}
	
	
	/**
	 * Metodo que realiza la eliminacion d un plan seleccionado.
	 * Usa el Store Procedure PKG_CONFG_CUENTA.P_BORRAR_PLANPRO
	 * 
	 * @param PlanesMPlanProVO planesMPlanProVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String borrarPlan(PlanesMPlanProVO planesMPlanProVO) throws ApplicationException {

			HashMap map = new HashMap ();
			map.put("cdramo", planesMPlanProVO.getCdRamo());
			map.put("cdplan", planesMPlanProVO.getCdPlan());
			map.put("cdtipsit", planesMPlanProVO.getCdTipSit());
			map.put("cdgarant", planesMPlanProVO.getCdGarant());
			map.put("swoblig", planesMPlanProVO.getSwOblig());

            WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_PLANPRO");
            return res.getMsgText();
	}
	
	
	/**
	 * Metodo que obtiene un plan seleccionado.
	 * Usa el Store Procedure PKG_CONFG_CUENTA.P_OBTIENE_PLANPRO_REG
	 * 
	 * @param PlanesMPlanProVO planesMPlanProVO
	 * 
	 * @return PlanesMPlanProVO
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PlanesMPlanProVO getPlan(PlanesMPlanProVO planesMPlanProVO) throws ApplicationException {

			HashMap map = new HashMap();
			map.put("cdramo", planesMPlanProVO.getCdRamo());
			map.put("cdplan", planesMPlanProVO.getCdPlan());
			map.put("cdtipsit", planesMPlanProVO.getCdTipSit());
			map.put("cdgarant", planesMPlanProVO.getCdGarant());
            return (PlanesMPlanProVO)getBackBoneInvoke(map,"GET_CONFIGURAR_PLAN");
    }

	
	/**
	 * Metodo que realiza la busqueda de planes para exportar el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @param codigoProducto
	 * @param codigoPlan
	 * @param tipoSituacion
	 * @param garantia
	 * 
	 * @return TableModelExport: Tabla con datos a exportar
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String codigoProducto,String codigoPlan,String tipoSituacion,String garantia)throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("cdramo", codigoProducto);
		map.put("cdplan", codigoPlan);
		map.put("cdtipsit", tipoSituacion);
		map.put("cdgarant", garantia);
					
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_PLANES_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Tipo Situacion","Garantia","Obligatorio"});
		
		return model;
	}
}
