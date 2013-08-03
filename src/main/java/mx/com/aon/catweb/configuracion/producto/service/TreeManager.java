package mx.com.aon.catweb.configuracion.producto.service;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.catweb.configuracion.producto.expresiones.model.RamaVO;
import mx.com.aon.core.ApplicationException;

public interface TreeManager {

	public List<RamaVO> obtenerNiveles(String tipoObjeto,
			ArrayList<String> parametros) throws ApplicationException;

	List<RamaVO> obtenerProductos() throws ApplicationException;

	List<RamaVO> obtenerEstructuraProducto() throws ApplicationException;

	List<RamaVO> obtenerEstructuraSituacion() throws ApplicationException;

	public List<RamaVO> obtenerEstructuraCobertura()
			throws ApplicationException;

	public List<RamaVO> obtenerEstructuraObjetos() throws ApplicationException;
	
	List<RamaVO> obtenerEstructuraPlanes() throws ApplicationException ;
}
