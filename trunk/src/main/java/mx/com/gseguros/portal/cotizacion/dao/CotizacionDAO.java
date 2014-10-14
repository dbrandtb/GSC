package mx.com.gseguros.portal.cotizacion.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.ParametroCotizacion;
import mx.com.gseguros.portal.general.model.ComponenteVO;

public interface CotizacionDAO
{
	public void movimientoTvalogarGrupoCompleto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdgrupo
			,String cdgarant
			,String status
			,Map<String,String>valores
			)throws DaoException;
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
			,String valor
			)throws Exception;
	public void movimientoMpolisitTvalositGrupo(Map<String,String>params)throws Exception;
	public void movimientoMpoligarGrupo(
			String cdunieco
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
			)throws Exception;
	public Map<String,String>cargarDatosCotizacionGrupo(Map<String,String>params)throws Exception;
	public Map<String,String>cargarDatosCotizacionGrupo2(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String ntramite
			)throws ApplicationException,Exception;
	public List<Map<String,String>>cargarGruposCotizacion(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarGruposCotizacion2(String cdunieco,String cdramo,String estado,String nmpoliza)throws DaoException,ApplicationException;
	public Map<String,String>cargarDatosGrupoLinea(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarTvalogarsGrupo(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarTarifasPorEdad(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarTarifasPorCobertura(Map<String,String>params)throws Exception;
	public String cargarNombreAgenteTramite(String ntramite)throws Exception;
	public Map<String,String>cargarPermisosPantallaGrupo(String cdsisrol,String status)throws Exception;
	public Map<String,String>obtieneTipoValorAutomovil(Map<String,String>params)throws Exception;
	public void guardarCensoCompleto(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarAseguradosExtraprimas(Map<String,String>params)throws Exception;
	public void guardarExtraprimaAsegurado(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarAseguradosGrupo(Map<String,String>params)throws Exception;
	public void borrarMpoliperGrupo(Map<String,String>params)throws Exception;
	public Map<String,String>cargarTipoSituacion(Map<String,String>params)throws Exception;
	public String cargarCduniecoAgenteAuto(Map<String,String>params)throws Exception;
	public Map<String,String>obtenerDatosAgente(String cdagente,String cdramo)throws Exception;
	public Map<String,String>obtenerParametrosCotizacion(
			ParametroCotizacion parametro
			,String cdramo
			,String cdtipsit
			,String clave4
			,String clave5)throws ApplicationException,Exception;
	public Map<String,String>cargarAutoPorClaveGS(String cdramo,String clavegs,String cdtipsit,String cdsisrol)throws Exception;
	public Map<String,String>cargarClaveGSPorAuto(Map<String,String>params)throws Exception;
	public Map<String,String>cargarSumaAseguradaAuto(Map<String,String>params)throws Exception;
	public void movimientoMpolicotICD(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarMpolicotICD(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarConfiguracionGrupo(Map<String,String>params)throws Exception;
	public ComponenteVO cargarComponenteTatrisit(Map<String,String>params)throws Exception;
	public ComponenteVO cargarComponenteTatrigar(Map<String,String>params)throws Exception;
	public void validarDescuentoAgente(
			String tipoUnidad
			,String uso
			,String zona
			,String promotoria
			,String cdagente
			,String descuento)throws Exception;
	public List<Map<String,String>>impresionDocumentosPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String ntramite)throws DaoException,ApplicationException;
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
	public DatosUsuario cargarInformacionUsuario(String cdusuari,String cdtipsit)throws Exception;
	public List<ComponenteVO>cargarTatrisit(String cdtipsit,String cdusuari)throws Exception;
	public List<ComponenteVO>cargarTatripol(String cdramo,String cdtipsit)throws Exception;
	public Map<String,String>cargarClienteCotizacion(String cdunieco,String cdramo,String estado,String nmpoliza)throws Exception;
	public Map<String,String>cargarConceptosGlobalesGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdperpag)throws ApplicationException,Exception;
	public String calculaNumeroPoliza(String cdunieco,String cdramo,String estado)throws Exception,ApplicationException;
	public void movimientoPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String status
			,String swestado
			,String nmsolici
            ,String feautori
            ,String cdmotanu
            ,String feanulac
            ,String swautori
            ,String cdmoneda
            ,String feinisus
            ,String fefinsus
            ,String ottempot
            ,String feini
            ,String hhefecto
            ,String fefin
            ,String fevencim
            ,String nmrenova
            ,String ferecibo
            ,String feultsin
            ,String nmnumsin
            ,String cdtipcoa
            ,String swtarifi
            ,String swabrido
            ,String feemisio
            ,String cdperpag
            ,String nmpoliex
            ,String nmcuadro
            ,String porredau
            ,String swconsol
            ,String nmpolant
            ,String nmpolnva
            ,String fesolici
            ,String cdramant
            ,String cdmejred
            ,String nmpoldoc
            ,String nmpoliza2
            ,String nmrenove
            ,String nmsuplee
            ,String ttipcamc
            ,String ttipcamv
            ,String swpatent
            ,String pcpgocte
            ,String accion
			) throws Exception;
	public void movimientoTvalopol(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String status
			,Map<String,String>valores)throws Exception;
	public void procesarCenso(
			String nombreProcedure
			,String cdusuari
			,String cdsisrol
			,String nombreCenso
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsit
			,String cdagente
			,String codpostal
			,String cdedo
			,String cdmunici
			)throws Exception;
	public void actualizaMpolisitTvalositGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo
			,String nombreGrupo
			,String cdplan
			,Map<String,String>valores)throws Exception;
	public void valoresPorDefecto(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdgarant
			,String cdtipsup)throws Exception;
	public void movimientoMpoliper(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdrol
			,String cdperson
			,String nmsuplem
			,String status
			,String nmorddom
			,String swreclam
			,String accion
			,String swexiper)throws Exception;
	public void movimientoMpoliage(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdagente
			,String nmsuplem
			,String status
			,String cdtipoag
			,String porredau
			,String nmcuadro
			,String cdsucurs
			,String accion
			,String ntramite
			,String porparti)throws Exception;
	public void tarificaEmi(
			String cdusuari
			,String cdelemen
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdtipsit)throws Exception;
	public List<Map<String,String>>obtenerTiposSituacion()throws Exception;
	public List<Map<String,String>>cargarSituacionesGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo)throws DaoException,ApplicationException;
	public void actualizaValoresSituacion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,Map<String,String>valores)throws DaoException;
}