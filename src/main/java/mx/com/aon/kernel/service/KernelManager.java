package mx.com.aon.kernel.service;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.flujos.cotizacion.model.DatosEntradaCotizaVO;
import mx.com.aon.flujos.cotizacion.model.SituacionVO;
import mx.com.aon.flujos.endoso.model.SuplemVO;
import mx.com.aon.kernel.model.ValoresXDefectoCoberturaVO;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.ice.services.to.Campo;
import mx.com.ice.services.to.ResultadoTransaccion;
import mx.com.ice.services.to.screen.GlobalVariableContainerVO;

/**
 * @author ricardo.bautista
 * @date 24/07/2008
 *
 */
public interface KernelManager {

	/**
	 * Crea los bloques en memoria necesarios para despues cargar los valores por defecto del producto
	 * @param idSesion Identificador de la sesion actual del usuario
	 * @param user
	 * @param globalVarVo
	 * @return globalVariableContainerVO con sus atributos modificados
	 * @throws ApplicationException
	 */
	public GlobalVariableContainerVO cargaValoresPorDefecto(String idSesion, UserVO user, GlobalVariableContainerVO globalVarVo,
            String tipSup) throws ApplicationException;
    
    /**
     * 
     * @param idSesion
     * @param globalVarVo
     * @param parametrosPantalla
     * @throws mx.com.ice.services.exception.ApplicationException 
     */
    public void guardaDatosAdicionalesEndosos(String idSesion, GlobalVariableContainerVO globalVarVo,
            Map<String, String> parametrosPantalla) 
            throws mx.com.ice.services.exception.ApplicationException;
	
    /**
     * 
     * @param idSesion
     * @param globalVarVo
     * @param parametrosPantalla
     * @param sumaAsegurada 
     * @param cdGarant 
     * @throws mx.com.ice.services.exception.ApplicationException 
     */
    public void guardarCoberturasAtributos(String idSesion, GlobalVariableContainerVO globalVarVo,
            Map<String, String> parametrosPantalla, String cdGarant, String cdElemento, String sumaAsegurada) 
        throws mx.com.ice.services.exception.ApplicationException;
    /**
     * 
     * @param idSesion
     * @param globalVarVo
     * @param parametrosPantalla
     * @param sumaAsegurada 
     * @param cdGarant 
     * @throws mx.com.ice.services.exception.ApplicationException 
     */
    public void guardarCoberturasAtributosEndosos(String idSesion, GlobalVariableContainerVO globalVarVo,
            Map<String, String> parametrosPantalla, String cdGarant, String cdElemento, String sumaAsegurada,
            String modo) 
        throws mx.com.ice.services.exception.ApplicationException;
    
    /**
     * 
     * @param idSesion
     * @param globalVarVo
     * @param parametrosPantalla
     * @param claveTipoObj
     * @param montoCotizar
     * @param descripcionTipo
     * @param modoAcceso
     * @param cdObjeto
     * @throws mx.com.ice.services.exception.ApplicationException
     * @throws ApplicationException 
     */
    public void guardarAccesoriosAtributos(String idSesion, GlobalVariableContainerVO globalVarVo,
            Map<String, String> parametrosPantalla, String claveTipoObj, String montoCotizar, 
            String descripcionTipo, String modoAcceso, String cdObjeto) 
            throws mx.com.ice.services.exception.ApplicationException, ApplicationException;
    
	/**
	 * Ejecuta las validaciones del producto al dar click en el boton de Cotizar
	 * @param idSesion Identificador de la sesion actual del usuario
	 * @param globalVarVo
	 * @param parametrosPantalla
	 * @param usuario
	 * @return globalVariableContainerVO con sus atributos modificados
	 * @throws ApplicationException
	 */
	public GlobalVariableContainerVO ejecutaValidaciones(String idSesion, GlobalVariableContainerVO globalVarVo, Map<String, String> parametrosPantalla, UserVO usuario) throws ApplicationException, mx.com.ice.services.exception.ApplicationException;
	
	/**
	 * Calcula el siguiente numero de la poliza
	 * @param cdUnieco
	 * @param cdRamo
	 * @param estado
	 * @return Numero de la Poliza calculado
	 * @throws ApplicationException
	 */
	public String calculaNumeroPoliza(String cdUnieco, String cdRamo, String estado) throws ApplicationException;
    
    /**
     * 
     * @param idSesion
     * @param nmsituac
     * @param globalVarVo
     * @param idProducto
     * @param cdElemento
     * @param creaBloques
     */
    public void valoresXDefectoInciso(String idSesion, String nmsituac, GlobalVariableContainerVO globalVarVo,
            String idProducto, String cdElemento, boolean creaBloques, String cdGarant);
	
