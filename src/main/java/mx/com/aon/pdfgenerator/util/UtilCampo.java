package mx.com.aon.pdfgenerator.util;

import com.lowagie.text.Font;

public class UtilCampo {
	
	/**
     * Agrega un objeto campo a un arreglo de objetos Campos.
     * 
     * @return Arreglo de objetos Campo.
     * @param valor Atributo del objeto Campo a Agregar.
     * @param nombre Atributo del Objeto Campo a Agregar.
     * @param campos Arreglo de objetos campos sobre el que se agrega un objeto Campo.
     */
    public static Campo[] addCampo(Campo[] campos, String nombre, String valor) {

        Campo[] tempo = new Campo[campos.length + 1];
        tempo[campos.length] = new Campo(nombre, valor);
        System.arraycopy(campos, 0, tempo, 0, campos.length);

        return tempo;
    }
	
    
    public static Campo[] addCampo(Campo[] campos, String nombre, String valor, String font) {

        Campo[] tempo = new Campo[campos.length + 1];
        tempo[campos.length] = new Campo(nombre, valor, font);
        System.arraycopy(campos, 0, tempo, 0, campos.length);

        return tempo;
    }
}
