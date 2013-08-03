// Funcion de Agregar Desglose de Polizas
function editar(record) {

   
    
    var dsAseguradora = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_ASEGURADORA_CLIENTES
            }),
            reader: new Ext.data.JsonReader({
            root: 'aseguradoraComboBox',
            id: 'cdUniEco'
            },[
           {name: 'cdUniEco', type: 'string',mapping:'cdUniEco'},
           {name: 'dsUniEco', type: 'string',mapping:'dsUniEco'}
        ])
    });
    
    
   var dsTipoRamo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_RAMOS_CLIENTE
            }),
            reader: new Ext.data.JsonReader({
            root: 'tiposRamo',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });


   var dsProductos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTES
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosAseguradoraCliente',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
    });
    
     var dsTipoAgrupacion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_AGRUPACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'tiposAgrupacion',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
    var dsEstado = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_ESTADOS
            }),
            reader: new Ext.data.JsonReader({
            root: 'estadosAgrupacion',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcion'}
        ])
    });
    
   
   //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {
            root : 'agrupacionVO',
            totalProperty: 'total',
            successProperty : 'success'
        },
         [ 
           
            {name : 'cdElemento',type : 'string',mapping : 'cdElemento'},
            {name : 'cdEstado',type : 'string',mapping : 'cdEstado'},
            {name : 'cveAgrupa',type : 'string',mapping : 'cdGrupo'},
            {name : 'cdPerson',type : 'string',mapping : 'cdPerson'},
            {name : 'cdRamo',type : 'string',mapping : 'cdRamo'},
            {name : 'cdTipo',type : 'string',mapping : 'cdTipo'},
            {name : 'cdTipram',type : 'string',mapping : 'cdTipram'},
            {name : 'cdUnieco',type : 'string',mapping : 'cdUnieco'},
            {name : 'dsAgrupa',type : 'string',mapping : 'dsAgrupa'},
            {name : 'dsElemen',type : 'string',mapping : 'dsElemen'},
            {name : 'dsEstado',type : 'string',mapping : 'dsEstado'},
            {name : 'dsRamo',type : 'string',mapping : 'dsRamo'},
            {name : 'dsTipram',type : 'string',mapping : 'dsTipram'},
            {name : 'dsUnieco',type : 'string',mapping : 'dsUnieco'},
            {name : 'nombre',type : 'string',mapping : 'nombre'}
            
          ]
        );
      

