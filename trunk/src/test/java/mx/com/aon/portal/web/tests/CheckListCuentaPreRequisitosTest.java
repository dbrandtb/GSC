package mx.com.aon.portal.web.tests;

import java.util.List;

import mx.com.aon.portal.model.CheckListCtaTareaVO;
import mx.com.aon.portal.model.CheckListCuentaPreRequisitosVO;
import mx.com.aon.portal.model.CheckListXCuentaSeccion;
import mx.com.aon.portal.service.CheckListCuentaPreRequisitosManager;
import mx.com.aon.portal.service.PagedList;

public class CheckListCuentaPreRequisitosTest extends AbstractTestCases {
	protected CheckListCuentaPreRequisitosManager checkListCuentaPreRequisitosManager;
	
	
/*	public void testGuardarTareaSeccionCta () throws Exception{
		CheckListCuentaPreRequisitosVO checkListCuentaPreRequisitosVO = new CheckListCuentaPreRequisitosVO();
		try {
			checkListCuentaPreRequisitosVO.setCdConfig("");
			checkListCuentaPreRequisitosVO.setDsConfig("CONFIG 1");
			checkListCuentaPreRequisitosVO.setCdLinOpe("1");
			checkListCuentaPreRequisitosVO.setCdPerson("777");
			checkListCuentaPreRequisitosVO.setCdSeccion("1");
			checkListCuentaPreRequisitosVO.setCdTarea("1");
			checkListCuentaPreRequisitosVO.setCdCompletada("1");
			checkListCuentaPreRequisitosVO.setNoRequerida("1");
			//checkListCuentaPreRequisitosManager.guardaPreRequisito(checkListCuentaPreRequisitosVO);
			logger.info("Se ejecutó el guardado");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
	@SuppressWarnings("unchecked")
	public void testObtenerEncabezados () throws Exception {
		try {
			@SuppressWarnings("unused")
			PagedList result = checkListCuentaPreRequisitosManager.obtenerEncabezados("1", "1");
			logger.info("Se ejecutó Obtener Encabezados");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

/*	@SuppressWarnings("unchecked")
	public void testObtenerTareasSeccion () throws Exception{
		try {
			List lista = checkListCuentaPreRequisitosManager.obtenerTareasSeccion("1", "1");
			for (int i=0; i<lista.size(); i++) {
				CheckListCtaTareaVO checkListCtaTareaVO = (CheckListCtaTareaVO)lista.get(i);
				System.out.println("cdTarea: " + checkListCtaTareaVO.getCodigoTarea() + "  1dsTarea: " + checkListCtaTareaVO.getDsTarea() + " Completada: " + checkListCtaTareaVO.getFgNoRequ() + "   Tarea Padre: " + checkListCtaTareaVO.getCdtareaPadre());
			}
			logger.info("Se ejecutó Obtener Tareas Seccion");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testObtenerSeccion () throws Exception {
		try {
			List lista = checkListCuentaPreRequisitosManager.obtenerSecciones("1");
			for (int i=0; i<lista.size(); i++) {
				CheckListXCuentaSeccion checkListXCuentaSeccion = (CheckListXCuentaSeccion)lista.get(i);
				System.out.println("Seccion: " + checkListXCuentaSeccion.getCdSeccion());
			}
			logger.info("Se ejecutó Obtener Sección");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

/*	public void testChequeoConfiguracionTareas () throws Exception{
		try {
			boolean result = checkListCuentaPreRequisitosManager.isConfiguracionTareasCompleta("1");
			logger.info("Se ejecutó la validación");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*//*	public void testObtenerClientesCorpo () throws Exception {
		try {
			checkListCuentaPreRequisitosManager.obtenerClientes();
			logger.info("Se ejecutó Clientes Corpo");
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/
}
