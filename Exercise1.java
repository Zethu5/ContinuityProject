import org.json.JSONArray;
import org.json.JSONObject;
import resources.Album;
import resources.Comment;
import resources.Photo;
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

    public static Photo jsonObjectToPhoto(JSONObject jsonObject) {
        return new Photo(
                (int) jsonObject.get("albumId"),
                (int) jsonObject.get("id"),
                jsonObject.get("title").toString(),
                jsonObject.get("url").toString(),
                jsonObject.get("thumbnailUrl").toString()
        );
    }

    public static Album jsonObjectToAlbum(JSONObject jsonObject) {
        return new Album(
                (int) jsonObject.get("userId"),
                (int) jsonObject.get("id"),
                jsonObject.get("title").toString()
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
    public List<Todo> getUserUncompletedTodoSummaryByUserId(int userId)
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
    public Map<User, List<Todo>> getUsersUncompletedTodoSummary()
            throws URISyntaxException, IOException, InterruptedException {
        Map<User, List<Todo>> usersTodoSummaryMap = new HashMap<>();
        List<User> users = getUsers();

        for(User user: users) {
            usersTodoSummaryMap.put(user, getUserUncompletedTodoSummaryByUserId(user.getId()));
        }

        return usersTodoSummaryMap;
    }

    public static List<String> getEmailsOfCommentsByPostId(int postId)
            throws URISyntaxException, IOException, InterruptedException {
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

    public List<Post> getUserPostsByUserId(int userId)
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
    public Map<User, Map<Post, List<String>>> getUsersPostSummary()
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

    public static List<Album> getAlbumsByUserId(int userId)
            throws URISyntaxException, IOException, InterruptedException {
        final String albumsBaseUrl = "https://jsonplaceholder.typicode.com/albums";
        List<Album> albums = new ArrayList<>();

        JSONArray userAlbumsJsonArray = new JSONArray(getResponseString(
                albumsBaseUrl.concat("?userId=")
                        .concat(String.valueOf(userId))
        ));

        for(int albumIndex = 0; albumIndex < userAlbumsJsonArray.length(); albumIndex++) {
            albums.add(jsonObjectToAlbum(userAlbumsJsonArray.getJSONObject(albumIndex)));
        }

        return albums;
    }

    public static List<Photo> getPhotosByAlbumId(int albumId)
            throws URISyntaxException, IOException, InterruptedException {
        final String photosBaseUrl = "https://jsonplaceholder.typicode.com/photos";
        List<Photo> albums = new ArrayList<>();

        JSONArray albumPhotosJsonArray = new JSONArray(getResponseString(
                photosBaseUrl.concat("?albumId=")
                        .concat(String.valueOf(albumId))
        ));

        for(int photoIndex = 0; photoIndex < albumPhotosJsonArray.length(); photoIndex++) {
            albums.add(jsonObjectToPhoto(albumPhotosJsonArray.getJSONObject(photoIndex)));
        }

        return albums;
    }

    /*
    Method that returns all albums of a specific user that contains more photos than a given threshold
    Corresponds to task 1.d
    */
    public List<Album> getUserAlbumsByThreshold(int userId, int threshold)
            throws URISyntaxException, IOException, InterruptedException {
        List<Album> userAlbums = getAlbumsByUserId(userId);
        List<Album> filteredAlbums = new ArrayList<>();

        for(Album album: userAlbums) {
            List<Photo> albumPhotos = getPhotosByAlbumId(album.getId());

            if(albumPhotos.size() > threshold) {
                filteredAlbums.add(album);
            }
        }

        return filteredAlbums;
    }
}
