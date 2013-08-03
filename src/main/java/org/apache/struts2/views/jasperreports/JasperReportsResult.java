/*
 * $Id: JasperReportsResult.java 508875 2007-02-18 06:02:23Z tschneider $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.struts2.views.jasperreports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * <!-- START SNIPPET: description -->
 *
 * Generates a JasperReports report using the specified format or PDF if no
 * format is specified.
 *
 * <!-- END SNIPPET: description -->
 * <p />
 * <b>This result type takes the following parameters:</b>
 *
 * <!-- START SNIPPET: params -->
 *
 * <ul>
 *
 * <li><b>location (default)</b> - the location where the compiled jasper report
 * definition is (foo.jasper), relative from current URL.</li>
 *
 * <li><b>dataSource (required)</b> - the EL expression used to retrieve the
 * datasource from the value stack (usually a List).</li>
 *
 * <li><b>parse</b> - true by default. If set to false, the location param will
 * not be parsed for EL expressions.</li>
 *
 * <li><b>format</b> - the format in which the report should be generated. Valid
 * values can be found in {@link JasperReportConstants}. If no format is
 * specified, PDF will be used.</li>
 *
 * <li><b>contentDisposition</b> - disposition (defaults to "inline", values are
 * typically <i>filename="document.pdf"</i>).</li>
 *
 * <li><b>documentName</b> - name of the document (will generate the http header
 * <code>Content-disposition = X; filename=X.[format]</code>).</li>
 *
 * <li><b>delimiter</b> - the delimiter used when generating CSV reports. By
 * default, the character used is ",".</li>
 *
 * <li><b>imageServletUrl</b> - name of the url that, when prefixed with the
 * context page, can return report images.</li>
 *
 * </ul>
 *
 * <p>
 * This result follows the same rules from {@link StrutsResultSupport}.
 * Specifically, all parameters will be parsed if the "parse" parameter is not
 * set to false.
 * </p>
 * <!-- END SNIPPET: params -->
 *
 * <b>Example:</b>
 *
 * <pre><!-- START SNIPPET: example1 -->
 * &lt;result name="success" type="jasper"&gt;
 *   &lt;param name="location"&gt;foo.jasper&lt;/param&gt;
 *   &lt;param name="dataSource"&gt;mySource&lt;/param&gt;
 *   &lt;param name="format"&gt;CSV&lt;/param&gt;
 * &lt;/result&gt;
 * <!-- END SNIPPET: example1 --></pre>
 * or for pdf
 * <pre><!-- START SNIPPET: example2 -->
 * &lt;result name="success" type="jasper"&gt;
 *   &lt;param name="location"&gt;foo.jasper&lt;/param&gt;
 *   &lt;param name="dataSource"&gt;mySource&lt;/param&gt;
 * &lt;/result&gt;
 * <!-- END SNIPPET: example2 --></pre>
 *
 */
public class JasperReportsResult extends StrutsResultSupport implements JasperReportConstants {

    private static final long serialVersionUID = -2523174799621182907L;


    private final static Log LOG = LogFactory.getLog(JasperReportsResult.class);


    protected String dataSource;
    protected String format;
    protected String documentName;
    protected String contentDisposition;
    protected String delimiter;
    protected String imageServletUrl = "/images/";

    public JasperReportsResult() {
        super();
    }

    public JasperReportsResult(String location) {
        super(location);
    }

    public String getImageServletUrl() {
        return imageServletUrl;
    }

    public JasperReportsResult setImageServletUrl(final String imageServletUrl) {
        this.imageServletUrl = imageServletUrl;
        return this;
    }

    public JasperReportsResult setDataSource(String dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public JasperReportsResult setFormat(String format) {
        this.format = format;
        return this;
    }

    public JasperReportsResult setDocumentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public JasperReportsResult setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
        return this;
    }

    public JasperReportsResult setDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
        if (this.format == null) {
            this.format = FORMAT_PDF;
        }

        if (dataSource == null) {
            String message = "No dataSource specified...";
            LOG.error(message);
            throw new RuntimeException(message);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating JasperReport for dataSource = " + dataSource + ", format = " + this.format);
        }

        HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(ServletActionContext.HTTP_RESPONSE);


        //construct the data source for the report
        ValueStack stack = invocation.getStack();
        ValueStackDataSource stackDataSource = new ValueStackDataSource(stack, dataSource);

        format = conditionalParse(format, invocation);
        dataSource = conditionalParse(dataSource, invocation);

        if (contentDisposition != null) {
            contentDisposition = conditionalParse(contentDisposition, invocation);
        }

        if (documentName != null) {
            documentName = conditionalParse(documentName, invocation);
        }

        // (Map) ActionContext.getContext().getSession().get("IMAGES_MAP");
        if (StringUtils.isBlank(format)) {
            format = FORMAT_PDF;
        }

