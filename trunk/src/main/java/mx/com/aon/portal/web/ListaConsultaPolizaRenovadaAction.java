package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.CoberturaPolAntVO;
import mx.com.aon.portal.model.ExtJSComboBoxVO;
import mx.com.aon.portal.model.ExtJSDateFieldVO;
import mx.com.aon.portal.model.ExtJSFieldVO;
import mx.com.aon.portal.model.ItemVO;
import mx.com.aon.portal.model.MPoliObjVO;
import mx.com.aon.portal.model.ObjetoAsegurableVO;
import mx.com.aon.portal.model.ReciboPolizaVO;
import mx.com.aon.portal.model.TValoObjVO;
import mx.com.aon.portal.service.ConsultaPolizaRenovadaManager;
import mx.com.aon.portal.service.PagedList;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Consulta de Poliza Renovada.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaConsultaPolizaRenovadaAction extends AbstractListAction implements SessionAware{
	
	private static final long serialVersionUID = 13566341544878L;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaConsultaPolizaRenovadaAction.class);
	
	private transient ConsultaPolizaRenovadaManager consultaPolizaRenovadaManager;
	
	private List<ObjetoAsegurableVO> mListObjetosAsegurables;
	private List<ItemVO> mListFuncionesEnPoliza;
	private List<CoberturaPolAntVO> mListCoberturaPolizaAnterior;
	private List<MPoliObjVO> mListAccesoriosEquipoEspecial;
	private List<TValoObjVO> mListDetalleEquipoEspecial;
	private List<ReciboPolizaVO> mListRecibos;
	
	private String cdUniEco;
	private String cdRamo;
	private String estado;
	private String nmPoliza;
	private String nmSituac;
	private String cdPerson;
	private String cdAtribu;
	private String cdRol;
	private String cdGarant;
	private String status;
	private String nmObjeto;
	private String cdTipObj;
	
	@SuppressWarnings("unchecked")
	private List modelControl = new ArrayList();
	
	@SuppressWarnings("unchecked")
	private Map session;

	/**
	 * Metodo del action que obtiene un registro de atributos variables
	 *  de la pantalla Datos Generales de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void obtenerValoresPoliza()throws ApplicationException{
		@SuppressWarnings("unused")
		StringBuilder store = new StringBuilder();

		PagedList pagedList = consultaPolizaRenovadaManager.obtenerValoresPoliza(cdUniEco, cdRamo, estado, nmPoliza, cdAtribu, start, 80);
		totalCount = pagedList.getTotalItems();
		success = true;
		if (session.get("modelControl") != null) {
			session.remove("modelControl");
		}
		List lista = pagedList.getItemsRangeList();
		for (int i=0; i<lista.size(); i++) {
			ExtJSFieldVO extJSFieldVO = (ExtJSFieldVO)lista.get(i);
			@SuppressWarnings("unused")
			String extJSField = new String();
			if (extJSFieldVO.getXtype().equals("textfield")) {
				extJSFieldVO.setAnchor("90%");
				modelControl.add(extJSFieldVO);
			}
			if (extJSFieldVO.getXtype().equals("combo")) {
				ExtJSComboBoxVO extJSComboBoxVO = new ExtJSComboBoxVO();
				extJSComboBoxVO.setAllowBlank(extJSFieldVO.getAllowBlank());//(extJSFieldVO.getAllowBlank())?"S":"N");
				extJSComboBoxVO.setAnchor("90%");
				extJSComboBoxVO.setCdAtribu(extJSFieldVO.getCdAtribu());
				extJSComboBoxVO.setFieldLabel(extJSFieldVO.getFieldLabel());
				extJSComboBoxVO.setId(extJSFieldVO.getId());
				extJSComboBoxVO.setName(extJSFieldVO.getName());
				extJSComboBoxVO.setValue(extJSFieldVO.getValue());
				extJSComboBoxVO.setMinLength(extJSFieldVO.getMinLength());
				extJSComboBoxVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{codigo}.{descripcion}\" class=\"x-combo-list-item\">{descripcion}</div></tpl>");
				
				extJSComboBoxVO.setXtype("LIST");
				extJSComboBoxVO.setEmpyText("Seleccione ...");
				extJSComboBoxVO.setDisplayField("descripcion");
				extJSComboBoxVO.setForceSelection("true");
				extJSComboBoxVO.setHiddenName("dsHidden");
				extJSComboBoxVO.setLabelSeparator("");
				extJSComboBoxVO.setMode("local");
				extJSComboBoxVO.setSelectOnFocus("true");
				extJSComboBoxVO.setSelectOnFocus("true");
				extJSComboBoxVO.setTriggerAction("all");
				extJSComboBoxVO.setTypeAhead("true");
				extJSComboBoxVO.setValueField("codigo");
				extJSComboBoxVO.setStore("crearStoreDatosAdicionales2('" + extJSFieldVO.getOtTabVal() + "', '" + extJSFieldVO.getName() + "', '" + extJSFieldVO.getValue() + "')");
				extJSComboBoxVO.setWidth("120");
				extJSComboBoxVO.setLazyRender("true");
				modelControl.add(extJSComboBoxVO);
			}
			if (extJSFieldVO.getXtype().equals("datefield")) {
				ExtJSDateFieldVO extJSDateFieldVO = new ExtJSDateFieldVO();
				extJSDateFieldVO.setAllowBlank(extJSFieldVO.getAllowBlank());//(extJSFieldVO.getAllowBlank())?"S":"N");
				extJSDateFieldVO.setCdAtribu(extJSFieldVO.getCdAtribu());
				extJSDateFieldVO.setFieldLabel(extJSFieldVO.getFieldLabel());
				extJSDateFieldVO.setId(extJSFieldVO.getId());
				extJSDateFieldVO.setName(extJSFieldVO.getName());
				extJSDateFieldVO.setValue(extJSFieldVO.getValue());
				extJSDateFieldVO.setAnchor("90%");
				extJSDateFieldVO.setMinLength(extJSFieldVO.getMinLength());
				extJSDateFieldVO.setXtype("FECHA");
				extJSDateFieldVO.setWidth("100");
				extJSDateFieldVO.setFormat("d/m/Y");
				modelControl.add(extJSDateFieldVO);
			}
		}
		session.put("modelControl", modelControl);		
	}
	
	/**
	 * Metodo del action que obtiene un registro de atributos variables de la pantalla emergente
	 * Datos Variables de la Funcion en la Poliza de Objetos Asegurables de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void obtenerDatosVariablesDeFuncionEnPoliza()throws ApplicationException{
		@SuppressWarnings("unused")
		StringBuilder store = new StringBuilder();

		PagedList pagedList = consultaPolizaRenovadaManager.obtenerDatosVblesDeFuncioEnPoliza(cdUniEco, cdRamo, estado, nmPoliza, nmSituac, cdRol, cdPerson, cdAtribu, start, 80);
		totalCount = pagedList.getTotalItems();
		success = true;
		if (session.get("modelControl") != null) {
			session.remove("modelControl");
		}
		List lista = pagedList.getItemsRangeList();
		for (int i=0; i<lista.size(); i++) {
			ExtJSFieldVO extJSFieldVO = (ExtJSFieldVO)lista.get(i);
			@SuppressWarnings("unused")
			String extJSField = new String();
			if (extJSFieldVO.getXtype().equals("textfield")) {
				extJSFieldVO.setAnchor("90%");
				modelControl.add(extJSFieldVO);
			}
			if (extJSFieldVO.getXtype().equals("combo")) {
				ExtJSComboBoxVO extJSComboBoxVO = new ExtJSComboBoxVO();
				extJSComboBoxVO.setAllowBlank(extJSFieldVO.getAllowBlank());//(extJSFieldVO.getAllowBlank())?"S":"N");
				extJSComboBoxVO.setAnchor("90%");
				extJSComboBoxVO.setCdAtribu(extJSFieldVO.getCdAtribu());
				extJSComboBoxVO.setFieldLabel(extJSFieldVO.getFieldLabel());
				extJSComboBoxVO.setId(extJSFieldVO.getId());
				extJSComboBoxVO.setName(extJSFieldVO.getName());
				extJSComboBoxVO.setValue(extJSFieldVO.getValue());
				extJSComboBoxVO.setMinLength(extJSFieldVO.getMinLength());
				extJSComboBoxVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{codigo}.{descripcion}\" class=\"x-combo-list-item\">{descripcion}</div></tpl>");
				
				extJSComboBoxVO.setXtype("LIST");
				extJSComboBoxVO.setEmpyText("Seleccione ...");
				extJSComboBoxVO.setDisplayField("descripcion");
				extJSComboBoxVO.setForceSelection("true");
				extJSComboBoxVO.setHiddenName("dsHidden");
				extJSComboBoxVO.setLabelSeparator("");
				extJSComboBoxVO.setMode("local");
				extJSComboBoxVO.setSelectOnFocus("true");
				extJSComboBoxVO.setSelectOnFocus("true");
				extJSComboBoxVO.setTriggerAction("all");
				extJSComboBoxVO.setTypeAhead("true");
				extJSComboBoxVO.setValueField("codigo");
				extJSComboBoxVO.setStore("crearStoreDatosAdicionales2('" + extJSFieldVO.getOtTabVal() + "', '" + extJSFieldVO.getName() + "', '" + extJSFieldVO.getValue() + "')");
				extJSComboBoxVO.setWidth("120");
				extJSComboBoxVO.setLazyRender("true");
				modelControl.add(extJSComboBoxVO);
			}
			if (extJSFieldVO.getXtype().equals("datefield")) {
				ExtJSDateFieldVO extJSDateFieldVO = new ExtJSDateFieldVO();
				extJSDateFieldVO.setAllowBlank(extJSFieldVO.getAllowBlank());//(extJSFieldVO.getAllowBlank())?"S":"N");
				extJSDateFieldVO.setCdAtribu(extJSFieldVO.getCdAtribu());
				extJSDateFieldVO.setFieldLabel(extJSFieldVO.getFieldLabel());
				extJSDateFieldVO.setId(extJSFieldVO.getId());
				extJSDateFieldVO.setName(extJSFieldVO.getName());
				extJSDateFieldVO.setValue(extJSFieldVO.getValue());
				extJSDateFieldVO.setAnchor("90%");
				extJSDateFieldVO.setMinLength(extJSFieldVO.getMinLength());
				extJSDateFieldVO.setXtype("FECHA");
				extJSDateFieldVO.setWidth("100");
				extJSDateFieldVO.setFormat("d/m/Y");
				modelControl.add(extJSDateFieldVO);
			}
		}
		session.put("modelControl", modelControl);		
	}
	
	/**
	 * Metodo del action que obtiene un registro de atributos variables de la pantalla
	 * Datos Variables del Objeto Asegurable de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void obtenerDatosVariablesObjetoAsegurable()throws ApplicationException{
		@SuppressWarnings("unused")
		StringBuilder store = new StringBuilder();

		PagedList pagedList = consultaPolizaRenovadaManager.obtenerDatosDelObjeto(cdUniEco, cdRamo, estado, nmPoliza, nmSituac, start, 80);
		totalCount = pagedList.getTotalItems();
		success = true;
		if (session.get("modelControl") != null) {
			session.remove("modelControl");
		}
		List lista = pagedList.getItemsRangeList();
		for (int i=0; i<lista.size(); i++) {
			ExtJSFieldVO extJSFieldVO = (ExtJSFieldVO)lista.get(i);
			@SuppressWarnings("unused")
			String extJSField = new String();
			if (extJSFieldVO.getXtype().equals("textfield")) {
				extJSFieldVO.setAnchor("90%");
				modelControl.add(extJSFieldVO);
			}
			if (extJSFieldVO.getXtype().equals("combo")) {
				ExtJSComboBoxVO extJSComboBoxVO = new ExtJSComboBoxVO();
				extJSComboBoxVO.setAllowBlank(extJSFieldVO.getAllowBlank());//(extJSFieldVO.getAllowBlank())?"S":"N");
				extJSComboBoxVO.setAnchor("90%");
				extJSComboBoxVO.setCdAtribu(extJSFieldVO.getCdAtribu());
				extJSComboBoxVO.setFieldLabel(extJSFieldVO.getFieldLabel());
				extJSComboBoxVO.setId(extJSFieldVO.getId());
				extJSComboBoxVO.setName(extJSFieldVO.getName());
				extJSComboBoxVO.setValue(extJSFieldVO.getValue());
				extJSComboBoxVO.setMinLength(extJSFieldVO.getMinLength());
				extJSComboBoxVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{codigo}.{descripcion}\" class=\"x-combo-list-item\">{descripcion}</div></tpl>");
				
				extJSComboBoxVO.setXtype("LIST");
				extJSComboBoxVO.setEmpyText("Seleccione ...");
				extJSComboBoxVO.setDisplayField("descripcion");
				extJSComboBoxVO.setForceSelection("true");
				extJSComboBoxVO.setHiddenName("dsHidden");
				extJSComboBoxVO.setLabelSeparator("");
				extJSComboBoxVO.setMode("local");
				extJSComboBoxVO.setSelectOnFocus("true");
				extJSComboBoxVO.setSelectOnFocus("true");
				extJSComboBoxVO.setTriggerAction("all");
				extJSComboBoxVO.setTypeAhead("true");
				extJSComboBoxVO.setValueField("codigo");
				extJSComboBoxVO.setStore("crearStoreDatosAdicionales2('" + extJSFieldVO.getOtTabVal() + "', '" + extJSFieldVO.getName() + "', '" + extJSFieldVO.getValue() + "')");
				extJSComboBoxVO.setWidth("120");
				extJSComboBoxVO.setLazyRender("true");
				modelControl.add(extJSComboBoxVO);
			}
			if (extJSFieldVO.getXtype().equals("datefield")) {
				ExtJSDateFieldVO extJSDateFieldVO = new ExtJSDateFieldVO();
				extJSDateFieldVO.setAllowBlank(extJSFieldVO.getAllowBlank());//(extJSFieldVO.getAllowBlank())?"S":"N");
				extJSDateFieldVO.setCdAtribu(extJSFieldVO.getCdAtribu());
				extJSDateFieldVO.setFieldLabel(extJSFieldVO.getFieldLabel());
				extJSDateFieldVO.setId(extJSFieldVO.getId());
				extJSDateFieldVO.setName(extJSFieldVO.getName());
				extJSDateFieldVO.setValue(extJSFieldVO.getValue());
				extJSDateFieldVO.setAnchor("90%");
				extJSDateFieldVO.setMinLength(extJSFieldVO.getMinLength());
				extJSDateFieldVO.setXtype("FECHA");
				extJSDateFieldVO.setWidth("100");
				extJSDateFieldVO.setFormat("d/m/Y");
				modelControl.add(extJSDateFieldVO);
			}
		}
		session.put("modelControl", modelControl);		
	}
	
	/**
	 * Metodo del action que obtiene un registro de atributos variables de la pantalla 
	 * Datos de Coberturas de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void obtenerDatosVariablesCobertura()throws ApplicationException{
		@SuppressWarnings("unused")
		StringBuilder store = new StringBuilder();

		PagedList pagedList = consultaPolizaRenovadaManager.obtieneDatosVblesCobertura(cdUniEco, cdRamo, estado, nmPoliza,
				nmSituac, cdGarant, cdAtribu, status, start, 80);
		totalCount = pagedList.getTotalItems();
		success = true;
		if (session.get("modelControl") != null) {
			session.remove("modelControl");
		}
		List lista = pagedList.getItemsRangeList();
		for (int i=0; i<lista.size(); i++) {
			ExtJSFieldVO extJSFieldVO = (ExtJSFieldVO)lista.get(i);
			@SuppressWarnings("unused")
			String extJSField = new String();
			if (extJSFieldVO.getXtype().equals("textfield")) {
				extJSFieldVO.setAnchor("90%");
				modelControl.add(extJSFieldVO);
			}
			if (extJSFieldVO.getXtype().equals("combo")) {
				ExtJSComboBoxVO extJSComboBoxVO = new ExtJSComboBoxVO();
				extJSComboBoxVO.setAllowBlank(extJSFieldVO.getAllowBlank());//(extJSFieldVO.getAllowBlank())?"S":"N");
				extJSComboBoxVO.setAnchor("90%");
				extJSComboBoxVO.setCdAtribu(extJSFieldVO.getCdAtribu());
				extJSComboBoxVO.setFieldLabel(extJSFieldVO.getFieldLabel());
				extJSComboBoxVO.setId(extJSFieldVO.getId());
				extJSComboBoxVO.setName(extJSFieldVO.getName());
				extJSComboBoxVO.setValue(extJSFieldVO.getValue());
				extJSComboBoxVO.setMinLength(extJSFieldVO.getMinLength());
				extJSComboBoxVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{codigo}.{descripcion}\" class=\"x-combo-list-item\">{descripcion}</div></tpl>");
				
				extJSComboBoxVO.setXtype("LIST");
				extJSComboBoxVO.setEmpyText("Seleccione ...");
				extJSComboBoxVO.setDisplayField("descripcion");
				extJSComboBoxVO.setForceSelection("true");
				extJSComboBoxVO.setHiddenName("dsHidden");
				extJSComboBoxVO.setLabelSeparator("");
				extJSComboBoxVO.setMode("local");
				extJSComboBoxVO.setSelectOnFocus("true");
				extJSComboBoxVO.setSelectOnFocus("true");
				extJSComboBoxVO.setTriggerAction("all");
				extJSComboBoxVO.setTypeAhead("true");
				extJSComboBoxVO.setValueField("codigo");
				extJSComboBoxVO.setStore("crearStoreDatosAdicionales2('" + extJSFieldVO.getOtTabVal() + "', '" + extJSFieldVO.getName() + "', '" + extJSFieldVO.getValue() + "')");
				extJSComboBoxVO.setWidth("120");
				extJSComboBoxVO.setLazyRender("true");
				modelControl.add(extJSComboBoxVO);
			}
			if (extJSFieldVO.getXtype().equals("datefield")) {
				ExtJSDateFieldVO extJSDateFieldVO = new ExtJSDateFieldVO();
				extJSDateFieldVO.setAllowBlank(extJSFieldVO.getAllowBlank());//(extJSFieldVO.getAllowBlank())?"S":"N");
				extJSDateFieldVO.setCdAtribu(extJSFieldVO.getCdAtribu());
				extJSDateFieldVO.setFieldLabel(extJSFieldVO.getFieldLabel());
				extJSDateFieldVO.setId(extJSFieldVO.getId());
				extJSDateFieldVO.setName(extJSFieldVO.getName());
				extJSDateFieldVO.setValue(extJSFieldVO.getValue());
				extJSDateFieldVO.setAnchor("90%");
				extJSDateFieldVO.setMinLength(extJSFieldVO.getMinLength());
				extJSDateFieldVO.setXtype("FECHA");
				extJSDateFieldVO.setWidth("100");
				extJSDateFieldVO.setFormat("d/m/Y");
				modelControl.add(extJSDateFieldVO);
			}
		}
		session.put("modelControl", modelControl);		
	}
	
	
	/**
	 * Ejecuta la busqueda para el llenado del grid de la pantalla Objetos Asegurables de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtenerObjetosAsegurables() throws Exception{
		try{

            PagedList pagedList = consultaPolizaRenovadaManager.obtenerObjetosAsegurables(cdUniEco, cdRamo, estado, nmPoliza, start, limit); 
            mListObjetosAsegurables = pagedList.getItemsRangeList();
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
	 * Ejecuta la busqueda para el llenado del grid Función en la poliza de la pantalla Objetos Asegurables
	 *  de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtenerFuncionesEnPoliza() throws Exception{
		try{

            PagedList pagedList = consultaPolizaRenovadaManager.obtenerFunciones(cdUniEco, cdRamo, estado, nmPoliza, nmSituac, cdPerson, start, limit); 
            mListFuncionesEnPoliza = pagedList.getItemsRangeList();
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
	 * Ejecuta la busqueda para el llenado del grid Función en la poliza de la pantalla Coberturas de polizas a renovar
	 *  de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtenerCoberturaAnterior() throws Exception{
		try{

            PagedList pagedList = consultaPolizaRenovadaManager.obtieneCoberturaPolizaAnterior(cdUniEco, cdRamo, nmPoliza, nmSituac, start, limit);
            mListCoberturaPolizaAnterior = pagedList.getItemsRangeList();
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
	 * Ejecuta la busqueda para el llenado del grid Función en la poliza de la pantalla Coberturas de polizas a renovar
	 *  de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtenerCoberturaRenovada()throws Exception{
		try{
			PagedList pagedList = consultaPolizaRenovadaManager.obtieneCoberturaPolizaRenovada(cdUniEco, cdRamo, nmPoliza, nmSituac, start, limit);
			mListCoberturaPolizaAnterior = pagedList.getItemsRangeList();
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
	
	/**
	 * Ejecuta la busqueda para el llenado del grid Lista de Equipo Especial de la pantalla Accesorios 
	 * de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtenerListaEquipoEspecial()throws Exception{
		try{
			PagedList pagedList = consultaPolizaRenovadaManager.obtieneEquipoEspecial(cdUniEco, cdRamo, estado, nmPoliza, nmSituac, nmObjeto, start, limit);
			mListAccesoriosEquipoEspecial = pagedList.getItemsRangeList();
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
	
	/**
	 * Ejecuta la busqueda para el llenado del grid Equipo Especial de la pantalla Accesorios 
	 * de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtenerListaDetalleEquipoEspecial()throws Exception{
		try{
			PagedList pagedList = consultaPolizaRenovadaManager.obtieneDetalleEquipoEspecial(cdUniEco, cdRamo, estado, nmPoliza, nmSituac, nmObjeto, cdAtribu, start, limit);
			mListDetalleEquipoEspecial = pagedList.getItemsRangeList();
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
	
	/**
	 * Metodo del action que obtiene atributos variables cuando se selecciona un tipo de objeto
	 * en el combo Tipo de la pantalla Accesorios.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void obtenerAtributosVblesSegunTipoObjeto()throws ApplicationException{
		@SuppressWarnings("unused")
		StringBuilder store = new StringBuilder();

		PagedList pagedList = consultaPolizaRenovadaManager.obtenerDatosVblesSegunTipoObjeto(cdTipObj, start, limit);
		totalCount = pagedList.getTotalItems();
		success = true;
		if (session.get("modelControl") != null) {
			session.remove("modelControl");
		}
		List lista = pagedList.getItemsRangeList();
		for (int i=0; i<lista.size(); i++) {
			ExtJSFieldVO extJSFieldVO = (ExtJSFieldVO)lista.get(i);
			@SuppressWarnings("unused")
			String extJSField = new String();
			if (extJSFieldVO.getXtype().equals("textfield")) {
				extJSFieldVO.setAnchor("90%");
				modelControl.add(extJSFieldVO);
			}
			if (extJSFieldVO.getXtype().equals("combo")) {
				ExtJSComboBoxVO extJSComboBoxVO = new ExtJSComboBoxVO();
				extJSComboBoxVO.setAllowBlank(extJSFieldVO.getAllowBlank());//(extJSFieldVO.getAllowBlank())?"S":"N");
				extJSComboBoxVO.setAnchor("90%");
				extJSComboBoxVO.setCdAtribu(extJSFieldVO.getCdAtribu());
				extJSComboBoxVO.setFieldLabel(extJSFieldVO.getFieldLabel());
				extJSComboBoxVO.setId(extJSFieldVO.getId());
				extJSComboBoxVO.setName(extJSFieldVO.getName());
				extJSComboBoxVO.setValue(extJSFieldVO.getValue());
				extJSComboBoxVO.setMinLength(extJSFieldVO.getMinLength());
				extJSComboBoxVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{codigo}.{descripcion}\" class=\"x-combo-list-item\">{descripcion}</div></tpl>");
				
				extJSComboBoxVO.setXtype("LIST");
				extJSComboBoxVO.setEmpyText("Seleccione ...");
				extJSComboBoxVO.setDisplayField("descripcion");
				extJSComboBoxVO.setForceSelection("true");
				extJSComboBoxVO.setHiddenName("dsHidden");
				extJSComboBoxVO.setLabelSeparator("");
				extJSComboBoxVO.setMode("local");
				extJSComboBoxVO.setSelectOnFocus("true");
				extJSComboBoxVO.setSelectOnFocus("true");
				extJSComboBoxVO.setTriggerAction("all");
				extJSComboBoxVO.setTypeAhead("true");
				extJSComboBoxVO.setValueField("codigo");
				extJSComboBoxVO.setStore("crearStoreDatosAdicionales2('" + extJSFieldVO.getOtTabVal() + "', '" + extJSFieldVO.getName() + "', '" + extJSFieldVO.getValue() + "')");
				extJSComboBoxVO.setWidth("120");
				extJSComboBoxVO.setLazyRender("true");
				modelControl.add(extJSComboBoxVO);
			}
			if (extJSFieldVO.getXtype().equals("datefield")) {
				ExtJSDateFieldVO extJSDateFieldVO = new ExtJSDateFieldVO();
				extJSDateFieldVO.setAllowBlank(extJSFieldVO.getAllowBlank());//(extJSFieldVO.getAllowBlank())?"S":"N");
				extJSDateFieldVO.setCdAtribu(extJSFieldVO.getCdAtribu());
				extJSDateFieldVO.setFieldLabel(extJSFieldVO.getFieldLabel());
				extJSDateFieldVO.setId(extJSFieldVO.getId());
				extJSDateFieldVO.setName(extJSFieldVO.getName());
				extJSDateFieldVO.setValue(extJSFieldVO.getValue());
				extJSDateFieldVO.setAnchor("90%");
				extJSDateFieldVO.setMinLength(extJSFieldVO.getMinLength());
				extJSDateFieldVO.setXtype("FECHA");
				extJSDateFieldVO.setWidth("100");
				extJSDateFieldVO.setFormat("d/m/Y");
				modelControl.add(extJSDateFieldVO);
			}
		}
		session.put("modelControl", modelControl);		
	}
	
	/**
	 * Ejecuta la busqueda para el llenado del grid Detalle de la pantalla Recibos 
	 * de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String obtenerRecibos()throws Exception{
		try{
			PagedList pagedList = consultaPolizaRenovadaManager.obtieneRecibos(cdUniEco, cdRamo, nmPoliza, start, limit);
			mListRecibos = pagedList.getItemsRangeList();
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
	
	public String getCdUniEco() {
		return cdUniEco;
	}

	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNmPoliza() {
		return nmPoliza;
	}

	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}

	public void setConsultaPolizaRenovadaManager(
			ConsultaPolizaRenovadaManager consultaPolizaRenovadaManager) {
		this.consultaPolizaRenovadaManager = consultaPolizaRenovadaManager;
	}
	
	public String getNmSituac() {
		return nmSituac;
	}

	public void setNmSituac(String nmSituac) {
		this.nmSituac = nmSituac;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public List<ObjetoAsegurableVO> getMListObjetosAsegurables() {
		return mListObjetosAsegurables;
	}

	public void setMListObjetosAsegurables(
			List<ObjetoAsegurableVO> listObjetosAsegurables) {
		mListObjetosAsegurables = listObjetosAsegurables;
	}

	public List<ItemVO> getMListFuncionesEnPoliza() {
		return mListFuncionesEnPoliza;
	}

	public void setMListFuncionesEnPoliza(List<ItemVO> listFuncionesEnPoliza) {
		mListFuncionesEnPoliza = listFuncionesEnPoliza;
	}

	public List<CoberturaPolAntVO> getMListCoberturaPolizaAnterior() {
		return mListCoberturaPolizaAnterior;
	}

	public void setMListCoberturaPolizaAnterior(
			List<CoberturaPolAntVO> listCoberturaPolizaAnterior) {
		mListCoberturaPolizaAnterior = listCoberturaPolizaAnterior;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		
	}

	public String getCdRol() {
		return cdRol;
	}

	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}

	public String getCdGarant() {
		return cdGarant;
	}

	public void setCdGarant(String cdGarant) {
		this.cdGarant = cdGarant;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNmObjeto() {
		return nmObjeto;
	}

	public void setNmObjeto(String nmObjeto) {
		this.nmObjeto = nmObjeto;
	}

	public List<MPoliObjVO> getMListAccesoriosEquipoEspecial() {
		return mListAccesoriosEquipoEspecial;
	}

	public void setMListAccesoriosEquipoEspecial(
			List<MPoliObjVO> listAccesoriosEquipoEspecial) {
		mListAccesoriosEquipoEspecial = listAccesoriosEquipoEspecial;
	}

	public List<TValoObjVO> getMListDetalleEquipoEspecial() {
		return mListDetalleEquipoEspecial;
	}

	public void setMListDetalleEquipoEspecial(
			List<TValoObjVO> listDetalleEquipoEspecial) {
		mListDetalleEquipoEspecial = listDetalleEquipoEspecial;
	}

	public String getCdTipObj() {
		return cdTipObj;
	}

	public void setCdTipObj(String cdTipObj) {
		this.cdTipObj = cdTipObj;
	}

	public List<ReciboPolizaVO> getMListRecibos() {
		return mListRecibos;
	}

	public void setMListRecibos(List<ReciboPolizaVO> listRecibos) {
		mListRecibos = listRecibos;
	}
	
	public String getCdAtribu() {
		return cdAtribu;
	}

	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}

	@SuppressWarnings("unchecked")
	public List getModelControl() {

		return modelControl;
	}

	@SuppressWarnings("unchecked")
	public void setModelControl(List modelControl) {
		this.modelControl = modelControl;
	}

	@SuppressWarnings("unchecked")
	public Map getSession() {
		return session;
	}

}
