package mx.com.aon.portal.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.AdministraCatalogoVO;
import mx.com.aon.portal.service.AdministrarCatalogSistemaExternoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;

import org.apache.log4j.Logger;



/**
 * Action que atiende las peticiones de la pantalla descuento.
 *
 */
@SuppressWarnings("serial")
public class ListaAdministraCatalogSistemExternoAction extends AbstractListAction {
	
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	private static Logger logger = Logger.getLogger(ListaAdministraCatalogSistemExternoAction.class);

	private String dsDescuento;

	//private String otValor;

	private String dsCliente;
	
	private String codPais;
	private String cdSistem;
	private String otClave;
	private String otValor;
	private String cdTabla;
	private String otvalorExt;
	
	
	 //pv_country_code_i, pv_cdsistema_i, pv_otclave01_i, pv_otvalor_i, pv_cdtablaext_i
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo DescuentoVO
	 * con los valores de la consulta.
	 */
	//private List<DescuentoVO> mDescuentoList;
	
	private List <AdministraCatalogoVO> mTcataexList;
	
	/**
	 * Manager con la implementacion de Endpoint para la consulta a BD
	 * Este objeto no sera serializado.
	 */
//	private transient DescuentosManager descuentosManager;
	
	private transient AdministrarCatalogSistemaExternoManager administrarCatalogSistemaExternoManager;
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
	 *  Obtiene un conjunto de descuento y los mustra en el grid de descuentos.
	 *  
	 * @return success
	 *  
	 * @throws Exception
	 */	
    @SuppressWarnings("unchecked")
	public String buscar()throws Exception{
    	if (logger.isDebugEnabled()) {
    		logger.debug("-> ListaAdministraCatalogSistemExternoAction.buscar");
    		logger.debug(":: codPais    : " + codPais);
    		logger.debug(":: cdSistem   : " + cdSistem);
    		logger.debug(":: cdTabla    : " + cdTabla);
    		logger.debug(":: otvalorExt : " + otvalorExt);
    	}
		try
		{
			PagedList pagedList  = administrarCatalogSistemaExternoManager.buscarDatosTcataex(start, limit, codPais, cdSistem, cdTabla, otvalorExt);//.buscarDescuentos(start, limit, dsDescuento, otValor, dsCliente);
			mTcataexList = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
        	mTcataexList = new ArrayList<AdministraCatalogoVO>();
        	success = true;
            addActionError(e.getMessage());            
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}    
    
    /**
	 * Exporta el resultado mostrado en el grid de la pantalla descuento en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
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
			
			filename = "Descuentos." + exportFormat.getExtension();
			
			TableModelExport model = administrarCatalogSistemaExternoManager.getModel(codPais, cdSistem, cdTabla, otvalorExt);
			
			inputStream = exportFormat.export(model);
			
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;

	}
	
	public String getDsDescuento() {
		return dsDescuento;
	}

	public void setDsDescuento(String dsDescuento) {
		this.dsDescuento = dsDescuento;
	}

	public String getOtValor() {
		return otValor;
	}

	public void setOtValor(String otValor) {
		this.otValor = otValor;
	}

	public String getDsCliente() {
		return dsCliente;
	}

	public void setDsCliente(String dsCliente) {
		this.dsCliente = dsCliente;
	}

	public List<AdministraCatalogoVO> getMDescuentoList() {
		return mTcataexList;
	}

	/*public void setMDescuentoList(List<AdministraCatalogoVO> mTcataexList) {
		mTcataexList = mTcataexList;
	}

	/*public void setDescuentosManager(DescuentosManager descuentosManager) {
		this.descuentosManager = descuentosManager;
	}*/
	
	public void setAdministrarCatalogSistemaExternoManager(AdministrarCatalogSistemaExternoManager administrarCatalogSistemaExternoManager) {
		this.administrarCatalogSistemaExternoManager = administrarCatalogSistemaExternoManager;
	}
	
	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}


	public String getCodPais() {
		return codPais;
	}


	public void setCodPais(String codPais) {
		this.codPais = codPais;
	}


	public String getCdSistem() {
		return cdSistem;
	}


	public void setCdSistem(String cdSistem) {
		this.cdSistem = cdSistem;
	}


	public String getOtVlave() {
		return otClave;
	}


	public void setOtVlave(String otVlave) {
		this.otClave = otVlave;
	}


	public String getCdTabla() {
		return cdTabla;
	}


	public void setCdTabla(String cdTabla) {
		this.cdTabla = cdTabla;
	}


	public String getOtClave() {
		return otClave;
	}


	public void setOtClave(String otClave) {
		this.otClave = otClave;
	}


	public List<AdministraCatalogoVO> getMTcataexList() {
		return mTcataexList;
	}


	public void setMTcataexList(List<AdministraCatalogoVO> tcataexList) {
		mTcataexList = tcataexList;
	}

	public String getOtvalorExt() {
		return otvalorExt;
	}

	public void setOtvalorExt(String otvalorExt) {
		this.otvalorExt = otvalorExt;
	}
}