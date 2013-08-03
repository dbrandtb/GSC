package mx.com.aon.portal.web;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.PolizasRenovacionVO;
import mx.com.aon.portal.service.SeleccionPolizasRenovacionManager;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action que atiende las solicitudes de abm de la pantalla de Seleccion de polizas para renovacion.
 *
 */
public class SeleccionPolizasRenovacionAction extends ActionSupport{
	
	private transient SeleccionPolizasRenovacionManager seleccionPolizasRenovacionManager;

	private static final long serialVersionUID = -4055368921124944287L;

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(SeleccionPolizasRenovacionAction.class);
	
	public String cdElemento;
	public String cdRamo;
	public String cdUniEco;
	public String cdTipRam;    
	public String rpDesde;
	public String rpHasta;
	public String diasVencim;
    private boolean success;
	
	
	/**
	 * Selecciona las polizas acorde a los criterios de la pantalla y las inserta.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdEjecutarClick() throws Exception{
		
		String messageResult = "";
		PolizasRenovacionVO polizasRenovacionVO = new PolizasRenovacionVO();		
		
		try{
			polizasRenovacionVO.setCdElemento(cdElemento);
			polizasRenovacionVO.setCdRamo(cdRamo);
			polizasRenovacionVO.setCdTipRam(cdTipRam);
			polizasRenovacionVO.setCdUniEco(cdUniEco);
			polizasRenovacionVO.setDiasVencim(diasVencim);
			polizasRenovacionVO.setRpDesde(rpDesde);
			polizasRenovacionVO.setRpHasta(rpHasta);
			
			messageResult = seleccionPolizasRenovacionManager.ejecutarSeleccionPolizasParaRenovacion(polizasRenovacionVO);	
            
			logger.debug("messageResult: "+messageResult);
			
			success = true;
            addActionMessage(messageResult);
            return SUCCESS;
            
        }catch(ApplicationException e)
		{
            success = false;
            logger.debug(e);
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            logger.debug(e);
            addActionError(e.getMessage());
            return SUCCESS;
        }

	}
	public void setSeleccionPolizasRenovacionManager(
			SeleccionPolizasRenovacionManager seleccionPolizasRenovacionManager) {
		this.seleccionPolizasRenovacionManager = seleccionPolizasRenovacionManager;
	}

	public boolean getSuccess() {return success;}
	public void setSuccess(boolean success) {this.success = success;}	
	
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	public String getCdUniEco() {
		return cdUniEco;
	}
	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}
	public String getCdTipRam() {
		return cdTipRam;
	}
	public void setCdTipRam(String cdTipRam) {
		this.cdTipRam = cdTipRam;
	}
	public String getRpDesde() {
		return rpDesde;
	}
	public void setRpDesde(String rpDesde) {
		this.rpDesde = rpDesde;
	}
	public String getRpHasta() {
		return rpHasta;
	}
	public void setRpHasta(String rpHasta) {
		this.rpHasta = rpHasta;
	}
	public String getDiasVencim() {
		return diasVencim;
	}
	public void setDiasVencim(String diasVencim) {
		this.diasVencim = diasVencim;
	}
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

}
