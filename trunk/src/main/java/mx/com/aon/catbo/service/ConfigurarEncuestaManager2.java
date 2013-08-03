package mx.com.aon.catbo.service;


import mx.com.aon.catbo.model.ConfigurarEncuestaVO;
import mx.com.aon.catbo.model.EncuestaVO;
import mx.com.aon.core.ApplicationException;

public interface ConfigurarEncuestaManager2 {		
		
		public String guardarConfigurarEncuesta(ConfigurarEncuestaVO configurarEncuestaVO)throws ApplicationException;
		
		public String guardarConfigurarEncuestaEditar(ConfigurarEncuestaVO configurarEncuestaVO)throws ApplicationException;
		
		public EncuestaVO getEncuestaPregunta(String cdEncuesta) throws ApplicationException;
		
}
