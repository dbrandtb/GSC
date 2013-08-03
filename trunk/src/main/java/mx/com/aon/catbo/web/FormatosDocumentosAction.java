package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.FormatoDocumentoVO;
import mx.com.aon.catbo.service.FormatosDocumentosManager;


import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Ayuda de Coberturas.
 * 
 */
@SuppressWarnings("serial")
public class FormatosDocumentosAction extends ActionSupport {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(FormatosDocumentosAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient FormatosDocumentosManager formatosDocumentosManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	private List<FormatoDocumentoVO> mEstructuraList;
	private String cdFormato;
	private String dsNomFormato;
	private String dsFormato;
   private String  cdCodigo;

	private boolean success;

		
	/**
	 * Metodo que elimina un registro de tabla Notificaciones.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult =  formatosDocumentosManager.borrarFormatosDocumentos(cdFormato);
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
	 * Metodo que obtiene una notificacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetClick()throws Exception
	{
		try
		{
			mEstructuraList=new ArrayList<FormatoDocumentoVO>();
			FormatoDocumentoVO formatoDocumentoVO=formatosDocumentosManager.getFormatosDocumentos(cdFormato);
			mEstructuraList.add(formatoDocumentoVO);
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
	 * Metodo que atualiza una ayuda de coberturas modificada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */
	public String cmdGuardarClick()throws Exception
	{
		String messageResult = "";
        try
        {
        	FormatoDocumentoVO formatoDocumentoVO = new FormatoDocumentoVO();
        	formatoDocumentoVO.setCdFormato(cdFormato);
        	formatoDocumentoVO.setDsNomFormato(dsNomFormato);
        	formatoDocumentoVO.setDsFormato(dsFormato);
        	messageResult = formatosDocumentosManager.guardarFormatosDocumentos(formatoDocumentoVO);
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
	

	public List<FormatoDocumentoVO> getMEstructuraList() {
		return mEstructuraList;
	}

	public void setMEstructuraList(List<FormatoDocumentoVO> estructuraList) {
		mEstructuraList = estructuraList;
	}

	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCdFormato() {
		return cdFormato;
	}

	public void setCdFormato(String cdFormato) {
		this.cdFormato = cdFormato;
	}

	public String getDsNomFormato() {
		return dsNomFormato;
	}

	public void setDsNomFormato(String dsNomFormato) {
		this.dsNomFormato = dsNomFormato;
	}

	public String getDsFormato() {
		return dsFormato;
	}

	public void setDsFormato(String dsFormato) {
		this.dsFormato = dsFormato;
	}

	public void setFormatosDocumentosManager(
			FormatosDocumentosManager formatosDocumentosManager) {
		this.formatosDocumentosManager = formatosDocumentosManager;
	}

	public String getCdCodigo() {
		return cdCodigo;
	}

	public void setCdCodigo(String cdCodigo) {
		this.cdCodigo = cdCodigo;
	}
	
	

}