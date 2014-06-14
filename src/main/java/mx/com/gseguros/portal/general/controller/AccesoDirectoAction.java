package mx.com.gseguros.portal.general.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.EmpresaVO;
import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.RamaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.LoginManager;
import mx.com.aon.portal.service.NavigationManager;
import mx.com.aon.portal.service.UsuarioManager;
import mx.com.aon.portal.service.principal.PrincipalManager;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.general.model.RolVO;
import mx.com.gseguros.utils.Constantes;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author HMLT
 */
public class AccesoDirectoAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 7885456537983878685L;

	private static org.apache.log4j.Logger logger =org.apache.log4j.Logger.getLogger(AccesoDirectoAction.class);
	
	public static String DEFAULT_DATE_FORMAT_PARAM = "defaultDateFormat";
    public static String DEFAULT_DECIMAL_SEPARATOR_PARAM = "defaultDecimalSeparator";
    public static String DEFAULT_DECIMAL_PRECISION_PARAM = "decimalPrecision";
    
    /**
     * Success property
     */
    private boolean success;
    
    private LoginManager loginManager;
	private NavigationManager navigationManager;
    private UsuarioManager usuarioManager;
    private transient PrincipalManager principalManagerJdbcTemplate;
    
    private HashMap<String,String> params;
    
    private String user;
    /**
     * Clave de usuario de captura que env&iacute;a GSeguros
     */
    private String e;
    private String codigoCliente;
    private String codigoRol;
    private String _codigoCliente = null;
    private boolean codigoValido;
    private int numReg;
    
    private List<RamaVO> listaRolCliente;
    private List<UserVO> userList;
    
    
    /**
     * Crea el usuario en sesion automaticamente
     * @return String result
     */
    public boolean instanciaUsuarioLigaDirecta(){
    	logger.debug(">>>> Entrando a instanciar usuario para Liga Directa ****");
        try {
        	creaSesionDeUsuario(user);
        
        	obtenRolesClientes();
			
			setCodigoCliente(codigoCliente);
			setCodigoRol(codigoRol);
			//setCodigoCliente("6442");
			//setCodigoRol("EJECUTIVOCUENTA");
			
			obtenCodigoTree();
			
			UserVO usuario=(UserVO) session.get("USUARIO");
	        logger.debug(">>>> usuario name: "+usuario.getName());
	        logger.debug(">>>> usuario user: "+usuario.getUser());
	        logger.debug(">>>> usuario empresa cdelemento id: "+usuario.getEmpresa().getElementoId());
	        logger.debug(">>>> usuario codigopersona: "+usuario.getCodigoPersona());
	        logger.debug(">>>> usuario claveUsuarioCaptura: "+usuario.getClaveUsuarioCaptura());
        }catch( Exception e){
            logger.error(">>>> Error en el proceso Interno", e);
            return false;
        }
        return true;
    }
    
    
    /**
     * Realiza un acceso directo a distintos m&oacute;dulos de la aplicaci&oacute;n 
     * @return
     * @throws Exception
     */
    public String accesoDirecto() throws Exception {
    	
    	String acceso = (String)params.get("acceso");
    	logger.info(new StringBuilder(">>>> Entrando a Acceso Directo: ").append(acceso).append(" con usuario: ").append(user));
    	
    	if("cotizador".equals(acceso) || "consultaPolizas".equals(acceso)){
    		instanciaUsuarioLigaDirecta();
    		logger.info(new StringBuilder(">>>> Redirigiendo a Acceso Directo: ").append(acceso));
    	} else {
    		logger.warn(new StringBuilder(">>>> No está definido el Acceso Directo: ").append(acceso));
    		acceso= "login";
    	}
    	
    	return acceso;
    }
    
    
    /**
     * Crea la sesi&oacute;n de usuario en el sistema 
     * @param usuario
     * @param claveUsuarioCaptura
     * @return
     * @throws Exception
     */
    private boolean creaSesionDeUsuario(String usuario) throws Exception {

		boolean exito = false;
		
		String dateFormat = ServletActionContext.getServletContext().getInitParameter(DEFAULT_DATE_FORMAT_PARAM);
        //String decimalSeparator = ServletActionContext.getServletContext().getInitParameter(DEFAULT_DECIMAL_SEPARATOR_PARAM);

		UserVO userVO = new UserVO();
		userVO.setUser(usuario);
		userVO = loginManager.obtenerDatosUsuario(usuario);

		logger.debug(">>>> DATOS USUARIO *****: " + userVO);

		//userVO.setDecimalSeparator(decimalSeparator);
		IsoVO isoVO = navigationManager.getVariablesIso(userVO.getUser());

		logger.debug(">>>> DATOS USUARIO222 *****: " + userVO);
		userVO.setClientFormatDate(isoVO.getClientDateFormat());
		userVO.setFormatDate(dateFormat);
		userVO.setDecimalSeparator(isoVO.getFormatoNumerico());
		// Se agrega la clave interna de GSeguros de usuario que captura:
		logger.debug("claveUsuarioCaptura=" + e);
		userVO.setClaveUsuarioCaptura(e);

		logger.debug(">>>> DATOS USUARIO333 *****: " + userVO);
		session.put(Constantes.USER, userVO);
		session.put("userVO", userVO);
		
		exito = true;
		return exito;
	}
    
    
    /**
     * Método que carga la pantalla que contiene el árbol de clientes y roles
     * @return success
     * @throws Exception
     */
    public String obtenRolesClientes() throws Exception {

        String retorno=SUCCESS;

        ///////////////////////////////////////////////////////////////////////////////
        ////// Crear un usuario y complementar su propiedades:                   //////
        ////// formato decha, formato numerico, idioma, tamanio paginacion grid, //////
        ////// cliente format date y region. Se guarda en sesión                 //////
        ///////////////////////////////////////////////////////////////////////////////
        UserVO usuario = (UserVO) session.get("USUARIO");
        session.put("MessageConf", null);
        listaRolCliente = usuarioManager.getClientesRoles(usuario.getUser());
        numReg = navigationManager.getNumRegistro(usuario.getUser());
        userList = usuarioManager.getAttributesUser(usuario.getUser());
		// Se agrega la clave interna de GSeguros de usuario que captura:
        Iterator<UserVO> itUserList = userList.iterator();
        while(itUserList.hasNext()) {
        	UserVO usVO = (UserVO)itUserList.next();
        	usVO.setClaveUsuarioCaptura(e);
        }
        
        logger.debug(">>>> Usuarios totales: "+(userList!=null?userList.size():"null")+ " pero solo el de sesion se complemento (ERROR)");
       
        complementaUsuario(usuario);
        session.put("USUARIO", usuario);
        ///////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////
        ////// Verificar los permisos del usuario //////
        ////////////////////////////////////////////////
        if (listaRolCliente==null || listaRolCliente.isEmpty())
        // sin permisos
        {
            //success = false; //jtezva -> inecesario, no es json
            session.clear();
            throw new Exception("Usted no posee un Rol Asociado por favor contacte al Administrador!");
        }
        else if((listaRolCliente.size() == 1) && (listaRolCliente.get(0).getChildren().length == 1))
        // solo tiene un cliente y un rol para ese cliente, se redirecciona la pagina
        {
            codigoRol = userList.get(0).getRoles().get(0).getObjeto().getValue();
            codigoCliente = userList.get(0).getEmpresa().getElementoId();
            _codigoCliente = userList.get(0).getEmpresa().getElementoId();
            boolean configuracionCompleta=false;
            try
            {
                principalManagerJdbcTemplate.configuracionCompleta(_codigoCliente);
                configuracionCompleta=true;
            }
            catch (Exception e)
            {
                configuracionCompleta=false;
                logger.error(e.getMessage(), e);
                addActionError(e.getMessage());
                //success = false; //jtezva -> inecesario, no es json
                session.put("MessageConf", e.getMessage()); //Se pone en sesión el mensaje a mostrar
                //return SUCCESS; //jtezva -> se sustituyo por la variable retorno
            }
            if(configuracionCompleta)
            {
                for (UserVO user : userList) {
                    usuario.setEmpresa(user.getEmpresa());
                    if (usuario.getRoles() != null) {
                        usuario.setRolActivo(usuario.getRoles().get(0));
                    } else {
                        usuario.setRolActivo(userList.get(0).getRoles().get(0));
                    }
                }
                complementaUsuario(userList.get(0));
                
                session.put("CARGA_USUARIO_COMPLETO", userList);
                obtenCodigoTree();
                retorno="load";
            }
        }
        else
        // tiene almenos dos clientes o dos roles distintos
        {
            logger.debug(">>>> listaRolCliente=" + listaRolCliente.size());
            for (RamaVO nodos : listaRolCliente) {
                if (nodos.getChildren() != null) {
                    nodos.setLeaf(false);
                }
            }
            session.put("CARGA_USUARIO_COMPLETO", userList);
            success = true;
            logger.debug(">>>> retorno=" + retorno);
        }
        ////////////////////////////////////////////////
        
        return retorno;
    }
    
    
    @SuppressWarnings("unchecked")
    public String obtenCodigoTree() throws Exception
    {
        String retorno=SUCCESS;
        logger.debug(">>>> Entrada metodo getCodigoTree");
        if (StringUtils.isNotBlank(codigoCliente) && StringUtils.isNotBlank(codigoRol))
        // Si se reciben los parametros del cliente
        {
            if (codigoCliente.trim().equals(codigoRol.trim()))
            // Si el cliente y el rol llegaron iguales desde el cliente
            {
                codigoValido=false;		
            }
            else
            // Buscamos el usuario con ese rol para ese cliente
            {
                //Se carga la lista de roles que se puso en sesion cuando se cargo
                //la vista del arbol con obtenRolesClientes()
                List<UserVO> listaUsuario = (List<UserVO>) session.get("CARGA_USUARIO_COMPLETO");
                
                EmpresaVO empresa = null;
                BaseObjectVO baseObjectVO = null;
                RolVO rolActivo = new RolVO();
                boolean rolActivado = false;
                for (UserVO userVO : listaUsuario) {
                    empresa = userVO.getEmpresa();
                    for (RolVO rols : userVO.getRoles()) {
                        baseObjectVO = rols.getObjeto();
                        if (empresa.getElementoId().trim().equals(codigoCliente.trim()) && baseObjectVO.getValue().trim().equals(codigoRol.trim())) {
                            rolActivo = rols;
                            rolActivado = true;
                            _codigoCliente = userVO.getEmpresa().getElementoId();
                            break;
                        }
                    }
                    if (rolActivado) {
                        if (rolActivo.getObjeto().getValue().equals("EJECUTIVOCUENTA")) {
                            userVO.setCodigoPersona("0");
                        }
                        userVO.setRolActivo(rolActivo);
                        session.put("USUARIO", userVO);
                        break;
                    }
                }
                try
                {
                    String msg = principalManagerJdbcTemplate.configuracionCompleta(_codigoCliente);
                    logger.debug(">>>> Configuración: " + msg);
                    codigoValido = true;
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    addActionError(e.getMessage());
                    codigoValido = false;
                    retorno= "confcompleta";
                }
            } // else
        } // if
        else
        // No se recibieron datos del cliente
        {
            codigoValido = false;
        }
        if(retorno.equals(SUCCESS))
        //si no intento guardar la configuracion de un usuario leido por parametros incorrectos
        //o si se cargo un usuario, se cargo correctamente
        {
            logger.debug(">>>> Salida metodo getCodigoTree");
            logger.debug(">>>> CODIGO VALIDO: " + codigoValido);
            UserVO usuario = (UserVO) session.get("USUARIO");
            complementaUsuario(usuario);
            session.put("USUARIO", usuario);
        }
        return retorno;
    }
    
    
    /**
     * Metodo que complementa los datos necesarios de un usuario
     * para que no se ciclen los jsp en taglibs.jsp:
     * String directorioIdioma = "/biosnet/"+((mx.com.aon.portal.model.UserVO)session.getAttribute("USUARIO")).getIdioma().getLabel();
     * @param usuario 
     */
    private void complementaUsuario(UserVO usuario) throws ApplicationException
    {
        IsoVO isoLocal = navigationManager.getVariablesIso(usuario.getUser());
        BaseObjectVO languague = new BaseObjectVO();
        languague.setValue(isoLocal.getCdIdioma());
        languague.setLabel(isoLocal.getLanguague());
        BaseObjectVO pais = new BaseObjectVO();
        pais.setValue(isoLocal.getPais());
        usuario.setPais(pais);
        String cdPais = usuario.getPais().getValue();
        logger.debug(">>>> El país del usuario es : " + cdPais);
        usuario.setFormatoFecha(isoLocal.getFormatoFecha());
        usuario.setFormatoNumerico(isoLocal.getFormatoNumerico());
        usuario.setIdioma(languague);
        usuario.setTamagnoPaginacionGrid(numReg);
        logger.debug(">>>> En seleccionaRolCliente: " + isoLocal.getClientDateFormat());
        usuario.setClientFormatDate(isoLocal.getClientDateFormat());
        //Agregados códigos de Idioma y Región
        //languague.setValue(iso.getCdIdioma());
        //usuario.setIdioma(languague);
        BaseObjectVO region = new BaseObjectVO();
        region.setValue(isoLocal.getCdRegion());
        usuario.setRegion(region);
    }
    

    //Getters and setters:
    
	public HashMap<String, String> getParams() {
		return params;
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getCodigoRol() {
		return codigoRol;
	}

	public void setCodigoRol(String codigoRol) {
		this.codigoRol = codigoRol;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	public void setNavigationManager(NavigationManager navigationManager) {
		this.navigationManager = navigationManager;
	}

	public List<RamaVO> getListaRolCliente() {
		return listaRolCliente;
	}

	public void setListaRolCliente(List<RamaVO> listaRolCliente) {
		this.listaRolCliente = listaRolCliente;
	}

	public List<UserVO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserVO> userList) {
		this.userList = userList;
	}

	public void setUsuarioManager(UsuarioManager usuarioManager) {
		this.usuarioManager = usuarioManager;
	}

	public boolean isCodigoValido() {
		return codigoValido;
	}

	public void setCodigoValido(boolean codigoValido) {
		this.codigoValido = codigoValido;
	}

	public int getNumReg() {
		return numReg;
	}

	public void setNumReg(int numReg) {
		this.numReg = numReg;
	}

	public void setPrincipalManagerJdbcTemplate(
			PrincipalManager principalManagerJdbcTemplate) {
		this.principalManagerJdbcTemplate = principalManagerJdbcTemplate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}


	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}
	
}