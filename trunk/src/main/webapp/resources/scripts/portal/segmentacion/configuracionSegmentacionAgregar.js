function agregarConfiguracionEncuesta() {


	 var dsAseguradoras = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ASEGURADORAS}),
						reader: new Ext.data.JsonReader({
								root: 'aseguradoraComboBox',
								id: 'cdUniEco',
								successProperty: '@success'
							}, [
								{name: 'cdUniEco', type: 'string', mapping: 'cdUniEco'},
								{name: 'dsUniEco', type: 'string', mapping: 'dsUniEco'} 
							]),
						remoteSort: true
				});
				
	
      var dsClientes = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_CLIENTES_CORPORA}),
						reader: new Ext.data.JsonReader({
								root: 'comboClientesCorpBO',
								id: 'cdUniEco',
								successProperty: '@success'
							}, [
								{name: 'cdPerson', type: 'string', mapping: 'cdPerson'},
								{name: 'cdElemento', type: 'string', mapping: 'cdElemento'},
								{name: 'dsElemen', type: 'string', mapping: 'dsElemen'} 
							]),
						remoteSort: true
				});
				
	var dsProductos = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_PRODUCTOS_ASEG_ENCUESTA}),
						reader: new Ext.data.JsonReader({
								root: 'cboProductosAsegEncuestas',
								id: 'cdRamo',
								successProperty: '@success'
							}, [
								{name: 'cdRamo', type: 'string', mapping: 'cdRamo'},
								{name: 'dsRamo', type: 'string', mapping: 'dsRamo'}
							]),
						remoteSort: true
				});			
/*

   var dsModulos = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_MODULOS_ENCUESTAS}),
						reader: new Ext.data.JsonReader({
								root: 'comboModulosEncuestas',
								id: 'codigo',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							]),
						remoteSort: true
				});
				
	var dsEncuestas = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ENCUESTAS}),
						reader: new Ext.data.JsonReader({
								root: 'comboEncuestas',
								id: 'cdEncuesta',
								successProperty: '@success'
							}, [
								{name: 'cdEncuesta', type: 'string', mapping: 'cdEncuesta'},
								{name: 'dsEncuesta', type: 'string', mapping: 'dsEncuesta'}
							]),
						remoteSort: true
				});		
				
 var dsProcesos = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_PROCESOS}),
						reader: new Ext.data.JsonReader({
								root: 'comboProcesosCat',
								id: 'cdProceso',
								successProperty: '@success'
							}, [
								{name: 'cdProceso', type: 'string', mapping: 'cdProceso'},
								{name: 'dsProceso', type: 'string', mapping: 'dsProceso'}
							]),
						remoteSort: true
				});		
	
		var dsCampan = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_CAMPAN_ENCUESTAS}),
						reader: new Ext.data.JsonReader({
								root: 'cboCampanEncuestas',
								id: 'codigo',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							]),
						remoteSort: true
				});				
				
		
				*/
//se define el formulario
	
	
	
var nmPoliza = new Ext.form.NumberField({
        fieldLabel: getLabelFromMap('nmPolizaId',helpMap,'Fin'),
        tooltip:getToolTipFromMap('nmPolizaId',helpMap,'Fin'), 
        hasHelpIcon:getHelpIconFromMap('nmPolizaId',helpMap),
        Ayuda:getHelpTextFromMap('nmPolizaId',helpMap),
        id: 'nmPolizaId', 
        name: 'nmPoliza',
        allowBlank: true,
        width:186
        //anchor: '95%'
    });
    
    
