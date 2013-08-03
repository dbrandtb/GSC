package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener grupo de personas
 * 
 * @param cdGrupo               codigo grupo
 * @param cdUnieco              codigo aseguradora  
 * @param dsUnieco              nombre aseguradora
 * @param cdTipram              codigo tipo de ramo    
 * @param dsTipram              nombre tipo de ramo
 * @param cdRamo                codigo producto  
 * @param dsRamo                nombre producto
 * @param cdTipo                codigo tipo    
 * @param cdElemento            codigo cliente
 * @param cdGrupoPer            codigo grupo de personas   
 * @param dsElemen              nombre cliente
 */
public class AgrupacionGrupoPersonaVO {

	private String cdGrupo;
	private String cdUnieco;
	private String dsUnieco;
	private String cdTipram;
	private String dsTipram;
	private String cdRamo;
	private String dsRamo;
	private String cdTipo;
	private String dsAgrupa;
	private String cdElemento;
	private String dsElemen;

	public String getCdGrupo() {
		return cdGrupo;
	}

	public void setCdGrupo(String cdGrupo) {
		this.cdGrupo = cdGrupo;
	}

	public String getCdUnieco() {
		return cdUnieco;
	}

	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}

	public String getDsUnieco() {
		return dsUnieco;
	}

	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
	}

	public String getCdTipram() {
		return cdTipram;
	}

	public void setCdTipram(String cdTipram) {
		this.cdTipram = cdTipram;
	}

	public String getDsTipram() {
		return dsTipram;
	}

	public void setDsTipram(String dsTipram) {
		this.dsTipram = dsTipram;
	}

	public String getCdRamo() {
		return cdRamo;
	}

	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	public String getDsRamo() {
		return dsRamo;
	}

	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}

	public String getCdTipo() {
		return cdTipo;
	}

	public void setCdTipo(String cdTipo) {
		this.cdTipo = cdTipo;
	}

	public String getDsAgrupa() {
		return dsAgrupa;
	}

	public void setDsAgrupa(String dsAgrupa) {
		this.dsAgrupa = dsAgrupa;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getDsElemen() {
		return dsElemen;
	}

	public void setDsElemen(String dsElemen) {
		this.dsElemen = dsElemen;
	}
    
}



