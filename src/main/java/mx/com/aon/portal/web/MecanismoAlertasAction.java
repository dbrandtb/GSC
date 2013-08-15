package mx.com.aon.portal.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.MecanismoAlertaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.MecanismosAlertasManager;
import mx.com.aon.portal.service.PagedList;

import org.apache.log4j.Logger;


public class MecanismoAlertasAction extends AbstractListAction{
	/**
	 *Serial Version 
	 */
	protected final transient Logger logger = Logger.getLogger(MecanismoAlertasAction.class);
	
	private transient MecanismosAlertasManager mecanismosAlertasManager;
	
	 private List<MecanismoAlertaVO> mEstructuraList;
	 
	private static final long serialVersionUID = 8276444738312293918L;
	private boolean success;
	@SuppressWarnings("unused")
	
	private String cdUsuario;
	private String cdRol;
	private String cdElemento;
	private String cdProceso;
	private String fecha;
	private String cdRamo;
	
	
	@SuppressWarnings("unchecked")
	public String cmdBuscarMensajesAlertaClick() {

		UserVO usuario = (UserVO)session.get("USUARIO");
		if(usuario != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date hoy = new Date();
			String fechaActual = (String)dateFormat.format(hoy).toString();
			
		cdUsuario = usuario.getUser();
		cdRol = usuario.getRolActivo().getObjeto().getValue();
		cdElemento = usuario.getEmpresa().getElementoId();
		cdProceso = null;
		fecha = fechaActual;
		cdRamo = null;
		}
	
		
        try {
        	
            PagedList  pagedList =  this.mecanismosAlertasManager.buscarMensajesAlertas(cdUsuario, cdRol, cdElemento, cdProceso, fecha, cdRamo, start, limit);
            this.mEstructuraList = pagedList.getItemsRangeList();
            this.totalCount = pagedList.getTotalItems();
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
	
	
	public String cmdBuscarMensajesAlertaPantallaClick() {
		
		UserVO usuario = (UserVO)session.get("USUARIO");
		
		if(usuario != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date hoy = new Date();
			String fechaActual = dateFormat.format(hoy).toString();
			
			cdUsuario = usuario.getUser();
			cdRol = usuario.getRolActivo().getObjeto().getValue();
			cdElemento = usuario.getEmpresa().getElementoId();
			cdProceso = null; 
			fecha = fechaActual;
			cdRamo = null;
			if(logger.isDebugEnabled()){
				logger.debug("cdUsuario=" + cdUsuario);
				logger.debug("cdRol=" + cdRol);
				logger.debug("cdElemento=" + cdElemento);
				logger.debug("fechaActual=" + fecha);
			}
		}
		
        try {
            this.mEstructuraList = mecanismosAlertasManager.buscarMensajesAlertasPantalla(cdUsuario, cdRol, cdElemento, cdProceso, fecha, cdRamo);
            if(logger.isDebugEnabled()){
            	logger.debug("mEstructuraList Pantalla->" + mEstructuraList);
            	logger.debug("mEstructuraList Pantalla size->" + mEstructuraList.size());
            }
            this.totalCount = mEstructuraList.size();
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public MecanismosAlertasManager obtenMecanismosAlertasManager() {
		return mecanismosAlertasManager;
	}

	public void setMecanismosAlertasManager(
			MecanismosAlertasManager mecanismosAlertasManager) {
		this.mecanismosAlertasManager = mecanismosAlertasManager;
	}

	public List<MecanismoAlertaVO> getMEstructuraList() {
		return mEstructuraList;
	}

	public void setMEstructuraList(List<MecanismoAlertaVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public String getCdUsuario() {
		return cdUsuario;
	}

	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}

	public String getCdRol() {
		return cdRol;
	}

	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getCdProceso() {
		return cdProceso;
	}

	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCdRamo() {
		return cdRamo;
	}
	
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

}