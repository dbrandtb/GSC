/**
 * 
 */
package mx.com.aon.portal.service.impl;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.MecanismoDeTooltipVO;
import mx.com.aon.portal.service.MecanismoDeTooltipManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements MecanismoDeTooltipManager
 * 
 * @extends AbstractManager
 */
public class MecanismoDeTooltipManagerImpl extends AbstractManager implements MecanismoDeTooltipManager{

	
	/**
	 *  Obtiene un conjunto de Mecanismo de Tooltip.
	 *  Hace uso del Store Procedure PKG_TRADUC.P_OBTIENE_PROPIEDADES.
	 *   
	 *  @param nbObjeto
	 *  @param lang_Code
	 *  @param dsTitulo
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList buscarMecanismoDeTooltip(String nbObjeto, String lang_Code, String dsTitulo, int start, int limit )throws ApplicationException
	{
			HashMap map = new HashMap();
			map.put("nbObjeto",nbObjeto);
			map.put("lang_Code",lang_Code);
			map.put("dsTitulo", dsTitulo);
			map.put("start",start);
			map.put("limit",limit);
			
            return pagedBackBoneInvoke(map, "BUSCAR_MECANISMO_TOOLTIP", start, limit);
	}


	/**
	 *  Agrega un nuevo Mecanismo de Tooltip.
	 *  Hace uso del Store Procedure 
	 * 
	 *  @param mecanismoDeTooltipVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")	
    public String agregarGuardarMecanismoDeTooltip(MecanismoDeTooltipVO mecanismoDeTooltipVO) throws ApplicationException{
      
			HashMap map = new HashMap();
			map.put("idTitulo",mecanismoDeTooltipVO.getIdTitulo() );
			map.put("idObjeto",mecanismoDeTooltipVO.getIdObjeto());
			map.put("nbObjeto",mecanismoDeTooltipVO.getNbObjeto());
			map.put("fgTipoObjeto",mecanismoDeTooltipVO.getFgTipoObjeto());
			map.put("nbEtiqueta",mecanismoDeTooltipVO.getNbEtiqueta());
			map.put("dsTooltip",mecanismoDeTooltipVO.getDsTooltip());
			map.put("fgAyuda",mecanismoDeTooltipVO.getFgAyuda());
			map.put("dsAyuda",mecanismoDeTooltipVO.getDsAyuda());
			map.put("lang_Code",mecanismoDeTooltipVO.getLang_Code());
			
            WrapperResultados res =  returnBackBoneInvoke(map,"AGREGAR_GUARDAR_MECANISMO_TOOLTIP");
            return res.getMsgText();
	}
  

	/**
	 *  Da de baja informacion de un Mecanismo de Tooltip.
	 *  Hace uso del Store Procedure .
	 * 
	 *  @param idObjeto
	 *  @param lang_Code 
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String borrarMecanismoDeTooltip(String idObjeto, String lang_Code) throws ApplicationException{

			HashMap map = new HashMap();
			map.put("idObjeto",idObjeto);
			map.put("lang_Code",lang_Code);
			
            WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_MECANISMO_TOOLTIP");
            return res.getMsgText();
    }
	
    
	/**
	 *  Obtiene un Mecanismo de Tooltip especifica en base a un parametro de entrada.
	 *  Hace uso del Store Procedure .
	 * 
	 *  @param idObjedo
	 *  @param lang_Code
	 *  
	 *  @return MecanismoDeTooltipVO
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public MecanismoDeTooltipVO getMecanismoDeTooltipVO(String idObjedo, String lang_Code) throws ApplicationException
	{
		HashMap map = new HashMap();
		map.put("idObjeto",idObjedo);
		map.put("lang_Code", lang_Code);
		
        return (MecanismoDeTooltipVO)getBackBoneInvoke(map,"OBTIENE_MECANISMO_TOOLTIP");
	}
	
	
	/**
	 *  Copia un Mecanismo de Tooltip.
	 *  Hace uso del Store Procedure .
	 * 
	 *  @param mecanismoDeTooltipVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String copiarMecanismoDeTooltipVO(MecanismoDeTooltipVO mecanismoDeTooltipVO) throws ApplicationException
	{
		HashMap map = new HashMap();
		map.put("idTitulo",mecanismoDeTooltipVO.getIdTitulo() );
		map.put("idObjeto",mecanismoDeTooltipVO.getIdObjeto());
		map.put("nbObjeto",mecanismoDeTooltipVO.getNbObjeto());
		map.put("fgTipoObjeto",mecanismoDeTooltipVO.getFgTipoObjeto());
		map.put("nbEtiqueta",mecanismoDeTooltipVO.getNbEtiqueta());
		map.put("dsTooltip",mecanismoDeTooltipVO.getDsTooltip());
		map.put("fgAyuda",mecanismoDeTooltipVO.getFgAyuda());
		map.put("dsAyuda",mecanismoDeTooltipVO.getDsAyuda());
		map.put("lang_Code",mecanismoDeTooltipVO.getLang_Code());
		map.put("nbCajaIzquieda",mecanismoDeTooltipVO.getNbCajaIzquieda());
		map.put("nbCajaDerecha",mecanismoDeTooltipVO.getNbCajaDerecha());
	
            WrapperResultados res =  returnBackBoneInvoke(map,"COPIAR_MECANISMO_TOOLTIP");
            return res.getMsgText();
	}
	
	
	/**
	 *  Obtiene un conjunto de Mecanismo de Tooltip para la exportacion a un formato predeterminado.
	 * 
	 *  @param nbObjeto
	 *  @param lang_Code
	 *  @param dsTitulo
	 *  
	 *  @return TableModelExport.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String nbObjeto, String lang_Code, String dsTitulo)throws ApplicationException {

		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("nbObjeto",nbObjeto);
		map.put("lang_Code",lang_Code);
		map.put("dsTitulo", dsTitulo);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_MECANISMO_TOOLTIP_EXPORT");
		model.setInformation(lista);
		model.setColumnName(new String[]{"Etiqueta","Objeto","Pantalla"});
		//nbObjeto
		return model;
	}

	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}


    public List getToolTipsForPage(String cdTitulo, String langCode) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("lang_Code",langCode);
        map.put("cdpantalla",cdTitulo );

        return getAllBackBoneInvoke(map,"BUSCAR_MECANISMO_TOOLTIP_PAGE");

    }


	public List getGB_Messages(String cdTitle, String langCode) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdTitle", cdTitle);
		map.put("langCode", langCode);

		return getAllBackBoneInvoke(map, "BUSCAR_MENSAJES_USUARIO");
	}

}