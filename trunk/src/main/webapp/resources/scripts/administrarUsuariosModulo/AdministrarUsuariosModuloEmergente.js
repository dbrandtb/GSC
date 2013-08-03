function administrarUsuarioModuloEmergente(){

    var storeArchivo = new Ext.data.Store({
			proxy:  new Ext.data.HttpProxy({url: _ACTION_AGREGAR_USUARIO}),
			reader: new Ext.data.JsonReader({
						root:'MUsuariosList',
						totalProperty: 'totalCount',
						successProperty : '@success'
					},
					[
						{name: 'tipo',  type: 'string'},
						{name: 'descripcion',  type: 'string'}
					]
			)
	});
	
	/*dsUsuario;
	private String cdUsuario;*/
	
var storeGetProceNotifi = new Ext.data.Store({
    url:_ACTION_BUSCAR_USUARIOS_ASIGNAR,
    reader: new Ext.data.JsonReader({
    	id: 'cdUsuario',
    	root: 'MLisAdministrarUsuariosModulo',
    	totalProperty: 'totalCount',
    	successProperty: '@success'
    	},[
    	    {name: 'cdUsuario',  type: 'string', mapping: 'cdUsuario'},
    	    {name: 'dsUsuario',  type: 'string', mapping: 'dsUsuario'}
    	]
    )
});
	var Usuarios = new Ext.form.TextField({
       	fieldLabel: getLabelFromMap('txtFldPrcsTrNtfcn', helpMap,'Usuarios'), 
		tooltip: getToolTipFromMap('txtFldPrcsTrNtfcn', helpMap,'Usuarios'),  				                    	
        id: 'usuarios', 
        name: 'usuarios',
        allowBlank: true,
        width:260
    });
	
var codigo = new Ext.form.Hidden( {
                disabled:false,
                name:'cdNotificacion',
                id:'cdNotificacion'
            });

var comboModulo = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripCorta}.{descripLarga}" class="x-combo-list-item">{descripCorta}</div></tpl>',
	    store:obtenerStoreParaCombo(),
	   // success:function() {obtenerStoreParaCombo()}, 
	    id:'nivelId',
	    name: 'nivelId',
	    width:260,
	    displayField:'descripCorta',
	    valueField:'codigo',
	    hiddenName: 'codigoh',
	    typeAhead: true,
	    anchor:'%',
	    allowBlank : false,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('administrarUsuariosCboModulo',helpMap,'M&oacute;dulo'),
	    tooltip:getToolTipFromMap('administrarUsuariosCboModulo',helpMap,'Seleccione M&oacute;dulo'),
	    labelAlign:'right',
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'...'
	    
       });	

function obtenerStoreParaCombo(){//cat
		var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url:_ACTION_COMBO_MODULO}),
			reader: new Ext.data.JsonReader(
				{
					root:'comboDatosModulo',
					totalProperty: 'totalCount',
					successProperty : '@success'
				},
				[
					{name: 'codigo', type: 'string'},
					{name: 'descripCorta', type: 'string'},
					 {name: 'descripLarga', type: 'string'}
				
				]
				)
		});
		
		store.load({
			params: {
    				cdTabla: 'CATBOMODUL'
    			}
		});
		return store;
	}; 
	
	
 var isForm = new Ext.form.FormPanel({
        //title: 'ItemSelector Test',
        width:700,
        bodyStyle: 'padding:10px;',
        renderTo: 'formMultiselect',
        items:[{
            xtype:"itemselector",
            name:"itemselector",
            id:"itemselector",
            //fieldLabel:"",            
            fromStore: storeGetProceNotifi,
           // toStore: el_storeDer,
           
           //dataFields:["code", "desc"],
            dataFields:["cdUsuario", "dsUsuario"],
            
           toData:[/*["10", "Ten"]*/],
            
            msWidth:250,
            msHeight:200,
            //valueField:"code",
            //displayField:"desc",
            
            valueField:"cdUsuario",           
            displayField:"dsUsuario",
            //imagePath:"images/",
            toLegend:"Usuarios por Modulo",
            fromLegend:"Usuarios ",
            
            fromData:[[123,"One Hundred Twenty Three"],
                ["1", "One"], ["2", "Two"], ["3", "Three"], ["4", "Four"], ["5", "Five"],
                ["6", "Six"], ["7", "Seven"], ["8", "Eight"], ["9", "Nine"]]//,
           /* toTBar:[{
                //text:"",
                handler:function(){
                    var i=isForm.getForm().findField("itemselector");
                    i.reset.call(i);
                }
            }]*/
        }]//,
        
       /* buttons: [{
            text: 'Save',
            handler: function(){
                if(isForm.getForm().isValid()){
                    Ext.Msg.alert('Submitted Values', 'The following will be sent to the server: <br />'+ 
                        isForm.getForm().getValues(true));
                }
            }
        }]*/
    });
    

	isForm.render();

	var formPanel = new Ext.FormPanel({
		    width: 700,
		    store: storeArchivo,
		    bodyStyle:'background: white',
	        buttonAlign: "center",
	        labelAlign: 'right',
	        frame:true,
	        layout: 'table',
            layoutConfig: { columns: 1, columnWidth: .33},
		    //url: _ACTION_AGREGAR_ARCHIVO,
		    items: [
		    		{
           			html: '<br/><span class="x-form-item" style="font-weight:bold"></span>',
            		colspan:3
            		},
		            {            		
           			layout: 'form',  
           			labelWidth: 90,  
           			
           			     			            		
           			items: [
           			        comboModulo
           			        
           				]
            		},
            		
            		
            		  {
           			layout: 'form',
           			labelWidth: 90,
           			items: [{
                                //xtype:"itemselector",
					            //name:"itemselector",
					           // fieldLabel:"ItemSelector"
					            //dataFields:["code", "desc"],
					           // toData:[["10", "Ten"]],
					           // msWidth: 250,
					            //msHeight:200,
					           // valueField:"code",
					           // displayField:"desc",
					            //imagePath:"images/",
					            //toLegend:"Selected",
					            //fromLegend:"Available",
					           // fromData:[[123,"One Hundred Twenty Three"],
					                //["1", "One"], ["2", "Two"], ["3", "Three"], ["4", "Four"], ["5", "Five"],
					                //["6", "Six"], ["7", "Seven"], ["8", "Eight"], ["9", "Nine"]]
				
					        }]
            		},
            		   
                    
            		{
           			layout: 'form',
           			labelWidth: 90,			     			            		
           			items: [
           			        Usuarios
           			        
           				],
           				
           				 buttonAlign: 'right',
			           	 buttons:[
			                    {
			                    text:getLabelFromMap('dtlFrmtButtonBuscar', helpMap,'Buscar'),
			                    tooltip:getToolTipFromMap('dtlFrmtButtonBuscar', helpMap,'Buscar Usuarios'),                               
			                    handler: function () {
			                        buscarUsuarios(formPanel.findById('usuarios').getValue());
			                    }
			                 }
			                ]		           			       
           			       
           			
            		},
            		       				
           			
            	
            		{
           			html: '<br/><span class="x-form-item" style="font-weight:bold"></span>',
            		colspan:3
            		}
		   ]
	});

