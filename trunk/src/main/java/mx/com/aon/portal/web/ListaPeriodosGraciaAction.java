package mx.com.aon.portal.web;

import org.apache.log4j.Logger;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.PeriodosGraciaManager;
import mx.com.aon.portal.model.PeriodosGraciaVO;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;
import mx.com.gseguros.exception.ApplicationException;

import java.io.InputStream;
import java.util.List;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Periodos de Gracia
 * 
 */
@SuppressWarnings("serial")
public class ListaPeriodosGraciaAction extends AbstractListAction{


	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */

	private static Logger logger = Logger.getLogger(ListaPeriodosGraciaAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 * Este objeto no es serializable
	 */
	private transient PeriodosGraciaManager  periodosGraciaManager;

	private List<PeriodosGraciaVO> listaPeriodosGracia;

	private String dsTramo;
	private String nmMinimo;
    private String nmMaximo;
    private String diasGrac;
    private String diasCanc;

	private String descripcion;


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
	 * Metodo que realiza la busqueda de datos de periodos de gracia por clientes
	 * en base a descripcion 
	 * 
	 * @param descripcion
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick () {
		try {
    		PagedList pagedList = periodosGraciaManager.buscarPeriodosGracia(descripcion,start,limit);
    		listaPeriodosGracia = pagedList.getItemsRangeList();
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
	 * Metodo que busca un conjunto de peridos de gracia por cliente 
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
    public String cmdExportarClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "formato : " + formato );
		}
		try {

            contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "PeriodosGracia." + exportFormat.getExtension();
			TableModelExport model = periodosGraciaManager.getModel(dsTramo, nmMinimo, nmMaximo,diasGrac,diasCanc);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}
    
		public List<PeriodosGraciaVO> getListaPeriodosGracia() {
			return listaPeriodosGracia;
		}

		public void setListaPeriodosGracia(List<PeriodosGraciaVO> listaPeriodosGracia) {
			this.listaPeriodosGracia = listaPeriodosGracia;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public PeriodosGraciaManager obtenPeriodosGraciaManager() {
			return periodosGraciaManager;
		}

		public String getDsTramo() {
			return dsTramo;
		}

		public void setDsTramo(String dsTramo) {
			this.dsTramo = dsTramo;
		}

		public String getNmMinimo() {
			return nmMinimo;
		}

		public void setNmMinimo(String nmMinimo) {
			this.nmMinimo = nmMinimo;
		}

		public String getNmMaximo() {
			return nmMaximo;
		}

		public void setNmMaximo(String nmMaximo) {
			this.nmMaximo = nmMaximo;
		}

		public String getDiasGrac() {
			return diasGrac;
		}

		public void setDiasGrac(String diasGrac) {
			this.diasGrac = diasGrac;
		}

		public String getDiasCanc() {
			return diasCanc;
		}

		public void setDiasCanc(String diasCanc) {
			this.diasCanc = diasCanc;
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

		public ExportMediator getExportMediator() {
			return exportMediator;
		}

		public void setExportMediator(ExportMediator exportMediator) {
			this.exportMediator = exportMediator;
		}
	   public void setPeriodosGraciaManager(PeriodosGraciaManager periodosGraciaManager) {
	        this.periodosGraciaManager = periodosGraciaManager;
	    } 

}
