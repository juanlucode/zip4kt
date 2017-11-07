import io.github.juanlucode.zip.ZipFile
import org.junit.jupiter.api.Disabled
import java.io.File
import org.junit.jupiter.api.Test

class ZipFileTest {

    //@Disabled
    @Test
    fun zip(){

        val zipFileSystem = ZipFile(File("test.zip"))

        //zipFileSystem.add(File("File1.txt"))
        zipFileSystem.add(File("File2.txt"))

    }

    @Disabled
    @Test
    fun delete(){
        val zipFileSystem = ZipFile(File("test.zip"))

        zipFileSystem.delete("File1.txt")

    }

    @Disabled
    @Test
    fun extract(){
        val zipFileSystem = ZipFile(File("test.zip"))

        zipFileSystem.extract("File2.txt")
    }
}