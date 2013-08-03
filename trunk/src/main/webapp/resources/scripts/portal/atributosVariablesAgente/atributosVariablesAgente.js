Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

 var storeListaValores = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_OBTENER_LISTA_VALORES_ATRIBUTOS_VARIABLES_AGENTE
                }),

                reader: new Ext.data.JsonReader({
            	root:'comboListaValores',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },
	        [
          		{name : 'codigo', mapping : 'codigo', type : 'string'},
             	{name : 'descripcion',mapping : 'descripcion',type : 'string'}
             ]
	        ),
	        baseParams: {cdTipo: '1'}
    });

    var readerFormatosAtributos = new Ext.data.JsonReader( {
            root : 'comboFormatosCampo',
            totalProperty: 'totalCount',
            successProperty : '@success'
         },
         [
          {name : 'id', mapping : 'codigo', type : 'string'},
          {name : 'texto',mapping : 'descripcion',type : 'string'}
         ]
     );
     
    var dsFormatos = new Ext.data.Store({
         proxy: new Ext.data.HttpProxy({
             url: _ACTION_OBTENER_FORMATO_ATRIBUTOS_VARIABLES_AGENTE
         }),
         reader: readerFormatosAtributos
     }); 


    var comboListaValores = new Ext.form.ComboBox(
    	{
    		tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
            store: storeListaValores, 
            anchor:'100%', 
            displayField:'descripcion', 
            valueField: 'codigo', 
            hiddenName: 'cdValor',
            typeAhead: true, 
            triggerAction: 'all', 
            lazyRender:   true, 
            emptyText:'Selecione ...', 
            selectOnFocus:true,
            name: 'cdListaValor',
            id:'cdListaValor',
            mode: 'local',
            forceSelection: true, 
            labelSeparator:''
       });

    var comboFormatos = new Ext.form.ComboBox(
    	{
    		tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
            store: dsFormatos, 
            anchor:'100%', 
            displayField:'texto', 
            valueField: 'id', 
            hiddenName: 'cdFormato',
            typeAhead: true, 
            triggerAction: 'all', 
            lazyRender:   true, 
            emptyText:'Selecione Formato...', 
            selectOnFocus:true,
            name: 'codFormato',
            id:'codFormato',
            forceSelection: true, 
            labelSeparator:'',
            mode: 'local',
            //allowBlank: false,
		    selectOnFocus:true,
		    forceSelection:true
     });
     
    var nmObligatorio = new Ext.grid.CheckColumn({
      //id: 'nmObligatorioId',
      header:getLabelFromMap('cmNmObligatorioAtbVrbAgn',helpMap,'Oblig.'),
      tooltip:getToolTipFromMap('cmNmObligatorioAtbVrbAgn',helpMap, 'Tilde si es obligatorio'),
      dataIndex: 'nmObligatorio',
      align: 'center',
      sortable: true,
      width: 40
    });

	var recordGrilla = new Ext.data.Record.create([
				{name:'cdNombre',  type:'string',  mapping:'cdAtribu'},
		        {name:'dsNombre',  type:'string',  mapping:'dsAtribu'},
		        {name:'codiFormato', type:'string',  mapping:'swFormat'},
		        {name:'nmMaximo',  type:'string',  mapping:'nmlMax'},
		        {name:'nmMinimo',  type:'string',  mapping:'nmlMin'},
		        {name:'nmObligatorio',  type:'bool',  mapping:'swObliga'},
		        {name:'cdListaValores',  type:'string',  mapping:'otTabVal'},
		        {name:'gbDsFormato',  type:'string',  mapping:'gbSwFormat'}
			]);	
			
    var storeGrilla = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_BUSCAR_ATRIBUTOS_VARIABLES_AGENTE
                }),

                reader: new Ext.data.JsonReader({
            	root:'MEstructuraList',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },
	        	recordGrilla
	        )
    });

/****************Funcion para obtener la celda invalida***************/
 function validarGrilla(grilla){
 	
 }