function  buscarUsuarios(parametro){
    storeGetProceNotifi.reload({
        params: {   
                   // cdNotificacion:key,
                    //dsUsuario: parametro
                    pv_dsusuario_i:parametro
                 },
        callback: function (r, o, success) {
        		Ext.getCmp('itemselector').refreshFromView(storeGetProceNotifi);
        }
    });
}
function cbkBuscar (_success, _message) {
	if (!_success) {
		Ext.Msg.alert('Error', _message);
	}
}


	
	
		var ventana = new Ext.Window ({
		    title: '<span style="color:black;font-size:12px;">Administrar Usuarios por M&oacute;dulo</span>',
		    width: 700,
		    modal: true,
		    autoHeight: true,
		    items: [formPanel,isForm],
		      buttonAlign:'center',
		       buttons: [ {
		        text:getLabelFromMap('BtnGuardar',helpMap,'Guardar'),
		        tooltip: getToolTipFromMap('BtnGuardar',helpMap,'Guardar'),
				handler: function(){
                if (formPanel.form.isValid()) {
                    /*formPanel.form.submit({
                        url : _ACTION_AGREGAR_USUARIO,
                        success : function(form, action) {
                            Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0]);
                            formPanel.getForm().reset();
                            ventana.close();                                     
                        },
                        failure : function(form, action){
                            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                       },
                       	waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
                        waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                    });*/
                    guardarUsuarios();
                    
                } 
                
                else {
                    Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
                }
            }
						},
						
						  { 
		                    text:getLabelFromMap('compraBtnBack',helpMap,'Regresar'),
		                    tooltip: getToolTipFromMap('compraBtnBack',helpMap,'Regresa a la pantalla anterior'),
							handler: function(){ventana.close()}
						}
				 
			]
						
						
		});
			function guardarUsuarios(){
			        var conn = new Ext.data.Connection();
				                conn.request({
							    	url: _ACTION_AGREGAR_USUARIO,
							    	method: 'POST',
							    	params: {				    				
							    				cdModulo: formPanel.findById("nivelId").getValue(),
									    		cdUsuario: formPanel.findById("usuarios").getValue()									    		
									    		// cdUsuario:  Ext.getCmp('itemselector').toMultiselect.view.getSelectedIndexes()
									    		// cdUusario: isForm.getForm().getValues(true)
			                                    // cdUsuario: Ext.getCmp('itemselector').toMultiselect.view.selectRange() 
			                                    
			                                      
							    			},
				 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
			                   		   
			                   		    callback: function (options, success, response) {
			        if (Ext.util.JSON.decode(response.responseText).success == false) {
			            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
			        }
			           else {
			                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito');
			              //codNoti=Ext.util.JSON.decode(response.responseText).cdNotificacionNew;
			               //guardarUsuarios2(codNoti);
			        }
			    }
			 })
			}
			
			
			

		ventana.show();

        //formPanel.store.load({
			//params:{nmcaso: caso,nmovimiento:movimiento,start:0,limit:10}
		//});
		
	
	}


