/**
 * Valida un RFC a partir de sus datos de entrada
 * @param {} tipoPersona Indica si es Fï¿½sica o Moral
 * @param {} nombre Primer nombre
 * @param {} nombre2 Segundo nombre
 * @param {} apaterno 
 * @param {} amaterno 
 * @param {} fecha Fecha de nacimiento para persona fisica, fecha inicio para persona moral
 * @param {} rfc RFC a validar
 * @return true si es valido, false si es invalido
 */
function validaRFC(tipoPersona, nombre, nombre2, apaterno, amaterno, fecha, rfc) {
    try {
        // Persona fisica
        if(tipoPersona == 'F') {
            if(rfc.substr(0, 1) != apaterno.substr(0, 1)) {
                return false;
            }
            if(rfc.substr(2, 1) != amaterno.substr(0, 1)) {
                return false;
            }
            if(rfc.substr(3, 1) != nombre.substr(0, 1)) {
                return false;
            }
            if(rfc.substr(4, 2) != fecha.substr(8, 2)) {
                return false;
            }
            if(rfc.substr(6, 2) != fecha.substr(3, 2)) {
                return false;
            }
            if(rfc.substr(8, 2) != fecha.substr(0, 2)) {
                return false;
            }
        } else if(tipoPersona == 'M' || tipoPersona == 'S') { // Persona moral y regimen Simplificado
            if(rfc.substr(0, 1) != nombre.substr(0, 1)) {
                return false;
            }
            if(rfc.substr(1, 1) != nombre.substr(1, 1)) {
                return false;
            }
            if(rfc.substr(2, 1) != nombre.substr(2, 1)) {
                return false;
            }
            if(rfc.substr(3, 2) != fecha.substr(8, 2)) {
                return false;
            }
            if(rfc.substr(5, 2) != fecha.substr(3, 2)) {
                return false;
            }
            if(rfc.substr(7, 2) != fecha.substr(0, 2)) {
                return false;
            }
        } else {
            throw 'Error en la validacion, tipo de persona invalido: "' + tipoPersona + '"';
        }
        return true;
    } catch(e) {
        throw 'Error al validar RFC' + e.toString();
    }
}


/**
 * Genera un RFC de acuerdo a los datos solicitados
 * @param {} tipoPersona
 * @param {} nombre
 * @param {} nombre2
 * @param {} apaterno
 * @param {} amaterno
 * @param {} fecha
 */
function generaRFC(tipoPersona, nombre, nombre2, apaterno, amaterno, fecha) {
    try {
        if(tipoPersona == 'F') {
            //return apaterno.substr(0,2) + amaterno.substr(0,1) + nombre.substr(0, 1) +
                //fecha.substr(8, 2) + fecha.substr(3, 2) + fecha.substr(0, 2);
            return generaRFCPersonaFisica(nombre, nombre2, apaterno, amaterno, fecha);
        } else if(tipoPersona == 'M' || tipoPersona == 'S') { // Persona moral y regimen Simplificado
            //return nombre.substr(0,3) + fecha.substr(8, 2) + fecha.substr(3, 2) + fecha.substr(0, 2);
            return generaRFCPersonaMoral(nombre, fecha);
        } else {
            throw 'Error en la validacion, tipo de persona invalido';
        }
    } catch(e) {
        throw 'Error al generar RFC' + e.toString();
    }
}


function run(){
   
    var tipoPer = document.getElementById('tipoPersona').value;
    var name = document.getElementById('nombre').value;
    var name2 = document.getElementById('nombre2').value;
    var paterno = document.getElementById('apaterno').value;
    var materno = document.getElementById('amaterno').value;
    var date = document.getElementById('fecha').value;
    
    //alert( cuentaPalabras(entrada) );
    //alert('es vocal?' + esVocal('s')  );
    
    //document.getElementById('rfc').value = generaRFCPersonaMoral(name, date);
    
    document.getElementById('rfc').value = generaRFC(tipoPer, name, name2, paterno, materno, date);
    
    document.getElementById('numPalabras').value = cuentaPalabras(name);
}


/**
 * Genera el RFC de una persona física
 * @param {} nombre Nombre
 * @param {} nombre2 Segundo nombre
 * @param {} apaterno Apellido paterno
 * @param {} amaterno Apellido materno
 * @param {} fecha  Fecha de nacimiento en formato DD/MM/YYYY
 */
