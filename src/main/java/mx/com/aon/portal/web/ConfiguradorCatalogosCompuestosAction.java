package mx.com.aon.portal.web;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.CampoCatalogoVO;
import mx.com.aon.portal.model.ColumnVO;
import mx.com.aon.portal.model.DynamicBeanVO;
import mx.com.aon.portal.model.ExtJSComboBoxVO;
import mx.com.aon.portal.model.ExtJSDateFieldVO;
import mx.com.aon.portal.model.ExtJSFieldVO;
import mx.com.aon.portal.model.GridColumnModelVO;
import mx.com.aon.portal.model.JSONRecordReaderVO;
import mx.com.aon.portal.model.ValoresCamposCatalogosCompuestosVO;
import mx.com.aon.portal.model.ValoresCamposCatalogosSimplesVO;
import mx.com.aon.portal.service.ConfiguradorCatalogosCompuestosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.Util;
import mx.com.aon.portal.web.tooltip.ToolTipBean;

//import mx.com.aon.portal.web.tests.ConverterTest;

import com.opensymphony.xwork2.ActionSupport;

public class ConfiguradorCatalogosCompuestosAction extends AbstractListAction implements SessionAware{
	private transient ConfiguradorCatalogosCompuestosManager configuradorCatalogosCompuestosManager;

	private static Logger logger = Logger.getLogger(ConfiguradorCatalogosCompuestosAction.class);

	private String cdPantalla;
	

	private Map session;

	private List<CampoCatalogoVO> listaCampos;
	
	private List<GridColumnModelVO> columnasGrilla;
	
	private List<ValoresCamposCatalogosCompuestosVO> valoresPantalla;

	private BasicDynaClass dynaClass;
	private DynaProperty[] props = null;
	private DynaBean dynaBean;
	private List listaBeans;

