
package mx.com.aon.portal.web;



import mx.com.aon.portal.model.NumeroIncisoVO;
import mx.com.aon.portal.service.NumeroIncisosManager;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;


/**
 * Action que atiende las peticiones de la pantalla numeros de incisos.
 *
 */
@SuppressWarnings("serial")
public class NumeroIncisosAction extends ActionSupport {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(NumeroIncisosAction.class);
	
	

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient NumeroIncisosManager numeroIncisosManager;

	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo ReciboVO
	 * con los valores de la consulta.
	 */
	@SuppressWarnings("unchecked")
	private List numeroIncisosManagerList;
	private List<NumeroIncisoVO> numeroIncisosManagerListLoadTramo;

    private boolean success;	

	private String cdUniEco;
	private String cdElemento;
	private String cdRamo;
	private String indSituac;
	private String cdPerson;
	private String indSufPre;
	private String dsSufPre;
	private String indCalc;
	private String dsCalculo;
	private String nmFolioIni;
	private String nmFolioFin;
	private String cdSufPreCia;	
	

	/**
	 * Elimina un registro del grid previamente seleccionado de la pantalla numeros de incisos.
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 * 
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = numeroIncisosManager.borrarNumeroInciso(cdUniEco, cdElemento, cdRamo, indSituac);	
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
     * Inserta la informacion de un numero de inciso.
     * 
     * @return success
     * 
     * @throws Exception
     *
     */
    public String cmdAgregarClick()throws Exception
    {
		String messageResult = "";
       try
        {
    	   NumeroIncisoVO numeroIncisoVO = new NumeroIncisoVO();
           
    	   numeroIncisoVO.setCdUniEco(cdUniEco);
    	   numeroIncisoVO.setCdElemento(cdElemento);
    	   numeroIncisoVO.setCdRamo(cdRamo);
    	   numeroIncisoVO.setIndSituac(indSituac);
    	   numeroIncisoVO.setCdPerson(cdPerson);
    	   numeroIncisoVO.setIndSufPre(indSufPre);
    	   numeroIncisoVO.setDsSufPre(dsSufPre);
    	   numeroIncisoVO.setIndCalc(indCalc);
    	   numeroIncisoVO.setDsCalculo(dsCalculo);
    	   numeroIncisoVO.setNmFolioIni(nmFolioIni);
    	   numeroIncisoVO.setCdSufPreCia(cdSufPreCia);
    		
    	   numeroIncisoVO.setNmFolioFin(nmFolioFin);
    	 
    	   
    	   messageResult = numeroIncisosManager.insertarNumerosInciso(numeroIncisoVO);
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
	 * Actualiza los datos modificados de una alerta editada.
	 *
	 */
	public String cmdGuardarClick()throws Exception
	{
		String messageResult = "";
        try
        {
        	 NumeroIncisoVO numeroIncisoVO = new NumeroIncisoVO();
             
      	   numeroIncisoVO.setCdUniEco(cdUniEco);
      	   numeroIncisoVO.setCdElemento(cdElemento);
      	   numeroIncisoVO.setCdRamo(cdRamo);
      	   numeroIncisoVO.setIndSituac(indSituac);
      	   numeroIncisoVO.setCdPerson(cdPerson);
      	   numeroIncisoVO.setIndSufPre(indSufPre);
      	   numeroIncisoVO.setDsSufPre(dsSufPre);
      	   numeroIncisoVO.setIndCalc(indCalc);
      	   numeroIncisoVO.setDsCalculo(dsCalculo);
      	   numeroIncisoVO.setNmFolioIni(nmFolioIni);
      	   numeroIncisoVO.setNmFolioFin(nmFolioFin);
      	   numeroIncisoVO.setCdSufPreCia(cdSufPreCia);
      	 
      	   messageResult = numeroIncisosManager.guardarNumerosInciso(numeroIncisoVO);
    	   
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
	 * Metodo que busca y obtiene un unico registro de numeros de incisos.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetClick()throws Exception
	{

		try
		{
			numeroIncisosManagerList = new ArrayList<NumeroIncisoVO>();
			NumeroIncisoVO numeroIncisoVO = numeroIncisosManager.getNumeroInciso(cdUniEco, cdElemento, cdRamo, indSituac);
			numeroIncisosManagerList.add(numeroIncisoVO);
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
	 * Metodo que realiza la carga de un tramo
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String loadTramos()throws Exception
	{
        try
		{
	        numeroIncisosManagerListLoadTramo = new ArrayList<NumeroIncisoVO>();
			NumeroIncisoVO numeroIncisoVO = numeroIncisosManager.getTramos(cdRamo);
			numeroIncisosManagerListLoadTramo.add(numeroIncisoVO);
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
	
	public boolean getSuccess() {return success;}
	public void setSuccess(boolean success) {this.success = success;}
	
	public void setNumeroIncisosManager(NumeroIncisosManager numeroIncisosManager) {
		this.numeroIncisosManager = numeroIncisosManager;
	}
	
	@SuppressWarnings("unchecked")
	public List getNumeroIncisosManagerList() {
		return numeroIncisosManagerList;
	}
	@SuppressWarnings("unchecked")
	public void setNumeroIncisosManagerList(List numeroIncisosManagerList) {
		this.numeroIncisosManagerList = numeroIncisosManagerList;
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
	public String getIndSituac() {
		return indSituac;
	}
	public void setIndSituac(String indSituac) {
		this.indSituac = indSituac;
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
	public void setNumeroIncisosManagerListLoadTramo(
			List<NumeroIncisoVO> numeroIncisosManagerListLoadTramo) {
		this.numeroIncisosManagerListLoadTramo = numeroIncisosManagerListLoadTramo;
	}
	public String getNmFolioFin() {
		return nmFolioFin;
	}
	public void setNmFolioFin(String nmFolioFin) {
		this.nmFolioFin = nmFolioFin;
	}
	public String getCdSufPreCia() {
		return cdSufPreCia;
	}
	public void setCdSufPreCia(String cdSufPreCia) {
		this.cdSufPreCia = cdSufPreCia;
	}
	@SuppressWarnings("unchecked")
	public List getNumeroIncisosManagerListLoadTramo() {
		return numeroIncisosManagerListLoadTramo;
	}

}