function generaRFCPersonaFisica(nombre1, nombre2, apaterno, amaterno, fecha) {
    
    // TODO: Analizar si le pasamos el nombre 1 y 2 en una sola variable
	
	
	var nombreComp = nombre1 + ' ' + nombre2; 
	
	// Se convierte a mayuscula y sin acentos:
	nombreComp = nombreComp.toUpperCase();
	nombreComp = reemplazaCaracteresLatinos(nombreComp);
    apaterno = apaterno.toUpperCase();
    apaterno = reemplazaCaracteresLatinos(apaterno);
    amaterno = amaterno.toUpperCase();
    amaterno = reemplazaCaracteresLatinos(amaterno);
    debug('A mayusculas y sin acentos:', nombreComp, apaterno, amaterno);
    
    //
    nombreComp = reemplazarCaracteresEspecialesPersonaFisica(nombreComp);
    apaterno = reemplazarCaracteresEspecialesPersonaFisica(apaterno);
    amaterno = reemplazarCaracteresEspecialesPersonaFisica(amaterno);
    debug('Reemplazar caracteres especiales por su descripcion:', nombreComp, apaterno, amaterno);
    
    // Se eliminan guiones, signos y puntos, etc:
    nombreComp = quitaEspaciosSignosYPuntos(nombreComp);
    apaterno = quitaEspaciosSignosYPuntos(apaterno);
    amaterno = quitaEspaciosSignosYPuntos(amaterno);
    debug('Sin espacios, signos ni puntos:', nombreComp, apaterno, amaterno);
    
    // REGLA 8
    // Cuando en el nombre de las personas físicas figuren artículos, preposiciones, conjunciones o contracciones 
    // no se tomarán como elementos de integración de la clave:
    nombreComp = quitaAbreviaturasPersonaFisica(nombreComp);
    apaterno   = quitaAbreviaturasPersonaFisica(apaterno);
    amaterno   = quitaAbreviaturasPersonaFisica(amaterno);
    debug('Sin abreviaturas persona Fisica:', nombreComp, apaterno, amaterno);
    
    // REGLA 6
    // Cuando el nombre es compuesto, o sea, que esta formado por dos o más palabras, 
    // se tomará para la conformación la letra inicial de la primera, 
    // siempre que no sea MARIA o JOSE dado su frecuente uso, en cuyo caso se tomará la primera letra de la segunda palabra.
    nombreComp  = nombreComp.replace(/\b(MARIA)\b/gi, '').replace(/\b(JOSE)\b/gi, '');
    
    var tmp = '';
    
    // REGLA 7
    // En los casos en que la persona física tenga un solo apellido, 
    // se conformará con la primera y segunda letras del apellido paterno o materno, 
    // según figure en el acta de nacimiento, más la primera y segunda letras del nombre.
    // Ejemplos:
    // Juan Martínez       MAJU-420116
    // Gerarda Zafra       ZAGE-251115
    if(!apaterno || !amaterno) {
    	debug('No cuenta con algun apellido');
    	var apellido = apaterno + amaterno;
    	debug('Apellido compuesto:', apellido);
    	tmp = apellido.match(/\b(\w){2}/i)[0];
    	tmp += nombreComp.match(/\b(\w){2}/i)[0];
    	
    } else {
    	debug('Tiene los 2 apellidos');
	    // REGLA 1
        // Se integra la clave con los siguientes datos:
        // 1.  La primera letra del apellido paterno y la siguiente primera vocal del mismo.
        debug('Parametros:', nombreComp, apaterno, amaterno, fecha);
        tmp = apaterno.match(/\b(\w)/i)[0];
        tmp += apaterno.match(/[aeiou]/i)[0];
        debug('Con apaterno:', tmp);
        
        // 2.  La primera letra del apellido materno.
        tmp += amaterno.match(/\b(\w)/i)[0];
        debug('Con amaterno:', tmp);
        
        // 3.  La primera letra del nombre.
        tmp += nombreComp.match(/\b(\w)/i)[0];
        debug('Con nombre:', tmp);
    }

    
    // REGLA 9
    // Cuando de las cuatro letras que formen la expresión alfabética, resulte una palabra inconveniente, 
    // la última letra será sustituida por una  “ X “.
    tmp = quitaPalabrasInconvenientesPersonaFisica(tmp);
    
    // REGLA 2
    // A continuación se anotará la fecha de nacimiento del contribuyente, en el siguiente orden:
    // Año: Se tomarán las dos últimas cifras, escribiéndolas con números arábigos.
    // Mes: Se tomará el mes de nacimiento en su número de orden, en un año de calendario, escribiéndolo con números arábigos.
    // Día: Se escribirá con números arábigos.
    tmp += fecha.substr(8, 2) + fecha.substr(3, 2) + fecha.substr(0, 2);
    debug('Con fecha:', tmp);
    
    return tmp;
}


