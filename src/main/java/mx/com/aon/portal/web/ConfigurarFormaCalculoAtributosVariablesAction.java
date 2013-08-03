package mx.com.aon.portal.web;

import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import mx.com.aon.portal.service.ConfigurarFormaCalculoAtributosVariablesManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.model.ConfigurarFormaCalculoAtributoVariableVO;
import mx.com.aon.core.ApplicationException;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Configurar Forma de Calculo Variables
 * 
 */
@SuppressWarnings("serial")
public class ConfigurarFormaCalculoAtributosVariablesAction extends ActionSupport {


	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConfigurarFormaCalculoAtributosVariablesAction.class);


	/**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 */
	private transient ConfigurarFormaCalculoAtributosVariablesManager configurarFormaCalculoAtributosVariablesManager;
    
    private List<ConfigurarFormaCalculoAtributoVariableVO> aotEstructuraList;
    
  
	private String cdIden;
    private String cdElemento;
    private String cdUnieco;
    private String cdRamo;
    private String cdTipSit;
    private String cdTabla;
    private String swFormaCalculo;
    private String cdError;

	private boolean success;

   /**
     * Metodo que atiende una peticion de obtener una forma de calculo variable
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdGetClick()throws Exception
	{
		try
		{
			aotEstructuraList=new ArrayList<ConfigurarFormaCalculoAtributoVariableVO>();
			ConfigurarFormaCalculoAtributoVariableVO configurarFormaCalculoAtributoVariableVO=configurarFormaCalculoAtributosVariablesManager.getConfigurarFormaCalculoAtributo(cdIden);
			aotEstructuraList.add(configurarFormaCalculoAtributoVariableVO);
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
     * Metodo que atiende una peticion de obtener una forma de calculo variable para copiar
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetCopiaClick()throws Exception
	{
		try
		{
			aotEstructuraList=new ArrayList<ConfigurarFormaCalculoAtributoVariableVO>();
			ConfigurarFormaCalculoAtributoVariableVO configurarFormaCalculoAtributoVariableVO=configurarFormaCalculoAtributosVariablesManager.getConfigurarFormaCalculoAtributoCopia(cdIden);
			aotEstructuraList.add(configurarFormaCalculoAtributoVariableVO);
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
     * Metodo que atiende una peticion de insertar una forma de calculo variable
	 * 
	 * @return success
	 * 
	 * @throws Exception
     */
	public String cmdAgregarClick()throws Exception
	{
		String messageResult = "";
		try
		{
			ConfigurarFormaCalculoAtributoVariableVO configurarFormaCalculoAtributoVariableVO = new ConfigurarFormaCalculoAtributoVariableVO();
            
			configurarFormaCalculoAtributoVariableVO.setCdIden(cdIden);
			configurarFormaCalculoAtributoVariableVO.setCdElemento(cdElemento);
			configurarFormaCalculoAtributoVariableVO.setCdUnieco(cdUnieco);
			configurarFormaCalculoAtributoVariableVO.setCdRamo(cdRamo);   
			configurarFormaCalculoAtributoVariableVO.setCdTipSit(cdTipSit);
			configurarFormaCalculoAtributoVariableVO.setCdTabla(cdTabla);            
			configurarFormaCalculoAtributoVariableVO.setSwFormaCalculo(swFormaCalculo);

            messageResult = configurarFormaCalculoAtributosVariablesManager.agregarConfigurarFormaCalculoAtributo(configurarFormaCalculoAtributoVariableVO);
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
     * Metodo que atiende una peticion de actualizar una forma de calculo variable
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
			ConfigurarFormaCalculoAtributoVariableVO configurarFormaCalculoAtributoVariableVO = new ConfigurarFormaCalculoAtributoVariableVO();
            
			configurarFormaCalculoAtributoVariableVO.setCdIden(cdIden);
			configurarFormaCalculoAtributoVariableVO.setCdUnieco(cdUnieco);			
			configurarFormaCalculoAtributoVariableVO.setCdRamo(cdRamo);
			configurarFormaCalculoAtributoVariableVO.setCdElemento(cdElemento);
			configurarFormaCalculoAtributoVariableVO.setCdTipSit(cdTipSit);
			configurarFormaCalculoAtributoVariableVO.setCdTabla(cdTabla);            
			configurarFormaCalculoAtributoVariableVO.setSwFormaCalculo(swFormaCalculo);

            messageResult = configurarFormaCalculoAtributosVariablesManager.guardarConfigurarFormaCalculoAtributo(configurarFormaCalculoAtributoVariableVO);
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
	 * Elimina un registro de la grilla previamente seleccionado
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = getConfigurarFormaCalculoAtributosVariablesManager().borrarConfigurarFormaCalculoAtributo(cdIden);	
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
	 * Copia un registro de la grilla previamente seleccionado
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdCopiarClick() throws Exception{
		try{
			
			ConfigurarFormaCalculoAtributoVariableVO configurarFormaCalculoAtributoVariableVO = new ConfigurarFormaCalculoAtributoVariableVO();
			configurarFormaCalculoAtributoVariableVO.setCdIden(cdIden);
			configurarFormaCalculoAtributoVariableVO.setCdUnieco(cdUnieco);
			configurarFormaCalculoAtributoVariableVO.setCdRamo(cdRamo);
			configurarFormaCalculoAtributoVariableVO.setCdElemento(cdElemento); 	
			WrapperResultados res= getConfigurarFormaCalculoAtributosVariablesManager().copiarConfigurarFormaCalculoAtributo(configurarFormaCalculoAtributoVariableVO);		
            cdError=res.getMsgId();
			success = true;
            addActionMessage(res.getMsgText());
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


    public boolean getSuccess() {return success;}

	public void setSuccess(boolean success) {this.success = success;}


	public ConfigurarFormaCalculoAtributosVariablesManager getConfigurarFormaCalculoAtributosVariablesManager() {
		return configurarFormaCalculoAtributosVariablesManager;
	}

	public void setConfigurarFormaCalculoAtributosVariablesManager(
			ConfigurarFormaCalculoAtributosVariablesManager configurarFormaCalculoAtributosVariablesManager) {
		this.configurarFormaCalculoAtributosVariablesManager = configurarFormaCalculoAtributosVariablesManager;
	}

	public List<ConfigurarFormaCalculoAtributoVariableVO> getAotEstructuraList() {
		return aotEstructuraList;
	}

	public void setAotEstructuraList(
			List<ConfigurarFormaCalculoAtributoVariableVO> aotEstructuraList) {
		this.aotEstructuraList = aotEstructuraList;
	}

	public String getCdIden() {
		return cdIden;
	}

	public void setCdIden(String cdIden) {
		this.cdIden = cdIden;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getCdUnieco() {
		return cdUnieco;
	}

	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getCdTipSit() {
		return cdTipSit;
	}

	public void setCdTipSit(String cdTipSit) {
		this.cdTipSit = cdTipSit;
	}

	public String getCdTabla() {
		return cdTabla;
	}

	public void setCdTabla(String cdTabla) {
		this.cdTabla = cdTabla;
	}

	public String getSwFormaCalculo() {
		return swFormaCalculo;
	}

	public void setSwFormaCalculo(String swFormaCalculo) {
		this.swFormaCalculo = swFormaCalculo;
	}

	public String getCdError() {
		return cdError;
	}

	public void setCdError(String cdError) {
		this.cdError = cdError;
	}

}