/**
*	Función usada para mostrar la descripción de un combo en vez del código
*	en una grilla de tipo EditorGridPanel
*
*
*/
function renderComboEditor2 (combo) {
	return function (value) {
		var idx;
		//value = eval(value);
		for (var i=0; i < combo.store.data.items.length; i++) {
			//console.log("entrando:" + i);
			var registro = combo.store.getAt(i);
			if (registro) {
				if (registro.get(combo.valueField) == value) {
					//alert("matching: " + i + "\nvalue: " + value);
					idx = i;
				}//else alert("registro: " + registro.get(combo.valueField) + "\nvalue: " + value);
			} 
		}
		//var idx = combo.store.find(combo.valueField, value, 0, true, true);
		var rec = combo.store.getAt(idx);
		return (rec == null)?value:rec.get(combo.displayField);
	}
}


	var cm = new Ext.grid.ColumnModel ([
			{
		        header: getLabelFromMap('cmDsNombreAtbVrbAgn',helpMap,'Nombre'),
		        tooltip: getToolTipFromMap('cmDsNombreAtbVrbAgn',helpMap,'Nombre'),
		        dataIndex: 'dsNombre',
		        maxLength:15,
		        sortable: true,
		        editor: new Ext.form.TextField({name: 'dsNombre',id:'nombreId',maxLength:15/*,allowBlank: false*/}),
		        renderer: function extGrid_renderer(val, cell, record, row, col, store) {
					if (record != undefined) {
						// Make sure we have a value
						if (val == '') {
							// No value so highlight this cell as in an error state
							cell.css += 'x-form-invalid';
							cell.attr = 'ext:qtip="Este campo es requerido"; ext:qclass="x-form-invalid-tip"';
							//cell.attr = 'ext:qclass="x-form-invalid-tip"';
						} else {
						// Value is valid
						cell.css = '';
						cell.attr = 'ext:qtip=""';
						}
					}
					// Return the value so that it is displayed within the grid
					return val;
				}
			},
			{
		        header: getLabelFromMap('cmDsFormatoAtbVrbAgn',helpMap,'Formato'),
		        tooltip: getToolTipFromMap('cmDsFormatoAtbVrbAgn',helpMap,'Formato'),
		        dataIndex: 'codiFormato',
		        sortable: true,
		        editor: comboFormatos,
		        renderer: function extGrid_renderer(val, cell, record, row, col, store) {
					if (record != undefined) {
						// Make sure we have a value
						if (val == '') {
							// No value so highlight this cell as in an error state
							cell.css += 'x-form-invalid';
							cell.attr = 'ext:qtip="Este campo es requerido"; ext:qclass="x-form-invalid-tip"';
							//cell.attr = 'ext:qclass="x-form-invalid-tip"';
						} else {
						// Value is valid
						cell.css = '';
						cell.attr = 'ext:qtip=""';
						}
					}
					//console.log(comboFormatos);
					// Return the value so that it is displayed within the grid
					var idx;
					for (var i=0; i < comboFormatos.store.data.items.length; i++) {
						//console.log("entrando:" + i);
						var registro = comboFormatos.store.getAt(i);
						if (registro) {
							if (registro.get(comboFormatos.valueField) == val) {
								//alert("matching: " + i + "\nvalue: " + value);
								idx = i;
							}//else alert("registro: " + registro.get(combo.valueField) + "\nvalue: " + value);
						} 
					}
					var rec = comboFormatos.store.getAt(idx);
					return (rec == null)?val:rec.get(comboFormatos.displayField);
					
				}
				
			},
			{
		        header: getLabelFromMap('cmNmMinimoAtbVrbAgn',helpMap,'M&iacute;n.'),
		        tooltip: getToolTipFromMap('cmNmMinimoAtbVrbAgn',helpMap,'M&iacute;nimo'),
		        dataIndex: 'nmMinimo',
		        maxLength:2,
		        width: 60,
		        sortable: true,
		        editor: new Ext.form.NumberField({name: 'nmMinimo',maxLength: 2/*allowBlank: false*/}),
		        renderer: function extGrid_renderer(val, cell, record, row, col, store) {
					if (record != undefined) {
						// Make sure we have a value
						if (val == '') {
							// No value so highlight this cell as in an error state
							cell.css += 'x-form-invalid';
							cell.attr = 'ext:qtip="Este campo es requerido"; ext:qclass="x-form-invalid-tip"';
							//cell.attr = 'ext:qclass="x-form-invalid-tip"';
						} else {
						// Value is valid
						cell.css = '';
						cell.attr = 'ext:qtip=""';
						}
					}
					// Return the value so that it is displayed within the grid
					return val;
				}
			},
			{
		        header: getLabelFromMap('cmNmMaximoAtbVrbAgn',helpMap,'M&aacute;x.'),
		        tooltip: getToolTipFromMap('cmNmMaximoAtbVrbAgn',helpMap,'M&aacute;ximo'),
		        dataIndex: 'nmMaximo',
		        maxLength:2,
		        sortable: true,
		        width: 60,
		        editor: new Ext.form.NumberField({name: 'nmMaximo',maxLength: 2/*,allowBlank: false*/}),
		        renderer: function extGrid_renderer(val, cell, record, row, col, store) {
					if (record != undefined) {
						// Make sure we have a value
						if (val == '') {
							// No value so highlight this cell as in an error state
							cell.css += 'x-form-invalid';
							cell.attr = 'ext:qtip="Este campo es requerido"; ext:qclass="x-form-invalid-tip"';
							//cell.attr = 'ext:qclass="x-form-invalid-tip"';
						} else {
						// Value is valid
						cell.css = '';
						cell.attr = 'ext:qtip=""';
						}
					}
					// Return the value so that it is displayed within the grid
					return val;
				}
			},

			nmObligatorio,
			{
		        header: getLabelFromMap('cmCdListaValoresAtbVrbAgn',helpMap,'Lista de Valores'),
		        tooltip: getToolTipFromMap('cmCdListaValoresAtbVrbAgn',helpMap,'Lista de Valores'),
		        dataIndex: 'cdListaValores',
		         width: 120,
		        sortable: true,
		        editor: comboListaValores,
		        renderer: renderComboEditor(comboListaValores)
			},
	        {
	        dataIndex: 'cdNombre',
	        hidden :true
	        },
	        {
	        dataIndex: 'gbDsFormato',
	        hidden :true
	        }
			
	]);

