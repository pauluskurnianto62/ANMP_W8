package com.paulus.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.paulus.todoapp.model.Todo
import com.paulus.todoapp.model.TodoDatabase
import com.paulus.todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TodoListViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {

    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = buildDb(getApplication())

            todoLD.postValue(db.todoDao().selectAllTodo())
            loadingLD.postValue(false)
        }
    }

    //fun clearTask(todo: Todo) {
        //launch {
            //val db = TodoDatabase.buildDatabase(
                //getApplication()
            //)
            //db.todoDao().deleteTodo(todo)

            //todoLD.postValue(db.todoDao().selectAllTodo())
        //}
    //}

    fun changeTaskStatus(uuid:Int) {
        launch {
            val db = TodoDatabase.buildDatabase(
                getApplication()
            )
            db.todoDao().updateIsdone(uuid)

            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }
}

