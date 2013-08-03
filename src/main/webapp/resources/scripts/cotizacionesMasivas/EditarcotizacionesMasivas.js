      function muestraDetalle(record){//Variables temporales que se usarán como parametros para la url flujocotizacion/comprarCotizaciones.action 
		//sm, row, rec
		var tmpCodigoAseguradora;
	    var tmpCodigoProducto;
	    var tmpEstado;
	    var tmpNmpoliza;
	    var tmpNmsituac;
	    

        var nombreMarca = new Ext.form.TextField({
		    fieldLabel: 'Marca',
		    name:'nombreMarca',
		    //value:_aseguradora,
		    readonly:true,
		    width: 160
		});
		
		var nombreModelo = new Ext.form.TextField({
		    fieldLabel: 'Modelo',
		    name:'nombreModelo',
		    //value:_aseguradora,
		    readonly:true,
		    width: 160
		});
       
        var descripcion = new Ext.form.TextField({
		    fieldLabel: 'Descripcion',
		    name:'descripcion',
		    //value:_aseguradora,
		    readonly:true,
		    width: 160
		});
       
		var nombreAseguradora = new Ext.form.TextField({
		    fieldLabel: 'Aseguradora',
		    name:'nombreAseguradora',
		    //value:_aseguradora,
		    readonly:true,
		    width: 160
		});
		
		var nombrePlan = new Ext.form.TextField({
		    fieldLabel: 'Plan',
		    name:'nombrePlan',
		    //value:_aseguradora,
		    readonly:true,
		    width: 160
		});
		
		
		var nombrePrima = new Ext.form.TextField({
		    fieldLabel: 'Prima total  Anual Contado',
		    name:'nombrePrima',
		    readonly:true,
		    width: 160
		});
		
		var conn = new Ext.data.Connection();
		var codeExt;

		/*alert("codigoAseguradora = " + rec.get('codigoAseguradora') + "  " +"descripcionAseguradora = " + rec.get('aseguradora') + "  " +"codigoProducto = " + rec.get('codigoProducto') + "  " +"descripcionProducto = " + rec.get('producto') + "  " +
		"fechaFormato = " + rec.get('fechaFormato') + "  " + "fecha = " + rec.get('fecha') + "  " + 	"codigoFormaPago = " + rec.get('codigoFormaPago') + "  " + "descripcionFormaPago = " + rec.get('formaPago') + "  " +	"prima = " + rec.get('prima') + "  " + 	"cdcia = " + rec.get('cdcia') + "  " +"estado = " + rec.get('estado') + "  " +			"nmpoliza = " + rec.get('nmpoliza') + "  " +"nmsituac = " + rec.get('nmsituac') + "  " +	"cdplan = " + rec.get('cdplan') + "  " +	"dsplan = " + rec.get('dsplan'));*/

		//Asignar valores para variables que se usarán como parametros para la url flujocotizacion/comprarCotizaciones.action
        tmpCodigoAseguradora = rec.get('codigoAseguradora');
        tmpCodigoProducto = rec.get('codigoProducto');
        tmpEstado = rec.get('estado');
        tmpNmpoliza = rec.get('nmpoliza');
        tmpNmsituac = rec.get('nmsituac');

		nombreAseguradora.setValue(rec.get('aseguradora'));
        nombrePlan.setValue(rec.get('dsplan'));
        nombrePrima.setValue(rec.get('prima'));
        
		
    	var storeCoberturas = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
    	    	url: _ACTION_OBTIENE_COBERTURAS
    		}),
    		reader: new Ext.data.JsonReader({
        		root:'listaCoberturas',
        		totalProperty: 'totalCount'
            	//id: 'nmObjeto'
        		},[
        		{name: 'cdGarant',		type: 'string',  mapping:'cdGarant'},
        		{name: 'dsGarant',		type: 'string',  mapping:'dsGarant'},
        		{name: 'sumaAsegurada',	type: 'string',  mapping:'sumaAsegurada'},
        		{name: 'deducible',		type: 'string',  mapping:'deducible'},
        		{name: 'cdCiaaseg',		type: 'string',  mapping:'cdCiaaseg'},
        		{name: 'cdRamo',		type: 'string',  mapping:'cdRamo'}
        	]),
            remoteSort: true,
    		baseParams : [ 
				{ cdunieco :' '}, 
				{ cdramo :' '},
				{ estado :' '},
				{ nmpoliza :' '},
				{ nmsituac :' '},
				{ nmsuplem :' '},
				{ cdplan :' '},
				{ cdcia :' '}
			],
    		sortInfo : {
    			field :'dsGarant', direction :'ASC'
    		}
    	});

    	storeCoberturas.baseParams['cdunieco'] = rec.get('codigoAseguradora');
        storeCoberturas.baseParams['cdramo'] = rec.get('codigoProducto');
        storeCoberturas.baseParams['estado'] = rec.get('estado');
        storeCoberturas.baseParams['nmpoliza'] = rec.get('nmpoliza');       //   '11863';//rec.get('nmpoliza');//
        storeCoberturas.baseParams['nmsituac'] = rec.get('nmsituac');    //'5';//rec.get('nmsituac');//
		storeCoberturas.baseParams['nmsuplem'] = rec.get('nmsuplem');//'';
        storeCoberturas.baseParams['cdplan'] = rec.get('cdplan');
		storeCoberturas.baseParams['cdcia'] = rec.get('cdcia');
        storeCoberturas.load({
        	params:{start:0, limit:itemsPerPage},
        	callback : function(r,options,success) {
            	//Ext.MessageBox.alert('TotalCount:',storeCoberturas.getTotalCount());
            }//callback 
		});

 
	    conn.request ({
			url: _ACTION_OBTIENE_TVALOSIT_COTIZA,
			method: 'POST',
			successProperty : '@success',
			params : {
				cdunieco : rec.get('codigoAseguradora'),
				nmpoliza : rec.get('nmpoliza'),
				cdramo : rec.get('codigoProducto'),
				estado : rec.get('estado')
			},
			callback: function (options, success, response) {
				if (Ext.util.JSON.decode(response.responseText).success == false) {
					Ext.Msg.alert('Error', 'No hay resultados de marca, modelo,descripcion');
				} else {
					nombreMarca.setValue(Ext.util.JSON.decode(response.responseText).marca);
					nombreModelo.setValue(Ext.util.JSON.decode(response.responseText).modelo);
					descripcion.setValue(Ext.util.JSON.decode(response.responseText).descripciondsatribut);
				}
			}
	    });
	    
    	//Variable tipo hidden para evitar la excepcion de la pantalla si el pl no trae datos.
    	var sinDatos = new Ext.form.Hidden({
    		id:'sinDatos', name:'SinDatos'
    	});
    	//Si el Pl no trae datos se mostrara un mensaje y se pintara un hidden.
    	if (codeExt==""){
    		Ext.Msg.show({ title:'Datos Rol', msg: 'No se encontraron datos.', buttons: Ext.Msg.OK, icon: Ext.MessageBox.INFO });
    		codeExt = sinDatos;
    	}
    	
    	//*************************************************************
    	//** FormPanel de Ventana de Detalle
    	var detalleFormPanel =  new Ext.form.FormPanel({                          
    		id:'detalleFormPanel',
    		//url:'?????????????????',
    		border:false,
    		layout:'form',
    		autoHeight : true,
    		items: [{
    			border:false,
    			layout:'form',
    			bodyStyle:'margin-top: 0px; margin-left: 5px;',
    			//width:500,
    			items:[
    			       {
                        layout:'column', 
                        border:true,
                        //width:540,
                        items:[{                                            
                                columnWidth:.5,
                                border:false,
                                layout:'form',
                                items:[
                                       nombreAseguradora,
                                       nombrePlan
                                      ]  
                               },
                               {                                         
                                columnWidth:.5,
                                border:false,
                                layout:'form',
                                items:[
                                         nombreMarca,
                                         nombreModelo,
                                         descripcion
                                      ]
                              }]
                       }
            		,
            		{
    				xtype: 'grid',
    				frame:true,
    				id:'gridDetalle',
    				store: storeCoberturas,
    				stripeRows: true,
    				cm:new Ext.grid.ColumnModel([
    					{
    						header: "cdGarantia",
    						dataIndex:'cdGarant',
    						//sortable:true,
    						width:20,
    						id:'cdatribu',
    						hidden:true
    					},{
    						header: "Nombre",
    						dataIndex:'dsGarant',
    						sortable:false,
    						//renderer : crearHLink,
    						width:300
    					},{
    						header: "Suma Asegurada",
    						dataIndex:'sumaAsegurada',
    						sortable:false,
    						width:200
    					},{
    						header: "Deducible",
    						dataIndex:'deducible',
    						sortable:false,
    						width:100
    					}
    				])
    				,
    				buttonAlign : 'center',
    				autoHeight : true,
    				width:600,
    				buttons: [
    					{
    						id: 'btnComprarCotizaciones',
    						text:'Comprar',
    						tooltip: 'Ir a Comprar Cotizaciones',
    						handler: function() {
    							window.location.replace( _ACTION_COMPRAR_COTIZACIONES+ "&cdCiaaseg="+tmpCodigoAseguradora+"&cdRamo="+tmpCodigoProducto+"&estado="+tmpEstado+"&nmPoliza="+tmpNmpoliza+"&numeroSituacion="+tmpNmsituac ); 
    		           		}
    		      		},{
    						text:'Regresar',
    						tooltip: 'Regresar a Consulta de Cotizaci&oacute;n',
    						handler: function() {
    							windowDetalle.close();
    		           		}
    		      		}
    		      	],
    				viewConfig: {autoFill: true,forceFit:true}, 
    				sm: new Ext.grid.RowSelectionModel({
    					singleSelect: false,
    					listeners: {                            
    		            	rowselect: function(sm, row, rec) { 
		            			////
    						}
    					}
    				}),
    				bbar: new Ext.PagingToolbar({
    		     		pageSize: 20,
    		     		store: storeCoberturas,
    		     		displayInfo: true,
    		     		displayMsg: 'Displaying rows {0} - {1} of {2}',
    		     		emptyMsg: "No rows to display"
    				})
    			},
    			{
                  layout:'column',
                  border:false,
                  labelAlign:'right',
                  width: '500',
                  items:[
                         {
                           columnWidth:.40,
                           layout:'form',                            		
                           border:'true',
                           labelAlign:'right'
                        
                         },                         
                         {
                           columnWidth:.60,
                           layout:'form',                            		
                           border:'true',
                           items:[nombrePrima]
                         }                                  
                        ]
    			}	
            ] 
    		},{
                //title :'<span style="color:#98012e;font-size:12px;font-family:Arial,Helvetica,sans-serif;">Rol de la P&oacute;liza</span>',
                labelWidth: 250, 
                labelAlign: 'right',         
                layout: 'form',
                frame: true,
                items: codeExt
				////items: [{"allowBlank":false,"disabled":true,"fieldLabel":"MODELO","hiddeParent":true,"hidden":false,"id":"B5B_C1_A+","labelSeparator":":","maxLengthText":4,"minLengthText":1,"name":"parameters.B5B_C1_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"MARCA","hiddeParent":true,"hidden":false,"id":"B5B_C2_A+","labelSeparator":":","maxLengthText":3,"minLengthText":1,"name":"parameters.B5B_C2_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"TIPO","hiddeParent":true,"hidden":false,"id":"B5B_C3_A+","labelSeparator":":","maxLengthText":2,"minLengthText":1,"name":"parameters.B5B_C3_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"CLASE","hiddeParent":true,"hidden":false,"id":"B5B_C4_A+","labelSeparator":":","maxLengthText":2,"minLengthText":1,"name":"parameters.B5B_C4_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"VALOR DEL VEHICULO","hiddeParent":true,"hidden":false,"id":"B5B_C5_A+","labelSeparator":":","maxLengthText":10,"minLengthText":1,"name":"parameters.B5B_C5_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"TIPO DE VALOR","hiddeParent":true,"hidden":false,"id":"B5B_C6_A+","labelSeparator":":","maxLengthText":5,"minLengthText":1,"name":"parameters.B5B_C6_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"% VALOR","hiddeParent":true,"hidden":false,"id":"B5B_C7_A+","labelSeparator":":","maxLengthText":3,"minLengthText":1,"name":"parameters.B5B_C7_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"ESTADO DE PLACAS","hiddeParent":true,"hidden":false,"id":"B5B_C8_A+","labelSeparator":":","maxLengthText":5,"minLengthText":1,"name":"parameters.B5B_C8_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"NUMERO DE SERIE","hiddeParent":true,"hidden":false,"id":"B5B_C9_A+","labelSeparator":":","maxLengthText":27,"minLengthText":1,"name":"parameters.B5B_C9_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"SERVICIO","hiddeParent":true,"hidden":false,"id":"B5B_C10_A+","labelSeparator":":","maxLengthText":1,"minLengthText":1,"name":"parameters.B5B_C10_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"USO","hiddeParent":true,"hidden":false,"id":"B5B_C11_A+","labelSeparator":":","maxLengthText":5,"minLengthText":1,"name":"parameters.B5B_C11_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"AFIANZADORA","hiddeParent":true,"hidden":false,"id":"B5B_C12_A+","labelSeparator":":","maxLengthText":5,"minLengthText":1,"name":"parameters.B5B_C12_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"MONTO DE FIANZA","hiddeParent":true,"hidden":false,"id":"B5B_C13_A+","labelSeparator":":","maxLengthText":10,"minLengthText":1,"name":"parameters.B5B_C13_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"SERVICIOS DE ASISTENCIA","hiddeParent":true,"hidden":false,"id":"B5B_C14_A+","labelSeparator":":","maxLengthText":5,"minLengthText":1,"name":"parameters.B5B_C14_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"NUMERO DE MOTOR","hiddeParent":true,"hidden":false,"id":"B5B_C15_A+","labelSeparator":":","maxLengthText":20,"minLengthText":1,"name":"parameters.B5B_C15_A+","width":200,"xtype":"textfield"}, {"allowBlank":false,"disabled":true,"fieldLabel":"PLACA DEL VEHICULO","hiddeParent":true,"hidden":false,"id":"B5B_C16_A+","labelSeparator":":","maxLengthText":10,"minLengthText":1,"name":"parameters.B5B_C16_A+","width":200,"xtype":"textfield"}]
            }
    	  ]
    	});//end FormPanel

    	
    	var windowDetalle = new Ext.Window({
    		plain:true,
    		id:'windowDetalle',
    		width: 620,
            autoHeight : true,
            //height:400,
            title: 'Detalle de Cotizaci&oacute;n',
            minWidth: 620,
            //minHeight: 400,
            layout: 'fit',
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
           // closeAction:'hide',
            //modal: false,
            closable : true,
            items: detalleFormPanel
        });
        windowDetalle.show();
        
	}//end muestraDetalle()
	
