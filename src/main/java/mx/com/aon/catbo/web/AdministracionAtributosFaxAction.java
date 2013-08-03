package mx.com.aon.catbo.web;



import mx.com.aon.core.ApplicationException;



import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Administracion de Faxes.
 * 
 */
@SuppressWarnings("serial")
public class AdministracionAtributosFaxAction extends ActionSupport {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AdministracionAtributosFaxAction.class);
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	//private transient AdministracionAtributosDeFaxManager administracionAtributosDeFaxManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	//private List<AdministraFaxVO> mEstructuraList;


	private boolean success;

		
	/**
	 * Metodo que elimina un registro de tabla Notificaciones.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			//messageResult = administracionAtributosDeFaxManager.borrarAdministracionFaxes();
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        /*}catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;*/

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }

	}
	
	/**
	 * Metodo que obtiene una notificacion.
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
			//mEstructuraList=new ArrayList<AdministraFaxVO>();
			//AdministraFaxVO administraFaxVO=administracionFaxesManager.getFormatosDocumentos(cdFormato);
			//mEstructuraList.add(administraFaxVO);
			success = true;
            return SUCCESS;
      /*  }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;*/

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

	/**
	 * Metodo que atualiza una ayuda de coberturas modificada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */
	public String cmdGuardarClick()throws Exception
	{
		String messageResult = "";
        try
        {
        	//AdministraFaxVO administraFaxVO = new AdministraFaxVO();
        	//administraFaxVO.setCdFormato(cdFormato);
        	//administraFaxVO.setDsNomFormato(dsNomFormato);
        	//administraFaxVO.setDsFormato(dsFormato);
        	//administraFaxVO
        	//administraFaxVO
        	//administraFaxVO
        	//administraFaxVO
        	//administraFaxVO
        	//administraFaxVO
        	//messageResult = administracionFaxesManager.guardarFormatosDocumentos(administraFaxVO);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
      /*  }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;*/

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}	
	

	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}


	
	

}