/**
 * Genera el RFC de una persona moral
 * @param {} nombre Nombre de la persona moral
 * @param {} fecha  Fecha de constitución
 */
function generaRFCPersonaMoral(nombre, fecha) {
    
    // Se convierte a mayuscula y sin acentos:
    nombre = nombre.toUpperCase();  
    nombre = reemplazaCaracteresLatinos(nombre);
    debug('A mayusculas y sin acentos:', nombre);
    
    //
    nombre = reemplazarCaracteresEspecialesPersonaMoral(nombre);
    debug('Reemplazar caracteres especiales por su descripcion:', nombre);
    
    // Se eliminan guiones, signos y puntos, etc:
    nombre = quitaEspaciosSignosYPuntos(nombre);
    debug('Sin espacios, signos ni puntos:', nombre);
    
    // Regla 9.-
    // Cuando en la denominación o razón social figuren artículos, preposiciones y conjunciones o contracciones 
    // no se tomaran como elementos de integración de la clave:
    nombre = quitaAbreviaturasPersonaMoral(nombre);
    debug('Sin abreviaturas persona Moral:', nombre);
    
    var numPalabras = cuentaPalabras(nombre);
    debug('numPalabras=', numPalabras);
    
    // Se obtiene la fecha de constitucion
    
    // Regla 1.-
    
    
    // Regla 3:
    // Cuando la letra inicial de cualquiera de las tres primeras palabras 
    // de la denominación o razón social sea compuesta, 
    // únicamente se anotará la inicial de esta.
    // NO APLICA CODIGO
    
    //rfc = cuentaPalabras(nombre);
    
    if(numPalabras == 1) {
        nombre = obtienePalabra(nombre, 1).substr(0, 3);
    } else if(numPalabras == 2) {
        // REGLA 6ª.
        // Si la denominación o razón social se comprende de dos elementos, 
        // para efectos de la conformación de la clave, se tomará la letra inicial de la primera palabra 
        // y las dos primeras letras de la segunda
        nombre = obtienePalabra(nombre, 1).substr(0, 1) + obtienePalabra(nombre, 2).substr(0, 2); 
        
    } else if(numPalabras >= 3) {
        
        nombre = obtienePalabra(nombre, 1).substr(0, 1)
                + obtienePalabra(nombre, 2).substr(0, 1)
                + obtienePalabra(nombre, 3).substr(0, 1);
        
    } else {
        //throw 'No existen palabras para generar el RFC: "' + nombre + '"';
    }
    
    // Regla 8:
    // Cuando la denominación o razón social se componga de un solo elemento y sus letras no completen las tres requeridas, 
    // para efectos de conformación de la clave, se tomaran las empleadas por el contribuyente 
    // y las restantes se suplirán con una “X”:
    nombre = padding_right(nombre, 'X', 3);
    
    
    // Regla 2:
    // Se agrega la fecha de constitucion:
    nombre += fecha.substr(8, 2) + fecha.substr(3, 2) + fecha.substr(0, 2);
    
    return nombre;
}


/**
 * Cuenta el numero de palabras en un texto
 * @param {} txt Texto de entrada
 * @return Numero de palabras encontradas
 */
function cuentaPalabras(txt) {
	txt = txt.replace(/[\.]{1,}/gi," "); // reemplazamos los puntos por espacios
    txt = txt.replace(/(^\s*)|(\s*$)/gi,"");
    txt = txt.replace(/[ ]{2,}/gi," ");
    txt = txt.replace(/\n /,"\n");
    
    //debug('txt=="', txt, '"');
    
    if(txt.length > 0) {
    	return txt.split(" ").length;
    } else {
    	return 0;
    }
}

