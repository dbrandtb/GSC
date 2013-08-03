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
						proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_CLIENTES_CORPO}),
						reader: new Ext.data.JsonReader({
								root: 'cboCliCorpoAseguradoraProducto',
								id: 'cdUniEco',
								successProperty: '@success'
							}, [
								{name: 'cdPerson', type: 'string', mapping: 'cdPerson'},
								{name: 'cdElemento', type: 'string', mapping: 'cdElemento'},
								{name: 'dsElemen', type: 'string', mapping: 'dsElemen'} 
							]),
						remoteSort: true
				});


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
				
//se define el formulario
var formPanel = new Ext.FormPanel ({
            labelWidth : 100,
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
	                            				
	                            				formPanel.findById(('cdPersonId')).setValue("");				
	                            					    dsClientes.removeAll();
	                            					    dsClientes.load({
	                            											params: {
	                            											          cdUniEco: record.get('cdUniEco'),
	                            											          cdRamo: ""
	                            											         },
	                            											failure: dsClientes.removeAll()
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
	                            			
	                            				formPanel.findById(('cdPersonId')).setValue("");				
	                            					    dsClientes.removeAll();
	                            					    dsClientes.load({
	                            											params: {
	                            											          cdUniEco: formPanel.findById(('cdUniEcoId')).getValue(),	
	                            											          cdRamo: record.get('cdRamo')
	                            											         },
	                            											failure: dsClientes.removeAll()
	                            										});						
	                            				this.collapse();
	                            				
	                            		
	                                       }
	             },

                {
                    xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{cdElemento}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientes,
                    displayField:'dsElemen',valueField:'cdElemento',hiddenName: 'cdPerson',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Cuenta/Cliente",
                     header: getLabelFromMap('cmbCtaCliente',helpMap,'Cuenta/Cliente'),
                    tooltip: getToolTipFromMap('cmbCtaCliente',helpMap,'Seleccione Cuenta/Cliente'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbCtaCliente',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbCtaCliente',helpMap,''),  
                    forceSelection: true,width: 300,emptyText:'Seleccione Cuenta/Cliente...',
                    selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdPersonId'
	                            		        
                } ,
                 {
                    xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{cdProceso}. {dsProceso}" class="x-combo-list-item">{dsProceso}</div></tpl>',
                    store: dsProcesos,
                    displayField:'dsProceso',valueField:'cdProceso',hiddenName: 'cdProceso',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Operaci&oacute;n",
                    header: getLabelFromMap('cmbOperacion',helpMap,'Operacion'),
                    tooltip: getToolTipFromMap('cmbOperacion',helpMap,'Seleccione Operacion'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbOperacion',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbOperacion',helpMap,''),  
	              	
                    forceSelection: true,width: 300,emptyText:'Seleccione Operacion...',
                    selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdProcesoId'
                },
                {
                     xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsCampan,
                    displayField:'descripcion',valueField:'codigo',hiddenName: 'cdCampan',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Campaña",
                    header: getLabelFromMap('cmbCampan',helpMap,'Campaña'),
                    tooltip: getToolTipFromMap('cmbCampan',helpMap,'Seleccione Campaña'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbCampan',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbCampan',helpMap,''),  
	              	
                    forceSelection: true,width: 300,emptyText:'Seleccione Campaña...',
                    selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdCampanId'
	                            					
                },
                {
                    xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsModulos,
                    displayField:'descripcion',valueField:'codigo',hiddenName: 'cdModulo',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Modulo",
                    header: getLabelFromMap('cmbModulo',helpMap,'Modulo'),
                    tooltip: getToolTipFromMap('cmbModulo',helpMap,'Seleccione Modulo'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbModulo',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbModulo',helpMap,''),  
	              	
                    forceSelection: true,width: 300,emptyText:'Seleccione Modulo...',
                    selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdModuloId'
                },
                {
                    xtype: 'combo', tpl: '<tpl for="."><div ext:qtip="{cdEncuesta}. {dsEncuesta}" class="x-combo-list-item">{dsEncuesta}</div></tpl>',
                    store: dsEncuestas,
                    displayField:'dsEncuesta',valueField:'cdEncuesta',hiddenName: 'cdEncuesta',
                    typeAhead: true,mode: 'local',triggerAction: 'all',fieldLabel: "Encuesta",
                    header: getLabelFromMap('cmbEncuesta',helpMap,'Encuesta'),
                    tooltip: getToolTipFromMap('cmbEncuesta',helpMap,'Seleccione Encuesta'),                   
                    hasHelpIcon:getHelpIconFromMap('cmbEncuesta',helpMap),
	              	Ayuda: getHelpTextFromMap('cmbEncuesta',helpMap,''),  
                    forceSelection: true,width: 300,emptyText:'Seleccione Encuesta...',
                    selectOnFocus:true,labelSeparator:'',allowBlank : false,id:'cdEncuaestaId'
                },
                {
                    xtype: 'datefield', fieldLabel: "Fecha Desde",
                    id: 'feDesdeId', 
		            fieldLabel: getLabelFromMap('nmTarjDesd',helpMap,'Fecha Desde'),
		            tooltip: getToolTipFromMap('nmTarjDesd',helpMap,'Fecha Desde'), 
		                             
                    hasHelpIcon:getHelpIconFromMap('nmTarjDesd',helpMap),
	              	Ayuda: getHelpTextFromMap('nmTarjDesd',helpMap,''),                				    
                    name: 'feDesde',
                    allowBlank:false
                },
                {
                    xtype: 'datefield', fieldLabel: "Fecha Hasta",
                    id: 'feHastaId', 
		            fieldLabel: getLabelFromMap('nmTarjHasta',helpMap,'Fecha Hasta'),
		            tooltip: getToolTipFromMap('nmTarjHasta',helpMap,'Fecha Hasta'),   
		            hasHelpIcon:getHelpIconFromMap('nmTarjHasta',helpMap),
	              	Ayuda: getHelpTextFromMap('nmTarjHasta',helpMap,''),                          				    
                    name: 'feHasta',
                    allowBlank:false
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
	   title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('AdminiAsigEncuestWindow', helpMap,'Administrar Asignaci&oacute;n Encuestas')+'</span>',
        width: 500,
        height:320,
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
 
dsAseguradoras.load();
dsModulos.load();
dsEncuestas.load();
dsProcesos.load();
dsCampan.load();
};