var colModel;
var cols;
var rows;
var cell;
var field;
var record;
	
	var grid2= new Ext.grid.EditorGridPanel({
        renderTo:'gridElementos',
        id: 'grid2',
        store: storeGrilla,
        clicksToEdit: 1,
		border:true,
		title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Datos Variables de Ejecutivos de Cuenta</span>',
        cm: cm,
        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},            	
    	width:500,
    	frame:true,
		height:590,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		collapsible: true,
		buttonAlign: 'center',
		plugins: [nmObligatorio,new Ext.ux.plugins.GridValidator()/*plugins para validar la celda en el jsp se debe agregar: <script src="${ctx}/resources/scripts/ValidacionGrilla/Ext.ux.plugins.GridValidator.js" type="text/javascript"></script>*/ ],
		buttons: [
            		{
            			text: getLabelFromMap('atbVrbAgnBottonAgregar',helpMap,'Agregar'),
                 		tooltip:getToolTipFromMap('atbVrbAgnBottonAgregar',helpMap,'Agregar'),
                 		handler: function () {
                 				var _record = new recordGrilla ({
                 				cdNombre:'',
                 				dsNombre: '',
                 				codiFormato: '',
                 				nmMaximo: '',
                 				nmMinimo: '',
                 				nmObligatorio: '',
                 				cdListaValores: ''
                 			});
                 				grid2.stopEditing();
								grid2.store.insert(0, _record);
								grid2.startEditing(0, 0);
								grid2.getSelectionModel().selectRow(0);
                 		}                 		
            		},
            		{
            			text: getLabelFromMap('atbVrbAgnBottonGuardar',helpMap,'Guardar'),
                 		tooltip:getToolTipFromMap('atbVrbAgnBottonGuardar',helpMap,'Guardar'),
                 		handler: function () {
                 			//alert(Ext.getCmp('nombreId').getSize());
                 			if (grid2.store.getModifiedRecords()==0)
							{
					  	  		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un registro para realizar esta operaci&oacute;n'));
	            	 		}else{
	            		 		/*blancoAd=grid2.store.getModifiedRecords();
	            		 		console.log(grid2.store.getModifiedRecords());
	            		 		exiteBlanco=verificoBlanco(blancoAd);
	            		 		if (exiteBlanco==0)
	            		 		{
		            		 		verifico=grid2.store.getModifiedRecords();
		            		 		
		            	 			//grid2.stopEditing();
		            	 			bandEx=0;
	  								for (var i=0; i<verifico.length; i++) {
	  									if ((verifico[i].get('nmMinimo'))>(verifico[i].get('nmMaximo'))){
	  										bandEx++;
	  									}
	  								}
		            	 			//console.log(grid2.store.getModifiedRecords());
		            	 			if (bandEx!=0)
						 			{
							  			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400039', helpMap,'Existen valores m&iacute;nimos mayores que los m&aacute;ximos'));
						 				bandEx=0;
						 			}else{
		            	 				guardarAtributoVariablesAgente();
						 			}
	            		 		}else{
			                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
	            		 		}*/
	            	 			
	            	 			
	            	 			cols = grid2.colModel.getColumnCount(); // determino cantidad de columnas de la grilla
								rows = grid2.store.getCount();		  // determino cantidad de filas de la grilla
								 
								var bandValid = 0; // bandera para saber si ya todas las celdas de mi grilla son validas
								
								//recorro toda mi grilla preguntando si las celdas son validas
								for (var i = 0; i < rows; i++) {
									for (var j = 0; j < cols; j++) {
										if(!grid2.isCellValid(j,i)){
											bandValid =+ 1;
										}
									}
								}
								//aseguro de que los combos son validas
								verifico = grid2.store.getModifiedRecords();
								for (var i=0; i<verifico.length; i++) {
									if ((verifico[i].get('codiFormato'))==''){
										bandValid++;
									}
								}
								
								if (bandValid != 0){
									Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requeridas'));
								}else{
									verifico=grid2.store.getModifiedRecords();
		            		 		
		            	 			//grid2.stopEditing();
		            	 			bandEx=0;
	  								for (var i=0; i<verifico.length; i++) {
	  									if ((verifico[i].get('nmMinimo'))>(verifico[i].get('nmMaximo'))){
	  										bandEx++;
	  									}
	  								}
		            	 			//console.log(grid2.store.getModifiedRecords());
		            	 			if (bandEx!=0)
						 			{
							  			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400039', helpMap,'Existen valores m&iacute;nimos mayores que los m&aacute;ximos'));
						 				bandEx=0;
						 			}else{
		            	 				guardarAtributoVariablesAgente();
						 			}
								}         
			 				}
                 		} 
					},
            		{
            			text: getLabelFromMap('atbVrbAgnBottonBorrar',helpMap,'Eliminar'),
                 		tooltip:getToolTipFromMap('atbVrbAgnBottonBorrar',helpMap,'Eliminar'),
                 		handler: function () {
                 				if (!grid2.getSelectionModel().getSelected()) {
										Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
	   							}else{
	   								if (grid2.getSelectionModel().getSelected().get("cdNombre")==""){
	   									grid2.store.remove(grid2.getSelectionModel().getSelected());
	   								}else{
	   	    		   					borrar(getSelectedRecord(grid2));
	   								}
                 				}
                 	
						}
					}            		
		],
		bbar: new Ext.PagingToolbar({
				pageSize: itemsPerPage,
				store: storeGrilla,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		    })
		});

	grid2.render();


