package mx.com.aon.portal.web;

import java.io.InputStream;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.ConfiguracionNumeracionEndososVO;
import mx.com.aon.portal.service.ConfigurarNumeracionEndososManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;


import org.apache.log4j.Logger;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Configuracion de numeracion de endosos.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaConfiguracionNumeracionEndososAction extends AbstractListAction{

	private static final long serialVersionUID = 19347572654734543L;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaConfiguracionNumeracionEndososAction.class);

	private transient ConfigurarNumeracionEndososManager configurarNumeracionEndososManager;

	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo
	 * con los valores de la consulta.
	 */
	private List<ConfiguracionNumeracionEndososVO> numEndososEstructuraList;
	private String dsElemen;
	private String dsUniEco;
	private String dsRamo; 
	private String dsPlan;
	private String nmPoliEx;
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
	 * Metodo que ejecuta una busqueda y obtiene un conjunto de registros
	 * para mostrar en la grilla.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
			ConfiguracionNumeracionEndososVO configuracionNumeracionEndososVO = new ConfiguracionNumeracionEndososVO();
            
			configuracionNumeracionEndososVO.setDsElemen(dsElemen);
			configuracionNumeracionEndososVO.setDsUniEco(dsUniEco);
			configuracionNumeracionEndososVO.setDsRamo(dsRamo);
			configuracionNumeracionEndososVO.setDsPlan(dsPlan);
			configuracionNumeracionEndososVO.setNmPoliEx(nmPoliEx);
			
			PagedList pagedList = configurarNumeracionEndososManager.obtenerNumeracionEndosos(start, limit, configuracionNumeracionEndososVO);
			numEndososEstructuraList = pagedList.getItemsRangeList();
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
	 * Exporta la lista de endosos obtenida en Formato PDF, Excel, etc.
	 * @return
	 * @throws Exception
	 */
	public String cmdExportarClick() throws Exception{
		
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			   contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }
			ConfiguracionNumeracionEndososVO configuracionNumeracionEndososVO = new ConfiguracionNumeracionEndososVO();
            
			configuracionNumeracionEndososVO.setDsElemen(dsElemen);
			configuracionNumeracionEndososVO.setDsUniEco(dsUniEco);
			configuracionNumeracionEndososVO.setDsRamo(dsRamo);
			configuracionNumeracionEndososVO.setDsPlan(dsPlan);
			configuracionNumeracionEndososVO.setNmPoliEx(nmPoliEx);
			
			ExportView exportFormat = (ExportView)exportMediator.getView(formato);
			filename = "Numeracion de Endosos." + exportFormat.getExtension();
			TableModelExport model = configurarNumeracionEndososManager.getModel(configuracionNumeracionEndososVO);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}	
		
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}

	public void setConfigurarNumeracionEndososManager(
			ConfigurarNumeracionEndososManager configurarNumeracionEndososManager) {
		this.configurarNumeracionEndososManager = configurarNumeracionEndososManager;
	}

	public List<ConfiguracionNumeracionEndososVO> getNumEndososEstructuraList() {
		return numEndososEstructuraList;
	}

	public void setNumEndososEstructuraList(
			List<ConfiguracionNumeracionEndososVO> numEndososEstructuraList) {
		this.numEndososEstructuraList = numEndososEstructuraList;
	}
	
	public String getDsElemen() {
		return dsElemen;
	}

	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}

	public String getDsUniEco() {
		return dsUniEco;
	}

	public void setDsUniEco(String dsUniEco) {
		this.dsUniEco = dsUniEco;
	}

	public String getDsRamo() {
		return dsRamo;
	}

	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}

	public String getDsPlan() {
		return dsPlan;
	}

	public void setDsPlan(String dsPlan) {
		this.dsPlan = dsPlan;
	}

	public String getNmPoliEx() {
		return nmPoliEx;
	}

	public void setNmPoliEx(String nmPoliEx) {
		this.nmPoliEx = nmPoliEx;
	}

	public ExportMediator getExportMediator() {
		return exportMediator;
	}
	

}
