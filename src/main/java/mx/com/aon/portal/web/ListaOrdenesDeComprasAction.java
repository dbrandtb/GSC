package mx.com.aon.portal.web;


import mx.com.aon.portal.model.OrdenDeCompraVO;
import mx.com.aon.portal.service.OrdenesDeComprasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;



import org.apache.log4j.Logger;

import java.util.List;


/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Ordenes de Compras.
 *   
 *   @extends AbstractListAction
 * 
 */
@SuppressWarnings("serial")
public class ListaOrdenesDeComprasAction extends AbstractListAction{


	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaOrdenesDeComprasAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 * 
	 */
	private transient OrdenesDeComprasManager ordenesDeComprasManager;

	private List<OrdenDeCompraVO> mcEstructuraList;
	
	private String dsCarro;
	private String feInicioDe;
	private String feInicioA;
	

	/**
	 * Metodo que realiza la busqueda de datos de ordenes de compras
	 * en base a descripcion del carrito, fecha de inicio desde,
	 * fecha de inicio hasta 
	 * 
	 * @param dsCarro
	 * @param feInicioDe
	 * @param feInicioA
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = this.ordenesDeComprasManager.buscarOrdenesDeCompras(dsCarro, feInicioDe, feInicioA, start, limit);
            mcEstructuraList = pagedList.getItemsRangeList();
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
	
	public String cmdIrDetalleOrdenesDeCompras(){
		return "detallesOrdenes";
	}
	
	 public String cmdRegresarOrdenesDeCompras(){
			return "ordenesDeCompras";
	 }

	public OrdenesDeComprasManager getOrdenesDeComprasManager() {
		return ordenesDeComprasManager;
	}

	public void setOrdenesDeComprasManager(
			OrdenesDeComprasManager ordenesDeComprasManager) {
		this.ordenesDeComprasManager = ordenesDeComprasManager;
	}

	public String getDsCarro() {
		return dsCarro;
	}

	public void setDsCarro(String dsCarro) {
		this.dsCarro = dsCarro;
	}

	public String getFeInicioDe() {
		return feInicioDe;
	}

	public void setFeInicioDe(String feInicioDe) {
		this.feInicioDe = feInicioDe;
	}

	public String getFeInicioA() {
		return feInicioA;
	}

	public void setFeInicioA(String feInicioA) {
		this.feInicioA = feInicioA;
	}

	public void setMcEstructuraList(List<OrdenDeCompraVO> mcEstructuraList) {
		this.mcEstructuraList = mcEstructuraList;
	}

	public List<OrdenDeCompraVO> getMcEstructuraList() {
		return mcEstructuraList;
	}

}
