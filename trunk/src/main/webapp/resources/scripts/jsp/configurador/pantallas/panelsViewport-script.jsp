<%@ include file="/taglibs.jsp"%>
/*************************************************************
** Paneles del viewport(3):parametros, master, area de trabajo
**************************************************************/
   var paramEntradaPanel = new Ext.Panel({
        region: 'north',
        title: PANEL_1_TITLE,
        iconCls:'logo',
        split:true,
        height: 350,
        id:'up',
        bodyStyle:'padding:5px',
        collapsible: true,
        layoutConfig:{
             animate:true
        }
    });
 
  /*
    var masterPanel = new Ext.Panel({
        region: 'west',
        title: 'MENU',
        split:true,
        width:200,
        heigth:'500',
        minSize:'200',
        maxSize:'200',
        margins:'0 5 0 5',
        bodyStyle:'padding:5px',
        collapsible: true,
        layout:'accordion',
        layoutConfig:{
             animate:true
        }     
    });
 */
 
/*************************************************************
** Paneles del masterPanel (5)
**************************************************************/
   /******Paneles del masterPanel ********/
   
   /*
   var atributosMasterPanel = new Ext.Panel({
             border:false,
             title: 'Atributos del Master',
             autoScroll:true,
             items:[{
                    xtype: 'treepanel',
                    width: 190,
                    border : false, 
                    autoScroll: true,
                    split: true,
                    loader: new Ext.tree.TreeLoader({
                           dataUrl:''
                            
                            
                    }), 
                    root: new Ext.tree.AsyncTreeNode({
                          text: 'Coberturas', 
                          draggable:false, // disable root node dragging
                          id:'source'
                    }),
                   rootVisible: false
                                
             }]
            
   });

  */
   
    /*
    var elementosComunesPanel = new Ext.Panel({
              border:false,
              title: 'Elementos Comunes a las Pantallas'
    });
   */
   
    /*
   var gridPropiedadesPanel = new Ext.Panel({
             border:false,
             title: 'Grid de Propiedades'
   });
   */
   
    /*
   var arbolElementosPanel = new Ext.Panel({
             border:false,
             title: 'Arbol de Elementos'
   });
   */
   
    
   var navegacionPantallasPanel = new Ext.Panel({
             border:false,
             title: MENU_NAVEGACION_TITLE,
             autoScroll:true,
             items:[{
                    xtype: 'treepanel',
                    id:'treeNavegacion',
                    width: 190,
                    border : false, 
                    autoScroll: true,
                    split: true,
                    clearOnLoad : true,
                    lines: false,
                    loader: new Ext.tree.TreeLoader({
                           dataUrl:'<s:url value="../configuradorpantallas/obtenerTreeNavegacionPantallas.action"/>'
                            
                            
                    }), 
                    root: new Ext.tree.AsyncTreeNode({
                          text: MENU_NAVEGACION_ROOT, 
                          draggable:false, // disable root node dragging
                          id:'sourceP'
                    }),
                   rootVisible: false,
                   listeners: {
                   				//instead click
                               dblclick : function(n) {
                               
                               //Para corregir el error cuando el combo se oculta inesperadamente (se requiere que este en el foco de la segunda pestaña)
                               Ext.getCmp('panelTabsId').activate('datosPantallaPanel');
                               
                              clavePantalla = n.attributes.id;
                              
                              getRegistroPantalla(clavePantalla);
                                storeRegistroPantalla.on('load', function(){
                                	
                                 if(storeRegistroPantalla.getTotalCount()==null || storeRegistroPantalla.getTotalCount()==""){
                                 
                                 }else{
                                    var recordP = storeRegistroPantalla.getAt(0);
                                    formPanelDatos.getForm().loadRecord(recordP);
                                     cdTipoMaster.setValue(recordP.get('htipoPantalla'));
				                                     Ext.getCmp('comboRol').setValue(recordP.json.cdRol);

										/*storeComboRolXNivel.load({
											params: {pv_cdelemento_i: comboNivel.getValue()},
											callback: function () {
											}
										});*/
                                        if(recordP.get('cdPantalla')==null || recordP.get('cdPantalla')=="")
                                            {
                                                 comboTipoPantalla.enable();
                                                 formPanelDatos.getForm().reset();
                                                 Ext.getCmp('btnEliminar').disable();
                                                 Ext.getCmp('btnNPantalla').disable();
                                                 Ext.getCmp('btnVistaPrevia').disable();
                                        }else{
                                                 comboTipoPantalla.disable();
                                                 comboTipoPantalla.setHeight(25);//Para corregir el error cuando el combo se oculta inesperadamente
                                                 
                                                 Ext.getCmp('btnEliminar').enable();
                                                 Ext.getCmp('btnNPantalla').enable();
                                                 Ext.getCmp('btnVistaPrevia').enable();
                                                
                                                storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
                                                storeTipoPantalla.load({
                                                
                                                        callback : function(r,options,success) {
                                                            if (!success) {
                                                             //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                                                            	storeProducto.removeAll();
                                                            } 
                                                         //   comboTipoPantalla.setValue(recordP.get('htipoPantalla'));
                                                        }
                                                            
                                                      });
                                                      
                                               ////////////////////////////////////////////////
                                               var treeMasterTest = Ext.getCmp('treeProcesosMaster');
                                            		
                                            		var _idx = Ext.getCmp('tipoPantalla').store.find(Ext.getCmp('tipoPantalla').valueField, Ext.getCmp('tipoPantalla').getValue());
                                            		var _rec = Ext.getCmp('tipoPantalla').store.getAt(_idx);
                                                        
										treeMasterTest.loader.baseParams['cdTipoMaster'] = Ext.getCmp('cdTipoMaster').getValue();
										treeMasterTest.loader.baseParams['cdProceso'] = Ext.getCmp('cdProceso').getValue();
										treeMasterTest.loader.baseParams['tipoMaster'] = (_rec)?_rec.get('cdTipo'):Ext.getCmp('tipoMaster').getValue();
										treeMasterTest.loader.baseParams['cdProducto'] = Ext.getCmp('cdProducto').getValue();
										treeMasterTest.loader.baseParams['claveSituacion'] = Ext.getCmp('claveSituacion').getValue();
                                                        
                                               treeMasterTest.root.reload();
                                               
       
       
                                                      
                                               ///////////////////////////////////////////////       
                                       }
                                       
                                        //SI dsArchivo TRAE ELEMENTOS, LOS CARGAMOS EN EL EDITOR
                                        if(dsArchivo.getValue() != "{}"){
                                            var configuracion = null;
                                            configuracion = Ext.decode(dsArchivo.getValue());
                                            main.setConfig({items:[configuracion]});
                                        }
                                        //SINO, DEJAMOS VACIO EL EDITOR
                                        else{
                                            main.setConfig({items:[]});
                                        }
                                     }//else totalcount
                                     
                                   });                                   
                              }
                    }// end listeners
                                
             }]
             
            
   });
   
   /*
   var layoutPanel = new Ext.Panel({
        border:false,
        title: 'Elementos Layout',
        autoScroll: true
   
   });
   */
   
   /*
   masterPanel.add(elementosComunesPanel);
   masterPanel.add(gridPropiedadesPanel);
   masterPanel.add(arbolElementosPanel);         
   masterPanel.add(atributosMasterPanel);
   masterPanel.add(navegacionPantallasPanel);
     */   
   
    /*
   var areaTrabajoPanel = new Ext.Panel({
        region: 'center',
        title: 'Area de Trabajo',
        split:true,
        items:[ejemAreaTrabajo]
    });
    */

    