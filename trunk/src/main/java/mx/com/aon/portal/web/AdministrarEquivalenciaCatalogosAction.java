package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import mx.com.aon.portal.service.AdministrarEquivalenciaCatalogosManager;
import mx.com.aon.portal.service.AgrupacionPolizasManager;
import mx.com.aon.portal.model.EquivalenciaCatalogosVO;
import mx.com.aon.core.ApplicationException;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Administrar Equivalencia de Catálogos.
 * 
 */
public class AdministrarEquivalenciaCatalogosAction extends ActionSupport {

	private static final long serialVersionUID = 178787999654L;

	
    /**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AgrupacionPolizaVO
	 * con los valores de la consulta.
	 */
    @SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(AdministrarEquivalenciaCatalogosAction.class);
	@SuppressWarnings("unchecked")
	private List agrupacionVO;
	private transient AdministrarEquivalenciaCatalogosManager  administrarEquivalenciaCatalogosManager;
	private String cdPais;
	private String cdSistema;
	private String cdTablaAcw;
	private String cdTablaExt;
	private String indUso;
	private String dsUsoAcw;
	private String nmTabla;
	private String countryCode;
	private String nmColumna;
	/**
	 * Respuesta para JSON
	 */
	private boolean success;
	
	
    /**
	 * Metodo que inserta una nueva Equivalencia de catálogo.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */
	public String cmdAgregarClick()throws Exception
	{
		try
		{
			EquivalenciaCatalogosVO equivalenciaCatalogosVO = new EquivalenciaCatalogosVO();
            
			equivalenciaCatalogosVO.setCountryCode(countryCode);
			equivalenciaCatalogosVO.setCdSistema(cdSistema);
			equivalenciaCatalogosVO.setNmTabla(nmTabla);
			equivalenciaCatalogosVO.setCdTablaAcw(cdTablaAcw);
			equivalenciaCatalogosVO.setCdTablaExt(cdTablaExt);
			equivalenciaCatalogosVO.setIndUso(indUso);
			equivalenciaCatalogosVO.setNmColumna(nmColumna);
			equivalenciaCatalogosVO.setDsUsoAcw(dsUsoAcw);    
            
            String messageResult = administrarEquivalenciaCatalogosManager.agregarEquivalenciaCatalogo(equivalenciaCatalogosVO);
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
	 * Metodo que elimina una Equivalencia de catalogos seleccionada en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */
	@SuppressWarnings("unchecked")
	public String cmdBorrarClick() throws Exception
	{
		try
		{
            String messageResult = administrarEquivalenciaCatalogosManager.borrarAgrupacionPoliza(cdPais, cdSistema, nmTabla);
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
	 * Metodo que permite redireccionar a otra pagina
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
    @SuppressWarnings("unchecked")
    public String cmdIrAdministrarCatalogSistemaExterno() throws Exception
    {
        if (cdPais== null || cdPais.equals("") || nmTabla==null || nmTabla.equals("")) {
            success = false;
            addActionError("Pais nulo o no seleccionado, Numero de tabla nulo o no seleccionado");
            return SUCCESS;
        }
        if (cdPais != null && nmTabla != null)
        	return "administrarCatalogSistemaExterno";
        else {
            success = false;
            addActionError("Tipo de Agrupacion indefinido o nulo");
            return SUCCESS;
        }
     }

		 
    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}

    @SuppressWarnings("unchecked")
	public List getAgrupacionVO() {
        return agrupacionVO;
    }

    @SuppressWarnings("unchecked")
	public void setAgrupacionVO(List agrupacionVO) {
        this.agrupacionVO = agrupacionVO;
    }

	public void setAdministrarEquivalenciaCatalogosManager(
			AdministrarEquivalenciaCatalogosManager administrarEquivalenciaCatalogosManager) {
		this.administrarEquivalenciaCatalogosManager = administrarEquivalenciaCatalogosManager;
	}

	public String getCdPais() {
		return cdPais;
	}

	public void setCdPais(String cdPais) {
		this.cdPais = cdPais;
	}

	public String getCdSistema() {
		return cdSistema;
	}

	public void setCdSistema(String cdSistema) {
		this.cdSistema = cdSistema;
	}

	public String getCdTablaAcw() {
		return cdTablaAcw;
	}

	public void setCdTablaAcw(String cdTablaAcw) {
		this.cdTablaAcw = cdTablaAcw;
	}

	public String getCdTablaExt() {
		return cdTablaExt;
	}

	public void setCdTablaExt(String cdTablaExt) {
		this.cdTablaExt = cdTablaExt;
	}

	public String getIndUso() {
		return indUso;
	}

	public void setIndUso(String indUso) {
		this.indUso = indUso;
	}

	public String getDsUsoAcw() {
		return dsUsoAcw;
	}

	public void setDsUsoAcw(String dsUsoAcw) {
		this.dsUsoAcw = dsUsoAcw;
	}

	public String getNmTabla() {
		return nmTabla;
	}

	public void setNmTabla(String nmTabla) {
		this.nmTabla = nmTabla;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getNmColumna() {
		return nmColumna;
	}

	public void setNmColumna(String nmColumna) {
		this.nmColumna = nmColumna;
	}

}
