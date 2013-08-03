package mx.com.aon.portal.model;

/**
 * Created by IntelliJ IDEA.
 * User: gabrielforradellas
 * Date: Mar 24, 2009
 * Time: 9:39:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReporteDeudaVO {
    
    private String numeroTipoBien;
    private String codigoPoliza;
    private String iCliente;

    //éstos son los que pedía la pantalla en el FRD
	private String poliza;
	private String endoso;
	private String certificado;
	private String fechaPago;
	private String referenciaBancaria;
	private String recibo;
	private String monto;

	// agregué éstos, si hacen falta los mapeamos en pantalla
	private String idAseguradora;
	private String codAseguradora;
	private String aseguradora;
	private String idCliente;
	private String cliente;
	private String idOperacion;
	private String codPoliza;
	private String codRamo;
	private String descRamo;
	private String nroCuota;
	private String fecVencimiento;
	private String descMoneda;
	private String impSaldoCuota;
	private String fecFinalCuota;
	private String nroCuotas;
	private String asegurado;
	private String idAsegurado;
	private String codEndoso;
	private String fecPago;
	private String impCouta;
	private String idPoliza;
	private String codCertificado;
	private String nroCuentaDebito;
	
	
	
    public String getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(String idOperacion) {
        this.idOperacion = idOperacion;
    }

    public String getNumeroTipoBien() {
        return numeroTipoBien;
    }

    public void setNumeroTipoBien(String numeroTipoBien) {
        this.numeroTipoBien = numeroTipoBien;
    }

    public String getCodigoPoliza() {
        return codigoPoliza;
    }

    public void setCodigoPoliza(String codigoPoliza) {
        this.codigoPoliza = codigoPoliza;
    }

    public String getICliente() {
        return iCliente;
    }

    public void setICliente(String iCliente) {
        this.iCliente = iCliente;
    }

	public String getPoliza() {
		return poliza;
	}

	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}

	public String getEndoso() {
		return endoso;
	}

	public void setEndoso(String endoso) {
		this.endoso = endoso;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public String getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getReferenciaBancaria() {
		return referenciaBancaria;
	}

	public void setReferenciaBancaria(String referenciaBancaria) {
		this.referenciaBancaria = referenciaBancaria;
	}

	public String getRecibo() {
		return recibo;
	}

	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getIdAseguradora() {
		return idAseguradora;
	}

	public void setIdAseguradora(String idAseguradora) {
		this.idAseguradora = idAseguradora;
	}

	public String getCodAseguradora() {
		return codAseguradora;
	}

	public void setCodAseguradora(String codAseguradora) {
		this.codAseguradora = codAseguradora;
	}

	public String getAseguradora() {
		return aseguradora;
	}

	public void setAseguradora(String aseguradora) {
		this.aseguradora = aseguradora;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCodPoliza() {
		return codPoliza;
	}

	public void setCodPoliza(String codPoliza) {
		this.codPoliza = codPoliza;
	}

	public String getCodRamo() {
		return codRamo;
	}

	public void setCodRamo(String codRamo) {
		this.codRamo = codRamo;
	}

	public String getDescRamo() {
		return descRamo;
	}

	public void setDescRamo(String descRamo) {
		this.descRamo = descRamo;
	}

	public String getNroCuota() {
		return nroCuota;
	}

	public void setNroCuota(String nroCuota) {
		this.nroCuota = nroCuota;
	}

	public String getFecVencimiento() {
		return fecVencimiento;
	}

	public void setFecVencimiento(String fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}

	public String getDescMoneda() {
		return descMoneda;
	}

	public void setDescMoneda(String descMoneda) {
		this.descMoneda = descMoneda;
	}

	public String getImpSaldoCuota() {
		return impSaldoCuota;
	}

	public void setImpSaldoCuota(String impSaldoCuota) {
		this.impSaldoCuota = impSaldoCuota;
	}

	public String getFecFinalCuota() {
		return fecFinalCuota;
	}

	public void setFecFinalCuota(String fecFinalCuota) {
		this.fecFinalCuota = fecFinalCuota;
	}

	public String getNroCuotas() {
		return nroCuotas;
	}

	public void setNroCuotas(String nroCuotas) {
		this.nroCuotas = nroCuotas;
	}

	public String getAsegurado() {
		return asegurado;
	}

	public void setAsegurado(String asegurado) {
		this.asegurado = asegurado;
	}

	public String getIdAsegurado() {
		return idAsegurado;
	}

	public void setIdAsegurado(String idAsegurado) {
		this.idAsegurado = idAsegurado;
	}

	public String getCodEndoso() {
		return codEndoso;
	}

	public void setCodEndoso(String codEndoso) {
		this.codEndoso = codEndoso;
	}

	public String getFecPago() {
		return fecPago;
	}

	public void setFecPago(String fecPago) {
		this.fecPago = fecPago;
	}

	public String getImpCouta() {
		return impCouta;
	}

	public void setImpCouta(String impCouta) {
		this.impCouta = impCouta;
	}

	public String getIdPoliza() {
		return idPoliza;
	}

	public void setIdPoliza(String idPoliza) {
		this.idPoliza = idPoliza;
	}

	public String getCodCertificado() {
		return codCertificado;
	}

	public void setCodCertificado(String codCertificado) {
		this.codCertificado = codCertificado;
	}

	public String getNroCuentaDebito() {
		return nroCuentaDebito;
	}

	public void setNroCuentaDebito(String nroCuentaDebito) {
		this.nroCuentaDebito = nroCuentaDebito;
	}
}
