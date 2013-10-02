package mx.com.aon.portal.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import mx.com.aon.portal.model.ValoresCamposCatalogosSimplesVO;
import mx.com.aon.portal.service.ConfiguradorCatalogosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;
import mx.com.gseguros.exception.ApplicationException;
import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;


public class ConfigurarCatalogosSimplesAction extends AbstractListAction implements
		SessionAware {
	private static Logger logger = Logger.getLogger(ConfigurarCatalogosSimplesAction.class);

	private transient ConfiguradorCatalogosManager configuradorCatalogosManager;

	private Map session;

	private List<CampoCatalogoVO> listaCampos;
	
	private List<GridColumnModelVO> columnasGrilla;
	
	private List<ValoresCamposCatalogosSimplesVO> valoresPantalla;
	

	private String cdPantalla;
	
	//private int totalCount;
	
	//private boolean success;

	private BasicDynaClass dynaClass;
	private DynaProperty[] props = null;
	private DynaBean dynaBean;
	private List listaBeans;
	private String vlLlave;//Contiene el valor de la clave a borrar
	private String valor1;
	private String valor2;
	private String valor3;
	private String valor4;
	private String valor5;
	private String dsTitulo;
	//private int start;
	//private int limit;

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

	
	@SuppressWarnings("unchecked")
	public String cmdObtenerTitulo () throws ApplicationException {
		try {
			dsTitulo = configuradorCatalogosManager.obtenerTituloPantalla(cdPantalla);
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
	@SuppressWarnings("unchecked")
	public void cmdObtenerCamposBusquedaCatalogoSimple () throws ApplicationException {
			logger.debug("Obteniendo campos catalogo simple: " + cdPantalla);
			PagedList pagedList = configuradorCatalogosManager.obtenerCamposBusquedaSimple(cdPantalla);
			listaCampos = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
			List lista = pagedList.getItemsRangeList();
			List camposForm = new ArrayList();
			List camposFormAdd = new ArrayList();
			List camposFormEdit = new ArrayList();
			List gridColumnModel = new ArrayList();
			List recordsReader = new ArrayList();
			List dynaProps = new ArrayList();
			
			
			for (int i=0; i<lista.size(); i++) {
				CampoCatalogoVO campoCatalogoVO = (CampoCatalogoVO)lista.get(i);
				if (campoCatalogoVO.getDsLista() == null || campoCatalogoVO.getDsLista().equals("")) {
					ExtJSFieldVO extJSFieldVO = new ExtJSFieldVO();
					if (campoCatalogoVO.getSwFormat().equals("N") || campoCatalogoVO.getSwFormat().equals("A")) {
						extJSFieldVO.setXtype(((campoCatalogoVO.getSwFormat().equals("N"))?"NUMBER":"TEXT"));
						
						//extJSFieldVO.setAllowBlank(((campoCatalogoVO.getSwMandat().equals("S"))?"N":"S"));
						extJSFieldVO.setAnchor("70%");
						extJSFieldVO.setName(campoCatalogoVO.getCdColumn());
						extJSFieldVO.setFieldLabel(campoCatalogoVO.getDsColumn());
						extJSFieldVO.setMinLength(campoCatalogoVO.getNmMinLng());//Integer.parseInt(campoCatalogoVO.getNmMinLng()));
						extJSFieldVO.setMaxLength(campoCatalogoVO.getNmMaxLng());//Integer.parseInt(campoCatalogoVO.getNmMaxLng()));
						extJSFieldVO.setSwLlave(campoCatalogoVO.getSwLlave());
						extJSFieldVO.setCdAtribu(campoCatalogoVO.getNmOrden());
						extJSFieldVO.setHidden(((campoCatalogoVO.getSwFiltro().equals("N"))?"true":"false"));
						extJSFieldVO.setAllowBlank((campoCatalogoVO.getSwMandat().equals("") || campoCatalogoVO.getSwMandat().equals("N"))?"true":"false");
						extJSFieldVO.setTooltip(campoCatalogoVO.getTooltip());
						extJSFieldVO.setAyuda(campoCatalogoVO.getAyuda());
						/*if (campoCatalogoVO.getNomColumn() != null && !campoCatalogoVO.getNomColumn().equals("") && campoCatalogoVO.getNomColumn().length() > 1) {
							extJSFieldVO.setCdAtribu(campoCatalogoVO.getNomColumn().substring(1, campoCatalogoVO.getNomColumn().length()));
						} else {
							//extJSFieldVO.setCdAtribu("");
						}*/
						if (campoCatalogoVO.getNomColumn().startsWith("E")) {
							extJSFieldVO.setFgObligatorio("N");
							extJSFieldVO.setVisibleEnEdicion("N");
						} else {
							extJSFieldVO.setFgObligatorio("S");
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
						//extJSFieldVO.setAllowBlank(((campoCatalogoVO.getSwMandat().equals("S"))?"N":"S"));
						extJSDateFieldVO.setAnchor("70%");
						extJSDateFieldVO.setName(campoCatalogoVO.getCdColumn());
						extJSDateFieldVO.setFieldLabel(campoCatalogoVO.getDsColumn());
						extJSDateFieldVO.setMinLength(campoCatalogoVO.getNmMinLng());//Integer.parseInt(campoCatalogoVO.getNmMinLng()));
						extJSDateFieldVO.setMaxLength(campoCatalogoVO.getNmMaxLng());//Integer.parseInt(campoCatalogoVO.getNmMaxLng()));
						extJSDateFieldVO.setSwLlave(campoCatalogoVO.getSwLlave());
						extJSDateFieldVO.setCdAtribu(campoCatalogoVO.getNmOrden());
						extJSDateFieldVO.setHidden(((campoCatalogoVO.getSwFiltro().equals("N"))?"true":"false"));
						extJSDateFieldVO.setAllowBlank((campoCatalogoVO.getSwMandat().equals("") || campoCatalogoVO.getSwMandat().equals("N"))?"true":"false");
						extJSDateFieldVO.setFormat("d/m/Y");
						extJSDateFieldVO.setTooltip(campoCatalogoVO.getTooltip());
						extJSDateFieldVO.setAyuda(campoCatalogoVO.getAyuda());
						/*if (campoCatalogoVO.getNomColumn() != null && !campoCatalogoVO.getNomColumn().equals("") && campoCatalogoVO.getNomColumn().length() > 1) {
							extJSFieldVO.setCdAtribu(campoCatalogoVO.getNomColumn().substring(1, campoCatalogoVO.getNomColumn().length()));
						} else {
							//extJSFieldVO.setCdAtribu("");
						}*/
						if (campoCatalogoVO.getNomColumn().startsWith("E")) {
							extJSDateFieldVO.setFgObligatorio("N");
							extJSDateFieldVO.setVisibleEnEdicion("N");
						} else {
							extJSDateFieldVO.setFgObligatorio("S");
						}
						camposFormAdd.add(extJSDateFieldVO);
						camposFormEdit.add(extJSDateFieldVO);
						/*if(campoCatalogoVO.getSwFiltro().equals("N")){
							extJSDateFieldVO.setXtype("HIDDEN");
						}*/						
						camposForm.add(extJSDateFieldVO);

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
					
					//extJSFieldVO.setAllowBlank(((campoCatalogoVO.getSwMandat().equals("S"))?"N":"S"));
					extJSFieldVO1.setAnchor("70%");
					
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
					extJSComboBoxVO.setTooltip(campoCatalogoVO.getTooltip());
					extJSComboBoxVO.setAyuda(campoCatalogoVO.getAyuda());
					String[] params = {"record"};
					StringBuffer sbOnSelect = new StringBuffer();
					sbOnSelect.append("").append("this.setValue(record.get('codigo'));").append("this.collapse();");
					extJSComboBoxVO.setOnSelect(new JSONFunction(params, sbOnSelect.toString()));
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
					/*if(campoCatalogoVO.getSwFiltro().equals("N")){
						extJSComboBoxVO.setXtype("HIDDEN");
					}*/
					extJSComboBoxVO.setMinLength("1");
					
					//camposForm.add(extJSComboBoxVO);
					camposFormEdit.add(extJSComboBoxVO);
					
					if (campoCatalogoVO.getSwAgrega().equals("N")) {
						ExtJSFieldVO extJSFieldVO = new ExtJSFieldVO();
						if (campoCatalogoVO.getSwFormat().equals("N")) {
							extJSFieldVO.setXtype("NUMBER");
						} else {
							extJSFieldVO.setXtype("TEXT");
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
						camposFormAdd.add(extJSComboBoxVO);
					}
				}
				
				GridColumnModelVO gridColumnModelVO = new GridColumnModelVO();
				gridColumnModelVO.setDataIndex(campoCatalogoVO.getCdColumn());
				gridColumnModelVO.setHeader(campoCatalogoVO.getDsColumn());
				gridColumnModelVO.setHidden(campoCatalogoVO.getSwVisible());
				gridColumnModelVO.setSortable(true);
				gridColumnModelVO.setTooltip("");
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
			
			session.put("camposForm", camposForm);
			session.put("camposFormAdd", camposFormAdd);
			session.put("camposFormEdit", camposFormEdit);
			session.put("gridColumnModel", gridColumnModel);
			session.put("recordsReader", recordsReader);
			session.put("dynaClass", JSONObject.fromObject(dynaClass).toString());
			success = true;
			//return SUCCESS;
	}

	public String cmdConfigurarCatalogoSimple () throws ApplicationException{
		session.put("dsTitulo", null);
		session.remove("dsTitulo");
		session.put("camposForm", null);
		session.remove("camposForm");
		session.put("gridColumnModel", null);
		session.remove("gridColumnModel");
		session.put("recordsReader", null);
		session.remove("recordsReader");
		session.put("dynaClass", null);
		session.remove("dynaClass");
		session.put("camposFormEdit", null);
		session.remove("camposFormEdit");
		

		session.put("modelControl", null);
		session.remove("modelControl");
		cmdObtenerTitulo();
		cmdObtenerCamposBusquedaCatalogoSimple();
		success = true;
		return "configurarCatalogoSimple";
	}

	public String cmdBuscarDatosGrilla () throws ApplicationException {
		try {
			PagedList pagedList = configuradorCatalogosManager.obtenerCamposBusquedaSimple(cdPantalla);
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
			
			ValoresCamposCatalogosSimplesVO camposCatalogosSimplesVO = new ValoresCamposCatalogosSimplesVO();
			camposCatalogosSimplesVO.setValor1(valor1);
			camposCatalogosSimplesVO.setValor2(valor2);
			camposCatalogosSimplesVO.setValor3(valor3);
			camposCatalogosSimplesVO.setValor4(valor4);
			camposCatalogosSimplesVO.setValor5(valor5);
	
			valoresPantalla = new ArrayList<ValoresCamposCatalogosSimplesVO>();
			valoresPantalla.add(camposCatalogosSimplesVO);
			int _limit = limit;
			int _start = start;
			pagedList = configuradorCatalogosManager.obtenerColumnasGrilla(cdPantalla, valoresPantalla, _start, _limit);
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
	
	public String cmdGuardarDatos () throws ApplicationException {
		try {
			PagedList pagedList = configuradorCatalogosManager.obtenerCamposBusquedaSimple(cdPantalla);
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


			String msg = configuradorCatalogosManager.guardarRegistro(cdPantalla, valoresPantalla);
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
			PagedList pagedList = configuradorCatalogosManager.obtenerCamposBusquedaSimple(cdPantalla);
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


			String msg = configuradorCatalogosManager.actualizarRegistro(cdPantalla, valoresPantalla);
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
			String msg = configuradorCatalogosManager.borrarRegistro(cdPantalla, vlLlave);
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
			ValoresCamposCatalogosSimplesVO valoresCamposCatalogosSimplesVO = new ValoresCamposCatalogosSimplesVO();
			valoresCamposCatalogosSimplesVO.setValor1(valor1);
			valoresCamposCatalogosSimplesVO.setValor2(valor2);
			valoresCamposCatalogosSimplesVO.setValor3(valor3);
			valoresCamposCatalogosSimplesVO.setValor4(valor4);
			valoresCamposCatalogosSimplesVO.setValor5(valor5);

			valoresPantalla = new ArrayList<ValoresCamposCatalogosSimplesVO>();
			valoresPantalla.add(valoresCamposCatalogosSimplesVO);
			TableModelExport model = configuradorCatalogosManager.getModel(cdPantalla, valoresPantalla);
			inputStream = exportFormat.export(model);
			success = true;
		} catch (Exception e) {
			success = false;
		}
		return SUCCESS;
	}

	public List<CampoCatalogoVO> getListaCampos() {
		return listaCampos;
	}

	public void setListaCampos(List<CampoCatalogoVO> listaCampos) {
		this.listaCampos = listaCampos;
	}

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
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}*/

	public Map getSession() {
		return session;
	}

	public void setConfiguradorCatalogosManager(
			ConfiguradorCatalogosManager configuradorCatalogosManager) {
		this.configuradorCatalogosManager = configuradorCatalogosManager;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public List<GridColumnModelVO> getColumnasGrilla() {
		return columnasGrilla;
	}

	public void setColumnasGrilla(List<GridColumnModelVO> columnasGrilla) {
		this.columnasGrilla = columnasGrilla;
	}

	public BasicDynaClass getDynaClass() {
		return dynaClass;
	}

	public List<DynaBean> getListaBeans() {
		return listaBeans;
	}

	public List<ValoresCamposCatalogosSimplesVO> getValoresPantalla() {
		return valoresPantalla;
	}

	public void setValoresPantalla(List<ValoresCamposCatalogosSimplesVO> valoresPantalla) {
		this.valoresPantalla = valoresPantalla;
	}

	public String getVlLlave() {
		return vlLlave;
	}

	public void setVlLlave(String vlLlave) {
		this.vlLlave = vlLlave;
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

	public String getDsTitulo() {
		return dsTitulo;
	}

	public void setDsTitulo(String dsTitulo) {
		this.dsTitulo = dsTitulo;
	}

	/*public int getStart() {
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
	}*/

}
