package mx.com.gseguros.portal.mesacontrol.service.impl;

import java.util.HashMap;
import java.util.Map;

import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.portal.mesacontrol.service.MesaControlManager;

import org.apache.log4j.Logger;

public class MesaControlManagerImpl implements MesaControlManager
{
	private final Logger logger=Logger.getLogger(MesaControlManagerImpl.class);
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
	
	/*
	 * Getters y setters
	 */
	public void setMesaControlDAO(MesaControlDAO mesaControlDAO) {
		this.mesaControlDAO = mesaControlDAO;
	}
}