package mx.com.gseguros.portal.siniestros.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.general.service.CatalogosManager;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;
import mx.com.gseguros.utils.Constantes;

import org.apache.log4j.Logger;

public class AjustesMedicosSiniestrosAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 1L;
	private Logger            logger           = Logger.getLogger(AjustesMedicosSiniestrosAction.class);
	private DateFormat        renderFechas     = new SimpleDateFormat("dd/MM/yyyy");

	private transient SiniestrosManager      siniestrosManager;
	private transient PantallasManager       pantallasManager;
	private transient CatalogosManager       catalogosManager;
	private transient KernelManagerSustituto kernelManager;
	private Map<String,String>               params;
	private List<Map<String,String>>         loadList;
	private String                           mensaje;
	private boolean                          success;	
	
	public String execute() throws Exception {
    	success = true;
    	return SUCCESS;
    }

    public String pantallaAjustesMedicos()
    {
    	/*params = new HashMap<String,String>();
    	params.put("cdunieco" , "1");
    	params.put("cdramo"   , "2");
    	params.put("estado"   , "3");
    	params.put("nmpoliza" , "4");
    	params.put("nmsuplem" , "5");
    	params.put("nmsituac" , "6");
    	params.put("aaapertu" , "7");
    	params.put("status"   , "8");
    	params.put("nmsinies" , "9");
    	params.put("nfactura" , "10");
    	params.put("cdgarant" , "11");
    	params.put("cdconval" , "12");
    	params.put("cdconcep" , "13");
    	params.put("idconcep" , "14");
    	params.put("nmordina" , "15");*/
    	return SUCCESS;
    }
	
	public String obtenerTdsinival()
    {
    	logger.debug(""
    			+ "\n##############################"
    			+ "\n##############################"
    			+ "\n###### obtenerTdsinival ######"
    			+ "\n######                  ######"
    			);
    	logger.debug("params: "+params);
    	try
    	{
    		String cdunieco = params.get("cdunieco");
    		String cdramo   = params.get("cdramo");
    		String estado   = params.get("estado");
    		String nmpoliza = params.get("nmpoliza");
    		String nmsuplem = params.get("nmsuplem");
    		String nmsituac = params.get("nmsituac");
    		String aaapertu = params.get("aaapertu");
    		String status   = params.get("status");
    		String nmsinies = params.get("nmsinies");
    		String nfactura = params.get("nfactura");
    		String cdgarant = params.get("cdgarant");
    		String cdconval = params.get("cdconval");
    		String cdconcep = params.get("cdconcep");
    		String idconcep = params.get("idconcep");
    		String nmordina = params.get("nmordina");
    		
    		loadList = siniestrosManager.P_GET_TDSINIVAL(
    				cdunieco, cdramo, estado, nmpoliza, nmsuplem,
    				nmsituac, aaapertu, status, nmsinies, nfactura,
    				cdgarant, cdconval, cdconcep, idconcep, nmordina);
    		
    		mensaje = "Datos obtenidos";
    		success = true;
    	}
    	catch(Exception ex)
    	{
    		logger.error("error al obtener tdsinival",ex);
    		success = false;
    		mensaje = ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                  ######"
    			+ "\n###### obtenerTdsinival ######"
    			+ "\n##############################"
    			+ "\n##############################"
    			);
    	return SUCCESS;
    }
    
    public String guardarTdsinival()
    {
    	logger.debug(""
    			+ "\n##############################"
    			+ "\n##############################"
    			+ "\n###### guardarTdsinival ######"
    			+ "\n######                  ######"
    			);
    	logger.debug("params: "+params);
    	try
    	{
    		UserVO usuario = (UserVO)session.get("USUARIO");
    		
    		String cdunieco  = params.get("cdunieco");
    		String cdramo    = params.get("cdramo");
    		String estado    = params.get("estado");
    		String nmpoliza  = params.get("nmpoliza");
    		String nmsuplem  = params.get("nmsuplem");
    		String nmsituac  = params.get("nmsituac");
    		String aaapertu  = params.get("aaapertu");
    		String status    = params.get("status");
    		String nmsinies  = params.get("nmsinies");
    		String nfactura  = params.get("nfactura");
    		String cdgarant  = params.get("cdgarant");
    		String cdconval  = params.get("cdconval");
    		String cdconcep  = params.get("cdconcep");
    		String idconcep  = params.get("idconcep");
    		String nmordina  = params.get("nmordina");
    		String ptimport  = params.get("ptimport");
    		String userregi  = usuario.getUser();
    		Date   dFeregist = new Date();
    		String comments  = params.get("comments");
    		String nmordmov  = params.get("nmordmov");
    		
    		siniestrosManager.P_MOV_TDSINIVAL(
    				cdunieco, cdramo, estado, nmpoliza, nmsuplem,
    				nmsituac, aaapertu, status, nmsinies, nfactura,
    				cdgarant, cdconval, cdconcep, idconcep, nmordina,
    				nmordmov, ptimport, comments, userregi, dFeregist,
    				Constantes.INSERT_MODE);
    		
    		mensaje = "Datos guardados";
    		success = true;
    		
    	}
    	catch(Exception ex)
    	{
    		logger.debug("error al guardar tdsinival",ex);
    		success=false;
    		mensaje=ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                  ######"
    			+ "\n###### guardarTdsinival ######"
    			+ "\n##############################"
    			+ "\n##############################"
    			);
    	return SUCCESS;
    }
    
    
    public String eliminarTdsinival()
    {
    	logger.debug(""
    			+ "\n##############################"
    			+ "\n##############################"
    			+ "\n###### eliminarTdsinival ######"
    			+ "\n######                  ######"
    			);
    	logger.debug("params: "+params);
    	try
    	{
    		UserVO usuario = (UserVO)session.get("USUARIO");
    		
    		String cdunieco  = params.get("cdunieco");
    		String cdramo    = params.get("cdramo");
    		String estado    = params.get("estado");
    		String nmpoliza  = params.get("nmpoliza");
    		String nmsuplem  = params.get("nmsuplem");
    		String nmsituac  = params.get("nmsituac");
    		String aaapertu  = params.get("aaapertu");
    		String status    = params.get("status");
    		String nmsinies  = params.get("nmsinies");
    		String nfactura  = params.get("nfactura");
    		String cdgarant  = params.get("cdgarant");
    		String cdconval  = params.get("cdconval");
    		String cdconcep  = params.get("cdconcep");
    		String idconcep  = params.get("idconcep");
    		String nmordina  = params.get("nmordina");
    		String ptimport  = params.get("ptimport");
    		String userregi  = usuario.getUser();
    		Date   dFeregist = new Date();
    		String comments  = params.get("comments");
    		String nmordmov  = params.get("nmordmov");
    		
    		siniestrosManager.P_MOV_TDSINIVAL(
    				cdunieco, cdramo, estado, nmpoliza, nmsuplem,
    				nmsituac, aaapertu, status, nmsinies, nfactura,
    				cdgarant, cdconval, cdconcep, idconcep, nmordina,
    				nmordmov, ptimport, comments, userregi, dFeregist,
    				Constantes.DELETE_MODE);
    		
    		mensaje = "error al eliminar el registro";
    		success = true;
    		
    	}
    	catch(Exception ex)
    	{
    		logger.debug("error al eliminar el registro",ex);
    		success=false;
    		mensaje=ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                  ######"
    			+ "\n###### eliminarTdsinival ######"
    			+ "\n##############################"
    			+ "\n##############################"
    			);
    	return SUCCESS;
    }
	
    public String modificarTdsinival()
    {
    	logger.debug(""
    			+ "\n##############################"
    			+ "\n##############################"
    			+ "\n###### modificarTdsinival ######"
    			+ "\n######                  ######"
    			);
    	logger.debug("params: "+params);
    	try
    	{
    		UserVO usuario = (UserVO)session.get("USUARIO");
    		
    		String cdunieco  = params.get("cdunieco");
    		String cdramo    = params.get("cdramo");
    		String estado    = params.get("estado");
    		String nmpoliza  = params.get("nmpoliza");
    		String nmsuplem  = params.get("nmsuplem");
    		String nmsituac  = params.get("nmsituac");
    		String aaapertu  = params.get("aaapertu");
    		String status    = params.get("status");
    		String nmsinies  = params.get("nmsinies");
    		String nfactura  = params.get("nfactura");
    		String cdgarant  = params.get("cdgarant");
    		String cdconval  = params.get("cdconval");
    		String cdconcep  = params.get("cdconcep");
    		String idconcep  = params.get("idconcep");
    		String nmordina  = params.get("nmordina");
    		String ptimport  = params.get("ptimport");
    		String userregi  = usuario.getUser();
    		Date   dFeregist = new Date();
    		String comments  = params.get("comments");
    		String nmordmov  = params.get("nmordmov");
    		
    		siniestrosManager.P_MOV_TDSINIVAL(
    				cdunieco, cdramo, estado, nmpoliza, nmsuplem,
    				nmsituac, aaapertu, status, nmsinies, nfactura,
    				cdgarant, cdconval, cdconcep, idconcep, nmordina,
    				nmordmov, ptimport, comments, userregi, dFeregist,
    				Constantes.UPDATE_MODE);
    		
    		mensaje = "error al modificar el registro";
    		success = true;
    		
    	}
    	catch(Exception ex)
    	{
    		logger.debug("error al eliminar el registro",ex);
    		success=false;
    		mensaje=ex.getMessage();
    	}
    	logger.debug(""
    			+ "\n######                  ######"
    			+ "\n###### modificar Tdsinival ######"
    			+ "\n##############################"
    			+ "\n##############################"
    			);
    	return SUCCESS;
    }
	//Getters and setters:

	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}

	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public List<Map<String, String>> getLoadList() {
		return loadList;
	}

	public void setLoadList(List<Map<String, String>> loadList) {
		this.loadList = loadList;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public void setCatalogosManager(CatalogosManager catalogosManager) {
		this.catalogosManager = catalogosManager;
	}

	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}
	
}