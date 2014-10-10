package mx.com.gseguros.portal.cotizacion.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.catalogos.dao.PersonasDAO;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.cotizacion.model.ParametroCotizacion;
import mx.com.gseguros.portal.cotizacion.service.CotizacionManager;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.FTPSUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

public class CotizacionManagerImpl implements CotizacionManager 
{

	private static final Logger           logger       = Logger.getLogger(CotizacionManagerImpl.class);
	private static final SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	
	private CotizacionDAO  cotizacionDAO;
	private PantallasDAO   pantallasDAO;
	private PersonasDAO    personasDAO;
	private MesaControlDAO mesaControlDAO;
	
	@Override
	public void movimientoTvalogarGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdgrupo
			,String cdgarant
			,String status
			,String cdatribu
			,String valor)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ movimientoTvalogarGrupo @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ cdgrupo=") .append(cdgrupo)
				.append("\n@@@@@@ cdgarant=").append(cdgarant)
				.append("\n@@@@@@ status=")  .append(status)
				.append("\n@@@@@@ cdatribu=").append(cdatribu)
				.append("\n@@@@@@ valor=")   .append(valor)
				.toString()
				);
		cotizacionDAO.movimientoTvalogarGrupo(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nmsuplem
				,cdtipsit
				,cdgrupo
				,cdgarant
				,status
				,cdatribu
				,valor
				);
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ movimientoTvalogarGrupo @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
	}
	
	@Override
	public void movimientoMpolisitTvalositGrupo(
			String  cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo
			,String otvalor06
			,String otvalor07
			,String otvalor08
			,String otvalor09
			,String otvalor10
			,String otvalor11
			,String otvalor12
			,String otvalor13
			,String otvalor16)throws Exception
	{
		logger.info(""
				+ "\n#############################################"
				+ "\n###### movimientoMpolisitTvalositGrupo ######"
				+ "\ncdunieco "+cdunieco
				+ "\ncdramo "+cdramo
				+ "\nestado "+estado
				+ "\nnmpoliza "+nmpoliza
				+ "\ncdgrupo "+cdgrupo
				+ "\notvalor06 "+otvalor06
				+ "\notvalor07 "+otvalor07
				+ "\notvalor08 "+otvalor08
				+ "\notvalor09 "+otvalor09
				+ "\notvalor10 "+otvalor10
				+ "\notvalor11 "+otvalor11
				+ "\notvalor12 "+otvalor12
				+ "\notvalor13 "+otvalor13
				+ "\notvalor16 "+otvalor16
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco"  , cdunieco);
		params.put("cdramo"    , cdramo);
		params.put("estado"    , estado);
		params.put("nmpoliza"  , nmpoliza);
		params.put("cdgrupo"   , cdgrupo);
		params.put("otvalor06" , otvalor06);
		params.put("otvalor07" , otvalor07);
		params.put("otvalor08" , otvalor08);
		params.put("otvalor09" , otvalor09);
		params.put("otvalor10" , otvalor10);
		params.put("otvalor11" , otvalor11);
		params.put("otvalor12" , otvalor12);
		params.put("otvalor13" , otvalor13);
		params.put("otvalor16" , otvalor16);
		cotizacionDAO.movimientoMpolisitTvalositGrupo(params);
		logger.info("" 
				+ "\n###### movimientoMpolisitTvalositGrupo ######"
				+ "\n#############################################"
				);
	}

	@Override
	public void movimientoMpoligarGrupo(
			String  cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdgrupo
			,String cdgarant
			,String status
			,String cdmoneda
			,String accion)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ movimientoMpoligarGrupo @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ cdgrupo=") .append(cdgrupo)
				.append("\n@@@@@@ cdgarant=").append(cdgarant)
				.append("\n@@@@@@ status=")  .append(status)
				.append("\n@@@@@@ cdmoneda=").append(cdmoneda)
				.append("\n@@@@@@ accion=")  .append(accion)
				.toString()
				);
		cotizacionDAO.movimientoMpoligarGrupo(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nmsuplem
				,cdtipsit
				,cdgrupo
				,cdgarant
				,status
				,cdmoneda
				,accion
				);
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ movimientoMpoligarGrupo @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
	}
	
	@Override
	public Map<String,String> cargarDatosCotizacionGrupo(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String ntramite) throws Exception
	{
		logger.info(""
				+ "\n########################################"
				+ "\n###### cargarDatosCotizacionGrupo ######"
				+ "\n cdunieco "+cdunieco
				+ "\n cdramo "+cdramo
				+ "\n cdtipsit "+cdtipsit
				+ "\n estado "+estado
				+ "\n nmpoliza "+nmpoliza
				+ "\n ntramite "+ntramite
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("ntramite" , ntramite);
		Map<String,String>datos=cotizacionDAO.cargarDatosCotizacionGrupo(params);
		if(datos==null)
		{
			datos=new HashMap<String,String>();
		}
		logger.info(""
				+ "\n###### cargarDatosCotizacionGrupo ######"
				+ "\n########################################"
				);
		return datos;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarDatosCotizacionGrupo2(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String ntramite)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarDatosCotizacionGrupo2 @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ ntramite=").append(ntramite)
				.toString()
				);
		
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			Map<String,String>mapaDatos=cotizacionDAO.cargarDatosCotizacionGrupo2(
					cdunieco
					,cdramo
					,cdtipsit
					,estado
					,nmpoliza
					,ntramite
					);
			
			Map<String,String>mapaDatosAux = new HashMap<String,String>();
			for(Entry<String,String>en:mapaDatos.entrySet())
			{
				String key = en.getKey();
				if(StringUtils.isNotBlank(key)
						&&key.length()>"otvalor".length()
						&&key.substring(0, "otvalor".length()).equals("otvalor")
						)
				{
					mapaDatosAux.put(new StringBuilder("tvalopol_parametros.pv_").append(key).toString(),en.getValue());
				}
				else
				{
					mapaDatosAux.put(key,en.getValue());
				}
			}
			resp.setSmap(mapaDatosAux);
		}
		catch(ApplicationException ax)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString());
			resp.setRespuestaOculta(ax.getMessage());
			logger.error(resp.getRespuesta(),ax);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al cargar cotizacion #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarDatosCotizacionGrupo2 @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlistVO cargarGruposCotizacion2(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarGruposCotizacion2 @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.toString()
				);
		
		ManagerRespuestaSlistVO resp = new ManagerRespuestaSlistVO(true);
		
		try
		{
			List<Map<String,String>>listaAux    =cotizacionDAO.cargarGruposCotizacion2(cdunieco,cdramo,estado,nmpoliza);
			List<Map<String,String>>listaGrupos = new ArrayList<Map<String,String>>();
			
			for(Map<String,String>grupo:listaAux)
			{
				Map<String,String>grupoEditado=new HashMap<String,String>();
				
				for(Entry<String,String>en:grupo.entrySet())
				{
					String key = en.getKey();
					if(StringUtils.isNotBlank(key)
							&&key.length()>"otvalor".length()
							&&key.substring(0, "otvalor".length()).equals("otvalor")
							)
					{
						grupoEditado.put(new StringBuilder("parametros.pv_").append(key).toString(),en.getValue());
					}
					else
					{
						grupoEditado.put(key,en.getValue());
					}
				}
				listaGrupos.add(grupoEditado);
			}
			
			resp.setSlist(listaGrupos);
		}
		catch(ApplicationException ax)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString());
			resp.setRespuestaOculta(ax.getMessage());
			logger.error(resp.getRespuesta(),ax);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al cargar grupos #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarGruposCotizacion2 @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public List<Map<String,String>>cargarGruposCotizacion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)throws Exception
	{
		logger.info(""
				+ "\n####################################"
				+ "\n###### cargarGruposCotizacion ######"
				+ "\n cdunieco "+cdunieco
				+ "\n cdramo "+cdramo
				+ "\n estado "+estado
				+ "\n nmpoliza "+nmpoliza
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		List<Map<String,String>>listaGrupos=cotizacionDAO.cargarGruposCotizacion(params);
		if(listaGrupos==null)
		{
			listaGrupos=new ArrayList<Map<String,String>>();
		}
		logger.info("lista size: "+listaGrupos.size());
		logger.info(""
				+ "\n###### cargarGruposCotizacion ######"
				+ "\n####################################"
				);
		return listaGrupos;
	}
	
	@Override
	public Map<String,String>cargarDatosGrupoLinea(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo)throws Exception
	{
		logger.info(""
				+ "\n###################################"
				+ "\n###### cargarDatosGrupoLinea ######"
				+ "\n cdunieco "+cdunieco
				+ "\n cdramo "+cdramo
				+ "\n estado "+estado
				+ "\n nmpoliza "+nmpoliza
				+ "\n cdgrupo "+cdgrupo
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdgrupo"  , cdgrupo);
		Map<String,String>datos=cotizacionDAO.cargarDatosGrupoLinea(params);
		if(datos==null)
		{
			datos=new HashMap<String,String>();
		}
		logger.info(""
				+ "\n###### cargarDatosGrupoLinea ######"
				+ "\n###################################"
				);
		return datos;
	}
	
	@Override
	public List<Map<String,String>>cargarTvalogarsGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo)throws Exception
	{
		logger.info(""
				+ "\n##################################"
				+ "\n###### cargarTvalogarsGrupo ######"
				+ "\n cdunieco "+cdunieco
				+ "\n cdramo "+cdramo
				+ "\n estado "+estado
				+ "\n nmpoliza "+nmpoliza
				+ "\n cdgrupo "+cdgrupo
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdgrupo"  , cdgrupo);
		List<Map<String,String>>listaTvalogars=cotizacionDAO.cargarTvalogarsGrupo(params);
		if(listaTvalogars==null)
		{
			listaTvalogars=new ArrayList<Map<String,String>>();
		}
		logger.debug("lista size: "+listaTvalogars.size());
		logger.info(""
				+ "\n###### cargarTvalogarsGrupo ######"
				+ "\n##################################"
				);
		return listaTvalogars;
	}
	
	@Override
	public List<Map<String,String>>cargarTarifasPorEdad(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdplan
			,String cdgrupo
			,String cdperpag)throws Exception
	{
		logger.info(""
				+ "\n##################################"
				+ "\n###### cargarTarifasPorEdad ######"
				+ "\n cdunieco "+cdunieco
				+ "\n cdramo "+cdramo
				+ "\n estado "+estado
				+ "\n nmpoliza "+nmpoliza
				+ "\n nmsuplem "+nmsuplem
				+ "\n cdplan "+cdplan
				+ "\n cdgrupo "+cdgrupo
				+ "\n cdperpag "+cdperpag
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdplan"   , cdplan);
		params.put("cdgrupo"  , cdgrupo);
		params.put("cdperpag" , cdperpag);
		List<Map<String,String>>lista=cotizacionDAO.cargarTarifasPorEdad(params);
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		logger.info("cargarTarifasPorEdad lista size: "+lista.size());
		logger.info(""
				+ "\n###### cargarTarifasPorEdad ######"
				+ "\n##################################"
				);
		return lista;
	}
	
	@Override
	public List<Map<String,String>>cargarTarifasPorCobertura(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdplan
			,String cdgrupo
			,String cdperpag)throws Exception
	{
		logger.info(""
				+ "\n#######################################"
				+ "\n###### cargarTarifasPorCobertura ######"
				+ "\n cdunieco "+cdunieco
				+ "\n cdramo "+cdramo
				+ "\n estado "+estado
				+ "\n nmpoliza "+nmpoliza
				+ "\n nmsuplem "+nmsuplem
				+ "\n cdplan "+cdplan
				+ "\n cdgrupo "+cdgrupo
				+ "\n cdperpag "+cdperpag
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdplan"   , cdplan);
		params.put("cdgrupo"  , cdgrupo);
		params.put("cdperpag"  , cdperpag);
		List<Map<String,String>>lista=cotizacionDAO.cargarTarifasPorCobertura(params);
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		logger.info("cargarTarifasPorCobertura lista size: "+lista.size());
		logger.info(""
				+ "\n###### cargarTarifasPorCobertura ######"
				+ "\n#######################################"
				);
		return lista;
	}
	
	@Override
	public String cargarNombreAgenteTramite(String ntramite)throws Exception
	{
		logger.info(""
				+ "\n#######################################"
				+ "\n###### cargarNombreAgenteTramite ######"
				+ "\nntramite "+ntramite
				);
		String nombre=cotizacionDAO.cargarNombreAgenteTramite(ntramite);
		logger.info("cargarNombreAgenteTramite nombre: "+nombre);
		logger.info(""
				+ "\n###### cargarNombreAgenteTramite ######"
				+ "\n#######################################"
				);
		return nombre;
	}
	
	@Override
	public Map<String,String>cargarPermisosPantallaGrupo(String cdsisrol,String status)throws Exception
	{
		logger.info(""
				+ "\n#########################################"
				+ "\n###### cargarPermisosPantallaGrupo ######"
				+ "\ncdsisrol "+cdsisrol
				+ "\nstatus "+status
				);
		Map<String,String>res=cotizacionDAO.cargarPermisosPantallaGrupo(cdsisrol,status);
		logger.info(""
				+ "\nresponse "+res
				+ "\n###### cargarPermisosPantallaGrupo ######"
				+ "\n#########################################"
				);
		return res;
	}
	
	@Override
	public void guardarCensoCompleto(
			String  nombreCenso
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdedo
			,String cdmunici
			,String cdplan1
			,String cdplan2
			,String cdplan3
			,String cdplan4
			,String cdplan5)throws Exception
	{
		logger.info(""
				+ "\n##################################"
				+ "\n###### guardarCensoCompleto ######"
				+ "\nnombreCenso "+nombreCenso
				+ "\ncdunieco "+cdunieco
				+ "\ncdramo "+cdramo
				+ "\nestado "+estado
				+ "\nmpoliza "+nmpoliza
				+ "\ncdedo "+cdedo
				+ "\ncdmunici "+cdmunici
				+ "\ncdplan1 "+cdplan1
				+ "\ncdplan2 "+cdplan2
				+ "\ncdplan3 "+cdplan3
				+ "\ncdplan4 "+cdplan4
				+ "\ncdplan5 "+cdplan5
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("censo"     , nombreCenso);
		params.put("cdunieco"  , cdunieco);
		params.put("cdramo"    , cdramo);
		params.put("estado"    , estado);
		params.put("nmpoliza"  , nmpoliza);
		params.put("otvalor04" , cdedo);
		params.put("otvalor05" , cdmunici);
		params.put("cdplan1"   , cdplan1);
		params.put("cdplan2"   , cdplan2);
		params.put("cdplan3"   , cdplan3);
		params.put("cdplan4"   , cdplan4);
		params.put("cdplan5"   , cdplan5);
		cotizacionDAO.guardarCensoCompleto(params);
		logger.info(""
				+ "\n###### guardarCensoCompleto ######"
				+ "\n##################################"
				);
	}
	
	
	public int obtieneTipoValorAutomovil(String codigoPostal, String tipoVehiculo)throws Exception{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdpostal_i"     , codigoPostal);
		params.put("pv_cdtipveh_i"  , tipoVehiculo);
		Map<String,String>res = cotizacionDAO.obtieneTipoValorAutomovil(params);
		return Integer.parseInt(res.get("pv_etiqueta_o"));
	}
	
	@Override
	public List<Map<String,String>>cargarAseguradosExtraprimas(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo)throws Exception
	{
		logger.debug(""
				+ "\n#########################################"
				+ "\n###### cargarAseguradosExtraprimas ######"
				+ "\ncdunieco "+cdunieco
				+ "\ncdramo "+cdramo
				+ "\nestado "+estado
				+ "\nnmpoliza "+nmpoliza
				+ "\nnmsuplem "+nmsuplem
				+ "\ncdgrupo "+cdgrupo
				);
		
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdgrupo"  , cdgrupo);
		List<Map<String,String>>lista=cotizacionDAO.cargarAseguradosExtraprimas(params);
		
		logger.debug(""
				+ "\nlista size "+lista.size()
				+ "\n###### cargarAseguradosExtraprimas ######"
				+ "\n#########################################"
				);
		return lista;
	}
	
	@Override
	public void guardarExtraprimaAsegurado(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String nmsituac
			,String ocupacion
			,String extraprimaOcupacion
			,String peso
			,String estatura
			,String extraprimaSobrepeso
			)throws Exception
	{
		logger.info(""
				+ "\n########################################"
				+ "\n###### guardarExtraprimaAsegurado ######"
				+ "\ncdunieco "+cdunieco
				+ "\ncdramo "+cdramo
				+ "\nestado "+estado
				+ "\nnmpoliza "+nmpoliza
				+ "\nnmsuplem "+nmsuplem
				+ "\nnmsituac "+nmsituac
				+ "\nocupacion "+ocupacion
				+ "\nextraprimaOcupacion "+extraprimaOcupacion
				+ "\npeso "+peso
				+ "\nestatura "+estatura
				+ "\nextraprimaSobrepeso "+extraprimaSobrepeso
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco"            , cdunieco);
		params.put("cdramo"              , cdramo);
		params.put("estado"              , estado);
		params.put("nmpoliza"            , nmpoliza);
		params.put("nmsuplem"            , nmsuplem);
		params.put("nmsituac"            , nmsituac);
		params.put("ocupacion"           , ocupacion);
		params.put("extraprimaOcupacion" , extraprimaOcupacion);
		params.put("peso"                , peso);
		params.put("estatura"            , estatura);
		params.put("extraprimaSobrepeso" , extraprimaSobrepeso);
		cotizacionDAO.guardarExtraprimaAsegurado(params);
		logger.info(""
				+ "\n###### guardarExtraprimaAsegurado ######"
				+ "\n########################################"
				);
	}
	
	@Override
	public List<Map<String,String>>cargarAseguradosGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo)throws Exception
	{
		logger.info(""
				+ "\n###################################"
				+ "\n###### cargarAseguradosGrupo ######"
				+ "\ncdunieco "+cdunieco
				+ "\ncdramo "+cdramo
				+ "\nestado "+estado
				+ "\nnmpoliza "+nmpoliza
				+ "\nnmsuplem "+nmsuplem
				+ "\ncdgrupo "+cdgrupo
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("cdgrupo"  , cdgrupo);
		List<Map<String,String>>lista=cotizacionDAO.cargarAseguradosGrupo(params);
		logger.info(""
				+ "\nlista size "+lista.size()
				+ "\n###### cargarAseguradosGrupo ######"
				+ "\n###################################"
				);
		return lista;
	}

	@Override
	public void borrarMpoliperGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdgrupo)throws Exception
	{
		logger.info(""
				+ "\n#################################"
				+ "\n###### borrarMpoliperGrupo ######"
				+ "\ncdunieco "+cdunieco
				+ "\ncdramo "+cdramo
				+ "\nestado "+estado
				+ "\nnmpoliza "+nmpoliza
				+ "\ncdgrupo "+cdgrupo
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("cdgrupo"  , cdgrupo);
		cotizacionDAO.borrarMpoliperGrupo(params);
		logger.info(""
				+ "\n###### borrarMpoliperGrupo ######"
				+ "\n#################################"
				);
	}
	
	@Override
	public Map<String,String>cargarTipoSituacion(String cdramo,String cdtipsit)throws Exception
	{
		logger.info(""
				+ "\n#################################"
				+ "\n###### cargarTipoSituacion ######"
				+ "\ncdramo "+cdramo
				+ "\ncdtipsit "+cdtipsit
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdramo",cdramo);
		params.put("cdtipsit",cdtipsit);
		Map<String,String>respuesta=cotizacionDAO.cargarTipoSituacion(params);
		logger.info(""
				+ "\nrespuesta "+respuesta
				+ "\n###### cargarTipoSituacion ######"
				+ "\n#################################"
				);
		return respuesta;
	}
	
	@Override
	public String cargarCduniecoAgenteAuto(String cdagente)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n######################################")
				.append("\n###### cargarCduniecoAgenteAuto ######")
				.append("\ncdagente ")
				.append(cdagente)
				.toString()
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdagente",cdagente);
		String cdunieco=cotizacionDAO.cargarCduniecoAgenteAuto(params);
		logger.info(
				new StringBuilder()
				.append("\ncdunieco ")
				.append(cdunieco)
				.append("\n###### cargarCduniecoAgenteAuto ######")
				.append("\n######################################")
				.toString()
				);
		return cdunieco;
	}
	
	@Override
	public Map<String,String>obtenerDatosAgente(String cdagente,String cdramo)throws Exception
	{
		logger.info(new StringBuilder()
		.append("\n################################")
		.append("\n###### obtenerDatosAgente ######")
		.append("\ncdagente=").append(cdagente)
		.append("\ncdramo=").append(cdramo)
		.toString()
				);
		Map<String,String>datos=cotizacionDAO.obtenerDatosAgente(cdagente,cdramo);
		logger.info(new StringBuilder()
		.append("\ndatos=").append(datos)
		.append("\n###### obtenerDatosAgente ######")
		.append("\n################################")
		.toString()
				);
		return datos;
	}
	
	@Override
	public ManagerRespuestaSmapVO obtenerParametrosCotizacion(
			ParametroCotizacion parametro
			,String cdramo
			,String cdtipsit
			,String clave4
			,String clave5)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ obtenerParametrosCotizacion @@@@@@")
				.append("\n@@@@@@ parametro=").append(parametro.getParametro())
				.append("\n@@@@@@ cdramo=")   .append(cdramo)
				.append("\n@@@@@@ cdtipsit=") .append(cdtipsit)
				.append("\n@@@@@@ clave4=")   .append(clave4)
				.append("\n@@@@@@ clave5=")   .append(clave5)
				.toString()
				);
		
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			Map<String,String>valores=cotizacionDAO.obtenerParametrosCotizacion(parametro,cdramo,cdtipsit,clave4,clave5);
			resp.setSmap(valores);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("No existe el par&aacute;metro #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ obtenerParametrosCotizacion @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarAutoPorClaveGS(String cdramo,String clavegs,String cdtipsit,String cdsisrol) throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarAutoPorClaveGS @@@@@@")
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ clave gs=").append(clavegs)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ cdsisrol=").append(cdtipsit)
				.toString()
				);
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			Map<String,String>valores=cotizacionDAO.cargarAutoPorClaveGS(cdramo,clavegs,cdtipsit,cdsisrol);
			resp.setSmap(valores);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al recuperar datos del auto #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarAutoPorClaveGS @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarClaveGSPorAuto(String cdramo,String modelo) throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarClaveGSPorAuto @@@@@@")
				.append("\n@@@@@@ cdramo=").append(cdramo)
				.append("\n@@@@@@ modelo=").append(modelo)
				.toString()
				);
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			Map<String,String>params=new HashMap<String,String>();
			params.put("cdramo" , cdramo);
			params.put("modelo" , modelo);
			Map<String,String>valores=cotizacionDAO.cargarClaveGSPorAuto(params);
			resp.setSmap(valores);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al recuperar clave gs del auto #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarClaveGSPorAuto @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarSumaAseguradaAuto(String cdsisrol,String modelo,String version,String cdramo,String cdtipsit)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarSumaAseguradaAuto @@@@@@")
				.append("\n@@@@@@ cdsisrol=").append(cdsisrol)
				.append("\n@@@@@@ modelo=")  .append(modelo)
				.append("\n@@@@@@ version=") .append(version)
				.toString()
				);
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			Map<String,String>params=new HashMap<String,String>();
			params.put("cdsisrol" , cdsisrol);
			params.put("modelo"   , modelo);
			params.put("version"  , version);
			Map<String,String>valores=cotizacionDAO.cargarSumaAseguradaAuto(params);
			logger.debug(
					new StringBuilder()
					.append("suma asegurada=")
					.append(valores)
					.toString());
			
			String claveSuma = "SUMASEG";
			String suma      = valores.get(claveSuma);
			Double dSuma     = Double.valueOf(suma);
			
			Map<String,String>valoresDeprecio=cotizacionDAO.obtenerParametrosCotizacion(
					ParametroCotizacion.DEPRECIACION
					,cdramo
					,cdtipsit
					,cdsisrol
					,null
					);
			
			logger.debug(
					new StringBuilder()
					.append("depreciacion=")
					.append(valoresDeprecio)
					.toString());
			
			Double deprecio=Double.valueOf(valoresDeprecio.get("P1VALOR"));
			dSuma = dSuma*(1d-deprecio);
			
			valores.put(claveSuma,String.format("%.2f", dSuma));
			
			resp.setSmap(valores);

			logger.debug(
					new StringBuilder()
					.append("suma asegurada nueva=")
					.append(valores)
					.toString());
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al cargar valor comercial del auto #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarSumaAseguradaAuto @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO agregarClausulaICD(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdclausu
			,String nmsuplem
			,String icd)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ agregarClausulaICD @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsituac=").append(nmsituac)
				.append("\n@@@@@@ cdclausu=").append(cdclausu)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.append("\n@@@@@@ icd=")     .append(icd)
				.toString()
				);
		ManagerRespuestaVoidVO resp = new ManagerRespuestaVoidVO(true);
		
		try
		{
			Map<String,String>params=new HashMap<String,String>();
			params.put("cdunieco" , cdunieco);
			params.put("cdramo"   , cdramo);
			params.put("estado"   , estado);
			params.put("nmpoliza" , nmpoliza);
			params.put("nmsituac" , nmsituac);
			params.put("cdclausu" , cdclausu);
			params.put("nmsuplem" , nmsuplem);
			params.put("icd"      , icd);
			params.put("accion"   , Constantes.INSERT_MODE);
			cotizacionDAO.movimientoMpolicotICD(params);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al relacionar ICD #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ agregarClausulaICD @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlistVO cargarClausulaICD(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdclausu
			,String nmsuplem)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarClausulaICD @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsituac=").append(nmsituac)
				.append("\n@@@@@@ cdclausu=").append(cdclausu)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.toString()
				);
		ManagerRespuestaSlistVO resp = new ManagerRespuestaSlistVO(true);
		
		try
		{
			Map<String,String>params=new HashMap<String,String>();
			params.put("cdunieco" , cdunieco);
			params.put("cdramo"   , cdramo);
			params.put("estado"   , estado);
			params.put("nmpoliza" , nmpoliza);
			params.put("nmsituac" , nmsituac);
			params.put("cdclausu" , cdclausu);
			params.put("nmsuplem" , nmsuplem);
			resp.setSlist(cotizacionDAO.cargarMpolicotICD(params));
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al obtener los ICD #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
			
			resp.setSlist(new ArrayList<Map<String,String>>());
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarClausulaICD @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO borrarClausulaICD(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String cdclausu
			,String nmsuplem
			,String icd)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ borrarClausulaICD @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsituac=").append(nmsituac)
				.append("\n@@@@@@ cdclausu=").append(cdclausu)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.append("\n@@@@@@ icd=")     .append(icd)
				.toString()
				);
		ManagerRespuestaVoidVO resp = new ManagerRespuestaVoidVO(true);
		
		try
		{
			Map<String,String>params=new HashMap<String,String>();
			params.put("cdunieco" , cdunieco);
			params.put("cdramo"   , cdramo);
			params.put("estado"   , estado);
			params.put("nmpoliza" , nmpoliza);
			params.put("nmsituac" , nmsituac);
			params.put("cdclausu" , cdclausu);
			params.put("nmsuplem" , nmsuplem);
			params.put("icd"      , icd);
			params.put("accion"   , Constantes.DELETE_MODE);
			cotizacionDAO.movimientoMpolicotICD(params);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(
					new StringBuilder()
					.append("Error al borrar ICD #").append(timestamp)
					.toString()
					);
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ borrarClausulaICD @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public List<Map<String,String>>cargarConfiguracionGrupo(String cdramo,String cdtipsit)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n######################################")
				.append("\n###### cargarConfiguracionGrupo ######")
				.append("\n###### cdramo=")  .append(cdramo)
				.append("\n###### cdtipsit=").append(cdtipsit)
				.toString()
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		List<Map<String,String>>lista=cotizacionDAO.cargarConfiguracionGrupo(params);
		logger.info(
				new StringBuilder()
				.append("\n###### lista=").append(lista)
				.append("\n###### cargarConfiguracionGrupo ######")
				.append("\n######################################")
				.toString()
				);
		return lista;
	}
	
	@Override
	public ComponenteVO cargarComponenteTatrisit(String cdtipsit,String cdusuari,String cdatribu)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n######################################")
				.append("\n###### cargarComponenteTatrisit ######")
				.append("\n###### cdtipsit=").append(cdtipsit)
				.append("\n###### cdusuari=").append(cdusuari)
				.append("\n###### cdatribu=").append(cdatribu)
				.toString()
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdtipsit" , cdtipsit);
		params.put("cdusuari" , cdusuari);
		params.put("cdatribu" , cdatribu);
		ComponenteVO comp = cotizacionDAO.cargarComponenteTatrisit(params);
		logger.info(
				new StringBuilder()
				.append("\n###### componente=").append(comp)
				.append("\n###### cargarComponenteTatrisit ######")
				.append("\n######################################")
				.toString()
				);
		return comp;
	}
	
	@Override
	public ComponenteVO cargarComponenteTatrigar(String cdramo,String cdtipsit,String cdgarant,String cdatribu)throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n######################################")
				.append("\n###### cargarComponenteTatrigar ######")
				.append("\n###### cdramo=")  .append(cdramo)
				.append("\n###### cdtipsit=").append(cdtipsit)
				.append("\n###### cdgarant=").append(cdgarant)
				.append("\n###### cdatribu=").append(cdatribu)
				.toString()
				);
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdramo"   , cdramo);
		params.put("cdtipsit" , cdtipsit);
		params.put("cdgarant" , cdgarant);
		params.put("cdatribu" , cdatribu);
		ComponenteVO comp = cotizacionDAO.cargarComponenteTatrigar(params);
		logger.info(
				new StringBuilder()
				.append("\n###### componente=").append(comp)
				.append("\n###### cargarComponenteTatrigar ######")
				.append("\n######################################")
				.toString()
				);
		return comp;
	}
	
	@Override
	public ManagerRespuestaVoidVO validarDescuentoAgente(
			String  tipoUnidad
			,String uso
			,String zona
			,String promotoria
			,String cdagente
			,String descuento)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ validarDescuentoAgente @@@@@@")
				.append("\n@@@@@@ tipoUnidad=").append(tipoUnidad)
				.append("\n@@@@@@ uso=").append(uso)
				.append("\n@@@@@@ zona=").append(zona)
				.append("\n@@@@@@ promotoria=").append(promotoria)
				.append("\n@@@@@@ cdagente=").append(cdagente)
				.append("\n@@@@@@ descuento=").append(descuento)
				.toString()
				);
		ManagerRespuestaVoidVO resp = new ManagerRespuestaVoidVO(true);
		
		//procedure
		try
		{
			cotizacionDAO.validarDescuentoAgente(tipoUnidad,uso,zona,promotoria,cdagente,descuento);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder(ex.getMessage()).append(" #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ validarDescuentoAgente @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public void movimientoTdescsup(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nsuplogi
			,String cdtipsup
			,Date feemisio
			,String nmsolici
			,Date fesolici
			,Date ferefere
			,String cdseqpol
			,String cdusuari
			,String nusuasus
			,String nlogisus
			,String cdperson
			,String accion)throws Exception
	{
		cotizacionDAO.movimientoTdescsup(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nsuplogi
				,cdtipsup
				,feemisio
				,nmsolici
				,fesolici
				,ferefere
				,cdseqpol
				,cdusuari
				,nusuasus
				,nlogisus
				,cdperson
				,accion);
	}
	
	@Override
	public ManagerRespuestaImapSmapVO pantallaCotizacionGrupo(
			String cdramo
			,String cdtipsit
			,String ntramite
			,String ntramiteVacio
			,String status
			,String cdusuari
			,String cdsisrol
			,String nombreUsuario
			,String cdagente
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ pantallaCotizacionGrupo @@@@@@")
				.append("\n@@@@@@ cdramo=")       .append(cdramo)
				.append("\n@@@@@@ cdtipsit=")     .append(cdtipsit)
				.append("\n@@@@@@ ntramite=")     .append(ntramite)
				.append("\n@@@@@@ ntramiteVacio=").append(ntramiteVacio)
				.append("\n@@@@@@ status=")       .append(status)
				.append("\n@@@@@@ cdusuari=")     .append(cdusuari)
				.append("\n@@@@@@ cdsisrol=")     .append(cdsisrol)
				.append("\n@@@@@@ nombreUsuario=").append(nombreUsuario)
				.append("\n@@@@@@ cdagente=")     .append(cdagente)
				.toString()
				);
		
		ManagerRespuestaImapSmapVO resp = new ManagerRespuestaImapSmapVO(true);

		//retocar datos de entrada
		try
		{
			resp.setSmap(new HashMap<String,String>());
			if(StringUtils.isBlank(status))
			{
				status = "0";
			}
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al procesar datos #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		//retocar datos de entrada
		
		String nombreAgente = null;
		
		//datos del agente
		if(resp.isExito())
		{
			try
			{
				//si entran como agente
				if(StringUtils.isBlank(ntramite)&&StringUtils.isBlank(ntramiteVacio))
				{
				    DatosUsuario datUsu = cotizacionDAO.cargarInformacionUsuario(cdusuari,cdtipsit);
				    String cdunieco     = datUsu.getCdunieco();
	        		
				    resp.getSmap().put("cdunieco",cdunieco);
				    
	        		cdagente     = datUsu.getCdagente();
	        		nombreAgente = nombreUsuario;
				}
				//si entran por tramite o tramite vacio
				else if(StringUtils.isNotBlank(ntramite)||StringUtils.isNotBlank(ntramiteVacio))
				{
					nombreAgente = cotizacionDAO.cargarNombreAgenteTramite(StringUtils.isNotBlank(ntramite)?ntramite:ntramiteVacio);
				}
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener datos del agente #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		//datos del agente
		
		GeneradorCampos gcTatri            = null;
		GeneradorCampos gcGral             = null;
		List<ComponenteVO>tatrisitOriginal = null;
		
		//componentes
		if(resp.isExito())
		{
			try
			{
				gcTatri = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gcTatri.setCdramo(cdramo);
				gcTatri.setCdtipsit(cdtipsit);
				resp.setImap(new HashMap<String,Item>());
				
				//TATRISIT ORIGINAL
				tatrisitOriginal = cotizacionDAO.cargarTatrisit(cdtipsit, cdusuari);
				
				//columnas base
				List<ComponenteVO>tatrisitColsBase = new ArrayList<ComponenteVO>();
				for(ComponenteVO iTatri:tatrisitOriginal)
				{
					if(StringUtils.isNotBlank(iTatri.getSwsuscri())
							&&iTatri.getSwsuscri().equals("N")
							&&iTatri.getSwGrupo().equals("S")
							&&iTatri.getSwGrupoLinea().equals("N")
							)
					{
						logger.debug(new StringBuilder("SE AGREGA PARA COLUMNA BASE ").append(iTatri).toString());
						iTatri.setColumna("S");
						tatrisitColsBase.add(iTatri);
					}
				}
				
				if(tatrisitColsBase.size()>0)
				{
					gcTatri.generaComponentes(tatrisitColsBase, true, true, false, true, true, false);
					resp.getImap().put("colsBaseFields"  , gcTatri.getFields());
					resp.getImap().put("colsBaseColumns" , gcTatri.getColumns());
				}
				else
				{
					resp.getImap().put("colsBaseFields"  , null);
					resp.getImap().put("colsBaseColumns" , null);
				}
				//columnas base
				
				//columnas extendidas (de coberturas)
				List<ComponenteVO> tatrisitColsCober = new ArrayList<ComponenteVO>();
				for(ComponenteVO iTatri:tatrisitOriginal)
				{
					if(StringUtils.isNotBlank(iTatri.getSwsuscri())
							&&iTatri.getSwsuscri().equals("N")
							&&iTatri.getSwGrupo().equals("S")
							)
					{
						logger.debug(new StringBuilder("SE AGREGA PARA COLUMNA DE COBERTURA ").append(iTatri).toString());
						iTatri.setColumna("S");
						tatrisitColsCober.add(iTatri);
					}
				}
				if(tatrisitColsCober.size()>0)
				{
					gcTatri.generaComponentes(tatrisitColsCober, true, true, false, true, true, false);
					resp.getImap().put("colsExtFields"  , gcTatri.getFields());
					resp.getImap().put("colsExtColumns" , gcTatri.getColumns());
				}
				else
				{
					resp.getImap().put("colsExtFields"  , null);
					resp.getImap().put("colsExtColumns" , null);
				}
				//columnas extendidas (de coberturas)
				
				//factores
				List<ComponenteVO>factores = new ArrayList<ComponenteVO>();
				for(ComponenteVO iTatri:tatrisitOriginal)
				{
					if(StringUtils.isNotBlank(iTatri.getSwsuscri())
							&&iTatri.getSwsuscri().equals("N")
							&&iTatri.getSwGrupo().equals("N")
							&&iTatri.getSwGrupoFact().equals("S")
							)
					{
						logger.debug(new StringBuilder("SE AGREGA PARA FACTOR ").append(iTatri).toString());
						iTatri.setColumna("S");
						factores.add(iTatri);
					}
				}
				if(factores.size()>0)
				{
					gcTatri.generaComponentes(factores, true, true, false, true, true, false);
					resp.getImap().put("factoresFields"  , gcTatri.getFields());
					resp.getImap().put("factoresColumns" , gcTatri.getColumns());
				}
				else
				{
					resp.getImap().put("factoresFields"  , null);
					resp.getImap().put("factoresColumns" , null);
				}
				//factores
				
				gcGral = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				
				List<ComponenteVO>tatripol=cotizacionDAO.cargarTatripol(cdramo);
				if(tatripol!=null&&tatripol.size()>0)
				{
					gcGral.generaComponentes(tatripol,true,false,true,false,false,false);
					resp.getImap().put("itemsRiesgo",gcGral.getItems());
				}
				else
				{
					resp.getImap().put("itemsRiesgo" , null);
				}
				
				List<ComponenteVO>componentesContratante=pantallasDAO.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "CONTRATANTE", null);
				gcGral.generaComponentes(componentesContratante, true,false,true,false,false,false);
				resp.getImap().put("itemsContratante"  , gcGral.getItems());
				
				List<ComponenteVO>componentesAgente=pantallasDAO.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "AGENTE", null);
				componentesAgente.get(0).setDefaultValue(nombreAgente);
				componentesAgente.get(1).setDefaultValue(cdagente);
				gcGral.generaComponentes(componentesAgente, true,false,true,false,false,false);
				resp.getImap().put("itemsAgente"  , gcGral.getItems());
				
				List<ComponenteVO>columnaEditorPlan=pantallasDAO.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "EDITOR_PLANES2", null);
				gcGral.generaComponentes(columnaEditorPlan, true, false, false, true, true, false);
				resp.getImap().put("editorPlanesColumn",gcGral.getColumns());
				
				List<ComponenteVO>comboFormaPago=pantallasDAO.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "COMBO_FORMA_PAGO", null);
				gcGral.generaComponentes(comboFormaPago, true,false,true,false,false,false);
				resp.getImap().put("comboFormaPago"  , gcGral.getItems());
				
				List<ComponenteVO>comboRepartoPago=pantallasDAO.obtenerComponentes(
						null, null, null,
						null, null, null,
						"COTIZACION_GRUPO", "COMBO_REPARTO_PAGO", null);
				gcGral.generaComponentes(comboRepartoPago, true,false,true,false,false,false);
				resp.getImap().put("comboRepartoPago"  , gcGral.getItems());
				
				List<ComponenteVO>botones=pantallasDAO.obtenerComponentes(
						null, null, "|"+status+"|",
						null, null, cdsisrol,
						"COTIZACION_GRUPO", "BOTO2NES", null);
				if(botones!=null&&botones.size()>0)
				{
					gcGral.generaComponentes(botones, true, false, false, false, false, true);
					resp.getImap().put("botones" , gcGral.getButtons());
				}
				else
				{
					resp.getImap().put("botones" , null);
				}
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener componentes #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		//componentes
		
		//permisos
		if(resp.isExito())
		{
			try
			{
				resp.getSmap().put("status" , status);
				resp.getSmap().putAll(cotizacionDAO.cargarPermisosPantallaGrupo(cdsisrol,status));
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener permisos #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		//permisos
		
		//campos para extraprimas
		if(resp.isExito() && resp.getSmap().containsKey("EXTRAPRIMAS")
				 && StringUtils.isNotBlank(resp.getSmap().get("EXTRAPRIMAS"))
				 && resp.getSmap().get("EXTRAPRIMAS").equals("S"))
		{
			try
			{
				List<ComponenteVO>tatrisitExtraprima = new ArrayList<ComponenteVO>();
				for(ComponenteVO iTatri:tatrisitOriginal)
				{
					if(StringUtils.isNotBlank(iTatri.getSwsuscri())
							&&iTatri.getSwsuscri().equals("S")
							&&iTatri.getSwGrupoExtr().equals("S")
							)
					{
						logger.debug(new StringBuilder("SE AGREGA PARA EXTRAPRIMA ").append(iTatri).toString());
						iTatri.setColumna("S");
						tatrisitExtraprima.add(iTatri);
					}
				}
				
				if(tatrisitExtraprima.size()>0)
				{
					gcTatri.generaComponentes(tatrisitExtraprima, true, true, false, true, true, false);
					resp.getImap().put("extraprimasFields"  , gcTatri.getFields());
					resp.getImap().put("extraprimasColumns" , gcTatri.getColumns());
				}
				else
				{
					resp.getImap().put("extraprimasFields"  , null);
					resp.getImap().put("extraprimasColumns" , null);
				}
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener componentes de extraprimas #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		//campos para extraprimas
		
		//campos de asegurados
		if(resp.isExito() && resp.getSmap().containsKey("ASEGURADOS")
				 && StringUtils.isNotBlank(resp.getSmap().get("ASEGURADOS"))
				 && resp.getSmap().get("ASEGURADOS").equals("S"))
		{
			try
			{
				
				List<ComponenteVO>componentesExtraprimas=pantallasDAO.obtenerComponentes(
						null  , null , null
						,null , null , cdsisrol
						,"COTIZACION_GRUPO", "ASEGURADOS", null);
				gcGral.generaComponentes(componentesExtraprimas, true, true, false, true, false, false);
				resp.getImap().put("aseguradosColumns" , gcGral.getColumns());
				resp.getImap().put("aseguradosFields"  , gcGral.getFields());
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener componentes de asegurados #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		//campos de asegurados
		
		//campos para recuperados
		if(resp.isExito() && resp.getSmap().containsKey("ASEGURADOS_EDITAR")
				 && StringUtils.isNotBlank(resp.getSmap().get("ASEGURADOS_EDITAR"))
				 && resp.getSmap().get("ASEGURADOS_EDITAR").equals("S"))
		{
			try
			{
				List<ComponenteVO>componentesRecuperados=pantallasDAO.obtenerComponentes(
						null  , null , null
						,null , null , cdsisrol
						,"COTIZACION_GRUPO", "RECUPERADOS", null);
				gcGral.generaComponentes(componentesRecuperados, true, true, false, true, true, false);
				resp.getImap().put("recuperadosColumns" , gcGral.getColumns());
				resp.getImap().put("recuperadosFields"  , gcGral.getFields());
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener componentes de recuperados #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		//campos para recuperados
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ pantallaCotizacionGrupo @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarClienteCotizacion(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarClienteCotizacion @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		
		ManagerRespuestaSmapVO resp = new ManagerRespuestaSmapVO(true);
		
		try
		{
			resp.setSmap(cotizacionDAO.cargarClienteCotizacion(cdunieco,cdramo,estado,nmpoliza));
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al obtener cliente de cotizacion #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarClienteCotizacion @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarConceptosGlobalesGrupo(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdperpag)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarConceptosGlobalesGrupo @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.append("\n@@@@@@ cdperpag=").append(cdperpag)
				.toString()
				);
		
		ManagerRespuestaSmapVO resp=new ManagerRespuestaSmapVO(true);
		
		//procedure
		try
		{
			resp.setSmap(cotizacionDAO.cargarConceptosGlobalesGrupo(cdunieco,cdramo,estado,nmpoliza,nmsuplem,cdperpag));
		}
		catch(ApplicationException ax)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString());
			resp.setRespuestaOculta(ax.getMessage());
			logger.error(resp.getRespuesta(),ax);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al obtener conceptos globales #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarConceptosGlobalesGrupo @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO generarTramiteGrupo(
			String cdunieco
			,String cdramo
			,String nmpoliza
			,String feini
			,String fefin
			,String cdperpag
			,String pcpgocte
			,Map<String,String> tvalopol
			,String ntramite
			,String ntramiteVacio
			,String miTimestamp
			,String rutaDocumentosTemporal
			,String tipoCenso
			,String dominioServerLayouts
			,String userServerLayouts
			,String passServerLayouts
			,String directorioServerLayouts
			,String cdtipsit
			,List<Map<String,Object>>grupos
			,String codpostalCli
			,String cdedoCli
			,String cdmuniciCli
			,String cdagente
			,String cdusuari
			,String cdsisrol
			,String clasif
			,String LINEA_EXTENDIDA
			,String cdpersonCli
			,String nombreCli
			,String rfcCli
			,String dsdomiciCli
			,String nmnumeroCli
			,String nmnumintCli
			,String cdelemen
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ generarTramiteGrupo @@@@@@")
				.append("\n@@@@@@ cdunieco=")               .append(cdunieco)
				.append("\n@@@@@@ cdramo=")                 .append(cdramo)
				.append("\n@@@@@@ nmpoliza=")               .append(nmpoliza)
				.append("\n@@@@@@ feini=")                  .append(feini)
				.append("\n@@@@@@ fefin=")                  .append(fefin)
				.append("\n@@@@@@ cdperpag=")               .append(cdperpag)
				.append("\n@@@@@@ pcpgocte=")               .append(pcpgocte)
				.append("\n@@@@@@ smap=")                   .append(tvalopol)
				.append("\n@@@@@@ ntramite=")               .append(ntramite)
				.append("\n@@@@@@ ntramiteVacio=")          .append(ntramiteVacio)
				.append("\n@@@@@@ miTimestamp=")            .append(miTimestamp)
				.append("\n@@@@@@ rutaDocumentosTemporal=") .append(rutaDocumentosTemporal)
				.append("\n@@@@@@ tipoCenso=")              .append(tipoCenso)
				.append("\n@@@@@@ dominioServerLayouts=")   .append(dominioServerLayouts)
				.append("\n@@@@@@ userServerLayouts=")      .append(userServerLayouts)
				.append("\n@@@@@@ passServerLayouts=")      .append(passServerLayouts)
				.append("\n@@@@@@ directorioServerLayouts=").append(directorioServerLayouts)
				.append("\n@@@@@@ cdtipsit=")               .append(cdtipsit)
				.append("\n@@@@@@ grupos=")                 .append(grupos)
				.append("\n@@@@@@ codpostalCli=")           .append(codpostalCli)
				.append("\n@@@@@@ cdedoCli=")               .append(cdedoCli)
				.append("\n@@@@@@ cdmuniciCli=")            .append(cdmuniciCli)
				.append("\n@@@@@@ cdagente=")               .append(cdagente)
				.append("\n@@@@@@ cdusuari=")               .append(cdusuari)
				.append("\n@@@@@@ cdsisrol=")               .append(cdsisrol)
				.append("\n@@@@@@ clasif=")                 .append(clasif)
				.append("\n@@@@@@ LINEA_EXTENDIDA=")        .append(LINEA_EXTENDIDA)
				.append("\n@@@@@@ cdpersonCli=")            .append(cdpersonCli)
				.append("\n@@@@@@ nombreCli=")              .append(nombreCli)
				.append("\n@@@@@@ rfcCli=")                 .append(rfcCli)
				.append("\n@@@@@@ dsdomiciCli=")            .append(dsdomiciCli)
				.append("\n@@@@@@ nmnumeroCli=")            .append(nmnumeroCli)
				.append("\n@@@@@@ nmnumintCli=")            .append(nmnumintCli)
				.append("\n@@@@@@ cdelemen=")               .append(cdelemen)
				.toString()
				);
		
		ManagerRespuestaSmapVO resp=new ManagerRespuestaSmapVO(true);
		resp.setSmap(new HashMap<String,String>());
		
		//nmpoliza
		if(resp.isExito()&&StringUtils.isBlank(nmpoliza))
		{
			try
			{
				nmpoliza = cotizacionDAO.calculaNumeroPoliza(cdunieco,cdramo,"W");
				resp.getSmap().put("nmpoliza",nmpoliza);
			}
			catch(ApplicationException ax)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString());
				resp.setRespuestaOculta(ax.getMessage());
				logger.error(resp.getRespuesta(),ax);
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al calcular numero de poliza #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		Date fechaHoy = new Date();
		
		//nmpoliza
		if(resp.isExito())
		{
			try
			{
				cotizacionDAO.movimientoPoliza(
						cdunieco
						,cdramo
						,"W"      //estado
						,nmpoliza
						,"0"      //nmsuplem
						,"V"      //status
						,"0"      //swestado
						,null     //nmsolici
			            ,null     //feautori
			            ,null     //cdmotanu
			            ,null     //feanulac
			            ,"N"      //swautori
			            ,"001"    //cdmoneda
			            ,null     //feinisus
			            ,null     //fefinsus
			            ,"R"      //ottempot
			            ,feini
			            ,"12:00"  //hhefecto
			            ,fefin
			            ,null     //fevencim
			            ,"0"      //nmrenova
			            ,null     //ferecibo
			            ,null     //feultsin
			            ,"0"      //nmnumsin
			            ,"N"      //cdtipcoa
			            ,"A"      //swtarifi
			            ,null     //swabrido
			            ,renderFechas.format(fechaHoy) //feemisio
			            ,cdperpag
			            ,null     //nmpoliex
			            ,"P1"     //nmcuadro
			            ,"100"    //porredau
			            ,"S"      //swconsol
			            ,null     //nmpolant
			            ,null     //nmpolnva
			            ,renderFechas.format(fechaHoy) //fesolici
			            ,null     //cdramant
			            ,null     //cdmejred
			            ,null     //nmpoldoc
			            ,null     //nmpoliza2
			            ,null     //nmrenove
			            ,null     //nmsuplee
			            ,null     //ttipcamc
			            ,null     //ttipcamv
			            ,null     //swpatent
			            ,pcpgocte
			            ,"U"      //accion
						);
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al guardar poliza #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		//tvalopol
		if(resp.isExito())
		{
			try
			{
				Map<String,String>valores = new HashMap<String,String>();
				for(Entry<String,String>atrib:tvalopol.entrySet())
				{
					String key = atrib.getKey();
					if(StringUtils.isNotBlank(key)
							&&key.length()>="tvalopol_".length())
					{
						if(key.substring(0, "tvalopol_".length()).equals("tvalopol_"))
						{
							valores.put(key.substring("tvalopol_parametros.pv_".length(), key.length()),atrib.getValue());
						}
					}
				}
				if(valores.size()>0)
				{
					logger.debug(new StringBuilder("SE GUARDAN VALORES EN TVALOPOL=").append(valores).toString());
					cotizacionDAO.movimientoTvalopol(
							cdunieco
							,cdramo
							,"W"
							,nmpoliza
							,"0" //nmsuplem
							,"V" //status
							,valores
							);
				}
				else
				{
					logger.debug("NO SE GUARDAN VALORES EN TVALOPOL");
				}
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al guardar datos adicionales de poliza #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		boolean hayTramite      = StringUtils.isNotBlank(ntramite);
		boolean hayTramiteVacio = StringUtils.isNotBlank(ntramiteVacio);
		boolean esCensoSolo     = StringUtils.isNotBlank(tipoCenso)&&tipoCenso.equalsIgnoreCase("solo");
		String  nombreCenso     = null;
		
		//enviar censo
		if(resp.isExito()&&(!hayTramite||hayTramiteVacio))
		{
			FileInputStream input       = null;
			XSSFSheet       sheet       = null;
			File            archivoTxt  = null;
			PrintStream     output      = null;
			
			//instanciar
			try
			{
				File censo            = new File(new StringBuilder(rutaDocumentosTemporal).append("/censo_").append(miTimestamp).toString());
				input                 = new FileInputStream(censo);
				XSSFWorkbook workbook = new XSSFWorkbook(input);
				sheet                 = workbook.getSheetAt(0);
				nombreCenso           = new StringBuilder("censo_").append(miTimestamp).append("_").append(nmpoliza).append(".txt").toString();
				archivoTxt            = new File(new StringBuilder(rutaDocumentosTemporal).append("/").append(nombreCenso).toString());
				output                = new PrintStream(archivoTxt);
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al procesar censo #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
			
			//crear pipes y ejecutar procedure
			if(resp.isExito())
			{
				//pipes para censo solo
				if(esCensoSolo)
				{
					//Iterate through each rows one by one
					logger.debug(
							new StringBuilder()
							.append("\n----------------------------------------------")
							.append("\n------ ").append(archivoTxt.getAbsolutePath())
							.toString()
							);
		            Iterator<Row> rowIterator = sheet.iterator();
		            int fila = 0;
		            while (rowIterator.hasNext()&&resp.isExito()) 
		            {
		                Row  row     = rowIterator.next();
		                Date auxDate = null;
		                Cell auxCell = null;
		                
		                fila = fila + 1;
		                
		                if(resp.isExito())
		                {
			                try
			                {	
				                auxCell=row.getCell(0);
				                logger.debug(
				                		new StringBuilder("NOMBRE: ")
				                		.append(
				                				auxCell!=null?
				                					new StringBuilder(auxCell.getStringCellValue()).append("|").toString()
				                					:"|"
				                				)
				                		.toString()
				                		);
				                output.print(
				                		auxCell!=null?
				                				new StringBuilder(auxCell.getStringCellValue()).append("|").toString()
				                				:"|"
				                );
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Nombre' (")
			                			.append("A")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString()
			                			);
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
			                try
			                {
				                auxCell=row.getCell(1);
				                logger.debug(
				                		new StringBuilder("APELLIDO: ")
				                		.append(
				                				auxCell!=null?
				                					new StringBuilder(auxCell.getStringCellValue()).append("|").toString()
				                					:"|"
				                		).toString()
				                );
				                output.print(
				                		auxCell!=null?
				                				new StringBuilder(auxCell.getStringCellValue()).append("|").toString()
				                				:"|"
				                );
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Apellido paterno' (")
			                			.append("B")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #").append(timestamp)
			                			.toString()
			                			);
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                auxCell=row.getCell(2);
				                logger.debug(
				                		new StringBuilder("APELLIDO 2: ")
				                		.append(
				                				auxCell!=null?
				                						new StringBuilder(auxCell.getStringCellValue()).append("|").toString()
				                						:"|"
				                		).toString()
				                		);
				                output.print(
				                		auxCell!=null?
				                				new StringBuilder(auxCell.getStringCellValue()).append("|").toString()
				                				:"|"
				                		);
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Apellido materno' (")
			                			.append("C")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString()
			                			);
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                auxCell=row.getCell(3);
				                logger.debug(
				                		new StringBuilder("EDAD: ")
				                		.append(
				                				auxCell!=null?
				                						new StringBuilder(String.format("%.0f",auxCell.getNumericCellValue())).append("|").toString()
				                						:"|"
				                		).toString());
				                output.print(
				                		auxCell!=null?
				                				new StringBuilder(String.format("%.0f",auxCell.getNumericCellValue())).append("|").toString()
				                				:"|"
				                		);
				                
				                auxDate=row.getCell(4).getDateCellValue();
				                logger.debug(
				                		new StringBuilder("FENACIMI: ")
				                		.append(
				                				auxDate!=null?
				                						new StringBuilder(renderFechas.format(auxDate)).append("|").toString()
				                						:"|"
				                		).toString());
				                output.print(
				                		auxDate!=null?
				                				new StringBuilder(renderFechas.format(auxDate)).append("|").toString()
				                				:"|"
				                		);
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Edad' o 'Fecha de nacimiento' (")
			                			.append("D")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString()
			                			);
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                logger.debug(
				                		new StringBuilder("SEXO: ")
				                		.append(row.getCell(5).getStringCellValue())
				                		.append("|")
				                		.toString());
				                output.print(
				                		new StringBuilder(row.getCell(5).getStringCellValue()).append("|").toString());
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Sexo' (")
			                			.append("F")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString());
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                logger.debug(
				                		new StringBuilder("PARENTESCO: ")
				                		.append(row.getCell(6).getStringCellValue())
				                		.append("|")
				                		.toString());
				                output.print(
				                		new StringBuilder(row.getCell(6).getStringCellValue()).append("|").toString());
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Parentesco' (")
			                			.append("G")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString());
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                auxCell=row.getCell(7);
				                logger.debug(
				                		new StringBuilder("OCUPACION: ")
				                		.append(
				                				auxCell!=null?
				                				new StringBuilder(auxCell.getStringCellValue()).append("|").toString()
				                				:"|"
				                		).toString());
				                output.print(
				                		auxCell!=null?
				                				new StringBuilder(auxCell.getStringCellValue()).append("|").toString()
				                				:"|");
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Ocupacion' (")
			                			.append("H")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString());
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                auxCell=row.getCell(8);
				                logger.debug(
				                		new StringBuilder("EXTRAPRIMA OCUPACION: ")
				                		.append(
				                				auxCell!=null?
				                						new StringBuilder(String.format("%.2f",auxCell.getNumericCellValue())).append("|").toString()
				                						:"|"
				                		).toString());
				                output.print(
				                		auxCell!=null?
				                				new StringBuilder(String.format("%.2f",auxCell.getNumericCellValue())).append("|").toString()
				                				:"|");
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Extraprima de ocupacion' (")
			                			.append("I")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString());
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                auxCell=row.getCell(9);
				                logger.debug(
				                		new StringBuilder("PESO: ")
				                		.append(
				                				auxCell!=null?
				                						new StringBuilder(String.format("%.2f",auxCell.getNumericCellValue())).append("|").toString()
				                						:"|"
				                		).toString());
				                output.print(
				                		auxCell!=null?
				                				new StringBuilder(String.format("%.2f",auxCell.getNumericCellValue())).append("|").toString()
				                				:"|");
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Peso' (")
			                			.append("J")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString());
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                auxCell=row.getCell(10);
				                logger.debug(
				                		new StringBuilder("ESTATURA: ")
				                		.append(
				                				auxCell!=null?
				                						new StringBuilder(String.format("%.2f",auxCell.getNumericCellValue())).append("|").toString()
				                						:"|"
				                		).toString());
				                output.print(
				                		auxCell!=null?
				                				new StringBuilder(String.format("%.2f",auxCell.getNumericCellValue())).append("|").toString()
				                				:"|");
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Estatura' (")
			                			.append("K")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString());
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                auxCell=row.getCell(11);
				                logger.debug(
				                		new StringBuilder("EXTRAPRIMA SOBREPESO: ")
				                		.append(
				                				auxCell!=null?
				                						new StringBuilder(String.format("%.2f",auxCell.getNumericCellValue())).append("|").toString()
				                						:"|"
				                		).toString());
				                output.print(
				                		auxCell!=null?
				                				new StringBuilder(String.format("%.2f",auxCell.getNumericCellValue())).append("|").toString()
				                				:"|");
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Extraprima de sobrepeso' (")
			                			.append("L")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString());
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                logger.debug(
				                		new StringBuilder("GRUPO: ")
				                		.append(
				                				String.format("%.0f",row.getCell(12).getNumericCellValue()))
				                		.append("|")
				                		.toString());
				                output.print(
				                		new StringBuilder(String.format("%.0f",row.getCell(12).getNumericCellValue())).append("|").toString());
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Grupo' (")
			                			.append("M")
			                			.append(") de la fila ")
			                			.append(fila).append(" #")
			                			.append(timestamp)
			                			.toString());
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                output.println("");
		                logger.debug("** NUEVA_FILA **");
		            }
		            
		            try
		            {
		                input.close();
		            }
		            catch(IOException ex)
		            {
		            	long timestamp = System.currentTimeMillis();
		            	resp.setExito(false);
		            	resp.setRespuesta(new StringBuilder("Error en el buffer #").append(timestamp).toString());
		            	resp.setRespuestaOculta(ex.getMessage());
		            	logger.error(resp.getRespuesta(),ex);
		            }
		            
		            output.close();
		            logger.debug(
		            		new StringBuilder()
		            		.append("\n------ ").append(archivoTxt.getAbsolutePath())
							.append("\n----------------------------------------------")
							.toString()
							);
				}
				//pipes para censo agrupado
				else
				{
					//Iterate through each rows one by one
					logger.debug(
							new StringBuilder()
							.append("\n----------------------------------------------")
							.append("\n------ ").append(archivoTxt.getAbsolutePath())
							.toString()
							);
		            Iterator<Row> rowIterator = sheet.iterator();
		            int fila = 0;
		            while (rowIterator.hasNext()&&resp.isExito()) 
		            {
		                Row row = rowIterator.next();
		                
		                fila = fila + 1;
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                logger.debug(
				                		new StringBuilder("EDAD: ")
				                		.append(String.format("%.0f",row.getCell(0).getNumericCellValue())).append("|")
				                		.toString());
				                output.print(
				                		new StringBuilder(String.format("%.0f",row.getCell(0).getNumericCellValue())).append("|").toString());
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Edad' (")
			                			.append("A")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString());
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                logger.debug(
				                		new StringBuilder("SEXO: ").append(row.getCell(1).getStringCellValue()).append("|").toString());
				                output.print(
				                		new StringBuilder(row.getCell(1).getStringCellValue()).append("|").toString());
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Sexo' (").
			                			append("B")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString());
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                logger.debug(
				                		new StringBuilder("CUANTOS: ")
				                		.append(String.format("%.0f",row.getCell(2).getNumericCellValue()))
				                		.append("|")
				                		.toString());
				                output.print(
				                		new StringBuilder(String.format("%.0f",row.getCell(2).getNumericCellValue())).append("|").toString());
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Cantidad' (")
			                			.append("C")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString());
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                if(resp.isExito())
		                {
		                	try
		                	{
				                logger.debug(
				                		new StringBuilder("GRUPO: ")
				                		.append(String.format("%.0f",row.getCell(3).getNumericCellValue()))
				                		.append("|")
				                		.toString());
				                output.print(
				                		new StringBuilder(String.format("%.0f",row.getCell(3).getNumericCellValue())).append("|").toString());
			                }
			                catch(Exception ex)
			                {
			                	long timestamp = System.currentTimeMillis();
			                	resp.setExito(false);
			                	resp.setRespuesta(
			                			new StringBuilder("Error en el campo 'Grupo' (")
			                			.append("D")
			                			.append(") de la fila ")
			                			.append(fila)
			                			.append(" #")
			                			.append(timestamp)
			                			.toString());
			                	resp.setRespuestaOculta(ex.getMessage());
			                	logger.error(resp.getRespuesta(),ex);
			                }
		                }
		                
		                output.println("");
		                logger.debug("** NUEVA_FILA **");
		            }
		            
		            try
		            {
		            	input.close();
		            }
		            catch(IOException ex)
		            {
		            	long timestamp = System.currentTimeMillis();
		            	resp.setExito(false);
		            	resp.setRespuesta(new StringBuilder("Error en el buffer #").append(timestamp).toString());
		            	resp.setRespuestaOculta(ex.getMessage());
		            	logger.error(resp.getRespuesta(),ex);
		            }
		            
		            output.close();
		            logger.debug(
		            		new StringBuilder()
		            		.append("\n------ ").append(archivoTxt.getAbsolutePath())
							.append("\n----------------------------------------------")
		            		.toString()
							);
				}
				
				//enviar archivo
				if(resp.isExito())
				{
					resp.setExito(FTPSUtils.upload(
							dominioServerLayouts,
							userServerLayouts,
							passServerLayouts,
							archivoTxt.getAbsolutePath(),
							new StringBuilder(directorioServerLayouts).append("/").append(nombreCenso).toString())
							);
					
					if(!resp.isExito())
					{
						long timestamp = System.currentTimeMillis();
						resp.setExito(false);
						resp.setRespuesta(
								new StringBuilder("Error al transferir archivo al servidor #").append(timestamp).toString());
						resp.setRespuestaOculta(resp.getRespuesta());
						logger.error(resp.getRespuesta());
					}
				}
			}
		}
		
		//pl censo
		if(resp.isExito()&&(!hayTramite||hayTramiteVacio))
		{
			String nombreProcedureCenso = null;
			String tipoCensoParam       = "INDIVIDUAL";
			if(!esCensoSolo)
			{
				tipoCensoParam = "AGRUPADO";
			}
			
			//obtener el PL
			try
			{
				Map<String,String>mapaAux=cotizacionDAO.obtenerParametrosCotizacion(
						ParametroCotizacion.PROCEDURE_CENSO
						,cdramo
						,cdtipsit
						,tipoCensoParam
						,null
						);
				nombreProcedureCenso = mapaAux.get("P1VALOR");
				if(StringUtils.isBlank(nombreProcedureCenso))
				{
					throw new ApplicationException("No se encontraron datos");
				}
			}
            catch(ApplicationException ax)
            {
            	long timestamp = System.currentTimeMillis();
            	resp.setExito(false);
            	resp.setRespuesta(
            			new StringBuilder("Error al obtener el nombre del procedimiento del censo: ")
            			.append(ax.getMessage())
            			.append(" #")
            			.append(timestamp)
            			.toString());
            	resp.setRespuestaOculta(ax.getMessage());
            	logger.error(resp.getRespuesta(),ax);
            }
            catch(Exception ex)
            {
            	long timestamp = System.currentTimeMillis();
            	resp.setExito(false);
            	resp.setRespuesta(new StringBuilder("Error al obtener el nombre del procedimiento para el censo #").append(timestamp).toString());
            	resp.setRespuestaOculta(ex.getMessage());
            	logger.error(resp.getRespuesta(),ex);
            }
			
			//ejecutar el PL
			if(resp.isExito())
			{
				try
				{
					cotizacionDAO.procesarCenso(
							nombreProcedureCenso
							,cdusuari
							,cdsisrol
							,nombreCenso
							,cdunieco
							,cdramo
							,"W"
							,nmpoliza
							,cdtipsit
							,cdagente
							,codpostalCli
							,cdedoCli
							,cdmuniciCli
							);
				}
	            catch(Exception ex)
	            {
	            	long timestamp = System.currentTimeMillis();
	            	resp.setExito(false);
	            	resp.setRespuesta(new StringBuilder("Error al ejecutar procedimiento del censo #").append(timestamp).toString());
	            	resp.setRespuestaOculta(ex.getMessage());
	            	logger.error(resp.getRespuesta(),ex);
	            }
			}
		}
		
		//mpolisit y tvalosit
		if(resp.isExito())
		{
			try
			{
				for(Map<String,Object>grupoIte:grupos)
				{
					String grupoIteCdgrupo = (String)grupoIte.get("letra");
					String grupoIteCdplan  = (String)grupoIte.get("cdplan");
					String grupoIteNombre  = (String)grupoIte.get("nombre");
					
					Map<String,String>grupoIteValoresSit = new HashMap<String,String>();
					for(Entry<String,Object>grupoIteAtribIte:grupoIte.entrySet())
					{
						String key = grupoIteAtribIte.getKey();
						if(StringUtils.isNotBlank(key)
								&&key.length()>="parametros.pv_".length()
								&&key.substring(0, "parametros.pv_".length()).equals("parametros.pv_")
								)
						{
							grupoIteValoresSit.put(key.substring("parametros.pv_".length(), key.length()),String.valueOf(grupoIteAtribIte.getValue()));
						}
					}
					
					cotizacionDAO.actualizaMpolisitTvalositGrupo(
							cdunieco
							,cdramo
							,"W"
							,nmpoliza
							,grupoIteCdgrupo
							,grupoIteNombre
							,grupoIteCdplan
							,grupoIteValoresSit
							);
				}
			}
            catch(Exception ex)
            {
            	long timestamp = System.currentTimeMillis();
            	resp.setExito(false);
            	resp.setRespuesta(new StringBuilder("Error al actualizar grupos #").append(timestamp).toString());
            	resp.setRespuestaOculta(ex.getMessage());
            	logger.error(resp.getRespuesta(),ex);
            }
		}
		
		//sigsvdef
		if(resp.isExito()&&(!hayTramite||hayTramiteVacio))
		{
			try
			{
				cotizacionDAO.valoresPorDefecto(
						cdunieco
						,cdramo
						,"W"
						,nmpoliza
						,"0"    //nmsituac
						,"0"    //nmsuplem
						,"TODO" //cdgarant
						,"1"    //cdtipsup
						);
			}
            catch(Exception ex)
            {
            	long timestamp = System.currentTimeMillis();
            	resp.setExito(false);
            	resp.setRespuesta(new StringBuilder("Error al insertar valores por defecto #").append(timestamp).toString());
            	resp.setRespuestaOculta(ex.getMessage());
            	logger.error(resp.getRespuesta(),ex);
            }
		}
		
		final String LINEA = "1";
		
		//tvalogar
		if(resp.isExito()&&(
				(!clasif.equals(LINEA))
				||LINEA_EXTENDIDA.equals("N")
				)
				)
		{
			try
			{
				for(Map<String,Object>grupoIte:grupos)
				{
					String grupoIteCdgrupo                     = (String)grupoIte.get("letra");
					List<Map<String,String>>grupoIteCoberturas = (List<Map<String,String>>)grupoIte.get("tvalogars");
					for(Map<String,String>grupoIteCoberturaIte:grupoIteCoberturas)
					{
						String grupoIteCoberturaIteCdgarant = grupoIteCoberturaIte.get("cdgarant");
						boolean grupoIteCoberturaIteAmparada = StringUtils.isNotBlank(grupoIteCoberturaIte.get("amparada"))
								&&grupoIteCoberturaIte.get("amparada").equalsIgnoreCase("S");
						if(grupoIteCoberturaIteAmparada)
						{
							cotizacionDAO.movimientoMpoligarGrupo(
									cdunieco
									,cdramo
									,"W"      //estado
									,nmpoliza
									,"0"      //nmsuplem
									,cdtipsit
									,grupoIteCdgrupo
									,grupoIteCoberturaIteCdgarant
									,"V"      //status
									,"001"    //cdmoneda
									,Constantes.INSERT_MODE
									);
							boolean grupoIteCoberturaIteTieneAtrib          = false;
							Map<String,String>grupoIteCoberturaIteTvalogars = new HashMap<String,String>();
							for(Entry<String,String>grupoIteCoberturaIteAtribIte:grupoIteCoberturaIte.entrySet())
							{
								String grupoIteCoberturaIteAtribIteKey=grupoIteCoberturaIteAtribIte.getKey();
								if(StringUtils.isNotBlank(grupoIteCoberturaIteAtribIteKey)
										&&grupoIteCoberturaIteAtribIteKey.length()>"parametros.pv_otvalor".length()
										&&grupoIteCoberturaIteAtribIteKey.substring(0, "parametros.pv_otvalor".length()).equalsIgnoreCase("parametros.pv_otvalor")
										&&grupoIteCoberturaIteAtribIte.getValue()!=null
										)
								{
									grupoIteCoberturaIteTieneAtrib=true;
									grupoIteCoberturaIteTvalogars.put(
											grupoIteCoberturaIteAtribIteKey.substring("parametros.pv_".length()
													,grupoIteCoberturaIteAtribIteKey.length()),String.valueOf(grupoIteCoberturaIteAtribIte.getValue()));
								}
							}
							if(grupoIteCoberturaIteTieneAtrib)
							{
								cotizacionDAO.movimientoTvalogarGrupoCompleto(
										cdunieco
										,cdramo
										,"W"      //estado
										,nmpoliza
										,"0"      //nmsuplem
										,cdtipsit
										,grupoIteCdgrupo
										,grupoIteCoberturaIteCdgarant
										,"V"      //status
										,grupoIteCoberturaIteTvalogars
										);
							}
						}
					}
				}
			}
            catch(Exception ex)
            {
            	long timestamp = System.currentTimeMillis();
            	resp.setExito(false);
            	resp.setRespuesta(new StringBuilder("Error al guardar atributos de coberturas #").append(timestamp).toString());
            	resp.setRespuestaOculta(ex.getMessage());
            	logger.error(resp.getRespuesta(),ex);
            }
		}
		
		//contratante
		if(resp.isExito())
		{
			try
			{
				String swexiper = "S";
				if(StringUtils.isBlank(cdpersonCli))
				{
					cdpersonCli = personasDAO.obtenerNuevoCdperson();
					swexiper = "N";
				}
				
				if(swexiper.equals("N")||true)//TODO hace falta separar en otro metodo con "reinsertaContratante"
				{
					personasDAO.movimientosMpersona(
							cdpersonCli
							,"1"         //cdtipide
							,null        //cdideper
							,nombreCli
							,"1"         //cdtipper
							,"M"         //otfisjur
							,"H"         //otsexo
							,new Date()  //fenacimi
							,rfcCli
							,""          //dsemail
							,null        //dsnombre1
							,null        //dsapellido
							,null        //dsapellido1
							,new Date()  //feingreso
							,null        //cdnacion
							,null        //canaling
							,null        //conducto
							,null        //ptcumupr
							,null        //residencia
							,Constantes.INSERT_MODE
							);
				}
				
				cotizacionDAO.movimientoMpoliper(
						cdunieco
						,cdramo
						,"W"
						,nmpoliza
						,"0"       //nmsituac
						,"1"       //cdrol
						,cdpersonCli
						,"0"       //nmsuplem
						,"V"       //status
						,"1"       //nmorddom
						,null      //swreclam
						,Constantes.INSERT_MODE
						,swexiper
						);
				
				personasDAO.movimientosMdomicil(
						cdpersonCli
						,"1"        //nmorddom
						,dsdomiciCli
						,null       //nmtelefo
						,codpostalCli
						,cdedoCli
						,cdmuniciCli
						,null       //cdcoloni
						,nmnumeroCli
						,nmnumintCli
						,Constantes.INSERT_MODE
						);
			}
            catch(Exception ex)
            {
            	long timestamp = System.currentTimeMillis();
            	resp.setExito(false);
            	resp.setRespuesta(new StringBuilder("Error al guardar el contratante #").append(timestamp).toString());
            	resp.setRespuestaOculta(ex.getMessage());
            	logger.error(resp.getRespuesta(),ex);
            }
		}
		
		//tramite
		if(resp.isExito()&&(!hayTramite||hayTramiteVacio))
		{
			try
			{
				if(!hayTramiteVacio)//es agente
				{
					Map<String,String>otvalorMesaControl=new HashMap<String,String>();
					otvalorMesaControl.put("otvalor01",clasif);
					ntramite = mesaControlDAO.movimientoMesaControl(
							cdunieco
							,cdramo
							,"W"       //estado
							,"0"
							,"0"       //nmsuplem
							,cdunieco
							,cdunieco
							,TipoTramite.POLIZA_NUEVA.getCdtiptra()
							,new Date()
							,cdagente
							,null      //referencia
							,null      //nombre
							,new Date()
							,EstatusTramite.EN_ESPERA_DE_COTIZACION.getCodigo()
							,null      //comments
							,nmpoliza
							,cdtipsit
							,otvalorMesaControl);
					resp.getSmap().put("ntramite" , ntramite);
					
					mesaControlDAO.movimientoDetalleTramite(
							ntramite
							,new Date()
							,null       //cdclausu
							,"Se guard&oacute; un nuevo tr&aacute;mite en mesa de control desde cotizaci&oacute;n de agente"
							,cdusuari
							,null       //cdmotivo
							);
				}
				else
				{
					mesaControlDAO.actualizarNmsoliciTramite(ntramiteVacio, nmpoliza);
					
					Map<String,String>valoresTramite=new HashMap<String,String>();
					valoresTramite.put("otvalor01" , clasif);
					mesaControlDAO.actualizaValoresTramite(
							ntramiteVacio
							,null    //cdramo
							,null    //cdtipsit
							,null    //cdsucadm
							,null    //cdsucdoc
							,null    //comments
							,valoresTramite);
				}
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
            	resp.setExito(false);
            	resp.setRespuesta(new StringBuilder("Error al guardar tramite #").append(timestamp).toString());
            	resp.setRespuestaOculta(ex.getMessage());
            	logger.error(resp.getRespuesta(),ex);
			}
		}
		
		//mpoliage
		if(resp.isExito()&&(!hayTramite||hayTramiteVacio))
		{
			try
			{
				Map<String,String>datosAgenteExterno=cotizacionDAO.obtenerDatosAgente(cdagente,cdramo);
    			String nmcuadro=datosAgenteExterno.get("NMCUADRO");
    			
    			String paramNtramite = ntramite;
    			if(hayTramiteVacio)
    			{
    				paramNtramite = ntramiteVacio;
    			}
				
    			cotizacionDAO.movimientoMpoliage(
    					cdunieco
    					,cdramo
    					,"W"      //estado
    					,nmpoliza
    					,cdagente
    					,"0"      //nmsuplem
    					,"V"      //status
    					,"1"      //cdtipoag
    					,"0"      //porredau
    					,nmcuadro
    					,null     //cdsucurs
    					,Constantes.INSERT_MODE
    					,paramNtramite
    					,"100"    //porparti
    					);
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
            	resp.setExito(false);
            	resp.setRespuesta(new StringBuilder("Error al guardar datos del agente #").append(timestamp).toString());
            	resp.setRespuestaOculta(ex.getMessage());
            	logger.error(resp.getRespuesta(),ex);
			}
		}
		
		//sigsvalipol
		if(resp.isExito())
		{
			try
			{
				cotizacionDAO.tarificaEmi(
						cdusuari
						,cdelemen
						,cdunieco
						,cdramo
						,"W"       //estado
						,nmpoliza
						,"0"       //nmstiuac
						,"0"       //nmsuplem
						,cdtipsit);
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
            	resp.setExito(false);
            	resp.setRespuesta(new StringBuilder("Error al tarificar #").append(timestamp).toString());
            	resp.setRespuestaOculta(ex.getMessage());
            	logger.error(resp.getRespuesta(),ex);
			}
		}
		
		if(resp.isExito())
		{
			resp.setRespuesta(new StringBuilder("Se gener&oacute; el tr&aacute;mite ").append(ntramite).toString());
			resp.setRespuestaOculta("Todo OK");
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ generarTramiteGrupo @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	public ManagerRespuestaSlistVO obtenerTiposSituacion()
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ obtenerTiposSituacion @@@@@@")
				.toString()
				);
		
		ManagerRespuestaSlistVO resp = new ManagerRespuestaSlistVO(true);
		
		try
		{
			resp.setSlist(cotizacionDAO.obtenerTiposSituacion());
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al obtener tipos de situacion #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ obtenerTiposSituacion @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSlistVO cargarAseguradosExtraprimas2(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdgrupo
			)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarAseguradosExtraprimas2 @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.append("\n@@@@@@ cdgrupo=") .append(cdgrupo)
				.toString()
				);
		
		ManagerRespuestaSlistVO resp = new ManagerRespuestaSlistVO(true);
		
		//cargar situaciones grupo
		try
		{
			List<Map<String,String>>situaciones=cotizacionDAO.cargarSituacionesGrupo(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsuplem
					,cdgrupo);
			
			List<Map<String,String>>editadas=new ArrayList<Map<String,String>>();
			for(Map<String,String>situacion:situaciones)
		    {
		    	String tpl = null;
		    	if(StringUtils.isBlank(situacion.get("titular")))
		    	{
		    		tpl = "Asegurados";
		    	}
		    	else
		    	{
		    		tpl = new StringBuilder()
    	                    .append("Familia (")
    	                    .append(situacion.get("familia"))
    	                    .append(") de ")
    	                    .append(situacion.get("titular"))
    	            		.toString();
		    	}
		    	situacion.put("agrupador",
		    			new StringBuilder()
		    	            .append(StringUtils.leftPad(situacion.get("familia"),3,"0"))
		    	            .append("_")
		    	            .append(tpl)
		    	            .toString());
		    	
		    	Map<String,String>editada=new HashMap<String,String>();
		    	for(Entry<String,String>en:situacion.entrySet())
		    	{
		    		String key = en.getKey();
		    		if(StringUtils.isNotBlank(key)
		    				&&key.length()>"otvalor".length()
		    				&&key.substring(0, "otvalor".length()).equals("otvalor")
		    				)
		    		{
		    			editada.put(new StringBuilder("parametros.pv_").append(key).toString(),en.getValue());
		    		}
		    		else
		    		{
		    			editada.put(key,en.getValue());
		    		}
		    	}
		    	editadas.add(editada);
		    }
			
			resp.setSlist(editadas);
		}
		catch(DaoException dx)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al obtener situaciones #").append(timestamp).toString());
			resp.setRespuestaOculta(dx.getMessage());
			logger.error(resp.getRespuesta(),dx);
		}
		catch(ApplicationException ax)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString());
			resp.setRespuestaOculta(ax.getMessage());
			logger.error(resp.getRespuesta(),ax);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarAseguradosExtraprimas2 @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	public ManagerRespuestaVoidVO guardarValoresSituaciones(List<Map<String,String>>situaciones)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ guardarValoresSituaciones @@@@@@")
				.append("\n@@@@@@ situaciones=").append(situaciones)
				.toString()
				);
		
		ManagerRespuestaVoidVO resp = new ManagerRespuestaVoidVO(true);
		
		//actualizar situaciones
		try
		{
			for(Map<String,String>situacion:situaciones)
			{
				Map<String,String>valores=new HashMap<String,String>();
				for(Entry<String,String>en:situacion.entrySet())
				{
					String key = en.getKey();
					if(StringUtils.isNotBlank(key)
							&&key.length()>"otvalor".length()
							&&key.substring(0,"otvalor".length()).equals("otvalor")
							)
					{
						valores.put(key,en.getValue());
					}
				}
				cotizacionDAO.actualizaValoresSituacion(
						situacion.get("cdunieco")
						,situacion.get("cdramo")
						,situacion.get("estado")
						,situacion.get("nmpoliza")
						,situacion.get("nmsuplem")
						,situacion.get("nmsituac")
						,valores
						);
			}
			resp.setRespuesta("Se guardaron todos los datos");
		}
		catch(DaoException dx)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al guardar situacion #").append(timestamp).toString());
			resp.setRespuestaOculta(dx.getMessage());
			logger.error(resp.getRespuesta(),dx);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ guardarValoresSituaciones @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	///////////////////////////////
	////// getters y setters //////
	public void setCotizacionDAO(CotizacionDAO cotizacionDAO) {
		this.cotizacionDAO = cotizacionDAO;
	}

	public void setPantallasDAO(PantallasDAO pantallasDAO) {
		this.pantallasDAO = pantallasDAO;
	}

	public void setPersonasDAO(PersonasDAO personasDAO) {
		this.personasDAO = personasDAO;
	}

	public void setMesaControlDAO(MesaControlDAO mesaControlDAO) {
		this.mesaControlDAO = mesaControlDAO;
	}
	
}