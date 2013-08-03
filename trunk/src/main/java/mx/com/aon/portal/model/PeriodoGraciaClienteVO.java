package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener el carrito de compras
 * 
 * @param cdPerson                  codigo de personaconfiguaracion
 * @param cdElemento                codigo elemento  
 * @param cliente                   codigo cliente
 * @param aseguradora               nombre aseguradora
 * @param cveProducto               codigo del producto   
 * @param producto                  nombre del producto
 * @param recibos                   recibo  
 * @param diasDeGracia              dias de gracia otorgados
 * @param diasAntesDeCancelacion    dias transcurridos antes de cancelar
 * @param cdTramo                   codigo del tramo 
 * @param dsTramo                   descripcion del tramo 
 */
public class PeriodoGraciaClienteVO {

	private String cdPerson;
    private String cdElemento;
    private String cliente;
    private String cveAseguradora;
    private String aseguradora;
    private String cveProducto;
    private String producto;
    private String recibos;
    private String diasDeGracia;
    private String diasAntesDeCancelacion;
    private String cdTramo;
    private String dsTramo;
    
	public String getCdElemento() {
		return cdElemento;
	}
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getCveAseguradora() {
		return cveAseguradora;
	}
	public void setCveAseguradora(String cveAseguradora) {
		this.cveAseguradora = cveAseguradora;
	}
	public String getAseguradora() {
		return aseguradora;
	}
	public void setAseguradora(String aseguradora) {
		this.aseguradora = aseguradora;
	}
	public String getCveProducto() {
		return cveProducto;
	}
	public void setCveProducto(String cveProducto) {
		this.cveProducto = cveProducto;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getRecibos() {
		return recibos;
	}
	public void setRecibos(String recibos) {
		this.recibos = recibos;
	}
	public String getDiasDeGracia() {
		return diasDeGracia;
	}
	public void setDiasDeGracia(String diasDeGracia) {
		this.diasDeGracia = diasDeGracia;
	}
	public String getDiasAntesDeCancelacion() {
		return diasAntesDeCancelacion;
	}
	public void setDiasAntesDeCancelacion(String diasAntesDeCancelacion) {
		this.diasAntesDeCancelacion = diasAntesDeCancelacion;
	}
	public String getCdTramo() {
		return cdTramo;
	}
	public void setCdTramo(String cdTramo) {
		this.cdTramo = cdTramo;
	}
	public String getCdPerson() {
		return cdPerson;
	}
	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}
	public String getDsTramo() {
		return dsTramo;
	}
	public void setDsTramo(String dsTramo) {
		this.dsTramo = dsTramo;
	}
    
}


