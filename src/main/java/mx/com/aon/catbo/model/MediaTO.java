package mx.com.aon.catbo.model;

import java.io.InputStream;

public class MediaTO {
    private String idCaso ="";
    private String idDocumento ="";
    private String nmMovimiento;
    private String name ="";
    private String mime ="";
    private String filename ="";
    private int length;
    private InputStream contenidoBytes;
    private String cdTipoArchivo;
    private String fechaArchivo;
    private String dsArchivo;
    private String user;

    public MediaTO() {}

    public MediaTO(String idCaso, String idDocumento, String nmMovimiento, String name, String mime, String filename, int length, String cdTipoArchivo, String fechaArchivo, InputStream contenidoBytes) {
        this.idCaso = idCaso;
        this.idDocumento = idDocumento;
        this.nmMovimiento = nmMovimiento;
        this.name = name;
        this.mime = mime;
        this.filename = filename;
        this.length = length;
        this.cdTipoArchivo = cdTipoArchivo;
        this.fechaArchivo = fechaArchivo;
        this.contenidoBytes = contenidoBytes;
        
    }

    public String getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(String idCaso) {
        this.idCaso = idCaso;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

	public String getNmMovimiento() {
		return nmMovimiento;
	}

	public void setNmMovimiento(String nmMovimiento) {
		this.nmMovimiento = nmMovimiento;
	}

	public String getCdTipoArchivo() {
		return cdTipoArchivo;
	}

	public void setCdTipoArchivo(String cdTipoArchivo) {
		this.cdTipoArchivo = cdTipoArchivo;
	}

	public String getFechaArchivo() {
		return fechaArchivo;
	}

	public void setFechaArchivo(String fechaArchivo) {
		this.fechaArchivo = fechaArchivo;
	}

	public String getDsArchivo() {
		return dsArchivo;
	}

	public void setDsArchivo(String dsArchivo) {
		this.dsArchivo = dsArchivo;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public InputStream getContenidoBytes() {
		return contenidoBytes;
	}

	public void setContenidoBytes(InputStream contenidoBytes) {
		this.contenidoBytes = contenidoBytes;
	}
}
