package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import mx.com.aon.portal.service.AgrupacionPolizasManager;
import mx.com.aon.portal.model.EstructuraVO;
import mx.com.aon.portal.model.AgrupacionPolizaVO;
import mx.com.aon.core.ApplicationException;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Agrupacion de polizas.
 * 
 */
public class AgrupacionPolizasAction extends ActionSupport {

	private static final long serialVersionUID = 178787999654L;
	public static String TIPO_AGRUPACION_PAPEL = "1";
    public static String TIPO_AGRUPACION_PERSONAS = "2";
	private String codigo;
    private String descripcion;
    /**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AgrupacionPolizaVO
	 * con los valores de la consulta.
	 */
    @SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(AgrupacionPolizasAction.class);
	@SuppressWarnings("unchecked")
	private List agrupacionVO;
    private transient AgrupacionPolizasManager agrupacionPolizasManager;
    private String cveAgrupa;
    private String cdElemento;
    private String cdPerson;
    private String cdUnieco;
    private String cdTipram;
    private String cdRamo;
    private String cdTipo;
    private String cdEstado;
    /**
	 * Respuesta para JSON
	 */
	private boolean success;
	
	
    /**
	 * Metodo que inserta una nueva agrupacion de poliza.
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
            AgrupacionPolizaVO agrupacionPolizaVO = new AgrupacionPolizaVO();
            
            agrupacionPolizaVO.setCdPerson(cdPerson);
            agrupacionPolizaVO.setCdTipram(cdTipram);
            agrupacionPolizaVO.setCdUnieco(cdUnieco);
            agrupacionPolizaVO.setCdRamo(cdRamo);
            agrupacionPolizaVO.setCdTipo(cdTipo);
            agrupacionPolizaVO.setCdEstado(cdEstado);
            agrupacionPolizaVO.setCdElemento(cdElemento);
            
            String messageResult = agrupacionPolizasManager.agregarAgrupacionPoliza(agrupacionPolizaVO);
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
	 * Metodo que obtiene una agrupacion de poliza seleccionada del grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 *
	 */
	@SuppressWarnings("unchecked")
	public String cmdGetClick()throws Exception
	{
		try
		{
            agrupacionVO = new ArrayList<EstructuraVO>();
            AgrupacionPolizaVO agrupacionPolizaVO  = agrupacionPolizasManager.getAgrupacionPoliza(cveAgrupa);
            agrupacionVO.add(agrupacionPolizaVO);
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
     * Metodo que permite actualizar una agrupacion de poliza modificada en la pantalla Agrupacion de Polizas.
     * 
     * @return success
     * 
     * @throws Exception
     *
     */
    public String cmdGuardarClick()throws Exception
    {
        try
        {
            AgrupacionPolizaVO agrupacionPolizaVO = new AgrupacionPolizaVO();
            agrupacionPolizaVO.setCdPerson(cdPerson);
            agrupacionPolizaVO.setCdTipram(cdTipram);
            agrupacionPolizaVO.setCdUnieco(cdUnieco);
            agrupacionPolizaVO.setCdRamo(cdRamo);
            agrupacionPolizaVO.setCdTipo(cdTipo);
            agrupacionPolizaVO.setCdEstado(cdEstado);
            agrupacionPolizaVO.setCdElemento(cdElemento);
            agrupacionPolizaVO.setCdGrupo(cveAgrupa);
            String messageResult = agrupacionPolizasManager.guardarAgrupacionPoliza(agrupacionPolizaVO);
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
	 * Metodo que elimina una agrupacion de poliza seleccionada en el grid.
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
            String messageResult = agrupacionPolizasManager.borrarAgrupacionPoliza(cveAgrupa);
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
    public String cmdIrConfigurarAgrupacion() throws Exception
    {
        if (cdTipo== null || cdTipo.equals("")) {
            success = false;
            addActionError("Tipo de Agrupacion nulo O no seleccionado");
            return SUCCESS;
        }
        if (cdTipo.equals(TIPO_AGRUPACION_PAPEL)) {
            return "agrupacionPapel";
        }
        if (cdTipo.equals(TIPO_AGRUPACION_PERSONAS)) {
            return "agrupacionGrupoPersonas";
        } else {
            success = false;
            addActionError("Tipo de Agrupacion indefinido o nulo");
            return SUCCESS;
        }
     }

	public void setAgrupacionPolizasManager(AgrupacionPolizasManager agrupacionPolizasManager) {
	        this.agrupacionPolizasManager = agrupacionPolizasManager;
	    }
	 
    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @SuppressWarnings("unchecked")
	public List getAgrupacionVO() {
        return agrupacionVO;
    }

    @SuppressWarnings("unchecked")
	public void setAgrupacionVO(List agrupacionVO) {
        this.agrupacionVO = agrupacionVO;
    }

    public String getCveAgrupa() {
        return cveAgrupa;
    }

    public void setCveAgrupa(String cveAgrupa) {
        this.cveAgrupa = cveAgrupa;
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

    public String getCdUnieco() {
        return cdUnieco;
    }

    public void setCdUnieco(String cdUnieco) {
        this.cdUnieco = cdUnieco;
    }

    public String getCdTipram() {
        return cdTipram;
    }

    public void setCdTipram(String cdTipram) {
        this.cdTipram = cdTipram;
    }

    public String getCdRamo() {
        return cdRamo;
    }

    public void setCdRamo(String cdRamo) {
        this.cdRamo = cdRamo;
    }

    public String getCdTipo() {
        return cdTipo;
    }

    public void setCdTipo(String cdTipo) {
        this.cdTipo = cdTipo;
    }

    public String getCdEstado() {
        return cdEstado;
    }

    public void setCdEstado(String cdEstado) {
        this.cdEstado = cdEstado;
    }
}
