/**
 * 
 */
package mx.com.aon.kernel.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author eflores
 * @date 02/09/2008
 *
 */
public class MpoliagrVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String cdUnieco;
    
    private String cdRamo;
    
    private String estado;
    
    private String nmPoliza;
    
    private String cdAgrupa;
    
    private String nmSuplem;
    
    private String status;
    
    private String cdPerson;
    
    private String nmorddom;
    
    private String cdForpag;
    
    private String dsForpag;
    
    private String cdPagcom;
    
    private String dsNombre;
    
    private String dsDomicilio;
    
    private String cdPerreg;
    
    private String cdBanco;
    
    private String cdSucursal;
    
    private String dsBanco;
    
    private String dsSucursal;
    
    private String cdCuenta;
    
    private String cdRazon;
    
    private String swRegula;
    
    private String cdGestor;
    
    private String cdRol;
    
    private String cdTipocuenta;
    
    private String nmcuenta;
    
    private String dsTipotarj;
    
    private String fechaUltreg;
    
    private String nmDigver;
    
    private String muestraCampos;
    
    private String cdTipoTarjeta;
    
    private String dsTipoTarjeta;
    
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the cdAgrupa
     */
    public String getCdAgrupa() {
        return cdAgrupa;
    }

    /**
     * @param cdAgrupa the cdAgrupa to set
     */
    public void setCdAgrupa(String cdAgrupa) {
        this.cdAgrupa = cdAgrupa;
    }

    /**
     * @return the cdForpag
     */
    public String getCdForpag() {
        return cdForpag;
    }

    /**
     * @param cdForpag the cdForpag to set
     */
    public void setCdForpag(String cdForpag) {
        this.cdForpag = cdForpag;
    }

    /**
     * @return the cdPagcom
     */
    public String getCdPagcom() {
        return cdPagcom;
    }

    /**
     * @param cdPagcom the cdPagcom to set
     */
    public void setCdPagcom(String cdPagcom) {
        this.cdPagcom = cdPagcom;
    }

    /**
     * @return the cdPerreg
     */
    public String getCdPerreg() {
        return cdPerreg;
    }

    /**
     * @param cdPerreg the cdPerreg to set
     */
    public void setCdPerreg(String cdPerreg) {
        this.cdPerreg = cdPerreg;
    }

    /**
     * @return the cdPerson
     */
    public String getCdPerson() {
        return cdPerson;
    }

    /**
     * @param cdPerson the cdPerson to set
     */
    public void setCdPerson(String cdPerson) {
        this.cdPerson = cdPerson;
    }

    /**
     * @return the cdRamo
     */
    public String getCdRamo() {
        return cdRamo;
    }

    /**
     * @param cdRamo the cdRamo to set
     */
    public void setCdRamo(String cdRamo) {
        this.cdRamo = cdRamo;
    }

    /**
     * @return the cdUnieco
     */
    public String getCdUnieco() {
        return cdUnieco;
    }

    /**
     * @param cdUnieco the cdUnieco to set
     */
    public void setCdUnieco(String cdUnieco) {
        this.cdUnieco = cdUnieco;
    }

    /**
     * @return the dsNombre
     */
    public String getDsNombre() {
        return dsNombre;
    }

    /**
     * @param dsNombre the dsNombre to set
     */
    public void setDsNombre(String dsNombre) {
        this.dsNombre = dsNombre;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the nmorddom
     */
    public String getNmorddom() {
        return nmorddom;
    }

    /**
     * @param nmorddom the nmorddom to set
     */
    public void setNmorddom(String nmorddom) {
        this.nmorddom = nmorddom;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the cdBanco
     */
    public String getCdBanco() {
        return cdBanco;
    }

    /**
     * @param cdBanco the cdBanco to set
     */
    public void setCdBanco(String cdBanco) {
        this.cdBanco = cdBanco;
    }

    /**
     * @return the cdCuenta
     */
    public String getCdCuenta() {
        return cdCuenta;
    }

    /**
     * @param cdCuenta the cdCuenta to set
     */
    public void setCdCuenta(String cdCuenta) {
        this.cdCuenta = cdCuenta;
    }

    /**
     * @return the cdGestor
     */
    public String getCdGestor() {
        return cdGestor;
    }

    /**
     * @param cdGestor the cdGestor to set
     */
    public void setCdGestor(String cdGestor) {
        this.cdGestor = cdGestor;
    }

    /**
     * @return the cdRazon
     */
    public String getCdRazon() {
        return cdRazon;
    }

    /**
     * @param cdRazon the cdRazon to set
     */
    public void setCdRazon(String cdRazon) {
        this.cdRazon = cdRazon;
    }

    /**
     * @return the cdRol
     */
    public String getCdRol() {
        return cdRol;
    }

    /**
     * @param cdRol the cdRol to set
     */
    public void setCdRol(String cdRol) {
        this.cdRol = cdRol;
    }

    /**
     * @return the cdSucursal
     */
    public String getCdSucursal() {
        return cdSucursal;
    }

    /**
     * @param cdSucursal the cdSucursal to set
     */
    public void setCdSucursal(String cdSucursal) {
        this.cdSucursal = cdSucursal;
    }

    /**
     * @return the cdTipocuenta
     */
    public String getCdTipocuenta() {
        return cdTipocuenta;
    }

    /**
     * @param cdTipocuenta the cdTipocuenta to set
     */
    public void setCdTipocuenta(String cdTipocuenta) {
        this.cdTipocuenta = cdTipocuenta;
    }

    /**
     * @return the dsDomicilio
     */
    public String getDsDomicilio() {
        return dsDomicilio;
    }

    /**
     * @param dsDomicilio the dsDomicilio to set
     */
    public void setDsDomicilio(String dsDomicilio) {
        this.dsDomicilio = dsDomicilio;
    }

    /**
     * @return the nmcuenta
     */
    public String getNmcuenta() {
        return nmcuenta;
    }

    /**
     * @param nmcuenta the nmcuenta to set
     */
    public void setNmcuenta(String nmcuenta) {
        this.nmcuenta = nmcuenta;
    }

    /**
     * @return the swRegula
     */
    public String getSwRegula() {
        return swRegula;
    }

    /**
     * @param swRegula the swRegula to set
     */
    public void setSwRegula(String swRegula) {
        this.swRegula = swRegula;
    }

    /**
     * @return the dsBanco
     */
    public String getDsBanco() {
        return dsBanco;
    }

    /**
     * @param dsBanco the dsBanco to set
     */
    public void setDsBanco(String dsBanco) {
        this.dsBanco = dsBanco;
    }

    /**
     * @return the dsSucursal
     */
    public String getDsSucursal() {
        return dsSucursal;
    }

    /**
     * @param dsSucursal the dsSucursal to set
     */
    public void setDsSucursal(String dsSucursal) {
        this.dsSucursal = dsSucursal;
    }

	/**
	 * @return the dsForpag
	 */
	public String getDsForpag() {
		return dsForpag;
	}

	/**
	 * @param dsForpag the dsForpag to set
	 */
	public void setDsForpag(String dsForpag) {
		this.dsForpag = dsForpag;
	}

	/**
	 * @return the dsTipotarj
	 */
	public String getDsTipotarj() {
		return dsTipotarj;
	}

	/**
	 * @param dsTipotarj the dsTipotarj to set
	 */
	public void setDsTipotarj(String dsTipotarj) {
		this.dsTipotarj = dsTipotarj;
	}

	/**
	 * @return the fechaUltreg
	 */
	public String getFechaUltreg() {
		return fechaUltreg;
	}

	/**
	 * @param fechaUltreg the fechaUltreg to set
	 */
	public void setFechaUltreg(String fechaUltreg) {
		this.fechaUltreg = fechaUltreg;
	}

	/**
	 * @return the nmDigver
	 */
	public String getNmDigver() {
		return nmDigver;
	}

	/**
	 * @param nmDigver the nmDigver to set
	 */
	public void setNmDigver(String nmDigver) {
		this.nmDigver = nmDigver;
	}

    /**
     * @return the nmSuplem
     */
    public String getNmSuplem() {
        return nmSuplem;
    }

    /**
     * @param nmSuplem the nmSuplem to set
     */
    public void setNmSuplem(String nmSuplem) {
        this.nmSuplem = nmSuplem;
    }

    /**
     * @return the nmPoliza
     */
    public String getNmPoliza() {
        return nmPoliza;
    }

    /**
     * @param nmPoliza the nmPoliza to set
     */
    public void setNmPoliza(String nmPoliza) {
        this.nmPoliza = nmPoliza;
    }

	public String getMuestraCampos() {
		return muestraCampos;
	}

	public void setMuestraCampos(String muestraCampos) {
		this.muestraCampos = muestraCampos;
	}

	public String getCdTipoTarjeta() {
		return cdTipoTarjeta;
	}

	public void setCdTipoTarjeta(String cdTipoTarjeta) {
		this.cdTipoTarjeta = cdTipoTarjeta;
	}

	public String getDsTipoTarjeta() {
		return dsTipoTarjeta;
	}

	public void setDsTipoTarjeta(String dsTipoTarjeta) {
		this.dsTipoTarjeta = dsTipoTarjeta;
	}

}
