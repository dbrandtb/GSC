
package mx.com.aon.portal.web;


import mx.com.aon.portal.model.DesglosePolizasVO;
import mx.com.aon.portal.service.DesglosePolizasManager;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Desglose de Polizas.
 * 
 */
public class DesglosePolizasAction extends ActionSupport {

	private static final long serialVersionUID = 5038370146694158328L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(DesglosePolizasAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient DesglosePolizasManager desglosePolizasManager;
    public void setDesglosePolizasManager(DesglosePolizasManager desglosePolizasManager) {
        this.desglosePolizasManager = desglosePolizasManager;
    }
    /**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo DesglosePolizasVO
	 * con los valores de la consulta.
	 */
	@SuppressWarnings("unchecked")
	private List mDesglosePolizasList;
	private String cdPerson;
	private String dsPerson;
	private String cdElemento;
    private String cdTipCon;
    private String cdRamo;
    private String dsRamo;
    private boolean success;
	
	/**
	 * Metodo que elimina un registro seleccionado de la grilla.
	 * 
	 * @return String success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = desglosePolizasManager.borrarDesglosePolizas(cdPerson, cdTipCon, cdRamo);	
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
     * Metodo que inserta un nuevo registro de desglose de poliza.
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
    	   DesglosePolizasVO desglosePolizasVO = new DesglosePolizasVO();
           
    	   desglosePolizasVO.setCdElemento(cdElemento);
    	   desglosePolizasVO.setCdPerson(cdPerson);
    	   desglosePolizasVO.setCdRamo(cdRamo);
    	   desglosePolizasVO.setCdTipCon(cdTipCon);
    	   messageResult = desglosePolizasManager.agregarDesglosePolizas(desglosePolizasVO);
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
     * Metodo que realiza la copia de un registro seleccionado en el grid.
     * 
     * @return success
     * 
     * @throws Exception
     *
     */
    public String cmdCopiarClick()throws Exception
    {
		String messageResult = "";
       try
        {
    	   messageResult = desglosePolizasManager.copiarDesglosePolizas(cdPerson, cdRamo, dsPerson, dsRamo);
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
    
    
    //Se quito el metodo que actualizaba un desglose de poliza.

    /**
     * Metodo que obtiene un unico registro de desglose de poliza seleccionado en el grid.
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
			mDesglosePolizasList = new ArrayList<DesglosePolizasVO>();
			DesglosePolizasVO desglosePolizasVO=desglosePolizasManager.getDesglosePolizas(cdPerson, cdTipCon, cdRamo);
			mDesglosePolizasList.add(desglosePolizasVO);
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
	
	 public String getDsPerson() {
			return dsPerson;
		}
		public void setDsPerson(String dsPerson) {
			this.dsPerson = dsPerson;
		}
		public String getDsRamo() {
			return dsRamo;
		}
		public void setDsRamo(String dsRamo) {
			this.dsRamo = dsRamo;
		}
		public String getCdElemento() {
			return cdElemento;
		}
		public void setCdElemento(String cdElemento) {
			this.cdElemento = cdElemento;
		}
		public String getCdPerson() {
			return cdPerson;
		}
		public void setCdPerson(String cdPerson) {
			this.cdPerson = cdPerson;
		}
		public String getCdTipCon() {
			return cdTipCon;
		}
		public void setCdTipCon(String cdTipCon) {
			this.cdTipCon = cdTipCon;
		}
		public String getCdRamo() {
			return cdRamo;
		}
		public void setCdRamo(String cdRamo) {
			this.cdRamo = cdRamo;
		}

		public boolean getSuccess() {return success;}
		public void setSuccess(boolean success) {this.success = success;}
	
	@SuppressWarnings("unchecked")
	public List getMDesglosePolizasList() {
		return mDesglosePolizasList;
	}
	@SuppressWarnings("unchecked")
	public void setMDesglosePolizasList(List desglosePolizasList) {
		mDesglosePolizasList = desglosePolizasList;
	}
		
	

}