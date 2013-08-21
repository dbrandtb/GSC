/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.aon.portal.web;

import java.util.Map;
import mx.com.aon.core.web.PrincipalCoreAction;
import org.apache.struts2.interceptor.CookiesAware;

/**
 *
 * @author Jair
 */
public class DinosaurioAction extends PrincipalCoreAction implements CookiesAware{

    private Map<String, String> cookiesMap;
    private Dinosaurio dino;
    private String llave;
    
    public String jsoo()
    {
        System.out.println("### dino "+(dino!=null?dino:"null"));
        System.out.println("### llave "+(llave!=null?llave:"null"));
        return SUCCESS;
    }
    
    public Map<String, String> getCookiesMap() {
        return cookiesMap;
    }
    
    public void setCookiesMap(Map<String, String> map) {
        this.cookiesMap=map;
    }

    public Dinosaurio getDino() {
        return dino;
    }

    public void setDino(Dinosaurio dino) {
        this.dino = dino;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }
    
}
