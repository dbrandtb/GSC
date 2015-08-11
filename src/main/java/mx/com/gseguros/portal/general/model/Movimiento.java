package mx.com.gseguros.portal.general.model;

public enum Movimiento
{
	DESHACER_PASO_ASEGURADO ("V"),
	PASO_QUITAR_ASEGURADO   ("M"),
	SACAENDOSO              ("V");
	
	String tipo;
	
	private Movimiento(String tipo)
	{
		this.tipo = tipo;
	}
	
	public String getTipo() {
		return tipo;
	}
}