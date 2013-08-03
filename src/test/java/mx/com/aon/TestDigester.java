package mx.com.aon;

import org.apache.log4j.Logger;
import mx.com.aon.portal.dao.ReporteDeudaDAO;
import mx.com.aon.portal.service.ReporteDeudaManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.web.tests.AbstractTestCases;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Mar 24, 2009
 * Time: 9:33:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestDigester extends AbstractTestCases {

    protected ReporteDeudaManager reporteDeudaManager;

    private static Logger logger = Logger.getLogger(TestDigester.class);

    public void testParseResponse() {
        try {

          ReporteDeudaDAO reporteDeudaDAO = new ReporteDeudaDAO();
          List resultado = reporteDeudaDAO.findReportesDeuda(getMessage());
            System.out.println("salir");




        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }

    public void testFindReporte() {
        try {
          Map params = new HashMap();
          params.put("xmlMessage",getMessage());
          PagedList resultado = reporteDeudaManager.buscarReportesDeuda(0, 10, "", "", "", "");
          List listResult = resultado.getItemsRangeList();
            System.out.println("sali");



        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }


    private String getMessage() {


        String message =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<NewDataSet xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"C:\\DOCUME~1\\Usuario\\Escritorio\\Pantalla\\REPORT~1\\ResponseSchema.xsd\">\n" +
                "\t<xs:schema id=\"NewDataSet\" xmlns=\"\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:msdata=\"urn:schemas-microsoft-com:xml-msdata\">\n" +
                "\t\t<xs:element name=\"NewDataSet\" msdata:IsDataSet=\"true\" msdata:MainDataTable=\"MASTER\" msdata:UseCurrentLocale=\"true\">\n" +
                "\t\t\t<xs:complexType>\n" +
                "\t\t\t\t<xs:choice minOccurs=\"0\" maxOccurs=\"unbounded\">\n" +
                "\t\t\t\t\t<xs:element name=\"MASTER\">\n" +
                "\t\t\t\t\t\t<xs:complexType>\n" +
                "\t\t\t\t\t\t\t<xs:sequence>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"Link0\" msdata:AutoIncrement=\"true\" type=\"xs:int\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDOPERACION\" msdata:Caption=\"Id. Operaci—n\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODDOCRESPALDOENDOSO\" msdata:Caption=\"Doc. Respaldo Endoso\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODPRODUCTO\" msdata:Caption=\"C—d. Producto\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NROTIPOBIEN\" msdata:Caption=\"Tipo de Bien\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODTIPOORDEN\" msdata:Caption=\"Tipo Operaci—n\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODTIPOENDOSO\" msdata:Caption=\"Tipo Endoso\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODOPERACIONESTADO\" msdata:Caption=\"Estado Operaci—n\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSUBPRODUCTO\" msdata:Caption=\"C—d. SubProducto\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NROORDENEMISION\" msdata:Caption=\"Nro. Orden Emisi—n\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODPOLIZA\" msdata:Caption=\"P—liza\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDSECUENCIA\" msdata:Caption=\"Id. Secuencia\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODENDOSO\" msdata:Caption=\"Endoso\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODCERTIFICADO\" msdata:Caption=\"Certificado\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSOLICITUD\" msdata:Caption=\"Solicitud\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNULTIMOESTADO\" msdata:Caption=\"UE\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODTRANSACCION\" msdata:Caption=\"Transaccion\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODPAIS\" msdata:Caption=\"C—d. Pais\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDCLIENTE\" msdata:Caption=\"Id. Cliente\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDASEGURADORA\" msdata:Caption=\"Id. Aseguradora\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDPRODUCTOROPERACION\" msdata:Caption=\"Id. Productor Oper.\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODPUNTOVENTA\" msdata:Caption=\"C—d. Punto Venta\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSECCION\" msdata:Caption=\"Cod. Secci—n\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODMONEDA\" msdata:Caption=\"C—d. Moneda\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDASEGURADO\" msdata:Caption=\"Id. Asegurado\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDDOMICILIOPOLIZA\" msdata:Caption=\"Id. Domicilio P—liza\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODVENDEDORGRUPO\" msdata:Caption=\"C—d. Grupo Vendedor\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDVENDEDOR\" msdata:Caption=\"Id. Vendedor\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDTOMADOR\" msdata:Caption=\"Id. Tomador\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDCONTACTOCLIENTE\" msdata:Caption=\"Id. Contacto Cliente\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDCONTACTOASEGURADORA\" msdata:Caption=\"Id. Contacto Cia. 1\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDOPERACIONRENOVADA\" msdata:Caption=\"Renueva a\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODPROVINCIA\" msdata:Caption=\"C—d. Provincia\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECVIGENCIAINICIALPOLIZA\" msdata:Caption=\"Fec. Vig Ini P—liza\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECVIGENCIAFINALPOLIZA\" msdata:Caption=\"Fec. Vig Fin P—liza\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECVIGENCIAINICIALOPERACION\" msdata:Caption=\"Fec. Vig Ini Oper.\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECVIGENCIAFINALOPERACION\" msdata:Caption=\"Fec. Vig Fin Oper.\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECEMISION\" msdata:Caption=\"Fec. Emisi—n\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECFACTURACION\" msdata:Caption=\"Fec. Facturaci—n\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECEMISIONPOLIZAENDOSO\" msdata:Caption=\"Fec. Emisi—n P—l/End\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNPOLIZAMASIVA\" msdata:Caption=\"P—liza Masiva\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNPOLIZAPILOTO\" msdata:Caption=\"P—liza Piloto\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNCOASEGURO\" msdata:Caption=\"Coaseguro\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDCOASEGURO\" msdata:Caption=\"Id. Coaseguro\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNADHERIDODEBITOAUTOMATICO\" msdata:Caption=\"Adherido DŽbito Aut.\" type=\"xs:string\" default=\"S\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNSUSPENDEDEBITO\" msdata:Caption=\"DŽbito Suspend.\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNFACTURADA\" msdata:Caption=\"Fc\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODFACTURAASEGURADORA\" msdata:Caption=\"Factura Aseguradora\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"TXTFACTURACION\" msdata:Caption=\"Texto Facturaci—n\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODMOTIVOENDOSO\" msdata:Caption=\"C—d. Motivo Endoso\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DESCMOTIVOENDOSO\" msdata:Caption=\"Motivo Endoso\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODCATEGORIADEUDA\" msdata:Caption=\"Cat. Deuda\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IMPSUMAASEGURADAPOLIZA\" msdata:Caption=\"Suma Aseg. P—liza\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IMPSUMAASEGURADAOPERACION\" msdata:Caption=\"Suma Aseg. Operaci—n\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPPRIMAAUTOMATICA\" msdata:Caption=\"Imp.Prima Autom‡tica\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPPRIMAMANUAL\" msdata:Caption=\"Imp. Prima Ajuste\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPPRIMA\" msdata:Caption=\"Imp. Prima\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_PORRECARGOADMINISTRATIVO\" msdata:Caption=\"% Recargo Administ.\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPRECARGOADMINISTRATIVO\" msdata:Caption=\"Imp. Rec. Administ.\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPDERECHOEMISION\" msdata:Caption=\"Imp. Der. de Emisi—n\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_POROTROSIMPUESTOS\" msdata:Caption=\"% Otros Impuestos\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPOTROSIMPUESTOS\" msdata:Caption=\"Imp. Otros Impuestos\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSEGUIMIENTO\" msdata:Caption=\"C—d. Seguimiento\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_PORRECARGOFINANCIERO\" msdata:Caption=\"% Recargo Financiero\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPRECARGOFINANCIERO\" msdata:Caption=\"Imp. Rec. Financiero\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_PORINGRESOSBRUTOS\" msdata:Caption=\"% Ingresos Brutos\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPINGRESOSBRUTOS\" msdata:Caption=\"Imp. Ingresos Brutos\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_PORSELLADOS\" msdata:Caption=\"% Sellados\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPSELLADOS\" msdata:Caption=\"Imp. Sellados\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPPREMIO\" msdata:Caption=\"Imp. Premio Bruto\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_PORBONIFICACIONES\" msdata:Caption=\"% Bonificaciones\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPBONIFICACIONES\" msdata:Caption=\"Imp. Bonificaciones\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPPREMIONETO\" msdata:Caption=\"Imp. Premio S/IVA\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_PORIVA\" msdata:Caption=\"% IVA\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPIVA\" msdata:Caption=\"Imp. IVA\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPPREMIOIVA\" msdata:Caption=\"Imp. Premio C/IVA\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPPREMIOSCORING\" msdata:Caption=\"Imp. Premio Scoring\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPPRIMASCORING\" msdata:Caption=\"Imp. Prima Scoring\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_PORPREMIOSCORING\" msdata:Caption=\"% Premio Scoring\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_PORPRIMASCORING\" msdata:Caption=\"% Prima Scoring\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPPREMIOSCORINGFIJO\" msdata:Caption=\"Premio Scoring Fijo\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPPRIMAGRAVADA\" msdata:Caption=\"Imp. Prima Gravada\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPPRIMANOGRAVADA\" msdata:Caption=\"Imp Prima No Gravada\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_IMPPRIMASCORINGFIJO\" msdata:Caption=\"Prima Scoring Fijo\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"TIPOCAMBIOOPERACION\" msdata:Caption=\"T.C. (ML)\" type=\"xs:decimal\" default=\"1\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODDEBITOFORMA\" msdata:Caption=\"C—d. Forma Debito\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DESCORDENCUENTA\" msdata:Caption=\"Desc. Cuenta/Tarjeta\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECVENCIMIENTOCUENTA\" msdata:Caption=\"Fec. Vto. Tarjeta\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODFISCALCUENTA\" msdata:Caption=\"Doc. Titular Cuenta\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NROCLAVEBANCARIAUNIFORME\" msdata:Caption=\"C.B.U.\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSEGURIDADTARJETA\" msdata:Caption=\"C—d. Seg. Tarjeta\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODREFERIDO\" msdata:Caption=\"C—d. Referido\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNCREDITO\" msdata:Caption=\"Con PrŽstamo\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"OPERACIONBANCARIARELACIONADA\" msdata:Caption=\"Nro. PrŽstamo\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IMPCREDITO\" msdata:Caption=\"Imp. PrŽstamo\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECVTOCREDITO\" msdata:Caption=\"Fec. Vto. PrŽstamo\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CANTCUOTASCREDITO\" msdata:Caption=\"Cuotas PrŽstamo\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"TIPOMONEDACUENTA\" msdata:Caption=\"Moneda Cuenta\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODTIPORENOVACION\" msdata:Caption=\"Tipo Renovaci—n\" type=\"xs:string\" default=\"A\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNVUELCO\" msdata:Caption=\"Vuelco\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNDEBITOAUTOMATICO\" msdata:Caption=\"DŽbito Autom‡tico\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNIVAPRORRATEADO\" msdata:Caption=\"Iva Prorrateado\" type=\"xs:string\" default=\"S\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNCOMISIONAUTORIZADA\" msdata:Caption=\"Comisi—n Autorizada\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNDOCUMENTACIONRECIBIDA\" msdata:Caption=\"Docum. Recibida\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IMPSUMAASEGURADAPOLIZAANT\" msdata:Caption=\"Suma Aseg. Anterior\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSEGMENTACION1\" msdata:Caption=\"C—d. Segmentaci—n 1\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSEGMENTACION2\" msdata:Caption=\"C—d. Segmentaci—n 2\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSEGMENTACION3\" msdata:Caption=\"C—d. Segmentaci—n 3\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSEGMENTACION4\" msdata:Caption=\"C—d. Segmentaci—n 4\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODLINEACREDITO\" msdata:Caption=\"L’nea de CrŽdito\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODMONEDAPRESTAMO\" msdata:Caption=\"C—d. Moneda Prestamo\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNMULTIRIESGO\" msdata:Caption=\"Comis. por Cobertura\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNMONOITEM\" msdata:Caption=\"Mono Item\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"TIPOMONEDACREDITO\" msdata:Caption=\"Tipo Moneda Credito\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NROCUOTAS\" msdata:Caption=\"Cant. de Cuotas\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IMPSALDOOPERACION\" msdata:Caption=\"Imp. Saldo Orden\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DESCCLIENTE\" msdata:Caption=\"Nom. Cliente\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDGRUPOECONOMICO\" msdata:Caption=\"Id. Grupo Econ—mico\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODGRUPOEMPRESA\" msdata:Caption=\"Grupo Empresa\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DESCASEGURADORA\" msdata:Caption=\"Nom. Cia. Aseg.\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"ABREVIATURAASEG\" msdata:Caption=\"Abrev. Aseguradora\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DESCSUBPRODUCTO\" msdata:Caption=\"Desc. SubProducto\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNPLANPAGOADELANTADO\" msdata:Caption=\"Plan Pago Adelantado\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DESCMONEDA\" msdata:Caption=\"Moneda\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DESCSECCION\" msdata:Caption=\"Desc. Secci—n\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODGRUPOSECCION\" msdata:Caption=\"Grupo Secci—n\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"ABREVIATURASECCION\" msdata:Caption=\"Abrev. Secci—n\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"TIENESALDO\" msdata:Caption=\"S.\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NROCUENTADEBITO\" msdata:Caption=\"Nro. Cuenta/Tarjeta\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECALTA\" msdata:Caption=\"Fec. Alta\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECMODIFICACION\" msdata:Caption=\"Fec. Modificacion\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"ESTADO\" msdata:Caption=\"Estado\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"USUARIO\" msdata:Caption=\"Usuario\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"GUIDSTAMP\" msdata:Caption=\"Guid Stamp\" type=\"xs:base64Binary\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_ADICIONAL1\" msdata:Caption=\"Imp. Adicional 1\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_ADICIONAL2\" msdata:Caption=\"Imp. Adicional 2\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_ADICIONAL3\" msdata:Caption=\"Imp. Adicional 3\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_ADICIONAL4\" msdata:Caption=\"Imp. Adicional 4\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODREFERIDO1\" msdata:Caption=\"Plataformista\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDPOLIZA\" msdata:Caption=\"Id. P—liza\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDCERTIFICADO\" msdata:Caption=\"Id. Certificado\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSEGMENTACION5\" msdata:Caption=\"C—d. Segmentaci—n 5\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSEGMENTACION6\" msdata:Caption=\"C—d. Segmentaci—n 6\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSEGMENTACION7\" msdata:Caption=\"C—d. Segmentaci—n 7\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSEGMENTACION8\" msdata:Caption=\"C—d. Segmentaci—n 8\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODBANCO\" msdata:Caption=\"C—d. Banco\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSUCURSAL\" msdata:Caption=\"Sucursal Cuenta\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSUCURSALPOLIZA\" msdata:Caption=\"Sucursal P—liza\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NROPROCESO\" msdata:Caption=\"Nro. Proceso\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_ADICIONAL5\" msdata:Caption=\"Imp. Adicional 5\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_ADICIONAL6\" msdata:Caption=\"Imp. Adicional 6\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_ADICIONAL7\" msdata:Caption=\"Imp. Adicional 7\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DP_ADICIONAL8\" msdata:Caption=\"Imp. Adicional 8\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDOPERACIONENDOSADA\" msdata:Caption=\"Id. Oper. Endosada\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NROCONFIDENCIALIDAD\" msdata:Caption=\"Confidencialidad\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NROVISIBILIDAD\" msdata:Caption=\"Visibilidad\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODPERIODICIDADREFACTURACION\" msdata:Caption=\"Period. Ref. Autom.\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNREFACTURACIONAUTOMATICA\" msdata:Caption=\"Refact. Autom‡tica\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDCAMPANIA\" msdata:Caption=\"Id. Campania\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"TIPONUMERACIONPOLIZA\" msdata:Caption=\"Numeraci—n P—lizas\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"TIPONUMERACIONENDOSO\" msdata:Caption=\"Numeraci—n Endosos\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDOPERACIONRENOVANTE\" msdata:Caption=\"Renovada por\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODCLASENEGOCIO\" msdata:Caption=\"C—d. Clase Negocio\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NROFORMACALCULOPRIMA\" msdata:Caption=\"Forma C‡lculo Prima\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODTIPOPOLIZA\" msdata:Caption=\"C—d. Tipo P—liza\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODTIPOIVACLIENTE\" msdata:Caption=\"Tipo IVA Cliente\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNFACTURAPROVISORIA\" msdata:Caption=\"Fp\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNCLIENTEAGENTERETENCION\" msdata:Caption=\"Cliente Agen. Reten.\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNCLIENTEAGENTEPERCEPCION\" msdata:Caption=\"Cliente Agen Percep.\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODTIPOIVACOMPANIA\" msdata:Caption=\"Tipo IVA Aseguradora\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNCOMPANIAAGENTERETENCION\" msdata:Caption=\"C’a. Agen. Retenc.\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNCOMPANIAAGENTEPERCEPCION\" msdata:Caption=\"C’a. Agen. Percep.\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NROFISCALASEGURADORA\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NROTIPODOMICILIOSECCION\" msdata:Caption=\"Tipo Domicilio Secc.\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNSINIESTRADA\" msdata:Caption=\"Siniestrada\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDOPERACIONCERTRENOVANTE\" msdata:Caption=\"Cert. Renovado por\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODTIPOPERDIDANEGOCIO\" msdata:Caption=\"Tipo Perdida Negocio\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODTIPOINGRESO\" msdata:Caption=\"Tipo de Ingreso\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODFORMAPAGO\" msdata:Caption=\"Forma de Pago\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"PORDESCUENTOCONTADO\" msdata:Caption=\"% Desc. Contado\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"PORCONTADO\" msdata:Caption=\"% Contado\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNAUTOSEGURO\" msdata:Caption=\"Autoseguro\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDPOLIZARELACIONADA\" msdata:Caption=\"P—liza Relacionada\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"PORSOBRECOMISION\" msdata:Caption=\"% Sobre Comisi—n\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"PERIODOPRODUCCION\" msdata:Caption=\"Periodo Producci—n\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNNOPRORRATEACONCEPTOS\" msdata:Caption=\"No Prorratea Concep.\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDPARAMETROPLANPAGO\" msdata:Caption=\"Par‡metro Plan Pago\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECANIVERSARIOPOLIZA\" msdata:Caption=\"Aniversario P—liza\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNCUENTADEBITOTERCEROS\" msdata:Caption=\"Cuenta DŽbitos Terc.\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODTIPODOCUMENTOTITULAR\" msdata:Caption=\"Tipo Docum. Titular\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"TASAINTERES\" msdata:Caption=\"Tasa de InterŽs\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CANTDIASGRACIA\" msdata:Caption=\"Cant. D’as Gracia\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODTIPOASEGURADORA\" msdata:Caption=\"Tipo Aseguradora\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"PORSOBRECOMISIONASEGURADORA\" msdata:Caption=\"% Sobre Comis. Aseg.\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IMPMARKUP\" msdata:Caption=\"Imp. Mark Up\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODRAMO\" msdata:Caption=\"Ramo\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNVIP\" msdata:Caption=\"Cliente VIP\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"LISTAPORAJUSTE\" msdata:Caption=\"ListaPorAjuste\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDCONTACTOASEGURADORA2\" msdata:Caption=\"Id. Contacto Cia. 2\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNPRINCIPALMASIVA\" msdata:Caption=\"PM\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODIGOPOSTAL\" msdata:Caption=\"C. P.\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNCOBERTURAUNIFICADA\" msdata:Caption=\"Cob. Unificada\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SIMBOLOMONEDA\" msdata:Caption=\"S’mbolo Moneda\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDOPERACIONRELACIONADA\" msdata:Caption=\"Id Operaci—n Relacio\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNPOLIZAABIERTA\" msdata:Caption=\"PA\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODASEGURADORA\" msdata:Caption=\"C—d. Aseguradora\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODCOEFICIENTE\" msdata:Caption=\"C—d. Coeficiente\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CANTDIASREFACTURACION\" msdata:Caption=\"Cant. D’as Refact.\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"OPERACIONBANCARIARELACIONADA2\" msdata:Caption=\"Nro. PrŽstamo 2\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"HORAVIGENCIAINICIALPOLIZA\" msdata:Caption=\"Hora Ini Vig Poliza\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"HORAVIGENCIAFINALPOLIZA\" msdata:Caption=\"Hora Fin Vig P—liza\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NROORDENEXCESO\" msdata:Caption=\"Nro. Orden Exceso\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"TIPOCOASEGURO\" msdata:Caption=\"Tipo Coaseguro\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"TIPODOMICILIOOPERACION\" msdata:Caption=\"Tipo Domicilio\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNPLANPAGOADELANTADOSC\" msdata:Caption=\"Plan Pago Adel. Sec.\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNORIGENWEB\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNHABILITADOLICITACIONES\" msdata:Caption=\"Habilit. Licitacione\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNPRIMANEGATIVA\" msdata:Caption=\"Prima Negativa\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDTABLACOSTOAUTO\" msdata:Caption=\"Cond. Cotizaci—n\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNVIGENCIAENHORAS\" msdata:Caption=\"Vig. en Hs.\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"HORADEFAULTINICIOVIGENCIA\" msdata:Caption=\"Hs. Def. Inicio Vig.\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"HORADEFAULTFINVIGENCIA\" msdata:Caption=\"Hs. Def. F’n Vig.\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNUSARINCTASACOSTOAUTO\" msdata:Caption=\"Usar Inc.Tasa C.Auto\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NRORUTA\" msdata:Caption=\"Nro. de RUTA\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODPOLIZARELACIONADA\" msdata:Caption=\"C—d. P—liza Relacion\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECINICIOCREDITO\" msdata:Caption=\"Fecha Inicio CrŽdito\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODFINANCIADOR\" msdata:Caption=\"C—d. Financiador\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODFINANCIACIONTIPO\" msdata:Caption=\"C—d. Tipo Financiaci\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"MODORENOVACION\" msdata:Caption=\"Modo Renovaci—n\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNAVISOITEMBAJA\" msdata:Caption=\"Aviso Item Baja\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODTIPOCLIENTE\" msdata:Caption=\"C—d. Tipo Cliente\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODOPEESTADORENOVACION\" msdata:Caption=\"C—d. Ope. Estado RN\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNVISIBLEWEB\" msdata:Caption=\"VW\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNVALIDAFORMAPAGO\" msdata:Caption=\"Valida Forma Pago\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNINCTASAPORCENTAJE\" msdata:Caption=\"Inc. Tasa Porcentaje\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNPERMITEPRIMACOBCERO\" msdata:Caption=\"Permite Prima Cob. 0\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"DESCTABLACOSTOAUTO\" msdata:Caption=\"Desc. Cond. Cotiz.\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNREASEGURO\" msdata:Caption=\"Reaseguro\" type=\"xs:string\" default=\"N\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"TIPONUMERACIONCERTIFICADO\" msdata:Caption=\"Num Certificado\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNDEDUCIBLEOPEINVISIBLE\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNCLAUSULAOPEINVISIBLE\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNSUBLIMITEOPEINVISIBLE\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNITEMOBLIGATORIOSINIESTRO\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNSCORINGOPEINVISIBLE\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNACCESORIOSOPEINVISIBLE\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"EDADACTUARIALMIN\" msdata:Caption=\"Edad Act. Min.\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"EDADACTUARIALMAX\" msdata:Caption=\"Edad Act. Max.\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CANTANIOSAPORTEPRIMA\" msdata:Caption=\"A–os aporte Prima\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"PORTASAGARANTDEFAULT\" msdata:Caption=\"% Tasa Garant. Defau\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"PORTASAPROYNOGARANTDEFAULT\" msdata:Caption=\"% Tasa Proy./No Gara\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"PORRESCATEDEFAULT\" msdata:Caption=\"% Rescate Default\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSEXO\" msdata:Caption=\"C—d. Sexo\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODBANCOPRESTAMO\" msdata:Caption=\"C—d. Banco\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSUCURSALPRESTAMO\" msdata:Caption=\"C—d. Sucursal\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"ENTEEJECUTIVOPRESTAMO\" msdata:Caption=\"Id. Ejecutivo\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"EJECUTIVOPRESTAMO\" msdata:Caption=\"tivo\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"TIPOACREEDORPRESTAMO\" msdata:Caption=\"Tipo Acreedor\" type=\"xs:decimal\" default=\"0\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"ENTEACREEDORPRESTAMO\" msdata:Caption=\"Id. Acreedor\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"ACREEDORPRESTAMO\" msdata:Caption=\"Acreedor\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODTIPODOCACREEDORPRESTAMO\" msdata:Caption=\"Tipo Doc. Acreedor\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"NROFISCALACREEDORPRESTAMO\" msdata:Caption=\"Nro. Fiscal Acreedor\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"IDDOMICILIOACREEDORPRESTAMO\" msdata:Caption=\"Id. Domicilio Acreed\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"ENTECONTACTOACREEDORPRESTAMO\" msdata:Caption=\"Id. Contacto Acreedo\" type=\"xs:decimal\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CONTACTOACREEDORPRESTAMO\" msdata:Caption=\"Contacto Acreedor\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNHABILITAPRESTAMO\" msdata:Caption=\"Habilita Prestamo\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECVIGENCIAINICIALCERTIFICADO\" msdata:Caption=\"Fec. vig. Ini. Cert.\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECVIGENCIAFINALCERTIFICADO\" msdata:Caption=\"Fec. Vig. Fin Cert.\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"SNNOEDITAFINVIGPOLIZA\" msdata:Caption=\"No Edita Fin Vig P—l\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"FECFINVIGENCIA\" msdata:Caption=\"Fecha Fin Vigencia\" type=\"xs:dateTime\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t\t<xs:element name=\"CODSUBPRODUCTOVIGENCIA\" type=\"xs:string\" minOccurs=\"0\"/>\n" +
                "\t\t\t\t\t\t\t</xs:sequence>\n" +
                "\t\t\t\t\t\t</xs:complexType>\n" +
                "\t\t\t\t\t</xs:element>\n" +
                "\t\t\t\t</xs:choice>\n" +
                "\t\t\t</xs:complexType>\n" +
                "\t\t</xs:element>\n" +
                "\t</xs:schema>\n" +
                "\t<MASTER>\n" +
                "\t\t<Link0>0</Link0>\n" +
                "\t\t<IDOPERACION>1</IDOPERACION>\n" +
                "\t\t<NROTIPOBIEN>1</NROTIPOBIEN>\n" +
                "\t\t<CODTIPOORDEN>RN</CODTIPOORDEN>\n" +
                "\t\t<CODOPERACIONESTADO>1</CODOPERACIONESTADO>\n" +
                "\t\t<NROORDENEMISION>1</NROORDENEMISION>\n" +
                "\t\t<CODPOLIZA>202684</CODPOLIZA>\n" +
                "\t\t<IDSECUENCIA>1</IDSECUENCIA>\n" +
                "\t\t<SNULTIMOESTADO>S</SNULTIMOESTADO>\n" +
                "\t\t<CODTRANSACCION>99</CODTRANSACCION>\n" +
                "\t\t<CODPAIS>054</CODPAIS>\n" +
                "\t\t<IDCLIENTE>100013</IDCLIENTE>\n" +
                "\t\t<IDASEGURADORA>122</IDASEGURADORA>\n" +
                "\t\t<IDPRODUCTOROPERACION>1000</IDPRODUCTOROPERACION>\n" +
                "\t\t<CODSECCION>001</CODSECCION>\n" +
                "\t\t<CODMONEDA>PE</CODMONEDA>\n" +
                "\t\t<IDASEGURADO>100013</IDASEGURADO>\n" +
                "\t\t<IDDOMICILIOPOLIZA>23</IDDOMICILIOPOLIZA>\n" +
                "\t\t<IDVENDEDOR>2000</IDVENDEDOR>\n" +
                "\t\t<IDTOMADOR>100013</IDTOMADOR>\n" +
                "\t\t<CODPROVINCIA>C</CODPROVINCIA>\n" +
                "\t\t<FECVIGENCIAINICIALPOLIZA>1987-07-01T00:00:00-03:00</FECVIGENCIAINICIALPOLIZA>\n" +
                "\t\t<FECVIGENCIAFINALPOLIZA>1990-06-30T00:00:00-03:00</FECVIGENCIAFINALPOLIZA>\n" +
                "\t\t<FECVIGENCIAINICIALOPERACION>1987-07-01T00:00:00-03:00</FECVIGENCIAINICIALOPERACION>\n" +
                "\t\t<FECVIGENCIAFINALOPERACION>1990-06-30T00:00:00-03:00</FECVIGENCIAFINALOPERACION>\n" +
                "\t\t<FECEMISION>1987-08-03T00:00:00-03:00</FECEMISION>\n" +
                "\t\t<FECFACTURACION>1987-08-03T00:00:00-03:00</FECFACTURACION>\n" +
                "\t\t<FECEMISIONPOLIZAENDOSO>1987-08-03T00:00:00-03:00</FECEMISIONPOLIZAENDOSO>\n" +
                "\t\t<SNPOLIZAMASIVA>N</SNPOLIZAMASIVA>\n" +
                "\t\t<SNPOLIZAPILOTO>N</SNPOLIZAPILOTO>\n" +
                "\t\t<SNCOASEGURO>N</SNCOASEGURO>\n" +
                "\t\t<SNADHERIDODEBITOAUTOMATICO>N</SNADHERIDODEBITOAUTOMATICO>\n" +
                "\t\t<SNSUSPENDEDEBITO>N</SNSUSPENDEDEBITO>\n" +
                "\t\t<SNFACTURADA>S</SNFACTURADA>\n" +
                "\t\t<CODMOTIVOENDOSO>001</CODMOTIVOENDOSO>\n" +
                "\t\t<DESCMOTIVOENDOSO>Poliza Nueva</DESCMOTIVOENDOSO>\n" +
                "\t\t<CODCATEGORIADEUDA>N</CODCATEGORIADEUDA>\n" +
                "\t\t<IMPSUMAASEGURADAPOLIZA>6</IMPSUMAASEGURADAPOLIZA>\n" +
                "\t\t<IMPSUMAASEGURADAOPERACION>6</IMPSUMAASEGURADAOPERACION>\n" +
                "\t\t<DP_IMPPRIMAAUTOMATICA>0.02</DP_IMPPRIMAAUTOMATICA>\n" +
                "\t\t<DP_IMPPRIMAMANUAL>0</DP_IMPPRIMAMANUAL>\n" +
                "\t\t<DP_IMPPRIMA>0.02</DP_IMPPRIMA>\n" +
                "\t\t<DP_PORRECARGOADMINISTRATIVO>0</DP_PORRECARGOADMINISTRATIVO>\n" +
                "\t\t<DP_IMPRECARGOADMINISTRATIVO>0</DP_IMPRECARGOADMINISTRATIVO>\n" +
                "\t\t<DP_IMPDERECHOEMISION>0</DP_IMPDERECHOEMISION>\n" +
                "\t\t<DP_POROTROSIMPUESTOS>0</DP_POROTROSIMPUESTOS>\n" +
                "\t\t<DP_IMPOTROSIMPUESTOS>0</DP_IMPOTROSIMPUESTOS>\n" +
                "\t\t<CODSEGUIMIENTO>01</CODSEGUIMIENTO>\n" +
                "\t\t<DP_PORRECARGOFINANCIERO>0</DP_PORRECARGOFINANCIERO>\n" +
                "\t\t<DP_IMPRECARGOFINANCIERO>0</DP_IMPRECARGOFINANCIERO>\n" +
                "\t\t<DP_IMPINGRESOSBRUTOS>0</DP_IMPINGRESOSBRUTOS>\n" +
                "\t\t<DP_PORSELLADOS>0</DP_PORSELLADOS>\n" +
                "\t\t<DP_IMPSELLADOS>0</DP_IMPSELLADOS>\n" +
                "\t\t<DP_IMPPREMIO>0</DP_IMPPREMIO>\n" +
                "\t\t<DP_IMPBONIFICACIONES>0</DP_IMPBONIFICACIONES>\n" +
                "\t\t<DP_IMPPREMIONETO>0.03</DP_IMPPREMIONETO>\n" +
                "\t\t<DP_PORIVA>0</DP_PORIVA>\n" +
                "\t\t<DP_IMPIVA>0</DP_IMPIVA>\n" +
                "\t\t<DP_IMPPREMIOIVA>0.03</DP_IMPPREMIOIVA>\n" +
                "\t\t<DP_PORPREMIOSCORING>0</DP_PORPREMIOSCORING>\n" +
                "\t\t<DP_PORPRIMASCORING>0</DP_PORPRIMASCORING>\n" +
                "\t\t<DP_IMPPRIMAGRAVADA>0</DP_IMPPRIMAGRAVADA>\n" +
                "\t\t<DP_IMPPRIMANOGRAVADA>0</DP_IMPPRIMANOGRAVADA>\n" +
                "\t\t<TIPOCAMBIOOPERACION>1</TIPOCAMBIOOPERACION>\n" +
                "\t\t<CODDEBITOFORMA>EFE</CODDEBITOFORMA>\n" +
                "\t\t<SNCREDITO>N</SNCREDITO>\n" +
                "\t\t<CODTIPORENOVACION>M</CODTIPORENOVACION>\n" +
                "\t\t<SNVUELCO>S</SNVUELCO>\n" +
                "\t\t<SNDEBITOAUTOMATICO>N</SNDEBITOAUTOMATICO>\n" +
                "\t\t<SNIVAPRORRATEADO>S</SNIVAPRORRATEADO>\n" +
                "\t\t<SNCOMISIONAUTORIZADA>S</SNCOMISIONAUTORIZADA>\n" +
                "\t\t<SNDOCUMENTACIONRECIBIDA>S</SNDOCUMENTACIONRECIBIDA>\n" +
                "\t\t<CODSEGMENTACION1>01006</CODSEGMENTACION1>\n" +
                "\t\t<CODSEGMENTACION2>02001</CODSEGMENTACION2>\n" +
                "\t\t<CODSEGMENTACION3>03002</CODSEGMENTACION3>\n" +
                "\t\t<CODSEGMENTACION4>04096</CODSEGMENTACION4>\n" +
                "\t\t<SNMULTIRIESGO>N</SNMULTIRIESGO>\n" +
                "\t\t<SNMONOITEM>N</SNMONOITEM>\n" +
                "\t\t<NROCUOTAS>1</NROCUOTAS>\n" +
                "\t\t<IMPSALDOOPERACION>0</IMPSALDOOPERACION>\n" +
                "\t\t<DESCCLIENTE>IECSA S.A.</DESCCLIENTE>\n" +
                "\t\t<DESCASEGURADORA>LA ANGLO ARGENTINA</DESCASEGURADORA>\n" +
                "\t\t<ABREVIATURAASEG>OTRAS CIAS</ABREVIATURAASEG>\n" +
                "\t\t<DESCMONEDA>PESOS</DESCMONEDA>\n" +
                "\t\t<DESCSECCION>INCENDIO</DESCSECCION>\n" +
                "\t\t<TIENESALDO>0</TIENESALDO>\n" +
                "\t\t<FECALTA>2000-07-03T03:21:51-03:00</FECALTA>\n" +
                "\t\t<ESTADO>A</ESTADO>\n" +
                "\t\t<USUARIO>VUELCO INWORX</USUARIO>\n" +
                "\t\t<GUIDSTAMP>EAAVHZDh7lDUEYDTAFCLo3Yd</GUIDSTAMP>\n" +
                "\t\t<IDPOLIZA>1</IDPOLIZA>\n" +
                "\t\t<IDCERTIFICADO>1</IDCERTIFICADO>\n" +
                "\t\t<CODSEGMENTACION5>05003</CODSEGMENTACION5>\n" +
                "\t\t<CODSEGMENTACION7>07033</CODSEGMENTACION7>\n" +
                "\t\t<CODSEGMENTACION8>08068</CODSEGMENTACION8>\n" +
                "\t\t<NROPROCESO>0</NROPROCESO>\n" +
                "\t\t<NROCONFIDENCIALIDAD>2</NROCONFIDENCIALIDAD>\n" +
                "\t\t<NROVISIBILIDAD>2</NROVISIBILIDAD>\n" +
                "\t\t<TIPONUMERACIONPOLIZA>0</TIPONUMERACIONPOLIZA>\n" +
                "\t\t<TIPONUMERACIONENDOSO>0</TIPONUMERACIONENDOSO>\n" +
                "\t\t<IDOPERACIONRENOVANTE>574885</IDOPERACIONRENOVANTE>\n" +
                "\t\t<CODCLASENEGOCIO>CER</CODCLASENEGOCIO>\n" +
                "\t\t<NROFORMACALCULOPRIMA>0</NROFORMACALCULOPRIMA>\n" +
                "\t\t<SNFACTURAPROVISORIA>N</SNFACTURAPROVISORIA>\n" +
                "\t\t<SNCLIENTEAGENTERETENCION>N</SNCLIENTEAGENTERETENCION>\n" +
                "\t\t<SNCLIENTEAGENTEPERCEPCION>N</SNCLIENTEAGENTEPERCEPCION>\n" +
                "\t\t<CODTIPOIVACOMPANIA>009</CODTIPOIVACOMPANIA>\n" +
                "\t\t<SNCOMPANIAAGENTERETENCION>N</SNCOMPANIAAGENTERETENCION>\n" +
                "\t\t<SNCOMPANIAAGENTEPERCEPCION>N</SNCOMPANIAAGENTEPERCEPCION>\n" +
                "\t\t<NROFISCALASEGURADORA>30-50005949-0</NROFISCALASEGURADORA>\n" +
                "\t\t<NROTIPODOMICILIOSECCION>0</NROTIPODOMICILIOSECCION>\n" +
                "\t\t<SNSINIESTRADA>N</SNSINIESTRADA>\n" +
                "\t\t<SNAUTOSEGURO>N</SNAUTOSEGURO>\n" +
                "\t\t<SNNOPRORRATEACONCEPTOS>N</SNNOPRORRATEACONCEPTOS>\n" +
                "\t\t<SNCUENTADEBITOTERCEROS>N</SNCUENTADEBITOTERCEROS>\n" +
                "\t\t<TASAINTERES>0</TASAINTERES>\n" +
                "\t\t<CANTDIASGRACIA>0</CANTDIASGRACIA>\n" +
                "\t\t<CODRAMO>PRO</CODRAMO>\n" +
                "\t\t<SNVIP>N</SNVIP>\n" +
                "\t\t<SNPRINCIPALMASIVA>N</SNPRINCIPALMASIVA>\n" +
                "\t\t<SNCOBERTURAUNIFICADA>N</SNCOBERTURAUNIFICADA>\n" +
                "\t\t<SNPOLIZAABIERTA>N</SNPOLIZAABIERTA>\n" +
                "\t\t<CODASEGURADORA>99023</CODASEGURADORA>\n" +
                "\t\t<TIPOCOASEGURO>0</TIPOCOASEGURO>\n" +
                "\t\t<TIPODOMICILIOOPERACION>0</TIPODOMICILIOOPERACION>\n" +
                "\t\t<SNPLANPAGOADELANTADOSC>N</SNPLANPAGOADELANTADOSC>\n" +
                "\t\t<SNORIGENWEB>N</SNORIGENWEB>\n" +
                "\t\t<SNPRIMANEGATIVA>N</SNPRIMANEGATIVA>\n" +
                "\t\t<SNVIGENCIAENHORAS>S</SNVIGENCIAENHORAS>\n" +
                "\t\t<HORADEFAULTINICIOVIGENCIA>1900-12-30T12:00:00-03:00</HORADEFAULTINICIOVIGENCIA>\n" +
                "\t\t<HORADEFAULTFINVIGENCIA>1900-12-30T12:00:00-03:00</HORADEFAULTFINVIGENCIA>\n" +
                "\t\t<SNAVISOITEMBAJA>N</SNAVISOITEMBAJA>\n" +
                "\t\t<CODOPEESTADORENOVACION>1</CODOPEESTADORENOVACION>\n" +
                "\t\t<SNINCTASAPORCENTAJE>N</SNINCTASAPORCENTAJE>\n" +
                "\t\t<SNPERMITEPRIMACOBCERO>N</SNPERMITEPRIMACOBCERO>\n" +
                "\t\t<SNREASEGURO>N</SNREASEGURO>\n" +
                "\t\t<TIPONUMERACIONCERTIFICADO>0</TIPONUMERACIONCERTIFICADO>\n" +
                "\t\t<SNDEDUCIBLEOPEINVISIBLE>N</SNDEDUCIBLEOPEINVISIBLE>\n" +
                "\t\t<SNCLAUSULAOPEINVISIBLE>N</SNCLAUSULAOPEINVISIBLE>\n" +
                "\t\t<SNSUBLIMITEOPEINVISIBLE>N</SNSUBLIMITEOPEINVISIBLE>\n" +
                "\t\t<SNITEMOBLIGATORIOSINIESTRO>N</SNITEMOBLIGATORIOSINIESTRO>\n" +
                "\t\t<SNSCORINGOPEINVISIBLE>N</SNSCORINGOPEINVISIBLE>\n" +
                "\t\t<SNACCESORIOSOPEINVISIBLE>N</SNACCESORIOSOPEINVISIBLE>\n" +
                "\t\t<CODSEXO>N</CODSEXO>\n" +
                "\t\t<SNHABILITAPRESTAMO>S</SNHABILITAPRESTAMO>\n" +
                "\t\t<FECVIGENCIAINICIALCERTIFICADO>1987-07-01T00:00:00-03:00</FECVIGENCIAINICIALCERTIFICADO>\n" +
                "\t\t<FECVIGENCIAFINALCERTIFICADO>1990-06-30T00:00:00-03:00</FECVIGENCIAFINALCERTIFICADO>\n" +
                "\t</MASTER1>\n" +
                "</NewDataSet>";
        return message;
    }


}
