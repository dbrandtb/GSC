package mx.com.gseguros.portal.consultas.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.consultas.service.ConsultasManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ParametroGeneral;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.ObjetoBD;
import mx.com.gseguros.portal.renovacion.dao.RenovacionDAO;
import mx.com.gseguros.portal.siniestros.dao.SiniestrosDAO;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultasManagerImpl implements ConsultasManager
{
	private static Logger logger = Logger.getLogger(ConsultasManagerImpl.class);
	
	@Autowired
	private ConsultasDAO consultasDAO;
	
	@Autowired
	private RenovacionDAO renovacionDAO;
	
	@Autowired
	private SiniestrosDAO siniestrosDAO;
	
	@Autowired
	private PantallasDAO pantallasDAO;
	
	@Override
	public List<Map<String,String>> consultaDinamica(ObjetoBD objetoBD,LinkedHashMap<String,Object>params) throws Exception
	{
		logger.debug(""
				+ "\n##############################"
				+ "\n###### consultaDinamica ######"
				);
		logger.debug("storedProcedure: "+ objetoBD.getNombre());
		logger.debug("params: "+params);
		List<Map<String,String>> lista = consultasDAO.consultaDinamica(objetoBD.getNombre(), params);
		if(lista==null)
		{
			lista = new ArrayList<Map<String,String>>();
		}
		logger.debug("lista: "+lista);
		logger.debug(""
				+ "\n###### consultaDinamica ######"
				+ "\n##############################"
				);
		return lista;
	}
	
	@Override
	public void validarDatosCliente(String cdunieco, String cdramo, String estado, String nmpoliza) throws Exception {
		consultasDAO.validarDatosCliente(cdunieco, cdramo, estado, nmpoliza);
	}

	@Override
	public void validarDatosObligatoriosPrevex(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception
	{
		consultasDAO.validarDatosObligatoriosPrevex(cdunieco, cdramo, estado, nmpoliza);
	}
	
	@Override
	public void validarDatosDXN(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem)throws Exception
	{
		consultasDAO.validarAtributosDXN(cdunieco, cdramo, estado, nmpoliza, nmsuplem);
	}
	
	@Override
	@Deprecated
	public Map<String,String>cargarAtributosBaseCotizacion(String cdtipsit)throws Exception
	{
		return consultasDAO.cargarAtributosBaseCotizacion(cdtipsit);
	}
	
	@Override
	@Deprecated
	public Map<String,String>cargarInformacionPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdusuari
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ cargarInformacionPoliza @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ cdusuari=" , cdusuari
				));
		
		Map<String,String>datos=consultasDAO.cargarInformacionPoliza(cdunieco,cdramo,estado,nmpoliza,cdusuari);
		
		logger.debug(Utils.log(
				 "\n@@@@@@ " , datos
				,"\n@@@@@@ cargarInformacionPoliza @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return datos;
	}
	
	@Override
	@Deprecated
	public List<Map<String,String>>cargarMpolizasPorParametrosVariables(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsolici
			,String cdramant
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ cargarMpolizasPorParametrosVariables @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ nmsuplem=" , nmsuplem
				,"\n@@@@@@ nmsolici=" , nmsolici
				,"\n@@@@@@ cdramant=" , cdramant
				));

		List<Map<String,String>>lista=consultasDAO.cargarMpolizasPorParametrosVariables(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nmsuplem
				,nmsolici
				,cdramant
				);
		
		logger.debug(Utils.log(
				 "\n@@@@@@ lista=" , lista
				,"\n@@@@@@ cargarMpolizasPorParametrosVariables @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return lista;
	}

	@Override
	public List<Map<String,String>> obtieneContratantePoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdrol
			,String cdperson
			)throws Exception
			{
		logger.debug(Utils.log(
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ obtieneContratantePoliza @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ nmsituac=" , nmsituac
				,"\n@@@@@@ cdrol="    , cdrol
				,"\n@@@@@@ cdperson=" , cdperson
				));
		
		List<Map<String,String>>lista=consultasDAO.obtieneContratantePoliza(
				  cdunieco
				, cdramo
				, estado
				, nmpoliza
				, nmsituac
				, cdrol
				, cdperson
				);
		
		logger.debug(Utils.log(
				"\n@@@@@@ lista=" , lista
				,"\n@@@@@@ obtieneContratantePoliza @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return lista;
			}
	
	
	@Override
	public boolean esProductoSalud(String cdramo) throws Exception {
		return consultasDAO.esProductoSalud(cdramo);
	}
	
	@Override
	public String validacionesSuplemento(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdtipsup
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ validacionesSuplemento @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				));
		
		String error=consultasDAO.validacionesSuplemento(cdunieco,cdramo,estado,nmpoliza,nmsituac,nmsuplem,cdtipsup);
		
		logger.debug(Utils.log(
				 "\n@@@@@@ error=",error
				,"\n@@@@@@ validacionesSuplemento @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return error;
	}
	
	
	public boolean copiarArchivosUsuarioTramite(String cduniecoOrigen, String cdramoOrigen, String estadoOrigen, 
			String nmpolizaOrigen, String ntramiteDestino, String rutaDocumentos)throws Exception{
		
		//copiar documentos de usuario
		List<Map<String,String>>iPolizaUserDocs=renovacionDAO.cargarDocumentosSubidosPorUsuario(cduniecoOrigen,cdramoOrigen,estadoOrigen,nmpolizaOrigen);
		for(Map<String,String>iUserDoc:iPolizaUserDocs)
		{
			String ntramiteAnt = iUserDoc.get("ntramite");
			String cddocume    = iUserDoc.get("cddocume");
			
			File doc = new File(
					new StringBuilder(rutaDocumentos)
					.append("/")
					.append(ntramiteAnt)
					.append("/")
					.append(cddocume)
					.toString()
					);
			
			File newDoc = new File(
					new StringBuilder(rutaDocumentos)
					.append("/")
					.append(ntramiteDestino)
					.append("/")
					.append(cddocume)
					.toString()
					);
			
			if(doc!=null&&doc.exists())
			{
				try
				{
					FileUtils.copyFile(doc, newDoc);
				}
				catch(Exception ex)
				{
					logger.error("Error copiando archivo de usuario",ex);
				}
			}
			else
			{
				logger.error(new StringBuilder("No existe el documento").append(doc).toString());
			}
		}
		
		consultasDAO.copiaDocumentosTdocupol(cduniecoOrigen, cdramoOrigen, estadoOrigen, 
				nmpolizaOrigen, ntramiteDestino);
		
		return true;
	}
	
	@Deprecated
	@Override
	public boolean validaClientePideNumeroEmpleado(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ validaClientePideNumeroEmpleado @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				));
		
		boolean pide = consultasDAO.validaClientePideNumeroEmpleado(cdunieco,cdramo,estado,nmpoliza);
		
		logger.debug(Utils.log(
				 "\n@@@@@@ pide=",pide
				,"\n@@@@@@ validaClientePideNumeroEmpleado @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return pide;
	}
	
	@Deprecated
	@Override
	public boolean validarVentanaDocumentosBloqueada(
			String ntramite
			,String cdtiptra
			,String cdusuari
			,String cdsisrol
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ validarVentanaDocumentosBloqueada @@@@@@"
				));
		
		boolean bloqueada = consultasDAO.validarVentanaDocumentosBloqueada(ntramite,cdtiptra,cdusuari,cdsisrol);
		
		logger.debug(Utils.log(
				 "\n@@@@@@ bloqueada=",bloqueada
				,"\n@@@@@@ validarVentanaDocumentosBloqueada @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return bloqueada;
	}
	
	
	public Map<String,String> consultaFeNacContratanteAuto(Map<String,String> params)throws Exception{
		
		Map<String,String> res = null;
		
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ consultaFeNacContratanteAuto @@@@@@"
				,"\n@@@@@@ params=" , params
				));
		
		res = consultasDAO.consultaFeNacContratanteAuto(params);
		
		logger.debug(Utils.log(
				 "\n@@@@@@ res=",res
				,"\n@@@@@@ validaClientePideNumeroEmpleado @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		
		return res;
	}
	
	@Override
	@Deprecated
	public String recuperarTparagen(ParametroGeneral paragen) throws Exception
	{
		return consultasDAO.recuperarTparagen(paragen);
	}
	
	@Deprecated
	@Override
	public Map<String,String> recuperarDatosFlujoEmision(String cdramo, String tipoflot) throws Exception
	{
		return consultasDAO.recuperarDatosFlujoEmision(cdramo,tipoflot);
	}
	
	@Override
	public String recuperarCodigoCustom(String cdpantalla, String cdsisrol) throws Exception
	{
		logger.debug(Utils.log("\n@@@@@@ > recuperarCodigoCustom cdpantalla=", cdpantalla, ", cdsisrol=", cdsisrol));
		
		String codigo = consultasDAO.recuperarCodigoCustom(cdpantalla,cdsisrol);
		
		logger.debug(Utils.log("\n@@@@@@ < recuperarCodigoCustom codigo=", codigo));
		return codigo;
	}
	
	@Override
	public void modificaPermisosEdicionCoberturas(int cdramo, String cdtipsit, String cdplan, String cdgarant, String cdsisrol, String swmodifi, String accion) throws Exception{
		consultasDAO.modificaPermisosEdicionCoberturas(cdramo, cdtipsit, cdplan, cdgarant, cdsisrol, swmodifi, accion);
	}
	
	@Override
	public List<Map<String,String>> consultaPermisosEdicionCoberturas(int cdramo, String cdtipsit, String cdplan, String cdgarant, String cdsisrol) throws Exception{
		return	consultasDAO.consultaPermisosEdicionCoberturas(cdramo,cdtipsit,cdplan,cdgarant,cdsisrol);
	}
	
	@Override
	@Deprecated
	public String recuperarCdpersonClienteTramite(String ntramite) throws Exception
	{
		return consultasDAO.recuperarCdpersonClienteTramite(ntramite);
	}
	
	@Override
	@Deprecated
	public Map<String,String> recuperarDatosFlujoEndoso(String cdramo, String cdtipsup) throws Exception
	{
		return consultasDAO.recuperarDatosFlujoEndoso(cdramo, cdtipsup);
	}
	
	@Override
	public boolean esTramiteSalud (String ntramite) throws Exception {
		logger.debug(Utils.log(
			"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
			"\n@@@@@@ esTramiteSalud @@@@@@",
			"\n@@@@@@ ntramite = ", ntramite
		));
		boolean esSalud = false;
		String paso = null;
		try {
			paso = "Recuperando datos tr\u00e1mite";
			logger.debug(paso);
			Map<String, String> tramite = siniestrosDAO.obtenerTramiteCompleto(ntramite);
			String cdramo = tramite.get("CDRAMO");
			if (StringUtils.isBlank(cdramo)) {
				throw new ApplicationException("El tr\u00e1mite no tiene producto");
			}
			esSalud = this.esProductoSalud(cdramo);
		} catch (Exception ex) {
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
			"\n@@@@@@ esSalud = ", esSalud,
			"\n@@@@@@ esTramiteSalud @@@@@@",
			"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
		));
		return esSalud;
	}
	
	@Override
	public void actualizaFlujoTramite(String ntramite, String cdflujomc, String cdtipflu) throws Exception {
		logger.debug(Utils.log(
			"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
			"\n@@@@@@ actualizaFlujoTramite @@@@@@",
			"\n@@@@@@ ntramite = ", ntramite,
			"\n@@@@@@ cdflujomc = ", cdflujomc,
			"\n@@@@@@ cdtipflu = ", cdtipflu
		));
		String paso = null;
		try {
			paso = "Actualizando datos tr\u00e1mite";
			consultasDAO.actualizaFlujoTramite(ntramite, cdflujomc, cdtipflu);
			paso = "Termino de actualizar datos tr\u00e1mite";
		} catch (Exception ex) {
			Utils.generaExcepcion(ex, paso);
		}
	}
	
	public ManagerRespuestaImapVO pantallaTrafudoc(String cdsisrol) throws Exception{
	        ManagerRespuestaImapVO resp = new ManagerRespuestaImapVO(true);
	        String paso = "";
	        //obtener componentes
	        try{
	            paso = "antes de obtener componentes busqueda";
	            List<ComponenteVO>componentesBusqueda = pantallasDAO.obtenerComponentes(
	                    null,null,null,null,null,cdsisrol,"TRAFUDOC_DINAMICA","BUSQUEDA",null);
	            
	            GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
	            
	            gc.generaComponentes(componentesBusqueda, true, false, true, false, false, false);
	            
	            Map<String,Item> imap = new HashMap<String,Item>();
	            resp.setImap(imap);         
	            imap.put("busquedaItems" , gc.getItems());
	        }
	        catch(Exception ex){
	            Utils.generaExcepcion(ex, paso);
	        }
	        return resp;
	}
	
	@Override
	public List<Map<String, String>> obtenerCursorTrafudoc(String cdfunci, String cdramo, String cdtipsit) throws Exception{
	    String paso = "";
	    List<Map<String, String>> lista = new ArrayList<Map<String,String>>();
	    try{
	        paso = "Antes de obtener cursor trafudoc;";
	        lista = consultasDAO.obtenerCursorTrafudoc(cdfunci, cdramo, cdtipsit);
	    }
	    catch(Exception ex){
	        Utils.generaExcepcion(ex, paso);
	    }
	    return lista;
	}
}