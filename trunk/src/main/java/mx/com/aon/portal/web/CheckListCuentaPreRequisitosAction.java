package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.CheckListCuentaPreRequisitosVO;
import mx.com.aon.portal.model.CheckListXCuentaSeccion;
import mx.com.aon.portal.model.ClienteVO;
import mx.com.aon.portal.model.EncabezadosCheckListCuentaVO;
import mx.com.aon.portal.model.ErrorVO;
import mx.com.aon.portal.model.TareaChecklistVO;
import mx.com.aon.portal.service.CheckListCuentaPreRequisitosManager;
import mx.com.aon.portal.service.PagedList;

import org.apache.log4j.Logger;

/**
 *   Action que atiende las peticiones que vienen de la pantalla Configurar checklist de la cuenta.
 * 
 */
public class CheckListCuentaPreRequisitosAction extends AbstractListAction{

	private static final long serialVersionUID = 8106543262330598802L;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(CheckListCuentaPreRequisitosAction.class);
 
 /**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 */
	private transient CheckListCuentaPreRequisitosManager checkListCuentaPreRequisitosManager;

	private List<ClienteVO> mClientesVO;
	private List<TareaChecklistVO> mTareaChecklistVO; 
	private List<EncabezadosCheckListCuentaVO> mEncabezadosCheckListVO;
	private List<CheckListXCuentaSeccion> mCheckListXCuentaSeccion;
	private List<ErrorVO> errors;

	private List<CheckListCuentaPreRequisitosVO> listaParams;

	private String codigoCliente;

	private String codigoConfiguracion;

	private String codigoSeccion;

    private String cdPerson;

	private String descripcionConf;

	private String lineaOperacion;

	private String codigoTarea;

	private String fgComple;

	private String fgNoRequ;
	
	private String fgPendiente;
	
	private String flag;
	
	

	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	/**
	 * Metodo que atiende una peticion para obtener el encabezado de la cuenta.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtenerEncabezados () throws ApplicationException{
		try {
			//mEncabezadosCheckListVO = 
			PagedList pagedList = checkListCuentaPreRequisitosManager.obtenerEncabezados(codigoConfiguracion, codigoCliente);
			mEncabezadosCheckListVO = pagedList.getItemsRangeList();
			//this.totalCount = mEncabezadosCheckListVO.size();
			totalCount = pagedList.getTotalItems();
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
	 * Metodo que atiende una peticion de traer lasa secciones de acuerdo a las tareas.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtenerTareasSeccion () throws ApplicationException{
		try {
			mTareaChecklistVO= checkListCuentaPreRequisitosManager.obtenerTareasSeccion(codigoConfiguracion, codigoSeccion);
			totalCount = mTareaChecklistVO.size();
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
	 * Metodo que permite redireccionar la pantalla a la anterior, de la cual vino.
	 * @return String
	 */
	public String regresarCheckListConfigCuentaClick(){
		return "irACheckListConfiguraCuenta";
	}
	
	/**
	 * Metodo que atiende una peticion para traer datos del detalle de la sección del checklist.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdObtenerSecciones () throws ApplicationException{
		try {
			mCheckListXCuentaSeccion = checkListCuentaPreRequisitosManager.obtenerSecciones(codigoConfiguracion);
			totalCount = mCheckListXCuentaSeccion.size();
			success = true;
			return SUCCESS;
		}catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}


	/**
	 * Metodo que atiende una peticion de salvar la información de las tareas.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String guardaTareasSeccion () throws ApplicationException{
		try {
			if (codigoConfiguracion == null || codigoConfiguracion.equals("")) {
                codigoConfiguracion = checkListCuentaPreRequisitosManager.guardaPreRequisito(codigoConfiguracion, cdPerson, codigoCliente, codigoSeccion, descripcionConf, lineaOperacion, listaParams/*, mCheckListXCuentaSeccion*/);
			} else {
				codigoConfiguracion = checkListCuentaPreRequisitosManager.guardaPreRequisito(codigoConfiguracion, cdPerson, codigoCliente, codigoSeccion, descripcionConf, lineaOperacion, listaParams/*, null*/);
			}
			success = true;
			return SUCCESS;
		}catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
	public String cmdIrEditarConfiguraCuentaClick () {
		return "editarConfiguraCuenta";
	}
	public String cmdIrAgregarConfiguraCuentaClick () {
		return "agregarConfiguraCuenta";
	}
    public List<ClienteVO> getMClientesVO() {
        return mClientesVO;
    }

    public void setMClientesVO(List<ClienteVO> mClientesVO) {
        this.mClientesVO = mClientesVO;
    }

    public List<TareaChecklistVO> getMTareaChecklistVO() {
        return mTareaChecklistVO;
    }

    public void setMTareaChecklistVO(List<TareaChecklistVO> mTareaChecklistVO) {
        this.mTareaChecklistVO = mTareaChecklistVO;
    }

    public List<EncabezadosCheckListCuentaVO> getMEncabezadosCheckListVO() {
        return mEncabezadosCheckListVO;
    }

    public void setMEncabezadosCheckListVO(List<EncabezadosCheckListCuentaVO> mEncabezadosCheckListVO) {
        this.mEncabezadosCheckListVO = mEncabezadosCheckListVO;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getCodigoConfiguracion() {
        return codigoConfiguracion;
    }

    public void setCodigoConfiguracion(String codigoConfiguracion) {
        this.codigoConfiguracion = codigoConfiguracion;
    }

    public String getCodigoSeccion() {
        return codigoSeccion;
    }

    public void setCodigoSeccion(String codigoSeccion) {
        this.codigoSeccion = codigoSeccion;
    }
	public List<CheckListXCuentaSeccion> getMCheckListXCuentaSeccion() {
		return mCheckListXCuentaSeccion;
	}
	public void setMCheckListXCuentaSeccion(
			List<CheckListXCuentaSeccion> checkListXCuentaSeccion) {
		mCheckListXCuentaSeccion = checkListXCuentaSeccion;
	}
	public void setCheckListCuentaPreRequisitosManager(
			CheckListCuentaPreRequisitosManager checkListCuentaPreRequisitosManager) {
		this.checkListCuentaPreRequisitosManager = checkListCuentaPreRequisitosManager;
	}
	public String getDescripcionConf() {
		return descripcionConf;
	}
	public void setDescripcionConf(String descripcionConf) {
		this.descripcionConf = descripcionConf;
	}
	public String getLineaOperacion() {
		return lineaOperacion;
	}
	public void setLineaOperacion(String lineaOperacion) {
		this.lineaOperacion = lineaOperacion;
	}
	public String getCodigoTarea() {
		return codigoTarea;
	}
	public void setCodigoTarea(String codigoTarea) {
		this.codigoTarea = codigoTarea;
	}
	public String getFgComple() {
		return fgComple;
	}
	public void setFgComple(String fgComple) {
		this.fgComple = fgComple;
	}
	public String getFgNoRequ() {
		return fgNoRequ;
	}
	public void setFgNoRequ(String fgNoRequ) {
		this.fgNoRequ = fgNoRequ;
	}
	public List<CheckListCuentaPreRequisitosVO> getListaParams() {
		return listaParams;
	}
	public void setListaParams(List<CheckListCuentaPreRequisitosVO> listaParams) {
		this.listaParams = listaParams;
	}




    public String getCdPerson() {
        return cdPerson;
    }

    public void setCdPerson(String cdPerson) {
        this.cdPerson = cdPerson;
    }


	public String getFgPendiente() {
		return fgPendiente;
	}


	public void setFgPendiente(String fgPendiente) {
		this.fgPendiente = fgPendiente;
	}
}
