function editar(record) {
    var numCasos_reg = new Ext.data.JsonReader({
						root: 'MEstructuraNumCasoList',
						totalProperty: 'totalCount',
						successProperty: '@success'
						}, [
							{name: 'cdNumCaso', type: 'string', mapping: 'cdNumCaso'},
							{name: 'cdModulo', type: 'string', mapping: 'cdModulo'},
							{name: 'indNumer', type: 'string', mapping: 'indNumer'},
							{name: 'nValorId', type: 'string', mapping: 'dssufpre'},
							{name: 'desModuloM', type: 'string', mapping: 'desModulo'},
							{name: 'nmDesde', type: 'string', mapping: 'nmDesde'},
							{name: 'nmHasta', type: 'string', mapping: 'nmHasta'},
							{name: 'nmCaso', type: 'string', mapping: 'nmCaso'}
						]
		);

	var idNumera = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_INDICADOR_NUMERACION 
	            }),
	        reader: new Ext.data.JsonReader({
	        root: 'comboDatosCatalogo',
	        id: 'datosCat'
	        },[
/*	       {name: 'indNumer', mapping:'id', type: 'string'},
	       {name: 'desc', mapping:'texto', type: 'string'}*/
	       {name: 'indNumer', mapping:'id'},
	       {name: 'texto', mapping:'texto'}
	       
	       
	    ])
	});			

	var pref = record.get('nmMod');
	
	var agPref = 'M';
	
	var cmbIdNumera = new Ext.form.ComboBox({
	    //tpl: '<tpl for="."><div ext:qtip="{indNumer}.{desc}" class="x-combo-list-item">{desc}</div></tpl>',
	    tpl: '<tpl for="."><div ext:qtip="{indNumer}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
	    
	    store: idNumera,
	    id:'indNumerId',
	    displayField:'texto',          //desc',
	    valueField:'indNumer',         //indNumer',
	    hiddenName: 'indNumer',        //indNumer',
	    typeAhead: true,
	    allowBlank : false,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('edNumCasosCboIdNum',helpMap,'Indicador de Numeracion'),
	    tooltip:getToolTipFromMap('edNumCasosCboIdNum',helpMap,'Seleccione Indicador de Numeracion '),
	    hasHelpIcon:getHelpIconFromMap('edNumCasosCboIdNum',helpMap),
	    Ayuda: getHelpTextFromMap('edNumCasosCboIdNum',helpMap,''), 
	    labelAlign:'right',
	    anchor:'80%',
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'Indicador de Numeracion...',
	    onSelect: function (record) {
	    	this.setValue(record.get('indNumer'));

	    	this.collapse();
	    	if (record.get('indNumer')=='U'){
	    		cmbModulo.setDisabled(true);
	    			//alert('indnumer=U',pref);
	    		form_edit.findById('nValorId').setValue('');
	    		agPref=record.get('indNumer');
	    		this.collapse()
	    	}
	    	else{
	    		form_edit.findById('nValorId').setValue(pref);
	    		//alert('CIN indnumer=M',pref);
	    		agPref=record.get('indNumer');
	    		this.collapse()
	    		
	    	}
            if (agPref=='M'){
            	cmbModulo.setDisabled(false);
            	//alert('CIN2 indnumer=M',pref);
	    		form_edit.findById('nValorId').setValue(pref);
	    		agPref=record.get('indNumer');
	    		this.collapse()
	    	}
	    	else{
	    		//alert('CIN2 indnumer=U',pref);
	    		form_edit.findById('nValorId').setValue('');
	    		agPref=record.get('indNumer');
	    		this.collapse()
	    	}
	    	
	    }
    });	

	var idModulo = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_DATOS_MODULO 
	            }),
	        reader: new Ext.data.JsonReader({
	        root: 'comboDatosModulo',
	        id: 'codigoModulo'
	        },[
	       {name: 'cdMod', mapping:'codigo', type: 'string'},
	       {name: 'dsMod', mapping:'descripLarga', type: 'string'},
	       {name: 'nmMod', mapping:'descripCorta', type: 'string'}
	    ])
	});			


	var cmbModulo = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{cdMod}.{dsMod}" class="x-combo-list-item">{dsMod}</div></tpl>',
	    store: idModulo,
	    id:'moduloId',
	    displayField:'dsMod',
	    valueField:'cdMod',
	    hiddenName: 'cdMod',
	    typeAhead: true,
	    allowBlank : false,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('edNumModulo',helpMap,' M&oacute;dulo'),
	    tooltip:getToolTipFromMap('edNumModulo',helpMap,'Seleccione  M&oacute;dulo '),
	     hasHelpIcon:getHelpIconFromMap('edNumModulo',helpMap),
	     Ayuda: getHelpTextFromMap('edNumModulo',helpMap,''), 
	    labelAlign:'right',
	    anchor:'80%',
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'Seleccione  Módulo...',

        onSelect: function (record) {
            this.setValue(record.get('cdMod'));
 
            form_edit.findById('nValorId').setValue(record.get('nmMod'));
            pref = record.get('nmMod');
           
            if (agPref=='U'){
            	
	    		form_edit.findById('nValorId').setValue('');
	    		//agPref=record.get('indNumer');
	    		//alert('valor de agPref=U');
	    		//alert(agPref);
	    		this.collapse()
	    	}
	    	else{
	    		//if (agPref=='M')
            	//form_ag.findById('nValorId').setValue(record.get('nmMod'));
            	form_edit.findById('nValorId').setValue(pref);
            	//agPref=record.get('indNumer');
	    		//alert('valor de agPref=M');
	    		//alert(agPref);
            	this.collapse()
            }
            //this.collapse();
 				//***
            //alert(record.get('nmMod'))
            //alert(pref)
          //  }
       }
	});	

	var form_edit = new Ext.FormPanel ({
        labelWidth : 100,
        url : _ACTION_OBTENER_NUMERACION_CASOS,
        frame : true,
        bodyStyle : 'padding:5px 5px 0',
        bodyStyle:'background: white',
        width : 500,
        autoHeight: true,
        waitMsgTarget : true,
        layout: 'form',
        buttonAlign: "center",
        labelAlign: 'right',
        reader: numCasos_reg,  
        items: [
                {xtype: 'hidden', 
                name: 'cdNumCaso',
                id: 'cdNumCasoId'
                },

                {xtype: 'hidden', 
                name: 'cd_Modulo',
                id:'cd_Modulo',
                value:record.get('cdModulo')
                },

                
                cmbIdNumera,
                cmbModulo,
                
                 
                {xtype: 'textfield', 
				 fieldLabel: getLabelFromMap('edValor',helpMap,'Valor'),
                 tooltip: getToolTipFromMap('edValor',helpMap,'Valor de Numeraci&oacute;n de casos'),
                 hasHelpIcon:getHelpIconFromMap('edValor',helpMap),
		         Ayuda: getHelpTextFromMap('edValor',helpMap,''),  
				 name: 'nValor', 
				 id: 'nValorId',
				 anchor: '50%',
				 disabled:true
				 },
				 
                {xtype: 'numberfield', 
				 fieldLabel: getLabelFromMap('edNumDesde',helpMap,'N&uacute;mero Desde'),
                 tooltip: getToolTipFromMap('edNumDesde',helpMap,'Valor de Inicio...'),
                 hasHelpIcon:getHelpIconFromMap('edNumDesde',helpMap),
		         Ayuda: getHelpTextFromMap('edNumDesde',helpMap,''),  
				 name: 'nmDesde', 
				 id: 'nmDesdeId',
				 anchor: '50%',
				 clearCls:'',
				 allowBlank : false,
				 mode: 'local',
				 listeners: {
                 			blur: function () {
                 							
                 							form_edit.findById('nmCasoId').setValue(this.getValue());
                 				}
                 			}
				 
				 },
				 
                {xtype: 'numberfield', 
                 fieldLabel: getLabelFromMap('edNumHasta',helpMap,'N&uacute;mero Hasta'),
                 tooltip: getToolTipFromMap('edNumHasta',helpMap,'Valor de fin...'),
                 hasHelpIcon:getHelpIconFromMap('edNumHasta',helpMap),
		         Ayuda: getHelpTextFromMap('edNumHasta',helpMap,''), 
		         anchor: '50%', 
                 name: 'nmHasta',
                 id: 'nmHastaId',
				 clearCls:'',
                 allowBlank : false,
                 mode: 'local'
                 },
                 
                {xtype: 'numberfield', 
                 fieldLabel: getLabelFromMap('edNumActual',helpMap,'N&uacute;mero Actual'),
                 tooltip: getToolTipFromMap('edNumActual',helpMap,'Valor actual...'),
                 hasHelpIcon:getHelpIconFromMap('edNumActual',helpMap),
		         Ayuda: getHelpTextFromMap('edNumActual',helpMap,''),  
                 name: 'nmCaso', 
                 id: 'nmCasoId',
                 anchor: '50%',
				 clearCls:'',
                 disabled:true
                 }
        	]

    });

	var window = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('106',helpMap,'Editar Numeraci&oacute;n de Solicitudes  ')+'</span>',
			width: 500,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	modal: true,
        	buttonAlign:'center',
        	labelAlign:'right',
        	items: form_edit,
            //se definen los botones del formulario
            buttons : [ {

                text : getLabelFromMap('edNumCasosBtnSave',helpMap,'Guardar'),
				tooltip:getToolTipFromMap('edNumCasosBtnSave',helpMap,'Guarda actualizacion de numeraci&oacute;n de casos'),
                disabled : false,

                handler : function() {

                    if (form_edit.form.isValid()) {
						if(validarNumDesde()==true){
						
								guardarNumeracionCasos();
								window.close()
						}
                    } else {

                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'))

                    }

                }
                },
            	{

                text : getLabelFromMap('edNumCasosBtnBack',helpMap,'Regresar'),
				tooltip:getToolTipFromMap('edNumCasosBtnBack',helpMap,'Regresa a la pantalla anterior'),

                handler : function() {
                    window.close()
                		}
                }
            ]

	});

		//alert(record.get('cdNumCaso'));
	
    idNumera.load({
			   	params: {
						cdTabla: 'INDNUMER'
			   			},
				callback:function(){form_edit.findById("indNumerId").setValue(record.get('indNumer'))}
				    //cmbIdNumera.setValue(record.get('indNumerId'))}   			
    });

	
 form_edit.form.load ({
 
 	params: {
 		cdNumeCaso: record.get('cdNumCaso')
 	},
 	success: function(form, action){
         //console.log(action.result);
         var cdIndi_Envio = action.result.data.nmDesde
        // alert(cdIndi_Envio)
        }
 }
 );

    window.show();
 
    
    		//alert('cdModulo',cdModulo);

	//alert(record.get('cdModulo'));
	
    idModulo.load({
    	params: {
    				cdTabla: 'CATBOMODUL'
    			},
    	callback:function(){cmbModulo.setValue(record.get('cdModulo'))}
    });
	
    
