package mx.com.aon.portal.service.impl;

import java.util.HashMap;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.FormatoOrdenesTrabajoVO;
import mx.com.aon.portal.service.FormatoOrdenesTrabajoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.log4j.Logger;


/**
 * Implementa la interface de servicios para formato ordenes de trabajo.
 *
 */
public class FormatoOrdenesTrabajoManagerImpl extends AbstractManager implements FormatoOrdenesTrabajoManager {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(EstructuraManagerImpl.class);
	

	/**
	 *  Realiza la baja de formato de ordenes de trabajo.
	 *  Usa el store procedure PKG_ORDENT.P_BORRA_FORMATO.
	 * 
	 *  @param cdFormatoOrden
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	@SuppressWarnings("unchecked")
	public String borrarFormatoOrdenesTrabajo(String cdFormatoOrden) throws ApplicationException{
	
		HashMap map = new HashMap();
		map.put("cdFormatoOrden",cdFormatoOrden);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_FORMATO_ORDENES_TRABAJO");
        return res.getMsgText();
	}
	


	/**
	 *  Salva el detalle del producto de descuento.
	 *  Usa el store procedure PKG_ORDENT.P_GUARDA_FORMATO.
	 * 
	 *  @param formatoOrdenesTrabajoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	@SuppressWarnings("unchecked")
	public String guardarFormatoOrdenesTrabajo(FormatoOrdenesTrabajoVO formatoOrdenesTrabajoVO) throws ApplicationException{
			HashMap map = new HashMap();
			map.put("cdFormatoOrden",formatoOrdenesTrabajoVO.getCdFormatoOrden());
			map.put("dsFormatoOrden",formatoOrdenesTrabajoVO.getDsFormatoOrden());
	
            WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_FORMATO_ORDENES_TRABAJO");
            return res.getMsgText();
    }
	

	/**
	 *  Obtiene el formato de ordenes de trabajo.
	 *  Usa el store procedure PKG_ORDENT.P_OBTIENE_FORMATO.
	 * 
	 *  @param cdFormatoOrden
	 *  
	 *  @return FormatoOrdenesTrabajoVO
	 */		
	@SuppressWarnings("unchecked")
	public FormatoOrdenesTrabajoVO getFormatoOrdenesTrabajo (String cdFormatoOrden) throws ApplicationException
	{

			HashMap map = new HashMap();
			map.put("cdFormatoOrden",cdFormatoOrden);
						
            return (FormatoOrdenesTrabajoVO)getBackBoneInvoke(map,"OBTIENE_FORMATO_ORDENES_TRABAJO");

	}
   

	/**
	 *  Realiza la copia de formato de ordenes de trabajo
	 *  Usa el store procedure PKG_ORDENT.P_COPIA_FORMATO.
	 * 
	 *  @param cdFormatoOrden
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	@SuppressWarnings("unchecked")
	public String copiarFormatoOrdenesTrabajo(String cdFormatoOrden)throws ApplicationException
	{
			HashMap map = new HashMap();
			map.put("cdFormatoOrden",cdFormatoOrden);
			
            WrapperResultados res =  returnBackBoneInvoke(map,"COPIA_FORMATO_ORDENES_TRABAJO");
            return res.getMsgText();
	}


	/**
	 *  Obtiene un conjunto de formato de ordenes de trabajo.
	 *  Usa el store procedure PKG_ORDENT.P_OBTIENE_FORMATOS.
	 *  
	 *  @param dsFormatoOrden
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList 
	 */	
	@SuppressWarnings("unchecked")
	public PagedList buscarFormatoOrdenesTrabajo(String dsFormatoOrden, int start, int limit) throws ApplicationException
	{
			// Se crea un mapa para pasar los parametros de ejecucion al endpoint
			HashMap map = new HashMap();
			map.put("dsFormatoOrden",dsFormatoOrden);
			map.put("start",start);
			map.put("limit",limit);
            return pagedBackBoneInvoke(map, "BUSCAR_FORMATO_ORDENES_TRABAJO", start, limit);

	}
    
}


	
	
	


