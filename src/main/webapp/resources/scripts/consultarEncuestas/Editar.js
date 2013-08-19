function editarEncuestas(record){
	alert(1);
	var elreader = new Ext.data.JsonReader( {          
        root : 'MListValorEncuesta',
        totalProperty: 'totalCount',
        successProperty : '@success'

        }, [
		        
		        {name: 'dsUnieco',  type: 'string',  mapping:'dsUnieco'}
		        
				]);
	var storeForm = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_BUSCAR_PREGUNTA_ENCUESTA_ENC
        }),
		reader : elreader});
        
        
   	var formPanelEdit = new Ext.FormPanel({			
	        //el:'encabezado',
	        id: 'encabezadoId',
	        //store: storeForm,
	        //title: '<span style="color:black;font-size:12px;">Ingresar Encuesta</span>',
	        iconCls:'logo',
	        //bodyStyle:'background: white',
	        bodyStyle : 'padding:5px 5px 0',
	        labelAlign: "right",
	        
	        reader: elreader,
	        successProperty: 'success',
	        frame:true,
	        width: 650,
			layout:'table',
			layoutConfig: {columns:3},
			url:_ACTION_BUSCAR_PREGUNTA_ENCUESTA_ENC,
            defaults: {width:210},	        
	        autoHeight:true,
            items:[
					{	
						colspan:3,
    					layout: 'form',  
	            		labelWidth: 100,
	            		width: 340,
	           			items: [
	           			 new Ext.form.TextField({
	           			 			id: 'dsUniecoEdId',
	           			 			fieldLabel: getLabelFromMap('txtfldaseguradoraId',helpMap,'Aseguradora'),
							        tooltip:getToolTipFromMap('txtfldaseguradoraId',helpMap,'Aseguradora'),
							        hasHelpIcon:getHelpIconFromMap('txtfldaseguradoraId',helpMap),
			       					Ayuda:getHelpTextFromMap('txtfldaseguradoraId',helpMap),
	           			 			name: 'dsUnieco',
	           			 			width: 180
	           			 })
	           			
	           			
			   		 			]
    				}
            	]												
	});
       
	
	var win = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('95', helpMap,'Editar Encuesta')+'</span>',
			width: 670,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	//modal: true,
        	//items: [formPanelEdit,el_formDatosEncuestas,formBoton]
        	items: formPanelEdit
        	
            
	});
	
	formPanelEdit.load({
				waitTitle : getLabelFromMap('400021', helpMap,'Espere...'),
                waitMsg : getLabelFromMap('400028', helpMap,'Leyendo datos...'),
                    
				params:{
				pv_nmconfig_i:'43',//record.get('nmConfig')
				pv_cdunieco_i:'52',//record.get('cdUnieco')
				pv_cdramo_i:'500',//record.get('cdRamo')
				pv_estado_i:'M',//record.get('estado')
				pv_nmpoliza_i:'516',//record.get('nmPoliza');
				pv_cdperson_i:'714'//record.get('')
				},
				success:function(form, action){alert('succes');/*console.log(action)*/},
				failure:function(){alert('failure');/*console.log(formPanelEdit);console.log(storeForm);*//*alert(storeForm.reader.jsonData.mListValorEncuesta[0])*/},
				callback:function(r,o,success){
					if(success){alert('callback:'+ r)}
					}
				}
				);
	
	win.show();
        alert(1);
        storeForm.load({
				params:{
				pv_nmconfig_i:'43',//record.get('nmConfig')
				pv_cdunieco_i:'52',//record.get('cdUnieco')
				pv_cdramo_i:'500',//record.get('cdRamo')
				pv_estado_i:'M',//record.get('estado')
				pv_nmpoliza_i:'516',//record.get('nmPoliza');
				pv_cdperson_i:'714'//record.get('')
				},
				callback: function (r, o, suc) {
					alert("uniEco: " + storeForm.reader.jsonData.MListValorEncuesta[0].dsUnieco);
					alert("success: " + suc);
				}
		})
}