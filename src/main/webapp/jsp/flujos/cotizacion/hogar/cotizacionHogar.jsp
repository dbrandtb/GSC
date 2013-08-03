<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Consulta de datos de Cotizacion</title>

<script type="text/javascript">
/*
 * Ext JS Library 2.1
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.onReady(function(){
    Ext.QuickTips.init();
	var cmCotizacion;
	var storeClaves;
	var gridEditableClaves;
	var Plant;
	var idFilaClaveAnterior;

	var cmAtributos;
	var storeAtributos;
	var gridEditableAtributos;
	var contenidoSumaAseg;
	var formPanelEditable;
	var tipoVivienda;
	var tipo;
	var pisos;
	var seleccionarCheck;

	var primas;
	var mensual;
	var anual;

	var aseguradora;
	var inmuebleSumaAseg;
	var selIndex;
       
	var windowConsulta;
	var fm = Ext.form;


	var myData = [
        ['1','Si'],
        ['0','No']       
    ];
	var storeIncluir = new Ext.data.SimpleStore({
        fields: [
           {name: 'codigo'},
           {name: 'descripcion'},
        ]
    });
    
    storeIncluir.loadData(myData);
    cmCotizacion = new Ext.grid.ColumnModel([{
           id:'descripcionClave1',
           header: "Cobertura",
           dataIndex: 'dsGarant',
           width: 200
        },{
           header: "Tipo",
           dataIndex: 'dsTipsit',
           width: 80
        },{
           header: "Incluir",
           dataIndex: 'swOblig',
           width: 80,
           
           editor: new fm.ComboBox({
          		typeAhead: true,
               	triggerAction: 'all',
                displayField:'descripcion',
                valueField: 'descripcion',
                typeAhead: true,
                mode: 'local',
                store:storeIncluir,
                forceSelection:true,
                triggerAction: 'all',
                emptyText:'Seleccione...',
                selectOnFocus:true,
				hideLabel:true
            })
           
        },{
           header: "Suma Asegurada",
           dataIndex: 'sumaAseg',
           width: 80,
           editor: new fm.NumberField({
           })
        },{
           header: "Prima",
           dataIndex: 'mnPrima',
           width: 80
        }
    ]);
    
    
    cmCotizacion.defaultSortable = false;
    
    Plant = Ext.data.Record.create([

           {name: 'identificador', 		type: 'string'},
           {name: 'dsGarant', 			type: 'string'},
           {name: 'dsTipsit', 			type: 'string'},
           {name: 'swOblig', 			type: 'string'},          
           {name: 'sumaAseg', 			type: 'string'},   
           {name: 'mnPrima', 			type: 'string'}         
      ]);

    
    storeClaves = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url:'<s:url namespace="flujocotizacion" action="cotizacionHogar" includeParams="none"/>'
    
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaResultadoCotizacionhogar',
            id: 'id-undefined-mine'
            }, Plant)
     
    });
    gridEditableClaves = new Ext.grid.EditorGridPanel({
			id:'grid-editable-claves',
        	store: storeClaves,
        	cm: cmCotizacion,
        	autoScroll:true,
        	width:620,
        	height:200,
        	autoExpandColumn:'sumaAseg',
        	frame:true,
        	clicksToEdit:1,
        	viewConfig: {
				forceFit: true
        	},
        	sm: new Ext.grid.RowSelectionModel({
            	singleSelect: true,
            	listeners: {}
        		})
    	});

 
    storeClaves.load();
	tipoVivienda = new Ext.form.TextField({
				id:'tipoVivienda-editable',
                fieldLabel: 'Tipo de Vivienda',
                labelSeparator:'',
                width:'80%'  ,
                disabled:true,                    
                blankText : 'Tipo de Vivienda',
                name:'tipoVivienda' 
        });     

    tipo = new Ext.form.TextField({
                id:'id-tipo-editable',  
                fieldLabel: 'Tipo',
                labelSeparator:'',                    
                width:'80%'  ,
                disabled:true,                               
                name:'tipo' 
        });     

	inmuebleSumaAseg = new Ext.form.TextField({
				id:'inmuebleSumaAseg-editable',
                fieldLabel: 'Inmueble (Suma Asegurada)',
                labelSeparator:'',                    
                width:'80%' ,
                disabled:true  ,
                name:'inmuebleSumaAseg'
        }); 
	contenidoSumaAseg = new Ext.form.TextField({
				id:'contenidoSumaAseg-editable',
				fieldLabel: 'Contenido (Suma Asegurada)',
				labelSeparator:'',                    
				width:'80%' ,
				disabled:true  ,
				name:'contenidoSumaAseg'
        });     
	pisos = new Ext.form.TextField({
				id:'id-pisos-editable',
				fieldLabel: 'Numero de Pisos',
				labelSeparator:'',                    
				width:'80%' ,
				disabled:true  ,
				name:'pisos'
        });       
	aseguradora = new Ext.form.TextField({
				id:'id-aseguradora-editable',
				fieldLabel: 'Aseguradora',
				labelSeparator:'',                    
				width:'90%' ,
				disabled:true  ,
				name:'aseguradora'
        });         
	seleccionarCheck = new Ext.form.Checkbox({
                id:'id-obligatorio-editable',
                name:'obligatorio',
                fieldLabel: 'Seleccionar',
                labelSepartor:'', 
                checked:false,
                onClick:function(){
					if(this.getValue()){
						this.setRawValue("1");                              
					}else{
						this.setRawValue("0");
						}
					}
		});
            
	primas = new Ext.form.TextField({
				fieldLabel: 'Primas',
				labelSeparator:'',                    
				width:'80%'  ,
				//disabled:true,
				readOnly:true,                               
				name:'primas',
				value:'Prima Total' 
        });     
	mensual = new Ext.form.TextField({
				fieldLabel: 'Mensual',
				labelSeparator:'',                    
				width:'80%'  ,
				readOnly:true,
				//disabled:true,                               
				name:'mensual'              
        });     
	anual  = new Ext.form.TextField({  
                fieldLabel: 'Anual',
                labelSeparator:'',                    
                width:'80%',
                readOnly:true,
                //disabled:true,                               
                name:'anual' 
        });     
     

	formPanelEditable = new Ext.FormPanel({
    	autoScroll:true,
        id:'panel-editable-grids',
        frame:true,
        region:'center',
        bodyStyle:'padding:5px 5px 0',
        url:'<s:url namespace="flujocotizacion" action="guardaValores" includeParams="none"/>',
        width:650,
        title:'Resultados Cotizacion / Hogar',
        border:false,
        items:[{
        		layout:'form',
                border:true,
                 buttonAlign:'left',
                width: '620',
               	items:[{
                		layout:'column',
                    	border:true,
                    	width: '430',
                    	items: [{
                            	columnWidth:.20,
                            	labelAlign: 'right',
                            	layout:'form',
                            	border:false                          
                        	},{
                            	columnWidth:.80,
                            	labelAlign: 'rigth',
                            	layout:'form',
                            	labelWidth:170,
                            	border:false,
                            	items:[
                                		tipoVivienda,
                                		tipo,
                                		inmuebleSumaAseg,
                                		contenidoSumaAseg,
                                		pisos
                                	]
                        	}]
                	},{
                		layout:'form',
                		border:false,
                		frame:true,
                		width: '620',
                		items:[{
                    			layout:'column',
                    			border:false,
                    			width: '430',
                    			items: [{
                            			columnWidth:.7,
                            			layout:'form',
                            			border:false,
                            			items:[aseguradora]
                        				},{
                            			columnWidth:.3,
                            			layout:'form',
                            			border:false,
                            			items:[seleccionarCheck]
                        			}]
                			}]          
            		},gridEditableClaves,
            		{
                		layout:'form',
                		border:false,
                		frame:true,
                		width: '620',
                		items:[{
                    			layout:'column',
                    			border:false,
                    			width: '620',
                    			items: [{
                            			columnWidth:.3,
                            			layout:'form',
                            			labelAlign:'top',
                            			border:false
                        				},{
                            			columnWidth:.36,
                            			layout:'form',
                            			labelAlign:'top',
                            			border:false,
                            			items:[
                            			primas,
                						{xtype:'button', 
                       					text: 'Recalcular', 
                       					name: 'AgregarCondicion', 
                       					buttonAlign: "right", 
                       					handler: function() {
                       						var recs = storeClaves.getModifiedRecords();
                       						params="";                
       										for (var i=0; i<recs.length; i++) {        
        										params = params +"listaCotizacionModificada[" + i + "].swOblig=" + recs[i].get('swOblig') + 
           										"&&listaCotizacionModificada[" + i + "].sumaAseg=" + recs[i].get('sumaAseg')+ "&&";
       											}
       										var connect = new Ext.data.Connection();
											connect.request ({                  
                									url:'<s:url namespace="flujocotizacion" action="obtieneModificados" includeParams="none"/>',
                  									method: 'POST',
                  									successProperty : '@success',
                  									params : params,
                    								callback: function (options, success, response) {
													}
												});
                       						}
                       					}]
                        				},{
                            			columnWidth:.17,
                            			layout:'form',
                            			labelAlign:'top',
                            			border:false,
                            			items:[mensual]
                        				},{
                            			columnWidth:.17,
                            			layout:'form',
                            			labelAlign:'top',
                            			border:false,
                            			items:[anual]
                        			  }]
                			}]          
            		}],
            		buttons:[{        							
        						text:'Comprar',	
        						handler: function() {}	       							        			
        					},{
        						text:'Cancelar',
        						handler: function() {
        							//window.location.replace("/load.action");
        						}
        					},{
        					text:'Imprimir',
        						handler: function() {}
        					},{
        						text:'Enviar correo',
        						handler: function() {}
        					}]
		}]     
    });
    
    
	function cargaSumas(){
 	var params="";
		var conn = new Ext.data.Connection();
				conn.request ({                  
                	url:'<s:url namespace="flujocotizacion" action="cargarSumaTotal" includeParams="none"/>',
                  	method: 'POST',
                  	successProperty : '@success',
                  	params : params,
                    	callback: function (options, success, response) {                 
                        	mensual.setValue(Ext.util.JSON.decode(response.responseText).sumaMensual);
                            anual.setValue(Ext.util.JSON.decode(response.responseText).sumaAnual);
						}
					}); 
	}
    cargaSumas();
	formPanelEditable.render("algo");

});

</script>

</head>
<body>

<div id="algo"></div>

</body>
</html>