function agregarEncuesta(key){
var CODIGO_ENCUESTA;
var today = new Date();
var mesCabecera =  today.getMonth() + 1;
mesCabecera = (mesCabecera<10)?'0'+mesCabecera:mesCabecera+'';
var todayCabecera = today.getDate()+'/'+mesCabecera+'/'+today.getFullYear();
var todayCabeceraMas1 = today.getDate()+'/'+mesCabecera+'/'+(today.getFullYear()+1);

var dsModulos = new Ext.data.Store ({
					proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_MODULOS_ENCUESTAS}),
					reader: new Ext.data.JsonReader({
							root: 'comboModulosEncuestas',
							id: 'codigo',
							successProperty: '@success'
						}, [
							{name: 'codigo', type: 'string', mapping: 'codigo'},
							{name: 'descripcion', type: 'string', mapping: 'descripcion'}
						]),
					remoteSort: true
			});

var codigoEncuesta = new Ext.form.Hidden({
    	disabled:false,
    	id: 'codigoEncuestaId',
        name:'codigoEncuesta',
        value: key 
    });
			
var dsEstado = new Ext.data.Store ({
					proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ESTADO_ENCUESTAS}),
					reader: new Ext.data.JsonReader({
							root: 'estadosEjecutivo',
							id: 'id',
							successProperty: '@success'
						}, [
							{name: 'codigo', type: 'string', mapping: 'codigo'},
							{name: 'descripcion', type: 'string', mapping: 'descripcion'}
						]),
					remoteSort: true
			});			

/*
var dsListas = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_GET_LISTAS 
            }),
        reader: new Ext.data.JsonReader({
        root: 'comboGetListas',
        id: 'cd'
        },[
       {name: 'cd', mapping:'cd', type: 'string'},
       {name: 'ds', mapping:'ds', type: 'string'}
    ])
});			
*/

var dsListas = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_TABLA 
            }),
        reader: new Ext.data.JsonReader({
        root: 'comboTabla',
        id: 'cd'
        },[
       {name: 'cd', mapping:'dsLabel', type: 'string'},
       {name: 'ds', mapping:'cdTabla', type: 'string'}
    ])
});			


	
var comboListaValores = new Ext.form.ComboBox({
		tpl: '<tpl for="."><div ext:qtip="{cd}.{ds}" class="x-combo-list-item">{ds}</div></tpl>',
		store: dsListas,
		anchor:'90%',
		displayField:'ds',
		valueField: 'cd',
		hiddenName: 'lista',
		header:getLabelFromMap('comboListaValrs',helpMap,'Lista de Valores'),
        tooltip:getToolTipFromMap('comboListaValrs',helpMap,''),
		//hiddenName: 'cdTabla',
		//hiddenName: 'ds',
		typeAhead: true,
		triggerAction: 'all',
		lazyRender: true,
		emptyText:'Selecione Valores...',
		selectOnFocus:true,
		mode: 'local',
		name: 'lista', 
		id:'listaId', 
		forceSelection: true,
		labelSeparator:'',
		listeners: {
	     blur: function () {
			if (this.getRawValue() == "") {
				this.setValue();
				//Ext.getCmp("dsTabla").setValue("");
				var rec1 = gridComun.getSelectionModel().getSelected();
                 rec1.set("dsTabla", "");
      
				}
			  }
		   },
		onSelect: function (record){
    	this.setValue(record.get("ds"));
        this.collapse();
     
        var rec = gridComun.getSelectionModel().getSelected();
         rec.set("dsTabla", record.get("cd"));
      
         
      
    }
});
	
var dsEncuesta = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('dsEncuestaId',helpMap,'Encuesta'),
        tooltip:getToolTipFromMap('dsEncuestaId',helpMap,'Nombre de la Encuesta'),
        hasHelpIcon:getHelpIconFromMap('dsEncuestaId',helpMap),
		Ayuda: getHelpTextFromMap('dsEncuestaId',helpMap),
        id: 'dsEncuestaId', 
        name: 'dsEncuesta',
        allowBlank: false,
        anchor: '90%'
    });