/**
 * Reemplaza caracteres latinos, letras con acento y tildes
 * @param {} txt
 * @return {}
 */
function reemplazaCaracteresLatinos(txt) {
	return txt.replace(/\u00E1/g, 'a').replace(/\u00C1/g, 'A')
              .replace(/\u00E9/g, 'e').replace(/\u00C9/g, 'E')
              .replace(/\u00ED/g, 'i').replace(/\u00CD/g, 'I')
              .replace(/\u00F3/g, 'o').replace(/\u00D3/g, 'O')
              .replace(/\u00FA/g, 'u').replace(/\u00DA/g, 'U').replace(/\u00FC/gi,"u").replace(/\u00DC/,"U")
              .replace(/\u00F1/g, 'n').replace(/\u00D1/g, 'N');
}

/**
 * 
 * @param {} txt
 * @return {}
 */
function obtienePalabra(txt, pos) {
    txt = txt.replace(/[\.]{1,}/gi," "); // reemplazamos los puntos por espacios
    txt = txt.replace(/(^\s*)|(\s*$)/gi,"");
    txt = txt.replace(/[ ]{2,}/gi," ");
    txt = txt.replace(/\n /,"\n");
    
    return txt.split(" ")[pos-1];
}

/**
 * 
 * @param {} palabra
 * @return {}
 */
function quitaEspaciosSignosYPuntos(texto) {
    return texto.replace(/[,]{1,}/gi," ") // reemplazamos las comas por espacios
                .replace(/[\.]{1,}/gi," ") // reemplazamos los puntos por espacios
                .replace(/(^\s*)|(\s*$)/gi,"")
                .replace(/[ ]{2,}/gi," ")
                .replace(/\n /,"\n");
    return txt;
}


function quitaPalabrasInconvenientesPersonaFisica(texto) {
    
    return texto.replace(/\b(BUEI)\b/gi, 'BUEX')
                .replace(/\b(BUEY)\b/gi, 'BUEX')
                .replace(/\b(CACA)\b/gi, 'CACX')
                .replace(/\b(CACO)\b/gi, 'CACX')
                .replace(/\b(CAGA)\b/gi, 'CAGX')
                .replace(/\b(CAGO)\b/gi, 'CAGX')
                .replace(/\b(CAKA)\b/gi, 'CAKX')
                .replace(/\b(COGE)\b/gi, 'COGX')
                .replace(/\b(COJA)\b/gi, 'COJX')
                .replace(/\b(COJE)\b/gi, 'COJX')
                .replace(/\b(COJI)\b/gi, 'COJX')
                .replace(/\b(COJO)\b/gi, 'COJX')
                .replace(/\b(CULO)\b/gi, 'CULX')
                .replace(/\b(FETO)\b/gi, 'FETX')
                .replace(/\b(GUEY)\b/gi, 'GUEX')
                .replace(/\b(JOTO)\b/gi, 'JOTX')
                .replace(/\b(KACA)\b/gi, 'KACX')
                .replace(/\b(KACO)\b/gi, 'KACX')
                .replace(/\b(KAGA)\b/gi, 'KAGX')
                .replace(/\b(KAGO)\b/gi, 'KAGX')
                .replace(/\b(KOGE)\b/gi, 'KOGX')
                .replace(/\b(KOJO)\b/gi, 'KOJX')
                .replace(/\b(KAKA)\b/gi, 'KAKX')
                .replace(/\b(KULO)\b/gi, 'KULX')
                .replace(/\b(MAME )\b/gi, 'MAMX')
                .replace(/\b(MAMO)\b/gi, 'MAMX')
                .replace(/\b(MEAR)\b/gi, 'MEAX')
                .replace(/\b(MEON)\b/gi, 'MEOX')
                .replace(/\b(MION)\b/gi, 'MIOX')
                .replace(/\b(MOCO)\b/gi, 'MOCX')
                .replace(/\b(MULA)\b/gi, 'MULX')
                .replace(/\b(PEDA)\b/gi, 'PEDX')
                .replace(/\b(PEDO)\b/gi, 'PEDX')
                .replace(/\b(PENE)\b/gi, 'PENX')
                .replace(/\b(PUTA)\b/gi, 'PUTX')
                .replace(/\b(PUTO)\b/gi, 'PUTX')
                .replace(/\b(QULO)\b/gi, 'QULX')
                .replace(/\b(RATA)\b/gi, 'RATX')
                .replace(/\b(RUIN)\b/gi, 'RUIX');
}

