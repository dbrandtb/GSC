package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import mx.com.aon.portal.model.DescuentoDetVolumenVO;
import mx.com.aon.portal.model.DescuentoVO;
import mx.com.aon.portal.service.DescuentosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Clase Action que atiende requerimientos de abm de descuentos por volumenes que vienen del cliente.
 * 
 * @extends AbstractListAction
 */
public class DescuentosConfigurarVolumenAction extends AbstractListAction{

	private static final long serialVersionUID = 165454897879843136L;
	
	private String cdDscto;
	private String dsNombre;
	private String cdTipo;
	private String cdElemento;
	private String cdPerson;
	private String fgAcumul;
	private String cdEstado;
	private String mnVolIni;
	private String mnVolFin;
	private String prDescto;
	private String mnDescto;
	private String cdDsctod;
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(DescuentosConfigurarVolumenAction.class);
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo ReciboVO
	 * con los valores de la consulta.
	 */
	private List<DescuentoDetVolumenVO> detalleVolumen;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo ReciboVO
	 * con los valores de la consulta.
	 */
	private List<DescuentoVO> encabezadoVolumen;
	
	/**
	 * Manager para gestionar estructuras
	 * Este objeto no sera serializado.
	 */
	private transient DescuentosManager descuentosManager;

	/**
	 * Metodo que obtiene un registro para el encabezado de la pantalla de configurar descuentos.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetEncabezadoVolumen()throws Exception{
		try
		{
			encabezadoVolumen = new ArrayList<DescuentoVO>();
			DescuentoVO descuentoVO = descuentosManager.getEncabezadoVolumen(cdDscto);
			encabezadoVolumen.add(descuentoVO);
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
	public String cmdGetDetalleVolumen(){
		try
		{
			PagedList pagedList = descuentosManager.getDetalleVolumen(start, limit, cdDscto);
			detalleVolumen = pagedList.getItemsRangeList();
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
	 * Metodo que realiza la actualizacion o insercion de un registro de volumen.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarVolumen(){
		try
		{
			DescuentoVO descuentoVO = new DescuentoVO();
			
			descuentoVO.setCdDscto(cdDscto);
			descuentoVO.setDsNombre(dsNombre);
			descuentoVO.setCdTipo(cdTipo);
			descuentoVO.setCdElemento(cdElemento);
			descuentoVO.setCdPerson(cdPerson);
			descuentoVO.setFgAcumul(fgAcumul);
			descuentoVO.setCdEstado(cdEstado);
			
			WrapperResultados rdo = descuentosManager.guardarVolumen(descuentoVO);
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
	 * Metodo que realiza la actualizacion o insercion de un registro de detalle por volumen.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarDetalleVolumen() {
		try
		{
			DescuentoDetVolumenVO descuentoDetVolumenVO = new DescuentoDetVolumenVO();
			
			descuentoDetVolumenVO.setCdDscto(cdDscto);
			descuentoDetVolumenVO.setCdDsctod(cdDsctod);
			descuentoDetVolumenVO.setMnVolIni(mnVolIni);
			descuentoDetVolumenVO.setMnVolFin(mnVolFin);
			descuentoDetVolumenVO.setPrDescto(prDescto);
			descuentoDetVolumenVO.setMnDescto(mnDescto);
			String msg = descuentosManager.guardarDetalleVolumen(descuentoDetVolumenVO);
			
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
	 * Metodo que realiza la eliminacion de un registro de detalle por volumen.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarVolumenDetalle() {
		try
		{	
			String msg = descuentosManager.borrarDetalleVolumen(cdDscto, cdDsctod);
			
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
	 * Metodo que permite redireccionar a la pantalla de descuentos.
	 * 
	 * @return string
	 * 
	 * @throws Exception
	 */
	public String irDescuentosClick(){
		return "descuentos";
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

	public String getDsNombre() {
		return dsNombre;
	}

	public void setDsNombre(String dsNombre) {
		this.dsNombre = dsNombre;
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

	public String getMnVolIni() {
		return mnVolIni;
	}

	public void setMnVolIni(String mnVolIni) {
		this.mnVolIni = mnVolIni;
	}

	public String getMnVolFin() {
		return mnVolFin;
	}

	public void setMnVolFin(String mnVolFin) {
		this.mnVolFin = mnVolFin;
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

	public String getCdDsctod() {
		return cdDsctod;
	}

	public void setCdDsctod(String cdDsctod) {
		this.cdDsctod = cdDsctod;
	}

	public List<DescuentoDetVolumenVO> getDetalleVolumen() {
		return detalleVolumen;
	}

	public void setDetalleVolumen(List<DescuentoDetVolumenVO> detalleVolumen) {
		this.detalleVolumen = detalleVolumen;
	}

	public void setEncabezadoVolumen(List<DescuentoVO> encabezadoVolumen) {
		this.encabezadoVolumen = encabezadoVolumen;
	}

	public List<DescuentoVO> getEncabezadoVolumen() {
		return encabezadoVolumen;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}


}
