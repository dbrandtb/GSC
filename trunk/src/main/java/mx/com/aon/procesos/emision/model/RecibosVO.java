/**
 * 
 */
package mx.com.aon.procesos.emision.model;

/**
 * @author Alejandro Garcia
 */
public class RecibosVO {
	
	private String nmrecibo;
	private String nmreciboexterno;
	private String feinicio;
	private String fefinal;
	private String feemisio;
	private String cdestado;
	private String feestado;
	private String ptimport;
	private String feexpedi;
	private String dsestado;
    private String cdTipoRecibo;
    private String dsTipoRecibo;
	//Detalle Recibos
	private String cdTipoCon;
	private String dsTipoCon;
	private String importeCon;
	
	
	public String getNmrecibo() {
		return nmrecibo;
	}

	
	public void setNmrecibo(String nmrecibo) {
		this.nmrecibo = nmrecibo;
	}

	
	public String getFeinicio() {
		return feinicio;
	}

	
	public void setFeinicio(String feinicio) {
		this.feinicio = feinicio;
	}

	
	public String getFefinal() {
		return fefinal;
	}

	
	public void setFefinal(String fefinal) {
		this.fefinal = fefinal;
	}

	
	public String getFeemisio() {
		return feemisio;
	}

	
	public void setFeemisio(String feemisio) {
		this.feemisio = feemisio;
	}

	
	public String getCdestado() {
		return cdestado;
	}

	
	public void setCdestado(String cdestado) {
		this.cdestado = cdestado;
	}

	
	public String getFeestado() {
		return feestado;
	}

	
	public void setFeestado(String feestado) {
		this.feestado = feestado;
	}

	
	public String getPtimport() {
		return ptimport;
	}

	
	public void setPtimport(String ptimport) {
		this.ptimport = ptimport;
	}

	
	public String getFeexpedi() {
		return feexpedi;
	}

	
	public void setFeexpedi(String feexpedi) {
		this.feexpedi = feexpedi;
	}

	
	public String getDsestado() {
		return dsestado;
	}

	
	public void setDsestado(String dsestado) {
		this.dsestado = dsestado;
	}


	/**
	 * @return the cdTipoCon
	 */
	public String getCdTipoCon() {
		return cdTipoCon;
	}


	/**
	 * @param cdTipoCon the cdTipoCon to set
	 */
	public void setCdTipoCon(String cdTipoCon) {
		this.cdTipoCon = cdTipoCon;
	}


	/**
	 * @return the dsTipoCon
	 */
	public String getDsTipoCon() {
		return dsTipoCon;
	}


	/**
	 * @param dsTipoCon the dsTipoCon to set
	 */
	public void setDsTipoCon(String dsTipoCon) {
		this.dsTipoCon = dsTipoCon;
	}


	/**
	 * @return the importeCon
	 */
	public String getImporteCon() {
		return importeCon;
	}


	/**
	 * @param importeCon the importeCon to set
	 */
	public void setImporteCon(String importeCon) {
		this.importeCon = importeCon;
	}


    /**
     * @return the cdTipoRecibo
     */
    public String getCdTipoRecibo() {
        return cdTipoRecibo;
    }


    /**
     * @param cdTipoRecibo the cdTipoRecibo to set
     */
    public void setCdTipoRecibo(String cdTipoRecibo) {
        this.cdTipoRecibo = cdTipoRecibo;
    }


    /**
     * @return the dsTipoRecibo
     */
    public String getDsTipoRecibo() {
        return dsTipoRecibo;
    }


    /**
     * @param dsTipoRecibo the dsTipoRecibo to set
     */
    public void setDsTipoRecibo(String dsTipoRecibo) {
        this.dsTipoRecibo = dsTipoRecibo;
    }


	public String getNmreciboexterno() {
		return nmreciboexterno;
	}


	public void setNmreciboexterno(String nmreciboexterno) {
		this.nmreciboexterno = nmreciboexterno;
	}

}
