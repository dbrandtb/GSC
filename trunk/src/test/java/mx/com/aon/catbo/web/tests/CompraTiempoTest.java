package mx.com.aon.catbo.web.tests;

import java.util.ArrayList;
import java.util.List;


import mx.com.aon.catbo.model.AsignarEncuestaVO;
import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.CatboTimecVO;
import mx.com.aon.catbo.model.CompraTiempoVO;
import mx.com.aon.catbo.model.ConfigurarEncuestaVO;
import mx.com.aon.catbo.model.EncuestaVO;
import mx.com.aon.catbo.model.FormatoDocumentoVO;
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.service.ConfigurarCompraTiempoManager;
import mx.com.aon.catbo.service.ConfigurarEncuestaManager;
import mx.com.aon.catbo.service.FormatosDocumentosManager;
import mx.com.aon.portal.service.PagedList;

public class CompraTiempoTest extends AbstractTestCases {
	protected ConfigurarCompraTiempoManager configurarCompraTiempoManager;
	
	/* public void testBuscarConfguracionEncuesta() throws Exception {
			try {
				
				PagedList pagedList =  (PagedList)configurarEncuestaManager.obtieneEncuesta("", "", "", "", "", "", "", 0, 10);
				//pv_dsunieco_i, pv_dsramo_i, pv_dselemento_i, pv_dsproceso_, pv_dscampan_i, pv_dsmodulo_i, pv_dsencuesta_i, start, limit
				//assertNotNull(pagedList);
	            List lista = pagedList.getItemsRangeList();
	            for (int i=0; i<lista.size(); i++){
	            	FormatoDocumentoVO formatoDocumentoVO = (FormatoDocumentoVO)lista.get(i);
	    			System.out.println("Codigo: "+formatoDocumentoVO.getCdFormato() +"  Nombre: " +formatoDocumentoVO.getDsNomFormato() +" Descripcion: "+formatoDocumentoVO.getDsFormato());
	    			
	            }
	            System.out.println("sali");           
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	 
	 
	 
	 @SuppressWarnings("unchecked")
	public void testBuscarConfguracionEncuesta() throws Exception {
			try {
				
				PagedList pagedList =  (PagedList)configurarEncuestaManager.obtenerEncuestas("",0,10);
				//pv_dsencuesta_i
				
				//assertNotNull(pagedList);

				List lista = pagedList.getItemsRangeList();
	            for (int i=0; i<lista.size(); i++){
	            	EncuestaVO encuestaVO = (EncuestaVO)lista.get(i);
	    			logger.debug("Codigo: "+ encuestaVO.getCdEncuesta()+"  Nombre: " + encuestaVO.getDsEncuesta());
	    			
	            }
	            logger.debug("sali");           
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	 
	 @SuppressWarnings("unchecked")
		public void testBuscarValorEncuesta() throws Exception {
				try {
					
					PagedList pagedList =  (PagedList)configurarEncuestaManager.obtenerValorEncuesta("","","","","","","",0,10);
					//pv_dsunieco_i, pv_dsramo_i, pv_dsencuesta_i, pv_dscampana_i, pv_dsmodulo_i, pv_dsproceso_i, pv_nmpoliza_i
					
					//assertNotNull(pagedList);

					List lista = pagedList.getItemsRangeList();
		            for (int i=0; i<lista.size(); i++){
		            	ConfigurarEncuestaVO configurarEncuestaVO = (ConfigurarEncuestaVO)lista.get(i);
		    			logger.debug("Codigo: "+ configurarEncuestaVO.getCdEncuesta()+"  Nombre: " + configurarEncuestaVO.getDsEncuesta());
		    			
		            }
		            logger.debug("sali");           
					
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
	 */
	
	/*
	 public void testGetObtenerEncuestaRegistro() throws Exception {
			try {
				String CdFormato_b = "1";
				
				ConfigurarEncuestaVO configurarEncuestaVO = configurarEncuestaManager.getObtenerEncuestaRegistro("7", "11", "1", "1", "1");
							
		        assertNotNull(configurarEncuestaVO);
		        assertEquals("El valor de numconfig debe ser ",configurarEncuestaVO.getNmConfig(),"7" );
		        assertEquals("El valor de cdproceso debe ser ",configurarEncuestaVO.getCdProceso(),"11" );
		        assertEquals("El valor de cdcampan debe ser ",configurarEncuestaVO.getCdCampan(), "1" );
		        assertEquals("El valor de cdmodulo debe ser ",configurarEncuestaVO.getCdModulo(), "1" );
		        assertEquals("El valor de cdencuesta debe ser ",configurarEncuestaVO.getCdEncuesta(), "1" );
		        
			
			}catch (Exception e) {
		        fail("Fallo en la lectura del registro");
		        e.printStackTrace();
		    }
		}*/

