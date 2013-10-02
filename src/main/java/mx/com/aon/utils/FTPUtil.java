package mx.com.aon.utils;

import static mx.com.aon.portal.dao.ParametroGeneralDAO.BUSCAR_PARAMETROS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FTPUtil extends AbstractManagerJdbcTemplateInvoke {

	private static Logger logger = Logger.getLogger(FTPUtil.class);

	/*
	 * @param remote @param file
	 */
	public void storeFTP(String remote, String fileName, File file)
			throws ApplicationException, ServerConfigurationException {
		boolean binaryTransfer = false;
		String server, username, password;
		FTPClient ftp;

		logger.info("Buscando valores de envio de email");
		String[] valores = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("pi_nbparam", "FTP_SERVER|USER_FTP|PASS_FTP");

		try {
			WrapperResultados resultados = returnResult(map, BUSCAR_PARAMETROS);

			valores = StringUtils.split(resultados.getItemMap().get("pi_valor")
					.toString(), "|");

			binaryTransfer = true;

			if (StringUtils.isNotBlank(valores[0]))
				server = valores[0];
			else
				throw new ServerConfigurationException(
						"No es posible encontrar el servidor FTP en donde se va a subir el archivo");
			
			//server = "192.168.1.190";
			
			if (StringUtils.isNotBlank(valores[1]))
				username = valores[1];
			else
				throw new ServerConfigurationException("No es posible encontrar el usuario FTP en donde se va a subir el archivo");
			//username = "ice";
			
			if (StringUtils.isNotBlank(valores[2]))
				password = valores[2];
			else
				throw new ServerConfigurationException("No es posible encontrar el password FTP en donde se va a subir el archivo");
			//password = "icedev190";

		} catch (ApplicationException e) {
			logger
					.error("Error al buscar los datos del servidor de email en la base de datos");
			throw new ApplicationException(
					"Error al buscar los datos del servidor de email en la base de datos: "
							+ e.getMessage());
		}

		ftp = new FTPClient();
		ftp.addProtocolCommandListener(new PrintCommandListener(
				new PrintWriter(System.out)));

		try {
			int reply;
			ftp.connect(server);
			logger.info("Connected to " + server + ".");

			// After connection attempt, you should check the reply code to
			// verify
			// success.
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.error("FTP server refused connection.");
				throw new ApplicationException("FTP server refused connection.");
			}
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					// do nothing
				}
			}
			logger.error("Could not connect to server.");
			e.printStackTrace();
			throw new ApplicationException("Could not connect to server.");
		}

		__main: try {
			if (!ftp.login(username, password)) {
				ftp.logout();
				break __main;
			}

			logger.debug("Remote system is " + ftp.getSystemName());

			if (binaryTransfer)
				ftp.setFileType(FTP.BINARY_FILE_TYPE);

			ftp.enterLocalPassiveMode();

			InputStream input;
			input = new FileInputStream(file);
			ftp.storeFile(remote + "/" + fileName, input);
			input.close();

			ftp.logout();

		} catch (FTPConnectionClosedException e) {

		} catch (IOException e) {

		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					// do nothing
				}
			}
		}
	}
}