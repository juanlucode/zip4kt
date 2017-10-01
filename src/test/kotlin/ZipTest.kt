import io.github.juanlucode.zipfiles.Zip
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import java.io.File
import org.junit.jupiter.api.Test

class ZipTest {

    //@Disabled
    @Test
    fun populateFilesListTest(){
        val dir = File(this.javaClass.getResource("/directory").path)

        val list = Zip.populateFilesList(dir)

        assertTrue(list.size > 0, "directorio con ficheros")
    }

    @Disabled
    @Test
    fun zipSingleFileTest(){

        assertTrue { if (1 == 0) true else false }

    }

    @Disabled
    @Test
    fun zipDirectoryTest(){
        assertTrue(false, "falso!!")
    }
}