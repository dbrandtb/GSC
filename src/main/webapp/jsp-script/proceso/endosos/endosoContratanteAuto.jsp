<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
///////////////////////
////// variables //////

/*
smap1:
    CDRAMO: "2"
    CDTIPSIT: "SL"
    CDUNIECO: "1006"
    DSCOMENT: ""
    DSTIPSIT: "SALUD VITAL"
    ESTADO: "M"
    FEEMISIO: "22/01/2014"
    FEINIVAL: "22/01/2014"
    NMPOLIEX: "1006213000024000000"
    NMPOLIZA: "24"
    NMSUPLEM: "245668019180000000"
    NSUPLOGI: "1"
    NTRAMITE: "678"
    PRIMA_TOTAL: "12207.37"
*/
//Obtenemos el contenido en formato JSON de la propiedad solicitada:
var _35_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;

var _35_formLectura;
var _35_formContratante;
var _35_panelPri;
var _35_panelEndoso;
var _35_fieldFechaEndoso;
var _35_storeContratantes;
var _35_gridContratantes;

var _35_urlGuardar     = '<s:url namespace="/endosos" action="guardarEndosoContratante"       />';
var _35_urlLoadContratantes = '<s:url namespace="/endosos" action="cargarContratantesEndosoContratante" />';

var _p35_urlRecuperarCliente = '<s:url namespace="/" action="buscarPersonasRepetidas" />';

debug('_35_smap1:',_35_smap1);


var _cdpersonActual;
var _cdpersonNuevo = '';
var _cdpostalNuevo = '';
////// variables //////
///////////////////////

