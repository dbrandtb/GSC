package mx.com.aon.flujos.presinietros.service;

import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.model.presiniestros.PreSiniestroVO;
import mx.com.aon.portal.model.presiniestros.AutomovilVO;
import mx.com.aon.portal.model.presiniestros.BeneficioVO;
import mx.com.aon.portal.model.presiniestros.DanoVO;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.flujos.presinietros.model.DocumentoVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

public interface PresiniestrosManager {
	
	public String test()throws ApplicationException;
	
	/**
	 * Método genérico que obtiene una lista de objetos BaseObjectVO.
	 * @param parameters
	 * @param endpoint
	 * @return Lista de objetos BaseObjectVO
	 * @throws ApplicationException
	 */
	public List<BaseObjectVO> getItemList(Map<String, String> parameters, String endpoint) throws ApplicationException;
	
	/**
	 * Método genérico que obtiene resultados paginados por el objeto PagedList.
	 * @param parameters
	 * @param endpoint
	 * @param start
	 * @param limit
	 * @return Objeto PagedList que contiene resultados paginados
	 * @throws ApplicationException
	 */
	public PagedList paginaResultado(Map<String, String> parameters, String endpoint, int start, int limit) throws ApplicationException;

	/**
	 * Método genérico que obtiene el modelo de exportación de resultados en diferentes formatos.
	 * @param params
	 * @param endpoint
	 * @param columnas[]
	 * @return Objeto TableModelExport que contiene los resultados en un objeto TableModelExport
	 * @throws ApplicationException
	 */
	public TableModelExport obtieneModelo(Map<String, String> params, String endpoint, String columnas[]) throws ApplicationException; 
	
	/**
	 * Método que obtiene los datos básicos/inserta/actualiza un presiniestro de Automóviles.
	 * @param psVO
	 * @param aVO
	 * @param operacion
	 * @return Objeto WrapperResultados que contiene el resultado
	 * @throws ApplicationException
	 */
	public WrapperResultados servicePresiniestroAutomovil(PreSiniestroVO psVO, AutomovilVO aVO, String operacion) throws ApplicationException;

	/**
	 * Método que obtiene los datos correspondientes a un presiniestro de Automóviles previamente guardado para su edición.
	 * @param psVO
	 * @return Objeto WrapperResultados que contiene el resultado
	 * @throws ApplicationException
	 */	
	public WrapperResultados obtieneDatosEditarAutomovil(PreSiniestroVO psVO) throws ApplicationException;

	/**
	 * Método que obtiene los datos básicos/inserta/actualiza un presiniestro Daños.
	 * @param psVO
	 * @param dVO
	 * @param operacion
	 * @return Objeto WrapperResultados que contiene el resultado
	 * @throws ApplicationException
	 */
	public WrapperResultados servicePresiniestroDano(PreSiniestroVO psVO, DanoVO dVO, String operacion) throws ApplicationException;

	/**
	 * Método que obtiene los datos correspondientes a un presiniestro de Daños previamente guardado para su edición.
	 * @param psVO
	 * @return Objeto WrapperResultados que contiene el resultado
	 * @throws ApplicationException
	 */	
	public WrapperResultados obtieneDatosEditarDano(PreSiniestroVO psVO) throws ApplicationException;
	
	/**
	 * Metodo que obtiene una lista de todos los padecimientos para presiniestros
	 * @return Lista de los tipos de tramite
	 * @throws ApplicationException
	 */
	public List<BaseObjectVO> obtieneListaTramites() throws ApplicationException;
	
	/**
	 * Metodo que obtiene una lista de todos los padecimientos para presiniestros
	 * @return Lista de los tipos de Padecimiento
	 * @throws ApplicationException
	 */
	public List<BaseObjectVO> obtieneListaPadecimientos() throws ApplicationException;
	
	/**
	 * Metodo que obtiene los datos basicos para el agregado de un presiniestro de Beneficios
	 * @param poliza
	 * @param ramo
	 * @return VO con los valores de la poliza para beneficio
	 * @throws ApplicationException
	 */
	public BeneficioVO obtieneDatosAgregarBeneficio(String poliza, String ramo) throws ApplicationException;
	
