package mx.com.aon.catbo.web;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.AccesoEstructuraRolUsuarioVO;
import mx.com.aon.catbo.model.ActividadUsuarioVO;
import mx.com.aon.catbo.model.FormatoTimeStampVO;
import mx.com.aon.catbo.service.AccesoEstructuraRolUsuarioManager;
import mx.com.aon.portal.service.ConsultaActividadUsuarioManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;
import mx.com.aon.portal.util.WrapperResultados;


import org.apache.log4j.Logger;

public class ListaActividadUsuarioAction extends AbstractListAction {

	private static final long serialVersionUID = -792632067915334348L;
	private Calendar calIni;
	private Calendar calFin;
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaActividadUsuarioAction.class);

	private transient ConsultaActividadUsuarioManager consultaActividadUsuarioManager;

	private List<ActividadUsuarioVO> listaActividadUsuario; 
	private String pv_dsusuari_i;
	private String pv_url_i;
	private String dsUser;
	private String feInicial;
	private String feFinal;
	private String hrInicial;// = "03:00 AM";
	private String hrFinal;// = "03:15 AM";
	private int inicioExport;
	
	private String dia;
	private String mes;
	private String anio;
	private String hora;
	private String minutos;
	private String hora2;
	private String minutos2;
	private int horaAux;
	
	Timestamp timeI;
	Timestamp timeF;

	private FormatoTimeStampVO formatoTimeStampVOInicial;
	private FormatoTimeStampVO formatoTimeStampVOFinal;
	
	private String fechaInicialDeTabla;
	private String fechaFinalDeTabla;
	
	private String auxiliarInicial;
	private String auxiliarFinal;
	private String auxiliarMinInicial;
	private String auxiliarMinFinal;
	
	
	private int itemsPorPaginas; 
	private String dir;
	private String sort;
	/**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
		
	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
	

	public String getFormato() {
		return formato;
	}


	public void setFormato(String formato) {
		this.formato = formato;
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public ExportMediator getExportMediator() {
		return exportMediator;
	}


	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}


	public String getPv_dsusuari_i() {
		return pv_dsusuari_i;
	}


	public void setPv_dsusuari_i(String pv_dsusuari_i) {
		this.pv_dsusuari_i = pv_dsusuari_i;
	}


	public String getDsUser() {
		return dsUser;
	}


	public void setDsUser(String dsUser) {
		this.dsUser = dsUser;
	}

	
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws ApplicationException {
		Boolean  bandIni = false;
		Boolean  bandFin = false;
		String pmAmIn = "";
		String pmAmFn = "";
		try {
			formatoTimeStampVOInicial = new FormatoTimeStampVO();
			formatoTimeStampVOFinal = new FormatoTimeStampVO();
			
			formatoTimeStampVOInicial.setHora(0);
			formatoTimeStampVOInicial.setMinuto(0);
			
			formatoTimeStampVOFinal.setHora(23);
			formatoTimeStampVOFinal.setMinuto(59);
			
			
		/*	if (feInicial == null || feInicial.equals("")){
				fechaInicialDeTabla = consultaActividadUsuarioManager.buscarFechaInicialActividadesUsuario(pv_dsusuari_i, pv_url_i, start, limit);
				StringTokenizer tokensFI = new StringTokenizer(fechaInicialDeTabla);
					while (tokensFI.hasMoreTokens()){
						feInicial = tokensFI.nextToken();
						hrInicial =  tokensFI.nextToken();
						pmAmIn = tokensFI.nextToken();
					}
					this.hrInicial = hrInicial + " " + pmAmIn; 
					
			};*/
				
				
		/*	if (feFinal == null || feFinal.equals("")){
				fechaFinalDeTabla = consultaActividadUsuarioManager.buscarFechaFinalActividadesUsuario(pv_dsusuari_i, pv_url_i, start, limit);
				StringTokenizer tokensFF = new StringTokenizer(fechaFinalDeTabla);
					while (tokensFF.hasMoreTokens()){
						feFinal = tokensFF.nextToken();
						hrFinal = tokensFF.nextToken();
						pmAmFn = tokensFF.nextToken();
					}
					this.hrFinal = hrFinal + " " + pmAmFn;
					};*/
			
			if (feInicial != null && feInicial.compareTo("") != 0){
				if (hrInicial != null){
					bandIni = true;
					StringTokenizer tokensFeI = new StringTokenizer(feInicial,"/");
					while (tokensFeI.hasMoreTokens()){
						formatoTimeStampVOInicial.setDia(Integer.parseInt(tokensFeI.nextToken()));
						formatoTimeStampVOInicial.setMes(Integer.parseInt(tokensFeI.nextToken()));
						formatoTimeStampVOInicial.setAnio(Integer.parseInt(tokensFeI.nextToken()));
						
					}
					StringTokenizer tokensHoAmPm = new StringTokenizer(hrInicial);
					while (tokensHoAmPm.hasMoreTokens()){
						hora =  tokensHoAmPm.nextToken();
						formatoTimeStampVOInicial.setAmopm(tokensHoAmPm.nextToken());
					}
					StringTokenizer tokensHoIni = new StringTokenizer(hora,":");
					while (tokensHoIni.hasMoreTokens()){
						formatoTimeStampVOInicial.setHora(Integer.parseInt(tokensHoIni.nextToken()));
						formatoTimeStampVOInicial.setMinuto(Integer.parseInt(tokensHoIni.nextToken()));
					}
					
				}
			}
			
			if (feFinal != null && feFinal.compareTo("")!= 0 ){
				if (hrFinal != null){
					bandFin = true;
					StringTokenizer tokensFeI = new StringTokenizer(feFinal,"/");
					while (tokensFeI.hasMoreTokens()){
						formatoTimeStampVOFinal.setDia(Integer.parseInt(tokensFeI.nextToken()));
						formatoTimeStampVOFinal.setMes(Integer.parseInt(tokensFeI.nextToken()));
						formatoTimeStampVOFinal.setAnio(Integer.parseInt(tokensFeI.nextToken()));
						
					}
					StringTokenizer tokensHoAmPm = new StringTokenizer(hrFinal);
					while (tokensHoAmPm.hasMoreTokens()){
						hora2 =  tokensHoAmPm.nextToken();
						formatoTimeStampVOFinal.setAmopm(tokensHoAmPm.nextToken());
					}
					StringTokenizer tokensHoIni = new StringTokenizer(hora2,":");
					while (tokensHoIni.hasMoreTokens()){
						formatoTimeStampVOFinal.setHora(Integer.parseInt(tokensHoIni.nextToken()));
						formatoTimeStampVOFinal.setMinuto(Integer.parseInt(tokensHoIni.nextToken()));
					}
					
				}
			}
			if(bandIni){
				if(formatoTimeStampVOInicial.getAmopm().equals("PM"))
				{ 
					if (formatoTimeStampVOInicial.getHora() != 12 ){
						this.horaAux = formatoTimeStampVOInicial.getHora() + 12;
						formatoTimeStampVOInicial.setHora(horaAux);}
				}else{
					if(formatoTimeStampVOInicial.getHora() == 12){
						formatoTimeStampVOInicial.setHora(0);
					}
			}};
			
			if(bandFin){
				if(formatoTimeStampVOFinal.getAmopm().equals("PM"))
				{ 
					if (formatoTimeStampVOFinal.getHora() != 12 ){
						this.horaAux = formatoTimeStampVOFinal.getHora() + 12;
						formatoTimeStampVOFinal.setHora(horaAux);}
				}else{
					if(formatoTimeStampVOFinal.getHora() == 12){
						formatoTimeStampVOFinal.setHora(0);
					}
			}};	
			formatoTimeStampVOInicial.setSegundo(0);
			formatoTimeStampVOFinal.setSegundo(0);
			
			if(bandIni){
			calIni = Calendar.getInstance();
			calIni.set(formatoTimeStampVOInicial.getAnio(), formatoTimeStampVOInicial.getMes()-1,formatoTimeStampVOInicial.getDia(),formatoTimeStampVOInicial.getHora(), formatoTimeStampVOInicial.getMinuto(),formatoTimeStampVOInicial.getSegundo());
			long timeIni = calIni.getTimeInMillis() ;
			timeI = new Timestamp(timeIni);
			timeI.setNanos(0);
			if (logger.isDebugEnabled()) {
				logger.debug("AquiTa: timeI= "+timeI.getTime());
			};
			};
			
			if(bandFin){
			calFin = Calendar.getInstance();
			calFin.set(formatoTimeStampVOFinal.getAnio() , formatoTimeStampVOFinal.getMes()-1,formatoTimeStampVOFinal.getDia(),formatoTimeStampVOFinal.getHora(), formatoTimeStampVOFinal.getMinuto(),formatoTimeStampVOFinal.getSegundo());
			long timeFin = calFin.getTimeInMillis() ;
			timeF = new Timestamp(timeFin);
			timeF.setNanos(0);
			if (logger.isDebugEnabled()) {
				logger.debug("AquiTa: timeF= "+timeF.getTime());
			}
			};
			
			if(start != 0){
				limit = start + limit;
			};
			WrapperResultados wrapperResultados = consultaActividadUsuarioManager.buscarActividadesUsuario(pv_dsusuari_i, pv_url_i,timeI,timeF,/*sort,dir,*/ start, limit);
			//PagedList pagedList = consultaActividadUsuarioManager.buscarActividadesUsuario(pv_dsusuari_i, pv_url_i,timeI,timeF, start, limit);
			//listaActividadUsuario = pagedList.getItemsRangeList();
			//totalCount = pagedList.getTotalItems();
			listaActividadUsuario = wrapperResultados.getItemList();
			totalCount = Integer.parseInt(wrapperResultados.getResultado());
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}

	public String cmdExportarClick() throws Exception{
		Boolean  bandIni = false;
		Boolean  bandFin = false;
		String pmAmIn = "";
		String pmAmFn = "";
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		try {
			
			formatoTimeStampVOInicial = new FormatoTimeStampVO();
			formatoTimeStampVOFinal = new FormatoTimeStampVO();
			
			formatoTimeStampVOInicial.setHora(0);
			formatoTimeStampVOInicial.setMinuto(0);
			
			formatoTimeStampVOFinal.setHora(23);
			formatoTimeStampVOFinal.setMinuto(59);
			
			if (feInicial != null && feInicial.compareTo("") != 0){
				if (hrInicial != null){
					bandIni = true;
					StringTokenizer tokensFeI = new StringTokenizer(feInicial,"/");
					while (tokensFeI.hasMoreTokens()){
						formatoTimeStampVOInicial.setDia(Integer.parseInt(tokensFeI.nextToken()));
						formatoTimeStampVOInicial.setMes(Integer.parseInt(tokensFeI.nextToken()));
						formatoTimeStampVOInicial.setAnio(Integer.parseInt(tokensFeI.nextToken()));
						
					}
					StringTokenizer tokensHoAmPm = new StringTokenizer(hrInicial);
					while (tokensHoAmPm.hasMoreTokens()){
						hora =  tokensHoAmPm.nextToken();
						formatoTimeStampVOInicial.setAmopm(tokensHoAmPm.nextToken());
					}
					StringTokenizer tokensHoIni = new StringTokenizer(hora,":");
					while (tokensHoIni.hasMoreTokens()){
						formatoTimeStampVOInicial.setHora(Integer.parseInt(tokensHoIni.nextToken()));
						formatoTimeStampVOInicial.setMinuto(Integer.parseInt(tokensHoIni.nextToken()));
					}
					
				}
			}
			
			if (feFinal != null && feFinal.compareTo("")!= 0 ){
				if (hrFinal != null){
					bandFin = true;
					StringTokenizer tokensFeI = new StringTokenizer(feFinal,"/");
					while (tokensFeI.hasMoreTokens()){
						formatoTimeStampVOFinal.setDia(Integer.parseInt(tokensFeI.nextToken()));
						formatoTimeStampVOFinal.setMes(Integer.parseInt(tokensFeI.nextToken()));
						formatoTimeStampVOFinal.setAnio(Integer.parseInt(tokensFeI.nextToken()));
						
					}
					StringTokenizer tokensHoAmPm = new StringTokenizer(hrFinal);
					while (tokensHoAmPm.hasMoreTokens()){
						hora2 =  tokensHoAmPm.nextToken();
						formatoTimeStampVOFinal.setAmopm(tokensHoAmPm.nextToken());
					}
					StringTokenizer tokensHoIni = new StringTokenizer(hora2,":");
					while (tokensHoIni.hasMoreTokens()){
						formatoTimeStampVOFinal.setHora(Integer.parseInt(tokensHoIni.nextToken()));
						formatoTimeStampVOFinal.setMinuto(Integer.parseInt(tokensHoIni.nextToken()));
					}
					
				}
			}
			if(bandIni){
				if(formatoTimeStampVOInicial.getAmopm().equals("PM"))
				{ 
					if (formatoTimeStampVOInicial.getHora() != 12 ){
						this.horaAux = formatoTimeStampVOInicial.getHora() + 12;
						formatoTimeStampVOInicial.setHora(horaAux);}
				}else{
					if(formatoTimeStampVOInicial.getHora() == 12){
						formatoTimeStampVOInicial.setHora(0);
					}
			}};
			
			if(bandFin){
				if(formatoTimeStampVOFinal.getAmopm().equals("PM"))
				{ 
					if (formatoTimeStampVOFinal.getHora() != 12 ){
						this.horaAux = formatoTimeStampVOFinal.getHora() + 12;
						formatoTimeStampVOFinal.setHora(horaAux);}
				}else{
					if(formatoTimeStampVOFinal.getHora() == 12){
						formatoTimeStampVOFinal.setHora(0);
					}
			}};	
			formatoTimeStampVOInicial.setSegundo(0);
			formatoTimeStampVOFinal.setSegundo(0);
			
			if(bandIni){
			calIni = Calendar.getInstance();
			calIni.set(formatoTimeStampVOInicial.getAnio(), formatoTimeStampVOInicial.getMes()-1,formatoTimeStampVOInicial.getDia(),formatoTimeStampVOInicial.getHora(), formatoTimeStampVOInicial.getMinuto(),formatoTimeStampVOInicial.getSegundo());
			long timeIni = calIni.getTimeInMillis() ;
			timeI = new Timestamp(timeIni);
			timeI.setNanos(0);
			};
			
			if(bandFin){
			calFin = Calendar.getInstance();
			calFin.set(formatoTimeStampVOFinal.getAnio() , formatoTimeStampVOFinal.getMes()-1,formatoTimeStampVOFinal.getDia(),formatoTimeStampVOFinal.getHora(), formatoTimeStampVOFinal.getMinuto(),formatoTimeStampVOFinal.getSegundo());
			long timeFin = calFin.getTimeInMillis() ;
			timeF = new Timestamp(timeFin);
			timeF.setNanos(0);
			};
			
			
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			
			filename = "ActUsuario." + exportFormat.getExtension();
			
			TableModelExport model = consultaActividadUsuarioManager.getModel(pv_dsusuari_i, pv_url_i,timeI,timeF);
			
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}


	public String irActividadUsuarioResultadoClick(){
		return "actividadUsuarioResultado";
	}

	public String irActividadUsuarioClick(){
		return "actividadUsuario";
	}

	/*public ConsultaActividadUsuarioManager getConsultaActividadUsuarioManager() {
		return consultaActividadUsuarioManager;
	}*/



	public List<ActividadUsuarioVO> getListaActividadUsuario() {
		return listaActividadUsuario;
	}




	public void setListaActividadUsuario(
			List<ActividadUsuarioVO> listaActividadUsuario) {
		this.listaActividadUsuario = listaActividadUsuario;
	}




	public String getPv_url_i() {
		return pv_url_i;
	}




	public void setPv_url_i(String pv_url_i) {
		this.pv_url_i = pv_url_i;
	}




	public void setConsultaActividadUsuarioManager(
			ConsultaActividadUsuarioManager consultaActividadUsuarioManager) {
		this.consultaActividadUsuarioManager = consultaActividadUsuarioManager;
	}


	public String getFeInicial() {
		return feInicial;
	}


	public void setFeInicial(String feInicial) {
		this.feInicial = feInicial;
	}


	public String getFeFinal() {
		return feFinal;
	}


	public void setFeFinal(String feFinal) {
		this.feFinal = feFinal;
	}


	public String getHrInicial() {
		return hrInicial;
	}


	public void setHrInicial(String hrInicial) {
		this.hrInicial = hrInicial;
	}


	public String getHrFinal() {
		return hrFinal;
	}


	public void setHrFinal(String hrFinal) {
		this.hrFinal = hrFinal;
	}

	
	public String getDia() {
		return dia;
	}


	public void setDia(String dia) {
		this.dia = dia;
	}


	public String getMes() {
		return mes;
	}


	public void setMes(String mes) {
		this.mes = mes;
	}


	public String getAnio() {
		return anio;
	}


	public void setAnio(String anio) {
		this.anio = anio;
	}


	public String getHora() {
		return hora;
	}


	public void setHora(String hora) {
		this.hora = hora;
	}


	public String getMinutos() {
		return minutos;
	}


	public void setMinutos(String minutos) {
		this.minutos = minutos;
	}


	public String getHora2() {
		return hora2;
	}


	public void setHora2(String hora2) {
		this.hora2 = hora2;
	}


	public String getMinutos2() {
		return minutos2;
	}


	public void setMinutos2(String minutos2) {
		this.minutos2 = minutos2;
	}


	public Timestamp getTimeI() {
		return timeI;
	}


	public void setTimeI(Timestamp timeI) {
		this.timeI = timeI;
	}


	public Timestamp getTimeF() {
		return timeF;
	}


	public void setTimeF(Timestamp timeF) {
		this.timeF = timeF;
	}


	public int getHoraAux() {
		return horaAux;
	}


	public void setHoraAux(int horaAux) {
		this.horaAux = horaAux;
	}


	public String getAuxiliarInicial() {
		return auxiliarInicial;
	}


	public void setAuxiliarInicial(String auxiliarInicial) {
		this.auxiliarInicial = auxiliarInicial;
	}


	public String getAuxiliarFinal() {
		return auxiliarFinal;
	}


	public void setAuxiliarFinal(String auxiliarFinal) {
		this.auxiliarFinal = auxiliarFinal;
	}


	public String getAuxiliarMinInicial() {
		return auxiliarMinInicial;
	}


	public void setAuxiliarMinInicial(String auxiliarMinInicial) {
		this.auxiliarMinInicial = auxiliarMinInicial;
	}


	public String getAuxiliarMinFinal() {
		return auxiliarMinFinal;
	}


	public void setAuxiliarMinFinal(String auxiliarMinFinal) {
		this.auxiliarMinFinal = auxiliarMinFinal;
	}


	public String getFechaInicialDeTabla() {
		return fechaInicialDeTabla;
	}


	public void setFechaInicialDeTabla(String fechaInicialDeTabla) {
		this.fechaInicialDeTabla = fechaInicialDeTabla;
	}


	public String getFechaFinalDeTabla() {
		return fechaFinalDeTabla;
	}


	public void setFechaFinalDeTabla(String fechaFinalDeTabla) {
		this.fechaFinalDeTabla = fechaFinalDeTabla;
	}


	public int getItemsPorPaginas() {
		return itemsPorPaginas;
	}


	public void setItemsPorPaginas(int itemsPorPaginas) {
		this.itemsPorPaginas = itemsPorPaginas;
	}


	public int getInicioExport() {
		return inicioExport;
	}


	public void setInicioExport(int inicioExport) {
		this.inicioExport = inicioExport;
	}


	public String getDir() {
		return dir;
	}


	public void setDir(String dir) {
		this.dir = dir;
	}


	public String getSort() {
		return sort;
	}


	public void setSort(String sort) {
		this.sort = sort;
	}


	



	
}
