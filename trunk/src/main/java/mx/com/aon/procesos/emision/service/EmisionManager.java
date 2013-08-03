/**
 * 
 */
package mx.com.aon.procesos.emision.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO;
import mx.com.aon.kernel.model.MpoliagrVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.procesos.emision.model.DatosAdicionalesVO;
import mx.com.aon.procesos.emision.model.DatosRolVO;
import mx.com.aon.procesos.emision.model.EmisionVO;
import mx.com.aon.procesos.emision.model.PolizaDetVO;
import mx.com.aon.procesos.emision.model.PolizaMaestraVO;
import mx.com.aon.export.model.TableModelExport;

import com.biosnet.ice.ext.elements.form.TextFieldControl;

/**
 * @author Cesar Hernandez
 *
 */
public interface EmisionManager {

	//public void buscaPolizas(Map map) throws ApplicationException;
	
	public PagedList buscaPolizas(Map<String, String> params, int start, int limit) throws ApplicationException;
	
	@SuppressWarnings("unchecked")
	public ArrayList<EmisionVO> buscaPolizas(Map params)throws ApplicationException;
	
	@SuppressWarnings("unchecked")
	public List<PolizaDetVO> consultaPolizaDetalle(Map params) throws ApplicationException;
	
	public PagedList consultaObjetoAsegurable(Map<String, String> params, int start, int limit) throws ApplicationException;
	
	public PagedList consultaFuncionPoliza(Map<String, String> params, int start, int limit) throws ApplicationException;
	
	@SuppressWarnings("unchecked")
	public PagedList consultaRecibosDetalle(Map params, int start, int limit) throws ApplicationException;
	
	public String obtienePantalla(Map<String, String> params) throws ApplicationException;
	
	public List<DatosAdicionalesVO> obtieneDatosAdicionales(Map<String, String> params) throws ApplicationException;
	
	public DatosRolVO getDatosRol(Map<String,String> params, String EndPointName) throws ApplicationException;
	
	public ArrayList<TextFieldControl> getDatosRolExt(Map<String,String> params, String EndPointName) throws ApplicationException;
	
	public PagedList getPagedList(Map<String, String> params, String EndPointName, int start, int limit) throws ApplicationException;
	
	public ArrayList<MpoliagrVO> getAgrupador(Map<String,String> params, String EndPointName) throws ApplicationException;
	
	public AyudaCoberturaCotizacionVO getAyudaCobertura(Map<String, String> parameters) throws ApplicationException;
	
	public PagedList buscaPolizasMaestras(Map<String,String> params, int start, int limit) throws ApplicationException;
	
	public String getAction(Map<String, String> params, String endPointName) throws ApplicationException;
	
	public PolizaMaestraVO getEndpoint(Map<String, String> params, String endPointName)throws ApplicationException;
	
	/**
	 * Método encargado de validar la póliza en endosos
	 * @param param
	 * @param EndPointName
	 * @return boolean
	 * @throws ApplicationException
	 */
	public boolean validaEndosoPoliza(String param, String EndPointName) throws ApplicationException;
	
   	public TableModelExport getModel(String dselemen, String dsunieco, String dsramo) throws ApplicationException;


}
