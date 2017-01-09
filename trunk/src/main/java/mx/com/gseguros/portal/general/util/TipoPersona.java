package mx.com.gseguros.portal.general.util;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public enum TipoPersona {
    FISICA("F"),
    MORAL("M"),
    REGIMEN_SIMPLIFICADO("S");
    
    String tipoPersona;
    private TipoPersona(String tipoPersona){
        this.tipoPersona=tipoPersona;
    }
    
    public String getTipoPersona(){
        return tipoPersona;
    }
}
