package mx.com.aon.portal.model;
import net.sf.json.JSONObject;

public class AlertasUsuarioPolizaPorAseguradoraVO {

	private String cdUniEco;
	private String nmPoliza;
	private String nmPoliex;

	public String getCdUniEco() {
		return cdUniEco;
	}
	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}
	public String getNmPoliza() {
		return nmPoliza;
	}
	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}
	public String getNmPoliex() {
		return nmPoliex;
	}
	public void setNmPoliex(String nmPoliex) {
		this.nmPoliex = nmPoliex;
	}
	
	public String toString () {
		String jsonObject = JSONObject.fromObject(this).toString();
		return jsonObject;
	}
	
}
