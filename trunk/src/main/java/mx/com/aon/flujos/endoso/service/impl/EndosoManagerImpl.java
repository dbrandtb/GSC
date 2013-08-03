/**
 * 
 */
package mx.com.aon.flujos.endoso.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.flujos.cotizacion.model.ObjetoCotizacionVO;
import mx.com.aon.flujos.endoso.model.DatosPolizaVO;
import mx.com.aon.flujos.endoso.model.PolizaCancelVO;
import mx.com.aon.flujos.endoso.model.TarificarVO;
import mx.com.aon.flujos.endoso.service.EndosoManager;
import mx.com.aon.kernel.model.MpoliagrVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.procesos.emision.model.DatosAdicionalesVO;
import mx.com.aon.procesos.emision.model.DatosRolVO;
import mx.com.aon.procesos.emision.model.EmisionVO;
import mx.com.aon.procesos.emision.model.PolizaDetVO;

import com.biosnet.ice.ext.elements.form.ComboControl;
import com.biosnet.ice.ext.elements.form.ExtElement;
import com.biosnet.ice.ext.elements.form.TextFieldControl;
import com.wittyconsulting.backbone.endpoint.Endpoint;
import com.wittyconsulting.backbone.exception.BackboneApplicationException;

/**
 * @author eflores
 * @date 28/08/2008
 *
 */
public class EndosoManagerImpl extends AbstractManager implements EndosoManager {
    /**
     * Busca las Polizas con Paginacion
     * @param params Parametros de busqueda
     * @start donde empieza la busqueda (parametro de paginacion)
     * @limit donde termina la busqueda (parametro de paginacion)
     */
    @SuppressWarnings("unchecked")
	public PagedList buscaPolizas(Map params, int start, int limit) throws ApplicationException{
        String endpointName = "OBTIENE_POLIZAS";
        
        return pagedBackBoneInvoke(params, endpointName, start, limit);
    }
    
