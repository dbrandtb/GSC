
package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.EjecutivoCuentaVO;
import mx.com.aon.portal.service.EjecutivosCuentaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.log4j.Logger;


/**
 * Interface de implementacion de servicios para ejecutivos por cuenta.
 *
 */
public class EjecutivosCuentaManagerImpl extends AbstractManager implements EjecutivosCuentaManager {
	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(EjecutivosCuentaManagerImpl.class);
	

	/**
	 *  Obtiene un ejecutivo de cuenta.
	 *  Usa el Store Procedure PKG_CONFIGURA.P_OBTIENE_EJECUTIVO.
	 * 
	 *  @param cdAgente
	 *  @param cdPerson
	 *  
	 *  @return EjecutivoCuentaVO
	 */			
	@SuppressWarnings("unchecked")
	public EjecutivoCuentaVO getEjecutivoCuenta(String cdAgente, String cdElemento)throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdAgente",cdAgente);
		map.put("cdElemento",cdElemento);
		return (EjecutivoCuentaVO)getBackBoneInvoke(map,"OBTIENE_EJECUTIVO_CUENTA_REG");
	}
	

	
	/**
	 *  Agrega un nuevo ejecutivo a la cuenta.
	 *  Usa el Store Procedure PKG_CONFIGURA.P_GUARDA_EJECUTIVO.
	 * 
	 *  @param ejecutivoCuentaVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */		
	@SuppressWarnings("unchecked") // Manejo de ArrayList Controlado
	public String agregarGuardarEjecutivoCuenta(EjecutivoCuentaVO ejecutivoCuentaVO) throws ApplicationException {
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		
		map.put("cdAgente",ejecutivoCuentaVO.getCdAgente());
        map.put("cdPerson",ejecutivoCuentaVO.getCdPerson());
        map.put("cdEstado",ejecutivoCuentaVO.getCdEstado());
		map.put("feInicio",ejecutivoCuentaVO.getFeInicio());
		map.put("feFin",ejecutivoCuentaVO.getFeFin());
        map.put("cdTipRam",ejecutivoCuentaVO.getCdTipRam());
        map.put("cdRamo",ejecutivoCuentaVO.getCdRamo());
        map.put("cdLinCta",ejecutivoCuentaVO.getCdLinCta());
		map.put("swNivelPosterior",ejecutivoCuentaVO.getSwNivelPosterior());
        map.put("cdGrupo",ejecutivoCuentaVO.getCdGrupo());
        map.put("cdElemento",ejecutivoCuentaVO.getCdElemento());
        map.put("p_accion", ejecutivoCuentaVO.getAccion());
        
        WrapperResultados res =  returnBackBoneInvoke(map,"INSERTA_GUARDA_EJECUTIVO_CUENTA");
        return res.getMsgText();
	}
	
	
	
	/**
	 *  Relaiza la baja de un ejecutivo de cuenta.
	 *  Usa el Store Procedure PKG_CONFIGURA.P_ELIMINA_EJECUTIVO.
	 * 
	 *  @param cdAgente
	 *  @param cdElemento
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio. 
	 */		    
    @SuppressWarnings("unchecked")
    public String borrarEjecutivoCuenta(String cdAgente, String cdElemento)throws ApplicationException {
    	// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("cdAgente",cdAgente);
		map.put("cdElemento",cdElemento);
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_EJECUTIVO_CUENTA");
        return res.getMsgText();
	}
    
    
	/**
	 *  Obtiene un conjunto de ejecutivos por cuenta.
	 *  Usa el Store Procedure PKG_CONFIGURA.P_OBTIENE_EJECUTIVOS.
	 * 
	 *  @param dsNombre
	 *  @param nomAgente
	 *  @param desGrupo
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList 
	 */		
	@SuppressWarnings("unchecked")
	public PagedList buscarEjecutivosCuenta(String dsNombre, String nomAgente,String desGrupo, int start, int limit) throws ApplicationException {
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("dsNombre",dsNombre);
		map.put("nomAgente",nomAgente);
		map.put("desGrupo",desGrupo);
		return pagedBackBoneInvoke(map, "OBTIENE_EJECUTIVOS_CUENTA", start, limit);
	}
	
	public TableModelExport getModel(String dsNombre, String nomAgente,String desGrupo) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("dsNombre",dsNombre);
		map.put("nomAgente",nomAgente);
		map.put("desGrupo",desGrupo);
				
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_EJECUTIVOS_CUENTA_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Cliente","Ramo","Producto","Grupo de Personas","Ejecutivo"});
		return model;
   }

}