	/*
	 public void testBorrarTiempoEncuesta () throws Exception {
	       try {
	          // borro el registro ingresado previamente
	     	  configurarEncuestaManager.borraTiempoEncuesta("2");
	     	  logger.debug("Listo");
	       }catch (Exception e) {
	           fail("Fallo en el borrar del registro de motivos");
	           e.printStackTrace();
	           throw e;
	       }
	   }
	
	public void testBorrarAsignarEncuesta () throws Exception {
	       try {
	          // borro el registro ingresado previamente
	     	  configurarEncuestaManager.borraAsignaEncuesta("8", "3", "50" , "W" , "10558" , "212" , "CCRuz");
	     	  //logger.debug("Listo");
	       }catch (Exception e) {
	           fail("Fallo en el borrar del registro de motivos");
	           e.printStackTrace();
	           throw e;
	       }
	   }
	   */
	/*	public void testBorrarEncuestaRegistro () throws Exception {
	       try {
	          // borro el registro ingresado previamente
	     	  configurarEncuestaManager.borrarEncuestaRegistro("22", "1", "1", "6", "1");
	     	  //logger.debug("Listo");
	       }catch (Exception e) {
	           fail("Fallo en el borrar del registro de motivos");
	           e.printStackTrace();
	           throw e;
	       }
	   }

	*//*	
	public void testBorrarTvalenct () throws Exception {
	       try {
	          // borro el registro ingresado previamente
	     	  configurarEncuestaManager.borrarTvalenct("7", "8", "120", "10557","1","1");
	     	  //logger.debug("Listo");
	       }catch (Exception e) {
	           fail("Fallo en borrar Tvalenct");
	           e.printStackTrace();
	           throw e;
	       }
	   } 
	   
	
	public void testBorrarTpregunt () throws Exception {
	       try {
	          // borro el registro ingresado previamente
	     	  configurarEncuestaManager.borrarTpregunt("", "");
	     	  //logger.debug("Listo");
	       }catch (Exception e) {
	           fail("Fallo en borrar Tpregunt");
	           e.printStackTrace();
	           throw e;
	       }
	   }

	public void testBorrarcatbotimec () throws Exception {
	       try {
	          // borro el registro ingresado previamente
	     	  configurarEncuestaManager.borraCATBOTiempoEncuesta("10", "3" , "60");
	     	  logger.debug("Listo");
	       }catch (Exception e) {
	           fail("Fallo en borrar Tpregunt");
	           e.printStackTrace();
	           throw e;
	       }
	   }
	
	public void testFormatosDocumentos () throws Exception {
	      try {
	          //borro el registro ingresado previamente
	    	  formatosDocumentosManager.borrarFormatosDocumentos("4");
	      }catch (Exception e) {
	          fail("Fallo en el borrar del registro de motivos");
	          e.printStackTrace();
	          throw e;
	      }
	  }

	 public void testGetFormatosDocumentos() throws Exception {
			try {
				String CdFormato_b = "1";
				
				FormatoDocumentoVO formatoDocumentoVO = formatosDocumentosManager.getFormatosDocumentos(CdFormato_b);
			
		       assertNotNull(formatoDocumentoVO);
		        assertEquals("El valor de Codigo debe ser ",formatoDocumentoVO.getCdFormato(), "1" );
		        assertEquals("El valor de Nombre debe ser ",formatoDocumentoVO.getDsNomFormato(),"nose" );
		        assertEquals("El valor de Descripcion debe ser ",formatoDocumentoVO.getDsFormato(), "nose" );
				
			}catch (Exception e) {
		        fail("Fallo en la lectura del registro");
		        e.printStackTrace();
		    }
		}

    public void testInsertarFormatosDocumentos() throws Exception {
        try {
			FormatoDocumentoVO formatoDocumentoVO = new FormatoDocumentoVO();
			formatoDocumentoVO.setCdFormato("");
			formatoDocumentoVO.setDsNomFormato("Borrar Despues");
			formatoDocumentoVO.setDsFormato("<html><body>Por favor Borrar Despues</body></html>");
      	  
    		//todo setear los parametros de la agregacion
			formatosDocumentosManager.guardarFormatosDocumentos(formatoDocumentoVO);
			formatoDocumentoVO = formatosDocumentosManager.getFormatosDocumentos("5");

			assertNotNull(formatoDocumentoVO);
	        assertEquals("El valor de Codigo debe ser ",formatoDocumentoVO.getCdFormato(), "5" );
	        assertEquals("El valor de Nombre debe ser ",formatoDocumentoVO.getDsNomFormato(),"Borrar Despues" );
	        assertEquals("El valor de Descripcion debe ser ",formatoDocumentoVO.getDsFormato(), "<html><body>Por favor Borrar Despues</body></html>" );
	        System.out.println("sali");
          
          logger.error("Todo va bien en Insertar formatosDocumentos");
        } catch (Exception e) {
            logger.error("Exception Insertar formatosDocumentos");
            e.printStackTrace();
            throw e;

        }

    }
	public void testGetModel()throws Exception{
		try{
			this.formatosDocumentosManager.getModel("");
			}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	}

	 public void testGuardaTiempoEncuesta() throws Exception {
	        try {
	        	ConfigurarEncuestaVO configurarEncuestaVO = new ConfigurarEncuestaVO();
	        	configurarEncuestaVO.setCdEncuesta("2");
	        	configurarEncuestaVO.setDsEncuesta("PRIMERA ENCUESTA REALIZADA PRUEBA");
	        	configurarEncuestaVO.setSwEstado("0");
	        	
	        	//todo setear los parametros de la agregacion
	        	//BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
	        	
	        	configurarEncuestaManager.guardaTiempoEncuestaTencuest(configurarEncuestaVO);
	        	
	        	logger.debug("Listo");
	        	
	        } catch (Exception e) {
	            logger.error("Exception Insertar formatosDocumentos");
	            e.printStackTrace();
	            throw e;
	        }*/
	        
