package com.silabo.formato;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.apache.poi.xwpf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/plantilla")
public class PlantillaController {

    @Autowired
    private DocumentoService documentoService;

    @PostMapping("/{idSilabo}/docx")
    public ResponseEntity<byte[]> generarDocxDesdeBD(@PathVariable Integer idSilabo) {
        try {
            Map<String, Object> datos = documentoService.datosDesdeBD(idSilabo);
            System.out.println("Datos recuperados: " + datos);

            XWPFDocument documento = generarDocumento(datos);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            documento.write(baos);
            documento.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename("Silabo-" + idSilabo + ".docx").build());

            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generando documento: " + e.getMessage());
        }
    }

    private XWPFDocument generarDocumento(Map<String, Object> datos) throws IOException {
        InputStream plantilla = getClass().getResourceAsStream("/Plantilla-SoftwareWeb.docx");
        if (plantilla == null) {
            throw new RuntimeException("No se encontró la plantilla .docx");
        }

        XWPFDocument doc = new XWPFDocument(plantilla);
        for (XWPFParagraph p : doc.getParagraphs()) {
            String originalText = p.getText();
            if (originalText == null || originalText.isEmpty()) continue;

            String reemplazado = originalText;
            for (Map.Entry<String, Object> e : datos.entrySet()) {
                reemplazado = reemplazado.replace("[" + e.getKey() + "]", String.valueOf(e.getValue()));
            }

            int runCount = p.getRuns().size();
            for (int i = runCount - 1; i >= 0; i--) {
                p.removeRun(i);
            }

            p.createRun().setText(reemplazado);
        }
        return doc;
    }
}

