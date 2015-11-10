package mx.com.gseguros.portal.consultas.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.consultas.model.DocumentoReciboParaMostrarDTO;
import mx.com.gseguros.portal.consultas.service.ExplotacionDocumentosManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.emision.dao.EmisionDAO;
import mx.com.gseguros.portal.general.dao.CatalogosDAO;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.Reporte;
import mx.com.gseguros.portal.general.service.ImpresionService;
import mx.com.gseguros.portal.general.service.ReportesManager;
import mx.com.gseguros.portal.general.util.Catalogos;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.TipoArchivo;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Autowired
	private CatalogosDAO catalogosDAO;
	
	@Autowired
	private ReportesManager reportesManager;
	
	@Autowired
	private ImpresionService impresionService;
	
	@Value("${ruta.documentos.poliza}")
	private String rutaDocumentosPoliza;
	
	@Value("${ruta.documentos.temporal}")
	private String rutaDocumentosTemporal;
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
			,String tipolote
			,List<Map<String, String>> movs
			,String rutaDocumentosPoliza
			,String rutaServidorReports
			,String passServidorReports
			,String nombreReporteRemesa
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ generarLote @@@@@@"
				,"\n@@@@@@ cdusuari="             , cdusuari
				,"\n@@@@@@ cdsisrol="             , cdsisrol
				,"\n@@@@@@ cdtipram="             , cdtipram
				,"\n@@@@@@ cdtipimp="             , cdtipimp
				,"\n@@@@@@ tipolote="             , tipolote
				,"\n@@@@@@ movs="                 , movs
				,"\n@@@@@@ rutaDocumentosPoliza=" , rutaDocumentosPoliza
				,"\n@@@@@@ rutaServidorReports="  , rutaServidorReports
				,"\n@@@@@@ passServidorReports="  , passServidorReports
				,"\n@@@@@@ nombreReporteRemesa="  , nombreReporteRemesa
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
			
			String impDis = consultasDAO.recuperarImpresionesDisponiblesPorTipoRamo(cdtipram,tipolote);
			
			paso = "Generando tr\u00E1mites de agentes";
			logger.debug("@@@@@@ paso: {}",paso);
			
			Map<String,String> valores = new HashMap<String,String>();
			valores.put("otvalor01" , lote);
			valores.put("otvalor02" , cdtipimp);
			valores.put("otvalor03" , cdtipram);
			valores.put("otvalor04" , impDis); //impresiones disponibles
			valores.put("otvalor05" , "0");    //impresiones ejecutadas
			valores.put("otvalor06" , tipolote);    //POLIZA - RECIBO
			
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
							,tipolote
							,"P".equals(tipolote) ? movAgente.get("ntramite") : null
							,"R".equals(tipolote) ? movAgente.get("nmrecibo") : null
							);
					
					if("P".equals(tipolote))
					{
						paso = "Marcando tr\u00E1mite original iterado";
						logger.debug("@@@@@@ paso: {}",paso);
						
						emisionDAO.marcarTramiteImpreso(
								movAgente.get("ntramite")
								,"S"
								);
					}
				}
				
				paso = "Generando carpeta de tr\u00E1mite de agentes";
				logger.debug("@@@@@@ paso: {}",paso);
				
				String rutaCarpeta = Utils.join(rutaDocumentosPoliza,"/",ntramite);
				File   carpeta     = new File(rutaCarpeta);
				if(!carpeta.exists())
				{
					logger.info(Utils.log("@@@@@@ Se va a crear la carpeta ",carpeta));
					if(!carpeta.mkdir())
					{
						throw new ApplicationException("No se pudo crear la carpeta para los documentos");
					}
				}
				
				paso = "Generando remesa pdf";
				logger.debug("@@@@@@ paso: {}",paso);
				
				String urlReporteCotizacion = Utils.join(
						  rutaServidorReports
						, "?p_lote="     , lote
						, "&p_usr_imp="  , cdusuari
						, "&p_ntramite=" , ntramite
	                    , "&destype=cache"
	                    , "&desformat=PDF"
	                    , "&userid="        , passServidorReports
	                    , "&ACCESSIBLE=YES"
	                    , "&report="        , nombreReporteRemesa
	                    , "&paramform=no"
	                    );
				
				String pathRemesa=Utils.join(
						rutaDocumentosPoliza
						,"/",ntramite
						,"/remesa.pdf"
						);
				
				HttpUtil.generaArchivo(urlReporteCotizacion, pathRemesa);
				
				mesaControlDAO.guardarDocumento(
						null          //cdunieco
						,null         //cdramo
						,null         //estado
						,null         //nmpoliza
						,null         //nmsuplem
						,new Date()   //feinici
						,"remesa.pdf" //cddocume
						,"REMESA PDF" //dsdocume
						,null         //nmsolici
						,ntramite
						,"1"          //tipmov
						,null         //swvisible
						,null         //codidocu
						,TipoTramite.IMPRESION.getCdtiptra()
						,"0"
						,"59" // <<< 59 es el CDMODDOCU de REMESA
						);
				
				paso = "Generando remesa excel";
				logger.debug("@@@@@@ paso: {}",paso);
				
				Map<String,String> paramsExcel = new LinkedHashMap<String,String>();
				paramsExcel.put("pv_lote_i"     , lote);
				paramsExcel.put("pv_usr_imp_i"  , cdusuari);
				paramsExcel.put("pv_ntramite_i" , ntramite);
				
				InputStream excel = reportesManager.obtenerDatosReporte(Reporte.REMESA.getCdreporte()
						,cdusuari
						,paramsExcel
						);
				
				String nombreExcel = Utils.join("remesa",TipoArchivo.XLS.getExtension());
				
				FileUtils.copyInputStreamToFile(excel, new File(Utils.join(
								rutaDocumentosPoliza,"/",ntramite,"/",nombreExcel
				)));
				
				mesaControlDAO.guardarDocumento(
						null            //cdunieco
						,null           //cdramo
						,null           //estado
						,null           //nmpoliza
						,null           //nmsuplem
						,new Date()     //feinici
						,nombreExcel    //cddocume
						,"REMESA EXCEL" //dsdocume
						,null           //nmsolici
						,ntramite
						,"1"            //tipmov
						,null           //swvisible
						,null           //codidocu
						,TipoTramite.IMPRESION.getCdtiptra()
						,null           //cdorddoc
						,null           //cdmoddoc
						);
				
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
			,String dsimpres
			,String charola1
			,String charola2
			,String cdusuari
			,String cdsisrol
			,boolean test
			)throws Exception
	{
		StringBuilder sb = new StringBuilder(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@"
			    ,"\n@@@@@@ imprimirLote @@@@@@"
			    ,"\n@@@@@@ lote="     , lote
			    ,"\n@@@@@@ hoja="     , hoja
			    ,"\n@@@@@@ peso="     , peso
			    ,"\n@@@@@@ cdtipram=" , cdtipram
			    ,"\n@@@@@@ cdtipimp=" , cdtipimp
			    ,"\n@@@@@@ tipolote=" , tipolote
			    ,"\n@@@@@@ dsimpres=" , dsimpres
			    ,"\n@@@@@@ charola1=" , charola1
			    ,"\n@@@@@@ charola2=" , charola2
			    ,"\n@@@@@@ cdusuari=" , cdusuari
			    ,"\n@@@@@@ cdsisrol=" , cdsisrol
			    ,"\n@@@@@@ test="     , test
				));
		
		String paso = "Iniciando impresi\u00F3n";
		sb.append("\n").append(paso);
		
		try
		{
			//TODO si el papel es multiple hay que mandar por charola especifica (B por la 1, M por la 2)
			if(hoja.length()>1)
			{
				throw new ApplicationException("No soporta intercalada, consulte a desarrollo");
			}
			
			paso = "Recuperando archivos";
			sb.append("\n").append(paso);
			
			List<Map<String,String>> listaArchivos = consultasDAO.recuperarArchivosParaImprimirLote(
					lote
					,hoja
					,tipolote
					);
			
			sb.append(Utils.log("\nlista=",listaArchivos));
			
			paso = "Imprimiendo archivos";
			sb.append("\n").append(paso);
			
			boolean apagado = false;
			
			for(Map<String,String>archivo:listaArchivos)
			{
				if(!apagado)
				{
					String ntramite = archivo.get("ntramite");
					String cddocume = archivo.get("cddocume");
					String filePath = Utils.join(rutaDocumentosPoliza,"/",ntramite,"/",cddocume);
					
					sb.append(Utils.log("\ntramite,archivo=",ntramite,cddocume));
					
					if(cddocume.toLowerCase().indexOf("://")!=-1)
					{
						paso = "Descargando archivo remoto";
						sb.append("\n").append(paso);
						long timestamp = System.currentTimeMillis();
						long rand      = new Double(1000d*Math.random()).longValue();
						filePath       = Utils.join(
								rutaDocumentosTemporal
								,"/lote_"    , lote
								,"_remesa_"  , archivo.get("remesa")
								,"_tramite_" , ntramite
								,"_t_"       , timestamp , "_" , rand
								,".pdf"
								);
						
						File local = new File(filePath);
						
						InputStream remoto = HttpUtil.obtenInputStream(cddocume);
						FileUtils.copyInputStreamToFile(remoto, local);
					}
					
					impresionService.imprimeDocumento(
							filePath
							,dsimpres
							,Integer.parseInt(archivo.get("nmcopias")) //numCopias
							,null                                      //mediaId
							);
					
					if(test)
					{
						apagado = true;
					}
				}
			}
			
			/*
			else
			{
				for(Map<String,String>archivo:listaArchivos)
				{
					//qwe TODO si el papel es multiple hay que mandar por charola especifica (B por la 1, M por la 2)
					if(hoja.length()>1)
					{
						throw new ApplicationException("No soporta intercalada, consulte a desarrollo");
					}
					
					if(!apagado)
					{
						if("B".equals(archivo.get("tipodoc")))
						{
							impresionService.imprimeDocumento(
									Utils.join(rutaDocumentosPoliza,"/",archivo.get("ntramite"),"/",archivo.get("cddocume"))
									,dsimpres
									,Integer.parseInt(archivo.get("nmcopias")) //numCopias
									,null                                      //mediaId
									);
							if(test)
							{
								apagado = true;
							}
						}
						else if("M".equals(archivo.get("tipodoc")))
						{
							//qwe TODO Ricardo hay que bajar los recibos a un archivo local temporal y luego imprimir
							throw new ApplicationException("No soporta recibos remotos, consulte a desarrollo");
						}
					}
				}
			}
			*/
			
			paso = "Actualizando remesas, emisiones y endosos";
			sb.append("\n").append(paso);
			
			/*
			 * en este procedimiento se actualizan las sumas,
			 * y si estan completas: se marcan las remesas como impresas, y tambien los hijos cuando son emisiones/endosos
			 */
			boolean impresos = emisionDAO.sumarImpresiones(lote,tipolote,peso);
			
			if("R".equals(tipolote))
			{
				paso = "Actualizando recibos";
				sb.append("\n").append(paso);
				
				List<DocumentoReciboParaMostrarDTO> listaRecibos = new ArrayList<DocumentoReciboParaMostrarDTO>();
				for(Map<String,String> archivo : listaArchivos)
				{
					listaRecibos.add(new DocumentoReciboParaMostrarDTO(archivo.get("ntramite"),archivo.get("cddocume")));
				}
				
				emisionDAO.mostrarRecibosImpresosListaDeListas(listaRecibos);
			}
			
			paso = "Recuperando remesas del lote";
			sb.append("\n").append(paso);
			
			List<Map<String,String>> remesas = mesaControlDAO.recuperarTramites(
					null  //cdunieco
					,null //ntramite
					,null //cdramo
					,null //nmpoliza
					,null //estado
					,null //cdagente
					,"0"  //status
					,null //cdtipsit
					,null //fedesde
					,null //fehasta
					,cdsisrol
					,TipoTramite.IMPRESION.getCdtiptra()
					,null //contrarecibo
					,null //tipoPago
					,null //nfactura
					,null //cdpresta
					,null //cdusuari
					,null //cdtipram
					,lote
					,null //tipolote
					,null //tipoimpr
					,null //cdusuari_busq
					);
			
			for(Map<String,String> remesa : remesas)
			{
				paso = "Guardando detalle de remesa";
				sb.append("\n").append(paso);
				
				mesaControlDAO.movimientoDetalleTramite(
						remesa.get("ntramite")
						,new Date() //feinicio
						,null       //cdclausu
						,Utils.join(
								impresos ?
										"Se realiz\u00F3 la impresi\u00F3n final de la remesa ("
										: "Se realiz\u00F3 una impresi\u00F3n de la remesa ("
								,"B".equals(hoja) ?
										("papeler\u00EDa")
										:(
												"M".equals(hoja) ?
														("recibos")
														:(
																"C".equals(hoja) ?
																		("credenciales")
																		: ("papeler\u00EDa y recibos")
														)
										)
								,")"
								)
						,cdusuari
						,null       //cdmotivo
						,cdsisrol
						);
			}
			
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso, sb.toString());
		}
		
		sb.append(Utils.log(
			     "\n@@@@@@ imprimirLote @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		
		logger.debug(sb.toString());
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
	
	@Override
	public void actualizarStatusRemesa(
			String ntramite
			,String status
			,String cdusuari
			,String cdsisrol
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ actualizarStatusRemesa @@@@@@"
				,"\n@@@@@@ ntramite=" , ntramite
				,"\n@@@@@@ status="   , status
				,"\n@@@@@@ cdusuari=" , cdusuari
				,"\n@@@@@@ cdsisrol=" , cdsisrol
				));
		
		String paso = null;
		try
		{
			paso = "Actualizando status de remesa";
			logger.debug("@@@@@@ paso: {}",paso);
			
			mesaControlDAO.actualizarStatusRemesa(ntramite,status);
			
			paso = "Actualizando status de hijos de remesa";
			logger.debug("@@@@@@ paso: {}",paso);
			
			mesaControlDAO.actualizarHijosRemesa(null, ntramite, status);
			
			paso = "Recuperando status";
			logger.debug("@@@@@@ paso: {}",paso);
			
			List<GenericVO> statuses = catalogosDAO.obtieneTmanteni(Catalogos.MC_ESTATUS_TRAMITE.getCdTabla());
			String          dsStatus = null;
			for(GenericVO statusIte : statuses)
			{
				if(statusIte.getKey().equals(status))
				{
					dsStatus = statusIte.getValue();
					break;
				}
			}
			if(StringUtils.isBlank(dsStatus))
			{
				throw new ApplicationException("No se encuentra el status");
			}
			
			paso = "Guardando detalle";
			logger.debug("@@@@@@ paso: {}",paso);
			
			mesaControlDAO.movimientoDetalleTramite(
					ntramite
					,new Date() //feinicio
					,null       //cdclausu
					,Utils.join("Remesa actualizada a status '",dsStatus,"'")
					,cdusuari
					,null       //cdmotivo
					,cdsisrol);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ actualizarStatusRemesa @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
	}
}