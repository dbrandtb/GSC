package mx.com.gseguros.utils;

public interface Constantes {
	
	public static final String USER = "USUARIO";
	public static final String CDUNIECO = "1";
	public static final String FORMATO_FECHA = "dd/MM/yyyy";
	
	public static final String MSG_ID_ERROR      = "100000";
	public static final String MSG_ID_OK         = "200000";
	public static final String MSG_TITLE_ERROR   = "1";
	public static final String MSG_TITLE_OK      = "2";
	public static final String MSG_TITLE_WARNING = "4";
    
    public static final String INSERT_MODE = "I";
    public static final String UPDATE_MODE = "U";
    public static final String DELETE_MODE = "D";
    
    public static final String SI = "S";
    public static final String NO = "N";
    
    public static final String STATUS_VIVO   = "V";
    public static final String STATUS_MUERTO = "M";
    
    public static final String MAUTSINI_AREA_RECLAMACIONES = "RE";
    public static final String MAUTSINI_AREA_MEDICA        = "ME";
    public static final String MAUTSINI_SINIESTRO          = "S";
    public static final String MAUTSINI_FACTURA            = "F";
    public static final String MAUTSINI_DETALLE            = "D";
	
    /**
     * Mensaje Atributo Variable cuando tiene hijos asociados
     */
    public static final String MESSAGE_ATRIBUTO_CON_HIJOS_ASOCIADOS = "El atributo tiene hijos asociados, si se elimina tambi�n se eliminar�n esas asociaciones. �Desea continuar?";
    
    public static final String REGISTRO_DUPLICADO = "100006";
    
    public static final String NMSITUAC_TITULAR = "1";
}
