package mx.com.aon.portal.service.impl;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.AseguradoraVO;
import mx.com.aon.portal.model.DetallePlanXClienteVO;
import mx.com.aon.portal.model.TiposCoberturasVO;
import mx.com.aon.portal.model.TiposSituacionVO;
import mx.com.aon.portal.service.DetallePlanXClienteManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * clase que implementa los servicios de DetallePlanXClienteManager
 * 
 *
 */
public class DetallePlanXClienteManagerImpl  extends AbstractManager  implements
		DetallePlanXClienteManager {

	public static Logger logger = Logger.getLogger(DetallePlanXClienteManagerImpl.class);
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros para listar en el grid.
	 * Invoca al Store Procedure PKG_CONFG_CUENTA.P_OBTIENE_PLANCIA.
	 * 
	 * @param start
	 * @param limit
	 * @param codigoCliente
	 * @param codigoElemento
	 * @param codigoProducto
	 * @param codigoPlan
	 * @param codigoTipoSituacion
	 * @param garantia
	 * @param aseguradora
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList buscarDetallePlanes(int start, int limit, String codigoElemento, String codigoProducto, String codigoPlan,
        String codigoTipoSituacion, String garantia, String aseguradora)
        throws ApplicationException {

        HashMap map = new HashMap();
        map.put("codigoElemento", codigoElemento);
        map.put("codigoProducto", codigoProducto);
        map.put("codigoPlan", codigoPlan);
        map.put("codigoSituacion", codigoTipoSituacion);
        map.put("garantia", garantia);
        map.put("aseguradora", aseguradora);
        
        return pagedBackBoneInvoke(map, "P_OBTIENE_PLANCIA", start, limit);

	}
	
	/**
	 * Obtiene un unico registro de datos de un plan por cliente.
	 * Invoca al Store Procedure PKG_CONFG_CUENTA.P_OBTIENE_PLANCIA_REG.
	 * 
	 * @param detallePlanXClienteVO
	 * 
	 * @return DetallePlanXClienteVO
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public DetallePlanXClienteVO getPlanXCliente (DetallePlanXClienteVO detallePlanXClienteVO) throws ApplicationException{

			HashMap map = new HashMap();
			map.put("codigoElemento", detallePlanXClienteVO.getCdElemento());
			map.put("codigoProducto", detallePlanXClienteVO.getCdRamo());
			map.put("codigoPlan", detallePlanXClienteVO.getCdPlan());
			map.put("codigoSituacion", detallePlanXClienteVO.getCdTipSit());
			map.put("garantia", detallePlanXClienteVO.getCdGarant());
			map.put("aseguradora", detallePlanXClienteVO.getCdUniEco());

            return (DetallePlanXClienteVO)getBackBoneInvoke(map,"GET_PLANCIA");

	}
	
	/**
	 * Metodo que inserta un plan de un cliente
	 * Invoca al Store Procedure PKG_CONFG_CUENTA.P_INSERTA_PLANCIA.
	 * 
	 * @param detallePlanXClienteVO Estructura de plan a modificar
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String addPlanXCliente (DetallePlanXClienteVO detallePlanXClienteVO) throws ApplicationException {

			HashMap map = new HashMap();
			map.put("codigocliente", detallePlanXClienteVO.getCdPerson());
			map.put("codigoelemento", detallePlanXClienteVO.getCdElemento());
			map.put("codigoproducto", detallePlanXClienteVO.getCdRamo());
			map.put("codigoplan", detallePlanXClienteVO.getCdPlan());
			map.put("codigosituacion", detallePlanXClienteVO.getCdTipSit());
			map.put("codigogarantia", detallePlanXClienteVO.getCdGarant());
			map.put("codigoobligatorio", detallePlanXClienteVO.getSwOblig());
			map.put("codigoaseguradora", detallePlanXClienteVO.getCdUniEco());

            WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_PLANPRCIA");
            return res.getMsgText();

    }
	
	/**
	 * Metodo que actualiza un plan de un cliente.
	 * Invoca al Store Procedure PKG_CONFG_CUENTA.P_GUARDA_PLANCIA.
	 * 
	 * @param detallePlanXClienteVO Estructura de plan a modificar
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String setPlanXCliente (DetallePlanXClienteVO detallePlanXClienteVO) throws ApplicationException {

			HashMap map = new HashMap();
			map.put("codigocliente", detallePlanXClienteVO.getCdPerson());
			map.put("codigoelemento", detallePlanXClienteVO.getCdElemento());
			map.put("codigoproducto", detallePlanXClienteVO.getCdRamo());
			map.put("codigoplan", detallePlanXClienteVO.getCdPlan());
			map.put("codigosituacion", detallePlanXClienteVO.getCdTipSit());
			map.put("codigogarantia", detallePlanXClienteVO.getCdGarant());
			map.put("codigoobligatorio", detallePlanXClienteVO.getSwOblig());
			map.put("codigoaseguradora", detallePlanXClienteVO.getCdUniEco());

            WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_PLANPRCIA");
            return res.getMsgText();
    }
	
	/**
	 * Metodo que elimina un plan de un cliente seleccionado en el grid de la pantalla
	 * Invoca al Store Procedure PKG_CONFG_CUENTA.P_BORRAR_PLANCIA.
	 * 
	 * @param detallePlanXClienteVO
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String borrarPlanXCliente (DetallePlanXClienteVO detallePlanXClienteVO) throws ApplicationException {

			HashMap map = new HashMap();
			map.put("codigocliente", detallePlanXClienteVO.getCdPerson());
			map.put("codigoelemento", detallePlanXClienteVO.getCdElemento());
			map.put("codigoproducto", detallePlanXClienteVO.getCdRamo());
			map.put("codigoplan", detallePlanXClienteVO.getCdPlan());
			map.put("codigosituacion", detallePlanXClienteVO.getCdTipSit());
			map.put("codigogarantia", detallePlanXClienteVO.getCdGarant());
			map.put("codigoobligatorio", detallePlanXClienteVO.getSwOblig());
			map.put("codigoaseguradora", detallePlanXClienteVO.getCdUniEco());

            WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_PLANPRCIA");
            return res.getMsgText();

	}
	

	@SuppressWarnings("unchecked")
	public PagedList comboTipoSituacion () throws ApplicationException {
		PagedList pagedList = new PagedList();
		List<TiposSituacionVO> lista = null;
		try {
			Endpoint endpoint = endpoints.get("TIPO_SITUACION");
			ArrayList<TiposSituacionVO> invoke = (ArrayList<TiposSituacionVO>)endpoint.invoke(null);
			lista = invoke;
		}catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage(), bae);
			throw new ApplicationException("No se pudo obtener el Listado de Situaciones");
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}

	@SuppressWarnings("unchecked")
	public PagedList comboCoberturas () throws ApplicationException {
		PagedList pagedList = new PagedList();
		List<TiposCoberturasVO> lista = null;
		try {
			Endpoint endpoint = endpoints.get("TIPOS_COBERTURAS");
			ArrayList<TiposCoberturasVO> invoke = (ArrayList<TiposCoberturasVO>)endpoint.invoke(null);
			lista = invoke;
		}catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage(), bae);
			throw new ApplicationException ("No se pudo obtener el Listado de Coberturas");
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}

	@SuppressWarnings("unchecked")
	public PagedList comboAseguradora () throws ApplicationException {
		PagedList pagedList = new PagedList();
		List<AseguradoraVO> lista = null;
		try {
			Endpoint endpoint = endpoints.get("LISTA_ASEGURADORAS");
			ArrayList<AseguradoraVO> invoke = (ArrayList<AseguradoraVO>)endpoint.invoke(null);
			lista = invoke;
		}catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage(), bae);
			throw new ApplicationException ("No se pudo obtener las Aseguradoras");
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}

 /**
	 *  Obtiene un conjunto de personas para la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure  PKG_CANCELA.P_OBTIENE_PERIODOS
	 *  
	 *  @param descripcion
	 *  @param minimo
	 *  @param maximo
	 *  @param diasGracias
	 *  @param diasCancela
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */ 
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String codigoElemento,String codigoProducto,String codigoPlan,String codigoTipoSituacion,String garantia, String aseguradora) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("codigoElemento", codigoElemento);
		map.put("codigoProducto", codigoProducto);
		map.put("codigoPlan", codigoPlan);
		map.put("codigoTipoSituacion", codigoTipoSituacion);
		map.put("garantia", garantia);
		map.put("aseguradora", aseguradora);

		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_PLANCLIENTE_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Cliente","Aseguradora","Producto","Plan","Riesgo","Cobertura","Obligatorio"});
			
		
		return model;
	}
	
}
