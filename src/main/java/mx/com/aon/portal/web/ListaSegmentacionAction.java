package mx.com.aon.portal.web;

import mx.com.aon.portal.model.AyudaCoberturasVO;
import mx.com.aon.portal.service.AyudaCoberturasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.portal.service.SegmentacionManager;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;



import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Ayuda de Coberturas.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaSegmentacionAction extends AbstractListAction{

	private static final long serialVersionUID = 165449872125584L;

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaConfigurarEstructuraAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */
	private transient AyudaCoberturasManager ayudaCoberturasManager;

	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo ReciboVO
	 * con los valores de la consulta.
	 */
	private List<AyudaCoberturasVO> mEstructuraList;
	private String dsUnieco;
	private String dsSubram;
	private String dsGarant;
	private String dsTipram;
	private String dsRamo;
	private String langCode;
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
	 * ejecuta la busqueda para el llenado de la grilla
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{

            PagedList pagedList = this.ayudaCoberturasManager.buscarAyudaCoberturas(dsUnieco, dsSubram, dsGarant, dsTipram, dsRamo, langCode,start, limit);
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
	 * Obtiene un conjunto de ayudas de cobertura y exporta el resultado en Formato PDF, Excel, CSV, etc.
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
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			
			filename = "Recibos." + exportFormat.getExtension();
			
			TableModelExport model = ayudaCoberturasManager.getModel(dsUnieco, dsSubram, dsGarant, dsTipram, dsRamo, langCode);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
	
	public List<AyudaCoberturasVO> getMEstructuraList() {return mEstructuraList;}
	public void setMEstructuraList(List<AyudaCoberturasVO> mEstructuraList) {this.mEstructuraList = mEstructuraList;}

    public void setAyudaCoberturasManager(AyudaCoberturasManager ayudaCoberturasManager) {
        this.ayudaCoberturasManager = ayudaCoberturasManager;
    }
    
	public String getDsUnieco() {
		return dsUnieco;
	}

	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
	}

	public String getDsSubram() {
		return dsSubram;
	}

	public void setDsSubram(String dsSubram) {
		this.dsSubram = dsSubram;
	}

	public String getDsGarant() {
		return dsGarant;
	}

	public void setDsGarant(String dsGarant) {
		this.dsGarant = dsGarant;
	}

	public String getDsTipram() {
		return dsTipram;
	}

	public void setDsTipram(String dsTipram) {
		this.dsTipram = dsTipram;
	}

	public String getDsRamo() {
		return dsRamo;
	}

	public String getLangCode() {
		return langCode;
	}


	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}


	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}
	
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
}
