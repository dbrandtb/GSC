package mx.com.aon.catbo.web.tests;

import java.util.ArrayList;



import java.util.List;


import mx.com.aon.portal.model.Tabla_EquivalenciaVO;
import mx.com.aon.portal.service.AdministracionEquivalenciaManager;
import mx.com.aon.portal.service.PagedList;

import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.model.ListaEmisionCasoVO;
import mx.com.aon.catbo.service.AdministracionCasosManager;

import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;

public class AdministracionEquivalenciaTest extends AbstractTestCases {
	protected AdministracionEquivalenciaManager administracionEquivalenciaManager;

	
	/*public void testGuaradEquivalencia() throws Exception {
        try {
      	        	
        	 
        	Tabla_EquivalenciaVO res= administracionEquivalenciaManager("MEX", "AA", "", "", 1, "kjk", "vfv", "fv", "fv", "f", "fv", "fv", "fv", "fv");
            logger.error("Todo va bien en agregar numeracion casos");
            
        } catch (Exception e) {
            logger.error("Exception en Agregar casos");
            e.printStackTrace();
            throw e;
        }
    }

	private Tabla_EquivalenciaVO administracionEquivalenciaManager(String string,
		String string2, String string3, String string4, int i, String string5,
		String string6, String string7, String string8, String string9,
		String string10, String string11, String string12, String string13) {
	// TODO Auto-generated method stub
	return null;
}
*/ 
	
	/*public void testobtenerReporteUno() throws Exception {
		try {
			
			PagedList pagedList =  (PagedList)administracionEquivalenciaManager.obtenerReporte(0, 10);
	        assertNotNull(pagedList);
	        List lista = pagedList.getItemsRangeList();
	       // for (int i=0; i<lista.size(); i++){
	        	//CasoVO casoVO = (CasoVO)lista.get(i);
				//System.out.println("Descripcion: "+Tabla_EquivalenciaVO.getDesModulo());
	        
		
	        System.out.println("sali");           
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	*/
	
	
	/*public void testobtenerReporteDos() throws Exception {
		try {
			
			PagedList pagedList =  (PagedList)administracionEquivalenciaManager.obtieneReporte(0, 10);
	        assertNotNull(pagedList);
	        List lista = pagedList.getItemsRangeList();
	       // for (int i=0; i<lista.size(); i++){
	        	//CasoVO casoVO = (CasoVO)lista.get(i);
				//System.out.println("Descripcion: "+Tabla_EquivalenciaVO.getDesModulo());
	        
		
	        System.out.println("sali");           
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}/*
	
	
	public void testobtenerEquivalencia() throws Exception {
		try {
			
			PagedList pagedList =  (PagedList)administracionEquivalenciaManager.obtenerEquivalencia("", "", "", "", "", 0, 10);
	        assertNotNull(pagedList);
	        List lista = pagedList.getItemsRangeList();
	       // for (int i=0; i<lista.size(); i++){
	        	//CasoVO casoVO = (CasoVO)lista.get(i);
				//System.out.println("Descripcion: "+Tabla_EquivalenciaVO.getDesModulo());
	        
		
	        System.out.println("sali");           
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
	
	

	/* public void testBorrarNotificaciones () throws Exception {
	     try {
	         //borro el registro ingresado previamente
	    	 administracionEquivalenciaManager.borrarEquivalencia(pv_country_code_i, pv_nmtabla_i, pv_cdsistema_i, pv_otclave01acw_i, pv_otclave01ext_i);
	    	   System.out.println("sali");

	     }catch (Exception e) {
	         fail("Fallo en el borrar del registro de de Caso");
	         e.printStackTrace();
	         throw e;
	     }

		*/
		
	
 } //fin Test
