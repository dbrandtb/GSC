package mx.com.aon.catbo.web;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class ConsultaArchivosAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 131245454489L;
	
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConsultaArchivosAction.class);
	
	private transient AdministracionCasosManager administracionCasosManager;
	private List<CasoVO> mEstructuraCasoList;
	
	private String pv_nmcaso_i;
	private String pv_nmovimiento_i;
	private String pv_cdpriord_i;
	private String pv_cdstatus_i;
	private String pv_dsobservacion_i; 
	private String pv_cdusuario_i;
	private String pv_cdnivatn;
	private String pv_cdrolmat_i;
	
	
	private boolean success;
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	//private transient Manager manager;
	
	
	public String getEncArchivoPorMovimientoDeCaso()throws Exception{
		try
		{
			/*mEncEncArchivoCasoVO = new ArrayList<VO>();
			VO vo = manager.get(params);
			mEncEncArchivoCasoVO.add(vo);*/
			success = true;
            return SUCCESS;
        /*}catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;*/

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

	
	/**
	 * Metodo que elimina un registro de tabla Notificaciones.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	/*
	public String cmdBorrarArchivoClick() throws ApplicationException{
		String messageResult = "";
		try{
			messageResult = this.administracionCasosManager.borr
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
	*/
	
	/*
	public String cmdGuardarArchivoAnexadoClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.administracionCasosManager.guar
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
	*/
	
	
	public String cmdGuardarMovimientoPorArchivoAnexadoClick() throws Exception{
		String messageResult = "";
		try{
			//messageResult = manager.guardarMovimientoArchivoAnexado();
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }/*catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }*/catch( Exception e){
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
	

}
