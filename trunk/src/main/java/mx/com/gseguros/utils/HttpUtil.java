package mx.com.gseguros.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class HttpUtil {

	private static Logger logger = Logger.getLogger(HttpUtil.class);

	// private final String USER_AGENT = "Mozilla/5.0";

	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final int CODIGO_RESPUESTA_OK = 200;
	
	
	/**
	 * Obtiene un archivo a partir de su URL y lo genera en una ruta destino
	 * @param urlOrigen   URL de donde se obtendrá el flujo de datos del archivo a guardar 
	 * @param rutaDestino Ruta y nombre del archivo donde se almacenará el archivo con el flujo de datos
	 * @return true si gener&oacute; el archivo, false si fall&oacute;
	 */
	public static boolean generaArchivo(String urlOrigen, String rutaDestino) {
		
		logger.debug("Entrando a generaReporte()");
		logger.debug("urlInicio=" + urlOrigen);
		logger.debug("rutaDestino=" + rutaDestino);
		boolean isArchivoGenerado = false;
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			
			URL obj = new URL(urlOrigen);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod(GET);
	
			int responseCode = con.getResponseCode();
			logger.debug("Codigo de Respuesta : " + responseCode);
			if (responseCode != CODIGO_RESPUESTA_OK) {
				throw new Exception("Codigo de respuesta erroneo: " + responseCode);
			}
			
			inputStream = con.getInputStream();
			outputStream = null;
			// write the inputStream to a FileOutputStream
			outputStream = new FileOutputStream(new File(rutaDestino));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			
			isArchivoGenerado = true;
			logger.debug("Archivo creado: " + rutaDestino);

		} catch (Exception e) {
			logger.error("Error al generar el reporte en " + rutaDestino, e);
			isArchivoGenerado = false;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.warn("No se pudo cerrar el flujo de entrada", e);
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					logger.warn("No se pudo cerrar el flujo de salida", e);
				}

			}
		}
		return isArchivoGenerado;
	}

	
	/*
	public static void creaPeticionGet(String url) throws Exception {
		String inputLine;
		URL obj = new URL(url);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod(GET);

		int responseCode = con.getResponseCode();
		logger.debug("Codigo de Respuesta : " + responseCode);
		if (responseCode != CODIGO_RESPUESTA_OK) {
			throw new Exception("Codigo de respuesta erroneo: " + responseCode);
		}
		// Se lee la información de la respuesta:
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		logger.debug("Respuesta GET --> " + response.toString());
	}

	public static void main(String[] args) throws Exception {
    //for (int i = 0; i <= 5; i++) {
			HttpRequestUtil.generaReporte("http://201.122.160.245:7777/reports/rwservlet?destype=cache&desformat=PDF&userid=ice/ice@acwqa&report=CARATULA.rdf&paramform=no&p_unieco=1&p_cdramo=2&p_estado='W'&p_poliza=1250&desname=/opt/ice/gseguros/documentos/1250/CARATULA.pdf", "E:\\poliza.pdf");
    //}
	}
	*/

}