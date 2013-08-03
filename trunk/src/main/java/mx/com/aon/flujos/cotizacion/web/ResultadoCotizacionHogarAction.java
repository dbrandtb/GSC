package mx.com.aon.flujos.cotizacion.web;

import java.util.ArrayList;

import mx.com.aon.flujos.cotizacion.model.DesgloseCoberturaVO;
import mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO;
import mx.com.aon.flujos.cotizacion.service.CotizacionService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author sergio.ramirez
 *
 */
public class ResultadoCotizacionHogarAction extends PrincipalCotizacionAction {

	/**
	 *@serial
	 *Serial Version 
	 */
	private static final long serialVersionUID = 7477747339252126302L;
	private static final transient Log log= LogFactory.getLog(ResultadoCotizacionHogarAction.class);
	private boolean success;
	private CotizacionService cotizacionManager;
	private ArrayList <ResultadoCotizacionVO> listaResultadoCotizacionhogar;
	private DesgloseCoberturaVO parametrosEntrada;
	private ArrayList<ResultadoCotizacionVO> listaCotizacionModificada;
	
	private double sumaMensual;
	private double sumaAnual;
	
	/**
	 * @throws Exception
	 *@return INPUT 
	 */
	public String execute() throws Exception{
		
		return INPUT;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 * Metodo que carga la lista de Dezglose de Cotizacion para el Hogar.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cargaDatosCotizacion()throws Exception{
		parametrosEntrada = new DesgloseCoberturaVO();
		
		parametrosEntrada.setUsuario("SROMAN");
		parametrosEntrada.setCdUnieco("1");
		parametrosEntrada.setEstado("W");
		parametrosEntrada.setNmPoliza("13617");
		parametrosEntrada.setNmSituac("1");
		parametrosEntrada.setNmSupleme("0");
		parametrosEntrada.setCdElemen("3002");
		parametrosEntrada.setCdRamo("2");
		parametrosEntrada.setCdTipsit("AU");
		parametrosEntrada.setRegion("ME");
		parametrosEntrada.setPais("1");
		parametrosEntrada.setIdioma("1");
		
		
		listaResultadoCotizacionhogar = cotizacionManager.getResultadoCotizacion(parametrosEntrada);
		session.put("LISTA_RESULTADO_COTIZACION_HOGAR", listaResultadoCotizacionhogar);
		for (ResultadoCotizacionVO element : listaResultadoCotizacionhogar) {
			if(element.getSwOblig().equals("1")){
				element.setSwOblig("Si");
			}
			if(element.getSwOblig().equals("0")){
				element.setSwOblig("No");
			}
			if(element.getDsTipsit().equalsIgnoreCase("Obligatorio")){
				element.setSwOblig(null);
			}
		success=true;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 * Metodo que suma la prima mensual y Anual de Cotizacion.
	 */
	@SuppressWarnings("unchecked")
	public String cargaSumas() throws Exception{
		log.debug("entering  to the method");
		if(session.containsKey("LISTA_RESULTADO_COTIZACION_HOGAR")){
			ArrayList<ResultadoCotizacionVO> listaResultado = (ArrayList<ResultadoCotizacionVO>) session.get("LISTA_RESULTADO_COTIZACION_HOGAR");
			log.debug("LISTA_VALORES--::--" + listaResultado.size());
			for (ResultadoCotizacionVO resultadoCotizacionVO : listaResultado) {
				log.debug("numeros--->"+ resultadoCotizacionVO.getPrimaFormap());
				if(StringUtils.isNumeric(resultadoCotizacionVO.getPrimaFormap())){
					log.debug("valores de la lista:"+resultadoCotizacionVO.getPrimaFormap());
					double valores=0.0;
					valores = Double.parseDouble(resultadoCotizacionVO.getPrimaFormap());
					sumaMensual += valores;
					log.debug("sumaMensual----->"+sumaMensual);
					success=true;
				}
				if(StringUtils.isNumeric(resultadoCotizacionVO.getMnPrima())){
					double values=0.0;
					values = Double.parseDouble(resultadoCotizacionVO.getMnPrima());
					sumaAnual +=values;
					log.debug("sumaAnual------>"+ sumaAnual);
					success=true;
				}
			
		
		}
			success=true;
		}
		return SUCCESS;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 * Metodo que se encarga de Obtener los datos que se modificaron desde el grid.
	 */
	public String recuperaModificados() throws Exception{
		log.debug("::Entro al metodo recuperar listas::");
		log.debug("lista"+ listaCotizacionModificada);
		for(ResultadoCotizacionVO lisResultadoCotizacion: listaCotizacionModificada){
			log.debug("combos oblig--->"+ lisResultadoCotizacion.getSwOblig());
			lisResultadoCotizacion.getSwOblig();
			log.debug("suma Asegurada--->"+ lisResultadoCotizacion.getSumaAseg());
			lisResultadoCotizacion.getSumaAseg();
			success=true;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
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
	 * @param cotizacionManager
	 */	
	public void setCotizacionManager(CotizacionService cotizacionManager) {
		this.cotizacionManager = cotizacionManager;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<ResultadoCotizacionVO> getListaResultadoCotizacionhogar() {
		return listaResultadoCotizacionhogar;
	}
	/**
	 * 
	 * @param listaResultadoCotizacionhogar
	 */
	public void setListaResultadoCotizacionhogar(
			ArrayList<ResultadoCotizacionVO> listaResultadoCotizacionhogar) {
		this.listaResultadoCotizacionhogar = listaResultadoCotizacionhogar;
	}	

	/**
	 * 
	 * @return
	 */
	public double getSumaMensual() {
		return sumaMensual;
	}

	/**
	 * 
	 * @param sumaMensual
	 */
	public void setSumaMensual(double sumaMensual) {
		this.sumaMensual = sumaMensual;
	}

	/**
	 * 
	 * @return
	 */
	public double getSumaAnual() {
		return sumaAnual;
	}

	/**
	 * 
	 * @param sumaAnual
	 */
	public void setSumaAnual(double sumaAnual) {
		this.sumaAnual = sumaAnual;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<ResultadoCotizacionVO> getListaCotizacionModificada() {
		return listaCotizacionModificada;
	}

	/**
	 * 
	 * @param listaCotizacionModificada
	 */
	public void setListaCotizacionModificada(
			ArrayList<ResultadoCotizacionVO> listaCotizacionModificada) {
		this.listaCotizacionModificada = listaCotizacionModificada;
	}
	/**
	 * 
	 * @return
	 */
	public DesgloseCoberturaVO getParametrosEntrada() {
		return parametrosEntrada;
	}
	/**
	 * 
	 * @param parametrosEntrada
	 */
	public void setParametrosEntrada(DesgloseCoberturaVO parametrosEntrada) {
		this.parametrosEntrada = parametrosEntrada;
	}
	
	
}