    /**
     * 
     * @param idSesion
     * @param nmsituac
     * @param globalVarVo
     * @param idProducto
     * @param cdElemento
     * @param creaBloques
     * @param cdGarant
     * @return Map<String, ValoresXDefectoCoberturaVO>
     */
    public Map<String, ValoresXDefectoCoberturaVO> valoresXDefectoIncisoCobertura(String idSesion, String nmsituac, GlobalVariableContainerVO globalVarVo,
            String idProducto, String cdElemento, boolean creaBloques, String cdGarant);
    
    /**
     * 
     * @param idSesion
     * @param nmsituac
     * @param globalVarVo
     * @param idProducto
     * @param cdElemento
     * @param creaBloques
     * @param cdGarant
     * @return Map<String, ValoresXDefectoCoberturaVO>
     */
    public Map<String, ValoresXDefectoCoberturaVO> valoresXDefectoIncisoCoberturaEndosos(String idSesion, String nmsituac, GlobalVariableContainerVO globalVarVo,
            String idProducto, String cdElemento, boolean creaBloques, String cdGarant);
    
	/**
	 * Genera el Suplemento Fisico
	 * @param cdUnieco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @param fecha
	 * @return Numero de Suplemento Fisico
	 * @throws ApplicationException
	 */
	public String generaSuplFisico(String cdUnieco, String cdRamo, String estado, String nmPoliza, String fecha) throws ApplicationException;
	
	/**
	 * Genera el Suplemento Logico
	 * @param cdUnieco
	 * @param cdRamo
	 * @param estado
	 * @param nmPoliza
	 * @return Numero de Suplemento Logico
	 * @throws ApplicationException
	 */
	public int generaSuplLogico(String cdUnieco, String cdRamo, String estado, String nmPoliza) throws ApplicationException;
	
    /**
     * 
     * @param cdUnieco
     * @param cdRamo
     * @param estado
     * @param nmPoliza
     * @param fecha
     * @param cdElemento
     * @param usuario
     * @return
     * @throws ApplicationException
     */
    public SuplemVO generaSuplems(String cdUnieco, String cdRamo, String estado, String nmPoliza, String fecha,
            String cdElemento, String usuario, String cdProceso) throws ApplicationException;
    
	/**
	 * 
	 * @param cdUnieco
	 * @param cdRamo
	 * @param estado
	 * @param nmpoliza
	 * @param nSupLogi
	 * @param feEfecto
	 * @param feEmisio
	 * @param user
	 * @throws ApplicationException
	 */
	public void movTdescSup(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nSupLogi, String feEfecto, String feEmisio, String user,
            String tipSup) throws ApplicationException;
	
	/**
	 * 
	 * @param cdUnieco
	 * @param cdRamo
	 * @param estado
	 * @param nmpoliza
	 * @param nmSuplem
	 * @param feIniVig
	 * @param feFinVig
	 * @param nSupLogi
	 * @throws ApplicationException
	 */
	public void movMSupleme(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmSuplem, String feIniVig, String feFinVig, String nSupLogi) throws ApplicationException;
	
	/**
	 * 
	 * @param cdUnieco
	 * @param cdRamo
	 * @param estado
	 * @param nmpoliza
	 * @param nmSuplem
	 * @param cdUniage
	 * @param cdElemento
	 * @throws ApplicationException
	 */
	public void movMPolagen(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmSuplem, String cdUniage, String cdElemento) throws ApplicationException;
	
	/**
	 * 
	 * @param cdUnieco
	 * @param cdRamo
	 * @param estado
	 * @param nmpoliza
	 * @param nmSuplem
	 * @throws ApplicationException
	 */
	public void movMPoliagr(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmSuplem) throws ApplicationException;
	
	/**
	 * 
	 * @param cdUnieco
	 * @param cdRamo
	 * @param estado
	 * @param nmpoliza
	 * @param nmSuplem
	 * @throws ApplicationException
	 */
	public void movMPoliage(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmSuplem) throws ApplicationException;

	
	/**
	 * 
	 * @param cdElement
	 * @param cdUnieco
	 * @param cdRamo
	 * @param estado
	 * @param nmpoliza
	 * @param nmSituac
	 * @param cdTipsit
	 * @param cdUsuario
	 * @param fecha
	 * @param cadenaSolicitudTarifas
	 * @return
	 * @throws ApplicationException
	 */
	public List<SituacionVO> clonarSituacion(String cdElement, String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmSituac, String cdTipsit, String cdUsuario, String fecha, String cadenaSolicitudTarifas) throws ApplicationException;
	
	
	/**
	 * 
	 * @param usuario
	 * @param cdElement
	 * @param cdUnieco
	 * @param cdRamo
	 * @param estado
	 * @param nmpoliza
	 * @param nmSituac
	 * @param cdTipsit
	 * @param nmSuplem
	 * @throws ApplicationException
	 */
	public void ejecutaSIGSVALIPOL(String usuario, String cdElement, String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmSituac, String cdTipsit, String nmSuplem) throws ApplicationException;
	