/*    idModulo.load({
    		callback:function(){cmbModulo.setValue(record.get('cdModulo'))}
    });
*/

function guardarNumeracionCasos()
{
        var params = {
                 cdNumCaso: form_edit.findById('cdNumCasoId').getValue(),
                 indNumer: form_edit.findById('indNumerId').getValue(),
                 cdModulo: form_edit.findById('moduloId').getValue(),  
                 dssufpre: form_edit.findById('nValorId').getValue(),               
                 nmCaso: form_edit.findById('nmCasoId').getValue(),
                 nmDesde: form_edit.findById('nmDesdeId').getValue(),
                 nmHasta: form_edit.findById('nmHastaId').getValue()
            
             };
        execConnection (_ACTION_AGREGAR_GUARDAR_NUMERACION_CASOS, params, cbkGuardar);
}


function cbkGuardar (success, message) {
    if (success) {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message,function(){reloadGrid()})
    } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message)
    }

}

function validarNumDesde(){
		if((form_edit.findById('nmDesdeId').getValue()!="")&& (form_edit.findById('nmHastaId').getValue()!="")){
   			if (eval(form_edit.findById('nmDesdeId').getValue()) > eval(form_edit.findById('nmHastaId').getValue())){
   			       //Ext.Msg.alert('Informacion', 'El campo Numero Desde debe ser menor que el campo Numero Hasta');
   			         Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400084', helpMap,'El campo Numero Desde debe ser menor que el campo Numero Hasta'));
  			       return false;
   				} else {
   					return true;
   					}
   		    }
   		    else{
   			      // Ext.Msg.alert('Aviso', 'Los campos Numero Desde y Numero Hasta no pueden estar Vacios.');   
   			        Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400085', helpMap,'Los campos Numero Desde y Numero Hasta no pueden estar Vacios'));
   			       return false;		    
   		    }
   		return true;
   };

}
