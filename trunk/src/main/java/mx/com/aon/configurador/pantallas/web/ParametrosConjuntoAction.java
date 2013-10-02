package mx.com.aon.configurador.pantallas.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import mx.com.aon.configurador.pantallas.model.ArchivoVo;
import mx.com.aon.configurador.pantallas.model.BackBoneResultVO;
import mx.com.aon.configurador.pantallas.model.CarpetaVo;
import mx.com.aon.configurador.pantallas.model.ClienteCorpoVO;
import mx.com.aon.configurador.pantallas.model.ConjuntoPantallaVO;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

/**
 * Clase Action para el control y visualizacion de datos de la seccion de Parametros de Conjunto de pantalla
 * 
 * @author aurora.lozada
 * 
 */
public class ParametrosConjuntoAction extends PrincipalConfPantallaAction {

	/**
	 * 
	 */
    private static final long serialVersionUID = 8113736117779249695L;

    /**
     * 
     */
    public static final String CD_CONJUNTO = "cd_conjunto";
    
    /**
     * 
     */
    public static final String ID = "id";


    /**
     * 
     */
    private List<ConjuntoPantallaVO> registroList;

    
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
    private List<BaseObjectVO> productoList;
    
    /**
     * 
     */
    private List<BaseObjectVO> situacionesList;
    
    /**
     * 
     */
    private List<BaseObjectVO> pantallaList;
    
    /**
     * 
     */
    private List<BaseObjectVO> pantallaCopiaList;
    
    /**
     * 
     */
    private List<CarpetaVo> elementos = new ArrayList<CarpetaVo>();

    /**
     * 
     */
    private String id;

    /**
     * 
     */
    private String nombreConjunto;

    /**
     * 
     */
    private String descripcion;

    /**
     * 
     */
    private String cdConjunto;

    // /COMBOS
    private String proceso;

    private String cliente;

    private String producto;

    private String hproceso;

    private String hcliente;

    private String hproducto;

    private String hclienteCopia;    
    
    private String hsituacion;
    
    
    private String claveSituacion;
    

    // ELEMENTOS COPIA
    private String procesoOriginal;
    private String clienteOriginal;
    private String productoOriginal;
    private String nombreConjuntoOriginal;
    private String descripcionConjuntoOriginal;
    private String procesoCopia;
    private String clienteCopia;
    private String productoCopia;
    private String nombreConjuntoCopia;
    private String descripcionConjuntoCopia;
    private String cdProceso;
    private String cdProducto;
    private String resultado;

    private boolean success;

    private String[] originalPantallas;

    private String[] copiaPantallas;

    private String ejemAreaTrabajo;
    
    private String parameterValueItem;

    /**
     * @return the ejemAreaTrabajo
     */
    public String getEjemAreaTrabajo() {
        return ejemAreaTrabajo;
    }

    /**
     * @param ejemAreaTrabajo the ejemAreaTrabajo to set
     */
    public void setEjemAreaTrabajo(String ejemAreaTrabajo) {
        this.ejemAreaTrabajo = ejemAreaTrabajo;
    }

    @Override
    public void prepare() throws Exception {
        super.prepare();

        boolean isDebug = logger.isDebugEnabled();

        if (isDebug) {
            logger.debug("### Enterintg to prepare in ParametrosConjunto ...");
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
            logger.debug("### Enterintg into method validate in ParametrosConjunto ...");
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
            logger.debug("### Enterintg into method execute in ParametrosConjunto ...");
        }

        logger.debug("###... procesos en execute" + session.get(COMBO_PROCESOS_LIST));
        procesoList = (List<BaseObjectVO>) session.get(COMBO_PROCESOS_LIST);

        logger.debug("### clientes execute...." + session.get(COMBO_CORPORATIVO_LIST));
        clienteList = (List<ClienteCorpoVO>) session.get(COMBO_CORPORATIVO_LIST);

        return SUCCESS;
    }

