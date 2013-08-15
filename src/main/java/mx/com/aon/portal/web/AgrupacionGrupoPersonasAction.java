package mx.com.aon.portal.web;

import mx.com.aon.portal.model.AgrupacionGrupoPersonaVO;
import mx.com.aon.portal.model.GrupoPersonaVO;
import mx.com.aon.portal.service.AgrupacionGrupoPersonasManager;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;


/**
 *   Action que atiende las peticiones de que vienen de la pantalla Agrupacion Grupo Personas
 * 
 */
@SuppressWarnings("serial")
public class AgrupacionGrupoPersonasAction extends ActionSupport {
	
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AgrupacionGrupoPersonasAction.class);	

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 * Este objeto no es serializable
	 */
	private transient AgrupacionGrupoPersonasManager agrupacionGrupoPersonasManager;

	@SuppressWarnings("unchecked")
	private List mEstructuraList;
	
	private String cdGrupo;
	private String cdAgrGrupo;
	private String cdAgrupa;
	private String cdGrupoPer;
	private String cdPolMtra;
	
	private String descripcionNivel;
	private String descripcionAseguradora;
	private String descripcionProducto;
	private String codigoAgrupacion;
	private String codigoTipo;

	
	private boolean success;
	private List<GrupoPersonaVO> listaGrupoPersonaVO;
	
    /**
     * Metodo que atiende una peticion de insertar una nueva agrupacion grupo de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = agrupacionGrupoPersonasManager.borrarGrupo(cdGrupo, cdAgrGrupo);	
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
	
	
	
    public String cmdInsertarClick()throws Exception
    {
    	String messageResult = "";
        try
        {        	
        	for (int i=0; i<listaGrupoPersonaVO.size(); i++){
        		GrupoPersonaVO grupoPersonaVO = listaGrupoPersonaVO.get(i);
        		messageResult = agrupacionGrupoPersonasManager.agregarAgrupacionGrupoPersona(grupoPersonaVO);
			}        	
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
     * Metodo que atiende una peticion de actualizar una agrupacion grupo de persona
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGuardarClick()throws Exception
	{
		String messageResult = "";
        try
        {        	  
        	for (int i=0; i<listaGrupoPersonaVO.size(); i++){
        		GrupoPersonaVO grupoPersonaVO = listaGrupoPersonaVO.get(i);
        		messageResult = agrupacionGrupoPersonasManager.guardarAgrupacionGrupoPersona(grupoPersonaVO);
			}        	
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
	
	
	public String cmdActualizarClick()throws Exception
	{
		String messageResult = "";
        try
        {   
        	GrupoPersonaVO grupoPersonaVO = new GrupoPersonaVO();
        	grupoPersonaVO.setCdGrupo(cdGrupo);
        	grupoPersonaVO.setCdAgrGrupo(cdAgrGrupo);
        	grupoPersonaVO.setCdAgrupa(cdAgrupa);
        	grupoPersonaVO.setCdGrupoPer(cdGrupoPer);
        	grupoPersonaVO.setCdPolMtra(cdPolMtra);    	
        	
       		messageResult = agrupacionGrupoPersonasManager.guardarAgrupacionGrupoPersona(grupoPersonaVO);
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
	 * Metodo que atiende una peticion para obtener una agrupacion grupo de personas
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public String cmdGetClickAgp()throws Exception
	{      
		try
		{
			mEstructuraList=new ArrayList<AgrupacionGrupoPersonaVO>();
			AgrupacionGrupoPersonaVO agrupacionGrupoPersonaVO=agrupacionGrupoPersonasManager.getAgrupacionGrupoPersonas(cdGrupo);
			mEstructuraList.add(agrupacionGrupoPersonaVO);
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
	 * Metodo que atiende una peticion para dirigirse a la pagina agrupacion polizas
	 * 
	 * @return String
	 *  
     */
	public String irAgrupacionPolizasClick(){
		return "agrupacionPolizas";
	}
	
    /**
	 * Metodo que atiende una peticion para dirigirse a la pagina de polizas Maestras
	 * 
	 * @return String
	 *  
     */
	public String irAsignarPolizasClick() {
		return "consultaPolizasMaestras";
	}

	@SuppressWarnings("unchecked")
	public List getMEstructuraList() {return mEstructuraList;}
	
	public AgrupacionGrupoPersonasManager obtenAgrupacionGrupoPersonasManager() {
		return agrupacionGrupoPersonasManager;
	}


    public void setAgrupacionGrupoPersonasManager(AgrupacionGrupoPersonasManager agrupacionGrupoPersonasManager) {
        this.agrupacionGrupoPersonasManager = agrupacionGrupoPersonasManager;
    }

    public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	@SuppressWarnings("unchecked")
	public void setMEstructuraList(List estructuraList) {
		mEstructuraList = estructuraList;
	}

	public String getCdGrupo() {
		return cdGrupo;
	}

	public void setCdGrupo(String cdGrupo) {
		this.cdGrupo = cdGrupo;
	}

	public String getCdAgrGrupo() {
		return cdAgrGrupo;
	}

	public void setCdAgrGrupo(String cdAgrGrupo) {
		this.cdAgrGrupo = cdAgrGrupo;
	}

	public String getCdAgrupa() {
		return cdAgrupa;
	}

	public void setCdAgrupa(String cdAgrupa) {
		this.cdAgrupa = cdAgrupa;
	}

	public String getCdGrupoPer() {
		return cdGrupoPer;
	}

	public void setCdGrupoPer(String cdGrupoPer) {
		this.cdGrupoPer = cdGrupoPer;
	}

	public List<GrupoPersonaVO> getListaGrupoPersonaVO() {
		return listaGrupoPersonaVO;
	}

	public void setListaGrupoPersonaVO(List<GrupoPersonaVO> listaGrupoPersonaVO) {
		this.listaGrupoPersonaVO = listaGrupoPersonaVO;
	}



	public String getCdPolMtra() {
		return cdPolMtra;
	}



	public void setCdPolMtra(String cdPolMtra) {
		this.cdPolMtra = cdPolMtra;
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



	public String getCodigoTipo() {
		return codigoTipo;
	}



	public void setCodigoTipo(String codigoTipo) {
		this.codigoTipo = codigoTipo;
	}

	
}