package mx.com.aon.catbo.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.ConfigurarEncuestaVO;
import mx.com.aon.catbo.model.OperacionCATVO;
import mx.com.aon.catbo.model.ScriptObservacionVO;
import mx.com.aon.catbo.service.OperacionCATManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Clase que sirve para dar servicios al action que lo invoca.
 * 
 * @implements NotificacionesManager
 * 
 * @extends AbstractManager
 */
public class OperacionCATManagerImpl extends AbstractManager implements OperacionCATManager {


	/**
	 *  Obtiene un conjunto de Notificaciones
	 *  Hace uso del Store Procedure PKG_CATBO.P_OBTIENE_TGUIONES
	 * 
	 *  @param dsUniEco
	 *  @param dsElemento
	 *  @param dsGuion
	 *  @param dsProceso
	 *  @param dsTipGuion
	 *  @param dsRamo
	 *  
	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */	
    @SuppressWarnings("unchecked")
    	public PagedList buscarGuiones(String dsUniEco, String dsElemento,
			String dsGuion, String dsProceso, String dsTipGuion, String dsRamo,
			int start, int limit) throws ApplicationException {
    	
    	HashMap map = new HashMap();
		map.put("dsUniEco", dsUniEco);
		map.put("dsElemento",dsElemento);
		map.put("dsGuion",dsGuion);
		map.put("dsProceso", dsProceso);
		map.put("dsTipGuion",dsTipGuion);
		map.put("dsRamo",dsRamo);

		
        return pagedBackBoneInvoke(map, "BUSCAR_GUIONES", start, limit);
	}
    
	
	/**
	 *  Elimina un guion 
	 *  Hace uso del Store Procedure PKG_CATBO.P_BORRA_TGUION
	 * 
	 *  @param cdUniEco
	 *  @param cdElemento
	 *  @param cdGuion
	 *  @param cdProceso
	 *  @param cdRamo
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
		public String borrarGuion(String cdUniEco, String cdElemento,
			String cdGuion, String cdProceso, String cdRamo)
			throws ApplicationException {
	    HashMap map = new HashMap();
		map.put("cdUniEco",cdUniEco);
		map.put("cdElemento",cdElemento);
		map.put("cdGuion",cdGuion);
		map.put("cdProceso",cdProceso);
		map.put("cdRamo",cdRamo);
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_GUION");
        return res.getMsgText();
	}

	/**
	 *  Elimina un Dialogo 
	 *  Hace uso del Store Procedure PKG_CATBO.P_BORRA_TDIALOGO
	 * 
	 *  @param cdUniEco
	 *  @param cdRamo
	 *  @param cdElemento
	 *  @param cdProceso
	 *  @param cdGuion
	 *  @param cdDialogo
	 *  
	 *  @return Objeto WrapperResultados
	 *  
	 *  @throws ApplicationException
	 */	
	@SuppressWarnings("unchecked")
	public String borrarDialogo(String cdGuion, String cdDialogo) throws ApplicationException{
	    HashMap map = new HashMap();
		
		map.put("cdGuion",cdGuion);
		map.put("cdDialogo",cdDialogo);
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_DIALOGO");
        return res.getMsgText();	
	}

    /**
	 *  Obtiene un GUION en base a un parametro de entrada.
	 *  Hace uso del Store Procedure PKG_CATBO.P_OBTIENE_TGUION
	 * 
	 *  @param cdUniEco
	 *  @param cdElemento
	 *  @param cdGuion
	 *  @param cdProceso
	 *  @param cdRamo
	 *  
	 *  @return Objeto OperacionCATVO
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public OperacionCATVO getGuion(String cdUniEco, String cdElemento,
			String cdGuion, String cdProceso, String cdRamo)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco",cdUniEco);
		map.put("cdElemento",cdElemento);
		map.put("cdGuion",cdGuion);
		map.put("cdProceso",cdProceso);
		map.put("cdRamo",cdRamo);
        return (OperacionCATVO)getBackBoneInvoke(map,"OBTENERREG_GUION");
	}
	

	
	
	/**
	 *  Inserta o Actualiza un GUION.
	 *  Hace uso del Store Procedure PKG_CATBO.P_GUARDA_TGUION
	 * 
	 *  @param OperacionCATVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")	
    public String guardarGuion(OperacionCATVO operacionCATVO)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco",operacionCATVO.getCdUniEco());
		map.put("cdRamo",operacionCATVO.getCdRamo());
		map.put("cdElemento",operacionCATVO.getCdElemento());
		map.put("cdProceso",operacionCATVO.getCdProceso());
		map.put("cdGuion",operacionCATVO.getCdGuion());
		map.put("dsGuion",operacionCATVO.getDsGuion());
		map.put("cdTipGuion",operacionCATVO.getCdTipGuion());
		map.put("indInicial",operacionCATVO.getIndInicial());
		map.put("status",operacionCATVO.getStatus());
		
        WrapperResultados res =  returnBackBoneInvoke(map,"INSERTAR_GUARDAR_GUION");
        return res.getMsgText();
	}

	/**
	 *  Obtiene un conjunto de Dialogos para un guion
	 * 
	 * @param cdUniEco
	 * @param cdRamo 
	 * @param cdElemento 
	 * @param cdProceso 
	 * @param cdGuion
	 * 
   	 *  @return Objeto PagedList
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public List buscarDialogosGuion(String cdGuion) throws ApplicationException {
		
		HashMap map = new HashMap();
		map.put("cdGuion",cdGuion);
		        
		return getAllBackBoneInvoke(map, "BUSCAR_DIALOGOS_GUION");
	}


	@SuppressWarnings("unchecked")
	public String guardarDialogoGuion(OperacionCATVO operacionCATVO)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco",operacionCATVO.getCdUniEco());
		map.put("cdRamo",operacionCATVO.getCdRamo());
		map.put("cdElemento",operacionCATVO.getCdElemento());
		map.put("cdProceso",operacionCATVO.getCdProceso());
		map.put("cdGuion",operacionCATVO.getCdGuion());
		map.put("cdDialogo",operacionCATVO.getCdDialogo());
		map.put("dsDialogo",operacionCATVO.getDsDialogo());
		map.put("cdSecuencia",operacionCATVO.getCdSecuencia());
		map.put("otTapVal",operacionCATVO.getOtTapVal());
		
		
        WrapperResultados res =  returnBackBoneInvoke(map,"INSERTAR_GUARDAR_DIALOGO_GUION");
        return res.getMsgText();
	}

/*	
	@SuppressWarnings("unchecked")
	public List buscarGuionesDisp() throws ApplicationException {
		return (List) getAllBackBoneInvoke(null,"OBTENER_GUIONES_DISP");
	}
*/

