var helpMap = new Map();

Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";



 var cm = new Ext.grid.ColumnModel([
        {        
        header: "cdUniEco",
        dataIndex: 'cdUniEco',
        hidden :true,
        sortable:true
        },{
        header: "cdRamo",
        dataIndex: 'cdRamo',
        hidden :true,
        sortable:true
        },{
        header: "cdPlan",
        dataIndex: 'cdPlan',
        hidden :true,
        sortable:true
        },{
        id:'cmDsUniEcoCarritoComprasFinalizarId',
        header: getLabelFromMap('cmDsUniEcoCarritoComprasProductosId',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('cmDsUniEcoCarritoComprasProductosId', helpMap,'Aseguradora'), 
        //header: "Aseguradora",
        dataIndex: 'dsUniEco',
        width: 120,
        sortable:true
        },{
        id:'cmDsRamoCarritoComprasFinalizarId',
        header: getLabelFromMap('cmDsRamoCarritoComprasFinalizarId',helpMap,'Producto'),
        tooltip: getToolTipFromMap('cmDsUniEcoCarritoComprasProductosId', helpMap,'Producto'), 
        //header: "Producto",
        dataIndex: 'dsRamo',
        width: 120,
        sortable:true
        },{
        id:'cmDsPlanCarritoComprasFinalizarId',
        header: getLabelFromMap('cmDsPlanCarritoComprasFinalizarId',helpMap,'Plan'),
        tooltip: getToolTipFromMap('cmDsPlanCarritoComprasFinalizarId', helpMap,'Plan'), 
        //header: "Plan",
        dataIndex: 'dsPlan',
        width: 120,
        sortable:true
        },{
        id:'cmFeInicioCarritoComprasFinalizarId',
        header: getLabelFromMap('cmFeInicioCarritoComprasFinalizarId',helpMap,'Inicio'),
        tooltip: getToolTipFromMap('cmFeInicioCarritoComprasFinalizarId', helpMap,'Inicio'), 
        //header: "Inicio",
        dataIndex: 'feInicio',
        width: 120,
        sortable:true,
        format:'d/m/Y'
        },{
        id:'cmFeEstadoCarritoComprasFinalizarId',
        header: getLabelFromMap('cmFeEstadoCarritoComprasFinalizarId',helpMap,'Fin'),
        tooltip: getToolTipFromMap('cmFeEstadoCarritoComprasFinalizarId', helpMap,'Fin'), 
        header: "Fin",
        dataIndex: 'feEstado',
        width: 120,
        sortable:true
        },{
        id:'cmMnTotalpCarritoComprasFinalizarId',
        header: getLabelFromMap('cmMnTotalpCarritoComprasFinalizarId',helpMap,'Prima Total'),
        tooltip: getToolTipFromMap('cmMnTotalpCarritoComprasFinalizarId', helpMap,'Prima Total'), 
        header: "Prima Total",
        dataIndex: 'mnTotalp',
        width: 120,
        sortable:true
    }]);


 function test(){

                store = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_OBTENER_PRODUCTOS_CARRITO_COMPRAS
                }),
                reader: new Ext.data.JsonReader({
            	root:'mCarritoComprasManagerList',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
	        {name: 'cdUniEco',  type: 'string',  mapping:'cdUniEco'},
	        {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
	        {name: 'cdPlan',  type: 'string',  mapping:'cdPlan'},
	        {name: 'dsUniEco',  type: 'string',  mapping:'dsUniEco'},
	        {name: 'dsRamo',  type: 'string',  mapping:'dsRamo'},
	        {name: 'dsPlan',  type: 'string',  mapping:'dsPlan'},
	        {name: 'feInicio',  type: 'string',  mapping:'feInicio'},
	        {name: 'feEstado',  type: 'string',  mapping:'feEstado'},
	        {name: 'mnTotalp',  type: 'string',  mapping:'mnTotalp'}
			])
			});

       return store;
 	}


 	
//READER DE DATOS DE LAS PERSONAS
var readerFormato = new Ext.data.JsonReader(
		{
			root:'aotEstructuraList',
			totalProperty: 'totalCount',
			successProperty : 'success'
		},
		[
		{name: 'desOrden',  mapping:'nmOrden',  type:'string'},
		{name: 'contratante',  mapping:'nombreContratante',  type:'string'}
		]
	);

//READER DE DIRECCION 
var readerFormatoDire = new Ext.data.JsonReader(
		{
			root:'MCarritoComprasManagerListDire',
			totalProperty: 'totalCount',
			successProperty : 'success'
		},
		[
		{name: 'cdColoni',  mapping:'cdColoni',  type:'string'},
		{name: 'cdEdo',  mapping:'cdEdo',  type:'string'},
		{name: 'cdMunici', mapping:'cdMunici', type:'string'},	
		{name: 'cdPais',   mapping:'cdPais',   type:'string'},
		{name: 'cdTipDom',   mapping:'cdTipDom',   type:'string'},
		{name: 'dsColoni',   mapping:'dsColoni',   type:'string'},
		{name: 'dsDomici',   mapping:'dsDomici',   type:'string'},
		{name: 'dsEdo',  mapping:'dsEdo',  type:'string'},
		{name: 'dsMunici',  mapping:'dsMunici',  type:'string'},
		{name: 'dsPais', mapping:'dsPais', type:'string'},	
		{name: 'dsTipDom',   mapping:'dsTipDom',   type:'string'},
		{name: 'nmNumInt',   mapping:'nmNumInt',   type:'string'},
		{name: 'nmNumero',   mapping:'nmNumero',   type:'string'},
		{name: 'nmOrdDom',   mapping:'nmOrdDom',   type:'string'}		
		]
	);	

