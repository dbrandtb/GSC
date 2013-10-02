package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.portal.model.CatalogoLogicoVO;
import mx.com.aon.portal.service.ManttoCatalogosLogicosManager;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class RegistrosCatalogosLogicosAction extends ActionSupport {

	private static final long serialVersionUID = 198721333344477L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(RegistrosCatalogosLogicosAction.class);
	
	private transient ManttoCatalogosLogicosManager manttoCatalogosLogicosManager;
	private List<CatalogoLogicoVO> mRegistroCatalogoLogico;

	private String cdTabla;
	private String cdRegion;
	private String cdIdioma;
	private String codigo;
	private String dsDescripcion;
	private String descripcionLarga;
	private String etiqueta;
	private String descripcion;
	private String descripl;
	private String dsLabel;
	private boolean success;

	public String obtenerRegistroCatalogoLogico () throws ApplicationException {
		try{
			mRegistroCatalogoLogico = new ArrayList<CatalogoLogicoVO>();
			CatalogoLogicoVO vo = manttoCatalogosLogicosManager.obtenerRegistroCatalogosLogicos(cdTabla, cdRegion, cdIdioma, codigo, descripcion);
			mRegistroCatalogoLogico.add(vo);
			
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
	
	public String cmdGuardarRegistroClick () throws ApplicationException {
		try{
			String message = manttoCatalogosLogicosManager.insertarOActualizarRegistroCatalogoLogico(cdTabla, cdRegion, cdIdioma, codigo, descripcion, descripcionLarga, etiqueta);
			success = true;
			addActionMessage(message);
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
	
	public String cmdBorrarRegistroClick () throws ApplicationException {
		try{
			String message = manttoCatalogosLogicosManager.borrarRegistroCatalogoLogico(cdTabla, cdRegion, cdIdioma, codigo);
			success = true;
			addActionMessage(message);
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

	//SETTERS y GETTERS
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCdRegion() {
		return cdRegion;
	}

	public void setCdRegion(String cdRegion) {
		this.cdRegion = cdRegion;
	}

	public String getCdIdioma() {
		return cdIdioma;
	}

	public void setCdIdioma(String cdIdioma) {
		this.cdIdioma = cdIdioma;
	}

	public String getDsDescripcion() {
		return dsDescripcion;
	}

	public void setDsDescripcion(String dsDescripcion) {
		this.dsDescripcion = dsDescripcion;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public List<CatalogoLogicoVO> getMRegistroCatalogoLogico() {
		return mRegistroCatalogoLogico;
	}

	public void setMRegistroCatalogoLogico(
			List<CatalogoLogicoVO> registroCatalogoLogico) {
		mRegistroCatalogoLogico = registroCatalogoLogico;
	}

	public String getCdTabla() {
		return cdTabla;
	}

	public void setCdTabla(String cdTabla) {
		this.cdTabla = cdTabla;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripl() {
		return descripl;
	}

	public void setDescripl(String descripl) {
		this.descripl = descripl;
	}

	public String getDsLabel() {
		return dsLabel;
	}

	public void setDsLabel(String dsLabel) {
		this.dsLabel = dsLabel;
	}

	public void setManttoCatalogosLogicosManager(
			ManttoCatalogosLogicosManager manttoCatalogosLogicosManager) {
		this.manttoCatalogosLogicosManager = manttoCatalogosLogicosManager;
	}

	public String getDescripcionLarga() {
		return descripcionLarga;
	}

	public void setDescripcionLarga(String descripcionLarga) {
		this.descripcionLarga = descripcionLarga;
	}

}
