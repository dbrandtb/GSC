package mx.com.gseguros.portal.emision.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.emision.dao.EmisionDAO;
import mx.com.gseguros.portal.emision.service.EmisionManager;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.utils.Utils;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class EmisionManagerImpl implements EmisionManager
{
	private Map<String,Object> session;
	
	private static Logger logger = LoggerFactory.getLogger(EmisionManagerImpl.class);
	
	private PantallasDAO pantallasDAO;
	
	@Autowired
	private CotizacionDAO cotizacionDAO;
	
	@Autowired
	private EmisionDAO emisionDAO;
	
	@Value("${url.descargar.documentos}")
	private String urlDescargarDocumentosLibre;
	
	@Override
	public ManagerRespuestaImapVO construirPantallaClausulasPoliza() throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ construirPantallaClausulasPoliza @@@@@@"
				));
		
		ManagerRespuestaImapVO resp=new ManagerRespuestaImapVO(true);
		resp.setImap(new HashMap<String,Item>());
		
		String paso = null;
		
		try
		{
			paso = "Recuperando combo de clausulas por poliza";
			List<ComponenteVO>comboClausulas=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,null //cdsisrol
					,"PANTALLA_CLAUSULAS_POLIZA"
					,"COMBO_CLAUSULAS"
					,null //orden
					);
			Utils.validate(comboClausulas , "Error al obtener el combo de clausulas por poliza");
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(comboClausulas, true, false, true, false, false, false);
			resp.getImap().put("comboClausulas" , gc.getItems());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex,paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ construirPantallaClausulasPoliza @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO guardarClausulasPoliza(List<Map<String,String>>clausulas)
	{
		logger.debug(Utils.log(
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarClausulasPoliza @@@@@@"
				,"\n@@@@@@ clausulas=",clausulas
				));
		
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		
		logger.debug(Utils.log(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ guardarClausulasPoliza @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	/*
	@Deprecated
	@Override
	public String insercionDocumentosParametrizados(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String proceso
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ insercionDocumentosParametrizados @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ nmsituac=" , nmsituac
				,"\n@@@@@@ nmsuplem=" , nmsuplem
				,"\n@@@@@@ proceso="  , proceso
				));
		
		String cdorddoc = null
		       ,paso    = null;
		try
		{
			paso = "Generando documentos parametrizados";
			logger.debug("Paso: {}",paso);
			cdorddoc = cotizacionDAO.insercionDocumentosParametrizados(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsituac
					,nmsuplem
					,proceso
					);
			logger.debug("cdorddoc: {}",cdorddoc);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ cdorddoc=",cdorddoc
				,"\n@@@@@@ insercionDocumentosParametrizados @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return cdorddoc;
	}
	*/
	
	/*
	 * Getters y setters
	 */
	
	@Override
	public void setSession(Map<String,Object>session)
	{
		this.session=session;
	}

	public void setPantallasDAO(PantallasDAO pantallasDAO) {
		this.pantallasDAO = pantallasDAO;
	}

	@Override
	public void getActualizaCuadroComision(Map<String, Object> paramsPoliage) throws Exception {
		try {
			cotizacionDAO.getActualizaCuadroComision(paramsPoliage);
		} catch (DaoException daoExc) {
			throw new Exception(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	@Deprecated
	public void actualizaNmsituaextMpolisit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac 
			,String nmsuplem
			,String nmsituaext
			)throws Exception
	{
		cotizacionDAO.actualizaNmsituaextMpolisit(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nmsituac
				,nmsuplem
				,nmsituaext
				);
	}
	
	@Override
	@Deprecated
	public void actualizaDatosMpersona(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdestciv
			,String ocup
			)throws Exception
	{
		cotizacionDAO.actualizaDatosMpersona(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nmsituac
				,nmsuplem
				,cdestciv
				,ocup);
	}
	
	@Deprecated
	@Override
	public void validarDocumentoTramite (String ntramite, String cddocume) throws Exception {
		emisionDAO.validarDocumentoTramite(ntramite, cddocume);
	}
	
	@Override
	@Deprecated
	public String recuperarTramiteCotizacion (String cdunieco, String cdramo, String estado, String nmpoliza) throws Exception {
		return emisionDAO.recuperarTramiteCotizacion(cdunieco, cdramo, estado, nmpoliza);
	}
	
	@Override
	public String generarLigasDocumentosEmisionLocalesIce (String ntramite) throws Exception {
		logger.debug(Utils.log(
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
				"\n@@@@@@ generarLigasDocumentosEmisionLocalesIce @@@@@@",
				"\n@@@@@@ ntramite = ", ntramite));
		String ligas = "",
			paso = null;
		try {
			paso = "Recuperando documentos generados por parametrizaci\u00f3n";
			logger.debug(paso);
			List<Map<String, String>> docsGenerados = emisionDAO.recuperarDocumentosGeneradosPorParametrizacion(ntramite);
			if (docsGenerados.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (Map<String, String> doc : docsGenerados) {
					sb.append(Utils.join(
							"<br/><br/><a style=\"font-weight: bold\" href=\"",
							urlDescargarDocumentosLibre,
							"?subfolder=", ntramite,
							"&filename=", doc.get("CDDOCUME"),
							"\">",
							doc.get("DSDOCUME"),
							"</a>"));
				}
				ligas = sb.toString();
			}
		} catch (Exception ex) {
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
				"\n@@@@@@ ligas =", ligas,
				"\n@@@@@@ generarLigasDocumentosEmisionLocalesIce @@@@@@",
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"));
		return ligas;
	}
}