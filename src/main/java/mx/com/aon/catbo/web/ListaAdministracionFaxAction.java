package mx.com.aon.catbo.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.AtributosVblesVO;
import mx.com.aon.catbo.model.PolizaFaxVO;
import mx.com.aon.catbo.model.CasoDetalleVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ClienteVO;
import mx.com.aon.catbo.model.ExtJSAtributosFaxVO;
import mx.com.aon.catbo.model.ExtJSAtributosVO;
import mx.com.aon.catbo.model.ExtJSComboBoxVO;
import mx.com.aon.catbo.model.ExtJSDateFieldVO;
import mx.com.aon.catbo.model.ExtJSFieldVO;
import mx.com.aon.catbo.model.ExtJSLabelVO;
import mx.com.aon.catbo.model.ExtJSTextFieldVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.TipoGuionVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.service.AdministracionFaxManager;
import mx.com.aon.catbo.service.MatrizAsignacionManager;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;



import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;



public class ListaAdministracionFaxAction extends AbstractListAction implements SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1137878786546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaAdministracionFaxAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AdministracionFaxManager administracionFaxManager;
	
	//sacar estas declaraciones
	private transient AdministracionCasosManager administracionCasosManager;
	private transient MatrizAsignacionManager matrizAsignacionManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo VO
	 * con los valores de la consulta.
	 */
	private List<CasoVO> mListPolizas;
	private List<FaxesVO> mListAdministracionFaxes;
	private List<FaxesVO> mListAdministracionValorFaxes;
	private List<FaxesVO> mListAtributosVariablesFax;
	private List<PolizaFaxVO> mListPolizasFaxes;
	
	
	
	private String dsarchivo;
	private String nmfax;
	private String nmpoliex;
	private String nmsituac;
	
	
	
	// viejos sacarlos
	private List<CasoVO> mUsuariosResponsablesList;
	private List<CasoDetalleVO> mEstructuraCasoList;
	private List<CasoVO> mEstructuraMovCasoList;
	private List<CasoVO> mEstructuraNumCasoList;
	private List<ClienteVO> mListConsultaClientes;
	private List<CasoVO> mListAtributosVariables;
	private List<AtributosVblesVO> mListaSeccionesOrden;
	private List<CasoVO> mListMovimientosCaso;
	private List<CasoVO> mListResponsablesMCaso;
	private List<TipoGuionVO> traerSufijoPrefijo;
	private List<CasoVO> mEstructuraRsgCasoList;
	private List<CasoVO> mEstructuraRsgCasoUsrList;
	
	
	
	private String pv_nmcaso_i;
	private String codigoMatriz;
	private String dsModulo;
	private String cdProceso;
	private String cdmatriz;
	
	private String cdelemento;
	private String cdideper;
	private String cdperson;
	private String dsnombre;
	private String dsapellido;
	private String dsapellido1;
	private String cdformatoorden;
	private String cdseccion;
	
	private String desmodulo;
	
	private String cdmodulo;
	private String nmcaso;
	private String nmovimiento;
	private String cdordentrabajo;
	private String cdModulo;
	private String cdUsuario;
	private String cdUsuarioOld;
	private String desUsuario;	
	
	private String edit;
	private String cdtipoar; 
	
	private String funcionNombre;
	private String rol;
	private String cdusuari;
	
	private int cdVariable;
	private String flag;
	/**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
		
	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
	@SuppressWarnings("unchecked")
	private List modelControl = new ArrayList();
	
	@SuppressWarnings("unchecked")
	private Map session;

	
	
	
	

	
/******************************************* FIN *****************************************/	
	
	
	/* ***********************************************************************************************************************************
	 * Analizar si se puede hacer un solo metodo para llamar al exportar, dependiendo de la pantalla de donde venga la solicitud 
	 * ***********************************************************************************************************************************
	 */
	/**
	 * Metodo que exporta el listado de la grilla a un Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarListadoCasosSolicitudesClick1() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Consulta de Casos/Solicitudes." + exportFormat.getExtension();
			//TableModelExport model =  administracionCasosManager.getModel();			
			//inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
	
	public String cmdExportarListadoMovimientosPoCasoClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Consulta de Casos/Solicitudes." + exportFormat.getExtension();			
			//TableModelExport model =  administracionCasosManager.getModel();
			//inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
	public String cmbFaxSelectEditar() throws ApplicationException{		
		session.remove("modelControl");
		obtenerAtributosVariablesCasosEditar();
		success = true;
		return "dinamico";
	}
	
	public String cmbTareaSelect() throws ApplicationException{		
		session.remove("modelControl");
		obtenerAtributosVariablesCasos();
		success = true;
		return "dinamico";
	}
	
	public String cmbIrConsultarCasoDetalleClick() throws ApplicationException{		
		session.remove("modelControl");
		obtenerAtributosVariablesCasos();
		success = true;
		return "irConsultarCasoDetalle";
	}
	
	public String cmbIrConsultarMovimientoCasoClick() throws ApplicationException{		
		session.remove("modelControl");
		obtenerAtributosVariablesCasos();
		success = true;
		return "irConsultarMovimientoCaso";
	}
	
	@SuppressWarnings("unchecked")
	public void obtenerAtributosVariablesCasos()throws ApplicationException{
		@SuppressWarnings("unused")
		StringBuilder store = new StringBuilder();

		PagedList pagedList = administracionFaxManager.obtenerTatriarcFax(cdtipoar, start, limit);
		//mListAtributosVariablesFax = pagedList.getItemsRangeList();
		totalCount = pagedList.getTotalItems();
		success = true;
		
		if (session.get("modelControl") != null) {
			session.remove("modelControl");
		}
		
		List lista = pagedList.getItemsRangeList();
		
		ExtJSLabelVO extJSLabelVO;
		ExtJSAtributosFaxVO extJSAtributosFaxVO = (ExtJSAtributosFaxVO)lista.get(0);
		//String aux = extJSAtributosFaxVO.getCdSeccion();
		
		extJSLabelVO = new ExtJSLabelVO();
		StringBuffer sb = new StringBuffer();
		StringBuffer sbHtml = new StringBuffer();
		sbHtml.append("<span class='x-form-item' style='font-weight:bold'>").append(" "/*extJSAtributosFaxVO.getDsSeccion()*/).append("</span>");
		extJSLabelVO.setHtml(sbHtml.toString());
		sbHtml.delete(0, sbHtml.length());
		sb.append("{layout: 'form', colspan: 2, items: [").append(extJSLabelVO).append("]}\n");
		int contColumns = 0;
		
		for (int i=0; i<lista.size(); i++){
			extJSAtributosFaxVO = (ExtJSAtributosFaxVO)lista.get(i);
			/*if(!extJSAtributosFaxVO.getCdSeccion().equals(aux)){
				//aux = extJSAtributosFaxVO.getCdSeccion();
				extJSLabelVO = new ExtJSLabelVO();
				//sbHtml.append("<span class='x-form-item' style='font-weight:bold'>").append(extJSAtributosFaxVO.getDsSeccion()).append("</span>");
				extJSLabelVO.setHtml(sbHtml.toString());
				//sb = new StringBuffer();
				//sb.append("{layout: 'form', columnWidth: .1, items:[").append(extJSAtributosVO).append("]}");
				//modelControl.add(sb.toString());
				sbHtml.delete(0, sbHtml.length());
				int modulo = (contColumns % 2);  
				if (modulo != 0) {
					sb.append("{layout: 'form', items:[{html: \'<span class=\"x-form-item\" style=\"font-weight:bold\"></span>\'}]}\n");
					contColumns = 0;
				}
				sb.append("{layout: 'form', colspan: 2, items:[").append(extJSLabelVO).append("]}\n");
				//modelControl.add(sb.toString().replace("}\n{", "},\n{"));
			//}*/
			//extJSAtributosVO.setId(extJSAtributosVO.getCdFormatoOrden().concat("_").concat(extJSAtributosVO.getCdSeccion().concat("_").concat(extJSAtributosVO.getCdAtribu())));		
			//TODO analizar esto porque despues puede cambiar
			//El tipo de campo debe venir de la base de datos
			//Ahora esta hardcodeado para texfield o combo
			
			
			if(extJSAtributosFaxVO.getOtTabVal()==null){//.equals(null)){
				extJSAtributosFaxVO.setXtype("textfield");
			}else{
				//extJSAtributosVO.setXtype("combo");
				extJSAtributosFaxVO.setXtype("combo");
			}
			
			if (extJSAtributosFaxVO.getXtype().equals("textfield")){				
				extJSAtributosFaxVO.setAnchor("90%");
				extJSAtributosFaxVO.setAllowBlank("false");			
				extJSAtributosFaxVO.setFieldLabel(extJSAtributosFaxVO.getDsAtribu());
				StringBuffer _name = new StringBuffer();
				_name.append(extJSAtributosFaxVO.getCdAtribu());
				//_name.append(extJSAtributosFaxVO.getCdFormatoOrden()).append("_").append(extJSAtributosFaxVO.getCdSeccion()).append("_").append(extJSAtributosFaxVO.getCdAtribu());
				extJSAtributosFaxVO.setName(_name.toString());
				extJSAtributosFaxVO.setId(_name.toString());
				//extJSAtributosVO.setMinLength("1");
				extJSAtributosFaxVO.setMaxLength("25");
				if(this.edit != null){if(this.edit.equals("0"))extJSAtributosFaxVO.setDisabled("true");}				
				//if(this.edit.equals("0"))extJSAtributosVO.setReadOnly("true");
				//sb = new StringBuffer();
				sb.append("{layout: 'form', items:[").append(extJSAtributosFaxVO).append("]}\n");
				//modelControl.add(sb.toString().replace("}\n{", "},\n{"));
			}									
			
			if (extJSAtributosFaxVO.getXtype().equals("combo")) {
				ExtJSComboBoxVO extJSComboBoxVO = new ExtJSComboBoxVO();
				extJSComboBoxVO.setAllowBlank("false");
				extJSComboBoxVO.setAnchor("90%");
				extJSComboBoxVO.setCdAtribu(extJSAtributosFaxVO.getCdAtribu());
				extJSComboBoxVO.setCdSeccion(extJSAtributosFaxVO.getCdSeccion());
				extJSComboBoxVO.setCdFormatoOrden(extJSAtributosFaxVO.getCdFormatoOrden());
				if(this.edit != null){if(this.edit.equals("0"))extJSComboBoxVO.setDisabled("true");}
				//if(this.edit.equals("0"))extJSComboBoxVO.setReadOnly("true");
				extJSComboBoxVO.setFieldLabel(extJSAtributosFaxVO.getDsAtribu());				
				StringBuffer _name = new StringBuffer();
				_name.append(extJSAtributosFaxVO.getCdAtribu());
				extJSComboBoxVO.setName(_name.toString());
				extJSComboBoxVO.setId(_name.toString());
				//extJSComboBoxVO.setValue(extJSAtributosVO.getValue());
				//extJSComboBoxVO.setMinLength(extJSAtributosVO.getMinLength());
				extJSComboBoxVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{cd}.{ds}\" class=\"x-combo-list-item\">{ds}</div></tpl>");
				
				extJSComboBoxVO.setXtype("combo");
				extJSComboBoxVO.setEmpyText("Seleccione ...");
				extJSComboBoxVO.setMaxHeight(50);
				extJSComboBoxVO.setDisplayField("ds");
				extJSComboBoxVO.setForceSelection("true");
				extJSComboBoxVO.setHiddenName("dsHidden");
				extJSComboBoxVO.setLabelSeparator("");
				extJSComboBoxVO.setMode("local");
				extJSComboBoxVO.setSelectOnFocus("true");
				extJSComboBoxVO.setTriggerAction("all");
				extJSComboBoxVO.setTypeAhead("true");
				extJSComboBoxVO.setValueField("cd");
				
				extJSComboBoxVO.setOtTabVal(extJSAtributosFaxVO.getOtTabVal());
				
				
				extJSComboBoxVO.setStore("crearStoreComboAtributosVariables('" + extJSComboBoxVO.getOtTabVal() + "', '" + extJSComboBoxVO.getName() + "', null)");
				extJSComboBoxVO.setWidth("120");
				extJSComboBoxVO.setLazyRender("true");
				extJSComboBoxVO.setMaxLength("9999");
				extJSComboBoxVO.setMinLength("1");
				extJSComboBoxVO.setMaxLength("25");
			
				//StringBuffer sb = new StringBuffer();
				//sb.append("{layout: 'form', items: [").append(extJSComboBoxVO).append("]}");
				//sb = new StringBuffer();
				sb.append("{layout: 'form', columnWidth: .50, items:[").append(extJSComboBoxVO).append("]}\n");
				//modelControl.add(sb.toString().replace("}\n{", "},\n{"));
			}
			contColumns++;
			/*if (extJSAtributosVO.getXtype().equals("datefield")) {
				ExtJSDateFieldVO extJSDateFieldVO = new ExtJSDateFieldVO();
				extJSDateFieldVO.setAllowBlank(extJSAtributosVO.getAllowBlank());
				extJSDateFieldVO.setCdAtribu(extJSAtributosVO.getCdAtribu());
				extJSDateFieldVO.setFieldLabel(extJSAtributosVO.getFieldLabel());
				extJSDateFieldVO.setId(extJSAtributosVO.getId());
				extJSDateFieldVO.setName(extJSAtributosVO.getName());
				extJSDateFieldVO.setValue(extJSAtributosVO.getValue());
				extJSDateFieldVO.setAnchor("90%");
				extJSDateFieldVO.setMinLength(extJSAtributosVO.getMinLength());
				extJSDateFieldVO.setXtype("FECHA");
				extJSDateFieldVO.setWidth("100");
				extJSDateFieldVO.setFormat("d/m/Y");
				modelControl.add(extJSDateFieldVO);
			}*/
			//if(this.edit.equals("0"))extJSAtributosVO.setDisabled("true");
		}
		
		modelControl.add(sb.toString().replace("}\n{", "},\n{"));
		session.put("modelControl", modelControl);									
	}
	
	
	
	/**********Editar administracion de Fax*****/
	
	@SuppressWarnings("unchecked")
	public void obtenerAtributosVariablesCasosEditar()throws ApplicationException{
		@SuppressWarnings("unused")
		StringBuilder store = new StringBuilder();

		PagedList pagedList = administracionFaxManager.obtenerValoresFax(cdtipoar, nmcaso, nmfax, start, limit);
		//mListAtributosVariablesFax = pagedList.getItemsRangeList();
		totalCount = pagedList.getTotalItems();
		success = true;
		
		if (session.get("modelControl") != null) {
			session.remove("modelControl");
		}
		
		List lista = pagedList.getItemsRangeList();
		
		ExtJSLabelVO extJSLabelVO;
		ExtJSAtributosFaxVO extJSAtributosFaxVO = (ExtJSAtributosFaxVO)lista.get(0);
		//String aux = extJSAtributosFaxVO.getCdSeccion();
		
		extJSLabelVO = new ExtJSLabelVO();
		StringBuffer sb = new StringBuffer();
		StringBuffer sbHtml = new StringBuffer();
		sbHtml.append("<span class='x-form-item' style='font-weight:bold'>").append(""/*extJSAtributosFaxVO.getDsSeccion()*/).append("</span>");
		extJSLabelVO.setHtml(sbHtml.toString());
		sbHtml.delete(0, sbHtml.length());
		sb.append("{layout: 'form', colspan: 2, items: [").append(extJSLabelVO).append("]}\n");
		int contColumns = 0;
		
		for (int i=0; i<lista.size(); i++){
			extJSAtributosFaxVO = (ExtJSAtributosFaxVO)lista.get(i);
			/*if(!extJSAtributosFaxVO.getCdSeccion().equals(aux)){
				//aux = extJSAtributosFaxVO.getCdSeccion();
				extJSLabelVO = new ExtJSLabelVO();
				//sbHtml.append("<span class='x-form-item' style='font-weight:bold'>").append(extJSAtributosFaxVO.getDsSeccion()).append("</span>");
				extJSLabelVO.setHtml(sbHtml.toString());
				//sb = new StringBuffer();
				//sb.append("{layout: 'form', columnWidth: .1, items:[").append(extJSAtributosVO).append("]}");
				//modelControl.add(sb.toString());
				sbHtml.delete(0, sbHtml.length());
				int modulo = (contColumns % 2);  
				if (modulo != 0) {
					sb.append("{layout: 'form', items:[{html: \'<span class=\"x-form-item\" style=\"font-weight:bold\"></span>\'}]}\n");
					contColumns = 0;
				}
				sb.append("{layout: 'form', colspan: 2, items:[").append(extJSLabelVO).append("]}\n");
				//modelControl.add(sb.toString().replace("}\n{", "},\n{"));
			//}*/
			//extJSAtributosVO.setId(extJSAtributosVO.getCdFormatoOrden().concat("_").concat(extJSAtributosVO.getCdSeccion().concat("_").concat(extJSAtributosVO.getCdAtribu())));		
			//TODO analizar esto porque despues puede cambiar
			//El tipo de campo debe venir de la base de datos
			//Ahora esta hardcodeado para texfield o combo
			
			
			//if(extJSAtributosFaxVO.getOtTabVal().equals("")){
			
				
			
			if(extJSAtributosFaxVO.getOtTabVal()!=null){
				extJSAtributosFaxVO.setXtype("combo");
			}else{
				
				if (extJSAtributosFaxVO.getSwFormat().equals("A")||extJSAtributosFaxVO.getSwFormat().equals("F"))
				
				{extJSAtributosFaxVO.setXtype("textfield");}
					
					else
						
						if (extJSAtributosFaxVO.getSwFormat().equals("N")){
							extJSAtributosFaxVO.setXtype("numberfield");
							
						}			
				extJSAtributosFaxVO.setAnchor("90%");
				extJSAtributosFaxVO.setAllowBlank("false");			
				extJSAtributosFaxVO.setFieldLabel(extJSAtributosFaxVO.getDsAtribu());
				extJSAtributosFaxVO.setValue(extJSAtributosFaxVO.getOtvalor());
				
				
				StringBuffer _name = new StringBuffer();
				_name.append(extJSAtributosFaxVO.getCdAtribu());
				//_name.append(extJSAtributosFaxVO.getCdFormatoOrden()).append("_").append(extJSAtributosFaxVO.getCdSeccion()).append("_").append(extJSAtributosFaxVO.getCdAtribu());
				extJSAtributosFaxVO.setName(_name.toString());
				extJSAtributosFaxVO.setId(_name.toString());
				//extJSAtributosVO.setMinLength("1");
				extJSAtributosFaxVO.setMaxLength("25");
				if(this.edit != null){if(this.edit.equals("0"))extJSAtributosFaxVO.setDisabled("true");}				
				//if(this.edit.equals("0"))extJSAtributosVO.setReadOnly("true");
				//sb = new StringBuffer();
				sb.append("{layout: 'form', items:[").append(extJSAtributosFaxVO).append("]}\n");
				//modelControl.add(sb.toString().replace("}\n{", "},\n{"));
			}									
			
			if (extJSAtributosFaxVO.getXtype().equals("combo")) {
				ExtJSComboBoxVO extJSComboBoxVO = new ExtJSComboBoxVO();
				extJSComboBoxVO.setAllowBlank("false");
				extJSComboBoxVO.setAnchor("90%");
				extJSComboBoxVO.setCdAtribu(extJSAtributosFaxVO.getCdAtribu());
				extJSComboBoxVO.setValue(extJSAtributosFaxVO.getOtvalor());
				extJSComboBoxVO.setCdSeccion(extJSAtributosFaxVO.getCdSeccion());
				extJSComboBoxVO.setCdFormatoOrden(extJSAtributosFaxVO.getCdFormatoOrden());
				if(this.edit != null){if(this.edit.equals("0"))extJSComboBoxVO.setDisabled("true");}
				//if(this.edit.equals("0"))extJSComboBoxVO.setReadOnly("true");
				extJSComboBoxVO.setFieldLabel(extJSAtributosFaxVO.getDsAtribu());				
				StringBuffer _name = new StringBuffer();
				//_name.append(extJSAtributosFaxVO.getCdAtribu());
				
				_name.append(extJSAtributosFaxVO.getNmfax()).append("_").append(extJSAtributosFaxVO.getNmcaso()).append("_").append(extJSAtributosFaxVO.getCdAtribu());
				extJSComboBoxVO.setName(_name.toString());
				extJSComboBoxVO.setId(_name.toString());
				extJSComboBoxVO.setValue(extJSAtributosFaxVO.getValue());
				//extJSComboBoxVO.setMinLength(extJSAtributosVO.getMinLength());
				extJSComboBoxVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{cd}.{ds}\" class=\"x-combo-list-item\">{ds}</div></tpl>");
				
				extJSComboBoxVO.setXtype("combo");
				extJSComboBoxVO.setEmpyText("Seleccione ...");
				extJSComboBoxVO.setDisplayField("ds");
				extJSComboBoxVO.setForceSelection("true");
				extJSComboBoxVO.setHiddenName("dsHidden");
				//extJSComboBoxVO.setLabelSeparator("");
				extJSComboBoxVO.setMode("local");
				extJSComboBoxVO.setSelectOnFocus("true");
				extJSComboBoxVO.setTriggerAction("all");
				extJSComboBoxVO.setTypeAhead("true");
				extJSComboBoxVO.setValueField("cd");
				extJSComboBoxVO.setMaxHeight(50);
				//extJSComboBoxVO.setValueField(extJSAtributosFaxVO.getOtvalor());
				extJSComboBoxVO.setStore("crearStoreComboAtributosVariables('" + extJSAtributosFaxVO.getOtTabVal() + "', '" + /*extJSAtributosFaxVO.getCdAtribu() */ _name.toString() + "', '" + extJSAtributosFaxVO.getOtvalor() + "')");
				//extJSComboBoxVO.setStore("crearStoreComboAtributosVariables('" + extJSAtributosFaxVO.getOtTabVal() + "', '" + extJSAtributosFaxVO.getName() + "', null)");
				extJSComboBoxVO.setValue(extJSAtributosFaxVO.getOtvalor());
				extJSComboBoxVO.setWidth("120");
				extJSComboBoxVO.setLazyRender("true");
				extJSComboBoxVO.setMaxLength("9999");
				extJSComboBoxVO.setMinLength("1");
				extJSComboBoxVO.setMaxLength("25");
				//StringBuffer sb = new StringBuffer();
				//sb.append("{layout: 'form', items: [").append(extJSComboBoxVO).append("]}");
				//sb = new StringBuffer();
				sb.append("{layout: 'form', columnWidth: .50, items:[").append(extJSComboBoxVO).append("]}\n");
				//modelControl.add(sb.toString().replace("}\n{", "},\n{"));
			}
			contColumns++;
			/*if (extJSAtributosVO.getXtype().equals("datefield")) {
				ExtJSDateFieldVO extJSDateFieldVO = new ExtJSDateFieldVO();
				extJSDateFieldVO.setAllowBlank(extJSAtributosVO.getAllowBlank());
				extJSDateFieldVO.setCdAtribu(extJSAtributosVO.getCdAtribu());
				extJSDateFieldVO.setFieldLabel(extJSAtributosVO.getFieldLabel());
				extJSDateFieldVO.setId(extJSAtributosVO.getId());
				extJSDateFieldVO.setName(extJSAtributosVO.getName());
				extJSDateFieldVO.setValue(extJSAtributosVO.getValue());
				extJSDateFieldVO.setAnchor("90%");
				extJSDateFieldVO.setMinLength(extJSAtributosVO.getMinLength());
				extJSDateFieldVO.setXtype("FECHA");
				extJSDateFieldVO.setWidth("100");
				extJSDateFieldVO.setFormat("d/m/Y");
				modelControl.add(extJSDateFieldVO);
			}*/
			//if(this.edit.equals("0"))extJSAtributosVO.setDisabled("true");
		}
		modelControl.add(sb.toString().replace("}\n{", "},\n{"));
		session.put("modelControl", modelControl);									
	}
	
	
	/********************************************************************************************************
	 * ******************************************************************************************************
	 * Pantalla CatBo_AdministracionFax **********************************************************************
	 *********************************************************************************************************
	 *********************************************************************************************************/
	
	
	@SuppressWarnings("unchecked")
	public String btnObtenerAdministracionFaxClick() throws ApplicationException{
		try{
			PagedList pagedList = this.administracionFaxManager.obtenerAdministracionFax(dsarchivo, nmcaso, nmfax, nmpoliex, start, limit);
			//pv_dsarchivo_i, pv_nmcaso_i, pv_nmfax_i, pv_nmpoliex_i, pv_nmsituac_i
			mListAdministracionFaxes = pagedList.getItemsRangeList();
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
	
	@SuppressWarnings("unchecked")
	public String ObtenerPolizaFaxClick() throws ApplicationException{
		try{
			
			mListPolizasFaxes = new ArrayList<PolizaFaxVO>();
			PolizaFaxVO polizaFaxVO = this.administracionFaxManager.obtenerPolizaFax(nmcaso);
			
			mListPolizasFaxes.add(polizaFaxVO);
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
	
	
	
	@SuppressWarnings("unchecked")
	public String btnObtenerAdministracionValorFaxClick() throws ApplicationException{
		try{
			PagedList pagedList = this.administracionFaxManager.obtenerAdministracionValorFax(nmcaso, nmfax, start, limit);
			//pv_nmcaso_i, pv_nmfax_i
			mListAdministracionValorFaxes = pagedList.getItemsRangeList();
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
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public String btnObtenerCasoClick() throws ApplicationException{
		try{
			
			mEstructuraCasoList = new ArrayList<CasoDetalleVO>();
			CasoDetalleVO casoDetalleVO = this.administracionCasosManager.getObtenerCaso(pv_nmcaso_i,cdmatriz);
			mEstructuraCasoList.add(casoDetalleVO);
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
	
	
	public String cmdExportarListadoMovimientosCasoClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}		
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Consulta de Casos/Solicitudes." + exportFormat.getExtension();
			TableModelExport model =  administracionCasosManager.getModelMCaso(pv_nmcaso_i);			
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
	/*****************funcion que obtiene el ususario de session*************************/
	
	public String cmdGetFuncionNombreClick()throws Exception
	{
		try
		{
			UserVO usuario = (UserVO) session.get("USUARIO");

				if(usuario != null && usuario.getUser()!= null){
	                logger.debug("seteando en el caso el usuario logueado al sistema: " + usuario.getUser());
	               /* if (usuario.getRolActivo().getObjeto().getValue()!=null)
	                {
	                	rol=usuario.getRolActivo().getObjeto().getValue();
	                }*/
	                funcionNombre= usuario.getUser();
				}
			success = true;
            return SUCCESS;
        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	
	public String cmdGetFuncionCodigoClick()throws Exception
	{
		try
		{
			UserVO usuario = (UserVO) session.get("USUARIO");

				if(usuario != null && usuario.getUser()!= null){
	                logger.debug("seteando en el caso el usuario logueado al sistema: " + usuario.getUser());
	               /* if (usuario.getRolActivo().getObjeto().getValue()!=null)
	                {
	                	rol=usuario.getRolActivo().getObjeto().getValue();
	                }*/
	                cdusuari= usuario.getUser();
				}
			success = true;
            return SUCCESS;
        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	/************************************************************************************************/
	/**************Metodo mejorado para mostrar un mensaje cuando no viene atributos variables*******/
	/************************************************************************************************/
	public String cmbTareaSelectFaxAgregar() throws ApplicationException{		
		session.remove("modelControl");
		String resultado ="vacio"; 
		resultado = obtenerAtributosVariablesAgrgarFax();
		return resultado;
		}
	
	
	@SuppressWarnings("unchecked")
	public String obtenerAtributosVariablesAgrgarFax()throws ApplicationException{
		@SuppressWarnings("unused")
		StringBuilder store = new StringBuilder();
	try{	
		PagedList pagedList = administracionFaxManager.obtenerTatriarcFax(cdtipoar, start, limit);
		//mListAtributosVariablesFax = pagedList.getItemsRangeList();
		totalCount = pagedList.getTotalItems();
		
		if (totalCount > 0){
						success = true;
						
						if (session.get("modelControl") != null) {
							session.remove("modelControl");
						}
						
						List lista = pagedList.getItemsRangeList();
						
						ExtJSLabelVO extJSLabelVO;
						ExtJSAtributosFaxVO extJSAtributosFaxVO = (ExtJSAtributosFaxVO)lista.get(0);
						//String aux = extJSAtributosFaxVO.getCdSeccion();
						
						extJSLabelVO = new ExtJSLabelVO();
						StringBuffer sb = new StringBuffer();
						StringBuffer sbHtml = new StringBuffer();
						sbHtml.append("<span class='x-form-item' style='font-weight:bold'>").append(" "/*extJSAtributosFaxVO.getDsSeccion()*/).append("</span>");
						extJSLabelVO.setHtml(sbHtml.toString());
						sbHtml.delete(0, sbHtml.length());
						sb.append("{layout: 'form', colspan: 2, items: [").append(extJSLabelVO).append("]}\n");
						int contColumns = 0;
						
						for (int i=0; i<lista.size(); i++){
							extJSAtributosFaxVO = (ExtJSAtributosFaxVO)lista.get(i);
							
							if(extJSAtributosFaxVO.getOtTabVal()!=null){//.equals(null)){
								extJSAtributosFaxVO.setXtype("combo");
							}
							
							else{
							
							if (extJSAtributosFaxVO.getSwFormat().equals("A")||extJSAtributosFaxVO.getSwFormat().equals("F"))
							
							{extJSAtributosFaxVO.setXtype("textfield");}
								
								else
									
									if (extJSAtributosFaxVO.getSwFormat().equals("N")){
										extJSAtributosFaxVO.setXtype("numberfield");
										
									}
									
								extJSAtributosFaxVO.setAnchor("90%");
								extJSAtributosFaxVO.setAllowBlank("false");			
								extJSAtributosFaxVO.setFieldLabel(extJSAtributosFaxVO.getDsAtribu());
								StringBuffer _name = new StringBuffer();
								_name.append(extJSAtributosFaxVO.getCdAtribu());
								extJSAtributosFaxVO.setName(_name.toString());
								
								extJSAtributosFaxVO.setId(_name.toString());
								extJSAtributosFaxVO.setMinLength(extJSAtributosFaxVO.getNmLmin());
								extJSAtributosFaxVO.setMaxLength("99");
								if(this.edit != null){if(this.edit.equals("0"))extJSAtributosFaxVO.setDisabled("true");}				
								sb.append("{layout: 'form', items:[").append(extJSAtributosFaxVO).append("]}\n");
								
							}									
							
							if (extJSAtributosFaxVO.getXtype().equals("combo")) {
								ExtJSComboBoxVO extJSComboBoxVO = new ExtJSComboBoxVO();
								extJSComboBoxVO.setAllowBlank("false");
								extJSComboBoxVO.setAnchor("90%");
								extJSComboBoxVO.setCdAtribu(extJSAtributosFaxVO.getCdAtribu());
								extJSComboBoxVO.setCdSeccion(extJSAtributosFaxVO.getCdSeccion());
								extJSComboBoxVO.setCdFormatoOrden(extJSAtributosFaxVO.getCdFormatoOrden());
								if(this.edit != null){if(this.edit.equals("0"))extJSComboBoxVO.setDisabled("true");}
								extJSComboBoxVO.setFieldLabel(extJSAtributosFaxVO.getDsAtribu());				
								StringBuffer _name = new StringBuffer();
								_name.append(extJSAtributosFaxVO.getCdAtribu());
								extJSComboBoxVO.setName(_name.toString());
								extJSComboBoxVO.setId(_name.toString());
								extJSComboBoxVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{cd}.{ds}\" class=\"x-combo-list-item\">{ds}</div></tpl>");
								
								extJSComboBoxVO.setXtype("combo");
								extJSComboBoxVO.setEmpyText("Seleccione ...");
								extJSComboBoxVO.setMaxHeight(50);
								extJSComboBoxVO.setDisplayField("ds");
								extJSComboBoxVO.setForceSelection("true");
								extJSComboBoxVO.setHiddenName("dsHidden");
								extJSComboBoxVO.setLabelSeparator("");
								extJSComboBoxVO.setMode("local");
								extJSComboBoxVO.setSelectOnFocus("true");
								extJSComboBoxVO.setTriggerAction("all");
								extJSComboBoxVO.setTypeAhead("true");
								extJSComboBoxVO.setValueField("cd");
								
								extJSComboBoxVO.setOtTabVal(extJSAtributosFaxVO.getOtTabVal());
								
								
								extJSComboBoxVO.setStore("crearStoreComboAtributosVariables('" + extJSComboBoxVO.getOtTabVal() + "', '" + extJSComboBoxVO.getName() + "', null)");
								extJSComboBoxVO.setWidth("120");
								extJSComboBoxVO.setLazyRender("true");
								extJSComboBoxVO.setMaxLength("9999");
								extJSComboBoxVO.setMinLength("1");
								extJSComboBoxVO.setMaxLength("99");
							
								sb.append("{layout: 'form', columnWidth: .50, items:[").append(extJSComboBoxVO).append("]}\n");
								
							}
							contColumns++;
							
						}
						
						modelControl.add(sb.toString().replace("}\n{", "},\n{"));
						session.put("modelControl", modelControl);
						return "dinamico";
	}else{
		return "vacio";
	}
	
	
	}catch(ApplicationException e)
	{
		
		return "vacio";

    }catch( Exception e){
    	
    	return "vacio";
    }
	}
	
	/***********************************************FIN*************************************************/
	
	public List getModelControl() {
		return modelControl;
	}


	public void setModelControl(List modelControl) {
		this.modelControl = modelControl;
	}


	public Map getSession() {
		return session;
	}


	public void setSession(Map session) {
		this.session = session;
	}


	public void setAdministracionCasosManager(
			AdministracionCasosManager administracionCasosManager) {
		this.administracionCasosManager = administracionCasosManager;
	}


	public MatrizAsignacionManager getMatrizAsignacionManager() {
		return matrizAsignacionManager;
	}


	public void setMatrizAsignacionManager(
			MatrizAsignacionManager matrizAsignacionManager) {
		this.matrizAsignacionManager = matrizAsignacionManager;
	}


	public String getPv_nmcaso_i() {
		return pv_nmcaso_i;
	}


	public void setPv_nmcaso_i(String pv_nmcaso_i) {
		this.pv_nmcaso_i = pv_nmcaso_i;
	}


	public AdministracionCasosManager getAdministracionCasosManager() {
		return administracionCasosManager;
	}






	public List<CasoDetalleVO> getMEstructuraCasoList() {
		return mEstructuraCasoList;
	}


	public void setMEstructuraCasoList(List<CasoDetalleVO> estructuraCasoList) {
		mEstructuraCasoList = estructuraCasoList;
	}


	public String getCodigoMatriz() {
		return codigoMatriz;
	}


	public void setCodigoMatriz(String codigoMatriz) {
		this.codigoMatriz = codigoMatriz;
	}


	public List<CasoVO> getMEstructuraMovCasoList() {
		return mEstructuraMovCasoList;
	}


	public void setMEstructuraMovCasoList(List<CasoVO> estructuraMovCasoList) {
		mEstructuraMovCasoList = estructuraMovCasoList;
	}


	public List<CasoVO> getMEstructuraNumCasoList() {
		return mEstructuraNumCasoList;
	}


	public void setMEstructuraNumCasoList(List<CasoVO> estructuraNumCasoList) {
		mEstructuraNumCasoList = estructuraNumCasoList;
	}


	public String getDsModulo() {
		return dsModulo;
	}


	public void setDsModulo(String dsModulo) {
		this.dsModulo = dsModulo;
	}


	public String getCdProceso() {
		return cdProceso;
	}


	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}



	public List<CasoVO> getMUsuariosResponsablesList() {
		return mUsuariosResponsablesList;
	}


	public void setMUsuariosResponsablesList(List<CasoVO> usuariosResponsablesList) {
		mUsuariosResponsablesList = usuariosResponsablesList;
	}


	public String getCdmatriz() {
		return cdmatriz;
	}


	public void setCdmatriz(String cdmatriz) {
		this.cdmatriz = cdmatriz;
	}


	public String getCdideper() {
		return cdideper;
	}


	public void setCdideper(String cdideper) {
		this.cdideper = cdideper;
	}


	public String getCdperson() {
		return cdperson;
	}


	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}


	public String getDsnombre() {
		return dsnombre;
	}


	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}


	public String getDsapellido() {
		return dsapellido;
	}


	public void setDsapellido(String dsapellido) {
		this.dsapellido = dsapellido;
	}


	public String getDsapellido1() {
		return dsapellido1;
	}


	public void setDsapellido1(String dsapellido1) {
		this.dsapellido1 = dsapellido1;
	}


	public List<ClienteVO> getMListConsultaClientes() {
		return mListConsultaClientes;
	}


	public void setMListConsultaClientes(List<ClienteVO> listConsultaClientes) {
		mListConsultaClientes = listConsultaClientes;
	}


	public String getCdelemento() {
		return cdelemento;
	}


	public void setCdelemento(String cdelemento) {
		this.cdelemento = cdelemento;
	}


	public String getCdformatoorden() {
		return cdformatoorden;
	}


	public void setCdformatoorden(String cdformatoorden) {
		this.cdformatoorden = cdformatoorden;
	}


	public String getCdseccion() {
		return cdseccion;
	}


	public void setCdseccion(String cdseccion) {
		this.cdseccion = cdseccion;
	}


	public List<CasoVO> getMListAtributosVariables() {
		return mListAtributosVariables;
	}


	public void setMListAtributosVariables(List<CasoVO> listAtributosVariables) {
		mListAtributosVariables = listAtributosVariables;
	}


	public String getDesmodulo() {
		return desmodulo;
	}


	public void setDesmodulo(String desmodulo) {
		this.desmodulo = desmodulo;
	}

	
	public List<AtributosVblesVO> getMListaSeccionesOrden() {
		return mListaSeccionesOrden;
	}


	public void setMListaSeccionesOrden(List<AtributosVblesVO> listaSeccionesOrden) {
		mListaSeccionesOrden = listaSeccionesOrden;
	}


	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}


	public List<CasoVO> getMListMovimientosCaso() {
		return mListMovimientosCaso;
	}


	public void setMListMovimientosCaso(List<CasoVO> listMovimientosCaso) {
		mListMovimientosCaso = listMovimientosCaso;
	}


	public ExportMediator getExportMediator() {
		return exportMediator;
	}


	public List<CasoVO> getMListResponsablesMCaso() {
		return mListResponsablesMCaso;
	}


	public void setMListResponsablesMCaso(List<CasoVO> listResponsablesMCaso) {
		mListResponsablesMCaso = listResponsablesMCaso;
	}


	public String getNmcaso() {
		return nmcaso;
	}


	public void setNmcaso(String nmcaso) {
		this.nmcaso = nmcaso;
	}


	public String getNmovimiento() {
		return nmovimiento;
	}


	public void setNmovimiento(String nmovimiento) {
		this.nmovimiento = nmovimiento;
	}


	public String getCdordentrabajo() {
		return cdordentrabajo;
	}


	public void setCdordentrabajo(String cdordentrabajo) {
		this.cdordentrabajo = cdordentrabajo;
	}


	public List<TipoGuionVO> getTraerSufijoPrefijo() {
		return traerSufijoPrefijo;
	}


	public void setTraerSufijoPrefijo(List<TipoGuionVO> traerSufijoPrefijo) {
		this.traerSufijoPrefijo = traerSufijoPrefijo;
	}


	public String getCdmodulo() {
		return cdmodulo;
	}


	public void setCdmodulo(String cdmodulo) {
		this.cdmodulo = cdmodulo;
	}


	public List<CasoVO> getMEstructuraRsgCasoList() {
		return mEstructuraRsgCasoList;
	}


	public void setMEstructuraRsgCasoList(List<CasoVO> estructuraRsgCasoList) {
		mEstructuraRsgCasoList = estructuraRsgCasoList;
	}


	public String getCdModulo() {
		return cdModulo;
	}


	public void setCdModulo(String cdModulo) {
		this.cdModulo = cdModulo;
	}


	public List<CasoVO> getMEstructuraRsgCasoUsrList() {
		return mEstructuraRsgCasoUsrList;
	}


	public void setMEstructuraRsgCasoUsrList(List<CasoVO> estructuraRsgCasoUsrList) {
		mEstructuraRsgCasoUsrList = estructuraRsgCasoUsrList;
	}


	public String getCdUsuario() {
		return cdUsuario;
	}


	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}


	


	public String getDesUsuario() {
		return desUsuario;
	}


	public void setDesUsuario(String desUsuario) {
		this.desUsuario = desUsuario;
	}


	public String getEdit() {
		return edit;
	}


	public void setEdit(String edit) {
		this.edit = edit;
	}


	public String getCdUsuarioOld() {
		return cdUsuarioOld;
	}


	public void setCdUsuarioOld(String cdUsuarioOld) {
		this.cdUsuarioOld = cdUsuarioOld;
	}
	/*Metodos de Administracion de fax*/


	public void setAdministracionFaxManager(
			AdministracionFaxManager administracionFaxManager) {
		this.administracionFaxManager = administracionFaxManager;
	}


	public List<FaxesVO> getMListAdministracionFaxes() {
		return mListAdministracionFaxes;
	}


	public void setMListAdministracionFaxes(List<FaxesVO> listAdministracionFaxes) {
		mListAdministracionFaxes = listAdministracionFaxes;
	}


	public String getDsarchivo() {
		return dsarchivo;
	}


	public void setDsarchivo(String dsarchivo) {
		this.dsarchivo = dsarchivo;
	}


	public String getNmfax() {
		return nmfax;
	}


	public void setNmfax(String nmfax) {
		this.nmfax = nmfax;
	}


	public String getNmpoliex() {
		return nmpoliex;
	}


	public void setNmpoliex(String nmpoliex) {
		this.nmpoliex = nmpoliex;
	}


	public String getNmsituac() {
		return nmsituac;
	}


	public void setNmsituac(String nmsituac) {
		this.nmsituac = nmsituac;
	}


	public List<FaxesVO> getMListAdministracionValorFaxes() {
		return mListAdministracionValorFaxes;
	}


	public void setMListAdministracionValorFaxes(
			List<FaxesVO> listAdministracionValorFaxes) {
		mListAdministracionValorFaxes = listAdministracionValorFaxes;
	}

	public String getCdtipoar() {
		return cdtipoar;
	}

	public void setCdtipoar(String cdtipoar) {
		this.cdtipoar = cdtipoar;
	}

	public List<FaxesVO> getMListAtributosVariablesFax() {
		return mListAtributosVariablesFax;
	}

	public void setMListAtributosVariablesFax(
			List<FaxesVO> listAtributosVariablesFax) {
		mListAtributosVariablesFax = listAtributosVariablesFax;
	}

	public String getFuncionNombre() {
		return funcionNombre;
	}

	public void setFuncionNombre(String funcionNombre) {
		this.funcionNombre = funcionNombre;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getCdusuari() {
		return cdusuari;
	}

	public void setCdusuari(String cdusuari) {
		this.cdusuari = cdusuari;
	}

	public int getCdVariable() {
		return cdVariable;
	}

	public void setCdVariable(int cdVariable) {
		this.cdVariable = cdVariable;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<PolizaFaxVO> getMListPolizasFaxes() {
		return mListPolizasFaxes;
	}

	public void setMListPolizasFaxes(List<PolizaFaxVO> listPolizasFaxes) {
		mListPolizasFaxes = listPolizasFaxes;
	}

	public List<CasoVO> getMListPolizas() {
		return mListPolizas;
	}

	public void setMListPolizas(List<CasoVO> listPolizas) {
		mListPolizas = listPolizas;
	}
	
	
	
}