    /**
     * Busca las Polizas sin Paginacion
     * @param params Parametros de busqueda
     */
    @SuppressWarnings("unchecked")
    public ArrayList<EmisionVO> buscaPolizas(Map params)throws ApplicationException{
        ArrayList<EmisionVO> emision = null;
        
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_POLIZAS");
            emision = (ArrayList<EmisionVO>) endpoint.invoke(params);
            
        } catch (Exception e) {
            logger.error("Backbone exception", e);
            //throw new ApplicationException("Error al recuperar los datos");

        }
        return (ArrayList<EmisionVO>) emision;
    }
    
    /**
     * Consulta el detalle de las polizas - Datos Generales de la Poliza
     * @param params Parametros de busqueda
     */
    @SuppressWarnings("unchecked")
    public List<PolizaDetVO> consultaPolizaDetalle(Map params) throws ApplicationException{
        List<PolizaDetVO> list = null;
        try{
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_POLIZA_DETALLE");
            list = (List<PolizaDetVO>) endpoint.invoke(params);
        }
        catch(BackboneApplicationException ex){
            logger.debug("consultaPolizaDetalle EXCEPTION:: " + ex);
        }
        
        return list;
    }
    
    /**
     * Consulta el Objeto Asegurable de la Poliza
     * @param params Parametros de busqueda
     * @start donde empieza la busqueda (parametro de paginacion)
     * @limit donde termina la busqueda (parametro de paginacion)
     */
    @SuppressWarnings("unchecked")
	public PagedList consultaObjetoAsegurable(Map params, int start, int limit) throws ApplicationException{
        String endpointName = "OBTIENE_OBJETO_ASEGURABLE";
        
        return pagedBackBoneInvoke(params, endpointName, start, limit);
    }
    
    /**
     * Consulta Funcion de Poliza
     * @param params Parametros de busqueda
     * @start donde empieza la busqueda (parametro de paginacion)
     * @limit donde termina la busqueda (parametro de paginacion)
     */
    @SuppressWarnings("unchecked")
	public PagedList consultaFuncionPoliza(Map params, int start, int limit) throws ApplicationException{
        String endpointName = "OBTIENE_FUNCION_POLIZA";
        
        return pagedBackBoneInvoke(params, endpointName, start, limit);
    }
    
    /**
     * Consulta los recibos de poliza
     * @param params Parametros de busqueda
     * @start donde empieza la busqueda (parametro de paginacion)
     * @limit donde termina la busqueda (parametro de paginacion)
     */
    @SuppressWarnings("unchecked")
    public PagedList consultaRecibosDetalle(Map params, int start, int limit) throws ApplicationException{
        String endpointName = "EMISION_OBTIENE_RECIBOS";
        return pagedBackBoneInvoke(params, endpointName, start, limit);
    }
    
    /**
     * Obtiene parte de la pantalla a pintar (atributos variables)
     * @param params Parametros de busqueda
     * @start donde empieza la busqueda (parametro de paginacion)
     * @limit donde termina la busqueda (parametro de paginacion)
     */
    @SuppressWarnings("unchecked")
    public String obtienePantalla(Map<String, String> params) throws ApplicationException{
        ArrayList<BaseObjectVO> list = null;
        
        try{
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_PANTALLA_POLIZA");
            list = (ArrayList<BaseObjectVO>) endpoint.invoke(params);
        }
        catch(BackboneApplicationException ex){
            logger.debug("obtienePantalla EXCEPTION:: " + ex);
        }
        
        BaseObjectVO base = list.get(0);
        
        return (String)base.getLabel();
    }
    
    /**
     * Consulta los datos adicionales
     * @param params Parametros de busqueda
     * @start donde empieza la busqueda (parametro de paginacion)
     * @limit donde termina la busqueda (parametro de paginacion)
     */
    @SuppressWarnings("unchecked")
    public List<DatosAdicionalesVO> obtieneDatosAdicionales(Map params) throws ApplicationException{
        List<DatosAdicionalesVO> list = null;
        try{
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_DATOS_ADICIONALES");
            list = (List<DatosAdicionalesVO>) endpoint.invoke(params);
        }
        catch(BackboneApplicationException ex){
            logger.debug("obtieneDatosAdicionales EXCEPTION:: " + ex);
        }
        
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public DatosRolVO getDatosRol(Map<String,String> params, String EndPointName) throws ApplicationException{
        DatosRolVO list = null;
        
        try{
            Endpoint endpoint = (Endpoint) endpoints.get(EndPointName);
            list = (DatosRolVO) endpoint.invoke(params);
        }
        catch(BackboneApplicationException ex){
            logger.debug("getDatosRol EXCEPTION:: " + ex);
        }
        
        return list;
        
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<TextFieldControl> getDatosRolExt(Map<String,String> params, String EndPointName) throws ApplicationException{
        ArrayList<TextFieldControl> list = null;
        
        try{
            logger.debug("params: " + params);
            logger.debug("EndPointName: " + EndPointName);
            Endpoint endpoint = (Endpoint) endpoints.get(EndPointName);
            list =  (ArrayList<TextFieldControl>) endpoint.invoke(params);
        }
        catch(BackboneApplicationException ex){
            logger.debug("getDatosRol EXCEPTION:: " + ex);
        }
        
        return list;
        
    }
    
    /**
     * @param params
     * @param EndPointName
     * @return ArrayList<MpoliagrVO>
     * @exception ApplicationException
     */
    @SuppressWarnings("unchecked")
	public ArrayList<MpoliagrVO> getAgrupador(Map<String,String> params, String EndPointName) throws ApplicationException{
        if (logger.isDebugEnabled()) {
            logger.debug("-> getAgrupador");
        }
        ArrayList<MpoliagrVO> list = null;
        
        try{
            logger.debug("params: " + params);
            logger.debug("EndPointName: " + EndPointName);
            Endpoint endpoint = (Endpoint) endpoints.get(EndPointName);
            list =  (ArrayList<MpoliagrVO>) endpoint.invoke(params);
        }
        catch(BackboneApplicationException ex){
            logger.debug("getAgrupador EXCEPTION:: " + ex);
        }
        
        return list;
        
    }
    
    public PagedList getPagedList(Map<String, String> params, String EndPointName, int start, int limit) throws ApplicationException{
        return pagedBackBoneInvoke(params, EndPointName, start, limit);
    }
    
    @SuppressWarnings("unchecked")
	public List getDatosRolExt(Map<String,String> params) throws ApplicationException{
		List list = null;
		
		try{
			logger.debug("*** PARAMS:: " + params);

			Endpoint endpoint = (Endpoint) endpoints.get("ENDOSOS_DATOS_ROL_EXT");
			list = (List)endpoint.invoke(params);
		}
		catch(BackboneApplicationException ex){
			logger.debug("getDatosRol EXCEPTION:: " + ex);
		}
		
		return list;
	}
    
    
    @SuppressWarnings("unchecked")
	public List getDatosDetallePolizaExt(Map<String,String> params) throws ApplicationException{
		List list = null;
		
		try{
			logger.debug("*** PARAMS:: " + params);

			Endpoint endpoint = (Endpoint) endpoints.get("ENDOSOS_OBTIENE_DATOS_ADICIONALES_POLIZA");
			list = (List)endpoint.invoke(params);
		}
		catch(BackboneApplicationException ex){
			logger.debug("getDatosDetallePolizaExt EXCEPTION:: " + ex);
		}
		
		return list;
	}
    
    
    /**
     * @param params
     * @throws BackboneApplicationException
     * @return endosos
     * 
     */
    @SuppressWarnings("unchecked")
	public List<ObjetoCotizacionVO> getEndosos(Map<String, String> params) throws ApplicationException{
    	ArrayList<ObjetoCotizacionVO> endosos=null;
    	try {
			Endpoint endpoint = (Endpoint)endpoints.get("OBTIENE_TARIFICACION_ENDOSOS_POLIZA");
			endosos=(ArrayList<ObjetoCotizacionVO>) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error retrieving data");
		}
    	return (List<ObjetoCotizacionVO>)endosos;
    }
    /**
     * @param parameters
     * @throws BackboneApplicationException
     * 
     */
    public void sacaEndoso(Map<String, String> parameters)throws ApplicationException{
    	Endpoint endpoint = (Endpoint)endpoints.get("BORRA_ENDOSO_RESUMEN_TARIFICACION");
    	try{
			endpoint.invoke(parameters);
		}catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error during transaction");
		}
 
    }
    
    /**
     * Metodo encargado de llamar el pl que se encarga del reversar
     * la maquilla en renovación
     * @param parameters
     * @throws BackboneApplicationException
     * 
     */
    public void reversaMaquilla(Map<String, String> parameters)throws ApplicationException{
    	Endpoint endpoint = (Endpoint)endpoints.get("REVERSA_MAQUILLA");
    	try{
			endpoint.invoke(parameters);
		}catch (BackboneApplicationException e) {
			logger.error("BackboneApplicationException: " + e.getMessage(), e);
			throw new ApplicationException("Error during transaction");
		}
 
    }
    
    public void guardaAgrupadores(Map<String, String> params)throws ApplicationException{
    	logger.debug(".:***GUARDA AGRUPADORES IMPL***:.");
    	try{
    		
    		 Endpoint endpointP = (Endpoint)endpoints.get("GUARDA_DATOS_ENDOSO_POLIAGR");
		     endpointP.invoke(params);
    		 
    		
    		Endpoint endpoint = (Endpoint)endpoints.get("GUARDA_DATOS_ENDOSO_TARJETA");
			endpoint.invoke(params);
		}catch (BackboneApplicationException ex) {
			logger.debug("guardaAgrupadores EXCEPTION:: " + ex);
		}
		logger.debug(".:***GUARDA AGRUPADORES IMPL TERMINA***:.");
    }
    
    public String getSuplLogico(Map<String, String> params)throws ApplicationException{
    	BaseObjectVO vo = null;
    	
    	try{
	    	Endpoint endpoint = (Endpoint)endpoints.get("ENDOSO_GENERA_SUPL_LOGICO");
	    	
	    	vo = (BaseObjectVO)endpoint.invoke(params);
    	}
    	catch(BackboneApplicationException ex){
    		logger.debug("getSuplLogico EXCEPTION:: " + ex);
    	}
    	
    	return vo.getLabel();
    }
    
    public String getSuplFisico(Map<String, String> params)throws ApplicationException{
    	BaseObjectVO vo = null;
    	
    	try{
	    	Endpoint endpoint = (Endpoint)endpoints.get("ENDOSO_GENERA_SUPL_FISICO");
	    	
	    	vo = (BaseObjectVO)endpoint.invoke(params);
    	}
    	catch(BackboneApplicationException ex){
    		logger.debug("getSuplLogico EXCEPTION:: " + ex);
    	}
    	
    	return vo.getLabel();
    }
    
    /**
     * 
     * @param params
     * @return
     * @throws ApplicationException
     */
    public BaseObjectVO actualizaFechas(Map<String, String> params)throws ApplicationException{
        BaseObjectVO vo = null;
        
        try{
            Endpoint endpoint = (Endpoint)endpoints.get("ENDOSO_ACTUALIZA_FECHAS");
            
            vo = (BaseObjectVO)endpoint.invoke(params);
        }
        catch(BackboneApplicationException ex){
            logger.debug("actualizaFechas EXCEPTION:: " + ex);
        }
        
        return vo;
    }
    
    /**
     * Metodo que se encarga de editar los datos de endoso.
     */
    public void editarDatosEndoso(Map<String, String> params)
            throws ApplicationException {
        Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_DATOS_ENDOSO");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error editando datos de endoso" + e);
        }
    }
    
    /**
     * Metodo que se encarga de editar los datos de la tarjeta.
     */
    public void editarDatosEndosoTarjeta(Map<String, String> params)
            throws ApplicationException {
        Endpoint endpoint = (Endpoint) endpoints.get("GUARDA_DATOS_ENDOSO_TARJETA");
        try {
            endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            throw new ApplicationException("Error editando datos de forma de pago endoso" + e);
        }
    }
    
    /**
     * getExtElements
     * @param params
     * @param EndPointName
     * @return ArrayList<ExtElement>
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
	public ArrayList<ExtElement> getExtElements(Map<String,String> params, String EndPointName) throws ApplicationException{
        ArrayList<ExtElement> list = null;
        
        try{
            logger.debug("params: " + params);
            logger.debug("EndPointName: " + EndPointName);
            Endpoint endpoint = (Endpoint) endpoints.get(EndPointName);
            list =  (ArrayList<ExtElement>) endpoint.invoke(params);
        }
        catch(BackboneApplicationException ex){
            logger.debug("getDatosRol EXCEPTION:: " + ex);
        }
        
        return list;
        
    }
    
    /**
     * getComboControl
     * @param params
     * @param EndPointName
     * @return ArrayList<ComboControl>
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
	public ArrayList<ComboControl> getComboControl(Map<String,String> params, String EndPointName) throws ApplicationException{
        ArrayList<ComboControl> list = null;
        
        try{
            logger.debug("params: " + params);
            logger.debug("EndPointName: " + EndPointName);
            Endpoint endpoint = (Endpoint) endpoints.get(EndPointName);
            list =  (ArrayList<ComboControl>) endpoint.invoke(params);
        }
        catch(BackboneApplicationException ex){
            logger.debug("getDatosRol EXCEPTION:: " + ex);
        }
        
        return list;
        
    }
    
    
    @SuppressWarnings("unchecked")
	public List<DatosPolizaVO> obtieneDatosPoliza(Map<String,String> params) throws ApplicationException {
    	
    	List<DatosPolizaVO> datosPolizaList = null;
        try{
            logger.debug("params: " + params);
            Endpoint endpoint = (Endpoint) endpoints.get("ENDOSOS_OBTIENE_DATOS_POLIZA");
            datosPolizaList =  (ArrayList<DatosPolizaVO>) endpoint.invoke(params);
        }
        catch(BackboneApplicationException ex){
            logger.debug("obtieneDatosPoliza EXCEPTION:: " + ex);
        }
        return datosPolizaList;
    }
    
    public PolizaCancelVO validaPolizaCancel(Map<String,String> params) throws ApplicationException {
    	
    	PolizaCancelVO polizaCancelVO = null;
        try{
            logger.debug("params: " + params);
            Endpoint endpoint = (Endpoint) endpoints.get("ENDOSOS_VAL_POLIZA_CANCEL");
            polizaCancelVO =  (PolizaCancelVO) endpoint.invoke(params);
        }
        catch(BackboneApplicationException ex){
            logger.debug("validaPolizaCancel EXCEPTION:: " + ex);
        }
        return polizaCancelVO;
    }
    
    public BaseObjectVO bajaInciso(Map<String,String> params) throws ApplicationException {
    	
    	BaseObjectVO baseObjectVO = null;
        try{
            logger.debug("params: " + params);
            Endpoint endpoint = (Endpoint) endpoints.get("ENDOSOS_BAJA_INCISO");
            baseObjectVO =  (BaseObjectVO) endpoint.invoke(params);
        }
        catch(BackboneApplicationException ex){
            logger.debug("bajaInciso EXCEPTION:: " + ex);
        }
        return baseObjectVO;
    }

    
    public String getEndPoint(Map<String, Object> params, String EndPointName) throws ApplicationException{
    	String msg = null;
    	WrapperResultados res = new WrapperResultados();
    	try{
    		Endpoint endpoint = (Endpoint) endpoints.get(EndPointName);
    		res =  (WrapperResultados) endpoint.invoke(params);
    		msg = res.getMsgText();
    	}catch(Exception e){
    		logger.debug("getEndPoint EXCEPTION :"+e);
    	}
    	return msg;
    }

    
    /**
     * Obtiene descripcion del valor en la tabla de apoyo correspondiente
     * @param tabla
     * @param value
     * @return String
     * @throws ApplicationException.
     */
    @SuppressWarnings("unchecked")
    public String getVariableAtributoCoberturas(String tabla, String value) throws ApplicationException {
        Map params = new HashMap<String, String>();
        params.put("TABLA", tabla);
        params.put("CLAVE", value);
        
        BaseObjectVO variableAtrib = null; 
        try{
            Endpoint endpoint= (Endpoint) endpoints.get("OBTIENE_VALOR_ATRIBUTO_COBERTURA");
            variableAtrib=  (BaseObjectVO) endpoint.invoke(params);
        }catch (BackboneApplicationException bae) {
            throw new ApplicationException("Error retrieving data");
        }
        return variableAtrib.getLabel();
    }

    
    /**
     * @param cdramo
     * @throws BackboneApplicationException
     * @thows {@link ApplicationException} 
     * @return (List<ExtElement>) items
     */
    @SuppressWarnings("unchecked")  
    public List<ExtElement> getItems(String clave, String cdUnieco, String cdRamo, String estado,
            String nmPoliza, String nmSituac, String nmObjeto) throws ApplicationException {
        Map params= new HashMap<String, String>();
        params.put("clave", clave);
        params.put("cdUnieco", cdUnieco);
        params.put("cdRamo", cdRamo);
        params.put("estado", estado);
        params.put("nmPoliza", nmPoliza);
        params.put("nmSituac", nmSituac);
        params.put("nmObjeto", nmObjeto);
        List<ExtElement> itemLista = null;
        
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_TIPOS_ITEMS_ATRIBUTOS_ACCESORIOS");
            itemLista = (List<ExtElement>) endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            e.getMessage();
            throw new ApplicationException("Error retrieving data");
        }
        
        return (List<ExtElement>) itemLista;
    
    }
    
    @SuppressWarnings("unchecked")
    public List<ComboControl> getCombos(String clave, String cdUnieco, String cdRamo, String estado,
            String nmPoliza, String nmSituac, String nmObjeto) throws ApplicationException {
        Map params= new HashMap<String, String>();
        params.put("clave", clave);
        params.put("cdUnieco", cdUnieco);
        params.put("cdRamo", cdRamo);
        params.put("estado", estado);
        params.put("nmPoliza", nmPoliza);
        params.put("nmSituac", nmSituac);
        params.put("nmObjeto", nmObjeto);
        List<ComboControl> combosList = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_COMBOS_ITEMS_ATRIBUTOS_ACCESORIOS");
            combosList = (List<ComboControl>) endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            e.getMessage();
            throw new ApplicationException("Error retrieving data");
        }
        return (List<ComboControl>) combosList;
    }
    
    /**
     * @param cdramo
     * @param cdtipsit
     * @throws BackboneApplicationException
     * @return tipos
     */
    @SuppressWarnings("unchecked")
    public List<BaseObjectVO> getTipos(String cdramo, String cdtipsit)throws ApplicationException {
        Map params= new HashMap<String, String>();
        params.put("cdRamo", cdramo);
        params.put("cdTipsit", cdtipsit);
        List<BaseObjectVO> tipos= null;
        try {
            Endpoint endpoint = (Endpoint)endpoints.get("OBTIENE_LISTA_TIPO_PRODUCTO_ACCESORIOS");
            tipos= (List<BaseObjectVO>) endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            e.getMessage();
            throw new  ApplicationException("Error retrieving data list");
        }
        return (List<BaseObjectVO>)tipos;
    }
    
    @SuppressWarnings("unchecked")
	public List getDatosRolExt2(Map<String,String> params) throws ApplicationException{
		List list = null;
		
		try{
			logger.debug("*** PARAMS:: " + params);
			Endpoint endpoint = (Endpoint) endpoints.get("ENDOSOS_OBTIENE_DATOS_ROL");
			list = (List)endpoint.invoke(params);
		}
		catch(BackboneApplicationException ex){
			logger.debug("getDatosRol2 EXCEPTION:: " + ex);
		}
		
		return list;
	}
    
    @SuppressWarnings("unchecked")
	public List<ExtElement> getItems(Map<String,String> params, String endPointName) throws ApplicationException{
        List<ExtElement> itemLista = null;
        
        try {
            Endpoint endpoint = (Endpoint) endpoints.get(endPointName);
            itemLista = (List<ExtElement>) endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            e.getMessage();
            throw new ApplicationException("Error retrieving data");
        }
        
        return (List<ExtElement>) itemLista;
    }
    
    @SuppressWarnings("unchecked")
	public List<ComboControl> getCombos(Map<String,String> params, String endPointName) throws ApplicationException{
        List<ComboControl> combosList = null;
        try {
            Endpoint endpoint = (Endpoint) endpoints.get(endPointName);
            combosList = (List<ComboControl>) endpoint.invoke(params);
        } catch (BackboneApplicationException e) {
            e.getMessage();
            throw new ApplicationException("Error retrieving data");
        }
        return (List<ComboControl>) combosList;
    }
    
    /**
     * @param params
     * @throws BackboneApplicationException
     * @return endosos
     * 
     */
    @SuppressWarnings("unchecked")
	public List<ObjetoCotizacionVO> detalleTarificar(Map<String, String> params, String endPointName) throws ApplicationException{
    	ArrayList<ObjetoCotizacionVO> endosos=null;
    	try {
			Endpoint endpoint = (Endpoint)endpoints.get(endPointName);
			endosos=(ArrayList<ObjetoCotizacionVO>) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error retrieving data");
		}
    	return (List<ObjetoCotizacionVO>)endosos;
    }
    
    @SuppressWarnings("unchecked")
	public WrapperResultados getResultadoWrapper(Map<String, String> params, String endPointName) throws ApplicationException{
    	WrapperResultados resultado = null;
    	try {
			Endpoint endpoint = (Endpoint)endpoints.get(endPointName);
			resultado = (WrapperResultados) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error retrieving data");
		}
    	return (WrapperResultados)resultado;
    }

    @SuppressWarnings("unchecked")
	public TarificarVO getResultadoTarificar(Map<String, String> params, String endPointName) throws ApplicationException{
    	TarificarVO resultado = null;
    	try {
			Endpoint endpoint = (Endpoint)endpoints.get(endPointName);
			resultado = (TarificarVO) endpoint.invoke(params);
		} catch (BackboneApplicationException e) {
			e.getMessage();
			throw new ApplicationException("Error retrieving data");
		}
    	return (TarificarVO)resultado;
    }
    
    public BaseObjectVO obtieneNmsuplem(Map<String,String> parameters)throws ApplicationException {
    	BaseObjectVO nmsuplem;
    		try {
    			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_NMSUPLEM_ENDOSO");
    			nmsuplem=(BaseObjectVO)endpoint.invoke(parameters);
    		} catch (BackboneApplicationException bae) {
    			logger.error("Error en ... OBTIENE_NMSUPLEM_ENDOSO", bae);
    			throw new ApplicationException("Error en ... OBTIENE_NMSUPLEM_ENDOSO");
    		}
    	return nmsuplem;
    }
    
    public String obtieneMensajeEndoso(String msgId)throws ApplicationException {
    	WrapperResultados resultado = null;
    	
    	Map<String,String> params = new HashMap();
        params.put("pv_msg_id_i", 	msgId);
        params.put("pv_log_i", "");
        params.put("pv_cdusuario_i", "");
        params.put("pv_dsprograma_i", "");
        
    		try {
    			Endpoint endpoint = (Endpoint) endpoints.get("OBTIENE_MENSAJE");
    			resultado = (WrapperResultados)endpoint.invoke(params);
    		} catch (BackboneApplicationException bae) {
    			logger.error("Error en ... OBTIENE_MENSAJE_ENDOSO", bae);
    			throw new ApplicationException("Error en ... OBTIENE_MENSAJE_ENDOSO");
    		}
    		
    		if(resultado!=null){
    		return resultado.getMsgText();
    		}
    		else return null;
    }
}