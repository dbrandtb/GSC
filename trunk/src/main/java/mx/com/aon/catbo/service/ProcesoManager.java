package mx.com.aon.catbo.service;

import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.ProcesosVO;
import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;
import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.ReasignacionCasoVO;
import mx.com.aon.portal.service.PagedList;

public interface ProcesoManager {

    public PagedList buscarAsociarFormatos(String dsProceso, String dsElemen, String dsRamo, String dsFormatoOrden, String dsUnieco, int start, int limit ) throws ApplicationException;

    public String obtenerTiempoCaso(String idCaso ) throws ApplicationException;
    
 //  public ResultadoGeneraCasoVO obtenerUsuarioProceso(String pv_nmcaso_i) throws ApplicationException;

    public String obtenerTiempoRestante(String pv_nmcaso_i) throws ApplicationException;
   
    public List obtenerUsuarioProceso(String pv_nmcaso_i) throws ApplicationException;

    public String guardarIdTareaIdInstancia(String pv_nmcaso_i, String pv_task_id_i,  String pv_instance_id_i ) throws ApplicationException;
    
    public String guardarResponsablesAEscalar(String pv_nmcaso_i) throws ApplicationException;
    
    public ProcesosVO getvalidaNivelEscalamiento(String pv_nmcaso_i)throws ApplicationException;
    
    public List obtenerParametros(String pv_clave_i, String pv_grupo_i) throws ApplicationException;
    
   public String obtieneTaskId(String pv_nmcaso_i)throws ApplicationException;
   
   public List obtenerResponsablesEnvio()throws ApplicationException;
   
   public PagedList obtenerMailResponsables(String pv_cdusuario_i, int start,	int limit) throws ApplicationException;
   
   public PagedList obtenerMailCliente(String pv_cdperson_i, int start, int limit) throws ApplicationException;
  
    public String cancelarProceso(List<CasoVO> listMCasoMovVO, String usuario, String status) throws ApplicationException;

    public String reasignarCasos(List casos, String estado) throws ApplicationException;

    public String guardaReasignacion(String numCaso, String cdUsuarioMov, List<ReasignacionCasoVO> listaReasignacionCasoVO) throws ApplicationException;

    public String guardaSuplente(String cdmatriz,String cdnivatn,String cdusrOld,String cdusrNew,String usuario) throws ApplicationException;

    public String guardaCompraTiempo( String nmcaso, String cdproceso, String cdusuario, String cdnivatn, String nmcompra, String tunidad) throws ApplicationException;
    
    public List obtieneCasos(String cdmatriz, String cdusuario, String cdnivatn, String cdusuarioNew,String cdusuarioOld) throws ApplicationException;

}
