package mx.com.aon.catbo.service.impl;

/*import mx.biosnet.procesobobpelclient.proxy.ProcesoBOBPELPortClient;
import mx.biosnet.procesobobpelclient.proxy.SvcRequest;
import mx.biosnet.procesobobpelclient.proxy.SvcResponse;*/
import mx.com.aon.catbo.model.CalendarioVO;
import mx.com.aon.catbo.model.CasoDetalleVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.model.ItemVO;
import mx.com.aon.catbo.model.ReasignacionCasoVO;
import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;
import mx.com.aon.catbo.model.StatusCasoVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;

import mx.com.aon.export.model.TableModelExport;
import mx.biosnet.procesobobpelclient.proxy.ProcesoBOBPELPortClient;
import mx.biosnet.procesobobpelclient.proxy.SvcRequest;
import mx.biosnet.procesobobpelclient.proxy.SvcResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.math.BigInteger;

import com.biosnet.ice.ext.elements.form.ComboControl;
import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

public class AdministracionCasosManagerImpl extends AbstractManager implements AdministracionCasosManager {
    
	SvcResponse response = null;
	
	@SuppressWarnings("unchecked")
	public PagedList obtenerNumerosCasos(String desmodulo, int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("desmodulo", desmodulo);

        String endpointName = "OBTENER_NUM_CASOS";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }

	public String borrarNumCasos(String pv_cdnumecaso_i) throws ApplicationException {
	    HashMap map = new HashMap();
		map.put("pv_cdnumecaso_i",pv_cdnumecaso_i);
		
	    WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_NUM_CASOS");
	    return res.getMsgText();
				
	}

	public CasoVO getObtenerNumeroCaso(String  pv_cdnumecaso_i) throws ApplicationException {
		HashMap map= new HashMap();
		map.put("pv_cdnumecaso_i",pv_cdnumecaso_i);
		
		String endpoindName = "OBTENER_NUM_REGISTRO";
		
		return (CasoVO)getBackBoneInvoke(map, endpoindName );
		
	}
	public String guardarNumerosCaso(String pv_cdnumecaso_i, String pv_indnumer_i,String  pv_cdmodulo_i,String  pv_nmcaso_i, String  pv_dssufpre_i,String pv_nmdesde_i , String pv_nmhasta_i  )throws ApplicationException{
		
			HashMap map = new HashMap();
			map.put("pv_cdnumecaso_i",pv_cdnumecaso_i);
			map.put("pv_indnumer_i",pv_indnumer_i);
		    map.put("pv_cdmodulo_i",pv_cdmodulo_i);
			map.put("pv_nmcaso_i",pv_nmcaso_i);
			map.put("pv_dssufpre_i",pv_dssufpre_i);
			map.put("pv_nmdesde_i",pv_nmdesde_i);
			map.put("pv_nmhasta_i",pv_nmhasta_i);
			
	WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_NUMEROS_CASO");
	return res.getMsgText();
		
	}
	
