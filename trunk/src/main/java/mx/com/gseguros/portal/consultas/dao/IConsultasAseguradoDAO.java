package mx.com.gseguros.portal.consultas.dao;

import java.util.List;

import mx.com.gseguros.portal.consultas.model.AseguradoDetalleVO;
import mx.com.gseguros.portal.consultas.model.AseguradoVO;
import mx.com.gseguros.portal.consultas.model.CoberturasBasicasVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosComplementariosVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosContratanteVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosGeneralesPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosHistoricoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPlanVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosTitularVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPeriodosVigenciaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaActualVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaResultadosAseguradoVO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
import mx.com.gseguros.portal.consultas.model.EndosoVO;
import mx.com.gseguros.portal.consultas.model.EnfermedadVO;
import mx.com.gseguros.portal.consultas.model.HistoricoFarmaciaVO;
import mx.com.gseguros.portal.general.model.PolizaVO;

public interface IConsultasAseguradoDAO {
	/**
     * Obtiene los asegurados asociados a un criterio de búsqueda
     * @param rfc RFC del asegurado
     * @param cdperson C&oacute;digo de persona del asegurado
     * @param nombre Nombre del asegurado
     * @return ConsultaResultadosAseguradoVO
     * @throws Exception
     */
	public List<ConsultaResultadosAseguradoVO> obtieneResultadosAsegurado(String rfc, String cdperson, String nombre) throws Exception;
	
	/**
	 * 
	 * @param polizaAsegurado
	 * @return
	 * @throws Exception
	 */
	public List<ConsultaPolizaActualVO> obtienePolizaActual(ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception;
	
	/**
     * Obtiene los datos complementarios de la p&oacute;liza
     * @param asegurado
     * @return
     * @throws Exception
     */
    public List<ConsultaDatosComplementariosVO> obtieneDatosComplementarios(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
    /**
     * Obtiene los datos generales de la p&oacute;liza
     * @param polizaAsegurado
     * @return
     * @throws Exception
     */
    public List<ConsultaDatosGeneralesPolizaVO> obtieneDatosPoliza(ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception;
    
    /**
     * Obtiene el detalle del asegurado
     * @param asegurado
     * @return
     * @throws Exception
     */
    public List<AseguradoDetalleVO> obtieneAseguradoDetalle(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
    /**
     * Obtiene datos del titular
     * @param poliza
     * @param asegurado
     * @return
     */
    public List<ConsultaDatosTitularVO> obtieneDatosTitular(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
    /**
     * Obtiene datos del contratante
     * @param poliza
     * @return
     */
    public List<ConsultaDatosContratanteVO> obtieneDatosContratante(PolizaVO poliza) throws Exception;
    
    
    /**
     * Obtiene los asegurados de la p&oacute;liza
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<AseguradoVO> obtieneAsegurados(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
    /**
     * Obtiene los endosos del asegurado
     * @param poliza
     * @param asegurado
     * @return
     * @throws Exception
     */
    public List<EndosoVO> obtieneEndososPoliza(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
    /**
     * Obtiene las enfermedades crónicas del asegurado
     * @param poliza
     * @param asegurado
     * @return
     * @throws Exception
     */
    public List<EnfermedadVO> obtieneEnfermedades(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
    
    /**
     * Obtiene los datos del plan
     * @param poliza
     * @return
     */
    public List<ConsultaDatosPlanVO> obtieneDatosPlan(PolizaVO poliza) throws Exception;
    
    /**
     * Obtiene los copagos de la p&oacute;liza
     * @param poliza
     * @return
     */
    public List<CopagoVO> obtieneCopagosPoliza(PolizaVO poliza) throws Exception;
    
    /**
     * Obtiene las coberturas
     * @param poliza
     * @return
     */
    public List<CoberturasBasicasVO> obtieneCoberturasPoliza(PolizaVO poliza) throws Exception;
    
    /**
     * Obtiene las coberturas básicas
     * @param poliza
     * @return
     */
    public List<CoberturasBasicasVO> obtieneCoberturasBasicas(PolizaVO poliza) throws Exception;
    
    /**
	 * 
	 * @param polizaAsegurado
	 * @return
	 * @throws Exception
	 */
	public List<ConsultaDatosHistoricoVO> obtieneHistoricoAsegurado(ConsultaPolizaAseguradoVO polizaAsegurado, AseguradoVO asegurado) throws Exception;
	
	/**
     * Obtiene histórico de farmacia
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<HistoricoFarmaciaVO> obtieneHistoricoFarmacia(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
    /**
     * Obtiene periodos de vigencia
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<ConsultaPeriodosVigenciaVO> obtienePeriodosVigencia(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
}
