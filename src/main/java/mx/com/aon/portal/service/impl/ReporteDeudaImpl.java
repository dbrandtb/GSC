package mx.com.aon.portal.service.impl;


import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.ReporteDeudaManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * Clase que implementa EstructuraManager para dar respuestas a solicitudes del action.
 *
 */
public class ReporteDeudaImpl extends AbstractManagerJdbcTemplateInvoke implements ReporteDeudaManager {


	public PagedList buscarReportesDeuda(int start, int limit, String cdAsegurado,String cdAseguradora, String nmPoliza, String fechaDesde )throws ApplicationException
	{
		
		HashMap map = new HashMap();
		//map.put("xmlMessage", descripcion);
		map.put("cdAsegurado", cdAsegurado);
		map.put("cdAseguradora", cdAseguradora);
		map.put("nmPoliza", nmPoliza);
		map.put("fechaDesde", fechaDesde);
		map.put("operacion", "buscar");
		String endpointName = "OBTIENE_REPORTE_DEUDA";
		return pagedBackBoneInvoke(map, endpointName, start, limit);
	}
	
	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String cdAsegurado,String cdAseguradora, String nmPoliza, String fechaDesde ) throws ApplicationException {
    	// Se crea el objeto de respuesta
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		//map.put("xmlMessage", descripcion);
		map.put("cdAsegurado", cdAsegurado);
		map.put("cdAseguradora", cdAseguradora);
		map.put("nmPoliza", nmPoliza);
		map.put("fechaDesde", fechaDesde);
		map.put("operacion", "exportar");
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "EXPORT_REPORTE_DEUDA");

		model.setInformation(lista);
		// Se agregan los nombre de las columnas del modelo de datos
		//model.setColumnName(new String[]{"Póliza","Endoso","Certificado","Fecha Pago","Referencia Bancaria","Recibo","Monto","Id Aseguradora","Cod Aseguradora","Aseguradora","Id Cliente","Cliente", "Id Operacion", "Cod Poliza", "Cod Ramo", "Ramo", "Nro Cuota", "Fecha Vencimiento","Moneda","Imp Saldo Cuota", "Fecha Final Cuota", "Nro Cuotas"});
		ArrayList array = (ArrayList)lista.get(0);
		if (array.size()==3)
			model.setColumnName(new String[]{"Poliza","Recibo","Monto"});
		else	
		model.setColumnName(new String[]{"Poliza","Endoso","Certificado","Fecha Pago","Referencia Bancaria","Recibo","Monto"});
		
		//Así se pide en pantalla
		//model.setColumnName(new String[]{"Póliza","Endoso","Certificado","Fecha Pago","Referencia Bancaria","Recibo"});
		
		return model;
    }


}
