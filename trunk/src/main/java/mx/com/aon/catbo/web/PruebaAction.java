package mx.com.aon.catbo.web;

import org.springframework.context.ApplicationContextException;

import mx.com.aon.catbo.service.PruebaActionManager;

public class PruebaAction extends AbstractListAction {
	private transient PruebaActionManager pruebaActionManager;

	private String _actionResult;
	public String metodoPrueba () throws ApplicationContextException {
		try {
			_actionResult = pruebaActionManager.metodoPrueba();
			addActionMessage(_actionResult);
			success = true;
			return SUCCESS;
		} catch (ApplicationContextException ae) {
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		} catch (Exception e) {
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
	public String get_actionResult() {
		return _actionResult;
	}
	public void set_actionResult(String result) {
		_actionResult = result;
	}
	public void setPruebaActionManager(PruebaActionManager pruebaActionManager) {
		this.pruebaActionManager = pruebaActionManager;
	}
}
