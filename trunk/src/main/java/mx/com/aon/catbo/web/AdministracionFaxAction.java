package mx.com.aon.catbo.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.*;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.service.AdministracionFaxManager;
import mx.com.aon.catbo.service.FaxDAO;
import mx.com.aon.catbo.service.MediaDAO;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AdministracionFaxAction extends ActionSupport implements SessionAware {
	
	private static final long serialVersionUID = 131121324354659L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AdministracionFaxAction.class);
	
	//private transient AdministradorGuardarCasoManager administradorGuardarCasoManager;
	private transient AdministracionFaxManager administracionFaxManager;
	private transient FaxDAO faxDAOJdbc;
	 
	private String nmcaso;
	private String nmfax;
	private String cdtipoar;
	private String feingreso;
	private String ferecepcion;
	private String nmpoliex;
	private String cdusuari;
	private String blarchivo; 
	private String cdatribu;
	private String otvalor;
	
	private String cmd;
	private String tipoArchivo;
	private String dsArchivo;
	private String cdmatriz;
	private String cdformatoorden;
	private int cdVariable;
	
	private String resultadoUpload;
	 
	private transient AdministracionCasosManager administracionCasosManager;
	
	private List<ListaEmisionCasoVO> listaEmisionVO;
	private List<CasoVO> mCasoListVO;
	
	private List<ListaFaxCasoVO> listaFaxesVO;
	private List<FaxesVO> mFaxListVO;
	
	private boolean success;
	
	private String messageResult;
	private String strUsuariosSeg;		

	private String cdordentrabajo;
	//private String nmcaso;
	private String cdperson;
	private String flag;

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

            UserVO userVO = (UserVO) session.get("contextUserVO");

			if(userVO != null && userVO.getUser()!= null){
                logger.debug("seteando en el caso el usuario logueado al sistema: " + userVO.getUser());
                casoVO.setCdUsuario(userVO.getUser());
			}


            ResultadoGeneraCasoVO res = administracionCasosManager.guardarNuevoCaso(casoVO, formatoOrdenVO);
			cdordentrabajo = res.getCdOrdenTrabajo();
			nmcaso = res.getNumCaso();
            success = true;
            addActionMessage("Caso Creado");
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
    
    
    /******************AdministracionFax*******************************/
  /*  public String guardarAdministracionFaxClick() throws ApplicationException{
		String messageResult = "";
		try{
			
			messageResult = administracionFaxManager.guardarAdministracionFax(nmcaso, nmfax, cdtipoar, feingreso, ferecepcion, nmpoliex, cdusuari, blarchivo);
			//pv_nmcaso_i, pv_nmfax_i, pv_cdtipoar_i, pv_feingreso_i, pv_ferecepcion_i, pv_nmpoliex_i, pv_cdusuari_i, pv_blarchivo_i
			success = true;
			addActionMessage(messageResult);
			return SUCCESS;
		}catch(ApplicationException ae){
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch(Exception e){
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
    */
   /* public String guardarAdministracionValorFaxClick() throws ApplicationException{
		String messageResult = "";
		try{
			
			messageResult = administracionFaxManager.guardarAdministracionValorFax(nmcaso, nmfax, cdtipoar, cdatribu, otvalor, nmpoliex, cdusuari, blarchivo);
			//pv_nmcaso_i, pv_nmfax_i, pv_cdtipoar_i, pv_cdatribu_i, pv_otvalor_i, pv_nmpoliex_i, pv_cdusuari_i, pv_blarchivo_i
			success = true;
			addActionMessage(messageResult);
			return SUCCESS;
		}catch(ApplicationException ae){
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch(Exception e){
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}*/
    
    
    @SuppressWarnings("unchecked")
	public String btnValidaNmcasoFaxClick() throws ApplicationException{
    	String messageResult = "";
    	try{
    		messageResult = this.administracionFaxManager.validaNmcasoFaxes(nmcaso);
			//pv_nmcaso_i, pv_nmfax_i
    		success = true;
			addActionMessage(messageResult);
			return SUCCESS;
		}catch(ApplicationException ae){
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch(Exception e){
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
    
  /************Metodo para guardar un fax con archivo************************/
    public String cmdGuardaArchivosFax() throws Exception{
		
		String nomParam = null; 
		BackBoneResultVO msg = null;
		try {
		
			FormatoOrdenFaxVO formatoOrdenFaxVO = new FormatoOrdenFaxVO();
			formatoOrdenFaxVO.setListaFaxesVO(listaFaxesVO);
			FaxesVO faxesVO = new FaxesVO();
			if(mFaxListVO.size() != 0){
				for(int i=0; i < mFaxListVO.size();i++){
					faxesVO = mFaxListVO.get(i);					
				}
				//faxesVO.setStrUsuariosSeg(strUsuariosSeg);
			}
			
		if (cmd != null && !cmd.equals("")) {
	    	MultiPartRequestWrapper multiPartRequestWrapper = ((MultiPartRequestWrapper)ServletActionContext.getRequest());
			Enumeration<String> enumParams = multiPartRequestWrapper.getFileParameterNames();
			
			
			while (enumParams.hasMoreElements()) {
				nomParam = enumParams.nextElement();
				logger.debug("Parametro: " + nomParam);
				File[] files = multiPartRequestWrapper.getFiles(nomParam);
				FileInputStream fileInputStream = new FileInputStream(files[0]);
				
				
				msg = faxDAOJdbc.guardarAdministracionFax(faxesVO, fileInputStream, (int)files[0].length()) ;
				
				
				if (listaFaxesVO != null) {
				for(int i=0; i<formatoOrdenFaxVO.getListaFaxesVO().size();i++){
				administracionFaxManager.guardarAdministracionValorFax(
						formatoOrdenFaxVO.getListaFaxesVO().get(i).getNmcaso(), 
						msg.getOutParam(),
						formatoOrdenFaxVO.getListaFaxesVO().get(i).getCdtipoar(), 
						formatoOrdenFaxVO.getListaFaxesVO().get(i).getCdAtribu(), 
						formatoOrdenFaxVO.getListaFaxesVO().get(i).getOtvalor()); 
						
				}//fin for
				}
			}
		}else {
			
						
			InputStream inputStream = new ByteArrayInputStream("".getBytes());
						
			msg = faxDAOJdbc.guardarAdministracionFax(faxesVO, inputStream, 0);
			addActionMessage(msg.getMsgText());
		}
		resultadoUpload = "{'success':true, actionMessages:['" + msg.getMsgText() + "']}";
		success = true;
		return SUCCESS;
		} catch (Exception e) {
			resultadoUpload = "{'success':false,'errors':{'Error':'" + e.getMessage() + "'}, actionErrors:['" + e.getMessage() + "']}";
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
    /***************************Guardar del Editar***********************************/
    
 public String cmdGuardaArchivosFaxEditar() throws Exception{
		
		String nomParam = null; 
		BackBoneResultVO msg = null;
		try {
		
			FormatoOrdenFaxVO formatoOrdenFaxVO = new FormatoOrdenFaxVO();
			formatoOrdenFaxVO.setListaFaxesVO(listaFaxesVO);
			FaxesVO faxesVO = new FaxesVO();
			if(mFaxListVO.size() != 0){
				for(int i=0; i < mFaxListVO.size();i++){
					faxesVO = mFaxListVO.get(i);					
				}
				//faxesVO.setStrUsuariosSeg(strUsuariosSeg);
			}
			
		if (cmd != null && !cmd.equals("")) {
	    	MultiPartRequestWrapper multiPartRequestWrapper = ((MultiPartRequestWrapper)ServletActionContext.getRequest());
			Enumeration<String> enumParams = multiPartRequestWrapper.getFileParameterNames();
			
			
			while (enumParams.hasMoreElements()) {
				nomParam = enumParams.nextElement();
				logger.debug("Parametro: " + nomParam);
				File[] files = multiPartRequestWrapper.getFiles(nomParam);
				FileInputStream fileInputStream = new FileInputStream(files[0]);
				
				
				msg = faxDAOJdbc.guardarAdministracionFaxEditar(faxesVO, fileInputStream, (int)files[0].length()) ;
				
				
				if (listaFaxesVO != null) {
				for(int i=0; i<formatoOrdenFaxVO.getListaFaxesVO().size();i++){
				administracionFaxManager.guardarAdministracionValorFax(
						formatoOrdenFaxVO.getListaFaxesVO().get(i).getNmcaso(), 
						formatoOrdenFaxVO.getListaFaxesVO().get(i).getNmfax(),
						formatoOrdenFaxVO.getListaFaxesVO().get(i).getCdtipoar(), 
						formatoOrdenFaxVO.getListaFaxesVO().get(i).getCdAtribu(), 
						formatoOrdenFaxVO.getListaFaxesVO().get(i).getOtvalor()); 
						
				}//fin for
				}
			}
		}else {
			
						
			InputStream inputStream = new ByteArrayInputStream("".getBytes());
						
			msg = faxDAOJdbc.guardarAdministracionFax(faxesVO, inputStream, 0);
			addActionMessage(msg.getMsgText());
		}
		resultadoUpload = "{'success':true, actionMessages:['" + msg.getMsgText() + "']}";
		success = true;
		return SUCCESS;
		} catch (Exception e) {
			resultadoUpload = "{'success':false,'errors':{'Error':'" + e.getMessage() + "'}, actionErrors:['" + e.getMessage() + "']}";
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
    
    
    /*******************************************************************************/
    
    public String cmdGuardarNuevoCasoFax()throws Exception{
		String messageResult = "";
    	try
		{	
			
			FormatoOrdenFaxVO formatoOrdenFaxVO = new FormatoOrdenFaxVO();
			formatoOrdenFaxVO.setListaFaxesVO(listaFaxesVO);
			FaxesVO faxesVO = new FaxesVO();
			if(mFaxListVO.size() != 0){
				for(int i=0; i < mFaxListVO.size();i++){
					faxesVO = mFaxListVO.get(i);					
				}
				//faxesVO.setStrUsuariosSeg(strUsuariosSeg);
			}
			//Antes estaba contextUserVO - mod 30-01-09
			//ThreadLocal<UserVO> tl = localUser;
            //UserVO userVO = tl.get();//(UserVO) session.get("contextUserVO");

			/*if(userVO != null && userVO.getUser()!= null){
                logger.debug("seteando en el caso el usuario logueado al sistema: " + userVO.getUser());
                casoVO.setCdUsuario(userVO.getUser());
			}*/


			WrapperResultados res = administracionFaxManager.guardarNuevoCasoFax(faxesVO, formatoOrdenFaxVO);
			messageResult = res.getMsgText();
            success = true;
            addActionMessage("Fax Creado");
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
    
    public String cmdIrADministracionFaxClick()throws Exception{
		return "irPantallaAdministracionFax";
	}
    
    public String cmdIrADministracionFaxEditarClick()throws Exception{
		return "irPantallaAdministracionFaxEditar";
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

	public String getCdperson() {
		return cdperson;
	}

	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}


    public void setSession(Map map) {
        session = map;
    }


	public String getNmfax() {
		return nmfax;
	}


	public void setNmfax(String nmfax) {
		this.nmfax = nmfax;
	}


	public String getCdtipoar() {
		return cdtipoar;
	}


	public void setCdtipoar(String cdtipoar) {
		this.cdtipoar = cdtipoar;
	}


	public String getFeingreso() {
		return feingreso;
	}


	public void setFeingreso(String feingreso) {
		this.feingreso = feingreso;
	}


	public String getFerecepcion() {
		return ferecepcion;
	}


	public void setFerecepcion(String ferecepcion) {
		this.ferecepcion = ferecepcion;
	}


	public String getNmpoliex() {
		return nmpoliex;
	}


	public void setNmpoliex(String nmpoliex) {
		this.nmpoliex = nmpoliex;
	}


	public String getCdusuari() {
		return cdusuari;
	}


	public void setCdusuari(String cdusuari) {
		this.cdusuari = cdusuari;
	}


	public String getBlarchivo() {
		return blarchivo;
	}


	public void setBlarchivo(String blarchivo) {
		this.blarchivo = blarchivo;
	}


	public String getCdatribu() {
		return cdatribu;
	}


	public void setCdatribu(String cdatribu) {
		this.cdatribu = cdatribu;
	}


	public String getOtvalor() {
		return otvalor;
	}


	public void setOtvalor(String otvalor) {
		this.otvalor = otvalor;
	}


	public void setAdministracionFaxManager(
			AdministracionFaxManager administracionFaxManager) {
		this.administracionFaxManager = administracionFaxManager;
	}


	public List<ListaFaxCasoVO> getListaFaxesVO() {
		return listaFaxesVO;
	}


	public void setListaFaxesVO(List<ListaFaxCasoVO> listaFaxesVO) {
		this.listaFaxesVO = listaFaxesVO;
	}


	public List<FaxesVO> getMFaxListVO() {
		return mFaxListVO;
	}


	public void setMFaxListVO(List<FaxesVO> faxListVO) {
		mFaxListVO = faxListVO;
	}


	public String getCmd() {
		return cmd;
	}


	public void setCmd(String cmd) {
		this.cmd = cmd;
	}


	public String getTipoArchivo() {
		return tipoArchivo;
	}


	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}


	public String getDsArchivo() {
		return dsArchivo;
	}


	public void setDsArchivo(String dsArchivo) {
		this.dsArchivo = dsArchivo;
	}


	public void setFaxDAOJdbc(FaxDAO faxDAOJdbc) {
		this.faxDAOJdbc = faxDAOJdbc;
	}


	public String getResultadoUpload() {
		return resultadoUpload;
	}


	public void setResultadoUpload(String resultadoUpload) {
		this.resultadoUpload = resultadoUpload;
	}


	public int getCdVariable() {
		return cdVariable;
	}


	public void setCdVariable(int cdVariable) {
		this.cdVariable = cdVariable;
	}


	public String getCdmatriz() {
		return cdmatriz;
	}


	public void setCdmatriz(String cdmatriz) {
		this.cdmatriz = cdmatriz;
	}


	public String getCdformatoorden() {
		return cdformatoorden;
	}


	public void setCdformatoorden(String cdformatoorden) {
		this.cdformatoorden = cdformatoorden;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}
    
    
    /********************AdministracionFaxes***********************/
    
    
    
}
