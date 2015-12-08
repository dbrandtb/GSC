package mx.com.gseguros.mesacontrol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.mesacontrol.dao.FlujoMesaControlDAO;
import mx.com.gseguros.mesacontrol.service.FlujoMesaControlManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.utils.Utils;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlujoMesaControlManagerImpl implements FlujoMesaControlManager {
	
	@Autowired
	private FlujoMesaControlDAO flujoMesaControlDAO;
	
	@Autowired
	private PantallasDAO pantallasDAO;
	
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
			paso = "Recuperando componentes";
			sb.append("\n").append(paso);
			
			GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			List<ComponenteVO> ttipfluCmp = pantallasDAO.obtenerComponentes(null, null, null, null, null, cdsisrol, "FLUJOMC", "TTIPFLU", null);
			gc.generaComponentes(ttipfluCmp, true, false, true, false, false, false);
			
			items.put("ttipfluFormItems" , gc.getItems());
			
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
	
	@Override
	public void movimientoTtipflumc(
			StringBuilder sb
			,String accion
			,String cdtipflu
			,String dstipflu
			,String cdtiptra
			,String swreqpol
			,String swmultipol
			)throws Exception
	{
		sb.append(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ movimientoTtipflumc @@@@@@"
				,"\n@@@@@@ accion="     , accion
				,"\n@@@@@@ cdtipflu="   , cdtipflu
				,"\n@@@@@@ dstipflu="   , dstipflu
				,"\n@@@@@@ cdtiptra="   , cdtiptra
				,"\n@@@@@@ swreqpol="   , swreqpol
				,"\n@@@@@@ swmultipol=" , swmultipol
				));
		
		String paso = "Guardando tr\u00E1mite";
		sb.append("\n").append(paso);
		
		try
		{
			flujoMesaControlDAO.movimientoTtipflumc(
					cdtipflu
					,dstipflu
					,cdtiptra
					,"S".equals(swmultipol) ? "S" : "N"
					,"S".equals(swreqpol) ? "S" : "N"
					,accion
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		sb.append(Utils.log(
				 "\n@@@@@@ movimientoTtipflumc @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void movimientoTflujomc(
			StringBuilder sb
			,String accion
			,String cdtipflu
			,String cdflujomc
			,String dsflujomc
			,String swfinal
			)throws Exception
	{
		sb.append(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ movimientoTflujomc @@@@@@"
				,"\n@@@@@@ accion="     , accion
				,"\n@@@@@@ cdtipflu="   , cdtipflu
				,"\n@@@@@@ cdflujomc="  , cdflujomc
				,"\n@@@@@@ dsflujomc="  , dsflujomc
				,"\n@@@@@@ swfinal="    , swfinal
				));
		
		String paso = "Guardando proceso";
		sb.append("\n").append(paso);
		
		try
		{
			flujoMesaControlDAO.movimientoTflujomc(
					cdtipflu
					,cdflujomc
					,dsflujomc
					,"S".equals(swfinal) ? "S" : "N"
					,accion
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ movimientoTflujomc @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void movimientoCatalogo(
			StringBuilder sb
			,String accion
			,String tipo
			,Map<String,String> params
			)throws Exception
	{
		sb.append(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ movimientoCatalogo @@@@@@"
				,"\n@@@@@@ accion=" , accion
				,"\n@@@@@@ tipo="   , tipo
				,"\n@@@@@@ params=" , params
				));
		
		String paso = "Guardando cat\u00e1logo";
		sb.append("\n").append(paso);
		
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
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ movimientoCatalogo @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void registrarEntidad(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String tipo
			,String clave
			,String webid
			,String xpos
			,String ypos
			)throws Exception
	{
		sb.append(Utils.log(
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
		
		String paso = "Registrando entidad";
		sb.append("\n").append(paso);
		
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
						,null //swescala
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
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ registrarEntidad @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void borrarEntidad(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String tipo
			,String clave
			,String webid
			)throws Exception
	{
		sb.append(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ borrarEntidad @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ tipo="      , tipo
				,"\n@@@@@@ clave="     , clave
				,"\n@@@@@@ webid="     , cdtipflu
				));
		
		String paso = "Borrando entidad";
		sb.append("\n").append(paso);
		
		try
		{
			if("E".equals(tipo))
			{
				flujoMesaControlDAO.movimientoTfluest(
						cdtipflu
						,cdflujomc
						,clave //cdestadomc
						,null //webid
						,null //xpos
						,null //ypos
						,null //timemax
						,null //timewrn1
						,null //timewrn2
						,null //swescala
						,null //cdtipasig
						,"D"
						);
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ borrarEntidad @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public List<Map<String,String>> cargarModelado(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			)throws Exception
	{
		sb.append(Utils.log(
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
			sb.append("\n").append(paso);
			
			List<Map<String,String>> estados = flujoMesaControlDAO.recuperaTfluest(cdtipflu, cdflujomc);
			
			if(estados.size()>0)
			{
				for(Map<String,String>estado:estados)
				{
					estado.put("TIPO" , "E");
					list.add(estado);
				}
				
				paso = "Recuperando pantallas";
				sb.append("\n").append(paso);
				
				List<Map<String,String>> pantallas = flujoMesaControlDAO.recuperaTflupant(
						cdtipflu
						,cdflujomc);
				
				for(Map<String,String>pantalla:pantallas)
				{
					pantalla.put("TIPO" , "P");
					list.add(pantalla);
				}
				
				paso = "Recuperando componentes";
				sb.append("\n").append(paso);
				
				List<Map<String,String>> componentes = flujoMesaControlDAO.recuperaTflucomp(
						cdtipflu
						,cdflujomc);
				
				for(Map<String,String>componente:componentes)
				{
					componente.put("TIPO" , "C");
					list.add(componente);
				}
			}
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ list=" , list
				,"\n@@@@@@ cargarModelado @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return list;
	}
	
	@Override
	public Map<String,Object> cargarDatosEstado(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdestadomc
			)throws Exception
	{
		sb.append(Utils.log(
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
			sb.append("\n").append(paso);
			
			List<Map<String,String>> lista  = flujoMesaControlDAO.recuperaTfluest(cdtipflu, cdflujomc);
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
			sb.append("\n").append(paso);
			
			List<Map<String,String>> permisos = flujoMesaControlDAO.recuperaTfluestrol(cdtipflu, cdflujomc, estado.get("CDESTADOMC"));
			
			paso = "Recuperando avisos";
			sb.append("\n").append(paso);
			
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
			
			sb.append(Utils.log(
					 "\nmapa="  , estado
					,"\nlista=" , listaProps
					));
			
			result.put("mapa"  , estado);
			result.put("lista" , listaProps);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ cargarDatosEstado @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return result;
	}
	
	@Override
	public void guardarDatosEstado(
			StringBuilder sb
			,String cdtipflu
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
		sb.append(Utils.log(
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
			sb.append("\n").append(paso);
			
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
					,"S".equals(swescala) ? "S" : "N"
					,cdtipasig
					,accion
					);
			
			paso = "Guardando permisos y avisos";
			sb.append("\n").append(paso);
			
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
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ guardarDatosEstado @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
}