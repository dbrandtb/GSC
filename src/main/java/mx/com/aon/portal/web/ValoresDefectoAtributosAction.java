package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.portal.model.ValorDefectoAtributosOrdenVO;
import mx.com.aon.portal.service.ValoresDefectoAtributosManager;
import mx.com.gseguros.exception.ApplicationException;

public class ValoresDefectoAtributosAction extends AbstractListAction{
	
	private static final long serialVersionUID = 16986544465487744L;
	
	private String cdFormatoOrden;
	private String dsFormatoOrden;
	private String cdSeccion;
	private String cdAtribu;
	private String cdExpres;
	private String resultadoOperacion;
	
	//bandera usada para cuando retorne de valores por defecto
	private String flag;

    /**
	 * Manager para gestionar descuentos
	 * Este objeto no sera serializado.
	 */
	private transient ValoresDefectoAtributosManager valoresDefectoAtributosManager;
	
	private List<ValorDefectoAtributosOrdenVO> valoresDefectoAtributosList;
	
	private List<ValorDefectoAtributosOrdenVO> valoresList;
	
	public String obtenerValoresDefectoAtributos()throws Exception{
		try
		{
			valoresDefectoAtributosList = new ArrayList<ValorDefectoAtributosOrdenVO>();
			ValorDefectoAtributosOrdenVO valorDefectoAtributosOrdenVO = valoresDefectoAtributosManager.obtenerValorDefectoAtributosOrden(cdFormatoOrden, cdSeccion, cdAtribu);
			valoresDefectoAtributosList.add(valorDefectoAtributosOrdenVO);
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
	
	public String guardarValorDefectoAtributosClick() {
		try
		{
			resultadoOperacion = valoresDefectoAtributosManager.guardarValorDefectoAtributosOrden(cdFormatoOrden,
					cdSeccion, cdAtribu, cdExpres);
			
			/*
			for(int i=0;i<valoresList.size();i++){
					valoresDefectoAtributosManager.guardarValorDefectoAtributosOrden(valoresVO.getCdFormatoOrden(),
							valoresVO.getCdSeccion(), valoresVO.getCdAtribu(), valoresVO.getCdExpres());	
				}
			*/
			
			success = true;
			addActionMessage(resultadoOperacion);
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
	
	public String irConfigurarOrdenesTrabajoClick()throws Exception
	{
		return "configurarSeccionesOrdenesTrabajo";
	}

	public String getCdFormatoOrden() {
		return cdFormatoOrden;
	}


	public void setCdFormatoOrden(String cdFormatoOrden) {
		this.cdFormatoOrden = cdFormatoOrden;
	}


	public String getCdSeccion() {
		return cdSeccion;
	}


	public void setCdSeccion(String cdSeccion) {
		this.cdSeccion = cdSeccion;
	}


	public String getCdAtribu() {
		return cdAtribu;
	}


	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}

	public String getCdExpres() {
		return cdExpres;
	}

	public void setCdExpres(String cdExpres) {
		this.cdExpres = cdExpres;
	}

	public void setValoresDefectoAtributosManager(
			ValoresDefectoAtributosManager valoresDefectoAtributosManager) {
		this.valoresDefectoAtributosManager = valoresDefectoAtributosManager;
	}

	public List<ValorDefectoAtributosOrdenVO> getValoresDefectoAtributosList() {
		return valoresDefectoAtributosList;
	}

	public void setValoresDefectoAtributosList(
			List<ValorDefectoAtributosOrdenVO> valoresDefectoAtributosList) {
		this.valoresDefectoAtributosList = valoresDefectoAtributosList;
	}

	public List<ValorDefectoAtributosOrdenVO> getValoresList() {
		return valoresList;
	}

	public void setValoresList(List<ValorDefectoAtributosOrdenVO> valoresList) {
		this.valoresList = valoresList;
	}


    public String getResultadoOperacion() {
        return resultadoOperacion;
    }

    public void setResultadoOperacion(String resultadoOperacion) {
        this.resultadoOperacion = resultadoOperacion;
    }

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDsFormatoOrden() {
		return dsFormatoOrden;
	}

	public void setDsFormatoOrden(String dsFormatoOrden) {
		this.dsFormatoOrden = dsFormatoOrden;
	}
}
