package mx.com.gseguros.portal.cancelacion.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cancelacion.dao.CancelacionDAO;
import mx.com.gseguros.portal.cancelacion.service.CancelacionManager;

public class CancelacionManagerImpl implements CancelacionManager
{

	private static org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(CancelacionManagerImpl.class);
	private CancelacionDAO cancelacionDAO;
	
	@Override
	public List<Map<String, String>> buscarPolizas(Map<String, String> params) throws Exception {
		log.debug("CancelacionManager buscarPolizas params: "+params);
		List<Map<String,String>> lista=cancelacionDAO.buscarPolizas(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("CancelacionManager buscarPolizas lista size: "+lista.size());
		return lista;
	}
	
	public void setCancelacionDAO(CancelacionDAO cancelacionDAO)
	{
		this.cancelacionDAO=cancelacionDAO;
	}

	@Override
	public Map<String, String> obtenerDetalleCancelacion(Map<String, String> params) throws Exception
	{
		log.debug("CancelacionManager obtenerDetalleCancelacion params: "+params);
		Map<String,String> res=cancelacionDAO.obtenerDetalleCancelacion(params);
		log.debug("CancelacionManager obtenerDetalleCancelacion: "+res);
		return res;
	}
	
	@Override
	public List<Map<String, String>> obtenerPolizasCandidatas(Map<String, String> params) throws Exception
	{
		log.debug("CancelacionManager obtenerPolizasCandidatas params: "+params);
		
		List<Map<String,String>> lista = cancelacionDAO.obtenerPolizasCandidatas(params);
		lista                          = lista!=null?lista:new ArrayList<Map<String,String>>(0);
		
		log.debug("CancelacionManager obtenerPolizasCandidatas lista size: "+lista.size());
		
		return lista;
	}
	
	@Override
	public void seleccionaPolizas (Map<String,String> params) throws Exception
	{
		log.debug("CancelacionManager seleccionaPolizas params: "+params);
		cancelacionDAO.seleccionaPolizas(params);
		log.debug("CancelacionManager seleccionaPolizas end");
	}

}
