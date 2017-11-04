import io.github.juanlucode.zip.ZipFileSystem
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import java.io.File
import org.junit.jupiter.api.Test

class ZipFileSystemTest {

    @Disabled
    @Test
    fun zip(){

        //val zipFileSystem = ZipFileSystem("test.zip")

        val zipFileSystem = ZipFileSystem("test.zip")

        //val sourceFile =  File(this.javaClass.getResource("/directory/File1.txt").file)

        //val sourceFile2 = File(this.javaClass.getResource("/directory/File2.txt").file)


        zipFileSystem.add(File("File1.txt"))
        zipFileSystem.add(File("File2.txt"))

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

    @Disabled
    @Test
    fun delete(){
        val zipFileSystem = ZipFileSystem("test.zip")

        zipFileSystem.delete("File1.txt")

    }

    @Test
    fun extract(){
        val zipFileSystem = ZipFileSystem("test.zip")

        zipFileSystem.extract("File2.txt")
    }
}