function quitaAbreviaturasPersonaFisica(texto) {
    
    return texto.replace('-', '')
//                .replace(/\b(A C)\b/gi, '')
                .replace(/\b(A EN P)\b/gi, '').replace(/\b(A  EN P)\b/gi, '')
//                .replace(/\b(S DE RL)\b/gi, '').replace(/\b(S DE R L)\b/gi, '').replace(/\b(S  DE R L)\b/gi, '')
                .replace(/\b(COMPAÑÍA)\b/gi, '').replace(/\b(COMPAÑIA)\b/gi, '').replace(/\b(COMPANIA)\b/gi, '').replace(/\b(COMPA&ÍA)\b/gi, '')
                .replace(/\b(CIA)\b/gi, '').replace(/\b(CÍA)\b/gi, '')
                .replace(/\b(SOC)\b/gi, '')
                .replace(/\b(COOP)\b/gi, '')
                .replace(/\b(S EN C POR A)\b/gi, '').replace(/\b(S  EN C POR A)\b/gi, '')
//                .replace(/\b(S EN NC)\b/gi, '').replace(/\b(S EN N C)\b/gi, '')
                .replace(/\b(SA DE CV)\b/gi, '').replace(/\b(S A DE C V)\b/gi, '').replace(/\b(S A  DE C V)\b/gi, '').replace(/\b(S A)\b/gi, '').replace(/\b(SA)\b/gi, '')
//                .replace(/\b(PARA)\b/gi, '')
//                .replace(/\b(POR)\b/gi, '')
//                .replace(/\b(AL)\b/gi, '')
                .replace(/\b(SC)\b/gi, '').replace(/\b(S C)\b/gi, '').replace(/\b(SCS)\b/gi, '').replace(/\b(S C S)\b/gi, '')
//                .replace(/\b(SCL)\b/gi, '').replace(/\b(S C L)\b/gi, '')
//                .replace(/\b(SNC)\b/gi, '').replace(/\b(S N C)\b/gi, '')
//                .replace(/\b(OF)\b/gi, '')
//                .replace(/\b(COMPANY)\b/gi, '')
                .replace(/\b(MC)\b/gi, '').replace(/\b(MAC)\b/gi, '')
                .replace(/\b(VAN)\b/gi, '')
                .replace(/\b(VON)\b/gi, '')
                .replace(/\b(MI)\b/gi, '')
                .replace(/\b(SRL MI)\b/gi, '')
//                .replace(/\b(SA MI)\b/gi, '')
//                .replace(/\b(SRL CV MI)\b/gi, '')
                // Articulos:
//                .replace(/\b(CON)\b/gi, '')
                .replace(/\b(DE)\b/gi, '')
                .replace(/\b(DEL)\b/gi, '')
//                .replace(/\b(EL)\b/gi, '')
//                .replace(/\b(EN)\b/gi, '')
                .replace(/\b(LA)\b/gi, '')
                .replace(/\b(LOS)\b/gi, '')
                .replace(/\b(LAS)\b/gi, '')
                .replace(/\b(SUS)\b/gi, '')
                .replace(/\b(THE)\b/gi, '')
                .replace(/\b(AND)\b/gi, '')
                .replace(/\b(CO)\b/gi, '')
                .replace(/\b(Y)\b/gi, '')
                .replace(/\b(A)\b/gi, '');
                
}

