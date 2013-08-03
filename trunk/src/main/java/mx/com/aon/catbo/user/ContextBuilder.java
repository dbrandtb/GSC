package mx.com.aon.catbo.user;
//import mx.com.aon.catbo.user.UserContext;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.catbo.model.UsuarioVO;

public interface ContextBuilder {
	public UsuarioVO buildContext (String userId, int start, int limit) throws ApplicationException;
}
