package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.service.MatrizAsignacionManager;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.FormatoDocumentoVO;
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.model.ResponsablesVO;

import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Matriz Asignacion.
 * 
 */
public class ConfiguraMatrizTareaAction extends ActionSupport {

	private static final long serialVersionUID = 19873215465454L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConfiguraMatrizTareaAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient MatrizAsignacionManager matrizAsignacionManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo MatrizAsignacionVO
	 * con los valores de la consulta.
	 */
	private List<MatrizAsignacionVO> mEstructuraList;
	
    private String pv_cdmatriz_i;
    private String pv_cdnivatn_i;
    private String pv_cdusuario_i;
    
    private String newCdMatriz;
    private String cdmatriz;
    private String cdelemento;
	private String cdformatoorden;
	private String cdproceso;
	private String cdramo;
	private String cdunieco;
	
	private String pv_cdusuari_i;
	private String cdusr_old;
	private String cdusr_new;
	
	private boolean success;

		
	
	/**
	 * Metodo que guarda una matriz de asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */
	public String cmdGuardarMatrizAsignacionClick()throws Exception
	{
		String messageResult = "";
        try
        {
        	MatrizAsignacionVO matrizAsignacionVO = new MatrizAsignacionVO();
        	
        	
        	matrizAsignacionVO.setcdmatriz(cdmatriz);
        	matrizAsignacionVO.setCdelemento(cdelemento);
        	matrizAsignacionVO.setCdformatoorden(cdformatoorden);
        	matrizAsignacionVO.setCdproceso(cdproceso);
        	matrizAsignacionVO.setCdramo(cdramo);
        	matrizAsignacionVO.setCdunieco(cdunieco);
        	
        	BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
        	backBoneResultVO = this.matrizAsignacionManager.guardarMatriz(matrizAsignacionVO);
        	newCdMatriz = backBoneResultVO.getOutParam();
        	messageResult = backBoneResultVO.getMsgText();
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
	 * Metodo que elimina responsables para una matriz de asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarResponsablesMatrizAsignacionClick() throws Exception{
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
	 * Metodo que elimina tiempos para una matriz de asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarTiemposMatrizAsignacionClick() throws Exception{
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
	 * Metodo que atiende una peticion para dirigirse a la pagina Matriz de Asignacion
	 * 
	 * @return String
	 *  
     */
	public String irMatrizAsignacionClick(){
		return "matrizAsignacion";
	}
	
	/**
	 * Metodo que valida si existen suplentes para usuarios responsables.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmbStatusSelect() throws Exception{
		String rdoValidacion = "";
		try{
			rdoValidacion = this.matrizAsignacionManager.validaResponsable(pv_cdmatriz_i, pv_cdnivatn_i, pv_cdusuari_i);
            success = true;
            addActionMessage(rdoValidacion);
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
	
/*	public String cmdGuardarReemplazoClick() throws Exception{
		String message = "";
		try{			
			message = this.matrizAsignacionManager.guardarSuplente(cdmatriz, cdusr_old, cdusr_new, pv_cdusuario_i, pv_cdnivatn_i);
            success = true;
            addActionMessage(message);
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

	}*/
	
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


	public String getCdmatriz() {
		return cdmatriz;
	}


	public void setCdmatriz(String cdmatriz) {
		this.cdmatriz = cdmatriz;
	}


	public String getCdelemento() {
		return cdelemento;
	}


	public void setCdelemento(String cdelemento) {
		this.cdelemento = cdelemento;
	}


	public String getCdformatoorden() {
		return cdformatoorden;
	}


	public void setCdformatoorden(String cdformatoorden) {
		this.cdformatoorden = cdformatoorden;
	}


	public String getCdproceso() {
		return cdproceso;
	}


	public void setCdproceso(String cdproceso) {
		this.cdproceso = cdproceso;
	}


	public String getCdramo() {
		return cdramo;
	}


	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}


	public String getCdunieco() {
		return cdunieco;
	}


	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}


	public String getNewCdMatriz() {
		return newCdMatriz;
	}


	public void setNewCdMatriz(String newCdMatriz) {
		this.newCdMatriz = newCdMatriz;
	}


	public String getPv_cdusuari_i() {
		return pv_cdusuari_i;
	}


	public void setPv_cdusuari_i(String pv_cdusuari_i) {
		this.pv_cdusuari_i = pv_cdusuari_i;
	}


	public String getCdusr_old() {
		return cdusr_old;
	}


	public void setCdusr_old(String cdusr_old) {
		this.cdusr_old = cdusr_old;
	}


	public String getCdusr_new() {
		return cdusr_new;
	}


	public void setCdusr_new(String cdusr_new) {
		this.cdusr_new = cdusr_new;
	}

}