//##########################################################################
//COMBOS STORE DE DIRECCCION
var readerTipoDomicilio = new Ext.data.JsonReader( {
            root : 'comboTiposDomicilio',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);

       var dsTipoDomicilio = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPO_DOMICILIO
            }),
            reader: readerTipoDomicilio
        });

        var readerPaises = new Ext.data.JsonReader( {
            root : 'comboPaises',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);
		var dsPaises = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PAISES
            }),
            reader: readerPaises
        }); 

        var readerEstados = new Ext.data.JsonReader( {
            root : 'comboEstados',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);
		var dsEstados = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_ESTADOS
            }),
            reader: readerEstados
        }); 

         var readerMunicipios = new Ext.data.JsonReader( {
            root : 'comboMunicipios',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);
		var dsMunicipios = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_MUNICIPIOS
            }),
            reader: readerMunicipios
        }); 

        var readerColonias = new Ext.data.JsonReader( {
            root : 'comboColonias',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'codigo',
            mapping : 'codigo',
            type : 'string'
        }, {
            name : 'descripcion',
            type : 'string',
            mapping : 'descripcion'
        }]);
		var dsColonias = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_COLONIAS
            }),
            reader: readerColonias
        });

        
         var readerInstrumentosPago = new Ext.data.JsonReader( {
            root : 'comboInstrumentosPago',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'cdForPag',
            mapping : 'cdForPag',
            type : 'string'
        }, {
            name : 'dsForPag',
            type : 'string',
            mapping : 'dsForPag'
        }, {
            name : 'cdMuestra',
            type : 'string',
            mapping : 'cdMuestra'    
        }]);
		var dsInstrumentosPago = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_INSTRUMENTOS_PAGO
            }),
            reader: readerInstrumentosPago
        });
        
	
        var readerTiposTarjetas = new Ext.data.JsonReader( {
            root : 'comboTiposTarjetas',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'cdTiTarj',
            mapping : 'cdTiTarj',
            type : 'string'
        }, {
            name : 'dsTiTarj',
            type : 'string',
            mapping : 'dsTiTarj'
        }]);
        
	 var dsTiposTarjetas = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPOS_TARJETAS
            }),
            reader: readerTiposTarjetas
        });
        
	 var readerBancos = new Ext.data.JsonReader( {
            root : 'comboBancos',
            totalProperty: 'totalCount',
            successProperty : '@success'

        }, [ {
            name : 'cdBanco',
            mapping : 'cdBanco',
            type : 'string'
        }, {
            name : 'dsNombre',
            type : 'string',
            mapping : 'dsNombre'
        }]);
	 var dsBancos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_BANCOS
            }),
            reader: readerBancos
        });
        
           
        var readerPagar = new Ext.data.JsonReader(
		{
			root:'MCarritoComprasManagerListDetOrden',
			totalProperty: 'totalCount',
			successProperty : 'success'
		},
		[
		{name: 'cdBanco',  mapping:'cdBanco',  type:'string'},
		{name: 'dsNombre',  mapping:'dsNombre',  type:'string'},
		{name: 'cdPerson', mapping:'cdPerson', type:'string'},	
		{name: 'dsForPag',   mapping:'dsForPag',   type:'string'},
		{name: 'cdTitArg',   mapping:'cdTitArg',   type:'string'},		
		{name: 'nmTarj',   mapping:'nmTarj',   type:'string'},
		{name: 'feVence',   mapping:'feVence',   type:'string'}
		]);
         
		var dsPagar = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_DET_ORDEN
            }),
            reader: readerPagar,
              baseParams: {
					         cdCarro:'1',
					         cdUsuari:'RZ',
					         cdPerson:'1000'
					      }
        });
        
        
        
        var readerMontos = new Ext.data.JsonReader(
		{
			root:'MCarritoComprasManagerListDetOrden',
			totalProperty: 'totalCount',
			successProperty : 'success'
		},
		[
		{name: 'cdBanco',  mapping:'cdBanco',  type:'string'},
		{name: 'dsNombre',  mapping:'dsNombre',  type:'string'},
		{name: 'cdPerson', mapping:'cdPerson', type:'string'},	
		{name: 'dsForPag',   mapping:'dsForPag',   type:'string'},
		{name: 'cdTitArg',   mapping:'cdTitArg',   type:'string'},		
		{name: 'nmTarj',   mapping:'nmTarj',   type:'string'},
		{name: 'feVence',   mapping:'feVence',   type:'string'}
		]);
         
		var dsMontos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_MONTOS_CARRITO_COMPRAS
            }),
            reader: readerMontos,
              baseParams: {
					         cdCarro:CDCARRO,
					         cdUsuari:CDUSUARI,
					         cdPerson:CDPERSON
					      }
        });
        
        
        
        
//##########################################################################
// DATOS DE LAS PERSONAS
var contratante = new Ext.form.TextField({
		id: 'contratante', 
		fieldLabel: getLabelFromMap('contratante',helpMap,'Contratante'),
		tooltip: getToolTipFromMap('contratante',helpMap,'Contratante'),
        //fieldLabel: 'Contratante', 
        //id: 'contratante', 
        name: 'contratante',
        allowBlank: true,
        readOnly: true,
        width:270
    });

var nmOrden = new Ext.form.TextField({
		id: 'nmOrden', 
		fieldLabel: getLabelFromMap('nmOrden',helpMap,'Orden'),
		tooltip: getToolTipFromMap('nmOrden',helpMap,'Orden'),
        name: 'nmOrden',
        allowBlank: true,
        readOnly: true
    });

//##########################################################################
//DIRECCION
       var recordDomicilios = new Ext.data.Record.create([ {
		            name : 'cdTipDom',
		            type : 'string',
		            mapping : 'cdTipDom'
		        }, {
		            name : 'cdColoni',
		            type : 'string',
		            mapping : 'dsColoni'
		        }, {
		            name : 'dsDomici',
		            type : 'string',
		            mapping : 'dsDomici'
		        }, {
		            name : 'cdEdo',
		            type : 'string',
		            mapping : 'dsEdo'
		        }, {
		            name : 'cdEdo1',
		            type : 'string',
		            mapping : 'cdEdo'
		        }, {
		            name : 'cdMunici',
		            type : 'string',
		            mapping : 'dsMunici'
		        }, {
		            name : 'cdPais',
		            type : 'string',
		            mapping : 'dsPais'
		        }, {
		            name : 'dsTipDom',
		            type : 'string',
		            mapping : 'dsTipDom'
		        },{
		            name : 'nmNumInt',
		            type : 'string',
		            mapping : 'nmNumInt'
		        },{
		            name : 'nmNumero',
		            type : 'string',
		            mapping : 'nmNumero'
		        },{
		            name : 'nmOrdDom',
		            type : 'string',
		            mapping : 'nmOrdDom'
		        }]);
		        
		

		 var readerEncOrden = new Ext.data.JsonReader({
            root : 'MCarritoComprasManagerListEncOrden',
            totalProperty: 'totalCount',
            successProperty : '@success'
		    },
           [{
            	name : 'nmOrden',
            	mapping : 'nmOrden',
            	type : 'string'
            },{
            	name : 'nombreContratante',
            	type : 'string',
            	mapping : 'contratante'
            }]);
        
		var dsEncOrden = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_ENC_ORDEN
            }),
            reader: readerEncOrden,
              baseParams: {
					         cdCarro:CDCARRO	
					      }
        });
		        
	function crearGridDomciliosStore(){
		        var readerDomicilio = new Ext.data.JsonReader( {
		            root : 'MCarritoComprasManagerListDire',
		            totalProperty: 'totalCount',
		            successProperty : '@success'
		
		        },
		        recordDomicilios 
		        );        
		        var dsDomicilio = new Ext.data.Store ({
		            proxy: new Ext.data.HttpProxy({
		                url: _ACTION_OBTENER_DOMICILIOS
		            }),
		            reader: readerDomicilio,
		            baseParams: {
					cdContra: CDCONTRA,
       	            cdTipDom: CDTIPDOM
					
					}
		        });
		
				return dsDomicilio;
		 	}
		 	
		 	
var calle = new Ext.form.TextField({
        id: 'calle', 
        name: 'calle',
        allowBlank: false,
        disabled:true
    });

