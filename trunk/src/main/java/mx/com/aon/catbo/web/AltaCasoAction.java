package mx.com.aon.catbo.web;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.*;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.ConnectionCallInterceptor;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class AltaCasoAction extends ActionSupport implements SessionAware {
	
	//Agregado el 30-01-09 para solucionar problema del user en el alta de caso
	protected static ThreadLocal<UserVO> localUser = new ThreadLocal<UserVO>();
		
	private static final long serialVersionUID = 131121324354659L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AltaCasoAction.class);
	
	//private transient AdministradorGuardarCasoManager administradorGuardarCasoManager;
	private transient AdministracionCasosManager administracionCasosManager;
	
	private List<ListaEmisionCasoVO> listaEmisionVO;
	private List<CasoVO> mCasoListVO;
	
	private boolean success;
	
	private String messageResult;
	private String strUsuariosSeg;		
	
	private String cdperson;
	private String cdordentrabajo;
	private String nmcaso;
	private String codigoMensaje;
	
	private String wORIGEN;

    private Map session;


    public String cmdGuardarClick()throws Exception{
		try
		{	
			
			FormatoOrdenVO formatoOrdenVO = new FormatoOrdenVO();
			formatoOrdenVO.setListaEmisionVO(listaEmisionVO);
			CasoVO casoVO = new CasoVO();
			if(mCasoListVO.size() != 0){
				for(int i=0; i < mCasoListVO.size();i++){
					casoVO = mCasoListVO.get(i);					
				}
				casoVO.setStrUsuariosSeg(strUsuariosSeg);
			}
			UserVO userVO = new UserVO();
			ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
            userVO = tl.get();

			if(userVO != null && userVO.getUser()!= null){
                logger.debug("seteando en el caso el usuario logueado al sistema: " + userVO.getUser());
                casoVO.setCdUsuario(userVO.getUser());
			}
			
			logger.debug("LoggerGuardarNuevoCaso. Observacion: "+casoVO.getDsObservacion());
            ResultadoGeneraCasoVO res = administracionCasosManager.guardarNuevoCaso(casoVO, formatoOrdenVO);
			cdordentrabajo = (res.getCdOrdenTrabajo() != null && !res.getCdOrdenTrabajo().equals(""))?res.getCdOrdenTrabajo():"";
			nmcaso = (res.getNumCaso() != null && !res.getNumCaso().equals(""))?res.getNumCaso():"";
			logger.debug("nnmcaso y cdordentrabajo obtenidos: " + nmcaso + "   " + cdordentrabajo);
			/*
			 * Si el nmcaso viene con un # obtengo el codigo de mensaje y lo guardo en codigoMensaje, si no codigoMensaje=vacio
			 * 
			 * */
			try{
				int pos = nmcaso.indexOf('#');				
				if(pos != -1){
					codigoMensaje = nmcaso.substring(pos + 1,nmcaso.length());
					nmcaso = nmcaso.substring(0, pos);
				}else
				{
					codigoMensaje = "";
				}
				logger.debug("************** nmcaso " + nmcaso);
				logger.debug("************** codigoMensaje " + codigoMensaje);
			}catch(Exception e){
				logger.debug("Excepcion de substring para nmcaso " + nmcaso + ". " + e.getMessage());
			}			
            success = true;
            addActionMessage(nmcaso);
            return SUCCESS;
        }catch(ApplicationException ae)
		{	
        	logger.debug("ApplicationException. Mostrando excepcion: "+ae.getMessage());
            success = false;
            addActionError(ae.getMessage());
            return SUCCESS;

        }catch(Exception e)
		{	
        	logger.debug("Exception. Mostrando excepcion: "+e.getMessage());
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }		
        
	}
	
	public String cmdIrConsultaCasoClick()throws Exception{
		return "irPantallaConsultaDeCaso";
	}
	

	

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<ListaEmisionCasoVO> getListaEmisionVO() {
		return listaEmisionVO;
	}

	public void setListaEmisionVO(List<ListaEmisionCasoVO> listaEmisionVO) {
		this.listaEmisionVO = listaEmisionVO;
	}



	public String getMessageResult() {
		return messageResult;
	}

	public void setMessageResult(String messageResult) {
		this.messageResult = messageResult;
	}



	public List<CasoVO> getMCasoListVO() {
		return mCasoListVO;
	}

	public void setMCasoListVO(List<CasoVO> casoListVO) {
		mCasoListVO = casoListVO;
	}
	
	public String getStrUsuariosSeg() {
		return strUsuariosSeg;
	}

	public void setStrUsuariosSeg(String strUsuariosSeg) {
		this.strUsuariosSeg = strUsuariosSeg;
	}

	public void setAdministracionCasosManager(
			AdministracionCasosManager administracionCasosManager) {
		this.administracionCasosManager = administracionCasosManager;
	}

	public String getCdordentrabajo() {
		return cdordentrabajo;
	}

	public void setCdordentrabajo(String cdordentrabajo) {
		this.cdordentrabajo = cdordentrabajo;
	}

	public String getNmcaso() {
		return nmcaso;
	}

	public void setNmcaso(String nmcaso) {
		this.nmcaso = nmcaso;
	}


    public void setSession(Map map) {
        session = map;
    }

	public String getCdperson() {
		return cdperson;
	}

	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}

	public String getWORIGEN() {
		return wORIGEN;
	}

	public void setWORIGEN(String worigen) {
		wORIGEN = worigen;
	}

	public String getCodigoMensaje() {
		return codigoMensaje;
	}
}
