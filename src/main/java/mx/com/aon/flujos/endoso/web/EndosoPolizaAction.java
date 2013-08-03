package mx.com.aon.flujos.endoso.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.VariableKernel;
import mx.com.aon.flujos.cotizacion.model.ObjetoCotizacionVO;
import mx.com.aon.flujos.endoso.model.TarificarVO;
import mx.com.aon.flujos.endoso.service.EndosoManager;
import mx.com.aon.pdfgenerator.PDFGenerator;
import mx.com.aon.pdfgenerator.PDFGeneratorEndosos;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.utils.AONCatwebUtils;
import mx.com.aon.utils.Constantes;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author sergio.ramirez
 * 
 */
public class EndosoPolizaAction extends PrincipalEndosoAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5786521722352071671L;
	
	
	//para especificar que es de proceso de renovacion
	private static final String RENOVACION = "ren";
	private static final String ENDOSO = "end";
	
	//Para la definicion de los tipos de caratula
	private static final String CARATULA_A = "A";
	private static final String CARATULA_D = "D";
	
	private static final String EXISTEN_CAMBIOS = "1";

	private EndosoManager endosoManager;
	private List<ObjetoCotizacionVO> endososList;
	private boolean success;
	private boolean indicador;

	private String cdUnieco;
	private String cdRamo;
	private String estado;
	private String nmPoliza;
	private String nmSituac;
	private String cdContar;
	private String cdGarant;
	
	private String nsuplogi;
	private String nmSuplem;
	private String nmSuplogi;
	
	private double total;
    private String totalString;
	private String nmEndoso;
	private String dsFormaPag;
	private String totalPagar;
	private String totalPagarF;
	private String nmrecsub;
	private String resultadoValidaPoliza;
	private String mensajeError;
	
	private TarificarVO resTarificar;
	
	private String dsDescripcion;
	private String cdInciso;
	private String status;
	private String aseguradora;
	private String poliza;
	private String producto;
	private String Origen;
	private String comentario;
	
	private String fechaefectividadendoso;
	private String mensajeEndoso;
    /**
     * Contiene el tipo de procedimiento
     */
    private String proc;
    
    /**
     * Para las caratulas y recibos
     */
    private String namePdf;
    private String namePdfRecibo;
    
	
	public String borraSessiones() throws Exception {
		logger.debug("borraSessiones getEndososPoliza########");
		session.remove("LISTA_ENDOSOS_POLIZA");
		return INPUT;
	}

	public String validaPoliza() throws Exception{
		logger.debug("***EndosoPolizaAction validaPoliza***");
		if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
            logger.debug("globalVarVO	:"+globalVarVO);
        }
        
		cdUnieco 		= globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo 			= globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado 			= globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza 		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSuplem		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("cdunieco",	cdUnieco);
        params.put("cdramo",	cdRamo);
        params.put("estado",	estado);
        params.put("nmpoliza",	nmPoliza);
        params.put("nmsuplem",	nmSuplem);
        params.put("nmsituac",  "0");
        
        logger.debug("El valor de proc (proceso) es: "+this.proc);
        
        if(this.proc != null){
        	if(RENOVACION.equals(this.proc)){
        		params.put("proc","1");
        	}else 
        		if(ENDOSO.equals(this.proc)){
            	params.put("proc","2");
        	}
        }
        
        //WrapperResultados res = endosoManager.getResultadoWrapper(params, "VALIDA_TARIFICACION_ENDOSOS_POLIZA");
        WrapperResultados res = endosoManager.getResultadoWrapper(params, "TARIFICA_INCISOS_FALTANTES");
        if (logger.isDebugEnabled()) {
	        logger.debug("res			:"+res.getResultado());
	        logger.debug("res MsgText	:"+res.getMsgText());
	        logger.debug("res MsgTitle	:"+res.getMsgTitle());
        }
        
        boolean validaCalculaEndoso = true;
        WrapperResultados existenCambiosResult = null;
        if(res.getResultado().equals("0") || StringUtils.isBlank(res.getResultado()) ) {
            validaCalculaEndoso = calculaEndoso(globalVarVO); 
            if (validaCalculaEndoso) {
                /////////////// Se verifica si existen cambios en endosos
                existenCambiosResult = endosoManager.getResultadoWrapper(params, "EXISTEN_CAMBIOS");
                if (logger.isDebugEnabled()) {
	                logger.debug(".. existenCambiosResult			: " + existenCambiosResult.getResultado());
	                logger.debug(".. existenCambiosResult MsgText	: " + res.getMsgText());
	                logger.debug(".. existenCambiosResult MsgTitle	: " + res.getMsgTitle());
                }
                
                if (EXISTEN_CAMBIOS.equalsIgnoreCase(existenCambiosResult.getResultado())) {
                	resultadoValidaPoliza = "ok";	
                	session.put("TARIFICA", Constantes.SI);
                } else {
                	resultadoValidaPoliza = "fail";
                    mensajeError = "No se realizaron cambios en Endosos";
                }                
            } else {
                resultadoValidaPoliza = "fail";
                mensajeError = "Ha fallado la tarificación del Endoso";
            }
        } else {
        	resultadoValidaPoliza = "fail";
        	mensajeError = "Ha fallado la tarificación del Endoso";
        }
                
        return SUCCESS;
	}
	
	/**
	 * Método encargado de deshacer los cambios hechos en el endoso en caso de que
	 * éste no se haya conformado
	 * @return String
	 * @throws Exception
	 */
	public String psacaEndoso() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("-> EndosoPolizaAction.psacaEndoso");
			logger.debug("	 El valor de proc (proceso) es: " + this.proc);
		}
		        
		String exstCmbs = (String) session.get("TARIFICA");
		if (logger.isDebugEnabled()) {
        	logger.debug(":: TARIFICA en sesion  : " + exstCmbs);
        }
		
		if (ENDOSO.equals(this.proc)) {	
	        if (StringUtils.isBlank(exstCmbs) || !Constantes.SI.equalsIgnoreCase(exstCmbs)) {
	        	borraEndoso();	        	
	        }
		} else if (RENOVACION.equals(this.proc)) {			
	        if (StringUtils.isBlank(exstCmbs) || !Constantes.SI.equalsIgnoreCase(exstCmbs)) {
	        	reversaMaquilla();
	        }
		}
		
		session.remove("TARIFICA");
                
        return SUCCESS;
	}
	
	/**
     * Método encargado de calcular Valor Endoso
	 * @param globalVarVO 
     * @return
	 * @throws ApplicationException 
     */
    private boolean calculaEndoso(GlobalVariableContainerVO globalVarVO) throws ApplicationException {
        if (logger.isDebugEnabled()) {
            logger.debug("-> calculaEndoso");
        }
        
        cdUnieco        = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo          = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado          = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza        = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSituac        = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSituacion());
        nmSuplem        = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
        
        String fechaIniVal = (String) session.get(FECHA_EFECTIVIDAD);
        if (logger.isDebugEnabled()) {
            logger.debug("::: fechaIniVal :: " + fechaIniVal);
        }
        
        UserVO usuario = (UserVO)session.get("USUARIO");
        if (logger.isDebugEnabled()) {
            logger.debug("CDELEMENTO: " + usuario.getEmpresa().getElementoId()); 
        }
        String cdElemento = usuario.getEmpresa().getElementoId();
        
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("cdunieco", cdUnieco );
        parameters.put("cdramo", cdRamo);
        parameters.put("estado", estado);
        parameters.put("nmpoliza", nmPoliza);
        parameters.put("nmsituac", nmSituac);
        parameters.put("nmsuplem", nmSuplem);
        parameters.put("feinival", fechaIniVal);
        parameters.put("cdelemen", cdElemento);
        
        WrapperResultados res = endosoManager.getResultadoWrapper(parameters, "CALCULA_VALOR_ENDOSOS");
        if (logger.isDebugEnabled()) {
            logger.debug(":: res MsgText  : " + res.getMsgText());
            logger.debug(":: res MsgTitle : " + res.getMsgTitle());
        }
          
        if (StringUtils.isBlank(res.getMsgText()) && StringUtils.isBlank(res.getMsgTitle())) {
            return true;
        } else {
            return false;    
        }        
    }

    public String getValorEndoso() throws Exception{
		logger.debug("***EndosoPolizaAction getValorEndoso***");
		if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
            logger.debug("globalVarVO	:"+globalVarVO);
        }
        
		cdUnieco 		= globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo 			= globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado 			= globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza 		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSituac 		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSituacion());
        nmSuplem		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("cdunieco", cdUnieco);
		params.put("cdramo",   cdRamo);
		params.put("estado",   estado);
		params.put("nmpoliza", nmPoliza);
		params.put("nmsituac", nmSituac);
		params.put("nmsuplem", nmSuplem);
		
		resTarificar = endosoManager.getResultadoTarificar(params, "ENDOSOS_OBTIENE_VALOR_TARIFICAR");
		logger.debug("resTarificar:		"+resTarificar.toString());
		
		success = true;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String getEndososPoliza() throws Exception {
		logger.debug("EndosoPolizaAction getEndososPoliza########");
		
		if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
            logger.debug("globalVarVO	:"+globalVarVO);
        }
        cdUnieco 		= globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo 			= globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado 			= globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza 		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSituac 		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSituacion());
        nmSuplem        = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
        
        Map<String, String> params = new HashMap<String, String>();
		params.put("cdUnieco", cdUnieco);
		params.put("cdRamo", cdRamo);
		params.put("estado", estado);
		params.put("nmPoliza", nmPoliza);
		params.put("nmSituac", nmSituac);
		params.put("cdContar", cdContar);
		params.put("cdGarant", cdGarant);
		//endososList = endosoManager.getEndosos(params);
		//totalCount = endososList.size();
		
        if (logger.isDebugEnabled()) {
            logger.debug("getEndososPoliza params:	"+params);
        }
		
		try{
            PagedList pagedList = endosoManager.getPagedList(params, "OBTIENE_TARIFICACION_ENDOSOS_POLIZA", start, limit);
    		endososList = pagedList.getItemsRangeList();
    		totalCount	= pagedList.getTotalItems();
    			
    		if (!endososList.isEmpty()) {
    			session.put("LISTA_ENDOSOS_POLIZA", endososList);
    		}
            
            if (logger.isDebugEnabled()) {
                logger.debug("session LISTA_ENDOSOS_POLIZA :" + session.get("LISTA_ENDOSOS_POLIZA"));
            }
		}catch(Exception e){
            if (logger.isDebugEnabled()) {
                logger.error("getEndososPoliza No se encontraron datos:  "+e);
            }
		}
        
        success = true;
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String cargaImportes() throws Exception {
		logger.debug("EndosoPolizaAction cargaImportes########");
		List<ObjetoCotizacionVO> listaEndosos = new ArrayList<ObjetoCotizacionVO>();
		logger.debug("session LISTA_ENDOSOS_POLIZA :"+session.get("LISTA_ENDOSOS_POLIZA"));
		if (session.containsKey("LISTA_ENDOSOS_POLIZA")) {
			listaEndosos = (List<ObjetoCotizacionVO>) session.get("LISTA_ENDOSOS_POLIZA");
			for (ObjetoCotizacionVO objetoCotizacionVO : listaEndosos) {
				logger.debug("importes-->"+objetoCotizacionVO.getNmImport());
				double importes=0.0;
				importes=Double.parseDouble(objetoCotizacionVO.getNmImport());
				total += importes;
				logger.debug("TOTAL:"+total);
                
				success=true;
			}
            
            totalString = AONCatwebUtils.formatNumber(new BigDecimal(total));
            logger.debug("TOTAL String "+totalString);
		}
		return SUCCESS;
	}
	
	public String obtieneDetalleTarificar()throws Exception{
		logger.debug("-> EndosoPolizaAction obtieneDetalleTarificar");
		
		if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
            logger.debug("globalVarVO	:"+globalVarVO);
        }
        
		cdUnieco 		= globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo 			= globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado 			= globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza 		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSuplem		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
        nmSuplogi		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplementoLogico());
		
		Map<String, String> params = new HashMap<String, String>();
		/*params.put("cdunieco", "1");
		params.put("cdramo", "500");
		params.put("estado", "M");
		params.put("nmpoliza", "96");
		params.put("nmsuplem", "245480612000000001");
		params.put("nsuplogi", "60");*/
        params.put("cdunieco", cdUnieco);
        params.put("cdramo", cdRamo);
        params.put("estado", estado);
        params.put("nmpoliza", nmPoliza);
        params.put("nmsuplem", nmSuplem);
        params.put("nsuplogi", nmSuplogi);
		
		endososList = endosoManager.detalleTarificar(params,"ENDOSOS_OBTIENE_DATOS_TARIFICAR");
		logger.debug("obtieneDetalleTarificar params:	"+params);
		
		if(endososList.size()>0){
			for(ObjetoCotizacionVO ob : endososList){
				logger.debug("@@@ Estoy adentro de for endososList: ");
				nmEndoso 		= ob.getNmEndoso();
				dsFormaPag 		= ob.getDsFormaPag();
				totalPagar 		= ob.getTotalPagar();
				totalPagarF 	= ob.getTotalPagarF();
				nmrecsub        = ob.getNmrecsub();
			}
		}
		
		success = true;
		return SUCCESS;
	}
	
	public String borraEndoso()throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug("EndosoPolizaAction borraEndoso########");
		}

		if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);            
        }
		
		if (logger.isDebugEnabled()) {
			logger.debug("globalVarVO	:"+globalVarVO);
		}
        
		cdUnieco 		= globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo 			= globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado 			= globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza 		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSuplem		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
        nmSuplogi		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplementoLogico());
        
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("cdunieco",cdUnieco );
		parameters.put("cdramo", cdRamo);
		parameters.put("estado", estado);
		parameters.put("nmpoliza", nmPoliza);
		parameters.put("nsuplogi", nmSuplogi);
		parameters.put("nmsuplem", nmSuplem);
		endosoManager.sacaEndoso(parameters);
		success=true;
		indicador=true;
		return SUCCESS;
	}
	
	/**
	 * Método utilizado para eversar Maquilla dentro de la renovación
	 * @return String
	 * @throws Exception
	 */
	public String reversaMaquilla()throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("-> EndosoPolizaAction.reversaMaquilla");
		}

		if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);            
        }
		
		if (logger.isDebugEnabled()) {
			logger.debug("globalVarVO	: " + globalVarVO);
		}
        
		cdUnieco = globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo 	 = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado   = globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("cdunieco",cdUnieco );
		parameters.put("cdramo", cdRamo);
		parameters.put("estado", estado);
		parameters.put("nmpoliza", nmPoliza);
		
		endosoManager.reversaMaquilla(parameters);
		success = true;
		indicador = true;
		return SUCCESS;
	}
	
	/**
	 * Metodo que se ejecuta al confirmar el endoso de una poliza
	 * @return String 
	 * @throws Exception
	 */
	public String confirmarTarificar() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug("**EndosoPolizaAction confirmarTarificar***");
			logger.debug("EndosoPolizaAction obtieneDetalleTarificar");
	        logger.debug("comentario   :"+comentario);
		}
		
		if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);            
        }
		
		if (logger.isDebugEnabled()) {
			logger.debug("globalVarVO	:"+globalVarVO);
		}
        
		cdUnieco 		= globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo 			= globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado 			= globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza 		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        nmSuplem		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
        nmSuplogi		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplementoLogico());
        
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("cdunieco",cdUnieco );
		parameters.put("cdramo", cdRamo);
		parameters.put("estado", estado);
		parameters.put("nmpoliza", nmPoliza);
		parameters.put("nsuplogi", nmSuplogi);
		parameters.put("nmsuplem", nmSuplem);
		//TODO Se mandara como parametro 30 de forma fija, cambiar para que sea variable.
		parameters.put("cdtipsup", "30");
        parameters.put("dscoment", comentario);
		
        WrapperResultados res = endosoManager.getResultadoWrapper(parameters, "ENDOSOS_CONFIRMAR_TARIFICAR");
        if (logger.isDebugEnabled()) {
	        logger.debug("res MsgId	:"+res.getMsgId());
	        logger.debug("res MsgTitle	:"+res.getMsgTitle());
        }
        
        String nmSuplex=res.getMsgText();
        
        namePdf="";
        namePdfRecibo="";
        String tipoCaratula="";
        
        mensajeEndoso = endosoManager.obtieneMensajeEndoso(res.getMsgId());
    	
    	if(StringUtils.isBlank(mensajeEndoso)) mensajeEndoso = "Error";
    	if(StringUtils.isNotBlank(nmSuplex)){
    		mensajeEndoso += " ";
    		mensajeEndoso += nmSuplex;
    		
    	}
    		
    	if (logger.isDebugEnabled()) {
    		logger.debug("El mensaje generado del Endoso: "+mensajeEndoso);
    	}    	
    	
        if (session.containsKey("LISTA_ENDOSOS_POLIZA")) {
        	List<ObjetoCotizacionVO> listaEndososPdf = (List<ObjetoCotizacionVO>) session.get("LISTA_ENDOSOS_POLIZA");
        	
        	if(listaEndososPdf!=null){
        		ArrayList<String> nmSituacs= new ArrayList<String>();
				String nmSituaTmp;
				for (ObjetoCotizacionVO objetoCotizacionVO : listaEndososPdf) {
					if(objetoCotizacionVO.getNmSituac()!=null){
						nmSituaTmp=objetoCotizacionVO.getNmSituac();
					
						if(!nmSituacs.contains(nmSituaTmp)){	
							nmSituacs.add(nmSituaTmp);
							tipoCaratula = PDFGeneratorEndosos.generaCaratulaEndosos(cdUnieco, cdRamo, estado, nmPoliza, nmSuplem, nmSituaTmp);
							namePdf+="reporte_caratula_endoso_"+cdUnieco+cdRamo+nmPoliza +"_"+tipoCaratula+".pdf|";
							
							if(tipoCaratula!=null && (CARATULA_A.equals(tipoCaratula) || CARATULA_D.equals(tipoCaratula))){
								int reciboGenerado = PDFGenerator.generaReciboPagoPdf("_endoso",cdUnieco, cdRamo, estado, nmPoliza, nmSuplem);
								if(reciboGenerado== 0){
									namePdfRecibo+="reporte_recibo_pago"+"_endoso"+cdUnieco+cdRamo+nmPoliza + ".pdf|";
									if (logger.isDebugEnabled()) {
										logger.debug("Generacion correcta de un recibo con nmSituac: "+nmSituaTmp+" y con una caratula tipo: "+tipoCaratula);
									}
								}
							}
						}
					}
					
				}
				if (logger.isDebugEnabled()) {
					logger.debug(" El tamaño de nmSituacs fue: "+nmSituacs.size());
				}
				if (logger.isDebugEnabled()) {
					if(nmSituacs.isEmpty()){
						logger.debug("  ERROR: No existen valores en los tipos de situacion de la Poliza, por lo que no se puede generar las caratula PDF ");
					}
				}
        	}
        	
        	
        	
        	
        }
        
        
        /*
        logger.debug(" PDF 2222: ");
        PDFGeneratorEndosos.generaCaratulaEndosos("1", "500", "M", "6772", "245488612000000000", "1");
        PDFGeneratorEndosos.generaCaratulaEndosos("1", "500", "M", "6696", "245487512000000000", "1");
        
        //para B
        logger.debug(" PDF BBBB: ");
        PDFGeneratorEndosos.generaCaratulaEndosos("1", "500", "M", "182", "245491412000000000", "1");
        //para el B con pasadp, no hay nmordina
        //PDFGeneratorEndosos.generaCaratulaEndosos("1", "500", "M", "182", "245487512000000000", "1");
        
        //para A 
        //PDFGeneratorEndosos.generaCaratulaEndosos("1", "500", "M", "6696", "245487512000000000", "1");
        //PDFGeneratorEndosos.generaCaratulaEndosos("1", "500", "M", "6696", "245485312000000000", "1");
      
        //para D
        logger.debug(" PDF DDDD: ");
        PDFGeneratorEndosos.generaCaratulaEndosos("1", "500", "M", "6707", "245491612000000000", "1");
        */
        success = true;
		return SUCCESS;
	}
	
	/**
	 * 
	 * @param endosoManager
	 */
	public void setEndosoManager(EndosoManager endosoManager) {
		this.endosoManager = endosoManager;
	}
	/**
	 * 
	 * @return endososList
	 */
	public List<ObjetoCotizacionVO> getEndososList() {
		return endososList;
	}
	/**
	 * 
	 * @param endososList
	 */
	public void setEndososList(List<ObjetoCotizacionVO> endososList) {
		this.endososList = endososList;
	}
	/**
	 * 
	 * @return success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * 
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * 
	 * @return cdUnieco
	 */
	public String getCdUnieco() {
		return cdUnieco;
	}
	/**
	 * 
	 * @param cdUnieco
	 */
	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}
	/**
	 * 
	 * @return cdRamo
	 */
	public String getCdRamo() {
		return cdRamo;
	}
	/**
	 * 
	 * @param cdRamo
	 */
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	/**
	 * 
	 * @return estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * 
	 * @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * 
	 * @return nmPoliza
	 */
	public String getNmPoliza() {
		return nmPoliza;
	}
	/**
	 * 
	 * @param nmPoliza
	 */
	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}
	/**
	 * 
	 * @return nmSituac
	 */
	public String getNmSituac() {
		return nmSituac;
	}
	/**
	 * 
	 * @param nmSituac
	 */
	public void setNmSituac(String nmSituac) {
		this.nmSituac = nmSituac;
	}
	/**
	 * 
	 * @return cdContar
	 */
	public String getCdContar() {
		return cdContar;
	}
	/**
	 * 
	 * @param cdContar
	 */
	public void setCdContar(String cdContar) {
		this.cdContar = cdContar;
	}
	/**
	 * 
	 * @return cdGarant
	 */
	public String getCdGarant() {
		return cdGarant;
	}
	/**
	 * 
	 * @param cdGarant
	 */
	public void setCdGarant(String cdGarant) {
		this.cdGarant = cdGarant;
	}
	/**
	 * 
	 * @return total
	 */
	public double getTotal() {
		return total;
	}
	/**
	 * 
	 * @param total
	 */
	public void setTotal(double total) {
		this.total = total;
	}
	/**
	 * 
	 * @return nsuplogi
	 */
	public String getNsuplogi() {
		return nsuplogi;
	}
	/**
	 * 
	 * @param nsuplogi
	 */
	public void setNsuplogi(String nsuplogi) {
		this.nsuplogi = nsuplogi;
	}
	/**
	 * 
	 * @return nmSuplem
	 */
	public String getNmSuplem() {
		return nmSuplem;
	}
	/**
	 * 
	 * @param nmSuplem
	 */
	public void setNmSuplem(String nmSuplem) {
		this.nmSuplem = nmSuplem;
	}
	/**
	 * 
	 * @return indicador
	 */
	public boolean isIndicador() {
		return indicador;
	}
	/**
	 * 
	 * @param indicador
	 */
	public void setIndicador(boolean indicador) {
		this.indicador = indicador;
	}

	public String getNmEndoso() {
		return nmEndoso;
	}

	public void setNmEndoso(String nmEndoso) {
		this.nmEndoso = nmEndoso;
	}

	public String getDsFormaPag() {
		return dsFormaPag;
	}

	public void setDsFormaPag(String dsFormaPag) {
		this.dsFormaPag = dsFormaPag;
	}

	public String getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(String totalPagar) {
		this.totalPagar = totalPagar;
	}

	public String getTotalPagarF() {
		return totalPagarF;
	}

	public void setTotalPagarF(String totalPagarF) {
		this.totalPagarF = totalPagarF;
	}

	public EndosoManager getEndosoManager() {
		return endosoManager;
	}

	public String getNmSuplogi() {
		return nmSuplogi;
	}

	public void setNmSuplogi(String nmSuplogi) {
		this.nmSuplogi = nmSuplogi;
	}

	public String getResultadoValidaPoliza() {
		return resultadoValidaPoliza;
	}

	public void setResultadoValidaPoliza(String resultadoValidaPoliza) {
		this.resultadoValidaPoliza = resultadoValidaPoliza;
	}

	public TarificarVO getResTarificar() {
		return resTarificar;
	}

	public void setResTarificar(TarificarVO resTarificar) {
		this.resTarificar = resTarificar;
	}

	public String getDsDescripcion() {
		return dsDescripcion;
	}

	public void setDsDescripcion(String dsDescripcion) {
		this.dsDescripcion = dsDescripcion;
	}

	public String getCdInciso() {
		return cdInciso;
	}

	public void setCdInciso(String cdInciso) {
		this.cdInciso = cdInciso;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAseguradora() {
		return aseguradora;
	}

	public void setAseguradora(String aseguradora) {
		this.aseguradora = aseguradora;
	}

	public String getPoliza() {
		return poliza;
	}

	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getOrigen() {
		return Origen;
	}

	public void setOrigen(String origen) {
		Origen = origen;
	}
	
	/**
     * @return the totalString
     */
    public String getTotalString() {
        return totalString;
    }

    /**
     * @param totalString the totalString to set
     */
    public void setTotalString(String totalString) {
        this.totalString = totalString;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

	public void setProc(String proc) {
		this.proc = proc;
	}

	public String getProc() {
		return proc;
	}
	
	public void setFechaefectividadendoso(String fechaefectividadendoso) {
		this.fechaefectividadendoso = fechaefectividadendoso;
	}

	public String getFechaefectividadendoso() {
		return fechaefectividadendoso;
	}

	public String getNmrecsub() {
		return nmrecsub;
	}

	public void setNmrecsub(String nmrecsub) {
		this.nmrecsub = nmrecsub;
	}

	public String getNamePdf() {
		return namePdf;
	}

	public void setNamePdf(String namePdf) {
		this.namePdf = namePdf;
	}

	public String getNamePdfRecibo() {
		return namePdfRecibo;
	}

	public void setNamePdfRecibo(String namePdfRecibo) {
		this.namePdfRecibo = namePdfRecibo;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public String getMensajeEndoso() {
		return mensajeEndoso;
	}

	public void setMensajeEndoso(String mensajeEndoso) {
		this.mensajeEndoso = mensajeEndoso;
	}
	
}
