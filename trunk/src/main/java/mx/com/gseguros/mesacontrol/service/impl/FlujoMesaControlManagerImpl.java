package mx.com.gseguros.mesacontrol.service.impl;

import java.util.HashMap;
import java.util.Map;

import mx.com.gseguros.mesacontrol.dao.FlujoMesaControlDAO;
import mx.com.gseguros.mesacontrol.service.FlujoMesaControlManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlujoMesaControlManagerImpl implements FlujoMesaControlManager {
	
	@Autowired
	private FlujoMesaControlDAO flujoMesaControlDAO;
	
	@Override
	public Map<String,Item> workflow(
			StringBuilder sb
			,String cdsisrol
			) throws Exception
	{
		sb.append(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ workflow @@@@@@"
				,"\n@@@@@@ cdsisrol=",cdsisrol
				));
		
		Map<String,Item> items = new HashMap<String,Item>();
		String           paso  = null;
		
		try
		{
			paso = "Generando exception";
			sb.append("\n").append(paso);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ workflow @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
}