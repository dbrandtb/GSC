package mx.com.aon.configurador.pantallas.service;

import java.util.List;
import java.util.Map;

import mx.com.aon.configurador.pantallas.model.BackBoneResultVO;
import mx.com.aon.configurador.pantallas.model.ConjuntoPantallaVO;
import mx.com.aon.configurador.pantallas.model.MasterWrapperVO;
import mx.com.aon.configurador.pantallas.model.PantallaVO;
import mx.com.aon.configurador.pantallas.model.master.MasterVO;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

/**
 * 
 * Interfaz Manager para el control y visualizacion de datos
 * 
 * @author aurora.lozada
 * 
 */

public interface ConfiguradorPantallaService {

    /**
     * Metodo para la busqueda de conjuntos de pantallas
     * 
     * @param parameters Listado de parametros para la invocacion del servicio en la base de datos
     * @return Una lista de objetos de tipo ConjuntoPantallaVO
     * @throws ApplicationException
     */
    List<ConjuntoPantallaVO> buscaConjuntos(Map<String, String> parameters) throws ApplicationException;

    /**
     * Metodo para guardar un conjunto de pantallas
     * 
     * @param conjunto Objeto que representa al conjunto de pantallas a guardar
     * @return La clave del conjunto almacenado
     * @throws ApplicationException
     */
    //public String guardaConjunto(ConjuntoPantallaVO conjunto) throws ApplicationException;
    public WrapperResultados guardaConjunto(ConjuntoPantallaVO conjunto) throws ApplicationException;

    /**
     * Metodo para obtener un conjunto de pantallas
     * 
     * @param cdConjunto Clave del conjunto a obtener
     * @return Objeto de tipo ConjuntoPantallaVO
     * @throws ApplicationException
     */
    public ConjuntoPantallaVO getConjunto(String cdConjunto) throws ApplicationException;

    /**
     * Metodo para la busqueda de pantallas
     * 
     * @param cdConjunto Clave del conjunto para realizar la búsqueda
     * @return Una lista de pantallas
     * @throws ApplicationException
     */
    List<?> getPantallasConjunto(String cdConjunto) throws ApplicationException;

    /**
     * Metodo para copiar un conjunto de pantallas
     * 
     * @param conjuntoCopiar Objeto que representa al conjunto de pantallas a copiar
     * @return La clave del conjunto copiado
     * @throws ApplicationException
     */
    //public String copiarConjunto(ConjuntoPantallaVO conjuntoCopiar) throws ApplicationException;
    public BackBoneResultVO copiarConjunto(ConjuntoPantallaVO conjuntoCopiar) throws ApplicationException;

    /**
     * Metodo para copiar las pantallas asociadas al conjunto copiado
     * 
     * @param cdConjunto Clave del conjunto
     * @param copiaPantallas Listado de las pantallas
     * @param cdElemento de las pantallas destino
     * @throws ApplicationException
     * @return WrapperResultados del mensaje final para el copiado de las pantallas
     */
    public WrapperResultados copiarPantalla(String cdConjunto, String[] copiaPantallas, String cdElemento) throws ApplicationException;

    /**
     * Metodo para guardar una pantalla
     * 
     * @param pantalla Objeto de tipo PantallaVO
     * @return La clave de la pantalla
     * @throws ApplicationException
     */
    public BackBoneResultVO guardaPantalla(PantallaVO pantalla) throws ApplicationException;

    /**
     * Metodo para obtener una pantalla
     * 
     * @param cdConjunto Clave del conjunto
     * @param cdPantalla Clave de la pantalla
     * @return Objeto de tipo PantallaVO
     * @throws ApplicationException
     */
    public PantallaVO getPantalla(String cdConjunto, String cdPantalla) throws ApplicationException;

    /**
     * Metodo para eliminar una pantalla
     * 
     * @param cdConjunto Clave del conjunto
     * @param cdPantalla Clave de la pantalla
     * @throws ApplicationException
     */
    //public void eliminarPantalla(String cdConjunto, String cdPantalla) throws ApplicationException;

    /**
     * Metodo para obtener los masters de un proceso
     * 
     * @param cdProceso Clave del proceso
     * @return Una lista de masters
     * @throws ApplicationException
     */
    List<?> getMasters(String cdProceso) throws ApplicationException;

    /**
     * Metodo para activar un conjunto de pantallas
     * 
     * @param parameters Listado de parametros para la invocacion del servicio en la base de datos
     * @throws ApplicationException
     */
    //public void activarConjunto(Map<String, String> parameters) throws ApplicationException;

    /**
     * Metodo que obtiene los atributos de una pantalla
     * 
     * @param master Clave del master
     * @param proceso Clave del proceso
     * @param tipo  Tipo de pantalla 
     * @param producto  Clave del producto
     * @retrun Objeto de tipo MasterVO
     * @throws ApplicationException
     */
    @Deprecated
    public MasterVO getAtributosPantalla(String master, String proceso, String tipo, String producto) throws ApplicationException;

    /**
     * Metodo que obtiene las propiedades de los atributos de una pantalla
     * 
     * @param master Clave del master //combo master --- cdMaster
     * @param proceso Clave del proceso //combo proceso
     * @param tipo  Tipo de pantalla // combo master  ---cdTipo
     * @param producto  Clave del producto //combo producto
     * @retrun Objeto de tipo MasterVO
     * @throws ApplicationException
     */
//    public MasterVO getAtributosPropiedades(String master, String proceso, String tipo, String producto)
//            throws ApplicationException;
    
    
    
    MasterWrapperVO getAtributosPropiedades(String master, String proceso, String tipo, String producto)
    throws ApplicationException;
    
    
    MasterWrapperVO getAtributosPropiedades(Map parameters) throws ApplicationException;
    
    
//    JSONMasterVO getJSONMaster(String cdTipoMaster, String cdProceso, String tipoMaster, 
//    		String cdProducto, String claveSituacion) throws ApplicationException;
    
	public TableModelExport getModel(Map<String, String> parameters) throws ApplicationException;

    /**
     * Método sobrecargado para soportar el modelo de WrapperResultados
     * @param parameters Map con los datos a enviar al sp
     * @param start
     * @param limit
     * @return
     * @throws ApplicationException
     */
	public PagedList buscaConjuntos (Map<String, String> parameters, int start, int limit) throws ApplicationException;

	/**
	 * Método sobrecargado para soportar el modelo de WrapperResultados
	 * @param cdConjunto
	 * @return
	 * @throws ApplicationException
	 */
	public PagedList getPantallasConjunto (String cdConjunto, int start, int limit) throws ApplicationException;

    /**
     * Metodo para eliminar una pantalla
     * 
     * @param cdConjunto Clave del conjunto
     * @param cdPantalla Clave de la pantalla
     * @throws ApplicationException
     */
    public String eliminarPantalla(String cdConjunto, String cdPantalla) throws ApplicationException;

    public PagedList getMasters(String cdProceso, int start, int limit) throws ApplicationException;

    /**
     * Metodo para activar un conjunto de pantallas
     * 
     * @param parameters Listado de parametros para la invocacion del servicio en la base de datos
     * @throws ApplicationException
     */
    public String activarConjunto(Map<String, String> parameters) throws ApplicationException;
    
    /**
     * Metodo para obtener el JSON de alguna pantalla
     * @param parameters
     * @return
     * @throws ApplicationException
     */
    public PantallaVO getPantallaFinal(Map<String, String> parameters) throws ApplicationException;

}