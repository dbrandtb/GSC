package mx.com.aon.portal.web;


import mx.com.aon.portal.model.EjecutivoCuentaVO;
import mx.com.aon.portal.service.EjecutivoManager;
import mx.com.aon.portal.service.EjecutivosCuentaManager;
import mx.com.aon.portal.service.MantenimientoEjecutivosCuentaManager;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;


/**
 * Action que atiende las solicitudes de abm la pantalla de ejecutivos por cuenta.
 *
 */
@SuppressWarnings("serial")
public class EjecutivosCuentaAction extends ActionSupport {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(EjecutivosCuentaAction.class);
	

	private transient EjecutivosCuentaManager ejecutivosCuentaManager;
	private transient EjecutivoManager ejecutivoManager;

	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans 
	 * con los valores de la consulta.
	 */
	@SuppressWarnings("unchecked")
	private List<EjecutivoCuentaVO> mEjecutivosCuentaList;

	    private String cdAgente;
	    private String cdTipage;
	    private String cdElemento;
	 	private String cdPerson;
	    private String cdEstado;
	    private String feInicio;
	    private String feFin;
	    private String cdTipRam;
	    private String cdRamo;
	    private String cdLinCta;
	    private String swNivelPosterior;
	    private String cdGrupo;	
	    private String accion;
	    private boolean success;

	
	/**
	 * Elimina un registro de la grilla previamente seleccionado de la pantalla de ejecutivos por cuenta. 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = ejecutivosCuentaManager.borrarEjecutivoCuenta(cdAgente, cdElemento);	
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
	 *  Agrega un nuevo ejecutivo a la cuenta.
	 * 
	 *  @return success
	 *  
	 *  @throws Exception 
	 */		
    public String cmdAgregarGuardarClick()throws Exception
    {
		String messageResult = "";
       try
        {
    	
    	   EjecutivoCuentaVO ejecutivoCuentaVO = new EjecutivoCuentaVO();
           
    	   ejecutivoCuentaVO.setCdAgente(cdAgente);
    	   ejecutivoCuentaVO.setCdPerson(cdPerson);
    	   ejecutivoCuentaVO.setCdEstado(cdEstado);
    	   ejecutivoCuentaVO.setFeInicio(feInicio);
    	   ejecutivoCuentaVO.setFeFin(feFin);
    	   ejecutivoCuentaVO.setCdTipRam(cdTipRam);
    	   ejecutivoCuentaVO.setCdRamo(cdRamo);
    	   ejecutivoCuentaVO.setCdGrupo(cdGrupo);
    	   ejecutivoCuentaVO.setCdLinCta(cdLinCta);
    	   ejecutivoCuentaVO.setCdElemento(cdElemento);
    	   ejecutivoCuentaVO.setCdTipage(cdTipage);//NUEVO
    	   ejecutivoCuentaVO.setAccion(accion);
    	   
    	   String _swNivelPosterior = (swNivelPosterior !=null && !swNivelPosterior.equals("") && swNivelPosterior.equals("on"))?"1":"0";
    	   if (swNivelPosterior == null) _swNivelPosterior = "0";
    	   ejecutivoCuentaVO.setSwNivelPosterior(_swNivelPosterior);
    	   
    	   messageResult = ejecutivoManager.agregarGuardarEjecutivoCuenta(ejecutivoCuentaVO);
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
	 *  Obtiene un ejecutivo de cuenta.
	 * 
	 *  @param cdAgente
	 *  @param cdPerson
	 *  
	 *  @throws Exception
	 */			
	public String cmdGetClick()throws Exception
	{

		try
		{
			
			mEjecutivosCuentaList = new ArrayList<EjecutivoCuentaVO>();
			EjecutivoCuentaVO ejecutivoCuentaVO = ejecutivosCuentaManager.getEjecutivoCuenta(cdAgente,cdElemento);
			mEjecutivosCuentaList.add(ejecutivoCuentaVO);
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
	
	
	public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	
	public String getCdTipRam() {
		return cdTipRam;
	}
	public void setCdTipRam(String cdTipRam) {
		this.cdTipRam = cdTipRam;
	}
	
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	
	public String getCdGrupo() {
		return cdGrupo;
	}
	public void setCdGrupo(String cdGrupo) {
		this.cdGrupo = cdGrupo;
	}
	
	public String getCdAgente() {
		return cdAgente;
	}
	public void setCdAgente(String cdAgente) {
		this.cdAgente = cdAgente;
	}
	public String getCdEstado() {
		return cdEstado;
	}
	public void setCdEstado(String cdEstado) {
		this.cdEstado = cdEstado;
	}
	public String getFeInicio() {
		return feInicio;
	}
	public void setFeInicio(String feInicio) {
		this.feInicio = feInicio;
	}
	public String getFeFin() {
		return feFin;
	}
	public void setFeFin(String feFin) {
		this.feFin = feFin;
	}
	public EjecutivosCuentaManager obtenEjecutivoCuentasManager() {
		return ejecutivosCuentaManager;
	}
	public void setEjecutivoCuentasManager(
			EjecutivosCuentaManager ejecutivoCuentasManager) {
		this.ejecutivosCuentaManager = ejecutivoCuentasManager;
	}
	public String getCdLinCta() {
		return cdLinCta;
	}
	public void setCdLinCta(String cdLinCta) {
		this.cdLinCta = cdLinCta;
	}
	public String getSwNivelPosterior() {
		return swNivelPosterior;
	}
	public void setSwNivelPosterior(String swNivelPosterior) {
		this.swNivelPosterior = swNivelPosterior;
	}
	public List<EjecutivoCuentaVO> getMEjecutivosCuentaList() {
		return mEjecutivosCuentaList;
	}
	public void setMEjecutivosCuentaList(
			List<EjecutivoCuentaVO> ejecutivosCuentaList) {
		mEjecutivosCuentaList = ejecutivosCuentaList;
	}
	public EjecutivosCuentaManager obtenEjecutivosCuentaManager() {
		return ejecutivosCuentaManager;
	}
	public void setEjecutivosCuentaManager(
			EjecutivosCuentaManager ejecutivosCuentaManager) {
		this.ejecutivosCuentaManager = ejecutivosCuentaManager;
	}
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	public boolean getSuccess() {return success;}
	public void setSuccess(boolean success) {this.success = success;}


	public String getAccion() {
		return accion;
	}


	public void setAccion(String accion) {
		this.accion = accion;
	}


	public void setEjecutivoManager(EjecutivoManager ejecutivoManager) {
		this.ejecutivoManager = ejecutivoManager;
	}


	public String getCdTipage() {
		return cdTipage;
	}


	public void setCdTipage(String cdTipage) {
		this.cdTipage = cdTipage;
	}


}