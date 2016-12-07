<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// overrides //////
////// overrides //////
var _p30_urlCargaMasiva = '<s:url namespace="/cargamasiva"         action="procesarCargaMasivaRecuperaInd" />';

Ext.onReady(function(){

	Ext.create('Ext.form.Panel', {
	    title: 'CARGA DE ARCHIVOS SALUD',
	    bodyPadding: 5,
	    width: 350,
	    layout: 'anchor', // Fields will be arranged vertically, stretched to full width
	    defaults: {
	        anchor: '100%'
        },
	    items: [{
            xtype        : 'filefield',
            buttonOnly   : true,
            style        : 'margin:0px;',
            name         : 'file',
            style        : 'background:#223772;',
            buttonConfig : {
                text  : 'Carga masiva...',
                icon : '${ctx}/resources/fam3icons/icons/book_next.png'
            },
            listeners : {
                change : function(me) {   
                    var descripcion ='';
                    var msnIncInv='';
                    var indexofPeriod = me.getValue().lastIndexOf(".");
                    var uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
                    debug('uploadedExtension:',uploadedExtension);
                    var valido = Ext.Array.contains(['xls','xlsx'], uploadedExtension);
                    if(!valido) {
                        mensajeWarning('Solo se permiten hojas de c&aacute;lculo');
                        me.reset();
                    } else {
                   	    me.up('form').submit({
	                        url : _p30_urlCargaMasiva
                   	    });
                    }
                }
            }
	    }],
	    renderTo: 'dvCargaEmisionRecInd'
	});

});

</script>
</head>
<body>
    <div id="dvCargaEmisionRecInd" style="height:1100px;"></div>
</body>
</html>