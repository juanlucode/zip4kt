import io.github.juanlucode.zip.ZipFile
import org.junit.jupiter.api.Disabled
import java.io.File
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class ZipFileTest {


    @Test
    fun read(){
        val zipFileSystem = ZipFile(File("2080.modelo.zip"))

        val _stringBuffer = StringBuffer()

        zipFileSystem.read("Modelo2080V1PA.xml", _stringBuffer,  StandardCharsets.ISO_8859_1)
        print("Prueba read...")
        print(_stringBuffer)

    }

    @Disabled
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