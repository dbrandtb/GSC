package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.TareaVO;
import mx.com.aon.catbo.service.TareasCatBoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;



import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Ayuda de Coberturas.
 * 
 */
@SuppressWarnings("serial")
public class TareasCatBoAction extends ActionSupport {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(TareasCatBoAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient TareasCatBoManager tareasCatBoManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	private List<TareaVO> mEstructuraList;
	private String cdProceso;
	private String estatus;
	private String cdPriord;
	private String indSemaforo;
	private String cdModulo;
	private String frmAb;
	private String valida="";

	private boolean success;

		
	/**
	 * Metodo que Elimina un Estatus de Caso
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult =  tareasCatBoManager.borrarTareasCatBo(cdProceso);
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
	 * Metodo que obtiene un Estatus de Caso
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetClick()throws Exception
	{
		try
		{
			mEstructuraList=new ArrayList<TareaVO>();
			TareaVO tareaVO=tareasCatBoManager.getTareasCatBo(cdProceso);
			mEstructuraList.add(tareaVO);
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

	/**
	 * Metodo que actualiza o genera un Estatus de Caso
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */
	public String cmdGuardarClick()throws Exception
	{
		String messageResult = "";
        try
        {
        	TareaVO tareaVO= new TareaVO();
        	tareaVO.setCdProceso(cdProceso);
        	tareaVO.setEstatus(estatus);
        	tareaVO.setCdModulo(cdModulo);
        	tareaVO.setCdPriord(cdPriord);
        	tareaVO.setIndSemaforo(indSemaforo);
        	tareaVO.setFrmAb(frmAb);
        	messageResult = tareasCatBoManager.guardarTareasCatBo(tareaVO);
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
	
	public String cmdBuscarClickValidar() throws Exception{
		try{
			WrapperResultados res = null;
			res =  this.tareasCatBoManager.buscarTareasCatBoValidar(cdProceso);
			valida=res.getResultado();
           // mEstructuraListTareas = pagedList.getItemsRangeList();
           // totalCount = pagedList.getTotalItems();                                                    
            success = true;
            addActionMessage(res.getMsgText());
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

	
	
	
	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<TareaVO> getMEstructuraList() {
		return mEstructuraList;
	}

	public void setMEstructuraList(List<TareaVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public String getCdProceso() {
		return cdProceso;
	}

	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getCdPriord() {
		return cdPriord;
	}

	public void setCdPriord(String cdPriord) {
		this.cdPriord = cdPriord;
	}

	public String getIndSemaforo() {
		return indSemaforo;
	}

	public void setIndSemaforo(String indSemaforo) {
		this.indSemaforo = indSemaforo;
	}

	public String getCdModulo() {
		return cdModulo;
	}

	public void setCdModulo(String cdModulo) {
		this.cdModulo = cdModulo;
	}

	public void setTareasCatBoManager(TareasCatBoManager tareasCatBoManager) {
		this.tareasCatBoManager = tareasCatBoManager;
	}

	public String getFrmAb() {
		return frmAb;
	}

	public void setFrmAb(String frmAb) {
		this.frmAb = frmAb;
	}

	public String getValida() {
		return valida;
	}

	public void setValida(String valida) {
		this.valida = valida;

	}
}
