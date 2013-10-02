package mx.com.aon.portal.web;

import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.portal.model.AgrupacionPapel_AgrupacionVO;
import mx.com.aon.portal.service.AgrupacionPapelManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.gseguros.exception.ApplicationException;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Agrupacion por papel.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaAgrupacionPapelAction extends AbstractListAction {
	private transient AgrupacionPapelManager agrupacionPapelManager;
	
	private List<AgrupacionPapel_AgrupacionVO> mListaAgrupacion;

	private String dsCliente;

	private String dsAseguradora;

	private String dsTipoRamo;

	private String dsProducto;

	private String codTipoAgrupacion;

	private String codConfiguracion;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaAgrupacionPapelAction.class);
	
	/**
	 * Metodo que busca y obtiene un conjunto de registros
	 *  de agrupacion para listar en el grid.
	 *  
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick () throws Exception {
		try {
			PagedList pagedList = agrupacionPapelManager.buscarPapeles(codConfiguracion, start, limit);
			mListaAgrupacion = pagedList.getItemsRangeList();
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
	
	public List<AgrupacionPapel_AgrupacionVO> getMListaAgrupacion() {
		return mListaAgrupacion;
	}
	public void setMListaAgrupacion(
			List<AgrupacionPapel_AgrupacionVO> listaAgrupacion) {
		mListaAgrupacion = listaAgrupacion;
	}
	public String getDsCliente() {
		return dsCliente;
	}
	public void setDsCliente(String dsCliente) {
		this.dsCliente = dsCliente;
	}
	public String getDsAseguradora() {
		return dsAseguradora;
	}
	public void setDsAseguradora(String dsAseguradora) {
		this.dsAseguradora = dsAseguradora;
	}
	public String getDsTipoRamo() {
		return dsTipoRamo;
	}
	public void setDsTipoRamo(String dsTipoRamo) {
		this.dsTipoRamo = dsTipoRamo;
	}
	public String getDsProducto() {
		return dsProducto;
	}
	public void setDsProducto(String dsProducto) {
		this.dsProducto = dsProducto;
	}
	public String getCodTipoAgrupacion() {
		return codTipoAgrupacion;
	}
	public void setCodTipoAgrupacion(String codTipoAgrupacion) {
		this.codTipoAgrupacion = codTipoAgrupacion;
	}
	public void setAgrupacionPapelManager(
			AgrupacionPapelManager agrupacionPapelManager) {
		this.agrupacionPapelManager = agrupacionPapelManager;
	}

	public String getCodConfiguracion() {
		return codConfiguracion;
	}

	public void setCodConfiguracion(String codConfiguracion) {
		this.codConfiguracion = codConfiguracion;
	}
}
