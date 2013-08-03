Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	//*******************************GRILLA DE VALORES DADOS POR EL COMPONENTE GENERICO **************
	var recalcularAtributo = new Ext.grid.CheckColumn({
      header: "Recalcular",
      dataIndex: 'recalcular',
      sortable: true,
      align: 'center',
      width: 80
    });
	
	var readerComponenteGenerico = new Ext.data.JsonReader({
           			//root:'cgEstructuraList',
           			totalProperty: 'totalCount',
            		successProperty : '@success'
		        },
		        [
		        {name: 'codigoVariable',   type: 'string',  mapping:'cdVariable'},
		        {name: 'descripcionVariable',   type: 'string',  mapping:'dsVariable'},
				{name: 'codigoTabla',  type: 'string',  mapping:'cdTabla'},
		        {name: 'descripcionTabla',  type: 'string',  mapping:'dsTabla'},
		        {name: 'codigoColumna', type: 'string',  mapping:'cdColumna'},
		        {name: 'descripcionColumna', type: 'string',  mapping:'dsColumna'},
		        {name: 'codigoClave', type: 'string',  mapping:'cdClave'},
		        {name: 'descripcionClave', type: 'string',  mapping:'dsClave'},
		        {name: 'codigoValor', type: 'string',  mapping:'cdValor'},
		        {name: 'descripcionValor', type: 'string',  mapping:'dsValor'},
		        {name: 'recalcular', type: 'string',  mapping:'recalcular'}
				]
		);
				
	var store = new Ext.data.Store({
	   		proxy: new Ext.data.HttpProxy({
				//url: _OBTENER_DATOS_COMPONENTE_GENERICO
        	       }),
            	   reader: readerComponenteGenerico
       			});
	
	var cm = new Ext.grid.ColumnModel([
		 {
		   	header: "Variable",
		   	dataIndex: 'dsVariable',
		   	sortable: true,
		   	editor: new Ext.form.TextField({
	            		allowBlank: false
	            		})
		 },
		 {
	       	header: "Tabla",
	       	dataIndex: 'dsTabla',
	       	sortable: true,
	       	editor: new Ext.form.TextField({
	            		allowBlank: false
	            		})
	     },
	     {
	       	header: "Columna",
	       	dataIndex: 'dsColumna',
	       	sortable: true,
	       	editor: new Ext.form.TextField({
	            		allowBlank: false
	            		})
	     },
	     {
	       	header: "Claves",
	       	dataIndex: 'dsClaves',
	       	sortable: true,
	       	editor: new Ext.form.TextField({
	            		allowBlank: false
	            		})
	     },
	     {
	       	header: "Valores",
	       	dataIndex: 'dsValores',
	       	sortable: true,
	       	editor: new Ext.form.TextField({
	            		allowBlank: false
	            		})
	     },
	     recalcularAtributo
	 ]);
	
	var grillaValores = new Ext.grid.EditorGridPanel({
	        el:'grilla',
	        store:store,
			border:true,
			bodyStyle:'background: white',
			buttonAlign: 'center',
			title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        cm: cm,
	        loadMask: {msg: 'Cargando datos ...', disabled: false},
	        //getLabelFromMap('400058',helpMap,'Cargando datos ...')
			buttons:[
	        		{
        			text:'Guardar',
            		tooltip:'Guarda la información de la configuración',
            		handler: function(){guardarValores();}
	            	},
	            	{
            		text:'Regresar',
            		tooltip:'Regresa a la pantalla anterior',
            		handler: function(){window.location.href = _ACTION_IR_CONFIGURAR_ORDENES_TRABAJO+'?cdFormatoOrden='+CODIGO_FORMATO_ORDEN+'&cdSeccion='+CODIGO_SECCION+'&cdAtribu='+CODIGO_ATRIBUTO+'&flag='+true+'&dsFormatoOrden='+DESCRIPCION_FORMATO_ORDEN;}
	                }
	            	],
	    	width:500,
	    	frame:true,
			height:320,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			plugins: [recalcularAtributo],
			bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: store,
					displayInfo: true,
	                displayMsg: 'Mostrando registros {0} - {1} of {2}',
	                emptyMsg: "No hay registros para visualizar"
			    })
			});
	
	//+++++++++++++++++++++++++++ ENCABEZADO DE VALORES  POR DEFECTOS ++++++++++++++++++++++++++++++++
	var expresion = new Ext.form.Hidden({
		name:'codigoExpresion',
		id: 'codigoExpresion'
	});
	
	var readerValoresDefectoAtributos = new Ext.data.JsonReader({
	            root : 'valoresDefectoAtributosList',
	            totalProperty: 'totalCount',
	            successProperty : '@success'
	       		 },
	       		[
	       		{name : 'codigoFormatoOrden', 	  mapping : 'cdFormatoOrden', type : 'string'},
	            {name : 'dsFormatoOrden',mapping : 'dsFormatoOrden', type : 'string'},
	            {name : 'codigoSeccion',          mapping : 'cdSeccion',	  type : 'string'},
	            {name : 'descripcionSeccion',     mapping : 'dsSeccion',	  type : 'string'},
	            {name : 'cdAtribu',   	  mapping : 'cdAtribu',	      type : 'string'},
	            {name : 'descripcionAtributo',	  mapping : 'dsAtribu',		  type : 'string'},
	            {name : 'codigoExpresion',		  mapping : 'cdExpres',		  type : 'string'}
	            ]
	       );        
		
	var dsValoresDefectoAtributos = new Ext.data.Store ({
	            proxy: new Ext.data.HttpProxy({
	                url: _ACTION_OBTENER_VALOR_DEFECTOS_ATRIBUTO
	            }),
	            reader: readerValoresDefectoAtributos
	        });
	
	function reloadGrid(){
		var _params = {
	     		cdExpresion: formPanel.findId('expresionId').getValue()
		};
		reloadComponentStore(grillaValores, _params, cbkReload);
		return;
  		var _storeDom = grillaValores.store;
     	var o = {start: 0};
	     _storeDom.baseParams = _storeDom.baseParams || {};
	     _storeDom.baseParams['cdExpresion'] = formPanel.findId('expresionId').getValue();
	     _storeDom.reload({
                   params:{start:0,limit:itemsPerPage},
                   callback : function(r, options, success) {
                       if (!success) {
                          _storeDom.removeAll();
                       }
                   }
 
               }
         );
 	}
 	function cbkReload (_r, _o, _success, _store) {
 		if (!_success) {
 			//Ext.Msg.alert('Error', _store.reader.jsonData.actionErrors[0]);
 			Ext.Msg.alert('Aviso', _store.reader.jsonData.actionErrors[0]);
 			_store.removeAll();
 		}
 	}
 	
 	function reloadForm(){
  		formPanel.form.load({
				params:{cdFormatoOrden: CODIGO_FORMATO_ORDEN,cdSeccion: CODIGO_SECCION,cdAtribu: CODIGO_ATRIBUTO}
				});
 	}
	        
	var formPanel = new Ext.FormPanel ({
			el: 'encabezado',
			title: '<span style="color:black;font-size:14px;">Valores por defecto para atributos de ordenes de trabajo</span>',
            url : _ACTION_OBTENER_VALOR_DEFECTOS_ATRIBUTO,
            frame : true,
            width : 500,
            height: 170,
            waitMsgTarget : true,
            store: dsValoresDefectoAtributos,
            reader: readerValoresDefectoAtributos,
            bodyStyle:'background: white',
            successProperty: 'success',
            items: [//form
            		expresion,
            		{
           			layout: 'column',
           			defaults: {labelWidth: 70},
           			items:[//text
           					{//1ra columna
	           					columnWidth: .50,
	           					layout: 'form',
	           					items: [
		           					{xtype: 'hidden', name: 'codigoFormatoOrden', id:'codigoFormatoId'},
		           					{xtype: 'hidden', name: 'cdAtribu',id:'codigoAtributoId'},
									{xtype: 'textfield', fieldLabel: 'Formato', name: 'dsFormatoOrden', anchor: '95%', id: 'formatoId', disabled:true},
									{xtype: 'textfield', fieldLabel: 'Atributo', name: 'descripcionAtributo', anchor: '95%', id: 'atributoId', disabled:true}
								]
           				    },
           				    {//2da columna
	           				   	columnWidth: .5,
	           					layout: 'form',
	           					items: [
		           					{xtype: 'hidden', name: 'codigoSeccion'},
									{xtype: 'textfield', fieldLabel: 'Sección', name: 'descripcionSeccion',anchor: '95%', id: 'seccionId', disabled:true} 
									]
           				    }
           			]
           		   },
           		   {
           			layout: 'column',
           			items:[//textArea
           					{//1ra columna
	           					columnWidth: .3,
	           					layout: 'form',
	           					items: [
		       					{xtype: 'hidden', name: 'codigoExpresion'},
		       					{xtype: 'textfield', name: 'codigoExpresion', width: 60, id: 'codigoExpresionId', disabled:true, tooltip:''}
		       					]
           				    },
           				    {//2da columna
	           				   	columnWidth: .7,
	           					layout: 'form',
	           					items: [
		           						{xtype: 'textarea', name: 'descripcionExpresion', id: 'descripcionExpresionId',width:230,height:70}
		           				]
           				    }
           			]
           		},
           		{
           		 layout: 'form',
           		 items:[grillaValores]
           		 }
           		]
	});
	//Bloque de código que sirve para poder limpiar los label de los textField
	/*var campito = Ext.getCmp('codigoExpresionId');
	 var _label = Ext.DomQuery.select(String.format('label[for="{0}"]', campito.tooltip));
      if (_label) {
       _label[0].childNodes[0].nodeValue = el_Map.get(campito.tooltip).fieldLabel + ": ";
      };*/
	
	function guardarValores() {
		   var _params = "";
		  /*
		   _params +=  "valoresList[" + 0 + "].cdFormatoOrden=" + CODIGO_FORMATO_ORDEN + "&" +
		   "valoresList[" + 1 + "].cdSeccion=" + CODIGO_SECCION + "&" +
		   "valoresList[" + 2 + "].cdAtribu=" + CODIGO_ATRIBUTO + "&" +
		   "valoresList[" + 3 + "].cdExpres=" + formPanel.findById('codigoExpresion').getValue() + "&";
		  */
		  _params = {
			     		 cdFormatoOrden: CODIGO_FORMATO_ORDEN,
			     		 cdSeccion: CODIGO_SECCION,
			     		 cdAtribu: CODIGO_ATRIBUTO,
			     		 cdExpres: formPanel.findById('codigoExpresion').getValue()
			     	};
		  execConnection(_ACTION_GUARDAR_VALOR_DEFECTOS_ATRIBUTO, _params, cbkGuardar);
		  return;
		  var conn = new Ext.data.Connection ();
		  conn.request({
			     url: _ACTION_GUARDAR_VALOR_DEFECTOS_ATRIBUTO,
			     //params: _params,
			     params:{
			     		 cdFormatoOrden: CODIGO_FORMATO_ORDEN,
			     		 cdSeccion: CODIGO_SECCION,
			     		 cdAtribu: CODIGO_ATRIBUTO,
			     		 cdExpres: formPanel.findById('codigoExpresion').getValue()
			     		 },
			     method: 'POST',
			     callback: function (options, success, response) {
				         if (Ext.util.JSON.decode(response.responseText).success == false) {
				          Ext.Msg.alert('Error', 'Problemas al guardar: ' + Ext.util.JSON.decode(response.responseText).errorMessages[0]);
			         } else {
				          Ext.Msg.alert('Aviso', 'Los datos se guardaron con éxito', function(){reloadForm();});
				          //grillaSecciones.store.commitChanges();
			         }
			      }
		   });
		  /*
		  var recs = grillaValores.store.getModifiedRecords();
		  grillaValores.stopEditing();
		  for (var i=0; i<recs.length; i++) {
		   	 _params +=  "valoresList[" + i + "].cdFormatoOrden=" + CODIGO_FORMATO_ORDEN + "&" +
		      "valoresList[" + i + "].cdSeccion=" + CODIGO_SECCION + "&" +
		      "valoresList[" + i + "].cdVariable=" + CODIGO_ATRIBUTO + "&" +
		      "valoresList[" + i + "].cdExpresion=" + recs[i].get('codigoExpresion') + "&";
		  }
		  if (recs.length > 0) {
		   	var conn = new Ext.data.Connection ();
		   	conn.request({
			     url: _ACTION_GUARDAR_VALOR_DEFECTOS_ATRIBUTO,
			     params: _params,
			     method: 'POST',
			     callback: function (options, success, response) {
				         if (Ext.util.JSON.decode(response.responseText).success == false) {
				          Ext.Msg.alert('Error', 'Problemas al guardar: ' + Ext.util.JSON.decode(response.responseText).errorMessages[0]);
			         } else {
				          Ext.Msg.alert('Aviso', 'Los datos se guardaron con éxito', function(){reloadGrid();});
				          grillaSecciones.store.commitChanges();
			         }
			      }
		   });
		 }*/
	 }            
	function cbkGuardar (_success, _message) {
		if (!_success) {
			Ext.Msg.alert('Error', _message);
		}else {
			Ext.Msg.alert('Aviso', _message, function() {reloadForm()});
		}
	}
	grillaValores.render();
	formPanel.render();
	
	if((CODIGO_FORMATO_ORDEN != null && CODIGO_SECCION != null && CODIGO_ATRIBUTO != null) || (CODIGO_FORMATO_ORDEN != "" && CODIGO_SECCION != "" && CODIGO_ATRIBUTO != "")){
		formPanel.form.load({
				params:{cdFormatoOrden: CODIGO_FORMATO_ORDEN,cdSeccion: CODIGO_SECCION,cdAtribu: CODIGO_ATRIBUTO}
				});
	}
	
	formPanel.findById('codigoExpresionId').setFieldLabel("");
	formPanel.findById('descripcionExpresionId').setFieldLabel("");
	
});

