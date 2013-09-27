/**
 * 
 */
package mx.com.aon.configurador.pantallas.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.configurador.pantallas.model.BackBoneResultVO;
import mx.com.aon.configurador.pantallas.model.MasterVO;
import mx.com.aon.configurador.pantallas.model.PantallaVO;
import mx.com.aon.configurador.pantallas.model.RamaPantallaVO;
import mx.com.aon.configurador.pantallas.model.controls.ExtElement;
import mx.com.aon.configurador.pantallas.util.UtilTransformer;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.model.BaseObjectVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Clase Action para el control y visualizacion de datos de la seccion de Datos de pantalla
 * 
 * @author aurora.lozada
 * 
 */
public class DatosPantallaAction extends PrincipalConfPantallaAction {

    /**
     * 
     */
    private static final long serialVersionUID = -7505782563809197769L;


    /**
     * 
     */
    public static final String CD_CONJUNTO = "cd_conjunto";

    /**
     * 
     */
    public static final String ID_PANTALLA = "idPantalla";

    /**
     * 
     */
    public static final String ID = "id";

    
    /**
     * 
     */
    private List<MasterVO> tipoPantallaList = new ArrayList<MasterVO>();

    /**
     * 
     */
    private List<BaseObjectVO> navegacionPantallaList = new ArrayList<BaseObjectVO>();

    
    /**
     * 
     */
    private List<PantallaVO> registroPantallaList = new ArrayList<PantallaVO>();

    /**
     * 
     */
    private List<RamaPantallaVO> treePantallas = new ArrayList<RamaPantallaVO>();
    
    
//    /**
//     * 
//     */
//    private List<RamaMaster> treeMaster = new ArrayList<RamaMaster>();
//
//    /**
//     * 
//     * @return
//     */
//    public List<RamaMaster> getTreeMaster() {
//        return treeMaster;
//    }
//
//    /**
//     * 
//     * @param treeMaster
//     */
//    public void setTreeMaster(List<RamaMaster> treeMaster) {
//        this.treeMaster = treeMaster;
//    }

    /**
     * 
     */
    private String idP;

    /**
     * 
     */
    private String cdConjunto;

    /**
     * 
     */
    private String cdProceso;

    /**
     * 
     */
    private String cdProducto;

    /**
     * 
     */
    private String cdPantalla;

    /**
     * 
     */
    private String nombrePantalla;

    /**
     * 
     */
    private String descripcionPantalla;

    /**
     * Almacena el codigo generado por el Editor en formato JSON
     */
    private String dsArchivo;

    /**
     * 
     */
    private String dsArchivoSec;

    // /COMBOS
    private String tipoPantalla;

    /**
     * 
     */
    private String htipoPantalla;

    /**
     * 
     */
    private String cdTipoMaster;
    
    
    /**
     * 
     */
    private String tipoMaster;

    /**
     * 
     */
    private String dsCampos;

    /**
     * 
     */
    private String dsLabels;

    /**
     * 
     */
    private String cdRol;

    /**
     * 
     */
    private boolean success;

    /**
     * 
     */
    private JSONArray atributosList = new JSONArray();
    

    /**
     * 
     */
    private UtilTransformer masterTransformer;
    
    
    private String claveSituacion;
    
    
    private List<JSONObject> jsonFieldsList;
    
    private List<ExtElement> extList;


    public List<JSONObject> getJsonFieldsList() {
        return jsonFieldsList;
    }

    public void setJsonFieldsList(List<JSONObject> jsonFieldsList) {
        this.jsonFieldsList = jsonFieldsList;
    }

    /**
     * @param masterTransformer the masterTransformer to set
     */
    public void setMasterTransformer(UtilTransformer masterTransformer) {
        this.masterTransformer = masterTransformer;
    }

