package mx.com.aon.pdfgenerator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import mx.com.ice.kernel.core.PropertyReader;



public class PDFGeneratorFormatoOT extends AbstractManagerJdbcTemplateInvoke{

	private final Log logger = LogFactory.getLog(PDFGeneratorFormatoOT.class);
	
	
	//TODO FIJAR LOS VALORES CORRESPONDIENTES PARA LOS PATHS Y NOMBRES DE ARCHIVOS
	private static final String pathPdf = PropertyReader.readProperty("pdf.url.bo");
    
    private static final String pathResources = PropertyReader.readProperty("img.url.resources.pdf");
	private static String nameImagenAON="AON.jpg";
	private static String nameImagenUniversal="Universal.jpg";
	

	//TODO QUITAR EL MAIN
	/*public static void main(String[] args) {
		
		PDFGeneratorFormatoOT formato=new PDFGeneratorFormatoOT();
		
		formato.generaFormatoOTpdf("BO1200903294");
		
		
	}*/
	
	
	/**
	 * Metodo Principal para generar el Formato de OT 
	 * @param String cdUnieco
	 * @param String cdRamo
	 * @param String estado
	 * @param String nmpoliza
	 * @return boolean success 
	 * 
	 */
	
	public boolean generaFormatoOTpdf(String cdUnieco, String cdRamo, String estado, String nmpoliza){
		logger.debug("--->>> ESTE ES EL GENERADOR DE EL FORMATO DE OT <<<---");
		
		
		
		/**
		 * EL METODO OBTEBER DATOS DEBE DE REGRESAR UN MAPA CON LOS 4 TIPOS DE OBJETOS QUE SE ACORDO
		 * 1) List de List con la llave "datosEncabezado"
		 * 2) List con la llave "datosRiesgo"
		 * 3) List de Maps con la llave "datosCoberturas" cada elemnto tendra un mapa con las llaves "COBERTURA","DEDUCIBLE"  y "SUMA_ASEGURADA"
		 * 4) List con la llave "primaTotal"
		 * */
		
		
		Map datos= obtenDatos( cdUnieco,  cdRamo,  estado,  nmpoliza);
		
		
		
		if(datos==null||datos.isEmpty()){
			logger.debug("ERROR AL GENERAR EL FORMATO DE OT");
			return false;
		}
		
		
		if(!datos.containsKey("datosEncabezado")||!datos.containsKey("datosRiesgo")||!datos.containsKey("datosCoberturas")||!datos.containsKey("primaTotal")){
			
			logger.debug("ERROR AL GENERAR EL FORMATO DE OT (FALTAN DATOS) ");
			return false;
		}
	
	
		List datosEncabezado=(ArrayList)datos.get("datosEncabezado");
		List datosRiesgo=(ArrayList)datos.get("datosRiesgo");
		List datosCoberturas=(ArrayList)datos.get("datosCoberturas");
		List primaTotal=(ArrayList)datos.get("primaTotal");	
		
	
		String ruta = pathPdf + "CASO_" + cdUnieco + cdRamo + estado + nmpoliza + ".pdf";


		Document document1 = new Document();

		try {


			PdfWriter writer1 = PdfWriter.getInstance(document1, new FileOutputStream(ruta));
			document1.open();
			document1.newPage();

			PdfContentByte cb1 = writer1.getDirectContent();

			
			Image jpg1 = Image.getInstance(pathResources +nameImagenAON);
			jpg1.scaleAbsolute(85, 45); 
			jpg1.setAbsolutePosition(20, 760);
			document1.add(jpg1);
			Image jpg2 = Image.getInstance(pathResources + nameImagenUniversal);
			jpg2.scaleAbsolute(120, 52);
			jpg2.setAbsolutePosition(450, 765);
			document1.add(jpg2);
			    
			
			//???
			cb1.moveTo(20, 600);
			cb1.lineTo(570, 600);
			cb1.moveTo(20, 355);
			cb1.lineTo(570, 355);
			cb1.moveTo(20, 145);
			cb1.lineTo(570, 145);
			cb1.moveTo(20, 70);
			cb1.lineTo(570, 70);
			cb1.stroke();
			
			
			cb1.beginText();
			
			
//////////////////////////////////////////PARA TODOS LOS HEADERS//////////////////////////////////////////////////////////////////////////////
			
			BaseFont bf0 = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			cb1.setFontAndSize(bf0, 12);
			
			String header01 = "Datos de la Póliza";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, header01, 20, 700, 0);
			String header02 = "Datos del Riesgo";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, header02, 20, 585, 0);
			String header03 = "Coberturas Amparadas";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, header03, 20, 335, 0);
			String header04 = "Cobertura";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, header04, 20, 315, 0);
			String header04a = "Deducible";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, header04a, 250, 315, 0);
			String header04b = "Suma Asegurada";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, header04b, 400, 315, 0);
			String header04c = "Costo del Seguro";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, header04c, 20, 120, 0);
			
			
			
			int x=0,y=0,cont=0,desc=15,contadorTotal=0;
