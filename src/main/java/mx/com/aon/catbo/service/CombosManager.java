package mx.com.aon.catbo.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.ElementoComboBoxVO;
import mx.com.aon.portal.service.PagedList;


import java.util.ArrayList;
import java.util.List;

/**
 * Interface con servicios para todos los combos del proyecto.
 *
 */
public interface CombosManager {
    //public ArrayList<ElementoComboBoxVO> obtenerProductos() throws ApplicationException;
	@SuppressWarnings("unchecked")
	public List obtenerProductos() throws ApplicationException;

    public PagedList comboTipoSituacion () throws ApplicationException;

    public PagedList comboCoberturas () throws ApplicationException;

    public PagedList comboAseguradoras() throws ApplicationException;

    public PagedList comboSecciones() throws ApplicationException;

    public PagedList comboEstados() throws ApplicationException;

    public PagedList obtenerClientesCorp() throws ApplicationException;
    
    public List comboClientes() throws ApplicationException;
    
    public List comboPadres() throws ApplicationException;

    public List comboTiposNivel() throws ApplicationException;
    
    public List comboRamos() throws ApplicationException;
    
    public List comboRamos2(String cdunieco, String cdelemento, String cdramo) throws ApplicationException;
    
    public List comboSubRamos() throws ApplicationException;
    
    public List comboProductos() throws ApplicationException;
    
    public List comboIdiomas() throws ApplicationException;
    
    public List comboUsuarios() throws ApplicationException;
    
    public List comboProcesos() throws ApplicationException;
    
    public List comboTemporalidades() throws ApplicationException;
    
    public List comboRegiones() throws ApplicationException;
    
    public List comboEstadosDescuentos() throws ApplicationException;
    
    public List comboProductosDescuentos() throws ApplicationException;
    
    public List comboTiposDscto() throws ApplicationException;

    public List comboPlanes(String cdElemento,String cdUniEco,String cdRamo,String cdTipSit) throws ApplicationException;

	public List comboConfAlertasAutoClientes() throws ApplicationException ;

	public List comboConfAlertasAutoProcesos() throws ApplicationException ;

	public List comboConfAlertasAutoTemporalidad() throws ApplicationException ;

	public List comboConfAlertasAutoRol() throws ApplicationException ;

	public List comboConfAlertasAutoTipoRamo() throws ApplicationException ;

    public List comboSiNo() throws ApplicationException;

    public List comboFrecuencias() throws ApplicationException;

    public List comboConceptosProducto() throws ApplicationException;

    public List comboProductosAyuda(String cdUnieco, String cdRamo, String subRamo) throws ApplicationException;

    public List comboCoberturasProducto(String cdRamo) throws ApplicationException;

    public List comboLineasCaptura() throws ApplicationException;
    
    public List comboGrupoPersonasCliente(String cdElemento) throws ApplicationException; //no existe ha crear AgrupacionGrupoPersonas
    
    public List comboLineasAtencion() throws ApplicationException;
    
    public List comboEjecutivosAon() throws ApplicationException;
    
    public List comboEstadosEjecutivo() throws ApplicationException;
    
    public List comboPolizasPorAseguradora (String cdUniEco) throws ApplicationException;
   
    public List comboReciboPorPolizaPorAseguradora (String cdUniEco, String cdRamo, String nmPoliza) throws ApplicationException;

    public List comboVinculosPadrePorEstructura (String codigoEstructura) throws ApplicationException;
    
    public List comboClientesCorporativo (String cdPerson) throws ApplicationException;
    
    public List comboProductosEjecutivosCuenta (String cdPerson, String cdTipRam ) throws ApplicationException;

    public List comboAseguradorasCliente(String cliente) throws ApplicationException;

    public List comboProductosAseguradoraCliente(String cliente, String aseguradora) throws ApplicationException;

    public List comboRamosCliente(String cliente) throws ApplicationException;

    public List comboTiposAgrupacion() throws ApplicationException;

    public List comboEstadosAgrupacionPoliza() throws ApplicationException;

    public List comboTiposRamoClienteEjecutivoCuenta(String cdElemento) throws ApplicationException;
    
    public List comboAseguradoraPorElemento (String cdElemento) throws ApplicationException;

    public List comboProductosPorAseguradoraYCliente(String codigoAseguradora, String codigoElemento, String codigoRamo) throws ApplicationException;

    public List comboPapelesPorCliente (String codigoElemento) throws ApplicationException;

    public List comboTipoPersonaJ () throws ApplicationException;

    public List comboBloques () throws ApplicationException;
    
