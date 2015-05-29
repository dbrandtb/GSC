package mx.com.gseguros.portal.consultas.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.consultas.service.ConsultasManager;
import mx.com.gseguros.portal.general.util.ObjetoBD;
import mx.com.gseguros.utils.Utilerias;

import org.apache.log4j.Logger;

public class ConsultasManagerImpl implements ConsultasManager
{
	private static Logger logger = Logger.getLogger(ConsultasManagerImpl.class);
	
	private ConsultasDAO consultasDAO;
	
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
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ cargarInformacionPoliza @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ cdusuari=" , cdusuari
				));
		
		Map<String,String>datos=consultasDAO.cargarInformacionPoliza(cdunieco,cdramo,estado,nmpoliza,cdusuari);
		
		logger.info(Utilerias.join(
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
		logger.info(Utilerias.join(
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
		
		logger.info(Utilerias.join(
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
		logger.info(Utilerias.join(
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
		
		logger.info(Utilerias.join(
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
	
	///////////////////////////////
	////// getters y setters //////
	///////////////////////////////
	public void setConsultasDAO(ConsultasDAO consultasDAO)
	{
		this.consultasDAO = consultasDAO;
	}
}