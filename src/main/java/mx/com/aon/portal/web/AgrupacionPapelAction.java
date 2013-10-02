package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.portal.model.AgrupacionPapel_AgrupacionVO;
import mx.com.aon.portal.service.AgrupacionPapelManager;
import mx.com.gseguros.exception.ApplicationException;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Agrupacion por papel.
 * 
 */
@SuppressWarnings("serial")
public class AgrupacionPapelAction extends ActionSupport{
	private transient AgrupacionPapelManager agrupacionPapelManager;

	private List<AgrupacionPapel_AgrupacionVO> agrupacionList;

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(AgrupacionPapelAction.class);
	
	private boolean success;

	private String codigoConfiguracion;

	private String codigoNivel;

	private String cdRol;

	private String codigoAseguradora;
	
	private String codigoProducto;
	
	private String cdAgrupa;

	private List <AgrupacionPapel_AgrupacionVO> lista;

	private String messageResult;
	
	private String descripcionNivel;
	
	private String descripcionAseguradora;
	
	private String descripcionProducto;
	
	private String codigoTipo;
	
	private String codigoAgrupacion;
	
	private String cdAgrRol;
	
	private String cdPolMtra;
	
	private String cdRamo;
	
	private String cdUniEco;
	


	/**
	 * Metodo que busca y obtiene un unico registro de Agrupacion por papel.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetAgrupacionPapel () throws Exception {
		try {
			agrupacionList = new ArrayList<AgrupacionPapel_AgrupacionVO>();
			AgrupacionPapel_AgrupacionVO agrupacionPapel_AgrupacionVO = agrupacionPapelManager.getAgrupacionPapel(codigoConfiguracion);
			agrupacionList.add(agrupacionPapel_AgrupacionVO);
			success = true;
			return SUCCESS;
			
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
	/**
	 * Metodo que permite actualizar un registro modificado en la pantalla de Agrupacion por papel.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGuardarClick () throws Exception {
		try {
			for (int i=0; i<lista.size(); i++) {
				AgrupacionPapel_AgrupacionVO agrupacionVO = lista.get(i);
				messageResult = agrupacionPapelManager.guardarAgrupacionPapel(codigoConfiguracion, agrupacionVO.getCdAgrRol(), agrupacionVO.getCdAgrupa(), agrupacionVO.getCdRol(), agrupacionVO.getCdUniEco(), agrupacionVO.getCdRamo(), agrupacionVO.getCdPolMtra());
			}
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}

	public String cmdActualizarClick () throws Exception {
		try {
			messageResult = agrupacionPapelManager.guardarAgrupacionPapel(codigoConfiguracion, cdAgrRol, cdAgrupa, cdRol, cdUniEco, cdRamo, cdPolMtra);
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = agrupacionPapelManager.borrarRol(cdAgrupa, cdRol);	
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
	 * Metodo que redirecciona a otra pantalla para editar una Agrupacion por papel.
	 * 
	 * @return string
	 * 
	 * @throws Exception
	 */
	public String cmdIrEditarAgrupacion () {
		return "getAgrupacionPapel";
	}


	/**
	 * Metodo que redirecciona a la pantalla agrupacion de polizas.
	 * 
	 * @return string
	 * 
	 * @throws Exception
	 */
	public String irAgrupacionPolizasClick() {
		return "agrupacionPolizas";
	}
	
	public String irAsignarPolizasClick() {
		return "consultaPolizasMaestras";
	}
	
	public List<AgrupacionPapel_AgrupacionVO> getAgrupacionList() {
		return agrupacionList;
	}
	public void setAgrupacionList(List<AgrupacionPapel_AgrupacionVO> agrupacionList) {
		this.agrupacionList = agrupacionList;
	}
	public String getCodigoConfiguracion() {
		return codigoConfiguracion;
	}
	public void setCodigoConfiguracion(String codigoConfiguracion) {
		this.codigoConfiguracion = codigoConfiguracion;
	}
	public String getCodigoNivel() {
		return codigoNivel;
	}
	
	public void setCodigoNivel(String codigoNivel) {
		this.codigoNivel = codigoNivel;
	}
	public String getCodigoAseguradora() {
		return codigoAseguradora;
	}
	public void setCodigoAseguradora(String codigoAseguradora) {
		this.codigoAseguradora = codigoAseguradora;
	}
	public String getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	public void setAgrupacionPapelManager(
			AgrupacionPapelManager agrupacionPapelManager) {
		this.agrupacionPapelManager = agrupacionPapelManager;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<AgrupacionPapel_AgrupacionVO> getLista() {
		return lista;
	}

	public void setLista(List<AgrupacionPapel_AgrupacionVO> lista) {
		this.lista = lista;
	}

	public String getCdRol() {
		return cdRol;
	}

	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}

	public String getCdAgrupa() {
		return cdAgrupa;
	}

	public void setCdAgrupa(String cdAgrupa) {
		this.cdAgrupa = cdAgrupa;
	}

	public String getDescripcionNivel() {
		return descripcionNivel;
	}

	public void setDescripcionNivel(String descripcionNivel) {
		this.descripcionNivel = descripcionNivel;
	}

	public String getDescripcionAseguradora() {
		return descripcionAseguradora;
	}

	public void setDescripcionAseguradora(String descripcionAseguradora) {
		this.descripcionAseguradora = descripcionAseguradora;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public String getCodigoAgrupacion() {
		return codigoAgrupacion;
	}

	public void setCodigoAgrupacion(String codigoAgrupacion) {
		this.codigoAgrupacion = codigoAgrupacion;
	}
	
	public String getCdAgrRol() {
		return cdAgrRol;
	}

	public void setCdAgrRol(String cdAgrRol) {
		this.cdAgrRol = cdAgrRol;
	}

	public String getCdPolMtra() {
		return cdPolMtra;
	}

	public void setCdPolMtra(String cdPolMtra) {
		this.cdPolMtra = cdPolMtra;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getCdUniEco() {
		return cdUniEco;
	}

	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}

	public String getCodigoTipo() {
		return codigoTipo;
	}

	public void setCodigoTipo(String codigoTipo) {
		this.codigoTipo = codigoTipo;
	}	
	
}
