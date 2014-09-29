package mx.com.gseguros.portal.consultas.dao;

import java.util.List;

import mx.com.gseguros.portal.consultas.model.ConsultaDatosAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSuplementoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaReciboAgenteVO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
import mx.com.gseguros.portal.general.model.AgenteVO;
import mx.com.gseguros.portal.general.model.DetalleReciboVO;
import mx.com.gseguros.portal.general.model.PolizaVO;
import mx.com.gseguros.portal.general.model.ReciboVO;


public interface IConsultasPolizaDAO {
		
    /**
     * Obtiene las polizas asociadas al asegurado
     * @param rfc RFC del asegurado
     * @param cdperson C&oacute;digo de persona del asegurado
     * @param nombre Nombre del asegurado
     * @return Polizas asociadas al asegurado
     * @throws Exception
     */
	public List<ConsultaPolizaAseguradoVO> obtienePolizasAsegurado(String rfc, String cdperson, String nombre) throws Exception;
	
	
	/**
	 * Obtiene el hist&oacute;rico (suplementos) de una p&oacute;liza
	 * @param nmpoliex N&uacute;mero externo/completo de la p&oacute;liza
	 * @return El hist&oacute;rico (suplementos) de la p&oacute;liza
	 * @throws Exception
	 */
	public List<ConsultaDatosSuplementoVO> obtieneHistoricoPoliza(String nmpoliex, String icodpoliza) throws Exception;
	
	
	/**
	 * 
	 * @param polizaAsegurado
	 * @return
	 * @throws Exception
	 */
	public List<ConsultaDatosSuplementoVO> obtieneHistoricoPoliza(ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception;
	
	
	/**
	 * Obtiene los datos generales de la p&oacute;liza
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @return
	 * @throws Exception
	 */
    public List<ConsultaDatosPolizaVO> obtieneDatosPoliza(String cdunieco, String cdramo, String estado, String nmpoliza, String icodpoliza) throws Exception;
    
    
    /**
     * Obtiene los datos generales de la p&oacute;liza
     * @param polizaAsegurado
     * @return
     * @throws Exception
     */
    public List<ConsultaDatosPolizaVO> obtieneDatosPoliza(ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception;
    
    
    /**
     * Obtiene los copagos de la p&oacute;liza
     * @param poliza
     * @return
     */
    public List<CopagoVO> obtieneCopagosPoliza(PolizaVO poliza) throws Exception;
    
    
    /**
     * Obtiene los asegurados de la p&oacute;liza
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<ConsultaDatosAseguradoVO> obtieneAsegurados(PolizaVO poliza) throws Exception;
    
    
    /**
     * Obtiene los recibos del agente asociados a una p&oacute;liza
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<ConsultaReciboAgenteVO> obtieneRecibosAgente(PolizaVO poliza) throws Exception;
    
    
    /**
     * Obtiene los datos de un recibo asociado a una p&oacute;liza
     * @param poliza
     * @param recibo
     * @return
     * @throws Exception
     */
    public List<DetalleReciboVO> obtieneDetalleRecibo(PolizaVO poliza, ReciboVO recibo) throws Exception;
    
    
    /**
     * Obtiene los agentes asociados a una p&oacute;liza
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<AgenteVO> obtieneAgentesPoliza(PolizaVO poliza) throws Exception;
    
    
    /**
     * Obtiene los recibos asociados a una p&oacute;liza
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<ConsultaReciboAgenteVO> obtieneRecibosPoliza(PolizaVO poliza) throws Exception;
    
	
}