<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// urls //////
////// urls //////

////// variables //////
var store;
var win;
var mesConUrlDocu               = '<s:url namespace="/documentos"  action="ventanaDocumentosPoliza"     />';
var mesConUrlComGrupo           = '<s:url namespace="/emision"     action="cotizacionGrupo"             />';
var mesConUrlComGrupo2          = '<s:url namespace="/emision"     action="cotizacionGrupo2"            />';
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
var itemsFormulario = [<s:property value="imap1.itemsFormulario" escapeHtml="false" />];
var itemsGrid = [<s:property value="imap1.itemsGrid" escapeHtml="false" />];
// var itemsInsert = [<s:property value="items.itemsInsert" escapeHtml="false" />];
var itemsGridModel = [<s:property value="imap1.itemsGridModel" escapeHtml="false" />];

itemsGrid.push({xtype : 'actioncolumn'
		        ,icon : '${icons}page_copy.png'
		        ,tooltip : 'clonacion total'
		        ,handler : function(itemsGrid, rowIndex)
                    {   
		        	_mask('Procesando...');
		        	  var values = store.getAt(rowIndex).getData();	
  		        	  debug("rec",values);		        		
		            	   Ext.Ajax.request({
                            url     : '<s:url namespace="/endosos" action="generarCopiaCompleta" />'
                            ,params :
                            {
                                'params.cdunieco'    : values.cdunieco
                                ,'params.cdramo'     : values.cdramo
                                ,'params.cdtipsit'   : values.cdtipsit
                                ,'params.estado' 	 : values.estado
                                ,'params.nmpoliza' 	 : values.nmpoliza
                                ,'params.nmsolici' 	 : values.nmsolici
                                ,'params.ntramite' 	 : values.ntramite
                                ,'params.status' 	 : values.status
                                ,'params.ferecepc' 	 : Ext.Date.format(values.ferecepc,'d/m/Y')
                                ,'params.fecstatus'  : Ext.Date.format(values.fecstatus,'d/m/Y')
                                ,'params.TIPOFLOT'   : values.tipoflot
                            }
                            ,success : function(response)
                            {
                                _unmask();
                                var json = Ext.decode(response.responseText);
                                debug("json",json);
                                if(json.success==true){
                                    mensajeCorrecto('Tramite',json.mensaje,function(){                                    		
                                    Ext.create('Ext.window.Window',
                                    	    {
                                    	        title        : 'Documentaci&oacute;n'
                                    	        ,modal       : true
                                    	        ,buttonAlign : 'center'
                                    	        ,width       : 600
                                    	        ,height      : 400
                                    	        ,autoScroll  : true
                                    	        ,loader      :
                                    	        {
                                    	            url       : mesConUrlDocu
                                    	            ,params   :
                                    	            {
                                    	                'smap1.nmpoliza'  : '0'
                                    	                ,'smap1.cdunieco' : json.params.cdunieco
                                    	                ,'smap1.cdramo'   : json.params.cdramo
                                    	                ,'smap1.estado'   : json.params.estado
                                    	                ,'smap1.nmsuplem' : '0'
                                    	                ,'smap1.ntramite' : json.params.ntramite
                                    	                ,'smap1.nmsolici' : '0'
                                    	                ,'smap1.tipomov'  : '0'
                                    	            }
                                    	            ,scripts  : true
                                    	            ,autoLoad : true
                                    	        }
                                    	        ,buttons : [
                                    	            {
                                    	            	text     : 'continuar'
                                    	            	,icon    : '${icons}accept.png'
                                    	            	,handler : function(){
                                    	            		if(json.params.redireccion == 'S'){
							                                	if(values.cdtipsit == 'MSC')
							                                	{
							                                        Ext.create('Ext.form.Panel').submit(
							                                        {
							                                            url             : mesConUrlComGrupo
							                                            ,standardSubmit : true
							                                            ,params         :
							                                            {
							                                                'smap1.cdunieco'  : json.params.cdunieco
							                                                ,'smap1.cdramo'   : json.params.cdramo
							                                                ,'smap1.cdtipsit' : values.cdtipsit
							                                                ,'smap1.estado'   : json.params.estado
							                                                ,'smap1.nmpoliza' : 0
							                                                ,'smap1.ntramite' : json.params.ntramite
							                                                ,'smap1.cdagente' : values.cdagente
							                                                ,'smap1.status'   : values.status
							                                                ,'smap1.sincenso' : 'N'
							                                            }
							                                        });
							                                    }
							                                    else
							                                    {
							                                        Ext.create('Ext.form.Panel').submit(
							                                        {
							                                            url             : mesConUrlComGrupo2
							                                			,standardSubmit : true
							                                			,params         :
							                                            {
							                                				'smap1.cdunieco'  : json.params.cdunieco
							                                				,'smap1.cdramo'   : json.params.cdramo
							                                				,'smap1.cdtipsit' : values.cdtipsit
							                                				,'smap1.estado'   : json.params.estado
							                                				,'smap1.nmpoliza' : 0
							                                				,'smap1.ntramite' : json.params.ntramite
							                                				,'smap1.cdagente' : values.cdagente
							                                				,'smap1.status'   : values.status
							                                				,'smap1.sincenso' : 'N'
							                                             }
							                                        });
							                                   }
                                    	            		}else{
                                    	            			this.up('window').close();
                                    	            		}
                                    	            	}
                                    	            }
                                    	        ]
                                    	    }).show();
									});
                                }
                                else
                                {
                                    mensajeError('Error al guardar',json.message);                                    
                                }
                            }
                            ,failure : function()
                            {
                                _unmask();
                                errorComunicacion(null,'Error de red al guardar');
                            }
                        });
                    }
			    });
			    
