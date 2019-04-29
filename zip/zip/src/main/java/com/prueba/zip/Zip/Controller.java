package com.prueba.zip.Zip;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import net.lingala.zip4j.exception.ZipException;


@RestController
@RequestMapping("/api/comprimir")
public class Controller {

    @PostMapping
    public String Controller(@RequestBody Ruta ruta) {
        comprimir(ruta);
        //comprimir1(ruta);
        return "listo";
    }

    public void agregarCarpeta(String ruta, String carpeta, ZipOutputStream zip) throws Exception {
        File directorio = new File(carpeta);
        for (String nombreArchivo : directorio.list()) {
            if (ruta.equals("")) {
                agregarArchivo(directorio.getName(), carpeta + "/" + nombreArchivo, zip);
            } else {
                agregarArchivo(ruta + "/" + directorio.getName(), carpeta + "/" + nombreArchivo, zip);
            }
        }
    }

    public void agregarArchivo(String ruta, String directorio, ZipOutputStream zip) throws Exception {
        File archivo = new File(directorio);
        if (archivo.isDirectory()) {
            agregarCarpeta(ruta, directorio, zip);
        } else {
            byte[] buffer = new byte[4096];
            int leido;
            FileInputStream entrada = new FileInputStream(archivo);
            zip.putNextEntry(new ZipEntry(ruta + "/" + archivo.getName()));
            while ((leido = entrada.read(buffer)) > 0) {
                zip.write(buffer, 0, leido);
            }
        }
    }

    public void comprimir(Ruta ruta) {

        try {
            //"C:/Users/andres2288/Documents/compression/andres2288.zip"
            ZipFile zipFile = new ZipFile(ruta.origen);
            File archivo = new File(ruta.origen);

            ArrayList filesToAdd = new ArrayList();
            //"C:/Users/andres2288/Documents/compression/ZipTest/sample.txt"
            if(archivo.isDirectory()) {
                filesToAdd.add(new File(ruta.archivo));
            }
            //filesToAdd.add(new File(ruta.archivo));

            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression


            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);


            zipFile.addFiles(filesToAdd, parameters);

        } catch (ZipException e) {
            e.printStackTrace();
        }
        try {
            ZipFile zipFile = new ZipFile(ruta.origen);

            ArrayList filesToAdd = new ArrayList();
            filesToAdd.add(new File(ruta.archivo));

            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression

            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            parameters.setRootFolderInZip("agregado/");

            zipFile.addFiles(filesToAdd, parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

}
