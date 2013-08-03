//var helpMap = new Map();
function validarMensajesAlerta(){

var jsonGrillaMecanismoAlerta = new Ext.data.JsonReader(
{
    root:'MEstructuraList',
    totalProperty: 'totalCount',
    successProperty : '@success'
}, 
[
    {name: 'dsMensaje',  mapping:'dsMensaje',  type: 'string'}
] 
);


/* ********************************** STORES *************************** */ 

var storeGrillaMecanismoAlerta = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
	url: _OBTENER_MENSAJES_ALERTA
        }),
reader:jsonGrillaMecanismoAlerta
});

var mensajes = new Ext.form.HtmlEditor({
			name:'dsMensaje',
	        id:'dsMensajeId',
	        width:630,
	        heigth:880,
	        enableAlignments : false,
            enableColors : false,
            enableFont : false,
        	enableFontSize : false,
        	enableFormat : false,
        	enableLinks : false,
        	enableLists : false,
        	enableSourceEdit : false,
        	hideLabel: true,
        	labelSeparator: '',
			store: storeGrillaMecanismoAlerta,
			displayField: 'dsMensaje'
			
        });

var botones = new Ext.PagingToolbar({
				pageSize:1,
				store: storeGrillaMecanismoAlerta,
				displayInfo: true,
				displayMsg: 'Mostrando Aviso {0} de {2}',
				emptyMsg: 'No hay avisos para visualizar',
				beforePageText : "Aviso",
				firstText : "Primer Aviso",
				prevText : "Aviso Anterior",
				nextText : "Siguiente Aviso",
				overrideOnClick: true,
				lastText : "Ultimo Aviso"/*,
				listeners : {
				
    				onPagingBlur: function(boton){
    				//alert(boton);
    				botones.store.reader.jsonData.MEstructuraList[0].dsMensaje
    				//mensajes.setValue(storeGrillaMecanismoAlerta.getAt(botones.currentPage).get("dsMensaje"));
    				//alert(storeGrillaMecanismoAlerta.getAt(botones.currentPage).get("dsMensaje"));
    				} 
					
					/*click : function(boton){
    				//alert(boton);
    				mensajes.setValue(storeGrillaMecanismoAlerta.getAt(botones.currentPage).get("dsMensaje"));
    				//alert(storeGrillaMecanismoAlerta.getAt(botones.currentPage).get("dsMensaje"));
    				}*/
 				//}*/
				
		});

intervalo = setInterval(function fnNavega(){
//console.log(botones.store.reader.jsonData.MEstructuraList[0].dsMensaje);
//var _texto= storeGrillaMecanismoAlerta.getAt(0).get("dsMensaje");
//Ext.getCmp('dsMensajeId').setValue(_texto);
//alert (Ext.getCmp('dsMensajeId').getValue());
if(botones.field != undefined ){
//console.log(botones.field);
//console.log(botones.field.getValue());	
//console.log(botones.store.getTotalCount());	

if (botones.field.getValue() < botones.store.getTotalCount())
	{
		/*
		//botones.store.removeAll();
		//console.log(botones.store.data.items[0].json.dsMensaje);
		botones.onClick('next');
		//console.log(botones.store.reader.jsonData.MEstructuraList[0].dsMensaje);
		mensajes.setValue(botones.store.reader.jsonData.MEstructuraList[0].dsMensaje);
		//alert(1);
		//(botones.store.getAt(0).get("dsMensaje"))
		//console.log(botones.store.reader.jsonData.MEstructuraList[0].dsMensaje);
		*/
		var B = {}, A = botones.paramNames;
		B[A.start] = botones.cursor + botones.pageSize;
		B[A.limit] = botones.pageSize;
		botones.store.load({
			params : B,
			callback: function (r, options, success) {
				if (success) {
					mensajes.setValue("<center>" + botones.store.reader.jsonData.MEstructuraList[0].dsMensaje + "</center>");
				}
			}
		});

	}else{
		/*botones.onClick('first');
		mensajes.setValue(storeGrillaMecanismoAlerta.getAt(0).get("dsMensaje"));
		//alert(2);
		//var _texto= storeGrillaMecanismoAlerta.getAt(0).get("dsMensaje");
		//Ext.getCmp('dsMensajeId').setValue(_texto);
		//alert(3);
		*/
		var B = {}, A = botones.paramNames;
		B[A.start] = 0;
		B[A.limit] = botones.pageSize;
		botones.store.load({
			params : B,
			callback: function (r, options, success) {
				if (success) {
					mensajes.setValue("<center>" + storeGrillaMecanismoAlerta.getAt(0).get("dsMensaje") + "</center>");
				}
			}
		});
}
	//var _texto= storeGrillaMecanismoAlerta.getAt(0).get("dsMensaje");
	//Ext.getCmp('dsMensajeId').setValue(_texto);
	//console.log(botones.store.reader.jsonData.MEstructuraList[0].dsMensaje);

}//else{alert('No hay Datos')}
}
,12000);

