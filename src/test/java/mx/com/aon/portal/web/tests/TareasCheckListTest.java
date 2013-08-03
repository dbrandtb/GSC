package mx.com.aon.portal.web.tests;

import java.util.List;

import mx.com.aon.portal.service.EstructuraManagerTareasChecklist;
import mx.com.aon.portal.service.ManagerCuentaChecklist;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.ManagerCuentaClienteConfiguraImpl;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.model.ObtienetareaVO;
import mx.com.aon.portal.model.TareaChecklistVO;

public class TareasCheckListTest extends AbstractTestCases {

	
	
	protected EstructuraManagerTareasChecklist estructuraManagerTareasChecklist;
	protected  ManagerCuentaChecklist  managerCuentaChecklist;
	//protected ManagerCuentaClienteConfiguraImpl estructuraManagerCuentaChecklist;
	
    
   /* public void testTareasChecklist() throws Exception {
    	try {
    		
    	// ya estan probado estos procedimientos 	
			//PagedList lista = this.estructuraManagerTareasChecklist.getTareas("C", null, null, 0, 10);
    	//	this.estructuraManagerTareasChecklist.getSecciones();
      	//	this.estructuraManagerTareasChecklist.getEstados();
            //ObtienetareaVO obtienetareaVO =   this.estructuraManagerTareasChecklist.getTarea("1", "1");
    		
    		// faltan provar estos procedimientos EstructuraManagerTareasChecklist   		
    		//this.estructuraManagerTareasChecklist.validaBorraTarea("1", "2");
    		//this.estructuraManagerTareasChecklist.borrarTarea("1", "2");
    		//this.estructuraManagerTareasChecklist.guardarTarea("1","Configura Catalogos", null, "0", "carlos@yahoo.com.ar", "0", "Es una Prueba");

            //boolean  retorno = this.estructuraManagerTareasChecklist.existeTarea("1", "2");


            // faltan provar estos procedimientos ManagerCuentaChecklist
    		this.managerCuentaChecklist.getConfiguraciones("","",0,10);
    		//this.managerCuentaChecklist.borraConfiguracion(null);
            System.out.println("sali");
        } catch (Exception e) {
        	System.out.println("FALLO " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
    }*/
	
	
	public void testBuscarCarritoComprasManager() throws Exception {
    	try {
    		PagedList salida = (PagedList) this.managerCuentaChecklist.getConfiguraciones("", "", 0, 10);

			List lista = salida.getItemsRangeList(); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	
	
/*	
	public void testListaTareasChecklist() throws Exception{
		try {
    		PagedList pagedlist = new PagedList();
			pagedlist = estructuraManagerTareasChecklist.obtenerListaTareasChecklist("1", "1", 0, 10);
    		List lista = pagedlist.getItemsRangeList();
    		System.out.println("Valor de size: " + lista.size());
    		for (int i=0; i<lista.size(); i++) {
    			TareaChecklistVO tareaVO = (TareaChecklistVO)lista.get(i);
    			System.out.println("Tarea Padre: " + tareaVO.getCdTareapadre() + "Descripción: " + tareaVO.getDsTareapadre());
    		}
			logger.info("Se ejecuto la busqueda");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	*/
    /*
	public void testValidaBorrarTarea() throws Exception{
		try {
    		//WrapperResultados res = estructuraManagerTareasChecklist.validaBorraTarea("1", "92");
			WrapperResultados res = estructuraManagerTareasChecklist.validaBorraTarea("3", "200");
    		
    		System.out.println("Valor de retorno - Resultado: " + res.getResultado());
    		//logger.info("Se ejecuto la busqueda");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	*/
}
