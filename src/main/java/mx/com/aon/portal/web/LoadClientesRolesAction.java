package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.EmpresaVO;
import mx.com.aon.portal.model.IsoVO;
import mx.com.aon.portal.model.RamaVO;
import mx.com.aon.portal.model.RolVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.NavigationManager;
import mx.com.aon.portal.service.UsuarioManager;
import mx.com.aon.portal.service.principal.PrincipalManager;
import mx.com.aon.utils.Constantes;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContextException;
/**
 * 
 * @author sergio.ramirez
 *
 */
public class LoadClientesRolesAction extends PrincipalCoreAction{

	private static final long serialVersionUID = -7704147817021446988L;
	private boolean success;
	private String user;
	private NavigationManager navigationManager;
    private UsuarioManager usuarioManager;
    private transient PrincipalManager principalManagerJdbcTemplate;
    private List<RamaVO> listaRolCliente;
	private List<UserVO> userList;
	private IsoVO iso;
	private String codigoCliente;
	private String codigoRol;
	private boolean codigoValido; 
	private int numReg;
	private int registrosEncontrados;
	private String _codigoCliente = null;

    public void setUsuarioManager(UsuarioManager usuarioManager) {
        this.usuarioManager = usuarioManager;
    }

    /**
	 * 
	 * @return success
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getCodigoTree() throws Exception{
		
		logger.debug("Entrada metodo getCodigoTree");
		
		if( StringUtils.isNotBlank(codigoCliente)&& StringUtils.isNotBlank(codigoRol) )
		{
			if( codigoCliente.trim().equals(codigoRol.trim()) )
			{
				codigoValido=false;		
			}
			else
			{
				List<UserVO> listaUsuario = new ArrayList<UserVO>();
				listaUsuario = (List<UserVO>) session.get("CARGA_USUARIO_COMPLETO");
				EmpresaVO empresa = null;
				BaseObjectVO baseObjectVO = null;
				RolVO rolActivo = new RolVO();
				boolean rolActivado = false;

				for(UserVO userVO : listaUsuario) 
				{
					empresa = userVO.getEmpresa();
					for(RolVO rols : userVO.getRoles())
					{
						baseObjectVO = rols.getObjeto();	
						if (empresa.getElementoId().trim().equals(codigoCliente.trim()) &&  baseObjectVO.getValue().trim().equals(codigoRol.trim()))
						{
							rolActivo = rols;
							rolActivado = true;
							_codigoCliente = userVO.getEmpresa().getElementoId();
							break;
						}
					}
					if (rolActivado) 
					{						
						if (rolActivo.getObjeto().getValue().equals("EJECUTIVOCUENTA"))
							userVO.setCodigoPersona("0");
						
						userVO.setRolActivo(rolActivo);
						session.put("USUARIO", userVO);
						break;
					}
				}		
				
				codigoValido=true;
				
				try 
				{
				String msg = principalManagerJdbcTemplate.configuracionCompleta(_codigoCliente);
				logger.debug("Configuración: " + msg);
				} catch (ApplicationContextException ae) {
					addActionError(ae.getMessage());
					success = false;
					codigoValido = false;
					return "confcompleta";
				} catch (Exception e) {
					addActionError(e.getMessage());
					success = false;
					codigoValido = false;
					return "confcompleta";
				}
			} // else
		} // if
		else
		{			
			codigoValido=false;
		}

		
		logger.debug("Salida metodo getCodigoTree");
		logger.debug("CODIGO VALIDO: " + codigoValido);
        //Seteamos  si tiene permisos para exportar
        UserVO usuario=(UserVO) session.get("USUARIO");
        try {
        	usuario.setAuthorizedExport(usuarioManager.isAuthorizedExport(usuario.getUser(),usuario.getRolActivo().getObjeto().getValue(),usuario.getEmpresa().getElementoId()));
        	logger.debug("EL usuario " + usuario.getUser() + (usuario.isAuthorizedExport()?" SI ":" NO ") + "esta autorizado a Exportar");
        } catch (Exception ex) {
        	logger.error("No se pudo setear si el usuario "+ usuario.getUser() +" esta autorizado a exportar" , ex);
        	usuario.setAuthorizedExport(false);
        }

        session.put("USUARIO", usuario);

		return SUCCESS;
	}
	
/**
 * 
 * @return success
 * @throws Exception
 */
	
