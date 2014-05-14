package mx.com.gseguros.interceptors;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mx.com.gseguros.utils.Utilerias;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Interceptor que redirige la petici�n dependiendo del dispositivo origen
 *
 */
public class DeviceInterceptor implements Interceptor {

	private static final long serialVersionUID = 4074812194873811352L;

	protected final transient Logger logger = Logger.getLogger(DeviceInterceptor.class);
	
	@Override
	public void init() {
	}

	private static final String RESULT_CODE_SUFFIX_MOBILE = "mobile";
	private static final String REQUEST_HEADER_ACCEPT = "Accept";
	private static final String[] MOBILE_BROWSER_UAS = {"iPhone OS","Android","BlackBerry","Windows Phone"};
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

        ActionContext context = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
        logger.debug("request=" + request);
        
        HttpServletRequest request2 = ServletActionContext.getRequest();
        logger.debug("request2=" + request2);
		
		Map session = invocation.getInvocationContext().getSession();
		if(session != null) {
			String ua = request.getHeader("User-Agent").toLowerCase();
			logger.debug("user agent: "+ua);
			if(Utilerias.esSesionMovil(ua)) {
			    logger.debug("movil");
			    session.put("ES_MOVIL", true);
			} else {
			    logger.debug("desktop");
			    session.put("ES_MOVIL", false);
			}	
		}
		
/*
	    invocation.addPreResultListener(new PreResultListener() {
	        public void beforeResult(ActionInvocation invocation, String resultCode) {

	            // check if a wireless version of the page exists
	            // by looking for a wireless action mapping in the struts.xml
	            Map results = invocation.getProxy().getConfig().getResults();
	            System.out.println("Results:"+results.toString());
	            if(!results.containsKey(resultCode + RESULT_CODE_SUFFIX_MOBILE)) {

	                return;
	            }

	            // send to mobile version if mobile browser is used
	            final String acceptHeader = ServletActionContext.getRequest().getHeader(REQUEST_HEADER_ACCEPT);

	            //Get User Agent String
	            String userAgent = ServletActionContext.getRequest().getHeader("User-Agent");
	            System.out.println("UA: "+userAgent);

	            //Boolean to indicate whether to show mobile version
	            boolean showMobileVersion = false;

	            //Run through each entry in the list of browsers
	            for(String ua : MOBILE_BROWSER_UAS){
	                if(userAgent.toLowerCase().matches(".*"+ua.toLowerCase()+".*")){
	                    showMobileVersion = true;
	                }
	            }

	            if(showMobileVersion) {
	                invocation.setResultCode(resultCode + RESULT_CODE_SUFFIX_MOBILE);
	            }
	        }
	    });
*/
	    return invocation.invoke();
	}
	
	@Override
	public void destroy() {
	}
	
}