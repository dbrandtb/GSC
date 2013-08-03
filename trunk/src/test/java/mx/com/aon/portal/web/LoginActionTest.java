package mx.com.aon.portal.web;

import mx.com.aon.portal.web.LoginAction;
import junit.framework.TestCase;

import com.opensymphony.xwork2.Action;

/**
 * 
 */
public class LoginActionTest extends TestCase {
    
    public void testIndexAction() throws Exception {
        LoginAction action = new LoginAction();
        String result = action.execute();
        assertEquals(Action.SUCCESS, result);
    }
}
