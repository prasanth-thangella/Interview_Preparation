package com.automation.tests;

import com.automation.core.RestResource;
import com.automation.pojo.Post;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CrudTest extends BaseTest {

    private Integer postId;

    @Test(priority = 1, description = "Create a new post")
    public void createNewPost() {
        Post post = Post.builder().userId(1).title("foo").body("bar").build();

        Response response = RestResource.post("/posts", post);

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("title"), "foo");

        // Capture ID for next tests
        postId = response.jsonPath().getInt("id");
    }

    @Test(priority = 2, description = "Get the created post")
    public void getPost() {
        // JSONPlaceholder creates resources with ID 101 always for demo (mock)
        // So we will query an existing one for stability (e.g., id 1)
        Response response = RestResource.get("/posts/1");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("id"), 1);
    }

    @Test(priority = 3, description = "Update the post")
    public void updatePost() {
        Post postUpdate = Post.builder().userId(1).title("updated title").body("updated body").build();

        Response response = RestResource.put("/posts/1", postUpdate);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "updated title");
    }

    @Test(priority = 4, description = "Delete the post")
    public void deletePost() {
        Response response = RestResource.delete("/posts/1");
        // JSONPlaceholder returns 200 on DELETE usually, or 204 if strictly RESTful but
        // often 200 with empty body. All docs say 200.
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
