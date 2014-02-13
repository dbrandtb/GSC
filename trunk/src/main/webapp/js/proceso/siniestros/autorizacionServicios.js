Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
var datosgrid;
var storeConceptoAutorizados;
var storeQuirugicoBase;
var storeQuirugico;
var extraParams='';
Ext.onReady(function() {

	Ext.selection.CheckboxModel.override( {
		mode: 'SINGLE',
		allowDeselect: true
	});
	
    // Conversión para el tipo de moneda
    Ext.util.Format.thousandSeparator = ',';
    Ext.util.Format.decimalSeparator = '.';
    
    
	////////////////////////////////////////////////
	/////////// 		MODELO				////////
	////////////////////////////////////////////////
    Ext.define('modelListadoCobertura',{
        extend: 'Ext.data.Model',
        fields: [
                 	{type:'string',    name:'cdgarant'},		{type:'string',    name:'dsgarant'},		{type:'string',    name:'ptcapita'}
				]
    });
    
    Ext.define('modelListadoProvMedico',{
        extend: 'Ext.data.Model',
        fields: [
                 	{type:'string',    name:'cdpresta'},		{type:'string',    name:'nombre'},		{type:'string',    name:'cdespeci'},		{type:'string',    name:'descesp'}
				]
    });
    
	Ext.define('modelListadoAsegurado',{
        extend: 'Ext.data.Model',
        fields: [
                 	{type:'string',    name:'nmautser'},		{type:'string',    name:'nmautant'},		{type:'string',    name:'fesolici'},
					{type:'string',    name:'polizaafectada'},	{type:'string',    name:'cdprovee'},		{type:'string',    name:'nombreProveedor'}
				]
    });
	
	Ext.define('modelListadoPoliza',{
        extend: 'Ext.data.Model',
        fields: [
                 	{type:'string',    name:'cdramo'},		{type:'string',    name:'cdunieco'},		{type:'string',    name:'estado'},
					{type:'string',    name:'nmpoliza'},	{type:'string',    name:'nmsituac'}
				]
    });
	
	Ext.define('modelListadoTmanteni',{
        extend: 'Ext.data.Model',
        fields: [
                 	{type:'string',    name:'cdtabla'},		{type:'string',    name:'codigo'},		{type:'string',    name:'descripc'},
					{type:'string',    name:'descripl'}
				]
    });
	
	Ext.define('modelListadoTabulador',{
        extend: 'Ext.data.Model',
        fields: [
                 	{type:'string',    name:'porcentaje'},		{type:'string',    name:'mtomedico'}
				]
    });


	Ext.define('modelListadoTablas',{
        extend: 'Ext.data.Model',
        fields: [
                 	{type:'string',    name:'nmautser'},		{type:'string',    name:'cdtipaut'},		{type:'string',    name:'cdmedico'},
                 	{type:'string',    name:'nombreMedico'},	{type:'string',    name:'cdtipmed'},		{type:'string',    name:'cdcpt'},
                 	{type:'string',    name:'desccpt'},			{type:'string',    name:'precio'},		{type:'string',    name:'cantporc'},
                 	{type:'string',    name:'ptimport'},		{type:'string',    name:'descTipMed'}
                 	
				]
    });
	
	////////////////////////////////////////////////
	/////////// 		STORE				////////
	////////////////////////////////////////////////
	
    //STORE PARA SELECCIONAR EL TIPO DE AUTORIZACION
    storeTipoAutorizacion= Ext.create('Ext.data.JsonStore', {
    	model:'Generic',
        proxy: {
            type: 'ajax',
            url: _URL_TIPO_AUTORIZACION,
            reader: {
                type: 'json',
                root: 'tiposAutorizacion'
            }
        }
    });
    storeTipoAutorizacion.load();
	storeAsegurados = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTADO_ASEGURADO,
            reader: {
                type: 'json',
                root: 'listaAsegurado'
            }
        }
    });
    
    storeSiniestros = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_CAUSA_SINIESTRO,
            reader: {
                type: 'json',
                root: 'listaCausaSiniestro'
            }
        }
    });

    storeSiniestros.load();
    
    /*Store que contiene la información general en el grid*/
    storeListadoAsegurado = new Ext.data.Store(
    {
    	pageSize : 5
        ,model      : 'modelListadoAsegurado'
        ,autoLoad  : false
        ,proxy     :
        {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });
    
    
    /*Store que contiene la información general en el grid para la poliza*/
    storeListadoPoliza = new Ext.data.Store(
    {
    	pageSize : 5
        ,model      : 'modelListadoPoliza'
        ,autoLoad  : false
        ,proxy     :
        {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });
    
    var storeMedico = Ext.create('Ext.data.Store', {
        model:'modelListadoProvMedico',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_PROVEEDOR_MEDICO,
            extraParams:{
            	'params.tipoprov' : 'M'
            },
            reader: {
                type: 'json',
                root: 'listaProvMedico'
            }
        }
    });
    
    var storeProveedor = Ext.create('Ext.data.Store', {
        model:'modelListadoProvMedico',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_PROVEEDOR_MEDICO,
            extraParams:{
            	'params.tipoprov' : 'C'
            },
            reader: {
                type: 'json',
                root: 'listaProvMedico'
            }
        }
    });
    
    var storeTiposICD = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTA_CPTICD,
            extraParams:{
            	'params.cdtabla' : '2TABLICD'
            },
            reader: {
                type: 'json',
                root: 'listaCPTICD'
            }
        }
    });
    
    var storeTiposCPT = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTA_CPTICD,
            extraParams:{
            	'params.cdtabla' : '2TABLCPT'
            },
            reader: {
                type: 'json',
                root: 'listaCPTICD'
            }
        }
    });
    
    storeTipoMedico = Ext.create('Ext.data.Store', {
        model:'modelListadoTmanteni',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTA_TMANTENI,
            extraParams:{
            	'params.cdtabla' : 'TIPOMEDICO'
            },
            reader: {
                type: 'json',
                root: 'listaConsultaManteni'
            }
        }
    });
    
    
    
    storePenalizacion = Ext.create('Ext.data.Store', {
        model:'modelListadoTmanteni',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTA_TMANTENI,
            extraParams:{
            	'params.cdtabla' : 'TPENALIZACIONES'
            },
            reader: {
                type: 'json',
                root: 'listaConsultaManteni'
            }
        }
    });
    
    
    storeTabulador = Ext.create('Ext.data.Store', {
        model:'modelListadoTabulador',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTA_TABULADOR,
            reader: {
                type: 'json',
                root: 'listaPorcentaje'
            }
        }
    });
    
    
    
    
    //DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
	storeConceptoAutorizados=new Ext.data.Store(
	{
		autoDestroy: true,
		model: 'modelListadoTablas'
	});
	
	//DATOS PARA EL SEGUNDO GRID --> EQUIPO QUIRURGICO BASE
	storeQuirugicoBase=new Ext.data.Store(
	{
		autoDestroy: true,
		model: 'modelListadoTablas'
	});
	
	//DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO
	storeQuirurgico=new Ext.data.Store(
	{
		autoDestroy: true,
		model: 'modelListadoTablas'
	});
    
    
    /*//////////////////////////////////////////////////////////////////////////////////
     *////////////////////////////		VERIFICAR 	////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////*/
	
	var storeCobertura = Ext.create('Ext.data.Store', {
        model:'modelListadoCobertura',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTA_COBERTURA,
            reader: {
                type: 'json',
                root: 'listaCoberturaPoliza'
            }
        }
    });
    
    
    var storeSubcobertura= Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTA_SUBCOBERTURA,
            reader: {
                type: 'json',
                root: 'listaSubcobertura'
            }
        }
    });

	//////////////////////////////////////////
	///// DECLARACION DE LOS COMBOS /////////
	////////////////////////////////////////
	
	
	gridDatosPoliza= Ext.create('Ext.grid.Panel',
			{
			    id             : 'polizaGridId'
			    ,store         :  storeListadoPoliza
			    ,collapsible   : true
			    ,titleCollapse : true
			    //,style         : 'margin:5px'
			    ,width   : 640
			    ,height: 200
			    ,columns       :
			    [
			        {
			             header     : 'N&uacute;mero de P&oacute;liza'
			             ,dataIndex : 'nmpoliza'
			             ,width	 	: 150
			         },
			         {
			             header     : 'Ramo'
			             ,dataIndex : 'cdramo'
			             ,width     : 100
			         }
			         ,
			         {
			             header     : 'Unieco'
			             ,dataIndex : 'cdunieco'
			             ,width     : 100
			         },
			         {
			             header     : 'Estado'
			             ,dataIndex : 'estado'
			             ,width	    : 100
			         }
			         ,
			         {
			             header     : 'N&uacute;mero de Situaci&oacute;n'
			             ,dataIndex : 'nmsituac'
			             ,width	    : 200
			         }
			     ],
			     bbar     :
			     {
			         displayInfo : true,
			         store       : storeListadoPoliza,
			         xtype       : 'pagingtoolbar'
			     }
			});

	



			modificacionPolizas = Ext.create('Ext.window.Window',
			{
				title        : 'Listado de P&oacute;liza'
				,modal       : true
				,buttonAlign : 'center'
				,closable : false
				,width		 : 650
				,minHeight 	 : 100 
				,maxheight      : 400
				,items       :
					[
					 	gridDatosPoliza
					 ],
					 buttons: [{
							text: "Seleccionar"
							,icon:_CONTEXT+'/resources/fam3icons/icons/application_edit.png'
							,handler: function(){
									if(Ext.getCmp('polizaGridId').getSelectionModel().hasSelection()){
										var rowSelected = Ext.getCmp('polizaGridId').getSelectionModel().getSelection()[0];
										Ext.getCmp('idUnieco').setValue(rowSelected.get('cdunieco'));
										Ext.getCmp('idEstado').setValue(rowSelected.get('estado'));
										Ext.getCmp('idcdRamo').setValue(rowSelected.get('cdramo'));
										Ext.getCmp('idNmSituac').setValue(rowSelected.get('nmsituac'));
										Ext.getCmp('polizaAfectada').setValue(rowSelected.get('nmpoliza'));
										storeCobertura.load({
						                    params:{
						                    	'params.cdunieco':Ext.getCmp('idUnieco').getValue(),
								            	'params.estado':Ext.getCmp('idEstado').getValue(),
								            	'params.cdramo':Ext.getCmp('idcdRamo').getValue(),
								            	'params.nmpoliza':Ext.getCmp('polizaAfectada').getValue(),
								            	'params.nmsituac':Ext.getCmp('idNmSituac').getValue()
						                    }
										});
										
										modificacionPolizas.hide();
									}else {
										Ext.Msg.show({
											title: 'Aviso',
											msg: 'Debe de seleccionar la p&oacute;liza a afectar',
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.ERROR
										});
										
									}
							}
						}
					 ]
			});
			
			
	asegurado = Ext.create('Ext.form.field.ComboBox',
    {
		colspan:2,						fieldLabel : 'Asegurado',			allowBlank: false,				displayField : 'value',		name:'cdperson',
        id:'idAsegurado',				labelWidth: 170,					width:500,						valueField   : 'key',
        forceSelection : false,			matchFieldWidth: false,				queryMode :'remote',			queryParam: 'params.cdperson',
        store : storeAsegurados,		triggerAction: 'all',				//editable:false,
        listeners : {
			'select' : function(combo, record) {
					obtieneCDPerson = this.getValue();
					
					storeSubcobertura.removeAll();
					storeCobertura.removeAll();
					Ext.getCmp('idDeducible').setValue('');
			    	Ext.getCmp('idCopago').setValue('');
			    	Ext.getCmp('idCobAfectada').reset();
			    	Ext.getCmp('idSubcobertura').reset();
					
					var params = {
			                'params.cdperson' : obtieneCDPerson
			        };
					
			        cargaStorePaginadoLocal(storeListadoPoliza, _URL_CONSULTA_LISTADO_POLIZA, 'listaPoliza', params, function(options, success, response){
			            if(success){
			                var jsonResponse = Ext.decode(response.responseText);
			                if(jsonResponse.listaPoliza == null) {								
			                    Ext.Msg.show({
			                        title: 'Aviso',
			                        msg: 'No se encontraron datos.',
			                        buttons: Ext.Msg.OK,
			                        icon: Ext.Msg.WARNING
			                    });
			                    return;
			                }
			                
			            }else{
			                Ext.Msg.show({
			                    title: 'Aviso',
			                    msg: 'Error al obtener los datos.',
			                    buttons: Ext.Msg.OK,
			                    icon: Ext.Msg.ERROR
			                });
			            }
			        });
			        
			        modificacionPolizas.showAt(200,100);
				}
			}
    });
	
	causaSiniestro = Ext.create('Ext.form.field.ComboBox',
    {
		colspan:2,						fieldLabel : 'Causa Siniestro',		allowBlank: false,				displayField : 'value',
		id:'idCausaSiniestro',			labelWidth: 170,					width:500,						valueField   : 'key',
		name:'cdcausa',		forceSelection : false,				matchFieldWidth: false,				queryMode :'remote',
		queryParam: 'params.cdcausa',	store : storeSiniestros,			triggerAction: 'all',				editable:false,
	});

	
	//////////////////////////////////////////////////
    medico = Ext.create('Ext.form.field.ComboBox',
    {
		fieldLabel : 'M&eacute;dico',	allowBlank: false,					displayField : 'nombre',			id:'idMedico',			name:'cdmedico',
		labelWidth: 170,				valueField   : 'cdpresta',				forceSelection : false,			matchFieldWidth: false,
		queryMode :'remote',			queryParam: 'params.cdpresta',		store : storeMedico,			triggerAction: 'all',		editable:false,
		listeners : {
	    	change:function(el) {
	    		Ext.getCmp('idEspecialidad').setValue('');
	    		Ext.Ajax.request(
	    				{
	    				    url     : _URL_CONSULTA_PROVEEDOR_MEDICO
	    				    ,params:{
	    						'params.cdpresta' : el.value,
	    						'params.tipoprov' : 'M'
	    	                }
	    				    ,success : function (response)
	    				    {
	    				    	if(Ext.decode(response.responseText).listaProvMedico != null)
    				    		{
	    				    		var json=Ext.decode(response.responseText).listaProvMedico[0];
		    				        Ext.getCmp('idEspecialidad').setValue(json.descesp);
    				    		}
	    				    },
	    				    failure : function ()
	    				    {
	    				        me.up().up().setLoading(false);
	    				        Ext.Msg.show({
	    				            title:'Error',
	    				            msg: 'Error de comunicaci&oacute;n',
	    				            buttons: Ext.Msg.OK,
	    				            icon: Ext.Msg.ERROR
	    				        });
	    				    }
	    				});
	    		
	        }
        }
			
    });
    
    medicoConAutorizado = Ext.create('Ext.form.field.ComboBox',
    {
		fieldLabel : 'M&eacute;dico',	allowBlank: false,					displayField : 'nombre',			id:'idmedicoConAutorizado',
		labelWidth: 100,				width:450,							valueField   : 'cdpresta',			forceSelection : false,
		matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.cdpresta',	store : storeMedico,		editable:false,
		triggerAction: 'all',			name:'idmedicoConAutorizado'
	});
    
    medicoEqQuirurg = Ext.create('Ext.form.field.ComboBox',
    {
		fieldLabel : 'M&eacute;dico',	allowBlank: false,					displayField : 'nombre',			id:'idmedicoEqQuirurg',
		labelWidth: 100,				width:450,							valueField   : 'cdpresta',			forceSelection : false,
		matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.cdpresta',	store : storeMedico,		editable:false,
		triggerAction: 'all',			name:'idmedicoEqQuirurg',
		listeners : {
	    	change:function(el) {
	    		storeTipoMedico.load();
	        }
        }
		
	});
    
    //////////////////////////////////////////////////////////
    
    proveedor = Ext.create('Ext.form.field.ComboBox',
    {
    	colspan:2,						fieldLabel : 'Proveedor',			allowBlank: false,				displayField : 'nombre',		name:'cdprovee',
    		id:'idProveedor',				labelWidth: 170,					valueField   : 'cdpresta',			forceSelection : false,	width:500,
    	matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.cdpresta',	store : storeProveedor,			editable:false,
    	triggerAction: 'all'
    });
    
    ////////////////////////////////////////////////
    coberturaAfectada = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel :'Cobertura afectada',	allowBlank: false,				displayField : 'dsgarant',		id:'idCobAfectada',		name:'cdgarant',
    	labelWidth: 170,					valueField   : 'cdgarant',		forceSelection : false,			matchFieldWidth: false,
    	queryMode :'remote',				store : storeCobertura,			triggerAction: 'all',			editable:false,
        listeners : {
	    	change:function(el) {
	    		Ext.getCmp('idSubcobertura').reset();
	    		Ext.getCmp('idSubcobertura').setValue('');
	    		Ext.getCmp('idDeducible').setValue('');
		    	Ext.getCmp('idCopago').setValue('');
	    		storeSubcobertura.removeAll();
		        	storeSubcobertura.load({
		                params:{
		                'params.cdgarant' : el.value
		                }
		            });
	        }
        }
    });
    /** VERIFICAR **/
    subCobertura = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'Subcobertura',	allowBlank: false,					displayField : 'value',			id:'idSubcobertura',		name:'cdconval',
    	labelWidth: 170,				valueField   : 'key',				forceSelection : false,			matchFieldWidth: false,
    	queryMode :'remote',			store : storeSubcobertura,		triggerAction: 'all',				editable:false,
    	listeners : {
	    	change:function(el) {
	    		Ext.getCmp('idDeducible').setValue('');
            	Ext.getCmp('idCopago').setValue('');
	    		Ext.Ajax.request(
	    		        {
	    		            url     : _URL_CONSULTA_DEDUCIBLE_COPAGO
	    		            ,params : 
	    		            {
	    		            	'params.cdunieco':Ext.getCmp('idUnieco').getValue(),
	    		            	'params.estado':Ext.getCmp('idEstado').getValue(),
	    		            	'params.cdramo':Ext.getCmp('idcdRamo').getValue(),
	    		            	'params.nmpoliza':Ext.getCmp('polizaAfectada').getValue(),
	    		            	'params.nmsituac':Ext.getCmp('idNmSituac').getValue(),
	    		            	'params.cdgarant':Ext.getCmp('idCobAfectada').getValue(),
	    		            	'params.subcober':Ext.getCmp('idSubcobertura').getValue(),
	    		            	
	    		            }
	    		            ,success : function (response)
	    		            {
	    		            	var respuesta= Ext.decode(response.responseText);
	    		            	
	    						if(respuesta.listaDatosSiniestro != null)
    							{
	    							var json=Ext.decode(response.responseText).listaDatosSiniestro[0];
    	    		            	Ext.getCmp('idDeducible').setValue(json.deducible);
    	    		            	Ext.getCmp('idCopago').setValue(json.copago);
								}
	    		
	    		            	
	    		            },
	    		            failure : function ()
	    		            {
	    		                me.up().up().setLoading(false);
	    		                Ext.Msg.show({
	    		                    title:'Error',
	    		                    msg: 'Error de comunicaci&oacute;n',
	    		                    buttons: Ext.Msg.OK,
	    		                    icon: Ext.Msg.ERROR
	    		                });
	    		            }
	    		        });
	        }
        }
    });
    
    //storeTiposCPT
    
	

    comboICD = Ext.create('Ext.form.field.ComboBox',
    {
    	colspan:2,						fieldLabel : 'ICD',					allowBlank: false,				displayField : 'value',		width:500,
    	id:'idComboICD',				labelWidth: 170,					valueField   : 'key',			forceSelection : false,
    	matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.cdsubcob',	store : storeTiposICD,
    	name:'cdicd',	editable:false,					triggerAction: 'all'
    });
    
    cptConAutorizado = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'CPT',				allowBlank: false,					displayField : 'value',			id:'cptConAutorizado',
    	width:450,						valueField   : 'key',				forceSelection : false,			matchFieldWidth: false,
    	queryMode :'remote',			queryParam: 'params.cdsubcob',   	store : storeTiposCPT,			triggerAction: 'all',
    	editable:false,					name:'cptConAutorizado',
    	listeners : {
    	    change:function(el) {
    	    	Ext.getCmp('precioConAutorizado').setValue('');
    	        Ext.Ajax.request(
    	        {
    	            url     : _URL_LISTA_TABULADOR
    	            ,params : 
    	            {           
    	                'params.cdcpt':el.value,
    	                'params.cdtipmed':'1',
    	                'params.mtobase':_MONTO_BASE
    	            }
    	            ,success : function (response)
    	            {
    	                if(Ext.decode(response.responseText).listaPorcentaje != null)
	                	{
    	                	var json=Ext.decode(response.responseText).listaPorcentaje[0];
    				        Ext.getCmp('precioConAutorizado').setValue(json.mtomedico);
	                	}
    	            },
    	            failure : function ()
    	            {
    	                me.up().up().setLoading(false);
    	                Ext.Msg.show({
    	                    title:'Error',
    	                    msg: 'Error de comunicaci&oacute;n',
    	                    buttons: Ext.Msg.OK,
    	                    icon: Ext.Msg.ERROR
    	                });
    	            }
    	        });
    	    }
    	}
	});
    
    cptQuirBase = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'CPT',				allowBlank: false,					displayField : 'value',			id:'cptQuirBase',
    	width:450,						valueField: 'key',					forceSelection : false,			matchFieldWidth: false,
    	queryMode :'remote',			queryParam: 'params.cdsubcob',		store : storeTiposCPT,			triggerAction: 'all',
    	editable:false,					name:'cptQuirBase',
    	listeners : {
    	    change:function(el) {
    	    	Ext.getCmp('precioQuirurgico').setValue('');
		        Ext.getCmp('porcentajeQuirurgico').setValue('');
		        Ext.getCmp('importeQuirurgico').setValue('');
    	        Ext.Ajax.request(
    	        {
    	            url     : _URL_LISTA_TABULADOR
    	            ,params : 
    	            {           
    	                'params.cdcpt':el.value,
    	                'params.cdtipmed':'1',
    	                'params.mtobase':_MONTO_BASE
    	            }
    	            ,success : function (response)
    	            {
    	            	if(Ext.decode(response.responseText).listaPorcentaje != null)
	            		{
    	            		var json=Ext.decode(response.responseText).listaPorcentaje[0];
        	                Ext.getCmp('precioQuirurgico').setValue(json.mtomedico);
    				        Ext.getCmp('porcentajeQuirurgico').setValue(json.porcentaje);
    				        
    				        var importeTotal= Ext.getCmp('precioQuirurgico').getValue() * Ext.getCmp('porcentajeQuirurgico').getValue();
    						Ext.getCmp('importeQuirurgico').setValue(importeTotal);
	            		}
    	            },
    	            failure : function ()
    	            {
    	                me.up().up().setLoading(false);
    	                Ext.Msg.show({
    	                    title:'Error',
    	                    msg: 'Error de comunicaci&oacute;n',
    	                    buttons: Ext.Msg.OK,
    	                    icon: Ext.Msg.ERROR
    	                });
    	            }
    	        });
    	    }
    	}
    });
    
	//SUCURSAL
	sucursal= Ext.create('Ext.form.field.ComboBox',
	{
		colspan		:2,					fieldLabel   : 'Sucursal',			id: 'idSucursal',				allowBlank: false,			width:500	
		,editable   : false,			displayField : 'value',				valueField:'key',			    forceSelection : true
		,labelWidth : 170,				queryMode    :'local',				editable:false,					name:'cduniecs'
		,store : Ext.create('Ext.data.Store', {
			model:'Generic',
			autoLoad:true,
			proxy:
			{
				type: 'ajax',
				url:mesConUrlLoadCatalo,
				extraParams : {catalogo:_CAT_AUTORIZACION},
				reader:
				{
					type: 'json',
					root: 'lista'
				}
			}
		})
	});	
	
	
	
	
	tipoMedico = Ext.create('Ext.form.field.ComboBox',
    {
		fieldLabel : 'Tipo m&eacute;dico',	allowBlank: false,					displayField : 'descripc',			id:'idTipoMedico',
		labelWidth: 100,				width:450,							valueField   : 'codigo',			forceSelection : false,
		matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.codigo',	store : storeTipoMedico,		editable:false,
		triggerAction: 'all',			name:'idTipoMedico',
		listeners : {
		    change:function(el) {
		        Ext.Ajax.request(
		                {
		    	            url     : _URL_LISTA_TABULADOR
		    	            ,params : 
		    	            {           
		    	                'params.cdcpt':'28052',
		    	                'params.cdtipmed':el.value,
		    	                'params.mtobase':_MONTO_BASE
		    	            }
		    	            ,success : function (response)
		    	            {
		    	            	if(Ext.decode(response.responseText).listaPorcentaje != null)
			            		{
		    	            		var json=Ext.decode(response.responseText).listaPorcentaje[0];
			    	                Ext.getCmp('importeEqQuirurg').setValue(json.mtomedico);
							        Ext.getCmp('porcentajeEqQuirurg').setValue(json.porcentaje);
			            		}
		    	            },
		    	            failure : function ()
		    	            {
		    	                me.up().up().setLoading(false);
		    	                Ext.Msg.show({
		    	                    title:'Error',
		    	                    msg: 'Error de comunicaci&oacute;n',
		    	                    buttons: Ext.Msg.OK,
		    	                    icon: Ext.Msg.ERROR
		    	                });
		    	            }
		    	        });
		        
		    }
		}
		
	});
	
	//PENALIZACION
	penalizacion = Ext.create('Ext.form.field.ComboBox',
    {
    	colspan:2,						fieldLabel : 'Penalizaci&oacute;n',			allowBlank: false,				displayField : 'descripc',	width:500,
    	id:'idPenalizacion',				labelWidth: 170,					valueField   : 'codigo',			forceSelection : false,
    	matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.codigo',	store : storePenalizacion,			editable:false,
    	name:'porpenal',			triggerAction: 'all'
    });
	
	
	////////////////////////////////////////////////////////////////////////
	/////////// 		PANEL PARA PEDIR LOS REGISTROS				////////
	///////////////////////////////////////////////////////////////////////
	//DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
	var panelConceptosAutorizados= Ext.create('Ext.form.Panel',{
		border  : 0
		,startCollapsed : true
		,bodyStyle:'padding:5px;'
		,items :
		[   			
		 	medicoConAutorizado,
		 	cptConAutorizado,
			{
				id		: 'precioConAutorizado',		xtype      	: 'textfield',			fieldLabel 	: 'Precio',					labelWidth: 100,
				width	:350,							name		:'precioConAutorizado',
				allowBlank	: false,					allowDecimals :true,          			decimalSeparator :'.',
				listeners:{
					change:function(field,value)
					{
						try
						{
							var importeTotal= Ext.getCmp('precioConAutorizado').getValue() * Ext.getCmp('cantidadConAutorizado').getValue();
							Ext.getCmp('importeConAutorizado').setValue(importeTotal);
						}catch(e){}
					}
				}
			},
			{
				id		: 'cantidadConAutorizado',		xtype      	: 'numberfield',			fieldLabel 	: 'Cantidad',				labelWidth: 100,			width:350,
				name    : 'cantidadConAutorizado',		allowBlank	: false,
				listeners:{
					change:function(field,value)
					{
						try
						{
							var importeTotal= Ext.getCmp('precioConAutorizado').getValue() * Ext.getCmp('cantidadConAutorizado').getValue();
							Ext.getCmp('importeConAutorizado').setValue(importeTotal);
						}catch(e){}
					}
				}
			},
			{
				id		: 'importeConAutorizado',		xtype      	: 'numberfield',			fieldLabel 	: 'Importe',				labelWidth: 100,			width:350,
				name    : 'importeConAutorizado',		allowBlank	: false,					allowDecimals :true,          			decimalSeparator :'.'
			}
		]
	});
	
	//DATOS PARA EL SEGUDO GRID --> EQUIPO QUIRURGICO BASE
	var panelEquipoQuirurgicoBase= Ext.create('Ext.form.Panel',{
		border  : 0
		,bodyStyle:'padding:5px;'
		,items :
		[              
		 	cptQuirBase
			,
			{
			id		: 'precioQuirurgico',				xtype      	: 'numberfield',			fieldLabel 	: 'Precio',					labelWidth: 100,
			name    : 'precioQuirurgico',				allowBlank	: false,					allowDecimals :true,          			decimalSeparator :'.',
			editable:false
			
			}
			,
			{
			id		: 'porcentajeQuirurgico',			xtype      	: 'numberfield',		fieldLabel 	: 'Porcentaje',				labelWidth: 100,
			name    : 'porcentajeQuirurgico',			allowBlank	: false,				editable:false,							decimalSeparator :'.'
			}
			,
			{
			id		: 'importeQuirurgico',				xtype      	: 'numberfield',		fieldLabel 	: 'Importe',				labelWidth: 100,
			name    : 'importeQuirurgico',				allowBlank	:false,					allowDecimals :true,          			decimalSeparator :'.',
			editable:false
			}
		]
	});
	
	
	//DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO
	var panelEquipoQuirurgico= Ext.create('Ext.form.Panel',{
		border  : 0
		,bodyStyle:'padding:5px;'
		,items :
		[ 
		 	medicoEqQuirurg,
		 	tipoMedico
		 	,
		 	{
		 		id		: 'porcentajeEqQuirurg',			xtype      	: 'numberfield',	fieldLabel 	: 'Porcentaje',		labelWidth: 100,
		 		name    : 'porcentajeEqQuirurg',			allowBlank	: false,			decimalSeparator :'.'
		 	}
		 	,
		 	{
		 		id		: 'importeEqQuirurg',			xtype      	: 'numberfield',		fieldLabel 	: 'Importe',		labelWidth: 100,
		 		name    : 'importeEqQuirurg',			allowBlank	: false,				allowDecimals :true,     		decimalSeparator :'.'
		 	}
		]
	});
	
	////////////////////////////////////////////////////////////////////////////
	/////////// 		VENTANA PARA PEDIR LOS REGISTROS				////////
	///////////////////////////////////////////////////////////////////////////
	//DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
	ventanaConceptosAutorizado= Ext.create('Ext.window.Window', {
		renderTo: document.body,
		title: 'Conceptos Autorizados',
		//height: 200,
		closeAction: 'hide',           
		items:[panelConceptosAutorizados],
		buttonAlign : 'center',
		buttons:[
	        {
	        	text: 'Aceptar',
	        	icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
	        	handler: function() {
	        		if (panelConceptosAutorizados.form.isValid()) {			
	        			var datos=panelConceptosAutorizados.form.getValues();
	        			
	        			var rec = new modelListadoTablas({
	        					cdmedico: datos.idmedicoConAutorizado,
	        					nombreMedico: medicoConAutorizado.rawValue,
	        					cdcpt: datos.cptConAutorizado,
	        					desccpt:cptConAutorizado.rawValue,
	        					precio: datos.precioConAutorizado,
	        					cantporc: datos.cantidadConAutorizado,
	        					ptimport: datos.importeConAutorizado,
	        					cdtipaut:'1'
	        						
	    				});
	        			storeConceptoAutorizados.add(rec);
	        			panelConceptosAutorizados.getForm().reset();
	        		} else {
	        			Ext.Msg.show({
	        				title: 'Aviso',
	        				msg: 'Complete la informaci&oacute;n requerida',
	        				buttons: Ext.Msg.OK,
	        				icon: Ext.Msg.WARNING
	        			});
	        		}
	        	}
		},
		{
			text: 'Cancelar',
			icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
			handler: function(){
				panelConceptosAutorizados.getForm().reset();
				ventanaConceptosAutorizado.close();
			}
		}
	]
	});
	
	//DATOS PARA EL SEGUDO GRID --> EQUIPO QUIRURGICO BASE
	
	ventanaEqQuirurgicoBase= Ext.create('Ext.window.Window', {
		renderTo: document.body,
		title: 'Equipo quir&uacute;rgico base',
		//height: 270,
		closeAction: 'hide',
		items:[panelEquipoQuirurgicoBase],		
		buttonAlign : 'center',
		buttons:[{
			text: 'Aceptar',
			icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
			handler: function() {
				
				if (panelEquipoQuirurgicoBase.form.isValid()){
					var datos=panelEquipoQuirurgicoBase.form.getValues();
					var rec = new modelListadoTablas({
						cdcpt: datos.cptQuirBase,
						desccpt:cptQuirBase.rawValue,
						precio: datos.precioQuirurgico,
						cantporc: datos.porcentajeQuirurgico,
						ptimport: datos.importeQuirurgico,
						cdtipaut:'2'
						
					});
					storeQuirugicoBase.add(rec);
					panelEquipoQuirurgicoBase.getForm().reset();
				} else {
					Ext.Msg.show({
						title: 'Aviso',
						msg: 'Complete la informaci&oacute;n requerida',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.WARNING
					});
				}
			}
		},
		{
			text: 'Cancelar',
			icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
			handler: function() {
				panelEquipoQuirurgicoBase.getForm().reset();
				ventanaEqQuirurgicoBase.close();
			}
		}
	]
	});
	
	//DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO
	
	ventanaEqQuirurgico= Ext.create('Ext.window.Window', {
		renderTo: document.body,
		title: 'Equipo quir&uacute;rgico',
		//height: 270,
		closeAction: 'hide',
		items:[panelEquipoQuirurgico],		
		buttonAlign : 'center',
		buttons:[{
			text: 'Aceptar',
			icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
			handler: function() {
				
				if (panelEquipoQuirurgico.form.isValid()){
					var datos=panelEquipoQuirurgico.form.getValues();
					var rec = new modelListadoTablas({
						cdmedico: datos.idmedicoEqQuirurg,
						nombreMedico:medicoEqQuirurg.rawValue,
						cantporc: datos.porcentajeEqQuirurg,
						ptimport: datos.importeEqQuirurg,
						cdtipmed:datos.idTipoMedico,
						descTipMed:tipoMedico.rawValue,
						cdtipaut:'3'
					});
					storeQuirurgico.add(rec);
					panelEquipoQuirurgico.getForm().reset();
				} else {
					Ext.Msg.show({
						title: 'Aviso',
						msg: 'Complete la informaci&oacute;n requerida',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.WARNING
					});
				}
			}
		},
		{
			text: 'Cancelar',
			icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
			handler: function() {
				panelEquipoQuirurgico.getForm().reset();
				ventanaEqQuirurgico.close();
			}
		}
	]
	});
	
	//////////////////////////////////////////////////////////
	////////// EDITOR PARA MANIPULACION DE LAS TABLAS  ///////
	//////////////////////////////////////////////////////////
	
	//DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
	Ext.define('EditorIncisos', {
		extend: 'Ext.grid.Panel',
		collapsible   : true,
		titleCollapse : true,
		requires: [
		           'Ext.selection.CellModel',
		           'Ext.grid.*',
		           'Ext.data.*',
		           'Ext.util.*',
		           'Ext.form.*'
		           ],
       xtype: 'cell-editing',
       title: 'Conceptos Autorizados',
       frame: false,
       initComponent: function(){
    	   this.cellEditing = new Ext.grid.plugin.CellEditing({
    		   clicksToEdit: 1
    	   });
    	   Ext.apply(this, {
    		   height: 200,
    		   plugins: [this.cellEditing],
    		   store: storeConceptoAutorizados,
    		   columns: 
    			   [
	    			    
	    			    {	
	    			    	header: 'M&eacute;dico',			dataIndex: 'nombreMedico',			width:250
	    			    },
	    			    
	    			    {
	    			    	header: 'CPT',						dataIndex: 'desccpt',				width:250
	    			    },
	    			    {
	    			    	header: 'Precio',					dataIndex: 'precio',			width:150,				renderer: Ext.util.Format.usMoney
	    			    },
	    			    {
	    			    	header: 'Cantidad', 				dataIndex: 'cantporc',		 	width:150
	    			    },
	    			    {
	    			    	header: 'Importe', 					dataIndex: 'ptimport',		 	width:150,				renderer: Ext.util.Format.usMoney
	    			    },
	    			    {
	    			    	xtype: 'actioncolumn',				width: 30,					 	sortable: false,	 	menuDisabled: true,
	    			    	items: [{
	    			    		icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
	    			    		tooltip: 'Quitar conceptos autorizados',
	    			    		scope: this,
	    			    		handler: this.onRemoveClick
	    			    	}]
	    			    }
    			    ],
    			    selModel: {
    			    	selType: 'cellmodel'
    			    },
    			    tbar: [{
    			    	icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
    			    	text: 'Agregar conceptos autorizados',
    			    	scope: this,
    			    	handler: this.onAddClick
    			    }]
    	   });
    	   this.callParent();
	   },
	   onAddClick: function(btn, e){
		   ventanaConceptosAutorizado.animateTarget=btn;
		   ventanaConceptosAutorizado.show();
	   },
	   onRemoveClick: function(grid, rowIndex){
		   var record=this.getStore().getAt(rowIndex);
		   this.getStore().removeAt(rowIndex);
	   }
	});
	
	gridIncisos=new EditorIncisos();
		
		
	
	//DATOS PARA EL SEGUDO GRID --> EQUIPO QUIRURGICO BASE
	Ext.define('EditorIncisos2', {
		extend: 'Ext.grid.Panel',
		collapsible   : true,
		titleCollapse : true,
		requires: [
		           'Ext.selection.CellModel',
		           'Ext.grid.*',
		           'Ext.data.*',
		           'Ext.util.*',
		           'Ext.form.*'
		           ],
       xtype: 'cell-editing',
       title: 'Equipo quir&uacute;rgico base',
       frame: false,
       initComponent: function(){
    	   this.cellEditing = new Ext.grid.plugin.CellEditing({
    		   clicksToEdit: 1
    	   });
	
	   Ext.apply(this, {
		   height: 200,
		   plugins: [this.cellEditing],
		   store: storeQuirugicoBase,
		   columns: 
			   [	
			    	{
			    		header: 'CPT',						dataIndex: 'desccpt',				width:250				 		
			    	},
			    	{
			    		header: 'Precio',					dataIndex: 'precio',			width:150,				renderer: Ext.util.Format.usMoney
			    	},
			    	{
			    		header: 'Porcentaje', 				dataIndex: 'cantporc',		width:150
			    	},
			    	{
			    		header: 'Importe', 					dataIndex: 'ptimport',		 	width:150,				renderer: Ext.util.Format.usMoney
			    	},
			    	{
			    		xtype: 'actioncolumn',				width: 30,						sortable: false,		menuDisabled: true,
			    		items: [{
			    			icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
			    			tooltip: 'Quitar inciso',
			    			scope: this,
			    			handler: this.onRemoveClick
			    		}]
			    	}
	    	],
	    	selModel: {
	    		selType: 'cellmodel'
	    	},
	    	tbar: [{
	    		icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
	    		text: 'Agregar equipo quir&uacute;gico',
	    		scope: this,
	    		handler: this.onAddClick
	    	}]
	   });
	   this.callParent();
       },
	
       onAddClick: function(btn, e){
    	   ventanaEqQuirurgicoBase.animateTarget=btn;
    	   ventanaEqQuirurgicoBase.show();
       },
       onRemoveClick: function(grid, rowIndex){
    	   var record=this.getStore().getAt(rowIndex);
    	   this.getStore().removeAt(rowIndex);
       }
});

	
	gridIncisos2=new EditorIncisos2();
	
	
	//DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO
	Ext.define('EditorIncisos3', {
		extend: 'Ext.grid.Panel',
		collapsible   : true,
		titleCollapse : true,
		requires: [
		           'Ext.selection.CellModel',
		           'Ext.grid.*',
		           'Ext.data.*',
		           'Ext.util.*',
		           'Ext.form.*'
		           ],
       xtype: 'cell-editing',
       title: 'Equipo quir&uacute;rgico',
       frame: false,
       initComponent: function(){
    	   this.cellEditing = new Ext.grid.plugin.CellEditing({
    		   clicksToEdit: 1
    	   });
	
	   Ext.apply(this, {
		   height: 200,
		   plugins: [this.cellEditing],
		   store: storeQuirurgico,
		   columns: 
			   [	{
			    		header: 'M&eacute;dico',		dataIndex: 'nombreMedico',	 		width:250	
			    	},
			    	{
			    		header: 'Porcentaje',			dataIndex: 'cantporc',		width:150				 		
			    	},
			    	{
			    		//<<
			    		header: 'Tipo medico',				dataIndex: 'descTipMed',	 		width:250
			    	},
			    	{
			    		header: 'Importe',				dataIndex: 'ptimport',	 		width:250,					renderer: Ext.util.Format.usMoney
			    	},						
			    	{
			    		xtype: 'actioncolumn',				width: 30,						sortable: false,		menuDisabled: true,
			    		items: [{
			    			icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
			    			tooltip: 'Quitar inciso',
			    			scope: this,
			    			handler: this.onRemoveClick
			    		}]
			    	}
		    	],
		    	selModel: {
		    		selType: 'cellmodel'
		    	},
		    	tbar: [{
		    		icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
		    		text: 'Agregar equipo quir&uacute;gico',
		    		scope: this,
		    		handler: this.onAddClick
		    	}]
	   });
	   this.callParent();
       },
	
       onAddClick: function(btn, e){
    	   ventanaEqQuirurgico.animateTarget=btn;
    	   ventanaEqQuirurgico.show();
       },
       onRemoveClick: function(grid, rowIndex){
    	   var record=this.getStore().getAt(rowIndex);
    	   this.getStore().removeAt(rowIndex);
       }
	});

	gridIncisos3=new EditorIncisos3();

	
	
	
	
	

	
	
