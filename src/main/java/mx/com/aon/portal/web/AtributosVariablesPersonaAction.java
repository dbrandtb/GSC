package mx.com.aon.portal.web;

import java.util.List;

import mx.com.aon.portal.model.AtributosVariablesPersonaVO;
import mx.com.aon.portal.service.AtributosVariablesPersonaManager;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class AtributosVariablesPersonaAction extends ActionSupport{

	private static final long serialVersionUID = 114132132312354L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AtributosVariablesPersonaAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AtributosVariablesPersonaManager atributosVariablesPersonaManager;
	
	private List<AtributosVariablesPersonaVO> grillaListAtributosVblesPersona;
	
	private String cdAtribu;
	private String dsAtribu;
	private String swFormat;
	private String nmLMin;
	private String nmLMax;
	private String cdFisJur;
	private String cdCatego;
	private String swObliga;
	private String otTabVal;  
	private String gbSwFormat;

	private boolean success;


	
	/**
	 * Metodo que elimina un registro seleccionado del grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = atributosVariablesPersonaManager.borrarAtributosVariablesPersona(cdAtribu);	
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
	 * Metodo que atualiza una ayuda de coberturas modificada.
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
        	AtributosVariablesPersonaVO atributosVariablesPersonaVO = new AtributosVariablesPersonaVO();
	    	
	    	for (int i=0; i<grillaListAtributosVblesPersona.size(); i++) {
	    		
	    		AtributosVariablesPersonaVO atributosVariablesPersonaVO_grid=grillaListAtributosVblesPersona.get(i);
	    		
	    		atributosVariablesPersonaVO.setCdAtribu(atributosVariablesPersonaVO_grid.getCdAtribu());
	    		atributosVariablesPersonaVO.setDsAtribu(atributosVariablesPersonaVO_grid.getDsAtribu());
	    		atributosVariablesPersonaVO.setSwFormat(atributosVariablesPersonaVO_grid.getSwFormat());
	    		atributosVariablesPersonaVO.setNmlmax(atributosVariablesPersonaVO_grid.getNmlmax());
	    		atributosVariablesPersonaVO.setNmlmin(atributosVariablesPersonaVO_grid.getNmlmin());
	        	String _swObliga = (atributosVariablesPersonaVO_grid.getSwObliga().equals("true"))?"S":"N";
	        	atributosVariablesPersonaVO._setSwObliga(_swObliga);
	        	atributosVariablesPersonaVO.setOtTabVal(atributosVariablesPersonaVO_grid.getOtTabVal());
	        	atributosVariablesPersonaVO.setGbSwFormat(atributosVariablesPersonaVO_grid.getGbSwFormat());
	        	atributosVariablesPersonaVO.setCdFisJur(atributosVariablesPersonaVO_grid.getCdFisJur());
	        	atributosVariablesPersonaVO.setCdCatego(atributosVariablesPersonaVO_grid.getCdCatego());

	        	messageResult = atributosVariablesPersonaManager.guardarAtributosVariablesPersona(atributosVariablesPersonaVO);
	    	}
        	        	
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

	public String getCdAtribu() {
		return cdAtribu;
	}

	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}

	public String getDsAtribu() {
		return dsAtribu;
	}

	public void setDsAtribu(String dsAtribu) {
		this.dsAtribu = dsAtribu;
	}

	public String getSwFormat() {
		return swFormat;
	}

	public void setSwFormat(String swFormat) {
		this.swFormat = swFormat;
	}

	public String getNmLMin() {
		return nmLMin;
	}

	public void setNmLMin(String nmLMin) {
		this.nmLMin = nmLMin;
	}

	public String getNmLMax() {
		return nmLMax;
	}

	public void setNmLMax(String nmLMax) {
		this.nmLMax = nmLMax;
	}

	public String getCdFisJur() {
		return cdFisJur;
	}

	public void setCdFisJur(String cdFisJur) {
		this.cdFisJur = cdFisJur;
	}

	public String getCdCatego() {
		return cdCatego;
	}

	public void setCdCatego(String cdCatego) {
		this.cdCatego = cdCatego;
	}

	public String getSwObliga() {
		return swObliga;
	}

	public void setSwObliga(String swObliga) {
		this.swObliga = swObliga;
	}

	public String getOtTabVal() {
		return otTabVal;
	}

	public void setOtTabVal(String otTabVal) {
		this.otTabVal = otTabVal;
	}

	public String getGbSwFormat() {
		return gbSwFormat;
	}

	public void setGbSwFormat(String gbSwFormat) {
		this.gbSwFormat = gbSwFormat;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setAtributosVariablesPersonaManager(
			AtributosVariablesPersonaManager atributosVariablesPersonaManager) {
		this.atributosVariablesPersonaManager = atributosVariablesPersonaManager;
	}

	public List<AtributosVariablesPersonaVO> getGrillaListAtributosVblesPersona() {
		return grillaListAtributosVblesPersona;
	}

	public void setGrillaListAtributosVblesPersona(
			List<AtributosVariablesPersonaVO> grillaListAtributosVblesPersona) {
		this.grillaListAtributosVblesPersona = grillaListAtributosVblesPersona;
	}
}
