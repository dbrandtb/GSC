package mx.com.gseguros.portal.despachador.controller;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import mx.com.aon.core.web.PrincipalCoreAction;

@Controller
@Scope("prototype")
@ParentPackage(value="default")
@Namespace("/despachador")
public class DespachadorAction extends PrincipalCoreAction {
    
}