////////////////////////////////////////////PARA EL CONTENIDO DE LOS APARTADOS////////////////////////////////////////////////////////////////
			
			BaseFont bf1 = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			BaseFont bf2 = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	
			//logger.debug("datooos"+datosEncabezado);
			
			
			cb1.setFontAndSize(bf1, 11);
			///////////////////////////////////Para el apartado del encabezado//////////////////////////////
			
			//Para los labels
			//String txttit1 = "Número de Orden de Trabajo:";
			//cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit1, 283, 745, 0);
			
			
			String txttit2 = "Fecha de Envío a Aseguradora:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit2, 283, 730, 0);
			
			cb1.setFontAndSize(bf2, 10);
			
			
			//para los Values
			//String txttit1 = "Número de Orden de Trabajo:";
			//cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)(((ArrayList)datosEncabezado.get(0)).get(0)), 450, 745, 0);
			
			
			//String txttit2 = "Fecha de Envío a Aseguradora:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)(((ArrayList)datosEncabezado.get(0)).get(0)), 450, 730, 0);
			
		
			cb1.setFontAndSize(bf1, 11);
			///////////////////////////////////Para apartado de los datos de la poliza///////////////////////
			x=20;
			y=675;
			cont=0;
			
			
			
			//Labels encabezado
			String txttit3 = "Aseguradora:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit3, x, 675, 0);
			cont++;
			
			String txttit4 = "Fecha inicio vigencia:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit4, x, 660, 0);
			cont++;
			
			String txttit5 = "Forma de Pago:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit5, x, 645, 0);
			cont++;
			
			String txttit6 = "Instrumento de Pago:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit6, x, 630, 0);
			cont++;
			
			String txttit7 = "Plan:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit7, x, 615, 0);
			cont++;
	
			
			
			String txttit4b = "Fecha fin vigencia:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit4b, 283,660, 0);
			
			
			String txttit4c = "Asegurado:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit4c, 283,630, 0);
			
			cb1.setFontAndSize(bf2, 10);
			
			
			//Values encabezado
			
			logger.debug("adasd" + (ArrayList)datosEncabezado.get(0));
			//String txttit3 = "Aseguradora:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)(((ArrayList)datosEncabezado.get(0)).get(1)), x+120, 675, 0);
			cont++;
			
			//String txttit4 = "Fecha inicio vigengia:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)(((ArrayList)datosEncabezado.get(0)).get(2)), x+120, 660, 0);
			cont++;
			
			//String txttit5 = "Forma de Pago:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)(((ArrayList)datosEncabezado.get(0)).get(4)), x+120, 645, 0);
			cont++;
			
			//String txttit6 = "Instrumento de Pago:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)(((ArrayList)datosEncabezado.get(0)).get(5)), x+120, 630, 0);
			cont++;
			
			//String txttit7 = "Plan:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)(((ArrayList)datosEncabezado.get(0)).get(6)), x+120, 615, 0);
			cont++;
	
			
			
			//String txttit4b = "Fecha fin vigengia:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)(((ArrayList)datosEncabezado.get(0)).get(3)),385,660, 0);
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)(((ArrayList)datosEncabezado.get(0)).get(7)),385,630, 0);
			
			
			
			cb1.setFontAndSize(bf1, 11);
			///////////////////////////////////Para el apartado de datos del riesgo/////////////////////////////
			cont=0;
			y=560;
			x=20;
			
			
			//Labels riesgo
			String txttit8 = "Marca:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit8, x,560, 0);
			cont++;
			
			String txttit9 = "Modelo:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit9, x,545, 0);
			cont++;
			
			String txttit10 = "Año:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit10, x,530, 0);
			cont++;
			
			String txttit11 = "Nuevo/Usado:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit11, x,515, 0);
			cont++;
			
			String txttit12 = "Costo Original:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit12, x,500, 0);
			cont++;
			
			String txttit13 = "Uso:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit13, x,380, 0);
			cont++;
			
			String txttit15 = "Calle";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit15, x,485, 0);
			cont++;
			
			String txttit16 = "Pueblo:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit16, x,470, 0);
			cont++;
			
			String txttit17 = "Código Postal";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit17, x,455, 0);
			cont++;
			
			String txttit18 = "Asistencia en la Carretera:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit18, x,440, 0);
			cont++;
			
			String txttit19 = "Reembolso por Alquiler:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit19, x,425, 0);
			cont++;
			
			String txttit20 = "Grúa y Labor:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit20, x,410, 0);
			cont++;
			
			String txttit200 = "Gastos Médicos:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit200, x,395, 0);
			cont++;
			
			
			//Values riesgo
			cb1.setFontAndSize(bf2, 10);
			
			for(contadorTotal=0;contadorTotal<cont;contadorTotal++){
				if(contadorTotal==5)continue;
				
				cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)datosRiesgo.get(contadorTotal), x+130,y, 0);
				y-=desc;
				
				if(contadorTotal==cont-1)cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)datosRiesgo.get(5), x+130,y, 0);
			}
			
			
			x=320;
			y=560;
			cb1.setFontAndSize(bf1, 11);
			
			
			////////Seguda columna////////
			//Labels riesgo (segundaColumna)
			String txttit8a0 = "VIN:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit8a0, x,560, 0);
			
			String txttit8a1 = "Banco:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit8a1, x,545, 0);
			
			String txttit8a2 = "Tablilla:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit8a2, x,530, 0);
			
			String txttit8a = "Responsabilidad Civil:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit8a, x,515, 0);
			
			String txttit9a = "Comprensiva/Colisión:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit9a, x,500, 0);
			
			String txttit11a = "Fecha de Nacimiento:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit11a, x,485, 0);
			
			String txttit12a = "Estado Civil";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit12a, x,470, 0);
			
			String txttit13a = "Licencia:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit13a, x,455, 0);
						
			String txttit15a = "Numero Seguro Social:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit15a, x,440, 0);
			
			String txttit16a = "Ocupación:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit16a, x,425, 0);
			
			/*
			String txttit17a = "Código Postal:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit17a, x,410, 0);
			String txttit18a = "Asegurado:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit18a, x,395, 0);
			*/
			
			
			//Values riesgo (segundaColumna)
			cb1.setFontAndSize(bf2, 10);
			for(;contadorTotal<datosRiesgo.size();contadorTotal++){
				
				cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)datosRiesgo.get(contadorTotal), x+120,y, 0);
				y-=desc;
			}
			
			
			x=20;
			y=275;
			//////////////////////////////////////PARA COBERTURAS AMPARADAS///////////////////////////
			
			Map cobertura;
			String elemento;
			for(int i=0;i<datosCoberturas.size();i++){
				cobertura= new HashMap((HashMap)datosCoberturas.get(i));
				
				
				cb1.setFontAndSize(bf1, 10);
				
				elemento=(String)cobertura.get("COBERTURA");
				cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, elemento, x,y, 0);
				
				
				cb1.setFontAndSize(bf2, 10);
				
				elemento=(String)cobertura.get("DEDUCIBLE");
				cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, elemento, x+250,y, 0);
				
				elemento=(String)cobertura.get("SUMA_ASEGURADA");
				cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, elemento, x+390,y, 0);
				
				
				y=y-15;
				
			}
			
			
			
			cb1.setFontAndSize(bf1, 11);
			//////////////////////////////////////PARA PRIMA TOTAL/////////////////////////////////
			String txttit21 = "Prima Total:";
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, txttit21, 20,100, 0);
			
			
			cb1.setFontAndSize(bf2, 10);
			cb1.showTextAligned(PdfContentByte.ALIGN_LEFT, (String)primaTotal.get(0), 70,100, 0);
			

			
			
			
			cb1.setTextMatrix(280, 40);
			// cb1.showText(text);
			cb1.endText();
			document1.close();
			writer1.close();

		} catch (DocumentException de) {
			logger.error(de, de);
			return false;
		} catch (IOException ioe) {
			logger.error(ioe, ioe);
			return false;
		}catch (Exception e){
			logger.error(e, e);
			return false;
		}
		
		if(!registraNombrePDF( cdUnieco,  cdRamo,  estado,  nmpoliza)){
			logger.debug("ERROR al Registrar el Nombre del PDF!!!");
		}
		
		File file = new File(ruta);
		while (file.exists() == false) {
			logger.info("No se ha generado el PDF");
		}
		
		return true;
	}
	
	/**
	 * Metodo para obtener los datos del Formato de OT
	 * @param String cdUnieco
	 * @param String cdRamo
	 * @param String estado
	 * @param String nmpoliza
	 * @return Map datosPdf 
	 * 
	 */
	
	public Map obtenDatos(String cdUnieco, String cdRamo, String estado, String nmpoliza){
		
		if (logger.isInfoEnabled())
			logger.info("Metodo obtenDatos");
		
		Map<String,Object> dataTotal= new HashMap<String, Object>();
		
		List dataEnc=new ArrayList();
		List dataRiesgo=new ArrayList();
		List coberturas=new ArrayList();
		List primaTotal=new ArrayList();
		
	    
		HashMap<String,String> parametros = new HashMap<String, String>();
		parametros.put("pv_cdunieco_i",cdUnieco);
		parametros.put("pv_cdramo_i",cdRamo);
		parametros.put("pv_estado_i",estado);
		parametros.put("pv_nmpoliza_i",nmpoliza);
		
        
		
        String procedure = "DATOS_POLIZA";
        try {
			dataEnc=getAllBackBoneInvoke(parametros, procedure);
			dataTotal.put("datosEncabezado", dataEnc);
		} catch (ApplicationException e) {
			logger.debug("Error al obtener los datos de la Poliza: "+e.getMessage());
			return null;
		
		}
		
		procedure = "DATOS_RIESGOS";
        try {
        	dataRiesgo=getAllBackBoneInvoke(parametros, procedure);
        	dataTotal.put("datosRiesgo", dataRiesgo);
		} catch (ApplicationException e) {
			logger.debug("Error al obtener los datos de la Poliza: "+e.getMessage());
			return null;
		
		}
		
		procedure = "COBERTURAS_AMPARADAS";
        try {
        	coberturas=getAllBackBoneInvoke(parametros, procedure);
        	dataTotal.put("datosCoberturas", coberturas);
		} catch (ApplicationException e) {
			logger.debug("Error al obtener los datos de la Poliza: "+e.getMessage());
			return null;
		
		}
		
		procedure = "COSTO_SEGURO";
        try {
        	primaTotal=getAllBackBoneInvoke(parametros, procedure);
        	dataTotal.put("primaTotal", primaTotal);
		} catch (ApplicationException e) {
			logger.debug("Error al obtener los datos de la Poliza: "+e.getMessage());
			return null;
		
		}
		/*
        
        
        
        
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn1 = null;

		String host = "192.168.1.190", puerto = "1527", SID = "ACWQA";
		String driver = "oracle.jdbc.driver.OracleDriver";
		String user = "ICE_CONFIGURING", pass = "ICE_CONFIGURING";
		
		
		try {
			Class.forName(driver);
		
			conn1 = DriverManager.getConnection("jdbc:oracle:thin:@" + host
					+ ":" + puerto + ":" + SID, user, pass);
			
			logger.debug("Conexion abierta");
			
			cs = conn1.prepareCall("{call PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_ENCABEZA (?,?,?,?,?,?,?) }");
			cs.setString(1, cdUnieco);
			cs.setString(2, cdRamo);
			cs.setString(3, estado);
			cs.setString(4, nmpoliza);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			cs.registerOutParameter(6, OracleTypes.NUMERIC);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			logger.debug("antes PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_ENCABEZA(?,?,?,?)");
			
			cs.execute();
			
			logger.debug("despues PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_ENCABEZA(?,?,?,?)");
			rs = (ResultSet) cs.getObject(5);

			
			
			if (rs.next()) {
				ArrayList tmp= new ArrayList();
				for(int i=1;i<=8;i++){
				logger.debug(rs.getString(i));
				
				tmp.add(rs.getString(i));
				
				}
				dataEnc.add(tmp);
			}
			logger.debug("dataEnc" + dataEnc.toString());
			cs.close();
			dataTotal.put("datosEncabezado", dataEnc);
			
			
			cs = conn1.prepareCall("{call PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_RIESGO (?,?,?,?,?,?,?) }");
			cs.setString(1, cdUnieco);
			cs.setString(2, cdRamo);
			cs.setString(3, estado);
			cs.setString(4, nmpoliza);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			cs.registerOutParameter(6, OracleTypes.NUMERIC);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			logger.debug("antes PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_RIESGO(?,?,?,?)");
			
			cs.execute();
			
			logger.debug("despues PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_RIESGO(?,?,?,?)");
			rs = (ResultSet) cs.getObject(5);

			
			
			while (rs.next()) {
				
				logger.debug(rs.getString(2));
				dataRiesgo.add(rs.getString(2));
				
			}
			cs.close();
			dataTotal.put("datosRiesgo", dataRiesgo);
			
			
			cs = conn1.prepareCall("{call PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_COBERTUR (?,?,?,?,?,?,?) }");
			cs.setString(1, cdUnieco);
			cs.setString(2, cdRamo);
			cs.setString(3, estado);
			cs.setString(4, nmpoliza);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			cs.registerOutParameter(6, OracleTypes.NUMERIC);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			logger.debug("antes PKG_CARATULAS2.P_Gen_Coti_Polizas(?,?,?,?)");
			
			cs.execute();
			
			logger.debug("despues PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_COBERTUR(?,?,?,?)");
			rs = (ResultSet) cs.getObject(5);

			
			String[] mapasKeys = new String[]{"COBERTURA","DEDUCIBLE","SUMA_ASEGURADA"};
			HashMap tmp;
			while (rs.next()) {
			tmp= new HashMap();
			for(int i=1;i<=3;i++){
				
				tmp.put(mapasKeys[i-1],rs.getString(i));
				logger.debug(rs.getString(i));
				
				}
				coberturas.add(tmp);
			}
			cs.close();
			dataTotal.put("datosCoberturas", coberturas);
			
			//logger.debug("Coberturas"+coberturas);
			
			
			
			cs = conn1.prepareCall("{call PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_PRIMA (?,?,?,?,?,?,?) }");
			cs.setString(1, cdUnieco);
			cs.setString(2, cdRamo);
			cs.setString(3, estado);
			cs.setString(4, nmpoliza);
			cs.registerOutParameter(5, OracleTypes.CURSOR);
			cs.registerOutParameter(6, OracleTypes.NUMERIC);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			logger.debug("antes PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_PRIMA(?,?,?,?)");
			
			cs.execute();
			
			logger.debug("despues PKG_CARATULAS2.P_OBTIENE_DATOS_ORDEN_PRIMA(?,?,?,?)");
			rs = (ResultSet) cs.getObject(5);

			
			
			if (rs.next()) {
				
				logger.debug(rs.getString(1));
				primaTotal.add(rs.getString(1));
				
			}
			cs.close();
			dataTotal.put("primaTotal", primaTotal);
			
			
			

		} catch (Exception e) {
			logger.debug("Error:" + e.toString());
			logger.error(e, e);
			e.printStackTrace();
		} finally {
			if (conn1 != null) {
				try {
					conn1.close();
					logger.debug("Conexion cerrada");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
*/

		return dataTotal;
	}
	
	/**
	 * Metodo para registrar el nombre del PDF luego de obtener generar el Formato de OT satisfactoriamente
	 * @param String cdUnieco
	 * @param String cdRamo
	 * @param String estado
	 * @param String nmpoliza
	 * @return boolean success 
	 * 
	 */
	public boolean registraNombrePDF(String cdUnieco, String cdRamo, String estado, String nmpoliza){
		
		HashMap<String,String> parametros = new HashMap<String, String>();
		parametros.put("pv_cdunieco_i",cdUnieco);
		parametros.put("pv_cdramo_i",cdRamo);
		parametros.put("pv_estado_i",estado);
		parametros.put("pv_nmpoliza_i",nmpoliza);
		
		String procedure = "NOMBRE_PDF";
        try {
        	returnBackBoneInvoke(parametros, procedure);
		} catch (ApplicationException e) {
			logger.debug("Error al llamar al registrar el Nombre del PDF: "+e.getMessage());
			return false;
		
		}
		return true;
		
	}

	public static void main(String a[]) {
		new PDFGeneratorFormatoOT().generaFormatoOTpdf("1", "41", "M", "118");
	}
	
}