        if (!"contype".equals(request.getHeader("User-Agent"))) {
            // Determine the directory that the report file is in and set the reportDirectory parameter
            // For WW 2.1.7:
            //  ServletContext servletContext = ((ServletConfig) invocation.getInvocationContext().get(ServletActionContext.SERVLET_CONFIG)).getServletContext();
            ServletContext servletContext = (ServletContext) invocation.getInvocationContext().get(ServletActionContext.SERVLET_CONTEXT);

            String systemId = servletContext.getRealPath((String)request.getAttribute("location"));//***********Para verificar si el parametro location se pasa por .xml o en el action
            if(request.getAttribute("location")== null){
                 systemId = servletContext.getRealPath(finalLocation);
            }
            Map parameters = new ValueStackShadowMap(stack);
            parameters.putAll((Map)request.getAttribute("parametros"));//**************************Mapeo directo de los parametros
            File directory = new File(systemId.substring(0, systemId.lastIndexOf(File.separator)));
            parameters.put("reportDirectory", directory);
            parameters.put(JRParameter.REPORT_LOCALE, invocation.getInvocationContext().getLocale());

            byte[] output;
            JasperPrint jasperPrint;

            // Fill the report and produce a print object
            try {
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(systemId);
                DataSource dataS = (DataSource)request.getAttribute("dataSource"); //****************Data que se obtiene por request obtenido del actionJasper

                /*jasperPrint =
                        JasperFillManager.fillReport(jasperReport,
                                parameters,
                                stackDataSource); */        //*****Metodo antiguo

                jasperPrint =
                        JasperFillManager.fillReport(jasperReport,
                                parameters,
                                dataS.getConnection());   //***Metodo pasasando la conexion traida por request

            } catch (JRException e) {
                LOG.error("Error building report for uri " + systemId, e);
                throw new ServletException(e.getMessage(), e);
            }

            // Export the print object to the desired output format
            try {
                if (contentDisposition != null || documentName != null) {
                    final StringBuffer tmp = new StringBuffer();
                    tmp.append((contentDisposition == null) ? "inline" : contentDisposition);

                    if (documentName != null) {
                        tmp.append("; filename=");
                        tmp.append(documentName);
                        tmp.append(".");
                        tmp.append(format.toLowerCase());
                    }

                    response.setHeader("Content-disposition", tmp.toString());
                }

                if (format.equals(FORMAT_PDF)) {
                    response.setContentType("application/pdf");

                    // response.setHeader("Content-disposition", "inline; filename=report.pdf");
                    output = JasperExportManager.exportReportToPdf(jasperPrint);
                } else {
                    JRExporter exporter;

                    if (format.equals(FORMAT_CSV)) {
                        response.setContentType("text/plain");
                        exporter = new JRCsvExporter();
                    } else if (format.equals(FORMAT_HTML)) {
                        response.setContentType("text/html");

                        // IMAGES_MAPS seems to be only supported as "backward compatible" from JasperReports 1.1.0

                        Map imagesMap = new HashMap();

                        request.getSession(true).setAttribute("IMAGES_MAP", imagesMap);
                        exporter = new JRHtmlExporter();
                        exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
                        exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath() + imageServletUrl);
                        // Needed to support chart images:
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        request.getSession().setAttribute("net.sf.jasperreports.j2ee.jasper_print", jasperPrint);

                    } else if (format.equals(FORMAT_XLS)) {
                        response.setContentType("application/vnd.ms-excel");
                        exporter = new JRXlsExporter();
                    } else if (format.equals(FORMAT_XML)) {
                        response.setContentType("text/xml");
                        exporter = new JRXmlExporter();
                    } else if (format.equals(FORMAT_RTF)) {
                        response.setContentType("application/rtf");
                        exporter = new JRRtfExporter();
                    } else {
                        throw new ServletException("Unknown report format: " + format);
                    }

                    output = exportReportToBytes(jasperPrint, exporter);
                }
            } catch (JRException e) {
                String message = "Error producing " + format + " report for uri " + systemId;
                LOG.error(message, e);
                throw new ServletException(e.getMessage(), e);
            }

            response.setContentLength(output.length);

            ServletOutputStream ouputStream;

            try {
                ouputStream = response.getOutputStream();
                ouputStream.write(output);
                ouputStream.flush();
                ouputStream.close();
            } catch (IOException e) {
                LOG.error("Error writing report output", e);
                throw new ServletException(e.getMessage(), e);
            }
        } else {
            // Code to handle "contype" request from IE
            try {
                ServletOutputStream outputStream;
                response.setContentType("application/pdf");
                response.setContentLength(0);
                outputStream = response.getOutputStream();
                outputStream.close();
            } catch (IOException e) {
                LOG.error("Error writing report output", e);
                throw new ServletException(e.getMessage(), e);
            }
        }
    }

    /**
     * Run a Jasper report to CSV format and put the results in a byte array
     *
     * @param jasperPrint The Print object to render as CSV
     * @param exporter    The exporter to use to export the report
     * @return A CSV formatted report
     * @throws net.sf.jasperreports.engine.JRException
     *          If there is a problem running the report
     */
    private byte[] exportReportToBytes(JasperPrint jasperPrint, JRExporter exporter) throws JRException {
        byte[] output;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        if (delimiter != null) {
            exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, delimiter);
        }

        exporter.exportReport();

        output = baos.toByteArray();

        return output;
    }
}
