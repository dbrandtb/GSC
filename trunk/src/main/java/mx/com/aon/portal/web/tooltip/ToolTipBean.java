package mx.com.aon.portal.web.tooltip;

import mx.com.aon.portal.service.MecanismoDeTooltipManager;
import mx.com.aon.portal.model.ItemVO;
import mx.com.aon.portal.model.MecanismoDeTooltipVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.ConnectionCallInterceptor;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Aug 25, 2008
 * Time: 4:41:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ToolTipBean {

    @SuppressWarnings("unused")
    private Logger logger = Logger.getLogger(ToolTipBean.class);

    public static String DEFAULT_LANGUAGE = "1";
    public static String DEFAULT_CDTITLE = "4";

    @SuppressWarnings("unchecked")
	private Map tooltipsMapPages = new HashMap();


    private MecanismoDeTooltipManager mecanismoDeTooltipManager;


    private String cdTitle;

    public void setMecanismoDeTooltipManager(MecanismoDeTooltipManager mecanismoDeTooltipManager) {
        this.mecanismoDeTooltipManager = mecanismoDeTooltipManager;
    }

    @SuppressWarnings("unchecked")
	public String getHelpMap(String pageCode, String langCode) {

        String key = pageCode+langCode;
        String helpMap = (String)tooltipsMapPages.get(key);
        logger.debug("Ejecutando getHelpMap()");
        if (helpMap == null ) {
          tooltipsMapPages.put(key,buildHelpMap(pageCode,langCode));
        }

        return (String)tooltipsMapPages.get(key);

    }


    @SuppressWarnings("unchecked")
	public String getHelpMap(String pageCode) {

        ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
		UserVO userVo = tl.get();
        String langCode = userVo.getIdioma().getValue();

        if (langCode == null || langCode.equals("")) langCode = DEFAULT_LANGUAGE;
        
        if (cdTitle == null || cdTitle.equals("")) cdTitle = DEFAULT_CDTITLE;
        /*if (userVo != null && userVo.getIdioma()!=null) {
            langCode = userVo.getIdioma().getValue();
        }*/
        String key = pageCode+langCode;


        String helpMap = (String)tooltipsMapPages.get(key);
        logger.debug("Ejecutando getHelpMap()");
        if (helpMap == null ) {
          tooltipsMapPages.put(key,buildHelpMap(pageCode,langCode));
        }

        /**
         * Obtiene los mensajes informativos
         */

        String keyMessages = cdTitle + "_" + langCode;
        String gb_Messages = (String)tooltipsMapPages.get(keyMessages);
        if (gb_Messages == null) {
        	tooltipsMapPages.put(keyMessages, buildMapGB_Messages(cdTitle, langCode));
        }
        StringBuffer sb = new StringBuffer();
        sb.append((String)tooltipsMapPages.get(keyMessages));
        sb.append((String)tooltipsMapPages.get(key));
        //return (String)tooltipsMapPages.get(key);
        return sb.toString();

    }

    @SuppressWarnings("unchecked")
	private String buildHelpMap(String pageCode,String langCode) {
        logger.debug("buildheloMap "+ "pageCode " + pageCode + " langCode "+ langCode);
        //Si el langCode no es numérico, entonces retorna una cadena vacía para evitar errores de conversión
        try {
        	int i = Integer.parseInt(langCode);
        } catch (NumberFormatException nfe) {
        	return "";
        }
        try {
            List tooltips = mecanismoDeTooltipManager.getToolTipsForPage(pageCode,langCode);
            if (tooltips != null) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < tooltips.size(); i++) {
                    MecanismoDeTooltipVO mecanismoDeTooltipVO = (MecanismoDeTooltipVO) tooltips.get(i);
                    stringBuffer.append("helpMap.put('").append(mecanismoDeTooltipVO.getNbObjeto()).append("', {");
                    stringBuffer.append("fieldLabel: '").append(mecanismoDeTooltipVO.getNbEtiqueta()).append("', ");
                    stringBuffer.append("ayuda: '").append(mecanismoDeTooltipVO.getDsAyuda()).append("', ");
                    stringBuffer.append("tooltip: '").append(mecanismoDeTooltipVO.getDsTooltip()).append("'});\n");
                    
                }
                return stringBuffer.toString();
            } else {
                return "";
            }
        } catch (ApplicationException ex) {
            logger.error("Error al recuperar la lista de tooltips asociado al pageCode "+pageCode +" , langCode "+ langCode, ex);
            return "";
        }
    }

    @SuppressWarnings("unchecked")
	private String buildMapGB_Messages (String cdTitle, String langCode) {
    	logger.debug("buildGB_Messages cdTitle: " + cdTitle + " langCode: " + langCode);
    	try {
    		List gbMessages = mecanismoDeTooltipManager.getGB_Messages(cdTitle, langCode);
    		if (gbMessages != null) {
    			StringBuffer sb = new StringBuffer();
    			for (int i=0; i<gbMessages.size(); i++) {
    				ItemVO itemVO = (ItemVO)gbMessages.get(i);
    				sb.append("helpMap.put('").append(itemVO.getId()).append("', {");
    				sb.append("fieldLabel: '").append(itemVO.getTexto()).append("'});\n");
    			}
    			return sb.toString();
    		}
    		return "";
    	} catch (ApplicationException ae) {
    		logger.error("Error al recuperar gb_messages: " + ae.getMessage());
        	return "";
    	}
    }

    public void removeKeyMap (String pageCode) {
        ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
        UserVO userVo = tl.get();
        String langCode = userVo.getIdioma().getValue();
        if (langCode == null || langCode.equals("")) langCode = DEFAULT_LANGUAGE;

        String key = pageCode + langCode;
        String helpMap = (String)tooltipsMapPages.get(key);
        if (helpMap != null) {
         tooltipsMapPages.remove(key);
        }
    }
    
    public void removeKeyMessag (String pageCode, String langCode) {
       // ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser(); 
        //UserVO userVo = tl.get();
        //String langCode = userVo.getIdioma().getValue(); 
        if (langCode == null || langCode.equals("")) langCode = DEFAULT_LANGUAGE;

        String key = pageCode + "_" + langCode;
        String helpMap = (String)tooltipsMapPages.get(key);
        if (helpMap != null) {
         tooltipsMapPages.remove(key);
        }
    }
    
	public String getCdTitle() {
		return cdTitle;
	}

	public void setCdTitle(String cdTitle) {
		this.cdTitle = cdTitle;
	}
}


