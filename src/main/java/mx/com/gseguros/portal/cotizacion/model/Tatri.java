package mx.com.gseguros.portal.cotizacion.model;

public class Tatri {
	public static final String TATRISIT=	"1";
	public static final String TATRIPOL=	"2";
	public static final String TATRIGAR=	"3";
	public static final String TATRIPER=	"4";
	private String cdatribu;
    private String swformat;
    private String nmlmin;
    private String nmlmax;
    private String swobliga;
    private String dsatribu;
    private String ottabval;
    private String cdtablj1;
    private String type="";
    private String swsuscri;
    private boolean readOnly=false;
    private String swtarifi;

    public String getCdatribu() {
        return cdatribu;
    }

    public void setCdatribu(String cdatribu) {
        this.cdatribu = cdatribu;
    }

    public String getSwformat() {
        return swformat;
    }

    public void setSwformat(String swformat) {
        this.swformat = swformat;
    }

    public String getNmlmin() {
        return nmlmin;
    }

    public void setNmlmin(String nmlmin) {
        this.nmlmin = nmlmin;
    }

    public String getNmlmax() {
        return nmlmax;
    }

    public void setNmlmax(String nmlmax) {
        this.nmlmax = nmlmax;
    }

    public String getSwobliga() {
        return swobliga;
    }

    public void setSwobliga(String swobliga) {
        this.swobliga = swobliga;
    }

    public String getDsatribu() {
        return dsatribu;
    }

    public void setDsatribu(String dsatribu) {
        this.dsatribu = dsatribu;
    }

    public String getOttabval() {
        return ottabval;
    }

    public void setOttabval(String ottabval) {
        this.ottabval = ottabval;
    }

    public String getCdtablj1() {
        return cdtablj1;
    }

    public void setCdtablj1(String cdtablj1) {
        this.cdtablj1 = cdtablj1;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSwsuscri() {
		return swsuscri;
	}

	public void setSwsuscri(String swsuscri) {
		this.swsuscri = swsuscri;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getSwtarifi() {
		return swtarifi;
	}

	public void setSwtarifi(String swtarifi) {
		this.swtarifi = swtarifi;
	}
}