function quitaAbreviaturasPersonaMoral(texto) {
	
	return texto.replace('-', '')
            	//.replace('A C ', '')
	            .replace(/\b(A C)\b/gi, '')
	            //.replace('A  EN P ', '')
	            .replace(/\b(A  EN P)\b/gi, '')
                //.replace('S DE RL', '').replace('S DE R L', '').replace('S  DE R L', '')
                .replace(/\b(S DE RL)\b/gi, '').replace(/\b(S DE R L)\b/gi, '').replace(/\b(S  DE R L)\b/gi, '')
                //.replace('COMPAÑÍA ', '').replace('COMPAÑIA ', '').replace('COMPANIA ', '')
                .replace(/\b(COMPAÑÍA)\b/gi, '').replace(/\b(COMPAÑIA)\b/gi, '').replace(/\b(COMPANIA)\b/gi, '')
                //.replace('CIA ', '').replace('CÍA ', '')
                .replace(/\b(CIA)\b/gi, '').replace(/\b(CÍA)\b/gi, '')
                //.replace('SOCIEDAD ', '')
                .replace(/\b(SOCIEDAD)\b/gi, '')
                //.replace('COOPERATIVA ', '')
                .replace(/\b(COOPERATIVA)\b/gi, '')
                //.replace('S EN C POR A ', '').replace('S  EN C POR A ', '')
                .replace(/\b(S EN C POR A)\b/gi, '').replace(/\b(S  EN C POR A)\b/gi, '')
                //.replace('S EN NC ', '').replace('S EN N C  ', '')
                .replace(/\b(S EN NC)\b/gi, '').replace(/\b(S EN N C)\b/gi, '')
                //.replace('S A  DE C V ', '').replace('S A', '')
                .replace(/\b(SA DE CV)\b/gi, '').replace(/\b(S A DE C V)\b/gi, '').replace(/\b(S A  DE C V)\b/gi, '').replace(/\b(S A)\b/gi, '')
                //.replace('PARA ', '')
                .replace(/\b(PARA)\b/gi, '')
                //.replace('POR ', '')
                .replace(/\b(POR)\b/gi, '')
                //.replace('AL ', '')
                .replace(/\b(AL)\b/gi, '')
                //.replace('S C ', '').replace('S C S ', '')
                .replace(/\b(S C)\b/gi, '').replace(/\b(S C S)\b/gi, '')
                //.replace('SCL ', '').replace('S C L ', '')
                .replace(/\b(SCL)\b/gi, '').replace(/\b(S C L)\b/gi, '')
                //.replace('SNC ', '').replace('S N C ', '')
                .replace(/\b(SNC)\b/gi, '').replace(/\b(S N C)\b/gi, '')
                //.replace('OF ', '')
                .replace(/\b(OF)\b/gi, '')
                //.replace('COMPANY ', '')
                .replace(/\b(COMPANY)\b/gi, '')
                //.replace('MC ', '')
                .replace(/\b(MC)\b/gi, '')
                //.replace('VON ', '')
                .replace(/\b(VON)\b/gi, '')
                //.replace('MI ', '')
                .replace(/\b(MI)\b/gi, '')
                //.replace('SRL CV ', '')
                .replace(/\b(SRL CV)\b/gi, '')
                //.replace('SA MI ', '')
                .replace(/\b(SA MI)\b/gi, '')
                //.replace('SRL CV MI ', '')
                .replace(/\b(SRL CV MI)\b/gi, '')
                // Articulos:
                //.replace(' DE ', '')
                .replace(/\b(DE)\b/gi, '')
                //.replace(' DEL ', '')
                .replace(/\b(DEL)\b/gi, '')
                //.replace(' EL ', '')
                .replace(/\b(EL)\b/gi, '')
                //.replace(' LA ', '')
                .replace(/\b(LA)\b/gi, '')
                //.replace(' LOS ', '')
                .replace(/\b(LOS)\b/gi, '')
                //.replace(' LAS ', '')
                .replace(/\b(LAS)\b/gi, '')
                //.replace(' E ', '') // TODO: Crear una funcion que busque coincidencias por palabra completa
                .replace(/\b(E)\b/gi, '')
                //.replace(' Y ', '')
                .replace(/\b(Y)\b/gi, '');
                //.replace(/(^\s*)|(\s*$)/gi,"")
                
}

