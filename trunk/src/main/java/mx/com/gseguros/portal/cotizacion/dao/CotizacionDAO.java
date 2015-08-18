package mx.com.gseguros.portal.cotizacion.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.ConfiguracionCoberturaDTO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.PInsertaTbasvalsitDTO;
import mx.com.gseguros.portal.cotizacion.model.PInsertaTconvalsitDTO;
import mx.com.gseguros.portal.cotizacion.model.PMovMpolisitDTO;
import mx.com.gseguros.portal.cotizacion.model.PMovTvalositDTO;
import mx.com.gseguros.portal.cotizacion.model.ParametroCotizacion;
import mx.com.gseguros.portal.general.model.ComponenteVO;

public interface CotizacionDAO {
	public void movimientoTvalogarGrupoCompleto(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String cdtipsit,
			String cdgrupo, String cdgarant, String status,
			Map<String, String> valores) throws Exception;

	public void movimientoTvalogarGrupo(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String cdtipsit,
			String cdgrupo, String cdgarant, String status, String cdatribu,
			String valor) throws Exception;

	public void movimientoMpolisitTvalositGrupo(Map<String, String> params)
			throws Exception;

	public void movimientoMpoligarGrupo(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String cdtipsit,
			String cdgrupo, String cdgarant, String status, String cdmoneda,
			String accion, String respvalogar) throws Exception;

	public Map<String, String> cargarDatosCotizacionGrupo(
			Map<String, String> params) throws Exception;

	public Map<String, String> cargarDatosCotizacionGrupo2(String cdunieco,
			String cdramo, String cdtipsit, String estado, String nmpoliza,
			String ntramite) throws Exception;

	public List<Map<String, String>> cargarGruposCotizacion(
			Map<String, String> params) throws Exception;

	public List<Map<String, String>> cargarGruposCotizacion2(String cdunieco,
			String cdramo, String estado, String nmpoliza) throws Exception;

	public Map<String, String> cargarDatosGrupoLinea(Map<String, String> params)
			throws Exception;

	public List<Map<String, String>> cargarTvalogarsGrupo(
			Map<String, String> params) throws Exception;

	public List<Map<String, String>> cargarTarifasPorEdad(
			Map<String, String> params) throws Exception;

	public List<Map<String, String>> cargarTarifasPorCobertura(
			Map<String, String> params) throws Exception;

	public String cargarNombreAgenteTramite(String ntramite) throws Exception;

	public Map<String, String> cargarPermisosPantallaGrupo(String cdsisrol,
			String status) throws Exception;

	public Map<String, String> obtieneTipoValorAutomovil(
			Map<String, String> params) throws Exception;

	public void guardarCensoCompletoMultisalud(String nombreArchivo,
			String cdunieco, String cdramo, String estado, String nmpoliza,
			String cdestadoCli, String cdmuniciCli, String cdplan1,
			String cdplan2, String cdplan3, String cdplan4, String cdplan5,
			String complemento) throws Exception;

	public List<Map<String, String>> cargarAseguradosExtraprimas(
			Map<String, String> params) throws Exception;

	public void guardarExtraprimaAsegurado(Map<String, String> params)
			throws Exception;

	public List<Map<String, String>> cargarAseguradosGrupo(
			Map<String, String> params) throws Exception;

	public void borrarMpoliperGrupo(Map<String, String> params)
			throws Exception;

	@Deprecated
	public Map<String, String> cargarTipoSituacion(Map<String, String> params)
			throws Exception;

	public Map<String, String> cargarTipoSituacion(String cdramo,
			String cdtipsit) throws Exception;

	public String cargarCduniecoAgenteAuto(Map<String, String> params)
			throws Exception;

	public Map<String, String> obtenerDatosAgente(String cdagente, String cdramo)
			throws Exception;

	public Map<String, String> obtenerParametrosCotizacion(
			ParametroCotizacion parametro, String cdramo, String cdtipsit,
			String clave4, String clave5) throws Exception;

