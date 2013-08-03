package mx.com.aon.catbo.facade;

import java.util.List;

import mx.com.aon.catbo.service.AdministracionCasosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.ProcesoManager;
import mx.com.aon.catbo.model.ProcesosVO;
import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;

/**
 * User: gabrielforradellas
 * Date: Sep 23, 2008
 * Time: 10:26:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServicioBOServiceFacade extends AbstractFacade{

    public static Logger logger = Logger.getLogger(ServicioBOServiceFacade.class);



    public ServicioBOServiceFacade() throws ApplicationException{
        this.administracionCasosManager = (AdministracionCasosManager)getBeanFactory().getBean("administracionCasosManager");
        if (administracionCasosManager == null) {
            throw new ApplicationException("No se  pudo obtener la instancia del AdministracionCasosManager");
        }
        this.procesoManager = (ProcesoManager)getBeanFactory().getBean("procesoManager");
        if (procesoManager == null) {
            throw new ApplicationException("No se  pudo obtener la instancia del ProcesoManager");
        }
    }

    
    private AdministracionCasosManager administracionCasosManager;
    private ProcesoManager procesoManager;


    public void setAdministracionCasosManager(AdministracionCasosManager administracionCasosManager) {
        this.administracionCasosManager = administracionCasosManager;
    }

    public void setProcesoManager(ProcesoManager procesoManager) {
        this.procesoManager = procesoManager;
    }


  public ResultadoGeneraCasoVO guardarCasos(String pv_cdmatriz_i,
			String pv_cdusuario_i, String pv_cdunieco_i, String pv_cdramo_i,
			String pv_estado_i, String pv_nmsituac_i, String pv_nmsituaext_i,
			String pv_nmsbsitext_i,String pv_nmpoliza_i, String pv_nmpoliex_i,
			String pv_cdperson_i, String pv_dsmetcontact_i, String pv_ind_poliza_i,
			String pv_cdpriord_i, String pv_cdproceso_i, String pv_dsobservacion_i )throws ApplicationException {

      logger.info("invocando el guardarCasos ");
      logger.debug("Valores recibidos ");
      logger.debug("pv_cdmatriz_i: "+pv_cdmatriz_i);
      logger.debug("pv_cdusuario_i: "+pv_cdusuario_i);      
      logger.debug("pv_cdunieco_i: "+pv_cdunieco_i);
      logger.debug("pv_cdramo_i: "+pv_cdramo_i);
      logger.debug("pv_estado_i: "+pv_estado_i);


      logger.debug("pv_nmsituac_i: "+pv_nmsituac_i);
      logger.debug("pv_nmsituaext_i: "+pv_nmsituaext_i);
      logger.debug("pv_nmsbsitext_i: "+pv_nmsbsitext_i);
      logger.debug("pv_nmpoliza_i: "+pv_nmpoliza_i);
      logger.debug("pv_nmpoliex_i: "+pv_nmpoliex_i);

      logger.debug("pv_cdperson_i: "+pv_cdperson_i);
      logger.debug("pv_dsmetcontact_i: "+pv_dsmetcontact_i);
      logger.debug("pv_ind_poliza_i: "+pv_ind_poliza_i);
      logger.debug("pv_cdpriord_i: "+pv_cdpriord_i);
      logger.debug("pv_cdproceso_i: "+pv_cdproceso_i);
      logger.debug("pv_dsobservacion_i: "+pv_dsobservacion_i);

      return  administracionCasosManager.guardarCasos(pv_cdmatriz_i,pv_cdusuario_i,pv_cdunieco_i,pv_cdramo_i,pv_estado_i, pv_nmsituac_i,pv_nmsituaext_i,pv_nmsbsitext_i, pv_nmpoliza_i,pv_nmpoliex_i,pv_cdperson_i,
              pv_dsmetcontact_i,pv_ind_poliza_i,pv_cdpriord_i,pv_cdproceso_i,pv_dsobservacion_i);

  }

    public String guardarIds(String numeroCaso, String taskId, String instanceId )throws ApplicationException {

        logger.info("invocando el guardarCasos ");
        logger.debug("Valores recibidos ");
        logger.debug("numeroCaso: "+numeroCaso);

        return  procesoManager.guardarIdTareaIdInstancia(numeroCaso,taskId,instanceId);

    }

    public String obtenerTiempoCaso(String numeroCaso) throws ApplicationException {
    	logger.info("invocando obtener tiempo");
    	logger.info("Valores recibidos");
    	logger.info("numeroCaso: " + numeroCaso);
    	
    	return procesoManager.obtenerTiempoCaso(numeroCaso);
    }

    public String obtenerTiempoRestante(String numeroCaso) throws ApplicationException {
    	logger.info("invocando obtener tiempo restante");
    	logger.info("Valores recibidos");
    	logger.info("numeroCaso: " + numeroCaso);

    	return procesoManager.obtenerTiempoRestante(numeroCaso);
    }


    
    public List obtenerUsuarioProceso (String numeroCaso) throws ApplicationException {
    	logger.info("Invocando obtenerUsuarioProceso");
    	logger.info("Valores");
    	logger.info("numeroCaso: " + numeroCaso);

    	return procesoManager.obtenerUsuarioProceso(numeroCaso);
    }
    
/*



            sce.setError(true);
            sce.setMessage(errString);
            sce.setMessageDetail(Util.stackTraceToString(e));
            throw sce;
        }
    }

    /**
     *
     *
     * @param numeroCaso
     * @param formatoOrden
     * @throws ServicioCasosException
     */
    public void enviarOrdenTrabajo(String numeroCaso, Integer formatoOrden)  throws ApplicationException{
    }

    /**
     *
     *
     * @param numeroCaso
     * @param medio
     * @param responsables
     * @param formatoEnvio
     * @param motivoAlerta
     */
    public void enviarNotificacion(String numeroCaso, String medio,
                                   String responsables, Integer formatoEnvio,
                                   String motivoAlerta)  throws ApplicationException {
    }


    /**
     *
     *
     * @param numeroCaso
     * @param cdproceso
     * @param cdusuario
     * @param cdnivatn
     * @param nmcompra
     * @param tunidad
     * @return
     */
    public boolean validaCompraTiempo(String cdproceso, int cdnivatn, String nmCaso)  throws ApplicationException{

    	logger.info("Validando compra tiempo");
    	logger.info("Valores recibidos");
    	logger.info("cdproceso: " + cdproceso);
    	logger.info("cdnivatn: " + cdnivatn);
    	logger.info("nmCaso: " + nmCaso);
        String resultado = administracionCasosManager.validaCompraTiempo(cdproceso,cdnivatn,nmCaso);        
    	return (resultado!=null&&resultado.equals("1"));
    }

    /**
     *
     *
     * @param numeroCaso
     * @param cdproceso
     * @param cdusuario
     * @param cdnivatn
     * @param nmcompra
     * @param tunidad
     */
    public void guardarNuevoTiempo(String numeroCaso, String cdproceso,
                                   String cdusuario, int cdnivatn,
                                   int nmcompra, String tunidad)  throws ApplicationException {

    }


    /**
     *
     *
     * @param numeroCaso
     * @return
     * @throws ServicioCasosException
     */
    public void obtenerTiempoComprado(String numeroCaso)  throws ApplicationException{
    }


    /**
     *
     *
     * @throws ApplicationException
     */
    public void guardaReasignacion(String pv_nmcaso_i, String pv_cdnivatn,String pv_cdusuario_i, String pv_cdrolmat_i
                                   ) throws ApplicationException {

        logger.info("Entrando a guardaReasignacion");
        logger.info("Valores recibidos");
        logger.info("numeroCaso: " + pv_nmcaso_i);
        logger.info("pv_cdnivatn: " + pv_cdnivatn);
        logger.info("pv_cdusuario_i: " + pv_cdusuario_i);
        logger.info("pv_cdrolmat_i: " + pv_cdrolmat_i);

        administracionCasosManager.guardarReasignacion(pv_nmcaso_i,pv_cdnivatn,pv_cdusuario_i,pv_cdrolmat_i);

    }





    /**
     *
     *
     * @param numeroCaso
     * @return
     */
    public boolean validaEscalamiento(String numeroCaso) throws ApplicationException {
    	logger.info("Validando escalamiento");
    	logger.info("numeroCaso: " + numeroCaso);

        ProcesosVO procesosVO = procesoManager.getvalidaNivelEscalamiento(numeroCaso);
        
        return ((procesosVO.getValidaO().equals("1"))?true:false);
    }


    /**
     *
     *
     * @param numeroCaso
     *
     */
    public void guardarResponsablesAEscalar(String numeroCaso)
                throws ApplicationException {

    	logger.info("Entrando al guardarResponsablesAEscalar");
    	logger.info("Valores recibidos");
    	logger.info("numeroCaso: " + numeroCaso);

    	procesoManager.guardarResponsablesAEscalar(numeroCaso);
    }

    public void obtenerUsrTmpoEscalados(String numeroCaso) throws ApplicationException {

    }


    public List obtenerParametros(String grupo, String nombre) throws ApplicationException {
    	logger.info("Obteniendo parametros");
    	logger.info("grupo: " + grupo);
    	logger.info("nombre: " + nombre);
    	
    	return procesoManager.obtenerParametros(nombre, grupo);
    }

    // Guardar Movimiento

    public void guardarMovimiento(String numeroCaso, String cdusuario,
                                  String cdnivatn, String cdstatus, String cdmodulo,
                                  String cdpriord, String nmcompra,
                                  String tunidad, String dsobservacion) throws ApplicationException {
    	logger.info("Entrando a guardarMovimiento");
    	logger.info("Valores recibidos");
    	logger.info("numeroCaso: " + numeroCaso);
    	logger.info("cdusuario: " + cdusuario);
    	logger.info("cdnivatn: " + cdnivatn);
    	logger.info("cdstatus: " + cdstatus);
    	logger.info("cdmodulo: " + cdmodulo);
    	logger.info("cdpriord: " + cdpriord);
    	logger.info("nmcompra: " + nmcompra);
    	logger.info("tunidad: " + tunidad);
    	logger.info("dsobservacion: " + dsobservacion);

    	administracionCasosManager.guardaMovimientoGenerico(numeroCaso, cdusuario, cdnivatn, cdstatus, cdmodulo, cdpriord, nmcompra, tunidad, dsobservacion);
    }

    public List obtenerResponsablesEnvio()
            throws ApplicationException {
        logger.info("obteniendo los resposanbles de envio");

        return procesoManager.obtenerResponsablesEnvio();
    }

    public String guardaSuplente(int cdMatriz, int cdNivatn, String cdUsuarioOld, String cdUsuarioNew,String nmcaso,String cdusuario ) throws ApplicationException {

        logger.info("Entrando a guardaSuplente");
        logger.info("Valores recibidos");
        logger.info("cdMatriz: " + cdMatriz);
        logger.info("pv_cdnivatn_i: " + cdNivatn);
        logger.info("pv_cdusr_old_i: " + cdUsuarioOld);
        logger.info("pv_cdusr_new_i: " + cdUsuarioNew);        
        logger.info("pv_nmcaso_i: " + nmcaso);
        logger.info("pv_cdusuario_i: " + cdusuario);

        return administracionCasosManager.guardaSuplente(cdMatriz,cdNivatn,cdUsuarioOld,cdUsuarioNew,nmcaso,cdusuario);
    }


	public String guardarCompra(String pv_nmcaso_i,	String pv_cdusuario_i, String pv_cdnivatn_i, String pv_nmcompra_i,String pv_tunidad_i)
			throws ApplicationException {
        logger.info("Entrando a validar la compra de tiempo");
        logger.info("Valores recibidos");
        logger.info("pv_nmcaso_i: " + pv_nmcaso_i);
        logger.info("pv_cdusuario_i: " + pv_cdusuario_i);
        logger.info("pv_nmcompra_i: " + pv_nmcompra_i);
        logger.info("pv_tunidad_i: " + pv_tunidad_i);

        return administracionCasosManager.guardarCompra(pv_nmcaso_i,pv_cdusuario_i,pv_cdnivatn_i,pv_nmcompra_i,pv_tunidad_i);
    }



    public String guardarReasignacionCaso (String pv_nmcaso_i, String cdUsuarioMov, String cdUsuario, String cdRolmat) throws ApplicationException{

        logger.info("Entrando a guardar reasignacion del caso");
        logger.info("Valores recibidos");
        logger.info("pv_nmcaso_i: " + pv_nmcaso_i);
        logger.info("cdUsuarioMov: " + cdUsuarioMov);
        logger.info("cdUsuario: " + cdUsuario);
        logger.info("cdRolmat: " + cdRolmat);

        CasoVO casoVO = new CasoVO();
        casoVO.setNmCaso(pv_nmcaso_i);
        casoVO.setCdUsuMov(cdUsuarioMov);
        casoVO.setCdUsuario(cdUsuario);
        casoVO.setDesRolmat(cdRolmat);

        return administracionCasosManager.guardarReasignacionCaso(casoVO);
    }





    public String guardarReasignacion1(String pv_nmcaso_i, String pv_cdusumov_i,String pv_cdusuari_old_i, String pv_cdusuari_new_i)  throws ApplicationException {
        logger.info("Entrando a guardar reasignacion del caso");
        logger.info("Valores recibidos");
        logger.info("pv_nmcaso_i: " + pv_nmcaso_i);
        logger.info("pv_cdusumov_i: " + pv_cdusumov_i);
        logger.info("pv_cdusuari_old_i: " + pv_cdusuari_old_i);
        logger.info("pv_cdusuari_new_i: " + pv_cdusuari_new_i);

        return administracionCasosManager.guardarReasignacion1(pv_nmcaso_i,pv_cdusumov_i,pv_cdusuari_old_i,pv_cdusuari_new_i);

    }

    
    public String guardarReasignacion(String pv_nmcaso_i, String pv_cdusumov_i,String pv_cdusuario_i, String pv_cdrolmat_i)
            throws ApplicationException {

            logger.info("Entrando a guardar reasignacion del caso");
            logger.info("Valores recibidos");
            logger.info("pv_nmcaso_i: " + pv_nmcaso_i);
            logger.info("pv_cdusumov_i: " + pv_cdusumov_i);
            logger.info("pv_cdusuario_i: " + pv_cdusuario_i);
            logger.info("pv_cdrolmat_i: " + pv_cdrolmat_i);

            return administracionCasosManager.guardarReasignacion(pv_nmcaso_i,pv_cdusumov_i,pv_cdusuario_i,pv_cdrolmat_i);

        }




  }
