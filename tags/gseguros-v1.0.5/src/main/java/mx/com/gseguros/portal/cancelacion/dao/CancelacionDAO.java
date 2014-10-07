package mx.com.gseguros.portal.cancelacion.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.general.model.PolizaVO;

public interface CancelacionDAO
{
    public List<Map<String,String>> buscarPolizas             (Map<String,String> params) throws Exception;
    public Map<String,String>       obtenerDetalleCancelacion (Map<String,String> params) throws Exception;
    public List<Map<String,String>> obtenerPolizasCandidatas  (Map<String,String> params) throws Exception;
    public void                     seleccionaPolizas         (Map<String,Object> params) throws Exception;
    public String                   cancelaPoliza             (Map<String,String> params) throws Exception;
    public void                     seleccionaPolizaUnica     (Map<String,Object> params) throws Exception;
    public void                     actualizarTagrucan        (Map<String,String> params) throws Exception;
    public void                     cancelacionMasiva         (Map<String,String> params) throws Exception;
    /**
	 * PKG_CONSULTA.P_IMP_DOC_CANCELACION
	 * @return nmsolici,nmsituac,descripc,descripl,ntramite,nmsuplem
	 */
	public List<Map<String,String>> reimprimeDocumentos       (Map<String,String> params) throws Exception;
	public ArrayList<PolizaVO> obtienePolizasCancelacionMasiva(Map<String,String> params) throws Exception;
}