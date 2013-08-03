/*
 * AON
 * 
 * Creado el 26/02/2008 11:51:04 a.m. (dd/mm/aaaa hh:mm:ss)
 *
 * Copyright (C) Biosnet S.C.    
 * All rights reserved. Todos los derechos reservados.
 *
 * http://www.biosnetmx.com/
 */
package mx.com.aon.export.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import mx.com.aon.export.ExportView;
import mx.com.aon.export.model.TableModelExport;

import org.apache.log4j.Logger;

/**
 * JpgView
 * 
 * <pre>
 *     Implementacion de vista a exportar con la cual se genera lainformacion en una imagen de formato JPG
 * <Pre>
 * 
 * @author &lt;a href=&quot;mailto:freddy.juarez@biosnetmx.com&quot;&gt;Freddy Ju&aacute;rez&lt;/a&gt;
 * @version	 1.0
 * 
 * @since	 1.0
 * 
 */
public class JpgView implements ExportView {
	
	private static Logger logger = Logger.getLogger(JpgView.class);

	private static final String EXTENSION = "jpg";

	private static final int FACT_COL = 260;
	private static final int MARGEN_COL = 30;
	private static final int MARGEN_ROW = 12;
	private static final int FACT_ROW = 15;
	private static final int SPACE_HEADER = 40;
	private static final int MAX_AREA = 10000000;

	private static final String MESSAGE_INCOMPLETE = "Lista demasiado larga para ser mostrada completamente...";
	
	/**
	 * Regresa la extension a ser exportada la informacion
	 */
	public String getExtension() {
		return EXTENSION;
	}

	/**
	 * Inicializa el ambiente para generar el grafico con la informacion
	 * @param imagen Buffer a ser utilizado para exportar
	 * @param width Tamaño del horizontal de la imagen
	 * @param height Tamaño del vertical de la imagen
	 * @return Graphics2D a ser utilizado para dibujar sobre este la informacion
	 */
	private Graphics2D initDraw(BufferedImage imagen, int width, int height){
		Graphics2D g = (Graphics2D) imagen.getGraphics();
		g.setFont(new Font("Helvetica", Font.PLAIN, 11));
		g.setColor( Color.WHITE );
		g.fillRect( 0, 0, width, height );
		g.setColor( Color.BLACK );
		return g;
	}
	
	/**
	 * Se dibuja la cabecera de la imagen
	 * @param Graphics2D en donde se dibuja la informacion
	 * @param TableModelExport modelo de la informacion 
	 */
	private void drawHeader( Graphics2D g , TableModelExport model){
		g.setColor( new Color(132,12,44) );
		int y = (SPACE_HEADER/2);
		int x = MARGEN_COL;
		int numCol = model.getColumnCount();
		for (int i = 0; i < numCol; i++) {
			g.drawString( model.getColumnName(i).toString(), x, y );
			x = x + FACT_COL;
		}
		g.setColor( Color.BLACK );

	}

	/**
	 * Se dibuja la informacion principal en el lienzo.
	 * @param Graphics2D en donde se dibuja la informacion
	 * @param TableModelExport modelo de la tabla
	 * @param height Tamaño del vertical de la imagen
	 * @param complete boolean que indica si se va a dibujar completa la imagen
	 */
	private void drawData( Graphics2D g , TableModelExport model, int height, boolean complete){
		int y = SPACE_HEADER;
		int x = MARGEN_COL;
		int limit = height;
		// Se itera sobre los datos  y se escribe en renglon columna la informacion
		for (int i = 0; i < model.getRowCount(); i++) {
			if( y >= limit ){
				break;
			}
			ArrayList<?> listCol = model.getRow(i);
			for (int j = 0; j < listCol.size(); j++) {
				g.drawString( ((listCol.get(j)!=null)?listCol.get(j).toString():""), x, y );
				x = x + FACT_COL;
			}
			x = MARGEN_COL;
			y = y + FACT_ROW;
		}
		// En caso de no estar completa la informacion se escribe un mensaje
		if( !complete ){
			g.setColor( new Color(132,12,44) );
			g.drawString( MESSAGE_INCOMPLETE , x, y );
		}
	}

	/**
	 * Metodo principal de exportacion
	 * @param TableModelExport modelo con la informacion a ser exportada
	 */
	public InputStream export(TableModelExport model) { 
		ByteArrayOutputStream salida = new ByteArrayOutputStream();
		try {
			// Se calculan los tamaños de la imagen
			int width  = (model.getColumnCount() * FACT_COL) + (2 * MARGEN_COL);
			int height = (model.getRowCount() * FACT_ROW) + (SPACE_HEADER) + (2 * MARGEN_ROW);
			// Se calcula el area a ser utilizada
			int area = width * height;
			boolean complete = true;
			// Se reajusta el area al tamaño limite posible
			while( area >= MAX_AREA ){
				height = height - FACT_ROW;
				area = width * height;
				complete = false;
			}
			if( logger.isDebugEnabled() ){
				logger.debug("Se genera la imagen de tamaño: " + width + "," + height + " : " + (width * height));
			}
			// Se crea el buffer de imagen
			BufferedImage imagen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// Se inicializa el lienzo a ser utilizado
			Graphics2D g = initDraw(imagen, width, height);
			// Se escribe la cabecera de la informacion
			drawHeader( g, model );
			// Se dibuja la informacion a ser exportada
			drawData( g, model, height, complete );
			// Se cierra el lienzo
			g.dispose();
			// Se genera el stream con el buffer de la imagen
			ImageIO.write(imagen, EXTENSION, salida);
			
		} catch (IOException ioe) {
			logger.error("Error en la generacion del buffer de salida",ioe);
		}catch (Exception e) {
			logger.error("Error general",e);
		}
		// Se transforma la salida a un stream esperado
		return new ByteArrayInputStream( salida.toByteArray());
	}

}