var cdModulo = new Ext.form.ComboBox({
        fieldLabel: getLabelFromMap('cdModuloId',helpMap,'M&oacute;dulo'),
        tooltip:getToolTipFromMap('cdModuloId',helpMap,'M&oacute;dulo'),
        hasHelpIcon:getHelpIconFromMap('cdModuloId',helpMap),
		Ayuda: getHelpTextFromMap('cdModuloId',helpMap),
        store: dsModulos,
        tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>', 
        valueField:'codigo',
        displayField:'descripcion',
        id: 'cdModuloId', 
        name: 'cdModulo',
        hiddenName: 'cdModulo',
        allowBlank: false,
        typeAhead: true,
        //mode: 'local',
        triggerAction: 'all',
        forceSelection: true,
        emptyText:'Seleccione Modulo...',
        selectOnFocus:true,
        anchor: '90%'
    });
    
var cdEstado = new Ext.form.ComboBox({
        fieldLabel: getLabelFromMap('cdEstadoId',helpMap,'Estado'),
        tooltip:getToolTipFromMap('cdEstadoId',helpMap,'Estado'),
         hasHelpIcon:getHelpIconFromMap('cdEstadoId',helpMap),
		Ayuda: getHelpTextFromMap('cdEstadoId',helpMap),
        store: dsEstado,
        tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
        valueField:'codigo',
        displayField:'descripcion',
        id: 'cdEstadoId', 
        name: 'cdEstado',
        hiddenName: 'cdModulo',
        typeAhead: true,
        allowBlank: false,
        triggerAction: 'all',
        mode: 'local',
        forceSelection: true,
        emptyText:'Seleccione Estado...',
        selectOnFocus:true,
        anchor: '90%'
    });

 var ordSolFecha = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('ordSolFechaId',helpMap,'Fecha'),
        tooltip:getToolTipFromMap('ordSolFechaId',helpMap,'Fecha De Solicitud'),
         hasHelpIcon:getHelpIconFromMap('ordSolFechaId',helpMap),
		Ayuda: getHelpTextFromMap('ordSolFechaId',helpMap),
        id: 'ordSolFechaId', 
        name: 'feRegistro',
		//format:'d/m/Y',
        disabled:true,
        /*renderer: function(val) {
		           			try{
		           			var fecha = new Date();
		           			fecha = Date.parseDate(val, 'Y-m-d H:i:s.g');
		           			//alert("Valor: " + val + "\nFecha: " + fecha + "\nformateada : " + fecha.format('d/m/Y'));
               var _val2 = val.format ('Y-m-d H:i:s.g');
              // alert(_val2);
               return _val2.format('d/m/Y');
               }
              catch(e)
              {
              	return fecha.format('d/m/Y');
              }
                },*/
        width: 80//, 
        //value:todayCabecera 
    });  

    
 var swOblig = new Ext.grid.CheckColumn({
      id:'swObligaId',
      header: getLabelFromMap('swObligaId',helpMap,'Obligatorio'),
      tooltip: getToolTipFromMap('swObligaId', helpMap,'Columna Obligatorio'),
       hasHelpIcon:getHelpIconFromMap('swObligaId',helpMap),
		Ayuda: getHelpTextFromMap('swObligaId',helpMap),
      //header: "No Requerida",
      dataIndex: 'swOblig',
      align: 'center',
      sortable: true,
       allowBlank: false,
      width: 80
    });
    