    /**
	 *  Obtiene un un conjunto de numeracion de casos para la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure PKG_CATBO_P.OBTENER_TNUMECAS
	 *  
	 *  @param dsStatus
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public TableModelExport getModel(String desmodulo) throws ApplicationException {
		
  		 TableModelExport model = new TableModelExport();
		
		 List lista = null;
		 HashMap map = new HashMap();

		map.put("desmodulo", desmodulo);
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTENER_NUMERACION_CASOS_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Módulo","Número Desde","Número Hasta","Número Actual"});
		
		return model;
	}
	
	
	public PagedList obtieneTareas(String pv_dsproceso_i, String pv_dsmodulo_i, String pv_cdpriord_i , int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_dsproceso_i", pv_dsproceso_i);
        map.put("pv_dsmodulo_i", pv_dsmodulo_i);
        map.put("pv_cdpriord_i", pv_cdpriord_i);

        String endpointName = "OBTIENE_TAREAS";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }
	public CasoVO getObtenerTareas(String  pv_cdproceso_i) throws ApplicationException {
		HashMap map= new HashMap();
		map.put("pv_cdproceso_i",pv_cdproceso_i);
		
		String endpoindName = "OBTENER_TAREAS_REGISTRO";
		
		return (CasoVO)getBackBoneInvoke(map, endpoindName );
		
	}
	public String borrarTarea(String pv_cdproceso_i) throws ApplicationException {
	    HashMap map = new HashMap();
		map.put("pv_cdproceso_i",pv_cdproceso_i);
		
	    WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_TAREA");
	    return res.getMsgText();
		
		
	}
	public String guardarTarea(String pv_cdproceso_i,
			String pv_estatus_i, String  pv_cdmodulo_i, String pv_cdpriord_i,
			String pv_indesemaforo_i)
			throws ApplicationException {
		
			HashMap map = new HashMap();
			map.put("pv_cdproceso_i",pv_cdproceso_i);
		    map.put("pv_estatus_i",pv_estatus_i);
			map.put("pv_cdmodulo_i", pv_cdmodulo_i);
			map.put("pv_cdpriord_i",pv_cdpriord_i);
			map.put("pv_indesemaforo_i",pv_indesemaforo_i);
			
			
	WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_TAREA");
	return res.getMsgText();
		
	}
	public String guardarTiempo(String pv_cdproceso_i,
			String pv_cdnivatn_i , String  pv_nmcant_desde_i, String pv_nmcant_hasta_i,
			String pv_tunidad_i , String pv_nmvecescompra_i )
			throws ApplicationException {
		
			HashMap map = new HashMap();
			map.put("pv_cdproceso_i",pv_cdproceso_i);
		    map.put("pv_cdnivatn_i ",pv_cdnivatn_i );
			map.put("pv_nmcant_desde_i", pv_nmcant_desde_i);
			map.put("ppv_nmcant_hasta_i",pv_nmcant_hasta_i);
			map.put("pv_tunidad_i",pv_tunidad_i);
			map.put("pv_nmvecescompra_i",pv_nmvecescompra_i);
			
	WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_TIEMPO");
	return res.getMsgText();
		
	}

	@SuppressWarnings("unchecked")
	public String borrarCaso(String pv_nmcaso_i)
			throws ApplicationException {
		 HashMap map = new HashMap();
			map.put("pv_nmcaso_i",pv_nmcaso_i);
			
		    WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_CASO");
		    return res.getMsgText();
			
	}

	@SuppressWarnings("unchecked")
	public PagedList getObtenerResponsable(String pv_nmcaso_i, String cdmatriz, int start, int limit)
			throws ApplicationException {
		HashMap map= new HashMap();
		
		map.put("pv_nmcaso_i",pv_nmcaso_i);
		map.put("pv_cdmatriz_i",cdmatriz);
		
		String endpointName = "OBTENER_RESPONSABLE";
		
		 return pagedBackBoneInvoke(map, endpointName, start, limit);
	}


	/*@SuppressWarnings("unchecked")
	public PagedList obtenerCasos(String pv_nmcaso_i, String pv_cdorden_i, String pv_dsproceso_i, String pv_feingreso_i,
			String pv_cdpriord_i, String pv_cdperson_i, String pv_dsperson_i,  String pv_cdusuario_i,int start, int limit)
			throws ApplicationException {
		
	    //Converter converter = new UserSQLDateConverter("");
		HashMap map = new HashMap();
        map.put("pv_nmcaso_i", pv_nmcaso_i);
        map.put("pv_cdorden_i", pv_cdorden_i);
        map.put("pv_dsproceso_i",pv_dsproceso_i);
        map.put("pv_feingreso_i",pv_feingreso_i);
        //map.put("pv_feingreso_i", converter.convert(java.util.Date.class, pv_feingreso_i));
        map.put("pv_cdpriord_i",pv_cdpriord_i);
        map.put("pv_cdperson_i", pv_cdperson_i);
        map.put("pv_dsperson_i", pv_dsperson_i);
        map.put("pv_cdusuario_i", pv_cdusuario_i);
        
        String endpointName = "OBTENER_CASOS";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
*/
	@SuppressWarnings("unchecked")
	public CasoDetalleVO getObtenerCaso(String pv_nmcaso_i,String cdmatriz)
	         throws ApplicationException {
	HashMap map= new HashMap();
	map.put("pv_nmcaso_i",pv_nmcaso_i);
	map.put("cdmatriz",cdmatriz);
	
	String endpoindName = "OBTENER_CASO_REG";
	
	return (CasoDetalleVO)getBackBoneInvoke(map, endpoindName );
	}

