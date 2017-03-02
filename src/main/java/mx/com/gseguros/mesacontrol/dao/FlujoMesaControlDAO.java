package mx.com.gseguros.mesacontrol.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FlujoMesaControlDAO {

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTtiptramc() throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTtipflumc(String agrupamc) throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTestadomc(String cdestadomc) throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTpantamc(String cdpantmc) throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTcompmc(String cdcompmc) throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTprocmc(String cdprocmc) throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTdocume() throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTiconos() throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param swfinal TODO
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTflujomc(String cdtipflu, String swfinal)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTfluest(String cdtipflu, String cdflujomc, String cdestadomc)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdestadomc
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTfluestrol(String cdtipflu,
			String cdflujomc, String cdestadomc) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdestadomc
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTfluestavi(String cdtipflu,
			String cdflujomc, String cdestadomc) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdtipflumc
	 * @param cdpantmc
	 * @param webid
	 * @param xpos
	 * @param ypos
	 * @param subrayado
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTflupant(String cdtipflu,
			String cdflujomc, String cdpantmc) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTflucomp(String cdtipflu, String cdflujomc)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTfluproc(String cdtipflu, String cdflujomc)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTfluval(String cdtipflu, String cdflujomc, String cdvalida)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTflurev(String cdtipflu, String cdflujomc)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdrevisi
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTflurevdoc(String cdtipflu,
			String cdflujomc, String cdrevisi) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdtipflumc
	 * @param cdaccion
	 * @param dsaccion
	 * @param cdicono
	 * @param cdvalor
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTfluacc(String cdtipflu,String cdflujomc) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdaccion
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> recuperaTfluaccrol(String cdtipflu,
			String cdflujomc, String cdaccion) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param dstipflu
	 * @param cdtiptra
	 * @param swmultipol
	 * @param swreqpol
	 * @param accion
	 * @throws Exception
	 */
	public void movimientoTtipflumc(String cdtipflu, String dstipflu, String cdtiptra,
			String swmultipol, String swreqpol, String cdtipsup, String accion) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param dsflujomc
	 * @param swfinal
	 * @param accion
	 * @throws Exception
	 */
	public void movimientoTflujomc(String cdtipflu, String cdflujomc,
			String dsflujomc, String swfinal, String accion) throws Exception;

	/**
	 * 
	 * @param accion
	 * @param cdestadomc
	 * @param dsestadomc
	 * @throws Exception
	 */
	public void movimientoTestadomc(String accion, String cdestadomc, String dsestadomc)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdestadomc
	 * @param webid
	 * @param xpos
	 * @param ypos
	 * @param width
	 * @param height
	 * @param timemax
	 * @param timewrn1
	 * @param timewrn2
	 * @param swescala
	 * @param cdtipasig
	 * @param accion
	 * @throws Exception
	 */
	public void movimientoTfluest(String cdtipflu, String cdflujomc,
			String cdestadomc, String webid, String xpos, String ypos,
			String timemax, String timewrn1, String timewrn2,
			String cdtipasig, String accion)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdestadomc
	 * @param cdsisrol
	 * @param swver
	 * @param swtrabajo
	 * @param swcompra
	 * @param swreasig
	 * @param cdrolasig
	 * @param accion
	 * @throws Exception
	 */
	public void movimientoTfluestrol(String cdtipflu, String cdflujomc,
			String cdestadomc, String cdsisrol, String swver, String swtrabajo,
			String swcompra, String swreasig, String cdrolasig, String accion)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdestadomc
	 * @param webid
	 * @param xpos
	 * @param ypos
	 * @param timemax
	 * @param timewrn1
	 * @param timewrn2
	 * @param swescala
	 * @param cdtipasig
	 * @param accion
	 * @throws Exception
	 */
	public void movimientoTfluestavi(
			String cdtipflu,
			String cdflujomc,
			String cdestadomc,
			String cdaviso,
			String cdtipavi,
			String dscoment,
			String swautoavi,
			String dsmailavi,
			String cdusuariavi,
			String cdsisrolavi,
			String accion
			)throws Exception;

	/**
	 * 
	 * @param cdpantmc
	 * @param dspantmc
	 * @param urlpantmc
	 * @param swexterna
	 * @param accion
	 * @throws Exception
	 */
	public void movimientoTpantmc(String cdpantmc, String dspantmc, String urlpantmc,
			String swexterna, String accion) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdpantmc
	 * @param webid
	 * @param xpos
	 * @param ypos
	 * @param accion
	 * @throws Exception
	 */
	public void movimientoTflupant(String cdtipflu, String cdflujomc, String cdpantmc,
			String webid, String xpos, String ypos, String accion)
			throws Exception;

	/**
	 * 
	 * @param accion
	 * @param cdcompmc
	 * @param dscompmc
	 * @param nomcomp
	 * @throws Exception
	 */
	public void movimientoTcompmc(String accion, String cdcompmc, String dscompmc,
			String nomcomp) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdcompmc
	 * @param webid
	 * @param xpos
	 * @param ypos
	 * @param subrayado
	 * @param accion
	 * @throws Exception
	 */
	public void movimientoTflucomp(String cdtipflu, String cdflujomc, String cdcompmc,
			String webid, String xpos, String ypos, String accion) throws Exception;

	/**
	 * 
	 * @param cdprocmc
	 * @param dsprocmc
	 * @param urlprocmc
	 * @param accion
	 * @throws Exception
	 */
	public void movimientoTprocmc(String cdprocmc, String dsprocmc, String urlprocmc,
			String accion) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdprocmc
	 * @param webid
	 * @param xpos
	 * @param ypos
	 * @param width
	 * @param height
	 * @param accion
	 * @throws Exception
	 */
	public void movimientoTfluproc(String cdtipflu, String cdflujomc, String cdprocmc,
			String webid, String xpos, String ypos, String accion) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdvalida
	 * @param dsvalida
	 * @param cdvalidafk
	 * @param webid
	 * @param xpos
	 * @param ypoS
	 * @param accion
	 * @throws Exception
	 */
	public String movimientoTfluval(String cdtipflu, String cdflujomc, String cdvalida,
			String dsvalida, String cdvalidafk, String webid, String xpos,
			String ypos, String jsvalida, String accion) throws Exception;

	/**
	 * 
	 * @param cddocume
	 * @param dsdocume
	 * @param cdtiptra
	 * @param accion
	 * @throws Exception
	 */
	public void movimientoTdocume(String cddocume, String dsdocume, String cdtiptra,
			String accion) throws Exception;

	/**
	 * 
	 * @param accion
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdrevisi
	 * @param dsrevisi
	 * @param webid
	 * @param xpos
	 * @param ypos
	 * @throws Exception
	 */
	public String movimientoTflurev(String cdtipflu, String cdflujomc,
			String cdrevisi, String dsrevisi, String webid, String xpos,
			String ypos,String accion) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdrevisi
	 * @param cddocume
	 * @param swobliga
	 * @param subrayado
	 * @param accion
	 * @throws Exception
	 */
	public void movimientoTflurevdoc(String cdtipflu, String cdflujomc,
			String cdrevisi, String cddocume, String swobliga,
			String accion) throws Exception;

	/**
	 * 
	 * @param cdicono
	 * @param accion
	 * @throws Exception
	 */
	public void actualizaIcono(String cdicono, String accion) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdaccion
	 * @param dsaccion
	 * @param cdicono
	 * @param cdvalor
	 * @param idorigen
	 * @param iddestin
	 * @param accion
	 * @throws Exception
	 */
	public String movimientoTfluacc(String cdtipflu, String cdflujomc, String cdaccion,
			String dsaccion, String cdicono, String cdvalor, String idorigen,
			String iddestin, String swescala, String aux, String accion) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdaccion
	 * @param cdsisrol
	 * @param swpermiso
	 * @param accion
	 * @throws Exception
	 */
	public void movimientoTfluaccrol(String cdtipflu, String cdflujomc,
			String cdaccion, String cdsisrol, String swpermiso, String accion)
			throws Exception;
	
	public String ejecutaExpresion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdexpres
			)throws Exception;
	
	public void actualizaCoordenadas(
			String cdtipflu
			,String cdflujomc
			,String tipo
			,String clave
			,String webid
			,String xpos
			,String ypos
			)throws Exception;
	
	public List<Map<String,String>> recuperarTestadomcPorAgrupamc(String cdtiptra) throws Exception;
	
	public Map<String,Object> recuperarTramites(
			String agrupamc
			,String status
			,String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String cdagente
			,String ntramite
			,String fedesde
			,String fehasta
			,int start
			,int limit
			)throws Exception;
	
	public List<Map<String,String>> recuperarTtipsupl(String cdtiptra) throws Exception;
	
	public Map<String,String> recuperarPolizaUnica(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception;
	
	public String ejecutaValidacion(
			String ntramite
			,String status
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdvalidafk
			)throws Exception;
	
	public List<Map<String,String>>cargarAccionesEntidad(
			String cdtipflu
			,String cdflujomc
			,String tipoent
			,String cdentidad
			,String webid
			,String cdsisrol
			)throws Exception;
	
	public List<Map<String,String>> recuperarDocumentosRevisionFaltantes(
			String cdtipflu
			,String cdflujomc
			,String cdrevisi
			,String ntramite
			)throws Exception;
	
	public void actualizarStatusTramite(
			String ntramite
			,String status
			,Date fecstatu
			,String cdusuari
			)throws Exception;
	
	public Map<String,Object> recuperarDatosTramiteValidacionCliente(
			String cdtipflu
			,String cdflujomc
			,String tipoent
			,String claveent
			,String webid
			,String ntramite
			,String status
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			)throws Exception;
	
	public Map<String,String> recuperarUsuarioParaTurnado(
			String cdsisrol
			,String tipoasig
			)throws Exception;
	
	public String recuperarRolRecienteTramite(String ntramite, String cdusuari) throws Exception;
	
	public void restarTramiteUsuario(String cdusuari,String cdsisrol) throws Exception;
	
	
	public List<Map<String, String>> recuperaTtipflumcPorRolPorUsuario(String agrupamc,String cdsisrol,String cdusuari) throws Exception;
	
	public List<Map<String, String>> recuperaTflujomcPorRolPorUsuario(
			String cdtipflu
			,String swfinal
			,String cdsisrol
			,String cdusuari
			)throws Exception;
	
	public Map<String,String> recuperaTflujomc(String cdflujomc) throws Exception;
	
	
	/**
	 * Recupera el estatus por defecto para tramites nuevos
	 */
	public String recuperarEstatusDefectoRol (String cdsisrol) throws Exception;
	
	public String recuperaNombreMd5(String md5) throws Exception;
}
