import org.json.JSONArray;
import org.json.JSONObject;
import resources.Comment;
import resources.Post;
import resources.Todo;
import resources.User;
import resources.user.Address;
import resources.user.Company;
import resources.user.Geo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise1 {
    public static String getResponseString(String URL)
            throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest getUsersRequest = HttpRequest.newBuilder()
                .uri(new URI(URL))
                .build();

        HttpResponse<String> response = httpClient.send(getUsersRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static Geo jsonObjectToGeo(JSONObject jsonObject) {
        return new Geo(
                jsonObject.get("lat").toString(),
                jsonObject.get("lng").toString()
        );
    }

    public static Address jsonObjectToAddress(JSONObject jsonObject) {
        Geo geo = jsonObjectToGeo(jsonObject.getJSONObject("geo"));
        return new Address(
                jsonObject.get("street").toString(),
                jsonObject.get("suite").toString(),
                jsonObject.get("city").toString(),
                jsonObject.get("zipcode").toString(),
                geo
        );
    }

    public static Company jsonObjectToCompany(JSONObject jsonObject) {
        return new Company(
                jsonObject.get("name").toString(),
                jsonObject.get("catchPhrase").toString(),
                jsonObject.get("bs").toString()
        );
    }

    public static User jsonObjectToUser(JSONObject jsonObject) {
        Address address = jsonObjectToAddress(jsonObject.getJSONObject("address"));
        Company company = jsonObjectToCompany(jsonObject.getJSONObject("company"));

        return new User(
                (int) jsonObject.get("id"),
                jsonObject.get("name").toString(),
                jsonObject.get("username").toString(),
                jsonObject.get("email").toString(),
                address,
                jsonObject.get("phone").toString(),
                jsonObject.get("website").toString(),
                company
        );
    }

    public static Todo jsonObjectToTodo(JSONObject jsonObject) {
        return new Todo(
                (int) jsonObject.get("userId"),
                (int) jsonObject.get("id"),
                jsonObject.get("title").toString(),
                (boolean) jsonObject.get("completed")
        );
    }

    public static Post jsonObjectToPost(JSONObject jsonObject) {
        return new Post(
                (int) jsonObject.get("userId"),
                (int) jsonObject.get("id"),
                jsonObject.get("title").toString(),
                jsonObject.get("body").toString()
        );
    }

    public static Comment jsonObjectToComment(JSONObject jsonObject) {
        return new Comment(
                (int) jsonObject.get("postId"),
                (int) jsonObject.get("id"),
                jsonObject.get("name").toString(),
                jsonObject.get("email").toString(),
                jsonObject.get("body").toString()
        );
    }

    public static List<User> getUsers() throws URISyntaxException, IOException, InterruptedException {
        final String usersBaseUrl = "https://jsonplaceholder.typicode.com/users";
        JSONArray usersJsonArray = new JSONArray(getResponseString(usersBaseUrl));
        List<User> users = new ArrayList<>();

        for(int userIndex = 0; userIndex < usersJsonArray.length(); userIndex++) {
            users.add(jsonObjectToUser(usersJsonArray.getJSONObject(userIndex)));
        }

        return users;
    }

    /*
    Method that returns the uncompleted tasks of a given user id
    Corresponds to task 1.b
     */
    public static List<Todo> getUserUncompletedTodoSummaryByUserId(int userId)
            throws URISyntaxException, IOException, InterruptedException {
        final String todosBaseUrl = "https://jsonplaceholder.typicode.com/todos";
        List<Todo> userTodosList = new ArrayList<>();

        JSONArray userTodosJsonArray = new JSONArray(getResponseString(
                todosBaseUrl.concat("?userId=")
                        .concat(String.valueOf(userId))
                        .concat("&completed=false")
        ));

        for(int todoIndex = 0; todoIndex < userTodosJsonArray.length(); todoIndex++) {
            userTodosList.add(jsonObjectToTodo(userTodosJsonArray.getJSONObject(todoIndex)));
        }

        return userTodosList;
    }

    /*
    Method that returns the summary for each user, his/her uncompleted tasks (todos)
    Corresponds to task 1.a
     */
    public static Map<User, List<Todo>> getUsersUncompletedTodoSummary()
            throws URISyntaxException, IOException, InterruptedException {
        Map<User, List<Todo>> usersTodoSummaryMap = new HashMap<>();
        List<User> users = getUsers();

        for(User user: users) {
            usersTodoSummaryMap.put(user, getUserUncompletedTodoSummaryByUserId(user.getId()));
        }

        return usersTodoSummaryMap;
    }

    public static List<String> getEmailsOfCommentsByPostId(int postId) throws URISyntaxException, IOException, InterruptedException {
        final String commentsBaseUrl = "https://jsonplaceholder.typicode.com/comments";
        List<String> postCommentsEmailsList = new ArrayList<>();
        Comment comment;

        JSONArray postCommentsJsonArray = new JSONArray(getResponseString(
                commentsBaseUrl.concat("?postId=")
                        .concat(String.valueOf(postId))
        ));

        for(int postIndex = 0; postIndex < postCommentsJsonArray.length(); postIndex++) {
            comment = jsonObjectToComment(postCommentsJsonArray.getJSONObject(postIndex));
            postCommentsEmailsList.add(comment.getEmail());
        }

        return postCommentsEmailsList;
    }

    public static List<Post> getUserPostsByUserId(int userId)
            throws URISyntaxException, IOException, InterruptedException {
        final String postsBaseUrl = "https://jsonplaceholder.typicode.com/posts";
        List<Post> posts = new ArrayList<>();

        JSONArray userPostsJsonArray = new JSONArray(getResponseString(
                postsBaseUrl.concat("?userId=")
                        .concat(String.valueOf(userId))
        ));

        for(int postIndex = 0; postIndex < userPostsJsonArray.length(); postIndex++) {
            posts.add(jsonObjectToPost(userPostsJsonArray.getJSONObject(postIndex)));
        }

        return posts;
    }

    /*
    Method that returns the summary for each user, the email of each replier (in a
    comment) per each post that the user has posted.
    Corresponds to task 1.c
    */
    public static Map<User, Map<Post, List<String>>> getUsersPostSummary()
            throws URISyntaxException, IOException, InterruptedException {
        List<User> users = getUsers();
        Map<Post, List<String>> postEmails = new HashMap<>();
        Map<User, Map<Post,List<String>>> usersPostSummary = new HashMap<>();

        for(User user: users) {
            List<Post> userPosts = getUserPostsByUserId(user.getId());

            for(Post post: userPosts) {
                List<String> postCommentsEmails = getEmailsOfCommentsByPostId(post.getId());

                // don't add a post that has no replies
                if(!postCommentsEmails.isEmpty()) {
                    postEmails.put(post, postCommentsEmails);
                }
            }

            usersPostSummary.put(user, Map.copyOf(postEmails));
            postEmails.clear();
        }

        return usersPostSummary;
    }
}