	 /*
	        public void testGuardaTiempoEncuestaCATBOTIMEC() throws Exception {
		        try {
		        	ConfigurarEncuestaVO configurarEncuestaVO = new ConfigurarEncuestaVO();
		        	configurarEncuestaVO.setCdCampan("2");
		        	configurarEncuestaVO.setCdRamo("50");
		        	configurarEncuestaVO.setCdUnieco("3");
		        	configurarEncuestaVO.setCdUnidtmpo("H");
		        	configurarEncuestaVO.setNmUnidad("30");
		        	
		        	//todo setear los parametros de la agregacion
		        	//BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
		        	
		        	configurarEncuestaManager.guardaTiempoEncuesta(configurarEncuestaVO);
		        	
		        	logger.debug("Listo");
		        	
		        } catch (Exception e) {
		            logger.error("Exception Insertar formatosDocumentos");
		            e.printStackTrace();
		            throw e;
		        }
	
}

	*/
	 
	/*public void testGuardarTpregunta() throws Exception {
	        try {
	        	ConfigurarEncuestaVO configurarEncuestaVO = new ConfigurarEncuestaVO();
	        	configurarEncuestaVO.setCdEncuesta("1");
	        	configurarEncuestaVO.setCdPregunta("2");
	        	configurarEncuestaVO.setDsPregunta("cual");
	        	configurarEncuestaVO.setSwobliga("n");
	        	configurarEncuestaVO.setCdsecuencia("2");
	        	configurarEncuestaVO.setCddefault("ooj");
	        	
	        	
	        	
	        	//todo setear los parametros de la agregacion
	        	//BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
	        	
	        	configurarEncuestaManager.guardaTpregunta(configurarEncuestaVO);
	        	
	        	logger.debug("Listo");
	        } 
	        catch (Exception e) {
	            logger.error("Exception Insertar ConfigurarTpregunta");
	            e.printStackTrace();
	            throw e;
	        }
}	 
 */
	/*
	@SuppressWarnings("unchecked")
	public void testObtieneEncuesta() throws Exception {
		try {
				
				PagedList pagedList =  (PagedList)configurarEncuestaManager.obtieneEncuesta("","","","","","","",0,10);
				//pv_dsunieco_i, pv_dsramo_i, pv_dsencuesta_i, pv_dscampana_i, pv_dsmodulo_i, pv_dsproceso_i, pv_nmpoliza_i
				
				//assertNotNull(pagedList);

				List lista = pagedList.getItemsRangeList();
	            for (int i=0; i<lista.size(); i++){
	            	ConfigurarEncuestaVO configurarEncuestaVO = (ConfigurarEncuestaVO)lista.get(i);
	    			logger.debug("Nombre: "+ configurarEncuestaVO.getDsUnieco());
	    			
	            }
	            logger.debug("sali");           
				
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
			}
	}
	@SuppressWarnings("unchecked")
	public void testObtenerEncuestas() throws Exception {
		try {
				
				PagedList pagedList =  (PagedList)configurarEncuestaManager.obtenerEncuestas("", 0, 10);
				//pv_dsunieco_i, pv_dsramo_i, pv_dsencuesta_i, pv_dscampana_i, pv_dsmodulo_i, pv_dsproceso_i, pv_nmpoliza_i
				0
				//assertNotNull(pagedList);

				List lista = pagedList.getItemsRangeList();
	            for (int i=0; i<lista.size(); i++){
	            	EncuestaVO encuestaVO = (EncuestaVO)lista.get(i);
	    			logger.debug("Nombre: "+ encuestaVO.getDsEncuesta());
	    			
	            }
	            logger.debug("sali");           
				
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
			}
	}
	*/
	/* public void testGetCatboTimec() throws Exception {
			try {
				CatboTimecVO catboTimecVO = configurarEncuestaManager.getCatboTimec("1", "2", "50");
			
		        assertNotNull(catboTimecVO);
		        assertEquals("El valor de campana debe ser ",catboTimecVO.getCdUnidtmpo(),"H" );
		        assertEquals("El valor de unieco debe ser ",catboTimecVO.getNmUnidad(),"1" );
		       
				
			}catch (Exception e) {
		        fail("Fallo en la lectura del registro");
		        e.printStackTrace();
		    }
		}
	*/
	/*
	@SuppressWarnings("unchecked")
	public void testObtenerValorEncuestas() throws Exception {
		try {
				
				PagedList pagedList =  (PagedList)configurarEncuestaManager.obtenerValorEncuesta("", "", "", "", "", "", "", 0,10);
				
				
				//assertNotNull(pagedList);

				List lista = pagedList.getItemsRangeList();
	            for (int i=0; i<lista.size(); i++){
	            	ConfigurarEncuestaVO configurarEncuestaVO = (ConfigurarEncuestaVO)lista.get(i);
	    			logger.debug("Nombre Ramo: "+ configurarEncuestaVO.getDsRamo());
	    			
	            }
	            logger.debug("sali");           
				
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
			}
	}*/
		