var recordPreguntasEncuesta = new Ext.data.Record.create([
         {name : 'cdEncuesta', mapping : 'cdEncuesta', type : 'string'},
         {name : 'cdPregunta', mapping : 'cdPregunta', type : 'string'},
         {name : 'dsPregunta', mapping : 'dsPregunta', type : 'string'},
         {name : 'ottapval', mapping : 'ottapval', type : 'string'},
         {name : 'swOblig', mapping : 'swObliga', type : 'bool'},
         {name : 'cdDefault', mapping : 'cdDefault', type : 'string'},
         {name : 'cdSecuencia', mapping : 'cdSecuencia', type : 'string'},
         {name : 'ottapval', mapping : 'ottApval', type : 'string'},
          {name : 'dsTabla', mapping : 'dsLabel', type : 'string'}
]);


var readerPreguntasEncuesta = new Ext.data.JsonReader( 
{
        root : 'MEncuestaPregunta',
        totalProperty: 'totalCount',
        successProperty : '@success'
  	},
  	recordPreguntasEncuesta 
  );

function crearGridPreguntasStore(){    
    dsSeccionesFormato = new Ext.data.Store ({
	    	proxy: new Ext.data.HttpProxy({
	        url: _ACTION_BUSCAR_ENCUESTA_PREGUNTAS
     		}),
    		reader: readerPreguntasEncuesta
 	});
  
	return dsSeccionesFormato;
}
          
          
function addGridPreguntaNewRecord(){
  var new_record = new recordPreguntasEncuesta({
               cdEncuesta:'',
               cdPregunta: '',
		       dsPregunta: '',
		       ottapval: '',
		       //dsTabla: '',
		       swOblig: '',
		       cdDefault: '',
		       cdSecuencia: ''
      });
  gridComun.stopEditing();
  gridComun.store.insert(0, new_record);
  gridComun.startEditing(0, 0);
 }    
    
