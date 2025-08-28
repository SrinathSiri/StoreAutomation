package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.ExcelUtility;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class UserTestsDDT {


    @Test(priority = 1, dataProvider = "getdata",dataProviderClass = ExcelUtility.class)
    public void userTestsCreateDDT(String userid,String username,String fname,String lname,String email,String pwd,String phone){
        User pojodata = new User();
        pojodata.setId((int) Double.parseDouble(userid));
        pojodata.setUsername(username);
        pojodata.setFirstName(fname);
        pojodata.setLastName(lname);
        pojodata.setEmail(email);
        pojodata.setPassword(pwd);
        pojodata.setPhone(phone);
        Response response = UserEndPoints.createUser(pojodata);
        response.then().log().all();
    }

    @Test(priority = 2, dataProvider="getUserNamesData",dataProviderClass = ExcelUtility.class)
    public void userTestDeleteDDT(String ecusername){
    Response response = UserEndPoints.deleteUser(ecusername);
    response.then().log().all();
    }
}
