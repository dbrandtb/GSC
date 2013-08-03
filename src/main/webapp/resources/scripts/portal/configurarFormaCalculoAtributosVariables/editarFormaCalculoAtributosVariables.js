// Funcion de Editar Atributos Variables

function editar(record) {
			
			//para el JsonReader 
			var elJson = new Ext.data.JsonReader(
			    {
			        root : 'aotEstructuraList',
			        totalProperty: 'total',
			        successProperty : '@success'
			    },
			    [ 
			    {name: 'cdIden',  mapping:'cdIden',  type: 'string'},
			    {name: 'cdElemento',  mapping:'cdElemento',  type: 'string'},  
			    {name: 'dsElemen',  mapping:'dsElemen',  type: 'string'},  
			    {name: 'cdTipSit',  mapping:'cdTipSit',  type: 'string'},
			    {name: 'dsTipSit',  mapping:'dsTipSit',  type: 'string'},
			    {name: 'cdUnieco',  mapping:'cdUnieco',  type: 'string'},  
			    {name: 'dsUnieco',  mapping:'dsUnieco',  type: 'string'},  
			    {name: 'cdRamo',  mapping:'cdRamo',  type: 'string'},
			    {name: 'dsRamo',  mapping:'dsRamo',  type: 'string'},
			    {name: 'cdTabla',  mapping:'cdTabla',  type: 'string'},
			    {name: 'atributo',  mapping:'atributo',  type: 'string'},
			    {name: 'swFormaCalculo',  mapping:'swFormaCalculo',  type: 'string'},
			    {name: 'calculo',  mapping:'calculo',  type: 'string'}
			    
			    ]
			)


			var desElemen = new Ext.form.TextField({
                fieldLabel: getLabelFromMap('dsElemen',helpMap,'Cliente'),
                tooltip:getToolTipFromMap('dsElemen',helpMap,'Edita cliente para forma de c&aacute;lculo'),
                hasHelpIcon:getHelpIconFromMap('dsElemen',helpMap),								 
    			Ayuda: getHelpTextFromMap('dsElemen',helpMap),
			    name: 'dsElemen',
			    readOnly: true,
			    width: 150,
			    id: 'dsElemen'
			});
			
			var desTipSit = new Ext.form.TextField({
                fieldLabel: getLabelFromMap('dsTipSit',helpMap,'Tipo de situaci&oacute;n'),
                tooltip:getToolTipFromMap('dsTipSit',helpMap,'Edita tipo de situaci&oacute;n para forma de c&aacute;lculo'),
                  hasHelpIcon:getHelpIconFromMap('dsTipSit',helpMap),								 
    			Ayuda: getHelpTextFromMap('dsTipSit',helpMap),
			    name: 'dsTipSit',
			    readOnly: true,
			    width: 150,
			    id: 'dsTipSit'
			});
			
			var desUnieco = new Ext.form.TextField({
                fieldLabel: getLabelFromMap('dsUnieco',helpMap,'Aseguradora'),
                tooltip:getToolTipFromMap('dsUnieco',helpMap,'Edita aseguradora para forma de c&aacute;lculo'),
                  hasHelpIcon:getHelpIconFromMap('dsUnieco',helpMap),								 
    			Ayuda: getHelpTextFromMap('dsUnieco',helpMap),
			    name: 'dsUnieco',
			    readOnly: true,
			    width: 150,
			    id: 'dsUnieco'
			});
			
			var desRamo = new Ext.form.TextField({
                fieldLabel: getLabelFromMap('dsRamo',helpMap,'Producto'),
                tooltip:getToolTipFromMap('dsRamo',helpMap,'Edita producto para forma de c&aacute;lculo'),
                  hasHelpIcon:getHelpIconFromMap('dsRamo',helpMap),								 
    			Ayuda: getHelpTextFromMap('dsRamo',helpMap),
			    name: 'dsRamo',
			    readOnly: true,
			    width: 150,
			    id: 'dsRamo'
			});

			var campoAtributo = new Ext.form.TextField({
                fieldLabel: getLabelFromMap('atributo',helpMap,'Atributo'),
                tooltip:getToolTipFromMap('atributo',helpMap,'Edita atributo para forma de c&aacute;lculo'),
                  hasHelpIcon:getHelpIconFromMap('atributo',helpMap),								 
    			Ayuda: getHelpTextFromMap('atributo',helpMap),
			    name: 'atributo',
			    //name: 'cdTabla',
			    readOnly: true,
			    width: 150,
			    id: 'atributo'
			});
      
//**********  de la tabla de apoyo  CFORMACALCULO  **********//    
     var campoCalculo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_COMBO_CALCULO
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboFormaCalculo'
          
            },[
           {name: 'swFormaCalculo', type: 'string',mapping:'codigo'},
           {name: 'texto', type: 'string',mapping:'descripcionLarga'}
        ])
    });


