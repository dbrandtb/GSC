package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.ConfiguracionNumeracionEndososVO;
import mx.com.aon.portal.service.ConfigurarNumeracionEndososManager;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Action que atiende las peticiones de abm que vienen de la pantalla.
 *
 */
public class ConfiguracionNumeracionEndososAction extends ActionSupport{

	private static final long serialVersionUID = 169861245657147844L;
	private String cdElemento;
	private String cdUniEco;
	private String cdRamo;
	private String cdPlan;
	private String nmPoliEx;
	private String indCalc;
	private String nmInicial;
	private String nmFinal;
	private String nmActual;
	private String otExpres;
	private String accion;
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConfiguracionNumeracionEndososAction.class);
 
	
	private transient ConfigurarNumeracionEndososManager configurarNumeracionEndososManager;
	
	private List<ConfiguracionNumeracionEndososVO> regNumeracionEndosos;
	
	private boolean success;
	
	public String irAlgunaPaginaClick()throws Exception
	{
		return "";
	}
	
	/**
	 * Metodo que realiza la actualizacion o insercion de un registro de Numeracion de endosos.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGuardarClick() throws Exception
	{
		String messageResult = "";
		try
		{
			ConfiguracionNumeracionEndososVO configuracionNumeracionEndososVO = new ConfiguracionNumeracionEndososVO();
			
			configuracionNumeracionEndososVO.setCdElemento(cdElemento);
			configuracionNumeracionEndososVO.setCdPlan(cdPlan);
			configuracionNumeracionEndososVO.setCdRamo(cdRamo);
			configuracionNumeracionEndososVO.setCdUniEco(cdUniEco);
			configuracionNumeracionEndososVO.setIndCalc(indCalc);
			configuracionNumeracionEndososVO.setNmActual(nmActual);
			configuracionNumeracionEndososVO.setNmFinal(nmFinal);
			configuracionNumeracionEndososVO.setNmInicial(nmInicial);
			configuracionNumeracionEndososVO.setNmPoliEx(nmPoliEx);
			configuracionNumeracionEndososVO.setOtExpres(otExpres);
			configuracionNumeracionEndososVO.setAccion(accion);
			
			messageResult = configurarNumeracionEndososManager.guardarOActualizarNumeracionEndosos(configuracionNumeracionEndososVO);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
     }
	
	/**
	 * Metodo que realiza la eliminacion de un registro de Numeracion de endosos.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBorrarClick() throws Exception
	{
		String messageResult = "";
		try
		{
			ConfiguracionNumeracionEndososVO configuracionNumeracionEndososVO = new ConfiguracionNumeracionEndososVO();
			
			configuracionNumeracionEndososVO.setCdElemento(cdElemento);
			configuracionNumeracionEndososVO.setCdUniEco(cdUniEco);
			configuracionNumeracionEndososVO.setCdRamo(cdRamo);
			
			messageResult = configurarNumeracionEndososManager.borrarNumeracionEndosos(configuracionNumeracionEndososVO);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
     }
	
	/**
	 * Metodo que busca y obtiene un unicoregistro de Numeracion de endosos.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetClick()throws Exception{
		try
		{
			regNumeracionEndosos = new ArrayList<ConfiguracionNumeracionEndososVO>();
			ConfiguracionNumeracionEndososVO registro = configurarNumeracionEndososManager.getNumeracionEndosos(cdElemento, cdUniEco, cdRamo);
			regNumeracionEndosos.add(registro);
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getCdUniEco() {
		return cdUniEco;
	}

	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getCdPlan() {
		return cdPlan;
	}

	public void setCdPlan(String cdPlan) {
		this.cdPlan = cdPlan;
	}

	public String getNmPoliEx() {
		return nmPoliEx;
	}

	public void setNmPoliEx(String nmPoliEx) {
		this.nmPoliEx = nmPoliEx;
	}

	public String getIndCalc() {
		return indCalc;
	}

	public void setIndCalc(String indCalc) {
		this.indCalc = indCalc;
	}

	public String getNmInicial() {
		return nmInicial;
	}

	public void setNmInicial(String nmInicial) {
		this.nmInicial = nmInicial;
	}

	public String getNmFinal() {
		return nmFinal;
	}

	public void setNmFinal(String nmFinal) {
		this.nmFinal = nmFinal;
	}

	public String getNmActual() {
		return nmActual;
	}

	public void setNmActual(String nmActual) {
		this.nmActual = nmActual;
	}

	public String getOtExpres() {
		return otExpres;
	}

	public void setOtExpres(String otExpres) {
		this.otExpres = otExpres;
	}

	public void setConfigurarNumeracionEndososManager(
			ConfigurarNumeracionEndososManager configurarNumeracionEndososManager) {
		this.configurarNumeracionEndososManager = configurarNumeracionEndososManager;
	}

	public List<ConfiguracionNumeracionEndososVO> getRegNumeracionEndosos() {
		return regNumeracionEndosos;
	}

	public void setRegNumeracionEndosos(
			List<ConfiguracionNumeracionEndososVO> regNumeracionEndosos) {
		this.regNumeracionEndosos = regNumeracionEndosos;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
	
}