function reemplazarCaracteresEspecialesPersonaFisica(txt) {
    
    // REGLA 10
    // Cuando aparezcan formando parte del nombre, apellido paterno y apellido materno los caracteres especiales, 
	// éstos deben de excluirse para el cálculo del homónimo y del dígito verificador. 
	// Los caracteres se interpretarán, sí y sólo si, están en forma individual 
	// dentro del nombre, apellido paterno y apellido materno. 
    txt = txt.replace(/\u0027(?=\s|$)/gi, 'APOSTROFE').replace(/\u2018(?=\s|$)/gi, 'APOSTROFE').replace(/\u2019(?=\s|$)/gi, 'APOSTROFE')
             //.replace(/\.(?=\s|$)/gi, 'PUNTO')// TODO: buscar expresion regular que lo arregle
             ;
    // Ahora se reemplazan los caracteres especiales que no vienen en forma individual:
    txt = txt.replace(/\u0027/gi, '').replace(/\u2018/gi, '').replace(/\u2019/gi, '')
             //.replace(/\.(?=\s|$)/gi, '')// TODO: buscar expresion regular que lo arregle
             ;
    return txt;
}

function reemplazarCaracteresEspecialesPersonaMoral(txt) {
	
	// REGLA 12
    // Cuando aparezcan formando parte de la denominación o razón social los caracteres especiales, 
	// éstos deben de excluirse para el cálculo del homónimo y del dígito verificador. 
	// Los caracteres se interpretarán, sí y sólo si, están en forma individual dentro del texto de la denominación o razón social.
	txt = txt.replace(/@(?=\s|$)/gi, 'ARROBA')
	         .replace(/\u0027(?=\s|$)/gi, 'APOSTROFE').replace(/\u2018(?=\s|$)/gi, 'APOSTROFE').replace(/\u2019(?=\s|$)/gi, 'APOSTROFE')
	         .replace(/\%(?=\s|$)/gi, 'PORCIENTO')
	         .replace(/#(?=\s|$)/gi, 'NUMERO')
	         .replace(/!(?=\s|$)/gi, 'ADMIRACION')
	         //.replace(/\.(?=\s|$)/gi, 'PUNTO')// TODO: buscar expresion regular que lo arregle
	         .replace(/\$(?=\s|$)/gi, 'PESOS')
	         .replace(/\x22(?=\s|$)/gi, 'COMILLAS').replace(/\u201C(?=\s|$)/gi, 'COMILLAS').replace(/\u201D(?=\s|$)/gi, 'COMILLAS')
	         .replace(/-(?=\s|$)/gi, 'GUION')
	         .replace(/\/(?=\s|$)/gi, 'DIAGONAL')
	         .replace(/\+(?=\s|$)/gi, 'SUMA')
	         .replace(/\((?=\s|$)/gi, 'ABRE PARENTESIS')
	         .replace(/\)(?=\s|$)/gi, 'CIERRA PARENTESIS');
	// Ahora se reemplazan los caracteres especiales que no vienen en forma individual:
	txt = txt.replace(/@/gi, '')
	         .replace(/\u0027/gi, '').replace(/\u2018/gi, '').replace(/\u2019/gi, '')
             .replace(/\%/gi, '')
             .replace(/#/gi, '')
             .replace(/!/gi, '')
             //.replace(/\.(?=\s|$)/gi, '')// TODO: buscar expresion regular que lo arregle
             .replace(/\$/gi, '')
             .replace(/\x22/gi, '').replace(/\u201C/gi, '').replace(/\u201D/gi, '')
             .replace(/-/gi, '')
             .replace(/\//gi, '')
             .replace(/\+/gi, '')
             .replace(/\(/gi, '')
             .replace(/\)/gi, '');
    return txt;
}


/**
 * Indica si la letra es vocal
 * @param {} letra
 * @return {Boolean} true si es vocal, false si no lo es
 */
function esVocal(letra) {
    if (letra == 'A' || letra == 'E' || letra == 'I' || letra == 'O'
            || letra == 'U' || letra == 'a' || letra == 'e' || letra == 'i'
            || letra == 'o' || letra == 'u')
        return true;
    else
        return false;
}



/**
 * right padding s with c to a total of n chars
 * Fuente: http://eureka.ykyuen.info/2011/08/23/javascript-leftright-pad-a-string/
 * @param {} s
 * @param {} c
 * @param {} n
 * @return {}
 */
function padding_right(s, c, n) {
  if (! s || ! c || s.length >= n) {
    return s;
  }
  var max = (n - s.length)/c.length;
  for (var i = 0; i < max; i++) {
    s += c;
  }
  return s;
}

