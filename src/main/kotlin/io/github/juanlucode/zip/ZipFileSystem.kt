package io.github.juanlucode.zip

import java.io.File
import java.net.URI
import java.nio.charset.Charset
import java.util.HashMap
import java.nio.file.*
import java.nio.file.Files
import java.nio.file.Paths


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

class ZipFileSystem {

    private val charset = Charset.forName("UTF-8")
    private var zipProperties: HashMap<String, String>
    private var zipDisk: URI? = null
    //private var zipfs: FileSystem? = null
    private var zipPath: Path? = null

    constructor(zipFile: File) {


        zipProperties = HashMap()

        zipPath = zipFile.toPath()

        zipProperties!!.put("create", "false")

        /* Specify the encoding as UTF -8 */
        zipProperties.put("encoding", "UTF-8");

        /* Specify the path to the ZIP File that you want to read as a File System */
        zipDisk = URI.create("jar:file:" + zipPath!!.toUri().path)

    }


    fun add(file: File): Boolean {
        var ok = false

        FileSystems.newFileSystem(zipDisk, zipProperties).use { zipfs ->

            /* Create a Path in ZIP File */
            val zipFilePath = zipfs!!.getPath(file.name)
            /* Path where the file to be added resides */
            /* Append file to ZIP File */
            Files.copy(file.toPath(), zipFilePath)

            ok = true
        }
        return ok
    }

    fun delete(fileInZip: String): Boolean {
        var ok = false

        FileSystems.newFileSystem(zipDisk, zipProperties).use { zipfs ->
            /* Get the Path inside ZIP File to delete the ZIP Entry */
            val pathInZipfile = zipfs!!.getPath(fileInZip)
            /* Execute Delete */
            Files.delete(pathInZipfile)
            ok = true
        }
        return ok
    }

    fun extract(fileInZip: String, dir: String = "."): Boolean {
        var ok = false

        FileSystems.newFileSystem(zipDisk, zipProperties).use { zipfs ->
            /* Path inside ZIP File */
            val pathInZipfile = zipfs!!.getPath(fileInZip)
            /* Path to extract the file to  */
            val fileOutZip = Paths.get(dir + File.separator + fileInZip)
            /* Extract file to disk */
            Files.copy(pathInZipfile, fileOutZip)
            ok = true
        }

        return ok
    }

    /*
    read and write more efficiently using buffering methods.

    More info:

    https://docs.oracle.com/javase/tutorial/essential/io/file.html
     */

    fun read(fileInZip: String, _stringBuffer: StringBuffer): Boolean {
        var ok = false

        FileSystems.newFileSystem(zipDisk, zipProperties).use { zipfs ->
            val pathInZipfile = zipfs!!.getPath(fileInZip)

            _stringBuffer.delete(0, _stringBuffer.length)
            Files.newBufferedReader(pathInZipfile, charset).use { reader ->
                var line: String? = null
                line = reader.readLine()
                while (line  != null) {
                    _stringBuffer.append(line)
                }
            }
        }

        return ok
    }

    fun write(fileInZip: String, _stringBuffer: StringBuffer): Boolean {
        var ok = false
        FileSystems.newFileSystem(zipDisk, zipProperties).use { zipfs ->
            val pathInZipfile = zipfs!!.getPath(fileInZip)
            val writer = Files.newBufferedWriter(pathInZipfile, charset)
            writer.write(_stringBuffer.toString(), 0, _stringBuffer.length);
        }
        return ok
    }
}