var numero = new Ext.form.TextField({
        id: 'numero', 
        name: 'numero',
        allowBlank: false,
        disabled:true
    });

    var comboPais = new Ext.form.ComboBox({
    		tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
		    id:'comboPaisCarritoComprasFinalizarId',
            fieldLabel: getLabelFromMap('comboPaisCarritoComprasFinalizarId',helpMap,'Pais'),
            tooltip: getToolTipFromMap('comboPaisCarritoComprasFinalizarId',helpMap,'Pais'),
			store: dsPaises, 
			width: 100, 
			displayField:'descripcion', 
			valueField: 'codigo', 
			hiddenName: 'CodigoPais', 
			name: 'codigoPais',
			typeAhead: true, 
			triggerAction: 'all', 
			lazyRender:   true, 
			emptyText:'Seleccione Pais...', 
			selectOnFocus:true,
			//labelSeparator:'', 
			//fieldLabel: 'Pais',
			width: 100,
			disabled:true,
			forceSelection:true,
			onSelect: function (record) {
								this.setValue(record.get('codigo'));
			                    this.collapse();
			                    comboEstados.store.removeAll();
			                    comboMunicipio.store.removeAll();
			                    comboColonias.store.removeAll();
			                    comboEstados.store.load({
			                    					params: {codigoPais: record.get('codigo')}
			                                        });
			                 	comboEstados.setValue('');
			                    grillaDomicilios.getStore().getAt(0).set('cdEdo','');
			                    comboMunicipio.setValue('');
			                    grillaDomicilios.getStore().getAt(0).set('cdMunici','');
			                    comboColonias.setValue('');
			                    grillaDomicilios.getStore().getAt(0).set('cdColoni','');
					}
	});
			                  
	var comboEstados = new Ext.form.ComboBox({
			tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
		    id:'codEstado',
            fieldLabel: getLabelFromMap('codEstado',helpMap,'Estado'),
            tooltip: getToolTipFromMap('codEstado',helpMap,'Estado'),
			store: dsEstados, 
			width: 100, 
			displayField:'descripcion', 
			valueField: 'codigo', 
			hiddenName: 'CodigoEstado', 
			//id: 'codEstado', 
			name: 'codEstado',
			typeAhead: true, 
			triggerAction: 'all', 
			lazyRender:   true, 
			emptyText:'Seleccione Estado...', 
			selectOnFocus:true,
			//labelSeparator:'', 
			//fieldLabel: 'Estado',
			disabled:true,
			forceSelection:true,
			onSelect: function(record) {
					this.setValue(record.get('codigo'));
					this.collapse();
			        comboMunicipio.store.removeAll();
			        comboColonias.store.removeAll();
			        comboMunicipio.store.load({
			        params: {
	                		codigoPais: grillaDomicilios.getStore().getAt(0).get('cdPais'),
	                    	codigoEstado: record.get('codigo')
			               }
			       });
			       comboMunicipio.setValue('');
			       grillaDomicilios.getStore().getAt(0).set('cdMunici','');
			       comboColonias.setValue('');
			       grillaDomicilios.getStore().getAt(0).set('cdColoni','');
		 		},
			listeners: {
					focus: function () {
						this.store.reload({
			                 params: {codigoPais: grillaDomicilios.getStore().getAt(0).get('cdPais')},
			                 success: function () {
			                    	this.expand();
			                    	},
			                 failure: function () {
		                    		combo.store.removeAll();
		                    		combo.setValue('');
		                    		combo.setRawValue('');
			                    	}
			             });
					},
					expand: function (combo, record) {
			        		combo.store.reload({
			                    	params: {codigoPais: grillaDomicilios.getStore().getAt(0).get('cdPais')},
			                    	failure: function () {
			                    			combo.store.removeAll();
			                    			combo.setValue('');
			                    			combo.setRawValue('');
			                    		}
			                    	});
			        }
			    }
  });
			                  
	var comboMunicipio = new Ext.form.ComboBox({
			tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
		    id:'comboMunicipioCarritoComprasFinalizarId',
            fieldLabel: getLabelFromMap('comboMunicipioCarritoComprasFinalizarId',helpMap,'Municipio'),
            tooltip: getToolTipFromMap('comboMunicipioCarritoComprasFinalizarId',helpMap,'Municipio'),
			store: dsMunicipios, 
			width: 100, 
			displayField:'descripcion', 
			valueField: 'codigo', 
			hiddenName: 'CodigoPais', 
			name: 'codigoPais',
			typeAhead: true, 
			triggerAction: 'all', 
			lazyRender:   true, 
			emptyText:'Seleccione Municipio...', 
			selectOnFocus:true,
			//labelSeparator:'', 
			//fieldLabel: 'Municipio',
			disabled:true,
			forceSelection:true,
			onSelect: function (record) {
						this.setValue(record.get('codigo'));
						this.collapse();
						comboColonias.store.removeAll();
			            comboColonias.store.load({
			                    	params: {
                    						codigoPais:  grillaDomicilios.getStore().getAt(0).get('cdPais'),
                    						codigoEstado:grillaDomicilios.getStore().getAt(0).get('cdEdo'),
                    						codigoMunicipio: record.get('cdMunici')
			                    			}
			              });
			            comboColonias.setValue('');
			            grillaDomicilios.getStore().getAt(0).set('cdColoni','');
			},
			listeners: {
			      focus: function () {
         					this.store.reload({
			                params: {
						            codigoPais: grillaDomicilios.getStore().getAt(0).get('cdPais'),
						            codigoEstado: grillaDomicilios.getStore().getAt(0).get('cdEdo')
						       },
						       success: function () {
						       			this.expand();
						       			},
			                failure: function () {
			                    	combo.store.removeAll();
			                    	combo.setValue('');
			                    	combo.setRawValue('');
			                    }
			               	});
			           },
			     expand: function (combo, record) {
			             combo.store.reload({
			                   params: {
						              codigoPais: grillaDomicilios.getStore().getAt(0).get('cdPais'),
						              codigoEstado: grillaDomicilios.getStore().getAt(0).get('cdEdo')
						          },
			                   failure: function () {
			                    	  combo.store.removeAll();
			                    	  combo.setValue('');
			                    	  combo.setRawValue('');
			                    }
			              });
			      }
			}
       });
			                  
		var comboColonias = new Ext.form.ComboBox({
				tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			    id:'comboColoniasCarritoComprasFinalizarId',
                fieldLabel: getLabelFromMap('comboColoniasCarritoComprasFinalizarId',helpMap,'Colonias'),
                tooltip: getToolTipFromMap('comboColoniasCarritoComprasFinalizarId',helpMap,'Colonias'),
			    store: dsColonias, 
			    displayField:'descripcion', 
			    valueField: 'codigo', 
			    hiddenName: 'CodigColonia', 
			    name: 'codigoColonia',
			    typeAhead: true, 
			    triggerAction: 'all', 
			    lazyRender:   true, 
			    emptyText:'Seleccione Colonia...', 
			    selectOnFocus:true,
			    //labelSeparator:'',  
			    //fieldLabel: 'Colonias',
			    width: 100,
			    disabled:true,
			    forceSelection:true, 
			    listeners: {
			          focus: function () {
			          		this.store.reload({
			                    	params: {
			                    			codigoPais: grillaDomicilios.getStore().getAt(0).get('cdPais'),
			                    			codigoEstado:grillaDomicilios.getStore().getAt(0).get('cdEdo'),
			                    			codigoMunicipio: grillaDomicilios.getStore().getAt(0).get('cdMunici')
			                    			},
			                    	success: function () {
			                    					this.expand();
			                    					},
			                    	failure: function () {
		                    						combo.store.removeAll();
		                    						combo.setValue('');
		                    						combo.setRawValue('');
			                    					}
			                    	});
			                },
			          expand: function (combo, record){
			                    	combo.store.reload({
			                    		params: {
			                    				codigoPais: grillaDomicilios.getStore().getAt(0).get('cdPais'),
			                    				codigoEstado:grillaDomicilios.getStore().getAt(0).get('cdEdo'),
			                    				codigoMunicipio: grillaDomicilios.getStore().getAt(0).get('cdMunici')
			                    				},
			                    		failure: function () {
			                    				combo.store.removeAll();
			                    				combo.setValue('');
			                    				combo.setRawValue('');
			                    				}
			                    		});
			                  }
			         }
	      });
			                   
