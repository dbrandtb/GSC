package mx.com.aon.configurador.pantallas.model;

import java.io.Serializable;

/**
 * 
 *  Clase Value Object de un conjunto de pantalla
 * 
 * @author  aurora.lozada
 * 
 */

public class ConjuntoPantallaVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5360068401407129994L;
    
    private String cdConjunto;
  
    private String nombreConjunto;
    
    private String descripcion;
    
    private String proceso;
    
    private String cdProceso;
    
    private String cliente;
    
    private String cdCliente;
    
    private String cdRamo;
    
    private String dsRamo;
    
    private String cdProducto;
    
    private String producto;
    
    private String tipoSituacion;
    
    private String dsTipoSituacion;

    /**
     * @return the cdConjunto
     */
    public String getCdConjunto() {
        return cdConjunto;
    }

    /**
     * @param cdConjunto the cdConjunto to set
     */
    public void setCdConjunto(String cdConjunto) {
        this.cdConjunto = cdConjunto;
    }

    /**
     * @return the nombreConjunto
     */
    public String getNombreConjunto() {
        return nombreConjunto;
    }

    /**
     * @param nombreConjunto the nombreConjunto to set
     */
    public void setNombreConjunto(String nombreConjunto) {
        this.nombreConjunto = nombreConjunto;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the proceso
     */
    public String getProceso() {
        return proceso;
    }

    /**
     * @param proceso the proceso to set
     */
    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    /**
     * @return the cdProceso
     */
    public String getCdProceso() {
        return cdProceso;
    }

    /**
     * @param cdProceso the cdProceso to set
     */
    public void setCdProceso(String cdProceso) {
        this.cdProceso = cdProceso;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the cdCliente
     */
    public String getCdCliente() {
        return cdCliente;
    }

    /**
     * @param cdCliente the cdCliente to set
     */
    public void setCdCliente(String cdCliente) {
        this.cdCliente = cdCliente;
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
     * @return the cdProducto
     */
    public String getCdProducto() {
        return cdProducto;
    }

    /**
     * @param cdProducto the cdProducto to set
     */
    public void setCdProducto(String cdProducto) {
        this.cdProducto = cdProducto;
    }

    /**
     * @return the producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(String producto) {
        this.producto = producto;
    }

    /**
     * @return the dsRamo
     */
    public String getDsRamo() {
        return dsRamo;
    }

    /**
     * @param dsRamo the dsRamo to set
     */
    public void setDsRamo(String dsRamo) {
        this.dsRamo = dsRamo;
    }

	public String getTipoSituacion() {
		return tipoSituacion;
	}

	public void setTipoSituacion(String tipoSituacion) {
		this.tipoSituacion = tipoSituacion;
	}

	/**
	 * @return the dsTipoSituacion
	 */
	public String getDsTipoSituacion() {
		return dsTipoSituacion;
	}

	/**
	 * @param dsTipoSituacion the dsTipoSituacion to set
	 */
	public void setDsTipoSituacion(String dsTipoSituacion) {
		this.dsTipoSituacion = dsTipoSituacion;
	}
    
    

}
