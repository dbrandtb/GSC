package mx.com.aon.catbo.service.impl;

import java.util.HashMap;
import java.util.List;

import com.biosnet.ice.ext.elements.form.ComboControl;

import mx.com.aon.catbo.model.CasoDetalleVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ExtJSAtributosVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.model.ItemVO;
import mx.com.aon.catbo.model.ReasignacionCasoVO;
import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;

public class AdministracionCasosManagerJdbcTemplate extends
		AbstractManagerJdbcTemplateInvoke implements AdministracionCasosManager {

	public String borraArchivoCaso(String pv_nmcaso_i, String pv_nmovimiento_i,
			String pv_nmarchivo_i) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String borrarCaso(String pv_nmcaso_i) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String borrarMovimientos(String pv_nmcaso_i, String pv_nmovimiento_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String borrarNumCasos(String pv_cdnumecaso_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String borrarTarea(String pv_cdproceso_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public TableModelExport getModel(String desmodulo)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public TableModelExport getModelCasos(String pv_nmcaso_i,
			String pv_cdorden_i, String pv_dsproceso_i, String pv_feingreso_i,
			String pv_cdpriord_i, String pv_cdperson_i, String pv_dsperson_i,
			String pv_cdusuario_i) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public TableModelExport getModelMCaso(String pv_nmcaso_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public CasoDetalleVO getObtenerCaso(String pv_nmcaso_i, String cdmatriz)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public CasoVO getObtenerMovimientoCaso(String pv_nmcaso_i,
			String pv_nmovimiento_i) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public CasoVO getObtenerNumeroCaso(String pv_cdnumecaso_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList getObtenerResponsable(String pv_nmcaso_i, String cdmatriz,
			int start, int limit) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public CasoVO getObtenerTareas(String pv_cdproceso_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guaradarArchivoCaso(String pv_nmcaso_i,
			String pv_nmovimiento_i, String pv_nmarchivo_i,
			String pv_dsarchivo_i, String pv_cdtipoar_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardaMovimientoGenerico(String numeroCaso, String cdusuario,
			String cdnivatn, String cdstatus, String cdmodulo, String cdpriord,
			String nmcompra, String tunidad, String dsobservacion)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardaReasignacionCasoNv(CasoVO casoVO,
			List<ReasignacionCasoVO> listaReasignacionCasoVO)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardaSuplente(int cdMatriz, int cdNivatn,
			String cdUsuarioOld, String cdUsuarioNew, String nmcaso,
			String cdUsuario) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultadoGeneraCasoVO guardarCasos(String pv_cdmatriz_i,
			String pv_cdusuario_i, String pv_cdunieco_i, String pv_cdramo_i,
			String pv_estado_i, String pv_nmsituac_i, String pv_nmsituaext_i,
			String pv_nmsbsitext_i, String pv_nmpoliza_i, String pv_nmpoliex_i,
			String pv_cdperson_i, String pv_dsmetcontact_i,
			String pv_ind_poliza_i, String pv_cdpriord_i,
			String pv_cdproceso_i, String pv_dsobservacion_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardarCompra(String pv_nmcaso_i, String pv_cdusuario_i,
			String pv_cdnivatn_i, String pv_nmcompra_i, String pv_tunidad_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardarMovimientoCaso(CasoVO casoVO)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public WrapperResultados guardarMovimineto(String pv_nmcaso_i, String pv_cdpriord_i,
			String pv_cdstatus_i, String pv_dsobservacion_i,
			String pv_cdusuario_i) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultadoGeneraCasoVO guardarNuevoCaso(CasoVO casoVO,
			FormatoOrdenVO formatoOrdenVO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardarNumerosCaso(String pv_cdnumecaso_i,
			String pv_indnumer_i, String pv_cdmodulo_i, String pv_nmcaso_i,
			String pv_dssufpre_i, String pv_nmdesde_i, String pv_nmhasta_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardarReasignacion(String pv_nmcaso_i, String pv_cdusumov_i,
			String pv_cdusuario_i, String pv_cdrolmat_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardarReasignacion1(String pv_nmcaso_i,
			String pv_cdusumov_i, String pv_cdusuari_old_i,
			String pv_cdusuari_new_i) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardarReasignacionCaso(CasoVO casoVO)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardarReasignacionCasoUsr(CasoVO casoVO)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardarTarea(String pv_cdproceso_i, String pv_estatus_i,
			String pv_cdmodulo_i, String pv_cdpriord_i, String pv_indesemaforo_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String guardarTiempo(String pv_cdproceso_i, String pv_cdnivatn_i,
			String pv_nmcant_desde_i, String pv_nmcant_hasta_i,
			String pv_tunidad_i, String pv_nmvecescompra_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtenerAtributoSeccion(String pv_cdformatoorden_i,
			String pv_cdseccion_i, int start, int limit)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<ComboControl> obtenerAtributoSeccionComboControl(String pv_cdformatoorden_i,
			String pv_cdseccion_i)
			throws ApplicationException {
		return null;
	}

	public PagedList obtenerCasos(String pv_nmcaso_i, String pv_cdorden_i,
			String pv_dsproceso_i, String pv_feingreso_i, String pv_cdpriord_i,
			String pv_cdperson_i, String pv_dsperson_i, String pv_cdusuario_i,
			int start, int limit) throws ApplicationException {
	    HashMap map = new HashMap();
        map.put("pv_nmcaso_i", pv_nmcaso_i);
        map.put("pv_cdorden_i", pv_cdorden_i);
        map.put("pv_dsproceso_i",pv_dsproceso_i);
        map.put("pv_feingreso_i",pv_feingreso_i);
        map.put("pv_cdpriord_i",pv_cdpriord_i);
        map.put("pv_cdperson_i", pv_cdperson_i);
        map.put("pv_dsperson_i", pv_dsperson_i);
        map.put("pv_cdusuario_i", pv_cdusuario_i);
        
        String endpointName = "OBTENER_CASOS";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
	}

	public PagedList obtenerClientes(String cdelemento, String cdideper,
			String cdperson, String dsnombre, String dsapellido,
			String dsapellido1, int start, int limit)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public CasoVO obtenerCompra(String pv_cdproceso_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public CasoVO obtenerEncabezadoMovimientos(String pv_nmcaso_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String obtenerIdentificadorCaso(String pv_cdmodulo_i)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtenerMovimientos(String pv_nmcaso_i, int start, int limit)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtenerNumerosCasos(String desmodulo, int start, int limit)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List obtenerReasignacionCaso(String cdModulo)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtenerReasignacionCasoUsr(String cdUsuario, int start,
			int limit) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtenerReasignacionCasoUsrRspnsbl(String desUsuario,
			String cdUsuarioOld, int start, int limit)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtenerSufijoPrefijo(String pv_cdmodulo_i, int start,
			int limit) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtenerUsuariosResponsablesMovCaso(String pv_nmcaso_i,
			String pv_nmovimiento_i, int start, int limit)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtieneArchivosCaso(String pv_nmcaso_i,
			String pv_nmovimiento_i, int start, int limit)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtieneListaResponsablesCaso(String cdproceso,
			String cdmatriz, int start, int limit) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtienePolizas(String pv_cdelemento_i,
			String pv_cdperson_i, int start, int limit)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtieneResponsablesCaso(String pv_cdproceso_i, int start,
			int limit) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtieneSeccionesOrden(String pv_cdformatoorden_i,
			int start, int limit) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList obtieneTareas(String pv_dsproceso_i, String pv_dsmodulo_i,
			String pv_cdpriord_i, int start, int limit)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String validaCompraTiempo(String pv_cdproceso_i, int pv_cdnivatn_i,
			String pv_nmcaso_i) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public HashMap obtenerDetalleCasoParaExportar(String nroCaso, String cdMatriz)
			throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("pv_nmcaso_i", nroCaso);
		map.put("pv_cdmatriz_i", cdMatriz);

		//Carga de datos del Caso
		CasoVO casoVO = (CasoVO) getBackBoneInvoke(map, "OBTENER_CASO_REG");
		
		HashMap datosAExportar = new HashMap();
		datosAExportar.put("NRO_CASO", casoVO.getNmCaso());
		datosAExportar.put("DSPROCESO", casoVO.getDesProceso());
		datosAExportar.put("DSPRIORIDAD", casoVO.getDesPrioridad());
		datosAExportar.put("DSNIVELATENCION", casoVO.getDsNivatn());
		//datosAExportar.put("CDUSUARIO", casoVO.getCdUsuario());
		datosAExportar.put("DSUNIECO", casoVO.getDesUnieco());
		datosAExportar.put("COLOR", casoVO.getColor());
		datosAExportar.put("CDORDENTRABAJO", casoVO.getCdOrdenTrabajo());
		datosAExportar.put("TIEMPORESTANTEPARAATENDER", casoVO.getTreSolucion());
		datosAExportar.put("DSMODULO", casoVO.getDesModulo());
		datosAExportar.put("TIEMPORESTANTEPARAESCALAR", casoVO.getTesCalamiento());
		datosAExportar.put("DSSTATUS", casoVO.getDesStatus());
		datosAExportar.put("CDFORMATOORDEN", casoVO.getCdFormatoOrden());

		return datosAExportar;
	}

	@SuppressWarnings("unchecked")
	public PagedList obtenerListaUsuariosAExportar (String nroCaso, String cdMatriz, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("pv_nmcaso_i", nroCaso);
		map.put("pv_cdmatriz_i", cdMatriz);

		return pagedBackBoneInvoke(map, "OBTENER_MCASO_USR", start, limit);
		
	}
	public HashMap<String, ItemVO> obtenerAtributosVariablesCasoParaExportar(
			String cdFormatoOrden, String cdSeccion)
			throws ApplicationException {
		HashMap map = new HashMap();
		HashMap<String, ItemVO> mapaAtributos = new HashMap<String, ItemVO>();
		//Carga de atributos variables del caso
		map.put("pv_cdformatoorden_i", ConvertUtil.nvl(cdFormatoOrden));
		map.put("pv_cdseccion_i", ConvertUtil.nvl(cdSeccion));
		PagedList pagedList = pagedBackBoneInvoke(map, "OBTENER_ATRIBUTOS_VARIABLES_CASO", 0, 9999);
		List<ExtJSAtributosVO> listaAtributos = pagedList.getItemsRangeList();
		int i = 0;
		while (i < listaAtributos.size()) {
			ExtJSAtributosVO atributosVO = (ExtJSAtributosVO)listaAtributos.get(i);
			String key = "Atributo_" + atributosVO.getCdFormatoOrden() + "_" + atributosVO.getCdSeccion() + "_" + atributosVO.getCdAtribu();
			ItemVO itemVO = new ItemVO();
			itemVO.setModulo(atributosVO.getDsSeccion());
			itemVO.setId(atributosVO.getDsAtribu());
			itemVO.setTexto(atributosVO.getValue());
			mapaAtributos.put(key, itemVO);
			i++;
		}
		return mapaAtributos;
	}

}