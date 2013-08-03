package mx.com.aon.portal.web;


import mx.com.aon.portal.model.ConfigurarFormaCalculoAtributoVariableVO;
import mx.com.aon.portal.service.ConfigurarFormaCalculoAtributosVariablesManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Formas Calculo Atriburo Variable.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaConfigurarFormaCalculoAtributosVariablesAction extends AbstractListAction{


	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaConfigurarFormaCalculoAtributosVariablesAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 */
    private transient ConfigurarFormaCalculoAtributosVariablesManager configurarFormaCalculoAtributosVariablesManager;

	private List<ConfigurarFormaCalculoAtributoVariableVO> mEstructuraList;

	private String dsIden;
    private String dsElemen;
    private String dsUnieco;
    private String dsRamo;
    private String dsTipSit;


	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
    /**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
    
    
	/**
	 * Metodo que realiza la busqueda de datos de forma de calculo variable
	 * en base a aseguradora, producto, cliente, tipo situacion 
	 * 
	 * @param dsUnieco
	 * @param dsRamo
	 * @param dsElemen
 	 * @param dsTipSit 
	 *
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{

            PagedList pagedList = this.configurarFormaCalculoAtributosVariablesManager.buscarConfigurarFormaCalculoAtributo(dsUnieco, dsRamo, dsElemen, dsTipSit, start, limit);
            mEstructuraList = pagedList.getItemsRangeList();
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
	 * Metodo que busca un conjunto de peridos de gracia  
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		try {
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Recibos." + exportFormat.getExtension();
			TableModelExport model = configurarFormaCalculoAtributosVariablesManager.getModel(dsUnieco, dsRamo, dsElemen, dsTipSit);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
    

	public List<ConfigurarFormaCalculoAtributoVariableVO> getMEstructuraList() {return mEstructuraList;}
	public void setMEstructuraList(List<ConfigurarFormaCalculoAtributoVariableVO> mEstructuraList) {this.mEstructuraList = mEstructuraList;}
	
	
	public void setConfigurarFormaCalculoAtributosVariablesManager(
			ConfigurarFormaCalculoAtributosVariablesManager configurarFormaCalculoAtributosVariablesManager) {
		this.configurarFormaCalculoAtributosVariablesManager = configurarFormaCalculoAtributosVariablesManager;
	}

	public String getDsIden() {
		return dsIden;
	}

	public void setDsIden(String dsIden) {
		this.dsIden = dsIden;
	}

	public String getDsElemen() {
		return dsElemen;
	}

	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
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

	public String getDsTipSit() {
		return dsTipSit;
	}

	public void setDsTipSit(String dsTipSit) {
		this.dsTipSit = dsTipSit;
	}

	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}

}
