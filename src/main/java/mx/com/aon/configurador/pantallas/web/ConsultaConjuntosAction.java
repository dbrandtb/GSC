package mx.com.aon.configurador.pantallas.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import mx.com.aon.configurador.pantallas.model.ClienteCorpoVO;
import mx.com.aon.configurador.pantallas.model.ConjuntoPantallaVO;
import mx.com.aon.export.ExportMediator;
import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.service.GridPagerManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.Util;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Clase Action para el control y visualizacion de datos para la pantalla de busqueda de conjuntos
 * 
 * @author aurora.lozada
 * 
 */

public class ConsultaConjuntosAction extends PrincipalConfPantallaAction implements GridPagerManager {

    private static final long serialVersionUID = 8841819323299691792L;

    /**
     * 
     */
    private List<BaseObjectVO> procesoList;

    /**
     * 
     */
    private List<ClienteCorpoVO> clienteList;

    /**
     * 
     */
    private String nombreConjunto;

    /**
     * 
     */
    private String proceso;

    private String cliente;


    private boolean success;

    /**
	 * Atributo de respuesta interpretado por Struts2 con la lista de beans con los valores de la consulta 
	 * (en este caso son de tipo ConjuntoPantallaVO).
	 */
    @SuppressWarnings("unchecked")
	protected List pagedDataList;
    
    /**
     * Atributo agregado como parametro de la petición por struts que indica
     * el inicio de el número de linea en cual iniciar
     */
    protected int start;
    
    /**
     * Atributo agregado como parametro de la petición por struts que indica la cantidad
     * de registros a ser consultados
     */
    protected int limit=20;
    
    /**
     * Atributo de respuesta interpretado por strust con el número de registros totales
     * que devuelve la consulta.
     */
    protected int totalCount;
    
    
	/**
	 * Atributo inyectado por spring el cual direcciona a travez del tipo de formato para generar 
	 * el archivo a ser exportado
	 */
	private ExportMediator exportMediator;
	
    /**
	 * Atributo agregado por struts que contiene el tipo de formato a ser exportado
	 */
	private String formato;
	 //
	protected String contentType;
	
	/**
	 * Atributo de respuesta interpretado por strust con el nombre del archivo generado 
	 */
	private String filename;
	
	/**
	 * Atributo de respuesta con el flujo de datos para regresar el archivo generado.
	 */
	private InputStream inputStream;
	
    
    private String cdElemento;
    
    private String cdProceso;

