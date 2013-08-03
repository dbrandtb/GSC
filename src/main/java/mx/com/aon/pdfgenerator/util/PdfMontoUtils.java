package mx.com.aon.pdfgenerator.util;

public class PdfMontoUtils {
	
	
public static String convierteMontoALetra(String montoString){
		
		montoString = montoString.replaceAll(",", "");
    	String result = "";
    	 
    	String [] montoSplit = montoString.split("\\.");
    	
    	String patronU = "\\d{1}(\\.\\d{1,2})?";
    	String patronD = "\\d{2}(\\.\\d{1,2})?";
    	String patronC = "\\d{3}(\\.\\d{1,2})?";
    	String patronMil = "\\d{4,6}(\\.\\d{1,2})?";
    	String patronMillon = "\\d{7,9}(\\.\\d{1,2})?";
    	
    	String [] unidades = { "CERO","UNO", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE"};
    	
    	if(montoString.matches(patronU)){
    		System.out.println(">>>>>>>unidades");
    		result = unidades[Integer.parseInt(montoSplit[0])];
    		
    	}else if(montoString.matches(patronD)){
    		result = monto_LetraD(montoString);
    		
    	}else if(montoString.matches(patronC)){    		
    		result = monto_LetraC(montoString);
    		
    	}else if(montoString.matches(patronMil)){    		
    		result = monto_LetraMil(montoString);
    	}
    	else if(montoString.matches(patronMillon)){    		
    		result = monto_LetraMillon(montoString);
    	}
    	
    	return result + " PESOS "+ montoSplit[1]+"/100 M.N.";
    }
    
    /**
     * @param montoString
     * @return
     */
    public static String monto_LetraD(String montoString){
    	
    	String [] montoSplit = montoString.split("\\.");
    	String result = "";
    	String posicionUno = "";
    	String posicionDos = "";
    	
    	String [] unidades = { "CERO","UN", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE"};
    	String [][] decenas = { 
    							{"", "", "", "", "", "", "", "", "", ""}, 
    			                {"DIEZ","ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", "DIECISEIS", "DIECISIETE", "DIECIOCHO", "DIECINUEVE"},
    			                {"", "", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"},
    			                {"", "", "VEINTI", "TREINTA Y ", "CUARENTA Y ", "CINCUENTA Y ", "SESENTA Y ", "SETENTA Y ", "OCHENTA Y ", "NOVENTA Y "}
    						  };
    	
		if(montoSplit[0].startsWith("1")){
			posicionDos = montoSplit[0].substring(1);
			//montoSplit[0] = montoSplit[0].replaceAll("1", "");
			result = decenas[1][Integer.parseInt(posicionDos)];
			return result;
		}

		posicionUno = montoSplit[0].substring(0,1);
		System.out.println(">>>>>>> posicionUno ::: "+ posicionUno);//2
		
		if(montoSplit[0].startsWith(posicionUno)){
			
			posicionDos = montoSplit[0].substring(1);
			
			System.out.println(">>>>>>> posicionDos ::: "+ posicionDos);//1
			if("0".equalsIgnoreCase(posicionDos)){				
				result = decenas[2][Integer.parseInt(posicionUno)];
			}else{
				result = decenas[3][Integer.parseInt(posicionUno)] + unidades[Integer.parseInt(posicionDos)];
			}
		}
		return result;
    }
    
    
    public static String monto_LetraC(String montoString){
    	String result = "";
    	String parteUnoCent = "";
    	String parteDosCent = "";
    	String [] montoSplit = montoString.split("\\.");
    	
    	String [] centenas = { "", "CIENTO ", "DOSCIENTOS ", "TRESCIENTOS ", "CUATROCIENTOS ", "QUINIENTOS ", "SEISCIENTOS ", "SETECIENTOS ", "OCHOCIENTOS ", "NOVECIENTOS "};
    	
    	if(montoString. matches("100(\\.\\d{1,2})?")){
    		result = "CIEN";
    	}else{
    		parteUnoCent = montoSplit[0].substring(0,1);
    		parteDosCent = montoSplit[0].substring(1);
    		System.out.println(">>>>>>> parteUnoCent ::: "+ parteUnoCent);    		
    		System.out.println(">>>>>>> parteDosCent ::: "+ parteDosCent);
    		result = centenas[Integer.parseInt(parteUnoCent)] + monto_LetraD(parteDosCent);
    	}

		return result;
    }
    
    public static String monto_LetraMil(String montoString){
    	String result = "";
    	String parteUnoM = "";
    	String parteDosM = "";
    
    	String [] montoSplit = montoString.split("\\.");
    	
    	String [] miles = { "CERO","", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE"};
    	    	
    	if(montoString.matches("\\d{4}(\\.\\d{1,2})?")){
    		           
    		parteUnoM = montoSplit[0].substring(0,1);    		
    		parteDosM = montoSplit[0].substring(1);
    		
    		System.out.println(">>>>>>> parteUnoM ::: "+ parteUnoM);
    		System.out.println(">>>>>>> parteDosM ::: "+ parteDosM);
    		
    		result = miles[Integer.parseInt(parteUnoM)] +" MIL "+ monto_LetraC(parteDosM);
    		
    	}else if(montoString.matches("\\d{5}(\\.\\d{1,2})?")){
    		
    		parteUnoM = montoSplit[0].substring(0,2);    		
    		parteDosM = montoSplit[0].substring(2);
    		
    		System.out.println(">>>>>>> parteUnoM ::: "+ parteUnoM);
    		System.out.println(">>>>>>> parteDosM ::: "+ parteDosM);
    		
    		result = monto_LetraD(parteUnoM) +" MIL "+ monto_LetraC(parteDosM);
    		
    	}else if(montoString.matches("\\d{6}(\\.\\d{1,2})?")){
    		
    		parteUnoM = montoSplit[0].substring(0,3);    		
    		parteDosM = montoSplit[0].substring(3);
    		
    		System.out.println(">>>>>>> parteUnoM ::: "+ parteUnoM);
    		System.out.println(">>>>>>> parteDosM ::: "+ parteDosM);
    		
    		result = monto_LetraC(parteUnoM) +" MIL "+ monto_LetraC(parteDosM);    		
    	}
    	    	
		return result;
    }
    
    public static String monto_LetraMillon(String montoString){
    	String result = "";
    	String parteUnoM = "";
    	String parteDosM = "";
    	String parteTresM = "";
    
    	String [] montoSplit = montoString.split("\\.");
    	
    	String [] millones = { "CERO","UN", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE"};
    	String millions = "";
    	String miles = "";
    	if(montoString.matches("\\d{7}(\\.\\d{1,2})?")){
    		           
    		parteUnoM = montoSplit[0].substring(0,1);    		
    		parteDosM = montoSplit[0].substring(1,4);
    		parteTresM = montoSplit[0].substring(4);
    		
    		if("000".equalsIgnoreCase(parteDosM)){
    				miles = "";
    		}else{
    			miles = " MIL ";
    		}
    		System.out.println(">>>>>>> parteUnoM ::: "+ parteUnoM);
    		System.out.println(">>>>>>> parteDosM ::: "+ parteDosM);
    		System.out.println(">>>>>>> parteTresM ::: "+ parteDosM);
    		
    		if("1".equalsIgnoreCase(parteUnoM)){
    			millions = " MILLON ";
    		}else{
    			millions = " MILLONES ";
    		}
    		result = millones[Integer.parseInt(parteUnoM)] + millions + monto_LetraC(parteDosM)+ miles + monto_LetraC(parteTresM);
    		
    	}else if(montoString.matches("\\d{8}(\\.\\d{1,2})?")){
    		
    		parteUnoM = montoSplit[0].substring(0,2);    		
    		parteDosM = montoSplit[0].substring(2,5);
    		parteTresM = montoSplit[0].substring(5);
    		
    		if("000".equalsIgnoreCase(parteDosM)){
    				miles = "";
    		}else{
    			miles = " MIL ";
    		}
    		
    		System.out.println(">>>>>>> parteUnoM ::: "+ parteUnoM);
    		System.out.println(">>>>>>> parteDosM ::: "+ parteDosM);
    		System.out.println(">>>>>>> parteTresM ::: "+ parteDosM);
    		
    		result = monto_LetraD(parteUnoM) + " MILLONES " + monto_LetraC(parteDosM)+ miles + monto_LetraC(parteTresM);
    		
    	}else if(montoString.matches("\\d{6}(\\.\\d{1,2})?")){
    		
    		parteUnoM = montoSplit[0].substring(0,3);    		
    		parteDosM = montoSplit[0].substring(3);
    		
    		System.out.println(">>>>>>> parteUnoM ::: "+ parteUnoM);
    		System.out.println(">>>>>>> parteDosM ::: "+ parteDosM);
    		
    		result = monto_LetraC(parteUnoM) +" MIL "+ monto_LetraC(parteDosM);    		
    	}
    	    	
		return result;
    }
}