	public Map<String, String> cargarAutoPorClaveGS(String cdramo,
			String clavegs, String cdtipsit, String cdsisrol) throws Exception;

	public Map<String, String> cargarClaveGSPorAuto(Map<String, String> params)
			throws Exception;

	public Map<String, String> cargarSumaAseguradaAuto(
			Map<String, String> params) throws Exception;

	public void movimientoMpolicotICD(Map<String, String> params)
			throws Exception;

	public List<Map<String, String>> cargarMpolicotICD(
			Map<String, String> params) throws Exception;

	public List<Map<String, String>> cargarConfiguracionGrupo(
			Map<String, String> params) throws Exception;

	public ComponenteVO cargarComponenteTatrisit(Map<String, String> params)
			throws Exception;

	public ComponenteVO cargarComponenteTatrigar(Map<String, String> params)
			throws Exception;

	public void validarDescuentoAgente(String tipoUnidad, String uso,
			String zona, String promotoria, String cdagente, String descuento)
			throws Exception;

	public List<Map<String, String>> impresionDocumentosPoliza(String cdunieco,
			String cdramo, String estado, String nmpoliza, String nmsuplem,
			String ntramite) throws Exception;

	public void movimientoTdescsup(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nsuplogi, String cdtipsup,
			Date feemisio, String nmsolici, Date fesolici, Date ferefere,
			String cdseqpol, String cdusuari, String nusuasus, String nlogisus,
			String cdperson, String accion) throws Exception;

	public DatosUsuario cargarInformacionUsuario(String cdusuari,
			String cdtipsit) throws Exception;

	public List<ComponenteVO> cargarTatrisit(String cdtipsit, String cdusuari)
			throws Exception;

	public List<ComponenteVO> cargarTatripol(String cdramo, String cdtipsit,
			String cdtippol) throws Exception;

	public Map<String, String> cargarClienteCotizacion(String cdunieco,
			String cdramo, String estado, String nmpoliza) throws Exception;

	public Map<String, String> cargarConceptosGlobalesGrupo(String cdunieco,
			String cdramo, String estado, String nmpoliza, String nmsuplem,
			String cdperpag) throws Exception;

	public String calculaNumeroPoliza(String cdunieco, String cdramo,
			String estado) throws Exception;

	public void movimientoPoliza(String cdunieco, String cdramo, String estado,
			String nmpoliza, String nmsuplem, String status, String swestado,
			String nmsolici, String feautori, String cdmotanu, String feanulac,
			String swautori, String cdmoneda, String feinisus, String fefinsus,
			String ottempot, String feini, String hhefecto, String fefin,
			String fevencim, String nmrenova, String ferecibo, String feultsin,
			String nmnumsin, String cdtipcoa, String swtarifi, String swabrido,
			String feemisio, String cdperpag, String nmpoliex, String nmcuadro,
			String porredau, String swconsol, String nmpolant, String nmpolnva,
			String fesolici, String cdramant, String cdmejred, String nmpoldoc,
			String nmpoliza2, String nmrenove, String nmsuplee,
			String ttipcamc, String ttipcamv, String swpatent, String pcpgocte,
			String tipoflot, String agrupador, String accion) throws Exception;

	public void movimientoTvalopol(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String status,
			Map<String, String> valores) throws Exception;

	public void procesarCenso(String nombreProcedure, String cdusuari,
			String cdsisrol, String nombreCenso, String cdunieco,
			String cdramo, String estado, String nmpoliza, String cdtipsit,
			String cdagente, String codpostal, String cdedo, String cdmunici,
			String complemento
			) throws Exception;

	public void actualizaMpolisitTvalositGrupo(String cdunieco, String cdramo,
			String estado, String nmpoliza, String cdgrupo, String nombreGrupo,
			String cdplan, Map<String, String> valores) throws Exception;

	public void valoresPorDefecto(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsituac, String nmsuplem,
			String cdgarant, String cdtipsup) throws Exception;

