package mx.com.gseguros.portal.catalogos.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.general.model.ComponenteVO;

public interface PersonasDAO
{
	/**
	 * Obtiene personas por RFC de PKG_CONSULTA.P_GET_MPERSONA
	 */
	public List<Map<String,String>>obtenerPersonasPorRFC(Map<String,String>params) throws Exception;
	
	public Map<String,String>obtenerPersonaPorCdperson(Map<String,String>params) throws Exception;
	/**
	 * Guarda mpersona con PKG_SATELITES.P_MOV_MPERSONA
	 */
	public void movimientosMpersona(String cdperson
			,String cdtipide
			,String cdideper
			,String dsnombre
			,String cdtipper
			,String otfisjur
			,String otsexo
			,Date   fenacimi
			,String cdrfc
			,String dsemail
			,String dsnombre1
			,String dsapellido
			,String dsapellido1
			,Date   feingreso
			,String cdnacion
			,String canaling
			,String conducto
			,String ptcumupr
			,String residencia
			,String accion) throws Exception;
	/**
	 * obtener domicilio por cdperson desde PKG_CONSULTA.P_GET_MDOMICIL
	 */
	public Map<String,String> obtenerDomicilioPorCdperson(String cdperson) throws Exception;
	/**
	 * Obtener nuevo cdperson de PKG_SATELITES.P_GEN_CDPERSON
	 */
	public String obtenerNuevoCdperson() throws Exception;
	/**
	 * movimientos de domicilio por cdperson de PKG_SATELITES.P_MOV_MDOMICIL
	 */
	public void movimientosMdomicil(String cdperson
			,String nmorddom
			,String dsdomici
			,String nmtelefo
			,String cdpostal
			,String cdedo
			,String cdmunici
			,String cdcoloni
			,String nmnumero
			,String nmnumint
			,String accion) throws Exception;
	/**
	 * Obtener los componentes de tatriper por cdrol y cdperson de PKG_LISTAS.P_GET_ATRI_PER
	 */
	public List<ComponenteVO> obtenerAtributosPersona(String cdperson) throws Exception;
	/**
	 * Obtiene los valores de tvaloper para un cdperson y un cdrol de PKG_CONSULTA.P_GET_TVALOPER
	 */
	public Map<String,String> obtenerTvaloper(String cdrol,String cdperson) throws Exception;
	/**
	 * Movimientos de tvaloper por cdperson de PKG_SATELITES.P_MOV_TVALOPER_NUEVO
	 */
	public void movimientosTvaloper(String cdrol,String cdperson
			,String otvalor01,String otvalor02,String otvalor03,String otvalor04,String otvalor05
			,String otvalor06,String otvalor07,String otvalor08,String otvalor09,String otvalor10
			,String otvalor11,String otvalor12,String otvalor13,String otvalor14,String otvalor15
			,String otvalor16,String otvalor17,String otvalor18,String otvalor19,String otvalor20
			,String otvalor21,String otvalor22,String otvalor23,String otvalor24,String otvalor25
			,String otvalor26,String otvalor27,String otvalor28,String otvalor29,String otvalor30
			,String otvalor31,String otvalor32,String otvalor33,String otvalor34,String otvalor35
			,String otvalor36,String otvalor37,String otvalor38,String otvalor39,String otvalor40
			,String otvalor41,String otvalor42,String otvalor43,String otvalor44,String otvalor45
			,String otvalor46,String otvalor47,String otvalor48,String otvalor49,String otvalor50
			)throws Exception;
	
	public List<Map<String,String>>cargarDocumentosPersona(Map<String,String> params)throws Exception;
	
	public void validarDocumentosPersona(Map<String,String> params)throws Exception;
	
	public String cargarNombreDocumentoPersona(Map<String,String>params)throws Exception;

	public String guardaAccionista(Map<String,String>params)throws Exception;

	public String eliminaAccionistas(Map<String,String>params)throws Exception;

	public String actualizaStatusPersona(Map<String,String>params)throws Exception;
	
	public List<Map<String,String>> obtieneAccionistas(Map<String, String> params)throws Exception;
}