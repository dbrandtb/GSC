package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.catbo.model.CasoVO;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

//import com.opensymphony.xwork2.ActionSupport;


public class NumeracionCasosAction extends ActionSupport{
	
	private static final long serialVersionUID = 19873215465454L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(NumeracionCasosAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
		
	private transient AdministracionCasosManager administracionCasosManager;
	
	private List<CasoVO> mEstructuraNumCasoList;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans 
	 * con los valores de la consulta.
	 */
		
	private String cdNumeCaso;
	private String cdNumCaso;
	private String indNumer;
	private String cdModulo;
	//private String dsSufpre;
	private String nmCaso;
	private String nmDesde;
	private String nmHasta;
	private String dssufpre;
	

	private boolean success;

	
	/**
	 * Metodo que elimina un registro seleccionado del grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = administracionCasosManager.borrarNumCasos(cdNumeCaso);
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
     * Metodo que inserta un nuevo numeracion de casos 
     * 
     * @return success
     * 
     *@throws Exception
     */
    public String cmdGuardarClick()throws Exception
    {
		String messageResult = "";
        try
        {        	
        	CasoVO casoVO = new CasoVO();
        	
        	casoVO.setCdNumCaso(cdNumCaso);
        	casoVO.setCdDindNumero(indNumer);			
        	casoVO.setCdModulo(cdModulo);	
        	casoVO.setNmCaso(nmCaso);
        	casoVO.setDssufpre(dssufpre);						//setCdModulo(dssufpre);
        	casoVO.setNmDesde(nmDesde);		
        	casoVO.setNmHasta(nmHasta);	
        	
   /*     	cdMod	3
        	cdModulo	
        	cdNumCaso	
        	cod	M
        	nNumActual	154
        	nNumDesde	100
     		nNumHasta	200 */
        	
        	messageResult = administracionCasosManager.guardarNumerosCaso(cdNumCaso, indNumer, cdModulo, nmCaso, dssufpre, nmDesde, nmHasta);
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
	 * Metodo que obtiene un Mecanismo de Tooltip selccionado en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetClick()throws Exception
	{
		try
		{
			mEstructuraNumCasoList=new ArrayList<CasoVO>();
			CasoVO casoVO=administracionCasosManager.getObtenerNumeroCaso(cdNumeCaso);
			mEstructuraNumCasoList.add(casoVO);
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

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		NumeracionCasosAction.logger = logger;
	}

	public AdministracionCasosManager getAdministracionCasosManager() {
		return administracionCasosManager;
	}

	public void setAdministracionCasosManager(
			AdministracionCasosManager administracionCasosManager) {
		this.administracionCasosManager = administracionCasosManager;
	}

	public List<CasoVO> getMEstructuraNumCasoList() {
		return mEstructuraNumCasoList;
	}

	public void setMEstructuraNumCasoList(
			List<CasoVO> estructuraNumCasoList) {
		mEstructuraNumCasoList = estructuraNumCasoList;
	}

	public String getCdNumeCaso() {
		return cdNumeCaso;
	}

	public void setCdNumeCaso(String cdNumeCaso) {
		this.cdNumeCaso = cdNumeCaso;
	}

	public String getIndNumer() {
		return indNumer;
	}

	public void setIndNumer(String indNumer) {
		this.indNumer = indNumer;
	}

	public String getCdModulo() {
		return cdModulo;
	}

	public void setCdModulo(String cdModulo) {
		this.cdModulo = cdModulo;
	}
/*
	public String getDsSufpre() {
		return dsSufpre;
	}

	public void setDsSufpre(String dsSufpre) {
		this.dsSufpre = dsSufpre;
	}
*/
	public String getNmCaso() {
		return nmCaso;
	}

	public void setNmCaso(String nmCaso) {
		this.nmCaso = nmCaso;
	}

	public String getNmDesde() {
		return nmDesde;
	}

	public void setNmDesde(String nmDesde) {
		this.nmDesde = nmDesde;
	}

	public String getNmHasta() {
		return nmHasta;
	}

	public void setNmHasta(String nmHasta) {
		this.nmHasta = nmHasta;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getDssufpre() {
		return dssufpre;
	}

	public void setDssufpre(String dssufpre) {
		this.dssufpre = dssufpre;
	}

	public String getCdNumCaso() {
		return cdNumCaso;
	}

	public void setCdNumCaso(String cdNumCaso) {
		this.cdNumCaso = cdNumCaso;
	}
	

}