	public void movimientoMpoliper(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsituac, String cdrol,
			String cdperson, String nmsuplem, String status, String nmorddom,
			String swreclam, String accion, String swexiper) throws Exception;

	public void movimientoMpoliage(String cdunieco, String cdramo,
			String estado, String nmpoliza, String cdagente, String nmsuplem,
			String status, String cdtipoag, String porredau, String nmcuadro,
			String cdsucurs, String accion, String ntramite, String porparti)
			throws Exception;

	public void tarificaEmi(String cdusuari, String cdelemen, String cdunieco,
			String cdramo, String estado, String nmpoliza, String nmsituac,
			String nmsuplem, String cdtipsit) throws Exception;

	public List<Map<String, String>> obtenerTiposSituacion() throws Exception;

	public List<Map<String, String>> cargarSituacionesGrupo(String cdunieco,
			String cdramo, String estado, String nmpoliza, String nmsuplem,
			String cdgrupo) throws Exception;

	public void actualizaValoresSituacion(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String nmsituac,
			Map<String, String> valores) throws Exception;

	public void validarCambioZonaGMI(String cdunieco, String cdramo,
			String cdtipsit, String estado, String nmpoliza, String nmsuplem,
			String nmsituac, String codpostal) throws Exception;

	public void validarEnfermedadCatastGMI(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String nmsituac,
			String circHosp) throws Exception;

	public Map<String, String> cargarRetroactividadSuplemento(String cdunieco,
			String cdramo, String cdtipsup, String cdusuari, String cdtipsit)
			throws ApplicationException, Exception;

	public Map<String, String> cargarSumaAseguradaRamo5(String cdtipsit,
			String clave, String modelo, String cdsisrol) throws Exception;

	public Map<String, String> cargarDatosComplementariosAutoInd(
			String cdunieco, String cdramo, String estado, String nmpoliza)
			throws ApplicationException, Exception;

	public Map<String, String> cargarTvalopol(String cdunieco, String cdramo,
			String estado, String nmpoliza) throws ApplicationException,
			Exception;

	public Map<String, String> cargarTvalosit(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsituac)
			throws ApplicationException, Exception;

	public void actualizaMpolizas(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String swestado,
			String nmsolici, Date feautori, String cdmotanu, Date feanulac,
			String swautori, String cdmoneda, Date feinisus, Date fefinsus,
			String ottempot, Date feefecto, String hhefecto, Date feproren,
			Date fevencim, String nmrenova, Date ferecibo, Date feultsin,
			String nmnumsin, String cdtipcoa, String swtarifi, String swabrido,
			Date feemisio, String cdperpag, String nmpoliex, String nmcuadro,
			String porredau, String swconsol, String nmpolant, String nmpolnva,
			Date fesolici, String cdramant, String cdmejred, String nmpoldoc,
			String nmpoliza2, String nmrenove, String nmsuplee,
			String ttipcamc, String ttipcamv, String swpatent, String nmpolmst,
			String pcpgocte) throws Exception;

	public void borrarAgentesSecundarios(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem) throws Exception;

	public Map<String, String> cargarConfiguracionCotizacion(String cdramo,
			String cdtipsit, String cdusuari) throws ApplicationException,
			Exception;

	public void guardarConfiguracionCotizacion(String cdramo, String cdtipsit,
			String cdusuari, Map<String, String> valores) throws Exception;

	public Map<String, String> cargarRangoDescuentoRamo5(String cdtipsit,
			String cdagente, String negocio, String cdsisrol, String cdusuari)
			throws ApplicationException, Exception;

	public List<List<Map<String, String>>> cargarParamerizacionConfiguracionCoberturas(
			String cdtipsit, String cdsisrol, String negocio,
			String tipoServicio, String modelo, String tipoPersona,
			String submarca, String clavegs) throws Exception;

	public Map<String, String> cargarDatosVehiculoRamo5(String cdunieco,
			String cdramo, String estado, String nmpoliza)
			throws ApplicationException, Exception;

