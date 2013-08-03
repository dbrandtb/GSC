package mx.com.aon.catbo.web.tests;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.NotificacionVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.NotificacionesManager;

public class NotificacionesTest extends AbstractTestCases {
	protected NotificacionesManager notificacionesManager;
	
/*
 public void testBuscarNotificaciones() throws Exception {
	try {
		
		PagedList pagedList =  (PagedList)notificacionesManager.buscarNotificaciones("", "or", "", 0, 10);
        assertNotNull(pagedList);
        List lista = pagedList.getItemsRangeList();
        for (int i=0; i<lista.size(); i++){
        	NotificacionVO notificacionVO = (NotificacionVO)lista.get(i);
			System.out.println("Descripcion: "+notificacionVO.getDsNotificacion()+"  Formato: " +notificacionVO.getDsFormatoOrden()+" Met Env: "+notificacionVO.getDsMetEnv());
        }
        System.out.println("sali");           
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}*/

/*
public void testBorrarNotificaciones () throws Exception {
      try {
          //borro el registro ingresado previamente
    	  notificacionesManager.borrarNotificaciones("2");
      }catch (Exception e) {
          fail("Fallo en el borrar del registro de motivos");
          e.printStackTrace();
          throw e;
      }
  }
*/

/*
public void testBorrarNotificacionesProcesos () throws Exception {
      try {
          //borro el registro ingresado previamente
    	  notificacionesManager.borrarNotificacionesProcesos("3","3");
      }catch (Exception e) {
          fail("Fallo en el borrar del registro de notificaciones procesos");
          e.printStackTrace();
          throw e;
      }
  }
*/
		  
/*		
		
 public void testGetNotificaciones() throws Exception {
		try {
			String CdFormato_b = "1";
			
			NotificacionVO notificacionVO = notificacionesManager.getNotificaciones(CdFormato_b);
		
	       assertNotNull(notificacionVO);
	        assertEquals("El valor de Codigo debe ser ",notificacionVO.getCdNotificacion(),"1" );
	        assertEquals("El valor de la descripcion de Notificacion ser ",notificacionVO.getDsNotificacion(),"retraso" );
	        assertEquals("El valor de del codigo de formato debe ser ",notificacionVO.getCdFormatoOrden(), "1" );
	        assertEquals("El valor del nombre de formato debe ser ",notificacionVO.getDsFormatoOrden(), "Formal" );
	        assertEquals("El valor de Descripcion debe ser ",notificacionVO.getCdMetEnv() , "1" );
	        assertEquals("El valor de Descripcion debe ser ",notificacionVO.getDsMetEnv(), "Mail" );
			
		}catch (Exception e) {
	        fail("Fallo en la lectura del registro");
	        e.printStackTrace();
	    }
	}
*/
/*	
 public void testGetNotificacionesProceso() throws Exception {
		try {
			String CdFormato_b = "1";
			PagedList pagedList =  (PagedList)notificacionesManager.getNotificacionesProceso(CdFormato_b, 0, 10);
	        assertNotNull(pagedList);
	        List lista = pagedList.getItemsRangeList();
	        for (int i=0; i<lista.size(); i++){
	        	NotificacionVO notificacionVO = (NotificacionVO)lista.get(i);
				System.out.println("Codigo: "+notificacionVO.getCdProceso()+"  Descripcion: " +notificacionVO.getDsProceso());
	        }
	        System.out.println("sali");           
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
 }		
 */
 
/*	
	public void testBuscarProcesosNotificaciones() throws Exception {
		try {
			
			PagedList pagedList =  (PagedList)notificacionesManager.getProcesoNotificaciones("", 0, 10);
	        assertNotNull(pagedList);
	        List lista = pagedList.getItemsRangeList();
	        for (int i=0; i<lista.size(); i++){
	        	NotificacionVO notificacionVO = (NotificacionVO)lista.get(i);
				System.out.println("Descripcion: "+notificacionVO.getDsProceso()+"  Codigo: " +notificacionVO.getCdProceso());
	        }
	        System.out.println("sali");           
					} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	*/

	
    public void testInsertarNotificaciones() throws Exception {
        try {
        	NotificacionVO notificacionVO = new NotificacionVO();
        	notificacionVO.setCdNotificacion("");
        	notificacionVO.setDsNotificacion("sasasas"); 
        	notificacionVO.setDsMensaje("sasasasasdddddd");
        	notificacionVO.setCdFormatoOrden("1");
        	notificacionVO.setCdMetEnv("1");
        	
        	
        	BackBoneResultVO backBoneResultVO = new BackBoneResultVO();
        	
        	backBoneResultVO=notificacionesManager.guardarNotificaciones(notificacionVO);
        	
        	System.out.println("cdMatriz = " + backBoneResultVO.getOutParam());
        	
      	  /*
    		//todo setear los parametros de la agregacion
        	notificacionesManager.guardarNotificaciones(notificacionVO);
        	PagedList pagedList =  (PagedList)notificacionesManager.buscarNotificaciones("", "", "", 0, 10);
            assertNotNull(pagedList);
            List lista = pagedList.getItemsRangeList();
            for (int i=0; i<lista.size(); i++){
            	NotificacionVO notificacionVOut = (NotificacionVO)lista.get(i);
    			System.out.println("Descripcion: "+notificacionVOut.getDsNotificacion()+"  Formato: " +notificacionVOut.getDsFormatoOrden()+" Met Env: "+notificacionVOut.getDsMetEnv()+ " ");
            }
*/
        	//notificacionVO = notificacionesManager.getNotificaciones("3");

			//assertNotNull(notificacionVO);
	        //assertEquals("El valor de Codigo debe ser ",notificacionVO.getCdNotificacion(), "3" );
	        //assertEquals("El valor de Nombre debe ser ",notificacionVO.getDsNotificacion(),"renuncia" );
	        //assertEquals("El valor de Codigo debe ser ",notificacionVO.getDsMensaje(),"le remito mi renuncia" );
	        //assertEquals("El valor de Nombre debe ser ",notificacionVO.getDsMensaje(),"1" );
	        //assertEquals("El valor de Nombre debe ser ",notificacionVO.getCdMetEnv(),"2" );

	        System.out.println("sali");
          
          logger.error("Todo va bien en Insertar formatosDocumentos");
        } catch (Exception e) {
            logger.error("Exception Insertar formatosDocumentos");
            e.printStackTrace();
            throw e;
        }
    }


	/*
    public void testInsertarNotificacionesProcesos() throws Exception {
        try {
        	NotificacionVO notificacionVO = new NotificacionVO();
        	notificacionVO.setCdNotificacion("3");
        	notificacionVO.setCdProceso("2"); 

      	  
    		//todo setear los parametros de la agregacion
        	notificacionesManager.guardarNotificacionesProc(notificacionVO);
        	PagedList pagedList =  (PagedList)notificacionesManager.getNotificacionesProceso("3", 0, 10);
        	
	        assertNotNull(pagedList);
	        List lista = pagedList.getItemsRangeList();
	        for (int i=0; i<lista.size(); i++){
	        	NotificacionVO notificacionVOut = (NotificacionVO)lista.get(i);
				System.out.println("Codigo: "+notificacionVOut.getCdProceso()+"  Descripcion: " +notificacionVOut.getDsProceso());
	        }
	        System.out.println("sali"); 
          
          logger.error("Todo va bien en Insertar formatosDocumentos");
        } catch (Exception e) {
            logger.error("Exception Insertar formatosDocumentos");
            e.printStackTrace();
            throw e;
        }
    }
*/
}
