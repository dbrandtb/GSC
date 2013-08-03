Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	//*******************************GRILLA DE VALORES DADOS POR EL COMPONENTE GENERICO **************
	var recalcularAtributo = new Ext.grid.CheckColumn({	
      header: getLabelFromMap('forMetCancCmCheck',helpMap,'Recalcular'),
      tooltip: getToolTipFromMap('forMetCancCmCheck',helpMap,'Recalculo de formula metodo de cancelacion'),
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
	        header: getLabelFromMap('forMetCancCmVar',helpMap,'Variable'),
    	    tooltip: getToolTipFromMap('forMetCancCmVar',helpMap,'Variable de formula metodo de cancelacion'),
		   	dataIndex: 'dsVariable',
		   	sortable: true,
		   	editor: new Ext.form.TextField({
	            		allowBlank: false
	            		})
		 },
		 {
	        header: getLabelFromMap('forMetCancCmTab',helpMap,'Tabla'),
    	    tooltip: getToolTipFromMap('forMetCancCmTab',helpMap,'Tabla de formula metodo de cancelacion'),
	       	dataIndex: 'dsTabla',
	       	sortable: true,
	       	editor: new Ext.form.TextField({
	            		allowBlank: false
	            		})
	     },
	     {
	        header: getLabelFromMap('forMetCancCmCol',helpMap,'Columna'),
    	    tooltip: getToolTipFromMap('forMetCancCmCol',helpMap,'Columna de formula metodo de cancelacion'),
	       	dataIndex: 'dsColumna',
	       	sortable: true,
	       	editor: new Ext.form.TextField({
	            		allowBlank: false
	            		})
	     },
	     {
	        header: getLabelFromMap('forMetCancCmClav',helpMap,'Claves'),
    	    tooltip: getToolTipFromMap('forMetCancCmCalv',helpMap,'Claves de formula metodo de cancelacion'),
	       	dataIndex: 'dsClaves',
	       	sortable: true,
	       	editor: new Ext.form.TextField({
	            		allowBlank: false
	            		})
	     },
	     {
	        header: getLabelFromMap('forMetCancCmVal',helpMap,'Valores'),
    	    tooltip: getToolTipFromMap('forMetCancCmVal',helpMap,'Valores de formula metodo de cancelacion'),
	       	dataIndex: 'dsValores',
	       	sortable: true,
	       	editor: new Ext.form.TextField({
	            		allowBlank: false
	            		})
	     },
	     recalcularAtributo
	 ]);
	
	var grillaValores = new Ext.grid.EditorGridPanel({
	        el:'gridElementos',
	        store:store,
			border:true,
			title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	        cm: cm,
	        loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
			buttons:[
	        		{
                    text:getLabelFromMap('forMetCancBtnSave',helpMap,'Guardar'),
                    tooltip: getToolTipFromMap('forMetCancBtnSave',helpMap,'Guarda una formula de metodo de cancelacion'),
            		handler: function(){guardarValores();}
	            	},
	            	{
                    text:getLabelFromMap('forMetCancBtnBack',helpMap,'Regresar'),
                    tooltip: getToolTipFromMap('forMetCancBtnBack',helpMap,'Regresa a la pantalla anterior'),
            		handler: function(){
            				window.location.href = _ACTION_REGRESAR;
            			}
	                }
	            	],
	    	width:550,
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
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
	
	var expresion = new Ext.form.Hidden({
		name:'codigoExpresion', 
		id: 'codigoExpresion'
	});
	
	var readerFormulaMetodoCancelacion = new Ext.data.JsonReader({
	            root : 'MFormulaList',
	            totalProperty: 'totalCount',
	            successProperty : '@success'
	       		 },
	       		[
	       		{name : 'cdMetodo', 	  mapping : 'cdMetodo', type : 'string'},
	            {name : 'dsMetodo',mapping : 'dsMetodo', type : 'string'},
	            {name : 'cdExprespndp',          mapping : 'cdExprespndp',	  type : 'string'},
	            {name : 'cdExprespndt',     mapping : 'cdExprespndt',	  type : 'string'},
	            ]
	       );        
		
	var dsFormulaMetodoCancelacion = new Ext.data.Store ({
	            proxy: new Ext.data.HttpProxy({
	                url: _ACTION_GET_FORMULA_METODO_CANCELACION
	            }),
	            reader: readerFormulaMetodoCancelacion
	        });
	

	var formPanel = new Ext.FormPanel ({
			el: 'formBusqueda',
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('103',helpMap,'Formula del Metodo de Cancelacion')+'</span>',
            url : _ACTION_GET_FORMULA_METODO_CANCELACION,
            frame : true,
            width : 550,
            height: 150,
            waitMsg: true,
            store: dsFormulaMetodoCancelacion,
            reader: readerFormulaMetodoCancelacion,
            successProperty: 'success',
            items: [
            		expresion,
            		{
           			layout: 'column',
           			items:[
           					{//1ra columna
	           					columnWidth: .50,
	           					layout: 'form',
	           					items: [
		           					{xtype: 'hidden', name: 'cdTipoPrima',id:'cdTipoPrima'},
									{xtype: 'textfield', fieldLabel: 'Clave', tooltip:getToolTipFromMap('cdMetodo',helpMap,''), name: 'cdMetodo', anchor: '95%', id: 'cdMetodo', disabled:true}
								]
           				    },
           				    {//2da columna
	           				   	columnWidth: .5,
	           					layout: 'form',
	           					items: [
		           					{xtype: 'hidden', name: 'codigoSeccion'},
									{xtype: 'textfield', fieldLabel: 'Descripcion', tooltip:getToolTipFromMap('dsMetodo',helpMap,''), name: 'dsMetodo',anchor: '95%', id: 'dsMetodo', disabled:true} 
									]
           				    }
           			]
           		   },
           		   {
           			layout: 'column',
           			items:[
           					{//1ra columna
	           					columnWidth: .3,
	           					hideLabels: true,
	           					layout: 'form',
	           					items: [
		       					{xtype: 'hidden', name: 'codigoExpresion'},
		       					{xtype: 'textfield', labelSeparator: '', name: 'codigoExpresion', anchor: '95%', id: 'codigoExpresionId', disabled:true, tooltip:getToolTipFromMap('codigoExpresion',helpMap,'')}
		       					]
           				    },
           				    {//2da columna
	           				   	columnWidth: .7,
	           					hideLabels: true,
	           					layout: 'form',
	           					items: [
		           						{xtype: 'textarea', labelSeparator: '',tooltip:getToolTipFromMap('descripcionExpresion',helpMap,''), name: 'descripcionExpresion', anchor: '95%', id: 'descripcionExpresionId',width:270,height:50}
		           				]
           				    }
           			]
           		},
           		   {
           			layout: 'column',
           			items:[
           					{//1ra columna
	           					columnWidth: .3,
	           					layout: 'form',
	           					items: [
		       					{xtype: 'checkbox', fieldLabel: 'Recalcular',tooltip:getToolTipFromMap('chkRecalcular',helpMap,''), name: 'chkRecalcular', id: 'chkRecalcular'}
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
	
	function guardarValores() {
		   var _params = "";
	 }            

	grillaValores.render();
	formPanel.render();
	
	formPanel.form.load({
			params:{cdMetodo: CD_METODO}
	});
	
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