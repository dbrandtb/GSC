
package mx.com.aon.portal.web;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.AtributoVaribleAgenteVO;
import mx.com.aon.portal.service.AtributosVariablesAgenteManager;

import org.apache.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;

/**
 *   Action que atiende las peticiones de abm que vienen de la pantalla Atributos Variables por Agente.
 * 
 */
public class AtributosVariablesAgenteAction extends ActionSupport {

	private static final long serialVersionUID = 1987012565454L;
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AtributosVariablesAgenteAction.class);
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AtributosVariablesAgenteManager atributosVariablesAgenteManager;
	
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	private String cdAtribu;
	private String dsAtribu;
	private String swFormat;
	private String nmlMax;
	private String nmlMin;
	private String swObliga;
	private String otTabVal;  
	private String gbSwFormat;
	
	private List<AtributoVaribleAgenteVO> grillaListAtrVrbAgt;

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
			messageResult = atributosVariablesAgenteManager.borrarAtributosVariablesAgente(cdAtribu);	
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
        	AtributoVaribleAgenteVO atributoVaribleAgenteVO = new AtributoVaribleAgenteVO();
	    	
	    	for (int i=0; i<grillaListAtrVrbAgt.size(); i++) {
	    		
	    		AtributoVaribleAgenteVO atributoVaribleAgenteVO_grid=grillaListAtrVrbAgt.get(i);
	    		
	    		
	        	atributoVaribleAgenteVO.setCdAtribu(atributoVaribleAgenteVO_grid.getCdAtribu());
	        	atributoVaribleAgenteVO.setDsAtribu(atributoVaribleAgenteVO_grid.getDsAtribu());
	        	atributoVaribleAgenteVO.setSwFormat(atributoVaribleAgenteVO_grid.getSwFormat());
	        	atributoVaribleAgenteVO.setNmlMax(atributoVaribleAgenteVO_grid.getNmlMax());
	        	atributoVaribleAgenteVO.setNmlMin(atributoVaribleAgenteVO_grid.getNmlMin());
	        	String _swObliga = (atributoVaribleAgenteVO_grid.getSwObliga().equals("true"))?"S":"N";
	        	atributoVaribleAgenteVO._setSwObliga(_swObliga);
	        	atributoVaribleAgenteVO.setOtTabVal(atributoVaribleAgenteVO_grid.getOtTabVal());
	        	atributoVaribleAgenteVO.setGbSwFormat(atributoVaribleAgenteVO_grid.getGbSwFormat());

	        	messageResult = atributosVariablesAgenteManager.guardarAtributosVariablesAgente(atributoVaribleAgenteVO);
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
	


    public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCdAtribu() {
		return cdAtribu;
	}

	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}

	public String getDsAtribu() {
		return dsAtribu;
	}

	public void setDsAtribu(String dsAtribu) {
		this.dsAtribu = dsAtribu;
	}

	public String getSwFormat() {
		return swFormat;
	}

	public void setSwFormat(String swFormat) {
		this.swFormat = swFormat;
	}

	public String getNmlMax() {
		return nmlMax;
	}

	public void setNmlMax(String nmlMax) {
		this.nmlMax = nmlMax;
	}

	public String getNmlMin() {
		return nmlMin;
	}

	public void setNmlMin(String nmlMin) {
		this.nmlMin = nmlMin;
	}

	public String getSwObliga() {
		return swObliga;
	}

	public void setSwObliga(String swObliga) {
		this.swObliga = swObliga;
	}

	public String getOtTabVal() {
		return otTabVal;
	}

	public void setOtTabVal(String otTabVal) {
		this.otTabVal = otTabVal;
	}

	public String getGbSwFormat() {
		return gbSwFormat;
	}

	public void setGbSwFormat(String gbSwFormat) {
		this.gbSwFormat = gbSwFormat;
	}

	public void setAtributosVariablesAgenteManager(
			AtributosVariablesAgenteManager atributosVariablesAgenteManager) {
		this.atributosVariablesAgenteManager = atributosVariablesAgenteManager;
	}

	public List<AtributoVaribleAgenteVO> getGrillaListAtrVrbAgt() {
		return grillaListAtrVrbAgt;
	}

	public void setGrillaListAtrVrbAgt(
			List<AtributoVaribleAgenteVO> grillaListAtrVrbAgt) {
		this.grillaListAtrVrbAgt = grillaListAtrVrbAgt;
	}

	
}