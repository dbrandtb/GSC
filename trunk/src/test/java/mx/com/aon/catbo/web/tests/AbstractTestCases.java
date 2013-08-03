package mx.com.aon.catbo.web.tests;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.apache.log4j.Logger;

public abstract class AbstractTestCases extends AbstractDependencyInjectionSpringContextTests {

	public AbstractTestCases() {
		super();
		super.setPopulateProtectedVariables(true);		
		
	}

	public AbstractTestCases(String name) {
		super(name);
		super.setPopulateProtectedVariables(true);
	}
	
    /** Devuelve la configurcion de Spring */
    protected String[] getConfigLocations() {
        return new String[] {"classpath*:/spring/config/applicationContext*.xml",
                "classpath*:/spring/config/action-servlet*.xml" ,
                "classpath*:/spring/config/daoContext*.xml" ,
                "classpath*:/backbone/config/backbone*.xml"};
    }
	

	public static Logger logger = Logger.getLogger(AbstractTestCases.class);
}