	/**
	 * Servicio de BD procIncisoDef
	 * @param cdUnieco
	 * @param cdRamo
	 * @param estado
	 * @param nmpoliza
	 * @param nmSituac
	 * @param cdElement
	 * @param cdPerson
	 * @throws ApplicationException
	 */
	public void procIncisoDef(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmSituac, String cdElement, String cdPerson,String cdCiaaseg, String cdPlan,String cdPerpag) throws ApplicationException;

	/**
	 * 
	 * @param idSesion
	 * @param globalVarVo
	 * @param parametrosPantalla
	 * @param usuario
	 * @return
	 * @throws ApplicationException
	 */
	public GlobalVariableContainerVO comprar(String idSesion, GlobalVariableContainerVO globalVarVo, Map<String, String> parametrosPantalla, UserVO usuario) throws ApplicationException, mx.com.ice.services.exception.ApplicationException;
	
	
	/**
	 * Carga TODOS los bloques, tanto los que se refieren a la poliza como los de Incisos
	 * @param idSesion
	 * @param globalVarVo
	 * @param parametrosPantalla
	 * @param usuario
	 * @return
	 * @throws ApplicationException
	 */
	public void cargarBloques(String idSesion, GlobalVariableContainerVO globalVarVo, UserVO usuario) throws ApplicationException;

	/**
	 * Carga solo los bloques referentes a la poliza
	 * @param idSesion
	 * @param globalVarVo
	 * @param usuario
	 * @throws ApplicationException
	 */
	public void cargarBloquesPoliza(String idSesion, GlobalVariableContainerVO globalVarVo, UserVO usuario) throws ApplicationException;
	
	/**
	 * Carga solo los bloques referentes a Incisos
	 * @param idSesion
	 * @param globalVarVo
	 * @param usuario
	 * @throws ApplicationException
	 */
	public void cargarBloquesIncisos(String idSesion, GlobalVariableContainerVO globalVarVo, UserVO usuario) throws ApplicationException;
	
	/**
	 * Crea TODOS los bloques para una sesi&oacute;n dada
	 * @param idSesion
	 * @throws ApplicationException
	 */
	public void crearBloques(String idSesion) throws ApplicationException;
	
	/**
	 * Obtiene los datos de un bloque indicado 
	 * @param idSesion identificador de la sesion web del usuario
	 * @param bloque Nombre del bloque 
	 * @param campos arreglo con el nombre de los campos que se quieren obtener del bloque
	 * @return objeto ResultadoTransaccion el cual contendrá los datos del bloque que queremos
	 * @throws ApplicationException
	 */
	public ResultadoTransaccion obtenerDatosBloque(String idSesion, String bloque, Campo[] campos) throws ApplicationException;
	
	/**
	 * Obtiene la descripcion dada una tabla y la clave
	 * @param tabla
	 * @param valor1
	 * @return
	 * @throws ApplicationException
	 */
	public String obtieneDescripcion(String tabla, String valor1) throws ApplicationException;

	/**
	 * Guarda los datos adicionales de la poliza (bloque B1B) y lanza validaciones del bloque.
	 * @param idSesion
	 * @param globalVarVo
	 * @param parametrosPantalla valore que se guardan
	 */
	public ResultadoTransaccion guardaDatosAdicionales(String idSesion, GlobalVariableContainerVO globalVarVo, Map<String, String> parametrosPantalla) throws ApplicationException;
    
    /**
     * 
     * @param globalVarVo
     * @param cdGarant
     * @param idSession
     */
    public void borrarCoberturasBloques(GlobalVariableContainerVO globalVarVo, String cdGarant, 
            String idSession) throws ApplicationException, mx.com.ice.services.exception.ApplicationException;
    
    /**
     * @throws ApplicationException 
     * @throws mx.com.ice.services.exception.ApplicationException 
     * 
     */
    public void borrarAccesoriosBloques(GlobalVariableContainerVO globalVarVo, String nmobjeto,  
            String idSession) throws ApplicationException, mx.com.ice.services.exception.ApplicationException;
    
    
    public void ejecutarCaso(GlobalVariableContainerVO globalVarVo,
			UserVO usuario, String cdUnieco, String cdRamo, String cdTipsit,
			String nmpoliza, String nmSuplem, String cdCia, String nmSituac, String estado, String proceso, String numeroExterno)
			throws ApplicationException;
    
    /**
	 * Validaciones 
	 * @param parameters
	 * @return
	 * @throws ApplicationException
	 */
    public WrapperResultados ejecutaPExecValidador(String cdUnieco, String cdRamo, String estado, String nmpoliza, String nmsituac, String cdBloque) throws ApplicationException;
    
    /**
     * Obtiene los datos de entrada para la cotizacion
     * @param parameters
     * @return Lista de DatosEntradaCotizaVO
     * @throws ApplicationException
     */
    public List<DatosEntradaCotizaVO> getDatosEntradaCotiza(Map<String, String> parameters) throws ApplicationException;
}