    @SuppressWarnings("unchecked")
	public List comboIndicadorNumeracionNroIncisos() throws ApplicationException;
    
    @SuppressWarnings("unchecked")
	public List comboIndicador_SP_NroIncisos() throws ApplicationException;
    
    @SuppressWarnings("unchecked")
	public List comboFormaCalculoFolioNroIncisos() throws ApplicationException;
    
    public List comboNacionalidad () throws ApplicationException;
    
    public List comboEstadoCivil () throws ApplicationException;
    
	public List comboTipoEmpresa () throws ApplicationException;

	public List comboTipoIdentificador () throws ApplicationException;
	
	public List comboTipoDomicilio () throws ApplicationException;

	public List comboPaises () throws ApplicationException;

	public List comboEstados (String codigoPais) throws ApplicationException;

	public List comboMunicipios (String codigoPais, String codigoEstado) throws ApplicationException;

	public List comboColonias (String codigoPais, String codigoEstado, String codigoMunicipio) throws ApplicationException;

	public List comboTiposTelefono  () throws ApplicationException;

	public List comboGrupoCorporativo (String cdElemento) throws ApplicationException;

	public List comboEstadosCorporativos () throws ApplicationException;
	
	public List comboGrupoNivelCorporativo() throws ApplicationException;

	public List comboSexo () throws ApplicationException;
	
	public List comboRazonCancelacion() throws ApplicationException;
	
	public List comboMetodoCancelacion() throws ApplicationException;

    public List comboPeriodosGracia () throws ApplicationException;
	
    public List comboTiposDocumento (String cdElemento, String cdUniEco, String cdRamo) throws ApplicationException;
	
	public List comboSeccionesOrden() throws ApplicationException;
	
	public List comboTiposObjetos() throws ApplicationException;

    public List comboSeccionFormato (String cdFormato) throws ApplicationException;
	
	public List comboBloquesConfiguraAtributosFormatoOrdenTrabajo () throws ApplicationException;
	
	public List comboCampoBloques(String otTipo,String cdBloque) throws ApplicationException;
	
	public List comboFormatosCampo() throws ApplicationException;
	
	//************* Asociar formato de orden de trabajo *******************
	public List comboProceso () throws ApplicationException;
	
	public List comboFormato () throws ApplicationException;
	
	public List comboFormaCalculoFolio () throws ApplicationException;
	
	//public List comboFormaCalculoFolioAseguradora () throws ApplicationException;
	//************* Asociar formato de orden de trabajo *******************
	
	public List comboTipoError () throws ApplicationException;

	public List comboClientesCorpPorUsuario (String cdUsuario) throws ApplicationException;
	
	public List comboProductosTipoRamoCliente (String cdElemento, String cdTramo) throws ApplicationException;

	public List comboTipoSituacionProducto(String cdRamo)throws ApplicationException;
	
    public List comboInstrumentosPago () throws ApplicationException;
	
	public List comboBancos () throws ApplicationException;
	
	public List comboTiposTarjeta () throws ApplicationException;

	public List comboAseguradoraPorClienteYProducto (String cdElemento, String cdRamo) throws ApplicationException;
	
	public List comboIndicadorSINO () throws ApplicationException;

	public List comboTipoSituacionPorProductoYPlan (String cdRamo, String cdPlan) throws ApplicationException;

	public List comboTipoGarantiaPorProductoYSituacion(String cdRamo, String cdTipSit) throws ApplicationException;
	
	public List comboPlanesProducto(String cdRamo) throws ApplicationException;
	
	public List comboPersonas () throws ApplicationException;

	public List comboGenerico(String idTablaLogica) throws ApplicationException;
	
	public List comboFormaCalculo() throws ApplicationException;
	
	public List comboFormaAtributo() throws ApplicationException;
	
	public List comboNroPolizas(String cdUniEco, String cdRamo, String cdPlan, String cdElemento) throws ApplicationException;
	
	public List comboTiposRenovacion() throws ApplicationException;
	
	public List comboAccionRenovacionRol() throws ApplicationException;
	
	public List comboAccionRenovacionPantalla() throws ApplicationException;
	
	public List comboAccionRenovacionCampo(String cdTitulo) throws ApplicationException;
	
	public List comboAccionRenovacionAccion() throws ApplicationException;
	
	public List comboAccionRenovacionRoles(String cdRenova) throws ApplicationException;
	
	@SuppressWarnings("unchecked")
	public List obtenerRamosPorCliente(String cdElemento) throws ApplicationException;
	
	public List comboTipoObjeto() throws ApplicationException;
	
	public List obtenerFormasPago(String otClave1) throws ApplicationException;
	
