/**
 * 
 */
package mx.com.aon.portal.service.impl;

import com.wittyconsulting.backbone.endpoint.Endpoint;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.AyudaCoberturasVO;
import mx.com.aon.portal.service.AyudaCoberturasManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements AyudaCoberturasManager
 * 
 * @extends AbstractManager
 */
public class AyudaCoberturasManagerImpl extends AbstractManager implements AyudaCoberturasManager{
	
	/**
	 *  Obtiene una configuracion de ayuda de cobertura especifica en base a un parametro de entrada.
	 *  Hace uso del Store Procedure PKG_AYUDA.P_OBTIENE_AYUDA_REG
	 * 
	 *  @param cdGarantiaxCia
	 *  
	 *  @return Objeto EstructuraVO
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public AyudaCoberturasVO getAyudaCoberturas(String cdGarantiaxCia) throws ApplicationException
	{
		HashMap map = new HashMap();
		map.put("cdGarantiaxCia",cdGarantiaxCia);
		
        return (AyudaCoberturasVO)getBackBoneInvoke(map,"OBTENERREG_AYUDA_COBERTURAS");
	}
	
	/**
	 *  Obtiene un conjunto de ayudas de coberturas.
	 *  Hace uso del Store Procedure PKG_AYUDA.P_OBTIENE_COBERTURAS.
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public PagedList buscarAyudaCoberturas(String dsUnieco, String dsSubram, String dsGarant, String dsTipram, String dsRamo, String pv_lang_code_i,int start, int limit )throws ApplicationException
	{
			HashMap map = new HashMap();
			map.put("dsUnieco",dsUnieco);
			map.put("dsSubram",dsSubram);
			map.put("dsGarant",dsGarant);
			map.put("dsTipram",dsTipram);
			map.put("dsRamo",dsRamo);
			map.put("pv_lang_code_i",pv_lang_code_i);			
			map.put("start",start);
			map.put("limit",limit);
			
            return pagedBackBoneInvoke(map, "BUSCAR_AYUDA_COBERTURAS", start, limit);
	}
	
	/**
	 *  Inserta una nueva ayuda de coberturas.
	 *  Hace uso del Store Procedure PKG_AYUDA.P_GUARDA_COBERTURA
	 * 
	 *  @param AyudaCoberturasVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")	
    public String insertarAyudaCoberturas(AyudaCoberturasVO ayudaCoberturasVO) throws ApplicationException{
      
			HashMap map = new HashMap();
			map.put("cdGarantiaxCia",ayudaCoberturasVO.getCdGarantiaxCia());
			map.put("cdUnieco",ayudaCoberturasVO.getCdUnieco());
			map.put("cdTipram",ayudaCoberturasVO.getCdTipram());
			map.put("cdSubram",ayudaCoberturasVO.getCdSubram());
			map.put("cdRamo",ayudaCoberturasVO.getCdRamo());
			map.put("cdGarant",ayudaCoberturasVO.getCdGarant());
			map.put("langCode",ayudaCoberturasVO.getLangCode());
			map.put("dsAyuda",ayudaCoberturasVO.getDsAyuda());
					
            WrapperResultados res =  returnBackBoneInvoke(map,"INSERTAR_AYUDA_COBERTURAS");
            return res.getMsgText();
	}
    
	/**
	 *  Actualiza una ayuda de coberturas modificada.
	 *  Hace uso del Store Procedure PKG_AYUDA.P_GUARDA_COBERTURA
	 * 
	 *  @param AyudaCoberturasVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String guardarAyudaCoberturas(AyudaCoberturasVO ayudaCoberturasVO) throws ApplicationException{

			HashMap map = new HashMap();
			map.put("cdGarantiaxCia",ayudaCoberturasVO.getCdGarantiaxCia());
			map.put("cdUnieco",ayudaCoberturasVO.getCdUnieco());
			map.put("cdTipram",ayudaCoberturasVO.getCdTipram());
			map.put("cdSubram",ayudaCoberturasVO.getCdSubram());
			map.put("cdRamo",ayudaCoberturasVO.getCdRamo());
			map.put("cdGarant",ayudaCoberturasVO.getCdGarant());
			map.put("langCode",ayudaCoberturasVO.getLangCode());
			map.put("dsAyuda",ayudaCoberturasVO.getDsAyuda());
	
            WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_AYUDA_COBERTURAS");
            return res.getMsgText();
    }
    
    /**
	 *  Elimina una ayuda de cobertura seleccionada.
	 *  Hace uso del Store Procedure PKG_AYUDA.P_ELIMINA_COBERTURA.
	 * 
	 *  @param cdGarantiaxCia
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
    @SuppressWarnings("unchecked")
	public String borrarAyudaCoberturas(String cdGarantiaxCia) throws ApplicationException{

			HashMap map = new HashMap();
			map.put("cdGarantiaxCia",cdGarantiaxCia);
			
            WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_AYUDA_COBERTURAS");
            return res.getMsgText();
    }
    
    /**
	 *  Copia una ayuda de cobertura seleccionada.
	 *  Hace uso del Store Procedure PKG_AYUDA.p_copia_cobertura.
	 * 
	 *  @param objeto AyudaCoberturasVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String copiarAyudaCoberturas(AyudaCoberturasVO ayudaCoberturasVO) throws ApplicationException
	{
			HashMap map = new HashMap();
			map.put("cdGarantiaxCia",ayudaCoberturasVO.getCdGarantiaxCia());
			map.put("cdUnieco",ayudaCoberturasVO.getCdUnieco());
			map.put("cdSubram",ayudaCoberturasVO.getCdSubram());
			map.put("cdGarant",ayudaCoberturasVO.getCdGarant());
			map.put("cdTipram",ayudaCoberturasVO.getCdTipram());
			map.put("cdRamo",ayudaCoberturasVO.getCdRamo());
			map.put("langCode",ayudaCoberturasVO.getLangCode());
	
            WrapperResultados res =  returnBackBoneInvoke(map,"COPIAR_AYUDA_COBERTURAS");
            return res.getMsgText();
	}

	public void setEndpoints(Map<String, Endpoint> endpoints) {
		this.endpoints = endpoints;
	}
	
	/**
	 *  Obtiene un conjunto de ayudas de coberturas para la exportacion a un formato predeterminado.
	 * 
	 *  Parametros de busqueda:
	 *  @param dsUniEco
	 *  @param dsSubRam
	 *  @param dsGarant
	 *  @param dsTipram
	 *  @param dsRamo
	 *  
	 *  @return Objeto modelo para exportar.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsUnieco, String dsSubram,String dsGarant, String dsTipram, String dsRamo, String pv_lang_code_i)throws ApplicationException {

		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("dsUnieco",dsUnieco);
		map.put("dsSubram",dsSubram);
		map.put("dsGarant",dsGarant);
		map.put("dsTipram",dsTipram);
		map.put("dsRamo",dsRamo);
		map.put("pv_lang_code_i",pv_lang_code_i);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_AYUDA_COBERTURAS_EXPORT");
		model.setInformation(lista);
		model.setColumnName(new String[]{"Aseguradora","Ramo","Sub-Ramo","Producto","Cobertura", "Idioma"});
		
		return model;
	}
}