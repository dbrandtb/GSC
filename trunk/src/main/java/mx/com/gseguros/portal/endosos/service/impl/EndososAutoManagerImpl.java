package mx.com.gseguros.portal.endosos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaBaseVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.endosos.service.EndososAutoManager;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.utils.Utilerias;

public class EndososAutoManagerImpl implements EndososAutoManager
{
	private static Logger logger = Logger.getLogger(EndososAutoManagerImpl.class);
	
	private Map<String,Object>session;
	
	private PantallasDAO pantallasDAO;

	/*
	 * Utilidades
	 */
	private void setCheckpoint(String checkpoint)
	{
		logger.debug(Utilerias.join("checkpoint-->",checkpoint));
		session.put("checkpoint",checkpoint);
	}
	
	private String getCheckpoint()
	{
		return (String)session.get("checkpoint");
	}
	
	private void manejaException(Exception ex,ManagerRespuestaBaseVO resp)
	{
		long timestamp = System.currentTimeMillis();
		resp.setExito(false);
		
		if(ex.getClass().equals(ApplicationException.class))
		{
			resp.setRespuesta(Utilerias.join(ex.getMessage()," #",timestamp));
		}
		else
		{
			resp.setRespuesta(Utilerias.join("Error ",getCheckpoint().toLowerCase()," #",timestamp));
		}
		
		logger.error(resp.getRespuesta(),ex);
		setCheckpoint("0");
	}
	/*
	 * Utilidades
	 */
	
	@Override
	public ManagerRespuestaImapVO construirMarcoEndosos()
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ construirMarcoEndosos @@@@@@"
				));
		
		ManagerRespuestaImapVO resp=new ManagerRespuestaImapVO(true);
		resp.setImap(new HashMap<String,Item>());
		
		try
		{
			setCheckpoint("Recuperando componentes del formulario de busqueda");
			List<ComponenteVO>componentesFiltro=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,null //cdsisrol
					,"MARCO_ENDOSOS_AUTO" //pantalla
					,"FORM_BUSQUEDA"      //seccion
					,null //orden
					);
			
			setCheckpoint("Recuperando componentes del grid de polizas");
			List<ComponenteVO>componentesGridPolizas=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,null //cdsisrol
					,"MARCO_ENDOSOS_AUTO" //pantalla
					,"GRID_POLIZAS"       //seccion
					,null //orden
					);
			
			setCheckpoint("Recuperando componentes del grid de historico de poliza");
			List<ComponenteVO>componentesGridHistoricoPoliza=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,null //cdsisrol
					,"MARCO_ENDOSOS_AUTO"    //pantalla
					,"GRID_HISTORICO_POLIZA" //seccion
					,null //orden
					);
			
			setCheckpoint("Construyendo componentes del formulario de busqueda");
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(componentesFiltro, true, false, true, false, false, false);
			resp.getImap().put("formBusqItems" , gc.getItems());
			
			setCheckpoint("Construyendo componentes del grid de polizas");
			gc.generaComponentes(componentesGridPolizas, true, false, false, true, true, false);
			resp.getImap().put("gridPolizasColumns" , gc.getColumns());
			
			setCheckpoint("Construyendo componentes del grid de historico de poliza");
			gc.generaComponentes(componentesGridHistoricoPoliza, true, false, false, true, false, false);
			resp.getImap().put("gridHistoricoColumns" , gc.getColumns());
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ construirMarcoEndosos @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO recuperarColumnasIncisoRamo(String cdramo)
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarColumnasIncisoRamo @@@@@@"
				,"\n@@@@@@ cdramo=",cdramo
				));
		
		ManagerRespuestaSmapVO resp=new ManagerRespuestaSmapVO(true);
		resp.setSmap(new HashMap<String,String>());
		
		try
		{
			setCheckpoint("Recuperando columnas de incisos para el producto");
			List<ComponenteVO>columnas=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,cdramo
					,null //cdtipsit
					,null //estado
					,null //cdsisrol
					,"MARCO_ENDOSOS_AUTO" //pantalla
					,"COLUMNAS_INCISO"    //seccion
					,null //orden
					);
			
			setCheckpoint("Construyendo columnas de incisos para el producto");
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(columnas, true, false, false, true, true, false);
			resp.getSmap().put("columnas",gc.getColumns().toString());
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ recuperarColumnasIncisoRamo @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
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
}