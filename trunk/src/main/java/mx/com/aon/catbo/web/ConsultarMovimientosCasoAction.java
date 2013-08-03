package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ListaEmisionCasoVO;
import mx.com.aon.catbo.model.UsuarioVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.service.ProcesoManager;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;

public class ConsultarMovimientosCasoAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 13123134646489L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConsultarMovimientosCasoAction.class);
	
	private List<CasoVO> mListEncMovimientosCaso;
	private List<CasoVO> mListMovimientoCaso;
	private List<CasoVO> mListDatosMovimientoCaso;
	private List<CasoVO> listMCasoMovVO;
	private transient AdministracionCasosManager administracionCasosManager;
	private transient ProcesoManager procesoManager;
	
	private boolean success;
	private String nmcaso;
	private String nmovimiento;
	private String cdpriord;
	private String cdstatus;
	private String dsStatus;
	private String dsobservacion;
	private String cdusuario;
	private String cdperson;
	private String cdmatriz;
	private String cdformatoorden;
	private String respuesta;
		
    private Map session;



	public String obtenerEncabezadoMovimientosCaso()throws Exception{
		try
		{
			mListEncMovimientosCaso = new ArrayList<CasoVO>();
			CasoVO vo = administracionCasosManager.obtenerEncabezadoMovimientos(nmcaso);
			
			mListEncMovimientosCaso.add(vo);
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
	
	public String cmdGuardarClick() throws Exception{
		String messageResult = "";
		String taskId;
		try{
            String cdUsuario= "";
           /*/ UsuarioVO usuarioVO = (UsuarioVO)session.get("USUARIO");            
            if (usuarioVO != null && usuarioVO.getIdUsuario()!=null) {
                logger.debug("seteando en el caso el usuario logueado al sistema: " + usuarioVO.getIdUsuario());
                cdUsuario = usuarioVO.getIdUsuario();
            }*/
            UserVO usuario = (UserVO) session.get("USUARIO");

			if(usuario != null && usuario.getUser()!= null){
                logger.debug("seteando en el caso el usuario logueado al sistema: " + usuario.getUser());
                cdUsuario= usuario.getUser();
			}

            if(dsStatus.equals("CANCELADO") ||  dsStatus.equals("TERMINADO")){
                //invocando con usuario en dru
                //todo cambiar despues
            	logger.debug("callingCancelarProceso con dsobservacion: "+listMCasoMovVO.get(0).getObservacion());
                messageResult = procesoManager.cancelarProceso(listMCasoMovVO,cdUsuario,dsStatus);
                logger.debug("respuesta a la invocacion al cancelarProceso "+ messageResult);
                //procesoManager.cancelarProceso(listMCasoMovVO.get(0).getNmCaso(),listMCasoMovVO.get(0).getCdUsuario(),dsStatus);
			}else{
				WrapperResultados res =  administracionCasosManager.guardarMovimineto(listMCasoMovVO.get(0).getNmCaso(),
						listMCasoMovVO.get(0).getCdPrioridad(),listMCasoMovVO.get(0).getCdStatus(),
						listMCasoMovVO.get(0).getObservacion(),cdUsuario);
				messageResult = res.getMsgText();
				nmovimiento = res.getResultado();
				
				if(logger.isDebugEnabled()){
					logger.debug("El numero del movimiento generado es: "+nmovimiento);
					logger.debug("msg messageResult: "+messageResult);
				}
			}
          
            logger.debug("saliendo del action");
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
	
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult =  administracionCasosManager.borrarMovimientos(nmcaso, nmovimiento);
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
	
	public String obtenerMovimientoCaso()throws Exception{
		try
		{
			mListMovimientoCaso = new ArrayList<CasoVO>();
			CasoVO vo = administracionCasosManager.getObtenerMovimientoCaso(nmcaso, nmovimiento);
			
			mListMovimientoCaso.add(vo);
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
	
	public String obtenerDatosDelMovimiento()throws Exception{		
		try
		{
			mListDatosMovimientoCaso = new ArrayList<CasoVO>();
			CasoVO vo = administracionCasosManager.getObtenerMovimientoCaso(nmcaso, nmovimiento);
			
			mListDatosMovimientoCaso.add(vo);
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
	public String cmdIrPaginaConsultaCasoDetalleClick()throws Exception{
		return "consultarCasoDetalle";
	}
	
	public String cmdIrPantallaConsultaMovimientoClick(){return "consultarMovimientosCaso";}
	
	public List<CasoVO> getMListEncMovimientosCaso() {
		return mListEncMovimientosCaso;
	}

	public void setMListEncMovimientosCaso(List<CasoVO> listEncMovimientosCaso) {
		mListEncMovimientosCaso = listEncMovimientosCaso;
	}

	public List<CasoVO> getMListMovimientoCaso() {
		return mListMovimientoCaso;
	}

	public void setMListMovimientoCaso(List<CasoVO> listMovimientoCaso) {
		mListMovimientoCaso = listMovimientoCaso;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getNmcaso() {
		return nmcaso;
	}

	public void setNmcaso(String nmcaso) {
		this.nmcaso = nmcaso;
	}

	public String getCdpriord() {
		return cdpriord;
	}

	public void setCdpriord(String cdpriord) {
		this.cdpriord = cdpriord;
	}

	public String getCdstatus() {
		return cdstatus;
	}

	public void setCdstatus(String cdstatus) {
		this.cdstatus = cdstatus;
	}

	public String getDsobservacion() {
		return dsobservacion;
	}

	public void setDsobservacion(String dsobservacion) {
		this.dsobservacion = dsobservacion;
	}

	public String getCdusuario() {
		return cdusuario;
	}

	public void setCdusuario(String cdusuario) {
		this.cdusuario = cdusuario;
	}

	public void setAdministracionCasosManager(
			AdministracionCasosManager administracionCasosManager) {
		this.administracionCasosManager = administracionCasosManager;
	}
	
	public String getNmovimiento() {
		return nmovimiento;
	}

	public void setNmovimiento(String nmovimiento) {
		this.nmovimiento = nmovimiento;
	}

	public List<CasoVO> getMListDatosMovimientoCaso() {
		return mListDatosMovimientoCaso;
	}

	public void setMListDatosMovimientoCaso(List<CasoVO> listDatosMovimientoCaso) {
		mListDatosMovimientoCaso = listDatosMovimientoCaso;
	}
	public String getCdperson() {
		return cdperson;
	}

	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}

	public String getCdmatriz() {
		return cdmatriz;
	}

	public void setCdmatriz(String cdmatriz) {
		this.cdmatriz = cdmatriz;
	}

	public List<CasoVO> getListMCasoMovVO() {
		return listMCasoMovVO;
	}

	public void setListMCasoMovVO(List<CasoVO> listMCasoMovVO) {
		this.listMCasoMovVO = listMCasoMovVO;
	}

	public String getDsStatus() {
		return dsStatus;
	}

	public void setDsStatus(String dsStatus) {
		this.dsStatus = dsStatus;
	}

	public void setProcesoManager(ProcesoManager procesoManager) {
		this.procesoManager = procesoManager;
	}


    public void setSession(Map map) {
        session = map;
    }

	public String getCdformatoorden() {
		return cdformatoorden;
	}

	public void setCdformatoorden(String cdformatoorden) {
		this.cdformatoorden = cdformatoorden;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
}
