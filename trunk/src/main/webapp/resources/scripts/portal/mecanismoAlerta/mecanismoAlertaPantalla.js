//var helpMap = new Map();
function validarMensajesAlertaPantalla(seccionDePantalla){
	

	var _indexRegistroActual;
	var _indexSiguienteReg;

	var jsonGrillaMecanismoAlertaPantalla = new Ext.data.JsonReader({
		root : 'MEstructuraList',
		totalProperty : 'totalCount',
		successProperty : '@success'
	}, [{
		name : 'dsMensaje',
		mapping : 'dsMensaje',
		type : 'string'
	}]);

	var storeGrillaMecanismoAlertaPantalla = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : _OBTENER_MENSAJES_ALERTA_PANTALLA
		}),
		reader : jsonGrillaMecanismoAlertaPantalla
	});

	var mensajesAlertaPantalla = new Ext.form.HtmlEditor({
		// name:'dsMensajeAlertaPantalla',
		// id:'dsMensajeAlertaPantallaId',
		width : 150,
		heigth : 120,
		enableAlignments : false,
		enableColors : false,
		enableFont : false,
		enableFontSize : false,
		enableFormat : false,
		enableLinks : false,
		enableLists : false,
		enableSourceEdit : false,
		hideLabel : true,
		labelSeparator : '',
		store : storeGrillaMecanismoAlertaPantalla,
		displayField : 'dsMensaje'
	});

	var botonesPantalla = new Ext.PagingToolbar({
		pageSize : 1,
		store : storeGrillaMecanismoAlertaPantalla,
		displayInfo : false,
		displayMsg : '{0} de {2}',
		emptyMsg : 'No hay avisos',
		beforePageText : '',
		firstText : "Primer Aviso",
		prevText : "Aviso Anterior",
		nextText : "Siguiente Aviso",
		overrideOnClick : true,
		lastText : "Ultimo Aviso",
		hidden : true
	});

	// Configuracion del intervalo de tiempo y cambio de mensajes de alerta
	var intervaloPantalla = setInterval(
	
		function movePagePantalla (_index) {
			if(storeGrillaMecanismoAlertaPantalla.getTotalCount() > 0){
				//Avanzar al siguiente registro si hay mas, sino se regresa al primer registro del store
				if(_indexRegistroActual + 1  < storeGrillaMecanismoAlertaPantalla.getTotalCount()){
					_indexSiguienteReg = _indexRegistroActual + 1;
					_indexRegistroActual++;
				}else{
					_indexRegistroActual = 0;
					_indexSiguienteReg = 0;
				}
				mensajesAlertaPantalla.setValue("<center>" + storeGrillaMecanismoAlertaPantalla.getAt(_indexSiguienteReg).get("dsMensaje") + "</center>");
			}
		}
		, 12000);
	

	var formPanelPantalla = new Ext.FormPanel({
		id : 'formPanelPantallaId',
		layout : 'table',
		layoutConfig : {
			columns : 1
		},
		url : _OBTENER_MENSAJES_ALERTA,
		labelWidth : 0,
		frame : true,
		bodyStyle : ('padding:0 5px 5px 0', 'background: white'),
		width : 160,
		store : storeGrillaMecanismoAlertaPantalla,
		items : [{
			layout : 'fit',
			width : 150,
			height : 120,
			items : [mensajesAlertaPantalla]
		}, {
			layout : 'form',
			width : 150,
			items : [botonesPantalla]
		}]
	});


	// Funcion utilizada para cambiar las alertas con botones del toolbar (si es que queremos desplegarlos)
	function movePagePantalla(_index) {
		var B = {}, A = botonesPantalla.paramNames;
		B[A.start] = _index;
		B[A.limit] = botonesPantalla.pageSize;
		botonesPantalla.store.load({
			params : B,
			callback : function(r, options, success) {
				if (success) {
					mensajesAlertaPantalla.setValue("<center>" + storeGrillaMecanismoAlertaPantalla.getAt(0).get("dsMensaje") + "</center>");
				}
			}
		});
	}


	formPanelPantalla.store.load({
		callback:function(){
			//Renderear form iniciando con la 1er alerta
			if (storeGrillaMecanismoAlertaPantalla.getTotalCount() > 0){
				mensajesAlertaPantalla.setValue("<center>" + storeGrillaMecanismoAlertaPantalla.getAt(0).get("dsMensaje") + "</center>");
  				_indexRegistroActual = 0;
  				formPanelPantalla.render(seccionDePantalla);
			}
			
			//	Funcionalidad para los botones del toolbar (si es que queremos desplegarlos)
			if (storeGrillaMecanismoAlertaPantalla.getTotalCount()>1){  
				botonesPantalla.first.on('click', movePagePantalla.createDelegate(botonesPantalla.first, [0]));
				botonesPantalla.prev.on('click', movePagePantalla.createDelegate(botonesPantalla.prev, [Math.max(0, botonesPantalla.cursor - botonesPantalla.pageSize)]));
				botonesPantalla.next.on('click', movePagePantalla.createDelegate(botonesPantalla.first, [botonesPantalla.cursor + botonesPantalla.pageSize]));
				var D = botonesPantalla.store.getTotalCount();
				var A = D % botonesPantalla.pageSize;
				var C = A ? (D - A) : D - botonesPantalla.pageSize;
				botonesPantalla.last.on('click', movePagePantalla.createDelegate(botonesPantalla.first, [C]));
				mensajesAlertaPantalla.getToolbar().setVisible(false);
			}
		}
	});
}