// Definicion de las columnas de la grilla
var cmComun = new Ext.grid.ColumnModel([
       
        {
        dataIndex: 'cdPregunta',
        hidden :true
        }, {
        dataIndex: 'cdSecuencia',
        hidden :true
        },
        {
        header:getLabelFromMap('cmDsEncuesta',helpMap,'Descricpi&oacute;n'),
        tooltip:getToolTipFromMap('cmDsEncuesta',helpMap,'Columna Descricpi&oacute;n'),
        dataIndex: 'dsPregunta',
        sortable: true,
        width: 120,
        editor: new Ext.form.TextField({
      	       //maxValue:99,
      	       //maxLength:2,
              allowBlank: false	  
                             
               }),
               
         renderer:function extGrid_renderer(val, cell, record, row, col, store) {
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
      
        
        swOblig,
          {
        header:getLabelFromMap('cmListaValores',helpMap,'Lista de Valores'),
        tooltip:getToolTipFromMap('cmListaValores',helpMap,'Columna Lista de Valores'),
        dataIndex: 'ottapval',
        sortable: true,
        width: 120,
        editor: comboListaValores,
        renderer: renderComboEditor(comboListaValores)
        },
        {
        header:getLabelFromMap('dsTabla',helpMap,'Valor tabla'),
        tooltip:getToolTipFromMap('dsTabla',helpMap,''),
        dataIndex: 'dsTabla',
        sortable: true,
        width: 120/*,
        editor: new Ext.form.TextField({
        	   id:'dsTablaId',
        	   name: 'dsTablaN',
        	   maxValue:99,
               allowBlank: true,
               disabled:false
               })*/
        },
        {
        header:getLabelFromMap('cmValIni',helpMap,'Valor Inicial'),
        tooltip:getToolTipFromMap('cmValIni',helpMap,'Columna Valor Inicial'),
        dataIndex: 'cdDefault',
        sortable: true,
        width: 120,
        editor: new Ext.form.TextField({
      	       maxValue:99,
               allowBlank: true
               })
        }
        
        
        
])
var gridComun;

function createGridFrmDoc(){
    //if (gridComun != null && gridComun != undefined) return;
       gridComun= new Ext.grid.EditorGridPanel({
            id: 'gridComun',
            //el:'gridElementos',
            title: '<span style="height:10">Configurar Preguntas</span>',
          
            store: crearGridPreguntasStore(),
             buttonAlign: "center",
            //reader:jsonGrillaPreguntasEncuesta,
            border:true,
            cm: cmComun,
            clicksToEdit:1,
            //plugins: [swObliga],
            plugins: [swOblig,new Ext.ux.plugins.GridValidator()/*plugins para validar la celda en el jsp se debe agregar: <script src="${ctx}/resources/scripts/ValidacionGrilla/Ext.ux.plugins.GridValidator.js" type="text/javascript"></script>*/ ],
            successProperty: 'success',
            buttons:[
             {
	           	  text:getLabelFromMap('confOTBtnAdd',helpMap,'Agregar'),
          		tooltip: getToolTipFromMap('confOTBtnAdd',helpMap,'Agrega pregunta'),
	           	  handler: function () {
	           	  		if (incisosForm.form.isValid())
	           	  		//if(Ext.getCmd('dsEncuestaId').getValue()!=''&& Ext.getCmd('cdModuloId').getValue()!='' && Ext.getCmd('cdEstadoId').getValue()!='')
	           	  		{
	           	  			addGridPreguntaNewRecord();
	           	  		}
	           	  		else{
                        	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
	           	  			//Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe Ingresar los Datos Obligatorios'));
	           	  		}
	           	  	}
	              },
                  {
                  text:getLabelFromMap('gridFrmDocButtonAgregar',helpMap,'Guardar'),
                  tooltip:getToolTipFromMap('gridFrmDocButtonAgregar',helpMap,'Agrega un nuevo formato de Documento'),
                  handler: function(){ 
                  	     
						if (incisosForm.form.isValid()){
							
							cols = gridComun.colModel.getColumnCount(); // determino cantidad de columnas de la grilla
                            rows = gridComun.store.getCount();		  // determino cantidad de filas de la grilla
              	
                         	var bandValid = 0; // bandera para saber si ya todas las celdas de mi grilla son validas
              	
              	            //recorro toda mi grilla preguntando si las celdas son validas
              	           for (var i = 0; i < rows; i++) {
              	         	for (var j = 0; j < cols; j++) {
              		            if(!gridComun.isCellValid(j,i)){
              		   		       bandValid =+ 1;
              		                 }
              		               }
              		
                        	    }
                        	    
                           if (bandValid != 0){
              		         Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
              	             }else{/*
              		             guardarEncuestaPreguntas(gridComun);
              	               }*/
						   										  
							
							
							var _band = gridComun.store.getModifiedRecords(); 
							if(_band.length!=0)
							{ 
								guardarEncuestaPreguntas(gridComun);
							}else{
								Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe ingresar un registro para realizar esta operaci&oacute;n'));
							}
              	             }
						}
						
						else{
   							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));
						}
					}                
                  },
                 
                  {
                  text:getLabelFromMap('gridFrmDocButtonBorrar',helpMap,'Eliminar'),
                  tooltip:getToolTipFromMap('gridFrmDocButtonBorrar',helpMap,'Elimina un formato de Documento'),
                  handler:function(){
						if (!gridComun.getSelectionModel().getSelected()) {
							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));	               
	   					}
	   					else
	   						if (gridComun.getSelectionModel().getSelected().get("cdPregunta")==""){
	   							gridComun.store.remove(gridComun.getSelectionModel().getSelected());
	   						}else{
	   	    		    			borrarPreguntaEncuesta(gridComun.getSelectionModel().getSelected().get("cdPregunta"));
	   						}
               		}

                  },
                  {
                  id: 'exportarId',
                  text:getLabelFromMap('gridFrmDocButtonExportar',helpMap,'Exportar'),
                  tooltip:getToolTipFromMap('gridFrmDocButtonExportar',helpMap,'Exporta un formato de Documento'),
                  handler:function(){
                        var url = _ACTION_ENCUESTAS_PREGUNTAS_EXPORTAR + '?cdEncuesta=' + CODIGO_ENCUESTA;
                        showExportDialog(url);
                    }                 
                  }, {
                  text:getLabelFromMap('gridFrmDocButtonRegresar',helpMap,'Regresar'),
                  tooltip:getToolTipFromMap('gridFrmDocButtonRegresar',helpMap,'Regresa a la pantalla anterior'),
                  handler:function(){
                         _window.close();
                       /* if (vistaTipo!=1){
                                win_flotante.hide();
                         }*/
                   }                  
                  }
                  ],
            width:600,
            frame:true,
            height:250,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
            stripeRows: true,
            collapsible: true,
            bbar: new Ext.PagingToolbar({
                    pageSize:10,
                    store: dsSeccionesFormato,
                    displayInfo: true,
                    displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
                    emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
                })          
        });
   //gridComun.render();
}
    
