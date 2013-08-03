package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;


import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.BackBoneResultVO;
import mx.com.aon.portal.model.DomicilioVO;
import mx.com.aon.portal.model.ExtJSDateFieldVO;
import mx.com.aon.portal.model.ExtJSFieldVO;
import mx.com.aon.portal.model.ExtJS_ComboBoxVO;
import mx.com.aon.portal.model.FormConfigVO;
import mx.com.aon.portal.model.MetaDataVO;
import mx.com.aon.portal.model.PersonaCorporativoVO;
import mx.com.aon.portal.model.PersonaDatosAdicionalesVO;
import mx.com.aon.portal.model.PersonaDatosGeneralesVO;
import mx.com.aon.portal.model.PersonaVO;
import mx.com.aon.portal.model.RelacionesPersonaVO;
import mx.com.aon.portal.model.TelefonoVO;
import mx.com.aon.portal.model.UsuarioClaveVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.PersonasManager;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla de Administracion de Personas
 *   
 *   @extends ActionSupport
 * 
 */
@SuppressWarnings("serial")
public class PersonasAction extends ActionSupport implements SessionAware{
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient PersonasManager personasManager;
    private transient PersonasManager personasManagerJdbcTemplate;

    @SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PersonasAction.class);
	
	@SuppressWarnings("unchecked")
	private Map session;
	
	private List<PersonaVO> personaVO;

	private List<PersonaDatosGeneralesVO> datosGenerales;
	
	private List<DomicilioVO> datosDomicilio;
	
	private List<TelefonoVO> datosTelefono;
	
	private List<PersonaCorporativoVO> datosCorporativo;

	private List<PersonaDatosAdicionalesVO> datosAdicionales;
	
	private List<RelacionesPersonaVO> datosRelaciones;
	


	private MetaDataVO metaData = new MetaDataVO();

	private List<ExtJSFieldVO> fields;

	private String codigoPersona;
	private String codigoTipoPersona;
	private String numOrden;
	private String codigoAtributo;
	private int start;
	private int limit;
	private int totalCount;
	private boolean success;
	private String messageResult;
    private String cdperson;
    private String cdUsuari;
    private String dsUsuari;
    private String contrasenha;
    private String registGrid;
    private String nombre;
    private String cod;
    private List<UsuarioClaveVO> datosUsuarioClave;

    private List<UsuarioClaveVO> mUsuarioClave;

    public String getCdperson() {
		return cdperson;
	}

	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}

	/**
	 * Metodo que atiende una peticion para obtener una datos de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGetPersona () throws ApplicationException {
		try {
			personaVO = new ArrayList<PersonaVO>();
			PersonaVO persona = personasManagerJdbcTemplate.obtenerPersona(codigoTipoPersona, codigoPersona);
//			PersonaVO persona = personasManager.obtenerPersona(codigoTipoPersona, codigoPersona);
			personaVO.add(persona);
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

    /**
	 * Metodo que atiende una peticion para guardar o actualizar datos generales de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGuardarDatosGenerales() throws ApplicationException {
		try {
			if (datosGenerales != null || datosGenerales.size() > 0) {
				PersonaDatosGeneralesVO personaDatosGeneralesVO = datosGenerales.get(0);
				BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
				backBoneResultVO = personasManagerJdbcTemplate.guardarDatosGenerales(personaDatosGeneralesVO);
//				backBoneResultVO = personasManager.guardarDatosGenerales(personaDatosGeneralesVO);
				codigoPersona = backBoneResultVO.getOutParam();
				messageResult = backBoneResultVO.getMsgText();
				addActionMessage(messageResult);
			}
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
	
    /**
	 * Metodo que atiende una peticion para guardar o actualizar domicilio de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGuardarDomicilio () throws ApplicationException {
		try {
			for (int i=0; i<datosDomicilio.size(); i++) {
				DomicilioVO domicilioVO = datosDomicilio.get(i);
				messageResult = personasManager.guardarDomicilios(domicilioVO);
			}
			addActionMessage(messageResult);
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
	
    /**
	 * Metodo que atiende una peticion para obtener un domicilio de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public String cmdObtenerDomicilios () throws ApplicationException {
		try {
			PagedList pagedList = personasManager.getDomicilios(codigoPersona, start, limit);
			datosDomicilio = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
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

    /**
	 * Metodo que atiende una peticion para obtener un telefono de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public String cmdObtenerTelefonos () throws ApplicationException {
		try {
			PagedList pagedList = personasManager.getTelefono(codigoPersona, start, limit);
			datosTelefono = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
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

    /**
	 * Metodo que atiende una peticion para guardar o actualizar un telefono de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String guardarTelefonos () {
		try {
			for (int i=0; i<datosTelefono.size(); i++) {
				TelefonoVO telefonoVO = datosTelefono.get(i);
				messageResult = personasManager.guardarTelefonos(telefonoVO);
			}
			addActionMessage(messageResult);
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

    /**
	 * Metodo que atiende una peticion para obtener datos de persona corporativa
	 *  
	 * @return success
	 * 
	 * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public String cmdObtenerCorporativo () throws ApplicationException {
		try {
//            PagedList pagedList = personasManager.getCorporativo(codigoPersona, start, limit);
            PagedList pagedList = personasManagerJdbcTemplate.getCorporativo(codigoPersona, start, limit);
			datosCorporativo = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
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

    /**
	 * Metodo que atiende una peticion para guardar o actualizar datos persona corporativa
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGuardarCorporativo () throws ApplicationException {
		try {
			for (int i=0; i<datosCorporativo.size(); i++) {
				PersonaCorporativoVO personaCorporativoVO = datosCorporativo.get(i);
                messageResult = personasManagerJdbcTemplate.guardarCorporativo(personaCorporativoVO);
//                messageResult = personasManager.guardarCorporativo(personaCorporativoVO);
			}
			addActionMessage(messageResult);
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

    /**
	 * Metodo que atiende una peticion para borrar un domicilio de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdBorrarDomicilio () throws ApplicationException {
		try {
			messageResult = personasManager.borraDomicilio(codigoPersona, numOrden);
			addActionMessage(messageResult);
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

    /**
	 * Metodo que atiende una peticion para borrar telefono de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdBorrarTelefono () throws ApplicationException {
		try {
			messageResult = personasManager.borraTelefono(codigoPersona, numOrden);
			addActionMessage(messageResult);
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

    /**
	 * Metodo que atiende una peticion para obtener datos adicionales correspondientes a persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public String cmdObtenerDatosAdicionales () throws ApplicationException {

		try {
			@SuppressWarnings("unused")
			StringBuilder store = new StringBuilder();
			List modelControl = new ArrayList();

			if (session.get("modelControl") != null) {
				session.put("modelControl", null);
			}
			session.remove("modelControl");

			PagedList pagedList = personasManager.getDatosAdicionales(codigoTipoPersona, "", codigoPersona, start, 80);
			totalCount = pagedList.getTotalItems();
			success = true;
			modelControl = new ArrayList();
			List lista = pagedList.getItemsRangeList();
			FormConfigVO formConfigVO = new FormConfigVO();
			formConfigVO.setColumCount(1);
			formConfigVO.setLabelWidth(80);
			formConfigVO.setMsgTarget("side");
			fields = new ArrayList<ExtJSFieldVO>();
			
			for (int i=0; i<lista.size(); i++) {
				ExtJSFieldVO extJSFieldVO = (ExtJSFieldVO)lista.get(i);
				@SuppressWarnings("unused")
				String extJSField = new String();
				if (extJSFieldVO.getXtype().equals("textfield")) {
					extJSFieldVO.setAnchor("70%");
					extJSFieldVO.setWidth("170");
					//extJSFieldVO.setAllowBlank("false");
					extJSFieldVO.setHidden("false");
					//extJSFieldVO.setMinLength("1");
					//extJSFieldVO.setMaxLength("10");
					extJSFieldVO.setXtype("TEXT");
					modelControl.add(extJSFieldVO);
					fields.add(extJSFieldVO);
				}
				if (extJSFieldVO.getXtype().equals("combo")) {
					ExtJS_ComboBoxVO extJSComboBoxVO = new ExtJS_ComboBoxVO();
					extJSComboBoxVO.setAllowBlank(extJSFieldVO.getAllowBlank());//(extJSFieldVO.getAllowBlank())?"S":"N");
					extJSComboBoxVO.setAnchor("70%");
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
					extJSComboBoxVO.setWidth("170");
					extJSComboBoxVO.setLazyRender("true");
					extJSComboBoxVO.setMaxLength("999999");
					extJSComboBoxVO.setMinLength("1");
					//if (extJSComboBoxVO.getMinLength().equals("")) 
					//if (extJSComboBoxVO.getMaxLength().equals("")) 
					modelControl.add(extJSComboBoxVO);
					fields.add(extJSComboBoxVO);
				}
				if (extJSFieldVO.getXtype().equals("datefield")) {
					ExtJSDateFieldVO extJSDateFieldVO = new ExtJSDateFieldVO();
					extJSDateFieldVO.setAllowBlank(extJSFieldVO.getAllowBlank());//(extJSFieldVO.getAllowBlank())?"S":"N");
					extJSDateFieldVO.setCdAtribu(extJSFieldVO.getCdAtribu());
					extJSDateFieldVO.setFieldLabel(extJSFieldVO.getFieldLabel());
					extJSDateFieldVO.setId(extJSFieldVO.getId());
					extJSDateFieldVO.setName(extJSFieldVO.getName());
					extJSDateFieldVO.setValue(extJSFieldVO.getValue());
					extJSDateFieldVO.setAnchor("70%");
					extJSDateFieldVO.setMinLength(extJSFieldVO.getMinLength());
					extJSDateFieldVO.setXtype("FECHA");
					extJSDateFieldVO.setWidth("170");
					extJSDateFieldVO.setFormat("d/m/Y");
					modelControl.add(extJSDateFieldVO);
					fields.add(extJSDateFieldVO);
				}
			}
			metaData.setFormConfig(formConfigVO);
			metaData.setFields(fields);
			session.put("modelControl", modelControl);
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			ExtJSFieldVO extJSFieldVO = new ExtJSFieldVO();
			extJSFieldVO.setAnchor("70%");
			extJSFieldVO.setWidth("170");
			extJSFieldVO.setAllowBlank("false");
			extJSFieldVO.setHidden("false");
			extJSFieldVO.setMinLength("1");
			extJSFieldVO.setMaxLength("10");
			extJSFieldVO.setXtype("HIDDEN");
			fields = new ArrayList<ExtJSFieldVO>();
			fields.add(extJSFieldVO);
			FormConfigVO formConfigVO = new FormConfigVO();
			formConfigVO.setColumCount(1);
			formConfigVO.setLabelWidth(80);
			formConfigVO.setMsgTarget("side");
			metaData.setFormConfig(formConfigVO);
			metaData.setFields(fields);
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
    /**
	 * Metodo que atiende una peticion para guardar o actualizar datos adicionales de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGuardarDatosAdicionales () throws ApplicationException{
		try {
			if (datosAdicionales.size() > 0) {
				PersonaDatosAdicionalesVO personaDatosAdicionalesVO = (PersonaDatosAdicionalesVO)datosAdicionales.get(0);
				messageResult = personasManager.guardarDatosAdicionales(personaDatosAdicionalesVO);
			}
			success = true;
			addActionMessage(messageResult);
			return SUCCESS;
		}catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
    /**
	 * Metodo que atiende una peticion para actualizar datos persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdirEditarPersona () throws ApplicationException {
		//try {
			session.remove("modelControl");
			//cmdObtenerDatosAdicionales();
			success = true;
			return "getPersona";
		/*} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return "getPersona";
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return "getPersona";
		}*/
	}
	
	/**
	 * Metodo que atiende una peticion para agregar una persona. Recibe el
	 * tipo de persona juridica para poder obtener atributos adicionales
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public String cmdirAgregarPersona () throws ApplicationException {
		//try {
			session.remove("modelControl");
			//cmdObtenerDatosAdicionales();
			success = true;
			return "agregarPersona";
		/*} catch (ApplicationException ae) {
			success = true;
			//addActionError(ae.getMessage());
			return "agregarPersona";
		} catch (Exception e) {
			success = true;
			//addActionError(e.getMessage());
			return "agregarPersona";
		}*/
	}

	public String cmdGuardarRelaciones () {
		try {
			String msg = personasManager.guardarRelaciones(datosRelaciones);
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

	@SuppressWarnings("unchecked")
	public String cmdObtenerRelaciones () {
		try {
			PagedList pagedList = personasManager.getRelacionesPersona(codigoPersona, start, limit);
			datosRelaciones = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
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

	public String cmdBorrarPersona () {
		try {
			String msg = personasManagerJdbcTemplate.borrarPersona(codigoPersona);
			success = true;
			addActionMessage(msg);
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
	
	 /**
	 * Metodo que atiende una peticion para obtener una Clave de Usuario de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdObtenerUsuarioClave()throws Exception
	{
		try
		{
			mUsuarioClave=new ArrayList<UsuarioClaveVO>();
			UsuarioClaveVO usuarioClaveVO=personasManager.getUsuarioClave(cdperson);
			mUsuarioClave.add(usuarioClaveVO);
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
	 * Metodo que atiende una peticion para obtener una Clave de Usuario de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public String cmdGuardaUsuarioClave () throws ApplicationException {
		String messageResult = "";
	       try
	        {
	    	
	    	   UsuarioClaveVO usuarioClaveVO = new UsuarioClaveVO();

	    	   usuarioClaveVO.setCdUsuari(cdUsuari);
	    	   usuarioClaveVO.setRegistGrid(registGrid);
	    	   
	    	   messageResult = personasManager.guardaUsuarioClave(usuarioClaveVO);
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
	 * Metodo que atiende una peticion para obtener una Clave de Usuario de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public String cmdGuardaAsociarUsuarioClave () throws ApplicationException {
		String messageResult = "";
	       try
	        {
	    	   UsuarioClaveVO usuarioClaveVO = new UsuarioClaveVO();
	           
	    	   usuarioClaveVO.setCdUsuari(cdUsuari);
	    	   usuarioClaveVO.setCdPerson(cdperson);

	    	   
	    	   messageResult = personasManager.guardaAsociaUsuarioClave(usuarioClaveVO);
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
	 * Metodo que atiende una peticion para obtener una Clave de Usuario de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public String cmdGuardarCrearUsuariosPersonas () throws ApplicationException {
		String messageResult = "";
	       try
	        {
	    	   UsuarioClaveVO usuarioClaveVO = new UsuarioClaveVO();
	           
	    	   usuarioClaveVO.setCdUsuari(cdUsuari);
	    	   usuarioClaveVO.setContrasenha(contrasenha);
	    	   usuarioClaveVO.setDsUsuari(dsUsuari);
	    	   usuarioClaveVO.setCdPerson(cdperson);
	    	   
	    	   messageResult = personasManager.guardaCrearUsuarioClave(usuarioClaveVO);
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
	
	
	
	public String cmdIrPersonasClick()throws Exception{
		return "irPantallaPeronas";
	}
	public String getCodigoPersona() {
		return codigoPersona;
	}

	public void setCodigoPersona(String codigoPersona) {
		this.codigoPersona = codigoPersona;
	}

	public void setPersonasManager(PersonasManager personasManager) {
		this.personasManager = personasManager;
	}

	public List<PersonaVO> getPersonaVO() {
		return personaVO;
	}

	public void setPersonaVO(List<PersonaVO> personaVO) {
		this.personaVO = personaVO;
	}

	public List<PersonaDatosGeneralesVO> getDatosGenerales() {
		return datosGenerales;
	}

	public void setDatosGenerales(List<PersonaDatosGeneralesVO> datosGenerales) {
		this.datosGenerales = datosGenerales;
	}

	public String getCodigoTipoPersona() {
		return codigoTipoPersona;
	}

	public void setCodigoTipoPersona(String codigoTipoPersona) {
		this.codigoTipoPersona = codigoTipoPersona;
	}

	public List<DomicilioVO> getDatosDomicilio() {
		return datosDomicilio;
	}

	public void setDatosDomicilio(List<DomicilioVO> datosDomicilio) {
		this.datosDomicilio = datosDomicilio;
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

	public List<TelefonoVO> getDatosTelefono() {
		return datosTelefono;
	}

	public void setDatosTelefono(List<TelefonoVO> datosTelefono) {
		this.datosTelefono = datosTelefono;
	}

	public List<PersonaCorporativoVO> getDatosCorporativo() {
		return datosCorporativo;
	}

	public void setDatosCorporativo(List<PersonaCorporativoVO> datosCorporativo) {
		this.datosCorporativo = datosCorporativo;
	}

	public String getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(String numOrden) {
		this.numOrden = numOrden;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getCodigoAtributo() {
		return codigoAtributo;
	}

	public void setCodigoAtributo(String codigoAtributo) {
		this.codigoAtributo = codigoAtributo;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}

	public List<PersonaDatosAdicionalesVO> getDatosAdicionales() {
		return datosAdicionales;
	}

	public void setDatosAdicionales(List<PersonaDatosAdicionalesVO> datosAdicionales) {
		this.datosAdicionales = datosAdicionales;
	}

	public List<RelacionesPersonaVO> getDatosRelaciones() {
		return datosRelaciones;
	}

	public void setDatosRelaciones(List<RelacionesPersonaVO> datosRelaciones) {
		this.datosRelaciones = datosRelaciones;
	}


    public void setPersonasManagerJdbcTemplate(PersonasManager personasManagerJdbcTemplate) {
        this.personasManagerJdbcTemplate = personasManagerJdbcTemplate;
    }

	public MetaDataVO getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaDataVO metaData) {
		this.metaData = metaData;
	}

	public void setFields(List<ExtJSFieldVO> fields) {
		this.fields = fields;
	}

	
	public String getCdUsuari() {
		return cdUsuari;
	}

	public void setCdUsuari(String cdUsuari) {
		this.cdUsuari = cdUsuari;
	}

	public String getDsUsuari() {
		return dsUsuari;
	}

	public void setDsUsuari(String dsUsuari) {
		this.dsUsuari = dsUsuari;
	}

	public String getContrasenha() {
		return contrasenha;
	}

	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}

	public String getRegistGrid() {
		return registGrid;
	}

	public void setRegistGrid(String registGrid) {
		this.registGrid = registGrid;
	}

	public List<UsuarioClaveVO> getDatosUsuarioClave() {
		return datosUsuarioClave;
	}

	public void setDatosUsuarioClave(List<UsuarioClaveVO> datosUsuarioClave) {
		this.datosUsuarioClave = datosUsuarioClave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<UsuarioClaveVO> getMUsuarioClave() {
		return mUsuarioClave;
	}

	public void setMUsuarioClave(List<UsuarioClaveVO> usuarioClave) {
		mUsuarioClave = usuarioClave;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}
	
	
}