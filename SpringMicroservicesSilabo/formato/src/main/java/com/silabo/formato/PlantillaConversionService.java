package com.silabo.formato;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PlantillaConversionService {
    private static final Logger logger = LoggerFactory.getLogger(PlantillaConversionService.class);

    public XWPFDocument generarDocumento(Map<String, Object> datos, InputStream plantilla) throws IOException {
        try {
            logger.info("Iniciando generación de documento");
            logger.debug("Datos recibidos: {}", datos);
            
            XWPFDocument doc = new XWPFDocument(plantilla);
            logger.info("Documento base creado correctamente");

            // Procesar párrafos
            for (XWPFParagraph p : doc.getParagraphs()) {
                try {
                    reemplazarTexto(p, datos);
                } catch (Exception e) {
                    logger.error("Error al procesar párrafo: {}", p.getText(), e);
                    throw new RuntimeException("Error al procesar párrafo: " + e.getMessage(), e);
                }
            }
            logger.info("Párrafos procesados correctamente");

            // Procesar tablas
            try {
                logger.info("Iniciando procesamiento de tablas. Número de tablas: {}", doc.getTables().size());
                reemplazarTablas(doc, datos);
                logger.info("Tablas procesadas correctamente");
            } catch (Exception e) {
                logger.error("Error al procesar tablas: {}", e.getMessage(), e);
                throw new RuntimeException("Error al procesar tablas: " + e.getMessage(), e);
            }

            return doc;
        } catch (Exception e) {
            logger.error("Error general al generar documento: {}", e.getMessage(), e);
            throw new IOException("Error al generar documento: " + e.getMessage(), e);
        }
    }

    private void reemplazarTexto(XWPFParagraph p, Map<String, Object> datos) {
        String originalText = p.getText();
        if (originalText == null || originalText.isEmpty()) return;
        
        logger.debug("Procesando texto: {}", originalText);
    
        // Detectar claves entre llaves en el texto
        List<String> clavesLista = new ArrayList<>();
        for (Map.Entry<String, Object> e : datos.entrySet()) {
            String marcador = "{" + e.getKey() + "}";
            if (originalText.contains(marcador) && e.getValue() instanceof List) {
                clavesLista.add(e.getKey());
                logger.debug("Encontrada clave de lista: {}", e.getKey());
            }
        }
    
        if (!clavesLista.isEmpty()) {
            // Verificar que todas las listas tienen el mismo tamaño
            int size = -1;
            for (String clave : clavesLista) {
                List<?> lista = (List<?>) datos.get(clave);
                if (size == -1) size = lista.size();
                else if (lista.size() != size) {
                    String error = "Las listas asociadas a las claves entre llaves no tienen el mismo tamaño";
                    logger.error(error);
                    throw new RuntimeException(error);
                }
            }
    
            // Borrar los runs originales
            for (int i = p.getRuns().size() - 1; i >= 0; i--) {
                p.removeRun(i);
            }
    
            // Crear un párrafo por cada índice
            for (int i = 0; i < size; i++) {
                String textoGenerado = originalText;
                for (String clave : clavesLista) {
                    List<?> lista = (List<?>) datos.get(clave);
                    textoGenerado = textoGenerado.replace("{" + clave + "}", String.valueOf(lista.get(i)));
                }
                XWPFRun run = p.createRun();
                run.setText(textoGenerado);
                run.setFontFamily("Calibri");
                run.addBreak();
                logger.debug("Generado texto para índice {}: {}", i, textoGenerado);
            }
    
        } else {
            // Reemplazo normal con [clave]
            String reemplazado = originalText;
            for (Map.Entry<String, Object> e : datos.entrySet()) {
                String marcador = "[" + e.getKey() + "]";
                if (reemplazado.contains(marcador)) {
                    String valor = String.valueOf(e.getValue());
                    reemplazado = reemplazado.replace(marcador, valor);
                    logger.debug("Reemplazado {} por {}", marcador, valor);
                }
            }
    
            for (int i = p.getRuns().size() - 1; i >= 0; i--) {
                p.removeRun(i);
            }
    
            // Procesar texto entre asteriscos
            String[] partes = reemplazado.split("\\*\\*");
            for (int i = 0; i < partes.length; i++) {
                XWPFRun run = p.createRun();
                if (i % 2 == 0) {
                    // Texto normal
                    run.setText(partes[i]);
                } else {
                    // Texto en negrita
                    run.setText("\n" + partes[i] + "\n");
                    run.setBold(true);
                }
                run.setFontFamily("Calibri");
            }
            logger.debug("Texto final procesado: {}", reemplazado);
        }
    }

    private void reemplazarTablas(XWPFDocument doc, Map<String, Object> datos) {
        logger.info("Iniciando reemplazo de tablas");
        for (XWPFTable tabla : doc.getTables()) {
            try {
                List<XWPFTableRow> filasOriginales = new ArrayList<>(tabla.getRows());
                logger.info("Procesando tabla con {} filas", filasOriginales.size());
        
                for (int rowIndex = 0; rowIndex < filasOriginales.size(); rowIndex++) {
                    try {
                        XWPFTableRow fila = filasOriginales.get(rowIndex);
                        if (fila == null) {
                            logger.error("Fila nula encontrada en índice {}", rowIndex);
                            continue;
                        }

                        // Agregar logging detallado de la fila
                        logger.info("Procesando fila {}: Número de celdas: {}", rowIndex, fila.getTableCells().size());
                        
                        // Logging detallado de cada celda
                        for (int cellIndex = 0; cellIndex < fila.getTableCells().size(); cellIndex++) {
                            XWPFTableCell cell = fila.getTableCells().get(cellIndex);
                            if (cell == null) {
                                logger.error("Celda nula encontrada en fila {}, índice de celda: {}", rowIndex, cellIndex);
                                continue;
                            }
                            String cellText = cell.getText();
                            logger.info("Contenido de celda en fila {}, celda {}: '{}'", rowIndex, cellIndex, cellText);
                        }

                        boolean tieneLlaves = false;
                        List<String> clavesLista = new ArrayList<>();
        
                        String textoFila = fila.getTableCells().stream()
                            .map(cell -> cell != null ? cell.getText() : "")
                            .reduce("", (a, b) -> a + " " + b);
                        logger.info("Texto completo de la fila {}: '{}'", rowIndex, textoFila);

                        // Logging de las claves disponibles en los datos
                        logger.info("Claves disponibles en los datos: {}", datos.keySet());
        
                        for (Map.Entry<String, Object> e : datos.entrySet()) {
                            String marcador = "{" + e.getKey() + "}";
                            if (textoFila.contains(marcador) && e.getValue() instanceof List) {
                                tieneLlaves = true;
                                clavesLista.add(e.getKey());
                                logger.info("Encontrada clave de lista en tabla: {} con valor: {}", e.getKey(), e.getValue());
                            }
                        }
        
                        if (tieneLlaves) {
                            logger.info("Procesando fila con listas. Claves encontradas: {}", clavesLista);
                            // Verifica que las listas tengan el mismo tamaño
                            int size = -1;
                            for (String clave : clavesLista) {
                                List<?> lista = (List<?>) datos.get(clave);
                                if (lista == null) {
                                    String error = String.format("Lista nula encontrada para la clave: %s en la fila %d", clave, rowIndex);
                                    logger.error(error);
                                    throw new RuntimeException(error);
                                }
                                if (size == -1) size = lista.size();
                                else if (lista.size() != size) {
                                    String error = String.format("Las listas en una misma fila deben tener el mismo tamaño. Clave problemática: %s en la fila %d", clave, rowIndex);
                                    logger.error(error);
                                    throw new RuntimeException(error);
                                }
                            }
        
                            // Guardar textos antes de eliminar la fila
                            List<String> textosOriginales = new ArrayList<>();
                            for (XWPFTableCell cell : fila.getTableCells()) {
                                textosOriginales.add(cell.getText());
                            }
                            int numColumnas = textosOriginales.size();
                            tabla.removeRow(rowIndex);
                            logger.debug("Eliminada fila original y guardados {} textos", numColumnas);

                            // Crear nuevas filas con los textos reemplazados
                            for (int i = 0; i < size; i++) {
                                XWPFTableRow nuevaFila = tabla.insertNewTableRow(rowIndex + i);
                                for (int j = 0; j < numColumnas; j++) {
                                    String texto = textosOriginales.get(j);
                                    for (String clave : clavesLista) {
                                        List<?> lista = (List<?>) datos.get(clave);
                                        texto = texto.replace("{" + clave + "}", String.valueOf(lista.get(i)));
                                    }
                                    XWPFTableCell nuevaCelda = nuevaFila.addNewTableCell();
                                    XWPFParagraph paragraph = nuevaCelda.addParagraph();
                                    XWPFRun run = paragraph.createRun();
                                    run.setText(texto);
                                    run.setFontFamily("Calibri");
                                    logger.debug("Creada celda con texto: {}", texto);
                                }
                            }
        
                            rowIndex += size - 1; // Salta las filas generadas
                        } else {
                            // Reemplazo normal con [clave]
                            for (XWPFTableCell cell : fila.getTableCells()) {
                                String textoOriginal = cell.getText();
                                String nuevoTexto = textoOriginal;
                                for (Map.Entry<String, Object> e : datos.entrySet()) {
                                    String marcador = "[" + e.getKey() + "]";
                                    if (nuevoTexto.contains(marcador)) {
                                        String valor = String.valueOf(e.getValue());
                                        nuevoTexto = nuevoTexto.replace(marcador, valor);
                                        logger.debug("Reemplazado en celda {} por {}", marcador, valor);
                                    }
                                }
                                cell.removeParagraph(0);
                                XWPFParagraph paragraph = cell.addParagraph();
                                XWPFRun run = paragraph.createRun();
                                run.setText(nuevoTexto);
                                run.setFontFamily("Calibri");
                            }
                        }
                    } catch (Exception e) {
                        String errorMsg = String.format("Error al procesar fila %d: %s. Causa: %s", 
                            rowIndex, 
                            e.getMessage() != null ? e.getMessage() : "Mensaje nulo", 
                            e.getCause() != null ? e.getCause().getMessage() : "Sin causa específica");
                        logger.error(errorMsg, e);
                        throw new RuntimeException(errorMsg, e);
                    }
                }
            } catch (Exception e) {
                logger.error("Error al procesar tabla: {}", e.getMessage(), e);
                throw new RuntimeException("Error al procesar tabla: " + e.getMessage(), e);
            }
        }
    }
} 