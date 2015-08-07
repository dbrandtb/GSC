package mx.com.gseguros.portal.general.model;

public enum Movimiento
{
	PASO_QUITAR_ASEGURADO   ("M"),
	DESHACER_PASO_ASEGURADO ("V");
	
	String tipo;
	
	private Movimiento(String tipo)
	{
		this.tipo = tipo;
	}
	
	public String getTipo() {
		return tipo;
	}
}