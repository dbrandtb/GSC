package mx.com.gseguros.portal.consultas.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.consultas.service.ConsultasManager;
import mx.com.gseguros.portal.cotizacion.model.ParametroGeneral;
import mx.com.gseguros.portal.general.util.ObjetoBD;
import mx.com.gseguros.portal.renovacion.dao.RenovacionDAO;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class ConsultasManagerImpl implements ConsultasManager
{
	private static Logger logger = Logger.getLogger(ConsultasManagerImpl.class);
	
	private ConsultasDAO consultasDAO;
	
	@Autowired
	private RenovacionDAO renovacionDAO;
	
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
	
	///////////////////////////////
	////// getters y setters //////
	///////////////////////////////
	public void setConsultasDAO(ConsultasDAO consultasDAO)
	{
		this.consultasDAO = consultasDAO;
	}
}