package mx.com.aon.catbo.web.tests;

import java.util.ArrayList;
import java.util.List;
import mx.com.aon.catbo.model.OperacionCATVO;
import mx.com.aon.portal.service.PagedList;


public class OperacionesCATTest extends AbstractTestCases {
	protected mx.com.aon.catbo.service.OperacionCATManager operacionCATManager;
	
	/* public void testBuscarNotificaciones() throws Exception {
			try {
				
				PagedList pagedList =  (PagedList)operacionCATManager.buscarGuiones("", "", "", "", "", "", 1, 3);
	            assertNotNull(pagedList);
	            List lista = pagedList.getItemsRangeList();
	            for (int i=0; i<lista.size(); i++){
	            	OperacionCATVO operacionCATVO = (OperacionCATVO)lista.get(i);
	    			//System.out.println("Descripcion: "+notificacionVO.getDsNotificacion()+"  Formato: " +notificacionVO.getDsFormatoOrden()+" Met Env: "+notificacionVO.getDsMetEnv());
	            }
	            System.out.println("sali");           
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	 
	 
		public void testGetModel()throws Exception{
			try{
				this.operacionCATManager.getModel("", "", "", "", "", "");
				}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		}
		*/
	/*
	public void testBuscarGuionesDisp() throws Exception {
		try {
			
			List lista =  operacionCATManager.buscarGuionesDisp();
			OperacionCATVO operacionCATVO = (OperacionCATVO)lista.get(0);
	        System.out.println("DATOS " + operacionCATVO.getDsUniEco());           
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	*/
	
	public void testBuscarGuionesDisp() throws Exception {
		try {
			
			List lista =  operacionCATManager.buscarDialogosGuion("1");
			OperacionCATVO operacionCATVO = (OperacionCATVO)lista.get(0);
	        System.out.println("DATOS " + operacionCATVO.getDsDialogo());           
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
}
