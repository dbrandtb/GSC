/**
 * 
 */
package mx.com.aon.flujos.cotizacion.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mx.com.aon.flujos.cotizacion.service.CotizacionPrincipalManager;
import mx.com.aon.flujos.cotizacion.service.CotizacionService;
import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.biosnet.ice.ext.elements.form.TextFieldControl;

/**
 * 
 * Clase Action para el control y visualizacion de la pantalla de filtro
 * 
 * @author aurora.lozada
 * 
 */
public class CotizarAction extends PrincipalCotizacionAction {

    /**
     * 
     */
    private static final long serialVersionUID = 6637363136914380412L;

    private JSONArray registroList = new JSONArray();

    private boolean success;

    private String screenDetails;
    
    @SuppressWarnings("unused")
	private CotizacionService cotizacionManager;
    private CotizacionPrincipalManager cotizacionManagerJdbcTemplate;

    @Override
    public void prepare() throws Exception {

    }

    /**
     * Metodo que sube a sesion los parametros obtenidos la pantalla de filtro
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String cotizar() throws Exception {
        boolean isDebugueable = logger.isDebugEnabled();

        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### LLegando a metodo cotizar......");
            logger.debug("######################################################");
        }

        HttpServletRequest servletReq = ServletActionContext.getRequest();

        Map params = servletReq.getParameterMap();

        Object ob = new Object();
        List<TextFieldControl> textFields = new ArrayList<TextFieldControl>();
        TextFieldControl tfc = null;
        for (Object key : params.keySet()) {
        	logger.debug(" key is " + key + " -- value is " + params.get(key.toString()).getClass());            
        	logger.debug("param : " + params.get(key.toString()));
        	tfc = new TextFieldControl();
            String llave = (String)key;
            tfc.setAllowBlank(true);
            if(llave.contains("label")){
            	String descripcion = llave.replace("label", "");
            	tfc.setDisabled(true);
            	tfc.setFieldLabel(descripcion);
            	tfc.setLabelSeparator(" ");
            	tfc.setName(descripcion);
            	tfc.setXtype("textfield");
            	tfc.setWidth(150);
            
            	ob = params.get(key);
            	if (ob instanceof String[]) {
            		logger.debug("Array de Strings");

            		for (String s : (String[]) ob) {
            			logger.debug("@@@@ s is " + s);
            			tfc.setValue(s);
            		}

            	} else if (ob instanceof String) {
            		logger.debug("Simple String");
            		tfc.setValue((String)ob);
            	} else {
            		logger.debug("class is " + ob.getClass());
            	}
            	textFields.add(tfc);
            }
        }

        Map<String, String> parameters = new HashMap<String, String>();

        String key = null;
        Enumeration<?> enumeration = servletReq.getParameterNames();

        while (enumeration.hasMoreElements()) {
            key = enumeration.nextElement().toString();
            logger.debug("### entering key is-- " + key);
            logger.debug("### value is---" + servletReq.getParameter(key));
            parameters.put(key, servletReq.getParameter(key));

        }
        logger.debug("parameter size is " + parameters.size());
        session.put("COTIZACION_INPUT", parameters);
        logger.debug("COTIZACION_INPUT" + parameters);
        session.put("COTIZAR_ACTION", 'S');
        logger.debug("COTIZAR_ACTION=" + session.get("COTIZAR_ACTION"));
        if ( session.containsKey( "DETALLE_COTIZACION" ) ) {
        	session.remove( "DETALLE_COTIZACION" );
        	logger.debug("!!! Se elimino de session DETALLE_COTIZACION. Existe? = " + session.containsKey( "DETALLE_COTIZACION" ) );
        }

        success = true;
        return SUCCESS;
    }

    /**
     * Metodo que obtiene los parametros en sesion de la pantalla de filtro
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtieneDataCotizar() throws Exception {

        boolean isDebugueable = logger.isDebugEnabled();

        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### LLegando a metodo get data cotizar HHH...");
            logger.debug("######################################################");
        }
        Map<String, String> parametros = new HashMap<String, String>();

        parametros = (Map<String, String>) session.get("COTIZACION_INPUT");

        if (parametros == null) {
            logger.debug("### parametros en null get data cotizar---");
            success = false;
        } else {

            logger.debug("parametros size is--------- " + parametros.size());

            try {
                registroList.add(parametros);
                logger.debug("### registroList---" + registroList);

            } catch (Exception e) {
                logger.error("Exception en JSONObject...", e);
            }

            success = true;

        }

        return SUCCESS;
    }

    /**
     * Metodo que redirecciona a la pantalla de filtro del ejemplo con varios componentes de EXT JS
     * 
     * @return Cadena SUCCESS
     */
    public String entrarEjemplo() throws Exception {
        boolean isDebugueable = logger.isDebugEnabled();

        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### METODO entrarEjemplo ...");
            logger.debug("######################################################");
        }

