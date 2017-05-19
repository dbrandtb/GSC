<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
//////urls //////
var _p30_urlCargaMasivaClientes = '<s:url namespace="/emision"         action="cargaMasivaClientes"         />';
var _p30_urlCargarTipoCambioWS    = '<s:url namespace="/emision"         action="cargarTipoCambioWS"          />';
//////urls //////

//////variables //////
var _p30_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
debug('_p30_smap1:',_p30_smap1);
var _p28_smap1 =
{
    cdtipsit : _p30_smap1.cdtipsit
};
debug('_p28_smap1:',_p28_smap1);
var _p30_precioDolarDia          = null;
//////variables //////

//////dinamicos //////
var _p30_gridColsConf =
[
    <s:if test='%{getImap().get("gridCols")!=null}'>
        <s:property value="imap.gridCols" />
    </s:if>
];
var _p30_gridCols =
[
    {
        dataIndex     : 'nmsituac'
        ,width        : 50
        ,menuDisabled : true
    }
    ,{
        sortable      : false
        ,menuDisabled : true
        ,dataIndex    : 'personalizado'
        ,width        : 30
        ,renderer     : function(v)
        {
            var r='';
            if(v+'x'=='six')
            {
                r='<img src="${ctx}/resources/fam3icons/icons/tag_blue_edit.png" />';
            }
            return r;
        }
    }
];
for(var i=0;i<_p30_gridColsConf.length;i++)
{
    _p30_gridCols.push(_p30_gridColsConf[i]);
}
for(var i=0;i<_p30_gridCols.length;i++)
{
    if(!Ext.isEmpty(_p30_gridCols[i].editor)&&_p30_gridCols[i].editor.readOnly)
    {
        _p30_gridCols[i].editor='';
    }
}
var _p30_panel1ItemsConf =
[
    <s:if test='%{getImap().get("panel1Items")!=null}'>
        <s:property value="imap.panel1Items" />
    </s:if>
];

var _p30_gridTbarItems =
[
    {
        text     : 'Agregar'
        ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
        ,handler : function(){_p30_agregarAuto();}
    }
    ,'->'
];
//////dinamicos //////

Ext.onReady(function()
{	
    Ext.Ajax.timeout = 3*60*60*1000; // 1 hora
    Ext.override(Ext.form.Basic, { timeout: Ext.Ajax.timeout / 1000 });
    Ext.override(Ext.data.proxy.Server, { timeout: Ext.Ajax.timeout });
    Ext.override(Ext.data.Connection, { timeout: Ext.Ajax.timeout });
	
////// modelos //////
 Ext.define('_p30_modelo',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
        	  'estatus','cvegralseg','ajuridica','avialviajes','benefPref','cdagente','claveve','conductor','cp','ddmpp','ddmpt','descadaptaciones'
        	 ,'descriee','descripcion','desrec','drc','drt','dterss','ectGm','ectrc','fefin','feini','gestoria','gsasalto','maccidental','modelo','moneda'
        	 ,'motor','negocio',{name:'nmsituac',type:'int'},'objperdidos','paquete','placas','rcOcupantes','rceco','rcmaniobras','rcremolque','rcruzada','reduceGs','rfc','robop'
        	 ,'saadaptaciones','saeqesp','sagm','sagssigue','sarcfallecimiento','sarcluc','serie','tipCarga','tipUso','tipoSer','tipoValor','valorVeh'
        	 ,'yo'
        ]
    });

//////modelos //////

////// stores //////
  _p30_store = Ext.create('Ext.data.Store',
    {
        model : '_p30_modelo'
    });
////// stores //////
	
	////// componentes //////
	var _p30_panel1Items =
    [
        {
            xtype   : 'fieldset'
            ,itemId : 'fsPanel1'
            ,border : 0
            ,items  :
            [
                {
                    layout  :
                    {
                        type     : 'table'
                        ,columns : 2
                    }
                    ,border : 0
                    ,items  :
                    [
                        {
                            xtype        : 'numberfield'
                            ,fieldLabel  : 'FOLIO'
                            ,name        : 'nmpoliza'
                            ,style       : 'margin:5px;margin-left:15px;'
                            ,listeners   :
                            {
                                change : _p30_nmpolizaChange
                            }
                        }
                        ,{
                            xtype    : 'button'
                            ,itemId  : '_p30_botonCargar'
                            ,text    : 'BUSCAR'
                            ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                            ,handler : function(){_p30_cargarClic();}
                        }
                    ]
                }
            ]
        }
    ];
	
	for(var i=0;i<_p30_panel1ItemsConf.length;i++)
    {
        _p30_panel1ItemsConf[i].style='margin:5px;margin-left:15px;';
    }
	
    var _p30_formOcultoItems = [];
    <s:if test='%{getImap().get("panel5Items")!=null}'>
        var items = [<s:property value="imap.panel5Items" />];
        for(var i=0;i<items.length;i++)
        {
            _p30_formOcultoItems.push(items[i]);
        }
    </s:if>
    <s:if test='%{getImap().get("panel6Items")!=null}'>
        var items = [<s:property value="imap.panel6Items" />];
        for(var i=0;i<items.length;i++)
        {
            _p30_formOcultoItems.push(items[i]);
        }
    </s:if>
    debug('_p30_formOcultoItems:',_p30_formOcultoItems);
