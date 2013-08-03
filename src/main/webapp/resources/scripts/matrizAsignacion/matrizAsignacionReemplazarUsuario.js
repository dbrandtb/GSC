
function reemplazarUsuario(_cdMatriz, _cdNivatn, _cdUsuario, _cdmodulo, _cdrolmat, _email, _status, _operacion){

var USUARIO_LOG='';

	var dataStoreUsuariosReemplazantes = new Ext.data.Store({
	 	proxy: new Ext.data.HttpProxy({
	     	url: _ACTION_COMBO_USUARIOS_REEMPLAZO
	 	}),
	 	reader: new Ext.data.JsonReader({
	 		root: 'comboUsuariosReemplazantes',
	 		totalProperty: 'totalCount'
	 		},[
				{name: 'codigo', type: 'string', mapping:'cdusuari'},
				{name: 'descripcion', type: 'string', mapping:'dsusuari'}
				//{name: 'cdnivatn', type: 'string'}
			  ])
	});
	
	var hiddenNivatn = new Ext.form.Hidden({id:'hiddenNivatnId', name:'cdnivatn'});
	
	var hiddenCmodulo = new Ext.form.Hidden({id:'cdmodulo', name:'cdmodulo'});
	
	
	//LOS COMBOS
	var comboUsuarios = new Ext.form.ComboBox(
	{
	    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	    store: dataStoreUsuariosReemplazantes,
	    id:'comboUsuariosId',
	    fieldLabel: getLabelFromMap('comboUsuariosId',helpMap,'Usuario'),
	    tooltip: getToolTipFromMap('comboUsuariosId',helpMap,'Listado de Usuarios'),
	    hasHelpIcon:getHelpIconFromMap('comboUsuariosId',helpMap),
		Ayuda: getHelpTextFromMap('comboUsuariosId',helpMap),
	    //anchor:'100%',
	    width:200,
	    displayField:'descripcion',
	    valueField: 'codigo',
	    hiddenName: 'dsusuari',
	    typeAhead: true,
	    allowBlank: false,
	    triggerAction: 'all',
	    mode:'local',
	    emptyText:'Seleccione suplente...',
	    selectOnFocus:true,
	    forceSelection:true,
	    onSelect: function(record){
	    		this.setValue(record.get("codigo"));
	    		this.collapse();
	    		Ext.getCmp('hiddenNivatnId').setValue(record.get('cdnivatn'));
	    	}
	});
	
	dataStoreUsuariosReemplazantes.load({
		params:{//cdusuari: _cdUsuario,
		        cdmatriz: _cdMatriz,
		        cdmodulo: _cdmodulo,
		        cdnivatn: _cdNivatn
		        	        
		        }
	});		
	
	//CONFIGURACION PARA EL FORMULARIO DE EDICIÓN *****************************
	var formPanel = new Ext.FormPanel({
			id:'formPanelId',        
	        iconCls:'logo',       
	        frame:true,
	        //url: _ACTION_OBTENER_TIPOS_ENDOSOS,
	        //reader:_reader,
	        width: 500,
	        bodyStyle:'background: white',
	        height:150,       
	        items: [{
	        	items:[
	        	{        	 
	        	 buttonAlign: "center",
	             labelAlign: 'rigth',
	        	 layout: 'table',
				 layoutConfig: {columns: 2},        	
	             items:[
	             	{                
	       			layout: 'form',
	       			colspan: 2,
	       			html:'<br/>'
					},
	            	{                
	       			layout: 'form',
	       			colspan: 2,
	       			width:350,
	       			labelWidth:75,
	         		items: [comboUsuarios]
					},
					{                
	       			layout: 'form',
	       			colspan: 2,
	       			html:'<br/><br/>'
					}
				],
	        	buttons:[
	        		{
					text:getLabelFromMap('ntfcnButtonGuardarRemUs',helpMap,'Guardar'),
					tooltip: getToolTipFromMap('ntfcnButtonGuardarRemUs',helpMap,'Guarda el reemplazo'),
					handler: function(){reemplazarUsuario();}
					},
					{
					text:getLabelFromMap('ntfcnButtonRegresarRemUs',helpMap,'Regresar'),
					tooltip: getToolTipFromMap('ntfcnButtonRegresarRemUs',helpMap,'Cancela b&uacute;squeda de Asignaciones'),                              
					handler: function(){_window.close();}
	        		}
	       		 ]
	            }]
	        }]
	        			
			});
	
	
	 var _window = new Ext.Window({
	   	width: 510,
	   	id:'winAsigReempUsuId',
	   	title: getLabelFromMap('winAsigReempUsuId', helpMap,'Reemplazar Usuario'),
	   	//title: '<span style="color:black;font-size:14px;">Reemplazar Usuario</span>',
	   	height:175,
	   	minWidth: 300,
	   	minHeight: 175,
	   	layout: 'fit',
	   	modal:true,
	   	plain:true,
	   	bodyStyle:'padding:5px;',
	   	buttonAlign:'center',
	   	items: formPanel
	});
	
	_window.show();
	

	
	function reemplazarUsuario(){
			var conn = new Ext.data.Connection();
            conn.request({
			  	url: _ACTION_GUARDAR_REEMPLAZANTE,
			  	method: 'POST',
			  	params: {	cdmatriz: _cdMatriz,	    				
			  				cdUsuarioOld: _cdUsuario,
				    		cdUsuarioNew:  Ext.getCmp('comboUsuariosId').getValue(),
				    		cdUsuario: USUARIO_LOG,
			                cdnivatn: _cdNivatn
			  			},
						waitMsg : getLabelFromMap('400017', helpMap,'Espere por favor ...'),
				        callback: function (options, success, response) {
				        if (Ext.util.JSON.decode(response.responseText).success == false) {
				            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).actionErrors[0]);
				        }else {
			              Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){
				              														reloadGridResponsables(_cdMatriz,_cdNivatn);
				              														});
				          _window.close();	            
					        }
					   	 }
	 		})
	} 

function actualizarNuevoResponsable(_cdMatriz,_cdUsuario,_cdNivatn,_cdrolmat, _email, _status, _operacion){
		FLAG = 1;
	    var conn = new Ext.data.Connection();
	                conn.request({
				    	url: _ACTION_GUARDAR_RESPONSABLE,
				    	method: 'POST',
				    	params: {
				    				cdmatriz: _cdMatriz,
				    				cdnivatn: _cdNivatn,
						    		cdrolmat: _cdrolmat,
                                    cdusuari: _cdUsuario,  
						    		email: _email,
						    		status: _status,
						    		operacion:'I'
				    			},
	 					waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
        if (Ext.util.JSON.decode(response.responseText).success == false) {
          //  Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).actionErrors[0]);
        //	 _window.close();     
        }else {
             //Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), Ext.util.JSON.decode(response.responseText).actionMessages[0], function(){reloadGridResponsables(_cdMatriz,_cdNivatn);});
             reloadGridResponsables(_cdMatriz,_cdNivatn);
             //_window.close();
           
        }
    }
 });
}

};
