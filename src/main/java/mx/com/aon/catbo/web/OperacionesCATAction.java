package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.service.OperacionCATManager;
import mx.com.aon.catbo.model.NotificacionVO;
import mx.com.aon.catbo.model.OperacionCATVO;
import mx.com.aon.catbo.model.ScriptObservacionVO;

import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Ayuda de Coberturas.
 * 
 */
public class OperacionesCATAction extends ActionSupport {

	private static final long serialVersionUID = 19873215465454L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(OperacionesCATAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient OperacionCATManager operacionCATManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo 
	 * con los valores de la consulta.
	 */
	private List<NotificacionVO> mOperacionCatList;
	
	private List<OperacionCATVO> csoGrillaListDialogo;
	
	private String cdUniEco;
    private String dsUniEco;
    private String cdRamo;
    private String dsRamo;
    private String cdElemento;
    private String dsElemento;
    private String cdProceso;
    private String dsProceso;
    private String cdGuion;
    private String dsGuion;
	private String cdTipGuion;
	private String dsTipGuion;
	private String indInicial;
	private String status;
    
	private String cdDialogo;
    private String dsDialogo;
    private String cdSecuencia;
    private String otTapVal;
    
    private String cdTipGui;
    
    private String cdUsuario;
    private String cdPerson;
    private String dsObservacion;

	private boolean success;

		
	/**
	 * Metodo que elimina un registro de guiones.
	 * 
	 * @param cdUniEco
	 * @param cdElemento
	 * @param cdGuion
	 * @param cdProceso
	 * @param cdRamo
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarGuionClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = operacionCATManager.borrarGuion(cdUniEco, cdElemento, cdGuion, cdProceso, cdRamo);
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
	 * Metodo que elimina un registro de dialogo.
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param cdElemento
	 * @param cdProceso
	 * @param cdGuion
	 * @param cdDialogo
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarDialogoClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = operacionCATManager.borrarDialogo(cdGuion, cdDialogo);
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
	public String cmdGuardarGuionClick() throws Exception{
		String messageResult = "";
		try{
			OperacionCATVO operacionCATVO = new OperacionCATVO();
			
			operacionCATVO.setCdUniEco(cdUniEco);
			operacionCATVO.setCdRamo(cdRamo);
			operacionCATVO.setCdElemento(cdElemento);
			operacionCATVO.setCdProceso(cdProceso);
			operacionCATVO.setCdGuion(cdGuion);
			operacionCATVO.setDsGuion(dsGuion);
			operacionCATVO.setCdTipGuion(cdTipGuion);
			operacionCATVO.setIndInicial(indInicial);
			operacionCATVO.setStatus(status);
			
			
			messageResult = operacionCATManager.guardarGuion(operacionCATVO);
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
	 * Metodo que inserta guarda un registro de dialogo para un guion.
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
	public String cmdGuardarDialogoGuionClick() throws ApplicationException{
		String messageResult = "";
		try{
			
			OperacionCATVO operacionCATVO = new OperacionCATVO();
			
			for (int i=0; i<csoGrillaListDialogo.size(); i++) {
				
				OperacionCATVO operacionCATVO_grid = csoGrillaListDialogo.get(i);
				
				operacionCATVO.setCdDialogo(operacionCATVO_grid.getCdDialogo());
				/*operacionCATVO.setCdUniEco(operacionCATVO_grid.getCdUniEco());
				operacionCATVO.setCdRamo(operacionCATVO_grid.getCdRamo());
				operacionCATVO.setCdElemento(operacionCATVO_grid.getCdElemento());
				operacionCATVO.setCdProceso(operacionCATVO_grid.getCdProceso());*/
				operacionCATVO.setCdGuion(operacionCATVO_grid.getCdGuion());
				operacionCATVO.setDsDialogo(operacionCATVO_grid.getDsDialogo());
				operacionCATVO.setCdSecuencia(operacionCATVO_grid.getCdSecuencia());
				operacionCATVO.setOtTapVal(operacionCATVO_grid.getOtTapVal());
				
				messageResult = operacionCATManager.guardarDialogoGuion(operacionCATVO);
			}
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
	
	
	/**
	 * Metodo que inserta guarda un registro de dialogo para un guion.
	 * 
	 * @param cdUsuario
	 * @param cdPerson
	 * @param dsObservacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarDialogoScriptObservacion() throws ApplicationException{
		String messageResult = "";
		try{
			ScriptObservacionVO scriptObservacionVO = new ScriptObservacionVO();
			
			scriptObservacionVO.setCdUsuario(cdUsuario);
			scriptObservacionVO.setCdPerson(cdPerson);
			scriptObservacionVO.setDsObservacion(dsObservacion);
			
			messageResult = operacionCATManager.guardarDialogoScriptObservacion(scriptObservacionVO);
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
	
	
    public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public OperacionCATManager getOperacionCATManager() {
		return operacionCATManager;
	}

	public void setOperacionCATManager(OperacionCATManager operacionCATManager) {
		this.operacionCATManager = operacionCATManager;
	}

	public List<NotificacionVO> getMOperacionCatList() {
		return mOperacionCatList;
	}

	public void setMOperacionCatList(List<NotificacionVO> operacionCatList) {
		mOperacionCatList = operacionCatList;
	}

	public String getCdUniEco() {
		return cdUniEco;
	}

	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}

	public String getDsUniEco() {
		return dsUniEco;
	}

	public void setDsUniEco(String dsUniEco) {
		this.dsUniEco = dsUniEco;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getDsRamo() {
		return dsRamo;
	}

	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getDsElemento() {
		return dsElemento;
	}

	public void setDsElemento(String dsElemento) {
		this.dsElemento = dsElemento;
	}

	public String getCdProceso() {
		return cdProceso;
	}

	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}

	public String getDsProceso() {
		return dsProceso;
	}

	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}

	public String getCdGuion() {
		return cdGuion;
	}

	public void setCdGuion(String cdGuion) {
		this.cdGuion = cdGuion;
	}

	public String getDsGuion() {
		return dsGuion;
	}

	public void setDsGuion(String dsGuion) {
		this.dsGuion = dsGuion;
	}

	public String getCdTipGuion() {
		return cdTipGuion;
	}

	public void setCdTipGuion(String cdTipGuion) {
		this.cdTipGuion = cdTipGuion;
	}

	public String getDsTipGuion() {
		return dsTipGuion;
	}

	public void setDsTipGuion(String dsTipGuion) {
		this.dsTipGuion = dsTipGuion;
	}

	public String getIndInicial() {
		return indInicial;
	}

	public void setIndInicial(String indInicial) {
		this.indInicial = indInicial;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public String getCdDialogo() {
		return cdDialogo;
	}



	public void setCdDialogo(String cdDialogo) {
		this.cdDialogo = cdDialogo;
	}



	public String getDsDialogo() {
		return dsDialogo;
	}



	public void setDsDialogo(String dsDialogo) {
		this.dsDialogo = dsDialogo;
	}



	public String getCdSecuencia() {
		return cdSecuencia;
	}



	public void setCdSecuencia(String cdSecuencia) {
		this.cdSecuencia = cdSecuencia;
	}



	public String getOtTapVal() {
		return otTapVal;
	}



	public void setOtTapVal(String otTapVal) {
		this.otTapVal = otTapVal;
	}



	public List<OperacionCATVO> getCsoGrillaListDialogo() {
		return csoGrillaListDialogo;
	}



	public void setCsoGrillaListDialogo(List<OperacionCATVO> csoGrillaListDialogo) {
		this.csoGrillaListDialogo = csoGrillaListDialogo;
	}


	public String getCdTipGui() {
		return cdTipGui;
	}


	public void setCdTipGui(String cdTipGui) {
		this.cdTipGui = cdTipGui;
	}


	public String getCdUsuario() {
		return cdUsuario;
	}


	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}


	public String getCdPerson() {
		return cdPerson;
	}


	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}


	public String getDsObservacion() {
		return dsObservacion;
	}


	public void setDsObservacion(String dsObservacion) {
		this.dsObservacion = dsObservacion;
	}



}