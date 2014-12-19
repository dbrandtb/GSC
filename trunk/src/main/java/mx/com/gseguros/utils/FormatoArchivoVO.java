package mx.com.gseguros.utils;

import java.util.List;

public class FormatoArchivoVO {
	
	private List<CampoVO> campos;
	
	public FormatoArchivoVO() {
		super();
	}
	
	public FormatoArchivoVO(List<CampoVO> campos) {
		super();
		this.campos = campos;
	}

	public List<CampoVO> getCampos() {
		return campos;
	}

	public void setCampos(List<CampoVO> campos) {
		this.campos = campos;
	}
	
}