var comboDomicilios = new Ext.form.ComboBox({
			tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			id:'TipoDomicilio',
            fieldLabel: getLabelFromMap('TipoDomicilio',helpMap,'Tipo'),
            tooltip: getToolTipFromMap('TipoDomicilio',helpMap,'Tipo'),
			store: dsTipoDomicilio, 
			displayField:'descripcion', 
			valueField: 'codigo', 
			hiddenName: 'TipoDomicilio1',
			//id:'TipoDomicilio',
			typeAhead: true, 
			triggerAction: 'all', 
			lazyRender:   true, 
			emptyText:'Tipo.', 
			selectOnFocus:true,
			//labelSeparator:'', 
			//fieldLabel: 'Tipo',
			forceSelection:true,
			disabled:true,
			width:80
			})

			                    		
			                    		
var grillaDomicilios;

function createGridDomcilios(){
		//Definición Column Model
	    var cmDire = new Ext.grid.ColumnModel([
	    		{
	    			dataIndex: 'nmOrdDom',
	    			hidden: true
	    		},{
		           	id:'cmCdTipDomCarritoComprasCosultaFinalizarId',
        			header: getLabelFromMap('cmCdTipDomCarritoComprasCosultaFinalizarId',helpMap,'Tipo'),
        			tooltip: getToolTipFromMap('cmCdTipDomCarritoComprasCosultaFinalizarId', helpMap,'Tipo'),
		           	//header: "Tipo",
		           	dataIndex: 'cdTipDom',
	    			sortable: true,
		           	width: 80,
	           		resizable: true,
		            editor: comboDomicilios,
			        renderer: renderComboEditor(comboDomicilios)
	        	},{
		           	id:'cmDsDomiciCarritoComprasCosultaFinalizarId',
        			header: getLabelFromMap('cmDsDomiciCarritoComprasCosultaFinalizarId',helpMap,'Calle'),
        			tooltip: getToolTipFromMap('cmDsDomiciCarritoComprasCosultaFinalizarId', helpMap,'Calle'),        
		           	//header: "Calle",
		           	dataIndex: 'dsDomici',
		           	width: 60,
	    			sortable: true,
	           		resizable: true,
		           	//editor: new Ext.form.TextField({name: 'Calle', width: 100, maxLength: 50,id:'cdDom',disabled:true})
		           	editor: calle
		           	
	           	},{
		           	id:'cmNmNumeroCarritoComprasCosultaFinalizarId',
        			header: getLabelFromMap('cmNmNumeroCarritoComprasCosultaFinalizarId',helpMap,'N&uacute;mero'),
        			tooltip: getToolTipFromMap('cmNmNumeroCarritoComprasCosultaFinalizarId', helpMap,'N&uacute;mero'),        
		           	//header: "N&uacute;mero",
		           	dataIndex: 'nmNumero',
		           	width: 50,
	    			sortable: true,
	           		resizable: true,
		           	//editor: new Ext.form.NumberField({name: 'numeroExterno', width: 100, maxLength: 10})
		           	editor:numero
	           	},{
		           	id:'cmCdPaisCarritoComprasCosultaFinalizarId',
        			header: getLabelFromMap('cmCdPaisCarritoComprasCosultaFinalizarId',helpMap,'Pais'),
        			tooltip: getToolTipFromMap('cmCdPaisCarritoComprasCosultaFinalizarId', helpMap,'Pais'),        
	           		//header: 'Pa&iacute;s',
	           		dataIndex: 'cdPais',
		           	width: 100,
	    			sortable: true,
	           		resizable: true,
		           	editor: comboPais,
		           	renderer: renderComboEditor(comboPais)
	           		
	           	},
	           	{
		           	id:'cmCdEdoCarritoComprasCosultaFinalizarId',
        			header: getLabelFromMap('cmCdTipDomCarritoComprasCosultaFinalizarId',helpMap,'Estado'),
        			tooltip: getToolTipFromMap('cmCdTipDomCarritoComprasCosultaFinalizarId', helpMap,'Estado'),        
	           		//header: 'Estado',
	           		dataIndex: 'cdEdo',
		           	width: 90,
	    			sortable: true,
	           		resizable: true,
		           	editor: comboEstados,
		           	renderer: renderComboEditor(comboEstados)
	           	},{
		           	id:'cmCdMuniciCarritoComprasCosultaFinalizarId',
        			header: getLabelFromMap('cmCdMuniciCarritoComprasCosultaFinalizarId',helpMap,'Municipio'),
        			tooltip: getToolTipFromMap('cmCdMuniciCarritoComprasCosultaFinalizarId', helpMap,'Municipio'),        
	           		//header: 'Municipio',
	           		dataIndex: 'cdMunici',
		           	width: 90,
		           	sortable: true,
	           		resizable: true,
		           	editor: comboMunicipio,
		           	renderer: renderComboEditor(comboMunicipio)
	           	},
	           	{
		           	id:'cmCdColoniCarritoComprasCosultaFinalizarId',
        			header: getLabelFromMap('cmCdColoniCarritoComprasCosultaFinalizarId',helpMap,'Colonia'),
        			tooltip: getToolTipFromMap('cmCdColoniCarritoComprasCosultaFinalizarId', helpMap,'Colonia'),        
	           		//header: 'Colonia',
	           		dataIndex: 'cdColoni',
		           	width: 90,
		           	sortable: true,
	           		editor: comboColonias,
	           		resizable: true,
	           		renderer: renderComboEditor(comboColonias)
	           	},{
	           		header: 'N&uacute;m. Interno',
	           		dataIndex: 'nmNumInt',
		           	width: 70,
	    			sortable: true,
	           		resizable: true,
	           		hidden:true,
	           		editor: new Ext.form.NumberField({name: 'numeroInterno', width: 100, maxLength: 10})
	           	}
	           	]);
	           	
//Fin Definición Column Model
		 grillaDomicilios = new Ext.grid.EditorGridPanel({
		 id:'gridDomicilios',
		 store:crearGridDomciliosStore(),
		 clicksToEdit:1,
         cm: cmDire,
         buttons:[{
        		   id:'gridDomiciliosButtonModificarId',
        		   text:getLabelFromMap('gridDomiciliosButtonModificarId', helpMap,'Modificar'),
            	   tooltip:getToolTipFromMap('gridDomiciliosButtonModificarId', helpMap,'Modificar'),	
	               //text:'Modificar',
	               //tooltip:'Modificar direccion',
	               handler:function(){addGridDomicilioNewRecord()}
                  },{
        		   id:'gridDomiciliosButtonRestablecerId',
        		   text:getLabelFromMap('gridDomiciliosButtonModificarId', helpMap,'Restablecer'),
            	   tooltip:getToolTipFromMap('gridDomiciliosButtonModificarId', helpMap,'Restablecer'),	
	               //text:'Restablecer',
	               //tooltip:'Restablece la direccion inicial',
	               handler:function(){
	                                    grillaDomicilios.store.load();
	                                    calle.setDisabled(true);
									    numero.setDisabled(true);
									    comboDomicilios.setDisabled(true);  
									    comboPais.setDisabled(true);    
									    comboEstados.setDisabled(true);   
									    comboMunicipio.setDisabled(true);        
									    comboColonias.setDisabled(true);   
	                                 }
                  }],
         frame:true,
         height:150,
         width:600,
         title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Direcci&oacute;n</span>'
        });
	
	}
	
	function addGridDomicilioNewRecord () {
		var new_record = new recordDomicilios({
				            codigoPersona: CODIGO_PERSONA,
				        	numOrdenDomicilio: '',
				        	tipoDomicilio: '',
							dsDomicilio: '',
							cdPostal: '',
							numero: '',
							numeroInterno: '',
							codigoPais: '',
							codigoEstado: '',
							codigoMunicipio: '',
							codigoColonia: ''
						});
		grillaDomicilios.stopEditing();
		grillaDomicilios.store.removeAll();
		grillaDomicilios.store.insert(0, new_record);
		grillaDomicilios.startEditing(0, 0);
		comboEstados.store.removeAll();

		comboMunicipio.store.removeAll();
		comboColonias.store.removeAll();
		comboEstados.setValue('');
	                    						
	    calle.setDisabled(false);
	    numero.setDisabled(false);
	    comboDomicilios.setDisabled(false);  
	    comboPais.setDisabled(false);    
	    comboEstados.setDisabled(false);   
	    comboMunicipio.setDisabled(false);        
	    comboColonias.setDisabled(false);         						
	}

	
	createGridDomcilios();
	
	var comboInstrumentosPago = new Ext.form.ComboBox({
			tpl: '<tpl for="."><div ext:qtip="{cdForPag}.{dsForPag}" class="x-combo-list-item">{dsForPag}</div></tpl>',
		    id:'comboDsInstrumentosPagoCarritoComprasFinalizarId',
            fieldLabel: getLabelFromMap('comboDsInstrumentosPagoCarritoComprasFinalizarId',helpMap,'Instrumento de Pago'),
            tooltip: getToolTipFromMap('comboDsInstrumentosPagoCarritoComprasFinalizarId',helpMap,'Instrumento de Pago'),
			store: dsInstrumentosPago, 
			displayField:'dsForPag', 
			valueField: 'cdForPag', 
			hiddenName: 'InstrumentoPago',
			name: 'InstrPago',
			typeAhead: true, 
			triggerAction: 'all', 
			lazyRender:   true, 
			emptyText:'Seleccione Instrumento de Pago...', 
			selectOnFocus:true,
			//labelSeparator:'', 
			//fieldLabel: 'Instrumento de Pago',
			forceSelection:true,
			width: 200,
			onSelect: function (record) {
						this.setValue(record.get('cdForPag'));
			    		this.collapse();
			            validaCampos(record.get('cdMuestra'));
			}
       	})
			                    		
			                    		
	var comboBancos = new Ext.form.ComboBox({
			tpl: '<tpl for="."><div ext:qtip="{cdBanco}.{dsNombre}" class="x-combo-list-item">{dsNombre}</div></tpl>',
		    id:'comboDsBancoCarritoComprasFinalizarId',
            fieldLabel: getLabelFromMap('comboDsBancoCarritoComprasFinalizarId',helpMap,'Banco'),
            tooltip: getToolTipFromMap('comboDsBancoCarritoComprasFinalizarId',helpMap,'Banco'),
			store: dsBancos, 
			displayField:'dsNombre', 
			valueField: 'cdBanco', 
			hiddenName: 'Bancos',
			typeAhead: true, 
			triggerAction: 'all', 
			lazyRender:   true, 
			emptyText:'Seleccione Banco...', 
			selectOnFocus:true,
			//labelSeparator:'', 
			//fieldLabel: 'Banco',
			width: 200,
			forceSelection:true,
			align:'center'
	})
			                    		
			                    		
	var comboTiposTarjeta = new Ext.form.ComboBox({
			tpl: '<tpl for="."><div ext:qtip="{cdTiTarj}.{dsTiTarj}" class="x-combo-list-item">{dsTiTarj}</div></tpl>',
		    id:'comboDsTiposTarjetasCarritoComprasFinalizarId',
            fieldLabel: getLabelFromMap('comboDsTiposTarjetasCarritoComprasFinalizarId',helpMap,'Tipo de Tarjeta'),
            tooltip: getToolTipFromMap('comboDsTiposTarjetasCarritoComprasFinalizarId',helpMap,'Tipo de Tarjeta'),
			store: dsTiposTarjetas, 
			displayField:'dsTiTarj', 
			valueField: 'cdTiTarj', 
			hiddenName: 'TiposTarjeta',
			typeAhead: true, 
			triggerAction: 'all', 
			lazyRender:   true, 
			emptyText:'Seleccione tipo de tarjeta...', 
			selectOnFocus:true,
			//labelSeparator:'', 
			fieldLabel: 'Tipo de Tarjeta',
			forceSelection:true,
			width: 200
	})
			                    		
			                    		
