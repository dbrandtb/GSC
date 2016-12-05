<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// overrides //////
////// overrides //////
var _p30_urlCargaMasiva                       = '<s:url namespace="/emision"         action="procesarCargaMasivaRecuperaInd"         />';

var _p30_urlRecuperarDatosTramiteValidacion = '<s:url namespace="/flujomesacontrol" action="recuperarDatosTramiteValidacionCliente" />';
var _p29_urlObtieneValNumeroSerie           = '<s:url namespace="/emision"          action="obtieneValNumeroSerie"                  />';

var MontoMaximo = 0;
var MontoMinimo = 0;

var _p30_urlImprimirCotiza = '<s:text name="ruta.servidor.reports" />';
var _p30_reportsServerUser = '<s:text name="pass.servidor.reports" />';
var _p30_urlRecuperacion = '<s:url namespace="/recuperacion" action="recuperar"/>';
var _RUTA_DOCUMENTOS_TEMPORAL = '<s:text name="ruta.documentos.temporal" />';

var cargarXpoliza = false;

Ext.onReady(function(){
	

	
	Ext.create('Ext.form.Panel', {
	    title: 'CARGA DE ARCHIVOS SALUD',
	    bodyPadding: 5,
	    width: 350,

	    // The form will submit an AJAX request to this URL when submitted
	    //url: 'save-form.php',

	    // Fields will be arranged vertically, stretched to full width
	    layout: 'anchor',
	    defaults: {
	        anchor: '100%'
	    },

	    // The fields
	    defaultType: 'textfield',
	    items: [
           {
                xtype         : 'filefield'
                ,buttonOnly   : true
                ,style        : 'margin:0px;'
                ,name         : 'excel'
                ,style        : 'background:#223772;'
                ,buttonConfig :
                {
                    text  : 'Carga masiva...'
                    ,icon : '${ctx}/resources/fam3icons/icons/book_next.png'
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
                         }else{
                        	 me.up('form').submit(
                        			 {
								url     : _p30_urlCargaMasiva
                                            ,params :
                                            {
                                                'smap1.cdramo'    : '5'
                                                ,'smap1.cdtipsit' : 'AR'
                                                ,'smap1.tipoflot' : 'A'
                                                ,'smap1.codpos'   : 'S'
                                                ,'smap1.cambio'   : 'U'
                                                ,'smap1.negocio'  : 'q'
                                            }
                        			 });
                        	 <!--debug('',);-->
                         }
                     }
                }
            }
        ],
	    renderTo: Ext.getBody()
	});

});

</script>
</head>
<body>
<!-- div id="_p30_divpri" style="height:1100px;"></div -->
</body>
</html>