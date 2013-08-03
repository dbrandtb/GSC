package mx.com.aon.catbo.service.impl;

import mx.com.aon.catbo.model.*;
import mx.com.aon.catbo.service.CombosManager2;
import mx.com.aon.catbo.service.ProcesoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.biosnet.worklistserviceclient.proxy.types.mx.com.biosnet.servicios.worklistservice.facade.types.ParametroVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProcesoManagerImpl extends AbstractManager implements ProcesoManager {

    /**
     *  Obtiene un conjunto de asociar formatos
     *  Hace uso del Store Procedure PKG_ORDENT.P_OBTIENE_FORMATOSXCTA
     *
     *  @param dsProceso
     *  @param dsElemen
     *  @param dsRamo
     *  @param dsFormatoOrden
     *  @param dsUnieco
     *
     *  @return Objeto PagedList
     *
     *  @throws mx.com.aon.catbo.core.ApplicationException
     */
    @SuppressWarnings("unchecked")
    public PagedList buscarAsociarFormatos(String dsProceso, String dsElemen, String dsRamo, String dsFormatoOrden, String dsUnieco, int start, int limit ) throws ApplicationException {
        // Se crea un mapa para pasar los parametros de ejecucion al endpoint
        HashMap map = new HashMap();
        map.put("dsProceso",dsProceso);
        map.put("dsElemen", dsElemen);
        map.put("dsRamo",dsRamo);
        map.put("dsFormatoOrden",dsFormatoOrden);
        map.put("dsUnieco", dsUnieco);
        map.put("start",start);

        map.put("limit",limit);
        return pagedBackBoneInvoke(map, "BUSCAR_ASOCIAR_FORMATOS", start, limit);
    }


    public String obtenerTiempoCaso(String idCaso) throws ApplicationException {
        // Se crea un mapa para pasar los parametros de ejecucion al endpoint
        HashMap map = new HashMap();
        map.put("pv_nmcaso_i",idCaso);

        WrapperResultados wrapperResultados =  returnBackBoneInvoke(map, "OBTENER_TIEMPO_CASO");
        return  wrapperResultados.getResultado();

    }
    
    
    public String obtenerTiempoRestante(String pv_nmcaso_i) throws ApplicationException {
		HashMap map = new HashMap();
        map.put("pv_nmcaso_i", pv_nmcaso_i);

        String endpointName = "OBTENER_TIEMPO_RESTANTE";
        WrapperResultados wrapperResultados =  returnBackBoneInvoke(map, endpointName);
        return  wrapperResultados.getResultado();
	}


	public List obtenerUsuarioProceso(String pv_nmcaso_i) throws ApplicationException {
		HashMap map = new HashMap();
        map.put("pv_nmcaso_i", pv_nmcaso_i);  

        String endpointName = "OBTENER_USUARIO_PROCESO";
        return getAllBackBoneInvoke(map, endpointName);
	}	
	

	public String guardarIdTareaIdInstancia(String pv_nmcaso_i,
			String pv_task_id_i, String pv_instance_id_i)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_nmcaso_i",pv_nmcaso_i);
	    map.put("pv_task_id_i",pv_task_id_i);
		map.put("pv_instance_id_i", pv_instance_id_i);
		
		
		
WrapperResultados res =  returnBackBoneInvoke(map,"GUARADAR_ID_TAREA_INSTANCIA");
return res.getMsgText();

	}

	public String guardarResponsablesAEscalar(String pv_nmcaso_i) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_nmcaso_i",pv_nmcaso_i);	    	
				
