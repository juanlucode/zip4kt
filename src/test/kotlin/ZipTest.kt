import io.github.juanlucode.zipfiles.Zip
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertTrue

class ZipTest {

    @Test
    fun populateFilesListTest(){
        val dir:File = this.javaClass.getResource("/directory") as File

        val list = Zip.populateFilesList(dir)

        assertTrue(list.size > 0, "directorio con ficheros")
    }

    @Test
    fun zipSingleFileTest(){

        assertTrue { if (1 == 0) true else false }

    }

    @Test
    fun zipDirectoryTest(){

    }
}