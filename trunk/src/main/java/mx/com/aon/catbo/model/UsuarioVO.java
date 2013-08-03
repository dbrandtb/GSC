package mx.com.aon.catbo.model;

import java.io.Serializable;
import java.util.List;

public class UsuarioVO  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6946763403360270166L;
	private String idUsuario;
	private String nombre;
    private String cdUnieco;
    private String otdba;
    private String cdModelo;
    private String cdmodgra;
    private String langCode;
    private String idRegion;
    private String swciaage;
    private String cdimres;
    private String swintext;
    private String cdPerson;
    private String countryCode;

	private List<RolesVO> listaRoles;

	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<RolesVO> getListaRoles() {
		return listaRoles;
	}
	public void setListaRoles(List<RolesVO> listaRoles) {
		this.listaRoles = listaRoles;
	}


    public String getCdUnieco() {
        return cdUnieco;
    }

    public void setCdUnieco(String cdUnieco) {
        this.cdUnieco = cdUnieco;
    }

    public String getOtdba() {
        return otdba;
    }

    public void setOtdba(String otdba) {
        this.otdba = otdba;
    }

    public String getCdModelo() {
        return cdModelo;
    }

    public void setCdModelo(String cdModelo) {
        this.cdModelo = cdModelo;
    }

    public String getCdmodgra() {
        return cdmodgra;
    }

    public void setCdmodgra(String cdmodgra) {
        this.cdmodgra = cdmodgra;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(String idRegion) {
        this.idRegion = idRegion;
    }

    public String getSwciaage() {
        return swciaage;
    }

    public void setSwciaage(String swciaage) {
        this.swciaage = swciaage;
    }

    public String getCdimres() {
        return cdimres;
    }

    public void setCdimres(String cdimres) {
        this.cdimres = cdimres;
    }

    public String getSwintext() {
        return swintext;
    }

    public void setSwintext(String swintext) {
        this.swintext = swintext;
    }

    public String getCdPerson() {
        return cdPerson;
    }

    public void setCdPerson(String cdPerson) {
        this.cdPerson = cdPerson;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}








