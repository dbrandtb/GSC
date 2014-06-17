<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var endnomStoreAseg;

var endnomUrlGuardar       = '<s:url namespace="/endosos" action="guardarEndosoNombres" />';
var endnomUrlGuardarSimple = '<s:url namespace="/endosos" action="guardarEndosoNombresSimple" />';
var endnomUrlDoc           = '<s:url namespace="/documentos" action="ventanaDocumentosPolizaClon" />';

var endnomInput         = [];
endnomInput['cdunieco'] = '<s:property value="smap1.cdunieco" />';
endnomInput['cdramo']   = '<s:property value="smap1.cdramo" />';
endnomInput['cdtipsit'] = '<s:property value="smap1.cdtipsit" />';
endnomInput['estado']   = '<s:property value="smap1.estado" />';
endnomInput['nmpoliza'] = '<s:property value="smap1.nmpoliza" />';
endnomInput['ntramite'] = '<s:property value="smap1.ntramite" />';
endnomInput['simple']   = <s:property value="endosoSimple" />;
debug('endnomInput',endnomInput);
/*///////////////////*/
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
/*///////////////////*/

/*///////////////////*/
////// funciones //////
///////////////////////

Ext.onReady(function(){

	/////////////////////
	////// modelos //////
	/*/////////////////*/
    Ext.define('EndNomAsegurado',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            "nmsituac"
            ,"cdrol"
            ,{
                name        : 'fenacimi'
                ,type       : 'date'
                ,dateFormat : 'd/m/Y'
            }
            ,"sexo"
            ,"cdperson"
            ,"nombre"
            ,"segundo_nombre"
            ,"Apellido_Paterno"
            ,"Apellido_Materno"
            ,"cdrfc"
            ,"Parentesco"
            ,"tpersona"
            ,"nacional"
            ,"swexiper"
            ,{
                name  : 'activo'
                ,type : 'boolean'
            }
            ,'nombrecompleto'
            ,'CDUNIECO'
            ,'CDRAMO'
            ,'ESTADO'
            ,'NMPOLIZA'
            ,'NMPOLIEX'
            ,'NSUPLOGI'
            ,'CDTIPSIT'
        ]
    });
	/*/////////////////*/
	////// modelos //////
	/////////////////////
	
	////////////////////
	////// stores //////
	/*////////////////*/
	endnomStoreAseg = Ext.create('Ext.data.Store',
    {
        pageSize  : 10
        ,autoLoad : true
        ,model    : 'EndNomAsegurado'
        ,proxy    :
        {
            enablePaging  : true
            ,reader       : 'json'
            ,type         : 'memory'
            ,data         :
            [
				<s:set name="contador" value="0" />
				<s:iterator value="slist1">
				<s:if test="#contador>0">
				,
				</s:if>
				{
				    cdperson          : '<s:property value='%{getSlist1().get(#contador).get("cdperson")}' />'
				    ,nombre           : '<s:property value='%{getSlist1().get(#contador).get("nombre")}' />'
				    ,segundo_nombre   : '<s:property value='%{getSlist1().get(#contador).get("segundo_nombre")}' />'
				    ,Apellido_Paterno : '<s:property value='%{getSlist1().get(#contador).get("Apellido_Paterno")}' />'
				    ,Apellido_Materno : '<s:property value='%{getSlist1().get(#contador).get("Apellido_Materno")}' />'
				    ,cdrfc            : '<s:property value='%{getSlist1().get(#contador).get("cdrfc")}' />'
				    ,CDUNIECO         : '<s:property value='%{getSlist1().get(#contador).get("CDUNIECO")}' />'
				    ,CDRAMO           : '<s:property value='%{getSlist1().get(#contador).get("CDRAMO")}' />'
				    ,CDTIPSIT         : '<s:property value='%{getSlist1().get(#contador).get("CDTIPSIT")}' />'
				    ,ESTADO           : '<s:property value='%{getSlist1().get(#contador).get("ESTADO")}' />'
				    ,NMPOLIZA         : '<s:property value='%{getSlist1().get(#contador).get("NMPOLIZA")}' />'
				}
				<s:set name="contador" value="#contador+1" />
				</s:iterator>
            ]
        }
	    ,listeners        :
	    {
	    	load : function ( grid, records, successful, eOpts )
	        {
	    		debug('loaded');
   			    var indices=[];
   			    var contador=0;
	    		endnomStoreAseg.each(function(record1)
   			    {
   			        var cdperson=record1.get('cdperson');
   			        debug('verifi',cdperson);
   			        var repetidos=0;
   			        endnomStoreAseg.each(function(record2)
   			        {
   			            if(cdperson==record2.get('cdperson'))
   			            {
   			                repetidos=repetidos+1;
   			            }
   			        });
   			        if(repetidos>1)
   			        {
   			        	debug('quitar');
   			            record1.set('cdperson','@quitar');
   			            indices.push(contador);
   			        }
   			        else
   			        {
   			        	debug('mantener');
   			        }
   			        contador=contador+1;
   			    });
	    		debug(indices);
	    		for(contador=indices.length-1;contador>=0;contador--)
	    		{
	    			debug('quitando indice','-->'+indices[contador]);
	    			endnomStoreAseg.removeAt(indices[contador]);
	    		}
	        }
	    }
    });
    debug(endnomStoreAseg);
	/*////////////////*/
	////// stores //////
	////////////////////
	
	/////////////////////////
	////// componentes //////
	/*/////////////////////*/
	/*/////////////////////*/
	////// componentes //////
	/////////////////////////
	
	///////////////////////
	////// contenido //////
	/*///////////////////*/
	Ext.create('Ext.panel.Panel',
	{
		border    : 0
		,renderTo : 'endnomDivPri'
		,defaults :
		{
		    style : 'margin : 5px;'
		}
	    ,items    :
	    [
	        
            Ext.create('Ext.grid.Panel',
		    {
		        title     : 'Asegurados seleccionados'
		        ,store    : endnomStoreAseg
		        ,height   : 200
		        ,selType  : 'cellmodel'
		        ,plugins  :
		        [
		            Ext.create('Ext.grid.plugin.CellEditing',
		            {
		                clicksToEdit: 1
		            })
		        ]
		        ,columns  :
		        [
		            {
		                header        : 'Clave'
		                ,dataIndex    : 'cdperson'
		                ,flex         : 1
		                ,menuDisabled : true
		            }
		            ,{
		                header     : 'Nombre'
		                ,dataIndex : 'nombre'
		                ,flex      : 1
		                ,editor    :
		                {
		                    xtype : 'textfield'
		                }
		            }
		            ,{
		                header     : 'Segundo nombre'
		                ,dataIndex : 'segundo_nombre'
		                ,flex      : 1
		                ,editor    :
		                {
		                    xtype : 'textfield'
		                }
		            }
		            ,{
		                header     : 'Apellido paterno'
		                ,dataIndex : 'Apellido_Paterno'
		                ,flex      : 1
		                ,editor    :
		                {
		                    xtype : 'textfield'
		                }
		            }
		            ,{
		                header     : 'Apellido materno'
		                ,dataIndex : 'Apellido_Materno'
		                ,flex      : 1
		                ,editor    :
		                {
		                    xtype : 'textfield'
		                }
		            }
		            ,{
		                header     : 'RFC'
		                ,dataIndex : 'cdrfc'
		                ,flex      : 1
		                ,editor    :
		                {
		                    xtype : 'textfield'
		                }
		            }
		        ]
		    })
		    ,Ext.create('Ext.form.Panel',
            {
                title        : 'Informaci&oacute;n del endoso'
                ,buttonAlign : 'center'
                ,layout      :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,defaults    :
                {
                    style : 'margin : 5px;'
                }
                ,items       :
                [
                    {
                        xtype       : 'datefield'
                        ,fieldLabel : 'Fecha de inicio'
                        ,format     : 'd/m/Y'
                        ,value      : new Date()
                        ,allowBlank : false
                        ,name       : 'pv_fecha_i'
                        ,readOnly   : endnomInput['simple']
                    }
                ]
                ,buttons     :
                [
                    {
                    	text     : 'Generar endoso'
                    	,icon    : '${ctx}/resources/fam3icons/icons/key.png' 
                    	,handler : function()
                    	{
                    		debug('generar endoso');
                    		var form=this.up().up();
                    		if(form.isValid())
                    		{
                    			debug(endnomStoreAseg.getAt(0));
                    			form.setLoading(true);
                    			var json={};
                    		    json['omap1']=form.getValues();
                    		    json['omap1']['pv_cdunieco_i'] = endnomInput['cdunieco'];
                    		    json['omap1']['pv_cdramo_i']   = endnomInput['cdramo'];
                    		    json['omap1']['pv_estado_i']   = endnomInput['estado'];
                    		    json['omap1']['pv_nmpoliza_i'] = endnomInput['nmpoliza'];
                    			var slist1=[];
                    			endnomStoreAseg.each(function(record)
                    			{
                    				slist1.push(
                    				{
	                    				cdperson : record.get('cdperson')
	                    			    ,nombre  : record.get('nombre')
	                    			    ,nombre2 : record.get('segundo_nombre')
	                    			    ,apat    : record.get('Apellido_Paterno')
	                    			    ,amat    : record.get('Apellido_Materno')
	                    			    ,rfc     : record.get('cdrfc')
                    				});
                    			});
                    			json['slist1']=slist1;
                    			debug(json);
                    			Ext.Ajax.request(
                    			{
                    				url       : endnomInput['simple'] ? endnomUrlGuardarSimple : endnomUrlGuardar
                    				,jsonData : json
                    				,success  : function(response)
                    			    {
                    					form.setLoading(false);
                    					json=Ext.decode(response.responseText);
                    					debug(json);
                    					if(json.success==true)
                    					{
                    						//////////////////////////////////
                    						////// usa codigo del padre //////
                    						/*//////////////////////////////*/
                    						marendNavegacion(2);
                                            /*//////////////////////////////*/
                                            ////// usa codigo del padre //////
                    						//////////////////////////////////
                    						Ext.Msg.show(
                                            {
                                                title   : 'Endoso generado',
                                                msg     : json.mensaje,
                                                buttons : Ext.Msg.OK
                                            });
                    					}
                    					else
                    					{
                    						mensajeError(json.error);
                    					}
                    			    }
                    			    ,failure  : function()
                    			    {
                    			    	form.setLoading(false);
                    			    	Ext.Msg.show(
                                        {
                                            title   : 'Error',
                                            icon    : Ext.Msg.ERROR,
                                            msg     : 'Error de comunicaci&oacute;n',
                                            buttons : Ext.Msg.OK
                                        });
                    			    }
                    			});
                    		}
                    		else
                    		{
                    			Ext.Msg.show(
                                {
                                    title   : 'Datos imcompletos',
                                    icon    : Ext.Msg.WARNING,
                                    msg     : 'Favor de llenar los campos requeridos',
                                    buttons : Ext.Msg.OK
                                });
                    		}
                    	}
                    }
                    ,{
                    	text     : 'Documentos'
                    	,icon    : '${ctx}/resources/fam3icons/icons/printer.png'
                    	,handler : function()
                        {
                    		Ext.create('Ext.window.Window',
                            {
                                title        : 'Documentos del tr&aacute;mite '+endnomInput['ntramite']
                                ,modal       : true
                                ,buttonAlign : 'center'
                                ,width       : 600
                                ,height      : 400
                                ,autoScroll  : true
                                ,loader      :
                                {
                                    url       : endnomUrlDoc
                                    ,params   :
                                    {
                                        'smap1.nmpoliza'  : endnomInput['nmpoliza']
                                        ,'smap1.cdunieco' : endnomInput['cdunieco']
                                        ,'smap1.cdramo'   : endnomInput['cdramo']
                                        ,'smap1.estado'   : endnomInput['estado']
                                        ,'smap1.nmsuplem' : '0'
                                        ,'smap1.ntramite' : endnomInput['ntramite']
                                        ,'smap1.nmsolici' : ''
                                        ,'smap1.tipomov'  : '0'
                                    }
                                    ,scripts  : true
                                    ,autoLoad : true
                                }
                            }).show();
                        }
                    }
                ]
            })
	    ]
	});
	/*///////////////////*/
	////// contenido //////
	///////////////////////
	
	//////////////////////
	////// cargador //////
	/*//////////////////*/
	/*//////////////////*/
	////// cargador //////
	//////////////////////

});
</script>
<div id="endnomDivPri"></div>