Ext.onReady(function()
{
	
	
	var query = Ext.ComponentQuery.query('#_p22_PanelPrincipal');
	if(query.length>0){
		
		
		for(var pos = query.length-1 ; pos>=0; pos--){
		
			var pantallaPersonas = query[pos];
			
			pantallaPersonas.query().forEach(function(element){
				try{
					element.destroy();
				}catch(e){
					debug('Error al destruir en Pantalla de Clientes',e);
				}
			});
		
			try{
				pantallaPersonas.destroy();	
			}catch(e){
				debug('Error al destruir Panel Principal en Pantalla de Clientes',e);
			}
		
			try{
				windowAccionistas.destroy();	
			}catch(e){
				debug('Error al destruir Window accionistas en Pantalla de Clientes',e);
			}
		}
	}
	
	
	///////////////////////
	////// overrides //////
	Ext.override(Ext.form.field.ComboBox,
	{
		initComponent : function()
		{
			debug('Ext.form.field.ComboBox initComponent');
			Ext.apply(this,
			{
				forceSelection : false
			});
			return this.callParent();
		}
	});
	////// overrides //////
	///////////////////////
	
    /////////////////////
    ////// modelos //////
    Ext.define('_35_ModeloContratante',
    {
    	extend  : 'Ext.data.Model'
    	,fields : [ <s:property value="imap1.fieldsModelo" /> ]
    });
    
    Ext.define('_p35_modeloRecuperado',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'NOMBRECLI'
            ,'DIRECCIONCLI'
        ]
    });
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    _35_storeContratantes=Ext.create('Ext.data.Store',
    {
		autoLoad : false
    	,model   : '_35_ModeloContratante'
        ,proxy   :
        {
            type    : 'ajax'
            ,reader : 'json'
            ,data   : []
        }
    });
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    Ext.define('_35_GridContratantes',
    {
    	extend         : 'Ext.grid.Panel'
    	,initComponent : function()
    	{
    		debug('_35_GridContratantes initComponent');
    		Ext.apply(this,
    		{
    			title    : 'Contratante Actual'
    			,store   : _35_storeContratantes
    			,columns : [ <s:property value="imap1.columnsGrid" escapeHtml="false" /> ]
    		});
    		this.callParent();
    	}
    });
    
    Ext.define('_35_PanelEndoso',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_35_PanelEndoso initComponent');
            Ext.apply(this,
            {
                title     : 'Datos del endoso'
                ,defaults :
                {
                    style : 'margin : 5px;'
                }
                ,items    :
                [
                    _35_fieldFechaEndoso
                ]
            });
            this.callParent();
        }
    });
    
    Ext.define('_35_FormContratante',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_35_FormContratante initComponent');
            Ext.apply(this,
            {
                title       : 'Cambio de Nombre del Contratante'
//                ,height     : 80
//                ,autoScroll : true
                ,defaults :
                {
                    style : 'margin : 8px;'
                }
				,items     : [
				{
					xtype: 'textfield',
					name:  'nuevoContratante',
					width: 550,
					fieldLabel: 'Nombre',
					allowBlank: false,
					readOnly: true
				},
				{
					xtype: 'button',
					text: 'Seleccionar Cliente',
					handler: function(){
					        var ventana=Ext.create('Ext.window.Window',
					        {
					            title      : 'Recuperar cliente'
					            ,modal     : true
					            ,width     : 600
					            ,height    : 400
					            ,items     :
					            [
					                {
					                    layout    : 'hbox'
					                    ,defaults : { style : 'margin : 5px;' }
					                    ,items    :
					                    [
					                        {
					                            xtype       : 'textfield'
					                            ,name       : '_p35_recuperaRfc'
					                            ,fieldLabel : 'RFC'
					                            ,minLength  : 9
					                            ,maxLength  : 13
					                        }
					                        ,{
					                            xtype    : 'button'
					                            ,text    : 'Buscar'
					                            ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
					                            ,handler : function(button)
					                            {
					                                debug('recuperar cliente buscar');
					                                var rfc=_fieldByName('_p35_recuperaRfc').getValue();
					                                var valido=true;
					                                if(valido)
					                                {
					                                    valido = !Ext.isEmpty(rfc)
					                                             &&rfc.length>8
					                                             &&rfc.length<14;
					                                    if(!valido)
					                                    {
					                                        mensajeWarning('Introduza un RFC v&aacute;lido');
					                                    }
					                                }
					                                
					                                if(valido)
					                                {
					                                    button.up('window').down('grid').getStore().load(
					                                    {
					                                        params :
					                                        {
					                                            'map1.pv_rfc_i'       : rfc
					                                            ,'map1.cdtipsit'      : _35_smap1.CDTIPSIT
					                                            ,'map1.pv_cdtipsit_i' : _35_smap1.CDTIPSIT
					                                            ,'map1.pv_cdunieco_i' : _35_smap1.CDUNIECO
					                                            ,'map1.pv_cdramo_i'   : _35_smap1.CDRAMO
					                                            ,'map1.pv_estado_i'   : 'W'
					                                            ,'map1.pv_nmpoliza_i' : ''
					                                            ,'map1.soloBD' : 'S'
					                                        }
					                                    });
					                                }
					                            }
					                        }
					                    ]
					                }
					                ,Ext.create('Ext.grid.Panel',
					                {
					                    title    : 'Resultados'
					                    ,columns :
					                    [
					                        {
					                            xtype    : 'actioncolumn'
					                            ,width   : 30
					                            ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
					                            ,handler : function(view,row,col,item,e,record)
					                            {
					                                debug('recuperar cliente handler record:',record);
					                                
					                                if(_cdpersonActual == record.raw.CLAVECLI){
					                                	mensajeWarning('El cliente seleccionado es el mismo que se encuentra actualmente registrado en la p&oacute;liza');
					                                	return;
					                                }
					                                
													_cdpersonNuevo = record.raw.CLAVECLI;
													_cdpostalNuevo = record.raw.CODPOSTAL;
													
													_35_formContratante.down('[name=nuevoContratante]').setValue( record.raw.NOMBRECLI);
					                                
					                                ventana.destroy();
					                            }
					                        }
					                        ,{
					                            text       : 'Nombre'
					                            ,dataIndex : 'NOMBRECLI'
					                            ,width     : 200
					                        }
					                        ,{
					                            text       : 'Direcci&oacute;n'
					                            ,dataIndex : 'DIRECCIONCLI'
					                            ,flex      : 1
					                        }
					                    ]
					                    ,store : Ext.create('Ext.data.Store',
					                    {
					                        model     : '_p35_modeloRecuperado'
					                        ,autoLoad : false
					                        ,proxy    :
					                        {
					                            type    : 'ajax'
					                            ,url    : _p35_urlRecuperarCliente
					                            ,timeout: 240000
					                            ,reader :
					                            {
					                                type  : 'json'
					                                ,root : 'slist1'
					                            }
					                        }
					                    })
					                })
					            ]
					        }).show();
					        centrarVentanaInterna(ventana);
					    
					}
				}
				]
            });
            this.callParent();
        }
    });
    
    Ext.define('_35_FormLectura',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_35_FormLectura initComponent');
            Ext.apply(this,
            {
                title      : 'Datos de la p&oacute;liza'
                ,defaults  :
                {
                    style : 'margin : 5px;'
                }
                ,layout    :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,listeners :
                {
                    afterrender : function(me){heredarPanel(me);}
                }
                ,items     : [ <s:property value="imap1.itemsPanelLectura" /> ]
            });
            this.callParent();
        }
    });
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    _35_fieldFechaEndoso=Ext.create('Ext.form.field.Date',
    {
        format      : 'd/m/Y'
        ,fieldLabel : 'Fecha de efecto'
        ,allowBlank : false
        ,value      : '<s:property value="smap1.fechaInicioEndoso" />'
        ,readOnly   : true
        ,name       : 'fecha_endoso'
    });
    _35_panelEndoso = new _35_PanelEndoso();
    _35_formContratante  = new _35_FormContratante();
    _35_formLectura = new _35_FormLectura();
    _35_gridContratantes = new _35_GridContratantes();
    
    _35_panelPri=Ext.create('Ext.panel.Panel',
    {
        renderTo     : '_35_divPri'
        ,defaults    :
        {
            style : 'margin : 5px;'
        }
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                text      : 'Confirmar endoso'
                ,icon     : '${ctx}/resources/fam3icons/icons/key.png'
                ,handler  : _35_confirmar
            }
        ]
        ,items       :
        [
            _35_formLectura
            ,_35_gridContratantes
            ,_35_formContratante
            ,_35_panelEndoso
        ]
    });
    ////// contenido //////
    ///////////////////////
    
    ////////////////////
    ////// loader //////
    _35_panelPri.setLoading(true);
    Ext.Ajax.request(
    {
    	url      : _35_urlLoadContratantes
    	,params  :
    	{
    		'smap1.cdunieco'  : _35_smap1.CDUNIECO
    		,'smap1.cdramo'   : _35_smap1.CDRAMO
    		,'smap1.estado'   : _35_smap1.ESTADO
    		,'smap1.nmpoliza' : _35_smap1.NMPOLIZA
    		,'smap1.nmsuplem' : _35_smap1.NMSUPLEM
    	}
    	,success : function (response)
    	{
    		_35_panelPri.setLoading(false);
    		var json=Ext.decode(response.responseText);
    		debug('contratantes cargados:',json);
    		if(json.success==true)
    		{
    			/*
    			a.cdunieco, a.cdramo, a.estado, a.nmpoliza, a.cdcontratante, a.nmsuplem, a.status, a.cdtipoag, porredau, a.porparti,nombre
    			*/
    			_35_storeContratantes.add(json.slist1);
    			
    			_cdpersonActual = _35_storeContratantes.getAt(0).get('CDPERSON');
    			
    		}
    		else
    		{
    			mensajeError(json.error);
    			//////////////////////////////////
                ////// usa codigo del padre //////
                /*//////////////////////////////*/
                marendNavegacion(4);
//                if(!Ext.isEmpty(destruirContLoaderPersona)){
//					destruirContLoaderPersona();	                                
//	            }
                /*//////////////////////////////*/
                ////// usa codigo del padre //////
                //////////////////////////////////
    		}
    	}
        ,failure : function()
        {
        	_35_panelPri.setLoading(false);
        	errorComunicacion();
        }
    });
    ////// loader //////
    ////////////////////
});

