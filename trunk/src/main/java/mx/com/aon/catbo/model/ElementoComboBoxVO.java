/**
 * 
 */
package mx.com.aon.catbo.model;


/**
 * Clase VO que modela una estructura de datos reusable 
 * para setear valores que contengan un codigo y una descripcion.
 * 
 * @vars codigo,descripcion
 *
 */
public class ElementoComboBoxVO {
    private String codigo;
    private String codigo2;
    private String descripcion;
    
    
    private String cdRamo;
    private String dsRamo;

    private String cdProceso;
    private String dsProceso;
    
    private String cdMatriz;

    private String cdNivAtn;
    private String dsNivAtn;
    
    private String cdTipGui;
    private String dsTipGui;
    
    private String cdtipoar;
    private String dsarchivo;
    private String indarchivo;
    
    private String directorioAsociado;
    
    private String nmConfig;
    
    public String getCdtipoar() {
		return cdtipoar;
	}

	public void setCdtipoar(String cdtipoar) {
		this.cdtipoar = cdtipoar;
	}

	public String getDsarchivo() {
		return dsarchivo;
	}

	public void setDsarchivo(String dsarchivo) {
		this.dsarchivo = dsarchivo;
	}

	public String getIndarchivo() {
		return indarchivo;
	}

	public void setIndarchivo(String indarchivo) {
		this.indarchivo = indarchivo;
	}

	public String getCdProceso() {
		return cdProceso;
	}

	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}

	public String getDsProceso() {
		return dsProceso;
	}

	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}

	public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

	public String getCdMatriz() {
		return cdMatriz;
	}

	public void setCdMatriz(String cdMatriz) {
		this.cdMatriz = cdMatriz;
	}

	public String getCdNivAtn() {
		return cdNivAtn;
	}

	public void setCdNivAtn(String cdNivAtn) {
		this.cdNivAtn = cdNivAtn;
	}

	public String getDsNivAtn() {
		return dsNivAtn;
	}

	public void setDsNivAtn(String dsNivAtn) {
		this.dsNivAtn = dsNivAtn;
	}

	public String getCodigo2() {
		return codigo2;
	}

	public void setCodigo2(String codigo2) {
		this.codigo2 = codigo2;
	}

	public String getCdTipGui() {
		return cdTipGui;
	}

	public void setCdTipGui(String cdTipGui) {
		this.cdTipGui = cdTipGui;
	}

	public String getDsTipGui() {
		return dsTipGui;
	}

	public void setDsTipGui(String dsTipGui) {
		this.dsTipGui = dsTipGui;
	}

	public String getDirectorioAsociado() {
		return directorioAsociado;
	}

	public void setDirectorioAsociado(String directorioAsociado) {
		this.directorioAsociado = directorioAsociado;
	}

	public String getNmConfig() {
		return nmConfig;
	}

	public void setNmConfig(String nmConfig) {
		this.nmConfig = nmConfig;
	}
	
}
