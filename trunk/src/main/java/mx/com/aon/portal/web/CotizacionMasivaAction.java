package mx.com.aon.portal.web;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.CotizacionMasivaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.CombosManager;
import mx.com.aon.portal.service.CotizacionManager;
import mx.com.aon.portal.service.PagedList;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.SessionAware;

public class CotizacionMasivaAction extends AbstractListAction implements  SessionAware{

	private static final long serialVersionUID = -3458680789943432654L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(CotizacionMasivaAction.class);

	
	private transient CotizacionManager cotizacionManager; 
	private transient CombosManager combosManager;
	
	private List<CotizacionMasivaVO> detalleCotizacionMasiva;
	private List comboTipoRamoList;
	
	//los parametros aqui tienen que tener el mismo nombre que en el js
	
	private String pv_cdelement;
	private String pv_asegura;
	private String pv_cdramo_i;
	private String pv_cdlayout;
	private String pv_fedesde_i;
	private String pv_fehasta_i;
	private String pv_cdusuari;
	private String pv_cdunieco;
	private String pv_cdramo;
	private String pv_estado;
	private String pv_nmpoliza;
	private String pv_nmsituac;
	private String pv_nmsuplem;
	private String pv_cdcia;
	private String pv_cdplan;
	private String pv_cdperpag;
	private String pv_cdperson;
	private String pv_fecha;
	
	@SuppressWarnings("unchecked")
    private transient Map session;
	
	private boolean success;
	
