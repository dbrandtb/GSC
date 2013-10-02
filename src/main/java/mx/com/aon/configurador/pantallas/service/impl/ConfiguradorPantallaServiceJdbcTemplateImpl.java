package mx.com.aon.configurador.pantallas.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.configurador.pantallas.model.BackBoneResultVO;
import mx.com.aon.configurador.pantallas.model.ConjuntoPantallaVO;
import mx.com.aon.configurador.pantallas.model.MasterWrapperVO;
import mx.com.aon.configurador.pantallas.model.PantallaVO;
import mx.com.aon.configurador.pantallas.model.master.MasterVO;
import mx.com.aon.configurador.pantallas.service.ConfiguradorPantallaService;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManagerJdbcTemplateInvoke;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.OracleLobHandler;
import org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor;

/**
 * 
 * Implementacion de la Interfaz Manager utilizando JdbcTemplates
 * 
 * @author  ricardo.bautista
 * 
 */

public class ConfiguradorPantallaServiceJdbcTemplateImpl extends AbstractManagerJdbcTemplateInvoke implements ConfiguradorPantallaService {

    //private final transient Logger logger = Logger.getLogger(ConfiguradorPantallaServiceJdbcTemplateImpl.class);

	public String activarConjunto(Map<String, String> parameters) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ConjuntoPantallaVO> buscaConjuntos(Map<String, String> parameters) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList buscaConjuntos(Map<String, String> parameters, int start, int limit) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public BackBoneResultVO copiarConjunto(ConjuntoPantallaVO conjuntoCopiar) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public WrapperResultados copiarPantalla(String cdConjunto, String[] copiaPantallas, String cdElemento) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public String eliminarPantalla(String cdConjunto, String cdPantalla) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public MasterVO getAtributosPantalla(String master, String proceso, String tipo, String producto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public MasterWrapperVO getAtributosPropiedades(String master, String proceso, String tipo, String producto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public MasterWrapperVO getAtributosPropiedades(Map parameters) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public ConjuntoPantallaVO getConjunto(String cdConjunto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

//	public JSONMasterVO getJSONMaster(String cdTipoMaster, String cdProceso, String tipoMaster, String cdProducto, String claveSituacion) throws ApplicationException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public List<?> getMasters(String cdProceso) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList getMasters(String cdProceso, int start, int limit) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public TableModelExport getModel(Map<String, String> parameters) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PantallaVO getPantalla(String cdConjunto, String cdPantalla) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PantallaVO getPantallaFinal(Map<String, String> parameters) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<?> getPantallasConjunto(String cdConjunto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagedList getPantallasConjunto(String cdConjunto, int start, int limit) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public WrapperResultados guardaConjunto(ConjuntoPantallaVO conjunto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public BackBoneResultVO guardaPantalla(PantallaVO pantalla) throws ApplicationException {
    	
    	Map <String, Object> parameters = new HashMap<String, Object>();
        parameters.put("pv_cdconjunto_i", pantalla.getCdConjunto());
        parameters.put("pv_cdpantalla_io", pantalla.getCdPantalla());
        parameters.put("pv_dsnombrepantalla_i", pantalla.getNombrePantalla());
        parameters.put("pv_cdmaster_i", pantalla.getCdMaster());
        parameters.put("pv_dsdescpantalla_i", pantalla.getDescripcion());
        parameters.put("PV_CDSISROL_I", pantalla.getCdRol());
        
        //Crear parametros CLOB
        OracleLobHandler lobHandler = new OracleLobHandler();
        lobHandler.setNativeJdbcExtractor(new SimpleNativeJdbcExtractor());
        SqlLobValue clobDsArchivo = new SqlLobValue(pantalla.getDsArchivo(), lobHandler);
        parameters.put("pv_dsarchivo_i", clobDsArchivo);
        SqlLobValue clobDsArchivoSec = new SqlLobValue(pantalla.getDsArchivoSec(), lobHandler);
        parameters.put("PV_DSARCHIVOSEC_I", clobDsArchivoSec );
        SqlLobValue clobDsCampos = new SqlLobValue(pantalla.getDsCampos(), lobHandler);
        parameters.put("PV_DSCAMPOS_I", clobDsCampos);
        SqlLobValue clobDsLabel = new SqlLobValue(pantalla.getDsLabels(), lobHandler);
        parameters.put("pv_dslabel_i", clobDsLabel);
        
        WrapperResultados res = returnBackBoneInvoke(parameters, "GUARDA_PANTALLA_JDBCTEMPL");
    	
    	BackBoneResultVO backBoneResultVO = new BackBoneResultVO ();
    	backBoneResultVO.setMsgText(res.getMsgText());
    	backBoneResultVO.setOutParam(res.getResultado());
    	return backBoneResultVO;
	}
}