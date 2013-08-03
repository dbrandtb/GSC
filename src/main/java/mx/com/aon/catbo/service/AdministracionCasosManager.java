package mx.com.aon.catbo.service;

import java.util.HashMap;
import java.util.List;

import com.biosnet.ice.ext.elements.form.ComboControl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.CasoDetalleVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.model.ItemVO;
import mx.com.aon.catbo.model.ReasignacionCasoVO;
import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

/**
 * User: gabrielforradellas
 * Date: Sep 2, 2008
 * Time: 10:42:12 PM
 */
public interface AdministracionCasosManager {
	
	public PagedList obtenerNumerosCasos(String desmodulo, int start, int limit) throws ApplicationException;
	
	public String borrarNumCasos(String pv_cdnumecaso_i) throws ApplicationException;
    
	public CasoVO getObtenerNumeroCaso(String pv_cdnumecaso_i) throws ApplicationException;
    
	public String guardarNumerosCaso(String pv_cdnumecaso_i, String pv_indnumer_i,String  pv_cdmodulo_i,String pv_nmcaso_i  ,String  pv_dssufpre_i, String pv_nmdesde_i , String pv_nmhasta_i  )throws ApplicationException;
	
	public TableModelExport getModel(String desmodulo) throws ApplicationException;
    
	public PagedList obtieneTareas(String pv_dsproceso_i, String pv_dsmodulo_i, String pv_cdpriord_i, int start, int limit) throws ApplicationException;
   
	public CasoVO getObtenerTareas(String pv_cdproceso_i) throws ApplicationException;
	
    public String borrarTarea(String pv_cdproceso_i) throws ApplicationException;
   
    public String guardarTarea(String pv_cdproceso_i,String  pv_estatus_i,String pv_cdmodulo_i,String pv_cdpriord_i,String pv_indesemaforo_i)throws ApplicationException;
    
    public String guardarTiempo(String pv_cdproceso_i,String pv_cdnivatn_i,String pv_nmcant_desde_i,String pv_nmcant_hasta_i,String pv_tunidad_i, String pv_nmvecescompra_i)throws ApplicationException;
    
    public String borrarCaso(String pv_nmcaso_i) throws ApplicationException;
    
    public PagedList getObtenerResponsable(String pv_nmcaso_i, String cdmatriz,int start,int limit) throws ApplicationException;      

    
   // public PagedList obtenerCasos(String pv_nmcaso_i, String pv_cdorden_i, String pv_dsproceso_i, String pv_feingreso_i, String pv_cdpriord_i, String pv_cdperson_i, String pv_dsperson_i, String pv_cdusuario_i, int start, int limit) throws ApplicationException;
    
    public CasoDetalleVO getObtenerCaso(String pv_nmcaso_i, String cdmatriz) throws ApplicationException;

    
    public String guardarCompra(String pv_nmcaso_i,String  pv_cdusuario_i,String  pv_cdnivatn_i,String pv_nmcompra_i,String pv_tunidad_i)throws ApplicationException;
    
    public String validaCompraTiempo(String pv_cdproceso_i, int pv_cdnivatn_i,String pv_nmcaso_i)throws ApplicationException;
    
    public CasoVO obtenerCompra(String pv_cdproceso_i) throws ApplicationException;
    
    public CasoVO obtenerEncabezadoMovimientos(String pv_nmcaso_i) throws ApplicationException;
    
    public PagedList obtenerMovimientos(String pv_nmcaso_i, int start, int limit) throws ApplicationException;
    
    public String borrarMovimientos(String pv_nmcaso_i, String pv_nmovimiento_i) throws ApplicationException;
    
    public WrapperResultados guardarMovimineto(String pv_nmcaso_i, String pv_cdpriord_i, String pv_cdstatus_i, String pv_dsobservacion_i, String pv_cdusuario_i)throws ApplicationException;
    
    public String guardarReasignacion(String pv_nmcaso_i, String pv_cdusumov_i, String pv_cdusuario_i, String pv_cdrolmat_i)throws ApplicationException;

    public CasoVO getObtenerMovimientoCaso (String pv_nmcaso_i, String pv_nmovimiento_i) throws ApplicationException;

    public PagedList obtieneArchivosCaso(String pv_nmcaso_i, String pv_nmovimiento_i , int start, int limit) throws ApplicationException;

    public String borraArchivoCaso(String pv_nmcaso_i, String pv_nmovimiento_i, String pv_nmarchivo_i) throws ApplicationException;
    
    public String guaradarArchivoCaso(String pv_nmcaso_i, String pv_nmovimiento_i, String pv_nmarchivo_i, String pv_dsarchivo_i, String pv_cdtipoar_i) throws ApplicationException;
   
