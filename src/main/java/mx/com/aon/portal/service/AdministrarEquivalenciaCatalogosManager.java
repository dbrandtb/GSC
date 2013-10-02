package mx.com.aon.portal.service;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.AgrupacionPolizaVO;
import mx.com.aon.portal.model.EquivalenciaCatalogosVO;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Interface de servicios para Administrar Equivalencia de Catalogos.
 *
 */
public interface AdministrarEquivalenciaCatalogosManager{

	/**
	 *  Obtiene un conjunto de equivalencia de catalogos.
	 * 
	 *  @param start, limit: marcan el rango de la lista a obtener para la paginacion del grid.
	 *  @param cdPais
	 *  @param cdSistema
	 *  @param cdTablaAcw
	 *  @param cdTablaExt
	 *  @param indUso
	 *  @param dsUsoAcw
	 *  
	 *  @return un objeto PagedList con un conjunto de registros.
	 *  
	 *  @throws ApplicationException
	 */
    public PagedList buscarEquivalenciaCatalogos(String cdPais, String cdSistema, String cdTablaAcw, String cdTablaExt, String indUso, String dsUsoAcw, int start, int limit ) throws ApplicationException;
    
    /**
	 *  Inserta una nueva Equivalencia de Catalogo.
	 * 
	 *  @param equivalenciaCatalogosVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    public String agregarEquivalenciaCatalogo(EquivalenciaCatalogosVO equivalenciaCatalogosVO) throws ApplicationException;
    
    /**
	 *  Elimina una Equivalencia de Catálogos seleccionada.
	 * 
	 *  @param cdPais
	 *  @param cdSistema
	 *  @param cdSistema
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    public String borrarAgrupacionPoliza(String cdPais, String cdSistema, String nmTabla) throws ApplicationException;
    
    /**
	 *  Obtiene una Equivalencia de Catalogos seleccionada.
	 * 
	 *  @param cveAgrupa
	 *  
	 *  @return Objeto EquivalenciaCatalogosVO
	 *  
	 *  @throws ApplicationException
	 */
    //public EquivalenciaCatalogosVO getEquivalenciaCatalogo(String cveAgrupa) throws ApplicationException;
    
    /**
	 *  Obtiene una lista de registros de Equivalencia de Catálogos para la exportacion a un formato predeterminado.
	 * 
	 *  @param cdPais
	 *  @param cdSistema
	 *  @param cdTablaAcw
	 *  @param cdTablaExt
	 *  @param indUso
	 *  @param dsUsoAcw
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
    public TableModelExport getModel(String cdPais, String cdSistema, String cdTablaAcw, String cdTablaExt, String indUso, String dsUsoAcw) throws ApplicationException;

}
