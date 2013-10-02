package mx.com.aon.portal.service.impl;

import mx.com.aon.portal.service.AgrupacionPolizasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.AgrupacionPolizaVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements AgrupacionPolizasManager
 * 
 * @extends AbstractManager
 */
public class AgrupacionPolizasManagerImpl extends AbstractManager implements AgrupacionPolizasManager {
    
    /**
	 *  Obtiene un conjunto de agrupaciones de polizas.
	 *  Usa el Store Procedure PKG_AGRUPAPOL.P_OBTIENE_AGRUPACIONES.
	 *  
	 *  @return un objeto PagedLis con un conjunto de registros.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public PagedList buscarAgrupacionPolizas(String cliente, String tipoRamo, String tipoAgrupacion, String aseguradora, String producto, int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cliente_i", cliente);
        map.put("pv_tipo_ramo_i", tipoRamo);
        map.put("pv_tipo_agrupa_i", tipoAgrupacion);
        map.put("pv_aseguradora_i", aseguradora);
        map.put("pv_producto_i", producto);

        return pagedBackBoneInvoke(map, "OBTIENE_AGRUPACIONES", start, limit);
    }

    /**
	 *  Inserta una nueva agrupacion de polizas.
	 *  Usa el Store Procedure PKG_AGRUPAPOL.P_GUARDA_AGRUPACION.
	 * 
	 *  @param agrupacionPolizaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String agregarAgrupacionPoliza(AgrupacionPolizaVO agrupacionPolizaVO) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdgrupo_i",null);
        map.put("pv_cliente_i",agrupacionPolizaVO.getCdPerson());
        map.put("pv_cdelemento_i",agrupacionPolizaVO.getCdElemento());
        map.put("pv_aseguradora_i",agrupacionPolizaVO.getCdUnieco());
        map.put("pv_tipo_ramo_i",agrupacionPolizaVO.getCdTipram());
        map.put("pv_producto_i",agrupacionPolizaVO.getCdRamo());
        map.put("pv_tipo_agrupa_i",agrupacionPolizaVO.getCdTipo());
        map.put("pv_cdestado_i",agrupacionPolizaVO.getCdEstado());

        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_AGRUPACION");
        return res.getMsgText();
    }
    
    /**
	 *  Actualiza una agrupacion de polizas modificada.
	 *  Usa el Store Procedure PKG_AGRUPAPOL.P_GUARDA_AGRUPACION
	 * 
	 *  @param agrupacionPolizaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String guardarAgrupacionPoliza(AgrupacionPolizaVO agrupacionPolizaVO) throws ApplicationException {
        // Se crea un mapa para pasar los parametros de ejecucion al endpoint
        HashMap map = new HashMap();
        map.put("pv_cdgrupo_i",agrupacionPolizaVO.getCdGrupo());
        map.put("pv_cliente_i",agrupacionPolizaVO.getCdPerson());
        map.put("pv_cdelemento_i",agrupacionPolizaVO.getCdElemento());
        map.put("pv_aseguradora_i",agrupacionPolizaVO.getCdUnieco());
        map.put("pv_tipo_ramo_i",agrupacionPolizaVO.getCdTipram());
        map.put("pv_producto_i",agrupacionPolizaVO.getCdRamo());
        map.put("pv_tipo_agrupa_i",agrupacionPolizaVO.getCdTipo());
        map.put("pv_cdestado_i",agrupacionPolizaVO.getCdEstado());

        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_AGRUPACION");
        return res.getMsgText();
    }
    
    /**
	 *  Elimina una agrupacion de polizas seleccionada.
	 *  Usa el Store Procedure PKG_AGRUPAPOL.P_BORRA_AGRUPACION.
	 * 
	 *  @param cveAgrupa
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String borrarAgrupacionPoliza(String cveAgrupa) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cve_agrupa_i", cveAgrupa);


        WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_AGRUPACION");
        return res.getMsgText();
    }
    
    /**
	 *  Obtiene una agrupacion de polizas seleccionada.
	 *  Usa el Store Procedure PKG_AGRUPAPOL.P_OBTIENE_AGRUPACION.
	 * 
	 *  @param cveAgrupa
	 *  
	 *  @return Objeto AgrupacionPolizaVO
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public AgrupacionPolizaVO getAgrupacionPoliza(String cveAgrupa) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cve_agrupa_i", cveAgrupa);

        return (AgrupacionPolizaVO)getBackBoneInvoke(map,"OBTIENE_AGRUPACION");
    }
    
    /**
	 *  Obtiene una lista de registros de agrupacion de polizas para la exportacion a un formato predeterminado.
	 * 
	 *  @param cliente
	 *  @param tipoRamo
	 *  @param tipoAgrupacion
	 *  @param aseguradora
	 *  @param producto
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public TableModelExport getModel(String cliente, String tipoRamo, String tipoAgrupacion, String aseguradora, String producto) throws ApplicationException {
    	// Se crea el objeto de respuesta
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("pv_cliente_i", cliente);
        map.put("pv_tipo_ramo_i", tipoRamo);
        map.put("pv_tipo_agrupa_i", tipoAgrupacion);
        map.put("pv_aseguradora_i", aseguradora);
        map.put("pv_producto_i", producto);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_AGRUPACION_POLIZAS_EXPORT");

		model.setInformation(lista);
		// Se agregan los nombre de las columnas del modelo de datos
		model.setColumnName(new String[]{"Cliente","Aseguradora","Tipo Ramo","Producto","Tipo Agrupacion","Estado"});
		
		return model;
    }
}
