package mx.com.gseguros.mesacontrol.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlujoMesaControlManagerImpl implements FlujoMesaControlManager {
	
	@Autowired
	private FlujoMesaControlDAO flujoMesaControlDAO;
	
	@Autowired
	private PantallasDAO pantallasDAO;
	
	@Autowired
	private MesaControlDAO mesaControlDAO;
	
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
			
			List<ComponenteVO> tdocumeCmp = pantallasDAO.obtenerComponentes(null, null, null, null, null, cdsisrol, "FLUJOMC", "TDOCUME", null);
			gc.generaComponentes(tdocumeCmp, true, false, true, false, false, false);
			
			items.put("tdocumeFormItems" , gc.getItems());
			
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
			,String cdtipsup
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
				,"\n@@@@@@ cdtipsup="   , cdtipsup
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
					,cdtipsup
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
	public String registrarEntidad(
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
		
		String cdentidad = null
		       ,paso     = "Registrando entidad";
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
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ cdentidad=",cdentidad
				,"\n@@@@@@ registrarEntidad @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return cdentidad;
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
				,"\n@@@@@@ webid="     , webid
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
						,webid
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
				
				List<Map<String,String>> procesos = flujoMesaControlDAO.recuperaTfluproc(cdtipflu, cdflujomc);
				
				for(Map<String,String>proceso:procesos)
				{
					proceso.put("TIPO" , "O");
					list.add(proceso);
				}
				
				List<Map<String,String>> validaciones = flujoMesaControlDAO.recuperaTfluval(cdtipflu, cdflujomc);
				
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
	
	@Override
	public String registrarConnection(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String idorigen
			,String iddestin
			)throws Exception
	{
		sb.append(Utils.log(
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
			sb.append("\n").append(paso);
			
			cdaccion = flujoMesaControlDAO.movimientoTfluacc(
					cdtipflu
					,cdflujomc
					,null //cdaccion
					,""   //dsaccion
					,""   //cdicono
					,""   //cdvalor
					,idorigen
					,iddestin
					,"I"
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ cdaccion=",cdaccion
				,"\n@@@@@@ registrarConnection @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return cdaccion;
	}
	
	@Override
	public Map<String,String> cargarDatosValidacion(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdvalida
			)throws Exception
	{
		sb.append(Utils.log(
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
			sb.append("\n").append(paso);
			
			List<Map<String,String>> validaciones = flujoMesaControlDAO.recuperaTfluval(cdtipflu, cdflujomc);
			
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
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ cargarDatosValidacion @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return validacion;
	}
	
	@Override
	public void guardarDatosValidacion(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdvalida
			,String webid
			,String xpos
			,String ypos
			,String dsvalida
			,String cdvalidafk
			,String accion
			)throws Exception
	{
		sb.append(Utils.log(
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
				,"\n@@@@@@ accion="     , accion
				));
		
		String paso = "Guardando datos de validaci\u00f3n";
		sb.append("\n").append(paso);
		
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
					,accion
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ guardarDatosValidacion @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void guardarCoordenadas(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,List<Map<String,String>>list
			)throws Exception
	{
		sb.append(Utils.log(
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
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ guardarCoordenadas @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public String ejecutaValidacion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdvalidafk
			)throws Exception
	{
		return flujoMesaControlDAO.ejecutaValidacion(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nmsituac
				,nmsuplem
				,cdvalidafk
				);
	}
	
	@Override
	public Map<String,Object> cargarDatosRevision(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdrevisi
			)throws Exception
	{
		sb.append(Utils.log(
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
			sb.append("\n").append(paso);
			
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
			sb.append("\n").append(paso);
			
			List<Map<String,String>> permisos = flujoMesaControlDAO.recuperaTflurevdoc(cdtipflu, cdflujomc, cdrevisi);
			
			sb.append(Utils.log(
					 "\nmapa="  , revision
					,"\nlista=" , permisos
					));
			
			result.put("mapa"  , revision);
			result.put("lista" , permisos);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ cargarDatosRevision @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return result;
	}
	
	@Override
	public void guardarDatosRevision(
			StringBuilder sb
			,String cdtipflu
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
		sb.append(Utils.log(
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
			sb.append("\n").append(paso);
			
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
			sb.append("\n").append(paso);
			
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
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ guardarDatosRevision @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void movimientoTdocume(
			StringBuilder sb
			,String accion
			,String cddocume
			,String dsdocume
			,String cdtiptra
			)throws Exception
	{
		sb.append(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ movimientoTdocume @@@@@@"
				,"\n@@@@@@ accion="   , accion
				,"\n@@@@@@ dsdocume=" , dsdocume
				,"\n@@@@@@ dsdocume=" , dsdocume
				,"\n@@@@@@ cdtiptra=" , cdtiptra
				));
		
		String paso = "Guardando documento";
		sb.append("\n").append(paso);
		
		try
		{
			flujoMesaControlDAO.movimientoTdocume(cddocume, dsdocume, cdtiptra, accion);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		sb.append(Utils.log(
				 "\n@@@@@@ movimientoTdocume @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public void borrarConnection(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdaccion
			)throws Exception
	{
		sb.append(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ borrarConnection @@@@@@"
				,"\n@@@@@@ cdtipflu="  , cdtipflu
				,"\n@@@@@@ cdflujomc=" , cdflujomc
				,"\n@@@@@@ cdaccion="  , cdaccion
				));
		
		String paso = "Borrando acci\u00f3n";
		sb.append("\n").append(paso);
		
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
					,"D" //accion
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ borrarConnection @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public Map<String,Object> cargarDatosAccion(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdaccion
			)throws Exception
	{
		sb.append(Utils.log(
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
			sb.append("\n").append(paso);
			
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
			sb.append("\n").append(paso);
			
			List<Map<String,String>> permisos = flujoMesaControlDAO.recuperaTfluaccrol(cdtipflu, cdflujomc, cdaccion);
			
			sb.append(Utils.log(
					 "\nmapa="  , accion
					,"\nlista=" , permisos
					));
			
			result.put("mapa"  , accion);
			result.put("lista" , permisos);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ cargarDatosAccion @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return result;
	}
	
	@Override
	public void guardarDatosAccion(
			StringBuilder sb
			,String cdtipflu
			,String cdflujomc
			,String cdaccion
			,String dsaccion
			,String accion
			,String idorigen
			,String iddestin
			,String cdvalor
			,String cdicono
			,List<Map<String,String>>list
			)throws Exception
	{
		sb.append(Utils.log(
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
				,"\n@@@@@@ list="      , list
				));
		
		String paso = null;
		
		try
		{
			paso = "Guardando datos de acci\u00f3n";
			sb.append("\n").append(paso);
			
			flujoMesaControlDAO.movimientoTfluacc(
					cdtipflu
					,cdflujomc
					,cdaccion
					,dsaccion
					,cdicono
					,cdvalor
					,idorigen
					,iddestin
					,accion
					);
			
			paso = "Guardando permisos";
			sb.append("\n").append(paso);
			
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
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
				 "\n@@@@@@ guardarDatosAccion @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public Map<String,Item> debugScreen(StringBuilder sb) throws Exception
	{
		sb.append(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ debugScreen @@@@@@"
				));
		String           paso  = null;
		Map<String,Item> items = new HashMap<String,Item>();
		try
		{
			paso = "Recuperando componentes";
			sb.append("\n").append(paso);
			
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
			
			sb.append(Utils.log(
					 "\n@@@@@@ items=",items
					,"\n@@@@@@ debugScreen @@@@@@"
					,"\n@@@@@@@@@@@@@@@@@@@@@@@@@"
					));
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		return items;
	}
	
	@Override
	public Map<String,Object> mesaControl(
			StringBuilder sb
			,String cdsisrol
			,String agrupamc
			,String cdusuari
			)throws Exception
	{
		sb.append(Utils.log(
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
			sb.append("\n").append(paso);
			
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
			sb.append("\n").append(paso);
			
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
			
			paso = "Generando componentes";
			sb.append("\n").append(paso);
			
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			gc.generaComponentes(filtro, true, false, true, false, false, false);
			
			Map<String,Item> items = new HashMap<String,Item>();
			items.put("filtroItems" , gc.getItems());
			
			gc.generaComponentes(grid, true, true, false, true, false, false);
			
			items.put("gridFields"  , gc.getFields());
			items.put("gridColumns" , gc.getColumns());
			
			gc.generaComponentes(form, true, false, true, false, false, false);
			
			items.put("formItems" , gc.getItems());
			
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
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		sb.append(Utils.log(
				 "\n@@@@@@ result=",result
				,"\n@@@@@@ mesaControl @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return result;
	}
	
	@Override
	public Map<String,Object> recuperarTramites(
			StringBuilder sb
			,String agrupamc
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
			,int start
			,int limit
			)throws Exception
	{
		sb.append(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ recuperarTramites @@@@@@"
				,"\n@@@@@@ agrupamc=" , agrupamc
				,"\n@@@@@@ status="   , status
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ cdtipsit=" , cdtipsit
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ cdagente=" , cdagente
				,"\n@@@@@@ ntramite=" , ntramite
				,"\n@@@@@@ fedesde="  , fedesde
				,"\n@@@@@@ fehasta="  , fehasta
				,"\n@@@@@@ start="    , start
				,"\n@@@@@@ limit="    , limit
				));
		String             paso   = null;
		Map<String,Object> result = null;
		try
		{
			paso = "Recuperando tr\u00e1mites";
			sb.append("\n").append(paso);
			
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
					,start
					,limit
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}

		sb.append(Utils.log(
				 "\n@@@@@@ result=" , result
				,"\n@@@@@@ recuperarTramites @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return result;
	}
	
	@Override
	public Map<String,String> recuperarPolizaUnica(
			StringBuilder sb
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			)throws Exception
	{
		sb.append(Utils.log(
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
			Utils.generaExcepcion(ex, paso, sb.toString());
		}

		sb.append(Utils.log(
				 "\n@@@@@@ recuperarPolizaUnica @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return poliza;
	}
	
	@Override
	public String registrarTramite(
			StringBuilder sb , String cdunieco , String cdramo     , String estado   , String nmpoliza
			,String nmsuplem , String cdsucadm , String cdsucdoc   , String cdtiptra
			,Date ferecepc   , String cdagente , String referencia , String nombre
			,Date festatus   , String status   , String comments   , String nmsolici
			,String cdtipsit , String cdusuari , String cdsisrol   , String swimpres
			,String cdtipflu , String cdflujomc
			,Map<String, String> valores, String cdtipsup
			)throws Exception
	{
		sb.append(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ registrarTramite @@@@@@"
				,"\n@@@@@@ cdunieco="   , cdunieco
				,"\n@@@@@@ cdramo="     , cdramo
				,"\n@@@@@@ estado="     , estado
				,"\n@@@@@@ nmpoliza="   , nmpoliza
				,"\n@@@@@@ nmsuplem="   , nmsuplem
				,"\n@@@@@@ cdsucadm="   , cdsucadm
				,"\n@@@@@@ cdsucdoc="   , cdsucdoc
				,"\n@@@@@@ cdtiptra="   , cdtiptra
				,"\n@@@@@@ ferecepc="   , ferecepc
				,"\n@@@@@@ cdagente="   , cdagente
				,"\n@@@@@@ referencia=" , referencia
				,"\n@@@@@@ nombre="     , nombre
				,"\n@@@@@@ festatus="   , festatus
				,"\n@@@@@@ status="     , status
				,"\n@@@@@@ comments="   , comments
				,"\n@@@@@@ nmsolici="   , nmsolici
				,"\n@@@@@@ cdtipsit="   , cdtipsit
				,"\n@@@@@@ cdusuari="   , cdusuari
				,"\n@@@@@@ cdsisrol="   , cdsisrol
				,"\n@@@@@@ swimpres="   , swimpres
				,"\n@@@@@@ cdtipflu="   , cdtipflu
				,"\n@@@@@@ cdflujomc="  , cdflujomc
				,"\n@@@@@@ valores="    , valores
				,"\n@@@@@@ cdtipsup="   , cdtipsup
				));
		
		String paso = "Registrando tr\u00e1mite";
		sb.append("\n").append(paso);
		String ntramite = null;
		try
		{		
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
					);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		sb.append(Utils.log(
				 "\n@@@@@@ ntramite=",ntramite
				,"\n@@@@@@ registrarTramite @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return ntramite;
	}
}