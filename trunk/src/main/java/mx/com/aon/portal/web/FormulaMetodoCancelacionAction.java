package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.FormulaMetodoCancelacionVO;
import mx.com.aon.portal.service.FormulaMetodoCancelacionManager;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action que atiende peticiones para Formula del Metodo de Cancelacion
 *
 */
public class FormulaMetodoCancelacionAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6675861541895608321L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(FormulaMetodoCancelacionAction.class);

	private transient FormulaMetodoCancelacionManager formulaMetodoCancelacionManager;

	private String cdMetodo;
	
	private String cdTipoPrima;

	@SuppressWarnings("unchecked")
	private List mFormulaList;

	private boolean success;

	/**
	 * Metodo <code>cmdObtenerEncabezado</code> obtiene informacion de encabezado de Formula del Metodo de Cancelacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdObtenerEncabezado () throws Exception {
		try {
			FormulaMetodoCancelacionVO formulaMetodoCancelacionVO = new FormulaMetodoCancelacionVO();
			formulaMetodoCancelacionVO = formulaMetodoCancelacionManager.getEncabezado(cdMetodo);
			mFormulaList = new ArrayList();
			mFormulaList.add(formulaMetodoCancelacionVO);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			success = true;
			return SUCCESS;
		}
	}
	
	/**
	 * Metodo <code>irFormulaMetodoCancelacion</code> usado para redireccionar a la pantalla Formula del Metodo de Cancelacion
	 * 
	 * @return
	 */
	public String irFormulaMetodoCancelacion () {
		return "formulaMetodoCancelacion";
	}

	public String getCdMetodo() {
		return cdMetodo;
	}

	public void setCdMetodo(String cdMetodo) {
		this.cdMetodo = cdMetodo;
	}

	@SuppressWarnings("unchecked")
	public List getMFormulaList() {
		return mFormulaList;
	}

	@SuppressWarnings("unchecked")
	public void setMFormulaList(List formulaList) {
		mFormulaList = formulaList;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setFormulaMetodoCancelacionManager(
			FormulaMetodoCancelacionManager formulaMetodoCancelacionManager) {
		this.formulaMetodoCancelacionManager = formulaMetodoCancelacionManager;
	}

	public String getCdTipoPrima() {
		return cdTipoPrima;
	}

	public void setCdTipoPrima(String cdTipoPrima) {
		this.cdTipoPrima = cdTipoPrima;
	}
}