//##########################################################################
//PAGAR

var nroTarjeta = new Ext.form.TextField({
		id: 'nmTarj', 
		fieldLabel: getLabelFromMap('nmTarj',helpMap,'N&uacute;mero de Tarjeta'),
		tooltip: getToolTipFromMap('nmTarj',helpMap,'N&uacute;mero de Tarjeta'),                    				    
        name: 'nmTarj'
    });
    
var fechaVencimiento = new Ext.form.DateField({
		id: 'feVence', 
		fieldLabel: getLabelFromMap('nmTarj',helpMap,'Fecha de Vencimiento'),
		tooltip: getToolTipFromMap('nmTarj',helpMap,'Fecha de Vencimiento'),                    				    
        name: 'feVence',
        format:'d/m/Y'
    });

//la grilla
var lasFilas=new Ext.data.Record.create([
            {name: 'cdUniEco',  type: 'string',  mapping:'cdUniEco'},
	        {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
	        {name: 'cdPlan',  type: 'string',  mapping:'cdPlan'},
	        {name: 'dsUniEco',  type: 'string',  mapping:'dsUniEco'},
	        {name: 'dsRamo',  type: 'string',  mapping:'dsRamo'},
	        {name: 'dsPlan',  type: 'string',  mapping:'dsPlan'},
	        {name: 'feInicio',  type: 'string',  mapping:'feInicio'},
	        {name: 'feEstado',  type: 'string',  mapping:'feEstado'},
	        {name: 'mnTotalp',  type: 'string',  mapping:'mnTotalp'}               
]);

//el JsonReader de la grilla a mostrar
var jsonGrilla= new Ext.data.JsonReader(
  {
   root:'MCarritoComprasManagerList',
   totalProperty: 'totalCount',
   successProperty : '@success'
  },
 lasFilas
);

function storeGrilla(){
       store = new Ext.data.Store({
       proxy: new Ext.data.HttpProxy({
             url: _ACTION_OBTENER_PRODUCTOS_CARRITO_COMPRAS,
             waitMsg: 'Espere por favor....'
            }),
       reader:jsonGrilla,
       baseParams: {
					cdCarro:CDCARRO,
       	            cdUsuari:CDUSUARI
					
					}
       });
       return store;
}

var grid2;


  
 var cdCarro = new Ext.form.Hidden( {
                 name : 'cdCarro'
                });
    
 var cdUsuari = new Ext.form.Hidden({
 	   name:'cdUsuari'
    });
  
              	          
 var mnTotalp = new Ext.form.TextField({
 		id: 'mnTotalpCarritoComprasFinalizarId', 
		fieldLabel: getLabelFromMap('mnTotalpCarritoComprasFinalizarId',helpMap,'Subtotal'),
		tooltip: getToolTipFromMap('mnTotalpCarritoComprasFinalizarId',helpMap,'Subtotal'),                    				    
 	    //fieldLabel: 'Subtotal',
 	    labelWidth:10,
        name:'mnTotalp',
        readOnly:true,
        width: 60
    });

    var nmdsc = new Ext.form.TextField({
 		id: 'nmDscCarritoComprasFinalizarId', 
		fieldLabel: getLabelFromMap('nmDscCarritoComprasFinalizarId',helpMap,'Dscto'),
		tooltip: getToolTipFromMap('nmDscCarritoComprasFinalizarId',helpMap,'Dscto'),                    				    
        //fieldLabel: 'Dscto',
        labelWidth:10,
        name:'nmDsc',
        readOnly:true,
        width: 60
    });
    
    var nmtotal = new Ext.form.TextField({
 		id: 'nmtotalCarritoComprasFinalizarId', 
		fieldLabel: getLabelFromMap('nmtotalCarritoComprasFinalizarId',helpMap,'Total'),
		tooltip: getToolTipFromMap('nmtotalCarritoComprasFinalizarId',helpMap,'Total'),                    				    
    	//fieldLabel: 'Total',
    	labelWidth:10,
        name:'nmtotal',
        readOnly:true,
        width: 60
    });

    
direccion=new Ext.Panel({
  layout:'form',
  bodyStyle:'background: white',
  borderStyle:false, 
  layout: 'column',
  layoutConfig: {columns: 2, align: 'left'},
  items:[ 
          calle
          //grillaDomicilios
        ]
});   

datosPersona=new Ext.Panel({
  layout:'form',
  borderStyle:false,
  labelAlign: 'right',
  width: 505,
  bodyStyle:'background: white',
  items:[ 
         contratante
         //nmOrden
        ]
});
pagar=new Ext.Panel({
  layout:'form',
  bodyStyle:'background: white',
  borderStyle:false,
  labelAlign: 'right',
  items:[ 
          comboInstrumentosPago,
          comboBancos,
          comboTiposTarjeta,
          nroTarjeta,
          fechaVencimiento
        ]
});   



pie=new Ext.Panel({
  layout:'form',
  borderStyle:true, 
  bodyStyle:'background: white',
  height:120,
  labelAlign: 'right',
  items:[ 
          mnTotalp,
	      nmdsc,
		  nmtotal
        ]
});

/*    
pie=new Ext.Panel({
  layout:'form',
  borderStyle:true, 
  autoWidth: true, 
  height: 80, 
  bodyStyle:'background: white',
  //bodyStyle: {position: 'relative'},  
  items:[ 
          mnTotalp,
	      nmdsc,
		  nmtotal
        ]
});*/

var tableDatosPersona = new Ext.Panel({
    id: 'tableDatosPersonaPanelId',
	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('tableDatosPersonaPanelId', helpMap,'Datos de las personas')+'</span>',
    layout:'column',
    border :true, 
    frame: true, 
    width: 600,
    bodyStyle:'background: white', 
   	height: 60,
    items: [{
                columnWidth: .1 
		    },{
		        columnWidth: .8,
		        layout: 'fit',
		        items:[datosPersona]
		    },{
		        columnWidth: .1
		    }]
}); 

var tableDireccion = new Ext.Panel({
    id: 'tableDireccionPanelId',
	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('tableDatosPersonaPanelId', helpMap,'Direcci&oacute;n')+'</span>',
	//title: '<span style="color:black;font-size:14px;">Direcci&oacuten</span>',
   	layout:'column',
   	bodyStyle:'background: white',
   	border :false,
    defaults: {
        // applied to each contained panel
        bodyStyle:'padding:5px'
    },
    items: [{
                columnWidth: .1 
		    },{
		        columnWidth: .8,
		        items:[direccion]
		    },{
		        columnWidth: .1
		    }]
}); 

var tablePagar = new Ext.Panel({
    id: 'tablePagarPanelId',
	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('tablePagarPanelId', helpMap,'Pagar')+'</span>',
    //title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Pagar</span>',
    layout:'column',
    border :false, 
    frame: true, 
    width: 600,
    bodyStyle:'background: white',
    defaults: {
        // applied to each contained panel
        bodyStyle:'padding:5px'
    },
    items: [{
                columnWidth: .2 
		    },{
		        columnWidth: .6,
		        items:[pagar]
		    },{
		        columnWidth: .2
		    }]
}); 


var tableResumen = new Ext.Panel({
	//title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('pnlPtDtlOrdnCmpr', helpMap,'Resumen Prima Total')+'</span>',    
    title: '<span style="color:black;font-size:14px;">Resumen Prima Total</span>',
    border :false, 
    frame: true, 
    width: 586,
    layout:'column',
    border :false,
	 bodyStyle:'background: white',
    defaults: {
        bodyStyle:'padding:0px'
    },
    items: [
    	
    		{
                layout:'form',
		        height:80,
                columnWidth: .4 
            },
            {
                layout:'form',
		        height:80,
                columnWidth: .3
            },
            {
                layout:'form',
		        height:80,
                columnWidth: .3,
                items:[pie]
            }
            
            ]
});
 
function createGrid(){
    grid2= new Ext.grid.GridPanel({
    	 id: 'grid2',
         store:storeGrilla(),
         reader:jsonGrilla,
         border:true,
         cm: cm,
         clicksToEdit:1,
         successProperty: 'success',
         width:586,
         height:150,
         frame:true,
         sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
         viewConfig: {
                    autoFill: true,
                    forceFit:true
                    }
      });
 
}

createGrid();

botones=new Ext.Panel({
  layout:'column',
  borderStyle:true, 
  bodyStyle:'background: white',
  width: 550,
  height: 50, 
  items:[ 
          {
          buttons:[
               {
        		   id:'grid2ButtonFinalizarComprarId',
        		   text:getLabelFromMap('grid2ButtonFinalizarComprarId', helpMap,'Finalizar Compra'),
            	   tooltip:getToolTipFromMap('grid2ButtonFinalizarComprarId', helpMap,'Finalizar Compra'),	
	               //text:'Finalizar',
	               //tooltip:'Finalizar Compra',
	               handler:function(){
	                                   guardarDatosProductosCarrito();
	                                 }
               },
               {
        		   id:'grid2ButtonCancelarFinalizarComprarId',
        		   text:getLabelFromMap('grid2ButtonCancelarFinalizarComprarId', helpMap,'Cancelar'),
            	   tooltip:getToolTipFromMap('grid2ButtonCancelarFinalizarComprarId', helpMap,'Cancelar Finalizar Compra')
	               //text:'Cancelar',
	               //tooltip:'Cancelar Compra'
               }
               ]
         }
        ]
});



//el JsonReader de la parte izquierda
var elJson = new Ext.data.JsonReader(
    {
        root : 'MEstructuraList',
        totalProperty: 'total',
        successProperty : '@success'
    },
    [ 
    {name: 'codRazon',  mapping:'cdRazon',  type: 'string'},
    {name: 'dsRazon',  mapping:'dsRazon',  type: 'string'},  
    {name: 'swReInst',  mapping:'swReInst',  type: 'string'},
    {name: 'swVerPag',  mapping:'swVerPag',  type: 'string'}  
    ]
)

       
var incisosForm = new Ext.FormPanel({
	id: 'incisosForm',
	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm', helpMap,'Finalizar Compra')+'</span>',
    el:'gridProductos',
    iconCls:'logo',
    bodyStyle:'background: white',
    frame:true,   
    url: _ACTION_OBTENER_PRODUCTOS_CARRITO_COMPRAS,
    store:storeGrilla(),
    reader:jsonGrilla,
    layout: 'column',
    layoutConfig: {columns: 2, align: 'right'},
    labelAlign:'top',
    width: 600,
    height:786,
    items: [{
        		 border: true,
        		 labelAlign:'rigth', 
        		 layout: 'form',
              	 items:[ 
              	          tableDatosPersona,
              	          grillaDomicilios,
              	          tablePagar,
    		              grid2,
    		              tableResumen,
    		              botones 
                        ]
        	}]	            
        
});   


 function guardarDatosProductosCarrito () {
 
  var _params = "";
 
   
  
  var recs = grid2.store.getRange(0,grid2.store.getTotalCount()-1) ;
  grid2.stopEditing();
  //
  for (var i=0; i<recs.length; i++) {
  
     var feIni = new Date();
     var feEst = new Date();
  
     feIni = Date.parseDate(recs[i].get('feInicio'), 'Y-m-d H:i:s.g');
     var feInicio= feIni.format('d/m/Y');
     feEst = Date.parseDate(recs[i].get('feEstado'), 'Y-m-d H:i:s.g');
     var feEstado = feEst.format('d/m/Y');
  
   _params +=  "csoGrillaProductosCarrito[" + i + "].cdCarro=" + CDCARRO + "&" +
      "&csoGrillaProductosCarrito[" + i + "].cdUsuari=" + CDUSUARI + "&" +
      "&csoGrillaProductosCarrito[" + i + "].feInicio=" + feInicio + "&" + 
      "&csoGrillaProductosCarrito[" + i + "].nmTarj=" + nroTarjeta.getValue() + "&" +
      "&csoGrillaProductosCarrito[" + i + "].cdContra=" + CDCONTRA + "&" +
      "&csoGrillaProductosCarrito[" + i + "].cdAsegur=" + CDASEGUR + "&" +
      "&csoGrillaProductosCarrito[" + i + "].nmSubtot=" + mnTotalp.getValue() + "&" + 
      "&csoGrillaProductosCarrito[" + i + "].nmDsc=" + nmdsc.getValue() + "&" +
      "&csoGrillaProductosCarrito[" + i + "].nmTotal=" + nmtotal.getValue() + "&" +
      "&csoGrillaProductosCarrito[" + i + "].cdEstado=" + grillaDomicilios.getStore().getAt(0).get('cdEdo1') + "&" +
      "&csoGrillaProductosCarrito[" + i + "].feEstado=" + feEstado + "&" +
      "&csoGrillaProductosCarrito[" + i + "].cdTipDom=" + grillaDomicilios.getStore().getAt(0).get('cdTipDom') + "&" +
      "&csoGrillaProductosCarrito[" + i + "].nmOrdDom=" + grillaDomicilios.getStore().getAt(0).get('nmOrdDom') + "&" +
      "&csoGrillaProductosCarrito[" + i + "].cdClient=" + CDCLIENT + "&" +
      "&csoGrillaProductosCarrito[" + i + "].cdForPag=" + comboInstrumentosPago.getValue() + "&" +
      "&csoGrillaProductosCarrito[" + i + "].cdUniEco=" + recs[i].get('cdUniEco') + "&" +
      "&csoGrillaProductosCarrito[" + i + "].cdRamo=" + recs[i].get('cdRamo') + "&" +
      "&csoGrillaProductosCarrito[" + i + "].nmPoliza=" + NMPOLIZA + "&" +
      "&csoGrillaProductosCarrito[" + i + "].nmSuplem=" + NMSUPLEM + "&" +
      "&csoGrillaProductosCarrito[" + i + "].mnTotalP=" + recs[i].get('mnTotalp') + "&" +
      "&csoGrillaProductosCarrito[" + i + "].cdTipSit=" + CDTIPSIT + "&" +
      "&csoGrillaProductosCarrito[" + i + "].cdPlan=" + recs[i].get('cdPlan') + "&" + 
      "&csoGrillaProductosCarrito[" + i + "].fgDscapli=" + FGDSCAPLI + "&" +
      "&csoGrillaProductosCarrito[" + i + "].feIngres=" + FEINGRES + "&" +
      "&csoGrillaProductosCarrito[" + i + "].cdEstadoD=" + CDESTADOD + "&";
  }
 

  if (recs.length > 0) {
  var conn = new Ext.data.Connection ();
   conn.request({
     url: _ACTION_GUARDAR_CARRITO_COMPRAS,
     params: _params,
     method: 'POST',
     callback: function (options, success, response) {
       
         if (Ext.util.JSON.decode(response.responseText).success == false) {
          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'Problemas al guardar datos de productos: ' + Ext.util.JSON.decode(response.responseText).errorMessages[0]);
         } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos de productos se guardaron con &eacute;xito');
          grid2.store.commitChanges();
         }
       }
   });
   var feVencim = new Date();
   feVencim = incisosForm.findById('feVence').getValue();
   
   var connForPag = new Ext.data.Connection ();
   connForPag.request({
     url: _ACTION_GUARDAR_FORMA_PAGO,
     params: {
               nmTarj: nroTarjeta.getValue(),
               cdTiTarj: comboTiposTarjeta.getValue(),
               cdPerson: CDPERSON,
               feVence: feVencim.format('d/m/Y'),
               cdBanco: comboBancos.getValue(),
               debCred: DEBCRED
             },
     method: 'POST',
     callback: function (options, success, response) {
       
         if (Ext.util.JSON.decode(response.responseText).success == false) {
                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'Problemas al guardar datos de forma de pago: ' + Ext.util.JSON.decode(response.responseText).errorMessages[0]);
             } else {
                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos de forma de pago se guardaron con &eacute;xito');
         }
       }
   });
  }
 }  


