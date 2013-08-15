package mx.com.aon.catbo.web;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.service.ConfigurarEncuestaManager;
import mx.com.aon.portal.model.FormConfigVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.model.AsignarEncuestaVO;
import mx.com.aon.catbo.model.CatboTimecVO;
import mx.com.aon.catbo.model.ConfigurarEncuestaVO;
import mx.com.aon.catbo.model.ExtJSFieldEncVO;
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.model.MetaDataEncVO;
import mx.com.aon.portal.util.Util;

import org.apache.log4j.Logger;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Notificaciones
 * 
 */
@SuppressWarnings("serial")
public class ListaConfigurarEncuestaDinamicoAction extends AbstractListAction{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaConfigurarEncuestaDinamicoAction.class);

	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient ConfigurarEncuestaManager configurarEncuestaManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	private List<ConfigurarEncuestaVO> mEstructuraList;
	private List<CatboTimecVO> mTiempoEstructuraList;
	private List<AsignarEncuestaVO> mAsignaEncuestaList;
	private List<ConfigurarEncuestaVO> mListValorEncuesta;

	private List<ExtJSFieldEncVO> fields;

	private MetaDataEncVO metaData = new MetaDataEncVO();
	
	private String dsUnieco;
    private String dsRamo;
    private String dsElemento;
    private String dsProceso;
    private String dsCampan;
    private String dsModulo;
    private String dsEncuesta;
	
    private String pv_dsunieco_i;
    private String pv_dsramo_i;
    private String pv_dselemento_i;
    private String pv_dsproceso_;
    private String pv_dscampan_i; 
    private String pv_dsmodulo_i;
    private String pv_dsencuesta_i;
    
    
    
    private String pv_nmconfig_i;
    private String pv_cdproceso_i;
    private String pv_cdcampan_i;
    private String pv_cdmodulo_i; 
    private String pv_cdencuesta_i;
    
    private String pv_cdcampana_i;
    private String pv_cdunieco_i;
    private String pv_cdramo_i;
    
    
    private String pv_estado_i;
    private String pv_nmpoliza_i;
    private String pv_dsperson_i;
    private String pv_dsusuario_i;
    
    private String pv_cdperson_i;
    private String pv_cdusuario_i;
    
    private String pv_dscampana_i;
    private String pv_dsproceso_i;
    private String pv_nmpoliex_i;
    
	//private boolean success;

	
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
	

	
	
	
	/**
	 * Metodo que realiza la busqueda de........ en base a
	 * en base  
	 * 
	 * 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	/*@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = this.configurarEncuestaManager.obtieneEncuesta(pv_dsunieco_i, pv_dsramo_i, pv_dselemento_i, pv_dsproceso_, pv_dscampan_i, pv_dsmodulo_i, pv_dsencuesta_i, start, limit);
            mEstructuraList = pagedList.getItemsRangeList();
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
	}*/

	
	/**
	 * Metodo que obtiene una Matriz de Asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	/*public String cmdGetConfigEncuestaClick()throws Exception
	{
		try
		{
			mEstructuraList = new ArrayList<ConfigurarEncuestaVO>();
			ConfigurarEncuestaVO configurarEncuestaVO = this.configurarEncuestaManager.getObtenerEncuestaRegistro(pv_nmconfig_i, pv_cdproceso_i, pv_cdcampan_i, pv_cdmodulo_i, pv_cdencuesta_i);
			mEstructuraList.add(configurarEncuestaVO);
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
	 * Metodo que obtiene una Matriz de Asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
/*	public String cmdObtenerAsignacionEncuestaClick()throws Exception
	{
		try
		{
			
			 PagedList pagedList =  this.configurarEncuestaManager.obtenerAsignacionEncuesta(pv_dsunieco_i, pv_dsramo_i, pv_estado_i, pv_nmpoliza_i, pv_dsperson_i, pv_dsusuario_i, start, limit);
			 mAsignaEncuestaList = pagedList.getItemsRangeList();
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
	
	
	/**
	 * Metodo que obtiene una Matriz de Asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	/*public String cmdObtenerValoresEncuestaClick()throws Exception
	{
		try
		{
			
			 PagedList pagedList =  this.configurarEncuestaManager.obtenerValorEncuesta(pv_dsunieco_i, pv_dsramo_i, pv_dsencuesta_i, pv_dscampana_i, pv_dselemento_i, pv_dsproceso_i, pv_nmpoliex_i, start, limit);
			 mEstructuraList = pagedList.getItemsRangeList();
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
	}*/
	
	
	/**
	 * Metodo que obtiene una Matriz de Asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
/*	public String cmdGetTiempoConfigEncuestaClick()throws Exception
	{
		try
		{
			mTiempoEstructuraList = new ArrayList<CatboTimecVO>();
			CatboTimecVO catboTimecVO = this.configurarEncuestaManager.getCatboTimec(pv_cdcampana_i, pv_cdunieco_i, pv_cdramo_i);
			mTiempoEstructuraList.add(catboTimecVO);
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
	
	
	
/*	public String cmdGetAsignacionEncuestaClick()throws Exception
	{
		try
		{
			mAsignaEncuestaList = new ArrayList<AsignarEncuestaVO>();
			AsignarEncuestaVO asignarEncuestaVO = this.configurarEncuestaManager.getObtenerAsignacionEncuesta(pv_nmconfig_i, pv_nmpoliza_i, pv_cdperson_i, pv_cdusuario_i);
			mAsignaEncuestaList.add(asignarEncuestaVO);
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
	 * Metodo que busca un conjunto de formatos documentos  
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	/*public String cmdExportarConfigEncuestaClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }

			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "ConfigEncuesta." + exportFormat.getExtension();
			TableModelExport model =  configurarEncuestaManager.getModelConfigEncuesta(pv_dsunieco_i, pv_dsramo_i, pv_dselemento_i, pv_dsproceso_, pv_dscampan_i, pv_dsmodulo_i, pv_dsencuesta_i);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}*/
	
	
	/**
	 * Metodo que busca un conjunto de formatos documentos  
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	/*public String cmdExportarAsignacionEncuestaClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
		      contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "AsigEncuesta." + exportFormat.getExtension();
			TableModelExport model =  configurarEncuestaManager.getModelAsignacionEncuesta(pv_dsunieco_i, pv_dsramo_i, pv_estado_i, pv_nmpoliza_i, pv_dsperson_i, pv_dsusuario_i);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
	*/
	
	/*public String cmdExportarValoresEncuestaClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
		      contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "ValoresEncuesta." + exportFormat.getExtension();
			TableModelExport model =  configurarEncuestaManager.getModelObtenerValoresEncuesta(pv_dsunieco_i, pv_dsramo_i, pv_dsencuesta_i, pv_dscampana_i, pv_dsmodulo_i, pv_dsproceso_i, pv_nmpoliza_i);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}*/
	
