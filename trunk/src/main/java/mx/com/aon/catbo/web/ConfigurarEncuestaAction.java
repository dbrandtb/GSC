package mx.com.aon.catbo.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.AsignarEncuestaVO;
import mx.com.aon.catbo.model.ConfigurarEncuestaVO;
import mx.com.aon.catbo.model.EncuestaPreguntaVO;
import mx.com.aon.catbo.model.EncuestaVO;
import mx.com.aon.catbo.service.ConfigurarEncuestaManager;
import mx.com.aon.catbo.service.ConfigurarEncuestaManager2;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class ConfigurarEncuestaAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 131245454489L;
	
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConfigurarEncuestaAction.class);
	
	private transient ConfigurarEncuestaManager configurarEncuestaManager;
	private transient ConfigurarEncuestaManager2 configurarEncuestaManager2;
	private List<ConfigurarEncuestaVO> csoGrillaListAtr;
	private List<EncuestaVO> mEstructuraList;
	
	
	private String nmConfig;
	private String cdUnieco;
	private String cdRamo;
	private String cdElemento;
	private String cdProceso;
	private String cdCampan;
	private String cdModulo;
	private String cdEncuesta;
	private String fedesde_i;
	private String fehasta_i;
	private String pv_nmconfig_i;
	private String pv_cdproceso_i;
	private String pv_cdcampan_i;
	private String pv_cdmodulo_i;
	private String pv_cdencuesta_i;
	private String nmUnidad;
	private String cdUnidtmpo;
	
	private String estado;
	private String nmPoliza;
	private String cdPerson;
	
	private String status;
	
	private String cdUsuario;
	
	private String  pv_cdcampana_i;
	private String   pv_cdunieco_i;
	private String   pv_cdramo_i;
	
	
	private String  pv_estado_i;
	private String  pv_nmpoliza_i;
	private String  pv_cdperson_i;
	private String  pv_cdusuario_i;
	
	
	private String dsEncuesta;
	private String swEstado;
	
	private EncuestaPreguntaVO encuestaPreguntaVO;
	
	private String codEncuesta;
	private String cdPregunta;
	
	private boolean success;
	
	private String valida_i;
	
	private String validaMio;
	
	
	public String cmdGuardarConfiguracionEncuestaClick() throws Exception{
		String messageResult = "";
		try{
			
			ConfigurarEncuestaVO configurarEncuestaVO = new ConfigurarEncuestaVO();
			
			
			configurarEncuestaVO.setNmConfig(nmConfig);
			configurarEncuestaVO.setCdUnieco(cdUnieco);
			configurarEncuestaVO.setCdRamo(cdRamo);
			configurarEncuestaVO.setCdElemento(cdElemento);
			configurarEncuestaVO.setCdProceso(cdProceso);
			configurarEncuestaVO.setCdCampan(cdCampan);
			configurarEncuestaVO.setCdModulo(cdModulo);
			configurarEncuestaVO.setCdEncuesta(cdEncuesta);
			configurarEncuestaVO.setFedesde_i(fedesde_i);
			configurarEncuestaVO.setFehasta_i(fehasta_i);
			
			messageResult = configurarEncuestaManager2.guardarConfigurarEncuesta(configurarEncuestaVO);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }/*catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
            

        }*/catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }

	}
	
	public String cmdGuardarConfiguracionEncuestaEditarClick() throws Exception{
		String messageResult = "";
		try{
			
			ConfigurarEncuestaVO configurarEncuestaVO = new ConfigurarEncuestaVO();
			
			
			configurarEncuestaVO.setNmConfig(nmConfig);
			configurarEncuestaVO.setCdUnieco(cdUnieco);
			configurarEncuestaVO.setCdRamo(cdRamo);
			configurarEncuestaVO.setCdElemento(cdElemento);
			configurarEncuestaVO.setCdProceso(cdProceso);
			configurarEncuestaVO.setCdCampan(cdCampan);
			configurarEncuestaVO.setCdModulo(cdModulo);
			configurarEncuestaVO.setCdEncuesta(cdEncuesta);
			configurarEncuestaVO.setFedesde_i(fedesde_i);
			configurarEncuestaVO.setFehasta_i(fehasta_i);
			
			messageResult = configurarEncuestaManager2.guardarConfigurarEncuestaEditar(configurarEncuestaVO);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }/*catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }*/catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }

	}
	public String cmdGuardarAsignacionEncuestaClick() throws Exception{
		String messageResult = "";
		try{
			
			AsignarEncuestaVO asignarEncuestaVO = new AsignarEncuestaVO();
			
			
			asignarEncuestaVO.setNmConfig(nmConfig);
			asignarEncuestaVO.setCdUnieco(cdUnieco);
			asignarEncuestaVO.setCdRamo(cdRamo);
			asignarEncuestaVO.setEstado(estado);
			asignarEncuestaVO.setNmPoliza(nmPoliza);
			asignarEncuestaVO.setCdPerson(cdPerson);
			asignarEncuestaVO.setStatus(status);
			asignarEncuestaVO.setCdUsuario(cdUsuario);
						
			messageResult = configurarEncuestaManager.guardaAsignacionEncuesta(asignarEncuestaVO);
            success = true;
            addActionMessage(messageResult);
            return SUCCESS;
        }/*catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }*/catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }

	}
	
	
	public String cmdGuardarTiempoConfigEncuestaClick() throws Exception{
		String messageResult = "";
		try{
			
			ConfigurarEncuestaVO configurarEncuestaVO = new ConfigurarEncuestaVO();
			
			configurarEncuestaVO.setCdCampan(cdCampan);
			configurarEncuestaVO.setCdRamo(cdRamo);
			configurarEncuestaVO.setCdUnieco(cdUnieco);
			configurarEncuestaVO.setNmUnidad(nmUnidad);
			configurarEncuestaVO.setCdUnidtmpo(cdUnidtmpo);
			
			messageResult = configurarEncuestaManager.guardaTiempoEncuesta(configurarEncuestaVO);
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
	
	
	public String cmdGuardarEncuestaClick() throws Exception{
		String messageResult = "";
		try{
			
			ConfigurarEncuestaVO configurarEncuestasVO = new ConfigurarEncuestaVO();
			
			configurarEncuestasVO.setCdEncuesta(cdEncuesta);
			configurarEncuestasVO.setDsEncuesta(dsEncuesta);
			configurarEncuestasVO.setSwEstado(swEstado);
			
			messageResult = configurarEncuestaManager.guardaTiempoEncuestaTencuest(configurarEncuestasVO);
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
	
	
	
	public String insertarGuardarPreguntasEncuesta() throws ApplicationException{
		String messageResult = "";
		try{
			
			ConfigurarEncuestaVO configurarEncuestasVO = new ConfigurarEncuestaVO();
			
			for (int i=0; i<csoGrillaListAtr.size(); i++) {
				
				ConfigurarEncuestaVO configurarEncuestasVO_grid = csoGrillaListAtr.get(i);
				
				configurarEncuestasVO.setCdEncuesta(configurarEncuestasVO_grid.getCdEncuesta()); 
				configurarEncuestasVO.setCdPregunta(configurarEncuestasVO_grid.getCdPregunta());
				configurarEncuestasVO.setDsPregunta(configurarEncuestasVO_grid.getDsPregunta());
				configurarEncuestasVO.setSwobliga(configurarEncuestasVO_grid.getSwobliga());
				configurarEncuestasVO.setCdsecuencia(configurarEncuestasVO_grid.getCdsecuencia());
				configurarEncuestasVO.setCddefault(configurarEncuestasVO_grid.getCddefault());

				messageResult = configurarEncuestaManager.guardaTpregunta(configurarEncuestasVO);
				
			}
			success = true;
			addActionMessage(messageResult);
			return SUCCESS;
		}catch(ApplicationException ae){
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch(Exception e){
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
    /**
	 * Metodo <code>cmdGuardarEncuestaNuevoClick</code> Agrega nueva encuesta desde la Pantalla.
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */		
	
	public String cmdGuardarEncuestaNuevaClick()throws Exception
    {
        try
        {
       	 BackBoneResultVO backBoneResultVO = configurarEncuestaManager.insertarNuevaEncuesta(encuestaPreguntaVO);
       	 codEncuesta = backBoneResultVO.getOutParam();
       	 addActionMessage(backBoneResultVO.getMsgText());
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
	 * Metodo que obtiene una encuesta seleccionada en el grid.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String obtenerEncuestaPreguntasGetClick()throws Exception
	{
		try
		{
			mEstructuraList=new ArrayList<EncuestaVO>();
			//EncuestaVO encuestaVO = configurarEncuestaManager.getEncuestaPregunta(cdEncuesta);
			EncuestaVO encuestaVO = configurarEncuestaManager2.getEncuestaPregunta(cdEncuesta);
			mEstructuraList.add(encuestaVO);
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
	 * Metodo <code>cmdGuardarEncuestaNuevoClick</code> Agrega nueva encuesta desde la Pantalla.
	 *
	 * @return success
	 *  
	 * @throws Exception
	 *
	 */		
	
	public String cmdGuardarEncuestaAgregarClick()throws Exception
    {
        try
        {
       	 BackBoneResultVO backBoneResultVO = configurarEncuestaManager.agregarNuevaEncuesta(encuestaPreguntaVO);
       	 codEncuesta = backBoneResultVO.getOutParam();
       	 addActionMessage(backBoneResultVO.getMsgText());
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
	
	public String cmdBorrarEncuestaPreguntaClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.configurarEncuestaManager.borrarTpregunt(cdPregunta);
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
	
	public String cmdBorrarEncuestaClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.configurarEncuestaManager.borraTiempoEncuesta(pv_cdencuesta_i);
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
	 * Metodo que elimina responsables para una matriz de asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarConfigEncuestaClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.configurarEncuestaManager.borrarEncuestaRegistro(pv_nmconfig_i, pv_cdproceso_i, pv_cdcampan_i, pv_cdmodulo_i, pv_cdencuesta_i);
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
	 * Metodo que elimina responsables para una matriz de asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarTiempoConfigEncuestaClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.configurarEncuestaManager.borraCATBOTiempoEncuesta(pv_cdcampana_i, pv_cdunieco_i, pv_cdramo_i);
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
	 * Metodo que elimina responsables para una matriz de asignacion.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdBorrarAsignacionEncuestaClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.configurarEncuestaManager.borraAsignaEncuesta(pv_nmconfig_i, pv_cdunieco_i, pv_cdramo_i, pv_estado_i, pv_nmpoliza_i, pv_cdperson_i, pv_cdusuario_i);
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
	
	
	public String cmdBorrarValoresEncuestaClick() throws Exception{
		String messageResult = "";
		try{
			messageResult = this.configurarEncuestaManager.borrarTvalenct(pv_nmconfig_i, pv_cdunieco_i, pv_cdramo_i, pv_nmpoliza_i, pv_estado_i, pv_cdencuesta_i);
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
	
	public String cmdValidaConfiguracionEncuestaClick(){
		WrapperResultados result = new WrapperResultados();
		try{
			result = this.configurarEncuestaManager.validaConfiguracionEncuesta(cdModulo, cdUnieco, cdRamo, cdElemento);
			//valida_i = result.getResultado();
            validaMio = result.getResultado().toString();
			success = true;
            addActionMessage(result.getMsgText());
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

	public ConfigurarEncuestaManager obtenConfigurarEncuestaManager() {
		return configurarEncuestaManager;
	}

	public void setConfigurarEncuestaManager(
			ConfigurarEncuestaManager configurarEncuestaManager) {
		this.configurarEncuestaManager = configurarEncuestaManager;
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

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getCdProceso() {
		return cdProceso;
	}

	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}

	public String getCdCampan() {
		return cdCampan;
	}

	public void setCdCampan(String cdCampan) {
		this.cdCampan = cdCampan;
	}

	public String getCdModulo() {
		return cdModulo;
	}

	public void setCdModulo(String cdModulo) {
		this.cdModulo = cdModulo;
	}

	public String getCdEncuesta() {
		return cdEncuesta;
	}

	public void setCdEncuesta(String cdEncuesta) {
		this.cdEncuesta = cdEncuesta;
	}

	public String getFedesde_i() {
		return fedesde_i;
	}

	public void setFedesde_i(String fedesde_i) {
		this.fedesde_i = fedesde_i;
	}

	public String getFehasta_i() {
		return fehasta_i;
	}

	public void setFehasta_i(String fehasta_i) {
		this.fehasta_i = fehasta_i;
	}

	public String getNmConfig() {
		return nmConfig;
	}

	public void setNmConfig(String nmConfig) {
		this.nmConfig = nmConfig;
	}

	public String getPv_nmconfig_i() {
		return pv_nmconfig_i;
	}

	public void setPv_nmconfig_i(String pv_nmconfig_i) {
		this.pv_nmconfig_i = pv_nmconfig_i;
	}

	public String getPv_cdproceso_i() {
		return pv_cdproceso_i;
	}

	public void setPv_cdproceso_i(String pv_cdproceso_i) {
		this.pv_cdproceso_i = pv_cdproceso_i;
	}

	public String getPv_cdcampan_i() {
		return pv_cdcampan_i;
	}

	public void setPv_cdcampan_i(String pv_cdcampan_i) {
		this.pv_cdcampan_i = pv_cdcampan_i;
	}

	public String getPv_cdmodulo_i() {
		return pv_cdmodulo_i;
	}

	public void setPv_cdmodulo_i(String pv_cdmodulo_i) {
		this.pv_cdmodulo_i = pv_cdmodulo_i;
	}

	public String getPv_cdencuesta_i() {
		return pv_cdencuesta_i;
	}

	public void setPv_cdencuesta_i(String pv_cdencuesta_i) {
		this.pv_cdencuesta_i = pv_cdencuesta_i;
	}

	public String getNmUnidad() {
		return nmUnidad;
	}

	public void setNmUnidad(String nmUnidad) {
		this.nmUnidad = nmUnidad;
	}

	public String getCdUnidtmpo() {
		return cdUnidtmpo;
	}

	public void setCdUnidtmpo(String cdUnidtmpo) {
		this.cdUnidtmpo = cdUnidtmpo;
	}

	public String getPv_cdcampana_i() {
		return pv_cdcampana_i;
	}

	public void setPv_cdcampana_i(String pv_cdcampana_i) {
		this.pv_cdcampana_i = pv_cdcampana_i;
	}

	public String getPv_cdunieco_i() {
		return pv_cdunieco_i;
	}

	public void setPv_cdunieco_i(String pv_cdunieco_i) {
		this.pv_cdunieco_i = pv_cdunieco_i;
	}

	public String getPv_cdramo_i() {
		return pv_cdramo_i;
	}

	public void setPv_cdramo_i(String pv_cdramo_i) {
		this.pv_cdramo_i = pv_cdramo_i;
	}

	public String getPv_estado_i() {
		return pv_estado_i;
	}

	public void setPv_estado_i(String pv_estado_i) {
		this.pv_estado_i = pv_estado_i;
	}

	public String getPv_nmpoliza_i() {
		return pv_nmpoliza_i;
	}

	public void setPv_nmpoliza_i(String pv_nmpoliza_i) {
		this.pv_nmpoliza_i = pv_nmpoliza_i;
	}

	public String getPv_cdperson_i() {
		return pv_cdperson_i;
	}

	public void setPv_cdperson_i(String pv_cdperson_i) {
		this.pv_cdperson_i = pv_cdperson_i;
	}

	public String getPv_cdusuario_i() {
		return pv_cdusuario_i;
	}

	public void setPv_cdusuario_i(String pv_cdusuario_i) {
		this.pv_cdusuario_i = pv_cdusuario_i;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getNmPoliza() {
		return nmPoliza;
	}


	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}


	public String getCdPerson() {
		return cdPerson;
	}


	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCdUsuario() {
		return cdUsuario;
	}


	public void setCdUsuario(String cdUsuario) {
		this.cdUsuario = cdUsuario;
	}


	public List<ConfigurarEncuestaVO> getCsoGrillaListAtr() {
		return csoGrillaListAtr;
	}


	public void setCsoGrillaListAtr(List<ConfigurarEncuestaVO> csoGrillaListAtr) {
		this.csoGrillaListAtr = csoGrillaListAtr;
	}


	public String getDsEncuesta() {
		return dsEncuesta;
	}


	public void setDsEncuesta(String dsEncuesta) {
		this.dsEncuesta = dsEncuesta;
	}


	public String getSwEstado() {
		return swEstado;
	}


	public void setSwEstado(String swEstado) {
		this.swEstado = swEstado;
	}


	public EncuestaPreguntaVO getEncuestaPreguntaVO() {
		return encuestaPreguntaVO;
	}


	public void setEncuestaPreguntaVO(EncuestaPreguntaVO encuestaPreguntaVO) {
		this.encuestaPreguntaVO = encuestaPreguntaVO;
	}


	public String getCodEncuesta() {
		return codEncuesta;
	}


	public void setCodEncuesta(String codEncuesta) {
		this.codEncuesta = codEncuesta;
	}


	public List<EncuestaVO> getMEstructuraList() {
		return mEstructuraList;
	}


	public void setMEstructuraList(List<EncuestaVO> estructuraList) {
		mEstructuraList = estructuraList;
	}


	public String getCdPregunta() {
		return cdPregunta;
	}


	public void setCdPregunta(String cdPregunta) {
		this.cdPregunta = cdPregunta;
	}
	
	public String getValida_i() {
		return valida_i;
	}


	public void setValida_i(String valida_i) {
		this.valida_i = valida_i;
	}

	public void setConfigurarEncuestaManager2(
			ConfigurarEncuestaManager2 configurarEncuestaManager2) {
		this.configurarEncuestaManager2 = configurarEncuestaManager2;
	}

	public String getValidaMio() {
		return validaMio;
	}

	public void setValidaMio(String validaMio) {
		this.validaMio = validaMio;
	}

}
