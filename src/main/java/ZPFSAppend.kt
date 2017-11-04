import java.util.*
import java.net.URI
import java.nio.file.Path
import java.nio.file.*

object ZPFSAppend {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        /* Define ZIP File System Properies in HashMap */
        val zip_properties = HashMap<String, String>()
        /* We want to read an existing ZIP File, so we set this to False */
        zip_properties.put("create", "true")
        /* Specify the encoding as UTF -8 */
        zip_properties.put("encoding", "UTF-8")
        /* Specify the path to the ZIP File that you want to read as a File System */
        val zip_disk = URI.create("jar:file:/home/juanluis/git/zip4kt/test.zip")
        /* Create ZIP file System */
        FileSystems.newFileSystem(zip_disk, zip_properties).use { zipfs ->
            /* Create a Path in ZIP File */
            val ZipFilePath = zipfs.getPath("File1.txt")
            /* Path where the file to be added resides */
            val addNewFile = Paths.get("/home/juanluis/git/zip4kt/File1.txt")
            /* Append file to ZIP File */
            Files.copy(addNewFile, ZipFilePath)
        }
    }
}
