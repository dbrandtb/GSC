package mx.com.aon.catbo.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.FormatoDocumentoVO;
import mx.com.aon.catbo.service.FormatosDocumentosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements NotificacionesManager
 * 
 * @extends AbstractManager
 */
public class FormatosDocumentosManagerImpl extends AbstractManager implements FormatosDocumentosManager {


	/**
	 *  Obtiene un conjunto de Formatos de Documentos
	 *  Hace uso del Store Procedure PKG_DOCUMENTOS.P_OBTIENE_FORMATOS
	 * 
	 *  @param dsNomFormato
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
	public PagedList buscarFormatosDocumentos(String dsNomFormato, int start, int limit )throws ApplicationException{
 		HashMap map = new HashMap();
		map.put("dsNomFormato", dsNomFormato);
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "BUSCAR_FORMATOS_DOCUMENTOS", start, limit);
	}
	
	/**
	 *  Elimina una notificacion 
	 *  Hace uso del Store Procedure PKG_DOCUMENTOS.P_BORRA_FORMATO
	 * 
	 *  @param cdFormato
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
		public String borrarFormatosDocumentos(String cdFormato) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdFormato",cdFormato);
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_FORMATOS_DOCUMENTOS");
        return res.getMsgText();
	}
	
	/**
	 *  Obtiene una orden de compras en particular
	 *  Hace uso del Store Procedure PKG_DOCUMENTOS.P_OBTIENE_FORMATO
	 * 
	 *  @param cdFormato
	 *  
	 *  @return Objeto FormatoDocumentoVO
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public FormatoDocumentoVO getFormatosDocumentos(String cdFormato) throws ApplicationException{
			HashMap map = new HashMap();
			map.put("cdFormato",cdFormato);
            return (FormatoDocumentoVO)getBackBoneInvoke(map,"OBTIENERREG_FORMATOS_DOCUMENTOS");

	}
	
	/**
	 *  Actualiza una ayuda de coberturas modificada.
	 *  Hace uso del Store Procedure PKG_DOCUMENTOS.P_GUARDAR_FORMATO
	 * 
	 *  @param FormatoDocumentoVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String guardarFormatosDocumentos(FormatoDocumentoVO formatoDocumentoVO) throws ApplicationException{

			HashMap map = new HashMap();
			map.put("cdFormato",formatoDocumentoVO.getCdFormato());
			map.put("dsNomFormato",formatoDocumentoVO.getDsNomFormato());
			map.put("dsFormato",formatoDocumentoVO.getDsFormato());

	
            WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_FORMATOS_DOCUMENTOS");
            return res.getMsgText();
    }
    
   /**
	 *  Obtiene un conjunto de formatos de documentos la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure PKG_DOCUMENTOS.P_OBTIENE_FORMATOS
	 *  
	 *  @param dsNomFormato
	 *    
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */

    @SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsNomFormato) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();

		map.put("dsNomFormato", dsNomFormato);
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_FORMANTOS_DOCUMENTOS_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"FORMATO"});
		
		return model;
	}
    
    
}


	
	
	


