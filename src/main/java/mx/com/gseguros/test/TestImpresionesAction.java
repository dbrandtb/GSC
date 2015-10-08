package mx.com.gseguros.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.Sides;

import mx.com.aon.core.web.PrincipalCoreAction;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
@ParentPackage(value="default")
@Namespace("/test")
public class TestImpresionesAction extends PrincipalCoreAction {

	private static final long serialVersionUID = -3861435458381281429L;
	
	final static Logger logger = LoggerFactory.getLogger(TestImpresionesAction.class);
	
	private List<PrintService> printServices;
	
	/**
	 * Lista las impresoras disponibles desde el servidor
	 * @return
	 * @throws Exception
	 */
	@Action(value="listaImpresorasServer",
			results={@Result(name="success", type="json")}
	)
	public String listaImpresorasServer() throws Exception {
		
		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
		
        logger.debug("Printer Services found:");
        printService(services);
        
        printServices = Arrays.asList(services);
		
		return SUCCESS;
	}
	
	public static void main(String[] args) {
		
		PrintService[] services2 = PrintServiceLookup.lookupPrintServices(null, null);
        logger.debug("Printer Services found:");
        printService(services2);
		
		// Input the file
		FileInputStream textStream = null; 
		try {
			textStream = new FileInputStream("E:\\R\\Pictures\\Beatles-Hard-Days-Night-cover.jpg"); 
		} catch (FileNotFoundException ffne) {
			ffne.printStackTrace();
		} 
		if (textStream == null) { 
			System.out.println("no existe el documento");
		    return; 
		} else {
			System.out.println("SI existe el documento");
		}
		// Set the document type
		DocFlavor myFormat = DocFlavor.INPUT_STREAM.JPEG;
		// Create a Doc
		Doc myDoc = new SimpleDoc(textStream, myFormat, null); 
		// Build a set of attributes
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet(); 
		aset.add(new Copies(5)); 
		aset.add(MediaSize.ISO.A4.getMediaSizeName());
		aset.add(Sides.DUPLEX); 
		// discover the printers that can print the format according to the
		// instructions in the attribute set
		PrintService [] //services = PrintServiceLookup.lookupDefaultPrintService();//PrintServiceLookup.lookupPrintServices(myFormat, aset);
		services = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.PDF, null);
		System.out.println("***");
		printService(services);
		System.out.println("***");
		
		DocPrintJob job = services2[1].createPrintJob(); 
		try { 
        	System.out.println("Antes de print");
            job.print(myDoc, aset);
            System.out.println("Despues de print");
        } catch (PrintException pe) {
        	pe.printStackTrace();
        } 
		//System.out.println("services=" + services);
		//System.out.println("services.length=" + services.length);
		/*
		// Create a print job from one of the print services
		if (services.length > 0) { 
		        DocPrintJob job = services[0].createPrintJob(); 
		        try { 
		        	System.out.println("Antes de print");
		            job.print(myDoc, aset);
		            System.out.println("Despues de print");
		        } catch (PrintException pe) {
		        	pe.printStackTrace();
		        } 
		}
		*/
	}
	
	private static void printService(PrintService[] services) {
        if (services!=null && services.length>0) {
            for (int i = 0; i < services.length; i++) {
                logger.debug("{}", services[i]);
            }
        }
    }

	public List<PrintService> getPrintServices() {
		return printServices;
	}

	public void setPrintServices(List<PrintService> printServices) {
		this.printServices = printServices;
	}
	
}