package mx.com.aon.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class HttpRequestUtil {

	private static Logger logger = Logger.getLogger(HttpRequestUtil.class);

	// private final String USER_AGENT = "Mozilla/5.0";

	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final int CODIGO_RESPUESTA_OK = 200;

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