itemsGrid.push(
    {
        xtype : 'actioncolumn'
        ,icon : '${icons}page_white_copy.png'
        ,tooltip : 'clonacion de condiciones'
        ,handler : function(itemsGrid, rowIndex)
        {
            alert(2);
            var values = store.getAt(rowIndex).getData();
            debug("rec",values);
            centrarVentanaInterna(
                Ext.create('Ext.window.Window',
                {
                    title        : 'Clonaci&oacute;n de tr&acute;mites'
                    ,modal       : true
                    ,buttonAlign : 'center'
                    ,autoScroll  : true
                    ,items       : [
                        Ext.create('Ext.form.Panel',
                        {
                            title     : 'Consulta y copia de tramites'
                            ,layout   :
                            {
                                type     : 'table'
                                ,columns : 3
                            }
                            ,defaults :
                            {
                                style : 'margin:5px;'
                            }
                            ,items : [
                                {
                                    xtype     : 'fieldset'
                                    ,title    : '<span style="font:bold 14px Calibri;">CENSO</span>'
                                    ,defaults : { style : 'margin:5px;' }
                                    //,hidden : _p21_ntramite&&_p21_smap1.sincenso!='S' ? true : false
                                    ,items    :
                                    [
                                        {
                                            xtype        : 'fieldcontainer'
                                            ,fieldLabel  : 'Tipo de censo'
                                            ,defaultType : 'radiofield'
                                            ,defaults    : { style : 'margin : 5px;' }
                                            ,layout      : 'hbox'
                                            ,items       :
                                            [
                                                {
                                                    boxLabel    : 'Por asegurado'
                                                    ,name       : 'tipoCenso'
                                                    ,inputValue : 'solo'
                                                    ,checked    : true
                                                }
                                                ,{
                                                    boxLabel    : 'Agrupado por edad'
                                                    ,name       : 'tipoCenso'
                                                    ,inputValue : 'grupo'
                                                }
                                            ]
                                        }
                                        ,{
                                            xtype       : 'filefield'
                                            ,fieldLabel : 'Censo de asegurados'
                                            ,name       : 'censo'
                                            ,buttonText : 'Examinar...'
                                            ,allowBlank : false
                                            ,buttonOnly : false
                                            ,width      : 450
                                            ,cAccept    : ['xls','xlsx']
                                            ,msgTarget  : 'side'
                                            ,listeners  :
                                            {
                                                change : function(me)
                                                {
                                                    var indexofPeriod = me.getValue().lastIndexOf("."),
                                                    uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
                                                    if (!Ext.Array.contains(this.cAccept, uploadedExtension))
                                                    {
                                                        centrarVentanaInterna(Ext.MessageBox.show(
                                                        {
                                                            title   : 'Error de tipo de archivo',
                                                            msg     : 'Extensiones permitidas: ' + this.cAccept.join(),
                                                            buttons : Ext.Msg.OK,
                                                            icon    : Ext.Msg.WARNING
                                                        }));
                                                        me.reset();
                                                    }
                                                }
                                            }
                                        }
                                    ]
                                }
                            ]
                        })
                    ]
                }).show()
            );
        }
    }
);			    
////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// modelos //////
     Ext.define('ModeloConvenio',
    {
        extend  : 'Ext.data.Model'
        ,fields : itemsGridModel
      }); 
    ////// modelos //////
    
    ////// stores //////
    store = Ext.create('Ext.data.Store',
    {
        model : 'ModeloConvenio'
    }); 
    ////// stores //////
    
    ////// componentes //////

    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        title     : 'Panel principal'
        ,panelPri : 'S'
        ,renderTo : '_p100_divpri'
        ,defaults :
        {
            style : 'margin:5px;'
        }
        ,items    :
        [
		    Ext.create('Ext.form.Panel',
		    {
		        title     : 'Consulta y copia de tramites'
		        ,layout   :
		        {
		            type     : 'table'
		            ,columns : 3
		        }
		        ,defaults :
		        {
		            style : 'margin:5px;'
		        }
		        ,items : itemsFormulario
		        ,buttonAlign : 'center'
		        ,buttons :
		        [
		            {
                        text     : 'BUSCAR'
                        ,icon    : '${icons}find.png'
                        ,handler : function(me)
                        {
                            var values = me.up('form').getValues();
                            _mask('Buscando...');
                            Ext.Ajax.request(
                            {
                                url     : '<s:url namespace="/endosos" action="buscarTramites" />'
                                ,params :
                                {
                                    'smap1.pv_cdunieco_i'    : values.cdunieco
                                    ,'smap1.pv_cdramo_i'     : values.cdramo
                                    ,'smap1.pv_cdtipsit_i'   : values.cdtipsit
                                    ,'smap1.pv_estado_i' 	 : values.estado
                                    ,'smap1.pv_ntramite_i'   : values.nmtramite
                                    ,'smap1.pv_status_i' 	 : values.status
                                    ,'smap1.pv_fedesde_i' 	 : values.fecini
                                    ,'smap1.pv_fehasta_i' 	 : values.fecfin
                                    
                                }
                                ,success : function(response)
                                {
                                    _unmask();
                                    var json = Ext.decode(response.responseText);
                                    if(json.success==true)
                                    {
                                    	store.removeAll();
                                        store.add(json.slist1);
                                        //debug('json',json);
                                    }
                                    else
                                    {
                                        mensajeError(json.message);
                                    }
                                }
                                ,failure : function()
                                {
                                    _unmask();
                                    errorComunicacion(null,'error de red al guardar');
                                }
                            });
                        }
                    },
 		            {
		                text     : 'LIMPIAR'
		                ,icon    : '${icons}arrow_refresh.png'
		                ,handler : function()
		                {
		                	this.up('form').getForm().reset();
		                }
		            } 
		        ]
		    })
 		    ,Ext.create('Ext.grid.Panel',
		    {
		        title    : 'Tabla'
		        //,width   : 900
		        ,height  : 200
		        ,store   : store
		        ,columns : itemsGrid
		    }) 
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
 
});
////// funciones //////
/* getData: function(includeAssociated){
               var me     = this,
                   fields = me.fields.items,
                   fLen   = fields.length,
                   data   = {},
                   name, f;

               for (f = 0; f < fLen; f++) {
                   name = fields[f].name;
                   data[name] = me.get(name);
               }

               if (includeAssociated === true) {
                   Ext.apply(data, me.getAssociatedData());
               }
               return data;
           } */
//});
// };
////// funciones //////
</script>
</head>
<body>
<div id="_p100_divpri" style="height:600px;border:1px solid #CCCCCC"></div>
</body>
</html>