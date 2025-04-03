import com.example.soli_todo.Todo
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("todos/1")
    fun getTodo(): Call<Todo>  // ⚠️ Pas suspend, pas de coroutine
}