////// componentes //////
////// contenido //////
 var itemspri=
    [
    	 Ext.create('Ext.form.Panel',
	     {
	        title      : 'CARGA MASIVA CLIENTES'
	       ,titleAlign : 'center'
	     })
        ,Ext.create('Ext.form.Panel',
        {
            itemId      : '_p30_form'
            ,title      : 'DATOS GENERALES'
            ,formOculto : Ext.create('Ext.form.Panel',{ items : _p30_formOcultoItems })
            ,defaults   : { style : 'margin:5px;' }
            ,layout     :
            {
                type     : 'table'
                ,columns : 2
                ,tdAttrs : {valign:'top'}
            }
            ,items    : _p30_panel1Items
        })
        ,Ext.create('Ext.grid.Panel',
        {
            itemId      : '_p30_grid'
            ,title      : 'INCISOS'
            ,tbar       : {
                layout: 'anchor',
                defaults: { margin:'2 2 0 0' },
                items: _p30_gridTbarItems
            }
            ,bbar       :
            [
                {
                    xtype    : 'button'
                    ,hidden  : _p30_smap1.tipoflot+'x'!='Fx' && _p30_smap1.tipoflot+'x'!='Px'
                    ,text    : 'Limpiar'
                    ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
                    ,hidden  : true
                    ,handler : function()
                    {
                        _p30_store.removeAll();
                    }
                }
                ,'->'
                ,{
                     xtype  : 'form'
                    ,hidden : _p30_smap1.tipoflot+'x'!='Fx' && _p30_smap1.tipoflot+'x'!='Px'
                    ,layout : 'hbox'
                    	,items  :
                            [
                                {
                                    xtype         : 'filefield'
                                    ,buttonOnly   : true
                                    ,style        : 'margin:0px;'
                                    ,name         : 'excel'
                                    ,style        : 'background:#223772;'
                                    ,buttonConfig :
                                    {
                                        text   : 'Carga masiva...'
                                        ,icon  : '${ctx}/resources/fam3icons/icons/book_next.png'
                                    }
                                    ,listeners :
                                    {
                                        change : function(me)
                                        {   var descripcion ='';
                                            var msnIncInv='';
                                            var indexofPeriod = me.getValue().lastIndexOf("."),
                                            uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
                                            debug('uploadedExtension:',uploadedExtension);
                                            var valido=Ext.Array.contains(['xls','xlsx'], uploadedExtension);
                                            if(!valido)
                                            {
                                                mensajeWarning('Solo se permiten hojas de c&aacute;lculo');
                                                me.reset();
                                            }
                                            if(valido)
                                            {
                                                       Ext.Ajax.request(
                                                         {
                                                             url      : _p30_urlCargarTipoCambioWS
                                                             ,success : function(response)
                                                             {
                                                                 var json=Ext.decode(response.responseText);
                                                                 debug('### dolar:',json);
                                                                 _p30_precioDolarDia=json.smap1.dolar;
                                                            }
                                                             ,failure : function()
                                                             {
                                                                 errorComunicacion();
                                                             }
                                                         });
                                                
                                                var panelpri = _fieldById('_p30_panelpri');
                                                panelpri.setLoading(true);
                                                me.up('form').submit(
                                                {
                                                    url     : _p30_urlCargaMasivaClientes
                                                    ,params :
                                                    {
                                                        'smap1.cdramo'    : _p30_smap1.cdramo
                                                        ,'smap1.cdtipsit' : _p30_smap1.cdtipsit
                                                        ,'smap1.tipoflot' : _p30_smap1.tipoflot
                                                        ,'smap1.cambio'   : _p30_precioDolarDia
                                                        ,'smap1.negocio'  : 'cargaMasivaClientes'
                                                    }
                                                    ,success : function(form,action)
                                                    {
                                                        panelpri.setLoading(false);
                                                        var json = Ext.decode(action.response.responseText);
                                                        debug('### excel:',json);
                                                        if(json.exito)
                                                        {
                                                            var mrecords = [];
                                                            var msnIncInv = json.respuestaOculta;
                                                            
        	                                                    for(var i in json.slist1)
        	                                                    {
        	                                                        var record=new _p30_modelo(json.slist1[i]);
        	                                                        mrecords.push(record);
        	                                                        debug('record.data:',record.data);
        	                                                    }
        	                                                    _p30_store.removeAll();
        	                                                    _p30_numerarIncisos(mrecords);
                                                                _p30_store.add(mrecords);
                                                        }
                                                        else
                                                        {
                                                            mensajeError(json.respuesta);
                                                        }
                                                    }
                                                    ,failure : function()
                                                    {
                                                        panelpri.setLoading(false);
                                                        errorComunicacion();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                             ]
                         }
                    ]
            ,columns    : _p30_gridCols
            ,height     : 330
            ,store      : _p30_store
            ,selModel   :
            {
                selType        : 'checkboxmodel'
                ,allowDeselect : true
                ,mode          : 'SINGLE'
                ,listeners     :
                {
                    selectionchange : function(me,selected,eOpts)
                    {
                        if(selected.length==1)
                        {
                            _p30_selectedRecord=selected[0];
                            _p30_editarAuto();
                        }
                        else
                        {
                            for(var i in _p30_situaciones)
                            {
                                _fieldById('_p30_tatrisitParcialForm'+_p30_situaciones[i]).hide();
                            }
                        }
                    }
                }
            }
        })
    ];
    
	 itemspri.push(
	        Ext.create('Ext.panel.Panel',
	        {
	            itemId       : '_p30_botonera'
	            ,buttonAlign : 'center'
	            ,border      : 0
	            ,buttons     :
	            [
	                {
	                    itemId   : '_p30_cotizarButton'
	                    ,text    : 'Cotizar'
	                    ,icon    : '${ctx}/resources/fam3icons/icons/calculator.png'
// 	                    ,handler : function(){_p30_cotizar();}
	                }
	                ,{
	                    itemId   : '_p30_limpiarButton'
	                    ,text    : 'Limpiar'
	                    ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
// 	                    ,handler : function(){_p30_limpiar();}
	                }
	                ,{
	                    itemId   : '_p30_endosoButton'
	                    ,text    : 'Emitir'
	                    ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
// 	                    ,handler : function(){_p30_confirmarEndoso();}
	                }
	            ]
	        })
	    );
    
 Ext.create('Ext.panel.Panel',
		    {	
		        renderTo  : '_p64_divpri'
		        ,itemId   : '_p30_panelpri'
		        ,border   : 0
		        ,defaults : { style : 'margin:5px;' }
		        ,items    : itemspri
		    });
		    
//////contenido //////
////// custom //////

 //grid
 //grid

 //paneles tconvalsit
 
 //cliente nuevo

 //tipo situacion

 //tipo situacion
 
 //loaders panels

 //loaders panels

//codigo dinamico recuperado de la base de datos
////// custom //////

////// loaders //////
////// loaders //////
});
//////funciones //////
function _p30_nmpolizaChange(me)
{
    var sem = me.semaforo;
    if(Ext.isEmpty(sem)||sem==false)
    {
        me.sucio = true;
    }
    else
    {
        me.sucio = false;
    }
}

function _p30_numerarIncisos(arreglo)
{
    var i=1;
    if(Ext.isEmpty(arreglo))
    {
        _p30_store.each(function(record)
        {
            record.set('nmsituac',i++);
        });
    }
    else
    {
        for(var j in arreglo)
        {
            arreglo[j].set('nmsituac',i++);
        }
    }
}

function _p30_agregarAuto()
{
    debug('>_p30_agregarAuto');
    
    try{
    	if(_p30_smap1.turistas=='S' && _p30_store.count()>=3){
    		mensajeWarning('Solo puedes agregar 3 incisos');
    		return;
    	}
    	
    }catch(e){
    	debugError(e);
    }
    var valido=true;
    if(valido&&_p30_smap1.cdramo+'x'=='5x')
    {
        valido=!Ext.isEmpty(_fieldByLabel('NEGOCIO',_fieldById('_p30_form')).getValue());
        if(!valido)
        {
            mensajeWarning('Seleccione el negocio');
        }
    }
    
    if(valido)
    {
        var record=new _p30_modelo();
        _p30_store.add(record);
        _p30_numerarIncisos();
        _fieldById('_p30_grid').getSelectionModel().select(record);
    }
    
    debug('<_p30_agregarAuto');
}

function _p30_gridBotonEliminarClic(view,row,col,item,e,record)
{
    debug('>_p30_gridBotonEliminarClic:',record);
    _p30_store.remove(record);
    _p30_numerarIncisos();
    _p30_semaforoBorrar=true;
    debug('<_p30_gridBotonEliminarClic');
}	

function _p30_renderer(record,mapeo)
{
//         debug('>_p30_renderer',mapeo,record.data);
//         label='N/A';        
//         var valor  = record.get(name);
//         label=valor;
   		return '.!.';
}
//////funciones //////
</script>
<script type="text/javascript"
	src="${ctx}/js/proceso/emision/cotizacionAutoFlotillaScript.js?now=${now}"></script>
</head>
<body>
	<div id="_p64_divpri" style="height: 500px;"></div>
</body>
</html>
