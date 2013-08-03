
package mx.com.aon.portal.web;

import org.apache.log4j.Logger;

import mx.com.aon.portal.service.FuncionalidadManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.AgrupacionPolizasManager;
import mx.com.aon.portal.service.PolizasManager;
import mx.com.aon.portal.model.AgrupacionPolizaVO;
import mx.com.aon.portal.model.ConfigurarSeccionOrdenVO;
import mx.com.aon.portal.model.ConsultaPolizasCanceladasVO;
import mx.com.aon.portal.model.EstructuraVO;
import mx.com.aon.portal.model.FuncionalidadVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;


import java.io.InputStream;
import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Agrupacion de polizas.
 *   
 *   @extends AbstractListAction
 * 
 */
public class PolizaAction extends AbstractListAction{

	private static final long serialVersionUID = 1644454515546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaConfigurarEstructuraAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient PolizasManager  polizasManager;
	private List<ConsultaPolizasCanceladasVO> csoGrillaList;
	
	private String cdUnieco;
	private String cdRamo;
	private String estado;
	private String nmPoliza;
	private String nmsuplem;
	private String supLogi;

	private String poliza;
	private String aseguradora;
	private String producto;
	private String asegurado;
	private String nmSituac;
	private String feCancel;
	private String dsRazon;
	private String feEfecto;
	private String feVencim;
	private String comentarios;
	
	private String pv_asegurado_i;
	private String pv_dsuniage_i;
	private String pv_dsramo_i;
	private String pv_nmpoliza_i;
	private String pv_nmsituac_i;
	private String pv_dsrazon_i;
	private String pv_fecancel_ini_i;
	private String pv_fecancel_fin_i;
	
	private String dsUnieco;
	private String dsRamo;        /* utilizado en polizas canceladas */
	private String cdRazon;
	
	private String nmCancel;
	private String cdPerson;
	private String cdMoneda;
	
	private String cdUniage;
	
	private String nmPoliex;

	/**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado.
	 */
	private String formato;
	private String volver1;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado.
	 */
	private String filename;
		