    /**
     * @return the success
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public void prepare() throws Exception {
        super.prepare();

        boolean isDebug = logger.isDebugEnabled();

        if (isDebug) {
            logger.debug("########Enterintg to prepare in Consulta ...");
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.ActionSupport#validate()
     */
    @Override
    public void validate() {
        boolean isDebug = logger.isDebugEnabled();

        if (isDebug) {
            logger.debug("#########Enterintg into method validate in Consulta ...");
        }
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String execute() throws Exception {
        boolean isDebug = logger.isDebugEnabled();

        if (isDebug) {
            logger.debug("####Enterintg into method execute in Consulta ...");
            logger.debug("###... procesos en execute" + session.get(COMBO_PROCESOS_LIST));
            logger.debug("### clientes execute...." + session.get(COMBO_CORPORATIVO_LIST));
        }

        
        procesoList = (List<BaseObjectVO>) session.get(COMBO_PROCESOS_LIST);        
        
        clienteList = (List<ClienteCorpoVO>) session.get(COMBO_CORPORATIVO_LIST);

        return SUCCESS;
    }

    /*
     * Metodo para la consulta y obtencion de conjuntos de pantallas
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    /*
	//No utilizado, ahora se utiliza consultarJson()
    public String consultar() throws Exception {

        boolean isDebug = logger.isDebugEnabled();

        if (isDebug) {
            logger.debug("#######Enterintg into method consultar...");

        }

        if (StringUtils.isNotBlank(proceso) && StringUtils.isNotBlank(cliente)) {
            logger.debug("Entró a la lista Json....");
            logger.debug("nombre conjunto-----" + nombreConjunto);
            logger.debug("proceso---------" + proceso);
            logger.debug("cliente--------" + cliente);

            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("NOMBRE_CONJUNTO", nombreConjunto);
            parameters.put("PROCESO", proceso);
            parameters.put("CLIENTE", cliente);

            resultadoGrid = configuradorManager.buscaConjuntos(parameters);

        } else {
            logger.debug("Entrando sin parametros....");
            logger.debug("nombre conjunto-----" + nombreConjunto);
            logger.debug("proceso---------" + proceso);
            logger.debug("cliente--------" + cliente);
            resultadoGrid = new ArrayList<ConjuntoPantallaVO>();
            success = false;
        }

        success = true;
        return SUCCESS;

    }
    */
    
    public String consultarJson() throws Exception{
    	
    	if (logger.isDebugEnabled()) {
            logger.debug("#######Enterintg into method consultarJson...");
        }
    	
    	//TODO: REVISAR
    	session.remove("elementos_proceso_master");
    	
    	try {
		    	if (StringUtils.isNotBlank(proceso) && StringUtils.isNotBlank(cliente)) {
		    		
					if (logger.isDebugEnabled()) {
			            logger.debug("Entró a la lista Json....");
		    	        logger.debug("start-----" + start);
		        	    logger.debug("limit-----" + limit);
		            	logger.debug("nombre conjunto-----" + nombreConjunto);
			            logger.debug("proceso---------" + proceso);
		    		    logger.debug("cliente--------" + cliente);
					}
		
		            Map<String, String> parametros = new HashMap<String, String>();
		        	parametros.put("NOMBRE_CONJUNTO", this.nombreConjunto);
		        	parametros.put("PROCESO", this.proceso);
		        	parametros.put("CLIENTE", this.cliente);
		        	
		        	PagedList pagedList = pagerManager.getPagedData(parametros, "BUSCA_CONJUNTOS_PANTALLAS", start, limit);
		        	this.pagedDataList = pagedList.getItemsRangeList();
		        	this.totalCount = pagedList.getTotalItems();
		        	
		        	if(logger.isDebugEnabled()){
		        		logger.debug("pagedDataList:" + pagedDataList);
		        	}
		
		        }  else {
		            
		        	if(logger.isDebugEnabled()){
		        		logger.debug("Entrando sin parametros....");
			            logger.debug("nombre conjunto-----" + nombreConjunto);
		    	        logger.debug("proceso---------" + proceso);
		        	    logger.debug("cliente--------" + cliente);
		            }
		            
		            this.pagedDataList = new ArrayList<ConjuntoPantallaVO>();
		            this.totalCount = 0;
		            success = false;
		        }
		    	
		    	//para notificar la busqueda exitosa ponemos el atributo success=true (se convertira a json)
		    	success = true;
		    	
		    	return SUCCESS;
    	} catch (ApplicationException ae) {
    		success = false;
    		addActionError(ae.getMessage());
    		return SUCCESS;
    	} catch (Exception e) {
    		success = false;
    		addActionError(e.getMessage());
    		return SUCCESS;
    	}
    }


    public String cmdExportar () throws Exception {
		try {
			contentType = Util.getContentType(formato);
            if (logger.isDebugEnabled()) {
                logger.debug( "content-type : " + contentType );
            }
			ExportView exportFormat = (ExportView)exportMediator.getView(formato);
			filename = "Personas." + exportFormat.getExtension();
			HashMap map = new HashMap<String, String>();
			map.put("NOMBRE_CONJUNTO", nombreConjunto);
			map.put("PROCESO", cdProceso);
			map.put("CLIENTE", cdElemento);
			TableModelExport model = configuradorManager.getModel(map);
			inputStream = exportFormat.export(model);
			success = true;
		} catch (Exception e) {
			success = false;
		}
    	return SUCCESS;
    }
    

    /**
     * @return the nombreConjunto
     */
    public String getNombreConjunto() {
        return nombreConjunto;
    }

    /**
     * @param nombreConjunto the nombreConjunto to set
     */
    public void setNombreConjunto(String nombreConjunto) {
        this.nombreConjunto = nombreConjunto;
    }

    /**
     * @return the proceso
     */
    public String getProceso() {
        return proceso;
    }

    /**
     * @param proceso the proceso to set
     */
    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the procesoList
     */
    public List<BaseObjectVO> getProcesoList() {
        return procesoList;
    }

    /**
     * @return the clienteList
     */
    public List<ClienteCorpoVO> getClienteList() {
        return clienteList;
    }

    /**
     * @param procesoList the procesoList to set
     */
    public void setProcesoList(List<BaseObjectVO> procesoList) {
        this.procesoList = procesoList;
    }

    /**
     * @param clienteList the clienteList to set
     */
    public void setClienteList(List<ClienteCorpoVO> clienteList) {
        this.clienteList = clienteList;
    }

	@SuppressWarnings("unchecked")
	public List getPagedDataList() {
		return pagedDataList;
	}

	@SuppressWarnings("unchecked")
	public void setPagedDataList(List pagedDataList) {
		this.pagedDataList = pagedDataList;
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

	
	public void setExportMediator(ExportMediator exportMediator) {
		this.exportMediator = exportMediator;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getCdElemento() {
		return cdElemento;
	}

	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}

	public String getCdProceso() {
		return cdProceso;
	}

	public void setCdProceso(String cdProceso) {
		this.cdProceso = cdProceso;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


}