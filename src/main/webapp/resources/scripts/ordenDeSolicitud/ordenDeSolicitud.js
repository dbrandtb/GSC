Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";		
var today = new Date();
var mesCabecera =  today.getMonth() + 1;
mesCabecera = (mesCabecera<10)?'0'+mesCabecera:mesCabecera+'';
var todayCabecera = today.getDate()+'/'+mesCabecera+'/'+today.getFullYear();
var todayCabeceraMas1 = today.getDate()+'/'+mesCabecera+'/'+(today.getFullYear()+1);


//wORIGEN =1;
//wORIGEN =2;

function gup( name )
{
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( window.location.href );
  if( results == null )
    return "";
  else
    return results[1];
}
var paramwORIGEN = gup('wORIGEN'); 


var codWorigen = new Ext.form.Hidden({
    	disabled:false,
        name:'codWorigen',
        value: paramwORIGEN//wORIGEN 
    });

var cdMatriz = new Ext.form.Hidden({
	id: 'cdmatrizHiddenId',
	name:'cdMatriz'
});
  
var cduniecoDelCombo = new Ext.form.Hidden({
	id: 'cduniecoHiddenId',
	name:'cdunieco'
});

var cdramoDelCombo = new Ext.form.Hidden({
	id: 'cdramoHiddenId',
	name:'cdramo'
});

var proceso = new Ext.form.Hidden({
	id: 'cdprocesoHiddenId',
	name:'cdproceso'
});

var comboTareas = new Ext.form.ComboBox(
{
    tpl: '<tpl for="."><div ext:qtip="{cdformatoorden}. {cdmatriz}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
    id:'acCmbTareaId',
    fieldLabel: getLabelFromMap('acCmbTareaId',helpMap,'Aseguradora'),
    tooltip:getToolTipFromMap('acCmbTareaId',helpMap,'Aseguradora'),
	hasHelpIcon:getHelpIconFromMap('acCmbTareaId',helpMap),
    Ayuda: getHelpTextFromMap('acCmbTareaId',helpMap),
    
    store: storeTareas,
    displayField:'descripcion',
    valueField:'cdformatoorden',
    hiddenName: 'cdformatoorden',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    width: 210,
    allowBlank: false,
    emptyText:'Seleccione Aseguradora...',
    selectOnFocus:true,
    forceSelection:true,
    onSelect: function(record){
       this.setValue(record.get("cdformatoorden"));
       this.collapse();

       Ext.getCmp("cdmatrizHiddenId").setValue(record.get("cdmatriz"));
	   Ext.getCmp("cdprocesoHiddenId").setValue(record.get("cdproceso"));
	   Ext.getCmp("cduniecoHiddenId").setValue(record.get("cdunieco"));
	   Ext.getCmp("cdramoHiddenId").setValue(record.get("cdramo"));
       storePlanesProducto.reload({
				params:{cdRamo: (Ext.getCmp('cdramoHiddenId')!=undefined)?Ext.getCmp('cdramoHiddenId').getValue():""}
		});
		
	   window.frames["atributosVariables"].location.href = _ACTION_OBTENER_ATRIBUTOS_VARIABLES+"?cdformatoorden="+record.get("cdformatoorden")+"&cdseccion="+"&limit=1000";
       
	   /*if(record.get("cdformatoorden") == "11")
       {
           
            Ext.getCmp("ordSolTablaId1").show();
            Ext.getCmp("ordSolTablaId2").hide();
          	Ext.getCmp('11_101_2').setValue(record.get('dsramo'));
			Ext.getCmp('11_101_7').setValue(record.get('dsunieco'));
   			storePlanesProducto.reload({
								params:{cdRamo: (Ext.getCmp('cdramoHiddenId')!=undefined)?Ext.getCmp('cdramoHiddenId').getValue():""}
						});
       }else {
            if(record.get("cdformatoorden")=="10") {
                
                Ext.getCmp("ordSolTablaId1").hide();
                Ext.getCmp("ordSolTablaId2").show();
  			    Ext.getCmp('10_100_2').setValue(record.get('dsramo'));
				Ext.getCmp('10_100_7').setValue(record.get('dsunieco'));
  			    storePlanesProducto.reload({
							params:{cdRamo: (Ext.getCmp('cdramoHiddenId')!=undefined)?Ext.getCmp('cdramoHiddenId').getValue():""}
					}); 
       		}else{
                Ext.getCmp("ordSolTablaId1").hide();
                Ext.getCmp("ordSolTablaId2").hide();      		
       		}
      }*/
	}
}
);

