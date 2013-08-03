package mx.com.aon.flujos.cotizacion.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.catbo.model.AseguradoraVO;
import mx.com.aon.configurador.pantallas.model.PantallaVO;
import mx.com.aon.configurador.pantallas.model.components.GridVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.configurador.pantallas.model.components.ComboClearOnSelectVO;
import mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.ConsultaCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.DatosEntradaCotizaVO;
import mx.com.aon.flujos.cotizacion.model.DesgloseCoberturaVO;
import mx.com.aon.flujos.cotizacion.model.MPoliObjVO;
import mx.com.aon.flujos.cotizacion.model.ObjetoCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.ObjetoValorVO;
import mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.RolVO;
import mx.com.aon.flujos.model.TatriParametrosVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.export.model.TableModelExport;

import com.biosnet.ice.ext.elements.form.ComboControl;
import com.biosnet.ice.ext.elements.form.ExtElement;
import com.biosnet.ice.ext.elements.form.TextFieldControl;


/**
 * 
 * Interfaz Manager para el control y visualizacion de datos
 * 
 * @author aurora.lozada
 * 
 */


public interface CotizacionService {
    
	/**
	 * Método para la obtención del padre de un combo. 
	 * 
	 * @param parameters Listado de parametros para la invocacion del servicio en la base de datos
	 * @return comboPadre
	 * @throws ApplicationException
	 */
	public List<ComboClearOnSelectVO> getComboPadre(Map<String, String> parameters) throws ApplicationException;  

	/**
	 * Método para la obtención del padre de un combo para instrumentos de pago. 
	 * 
	 * @param parameters Listado de parametros para la invocacion del servicio en la base de datos
	 * @return comboPadre
	 * @throws ApplicationException
	 */
	public List<ComboClearOnSelectVO> getComboPadreInstPago(Map<String, String> parameters) throws ApplicationException;
    
	
    /**
     * Metodo para la obtencion de los parametros necesarios en ejecucion de cada componente en pantalla
     * 
     * @param parameters Listado de parametros para la invocacion del servicio en la base de datos
     * @return Objeto de tipo TatriParametrosVO
     * @throws ApplicationException
     */
    public TatriParametrosVO getTatriParams(Map<String,String> parameters)throws ApplicationException;
    
    /**
     * Metodo para la obtencion de los registros resultado de cotizar
     *  
     * @param parameters Listado de parametros para la invocacion del servicio en la base de datos
     * @return Objeto de tipo GridVO
     * @throws ApplicationException
     */
    public GridVO getResultados(Map<String,String> parameters)throws ApplicationException;
    
    /**
     * Metodo para la obtencion de coberturas
     *  
     * @param parameters Listado de parametros para la invocacion del servicio en la base de datos
     * @return Una lista de coberturas
     * @throws ApplicationException
     */
    public List <?> getCoberturas(Map<String,String> parameters)throws ApplicationException;
    
    
    /**
     * Metodo para la obtencion de la ayuda para cierta cobertura
     *  
     * @param parameters Listado de parametros para la invocacion del servicio en la base de datos
     * @return Objeto de tipo AyudaCoberturaCotizacionVO
     * @throws ApplicationException
     */
    public AyudaCoberturaCotizacionVO getAyudaCobertura(Map<String,String> parameters)throws ApplicationException;
    
    
    /**
     * Metodo para la obtencion de la pantalla final 
     *  
     * @param parameters Listado de parametros para la invocacion del servicio en la base de datos
     * @return Objeto de tipo PantallaVO
     * @throws ApplicationException
     */
    public PantallaVO getPantallaFinal(Map<String,String> parameters)throws ApplicationException;
  
    String getBotonGuardaCotizacion() throws ApplicationException;
    
    void saveCotizacionResultado(String cdusuari, ResultadoCotizacionVO cotizacionCell) throws ApplicationException;
    
    public List<AseguradoraVO> getAseguradoras(ResultadoCotizacionVO cotizacionCell) throws ApplicationException;
    
    List<ConsultaCotizacionVO> getCotizacion(Map<String, String> parameters) throws ApplicationException;
    
    @SuppressWarnings("unchecked")
    public PagedList getCotizacion(Map<String, String> params, int start, int limit) throws ApplicationException;
    
    void deleteCotizacionesConsulta(String cdusuari, List<ConsultaCotizacionVO> listaConsultaCotizaciones) throws ApplicationException;
    
    
    @SuppressWarnings("unchecked")
	List getRecibos(Map<String, String> parameters) throws ApplicationException;
    