	public void borrarMpoliperTodos(String cdunieco, String cdramo,
			String estado, String nmpoliza) throws Exception;

	public void guardarTbasvalsit(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsituac, String nmsuplem,
			String status, String cdtipsit, Map<String, String> valores)
			throws Exception;

	public void guardarTconvalsit(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsituac, String nmsuplem,
			String status, String cdtipsit, Map<String, String> valores)
			throws Exception;

	public void borrarTbasvalsit(String cdunieco, String cdramo, String estado,
			String nmpoliza) throws Exception;

	public void borrarTconvalsit(String cdunieco, String cdramo, String estado,
			String nmpoliza) throws Exception;

	public void movimientoMpolisit(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsituac, String nmsuplem,
			String status, String cdtipsit, String swreduci, String cdagrupa,
			String cdestado, Date fefecsit, Date fecharef, String cdgrupo,
			String nmsituaext, String nmsitaux, String nmsbsitext,
			String cdplan, String cdasegur, String accion) throws Exception;

	public void movimientoTvalosit(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsituac, String nmsuplem,
			String status, String cdtipsit, Map<String, String> valores,
			String accion) throws Exception;

	public void clonarPersonas(String cdelemen, String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsituac, String cdtipsit,
			Date fecha, String cdusuari, String nombre1, String nombre2,
			String apellido1, String apellido2, String sexo, Date fenacimi,
			String parentesco) throws Exception;

	public List<Map<String, String>> cargarResultadosCotizacion(
			String cdusuari, String cdunieco, String cdramo, String estado,
			String nmpoliza, String cdelemen, String cdtipsit) throws Exception;

	public List<Map<String, String>> cargarParametrizacionExcel(String proceso,
			String cdramo, String cdtipsit) throws Exception;

	public String cargarClaveTtapvat1(String cdtabla, String otvalor,
			Map<String, List<Map<String, String>>> buffer) throws Exception;

	public List<Map<String, String>> cargarResultadosCotizacionAutoFlotilla(
			String cdunieco, String cdramo, String estado, String nmpoliza)
			throws Exception;

	public List<Map<String, String>> cargarDetallesCotizacionAutoFlotilla(
			String cdunieco, String cdramo, String estado, String nmpoliza,
			String cdperpag) throws Exception;

	public List<Map<String, String>> cargarDetallesCoberturasCotizacionAutoFlotilla(
			String cdunieco, String cdramo, String estado, String nmpoliza,
			String cdperpag) throws Exception;

	public String cargarTabuladoresGMIParche(String circulo, String cdatribu)
			throws Exception;

	public void sigsvdefEnd(String cdunieco, String cdramo, String estado,
			String nmpoliza, String nmsituac, String nmsuplem, String cdgarant,
			String cdtipsup) throws Exception;

	public void borrarMpoliperSituac0(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String cdrol)
			throws Exception;

	public String cargarTipoVehiculoRamo5(String clave) throws Exception;

	public Map<String, String> cargarDetalleNegocioRamo5(String negocio,
			String cdramo, String cdtipsit, String cdsisrol, String cdusuari)
			throws Exception;

	public Map<String, List<Map<String, String>>> cargarConfiguracionTvalositFlotillas(
			String cdramo, String cdtipsit, String negocio) throws Exception;

	public boolean cargarBanderaCambioCuadroPorProducto(String cdramo)
			throws Exception;

	public Map<String, String> cargarRangoDescuentoRamo5TodasSituaciones(
			String cdagente, String negocio, String cdsisrol, String cdusuari)
			throws Exception;

	public void validarCodpostalTarifa(String codpostal, String cdtipsit)
			throws Exception;

	public boolean validaDomicilioCotizacionTitular(Map<String, String> params)
			throws Exception;

	public boolean validarCuadroComisionNatural(String cdunieco, String cdramo,
			String estado, String nmpoliza) throws Exception;

