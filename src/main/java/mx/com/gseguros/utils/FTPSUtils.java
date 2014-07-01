package mx.com.gseguros.utils;

import java.io.File;

import mx.com.gseguros.portal.catalogos.controller.ClausuladoAction;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.log4j.Logger;

/**
 * Utiler&iacute;as para manejar recursos v&iacute;a el protocolo Secure FTP
 * @author Ricardo
 *
 */
public class FTPSUtils {
	
	private static Logger logger = Logger.getLogger(ClausuladoAction.class);

	public static void main(String[] args) {
		// Prueba de Secure FTP:
		FTPSUtils.subeArchivo("10.1.1.133", "oinstall", "j4v4n3s", "/export/home/oinstall/software", "E:\\", "CARATULA.pdf");
	}

	/**
	 * Sube un archivo v&iacute;a Secure FTP
	 * @param serverAddress
	 * @param userId
	 * @param password
	 * @param remoteDirectory
	 * @param localDirectory
	 * @param fileToFTP
	 * @return true si lo sube correctamente, false si no
	 */
	public static boolean subeArchivo(String serverAddress, String userId, String password, 
			String remoteDirectory, String localDirectory, String fileToFTP) {
		
		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			logger.debug("serverAddress=" + serverAddress);
			logger.debug("userId=" + userId);
			logger.debug("password="+password);
			logger.debug("remoteDirectory=" + remoteDirectory);
			logger.debug("localDirectory=" + localDirectory);
			
			// check if the file exists
			String filepath = localDirectory + fileToFTP;
			File file = new File(filepath);
			if (!file.exists()) {
				throw new RuntimeException("Error. Local file not found");
			}

			// Initializes the file manager
			manager.init();

			// Setup our SFTP configuration
			FileSystemOptions opts = new FileSystemOptions();
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
			SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

			// Create the SFTP URI using the host name, userid, password, remote
			// path and file name
			String sftpUri = "sftp://" + userId + ":" + password + "@"
					+ serverAddress + "/" + remoteDirectory + fileToFTP;

			// Create local file object
			FileObject localFile = manager.resolveFile(file.getAbsolutePath());

			logger.debug("localFile=" + localFile);
			
			// Create remote file object
			FileObject remoteFile = manager.resolveFile(sftpUri, opts);
			
			logger.debug("remoteFile=" + remoteFile);

			// Copy local file to sftp server
			remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
			logger.info("File upload successful via Secure FTP");

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return false;
		} finally {
			manager.close();
		}
		return true;
	}

}