	@SuppressWarnings("unchecked")
	public List buscarGuionesDisp(String CdTipGui, String cdElemento) throws ApplicationException {
		
	   	HashMap map = new HashMap();
		map.put("CdTipGui", CdTipGui);
		map.put("cdElemento", cdElemento);
		
		return (List) getAllBackBoneInvoke(map,"OBTENER_GUIONES_DISP");
	}

	
   /**
	 *  Obtiene un conjunto de guiones para la exportacion a un formato predeterminado
	 *  Hace uso del Store Procedure PKG_CATBO.P_OBTIENE_TGUIONES
	 * 
	 *  @param dsUniEco
	 *  @param dsElemento
	 *  @param dsGuion
	 *  @param dsProceso
	 *  @param dsTipGuion
	 *  @param dsRamo
	 *  
	*/
	
    @SuppressWarnings("unchecked")
	public TableModelExport getModel(String dsUniEco, String dsElemento, String dsGuion, String dsProceso, String dsTipGuion, String dsRamo) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();

		map.put("dsUniEco", dsUniEco);
		map.put("dsElemento", dsElemento);
		map.put("dsGuion", dsGuion);
		map.put("dsProceso", dsProceso);
		map.put("dsTipGuion", dsTipGuion);
		map.put("dsRamo", dsRamo);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_GUIONES_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Aseguradora","Producto","Cliente","Proceso","Guión","Tipo de Guión"});
		
		return model;
	}
    
    
    @SuppressWarnings("unchecked")
	public TableModelExport getModelDialogo(String cdUniEco, String cdElemento, String cdGuion, String cdProceso, String cdTipGuion, String cdRamo) throws ApplicationException {
		
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();

		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo",cdRamo);
		map.put("cdElemento",cdElemento);
		map.put("cdProceso", cdProceso);
		map.put("cdGuion",cdGuion);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_DIALOGOS_GUION_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Diálogo","Lista de Valores","Valor de Tabla"});
		
		return model;
	}


    public PagedList buscarDialogosGuiones(String cdGuion, int start, int limit) throws ApplicationException {
        	
        	HashMap map = new HashMap();
    		map.put("cdGuion", cdGuion);
    		
    		
            return pagedBackBoneInvoke(map, "BUSCAR_DIALOGOS_GUION", start, limit);
    
    }
 
    /**
	 *  Obtiene un GUION en base a un parametro de entrada.
	 *  Hace uso del Store Procedure PKG_CATBO.P_OBTIENE_TGUION
	 * 
	 *  @param cdUniEco
	 *  @param cdElemento
	 *  @param cdGuion
	 *  @param cdProceso
	 *  @param cdRamo
	 *  
	 *  @return Objeto OperacionCATVO
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public OperacionCATVO getScriptAtencionInicial(String cdTipGui, String indInicial) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdTipGui",cdTipGui);
		map.put("indInicial",indInicial);

        return (OperacionCATVO)getBackBoneInvoke(map,"OBTENER_GUION_SCRIPT_INICIAL");
	}
	
	/**
	 *  Inserta una observacion de script de CAT.
	 *  Hace uso del Store Procedure PKG_NOTIFICACIONES_CATBO.P_GUARDA_TLOGLLAM
	 * 
	 *  @param ScriptObservacionVO
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public String guardarDialogoScriptObservacion(ScriptObservacionVO scriptObservacionVO) throws ApplicationException{
		
		HashMap map = new HashMap();
		map.put("cdUsuario",scriptObservacionVO.getCdUsuario());
		map.put("cdPerson",scriptObservacionVO.getCdPerson() );
		map.put("dsObservacion",scriptObservacionVO.getDsObservacion() );
		
        WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_DIALOGO_SCRIPT_OBSERVACION");
        
        return res.getMsgText();
		
	}
	
    /**
	 *  Obtiene un GUION en base a un parametro de entrada.
	 *  Hace uso del Store Procedure PKG_ENCUESTAS.P_OBTIENE_ENCUESTA_PENDIENTE
	 * 
	 *  @param cdPerson
	 *  
	 *  @return Objeto ConfigurarEncuestaVO
	 *  
	 *  @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public ConfigurarEncuestaVO getObtenerEncuestasPendientes(String pv_cdperson_i) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdperson_i",pv_cdperson_i);


        return (ConfigurarEncuestaVO)getBackBoneInvoke(map,"OBTENER_ENCUESTAS_PENDIENTES");
	}
	
}


	
	
	


