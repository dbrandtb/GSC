package mx.com.aon.catbo.web;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ReasignacionCasoVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.service.AdministracionCasosManager2;
import mx.com.aon.catbo.service.ProcesoManager;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.ConnectionCallInterceptor;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class ReasignarCasoAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1363545454489L;
	
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ReasignarCasoAction.class);
	
	private transient AdministracionCasosManager administracionCasosManager;
	private transient ProcesoManager procesoManager;
	private transient AdministracionCasosManager2 administracionCasosManager2;

	private List<ReasignacionCasoVO> listaReasignacionCasoVO;
	private List<CasoVO> csoGrillaListReasignacionCasoUsr;
	
	private String nmCaso;
	private String cdUsuMov;
	private String cdUsuario;
	private String cdRolmat;
	private String cdUsuarioOld;
	private String cdUsuarioNew;
	private String cdNivatn;
	
	private String usuarioMov;
	private String usuarioOld;
	private String usuarioNew;
	private String estado;
	private String cdmatriz;
	private String cdperson;
	private String cdformatoorden;
	private String flag;
	private List listaCasos;
	
	private boolean success;
	
	public String cmdGuardarReasignacionCasoClick()throws Exception{

		String messageResult = "";
	    try
	    {    
        	CasoVO casoVO = new CasoVO();
        	casoVO.setNmCaso(nmCaso);
        	casoVO.setCdUsuMov(cdUsuMov);
        	casoVO.setCdNivatn(cdNivatn);
        	logger.debug("dentro del metodo cmdGuardarReasignacionCasoClick()...");
        	logger.debug("con los parametros "+nmCaso+", "+cdUsuMov+" y "+cdNivatn);
        	UserVO userVO = new UserVO();
			ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
            userVO = tl.get();
			if(userVO != null && userVO.getUser()!= null){
                logger.debug("seteando en el caso el usuario logueado al sistema: " + userVO.getUser());
                casoVO.setCdUsuario(userVO.getUser());
			}
    		logger.debug("llamando al metodo guardaReasignacion("+nmCaso+", "+userVO.getUser()+", "+listaReasignacionCasoVO.get(0).getCdModulo());
			messageResult = procesoManager.guardaReasignacion(nmCaso, userVO.getUser(), listaReasignacionCasoVO);
	    	addActionMessage(messageResult);
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
	
	public String cmdGuardarReasignacionCasoUsuarioClick()throws Exception{

		String messageResult = "";
	    try
	    {    
	    	String estado="ASIGNACASO";
	    	
		   	messageResult = procesoManager.reasignarCasos(csoGrillaListReasignacionCasoUsr, estado);
		   	
		   	addActionMessage(messageResult);
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
	
	public String cmdGuardarMovimientoCasoClick()throws Exception
	{
		String messageResult = "";
        try
        {
        	CasoVO casoVO = new CasoVO();
        	casoVO.setNmCaso(nmCaso);
        	casoVO.setCdUsuMov(cdUsuMov);
        	casoVO.setCdNivatn(cdNivatn);
        	
        	messageResult = administracionCasosManager.guardarMovimientoCaso(casoVO);
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
	
	public String cmdRegresarConsultaCasoClick()throws Exception{
		return "regresarPantallaConsultaCaso";
	}
	
	public String cmdConfigurarMatrizTareaClick()throws Exception{
		return "regresarPantallaConfigurarMatrizTarea";
	}
	
	public AdministracionCasosManager getAdministracionCasosManager() {
		return administracionCasosManager;
	}

	public void setAdministracionCasosManager(
			AdministracionCasosManager administracionCasosManager) {
		this.administracionCasosManager = administracionCasosManager;
	}

	public String getNmCaso() {
		return nmCaso;
	}

	public void setNmCaso(String nmCaso) {
		this.nmCaso = nmCaso;
	}

	public String getCdUsuMov() {
		return cdUsuMov;
	}

	public void setCdUsuMov(String cdUsuMov) {
		this.cdUsuMov = cdUsuMov;
	}

	public String getCdUsuario() {
		return cdUsuario;
	}

	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}

	public String getCdRolmat() {
		return cdRolmat;
	}

	public void setCdRolmat(String cdRolmat) {
		this.cdRolmat = cdRolmat;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCdUsuarioOld() {
		return cdUsuarioOld;
	}

	public void setCdUsuarioOld(String cdUsuarioOld) {
		this.cdUsuarioOld = cdUsuarioOld;
	}

	public String getCdUsuarioNew() {
		return cdUsuarioNew;
	}

	public void setCdUsuarioNew(String cdUsuarioNew) {
		this.cdUsuarioNew = cdUsuarioNew;
	}

	public List<CasoVO> getCsoGrillaListReasignacionCasoUsr() {
		return csoGrillaListReasignacionCasoUsr;
	}

	public void setCsoGrillaListReasignacionCasoUsr(
			List<CasoVO> csoGrillaListReasignacionCasoUsr) {
		this.csoGrillaListReasignacionCasoUsr = csoGrillaListReasignacionCasoUsr;
	}

	public String getCdNivatn() {
		return cdNivatn;
	}

	public void setCdNivatn(String cdNivatn) {
		this.cdNivatn = cdNivatn;
	}

	public ProcesoManager getProcesoManager() {
		return procesoManager;
	}

	public void setProcesoManager(ProcesoManager procesoManager) {
		this.procesoManager = procesoManager;
	}

	public List<ReasignacionCasoVO> getListaReasignacionCasoVO() {
		return listaReasignacionCasoVO;
	}

	public void setListaReasignacionCasoVO(
			List<ReasignacionCasoVO> listaReasignacionCasoVO) {
		this.listaReasignacionCasoVO = listaReasignacionCasoVO;
	}

	public String getUsuarioMov() {
		return usuarioMov;
	}

	public void setUsuarioMov(String usuarioMov) {
		this.usuarioMov = usuarioMov;
	}

	public String getUsuarioOld() {
		return usuarioOld;
	}

	public void setUsuarioOld(String usuarioOld) {
		this.usuarioOld = usuarioOld;
	}

	public String getUsuarioNew() {
		return usuarioNew;
	}

	public void setUsuarioNew(String usuarioNew) {
		this.usuarioNew = usuarioNew;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List getListaCasos() {
		return listaCasos;
	}

	public void setListaCasos(List listaCasos) {
		this.listaCasos = listaCasos;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCdmatriz() {
		return cdmatriz;
	}

	public void setCdmatriz(String cdmatriz) {
		this.cdmatriz = cdmatriz;
	}

	public String getCdperson() {
		return cdperson;
	}

	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}

	public String getCdformatoorden() {
		return cdformatoorden;
	}

	public void setCdformatoorden(String cdformatoorden) {
		this.cdformatoorden = cdformatoorden;
	}

	public void setAdministracionCasosManager2(
			AdministracionCasosManager2 administracionCasosManager2) {
		this.administracionCasosManager2 = administracionCasosManager2;
	}
	
	
}