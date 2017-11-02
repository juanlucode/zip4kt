import io.github.juanlucode.zip.ZipFileSystem
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.File
import org.junit.jupiter.api.Test

class ZipFileSystemTest {

    @Test
    fun zip(){

        val zipFileSystem = ZipFileSystem("./test.zip")

        val sourceFile =  File(this.javaClass.getResource("/directory/File1.txt").file)

        zipFileSystem.add(sourceFile)

        /*
        val dir = File(this.javaClass.getResource("/directory").path)

        val list = ZipIo.populateFilesList(dir)

        assertTrue(list.size > 0, "directorio con ficheros")
        */
        /*
        assertTrue(list[0].equals("File1.txt"))
        assertTrue(list[1].equals("File2.txt"))
        assertTrue(list[2].equals("File3.txt"))
        */
    }

    @Test
    fun zipSingleFileTest(){

        // val sourceFile =  File(this.javaClass.getResource("/directory/File1.txt").file)
        //val destFile = File(this.javaClass.getResource("/directory/zipped.zip").file)

        // assertTrue(ZipIo.zipSingleFile(sourceFile, "zipped_single.zip"))


    }

    @Test
    fun zipDirectoryTest(){
        // val sourceDir =  File(this.javaClass.getResource("/directory/").file)
        //val destFile = File(this.javaClass.getResource("/directory/zipped.zip").file)
        // assertTrue(ZipIo.zipDirectory(sourceDir, "zipped_dir.zip"))
    }
}