//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  Definición de Plugin para Checkbox en grillas $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		Ext.grid.CheckColumn = function(config){
		    Ext.apply(this, config);
		    if(!this.id){
		        this.id = Ext.id();
		    }
		    this.renderer = this.renderer.createDelegate(this);
		};

		Ext.grid.CheckColumn.prototype ={
		    init : function(grillaValores){
		        this.grillaValores = grillaValores;
		        this.grillaValores.on('render', function(){
		            var view = this.grillaValores.getView();
		            view.mainBody.on('mousedown', this.onMouseDown, this);
		        }, this);
		    },
		
		    onMouseDown : function(e, t){
		        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
		            e.stopEvent();
		            var index = this.grillaValores.getView().findRowIndex(t);
		            var record = this.grillaValores.store.getAt(index);
		            record.set(this.dataIndex, !record.data[this.dataIndex]);
					
		            if (this.dataIndex == 'recalcular') {
		            	if (record.data['fgNoRequ']) record.set('fgComple', false);
		            }
		            if (this.dataIndex == 'fgComple') {
		            	if (record.data['fgComple']) record.set('recalcular', false);
		            }
		            this.grillaValores.getSelectionModel().selectRow(index); //Selecciona la fila completa
		        }
		    },
			onClick: function (e, t) {
			},
		    renderer : function(v, p, record){
		        p.css += ' x-grid3-check-col-td'; 
		        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
		    }
};	