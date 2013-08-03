package mx.com.aon.catbo.web;

import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.CompraTiempoVO;
import mx.com.aon.catbo.model.ElementoComboBoxVO;
import mx.com.aon.catbo.service.CombosManager;
import mx.com.aon.catbo.service.ConfigurarCompraTiempoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.ProcesoManager;

import com.opensymphony.xwork2.ActionSupport;

public class CompraTiempoAction extends ActionSupport {
	private transient ConfigurarCompraTiempoManager configurarCompraTiempoManager;
	private transient ProcesoManager procesoManager;
	//private transient CombosManager combosManager;
	private static Logger logger=Logger.getLogger(CompraTiempoAction.class);
	private boolean success;
	private String tarea;
	private String dsTarea;
	private String nmCaso;
	private String nmOrden;
	private String nmCompra_Per;
	private String nmCompra_Con;
	private String nmCompra_Dis;
	private String nmDisComprar;
	private String unidadTDisponible;
	private String tCompra;
	private String tUnidad;

	private String nivAtencion;
	private List<CompraTiempoVO> listaCompra;
	
	
	private String cdUsuario;
	
	public String cmdObtenerCompraDisponible() throws ApplicationException{
		try{
			PagedList list=configurarCompraTiempoManager.obtieneCompraTiempoDisponible(tarea, nivAtencion,nmCaso, 0, 1);
			listaCompra=list.getItemsRangeList();
			success=true;
			return SUCCESS;
		}
		catch (ApplicationException ae){
			success=false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}
		catch (Exception ae){
			success=false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}
	}
	
		
	public String cmdProcesoManager() throws Exception{
		String message=""; 
		try{
			message = procesoManager.guardaCompraTiempo(nmCaso, tarea, cdUsuario, nivAtencion, tCompra, tUnidad);
			
			success=true;
			addActionMessage(message);
			return SUCCESS;
		}
		catch (ApplicationException ae){
			success=false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}
		catch (Exception ae){
			success=false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}
	}
	
	
	public String validaStatusCasoClik() throws Exception{
		String message=""; 
		try{
			message = configurarCompraTiempoManager.validaSatatusCaso(nmCaso);
			
			success=true;
			addActionMessage(message);
			return SUCCESS;
		}
		catch (ApplicationException ae){
			success=false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}
		catch (Exception ae){
			success=false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}
	}
	
	
	
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getTarea() {
		return tarea;
	}
	public void setTarea(String tarea) {
		this.tarea = tarea;
	}
	public String getDsTarea() {
		return dsTarea;
	}
	public void setDsTarea(String dsTarea) {
		this.dsTarea = dsTarea;
	}
	public String getNmCaso() {
		return nmCaso;
	}
	public void setNmCaso(String nmCaso) {
		this.nmCaso = nmCaso;
	}
	public String getNmOrden() {
		return nmOrden;
	}
	public void setNmOrden(String nmOrden) {
		this.nmOrden = nmOrden;
	}
	public String getNmCompra_Per() {
		return nmCompra_Per;
	}
	public void setNmCompra_Per(String nmCompra_Per) {
		this.nmCompra_Per = nmCompra_Per;
	}
	public String getNmCompra_Con() {
		return nmCompra_Con;
	}
	public void setNmCompra_Con(String nmCompra_Con) {
		this.nmCompra_Con = nmCompra_Con;
	}
	public String getNmCompra_Dis() {
		return nmCompra_Dis;
	}
	public void setNmCompra_Dis(String nmCompra_Dis) {
		this.nmCompra_Dis = nmCompra_Dis;
	}
	public String getUnidadTDisponible() {
		return unidadTDisponible;
	}
	public void setUnidadTDisponible(String unidadTDisponible) {
		this.unidadTDisponible = unidadTDisponible;
	}
	public String getTCompra() {
		return tCompra;
	}
	public void setTCompra(String compra) {
		tCompra = compra;
	}
	/*public void setConfigurarCompraTiempoManager(
			ConfigurarCompraTiempoManager configurarCompraTiempoManager) {
		this.compraTiempoManager = configurarCompraTiempoManager;
	}*/


	public String getNivAtencion() {
		return nivAtencion;
	}


	public void setNivAtencion(String nivAtencion) {
		this.nivAtencion = nivAtencion;
	}


	public List<CompraTiempoVO> getListaCompra() {
		return listaCompra;
	}


	public void setListaCompra(List<CompraTiempoVO> listaCompra) {
		this.listaCompra = listaCompra;
	}

	public String getTUnidad() {
		return tUnidad;
	}

	public void setTUnidad(String unidad) {
		tUnidad = unidad;
	}


	public String getNmDisComprar() {
		return nmDisComprar;
	}


	public void setNmDisComprar(String nmDisComprar) {
		this.nmDisComprar = nmDisComprar;
	}


	/*public ConfigurarCompraTiempoManager getCompraTiempoManager() {
		return compraTiempoManager;
	}


	public void setCompraTiempoManager(
			ConfigurarCompraTiempoManager compraTiempoManager) {
		this.compraTiempoManager = compraTiempoManager;
	}*/


	/*public ProcesoManager getProcesoManager() {
		return procesoManager;
	}*/


	public void setProcesoManager(ProcesoManager procesoManager) {
		this.procesoManager = procesoManager;
	}


	/*public CombosManager getCombosManager() {
		return combosManager;
	}


	public void setCombosManager(CombosManager combosManager) {
		this.combosManager = combosManager;
	}*/


	public String getCdUsuario() {
		return cdUsuario;
	}


	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}


	public void setConfigurarCompraTiempoManager(
			ConfigurarCompraTiempoManager configurarCompraTiempoManager) {
		this.configurarCompraTiempoManager = configurarCompraTiempoManager;
	}

		
}
