package mx.com.gseguros.portal.siniestros.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal2.web.GenericVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.service.CotizacionManager;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.TipoFecha;
import mx.com.gseguros.portal.general.validacionformato.CampoVO;
import mx.com.gseguros.portal.general.validacionformato.ValidadorFormatoContext;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.utils.FTPSUtils;
import mx.com.gseguros.utils.Utils;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;

public class ConfiguracionLayoutAction extends PrincipalCoreAction {
	private static final long serialVersionUID = -1637743812712245272L;
	static final Logger logger = LoggerFactory.getLogger(ConfiguracionLayoutAction.class);
	private DateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");
	private HashMap<String,String> 	params;
	private Map<String,String>      smap1;
	private String                  error;
	private String                  respuesta;
	private String                  respuestaOculta = null;
	private boolean                 exito           = false;
	private File                    censo;
	private List<Map<String,Object>> olist1;
	private SiniestrosManager 		 siniestrosManager;
	private KernelManagerSustituto kernelManagerSustituto;
	private PantallasManager       pantallasManager;
	private transient CatalogosManager catalogosManager;
	private transient Ice2sigsService ice2sigsService;
	private List<HashMap<String,String>> datosTablas;
	private List<Map<String,String>> datosValidacion;
	private List<Map<String,String>>  datosInformacionAdicional;
	private boolean success;
	private File layoutGral;
	private String tipo; 
	private String minimo = null;
	private String maximo = null;
	private boolean requerido = false;
	private String formatofecha = null;
	private GenericVO resultado;
	private String fileName;
    private String fileNameError;
    private String censoFileName;
	private String censoContentType;
	private List<Map<String,String>> slist1;
	private String mensaje;
	private String validacionGeneral;
	
	@Autowired
	private ValidadorFormatoContext validadorFormatoContext;
    
	@Autowired
	private CotizacionManager cotizacionManager;
	/**
	* Funcion donde obtenemos los datos de las validaciones del siniestro
	* @param params
	* @return List<Map<String, String>> datosValidacion
	*/ 
	public String consultaDatosConfiguracionProveedor(){
		logger.debug("Entra a consultaDatosProveedorSiniestro params de entrada :{} ",params);
		try {
			datosValidacion = siniestrosManager.getConsultaConfiguracionProveedor(params.get("cdpresta"));
			logger.debug("Respuesta datosValidacion : {}",datosValidacion);
		}catch( Exception e){
			logger.error("Error al obtener consultaDatosProveedorSiniestro : {}", e.getMessage(), e);
			return SUCCESS;
		}
		setSuccess(true);
		return SUCCESS;
	}
	
