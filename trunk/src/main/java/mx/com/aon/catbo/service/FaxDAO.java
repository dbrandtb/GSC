package mx.com.aon.catbo.service;

import java.io.InputStream;

import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.FaxesVO;
import mx.com.aon.catbo.model.MediaTO;

public interface FaxDAO {
	
	  public BackBoneResultVO guardarAdministracionFax(final FaxesVO faxesVO, final InputStream inputStream,final int contentLength) throws Exception;
	  
	  public BackBoneResultVO guardarAdministracionFaxEditar(final FaxesVO faxesVO, final InputStream inputStream,
	 			final int contentLength) throws Exception; 
}
