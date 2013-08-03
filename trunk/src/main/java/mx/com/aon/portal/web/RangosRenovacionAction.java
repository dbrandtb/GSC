
package mx.com.aon.portal.web;

import mx.com.aon.portal.model.RangoRenovacionReporteVO;
import mx.com.aon.portal.service.RangoRenovacionReporteManager;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;


/**
 *   Action que atiende las peticiones de que vienen de la pantalla Configurar Rangos de Renovacion
 * 
 */
@SuppressWarnings("serial")
public class RangosRenovacionAction extends ActionSupport {

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(RangosRenovacionAction.class);
	

    /**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 * Este objeto no es serializable
	 */
	private transient RangoRenovacionReporteManager rangoRenovacionReporteManager;
	
	@SuppressWarnings("unchecked")
	private List<RangoRenovacionReporteVO> mRangoRenovacionReporteManagerList;
	
	// Respuesta para JSON
    private boolean success;
	public boolean getSuccess() {return success;}
	public void setSuccess(boolean success) {this.success = success;}

	private String cdRenova;
	private String cdRango;
	private String cdInicioRango;
	private String cdFinRango;
	private String dsRango;
	
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
			messageResult = rangoRenovacionReporteManager.borrarRangoRenovacion(cdRenova, cdRango);	
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
	 * 
     * Metodo que atiende una peticion de insertar o actualizar rangos de renovacion
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
    	   RangoRenovacionReporteVO rangoRenovacionReporteVO = new RangoRenovacionReporteVO();
    	   rangoRenovacionReporteVO.setCdRenova(cdRenova);
    	   rangoRenovacionReporteVO.setCdRango(cdRango);
    	   rangoRenovacionReporteVO.setCdInicioRango(cdInicioRango);
    	   rangoRenovacionReporteVO.setCdFinRango(cdFinRango);
    	   rangoRenovacionReporteVO.setDsRango(dsRango);
    	   
    	   messageResult = rangoRenovacionReporteManager.agregarGuardarRangoRenovacion(rangoRenovacionReporteVO);
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
     * Metodo que atiende una peticion de obtener rango de renovacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGetRangoRenovacion()throws Exception
	{

		try
		{
			mRangoRenovacionReporteManagerList = new ArrayList<RangoRenovacionReporteVO>();
			RangoRenovacionReporteVO rangoRenovacionReporteVO=rangoRenovacionReporteManager.getRangoRenovacion(cdRenova, cdRango);
			mRangoRenovacionReporteManagerList.add(rangoRenovacionReporteVO);
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
     * Metodo que atiende una peticion de obtener un encabezado de rangos de renovacion
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGetEncabezadoRangoRenovacion()throws Exception
	{

		try
		{
			mRangoRenovacionReporteManagerList = new ArrayList<RangoRenovacionReporteVO>();
			RangoRenovacionReporteVO rangoRenovacionReporteVO=rangoRenovacionReporteManager.getEncabezadoRangoRenovacion(cdRenova);
			mRangoRenovacionReporteManagerList.add(rangoRenovacionReporteVO);
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
	

	
	public void setRangoRenovacionReporteManager(
			RangoRenovacionReporteManager rangoRenovacionReporteManager) {
		this.rangoRenovacionReporteManager = rangoRenovacionReporteManager;
	}
	public List<RangoRenovacionReporteVO> getMRangoRenovacionReporteManagerList() {
		return mRangoRenovacionReporteManagerList;
	}
	public void setMRangoRenovacionReporteManagerList(
			List<RangoRenovacionReporteVO> rangoRenovacionReporteManagerList) {
		mRangoRenovacionReporteManagerList = rangoRenovacionReporteManagerList;
	}
	public String getCdRenova() {
		return cdRenova;
	}
	public void setCdRenova(String cdRenova) {
		this.cdRenova = cdRenova;
	}
	public String getCdRango() {
		return cdRango;
	}
	public void setCdRango(String cdRango) {
		this.cdRango = cdRango;
	}
	public String getCdInicioRango() {
		return cdInicioRango;
	}
	public void setCdInicioRango(String cdInicioRango) {
		this.cdInicioRango = cdInicioRango;
	}
	public String getCdFinRango() {
		return cdFinRango;
	}
	public void setCdFinRango(String cdFinRango) {
		this.cdFinRango = cdFinRango;
	}
	public String getDsRango() {
		return dsRango;
	}
	public void setDsRango(String dsRango) {
		this.dsRango = dsRango;
	}

}