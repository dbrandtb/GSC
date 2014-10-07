package mx.com.gseguros.wizard.controller;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.gseguros.wizard.service.TablasApoyoManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TablasApoyoAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 3547571651089343357L;

	@Autowired
	private TablasApoyoManager tablasApoyoManager;
	
}