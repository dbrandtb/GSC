package mx.com.aon.portal.model;

import java.util.List;

import net.sf.json.JSONObject;

public class MapToolTips {
	private String key;
	private DatosToolTips datos;

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public DatosToolTips getDatos() {
		return datos;
	}
	public void setDatos(DatosToolTips datos) {
		this.datos = datos;
	}
	public String toString() {
		String result = JSONObject.fromObject(this).toString();
		return result;
	}
}