	@SuppressWarnings("unchecked")
	public String getRolesClientes() throws Exception{
		
			String urlInicio = new StringBuilder().append("http://").append(ServletActionContext.getRequest().getLocalName())
				.append(":").append(ServletActionContext.getRequest().getLocalPort())
				.append(Constantes.URL_LOGIN).toString();
				
			session.put("URL_INICIO", urlInicio);
	
			UserVO usuario=(UserVO) session.get("USUARIO");
			
			session.put("MessageConf", null);
			registrosEncontrados = 0;
			listaRolCliente = usuarioManager.getClientesRoles(usuario.getUser());
			iso = navigationManager.getVariablesIso(usuario.getUser());
			numReg=navigationManager.getNumRegistro(usuario.getUser());
			userList = usuarioManager.getAttributesUser(usuario.getUser());

			BaseObjectVO languague = new BaseObjectVO();
			languague.setValue(iso.getCdIdioma()); 
			languague.setLabel(iso.getLanguague());
			BaseObjectVO pais = new BaseObjectVO();
			pais.setValue(iso.getPais());
			
			usuario.setPais(pais);
			String cdPais = usuario.getPais().getValue();
			logger.debug("El país del usuario es : " + cdPais);
			usuario.setFormatoFecha(iso.getFormatoFecha());
			usuario.setFormatoNumerico(iso.getFormatoNumerico());
			usuario.setIdioma(languague);
			usuario.setTamagnoPaginacionGrid(numReg);
			logger.debug("En seleccionaRolCliente: " + iso.getClientDateFormat());
			usuario.setClientFormatDate(iso.getClientDateFormat());
			
			//Agregados códigos de Idioma y Región
			//languague.setValue(iso.getCdIdioma());
			//usuario.setIdioma(languague);
			BaseObjectVO region = new BaseObjectVO();
			region.setValue(iso.getCdRegion());
			usuario.setRegion(region);
			

            session.put("USUARIO", usuario);
			
			if(listaRolCliente.isEmpty() || listaRolCliente == null){	
				// TODO esta mal, no deberia entrar al portal
				success = false;
				session.clear();
				registrosEncontrados = 0;
				throw new Exception("Usted no posee un Rol Asociado por favor contacte al Administrador!");
				
			} 
			else if ((listaRolCliente.size() == 1) && (listaRolCliente.get(0).getChildren().length == 1))  
			{
				//Setea los datos del user
				codigoRol = userList.get(0).getRoles().get(0).getObjeto().getValue();
				codigoCliente = userList.get(0).getEmpresa().getElementoId();
				_codigoCliente = userList.get(0).getEmpresa().getElementoId();
								
				try {
				String msg = principalManagerJdbcTemplate.configuracionCompleta(_codigoCliente);
				} catch (ApplicationContextException ae) {
					addActionError(ae.getMessage());
					success = false;
					session.put("MessageConf", ae.getMessage()); //Se pone en sesión el mensaje a mostrar
					return SUCCESS;
				} catch (Exception e) {
					addActionError(e.getMessage());
					success = false;
					session.put("MessageConf", e.getMessage()); //Se pone en sesión el mensaje a mostrar
					return SUCCESS;
				}
				for (UserVO user : userList) {
					usuario.setEmpresa(user.getEmpresa());
					if (usuario.getRoles() != null)
						usuario.setRolActivo(usuario.getRoles().get(0));
					else 
						usuario.setRolActivo(userList.get(0).getRoles().get(0));
				}
				session.put("USUARIO",usuario);
				success = true;
				registrosEncontrados = listaRolCliente.size();
				
				//BaseObjectVO languague = new BaseObjectVO();
				languague.setValue(iso.getLanguague()); 
				
				//BaseObjectVO pais = new BaseObjectVO();
				pais.setValue(iso.getPais());
				
				userList.get(0).setPais(pais);
				userList.get(0).setFormatoFecha(iso.getFormatoFecha());
				userList.get(0).setFormatoNumerico(iso.getFormatoNumerico());
				userList.get(0).setIdioma(languague);
				userList.get(0).setTamagnoPaginacionGrid(numReg);
				logger.debug("En seleccionaRolCliente: " + iso.getClientDateFormat());
				userList.get(0).setClientFormatDate(iso.getClientDateFormat());
				
				//Agregados códigos de Idioma y Región
				languague.setValue(iso.getCdIdioma());
				userList.get(0).setIdioma(languague);
				//BaseObjectVO region = new BaseObjectVO();
				region.setValue(iso.getCdRegion());
				userList.get(0).setRegion(region);
				
				
				//userList.get(0).etRegion().
				

				session.put("CARGA_USUARIO_COMPLETO", userList);
				getCodigoTree();
				return "load";
			}
			else
			{
				logger.debug("listaRolCliente=" + listaRolCliente.size());
				for (RamaVO nodos : listaRolCliente) {
					if(nodos.getChildren()!=null){
						nodos.setLeaf(false);
					}
				}
				session.put("CARGA_USUARIO_COMPLETO", userList);
				success = true;
			}			
		return SUCCESS;
	}
	

