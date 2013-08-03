package mx.com.aon.catbo.web;

import com.opensymphony.xwork2.ActionSupport;
import mx.com.aon.catbo.model.ElementoComboBoxVO;
import mx.com.aon.catbo.model.AseguradoraVO;
import mx.com.aon.catbo.model.ResponsablesVO;
import mx.com.aon.catbo.model.EncuestaVO;
import mx.com.aon.catbo.service.CombosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.CombosManager2;
import mx.com.aon.core.ApplicationException;
import org.apache.log4j.Logger;
import mx.com.aon.catbo.model.ItemVO;


import mx.com.aon.catbo.model.ElementoComboBoxVO;
import mx.com.aon.portal.model.CatalogoLogicoVO;


import java.util.ArrayList;
import java.util.List;

/**
 * Action con requerimientos de combos para todo el proyecto.
 * 
 *
 */
@SuppressWarnings("serial")
public class CombosAction extends ActionSupport {

    private static Logger logger = Logger.getLogger(CombosAction.class);

    private  transient CombosManager combosManager;
    private  transient CombosManager2 combosManager2;


    public void setCombosManager2(CombosManager2 combosManager2) {
        this.combosManager2 = combosManager2;
    }

    private String cdTabla;

    //private ArrayList<ElementoComboBoxVO> elementosComboBox;
    private List elementosComboBox;
    private List tiposSituacionComboBox;
    private List tiposCoberturaComboBox;
    private List aseguradoraComboBox;
    private List seccionesComboBox;
    private List estadosComboBox;
    private List clientesCorp;
    private List clientesComboBox;
    private List tiposNivelComboBox;
    private List padresComboBox;
    private List ramosComboBox;
    private List subRamosComboBox;
    private List productosComboBox;
    private List idiomasComboBox;
    private List usuariosComboBox;
    private List procesosComboBox;
    private List temporalidadesComboBox;
    private List regionesComboBox;
    private List estadosDescuentosComboBox;
    private List productosDescuentosComboBox;
    private List tiposDsctoComboBox;
    private List planesComboBox;
    private List comboTiposArchivos;
    
    private List confAlertasAutoUsuarios;
    private List confAlertasAutoClientes; //Falta el SP
    private List confAlertasAutoProcesos;
    private List confAlertasAutoTemporalidad;
    private List confAlertasAutoRol;
    private List confAlertasAutoTipoRamo;
    private List confAlertasAutoRegion;
    private List siNo;
    private List frecuencias;
    private List conceptosProducto;
    private List productosAseguradoraRamoSubRamo;
    private List lineasOperacion;
    private List estadosEjecutivo;
    private List lineasAtencion;
    private List ejecutivosAon;
    private List grupoPersonasCliente;
    private List polizasPorAseguradora;
    private List reciboPorPolizaPorAseguradora;
    private List clientesCorporativo;
    private List productosEjecutivosCuenta;
    private List productosAseguradoraCliente;
    private List tiposRamo;
    private List tiposRamoClienteEjecCuenta;
    private List estadosAgrupacion;
    private List tiposAgrupacion;
    private List agrupacionPapel;
    private List personasJ;
    private List bloques;

    private List formaCalculoFolioNroIncisos;
    private List indicadorNumeracionNroIncisos;
    private List indicador_SP_NroIncisos;

    private List comboNacionalidad;
    private List comboEstadoCivil;
    private List comboTipoEmpresa;
    private List comboTipoIdentificador;
    private List comboTiposDomicilio;
    private List comboPaises;
    private List comboColonias;
    private List comboEstados;
    private List comboMunicipios;
    private List comboTipoTelefono;
    
    private List comboListaGrupoCorporativo;
    private List comboListaEstadoCorporativo;
    private List comboNivelCorporativo;
    private List comboSexo;
    
    private List comboRazones;
    private List comboMetodos;
    private List comboPeriodosGracia;
    private List comboTiposDocumento;  
    
    private List comboSeccionesOrden;
    private List comboTiposObjetos;

    private List comboSeccionFormato;
    private List comboBloquesConfigAtributosFormatoOrdenTrabajo;
    private List comboFormatosCampo;
    private List comboCampoBloques;
    private List comboTiposError;
    private List comboProcesos;
    private List comboFormatos;
    private List comboFormasCalculoFolio;
    private List comboProductosTramoCliente;
    private List comboTipoSituacionProducto;
    private List comboInstrumentosPago;
    private List comboBancos;
    private List comboTiposTarjetas;
    private List comboIndicadoresSINO;
    private List comboPlanesProducto;
    private List comboPersonas;
    private List comboGenerico;
    private List comboFormaCalculo;
    private List comboFormaAtributo;
    private List comboNrosPolizas;
    private List comboTiposRenova;
    private List comboAccionesRenovacionRol;
    private List comboAccionesRenovacionPantalla;
    private List comboAccionesRenovacionCampo;
    private List comboAccionesRenovacionAccion;
    private List comboAccionesRenovacionRoles;
    private List comboTipoObjeto;
    private List comboFormaPago;
    private List comboInstrumentoPago;
    private List comboRoles;
    private List comboNombrePersonas;
    private List comboIncisos;
    private List comboCoberturasProducto;
    private List comboObjetosRamo;

    private List comboTipoGuion;
    
    private List comboNotificacionFormato;
    
    private List comboNotificacionRegion;
    
    private List comboTipoMetodoEnvio;
    
    private List comboProcesosCat;
    
    private List comboClientesCorpBO;
   
    private List comboDatosCatalogo;
    
    private List comboDatosModulo;
    
    private List comboUnidadTiempoAComprar;
    private List comboPrioridad;
    private List comboEstatus;
    
    private List comboNivelAtencion;
    
    private List comboIndicadorAviso;
    
    
    private List comboObtenerTareas;
    private List comboPrioridades;
    private List comboTareas;
    private List comboResponsables;
    private List comboObtenerModulos;
    private List comboTablas;                  
    private List ComboTablasArchivos;
   
    private List comboDatosCatalogoDep;
    

    private List comboTareaPrioridad;
    private List comboTareaEstatus;
    
    private List comboMonedas;
    private List comboNivelAtencionMatriz;  
    
    private List comboUsuariosReemplazantes;
    
    private List comboRolesUsuarios;
    
    private List comboUsuarios;
    
    private List comboModulosEncuestas;
    
    private List comboEncuestas;
    
    private List cboUsuariosEncuestas;
    
    private List cboCampanEncuestas;
    
    private List cboProductosAsegEncuestas;
    
    private List cboCliCorpoAseguradoraProducto;

    private List comboDatosPais;
    
    private List comboDatosMes;
    
    private List comboGetListas;

    private List comboFormatosArchivos;
    
    private List comboAlgoritmos;
    
    private List comboTipoFax;
    
    private List comboUsuarioResponsable;
    
    private List comboCargaArchivos;
    
    private List comboTabla;
    
    private List comboModuloEnc;
    
    private List comboCampanhaEnc;
    
    private List comboTemaEnc;
    private List comboEncuestaEnc;
    
    private String cdModulo;
    private String cdusuari;
//
    
    private String cdMatriz;
    private String pv_cdtabla_i;
//usados para el combo box de Productos- pantalla :  Ayuda Coberturas
    private String cdunieco;
    private String cdtipram;
    private String cdsubram;
    private String nmConfig;
//usados para el combo box de : Agrupacion por grupo de personas    
    private String cdElemento;

//usados para el combo box de: Recibo Por Poliza Por Aseguradora
    private String cdRamo;
    private String nmPoliza;

//usado por el combo box de : Vínculos Padre Por Estructura
    private String codigoEstructura;

  //usado por el combo box de : Ejecutivos por Cuenta
    private String cdPerson;
    private String cdTipRam;
  
    //datos de entrada usados para el comboBox de obtener Planes
    //para el caso de uso Configurar Descuentos. Se muestra en la grilla
    //private String cdElemento;
    private String cdUniEco;
    //private String cdRamo;
    private String cdTipSit;
    
    //usado por comboBox de Situación Por Producto y Plan
    private String cdPlan;
    private List comboTiposGarantia;
    
    //obtener indicador de numeracion
    private String swTariFi;

    private String codigoPais;
    private String codigoEstado;
    private String codigoMunicipio;
    
    private String cdBloque;
    private String otTipo;
    private String cdUsuario;
    private String cdFormato;
    private String cdNivatn;
    private String idTablaLogica;
    
    //usado en el combobox obtiene Acciones Renovacion Campo
    private String cdTitulo;
    
    //usados en el combobox obtiene
    private String cdRenova;
    private String cdRol;
    
    private String otClave1;
    
    private String estado;
    private String nmSituac;
    
    private String dsNomFormato;
    private String cdNotificacion;
    
    //usados en el combobox  obtiene nivelAtn y tunidad
    private List cboUnidad;
    private List cboNivel;
    
    private boolean success;
    
    
    private String valor1;
    private String valor2;
    
    private String cdsisrol;
   
    private String pv_cdunieco_i;
    private String valAnter;
    private String valAntAnt;
    
    private String cdproceso;
    
    
    private String cdmatriz;
    private String cdmodulo;
    private String cdnivatn;
    private String cdelemento;
    private String cdramo;
    
    private String cdCampan;
    private String cdProceso;
    private String cdEncuesta;
    
    public String getCdelemento() {
		return cdelemento;
	}


	public void setCdelemento(String cdelemento) {
		this.cdelemento = cdelemento;
	}


	public String getCdramo() {
		return cdramo;
	}


	public void setCdramo(String cdramo) {
		this.cdramo = cdramo;
	}


	public String getCdproceso() {
		return cdproceso;
	}


	public void setCdproceso(String cdproceso) {
		this.cdproceso = cdproceso;
	}


	public String getCdsisrol() {
		return cdsisrol;
	}


	public void setCdsisrol(String cdsisrol) {
		this.cdsisrol = cdsisrol;
	}


	public String getCdRenova() {
		return cdRenova;
	}


	public void setCdRenova(String cdRenova) {
		this.cdRenova = cdRenova;
	}


