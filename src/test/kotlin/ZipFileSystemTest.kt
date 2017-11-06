import io.github.juanlucode.zip.ZipFileSystem
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import java.io.File
import org.junit.jupiter.api.Test

class ZipFileSystemTest {

    //@Disabled
    @Test
    fun zip(){

        val zipFileSystem = ZipFileSystem(File("test.zip"))

        //zipFileSystem.add(File("File1.txt"))
        zipFileSystem.add(File("File2.txt"))

    }

    @Disabled
    @Test
    fun delete(){
        val zipFileSystem = ZipFileSystem(File("test.zip"))

        zipFileSystem.delete("File1.txt")

    }

    @Disabled
    @Test
    fun extract(){
        val zipFileSystem = ZipFileSystem(File("test.zip"))

        zipFileSystem.extract("File2.txt")
    }
}