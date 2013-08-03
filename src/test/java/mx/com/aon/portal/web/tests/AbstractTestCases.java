package mx.com.aon.portal.web.tests;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.apache.log4j.Logger;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.ConnectionCallInterceptor;

public abstract class AbstractTestCases extends AbstractDependencyInjectionSpringContextTests {

	public AbstractTestCases() {
		super();
		super.setPopulateProtectedVariables(true);		
        setContextUserUser();
		
	}

	public AbstractTestCases(String name) {
		super(name);
		super.setPopulateProtectedVariables(true);
        setContextUserUser();
    }
	
    /** Devuelve la configurcion de Spring */
    protected String[] getConfigLocations() {
        return new String[] {"classpath*:/spring/config/applicationContext*.xml",
                "classpath*:/spring/config/daoContext.xml" ,
                "classpath*:/spring/config/action-servlet*.xml" ,
        		"classpath*:/backbone/config/backbone*.xml"};
    }
	

    private void setContextUserUser() {
        UserVO userVO = new UserVO();
        userVO.setFormatDate("dd/MM/yyyy");

        ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
        tl.set(userVO);

    }

    public static Logger logger = Logger.getLogger(AbstractTestCases.class);
}