WrapperResultados res =  returnBackBoneInvoke(map,"GUARDA_ESCALAMIENTO");
return res.getMsgText();
	}
		
	@SuppressWarnings("unchecked")
		public ProcesosVO getvalidaNivelEscalamiento(String pv_nmcaso_i)throws ApplicationException {
			   String endpointName = "VALIDAR_NIVEL_ESCALAMIENTO";
			   HashMap map = new HashMap();
		       map.put("pv_nmcaso_i",pv_nmcaso_i);
		       
		       
		       return (ProcesosVO)getBackBoneInvoke(map, endpointName);
		   
	}
	
	 public List obtenerParametros(String pv_clave_i, String pv_grupo_i) throws ApplicationException {
			HashMap map = new HashMap();
	        map.put("pv_clave_i",pv_clave_i);
	        map.put("pv_grupo_i",pv_grupo_i);

	        String endpointName = "OBTENER_PARAMETROS";
	        return getAllBackBoneInvoke(map, endpointName);
		}


	public String obtieneTaskId(String pv_nmcaso_i) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_nmcaso_i",pv_nmcaso_i);	    	
				
        WrapperResultados res =  returnBackBoneInvoke(map,"OBTIENE_TASK_ID");
        logger.debug("Valor de TASK_ID: "+res.getResultado());
        return res.getResultado();
	}


	public List obtenerResponsablesEnvio()
			throws ApplicationException {
		HashMap map = new HashMap();
		 
		   String endpointName = "OBTENER_RESONSABLES_DE_ENVIO";
	        return getAllBackBoneInvoke(map, endpointName);
	}
	    
	
	public PagedList obtenerMailResponsables(String pv_cdusuario_i, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
        map.put("pv_cdusuario_i",pv_cdusuario_i);

        String endpointName = "OBTENER_MAIL_RESPONSABLES";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	public PagedList obtenerMailCliente(String pv_cdperson_i, int start, int limit) throws ApplicationException {
		HashMap map = new HashMap();
        map.put("pv_cdperson_i",pv_cdperson_i);

        String endpointName = "OBTENER_MAIL_CLIENTE";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
	}


    public String cancelarProceso(List<CasoVO> listMCasoMovVO,String usuario, String status) throws ApplicationException {
        try {
            //String taskId = obtieneTaskId(idCaso);
        	logger.debug("callingObtieneTaskId con caso: "+listMCasoMovVO.get(0).getNmCaso());
        	String taskId = obtieneTaskId(listMCasoMovVO.get(0).getNmCaso());
            mx.biosnet.worklistserviceclient.proxy.WorklistServiceWSSoapHttpPortClient myPort = new mx.biosnet.worklistserviceclient.proxy.WorklistServiceWSSoapHttpPortClient();
            logger.debug("calling: " + myPort.getEndpoint());
            logger.debug("Usuario: "+ usuario);
            logger.debug("Status: "+ status);
            logger.debug("taskId: "+ taskId);
            logger.debug("cancelarProceso_dsobservacion: "+listMCasoMovVO.get(0).getObservacion());

            ParametroVO[] parametros = new ParametroVO[3];
            ParametroVO parametroVO = null;
            
            parametroVO = new ParametroVO();
            parametroVO.setClave("cdusuario");
            parametroVO.setValor(usuario);
            parametros[0] = parametroVO;


            parametroVO = new ParametroVO();
            parametroVO.setClave("cdstatus");
            parametroVO.setValor(status);
            parametros[1] = parametroVO;
            
            parametroVO = new ParametroVO();
            parametroVO.setClave("dsobservacion");
            parametroVO.setValor(listMCasoMovVO.get(0).getObservacion());
            parametros[2] = parametroVO;

            logger.debug("callCambiarEstado2 con:");
            logger.debug("Caso: "+listMCasoMovVO.get(0).getCaso());
            logger.debug("Status: "+status);
            logger.debug("taskId: "+taskId);
            logger.debug("parametros:"+parametros[0].getValor()+" - "+parametros[1].getValor()+" - "+parametros[2].getValor());
            myPort.cambiarEstado2(getUsuarioResponsable(listMCasoMovVO.get(0).getNmCaso()), status, taskId,parametros);
            logger.debug("se invoco exitosamente el llamado a "+  myPort.getEndpoint());
            //return ("El caso "+ idCaso +" fue "+ status +" exitosamente");
            return ("Operaci&oacute;n realizada con &eacute;xito");

        } catch (Exception ex) {
        	logger.debug("ExceptionCancelarProceso. ex = "+ex.getMessage());
			logger.error("No se puedo invocar el cambio de estado sobre la worklist " , ex);
			//throw new ApplicationException("No se puedo invocar el cambio de estado sobre la worklist " ,ex);
			throw new ApplicationException("No se pudo realizar la operaci&oacute;n. Consulte a Soporte.");
        }

    }


    public String reasignarCasos(List casos,  String estado) throws ApplicationException{
        try {
            mx.biosnet.worklistserviceclient.proxy.WorklistServiceWSSoapHttpPortClient myPort = new mx.biosnet.worklistserviceclient.proxy.WorklistServiceWSSoapHttpPortClient();
            logger.debug("calling: " + myPort.getEndpoint());

            ParametroVO parametroVO = null;

                ParametroVO[] parametros = new ParametroVO[4];
                for (int i = 0; i < casos.size(); i++) {
                    CasoVO  casoVO = (CasoVO) casos.get(i);
                    String taskId = obtieneTaskId(casoVO.getNmCaso());

                    parametroVO = new ParametroVO();
                    parametroVO.setClave("nmcaso");
                    parametroVO.setValor(casoVO.getNmCaso());
                    parametros[0] = parametroVO;

                    logger.debug("nmcaso " + casoVO.getNmCaso());


                    parametroVO = new ParametroVO();
                    parametroVO.setClave("usuarioMov");
                    parametroVO.setValor(casoVO.getCdUsuMov());
                    parametros[1] = parametroVO;
                    logger.debug("usuarioMov " + casoVO.getCdUsuMov());

                    parametroVO = new ParametroVO();
                    parametroVO.setClave("usuarioOld");
                    parametroVO.setValor(casoVO.getCdUsuarioOld());
                    parametros[2] = parametroVO;
                    logger.debug("usuarioOld " + casoVO.getCdUsuarioOld());

                    parametroVO = new ParametroVO();
                    parametroVO.setClave("usuarioNew");
                    parametroVO.setValor(casoVO.getCdUsuarioNew());
                    parametros[3] = parametroVO;
                    logger.debug("usuarioNew " + casoVO.getCdUsuarioNew());

                    logger.debug("estado: "+ estado);

                    myPort.cambiarEstado2(casoVO.getCdUsuarioOld(), estado, taskId,parametros );

                }
            logger.debug("se invoco exitosamente el llamado a "+  myPort.getEndpoint());
            //return ("Se invoco  exitosamente la reasignacion de los casos");
            return ("Operaci&oacute;n realizada con &eacute;xito");

        } catch (Exception ex) {
			logger.error("No se puedo invocar el cambio de estado sobre la worklist " , ex);
			//throw new ApplicationException("No se puedo invocar el cambio de estado sobre la worklist " ,ex);        
			throw new ApplicationException("No se pudo realizar la operaci&oacute;n. Consulte a Soporte.");
        }
			
    }

    public String guardaReasignacion(String numCaso, String cdUsuarioMov, List<ReasignacionCasoVO> listaReasignacionCasoVO) throws ApplicationException {
        try {
            mx.biosnet.worklistserviceclient.proxy.WorklistServiceWSSoapHttpPortClient myPort = new mx.biosnet.worklistserviceclient.proxy.WorklistServiceWSSoapHttpPortClient();
            logger.debug("calling: " + myPort.getEndpoint());


            String taskId = obtieneTaskId(numCaso);
            ParametroVO parametroVO = null;
            ParametroVO[] parametros = new ParametroVO[(listaReasignacionCasoVO.size()*2)+3];

            parametroVO = new ParametroVO();
            parametroVO.setClave("nmcaso");
            parametroVO.setValor(numCaso);
            parametros[0] = parametroVO;
            
            logger.debug("listaReasignacionCasoVO.size() = "+listaReasignacionCasoVO.size());
            if(listaReasignacionCasoVO.size()>0){
            	logger.debug("cdUsuarioMov: "+cdUsuarioMov);
            	logger.debug("cdModulo: "+listaReasignacionCasoVO.get(0).getCdModulo());
            	cdUsuarioMov = cdUsuarioMov.concat("#").concat(listaReasignacionCasoVO.get(0).getCdModulo());
            	logger.debug("cdModulo concatenado a cdUsuarioMov = "+cdUsuarioMov);
            	
            	//Quitar esto, es para pruebas nada mas
        		try{
        			int pos = cdUsuarioMov.indexOf("#")+1;
        			String cdModulo = cdUsuarioMov.substring(pos,pos+1);
        			logger.debug("posicion del caracter #: "+pos);
        			logger.debug("substring obtenido: "+cdModulo);
        			logger.debug("codigo del modulo: "+cdModulo);
        			String usuario = cdUsuarioMov.substring(0,pos-1);
        			logger.debug("Usuario del movimiento: "+usuario);
        		}catch(Exception e){
        			logger.debug("Excepcion en la obtencion del substring. "+e.getMessage());
        		}
            }
            
            parametroVO = new ParametroVO();
            parametroVO.setClave("cdusumov");
            parametroVO.setValor(cdUsuarioMov);
            parametros[1] = parametroVO;
            
            int j, k , z = 1; 
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < listaReasignacionCasoVO.size(); i++) {
                ReasignacionCasoVO reasignacionCasoVO =  listaReasignacionCasoVO.get(i);
                k = z+ 1;
                parametroVO = new ParametroVO();
                parametroVO.setClave("cdusuario");
                parametroVO.setValor(reasignacionCasoVO.getCodUsuario());
                parametros[k] = parametroVO;
                
                z = k +  1;
                parametroVO = new ParametroVO();
                parametroVO.setClave("rol");
                parametroVO.setValor(reasignacionCasoVO.getCodRolMat());
                parametros[z] = parametroVO;               
                
                buffer.append(reasignacionCasoVO.getCodUsuario()).append(",");
                
            }
            j = z + 1;
            String lista = buffer.toString();
            if(lista != null && lista.length()>0){
            	parametroVO = new ParametroVO();
            	parametroVO.setClave("listausr");
                parametroVO.setValor(lista.substring(0, lista.length() - 1));
                logger.debug("listausr en parametros["+j+"]"+lista.substring(0,lista.length()-1));                
            }else{
            	parametroVO.setValor("");
                logger.debug("listausr en parametros["+j+"] se seteo con vacio.");
            }
            logger.debug("listausr en parametros["+j+"] se seteo con "+parametroVO.getValor());
            parametros[j] = parametroVO;
            
            myPort.cambiarEstado2(getUsuarioResponsable(numCaso), "ASIGNARESP", taskId,parametros );

            logger.debug("se invoco exitosamente el llamado a "+  myPort.getEndpoint());
            //return ("Se invoco  exitosamente la reasignacion de los casos");
            
            return ("Operaci&oacute;n realizada con &eacute;xito");

        } catch (Exception ex) {
			logger.error("No se puedo invocar el cambio de estado sobre la worklist " , ex);
			//throw new ApplicationException("No se puedo invocar el cambio de estado sobre la worklist " ,ex);        
			throw new ApplicationException("No se pudo realizar la operaci&oacute;n. Consulte a Soporte.");
        }
    }    


    @SuppressWarnings("unchecked")
	public String guardaSuplente(String cdmatriz, String cdnivatn, String cdusrOld,String cdusrNew, String usuario ) throws ApplicationException {
 
        	List<SuplentesVO> listaCasos = new ArrayList<SuplentesVO>();
        	try{
        		/* llamar al metodo obtenerCasos mandando los parametros requeridos
                 *  guardar en una lista de tipo VO para guardar el parametro de retorno
                 */
             	logger.debug("guardaSuplenteResponsable. Llamando a obtieneCasos con:");
             	logger.debug("cdmatriz: "+cdmatriz);
             	logger.debug("cdusrOld: "+cdusrOld);
             	logger.debug("cdusrNew: "+cdusrNew);
             	logger.debug("usuario: "+usuario);
             	logger.debug("cdnivatn: "+cdnivatn);
             	listaCasos = obtieneCasos(cdmatriz,cdusrOld, cdusrNew, usuario, cdnivatn);
        	}catch(ApplicationException ae){
        		logger.debug("ExceptionObtieneCasos. Excepcion en el llamado al obtiene casos con:");
        		logger.debug("cdmatriz: "+cdmatriz);
             	logger.debug("cdusrOld: "+cdusrOld);
             	logger.debug("cdusrNew: "+cdusrNew);
             	logger.debug("usuario: "+usuario);
             	logger.debug("cdnivatn: "+cdnivatn);
             	throw new ApplicationException(ae.getMessage());
        	}
        	
            try {
	            if (!listaCasos.get(0).getNmCaso().equals("-1"))
	            {
	            	
	              logger.debug("Cantidad de casos "+listaCasos.size());
	                 
		            ParametroVO parametroVO= null;
		            ParametroVO[] parametros = new ParametroVO[6];
	                 
				    for (int i = 0; i < listaCasos.size(); i++) {
					 
		   		     String _numCaso  = listaCasos.get(i).getNmCaso();	 
	                 String taskId = obtieneTaskId(_numCaso);
	
	                 parametroVO = new ParametroVO();
	                 parametroVO.setClave("cdmatriz");
	                 parametroVO.setValor(cdmatriz);
	                 parametros[0] = parametroVO;
	                 logger.debug("parametroVO[0] - clave: cdmatriz"+" - valor: "+cdmatriz);
	 
	                 parametroVO = new ParametroVO();
	                 parametroVO.setClave("cdnivatn");
	                 parametroVO.setValor(cdnivatn);
	                 parametros[1] = parametroVO;
	                 logger.debug("parametroVO[1] - clave: cdnivatn"+" - valor: "+cdnivatn);
	
	                 parametroVO = new ParametroVO();
	                 parametroVO.setClave("cdusuario");
	                 parametroVO.setValor(usuario);
	                 parametros[2] = parametroVO;
	                 logger.debug("parametroVO[2] - clave: cdusuario"+" - valor: "+usuario);
	             
	                 parametroVO = new ParametroVO();
	                 parametroVO.setClave("cdusrOld");
	                 parametroVO.setValor(cdusrOld);
	                 parametros[3] = parametroVO;
	                 logger.debug("parametroVO[3] - clave: cdusrOld"+" - valor: "+cdusrOld);
	
	                 parametroVO = new ParametroVO();
	                 parametroVO.setClave("cdusrNew");
	                 parametroVO.setValor(cdusrNew);
	                 parametros[4] = parametroVO;
	                 logger.debug("parametroVO[4] - clave: cdusrNew"+" - valor: "+cdusrNew);
	               
	                 parametroVO = new ParametroVO();
	                 parametroVO.setClave("nmcaso");
	                 parametroVO.setValor(_numCaso);
	                 parametros[5] = parametroVO;
	                 logger.debug("parametroVO[5] - clave: nmcaso"+" - valor: "+_numCaso);
	                 
	                 mx.biosnet.worklistserviceclient.proxy.WorklistServiceWSSoapHttpPortClient myPort = new mx.biosnet.worklistserviceclient.proxy.WorklistServiceWSSoapHttpPortClient();
	                 logger.debug("calling: " + myPort.getEndpoint());
	                 logger.debug("callingCambiarEstado2 para iteracion i="+i);
	                 logger.debug("Usuario responsable: "+getUsuarioResponsable(_numCaso)+" para el caso "+_numCaso);
	                 logger.debug("Status: SUPLENTE");
	                 logger.debug("taskId: "+taskId);
	                 myPort.cambiarEstado2(getUsuarioResponsable(_numCaso), "SUPLENTE", taskId,parametros );
	
	
	               logger.debug("se invoco exitosamente el llamado a "+  myPort.getEndpoint());
	            
				 }
	            }//return ("Se invoco  exitosamente el guardar Suplente");
	            return ("Operaci&oacute;n realizada con &eacute;xito");
                    
        }catch (Exception ex) {
			logger.error("No se puedo invocar el cambio de estado sobre la worklist " , ex);
			throw new ApplicationException("No se pudo realizar la operaci&oacute;n. Consulte a Soporte.");
          }	
        
    }
    
    

    public String guardaCompraTiempo(String nmcaso, String cdproceso, String cdusuario, String cdnivatn, String nmcompra, String tunidad) throws ApplicationException {
        try {
        	
        	logger.debug("Guarda Compra Tiempo; valores de entrada: ");
        	logger.debug("nmcaso :" +nmcaso );
        	logger.debug("cdproceso :" + cdproceso);
        	logger.debug("cdusuario :" + cdusuario);
        	logger.debug("cdnivatn :" +cdnivatn );
        	logger.debug("nmcompra :" + nmcompra);
        	logger.debug("tunidad :" +tunidad );
        	
            mx.biosnet.worklistserviceclient.proxy.WorklistServiceWSSoapHttpPortClient myPort = new mx.biosnet.worklistserviceclient.proxy.WorklistServiceWSSoapHttpPortClient();
            logger.debug("calling: " + myPort.getEndpoint());


            String taskId = obtieneTaskId(nmcaso);
            ParametroVO parametroVO = null;
            ParametroVO[] parametros = new ParametroVO[6];

            parametroVO = new ParametroVO();
            parametroVO.setClave("nmcaso");
            parametroVO.setValor(nmcaso);
            parametros[0] = parametroVO;

            parametroVO = new ParametroVO();
            parametroVO.setClave("cdproceso");
            parametroVO.setValor(cdproceso);
            parametros[1] = parametroVO;

            parametroVO = new ParametroVO();
            parametroVO.setClave("cdusuario");
            parametroVO.setValor(cdusuario);
            parametros[2] = parametroVO;

            parametroVO = new ParametroVO();
            parametroVO.setClave("cdnivatn");
            parametroVO.setValor(cdnivatn);
            parametros[3] = parametroVO;


            parametroVO = new ParametroVO();
            parametroVO.setClave("nmcompra");
            parametroVO.setValor(nmcompra);
            parametros[4] = parametroVO;

            parametroVO = new ParametroVO();
            parametroVO.setClave("tunidad");
            parametroVO.setValor(tunidad);
            parametros[5] = parametroVO;

            myPort.cambiarEstado2(getUsuarioResponsable(nmcaso), "COMPRATIEMPO", taskId,parametros );


            logger.debug("se invoco exitosamente el llamado a "+  myPort.getEndpoint());
            //return ("Se invoco  exitosamente el guardaCompraTiempo");
            return ("Operaci&oacute;n realizada con &eacute;xito");

        } catch (Exception ex) {
            logger.error("No se puedo invocar el cambio de estado sobre la worklist " , ex);
            //throw new ApplicationException("No se puedo invocar el cambio de estado sobre la worklist " ,ex);
            throw new ApplicationException("No se pudo realizar la operaci&oacute;n. Consulte a Soporte.");
        }
    }

    private String getUsuarioResponsable(String numCaso) throws ApplicationException {
        List responssables =  obtenerUsuarioProceso(numCaso);
        if (responssables != null) {
           // obtengo el primer responsable
            ResultadoGeneraCasoVO resultadoGeneraCasoVO  = (ResultadoGeneraCasoVO) responssables.get(0);
            logger.debug("Usuario responsable: "+ resultadoGeneraCasoVO.getCdUsuario());
            return resultadoGeneraCasoVO.getCdUsuario();

        }  else {
            //throw new ApplicationException("No se encontraron responsables para el numero de Caso  " + numCaso);
        	logger.debug("ERROR al obtener el usuario responsable!!!");
        	throw new ApplicationException("No se pudo realizar la operaci&oacute;n. Consulte a Soporte.");
        }

    }

	@SuppressWarnings("unchecked")
	public List obtieneCasos(String cdmatriz,String cdusrOld, String cdusrNew,String usuario,String cdnivatn)throws ApplicationException {
			  	
			HashMap map = new HashMap();
	  		map.put("pv_cdusuario_i", usuario);
	  		map.put("pv_cdmatriz_i", cdmatriz);
	  		map.put("pv_cdusr_old_i", cdusrOld);
	  		map.put("pv_cdusr_new_i", cdusrNew);
	  		map.put("pv_cdnivatn_i", cdnivatn);
	  		logger.debug("obtieneCasos. Llamando al obtiene casos con:");
	  		logger.debug("pv_cdusuario_i: "+usuario);
	  		logger.debug("pv_cdmatriz_i:"+cdmatriz);
	  		logger.debug("pv_cdusr_old_i:"+cdusrOld);
	  		logger.debug("pv_cdusr_new_i:"+cdusrNew);
	  		logger.debug("pv_cdnivatn_i:"+cdnivatn);
	  		return this.getAllBackBoneInvoke(map,"OBTIENE_CASOS_CLIENTE");		
	}
}
