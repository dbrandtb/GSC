package mx.com.gseguros.portal.consultas.service.impl;

import java.util.List;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.consultas.dao.ConsultasSiniestroDAO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSiniestrosVO;
import mx.com.gseguros.portal.consultas.service.ConsultasSiniestroManager;

public class ConsultasSiniestroManagerImpl implements ConsultasSiniestroManager {
	
	private ConsultasSiniestroDAO consultasSiniestroDAO;
		
	@Override
	public List<ConsultaDatosSiniestrosVO> getConsultaAseguradosPagoReembolso(String cdperson) throws ApplicationException {
		try {
			return consultasSiniestroDAO.obtieneConsultaAseguradosPagoReembolso(cdperson);
		} catch (DaoException daoExc) {
			throw new ApplicationException(daoExc.getMessage(), daoExc);
		}
	}

	public void setConsultasSiniestroDAO(ConsultasSiniestroDAO consultasSiniestroDAO) {
		this.consultasSiniestroDAO = consultasSiniestroDAO;
	}
	
}