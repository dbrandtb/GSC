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
		//FTPSUtils.subeArchivo("10.1.1.133", "oinstall", "j4v4n3s", "software", "E:\\", "CARATULA.pdf");
		FTPSUtils.subeArchivo("10.1.1.133", "oinstall", "j4v4n3s", "software", new File("E:\\CARATULA.pdf"));
	}

	/*
	 * Sube un archivo v&iacute;a Secure FTP
	 * @param host            Nombre del host que recibir&aacute; el archivo
	 * @param user            Usuario FTP
	 * @param password        Password FTP
	 * @param remoteDirectory Ruta relativa del directorio remoto donde residir&aacute; el archivo
	 * @param localDirectory  Directorio local
	 * @param localFileName
	 * @return true si lo sube correctamente, false si no
	 */
	/*
	public static boolean subeArchivo(String host, String user, String password, 
			String remoteDirectory, String localDirectory, String localFileName) {
		
		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			logger.debug("host=" + host);
			logger.debug("user=" + user);
			logger.debug("password="+password);
			logger.debug("remoteDirectory=" + remoteDirectory);
			logger.debug("localDirectory=" + localDirectory);
			logger.debug("fileToFTP=" + localFileName);
			
			// check if the file exists
			String filepath = localDirectory + "/" + localFileName;
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
			String sftpUri = "sftp://" + user + ":" + password + "@"
					+ host + "/" + remoteDirectory + "/" + localFileName;

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
	*/
	
	
	/**
	 * Sube un archivo v&iacute;a Secure FTP
	 * @param host
	 * @param user
	 * @param password
	 * @param remoteDirectory
	 * @param localDirectory
	 * @param file
	 * @return true si lo sube correctamente, false si no
	 */
	public static boolean subeArchivo(String host, String user, String password, 
			String remoteDirectory, File file) {
		
		StandardFileSystemManager manager = new StandardFileSystemManager();

		try {
			logger.debug("host=" + host);
			logger.debug("user=" + user);
			logger.debug("password="+password);
			logger.debug("remoteDirectory=" + remoteDirectory);
			logger.debug("file=" + file.getAbsolutePath());
			
			// check if the file exists
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
			SftpFileSystemConfigBuilder.getInstance().setPreferredAuthentications(opts, "publickey,keyboard-interactive,password");

			// Create the SFTP URI using the host name, user, password, remote
			// path and file name
			String sftpUri = "sftp://" + user + ":" + password + "@"
					//+ host + "/" + remoteDirectory + "/" + file.getName();
					+ host + "/" + remoteDirectory + "/" + file.getName();

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