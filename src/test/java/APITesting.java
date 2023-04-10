import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class APITesting {
    private final String key= "57d2c16929msh20a39c676c76aebp10e388jsnbbff482e01c8";

    @Test(priority = 0)
    public void positiveTesttastyAPIAutoComplete(){
        Response response = given()
                .header("X-RapidAPI-Key",key)
                .when().queryParam("prefix", "chicken soup")
                .get("https://tasty.p.rapidapi.com/recipes/auto-complete");
        Assert.assertTrue(response.jsonPath().getList("results").size()>0);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 1)
    public void negativeTestTastyAPIAutoCompleteWrongParam(){
        Response response = given()
                .header("X-RapidAPI-Key",key)
                .when().queryParam("testing", "Coba")
                .get("https://tasty.p.rapidapi.com/recipes/auto-complete");
        Assert.assertEquals(response.getStatusCode(), 204);
    }

    @Test(priority = 2)
    public void negativeTestTastyAPIAutoCompleteHoHeaderKey(){
        Response response = given()
                .get("https://tasty.p.rapidapi.com/recipes/auto-complete");
        Assert.assertEquals(response.jsonPath().getString("message"), "Invalid API key. Go to https://docs.rapidapi.com/docs/keys for more info.");
        Assert.assertEquals(response.getStatusCode(), 401);
    }

    @Test(priority = 3)
    public void positiveTestTastyAPIRecipeList(){
        Response response = given()
                .header("X-RapidAPI-Key","57d2c16929msh20a39c676c76aebp10e388jsnbbff482e01c8")
                .when()
                .get("https://tasty.p.rapidapi.com/recipes/list");
        Assert.assertTrue(response.jsonPath().getInt("count")>0);
    }

    @Test(priority = 4)
    public void negativeTestTastyAPIRecipeHoHeaderKey(){
        Response response = given()
                .get("https://tasty.p.rapidapi.com/recipes/list");
        Assert.assertEquals(response.jsonPath().getString("message"), "Invalid API key. Go to https://docs.rapidapi.com/docs/keys for more info.");
        Assert.assertEquals(response.getStatusCode(), 401);
    }

    @Test(priority = 5)
    public void positiveTestListSimilarities(){
        Response response = given()
                .header("X-RapidAPI-Key","57d2c16929msh20a39c676c76aebp10e388jsnbbff482e01c8")
                .when()
                .queryParam("recipe_id", "8580")
                .get("https://tasty.p.rapidapi.com/recipes/list-similarities");
        Assert.assertTrue(response.jsonPath().getInt("count")>0);
    }

    @Test(priority = 6)
    public void negativeTestListSimilaritiesWrongRecipeIDParam(){
        Response response = given()
                .header("X-RapidAPI-Key","57d2c16929msh20a39c676c76aebp10e388jsnbbff482e01c8")
                .when()
                .queryParam("recipe_id", "asdfasdf")
                .get("https://tasty.p.rapidapi.com/recipes/list-similarities");
        Assert.assertEquals(response.jsonPath().getString("message"), "Bad Request");
        Assert.assertTrue(response.jsonPath().getJsonObject("errors").toString().contains("Not a valid integer."), "Not a valid integer.");
    }

    @Test(priority = 7)
    public void positiveTestTipList(){
        Response response = given()
                .header("X-RapidAPI-Key","57d2c16929msh20a39c676c76aebp10e388jsnbbff482e01c8")
                .when()
                .queryParam("id", "123")
                .get("https://tasty.p.rapidapi.com/tips/list");
        Assert.assertTrue(response.jsonPath().getInt("count")>0);
    }

    @Test(priority = 8)
    public void negativeTestTipListWrongParam(){
        Response response = given()
                .header("X-RapidAPI-Key","57d2c16929msh20a39c676c76aebp10e388jsnbbff482e01c8")
                .when()
                .queryParam("id", "asdfasdf")
                .get("https://tasty.p.rapidapi.com/tips/list");
        Assert.assertTrue(response.getBody().asString().isEmpty());
        Assert.assertEquals(response.getStatusCode(), 204);
    }
}