function movePage (_index) {

		var B = {}, A = botones.paramNames;
		B[A.start] = _index;
		B[A.limit] = botones.pageSize;
		botones.store.load({
			params : B,
			callback: function (r, options, success) {
				if (success) {
					mensajes.setValue("<center>" + storeGrillaMecanismoAlerta.getAt(0).get("dsMensaje") + "</center>");
				}
			}
		});
}
var formPanel = new Ext.FormPanel( {
	id:'formPanelId',
    layout: 'table',
 	layoutConfig: {columns: 1},
    url : _OBTENER_MENSAJES_ALERTA,
   	labelWidth: 0,
    frame : true,
    bodyStyle : ('padding:0 5px 5px 0','background: white'),
    width : 650,
  	store: storeGrillaMecanismoAlerta,
  	items: [
       		{
    		layout: 'fit',
       		width: 636,
       		height: 214,
       		items: [
                   		mensajes
    	           	]
        	},
         	{
           	layout: 'form',
           	width: 636,
           	items: [
                  		botones            
                  	]
         	}
        	]           
});

//Windows donde se van a visualizar la pantalla
var _window = new Ext.Window({
 	width: 668,
 	height:300,        	
 	//title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('51', helpMap,'Avisos')+'</span>',
 	title: '<span style="color:black;font-size:12px;">'+'Avisos'+'</span>',
 	//minWidth: 300,
 	modal:true,
 	//minHeight: 100,
 	//layout: 'fit',
 	plain:true,
 	bodyStyle:'padding:5px;',
 	items: [formPanel],
 	listeners:{
    	'beforeclose' : function(){
    	clearInterval(intervalo);}
 	}
});

var _params = {
         cdUsuario: "",
		 cdRol: "",
  		 cdElemento:"",
  		 cdProceso: "",//esto es nulo
  		 fecha: "",
  		 cdRamo: ""//esto es nulo
 };

formPanel.store.load({
	callback:function(){
		if (storeGrillaMecanismoAlerta.reader.jsonData.totalCount>=1){  
  			
  			_window.show();
  			//console.log(storeGrillaMecanismoAlerta.getAt(0).get("dsMensaje"));
  			mensajes.setValue("<center>" + storeGrillaMecanismoAlerta.getAt(0).get("dsMensaje") + "</center>");

			botones.first.on('click', movePage.createDelegate(botones.first, [0]));
			botones.prev.on('click', function () {
				movePage (Math.max(0, botones.cursor - botones.pageSize));
			});
			//botones.prev.on('click', movePage.createDelegate(botones.prev, [Math.max(0, botones.cursor - botones.pageSize)]));
			//botones.next.on('click', movePage.createDelegate(botones.first, [botones.cursor + botones.pageSize]));
			botones.next.on('click', function () {
				movePage (botones.cursor + botones.pageSize);
			});
			var D = botones.store.getTotalCount();
			var A = D % botones.pageSize;
			var C = A ? (D - A) : D - botones.pageSize;
			//movePage (C);
			botones.last.on('click', movePage.createDelegate(botones.first, [C]));
			mensajes.getToolbar().setVisible(false)//;
			/*var test = botones.onClick.createInterceptor (function () {
				alert("");
				return false;
			}); */
			/*botones.first.on('click', function () {
				movePage (0);
			});
			botones.prev.on('click', function () {
				movePage (Math.max(0, botones.cursor - botones.pageSize));
			});
			botones.next.on('click', function () {
				movePage (botones.cursor + botones.pageSize);
			});
			botones.last.on('click', function (boton) {
				var D = botones.store.getTotalCount();
				var A = D % botones.pageSize;
				var C = A ? (D - A) : D - botones.pageSize;
				movePage (C);
			});*/
		}
	}
});  	


/*
function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
} */
}