	public String getCdRol() {
		return cdRol;
	}


	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}


	public void setCombosManager(CombosManager combosManager) {
        this.combosManager = combosManager;
    }





    public List getElementosComboBox() {
		return elementosComboBox;
	}


	public void setElementosComboBox(List elementosComboBox) {
		this.elementosComboBox = elementosComboBox;
	}


	public List getTiposSituacionComboBox() {
        return tiposSituacionComboBox;
    }

    public void setTiposSituacionComboBox(List tiposSituacionComboBox) {
        this.tiposSituacionComboBox = tiposSituacionComboBox;
    }

    public List getTiposCoberturaComboBox() {
        return tiposCoberturaComboBox;
    }

    public void setTiposCoberturaComboBox(List tiposCoberturaComboBox) {
        this.tiposCoberturaComboBox = tiposCoberturaComboBox;
    }

    public List getAseguradoraComboBox() {
        return aseguradoraComboBox;
    }

    public void setAseguradoraComboBox(List aseguradoraComboBox) {
        this.aseguradoraComboBox = aseguradoraComboBox;
    }

    public void setAseguradoraComboBox(ArrayList<AseguradoraVO> aseguradoraComboBox) {
        this.aseguradoraComboBox = aseguradoraComboBox;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public List getSeccionesComboBox() {
        return seccionesComboBox;
    }

    public void setSeccionesComboBox(List seccionesComboBox) {
        this.seccionesComboBox = seccionesComboBox;
    }

    public List getEstadosComboBox() {
        return estadosComboBox;
    }

    public void setEstadosComboBox(List estadosComboBox) {
        this.estadosComboBox = estadosComboBox;
    }

    public List getClientesCorp() {
        return clientesCorp;
    }

    public void setClientesCorp(List clientesCorp) {
        this.clientesCorp = clientesCorp;
    }

    public String obtenerProductos() throws Exception {
        try {
            logger.info("obteniendo la lista de productos");
            elementosComboBox = combosManager.obtenerProductos();
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

    public String obtenerTiposSituacion() throws Exception {
        try {
            logger.info("obteniendo la lista de tipos Situacion");
            PagedList lista = combosManager.comboTipoSituacion();
            this.tiposSituacionComboBox = lista.getItemsRangeList();
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

    public String obtenerTiposCobertura() throws Exception {
        try {
            logger.info("obteniendo la lista de tipos de Cobertura");
            PagedList lista = combosManager.comboCoberturas();
            this.tiposCoberturaComboBox = lista.getItemsRangeList();
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

    public String obtenerAseguradoras() throws Exception {
        try {
            logger.info("obteniendo la lista de Aseguradoras");
            PagedList lista = combosManager.comboAseguradoras();
            this.aseguradoraComboBox = lista.getItemsRangeList();
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
    
    public String obtenerEstados() throws Exception {
        try {
            logger.info("obteniendo la lista de Estados");
            PagedList lista = combosManager.comboEstados();
            this.estadosComboBox = lista.getItemsRangeList();
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
    
    public String obtenerSecciones() throws Exception {
        try {
            PagedList lista = combosManager.comboSecciones();
            this.seccionesComboBox = lista.getItemsRangeList();
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

    public String obtenerClientesCorp() throws Exception {
        try {
            logger.info("obteniendo la lista de Clientes Corp");
            PagedList lista = combosManager.obtenerClientesCorp();
            this.clientesCorp = lista.getItemsRangeList();
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
    
    public String obtenerClientes() throws Exception {
        try {
            logger.info("obteniendo la lista de Clientes");
            this.clientesComboBox = combosManager.comboClientes();
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


    public String obtenerPadres() throws Exception {
        try {
            logger.info("obteniendo la lista de Padres");
            this.padresComboBox = combosManager.comboPadres();
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

    public String obtenerTiposNivel() throws Exception {
        try {
            logger.info("obteniendo la lista de Tipos de Nivel");
            this.tiposNivelComboBox = combosManager.comboTiposNivel();
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

    
    public String obtenerRamos() throws Exception {
        try {
            logger.info("obteniendo la lista de Ramos");
            this.ramosComboBox = combosManager.comboRamos();
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
    
    public String obtenerRamosNuevo() throws Exception {
        try {
            logger.info("obteniendo la lista de Ramos");
            this.ramosComboBox =combosManager2.comboRamos2(cdunieco, cdelemento, cdramo);// combosManager.comboRamos2(cdunieco, cdelemento, cdramo);//
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
    
    
    public String obtenerSubRamos() throws Exception {
        try {
            logger.info("obteniendo la lista de SubRamos");
            this.subRamosComboBox = combosManager.comboSubRamos();
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
    
    public String obtenerProductosAyuda() throws Exception {
        try {
            logger.info("obteniendo la lista de Productos");
            this.productosComboBox = combosManager.comboProductos();
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
    
    public String obtenerIdiomasAyuda() throws Exception {
        try {
            logger.info("obteniendo el listado de Idiomas");
            this.idiomasComboBox = combosManager.comboIdiomas();
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
    
    public String obtenerUsuarios() throws Exception {
        try {
            logger.info("obteniendo el listado de Usuarios");
            this.usuariosComboBox = combosManager.comboUsuarios();
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
    
    public String obtenerProcesos() throws Exception {
        try {
            logger.info("obteniendo el listado de Procesos");
            this.procesosComboBox = combosManager.comboProcesos();
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
    
    public String obtenerTemporalidades() throws Exception {
        try {
            logger.info("obteniendo el listado de Temporalidad");
            this.temporalidadesComboBox = combosManager.comboTemporalidades();
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
    
    public String obtenerRegiones() throws Exception {
        try {
            logger.info("obteniendo el listado de Regiones");
            this.regionesComboBox = combosManager.comboRegiones();
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
    
    
    
    public String obtenerEstadosDescuentos() throws Exception {
        try {
            logger.info("obteniendo la lista de Estados en Descuentos");
            this.estadosDescuentosComboBox = combosManager.comboEstadosDescuentos();
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
    
    public String obtenerProductosDescuentos() throws Exception {
        try {
            logger.info("obteniendo la lista de Descuento por Productos ");
            this.productosDescuentosComboBox = combosManager.comboProductosDescuentos();
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

    public String obtenerTiposDscto() throws Exception {
        try {
            logger.info("obteniendo el listado de Tipos de descuentos");
            this.tiposDsctoComboBox = combosManager.comboTiposDscto();
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
    

    public String obtenerPlanes() throws Exception {
        try {
            logger.info("obteniendo el listado de Planes");
            this.planesComboBox = combosManager.comboPlanes(cdElemento,cdUniEco, cdRamo, cdTipSit);
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


    public String obtenerConfAlertasAutoUsuarios () throws Exception {
    	try {
    		this.confAlertasAutoUsuarios = combosManager.comboUsuarios();
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
    
    public String obtenerConfAlertasAutoClientes () throws Exception {
    	try {
    		confAlertasAutoClientes = combosManager.comboConfAlertasAutoClientes();
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
  
    public String obtenerConfAlertasAutoProcesos () throws Exception {
    	try {
    		confAlertasAutoProcesos = combosManager.comboConfAlertasAutoProcesos();
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
   
    public String obtenerConfAlertasAutoTemporalidad () throws Exception {
    	try {
    		confAlertasAutoTemporalidad = combosManager.comboConfAlertasAutoTemporalidad();
    		success = true;
    		return SUCCESS;
    	} catch (ApplicationException ae) {
    		success = false;
    		addActionError(ae.getMessage());
    		return SUCCESS;
    	} catch (Exception e){
    		success = false;
    		addActionError(e.getMessage());
    		return SUCCESS;
    	}
    }
    
    public String obtenerConfAlertasAutoRol () throws Exception {
    	try {
    		confAlertasAutoRol = combosManager.comboConfAlertasAutoRol();
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
    
    public String obtenerConfAlertasAutoTipoRamo () throws Exception {
    	try {
    		confAlertasAutoTipoRamo = combosManager.comboConfAlertasAutoTipoRamo();
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
    
    public String obtenerConfAlertasAutoRegion () throws Exception {
    	try {
			confAlertasAutoRegion = combosManager.comboRegiones();
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
    }

    public String obtenerSiNo () throws Exception {
    	try {
			siNo = combosManager.comboSiNo();
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
    }

    public String obtenerFrecuencias () throws Exception {
    	try {
			frecuencias = combosManager.comboFrecuencias();
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
    }

    public String obtenerConceptosProducto () throws Exception {
    	try {
			conceptosProducto = combosManager.comboConceptosProducto();
			success = true;
			return SUCCESS;
		} catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
    }

    public String obtenerProductosAyudaCoberturas() throws Exception {
        try {
            logger.info("obteniendo la lista de productos");
            productosAseguradoraRamoSubRamo = combosManager.comboProductosAyuda(cdunieco,cdtipram,cdsubram);
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


    public String obtenerTiposCoberturaProducto() throws Exception {
        try {
            logger.info("obteniendo la lista de tipos de Cobertura por producto");
            tiposCoberturaComboBox = combosManager.comboCoberturasProducto(cdtipram);
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

    public String obtenerLineasOperacion() throws Exception {
        try {
            logger.info("obteniendo la lista de lineas de Operacion");
            lineasOperacion = combosManager.comboLineasCaptura();
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
    
    
    public String obtenerEjecutivosAon() throws Exception {
        try {
            logger.info("obteniendo la lista de Ejecutivos de Aon");
            ejecutivosAon = combosManager.comboEjecutivosAon();
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
    
    public String obtenerEstadoEjecutivo() throws Exception {
        try {
            logger.info("obteniendo la lista de Estados de Ejecutivo");
            estadosEjecutivo = combosManager.comboEstadosEjecutivo();
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
    
    
    
    public String obtenerLineasAtencion() throws Exception {
        try {
            logger.info("obteniendo la lista de Lineas de Atención");
            lineasAtencion = combosManager.comboLineasAtencion();
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
    
    
    public String obtenerGrupoPersonas() throws Exception {
        try {
            logger.info("obteniendo la lista de Grupos de Personas");
            grupoPersonasCliente = combosManager.comboGrupoPersonasCliente(cdElemento);
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
    

    public String obtenerPolizasPorAseguradora () throws Exception {
    	try {
    		polizasPorAseguradora = combosManager.comboPolizasPorAseguradora(cdunieco);
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
    
    public String obtenerPolizasPorAseguradora2 () throws Exception {
    	try {
    		polizasPorAseguradora = combosManager2.ComboObtienePolizaAseguradora2(cdunieco,cdElemento);
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
    
    
    
    public String obtenerReciboPorPolizaPorAseguradora () throws Exception {
    	try {
    		reciboPorPolizaPorAseguradora = combosManager.comboReciboPorPolizaPorAseguradora(cdunieco, cdRamo, nmPoliza);
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
    
    public String obtenerVinculosPadrePorCodigoEstructura() throws Exception {
        try {
            logger.info("obteniendo la lista de Padres");
            this.padresComboBox = combosManager.comboVinculosPadrePorEstructura(codigoEstructura);
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
    
    
    public String obtenerClientesCorporativo () throws Exception {
    	try {
    		clientesCorporativo = combosManager.comboClientesCorporativo(cdPerson);
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
    
    
    
    public String obtenerProductosEjecutivosCuenta() throws Exception {
        try {
            logger.info("obteniendo la lista de productos");
            productosEjecutivosCuenta = combosManager.comboProductosEjecutivosCuenta(cdPerson, cdTipRam);
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

    public String obtenerAseguradorasCliente() throws Exception {
        try {
            logger.info("obteniendo la lista de aseguradoras para el cliente "+ cdElemento);
            aseguradoraComboBox = combosManager.comboAseguradorasCliente(cdElemento);
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

    public String obtenerProductosAseguradoraCliente() throws Exception {
        try {
            logger.info("obteniendo la lista de productos para la aseguradora " + cdunieco + "y  el cliente "+ cdElemento);
            productosAseguradoraCliente = combosManager.comboProductosAseguradoraCliente(cdElemento,cdunieco);
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

    public String obtenerRamosCliente() throws Exception {
        try {
            logger.info("obteniendo la lista de ramos para  el cliente "+ cdElemento);
            tiposRamo = combosManager.comboRamosCliente(cdElemento);
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
    
    
    public String obtenerTiposRamoClienteEjecutivoCuenta () throws Exception {
    	try {
    		tiposRamoClienteEjecCuenta = combosManager.comboTiposRamoClienteEjecutivoCuenta(cdElemento);
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

    public String obtenerEstadosAgrupacionPoliza () throws Exception {
    	try {
    		estadosAgrupacion = combosManager.comboEstadosAgrupacionPoliza();
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

    public String obtenerTiposAgrupacionPoliza () throws Exception {
    	try {
    		tiposAgrupacion = combosManager.comboTiposAgrupacion();
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

    public String comboAgrupacionPapeles_Papel () throws Exception {
    	try {
    		agrupacionPapel = combosManager.comboPapelesPorCliente(cdElemento);
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
    
    public String comboAseguradorasPorCliente () throws Exception {
    	try {
    		aseguradoraComboBox = combosManager.comboAseguradoraPorElemento(cdElemento);
    		success = false;
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
    public String comboProductosPorAseguradorYCliente () throws Exception {
    	try {
    		productosComboBox = combosManager.comboProductosPorAseguradoraYCliente(cdunieco, cdElemento, cdRamo);
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
    public String comboTipoPersonaJ () throws Exception {
    	try {
    		personasJ = combosManager.comboTipoPersonaJ();
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

    public String comboBloques() throws Exception {
    	try {
    		bloques = combosManager.comboBloques();
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

    
  
    
    
    public String comboFormaCalculoFolioNroIncisos() throws Exception {
    	try {
    		formaCalculoFolioNroIncisos = combosManager.comboFormaCalculoFolioNroIncisos();
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

    public String comboIndicadorNumeracionNroIncisos() throws Exception {
    	try {
    		indicadorNumeracionNroIncisos = combosManager.comboIndicadorNumeracionNroIncisos();
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
    
    
    public String comboIndicador_SP_NroIncisos() throws Exception {
    	try {
    		indicador_SP_NroIncisos = combosManager.comboIndicador_SP_NroIncisos();
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
    
    public String comboNacionalidad () throws Exception {
    	try {
    		comboNacionalidad = combosManager.comboNacionalidad();
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
    
    public String combosEstadoCivil () throws Exception {
    	try {
    		comboEstadoCivil = combosManager.comboEstadoCivil();
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
    
    public String comboTipoEmpresa () throws Exception {
    	try {
    		comboTipoEmpresa = combosManager.comboTipoEmpresa();
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
    public String comboTipoIdentificador () throws Exception {
    	try {
    		comboTipoIdentificador = combosManager.comboTipoIdentificador();
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
    public String comboTipoDomicilio () throws Exception {
    	try {
    		comboTiposDomicilio = combosManager.comboTipoDomicilio();
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
    public String comboPais () throws Exception {
    	try {
    		comboPaises = combosManager.comboPaises();
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
    public String comboColonia () throws Exception {
    	try {
    		comboColonias = combosManager.comboColonias(codigoPais, codigoEstado, codigoMunicipio);
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
    public String comboEstadosPais () throws Exception {
    	try {
    		comboEstados = combosManager.comboEstados(codigoPais);
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
    public String comboMunicipio () throws Exception {
    	try {
    		comboMunicipios = combosManager.comboMunicipios(codigoPais, codigoEstado);
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
    public String comboTipoTelefono () throws Exception {
    	try {
    		comboTipoTelefono = combosManager.comboTiposTelefono();
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
    public String comboGruposCorporativos () throws Exception {
    	try {
    		comboListaGrupoCorporativo = combosManager.comboGrupoCorporativo(cdElemento);
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
    public String comboEstadosCorporativos () throws Exception {
    	try {
    		comboListaEstadoCorporativo = combosManager.comboEstadosCorporativos();
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
    
    public String comboNivelesCorporativos () {
    	try {
    		comboNivelCorporativo = combosManager.comboGrupoNivelCorporativo();
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
    
    public String comboSexos() throws ApplicationException {
    	try {
    		comboSexo = combosManager.comboSexo();
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
	 * @return the clientesComboBox
	 */
	public List getClientesComboBox() {
		return clientesComboBox;
	}

	
	public String comboRazones() throws ApplicationException {
    	try {
    		comboRazones = combosManager.comboRazonCancelacion();
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
    
    public String comboMetodos() throws ApplicationException {
    	try {
    		comboMetodos = combosManager.comboMetodoCancelacion();
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

    
    
    public String obtenerPeriodosGracia() throws Exception {
        try {
            logger.info("obteniendo la lista de periodos de gracia");
            comboPeriodosGracia = combosManager.comboPeriodosGracia();
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
    
    
    public String obtenerTiposDocumento() throws Exception {
        try {
            logger.info("obteniendo la lista de tipos de Documento");
            comboTiposDocumento = combosManager.comboTiposDocumento(cdElemento, cdUniEco, cdRamo);
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
    
    public String obtenerComboSeccionesOrden() throws Exception {
        try {
            comboSeccionesOrden = combosManager.comboSeccionesOrden();
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
    
    public String obtenerComboTiposObjetos() throws Exception {
        try {
            comboTiposObjetos = combosManager.comboTiposObjetos();
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

    public String obtenerSeccionesFormato() throws Exception {
        try {
            logger.info("obteniendo la lista de tipos de seccion formato");
            comboSeccionFormato = combosManager.comboSeccionFormato(cdFormato);
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


  public String obtenerBloquesConfAtributosFormatoOrdenTrabajo() throws Exception {
        try {
            logger.info("obteniendo la lista de bloques");
            comboBloquesConfigAtributosFormatoOrdenTrabajo = combosManager.comboBloquesConfiguraAtributosFormatoOrdenTrabajo();
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

	public String comboObtenerFormatos () throws Exception {
		try {
			comboFormatos = combosManager.comboFormato();
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
	
	public String comboObtenerProcesos () throws Exception {
		try {
			comboProcesos = combosManager.comboProceso();
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
	
	public String comboObtenerFormasCalculoFolio () throws Exception {
		try {
			comboFormasCalculoFolio = combosManager.comboFormaCalculoFolio();
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
	




  	public String obtenerComboFormatosCampo() throws Exception {
        try {
            logger.info("obteniendo la lista de formatos campos");
            comboFormatosCampo = combosManager.comboFormatosCampo();
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
    
	public String comboTipoError () throws Exception {
		try {
			comboTiposError = combosManager.comboTipoError();
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

	public String obtenerComboCampoBloques() throws Exception {
    try {
        logger.info("obteniendo la lista de  campos bloques");
        comboCampoBloques = combosManager.comboCampoBloques(otTipo,cdBloque);
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

	public String comboClientesPorUsuario() throws Exception {
		try {
			clientesCorp = combosManager.comboClientesCorpPorUsuario(cdUsuario);
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

	 
		public String obtenerComboProductosTramoCliente() throws Exception {
			try {
				comboProductosTramoCliente = combosManager.comboProductosTipoRamoCliente(cdElemento, cdtipram);
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
		
		public String obtenerComboTipoSituacionProductos() throws Exception {
			try {
				comboTipoSituacionProducto = combosManager.comboTipoSituacionProducto(cdRamo);
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
	
		
		public String comboInstrumentosPago() throws Exception {
			try {
				comboInstrumentosPago = combosManager.comboInstrumentosPago();
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
		
		public String comboBancos() throws Exception {
			try {
				comboBancos = combosManager.comboBancos();
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
		
		public String comboTiposTarjetas() throws Exception {
			try {
				comboTiposTarjetas = combosManager.comboTiposTarjeta();
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
		
	    public String comboAseguradoraPorClienteYProducto() throws Exception {
	        try {
	            aseguradoraComboBox = combosManager.comboAseguradoraPorClienteYProducto(cdElemento, cdRamo);
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
	    
		public String obtenerIndicadorSINO() throws Exception {
	        try {
	        	comboIndicadoresSINO = combosManager.comboIndicadorSINO();
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
	public String obtenerSituacionPorProductoYPlan() throws Exception{
		try {
			comboTipoSituacionProducto = combosManager.comboTipoSituacionPorProductoYPlan(cdRamo, cdPlan);
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
	public String obtenerGarantiaPorProductoYSituacion () throws Exception{
		try {
			comboTiposGarantia = combosManager.comboTipoGarantiaPorProductoYSituacion(cdRamo, cdTipSit);
			success = true;
			return SUCCESS;
		}catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
		
	public String obtenerPlanesProducto() throws Exception{
		try {
			comboPlanesProducto = combosManager.comboPlanesProducto(cdRamo);
			success = true;
			return SUCCESS;
		}catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
	public String obtenerComboPersonas () throws Exception{
		try {
			comboPersonas = combosManager.comboPersonas();
			success = true;
			return SUCCESS;
		}catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	public String obtenerComboGenerico () throws Exception {
		try {
			comboGenerico = combosManager.comboGenerico(idTablaLogica);
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
	
	public String obtenerComboFormaCalculo() throws Exception {
        try {
        	comboFormaCalculo = combosManager.comboFormaCalculo();
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
	
	public String obtenerComboFormaAtributo() throws Exception {
        try {
        	comboFormaAtributo = combosManager.comboFormaAtributo();
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
	
	public String obtenerComboNrosPolizas () throws Exception{
		try {
			comboNrosPolizas = combosManager.comboNroPolizas(cdUniEco, cdRamo, cdPlan, cdElemento);
			success = true;
			return SUCCESS;
		}catch (ApplicationException ae) {
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		} catch (Exception e) {
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}
	}
	
	public String obtenerComboTiposRenova() throws Exception {
        try {
        	comboTiposRenova = combosManager.comboTiposRenovacion();
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

	public String obtenerComboAccionesRenovacionRol() throws Exception {
        try {
        	comboAccionesRenovacionRol = combosManager.comboAccionRenovacionRol();
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
    
	public String obtenerComboAccionesRenovacionPantalla() throws Exception {
        try {
        	comboAccionesRenovacionPantalla = combosManager.comboAccionRenovacionPantalla();
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
    
	public String obtenerComboAccionesRenovacionCampo() throws Exception {
        try {
        	comboAccionesRenovacionCampo = combosManager.comboAccionRenovacionCampo(cdTitulo);
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
    
	public String obtenerComboAccionesRenovacionAccion() throws Exception {
        try {
        	comboAccionesRenovacionAccion = combosManager.comboAccionRenovacionAccion();
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
	
	public String obtenerComboAccionesRenovacionRoles() throws Exception {
        try {
        	comboAccionesRenovacionRoles = combosManager.comboAccionRenovacionRoles(cdRenova);
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
	 * Metodo que obtiene un conjunto de productos que dependen de un cliente
	 * determinado.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String obtenerRamosPorCliente() throws Exception {
        try {
            logger.info("obteniendo la lista de ramos por cliente");
            elementosComboBox = (ArrayList<ElementoComboBoxVO>) combosManager.obtenerRamosPorCliente(cdElemento);
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
	
	
	public String obtenerComboTipoObjeto() throws Exception {
        try {
        	comboTipoObjeto = combosManager.comboTipoObjeto();
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
	
	public String obtenerComboFormaPago() throws Exception {
        try {
        	comboFormaPago = combosManager.obtenerFormasPago(otClave1);
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
	
	public String obtenerComboInstrumentoPago() throws Exception {
        try {
        	comboInstrumentoPago = combosManager.obtenerInstrumentosPago();
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
	
	public String obtenerComboRoles() throws Exception {
        try {
        	comboRoles = combosManager.matrizObtieneRol();
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
	
	public String obtenerComboNombrePersonas() throws Exception {
        try {
        	comboNombrePersonas = combosManager.obtenerPersonas(null);
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
	
	public String obtenerComboIncisosCoberturaPoliza() throws Exception {
        try {
        	comboIncisos = combosManager.obtenerIncisos(cdUniEco,cdRamo,estado,nmPoliza,nmSituac);
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
	
	public String obtenerComboCoberturasProducto() throws Exception {
        try {
        	comboCoberturasProducto = combosManager.obtenerCoberturasProducto(cdUniEco,cdRamo,nmPoliza);
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
	
	public String obtenerComboObjetosRamo() throws Exception {
        try {
        	comboObjetosRamo = combosManager.obtenerObjetosRamos(cdRamo,cdTipSit);
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
	
	
	
	public String obtenerComboTipoGuion() throws Exception {
        try {
        	comboTipoGuion = combosManager.comboTipoGuion();
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
	
	public String obtenerComboNotificacionRegion() throws Exception {
        try {
        	comboNotificacionRegion = combosManager.comboNotificacionRegion(cdNotificacion);
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

	public String obtenerComboTipoMetodoEnvio() throws Exception {
        try {
        	comboTipoMetodoEnvio = combosManager.comboTipoMetodoEnvio();
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
	

	public String obtenerComboProcesosCat() throws Exception {
        try {
        	comboProcesosCat = combosManager.comboProcesosCat();
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
	
	public String obtenerComboProcesosCatMatriz() throws Exception {
        try {
        	comboProcesosCat = combosManager.comboProcesosCatMatriz();
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
	
	
	public String obtenerComboClientesCorpBO() throws Exception {
        try {
        	comboClientesCorpBO = combosManager.comboClientesCorpBO();
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
	
	
//***	Agregado recien para probar 
		public String obtenerDatosCatalogo() throws Exception {
	        try {	        		        	
	        	comboDatosCatalogo = combosManager.obtenerDatosCatalogo(cdTabla);
	        	success = true;
	            return SUCCESS;

	        }/*catch(ApplicationException e)
			{
	            success = false;
	            addActionError(e.getMessage());
	            return SUCCESS;

	        }*/catch( Exception e){
	            success = false;
	            addActionError(e.getMessage());
	            return SUCCESS;
	        }
	    }

//***	

	
	public String obtenerComboUnidadTiempoComprar() throws Exception {
        try {
        	comboUnidadTiempoAComprar = combosManager.matrizUnidadTiempo();
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
	
	
	public String obtenerComboPrioridadDatosDelCaso() throws Exception {
        try {
        	//comboPrioridad = combosManager.comboPrioridad();
            success = true;
            return SUCCESS;

        }/*catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }*/catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }
	
	public String obtenerComboEstatusDelCaso() throws Exception {
        try {
        	//comboEstatus = combosManager.comboEstatus();
            success = true;
            return SUCCESS;

        }/*catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }*/catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }
	
	public String obtenerComboModulosDelCaso() throws Exception {
        try {
        	//comboUnidadTiempoAComprar = combosManager.comboModulos();
            success = true;
            return SUCCESS;

        }/*catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }*/catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }
	
	public String obtenerComboRolesUsuariosResponsables() throws Exception {
        try {
        	//comboUnidadTiempoAComprar = combosManager.comboRolesUsuariosResponsables();
            success = true;
            return SUCCESS;

        }/*catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }*/catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }
	
	public String obtenerComboNivelesAtencion() throws Exception {
        try {
        	comboNivelAtencion = combosManager.matrizNivelAtencion();
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
	
	public String obtieneDatosModulo() throws Exception {
        try {
        	comboDatosModulo = combosManager.obtieneDatosModulo(cdTabla);		
        	success = true;
            return SUCCESS;

        }/*catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }*/catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }

	
	public String obtenerComboIndicadorAviso() throws Exception {
        try {
        	comboIndicadorAviso = combosManager.comboIndicadorAviso();
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
	
	public String obtenerTareas() throws Exception {
        try {
        	comboTareas = combosManager.obtenerTareas(cdElemento, cdUniEco, cdRamo);
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
	
	public String obtenerAseguradorasProdCorp() throws Exception {
        try {
        	comboTareas = combosManager.obtenerAseguradorasProdCorp(cdProceso, cdElemento, cdRamo);
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
	
	public String obtenerPrioridades() throws Exception {
        try {
        	comboPrioridades = combosManager.obtenerPrioridades();
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
		
	public String obtenerModulos() throws Exception {
        try {
        	comboObtenerModulos = combosManager.obtenerModulos();
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
	
	public String obtenerResponsables() throws Exception {
        try {
        	comboResponsables = combosManager.matrizResponsables(cdproceso);
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
	
	public String obtenerComboDatosCatalogosDep() throws Exception {
        try {
        	comboDatosCatalogoDep = combosManager.obtenerDatosCatalogoDep(cdTabla,valor1,valor2);
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

	public String obtenerComboTareaPrioridad() throws Exception {
        try {
        	comboTareaPrioridad = combosManager.obtenerTareaPrioridad();
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

	public String obtenerComboTareaEstatus() throws Exception {
        try {
        	comboTareaEstatus = combosManager.obtenerTareaEstatus();
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
	
	public String obtenerComboMonedas() throws Exception {
        try {
        	comboMonedas = combosManager.obtenerMonedas();
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
	
	public String obtenerComboNivelAtencionMatriz() throws Exception {
        try {
        	comboNivelAtencionMatriz = combosManager.comboNivelAtencionMatriz(cdMatriz);
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
	
	
	public String obtenerComboStatus() throws Exception {
        try {
        	elementosComboBox = combosManager.obtenerStatusCaso();
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
	//String pv_cdmatriz_i, String pv_cdmodulo_i,String pv_cdnivatn_i
	public String obtenerComboUsuariosReemplazantes() throws Exception {
        try {
        	comboUsuariosReemplazantes = combosManager2.comboObtieneUsuarios(cdmatriz,cdmodulo,cdnivatn);         /////
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
	

	


	public String obtenerComboUsuariosResponsables() throws Exception {
        try {
        	comboUsuarioResponsable= combosManager2.comboUsuarioResponsable(nmConfig);       
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
	
    
    


	
	
	public String comboRolesUsuarios() throws Exception {
        try {
        	comboRolesUsuarios = combosManager.obtenerRolesUsuarios();
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
	
	public String comboUsuarios() throws Exception {
        try {
        	comboUsuarios = combosManager.obtenerUsuarios(cdsisrol);
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
	
	
	public String comboModulosEncuestas() throws Exception {
        try {
        	comboModulosEncuestas = combosManager.comboModuloConfigEncuestas();
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
	
	public String comboEncuestas() throws Exception {
        try {
        	comboEncuestas = combosManager.comboEncuestas();
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
	
	public String comboUsuariosEncuestas() throws Exception {
        try {
        	cboUsuariosEncuestas = combosManager.comboUsuariosEncuestas(cdModulo);
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
	
	public String comboCampanEncuestas() throws Exception {
        try {
        	cboCampanEncuestas = combosManager.comboCampanEncuestas();
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
	
	
	public String comboProductosAseguradoraEncuestas() throws Exception {
        try {
        	cboProductosAsegEncuestas = combosManager.comboObtenerProductosAseguradoraEncuesta(pv_cdunieco_i);
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
	
	
	public String comboClientesAseguradoraEncuestas() throws Exception {
		
        try {
        	cboCliCorpoAseguradoraProducto = combosManager.comboClientesCorpoAseguradoraProducto(cdUniEco, cdRamo);
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
	
	public String obtenerDatosPais() throws Exception {
        try {
        	comboDatosPais = combosManager.obtienePais(cdTabla);		
        	success = true;			
            return SUCCESS;

        }/*catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }*/catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }
	

	public String obtenerDatosMes() throws Exception {
        try {
        	comboDatosMes = combosManager.obtieneMes(cdTabla);		
        	success = true;			
            return SUCCESS;

        }/*catch(ApplicationException e)
		{
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;

        }*/catch( Exception e){
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
    }
	
	

	public String obtieneTiposArchivos() throws Exception {
        try {
        	comboTiposArchivos = combosManager.obtieneTiposArchivos();
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
	
	
	
	
     public String comboObtenerListas() throws Exception {
		
        try {
        	comboGetListas = combosManager.getListas();
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
	
     public String comboListas() throws Exception {
 		
         try {
         	comboGetListas = combosManager.getListas(cdTabla, valAnter, valAntAnt);
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
   //--------------------Combos configurar compra tiempo----------------//
 	
 	public String comboUnidad() throws Exception {
         try {
         	cboUnidad= combosManager.obtieneUnidadTiempo();		
         	success = true;			
             return SUCCESS;

         }/*catch(ApplicationException e)
 		{
             success = false;
             addActionError(e.getMessage());
             return SUCCESS;

         }*/catch( Exception e){
             success = false;
             addActionError(e.getMessage());
             return SUCCESS;
         }
     }
 	
 	public String comboNivel() throws Exception {
         try {
         	cboNivel= combosManager.obtieneNivelAtencion();		
         	success = true;			
             return SUCCESS;

         }/*catch(ApplicationException e)
 		{
             success = false;
             addActionError(e.getMessage());
             return SUCCESS;

         }*/catch( Exception e){
             success = false;
             addActionError(e.getMessage());
             return SUCCESS;
         }
     }
	

    public String comboObtieneFormatos() throws Exception {
        try {
            comboFormatosArchivos= combosManager2.comboObtieneFormatos();
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

    public String comboObtieneTablas() throws Exception {
        try {
        	ComboTablasArchivos= combosManager2.comboObtieneTablas();
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
    
    public String comboObtenerTipoFax() throws Exception {
        try {
        	comboTipoFax= combosManager2.comboObtieneTipoFax();
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
    
    public String comboObtieneAlgoritmos() throws Exception {
        try {
            comboAlgoritmos= combosManager2.comboObtieneAlgoritmos();
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
    
    
    
    
    public String comboObtieneTipoGuion() throws Exception {
        try {
            comboTipoGuion= combosManager2.comboObtieneTipoGuion();
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
    
    public String obtenerComboCargaArchivos() throws Exception {
        try {
            comboCargaArchivos = combosManager2.comboCargaArchivos();
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
    
    public String obtenerComboCargaArchivos2() throws Exception {
        try {
            comboCargaArchivos = combosManager2.comboCargaArchivos2();
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

    public String obtenerTablaClick() throws Exception {
        try {
            comboTabla = combosManager2.obtieneTabla();
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

    public String obtenerComboModuloEnc() throws Exception {
        try {
        	comboModuloEnc = combosManager.comboModuloEnc(cdUniEco, cdRamo);
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

    public String obtenerComboCampanhaEnc() throws Exception {
        try {
        	comboCampanhaEnc = combosManager.comboCampanhaEnc(cdUniEco, cdRamo, cdModulo);
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

    public String obtenerComboTemaEnc() throws Exception {
        try {
        	comboTemaEnc = combosManager.comboTemaEnc(cdUniEco, cdRamo, cdModulo, cdCampan);
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
    
    public String obtenerComboEncuestaEnc() throws Exception {
        try {
        	comboEncuestaEnc = combosManager.comboEncuestaEnc(cdUniEco, cdRamo, cdModulo, cdCampan, cdProceso,nmConfig);
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
    
    public List getComboUnidadTiempoAComprar() {
		return comboUnidadTiempoAComprar;
	}


	public void setComboUnidadTiempoAComprar(List comboUnidadTiempoAComprar) {
		this.comboUnidadTiempoAComprar = comboUnidadTiempoAComprar;
	}


	public List getComboPrioridad() {
		return comboPrioridad;
	}


	public void setComboPrioridad(List comboPrioridad) {
		this.comboPrioridad = comboPrioridad;
	}


	public List getComboEstatus() {
		return comboEstatus;
	}


	public void setComboEstatus(List comboEstatus) {
		this.comboEstatus = comboEstatus;
	}


	public List getComboPersonas() {
		return comboPersonas;
	}


	public void setComboPersonas(List comboPersonas) {
		this.comboPersonas = comboPersonas;
	}


	/**
	 * @param clientesComboBox the clientesComboBox to set
	 */
	public void setClientesComboBox(List clientesComboBox) {
		this.clientesComboBox = clientesComboBox;
	}


    public List getTiposNivelComboBox() {
        return tiposNivelComboBox;
    }

    public void setTiposNivelComboBox(List tiposNivelComboBox) {
        this.tiposNivelComboBox = tiposNivelComboBox;
    }

    public List getPadresComboBox() {
        return padresComboBox;
    }

    public void setPadresComboBox(List padresComboBox) {
        this.padresComboBox = padresComboBox;
    }


	/**
	 * @return the ramosComboBox
	 */
	public List getRamosComboBox() {
		return ramosComboBox;
	}


	/**
	 * @param ramosComboBox the ramosComboBox to set
	 */
	public void setRamosComboBox(List ramosComboBox) {
		this.ramosComboBox = ramosComboBox;
	}


	/**
	 * @return the subRamosComboBox
	 */
	public List getSubRamosComboBox() {
		return subRamosComboBox;
	}


	/**
	 * @param subRamosComboBox the subRamosComboBox to set
	 */
	public void setSubRamosComboBox(List subRamosComboBox) {
		this.subRamosComboBox = subRamosComboBox;
	}


	/**
	 * @return the productosComboBox
	 */
	public List getProductosComboBox() {
		return productosComboBox;
	}


	/**
	 * @param productosComboBox the productosComboBox to set
	 */
	public void setProductosComboBox(List productosComboBox) {
		this.productosComboBox = productosComboBox;
	}


	/**
	 * @return the idiomasComboBox
	 */
	public List getIdiomasComboBox() {
		return idiomasComboBox;
	}


	/**
	 * @param idiomasComboBox the idiomasComboBox to set
	 */
	public void setIdiomasComboBox(List idiomasComboBox) {
		this.idiomasComboBox = idiomasComboBox;
	}


	/**
	 * @return the usuariosComboBox
	 */
	public List getUsuariosComboBox() {
		return usuariosComboBox;
	}


	/**
	 * @param usuariosComboBox the usuariosComboBox to set
	 */
	public void setUsuariosComboBox(List usuariosComboBox) {
		this.usuariosComboBox = usuariosComboBox;
	}


	/**
	 * @return the procesosComboBox
	 */
	public List getProcesosComboBox() {
		return procesosComboBox;
	}


	/**
	 * @param procesosComboBox the procesosComboBox to set
	 */
	public void setProcesosComboBox(List procesosComboBox) {
		this.procesosComboBox = procesosComboBox;
	}


	/**
	 * @return the temporalidadesComboBox
	 */
	public List getTemporalidadesComboBox() {
		return temporalidadesComboBox;
	}


	/**
	 * @param temporalidadesComboBox the temporalidadesComboBox to set
	 */
	public void setTemporalidadesComboBox(List temporalidadesComboBox) {
		this.temporalidadesComboBox = temporalidadesComboBox;
	}


	/**
	 * @return the regionesComboBox
	 */
	public List getRegionesComboBox() {
		return regionesComboBox;
	}


	/**
	 * @param regionesComboBox the regionesComboBox to set
	 */
	public void setRegionesComboBox(List regionesComboBox) {
		this.regionesComboBox = regionesComboBox;
	}


	/**
	 * @return the estadosDescuentosComboBox
	 */
	public List getEstadosDescuentosComboBox() {
		return estadosDescuentosComboBox;
	}


	/**
	 * @param estadosDescuentosComboBox the estadosDescuentosComboBox to set
	 */
	public void setEstadosDescuentosComboBox(List estadosDescuentosComboBox) {
		this.estadosDescuentosComboBox = estadosDescuentosComboBox;
	}


	/**
	 * @return the productosDescuentosComboBox
	 */
	public List getProductosDescuentosComboBox() {
		return productosDescuentosComboBox;
	}


	/**
	 * @param productosDescuentosComboBox the productosDescuentosComboBox to set
	 */
	public void setProductosDescuentosComboBox(List productosDescuentosComboBox) {
		this.productosDescuentosComboBox = productosDescuentosComboBox;
	}


	public List getTiposDsctoComboBox() {
		return tiposDsctoComboBox;
	}


	public void setTiposDsctoComboBox(List tiposDsctoComboBox) {
		this.tiposDsctoComboBox = tiposDsctoComboBox;
	}



	public List getPlanesComboBox() {
		return planesComboBox;
	}


	public void setPlanesComboBox(List planesComboBox) {
		this.planesComboBox = planesComboBox;
	}

	public void confAlertasAutoClientes () {
		
	}


	public List getConfAlertasAutoUsuarios() {
		return confAlertasAutoUsuarios;
	}


	public void setConfAlertasAutoUsuarios(List confAlertasAutoUsuarios) {
		this.confAlertasAutoUsuarios = confAlertasAutoUsuarios;
	}


	public List getConfAlertasAutoClientes() {
		return confAlertasAutoClientes;
	}


	public void setConfAlertasAutoClientes(List confAlertasAutoClientes) {
		this.confAlertasAutoClientes = confAlertasAutoClientes;
	}


	public List getConfAlertasAutoProcesos() {
		return confAlertasAutoProcesos;
	}


	public void setConfAlertasAutoProcesos(List confAlertasAutoProcesos) {
		this.confAlertasAutoProcesos = confAlertasAutoProcesos;
	}


	public List getConfAlertasAutoTemporalidad() {
		return confAlertasAutoTemporalidad;
	}


	public void setConfAlertasAutoTemporalidad(List confAlertasAutoTemporalidad) {
		this.confAlertasAutoTemporalidad = confAlertasAutoTemporalidad;
	}


	public List getConfAlertasAutoRol() {
		return confAlertasAutoRol;
	}


	public void setConfAlertasAutoRol(List confAlertasAutoRol) {
		this.confAlertasAutoRol = confAlertasAutoRol;
	}


	public List getConfAlertasAutoTipoRamo() {
		return confAlertasAutoTipoRamo;
	}


	public void setConfAlertasAutoTipoRamo(List confAlertasAutoTipoRamo) {
		this.confAlertasAutoTipoRamo = confAlertasAutoTipoRamo;
	}


	public List getConfAlertasAutoRegion() {
		return confAlertasAutoRegion;
	}


	public void setConfAlertasAutoRegion(List confAlertasAutoRegion) {
		this.confAlertasAutoRegion = confAlertasAutoRegion;
	}

    public void setSiNo(List siNo) {
        this.siNo = siNo;
    }


    public List getSiNo() {
        return siNo;
    }


    public List getFrecuencias() {
        return frecuencias;
    }

    public void setFrecuencias(List frecuencias) {
        this.frecuencias = frecuencias;
    }


    public List getConceptosProducto() {
        return conceptosProducto;
    }

    public void setConceptosProducto(List conceptosProducto) {
        this.conceptosProducto = conceptosProducto;
    }


    public String getCdunieco() {
        return cdunieco;
    }

    public void setCdunieco(String cdunieco) {
        this.cdunieco = cdunieco;
    }

    public String getCdtipram() {
        return cdtipram;
    }

    public void setCdtipram(String cdtipram) {
        this.cdtipram = cdtipram;
    }

    public String getCdsubram() {
        return cdsubram;
    }

    public void setCdsubram(String cdsubram) {
        this.cdsubram = cdsubram;
    }


    public List getProductosAseguradoraRamoSubRamo() {
        return productosAseguradoraRamoSubRamo;
    }

    public void setProductosAseguradoraRamoSubRamo(List productosAseguradoraRamoSubRamo) {
        this.productosAseguradoraRamoSubRamo = productosAseguradoraRamoSubRamo;
    }


    public List getLineasOperacion() {
        return lineasOperacion;
    }

    public void setLineasOperacion(List lineasOperacion) {
        this.lineasOperacion = lineasOperacion;
    }


	public List getEjecutivosAon() {
		return ejecutivosAon;
	}


	public void setEjecutivosAon(List ejecutivosAon) {
		this.ejecutivosAon = ejecutivosAon;
	}


	public List getEstadosEjecutivo() {
		return estadosEjecutivo;
	}


	public void setEstadosEjecutivo(List estadosEjecutivo) {
		this.estadosEjecutivo = estadosEjecutivo;
	}


	public List getLineasAtencion() {
		return lineasAtencion;
	}


	public void setLineasAtencion(List lineasAtencion) {
		this.lineasAtencion = lineasAtencion;
	}


	public List getGrupoPersonasCliente() {
		return grupoPersonasCliente;
	}


	public void setGrupoPersonasCliente(List grupoPersonasCliente) {
		this.grupoPersonasCliente = grupoPersonasCliente;
	}


	/**
	 * @return the cdElemento
	 */
	public String getCdElemento() {
		return cdElemento;
	}


	/**
	 * @param cdElemento the cdElemento to set
	 */
	public void setCdElemento(String cdElemento) {
		this.cdElemento = cdElemento;
	}


	public List getPolizasPorAseguradora() {
		return polizasPorAseguradora;
	}


	public void setPolizasPorAseguradora(List polizasPorAseguradora) {
		this.polizasPorAseguradora = polizasPorAseguradora;
	}


	public List getReciboPorPolizaPorAseguradora() {
		return reciboPorPolizaPorAseguradora;
	}


	public void setReciboPorPolizaPorAseguradora(List reciboPorPolizaPorAseguradora) {
		this.reciboPorPolizaPorAseguradora = reciboPorPolizaPorAseguradora;
	}


	public String getCdRamo() {
		return cdRamo;
	}


	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}


	public String getNmPoliza() {
		return nmPoliza;
	}


	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}


	public String getCodigoEstructura() {
		return codigoEstructura;
	}


	public void setCodigoEstructura(String codigoEstructura) {
		this.codigoEstructura = codigoEstructura;
	}


	public List getClientesCorporativo() {
			return clientesCorporativo;
		}


		public void setClientesCorporativo(List clientesCorporativo) {
			this.clientesCorporativo = clientesCorporativo;
		}


		public String getCdPerson() {
			return cdPerson;
		}


		public void setCdPerson(String cdPerson) {
			this.cdPerson = cdPerson;
		}


		public List getProductosEjecutivosCuenta() {
			return productosEjecutivosCuenta;
		}


		public void setProductosEjecutivosCuenta(List productosEjecutivosCuenta) {
			this.productosEjecutivosCuenta = productosEjecutivosCuenta;
		}


		public String getCdTipRam() {
			return cdTipRam;
		}


		public void setCdTipRam(String cdTipRam) {
			this.cdTipRam = cdTipRam;
		}


    public List getProductosAseguradoraCliente() {
        return productosAseguradoraCliente;
    }

    public void setProductosAseguradoraCliente(List productosAseguradoraCliente) {
        this.productosAseguradoraCliente = productosAseguradoraCliente;
    }


    public List getTiposRamo() {
        return tiposRamo;
    }

    public void setTiposRamo(List tiposRamo) {
        this.tiposRamo = tiposRamo;
    }


	public List getTiposRamoClienteEjecCuenta() {
		return tiposRamoClienteEjecCuenta;
	}


	public void setTiposRamoClienteEjecCuenta(List tiposRamoClienteEjecCuenta) {
		this.tiposRamoClienteEjecCuenta = tiposRamoClienteEjecCuenta;
	}


    public List getEstadosAgrupacion() {
        return estadosAgrupacion;
    }

    public void setEstadosAgrupacion(List estadosAgrupacion) {
        this.estadosAgrupacion = estadosAgrupacion;
    }

    public List getTiposAgrupacion() {
        return tiposAgrupacion;
    }

    public void setTiposAgrupacion(List tiposAgrupacion) {
        this.tiposAgrupacion = tiposAgrupacion;
    }


	public List getAgrupacionPapel() {
		return agrupacionPapel;
	}


	public void setAgrupacionPapel(List agrupacionPapel) {
		this.agrupacionPapel = agrupacionPapel;
	}


	public String getCdUniEco() {
		return cdUniEco;
	}


	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}


	public String getCdTipSit() {
		return cdTipSit;
	}


	public void setCdTipSit(String cdTipSit) {
		this.cdTipSit = cdTipSit;
	}


	public List getPersonasJ() {
		return personasJ;
	}


	public void setPersonasJ(List personasJ) {
		this.personasJ = personasJ;
	}


    public List getBloques() {
        return bloques;
    }

    public void setBloques(List bloques) {
        this.bloques = bloques;
    }


	public List getFormaCalculoFolioNroIncisos() {
		return formaCalculoFolioNroIncisos;
	}


	public void setFormaCalculoFolioNroIncisos(List formaCalculoFolioNroIncisos) {
		this.formaCalculoFolioNroIncisos = formaCalculoFolioNroIncisos;
	}


	public List getIndicadorNumeracionNroIncisos() {
		return indicadorNumeracionNroIncisos;
	}


	public void setIndicadorNumeracionNroIncisos(List indicadorNumeracionNroIncisos) {
		this.indicadorNumeracionNroIncisos = indicadorNumeracionNroIncisos;
	}


	public List getIndicador_SP_NroIncisos() {
		return indicador_SP_NroIncisos;
	}


	public void setIndicador_SP_NroIncisos(List indicador_SP_NroIncisos) {
		this.indicador_SP_NroIncisos = indicador_SP_NroIncisos;
	}


	public List getComboNacionalidad() {
		return comboNacionalidad;
	}


	public void setComboNacionalidad(List comboNacionalidad) {
		this.comboNacionalidad = comboNacionalidad;
	}


	public List getComboEstadoCivil() {
		return comboEstadoCivil;
	}


	public void setComboEstadoCivil(List comboEstadoCivil) {
		this.comboEstadoCivil = comboEstadoCivil;
	}


	public List getComboTipoEmpresa() {
		return comboTipoEmpresa;
	}


	public void setComboTipoEmpresa(List comboTipoEmpresa) {
		this.comboTipoEmpresa = comboTipoEmpresa;
	}


	public List getComboTipoIdentificador() {
		return comboTipoIdentificador;
	}


	public void setComboTipoIdentificador(List comboTipoIdentificador) {
		this.comboTipoIdentificador = comboTipoIdentificador;
	}


	public List getComboTiposDomicilio() {
		return comboTiposDomicilio;
	}


	public void setComboTiposDomicilio(List comboTiposDomicilio) {
		this.comboTiposDomicilio = comboTiposDomicilio;
	}


	public List getComboPaises() {
		return comboPaises;
	}


	public void setComboPaises(List comboPaises) {
		this.comboPaises = comboPaises;
	}


	public List getComboColonias() {
		return comboColonias;
	}


	public void setComboColonias(List comboColonias) {
		this.comboColonias = comboColonias;
	}


	public List getComboEstados() {
		return comboEstados;
	}


	public void setComboEstados(List comboEstados) {
		this.comboEstados = comboEstados;
	}


	public List getComboMunicipios() {
		return comboMunicipios;
	}


	public void setComboMunicipios(List comboMunicipios) {
		this.comboMunicipios = comboMunicipios;
	}


	public String getCodigoPais() {
		return codigoPais;
	}


	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}


	public String getCodigoEstado() {
		return codigoEstado;
	}


	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}


	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}


	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}


	public List getComboTipoTelefono() {
		return comboTipoTelefono;
	}


	public void setComboTipoTelefono(List comboTipoTelefono) {
		this.comboTipoTelefono = comboTipoTelefono;
	}


	public List getComboListaGrupoCorporativo() {
		return comboListaGrupoCorporativo;
	}


	public void setComboListaGrupoCorporativo(List comboListaGrupoCorporativo) {
		this.comboListaGrupoCorporativo = comboListaGrupoCorporativo;
	}


	public List getComboListaEstadoCorporativo() {
		return comboListaEstadoCorporativo;
	}


	public void setComboListaEstadoCorporativo(List comboListaEstadoCorporativo) {
		this.comboListaEstadoCorporativo = comboListaEstadoCorporativo;
	}


	public List getComboNivelCorporativo() {
		return comboNivelCorporativo;
	}


	public void setComboNivelCorporativo(List comboNivelCorporativo) {
		this.comboNivelCorporativo = comboNivelCorporativo;
	}


	public List getComboSexo() {
		return comboSexo;
	}


	public void setComboSexo(List comboSexo) {
		this.comboSexo = comboSexo;
	}


	public List getComboRazones() {
		return comboRazones;
	}


	public void setComboRazones(List comboRazones) {
		this.comboRazones = comboRazones;
	}


	public List getComboMetodos() {
		return comboMetodos;
	}


	public void setComboMetodos(List comboMetodos) {
		this.comboMetodos = comboMetodos;
	}
	


	public List getComboPeriodosGracia() {
			return comboPeriodosGracia;
		}


		public void setComboPeriodosGracia(List comboPeriodosGracia) {
			this.comboPeriodosGracia = comboPeriodosGracia;
		}


		public List getComboTiposDocumento() {
			return comboTiposDocumento;
		}


		public void setComboTiposDocumento(List comboTiposDocumento) {
			this.comboTiposDocumento = comboTiposDocumento;
		}


		public List getComboSeccionesOrden() {
			return comboSeccionesOrden;
		}


		public void setComboSeccionesOrden(List comboSeccionesOrden) {
			this.comboSeccionesOrden = comboSeccionesOrden;
		}


		public List getComboTiposObjetos() {
			return comboTiposObjetos;
		}


		public void setComboTiposObjetos(List comboTiposObjetos) {
			this.comboTiposObjetos = comboTiposObjetos;
		}
		

		 public List getComboSeccionFormato() {
			return comboSeccionFormato;
		}


		public void setComboSeccionFormato(List comboSeccionFormato) {
			this.comboSeccionFormato = comboSeccionFormato;
		}


		
		public List getComboBloquesConfigAtributosFormatoOrdenTrabajo() {
			return comboBloquesConfigAtributosFormatoOrdenTrabajo;
		}


		public void setComboBloquesConfigAtributosFormatoOrdenTrabajo(
				List comboBloquesConfigAtributosFormatoOrdenTrabajo) {
			this.comboBloquesConfigAtributosFormatoOrdenTrabajo = comboBloquesConfigAtributosFormatoOrdenTrabajo;
		}


		public List getComboFormatosCampo() {
			return comboFormatosCampo;
		}


		public void setComboFormatosCampo(List comboFormatosCampo) {
			this.comboFormatosCampo = comboFormatosCampo;
		}



		public List getComboCampoBloques() {
			return comboCampoBloques;
		}


		public void setComboCampoBloques(List comboCampoBloques) {
			this.comboCampoBloques = comboCampoBloques;
		}


		public String getCdBloque() {
			return cdBloque;
		}


		public void setCdBloque(String cdBloque) {
			this.cdBloque = cdBloque;
		}


		public String getOtTipo() {
			return otTipo;
		}


		public void setOtTipo(String otTipo) {
			this.otTipo = otTipo;
		}

		public List getComboTiposError() {
			return comboTiposError;
		}

		public void setComboTiposError(List comboTiposError) {
			this.comboTiposError = comboTiposError;
		}


		public List getComboProcesos() {
			return comboProcesos;
		}


		public void setComboProcesos(List comboProcesos) {
			this.comboProcesos = comboProcesos;
		}


		public List getComboFormatos() {
			return comboFormatos;
		}


		public void setComboFormatos(List comboFormatos) {
			this.comboFormatos = comboFormatos;
		}


		/*public CombosManager getCombosManager() {
			return combosManager;
		}*/


		public List getComboFormasCalculoFolio() {
			return comboFormasCalculoFolio;
		}


		public void setComboFormasCalculoFolio(List comboFormasCalculoFolio) {
			this.comboFormasCalculoFolio = comboFormasCalculoFolio;
		}


		public String getCdUsuario() {
			return cdUsuario;
		}


		public void setCdUsuario(String cdUsuario) {
			this.cdUsuario = cdUsuario;
		}


		public String getCdFormato() {
			return cdFormato;
		}


		public void setCdFormato(String cdFormato) {
			this.cdFormato = cdFormato;
		}


		public List getComboProductosTramoCliente() {
			return comboProductosTramoCliente;
		}


		public void setComboProductosTramoCliente(List comboProductosTramoCliente) {
			this.comboProductosTramoCliente = comboProductosTramoCliente;
		}


		public List getComboTipoSituacionProducto() {
			return comboTipoSituacionProducto;
		}


		public void setComboTipoSituacionProducto(List comboTipoSituacionProducto) {
			this.comboTipoSituacionProducto = comboTipoSituacionProducto;
		}


		public List getComboInstrumentosPago() {
			return comboInstrumentosPago;
		}


		public void setComboInstrumentosPago(List comboInstrumentosPago) {
			this.comboInstrumentosPago = comboInstrumentosPago;
		}


		public List getComboBancos() {
			return comboBancos;
		}


		public void setComboBancos(List comboBancos) {
			this.comboBancos = comboBancos;
		}


		public List getComboTiposTarjetas() {
			return comboTiposTarjetas;
		}


		public void setComboTiposTarjetas(List comboTiposTarjetas) {
			this.comboTiposTarjetas = comboTiposTarjetas;
		}


		public List getComboIndicadoresSINO() {
			return comboIndicadoresSINO;
		}


		public void setComboIndicadoresSINO(List comboIndicadoresSINO) {
			this.comboIndicadoresSINO = comboIndicadoresSINO;
		}


		public String getCdPlan() {
			return cdPlan;
		}


		public void setCdPlan(String cdPlan) {
			this.cdPlan = cdPlan;
		}


		public List getComboTiposGarantia() {
			return comboTiposGarantia;
		}


		public void setComboTiposGarantia(List comboTiposGarantia) {
			this.comboTiposGarantia = comboTiposGarantia;
		}


		public List getComboPlanesProducto() {
			return comboPlanesProducto;
		}


		public void setComboPlanesProducto(List comboPlanesProducto) {
			this.comboPlanesProducto = comboPlanesProducto;
		}


		public List getComboGenerico() {
			return comboGenerico;
		}


		public void setComboGenerico(List comboGenerico) {
			this.comboGenerico = comboGenerico;
		}


		public String getIdTablaLogica() {
			return idTablaLogica;
		}


		public void setIdTablaLogica(String idTablaLogica) {
			this.idTablaLogica = idTablaLogica;
		}


		public List getComboFormaCalculo() {
			return comboFormaCalculo;
		}


		public void setComboFormaCalculo(List comboFormaCalculo) {
			this.comboFormaCalculo = comboFormaCalculo;
		}


		public List getComboFormaAtributo() {
			return comboFormaAtributo;
		}


		public void setComboFormaAtributo(List comboFormaAtributo) {
			this.comboFormaAtributo = comboFormaAtributo;
		}


		public List getComboNrosPolizas() {
			return comboNrosPolizas;
		}


		public void setComboNrosPolizas(List comboNrosPolizas) {
			this.comboNrosPolizas = comboNrosPolizas;
		}


		public List getComboTiposRenova() {
			return comboTiposRenova;
		}


		public void setComboTiposRenova(List comboTiposRenova) {
			this.comboTiposRenova = comboTiposRenova;
		}


		public List getComboAccionesRenovacionRol() {
			return comboAccionesRenovacionRol;
		}


		public void setComboAccionesRenovacionRol(List comboAccionesRenovacionRol) {
			this.comboAccionesRenovacionRol = comboAccionesRenovacionRol;
		}


		public List getComboAccionesRenovacionPantalla() {
			return comboAccionesRenovacionPantalla;
		}


		public void setComboAccionesRenovacionPantalla(
				List comboAccionesRenovacionPantalla) {
			this.comboAccionesRenovacionPantalla = comboAccionesRenovacionPantalla;
		}


		public List getComboAccionesRenovacionCampo() {
			return comboAccionesRenovacionCampo;
		}


		public void setComboAccionesRenovacionCampo(List comboAccionesRenovacionCampo) {
			this.comboAccionesRenovacionCampo = comboAccionesRenovacionCampo;
		}


		public List getComboAccionesRenovacionAccion() {
			return comboAccionesRenovacionAccion;
		}


		public void setComboAccionesRenovacionAccion(List comboAccionesRenovacionAccion) {
			this.comboAccionesRenovacionAccion = comboAccionesRenovacionAccion;
		}


		public String getCdTitulo() {
			return cdTitulo;
		}


		public void setCdTitulo(String cdTitulo) {
			this.cdTitulo = cdTitulo;
		}


		public List getComboAccionesRenovacionRoles() {
			return comboAccionesRenovacionRoles;
		}


		public void setComboAccionesRenovacionRoles(List comboAccionesRenovacionRoles) {
			this.comboAccionesRenovacionRoles = comboAccionesRenovacionRoles;
		}


		public List getComboTipoObjeto() {
			return comboTipoObjeto;
		}


		public void setComboTipoObjeto(List comboTipoObjeto) {
			this.comboTipoObjeto = comboTipoObjeto;
		}


		public List getComboFormaPago() {
			return comboFormaPago;
		}


		public void setComboFormaPago(List comboFormaPago) {
			this.comboFormaPago = comboFormaPago;
		}


		public List getComboInstrumentoPago() {
			return comboInstrumentoPago;
		}


		public void setComboInstrumentoPago(List comboInstrumentoPago) {
			this.comboInstrumentoPago = comboInstrumentoPago;
		}


		public String getOtClave1() {
			return otClave1;
		}


		public void setOtClave1(String otClave1) {
			this.otClave1 = otClave1;
		}


		public List getComboRoles() {
			return comboRoles;
		}


		public void setComboRoles(List comboRoles) {
			this.comboRoles = comboRoles;
		}


		public List getComboNombrePersonas() {
			return comboNombrePersonas;
		}


		public void setComboNombrePersonas(List comboNombrePersonas) {
			this.comboNombrePersonas = comboNombrePersonas;
		}


		public List getComboIncisos() {
			return comboIncisos;
		}


		public void setComboIncisos(List comboIncisos) {
			this.comboIncisos = comboIncisos;
		}


		public String getEstado() {
			return estado;
		}


		public void setEstado(String estado) {
			this.estado = estado;
		}


		public String getNmSituac() {
			return nmSituac;
		}


		public void setNmSituac(String nmSituac) {
			this.nmSituac = nmSituac;
		}

		public List getComboCoberturasProducto() {
			return comboCoberturasProducto;
		}

		public void setComboCoberturasProducto(List comboCoberturasProducto) {
			this.comboCoberturasProducto = comboCoberturasProducto;
		}

		public List getComboObjetosRamo() {
			return comboObjetosRamo;
		}

		public void setComboObjetosRamo(List comboObjetosRamo) {
			this.comboObjetosRamo = comboObjetosRamo;
		}

		public List getComboNotificacionFormato() {
			return comboNotificacionFormato;
		}

		public void setComboNotificacionFormato(List comboNotificacionFormato) {
			this.comboNotificacionFormato = comboNotificacionFormato;
		}

		public List getComboTipoMetodoEnvio() {
			return comboTipoMetodoEnvio;
		}

		public void setComboTipoMetodoEnvio(List comboTipoMetodoEnvio) {
			this.comboTipoMetodoEnvio = comboTipoMetodoEnvio;
		}

		public String getDsNomFormato() {
			return dsNomFormato;
		}

		public void setDsNomFormato(String dsNomFormato) {
			this.dsNomFormato = dsNomFormato;
		}


		public List getComboTipoGuion() {
			return comboTipoGuion;
		}


		public void setComboTipoGuion(List comboTipoGuion) {
			this.comboTipoGuion = comboTipoGuion;
		}


		public List getComboProcesosCat() {
			return comboProcesosCat;
		}


		public void setComboProcesosCat(List comboProcesosCat) {
			this.comboProcesosCat = comboProcesosCat;
		}

		public List getComboClientesCorpBO() {
			return comboClientesCorpBO;
		}

		public void setComboClientesCorpBO(List comboClientesCorpBO) {
			this.comboClientesCorpBO = comboClientesCorpBO;
		}


		public List getComboNivelAtencion() {
			return comboNivelAtencion;
		}


		public void setComboNivelAtencion(List comboNivelAtencion) {
			this.comboNivelAtencion = comboNivelAtencion;
		}

		public List getComboIndicadorAviso() {
			return comboIndicadorAviso;
		}

		public void setComboIndicadorAviso(List comboIndicadorAviso) {
			this.comboIndicadorAviso = comboIndicadorAviso;
		}


		public String getCdTabla() {
			return cdTabla;
		}


		public void setCdTabla(String cdTabla) {
			this.cdTabla = cdTabla;
		}


		public List getComboDatosCatalogo() {
			return comboDatosCatalogo;
		}


		public void setComboDatosCatalogo(List comboDatosCatalogo) {
			this.comboDatosCatalogo = comboDatosCatalogo;
		}


		public List getComboObtenerTareas() {
			return comboObtenerTareas;
		}


		public void setComboObtenerTareas(List comboObtenerTareas) {
			this.comboObtenerTareas = comboObtenerTareas;
		}


		public List getComboPrioridades() {
			return comboPrioridades;
		}


		public void setComboPrioridades(List comboPrioridades) {
			this.comboPrioridades = comboPrioridades;
		}


		public List getComboTareas() {
			return comboTareas;
		}


		public void setComboTareas(List comboTareas) {
			this.comboTareas = comboTareas;
		}


		public String getPv_cdtabla_i() {
			return pv_cdtabla_i;
		}


		public void setPv_cdtabla_i(String pv_cdtabla_i) {
			this.pv_cdtabla_i = pv_cdtabla_i;
		}


		public List getComboResponsables() {
			return comboResponsables;
		}


		public void setComboResponsables(List comboResponsables) {
			this.comboResponsables = comboResponsables;
		}


		public List getComboObtenerModulos() {
			return comboObtenerModulos;
		}


		public void setComboObtenerModulos(List comboObtenerModulos) {
			this.comboObtenerModulos = comboObtenerModulos;
		}


		public List getComboDatosCatalogoDep() {
			return comboDatosCatalogoDep;
		}


		public void setComboDatosCatalogoDep(List comboDatosCatalogoDep) {
			this.comboDatosCatalogoDep = comboDatosCatalogoDep;
		}


		public String getValor1() {
			return valor1;
		}


		public void setValor1(String valor1) {
			this.valor1 = valor1;
		}


		public String getValor2() {
			return valor2;
		}


		public void setValor2(String valor2) {
			this.valor2 = valor2;
		}
		
		public List getComboTareaPrioridad() {
			return comboTareaPrioridad;
		}

		public void setComboTareaPrioridad(List comboTareaPrioridad) {
			this.comboTareaPrioridad = comboTareaPrioridad;
		}

		public List getComboTareaEstatus() {
			return comboTareaEstatus;
		}

		public void setComboTareaEstatus(List comboTareaEstatus) {
			this.comboTareaEstatus = comboTareaEstatus;
		}


		public List getComboMonedas() {
			return comboMonedas;
		}


		public void setComboMonedas(List comboMonedas) {
			this.comboMonedas = comboMonedas;
		}


		public List getComboNivelAtencionMatriz() {
			return comboNivelAtencionMatriz;
		}


		public void setComboNivelAtencionMatriz(List comboNivelAtencionMatriz) {
			this.comboNivelAtencionMatriz = comboNivelAtencionMatriz;
		}


		public String getCdMatriz() {
			return cdMatriz;
		}


		public void setCdMatriz(String cdMatriz) {
			this.cdMatriz = cdMatriz;
		}	
		


		
		public List getComboDatosModulo() {
			return comboDatosModulo;
		}


		public void setComboDatosModulo(List comboDatosModulo) {
			this.comboDatosModulo = comboDatosModulo;
		}


		public String getCdNivatn() {
			return cdNivatn;
		}


		public void setCdNivatn(String cdNivatn) {
			this.cdNivatn = cdNivatn;
		}


		public List getComboUsuariosReemplazantes() {
			return comboUsuariosReemplazantes;
		}


		public void setComboUsuariosReemplazantes(List comboUsuariosReemplazantes) {
			this.comboUsuariosReemplazantes = comboUsuariosReemplazantes;
		}


		public List getComboRolesUsuarios() {
			return comboRolesUsuarios;
		}


		public void setComboRolesUsuarios(List comboRolesUsuarios) {
			this.comboRolesUsuarios = comboRolesUsuarios;
		}


		public List getComboUsuarios() {
			return comboUsuarios;
		}


		public void setComboUsuarios(List comboUsuarios) {
			this.comboUsuarios = comboUsuarios;
		}


		public List getComboModulosEncuestas() {
			return comboModulosEncuestas;
		}


		public void setComboModulosEncuestas(List comboModulosEncuestas) {
			this.comboModulosEncuestas = comboModulosEncuestas;
		}


		public List getComboEncuestas() {
			return comboEncuestas;
		}


		public void setComboEncuestas(List comboEncuestas) {
			this.comboEncuestas = comboEncuestas;
		}


		public List getCboUsuariosEncuestas() {
			return cboUsuariosEncuestas;
		}


		public void setCboUsuariosEncuestas(List cboUsuariosEncuestas) {
			this.cboUsuariosEncuestas = cboUsuariosEncuestas;
		}


		public String getCdModulo() {
			return cdModulo;
		}


		public void setCdModulo(String cdModulo) {
			this.cdModulo = cdModulo;
		}


		public List getCboCampanEncuestas() {
			return cboCampanEncuestas;
		}


		public void setCboCampanEncuestas(List cboCampanEncuestas) {
			this.cboCampanEncuestas = cboCampanEncuestas;
		}


		public List getCboProductosAsegEncuestas() {
			return cboProductosAsegEncuestas;
		}


		public void setCboProductosAsegEncuestas(List cboProductosAsegEncuestas) {
			this.cboProductosAsegEncuestas = cboProductosAsegEncuestas;
		}


		public String getPv_cdunieco_i() {
			return pv_cdunieco_i;
		}


		public void setPv_cdunieco_i(String pv_cdunieco_i) {
			this.pv_cdunieco_i = pv_cdunieco_i;
		}


		public List getCboCliCorpoAseguradoraProducto() {
			return cboCliCorpoAseguradoraProducto;
		}


		public void setCboCliCorpoAseguradoraProducto(
				List cboCliCorpoAseguradoraProducto) {
			this.cboCliCorpoAseguradoraProducto = cboCliCorpoAseguradoraProducto;
		}


		public List getComboDatosPais() {
			return comboDatosPais;
		}


		public void setComboDatosPais(List comboDatosPais) {
			this.comboDatosPais = comboDatosPais;
		}


		public List getComboDatosMes() {
			return comboDatosMes;
		}


		public void setComboDatosMes(List comboDatosMes) {
			this.comboDatosMes = comboDatosMes;
		}


		public List getComboGetListas() {
			return comboGetListas;
		}


		public void setComboGetListas(List comboGetListas) {
			this.comboGetListas = comboGetListas;
		}


		public List getCboUnidad() {
			return cboUnidad;
		}


		public void setCboUnidad(List cboUnidad) {
			this.cboUnidad = cboUnidad;
		}

		public CombosManager getCombosManager() {
			return combosManager;
		}


		public List getComboTablas() {
			return comboTablas;
		}


		public void setComboTablas(List comboTablas) {
			this.comboTablas = comboTablas;
		}


		public List getComboTablasArchivos() {
			return ComboTablasArchivos;
		}


		public void setComboTablasArchivos(List comboTablasArchivos) {
			ComboTablasArchivos = comboTablasArchivos;
		}

		
		
		public List getCboNivel() {
			return cboNivel;
		}


		public void setCboNivel(List cboNivel) {
			this.cboNivel = cboNivel;
		}


		public String getValAnter() {
			return valAnter;
		}


		public void setValAnter(String valAnter) {
			this.valAnter = valAnter;
		}


		public String getValAntAnt() {
			return valAntAnt;
		}


		public void setValAntAnt(String valAntAnt) {
			this.valAntAnt = valAntAnt;
		}


        public List getComboFormatosArchivos() {
            return comboFormatosArchivos;
        }

        public void setComboFormatosArchivos(List comboFormatosArchivos) {
            this.comboFormatosArchivos = comboFormatosArchivos;
        }


		public String getCdusuari() {
			return cdusuari;
		}


		public void setCdusuari(String cdusuari) {
			this.cdusuari = cdusuari;
		}
		

		public List getComboAlgoritmos() {
			return comboAlgoritmos;
		}


		public void setComboAlgoritmos(List comboAlgoritmos) {
			this.comboAlgoritmos = comboAlgoritmos;
		}


		public CombosManager2 getCombosManager2() {
			return combosManager2;
		}


		public String getSwTariFi() {
			return swTariFi;
		}


		public void setSwTariFi(String swTariFi) {
			this.swTariFi = swTariFi;
		}


		public List getComboTipoFax() {
			return comboTipoFax;
		}


		public void setComboTipoFax(List comboTipoFax) {
			this.comboTipoFax = comboTipoFax;
		}


		public List getComboUsuarioResponsable() {
			return comboUsuarioResponsable;
		}


		public void setComboUsuarioResponsable(List comboUsuarioResponsable) {
			this.comboUsuarioResponsable = comboUsuarioResponsable;
		}


		public String getNmConfig() {
			return nmConfig;
		}


		public void setNmConfig(String nmConfig) {
			this.nmConfig = nmConfig;
		}


		public List getComboCargaArchivos() {
			return comboCargaArchivos;
		}


		public void setComboCargaArchivos(List comboCargaArchivos) {
			this.comboCargaArchivos = comboCargaArchivos;
		}


		public String getCdmatriz() {
			return cdmatriz;
		}


		public void setCdmatriz(String cdmatriz) {
			this.cdmatriz = cdmatriz;
		}


		public String getCdmodulo() {
			return cdmodulo;
		}


		public void setCdmodulo(String cdmodulo) {
			this.cdmodulo = cdmodulo;
		}


		public String getCdnivatn() {
			return cdnivatn;
		}


		public void setCdnivatn(String cdnivatn) {
			this.cdnivatn = cdnivatn;
		}


		public List getComboTiposArchivos() {
			return comboTiposArchivos;
		}


		public void setComboTiposArchivos(List comboTiposArchivos) {
			this.comboTiposArchivos = comboTiposArchivos;
		}


		public List getComboTabla() {
			return comboTabla;
		}


		public void setComboTabla(List comboTabla) {
			this.comboTabla = comboTabla;
		}


		public List getComboModuloEnc() {
			return comboModuloEnc;
		}


		public void setComboModuloEnc(List comboModuloEnc) {
			this.comboModuloEnc = comboModuloEnc;
		}


		public List getComboCampanhaEnc() {
			return comboCampanhaEnc;
		}


		public void setComboCampanhaEnc(List comboCampanhaEnc) {
			this.comboCampanhaEnc = comboCampanhaEnc;
		}


		public List getComboTemaEnc() {
			return comboTemaEnc;
		}


		public void setComboTemaEnc(List comboTemaEnc) {
			this.comboTemaEnc = comboTemaEnc;
		}


		public String getCdCampan() {
			return cdCampan;
		}


		public void setCdCampan(String cdCampan) {
			this.cdCampan = cdCampan;
		}


		public List getComboEncuestaEnc() {
			return comboEncuestaEnc;
		}


		public void setComboEncuestaEnc(List comboEncuestaEnc) {
			this.comboEncuestaEnc = comboEncuestaEnc;
		}


		public String getCdProceso() {
			return cdProceso;
		}


		public void setCdProceso(String cdProceso) {
			this.cdProceso = cdProceso;
		}


		public String getCdEncuesta() {
			return cdEncuesta;
		}


		public void setCdEncuesta(String cdEncuesta) {
			this.cdEncuesta = cdEncuesta;
		}


		public String getCdNotificacion() {
			return cdNotificacion;
		}


		public void setCdNotificacion(String cdNotificacion) {
			this.cdNotificacion = cdNotificacion;
		}


		public List getComboNotificacionRegion() {
			return comboNotificacionRegion;
		}


		public void setComboNotificacionRegion(List comboNotificacionRegion) {
			this.comboNotificacionRegion = comboNotificacionRegion;
		}
		
		
}