function agregar() {

	var idNumera = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_INDICADOR_NUMERACION 
	            }),
	        reader: new Ext.data.JsonReader({
	        root: 'comboDatosCatalogo',
	        id: 'datosCat'
	        },[
	       {name: 'indNumer', mapping:'id', type: 'string'},
	       {name: 'desc', mapping:'texto', type: 'string'}
	    ])
	});			

	var pref 
	var agPref = 'M'		//=form_ag.findById('indNumerId').getValue()	//=record.get('indNumer');


	var cmbIdNumera = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{indNumer}.{desc}" class="x-combo-list-item">{desc}</div></tpl>',
	    store: idNumera,
	    id:'indNumerId',
	    displayField:'desc',
	    valueField:'indNumer',
	    hiddenName: 'indNumer',
	    typeAhead: true,
	    allowBlank : false,
	    mode: 'local',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('indNumerId',helpMap,'Indicador de Numeracion'),
	    tooltip:getToolTipFromMap('indNumerId',helpMap,'Seleccione Indicador de Numeracion '),
	    hasHelpIcon:getHelpIconFromMap('indNumerId',helpMap),
		Ayuda: getHelpTextFromMap('indNumerId',helpMap,''),   
	    labelAlign:'right',
	    anchor:'80%',
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'Seleccione Indicador de Numeración...',
	    onSelect: function (record) {
	    	this.setValue(record.get('indNumer'));
	    	this.collapse();
	    	if (record.get('indNumer')=='U'){
	    		cmbModulo.setDisabled(true);
	    			//alert('indnumer=U',pref);
	    		form_ag.findById('nValorId').setValue('');
	    		agPref=record.get('indNumer');
	    		this.collapse()
	    	}
	    	else{
	    		form_ag.findById('nValorId').setValue(pref);
	    		//alert('CIN indnumer=M',pref);
	    		agPref=record.get('indNumer');
	    		this.collapse()
	    		
	    	}
            if (agPref=='M'){
            	cmbModulo.setDisabled(false);
            	//alert('CIN2 indnumer=M',pref);
	    		form_ag.findById('nValorId').setValue(pref);
	    		agPref=record.get('indNumer');
	    		this.collapse()
	    	}
	    	else{
	    		//alert('CIN2 indnumer=U',pref);
	    		form_ag.findById('nValorId').setValue('');
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
	    fieldLabel: getLabelFromMap('moduloId',helpMap,' M&oacute;dulo'),
	    tooltip:getToolTipFromMap('moduloId',helpMap,'Seleccione  M&oacute;dulo '),
	    hasHelpIcon:getHelpIconFromMap('moduloId',helpMap),
		Ayuda: getHelpTextFromMap('moduloId',helpMap,''),  
	    labelAlign:'right',
	    anchor:'80%',
	    selectOnFocus:true,
	    forceSelection:true,
	    emptyText:'Seleccione Modulo...',

        onSelect: function (record) {
            this.setValue(record.get('cdMod'));
 
            form_ag.findById('nValorId').setValue(record.get('nmMod'));
            pref = record.get('nmMod');
           
            if (agPref=='U'){
	    		form_ag.findById('nValorId').setValue('');
	    		//agPref=record.get('indNumer');
	    		//alert('valor de agPref=U');
	    		//alert(agPref);
	    		this.collapse()
	    	}
	    	else{
	    		//if (agPref=='M')
            	//form_ag.findById('nValorId').setValue(record.get('nmMod'));
            	form_ag.findById('nValorId').setValue(pref);
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

	var form_ag = new Ext.FormPanel ({
        labelWidth : 100,
        url : _ACTION_AGREGAR_GUARDAR_NUMERACION_CASOS,
        frame : true,
        bodyStyle : 'padding:5px 5px 0',
        bodyStyle:'background: white',
        width : 500,
        autoHeight: true,
        waitMsgTarget : true,
        layout: 'form',
        buttonAlign: "center",
        labelAlign: 'right',
        items: [
                {xtype: 'hidden', 
                id: 'cdNumCasoId', 
                name: 'cdNumCaso'
                },

				cmbIdNumera,
				
                {xtype: 'hidden', 
                id: 'moduloId', 
                name: 'cdModulo'
                },
                
                
                cmbModulo,

                {xtype: 'textfield', 
                 //width:500,
				 fieldLabel: getLabelFromMap('nValorId',helpMap,'Valor'),
                 tooltip: getToolTipFromMap('nValorId',helpMap,'Valor de Numeraci&oacute;n de casos'),
                 hasHelpIcon:getHelpIconFromMap('nValorId',helpMap),
		         Ayuda: getHelpTextFromMap('nValorId',helpMap,''),  
				 name: 'nValor', 
				 id: 'nValorId',
				  anchor: '50%',
				// width:500,
				 disabled:true
				 },
				
				 
                {xtype: 'numberfield', 
				 fieldLabel: getLabelFromMap('nmDesdeId',helpMap,'N&uacute;mero Desde'),
                 tooltip: getToolTipFromMap('nmDesdeId',helpMap,'Valor de Inicio...'),
                 hasHelpIcon:getHelpIconFromMap('nmDesdeId',helpMap),
		         Ayuda: getHelpTextFromMap('nmDesdeId',helpMap,''),  
				 name: 'nmDesde', 
				 anchor: '50%',
				 id: 'nmDesdeId',
				 allowBlank : false,
				 mode: 'local',
				 clearCls:'',
				 listeners: {
                 			blur: function () {

                 							form_ag.findById('nmCasoId').setValue(this.getValue());
                 				}
                 			}
				 },
				 
                {xtype: 'numberfield', 
                 fieldLabel: getLabelFromMap('nmHastaId',helpMap,'N&uacute;mero Hasta'),
                 tooltip: getToolTipFromMap('nmHastaId',helpMap,'Valor de fin...'),
                 hasHelpIcon:getHelpIconFromMap('nmHastaId',helpMap),
		         Ayuda: getHelpTextFromMap('nmHastaId',helpMap,''),  
                 name: 'nmHasta',
                 id: 'nmHastaId',
                 anchor: '50%',
                 allowBlank : false,
                 mode: 'local'
                 },
                 
                {xtype: 'numberfield', 
                 fieldLabel: getLabelFromMap('nmCasoId',helpMap,'N&uacute;mero Actual'),
                 tooltip: getToolTipFromMap('nmCasoId',helpMap,'Valor actual...'),
                 hasHelpIcon:getHelpIconFromMap('nmCasoId',helpMap),
		         Ayuda: getHelpTextFromMap('nmCasoId',helpMap,''),  
                 name: 'nmCaso', 
                 id: 'nmCasoId',
                 anchor: '50%',
                 disabled:true
                 }
        	]

    });

	var window = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('105',helpMap,'Agregar Numeraci&oacute;n de Solicitudes')+'</span>',
			width: 500,
			autoHeight: true,
			//bodyStyle : 'padding:5px 5px 0',
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	modal:true,
        	items: form_ag,
            //se definen los botones del formulario
            buttons : [ {

                text : getLabelFromMap('agNumCasosBtnSave',helpMap,'Guardar'),
				tooltip:getToolTipFromMap('agNumCasosBtnSave',helpMap,'Guarda Numeraci&oacute;n de Solicitudes'),

                disabled : false,

                handler : function() {

                    if (form_ag.form.isValid()) {
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

                text : getLabelFromMap('agNumCasosBtnBack',helpMap,'Regresar'),
				tooltip:getToolTipFromMap('agNumCasosBtnBack',helpMap,'Regresa a la pantalla anterior'),

                handler : function() {
                    window.close()
                		}
                }
         ]

	});

    window.show();
    idNumera.load({
    	params: {
    				cdTabla: 'INDNUMER'
    			}
    	//callback:function(){cmbIdNumera.setValue(record.get('indNumerId'))}		
    });
    //idModulo.load();
    idModulo.load({
    	params: {
    				cdTabla: 'CATBOMODUL'
    			}
    });
    
    //var vActual
    
/*    form_ag.findById('nmCasoId').setValue();
    form_ag.findById('nmDesdeId').setValue();
    form_ag.findById('nmHastaId').setValue();*/
 
//***    
 /*   vActual=form_ag.findById('nmDesdeId').getValue();
    alert(vActual);
    nmCaso=form_ag.findById('nmDesdeId').setValue(vActual);*/
//***

/*                 alert(form_ag.findById('nmCasoId').getValue()),              
                 alert(form_ag.findById('nmDesdeId').getValue()),
                 alert(form_ag.findById('nmHastaId').getValue()),*/


function guardarNumeracionCasos()
{
        var params = {
                 cdNumCaso: form_ag.findById('cdNumCasoId').getValue(),
                 indNumer: form_ag.findById('indNumerId').getValue(),
                 cdModulo: form_ag.findById('moduloId').getValue(),  
                 dssufpre: form_ag.findById('nValorId').getValue(), 
                 nmCaso: form_ag.findById('nmCasoId').getValue(),
                 nmDesde: form_ag.findById('nmDesdeId').getValue(),
                 nmHasta: form_ag.findById('nmHastaId').getValue()
            
             };
        execConnection (_ACTION_AGREGAR_GUARDAR_NUMERACION_CASOS, params, cbkGuardar)
}

function cbkGuardar (success, message) {
    if (success) {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message,function(){reloadGrid()})
    } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message)
    }

}
       
//***
function validarNumDesde(){
		if((form_ag.findById('nmDesdeId').getValue()!="")&& (form_ag.findById('nmHastaId').getValue()!="")){
   			if (eval(form_ag.findById('nmDesdeId').getValue()) > eval(form_ag.findById('nmHastaId').getValue())){
   			     //  Ext.Msg.alert('Informacion', 'El campo Numero Desde debe ser menor que el campo Numero Hasta');
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
       
       
 } 	//fin de la funcion

