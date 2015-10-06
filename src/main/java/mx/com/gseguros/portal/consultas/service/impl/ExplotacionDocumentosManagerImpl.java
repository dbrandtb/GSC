package mx.com.gseguros.portal.consultas.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.consultas.service.ExplotacionDocumentosManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.utils.Utils;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExplotacionDocumentosManagerImpl implements ExplotacionDocumentosManager
{
	private final static Logger logger = LoggerFactory.getLogger(ExplotacionDocumentosManagerImpl.class);
	
	@Autowired
	private PantallasDAO pantallasDAO;
	
	@Override
	public Map<String,Item> pantallaExplotacionDocumentos(String cdusuari, String cdsisrol) throws Exception
	{
		logger.debug(Utils.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ pantallaExplotacionDocumentos @@@@@@"
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				));
		
		Map<String,Item> items = new HashMap<String,Item>();
		String           paso  = null;
		
		try
		{
			paso = "Recuperando componentes";
			logger.debug(paso);
			
			List<ComponenteVO> itemsFormBusq = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"EXPLOTACION_DOCUMENTOS"
					,"FORM_BUSQUEDA"
					,null //orden
					);
			
			List<ComponenteVO> compsGridPolizas = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"EXPLOTACION_DOCUMENTOS"
					,"GRID_POLIZAS"
					,null //orden
					);
			
			paso = "Construyendo componentes";
			logger.debug(paso);
			
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			gc.generaComponentes(itemsFormBusq, true, false, true, false, false, false);
			items.put("itemsFormBusq" , gc.getItems());
			
			gc.generaComponentes(compsGridPolizas, true, true, false, true, false, false);
			items.put("gridPolizasFields" , gc.getFields());
			items.put("gridPolizasCols"   , gc.getColumns());
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.join(
				 "\n@@@@@@ pantallaExplotacionDocumentos @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
}