package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


//import mx.com.aon.portal.model.DescuentoProductoEncDetVO;
import mx.com.aon.portal.model.DescuentoProductoVO;
import mx.com.aon.portal.model.DescuentoVO;
import mx.com.aon.portal.model.DetalleProductoVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.DescuentosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Clase Action que atiende requerimientos de abm de descuentos por producto que vienen del cliente.
 * 
 * @extends AbstractListAction
 */
public class DescuentosConfigurarProductosAction extends AbstractListAction{

	private static final long serialVersionUID = 1654546879843136L;

	private List<DetalleProductoVO> detalleProducto;
	
	private List<DescuentoProductoVO> encabezadoProducto;
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(DescuentosConfigurarProductosAction.class);
	private String cdDscto;
	private String dsDscto;
	private String dsNombre;
	private String cdTipo;
	private String cdElemento;
	private String cdPerson;
	private String prDescto;
	private String mnDescto;
	private String fgAcumul;
	private String cdEstado;
	private String cdUniEco;
	private String cdRamo;
	private String cdTipSit;
	private String cdPlan;
	private String cdDsctod;
	private transient DescuentosManager descuentosManager;
	
	/**
	 * Metodo que obtiene un registro para el encabezado de la pantalla de configurar descuentos.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetEncabezadoProducto()throws Exception{
		try
		{
			encabezadoProducto = new ArrayList<DescuentoProductoVO>();
			DescuentoProductoVO desProductoVO = descuentosManager.getEncabezadoProducto(cdDscto);
			encabezadoProducto.add(desProductoVO);
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
	 * Metodo que obtiene un conjunto de registro para 
	 * el grid de la pantalla de configurar descuentos.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetDetalleProducto(){
		
		UserVO userVO = (UserVO) session.get("USUARIO");
		if (userVO != null) {
			
			logger.debug("HHH VALOR SESSION FORMATO: "+userVO.getFormatoNumerico());
		}
		try
		{
			PagedList pagedList = descuentosManager.getDetalleProducto(start, limit, cdDscto);
			detalleProducto = pagedList.getItemsRangeList();
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
	 * Metodo que realiza la actualizacion o insercion de un registro de descuento por producto.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarProducto(){
		try
		{	
			DescuentoVO descuentoVO = new DescuentoVO();
			
			descuentoVO.setCdDscto(cdDscto);
			descuentoVO.setDsDscto(dsNombre);
			descuentoVO.setCdTipo(cdTipo);
			descuentoVO.setCdElemento(cdElemento);
			descuentoVO.setCdPerson(cdPerson);
			descuentoVO.setPrDescto(prDescto);
			descuentoVO.setMnDescto(mnDescto);
			descuentoVO.setFgAcumul(fgAcumul);
			descuentoVO.setCdEstado(cdEstado);
			
			WrapperResultados rdo = descuentosManager.guardarProducto(descuentoVO);
			cdDscto = rdo.getResultado();
			String msg = rdo.getMsgText();
			
            success = true;
            addActionMessage(msg);
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
	 * Metodo que realiza la actualizacion o insercion de un registro de detalle de producto.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarProductoDetalle(){
		try
		{	
			DetalleProductoVO detalleProductoVO = new DetalleProductoVO();
			
			detalleProductoVO.setCdDscto(cdDscto);
			detalleProductoVO.setCdDsctod(cdDsctod);
			detalleProductoVO.setCdUniEco(cdUniEco);
			detalleProductoVO.setCdRamo(cdRamo);
			detalleProductoVO.setCdTipSit(cdTipSit);
			detalleProductoVO.setCdPlan(cdPlan);
			String msg = descuentosManager.guardarDetalleProducto(detalleProductoVO);
			
            success = true;
            addActionMessage(msg);
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
	 * Metodo que realiza la eliminacion de un registro de detalle por producto.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarProductoDetalle() {
		try
		{	
			String msg = descuentosManager.borrarDetalleProducto(cdDscto, cdDsctod);
			
            success = true;
            addActionMessage(msg);
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
	

	public void setDescuentosManager(DescuentosManager descuentosManager) {
		this.descuentosManager = descuentosManager;
	}

	public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}

	public String getCdDscto() {
		return cdDscto;
	}

	public void setCdDscto(String cdDscto) {
		this.cdDscto = cdDscto;
	}

	public String getCdTipo() {
		return cdTipo;
	}

	public void setCdTipo(String cdTipo) {
		this.cdTipo = cdTipo;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getPrDescto() {
		return prDescto;
	}

	public void setPrDescto(String prDescto) {
		this.prDescto = prDescto;
	}

	public String getMnDescto() {
		return mnDescto;
	}

	public void setMnDescto(String mnDescto) {
		this.mnDescto = mnDescto;
	}

	public String getFgAcumul() {
		return fgAcumul;
	}

	public void setFgAcumul(String fgAcumul) {
		this.fgAcumul = fgAcumul;
	}

	public String getCdEstado() {
		return cdEstado;
	}

	public void setCdEstado(String cdEstado) {
		this.cdEstado = cdEstado;
	}

	public String getCdDsctod() {
		return cdDsctod;
	}

	public void setCdDsctod(String cdDsctod) {
		this.cdDsctod = cdDsctod;
	}

	public List<DetalleProductoVO> getDetalleProducto() {
		return detalleProducto;
	}

	public void setDetalleProducto(List<DetalleProductoVO> detalleProducto) {
		this.detalleProducto = detalleProducto;
	}

	public String getDsDscto() {
		return dsDscto;
	}

	public void setDsDscto(String dsDscto) {
		this.dsDscto = dsDscto;
	}

	public void setEncabezadoProducto(List<DescuentoProductoVO> encabezadoProducto) {
		this.encabezadoProducto = encabezadoProducto;
	}

	public List<DescuentoProductoVO> getEncabezadoProducto() {
		return encabezadoProducto;
	}

	public String getDsNombre() {
		return dsNombre;
	}

	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
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

	public String getCdTipSit() {
		return cdTipSit;
	}

	public void setCdTipSit(String cdTipSit) {
		this.cdTipSit = cdTipSit;
	}

	public String getCdPlan() {
		return cdPlan;
	}

	public void setCdPlan(String cdPlan) {
		this.cdPlan = cdPlan;
	}

}
