function documentoPresiniestro(recordGrid, storeGrid, cdelemento, cdunieco, cdramo, updateMode, isAdd){
	
	var storeComboDocumentos = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_COMBO_DOCUMENTOS
        }),
        reader: new Ext.data.JsonReader({
            root: 'instrumentosClienteList'
            },[
           {name: 'value',      type: 'string', mapping:'cdForPag'},
           {name: 'label',      type: 'string', mapping:'dsForPag'},
           {name: 'cdInsCte',      type: 'string', mapping:'cdInsCte'}
        ]),
        remoteSort: true
	});

	var comboDocumentos =new Ext.form.ComboBox({
        tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        store: storeComboDocumentos,
        displayField:'label',
        valueField: 'value',
        allowBlank:false,
        typeAhead: true,
        labelAlign: 'top',
        labelStyle: 'text-align:right;',
        mode: 'local',
        editable:false,
        triggerAction: 'all',
        emptyText:'Seleccione...',
        selectOnFocus:true,
        fieldLabel: 'Tipo de Documento',
        name:"documento.dsTipDoc",
        focusAndSelect : function(record) {
            var index = typeof record === 'number' ? record : this.store.indexOf(record);
            this.select(index, this.isExpanded());
            this.onSelect(this.store.getAt(record), index, this.isExpanded());
        },
        onSelect : function(record, index, skipCollapse){
            if(this.fireEvent('beforeselect', this, record, index) !== false){
                this.setValue(record.data[this.valueField || this.displayField]);
                if( !skipCollapse ) {
                    this.collapse();
                }
                this.lastSelectedIndex = index + 1;
                this.fireEvent('select', this, record, index);
            }
            
            comboDocumentos.setDisabled(true);
            var myMask = new Ext.LoadMask(Ext.getCmp('formAgregarId').getEl(),{msg:'Cargando campos...'});
            myMask.show();
            
            var connect = new Ext.data.Connection();
            var paramsAtrsDinamicos;
            
            if(isAdd){
            	paramsAtrsDinamicos = {
					cdInsCte: record.get('cdInsCte')
				};
            }else{
            	paramsAtrsDinamicos = {
					cdInsCte: record.get('cdInsCte'),
					tipoTrans: 'U',
					cdValoDoc: recordGrid.get('cdValoDoc')
				};
            }
            
			connect.request ({
				url: _ACTION_OBTIENE_ATRIBUTOS,
				params: paramsAtrsDinamicos,
				callback: function (options, success, response) {	
					comboDocumentos.setDisabled(false);			   
					if (Ext.util.JSON.decode(response.responseText).success != false) {
						
						if(Ext.getCmp('fieldsetId')) Ext.getCmp('fieldsetId').destroy();
						Ext.getCmp('id-form-documentos').add(Ext.util.JSON.decode(Ext.util.JSON.decode(response.responseText).mensajeRespuesta));
						Ext.getCmp('id-form-documentos').doLayout();
						
						
					}else{
						if(Ext.getCmp('fieldsetId')) Ext.getCmp('fieldsetId').destroy();
						Ext.getCmp('id-form-documentos').doLayout();
					}
					
					myMask.hide();
					Ext.getCmp('tipoDocumentoSelUnica').setValue(record.get('cdInsCte'));
					Ext.getCmp('cdTipoDocumento').setValue(record.get('value'));
					
				}
			});
            
            //Ext.getCmp('hidden-codigo-intrumeto-pago-compra').setValue(record.get('value'));
            //Ext.getCmp('hidden-cdInsCte').setValue(record.get('cdInsCte'));
            
        }
});
storeComboDocumentos.load({
	params: {
		cdElemento: cdelemento,
		cdUnieco: cdunieco,
		cdRamo: cdramo
	},
	callback: function(){
		if(!isAdd){
			comboDocumentos.setValue(recordGrid.get('cdTipDoc'));
			
			comboDocumentos.setDisabled(true);
			var myMask = new Ext.LoadMask(Ext.getCmp('formAgregarId').getEl(),{msg:'Cargando campos...'});
			myMask.show();
			
            var connect = new Ext.data.Connection();
            
            var paramsAtrsDinamicos = {
					cdInsCte: recordGrid.get('cdUnica'),
					tipoTrans: 'U',
					cdValoDoc: recordGrid.get('cdValoDoc'),
					isNew: recordGrid.get('isNew')
				};
            
            
			connect.request ({
				url: _ACTION_OBTIENE_ATRIBUTOS,
				params: paramsAtrsDinamicos,
				callback: function (options, success, response) {	
					comboDocumentos.setDisabled(false);			   
					if (Ext.util.JSON.decode(response.responseText).success != false) {
						
						if(Ext.getCmp('fieldsetId')) Ext.getCmp('fieldsetId').destroy();
						Ext.getCmp('id-form-documentos').add(Ext.util.JSON.decode(Ext.util.JSON.decode(response.responseText).mensajeRespuesta));
						Ext.getCmp('id-form-documentos').doLayout();
						
						
					}else{
						if(Ext.getCmp('fieldsetId')) Ext.getCmp('fieldsetId').destroy();
						Ext.getCmp('id-form-documentos').doLayout();
					}
					
					myMask.hide();
					
					Ext.getCmp('tipoDocumentoSelUnica').setValue(recordGrid.get('cdUnica'));
					Ext.getCmp('cdTipoDocumento').setValue(recordGrid.get('cdTipDoc'));
					Ext.getCmp('claveDoc').setValue(recordGrid.get('cdValoDoc'));
					Ext.getCmp('isNewDoc').setValue(recordGrid.get('isNew'));
					
				}
			});

			
		}
	}
});
	
	var _actionFormPrincipal;
	var tituloVentana;

	if(isAdd){
		_actionFormPrincipal = _ACTION_AGREGA_DOCUMENTO;
		tituloVentana = 'Agregar Documento al Pre Siniestro';
	}else{
		_actionFormPrincipal = _ACTION_EDITA_DOCUMENTO;
		tituloVentana = 'Editar Documento del Pre Siniestro';
	}
	
	var formAgregar = new Ext.form.FormPanel({
		  id:'formAgregarId',
		  url: _actionFormPrincipal,
	      title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('',helpMap,tituloVentana)+'</span>',
	      bodyStyle:'background: white',
	      buttonAlign: "center",
	      labelAlign: 'right',
	      autoHeight:true,
	      frame:true,
	      width: 500,
	      items: [
	      		{
	      		layout:'form',
				border: false,
				items:[
					{
	      			//bodyStyle:'background: white',
	        		labelWidth: 100,
	              	layout: 'form',
					frame:true,
			       	baseCls: '',
			       	buttonAlign: "center",
      		        items:[
      		        	{
      		        	layout:'column',
      		        	html:'<br>',
      		    		items:[
	      		    			{
						    	columnWidth:1,
		              			layout: 'form', 
		      		        	items:[comboDocumentos, {
                                            layout:'form',
                                            id:'id-form-documentos',
                                            border:false,
                                            items:[{
                                    			xtype: 'hidden', 
                                    			id: 'fieldsetId' 
                                    		}]
                                    },{xtype:'hidden',id:'tipoDocumentoSelUnica',name:'documento.cdUnica'}, //TODO: en realidad deberian de guardarse documento.cdTipDoc y documento.cdUnica, ya que la cdUnica representa el tipo de documento 
                                    {xtype:'hidden',id:'cdTipoDocumento',name:'documento.cdTipDoc'},
                                    {xtype:'hidden',id:'claveDoc',name:'documento.cdValoDoc'},
                                    {xtype:'hidden',id:'isNewDoc',name:'documento.isNew'}
                                    ]
								}
	              			
	              			 ]
              			}
              		]
			   	}]
			}]
	});
	
