
package mx.com.aon.portal.web;


import mx.com.aon.portal.model.NumeroPolizaVO;
import mx.com.aon.portal.service.NumeroPolizasManager;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;


/**
 * Action que atiende las peticiones de la pantalla de numero de polizas.
 *
 */
public class NumeroPolizasAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1654654447777L;



	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(NumeroPolizasAction.class);
	
	

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient NumeroPolizasManager numeroPolizasManager;

	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo ReciboVO
	 * con los valores de la consulta.
	 */
	@SuppressWarnings("unchecked")
	
	private List mNumeroPolizasManagerList;

	
    private boolean success;	

	private String cdUniEco;
	private String dsUniEco;
	private String cdElemento;
	private String dsElemen;
	private String cdPerson;
	private String cdRamo;
	private String dsRamo;
    private String indSufPre;
	private String dsSufPre;
	private String indCalc;
	private String dsCalculo;
	private String nmFolioIni;
	private String nmFolioFin;
	private String nmFolioAct;
	
	private String cdNumPol;
	private String cdTipPol;
	private String swAgrupa;
	private String cdExpres;
	private String cdProceso;
	
	private String cdPolMtra;
	
	
	/**
	 * Elimina un registro del grid previamente seleccionado de la pantalla de numero de polizas.
	 * @return success
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = numeroPolizasManager.borrarNumeroPoliza(cdUniEco, cdElemento, cdRamo);	
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
	 * Elimina un registro del grid previamente seleccionado de la pantalla de numero de polizas para Emision.
	 * @return success
	 * @throws Exception
	 */
	public String cmdBorrarNumPolEmision() throws Exception{
		String messageResult = "";
		try{
			messageResult = numeroPolizasManager.borrarNumPolEmision(cdNumPol);	
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
     * Salva la informacion de una nueva alerta creada en Agregar en la pantalla de numeros de polizas.
     *
     * @return success
	 * @throws Exception
     */
    public String cmdAgregarClick()throws Exception
    {
		String messageResult = "";
       try
        {
    	   NumeroPolizaVO numeroPolizaVO = new NumeroPolizaVO();
           
    	   numeroPolizaVO.setCdUniEco(cdUniEco);
    	   numeroPolizaVO.setCdElemento(cdElemento);
    	   numeroPolizaVO.setCdRamo(cdRamo);
    	   numeroPolizaVO.setCdPerson(cdPerson);
    	   numeroPolizaVO.setIndSufPre(indSufPre);
    	   numeroPolizaVO.setDsSufPre(dsSufPre);
    	   numeroPolizaVO.setIndCalc(indCalc);
    	   numeroPolizaVO.setDsCalculo(dsCalculo);
    	   numeroPolizaVO.setNmFolioIni(nmFolioIni);
    	   numeroPolizaVO.setNmFolioFin(nmFolioFin);
    	   numeroPolizaVO.setNmFolioAct(nmFolioAct);
    	   
    	   numeroPolizaVO.setCdNumPol(cdNumPol);
    	   numeroPolizaVO.setCdTipPol(cdTipPol);
    	   numeroPolizaVO.setSwAgrupa(swAgrupa);
    	   numeroPolizaVO.setCdExpres(cdExpres);
    	   numeroPolizaVO.setCdProceso(cdProceso);
    	   
    	   messageResult = numeroPolizasManager.insertarNumerosPoliza(numeroPolizaVO);
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
	 * Muestra en modo de edicion un registro seleccionado en la pantalla de numero de polizas.
	 * @return success
	 * @throws Exception
	 *
	 */
	public String cmdGuardarClick()throws Exception
	{
		String messageResult = "";
        try
        {
        	 NumeroPolizaVO numeroPolizaVO = new NumeroPolizaVO();
             
      	   numeroPolizaVO.setCdUniEco(cdUniEco);
      	   numeroPolizaVO.setCdElemento(cdElemento);
      	   numeroPolizaVO.setCdRamo(cdRamo);
      	   numeroPolizaVO.setCdPerson(cdPerson);
      	   numeroPolizaVO.setIndSufPre(indSufPre);
      	   numeroPolizaVO.setDsSufPre(dsSufPre);
      	   numeroPolizaVO.setIndCalc(indCalc);
      	   numeroPolizaVO.setDsCalculo(dsCalculo);
      	   numeroPolizaVO.setNmFolioIni(nmFolioIni);
      	   numeroPolizaVO.setNmFolioFin(nmFolioFin);
      	   numeroPolizaVO.setNmFolioAct(nmFolioAct);
    	   
      	   numeroPolizaVO.setCdNumPol(cdNumPol);
  	       numeroPolizaVO.setCdTipPol(cdTipPol);
  	       numeroPolizaVO.setSwAgrupa(swAgrupa);
  	       numeroPolizaVO.setCdExpres(cdExpres);
  	       numeroPolizaVO.setCdProceso(cdProceso);
  	       
      	   messageResult = numeroPolizasManager.guardarNumerosPoliza(numeroPolizaVO);
    	   
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
	 * Obtiene un numero de poliza para la pantalla numero de polizas
	 * @return success
	 * @throws Exception
	 *
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetClick()throws Exception
	{
		try
		{
			mNumeroPolizasManagerList = new ArrayList<NumeroPolizaVO>();
			NumeroPolizaVO numeroPolizaVO = numeroPolizasManager.getNumeroPoliza(cdUniEco, cdElemento, cdRamo);
			mNumeroPolizasManagerList.add(numeroPolizaVO);
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
	
	public String cmdIrPolizasMaestras()throws Exception
	{
		return "consultaPolizasMaestras";
	}
	
	public NumeroPolizasManager obtenNumeroPolizasManager() {
		return numeroPolizasManager;
	}
	public void setNumeroPolizasManager(NumeroPolizasManager numeroPolizasManager) {
		this.numeroPolizasManager = numeroPolizasManager;
	}
	@SuppressWarnings("unchecked")
	public List obtenMNumeroPolizasManagerList() {
		return mNumeroPolizasManagerList;
	}
	@SuppressWarnings("unchecked")
	public void setMNumeroPolizasManagerList(List numeroPolizasManagerList) {
		mNumeroPolizasManagerList = numeroPolizasManagerList;
	}
	public String getDsUniEco() {
		return dsUniEco;
	}
	public void setDsUniEco(String dsUniEco) {
		this.dsUniEco = dsUniEco;
	}
	public String getDsElemen() {
		return dsElemen;
	}
	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}
	public String getDsRamo() {
		return dsRamo;
	}
	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}
	public String getNmFolioAct() {
		return nmFolioAct;
	}
	public void setNmFolioAct(String nmFolioAct) {
		this.nmFolioAct = nmFolioAct;
	}
	public String getCdUniEco() {
		return cdUniEco;
	}
	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	
	public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	
	public String getIndSufPre() {
		return indSufPre;
	}
	public void setIndSufPre(String indSufPre) {
		this.indSufPre = indSufPre;
	}
	public String getDsSufPre() {
		return dsSufPre;
	}
	public void setDsSufPre(String dsSufPre) {
		this.dsSufPre = dsSufPre;
	}
	public String getIndCalc() {
		return indCalc;
	}
	public void setIndCalc(String indCalc) {
		this.indCalc = indCalc;
	}
	public String getDsCalculo() {
		return dsCalculo;
	}
	public void setDsCalculo(String dsCalculo) {
		this.dsCalculo = dsCalculo;
	}
	public String getNmFolioIni() {
		return nmFolioIni;
	}
	public void setNmFolioIni(String nmFolioIni) {
		this.nmFolioIni = nmFolioIni;
	}
	public String getNmFolioFin() {
		return nmFolioFin;
	}
	public void setNmFolioFin(String nmFolioFin) {
		this.nmFolioFin = nmFolioFin;
	}
	public boolean getSuccess() {return success;}
	public void setSuccess(boolean success) {this.success = success;}


	public String getCdNumPol() {
		return cdNumPol;
	}


	public void setCdNumPol(String cdNumPol) {
		this.cdNumPol = cdNumPol;
	}


	public String getCdTipPol() {
		return cdTipPol;
	}


	public void setCdTipPol(String cdTipPol) {
		this.cdTipPol = cdTipPol;
	}


	public String getSwAgrupa() {
		return swAgrupa;
	}


	public void setSwAgrupa(String swAgrupa) {
		this.swAgrupa = swAgrupa;
	}


	public String getCdExpres() {
		return cdExpres;
	}


	public void setCdExpres(String cdExpres) {
		this.cdExpres = cdExpres;
	}


	public String getCdProceso() {
		return cdProceso;
	}


	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}


	public String getCdPolMtra() {
		return cdPolMtra;
	}


	public void setCdPolMtra(String cdPolMtra) {
		this.cdPolMtra = cdPolMtra;
	}
	
}