/*	public String cmdGetValorEncuestaEncClick()throws Exception
	{
		try
		{
			mListValorEncuesta = new ArrayList<ConfigurarEncuestaVO>();
			ConfigurarEncuestaVO configurarEncuestaVO = this.configurarEncuestaManager.getObtenerValorEncuestaEncabezado(pv_nmconfig_i, pv_cdunieco_i, pv_cdramo_i, pv_estado_i, pv_nmpoliza_i, pv_cdperson_i);
			mListValorEncuesta.add(configurarEncuestaVO);
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
	
	
	public String cmdGetValorEncuestaClick()throws Exception
	{
		//WrapperResultados resultado=new WrapperResultados();
		PagedList resultado = new PagedList();
		try
		{
			FormConfigVO formConfigVO = new FormConfigVO();
			formConfigVO.setColumCount(1);
			formConfigVO.setLabelWidth(80);
			formConfigVO.setMsgTarget("side");
			
			
			//mEstructuraList = new ArrayList<ConfigurarEncuestaVO>();
			
			resultado = this.configurarEncuestaManager.getObtenerValorEncuestaRegistro(pv_nmconfig_i, pv_cdunieco_i, pv_cdramo_i, pv_estado_i, pv_nmpoliza_i,pv_cdperson_i, start, limit);
			success = true;
            
			fields = new ArrayList<ExtJSFieldEncVO>();
			
			if(resultado.getTotalItems() != 0){
			
					List<?> elementos = resultado.getItemsRangeList();
					
					for (int i = 0; i < elementos.size(); i++) {
						ConfigurarEncuestaVO configurarEncuestaVO = (ConfigurarEncuestaVO)elementos.get(i);
						
						if(configurarEncuestaVO.getCdPregunta()!= null){
							
							//aca generar los textfield
							StringBuffer sbOnChange = new StringBuffer();
							String[] params = {"record"}; 
							
							ExtJSFieldEncVO extJSFieldEncVO = new ExtJSFieldEncVO();
							//extJSFieldEncVO.setAllowBlank((preguntaEncVO.getSwObliga().equals("S"))?"false":"true");
							extJSFieldEncVO.setId("ENCUESTA."+configurarEncuestaVO.getCdPregunta().toString()+configurarEncuestaVO.getDsPregunta()+"Id");
							extJSFieldEncVO.setMinLength("1");
							extJSFieldEncVO.setMaxLength("99");
							extJSFieldEncVO.setName("ENCUESTA."+configurarEncuestaVO.getCdPregunta().toString()+configurarEncuestaVO.getDsPregunta());
							extJSFieldEncVO.setHidden("false");
							extJSFieldEncVO.setDisabled("true");
							extJSFieldEncVO.setXtype("TEXT");
							extJSFieldEncVO.setAnchor("70%");
							extJSFieldEncVO.setWidth("170");
							//extJSFieldEncVO.setValue(configurarEncuestaVO.getDsPregunta());
							extJSFieldEncVO.setValue((configurarEncuestaVO.getOtValor()!=null)?configurarEncuestaVO.getOtValor():"");
							extJSFieldEncVO.setFieldLabel(configurarEncuestaVO.getDsPregunta().toString());
							//sbOnChange.append("recargarForm(").append(configurarEncuestaVO.getCdPregunta()).append(",").append(preguntaEncVO.getCdSecuencia()).append(",'").append(preguntaEncVO.getOtTapVal()).append("')");

							//extJSFieldEncVO.setOnChange(sbOnChange.toString());
							fields.add(extJSFieldEncVO); 
						}
						/*else
						{
							//aca generar el combo
							StringBuffer sbOnSelect = new StringBuffer();
							String[] params = {"record"}; 
							ExtJS_ComboBoxEncVO extJSComboBoxEncVO = new ExtJS_ComboBoxEncVO();
							extJSComboBoxEncVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{codigo}.{descripcion}\" class=\"x-combo-list-item\">{descripcion}</div></tpl>");
							extJSComboBoxEncVO.setAllowBlank((preguntaEncVO.getSwObliga().equals("S"))?"false":"true");
							extJSComboBoxEncVO.setXtype("LIST");
							extJSComboBoxEncVO.setAnchor("70%");
							extJSComboBoxEncVO.setFieldLabel(preguntaEncVO.getDsPregunta().toString());
							extJSComboBoxEncVO.setId(preguntaEncVO.getCdPregunta().toString()+"Id");
							//extJSComboBoxVO.setName();
							//extJSComboBoxVO.setValue();
							extJSComboBoxEncVO.setEmpyText("Seleccione ...");
							extJSComboBoxEncVO.setDisplayField("descripcion");
							extJSComboBoxEncVO.setForceSelection("true");
							extJSComboBoxEncVO.setHiddenName("dsHidden");
							extJSComboBoxEncVO.setLabelSeparator("");
							extJSComboBoxEncVO.setMode("local");
							extJSComboBoxEncVO.setSelectOnFocus("true");
							extJSComboBoxEncVO.setTriggerAction("all");
							extJSComboBoxEncVO.setTypeAhead("true");
							extJSComboBoxEncVO.setValueField("codigo");
							//extJSComboBoxVO.setStore(store);
							sbOnSelect.append("recargarForm(").append(preguntaEncVO.getCdPregunta()).append(",").append(preguntaEncVO.getCdSecuencia()).append(",'").append(preguntaEncVO.getOtTapVal()).append("')");
							extJSComboBoxEncVO.setOnSelect(sbOnSelect.toString());
							fields.add(extJSComboBoxEncVO);
						}*/
						
					}
					
			}
			
			metaData.setFormConfig(formConfigVO);
			metaData.setFields(fields);
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
	
	
	
	/*public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		ListaConfigurarEncuestaAction.logger = logger;
	}*/

	/*public ConfigurarEncuestaManager obtenConfigurarEncuestaManager() {
		return configurarEncuestaManager;
	}*/

	public void setConfigurarEncuestaManager(
			ConfigurarEncuestaManager configurarEncuestaManager) {
		this.configurarEncuestaManager = configurarEncuestaManager;
	}

	public List<ConfigurarEncuestaVO> getMEstructuraList() {
		return mEstructuraList;
	}

	public void setMEstructuraList(List<ConfigurarEncuestaVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public String getDsUnieco() {
		return dsUnieco;
	}

	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
	}

	public String getDsRamo() {
		return dsRamo;
	}

	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}

	public String getDsElemento() {
		return dsElemento;
	}

	public void setDsElemento(String dsElemento) {
		this.dsElemento = dsElemento;
	}

	public String getDsProceso() {
		return dsProceso;
	}

	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}

	public String getDsCampan() {
		return dsCampan;
	}

	public void setDsCampan(String dsCampan) {
		this.dsCampan = dsCampan;
	}

	public String getDsModulo() {
		return dsModulo;
	}

	public void setDsModulo(String dsModulo) {
		this.dsModulo = dsModulo;
	}

	public String getDsEncuesta() {
		return dsEncuesta;
	}

	public void setDsEncuesta(String dsEncuesta) {
		this.dsEncuesta = dsEncuesta;
	}

	/*public boolean isSuccess() {
		return success;
	}*/

	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getPv_nmconfig_i() {
		return pv_nmconfig_i;
	}


	public void setPv_nmconfig_i(String pv_nmconfig_i) {
		this.pv_nmconfig_i = pv_nmconfig_i;
	}


	public String getPv_cdproceso_i() {
		return pv_cdproceso_i;
	}


	public void setPv_cdproceso_i(String pv_cdproceso_i) {
		this.pv_cdproceso_i = pv_cdproceso_i;
	}


	public String getPv_cdcampan_i() {
		return pv_cdcampan_i;
	}


	public void setPv_cdcampan_i(String pv_cdcampan_i) {
		this.pv_cdcampan_i = pv_cdcampan_i;
	}


	public String getPv_cdmodulo_i() {
		return pv_cdmodulo_i;
	}


	public void setPv_cdmodulo_i(String pv_cdmodulo_i) {
		this.pv_cdmodulo_i = pv_cdmodulo_i;
	}


	public String getPv_cdencuesta_i() {
		return pv_cdencuesta_i;
	}


	public void setPv_cdencuesta_i(String pv_cdencuesta_i) {
		this.pv_cdencuesta_i = pv_cdencuesta_i;
	}


	public String getPv_dsunieco_i() {
		return pv_dsunieco_i;
	}


	public void setPv_dsunieco_i(String pv_dsunieco_i) {
		this.pv_dsunieco_i = pv_dsunieco_i;
	}


	public String getPv_dsramo_i() {
		return pv_dsramo_i;
	}


	public void setPv_dsramo_i(String pv_dsramo_i) {
		this.pv_dsramo_i = pv_dsramo_i;
	}


	public String getPv_dselemento_i() {
		return pv_dselemento_i;
	}


	public void setPv_dselemento_i(String pv_dselemento_i) {
		this.pv_dselemento_i = pv_dselemento_i;
	}


	public String getPv_dsproceso_() {
		return pv_dsproceso_;
	}


	public void setPv_dsproceso_(String pv_dsproceso_) {
		this.pv_dsproceso_ = pv_dsproceso_;
	}


	public String getPv_dscampan_i() {
		return pv_dscampan_i;
	}


	public void setPv_dscampan_i(String pv_dscampan_i) {
		this.pv_dscampan_i = pv_dscampan_i;
	}


	public String getPv_dsmodulo_i() {
		return pv_dsmodulo_i;
	}


	public void setPv_dsmodulo_i(String pv_dsmodulo_i) {
		this.pv_dsmodulo_i = pv_dsmodulo_i;
	}


	public String getPv_dsencuesta_i() {
		return pv_dsencuesta_i;
	}


	public void setPv_dsencuesta_i(String pv_dsencuesta_i) {
		this.pv_dsencuesta_i = pv_dsencuesta_i;
	}


	public String getFormato() {
		return formato;
	}


	public void setFormato(String formato) {
		this.formato = formato;
	}


