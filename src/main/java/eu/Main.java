package eu;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException, JRException {
        System.out.println("Hello World!");

        InputStream input = new FileInputStream(new File("/home/rmo/IdeaProjects/JasperTutorial/src/main/java/test.jrxml"));
        JasperDesign design = JRXmlLoader.load(input);
        JasperReport report = JasperCompileManager.compileReport(design);

        Map<String, Object> parameters1 = new HashMap();
        parameters1.put("ReportTitle", "Rado1");
        Map<String, Object> parameters2 = new HashMap();
        parameters2.put("ReportTitle", "Rado2");
        JasperPrint print1 = JasperFillManager.fillReport(report, parameters1, new JREmptyDataSource());
        JasperPrint print2 = JasperFillManager.fillReport(report, parameters2, new JREmptyDataSource());


        exportToPdf(print1, "/home/rmo/IdeaProjects/JasperTutorial/src/main/java/test1.pdf");
        exportToPdf(print2, "/home/rmo/IdeaProjects/JasperTutorial/src/main/java/test2.pdf");

        exportToXls(print1, "/home/rmo/IdeaProjects/JasperTutorial/src/main/java/test1.xls");
        exportToXls(print2, "/home/rmo/IdeaProjects/JasperTutorial/src/main/java/test2.xls");
    }

    private static void exportToXls(JasperPrint print, String path) throws JRException, IOException {
        OutputStream ouputStream1 = new FileOutputStream(new File(path));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRXlsExporter exporterXLS = new JRXlsExporter();
        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
        exporterXLS.exportReport();
        ouputStream1.write(byteArrayOutputStream.toByteArray());
        ouputStream1.flush();
        ouputStream1.close();
    }

    private static void exportToPdf(JasperPrint print, String path) throws FileNotFoundException, JRException {
        OutputStream output1 = new FileOutputStream(new File(path));
        JasperExportManager.exportReportToPdfStream(print, output1);
    }
}