	/**
	 * 
	 * @return
	 */
	public List<RamaVO> getListaRolCliente() {
		return listaRolCliente;
	}
	/**
	 * 
	 * @param listaRolCliente
	 */
	public void setListaRolCliente(List<RamaVO> listaRolCliente) {
		this.listaRolCliente = listaRolCliente;
	}
	/**
	 * 
	 * @param navigationManager
	 */
	public void setNavigationManager(NavigationManager navigationManager) {
		this.navigationManager = navigationManager;
	}
	/**
	 * 
	 * @return
	 */
	public String getUser() {
		return user;
	}
	/**
	 * 
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * 
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * 
	 * @return
	 */
	public IsoVO getIso() {
		return iso;
	}
	/**
	 * 
	 * @param iso
	 */
	public void setIso(IsoVO iso) {
		this.iso = iso;
	}
	/**
	 * 
	 * @return
	 */
	public String getCodigoCliente() {
		return codigoCliente;
	}
	/**
	 * 
	 * @param codigoCliente
	 */
	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	/**
	 * 
	 * @return
	 */
	public String getCodigoRol() {
		return codigoRol;
	}
	/**
	 * 
	 * @param codigoRol
	 */
	public void setCodigoRol(String codigoRol) {
		this.codigoRol = codigoRol;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isCodigoValido() {
		return codigoValido;
	}
	/**
	 * 
	 * @param codigoValido
	 */
	public void setCodigoValido(boolean codigoValido) {
		this.codigoValido = codigoValido;
	}

	
	/**
	 *@see
	 *Metodo implementado solo por herencia.
	 */
	public void prepare() throws Exception {
	}
	/**
	 * 
	 * @return
	 */
	public int getNumReg() {
		return numReg;
	}
	/**
	 * 
	 * @param numReg
	 */
	public void setNumReg(int numReg) {
		this.numReg = numReg;
	}
	/**
	 * 
	 * @return
	 */
	public List<UserVO> getUserList() {
		return userList;
	}
	/**
	 * 
	 * @param userList
	 */
	public void setUserList(List<UserVO> userList) {
		this.userList = userList;
	}

	public int getRegistrosEncontrados() {
		return registrosEncontrados;
	}

	public void setRegistrosEncontrados(int registrosEncontrados) {
		this.registrosEncontrados = registrosEncontrados;
	}

	public void setPrincipalManagerJdbcTemplate(
			PrincipalManager principalManagerJdbcTemplate) {
		this.principalManagerJdbcTemplate = principalManagerJdbcTemplate;
	}	
}

