
package mx.com.aon.portal.web;


import mx.com.aon.portal.model.CarritoComprasGuardarVO;
import mx.com.aon.portal.model.CarritoComprasVO;
import mx.com.aon.portal.model.FuncionalidadVO;
import mx.com.aon.portal.service.CarritoComprasManager;
import mx.com.aon.portal.service.FuncionalidadManager;
import mx.com.aon.portal.util.WrapperResultados;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import mx.com.aon.portal.service.PagedList;
import mx.com.gseguros.exception.ApplicationException;

import com.opensymphony.xwork2.ActionSupport;


/**
 *   Action que atiende las peticiones de que vienen de la pantalla Carrito de Compras
 * 
 */
@SuppressWarnings("serial")
public class FuncionalidadesPrivilegiosAction extends ActionSupport {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(FuncionalidadesPrivilegiosAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 * Este objeto no es serializable
	 *
	 */
	private transient FuncionalidadManager  funcionalidadManager;

	private String pv_cdelemento_i;
	private String  pv_cdsisrol_i; 
	private String  pv_cdusuario_i;
	private String pv_cdfunciona_i;
	private String  pv_cdopera_i;
	
	private String cdNivel;
	private String cdRol;
	private String cdUsuario;
	private String cdFunciona;
	
	private List <FuncionalidadVO> funcionalidades;
	private List  comboFuncionalidades;
	   
	/**
	 * Elimina un registro de la grilla previamente seleccionado
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarFuncionalidadPrivilegioClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = funcionalidadManager.borrarFuncionalidades(pv_cdelemento_i, pv_cdsisrol_i, pv_cdusuario_i, pv_cdfunciona_i, pv_cdopera_i);
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
	
	public String cmdGuardarFuncionalidadPrivilegioClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = funcionalidadManager.guardarFuncionalidades(funcionalidades);
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
	

    public String obtenerComboFuncionalidades() throws Exception {
        try {
        	logger.debug("into obtenerComboFuncionalidades");
        	
        	setComboFuncionalidades(funcionalidadManager.comboFuncionalidades());
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
    
  
	public String irConfiguraFuncionalidadesPrivilegiosClick(){
		return "configuracionFuncionalidadesPrivilegios";
	}
	
	public String irConfiguraFuncionalidadesPrivilegiosEditarClick(){
		return "configuracionFuncionalidadesPrivilegiosEditar";
	}
	
	public String irFuncionalidadesPrivilegiosClick(){
		return "funcionalidadesPrivilegios";
	}

	     
   private boolean success;
   public boolean getSuccess() {return success;}
   public void setSuccess(boolean success) {this.success = success;}
public String getPv_cdelemento_i() {
	return pv_cdelemento_i;
}
public void setPv_cdelemento_i(String pv_cdelemento_i) {
	this.pv_cdelemento_i = pv_cdelemento_i;
}
public String getPv_cdsisrol_i() {
	return pv_cdsisrol_i;
}
public void setPv_cdsisrol_i(String pv_cdsisrol_i) {
	this.pv_cdsisrol_i = pv_cdsisrol_i;
}
public String getPv_cdusuario_i() {
	return pv_cdusuario_i;
}
public void setPv_cdusuario_i(String pv_cdusuario_i) {
	this.pv_cdusuario_i = pv_cdusuario_i;
}
public String getPv_cdfunciona_i() {
	return pv_cdfunciona_i;
}
public void setPv_cdfunciona_i(String pv_cdfunciona_i) {
	this.pv_cdfunciona_i = pv_cdfunciona_i;
}
public String getPv_cdopera_i() {
	return pv_cdopera_i;
}
public void setPv_cdopera_i(String pv_cdopera_i) {
	this.pv_cdopera_i = pv_cdopera_i;
}




public FuncionalidadManager obtenFuncionalidadManager() {
	return funcionalidadManager;
}




public void setFuncionalidadManager(FuncionalidadManager funcionalidadManager) {
	this.funcionalidadManager = funcionalidadManager;
}

public List<FuncionalidadVO> getFuncionalidades() {
	return funcionalidades;
}

public void setFuncionalidades(List<FuncionalidadVO> funcionalidades) {
	this.funcionalidades = funcionalidades;
}

public String getCdNivel() {
	return cdNivel;
}

public void setCdNivel(String cdNivel) {
	this.cdNivel = cdNivel;
}

public String getCdRol() {
	return cdRol;
}

public void setCdRol(String cdRol) {
	this.cdRol = cdRol;
}

public String getCdUsuario() {
	return cdUsuario;
}

public void setCdUsuario(String cdUsuario) {
	this.cdUsuario = cdUsuario;
}

public String getCdFunciona() {
	return cdFunciona;
}

public void setCdFunciona(String cdFunciona) {
	this.cdFunciona = cdFunciona;
}

public void setComboFuncionalidades(List comboFuncionalidades) {
	this.comboFuncionalidades = comboFuncionalidades;
}

public List getComboFuncionalidades() {
	return comboFuncionalidades;
}

}