package io.github.juanlucode.zipfiles

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 * Zip
 * @author juanluis
 *
 * Manejo de ficheros tipo zip.
 */
class Zip {

    companion object {

        /**
         * This method populates all the files in a directory to a List
         * @param dir
         * @throws IOException
         */
        @Throws(IOException::class)
        fun populateFilesList(dir: File): MutableList<String> {
            val filesListInDir: MutableList<String> = ArrayList()
            val files = dir.listFiles()
            for (file in files!!) {
                if (file.isFile)
                    filesListInDir.add(file.absolutePath)
                else
                    filesListInDir.addAll(populateFilesList(file))
            }
            return filesListInDir
        }



        /**
         * This method compresses the single file to zip format
         * @param file
         * @param zipFileName
         */
        fun zipSingleFile(file: File, zipFileName: String): Boolean {
            var ok = false
            try {
                //create ZipOutputStream to write to the zip file
                val fos = FileOutputStream(zipFileName)
                val zos = ZipOutputStream(fos)
                //add a new Zip Entry to the ZipOutputStream
                val ze = ZipEntry(file.name)
                zos.putNextEntry(ze)
                //read the file and write to ZipOutputStream
                val fis = FileInputStream(file)
                val buffer = ByteArray(1024)
                var len: Int = fis.read(buffer)
                while (len > 0) {
                    zos.write(buffer, 0, len)
                    len = fis.read(buffer)
                }

                //Close the zip entry to write to zip file
                zos.closeEntry()
                //Close resources
                zos.close()
                fis.close()
                fos.close()
                ok = true
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return ok
        }

        /**
         * This method zips the directory
         * @param dir
         * @param zipDirName
         */
        fun zipDirectory(dir: File, zipDirName: String): Boolean {
            var ok = false
            try {

                //now zip files one by one
                //create ZipOutputStream to write to the zip file
                val fos = FileOutputStream(zipDirName)
                val zos = ZipOutputStream(fos)
                for (filePath in populateFilesList(dir)) {
                    //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                    val ze = ZipEntry(filePath.substring(dir.absolutePath.length + 1, filePath.length))
                    zos.putNextEntry(ze)
                    //read the file and write to ZipOutputStream
                    val fis = FileInputStream(filePath)
                    val buffer = ByteArray(1024)
                    var len: Int = fis.read(buffer)
                    while (len  > 0) {
                        zos.write(buffer, 0, len)
                        len = fis.read(buffer)
                    }
                    zos.closeEntry()
                    fis.close()
                    ok = true
                }
                zos.close()
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return ok
        }


        /**
         * This method unzip a zip file format
         * @param zipFile
         * @param dir
         */
        fun unzip(zipFile: File, dir: File): Boolean {
            var ok = false
            try {
                val buffer = ByteArray(1024)
                val zis = ZipInputStream(FileInputStream(zipFile))
                var zipEntry: ZipEntry? = zis.nextEntry
                while (zipEntry != null) {
                    val fileName = zipEntry.name
                    val newFile = File(dir.absolutePath + fileName)
                    val fos = FileOutputStream(newFile)
                    var len: Int = zis.read(buffer)
                    while (len  > 0) {
                        fos.write(buffer, 0, len)
                        len = zis.read(buffer)
                    }
                    fos.close()
                    zipEntry = zis.nextEntry
                }
                zis.closeEntry()
                zis.close()
                ok = true
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return  ok
        }
    }

}