	/**
	 * String con contenido en formato json con resultado tomado en cmdCargarArchivo()
	 */
	private String resultadoUpload;
	/**
	 * String utilizado por el componente uploadpanel en cmdCargarArchivo()
	 */
	private String cmd;
	
	
	public String cmdBuscarCotizacionMasiva () throws ApplicationException {
		try {
			
			
			PagedList pagedList = cotizacionManager.buscarCotizacionesMasivas(pv_cdelement, pv_asegura, pv_cdramo_i, pv_cdlayout, pv_fedesde_i, pv_fehasta_i, start, limit);
			detalleCotizacionMasiva = pagedList.getItemsRangeList();
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

	public String cmdAprobarCotizacion () throws ApplicationException {
		try {
			//faltan parametros del aprobar
			String msg = cotizacionManager.aprobarCotizacion(pv_cdusuari, pv_cdunieco, pv_cdramo, pv_estado, pv_nmpoliza, pv_nmsituac, pv_nmsuplem, pv_cdelement, pv_cdcia, pv_cdplan, pv_cdperpag, pv_cdperson, pv_fecha);
			
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

	public String cmdBorrarCotizacion () throws ApplicationException {
		try {
			//faltan parametros del borrar
			String msg = cotizacionManager.borrarCotizacion(pv_cdusuari, pv_cdunieco, pv_cdramo, pv_estado, pv_nmpoliza, pv_nmsituac, pv_nmsuplem, pv_cdelement);
			
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

	public String cmdBuscarTipoRamo () throws ApplicationException {
		try {
			UserVO usuario = (UserVO)session.get("USUARIO");
			pv_cdelement = usuario.getEmpresa().getElementoId();
			comboTipoRamoList = combosManager.comboProductosTipoRamoCliente(pv_cdelement, null);
			
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
     * Metodo llamado para cargar el archivo a un server por medio de FTP
     * @return
     */
    public String cmdCargarArchivo() throws ApplicationException {
    	
    	logger.debug("Entrando a cargarArchivo()");
    	cmd = "upload";
    	
    	try{
    		MultiPartRequestWrapper multipartRequest = ((MultiPartRequestWrapper)ServletActionContext.getRequest());
    		
    		//Nombre del parametro
    		String nombreParametro = null; 
    		Enumeration<String> enumNombreParametros = multipartRequest.getFileParameterNames();
    		//Solo tomaremos un solo nombre de parametro
    		while( enumNombreParametros.hasMoreElements() ){nombreParametro = enumNombreParametros.nextElement();}
    		if(logger.isDebugEnabled()){
    			logger.debug("nombreParametro=" + nombreParametro);
    		}
    		
    		//Nombre del archivo
    		String nombreArchivo = "";
    		String []nombres = multipartRequest.getFileNames(nombreParametro);
    		for(int i=0; i<nombres.length;i++){nombreArchivo = nombres[i];}
    		if(logger.isDebugEnabled()){
    			logger.debug("nombreArchivo=" + nombreArchivo);
    		}
    		
        	//Obtener archivos(en este caso solo tomaremos el primero)
    		File[] files = multipartRequest.getFiles(nombreParametro);
    		if(logger.isDebugEnabled()){
    			logger.debug("files=" + files + "   Num.archivos=" + files.length );
    		}
    		
    		UserVO usuario = (UserVO)session.get("USUARIO");
    		pv_cdelement = usuario.getEmpresa().getElementoId();
    		if(logger.isDebugEnabled()){
    			logger.debug("pv_cdramo =" +  pv_cdramo);
    		}
    		String carga = null;
    		carga = cotizacionManager.cotizarMasiva(pv_cdelement, pv_cdramo, nombreArchivo, files[0]);
    		logger.debug("carga=" + carga);
    		
    		resultadoUpload = "{'success':true, actionMessages:['Archivo cargado. La carga es " + carga + "']}";
    		success = true;
			return SUCCESS;
    	} catch (ApplicationException ae) {
			success = false;
			resultadoUpload = "{'success':false,'errors':{'Error':'" + ae.getMessage() + "'}, actionErrors:['" +ae.getMessage() + "']}";
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			//e.printStackTrace();
			success = false;
			//resultadoUpload = "{'success':false,'errors':{'Error':'File upload error.'}}";
			resultadoUpload = "{'success':false,'errors':{'Error':'" + e.getMessage() + "'}, actionErrors:['" + e.getMessage() + "']}";
			addActionError(e.getMessage());
			return SUCCESS;
		}
    }

	public CotizacionManager getCotizacionManager() {
		return cotizacionManager;
	}
	/**
	 * Metodo utilizado por spring
	 * @param cotizacionManager
	 */
	public void setCotizacionManager(CotizacionManager cotizacionManager) {
		this.cotizacionManager = cotizacionManager;
	}

	public void setCombosManager(CombosManager combosManager) {
		this.combosManager = combosManager;
	}

	public List<CotizacionMasivaVO> getDetalleCotizacionMasiva() {
		return detalleCotizacionMasiva;
	}

	public void setDetalleCotizacionMasiva(
			List<CotizacionMasivaVO> detalleCotizacionMasiva) {
		this.detalleCotizacionMasiva = detalleCotizacionMasiva;
	}

	public List getComboTipoRamoList() {
		return comboTipoRamoList;
	}

	public void setComboTipoRamoList(List comboTipoRamoList) {
		this.comboTipoRamoList = comboTipoRamoList;
	}

	public String getPv_cdelement() {
		return pv_cdelement;
	}

	public void setPv_cdelement(String pv_cdelement) {
		this.pv_cdelement = pv_cdelement;
	}

	public String getPv_asegura() {
		return pv_asegura;
	}

	public void setPv_asegura(String pv_asegura) {
		this.pv_asegura = pv_asegura;
	}

	public String getPv_cdramo_i() {
		return pv_cdramo_i;
	}

	public void setPv_cdramo_i(String pv_cdramo_i) {
		this.pv_cdramo_i = pv_cdramo_i;
	}

	public String getPv_cdlayout() {
		return pv_cdlayout;
	}

	public void setPv_cdlayout(String pv_cdlayout) {
		this.pv_cdlayout = pv_cdlayout;
	}

	public String getPv_fedesde_i() {
		return pv_fedesde_i;
	}

	public void setPv_fedesde_i(String pv_fedesde_i) {
		this.pv_fedesde_i = pv_fedesde_i;
	}

	public String getPv_fehasta_i() {
		return pv_fehasta_i;
	}

	public void setPv_fehasta_i(String pv_fehasta_i) {
		this.pv_fehasta_i = pv_fehasta_i;
	}

	public String getPv_cdusuari() {
		return pv_cdusuari;
	}

	public void setPv_cdusuari(String pv_cdusuari) {
		this.pv_cdusuari = pv_cdusuari;
	}

	public String getPv_cdunieco() {
		return pv_cdunieco;
	}

	public void setPv_cdunieco(String pv_cdunieco) {
		this.pv_cdunieco = pv_cdunieco;
	}

	public String getPv_cdramo() {
		return pv_cdramo;
	}

	public void setPv_cdramo(String pv_cdramo) {
		this.pv_cdramo = pv_cdramo;
	}

	public String getPv_estado() {
		return pv_estado;
	}

	public void setPv_estado(String pv_estado) {
		this.pv_estado = pv_estado;
	}

	public String getPv_nmpoliza() {
		return pv_nmpoliza;
	}

	public void setPv_nmpoliza(String pv_nmpoliza) {
		this.pv_nmpoliza = pv_nmpoliza;
	}

	public String getPv_nmsituac() {
		return pv_nmsituac;
	}

	public void setPv_nmsituac(String pv_nmsituac) {
		this.pv_nmsituac = pv_nmsituac;
	}

	public String getPv_nmsuplem() {
		return pv_nmsuplem;
	}

	public void setPv_nmsuplem(String pv_nmsuplem) {
		this.pv_nmsuplem = pv_nmsuplem;
	}

	public String getPv_cdcia() {
		return pv_cdcia;
	}

	public void setPv_cdcia(String pv_cdcia) {
		this.pv_cdcia = pv_cdcia;
	}

	public String getPv_cdplan() {
		return pv_cdplan;
	}

	public void setPv_cdplan(String pv_cdplan) {
		this.pv_cdplan = pv_cdplan;
	}

	public String getPv_cdperpag() {
		return pv_cdperpag;
	}

	public void setPv_cdperpag(String pv_cdperpag) {
		this.pv_cdperpag = pv_cdperpag;
	}

	public String getPv_cdperson() {
		return pv_cdperson;
	}

	public void setPv_cdperson(String pv_cdperson) {
		this.pv_cdperson = pv_cdperson;
	}

	public String getPv_fecha() {
		return pv_fecha;
	}

	public void setPv_fecha(String pv_fecha) {
		this.pv_fecha = pv_fecha;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
	}

	public String getResultadoUpload() {
		return resultadoUpload;
	}

	public void setResultadoUpload(String resultadoUpload) {
		this.resultadoUpload = resultadoUpload;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
}
