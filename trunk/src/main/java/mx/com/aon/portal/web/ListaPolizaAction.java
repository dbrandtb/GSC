package mx.com.aon.portal.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.PolizasManager;
import mx.com.aon.portal.model.AgrupacionPolizaVO;
import mx.com.aon.portal.model.ConsultaPolizasCanceladasVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Agrupacion de polizas.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaPolizaAction extends AbstractListAction{

	private static final long serialVersionUID = 1644454515546L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaConfigurarEstructuraAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient PolizasManager  polizasManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AgrupacionPolizaVO
	 * con los valores de la consulta.
	 */
	private List<AgrupacionPolizaVO> listaPolizas;
	private List<ConsultaPolizasCanceladasVO> listaPolizasCanceladas;
	
	
    private String pv_asegurado_i;  
    private String pv_dsuniage_i; 
    private String pv_dsramo_i;
    private String pv_nmpoliza_i;
    private String pv_nmsituac_i;
	
    
    private String pv_dsrazon_i; 
    private String  pv_fecancel_ini_i;
    private String  pv_fecancel_fin_i;
    
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
	
	/**
	 * Atributo con el(los) dato(s) que identificará(n) el objeto Ext.data.Record que se seleccionó
	 * desde una pantalla de origen. Una vez que se desee regresar, se utilizará este atributo. 
	 */
	private String idRegresar;
	
	/**
	 * Metodo que realiza la busqueda de un conjunto de registros de agrupacion de polizas.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
    @SuppressWarnings("unchecked")
	public String cmdBuscarClick () {
		try {
    		PagedList pagedList = polizasManager.buscarPolizasACancelar(pv_asegurado_i, pv_dsuniage_i, pv_dsramo_i, pv_nmpoliza_i, pv_nmsituac_i, start, limit);
    		listaPolizas = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
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
    
    @SuppressWarnings("unchecked")
    public String obtienePantallaPolizasCanceladas() {
    	logger.debug("****** Entrando a obtienePantallaPolizasCanceladas");
    	if ( StringUtils.isBlank( idRegresar ) )
    		super.updateParametrosRegresar();

    	return INPUT;
    }

    
    /**
	 * Metodo que realiza la busqueda de un conjunto de registros de agrupacion de polizas.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
    @SuppressWarnings("unchecked")
	public String cmdBuscarPolizasCanceladasClick () {
    	Map<String,String> parametrosRegresar = new HashMap<String,String>();
    	
    	parametrosRegresar.put("asegurado", pv_asegurado_i);
    	parametrosRegresar.put("aseguradora", pv_dsuniage_i);
    	parametrosRegresar.put("producto", pv_dsramo_i);
    	parametrosRegresar.put("poliza", pv_nmpoliza_i);
    	parametrosRegresar.put("razon", pv_dsrazon_i);
    	parametrosRegresar.put("fecha_inicio", pv_fecancel_ini_i);
    	parametrosRegresar.put("fecha_fin", pv_fecancel_fin_i);
    	parametrosRegresar.put("inciso", pv_nmsituac_i);
    	parametrosRegresar.put("idRegresar", idRegresar);
    	parametrosRegresar.put("clicBotonRegresar", "N");
    	
    	logger.debug("parametrosRegresar");
    	logger.debug(parametrosRegresar);
    	
    	session.put("PARAMETROS_REGRESAR", parametrosRegresar);
    	
		try {
    		PagedList pagedList = polizasManager.buscarPolizasCanceladas(pv_asegurado_i, pv_dsuniage_i, pv_dsramo_i, pv_nmpoliza_i, pv_nmsituac_i, pv_dsrazon_i, pv_fecancel_ini_i, pv_fecancel_fin_i, start, limit);
    		listaPolizasCanceladas = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
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
	 * Obtiene un conjunto de agrupacion de polizas y exporta el resultado en Formato PDF, Excel, CSV, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	/*public String cmdExportarClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		try {
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			
			filename = "AgrupacionPolizas." + exportFormat.getExtension();
			
			TableModelExport model = agrupacionPolizasManager.getModel(cliente, tipoRamo, tipoAgrupacion, aseguradora, producto);
			
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}*/
	
	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}

	
	public ExportMediator getExportMediator() {
		return exportMediator;
	}

	
	public void setPolizasManager(PolizasManager polizasManager) {
		this.polizasManager = polizasManager;
	}

	public List<AgrupacionPolizaVO> getListaPolizas() {
		return listaPolizas;
	}

	public void setListaPolizas(List<AgrupacionPolizaVO> listaPolizas) {
		this.listaPolizas = listaPolizas;
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


	public List<ConsultaPolizasCanceladasVO> getListaPolizasCanceladas() {
		return listaPolizasCanceladas;
	}


	public void setListaPolizasCanceladas(
			List<ConsultaPolizasCanceladasVO> listaPolizasCanceladas) {
		this.listaPolizasCanceladas = listaPolizasCanceladas;
	}


	public PolizasManager getPolizasManager() {
		return polizasManager;
	}


	public String getIdRegresar() {
		return idRegresar;
	}


	public void setIdRegresar(String idRegresar) {
		this.idRegresar = idRegresar;
	}
}
