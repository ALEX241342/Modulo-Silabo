package com.silabo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.silabo.service.DocumentoService;
import com.silabo.service.PlantillaConversionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/plantilla")
public class PlantillaController {
    private static final Logger logger = LoggerFactory.getLogger(PlantillaController.class);

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private PlantillaConversionService plantillaConversionService;

    @PostMapping("/{idSilabo}/docx")
    public ResponseEntity<byte[]> generarDocxDesdeBD(@PathVariable int idSilabo) {
        try {
            logger.info("Iniciando generación de documento para silabo ID: {}", idSilabo);
            
            // Obtener datos del silabo
            Map<String, Object> datos = documentoService.datosDesdeBD(idSilabo);
            logger.info("Datos obtenidos correctamente para el silabo ID: {}", idSilabo);

            // Cargar plantilla
            Resource resource = new ClassPathResource("Plantilla-SoftwareWeb.docx");
            if (!resource.exists()) {
                logger.error("No se encontró el archivo de plantilla en: {}", resource.getURL());
                throw new IOException("No se encontró el archivo de plantilla");
            }
            logger.info("Plantilla encontrada en: {}", resource.getURL());

            // Verificar que la plantilla es legible
            try (InputStream templateStream = resource.getInputStream()) {
                if (templateStream.available() == 0) {
                    logger.error("La plantilla está vacía");
                    throw new IOException("La plantilla está vacía");
                }
                logger.info("Plantilla cargada correctamente, tamaño: {} bytes", templateStream.available());
            }

            // Generar documento
            byte[] documentoBytes;
            try (InputStream templateStream = resource.getInputStream()) {
                var documento = plantillaConversionService.generarDocumento(datos, templateStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                documento.write(baos);
                documento.close();
                documentoBytes = baos.toByteArray();
                logger.info("Documento generado exitosamente, tamaño: {} bytes", documentoBytes.length);
            }

            // Configurar headers para descarga
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "silabo.docx");
            logger.info("Headers configurados para descarga");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(documentoBytes);

        } catch (IOException e) {
            logger.error("Error al procesar el archivo de plantilla: {}", e.getMessage(), e);
            return ResponseEntity.status(500)
                    .body(("Error al procesar el archivo de plantilla: " + e.getMessage()).getBytes());
        } catch (Exception e) {
            logger.error("Error al generar el documento: {}", e.getMessage(), e);
            return ResponseEntity.status(500)
                    .body(("Error al generar el documento: " + e.getMessage()).getBytes());
        }
    }
}