	/**
	 * Atributo inyectado por spring el cual direcciona a traves del tipo de formato para generar 
	 * el archivo a ser exportado.
	 */
	private ExportMediator exportMediator;
	
	
	public String getCdUnieco() {
		return cdUnieco;
	}


	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}


	public String getCdRamo() {
		return cdRamo;
	}


	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getNmPoliza() {
		return nmPoliza;
	}


	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}


	public String getNmsuplem() {
		return nmsuplem;
	}


	public void setNmsuplem(String nmsuplem) {
		this.nmsuplem = nmsuplem;
	}


	public String guardarPolizasClick() throws ApplicationException{
		String messageResult = "";
		try{
			
			messageResult = polizasManager.modificarCancelacionPoliza(csoGrillaList);
			
			success = true;
			addActionMessage(messageResult);
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

	
	public String revertirPolizasCanceladasClick() throws ApplicationException{
		String messageResult = "";
		try{
			
			
			ConsultaPolizasCanceladasVO consultaPolizasCanceladasVO = new ConsultaPolizasCanceladasVO();
			consultaPolizasCanceladasVO.setCdUnieco(cdUnieco);
			consultaPolizasCanceladasVO.setCdRamo(cdRamo);
			consultaPolizasCanceladasVO.setEstado(estado);
			consultaPolizasCanceladasVO.setNmPoliza(nmPoliza);
			consultaPolizasCanceladasVO.setNSupLogi(supLogi);
			consultaPolizasCanceladasVO.setNmsuplem(nmsuplem);
			
			messageResult = polizasManager.revertirPolizasCanceladas(consultaPolizasCanceladasVO);
			
			success = true;
			addActionMessage(messageResult);
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
	
	
	/**
	 * Obtiene un conjunto de estructuras y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportPolizasCanceladasClick()throws Exception{		
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}		
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 			
			filename = "PolizasCanceladas." + exportFormat.getExtension();			
			TableModelExport model = polizasManager.getModelPolizasCanceladas(pv_asegurado_i, pv_dsuniage_i, pv_dsramo_i, pv_nmpoliza_i, pv_nmsituac_i, pv_dsrazon_i, pv_fecancel_ini_i, pv_fecancel_fin_i);		
			inputStream = exportFormat.export(model);			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}		
		return SUCCESS;
	}	
	
	
	
	public String cmdExportPolizasACancelarClick()throws Exception{		
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}		
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 			
			filename = "PolizasACancelar." + exportFormat.getExtension();			
			TableModelExport model = polizasManager.getModelPolizasACancelar(pv_asegurado_i, pv_dsuniage_i, pv_dsramo_i, pv_nmpoliza_i, pv_nmsituac_i);		
			inputStream = exportFormat.export(model);			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}		
		return SUCCESS;
	}	
	
	
	
	public String irDetallePolizas(){
		
		return "poliza";
	}

	public String irRehabilitacionManual(){
		
		return "rehabilitacionManual";
	}

	
	public PolizasManager getPolizasManager() {
		return polizasManager;
	}


	public void setPolizasManager(PolizasManager polizasManager) {
		this.polizasManager = polizasManager;
	}


	public List<ConsultaPolizasCanceladasVO> getCsoGrillaList() {
		return csoGrillaList;
	}


	public void setCsoGrillaList(List<ConsultaPolizasCanceladasVO> csoGrillaList) {
		this.csoGrillaList = csoGrillaList;
	}
	
	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}


	public String getSupLogi() {
		return supLogi;
	}


	public void setSupLogi(String supLogi) {
		this.supLogi = supLogi;
	}


	public String getPv_asegurado_i() {
		return pv_asegurado_i;
	}


	public void setPv_asegurado_i(String pv_asegurado_i) {
		this.pv_asegurado_i = pv_asegurado_i;
	}


	public String getPv_dsuniage_i() {
		return pv_dsuniage_i;
	}


	public void setPv_dsuniage_i(String pv_dsuniage_i) {
		this.pv_dsuniage_i = pv_dsuniage_i;
	}


	public String getPv_dsramo_i() {
		return pv_dsramo_i;
	}


	public void setPv_dsramo_i(String pv_dsramo_i) {
		this.pv_dsramo_i = pv_dsramo_i;
	}


	public String getPv_nmpoliza_i() {
		return pv_nmpoliza_i;
	}


	public void setPv_nmpoliza_i(String pv_nmpoliza_i) {
		this.pv_nmpoliza_i = pv_nmpoliza_i;
	}


	public String getPv_nmsituac_i() {
		return pv_nmsituac_i;
	}


	public void setPv_nmsituac_i(String pv_nmsituac_i) {
		this.pv_nmsituac_i = pv_nmsituac_i;
	}


	public String getPv_dsrazon_i() {
		return pv_dsrazon_i;
	}


	public void setPv_dsrazon_i(String pv_dsrazon_i) {
		this.pv_dsrazon_i = pv_dsrazon_i;
	}


	public String getPv_fecancel_ini_i() {
		return pv_fecancel_ini_i;
	}


	public void setPv_fecancel_ini_i(String pv_fecancel_ini_i) {
		this.pv_fecancel_ini_i = pv_fecancel_ini_i;
	}


	public String getPv_fecancel_fin_i() {
		return pv_fecancel_fin_i;
	}


	public void setPv_fecancel_fin_i(String pv_fecancel_fin_i) {
		this.pv_fecancel_fin_i = pv_fecancel_fin_i;
	}


	public ExportMediator getExportMediator() {
		return exportMediator;
	}


	public String getPoliza() {
		return poliza;
	}


	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}


	public String getAseguradora() {
		return aseguradora;
	}


	public void setAseguradora(String aseguradora) {
		this.aseguradora = aseguradora;
	}


	public String getProducto() {
		return producto;
	}


	public void setProducto(String producto) {
		this.producto = producto;
	}


	public String getAsegurado() {
		return asegurado;
	}


	public void setAsegurado(String asegurado) {
		this.asegurado = asegurado;
	}


	public String getNmSituac() {
		return nmSituac;
	}


	public void setNmSituac(String nmSituac) {
		this.nmSituac = nmSituac;
	}


	public String getFeCancel() {
		return feCancel;
	}


	public void setFeCancel(String feCancel) {
		this.feCancel = feCancel;
	}


	public String getDsRazon() {
		return dsRazon;
	}


	public void setDsRazon(String dsRazon) {
		this.dsRazon = dsRazon;
	}


	public String getFeEfecto() {
		return feEfecto;
	}


	public void setFeEfecto(String feefecto) {
		this.feEfecto = feefecto;
	}


	public String getFeVencim() {
		return feVencim;
	}


	public void setFeVencim(String fevencim) {
		this.feVencim = fevencim;
	}


	public String getComentarios() {
		return comentarios;
	}


	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}


	public String getVolver1() {
		return volver1;
	}


	public void setVolver1(String volver1) {
		this.volver1 = volver1;
	}


	public String getDsUnieco() {
		return dsUnieco;
	}


	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
	}


	public String getDsRamo() {
		return dsRamo;
	}


	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}


	public String getCdRazon() {
		return cdRazon;
	}


	public void setCdRazon(String cdRazon) {
		this.cdRazon = cdRazon;
	}


	public String getNmCancel() {
		return nmCancel;
	}


	public void setNmCancel(String nmCancel) {
		this.nmCancel = nmCancel;
	}


	public String getCdPerson() {
		return cdPerson;
	}


	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}


	public String getCdMoneda() {
		return cdMoneda;
	}


	public void setCdMoneda(String cdMoneda) {
		this.cdMoneda = cdMoneda;
	}


	public String getCdUniage() {
		return cdUniage;
	}


	public void setCdUniage(String cdUniage) {
		this.cdUniage = cdUniage;
	}


	public String getNmPoliex() {
		return nmPoliex;
	}


	public void setNmPoliex(String nmPoliex) {
		this.nmPoliex = nmPoliex;
	}

	 
    
   
	
	
}
