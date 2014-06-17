package mx.com.gseguros.portal.general.util;

public enum Rol {
	
	MEDICO("15"),
	CLINICA("16");

	private String cdrol;

	private Rol(String cdrol) {
		this.cdrol = cdrol;
	}

	public String getCdrol() {
		return cdrol;
	}
	
}