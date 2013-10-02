package mx.com.aon.portal.web;

import mx.com.aon.portal.model.PeriodoGraciaClienteVO;
import mx.com.aon.portal.service.PeriodosGraciaClienteManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.Util;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Periodos de Gracia 
 *   por Clientes.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaPeriodosGraciaClienteAction extends AbstractListAction{


	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */

	private static Logger logger = Logger.getLogger(ListaPeriodosGraciaClienteAction.class);



	/**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 */
	private transient PeriodosGraciaClienteManager periodosGraciaClienteManager;

	private List<PeriodoGraciaClienteVO> mPeriodoGraciaClienteList;

	private String dsUnieco;
	private String dsSubram;
	private String dsGarant;
	private String dsTipram;
	private String dsRamo;
  private String dsElemen;
  private String dsUniEco;

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
	 * en base a descripcion elemento, producto, aseguradora 
	 * 
	 * @param dsElemen
	 * @param dsRamo
	 * @param dsUniEco
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            
            PagedList pagedList = this.periodosGraciaClienteManager.buscarPeriodosGraciaCliente(dsElemen, dsRamo, dsUniEco, start, limit);
            mPeriodoGraciaClienteList = pagedList.getItemsRangeList();
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
	 * Metodo que busca un conjunto de periodos de gracia por cliente 
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

            contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "PeriodoGraciaCliente." + exportFormat.getExtension();
			TableModelExport model = periodosGraciaClienteManager.getModel(dsElemen, dsRamo, dsUniEco);
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


		public PeriodosGraciaClienteManager obtenPeriodosGraciaClienteManager() {
			return periodosGraciaClienteManager;
		}


		public void setPeriodosGraciaClienteManager(
				PeriodosGraciaClienteManager periodosGraciaClienteManager) {
			this.periodosGraciaClienteManager = periodosGraciaClienteManager;
		}


		public List<PeriodoGraciaClienteVO> getMPeriodoGraciaClienteList() {
			return mPeriodoGraciaClienteList;
		}


		public void setMPeriodoGraciaClienteList(
				List<PeriodoGraciaClienteVO> periodoGraciaClienteList) {
			mPeriodoGraciaClienteList = periodoGraciaClienteList;
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


		public void setDsRamo(String dsRamo) {
			this.dsRamo = dsRamo;
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
}