	/*	 public void testGuardaTvalenct() throws Exception {
		        try {
		        	ConfigurarEncuestaVO configurarEncuestaVO = new ConfigurarEncuestaVO();
		        	
		        	configurarEncuestaVO.setNmConfig("8");
		        	configurarEncuestaVO.setCdUnieco("7");
		        	configurarEncuestaVO.setCdRamo("120");
		        	
		        	configurarEncuestaVO.setEstado("w");
		        	configurarEncuestaVO.setNmPoliza("10557");
		        	configurarEncuestaVO.setCdPerson("212");

		        	configurarEncuestaVO.setCdEncuesta("1");
		        	configurarEncuestaVO.setCdPregunta("1");
		        	configurarEncuestaVO.setOtValor("MUY BUENO");
		        	
		        	
		        	//todo setear los parametros de la agregacion
		        	//BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
		        	
		        	configurarEncuestaManager.guardarTvalenct(configurarEncuestaVO);
		        	
		        	logger.debug("Listo");
		        	
		         } catch (Exception e) {
		            logger.error("Exception Guardar Tvalenct");
		            e.printStackTrace();
		          throw e;
		 }
		 }
		*/
		 
	
		 @SuppressWarnings("unchecked")
		public void testObtenerCompraTiempoDisponible() throws Exception {
				try {
					
					PagedList pagedList =  (PagedList)configurarCompraTiempoManager.obtieneCompraTiempoDisponible("11", "1","BO100000100", 0, 10);
					
					//assertNotNull(pagedList);
		            List lista = pagedList.getItemsRangeList();
		            for (int i=0; i<lista.size(); i++){
		            	CompraTiempoVO compraTiempoVO = (CompraTiempoVO)lista.get(i);
		    			System.out.println("CDPROCESO:"+ compraTiempoVO.getCdProceso()+ "DSPROCESO: "+ compraTiempoVO.getDsProceso()+"nmCaso"+compraTiempoVO.getNmCaso());
		            }
		            System.out.println("sali");           
					
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
		 
		/* public void testGetObtenerAsignacionEncuesta() throws Exception {
				try {
					String CdFormato_b = "1";
					
					AsignarEncuestaVO asignarEncuestaVO = configurarEncuestaManager.getObtenerAsignacionEncuesta("7", "10557", "212", "CCRUZ");
								
			        assertNotNull(asignarEncuestaVO);
			        assertEquals("El valor de numconfig debe ser ",asignarEncuestaVO.getNmConfig(),"7" );
			        assertEquals("El valor de cdproceso debe ser ",asignarEncuestaVO.getNmPoliza(),"10557" );
			        assertEquals("El valor de cdcampan debe ser ",asignarEncuestaVO.getCdPerson(), "212" );
			        assertEquals("El valor de cdmodulo debe ser ",asignarEncuestaVO.getCdUsuario(), "CCRUZ" );
			        
			        
				
				}catch (Exception e) {
			        fail("Fallo en la lectura del registro");
			        e.printStackTrace();
			    }
			}*/
}



