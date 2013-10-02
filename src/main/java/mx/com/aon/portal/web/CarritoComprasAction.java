
package mx.com.aon.portal.web;


import mx.com.aon.portal.model.CarritoComprasGuardarVO;
import mx.com.aon.portal.model.CarritoComprasVO;
import mx.com.aon.portal.service.CarritoComprasManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;


/**
 *   Action que atiende las peticiones de que vienen de la pantalla Carrito de Compras
 * 
 */
@SuppressWarnings("serial")
public class CarritoComprasAction extends ActionSupport {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(CarritoComprasAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 * Este objeto no es serializable
	 *
	 */
	private transient CarritoComprasManager carritoComprasManager;

	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo ReciboVO
	 * con los valores de la consulta.
	 */
	@SuppressWarnings("unchecked")
	private List<CarritoComprasVO> mCarritoComprasManagerList;
	private List<CarritoComprasGuardarVO> csoGrillaProductosCarrito;

	 private String cdConfiguracion;
	 private String cdCliente;
	 private String cdElemento;
	 private String fgSiNo;
     
	 private String descuento;
     private String cdCarro;
     private String cdCarroD;
	 private String cdUsuari;
     private String cdClient;
     
     private String elDescuento;
     private String elSubTotal;
     private String elTotalFn;
     
     
	   private String nmTarj;
	   private String cdTiTarj;
	   private String cdPerson;
	   private String feVence;
	   private String cdBanco;
	   private String debCred;
	   
	
	/**
	 * Calcula el descuento a aplicar en el carrito de compras
	 * 
	 * @return succes
	 * 
	 * @throws Exception
	 */
	public String calculaDescuento() throws Exception{
		WrapperResultados resultado=new WrapperResultados();
		try{
			resultado = carritoComprasManager.calculaDescuentoCarritoCompras(cdCarro, cdUsuari, cdClient);
			this.setElSubTotal(resultado.getSubTotal());
			this.setElDescuento(resultado.getDescuento());
			this.setElTotalFn(resultado.getTotalFn());
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
	 * Elimina un registro de la grilla previamente seleccionado
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = carritoComprasManager.borrarCarritoCompras(cdConfiguracion);	
            success = true;
            addActionMessage(messageResult);
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
	 * Elimina un registro de la grilla previamente seleccionado
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarRegCarrito() throws Exception{
		String messageResult = "";
		try{
			messageResult = carritoComprasManager.borrarRegCarritoCompras(cdCarro, cdCarroD);	
            success = true;
            addActionMessage(messageResult);
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
     * Metodo que atiende una peticion de insertar o actualizar uso del carrito de compra
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
    public String cmdAgregarGuardarClick()throws Exception
    {
		String messageResult = "";
       try
        {
    	   CarritoComprasVO carritoComprasVO = new CarritoComprasVO();
           
    	   carritoComprasVO.setCdCliente(cdCliente);
    	   carritoComprasVO.setCdConfiguracion(cdConfiguracion);
    	   carritoComprasVO.setCdElemento(cdElemento);
    	   String _fgSiNo = (fgSiNo !=null && !fgSiNo.equals("") && (fgSiNo.equals("S")||fgSiNo.equals("Si")))?"1":"0";
    	   if (fgSiNo == null) _fgSiNo = "0";

    	   carritoComprasVO.setFgSiNo(_fgSiNo);	
    	   
    	   messageResult = carritoComprasManager.agregarGuardarCarritoCompras(carritoComprasVO);
           success = true;
           addActionMessage(messageResult);
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
	 * Metodo que atiende una peticion para obtener una estructura especifica de uso del carrito de compras
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGetClick()throws Exception
	{

		try
		{
			mCarritoComprasManagerList = new ArrayList<CarritoComprasVO>();
			CarritoComprasVO carritoComprasVO=carritoComprasManager.getCarritoCompras(cdConfiguracion);
			mCarritoComprasManagerList.add(carritoComprasVO);
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
	 * Metodo que atiende una peticion para guardar un carrito de compras su totalidad
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */	
	  public String guardarCarrito()throws Exception
	    {
			String messageResult = "";
	       try
	        {
	    	   
	    	   CarritoComprasGuardarVO carritoComprasGuardarVO = new CarritoComprasGuardarVO();	
				for (int i=0; i<csoGrillaProductosCarrito.size(); i++) {
					
					CarritoComprasGuardarVO carritoComprasGuardarVO_grid = csoGrillaProductosCarrito.get(i);
					
			    	   carritoComprasGuardarVO.setCdUsuari(carritoComprasGuardarVO_grid.getCdUsuari());
			    	   carritoComprasGuardarVO.setFeInicio(carritoComprasGuardarVO_grid.getFeInicio());
			    	   carritoComprasGuardarVO.setNmTarj(carritoComprasGuardarVO_grid.getNmTarj());
			    	   carritoComprasGuardarVO.setCdContra(carritoComprasGuardarVO_grid.getCdContra());
			    	   carritoComprasGuardarVO.setCdAsegur(carritoComprasGuardarVO_grid.getCdAsegur());
			    	   carritoComprasGuardarVO.setNmSubtot(carritoComprasGuardarVO_grid.getNmSubtot());
			    	   carritoComprasGuardarVO.setNmDsc(carritoComprasGuardarVO_grid.getNmDsc());
			    	   carritoComprasGuardarVO.setNmTotal(carritoComprasGuardarVO_grid.getNmTotal());
			    	   carritoComprasGuardarVO.setCdEstado(carritoComprasGuardarVO_grid.getCdEstado());
			    	   carritoComprasGuardarVO.setFeEstado(carritoComprasGuardarVO_grid.getFeEstado());
			    	   carritoComprasGuardarVO.setCdTipDom(carritoComprasGuardarVO_grid.getCdTipDom());
			    	   carritoComprasGuardarVO.setNmOrdDom(carritoComprasGuardarVO_grid.getNmOrdDom());
			    	   carritoComprasGuardarVO.setCdClient(carritoComprasGuardarVO_grid.getCdClient());
			    	   carritoComprasGuardarVO.setCdUniEco(carritoComprasGuardarVO_grid.getCdUniEco());
			    	   carritoComprasGuardarVO.setCdRamo(carritoComprasGuardarVO_grid.getCdRamo());
			    	   carritoComprasGuardarVO.setNmPoliza(carritoComprasGuardarVO_grid.getNmPoliza());
			    	   carritoComprasGuardarVO.setNmSuplem(carritoComprasGuardarVO_grid.getNmSuplem());
			    	   carritoComprasGuardarVO.setMnTotalP(carritoComprasGuardarVO_grid.getMnTotalP());
			    	   carritoComprasGuardarVO.setCdTipSit(carritoComprasGuardarVO_grid.getCdTipSit());
			    	   carritoComprasGuardarVO.setCdPlan(carritoComprasGuardarVO_grid.getCdPlan());
			    	   carritoComprasGuardarVO.setFgDscapli(carritoComprasGuardarVO_grid.getFgDscapli());
			    	   carritoComprasGuardarVO.setFeIngres(carritoComprasGuardarVO_grid.getFeIngres());
			    	   carritoComprasGuardarVO.setCdEstadoD(carritoComprasGuardarVO_grid.getCdEstadoD());
			    	   carritoComprasGuardarVO.setCdForPag(carritoComprasGuardarVO_grid.getCdForPag());

					messageResult =  carritoComprasManager.guardarCarrito(carritoComprasGuardarVO);
				}
	           success = true;
	           addActionMessage(messageResult);
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
		 * Metodo que atiende una peticion para guardar la forma de pago de un carrito de compras
		 * 
		 * @return success
		 * 
		 * @throws Exception
		 */	
	  public String guardarCarritoFormaPago()throws Exception
	    {
			String messageResult = "";
	       try
	        {
	    	   CarritoComprasGuardarVO carritoComprasGuardarVO = new CarritoComprasGuardarVO();	
			   carritoComprasGuardarVO.setNmTarj(nmTarj);
			   carritoComprasGuardarVO.setCdTiTarj(cdTiTarj);
			   carritoComprasGuardarVO.setCdPerson(cdPerson);
			   carritoComprasGuardarVO.setFeVence(feVence);
			   carritoComprasGuardarVO.setCdBanco(cdBanco);
			   carritoComprasGuardarVO.setDebCred(debCred);
			   messageResult = carritoComprasManager.guardarFormaPago(carritoComprasGuardarVO);
	           success = true;
	           addActionMessage(messageResult);
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

	     
   private boolean success;
   public boolean getSuccess() {return success;}
   public void setSuccess(boolean success) {this.success = success;}
	  
	public CarritoComprasManager obtenCarritoComprasManager() {
		return carritoComprasManager;
	}
	public void setCarritoComprasManager(CarritoComprasManager carritoComprasManager) {
		this.carritoComprasManager = carritoComprasManager;
	}
	public List<CarritoComprasVO> obtenMCarritoComprasManagerList() {
		return mCarritoComprasManagerList;
	}
	public void setMCarritoComprasManagerList(
			List<CarritoComprasVO> carritoComprasManagerList) {
		mCarritoComprasManagerList = carritoComprasManagerList;
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
	public String getDescuento() {
		return descuento;
	}
	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}

	public String getElDescuento() {
		return elDescuento;
	}

	public void setElDescuento(String elDescuento) {
		this.elDescuento = elDescuento;
	}

	public String getElSubTotal() {
		return elSubTotal;
	}

	public void setElSubTotal(String elSubTotal) {
		this.elSubTotal = elSubTotal;
	}

	public String getElTotalFn() {
		return elTotalFn;
	}

	public void setElTotalFn(String elTotalFn) {
		this.elTotalFn = elTotalFn;
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
	public String getCdClient() {
		return cdClient;
	}
	public void setCdClient(String cdClient) {
		this.cdClient = cdClient;
	}
	public String getCdCarroD() {
		return cdCarroD;
	}
	public void setCdCarroD(String cdCarroD) {
		this.cdCarroD = cdCarroD;
	}
	
	public List<CarritoComprasGuardarVO> getCsoGrillaProductosCarrito() {
		return csoGrillaProductosCarrito;
	}
	public void setCsoGrillaProductosCarrito(
			List<CarritoComprasGuardarVO> csoGrillaProductosCarrito) {
		this.csoGrillaProductosCarrito = csoGrillaProductosCarrito;
	}
	public String getNmTarj() {
		return nmTarj;
	}
	public void setNmTarj(String nmTarj) {
		this.nmTarj = nmTarj;
	}
	public String getCdTiTarj() {
		return cdTiTarj;
	}
	public void setCdTiTarj(String cdTiTarj) {
		this.cdTiTarj = cdTiTarj;
	}
	public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	public String getFeVence() {
		return feVence;
	}
	public void setFeVence(String feVence) {
		this.feVence = feVence;
	}
	public String getCdBanco() {
		return cdBanco;
	}
	public void setCdBanco(String cdBanco) {
		this.cdBanco = cdBanco;
	}
	public String getDebCred() {
		return debCred;
	}
	public void setDebCred(String debCred) {
		this.debCred = debCred;
	}

}