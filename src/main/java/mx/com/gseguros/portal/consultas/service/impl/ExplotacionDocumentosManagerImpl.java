package mx.com.gseguros.portal.consultas.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.consultas.service.ExplotacionDocumentosManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.emision.dao.EmisionDAO;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
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
	
	@Autowired
	private ConsultasDAO consultasDAO;
	
	@Autowired
	private MesaControlDAO mesaControlDAO;
	
	@Autowired
	private EmisionDAO emisionDAO;
	
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
	
	@Override
	public String generarLote(
			String cdusuari
			,String cdsisrol
			,String cdtipram
			,String cdtipimp
			,List<Map<String, String>> movs
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ generarLote @@@@@@"
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				,"\n@@@@@@ cdtipram=" , cdtipram
				,"\n@@@@@@ cdtipimp=" , cdtipimp
				,"\n@@@@@@ movs="     , movs
				));
		
		String lote  = null
			   ,paso = null;
		
		try
		{
			paso = "Generando secuencia";
			logger.debug("@@@@@@ paso: {}",paso);
			
			lote = consultasDAO.recuperarSecuenciaLote();
			
			paso = "Agrupando movimientos por agente";
			logger.debug("@@@@@@ paso: {}",paso);
			
			Map<String,Object> agentes = new HashMap<String,Object>();
			
			for(Map<String,String>mov:movs)
			{
				String cdagente = mov.get("cdagente");
				if(!agentes.containsKey(cdagente))
				{
					agentes.put(cdagente,new ArrayList<Map<String,String>>());
					logger.debug("@@@@@@ se crea el agrupador para el agente {}",cdagente);
				}
				logger.debug("@@@@@@ se agrega mov. al agente {}",cdagente);
				((List<Map<String,String>>)agentes.get(cdagente)).add(mov);
			}
			
			paso = "Recuperando impresiones disponibles";
			logger.debug("@@@@@@ paso: {}",paso);
			
			String impDis = consultasDAO.recuperarImpresionesDisponiblesPorTipoRamo(cdtipram,"P");
			
			paso = "Generando tr\u00E1mites de agentes";
			logger.debug("@@@@@@ paso: {}",paso);
			
			Map<String,String> valores = new HashMap<String,String>();
			valores.put("otvalor01" , lote);
			valores.put("otvalor02" , cdtipimp);
			valores.put("otvalor03" , cdtipram);
			valores.put("otvalor04" , impDis); //impresiones disponibles
			valores.put("otvalor05" , "0");    //impresiones ejecutadas
			valores.put("otvalor06" , "P");    //POLIZA - RECIBO
			
			for(Entry<String,Object>agente:agentes.entrySet())
			{
				String                   cdagente   = agente.getKey();
				List<Map<String,String>> movsAgente = (List<Map<String,String>>)agente.getValue();
				
				paso = "Generando tr\u00E1mite iterado";
				logger.debug("@@@@@@ paso: {}",paso);
				
				String ntramite = mesaControlDAO.movimientoMesaControl(
						null  //cdunieco
						,null //cdramo
						,null //estado
						,null //nmpoliza
						,null //nmsuplem
						,null //cdsucadm
						,null //cdsucdoc
						,TipoTramite.IMPRESION.getCdtiptra()
						,new Date() //ferecepc
						,cdagente
						,null //referencia
						,null //nombre
						,new Date() //festatus
						,EstatusTramite.IMPRESION_PENDIENTE.getCodigo()
						,null //comments
						,null //nmsolici
						,null //cdtipsit
						,valores
						,cdusuari
						,cdsisrol
						,null //swimpres
						);
				
				mesaControlDAO.movimientoDetalleTramite(
						ntramite
						,new Date()                //feinicio
						,null                      //cdclausu
						,"Nuevo registro de lote"  //comments
						,cdusuari
						,null                      //cdmotivo
						,cdsisrol
						);
				
				for(Map<String,String>movAgente:movsAgente)
				{
					paso = "Registrando relaci\u00F3n de movimiento iterado";
					logger.debug("@@@@@@ paso: {}",paso);
					
					emisionDAO.insertarMpoliimp(
							ntramite
							,movAgente.get("cdunieco")
							,movAgente.get("cdramo")
							,movAgente.get("estado")
							,movAgente.get("nmpoliza")
							,movAgente.get("nmsuplem")
							,"P"
							,movAgente.get("ntramite")
							,null
							);
					
					paso = "Marcando tr\u00E1mite original iterado";
					logger.debug("@@@@@@ paso: {}",paso);
					
					emisionDAO.marcarTramiteImpreso(
							movAgente.get("ntramite")
							,"S"
							);
				}
			}
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ lote=",lote
			    ,"\n@@@@@@ generarLote @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return lote;
	}
	
	@Override
	public void imprimirLote(
			String lote
			,String hoja
			,String peso
			,String cdtipram
			,String cdtipimp
			,String tipolote
			,String cdunieco
			,String ip
			,String nmimpres
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@"
			    ,"\n@@@@@@ imprimirLote @@@@@@"
			    ,"\n@@@@@@ lote="     , lote
			    ,"\n@@@@@@ hoja="     , hoja
			    ,"\n@@@@@@ peso="     , peso
			    ,"\n@@@@@@ cdtipram=" , cdtipram
			    ,"\n@@@@@@ cdtipimp=" , cdtipimp
			    ,"\n@@@@@@ tipolote=" , tipolote
			    ,"\n@@@@@@ cdunieco=" , cdunieco
			    ,"\n@@@@@@ ip="       , ip
			    ,"\n@@@@@@ nmimpres=" , nmimpres
				));
		
		String paso = "Iniciando impresi\u00F3n";
		try
		{
			boolean blanca = hoja.indexOf("B")!=-1;
			
			if(blanca)
			{
				//
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
			     "\n@@@@@@ imprimirLote @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
	
	@Override
	public Map<String,Item> pantallaExplotacionRecibos(String cdusuari, String cdsisrol) throws Exception
	{
		logger.debug(Utils.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ pantallaExplotacionRecibos @@@@@@"
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
					,"EXPLOTACION_RECIBOS"
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
					,"EXPLOTACION_RECIBOS"
					,"GRID_RECIBOS"
					,null //orden
					);
			
			paso = "Construyendo componentes";
			logger.debug(paso);
			
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			gc.generaComponentes(itemsFormBusq, true, false, true, false, false, false);
			items.put("itemsFormBusq" , gc.getItems());
			
			gc.generaComponentes(compsGridPolizas, true, true, false, true, false, false);
			items.put("gridRecibosFields" , gc.getFields());
			items.put("gridRecibosCols"   , gc.getColumns());
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.join(
				 "\n@@@@@@ pantallaExplotacionRecibos @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	@Override
	public Map<String,Item> pantallaPermisosImpresion(String cdusuari, String cdsisrol) throws Exception
	{
		logger.debug(Utils.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ pantallaPermisosImpresion @@@@@@"
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
					,"EXPLOTACION_PERMISOS"
					,"FORM_BUSQUEDA"
					,null //orden
					);
			
			List<ComponenteVO> itemsAgregarPerm = pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,cdsisrol
					,"EXPLOTACION_PERMISOS"
					,"FORM_PERMISO"
					,null //orden
					);
			
			paso = "Construyendo componentes";
			logger.debug(paso);
			
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			
			gc.generaComponentes(itemsFormBusq, true, false, true, false, false, false);
			items.put("itemsFormBusq" , gc.getItems());
			
			gc.generaComponentes(itemsAgregarPerm, true, false, true, false, false, false);
			items.put("itemsAgregarPerm" , gc.getItems());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.join(
				 "\n@@@@@@ pantallaPermisosImpresion @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return items;
	}
	
	@Override
	public void movPermisoImpresion(
			String tipo
			,String cdusuari
			,String cdunieco
			,String cdtipram
			,String clave
			,String funcion
			,String accion
			)throws Exception
	{
		logger.debug(Utils.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ movPermisoImpresion @@@@@@"
				,"\n@@@@@@ tipo="     , tipo
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdtipram=" , cdtipram
				,"\n@@@@@@ clave="    , clave
				,"\n@@@@@@ funcion="  , funcion
				,"\n@@@@@@ accion="   , accion
				));
		String paso = null;
		try
		{
			paso = "Borrando permiso";
			logger.debug("@@@@@@ paso: {}",paso);
			
			if("S".equals(tipo))
			{
				consultasDAO.movPermisoImpresionSucursal(
						cdusuari
						,cdunieco
						,cdtipram
						,clave
						,funcion
						,accion
						);
			}
			else if("A".equals(tipo))
			{
				consultasDAO.movPermisoImpresionAgente(
						cdusuari
						,cdunieco
						,cdtipram
						,clave
						,funcion
						,accion
						);
			}
			else
			{
				throw new ApplicationException("Tipo de permiso no soportado");
			}
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
	}
}