	/**
	 * Aplica los parches que se piden en cotizacion
	 */
	public void aplicarAjustesCotizacionPorProducto(String cdunieco,
			String cdramo, String estado, String nmpoliza, String cdtipsit,
			String tipocot) throws Exception;

	public List<List<Map<String, String>>> cargarParamerizacionConfiguracionCoberturasRol(
			String cdtipsit, String cdsisrol) throws Exception;

	public String cargarPorcentajeCesionComisionAutos(String cdunieco,
			String cdramo, String estado, String nmpoliza) throws Exception;

	public void ejecutaValoresDefectoTarificacionConcurrente(String cdunieco,
			String cdramo, String estado, String nmpoliza, String nmsuplem,
			String nmsituac, String tipotari, String cdperpag) throws Exception;

	public void movimientoMpolisitLote(List<PMovMpolisitDTO> lista)
			throws Exception;

	public void movimientoTvalositLote(List<PMovTvalositDTO> lista)
			throws Exception;

	public void movimientoTbasvalsitLote(List<PInsertaTbasvalsitDTO> lista)
			throws Exception;

	public void movimientoTconvalsitLote(List<PInsertaTconvalsitDTO> lista)
			throws Exception;

	public void movimientoMpoliagr(String cdunieco, String cdramo,
			String estado, String nmpoliza, String cdagrupa, String nmsuplem,
			String status, String cdperson, String nmorddom, String cdforpag,
			String cdbanco, String cdsucurs, String cdcuenta, String cdrazon,
			String swregula, String cdperreg, Date feultreg, String cdgestor,
			String cdrol, String cdbanco2, String cdsucurs2, String cdcuenta2,
			String cdtipcta, String cdtipcta2, String cdpagcom,
			String nmpresta, String nmpresta2, String cdbanco3,
			String cdsucurs3, String cdcuenta3, String cdtipcta3,
			String nmpresta3, String nmcuenta, String accion) throws Exception;

	public Map<String, String> validarReemplazoDocumentoCotizacion(
			String cdunieco, String cdramo, String estado, String nmpoliza)
			throws Exception;

	public void grabarEvento(StringBuilder sb, String cdmodulo,
			String cdevento, Date fecha, String cdusuari, String cdsisrol,
			String ntramite, String cdunieco, String cdramo, String estado,
			String nmpoliza, String nmsolici, String cdagente,
			String cdusuariDes, String cdsisrolDes, String status)
			throws Exception;

	public void ejecutaValoresDefectoConcurrente(String cdunieco,
			String cdramo, String estado, String nmpoliza, String nmsuplem,
			String nmsituac, String tipotari, String cdperpag) throws Exception;

	public void ejecutaTarificacionConcurrente(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem, String nmsituac,
			String tipotari, String cdperpag) throws Exception;

	public void actualizaValoresDefectoSituacion(String cdunieco,
			String cdramo, String estado, String nmpoliza, String nmsuplem)
			throws Exception;

	public void procesaLayoutCensoMultisalud(String nombreArchivo,
			String cdunieco, String cdramo, String estado, String nmpoliza,
			String cdestadoCli, String cdmuniciCli, String cdplan1,
			String cdplan2, String cdplan3, String cdplan4, String cdplan5,
			String complemento) throws Exception;
	
	public String guardarConfiguracionGarantias(
			String cdramo
			,String cdtipsit
			,String cdplan
			,String cdpaquete
			,boolean nuevo
			,String dspaquete
			,String derpol
			,List<ConfiguracionCoberturaDTO>lista
			)throws Exception;
	
	public List<Map<String,String>> obtenerCoberturasPlanColec(
			String cdramo
			,String cdtipsit
			,String cdplan
			)throws Exception;

	public List<Map<String,String>> obtieneDatosContratantePoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception;
	
	public String insercionDocumentosParametrizados(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			)throws Exception;
	
	public void actualizaCesionComision(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;

	public void actualizaAseguradosColectivo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdpostal
			,String cdedo
			,String cdmunici
			)throws Exception;
}