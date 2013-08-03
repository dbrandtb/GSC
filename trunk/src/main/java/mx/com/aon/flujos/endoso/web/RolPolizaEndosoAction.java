package mx.com.aon.flujos.endoso.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.struts2.ServletActionContext;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.core.VariableKernel;
import mx.com.aon.flujos.endoso.service.EndosoManager;
import mx.com.aon.portal.model.AtributosVariablesVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.model.principal.RolesVO;
import mx.com.aon.utils.Constantes;
import mx.com.ice.kernel.constants.ConstantsKernel;
import mx.com.ice.services.exception.manager.ExceptionManager;
import mx.com.ice.services.to.Campo;
import mx.com.ice.services.to.ResultadoTransaccion;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;

import com.biosnet.ice.ext.elements.form.ComboControl;
import com.biosnet.ice.ext.elements.form.ExtElement;
import com.biosnet.ice.ext.elements.form.SimpleCombo;
import com.biosnet.ice.ext.elements.form.TextFieldControl;
import com.biosnet.ice.ext.elements.utils.ComboBoxlUtils;

public class RolPolizaEndosoAction extends PrincipalEndosoAction {

	private static final long serialVersionUID = -6085720799580305044L;
	private static final String NMSITUAC_NIVEL_POLIZA = "0";
	
	private EndosoManager endosoManager;
	private List<RolesVO> datosRol;
	private List<BaseObjectVO> datosPersona;
	private Map<String, String> parameters;
	private List<ExtElement> dext;

	private boolean success;
	private String cdRol;
	private String cdPerson;
	private String cdTipsit;
	private String cdAtribu;
	private String status;
	private String accion;
	private String numRespuesta;
    private String respuesta;
	private String cdNivel;
	
	@SuppressWarnings("unchecked")
	public String getComboRol() throws Exception {
		//Se obtienen datos de la variable global de session
		if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
		cdRamo = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
		if(cdTipsit==null || cdTipsit.equals("")){
			cdTipsit = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion());
		}
		//Si no obtiene valor de la variable global la obtendra de la session si ya se mando por url
		if((cdTipsit==null || cdTipsit.equals("")) || (cdNivel==null || cdNivel.equals(""))){
			if(session.containsKey("CDTIPSIT"))
				cdTipsit = (String) session.get("CDTIPSIT");
			if(session.containsKey("CDNIVEL"))
				cdNivel  = (String) session.get("CDNIVEL");
		}else{
			session.put("CDTIPSIT",cdTipsit);
			session.put("CDNIVEL",cdNivel);
		}
		
		logger.debug("cdtipsit :" + cdTipsit);
		logger.debug("cdNivel  :" + cdNivel);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cdramo", cdRamo);
		params.put("cdnivel", cdNivel);
		params.put("cdtipsit", cdTipsit);
		datosRol = catalogManager.getItemList("OBTIENE_LISTA_ROLES", params);

		if (logger.isDebugEnabled()) {
			logger.debug("datosRol :" + datosRol);
		}

		success = true;
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String getComboPersona() throws Exception {

		UserVO usuario = (UserVO) session.get("USUARIO");
		//Map<String, Object> params = new HashMap<String, Object>();
		//params.put("cdusuario", usuario.getUser());
		datosPersona = catalogManager.getItemList("OBTIENE_PERSONA_USUARIO", usuario.getUser());
		
		if (logger.isDebugEnabled()) {
			logger.debug("datosPersona :" + datosPersona);
		}

		success = true;
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String getPantallaRolPoliza() throws Exception {

		if (globalVarVO == null) {
            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
        }
		if(cdTipsit==null || cdTipsit.equals("")){
			cdTipsit = globalVarVO.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion());
		}
        cdUnieco 		= globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
        cdRamo 			= globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
        estado 			= globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
        nmPoliza 		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
        if(nmSituac==null || nmSituac.equals("")){
        	nmSituac = globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSituacion());
        }
        logger.debug("session.containsKey(CDTIPSIT) :"+session.containsKey("CDTIPSIT"));
        logger.debug("session.containsKey(NMSITUAC) :"+session.containsKey("NMSITUAC"));
        logger.debug("CDTIPSIT :"+cdTipsit);
        logger.debug("NMSITUAC :"+nmSituac);
		//Si no obtiene valor de la variable global la obtendra de la session si ya se mando por url
		if((cdTipsit==null || cdTipsit.equals("")) || (nmSituac==null || nmSituac.equals(""))){
			if(session.containsKey("CDTIPSIT"))
				cdTipsit = (String) session.get("CDTIPSIT");
			if(session.containsKey("NMSITUAC"))
				nmSituac = (String) session.get("NMSITUAC");
		}else{
			session.put("CDTIPSIT",cdTipsit);
			session.put("NMSITUAC",nmSituac);
		}
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("cdunieco", cdUnieco);
		param.put("cdramo", cdRamo);
		param.put("estado", estado);
		param.put("nmpoliza", nmPoliza);
		param.put("cdtipsit", cdTipsit);
		param.put("nmsituac", nmSituac);
		param.put("cdrol", cdRol);
		param.put("cdperson", cdPerson);
		param.put("cdatribu", cdAtribu);
		param.put("accion", accion);
		
