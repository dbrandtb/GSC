package mx.com.aon.flujos.cotizacion.web;

import java.util.HashMap;
import java.util.Map;

import mx.com.aon.configurador.pantallas.model.PantallaVO;
import mx.com.aon.flujos.cotizacion.service.CotizacionService;

public class TestPantallaAction extends PrincipalCotizacionAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4970991366394628251L;
	
	private String cdElemento;
	private String cdRamo;
	private String cdTitulo;
	private String cdConjunto;
	private String cdPantalla;
	
	private String item;
	
	private CotizacionService cotizacionManager;
	
	/**
	 * @param cotizacionManager the cotizacionManager to set
	 */
	public void setCotizacionManager(CotizacionService cotizacionManager) {
		this.cotizacionManager = cotizacionManager;
	}
	
	
	@Override
	public String execute() throws Exception {
		logger.debug("Entrnado a buscar pantalla");
		
		item = "{\"xtype\":\"textfield\", \"fieldLabel\":\"Dato Test\", \"name\":\"datoTest\" }";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("cdConjunto", "60");
		parameters.put("cdPantalla", "41");
		
		PantallaVO pantalla = cotizacionManager.getPantallaTest(parameters);
		
		logger.debug("pantalla obtenida es " + pantalla.getDsArchivo());
		logger.debug("pantalla final obtenida es " + pantalla.getDsArchivoSec() );
		
		item = pantalla.getDsArchivoSec();
		
		
		
		return SUCCESS;
	}
	
	
	public String test() throws Exception {
		logger.debug("Entrnado a buscar pantalla");		
		
		Map<String,String> parameters = new HashMap<String,String>();
		
		logger.debug("cdConjunto "+ cdConjunto);
		logger.debug("cdPantalla" + cdPantalla);
		
		
		parameters.put("cdConjunto", cdConjunto);
		parameters.put("cdPantalla", cdPantalla);
		
		PantallaVO pantalla = cotizacionManager.getPantallaTest(parameters);
		
		logger.debug("pantalla obtenida es " + pantalla.getDsArchivo());
		logger.debug("pantalla final obtenida es " + pantalla.getDsArchivoSec() );
		
		item = pantalla.getDsArchivoSec();
		
		
		
		return SUCCESS;
	}



	/**
	 * @return the cdElemento
	 */
	public String getCdElemento() {
		return cdElemento;
	}



	/**
	 * @param cdElemento the cdElemento to set
	 */
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}



	/**
	 * @return the cdRamo
	 */
	public String getCdRamo() {
		return cdRamo;
	}



	/**
	 * @param cdRamo the cdRamo to set
	 */
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}



	/**
	 * @return the cdTitulo
	 */
	public String getCdTitulo() {
		return cdTitulo;
	}



	/**
	 * @param cdTitulo the cdTitulo to set
	 */
	public void setCdTitulo(String cdTitulo) {
		this.cdTitulo = cdTitulo;
	}



	/**
	 * @return the item
	 */
	public String getItem() {
		return item;
	}



	/**
	 * @param item the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}


	/**
	 * @return the cdConjunto
	 */
	public String getCdConjunto() {
		return cdConjunto;
	}


	/**
	 * @param cdConjunto the cdConjunto to set
	 */
	public void setCdConjunto(String cdConjunto) {
		this.cdConjunto = cdConjunto;
	}


	/**
	 * @return the cdPantalla
	 */
	public String getCdPantalla() {
		return cdPantalla;
	}


	/**
	 * @param cdPantalla the cdPantalla to set
	 */
	public void setCdPantalla(String cdPantalla) {
		this.cdPantalla = cdPantalla;
	}


}