/*	public InputStream getInputStream() {
		return inputStream;
	}*/


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	/*public ExportMediator getExportMediator() {
		return exportMediator;
	}*/


	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}


	public List<CatboTimecVO> getMTiempoEstructuraList() {
		return mTiempoEstructuraList;
	}


	public void setMTiempoEstructuraList(List<CatboTimecVO> tiempoEstructuraList) {
		mTiempoEstructuraList = tiempoEstructuraList;
	}


	public String getPv_cdcampana_i() {
		return pv_cdcampana_i;
	}


	public void setPv_cdcampana_i(String pv_cdcampana_i) {
		this.pv_cdcampana_i = pv_cdcampana_i;
	}


	public String getPv_cdunieco_i() {
		return pv_cdunieco_i;
	}


	public void setPv_cdunieco_i(String pv_cdunieco_i) {
		this.pv_cdunieco_i = pv_cdunieco_i;
	}


	public String getPv_cdramo_i() {
		return pv_cdramo_i;
	}


	public void setPv_cdramo_i(String pv_cdramo_i) {
		this.pv_cdramo_i = pv_cdramo_i;
	}


	public String getPv_estado_i() {
		return pv_estado_i;
	}


	public void setPv_estado_i(String pv_estado_i) {
		this.pv_estado_i = pv_estado_i;
	}


	public String getPv_nmpoliza_i() {
		return pv_nmpoliza_i;
	}


	public void setPv_nmpoliza_i(String pv_nmpoliza_i) {
		this.pv_nmpoliza_i = pv_nmpoliza_i;
	}


	public String getPv_dsperson_i() {
		return pv_dsperson_i;
	}


	public void setPv_dsperson_i(String pv_dsperson_i) {
		this.pv_dsperson_i = pv_dsperson_i;
	}


	public String getPv_dsusuario_i() {
		return pv_dsusuario_i;
	}


	public void setPv_dsusuario_i(String pv_dsusuario_i) {
		this.pv_dsusuario_i = pv_dsusuario_i;
	}


	/*public List<AsignarEncuestaVO> getMAsignaEncuestaList() {
		return mAsignaEncuestaList;
	}*/


	public void setMAsignaEncuestaList(List<AsignarEncuestaVO> asignaEncuestaList) {
		mAsignaEncuestaList = asignaEncuestaList;
	}


	public String getPv_cdperson_i() {
		return pv_cdperson_i;
	}


	public void setPv_cdperson_i(String pv_cdperson_i) {
		this.pv_cdperson_i = pv_cdperson_i;
	}


	public String getPv_cdusuario_i() {
		return pv_cdusuario_i;
	}


	public void setPv_cdusuario_i(String pv_cdusuario_i) {
		this.pv_cdusuario_i = pv_cdusuario_i;
	}


	public String getPv_dscampana_i() {
		return pv_dscampana_i;
	}


	public void setPv_dscampana_i(String pv_dscampana_i) {
		this.pv_dscampana_i = pv_dscampana_i;
	}


	public String getPv_dsproceso_i() {
		return pv_dsproceso_i;
	}


	public void setPv_dsproceso_i(String pv_dsproceso_i) {
		this.pv_dsproceso_i = pv_dsproceso_i;
	}


	public String getPv_nmpoliex_i() {
		return pv_nmpoliex_i;
	}


	public void setPv_nmpoliex_i(String pv_nmpoliex_i) {
		this.pv_nmpoliex_i = pv_nmpoliex_i;
	}


	public List<ConfigurarEncuestaVO> getMListValorEncuesta() {
		return mListValorEncuesta;
	}


	public void setMetaData(MetaDataEncVO metaData) {
		this.metaData = metaData;
	}





	public List<AsignarEncuestaVO> getMAsignaEncuestaList() {
		return mAsignaEncuestaList;
	}


	public MetaDataEncVO getMetaData() {
		return metaData;
	}


	public void setMListValorEncuesta(List<ConfigurarEncuestaVO> listValorEncuesta) {
		mListValorEncuesta = listValorEncuesta;
	}

	
	

	

	
	
	
}