		if (logger.isDebugEnabled()) {
			logger.debug("***getPantallaRolPoliza***");
			logger.debug("param			:"+param);
		}
		
		if(accion == null)
					return null;
		
			List<ExtElement> itemLista = new ArrayList<ExtElement>();
			List<ComboControl> comboElements = new ArrayList<ComboControl>();
			dext = new ArrayList<ExtElement>();
			
			if(accion.equalsIgnoreCase("I")){
				itemLista = endosoManager.getItems(param, "ENDOSOS_OBTIENE_DATOS_ROL_ITEMS");
				comboElements = endosoManager.getCombos(param, "ENDOSOS_OBTIENE_DATOS_ROL_COMBOS");
			}else if(accion.equalsIgnoreCase("U")){
				itemLista = endosoManager.getItems(param, "ENDOSOS_DATOS_ROL_ITEMS");
				comboElements = endosoManager.getCombos(param, "ENDOSOS_DATOS_ROL_COMBOS");
			}

			if (logger.isDebugEnabled()) {
				if (comboElements != null && !comboElements.isEmpty()) {
					for(ComboControl cb : comboElements){
						logger.debug("***BackupTable		:"+cb.getBackupTable());
					}
				}
			}
			
			if (logger.isDebugEnabled()){
				logger.debug("***itemLista		:"+itemLista);
            	logger.debug("***comboElements	:"+comboElements);
			}
			
            if(!itemLista.isEmpty()){
            	ComboBoxlUtils comboUtils = new ComboBoxlUtils();

				List<SimpleCombo> storeElements = comboUtils.getDefaultSimpleComboList(comboElements,
					ServletActionContext.getRequest().getContextPath()+
					Constantes.URL_ACTION_COMBOS);

                logger.debug("STORE DE LOS COMBOS" + storeElements);

                SimpleCombo simpleCombo = null;
                TextFieldControl textField = null;
                String id = null;

                for(ExtElement elements : itemLista){
                	if(elements instanceof SimpleCombo){
                		logger.debug("instanceof simplecombo");
                        simpleCombo = (SimpleCombo) elements;
                        id = simpleCombo.getId();

                        for (SimpleCombo scombo : storeElements) {

                            if (id.equals(scombo.getId())) {
                                if (!scombo.isHidden()) {
                                    dext.add(scombo);
                                }
                            }                                    
                        }

                	}else if (elements instanceof TextFieldControl){
                		logger.debug("instanceof textField");
                		textField = (TextFieldControl) elements;
                        
                        if (!textField.isHidden()) {
                            dext.add(textField);
                        }
                    }
                }

            }else{
            	if (logger.isDebugEnabled())
            		logger.debug("itemLista vacia ...");
            }
            
            if (dext == null || dext.isEmpty()) {
                TextFieldControl tfHidden = new TextFieldControl();
                tfHidden.setName("noFields");
                tfHidden.setId("noFields");
                tfHidden.setWidth(0);
                tfHidden.setHidden(true);
                tfHidden.setAllowBlank(true);
                
                if (dext == null) {
                    dext = new ArrayList<ExtElement>();
                }
                
                dext.add(tfHidden);
            }

