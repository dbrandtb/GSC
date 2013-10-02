package mx.com.aon.portal.service.impl;

import java.util.HashMap;

import org.apache.log4j.Logger;

import mx.com.aon.portal.model.AgrupacionPapel_AgrupacionVO;
import mx.com.aon.portal.service.AgrupacionPapelManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Clase que implementa la interfaz AgrupacionPapelManagerImpl.
 * 
 * @extends AbstractManager
 *
 */
public class AgrupacionPapelManagerImpl extends AbstractManager implements
		AgrupacionPapelManager {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(AgrupacionGrupoPersonasManagerImpl.class);
	
	/**
	 * Metodo que realiza la busqueda y obtiene un unico registro de agrupacion por papel.
	 * Invoca al Store Procedure PKG_AGRUPAPOL.P_OBTIENE_AGRUPACION.
	 * 
	 * @param codConfiguracion
	 * 
	 * @return AgrupacionPapel_AgrupacionVO
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public AgrupacionPapel_AgrupacionVO getAgrupacionPapel(
			String codigoConfiguracion) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codigoConfiguracion", codigoConfiguracion);
		return (AgrupacionPapel_AgrupacionVO)getBackBoneInvoke(map, "AGRUPACIONPAPEL_OBTIENE_AGRUPACION");
	}
	
	/**
	 * Metodo que realiza la actualizacion de un registro modificado de Agrupacion por papel.
	 * Invoca al Store Procedure PKG_AGRUPAPOL.P_GUARDA_PAPELES.
	 * 
	 * @param codigoConfiguracion
	 * @param codigoAgrRol
	 * @param codigoNivel
	 * @param codigoRol
	 * @param codigoAseguradora
	 * @param codigoProducto
	 * 
	 * @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String guardarAgrupacionPapel(String codigoConfiguracion, String codigoAgrRol,
			String codigoNivel, String codigoRol, String codigoAseguradora,
			String codigoProducto, String cdPolMtra) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codigoAgrupacion", codigoConfiguracion);
		map.put("codigoAgrRol", codigoAgrRol);
		map.put("codigoNivel", codigoNivel);
		map.put("codigoRol", codigoRol);
		map.put("codigoAseguradora", codigoAseguradora);
		map.put("codigoProducto", codigoProducto);
		map.put("cdPolMtra", cdPolMtra);

        WrapperResultados res =  returnBackBoneInvoke(map,"AGRUPACIONPAPEL_GUARDA_PAPELES");
        return res.getMsgText();
        
    }
	
	/**
	 * Metodo que realiza la busqueda y obtiene un conjunto de registros.
	 * Invoca al Store Procedure PKG_LISTAS.P_ASEGURADORA.
	 * 
	 * @param codConfiguracion
	 * @param start
	 * @param limit
	 * 
	 * @return PagedList
	 * 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList buscarPapeles(String codConfiguracion, int start, int limit)
			throws ApplicationException {

		HashMap map = new HashMap();
		map.put("codConfiguracion", codConfiguracion);

		return pagedBackBoneInvoke(map, "AGRUPACION_PAPEL_OBTIENE_PAPELES", start, limit);
	}
	
	 
	 @SuppressWarnings("unchecked")
	    public String borrarRol(String pv_agrupacion_i, String pv_cdagrrol_i)throws ApplicationException {
	    	// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			map.put("pv_agrupacion_i",pv_agrupacion_i);
			map.put("pv_cdagrrol_i",pv_cdagrrol_i);
	        WrapperResultados res =  returnBackBoneInvoke(map,"AGRUPACION_PAPEL_BORRAR_ROL");
	            return res.getMsgText();
		}
	
	
	
	

}