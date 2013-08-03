/**
 * 
 */
package mx.com.aon.flujos.endoso.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.flujos.cotizacion.model.ObjetoCotizacionVO;
import mx.com.aon.flujos.endoso.model.DatosPolizaVO;
import mx.com.aon.flujos.endoso.model.PolizaCancelVO;
import mx.com.aon.flujos.endoso.model.TarificarVO;
import mx.com.aon.kernel.model.MpoliagrVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.procesos.emision.model.DatosAdicionalesVO;
import mx.com.aon.procesos.emision.model.DatosRolVO;
import mx.com.aon.procesos.emision.model.EmisionVO;
import mx.com.aon.procesos.emision.model.PolizaDetVO;

import com.biosnet.ice.ext.elements.form.ComboControl;
import com.biosnet.ice.ext.elements.form.ExtElement;
import com.biosnet.ice.ext.elements.form.TextFieldControl;

/**
 * @author eflores
 * @date 28/08/2008
 *
 */
public interface EndosoManager {

    
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
    
    public ArrayList<MpoliagrVO> getAgrupador(Map<String,String> params, String EndPointName) throws ApplicationException;
    
    public PagedList getPagedList(Map<String, String> params, String EndPointName, int start, int limit) throws ApplicationException;
    
    @SuppressWarnings("unchecked")
	public List getDatosRolExt(Map<String,String> params) throws ApplicationException;
    
    @SuppressWarnings("unchecked")
	public List getDatosDetallePolizaExt(Map<String,String> params) throws ApplicationException;

	public List<ObjetoCotizacionVO> getEndosos(Map<String, String> params) throws ApplicationException;

	public void sacaEndoso(Map<String, String> parameters)throws ApplicationException;
	
	public void reversaMaquilla(Map<String, String> parameters)throws ApplicationException;

	public void guardaAgrupadores(Map<String, String> params)throws ApplicationException;
	
	public String getSuplLogico(Map<String, String> params)throws ApplicationException;
	
	public String getSuplFisico(Map<String, String> params)throws ApplicationException;
    
    public BaseObjectVO actualizaFechas(Map<String, String> params)throws ApplicationException;
	
    public void editarDatosEndoso(Map<String, String> params) throws ApplicationException;
    
    public void editarDatosEndosoTarjeta(Map<String, String> params) throws ApplicationException; 
    
    public ArrayList<ExtElement> getExtElements(Map<String,String> params, String EndPointName) throws ApplicationException;
    
    public ArrayList<ComboControl> getComboControl(Map<String,String> params, String EndPointName) throws ApplicationException;
    
    public List<DatosPolizaVO> obtieneDatosPoliza(Map<String,String> params) throws ApplicationException;
    
    public PolizaCancelVO validaPolizaCancel(Map<String,String> params) throws ApplicationException;
    
    public BaseObjectVO bajaInciso(Map<String,String> params) throws ApplicationException;
    
    public String getEndPoint(Map<String,Object> params, String EndPointName) throws ApplicationException;
    
    public String getVariableAtributoCoberturas(String tabla, String value) throws ApplicationException;

    public List<ExtElement> getItems(String clave, String cdUnieco, String cdRamo, String estado,
            String nmPoliza, String nmSituac, String nmObjeto) throws ApplicationException;
    
    /**
     * 
     * @param claveObjeto
     * @return
     * @throws ApplicationException
     */
    public List<ComboControl> getCombos(String clave, String cdUnieco, String cdRamo, String estado,
            String nmPoliza, String nmSituac, String nmObjeto) throws ApplicationException;
    
    /**
     * 
     * @param cdramo
     * @param cdtipsit
     * @return 
     * @throws ApplicationException
     */
    public List<BaseObjectVO> getTipos(String cdramo, String cdtipsit)throws ApplicationException;

    /**
     * @param params
     * @return 
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
	public List getDatosRolExt2(Map<String,String> params) throws ApplicationException;
    
    /**
     * @param  params
     * @return List<ExtElement>
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
	public List<ExtElement> getItems(Map<String,String> params, String endPointName) throws ApplicationException;
    
    /**
     * @param  params
     * @param  endPointName
     * @return List<ComboControl>
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
	public List<ComboControl> getCombos(Map<String,String> params, String endPointName) throws ApplicationException;
    
    @SuppressWarnings("unchecked")
    public List<ObjetoCotizacionVO> detalleTarificar(Map<String, String> params, String endPointName) throws ApplicationException;
    
    @SuppressWarnings("unchecked")    
    public WrapperResultados getResultadoWrapper(Map<String, String> params, String endPointName) throws ApplicationException;
    
    @SuppressWarnings("unchecked")
    public TarificarVO getResultadoTarificar(Map<String, String> params, String endPointName) throws ApplicationException;
    
    /**
     * 
     * @param parameters
     * @return BaseObjectVO suplemento Fisico
     * @throws ApplicationException
     */
    public BaseObjectVO obtieneNmsuplem(Map<String,String> parameters) throws ApplicationException;
    
    public String obtieneMensajeEndoso(String msgId)throws ApplicationException;
}
