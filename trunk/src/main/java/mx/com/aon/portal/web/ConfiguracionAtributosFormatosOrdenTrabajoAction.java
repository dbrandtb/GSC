package mx.com.aon.portal.web;

import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

import mx.com.aon.portal.model.ConfiguracionAtributoFormatoOrdenTrabajoVO;
import mx.com.aon.portal.service.ConfiguracionAtributosFormatosOrdenTrabajoManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.gseguros.exception.ApplicationException;
/**
 *   Action que atiende las peticiones de que vienen de la pantalla Configurar Atributo Formato Orden de Trabajo
 * 
 */
public class ConfiguracionAtributosFormatosOrdenTrabajoAction extends ActionSupport{

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD 
	 * Este objeto no es serializable
	 */
	private transient ConfiguracionAtributosFormatosOrdenTrabajoManager configuracionAtributosFormatosOrdenTrabajoManager;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PersonasAction.class);
	
	private boolean success;
	
	private List<ConfiguracionAtributoFormatoOrdenTrabajoVO> mConfiguracionAtributoFormatoOrdenTrabajoList;
	
	private String codFormatoOrden;
	private String codSeccion;
	
	
	private String cdFormatoOrden;
	private String cdSeccion;
	private String cdAtribu;
	private String dsAtribu;
	private String cdBloque;
	private String cdCampo;
	private String nmOrden;
	private String otTabVal;
	private String swFormat;
	private String nmlMax;
	private String nmlMin;
	private String cdExpres;
	
	private String flag;
	
	
	private int start;
	private int limit;
	private int totalCount;
	private List<ConfiguracionAtributoFormatoOrdenTrabajoVO> csoGrillaListAtr;
	
	
	/**
	 * Metodo que realiza la busqueda de datos de atributo formato orden de trabajo
	 * en base a codigo formato orden, codigo seccion 
	 * 
	 * @param cdFormatoOrden
	 * @param cdSeccion
	 * 
	 * @return success
	 * 
	 * @throws ApplicationException
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String obtenerAtributosFormatoOrdenTrabajo() throws ApplicationException{
		try{
			PagedList pagedList = configuracionAtributosFormatosOrdenTrabajoManager.buscarConfiguracionAtributoFormatoOrdenTrabajo(cdFormatoOrden, cdSeccion, start, limit);
			mConfiguracionAtributoFormatoOrdenTrabajoList = pagedList.getItemsRangeList();
			totalCount = pagedList.getTotalItems();
			success = true;
			return SUCCESS; 
		}catch(ApplicationException ae){
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch(Exception e){
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
 /**
   * Metodo que atiende una peticion de insertar o actualizar un atributo formato orden de trabajo
	 * 
	 * @return success
	 * 
	 * @throws Exception
   */
	
	public String insertarGuardarAtributosFormatoOrdenTrabajo() throws ApplicationException{
		String messageResult = "";
		try{
			
			ConfiguracionAtributoFormatoOrdenTrabajoVO configuracionAtributoFormatoOrdenTrabajoVO = new ConfiguracionAtributoFormatoOrdenTrabajoVO();
			
			for (int i=0; i<csoGrillaListAtr.size(); i++) {
				
				ConfiguracionAtributoFormatoOrdenTrabajoVO configuracionAtributoFormatoOrdenTrabajoVO_grid = csoGrillaListAtr.get(i);
				
				configuracionAtributoFormatoOrdenTrabajoVO.setCdFormatoOrden(configuracionAtributoFormatoOrdenTrabajoVO_grid.getCdFormatoOrden());
				configuracionAtributoFormatoOrdenTrabajoVO.setCdSeccion(configuracionAtributoFormatoOrdenTrabajoVO_grid.getCdSeccion());
				configuracionAtributoFormatoOrdenTrabajoVO.setCdAtribu(configuracionAtributoFormatoOrdenTrabajoVO_grid.getCdAtribu());
				configuracionAtributoFormatoOrdenTrabajoVO.setDsAtribu(configuracionAtributoFormatoOrdenTrabajoVO_grid.getDsAtribu());
				configuracionAtributoFormatoOrdenTrabajoVO.setCdBloque(configuracionAtributoFormatoOrdenTrabajoVO_grid.getCdBloque());
				configuracionAtributoFormatoOrdenTrabajoVO.setCdCampo(configuracionAtributoFormatoOrdenTrabajoVO_grid.getCdCampo());
				configuracionAtributoFormatoOrdenTrabajoVO.setNmOrden(configuracionAtributoFormatoOrdenTrabajoVO_grid.getNmOrden());
				configuracionAtributoFormatoOrdenTrabajoVO.setOtTabVal(configuracionAtributoFormatoOrdenTrabajoVO_grid.getOtTabVal());
				configuracionAtributoFormatoOrdenTrabajoVO.setSwFormat(configuracionAtributoFormatoOrdenTrabajoVO_grid.getSwFormat());
				configuracionAtributoFormatoOrdenTrabajoVO.setNmlMin(configuracionAtributoFormatoOrdenTrabajoVO_grid.getNmlMin());
				configuracionAtributoFormatoOrdenTrabajoVO.setNmlMax(configuracionAtributoFormatoOrdenTrabajoVO_grid.getNmlMax());
				configuracionAtributoFormatoOrdenTrabajoVO.setCdExpres(configuracionAtributoFormatoOrdenTrabajoVO_grid.getCdExpres());

				messageResult = configuracionAtributosFormatosOrdenTrabajoManager.agregarGuardarConfiguracionAtributoFormatoOrdenTrabajo(configuracionAtributoFormatoOrdenTrabajoVO);
				
			}
			success = true;
			addActionMessage(messageResult);
			return SUCCESS;
		}catch(ApplicationException ae){
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch(Exception e){
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
 /**
	 * Elimina un registro de la grilla previamente seleccionado
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String borrarAtributosFormatoOrdenTrabajo() throws ApplicationException{
		String messageResult = "";
		try{
			messageResult = configuracionAtributosFormatosOrdenTrabajoManager.borrarConfiguracionAtributoFormatoOrdenTrabajo(cdFormatoOrden, cdSeccion, cdAtribu);
			success = true;
			addActionMessage(messageResult);
			return SUCCESS;
		}catch(ApplicationException ae){
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch(Exception e){
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ConfiguracionAtributosFormatosOrdenTrabajoManager obtenConfiguracionAtributosFormatosOrdenTrabajoManager() {
		return configuracionAtributosFormatosOrdenTrabajoManager;
	}

	public void setConfiguracionAtributosFormatosOrdenTrabajoManager(
			ConfiguracionAtributosFormatosOrdenTrabajoManager configuracionAtributosFormatosOrdenTrabajoManager) {
		this.configuracionAtributosFormatosOrdenTrabajoManager = configuracionAtributosFormatosOrdenTrabajoManager;
	}

	public List<ConfiguracionAtributoFormatoOrdenTrabajoVO> getMConfiguracionAtributoFormatoOrdenTrabajoList() {
		return mConfiguracionAtributoFormatoOrdenTrabajoList;
	}

	public void setMConfiguracionAtributoFormatoOrdenTrabajoList(
			List<ConfiguracionAtributoFormatoOrdenTrabajoVO> configuracionAtributoFormatoOrdenTrabajoList) {
		mConfiguracionAtributoFormatoOrdenTrabajoList = configuracionAtributoFormatoOrdenTrabajoList;
	}

	public String getCdFormatoOrden() {
		return cdFormatoOrden;
	}

	public void setCdFormatoOrden(String cdFormatoOrden) {
		this.cdFormatoOrden = cdFormatoOrden;
	}

	public String getCdSeccion() {
		return cdSeccion;
	}

	public void setCdSeccion(String cdSeccion) {
		this.cdSeccion = cdSeccion;
	}

	public String getCdAtribu() {
		return cdAtribu;
	}

	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}

	public String getCodFormatoOrden() {
		return codFormatoOrden;
	}

	public void setCodFormatoOrden(String codFormatoOrden) {
		this.codFormatoOrden = codFormatoOrden;
	}

	public String getCodSeccion() {
		return codSeccion;
	}

	public void setCodSeccion(String codSeccion) {
		this.codSeccion = codSeccion;
	}

	public String getDsAtribu() {
		return dsAtribu;
	}

	public void setDsAtribu(String dsAtribu) {
		this.dsAtribu = dsAtribu;
	}

	public String getCdBloque() {
		return cdBloque;
	}

	public void setCdBloque(String cdBloque) {
		this.cdBloque = cdBloque;
	}

	public String getCdCampo() {
		return cdCampo;
	}

	public void setCdCampo(String cdCampo) {
		this.cdCampo = cdCampo;
	}

	public String getNmOrden() {
		return nmOrden;
	}

	public void setNmOrden(String nmOrden) {
		this.nmOrden = nmOrden;
	}

	public String getOtTabVal() {
		return otTabVal;
	}

	public void setOtTabVal(String otTabVal) {
		this.otTabVal = otTabVal;
	}

	public String getSwFormat() {
		return swFormat;
	}

	public void setSwFormat(String swFormat) {
		this.swFormat = swFormat;
	}

	public String getNmlMax() {
		return nmlMax;
	}

	public void setNmlMax(String nmlMax) {
		this.nmlMax = nmlMax;
	}

	public String getNmlMin() {
		return nmlMin;
	}

	public void setNmlMin(String nmlMin) {
		this.nmlMin = nmlMin;
	}

	public String getCdExpres() {
		return cdExpres;
	}

	public void setCdExpres(String cdExpres) {
		this.cdExpres = cdExpres;
	}

	public List<ConfiguracionAtributoFormatoOrdenTrabajoVO> getCsoGrillaListAtr() {
		return csoGrillaListAtr;
	}

	public void setCsoGrillaListAtr(
			List<ConfiguracionAtributoFormatoOrdenTrabajoVO> csoGrillaListAtr) {
		this.csoGrillaListAtr = csoGrillaListAtr;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}



}
