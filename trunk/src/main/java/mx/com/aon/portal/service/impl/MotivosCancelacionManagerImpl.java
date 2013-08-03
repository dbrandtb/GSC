/**
 * 
 */
package mx.com.aon.portal.service.impl;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;

import mx.com.aon.portal.model.BackBoneResultVO;
import mx.com.aon.portal.model.MotivoCancelacionVO;
import mx.com.aon.portal.model.RequisitoCancelacionVO;
import mx.com.aon.portal.service.MotivosCancelacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Implementacion de la interface de servicios para motivos de cancelacion.
 *
 */
public class MotivosCancelacionManagerImpl extends AbstractManager implements MotivosCancelacionManager {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(EstructuraManagerImpl.class);
	
	
	/**
	 *  Realiza la baja de motivos de cancelacion.
	 *  Usa el store procedure PKG_CANCELA.P_BORRA_MOTIVO.
	 * 
	 *  @param cdRazon
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	@SuppressWarnings("unchecked")
	public String borrarMotivosCancelacion(String cdRazon) throws ApplicationException{
	
		HashMap map = new HashMap();
		map.put("cdRazon",cdRazon);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_MOTIVOS_CANCELACION");
        return res.getMsgText();
	}
	

	/**
	 *  Realiza la baja de requisito de cancelacion.
	 *  Usa el store procedure PKG_CANCELA.P_BORRA_REQUISITO.
	 * 
	 *  @param cdRazon
	 *  @param cdAdvert
	 *  
	 *  @return Mensaje asociado en respuesta a la ejecucion del servicio.
	 */		
	@SuppressWarnings("unchecked")
	public String borrarRequisitoCancelacion(String cdRazon, String cdAdvert) throws ApplicationException{
	
		// Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("cdRazon",cdRazon);
		map.put("cdAdvert",cdAdvert);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_REQUISITOS_CANCELACION");
        return res.getMsgText();
	}

	
	/**
	 *  Salva la informacion de motivos de cancelacion.
	 *  Usa el store procedure PKG_CANCELA.P_GUARDA_MOTIVO para motivos de cancelacion y
	 *   PKG_CANCELA.P_GUARDA_REQUISITO para requisitos de cancelacion.
	 * 
	 *  @param motivoCancelacionVO
	 *  
	 *  @return BackBoneResultVO
	 */		
	@SuppressWarnings("unchecked")
	public BackBoneResultVO guardarMotivosCancelacion(MotivoCancelacionVO motivoCancelacionVO ) throws ApplicationException{
		WrapperResultados res1 = new WrapperResultados();
		BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
			HashMap map = new HashMap();
			map.put("cdRazon",motivoCancelacionVO.getCdRazon());
			map.put("dsRazon",motivoCancelacionVO.getDsRazon());
			map.put("swReInst",motivoCancelacionVO.getSwReInst());
			map.put("swVerFec",motivoCancelacionVO.getSwVerFec());
			map.put("swVerPag",motivoCancelacionVO.getSwVerPag());
            WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_MOTIVOS_CANCELACION");
            List<RequisitoCancelacionVO> listaRequisitoCancelacionVO = motivoCancelacionVO.getRequisitoCancelacionVOs();
            HashMap map1 = new HashMap();
            if (listaRequisitoCancelacionVO != null) {
                for (int i=0; i<listaRequisitoCancelacionVO.size(); i++)
                {
                    RequisitoCancelacionVO requisitoCancelacionVO=listaRequisitoCancelacionVO.get(i);
                    map1.put("cdRazon",motivoCancelacionVO.getCdRazon());
                    map1.put("cdAdvert",requisitoCancelacionVO.getCdAdvert());
                    map1.put("dsAdvert",requisitoCancelacionVO.getDsAdvert());
                    res1 =  returnBackBoneInvoke(map1,"GUARDAR_REQUISITOS_CANCELACION");
                }
                backBoneResultVO.setMsgText(res1.getMsgText());
                backBoneResultVO.setOutParam(motivoCancelacionVO.getCdRazon());
            }else {
                backBoneResultVO.setMsgText(res.getMsgText());
                backBoneResultVO.setOutParam(motivoCancelacionVO.getCdRazon());
            }
		return backBoneResultVO;
	}