var formPanel = new Ext.FormPanel ({
            labelWidth : 80,
            bodyStyle : 'padding:5px 5px 0',
            width : 350,
            defaults : {width : 200 },
            defaultType : 'textfield',
            labelAlign:'right',
            //store:dsTramos,
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },{
                    xtype: 'hidden',
                    name : 'swSubInc',
                    id: 'swSubInc'
                },
                
                {
                    xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{cdElemento}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientes,
                    displayField:'dsElemen',valueField:'cdElemento',hiddenName: 'cdElemento',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Cliente",
                     header: getLabelFromMap('cmbCtaCliente',helpMap,'Cliente'),
                    tooltip: getToolTipFromMap('cmbCtaCliente',helpMap,'Seleccione Cliente'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbCtaCliente',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbCtaCliente',helpMap,''),  
                    forceSelection: true,width: 300,emptyText:'Seleccione Cliente...',
                    selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdPersonId',
                     onSelect: function (record) {
	                            			
                     	                        this.setValue(record.get('cdElemento'));
	                            				
	                            				formPanel.findById(('cdRamoId')).setValue("");				
	                            					    dsAseguradoras.removeAll();
	                            					    dsAseguradoras.load({
	                            											params: {pv_cdunieco_i: record.get('cdUniEco')
	                            											        
	                            											         },
	                            											failure: dsAseguradoras.removeAll()
	                            				});
	                            				
	                            				
	                            				this.collapse();
	                            				
	                            		
	                                      
	                            				}

	                            		        
                } ,
                
                {
                   xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{cdUniEco}. {dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
                    store: dsAseguradoras,
                    displayField:'dsUniEco',valueField:'cdUniEco',hiddenName: 'cdUniEco',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Aseguradora",
                    header: getLabelFromMap('cmbAseguradora',helpMap,'Aseguradora'),
                    tooltip: getToolTipFromMap('cmbAseguradora',helpMap,'Seleccione Aseguradora'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbAseguradora',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbAseguradora',helpMap,''),  
	              	
                    forceSelection: true,width: 300,emptyText:'Seleccione Aseguradora...',
                    selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdUniEcoId',
                    onSelect: function (record) {
	                            				this.setValue(record.get('cdUniEco'));
	                            				
	                            				formPanel.findById(('cdRamoId')).setValue("");				
	                            					    dsProductos.removeAll();
	                            					    dsProductos.load({
	                            											params: {pv_cdunieco_i: record.get('cdUniEco')
	                            											        
	                            											         },
	                            											failure: dsProductos.removeAll()
	                            										});
	                            				
	                            				
	                            			this.collapse();
	                            		
	                                       }
                },
                {
                    xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{cdRamo}. {dsRamo}" class="x-combo-list-item">{dsRamo}</div></tpl>',
                    store: dsProductos,id:'cdRamoId',
                    displayField:'dsRamo',valueField:'cdRamo',hiddenName: 'cdRamo',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Producto",
                  
                    header: getLabelFromMap('cmbProducto',helpMap,'Producto'),
                    tooltip: getToolTipFromMap('cmbProducto',helpMap,'Seleccione Producto'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbProducto',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbProducto',helpMap,''),  
                    forceSelection: true,width: 300,emptyText:'Seleccione Producto...',
                    selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdRamoId',
                      onSelect: function (record) {
	                            				this.setValue(record.get('cdRamo'));
	                            				                            				
	                            				this.collapse();
	                            				
	                            		
	                                       }
	             },

                 {
                    xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{cdProceso}. {dsProceso}" class="x-combo-list-item">{dsProceso}</div></tpl>',
                  // store: dsProcesos,
                    displayField:'dsProceso',valueField:'cdProceso',hiddenName: 'cdProceso',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Oficina",
                    header: getLabelFromMap('cmbOperacion',helpMap,'Oficina'),
                    tooltip: getToolTipFromMap('cmbOperacion',helpMap,'Seleccione Oficina'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbOperacion',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbOperacion',helpMap,''),  
	              	
                    forceSelection: true,width: 300,emptyText:'Seleccione Oficina...',
                    selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdProcesoId'
                },
                {
                     xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                 //   store: dsCampan,
                    displayField:'descripcion',valueField:'codigo',hiddenName: 'cdCampan',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Area",
                    header: getLabelFromMap('cmbCampan',helpMap,'Area'),
                    tooltip: getToolTipFromMap('cmbCampan',helpMap,'Seleccione Area'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbCampan',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbCampan',helpMap,''),  
	              	
                    forceSelection: true,width: 300,emptyText:'Seleccione Area...',
                    selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdCampanId'
	                            					
                },
                {
                    xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    //store: dsModulos,
                    displayField:'descripcion',valueField:'codigo',hiddenName: 'cdModulo',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Ejecutivo",
                    header: getLabelFromMap('cmbModulo',helpMap,'Ejecutivo'),
                    tooltip: getToolTipFromMap('cmbModulo',helpMap,'Seleccione Ejecutivo'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbModulo',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbModulo',helpMap,''),  
	              	
                    forceSelection: true,width: 300,emptyText:'Seleccione Ejecutivo...',
                    selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdModuloId'
                },
                {
                    xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{cdEncuesta}. {dsEncuesta}" class="x-combo-list-item">{dsEncuesta}</div></tpl>',
                   // store: dsEncuestas,
                    displayField:'dsEncuesta',valueField:'cdEncuesta',hiddenName: 'cdEncuesta',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Gestor de Cuenta",
                    header: getLabelFromMap('cmbEncuesta',helpMap,'Gestor de Cuenta'),
                    tooltip: getToolTipFromMap('cmbEncuesta',helpMap,'Seleccione Gestor de Cuenta'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbEncuesta',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbEncuesta',helpMap,''),  
                    forceSelection: true,width: 300,emptyText:'Seleccione Gestor de Cuenta...',
                    selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdEncuaestaId'
                },
                 {
                    xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{cdEncuesta}. {dsEncuesta}" class="x-combo-list-item">{dsEncuesta}</div></tpl>',
                   // store: dsEncuestas,
                //    displayField:'dsEncuesta',valueField:'cdEncuesta',hiddenName: 'cdEncuesta',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Tipo de Cuenta",
                    header: getLabelFromMap('cmbEncuesta',helpMap,'Tipo de Cuenta'),
                    tooltip: getToolTipFromMap('cmbEncuesta',helpMap,'Seleccione Tipo de Cuenta'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbEncuesta',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbEncuesta',helpMap,''),  
                    forceSelection: true,width: 300,emptyText:'Seleccione Tipo de Cuenta...'
                  //  selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdEncuaestaId'
                },
                  {
                    xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{cdEncuesta}. {dsEncuesta}" class="x-combo-list-item">{dsEncuesta}</div></tpl>',
                   // store: dsEncuestas,
                    //displayField:'dsEncuesta',valueField:'cdEncuesta',hiddenName: 'cdEncuesta',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Segmentacion nivel 7",
                    header: getLabelFromMap('cmbEncuesta',helpMap,'Segmentacion nivel 7'),
                    tooltip: getToolTipFromMap('cmbEncuesta',helpMap,'Segmentacion nivel 7'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbEncuesta',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbEncuesta',helpMap,''),  
                    forceSelection: true,width: 300,emptyText:'Seleccione Segmentacion nivel 7...'
                  //  selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdEncuaestaId'
                },
                  {
                    xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{cdEncuesta}. {dsEncuesta}" class="x-combo-list-item">{dsEncuesta}</div></tpl>',
                   // store: dsEncuestas,
                  //  displayField:'dsEncuesta',valueField:'cdEncuesta',hiddenName: 'cdEncuesta',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Segmentacion nivel 8",
                    header: getLabelFromMap('cmbEncuesta',helpMap,'Segmentacion nivel 8'),
                    tooltip: getToolTipFromMap('cmbEncuesta',helpMap,'Segmentacion nivel 8'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbEncuesta',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbEncuesta',helpMap,''),  
                    forceSelection: true,width: 300,emptyText:'Seleccione Segmentacion nivel 8...'
                //    selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdEncuaestaId'
                }
               
           ]
        });


        
 function guardarConfiguracionEncuesta(){
    var conn = new Ext.data.Connection();
	                conn.request({
				    	url: _ACTION_GUARDAR_CONFIGURACION_ENCUESTAS,
				    	method: 'POST',
				    	params: {
				    				nmConfig:"",
				    				cdUnieco: formPanel.findById("cdUniEcoId").getValue(),
				    				cdRamo: formPanel.findById("cdRamoId").getValue(),
						    		cdElemento: formPanel.findById("cdPersonId").getValue(),
                                    cdProceso: formPanel.findById("cdProcesoId").getValue(),  
						    		cdCampan: formPanel.findById("cdCampanId").getValue(),
						    		cdModulo: formPanel.findById("cdModuloId").getValue(),
						    		cdEncuesta: formPanel.findById("cdEncuaestaId").getValue(),
						    		fedesde_i: formPanel.findById("feDesdeId").getRawValue(),
						    		fehasta_i: formPanel.findById("feHastaId").getRawValue()
						    		
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
        if (Ext.util.JSON.decode(response.responseText).success == false) {
        	
            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).actionErrors[0]);
         //  Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400062', helpMap,'No se pudo guardar los datos'));
     
         
        }else {
            // Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400068', helpMap,'Los datos se guardaron con exito'),function (){reloadGrid()});
           
           // Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), '',function(){reloadGrid()});
        	
              Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){reloadGrid();});
           
             window.close();
        }
    }
 })
} 


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
	   id: 'AdminiAsigEncuestWindow',
      //  title: 'Administrar Asignaci&oacute;n Encuesta',
	   title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('', helpMap,'Configurar Segmentacion Agregar')+'</span>',
        width: 500,
        height:450,
        minWidth: 300,
        minHeight: 100,
        modal: true,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
          //  text : 'Guardar',
           text:getLabelFromMap('btnGuardar',helpMap,'Guardar'),
           tooltip: getToolTipFromMap('btnGuardar',helpMap,'Guardar'), 
            disabled : false,
            handler : function(){               	
            	if (formPanel.form.isValid()){
                   //  if (Ext.getCmp('feDesdeId').getValue() > Ext.getCmp('feHastaId').getValue()){
            		           		
            	     if ((Ext.getCmp('feDesdeId').getValue() > Ext.getCmp('feHastaId').getValue())) {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400069', helpMap, 'La fecha desde debe ser menor o igual que la de hasta'));	                         
                    
                      }
                        else{guardarConfiguracionEncuesta();}
                    
                    
               	 }else{
                	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
       				}
            }
           },
           
           {
            //text : 'Regresar',
            
                 text:getLabelFromMap('btnREgresar',helpMap,'Regresar'),
                 tooltip: getToolTipFromMap('btnREgresar',helpMap,'Cierra la ventana'),
            handler : function(){window.close();}
            }
        ]
    	});

  window.show();
 
//
//dsModulos.load();
//dsEncuestas.load();
// dsProcesos.load();
//dsCampan.load();
  dsClientes.load();
 // dsAseguradoras.load();
  //dsProductos.load();
};

