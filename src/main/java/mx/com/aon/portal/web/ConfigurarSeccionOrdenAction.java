package mx.com.aon.portal.web;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.ConfigurarSeccionOrdenVO;
import mx.com.aon.portal.service.ConfigurarSeccionOrdenManager;
import mx.com.aon.portal.service.PagedList;

public class ConfigurarSeccionOrdenAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 146434998787147L;

	private transient ConfigurarSeccionOrdenManager configurarSeccionOrdenManager;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PersonasAction.class);
	
	private boolean success;
	
	private List<ConfigurarSeccionOrdenVO> csoEstructuraList;
	
	private List<ConfigurarSeccionOrdenVO> csoGrillaList;
	
	private String cdFormatoOrden;
	private String dsFormatoOrden;
	private String cdAtribu;
	private String cdSeccion;
	//private String nmOrden;
	//private String cdTipSit;
	//private String cdTipObj;
	private String descripcionEscapedJavaScript;
	
	private int start;
	private int limit;
	private int totalCount;
	
	@SuppressWarnings("unchecked")
	public String obtenerSeccionesFormato() throws ApplicationException{
		try{
			PagedList pagedList = configurarSeccionOrdenManager.obtenerSeccionesFormato(cdFormatoOrden, start, limit);
			csoEstructuraList = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
			success = true;
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
	
	public String guardarSeccionFormatoClick() throws ApplicationException{
		String messageResult = "";
		try{
			for (int i=0; i<csoGrillaList.size(); i++) {
				ConfigurarSeccionOrdenVO configurarSeccionOrdenVO = csoGrillaList.get(i);
				messageResult = configurarSeccionOrdenManager.guardarSeccionFormato(configurarSeccionOrdenVO.getCdFormatoOrden(),configurarSeccionOrdenVO.getCdSeccion(), configurarSeccionOrdenVO.getNmOrden(), configurarSeccionOrdenVO.getCdTipSit(), configurarSeccionOrdenVO.getCdTipObj());
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
	
	public String irFormatoOrdenesTrabajoClick()throws Exception
	{
		return "formatoOrdenesTrabajo";
	}
	
	public String irValoresxDefectoClick()throws Exception
	{
		return "valoresDefectoAtributos";
	}
	
	public String borrarSeccionFormatoClick() throws ApplicationException{
		String messageResult = "";
		try{
			messageResult = configurarSeccionOrdenManager.borraSeccionFormato(cdFormatoOrden, cdSeccion);
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
	
	//GETTERS & SETTERS
	public String getCdFormatoOrden() {
		return cdFormatoOrden;
	}
	public void setCdFormatoOrden(String cdFormatoOrden) {
		this.cdFormatoOrden = cdFormatoOrden;
	}

	public void setConfigurarSeccionOrdenManager(
			ConfigurarSeccionOrdenManager configurarSeccionOrdenManager) {
		this.configurarSeccionOrdenManager = configurarSeccionOrdenManager;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<ConfigurarSeccionOrdenVO> getCsoEstructuraList() {
		return csoEstructuraList;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCdSeccion() {
		return cdSeccion;
	}

	public void setCdSeccion(String cdSeccion) {
		this.cdSeccion = cdSeccion;
	}
	/*
	public String getNmOrden() {
		return nmOrden;
	}

	public void setNmOrden(String nmOrden) {
		this.nmOrden = nmOrden;
	}

	public String getCdTipSit() {
		return cdTipSit;
	}

	public void setCdTipSit(String cdTipSit) {
		this.cdTipSit = cdTipSit;
	}

	public String getCdTipObj() {
		return cdTipObj;
	}

	public void setCdTipObj(String cdTipObj) {
		this.cdTipObj = cdTipObj;
	}
	*/
	public void setCsoGrillaList(List<ConfigurarSeccionOrdenVO> csoGrillaList) {
		this.csoGrillaList = csoGrillaList;
	}

	public List<ConfigurarSeccionOrdenVO> getCsoGrillaList() {
		return csoGrillaList;
	}

	public String getDsFormatoOrden() {
		return dsFormatoOrden;
	}

	public void setDsFormatoOrden(String dsFormatoOrden) {
		this.dsFormatoOrden = dsFormatoOrden;
	}

	public String getCdAtribu() {
		return cdAtribu;
	}

	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}

	public String getDescripcionEscapedJavaScript() {
		return descripcionEscapedJavaScript;
	}

	public void setDescripcionEscapedJavaScript(String descripcionEscapedJavaScript) {
		//this.descripcionEscapedJavaScript = descripcionEscapedJavaScript;
		this.descripcionEscapedJavaScript = StringEscapeUtils.escapeJavaScript(descripcionEscapedJavaScript);
	}
	
}
