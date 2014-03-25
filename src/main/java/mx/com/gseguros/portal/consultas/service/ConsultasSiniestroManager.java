package mx.com.gseguros.portal.consultas.service;

import java.util.List;

import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSiniestrosVO;

/**
 * Interface de servicios para Consulta de Poliza
 * 
 * @author HMLT
 *
 */
public interface ConsultasSiniestroManager {
	
	public List<ConsultaDatosSiniestrosVO> getConsultaAseguradosPagoReembolso(String cdperson)throws ApplicationException;
	
}