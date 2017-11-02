package io.github.juanlucode.zip

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import java.io.FileReader
import java.io.BufferedReader
import java.io.IOException


/**
 * ZipIo
 * @author juanluis
 *
 * Manejo de ficheros tipo zip.
 */

// Tratar zip como un File System ZPFS (Zip File System)

// http://thinktibits.blogspot.com.es/2013/02/Add-Files-to-Existing-ZIP-Archive-in-Java-Example-Program.html

// http://thinktibits.blogspot.com.es/2013/02/Delete-Files-From-ZIP-Archive-Java-Example.html


class ZipIo(fileName: String?) : ZipFile(fileName) {

    /**
     * This method unzip a single file from a zip file format into an StringBuffer object
     * @param fileNameExtract
     * @param stringBuffer
     */
    fun unzip(fileNameExtract: String, stringBuffer: StringBuffer): Boolean {
        var ok = false
        stringBuffer.delete(0, stringBuffer.length)
        var zis: ZipInputStream? = null

        try {
            val buffer = ByteArray(1024)
            zis = ZipInputStream(this.getInputStream(this.getEntry(fileNameExtract)))
            var len: Int = zis.read(buffer)
            while (len > 0) {
                stringBuffer.append(buffer)
                len = zis.read(buffer)
            }
            ok = true
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            zis?.closeEntry()
            zis?.close()

        }

        return ok
    }

    fun zip(_file: File): Boolean {
        return zip(_file.name, File2StringBuffer(_file))
    }

    fun zip(_fileName: String, _stringBuffer: StringBuffer): Boolean {
        var ok: Boolean

        // ¿Existe ya la entrada? (¿Es actualización?)
        if (this.getEntry(_fileName) == null) {
            ok = zipNewSingleFile(_fileName, _stringBuffer)
        } else {
            ok = zipSingleFileUpdating(_fileName, _stringBuffer)
        }

        return ok
    }

    private fun zipSingleFileUpdating(_fileName: String, _stringBuffer: StringBuffer): Boolean {
        var ok = false
        // Fichero zip actual
        val currentFile = File(this.name)
        var tempFile: File?
        var zin: ZipInputStream? = null
        var zos: ZipOutputStream? = null

        try {
            // Obtenemos un fichero temporal
            tempFile = File.createTempFile(currentFile.name, null)

            // Borramos el fichero temporal, ya que no se podría renombrar el zip existente
            tempFile.delete()

            // Controlamos que se produzca el renombrado
            currentFile.renameTo(tempFile)

            // Definimos buffer
            val buffer = ByteArray(1024)

            // Declaración de Streams
            zin = ZipInputStream(FileInputStream(tempFile))
            zos = ZipOutputStream(FileOutputStream(currentFile))

            // Recorremos entradas zip
            var entry = zin.nextEntry
            while (entry != null) {

                if (_fileName != entry.name) {
                    // Si el fichero existe se ignora en la copia

                    // El resto de ficheros han de copiarse tal cual
                    // Add ZIP entry to output stream.
                    zos.putNextEntry(ZipEntry(name))
                    // Transfer bytes from the ZIP file to the output file
                    var len: Int = zin.read(buffer)
                    while (len > 0) {
                        zos.write(buffer, 0, len)
                        len = zin.read(buffer)
                    }
                }
                entry = zin.getNextEntry()
            }
            // Cerramos el Stream de lectura
            zin.close()

            // Finalmente añadimos de nuevo el fichero a actualizar
            // Compress the files

            // Add ZIP entry to output stream.
            zos.putNextEntry(ZipEntry(_fileName))

            // Transfer bytes from the file to the ZIP file
            for (linea in _stringBuffer.lines()) {
                zos.write(linea.plus("\n").toByteArray())
            }

            // Complete the entry
            zos.closeEntry()
            // Complete the ZIP file
            zos.close()
            tempFile.delete()

            ok = true
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            zin?.closeEntry()
            zin?.close()
            zos?.closeEntry()
            zos?.close()
        }
        return ok
    }


    /*
    TODO PROBAR A VER SI AÑADE FICHEROS
     */
    private fun zipNewSingleFile(_fileName: String, _stringBuffer: StringBuffer): Boolean {
        var ok = false
        var fos: FileOutputStream? = null
        var zos: ZipOutputStream? = null
        var zipEntry: ZipEntry? = null

        try {
            //create ZipOutputStream to write to the zip file
            fos = FileOutputStream(this.name, true)
            zos = ZipOutputStream(fos)
            //add a new Zip Entry to the ZipOutputStream
            zipEntry = ZipEntry(_fileName)
            zos.putNextEntry(zipEntry)
            // read the stream and write to ZipOutputStream
            for (linea in _stringBuffer.lines()) {
                zos.write(linea.plus("\n").toByteArray())
            }
            // marcamos la operación como exitosa
            ok = true

        } catch (ex: IOException) {
            ex.printStackTrace()
        } finally {
            //Close the zip entry to write to zip file
            zos?.closeEntry()
            zos?.close()
            //Close resources
            fos?.close()

        }
        return ok
    }

    private fun File2StringBuffer(fichero: File): StringBuffer {

        val bufferedReader = BufferedReader(FileReader(fichero))

        val stringBuffer = StringBuffer()
        var line: String? = null

        line = bufferedReader.readLine()
        while (line != null) {

            stringBuffer.append(line).append("\n")
        }

        return stringBuffer
    }

}