    public PagedList obtieneResponsablesCaso(String pv_cdproceso_i , int start, int limit) throws ApplicationException;
    
    public PagedList obtienePolizas(String pv_cdelemento_i,String pv_cdperson_i, int start,int limit) throws ApplicationException;
    
    public ResultadoGeneraCasoVO guardarCasos(String pv_cdmatriz_i,
			String pv_cdusuario_i, String pv_cdunieco_i, String pv_cdramo_i,
			String pv_estado_i, String pv_nmsituac_i, String pv_nmsituaext_i,
			String pv_nmsbsitext_i,String pv_nmpoliza_i, String pv_nmpoliex_i,
			String pv_cdperson_i, String pv_dsmetcontact_i, String pv_ind_poliza_i,	
			String pv_cdpriord_i, String pv_cdproceso_i, String pv_dsobservacion_i )throws ApplicationException;

    
    
    public String obtenerIdentificadorCaso(String pv_cdmodulo_i) throws ApplicationException;

    
    /**
	 *  Obtiene una lista de formatos de casos para la exportacion a un formato predeterminado.
	 * 
	 *  @param descripcion: parametro con el que se realiza la busqueda.
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	public TableModelExport getModelCasos(String pv_nmcaso_i, String pv_cdorden_i, String pv_dsproceso_i, String pv_feingreso_i, String pv_cdpriord_i, String pv_cdperson_i, String pv_dsperson_i,String pv_cdusuario_i) throws ApplicationException;
	




	public PagedList obtieneListaResponsablesCaso(String cdproceso,String cdmatriz, int start,
			int limit) throws ApplicationException;
	
	public PagedList obtenerSufijoPrefijo(String pv_cdmodulo_i, int start,int limit) throws ApplicationException;

	public PagedList obtenerClientes(String cdelemento, String cdideper, String cdperson, String dsnombre, String dsapellido, String dsapellido1,int start,int limit) throws ApplicationException;

	public PagedList obtenerAtributoSeccion(String pv_cdformatoorden_i, String pv_cdseccion_i, int start,int limit) throws ApplicationException;
	
	public List<ComboControl> obtenerAtributoSeccionComboControl(String pv_cdformatoorden_i, String pv_cdseccion_i) throws ApplicationException;

	public PagedList obtieneSeccionesOrden(String pv_cdformatoorden_i, int start,int limit) throws ApplicationException;
	
	public TableModelExport getModelMCaso(String pv_nmcaso_i)throws ApplicationException;
	
	public PagedList obtenerUsuariosResponsablesMovCaso(String pv_nmcaso_i,String pv_nmovimiento_i, int start, int limit) throws ApplicationException;
	
	public ResultadoGeneraCasoVO guardarNuevoCaso(CasoVO casoVO,FormatoOrdenVO formatoOrdenVO)throws Exception;

	public String guardaMovimientoGenerico (String numeroCaso, String cdusuario, String cdnivatn, String cdstatus, String cdmodulo, String cdpriord, String nmcompra, String tunidad, String dsobservacion) throws ApplicationException;

	public List obtenerReasignacionCaso(String cdModulo) throws ApplicationException;
	
	public String guardarReasignacionCaso (CasoVO casoVO) throws ApplicationException;

    public String guardaSuplente(int cdMatriz, int  cdNivatn ,String cdUsuarioOld, String cdUsuarioNew,String nmcaso,String cdUsuario ) throws ApplicationException;

    public String guardarReasignacion1(String pv_nmcaso_i, String pv_cdusumov_i,
              String pv_cdusuari_old_i, String pv_cdusuari_new_i)
              throws ApplicationException ;


    public PagedList obtenerReasignacionCasoUsr(String cdUsuario, int start, int limit) throws ApplicationException;
    
    public PagedList obtenerReasignacionCasoUsrRspnsbl(String desUsuario,String cdUsuarioOld, int start,int limit) throws ApplicationException;

    public String guardarReasignacionCasoUsr (CasoVO casoVO) throws ApplicationException;
    
	public String guardarMovimientoCaso (CasoVO casoVO) throws ApplicationException;

	public String guardaReasignacionCasoNv(CasoVO casoVO, List<ReasignacionCasoVO> listaReasignacionCasoVO) throws ApplicationException;

	public HashMap obtenerDetalleCasoParaExportar (String nroCaso, String cdMatriz) throws ApplicationException;

	public HashMap<String, ItemVO> obtenerAtributosVariablesCasoParaExportar (String cdFormatoOrden, String cdSeccion) throws ApplicationException;

	public PagedList obtenerListaUsuariosAExportar (String nroCaso, String cdMatriz, int start, int limit) throws ApplicationException;
}