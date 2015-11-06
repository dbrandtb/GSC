package mx.com.gseguros.portal.general.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaTray;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.Sides;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.general.service.ImpresionService;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ImpresionServiceImpl implements ImpresionService {
	
	final static Logger logger = LoggerFactory.getLogger(ImpresionServiceImpl.class);

	@Override
	public void imprimeDocumento(String documento, int iPrinter, int numCopias, Integer mediaId) throws Exception {
		
		//PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
		
		// Discover the printers that can print the format according to the instructions in the attribute set
		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        logger.debug("Printer Services found:");
        printService(services);
        logger.debug("End Printer Services");
        
		// Input the file
		FileInputStream textStream = null; 
		try {
			textStream = new FileInputStream(documento);
		} catch (FileNotFoundException ffne) {
			logger.error(ffne.getMessage(), ffne);
			throw new Exception("El archivo no existe: " + documento, ffne);
		}
		/*
		if (textStream == null) {
			logger.warn("No existe el documento: {}", documento);
		} else {
			logger.info("Si existe el documento: {}", documento);
		}
		*/
		
		String extensionArchivo = FilenameUtils.getExtension(documento);
		
		Doc myDoc = null;
		DocFlavor myFormat = null;
		if(extensionArchivo.equalsIgnoreCase("PDF")){
			myFormat = DocFlavor.INPUT_STREAM.PDF;
			myDoc = new SimpleDoc(textStream, myFormat, null);
		}else if(extensionArchivo.equalsIgnoreCase("JPEG")){
			myFormat = DocFlavor.INPUT_STREAM.JPEG;
			myDoc = new SimpleDoc(textStream, myFormat, null);
		}else if(extensionArchivo.equalsIgnoreCase("PNG")){
			myFormat = DocFlavor.INPUT_STREAM.PNG;
			myDoc = new SimpleDoc(textStream, myFormat, null);
		}
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet(); 
		aset.add(new Copies(numCopias)); 
		//aset.add(MediaSize.NA.ISO.A4.getMediaSizeName());
		aset.add(MediaSize.ISO.A4.getMediaSizeName());
		aset.add(Sides.DUPLEX);
		
		
		/*
		float printableX = someSize;
		float printableY = someSize;
		printSet = new HashPrintRequestAttributeSet();
		mediaSizeName = MediaSize.findMedia(printableX,printableY,MediaPrintableArea.INCH);
		printSet.add(mediaSizeName);
		*/
		logger.debug("Printer Trays found:");
		printTrays(services[iPrinter], myFormat);
		logger.debug("End Printer Trays");
		
		// Se se envia el codigo de la bandeja de impresion:
		if(mediaId != null) {
			
			// we store all the tray in a hashmap
	        Map<Integer, Media> trayMap = new HashMap<Integer, Media>(10);

	        // we chose something compatible with the printable interface
	        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
	        logger.debug("Service: {}", services[iPrinter]);
	        
	        /////////// /////////// ///////////
	        
	        // we retrieve all the supported attributes of type Media
	        // we can receive MediaTray, MediaSizeName, ...
	        Object o = services[iPrinter].getSupportedAttributeValues(Media.class, flavor, null);
	        if (o != null && o.getClass().isArray()) {
	            for (Media media : (Media[]) o) {
	                // we collect the MediaTray available
	                if (media instanceof MediaTray) {
	                    logger.debug("{} : {} - {}", media.getValue(), media, media.getClass().getName());
	                    trayMap.put(media.getValue(), media);
	                }
	            }
	        }

	        // Tray target id:
	        MediaTray selectedTray = (MediaTray) trayMap.get(Integer.valueOf(mediaId));
	        logger.debug("Selected tray : {}", selectedTray.toString());
	        
	        // we have to add the MediaTray selected as attribute
	        aset.add(selectedTray);
		}
		////////////////////////////////////////
		
		DocPrintJob job = services[iPrinter].createPrintJob(); 
		try { 
        	logger.info("Antes de imprimir");
            job.print(myDoc, aset);
            logger.info("Despu�s de imprimir");
        } catch (PrintException pe) {
        	logger.error(pe.getMessage(), pe);
        }
		
	}
	
	
	/*
	public void imprimeDocumento(List<String> documentos, String nombreImpresora, int numCopias) throws Exception {
		
	}
	*/
	
	
	private static void printService(PrintService[] services) {
        if (services!=null && services.length>0) {
            for (int i = 0; i < services.length; i++) {
                logger.debug("{}", services[i]);
            }
        }
    }
	
	
	private static void printTrays(PrintService service, DocFlavor flavor) {
		// we retrieve all the supported attributes of type Media
        // we can receive MediaTray, MediaSizeName, ...
        Object o = service.getSupportedAttributeValues(Media.class, flavor, null);
        if (o != null && o.getClass().isArray()) {
            for (Media media : (Media[]) o) {
                // we collect the MediaTray available
                if (media instanceof MediaTray) {
                    logger.debug("{} : {} - {}", media.getValue(), media, media.getClass().getName());
                }
            }
        }
	}
	
	
	public static void main(String[] args) {
		//System.out.println(FilenameUtils.getExtension("/tmp/poliza.txt"));
		
		/*
		ImpresionServiceImpl imprSrvImpl = new ImpresionServiceImpl();
		try {
			imprSrvImpl.imprimeDocumento("/biosnet logo.png", 1, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		/*
		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        logger.debug("Printer Services found:");
        printService(services);
        logger.debug("End Printer Services");
        
        for (PrintService printService : services) {
            String name = printService.getName();
            logger.debug("Name = " + name);
        }
        */
		ImpresionServiceImpl imprSrvImpl = new ImpresionServiceImpl();
		try {
			imprSrvImpl.imprimeDocumento("/biosnet logo.png", "Microsoft XPS Document Writer", 3, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	@Override
	public void imprimeDocumento(String documento, String nombreImpresora, int numCopias, Integer mediaId) throws Exception {
		
		PrintService printSrv = null;
		
		// Discover the printers that can print the format according to the instructions in the attribute set
		//PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
		
		// selection of all print services by printer name
		// the printer is selected
        AttributeSet attSet = new HashAttributeSet();
        attSet.add(new PrinterName(nombreImpresora, null));
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, attSet);
        logger.debug("Printer Services found:");
        printService(services);
        logger.debug("End Printer Services");
        
        for (PrintService printService : services) {
        	if(nombreImpresora.trim().equalsIgnoreCase(printService.getName())) {
        		printSrv = printService;
        		break;
        	}
        }
        
        if(printSrv == null) {
        	throw new ApplicationException("No existe la impresora indicada: " + nombreImpresora);
        }
        
		// Input the file
		FileInputStream textStream = null; 
		try {
			textStream = new FileInputStream(documento);
		} catch (FileNotFoundException ffne) {
			logger.error(ffne.getMessage(), ffne);
			throw new ApplicationException("El archivo no existe: " + documento, ffne);
		}
		/*
		if (textStream == null) {
			logger.warn("No existe el documento: {}", documento);
		} else {
			logger.info("Si existe el documento: {}", documento);
		}
		*/
		
		String extensionArchivo = FilenameUtils.getExtension(documento);
		
		Doc myDoc = null;
		DocFlavor myFormat = null;
		if(extensionArchivo.equalsIgnoreCase("PDF")) {
			myFormat = DocFlavor.INPUT_STREAM.PDF;
			myDoc = new SimpleDoc(textStream, myFormat, null);
		} else if(extensionArchivo.equalsIgnoreCase("JPEG")) {
			myFormat = DocFlavor.INPUT_STREAM.JPEG;
			myDoc = new SimpleDoc(textStream, myFormat, null);
		} else if(extensionArchivo.equalsIgnoreCase("PNG")) {
			myFormat = DocFlavor.INPUT_STREAM.PNG;
			myDoc = new SimpleDoc(textStream, myFormat, null);
		}
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet(); 
		aset.add(new Copies(numCopias)); 
		//aset.add(MediaSize.NA.ISO.A4.getMediaSizeName());
		aset.add(MediaSize.ISO.A4.getMediaSizeName());
		aset.add(Sides.DUPLEX);
		
		/*
		float printableX = someSize;
		float printableY = someSize;
		printSet = new HashPrintRequestAttributeSet();
		mediaSizeName = MediaSize.findMedia(printableX,printableY,MediaPrintableArea.INCH);
		printSet.add(mediaSizeName);
		*/
		logger.debug("Printer Trays found:");
		printTrays(printSrv, myFormat);
		logger.debug("End Printer Trays");
		
		// Se se envia el codigo de la bandeja de impresion:
		if(mediaId != null) {
			
			// we store all the tray in a hashmap
	        Map<Integer, Media> trayMap = new HashMap<Integer, Media>(10);

	        // we chose something compatible with the printable interface
	        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
	        logger.debug("Service: {}", printSrv);
	        
	        // we retrieve all the supported attributes of type Media
	        // we can receive MediaTray, MediaSizeName, ...
	        Object o = printSrv.getSupportedAttributeValues(Media.class, flavor, null);
	        if (o != null && o.getClass().isArray()) {
	            for (Media media : (Media[]) o) {
	                // we collect the MediaTray available
	                if (media instanceof MediaTray) {
	                    logger.debug("{} : {} - {}", media.getValue(), media, media.getClass().getName());
	                    trayMap.put(media.getValue(), media);
	                }
	            }
	        }

	        // Tray target id:
	        MediaTray selectedTray = (MediaTray) trayMap.get(Integer.valueOf(mediaId));
	        logger.debug("Selected tray : {}", selectedTray.toString());
	        
	        // we have to add the MediaTray selected as attribute
	        aset.add(selectedTray);
		}
		
		DocPrintJob job = printSrv.createPrintJob(); 
		try { 
        	logger.info("Antes de imprimir");
            job.print(myDoc, aset);
            logger.info("Despu�s de imprimir");
        } catch (PrintException pe) {
        	logger.error(pe.getMessage(), pe);
        }
		
	}
	
	
}