	private String dsTitulo;
	private String valor1;
	private String valor2;
	private String valor3;
	private String valor4;
	private String valor5;
	private String valor6;
	private String valor7;
	private String vlLlave1;
	private String vlLlave2;
	private String vlLlave3;
	private String vlLlave4;
	private String valor8;
	private String valor9;

	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
    /**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;

	public String cmdObtenerTitulo () throws ApplicationException {
		try {
			dsTitulo = configuradorCatalogosCompuestosManager.obtenerTituloPantalla(cdPantalla);
			session.put("dsTitulo", dsTitulo);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			return SUCCESS;
		}
	}

	public String obtenerValorClave (List lista, String nroClave) {
		for (int i=0; i<lista.size(); i++) {
			CampoCatalogoVO campoCatalogoVO = (CampoCatalogoVO)lista.get(i);
			if (campoCatalogoVO.getNomColumn().equals("C" + nroClave)) {
				return campoCatalogoVO.getCdColumn();
			}
		}
		return null;
	}
	
	public String obtenerCombosDependientes (List lista, String nroCombo) {
		for (int i=0; i<lista.size(); i++) {
			CampoCatalogoVO campoCatalogoVO = (CampoCatalogoVO)lista.get(i);
			
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public void cmdObtenerCamposBusquedaCatalogoCompuesto () throws ApplicationException {
		try {
			logger.debug("Obteniendo campos catalogo simple: " + cdPantalla);
			PagedList pagedList = configuradorCatalogosCompuestosManager.obtenerCamposBusqueda (cdPantalla);
			listaCampos = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
						
			List lista = pagedList.getItemsRangeList();
			List camposForm = new ArrayList();
			List camposFormAdd = new ArrayList();
			List camposFormEdit = new ArrayList();
			List gridColumnModel = new ArrayList();
			List recordsReader = new ArrayList();
			List dynaProps = new ArrayList();
			String apareceEditar  = "";
			String apareceAgregar = "";
			String apareceBorrar  = "";
			boolean valorHiddenBtnEditar  = false;
			boolean valorHiddenBtnAgregar = false;
			boolean valorHiddenBtnBorrar  = false;
			
			for (int i=0; i<lista.size(); i++) {
				CampoCatalogoVO campoCatalogoVO = (CampoCatalogoVO)lista.get(i);
				
				apareceEditar  = campoCatalogoVO.getSwEdicion()!= null ? campoCatalogoVO.getSwEdicion() 
																	  : "S";
				apareceAgregar = campoCatalogoVO.getSwInserta() != null ? campoCatalogoVO.getSwInserta(): "S";						
				apareceBorrar  = campoCatalogoVO.getSwBorra()  != null ? campoCatalogoVO.getSwBorra() : "S";		
				
				if (campoCatalogoVO.getDsLista() == null || campoCatalogoVO.getDsLista().equals("")) {
					ExtJSFieldVO extJSFieldVO = new ExtJSFieldVO();
					
					if (campoCatalogoVO.getSwFormat().equals("N") || campoCatalogoVO.getSwFormat().equals("A")) {
						extJSFieldVO.setXtype(((campoCatalogoVO.getSwFormat().equals("N"))?"NUMBER":"TEXT"));
						
						//extJSFieldVO.setAllowBlank(((campoCatalogoVO.getSwMandat().equals("S"))?"N":"S"));
						extJSFieldVO.setAnchor("80%");
						
						extJSFieldVO.setName(campoCatalogoVO.getCdColumn());
						extJSFieldVO.setFieldLabel(campoCatalogoVO.getDsColumn());
						extJSFieldVO.setMinLength(campoCatalogoVO.getNmMinLng());//Integer.parseInt(campoCatalogoVO.getNmMinLng()));
						extJSFieldVO.setMaxLength(campoCatalogoVO.getNmMaxLng());//Integer.parseInt(campoCatalogoVO.getNmMaxLng()));
						extJSFieldVO.setSwLlave(campoCatalogoVO.getSwLlave());
						extJSFieldVO.setAllowBlank((campoCatalogoVO.getSwMandat().equals("") || campoCatalogoVO.getSwMandat().equals("N"))?"true":"false");
						extJSFieldVO.setVisibleEnEdicion(campoCatalogoVO.getSwVisible());
						/*if (campoCatalogoVO.getNomColumn() != null && !campoCatalogoVO.getNomColumn().equals("") && campoCatalogoVO.getNomColumn().length() > 1) {
							extJSFieldVO.setCdAtribu(campoCatalogoVO.getNomColumn().substring(1, campoCatalogoVO.getNomColumn().length()));
						}*/
						extJSFieldVO.setCdAtribu(campoCatalogoVO.getNmOrden());
						extJSFieldVO.setHidden(((campoCatalogoVO.getSwFiltro().equals("N"))?"true":"false"));
						extJSFieldVO.setTooltip(campoCatalogoVO.getTooltip());
						extJSFieldVO.setAyuda(campoCatalogoVO.getAyuda());
						if (campoCatalogoVO.getNomColumn().startsWith("E")) {
							extJSFieldVO.setFgObligatorio("N");
							extJSFieldVO.setReadOnly("true");
							extJSFieldVO.setVisibleEnEdicion("N");
						} else {
							extJSFieldVO.setFgObligatorio("S");
							extJSFieldVO.setReadOnly("false");
						}
						camposFormAdd.add(extJSFieldVO);
						camposFormEdit.add(extJSFieldVO);
						/*if(campoCatalogoVO.getSwFiltro().equals("N")){
							extJSFieldVO.setXtype("HIDDEN");
						}*/
						camposForm.add(extJSFieldVO);
					}
					if (campoCatalogoVO.getSwFormat().equals("D") || campoCatalogoVO.getSwFormat().equals("F")) {
						ExtJSDateFieldVO extJSDateFieldVO = new ExtJSDateFieldVO();
						extJSDateFieldVO.setXtype("FECHA");
						extJSDateFieldVO.setAnchor("70%");
						
						extJSDateFieldVO.setName(campoCatalogoVO.getCdColumn());
						extJSDateFieldVO.setFieldLabel(campoCatalogoVO.getDsColumn());
						extJSDateFieldVO.setMinLength(campoCatalogoVO.getNmMinLng());//Integer.parseInt(campoCatalogoVO.getNmMinLng()));
						extJSDateFieldVO.setMaxLength(campoCatalogoVO.getNmMaxLng());//Integer.parseInt(campoCatalogoVO.getNmMaxLng()));
						extJSDateFieldVO.setSwLlave(campoCatalogoVO.getSwLlave());
						extJSDateFieldVO.setAllowBlank((campoCatalogoVO.getSwMandat().equals("") || campoCatalogoVO.getSwMandat().equals("N"))?"true":"false");
						extJSDateFieldVO.setVisibleEnEdicion(campoCatalogoVO.getSwVisible());
						extJSDateFieldVO.setFormat("d/m/Y");
						extJSDateFieldVO.setTooltip(campoCatalogoVO.getTooltip());
						extJSDateFieldVO.setAyuda(campoCatalogoVO.getAyuda());
						/*if (campoCatalogoVO.getNomColumn() != null && !campoCatalogoVO.getNomColumn().equals("") && campoCatalogoVO.getNomColumn().length() > 1) {
							extJSFieldVO.setCdAtribu(campoCatalogoVO.getNomColumn().substring(1, campoCatalogoVO.getNomColumn().length()));
						}*/
						extJSDateFieldVO.setCdAtribu(campoCatalogoVO.getNmOrden());
						extJSDateFieldVO.setHidden(((campoCatalogoVO.getSwFiltro().equals("N"))?"true":"false"));
						if (campoCatalogoVO.getNomColumn().startsWith("E")) {
							extJSDateFieldVO.setFgObligatorio("N");
							extJSDateFieldVO.setReadOnly("true");
							extJSDateFieldVO.setVisibleEnEdicion("N");
						} else {
							extJSDateFieldVO.setFgObligatorio("S");
							extJSDateFieldVO.setReadOnly("false");
						}
						camposFormAdd.add(extJSDateFieldVO);

						/*if(campoCatalogoVO.getSwFiltro().equals("N")){
							extJSDateFieldVO.setXtype("HIDDEN");
						}*/
						camposForm.add(extJSDateFieldVO);
						camposFormEdit.add(extJSDateFieldVO);
					}
				} else {
						ExtJSFieldVO extJSFieldVO1 = new ExtJSFieldVO();
						if (campoCatalogoVO.getSwFormat().equals("N")) {
							extJSFieldVO1.setXtype("NUMBER");
						}
						if (campoCatalogoVO.getSwFormat().equals("A")) {
							extJSFieldVO1.setXtype("TEXT");
						}
						if (campoCatalogoVO.getSwFormat().equals("D") || campoCatalogoVO.getSwFormat().equals("F")) {
							extJSFieldVO1.setXtype("FECHA");
						}
						//extJSFieldVO1.setXtype(((campoCatalogoVO.getSwFormat().equals("N"))?"NUMBER":"TEXT"));
						
						//extJSFieldVO.setAllowBlank(((campoCatalogoVO.getSwMandat().equals("S"))?"N":"S"));
						extJSFieldVO1.setAnchor("80%");
						
						extJSFieldVO1.setName(campoCatalogoVO.getCdColumn());
						extJSFieldVO1.setFieldLabel(campoCatalogoVO.getDsColumn());
						extJSFieldVO1.setMinLength(campoCatalogoVO.getNmMinLng());//Integer.parseInt(campoCatalogoVO.getNmMinLng()));
						extJSFieldVO1.setMaxLength(campoCatalogoVO.getNmMaxLng());//Integer.parseInt(campoCatalogoVO.getNmMaxLng()));
						extJSFieldVO1.setSwLlave(campoCatalogoVO.getSwLlave());
						extJSFieldVO1.setAllowBlank((campoCatalogoVO.getSwMandat().equals("") || campoCatalogoVO.getSwMandat().equals("N"))?"true":"false");
						extJSFieldVO1.setVisibleEnEdicion(campoCatalogoVO.getSwVisible());
						/*if (campoCatalogoVO.getNomColumn() != null && !campoCatalogoVO.getNomColumn().equals("") && campoCatalogoVO.getNomColumn().length() > 1) {
							extJSFieldVO.setCdAtribu(campoCatalogoVO.getNomColumn().substring(1, campoCatalogoVO.getNomColumn().length()));
						}*/
						extJSFieldVO1.setCdAtribu(campoCatalogoVO.getNmOrden());
						extJSFieldVO1.setHidden(((campoCatalogoVO.getSwFiltro().equals("N"))?"true":"false"));
						extJSFieldVO1.setTooltip(campoCatalogoVO.getTooltip());
						extJSFieldVO1.setAyuda(campoCatalogoVO.getAyuda());
						camposForm.add(extJSFieldVO1);

						ExtJSComboBoxVO extJSComboBoxVO = new ExtJSComboBoxVO();
						extJSComboBoxVO.setXtype("TEXT");
						//extJSFieldVO.setAllowBlank(((campoCatalogoVO.getSwMandat().equals("S"))?"N":"S"));
						extJSComboBoxVO.setAnchor("70%");
						extJSComboBoxVO.setName(campoCatalogoVO.getCdColumn());
						extJSComboBoxVO.setFieldLabel(campoCatalogoVO.getDsColumn());
						extJSComboBoxVO.setMinLength(campoCatalogoVO.getNmMinLng());//Integer.parseInt(campoCatalogoVO.getNmMinLng()));
						extJSComboBoxVO.setMaxLength(campoCatalogoVO.getNmMaxLng());//Integer.parseInt(campoCatalogoVO.getNmMaxLng()));
						extJSComboBoxVO.setSwLlave(campoCatalogoVO.getSwLlave());
						extJSComboBoxVO.setAllowBlank((campoCatalogoVO.getSwMandat().equals("") || campoCatalogoVO.getSwMandat().equals("N"))?"true":"false");
						extJSComboBoxVO.setVisibleEnEdicion(campoCatalogoVO.getSwVisible());
						/*if (campoCatalogoVO.getNomColumn() != null && !campoCatalogoVO.getNomColumn().equals("") && campoCatalogoVO.getNomColumn().length() > 1) {
							extJSFieldVO.setCdAtribu(campoCatalogoVO.getNomColumn().substring(1, campoCatalogoVO.getNomColumn().length()));
						}*/
						extJSComboBoxVO.setCdAtribu(campoCatalogoVO.getNmOrden());
						extJSComboBoxVO.setHidden(((campoCatalogoVO.getSwFiltro().equals("N"))?"true":"false"));
						if (campoCatalogoVO.getNomColumn().startsWith("E")) {
							extJSComboBoxVO.setFgObligatorio("N");
							extJSComboBoxVO.setReadOnly("true");
							extJSComboBoxVO.setVisibleEnEdicion("N");
						} else {
							extJSComboBoxVO.setFgObligatorio("S");
							extJSComboBoxVO.setReadOnly("false");
						}
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
						extJSComboBoxVO.setStore("crearStoreGenerico('" + campoCatalogoVO.getCdColumn() + "', '" + extJSComboBoxVO.getName() + "', '" + extJSComboBoxVO.getValue() + "')");
						extJSComboBoxVO.setWidth("120");
						extJSComboBoxVO.setLazyRender("true");
						extJSComboBoxVO.setMaxLength("999999");
						extJSComboBoxVO.setMinLength("1");
						extJSComboBoxVO.setCdColumna(campoCatalogoVO.getCdColumn());
						extJSComboBoxVO.setNroOrdenClave(campoCatalogoVO.getNroOrdenClave().substring(1));
						extJSComboBoxVO.setTooltip(campoCatalogoVO.getTooltip());
						extJSComboBoxVO.setAyuda(campoCatalogoVO.getAyuda());
						
						/*if(campoCatalogoVO.getSwFiltro().equals("N")){
							extJSComboBoxVO.setXtype("HIDDEN");
						}*/
						extJSComboBoxVO.setId("EditarCombo_" + campoCatalogoVO.getNroOrdenClave().substring(1));
						camposFormEdit.add(extJSComboBoxVO);

						if (campoCatalogoVO.getSwAgrega().equals("N")) {
							ExtJSFieldVO extJSFieldVO = new ExtJSFieldVO();
							if (campoCatalogoVO.getSwFormat().equals("N")) {
								extJSFieldVO.setXtype("NUMBER");
							} 
							if (campoCatalogoVO.getSwFormat().equals("A")){
								extJSFieldVO.setXtype("TEXT");
							}
							if (campoCatalogoVO.getSwFormat().equals("D") || campoCatalogoVO.getSwFormat().equals("F")) {
								extJSFieldVO.setXtype("FECHA");
							}
							//extJSFieldVO.setAllowBlank(((campoCatalogoVO.getSwMandat().equals("S"))?"N":"S"));
							extJSFieldVO.setAnchor("70%");
							extJSFieldVO.setName(campoCatalogoVO.getCdColumn());
							extJSFieldVO.setFieldLabel(campoCatalogoVO.getDsColumn());
							extJSFieldVO.setMinLength(campoCatalogoVO.getNmMinLng());//Integer.parseInt(campoCatalogoVO.getNmMinLng()));
							extJSFieldVO.setMaxLength(campoCatalogoVO.getNmMaxLng());//Integer.parseInt(campoCatalogoVO.getNmMaxLng()));
							extJSFieldVO.setSwLlave(campoCatalogoVO.getSwLlave());
							extJSFieldVO.setAllowBlank((campoCatalogoVO.getSwMandat().equals("") || campoCatalogoVO.getSwMandat().equals("N"))?"true":"false");
							extJSFieldVO.setVisibleEnEdicion(campoCatalogoVO.getSwVisible());
							extJSFieldVO.setTooltip(campoCatalogoVO.getTooltip());
							extJSFieldVO.setAyuda(campoCatalogoVO.getAyuda());
							/*if (campoCatalogoVO.getNomColumn() != null && !campoCatalogoVO.getNomColumn().equals("") && campoCatalogoVO.getNomColumn().length() > 1) {
								extJSFieldVO.setCdAtribu(campoCatalogoVO.getNomColumn().substring(1, campoCatalogoVO.getNomColumn().length()));
							}*/
							extJSFieldVO.setCdAtribu(campoCatalogoVO.getNmOrden());
							extJSFieldVO.setHidden(((campoCatalogoVO.getSwFiltro().equals("N"))?"true":"false"));
							if (campoCatalogoVO.getNomColumn().startsWith("E")) {
								extJSFieldVO.setFgObligatorio("N");
								extJSFieldVO.setReadOnly("true");
								extJSFieldVO.setVisibleEnEdicion("N");
							} else {
								extJSFieldVO.setFgObligatorio("S");
								extJSFieldVO.setReadOnly("false");
							}
							camposFormAdd.add(extJSFieldVO);
						} else {
							extJSComboBoxVO.setId("AgregarCombo_" + campoCatalogoVO.getNroOrdenClave().substring(1));
							StringBuffer sbOnSelect = new StringBuffer();
							sbOnSelect.append("").append("this.setValue(record.get('codigo'));").append("this.collapse();").append("reloadCombo(").append(campoCatalogoVO.getNroOrdenClave().substring(1)).append(");");
							String[] params = {"record"}; 
							extJSComboBoxVO.setOnSelect(new JSONFunction(params, sbOnSelect.toString()));
							camposFormAdd.add(extJSComboBoxVO);
						}
				}
				
				GridColumnModelVO gridColumnModelVO = new GridColumnModelVO();
				gridColumnModelVO.setDataIndex(campoCatalogoVO.getCdColumn());
				gridColumnModelVO.setHeader(campoCatalogoVO.getDsColumn());
				gridColumnModelVO.setHidden(campoCatalogoVO.getSwVisible());
				gridColumnModelVO.setSortable(true);
				gridColumnModelVO.setTooltip("");
				gridColumnModelVO.setNomColumn(campoCatalogoVO.getNroOrdenClave()); //Se guarda para recuperar la clave desde la grilla para editar o borrar

				gridColumnModel.add(gridColumnModelVO);
				
				JSONRecordReaderVO recordReaderVO = new JSONRecordReaderVO();
				recordReaderVO.setName(campoCatalogoVO.getCdColumn());
				recordReaderVO.setMapping(campoCatalogoVO.getCdColumn());
				recordReaderVO.setSwLlave(campoCatalogoVO.getSwLlave());
				recordReaderVO.setType("string");
				recordsReader.add(recordReaderVO);
				//props[0] = new DynaProperty(campoCatalogoVO.getCdCatalogo(), String.class);
				DynaProperty dynaProp = new DynaProperty(campoCatalogoVO.getCdColumn(), String.class);
				dynaProps.add(dynaProp);
				
			}
			props = (DynaProperty[])dynaProps.toArray(new DynaProperty[camposForm.size()]);
			dynaClass = new  BasicDynaClass("rows", null, props);
			
			if("S".equalsIgnoreCase(apareceEditar)){
				valorHiddenBtnEditar = false;
				if(logger.isDebugEnabled()){
					logger.debug(">>>>>>> apareceEditar ::: " +apareceEditar);
					logger.debug(">>>>>>> valorHiddenBtnEditar ::: " +valorHiddenBtnEditar);
				}
				
			}else{
				valorHiddenBtnEditar = true;
				if(logger.isDebugEnabled()){
					logger.debug(">>>>>>> apareceEditar ::: " +apareceEditar);
					logger.debug(">>>>>>> valorHiddenBtnEditar ::: " +valorHiddenBtnEditar);
				}
			}
			
			
			if("S".equalsIgnoreCase(apareceAgregar)){
				valorHiddenBtnAgregar = false;
				if(logger.isDebugEnabled()){
					logger.debug(">>>>>>> apareceAgregar ::: " +apareceAgregar);
					logger.debug(">>>>>>> valorHiddenBtnAgregar ::: " +valorHiddenBtnAgregar);
				}
			}else{
				valorHiddenBtnAgregar = true;
				if(logger.isDebugEnabled()){
					logger.debug(">>>>>>> apareceAgregar ::: " +apareceAgregar);
					logger.debug(">>>>>>> valorHiddenBtnAgregar ::: " +valorHiddenBtnAgregar);
				}
			}
			
			if("S".equalsIgnoreCase(apareceBorrar)){
				valorHiddenBtnBorrar = false;
				if(logger.isDebugEnabled()){
					logger.debug(">>>>>>> apareceBorrar ::: " +apareceBorrar );
					logger.debug(">>>>>>> valorHiddenBtnBorrar ::: " +valorHiddenBtnBorrar);
				}
				
			}else{
				valorHiddenBtnBorrar = true;
				if(logger.isDebugEnabled()){
					logger.debug(">>>>>>> apareceBorrar ::: " +apareceBorrar );
					logger.debug(">>>>>>> valorHiddenBtnBorrar ::: " +valorHiddenBtnBorrar);
				}
			}
			
			session.put("camposForm", camposForm);
			session.put("camposFormAdd", camposFormAdd);
			session.put("camposFormEdit", camposFormEdit);
			session.put("gridColumnModel", gridColumnModel);
			session.put("recordsReader", recordsReader);
			session.put("dynaClass", JSONObject.fromObject(dynaClass).toString());
			session.put("valorHiddenBtnEditar", valorHiddenBtnEditar);
			session.put("valorHiddenBtnAgregar", valorHiddenBtnAgregar);
			session.put("valorHiddenBtnBorrar", valorHiddenBtnBorrar);
			
			success = true;
			//return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
		} catch (Exception e) {
			success = false;
		}
	}

	public String cmdBuscarDatosGrilla () throws Exception {
		try {
			PagedList pagedList = configuradorCatalogosCompuestosManager.obtenerCamposBusqueda (cdPantalla);
			List lista = pagedList.getItemsRangeList();
			listaBeans = new ArrayList();
			List dynaProps = new ArrayList();
			for (int i=0; i<lista.size(); i++) {
				CampoCatalogoVO campoCatalogoVO = (CampoCatalogoVO)lista.get(i);
				DynaProperty dynaProp = new DynaProperty(campoCatalogoVO.getCdColumn(), String.class);
				dynaProps.add(dynaProp);
			}
			props = (DynaProperty[])dynaProps.toArray(new DynaProperty[lista.size()]);
			dynaClass = new  BasicDynaClass("rows", null, props);
			
			ValoresCamposCatalogosCompuestosVO camposCatalogosCompuestosVO = new ValoresCamposCatalogosCompuestosVO();
			camposCatalogosCompuestosVO.setValor1(valor1);
			camposCatalogosCompuestosVO.setValor2(valor2);
			camposCatalogosCompuestosVO.setValor3(valor3);
			camposCatalogosCompuestosVO.setValor4(valor4);
			camposCatalogosCompuestosVO.setValor5(valor5);
			camposCatalogosCompuestosVO.setValor6(valor6);
			camposCatalogosCompuestosVO.setValor7(valor7);
			camposCatalogosCompuestosVO.setValor8(valor8);
			camposCatalogosCompuestosVO.setValor9(valor9);
			
	
			valoresPantalla = new ArrayList<ValoresCamposCatalogosCompuestosVO>();
			valoresPantalla.add(camposCatalogosCompuestosVO);
			int _start = start;
			int _limit = limit;
			pagedList = configuradorCatalogosCompuestosManager.obtenerColumnasGrilla(cdPantalla, valoresPantalla, _start, _limit);
			totalCount = pagedList.getTotalItems();
			List listaDynamicBeans = pagedList.getItemsRangeList();
			for (int i=0; i<listaDynamicBeans.size(); i++) {
				DynamicBeanVO dynamicBeanVO = (DynamicBeanVO)listaDynamicBeans.get(i);
				List<ColumnVO> listaColumnas = dynamicBeanVO.getListaColumnas();
				dynaBean = dynaClass.newInstance();
				for (int j=0; j<listaColumnas.size(); j++) {
					ColumnVO columnVO = listaColumnas.get(j);
					for (int k=0; k<lista.size(); k++) {
						CampoCatalogoVO campoCatalogoVO = (CampoCatalogoVO)lista.get(k);
						if (columnVO.getName().equals(campoCatalogoVO.getCdColumn())) {
							/*if (campoCatalogoVO.getSwFormat().equals("D") || campoCatalogoVO.getSwFormat().equals("F")) {
								Long long1 = new Long(columnVO.getValue());
								dynaBean.set(columnVO.getName(), ConvertUtil.convertToString(long1));
							} else {*/
								dynaBean.set(columnVO.getName(), columnVO.getValue());
							//}
						}
					}
					//dynaBean.set(columnVO.getName(), columnVO.getValue());
				}
				JSONObject jsonObject = JSONObject.fromObject(dynaBean);
				listaBeans.add(jsonObject);
			}
			/*for (int i=0; i<11; i++) {
				dynaBean = dynaClass.newInstance();
				dynaBean.set("CDPERPAG", "Valor");
				dynaBean.set("DSPERPAG", "Valor" + i);
				JSONObject jsonObject = JSONObject.fromObject(dynaBean);
				listaBeans.add(jsonObject);
			}*/
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}

	public String cmdConfigurarCatalogoCompuesto () throws ApplicationException{
		session.put("dsTitulo", null);
		session.remove("dsTitulo");
		session.put("camposForm", null);
		session.remove("camposForm");
		session.put("gridColumnModel", null);
		session.remove("gridColumnModel");
		session.put("recordsReader", null);
		session.remove("recordsReader");
		session.put("camposFormEdit", null);
		session.remove("camposFormEdit");

		session.put("modelControl", null);
		session.remove("modelControl");
		cmdObtenerTitulo();
		cmdObtenerCamposBusquedaCatalogoCompuesto();
		success = true;
		return "configurarCatalogo";
	}

	public String cmdGuardarDatos () throws ApplicationException {
		try {
			PagedList pagedList = configuradorCatalogosCompuestosManager.obtenerCamposBusqueda (cdPantalla);
			List lista = pagedList.getItemsRangeList();

			if (valoresPantalla.get(0).getValor1() != null) {
				if (((CampoCatalogoVO)lista.get(0)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(0)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor1());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor1(long1.toString());*/
					if (valoresPantalla.get(0).getValor1() != null && !valoresPantalla.get(0).getValor1().equals("")) {
						valoresPantalla.get(0).setValor1(valoresPantalla.get(0).getValor1().replace("/", ""));
					}
				}
			}

			if (valoresPantalla.get(0).getValor2() != null) {
				if (((CampoCatalogoVO)lista.get(1)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(1)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor2());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor2(long1.toString());*/
					if (valoresPantalla.get(0).getValor2() != null && !valoresPantalla.get(0).getValor2().equals("")) {
						valoresPantalla.get(0).setValor2(valoresPantalla.get(0).getValor2().replace("/", ""));
					}
				}
			}

			if (valoresPantalla.get(0).getValor3() != null) {
				if (((CampoCatalogoVO)lista.get(2)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(2)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor3());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor3(long1.toString());*/
					if (valoresPantalla.get(0).getValor3() != null && !valoresPantalla.get(0).getValor3().equals("")) {
						valoresPantalla.get(0).setValor3(valoresPantalla.get(0).getValor3().replace("/", ""));
					}
				}
			}

			if (valoresPantalla.get(0).getValor4() != null) {
				if (((CampoCatalogoVO)lista.get(3)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(3)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor4());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor4(long1.toString());*/
					if (valoresPantalla.get(0).getValor4() != null && !valoresPantalla.get(0).getValor4().equals("")) {
						valoresPantalla.get(0).setValor4(valoresPantalla.get(0).getValor4().replace("/", ""));
					}
				}
			}

			if (valoresPantalla.get(0).getValor5() != null) {
				if (((CampoCatalogoVO)lista.get(4)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(4)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor5());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor5(long1.toString());*/
					if (valoresPantalla.get(0).getValor5() != null && !valoresPantalla.get(0).getValor5().equals("")) {
						valoresPantalla.get(0).setValor5(valoresPantalla.get(0).getValor5().replace("/", ""));
					}
				}
			}

			if (valoresPantalla.get(0).getValor6() != null) {
				if (((CampoCatalogoVO)lista.get(5)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(5)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor6());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor6(long1.toString());*/
					if (valoresPantalla.get(0).getValor6() != null && !valoresPantalla.get(0).getValor6().equals("")) {
						valoresPantalla.get(0).setValor6(valoresPantalla.get(0).getValor6().replace("/", ""));
					}
				}
			}

			if (valoresPantalla.get(0).getValor7() != null) {
				if (((CampoCatalogoVO)lista.get(6)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(6)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor6());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor6(long1.toString());*/
					if (valoresPantalla.get(0).getValor7() != null && !valoresPantalla.get(0).getValor7().equals("")) {
						valoresPantalla.get(0).setValor7(valoresPantalla.get(0).getValor7().replace("/", ""));
					}
				}
			}

			String msg = configuradorCatalogosCompuestosManager.guardarRegistro(cdPantalla, valoresPantalla);
			
			if(cdPantalla.equals("GB_MESSAGES")){
				ToolTipBean bean = ((ToolTipBean) ServletActionContext.getServletContext().getAttribute("toolTipName"));
				if (bean != null) {
	        		logger.debug("Removiendo Message");
	        		bean.removeKeyMessag(valoresPantalla.get(0).getValor4(),valoresPantalla.get(0).getValor2());
	        	}
				
			}
			
			addActionMessage(msg);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}

	public String cmdActualizarDatos () throws ApplicationException {
		try {
			PagedList pagedList = configuradorCatalogosCompuestosManager.obtenerCamposBusqueda (cdPantalla);
			List lista = pagedList.getItemsRangeList();

			if (valoresPantalla.get(0).getValor1() != null) {
				if (((CampoCatalogoVO)lista.get(0)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(0)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor1());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor1(long1.toString());*/
					if (valoresPantalla.get(0).getValor1() != null && !valoresPantalla.get(0).getValor1().equals("")) {
						valoresPantalla.get(0).setValor1(valoresPantalla.get(0).getValor1().replace("/", ""));
					}
				}
			}

			if (valoresPantalla.get(0).getValor2() != null) {
				if (((CampoCatalogoVO)lista.get(1)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(1)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor2());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor2(long1.toString());*/
					if (valoresPantalla.get(0).getValor2() != null && !valoresPantalla.get(0).getValor2().equals("")) {
						valoresPantalla.get(0).setValor2(valoresPantalla.get(0).getValor2().replace("/", ""));
					}
				}
			}

			if (valoresPantalla.get(0).getValor3() != null) {
				if (((CampoCatalogoVO)lista.get(2)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(2)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor3());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor3(long1.toString());*/
					if (valoresPantalla.get(0).getValor3() != null && !valoresPantalla.get(0).getValor3().equals("")) {
						valoresPantalla.get(0).setValor3(valoresPantalla.get(0).getValor3().replace("/", ""));
					}
				}
			}

			if (valoresPantalla.get(0).getValor4() != null) {
				if (((CampoCatalogoVO)lista.get(3)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(3)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor4());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor4(long1.toString());*/
					if (valoresPantalla.get(0).getValor4() != null && !valoresPantalla.get(0).getValor4().equals("")) {
						valoresPantalla.get(0).setValor4(valoresPantalla.get(0).getValor4().replace("/", ""));
					}
				}
			}

			if (valoresPantalla.get(0).getValor5() != null) {
				if (((CampoCatalogoVO)lista.get(4)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(4)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor5());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor5(long1.toString());*/
					if (valoresPantalla.get(0).getValor5() != null && !valoresPantalla.get(0).getValor5().equals("")) {
						valoresPantalla.get(0).setValor5(valoresPantalla.get(0).getValor5().replace("/", ""));
					}
				}
			}

			if (valoresPantalla.get(0).getValor6() != null) {
				if (((CampoCatalogoVO)lista.get(5)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(5)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor6());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor6(long1.toString());*/
					if (valoresPantalla.get(0).getValor6() != null && !valoresPantalla.get(0).getValor6().equals("")) {
						valoresPantalla.get(0).setValor6(valoresPantalla.get(0).getValor6().replace("/", ""));
					}
				}
			}

			if (valoresPantalla.get(0).getValor7() != null) {
				if (((CampoCatalogoVO)lista.get(6)).getSwFormat().equals("D") || ((CampoCatalogoVO)lista.get(6)).getSwFormat().equals("D")) {
					/*Timestamp timestamp = ConvertUtil.convertToTimeStamp(valoresPantalla.get(0).getValor6());
					Long long1 = timestamp.getTime();
					valoresPantalla.get(0).setValor6(long1.toString());*/
					if (valoresPantalla.get(0).getValor7() != null && !valoresPantalla.get(0).getValor7().equals("")) {
						valoresPantalla.get(0).setValor7(valoresPantalla.get(0).getValor7().replace("/", ""));
					}
				}
			}

			String msg = configuradorCatalogosCompuestosManager.actualizarRegistro(cdPantalla, valoresPantalla);
			
			if(cdPantalla.equals("GB_MESSAGES")){
				ToolTipBean bean = ((ToolTipBean) ServletActionContext.getServletContext().getAttribute("toolTipName"));
				if (bean != null) {
	        		logger.debug("Removiendo Message");
	        		bean.removeKeyMessag(valoresPantalla.get(0).getValor4(),valoresPantalla.get(0).getValor2());
	        	}
				
			}
			
			addActionMessage(msg);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
	public String cmdBorrar () throws ApplicationException {
		try {
			String msg = configuradorCatalogosCompuestosManager.borrarRegistro(cdPantalla, vlLlave1, vlLlave2, vlLlave3, vlLlave4);
			addActionMessage(msg);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}

	public String cmdExportarClick() throws Exception {
		try {
			   contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato);
			filename = "CatalogosB." + exportFormat.getExtension();
			ValoresCamposCatalogosCompuestosVO valoresCamposCatalogosCompuestosVO = new ValoresCamposCatalogosCompuestosVO();
			valoresCamposCatalogosCompuestosVO.setValor1(valor1);
			valoresCamposCatalogosCompuestosVO.setValor2(valor2);
			valoresCamposCatalogosCompuestosVO.setValor3(valor3);
			valoresCamposCatalogosCompuestosVO.setValor4(valor4);
			valoresCamposCatalogosCompuestosVO.setValor5(valor5);
			valoresCamposCatalogosCompuestosVO.setValor6(valor6);
			valoresCamposCatalogosCompuestosVO.setValor7(valor7);
			valoresCamposCatalogosCompuestosVO.setValor8(valor8);
			valoresCamposCatalogosCompuestosVO.setValor9(valor9);
			valoresPantalla = new ArrayList<ValoresCamposCatalogosCompuestosVO>();
			valoresPantalla.add(valoresCamposCatalogosCompuestosVO);
			TableModelExport model = configuradorCatalogosCompuestosManager.getModel(cdPantalla, valoresPantalla);
			inputStream = exportFormat.export(model);
			success = true;
		} catch (Exception e) {
			success = false;
		}
		return SUCCESS;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	/*public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}*/

	public String getCdPantalla() {
		return cdPantalla;
	}

	public void setCdPantalla(String cdPantalla) {
		this.cdPantalla = cdPantalla;
	}

	/*public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}*/

	public List<ValoresCamposCatalogosCompuestosVO> getValoresPantalla() {
		return valoresPantalla;
	}

	public void setValoresPantalla(
			List<ValoresCamposCatalogosCompuestosVO> valoresPantalla) {
		this.valoresPantalla = valoresPantalla;
	}

	public void setConfiguradorCatalogosCompuestosManager(
			ConfiguradorCatalogosCompuestosManager configuradorCatalogosCompuestosManager) {
		this.configuradorCatalogosCompuestosManager = configuradorCatalogosCompuestosManager;
	}

	public void setListaCampos(List<CampoCatalogoVO> listaCampos) {
		this.listaCampos = listaCampos;
	}

	public void setColumnasGrilla(List<GridColumnModelVO> columnasGrilla) {
		this.columnasGrilla = columnasGrilla;
	}

	public void setProps(DynaProperty[] props) {
		this.props = props;
	}

	public void setListaBeans(List listaBeans) {
		this.listaBeans = listaBeans;
	}

	public String getValor1() {
		return valor1;
	}

	public void setValor1(String valor1) {
		this.valor1 = valor1;
	}

	public String getValor2() {
		return valor2;
	}

	public void setValor2(String valor2) {
		this.valor2 = valor2;
	}

	public String getValor3() {
		return valor3;
	}

	public void setValor3(String valor3) {
		this.valor3 = valor3;
	}

	public String getValor4() {
		return valor4;
	}

	public void setValor4(String valor4) {
		this.valor4 = valor4;
	}

	public String getValor5() {
		return valor5;
	}

	public void setValor5(String valor5) {
		this.valor5 = valor5;
	}

	public String getValor6() {
		return valor6;
	}

	public void setValor6(String valor6) {
		this.valor6 = valor6;
	}

	public List getListaBeans() {
		return listaBeans;
	}

	public String getVlLlave1() {
		return vlLlave1;
	}

	public void setVlLlave1(String vlLlave1) {
		this.vlLlave1 = vlLlave1;
	}

	public String getVlLlave2() {
		return vlLlave2;
	}

	public void setVlLlave2(String vlLlave2) {
		this.vlLlave2 = vlLlave2;
	}

	public ExportMediator getExportMediator() {
		return exportMediator;
	}

	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getDsTitulo() {
		return dsTitulo;
	}

	public void setDsTitulo(String dsTitulo) {
		this.dsTitulo = dsTitulo;
	}

	public String getValor7() {
		return valor7;
	}

	public void setValor7(String valor7) {
		this.valor7 = valor7;
	}

	public String getValor8() {
		return valor8;
	}

	public void setValor8(String valor8) {
		this.valor8 = valor8;
	}

	public String getValor9() {
		return valor9;
	}

	public void setValor9(String valor9) {
		this.valor9 = valor9;
	}

	public String getVlLlave3() {
		return vlLlave3;
	}

	public void setVlLlave3(String vlLlave3) {
		this.vlLlave3 = vlLlave3;
	}

	public String getVlLlave4() {
		return vlLlave4;
	}

	public void setVlLlave4(String vlLlave4) {
		this.vlLlave4 = vlLlave4;
	}}
