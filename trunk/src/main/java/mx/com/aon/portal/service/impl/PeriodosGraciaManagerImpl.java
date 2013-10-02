package mx.com.aon.portal.service.impl;

import mx.com.aon.portal.service.PeriodosGraciaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.PeriodosGraciaVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.export.model.TableModelExport;
import mx.com.gseguros.exception.ApplicationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements PeriodosGraciaManager
 * 
 * @extends AbstractManager
 */
public class PeriodosGraciaManagerImpl extends AbstractManager implements PeriodosGraciaManager {
      
	/**
	 *  Obtiene un conjunto de periodos de gracia
	 *  Hace uso del Store Procedure PKG_CANCELA.P_OBTIENE_PERIODOS
	 * 
	 *  @param descripcion
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public PagedList buscarPeriodosGracia(String descripcion, int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_dstramo_i", descripcion);
        
        return pagedBackBoneInvoke(map, "BUSCA_PERIODOSGRACIA", start, limit);
    }

	/**
	 *  Elimina una configuracion de periodo de gracia
	 *  Hace uso del Store Procedure PKG_CANCELA.P_BORRA_PERIODO_GRACIA
	 * 
	 *  @param cdTramo
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public String borrarPeriodosGracia(String cdTramo) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdtramo_i", cdTramo);

        WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_PERIODOSGRACIA");
        return res.getMsgText();
    }
    
	/**
	 *  Inserta o actualiza un periodo de gracia
	 *  Hace uso del Store Procedure PKG_CANCELA.P_GUARDA_PERIODO_GRACIA
	 * 
	 *  @param periodosGraciaVO
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public String agregarGuardarPeriodosGracia(PeriodosGraciaVO periodosGraciaVO) throws ApplicationException {
        // Se crea un mapa para pasar los parametros de ejecucion al endpoint
       
    	HashMap map = new HashMap();
       
        map.put("pv_cdtramo_i",periodosGraciaVO.getCdTramo());
        map.put("pv_dstramo_i",periodosGraciaVO.getDsTramo());
        map.put("pv_nmminimo_i",periodosGraciaVO.getNmMinimo());
        map.put("pv_nmmaximo_i",periodosGraciaVO.getNmMaximo());
        map.put("pv_diasgrac_i",periodosGraciaVO.getDiasGrac());
        map.put("pv_diascanc_i",periodosGraciaVO.getDiasCanc());
               
       
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_PERIODOSGRACIA");
        return res.getMsgText();
    }

	/**
	 *  Obtiene un periodo de gracia en particular
	 *  Hace uso del Store Procedure PKG_CANCELA.P_EDITA_PERIODO_GRACIA
	 * 
	 *  @param cdTramo
	 *  
	 *  @return Objeto PeriodosGraciaVO
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	   public PeriodosGraciaVO getPeriodosGracia(String cdTramo) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdtramo_i", cdTramo);

        return (PeriodosGraciaVO)getBackBoneInvoke(map,"OBTIENE_PERIODOSGRACIA");
    }


    /**
	 *  Obtiene un conjunto de personas para la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure  PKG_CANCELA.P_OBTIENE_PERIODOS
	 *  
	 *  @param descripcion
	 *  @param minimo
	 *  @param maximo
	 *  @param diasGracias
	 *  @param diasCancela
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */    
    @SuppressWarnings("unchecked")    
	public TableModelExport getModel(String descripcion, String minimo, String maximo, String diasGracias, String diasCancela ) throws ApplicationException {

		TableModelExport model = new TableModelExport();		
		List lista = null;
		HashMap map = new HashMap();
		map.put("pv_cdtramo_i", descripcion);
				
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_PERIODOSGRACIA_EXPORT");

		model.setInformation(lista);

		model.setColumnName(new String[]{"Descripcion","Del Recibo","Al Recibo","Dias de Gracia","Dias antes Cancelar"});
		return model;
    }
    
}
