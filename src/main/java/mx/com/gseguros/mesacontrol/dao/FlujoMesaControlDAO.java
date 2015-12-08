package mx.com.gseguros.mesacontrol.dao;

import java.util.List;
import java.util.Map;

public interface FlujoMesaControlDAO {

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTtiptramc() throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTtipflumc() throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTestadomc() throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTpantamc() throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTcompmc() throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTprocmc() throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTdocume() throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTiconos() throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTflujomc(String cdtipflu)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTfluest(String cdtipflu, String cdflujomc)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdestadomc
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTfluestrol(String cdtipflu,
			String cdflujomc, String cdestadomc) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdestadomc
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTfluestavi(String cdtipflu,
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
	List<Map<String, String>> recuperaTflupant(String cdtipflu,
			String cdflujomc) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTflucomp(String cdtipflu, String cdflujomc)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTfluproc(String cdtipflu, String cdflujomc)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTfluval(String cdtipflu, String cdflujomc)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTflurev(String cdtipflu, String cdflujomc)
			throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdrevisi
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTflurevdoc(String cdtipflu,
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
	List<Map<String, String>> recuperaTfluacc(String cdtipflu,
			String cdtipflumc, String cdaccion, String dsaccion,
			String cdicono, String cdvalor) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdaccion
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> recuperaTfluaccrol(String cdtipflu,
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
	void movimientoTtipflumc(String cdtipflu, String dstipflu, String cdtiptra,
			String swmultipol, String swreqpol, String accion) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param dsflujomc
	 * @param swfinal
	 * @param accion
	 * @throws Exception
	 */
	void movimientoTflujomc(String cdtipflu, String cdflujomc,
			String dsflujomc, String swfinal, String accion) throws Exception;

	/**
	 * 
	 * @param accion
	 * @param cdestadomc
	 * @param dsestadomc
	 * @throws Exception
	 */
	void movimientoTestadomc(String accion, String cdestadomc, String dsestadomc)
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
	void movimientoTfluest(String cdtipflu, String cdflujomc,
			String cdestadomc, String webid, String xpos, String ypos,
			String timemax, String timewrn1, String timewrn2,
			String swescala, String cdtipasig, String accion)
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
	void movimientoTfluestrol(String cdtipflu, String cdflujomc,
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
	void movimientoTfluestavi(
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
	void movimientoTpantmc(String cdpantmc, String dspantmc, String urlpantmc,
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
	void movimientoTflupant(String cdtipflu, String cdflujomc, String cdpantmc,
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
	void movimientoTcompmc(String accion, String cdcompmc, String dscompmc,
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
	void movimientoTflucomp(String cdtipflu, String cdflujomc, String cdcompmc,
			String webid, String xpos, String ypos, String accion) throws Exception;

	/**
	 * 
	 * @param cdprocmc
	 * @param dsprocmc
	 * @param urlprocmc
	 * @param accion
	 * @throws Exception
	 */
	void movimientoTprocmc(String cdprocmc, String dsprocmc, String urlprocmc,
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
	void movimientoTfluproc(String cdtipflu, String cdflujomc, String cdprocmc,
			String webid, String xpos, String ypos, String width,
			String height, String accion) throws Exception;

	/**
	 * 
	 * @param cdtipflu
	 * @param cdflujomc
	 * @param cdvalida
	 * @param dsvalida
	 * @param cdexpres
	 * @param webid
	 * @param xpos
	 * @param ypoS
	 * @param accion
	 * @throws Exception
	 */
	void movimientoTfluval(String cdtipflu, String cdflujomc, String cdvalida,
			String dsvalida, String cdexpres, String webid, String xpos,
			String ypoS, String accion) throws Exception;

	/**
	 * 
	 * @param cddocume
	 * @param dsdocume
	 * @param cdtiptra
	 * @param accion
	 * @throws Exception
	 */
	void movimientoTdocume(String cddocume, String dsdocume, String cdtiptra,
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
	void movimientoTflurev(String accion, String cdtipflu, String cdflujomc,
			String cdrevisi, String dsrevisi, String webid, String xpos,
			String ypos) throws Exception;

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
	void movimientoTflurevdoc(String cdtipflu, String cdflujomc,
			String cdrevisi, String cddocume, String swobliga,
			String subrayado, String accion) throws Exception;

	/**
	 * 
	 * @param cdicono
	 * @param accion
	 * @throws Exception
	 */
	void actualizaIcono(String cdicono, String accion) throws Exception;

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
	void movimientoTfluacc(String cdtipflu, String cdflujomc, String cdaccion,
			String dsaccion, String cdicono, String cdvalor, String idorigen,
			String iddestin, String accion) throws Exception;

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
	void movimientoTfluaccrol(String cdtipflu, String cdflujomc,
			String cdaccion, String cdsisrol, String swpermiso, String accion)
			throws Exception;
	
}
