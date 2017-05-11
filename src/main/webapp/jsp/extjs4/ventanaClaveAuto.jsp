<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script>
////// urls //////
var _urlRecuperaClaveAutoFlujo = '<s:url namespace="/mesacontrol" action="recuperaClaveAutoFlujo"    />';
var _url_GuardaClaveAuto       = '<s:url namespace="/mesacontrol" action="guardaClaveAutoFlujo"      />';
////// urls //////

////// variables //////
var panDOcInputCdtipflu     = '<s:property value="smap1.cdtipflu"  />';
var panDOcInputCdflujomc    = '<s:property value="smap1.cdflujomc" />';
var panDOcInputTipoent      = '<s:property value="smap1.tipoent"   />';
var panDOcInputClaveent     = '<s:property value="smap1.claveent"  />';
var panDOcInputWebid        = '<s:property value="smap1.webid"     />';
var panDOcInputNtramite     = '<s:property value="smap1.ntramite"  />';
var panDOcInputStatus       = '<s:property value="smap1.status"    />';
var panDOcInputCdunieco     = '<s:property value="smap1.cdunieco"  />';
var panDOcInputCdramo       = '<s:property value="smap1.cdramo"    />';
var panDOcInputEstado       = '<s:property value="smap1.estado"    />';
var panDOcInputNmpoliza     = '<s:property value="smap1.nmpoliza"  />';
var panDOcInputNmsituac     = '<s:property value="smap1.nmsituac"  />';
var panDOcInputNmsuplem     = '<s:property value="smap1.nmsuplem"  />';
var panDOcInputCdusuari     = '<s:property value="smap1.cdusuari"  />';
var panDOcInputAux          = '<s:property value="smap1.aux"       />';

debug('panDOcInputCdtipflu : ', panDOcInputCdtipflu );
debug('panDOcInputCdflujomc: ', panDOcInputCdflujomc);
debug('panDOcInputTipoent  : ', panDOcInputTipoent  );
debug('panDOcInputClaveent : ', panDOcInputClaveent );
debug('panDOcInputWebid    : ', panDOcInputWebid    );
debug('panDOcInputNtramite : ', panDOcInputNtramite );
debug('panDOcInputStatus   : ', panDOcInputStatus   );
debug('panDOcInputCdunieco : ', panDOcInputCdunieco );
debug('panDOcInputCdramo   : ', panDOcInputCdramo   );
debug('panDOcInputEstado   : ', panDOcInputEstado   );
debug('panDOcInputNmpoliza : ', panDOcInputNmpoliza );
debug('panDOcInputNmsituac : ', panDOcInputNmsituac );
debug('panDOcInputNmsuplem : ', panDOcInputNmsuplem );
debug('panDOcInputCdusuari : ', panDOcInputCdusuari );

////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
////// componentes dinamicos //////