    /**
     * 
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
    }

    /**
     * 
     */
    @Override
    public void validate() {
        
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public String execute() throws Exception {
        return SUCCESS;
    }
    
    
    
    //@SuppressWarnings("unchecked")
    public String loadMaster() throws Exception {
        
        logger.debug("#### entrando a  loadMaster   ");
        
//      Map<String,String> paramValues = new HashMap<String, String>();
        
/*        logger.debug(" cdTipoMaster  "  + cdTipoMaster);
        logger.debug(" cdProceso  "  +  cdProceso );
        logger.debug(" tipoMaster  " + tipoMaster);
        logger.debug(" cdProducto  "  +  cdProducto);
        logger.debug(" bcclaveSituacion   " +  claveSituacion);
        
        try {
            if ( StringUtils.isNotBlank(tipoMaster)  ){
                    JSONMasterVO master = configuradorManager.getJSONMaster( cdTipoMaster,  cdProceso,
                         tipoMaster,  cdProducto,  claveSituacion);
                    
                    
                    List<RamaMaster> ramaList = master.getSectionList();
                    
                    RamaMaster[] children = null;
                    RamaMaster rama = null;
        //          RamaMaster nestedRama = null;
                    List<RamaMaster> tempItems = null;
                    
        //          RamaMaster ram = null;
                    int childrenSize;
                    boolean iterar = true;
                    FieldSet fieldSet = null;
                    List<JSONObject> jsonList;
                    String agrupador = null;
                    
//                  logger.debug("### ramaList is " + ramaList.toArray().toString());
                    
                    for( RamaMaster section : ramaList  ){
                        children = section.getChildren();               
                        childrenSize = children.length;
//                      logger.debug("#### extList " + children );
        //              ramas  = (RamaMaster[])children1;
                        tempItems = new ArrayList<RamaMaster>();
                        Map<String, String> agrupadores= new HashMap<String, String>();
                        for( int index =0; index < childrenSize; index++ ){
                            rama = children[index];
                            if( StringUtils.isBlank(rama.getAgrupador()) ){
                                tempItems.add(rama);
                            }else{
                            	
                                agrupador = rama.getAgrupador();
                                if(agrupadores.containsKey(agrupador.trim())){
                                	//tempItems.add(rama);
                                	continue;
                                } else agrupadores.put(agrupador.trim(), rama.getText());
                                
                                if(StringUtils.isNotBlank(rama.getText()))logger.debug("Para la rama: "+rama.getText()+ " Con el agrupador: "+ agrupador);
                                fieldSet = new FieldSet();
                                jsonList = new ArrayList<JSONObject>();
                                
                                String tmp= "[";
                                for( int j = 0 ; j < childrenSize ; j++ ){
                                    if(agrupador.equals(children[j].getAgrupador())){
                                    	jsonList.add(children[j].getItem());
                                    	tmp+=children[j].getText()+",";
                                    }
                                    
                                }
                                
                                rama.setText(tmp.substring(0,tmp.length()-1)+"]");
                                
                                fieldSet.setItems(jsonList.toArray());
                                fieldSet.setAutoHeight(true);
                                fieldSet.setXtype("fieldset");
                                fieldSet.setBorder(false);
                                fieldSet.setStyle("border:none; margin:0; padding:0;");
                                rama.setItem( JSONObject.fromObject(fieldSet.toString())  );
                                tempItems.add(rama);
                            }
                            
        //                  logger.debug("obj is " + obj.getClass() );
        //                  logger.debug("#### iteration ext " + obj);                  
        //                  logger.debug("####r "  + obj.getExtControl() );
                            
        //                  obj.setItem(  JSONObject.fromObject(obj.getExtControl().toString())  );
                            
        //                  logger.debug("obj is " + obj.getItem() );
                            
                        } 
                        
                        children = tempItems.toArray(new RamaMaster[tempItems.size()]);
                        section.setChildren(children);
                    }
                    
                    
                    treeMaster = master.getSectionList();
//                    logger.debug("El valor del treemaster es : " +treeMaster);
                
                }else{
                    logger.debug("Entrando a null...." );
                    treeMaster = new ArrayList<RamaMaster>();
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
        }*/
        success = true;
        return SUCCESS;
        
//        paramValues.put("master", cdTipoMaster);
//        paramValues.put("proceso", cdProceso);
//        paramValues.put("tipo", tipoMaster);
//        paramValues.put("producto", cdProducto);
//        paramValues.put("cdTipSit", claveSituacion);
//        
//        masterWrapper = configuradorManager.getAtributosPropiedades( paramValues );
//        
//        session.put( "elementos_proceso_master", masterWrapper);      
//      masterWrapper = configuradorManager.getAtributosPropiedades( paramValues );//        
//        List<SectionVO> sectionList = masterWrapper.getSectionList();        
//        jsonFieldsList = new ArrayList<JSONObject>();//        
//        for(SectionVO section : sectionList){
//          jsonFieldsList.addAll( section.getJsonFields()  );
//        }//        
//        for( JSONObject json : jsonFieldsList ){
//          logger.debug(" #### json is " +  json);
//        }
////        
//      List<String> tmp = new ArrayList<String>();
////        //['Test','Sexo test','test de combo sexo',{"displayField":"label","editable":false,
//      tmp.add("'Test'");
//      tmp.add("'sexo test'");
//      tmp.add("'test de combo'");
//      tmp.add("{ \"displayField\" :\"dato de prueba \",  \"xtype\": \"textfield\"  }");
//      
//      
//      
//      stringList = new ArrayList<List<String>>(); 
//      stringList.add(tmp);
        
        
        
        //{"displayField":"label","editable":false,"emptyText":
        //"Seleccione...","fieldLabel":"SEXO","forceSelection":true,"id":"sexo|SEX","labelSeparator":"","listWidth":200,
        //"mode":"local","name":"parameters.sexo","selectOnFocus":true,"triggerAction":"all","typeAhead":true,"valueField":"value","width":200,"xtype":"combo"}
        
        
//      ComboControl c = new ComboControl();
//      c.setFieldLabel("test");
//      c.setListWidth(200);
//      c.setMode("local");
//      c.setName("parameters.sexo");
//      c.setXtype("combo");
//      
//      JSONObject obj = new JSONObject();
//      obj.put("category", "Category Test");
//      obj.put("name", "Test Name");
//      obj.put("description", "Description Test name");
//      
//      JSONObject combo = JSONObject.fromObject(c.toString());     
//      obj.put("config",  combo  );        
//      jsonFieldsList = new ArrayList<JSONObject>();       
//      jsonFieldsList.add(obj);
        
        
        
        //aqui nueva version...
        
//      treeMaster = new ArrayList<RamaMasterVO>();
        
//      RamaMasterVO master = new RamaMasterVO();
//      master.setId("B5");
////        master.setCls("leaf");
//      master.setLeaf(false);
////        master.setIconCls("");
////        master.setIcon("");
//      master.setText("Grupo b5");     
        
        
//          RamaMasterVO rama = null;
//          
//            rama = new RamaMasterVO();
//            rama.setId("1");
//            rama.setCls("leaf");
//            rama.setLeaf(true);
//            rama.setIconCls("");
//            rama.setIcon("");
//            rama.setText("combo value");
//            rama.setItem(combo);
//            rama.setOtro("Aridnere");
            
            
//            RamaMasterVO rama2 = null;
//
//            rama2 = new RamaMasterVO();
//            rama2.setId("2");
//            rama2.setCls("leaf");
//            rama2.setLeaf(true);
//            rama2.setIconCls("");
//            rama2.setIcon("");
//            rama2.setText("combo value 2");
            
//            ComboControl c2 = new ComboControl();
//          c2.setFieldLabel("test");
//          c2.setListWidth(200);
//          c2.setMode("local");
//          c2.setName("parameters.sexo");
//          c2.setXtype("combo");
            
//          JSONObject obj2 = new JSONObject();
//          obj2.put("category", "Category Test");
//          obj2.put("name", "Test Name");
//          obj2.put("description", "Description Test name");
            
//          JSONObject combo2 = JSONObject.fromObject(c.toString());
//            rama2.setItem(combo2);
//            rama2.setOtro("Pozolito");
            
//            RamaMasterVO rama3 = null;
//            rama3 = new RamaMasterVO();
//            rama3.setId("3");
//            rama3.setCls("leaf");
//            rama3.setLeaf(true);
//            rama3.setIconCls("");
//            rama3.setIcon("");
//            rama3.setText("Combos anidados");           
//            
//            FieldSet fieldSet = new FieldSet();
//            //fieldSet.setXtype("fieldset");
//            List<RamaMasterVO> grupo1 = new ArrayList<RamaMasterVO>();
//            grupo1.add(rama);
//            grupo1.add(rama2);            
//            List<JSONObject> jsonList = new ArrayList<JSONObject>();
//            jsonList.add(combo);
//            jsonList.add(combo2);
//            fieldSet.setItems(jsonList.toArray());
//            fieldSet.setAutoHeight(true);            
//            JSONObject json3 = JSONObject.fromObject(fieldSet.toString());            
//            logger.debug("FieldSet Generado es " + json3);            
//            rama3.setItem(json3);
//            grupo1.add(rama3);            
//            master.setChildren(grupo1.toArray());
            
            
//            treeMaster.add(master);

        
        
    }
    
    

    /**
     * Metodo que obtiene el registro una pantalla
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String obtenerRegistroPantalla() throws Exception {
        boolean isDebug = logger.isDebugEnabled();        
        
        try {
            session.put( ID_PANTALLA, this.idP );
            this.cdConjunto = (String) session.get( ID );
            
            PantallaVO pantalla = new PantallaVO();
    
            if (isDebug) {
                logger.debug("#######Enterintg into method OBTENER_REGISTRO_PANTALLA...");
                logger.debug("&&&&---ID pantalla recibido" + idP);
                logger.debug("cdConjunto-----" + cdConjunto);
            }        
    
            if ( StringUtils.isBlank(this.idP) || StringUtils.isBlank(this.cdConjunto) || "undefined".equals(this.idP) ) {
    
                logger.debug("Entrando sin id pantalla en la Opcion ----- cargar registro pantalla....");
                pantalla.setCdPantalla("");
                pantalla.setCdConjunto("");
                pantalla.setCdMaster("");
                pantalla.setDescripcion("");
                pantalla.setNombrePantalla("");
                pantalla.setDsArchivo("");
                pantalla.setTipoMaster("");
    
                registroPantallaList.add(pantalla);
            } else {
                logger.debug("Entrando a la Opcion----- Cargar registro de pantalla....");
                //TODO: CARGAR A MEMORIA MASTERS
                
                pantalla = super.configuradorManager.getPantalla( this.cdConjunto, this.idP );
    
                registroPantallaList.add(pantalla);
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
     * Metodo que obtiene el listado de los tipos de pantallas master.
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String obtenerMasterPantallas() throws Exception {
        boolean isDebug = logger.isDebugEnabled();

        try {
            if (isDebug) {
                logger.debug("#######Enterintg into method obtener Masters Pantallas...");
            }
            logger.debug("cdProceso............-----" + cdProceso);
    
            if (StringUtils.isBlank(cdProceso) || cdProceso.equals("undefined")) {
                logger.debug("Entrando sin id del proceso en la Opcion ----- obtener tipo pantallas master....");
                logger.debug("cdProceso-----" + cdProceso);
                tipoPantallaList = new ArrayList<MasterVO>();
                success = false;
            } else {
                logger.debug("Entrando a la Opcion----- obtener tipo pantallas master....");
                tipoPantallaList = (List<MasterVO>) configuradorManager.getMasters(cdProceso);
                success = true;
            }
    
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
     * Metodo que guarda o actualiza una pantalla.
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String salvar() throws Exception {
        boolean isDebug = logger.isDebugEnabled();
        
        try {
                if (isDebug) {
                    logger.debug("#######Enterintg into method SALVAR PANTALLA...");
                }
                cdConjunto = (String) session.get(ID);
                
                if (StringUtils.isBlank(cdPantalla) && StringUtils.isBlank(htipoPantalla)) {
                    cdPantalla = (String) session.get(ID_PANTALLA);     
                }
                
                if (StringUtils.isNotBlank(htipoPantalla)) {
                    cdTipoMaster = htipoPantalla;
                }
                
                if (isDebug) {
                    logger.debug("id del conjunto-----" + cdConjunto);
                    logger.debug("id del proceso ---" + cdProceso);
                    logger.debug("id del producto ---" + cdProducto);
                    logger.debug("id de la pantalla ---" + cdPantalla);
                    logger.debug("htipoPantalla ---" + htipoPantalla);
                    logger.debug("nombrePantalla ---" + nombrePantalla);
                    logger.debug("descripcionPantalla ---" + descripcionPantalla);
                    logger.debug("cdTipoMaster ---" + cdTipoMaster);
                    logger.debug("tipoMaster ---" + tipoMaster);
                    
                    logger.debug("#### claveSituacion ---" + claveSituacion);
                    
                    logger.debug("dsArchivo ---" + dsArchivo);
                    logger.debug("dsArchivo longitud ---" + dsArchivo.length());
                    logger.debug("dsArchivo longitud ---" + dsArchivo.charAt(dsArchivo.length() - 1));
                    
                    logger.debug("dsCampos ---" + dsCampos);
                    logger.debug("dsLabels ---" + dsLabels);
                    
                    
                }
                
                //
                logger.debug("#############################################################");
                logger.debug("#############################################################");
                
        //        masterWrapper = configuradorManager.getAtributosPropiedades( "2", "1", "3", "777");
                
                logger.debug("#############################################################");
                
                //masterWrapper = configuradorManager.getAtributosPropiedades( cdTipoMaster, cdProceso, tipoMaster , cdProducto );
                Map<String,String> paramValues = new HashMap<String, String>();
                
                paramValues.put("master", cdTipoMaster);
                paramValues.put("proceso", cdProceso);
                paramValues.put("tipo", tipoMaster);
                paramValues.put("producto", cdProducto);
                paramValues.put("cdTipSit", claveSituacion);
                
                
        //        masterWrapper = configuradorManager.getAtributosPropiedades( paramValues );
                
                
        //        session.put("elementos_proceso_master", masterWrapper );
                // session.put("elementos_proceso_master", wrapper);        
        
                
                PantallaVO pantallaSalvar = new PantallaVO();
                pantallaSalvar.setCdConjunto(cdConjunto);
                pantallaSalvar.setNombrePantalla(nombrePantalla);
                pantallaSalvar.setDescripcion(descripcionPantalla);
        
                pantallaSalvar.setDsArchivo(dsArchivo);
        
                pantallaSalvar.setDsCampos(dsCampos);
                pantallaSalvar.setDsLabels(dsLabels);
                pantallaSalvar.setCdRol(cdRol);
                
                Map<String,Object> parameters = new HashMap<String, Object>();
                
                parameters.put("cdMaster", cdTipoMaster);
                parameters.put("cdProceso", cdProceso);
                parameters.put("cdTipo", tipoMaster);
                parameters.put("nombrePantalla", nombrePantalla);
                parameters.put("cdProducto", cdProducto);
                parameters.put("tipoBloque", "4");
                parameters.put("cdTipSit", claveSituacion);
                
                dsArchivoSec = this.masterTransformer.transform( dsArchivo, parameters, false);
                
                logger.debug("##### dsArchivo inicial es " + dsArchivo );
                logger.debug("##### dsArchivoSec obtenido es " + dsArchivoSec );
                
                pantallaSalvar.setDsArchivoSec(dsArchivoSec);
                
        
                if (StringUtils.isNotBlank(cdPantalla)) {
                    logger.debug("Entrando a la Opcion----- actualizar pantalla....");
        
                    pantallaSalvar.setCdPantalla(cdPantalla);
                    pantallaSalvar.setCdMaster(null);
        
                    // Metodo que salva la pantalla
                    BackBoneResultVO backBoneResultVO = configuradorManagerJdbcTemplate.guardaPantalla(pantallaSalvar);
                    cdPantalla = backBoneResultVO.getOutParam();
                    addActionMessage(backBoneResultVO.getMsgText());
                    logger.debug("CD_PANTALLA despues de actualizar ---" + cdPantalla);
        
                    session.put(ID_PANTALLA, cdPantalla);
        
                } else {
                    logger.debug("Entrando a la Opcion----- guardar nueva pantalla....");
                    pantallaSalvar.setCdPantalla(null);
                    pantallaSalvar.setCdMaster(htipoPantalla);
                    
                                
                    // Metodo que salva la pantalla
                    BackBoneResultVO backBoneResultVO = configuradorManagerJdbcTemplate.guardaPantalla(pantallaSalvar);
                    cdPantalla = backBoneResultVO.getOutParam();
                    addActionMessage(backBoneResultVO.getMsgText());
                    session.put(ID_PANTALLA, cdPantalla);
                    
                    logger.debug("CD_PANTALLA despues de guardar una nueva ---" + cdPantalla);
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
     * Metodo que limpia elementos de la pantalla en sesi�n de la aplicaci�n.
     * 
     * @return Cadena INPUT
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String limpiar() throws Exception {
        boolean isDebug = logger.isDebugEnabled();
        if (isDebug) {
            logger.debug("#######Enterintg into method LIMPIAR SESION DATOS PANTALLA...");
        }
        session.put(ID_PANTALLA, "");
        this.cdPantalla = "";
        this.descripcionPantalla = "";
        this.nombrePantalla = "";
        this.dsArchivo = "";
        this.cdProceso = "";
        return INPUT;
    }

    /**
     * Metodo que elimina una pantalla.
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    public String eliminar() throws Exception {
        boolean isDebug = logger.isDebugEnabled();

        try {
            if (isDebug) {
                logger.debug("#######Enterintg into method ELIMINAR PANTALLA...");
            }
            this.cdPantalla = (String) session.get(ID_PANTALLA);
    
            this.cdConjunto = (String) session.get(ID);
    
            logger.debug("cdConjunto de la pantalla a eliminar-----" + cdConjunto);
            logger.debug("id de la pantalla a eliminar-----" + cdPantalla);
    
            if (StringUtils.isNotBlank(cdPantalla) && StringUtils.isNotBlank(cdConjunto)) {
                String msg = configuradorManager.eliminarPantalla(cdConjunto, cdPantalla);
                success = true;
                addActionMessage(msg);
            } else {
                logger.debug("Sin id de la pantalla a eliminar-----en ELIMINAR PANTALLA");
                success = false;
                addActionError("No hay pantalla a eliminar");
            }
            return SUCCESS;
        } catch (ApplicationException ae) {
            success = false;
            addActionError(ae.getMessage());
            return SUCCESS;
        }
    }

    /**
     * Metodo que obtiene el listado de pantallas conforme al conjunto activo.
     * 
     * @return Cadena SUCCESS
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String obtenerTreeNavegacionPantallas() throws Exception {
        boolean isDebug = logger.isDebugEnabled();

        try {
            if (isDebug) {
                logger.debug("#######Enterintg into method OBTENER_TREE_NAVEGACION_PANTALLAS...");
            }
    
            this.cdConjunto = (String) session.get(ID);
            logger.debug("#ID e----" + this.cdConjunto);
            if (StringUtils.isBlank( this.cdConjunto) ) {
                success = false;
            } else {
    
                navegacionPantallaList = (List<BaseObjectVO>) configuradorManager.getPantallasConjunto(cdConjunto);
    
                logger.debug("---tama�o de navegacionPantallaList..." + navegacionPantallaList.size());
    
                RamaPantallaVO rama = null;
    
                for (BaseObjectVO listaNVO : navegacionPantallaList) {
                    rama = new RamaPantallaVO();
                    rama.setId(listaNVO.getValue());
                    rama.setCls("leaf");
                    rama.setLeaf(true);
                    rama.setIconCls("");
                    rama.setIcon("");
                    rama.setText(listaNVO.getLabel());
                    treePantallas.add(rama);
                }
    
                success = true;
            }
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
     * Metodo para obtener el json de la vista previa de la pantalla en edicion del configurador de pantallas
     * @return
     * @throws Exception
     */
    public String obtienePantallaVistaPrevia() throws Exception{
    	
    	

        boolean isDebug = logger.isDebugEnabled();
        
        try {
                if (isDebug) {
                    logger.debug("#######Enterintg into method obtienePantallaVistaPrevia()...");
                }
                cdConjunto = (String) session.get(ID);
                
                if (StringUtils.isBlank(cdPantalla) && StringUtils.isBlank(htipoPantalla)) {
                    cdPantalla = (String) session.get(ID_PANTALLA);     
                }
                
                if (StringUtils.isNotBlank(htipoPantalla)) {
                    cdTipoMaster = htipoPantalla;
                }
                
                if (isDebug) {
                    logger.debug("id del conjunto-----" + cdConjunto);
                    logger.debug("id del proceso ---" + cdProceso);
                    logger.debug("id del producto ---" + cdProducto);
                    logger.debug("id de la pantalla ---" + cdPantalla);
                    logger.debug("htipoPantalla ---" + htipoPantalla);
                    logger.debug("nombrePantalla ---" + nombrePantalla);
                    logger.debug("descripcionPantalla ---" + descripcionPantalla);
                    logger.debug("cdTipoMaster ---" + cdTipoMaster);
                    logger.debug("tipoMaster ---" + tipoMaster);
                    
                    logger.debug("#### claveSituacion ---" + claveSituacion);
                    
                    logger.debug("dsArchivo ---" + dsArchivo);
                    logger.debug("dsArchivo longitud ---" + dsArchivo.length());
                    logger.debug("dsArchivo longitud ---" + dsArchivo.charAt(dsArchivo.length() - 1));
                    
                    logger.debug("dsCampos ---" + dsCampos);
                    logger.debug("dsLabels ---" + dsLabels);
 
                }
              
                
                Map<String,Object> parameters = new HashMap<String, Object>();
                
                parameters.put("cdMaster", cdTipoMaster);
                parameters.put("cdProceso", cdProceso);
                parameters.put("cdTipo", tipoMaster);
                parameters.put("nombrePantalla", nombrePantalla);
                parameters.put("cdProducto", cdProducto);
                parameters.put("tipoBloque", "4");
                
                dsArchivoSec = this.masterTransformer.transform( dsArchivo, parameters, true);
                
        
                logger.debug(" HHH EN EL METODO obtienePantallaVistaPrevia DEL VISTA PREVIA ACTION: "+ dsArchivoSec );
                session.put("PANTALLA_VISTA_PREVIA", StringEscapeUtils.unescapeHtml(dsArchivoSec));
                success = true;
                return SUCCESS;
                
        } catch (Exception e) {
            success = false;
            addActionError(e.getMessage());
            return SUCCESS;
        }
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
     * @return the nombrePantalla
     */
    public String getNombrePantalla() {
        return nombrePantalla;
    }

    /**
     * @param nombrePantalla the nombrePantalla to set
     */
    public void setNombrePantalla(String nombrePantalla) {
        this.nombrePantalla = nombrePantalla;
    }

    /**
     * @return the descripcionPantalla
     */
    public String getDescripcionPantalla() {
        return descripcionPantalla;
    }

    /**
     * @param descripcionPantalla the descripcionPantalla to set
     */
    public void setDescripcionPantalla(String descripcionPantalla) {
        this.descripcionPantalla = descripcionPantalla;
    }

    /**
     * @return the tipoPantalla
     */
    public String getTipoPantalla() {
        return tipoPantalla;
    }

    /**
     * @param tipoPantalla the tipoPantalla to set
     */
    public void setTipoPantalla(String tipoPantalla) {
        this.tipoPantalla = tipoPantalla;
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
     * @return the htipoPantalla
     */
    public String getHtipoPantalla() {
        return htipoPantalla;
    }

    /**
     * @param htipoPantalla the htipoPantalla to set
     */
    public void setHtipoPantalla(String htipoPantalla) {
        this.htipoPantalla = htipoPantalla;
    }

    /**
     * @return the cdPantalla
     */
    public String getCdPantalla() {
        return cdPantalla;
    }

    /**
     * @param cdPantalla the cdPantalla to set
     */
    public void setCdPantalla(String cdPantalla) {
        this.cdPantalla = cdPantalla;
    }

    /**
     * @return the dsArchivo
     */
    public String getDsArchivo() {
        return dsArchivo;
    }

    /**
     * @param dsArchivo the dsArchivo to set
     */
    public void setDsArchivo(String dsArchivo) {
        this.dsArchivo = dsArchivo;
    }

    /**
     * @return the idP
     */
    public String getIdP() {
        return idP;
    }

    /**
     * @param idP the idP to set
     */
    public void setIdP(String idP) {
        this.idP = idP;
    }

    /**
     * @return the registroPantallaList
     */
    public List<PantallaVO> getRegistroPantallaList() {
        return registroPantallaList;
    }

    /**
     * @param registroPantallaList the registroPantallaList to set
     */
    public void setRegistroPantallaList(List<PantallaVO> registroPantallaList) {
        this.registroPantallaList = registroPantallaList;
    }

    /**
     * @return the treePantallas
     */
    public List<RamaPantallaVO> getTreePantallas() {
        return treePantallas;
    }

    /**
     * @param treePantallas the treePantallas to set
     */
    public void setTreePantallas(List<RamaPantallaVO> treePantallas) {
        this.treePantallas = treePantallas;
    }

    /**
     * @return the tipoPantallaList
     */
    public List<MasterVO> getTipoPantallaList() {
        return tipoPantallaList;
    }

    /**
     * @param tipoPantallaList the tipoPantallaList to set
     */
    public void setTipoPantallaList(List<MasterVO> tipoPantallaList) {
        this.tipoPantallaList = tipoPantallaList;
    }

    /**
     * @return the navegacionPantallaList
     */
    public List<BaseObjectVO> getNavegacionPantallaList() {
        return navegacionPantallaList;
    }

    /**
     * @param navegacionPantallaList the navegacionPantallaList to set
     */
    public void setNavegacionPantallaList(List<BaseObjectVO> navegacionPantallaList) {
        this.navegacionPantallaList = navegacionPantallaList;
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
     * @return the atributosList
     */
    public JSONArray getAtributosList() {
        return atributosList;
    }

    /**
     * @param atributosList the atributosList to set
     */
    public void setAtributosList(JSONArray atributosList) {
        this.atributosList = atributosList;
    }

    /**
     * @return the dsArchivoSec
     */
    public String getDsArchivoSec() {
        return dsArchivoSec;
    }

    /**
     * @param dsArchivoSec the dsArchivoSec to set
     */
    public void setDsArchivoSec(String dsArchivoSec) {
        this.dsArchivoSec = dsArchivoSec;
    }

    /**
     * @return the cdTipoMaster
     */
    public String getCdTipoMaster() {
        return cdTipoMaster;
    }

    /**
     * @param cdTipoMaster the cdTipoMaster to set
     */
    public void setCdTipoMaster(String cdTipoMaster) {
        this.cdTipoMaster = cdTipoMaster;
    }

    /**
     * @return the tipoMaster
     */
    public String getTipoMaster() {
        return tipoMaster;
    }

    /**
     * @param tipoMaster the tipoMaster to set
     */
    public void setTipoMaster(String tipoMaster) {
        this.tipoMaster = tipoMaster;
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

    public String getDsCampos() {
        return dsCampos;
    }

    public void setDsCampos(String dsCampos) {
        this.dsCampos = dsCampos;
    }

    public String getDsLabels() {
        return dsLabels;
    }

    public void setDsLabels(String dsLabels) {
        this.dsLabels = dsLabels;
    }

    /**
         * @return the extList
         */
        public List<ExtElement> getExtList() {
            return extList;
        }

    /**
     * @param extList the extList to set
     */
    public void setExtList(List<ExtElement> extList) {
        this.extList = extList;
    }

	public String getCdRol() {
		return cdRol;
	}

	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}
}