createGridFrmDoc();

var incisosForm = new Ext.FormPanel({
		id: 'incisosForm',
        //el:'formDocumentos',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosForm',helpMap,'Configurar Encuesta')+'</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,   
        url: _ACTION_BUSCAR_ENCUESTAS_GET,
        reader: jsonEncuestaPregunta,
        width: 600,
        height:120,
        items: [{
        		layout:'form',
				border: false,
				items:[{
                		bodyStyle:'background: white',
        		        labelWidth: 60,
                        layout: 'form',
        				frame:true,
        		       	baseCls: '',
        		       	buttonAlign: "center",
                		items: [{
            		        	layout:'column',
        	 				    border:false,
        	 				    columnWidth: 1,
            		    		items:[
            		    			{
        					    	columnWidth:.65,
                    				layout: 'form',
        	                		border: false,
            		        		items:[       		        				
            		        				 codigoEncuesta,
            		        				 dsEncuesta,
            		        				 //cdModulo,
            		        				 cdEstado
                                           ]
        							},
        							{
        							columnWidth:.35,
                    				layout: 'form',
                    				border: false,
            		        		items:[ ordSolFecha ]
                    				}]
                    			}]
            			}]
        		}]
})   

function reloadGridPregunta(){
	var _params = {
			cdEncuesta: CODIGO_ENCUESTA
	};
	reloadComponentStore(gridComun, _params, cbkReload);
}
function cbkReload(_r, _options, _success, _store) {
	if (!_success){
		_store.removeAll();
		//addGridNewRecord();
	}
}

_window = new Ext.Window ({

	width: 610,
    modal: true,
	height: true,
	items: [
            incisosForm,
            gridComun
           ]
       
});

if (typeof(key)!= "undefined")
{
	//alert(1);
	CODIGO_ENCUESTA = key;
	incisosForm.form.load({
		waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
	    waitMsg: getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
	    params: {cdEncuesta: key},
	    success: function(form, action) {
		    var _swEstado = action.reader.jsonData.MEstructuraList[0].swEstado;
			// CARGAMOS EL COMBO
		 	dsEstado.load({
	    		callback: function (r, options, success){
	    			if (success) {
							  incisosForm.findById('cdEstadoId').setValue(_swEstado);
							}
	    			}
	    		});           			
	   		reloadGridPregunta();
	   },
	   failure: function () {
           Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400033', helpMap,'No se encontraron registros'));
      }                   
	});
}
else
{
	incisosForm.findById('ordSolFechaId').setValue(todayCabecera);
	dsEstado.load();
	Ext.getCmp('exportarId').setVisible(false);
	CODIGO_ENCUESTA="";
	
}

_window.show();	
dsListas.load();
dsModulos.load();
//dsEstado.load();