//validador de minimos y maximos	
/*grid2.on('afteredit',function(e){
	alert(0);
	if (e.field=='nmMinimo'){
		alert (1);
		if (e.record.get("nmMaximo")!=""){
	   		if (e.record.get("nmMaximo")<= e.value)
	   		{
	   			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400039', helpMap,'El m&iacute;nimo no puede ser mayor al m&aacute;ximo'));
	   			e.record.set("nmMinimo",e.record.get("nmMaximo"));
	   		}
	  	}
		return false;
	}
});*/
	

 grid2.on("afteredit",function(e){
 //alert(1);
   	if (e.record.data["nmMinimo"]!="" && e.record.data["nmMaximo"]!="") {
   			if (eval(e.record.data["nmMinimo"]) > eval(e.record.data["nmMaximo"])){
   			       Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400039', helpMap,'El m&iacute;nimo no puede ser mayor al m&aacute;ximo'));
   			       //grid2.getSelectionModel().getSelected().set('nmMaximo', eval(e.record.data["nmMinimo"])+1);
   			}
   		}
   });

 storeListaValores.load({
		callback: function () {
				dsFormatos.load({
				callback: function(){
					reloadGrid(grid2);
					}
				}
				);
		}
	});
	
 function guardarAtributoVariablesAgente () {
  var _params = "";
  
  var recs = grid2.store.getModifiedRecords();
  grid2.stopEditing();
  for (var i=0; i<recs.length; i++) {
   _params +=  "grillaListAtrVrbAgt[" + i + "].cdAtribu=" + recs[i].get('cdNombre') + "&" +
      "&grillaListAtrVrbAgt[" + i + "].dsAtribu=" + recs[i].get('dsNombre') + "&" +
      "&grillaListAtrVrbAgt[" + i + "].swFormat=" + recs[i].get('codiFormato') + "&" +
      "&grillaListAtrVrbAgt[" + i + "].nmlMax=" + recs[i].get('nmMaximo') + "&" +
      "&grillaListAtrVrbAgt[" + i + "].nmlMin=" + recs[i].get('nmMinimo') + "&" +
      "&grillaListAtrVrbAgt[" + i + "].swObliga=" + recs[i].get('nmObligatorio') + "&" +
      "&grillaListAtrVrbAgt[" + i + "].otTabVal=" + recs[i].get('cdListaValores') + "&" +
       "&grillaListAtrVrbAgt[" + i + "].gbSwFormat=" + recs[i].get('codiFormato') +  "&";
       
      //"&grillaListAtrVrbAgt[" + i + "].gbSwFormat=" + recs[i].get('gbDsFormato') + "&";
  }
  if (recs.length > 0) {
   var conn = new Ext.data.Connection ();
   conn.request({
     url: _ACTION_GUARDAR_ATRIBUTOS_VARIABLES_AGENTE,
     params: _params,
     method: 'POST',
     callback: function (options, success, response) {
         if (Ext.util.JSON.decode(response.responseText).success == false) {
          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
         } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con &eacute;xito', function(){
	          	grid2.store.commitChanges();
	          	dsFormatos.load({
	          		callback: function () {
		          		reloadGrid(grid2);
	          		}
	          	});
          });
         }
       }
   });
  }
 } 	

