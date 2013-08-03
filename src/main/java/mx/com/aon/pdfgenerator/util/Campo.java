package mx.com.aon.pdfgenerator.util;

import java.io.Serializable;

import com.lowagie.text.Font;

public class Campo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7298293990987289096L;

    public Campo() {
    }

    private String nombre;
    private String valor;

    private String tipo;
    private String font;

    

	public Campo(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }
	/*
    public Campo(String nombre, String valor, String tipo) {
        this.nombre = nombre;
        this.valor = valor;
        this.tipo = tipo;
    }
    */
    public Campo(String nombre, String valor, String font) {
        this.nombre = nombre;
        this.valor = valor;
        this.font = font;
    }
       
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
       
        sb.append("--- Nombre: ");
        sb.append(this.nombre);
        sb.append("   Valor: ");
        sb.append(this.valor);
        return sb.toString();
    }

}