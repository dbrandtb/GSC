package mx.com.aon.portal.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.ReporteDeudaVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.DaoException;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;

import com.wittyconsulting.backbone.exception.BackboneApplicationException;

public class ReporteDeudaDAO  extends AbstractDAO {

    private static Logger logger = Logger.getLogger(ReporteDeudaDAO.class);


   public Object invoke(String storeProcedureName, Object parameters) throws DaoException {
          try {
        	         	  
        	  Map params = (Map) parameters;
        	  //acá debería obtener todos los param así luego llamo al web service
        	  String nmPoliza = (String)params.get("nmPoliza");
        	  String cdAsegurado = (String)params.get("cdAsegurado");
        	  String cdAseguradora = (String)params.get("cdAseguradora");
        	  String fechaDesde = (String)params.get("fechaDesde");
        	  String operacion = (String)params.get("operacion");
        	  String clausulaWhere = "";
        	  
        	  if ((nmPoliza != null)&& (!nmPoliza.equals("")))
        		  clausulaWhere+= "IdPoliza=1114818";
        	  if ((cdAsegurado != null) && (!cdAsegurado.equals("")))
        		  clausulaWhere+= " AND IdCliente=118202";
        	  if ((cdAseguradora != null) && (!cdAseguradora.equals("")))
        		  clausulaWhere+= " AND IdAseguradora=82";
        	  if ((fechaDesde != null)&&((!fechaDesde.equals(""))))
        		  clausulaWhere+=" AND fecvencimiento >= to_date('" + fechaDesde +"'"+ ",'dd/mm/yyyy')-5 AND fecvencimiento <= to_date('" + fechaDesde +"'"+ ",'dd/mm/yyyy')+5";
        		  
        	  //clausulaWhere+= "spc.FecVencimiento >= to_date('" + fechaDesde +"'"+ ",'yyyymmdd')";
/* TODO:habilitar codigo
        	  reportedeudaproject.proxy.InbrokerCatWebSoapClient myPort = new reportedeudaproject.proxy.InbrokerCatWebSoapClient();
        	  logger.debug("llamando a: " +  myPort.getEndpoint() );
        	  logger.debug("clausulaWhere: " + clausulaWhere );
        	  //invocación del método del WS
              // Nos quedamos con el xml resultante.
        	  String xmlMessage = myPort.ejecutarConsulta("ACW_REPORTEDEUDA",clausulaWhere);
*/
              WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
              WrapperResultados wrapperResultados = mapper.build();
              List result=null;
              if (operacion == "buscar")
            	  //result = findReportesDeuda(xmlMessage);//TODO:habilitar codigo              
              if (operacion == "exportar")
            	  //result = findReportesDeudaExport(xmlMessage);//TODO:habilitar codigo  
              wrapperResultados.setItemList(result);
              return wrapperResultados;

          } catch (Exception ex) {
              throw new DaoException("Error inesperado en el acceso a los datos",ex);
          }
    }

    public List findReportesDeuda(String xmlMessage) throws BackboneApplicationException {
          try {
                ReporteDeudaParser reporteDeudaParser = new ReporteDeudaParser();
                return reporteDeudaParser.parseResult(xmlMessage);
          } catch (Exception ex) {
              throw new BackboneApplicationException("Error inesperado en el acceso a los datos",ex);
          }
    }
    
    public List findReportesDeudaExport(String xmlMessage) throws BackboneApplicationException {
        try {
        	ReporteDeudaExportParser reporteDeudaExportParser = new ReporteDeudaExportParser();
              return reporteDeudaExportParser.parseResult(xmlMessage);
        } catch (Exception ex) {
            throw new BackboneApplicationException("Error inesperado en el acceso a los datos",ex);
        }
  }


    protected class ReporteDeudaParser {
        private List reportesDeudas = new ArrayList();

