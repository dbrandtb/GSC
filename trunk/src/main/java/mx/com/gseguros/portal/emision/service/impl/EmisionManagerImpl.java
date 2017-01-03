package mx.com.gseguros.portal.emision.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.consultas.dao.ConsultasPolizaDAO;
import mx.com.gseguros.portal.consultas.model.PolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.PolizaDTO;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.emision.dao.EmisionDAO;
import mx.com.gseguros.portal.emision.service.EmisionManager;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.utils.Utils;

public class EmisionManagerImpl implements EmisionManager
{
	private Map<String,Object> session;
	
	private static Logger logger = LoggerFactory.getLogger(EmisionManagerImpl.class);
	
	@Value("${sigs.generarPolizaRecupera.url}")
	private String urlGeneraPolizaRecupera;
	
	private PantallasDAO pantallasDAO;
	
	@Autowired
	private CotizacionDAO cotizacionDAO;
	
	@Autowired
	private EmisionDAO emisionDAO;
	
	@Autowired
	@Qualifier("consultasDAOICEImpl")
	private ConsultasPolizaDAO consultasPolizaDAO;
	
	@Value("${url.descargar.documentos}")
	private String urlDescargarDocumentosLibre;
	
	@Override
	public ManagerRespuestaImapVO construirPantallaClausulasPoliza() throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ construirPantallaClausulasPoliza @@@@@@"
				));
		
		ManagerRespuestaImapVO resp=new ManagerRespuestaImapVO(true);
		resp.setImap(new HashMap<String,Item>());
		
		String paso = null;
		
		try
		{
			paso = "Recuperando combo de clausulas por poliza";
			List<ComponenteVO>comboClausulas=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,null //cdsisrol
					,"PANTALLA_CLAUSULAS_POLIZA"
					,"COMBO_CLAUSULAS"
					,null //orden
					);
			Utils.validate(comboClausulas , "Error al obtener el combo de clausulas por poliza");
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(comboClausulas, true, false, true, false, false, false);
			resp.getImap().put("comboClausulas" , gc.getItems());
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex,paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ construirPantallaClausulasPoliza @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO guardarClausulasPoliza(List<Map<String,String>>clausulas)
	{
		logger.debug(Utils.log(
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarClausulasPoliza @@@@@@"
				,"\n@@@@@@ clausulas=",clausulas
				));
		
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		
		logger.debug(Utils.log(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ guardarClausulasPoliza @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	/*
	@Deprecated
	@Override
	public String insercionDocumentosParametrizados(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String proceso
			)throws Exception
	{
		logger.debug(Utils.log(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ insercionDocumentosParametrizados @@@@@@"
				,"\n@@@@@@ cdunieco=" , cdunieco
				,"\n@@@@@@ cdramo="   , cdramo
				,"\n@@@@@@ estado="   , estado
				,"\n@@@@@@ nmpoliza=" , nmpoliza
				,"\n@@@@@@ nmsituac=" , nmsituac
				,"\n@@@@@@ nmsuplem=" , nmsuplem
				,"\n@@@@@@ proceso="  , proceso
				));
		
		String cdorddoc = null
		       ,paso    = null;
		try
		{
			paso = "Generando documentos parametrizados";
			logger.debug("Paso: {}",paso);
			cdorddoc = cotizacionDAO.insercionDocumentosParametrizados(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,nmsituac
					,nmsuplem
					,proceso
					);
			logger.debug("cdorddoc: {}",cdorddoc);
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		
		logger.debug(Utils.log(
				 "\n@@@@@@ cdorddoc=",cdorddoc
				,"\n@@@@@@ insercionDocumentosParametrizados @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return cdorddoc;
	}
	*/
	
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

	@Override
	public void getActualizaCuadroComision(Map<String, Object> paramsPoliage) throws Exception {
		try {
			cotizacionDAO.getActualizaCuadroComision(paramsPoliage);
		} catch (DaoException daoExc) {
			throw new Exception(daoExc.getMessage(), daoExc);
		}
	}
	
	@Override
	@Deprecated
	public void actualizaNmsituaextMpolisit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac 
			,String nmsuplem
			,String nmsituaext
			)throws Exception
	{
		cotizacionDAO.actualizaNmsituaextMpolisit(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nmsituac
				,nmsuplem
				,nmsituaext
				);
	}
	
	@Override
	@Deprecated
	public void actualizaDatosMpersona(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdestciv
			,String ocup
			)throws Exception
	{
		cotizacionDAO.actualizaDatosMpersona(
				cdunieco
				,cdramo
				,estado
				,nmpoliza
				,nmsituac
				,nmsuplem
				,cdestciv
				,ocup);
	}
	
	@Deprecated
	@Override
	public void validarDocumentoTramite (String ntramite, String cddocume) throws Exception {
		emisionDAO.validarDocumentoTramite(ntramite, cddocume);
	}
	
	@Override
	@Deprecated
	public String recuperarTramiteCotizacion (String cdunieco, String cdramo, String estado, String nmpoliza) throws Exception {
		return emisionDAO.recuperarTramiteCotizacion(cdunieco, cdramo, estado, nmpoliza);
	}
	
	public boolean revierteEmision(String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem){
		
		logger.debug(" <<<<<<>>>>>> Entrando a Revierte Emision <<<<<<>>>>>>");
		try{
			emisionDAO.revierteEmision(cdunieco, cdramo, estado, nmpoliza, nmsuplem);
			return true;
		}catch(Exception e){
			logger.error("XXXXXxxx  ERROR AL REVETIR LA EMISION xxxXXXXX:::"+e.getMessage(), e);
			return false;
		}
	}
	
	@Override
	public String generarLigasDocumentosEmisionLocalesIce (String ntramite) throws Exception {
		logger.debug(Utils.log(
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",
				"\n@@@@@@ generarLigasDocumentosEmisionLocalesIce @@@@@@",
				"\n@@@@@@ ntramite = ", ntramite));
		String ligas = "",
			paso = null;
		try {
			paso = "Recuperando documentos generados por parametrizaci\u00f3n";
			logger.debug(paso);
			List<Map<String, String>> docsGenerados = emisionDAO.recuperarDocumentosGeneradosPorParametrizacion(ntramite);
			if (docsGenerados.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (Map<String, String> doc : docsGenerados) {
					sb.append(Utils.join(
							"<br/><br/><a style=\"font-weight: bold\" href=\"",
							urlDescargarDocumentosLibre,
							"?subfolder=", ntramite,
							"&filename=", doc.get("CDDOCUME"),
							"\">",
							doc.get("DSDOCUME"),
							"</a>"));
				}
				ligas = sb.toString();
			}
		} catch (Exception ex) {
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(Utils.log(
				"\n@@@@@@ ligas =", ligas,
				"\n@@@@@@ generarLigasDocumentosEmisionLocalesIce @@@@@@",
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"));
		return ligas;
	}
	
	//SILVIA 
	@Override
	public ManagerRespuestaSlistVO procesarCargaMasivaRecupera(File excel)throws Exception
	{
		String errores="";
		String polizas="";
		String campo="";
		double cambio=0;
		int entero=0;
		logger.debug(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ procesarCargaMasivaRecupera @@@@@@")
				.append("\n@@@@@@ excel=")   .append(excel)
				.toString()
				);
		
		ManagerRespuestaSlistVO resp = new ManagerRespuestaSlistVO(true);
		resp.setSlist(new ArrayList<Map<String,String>>());
		
		String paso = null;
		
		try
		{
			paso = "Iniciando procesador de hoja de calculo";
			FileInputStream input       = new FileInputStream(excel);
			XSSFWorkbook    workbook    = new XSSFWorkbook(input);
			XSSFSheet       sheet       = workbook.getSheetAt(0);
			Iterator<Row>   rowIterator = sheet.iterator();
			StringBuilder   sb;
			
			paso = "Iterando filas";
			int fila = 1;
			while (rowIterator.hasNext()) 
            {
				fila = fila + 1;
				paso = new StringBuilder("Iterando fila ").append(fila).toString();
				logger.debug("PASO: "+paso);
				Row row = rowIterator.next();
				
				if(fila==2)
				{
					row = rowIterator.next();
				}
				logger.debug("este "+Utils.isRowEmpty(row));
				if(Utils.isRowEmpty(row))
				{
					break;
				}
				sb = new StringBuilder();
				
				Map<String,String>record=new LinkedHashMap<String,String>();
				resp.getSlist().add(record);
				int numeroPol = 0;
				int col = 0;
				for(int i= 0;i<=17;i++)
				{
					paso = "Columna numero: "+i;
					PolizaAseguradoVO datos = new PolizaAseguradoVO();
					col  = i;
					Cell cell = row.getCell(i);
					
					if(i == 2){
						String poliza = cell.toString().trim();
						double numPol = Double.parseDouble(poliza);
						numeroPol = (int) numPol;
					}
					
					if(cell.getCellType() == cell.CELL_TYPE_NUMERIC)
						logger.debug("CELDA NUMERO: "+String.format("%.2f", cell.getNumericCellValue())+"  "+i);
					else
						logger.debug("CELDA: "+cell+"  "+i);
					
					///VALIDAR QUE NO EXISTA POLIZA EN MPOLIZAS///
					if(i == 2){
						int ban = 0;
						datos.setCdunieco("1000");
						datos.setCdramo("1");
						datos.setEstado("M");
						datos.setNmpoliza(cell.toString().trim());
						List<PolizaDTO> datosPolizas = consultasPolizaDAO.obtieneDatosPoliza(datos);
						for(PolizaDTO t:datosPolizas){
							errores = errores + "POLIZA EXISTENTE";
							polizas = polizas + numeroPol;
							resp.setRespuesta(errores);
							resp.setRespuestaOculta(polizas);
							resp.setExito(false);
						}	
					}
					if(i == 7){
						if(!cell.toString().trim().equals("RI")){
							errores = errores + "PRODUCTO VALIDO SOLO PARA RECUPERA INDIVIDUAL (RI)";
							polizas = polizas + numeroPol;
							resp.setRespuesta(errores);
							resp.setRespuestaOculta(polizas);
							resp.setExito(false);
						}
					}
					if(i == 9){
						String esq=cell.toString().trim();
						double esq1 = Double.parseDouble(esq);
						int esquema = (int) esq1;
						if(esquema != 20 && esquema != 50 && esquema != 100){
							errores = errores + "VALOR PARA ESQUEMA (20/50/100)";
							polizas = polizas + numeroPol;
							resp.setRespuesta(errores);
							resp.setRespuestaOculta(polizas);
							resp.setExito(false);
						}
					}
					if(i == 10){
						if(!cell.toString().trim().equals("T")){
							errores = errores + "VALOR PARA PARENTESCO OBLIGATORIO (T)";
							polizas = polizas + numeroPol;
							resp.setRespuesta(errores);
							resp.setRespuestaOculta(polizas);
							resp.setExito(false);
						}
					}
					if(i == 13){
						if((!cell.toString().trim().equals("H")) && (!cell.toString().trim().equals("M"))){
							errores = errores + "SEXO INCORRECTO (H/M)|";
							polizas = polizas + numeroPol;
							resp.setRespuesta(errores);
							resp.setRespuestaOculta(polizas);
							resp.setExito(false);
						}  
					}
					
					if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
						if(i != 14 && i !=15){
							if(i == 11 || i == 16){
								campo = cell.toString().trim();
								SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
								SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
								campo = sdf2.format(sdf1.parse(campo));
								
							}else{
								campo = String.format("%.2f", cell.getNumericCellValue());
								cambio = Double.parseDouble(campo);
								entero = (int) cambio;
								campo = String.valueOf(entero);
							}
						}else{
							campo = String.format("%.2f", cell.getNumericCellValue());
						}
						record.put(String.valueOf(i),campo);
					}else
						record.put(String.valueOf(i),cell.toString().trim());
				}
            }
		}
		catch(Exception ex)
		{
			Utils.generaExcepcion(ex, paso);
		}
		logger.debug(resp.getRespuesta());
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ procesarCargaMasivaFlotilla @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	public String generarPoliza(String cdperson, String sucursal, String poliza, String nombre1, String nombre2,
			String apePat, String apeMat, String producto, String cve_plan, String esq_suma_aseg, String parentesco,
			String f_nacimiento, String RFC, String sexo, String peso, String estatura, String fecinivig,
			String membresia) {
		
		String mensaje = "";
		boolean exito;
		ArrayList<String> resultados = new ArrayList<String>();
		try {
			
//			String params      = Utils.join("sucursal=",cdunieco,"&ramo=",cdramo,"&poliza=",cdpoliza,"&tipoflot=",
//					tipoflot,"&cdtipsit=",cdtipsit,"&cargaCot=",cargaCot)
//					  ,respuestaWS =HttpUtil.sendPost(getText("sigs.obtenerDatosPorSucRamPol.url"),params);
//					HashMap<String, ArrayList<String>> someObject = (HashMap<String, ArrayList<String>>)JSONUtil.deserialize(respuestaWS);
//					Map<String,String>parametros = (Map<String,String>)someObject.get("params");
//					String formpagSigs = parametros.get("formpagSigs");
//					paquetesYFormaPago.add(formpagSigs);
//					paquetesYFormaPago.addAll(1,someObject.get("paquetes"));
//					logger.debug(Utils.log(paquetesYFormaPago));
//			
			
			String params = Utils.join("cdperson=", cdperson, "&sucursal=", sucursal, "&poliza=", poliza, "&nombre1=",
					nombre1, "&nombre2=", nombre2, "&apePat=", apePat, "&apeMat=", apeMat, "&producto=", producto,
					"&cvePlan=", cve_plan, "&esqSumaAseg=", esq_suma_aseg, "&parentesco=", parentesco, "&fNacimiento=",
					f_nacimiento, "&rfc=", RFC, "&sexo=", sexo, "&peso=", peso, "&estatura=", estatura, "&fecinivig=",
					fecinivig, "&membresia=", membresia), respuestaWS = HttpUtil.sendPost(urlGeneraPolizaRecupera, params);
					HashMap<String, String> someObject = (HashMap<String, String>)JSONUtil.deserialize(respuestaWS);
					//exito = someObject.get("mensaje");
					mensaje = someObject.get("mensaje");
			if (mensaje != null) {
				return mensaje;
			}
			logger.debug("ENTRO ");
		} catch (Exception ex) {
			mensaje = Utils.manejaExcepcion(ex);
		}
		return mensaje;
	}
	
	
	public void insertaBitacora(Date fecha, String nombre, int polizas, String rango, String usuario) throws Exception {
			consultasPolizaDAO.insertaBitacora(fecha, nombre, polizas, rango, usuario);
	}
	
}