<%@ include file="/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript">
            //URL padre de los catalogos
            var _URL_OBTEN_CATALOGO_GENERICO=           '<s:url action="jsonObtenCatalogoGenerico" namespace="/" />';
            //URL que obtiene una cotizacion
            var _URL_CARGAR_COTIZACION=                 '<s:url action="dinosaurio2" namespace="/" />';
            
            /////////////////////////////////////
            ////// ini atributos catalogos //////
            /////////////////////////////////////
            var CDATRIBU_SEXO=                          '<s:property value="cdatribuSexo" />';                      //1
            //fecha nacimiento                                                                                        2
            var CDATRIBU_ESTADO=                        '<s:property value="cdatribuEstado" />';                    //3
            var CDATRIBU_CIUDAD=                        '<s:property value="cdatribuCiudad" />';                    //4
            //deducible                                                                                               5
            var CDATRIBU_COPAGO=                        '<s:property value="cdatribuCopago" />';                    //6
            var CDATRIBU_SUMA_ASEGURADA=                '<s:property value="cdatribuSumaAsegurada" />';             //7
            var CDATRIBU_CIRCULO_HOSPITALARIO=          '<s:property value="cdatribuCirculoHospitalario" />';       //8
            var CDATRIBU_COBERTURA_VACUNAS=             '<s:property value="cdatribuCoberturaVacunas" />';          //9
            var CDATRIBU_COBERTURA_PREV_ENF_ADULTOS=    '<s:property value="cdatribuCoberturaPrevEnfAdultos" />';   //10
            var CDATRIBU_MATERNIDAD=                    '<s:property value="cdatribuMaternidad" />';                //11
            var CDATRIBU_SUMA_ASEGUARADA_MATERNIDAD=    '<s:property value="cdatribuSumaAseguradaMatenidad" />';    //12
            var CDATRIBU_BASE_TABULADOR_REEMBOLSO=      '<s:property value="cdatribuBaseTabuladorRembolso" />';     //13
            var CDATRIBU_COSTO_EMERGENCIA_EXTRANJERO=   '<s:property value="cdatribuCostoEmergenciaExtranjero" />'; //14
            var CDATRIBU_COB_ELIM_PEN_CAMBIO_ZONA=      '<s:property value="cdatribuCobEliPenCamZona" />';          //15
            var CDATRIBU_ROL=                           '<s:property value="cdatribuRol" />';                       //16
            /////////////////////////////////////
            ////// fin atributos catalogos //////
            /////////////////////////////////////
            
            //URL destino
            var _URL_COTIZAR=                           '<s:url action="resultadoCotizacionVital" namespace="/flujocotizacion" />';
            //URL redireccion despues de cotizar
            var _URL_RESULTADO_COTIZACION=              '<s:url namespace="/flujocotizacion" action="resultCotizacion" />';
        </script>
        <script src="${ctx}/resources/jsp-script/extjs4/asegurados.js"></script>
    </head>
    <body>
        <div id="maindiv" style="height:850px;border:0px solid blue;"></div>
    </body>
</html>