	/**
	 *  Agrega nuevo mativos de cancelacion.
	 *  Usa el store procedure PKG_CANCELA.P_GUARDA_MOTIVO para motivos 
	 *  PKG_CANCELA.P_GUARDA_REQUISITO para requisitos.
	 * 
	 *  @param motivoCancelacionVO
	 *  
	 *  @return BackBoneResultVO
	 */		
    @SuppressWarnings("unchecked")
	public BackBoneResultVO insertarMotivosCancelacion(MotivoCancelacionVO motivoCancelacionVO) throws ApplicationException {
		WrapperResultados res1 = new WrapperResultados();
		BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
		HashMap map = new HashMap();
		map.put("cdRazon",motivoCancelacionVO.getCdRazon());
		map.put("dsRazon",motivoCancelacionVO.getDsRazon());
		map.put("swReInst",motivoCancelacionVO.getSwReInst());
		map.put("swVerFec",motivoCancelacionVO.getSwVerFec());
		map.put("swVerPag",motivoCancelacionVO.getSwVerPag());
        WrapperResultados res =  returnBackBoneInvoke(map,"INSERTAR_MOTIVOS_CANCELACION");
        List<RequisitoCancelacionVO> listaRequisitoCancelacionVO = motivoCancelacionVO.getRequisitoCancelacionVOs();
        HashMap map1 = new HashMap();
        if (listaRequisitoCancelacionVO != null) {
            for (int i=0; i<listaRequisitoCancelacionVO.size(); i++)
            {
                RequisitoCancelacionVO requisitoCancelacionVO=listaRequisitoCancelacionVO.get(i);
                map1.put("cdRazon",res.getResultado());
                map1.put("cdAdvert",requisitoCancelacionVO.getCdAdvert());
                map1.put("dsAdvert",requisitoCancelacionVO.getDsAdvert());
                res1 =  returnBackBoneInvoke(map1,"GUARDAR_REQUISITOS_CANCELACION");
            }
            backBoneResultVO.setMsgText(res1.getMsgText());
            backBoneResultVO.setOutParam(res.getResultado());
        }else {
            backBoneResultVO.setMsgText(res.getMsgText());
            backBoneResultVO.setOutParam(res.getResultado());
        }
        return backBoneResultVO;
    }

 
	/**
	 *  Obtiene un conjunto de motivos de cancelacion.
	 *  Usa el store procedure PKG_CANCELA.P_MOTIVOS.
	 *  
	 *  @param cdRazon
	 *  @param dsRazon
	 *  @param start
	 *  @param limit
	 *  
	 *  @return Objeto PagedList 
	 */	
    @SuppressWarnings("unchecked")
	public PagedList buscarMotivosCancelacion(String cdRazon, String dsRazon, int start, int limit ) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdRazon",cdRazon);
		map.put("dsRazon", dsRazon);
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "BUSCAR_MOTIVOS_CANCELACION", start, limit);
	}


	/**
	 *  Obtiene requisitos de cancelacion.
	 *  Usa el store procedure PKG_CANCELA.P_OBTIENE_REQUISITO_CANCELA.
	 * 
	 *  @param cdRazon
	 *  @param start
	 *  @param limit
	 *  
	 *  @return MotivoCancelacionVO
	 */		
	@SuppressWarnings("unchecked")
	public PagedList getRequisitoCancelacion(String cdRazon, int start, int limit ) throws ApplicationException{
        // Se crea un mapa para pasar los parametros de ejecucion al endpoint
		HashMap map = new HashMap();
		map.put("cdRazon",cdRazon);
		map.put("start",start);
		map.put("limit",limit);
        return pagedBackBoneInvoke(map, "OBTIENE_REQUISITOS_CANCELACION", start, limit);
	}
	

	/**
	 *  Obtiene motivos de cancelacion.
	 *  Usa el store procedure PKG_CANCELA.P_MOTIVOS.
	 * 
	 *  @param cdRazon
	 *  
	 *  @return MotivoCancelacionVO
	 */		
	@SuppressWarnings("unchecked")
	public MotivoCancelacionVO getMotivosCancelacion(String cdRazon) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdRazon",cdRazon);					
        return (MotivoCancelacionVO)getBackBoneInvoke(map,"OBTIENE_MOTIVOS_CANCELACION");		
	}
	

	/**
	  * Obtiene un conjunto de seciones y exporta el resultado en Formato PDF, Excel, CSV, etc.
	  * Usa el store procedure PKG_CANCELA.P_MOTIVOS.
	  *
	  * @param cdRazon
	  * @param dsRazon
	  * 
	  * @return TableModelExport
	  */
    @SuppressWarnings("unchecked")
	public TableModelExport getModel(String cdRazon, String dsRazon) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("cdRazon",cdRazon);
		map.put("dsRazon", dsRazon);
				
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_MOTIVOS_CANCELACION_EXPORT");

		model.setInformation(lista);
		model.setColumnName(new String[]{"Clave","Razon","Rehabilitacion","Ver Pagos"});
		return model;
    }
	   
}