/*
var ordSolFechaIni = new Ext.form.DateField({
        fieldLabel: getLabelFromMap('dtFldOrdSolFechaOrdSol',helpMap,'Fecha'),
        tooltip:getToolTipFromMap('dtFldOrdSolFechaOrdSol',helpMap,'Fecha De Solicitud'),
        id: 'ordSolFechaIniId', 
        name: 'ordSolFechaIni',
		format:'d/m/Y',
        disabled:true,
        width: 95, 
        value:today 
    });*/

var ordSolFecha = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('ordSolFechaId1',helpMap,'Fecha'),
        tooltip:getToolTipFromMap('ordSolFechaId1',helpMap,'Fecha De Solicitud'),
	    hasHelpIcon:getHelpIconFromMap('ordSolFechaId1',helpMap),
        Ayuda: getHelpTextFromMap('ordSolFechaId1',helpMap),
        id: 'ordSolFechaId1', 
        name: 'ordSolFecha',
		//format:'d/m/Y',
        disabled:true,
        width: 95, 
        value:todayCabecera 
    });
    
var ordSolNumero = new Ext.form.NumberField({
        fieldLabel: getLabelFromMap('ordSolNumeroId',helpMap,'N&uacute;mero'),
        tooltip:getToolTipFromMap('ordSolNumeroId',helpMap,'N&uacute;mero De Solicitud'),
	    hasHelpIcon:getHelpIconFromMap('ordSolNumeroId',helpMap),
        Ayuda: getHelpTextFromMap('ordSolNumeroId',helpMap),
        id: 'ordSolNumeroId', 
        name: 'ordSolNumero',
        disabled:true,
        width: 85 
});

var formEstOrdSlct = new Ext.FormPanel({			
        renderTo:'encabezadoEstOrdSlct',
        id: 'formEstOrdSlctId',	        
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formEstOrdSlctId', helpMap,'Orden de Solicitud')+'</span>',        
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        width: 620,
        autoHeight:true,
        layout: 'table',
        layoutConfig: { columns: 3, columnWidth: .33},
        items:[
        		{
       		    //html: '<span class="x-form-item" style="font-weight:bold">Orden de Solicitud</span>',
        		colspan:3,
                width: 600                                                       
        		},
        		{            		
       			layout: 'form',
       		    width: 300,     
       		    labelWidth: 75,  
       		    height:36,    			            		
       			items: [
       					codWorigen,
                        comboTareas
       				]
        		},
        		{
        		layout: 'form',
        		width: 150,
        		labelWidth: 40,
        		height:36,
        		items: ordSolFecha //ordSolFechaIni
        		},
                {
                layout: 'form',
                width: 170,
                labelWidth: 50,
                height:36,
                items: ordSolNumero
                },
                //ESTO DEBE MOSTRARSE SI SE SELECCIONA UN CODIGO DE FORMATO DE ORDEN IGUAL A 10 EN EL COMBO TAREAS $$$$$$$$$$$$$$$$$$$$$
        		{
                id:'ordSolTablaId1',
        		layout:'form',
                width: 600,                                        
        		colspan: 3
        		//html: '<span class="x-form-item" style="font-weight:bold">Dinamica 01</span>',
                //items:ordSolTabla1
          		},
        		{
                id:'ordSolTablaId2',
                layout:'form',
                width: 600,                                        
                colspan: 3
           		//html: '<span class="x-form-item" style="font-weight:bold">Dinamica 02</span>',
                //items:ordSolTabla2
        		}
        	]

});   