/*#####################################################################################################################################################
###################################################### 		 DATOS PARA LA BUSQUEDA INICIAL 	#######################################################
#######################################################################################################################################################*/
	
	codigoAsegurado= Ext.create('Ext.form.ComboBox',
	{
		//colspan:2,
		id:'tipoAutorizacion',				fieldLabel: 'Tipo autorizaci&oacute;n',		store: storeTipoAutorizacion,
		queryMode:'local',					displayField: 'value',						valueField: 'key',					allowBlank:false,
		blankText:'Es un dato requerido',	editable:false,								labelWidth : 170,
		width: 400,							emptyText:'Seleccione Autorizaci&oacute;n ...',
		listeners : {
			'select' : function(combo, record) {
					closedStatusSelectedID = this.getValue();
					Ext.getCmp('claveTipoAutoriza').setValue(closedStatusSelectedID);
					if(closedStatusSelectedID !=1){
						Ext.getCmp('panelbusqueda').show();
						Ext.getCmp('clausulasGridId').show();						
					}else{
						Ext.getCmp('panelbusqueda').hide();
						Ext.getCmp('clausulasGridId').hide();
					} 
					
					
				}
			}
	});
	
	
	panelbusquedas= Ext.create('Ext.panel.Panel',
	{
		border  : 0,		id     : 'panelbusqueda',				width	: 600,		style  : 'margin:5px'
		,items :
			[
			 Ext.create('Ext.form.field.ComboBox',
			 {
				 fieldLabel 	: 'Nombre/C&oacute;digo asegurado',			allowBlank		: false,		displayField 	: 'value'
				 ,id			: 'idCodigoAsegurado',						labelWidth		: 170,			width		 	: 500
				 ,valueField   	: 'key',									forceSelection 	: false,		matchFieldWidth	: false
				 ,minChars  	: 3,										queryMode 		:'remote'		,queryParam: 'params.cdperson'
				 ,store : storeAsegurados
				 ,triggerAction: 'all'
			 })
			 ]
		,buttonAlign: 'center'
		,buttons : [{
			text: "Buscar"
			,icon:_CONTEXT+'/resources/fam3icons/icons/magnifier.png'
			,handler: function(){
				if (panelClausula.form.isValid()) {
					storeListadoAsegurado.removeAll();
					var params = {
							'params.cdperson' : Ext.getCmp('idCodigoAsegurado').getValue(),
							'params.tipoAut' : Ext.getCmp('tipoAutorizacion').getValue()
					};
					
					storeSiniestros.load();// --> Verificar que no se carga el valor al momento de pasarle el valor para que se visualice correctamente
					storeMedico.load();
					storeProveedor.load();
					storeSubcobertura.load();
					storePenalizacion.load();
					storeTiposICD.load();
					
					cargaStorePaginadoLocal(storeListadoAsegurado, _URL_CONSULTA_LISTADO_AUTORIZACION, 'listaAutorizacion', params, function(options, success, response){
						if(success){
							var jsonResponse = Ext.decode(response.responseText);
							if(jsonResponse.listaAutorizacion == null) {								
								Ext.Msg.show({
									title: 'Aviso',
									msg: 'No se encontraron datos.',
									buttons: Ext.Msg.OK,
									icon: Ext.Msg.WARNING
								});
								return;
							}
							
						}else{
							Ext.Msg.show({
								title: 'Aviso',
								msg: 'Error al obtener los datos.',
								buttons: Ext.Msg.OK,
								icon: Ext.Msg.ERROR
							});
						}
					});
				} else {
					Ext.Msg.show({
						title: 'Aviso',
						msg: 'Complete la informaci&oacute;n requerida',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.WARNING
					});
				}
			}	
		}] 
	});
	
	gridDatos= Ext.create('Ext.grid.Panel',
	{
		id             : 'clausulasGridId'
		//,title         : 'Hist&oacute;rico de Reclamaciones'
		,store         :  storeListadoAsegurado
		,collapsible   : true
		,titleCollapse : true
		,style         : 'margin:5px'
		,width   : 600
		,height: 200
		,columns       :
		[
			 {
				 header     : 'N&uacute;mero <br/> Autorizaci&oacute;n'
				 ,dataIndex : 'nmautser'
				 ,width	 	: 100
			 },
			 {
				 header     : 'N&uacute;mero <br/> Autorizaci&oacute;n Anterior'
				 ,dataIndex : 'nmautant'
				 ,width     : 200
			 }
			 ,
			 {
				 header     : 'Fecha Solicitud'
				 ,dataIndex : 'fesolici'
				 ,width     : 100
			 },
			 {
				 header     : 'P&oacute;liza <br/> Afectada'
				 ,dataIndex : 'polizaafectada'
				 ,width	    : 100
			 }
			 ,
			 {
				 header     : 'Clave Proveedor'
				 ,dataIndex : 'cdprovee'
				 ,width	    : 100
			 }
			 ,
			 {
				 header     : 'Nombre del Proveedor'
				 ,dataIndex : 'nombreProveedor'
				 ,width	   : 200
			 }
		 ],
		 bbar     :
		 {
			 displayInfo : true,
			 store       : storeListadoAsegurado,
			 xtype       : 'pagingtoolbar'
		 }
	});
	
	/*OCULTAMOS LA INFORMACION DEL GRID Y DE LA BUSQUEDA*/
	
	Ext.getCmp('panelbusqueda').hide();
	Ext.getCmp('clausulasGridId').hide();
	
	panelClausula= Ext.create('Ext.form.Panel', {
		id: 'panelClausula',
		bodyPadding: 5,
		renderTo: Ext.getBody(),
		defaults 	:
		{	
			style : 'margin:5px;'
		}
		,
		items: [
		        codigoAsegurado 	// <-- contiene los valores de los combo
		        ,panelbusquedas 	// <-- contiene el formulario para la busqueda de acuerdo al codigo del asegurado
		        ,gridDatos 			// <-- Contiene la información de los asegurados
			],
		buttonAlign:'center',
		buttons: [{
			text: "Editar"
			,icon:_CONTEXT+'/resources/fam3icons/icons/application_edit.png'
			,handler: function(){
				idTipoAutorizacion= Ext.getCmp('tipoAutorizacion').getValue();
				if(idTipoAutorizacion !=3)
				{
					Ext.getCmp('idNumeroAnterior').hide();
					Ext.getCmp('btnBuscar').hide();
				}else{
					Ext.getCmp('idNumeroAnterior').show();
					Ext.getCmp('btnBuscar').show();
				}
				
				
				if(idTipoAutorizacion !=1)
				{
					if(Ext.getCmp('clausulasGridId').getSelectionModel().hasSelection()){
						var rowSelected = Ext.getCmp('clausulasGridId').getSelectionModel().getSelection()[0];
						var nmautser= rowSelected.get('nmautser');
						Ext.Ajax.request(
						{
						    url     : _URL_LISTADO_CONCEP_EQUIP
						    ,params : 
							{
								'params.nmautser'  : nmautser
							}
						    ,success : function (response)
						    {
						    	if(Ext.decode(response.responseText).listaConsultaTablas != null)
			            		{
						    		var json=Ext.decode(response.responseText);
							        
							        for(var i=0;i<json.listaConsultaTablas.length;i++)
				                    {
				                        /*OBTENEMOS LOS VALORES*/
				                        var rec = new modelListadoTablas({
			                        		nmautser: json.listaConsultaTablas[i].nmautser,
			                        		cdtipaut:json.listaConsultaTablas[i].cdtipaut,
			                        		cdmedico:json.listaConsultaTablas[i].cdmedico,
			                        		nombreMedico:json.listaConsultaTablas[i].nombreMedico,
			                        		desccpt:json.listaConsultaTablas[i].desccpt,
			                        		precio:json.listaConsultaTablas[i].precio,
			                        		cdtipmed:json.listaConsultaTablas[i].cdtipmed,
			                        		cdcpt:json.listaConsultaTablas[i].cdcpt,
			                        		cantporc:json.listaConsultaTablas[i].cantporc,
			                        		ptimport:json.listaConsultaTablas[i].ptimport,
			                        		descTipMed:json.listaConsultaTablas[i].descTipMed
					    				});
				                        if(json.listaConsultaTablas[i].cdtipaut==1)
			                        	{
				                        	storeConceptoAutorizados.add(rec);
			                        	}
				                        if(json.listaConsultaTablas[i].cdtipaut==2)
			                        	{
				                        	storeQuirugicoBase.add(rec);
			                        	}
				                        
				                        if(json.listaConsultaTablas[i].cdtipaut==3)
			                        	{
				                        	storeQuirurgico.add(rec);
			                        	}
				                    }
			            		}
						    },
						    failure : function ()
						    {
						        me.up().up().setLoading(false);
						        Ext.Msg.show({
						            title:'Error',
						            msg: 'Error de comunicaci&oacute;n',
						            buttons: Ext.Msg.OK,
						            icon: Ext.Msg.ERROR
						        });
						    }
						});
						
						Ext.Ajax.request(
								{
									url     : _URL_CONSULTA_AUTORIZACION_ESP
									,params : 
									{
										'params.nmautser'  : nmautser
									}
								,success : function (response)
								{
									
									
									
									var json=Ext.decode(response.responseText).datosAutorizacionEsp;
									
									if(Ext.getCmp('claveTipoAutoriza').getValue() == 3 )
									{
										//Número de autorización
										Ext.getCmp('idNoAutorizacion').setValue();
										
										//Número de autorizacion Anterior
										Ext.getCmp('idNumeroAnterior').setValue(json.nmautser);
									
									}else{
										//Número de autorización
										Ext.getCmp('idNoAutorizacion').setValue(json.nmautser);
										
										//Número de autorizacion Anterior
										Ext.getCmp('idNumeroAnterior').setValue(json.nmautant);
									}
									
									//INFORMACION DEL ASEGURADO
									Ext.getCmp('idAsegurado').setValue(json.cdperson);
										//console.log(json.nombreCliente);
									
									//Fecha Solicitud
									Ext.getCmp('fechaSolicitud').setValue(json.fesolici);
									
									//Fecha Autorización
									Ext.getCmp('fechaAutorizacion').setValue(json.feautori);
									
									//Fecha Afectado
									Ext.getCmp('fechaVencimiento').setValue(json.fevencim);
									
									//Fecha de Ingreso
									Ext.getCmp('fechaIngreso').setValue(json.feingres);
									
									// Póliza Afectada
									Ext.getCmp('polizaAfectada').setValue(json.nmpoliza);									
									
									//Proveedor
									Ext.getCmp('idProveedor').setValue(json.cdprovee);
										//console.log(json.nombreProveedor);
									
									//Médico
									Ext.getCmp('idMedico').setValue(json.cdmedico);
										//console.log(json.nombreMedico);
									
									//Causa Siniestro
									Ext.getCmp('idCausaSiniestro').setValue(json.cdcausa);
										//console.log(json.descCausa);
									
									//Tratamiento
									Ext.getCmp('tratamiento').setValue(json.dstratam);
									
									//Observaciones
									Ext.getCmp('observaciones').setValue(json.dsobserv);
									
									//Nota Interes
									Ext.getCmp('notaInterna').setValue(json.dsnotas);
									
									Ext.getCmp('idCobAfectada').setValue(json.cdgarant);
									
									Ext.getCmp('idUnieco').setValue(json.cdunieco);
									Ext.getCmp('idEstado').setValue(json.estado);
									Ext.getCmp('idcdRamo').setValue(json.cdramo);
									Ext.getCmp('idNmSituac').setValue(json.nmsituac);
									
									
									Ext.getCmp('idSubcobertura').setValue(json.cdconval);
									
									Ext.getCmp('idComboICD').setValue(json.cdicd);
									
									Ext.getCmp('sumDisponible').setValue(json.mtsumadp);
									
									Ext.getCmp('idPenalizacion').setValue(json.porpenal);
									
									Ext.getCmp('idSucursal').setValue(json.cduniecs);
									
									//CARGO LOS VALORES DE COBERTURA SEGUN LOS DATOS DE ENTRADA
									storeCobertura.load({
					                    params:{
					                    	'params.cdunieco':Ext.getCmp('idUnieco').getValue(),
							            	'params.estado':Ext.getCmp('idEstado').getValue(),
							            	'params.cdramo':Ext.getCmp('idcdRamo').getValue(),
							            	'params.nmpoliza':Ext.getCmp('polizaAfectada').getValue(),
							            	'params.nmsituac':Ext.getCmp('idNmSituac').getValue()
					                    }
									});
									
									storeCobertura.removeAll();
									
									storeSiniestros.load();
									
									//OBTENEMOS LOS VALORES DE DEDUCIBLE Y COPAGO
							        Ext.Ajax.request(
							        {
							            url     : _URL_CONSULTA_DEDUCIBLE_COPAGO
							            ,params : 
							            {
							            	'params.cdunieco':Ext.getCmp('idUnieco').getValue(),
							            	'params.estado':Ext.getCmp('idEstado').getValue(),
							            	'params.cdramo':Ext.getCmp('idcdRamo').getValue(),
							            	'params.nmpoliza':Ext.getCmp('polizaAfectada').getValue(),
							            	'params.nmsituac':Ext.getCmp('idNmSituac').getValue(),
							            	'params.cdgarant':Ext.getCmp('idCobAfectada').getValue(),
							            	'params.subcober':Ext.getCmp('idSubcobertura').getValue()
							            	
							            }
							            ,success : function (response)
							            {
							            	if(Ext.decode(response.responseText).listaDatosSiniestro != null)
						            		{
							            		var json=Ext.decode(response.responseText).listaDatosSiniestro[0];							            	
								            	Ext.getCmp('idDeducible').setValue(json.deducible);
								            	Ext.getCmp('idCopago').setValue(json.copago);
						            		}
							            },
							            failure : function ()
							            {
							                me.up().up().setLoading(false);
							                Ext.Msg.show({
							                    title:'Error',
							                    msg: 'Error de comunicaci&oacute;n',
							                    buttons: Ext.Msg.OK,
							                    icon: Ext.Msg.ERROR
							                });
							            }
							        });
									modificacionClausula.hide();
								},
								failure : function ()
								{
									me.up().up().setLoading(false);
									Ext.Msg.show({
										title:'Error',
										msg: 'Error de comunicaci&oacute;n',
										buttons: Ext.Msg.OK,
										icon: Ext.Msg.ERROR
									});
								}
								});
					}else {
						//Ext.Msg.alert('Aviso', 'Debe de seleccionar una cl&aacute;usula para realizar la edici&oacute;n');
						Ext.Msg.show({
							title: 'Aviso',
							msg: 'Debe de seleccionar un registro para realizar la edici&oacute;n',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.ERROR
						});
						
					}
				}else
				{
					modificacionClausula.hide();
				}
				
			}
		}
	]
});
	
