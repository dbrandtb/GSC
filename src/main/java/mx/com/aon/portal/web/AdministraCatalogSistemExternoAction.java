package mx.com.aon.portal.web;


import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.DescuentosManager;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.portal.service.AdministrarCatalogSistemaExternoManager;
import mx.com.aon.portal.model.AdministraCatalogoVO;


/**
 * Action que atiende las peticiones de la pantalla de descuento.
 *
 */
@SuppressWarnings("serial")
public class AdministraCatalogSistemExternoAction extends ActionSupport {
	
	private String codPais;
	private String hola;
	private String nmTabla;
	private String cdSistem;
	private String cdSistema;
	private String otClave;
	private String otValor;
	private String cdTabla;
	private String otClave2;
	private String otClave3;
	private String otClave4;
	private String otClave5;
	private String operacion;
	
	
	
	@SuppressWarnings("unchecked")
	private List descuentoVO;
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(AdministraCatalogSistemExternoAction.class);
	
	private transient AdministrarCatalogSistemaExternoManager administrarCatalogSistemaExternoManager;


    public void setDescuentosManager(DescuentosManager descuentosManager) {
        this.administrarCatalogSistemaExternoManager = administrarCatalogSistemaExternoManager;
    }

 	private boolean success;

	public String irAgregarDescuentoClick()throws Exception
	{
		return "agregarDescuento";
	}
	
	public String irEditarDescuentoClick()throws Exception
	{
		return "editarDescuento";
	}


	/**
	 * Metodo que elimina un descuento seleccionado del grid de la pantalla
	 * de descuento.
	 *
	 */
	
	 public String cmdBorrarClick () throws ApplicationException {
		 String messageResult ="";
		  try {
		   messageResult = administrarCatalogSistemaExternoManager.borrarTcataex(codPais, nmTabla, cdSistema, otClave, otValor, cdTabla);
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
	
	
	/*@SuppressWarnings("unchecked")  
	public String cmdBorrarClick() throws Exception
	{
		try
		{
			administrarCatalogSistemaExternoManager.borrarTcataex(codPais, nmTabla, cdSistem, otClave, otValor, cdTabla);//.borrarDescuento(cdDscto);
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
	
	public String regresar(){
		
		return "administrarEquivalenciaCatalogos";
	}
	
	public String guardar(){
		try
		{  AdministraCatalogoVO administraCatalogoVO= new AdministraCatalogoVO();
	      
	       administraCatalogoVO.setCodPais(codPais);
	       administraCatalogoVO.setCdSistema(cdSistema);	     
	       administraCatalogoVO.setOtClave(otClave);
	       administraCatalogoVO.setOtValor(otValor);
	       administraCatalogoVO.setOtClave2(otClave2);
	       administraCatalogoVO.setOtClave3(otClave3);
	       administraCatalogoVO.setOtClave4(otClave4);
	       administraCatalogoVO.setOtClave5(otClave5);	      
	       administraCatalogoVO.setCdTablaExt(cdTabla);
	       administraCatalogoVO.setOperacion(operacion);
			/*private String nmTabla;
	private String cdSistem;
	private String otClave;
	private String otValor;
	private String cdTabla;
	private String otClave1;
	private String otClave3;
	private String otClave4;
	private String otClave5;*/
			
			
			administrarCatalogSistemaExternoManager.guardarTcataex(administraCatalogoVO);//borrarTcataex(cdPais, nmTabla, cdSistem, otClave, otValor, cdTabla);//.borrarDescuento(cdDscto);
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
	/**private String cdPais;
	
	private String nmTabla;
	private String cdSistem;
	private String otClave;
	private String otValor;
	private String cdTabla;*/
	
    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}


   

	@SuppressWarnings("unchecked")
	public List getDescuentoVO() {
		return descuentoVO;
	}

	@SuppressWarnings("unchecked")
	public void setDescuentoVO(List descuentoVO) {
		this.descuentoVO = descuentoVO;
	}

	public String getCdPais() {
		return codPais;
	}

	public void setCdPais(String cdPais) {
		this.codPais = cdPais;
	}

	public String getNmTabla() {
		return nmTabla;
	}

	public void setNmTabla(String nmTabla) {
		this.nmTabla = nmTabla;
	}

	public String getCdSistem() {
		return cdSistema;
	}

	public void setCdSistem(String cdSistem) {
		this.cdSistema = cdSistem;
	}

	public String getOtClave() {
		return otClave;
	}

	public void setOtClave(String otClave) {
		this.otClave = otClave;
	}

	public String getOtValor() {
		return otValor;
	}

	public void setOtValor(String otValor) {
		this.otValor = otValor;
	}

	public String getCdTabla() {
		return cdTabla;
	}

	public void setCdTabla(String cdTabla) {
		this.cdTabla = cdTabla;
	}

	public String getOtClave1() {
		return otClave2;
	}

	public void setOtClave1(String otClave1) {
		this.otClave2 = otClave1;
	}

	public String getOtClave3() {
		return otClave3;
	}

	public void setOtClave3(String otClave3) {
		this.otClave3 = otClave3;
	}

	public String getOtClave4() {
		return otClave4;
	}

	public void setOtClave4(String otClave4) {
		this.otClave4 = otClave4;
	}

	public String getOtClave5() {
		return otClave5;
	}

	public void setOtClave5(String otClave5) {
		this.otClave5 = otClave5;
	}

	public String getOtClave2() {
		return otClave2;
	}

	public void setOtClave2(String otClave2) {
		this.otClave2 = otClave2;
	}

	public void setAdministrarCatalogSistemaExternoManager(
			AdministrarCatalogSistemaExternoManager administrarCatalogSistemaExternoManager) {
		this.administrarCatalogSistemaExternoManager = administrarCatalogSistemaExternoManager;
	}

	public String getCodPais() {
		return codPais;
	}

	public void setCodPais(String codPais) {
		this.codPais = codPais;
	}

	public String getCdSistema() {
		return cdSistema;
	}

	public void setCdSistema(String cdSistema) {
		this.cdSistema = cdSistema;
	}

	public String getHola() {
		return hola;
	}

	public void setHola(String hola) {
		this.hola = hola;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}


}

