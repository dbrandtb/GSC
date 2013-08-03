package mx.com.aon.portal.model;

import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Clase VO que modela la estructura de datos para usar en la pantalla
 * Agregar Cobertura de Consulta de Poliza Renovada
 * 
 *
 */
public class ConsultaPolizasCanceladasVO {

	private String asegurado;
    private String cdUniage;
    private String cdUnieco;
	private String dsUnieco;
	private String cdRamo;
	private String dsRamo;
	private String nmPoliza;
	private String nmSituac;
	private String feCancel;	
	private String primaNoDevengada;
	private String cdRazon;
	private String dsRazon;
	private String tipoCancel;
    private String nmsuplem;
    private String cdagrupa;
    private String nmrecibo;
    private String cdcancel;
    private String status;
    private String swcancela;
    private String estado;
    private String nSupLogi;
    private String nmPoliex;
    private String inciso;
    private String pol_bol;
    
    private String feefecto;
    private String feanulac;
    private String fevencim;
    private String feproren;
    private String comentarios;
    
    private String nmCancel;
    private String cdPerson;
    private String cdMoneda;
    
    private String reha;  


    public String getFeefecto() {
		return feefecto;
	}
	public void setFeefecto(String feefecto) {
		this.feefecto = feefecto;
	}
	public String getFeanulac() {
		return feanulac;
	}
	public void setFeanulac(String feanulac) {
		this.feanulac = feanulac;
	}
	public String getFevencim() {
		return fevencim;
	}
	public void setFevencim(String fevencim) {
		this.fevencim = fevencim;
	}
	public String getFeproren() {
		return feproren;
	}
	public void setFeproren(String feproren) {
		this.feproren = feproren;
	}
	public String getInciso() {
		return inciso;
	}
	public void setInciso(String inciso) {
		this.inciso = inciso;
	}
	public String getTipoCancel() {
		return tipoCancel;
	}
	public void setTipoCancel(String tipoCancel) {
		this.tipoCancel = tipoCancel;
	}
	public String getAsegurado() {
		return asegurado;
	}
	public void setAsegurado(String asegurado) {
		this.asegurado = asegurado;
	}
	public String getCdUniage() {
		return cdUniage;
	}
	public void setCdUniage(String cdUniage) {
		this.cdUniage = cdUniage;
	}
	public String getDsUnieco() {
		return dsUnieco;
	}
	public void setDsUnieco(String dsUnieco) {
		this.dsUnieco = dsUnieco;
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
	public String getNmPoliza() {
		return nmPoliza;
	}
	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}
	public String getNmSituac() {
		return nmSituac;
	}
	public void setNmSituac(String nmSituac) {
		this.nmSituac = nmSituac;
	}
	public String getFeCancel() {
		return feCancel;
	}
	public void setFeCancel(String feCancel) {
		this.feCancel = feCancel;
	}
	public String getPrimaNoDevengada() {
		return primaNoDevengada;
	}
	public void setPrimaNoDevengada(String primaNoDevengada) {
		this.primaNoDevengada = primaNoDevengada;
	}
	public String getCdRazon() {
		return cdRazon;
	}
	public void setCdRazon(String cdRazon) {
		this.cdRazon = cdRazon;
	}
	public String getDsRazon() {
		return dsRazon;
	}
	public void setDsRazon(String dsRazon) {
		this.dsRazon = dsRazon;
	}


    public String getCdUnieco() {
        return cdUnieco;
    }

    public void setCdUnieco(String cdUnieco) {
        this.cdUnieco = cdUnieco;
    }


    public String getNmsuplem() {
        return nmsuplem;
    }

    public void setNmsuplem(String nmsuplem) {
        this.nmsuplem = nmsuplem;
    }

    public String getCdagrupa() {
        return cdagrupa;
    }

    public void setCdagrupa(String cdagrupa) {
        this.cdagrupa = cdagrupa;
    }

    public String getNmrecibo() {
        return nmrecibo;
    }

    public void setNmrecibo(String nmrecibo) {
        this.nmrecibo = nmrecibo;
    }

    public String getCdcancel() {
        return cdcancel;
    }

    public void setCdcancel(String cdcancel) {
        this.cdcancel = cdcancel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSwcancela() {
        return swcancela;
    }

    public void setSwcancela(String swcancela) {
        this.swcancela = swcancela;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
	public String getNSupLogi() {
		return nSupLogi;
	}
	public void setNSupLogi(String supLogi) {
		nSupLogi = supLogi;
	}
	public String getNmPoliex() {
		return nmPoliex;
	}
	public void setNmPoliex(String nmPoliex) {
		this.nmPoliex = nmPoliex;
	}
	public String getPol_bol() {
		return pol_bol;
	}
	public void setPol_bol(String pol_bol) {
		this.pol_bol = pol_bol;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public String getNmCancel() {
		return nmCancel;
	}
	public void setNmCancel(String nmCancel) {
		this.nmCancel = nmCancel;
	}
	public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	public String getCdMoneda() {
		return cdMoneda;
	}
	public void setCdMoneda(String cdMoneda) {
		this.cdMoneda = cdMoneda;
	}
	
	public void setReha(String reha) {
		this.reha = reha;
	}
	
	public String getReha() {
		return reha;
	}
	
	public String toString () {
		String jsonObject = JSONObject.fromObject(this).toString();
		return jsonObject;
	}
	

}