//**************************************************************************//    

        //se define el formulario
        var formPanel = new Ext.FormPanel( {

            labelWidth : 100,

            url : _ACTION_OBTENER_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS,

            frame : true,

            renderTo: Ext.get('formulario'),

            bodyStyle : 'padding:5px 5px 0',

        	bodyStyle:'background: white',

            width : 350,
            
            labelAlign:'right',

            waitMsgTarget : true,

            defaults : {

            width : 230

            },

            defaultType : 'textfield',
            
            reader: elJson,

            //se definen los campos del formulario

           items : [
	                {
	    	    	xtype: 'hidden', 
 		            id: 'cdIden', 
 		            name:'cdIden',
            		value:record.get('cdIden') 
            		}, 

	                new Ext.form.Hidden( {
	                 name : 'cdElemento'
	                }),
                
	                new Ext.form.Hidden( {
	                 name : 'cdTipSit'
	                }),

	                new Ext.form.Hidden( {
	                 name : 'cdUnieco'
	                }),

	                new Ext.form.Hidden( {
	                 name : 'cdRamo'
	                }),

	                new Ext.form.Hidden( {
	                 name : 'cdTabla'
	                }),

					desElemen,
					desTipSit,	             
					desUnieco,	                            		        
                	desRamo,
                    campoAtributo,
                
                 {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{swFormaCalculo}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: campoCalculo,
                    displayField:'texto',
                    valueField:'swFormaCalculo',
                    hiddenName: 'swFormaCalculo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
		            fieldLabel: getLabelFromMap('calculoId',helpMap,'C&aacute;lculo'),
		            tooltip:getToolTipFromMap('calculoId',helpMap,'Elija C&aacute;lculo '),
		              hasHelpIcon:getHelpIconFromMap('calculoId',helpMap),								 
    			Ayuda: getHelpTextFromMap('calculoId',helpMap),
                    width: 300,
                    emptyText:'Seleccione Calculo...',
                    selectOnFocus:true,
                    forceSelection:true,
                    allowBlank : false,
                    id:'calculoId'
	                            		        
                }
                
                ]

        });


//Windows donde se van a visualizar la pantalla

        var window = new Ext.Window({
        	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('122',helpMap,'Configurar Forma C&aacute;lculo Atributos Variables')+'</span>',
        	width: 500,
            height:300,
            modal: true,
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
                text:getLabelFromMap('editForCalAtrVarBtnAdd',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('editForCalAtrVarBtnAdd',helpMap,'Guarda nuevos atributos de Configuraci&oacute;n'),

                text : 'Guardar',

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_CONFIGURAR_FORMA_CALCULO_ATRIBUTOS,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                            },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')

                        });

                    } else {
						Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }

                }

            }, 
            {
                text:getLabelFromMap('editForCalAtrVarBtnCanc',helpMap,'Cancelar'),
                tooltip: getToolTipFromMap('editForCalAtrVarBtnCanc',helpMap,'Cancela operaci&oacute;n de editar atributos de Configuraci&oacute;n'),

                handler : function() {
                window.close();
                }

            }]

    	});
    	
        campoCalculo.load();
        formPanel.form.load(
        	{
        	params:{cdIden: record.get('cdIden')},
			success:function(){Ext.getCmp('atributo').setValue(record.get('atributo'))}
			}
        );
        window.show();
        
};