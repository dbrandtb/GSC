package mx.com.aon.catbo.facade;

import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import mx.com.aon.core.ApplicationException;

/**
 * <p/>
 * Description Class
 * </p>
 * Last Modification Nov 16, 2006
 *
 * @author Gabriel Forradellas (Gabriel.Alejandro.Forradellas@oracle.com)
 * @version $Revision$
 */
public abstract class AbstractFacade {

    /**
     *  Permite obtener una instancia del Bean Factory de Spring, a partir de los archivos de configuracion de
     * Spring
     *  El contexto de Spring es Unico en la jvm en donde corre la aplicacion
     */
    public BeanFactory getBeanFactory() throws ApplicationException {
     try {
        BeanFactoryLocator locator = ContextSingletonBeanFactoryLocator.getInstance("classpath:/spring/config/beanRefContext.xml");
        BeanFactoryReference bfr = locator.useBeanFactory("serviceBO-core-context");
        BeanFactory factory = bfr.getFactory();
        return factory;
      } catch (Exception ex )
      {
        throw new ApplicationException("Error inseperado al tratar de obtener el BeanFactory de Spring => "+ ex.getMessage(),ex);
      }
    }


}
