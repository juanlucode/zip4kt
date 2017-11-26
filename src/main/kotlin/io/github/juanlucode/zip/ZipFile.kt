package io.github.juanlucode.zip

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.net.URI
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.*
import java.util.*


// basado en:

// http://thinktibits.blogspot.com.es/2013/02/Add-Files-to-Existing-ZIP-Archive-in-Java-Example-Program.html

// http://thinktibits.blogspot.com.es/2013/02/Delete-Files-From-ZIP-Archive-Java-Example.html

// https://jakubstas.com/creating-files-and-directories-nio2/#.Wfh-rK2Yp2Q

// http://thinktibits.blogspot.com.es/2013/02/Extract-ZIP-Files-Using-ZPFS-Java-NIO-Example.html

// http://thinktibits.blogspot.com.es/2013/02/Rename-ZIP-Entries-with-NIO-ZPFS-Java-Example.html

// http://thinktibits.blogspot.com.es/2013/02/Search-ZIP-File-Using-Java-NIO-ZPFS-Example.html

// http://thinktibits.blogspot.com.es/2013/02/Java-NIO-ZIP-File-System-Provider-Example.html

// http://fahdshariff.blogspot.com.es/2011/08/java-7-working-with-zip-files.html

// http://www.java2s.com/Tutorials/Java/IO/Zip/Java_Tutorial_Zip.htm

class ZipFile {

    private val charset = Charset.forName("utf-8")
    private var zipProperties: HashMap<String, String>
    private var zipDisk: URI? = null
    private var zipPath: Path? = null

    constructor(zipFile: File) {


        zipProperties = HashMap()

        zipPath = zipFile.toPath()

        /* Specify the path to the ZIP File that you want to read as a File System */
        zipDisk = URI.create("jar:file:" + zipPath!!.toUri().path)

        /* Specify the encoding as default ( = UTF -8) */
        zipProperties.put("encoding", charset.name())

        // If file doesn't exist, then create it
        if ( ! Files.exists(zipPath)) {
            zipProperties.put("create", "true")
            FileSystems.newFileSystem(zipDisk, zipProperties).use{}
        }

        zipProperties.put("create", "false")

    }


    fun add(file: File): Boolean {
        var ok = false

        try {

            FileSystems.newFileSystem(zipDisk, zipProperties).use { zipfs ->

                /* Create a Path in ZIP File */
                val zipFilePath = zipfs!!.getPath(file.name)
                /* Path where the file to be added resides */
                /* Append file to ZIP File */
                Files.copy(file.toPath(), zipFilePath, StandardCopyOption.REPLACE_EXISTING)

                ok = true
            }
        } catch (ex: Exception){
            ex.printStackTrace()
        }
        return ok
    }

    fun delete(fileInZip: String): Boolean {
        var ok = false

        try {
            FileSystems.newFileSystem(zipDisk, zipProperties).use { zipfs ->
                /* Get the Path inside ZIP File to delete the ZIP Entry */
                val pathInZipfile = zipfs!!.getPath(fileInZip)
                /* Execute Delete */
                Files.delete(pathInZipfile)
                ok = true
            }
        } catch (ex: Exception){
            ex.printStackTrace()
        }
        return ok
    }

    fun extract(fileInZip: String, dir: String = "."): Boolean {
        var ok = false

        try {
            FileSystems.newFileSystem(zipDisk, zipProperties).use { zipfs ->
                /* Path inside ZIP File */
                val pathInZipfile = zipfs!!.getPath(fileInZip)
                /* Path to extract the file to  */
                val fileOutZip = Paths.get(dir + File.separator + fileInZip)
                /* Extract file to disk */
                Files.copy(pathInZipfile, fileOutZip)
                ok = true
            }
        } catch (ex: Exception){
            ex.printStackTrace()
        }
        return ok
    }

    /*
    read and write more efficiently using buffering methods.

    More info:

    https://docs.oracle.com/javase/tutorial/essential/io/file.html
     */


    fun read(fileInZip: String, _bufferedOutputStream: BufferedOutputStream): Boolean {
        var ok = false

        try {
            FileSystems.newFileSystem(zipDisk, zipProperties).use { zipfs ->
                /* Path inside ZIP File */
                val pathInZipfile = zipfs!!.getPath(fileInZip)
                /* Extract file to disk */
                Files.copy(pathInZipfile, _bufferedOutputStream)
                ok = true
            }
        } catch (ex: Exception){
            ex.printStackTrace()
        }
        return ok
    }


    fun read(fileInZip: String, _stringBuffer: StringBuffer, fileCharset: Charset = charset): Boolean {
        var ok = false

        try {
            FileSystems.newFileSystem(zipDisk, zipProperties).use { zipfs ->
                val pathInZipfile = zipfs!!.getPath(fileInZip)

                _stringBuffer.delete(0, _stringBuffer.length)
                Files.newBufferedReader(pathInZipfile, fileCharset).use { reader ->
                    var line: String?
                    line = reader.readLine()
                    while (line != null) {
                        _stringBuffer.append(line)
                        line = reader.readLine()
                    }
                }
                ok = true
            }
        } catch (ex: Exception){
            ex.printStackTrace()
        }
        return ok
    }


    fun write(fileInZip: String, _inputStream: BufferedInputStream): Boolean {
        var ok = false

        try {
            FileSystems.newFileSystem(zipDisk, zipProperties).use { zipfs ->

                /* Create a Path in ZIP File */
                val zipFilePath = zipfs!!.getPath(fileInZip)
                /* Path where the file to be added resides */
                /* Append file to ZIP File */
                Files.copy(_inputStream, zipFilePath, StandardCopyOption.REPLACE_EXISTING)

                ok = true
            }
        } catch (ex: Exception){
            ex.printStackTrace()
        }
        return ok

    }

    fun write(fileInZip: String, _stringBuffer: StringBuffer, fileCharset: Charset = charset): Boolean {
        var ok = false
        try {
            FileSystems.newFileSystem(zipDisk, zipProperties).use { zipfs ->
                val pathInZipfile = zipfs!!.getPath(fileInZip)
                // overwriting
                java.nio.file.Files.deleteIfExists(pathInZipfile)
                val writer = Files.newBufferedWriter(pathInZipfile, charset)
                writer.write(_stringBuffer.toString())
                writer.flush()
                writer.close()
                ok = true
            }
        } catch (ex: Exception){
            ex.printStackTrace()
        }
        return ok
    }
}