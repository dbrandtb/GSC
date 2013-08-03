package mx.com.aon.catbo.web;



import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONFunction;

import mx.com.aon.catbo.model.ExtJSFieldEncVO;
import mx.com.aon.catbo.model.ExtJS_ComboBoxEncVO;
import mx.com.aon.portal.model.CampoCatalogoVO;
import mx.com.aon.portal.model.ExtJSComboBoxVO;
import mx.com.aon.portal.model.FormConfigVO;
import mx.com.aon.portal.model.ItemVO;
import mx.com.aon.catbo.model.PreguntaEncVO;
import mx.com.aon.catbo.service.IngresarEncuestaManager;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.MetaDataEncVO;
import mx.com.aon.portal.util.WrapperResultados;
import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.catbo.model.RespuestaEncVO;

/**
 *   Action que atiende las peticiones de ingreso de preguntas de la encuesta.
 * 
 */
public class IngresarEncuestaAction extends AbstractListAction {

	private static final long serialVersionUID = 1997311537351L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(IngresarEncuestaAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient IngresarEncuestaManager ingresarEncuestaManager;
	
	private List<PreguntaEncVO> mEstructuraList;
	
	private MetaDataEncVO metaData = new MetaDataEncVO();
	
	private List camposFormAdd;
	private List<ExtJSFieldEncVO> fields;
	
	
	//private List<CampoCatalogoVO> fields;
	

	private String cdEncuesta;
	private String cdSecuencia;
	private String cdPregunta;
	private String otValor;
	
	private String nmConfig;
	private String cdUniEco;
	private String cdRamo;
	private String estado;
	private String nmPoliza;
	private String cdPerson;
	
	
	private String cdunieco;
	private String cdramo;
	private String nmpoliza;
	private String cdperson;
	private String nombrePersona;
	private String nmpoliex;
	private String dsunieco;
	private String dsramo;
	

	private List<ItemVO> respuesEnc;

	/**
	 * Obtiene las pregunta de la encuesta una a una
	 * 
	 * @return succes
	 * 
	 * @throws Exception
	 */
	public String cmdPreguntaEncuestaClick() throws Exception{
		WrapperResultados resultado=new WrapperResultados();
		try{
			
			FormConfigVO formConfigVO = new FormConfigVO();
			formConfigVO.setColumCount(1);
			formConfigVO.setLabelWidth(80);
			formConfigVO.setMsgTarget("side");
			
			resultado = ingresarEncuestaManager.obtenerPreguntaEncuesta(cdEncuesta, cdSecuencia);
			success = true;
			boolean bandCombo=false;
			boolean bandSiguiente=false;
			fields = new ArrayList<ExtJSFieldEncVO>();
			camposFormAdd= new ArrayList<ExtJSFieldEncVO>();
			//List camposFormAdd = new ArrayList();
			
			while (resultado.getItemList()!=null && bandCombo!=true && bandSiguiente!=true)
			//if(resultado.getCdSecuenciaPro()!=null)
			{
				List<?> elementos = resultado.getItemList();
				
				int j=0;
				int i=elementos.size();
				while (j<i && bandCombo==false)
				{
					PreguntaEncVO preguntaEncVO=(PreguntaEncVO)elementos.get(j);
					if (preguntaEncVO.getOtTapVal()==null||preguntaEncVO.getOtTapVal().equals(""))
					{
						//aca generar los textfield
						StringBuffer sbOnChange = new StringBuffer();
						//String[] params = {"record"}; 
						
						ExtJSFieldEncVO extJSFieldEncVO = new ExtJSFieldEncVO();
						extJSFieldEncVO.setAllowBlank((preguntaEncVO.getSwObliga().equals("S"))?"false":"true");
						extJSFieldEncVO.setId("ENCUESTA."+preguntaEncVO.getCdPregunta().toString());
						extJSFieldEncVO.setMinLength("1");
						extJSFieldEncVO.setMaxLength("99");
						extJSFieldEncVO.setName(preguntaEncVO.getCdPregunta().toString());
						extJSFieldEncVO.setHidden("false");
						extJSFieldEncVO.setXtype("TEXT");
						extJSFieldEncVO.setAnchor("70%");
						extJSFieldEncVO.setWidth("170");
						//extJSFieldEncVO.setValue("");
						extJSFieldEncVO.setValue((preguntaEncVO.getCdDefault().equals(""))?"":preguntaEncVO.getCdDefault());
						extJSFieldEncVO.setOtValor((preguntaEncVO.getOtTapVal()!=null)?preguntaEncVO.getOtTapVal():"");
						extJSFieldEncVO.setFieldLabel(preguntaEncVO.getDsPregunta().toString());
						/*if (j==(i-1)){
							sbOnChange.append("recargarForm(").append(preguntaEncVO.getCdPregunta()).append(",").append(preguntaEncVO.getCdSecuencia()).append(",'").append(preguntaEncVO.getOtTapVal()).append("')");
	
							extJSFieldEncVO.setOnChange(sbOnChange.toString());
						}*/
						fields.add(extJSFieldEncVO); 
						
						bandCombo=false;
						j++;
						
					}
					else
					{
						//aca generar el combo
					      String combitoId="ENCUESTA."+preguntaEncVO.getCdPregunta().toString();
					      StringBuffer sbOnSelect = new StringBuffer();
					      //String[] params = {"record"}; 
					      ExtJS_ComboBoxEncVO extJSComboBoxEncVO = new ExtJS_ComboBoxEncVO();
					      extJSComboBoxEncVO.setTpl("<tpl for=\".\"><div ext:qtip=\"{codigo}.{descripcion}\" class=\"x-combo-list-item\">{descripcion}</div></tpl>");
					      extJSComboBoxEncVO.setAllowBlank((preguntaEncVO.getSwObliga().equals("S"))?"false":"true");
					      extJSComboBoxEncVO.setXtype("LIST");
					      extJSComboBoxEncVO.setAnchor("70%");
					      extJSComboBoxEncVO.setFieldLabel(preguntaEncVO.getDsPregunta().toString());
					      extJSComboBoxEncVO.setId(combitoId);
					      extJSComboBoxEncVO.setName(preguntaEncVO.getCdPregunta().toString());
					      extJSComboBoxEncVO.setValue("");
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
					      extJSComboBoxEncVO.setLazyRender("true");
					      extJSComboBoxEncVO.setWidth("170");
					      extJSComboBoxEncVO.setMaxLength("999999");
					      extJSComboBoxEncVO.setMinLength("1");
					      extJSComboBoxEncVO.setStore("crearStorePreguntaEncuesta('" + preguntaEncVO.getOtTapVal() + "', '" + combitoId + "', '" + preguntaEncVO.getCdDefault() + "')");
					      //sbOnSelect.append("recargarForm(").append(preguntaEncVO.getCdPregunta()).append(",").append(preguntaEncVO.getCdSecuencia()).append(",'").append(preguntaEncVO.getOtTapVal()).append("')");
					      sbOnSelect.append("recargarForm(").append(preguntaEncVO.getCdPregunta()).append(",").append(preguntaEncVO.getCdSecuencia()).append(",'").append(preguntaEncVO.getOtTapVal()).append("')");
					      extJSComboBoxEncVO.setOnSelect(sbOnSelect.toString());
					      fields.add(extJSComboBoxEncVO);
					      bandCombo=true;
					      j++;
					     
					}
					
				}
				
				/*for ( j<elementos.size(); j++){ 
					PreguntaEncVO preguntaEncVO=(PreguntaEncVO)elementos.get(j);
					
					if (preguntaEncVO.getOtTapVal()==null||preguntaEncVO.getOtTapVal()=="")
					{
						//aca generar los textfield
						StringBuffer sbOnChange = new StringBuffer();
						String[] params = {"record"}; 
						
						ExtJSFieldEncVO extJSFieldEncVO = new ExtJSFieldEncVO();
						extJSFieldEncVO.setAllowBlank((preguntaEncVO.getSwObliga().equals("S"))?"false":"true");
						extJSFieldEncVO.setId("ENCUESTA."+preguntaEncVO.getCdPregunta().toString());
						extJSFieldEncVO.setMinLength("1");
						extJSFieldEncVO.setMaxLength("99");
						extJSFieldEncVO.setName("ENCUESTA."+preguntaEncVO.getCdPregunta().toString());
						extJSFieldEncVO.setHidden("false");
						extJSFieldEncVO.setXtype("TEXT");
						extJSFieldEncVO.setAnchor("70%");
						extJSFieldEncVO.setWidth("170");
						extJSFieldEncVO.setValue("");
						extJSFieldEncVO.setOtValor((preguntaEncVO.getOtTapVal()!=null)?preguntaEncVO.getOtTapVal():"");
						extJSFieldEncVO.setFieldLabel(preguntaEncVO.getDsPregunta().toString());
						sbOnChange.append("recargarForm(").append(preguntaEncVO.getCdPregunta()).append(",").append(preguntaEncVO.getCdSecuencia()).append(",'").append(preguntaEncVO.getOtTapVal()).append("')");

						extJSFieldEncVO.setOnChange(sbOnChange.toString());
						fields.add(extJSFieldEncVO); 
					}
					else
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
					}
					
				}*/
				if(!resultado.getCdSecuenciaPro().equals("")){
					resultado = ingresarEncuestaManager.obtenerPreguntaEncuesta(cdEncuesta, resultado.getCdSecuenciaPro());
				}else
				{
					bandSiguiente=true;
				}
			}
			metaData.setFormConfig(formConfigVO);
		
			//metaData.setFields(fields);
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

	/**
	 * Metodo que obtiene una la siguiente pregunta del ingresar encuesta.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdPreguntaEncuestaSigClick()throws Exception
	{
		cdSecuencia="-1";
		try
		{
			mEstructuraList=new ArrayList<PreguntaEncVO>();
			PreguntaEncVO preguntaEncVO=ingresarEncuestaManager.obtenerPreguntaEncuestaSig(cdEncuesta, cdPregunta, otValor);
			mEstructuraList.add(preguntaEncVO);
			cdSecuencia=preguntaEncVO.getCdSecuencia();
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
	
	public String cmdRespuestaEncuestaGuardarClick() throws Exception{
		String messageResult = "";
        try
        {
    		RespuestaEncVO respuestaEncVO = new RespuestaEncVO();
    		respuestaEncVO.setNmConfig(nmConfig);
    		respuestaEncVO.setCdUniEco(cdUniEco);
    		respuestaEncVO.setCdRamo(cdRamo);
    		respuestaEncVO.setEstado(estado);
    		respuestaEncVO.setNmPoliza(nmPoliza);
    		respuestaEncVO.setCdPerson(cdPerson);
    		respuestaEncVO.setCdEncuesta(cdEncuesta);
    		
    		messageResult = ingresarEncuestaManager.guardarPreguntaEncuesta(respuestaEncVO,respuesEnc);
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
	
	public String cmdIrIngresarEncuestasClick()throws Exception{
		return "irPantallaIngresarEncuestas";
	}
	
	public void setIngresarEncuestaManager(
			IngresarEncuestaManager ingresarEncuestaManager) {
		this.ingresarEncuestaManager = ingresarEncuestaManager;
	}


	public String getCdEncuesta() {
		return cdEncuesta;
	}


	public void setCdEncuesta(String cdEncuesta) {
		this.cdEncuesta = cdEncuesta;
	}


	public String getCdSecuencia() {
		return cdSecuencia;
	}


	public void setCdSecuencia(String cdSecuencia) {
		this.cdSecuencia = cdSecuencia;
	}

	public List<PreguntaEncVO> getMEstructuraList() {
		return mEstructuraList;
	}

	public void setMEstructuraList(List<PreguntaEncVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public String getCdPregunta() {
		return cdPregunta;
	}

	public void setCdPregunta(String cdPregunta) {
		this.cdPregunta = cdPregunta;
	}

	public String getOtValor() {
		return otValor;
	}

	public void setOtValor(String otValor) {
		this.otValor = otValor;
	}

	public void setFields(List<ExtJSFieldEncVO> fields) {
		this.fields = fields;
	}

	public MetaDataEncVO getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaDataEncVO metaData) {
		this.metaData = metaData;
	}

	public List<ItemVO> getRespuesEnc() {
		return respuesEnc;
	}

	public void setRespuesEnc(List<ItemVO> respuesEnc) {
		this.respuesEnc = respuesEnc;
	}

	public String getNmConfig() {
		return nmConfig;
	}

	public void setNmConfig(String nmConfig) {
		this.nmConfig = nmConfig;
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

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getCdunieco() {
		return cdunieco;
	}

	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}

	public String getCdramo() {
		return cdramo;
	}

	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}

	public String getNmpoliza() {
		return nmpoliza;
	}

	public void setNmpoliza(String nmpoliza) {
		this.nmpoliza = nmpoliza;
	}

	public String getCdperson() {
		return cdperson;
	}

	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}

	public String getNombrePersona() {
		return nombrePersona;
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public String getNmpoliex() {
		return nmpoliex;
	}

	public void setNmpoliex(String nmpoliex) {
		this.nmpoliex = nmpoliex;
	}

	public String getDsunieco() {
		return dsunieco;
	}

	public void setDsunieco(String dsunieco) {
		this.dsunieco = dsunieco;
	}

	public String getDsramo() {
		return dsramo;
	}

	public void setDsramo(String dsramo) {
		this.dsramo = dsramo;
	}

	public void setCamposFormAdd(List camposFormAdd) {
		this.camposFormAdd = camposFormAdd;
	}
	
	
	
}