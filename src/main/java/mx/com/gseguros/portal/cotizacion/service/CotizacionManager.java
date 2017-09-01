package mx.com.gseguros.portal.cotizacion.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.consultas.model.AseguradosFiltroVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.cotizacion.model.ParametroCotizacion;
import mx.com.gseguros.portal.general.model.ComponenteVO;


public interface CotizacionManager
{
	
	public void movimientoTvalogarGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdgrupo
			,String cdgarant
			,String status
			,String cdatribu
			,String valor)throws Exception;
	
	public void movimientoMpolisitTvalositGrupo(
			String  cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo
			,String otvalor06
			,String otvalor07
			,String otvalor08
			,String otvalor09
			,String otvalor10
			,String otvalor11
			,String otvalor12
			,String otvalor22
			,String otvalor23
			,String otvalor24
			,String otvalor25
			,String otvalor26
			,String otvalor13
			,String otvalor16
			)throws Exception;
	
	public void movimientoMpoligarGrupo(
			String  cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdgrupo
			,String cdgarant
			,String status
			,String cdmoneda
			,String accion
			,String respvalogar)throws Exception;
	
	public Map<String,String> cargarDatosCotizacionGrupo(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String ntramite) throws Exception;
	
	public ManagerRespuestaSmapVO cargarDatosCotizacionGrupo2(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String ntramite);
	
	public Map<String,String> cargarDatosCotizacionGrupoEndoso(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String ntramite) throws Exception;
	
	public List<Map<String,String>>cargarGruposCotizacion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception;
	
	public ManagerRespuestaSlistVO cargarGruposCotizacion2(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza);

	public ManagerRespuestaSlistVO cargarGruposCotizacionReexpedicion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza);
	
	public Map<String,String>cargarDatosGrupoLinea(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo)throws Exception;
	
	public List<Map<String,String>>cargarTvalogarsGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo)throws Exception;
	
	public List<Map<String,String>>cargarTarifasPorEdad(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdplan
			,String cdgrupo
			,String cdperpag)throws Exception;
	
	public List<Map<String,String>>cargarTarifasPorCobertura(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdplan
			,String cdgrupo
			,String cdperpag)throws Exception;
	
	public String cargarNombreAgenteTramite(String ntramite)throws Exception;
	
	public Map<String,String>cargarPermisosPantallaGrupo(String cdsisrol,String status)throws Exception;
	
	public void guardarCensoCompletoMultisalud(
			String nombreArchivo
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdedo
			,String cdmunici
			,String cdplan1
			,String cdplan2
			,String cdplan3
			,String cdplan4
			,String cdplan5
			,String complemento
			)throws Exception;

	public int obtieneTipoValorAutomovil(String codigoPostal, String tipoVehiculo)throws Exception;

	public String obtieneCodigoPostalAutomovil(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsituac, String nmsuplem) throws Exception;
	
	/**
	 *********cargarAseguradosExtraprimas*********
	 *********************************************
	 **Obtiene asegurados y agrupa por familia
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param cdgrupo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>>cargarAseguradosExtraprimas(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo
			,String start
			,String limit)throws Exception;
		
	public void guardarExtraprimaAsegurado(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String cdtipsit
			,String ocupacion
			,String extraprimaOcupacion
			,String peso
			,String estatura
			,String extraprimaSobrepeso
			,String cdgrupo
			)throws Exception;
	
	/**
	 ***********guardarExtraprimaAsegurado***********
	 ************************************************
	 **Guarda valores de situacion de una sola vez
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param cdtipsit
	 * @param slist1
	 * @throws Exception
	 */
	public void guardarExtraprimaAsegurado(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,List<Map<String,String>> slist1
			)throws Exception;
	
	public List<Map<String,String>>cargarAseguradosGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo)throws Exception;
	
	public List<Map<String,String>>cargarAseguradosGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo
			,String start
			,String limit)throws Exception;
	
	public void borrarMpoliperGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo)throws Exception;
	
	@Deprecated
	public Map<String,String>cargarTipoSituacion(String cdramo,String cdtipsit)throws Exception;
	
	public String cargarCduniecoAgenteAuto(String cdagente)throws Exception;
	
	public Map<String,String>obtenerDatosAgente(String cdagente,String cdramo)throws Exception;
	
	public ManagerRespuestaSmapVO obtenerParametrosCotizacion(
			ParametroCotizacion parametro
			,String cdramo
			,String cdtipsit
			,String clave4
			,String clave5);
	
	public ManagerRespuestaSmapVO cargarAutoPorClaveGS(String cdramo,String clavegs,String cdtipsit,String cdsisrol, String tipoUnidad) throws Exception;
	
	public ManagerRespuestaSmapVO cargarClaveGSPorAuto(String cdramo,String modelo) throws Exception;
	
	public ManagerRespuestaSmapVO cargarSumaAseguradaAuto(
			String cdsisrol,String modelo,String version,String cdramo,String cdtipsit)throws Exception;
	
	public ManagerRespuestaVoidVO agregarClausulaICD(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdclausu
			,String nmsuplem
			,String icd)throws Exception;
	
	public ManagerRespuestaSlistVO cargarClausulaICD(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdclausu
			,String nmsuplem)throws Exception;
	
	public ManagerRespuestaVoidVO borrarClausulaICD(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdclausu
			,String nmsuplem
			,String icd)throws Exception;
	
	public List<Map<String,String>>cargarConfiguracionGrupo(String cdramo,String cdtipsit)throws Exception;
	
	public ComponenteVO cargarComponenteTatrisit(String cdtipsit,String cdusuari,String cdatribu)throws Exception;

	public ComponenteVO cargarComponenteTatrigar(String cdramo,String cdtipsit,String cdgarant,String cdatribu)throws Exception;
	
	public ManagerRespuestaVoidVO validarDescuentoAgente(
			String  tipoUnidad
			,String uso
			,String zona
			,String promotoria
			,String cdagente
			,String descuento);
	
	public void movimientoTdescsup(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nsuplogi
			,String cdtipsup
			,Date feemisio
			,String nmsolici
			,Date fesolici
			,Date ferefere
			,String cdseqpol
			,String cdusuari
			,String nusuasus
			,String nlogisus
			,String cdperson
			,String accion)throws Exception;
	
	public ManagerRespuestaImapSmapVO pantallaCotizacionGrupo(
			String cdramo
			,String cdtipsit
			,String ntramite
			,String ntramiteVacio
			,String status
			,String cdusuari
			,String cdsisrol
			,String nombreUsuario
			,String cdagente
			);
	
	public ManagerRespuestaSmapVO cargarClienteCotizacion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza);
	
	public ManagerRespuestaSmapVO cargarConceptosGlobalesGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdperpag);
	
	public ManagerRespuestaSmapVO generarTramiteGrupo(
			String cdunieco
			,String cdramo
			,String nmpoliza
			,String feini
			,String fefin
			,String cdperpag
			,String pcpgocte
			,Map<String,String> tvalopol
			,String ntramite
			,String ntramiteVacio
			,String miTimestamp
			,String rutaDocumentosTemporal
			,String tipoCenso
			,String dominioServerLayouts
			,String userServerLeyouts
			,String passServerLayouts
			,String directorioServerLayouts
			,String cdtipsit
			,List<Map<String,Object>>grupos
			,String codpostal
			,String cdedo
			,String cdmunici
			,String cdagente
			,String cdusuari
			,String cdsisrol
			,String clasif
			,String LINEA_EXTENDIDA
			,String cdpersonCli
			,String nombreCli
			,String rfcCli
			,String dsdomiciCli
			,String nmnumeroCli
			,String nmnumintCli
			,String cdelemen
			,boolean sincenso
			,boolean censoAtrasado
			,boolean resubirCenso
			,boolean complemento
			,String cdpool
			,String nombreCensoConfirmado
			,boolean asincrono
			,String cdideper_
			,String cdideext_
			,String nmpolant
			,String nmrenova
			,UserVO usuarioSesion
			);
	
	public ManagerRespuestaSlistVO obtenerTiposSituacion();
	
	public ManagerRespuestaSlistVO cargarAseguradosExtraprimas2(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo
			);
	
	/**
	 **************guardarValoresSituaciones**************
	 *****************************************************
	 * Recibe los valores a actualizar en
	 * base de datos y lanza valores por 
	 * defecto
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param situaciones
	 * @param cdtipsit
	 * @param guardarExt
	 * @return
	 */
	public ManagerRespuestaVoidVO guardarValoresSituaciones(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			List<Map<String,String>>situaciones,
			String cdtipsit,
			Boolean guardarExt);
	
	/**
	 **************guardarValoresSituacionesTitular**************
	 ************************************************************
	 **Actualiza el valor de extraprima ocupacional
	 **y devuelve las situaciones correspondientes
	 **para lanzar los valores por defecto
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param cdtipsit
	 * @param valor
	 * @param cdgrupo
	 * @return
	 */
	public ManagerRespuestaVoidVO guardarValoresSituacionesTitular(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza,
			String nmsuplem,
			String cdtipsit,
			String valor,
			String cdgrupo);
	
	public ManagerRespuestaSmapVO subirCensoCompleto(
			String cdunieco
			,String cdramo
			,String nmpoliza
			,String feini
			,String fefin
			,String cdperpag
			,String pcpgocte
			,String rutaDocsTemp
			,String censoTimestamp
			,String dominioServerLayout
			,String usuarioServerLayout
			,String passwordServerLayout
			,String direcServerLayout
			,String cdtipsit
			,String cdusuari
			,String cdsisrol
			,String cdagente
			,String codpostalCli
			,String cdedoCli
			,String cdmuniciCli
			,List<Map<String,Object>>grupos
			,String clasif
			,String LINEA_EXTENDIDA
			,String cdpersonCli
			,String nombreCli
			,String rfcCli
			,String dsdomiciCli
			,String nmnumeroCli
			,String nmnumintCli
			,String ntramite
			,String ntramiteVacio
			,String cdelemen
			,String nombreCensoConfirmado
			,String cdideper_
			,String cdideext_
			,String nmpolant
			,String nmrenova
			,UserVO usuarioSesion
			);
	public ManagerRespuestaVoidVO validarCambioZonaGMI(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String codpostal);
	public ManagerRespuestaVoidVO validarEnfermedadCatastGMI(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String circHosp);
	
	public String cargarTabuladoresGMIParche(
			String circulo
			,String cdatribu)throws Exception;
	
	public String guardarContratanteColectivo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String rfc
			,String cdperson
			,String nombre
			,String cdpostal
			,String cdedo
			,String cdmunici
			,String dsdomici
			,String nmnumero
			,String nmnumint
			,String nmorddom
			,boolean esConfirmaEmision
			,UserVO usuarioSesion
			)throws Exception;
	
	public ManagerRespuestaSmapVO cargarTramite(String ntramite)throws Exception;
	
	public boolean cargarBanderaCambioCuadroPorProducto(String cdramo);
	
	public ManagerRespuestaSlistSmapVO cotizar(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String cdusuari
			,String cdelemen
			,String nmpoliza
			,String feini
			,String fefin
			,String fesolici
			,String cdpersonCli
			,String cdideperCli
			,boolean noTarificar
			,boolean conIncisos
			,List<Map<String,String>>incisos
			,boolean flagMovil
			,Map<String,String>tvalopol
			,String cdagente
			)throws Exception;
	
	
	public boolean validaDomicilioCotizacionTitular(Map<String,String> params)throws Exception;
	
	
	@Deprecated
	public boolean validarCuadroComisionNatural(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	@Deprecated
	public Map<String,String>cargarTvalopol(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	@Deprecated
	public String cargarPorcentajeCesionComisionAutos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	@Deprecated
	public void ejecutaValoresDefectoTarificacionConcurrente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String tipotari
			,String cdperpag
			)throws Exception;
	
	@Deprecated
	public void ejecutaValoresDefectoConcurrente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String tipotari
			,String cdperpag
			)throws Exception;
	
	@Deprecated
	public void ejecutaTarificacionConcurrente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String tipotari
			,String cdperpag
			)throws Exception;
	
	@Deprecated
	public void actualizaValoresDefectoSituacion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception;
	
	public Map<String,Object> complementoSaludGrupo(
			String ntramite
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String complemento
			,File censo
			,String rutaDocumentosTemporal
			,String dominioServerLayouts
			,String userServerLayouts
			,String passServerLayouts
			,String rootServerLayouts
			,String cdtipsit
			,String cdusuari
			,String cdsisrol
			,String cdagente
			,String codpostalCli
			,String cdestadoCli
			,String cdmuniciCli
			,String cdplan1
			,String cdplan2
			,String cdplan3
			,String cdplan4
			,String cdplan5
			)throws Exception;
	
	@Deprecated
	public boolean validaPagoPolizaRepartido(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	@Deprecated
	public String turnaPorCargaTrabajo(
			String ntramite
			,String cdsisrol
			,String status
			)throws Exception;
	
	public String guardarConfiguracionGarantias(
			String cdramo
			,String cdtipsit
			,String cdplan
			,String cdpaq
			,String dspaq
			,String derpol
			,List<Map<String,String>>tvalogars
			)throws Exception;
	
	public List<Map<String,String>> obtenerCoberturasPlanColec(
			String cdramo
			,String cdtipsit
			,String cdplan
			,String cdsisrol
			)throws Exception;

	public Map<String,String> obtieneDatosContratantePoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception;
	
	public void actualizaCesionComision(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;

	/**
	 * Actualiza todos los registros de tvalosit para productos que tengan codigo postal, estado y municipio
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param cdpostal
	 * @param cdedo
	 * @param cdmunici
	 * @throws Exception
	 */
	public void actualizaDomicilioAseguradosColectivo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdpostal
			,String cdedo
			,String cdmunici
			)throws Exception;
	
	public void procesoColectivoAsincrono(
			boolean hayTramite
			,boolean hayTramiteVacio
			,boolean censoAtrasado
			,boolean complemento
			,String cdunieco
			,String cdramo
			,String nmpoliza
			,String cdperpag
			,String clasif
			,String LINEA
			,String LINEA_EXTENDIDA
			,List<Map<String,Object>> olist1
			,String cdtipsit
			);
	
	/**
	 * 
	 * @param comprarCdunieco
	 * @param comprarCdramo
	 * @param comprarNmpoliza
	 * @param cdtipsit
	 * @param fechaInicio
	 * @param fechaFin
	 * @param ntramite
	 * @param cdagenteExt
	 * @param comprarCdciaaguradora
	 * @param comprarCdplan
	 * @param comprarCdperpag
	 * @param cdusuari
	 * @param cdsisrol
	 * @param cdelemen
	 * @param esFlotilla
	 * @param tipoflot
	 * @param cdpersonCli
	 * @param cdideperCli
	 * @param nombreReporteCotizacion
	 * @param nombreReporteCotizacionFlot
	 * @return ntramite Numero de tramite creado
	 * @throws Exception
	 */
	public String procesoComprarCotizacion(
			String comprarCdunieco,
			String comprarCdramo,
			String comprarNmpoliza,
			String cdtipsit,
			String fechaInicio,
			String fechaFin,
			String ntramite,
			String cdagenteExt,
			String comprarCdciaaguradora,
			String comprarCdplan,
			String comprarCdperpag,
			String cdusuari,
			String cdsisrol,
			String cdelemen,
			boolean esFlotilla,
			String tipoflot,
			String cdpersonCli,
			String cdideperCli,
			String nombreReporteCotizacion,
			String nombreReporteCotizacionFlot) throws Exception;
	
	@Deprecated
	public List<Map<String,String>> recuperarListaDocumentosParametrizados(
			String cdorddoc
			,String nmsolici
			,String ntramite
			)throws Exception;
	
	/*
	@Deprecated
	public List<Map<String,String>> generarDocumentosBaseDatos(
			String cdorddoc
			,String nmsolici
			,String ntramite
			)throws Exception;
	*/
	
	@Deprecated
	public void actualizarFefecsitMpolisit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			)throws Exception;
	
	public void borrarIncisoCotizacion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			)throws Exception;
	
	@Deprecated
	public boolean isEstatusGeneraDocumentosCotizacion(String status) throws Exception;
	
	public void guardarCensoCompletoMultisaludEndoso(
			String nombreArchivo
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdedo
			,String cdmunici
			,String cdplan1
			,String cdplan2
			,String cdplan3
			,String cdplan4
			,String cdplan5
			,String complemento
			,String nmsuplem
			)throws Exception;

	public void ejecutasigsvdefEnd(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdgarant
			,String cdtipsup)throws Exception;
	
	@Deprecated
	public void restaurarRespaldoCenso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;

	@Deprecated
	public void borrarRespaldoCenso(
			String cdunieco
			,String cdramo
			,String nmpoliza
			)throws Exception;

	public void borrarMpoliperSituac0(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem,
			String cdrol)throws Exception;
	
	public void guardarLayoutGenerico(
			String nombreArchivo
			)throws Exception;
	
	/**
	 * 
	 * @param cdtipsit
	 * @return
	 * @throws Exception
	 */
	public String consultaExtraprimOcup(String cdtipsit)throws Exception;
	
	/**
	 *************cargarAseguradosExtraprimas2*************
	 **Carga asegurados y agrupa por familia
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param cdgrupo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> cargarAseguradosExtraprimas2(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo
			,String start
			,String limit
			)throws Exception;
	
	public Map<String,String> obtieneFormapago(String administradora,String retenedora) throws Exception;
	public List<Map<String,String>>cargarAseguradosExtraprimas(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo)throws Exception;

	public List<Map<String,String>> buescaEmpleado(String pv_administradora_i
            ,String pv_retenedora_i,String pv_clave_i, String pv_nombre_i, String pv_apellido_paterno_i,
			String pv_apellido_materno_i, String pv_rfc_i) throws Exception;

    public String guardaEmpleado(String pv_numsuc_i, String pv_cveent_i, String pv_cveemp_i, String pv_nomemp_i,
            String pv_apaterno_i, String pv_amaterno_i, String pv_rfc_i, String pv_curp_i, String pv_usuario_i,
            String pv_feregist_i, String pv_accion_i) throws Exception;
	

    public List<Map<String, String>> obtieneRetAdmin(String administradora, String retenedora) throws Exception;
	
	public AseguradosFiltroVO cargarAseguradosFiltroGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo
			//,String nmsitaux
			,String filtro
			,String valorFiltro
			,String start
			,String limit)throws Exception;

	public List<Map<String, String>> cargarAseguradosFiltroExtraprimas(
			String cdunieco
			, String cdramo
			, String estado
			, String nmpoliza
			, String nmsuplem
			, String cdgrupo
			, String start
			, String limit
			, String filtro
			, String valorFiltro) throws Exception;

	public List<Map<String, String>> cargarAseguradosFiltroExtraprimas2(
			String cdunieco
			, String cdramo
			, String estado
			, String nmpoliza
			, String nmsuplem
			, String cdgrupo
			, String start
			, String limit
			, String filtro
			, String valorFiltro) throws Exception;
	
	public void guardarMorbilidad(String nombreArchivo)throws Exception;

	public List<Map<String, String>> getConsultaMorbilidad(String morbilidad)throws Exception;
	
	public String existeMorbilidadNueva(String morbilidad) throws Exception;

}
