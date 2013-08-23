<%@ include file="/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript">
            var _URL_OBTEN_CATALOGO_GENERICO=           '<s:url action="jsonObtenCatalogoGenerico" namespace="/" />';
            var _URL_ASEGURAR=                          '<s:url action="dinosaurio2" namespace="/" />';
            var CDATRIBU_SEXO=                          '<s:property value="cdatribuSexo" />';
            var CDATRIBU_CIUDAD=                        '<s:property value="cdatribuCiudad" />';
            var CDATRIBU_COPAGO=                        '<s:property value="cdatribuCopago" />';
            var CDATRIBU_SUMA_ASEGURADA=                '<s:property value="cdatribuSumaAsegurada" />';
            var CDATRIBU_CIRCULO_HOSPITALARIO=          '<s:property value="cdatribuCirculoHospitalario" />';
            var CDATRIBU_COBERTURA_VACUNAS=             '<s:property value="cdatribuCoberturaVacunas" />';
            var CDATRIBU_COBERTURA_PREV_ENF_ADULTOS=    '<s:property value="cdatribuCoberturaPrevEnfAdultos" />';
            var CDATRIBU_MATERNIDAD=                    '<s:property value="cdatribuMaternidad" />';
            var CDATRIBU_SUMA_ASEGUARADA_MATERNIDAD=    '<s:property value="cdatribuSumaAseguradaMatenidad" />';
            var CDATRIBU_BASE_TABULADOR_REEMBOLSO=      '<s:property value="cdatribuBaseTabuladorRembolso" />';
            var CDATRIBU_COSTO_EMERGENCIA_EXTRANJERO=   '<s:property value="cdatribuCostoEmergenciaExtranjero" />';
            var _URL_COTIZAR=                           '<s:url action="cotizaSalud" namespace="/" />';
        </script>
        <script src="${ctx}/resources/jsp-script/extjs4/asegurados.js"></script>
    </head>
    <body>
        <div id="maindiv"></div>
    </body>
</html>
