package mx.com.gseguros.portal.mesacontrol.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.portal.mesacontrol.service.MesaControlManager;
import mx.com.gseguros.utils.Utils;

import org.apache.log4j.Logger;

public class MesaControlManagerImpl implements MesaControlManager
{
	private static Logger logger = Logger.getLogger(MesaControlManagerImpl.class);
	private MesaControlDAO mesaControlDAO;
	
	@Override
	public String cargarCdagentePorCdusuari(String cdusuari)throws Exception
	{
		logger.info(""
				+ "\n#######################################"
				+ "\n###### cargarCdagentePorCdusuari ######"
				+ "\ncdusuari: "+cdusuari);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdusuari",cdusuari);
		String cdagente=mesaControlDAO.cargarCdagentePorCdusuari(params);
		logger.info(""
				+ "\ncdagente: "+cdagente
				+ "\n###### cargarCdagentePorCdusuari ######"
				+ "\n#######################################");
		return cdagente;
	}
	
	@Override
	public void movimientoDetalleTramite(
			String ntramite
			,Date feinicio
			,String cdclausu
			,String comments
			,String cdusuari
			,String cdmotivo
			,String cdsisrol
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ movimientoDetalleTramite @@@@@@"
				,"\n@@@@@@ ntramite=" , ntramite
				,"\n@@@@@@ feinicio=" , feinicio
				,"\n@@@@@@ cdclausu=" , cdclausu
				,"\n@@@@@@ comments=" , comments
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdmotivo=" , cdmotivo
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				));
		
		mesaControlDAO.movimientoDetalleTramite(ntramite, feinicio, cdclausu, comments, cdusuari, cdmotivo, cdsisrol);
		
		logger.debug(Utils.log(
				 "\n@@@@@@ movimientoDetalleTramite @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void validarAntesDeTurnar(
    		String ntramite
    		,String status
    		,String cdusuari
    		,String cdsisrol
    		)throws Exception
    {
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ validarAntesDeTurnar @@@@@@"
				,"\n@@@@@@ ntramite=" , ntramite
				,"\n@@@@@@ status="   , status
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				));
		
		mesaControlDAO.validarAntesDeTurnar(ntramite,status,cdusuari,cdsisrol);
		
		logger.debug(Utils.log(
				 "\n@@@@@@ validarAntesDeTurnar @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
    }
	
	/*
	 * Getters y setters
	 */
	@Override
	public void guardarRegistroContrarecibo(String ntramite,String cdusuari)throws Exception
	{
		mesaControlDAO.guardarRegistroContrarecibo(ntramite,cdusuari);
	}
	
	@Override
	public void actualizarNombreDocumento(String ntramite,String cddocume,String nuevo)throws Exception
	{
		mesaControlDAO.actualizarNombreDocumento(ntramite,cddocume,nuevo);
	}
	
	@Override
	public void borrarDocumento(String ntramite,String cddocume)throws Exception
	{
		mesaControlDAO.borrarDocumento(ntramite,cddocume);
	}
	
	public void setMesaControlDAO(MesaControlDAO mesaControlDAO) {
		this.mesaControlDAO = mesaControlDAO;
	}
}