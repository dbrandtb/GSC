/**
 * 
 */
package mx.com.aon.flujos.ordentrabajo.web;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Leopoldo
 *
 */
public abstract class PrincipalOrdenTrabajoAction extends ActionSupport implements Preparable, RequestAware, SessionAware {

   /**
    * 
    */
   protected Map request;
   
   /**
    * 
    */
   protected Map session;
   
   
   /**
    * 
    */
   public void setRequest(Map request) {
       this.request = request;
   }

   /**
    * 
    */
   public void setSession(Map session) {
       this.session = session;
   }

   /**
    * 
    */
   public void prepare() throws Exception {
      
   }
   

}
