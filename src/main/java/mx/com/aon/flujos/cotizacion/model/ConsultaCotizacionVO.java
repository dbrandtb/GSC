package mx.com.aon.flujos.cotizacion.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ConsultaCotizacionVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3032541611590567429L;
	private String codigoAseguradora; 		//cdunieco
	private String descripcionAseguradora; 	//dsunieco
	private String codigoProducto;			//cdramo
	private String descripcionProducto;		//dsramo
	private String fechaFormato;			
	private String fecha;					//feinival
	private String codigoFormaPago;			//cdperpag
	private String descripcionFormaPago;	//dsperpag
	private String prima;					//nmimpfpg
	private String cdcia;					//cdcia
	private String estado;					//estado
	private String nmpoliza;				//nmpoliza
	private String cdplan;					//cdplan
	private String dsplan;					//dsplan
	
	private String nmsituac;                //nmsituac
	private String nmsuplem;                //nmsuplem
	private String nmsolici;				//nmsolici
	
	private String feefecto;				//feefecto
	private String fevencim;				//fevencim
	private String cdtipsit;				//cdtipsit
	
    /**
     * @return String
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
	//Getters && Setters
	public String getCodigoAseguradora() { return codigoAseguradora; }
	public void setCodigoAseguradora(String codigoAseguradora) { this.codigoAseguradora = codigoAseguradora; }
	public String getDescripcionAseguradora() {	return descripcionAseguradora; }
	public void setDescripcionAseguradora(String descripcionAseguradora) { this.descripcionAseguradora = descripcionAseguradora; }
	
	public String getCodigoProducto() { return codigoProducto; }
	public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }
	public String getDescripcionProducto() { return descripcionProducto; }
	public void setDescripcionProducto(String descripcionProducto) { this.descripcionProducto = descripcionProducto; }
	
	public String getFechaFormato() { return fechaFormato; }
	public void setFechaFormato(String fechaFormato) { this.fechaFormato = fechaFormato; }
	public String getFecha() { return fecha; }
	public void setFecha(String fecha) { this.fecha = fecha; }
	
	public String getCodigoFormaPago() { return codigoFormaPago; }
	public void setCodigoFormaPago(String codigoFormaPago) { this.codigoFormaPago = codigoFormaPago; }
	public String getDescripcionFormaPago() { return descripcionFormaPago; }
	public void setDescripcionFormaPago(String descripcionFormaPago) { this.descripcionFormaPago = descripcionFormaPago; }
	
	public String getPrima() { return prima; }	
	public void setPrima(String prima) { this.prima = prima; }
	
	public String getCdcia() { return cdcia; }
	public void setCdcia(String cdcia) { this.cdcia = cdcia; }
	
	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }
	
	public String getNmpoliza() { return nmpoliza; }
	public void setNmpoliza(String nmpoliza) { this.nmpoliza = nmpoliza; }
	
	public String getCdplan() { return cdplan; }
	public void setCdplan(String cdplan) { this.cdplan = cdplan; }
	public String getDsplan() { return dsplan; }
	public void setDsplan(String dsplan) { this.dsplan = dsplan; }
    public String getNmsituac() { return nmsituac; }
    public void setNmsituac(String nmsituac) { this.nmsituac = nmsituac; }
    public String getNmsuplem() { return nmsuplem; }
    public void setNmsuplem(String nmsuplem) { this.nmsuplem = nmsuplem; }
    public String getNmsolici() { return nmsolici; }
    public void setNmsolici(String nmsolici) { this.nmsolici = nmsolici; }

	public String getFeefecto() { return feefecto;	}
	public void setFeefecto(String feefecto) { this.feefecto = feefecto; }
	public String getFevencim() { return fevencim;}
	public void setFevencim(String fevencim) { this.fevencim = fevencim; }

	public String getCdtipsit() {
		return cdtipsit;
	}

	public void setCdtipsit(String cdtipsit) {
		this.cdtipsit = cdtipsit;
	}
	
    
}
