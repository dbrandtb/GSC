package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import mx.com.aon.portal.service.AsociarOrdenTrabajoManager;
import mx.com.aon.portal.model.AsociarOrdenTrabajoVO;
import mx.com.aon.core.ApplicationException;

/**
 * Action que atiende las solicitudes de abm para la pantalla de Asociar Orden de Trabajo
 *
 */
public class AsociarOrdenTrabajoAction extends ActionSupport {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AsociarOrdenTrabajoAction.class);
	
	private static final long serialVersionUID = 177888945343165111L;

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 * Este objeto no es serializable
	 */
  private transient AsociarOrdenTrabajoManager asociarOrdenTrabajoManager;
    
  private List<AsociarOrdenTrabajoVO> aotEstructuraList;    
    
	private String cdasocia;
	private String cdforord;
	private String cdproces;
    private String cdelemento;
	private String cdperson; 
	private String cdunieco;
	private String cdramo;
	private String cdfolioaon;
	private String cdfoliocia;
	private String dsformaon;
	private String dsformcia;
	private String cdfolaonini;
	private String cdfolaonfin;
	private String cdfolciaini;
	private String cdfolciafin;

	private boolean success;


    /**
	 * Metodo que obtiene un conjunto de registros para el grid de Asociacion Orden de Trabajo.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */
	
	public String obtenerFormatosxCuentaClick()throws Exception{
		try{
			aotEstructuraList = new ArrayList<AsociarOrdenTrabajoVO>();
			AsociarOrdenTrabajoVO asociarOrdenTrabajoVO = asociarOrdenTrabajoManager.obtenerFormatoxCuenta(cdasocia);
			aotEstructuraList.add(asociarOrdenTrabajoVO);
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
	 * Metodo que inserta o actualiza un registro de Asociacion Orden de Trabajo.
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
			AsociarOrdenTrabajoVO asociarOrdenTrabajoVO = new AsociarOrdenTrabajoVO();
            
			asociarOrdenTrabajoVO.setCdasocia(cdasocia);
			asociarOrdenTrabajoVO.setCdforord(cdforord);
			asociarOrdenTrabajoVO.setCdproces(cdproces);            
			asociarOrdenTrabajoVO.setCdelemento(cdelemento);
			asociarOrdenTrabajoVO.setCdperson(cdperson);
			asociarOrdenTrabajoVO.setCdunieco(cdunieco);
			asociarOrdenTrabajoVO.setCdramo(cdramo);   
			asociarOrdenTrabajoVO.setCdfolioaon(cdfolioaon);   

			asociarOrdenTrabajoVO.setCdfoliocia(cdfoliocia);
			asociarOrdenTrabajoVO.setDsformaon(dsformaon);   
			asociarOrdenTrabajoVO.setDsformcia(dsformcia);   
			asociarOrdenTrabajoVO.setCdfolaonini(cdfolaonini);
			asociarOrdenTrabajoVO.setCdfolaonfin(cdfolaonfin);
			asociarOrdenTrabajoVO.setCdfolciaini(cdfolciaini);
			asociarOrdenTrabajoVO.setCdfolciafin(cdfolciafin);

            messageResult = asociarOrdenTrabajoManager.guardarAsociacionOrdenTrabajo(asociarOrdenTrabajoVO);
            addActionMessage(messageResult);
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
	 * Metodo que redirecciona a la pagina de asociar formato.
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 *
	 */
	public String cmdRegresarClick(){
		return "asociarFormatos";
	}


    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}

	public void setAsociarOrdenTrabajoManager(AsociarOrdenTrabajoManager asociarOrdenTrabajoManager) {
		this.asociarOrdenTrabajoManager = asociarOrdenTrabajoManager;
	}

	public String getCdasocia() {
		return cdasocia;
	}


	public void setCdasocia(String cdasocia) {
		this.cdasocia = cdasocia;
	}


	public String getCdforord() {
		return cdforord;
	}


	public void setCdforord(String cdforord) {
		this.cdforord = cdforord;
	}


	public String getCdproces() {
		return cdproces;
	}


	public void setCdproces(String cdproces) {
		this.cdproces = cdproces;
	}


	public String getCdelemento() {
		return cdelemento;
	}


	public void setCdelemento(String cdelemento) {
		this.cdelemento = cdelemento;
	}


	public String getCdperson() {
		return cdperson;
	}


	public void setCdperson(String cdperson) {
		this.cdperson = cdperson;
	}


	public String getCdunieco() {
		return cdunieco;
	}


	public void setCdunieco(String cdunieco) {
		this.cdunieco = cdunieco;
	}


	public String getCdramo() {
		return cdramo;
	}


	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}


	public String getCdfolioaon() {
		return cdfolioaon;
	}


	public void setCdfolioaon(String cdfolioaon) {
		this.cdfolioaon = cdfolioaon;
	}


	public String getCdfoliocia() {
		return cdfoliocia;
	}


	public void setCdfoliocia(String cdfoliocia) {
		this.cdfoliocia = cdfoliocia;
	}


	public String getDsformaon() {
		return dsformaon;
	}


	public void setDsformaon(String dsformaon) {
		this.dsformaon = dsformaon;
	}


	public String getDsformcia() {
		return dsformcia;
	}


	public void setDsformcia(String dsformcia) {
		this.dsformcia = dsformcia;
	}


	public String getCdfolaonini() {
		return cdfolaonini;
	}


	public void setCdfolaonini(String cdfolaonini) {
		this.cdfolaonini = cdfolaonini;
	}


	public String getCdfolaonfin() {
		return cdfolaonfin;
	}


	public void setCdfolaonfin(String cdfolaonfin) {
		this.cdfolaonfin = cdfolaonfin;
	}


	public String getCdfolciaini() {
		return cdfolciaini;
	}


	public void setCdfolciaini(String cdfolciaini) {
		this.cdfolciaini = cdfolciaini;
	}


	public String getCdfolciafin() {
		return cdfolciafin;
	}


	public void setCdfolciafin(String cdfolciafin) {
		this.cdfolciafin = cdfolciafin;
	}


	public List<AsociarOrdenTrabajoVO> getAotEstructuraList() {
		return aotEstructuraList;
	}


	public void setAotEstructuraList(List<AsociarOrdenTrabajoVO> aotEstructuraList) {
		this.aotEstructuraList = aotEstructuraList;
	}

}