function reloadGrid(){
	var _params = {
			cdRazon: CODIGO_RAZON
	};
	reloadComponentStore(grid2, _params, cbkReload);
}

function reloadGridProductos () {
	var _params = {
			    cdCarro: CDCARRO,
			    cdUsuari: CDUSUARI
	}
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}

function cbkReload(_r, _options, _success, _store) {
	if (!_success){
		_store.removeAll();
		addGridNewRecord();
	}
}

function cbkConnection(_success, _message) {
	if (!_success) {
		Ext.Msg.alert('Error', _message);
	}else {
		Ext.Msg.alert('Aviso', _message, function(){reloadGrid();});
	}
}

function renderComboEditor (combo) {
		return function (value) {
			var idx = combo.store.find(combo.valueField, value);
			var rec = combo.store.getAt(idx);
			return (rec == null)?value:rec.get(combo.displayField);
		}
	}




dsTipoDomicilio.load();
incisosForm.render();
grid2.render();
//pie.doLayout();

grid2.store.load();

grillaDomicilios.store.load({callback:function(record,opt,success){

                        if (success) {
                            dsPaises.load({callback:function(record,opt,success){
		                        if (success) {
		                        	dsEstados.load({params:
		                        	                 {codigoPais:grillaDomicilios.store.reader.jsonData.MCarritoComprasManagerListDire[0].cdPais },
		                        	                 callback:function(record,opt,success){
		                        	                 dsMunicipios.load({
			                    										params: {
						                    										codigoPais: grillaDomicilios.store.reader.jsonData.MCarritoComprasManagerListDire[0].cdPais,
						                    										codigoEstado: grillaDomicilios.store.reader.jsonData.MCarritoComprasManagerListDire[0].cdEdo
						                    									},
						                    							callback:function(record,opt,success){
							                        	                dsColonias.load({
								                    										params: {
											                    										codigoPais: grillaDomicilios.store.reader.jsonData.MCarritoComprasManagerListDire[0].cdPais,
											                    										codigoEstado: grillaDomicilios.store.reader.jsonData.MCarritoComprasManagerListDire[0].cdEdo,
											                    										codigoMunicipio:grillaDomicilios.store.reader.jsonData.MCarritoComprasManagerListDire[0].cdMunici
											                    									}
											                    		 });							
											                    		}				
						                    		 });							
						                    		}							
		                        	               });
		                        }
		                     }   
                            });
                         }   
                         
}
});


