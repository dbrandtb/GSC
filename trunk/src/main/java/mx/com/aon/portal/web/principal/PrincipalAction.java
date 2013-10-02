package mx.com.aon.portal.web.principal;

import java.util.List;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.principal.ClienteVO;
import mx.com.aon.portal.model.principal.RolesVO;
import mx.com.aon.portal.service.principal.PrincipalManager;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.commons.lang.StringUtils;
/**
 * 
 * @author sergio.ramirez
 *
 */
public class PrincipalAction extends PrincipalCoreAction{

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -6514271768322006648L;
	private boolean success;
	
	private String claveDeConfiguracion;
	private String claveDeSeccion;
	
	private String dsElemen;
	private String dsRol;
	private String cliente;
	private String rolCopy;
	private String idCliente;
	private String idRol;
	private String idClienteNvo;
	private String idRolNvo;
	private String cdElemento;
	private PrincipalManager principalManager;
	private transient PrincipalManager principalManagerJdbcTemplate;
	
	/**
	 * metodo que se encarga de borrar una configuracion.
	 * @return
	 * @throws Exception
	 */
	public String borrarRol() throws Exception{
		logger.debug("claveDeConfiguracion:"+ claveDeConfiguracion);
		logger.debug("claveDeSeccion:"+claveDeSeccion);
		if(StringUtils.isNotBlank(claveDeConfiguracion)&& StringUtils.isNotBlank(claveDeSeccion)){
			try {
				String msg = principalManager.borrarConfig(claveDeConfiguracion, claveDeSeccion);
				success=true;
				addActionMessage(msg);
			} catch (ApplicationException ae) {
				addActionError(ae.getMessage());
				success = false;
			} catch (Exception e) {
				addActionError(e.getMessage());
				success = false;
			}
		}else{
			addActionError("Error tratando de borrar una configuracion");
			success=false;
		}
		return SUCCESS;
		
	}
	/**
	 * metodo que se encarga de copiar una configuracion.
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String copiarRoles() throws Exception{
		
		logger.debug("dsElemen-->"+dsElemen);
		logger.debug("dsRol-->"+ dsRol);
		logger.debug("dsSeccion-->"+cliente);
		logger.debug("dsTipo-->"+ rolCopy);
		
		List<ClienteVO> clienteUno = (List<ClienteVO>)session.get("LISTA_CLIENTES");
		for(ClienteVO clien : clienteUno){
			if(clien.getDescripcion().equals(dsElemen)){
				idCliente = clien.getClaveCliente();
			}else{
				success=false;
			}
		}
		List<RolesVO> rolesList = (List<RolesVO>)session.get("LISTA_ROLES"); 
		for(RolesVO ro : rolesList){
			if(ro.getDsRol().equals(dsRol)){
				idRol=ro.getCdRol();
			}else{
				success=false;
			}
		}
		List<ClienteVO> client = (List<ClienteVO>)session.get("LISTA_CLIENTES");
		for(ClienteVO clienVal : client){
			if(clienVal.getDescripcion().equals(cliente)){
				idClienteNvo=clienVal.getClaveCliente();
			}else{
				success=false;
			}
		}
		
		List<RolesVO> rolList = (List<RolesVO>) session.get("LISTA_ROLES");
		for(RolesVO ro : rolList){
			if(ro.getDsRol().equals(rolCopy)){
				idRolNvo=ro.getCdRol();
			}else{
				success=false;
			}
		}
		logger.debug("se asignan id para los atributos");
		logger.debug("idCliente--" + idCliente );
		logger.debug("idRol--" + idRol);
		logger.debug("idClienteNvo--"+idClienteNvo);
		logger.debug("idRolNvo--" + idRolNvo);
		
		principalManager.copiarConfig(idCliente, idRol, idClienteNvo,  idRolNvo);
		
		success=true;
		
		return SUCCESS;		
	}
	
	/**
	 * Valida si el usuario ha completado la configuración
	 * @return
	 * @throws ApplicationException
	 */
	public String cmdValidarConfiguracionCompleta () throws ApplicationException {
		try {
			String msg = principalManagerJdbcTemplate.configuracionCompleta(cdElemento);
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
	 * 
	 * @return
	 */
	public String getClaveDeConfiguracion() {
		return claveDeConfiguracion;
	}
	/**
	 * 
	 * @param claveDeConfiguracion
	 */
	public void setClaveDeConfiguracion(String claveDeConfiguracion) {
		this.claveDeConfiguracion = claveDeConfiguracion;
	}
	/**
	 * 
	 * @return
	 */
	public String getClaveDeSeccion() {
		return claveDeSeccion;
	}
	/**
	 * 
	 * @param claveDeSeccion
	 */
	public void setClaveDeSeccion(String claveDeSeccion) {
		this.claveDeSeccion = claveDeSeccion;
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
	public String getDsElemen() {
		return dsElemen;
	}
	/**
	 * 
	 * @param dsElemen
	 */
	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}
	/**
	 * 
	 * @return
	 */
	public String getDsRol() {
		return dsRol;
	}
	/**
	 * 
	 * @param dsRol
	 */
	public void setDsRol(String dsRol) {
		this.dsRol = dsRol;
	}
	/**
	 * 
	 * @return
	 */
	public String getCliente() {
		return cliente;
	}
	/**
	 * 
	 * @param cliente
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	/**
	 * 
	 * @return
	 */
	public String getRolCopy() {
		return rolCopy;
	}
	/**
	 * 
	 * @param rolCopy
	 */
	public void setRolCopy(String rolCopy) {
		this.rolCopy = rolCopy;
	}
	/**
	 * 
	 * @return
	 */
	public String getIdCliente() {
		return idCliente;
	}
	/**
	 * 
	 * @param idCliente
	 */
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	/**
	 * 
	 * @return
	 */
	public String getIdRol() {
		return idRol;
	}
	/**
	 * 
	 * @param idRol
	 */
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}
	/**
	 * 
	 * @return
	 */
	public String getIdClienteNvo() {
		return idClienteNvo;
	}
	/**
	 * 
	 * @param idClienteNvo
	 */
	public void setIdClienteNvo(String idClienteNvo) {
		this.idClienteNvo = idClienteNvo;
	}
	/**
	 * 
	 * @return
	 */
	public String getIdRolNvo() {
		return idRolNvo;
	}
	/**
	 * 
	 * @param idRolNvo
	 */
	public void setIdRolNvo(String idRolNvo) {
		this.idRolNvo = idRolNvo;
	}
	/**
	 * 
	 * @param principalManager
	 */
	public void setPrincipalManager(PrincipalManager principalManager) {
		this.principalManager = principalManager;
	}
	
	/**
	 * Metodo del padre (sin usar).
	 */
	public void prepare() throws Exception {
	
		
	}
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	public void setPrincipalManagerJdbcTemplate(
			PrincipalManager principalManagerJdbcTemplate) {
		this.principalManagerJdbcTemplate = principalManagerJdbcTemplate;
	}
	
	
	
	
	

}
