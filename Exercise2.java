import org.junit.jupiter.api.Test;
import resources.Album;
import resources.Post;
import resources.Todo;
import resources.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Exercise2 {
    /*
    For 1.a
    Users todos should all be uncompleted
     */
    @Test
    void usersTodosShouldAllBeUncompleted()
            throws URISyntaxException, IOException, InterruptedException {
        Exercise1 exercise1 = new Exercise1();
        Map<User, List<Todo>> usersUncompletedTodos = exercise1.getUsersUncompletedTodoSummary();

        for(User user: usersUncompletedTodos.keySet()) {
            List<Todo> userTodos = usersUncompletedTodos.get(user);

            for(Todo todo: userTodos) {
                assertFalse(todo.getCompleted());
            }
        }
    }

    /*
    For 1.b
    User todos should return empty list if id doesn't exist
     */
    @Test
    void userTodosShouldReturnEmptyListIfIdDoesntExist()
            throws URISyntaxException, IOException, InterruptedException {
        Exercise1 exercise1 = new Exercise1();
        List<Todo> userUncompletedTodos = exercise1.getUserUncompletedTodoSummaryByUserId(0);

        assertTrue(userUncompletedTodos.isEmpty());
    }

    /*
    For 1.b
    User todos should return a non-empty list if id exists
     */
    @Test
    void userTodosShouldReturnANonEmptyListIfIdExists()
            throws URISyntaxException, IOException, InterruptedException {
        Exercise1 exercise1 = new Exercise1();
        List<Todo> userUncompletedTodos = exercise1.getUserUncompletedTodoSummaryByUserId(1);
        assertFalse(userUncompletedTodos.isEmpty());
    }

    /*
    For 1.b
    User todos should all be uncompleted
     */
    @Test
    void userTodosShouldAllBeUncompleted()
            throws URISyntaxException, IOException, InterruptedException {
        Exercise1 exercise1 = new Exercise1();
        List<Todo> userUncompletedTodos = exercise1.getUserUncompletedTodoSummaryByUserId(1);

        for(Todo todo: userUncompletedTodos) {
            assertFalse(todo.getCompleted());
        }
    }

    /*
    For 1.c
    User posts should be shown only if they contain comments
    Checking by seeing if a post has a list of emails which is not empty
     */
    @Test
    void userPostsShouldBeShownOnlyIfTheyContainComments()
            throws URISyntaxException, IOException, InterruptedException {
        Exercise1 exercise1 = new Exercise1();
        Map<User, Map<Post, List<String>>> usersPostSummary = exercise1.getUsersPostSummary();

        for(User user: usersPostSummary.keySet()) {
            Map<Post, List<String>> userPosts = usersPostSummary.get(user);

            for(Post post: userPosts.keySet()) {
                List<String> emails = userPosts.get(post);

                assertFalse(emails.isEmpty());
            }
        }
    }

    /*
    For 1.c
    Existing user post summary should not be empty
     */
    @Test
    void existingUserPostSummaryShouldNotBeEmpty()
            throws URISyntaxException, IOException, InterruptedException {
        Exercise1 exercise1 = new Exercise1();
        List<Post> usersPostSummary = exercise1.getUserPostsByUserId(1);

        assertFalse(usersPostSummary.isEmpty());
    }

    /*
    For 1.c
    Non-existent user post summary should be empty
     */
    @Test
    void nonExistentUserPostSummaryShouldBeEmpty()
            throws URISyntaxException, IOException, InterruptedException {
        Exercise1 exercise1 = new Exercise1();
        List<Post> usersPostSummary = exercise1.getUserPostsByUserId(0);

        assertTrue(usersPostSummary.isEmpty());
    }

    /*
    For 1.d
    Existing user doesn't have albums with more than 100 photos
     */
    @Test
    void existingUserDoesntHaveAlbumsWithMoreThan100Photos()
            throws URISyntaxException, IOException, InterruptedException {
        Exercise1 exercise1 = new Exercise1();
        List<Album> filteredUserAlbums = exercise1.getUserAlbumsByThreshold(1, 100);
        assertTrue(filteredUserAlbums.isEmpty());
    }

    /*
    For 1.d
    Existing user does have albums with more than 20 photos
     */
    @Test
    void existingUserDoesHaveAlbumsWithMoreThan20Photos()
            throws URISyntaxException, IOException, InterruptedException {
        Exercise1 exercise1 = new Exercise1();
        List<Album> filteredUserAlbums = exercise1.getUserAlbumsByThreshold(1, 20);
        assertFalse(filteredUserAlbums.isEmpty());
    }

    /*
    For 1.d
    Non-existent user doesn't have albums with any threshold
     */
    @Test
    void nonExistentUserDoesntHaveAlbumsWithAnyThreshold()
            throws URISyntaxException, IOException, InterruptedException {
        Exercise1 exercise1 = new Exercise1();
        List<Album> filteredUserAlbums = exercise1.getUserAlbumsByThreshold(0, 0);
        assertTrue(filteredUserAlbums.isEmpty());
    }
}