dsBancos.load();


dsEncOrden.load({callback:function(record,opt,success){
                        if (success) {
                        	//Ext.getCmp("nmOrden").setValue(dsEncOrden.reader.jsonData.MCarritoComprasManagerListEncOrden[0].nmOrden);
                        	Ext.getCmp("contratante").setValue(dsEncOrden.reader.jsonData.MCarritoComprasManagerListEncOrden[0].nombreContratante);
                        }
                        }
                         
});

dsInstrumentosPago.load({callback:function(record,opt,success){
	                        if (success) {                       
		                        dsPagar.load({callback:function(record,opt,success){
			                        if (success) {
			                        	comboInstrumentosPago.setValue(dsPagar.reader.jsonData.MCarritoComprasManagerListDetOrden[0].cdForPag);
			                        	comboBancos.setValue(dsPagar.reader.jsonData.MCarritoComprasManagerListDetOrden[0].cdBanco);
			                        	comboTiposTarjeta.setValue(dsPagar.reader.jsonData.MCarritoComprasManagerListDetOrden[0].cdTitArg);
			                        	Ext.getCmp("nmTarj").setValue(dsPagar.reader.jsonData.MCarritoComprasManagerListDetOrden[0].nmTarj);
			                        	validaCampos(dsInstrumentosPago.reader.jsonData.comboInstrumentosPago[0].cdMuestra);
			                        }
		                        }
		                        });
							
	                        }
                        }
                         
					  });