    /**
     * Metodo que carga la pantalla del configurador y pone en session la clave del conjunto.
     * 
     * @return Cadena INPUT
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String cargarConfigurador() throws Exception {
        boolean isDebug = logger.isDebugEnabled();

        if (isDebug) {
            logger.debug("#######Enterintg into method cargarConfigurador...");
        }

        logger.debug("&&&&---ID recibido" + id);
        session.put(ID, id);

        return INPUT;
    }

    /**
     * Metodo que obtiene el registro de un conjunto de pantallas
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    public String obtenerRegistro() throws Exception {
        boolean isDebug = logger.isDebugEnabled();
        if (isDebug) {
            logger.debug("#######Enterintg into method OBTENER_REGISTRO...");
        }
        ConjuntoPantallaVO conjunto = new ConjuntoPantallaVO();

        id = (String) session.get(ID);
        logger.debug("&&&&---ID de session" + id);

        //TODO: REVISAR
        session.remove("elementos_proceso_master");
        
        registroList = new ArrayList<ConjuntoPantallaVO>();
        
        if (StringUtils.isNotBlank(id)) {
            logger.debug("Entrando a la Opcion----- Cargar registro....");
            conjunto = configuradorManager.getConjunto(id);

            registroList.add(conjunto);
        } else {
            logger.debug("Entrando sin id conjunto en la Opcion ----- cargar registro....");
            conjunto.setCdConjunto("");
            conjunto.setCdProceso("");
            conjunto.setProceso("");
            conjunto.setCdCliente("");
            conjunto.setCliente("");
            conjunto.setCdRamo("");
            conjunto.setDsRamo("");
            conjunto.setNombreConjunto("");
            conjunto.setDescripcion("");
            conjunto.setTipoSituacion("");
            conjunto.setDsTipoSituacion("");
            
            registroList.add(conjunto);

        }
        success = true;
        return SUCCESS;
    }

    /**
     * Metodo que obtiene el listado de productos conforme al cliente selecionado.
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String obtenerProductos() throws Exception {
        boolean isDebug = logger.isDebugEnabled();
        if (isDebug) {
            logger.debug("#######Enterintg into method obtenerProductos...");
        }

        if (StringUtils.isBlank(hcliente) || hcliente.equals("undefined")) {
            logger.debug("Entrando sin id del cliente en la Opcion ----- cargar productos....");
            logger.debug("hcliente-----" + hcliente);
            productoList = new ArrayList<BaseObjectVO>();
            success = false;
        } else {

            logger.debug("Entrando a la Opcion----- cargar productos....");
            logger.debug("id del cliente-----" + hcliente);
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("ID_CLIENTE", hcliente);
            productoList = catalogManager.getItemList("OBTIENE_PRODUCTOS_CLIENTE", parameters);

            success = true;
        }
        return SUCCESS;
    }
    
    
    @SuppressWarnings("unchecked")
	public String obtieneSituaciones() throws Exception {
    	//hproducto
    	logger.debug("OBTIENE_TIPO_SITUACION con id" +  hproducto );
    	logger.debug("OBTIENE_TIPO_SITUACION con parameterValueItem" +  parameterValueItem );
    	
    	situacionesList =  catalogManager.getItemList("OBTIENE_TIPO_SITUACION", parameterValueItem );
    	
    	return SUCCESS;
    }
    

    /**
     * Metodo que limpia elementos en sesión de la aplicación.
     * 
     * @return Cadena INPUT
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String limpiar() throws Exception {
        boolean isDebug = logger.isDebugEnabled();
        if (isDebug) {
            logger.debug("#######Enterintg into method LIMPIAR SESION...");
        }
        this.cdConjunto = "";
        session.put(ID, "");
        this.nombreConjunto = "";
        this.descripcion = "";
        this.proceso = "";
        this.cliente = "";
        this.producto = "";
        this.hproceso = "";
        this.hcliente = "";
        this.hproducto = "";

        success = true;
        return SUCCESS;
    }

    /**
     * Metodo que carga el conjunto a copiar y la lista de pantallas definidas en el conjunto original.
     * 
     * @return Cadena INPUT
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String cargarConjuntoCopiar() throws Exception {
        boolean isDebug = logger.isDebugEnabled();
        if (isDebug) {
            logger.debug("#######Enterintg into method CARGAR CONJUNTO COPIAR...");
        }

        if (StringUtils.isNotBlank(cdConjunto)) {
            logger.debug("Entrando a la Opcion----- Cargar Conjunto en Copiar...");
            logger.debug("id del conjunto-----" + cdConjunto);

            // //Metodo para obtener conjunto original
            ConjuntoPantallaVO cargaConjuntoOriginal = configuradorManager.getConjunto(cdConjunto);

            // /////////Original////////////////////////////
            setProcesoOriginal(cargaConjuntoOriginal.getProceso());
            setClienteOriginal(cargaConjuntoOriginal.getCliente());
            setProductoOriginal(cargaConjuntoOriginal.getDsRamo());
            setNombreConjuntoOriginal(cargaConjuntoOriginal.getNombreConjunto());
            setDescripcionConjuntoOriginal(cargaConjuntoOriginal.getDescripcion());
            // /Hidden
            setCdConjunto(cargaConjuntoOriginal.getCdConjunto());
            setCdProceso(cargaConjuntoOriginal.getCdProceso());
            setCdProducto(cargaConjuntoOriginal.getCdRamo());

            // /////////Copia////////////////////////////
            this.setProcesoCopia(procesoOriginal);
            this.setProductoCopia(productoOriginal);
            
            logger.debug("Proceso original--- " + procesoOriginal);
            logger.debug("Cliente original--- " + clienteOriginal);
            logger.debug("Producto original--- " + productoOriginal);
            logger.debug("Nombre Conjunto Original--- " + nombreConjuntoOriginal);
            logger.debug("cdconjunto--- " + cdConjunto);
            logger.debug("cdproceso--- " + cdProceso);
            logger.debug("cdproducto--- " + cdProducto);

            clienteList = (List<ClienteCorpoVO>) session.get(COMBO_CORPORATIVO_LIST);
            // //optiontransferselect///////////////////////////////////////////
            pantallaList = (List<BaseObjectVO>) configuradorManager.getPantallasConjunto(cdConjunto);
            pantallaCopiaList = new ArrayList<BaseObjectVO>();

        } else {
            logger.debug("Entrando a la Opcion sin id Conjunto----- Cargar Conjunto en Copiar....");
            logger.debug("id del conjunto-----" + cdConjunto);

        }

        return INPUT;
    }

    /**
     * Metodo que copia un conjunto de pantallas.
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String copiarConjunto() throws Exception {
        boolean isDebug = logger.isDebugEnabled();
        boolean tieneProducto = false;

        if (isDebug) {
            logger.debug("#######Enterintg into method COPIAR CONJUNTO...");
        }

        logger.debug("id producto original-----" + cdProducto);
        logger.debug("id del cliente copia-----" + clienteCopia);
        logger.debug("id del conjunto-----" + cdConjunto);

        // ///ver si tiene el cliente el producto original///////////////////
        List<BaseObjectVO> productoListCopia = new ArrayList<BaseObjectVO>();
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ID_CLIENTE", clienteCopia);
        productoListCopia = catalogManager.getItemList("OBTIENE_PRODUCTOS_CLIENTE", parameters);

        for (BaseObjectVO baseObjectVO : productoListCopia) {
            logger.debug("id producto....." + baseObjectVO.getValue());
            if (StringUtils.equals(cdProducto, baseObjectVO.getValue())) {
                tieneProducto = true;
            }
        }

        logger.debug("####tieneProducto......" + tieneProducto);

        try {
        		if (tieneProducto && StringUtils.isNotBlank(cdConjunto)) {
		            logger.debug("Entrando a la Opcion----- Copiar Conjunto....");
		            logger.debug("id del conjunto-----" + cdConjunto);
		            logger.debug("id del producto original-----" + cdProducto);
		            logger.debug("id del cliente copia-----" + clienteCopia);
		            logger.debug("nombre del nuevo conjunto-----" + nombreConjuntoCopia);
		            logger.debug("descripcion del nuevo conjunto-----" + descripcionConjuntoCopia);
		
		            logger.debug("Numero de elementos de pantallas copia -----" + copiaPantallas.length);
		
		            ConjuntoPantallaVO conjuntoCopiar = new ConjuntoPantallaVO();
		            conjuntoCopiar.setCdConjunto(cdConjunto);
		            conjuntoCopiar.setCdCliente(clienteCopia);
		            conjuntoCopiar.setNombreConjunto(nombreConjuntoCopia);
		            conjuntoCopiar.setDescripcion(descripcionConjuntoCopia);
		
		            logger.debug("conjuntoCopiar.getCdConjunto-----" + conjuntoCopiar.getCdConjunto());
		
		            // Metodo para copiar el conjunto de pantallas
		            BackBoneResultVO backBoneResultVO = configuradorManager.copiarConjunto(conjuntoCopiar);
		            cdConjunto = backBoneResultVO.getOutParam();
		            addActionMessage(backBoneResultVO.getMsgText());
		            logger.debug("Cd del conjunto copiado----" + cdConjunto);
		
		            WrapperResultados res = configuradorManager.copiarPantalla(cdConjunto, copiaPantallas, clienteCopia);
		            resultado = res.getMsgText();
		            
		            success = true;
		
		        } else {
		            logger.debug("Entrando a la Opcion sin id----- Copiar Conjunto....");
		            logger.debug("id del conjunto-----" + cdConjunto);
		            logger.debug("proceso ---" + proceso);
		            success = false;
		        }
        } catch (ApplicationException ae) {
        	success = false;
        	logger.error("ERROR "+ ae, ae);
        	addActionError(ae.getMessage());
        	resultado = ae.getMessage();
        } catch (Exception e) {
        	success = false;
        	addActionError(e.getMessage());
        	logger.error("ERROR "+ e, e);
        	resultado = "Consulte a Soporte";
        }
        
        return SUCCESS;
    }

    /**
     * Metodo que guarda o actualiza un conjunto de pantallas.
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String salvar() throws Exception {
        boolean isDebug = logger.isDebugEnabled();
        
        
        if( StringUtils.isNotBlank(hsituacion) &&  "A ".equals(hsituacion)  ){
        	hsituacion = "A+";
        }
        
        if (isDebug) {
            logger.debug("#######------Enterintg into method SALVAR...");
            logger.debug("id del conjunto-----" + cdConjunto);
            logger.debug("proceso ---" + hproceso);
            logger.debug("cliente ---" + hcliente);
            logger.debug("producto ---" + hproducto);
            logger.debug("nombreConjunto ---" + nombreConjunto);
            logger.debug("descripcion ---" + descripcion);
            logger.debug("ejemAreaTrabajo ---" + ejemAreaTrabajo);
            logger.debug("#### hsituacion--- " + hsituacion );
        }
        
        
        
        
        try {
	        ConjuntoPantallaVO conjuntoSalvar = new ConjuntoPantallaVO();
	        conjuntoSalvar.setNombreConjunto(nombreConjunto);
	        conjuntoSalvar.setDescripcion(descripcion);
	
	        if (StringUtils.isNotBlank(cdConjunto)) {
	            logger.debug("Entrando a la Opcion----- Actualizar un conjunto....");
	            conjuntoSalvar.setCdConjunto(cdConjunto);
	                        
	            //conjuntoSalvar.setCdProceso(null);
	            //conjuntoSalvar.setCdCliente(null);
	            //conjuntoSalvar.setCdRamo(null);
	
	            // Metodo para salva el conjunto de pantallas
	            WrapperResultados res = configuradorManager.guardaConjunto(conjuntoSalvar);
	            cdConjunto = res.getResultado();
	            addActionMessage(res.getMsgText());
	            session.put(ID, cdConjunto);
	
	            logger.debug("CDCONJUNTO despues de actualizar---" + cdConjunto);
	        } else {
	            logger.debug("Entrando a la Opcion----- Guardar nuevo conjunto....");
	            conjuntoSalvar.setCdConjunto(null);
	            conjuntoSalvar.setCdProceso(hproceso);
	            conjuntoSalvar.setCdCliente(hcliente);
	            conjuntoSalvar.setCdRamo(hproducto);
	            conjuntoSalvar.setTipoSituacion(hsituacion);
	
	            //this.cdConjunto = configuradorManager.guardaConjunto(conjuntoSalvar);
	            WrapperResultados res = configuradorManager.guardaConjunto(conjuntoSalvar);
	            cdConjunto = res.getResultado();
	            addActionMessage(res.getMsgText());
	            logger.debug("CDCONJUNTO despues de insertar uno nuevo--" + cdConjunto);
	
	            session.put(ID, cdConjunto);
	
	        }
	
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

    /**
     * Metodo que obtiene el arbol de los Master.
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    public String obtenerTreeMaster() throws Exception {
        boolean isDebug = logger.isDebugEnabled();
        if (isDebug) {
            logger.debug("#######Enterintg into method obtenerTreeMaster...");
        }

        CarpetaVo rama = null;
        List<ArchivoVo> listaFileJson = null;
        for (int j = 0; j < 5; j++) {
            listaFileJson = new ArrayList<ArchivoVo>();
            rama = new CarpetaVo();
            rama.setCls("file");
            rama.setExpanded(false);
            rama.setId("R" + j);
            rama.setLeaf(false);
            rama.setText("Ejemplo" + j);

            ArchivoVo hojaVO = null;
            for (int i = 0; i < 10; i++) {
                hojaVO = new ArchivoVo();
                hojaVO.setCls("file");
                hojaVO.setId("id-" + Integer.toString(i));
                hojaVO.setLeaf(true);
                hojaVO.setText("Ejemplo:" + j + "-" + Integer.toString(i));
                listaFileJson.add(hojaVO);
            }// for hojas
            rama.setChildren(listaFileJson.toArray());
            elementos.add(rama);
        }// for rama
        success = true;
        return SUCCESS;
    }

    /**
     * @param registroList the registroList to set
     */
    public void setRegistroList(List<ConjuntoPantallaVO> registroList) {
        this.registroList = registroList;
    }

