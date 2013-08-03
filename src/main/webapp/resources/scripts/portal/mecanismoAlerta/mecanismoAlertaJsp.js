
var helpMap = new Map();

Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

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
		//waitTitle: getLabelFromMap('400017',helpMap,'Espere ...')
        }),
reader:jsonGrillaMecanismoAlerta
});

    var cm = new Ext.grid.ColumnModel([
        
        {
        id:'cmDsMensajeAlertaId',
        dataIndex: 'dsMensaje',
        width: 650,
        editor:new Ext.form.HtmlEditor({
	        id:'dsMensaje',
	        width:350,
	        heigth:350,
	        enableAlignments : false,
            enableColors : false,
            enableFont : false,
        	enableFontSize : false,
        	enableFormat : false,
        	enableLinks : false,
        	enableLists : false,
        	enableSourceEdit : false
        })
        }]);
    
var grid2;

function createGrid(){
	grid2= new Ext.grid.GridPanel({
		
        id: 'grilla',
          title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Avisos</span>',
        store:storeGrillaMecanismoAlerta,
        border:true,
		cm: cm,
        width:670,
    	frame:true,
		height:230,
		renderTo: 'gridMensajes',
		bbar: new Ext.PagingToolbar({
				pageSize:1,
				store: storeGrillaMecanismoAlerta,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando Aviso {0} de {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay avisos para visualizar'),
				beforePageText : "Aviso",
				firstText : "Primer Aviso",
				prevText : "Aviso Anterior",
				nextText : "Siguiente Aviso",
				lastText : "Ultimo Aviso"
		})
		});

}


createGrid();

var intervalo = setInterval(function fnNavega(){


if (grid2.getBottomToolbar().field.getValue()<grid2.getBottomToolbar().store.getTotalCount())
	{
			grid2.getBottomToolbar().onClick('next');
	}else{
			grid2.getBottomToolbar().onClick('first');
	}
},12000);

var formPanel = new Ext.FormPanel( {
			id:'formPanelId',
            labelWidth : 100,
            url : _OBTENER_MENSAJES_ALERTA,
            frame : true,
            bodyStyle : ('padding:0 5px 5px 0','background: white'),
            labelAlign: 'right',
            width : 650,
            heigth:350,
            waitMsgTarget : true,            
            defaultType : 'textfield',
            items : [grid2]
});

	

  		
    	
    	var _params = {
    		
		            cdUsuario: "",
       				cdRol: "",
       				cdElemento:"",
       				cdProceso: "",//esto es nulo
       				fecha: "",
       				cdRamo: ""//esto es nulo
       			  };
       		//{params:_params}	  
       	  	
    	grid2.store.load({callback:function(){
    		
       			    if (storeGrillaMecanismoAlerta.reader.jsonData.totalCount>1){  
       			    
	   		         }}});
       			  
       			 
  formPanel.render(); 

    	
function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	}
} 
});
