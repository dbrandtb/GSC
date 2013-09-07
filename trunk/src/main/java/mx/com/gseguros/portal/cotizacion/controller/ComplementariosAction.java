/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gseguros.portal.cotizacion.controller;

import java.util.HashMap;
import java.util.Map;
import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.util.ConstantesCatalogos;

/**
 *
 * @author Jair
 */
public class ComplementariosAction extends PrincipalCoreAction implements ConstantesCatalogos{
    
    private static final long serialVersionUID = -1269892388621564059L;
    private org.apache.log4j.Logger log =org.apache.log4j.Logger.getLogger(ComplementariosAction.class);
    private Item items;
    private Item fields;
    private KernelManagerSustituto kernelManager;
    private Map<String,String> panel1;
    private Map<String,String> panel2;
    private Map<String,String> parametros;
    private String cdunieco;
    private String cdramo;
    private String estado;
    private String nmpoliza;
    private boolean success=true;
    private ScreenInterceptor scrInt=new ScreenInterceptor(); 
    
    public String mostrarPantalla()
    {
    	return scrInt.intercept(this,ScreenInterceptor.PANTALLA_COMPLEMENTARIOS_GENERAL);
    }
    
    public String mostrarPantallaGeneral()
    {
        try
        {
            //List<Tatrisit>listaTatrisit=kernelManager.obtenerTatrisit("SL");
            //GeneradorCampos gc=new GeneradorCampos();
            //gc.genera(listaTatrisit);
            //items=gc.getItems();
            //fields=gc.getFields();
            fields=new Item("fields",null,Item.ARR);
            
            fields.add(Item.crear(null,null,Item.OBJ).add(new Item("name","panel1.dsciaaseg")));
            fields.add(Item.crear(null,null,Item.OBJ).add(new Item("name","panel1.nombreagente")));
            fields.add(Item.crear(null,null,Item.OBJ).add(new Item("name","panel1.dsramo")));
            
            fields.add(Item.crear(null,null,Item.OBJ).add(new Item("name","panel2.nmpoliza")));
            fields.add(Item.crear(null,null,Item.OBJ).add(new Item("name","panel2.estado")));
            fields.add(Item.crear(null,null,Item.OBJ).add(new Item("name","panel2.fesolici")));
            fields.add(Item.crear(null,null,Item.OBJ).add(new Item("name","panel2.solici")));
            fields.add(Item.crear(null,null,Item.OBJ).add(new Item("name","panel2.feefec")));
            fields.add(Item.crear(null,null,Item.OBJ).add(new Item("name","panel2.ferenova")));
            fields.add(Item.crear(null,null,Item.OBJ).add(new Item("name","panel2.cdtipopol")));
            fields.add(Item.crear(null,null,Item.OBJ).add(new Item("name","panel2.cdperpag")));
        }
        catch(Exception ex)
        {
            log.error("error al obtener los campos dinamicos",ex);
            items=null;
            fields=null;
        }
        return SUCCESS;
    }
    
    /*
     * json
     * @out success
     */
    public String cargar()
    {
        panel1=new HashMap<String, String>(0);
        panel2=new HashMap<String, String>(0);
        //////////////////
        ////// duro //////
        /*//////////////*/
        panel1.put("dsciaaseg","#General de seguros");
        panel1.put("dsramo","#Salud Vital");
        panel1.put("nombreagente","#Alvaro");
        panel2.put("nmpoliza",nmpoliza);
        panel2.put("estado","#W");
        panel2.put("fesolici","06/09/1969");
        panel2.put("solici","696969");
        panel2.put("feefec","06/09/1969");
        panel2.put("ferenova","06/09/1969");
        panel2.put("cdtipopol","#R");
        panel2.put("cdperpag","#12");
        /*//////////////*/
        ////// duro //////
        //////////////////
        success=true;
        return SUCCESS;
    }
    
    public String guardar()
    {
        log.debug(panel1);
        log.debug(panel2);
        log.debug(parametros);
        success=true;
        return SUCCESS;
    }

    public Item getItems() {
        return items;
    }

    public void setItems(Item items) {
        this.items = items;
    }

    public void setKernelManager(KernelManagerSustituto kernelManager) {
        this.kernelManager = kernelManager;
    }

    public Map<String, String> getPanel1() {
        return panel1;
    }

    public void setPanel1(Map<String, String> panel1) {
        this.panel1 = panel1;
    }

    public Map<String, String> getPanel2() {
        return panel2;
    }

    public void setPanel2(Map<String, String> panel2) {
        this.panel2 = panel2;
    }

    public Map<String, String> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String, String> parametros) {
        this.parametros = parametros;
    }

    public Item getFields() {
        return fields;
    }

    public void setFields(Item fields) {
        this.fields = fields;
    }

    public String getCdunieco() {
        return cdunieco;
    }

    public void setCdunieco(String cdunieco) {
        this.cdunieco = cdunieco;
    }

    public String getCdramo() {
        return cdramo;
    }

    public void setCdramo(String cdramo) {
        this.cdramo = cdramo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNmpoliza() {
        return nmpoliza;
    }

    public void setNmpoliza(String nmpoliza) {
        this.nmpoliza = nmpoliza;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCON_CAT_POL_ESTADO() {
        return CON_CAT_POL_ESTADO;
    }

    public String getCON_CAT_POL_TIPO_POLIZA() {
        return CON_CAT_POL_TIPO_POLIZA;
    }

    public String getCON_CAT_POL_TIPO_PAGO() {
        return CON_CAT_POL_TIPO_PAGO;
    }
    
}