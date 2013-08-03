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
import mx.com.aon.catbo.model.CasoDetalleVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ClienteVO;
import mx.com.aon.catbo.model.ExtJSAtributosVO;
import mx.com.aon.catbo.model.ExtJSComboBoxVO;
import mx.com.aon.catbo.model.ExtJSDateFieldVO;
import mx.com.aon.catbo.model.ExtJSFieldVO;
import mx.com.aon.catbo.model.ExtJSLabelVO;
import mx.com.aon.catbo.model.ExtJSTextFieldVO;
import mx.com.aon.catbo.model.TipoGuionVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.service.AdministracionCasosManager2;
import mx.com.aon.catbo.service.MatrizAsignacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;
import mx.com.aon.utils.Constantes;



import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.biosnet.ice.ext.elements.form.ComboControl;
import com.biosnet.ice.ext.elements.form.SimpleCombo;
import com.biosnet.ice.ext.elements.utils.ComboBoxlUtils;



public class ListaAdministacionCasosAction extends AbstractListAction implements SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1137878786546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaAdministacionCasosAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AdministracionCasosManager administracionCasosManager;
	private transient MatrizAsignacionManager matrizAsignacionManager;
	private transient AdministracionCasosManager2 administracionCasosManager2;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo VO
	 * con los valores de la consulta.
	 */
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
	private List<CasoVO> mEstructuraRsgCasoUsrRspnsblList;
	
	
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
	private String pv_nmovimiento_i;
	private String flag;
	
	private String wORIGEN;
	
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

	
	
	/**
	 * Metodo que realiza una búsqueda en base a criterios de búsquedas
	 *  
	 * @param 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String btnBuscarClientesClick() throws Exception{
		try{
            //PagedList pagedList = manager.obtener...(params..., start, limit);
            //mConsultaClienteList = pagedList.getItemsRangeList();
            //totalCount = pagedList.getTotalItems();                                                    
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
		
	
	/**
	 * Metodo que realiza una búsqueda en base a criterios de búsquedas.
	 * Pantalla CatBo_ConsultarCasos
	 *  
	 * @param 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String btnBuscarCasosClick() throws Exception{
		try{
            //PagedList pagedList = administracionCasosManager.obtenerCasos(params...., start, limit);
            //mConsultaClienteList = pagedList.getItemsRangeList();
            //totalCount = pagedList.getTotalItems();                                                    
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
	

/************************************ Buscar Numeracion de Casos ****************************************/
	
	/**
	 * ejecuta la busqueda para el llenado del grid de Numeración de Casos
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarNcClick() throws Exception{
		try{

            PagedList pagedList = this.administracionCasosManager.obtenerNumerosCasos(desmodulo, start, limit);
            mEstructuraNumCasoList = pagedList.getItemsRangeList();
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
/******************************************* FIN *****************************************/
	