///////////////////////
////// funciones //////
function _35_confirmar()
{
    debug('_35_confirmar');
    
    var valido=true;
    
    if(valido)
    {
        valido=!Ext.isEmpty(_cdpersonNuevo);
        if(!valido)
        {
            mensajeWarning('Seleccione el nuevo Contratante.');
        }
    }
    
    if(valido)
    {
        /*
        a.cdunieco, a.cdramo, a.estado, a.nmpoliza, a.cdcontratante, a.nmsuplem, a.status, a.cdtipoag, porredau, a.porparti,nombre,cdsucurs,nmcuadro
        */
        var slist1=[];
        _35_storeContratantes.each(function(record)
        {
        	slist1.push(record.raw);
        });
        var json=
        {
            smap1   : _35_smap1
            ,smap2  :
            {
                'fecha_endoso'     : Ext.Date.format(_35_fieldFechaEndoso.getValue(),'d/m/Y'),
                'cdpersonNvoContr' : _cdpersonNuevo,
                'cdpostalNuevo'    : _cdpostalNuevo
            }
            ,slist1 : slist1
        }
        debug('datos que se enviaran:',json);
        var panelMask = new Ext.LoadMask('_35_divPri', {msg:"Confirmando..."});
		panelMask.show();
		
        Ext.Ajax.request(
        {
            url       : _35_urlGuardar
            ,jsonData : json
            ,success  : function(response)
            {
                panelMask.hide();
                json=Ext.decode(response.responseText);
                debug('datos recibidos:',json);
                if(json.success==true)
                {
                    mensajeCorrecto('Endoso generado',json.mensaje);
                    
                    //////////////////////////////////
                    ////// usa codigo del padre //////
                    /*//////////////////////////////*/
                    marendNavegacion(2);
                    
//                    if(!Ext.isEmpty(destruirContLoaderPersona)){
//							destruirContLoaderPersona();	                                
//	                }
                    /*//////////////////////////////*/
                    ////// usa codigo del padre //////
                    //////////////////////////////////
                }
                else
                {
                    mensajeError(json.error);
                }
            }
            ,failure  : function()
            {
                panelMask.hide();
                errorComunicacion();
            }
        });
    }
};
////// funciones //////
///////////////////////
</script>
<div id="_35_divPri" style="height:1000px;"></div>