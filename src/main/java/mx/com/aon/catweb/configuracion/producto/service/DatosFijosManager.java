package mx.com.aon.catweb.configuracion.producto.service;

import java.util.List;

import mx.com.aon.catweb.configuracion.producto.datosFijos.model.DatoFijoVO;
import mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO;
import mx.com.aon.core.ApplicationException;

public interface DatosFijosManager {
	List<DatoFijoVO> listaDatosFijos(String codigoRamo) throws ApplicationException;
	List<LlaveValorVO> catalogoBloque() throws ApplicationException;
	List<LlaveValorVO> catalogoCampo(String claveBloque) throws ApplicationException;
	void insertarDatoFijo(DatoFijoVO dfvo) throws ApplicationException;
}
