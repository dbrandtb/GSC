<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Codigos Postales</title>
<script>

///////////////////////
////// variables //////
/*///////////////////*/

var _UrlAgregaCP = '<s:url namespace="/catalogos"    action="agregaCP" />';
var _URL_CARGA_CATALOGO = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
var _CAT_NACIONALIDAD  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@NACIONALIDAD"/>';

var _MSG_SIN_DATOS = 'No hay datos';

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
	
	
	var codigoPostal = new Ext.form.TextField({
    	fieldLabel: 'C&oacute;digo Postal',
    	name: 		'params.codigoPostal',
    	allowBlank: false,
        blankText:'Campo requerido',
        minLength: 5,
        maskRe   : /^[0-9]+$/,
		minLengthText: 'El C&oacute;digo Postal debe tener almenos 5 caracteres.'
	});
	
	var estado = Ext.create('Ext.form.ComboBox', {
    	name:'params.cdedo',
    	fieldLabel: 'Estado',
    	queryMode:'local',
    	displayField: 'value',
    	valueField: 'key',
    	allowBlank:false,
    	forceSelection: true,
    	emptyText:'Seleccione...',
    	store: Ext.create('Ext.data.Store', {
            model     : 'Generic',
            autoLoad  : true,
            proxy     : {
                type        : 'ajax'
                ,url        : _URL_CARGA_CATALOGO
                ,extraParams: {catalogo:_CAT_NACIONALIDAD}
                ,reader     :
                {
                    type  : 'json'
                    ,root : 'lista'
                }
            }
        })
   	});   

	
	var panelCP = Ext.create('Ext.form.Panel', {
		title: 'Agregar un nuevo C&oacute;digo Postal',
		url: _UrlAgregaCP,
		border: false,
	    bodyStyle:'padding:5px 0px 0px 40px;',
	    renderTo: 'mainDivCP',
	    items    : [codigoPostal,estado],
        buttonAlign: 'center',
	    buttons: [{
        	text: 'Guardar',
        	icon    : '${ctx}/resources/fam3icons/icons/disk.png',
        	handler: function(btn, e) {
        		var form = this.up('form').getForm();
        		if (form.isValid()) {
        			
        			Ext.Msg.show({
    		            title: 'Confirmar acci&oacute;n',
    		            msg: '&iquest;Esta seguro que desea crear este nuevo C&oacute;digo Postal?',
    		            buttons: Ext.Msg.YESNO,
    		            fn: function(buttonId, text, opt) {
    		            	if(buttonId == 'yes') {
    		            		form.submit({
			    		        	waitMsg:'Procesando...',			        	
			    		        	failure: function(form, action) {
			    		        		showMessage('Error', action.result.errorMessage, Ext.Msg.OK, Ext.Msg.ERROR);
			    					},
			    					success: function(form, action) {
			    						form.reset();
			    						mensajeCorrecto('\u00C9xito', 'El C&oacute;digo Postal se actializ&oacute; correctamente.', Ext.Msg.OK, Ext.Msg.INFO);
			    					}
			    				});
    		            	}
            			},
    		            animateTarget: btn,
    		            icon: Ext.Msg.QUESTION
        			});
    			} else {
    				Ext.Msg.show({
    					title: 'Aviso',
    		            msg: 'Complete la informaci&oacute;n requerida',
    		            buttons: Ext.Msg.OK,
    		            animateTarget: btn,
    		            icon: Ext.Msg.WARNING
    				});
    			}
        	}
        },
        {
            	text: 'Agregar Otro',
            	icon    : '${ctx}/resources/fam3icons/icons/cancel.png',
            	handler: function(btn, e) {
            		panelCP.getForm().reset();
            	}
        }]
    });
	
		
});
</script>

</head>
<body>
<div id="mainDivCP" style="height:433px;"></div>
</body>
</html>