function guardarEncuestaPreguntas(gridComun){
	var _url="";
	if (CODIGO_ENCUESTA!="")
	{
		//alert(Ext.getCmp('dsEncuestaId').getValue());
		_url=_ACTION_GUARDAR_ENCUESTA_PREGUNTA_AGREGAR;
	}else{
		//alert(2);
		_url=_ACTION_GUARDAR_ENCUESTA_PREGUNTA;
    }
	var arreglo ="encuestaPreguntaVO.cdEncuesta="+CODIGO_ENCUESTA+"&encuestaPreguntaVO.dsEncuesta="+Ext.getCmp('dsEncuestaId').getValue()+"&encuestaPreguntaVO.swEstado="+Ext.getCmp('cdEstadoId').getValue()+"&";
		    arreglo +=generarStringGrillaPreguntas(gridComun);
	var conn = new Ext.data.Connection();
            conn.request({
   	               url: _url,
   	               method: 'POST',
   	               params: arreglo,
   	               callback: function (options, success, response) {
	                       if (Ext.util.JSON.decode(response.responseText).success == false) {
	                       		 gridComun.store.rejectChanges();
	                           //gridComun.store.remove(gridComun.getSelectionModel().getSelected());
	                           Ext.Msg.alert('Error', Ext.util.JSON.decode(response.responseText).errorMessages[0]);
	                           
	                           reloadGridPregunta();
	                           
	                       } else {
	                           CODIGO_ENCUESTA = Ext.util.JSON.decode(response.responseText).codEncuesta;
	                           //alert(CODIGO_ENCUESTA);
	                           Ext.Msg.alert('Aviso', Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){reloadGridPregunta(); reloadGrid();});
	                           if (typeof(key)!= "undefined")
	                                {
	                                	_window.close();	
	                                }
	                                else {
	                                      if ((gridComun.store.getCount())!=0){_window.close();}
	                                     }
                           }
	                   }
             });
}
	
function generarStringGrillaPreguntas(grilla){
		var arreglo = "";
		var registros = grilla.store.getModifiedRecords();
		grilla.stopEditing();
		for (var i=0; i<registros.length; i++) {
			arreglo += "encuestaPreguntaVO.preguntaEncuestaVOs["+i+"].cdEncuesta="+CODIGO_ENCUESTA+"&";
			arreglo += "encuestaPreguntaVO.preguntaEncuestaVOs["+i+"].cdPregunta="+registros[i].get('cdPregunta')+"&";
			arreglo += "encuestaPreguntaVO.preguntaEncuestaVOs["+i+"].dsPregunta="+registros[i].get('dsPregunta')+"&";
			//arreglo += "encuestaPreguntaVO.preguntaEncuestaVOs["+i+"].ottApval="+registros[i].get('ottapval')+"&";
			arreglo += "encuestaPreguntaVO.preguntaEncuestaVOs["+i+"].ottApval="+registros[i].get('ottapval')+"&";
			arreglo += "encuestaPreguntaVO.preguntaEncuestaVOs["+i+"].swObliga="+registros[i].get('swOblig')+"&";
			arreglo += "encuestaPreguntaVO.preguntaEncuestaVOs["+i+"].cdDefault="+registros[i].get('cdDefault')+"&";			
			arreglo += "encuestaPreguntaVO.preguntaEncuestaVOs["+i+"].cdSecuencia="+registros[i].get('cdSecuencia')+"&";
			
		}
		return arreglo;
	}


function borrarPreguntaEncuesta(key) {
	if(key != "")
	{
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
		{
	        if (btn == "yes")
	        {
				var _params = {
						cdPregunta: key
				};
				execConnection(_ACTION_BORRAR_ENCUESTA_PREGUNTA, _params, cbkBorrar);
           }
		}
		)
	}else{
			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operacion'));
	}
}

function cbkBorrar(_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGridPregunta();});
	}
}

function reloadGridFrmDoc(){
		var _params = {
        		dsNomFormato: Ext.getCmp('incisosForm').form.findField('desNomFormato').getValue()
		};
		reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReloadFrmDoc);
}

function cbkReloadFrmDoc(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}	
}

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

		            this.grid.getSelectionModel().selectRow(index); //Selecciona la fila completa
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
