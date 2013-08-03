package mx.com.aon.catbo.model;

public class ArchivoVO {
    private String tipo;
    private String descripcion;
    private String numCaso;
    private String numnMovimiento;
    private String numArchivo;
    
    private String cdTipoar;
    private String dsArchivo;
    private String cdAtribu;
    private String dsAtribu;
    private String swFormat;
    private String nmLmax;
    private String nmLmin;
    private String swObliga;
    private String dsTabla;
    private String otTabval;
    private String status;
    private boolean Obliga;

    private String indArchivo;
     private String dsIndArchivo;


    public String getNumCaso() {
		return numCaso;
	}

	public void setNumCaso(String numCaso) {
		this.numCaso = numCaso;
	}

	public String getNumnMovimiento() {
		return numnMovimiento;
	}

	public void setNumnMovimiento(String numnMovimiento) {
		this.numnMovimiento = numnMovimiento;
	}

	public String getNumArchivo() {
		return numArchivo;
	}

	public void setNumArchivo(String numArchivo) {
		this.numArchivo = numArchivo;
	}

	public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

	public String getCdTipoar() {
		return cdTipoar;
	}

	public void setCdTipoar(String cdTipoar) {
		this.cdTipoar = cdTipoar;
	}

	public String getDsArchivo() {
		return dsArchivo;
	}

	public void setDsArchivo(String dsArchivo) {
		this.dsArchivo = dsArchivo;
	}

	public String getCdAtribu() {
		return cdAtribu;
	}

	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}

	public String getDsAtribu() {
		return dsAtribu;
	}

	public void setDsAtribu(String dsAtribu) {
		this.dsAtribu = dsAtribu;
	}

	public String getSwFormat() {
		return swFormat;
	}

	public void setSwFormat(String swFormat) {
		this.swFormat = swFormat;
	}

	public String getNmLmax() {
		return nmLmax;
	}

	public void setNmLmax(String nmLmax) {
		this.nmLmax = nmLmax;
	}

	public String getNmLmin() {
		return nmLmin;
	}

	public void setNmLmin(String nmLmin) {
		this.nmLmin = nmLmin;
	}

	public String getSwObliga() {
		return swObliga;
	}

	boolean convert(String swObliga) {
		if(swObliga=="1")
			 return true;
		else return false;
	}
	
	
	public void setSwObliga(String swObliga) {
		
		Obliga= convert(swObliga);
		
        String Obliga  = (swObliga.equals("1"))?"true":"false";
		
		this.swObliga = swObliga;
	}

	public String getDsTabla() {
		return dsTabla;
	}

	public void setDsTabla(String dsTabla) {
		this.dsTabla = dsTabla;
	}

	public String getOtTabval() {
		return otTabval;
	}

	public void setOtTabval(String otTabval) {
		this.otTabval = otTabval;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIndArchivo() {
		return indArchivo;
	}

	public void setIndArchivo(String indArchivo) {
		this.indArchivo = indArchivo;
	}

	public String getDsIndArchivo() {
		return dsIndArchivo;
	}

	public void setDsIndArchivo(String dsIndArchivo) {
		this.dsIndArchivo = dsIndArchivo;
	}
}
