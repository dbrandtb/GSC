package mx.com.gseguros.mesacontrol.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.mesacontrol.dao.FlujoMesaControlDAO;
import mx.com.gseguros.mesacontrol.model.FlujoVO;
import mx.com.gseguros.mesacontrol.service.FlujoMesaControlManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlujoMesaControlManagerImpl implements FlujoMesaControlManager
{
	private static Logger logger = LoggerFactory.getLogger(FlujoMesaControlManagerImpl.class);
	
	@Autowired
	private FlujoMesaControlDAO flujoMesaControlDAO;
	
	@Autowired
	private PantallasDAO pantallasDAO;
	
	@Autowired
	private MesaControlDAO mesaControlDAO;
	
	@Override
	public Map<String,Item> workflow(String cdsisrol) throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ workflow @@@@@@"
				,"\n@@@@@@ cdsisrol=",cdsisrol
				));
		
		Map<String,Item> items = new HashMap<String,Item>();
		String           paso  = null;
		
		try
		{
			paso = "Recuperando componentes";
			logger.debug(paso);
			
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			List<ComponenteVO> ttipfluCmp = pantallasDAO.obtenerComponentes(null, null, null, null, null, cdsisrol, "FLUJOMC", "TTIPFLU", null);
			gc.generaComponentes(ttipfluCmp, true, false, true, false, false, false);
			
			items.put("ttipfluFormItems" , gc.getItems());
			
			List<ComponenteVO> tdocumeCmp = pantallasDAO.obtenerComponentes(null, null, null, null, null, cdsisrol, "FLUJOMC", "TDOCUME", null);
			gc.generaComponentes(tdocumeCmp, true, false, true, false, false, false);
			
			items.put("tdocumeFormItems" , gc.getItems());
			
			List<ComponenteVO> comboCdtipram = pantallasDAO.obtenerComponentes(null, null, null, null, null, cdsisrol, "FLUJOMC", "COMBO_CDTIPRAM", null);
			gc.generaComponentes(comboCdtipram, true, false, true, false, false, false);
			
			items.put("comboCdtipram" , gc.getItems());
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ workflow @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	@Override
	public String movimientoTtipflumc(
			String accion
			,String cdtipflu
			,String dstipflu
			,String cdtiptra
			,String swreqpol
			,String swmultipol
			,String cdtipsup
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ movimientoTtipflumc @@@@@@"
				,"\n@@@@@@ accion="     , accion
				,"\n@@@@@@ cdtipflu="   , cdtipflu
				,"\n@@@@@@ dstipflu="   , dstipflu
				,"\n@@@@@@ cdtiptra="   , cdtiptra
				,"\n@@@@@@ swreqpol="   , swreqpol
				,"\n@@@@@@ swmultipol=" , swmultipol
				,"\n@@@@@@ cdtipsup="   , cdtipsup
				));
		
		String paso = "Guardando tr\u00E1mite";
		logger.debug(paso);
		
		String cdtipfluRes = null;
		
		try
		{
			cdtipfluRes = flujoMesaControlDAO.movimientoTtipflumc(
					cdtipflu
					,dstipflu
					,cdtiptra
					,"S".equals(swmultipol) ? "S" : "N"
					,"S".equals(swreqpol) ? "S" : "N"
					,cdtipsup
					,accion
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
				 "\n@@@@@@ cdtipflu = ", cdtipfluRes
				,"\n@@@@@@ movimientoTtipflumc @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		
		return cdtipfluRes;
	}
	
	@Override
	public String movimientoTflujomc(
			String accion
			,String cdtipflu
			,String cdflujomc
			,String dsflujomc
			,String swfinal
			,String cdtipram
			,String swgrupo
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ movimientoTflujomc @@@@@@"
				,"\n@@@@@@ accion    = " , accion
				,"\n@@@@@@ cdtipflu  = " , cdtipflu
				,"\n@@@@@@ cdflujomc = " , cdflujomc
				,"\n@@@@@@ dsflujomc = " , dsflujomc
				,"\n@@@@@@ swfinal   = " , swfinal
				,"\n@@@@@@ cdtipram  = " , cdtipram
				,"\n@@@@@@ swgrupo   = " , swgrupo
				));
		
		String paso = "Guardando proceso";
		logger.debug(paso);
		
		String cdflujomcRes = null;
		
		try
		{
			cdflujomcRes = flujoMesaControlDAO.movimientoTflujomc(
					cdtipflu
					,cdflujomc
					,dsflujomc
					,"S".equals(swfinal) ? "S" : "N"
					,cdtipram
					,"S".equals(swgrupo) ? "S" : "N"
					,accion
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ cdflujomc = ", cdflujomcRes
				,"\n@@@@@@ movimientoTflujomc @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		
		return cdflujomcRes;
	}
	
	@Override
	public void movimientoCatalogo(
			String accion
			,String tipo
			,Map<String,String> params
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ movimientoCatalogo @@@@@@"
				,"\n@@@@@@ accion=" , accion
				,"\n@@@@@@ tipo="   , tipo
				,"\n@@@@@@ params=" , params
				));
		
		String paso = "Guardando cat\u00e1logo";
		logger.debug(paso);
		
		try
		{
			if("E".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTestadomc(
						accion
						,params.get("CDESTADOMC")
						,params.get("DSESTADOMC")
						);
			}
			else if("P".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTpantmc(
						params.get("CDPANTMC")
						,params.get("DSPANTMC")
						,params.get("URLPANTMC")
						,"S".equals(params.get("SWEXTERNA")) ? "S" : "N"
						,accion
						);
			}
			else if("C".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTcompmc(
						accion
						,params.get("CDCOMPMC")
						,params.get("DSCOMPMC")
						,params.get("NOMCOMP")
						);
			}
			else if("O".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTprocmc(
						params.get("CDPROCMC")
						,params.get("DSPROCMC")
						,params.get("URLPROCMC")
						,accion);
			}
			else
			{
				throw new ApplicationException("Tipo no reconocido");
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ movimientoCatalogo @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public String registrarEntidad(
			String cdtipflu
			,String cdflujomc
			,String tipo
			,String clave
			,String webid
			,String xpos
			,String ypos
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ registrarEntidad @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ tipo="      , tipo
				,"\n@@@@@@ clave="     , clave
				,"\n@@@@@@ webid="     , webid
				,"\n@@@@@@ xpos="      , xpos
				,"\n@@@@@@ ypos="      , ypos
				));
		
		String cdentidad = null
		       ,paso     = "Registrando entidad";
		logger.debug(paso);
		
		try
		{
			if("E".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTfluest(
						cdtipflu
						,cdflujomc
						,clave //cdestadomc
						,webid
						,xpos
						,ypos
						,null //timemax
						,null //timewrn1
						,null //timewrn2
						,"1"  //cdtipasig
						,"I"
						);
			}
			else if("P".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTflupant(
						cdtipflu
						,cdflujomc
						,clave //cdpantmc
						,webid
						,xpos
						,ypos
						,"I"
						);
			}
			else if("C".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTflucomp(
						cdtipflu
						,cdflujomc
						,clave //cdcompmc
						,webid
						,xpos
						,ypos
						,"I"
						);
			}
			else if("O".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTfluproc(
						cdtipflu
						,cdflujomc
						,clave //cdprocmc
						,webid
						,xpos
						,ypos
						,"I"
						);
			}
			else if("V".equals(tipo))
			{
				cdentidad = flujoMesaControlDAO.movimientoTfluval(
						cdtipflu
						,cdflujomc
						,null //cdvalida
						,"" //dsvalida
						,"-1" //cdvalidafk
						,webid
						,xpos
						,ypos
						,""
						,"I"
						);
			}
			else if("R".equals(tipo))
			{
				cdentidad = flujoMesaControlDAO.movimientoTflurev(
						cdtipflu
						,cdflujomc
						,null //cdrevisi
						,""   //dsrevisi
						,webid
						,xpos
						,ypos
						,"I"
						);
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ cdentidad=",cdentidad
				,"\n@@@@@@ registrarEntidad @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return cdentidad;
	}
	
	@Override
	public void borrarEntidad(
			String cdtipflu
			,String cdflujomc
			,String tipo
			,String clave
			,String webid
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ borrarEntidad @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ tipo="      , tipo
				,"\n@@@@@@ clave="     , clave
				,"\n@@@@@@ webid="     , webid
				));
		
		String paso = "Borrando entidad";
		logger.debug(paso);
		
		try
		{
			if("E".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTfluest(
						cdtipflu
						,cdflujomc
						,clave //cdestadomc
						,webid
						,null //xpos
						,null //ypos
						,null //timemax
						,null //timewrn1
						,null //timewrn2
						,null //cdtipasig
						,"D"
						);
			}
			else if("P".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTflupant(
						cdtipflu
						,cdflujomc
						,clave//cdpantmc
						,webid
						,null//xpos
						,null//ypos
						,"D"
						);
			}
			else if("C".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTflucomp(
						cdtipflu
						,cdflujomc
						,clave //cdcompmc
						,webid
						,null //xpos
						,null //ypos
						,"D" //accion
						);
			}
			else if("O".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTfluproc(
						cdtipflu
						,cdflujomc
						,clave //cdprocmc
						,webid
						,null//xpos
						,null//ypos
						,"D"//accion
						);
			}
			else if("V".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTfluval(
						cdtipflu
						,cdflujomc
						,clave //cdvalida
						,null //dsvalida
						,null //cdvalidafk
						,webid
						,null //xpos
						,null //ypoS
						,""//jsvalida
						,"D" //accion
						);
			}
			else if("R".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTflurev(
						cdtipflu
						,cdflujomc
						,clave //cdrevisi
						,null //dsrevisi
						,webid
						,null //xpos
						,null //ypos
						,"D" //accion
						);
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ borrarEntidad @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public List<Map<String,String>> cargarModelado(
			String cdtipflu
			,String cdflujomc
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ cargarModelado @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				));
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String                   paso = null;
		
		try
		{
			paso = "Recuperando status";
			logger.debug(paso);
			
			List<Map<String,String>> estados = flujoMesaControlDAO.recuperaTfluest(cdtipflu, cdflujomc, null);
			
			if(estados.size()>0)
			{
				for(Map<String,String>estado:estados)
				{
					estado.put("TIPO" , "E");
					list.add(estado);
				}
				
				paso = "Recuperando pantallas";
				logger.debug(paso);
				
				List<Map<String,String>> pantallas = flujoMesaControlDAO.recuperaTflupant(
						cdtipflu
						,cdflujomc
						,null
						);
				
				for(Map<String,String>pantalla:pantallas)
				{
					pantalla.put("TIPO" , "P");
					list.add(pantalla);
				}
				
				paso = "Recuperando componentes";
				logger.debug(paso);
				
				List<Map<String,String>> componentes = flujoMesaControlDAO.recuperaTflucomp(
						cdtipflu
						,cdflujomc);
				
				for(Map<String,String>componente:componentes)
				{
					componente.put("TIPO" , "C");
					list.add(componente);
				}
				
				List<Map<String,String>> procesos = flujoMesaControlDAO.recuperaTfluproc(cdtipflu, cdflujomc);
				
				for(Map<String,String>proceso:procesos)
				{
					proceso.put("TIPO" , "O");
					list.add(proceso);
				}
				
				List<Map<String,String>> validaciones = flujoMesaControlDAO.recuperaTfluval(cdtipflu, cdflujomc, null);
				
				for(Map<String,String>proceso:validaciones)
				{
					proceso.put("TIPO" , "V");
					list.add(proceso);
				}
				
				List<Map<String,String>> revisiones = flujoMesaControlDAO.recuperaTflurev(cdtipflu, cdflujomc);
				
				for(Map<String,String>revision:revisiones)
				{
					revision.put("TIPO" , "R");
					list.add(revision);
				}
				
				List<Map<String,String>> acciones = flujoMesaControlDAO.recuperaTfluacc(cdtipflu,cdflujomc);
				
				for(Map<String,String>accion:acciones)
				{
					accion.put("TIPO" , "A");
					list.add(accion);
				}
			}
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ list=" , list
				,"\n@@@@@@ cargarModelado @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return list;
	}
	
	@Override
	public Map<String,Object> cargarDatosEstado(
			String cdtipflu
			,String cdflujomc
			,String cdestadomc
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ cargarDatosEstado @@@@@@"
				,"\n@@@@@@ cdtipflu="   , cdtipflu
				,"\n@@@@@@ cdflujomc="  , cdflujomc
				,"\n@@@@@@ cdestadomc=" , cdestadomc
				));
		
		Map<String,Object> result = new HashMap<String,Object>();
		String             paso   = null;
		
		try
		{
			paso = "Recuperando status";
			logger.debug(paso);
			
			List<Map<String,String>> lista  = flujoMesaControlDAO.recuperaTfluest(cdtipflu, cdflujomc, cdestadomc);
			Map<String,String>       estado = null;
			
			for(Map<String,String>listaItem:lista)
			{
				if(listaItem.get("CDESTADOMC").equals(cdestadomc))
				{
					estado = listaItem;
					break;
				}
			}
			
			if(estado==null)
			{
				throw new ApplicationException("No se encuentra el status");
			}
			
			if(StringUtils.isBlank(estado.get("TIMEMAX")))
			{
				estado.put("TIMEMAX","0");
			}
			if(StringUtils.isBlank(estado.get("TIMEWRN1")))
			{
				estado.put("TIMEWRN1","0");
			}
			if(StringUtils.isBlank(estado.get("TIMEWRN2")))
			{
				estado.put("TIMEWRN2","0");
			}
			
			Double tmax = Double.parseDouble(estado.get("TIMEMAX"));
			Double wrn1 = Double.parseDouble(estado.get("TIMEWRN1"));
			Double wrn2 = Double.parseDouble(estado.get("TIMEWRN2"));
			
			estado.put("TIMEMAXH" , String.format("%.0f",Math.floor(tmax/60d)));
			estado.put("TIMEMAXM" , String.format("%.0f",Math.floor(tmax%60d)));
			
			estado.put("TIMEWRN1H" , String.format("%.0f",Math.floor(wrn1/60d)));
			estado.put("TIMEWRN1M" , String.format("%.0f",Math.floor(wrn1%60d)));
			
			estado.put("TIMEWRN2H" , String.format("%.0f",Math.floor(wrn2/60d)));
			estado.put("TIMEWRN2M" , String.format("%.0f",Math.floor(wrn2%60d)));
			
			paso = "Recuperando permisos";
			logger.debug(paso);
			
			List<Map<String,String>> permisos = flujoMesaControlDAO.recuperaTfluestrol(cdtipflu, cdflujomc, estado.get("CDESTADOMC"));
			
			paso = "Recuperando avisos";
			logger.debug(paso);
			
			List<Map<String,String>> avisos = flujoMesaControlDAO.recuperaTfluestavi(cdtipflu, cdflujomc, estado.get("CDESTADOMC"));

			List<Map<String,String>> listaProps = new ArrayList<Map<String,String>>();
			
			for(Map<String,String>permiso:permisos)
			{
				permiso.put("TIPO" , "P");
				listaProps.add(permiso);
			}
			
			for(Map<String,String>aviso:avisos)
			{
				aviso.put("TIPO" , "A");
				listaProps.add(aviso);
			}
			
			logger.debug(Utils.log(
					 "\nmapa="  , estado
					,"\nlista=" , listaProps
					));
			
			result.put("mapa"  , estado);
			result.put("lista" , listaProps);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ cargarDatosEstado @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return result;
	}
	
	@Override
	public void guardarDatosEstado(
			String cdtipflu
			,String cdflujomc
			,String cdestadomc
			,String accion
			,String webid
			,String xpos
			,String ypos
			,String timemaxh
			,String timemaxm
			,String timewrn1h
			,String timewrn1m
			,String timewrn2h
			,String timewrn2m
			,String cdtipasig
			,String swescala
			,List<Map<String,String>>list
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarDatosEstado @@@@@@"
				,"\n@@@@@@ cdtipflu="   , cdtipflu
				,"\n@@@@@@ cdflujomc="  , cdflujomc
				,"\n@@@@@@ cdestadomc=" , cdestadomc
				,"\n@@@@@@ accion="     , accion
				,"\n@@@@@@ webid="      , webid
				,"\n@@@@@@ xpos="       , xpos
				,"\n@@@@@@ ypos="       , ypos
				,"\n@@@@@@ timemaxh="   , timemaxh
				,"\n@@@@@@ timemaxm="   , timemaxm
				,"\n@@@@@@ timewrn1h="  , timewrn1h
				,"\n@@@@@@ timewrn1m="  , timewrn1m
				,"\n@@@@@@ timewrn2h="  , timewrn2h
				,"\n@@@@@@ timewrn2m="  , timewrn2m
				,"\n@@@@@@ cdtipasig="  , cdtipasig
				,"\n@@@@@@ swescala="   , swescala
				,"\n@@@@@@ list="       , list
				));
		
		String paso = null;
		try
		{
			paso = "Guardando datos de status";
			logger.debug(paso);
			
			flujoMesaControlDAO.movimientoTfluest(
					cdtipflu
					,cdflujomc
					,cdestadomc
					,webid
					,xpos
					,ypos
					,new Long((Long.valueOf(timemaxm))+(Long.valueOf(timemaxh)*60)).toString()   //timemax
					,new Long((Long.valueOf(timewrn1m))+(Long.valueOf(timewrn1h)*60)).toString() //timewrn1
					,new Long((Long.valueOf(timewrn2m))+(Long.valueOf(timewrn2h)*60)).toString() //timewrn2
					,cdtipasig
					,accion
					);
			
			paso = "Guardando permisos y avisos";
			logger.debug(paso);
			
			for(Map<String,String>ite : list)
			{
				String tipo = ite.get("TIPO");
				if(tipo.equals("P"))
				{
					flujoMesaControlDAO.movimientoTfluestrol(
							cdtipflu
							,cdflujomc
							,cdestadomc
							,ite.get("CDSISROL")
							,"S".equals(ite.get("SWVER")) ? "S" : "N"
							,"S".equals(ite.get("SWTRABAJO")) ? "S" : "N"
							,"S".equals(ite.get("SWCOMPRA")) ? "S" : "N"
							,"S".equals(ite.get("SWREASIG")) ? "S" : "N"
							,"" //cdrolasig
							,"S".equals(ite.get("SWVERDEF")) ? "S" : "N"
							,"I"
							);
				}
				else if(tipo.equals("A"))
				{
					flujoMesaControlDAO.movimientoTfluestavi(
							cdtipflu
							,cdflujomc
							,cdestadomc
							,ite.get("CDAVISO")
							,"1" //cdtipavi
							,""  //dscoment
							,"N" //swautoavi
							,ite.get("DSMAILAVI")
							,null //cdusuariavi
							,null //cdsisrolavi
							,"U"
							);
				}
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ guardarDatosEstado @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public String registrarConnection(
			String cdtipflu
			,String cdflujomc
			,String idorigen
			,String iddestin
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ registrarConnection @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ idorigen="  , idorigen
				,"\n@@@@@@ iddestin="  , iddestin
				));
		
		String cdaccion = null
		       ,paso    = null;
		
		try
		{
			paso = "Registrando conexi\u00f3n";
			logger.debug(paso);
			
			cdaccion = flujoMesaControlDAO.movimientoTfluacc(
					cdtipflu
					,cdflujomc
					,null //cdaccion
					,""   //dsaccion
					,""   //cdicono
					,""   //cdvalor
					,idorigen
					,iddestin
					,null
					,null
					,"I"
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ cdaccion=",cdaccion
				,"\n@@@@@@ registrarConnection @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return cdaccion;
	}
	
	@Override
	public Map<String,String> cargarDatosValidacion(
			String cdtipflu
			,String cdflujomc
			,String cdvalida
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ cargarDatosValidacion @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ cdvalida="  , cdvalida
				));
		
		Map<String,String> validacion = null;
		String             paso       = null;
		
		try
		{
			paso = "Recuperando validaciones";
			logger.debug(paso);
			
			List<Map<String,String>> validaciones = flujoMesaControlDAO.recuperaTfluval(cdtipflu, cdflujomc, cdvalida);
			
			for(Map<String,String>validaIte : validaciones)
			{
				if(validaIte.get("CDVALIDA").equals(cdvalida))
				{
					validacion = validaIte;
					break;
				}
			}
			
			if(validacion==null)
			{
				throw new ApplicationException("No se encuentra la validaci\u00f3n");
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ cargarDatosValidacion @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return validacion;
	}
	
	@Override
	public void guardarDatosValidacion(
			String cdtipflu
			,String cdflujomc
			,String cdvalida
			,String webid
			,String xpos
			,String ypos
			,String dsvalida
			,String cdvalidafk
			,String jsvalida
			,String accion
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarDatosValidacion @@@@@@"
				,"\n@@@@@@ cdtipflu="   , cdtipflu
				,"\n@@@@@@ cdflujomc="  , cdflujomc
				,"\n@@@@@@ cdvalida="   , cdvalida
				,"\n@@@@@@ webid="      , webid
				,"\n@@@@@@ xpos="       , xpos
				,"\n@@@@@@ ypos="       , ypos
				,"\n@@@@@@ dsvalida="   , dsvalida
				,"\n@@@@@@ cdvalidafk=" , cdvalidafk
				,"\n@@@@@@ jsvalida="   , jsvalida
				,"\n@@@@@@ accion="     , accion
				));
		
		String paso = "Guardando datos de validaci\u00f3n";
		logger.debug(paso);
		
		try
		{
			flujoMesaControlDAO.movimientoTfluval(
					cdtipflu
					,cdflujomc
					,cdvalida
					,dsvalida
					,cdvalidafk
					,webid
					,xpos
					,ypos
					,jsvalida
					,accion
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ guardarDatosValidacion @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void guardarCoordenadas(
			String cdtipflu
			,String cdflujomc
			,List<Map<String,String>>list
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarCoordenadas @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ list="      , list
				));

		String paso = "Guardando coordenadas";
		try
		{
			for(Map<String,String>entidad:list)
			{
				flujoMesaControlDAO.actualizaCoordenadas(
						cdtipflu
						,cdflujomc
						,entidad.get("tipo")
						,entidad.get("clave")
						,entidad.get("webid")
						,entidad.get("xpos")
						,entidad.get("ypos")
						);
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ guardarCoordenadas @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public String ejecutaValidacion(
			FlujoVO flujo
			,String cdvalidafk
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ ejecutaValidacion @@@@@@"
				,"\n@@@@@@ flujo="      , flujo
				,"\n@@@@@@ cdvalidafk=" , cdvalidafk
				));
		
		String salida = null
		       ,paso  = null;
		
		try
		{
			paso = "Ejecutando validaci\u00f3n";
			logger.debug(paso);
			
			salida = flujoMesaControlDAO.ejecutaValidacion(
					flujo.getNtramite()
					,flujo.getStatus()
					,flujo.getCdunieco()
					,flujo.getCdramo()
					,flujo.getEstado()
					,flujo.getNmpoliza()
					,flujo.getNmsituac()
					,flujo.getNmsuplem()
					,cdvalidafk
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ salida=",salida
				,"\n@@@@@@ ejecutaValidacion @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		
		return salida;
	}
	
	@Override
	public Map<String,Object> cargarDatosRevision(
			String cdtipflu
			,String cdflujomc
			,String cdrevisi
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ cargarDatosRevision @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ cdrevisi="  , cdrevisi
				));
		
		Map<String,Object> result = new HashMap<String,Object>();
		String             paso   = null;
		
		try
		{
			paso = "Recuperando revisi\u00f3n";
			logger.debug(paso);
			
			List<Map<String,String>> lista    = flujoMesaControlDAO.recuperaTflurev(cdtipflu, cdflujomc);
			Map<String,String>       revision = null;
			
			for(Map<String,String>listaItem:lista)
			{
				if(listaItem.get("CDREVISI").equals(cdrevisi))
				{
					revision = listaItem;
					break;
				}
			}
			
			if(revision==null)
			{
				throw new ApplicationException("No se encuentra la revisi\u00f3n");
			}
			
			paso = "Recuperando documentos";
			logger.debug(paso);
			
			List<Map<String,String>> permisos = flujoMesaControlDAO.recuperaTflurevdoc(cdtipflu, cdflujomc, cdrevisi);
			
			logger.debug(Utils.log(
					 "\nmapa="  , revision
					,"\nlista=" , permisos
					));
			
			result.put("mapa"  , revision);
			result.put("lista" , permisos);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ cargarDatosRevision @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return result;
	}
	
	@Override
	public void guardarDatosRevision(
			String cdtipflu
			,String cdflujomc
			,String cdrevisi
			,String dsrevisi
			,String accion
			,String webid
			,String xpos
			,String ypos
			,List<Map<String,String>>list
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarDatosRevision @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ cdrevisi="  , cdrevisi
				,"\n@@@@@@ dsrevisi="  , dsrevisi
				,"\n@@@@@@ accion="    , accion
				,"\n@@@@@@ webid="     , webid
				,"\n@@@@@@ xpos="      , xpos
				,"\n@@@@@@ ypos="      , ypos
				,"\n@@@@@@ list="      , list
				));
		
		String paso = null;
		try
		{
			paso = "Guardando datos de revisi\u00f3n";
			logger.debug(paso);
			
			flujoMesaControlDAO.movimientoTflurev(
					cdtipflu
					,cdflujomc
					,cdrevisi
					,dsrevisi
					,webid
					,xpos
					,ypos
					,accion
					);
			
			paso = "Guardando documentos";
			logger.debug(paso);
			
			for(Map<String,String>ite : list)
			{
				flujoMesaControlDAO.movimientoTflurevdoc(
						cdtipflu
						,cdflujomc
						,cdrevisi
						,ite.get("CDDOCUME")
						,ite.get("SWOBLIGA")
						,"I"
						);
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ guardarDatosRevision @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void movimientoTdocume(
			String accion
			,String cddocume
			,String dsdocume
			,String cdtiptra
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ movimientoTdocume @@@@@@"
				,"\n@@@@@@ accion="   , accion
				,"\n@@@@@@ dsdocume=" , dsdocume
				,"\n@@@@@@ dsdocume=" , dsdocume
				,"\n@@@@@@ cdtiptra=" , cdtiptra
				));
		
		String paso = "Guardando documento";
		logger.debug(paso);
		
		try
		{
			flujoMesaControlDAO.movimientoTdocume(cddocume, dsdocume, cdtiptra, accion);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
				 "\n@@@@@@ movimientoTdocume @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void borrarConnection(
			String cdtipflu
			,String cdflujomc
			,String cdaccion
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ borrarConnection @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ cdaccion="  , cdaccion
				));
		
		String paso = "Borrando acci\u00f3n";
		logger.debug(paso);
		
		try
		{
			flujoMesaControlDAO.movimientoTfluacc(
					cdtipflu
					,cdflujomc
					,cdaccion
					,null //dsaccion
					,null //cdicono
					,null //cdvalor
					,null //idorigen
					,null //iddestin
					,null
					,null
					,"D" //accion
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ borrarConnection @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public Map<String,Object> cargarDatosAccion(
			String cdtipflu
			,String cdflujomc
			,String cdaccion
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ cargarDatosAccion @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ cdaccion="  , cdaccion
				));
		
		Map<String,Object> result = new HashMap<String,Object>();
		String             paso   = null;
		
		try
		{
			paso = "Recuperando acci\u00f3n";
			logger.debug(paso);
			
			List<Map<String,String>> lista    = flujoMesaControlDAO.recuperaTfluacc(cdtipflu, cdflujomc);
			Map<String,String>       accion = null;
			
			for(Map<String,String>listaItem:lista)
			{
				if(listaItem.get("CDACCION").equals(cdaccion))
				{
					accion = listaItem;
					break;
				}
			}
			
			if(accion==null)
			{
				throw new ApplicationException("No se encuentra la acci\u00f3n");
			}
			
			paso = "Recuperando permisos";
			logger.debug(paso);
			
			List<Map<String,String>> permisos = flujoMesaControlDAO.recuperaTfluaccrol(cdtipflu, cdflujomc, cdaccion);
			
			logger.debug(Utils.log(
					 "\nmapa="  , accion
					,"\nlista=" , permisos
					));
			
			result.put("mapa"  , accion);
			result.put("lista" , permisos);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ cargarDatosAccion @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return result;
	}
	
	@Override
	public void guardarDatosAccion(
			String cdtipflu
			,String cdflujomc
			,String cdaccion
			,String dsaccion
			,String accion
			,String idorigen
			,String iddestin
			,String cdvalor
			,String cdicono
			,String swescala
			,String aux
			,List<Map<String,String>>list
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarDatosAccion @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ cdaccion="  , cdaccion
				,"\n@@@@@@ dsaccion="  , dsaccion
				,"\n@@@@@@ accion="    , accion
				,"\n@@@@@@ idorigen="  , idorigen
				,"\n@@@@@@ iddestin="  , iddestin
				,"\n@@@@@@ cdvalor="   , cdvalor
				,"\n@@@@@@ cdicono="   , cdicono
				,"\n@@@@@@ swescala="  , swescala
				,"\n@@@@@@ aux="       , aux
				,"\n@@@@@@ list="      , list
				));
		
		String paso = null;
		
		try
		{
			paso = "Guardando datos de acci\u00f3n";
			logger.debug(paso);
			
			flujoMesaControlDAO.movimientoTfluacc(
					cdtipflu
					,cdflujomc
					,cdaccion
					,dsaccion
					,cdicono
					,cdvalor
					,idorigen
					,iddestin
					,"S".equals(swescala) ? "S" : "N"
					,aux
					,accion
					);
			
			paso = "Guardando permisos";
			logger.debug(paso);
			
			for(Map<String,String>ite : list)
			{
				flujoMesaControlDAO.movimientoTfluaccrol(
						cdtipflu
						,cdflujomc
						,cdaccion
						,ite.get("CDSISROL")
						,"S".equals(ite.get("SWPERMISO")) ? "S" : "N"
						,"I"
						);
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ guardarDatosAccion @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public Map<String,Item> debugScreen() throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ debugScreen @@@@@@"
				));
		String           paso  = null;
		Map<String,Item> items = new HashMap<String,Item>();
		try
		{
			paso = "Recuperando componentes";
			logger.debug(paso);
			
			List<ComponenteVO> comps = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,null //cdsisrol
					,"DEBUG_SCREEN"
					,"ITEMS"
					,null //orden
					);
			
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(comps, true, false, true, false, false, false);
			
			items.put("items" , gc.getItems());
			
			logger.debug(Utils.log(
					 "\n@@@@@@ items=",items
					,"\n@@@@@@ debugScreen @@@@@@"
					,"\n@@@@@@@@@@@@@@@@@@@@@@@@@"
					));
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		return items;
	}
	
	@Override
	public Map<String,Object> mesaControl(
			String cdsisrol
			,String agrupamc
			,String cdusuari
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ mesaControl @@@@@@"
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ agrupamc=" , agrupamc
				,"\n@@@@@@ cdusuari=" , cdusuari
				));
		Map<String,Object> result = new HashMap<String,Object>();
		String             paso   = null;
		try
		{
			paso = "Recuperando componentes de filtro";
			logger.debug(paso);
			
			List<ComponenteVO> filtro = pantallasDAO.obtenerComponentes(
					agrupamc //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MESA_CONTROL"//pantalla
					,"FILTRO"//seccion
					,null //orden
					);
			
			paso = "Recuperando componentes de grid";
			logger.debug(paso);
			
			List<ComponenteVO> grid = pantallasDAO.obtenerComponentes(
					agrupamc //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MESA_CONTROL"//pantalla
					,"GRID"//seccion
					,null //orden
					);
			
			List<ComponenteVO> form = pantallasDAO.obtenerComponentes(
					agrupamc //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MESA_CONTROL"//pantalla
					,"FORMULARIO"//seccion
					,null //orden
					);
			
			List<ComponenteVO> botones = pantallasDAO.obtenerComponentes(
					agrupamc //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"MESA_CONTROL"//pantalla
					,"BOTONES"//seccion
					,null //orden
					);
			
			paso = "Generando componentes";
			logger.debug(paso);
			
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			gc.generaComponentes(filtro, true, false, true, false, false, false);
			
			Map<String,Item> items = new HashMap<String,Item>();
			items.put("filtroItems" , gc.getItems());
			
			gc.generaComponentes(grid, true, true, false, true, false, false);
			
			items.put("gridFields"  , gc.getFields());
			items.put("gridColumns" , gc.getColumns());
			
			gc.generaComponentes(form, true, false, true, false, false, false);
			
			items.put("formItems" , gc.getItems());
			
			gc.generaComponentes(botones, true, false, false, false, false, true);
			
			items.put("botonesGrid" , gc.getButtons());
			
			Map<String,String> mapa = new HashMap<String,String>();
			if(cdsisrol.equals(RolSistema.AGENTE.getCdsisrol()))
			{
				mapa.put("CDAGENTE" , mesaControlDAO.cargarCdagentePorCdusuari(cdusuari));
			}
			
			result.put("items" , items);
			result.put("mapa"  , mapa);
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
				 "\n@@@@@@ result=",result
				,"\n@@@@@@ mesaControl @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return result;
	}
	
	@Override
	public Map<String,Object> recuperarTramites(
			String agrupamc
			,String status
			,String cdusuari
			,String cdsisrol
			,String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String cdagente
			,String ntramite
			,String fedesde
			,String fehasta
			,String cdpersonCliente
			,String filtro
			,int start
			,int limit
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarTramites @@@@@@"
				,"\n@@@@@@ agrupamc="         , agrupamc
				,"\n@@@@@@ status="           , status
				,"\n@@@@@@ cdusuari="         , cdusuari
				,"\n@@@@@@ cdsisrol="         , cdsisrol
				,"\n@@@@@@ cdunieco="         , cdunieco
				,"\n@@@@@@ cdramo="           , cdramo
				,"\n@@@@@@ cdtipsit="         , cdtipsit
				,"\n@@@@@@ estado="           , estado
				,"\n@@@@@@ nmpoliza="         , nmpoliza
				,"\n@@@@@@ cdagente="         , cdagente
				,"\n@@@@@@ ntramite="         , ntramite
				,"\n@@@@@@ fedesde="          , fedesde
				,"\n@@@@@@ fehasta="          , fehasta
				,"\n@@@@@@ cdpersonCliente="  , cdpersonCliente
				,"\n@@@@@@ filtro="           , filtro
				,"\n@@@@@@ start="            , start
				,"\n@@@@@@ limit="            , limit
				));
		String             paso   = null;
		Map<String,Object> result = null;
		try
		{
			paso = "Recuperando tr\u00e1mites";
			logger.debug(paso);
			
			result = flujoMesaControlDAO.recuperarTramites(
					agrupamc
					,status
					,cdusuari
					,cdsisrol
					,cdunieco
					,cdramo
					,cdtipsit
					,estado
					,nmpoliza
					,cdagente
					,ntramite
					,fedesde
					,fehasta
					,cdpersonCliente
					,start
					,limit
					);
			
			if(StringUtils.isNotBlank(filtro))
			{
				
				List<Map<String,String>> slist1 = (List<Map<String,String>>)result.get("lista");
				
				List<Map<String,String>> aux = new ArrayList<Map<String,String>>();
				
				for(Map<String,String>rec:slist1)
				{
					for(Entry<String,String>en:rec.entrySet())
					{
						String value = en.getValue();
						if(value==null)
						{
							value = "";
						}
						if(value.toUpperCase().indexOf(filtro.toUpperCase())!=-1)
						{
							aux.add(rec);
							break;
						}
					}
				}
				
				result.put("lista", aux);
			}
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}

		logger.debug(Utils.log(
				 "\n@@@@@@ result=" , result
				,"\n@@@@@@ recuperarTramites @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return result;
	}
	
	@Override
	public Map<String,String> recuperarPolizaUnica(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarPolizaUnica @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				));
		String             paso   = "Recuperando p\u00f3liza";
		Map<String,String> poliza = null;
		try
		{
			poliza = flujoMesaControlDAO.recuperarPolizaUnica(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}

		logger.debug(Utils.log(
				 "\n@@@@@@ recuperarPolizaUnica @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return poliza;
	}
	
	@Override
	public String registrarTramite(
			String cdunieco  , String cdramo   , String estado     , String nmpoliza
			,String nmsuplem , String cdsucadm , String cdsucdoc   , String cdtiptra
			,Date ferecepc   , String cdagente , String referencia , String nombre
			,Date festatus   , String status   , String comments   , String nmsolici
			,String cdtipsit , String cdusuari , String cdsisrol   , String swimpres
			,String cdtipflu , String cdflujomc
			,Map<String, String> valores
			,String cdtipsup, String cduniext, String ramo, String nmpoliex
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ registrarTramite @@@@@@"
				,"\n@@@@@@ cdunieco   = " , cdunieco
				,"\n@@@@@@ cdramo     = " , cdramo
				,"\n@@@@@@ estado     = " , estado
				,"\n@@@@@@ nmpoliza   = " , nmpoliza
				,"\n@@@@@@ nmsuplem   = " , nmsuplem
				,"\n@@@@@@ cdsucadm   = " , cdsucadm
				,"\n@@@@@@ cdsucdoc   = " , cdsucdoc
				,"\n@@@@@@ cdtiptra   = " , cdtiptra
				,"\n@@@@@@ ferecepc   = " , ferecepc
				,"\n@@@@@@ cdagente   = " , cdagente
				,"\n@@@@@@ referencia = " , referencia
				,"\n@@@@@@ nombre     = " , nombre
				,"\n@@@@@@ festatus   = " , festatus
				,"\n@@@@@@ status     = " , status
				,"\n@@@@@@ comments   = " , comments
				,"\n@@@@@@ nmsolici   = " , nmsolici
				,"\n@@@@@@ cdtipsit   = " , cdtipsit
				,"\n@@@@@@ cdusuari   = " , cdusuari
				,"\n@@@@@@ cdsisrol   = " , cdsisrol
				,"\n@@@@@@ swimpres   = " , swimpres
				,"\n@@@@@@ cdtipflu   = " , cdtipflu
				,"\n@@@@@@ cdflujomc  = " , cdflujomc
				,"\n@@@@@@ valores    = " , valores
				,"\n@@@@@@ cdtipsup   = " , cdtipsup
				,"\n@@@@@@ cduniext   = " , cduniext
				,"\n@@@@@@ ramo       = " , ramo
				,"\n@@@@@@ nmpoliex   = " , nmpoliex
				));
		
		String paso = "Registrando tr\u00e1mite";
		logger.debug(paso);
		String ntramite = null;
		try
		{
			String renuniext   = null
					,renramo   = null
					,renpoliex = null;
			
			if("21".equals(cdtiptra)) // si es una renovacion
			{
				logger.debug(Utils.log("Es una renovacion: ",renuniext,", ",renramo,", ",renpoliex));
				renuniext = cduniext;
				renramo   = ramo;
				renpoliex = nmpoliex;
			}
			
			ntramite = mesaControlDAO.movimientoMesaControl(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,cdsucadm
					,cdsucdoc
					,cdtiptra
					,ferecepc
					,cdagente
					,referencia
					,nombre
					,festatus
					,status
					,comments
					,nmsolici
					,cdtipsit
					,cdusuari
					,cdsisrol
					,swimpres
					,cdtipflu
					,cdflujomc
					,valores
					,cdtipsup
					,renuniext
					,renramo
					,renpoliex
					);
			
			mesaControlDAO.movimientoDetalleTramite(
					ntramite
					,new Date()
					,null//cdclausu
					,Utils.join("Se registra un nuevo tr\u00e1mite desde mesa de control con las siguientes observaciones: "
							,StringUtils.isBlank(comments) ? "(sin observaciones)" : comments
							)
					,cdusuari
					,null//cdmotivo
					,cdsisrol
					,"S"
					,null
					,null
					,status
					,false
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
				 "\n@@@@@@ ntramite=",ntramite
				,"\n@@@@@@ registrarTramite @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return ntramite;
	}
	
	@Override
	public List<Map<String,String>>cargarAccionesEntidad(
			String cdtipflu
			,String cdflujomc
			,String tipoent
			,String cdentidad
			,String webid
			,String cdsisrol
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ cargarAccionesEntidad @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ tipoent="   , tipoent
				,"\n@@@@@@ cdentidad=" , cdentidad
				,"\n@@@@@@ webid="     , webid
				,"\n@@@@@@ cdsisrol="  , cdsisrol
				));
		
		List<Map<String,String>> acciones = new ArrayList<Map<String,String>>();
		String                   paso     = null;
		
		try
		{
			paso = "Recuperando acciones de entidad";
			logger.debug(paso);
			List<Map<String,String>> tmp = flujoMesaControlDAO.cargarAccionesEntidad(
					cdtipflu
					,cdflujomc
					,tipoent
					,cdentidad
					,webid
					,cdsisrol
					);
			
			for(Map<String,String> accion : tmp)
			{
				if(StringUtils.isNotBlank(accion.get("CDESTADOMC")))
				{
					accion.put("TIPODEST"  , "E");
					accion.put("CLAVEDEST" , accion.get("CDESTADOMC"));
					accion.put("WEBIDDEST" , accion.get("WEBIDESTADO"));
				}
				else if(StringUtils.isNotBlank(accion.get("CDPANTMC")))
				{
					accion.put("TIPODEST"  , "P");
					accion.put("CLAVEDEST" , accion.get("CDPANTMC"));
					accion.put("WEBIDDEST" , accion.get("WEBIDPANT"));
				}
				else if(StringUtils.isNotBlank(accion.get("CDCOMPMC")))
				{
					accion.put("TIPODEST"  , "C");
					accion.put("CLAVEDEST" , accion.get("CDCOMPMC"));
					accion.put("WEBIDDEST" , accion.get("WEBIDCOMP"));
				}
				else if(StringUtils.isNotBlank(accion.get("CDPROCMC")))
				{
					accion.put("TIPODEST"  , "O");
					accion.put("CLAVEDEST" , accion.get("CDPROCMC"));
					accion.put("WEBIDDEST" , accion.get("WEBIDPROC"));
				}
				else if(StringUtils.isNotBlank(accion.get("CDVALIDA")))
				{
					accion.put("TIPODEST"  , "V");
					accion.put("CLAVEDEST" , accion.get("CDVALIDA"));
					accion.put("WEBIDDEST" , accion.get("WEBIDVALIDA"));
				}
				else if(StringUtils.isNotBlank(accion.get("CDREVISI")))
				{
					accion.put("TIPODEST"  , "R");
					accion.put("CLAVEDEST" , accion.get("CDREVISI"));
					accion.put("WEBIDDEST" , accion.get("WEBIDREVISI"));
				}
				else
				{
					throw new ApplicationException("Acci\u00f3n no conectada");
				}
				if(StringUtils.isBlank(accion.get("TIPODEST"))
						||StringUtils.isBlank(accion.get("CLAVEDEST"))
						||StringUtils.isBlank(accion.get("WEBIDDEST"))
						)
				{
					throw new ApplicationException("Acci\u00f2n mapeada con error");
				}
				acciones.add(accion);
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ cargarAccionesEntidad @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return acciones;
	}
	
	@Override
	public void procesoDemo(
			FlujoVO flujo
			,String cdusuari
			,String cdsisrol
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ procesoDemo @@@@@@"
				,"\n@@@@@@ flujo="    , flujo
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				));

		String paso = "Cambiando fecha";
		logger.debug(paso);
		
		try
		{
			mesaControlDAO.movimientoDetalleTramite(
					flujo.getNtramite()
					,new Date()//feinicio
					,null//cdclausu
					,Utils.join("Se agrega nuevo detalle ",Utils.format(new Date()))
					,cdusuari
					,null//cdmotivo
					,cdsisrol
					,"S"
					,null
					,null
					,"-1"
					,false
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ procesoDemo @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public List<Map<String,String>> ejecutaRevision(FlujoVO flujo)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ ejecutaRevision @@@@@@"
				,"\n@@@@@@ flujo=",flujo
				));
		
		List<Map<String,String>> docsFaltan = null;
		String                   paso       = null;
		
		try
		{
			paso = "Recuperando lista de documentos faltantes";
			logger.debug(paso);
			
			docsFaltan = flujoMesaControlDAO.recuperarDocumentosRevisionFaltantes(
					flujo.getCdtipflu()
					,flujo.getCdflujomc()
					,flujo.getClaveent()
					,flujo.getNtramite()
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ docsFaltan=",docsFaltan
				,"\n@@@@@@ ejecutaRevision @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		
		return docsFaltan;
	}
	
	@Override
	public String turnarTramite(
			 String ntramite
			,String statusOld
			,String cdtipasigOld
			,String statusNew
			,String cdtipasigNew
			,String cdusuariSes
			,String cdsisrolSes
			,String comments
			,boolean cerrado
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ turnarTramite @@@@@@"
				,"\n@@@@@@ ntramite="     , ntramite
				,"\n@@@@@@ statusOld="    , statusOld
				,"\n@@@@@@ cdtipasigOld=" , cdtipasigOld
				,"\n@@@@@@ statusNew="    , statusNew
				,"\n@@@@@@ cdtipasigNew=" , cdtipasigNew
				,"\n@@@@@@ cdusuariSes="  , cdusuariSes
				,"\n@@@@@@ cdsisrolSes="  , cdsisrolSes
				,"\n@@@@@@ comments="     , comments
				,"\n@@@@@@ cerrado="      , cerrado
				));
		String paso     = "Iniciando turnado"
		       ,message = null;
		try
		{
			Map<String,String> usuarioDestino = new HashMap<String,String>();
			Date               fecstatu       = new Date();
			
			boolean destinoSimple = true;
			
			if(!"1".equals(cdtipasigNew)) //es por carrusel o por carga
			{
				destinoSimple = false;
				
				paso = "Recuperando datos del tr\u00e1mite";
				logger.debug(paso);
				Map<String,Object> datosTramite = flujoMesaControlDAO.recuperarDatosTramiteValidacionCliente(
						null//cdtipflu
						,null//cdflujomc
						,null//tipoent
						,null//claveent
						,null//webid
						,ntramite
						,null//status
						,null//cdunieco
						,null//cdramo
						,null//estado
						,null//nmpoliza
						,null//nmsituac
						,null//nmsuplem
						);
				Map<String,String> tramite = (Map<String,String>)datosTramite.get("TRAMITE");
				String cdtipflu  = tramite.get("CDTIPFLU");
				String cdflujomc = tramite.get("CDFLUJOMC");
				
				paso = "Recuperando rol destino";
				logger.debug(paso);
				
				List<Map<String,String>> roles         = flujoMesaControlDAO.recuperaTfluestrol(cdtipflu, cdflujomc, statusNew);
				logger.debug(Utils.log("\nroles=",roles));
				String                   cdsisrolTurnado = null;
				for(Map<String,String>rol:roles)
				{
					if("S".equals(rol.get("SWTRABAJO")))
					{
						cdsisrolTurnado = rol.get("CDSISROL");
						break;
					}
				}
				if(StringUtils.isBlank(cdsisrolTurnado))
				{
					throw new ApplicationException("No hay rol definido para ver el status nuevo");
				}
				logger.debug(Utils.log("\ncdsisrolNuevo=",cdsisrolTurnado));
				
				String cadenaTipoTurnado = null;
				if("3".equals(cdtipasigNew))
				{
					cadenaTipoTurnado = "CARRUSEL";
				}
				else if("4".equals(cdtipasigNew))
				{
					cadenaTipoTurnado = "TAREAS";
				}
				else
				{
					throw new ApplicationException("El tipo de asignaci\u00f3n no es correcto");
				}
				
				paso = "Verificando si lo tenia ese rol anteriormente";
				logger.debug(paso);
				
				Map<String,String> usuarioMismoRolAnteriormente =
						flujoMesaControlDAO.recuperarUsuarioHistoricoTramitePorRol(ntramite,cdsisrolTurnado);
				
				if(StringUtils.isNotBlank(usuarioMismoRolAnteriormente.get("cdusuari"))
						&&StringUtils.isNotBlank(usuarioMismoRolAnteriormente.get("dsusuari"))
						)
				{
					logger.debug(Utils.log("si lo tenia antes alguien del mismo rol <",cdsisrolTurnado,"> era <",usuarioMismoRolAnteriormente.get("dsusuari"),">"));
					usuarioDestino = usuarioMismoRolAnteriormente;
					usuarioDestino.put("cdsisrol" , cdsisrolTurnado);
				}
				else
				{
					paso = "Recuperando usuario destino del tr\u00e1mite";
					logger.debug(Utils.log("\n",paso,", tipo=",cadenaTipoTurnado));
					usuarioDestino = flujoMesaControlDAO.recuperarUsuarioParaTurnado(cdsisrolTurnado,cadenaTipoTurnado);
					usuarioDestino.put("cdsisrol" , cdsisrolTurnado);
					logger.debug(Utils.log("\nusuario destino=",usuarioDestino));
				}
				
				paso = "Guardando historico de tramite";
				logger.debug(paso);
				
				flujoMesaControlDAO.guardarHistoricoTramite(new Date(),ntramite,usuarioDestino.get("cdusuari"),cdsisrolTurnado,statusOld);
			}
			
			if(!"1".equals(cdtipasigOld)) //antes lo tenia un usuario especifico
			{
				Map<String,Object> datos = flujoMesaControlDAO.recuperarDatosTramiteValidacionCliente(
						null//cdtipflu
						,null//cdflujomc
						,null//tipoent
						,null//claveent
						,null//webid
						,ntramite
						,null//status
						,null//cdunieco
						,null//cdramo
						,null//estado
						,null//nmpoliza
						,null//nmsituac
						,null//nmsuplem
						);
				Map<String,String> tramite = (Map<String,String>)datos.get("TRAMITE");
				
				String cdusuariActual  = tramite.get("CDUSUARI")
				       ,cdsisrolActual = cdsisrolSes;
				
				if(!cdusuariSes.equals(cdusuariActual))
				{
					/*
					 * el usuario de sesion no es el dueño del tramite,
					 * hay que recuperar al dueño para descontarselo.
					 * Ya sabemos de quien es por TMESACONTROL.CDUSUARI
					 * pero no sabemos el rol, lo necesitamos para descontarlo en la lista,
					 * porque la lista viene en clave doble CDUSUARI-CDSISROL
					 */
					cdsisrolActual = flujoMesaControlDAO.recuperarRolRecienteTramite(ntramite,cdusuariActual);
				}
				
				flujoMesaControlDAO.restarTramiteUsuario(cdusuariActual,cdsisrolActual);
			}
			
			paso = "Actualizando status";
			logger.debug(paso);
			
			flujoMesaControlDAO.actualizarStatusTramite(
					ntramite
					,statusNew
					,fecstatu
					,usuarioDestino.get("cdusuari")
					);
			
			paso = "Actualizando recuperando descripci\u00f3n de status";
			logger.debug(paso);
			
			List<Map<String,String>>estados = flujoMesaControlDAO.recuperaTestadomc(statusNew);
			if(estados==null||estados.size()==0)
			{
				throw new ApplicationException("No se encuentra la descripci\u00f3n del status");
			}
			if(estados.size()>1)
			{
				throw new ApplicationException("Descripci\u00f3n del status duplicada");
			}
			
			paso = "Guardando detalle de tr\u00e1mite";
			logger.debug(paso);
			
			mesaControlDAO.movimientoDetalleTramite(
					ntramite
					,new Date()//feinicio
					,null//cdclausu
					,Utils.join("Tr\u00e1mite turnado a status \"",estados.get(0).get("DSESTADOMC"),"\": ",Utils.NVL(comments,"(sin comentarios)"))
					,cdusuariSes
					,null//cdmotivo
					,cdsisrolSes
					,"S"
					,usuarioDestino.get("cdusuari")
					,usuarioDestino.get("cdsisrol")
					,statusNew
					,cerrado
					);
			
			if(destinoSimple)
			{
				message = "El tr\u00e1mite ha sido turnado con \u00e9xito";
			}
			else
			{
				message = Utils.join("El tr\u00e1mite ha sido turnado a ",usuarioDestino.get("dsusuari"));
			}
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
				 "\n@@@@@@ message=",message
				,"\n@@@@@@ turnarTramite @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return message;
	}
	
	@Override
	public Map<String,Object> recuperarDatosTramiteValidacionCliente(FlujoVO flujo)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarDatosTramiteValidacionCliente @@@@@@"
				,"\n@@@@@@ flujo=",flujo
				));
		String paso = "Recuperando datos para validaci\u00f3n cliente";
		Map<String,Object> datos = null;
		try
		{
			datos = flujoMesaControlDAO.recuperarDatosTramiteValidacionCliente(
					flujo.getCdtipflu()
					,flujo.getCdflujomc()
					,flujo.getTipoent()
					,flujo.getClaveent()
					,flujo.getWebid()
					,flujo.getNtramite()
					,flujo.getStatus()
					,flujo.getCdunieco()
					,flujo.getCdramo()
					,flujo.getEstado()
					,flujo.getNmpoliza()
					,flujo.getNmsituac()
					,flujo.getNmsuplem()
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
				 "\n@@@@@@ datos=",datos
				,"\n@@@@@@ recuperarDatosTramiteValidacionCliente @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return datos;
	}
	
	@Override
	public String turnarDesdeComp(
			String cdusuari
			,String cdsisrol
			,String cdtipflu
			,String cdflujomc
			,String ntramite
			,String statusOld
			,String statusNew
			,String swagente
			,String comments
			,boolean cerrado
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ turnarDesdeComp @@@@@@"
				,"\n@@@@@@ cdusuari="  , cdusuari
				,"\n@@@@@@ cdsisrol="  , cdsisrol
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ ntramite="  , ntramite
				,"\n@@@@@@ statusOld=" , statusOld
				,"\n@@@@@@ statusNew=" , statusNew
				,"\n@@@@@@ swagente="  , swagente
				,"\n@@@@@@ comments="  , comments
				,"\n@@@@@@ cerrado="   , cerrado
				));
		
		String message = null
		       ,paso   = null;
		
		try
		{
			paso = "Recuperando status anterior";
			logger.debug(paso);
			
			List<Map<String,String>>statusesOld = flujoMesaControlDAO.recuperaTfluest(cdtipflu, cdflujomc, statusOld);
			if(statusesOld.size()==0)
			{
				throw new ApplicationException("Status anterior no existe");
			}
			if(statusesOld.size()>1)
			{
				throw new ApplicationException("Status anterior repetido");
			}
			Map<String,String> statusAnterior = statusesOld.get(0);
			logger.debug(Utils.log("=",statusAnterior));
			
			paso = "Recuperando status nuevo";
			logger.debug(paso);
			
			List<Map<String,String>>statusesNew = flujoMesaControlDAO.recuperaTfluest(cdtipflu, cdflujomc, statusNew);
			if(statusesNew.size()==0)
			{
				throw new ApplicationException("Status nuevo no existe");
			}
			if(statusesNew.size()>1)
			{
				throw new ApplicationException("Status nuevo repetido");
			}
			Map<String,String> statusNuevo = statusesNew.get(0);
			logger.debug(Utils.log("=",statusNuevo));
			
			paso = "Invocando turnado general";
			logger.debug(paso);
			
			message = this.turnarTramite(
					ntramite
					,statusOld
					,statusAnterior.get("CDTIPASIG")
					,statusNew
					,statusNuevo.get("CDTIPASIG")
					,cdusuari
					,cdsisrol
					,comments
					,cerrado
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ message=",message
				,"\n@@@@@@ turnarDesdeComp @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return message;
	}
	
	@Override
	public void recuperarPropiedadesDePantallaComponenteActualPorConexionSinPermisos(FlujoVO flujo) throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarPropiedadesDePantallaComponenteActualPorConexionSinPermisos @@@@@@"
				,"\n@@@@@@ flujo=", flujo
				));
		
		String paso = "Recuperando propiedades actuales desde componente anterior";
		
		try
		{
			Map<String,String> conexionFantasma = flujoMesaControlDAO.recuperarPropiedadesDePantallaComponenteActualPorConexionSinPermisos(
					flujo.getCdtipflu()
					,flujo.getCdflujomc()
					,flujo.getTipoent()
					,flujo.getClaveent()
					,flujo.getWebid()
					);
			
			logger.debug("Datos de pantalla/componente actual recuperados: {}",conexionFantasma);
			
			flujo.setTipoent(conexionFantasma.get("TIPOENT"));
			flujo.setClaveent(conexionFantasma.get("CDENTIDAD"));
			flujo.setWebid(conexionFantasma.get("WEBID"));
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ flujo=", flujo
				,"\n@@@@@@ recuperarPropiedadesDePantallaComponenteActualPorConexionSinPermisos @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public Map<String,String> recuperarPolizaUnicaDanios(
			String cduniext
			,String ramo
			,String nmpoliex
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarPolizaUnicaDanios @@@@@@"
				,"\n@@@@@@ cduniext = " , cduniext
				,"\n@@@@@@ ramo     = " , ramo
				,"\n@@@@@@ nmpoliex = " , nmpoliex
				));
		String             paso   = "Recuperando p\u00f3liza";
		Map<String,String> poliza = null;
		try
		{
			poliza = flujoMesaControlDAO.recuperarPolizaUnicaDanios(
					cduniext
					,ramo
					,nmpoliex
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}

		logger.debug(Utils.log(
				 "\n@@@@@@ recuperarPolizaUnicaDanios @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return poliza;
	}
	
	@Override
	public void guardarTtipflurol(String cdtipflu, List<Map<String,String>> lista) throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarTtipflurol @@@@@@"
				,"\n@@@@@@ cdtipflu = " , cdtipflu
				,"\n@@@@@@ lista    = " , lista
				));
		
		String paso = "Guardando permisos de tr\u00e1mite";
		
		try
		{
			flujoMesaControlDAO.guardarTtipflurol(cdtipflu,lista);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ guardarTtipflurol @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void guardarTflujorol(
			String cdtipflu
			,String cdflujomc
			,List<Map<String,String>> lista
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarTflujorol @@@@@@"
				,"\n@@@@@@ cdtipflu  = " , cdtipflu
				,"\n@@@@@@ cdflujomc = " , cdflujomc
				,"\n@@@@@@ lista     = " , lista
				));
		
		String paso = "Guardando permisos de proceso";
		
		try
		{
			flujoMesaControlDAO.guardarTflujorol(cdtipflu,cdflujomc,lista);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ guardarTflujorol @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
}