Ext.onReady(function()
{
	//Contingencia para aumentar timeot de consultas por error en produccion
	/*Ext.Ajax.timeout = 10*60*1000; //10 minutos
	Ext.override(Ext.form.Basic, { timeout: Ext.Ajax.timeout / 1000 });
	Ext.override(Ext.data.proxy.Server, { timeout: Ext.Ajax.timeout });
	Ext.override(Ext.data.Connection, { timeout: Ext.Ajax.timeout });*/
			
    ////// modelos //////
	Ext.define('ClaveAutoModel', {
                extend: 'Ext.data.Model',
                fields: [
                    /*{type:'string', name:'cdunieco'},
                    {type:'string', name:'cdramo'},
                    {type:'string', name:'estado'},
                    {type:'string', name:'nmpoliza'},*/
                    {type:'string', name:'NTRAMITE'},
                    {type:'string', name:'MARCA'},
                    {type:'string', name:'SUBMARCA'},
                    {type:'int', name:'MODELO'},
                    {type:'string', name:'VERSION'},
                    {type:'string', name:'COMENTARIOS'}
                ]
            });
    ////// modelos //////
    
    ////// stores //////
    var storeClaveAuto = new Ext.create('Ext.data.Store',
    {
        model     : 'ClaveAutoModel'
        ,autoLoad : false
        ,proxy    :
        {
            type    : 'ajax'
            ,reader :
            {
                type  : 'json'
                ,root : 'slist1'
            }
            ,url : _urlRecuperaClaveAutoFlujo
        }
    });
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
	    Ext.create('Ext.panel.Panel',
    {
        renderTo  : '_p999_divpri'
        ,itemId   : '_p99_panelpri'
       // ,border   : 0
        //,defaults : { style : 'margin:5px;' }
        ,items    :
		        [
		        Ext.create('Ext.grid.Panel', {
			    //title: 'Clave Auto AMIS',
			    itemId  :'gridClaveAuto',
			    plugins   :_GLOBAL_CDSISROL !='TECAUTOS'?
                
                    Ext.create('Ext.grid.plugin.RowEditing', 
                    {
                        clicksToEdit  : 2
                        ,errorSummary : true
                    }):null
                ,
                /*,selModel  :
                {
                    selType        : 'checkboxmodel'
                    ,allowDeselect : true
                    ,mode          : 'SINGLE'
                },*/
			    store: storeClaveAuto,
			    columns: [
			        /*{text:'cdunieco', dataIndex:'cdunieco', hidden: true},
					{text:'cdramo', dataIndex:'cdramo' , hidden: true},
					{text:'estado', dataIndex:'estado' , hidden: true},
					{text:'nmpoliza', dataIndex:'nmpoliza' , hidden: true},*/
					{text:'nmtramite', dataIndex:'NTRAMITE' , hidden: true},
					{text:'Marca', dataIndex:'MARCA',editor: {xtype: 'textfield',allowBlank: false}},
					{text:'SubMarca', dataIndex:'SUBMARCA',editor: {xtype: 'textfield',allowBlank: false}},
					{text:'Modelo', dataIndex:'MODELO',editor: {xtype: 'textfield',allowBlank: false}},
					{text:'Version', dataIndex:'VERSION',editor: {xtype: 'textfield',allowBlank: false}},
					{text:'Comentarios', dataIndex:'COMENTARIOS',editor: {xtype: 'textfield',allowBlank: false}}
			    ],
			    
			    height: 350,
			    width: 700,
			    bbar: [
			    	{
			    		text: 'Agregar',
			    		hidden : _GLOBAL_CDSISROL=='TECAUTOS',
						handler: function() {
							storeClaveAuto.add({'NTRAMITE':panDOcInputNtramite});
							}
			        },{
			        	text: 'ELiminar',
			        	hidden : _GLOBAL_CDSISROL=='TECAUTOS',
			        	handler: function(){
						      //.... keep the debug point here in browser developer tool and verify the value of grid in console. It is definitely not an instance of the grid u expected hence it wont have getView() method.
			        		  var grid= _fieldById('gridClaveAuto');
			        		  debug('grid: ',grid);
						      var selection = grid.getView().getSelectionModel().getSelection()[0];
						      debug('selection: ',selection);
						      storeClaveAuto.remove(selection);
                              //storeClaveAuto.sync();
												}
			        },{
			    		text: 'Guardar',
			    		hidden : _GLOBAL_CDSISROL=='TECAUTOS',
						handler: function() {
							var json=
						        {
						            smap1 :
						            {
						                ntramite  : panDOcInputNtramite
						            }
						            ,slist1 : []
						        };
						    storeClaveAuto.each(function(record)
						        {
						            debug(record.data);
						            
						           json.slist1.push(record.data);
						            
						        });
						        
						        debug('json:',json);
						        _p55_agregar(json);
							}
			        },{
			        	text: 'Aceptar',
			        	hidden : _GLOBAL_CDSISROL=='TECAUTOS',
						handler: function() {
							debug('_p30_botonOnCotizarClic args:', arguments);
						    var ck = 'Enviando datos de cotizaci\u00f3n';
						    try {
						        Ext.syncRequire(_GLOBAL_DIRECTORIO_DEFINES + 'VentanaTurnado');
						        new VentanaTurnado({
						            cdtipflu  :panDOcInputCdtipflu  
									,cdflujomc :panDOcInputCdflujomc
									,tipoent   :panDOcInputTipoent  
									,claveent  :panDOcInputClaveent 
									,webid     :panDOcInputWebid    
									,ntramite  :panDOcInputNtramite 
									,status    :panDOcInputStatus   
									,cdunieco  :panDOcInputCdunieco 
									,cdramo    :panDOcInputCdramo   
									,estado    :panDOcInputEstado   
									,nmpoliza  :panDOcInputNmpoliza 
									,nmsituac  :panDOcInputNmsituac 
									,nmsuplem  :panDOcInputNmsuplem 
									,cdusuari  :panDOcInputCdusuari 
									,aux       :panDOcInputAux
						            ,cdsisrol  : _GLOBAL_CDSISROL
						        }).mostrar();
						    } catch (e) {
						        manejaException(e, ck);
						    }
						}
			        
			        }
			    ]
			})
			]
    });
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    
     storeClaveAuto.load(
    {
        params :
        {
            'smap1.ntramite'      :panDOcInputNtramite 
            
        }
    });
    ////// loaders //////
});

////// funciones //////

function _p55_agregar(json){
	//
	debug('<<Entrando a _p55_agregar... ');
   debug('ZZ json: ',json)
	Ext.Ajax.request(
        {
            url     : _url_GuardaClaveAuto
            ,jsonData :json
            ,success : function(response)
            {
                var json=Ext.decode(response.responseText);
                debug('### nmsuplem:',json);
                if(json.success)
                {
                    mensajeCorrecto('\u00C9xito', 'Datos guard\u00F3s correctamente', Ext.Msg.OK);
                }
                else
                {
                    mensajeWarning(json.mensaje);
                }
            }
            ,failure : function(){ errorComunicacion(); }
        })
}

////// funciones //////
</script>
</head>
<body>
<div id="_p999_divpri" style="height:1000px;border:1px solid #999999;"></div>
</body>
</html>