//se define el formulario
var formPanel = new Ext.FormPanel({
            labelWidth : 200,
            
            url : _ACTION_GET_AGRUPACION_POLIZAS,
        
            baseParams : {
                 cveAgrupa: record.get('cdGrupo')
             },
            frame:true,
            bodyStyle : 'padding:5px 5px 0',
            width : 350,
            defaults : {width : 250 },
            defaultType : 'textfield',
            labelAlign:'right',
            labelWidth:120,
            waitMsgTarget : true, 
            reader : _jsonFormReader,
            //se definen los campos del formulario
            items : [
                     new Ext.form.Hidden({
                    name : 'cdElemento',
                    id : 'cdElementoId'
                    }),
                   
                    new Ext.form.Hidden( {
                    name : 'cdGrupo',
                    id : 'cdGrupo'
                    }),
                   
                    new Ext.form.TextField( {
                    name : 'dsElemen',
                    id:'edAgrPolTxtCli',
                    fieldLabel: getLabelFromMap('edAgrPolTxtCli',helpMap,'Cliente'),
					tooltip:getToolTipFromMap('edAgrPolTxtCli',helpMap,'Cliente'),
					hasHelpIcon:getHelpIconFromMap('edAgrPolTxtCli',helpMap),
		            Ayuda: getHelpTextFromMap('edAgrPolTxtCli',helpMap),
                    width: 300,
                    disabled: true,
                    readOnly:true
                    }),
                
                    new Ext.form.Hidden( {
                    name : 'cdUnieco',
                    id : 'cdUniecoId'
                    }),
                    
                    
                    new Ext.form.TextField( {
                    id:'edAgrPolTxtAseg',
                    name : 'dsUnieco',
                    fieldLabel: getLabelFromMap('edAgrPolTxtAseg',helpMap,'Aseguradora'),
					tooltip:getToolTipFromMap('edAgrPolTxtAseg',helpMap,'Aseguradora'),
					hasHelpIcon:getHelpIconFromMap('edAgrPolTxtAseg',helpMap),
		            Ayuda: getHelpTextFromMap('edAgrPolTxtAseg',helpMap),
                    width: 300,
                    disabled: true,
                    readOnly:true
                    }),
                
                    new Ext.form.Hidden( {
                    name : 'cdTipram',
                    id : 'cdTipramId'
                    }),
                    
                    new Ext.form.TextField( {
                    name : 'dsTipram',
                    id:'edAgrPolTxtTipRam',
                    fieldLabel: getLabelFromMap('edAgrPolTxtTipRam',helpMap,'Tipo de Ramo'),
					tooltip:getToolTipFromMap('edAgrPolTxtTipRam',helpMap,'Tipo de Ramo'),
					hasHelpIcon:getHelpIconFromMap('edAgrPolTxtTipRam',helpMap),
		            Ayuda: getHelpTextFromMap('edAgrPolTxtTipRam',helpMap),
                    width: 300,
                    disabled: true,
                    readOnly:true
                    }),
                    
                    new Ext.form.Hidden( {
                    name : 'cdRamo',
                    id : 'cdRamoId'
                    }),
                    
                    new Ext.form.TextField( {
                   	id:'edAgrPolTxtProd',
                    name : 'dsRamo',
                    fieldLabel: getLabelFromMap('edAgrPolTxtProd',helpMap,'Producto'),
					tooltip:getToolTipFromMap('edAgrPolTxtProd',helpMap,'Producto'),
                    hasHelpIcon:getHelpIconFromMap('edAgrPolTxtProd',helpMap),
		            Ayuda: getHelpTextFromMap('edAgrPolTxtProd',helpMap),
                    width: 300,
                    disabled: true,
                    readOnly:true
                    }),
                    
                    
                    new Ext.form.Hidden( {
                    name : 'cdTipo',
                    id : 'cdTipoId'
                    }),
                   
                    new Ext.form.TextField( {
                    id:'edAgrPolTxtTipAgr',
                    name : 'dsAgrupa',
                    fieldLabel: getLabelFromMap('edAgrPolTxtTipAgr',helpMap,'Tipo de Agrupacion'),
					tooltip:getToolTipFromMap('edAgrPolTxtTipAgr',helpMap,'Tipo de Agrupacion'),
					hasHelpIcon:getHelpIconFromMap('edAgrPolTxtTipAgr',helpMap),
		            Ayuda: getHelpTextFromMap('edAgrPolTxtTipAgr',helpMap),
                    width: 300,
                    disabled: true,
                    readOnly:true
                    }),
                    
                    
                     
                    new Ext.form.ComboBox( {
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsEstado,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'cdEstado',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: getLabelFromMap('edAgrPolCmbEst',helpMap,'Estado'),
					tooltip:getToolTipFromMap('edAgrPolCmbEst',helpMap,'Estado'),
					hasHelpIcon:getHelpIconFromMap('edAgrPolCmbEst',helpMap),
		            Ayuda: getHelpTextFromMap('edAgrPolCmbEst',helpMap),
                    width: 200,
                    emptyText:'Seleccione Estado ...',
                    selectOnFocus:true,
                    id:'edAgrPolCmbEst',                    
                    forceSelection:true,
                    onSelect: function (record) {
                                   this.setValue(record.get("id"));
                                   if (record.get("id")!=1){
                                   		desHabilitaBotonConfigura();
                                   }
                                   else
                                   {
                                   		habilitaBotonConfigura();
                                   }
                                   this.collapse();
                                 }
                    })            

            ]});


//Windows donde se van a visualizar la pantalla
var ventana = new Ext.Window({
	    id:'wndwElmtEstrAgrgrId',
        title: getLabelFromMap('wndwElmtEstrAgrgrId',helpMap,'Editar Agrupaci&oacute;n por P&oacute;lizas'),
        width: 500,
        height:280,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        buttons : [ {
                text : getLabelFromMap('edAgrPolBtnAdd',helpMap,'Guardar'),
				tooltip:getToolTipFromMap('edAgrPolBtnAdd',helpMap,'Guardar'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            url : _ACTION_GUARDAR_AGRUPACION_POLIZAS,
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso', action.result.actionMessages[0], function(){reloadGrid(Ext.getCmp('grid2'))});
                                habilitaBotonConfigura();
                                },
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'),action.result.errorMessages[0]);
                                },
                         //   waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizacion de datos...')
                        });
                     } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                     }
                }
            },
             {
                 text : getLabelFromMap('edAgrPolBtnCon',helpMap,'Configurar'),
				tooltip:getToolTipFromMap('edAgrPolBtnCon',helpMap,'Configurar'),
                 name:'configurar',
                 id:'ConfigurarId',
                 handler : function() {
                 		   if (record.get('cdTipo') == "1") {
			                         	window.location =_ACTION_IR_CONFIGURAR_AGRUPACION+"? cdTipo ="+record.get('cdTipo')+"& codigoConfiguracion="+record.get('cdGrupo');
			                   }else {
			                   			
			                   			window.location=_ACTION_IR_CONFIGURAR_AGRUPACION+"?cdTipo ="+record.get('cdTipo')+"& cveAgrupa ="+record.get('cdGrupo');
			                   			
			                 }
                 
                    }
            },
             {
                 text : getLabelFromMap('edAgrPolBtnBack',helpMap,'Regresar'),
				tooltip:getToolTipFromMap('edAgrPolBtnBack',helpMap,'Regresar'),
                 handler : function() {
                           ventana.close();
                    }
            }]
    	});


      formPanel.form.load( {
      		//	waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
            //    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...')
        });
        
        
 
   dsEstado.load();
   
 
     function habilitaBotonConfigura(){
           ventana.buttons[1].setDisabled(false);
     };
     
     function desHabilitaBotonConfigura(){
           ventana.buttons[1].setDisabled(true);
     };
     
   ventana.show();

};

