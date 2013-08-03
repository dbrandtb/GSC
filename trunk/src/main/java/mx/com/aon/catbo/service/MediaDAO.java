package mx.com.aon.catbo.service;

import java.io.InputStream;

import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.MediaTO;

public interface MediaDAO {
	//public String guardarArchivo (MediaTO mediaTO, InputStream inputStream, int contentLength) throws Exception;
	public BackBoneResultVO guardarArchivo (MediaTO mediaTO, InputStream inputStream, int contentLength) throws Exception;
	
	/**
	 * Metodo para la descarga de los archivos de los Movimientos en los casos de BO
	 * @param nmcaso
	 * @param nmovimiento
	 * @param nmarchivo
	 * @return
	 * @throws Exception
	 */
	public MediaTO descargarArchivo(String nmcaso, String nmovimiento, String nmarchivo) throws Exception;
	public MediaTO descargarArchivoJdbcPuro(String nmcaso, String nmovimiento, String nmarchivo) throws Exception;
}
