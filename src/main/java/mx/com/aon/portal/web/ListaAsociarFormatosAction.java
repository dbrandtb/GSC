package mx.com.aon.portal.web;




import mx.com.aon.portal.model.AsociarFormatoVO;
import mx.com.aon.portal.service.AsociarFormatosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;



import org.apache.log4j.Logger;

import java.util.List;
import java.io.InputStream;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Asociar Formatos
 *   por Clientes.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaAsociarFormatosAction extends AbstractListAction{


	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */

	private static Logger logger = Logger.getLogger(ListaAsociarFormatosAction.class);



	/**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 */
	private transient AsociarFormatosManager asociarFormatosManager;

	private List<AsociarFormatoVO> mEstructuraList;

	
	

	private String dsProceso; 
	private String dsElemen; 
	private String dsRamo; 
	private String dsFormatoOrden; 
	private String dsUnieco;
	
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
	 * ejecuta la busqueda para el llenado de la grilla
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{

            PagedList pagedList = this.asociarFormatosManager.buscarAsociarFormatos(dsProceso, dsElemen, dsRamo, dsFormatoOrden, dsUnieco, start, limit);
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
	 * Metodo que busca un conjunto de asociar formatos  
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarAsociarFormatosClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }


			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			

			filename = "Asociar Formato." + exportFormat.getExtension();
			

			TableModelExport model = asociarFormatosManager.getModel(dsProceso, dsElemen, dsRamo, dsFormatoOrden, dsUnieco);
			

			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}

		return SUCCESS;

	}
	
	

	public List<AsociarFormatoVO> getMEstructuraList() {return mEstructuraList;}
	public void setMEstructuraList(List<AsociarFormatoVO> mEstructuraList) {this.mEstructuraList = mEstructuraList;}

	public void setAsociarFormatosManager(
			AsociarFormatosManager asociarFormatosManager) {
		this.asociarFormatosManager = asociarFormatosManager;
	}


	public String getDsProceso() {
		return dsProceso;
	}


	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}


	public String getDsElemen() {
		return dsElemen;
	}


	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}


	public String getDsRamo() {
		return dsRamo;
	}


	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}


	public String getDsFormatoOrden() {
		return dsFormatoOrden;
	}


	public void setDsFormatoOrden(String dsFormatoOrden) {
		this.dsFormatoOrden = dsFormatoOrden;
	}


	public String getDsUnieco() {
		return dsUnieco;
	}


	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
	}


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


	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}
}