    List<MPoliObjVO> gerMPoliObj(Map<String, String> parameters) throws ApplicationException;
    
    List<RolVO> gerRolesDC(Map<String, String> parameters) throws ApplicationException;
    
    void pMovMPoliPer(Map<String,String> parameters) throws ApplicationException;
    /**
     * 
     * @param parametrosEntrada
     * @return
     * @throws ApplicationException
     */
	public ArrayList<ResultadoCotizacionVO> getResultadoCotizacion(
			DesgloseCoberturaVO parametrosEntrada) throws ApplicationException;
	
	
	
	PantallaVO getPantallaTest(Map<String,String> parameters)throws ApplicationException;
	
	/**
	 * 
	 * @param objetoCotizacionVO
	 * @return
	 * @throws ApplicationException
	 */
	public List<ObjetoCotizacionVO> getListaEquipo(ObjetoCotizacionVO objetoCotizacionVO) throws ApplicationException;
	/**
	 * 
	 * @param cdramo
	 * @param cdtipsit
	 * @return
	 * @throws ApplicationException
	 */
	public BaseObjectVO getEtiqueta(String cdramo, String cdtipsit)throws ApplicationException;
	/**
	 * 
	 * @param cdramo
	 * @param cdtipsit
	 * @return 
	 * @throws ApplicationException
	 */
	public List<BaseObjectVO> getTipos(String cdramo, String cdtipsit)throws ApplicationException;
	/**
	 * 
	 * @param principalObjeto
	 * @throws ApplicationException
	 */
	public void servicioSatelite(ObjetoCotizacionVO principalObjeto)throws ApplicationException;
	/**
	 * 
	 * @param objetoValorVO
	 * @throws ApplicationException
	 */
	public void servicioEquipoEspecial(ObjetoValorVO objetoValorVO) throws ApplicationException;
	/**
	 * 
	 * @param cdramo
	 * @return
	 * @throws ApplicationException
	 */
	
	public List<ExtElement> getItems(String claveRamo)throws ApplicationException;
	/**
	 * 
	 * @param claveObjeto
	 * @return
	 * @throws ApplicationException
	 */
	public List<ComboControl> getCombos(String claveObjeto) throws ApplicationException;	
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws ApplicationException
	 */
	public String guardaPersonaCotizacion(Map<String, String> parameters) throws ApplicationException;
	
	/**
	 * 
	 * @param parameters
	 * @throws ApplicationException
	 */
    public void movMPOLIAGR2(Map<String,String> parameters) throws ApplicationException;
    
    /**
     * 
     * @param parameters
     * @throws ApplicationException
     */
    public void movMTARJETA(Map<String,String> parameters) throws ApplicationException;
    /**
     * 
     * @param parameters
     * @return BaseObjectVO suplemento Fisico
     * @throws ApplicationException
     */
    public BaseObjectVO obtieneNmsuplem(Map<String,String> parameters) throws ApplicationException;
    /**
     * 
     * @param insertaObjeto
     * @return
     * @throws ApplicationException
     */
	public int getNumeroInsertaServicio(ObjetoCotizacionVO insertaObjeto)throws ApplicationException;
	/**
	 * 
	 * @param objetoCotizacion
	 * @return
	 * @throws ApplicationException
	 */
	public List<ExtElement> getAtributosEditables(ObjetoCotizacionVO objetoCotizacionVO)throws ApplicationException;
	/**
	 * 
	 * @param objetoCotizacionVO
	 * @return
	 * @throws ApplicationException
	 */
	public List<ComboControl> getCombosEdicion(ObjetoCotizacionVO objetoCotizacionVO) throws ApplicationException;
	
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws ApplicationException
	 */
	public List<TextFieldControl> getTVALOSIT3(Map<String, String> parameters) throws ApplicationException;
	
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws ApplicationException
	 */
	public List<DatosEntradaCotizaVO> getDatosEntradaCotiza(Map<String, String> parameters) throws ApplicationException;
	
	/**
	 * 
	 * @param parameters
	 * @throws ApplicationException
	 */
	public void clonaObjetos(Map<String, String> parameters) throws ApplicationException;
	
	/**
	 * 
	 * @param parameters
	 * @return
	 * @throws ApplicationException
	 */
	public RolVO obtieneAsegurado(Map<String, String> parameters) throws ApplicationException;
}
