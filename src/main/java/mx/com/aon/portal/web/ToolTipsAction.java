package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;


import mx.com.aon.portal.model.DatosToolTips;
import mx.com.aon.portal.model.MapToolTips;
import mx.com.aon.portal.service.ToolTipsManager;

import com.opensymphony.xwork2.ActionSupport;

public class ToolTipsAction extends ActionSupport implements SessionAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4196888038998639060L;

	private transient ToolTipsManager toolTipsManager;

	private List<MapToolTips> lista;

	private HashMap datosMap = new HashMap();

	private Map session;

	private boolean success;

	private String helpMap;

	public String cmdObtenerMapa () throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			DatosToolTips toolTipsMap = new DatosToolTips();
			
			sb.append("var helpMap = new Map();");

			toolTipsMap.setClave("icon-help-tooltip");
			toolTipsMap.setAyuda("");
			toolTipsMap.setFieldLabel("Presione para ver la ayuda");
			toolTipsMap.setTooltip("");

			helpMap = toolTipsMap.toString();
			sb.append(helpMap);

			toolTipsMap.setClave("codigoError");
			toolTipsMap.setAyuda("Este es un ejemplo de ayuda con mas caracteres para el\\n campo cdError");
			toolTipsMap.setFieldLabel("Codigo");
			toolTipsMap.setTooltip("Mensaje asociado al cdError");

			helpMap = toolTipsMap.toString();
			sb.append(helpMap);
			
			session.put("helpMap", sb.toString());
			success = true;
			return SUCCESS;
		}catch (Exception e) {
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public List<MapToolTips> getLista() {
		return lista;
	}

	public void setLista(List<MapToolTips> lista) {
		this.lista = lista;
	}

	public void setToolTipsManager(ToolTipsManager toolTipsManager) {
		this.toolTipsManager = toolTipsManager;
	}

	public HashMap getDatosMap() {
		return datosMap;
	}

	public String get_mapa() {
		return helpMap;
	}

	public void set_mapa(String _mapa) {
		this.helpMap = _mapa;
	}
}
