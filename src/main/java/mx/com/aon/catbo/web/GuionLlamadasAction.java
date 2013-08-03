package mx.com.aon.catbo.web;

import java.io.InputStream;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.AccesoEstructuraRolUsuarioVO;
import mx.com.aon.catbo.model.ActividadUsuarioVO;
import mx.com.aon.catbo.model.GuionLlamadasVO;
import mx.com.aon.catbo.service.AccesoEstructuraRolUsuarioManager;
import mx.com.aon.catbo.service.GuionLlamadasManager;
import mx.com.aon.portal.service.ConsultaActividadUsuarioManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;

import org.apache.log4j.Logger;

public class GuionLlamadasAction extends AbstractListAction {

	private static final long serialVersionUID = -792632067915334348L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaActividadUsuarioAction.class);

	private transient GuionLlamadasManager guionLlamadasManager;

	private List<GuionLlamadasVO> listaGuionLlamadas; 
	
	private String cdUnieco;
	private String dsUnieco;
	private String cdRamo;
	private String dsRamo;
	private String cdProceso;
	private String dsProceso;
	private String cdElemento;
	private String dsElemen;
	private String cdGuion;
	private String dsGuion;
	private String cdTipGuion;
	private String dsTipGuion;
	private String indInicial;
	private String estado;
	private String cdDialogo;
	private String dsDialogo;
	private String cdSecuencia;
	private String otTapVal;
	
	/**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
		
	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
	

	public String getFormato() {
		return formato;
	}


	public void setFormato(String formato) {
		this.formato = formato;
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public ExportMediator getExportMediator() {
		return exportMediator;
	}


	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}


	


	@SuppressWarnings("unchecked")
	public String cmdBuscarGuionLlamadas() throws ApplicationException {
		try {
			PagedList pagedList = guionLlamadasManager.obtenerGuionLlamadas(dsUnieco, dsElemen , dsGuion , dsProceso , dsTipGuion , dsRamo, start, limit);
			//pv_dsunieco_i, pv_dselemento_i, pv_dsguion_i, pv_dsproceso_i, pv_dstipgui_i, pv_dsramo_i, start, limit
			listaGuionLlamadas = pagedList.getItemsRangeList();
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
	
	public String cmdGuardarGuionLlamadas() throws ApplicationException{
		String messageResult = "";
		try{
			
			messageResult = guionLlamadasManager.guardarGuionLlamadas(cdUnieco, cdRamo, cdElemento, cdProceso, cdGuion, dsGuion, cdTipGuion, indInicial, estado);
	        //(pv_cdunieco_i, pv_cdramo_i, pv_cdelemento_i, pv_cdproceso_i, pv_cdguion_i, pv_dsguion_i, pv_cdtipguion_i, pv_indinicial_i, pv_status_i)		
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

	@SuppressWarnings("unchecked")
	public String cmdBuscarDialogoLlamadas() throws ApplicationException {
		try {
			PagedList pagedList = guionLlamadasManager.obtenerDialogoLlamadas(cdUnieco, cdRamo, cdElemento, cdProceso, cdGuion, cdDialogo, start, limit);
			listaGuionLlamadas = pagedList.getItemsRangeList();
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
	
	public String cmdGuardarDialogoLlamadas() throws ApplicationException{
		String messageResult = "";
		try{
			
			messageResult = guionLlamadasManager.guardarDialogoLlamadas(cdUnieco, cdRamo, cdElemento, cdProceso, cdGuion, cdDialogo, dsDialogo, cdSecuencia, otTapVal);
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
	
/*@SuppressWarnings("unchecked")
	public String cmdBorrarClick() throws Exception
	{
		try
		{
            String messageResult = agrupacionPolizasManager.borrarAgrupacionPoliza(cveAgrupa);
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
     }*/


	public String irActividadUsuarioResultadoClick(){
		return "actividadUsuarioResultado";
	}

	public String irActividadUsuarioClick(){
		return "actividadUsuario";
	}

	public List<GuionLlamadasVO> getListaGuionLlamadas() {
		return listaGuionLlamadas;
	}


	public void setListaGuionLlamadas(List<GuionLlamadasVO> listaGuionLlamadas) {
		this.listaGuionLlamadas = listaGuionLlamadas;
	}


	public String getCdUnieco() {
		return cdUnieco;
	}


	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}


	public String getDsUnieco() {
		return dsUnieco;
	}


	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
	}


	public String getCdRamo() {
		return cdRamo;
	}


	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}


	public String getDsRamo() {
		return dsRamo;
	}


	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}


	public String getCdProceso() {
		return cdProceso;
	}


	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}


	public String getDsProceso() {
		return dsProceso;
	}


	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}


	public String getCdElemento() {
		return cdElemento;
	}


	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}


	public String getDsElemen() {
		return dsElemen;
	}


	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}


	public String getCdGuion() {
		return cdGuion;
	}


	public void setCdGuion(String cdGuion) {
		this.cdGuion = cdGuion;
	}


	public String getDsGuion() {
		return dsGuion;
	}


	public void setDsGuion(String dsGuion) {
		this.dsGuion = dsGuion;
	}


	public String getCdTipGuion() {
		return cdTipGuion;
	}


	public void setCdTipGuion(String cdTipGuion) {
		this.cdTipGuion = cdTipGuion;
	}


	public String getIndInicial() {
		return indInicial;
	}


	public void setIndInicial(String indInicial) {
		this.indInicial = indInicial;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getCdDialogo() {
		return cdDialogo;
	}


	public void setCdDialogo(String cdDialogo) {
		this.cdDialogo = cdDialogo;
	}


	public String getDsDialogo() {
		return dsDialogo;
	}


	public void setDsDialogo(String dsDialogo) {
		this.dsDialogo = dsDialogo;
	}


	public String getCdSecuencia() {
		return cdSecuencia;
	}


	public void setCdSecuencia(String cdSecuencia) {
		this.cdSecuencia = cdSecuencia;
	}


	public String getOtTapVal() {
		return otTapVal;
	}


	public void setOtTapVal(String otTapVal) {
		this.otTapVal = otTapVal;
	}



	public void setGuionLlamadasManager(GuionLlamadasManager guionLlamadasManager) {
		this.guionLlamadasManager = guionLlamadasManager;
	}


	public String getDsTipGuion() {
		return dsTipGuion;
	}


	public void setDsTipGuion(String dsTipGuion) {
		this.dsTipGuion = dsTipGuion;
	}


	



	
}
