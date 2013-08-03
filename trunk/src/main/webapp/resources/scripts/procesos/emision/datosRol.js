Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//Variable tipo hidden para evitar la excepcion de la pantalla
//si el pl no trae datos.
var sinDatos = new Ext.form.Hidden({
    id:'sinDatos',
    name:'SinDatos'
});

//Si el Pl no trae datos se mostrara un mensaje y se pintara un hidden.
if (ElementosExt==""){

	Ext.Msg.show({
	   title:'Datos Rol',
	   msg: 'No se encontraron datos.',
	   buttons: Ext.Msg.OK,
	   icon: Ext.MessageBox.INFO
	});
	
    ElementosExt = sinDatos;
}
// Form datosRol
    var datosRolForm = new Ext.form.FormPanel({    
    	el:'gridDatosRol',
        title: '<span style="color:black;font-size:14px;">Datos Rol</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',   
        frame:true,                         
        width: 400,
        height:500,
        autoHeight: false,
        autoScroll: true,
        items: [{
                layout:'form',
                border: false,
                items:[{
                    //title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
                    bodyStyle:'background: white',
                labelWidth: 300,             
                layout: 'form',
                frame:true,
                baseCls: '',
                buttonAlign: "center",
                        items: [{
                            layout:'column',
                            border:false,
                            columnWidth: 1,
                            items:[{
                                columnWidth:1,
                                layout: 'form',                     
                                border: false,
                                items:  ElementosExt 
                                },{
                                columnWidth:.1,
                                layout: 'form'
                                }]
                            }]
                        }]
                }],
        buttons:[{
            	text:'Regresar',
            	tooltip:'Regresar a la pantalla anterior',
            	handler:function(){
                			regresar();
                		}
                }]
        });

// Window datosRol
    var datosRolWindow = new Ext.Window({
        title: 'Datos Rol',
        width: 400,
        height:400,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: datosRolForm,
        buttons: [{
                	text: 'Regresar',
                	handler: function(){datosRolWindow.hide();}
                 }]
        });
        
//******************************************
//Regresa a la pantalla principal
//******************************************
	function regresarDatosRol() {
			var url = _ACTION_HOME + '?cdUnieco='+_cdUnieco+'&cdRamo='+_cdRamo+"&estado="+_estado+'&nmPoliza='+_nmPoliza+'&poliza='+_poliza+'&producto='+_producto+'&aseguradora='+_aseguradora;
	        window.location.href = url;
	}

        datosRolForm.render();

});