function borrar(record) {
		if(record != "")
		{
			Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
		        if (btn == "yes")
		        {
         			var _params = {
         						cdAtribu: record.get('cdNombre')
         			};
         			execConnection(_ACTION_BORRAR_ATRIBUTOS_VARIABLES_AGENTE, _params, cbkConnection);
               }
			})
		}else{
				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		}
};
function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid(grid2);});
	}
}      

function reloadGrid(grid2){
	var _params = '';
	reloadComponentStore(grid2, _params, cbkReload);
};
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
};	

function verificoBlanco(blancoAd){
	bandEx=0;
	for (var i=0; i<blancoAd.length; i++) {
		if ((blancoAd[i].get('dsNombre'))==''){
			bandEx++;
		}
		if ((blancoAd[i].get('codiFormato'))==''){
			bandEx++;
		}
		if ((blancoAd[i].get('nmMinimo'))==''){
			bandEx++;
		}
		if ((blancoAd[i].get('nmMaximo'))==''){
			bandEx++;
		}
	}
	//alert(bandEx);
	return bandEx;
}	

});


/*************  Definición de Plugin para Checkbox en grillas ********************************************/
		Ext.grid.CheckColumn = function(config){
		    Ext.apply(this, config);
		    if(!this.id){
		        this.id = Ext.id();
		    }
		    this.renderer = this.renderer.createDelegate(this);
		};

		Ext.grid.CheckColumn.prototype ={
		    init : function(grid){
		        this.grid = grid;
		        this.grid.on('render', function(){
		            var view = this.grid.getView();
		            view.mainBody.on('mousedown', this.onMouseDown, this);
		        }, this);
		    },
		
		    onMouseDown : function(e, t){
		        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
		            e.stopEvent();
		            var index = this.grid.getView().findRowIndex(t);
		            var record = this.grid.store.getAt(index);
		            record.set(this.dataIndex, !record.data[this.dataIndex]);
		        }
		    },
			onClick: function (e, t) {
			},
		    renderer : function(v, p, record){
		        p.css += ' x-grid3-check-col-td'; 
		        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
		    }
		};
		
/*************  Fin Definición de Plugin para Checkbox en grillas ********************************************/