	public String  guardarConfiguracionProveedor(){
		logger.debug("Entra a guardarConfiguracionProveedor params de entrada :{}",params);
		try {
			Date   fechaProcesamiento = new Date();
			this.session=ActionContext.getContext().getSession();
			UserVO usuario=(UserVO) session.get("USUARIO");
			
			
			String respuesta = siniestrosManager.guardaConfiguracionProveedor(params.get("cmbProveedorMod"),params.get("tipoLayout"),params.get("idaplicaIVA"),
					params.get("secuenciaIVA"),params.get("idaplicaIVARET"),usuario.getUser(), fechaProcesamiento, params.get("proceso"));
		}catch( Exception e){
			logger.error("Error al obtener el monto del arancel : {}", e.getMessage(), e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	
	/**
	* metodo para el guardado del alta del tramite
	* @param Json con todos los valores del formulario y los grid
	* @return Lista AutorizaServiciosVO con la informacion de los asegurados
	*/
	public String guardaConfiguracionLayout(){
		logger.debug("Entra a guardaAltaTramite Params: {} datosTablas {}", params,datosTablas);
		try{
			//Realizamos la insercion de los guardados
			siniestrosManager.guardaLayoutProveedor(params.get("cmbProveedor"),params.get("tipoLayout"), null,null,null,null,null,null,null,null,"D");
			for(int i=0;i<datosTablas.size();i++) {
				siniestrosManager.guardaLayoutProveedor(
					params.get("cmbProveedor"), 
					params.get("tipoLayout"),
					datosTablas.get(i).get("claveAtributo"),
					datosTablas.get(i).get("claveFormatoAtributo"),
					datosTablas.get(i).get("valorMinimo"),
					datosTablas.get(i).get("valorMaximo"),
					datosTablas.get(i).get("columnaExcel"),
					datosTablas.get(i).get("claveFormatoFecha"),
					datosTablas.get(i).get("atributoRequerido"),
					i+"",
					null
				);
			}
		}catch( Exception e){
			logger.error("Error en el guardado de alta de tramite : {}", e.getMessage(), e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	
	public String validaExisteConfiguracionProv(){
		logger.debug("Entra a validaAutorizacionEspecial  Params: {}", params);
		try {
			validacionGeneral = siniestrosManager.validaExisteConfiguracionProv(params.get("cdpresta"), params.get("tipoLayout"));
			logger.debug("validacionGeneral : {}", validacionGeneral);
		}catch( Exception e){
			logger.error("Error validaAutorizacionEspecial : {}", e.getMessage(), e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	
	/**
	* Funcion para obtener el listado del alta del tramite
	* @param ntramite
	* @return Lista AltaTramiteVO - tramite Alta Tramite
	*/
	public String consultaConfiguracionLayout(){
		logger.debug("Entra a consultaConfiguracionLayout Params: {}", params);
		try {
			datosInformacionAdicional = siniestrosManager.consultaConfiguracionLayout(params.get("cdpresta"));
		}catch( Exception e){
			logger.error("Error consultaListadoAltaTramite: {}", e.getMessage(), e);
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	
	public String subirLayoutGeneral()
	{
		logger.debug(""
				+ "\n#############################"
				+ "\n###### subirLayoutGeneral ######"
				+ "\n censo "+censo+""
				+ "\n censoFileName "+censoFileName+""
				+ "\n censoContentType "+censoContentType+""
				+ "\n smap1 "+smap1
				);
		
		success = true;
		exito   = true;
		
		String ntramite=smap1.get("ntramite");
		if(StringUtils.isBlank(ntramite))
		{
			String timestamp = smap1.get("timestamp");
			//censo.renameTo(new File(this.getText("ruta.documentos.temporal")+"/censo_"+timestamp));
			try {
            	FileUtils.copyFile(censo, new File(this.getText("ruta.documentos.temporal")+"/layoutGral_"+timestamp));
            	logger.debug("archivo movido");
			} catch (Exception e) {
				logger.error("archivo NO movido", e);
			}
			
			logger.debug("censo renamed to: "+this.getText("ruta.documentos.temporal")+"/layoutGral_"+timestamp);
		}
		
		logger.debug(""
				+ "\n###### subirLayoutGeneral ######"
				+ "\n################################"
				);
		return SUCCESS;
	}
	
	public String validaLayoutFormatoExcel(){
		this.session=ActionContext.getContext().getSession();
		logger.debug(Utils.log(
				 "\n######################################"
				,"\n###### validaLayoutFormatoExcel ######"
				,"\n###### smap1="  , smap1
				,"\n###### olist1=" , olist1
				));
		success = true;
		exito   = true;
		try {
			//Recibimos el parametro de timestamp para validar el nombre del archivo
			String layoutTimestamp = smap1.get("timestamp");
			String cdpresta        = smap1.get("cdpresta");
			String layoutConf      = smap1.get("tipoLayout");
			
			layoutGral = new File(this.getText("ruta.documentos.temporal")+"/layoutGral_"+layoutTimestamp);
			logger.debug("============== VALOR DEL NOMBRE DEL LAYOUT GENERAL ==========:  {}",layoutGral);
			List<CampoVO> campos = new ArrayList<CampoVO>();
			List<Map<String,String>>  datosInformacionLayout;
			List<Map<String,String>>  confLayoutExcel;
			FileInputStream input       = null;
			Workbook        workbook    = null;
			
			datosInformacionLayout = siniestrosManager.requiereConfiguracionLayoutProveedor(cdpresta, layoutConf);
			input       = new FileInputStream(layoutGral);
			workbook    = WorkbookFactory.create(input);
			
			if(exito&&workbook.getNumberOfSheets()!=1)
			{
				long etimestamp = System.currentTimeMillis();
				exito           = false;
				respuesta       = "Favor de revisar el n\u00famero de hojas del layout #"+etimestamp;
				logger.error(respuesta);
			}else{
				for(int i = 0; i < Integer.parseInt(datosInformacionLayout.get(0).get("MAXREGISTRO"));i++){
					tipo = null;
					minimo = null;
					maximo = null;
					requerido = false;
					formatofecha = null;
					//Obtenemos la configuracion del layout configurado
					confLayoutExcel = siniestrosManager.obtieneConfiguracionLayoutExcel(cdpresta, layoutConf, String.valueOf(i+1));
					//logger.debug("Valor de confLayoutExcel =====> "+confLayoutExcel);
					try{
						tipo = confLayoutExcel.get(0).get("CVEFORMATO").toString();
					}
					catch(Exception ex){
						tipo = null;
					}
					
					try{
						minimo = confLayoutExcel.get(0).get("VALORMIN").toString();
					}
					catch(Exception ex){
						minimo = null;
					}
					
					try{
						maximo = confLayoutExcel.get(0).get("VALORMAX").toString();
					}
					catch(Exception ex){
						maximo = null;
					}
					
					try{
						requerido = Boolean.parseBoolean(confLayoutExcel.get(0).get("SWOBLIGA").toString());
					}
					catch(Exception ex){
						requerido =false;
					}
				
					try{
						formatofecha = confLayoutExcel.get(0).get("FORMATDATE").toString();
					}
					catch(Exception ex){
						formatofecha =null;
					}
					
					if(tipo.equalsIgnoreCase("F")){
						campos.add(new CampoVO(tipo, null, null, requerido,formatofecha));
					}else{
						campos.add(new CampoVO(tipo, Integer.parseInt(minimo), Integer.parseInt(maximo), requerido));
					}
				}
				
				String fullNameArchErrValida = getText("ruta.documentos.temporal") + Constantes.SEPARADOR_ARCHIVO+"conversion_" + System.currentTimeMillis() + "_err.txt";
				
				File archErrVal = validadorFormatoContext.ejecutaValidacionesFormato(layoutGral, campos, fullNameArchErrValida, ValidadorFormatoContext.Strategy.VALIDACION_EXCEL);
				
				if(archErrVal != null && archErrVal.length() > 0) {
					String msjeError = "Archivo tiene errores de formato";
					String cadena;
					FileReader f = new FileReader(fullNameArchErrValida);
				    BufferedReader b = new BufferedReader(f);
				    StringBuilder bufferErroresLayout = new StringBuilder();
				    while((cadena = b.readLine())!=null) {
				    	respuesta= cadena+"\n";
				    	bufferErroresLayout.append(respuesta);
			    	}
				    b.close();
				    
					StringBuilder errorLayout      = new StringBuilder();
					respuesta       = errorLayout.append(bufferErroresLayout.toString()).append("\nError #").append(System.currentTimeMillis()).toString();
					exito   = false;
					success = false;
					
					if (archErrVal.delete()){
						logger.debug("El fichero archErrVal ha sido borrado satisfactoriamente");
					}else{
						logger.debug("El fichero archErrVal no puede ser borrado");
					}
					
					throw new ApplicationException(msjeError);
					
				}
			}
		} catch (Exception e) {
			Utils.manejaExcepcion(e);
		}
		logger.debug(""
				+ "\n###### validaLayoutFormatoExcel ######"
				+ "\n######################################"
				);
		
		
		return SUCCESS;
	}
	
	public String validaLayoutConfiguracionExcel()
	{
		this.session=ActionContext.getContext().getSession();
		logger.debug(Utils.log(
				 "\n#####################################"
				,"\n###### subirexcelConfiguracion ######"
				,"\n###### smap1="  , smap1
				,"\n###### olist1=" , olist1
				));
		success = true;
		exito   = true;
		try {
			//Recibimos el parametro de timestamp para validar el nombre del archivo
			String layoutTimestamp = smap1.get("timestamp");
			String cdpresta        = smap1.get("cdpresta");
			String layoutConf      = smap1.get("tipoLayout");
			
			layoutGral = new File(this.getText("ruta.documentos.temporal")+"/layoutGral_"+layoutTimestamp);
			
			List<Map<String,String>>  datosInformacionLayout = siniestrosManager.requiereConfiguracionLayoutProveedor(cdpresta, layoutConf);
			String nombreLayout = null;
			String nombreLayoutConfirmado = smap1.get("nombreLayoutConfirmado");
				
			if(exito&&StringUtils.isBlank(nombreLayoutConfirmado)){
				FileInputStream input       = null;
				Workbook        workbook    = null;
				Sheet           sheet       = null;
				Long            inTimestamp = null;
				File            archivoTxt  = null;
				PrintStream     output      = null;
				
				try{
					input       = new FileInputStream(layoutGral);
					workbook    = WorkbookFactory.create(input);
					sheet       = workbook.getSheetAt(0);
					inTimestamp = System.currentTimeMillis();
					nombreLayout = "layout_"+inTimestamp+".txt";
					archivoTxt  = new File(this.getText("ruta.documentos.temporal")+"/"+nombreLayout);
					output      = new PrintStream(archivoTxt);
				}
				catch(Exception ex){
					long etimestamp = System.currentTimeMillis();
					exito           = false;
					respuesta       = "Error al procesar el layout #"+etimestamp;
					respuestaOculta = ex.getMessage();
					logger.error(respuesta,ex);
				}
				
				if(exito)
				{
					//Iterate through each rows one by one
					logger.debug(""
							+ "\n##############################################"
							+ "\n###### "+archivoTxt.getAbsolutePath()+" ######"
							);
					
		            Iterator<Row> rowIterator        = sheet.iterator();
		            int           fila               = 0;
		            int           nConsulta          = 0;
		            StringBuilder bufferErroresCenso = new StringBuilder();
		            int           filasLeidas        = 0;
		            int           filasProcesadas    = 0;
		            int           filasError         = 0;
		            
		            Map<Integer,String>  totalConsultasAseg = new LinkedHashMap<Integer,String>();
					Map<Integer,Boolean> estadoConsultas = new LinkedHashMap<Integer,Boolean>();
					Map<Integer,Integer> errorConsultas   = new LinkedHashMap<Integer,Integer>();
					
					boolean[] gruposValidos = new boolean[olist1.size()];
					while (rowIterator.hasNext()&&exito) 
		            {
						Row           row            = rowIterator.next();
		                Date          auxDate        = null;
		                Cell          auxCell        = null;
		                StringBuilder bufferLinea    = new StringBuilder();
		                StringBuilder bufferLineaStr = new StringBuilder();
		                boolean       filaBuena      = true;
		                
		                if(Utils.isRowEmpty(row))
		                {
		                	break;
		                }
		                
		                fila       		= fila + 1;
		                filasLeidas 	= filasLeidas + 1;
		                double cdgrupo	= -1d;

		                // Empezamos a leer los campos del archivo de Excel
		                String fechaOcurrencia = null;
		                String aseguradoAfectado = null;
		                int total = 1;
		                for(int i = 0; i < datosInformacionLayout.size();i++){
		                	//logger.debug("Valor de los datos ===> "+datosInformacionLayout.get(i));
		                	int celdaPrincipal = Integer.parseInt(datosInformacionLayout.get(i).get("CVEEXCEL").toString())-1;
		                	auxCell = row.getCell(celdaPrincipal);
		                	
		                	logger.debug("Valor ===>"+datosInformacionLayout.get(i).get("DESCEXCEL").toString());
		                	try{
		                		//ALFANUMERICO
		                		if(row.getCell(celdaPrincipal).getCellType()>0){
			                		//validamos si es una fecha
		                			if(datosInformacionLayout.get(i).get("CVEFORMATO").toString().equalsIgnoreCase("F")){
		                				
		                				String dateInString = auxCell.getStringCellValue().trim();
		                				if(datosInformacionLayout.get(i).get("FORMATFECH").toString().equalsIgnoreCase(TipoFecha.ddMMyyyy_Diagonal.getCodigo())){
		                					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		                					Date date = formatter.parse(dateInString);
		                					bufferLinea.append(
				                				auxCell!=null?renderFechas.format(date)+"|":"|"
					                		);		                					
		                					//logger.debug("1.- "+datosInformacionLayout.get(i).get("DESCEXCEL").toString());
		                					if(datosInformacionLayout.get(i).get("DESCEXCEL").toString().equalsIgnoreCase("FECHA OCURRENCIA")){
		                						logger.debug("1.- "+datosInformacionLayout.get(i).get("DESCEXCEL").toString()+"  "+renderFechas.format(date));
		                						fechaOcurrencia = renderFechas.format(date);
		                					}
		                				}
		                				
		                				if(datosInformacionLayout.get(i).get("FORMATFECH").toString().equalsIgnoreCase(TipoFecha.ddMMyyyy_Gion.getCodigo())){
		                					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		                					Date date = formatter.parse(dateInString);
		                					bufferLinea.append(
				                				auxCell!=null?renderFechas.format(date)+"|":"|"
					                		);
		                					if(datosInformacionLayout.get(i).get("DESCEXCEL").toString().equalsIgnoreCase("FECHA OCURRENCIA")){
		                						logger.debug("2.- "+datosInformacionLayout.get(i).get("DESCEXCEL").toString()+"  "+renderFechas.format(date));
		                						fechaOcurrencia = renderFechas.format(date);
		                					}
		                					
		                				}
		                				if(datosInformacionLayout.get(i).get("FORMATFECH").toString().equalsIgnoreCase(TipoFecha.yyyyMMdd_Diagonal.getCodigo())){
		                					SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		                					Date date = formatter.parse(dateInString);
		                					bufferLinea.append(
				                				auxCell!=null?renderFechas.format(date)+"|":"|"
					                		);
		                					if(datosInformacionLayout.get(i).get("DESCEXCEL").toString().equalsIgnoreCase("FECHA OCURRENCIA")){
		                						logger.debug("3.- "+datosInformacionLayout.get(i).get("DESCEXCEL").toString()+"  "+renderFechas.format(date));
		                						fechaOcurrencia = renderFechas.format(date);
		                					}
		                				}
		                				if(datosInformacionLayout.get(i).get("FORMATFECH").toString().equalsIgnoreCase(TipoFecha.yyyyMMdd_Gion.getCodigo())){
		                					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		                					Date date = formatter.parse(dateInString);
		                					bufferLinea.append(
				                				auxCell!=null?renderFechas.format(date)+"|":"|"
					                		);
		                					if(datosInformacionLayout.get(i).get("DESCEXCEL").toString().equalsIgnoreCase("FECHA OCURRENCIA")){
		                						logger.debug("4.- "+datosInformacionLayout.get(i).get("DESCEXCEL").toString()+"  "+renderFechas.format(date));
		                						fechaOcurrencia = renderFechas.format(date);
		                					}
		                				}
		                				if(datosInformacionLayout.get(i).get("FORMATFECH").toString().equalsIgnoreCase(TipoFecha.ddMMyyyyhhmmss_Diagonal.getCodigo())){
		                					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US);
		                					Date date = formatter.parse(dateInString);
		                					bufferLinea.append(
				                				auxCell!=null?renderFechas.format(date)+"|":"|"
					                		);
		                					if(datosInformacionLayout.get(i).get("DESCEXCEL").toString().equalsIgnoreCase("FECHA OCURRENCIA")){
		                						logger.debug("5.- "+datosInformacionLayout.get(i).get("DESCEXCEL").toString()+"  "+renderFechas.format(date));
		                						fechaOcurrencia = renderFechas.format(date);
		                					}
		                				}
		                			}else{
		                				String cadenaModificada = auxCell.getStringCellValue().trim().
		                						replaceAll("á","a").
		                						replaceAll("é","e").
		                						replaceAll("í","i").
		                						replaceAll("ó","o").
		                						replaceAll("ú","u").
		                						replaceAll("Á","A").
		                						replaceAll("É","E").
		                						replaceAll("Í","I").
		                						replaceAll("Ó","O").
		                						replaceAll("Ú","U").
		                						replaceAll("\\*","");
		                				
		                				if(datosInformacionLayout.get(i).get("DESCEXCEL").toString().equalsIgnoreCase("CVE. ASEGURADO")){
		                					HashMap<String, Object> paramPersona = new HashMap<String, Object>();
		                					paramPersona.put("pv_cdideext_i",cadenaModificada);
		                					
		                					String existePersona = siniestrosManager.validaPersonaSisaSicaps(paramPersona);
		                					if(Integer.parseInt(existePersona) > 0){
		                						aseguradoAfectado = "G"+existePersona;
		                						bufferLinea.append(
					                				auxCell!=null?"G"+existePersona+"|":"|"
						                		);
		                					}else{
		                						throw new Exception("El asegurado no se encuentra en SICAPS.");
		                					}
		                				}else{
		                					bufferLinea.append(
				                				auxCell!=null?cadenaModificada.trim()+"|":"|"
					                		);
		                				}
		                				
		                				if(datosInformacionLayout.get(i).get("DESCEXCEL").toString().equalsIgnoreCase("CLAVE ASEGURADO")){
		                					aseguradoAfectado = cadenaModificada.trim();
		                				}
		                			}
			                	}else{
			                		//NUMERICO
			                		bufferLinea.append(
			                			auxCell!=null?String.valueOf(auxCell.getNumericCellValue()).toString()+"|":"|"
			                		);
			                	}
		                		
		                		if(fechaOcurrencia!= null && aseguradoAfectado != null && total <= 1){
		                			total = total+1;
		                			HashMap<String, Object> paramPersona = new HashMap<String, Object>();
                					paramPersona.put("pv_feocurre_i",renderFechas.parse(fechaOcurrencia));
                					paramPersona.put("pv_cdperson_i",aseguradoAfectado.replaceAll("G", "")); 
                					String feocurreAsegurado = siniestrosManager.validaFeocurreAsegurado(paramPersona);
                					if(Integer.parseInt(feocurreAsegurado) == 0){
                						throw new Exception("El rango de la fecha no se encuentra en la p&oacute;liza");
                					}
		                		}
		                	}
		                	catch(Exception ex){
		                		filaBuena = false;
		                		logger.debug("Error ===>>>> {}", ex);
		                		if(datosInformacionLayout.get(i).get("DESCEXCEL").toString().equalsIgnoreCase("CVE. ASEGURADO")){
		                			bufferErroresCenso.append(Utils.join("La Clavel del Asegurado (CDIDEEXT) no se encuentra en SICAPS.\nError en el campo "+datosInformacionLayout.get(i).get("DESCEXCEL").toString()+" "+datosInformacionLayout.get(i).get("DESCRIPC").toString()+" de la fila ",fila," "));
		                		}else{
		                			bufferErroresCenso.append(Utils.join("Error en el campo "+datosInformacionLayout.get(i).get("DESCEXCEL").toString()+" "+datosInformacionLayout.get(i).get("DESCRIPC").toString()+" de la fila ",fila," "));
		                		}
			                }
			                finally{
			                	bufferLineaStr.append(Utils.join(extraerStringDeCelda(row.getCell(celdaPrincipal)),"-"));
			                }
		                }
		                bufferLinea.append(cdpresta+"|"+layoutTimestamp+"|");
		                nConsulta++;
	                	totalConsultasAseg.put(nConsulta,"");
	                	estadoConsultas.put(nConsulta,true);
	                	
	                	if(filaBuena) {
		                	totalConsultasAseg.put(nConsulta,Utils.join(totalConsultasAseg.get(nConsulta),bufferLinea.toString(),"\n"));
		                	filasProcesadas = filasProcesadas + 1;
		                }
		                else {
		                	filasError = filasError + 1;
		                	bufferErroresCenso.append(Utils.join(": ",bufferLineaStr.toString(),"\n"));
		                	estadoConsultas.put(nConsulta,false);
		                	if(!errorConsultas.containsKey(nConsulta)) {
		                		errorConsultas.put(nConsulta,fila);
		                	}
		                }		                	
		            } // fin del while
					
					logger.debug("VALOR DEL EXITO ====> "+exito);
					logger.debug("filasError ====>"+filasError);
					
					/*if(exito) {
		            	boolean       sonGruposValidos = true;
		            	StringBuilder errorGrupos      = new StringBuilder();
		            	
		            	if(!sonGruposValidos) {
		            		logger.debug("Entra a !sonGruposValidos");
		            		respuesta       = errorGrupos.append("\n").append(bufferErroresCenso.toString()).append("\nError #").append(System.currentTimeMillis()).toString();
		            		respuestaOculta = respuesta;
		            		logger.error(bufferErroresCenso.toString());
		            		logger.error(respuesta);
		            	}
		            }*/
					
					if(exito) {
		            	logger.debug("total Consultas: {}\nEstado Consultas: {}\nError Consultas: {}"
			            		,totalConsultasAseg,estadoConsultas,errorConsultas);
			            
			            for(Entry<Integer,Boolean>en:estadoConsultas.entrySet()){
			            	int     n = en.getKey();
			            	boolean v = en.getValue();
			            	if(v){
			            		output.print(totalConsultasAseg.get(n));
			            	}
			            }
			            
						smap1.put("erroresCenso"    , bufferErroresCenso.toString());
						smap1.put("filasLeidas"     , Integer.toString(filasLeidas));
						smap1.put("filasProcesadas" , Integer.toString(filasProcesadas));
						smap1.put("filasErrores"    , Integer.toString(filasError));
					}
					
					if(exito)
		            {
		            	try
		            	{
		            		input.close();
		            		output.close();
		            	}
		            	catch(Exception ex)
		            	{
		            		long etimestamp = System.currentTimeMillis();
		            		exito           = false;
		            		respuesta       = "Error al transformar el archivo #"+etimestamp;
		            		respuestaOculta = ex.getMessage();
		            		logger.error(respuesta,ex);
		            	}
		            }
					
					logger.debug(""
							+ "\n###### "+archivoTxt.getAbsolutePath()+" ######"
							+ "\n##############################################"
					);
					
					if(exito){
						if(filasError > 0){
							exito = false;
						}else{
							exito = FTPSUtils.upload
									(
										this.getText("dominio.server.layouts"),
										this.getText("user.server.layouts"),
										this.getText("pass.server.layouts"),
										archivoTxt.getAbsolutePath(),
										this.getText("directorio.server.layouts")+"/"+nombreLayout
								    )
									&&FTPSUtils.upload
									(
										this.getText("dominio.server.layouts2"),
										this.getText("user.server.layouts"),
										this.getText("pass.server.layouts"),
										archivoTxt.getAbsolutePath(),
										this.getText("directorio.server.layouts")+"/"+nombreLayout
									);
							
							if(!exito)
							{
								long etimestamp = System.currentTimeMillis();
								exito           = false;
								respuesta       = "Error al transferir archivo al servidor #"+etimestamp;
								respuestaOculta = respuesta;
								logger.error(respuesta);
							}
							
							if(exito)
							{
								try
								{
									logger.debug("Entra a la opcion del guardado ===> ");
									cotizacionManager.guardarLayoutGenerico(nombreLayout);
									logger.debug("<=== Sale a la opcion del guardado");
								}
								catch(Exception ex)
								{
									long etimestamp = System.currentTimeMillis();
									exito           = false;
									respuesta       = "Error al guardar los datos #"+etimestamp;
									respuestaOculta = ex.getMessage();
									logger.error(respuesta,ex);
									
								}
							}
						}
					}
				}// Fin del iterator
			}
			
			if(exito)
			{
				respuesta       = "Se ha complementado el guardado del layout";
				success = true;
				exito   = true;
				respuestaOculta = "Todo OK";
			}
		} catch (Exception e) {
			Utils.manejaExcepcion(e);
		}
		logger.debug(""
				+ "\n###### subirexcelConfiguracion ######"
				+ "\n#####################################"
				);
		
		
		return SUCCESS;
	}

	public String consultaLayoutConfigurados(){
		logger.debug("Entra a consultaDatosConfiguracionLayout params de entrada :{} ",params);
		try {
			datosValidacion = siniestrosManager.getConsultaLayoutConfigurados(params.get("descLayout"));
			logger.debug("Respuesta datosValidacion : {}",datosValidacion);
		}catch( Exception e){
			logger.error("Error al obtener consultaDatosConfiguracionLayout : {}", e.getMessage(), e);
			return SUCCESS;
		}
		setSuccess(true);
		return SUCCESS;
	}
	
	private String extraerStringDeCelda(Cell cell)
	{
		try
		{
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String cadena = cell.getStringCellValue();
			return cadena==null?"":cadena;
		}
		catch(Exception ex)
		{
			return "";
		}
	}
	
	/////////////////////////////////////////////////////7
	public Map<String, String> getSmap1() {
		return smap1;
	}
	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}
	
	public String getSmap1Json(){
		String r=null;
		try{
			r=JSONUtil.serialize(smap1);
		}
		catch (JSONException ex){
			logger.error("error al convertir smap a json",ex);
		}
		return r;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getRespuestaOculta() {
		return respuestaOculta;
	}
	public void setRespuestaOculta(String respuestaOculta) {
		this.respuestaOculta = respuestaOculta;
	}
	public boolean isExito() {
		return exito;
	}
	public void setExito(boolean exito) {
		this.exito = exito;
	}
	public File getCenso() {
		return censo;
	}
	public void setCenso(File censo) {
		this.censo = censo;
	}
	public List<Map<String, Object>> getOlist1() {
		return olist1;
	}
	public void setOlist1(List<Map<String, Object>> olist1) {
		this.olist1 = olist1;
	}
	public List<Map<String, String>> getDatosValidacion() {
		return datosValidacion;
	}
	
	public void setDatosValidacion(List<Map<String, String>> datosValidacion) {
		this.datosValidacion = datosValidacion;
	}


	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}


	public HashMap<String, String> getParams() {
		return params;
	}


	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}


	public KernelManagerSustituto getKernelManagerSustituto() {
		return kernelManagerSustituto;
	}


	public void setKernelManagerSustituto(KernelManagerSustituto kernelManagerSustituto) {
		this.kernelManagerSustituto = kernelManagerSustituto;
	}


	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}


	public void setCatalogosManager(CatalogosManager catalogosManager) {
		this.catalogosManager = catalogosManager;
	}


	public Ice2sigsService getIce2sigsService() {
		return ice2sigsService;
	}


	public void setIce2sigsService(Ice2sigsService ice2sigsService) {
		this.ice2sigsService = ice2sigsService;
	}

	public List<HashMap<String, String>> getDatosTablas() {
		return datosTablas;
	}

	public void setDatosTablas(List<HashMap<String, String>> datosTablas) {
		this.datosTablas = datosTablas;
	}

	public List<Map<String, String>> getDatosInformacionAdicional() {
		return datosInformacionAdicional;
	}

	public void setDatosInformacionAdicional(List<Map<String, String>> datosInformacionAdicional) {
		this.datosInformacionAdicional = datosInformacionAdicional;
	}
	
    public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}
	
	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getValidacionGeneral() {
		return validacionGeneral;
	}

	public void setValidacionGeneral(String validacionGeneral) {
		this.validacionGeneral = validacionGeneral;
	}
}