modificacionClausula = Ext.create('Ext.window.Window',
{
	title        : 'Modo de Autorizaci&oacute;n'
	,modal       : true
	,buttonAlign : 'center'
	,closable : false
	,width		 : 650
	,minHeight 	 : 100 
	,maxheight      : 400
	,items       :
		[
		 	panelClausula
		 ]
}).showAt(50,100);



/*################################################################################################################################################
###################################################### 			 PANTALLA 	PRINCIPAL 		 #####################################################
##################################################################################################################################################*/
	var panelInicialPrincipal= Ext.create('Ext.form.Panel',
	{
		border    : 0
		,title: 'Autorizaci&oacuten de Servicios'
		,renderTo : 'div_clau'
		,id : 'panelInicialPrincipal'
		,bodyPadding: 5
		,width: 750
		,url: _URL_GUARDA_AUTORIZACION
		,layout     :
		{
			type     : 'table'
			,columns : 2
		}
		,defaults 	:
		{
			style : 'margin:5px;'
		}
		,
		items    	:
			[
			 	//se agrupa el No. de autorización , el No. de autorización anterior y el botón de buscar
			 	{
	 				 xtype       : 'textfield',			fieldLabel : 'Tipo Autorizacion'				,id       : 'claveTipoAutoriza',				name       : 'claveTipoAutoriza'
					 ,allowBlank : false,				labelWidth: 170,	hidden:true
	 			},
	 			{
	 				 xtype       : 'textfield',			fieldLabel : 'Unieco'				,id       : 'idUnieco',				name       : 'cdunieco'
					 ,allowBlank : false,				labelWidth: 170,	hidden:true
	 			},
	 			{
	 				 xtype       : 'textfield',			fieldLabel : 'estado'				,id       : 'idEstado',				name       : 'estado'			
					 ,allowBlank : false,				labelWidth: 170,	hidden:true
	 			},
	 			{
	 				 xtype       : 'textfield',			fieldLabel : 'Ramo'				,id       : 'idcdRamo',					name       : 'cdramo'
					 ,allowBlank : false,				labelWidth: 170,	hidden:true
	 			},	 			
	 			{
	 				 xtype       : 'textfield',			fieldLabel : 'nmsituac'				,id       : 'idNmSituac',			name       : 'nmsituac'
					 ,allowBlank : false,				labelWidth: 170,	hidden:true
	 			},
	 			{
					id: 'idAaapertu'					,xtype		: 'datefield'								,fieldLabel	: 'aaapertu',
					labelWidth : 170						,name		: 'aaapertu'					,format		: 'Y',
					editable: true							,value		: new Date(),	hidden:true
				}
	 			,
	 			{
	 				 xtype       : 'textfield',			fieldLabel : 'status'				,id       : 'idstatus',			name       : 'status'
					 ,allowBlank : false,				labelWidth: 170	,					value:'1',		hidden:true
	 			},
	 			{
					id: 'idFesistem'					,xtype		: 'datefield'								,fieldLabel	: 'fesistem',
					labelWidth : 170						,name		: 'fesistem'					,format		: 'd/m/Y',
					editable: true							,value		: new Date(),		hidden:true
				}
	 			,
	 			{
	 				 colspan:2,	xtype       : 'textfield',			fieldLabel : 'cduser'				,id       : 'idCduser',			name       : 'cduser'
					 ,allowBlank : false,				labelWidth: 170,					value:'ICE',	hidden:true
	 			},
			 	
			 	{
			 		colspan:2
			 		,border: false
			 		,layout     :
			 		{
			 			type     : 'table'
		 				,columns : 3
			 		},				        
			 		items    :
			 			[
			 			 	{
		 			 			xtype       : 'textfield',			fieldLabel : 'No. de autorizaci&oacute;n',				id  : 'idNoAutorizacion',
		 			 			name       : 'nmautser',		labelWidth	: 170,					readOnly   : true
			 			 	},
			 			 	{
			 			 		xtype       : 'textfield'		    ,fieldLabel : 'No. de autorizaci&oacute;n anterior',	id	: 'idNumeroAnterior',	
			 			 		name       : 'nmautant'		,labelWidth	: 170,		readOnly   : true
			 			 	}
			 			 	,
			 			 	Ext.create('Ext.Button', {
				 				 text: 'Buscar',
				 				 id:'btnBuscar',
				 				icon : _CONTEXT + '/resources/fam3icons/icons/folder.png',
				 				 
				 				 
				 				handler: function() {
				 					 Ext.create('Ext.window.Window',
		 							 {
			 						 	title        : 'Autorizaci&oacute;n de servicio'
	 						 			//,modal       : true
		 						 		,buttonAlign : 'center'
	 						 			,width       : 750
	 						 			,height      : 400
	 						 			,autoScroll  : true
	 						 			,loader      :
	 						 			{
	 						 				url       : _VER_AUTORIZACION_SERVICIO
	 						 				,scripts  : true
	 						 				,autoLoad : true
	 						 				,params:{
						                    	'params.nmautser':Ext.getCmp('idNumeroAnterior').getValue()
						                    }
	 						 			},
		 							 }).showAt(150,150);
				 				 }
				 			 })	
			 			 	/*Ext.create('Ext.Button',{
			 			 			text: 'Buscar',
			 			 			id:'btnBuscar',
			 			 			icon : _CONTEXT + '/resources/fam3icons/icons/folder.png'
			 			 			//falta la implementacion del boton, para cuando la busqueda se encuentre
			 			 	})*/
		 			 	]
			 	}
			 	,
			 	asegurado
			 	,
			 	{
			 		id: 'fechaSolicitud'		,xtype		: 'datefield'								,fieldLabel	: 'Fecha Solicitud',
			 		name:'fesolici'				,labelWidth : 170						,id 		: 'fechaSolicitud'							,format		: 'd/m/Y',
			 		editable: true				,value		: new Date()								,readOnly   : true
			 	},
			 	{
			 		id: 'fechaAutorizacion'					,xtype		: 'datefield'								,fieldLabel	: 'Fecha Autorizaci&oacute;n',
			 		labelWidth : 170						,format		: 'd/m/Y',
			 		editable: true,							name		: 'feautori',
			 		listeners:{
			 			change:function(field,value)
			 			{
			 				try
			 				{
			 					Ext.getCmp('fechaVencimiento').setValue(Ext.Date.add(value, Ext.Date.DAY, 15));
			 				}catch(e){}
			 			}
			 		}
			 	},
			 	{
			 		id: 'fechaVencimiento'					,xtype		: 'datefield'								,fieldLabel	: 'Fecha de vencimiento',
			 		labelWidth : 170						,format		: 'd/m/Y',									name:'fevencim',
			 		editable: false							,readOnly   : true
			 	},
			 	{
			 		id: 'fechaIngreso'						,xtype		: 'datefield'								,fieldLabel	: 'Fecha de Ingreso',
			 		labelWidth : 170						,format		: 'd/m/Y',									name:'feingres',
			 		editable: true
			 	},
			 	{
			 		colspan:2
			 		,border: false
			 		,layout     :
			 		{
			 			type     : 'table'
		 				,columns : 3
			 		},
			 		items    :
			 			[
				 			 {
				 				 xtype       : 'textfield',			fieldLabel : 'P&oacute;liza afectada'				,id       : 'polizaAfectada'
			 					 ,allowBlank : false,				labelWidth: 170,				name:'nmpoliza'
				 			 }
				 			 ,
				 			 Ext.create('Ext.Button', {
				 				 text: 'Ver coberturas',
				 				 icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png',
				 				 
				 				 
				 				handler: function() {
				 					 Ext.create('Ext.window.Window',
		 							 {
			 						 	title        : 'COBERTURA'
	 						 			//,modal       : true
		 						 		,buttonAlign : 'center'
	 						 			,width       : 620
	 						 			,height      : 400
	 						 			,autoScroll  : true
	 						 			,loader      :
	 						 			{
	 						 				url       : _VER_COBERTURAS
	 						 				,scripts  : true
	 						 				,autoLoad : true
	 						 				,params:{
						                    	'params.cdunieco':Ext.getCmp('idUnieco').getValue(),
								            	'params.estado':Ext.getCmp('idEstado').getValue(),
								            	'params.cdramo':Ext.getCmp('idcdRamo').getValue(),
								            	'params.nmpoliza':Ext.getCmp('polizaAfectada').getValue(),
								            	'params.nmsituac':Ext.getCmp('idNmSituac').getValue()
						                    }
	 						 			},
		 							 }).showAt(150,150);
				 				 }
				 			 })				
			 			 ]
			 	},
			 	sucursal,
			 	coberturaAfectada,
			 	subCobertura,
			 	proveedor,
			 	medico,
			 	{
			 		xtype       : 'textfield'				,fieldLabel : 'Especialidad'				,id       : 'idEspecialidad',		name:'idEspecialidad'
		 			,allowBlank : false						,labelWidth: 170							,readOnly   : true
			 	},
			 	{
			 		xtype       : 'textfield'				,fieldLabel : 'Deducible'					,id       : 'idDeducible'
		 			,allowBlank : false						,labelWidth: 170							,readOnly   : true
			 	},
			 	{
			 		xtype       : 'textfield'				,fieldLabel : 'Copago'						,id       : 'idCopago'
		 			,allowBlank : false						,labelWidth: 170							,readOnly   : true
			 	},
			 	penalizacion
			 	,
			 	comboICD
			 	,
			 	{
			 		colspan:2,			xtype : 'numberfield',              id:'sumDisponible',           fieldLabel: 'Suma disponible proveedor',
			 		labelWidth: 170,    allowBlank: false,	                allowDecimals :true,          decimalSeparator :'.',                 allowBlank:false,
			 		name:'mtsumadp'
			 		
			 	}
			 	,
			 	causaSiniestro
			 	,
			 	{
			 		colspan:2								,xtype       : 'textareafield'				,fieldLabel : 'Tr&aacute;tamiento'			,id       : 'tratamiento'
		 			,labelWidth	 : 170						,name:'dstratam',
		 			width      : 700						,height		: 70
			 	},
			 	{
			 		colspan:2								,xtype       : 'textareafield'				,fieldLabel : 'Observaciones'				,id       : 'observaciones'
		 			,labelWidth	 : 170						,name:'dsobserv',
		 			width      : 700						,height		: 70
			 	},
			 	{
			 		colspan:2								,xtype       : 'textareafield'				,fieldLabel : 'Notas internas'				,id       : 'notaInterna'
		 			,labelWidth: 170						,name:'dsnotas',
		 			width      : 700						,height: 70
			 	},
			 	{
			 		colspan:2,
			 		items    :
			 			[
			 			 gridIncisos
			 			 ]
			 	},
			 	{
			 		colspan:2,
			 		items    :
			 			[
			 			 gridIncisos2
			 			 ]
			 	},
			 	{
			 		colspan:2,
			 		items    :
			 			[
			 			 gridIncisos3
			 			 ]
			 	}
		 	],
		 	buttonAlign:'center',
		 	buttons: [{
			 		text:'Guardar',
			 		icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
			 		id:'botonGuardar',
			 		handler: function() {
			 		    if (panelInicialPrincipal.form.isValid()) {
		 		    		if(guardadoAutorizacionServicio())
	 		    			{
	 		    				modificacionClausula.show();
	 		    			}
			 		    		
			 		    } else {
			 		        Ext.Msg.show({
			 		               title: 'Aviso',
			 		               msg: 'Complete la informaci&oacute;n requerida',
			 		               buttons: Ext.Msg.OK,
			 		               icon: Ext.Msg.WARNING
			 		           });
			 		    }
			 		}
			 	},
			 	{
			 		text:'Autorizar',
			 		icon:_CONTEXT+'/resources/fam3icons/icons/key.png',
			 		id:'Autorizar',
			 		handler:function()
			 		{
			 			
			 			if (panelInicialPrincipal.form.isValid()) {
			 				
		 		    		//guardadoAutorizacionServicio();
			 				if(Ext.getCmp('fechaAutorizacion').getValue()!=null && Ext.getCmp('fechaVencimiento').getValue())
		 					{
			 					//console.log("TIENE DATOS");
			 					Ext.getCmp('idstatus').setValue("2");
			 					guardadoAutorizacionServicio();
		 					}
			 				else
		 					{
			 					Ext.Msg.show({
				 		               title: 'Error',
				 		               msg: 'La fecha de autorizaci&oacute;n y fecha vencimiento son requeridas',
				 		               buttons: Ext.Msg.OK,
				 		               icon: Ext.Msg.ERROR
				 		           });
		 					}
			 				
			 		    } else {
			 		        Ext.Msg.show({
			 		               title: 'Aviso',
			 		               msg: 'Complete la informaci&oacute;n requerida',
			 		               buttons: Ext.Msg.OK,
			 		               icon: Ext.Msg.WARNING
			 		           });
			 		    }
			 		}
			 	}
		 	]		
	});
	
	function  guardadoAutorizacionServicio()
	{
			var respuesta=true;
	        var submitValues={};
            var formulario=panelInicialPrincipal.form.getValues();
            submitValues['params']=formulario;
            
           var datosTablas = [];
				storeConceptoAutorizados.each(function(record,index){
                    datosTablas.push({
                    	cdtipaut: record.get('cdtipaut'),
                    	cdmedico: record.get('cdmedico'),
                    	cdcpt: record.get('cdcpt'),
                    	precio: record.get('precio'),
                    	cantporc: record.get('cantporc'),
                    	ptimport: record.get('ptimport'),
                    	cdtipmed: record.get('cdtipmed')
                    	
                    });
               });
				storeQuirugicoBase.each(function(record,index){
            	datosTablas.push({
                	cdtipaut: record.get('cdtipaut'),
                	cdmedico: record.get('cdmedico'),
                	cdcpt: record.get('cdcpt'),
                	precio: record.get('precio'),
                	cantporc: record.get('cantporc'),
                	ptimport: record.get('ptimport'),
                	cdtipmed: record.get('cdtipmed')
                	
                });
           });
				storeQuirurgico.each(function(record,index){
            	datosTablas.push({
                	cdtipaut: record.get('cdtipaut'),
                	cdmedico: record.get('cdmedico'),
                	cdcpt: record.get('cdcpt'),
                	precio: record.get('precio'),
                	cantporc: record.get('cantporc'),
                	ptimport: record.get('ptimport'),
                	cdtipmed: record.get('cdtipmed')
                	
                });
           });
		        
				submitValues['datosTablas']=datosTablas;
				//console.log(submitValues);
				panelInicialPrincipal.setLoading(true);
            Ext.Ajax.request(
                    {
                        url: _URL_GUARDA_AUTORIZACION,
                        jsonData:Ext.encode(submitValues), // convierte a estructura JSON
                        
                        success:function(response,opts){
                        	panelInicialPrincipal.setLoading(false);
                            var jsonResp = Ext.decode(response.responseText);
                            
                            if(jsonResp.success==true){
                            	var numeroAutorizacion = Ext.decode(response.responseText).numeroAutorizacion.nmautser;
                                Ext.getCmp('idNoAutorizacion').setValue(numeroAutorizacion);
                                var mensaje='';
                                if(Ext.getCmp('claveTipoAutoriza').getValue() == 1)
                            	{
                                	mensaje= 'Se guardo la Autorizaci&oacute;n de Servicio con el n&uacute;mero ';
                            	}
                                if(Ext.getCmp('claveTipoAutoriza').getValue() == 2)
                            	{
                                	mensaje= 'Se modific&oacute; la Autorizaci&oacute;n de Servicio con el n&uacute;mero ';
                            	}
                                if(Ext.getCmp('claveTipoAutoriza').getValue() == 3)
                            	{
                                	mensaje= 'Se reemplaz&oacute; la Autorizaci&oacute;n de Servicio con el n&uacute;mero ';
                            	}
                                
                                
                                Ext.Msg.show({
                                    title:'Guardado',
                                    msg: mensaje+" : "+numeroAutorizacion ,
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.INFO
                                });
                                
                                
                                panelInicialPrincipal.getForm().reset();
                                storeMedico.removeAll();
                                Ext.getCmp('idEspecialidad').setValue('');
                                Ext.getCmp('fechaSolicitud').setValue('');
                                storeConceptoAutorizados.removeAll();
                				storeQuirugicoBase.removeAll();
                				storeQuirurgico.removeAll();
                				//
                            }
                            else{
                                Ext.Msg.show({
                                    title:'Error',
                                    msg: 'Error al modificar los registros',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.ERROR
                                });
                                
                                respuesta= false;
                            }
                        },
                        failure:function(response,opts)
                        {
                        	panelInicialPrincipal.setLoading(false);
                            Ext.Msg.show({
                                title:'Error',
                                msg: 'Error de comunicaci&oacute;n',
                                buttons: Ext.Msg.OK,
                                icon: Ext.Msg.ERROR
                            });
                            respuesta=false;
                        }
                    });
		return respuesta;
	}
	
	

});