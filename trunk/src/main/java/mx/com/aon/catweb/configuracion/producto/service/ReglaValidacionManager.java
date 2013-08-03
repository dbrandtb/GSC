package mx.com.aon.catweb.configuracion.producto.service;

import java.util.List;

import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.catweb.configuracion.producto.reglaValidacion.model.ReglaValidacionVO;
import mx.com.aon.core.ApplicationException;

public interface ReglaValidacionManager {

	List<ReglaValidacionVO> obtieneReglasDeValidacion(String codigoRamo) throws ApplicationException;

	List<LlaveValorVO> obtieneListaBloques()throws ApplicationException;

	List<LlaveValorVO> obtieneListaValidaciones()throws ApplicationException;

	List<LlaveValorVO> obtieneListaCondiciones() throws ApplicationException;

	void asociarReglaDeValidacion(ReglaValidacionVO reglaValidacion,
			String codigoRamo) throws ApplicationException;

	void eliminaReglaDeValidacion(String codigoRamo, String codigoBloque,
			String secuencia)throws ApplicationException;

}
