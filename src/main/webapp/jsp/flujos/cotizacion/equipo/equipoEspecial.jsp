<script type="text/javascript">
    var _ACTION_CARGA_TIPOS="<s:url action='cargaListaTipo' namespace='/flujocotizacion'/>";
    var _ACTION_CARGA_ITEMS="<s:url action='cargaItems' namespace='/flujocotizacion'/>";
    var _ACTION_EQUIPO_EDICION="<s:url action='cargaEdicion' namespace='/flujocotizacion'/>";
    var _ACTION_GUARDA_EQUIPO="<s:url action='guardaEquipo' namespace='/flujocotizacion'/>";
    var _ACTION_GUARDA_EQUIPO_EDICION="<s:url action='editaEquipo' namespace='/flujocotizacion'/>";
    var _ACTION_LISTA_EQUIPO="<s:url action='cargaListaEspecial' namespace='/flujocotizacion'/>";
    var _ACTION_SERVICIO_EQUIPO="<s:url action='servicioEquipo' namespace='/flujocotizacion'/>";
    var _TEXT_BUTTON_ACTION="<s:url action='obtieneEtiqueta' namespace='/flujocotizacion'/>";
</script>
<script type="text/javascript" src="${ctx}/resources/jsp-script/flujos/cotizacion/equipoEspecial/borrarEquipo.js"></script>
<script type="text/javascript">
Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	function autorizaciones(){
    	url=_ACTION_LISTA_EQUIPO
    	storeEquipo = new Ext.data.Store({
        
	    proxy: new Ext.data.HttpProxy({
    	    url: url
    	}),
    	reader: new Ext.data.JsonReader({
        	root:'listObjetoCotizacion',
        	totalProperty: 'totalCount'
	        //id: 'nmObjeto'
        	},[
        	{name: 'cdUnieco',  type: 'string',  mapping:'cdUnieco'},
        	{name: 'cdRamo',    type: 'string',  mapping:'cdRamo'},
        	{name: 'estado',    type: 'string',  mapping:'estado'},
        	{name: 'nmPoliza',  type: 'string',  mapping:'nmPoliza'},
        	{name: 'nmSituac',  type: 'string',  mapping:'nmSituac'},
        	{name: 'cdTipobj',  type: 'string',  mapping:'cdTipobj'},
        	{name: 'nmSuplem',  type: 'string',  mapping:'nmSuplem'},
        	{name: 'status',    type: 'string',  mapping:'status'},
        	{name: 'nmObjeto',  type: 'string',  mapping:'nmObjeto'},
        	{name: 'dsObjeto',  type: 'string',  mapping:'dsObjeto'},
        	{name: 'ptObjeto',  type: 'string',  mapping:'ptObjeto'},
        	{name: 'cdAgrupa',  type: 'string',  mapping:'cdAgrupa'},
        	{name: 'nmValor',   type: 'string',  mapping:'nmValor'},
            {name: 'dsDescripcion',   type: 'string',  mapping:'dsDescripcion'}           
        ]),
        remoteSort: true
    	});
    	storeEquipo.setDefaultSort('cdUnieco', 'desc');
    	//storeEquipo.load();
    	return storeEquipo;
	}
	var storeEquipo;
	var itemsVariables;
	var validacion;
	var windowEquipo;
	var valorCombo;


	function toggleDetails(btn, pressed){
	    var view = grid.getView();
	    view.showPreview = pressed;
	    view.refresh();
	}

    var cmEquipoEspecial = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        {header: "Tipo",    dataIndex:'dsObjeto',   width: 100, sortable:true},      
        {header: "Descripción", dataIndex:'dsDescripcion',   width: 100, sortable:true},
        {header: "Suma Asegurada",   dataIndex:'ptObjeto',   width: 50, sortable:true, renderer:Ext.util.Format.usMoney}
    ]);
    var grid4;
    var selectedId;
    var cdUnieco;
    var cdRamo;
    var estado;
    var nmPoliza;
    var nmSituac;
    var cdTipobj;
    var nmSuplem;
    var status;
    var nmObjeto;
    var dsObjeto;
    var ptObjeto;
    var cdAgrupa;
    var nmValor;
    var dataStoreTipos;

    function getSelectedRecord(){
        var m = grid4.getSelections();
        if (m.length == 1 ) {
           return m[0];
        }
	}
    var afuera;
    var temporal=-1;
    grid4= new Ext.grid.GridPanel({
        store:autorizaciones(),
        id:'grid-producto-especial',
        border:true,
        cm: cmEquipoEspecial,
        width:476,
        height:200,
        buttonAlign:'left',
        frame:true,     
        bodyStyle:'padding:5px',
        viewConfig: {autoFill: true,forceFit:true},  
        buttons:[{
                text:'Editar',
                id:'editar-elemento-grid',
                tooltip:'Edita registro seleccionado'              
                },{
                text:'Eliminar',
                id:'borrar-elemento-grid',
                tooltip:'Elimina P&aacute;gina seleccionada'
                }],
        sm: new Ext.grid.RowSelectionModel({
        	singleSelect: true,
        	listeners: {
            	rowselect: function(sm, row, rec) {
                	Ext.getCmp('borrar-elemento-grid').on('click',function(){
                    	cdUnieco  = rec.get('cdUnieco');
                    	cdRamo    = rec.get('cdRamo');
                    	estado    = rec.get('estado');
                    	nmPoliza  = rec.get('nmPoliza');
                    	nmSituac  = rec.get('nmSituac');
                    	cdTipobj  = rec.get('cdTipobj');
                    	nmSuplem  = rec.get('nmSuplem');
                    	status    = rec.get('status');
                    	nmObjeto  = rec.get('nmObjeto');
                    	dsObjeto  = rec.get('dsObjeto');
                    	ptObjeto  = rec.get('ptObjeto');
                    	cdAgrupa  = rec.get('cdAgrupa');
                    	nmValor   = rec.get('nmValor');
                    	borrarEquipo(storeEquipo, cdUnieco, cdRamo, estado, nmPoliza, 
                                        nmSituac, cdTipobj, nmSuplem, status,  
                                        nmObjeto, dsObjeto, ptObjeto,cdAgrupa, nmValor);                                         
					});
                	afuera=row;
                	
                	Ext.getCmp('editar-elemento-grid').on('click',function(){
                		if(afuera!=temporal){
            				temporal=afuera;
            				var record = storeEquipo.getAt(temporal);
                        	cdTipobj  = record.get('cdTipobj');
                        	nmObjeto  = record.get('nmObjeto');
                        	dsObjeto  = record.get('dsObjeto');
                        	ptObjeto  = record.get('ptObjeto');
                        	obtenerEdicion(storeEquipo, cdTipobj, nmObjeto,dsObjeto,ptObjeto);
                		}
                	});
                	temporal=-1;
				}
			}
		}),            
        bbar: new Ext.PagingToolbar({
            pageSize:25,
            store: storeEquipo,                     
            displayInfo: true,
            displayMsg: 'Registros mostrados {0} - {1} de {2}',
            emptyMsg:   'No hay registros para mostrar',
    		beforePageText: 'P&aacute;gina',
    		afterPageText:  'de {0}'
        })              
               
    });

    dataStoreTipos = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
        	url:_ACTION_CARGA_TIPOS
    	}),
    	reader: new Ext.data.JsonReader({
        	root: 'listaTipo'
        },[
            {name: 'value',  type: 'string',  mapping:'value'},
            {name: 'label',  type: 'string',  mapping:'label'}    
        ]),
        remoteSort: true
    });
    dataStoreTipos.setDefaultSort('label', 'desc');

    agregarEquipo = function(storeEquipo){
    
    
		/*ok*/
    	var montoCotizar = new Ext.form.TextField({
        	fieldLabel: 'Suma Asegurada',
        	labelSeparator: '',
        	width: 300,
        	blankText : 'Suma Asegurada',
        	allowBlank: false,
        	name:'montoCotizar',
        	maxLength: 20,
        	validator: function(){
        		if( isNaN(this.getValue()) ){
        			return 'Introduzca un n&uacute;mero v&aacute;lido';
        		}else if(this.getValue() < 0){
        			return 'Introduzca un n&uacute;mero positivo'
        		}else if(this.getValue().charAt(this.getValue().length-1) == '.'){
        			//Si el ultimo caracter fue '.' es error 
        			return 'Introduzca un n&uacute;mero v&aacute;lido';
        		}else if( this.getValue().search('\\.') != -1 ){
        			//Si tiene punto checamos que tenga solo el limite de decimales deseado
        			var LIMITE_DECIMALES = 4;
        			var indicePunto = this.getValue().search('\\.');
        			if(indicePunto + LIMITE_DECIMALES < this.getValue().length-1 ){
        				//this.setValue( this.getValue().substr(0, indicePunto + limiteDecimales +1) );
        				return 'Introduzca un valor con cuatro decimales como m&aacute;ximo';
        			}else{
        				return true;
        			}
        		}
        		else{
					return true;
        		}
        	}
		});
                
            
    	var comboTipos = new Ext.form.ComboBox({
			id:'combo-tipos-equipo-especial',
			tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
			store: dataStoreTipos,
			width: 300,
			mode: 'local',
			name: 'descripcionTipo',
			typeAhead: true,
			allowBlank:false,
			labelSeparator:'',
			triggerAction: 'all',
			displayField:'label',
			forceSelection: true,
			fieldLabel: 'Tipo',
			emptyText:'Seleccione un Tipo...',
			selectOnFocus:true,
			onSelect : function(record, index, skipCollapse){
                    if(this.fireEvent('beforeselect', this, record, index) !== false){
                        this.setValue(record.data[this.valueField || this.displayField]);
                        if( !skipCollapse ) {
                            this.collapse();
                        }
                        this.lastSelectedIndex = index + 1;
                        this.fireEvent('select', this, record, index);
                    }
                    
                    var valor=record.get('value');
                    var label=record.get('label');
                    var params="";                  
                    params  = "claveObjeto="+valor;
                    params += "&& descripcionObjeto="+label;
                    var conn = new Ext.data.Connection();
                    conn.request ({
                        url:_ACTION_CARGA_ITEMS,
                        method: 'POST',
                        successProperty : '@success',
                        params : params,
                        callback: function (options, success, response) {
                                                                            
                            var codeExtResp=Ext.util.JSON.decode(response.responseText).itemLista;    
                            var codeExtJson=Ext.util.JSON.encode(codeExtResp);
                            if(codeExtResp != ''){
                                var newStartStore = "\"store\":";
                                var newEndStore = ",\"triggerAction\"";
                                var onSelectVar = "";
                                
                                codeExtJson = codeExtJson.replace(/\"store\":\"/gi, newStartStore);
                                codeExtJson = codeExtJson.replace(/\",\"triggerAction\"/gi, newEndStore);
                                codeExtJson = codeExtJson.replace(/,\"onSelect\":null/gi, onSelectVar);
                            }
                            itemsVariables=Ext.util.JSON.decode(codeExtJson);                      
                            validacion=Ext.util.JSON.decode(response.responseText).valido;
                            valorCombo = Ext.util.JSON.decode(response.responseText).labelCombo;                           
                            if(validacion==true){
                                windowEquipo.close();
                                agregarEquipo(storeEquipo);
                                Ext.getCmp('combo-tipos-equipo-especial').setValue(valorCombo);
                            }else{
                                itemsVariables='';
                                windowEquipo.close();
                                agregarEquipo(storeEquipo);
                                Ext.getCmp('combo-tipos-equipo-especial').setValue(valorCombo);
                            }                                           
                        }
                    });
			}
        });
        
        dataStoreTipos.load();

    	var agregarForm = new Ext.form.FormPanel({
			id:'recarga-forma-window',
			url:_ACTION_GUARDA_EQUIPO,
			boder:false,
			frame:true,
			autoScroll:true,
			method:'post',
			width: 570,
			buttonAlign: "center",
			baseCls:'x-plain',
			labelWidth:75,
			items:[
				comboTipos,
				{  
					layout:'form',
					border:false,
					frame:true,
					method:'post',
					baseCls:'x-plain',
					autoScroll:true,
					items: itemsVariables
				},{  
					layout:'form',
					id:'forma-equipo-especial-variable',
					border:false,
					frame:true,
					method:'post',
					baseCls:'x-plain',
					autoScroll:true,
					items:[montoCotizar]
				}
			]
		});
                                
    	itemsVariables=null;
    
    	var windowEquipo = new Ext.Window({
	        title: 'Equipo Especial',
        	width: 510,
        	height:350,
        	autoScroll:true,
        	maximizable:true,
        	minWidth: 500,
        	minHeight: 200,
        	layout: 'fit',
        	modal:true,
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: agregarForm,
        	buttons:
        		[
        		{
            		text: 'Incluir',
            		handler: function() {
	                	if (agregarForm.form.isValid()) {
    	                	agregarForm.form.submit({
        	                	url:'',
            	            	waitTitle:'Espere',
                	        	waitMsg:'Procesando',
                    	    	failure: function(form, action) {
                        	    	Ext.MessageBox.alert('Estado','El equipo no se guard&oacute');
                        		},
                        		success: function(form, action) {
	                            	Ext.MessageBox.alert('Estado', 'Equipo Guardado');
                            		windowEquipo.close();
                            		storeEquipo.load();
                        		}
                    		});
                		} else{
	                    	Ext.MessageBox.alert('¡Error!', 'Por favor Verifique los Errores!');
                		}
            		}
	        	},{
	        		text: 'Regresar',
            		handler: function(){windowEquipo.close();}
        }]
    	});
    	windowEquipo.show();
	};
	var labelObjeto;
	function obtieneEtiqueta(){
    	var conn = new Ext.data.Connection();
    	conn.request ({
	        url:_TEXT_BUTTON_ACTION,
        	method: 'POST',
        	successProperty : '@success',
        	callback: function (options, success, response) {
        		labelObjeto=Ext.util.JSON.decode(response.responseText).etiqueta;
            	if(!Ext.isEmpty(labelObjeto)){
                	Ext.getCmp('boton-texto').setText(labelObjeto);
            	}else{
                	Ext.getCmp('panel-forma-equipo-especial').hide();
            	}
        	}
    	});
	};
	obtieneEtiqueta();

	formPanelEditable = new Ext.FormPanel({
        id:'panel-forma-equipo-especial',
        width:500,
        border:false,        
        items:[{
                layout:'form',
                border:false,
                buttonAlign:'left',
                items:[{xtype:'button',  
                    id:'boton-texto',                                       
                    name: 'AgregarCondicion', 
                    buttonAlign: "right",                        
                    handler: function(){
                        agregarEquipo(storeEquipo);
                    }
                },{
					xtype:'fieldset',
					width:500,
					autoHeight :true,
					frame:true,
					title:'<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Equipo</span>',
					items:[grid4]
				}]                                              
        }]
    });
	var itemsEdicion;
	var labelComboEdicion;
	var montoEquipoEdicion;

	
    /*se crea la ventana de editar*/    
	obtenerEdicion = function (storeEquipo, cdTipobj, nmObjeto, dsObjeto, ptObjeto){
    	if( cdTipobj!='' &&  nmObjeto != '' && dsObjeto !='' && ptObjeto!=''){
        	var params="";
			params ="cdTipobj="+cdTipobj;
	        params +="&&nmObjeto="+nmObjeto;
        	params +="&&dsObjeto="+dsObjeto;
        	params +="&&ptObjeto="+ptObjeto;
	    	var conn = new Ext.data.Connection();
        	conn.request ({
		    	url:_ACTION_EQUIPO_EDICION,
            	method: 'POST',
		    	successProperty : '@success',
		    	params : params,
            	callback: function (options, success, response) {
                	montoEquipoEdicion = Ext.util.JSON.decode(response.responseText).montoObjetoEdit;
                	labelComboEdicion  = Ext.util.JSON.decode(response.responseText).descripcionObjetoEdit;
        	    	var codeExtRespEdit=Ext.util.JSON.decode(response.responseText).itemEdicion;
                	var codeExtJson=Ext.util.JSON.encode(codeExtRespEdit);
                	if(codeExtRespEdit != ''){
                    	var newStartStore = "\"store\":";
                    	var newEndStore = ",\"triggerAction\"";
                    	var onSelectVar = "";
                
						codeExtJson = codeExtJson.replace(/\"store\":\"/gi, newStartStore);
						codeExtJson = codeExtJson.replace(/\",\"triggerAction\"/gi, newEndStore);
						codeExtJson = codeExtJson.replace(/,\"onSelect\":null/gi, onSelectVar);
					}
                	itemsEdicion=Ext.util.JSON.decode(codeExtJson);
                	if(itemsEdicion !=''){
                    	editarEquipo();
                    	if(montoEquipoEdicion != '' && labelComboEdicion !=''){
                    		Ext.getCmp('id-combo-descripcion-objeto-equipo').setValue(labelComboEdicion);
                    		Ext.getCmp('id-monto-equipo').setValue(montoEquipoEdicion);
                    		Ext.getCmp('hidden-combo-edicion-objetos-cotiza').setValue(labelComboEdicion);
                    	}
                	}else{
                		Ext.MessageBox.alert('¡Error!', 'Por favor consulte soporte');
						itemsEdicion='';
                	}
            	}
			});
		}
	};
    


	editarEquipo = function(){
	
		var dsObjeto = new Ext.form.ComboBox({
	        id:'id-combo-descripcion-objeto-equipo',
        	tpl: '<tpl for="."><div ext:qtip="{label}. {value}" class="x-combo-list-item">{label}</div></tpl>',
        	store: dataStoreTipos,
        	width: 300,
        	mode: 'local',
	        //name: 'dsObjetoEdit',
	        typeAhead: true,
	        allowBlank:false,
        	labelSeparator:'',
	        triggerAction: 'all',
        	displayField:'label',
	        //valueField:'value',
	        //hiddenName:'dsObjetoEdit',
        	disabled:true,
        	forceSelection: true,
        	fieldLabel: 'Tipo',
        	emptyText:'Seleccione un Tipo...',
        	selectOnFocus:true
    	});
		var ptObjeto = new Ext.form.TextField({
        	id:'id-monto-equipo',
        	fieldLabel: 'Suma Asegurada',
        	labelSeparator:'',
        	width:300,
        	blankText : 'Suma Asegurada',
        	allowBlank:false,
        	name:'ptObjetoEdit',
        	maxLength: 20,
        	validator: function(){
        		if( isNaN(this.getValue()) ){
        			return 'Introduzca un n&uacute;mero v&aacute;lido';
        		}else if(this.getValue() < 0){
        			return 'Introduzca un n&uacute;mero positivo'
        		}else if(this.getValue().charAt(this.getValue().length-1) == '.'){
        			//Si el ultimo caracter fue '.' es error 
        			return 'Introduzca un n&uacute;mero v&aacute;lido';
        		}else if( this.getValue().search('\\.') != -1 ){
        			//Si tiene punto checamos que tenga solo el limite de decimales deseado
        			var LIMITE_DECIMALES = 4;
        			var indicePunto = this.getValue().search('\\.');
        			if(indicePunto + LIMITE_DECIMALES < this.getValue().length-1 ){
        				//this.setValue( this.getValue().substr(0, indicePunto + limiteDecimales +1) );
        				return 'Introduzca un valor con cuatro decimales como m&aacute;ximo';
        			}else{
        				return true;
        			}
        		}
        		else{
					return true;
        		}
        	}
    	});
		
    	var editForma= new Ext.FormPanel({
			id:'edita-equipo-especial',
        	labelWidth: 75,
			baseCls: 'x-plain',
			url:_ACTION_GUARDA_EQUIPO_EDICION,
			items:[dsObjeto,
					{
                        layout:'form',                                  
                        border:false,
                        frame:true,
                        method:'post',  
                        baseCls:'x-plain',
                        autoScroll:true,                            
                        items:itemsEdicion                                                
					},
					ptObjeto,
					{xtype:'hidden',id:'hidden-combo-edicion-objetos-cotiza',name:'dsObjetoEdit'}]
        });
    	itemsEdicion=null;
    
    	var windowEditaEquipo = new Ext.Window({
        	title: 'Editar Equipo Especial',
        	minHeight: 100,
        	minWidth: 300,
        	width: 500,
        	height:350,
        	layout: 'fit',
        	plain:true,
        	modal:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: editForma,
        	closable: false, 
        	buttons: [
        		{
                    text: 'Guardar', 
                    handler: function() {
                        if (editForma.form.isValid()) {
                        	editForma.form.submit({
                                    waitTitle:'Espere',
                                    waitMsg:'Procesando...',
                                    failure: function(form, action) {
                                        Ext.MessageBox.alert('Estado','Error en la Edicion ');                              
                                    },
                                    success: function(form, action) {
                                        Ext.MessageBox.alert('Estado','Editado Exitosamente');
                                        windowEditaEquipo.close();
                                        storeEquipo.load();                                                                                                                                                                                               
                                    }
                                });                   
                        }else{
                            Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                        }             
                    }
                },{
                    text: 'Regresar',
                    handler: function(){
                    	Ext.getCmp('grid-producto-especial').getSelectionModel().clearSelections();
                    	windowEditaEquipo.close();
                    }
                }]
        });
    windowEditaEquipo.show();   
    };

   
    formPanelEditable.render("forma");
});
</script>