        return INPUT;
    }

    /**
     * Metodo que sube a sesion los parametros de la pantalla de filtro del ejemplo con varios componentes de EXT JS
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String generaDataEjemplo() throws Exception {
        boolean isDebugueable = logger.isDebugEnabled();

        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### METODO generaDataEjemplo ...");
            logger.debug("######################################################");
        }

        HttpServletRequest servletReq = ServletActionContext.getRequest();

        Map params = servletReq.getParameterMap();

        Object ob = null;

        for (Object key : params.keySet()) {
            logger.debug(" key is " + key + " -- value is " + params.get(key.toString()).getClass());
            ob = params.get(key);

            if (ob instanceof String[]) {
                logger.debug("Array de Strings");

                for (String s : (String[]) ob) {
                    logger.debug("@@@@ s is " + s);
                }

            } else if (ob instanceof String) {
                logger.debug("Simple String");
            } else {
                logger.debug("class is " + ob.getClass());
            }

        }

        Map<String, String> parameters1 = new HashMap<String, String>();

        String key = null;
        Enumeration<?> enumeration = servletReq.getParameterNames();

        while (enumeration.hasMoreElements()) {
            key = enumeration.nextElement().toString();
            logger.debug("### entering key is--- " + key);
            logger.debug("### value is---" + servletReq.getParameter(key));
            parameters1.put(key, servletReq.getParameter(key));
        }

        logger.debug("parameter size is " + parameters1.size());

        session.put("EJEMPLO_INPUT", parameters1);

        success = true;
        return SUCCESS;
    }

    /**
     * Metodo que obtiene los parametros de la pantalla de filtro en sesion del ejemplo con varios componentes de EXT JS
     * 
     * @return Cadena SUCCESS
     */
    @SuppressWarnings("unchecked")
    public String obtieneDataEjemplo() throws Exception {

        boolean isDebugueable = logger.isDebugEnabled();

        if (isDebugueable) {
            logger.debug("######################################################");
            logger.debug("### LLegando a metodo getDataEjemplo...");
            logger.debug("######################################################");
        }
        Map<String, String> parametros = new HashMap<String, String>();

        parametros = (Map<String, String>) session.get("EJEMPLO_INPUT");

        if (parametros == null) {
            logger.debug("### parametros null---");
           	success = false;
        } else {

            logger.debug("parametros size is--------- " + parametros.size());

            try {
                registroList.add(parametros);
                logger.debug("### registroList---" + registroList);

            } catch (Exception e) {
                logger.error("Exception en JSONObject...", e);
            }

            success = true;

        }

        return SUCCESS;
    }

    /**
     * Metodo que limpia elementos en sesión de la aplicación.
     * 
     * @return
     * @throws Exception
     */
    public String limpiar() throws Exception {
        boolean isDebug = logger.isDebugEnabled();
        if (isDebug) {
            logger.debug("#######Enterintg into method LIMPIAR SESION...");
        }

        session.remove("EJEMPLO_INPUT");
        //TODO Se comento la siguiente linea para que al regresar a la pantalla
        //no se pierda la información seleccionada.
        //session.remove("COTIZACION_INPUT");

        return INPUT;
    }
    
    
    public void setCotizacionManager(CotizacionService cotizacionManager) {
		this.cotizacionManager = cotizacionManager;
	}

	public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getScreenDetails() {
        return screenDetails;
    }

    public void setScreenDetails(String screenDetails) {
        this.screenDetails = screenDetails;
    }

    /**
     * @return the registroList
     */
    public JSONArray getRegistroList() {
        return registroList;
    }

    /**
     * @param registroList the registroList to set
     */
    public void setRegistroList(JSONArray registroList) {
        this.registroList = registroList;
    }

	public void setCotizacionManagerJdbcTemplate(
			CotizacionPrincipalManager cotizacionManagerJdbcTemplate) {
		this.cotizacionManagerJdbcTemplate = cotizacionManagerJdbcTemplate;
	}

}