	public String guardarCompra(String pv_nmcaso_i,	String pv_cdusuario_i, String pv_cdnivatn_i, String pv_nmcompra_i,String pv_tunidad_i)
			throws ApplicationException {
		
			HashMap map = new HashMap();
			map.put("pv_nmcaso_i",pv_nmcaso_i);
		    map.put("pv_cdusuario_i",pv_cdusuario_i);
			map.put("pv_cdnivatn_i", pv_cdnivatn_i);
			map.put("pv_nmcompra_i", pv_nmcompra_i);
			map.put("pv_tunidad_i",pv_tunidad_i);
			
			
	WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_COMPRA");
	return res.getMsgText();
		
	}

	@SuppressWarnings("unchecked")
	public String validaCompraTiempo(String pv_cdproceso_i,	int pv_cdnivatn_i,String pv_nmcaso_i) throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdproceso_i",pv_cdproceso_i);
		map.put("pv_cdnivatn_i",pv_cdnivatn_i);
		map.put("pv_nmcaso_i",pv_nmcaso_i);
		
	    WrapperResultados res =  returnBackBoneInvoke(map,"VALIDA_COMPRA_TIEMPO");
	    return res.getResultado();
		
		}

	public CasoVO obtenerCompra(String pv_cdproceso_i)
			throws ApplicationException {
		HashMap map= new HashMap();
		map.put("pv_cdproceso_i",pv_cdproceso_i);
		
		String endpoindName = "OBTENER_COMPRA";
		
		return (CasoVO)getBackBoneInvoke(map, endpoindName );
		
	}

	public CasoVO obtenerEncabezadoMovimientos(String pv_nmcaso_i)
			throws ApplicationException {
		HashMap map= new HashMap();
		map.put("pv_nmcaso_i",pv_nmcaso_i);
		
		String endpoindName = "OBTENER_ENCABEZADO_MOVIMIENTOS";
		
		return (CasoVO)getBackBoneInvoke(map, endpoindName );
		
		
	}

	public PagedList obtenerMovimientos(String pv_nmcaso_i, int start, int limit)
			throws ApplicationException {
		HashMap map = new HashMap();
        map.put("pv_nmcaso_i", pv_nmcaso_i);

        String endpointName = "OBTENER_MOVIMIENTOS";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
	}

	@SuppressWarnings("unchecked")
	public String borrarMovimientos(String pv_nmcaso_i, String pv_nmovimiento_i)
			throws ApplicationException {
		
			 HashMap map = new HashMap();
				map.put("pv_nmcaso_i",pv_nmcaso_i);
				map.put("pv_nmovimiento_i",pv_nmovimiento_i);
				
			    WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_MOVIMIENTOS");
			    return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public WrapperResultados guardarMovimineto(String pv_nmcaso_i, String pv_cdpriord_i,
			String pv_cdstatus_i, String pv_dsobservacion_i,
			String pv_cdusuario_i) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_nmcaso_i",pv_nmcaso_i);
	    map.put("pv_cdpriord_i",pv_cdpriord_i);
	    map.put("pv_cdstatus_i",pv_cdstatus_i);
	    map.put("pv_dsobservacion_i",pv_dsobservacion_i);
	    map.put("pv_cdusuario_i",pv_cdusuario_i);
	    
	    
	    
		WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_MOVIMIENTO");
		return res;
			
	}

	public String guardarReasignacion(String pv_nmcaso_i, String pv_cdusumov_i,
			String pv_cdusuario_i, String pv_cdrolmat_i)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_nmcaso_i",pv_nmcaso_i);
	    map.put("pv_cdusumov_i",pv_cdusumov_i);
		map.put("pv_cdusuario_i" , pv_cdusuario_i);
		map.put("pv_cdrolmat_i",pv_cdrolmat_i);
		
		
		
WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_REASIGNACION");
return res.getMsgText();
	}

	public CasoVO getObtenerMovimientoCaso(String pv_nmcaso_i,String pv_nmovimiento_i) throws ApplicationException {
		HashMap map= new HashMap();
		map.put("pv_nmcaso_i",pv_nmcaso_i);
		map.put("pv_nmovimiento_i",pv_nmovimiento_i);
		String endpoindName = "GET_OBTENER_MOVIMINETO_CASO";
		
		return (CasoVO)getBackBoneInvoke(map, endpoindName );
		
	
	}
	
	public PagedList obtieneArchivosCaso(String pv_nmcaso_i, String pv_nmovimiento_i, int start, int limit)
      	throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_nmcaso_i", pv_nmcaso_i);
		map.put("pv_nmovimiento_i", pv_nmovimiento_i);

		String endpointName = "OBTENER_ARCHIVOS_CASO";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
		}

	public String borraArchivoCaso(String pv_nmcaso_i, String pv_nmovimiento_i,
			String pv_nmarchivo_i) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_nmcaso_i",pv_nmcaso_i);
		map.put("pv_nmovimiento_i",pv_nmovimiento_i);
		map.put("pv_nmarchivo_i",pv_nmarchivo_i);
		
	    WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_ARCHIVO_CASO");
	    return res.getMsgText();
	}

	public String guaradarArchivoCaso(String pv_nmcaso_i,
			String pv_nmovimiento_i, String pv_nmarchivo_i,
			String pv_dsarchivo_i, String pv_cdtipoar_i) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_nmcaso_i",pv_nmcaso_i);
	    map.put("pv_nmovimiento_i",pv_nmovimiento_i);
		map.put("pv_nmarchivo_i" , pv_nmarchivo_i);
		map.put("pv_dsarchivo_i",pv_dsarchivo_i);
		map.put("pv_cdtipoar_i",pv_cdtipoar_i);
		
		
		
WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_ARCHIVO_CASO");
return res.getMsgText();
	}

	public PagedList obtieneResponsablesCaso(String pv_cdproceso_i, int start,
			int limit) throws ApplicationException {
		HashMap map = new HashMap();
        map.put("pv_cdproceso_i", pv_cdproceso_i);

        String endpointName = "OBTIENE_RESPONSABLES_CASO";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
	}	
    
	
	public PagedList obtienePolizas(String pv_cdelemento_i, String pv_cdperson_i, int start,
			int limit) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdelemento_i", pv_cdelemento_i);
        map.put("pv_cdperson_i", pv_cdperson_i);

        String endpointName = "OBTIENE_POLIZAS_CATBO";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
	}	
		
	public TableModelExport getModelCasos(String pv_nmcaso_i,
			String pv_cdorden_i, String pv_dsproceso_i, String pv_feingreso_i,
			String pv_cdpriord_i, String pv_cdperson_i, String pv_dsperson_i, String pv_cdusuario_i)
			throws ApplicationException {
		
        TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();

		    map.put("pv_nmcaso_i", pv_nmcaso_i);
	        map.put("pv_cdorden_i", pv_cdorden_i);
	        map.put("pv_dsproceso_i",pv_dsproceso_i);
	        map.put("pv_feingreso_i",pv_feingreso_i);
	        map.put("pv_cdpriord_ii",pv_cdpriord_i);
	        map.put("pv_cdperson_i", pv_cdperson_i);
	        map.put("pv_dsperson_i", pv_dsperson_i);
	        map.put("pv_cdusuario_i", pv_cdusuario_i);
	        
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_CASOS_SOLICITUD_EXPORT");
		
		model.setInformation(lista);
		model.setColumnName(new String[]{"Caso","Orden de Trabajo","Proceso","Fecha Registro","Usuario","Prioridad"});
		
		return model;
	}

	public ResultadoGeneraCasoVO guardarCasos(String pv_cdmatriz_i,
			String pv_cdusuario_i, String pv_cdunieco_i, String pv_cdramo_i,
			String pv_estado_i, String pv_nmsituac_i, String pv_nmsituaext_i,
			String pv_nmsbsitext_i,String pv_nmpoliza_i, String pv_nmpoliex_i,
			String pv_cdperson_i, String pv_dsmetcontact_i, String pv_ind_poliza_i,	
			String pv_cdpriord_i, String pv_cdproceso_i, String pv_dsobservacion_i)
	
			throws ApplicationException {
         HashMap map = new HashMap();
		
		
		map.put("pv_cdmatriz_i",pv_cdmatriz_i);
		map.put("pv_cdusuario_i",pv_cdusuario_i);
		map.put("pv_cdunieco_i",pv_cdunieco_i);
		map.put("pv_cdramo_i",pv_cdramo_i);
		map.put("pv_estado_i",pv_estado_i);
		map.put("pv_nmsituac_i",pv_nmsituac_i);
		map.put("pv_nmsituaext_i",pv_nmsituaext_i);
		map.put("pv_nmsbsitext_i",pv_nmsbsitext_i);
		map.put("pv_nmpoliza_i",pv_nmpoliza_i);
		map.put("pv_nmpoliex_i",pv_nmpoliex_i);
		map.put("pv_cdperson_i",pv_cdperson_i);
		map.put("pv_dsmetcontac_i",pv_dsmetcontact_i);
		map.put("pv_ind_poliza_i",pv_ind_poliza_i);	
		map.put("pv_cdpriord_i",pv_cdpriord_i);	
		map.put("pv_cdproceso_i",pv_cdproceso_i);	
		map.put("pv_dsobservacion_i",pv_dsobservacion_i);
		
		
		
          String endpoindName = "GUARDAR_CASOS";
		
		return (ResultadoGeneraCasoVO)getBackBoneInvoke(map, endpoindName );
		
	}

	public String obtenerIdentificadorCaso(String pv_cdmodulo_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	

	public PagedList obtieneListaResponsablesCaso(String cdproceso,String cdmatriz, int start,
			int limit) throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdproceso_i", cdproceso);
		map.put("pv_cdmatriz_i", cdmatriz);

		String endpointName = "OBTENER_LISTA_USUARIOS_RESPONSABLES";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}

	public PagedList obtenerSufijoPrefijo(String pv_cdmodulo_i, int start,int limit) throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_cdmodulo_i", pv_cdmodulo_i);
		
		String endpointName = "OBTENER_SUFIJO_PREFIJO";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	public PagedList obtenerClientes(String dselemento, String cdideper, String cdperson,
			String dsnombre, String dsapellido, String dsapellido1, int start,
			int limit) throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("pv_dselemento_i", dselemento);
		map.put("pv_cdideper_i", cdideper);
		map.put("pv_cdperson_i", cdperson);
		map.put("pv_dsnombre_i", dsnombre);
		map.put("pv_dsapellido_i", dsapellido);
		map.put("pv_dsapellido1_i", dsapellido1);
		
		String endpointName = "OBTENER_CLIENTES";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}

	public PagedList obtenerAtributoSeccion(String pv_cdformatoorden_i,
			String pv_cdseccion_i, int start, int limit)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdformatoorden_i", pv_cdformatoorden_i);
		map.put("pv_cdseccion_i", pv_cdseccion_i);
		
		
		String endpointName = "OBTENER_ATRIBUTO_SECCION";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	public List<ComboControl> obtenerAtributoSeccionComboControl(String pv_cdformatoorden_i,
			String pv_cdseccion_i)
			throws ApplicationException {
		List<ComboControl> listAtribComboControl = null;
		HashMap map = new HashMap();
		map.put("pv_cdformatoorden_i", pv_cdformatoorden_i);
		map.put("pv_cdseccion_i", pv_cdseccion_i);
		
		WrapperResultados res =  returnBackBoneInvoke(map,"OBTENER_ATRIBUTO_SECCION_COMBO_CONTROL");
		logger.debug("----> res : " + res);
		logger.debug("----> ItemList : " + res.getItemList());
		
		return res.getItemList();
	}

	public PagedList obtieneSeccionesOrden(String pv_cdordentrabajo_i,
			int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdordentrabajo_i", pv_cdordentrabajo_i);

		
		
		String endpointName = "OBTENER_SECCION_ORDEN";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	
	
	public TableModelExport getModelMCaso(String pv_nmcaso_i)throws ApplicationException {
		
        TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();

		    map.put("pv_nmcaso_i",pv_nmcaso_i);	       
	        
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map,"LISTADO_MOVIMIENTO_CASO_EXPORT");
		
		model.setInformation(lista);
		model.setColumnName(new String[]{"Movimiento","Estatus","","Descripción","Fecha de Ingreso"});
		
		return model;
	}
	
	public PagedList obtenerUsuariosResponsablesMovCaso(String pv_nmovimiento_i, String pv_nmcaso_i, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_nmcaso_i", pv_nmcaso_i);
		map.put("pv_nmovimiento_i", pv_nmovimiento_i);
			
		
		String endpointName = "USUARIOS_RESPONSABLES_MOVCASO";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	
	
	public ResultadoGeneraCasoVO guardarNuevoCaso(CasoVO casoVO, FormatoOrdenVO formatoOrdenVO)throws Exception {

		try {
			
	        logger.info("llamo al bpel");		
			ProcesoBOBPELPortClient myPort = new ProcesoBOBPELPortClient();
		    logger.debug("Invocando servicio: " + myPort.getEndpoint());	
		    
		    //SvcResponse response = null;
			SvcRequest req = new SvcRequest();

            req.setCdmatriz(new BigInteger(casoVO.getCdMatriz()));
            req.setCdperson(casoVO.getCdPerson());
            req.setCdramo(casoVO.getCdRamo());
            req.setCdunieco(casoVO.getCdUnieco());
            req.setCdusuario(casoVO.getCdUsuario());
            req.setDsmetcontac(casoVO.getDsmetcontac());
            req.setEstado(casoVO.getEstado());
            req.setIndPoliza(casoVO.getIndPoliza());
            req.setNmpoliex(casoVO.getNmpoliex());
            req.setNmpoliza(casoVO.getNmpoliza());
            req.setNmsbsitext(casoVO.getNmsbsitext());
            req.setNmsituaext(casoVO.getNmsituaext());
            req.setNmsituac(casoVO.getNmsituac());
            req.setCdprioridad(casoVO.getCdPrioridad());
            req.setCdproceso(casoVO.getCdProceso());
            if(casoVO.getDsObservacion() != null && !casoVO.getDsObservacion().equals("")){
            	req.setDsobservacion(casoVO.getDsObservacion());
            }else{
            	req.setDsobservacion("Iniciado desde la pantalla");
            }

			response = myPort.process(req);
			logger.info("esta es la response: numero de caso generado: " +
	                response.getNmCaso() + " numero de orden: " +
	                response.getNmOrden());
			
			ResultadoGeneraCasoVO res = new ResultadoGeneraCasoVO();
			if(response.getNmCaso().equals("0")){				
				throw new Exception(response.getMessages()[0].getMsgString());
			}
			res.setNumCaso(response.getNmCaso());
			res.setCdOrdenTrabajo(Integer.toString(response.getNmOrden()));
			logger.debug("Quebien, se genero el caso "+response.getNmCaso());
		
			if((res.getCdOrdenTrabajo()!=null && res.getCdOrdenTrabajo()!="") && (res.getNumCaso()!=null && res.getNumCaso()!="")){
				logger.debug("Quebien, entramos al if para el caso "+res.getNumCaso());
				WrapperResultados wres = new WrapperResultados();
							
				for(int i=0; i<formatoOrdenVO.getListaEmisionVO().size();i++){
					HashMap map = new HashMap();
					map.put("pv_cdordentrabajo_i", res.getCdOrdenTrabajo());
					map.put("pv_cdformatoorden_i", formatoOrdenVO.getListaEmisionVO().get(i).getCdformatoorden());
					map.put("pv_cdseccion_i", formatoOrdenVO.getListaEmisionVO().get(i).getCdSeccion());
					map.put("pv_cdatribu_i", formatoOrdenVO.getListaEmisionVO().get(i).getCdAtribu());
					map.put("pv_otvalor_i", formatoOrdenVO.getListaEmisionVO().get(i).getOtValor());
					
					String endpointName = "GUARDAR_FORMATO_ORDEN";
					wres =  (WrapperResultados) this.returnBackBoneInvoke(map, endpointName);
				}					
			}
			return res;

		} catch (Exception e) {
			logger.debug("ExcepcionIniciarCaso. MessageDetail: "+response.getMessages()[0].getMsgDetail());
			logger.debug("MessageString: "+response.getMessages()[0].getMsgString());
			if(response.getNmCaso().equals("0")){
				throw new Exception(response.getMessages()[0].getMsgString());
			}
			throw new Exception("No se guardaron los atributos variables. Consulte a Soporte");
		}
		
	}

	public String guardaMovimientoGenerico(String numeroCaso, String cdusuario,
            String cdnivatn, String cdstatus, String cdmodulo,
            String cdpriord, String nmcompra,
            String tunidad, String dsobservacion)
			throws ApplicationException {

		HashMap map = new HashMap();
		map.put("numeroCaso", numeroCaso);
		map.put("cdUsuario", cdusuario);
		map.put("cdNivAtn", cdnivatn);
		map.put("cdStatus", cdstatus);
		map.put("cdModulo", cdmodulo);
		map.put("cdPriord", cdpriord);
		map.put("nmCompra", nmcompra);
		map.put("unidad", tunidad);
		map.put("dsObservacion", dsobservacion);
		
		WrapperResultados res = returnBackBoneInvoke(map, "GUARDAR_MOVIM_GENERICO");
		return res.getMsgText();
	}
		
	
	public List obtenerReasignacionCaso(String cdModulo) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdModulo", cdModulo);
		        
		return getAllBackBoneInvoke(map,"BUSCAR_REASIGNACION_CASO");
	}

	public String guardarReasignacionCaso (CasoVO casoVO) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("nmCaso",casoVO.getNmCaso());
		map.put("cdUsuMov",casoVO.getCdUsuMov());
		map.put("cdUsuario",casoVO.getCdUsuario());
		map.put("cdRolmat",casoVO.getCdRolmat());
		
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_REASIGNACION_CASO");
        return res.getMsgText();
	}

    public String guardaSuplente(int cdMatriz, int  cdNivatn ,String cdUsuarioOld, String cdUsuarioNew,String nmcaso, String cdUsuario) throws ApplicationException {
         HashMap map = new HashMap();

         map.put("pv_cdmatriz_i", cdMatriz);
         map.put("pv_cdusr_old_i", cdUsuarioOld);
         map.put("pv_cdusr_new_i", cdUsuarioNew);
         map.put("pv_cdusuario_i", cdUsuario);
         map.put("pv_cdnivatn_i", cdNivatn);
         map.put("pv_nmcaso_i", nmcaso);

         WrapperResultados res = returnBackBoneInvoke(map, "GUARDA_SUPLENTE");
         return res.getMsgText();
     }


     public String guardarReasignacion1(String pv_nmcaso_i, String pv_cdusumov_i,
             String pv_cdusuari_old_i, String pv_cdusuari_new_i)
             throws ApplicationException {
         HashMap map = new HashMap();
         map.put("pv_nmcaso_i",pv_nmcaso_i);
         map.put("pv_cdusumov_i",pv_cdusumov_i);
         map.put("pv_cdusuari_old_i" , pv_cdusuari_old_i);
         map.put("pv_cdusuari_new_i",pv_cdusuari_new_i);

         WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_REASIGNACION1");
         return res.getMsgText();
     }

	public PagedList obtenerReasignacionCasoUsr(String cdUsuario, int start, int limit) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdUsuario", cdUsuario);
		
		map.put("start",start);
		map.put("limit",limit);		        
		
        return pagedBackBoneInvoke(map, "BUSCAR_REASIGNACION_CASO_USR", start, limit);		
	}
	
	public PagedList obtenerReasignacionCasoUsrRspnsbl(String desUsuario,String cdUsuarioOld, int start, int limit) throws ApplicationException{
 		HashMap map = new HashMap();
		map.put("desUsuario", desUsuario);
		map.put("cdUsuarioOld", cdUsuarioOld);
		
		map.put("start",start);
		map.put("limit",limit);
		
        return pagedBackBoneInvoke(map, "BUSCAR_REASIGNACION_CASO_USR_RSPNSBL", start, limit);
		
	}
	
	public String guardarReasignacionCasoUsr (CasoVO casoVO) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("nmCaso",casoVO.getNmCaso());
		map.put("cdUsuMov",casoVO.getCdUsuMov());
		map.put("cdUsuarioOld",casoVO.getCdUsuarioOld());
		map.put("cdUsuarioNew",casoVO.getCdUsuarioNew());
		
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_REASIGNACION_CASO_USR");
        return res.getMsgText();
	}
	
	public String guardarMovimientoCaso (CasoVO casoVO) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("nmCaso",casoVO.getNmCaso());
		map.put("cdUsuMov",casoVO.getCdUsuMov());
		map.put("cdNivatn",casoVO.getCdNivatn());
		
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_MOVIMIENTO_CASO");
        return res.getMsgText();
	}
	
	public String guardaReasignacionCasoNv(CasoVO casoVO, List<ReasignacionCasoVO> listaReasignacionCasoVO) throws ApplicationException{
		WrapperResultados res = new WrapperResultados();
		HashMap map = new HashMap();
		map.put("nmCaso",casoVO.getNmCaso());
		map.put("cdUsuMov",casoVO.getCdUsuMov());
		map.put("cdNivatn",casoVO.getCdNivatn());
		
        res =  returnBackBoneInvoke(map,"GUARDAR_MOVIMIENTO_CASO");
        
 

		   for (int i=0; i<listaReasignacionCasoVO.size(); i++) {
			   HashMap mapGuardar = new HashMap();
			   ReasignacionCasoVO reasignacionCasoVO=listaReasignacionCasoVO.get(i);
			   mapGuardar.put("numCaso",reasignacionCasoVO.getNumCaso() );
			   mapGuardar.put("codUsuMov",reasignacionCasoVO.getCodUsuMov());
			   mapGuardar.put("codUsuario",reasignacionCasoVO.getCodUsuario());
			   mapGuardar.put("codRolMat",reasignacionCasoVO.getCodRolMat());
			
			   res =  returnBackBoneInvoke(mapGuardar,"GUARDAR_REASIGNACION_CASO");
		   }
		   return res.getMsgText();
	}

	public TableModelExport getModelCasos(String pv_nmcaso_i,
			String pv_cdorden_i, String pv_dsproceso_i, String pv_feingreso_i,
			String pv_cdpriord_i, String pv_cdperson_i, String pv_dsperson_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public HashMap obtenerDetalleCasoParaExportar(String nroCaso, String cdMatriz)
	throws ApplicationException {
	// TODO Auto-generated method stub
	return null;
	}
	
	public HashMap<String, ItemVO> obtenerAtributosVariablesCasoParaExportar(
		String cdFormatoOrden, String cdSeccion)
		throws ApplicationException {
	// TODO Auto-generated method stub
	return null;
	}

	public PagedList obtenerListaUsuariosAExportar(String nroCaso,
			String cdMatriz, int start, int limit) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
}