package mx.com.aon.catbo.service.impl;

import mx.com.aon.catbo.service.ConfigurarEncuestaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.ConvertUtil;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.catbo.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ConfigurarEncuestaManagerImpl extends AbstractManager implements ConfigurarEncuestaManager {

	@SuppressWarnings("unchecked")
	public PagedList obtieneEncuesta(String pv_dsunieco_i, String pv_dsramo_i, String pv_dselemento_i ,String pv_dsproceso_i , String pv_dscampan_i, String pv_dsmodulo_i, String pv_dsencuesta_i,  int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_dsunieco_i", pv_dsunieco_i);
        map.put("pv_dsramo_i", pv_dsramo_i);
        map.put("pv_dselemento_i", pv_dselemento_i);
        map.put("pv_dsproceso_i", pv_dsproceso_i);
        map.put("pv_dscampan_i", pv_dscampan_i);
        map.put("pv_dsmodulo_i", pv_dsmodulo_i);
        map.put("pv_dsencuesta_i", pv_dsencuesta_i);
        
        String endpointName = "OBTIENE_ENCUESTA";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }
	
	@SuppressWarnings("unchecked")
	public PagedList obtenerEncuestas(String pv_dsencuesta_i, int start, int limit) throws ApplicationException{
		HashMap map = new HashMap();
        map.put("pv_dsencuesta_i", pv_dsencuesta_i);
        
        String endpointName = "OBTENER_ENCUESTAS";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	//Borrar esto despues, se codifico en ConfigurarEncuestaManager2 por el tema de las fechas
	/*@SuppressWarnings("unchecked")
	public String guardarConfigurarEncuesta(ConfigurarEncuestaVO configurarEncuestaVO)
	throws ApplicationException {
    	
		HashMap map = new HashMap();
		
	    map.put("pv_cdunieco_i",configurarEncuestaVO.getCdUnieco());
		map.put("pv_cdramo_i",configurarEncuestaVO.getCdRamo());
		map.put("pv_cdelemento_i",configurarEncuestaVO.getCdElemento());
		map.put("pv_cdproceso_i",configurarEncuestaVO.getCdProceso());
		map.put("pv_cdcampan_i",configurarEncuestaVO.getCdCampan());
		map.put("pv_cdmodulo_i",configurarEncuestaVO.getCdModulo());
		map.put("pv_cdencuesta_i",configurarEncuestaVO.getCdEncuesta());		
		map.put("pv_fedesde_i",ConvertUtil.convertToDate(configurarEncuestaVO.getFedesde_i()));
		map.put("pv_fehasta_i",ConvertUtil.convertToDate(configurarEncuestaVO.getFehasta_i()));
		
		WrapperResultados res =  returnBackBoneInvoke(map,"GUARDACONFIGURACION_ENCUESTA");
		return res.getMsgText();
	           
}	*/
	
	@SuppressWarnings("unchecked")
	public PagedList obtenerValorEncuesta(String pv_dsunieco_i, String pv_dsramo_i,String pv_dsencuesta_i,String pv_dscampana_i,String pv_dselemento_i,String pv_dsproceso_i,String pv_nmpoliex_i,int start, int limit) throws ApplicationException{
        
		HashMap map = new HashMap();
		
	    map.put("pv_dsunieco_i",pv_dsunieco_i);
		map.put("pv_dsramo_i",pv_dsramo_i);
		map.put("pv_dsencuesta_i",pv_dsencuesta_i);
		map.put("pv_dscampana_i",pv_dscampana_i);
		map.put("pv_dselemento_i",pv_dselemento_i);
		map.put("pv_dsproceso_i",pv_dsproceso_i);
		map.put("pv_nmpoliex_i",pv_nmpoliex_i);
		
		
		String endpointName = "OBTENER_VALOR_ENCUESTA";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	@SuppressWarnings("unchecked")
	public ConfigurarEncuestaVO getObtenerEncuestaRegistro(String pv_nmconfig_i, String pv_cdproceso_i, String pv_cdcampan_i, String pv_cdmodulo_i, String pv_cdencuesta_i) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_nmconfig_i", pv_nmconfig_i);
		map.put("pv_cdproceso_i", pv_cdproceso_i);
		map.put("pv_cdcampan_i", pv_cdcampan_i);
		map.put("pv_cdmodulo_i", pv_cdmodulo_i);
		map.put("pv_cdencuesta_i", pv_cdencuesta_i);		
		
        String endpointName = "OBTIENE_ENCUESTA_REGISTRO";
        return (ConfigurarEncuestaVO)getBackBoneInvoke(map, endpointName );

    }
	
	@SuppressWarnings("unchecked")
	public String borrarEncuestaRegistro(String pv_nmconfig_i, String pv_cdproceso_i, String pv_cdcampan_i, String pv_cdmodulo_i, String pv_cdencuesta_i) throws ApplicationException {
	    HashMap map = new HashMap();
		map.put("pv_nmconfig_i", pv_nmconfig_i);
		map.put("pv_cdproceso_i", pv_cdproceso_i);
		map.put("pv_cdcampan_i", pv_cdcampan_i);
		map.put("pv_cdmodulo_i", pv_cdmodulo_i);
		map.put("pv_cdencuesta_i", pv_cdencuesta_i);
		
	    WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_ENCUESTA");
	    return res.getMsgText();
				
	}
	
	@SuppressWarnings("unchecked")
	public CatboTimecVO getCatboTimec(String pv_cdcampana_i, String pv_cdunieco_i, String pv_cdramo_i) throws ApplicationException{
			HashMap map = new HashMap();
			map.put("pv_cdcampana_i",pv_cdcampana_i);
			map.put("pv_cdunieco_i",pv_cdunieco_i);
			map.put("pv_cdramo_i",pv_cdramo_i);
			
            return (CatboTimecVO)getBackBoneInvoke(map,"OBTIENER_CATBOTIMEC");

	}
	
	 @SuppressWarnings("unchecked")
     	
	
	public String guardaTiempoEncuesta(ConfigurarEncuestaVO configurarEncuestasVO)throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdcampana_i",configurarEncuestasVO.getCdCampan());
	    map.put("pv_cdunieco_i",configurarEncuestasVO.getCdUnieco());
		map.put("pv_cdramo_i" , configurarEncuestasVO.getCdRamo());
		map.put("pv_cdunidtmpo_i",configurarEncuestasVO.getCdUnidtmpo());
		map.put("pv_nmunidad_i",configurarEncuestasVO.getNmUnidad());
				
       WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_TIEMPO_ENCUESTA");
       return res.getMsgText();
	}
	 
	 @SuppressWarnings("unchecked")
		public String borraTiempoEncuesta(String pv_cdencuesta_i )throws ApplicationException{
			 HashMap map = new HashMap();
				map.put("pv_cdencuesta_i", pv_cdencuesta_i);
							
			    WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_TIEMPO_ENCUESTA");
			    return res.getMsgText(); 
		 }

		public TableModelExport getModel(String pv_dsencuesta_i)throws ApplicationException {
				TableModelExport model = new TableModelExport();
	
				List lista = null;
				HashMap map = new HashMap();

				try {
				map.put("pv_dsencuesta_i", pv_dsencuesta_i);
				lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_ENCUESTAS_EXPORT");

				model.setInformation(lista);
				model.setColumnName(new String[]{"Encuesta"});
				} catch (Exception e) {
					model.setColumnName(new String[]{"Encuesta"});
					model.setInformation(null);
				}
				return model;
		}
		
		@SuppressWarnings("unchecked")
		public String guardaTiempoEncuestaTencuest(ConfigurarEncuestaVO configurarEncuestasVO)throws ApplicationException{
			 HashMap map = new HashMap();
				map.put("pv_cdencuesta_i",configurarEncuestasVO.getCdEncuesta());
			    map.put("pv_dsencuesta_i",configurarEncuestasVO.getDsEncuesta());
				map.put("pv_swestado_i" , configurarEncuestasVO.getSwEstado());
				
						
		       WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_TENCUEST");
		       return res.getMsgText(); 
		 }
		
		public String guardaTpregunta(ConfigurarEncuestaVO configurarEncuestasVO)throws ApplicationException{
			HashMap map = new HashMap();
			map.put("pv_cdencuesta_i",configurarEncuestasVO.getCdEncuesta());
		    map.put("pv_cdpregunta_i",configurarEncuestasVO.getCdPregunta());
			map.put("pv_dspregunta_i" , configurarEncuestasVO.getDsPregunta());
			map.put("pv_swobliga_i",configurarEncuestasVO.getSwobliga());
			map.put("pv_cdsecuencia_i",configurarEncuestasVO.getCdsecuencia());
			map.put("pv_cddefault_i",configurarEncuestasVO.getCddefault());

			
	       WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_TPREGUNTA");
	       return res.getMsgText();
		}
		
		
		@SuppressWarnings("unchecked")
		public String borraAsignaEncuesta(String pv_nmconfig_i, String pv_cdunieco_i, String pv_cdramo_i, String pv_estado_i, String pv_nmpoliza_i, String pv_cdperson_i, String pv_cdusuario_i)throws ApplicationException{
			HashMap map = new HashMap();
			map.put("pv_nmconfig_i", pv_nmconfig_i);
			map.put("pv_cdunieco_i", pv_cdunieco_i);
			map.put("pv_cdramo_i", pv_cdramo_i);
			map.put("pv_estado_i", pv_estado_i);
			map.put("pv_nmpoliza_i", pv_nmpoliza_i);
			map.put("pv_cdperson_i", pv_cdperson_i);
			map.put("pv_cdusuario_i", pv_cdusuario_i);
			
		    WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_ASIGNAR_ENCUESTA");
		    return res.getMsgText(); 
		}
		
		@SuppressWarnings("unchecked")
		public String borrarTvalenct(String pv_nmconfig_i, String pv_cdunieco_i, String pv_cdramo_i, String pv_nmpoliza_i
				                     , String pv_estado_i, String pv_cdencuesta_i)throws ApplicationException{
			HashMap map = new HashMap();
			
			map.put("pv_nmconfig_i", pv_nmconfig_i);
			map.put("pv_cdunieco_i", pv_cdunieco_i);
			map.put("pv_cdramo_i", pv_cdramo_i);
			map.put("pv_estado_i", pv_estado_i);
			map.put("pv_nmpoliza_i", pv_nmpoliza_i);
			map.put("pv_cdencuesta_i", pv_cdencuesta_i);
			
		    WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_TVALENCT");
		    return res.getMsgText(); 
		}
	
		@SuppressWarnings("unchecked")
		public String borrarTpregunt(String cdPregunta)throws ApplicationException{
			HashMap map = new HashMap();

			map.put("cdPregunta", cdPregunta);

			WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_TPREGUNT");
			return res.getMsgText(); 
		}
		
		@SuppressWarnings("unchecked")
		public String borraCATBOTiempoEncuesta(String pv_cdcampana_i, String pv_cdunieco_i, String pv_cdramo_i)throws ApplicationException{
			HashMap map = new HashMap();

			map.put("pv_cdcampana_i", pv_cdcampana_i);
			map.put("pv_cdunieco_i", pv_cdunieco_i);
			map.put("pv_cdramo_i", pv_cdramo_i);
			
			WrapperResultados res =  returnBackBoneInvoke(map,"BORRA_CATBO_TIEMPO_ENCUESTA");
			return res.getMsgText();
		}

		
		@SuppressWarnings("unchecked")
		public TableModelExport getModelConfigEncuesta(String pv_dsunieco_i,
				String pv_dsramo_i, String pv_dselemento_i,
				String pv_dsproceso_, String pv_dscampan_i,
				String pv_dsmodulo_i, String pv_dsencuesta_i)
				throws ApplicationException {
			
			TableModelExport model = new TableModelExport();
			
			List lista = null;
			HashMap map = new HashMap();

			map.put("pv_dsunieco_i", pv_dsunieco_i);
			map.put("pv_dsramo_i", pv_dsramo_i);
			map.put("pv_dselemento_i", pv_dselemento_i);
			map.put("pv_dsproceso_", pv_dsproceso_);
			map.put("pv_dscampan_i", pv_dscampan_i);
			map.put("pv_dsmodulo_i", pv_dsmodulo_i);
			map.put("pv_dsencuesta_i", pv_dsencuesta_i);
			
			
			lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_CONFIG_ENCUESTAS_EXPORT");

			model.setInformation(lista);
			model.setColumnName(new String[]{"Aseguradora","Producto","Cuenta/Cliente","Operacion","Compañia","Modulo","Encuesta"});
			
			return model;
		}

		
		
		@SuppressWarnings("unchecked")
		public TableModelExport getModelAsignacionEncuesta(String pv_dsunieco_i,String pv_dsramo_i,String pv_estado_i,String  pv_nmpoliza_i, String pv_dsperson_i, String pv_dsusuario_i)
				throws ApplicationException {
			
			TableModelExport model = new TableModelExport();
			
			List lista = null;
			HashMap map = new HashMap();

			map.put("pv_dsunieco_i", pv_dsunieco_i);
			map.put("pv_dsramo_i", pv_dsramo_i);
			map.put("pv_estado_i", pv_estado_i);
			map.put("pv_nmpoliza_i", pv_nmpoliza_i);
			map.put("pv_dsperson_i", pv_dsperson_i);
			map.put("pv_dsusuario_i", pv_dsusuario_i);
			
			lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "ASIGNA_ENCUESTA_OBTIENE_ENCUESTA_EXPORT");

			model.setInformation(lista);
			model.setColumnName(new String[]{"Aseguradora","Producto","Estado","Póliza","Asegurado","Usuario"});
			
			return model;
		}

		
		
		public String guardarTvalenct(ConfigurarEncuestaVO configurarEncuestaVO)
		throws ApplicationException {
	    	
			HashMap map = new HashMap();
			
		    map.put("pv_nmconfig_i",configurarEncuestaVO.getNmConfig());
		    
		    map.put("pv_cdunieco_i",configurarEncuestaVO.getCdUnieco());
			map.put("pv_cdramo_i",configurarEncuestaVO.getCdRamo());
			map.put("pv_estado_i",configurarEncuestaVO.getEstado());
			map.put("pv_nmpoliza_i",configurarEncuestaVO.getNmPoliza());

			map.put("pv_cdperson_i",configurarEncuestaVO.getCdPerson());
			map.put("pv_cdencuesta_i",configurarEncuestaVO.getCdEncuesta());
			map.put("pv_cdpregunta_i",configurarEncuestaVO.getCdPregunta());
			map.put("pv_otvalor_i",configurarEncuestaVO.getOtValor());
			
			WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_TVALENCT");
			return res.getMsgText();
		           
	}	
	
		public PagedList obtenerAsignacionEncuesta(String pv_dsunieco_i,String pv_dsramo_i,String pv_estado_i,String  pv_nmpoliza_i, String pv_dsperson_i, String pv_dsusuario_i, int start, int limit) throws ApplicationException{
			HashMap map = new HashMap();
	        map.put("pv_dsunieco_i", pv_dsunieco_i);
			map.put("pv_dsramo_i", pv_dsramo_i);
			map.put("pv_estado_i", pv_estado_i);
			map.put("pv_nmpoliza_i", pv_nmpoliza_i);
			map.put("pv_dsperson_i", pv_dsperson_i);
			map.put("pv_dsusuario_i", pv_dsusuario_i);
	       
	        String endpointName = "ASIGNA_ENCUESTA_OBTIENE_ENCUESTA";
	        return pagedBackBoneInvoke(map, endpointName, start, limit);
		}	
			
		
		public AsignarEncuestaVO getObtenerAsignacionEncuesta(String pv_nmconfig_i, String pv_cdunieco_i, String pv_estado_i, String pv_cdramo_i, String pv_nmpoliza_i) throws ApplicationException {
	        HashMap map = new HashMap();
	        map.put("pv_nmconfig_i", pv_nmconfig_i);
	        map.put("pv_cdunieco_i", pv_cdunieco_i);
			map.put("pv_estado_i", pv_estado_i);
			map.put("pv_cdramo_i", pv_cdramo_i);
			map.put("pv_nmpoliza_i", pv_nmpoliza_i);
			
			
	        String endpointName = "ASIGNA_ENCUESTA_OBTIENE_ENCUESTA_REGISTRO";
	        return (AsignarEncuestaVO)getBackBoneInvoke(map, endpointName );

	    }

		public String guardaAsignacionEncuesta(AsignarEncuestaVO asignarEncuestaVO)
				throws ApplicationException {
             HashMap map = new HashMap();
			
		    map.put("pv_nmconfig_i",asignarEncuestaVO.getNmConfig());
		    
		    map.put("pv_cdunieco_i",asignarEncuestaVO.getCdUnieco());
			map.put("pv_cdramo_i",asignarEncuestaVO.getCdRamo());
			map.put("pv_estado_i",asignarEncuestaVO.getEstado());
			map.put("pv_nmpoliza_i",asignarEncuestaVO.getNmPoliza());

			map.put("pv_cdperson_i",asignarEncuestaVO.getCdPerson());
			map.put("pv_status_i",asignarEncuestaVO.getStatus());
			map.put("pv_cdusuario_i",asignarEncuestaVO.getCdUsuario());
			
			
			WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_ASIGNACION_ENCUESTA");
			return res.getMsgText();
		}

		public TableModelExport getModelObtenerValoresEncuesta(String pv_dsunieco_i, String pv_dsramo_i,String pv_dsencuesta_i, String pv_dscampana_i,
				String pv_dsmodulo_i, String pv_dsproceso_i,String pv_nmpoliza_i) throws ApplicationException {
			
            TableModelExport model = new TableModelExport();
			
			List lista = null;

			HashMap map = new HashMap();
		    map.put("pv_dsunieco_i",pv_dsunieco_i);
			map.put("pv_dsramo_i",pv_dsramo_i);
			map.put("pv_dsencuesta_i",pv_dsencuesta_i);
			map.put("pv_dscampana_i",pv_dscampana_i);
			map.put("pv_dsmodulo_i",pv_dsmodulo_i);
			map.put("pv_dsproceso_i",pv_dsproceso_i);
			map.put("pv_nmpoliza_i",pv_nmpoliza_i);
			
			lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTENER_VALORES_ENCUESTA_EXPORT");

			model.setInformation(lista);
			model.setColumnName(new String[]{"Aseguradora","Producto","Encuesta","Campaña","Cliente","Proceso","Póliza"});
			
			return model;
		}

		
		/**
		 *  Salva la informacion de encuesta junto con las preguntas.
		 *  Usa el store procedure PKG_ENCUESTAS.P_GUARDA_TENCUEST para la encuesta y
		 *   PKG_ENCUESTAS.P_GUARDA_TPREGUNTA para las pregunta de la encuesta.
		 * 
		 *  @param encuestaPreguntaVO
		 *  
		 *  @return BackBoneResultVO
		 */		
		@SuppressWarnings("unchecked")
		public BackBoneResultVO insertarNuevaEncuesta(EncuestaPreguntaVO encuestaPreguntaVO) throws ApplicationException{
			WrapperResultados res1 = new WrapperResultados();
			BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
				HashMap map = new HashMap();
				map.put("cdEncuesta",encuestaPreguntaVO.getCdEncuesta());
				map.put("dsEncuesta",encuestaPreguntaVO.getDsEncuesta());
				map.put("swEstado",encuestaPreguntaVO.getSwEstado());
	            WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_TENCUEST");
	            List<PreguntaEncuestaVO> listapreguntaEncuestaVO = encuestaPreguntaVO.getPreguntaEncuestaVOs();
	            HashMap map1 = new HashMap();
	            if (listapreguntaEncuestaVO != null) {
	                for (int i=0; i<listapreguntaEncuestaVO.size(); i++)
	                {
	                	PreguntaEncuestaVO preguntaEncuestaVO=listapreguntaEncuestaVO.get(i);
	                    map1.put("cdEncuesta",res.getResultado());
	                    map1.put("cdPregunta",preguntaEncuestaVO.getCdPregunta());
	                    map1.put("dsPregunta",preguntaEncuestaVO.getDsPregunta());
	                    map1.put("ottApval",preguntaEncuestaVO.getOttApval());
	                    String _getSwObliga=(preguntaEncuestaVO.getSwObliga().equals("true")?"S":"N");
	                    map1.put("swObliga",_getSwObliga);
	                    map1.put("cdDefault",preguntaEncuestaVO.getCdDefault());
	                    map1.put("cdSecuencia",(i+1));
	                    res1 =  returnBackBoneInvoke(map1,"GUARDAR_TPREGUNTA");
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
		 *  Obtine la informaciond de las preguntas de la encuesta.
		 *  Usa el store procedure PKG_ENCUESTAS.P_OBTIENE_TPREGUNTA para las pregunta de la encuesta.
		 * 
		 *  @param cdEncuesta
		 *  
		 *  @return PagedList
		 */	
		@SuppressWarnings("unchecked")
		public PagedList obtenerEncuestaPregunta(String cdEncuesta, int start, int limit) throws ApplicationException{
			HashMap map = new HashMap();
	        map.put("cdEncuesta", cdEncuesta);
	        
	        String endpointName = "OBTIENE_ENCUESTA_PREGUNTAS";
	        return pagedBackBoneInvoke(map, endpointName, start, limit);
		}
	
		/*
		@SuppressWarnings("unchecked")
		public List obtenerEncuestaPregunta(String cdEncuesta) throws ApplicationException {
			HashMap map = new HashMap();
			map.put("cdEncuesta", cdEncuesta);
			return getAllBackBoneInvoke(map,"OBTIENE_ENCUESTA_PREGUNTAS");
		}*/

		/**
		 *  Obtiene una configuracion de encuesta especifica en base a un parametro de entrada.
		 *  Hace uso del Store Procedure PKG_ENCUESTAS.P_OBTIENE_TENCUEST_REG
		 * 
		 *  @param cdEncuesta
		 *  
		 *  @return Objeto EncuestaVO
		 *  
		 *  @throws ApplicationException
		 */
		@SuppressWarnings("unchecked")
		public EncuestaVO getEncuestaPregunta(String cdEncuesta) throws ApplicationException
		{
			HashMap map = new HashMap();
			map.put("cdEncuesta",cdEncuesta);
			
	        return (EncuestaVO)getBackBoneInvoke(map,"OBTENERREG_ENCUESTA_PREGUNTAS");
		}
		
		/**
		 *  Obtiene un conjunto de preguntas de encuesta para la exportacion a un formato predeterminado.
		 * 
		 *  Parametros de busqueda:
		 *  @param cdEncuesta
		 *  
		 *  @return Objeto modelo para exportar.
		 *  
		 *  @throws ApplicationException
		 */
		@SuppressWarnings("unchecked")
		public TableModelExport getModelEncuestaPregunta(String cdEncuesta) throws ApplicationException {

			TableModelExport model = new TableModelExport();
			
			List lista = null;
			HashMap map = new HashMap();
			map.put("cdEncuesta",cdEncuesta);
			
			lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_ENCUESTA_PREGUNTAS_EXPORT");
			model.setInformation(lista);
			model.setColumnName(new String[]{"Descripción","Obligatorio","Lista de Valores","Valor Tabla","Valor Inicial"});
			
			return model;
		}

		/**
		 *  Salva la informacion de encuesta junto con las preguntas.
		 *  Usa el store procedure PKG_ENCUESTAS.P_GUARDA_TENCUEST para la encuesta y
		 *   PKG_ENCUESTAS.P_GUARDA_TPREGUNTA para las pregunta de la encuesta.
		 * 
		 *  @param encuestaPreguntaVO
		 *  
		 *  @return BackBoneResultVO
		 */		
		@SuppressWarnings("unchecked")
		public BackBoneResultVO agregarNuevaEncuesta(EncuestaPreguntaVO encuestaPreguntaVO) throws ApplicationException{
			WrapperResultados res1 = new WrapperResultados();
			BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
				HashMap map = new HashMap();
				map.put("cdEncuesta",encuestaPreguntaVO.getCdEncuesta());
				map.put("dsEncuesta",encuestaPreguntaVO.getDsEncuesta());
				map.put("swEstado",encuestaPreguntaVO.getSwEstado());
	            WrapperResultados res =  returnBackBoneInvoke(map,"GUARDAR_TENCUEST_AGREGAR");
	            List<PreguntaEncuestaVO> listapreguntaEncuestaVO = encuestaPreguntaVO.getPreguntaEncuestaVOs();
	            HashMap map1 = new HashMap();
	            if (listapreguntaEncuestaVO != null) {
	                for (int i=0; i<listapreguntaEncuestaVO.size(); i++)
	                {
	                	PreguntaEncuestaVO preguntaEncuestaVO=listapreguntaEncuestaVO.get(i);
	                    map1.put("cdEncuesta",preguntaEncuestaVO.getCdEncuesta());
	                    map1.put("cdPregunta",preguntaEncuestaVO.getCdPregunta());
	                    map1.put("dsPregunta",preguntaEncuestaVO.getDsPregunta());
	                    //map1.put("ottApval",preguntaEncuestaVO.getOttApval());
	                    String _getSwObliga=(preguntaEncuestaVO.getSwObliga().equals("true")?"S":"N");
	                    map1.put("swObliga",_getSwObliga);
	                    map1.put("cdDefault",preguntaEncuestaVO.getCdDefault());
	                    map1.put("ottApval", preguntaEncuestaVO.getOttApval());
	                    map1.put("cdSecuencia",(i+1));
	                    res1 =  returnBackBoneInvoke(map1,"GUARDAR_TPREGUNTA");
	                }
	                backBoneResultVO.setMsgText(res1.getMsgText());
	                backBoneResultVO.setOutParam(encuestaPreguntaVO.getCdEncuesta());
	            }else {
	                backBoneResultVO.setMsgText(res.getMsgText());
	                backBoneResultVO.setOutParam(encuestaPreguntaVO.getCdEncuesta());
	            }
			return backBoneResultVO;
		}

		public WrapperResultados validaConfiguracionEncuesta(String cdModulo,
				String cdUnieco, String cdRamo, String cdElemento)
				throws ApplicationException {
			
			HashMap map = new HashMap();
			map.put("pv_cdmodulo_i",cdModulo);
			map.put("pv_cdunieco_i",cdUnieco);
			map.put("pv_cdramo_i",cdRamo);
			map.put("pv_cdelemento_i",cdElemento);
			
	        return this.returnBackBoneInvoke(map,"VALIDA_TCONFIGENCUESTA_CLI");
	        			
		}
		
		
		public PagedList getObtenerValorEncuestaRegistro(String pv_nmconfig_i, String pv_cdunieco_i, String pv_cdramo_i, String pv_estado_i, String pv_nmpoliza_i,String pv_cdperson_i, int start, int limit) throws ApplicationException{
			 	
				HashMap map = new HashMap();
		        
				map.put("pv_nmconfig_i", pv_nmconfig_i);
				map.put("pv_cdunieco_i", pv_cdunieco_i);
				map.put("pv_cdramo_i", pv_cdramo_i);
				map.put("pv_estado_i", pv_estado_i);
				map.put("pv_nmpoliza_i", pv_nmpoliza_i);
				map.put("pv_cdperson_i", pv_cdperson_i);
				
		        
		        String endpointName = "OBTENER_TVALENCT_REG";
		        return pagedBackBoneInvoke(map, endpointName, start, limit);
		
		
		}
		
		@SuppressWarnings("unchecked")
		public ConfigurarEncuestaVO getObtenerValorEncuestaEncabezado(String pv_nmconfig_i, String pv_cdunieco_i, String pv_cdramo_i, String pv_estado_i, String pv_nmpoliza_i,String pv_cdperson_i) throws ApplicationException {
	        HashMap map = new HashMap();
	        map.put("pv_nmconfig_i", pv_nmconfig_i);
			map.put("pv_cdunieco_i", pv_cdunieco_i);
			map.put("pv_cdramo_i", pv_cdramo_i);
			map.put("pv_estado_i", pv_estado_i);
			map.put("pv_nmpoliza_i", pv_nmpoliza_i);
			map.put("pv_cdperson_i", pv_cdperson_i);
			
	        String endpointName = "OBTENER_TVALENCT_ENC";
	        return (ConfigurarEncuestaVO)getBackBoneInvoke(map, endpointName );

	    }
		
		@SuppressWarnings("unchecked")
		public HashMap obtenerIngresarEncuestasEditarParaExportar (String nmconfig, String cdunieco, String cdramo, String estado, String nmpoliza,String cdperson, String cdelemento) throws ApplicationException {
				
			//Carga de datos del Caso
			ConfigurarEncuestaVO configurarEncuestaVO = this.getObtenerValorEncuestaEncabezado(nmconfig,cdunieco,cdramo,estado,nmpoliza,cdperson); 
			
			HashMap datosAExportar = new HashMap();
			
			datosAExportar.put("01NOMBRE_CLIENTE", (configurarEncuestaVO.getDsPerson()!= null)?configurarEncuestaVO.getDsPerson():"");//configurarEncuestaVO.getDsPerson());
			datosAExportar.put("02CLAVE_CATWEB",(configurarEncuestaVO.getCdPerson()!= null)?configurarEncuestaVO.getCdPerson():"");// configurarEncuestaVO.getCdPerson());
			datosAExportar.put("03CLIENTE_CUENTA", (cdelemento!= null)?cdelemento:"");//cdelemento);
			datosAExportar.put("04ASEGURADORA",(configurarEncuestaVO.getDsUnieco()!= null)?configurarEncuestaVO.getDsUnieco():"");// configurarEncuestaVO.getDsUnieco());
			datosAExportar.put("05PRODUCTO", (configurarEncuestaVO.getDsRamo()!= null)?configurarEncuestaVO.getDsRamo():"");//configurarEncuestaVO.getDsRamo());
			datosAExportar.put("06MODULO", (configurarEncuestaVO.getDsModulo()!= null)?configurarEncuestaVO.getDsModulo():"");//configurarEncuestaVO.getDsModulo());
			//datosAExportar.put("06MODULO", (configurarEncuestaVO.getDsElemen()!= null)?configurarEncuestaVO.getDsElemen():"");//configurarEncuestaVO.getDsElemen());
			datosAExportar.put("07CAMPANIA",(configurarEncuestaVO.getDsCampan()!= null)?configurarEncuestaVO.getDsCampan():"");// configurarEncuestaVO.getDsCampan());
			datosAExportar.put("08PROCESO", (configurarEncuestaVO.getDsProceso()!= null)?configurarEncuestaVO.getDsProceso():"");//configurarEncuestaVO.getDsProceso());
			datosAExportar.put("09ENCUESTA",(configurarEncuestaVO.getDsEncuesta()!= null)?configurarEncuestaVO.getDsEncuesta():"");// configurarEncuestaVO.getDsEncuesta());
			datosAExportar.put("10NUMERO_POLIZA",(configurarEncuestaVO.getNmPoliex()!= null)?configurarEncuestaVO.getNmPoliex():"");// configurarEncuestaVO.getNmPoliex());

			
			/*datosAExportar.put("01NOMBRE_CLIENTE", configurarEncuestaVO.getDsPerson());
			datosAExportar.put("02CLAVE_CATWEB", configurarEncuestaVO.getCdPerson());
			datosAExportar.put("03CLIENTE_CUENTA", cdelemento);
			datosAExportar.put("04ASEGURADORA", configurarEncuestaVO.getDsUnieco());
			datosAExportar.put("05PRODUCTO", configurarEncuestaVO.getDsRamo());
			//datosAExportar.put("06MODULO", configurarEncuestaVO.getDsModulo());
			datosAExportar.put("06MODULO", configurarEncuestaVO.getDsElemen());
			datosAExportar.put("07CAMPANIA", configurarEncuestaVO.getDsCampan());
			datosAExportar.put("08PROCESO", configurarEncuestaVO.getDsProceso());
			datosAExportar.put("09ENCUESTA", configurarEncuestaVO.getDsEncuesta());
			datosAExportar.put("10NUMERO_POLIZA", configurarEncuestaVO.getNmPoliex());*/
			
			return datosAExportar;
		}
		
		public HashMap<String, ItemVO> obtenerIngresarEncuestasEditarVariablesParaExportar (String nmconfig, String cdunieco, String cdramo, String estado, String nmpoliza,String cdperson) throws ApplicationException {
			HashMap map = new HashMap();
			HashMap<String, ItemVO> mapaAtributos = new HashMap<String, ItemVO>();
			//Carga de atributos variables del caso
			PagedList pagedList = getObtenerValorEncuestaRegistro(nmconfig, cdunieco, cdramo, estado, nmpoliza, cdperson, 0, 9999); 
				
			List<ConfigurarEncuestaVO> listaAtributos = pagedList.getItemsRangeList();
			int i = 0;
			while (i < listaAtributos.size()) {
				ConfigurarEncuestaVO atributosVO = (ConfigurarEncuestaVO)listaAtributos.get(i);
				String key = "Atributo_" + atributosVO.getCdEncuesta()+ "_" + atributosVO.getOtValor();
				ItemVO itemVO = new ItemVO();
				
				itemVO.setId(atributosVO.getDsPregunta());
				itemVO.setTexto(atributosVO.getOtValor());
				mapaAtributos.put(key, itemVO);
				i++;
			}
			return mapaAtributos;
		}
		
}
	

	

	
	
	
	