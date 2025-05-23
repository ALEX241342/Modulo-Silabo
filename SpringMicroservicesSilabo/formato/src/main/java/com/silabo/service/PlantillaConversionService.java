package com.silabo.service;

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
            
            XWPFDocument doc = new XWPFDocument(plantilla);
            // Procesar párrafos
            for (XWPFParagraph p : doc.getParagraphs()) {
                    reemplazarTexto(p, datos);
            }
            logger.info("Párrafos procesados correctamente");
            // Procesar tablas
            for (XWPFTable tabla : doc.getTables()) {
                    reemplazarTabla(tabla, datos);
            }
            logger.info("Tablas procesadas correctamente");
            return doc;
        
    }

    private void reemplazarTexto(XWPFParagraph p, Map<String, Object> datos) {
        String originalText = p.getText();
        if (originalText == null || originalText.isEmpty()) return;
            // Reemplazo normal con [clave]
            String reemplazado = originalText;
            for (Map.Entry<String, Object> e : datos.entrySet()) {
                String marcador = "[" + e.getKey() + "]";
                if (reemplazado.contains(marcador)) {
                    String valor = String.valueOf(e.getValue());
                    reemplazado = reemplazado.replace(marcador, valor);
                }
            }
    
            if(reemplazado != originalText){
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
            }

        // Detectar claves entre llaves en el texto
        List<String> clavesLista = new ArrayList<>();
        for (Map.Entry<String, Object> e : datos.entrySet()) {
            String marcador = "{" + e.getKey() + "}";
            if (originalText.contains(marcador) && e.getValue() instanceof List) {
                clavesLista.add(e.getKey());
            }
        }
        //reemplazo interaciones
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
            logger.debug("No se encontraron claves de lista en el texto: {}", originalText);
        }
    }


    private void reemplazarTabla(XWPFTable tabla, Map<String, Object> datos) {
                List<XWPFTableRow> filasOriginales = new ArrayList<>(tabla.getRows());        
                for (int rowIndex = 0; rowIndex < filasOriginales.size(); rowIndex++) {
                        XWPFTableRow fila = filasOriginales.get(rowIndex);
                        if (fila == null) {
                            continue;
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
                            }
                        }
        
                        if (tieneLlaves) {
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
                        } 
                    
                }

        
    }
} 