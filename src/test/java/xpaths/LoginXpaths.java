package xpaths;

public class LoginXpaths {

    public String LogInFields;

    public String EmailTextField(){

        return this.LogInFields = "//input[@name='email']";
    }

    public String PasswordTextField(){

        return  this.LogInFields = "//input[@name='password']";
    }

    public String LogInButton(){
        return this.LogInFields = "//button[contains(text(),'LOG IN')]";
    }

    public String EmailRequiredField(){
        return this.LogInFields = "//form[1]/div[1]/div[1]";
    }


}