	/**
	 * Metodo que obtiene los datos completos para el editado de un presiniestro de Beneficios
	 * @param folio
	 * @param cdRamo
	 * @param nmPoliza
	 * @return
	 * @throws ApplicationException
	 */
	public BeneficioVO obtienePresiniestroBeneficio(String folio, String cdRamo, String nmPoliza) throws ApplicationException;
	
	/**
	 * Guarda un presiniestro de Beneficios
	 * @param beneficioVO
	 * @return Resultado de la Operacion
	 * @throws ApplicationException
	 */
	public String guardaPresiniestroBeneficios(BeneficioVO beneficio, String operacion) throws ApplicationException;

	/**
	 * Obtiene la lista de los documentos de un presiniestro
	 * @param parameters
	 * @param start
	 * @param limit
	 * @return Lista de los documentos y datos de la paginacion
	 * @throws ApplicationException
	 */
	
	/**
	 * Metodo que obtiene los datos basicos para el agregado de un presiniestro de Beneficios GastosFunerarios
	 * @param poliza
	 * @param ramo
	 * @return VO con los valores de la poliza para beneficio
	 * @throws ApplicationException
	 */
	public BeneficioVO obtieneDatosAgregarBeneficioGastosFunerarios(String poliza, String ramo) throws ApplicationException;
	
	/**
	 * Metodo que obtiene los datos completos para el editado de un presiniestro de Beneficios GastosFunerarios
	 * @param folio
	 * @param cdRamo
	 * @param nmPoliza
	 * @return
	 * @throws ApplicationException
	 */
	public BeneficioVO obtienePresiniestroBeneficioGastosFunerarios(String folio, String cdRamo, String nmPoliza) throws ApplicationException;
	
	/**
	 * Guarda un presiniestro de Beneficios GastosFunerarios
	 * @param beneficioVO
	 * @return Resultado de la Operacion
	 * @throws ApplicationException
	 */
	public String guardaPresiniestroBeneficiosGastosFunerarios(BeneficioVO beneficio, String operacion) throws ApplicationException;
	
	/**
	 * Obtiene la lista de los documentos de un presiniestro
	 * @param parameters
	 * @param start
	 * @param limit
	 * @return Lista de los documentos y datos de la paginacion
	 * @throws ApplicationException
	 */
	public PagedList obtieneDocumentosPre(Map<String, String> parameters, int start, int limit) throws ApplicationException;
	
	/**
	 * Metodo que guarda los atributos de un documento asignado a un presiniestro
	 * @param params
	 * @return
	 * @throws ApplicationException
	 */
	public String guardaAtributosDocumentoPre(Map<String, String> params)throws ApplicationException;
	
	/**
	 * Metodo para obtener los 50 atributos de un documento (valores)
	 * @param params
	 * @param documento
	 * @return DocumentoVO con todos los valores de sus atributos (otvalor)
	 * @throws ApplicationException
	 */
	public DocumentoVO obtenerAtributosDocumentoPre(Map<String, String> params, DocumentoVO documento)throws ApplicationException;
	
	/**
	 * Metodo para insertar un documento de un presiniestro en la base
	 * @param params
	 * @param documento
	 * @return
	 * @throws ApplicationException
	 */
	public boolean insertaDocumento(Map<String, String> params, DocumentoVO documento)throws ApplicationException;
	
	/**
	 * Metodo para actualizar un documento de un presiniestro en la base
	 * @param params
	 * @param documento
	 * @return
	 * @throws ApplicationException
	 */
	public boolean actualizaDocumento(Map<String, String> params, DocumentoVO documento)throws ApplicationException;
	
	/**
	 * Metodo para eliminar un documento de un presiniestro en la base
	 * @param params
	 * @param documento
	 * @return
	 * @throws ApplicationException
	 */
	public boolean eliminaDocumento(Map<String, String> params, DocumentoVO documento)throws ApplicationException;
	
}
