package mx.com.aon.portal.web;


import mx.com.aon.portal.model.CarritoComprasDireccionOrdenVO;
import mx.com.aon.portal.model.CarritoComprasProductosVO;
import mx.com.aon.portal.model.CarritoComprasRolesVO;
import mx.com.aon.portal.service.CarritoComprasManager;

import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;


import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

/**
 *   Action que atiende la peticion de informacion para la consulta de datos de 
 *   la tabla con mecanismo de paginacion de la pantalla Carritos de Compras.
 *   
 *   @extends AbstractListAction
 * 
 */
public class ListaCarritoComprasAction extends AbstractListAction{

	private static final long serialVersionUID = -2161929117098084652L;



	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaCarritoComprasAction.class);



	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 * 
	 * Este objeto no es serializable
	 */
	private transient CarritoComprasManager carritoComprasManager;


	private List<CarritoComprasManager> mCarritoComprasManagerList;
	private List<CarritoComprasDireccionOrdenVO> mCarritoComprasManagerListDire;
	private List<CarritoComprasRolesVO> mCarritoComprasManagerListEncOrden;
	private List<CarritoComprasRolesVO> mCarritoComprasManagerListDetOrden;
	private List<CarritoComprasProductosVO> mcEstructuraList;
	
	
	 private String cdConfiguracion;
	 private String cdCliente;
	 private String cdPerson;
	 private String cdElemento;
	 private String fgSiNo;
	
	 private String cdUniEco;
	 private String cdRamo;
	 private String nmPoliza;
	 private String nmSuplem;
	 private String cdAsegur;
	 private String cdRol;
	 
	 private String cdCarro;
	 private String cdUsuari;
	 private String cdClient;
	  
	 private String cdContra;
	 private String cdTipDom;

	/**
	 * Metodo que realiza la busqueda del uso del carrito de compras
	 * en base a cliente, y si lo usa o no 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
			
			String _fgSiNo;
			if (fgSiNo == null || fgSiNo.equals("")) {
				_fgSiNo = "";
			} else {
				_fgSiNo = (fgSiNo !=null && !fgSiNo.equals("") && fgSiNo.equals("S"))?"1":"0";
			}
            PagedList pagedList = this.carritoComprasManager.buscarCarritoCompras(cdCliente, _fgSiNo, start, limit);
            mCarritoComprasManagerList = pagedList.getItemsRangeList();
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
	 * Metodo que realiza la busqueda de roles en el carrito de compras
	 * en base a aseguradora, ramo, numero de poliza, numero de suplemento,
	 * codigo de asegurado, codigo de rol en el carrito de compras
	 * 
	 * @param cdUniEco
	 * @param cdRamo
	 * @param nmPoliza
	 * @param nmSuplem
	 * @param cdAsegur
	 * @param cdRol
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarRolesClick() throws Exception{
		try{
            PagedList pagedList = this.carritoComprasManager.buscarRolesCarritoCompras(cdUniEco, cdRamo, nmPoliza, nmSuplem, cdAsegur, cdRol, start, limit);
            mCarritoComprasManagerList = pagedList.getItemsRangeList();
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
	 * Metodo que realiza la busqueda de productos en el carrito de compras
	 * en base al codigo de carrito, codigo de usuario
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public String cmdBuscarProductosClick() throws Exception{
		try{
			PagedList pagedList = this.carritoComprasManager.obtenerProductosCarritoCompras(cdCarro, cdUsuari, start, limit);
            mCarritoComprasManagerList = pagedList.getItemsRangeList();
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
	 * Metodo que realiza la busqueda de los montos del carrito de compras
	 * en base al codigo de carrito, codigo de usuario, codigo de cliente
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public String cmdObtenerMontosClick() throws Exception{
		try{
			List list = this.carritoComprasManager.obtenerMontosCarrito(cdCarro, cdUsuari, cdClient);
            mCarritoComprasManagerList = (List) list.get(0);
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
	 * Metodo que realiza la busqueda de direccion en el carrito de compras
	 * en base al codigo del contrato, codigo tipo domicilio
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdObtenerDireccionClick() throws Exception{
		try{
			
			mCarritoComprasManagerListDire = new ArrayList<CarritoComprasDireccionOrdenVO>();
			CarritoComprasDireccionOrdenVO carritoComprasDireccionOrdenVO=carritoComprasManager.obtieneDireccionOrdenCarritoCompras(cdContra, cdTipDom);
			mCarritoComprasManagerListDire.add(carritoComprasDireccionOrdenVO);
			
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
	 * Metodo que realiza la busqueda de la orden correspondiente al carrito de compras
	 * en base al codigo de carrito
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public String obtenerEncOrdenCarritoCompras() throws Exception{
		try{
			
			mCarritoComprasManagerListEncOrden = new ArrayList<CarritoComprasRolesVO>();
			CarritoComprasRolesVO carritoComprasRolesVO=carritoComprasManager.obtieneEncOrdenCarritoCompras(cdCarro);
			mCarritoComprasManagerListEncOrden.add(carritoComprasRolesVO);
			
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
	 * Metodo que realiza la busqueda de determinada orden correspondiente al carrito de compras
	 * en base al codigo de carrito, codigo de usuario, codigo de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public String obtenerDetOrdenCarritoCompras() throws Exception{
		try{
			
			mCarritoComprasManagerListDetOrden = new ArrayList<CarritoComprasRolesVO>();
			CarritoComprasRolesVO carritoComprasRolesVO=carritoComprasManager.detOrdenCarritoCompras(cdCarro, cdUsuari, cdPerson);
			mCarritoComprasManagerListDetOrden.add(carritoComprasRolesVO);
			
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
	 * Metodo que realiza la busqueda de los detalles de los montos correspondiente al carrito de compras
	 * en base al codigo de carrito
	 * 
	 * @param cdCarro
	 *
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public String cmdObtenerDetalleMontosClick() throws Exception{
		try{
            PagedList pagedList = this.carritoComprasManager.montosOrdenCarritoCompras(cdCarro, start, limit);
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
	
	public String getCdClient() {
		return cdClient;
	}


	public void setCdClient(String cdClient) {
		this.cdClient = cdClient;
	}


	public String getCdCarro() {
		return cdCarro;
	}


	public void setCdCarro(String cdCarro) {
		this.cdCarro = cdCarro;
	}


	public String getCdUsuari() {
		return cdUsuari;
	}


	public void setCdUsuari(String cdUsuari) {
		this.cdUsuari = cdUsuari;
	}


	public CarritoComprasManager getCarritoComprasManager() {
		return carritoComprasManager;
	}


	public void setCarritoComprasManager(CarritoComprasManager carritoComprasManager) {
		this.carritoComprasManager = carritoComprasManager;
	}


	public String getCdConfiguracion() {
		return cdConfiguracion;
	}


	public void setCdConfiguracion(String cdConfiguracion) {
		this.cdConfiguracion = cdConfiguracion;
	}


	public String getCdCliente() {
		return cdCliente;
	}


	public void setCdCliente(String cdCliente) {
		this.cdCliente = cdCliente;
	}



	public String getCdElemento() {
		return cdElemento;
	}


	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}



	public String getFgSiNo() {
		return fgSiNo;
	}



	public void setFgSiNo(String fgSiNo) {
		this.fgSiNo = fgSiNo;
	}



	public void setMCarritoComprasManagerList(
			List<CarritoComprasManager> carritoComprasManagerList) {
		mCarritoComprasManagerList = carritoComprasManagerList;
	}
	
	public List<CarritoComprasManager> getMCarritoComprasManagerList() {
		return mCarritoComprasManagerList;
	}


	public String getCdUniEco() {
		return cdUniEco;
	}


	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}


	public String getCdRamo() {
		return cdRamo;
	}


	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}


	public String getNmPoliza() {
		return nmPoliza;
	}


	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}


	public String getNmSuplem() {
		return nmSuplem;
	}


	public void setNmSuplem(String nmSuplem) {
		this.nmSuplem = nmSuplem;
	}


	public String getCdAsegur() {
		return cdAsegur;
	}


	public void setCdAsegur(String cdAsegur) {
		this.cdAsegur = cdAsegur;
	}


	public String getCdRol() {
		return cdRol;
	}


	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}


	public String getCdContra() {
		return cdContra;
	}


	public void setCdContra(String cdContra) {
		this.cdContra = cdContra;
	}


	public String getCdTipDom() {
		return cdTipDom;
	}


	public void setCdTipDom(String cdTipDom) {
		this.cdTipDom = cdTipDom;
	}


	public List<CarritoComprasDireccionOrdenVO> getMCarritoComprasManagerListDire() {
		return mCarritoComprasManagerListDire;
	}


	public void setMCarritoComprasManagerListDire(
			List<CarritoComprasDireccionOrdenVO> carritoComprasManagerListDire) {
		mCarritoComprasManagerListDire = carritoComprasManagerListDire;
	}


	public List<CarritoComprasRolesVO> getMCarritoComprasManagerListEncOrden() {
		return mCarritoComprasManagerListEncOrden;
	}


	public void setMCarritoComprasManagerListEncOrden(
			List<CarritoComprasRolesVO> carritoComprasManagerListEncOrden) {
		mCarritoComprasManagerListEncOrden = carritoComprasManagerListEncOrden;
	}


	public List<CarritoComprasRolesVO> getMCarritoComprasManagerListDetOrden() {
		return mCarritoComprasManagerListDetOrden;
	}


	public void setMCarritoComprasManagerListDetOrden(
			List<CarritoComprasRolesVO> carritoComprasManagerListDetOrden) {
		mCarritoComprasManagerListDetOrden = carritoComprasManagerListDetOrden;
	}


	public String getCdPerson() {
		return cdPerson;
	}


	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}


	public void setMcEstructuraList(List<CarritoComprasProductosVO> mcEstructuraList) {
		this.mcEstructuraList = mcEstructuraList;
	}

	public List<CarritoComprasProductosVO> getMcEstructuraList() {
		return mcEstructuraList;
	}

		
}