var windowAgregar = new Ext.Window({
			id:'windowAgregarId',
	       	width: 450,
	       	autoHeight:true,
	       	minWidth: 300,
	       	minHeight: 100,
	       	modal: true,
	       	layout: 'fit',
	       	plain:true,
	       	bodyStyle:'padding:5px;',
	       	buttonAlign:'center',
	       	items: formAgregar,
	        buttons: [{
               text : 'Aceptar',
               handler : function(){
               		if(formAgregar.form.isValid()){
               			if(Ext.getCmp('fieldsetId') && Ext.getCmp('fieldsetId').items){
							if(!validarFormaFieldsetDinamico()){
								Ext.MessageBox.alert('INFORMACI&Oacute;N', 'Por favor complete la informaci&oacute;n requerida');
								return;
							}
						}
               			
                    	formAgregar.form.submit({
				                            success : function(from, action) {
				                            				//Ext.MessageBox.alert('Aviso', Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta, function () {storeGrid.load();});
						                                	windowAgregar.close();
						                                	storeGrid.load();
				                                },
				                            failure : function(form, action) {
				                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap, 'Error'), /*Ext.util.JSON.decode(action.response.responseText).mensajeRespuesta*/'No se pudo guardar el documento.');
				                                },
				                            waitTitle: getLabelFromMap('400021', helpMap, 'Espere...'),
				                            waitMsg : getLabelFromMap('', helpMap,'Actualizando datos del grid documentos...')
				        });
                    }else{
                        Ext.Msg.alert('Aviso', 'Por favor complete la informaci&oacute;n requerida');
                    }
               }
	        },{
	        text: 'Cancelar',
	        	handler: function(){
	        		windowAgregar.close();
	       	 }
	        }]
	   	});
	
windowAgregar.show();

function validarFormaFieldsetDinamico(){
	for(var i = 0 ; i< Ext.getCmp('fieldsetId').items.length ; i ++){
		if(!Ext.getCmp('fieldsetId').items.get(i).isValid())return false;
	}
return true;
}
	
	
}