package mx.com.aon.portal.web;

import mx.com.aon.portal.model.ConfigurarEstructuraVO;
import mx.com.aon.portal.service.ConfigurarEstructuraManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Configurar Estructuras.
 *   
 *   @extends AbstractListAction
 * 
 */

@SuppressWarnings("serial")
public class ListaConfigurarEstructuraAction extends AbstractListAction{

	private static Logger logger = Logger.getLogger(ListaConfigurarEstructuraAction.class);

	private transient ConfigurarEstructuraManager configurarEstructuraManager;

	private List<ConfigurarEstructuraVO> mEstructuraList;

	private String codigoElemento;
	private String codigoEstructura;
	private String nombre;
	private String vinculoPadre;
    private String nomina;
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
	 * Metodo que busca y obtiene un conjunto de registros para el grid de Configurar Estructura.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            String  _nomina  = (nomina!=null && !nomina.equals("") && nomina.equals("true"))?"1":"";
            PagedList pagedList = this.configurarEstructuraManager.buscarConfigurarEstructuras(codigoEstructura,nombre, vinculoPadre, _nomina , start, limit);
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
	 * Metodo que obtiene un conjunto de registro y exporta el resultado en Formato PDF, Excel, etc.
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
			
			filename = "ConfEstructura." + exportFormat.getExtension();
			
			String  _nomina  = (nomina!=null && !nomina.equals("") && nomina.equals("true"))?"1":"";
			TableModelExport model = configurarEstructuraManager.getModel( codigoEstructura, nombre,  vinculoPadre,  _nomina);
			
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

		
		public String getCodigoElemento() {
			return codigoElemento;
		}

		public void setCodigoElemento(String codigoElemento) {
			this.codigoElemento = codigoElemento;
		}

		public String getCodigoEstructura() {
			return codigoEstructura;
		}


		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		
		public String getVinculoPadre() {
			return vinculoPadre;
		}
	
		public void setVinculoPadre(String vinculoPadre) {
			this.vinculoPadre = vinculoPadre;
		}


	    public String getNomina() {
	        return nomina;
	    }


	    public void setCodigoEstructura(String codigoEstructura) {
	        this.codigoEstructura = codigoEstructura;
	    }

	    public void setNomina(String nomina) {
	        this.nomina = nomina;
	    }

		public List<ConfigurarEstructuraVO> getMEstructuraList() {return mEstructuraList;}
		public void setMEstructuraList(List<ConfigurarEstructuraVO> mEstructuraList) {this.mEstructuraList = mEstructuraList;}



	    public void setConfigurarEstructuraManager(ConfigurarEstructuraManager configurarEstructuraManager) {
	        this.configurarEstructuraManager = configurarEstructuraManager;
	    }

}