			if (logger.isDebugEnabled())
				logger.debug("***DEXT	:"+dext);

		success = true;
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String getPolizaRol() throws Exception {
		
		String msg = null;

			if (globalVarVO == null) {
	            globalVarVO = (GlobalVariableContainerVO) session.get(Constantes.GLOBAL_VARIABLE_CONTAINER);
	        }
			cdTipsit		= globalVarVO.getValueVariableGlobal(VariableKernel.CodigoTipoSituacion());
	        cdUnieco 		= globalVarVO.getValueVariableGlobal(VariableKernel.UnidadEconomica());
	        cdRamo 			= globalVarVO.getValueVariableGlobal(VariableKernel.CodigoRamo());
	        estado 			= globalVarVO.getValueVariableGlobal(VariableKernel.Estado());
	        nmPoliza 		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroPoliza());
	        if(nmSituac==null || nmSituac.equals("")){
	        	nmSituac 		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSituacion());
	        }
	        nmSuplem		= globalVarVO.getValueVariableGlobal(VariableKernel.NumeroSuplemento());
			
			//Si no obtiene valor de la variable global la obtendra de la session si ya se mando por url
			if((cdTipsit==null || cdTipsit.equals("")) || (nmSituac==null || nmSituac.equals(""))){
				if(session.containsKey("CDTIPSIT"))
					cdTipsit = (String) session.get("CDTIPSIT");
				if(session.containsKey("NMSITUAC"))
					nmSituac = (String) session.get("NMSITUAC");
			}else{
				session.put("CDTIPSIT",cdTipsit);
				session.put("NMSITUAC",nmSituac);
			}
			
            if (parameters == null) {
                return null;
            }

            if (logger.isDebugEnabled()) {
                logger.debug("-> RolPolizaEndosoAction.getPolizaRol");
                logger.debug(":: getPolizaRol parameters : " + parameters);
                logger.debug(":: nmSituac : " + nmSituac);
            }
            
            if (NMSITUAC_NIVEL_POLIZA.equals(nmSituac)) {
            	Map<String, String> mapRolesEmision = (Map<String, String>) session.get("MAP_ROLES_EMISION");
                
                if (logger.isDebugEnabled()) {
                    logger.debug(":: mapRolesEmision : " + mapRolesEmision);
                }
                
                if (mapRolesEmision != null && !mapRolesEmision.isEmpty()) {
                    if (Constantes.DELETE_MODE.equalsIgnoreCase(parameters.get("accion")) &&
                    		mapRolesEmision.containsKey(parameters.get("cdrol"))) {
                        if (Constantes.SI.equalsIgnoreCase(parameters.get("swobliga")) &&
                                1 == Integer.parseInt(mapRolesEmision.get(parameters.get("cdrol")))) {
                            respuesta = "No se puede borrar este Rol, ya que es obligatorio";
                            success = false;
                            return SUCCESS;
                        }
                    }
                    
                    if (Constantes.INSERT_MODE.equalsIgnoreCase(parameters.get("accion"))) {
                        String nmaximo = mapRolesEmision.get(new StringBuilder().append("NMAXIMROL")
                                    .append(parameters.get("cdrol")).toString());
                        
                        if (logger.isDebugEnabled()) {
                            logger.debug(":::: nmaximo mapRolesEmision : " + nmaximo);
                        }
                                    
                        if (nmaximo != null && "1".equals(nmaximo) &&
                                1 == Integer.parseInt(mapRolesEmision.get(parameters.get("cdrol")))) {
                            respuesta = "No se puede insertar el Rol, ya que fue agregado anteriormente";
                            success = false;
                            return SUCCESS;
                        }
                    }
                }	
            } else {
            	Map<String, String> mapRoles = (Map<String, String>) session.get("MAP_ROLES");
                
                if (logger.isDebugEnabled()) {
                    logger.debug(":: mapRoles : " + mapRoles);
                }
                
                if (mapRoles != null && !mapRoles.isEmpty()) {
                    if (Constantes.DELETE_MODE.equalsIgnoreCase(parameters.get("accion")) &&
                    		mapRoles.containsKey(parameters.get("cdrol"))) {
                        if (Constantes.SI.equalsIgnoreCase(parameters.get("swobliga")) &&
                                1 == Integer.parseInt(mapRoles.get(parameters.get("cdrol")))) {
                            respuesta = "No se puede borrar este Rol, ya que es obligatorio";
                            success = false;
                            return SUCCESS;
                        }
                    }
                    
                    if (Constantes.INSERT_MODE.equalsIgnoreCase(parameters.get("accion"))) {
                        String nmaximo = mapRoles.get(new StringBuilder().append("NMAXIMROL")
                                    .append(parameters.get("cdrol")).toString());
                        
                        if (logger.isDebugEnabled()) {
                            logger.debug(":::: nmaximo mapRoles : " + nmaximo);
                        }
                                    
                        if (nmaximo != null && "1".equals(nmaximo) &&
                                1 == Integer.parseInt(mapRoles.get(parameters.get("cdrol")))) {
                            respuesta = "No se puede insertar el Rol, ya que fue agregado anteriormente";
                            success = false;
                            return SUCCESS;
                        }
                    }
                }	
            }
            
            Map<String, Object> params = new HashMap<String, Object>();
	        params.put("cdunieco", cdUnieco);
	        params.put("cdramo", cdRamo);
	        params.put("estado", estado);
	        params.put("nmpoliza", nmPoliza);
	        params.put("nmsituac", nmSituac);
	        params.put("nmsuplem", nmSuplem);
	        params.put("cdtipsit", cdTipsit);
	        params.put("cdrol", parameters.get("cdrol"));
	        params.put("cdperson", parameters.get("cdperson"));
            params.put("cdpersonant", cdPerson);
	        params.put("accion", parameters.get("accion"));
	        params.put("status", "V");
            
            if (logger.isDebugEnabled()) {
                logger.debug("::: persona anterior  : " + params.get("cdpersonant"));
                logger.debug("::: persona nueva     : " + params.get("cdperson"));      
            }
	        
			String[] separaDato = null;
			String valor 		= null;
			String cdatribu 	= null;
			String clave		= null;
			
			msg = (String) endosoManager.getEndPoint(params, "ENDOSOS_PMOVMPOLIPER");
			logger.debug("getPolizaRol ENDOSOS_PMOVMPOLIPER :" + msg);
			AtributosVariablesVO varVo = new AtributosVariablesVO();
			for (Object key : parameters.keySet()){
				clave = key.toString();
				valor = parameters.get(key);
					if(clave.contains("_")){
						logger.debug("clave 	:"+clave);
						logger.debug("valor 	:"+valor);						
						separaDato = clave.split("\\_");
						logger.debug("bloque	: "+separaDato[0]);
						logger.debug("cdAtribu	: "+separaDato[1]);
						cdatribu = separaDato[1];
						cdatribu = cdatribu.replaceAll("C", "");
						logger.debug("cdAtribu	clean: "+cdatribu);
						asignaOtValor(Integer.parseInt(cdatribu), valor, varVo);
					}
			}
			params.put("item", varVo);
			logger.debug("getPolizaRol parameters :" + parameters);
			numRespuesta = (String) endosoManager.getEndPoint(params,"ENDOSOS_GUARDA_FUNCION_ROL");
			logger.debug("getPolizaRol ENDOSOS_GUARDA_FUNCION_ROL :" + msg);
            
            if (logger.isDebugEnabled()) {
                logger.debug("::: accion           : " + params.get("accion"));
                logger.debug("::: numRespuesta     : " + numRespuesta);      
            }
			
			if(numRespuesta != null){
				if("2".equals(numRespuesta)){ 
                    success = true; 
                } else { 
                    respuesta = "No se pudo agregar la opcion";
                    success = false;
                }
			}else{
                respuesta = "No se pudo agregar la opcion";
				success = false;
			}

		return SUCCESS;
	}
	
	 /**
	  * Se encarga de asignar el valor de otValor a un solo atributo del objeto atribVarVO, 
	  * basándose en el valor de cdAtribu.
	  * @param cdAtribu Valor con el que decidimos qu&eacute; atributo se va a llenar
	  * @param otValor Valor que ser&aacute; asignado al VO
	  * @param atribVarVO El VO al que se le llenar&aacute; un atributo
	  */
	 private void asignaOtValor(int cdAtribu, String otValor, AtributosVariablesVO atribVarVO){
	        if (logger.isDebugEnabled()) {
	            logger.debug("::: asignaOtValor");
	            logger.debug(":: cdAtribu = " + cdAtribu);
	            logger.debug(":: otValor = " + otValor);
	        }
	  switch (cdAtribu) {
	   case 1:  atribVarVO.setOtValor01(otValor); break;
	   case 2:  atribVarVO.setOtValor02(otValor); break;
	   case 3:  atribVarVO.setOtValor03(otValor); break;
	   case 4:  atribVarVO.setOtValor04(otValor); break;
	   case 5:  atribVarVO.setOtValor05(otValor); break;
	   case 6:  atribVarVO.setOtValor06(otValor); break;
	   case 7:  atribVarVO.setOtValor07(otValor); break;
	   case 8:  atribVarVO.setOtValor08(otValor); break;
	   case 9:  atribVarVO.setOtValor09(otValor); break;
	   case 10: atribVarVO.setOtValor10(otValor); break;
	   case 11: atribVarVO.setOtValor11(otValor); break;
	   case 12: atribVarVO.setOtValor12(otValor); break;
	   case 13: atribVarVO.setOtValor13(otValor); break;
	   case 14: atribVarVO.setOtValor14(otValor); break;
	   case 15: atribVarVO.setOtValor15(otValor); break;
	   case 16: atribVarVO.setOtValor16(otValor); break;
	   case 17: atribVarVO.setOtValor17(otValor); break;
	   case 18: atribVarVO.setOtValor18(otValor); break;
	   case 19: atribVarVO.setOtValor19(otValor); break;
	   case 20: atribVarVO.setOtValor20(otValor); break;
	   case 21: atribVarVO.setOtValor21(otValor); break;
	   case 22: atribVarVO.setOtValor22(otValor); break;
	   case 23: atribVarVO.setOtValor23(otValor); break;
	   case 24: atribVarVO.setOtValor24(otValor); break;
	   case 25: atribVarVO.setOtValor25(otValor); break;
	   case 26: atribVarVO.setOtValor26(otValor); break;
	   case 27: atribVarVO.setOtValor27(otValor); break;
	   case 28: atribVarVO.setOtValor28(otValor); break;
	   case 29: atribVarVO.setOtValor29(otValor); break;
	   case 30: atribVarVO.setOtValor30(otValor); break;
	   case 31: atribVarVO.setOtValor31(otValor); break;
	   case 32: atribVarVO.setOtValor32(otValor); break;
	   case 33: atribVarVO.setOtValor33(otValor); break;
	   case 34: atribVarVO.setOtValor34(otValor); break;
	   case 35: atribVarVO.setOtValor35(otValor); break;
	   case 36: atribVarVO.setOtValor36(otValor); break;
	   case 37: atribVarVO.setOtValor37(otValor); break;
	   case 38: atribVarVO.setOtValor38(otValor); break;
	   case 39: atribVarVO.setOtValor39(otValor); break;
	   case 40: atribVarVO.setOtValor40(otValor); break;
	   case 41: atribVarVO.setOtValor41(otValor); break;
	   case 42: atribVarVO.setOtValor42(otValor); break;
	   case 43: atribVarVO.setOtValor43(otValor); break;
	   case 44: atribVarVO.setOtValor44(otValor); break;
	   case 45: atribVarVO.setOtValor45(otValor); break;
	   case 46: atribVarVO.setOtValor46(otValor); break;
	   case 47: atribVarVO.setOtValor47(otValor); break;
	   case 48: atribVarVO.setOtValor48(otValor); break;
	   case 49: atribVarVO.setOtValor49(otValor); break;
	   case 50: atribVarVO.setOtValor50(otValor); break;
	  }
	 }

    /**
     * @param extDatosElem 
     * @return
     * @throws ApplicationException 
     * @throws mx.com.ice.services.exception.ApplicationException 
     */
    @SuppressWarnings("unused")
	private String[] llenaCamposValoresMap(List<ExtElement> extDatosElem, String nmObjeto) 
            throws mx.com.ice.services.exception.ApplicationException, ApplicationException {
        if (logger.isDebugEnabled()){
            logger.debug("------> AccesoriosEndosoAction.llenaCamposValoresMap");
            logger.debug("------> extDatosElem : " + extDatosElem);
            logger.debug("------> nmObjeto     : " + nmObjeto);
        }
        
        String[] camposValores = new String[extDatosElem.size()];
        Campo[] campos = new Campo[extDatosElem.size()];
        ResultadoTransaccion rt = null;
        String idSesion = ServletActionContext.getRequest().getSession().getId();
        String parametro = null;
        String nombreCampo = null;        
        StringTokenizer st = null;
        
        for (int i = 0; i < campos.length; i++) {
            parametro = ((ExtElement)extDatosElem.get(i)).getId();
            st = new StringTokenizer(parametro, "_");
            st.nextToken();
            nombreCampo = new StringBuilder().append(st.nextToken()).append('|').append(nmObjeto).toString();
            logger.debug("   ::::: nombreCampo : " + nombreCampo);
            campos[i] = new Campo(nombreCampo, "");            
        }
        
        if (logger.isDebugEnabled()){
            for (int i = 0; i < campos.length; i++) {
                logger.debug("------> campos : " + campos[i]);  
            }
        }
        
        rt = ExceptionManager.manage(kernelManager.obtenerDatosBloque(
                idSesion, ConstantsKernel.BLOQUE_B6B, campos));
        if (rt != null) {
            for (int index = 0; index < campos.length; index++) {
                camposValores[index] = rt.getCampos()[index].getValor();  
            }
        }
        if (logger.isDebugEnabled()){
            for (int k = 0; k < campos.length; k++) {
                logger.debug(":::::::: camposValores ::: " + campos[k]);  
            }
        }
                
        return camposValores;
    }
	    
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(final boolean success) {
		this.success = success;
	}

	public EndosoManager getEndosoManager() {
		return endosoManager;
	}

	public void setEndosoManager(final EndosoManager endosoManager) {
		this.endosoManager = endosoManager;
	}

	/**
	 * @return the dext
	 */
	public List<ExtElement> getDext() {
		return dext;
	}

	/**
	 * @param dext
	 *            the dext to set
	 */
	public void setDext(List<ExtElement> dext) {
		this.dext = dext;
	}

	/**
	 * @return the cdRol
	 */
	public String getCdRol() {
		return cdRol;
	}

	/**
	 * @param cdRol
	 *            the cdRol to set
	 */
	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}

	/**
	 * @return the cdPerson
	 */
	public String getCdPerson() {
		return cdPerson;
	}

	/**
	 * @param cdPerson
	 *            the cdPerson to set
	 */
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	/**
	 * @return the datosRol
	 */
	public List<RolesVO> getDatosRol() {
		return datosRol;
	}

	/**
	 * @param datosRol
	 *            the datosRol to set
	 */
	public void setDatosRol(List<RolesVO> datosRol) {
		this.datosRol = datosRol;
	}

	/**
	 * @return the datosPersona
	 */
	public List<BaseObjectVO> getDatosPersona() {
		return datosPersona;
	}

	/**
	 * @param datosPersona
	 *            the datosPersona to set
	 */
	public void setDatosPersona(List<BaseObjectVO> datosPersona) {
		this.datosPersona = datosPersona;
	}

	/**
	 * @return the cdTipsit
	 */
	public String getCdTipsit() {
		return cdTipsit;
	}

	/**
	 * @param cdTipsit
	 *            the cdTipsit to set
	 */
	public void setCdTipsit(String cdTipsit) {
		this.cdTipsit = cdTipsit;
	}

	/**
	 * @return the cdAtribu
	 */
	public String getCdAtribu() {
		return cdAtribu;
	}

	/**
	 * @param cdAtribu the cdAtribu to set
	 */
	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the accion
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * @param accion the accion to set
	 */
	public void setAccion(String accion) {
		this.accion = accion;
	}

	/**
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getNumRespuesta() {
		return numRespuesta;
	}

	public void setNumRespuesta(String numRespuesta) {
		this.numRespuesta = numRespuesta;
	}

	public String getCdNivel() {
		return cdNivel;
	}

	public void setCdNivel(String cdNivel) {
		this.cdNivel = cdNivel;
	}

    /**
     * @return the respuesta
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * @param respuesta the respuesta to set
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

}