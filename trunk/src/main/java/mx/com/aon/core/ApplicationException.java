/*
 * AON
 * 
 * Creado el 24/01/2008 11:36:37 a.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.core;

/**
 * ApplicationException
 * 
 * <pre>
 *  Clase excepcion para manejo propio de la aplicacion
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public class ApplicationException extends Exception {

	public ApplicationException() {
		super();
	}

	public ApplicationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ApplicationException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * UID por defecto
	 */
	private static final long serialVersionUID = 7137515794898926976L;

	/**
	 * Unico constructor para manejo de mensajes
	 * @param message
	 */
	public ApplicationException(String message) {
		super(message);
	}

}
