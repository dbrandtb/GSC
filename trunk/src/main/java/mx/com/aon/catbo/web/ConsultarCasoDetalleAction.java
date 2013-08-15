package mx.com.aon.catbo.web;

import java.util.ArrayList;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.CasoDetalleVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.FormatoDocumentoVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import java.util.List;

import org.apache.log4j.Logger;


import com.opensymphony.xwork2.ActionSupport;

public class ConsultarCasoDetalleAction extends ActionSupport{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 13787813468489L;
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	//private transient Manager manager;
	private transient AdministracionCasosManager administracionCasosManager;
	private List<CasoDetalleVO> mEstructuraCasoList;
	
	
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConsultarCasoDetalleAction.class);
	
	private String pv_cdproceso_i;
	private String pv_cdnivatn_i;
	private String nmcaso; 
	private String pv_cdusuario_i;
	private String pv_nmcompra_i; 
	private String pv_tunidad_i;
	private String cdmatriz;
	private String cdperson;
	private String cdformatoorden;
	private String flag;
	
	
	private String messageResult;
	
	private boolean success;
	
	public String cmdEncabezadoCasoDetalle()throws Exception{
		try
		{
			mEstructuraCasoList = new ArrayList<CasoDetalleVO>();
			CasoDetalleVO vo = administracionCasosManager.getObtenerCaso(nmcaso, cdmatriz);
			
			mEstructuraCasoList.add(vo);
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

	
	public String cmdValidacionCompraTiempo()throws ApplicationException{
		try
		{			
			messageResult = this.administracionCasosManager.validaCompraTiempo(pv_cdproceso_i, Integer.parseInt(pv_cdnivatn_i),nmcaso);
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
		
	/*
	public String cmdCompraDisponible()throws Exception{
		try
		{
			mEstructuraCasoList = new ArrayList<CasoVO>();
			CasoVO casoVO= this.administracionCasosManager
			mEstructuraCasoList.add(casoVO);
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
	*/
	
	public String cmdCompraConsumida()throws Exception{
		try
		{
			/*mTiempoDisponibleVO = new ArrayList<VO>();
			VO vo = manager.get(params);
			mTiempoDisponibleVO.add(vo);*/
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
	
	public String btnGuardarCompraDeTiempoClick()throws Exception{
		try
		{
			messageResult = this.administracionCasosManager.guardarCompra(nmcaso, pv_cdusuario_i, pv_cdnivatn_i, pv_nmcompra_i, pv_tunidad_i);
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
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


	public AdministracionCasosManager obtenAdministracionCasosManager() {
		return administracionCasosManager;
	}


	public void setAdministracionCasosManager(
			AdministracionCasosManager administracionCasosManager) {
		this.administracionCasosManager = administracionCasosManager;
	}




	public List<CasoDetalleVO> getMEstructuraCasoList() {
		return mEstructuraCasoList;
	}


	public void setMEstructuraCasoList(List<CasoDetalleVO> estructuraCasoList) {
		mEstructuraCasoList = estructuraCasoList;
	}


	public String getPv_cdproceso_i() {
		return pv_cdproceso_i;
	}


	public void setPv_cdproceso_i(String pv_cdproceso_i) {
		this.pv_cdproceso_i = pv_cdproceso_i;
	}


	public String getPv_cdnivatn_i() {
		return pv_cdnivatn_i;
	}


	public void setPv_cdnivatn_i(String pv_cdnivatn_i) {
		this.pv_cdnivatn_i = pv_cdnivatn_i;
	}


	public String getMessageResult() {
		return messageResult;
	}


	public void setMessageResult(String messageResult) {
		this.messageResult = messageResult;
	}


	public String getPv_cdusuario_i() {
		return pv_cdusuario_i;
	}


	public void setPv_cdusuario_i(String pv_cdusuario_i) {
		this.pv_cdusuario_i = pv_cdusuario_i;
	}


	public String getPv_nmcompra_i() {
		return pv_nmcompra_i;
	}


	public void setPv_nmcompra_i(String pv_nmcompra_i) {
		this.pv_nmcompra_i = pv_nmcompra_i;
	}


	public String getPv_tunidad_i() {
		return pv_tunidad_i;
	}


	public void setPv_tunidad_i(String pv_tunidad_i) {
		this.pv_tunidad_i = pv_tunidad_i;
	}


	public String getNmcaso() {
		return nmcaso;
	}


	public void setNmcaso(String nmcaso) {
		this.nmcaso = nmcaso;
	}


	public String getCdmatriz() {
		return cdmatriz;
	}


	public void setCdmatriz(String cdmatriz) {
		this.cdmatriz = cdmatriz;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getCdperson() {
		return cdperson;
	}


	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}


	public String getCdformatoorden() {
		return cdformatoorden;
	}


	public void setCdformatoorden(String cdformatoorden) {
		this.cdformatoorden = cdformatoorden;
	}



		
}
