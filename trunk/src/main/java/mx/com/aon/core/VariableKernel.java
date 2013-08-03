package mx.com.aon.core;

public class VariableKernel {

	private static final String[] variable = { "vg.CdUnieco", "vg.CdRamo",
			"vg.Estado", "vg.NmSuplem", "vg.UserBD", "vg.CdTipSit",
			"vg.NmSituac", "vg.NmPoliza",
			"vg.cdCia", "vg.cdPlan", "vg.cdPerpag", "vg.MessageProcess",
            "vg.FeVencim", "vg.NmSupLogi", "OT"};

	public static String UnidadEconomica() {
		return variable[0];
	}

	public static String CodigoRamo() {
		return variable[1];
	}
	
	public static String Estado() {
		return variable[2];
	}
	
	public static String NumeroSuplemento() {
		return variable[3];
	}
	
	public static String UsuarioBD() {
		return variable[4];
	}
	
	public static String CodigoTipoSituacion() {
		return variable[5];
	}
	
	public static String NumeroSituacion() {
		return variable[6];
	}
	
	public static String NumeroPoliza() {
		return variable[7];
	}
	
	public static String CodigoCompania() {
		return variable[8];
	}
	
	public static String CodigoPlan() {
		return variable[9];
	}
	
	public static String PeriodicidadPago() {
		return variable[10];
	}
	
	public static String MessageProcess() {
		return variable[11];
	}
    
    public static String FechaVencimiento() {
        return variable[12];
    }
    
    public static String NumeroSuplementoLogico() {
        return variable[13];
    }
    
    public static String OT() {
    	return variable[14];
    }
}