        public List parseResult(String xmlMessage) throws Exception{

                Digester digester = new Digester();
                StringReader stringReader = new StringReader(xmlMessage);

                digester.setValidating(false);
                digester.push(this);
                //REG_ACTUALIZA_ORIGEN 
                digester.addObjectCreate("NewDataSet/MASTER", "classname", ReporteDeudaVO.class);
              //éstas van a ser las reglas para la invocación a la consulta Sg_SaldoPorCuotaConsulta
                digester.addCallMethod("NewDataSet/MASTER/IDCLIENTE", "setIdCliente", 0);
                digester.addCallMethod("NewDataSet/MASTER/CLIENTE", "setCliente", 0);
                digester.addCallMethod("NewDataSet/MASTER/IDASEGURADO", "setIdAsegurado", 0);
                digester.addCallMethod("NewDataSet/MASTER/ASEGURADO", "setAsegurado", 0);
                digester.addCallMethod("NewDataSet/MASTER/IDASEGURADORA", "setIdAseguradora", 0);
                digester.addCallMethod("NewDataSet/MASTER/CODASEGURADORA", "setCodAseguradora", 0);
                digester.addCallMethod("NewDataSet/MASTER/ASEGURADORA", "setAseguradora", 0);
                digester.addCallMethod("NewDataSet/MASTER/IDPOLIZA", "setIdPoliza", 0);
                digester.addCallMethod("NewDataSet/MASTER/IDOPERACION", "setIdOperacion", 0);
                digester.addCallMethod("NewDataSet/MASTER/CODPOLIZA", "setCodPoliza", 0);  // se pide en el FRD de la pantalla
                digester.addCallMethod("NewDataSet/MASTER/CODENDOSO", "setCodEndoso", 0);
                digester.addCallMethod("NewDataSet/MASTER/CODCERTIFICADO", "setCodCertificado", 0);
                digester.addCallMethod("NewDataSet/MASTER/FECPAGO", "setFecPago", 0);
                digester.addCallMethod("NewDataSet/MASTER/NROCUENTADEBITO", "setNroCuentaDebito", 0);
                digester.addCallMethod("NewDataSet/MASTER/CODRAMO", "setCodRamo", 0);             
                digester.addCallMethod("NewDataSet/MASTER/DESCRAMO", "setDescRamo", 0);
                digester.addCallMethod("NewDataSet/MASTER/NROCUOTA", "setNroCuota", 0);
                digester.addCallMethod("NewDataSet/MASTER/FECVENCIMIENTO", "setFecVencimiento", 0); // se pide fecPago en el FRD de la pantalla 
                digester.addCallMethod("NewDataSet/MASTER/DESCMONEDA", "setDescMoneda", 0);                
                digester.addCallMethod("NewDataSet/MASTER/IMPCUOTA", "setImpCouta", 0); 
                digester.addCallMethod("NewDataSet/MASTER/IMPSALDOCUOTA", "setImpSaldoCuota", 0); // se pide en el FRD de la pantalla
                digester.addCallMethod("NewDataSet/MASTER/FECFINALCUOTA", "setFecFinalCuota", 0);
                digester.addCallMethod("NewDataSet/MASTER/NROCUOTAS", "setNroCuotas", 0);
                //esta son reglas para probar la consulta "Sg_Operacion","IdOperacion = 1"
                /*digester.addCallMethod("NewDataSet/MASTER/IDOPERACION", "setIdOperacion", 0);
                digester.addCallMethod("NewDataSet/MASTER/NROTIPOBIEN", "setNumeroTipoBien", 0);
                digester.addCallMethod("NewDataSet/MASTER/CODPOLIZA", "setCodigoPoliza", 0);
                digester.addCallMethod("NewDataSet/MASTER/IDCLIENTE", "setICliente", 0);*/
                
                digester.addSetNext("NewDataSet/MASTER", "addReporteDeudaVO");
                
                digester.parse(stringReader);

                return reportesDeudas;

        }
      public void addReporteDeudaVO(ReporteDeudaVO reporteDeudaVO) {
          reportesDeudas.add(reporteDeudaVO);
      }

    }
    
    protected class ReporteDeudaExportParser {
        private List reportesDeudas = new ArrayList();

        public List parseResult(String xmlMessage) throws Exception{

                Digester digester = new Digester();
                StringReader stringReader = new StringReader(xmlMessage);

                digester.setValidating(false);
                digester.push(this);
                //REG_ACTUALIZA_ORIGEN              
                digester.addObjectCreate("NewDataSet/MASTER", "classname", ArrayList.class);
              //éstas van a ser las reglas para la invocación a la consulta Sg_SaldoPorCuotaConsulta
                
                //digester.addCallMethod("NewDataSet/MASTER/IDCLIENTE", "add", 0);
                //digester.addCallMethod("NewDataSet/MASTER/CLIENTE", "add", 0);
                //digester.addCallMethod("NewDataSet/MASTER/IDASEGURADO", "add", 0);
                //digester.addCallMethod("NewDataSet/MASTER/ASEGURADO", "add", 0);
                //digester.addCallMethod("NewDataSet/MASTER/IDASEGURADORA", "add", 0);
                //digester.addCallMethod("NewDataSet/MASTER/CODASEGURADORA", "add", 0);
                //digester.addCallMethod("NewDataSet/MASTER/ASEGURADORA", "add", 0);
                digester.addCallMethod("NewDataSet/MASTER/IDPOLIZA", "add", 0);  // se pide en el FRD de la pantalla
                //digester.addCallMethod("NewDataSet/MASTER/IDOPERACION", "add", 0);
                //digester.addCallMethod("NewDataSet/MASTER/CODPOLIZA", "add", 0);  
                digester.addCallMethod("NewDataSet/MASTER/CODENDOSO", "add", 0);  // se pide fecPago en el FRD de la pantalla
                digester.addCallMethod("NewDataSet/MASTER/CODCERTIFICADO", "add", 0); // se pide fecPago en el FRD de la pantalla
                digester.addCallMethod("NewDataSet/MASTER/FECPAGO", "add", 0); // se pide fecPago en el FRD de la pantalla
                digester.addCallMethod("NewDataSet/MASTER/NROCUENTADEBITO", "add", 0); // se pide fecPago en el FRD de la pantalla
                //digester.addCallMethod("NewDataSet/MASTER/CODRAMO", "add", 0);             
                //digester.addCallMethod("NewDataSet/MASTER/DESCRAMO", "add", 0);
                digester.addCallMethod("NewDataSet/MASTER/NROCUOTA", "add", 0); // se pide fecPago en el FRD de la pantalla
                //digester.addCallMethod("NewDataSet/MASTER/FECVENCIMIENTO", "add", 0);  
                //digester.addCallMethod("NewDataSet/MASTER/DESCMONEDA", "add", 0);                
                //digester.addCallMethod("NewDataSet/MASTER/IMPCUOTA", "add", 0); 
                digester.addCallMethod("NewDataSet/MASTER/IMPSALDOCUOTA", "add", 0); // se pide en el FRD de la pantalla
                //digester.addCallMethod("NewDataSet/MASTER/FECFINALCUOTA", "add", 0);
                //digester.addCallMethod("NewDataSet/MASTER/NROCUOTAS", "add", 0);
                digester.addSetNext("NewDataSet/MASTER", "addArrayList");
                
                digester.parse(stringReader);

                return reportesDeudas;

        }
      public void addArrayList(ArrayList arrayList) {
          reportesDeudas.add(arrayList);
      }

    }



}
