package mx.com.aon.catbo.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.catbo.model.ConfigurarEncuestaVO;
import mx.com.aon.catbo.model.OperacionCATVO;
import mx.com.aon.catbo.model.PersonaVO;
import mx.com.aon.catbo.service.AltaCasosManager;
import mx.com.aon.catbo.service.OperacionCATManager;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.util.ConnectionCallInterceptor;
import mx.com.aon.portal.util.Util;


import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

/**
 *   Action que atiende las peticiones de que vienen de la pantalla Notificaciones
 * 
 */
@SuppressWarnings("serial")
public class ListaOperacionesCATAction extends AbstractListAction implements SessionAware{
	protected static ThreadLocal<UserVO> localUser = new ThreadLocal<UserVO>();

	/**
	 * Logger de la clase para monitoreo y registro de comportamiento
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ListaOperacionesCATAction.class);

	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient AltaCasosManager altaCasosManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo VO
	 * con los valores de la consulta.
	 */
	private List<PersonaVO> mPersonaList;
		
	private String pv_cdperson_i;
	
	
	
	/**
	 * Manager con implementacion de Endpoint para la consulta a BD
	 */	
	private transient OperacionCATManager operacionCATManager;
	/**
	 * Atributo de respuesta interpretado por strust con la lista de beans tipo AyudaCoberturasVO
	 * con los valores de la consulta.
	 */
	private List<OperacionCATVO> mOperacionCatList;

	private String cdUniEco;
    private String dsUniEco;
    private String cdRamo;
    private String dsRamo;
    private String cdElemento;
    private String dsElemento;
    private String cdProceso;
    private String dsProceso;
    private String cdGuion;
    private String dsGuion;
	private String cdTipGuion;
	private String dsTipGuion;
	private String indInicial;
	private String status;

	private List<OperacionCATVO> mOperacionCatLista;
	private String cdTipGui;
    
	private List<ConfigurarEncuestaVO> mEncuestaPendienteLista;
	
	private String cdPerson;
	
	private String funcionNombre;
	private String rol;
	
