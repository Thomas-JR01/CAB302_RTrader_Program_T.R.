package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import java.sql.SQLException;

public class ProgramExLibraryTest {

    Server_Folder.ProgramExLibrary ExLibraryTest = new Server_Folder.ProgramExLibrary();

    @Test
    public void LoginExTest() throws SQLException {
        String Request = "c_01,TestUser,Test";
        Object[][] ExpectedOutput = {{"9","TestDept"}};
        Object[][] Output = ExLibraryTest.LoginEx(Request);
        assertEquals(ExpectedOutput[0][0], Output[0][0], "Incorrect Login Details");
        assertEquals(ExpectedOutput[0][1], Output[0][1], "Incorrect Login Details");
    }


}