	public List obtenerInstrumentosPago() throws ApplicationException;
	
	public List obtenerRoles() throws ApplicationException;
	
	public List obtenerPersonas(String sesionUsuario) throws ApplicationException;
	
	public List obtenerIncisos(String cdUniEco,String cdRamo,String estado,String nmPoliza,String nmSituac) throws ApplicationException;
	
	public List obtenerCoberturasProducto(String cdUniEco,String cdRamo,String nmPoliza) throws ApplicationException;
	
	public List obtenerObjetosRamos(String cdRamo,String cdTipSit) throws ApplicationException;
	
	public List comboTipoGuion() throws ApplicationException;
	
	public List comboNotificacionRegion(String cdNotificacion) throws ApplicationException;
	
	public List comboTipoMetodoEnvio() throws ApplicationException;
	
	public List comboProcesosCat() throws ApplicationException;
	
	public List comboProcesosCatMatriz() throws ApplicationException;
	
	public List comboClientesCorpBO() throws ApplicationException;
	
	public List obtenerDatosCatalogo(String pv_cdtabla_i) throws ApplicationException;
	
	public List datosCatalogo() throws ApplicationException;
	
	public List catalogoPrioridad() throws ApplicationException;
	
	public List obtieneNivelAtencion() throws ApplicationException;
	
	public List obtieneUnidadTiempo() throws ApplicationException;
	
	public List obtieneRol() throws ApplicationException;
	
	public List obtieneTiposArchivos() throws ApplicationException;
	
	public List obtieneDatosCatalogo(String pv_cdtabla_i) throws ApplicationException;
	
	public List matrizObtieneRol() throws ApplicationException;
		
	public List matrizResponsables(String cdproceso) throws ApplicationException;
	
	public List matrizNivelAtencion() throws ApplicationException;
	
	public List matrizUnidadTiempo() throws ApplicationException;
	
	public List comboIndicadorAviso() throws ApplicationException;
	
	public List obtenerTareas(String cdElemento, String cdUniEco, String cdRamo) throws ApplicationException;
	
	public List obtenerAseguradorasProdCorp( String cdProceso, String cdElemento, String cdRamo) throws ApplicationException;
	
	public List obtenerPrioridades() throws ApplicationException;
	
    public List obtenerModulos() throws ApplicationException;
	
    public List obtenerDatosCatalogoDep(String cdTabla, String valor1, String valor2) throws ApplicationException;
	
    public List obtenerTareaPrioridad() throws ApplicationException;
    
    public List obtenerTareaEstatus() throws ApplicationException;
    
    public List obtenerMonedas() throws ApplicationException;
    
    public List comboNivelAtencionMatriz(String cdMatriz) throws ApplicationException;
    
    public List obtieneDatosModulo(String pv_cdtabla_i) throws ApplicationException;
    
    public List obtenerStatusCaso() throws ApplicationException;
    
    public List obtenerUsuariosReemplazantes(String cdmatriz, String cdnivatn) throws ApplicationException;
    
    public List obtenerRolesUsuarios() throws ApplicationException;
    
    public List obtenerUsuarios(String pv_cdsisrol_i) throws ApplicationException;
    
    public List comboEncuestas() throws ApplicationException;
    
    public List comboModuloConfigEncuestas() throws ApplicationException;
	
    public List comboUsuariosEncuestas(String cdModulo) throws ApplicationException;
    
    public List comboObtenerProductosAseguradoraEncuesta (String pv_cdunieco_i) throws ApplicationException; 
    
    public List comboCampanEncuestas()throws ApplicationException;
    
    public List comboClientesCorpoAseguradoraProducto(String cdUniEco, String cdRamo)throws ApplicationException;
    
    public List obtienePais(String pv_cdtabla_i) throws ApplicationException;
    
    public List obtieneMes(String pv_cdtabla_i) throws ApplicationException;    
   
    public List getListas() throws ApplicationException;    

	public List getListas(String cdTabla, String valAnter, String valAntAnt) throws ApplicationException;
	
	//public List comboUsuarioResponsable (String pv_nmconfig_i) throws ApplicationException;
	
	public List comboModuloEnc(String cdUniEco, String cdRamo) throws ApplicationException;

	public List comboCampanhaEnc(String cdUniEco, String cdRamo, String cdModulo) throws ApplicationException;

	public List comboTemaEnc(String cdUniEco, String cdRamo, String cdModulo, String cdCampan) throws ApplicationException;

	public List comboEncuestaEnc(String cdUniEco, String cdRamo, String cdModulo, String cdCampan, String cdProceso, String nmConfig) throws ApplicationException;
	
}