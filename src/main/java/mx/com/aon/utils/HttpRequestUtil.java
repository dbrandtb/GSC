package mx.com.aon.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class HttpRequestUtil {

	private static Logger logger = Logger.getLogger(HttpRequestUtil.class);

	// private final String USER_AGENT = "Mozilla/5.0";

	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final int CODIGO_RESPUESTA_OK = 200;
	
	public static void generaReporte(String urlInicio, String rutaDestino) throws Exception {
		String inputLine;
		URL obj = new URL(urlInicio);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod(GET);

		int responseCode = con.getResponseCode();
		if (responseCode != CODIGO_RESPUESTA_OK) {
			logger.debug("Codigo de Respuesta : " + responseCode);
			throw new Exception("Codigo de respuesta erroneo: " + responseCode);
		}
		InputStream inputStream = con.getInputStream();
		OutputStream outputStream = null;

		try {
			// write the inputStream to a FileOutputStream
			outputStream = new FileOutputStream(new File(rutaDestino));
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			logger.debug("Archivo creado");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		
	}

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
		for (int i = 0; i <= 5; i++) {
			HttpRequestUtil.creaPeticionGet("http://192.168.1.69/request.php?param1=val1&param2=val2");
		}
	}

}