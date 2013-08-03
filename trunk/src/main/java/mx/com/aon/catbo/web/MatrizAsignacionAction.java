package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.service.MatrizAsignacionManager;
import mx.com.aon.catbo.service.ProcesoManager;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.model.OperacionCATVO;
import mx.com.aon.catbo.model.ResponsablesVO;
import mx.com.aon.catbo.model.TiemposVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.ConnectionCallInterceptor;

import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Matriz Asignacion.
 * 
 */
public class MatrizAsignacionAction extends ActionSupport {

	private static final long serialVersionUID = 19873215465454L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(MatrizAsignacionAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient MatrizAsignacionManager matrizAsignacionManager;
	private transient ProcesoManager procesoManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo MatrizAsignacionVO
	 * con los valores de la consulta.
	 */
	private List<MatrizAsignacionVO> mEstructuraList;
	
    private String pv_cdmatriz_i;
    private String cdmatriz;
    private String cdunieco;
    private String cdramo;
    private String cdelemento;
    private String cdproceso;
    private String cdformatoorden;
    private String pv_cdnivatn_i;
    private String pv_cdusuario_i;
    private String operacion;
    
    private String cdnivatn;
    private String tresolucion;
    private String tresunidad;
    private String talarma;
    private String talaunidad;
    private String tescalamiento;
    private String tescaunidad;
	
    private String cdrolmat;
    private String cdusr;
    private String email;
    private String status;
    
	private String cdusuari;
	private String dsusuari;
    
    private boolean success;

    private String newCdMatriz;
    
    private String cdUsuario;
	private String cdUsuarioOld;
	private String cdUsuarioNew;
	
	private String numCaso;
    
	

		
	public String getNumCaso() {
		return numCaso;
	}




	public void setNumCaso(String numCaso) {
		this.numCaso = numCaso;
	}




	public String getNewCdMatriz() {
		return newCdMatriz;
	}




	public void setNewCdMatriz(String newCdMatriz) {
		this.newCdMatriz = newCdMatriz;
	}




	/**
	 * Metodo que inserta guarda un registro de guiones.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param cdElemento
	 * @param cdProceso
	 * @param cdGuion
     * @param dsGuion
	 * @param cdTipGuion
	 * @param indInicial
	 * @param status
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarMatrizClick() throws Exception{
		String messageResult = "";
		try{
			MatrizAsignacionVO matrizAsignacionVO = new MatrizAsignacionVO();
			
			matrizAsignacionVO.setcdmatriz(cdmatriz);
			matrizAsignacionVO.setCdunieco(cdunieco);
			matrizAsignacionVO.setCdramo(cdramo);
			matrizAsignacionVO.setCdelemento(cdelemento);
			matrizAsignacionVO.setCdproceso(cdproceso);
			matrizAsignacionVO.setCdformatoorden(cdformatoorden);
			
			BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
        	backBoneResultVO = this.matrizAsignacionManager.guardarMatriz(matrizAsignacionVO);
        	newCdMatriz = backBoneResultVO.getOutParam();
			success = true;
            addActionMessage(messageResult);
            return SUCCESS;
			
			/*messageResult = this.matrizAsignacionManager.guardarMatriz(matrizAsignacionVO);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;*/
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
	 * Metodo que elimina un registro de matriz de asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarMatrizClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.matrizAsignacionManager.borrarMatriz(pv_cdmatriz_i);
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
	 * Metodo que elimina un registro de matriz de asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarResponsableMatrizClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.matrizAsignacionManager.borrarResponsablesMatriz(pv_cdmatriz_i, pv_cdnivatn_i, pv_cdusuario_i);
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
	 * Metodo que elimina un registro de matriz de asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarTiempoMatrizClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.matrizAsignacionManager.borrarTiemposMatriz(pv_cdmatriz_i, pv_cdnivatn_i);
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
	 * Metodo que inserta guarda un registro de guiones.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param cdElemento
	 * @param cdProceso
	 * @param cdGuion
     * @param dsGuion
	 * @param cdTipGuion
	 * @param indInicial
	 * @param status
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarResponsableMatrizClick() throws Exception{
		String messageResult = "";
		try{
			ResponsablesVO responsablesVO = new ResponsablesVO();
			
			responsablesVO.setCdmatriz(cdmatriz);
			responsablesVO.setCdnivatn(cdnivatn);
			responsablesVO.setCdrolmat(cdrolmat);
			
			UserVO userVO = new UserVO();
			ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
            userVO = tl.get();
			
			responsablesVO.setCdusuari(cdusuari);
            //responsablesVO.setCdusuari(userVO.getUser());
			responsablesVO.setEmail(email);
			responsablesVO.setStatus(status);
			responsablesVO.setOperacion(operacion);
			
			
			messageResult = this.matrizAsignacionManager.guardarResponsables(responsablesVO);
			
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
	 * Metodo que inserta guarda un registro de guiones.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param cdElemento
	 * @param cdProceso
	 * @param cdGuion
     * @param dsGuion
	 * @param cdTipGuion
	 * @param indInicial
	 * @param status
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarTiemposMatrizClick() throws Exception{
		String messageResult = "";
		try{
			TiemposVO tiemposVO = new TiemposVO();
			
			tiemposVO.setCdmatriz(cdmatriz);
			tiemposVO.setCdnivatn(cdnivatn);
			tiemposVO.setTresolucion(tresolucion);
			tiemposVO.setTresunidad(tresunidad);
			tiemposVO.setTalarma(talarma);
			tiemposVO.setTalaunidad(talaunidad);
			tiemposVO.setTescalamiento(tescalamiento);
			tiemposVO.setTescaunidad(tescaunidad);
			tiemposVO.setOperacion(operacion);
			
			messageResult = this.matrizAsignacionManager.guardarTiempos(tiemposVO);
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
	 * Metodo que inserta guarda un registro de guiones.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param cdElemento
	 * @param cdProceso
	 * @param cdGuion
     * @param dsGuion
	 * @param cdTipGuion
	 * @param indInicial
	 * @param status
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarSuplenteClick() throws Exception{
		String messageResult = "";
		try{
			CasoVO casoVO = new CasoVO();
			
			casoVO.setCdMatriz(cdmatriz);
			casoVO.setCdUsuarioOld(cdUsuarioOld);
			casoVO.setCdUsuarioNew(cdUsuarioNew);
			casoVO.setCdUsuario(cdUsuario);
			casoVO.setCdNivatn(cdnivatn);
			
			logger.debug("Obteniendo el usuario de la sesion ....");
			UserVO userVO = new UserVO();
			ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
            userVO = tl.get();

			if(userVO != null && userVO.getUser()!= null){                
                casoVO.setCdUsuario(userVO.getUser());
                logger.debug("Usuario logueado al sistema: " + casoVO.getCdUsuario());
			}
			
			//messageResult = this.matrizAsignacionManager.guardarSuplente(casoVO);
			messageResult=procesoManager.guardaSuplente(cdmatriz, cdnivatn,cdUsuarioOld, cdUsuarioNew, casoVO.getCdUsuario());
			logger.debug("respuesta a la invocacion al guardaSuplente "+ messageResult);
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
	 * Metodo que atiende una peticion para dirigirse a la pagina configuracion de Matriz Tarea
	 * 
	 * @return String
	 *  
     */
	public String irConfiguraMatrizTareaClick(){
		return "configuraMatrizTarea";
	}
	
	 /**
	 * Metodo que atiende una peticion para dirigirse a la pagina configuracion de Matriz Tarea
	 * 
	 * @return String
	 *  
     */
	public String irConfiguraMatrizTareaEditarClick(){
		return "configurarMatrizTareaEditar";
	}
	
	
	/**
	 * Metodo que atiende una peticion para dirigirse a la pagina configuracion de Matriz Tarea
	 * 
	 * @return String
	 *  
     */
	public String irMatrizAsignacionReasignarCasoClick(){
		return "matrizAsignacionReasignarCaso";
	}
	
	public String irReasignarCasoUsrClick(){
		return "irReasignarCasoUsr";
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public MatrizAsignacionManager getMatrizAsignacionManager() {
		return matrizAsignacionManager;
	}

	public void setMatrizAsignacionManager(
			MatrizAsignacionManager matrizAsignacionManager) {
		this.matrizAsignacionManager = matrizAsignacionManager;
	}

	public List<MatrizAsignacionVO> getMEstructuraList() {
		return mEstructuraList;
	}

	public void setMEstructuraList(List<MatrizAsignacionVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public String getPv_cdmatriz_i() {
		return pv_cdmatriz_i;
	}

	public void setPv_cdmatriz_i(String pv_cdmatriz_i) {
		this.pv_cdmatriz_i = pv_cdmatriz_i;
	}




	public String getCdmatriz() {
		return cdmatriz;
	}




	public void setCdmatriz(String cdmatriz) {
		this.cdmatriz = cdmatriz;
	}




	public String getCdunieco() {
		return cdunieco;
	}




	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}




	public String getCdramo() {
		return cdramo;
	}




	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}




	public String getCdelemento() {
		return cdelemento;
	}




	public void setCdelemento(String cdelemento) {
		this.cdelemento = cdelemento;
	}




	public String getCdproceso() {
		return cdproceso;
	}




	public void setCdproceso(String cdproceso) {
		this.cdproceso = cdproceso;
	}




	public String getCdformatoorden() {
		return cdformatoorden;
	}




	public void setCdformatoorden(String cdformatoorden) {
		this.cdformatoorden = cdformatoorden;
	}




	public String getPv_cdnivatn_i() {
		return pv_cdnivatn_i;
	}




	public void setPv_cdnivatn_i(String pv_cdnivatn_i) {
		this.pv_cdnivatn_i = pv_cdnivatn_i;
	}




	public String getPv_cdusuario_i() {
		return pv_cdusuario_i;
	}




	public void setPv_cdusuario_i(String pv_cdusuario_i) {
		this.pv_cdusuario_i = pv_cdusuario_i;
	}




	public String getCdnivatn() {
		return cdnivatn;
	}




	public void setCdnivatn(String cdnivatn) {
		this.cdnivatn = cdnivatn;
	}




	public String getTresolucion() {
		return tresolucion;
	}




	public void setTresolucion(String tresolucion) {
		this.tresolucion = tresolucion;
	}




	public String getTresunidad() {
		return tresunidad;
	}




	public void setTresunidad(String tresunidad) {
		this.tresunidad = tresunidad;
	}




	public String getTalarma() {
		return talarma;
	}




	public void setTalarma(String talarma) {
		this.talarma = talarma;
	}




	public String getTalaunidad() {
		return talaunidad;
	}




	public void setTalaunidad(String talaunidad) {
		this.talaunidad = talaunidad;
	}




	public String getTescalamiento() {
		return tescalamiento;
	}




	public void setTescalamiento(String tescalamiento) {
		this.tescalamiento = tescalamiento;
	}




	public String getTescaunidad() {
		return tescaunidad;
	}




	public void setTescaunidad(String tescaunidad) {
		this.tescaunidad = tescaunidad;
	}




	public String getCdrolmat() {
		return cdrolmat;
	}




	public void setCdrolmat(String cdrolmat) {
		this.cdrolmat = cdrolmat;
	}




	public String getCdusr() {
		return cdusr;
	}




	public void setCdusr(String cdusr) {
		this.cdusr = cdusr;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




	public String getCdusuari() {
		return cdusuari;
	}




	public void setCdusuari(String cdusuari) {
		this.cdusuari = cdusuari;
	}




	public String getDsusuari() {
		return dsusuari;
	}




	public void setDsusuari(String dsusuari) {
		this.dsusuari = dsusuari;
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




	public String getCdUsuario() {
		return cdUsuario;
	}




	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}




	public void setProcesoManager(ProcesoManager procesoManager) {
		this.procesoManager = procesoManager;
	}




	public String getOperacion() {
		return operacion;
	}




	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	



	


	

}