function validaCampos(codMuestra){
if (codMuestra=="S"){
          	//hacer activos los campos
          	comboBancos.setDisabled(false);
          	comboBancos.allowBlank = false;
          					
          	comboTiposTarjeta.setDisabled(false);
          	comboTiposTarjeta.allowBlank = false;
          								
          	nroTarjeta.setDisabled(false);
          	nroTarjeta.allowBlank = false;
          								
          	fechaVencimiento.setDisabled(false);
          	fechaVencimiento.allowBlank = false;
          						
           }else{
           
          	//desactivar los campos
          	comboBancos.setDisabled(true);
          	comboBancos.hideLabel=true;
          	comboBancos.allowBlank = true;
          	comboBancos.setValue('');
          								
          	comboTiposTarjeta.setDisabled(true);
          	comboTiposTarjeta.allowBlank = true;
          	comboTiposTarjeta.setValue('');
          						
          	nroTarjeta.setDisabled(true);
          	nroTarjeta.allowBlank = true;
          	nroTarjeta.setValue('');
          								
          	fechaVencimiento.allowBlank = true;
          	fechaVencimiento.setValue('');
          	fechaVencimiento.setDisabled(true);
          	
          }
}



dsMontos.load({callback:function(record,opt,success){
                            mnTotalp.setValue(dsMontos.reader.jsonData.elSubTotal);
                        	nmdsc.setValue(dsMontos.reader.jsonData.elDescuento);
                        	nmtotal.setValue(dsMontos.reader.jsonData.elTotalFn);
                        
                        }
                         
});

            
});
