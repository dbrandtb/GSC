<%@ page language="java"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript">
/* ********** CATALOGOS JS ******************** */

//Estatus de tramite a utilizar:
var CatStatusTramite = {
    EndosoEnEspera   : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@ENDOSO_EN_ESPERA.codigo" />',
    EndosoConfirmado : '<s:property value="@mx.com.gseguros.portal.general.util.EstatusTramite@ENDOSO_CONFIRMADO.codigo" />'
};

//Tipos de tramite a utilizar:
var CatTipoTramite = {
    AutorizacionServicios : '<s:property value="@mx.com.gseguros.portal.general.util.TipoTramite@AUTORIZACION_SERVICIOS.codigo" />'
};

</script>