	private boolean success;
	
	
	/**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
		
	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
	
private Map session;
	
	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}



	@SuppressWarnings("unchecked")
	public String cmdGetPersonaCasoClick()throws Exception
	{
		try
		{
			mPersonaList=new ArrayList<PersonaVO>();
			PersonaVO personaVO = this.altaCasosManager.obtenerPersonCaso(pv_cdperson_i);
			mPersonaList.add(personaVO);
			
			UserVO userVO = new UserVO();
			ThreadLocal<UserVO> tl = ConnectionCallInterceptor.getLocalUser();
            userVO = tl.get();

				if(userVO != null && userVO.getUser()!= null){
	                logger.debug("seteando en el caso el usuario logueado al sistema: " + userVO.getUser());
	                personaVO.setCdElemento(userVO.getCdElemento());
				}
				
			success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	/**
	 * Metodo que realiza una búsqueda en base a criterios de búsquedas
	 *  
	 * @param 
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	public String cmdGetFuncionNombreClick()throws Exception
	{
		try
		{
			UserVO usuario = (UserVO) session.get("USUARIO");

				if(usuario != null && usuario.getUser()!= null){
	                logger.debug("seteando en el caso el usuario logueado al sistema: " + usuario.getUser());
	                if (usuario.getRolActivo().getObjeto().getValue()!=null)
	                {
	                	rol=usuario.getRolActivo().getObjeto().getValue();
	                }
	                funcionNombre=rol + " - "+usuario.getUser();
				}
			success = true;
            return SUCCESS;
        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

	/**
	 * Metodo que realiza la busqueda de guiones en base a
	 * en base a descripcion de aseguradora,elemento,guion,proceso,tipo de guion,ramo
	 * 
	 * @param dsUniEco
	 * @param dsElemento
	 * @param dsGuion
	 * @param dsProceso
	 * @param dsTipGuion
	 * @param dsRamo
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarClick() throws Exception{
		try{
            PagedList pagedList = this.operacionCATManager.buscarGuiones(dsUniEco, dsElemento, dsGuion, dsProceso, dsTipGuion, dsRamo, start, limit);
            mOperacionCatList = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();                                                    
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

	
	
	/**
	 * Metodo que realiza la busqueda de dialogos para un guion en base a
	 * en base a descripcion de aseguradora,elemento,guion,proceso,tipo de guion,ramo
	 * 
	 * @param dsUniEco
	 * @param dsElemento
	 * @param dsGuion
	 * @param dsProceso
	 * @param dsTipGuion
	 * @param dsRamo
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarDialogosClick() throws Exception{
		try{
            List pagedList = this.operacionCATManager.buscarDialogosGuion(cdGuion);
            mOperacionCatList = pagedList;
            success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	/**
	 * Metodo que realiza la busqueda de dialogos para un guion en base a
	 * en base a descripcion de aseguradora,elemento,guion,proceso,tipo de guion,ramo
	 * 
	 * @param dsUniEco
	 * @param dsElemento
	 * @param dsGuion
	 * @param dsProceso
	 * @param dsTipGuion
	 * @param dsRamo
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String cmdBuscarDialogos() throws Exception{
		try{
			PagedList pagedList = operacionCATManager.buscarDialogosGuiones(cdGuion, start, limit);
			mOperacionCatLista = pagedList.getItemsRangeList();
            totalCount = pagedList.getTotalItems();                                                    
            success = true;
            return SUCCESS; 
			
	
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	
	
	
	/**
	 * Metodo que obtiene un Guion.
	 * 
	 * @param cdUniEco
	 * @param cdElemento
	 * @param cdGuion
	 * @param cdProceso
	 * @param cdRamo
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdGetGuionClick()throws Exception
	{
		try
		{
			mOperacionCatList=new ArrayList<OperacionCATVO>();
			OperacionCATVO operacionCATVO = operacionCATManager.getGuion(cdUniEco, cdElemento, cdGuion, cdProceso, cdRamo);
			mOperacionCatList.add(operacionCATVO);
			success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

	/**
	 * Metodo que busca un conjunto de guiones  
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarGuionClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			   contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "Guion." + exportFormat.getExtension();
			TableModelExport model =  operacionCATManager.getModel(dsUniEco, dsElemento, dsGuion, dsProceso, dsTipGuion, dsRamo);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}


	
	/**
	 * Metodo que busca un conjunto de guiones  
	 * y exporta el resultado en Formato PDF, Excel, etc.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdExportarDialogosGuionClick() throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug( "Formato : " + formato );
		}
		
		try {
			   contentType = Util.getContentType(formato);
	            if (logger.isDebugEnabled()) {
	                logger.debug( "content-type : " + contentType );
	            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato); 
			filename = "DialogosGuion." + exportFormat.getExtension();
			TableModelExport model =  operacionCATManager.getModelDialogo(cdUniEco, cdElemento, cdGuion, cdProceso, cdTipGuion, cdRamo);
			inputStream = exportFormat.export(model);
		} catch (Exception e) {
			logger.error("Exception en Action Export",e);
		}
		return SUCCESS;
	}


	/**
	 * Metodo que obtiene un Script de Atencion Incial de catbo.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdObtenerDialogoGuionInicialClick()throws Exception
	{
		try
		{
			mOperacionCatList= new ArrayList<OperacionCATVO>();
			OperacionCATVO operacionCATVO=operacionCATManager.getScriptAtencionInicial(cdTipGui,indInicial);
			mOperacionCatList.add(operacionCATVO);
			success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}
	
	/**
	 * Metodo que obtiene un Script de Atencion Incial de catbo.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String cmdObtenerEncuestasPendientes()throws Exception
	{
		try
		{
			mEncuestaPendienteLista= new ArrayList<ConfigurarEncuestaVO>();
			ConfigurarEncuestaVO configurarEncuestaVO=operacionCATManager.getObtenerEncuestasPendientes(cdPerson);
			mEncuestaPendienteLista.add(configurarEncuestaVO);
			success = true;
            return SUCCESS;
        }catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
	}

	public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public OperacionCATManager getOperacionCATManager() {
		return operacionCATManager;
	}


	public void setOperacionCATManager(OperacionCATManager operacionCATManager) {
		this.operacionCATManager = operacionCATManager;
	}


	public List<OperacionCATVO> getMOperacionCatList() {
		return mOperacionCatList;
	}


	public void setMOperacionCatList(List<OperacionCATVO> operacionCatList) {
		mOperacionCatList = operacionCatList;
	}


	public String getCdUniEco() {
		return cdUniEco;
	}


	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}


	public String getDsUniEco() {
		return dsUniEco;
	}


	public void setDsUniEco(String dsUniEco) {
		this.dsUniEco = dsUniEco;
	}


	public String getCdRamo() {
		return cdRamo;
	}


	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}


	public String getDsRamo() {
		return dsRamo;
	}


	public void setDsRamo(String dsRamo) {
		this.dsRamo = dsRamo;
	}


	public String getCdElemento() {
		return cdElemento;
	}


	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}


	public String getDsElemento() {
		return dsElemento;
	}


	public void setDsElemento(String dsElemento) {
		this.dsElemento = dsElemento;
	}


	public String getCdProceso() {
		return cdProceso;
	}


	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}


	public String getDsProceso() {
		return dsProceso;
	}


	public void setDsProceso(String dsProceso) {
		this.dsProceso = dsProceso;
	}


	public String getCdGuion() {
		return cdGuion;
	}


	public void setCdGuion(String cdGuion) {
		this.cdGuion = cdGuion;
	}


	public String getDsGuion() {
		return dsGuion;
	}


	public void setDsGuion(String dsGuion) {
		this.dsGuion = dsGuion;
	}


	public String getCdTipGuion() {
		return cdTipGuion;
	}


	public void setCdTipGuion(String cdTipGuion) {
		this.cdTipGuion = cdTipGuion;
	}


	public String getDsTipGuion() {
		return dsTipGuion;
	}


	public void setDsTipGuion(String dsTipGuion) {
		this.dsTipGuion = dsTipGuion;
	}


	public String getIndInicial() {
		return indInicial;
	}


	public void setIndInicial(String indInicial) {
		this.indInicial = indInicial;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getFormato() {return formato;}
	public void setFormato(String formato) {this.formato = formato;}

	public InputStream getInputStream() {return inputStream;}
	public void setInputStream(InputStream inputStream) {this.inputStream = inputStream;}

	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}

	public void setExportMediator(ExportMediator exportMediator) {this.exportMediator = exportMediator;}




	public String getCdTipGui() {
		return cdTipGui;
	}



	public void setCdTipGui(String cdTipGui) {
		this.cdTipGui = cdTipGui;
	}




	public List<OperacionCATVO> getMOperacionCatLista() {
		return mOperacionCatLista;
	}



	public void setMOperacionCatLista(List<OperacionCATVO> operacionCatLista) {
		mOperacionCatLista = operacionCatLista;
	}



	public List<ConfigurarEncuestaVO> getMEncuestaPendienteLista() {
		return mEncuestaPendienteLista;
	}



	public void setMEncuestaPendienteLista(
			List<ConfigurarEncuestaVO> encuestaPendienteLista) {
		mEncuestaPendienteLista = encuestaPendienteLista;
	}



	public String getCdPerson() {
		return cdPerson;
	}



	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getFuncionNombre() {
		return funcionNombre;
	}

	public void setFuncionNombre(String funcionNombre) {
		this.funcionNombre = funcionNombre;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}



	
	
	
}