/***************************** Exporta Numeracion de Casos ****************************/
	
	/**
	 * Metodo que busca un conjunto de Numeracion de Casos 
	 * y EXPORTA el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	
	public String cmdExportarListadoCasosSolicitudesClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
		     contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "StatusDeCasos." + exportFormat.getExtension();
			TableModelExport model =  administracionCasosManager.getModel(desmodulo);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
	
	/*
	public String cmdExportarListadoCasosSolicitudesClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "AdminitracionCasos." + exportFormat.getExtension();
			TableModelExport model =  administracionCasosManager.getModel(desmodulo);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
	*/
	
	
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
		if (logger.isDebugEnabled()) {
			logger.debug("-> obtenerAtributosVariablesCasos ");
		}
		@SuppressWarnings("unused")
		StringBuilder store = new StringBuilder();

		PagedList pagedList = administracionCasosManager.obtenerAtributoSeccion(cdformatoorden, cdseccion, start, limit);
		//mListAtributosVariables = pagedList.getItemsRangeList();
		totalCount = pagedList.getTotalItems();
		success = true;
		
		if (session.get("modelControl") != null) {
			session.remove("modelControl");
		}
		
		List lista = pagedList.getItemsRangeList();
		
		if (logger.isDebugEnabled()) {
			logger.debug(".. lista : " + lista);
		}
		
		///////////////////////////////Se obtienen los ComboControl
		List<ComboControl> comboControlElements = administracionCasosManager.obtenerAtributoSeccionComboControl(cdformatoorden, cdseccion);
		logger.debug("::::::: comboControlElements : " + comboControlElements);
		
		String[] backupTables = null;
        if (comboControlElements != null && !comboControlElements.isEmpty()) {
            int storeBackup = 0;
            backupTables = new String[comboControlElements.size()];
            for (ComboControl comboControl : comboControlElements) {
                logger.debug("::::::::::::::: BackupTable : " + comboControl.getBackupTable());
                backupTables[storeBackup++] = comboControl.getBackupTable();
            }
        }
        
        //SimpleComboUtils comboUtils = new SimpleComboUtils();
        List<SimpleCombo> simpleComboElements = null;
        ComboBoxlUtils comboUtils = new ComboBoxlUtils();

        String url = ServletActionContext.getRequest().getContextPath()
        	+ Constantes.URL_ACTION_COMBOS;
        
        simpleComboElements = comboUtils.getDefaultSimpleComboList(comboControlElements, url);
        logger.debug("::::::: simpleComboElements : " + simpleComboElements);
		////////////////////////////////////////////////////////////////////////////////
		
		ExtJSLabelVO extJSLabelVO;
		ExtJSAtributosVO extJSAtributosVO = (ExtJSAtributosVO)lista.get(0);

		String aux = extJSAtributosVO.getCdSeccion();
		boolean anidado = true;
		
		extJSLabelVO = new ExtJSLabelVO();
		StringBuffer sb = new StringBuffer();
		StringBuffer sbHtml = new StringBuffer();
		sbHtml.append("<span class='x-form-item' style='font-weight:bold'>").append(extJSAtributosVO.getDsSeccion()).append("</span>");
		extJSLabelVO.setHtml(sbHtml.toString());
		sbHtml.delete(0, sbHtml.length());
		sb.append("{layout: 'form', colspan: 2, items: [").append(extJSLabelVO).append("]}\n");
		int contColumns = 0;
		
		for (int i=0; i<lista.size(); i++){
			extJSAtributosVO = (ExtJSAtributosVO)lista.get(i);
			if(!extJSAtributosVO.getCdSeccion().equals(aux)){				
				aux = extJSAtributosVO.getCdSeccion();
				extJSLabelVO = new ExtJSLabelVO();
				sbHtml.append("<span class='x-form-item' style='font-weight:bold'>").append(extJSAtributosVO.getDsSeccion()).append("</span>");
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
			}
			//extJSAtributosVO.setId(extJSAtributosVO.getCdFormatoOrden().concat("_").concat(extJSAtributosVO.getCdSeccion().concat("_").concat(extJSAtributosVO.getCdAtribu())));		
			//TODO analizar esto porque despues puede cambiar
			//El tipo de campo debe venir de la base de datos
			//Ahora esta hardcodeado para texfield o combo
			if(extJSAtributosVO.getOtTabVal().equals("")){
				extJSAtributosVO.setXtype("textfield");
			}else{
				//extJSAtributosVO.setXtype("combo");
				extJSAtributosVO.setXtype("combo");
			}
			
			if (extJSAtributosVO.getXtype().equals("textfield")){				
				extJSAtributosVO.setAnchor("90%");
				extJSAtributosVO.setAllowBlank("false");			
				extJSAtributosVO.setFieldLabel(extJSAtributosVO.getDsAtribu());
				StringBuffer _name = new StringBuffer();
				_name.append(extJSAtributosVO.getCdFormatoOrden()).append("_").append(extJSAtributosVO.getCdSeccion()).append("_").append(extJSAtributosVO.getCdAtribu());
				extJSAtributosVO.setName(_name.toString());
				extJSAtributosVO.setId(_name.toString());
				//extJSAtributosVO.setMinLength("1");
				extJSAtributosVO.setMaxLength("25");
				if(this.edit != null){if(this.edit.equals("0"))extJSAtributosVO.setDisabled("true");}				
				//if(this.edit.equals("0"))extJSAtributosVO.setReadOnly("true");
				//sb = new StringBuffer();
				sb.append("{layout: 'form', items:[").append(extJSAtributosVO).append("]}\n");
				//modelControl.add(sb.toString().replace("}\n{", "},\n{"));
			}									
			
			if (extJSAtributosVO.getXtype().equals("combo")) {
				if (logger.isDebugEnabled()) {
					logger.debug(".. extJSAtributosVO : " + extJSAtributosVO);
				}
				ExtJSComboBoxVO extJSComboBoxVO = new ExtJSComboBoxVO();
				
				extJSComboBoxVO.setAllowBlank("false");
				extJSComboBoxVO.setAnchor("90%");
				extJSComboBoxVO.setCdAtribu(extJSAtributosVO.getCdAtribu());
				extJSComboBoxVO.setCdSeccion(extJSAtributosVO.getCdSeccion());
				extJSComboBoxVO.setCdFormatoOrden(extJSAtributosVO.getCdFormatoOrden());
				if(this.edit != null){if(this.edit.equals("0"))extJSComboBoxVO.setDisabled("true");}
				//if(this.edit.equals("0"))extJSComboBoxVO.setReadOnly("true");
				extJSComboBoxVO.setFieldLabel(extJSAtributosVO.getDsAtribu());				
				StringBuffer _name = new StringBuffer();
				_name.append(extJSAtributosVO.getCdFormatoOrden()).append("_").append(extJSAtributosVO.getCdSeccion()).append("_").append(extJSAtributosVO.getCdAtribu());
				extJSComboBoxVO.setName(_name.toString());
				extJSComboBoxVO.setId(_name.toString());
				//extJSComboBoxVO.setValue(extJSAtributosVO.getValue());
				//extJSComboBoxVO.setMinLength(extJSAtributosVO.getMinLength());
				extJSComboBoxVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{value}.{label}\" class=\"x-combo-list-item\">{label}</div></tpl>");
				
				extJSComboBoxVO.setXtype("combo");
				extJSComboBoxVO.setEmpyText("Seleccione ...");
				extJSComboBoxVO.setDisplayField("label");
				extJSComboBoxVO.setForceSelection("true");
				extJSComboBoxVO.setHiddenName("dsHidden");
				extJSComboBoxVO.setLabelSeparator("");
				extJSComboBoxVO.setMode("local");
				extJSComboBoxVO.setSelectOnFocus("true");
				extJSComboBoxVO.setTriggerAction("all");
				extJSComboBoxVO.setTypeAhead("true");
				extJSComboBoxVO.setValueField("value");
				extJSComboBoxVO.setCdAgrupa(extJSAtributosVO.getCdAgrupa());
				if (logger.isDebugEnabled()) {
					logger.debug(":::::: OtTabVal : " + extJSAtributosVO.getOtTabVal());
					logger.debug(":::::: CdAgrupa : " + extJSAtributosVO.getCdAgrupa());
					logger.debug(":::::: CdAtribu : " + extJSAtributosVO.getCdAtribu());
				}
				////////////////Setting store and listeners
				if (StringUtils.isNotBlank(extJSComboBoxVO.getCdAgrupa())) {
					if (simpleComboElements != null && !simpleComboElements.isEmpty()) {
						for (SimpleCombo sc : simpleComboElements) {
							if (sc.getFieldLabel().equalsIgnoreCase(extJSAtributosVO.getDsAtribu())) {
								if (logger.isDebugEnabled()) {
									logger.debug(":::::: FieldLabel : " + sc.getFieldLabel());
									logger.debug(":::::: Store : " + sc.getStore());
									logger.debug(":::::: Listeners : " + sc.getListeners());
								}		
								extJSComboBoxVO.setStore(sc.getStore());
								
								if (sc.getListeners() != null) {
									extJSComboBoxVO.setListeners(sc.getListeners());
								}
							}
						}
					} else {
						anidado = false;
					}
				} else {
					anidado = false;
				}
				////////////////////////////////////////////////////////////////
				if (logger.isDebugEnabled()) {
					logger.debug(":::::: anidado : " + anidado);
				}
				if (!anidado) {
					extJSComboBoxVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{cd}.{ds}\" class=\"x-combo-list-item\">{ds}</div></tpl>");
					extJSComboBoxVO.setValueField("cd");
					extJSComboBoxVO.setDisplayField("ds");
					extJSComboBoxVO.setStore("crearStoreComboAtributosVariables('" + extJSAtributosVO.getOtTabVal() + "', '" + extJSAtributosVO.getName() + "', null)");
				}
				extJSComboBoxVO.setWidth("120");
				extJSComboBoxVO.setLazyRender("true");
				extJSComboBoxVO.setMaxLength("9999");
				extJSComboBoxVO.setMinLength("1");
				extJSComboBoxVO.setMaxLength("25");
				extJSComboBoxVO.setMaxHeight(145);
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
		if (logger.isDebugEnabled()) {
			logger.debug(":::: modelControl final : " + modelControl);
		}
		session.put("modelControl", modelControl);									
	}
	
	
	/**
	 * Pantalla CatBo_ConsultarCasoDetalle
	 */
	@SuppressWarnings("unchecked")
	public String btnObtenerUsuariosResponsablesCasosClick() throws ApplicationException{
		try{
			PagedList pagedList = this.administracionCasosManager.getObtenerResponsable(pv_nmcaso_i,cdmatriz, start, limit);
            mUsuariosResponsablesList = pagedList.getItemsRangeList();
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
	
	
	/*
	@SuppressWarnings("unchecked")
	public String btnObtenerAtributosCasoClick() throws ApplicationException{
		try{
			
			mEstructuraCasoList = new ArrayList<CasoVO>();
			CasoVO casoVO = this.administracionCasosManager.get
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
	
	
	
	//ANALIZAR SI ESTE METODO SERVIRA
	/*public String obtenerArchivosPorMovimientoDeCaso()throws Exception{
		try{
            //PagedList pagedList = manager.obtenerArchivos(params..., start, limit);
            //mConsultaClienteList = pagedList.getItemsRangeList();
            //totalCount = pagedList.getTotalItems();                                                    
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
	}*/
	
	/**
	 * Pantalla CatBo_ConsultarMovimientosCaso
	 * 
	 * @return
	 * @throws Exception
	 */
	public String obtenerMovimientoPorCasos()throws Exception{
		try{
            PagedList pagedList = this.administracionCasosManager.obtenerMovimientos(pv_nmcaso_i, start, limit);
            mEstructuraMovCasoList = pagedList.getItemsRangeList();
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
	 * Metodo del action que obtiene un registro de atributos variables
	 * Pantalla emergente de CatBo_ConsultarMovimientosCaso
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	/*@SuppressWarnings("unchecked")
	public void obtenerAtributosDeSeccionDatosDelCaso()throws ApplicationException{
		@SuppressWarnings("unused")
		StringBuilder store = new StringBuilder();

		PagedList pagedList = manager.metodo();
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
				extJSComboBoxVO.setAllowBlank(extJSFieldVO.getAllowBlank());
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
				extJSComboBoxVO.setForceSelection(true);
				extJSComboBoxVO.setHiddenName("dsHidden");
				extJSComboBoxVO.setLabelSeparator("");
				extJSComboBoxVO.setMode("local");
				extJSComboBoxVO.setSelectOnFocus(true);
				extJSComboBoxVO.setSelectOnFocus(true);
				extJSComboBoxVO.setTriggerAction("all");
				extJSComboBoxVO.setTypeAhead(true);
				extJSComboBoxVO.setValueField("codigo");
				extJSComboBoxVO.setStore("crearStoreDatosAdicionales2('" + extJSFieldVO.getOtTabVal() + "', '" + extJSFieldVO.getName() + "', '" + extJSFieldVO.getValue() + "')");
				extJSComboBoxVO.setWidth("120");
				extJSComboBoxVO.setLazyRender(true);
				modelControl.add(extJSComboBoxVO);
			}
			if (extJSFieldVO.getXtype().equals("datefield")) {
				ExtJSDateFieldVO extJSDateFieldVO = new ExtJSDateFieldVO();
				extJSDateFieldVO.setAllowBlank(extJSFieldVO.getAllowBlank());
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
	}*/
	
	/**
	 * Pantalla Emergente Consultar o Ingresar Movimiento de CatBo_ConsultarMovimientosCaso
	 */
	@SuppressWarnings("unchecked")
	public String obtenerUsuariosResponsablesMovCaso()throws Exception{
	try{
        PagedList pagedList = administracionCasosManager.obtenerUsuariosResponsablesMovCaso(pv_nmovimiento_i, pv_nmcaso_i, start, limit);
        mListResponsablesMCaso = pagedList.getItemsRangeList();
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
	public String obtenerConjuntoUsuariosPorModulo() throws Exception{
		try{
            //PagedList pagedList = manager.usuariosPor Modulo(params..., start, limit);
            //mConsultaClienteList = pagedList.getItemsRangeList();
            //totalCount = pagedList.getTotalItems();                                                    
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
	
	
	/**
	 * Pantalla CatBo_ConsultaArchivos
	 */
	
	/*
	@SuppressWarnings("unchecked")
	public String btnObtenerArchivosMovimientoCasosClick() throws ApplicationException{
		try{
			PagedList pagedList = this.administracionCasosManager.obti
            mUsuariosResponsablesList = pagedList.getItemsRangeList();
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
	*/
	
	
	@SuppressWarnings("unchecked")
	public String obtenerPolizas() throws ApplicationException{
		try{
			/*PagedList pagedList = this.administracionCasosManager.obtenerPolizas(cdPerson,start, limit);
			mListPolizas = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();  */                                                  
            success = true;
            return SUCCESS;            
        }/*catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }*/catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	
/*	@SuppressWarnings("unchecked")
	public String obtenerAtributosVariablesAltaCaso()throws ApplicationException{
		try{
			PagedList pagedList = this.administracionCasosManager.obtenerAtributoSeccion(cdformatoorden, cdseccion, start, limit);
			mListAtributosVariables = pagedList.getItemsRangeList();
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
*/	
	
	@SuppressWarnings("unchecked")
	public String buscarListaUsuariosResponsables()throws ApplicationException{
		try{
			PagedList pagedList = this.administracionCasosManager.obtieneListaResponsablesCaso(cdProceso,cdmatriz, start, limit);
			mUsuariosResponsablesList = pagedList.getItemsRangeList();
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

	@SuppressWarnings("unchecked")
	public String cmdBuscarClientesClick()throws ApplicationException{
		try{
			PagedList pagedList = this.administracionCasosManager.obtenerClientes(cdelemento,cdideper, cdperson, dsnombre, dsapellido, dsapellido1, start, limit);
			mListConsultaClientes = pagedList.getItemsRangeList();
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
	
	@SuppressWarnings("unchecked")
	public String obtenerDatosAtributosVariables() {
		try {
    		PagedList pagedList = administracionCasosManager.obtieneSeccionesOrden(cdordentrabajo,start,limit);
    		mListaSeccionesOrden = pagedList.getItemsRangeList();
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
	 * Metodo que realiza una búsqueda en base a criterios de búsquedas
	 *  
	 * @param 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String obtenerListadoMovimientosCaso() throws Exception{
		try{
            PagedList pagedList = administracionCasosManager.obtenerMovimientos(nmcaso, start, limit);
            mListMovimientosCaso = pagedList.getItemsRangeList();
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
			//TableModelExport model =  administracionCasosManager.getModelMCaso(pv_nmcaso_i);			
			TableModelExport model =  administracionCasosManager2.obtenerMovimientoCasoExportar(pv_nmcaso_i);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String cmdBuscarReasignacionCasoClick() throws Exception{
		try{
            List pagedList = this.administracionCasosManager.obtenerReasignacionCaso(cdModulo);
            mEstructuraRsgCasoList = pagedList;
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
	public String cmdBuscarReasignacionCasoUsrClick() throws Exception{
		try{
            PagedList pagedList =this.administracionCasosManager.obtenerReasignacionCasoUsr(cdUsuario, start, limit);
			mEstructuraRsgCasoUsrList = pagedList.getItemsRangeList();
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
		//return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String cmdBuscarReasignacionCasoUsrRspnsblClick()throws ApplicationException{
		try{
			PagedList pagedList = this.administracionCasosManager.obtenerReasignacionCasoUsrRspnsbl(desUsuario,cdUsuarioOld, start, limit);
			mEstructuraRsgCasoUsrRspnsblList = pagedList.getItemsRangeList();
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
		//return SUCCESS;
	}
	
	/******Metodo que usa Cansulta de Caso el agregar*****/
	public String cmbTareaSelectConCaso() throws ApplicationException{		
		session.remove("modelControl");
		String resultado ="vacio"; 
		resultado = obtenerAtributosVariablesCasosConCaso();
		return resultado;
		}
	
	@SuppressWarnings("unchecked")
	public String obtenerAtributosVariablesCasosConCaso()throws ApplicationException{
	try{	
		if (logger.isDebugEnabled()) {
			logger.debug("-> obtenerAtributosVariablesCasos ");
		}
		@SuppressWarnings("unused")
		StringBuilder store = new StringBuilder();

		PagedList pagedList = administracionCasosManager.obtenerAtributoSeccion(cdformatoorden, cdseccion, start, limit);
		//mListAtributosVariables = pagedList.getItemsRangeList();
		totalCount = pagedList.getTotalItems();
		if (totalCount > 0){
					success = true;
					
					if (session.get("modelControl") != null) {
						session.remove("modelControl");
					}
					
					List lista = pagedList.getItemsRangeList();
					
					if (logger.isDebugEnabled()) {
						logger.debug(".. lista : " + lista);
					}
					
					///////////////////////////////Se obtienen los ComboControl
					List<ComboControl> comboControlElements = administracionCasosManager.obtenerAtributoSeccionComboControl(cdformatoorden, cdseccion);
					logger.debug("::::::: comboControlElements : " + comboControlElements);
					
					String[] backupTables = null;
			        if (comboControlElements != null && !comboControlElements.isEmpty()) {
			            int storeBackup = 0;
			            backupTables = new String[comboControlElements.size()];
			            for (ComboControl comboControl : comboControlElements) {
			                logger.debug("::::::::::::::: BackupTable : " + comboControl.getBackupTable());
			                backupTables[storeBackup++] = comboControl.getBackupTable();
			            }
			        }
			        
			        //SimpleComboUtils comboUtils = new SimpleComboUtils();
			        List<SimpleCombo> simpleComboElements = null;
			        ComboBoxlUtils comboUtils = new ComboBoxlUtils();
		
			        String url = ServletActionContext.getRequest().getContextPath()
			        	+ Constantes.URL_ACTION_COMBOS;
			        
			        simpleComboElements = comboUtils.getDefaultSimpleComboList(comboControlElements, url);
			        logger.debug("::::::: simpleComboElements : " + simpleComboElements);
					////////////////////////////////////////////////////////////////////////////////
					
					ExtJSLabelVO extJSLabelVO;
					ExtJSAtributosVO extJSAtributosVO = (ExtJSAtributosVO)lista.get(0);
		
					String aux = extJSAtributosVO.getCdSeccion();
					boolean anidado = true;
					
					extJSLabelVO = new ExtJSLabelVO();
					StringBuffer sb = new StringBuffer();
					StringBuffer sbHtml = new StringBuffer();
					sbHtml.append("<span class='x-form-item' style='font-weight:bold'>").append(extJSAtributosVO.getDsSeccion()).append("</span>");
					extJSLabelVO.setHtml(sbHtml.toString());
					sbHtml.delete(0, sbHtml.length());
					sb.append("{layout: 'form', colspan: 2, items: [").append(extJSLabelVO).append("]}\n");
					int contColumns = 0;
					
					for (int i=0; i<lista.size(); i++){
						extJSAtributosVO = (ExtJSAtributosVO)lista.get(i);
						if(!extJSAtributosVO.getCdSeccion().equals(aux)){				
							aux = extJSAtributosVO.getCdSeccion();
							extJSLabelVO = new ExtJSLabelVO();
							sbHtml.append("<span class='x-form-item' style='font-weight:bold'>").append(extJSAtributosVO.getDsSeccion()).append("</span>");
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
						}
						//extJSAtributosVO.setId(extJSAtributosVO.getCdFormatoOrden().concat("_").concat(extJSAtributosVO.getCdSeccion().concat("_").concat(extJSAtributosVO.getCdAtribu())));		
						//TODO analizar esto porque despues puede cambiar
						//El tipo de campo debe venir de la base de datos
						//Ahora esta hardcodeado para texfield o combo
						if(extJSAtributosVO.getOtTabVal().equals("")){
							extJSAtributosVO.setXtype("textfield");
						}else{
							//extJSAtributosVO.setXtype("combo");
							extJSAtributosVO.setXtype("combo");
						}
						
						if (extJSAtributosVO.getXtype().equals("textfield")){				
							extJSAtributosVO.setAnchor("90%");
							extJSAtributosVO.setAllowBlank("false");			
							extJSAtributosVO.setFieldLabel(extJSAtributosVO.getDsAtribu());
							StringBuffer _name = new StringBuffer();
							_name.append(extJSAtributosVO.getCdFormatoOrden()).append("_").append(extJSAtributosVO.getCdSeccion()).append("_").append(extJSAtributosVO.getCdAtribu());
							extJSAtributosVO.setName(_name.toString());
							extJSAtributosVO.setId(_name.toString());
							//extJSAtributosVO.setMinLength("1");
							extJSAtributosVO.setMaxLength("25");
							if(this.edit != null){if(this.edit.equals("0"))extJSAtributosVO.setDisabled("true");}				
							//if(this.edit.equals("0"))extJSAtributosVO.setReadOnly("true");
							//sb = new StringBuffer();
							sb.append("{layout: 'form', items:[").append(extJSAtributosVO).append("]}\n");
							//modelControl.add(sb.toString().replace("}\n{", "},\n{"));
						}									
						
						if (extJSAtributosVO.getXtype().equals("combo")) {
							if (logger.isDebugEnabled()) {
								logger.debug(".. extJSAtributosVO : " + extJSAtributosVO);
							}
							ExtJSComboBoxVO extJSComboBoxVO = new ExtJSComboBoxVO();
							
							extJSComboBoxVO.setAllowBlank("false");
							extJSComboBoxVO.setAnchor("90%");
							extJSComboBoxVO.setCdAtribu(extJSAtributosVO.getCdAtribu());
							extJSComboBoxVO.setCdSeccion(extJSAtributosVO.getCdSeccion());
							extJSComboBoxVO.setCdFormatoOrden(extJSAtributosVO.getCdFormatoOrden());
							if(this.edit != null){if(this.edit.equals("0"))extJSComboBoxVO.setDisabled("true");}
							//if(this.edit.equals("0"))extJSComboBoxVO.setReadOnly("true");
							extJSComboBoxVO.setFieldLabel(extJSAtributosVO.getDsAtribu());				
							StringBuffer _name = new StringBuffer();
							_name.append(extJSAtributosVO.getCdFormatoOrden()).append("_").append(extJSAtributosVO.getCdSeccion()).append("_").append(extJSAtributosVO.getCdAtribu());
							extJSComboBoxVO.setName(_name.toString());
							extJSComboBoxVO.setId(_name.toString());
							//extJSComboBoxVO.setValue(extJSAtributosVO.getValue());
							//extJSComboBoxVO.setMinLength(extJSAtributosVO.getMinLength());
							extJSComboBoxVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{value}.{label}\" class=\"x-combo-list-item\">{label}</div></tpl>");
							
							extJSComboBoxVO.setXtype("combo");
							extJSComboBoxVO.setEmpyText("Seleccione ...");
							extJSComboBoxVO.setDisplayField("label");
							extJSComboBoxVO.setForceSelection("true");
							extJSComboBoxVO.setHiddenName("dsHidden");
							extJSComboBoxVO.setLabelSeparator("");
							extJSComboBoxVO.setMode("local");
							extJSComboBoxVO.setSelectOnFocus("true");
							extJSComboBoxVO.setTriggerAction("all");
							extJSComboBoxVO.setTypeAhead("true");
							extJSComboBoxVO.setValueField("value");
							extJSComboBoxVO.setCdAgrupa(extJSAtributosVO.getCdAgrupa());
							if (logger.isDebugEnabled()) {
								logger.debug(":::::: OtTabVal : " + extJSAtributosVO.getOtTabVal());
								logger.debug(":::::: CdAgrupa : " + extJSAtributosVO.getCdAgrupa());
								logger.debug(":::::: CdAtribu : " + extJSAtributosVO.getCdAtribu());
							}
							////////////////Setting store and listeners
							if (StringUtils.isNotBlank(extJSComboBoxVO.getCdAgrupa())) {
								if (simpleComboElements != null && !simpleComboElements.isEmpty()) {
									for (SimpleCombo sc : simpleComboElements) {
										if (sc.getFieldLabel().equalsIgnoreCase(extJSAtributosVO.getDsAtribu())) {
											if (logger.isDebugEnabled()) {
												logger.debug(":::::: FieldLabel : " + sc.getFieldLabel());
												logger.debug(":::::: Store : " + sc.getStore());
												logger.debug(":::::: Listeners : " + sc.getListeners());
											}		
											extJSComboBoxVO.setStore(sc.getStore());
											
											if (sc.getListeners() != null) {
												extJSComboBoxVO.setListeners(sc.getListeners());
											}
										}
									}
								} else {
									anidado = false;
								}
							} else {
								anidado = false;
							}
							////////////////////////////////////////////////////////////////
							if (logger.isDebugEnabled()) {
								logger.debug(":::::: anidado : " + anidado);
							}
							if (!anidado) {
								extJSComboBoxVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{cd}.{ds}\" class=\"x-combo-list-item\">{ds}</div></tpl>");
								extJSComboBoxVO.setValueField("cd");
								extJSComboBoxVO.setDisplayField("ds");
								extJSComboBoxVO.setStore("crearStoreComboAtributosVariables('" + extJSAtributosVO.getOtTabVal() + "', '" + extJSAtributosVO.getName() + "', null)");
							}
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
					if (logger.isDebugEnabled()) {
						logger.debug(":::: modelControl final : " + modelControl);
					}
					session.put("modelControl", modelControl);									
					return "dinamico";
		
		}else{
			return "vacio";
		}
	}
	catch(ApplicationException e)
	{
		
		return "vacio";

    }catch( Exception e){
    	
    	return "vacio";
    }
	}

	
	/******FIN Metodo que usa Cansulta de Caso el agregar*****/
	
	
	@SuppressWarnings("unchecked")
	public String validaTiemposMatrizClik() throws ApplicationException{
    	String messageResult = "";
    	try{
    		messageResult = this.administracionCasosManager2.validarTiempoMatriz(cdmatriz);
			//pv_nmcaso_i, pv_nmfax_i
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


	public List<CasoVO> getMEstructuraRsgCasoUsrRspnsblList() {
		return mEstructuraRsgCasoUsrRspnsblList;
	}


	public void setMEstructuraRsgCasoUsrRspnsblList(
			List<CasoVO> estructuraRsgCasoUsrRspnsblList) {
		mEstructuraRsgCasoUsrRspnsblList = estructuraRsgCasoUsrRspnsblList;
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


	public String getPv_nmovimiento_i() {
		return pv_nmovimiento_i;
	}


	public String getWORIGEN() {
		return wORIGEN;
	}


	public void setWORIGEN(String worigen) {
		wORIGEN = worigen;
	}


	public void setPv_nmovimiento_i(String pv_nmovimiento_i) {
		this.pv_nmovimiento_i = pv_nmovimiento_i;
	}


	public void setAdministracionCasosManager2(
			AdministracionCasosManager2 administracionCasosManager2) {
		this.administracionCasosManager2 = administracionCasosManager2;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
