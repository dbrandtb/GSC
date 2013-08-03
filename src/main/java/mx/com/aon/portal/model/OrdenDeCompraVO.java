
package mx.com.aon.portal.model;

/**
 * Clase VO usada para obtener Orden de compra
 * 
 * @param cdCarro               codigo del carrito de compras
 * @param cdUsuari              codigo del usuario
 * @param feInicio              fecha de inicio
 * @param nmTarj                numero de tarjeta de credito
 * @param cdContra              codigo del contrato
 * @param cdAsegur              codigo de la aseguradora
 * @param nmSubTot              monto subtotal de la orden de compra
 * @param nmDsc                 monto de descuento de la orden de compras
 * @param nmTotal               monto total de la orden de compras
 * @param cdEstado              codigo de estado de la orden de compras
 * @param dsEstado              descripcion del estado de la orden de compra
 * @param feEstado              fecha de estado de la orden de compra
 * @param nmOrden               numero de orden
 * @param cdTipDom              codigo de tipo de domicilio
 * @param nmOrdDon              numero de orden
 * @param cdClient              codigo del cliente 
 * @param cdForPag              codigo de la forma de pago
 * 
 */
public class OrdenDeCompraVO {

	private String cdCarro;
	private String cdUsuari;
	private String feInicio;
	private String nmTarj;
	private String cdContra;
	private String cdAsegur;
	private String nmSubTot;
	private String nmDsc;
	private String nmTotal;
	private String cdEstado;
	private String dsEstado;
	private String feEstado;
	private String nmOrden;
	private String cdTipDom;
	private String nmOrdDon;
	private String cdClient;
	private String cdForPag;


	public String getCdCarro() {
		return cdCarro;
	}

	public void setCdCarro(String cdCarro) {
		this.cdCarro = cdCarro;
	}

	public String getCdUsuari() {
		return cdUsuari;
	}

	public void setCdUsuari(String cdUsuari) {
		this.cdUsuari = cdUsuari;
	}

	public String getFeInicio() {
		return feInicio;
	}

	public void setFeInicio(String feInicio) {
		this.feInicio = feInicio;
	}

	public String getNmTarj() {
		return nmTarj;
	}

	public void setNmTarj(String nmTarj) {
		this.nmTarj = nmTarj;
	}

	public String getCdContra() {
		return cdContra;
	}

	public void setCdContra(String cdContra) {
		this.cdContra = cdContra;
	}

	public String getCdAsegur() {
		return cdAsegur;
	}

	public void setCdAsegur(String cdAsegur) {
		this.cdAsegur = cdAsegur;
	}

	public String getNmSubTot() {
		return nmSubTot;
	}

	public void setNmSubTot(String nmSubTot) {
		this.nmSubTot = nmSubTot;
	}

	public String getNmDsc() {
		return nmDsc;
	}

	public void setNmDsc(String nmDsc) {
		this.nmDsc = nmDsc;
	}

	public String getNmTotal() {
		return nmTotal;
	}

	public void setNmTotal(String nmTotal) {
		this.nmTotal = nmTotal;
	}

	public String getCdEstado() {
		return cdEstado;
	}

	public void setCdEstado(String cdEstado) {
		this.cdEstado = cdEstado;
	}

	public String getFeEstado() {
		return feEstado;
	}

	public void setFeEstado(String feEstado) {
		this.feEstado = feEstado;
	}

	public String getNmOrden() {
		return nmOrden;
	}

	public void setNmOrden(String nmOrden) {
		this.nmOrden = nmOrden;
	}

	public String getCdTipDom() {
		return cdTipDom;
	}

	public void setCdTipDom(String cdTipDom) {
		this.cdTipDom = cdTipDom;
	}

	public String getNmOrdDon() {
		return nmOrdDon;
	}

	public void setNmOrdDon(String nmOrdDon) {
		this.nmOrdDon = nmOrdDon;
	}

	public String getCdClient() {
		return cdClient;
	}

	public void setCdClient(String cdClient) {
		this.cdClient = cdClient;
	}

	public String getCdForPag() {
		return cdForPag;
	}

	public void setCdForPag(String cdForPag) {
		this.cdForPag = cdForPag;
	}

	public String getDsEstado() {
		return dsEstado;
	}

	public void setDsEstado(String dsEstado) {
		this.dsEstado = dsEstado;
	}
    
}