    /**
     * @return the registroList
     */
    public List<ConjuntoPantallaVO> getRegistroList() {
        return registroList;
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

    /**
     * @return the producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(String producto) {
        this.producto = producto;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the cdConjunto
     */
    public String getCdConjunto() {
        return cdConjunto;
    }

    /**
     * @param cdConjunto the cdConjunto to set
     */
    public void setCdConjunto(String cdConjunto) {
        this.cdConjunto = cdConjunto;
    }

    /**
     * @return the productoList
     */
    public List<BaseObjectVO> getProductoList() {
        return productoList;
    }

    /**
     * @param productoList the productoList to set
     */
    public void setProductoList(List<BaseObjectVO> productoList) {
        this.productoList = productoList;
    }

    /**
     * @return the procesoOriginal
     */
    public String getProcesoOriginal() {
        return procesoOriginal;
    }

    /**
     * @param procesoOriginal the procesoOriginal to set
     */
    public void setProcesoOriginal(String procesoOriginal) {
        this.procesoOriginal = procesoOriginal;
    }

    /**
     * @return the clienteOriginal
     */
    public String getClienteOriginal() {
        return clienteOriginal;
    }

    /**
     * @param clienteOriginal the clienteOriginal to set
     */
    public void setClienteOriginal(String clienteOriginal) {
        this.clienteOriginal = clienteOriginal;
    }

    /**
     * @return the productoOriginal
     */
    public String getProductoOriginal() {
        return productoOriginal;
    }

    /**
     * @param productoOriginal the productoOriginal to set
     */
    public void setProductoOriginal(String productoOriginal) {
        this.productoOriginal = productoOriginal;
    }

    /**
     * @return the nombreConjuntoOriginal
     */
    public String getNombreConjuntoOriginal() {
        return nombreConjuntoOriginal;
    }

    /**
     * @param nombreConjuntoOriginal the nombreConjuntoOriginal to set
     */
    public void setNombreConjuntoOriginal(String nombreConjuntoOriginal) {
        this.nombreConjuntoOriginal = nombreConjuntoOriginal;
    }

    /**
     * @return the procesoCopia
     */
    public String getProcesoCopia() {
        return procesoCopia;
    }

    /**
     * @param procesoCopia the procesoCopia to set
     */
    public void setProcesoCopia(String procesoCopia) {
        this.procesoCopia = procesoCopia;
    }

    /**
     * @return the productoCopia
     */
    public String getProductoCopia() {
        return productoCopia;
    }

    /**
     * @param productoCopia the productoCopia to set
     */
    public void setProductoCopia(String productoCopia) {
        this.productoCopia = productoCopia;
    }

    /**
     * @return the nombreConjuntoCopia
     */
    public String getNombreConjuntoCopia() {
        return nombreConjuntoCopia;
    }

    /**
     * @param nombreConjuntoCopia the nombreConjuntoCopia to set
     */
    public void setNombreConjuntoCopia(String nombreConjuntoCopia) {
        this.nombreConjuntoCopia = nombreConjuntoCopia;
    }

    /**
     * @return the cdProceso
     */
    public String getCdProceso() {
        return cdProceso;
    }

    /**
     * @param cdProceso the cdProceso to set
     */
    public void setCdProceso(String cdProceso) {
        this.cdProceso = cdProceso;
    }

    /**
     * @return the cdProducto
     */
    public String getCdProducto() {
        return cdProducto;
    }

    /**
     * @param cdProducto the cdProducto to set
     */
    public void setCdProducto(String cdProducto) {
        this.cdProducto = cdProducto;
    }

    /**
     * @return the clienteCopia
     */
    public String getClienteCopia() {
        return clienteCopia;
    }

    /**
     * @param clienteCopia the clienteCopia to set
     */
    public void setClienteCopia(String clienteCopia) {
        this.clienteCopia = clienteCopia;
    }

    /**
     * @return the pantallaList
     */
    public List<BaseObjectVO> getPantallaList() {
        return pantallaList;
    }

    /**
     * @param pantallaList the pantallaList to set
     */
    public void setPantallaList(List<BaseObjectVO> pantallaList) {
        this.pantallaList = pantallaList;
    }

    /**
     * @return the descripcionConjuntoOriginal
     */
    public String getDescripcionConjuntoOriginal() {
        return descripcionConjuntoOriginal;
    }

    /**
     * @param descripcionConjuntoOriginal the descripcionConjuntoOriginal to set
     */
    public void setDescripcionConjuntoOriginal(String descripcionConjuntoOriginal) {
        this.descripcionConjuntoOriginal = descripcionConjuntoOriginal;
    }

    /**
     * @return the descripcionConjuntoCopia
     */
    public String getDescripcionConjuntoCopia() {
        return descripcionConjuntoCopia;
    }

    /**
     * @param descripcionConjuntoCopia the descripcionConjuntoCopia to set
     */
    public void setDescripcionConjuntoCopia(String descripcionConjuntoCopia) {
        this.descripcionConjuntoCopia = descripcionConjuntoCopia;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the hproceso
     */
    public String getHproceso() {
        return hproceso;
    }

    /**
     * @param hproceso the hproceso to set
     */
    public void setHproceso(String hproceso) {
        this.hproceso = hproceso;
    }

    /**
     * @return the hcliente
     */
    public String getHcliente() {
        return hcliente;
    }

    /**
     * @param hcliente the hcliente to set
     */
    public void setHcliente(String hcliente) {
        this.hcliente = hcliente;
    }

    /**
     * @return the hproducto
     */
    public String getHproducto() {
        return hproducto;
    }

    /**
     * @param hproducto the hproducto to set
     */
    public void setHproducto(String hproducto) {
        this.hproducto = hproducto;
    }

    /**
     * @return the hclienteCopia
     */
    public String getHclienteCopia() {
        return hclienteCopia;
    }

    /**
     * @param hclienteCopia the hclienteCopia to set
     */
    public void setHclienteCopia(String hclienteCopia) {
        this.hclienteCopia = hclienteCopia;
    }

    /**
     * @return the pantallaCopiaList
     */
    public List<BaseObjectVO> getPantallaCopiaList() {
        return pantallaCopiaList;
    }

    /**
     * @param pantallaCopiaList the pantallaCopiaList to set
     */
    public void setPantallaCopiaList(List<BaseObjectVO> pantallaCopiaList) {
        this.pantallaCopiaList = pantallaCopiaList;
    }

    /**
     * @return the originalPantallas
     */
    public String[] getOriginalPantallas() {
        return originalPantallas;
    }

    /**
     * @param originalPantallas the originalPantallas to set
     */
    public void setOriginalPantallas(String[] originalPantallas) {
        this.originalPantallas = originalPantallas;
    }

    /**
     * @return the copiaPantallas
     */
    public String[] getCopiaPantallas() {
        return copiaPantallas;
    }

    /**
     * @param copiaPantallas the copiaPantallas to set
     */
    public void setCopiaPantallas(String[] copiaPantallas) {
        this.copiaPantallas = copiaPantallas;
    }

    /**
     * @return the elementos
     */
    public List<CarpetaVo> getElementos() {
        return elementos;
    }

    /**
     * @param elementos the elementos to set
     */
    public void setElementos(List<CarpetaVo> elementos) {
        this.elementos = elementos;
    }

    /**
     * 
     * @return
     */
	public List<BaseObjectVO> getSituacionesList() {
		return situacionesList;
	}

	/**
	 * 
	 * @param situacionesList
	 */
	public void setSituacionesList(List<BaseObjectVO> situacionesList) {
		this.situacionesList = situacionesList;
	}

	/**
	 * 
	 * @return
	 */
	public String getParameterValueItem() {
		return parameterValueItem;
	}

	/**
	 * 
	 * @param parameterValueItem
	 */
	public void setParameterValueItem(String parameterValueItem) {
		this.parameterValueItem = parameterValueItem;
	}

	/**
	 * 
	 * @return
	 */
	public String getHsituacion() {
		return hsituacion;
	}

	/**
	 * 
	 * @param hsituacion
	 */
	public void setHsituacion(String hsituacion) {
		this.hsituacion = hsituacion;
	}

	/**
	 * @return the claveSituacion
	 */
	public String getClaveSituacion() {
		return claveSituacion;
	}

	/**
	 * @param claveSituacion the claveSituacion to set
	 */
	public void setClaveSituacion(String claveSituacion) {
		this.claveSituacion = claveSituacion;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}