var botonesGrdrVrblOrdSlct = new Ext.Panel({
  layout:'form',
  renderTo: 'botonesVrblOrdSlct', 
  bodyStyle:'background: white',
  width: 600,
  height: 50, 
  border: false,  
  items:[ 
          {
        	buttonAlign: 'center',
   			labelAlign:'right',
   			border: false,
   			buttons : [ 
   					{
                        id:'btnEnvCotId',
                        text : getLabelFromMap('btnEnvCotId',helpMap,'Enviar a Cotizar'), 
                        tooltip: getToolTipFromMap('btnEnvCotId',helpMap,'Enviar a Cotizar'),
                        //CDPROCESO=10
                        handler : function() {
                               	if(Ext.getCmp('acCmbTareaId').getValue()){
       					 				guardarDatos(Ext.getCmp("acCmbTareaId").getValue());
       					 		}
       					 		else{
       					 			//Ext.MessageBox.alert('Aviso','Debe seleccionar una tarea');
									Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400075', helpMap,'Debe seleccionar una tarea'));
       					 		}
                        
                        }
                    }, 
                    {
                        id:'btnCmprId',
                        text : getLabelFromMap('btnCmprId',helpMap,'Comprar'), 
                        tooltip: getToolTipFromMap('btnCmprId',helpMap,'Comprar'),
                        //CDPROCESO=11
                        handler : function() {
                        		if(Ext.getCmp('acCmbTareaId').getValue()){
       					 				guardarDatos(Ext.getCmp("acCmbTareaId").getValue());
       					 		}
       					 		else{
       					 			//Ext.MessageBox.alert('Aviso','Debe seleccionar una tarea');
									Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400075', helpMap,'Debe seleccionar una tarea'));
       					 		}
                            }
                    },
                    {
                    	"buttonType" : "reset",
						"handler" : function() {
							window.frames["atributosVariables"].location.href = _ACTION_OBTENER_ATRIBUTOS_VARIABLES+"?cdformatoorden="+Ext.getCmp("acCmbTareaId").getValue()+"&cdseccion="+"&limit=1000";
						},
						"id" : "btnNvCtzcn",
						"text" : getLabelFromMap('btnNvCtzcn',helpMap,'Nueva Solicitud'),
						"tooltip" : getToolTipFromMap('btnNvCtzcn',helpMap,'Nueva Solicitud'),
						"type" : "reset",
						"xtype" : "button"
                    },
                    {
                       id:'btnIprmrId',
                       text : getLabelFromMap('btnIprmrId',helpMap,'Imprimir'), 
                       tooltip: getToolTipFromMap('btnIprmrId',helpMap,'Imprimir'),
                       handler : function() {
                         }
                    },
                    {
                       id:'btnGdrId',
                       text : getLabelFromMap('btnGdrId',helpMap,'Guardar'), 
                       tooltip: getToolTipFromMap('btnGdrId',helpMap,'Guardar'),
                       handler : function() {
                       			if(Ext.getCmp('acCmbTareaId').getValue()){
       					 				guardarDatos(Ext.getCmp("acCmbTareaId").getValue());
       					 		}
       					 		else{
       					 			//Ext.MessageBox.alert('Aviso','Debe seleccionar una tarea');
									Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400075', helpMap,'Debe seleccionar una tarea'));
       					 		}
                        }
                    },
                    {
                       text : getLabelFromMap('frmtOrdSlctdButtonRegresar',helpMap,'Regresar'), 
                       tooltip: getToolTipFromMap('frmtOrdSlctdButtonRegresar',helpMap,'Regresar')/*,
                       handler : function() {
							window.location=_ACTION_VOLVER_CAPTURA_COTIZACION;
                        }*/
                    }
            ]
              }
        ]
});
	
	//if (wORIGEN == 1) 
	if(paramwORIGEN == 1)
	{	
		formEstOrdSlct.render();
		Ext.getCmp("ordSolTablaId1").hide();
   		Ext.getCmp("ordSolTablaId2").hide();
		Ext.getCmp('btnGdrId').setVisible(false);		
	}else{
		//if (wORIGEN == 2)
			if(paramwORIGEN == 2)
				{
			formEstOrdSlct.render();
			Ext.getCmp("ordSolTablaId1").hide();
	   		Ext.getCmp("ordSolTablaId2").hide();
			Ext.getCmp('btnEnvCotId').setVisible(false);
		    Ext.getCmp('btnCmprId').setVisible(false);
		    Ext.getCmp('btnNvCtzcn').setVisible(false);
		    Ext.getCmp('btnIprmrId').setVisible(false);		
		}
	}
	
	storeTareas.load({
					params:{cdProceso: CDPROCESO,
							cdElemento: CDELEMENTO,
							cdRamo: CDRAMO
							}
					/*,callback:function(){
							storePrioridades.load();
					}*/
				});

	
	//Ext.getCmp('11_101_4').setValue(todayCabecera);

	//Ext.getCmp('11_101_5').setValue(todayCabeceraMas1);
	

	//Ext.getCmp('10_100_4').setValue(todayCabecera);

	//Ext.getCmp('10_100_5').setValue(todayCabeceraMas1);
	
	var fecha = new Date();
	var mes = fecha.getMonth() + 1;
	mes = (mes<10)?'0'+mes:mes+'';
	var minutos = fecha.getMinutes();	
	minutos = (minutos<10)?'0'+minutos:minutos+'';
	var fechaHoraMinuto = fecha.getDate()+'/'+mes+'/'+fecha.getFullYear()+'  '+fecha.getHours()+':'+minutos;
	//Ext.getCmp('11_101_6').setValue(fechaHoraMinuto);
	//Ext.getCmp('10_100_6').setValue(fechaHoraMinuto);
	
	
	function guardarDatos(codigo){
		
		var  _params = "";
		
		//Datos del encabezado		
		 _params += "&MCasoListVO[0].cdMatriz="+Ext.getCmp("cdmatrizHiddenId").getValue();
		 //La variable CDUSUARIO es temporal
		 _params += "&MCasoListVO[0].cdUsuario="+CDUSUARIO;
		 _params += "&MCasoListVO[0].cdUnieco="+Ext.getCmp("cduniecoHiddenId").getValue();
		 _params += "&MCasoListVO[0].cdRamo="+Ext.getCmp("cdramoHiddenId").getValue();
		 _params += "&MCasoListVO[0].estado=";
		 _params += "&MCasoListVO[0].nmsituac=";
		 _params += "&MCasoListVO[0].nmsituaext=";
		 _params += "&MCasoListVO[0].nmsbsitext=";
		 _params += "&MCasoListVO[0].nmpoliza=";
		 _params += "&MCasoListVO[0].nmpoliex=";
		 _params += "&MCasoListVO[0].cdPerson="+CDPERSON;
		 _params += "&MCasoListVO[0].dsmetcontac=";
		 _params += "&MCasoListVO[0].indPoliza=";
		 _params += "&MCasoListVO[0].cdPrioridad=";
		 _params += "&MCasoListVO[0].cdProceso="+Ext.getCmp("cdprocesoHiddenId").getValue();
		 _params += "&MCasoListVO[0].dsObservacion=";


	  
	  _params += "&strUsuariosSeg=";
	  
	  //Atributos variables
	  
	  _params = obtieneStringDatosVariables(_params, codigo);
	  
		startMask(formEstOrdSlct.id,"Guardando datos...");
		execConnection(_ACTION_GUARDAR_NUEVO_CASO, _params, cbkGuardarNuevoCaso, 60000);		
	}
	function obtieneStringDatosVariables(_params, codigo){
		var i = 0;
		var _form = window.frames["atributosVariables"].Ext.getCmp('dinamicFormPanelId');
		Ext.each(_form.getForm().items.items, function(campito){
			 _params += "&listaEmisionVO["+i+"].cdformatoorden="+codigo;
			 _params += "&listaEmisionVO["+i+"].cdSeccion="+campito.cdSeccion;
			 _params += "&listaEmisionVO["+i+"].cdAtribu="+campito.cdAtribu;
			 _params += "&listaEmisionVO["+i+"].otValor="+ ((campito.getValue() != undefined && campito.getValue() != null)?campito.getValue():"");
			i++;
		});
		return _params;
	}
	
	
	
	function cbkGuardarNuevoCaso (_success, _message, _response) {
		endMask();
		if (!_success) {
			Ext.Msg.alert('Error', _message);
		}else {
			Ext.Msg.alert('Aviso', _message)
			Ext.getCmp('ordSolNumeroId').setValue(Ext.util.JSON.decode(_response).cdordentrabajo);
		}
	}

});