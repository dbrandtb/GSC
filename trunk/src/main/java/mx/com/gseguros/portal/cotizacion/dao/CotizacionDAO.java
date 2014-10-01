package mx.com.gseguros.portal.cotizacion.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.general.model.ComponenteVO;

public interface CotizacionDAO
{
	public void movimientoTvalogarGrupo(Map<String,String>params)throws Exception;
	public void movimientoMpolisitTvalositGrupo(Map<String,String>params)throws Exception;
	public void movimientoMpoligarGrupo(Map<String,String>params)throws Exception;
	public Map<String,String>cargarDatosCotizacionGrupo(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarGruposCotizacion(Map<String,String>params)throws Exception;
	public Map<String,String>cargarDatosGrupoLinea(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarTvalogarsGrupo(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarTarifasPorEdad(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarTarifasPorCobertura(Map<String,String>params)throws Exception;
	public String cargarNombreAgenteTramite(String ntramite)throws Exception;
	public Map<String,String>cargarPermisosPantallaGrupo(Map<String,String>params)throws Exception;
	public Map<String,String>obtieneTipoValorAutomovil(Map<String,String>params)throws Exception;
	public void guardarCensoCompleto(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarAseguradosExtraprimas(Map<String,String>params)throws Exception;
	public void guardarExtraprimaAsegurado(Map<String,String>params)throws Exception;
	public List<Map<String,String>>cargarAseguradosGrupo(Map<String,String>params)throws Exception;
	public void borrarMpoliperGrupo(Map<String,String>params)throws Exception;
	public Map<String,String>cargarTipoSituacion(Map<String,String>params)throws Exception;
	public String cargarCduniecoAgenteAuto(Map<String,String>params)throws Exception;
	public Map<String,String>obtenerDatosAgente(Map<String,String>params)throws Exception;
	public Map<String,String>obtenerParametrosCotizacion(Map<String,String>params)throws Exception;
	public Map<String,String>cargarAutoPorClaveGS(Map<String,String>params)throws Exception;
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
}