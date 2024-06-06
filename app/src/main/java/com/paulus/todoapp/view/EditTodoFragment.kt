package com.paulus.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.paulus.todoapp.R
import com.paulus.todoapp.databinding.FragmentCreateTodoBinding
import com.paulus.todoapp.databinding.FragmentEditTodoBinding
import com.paulus.todoapp.model.Todo
import com.paulus.todoapp.viewmodel.TodoDetailViewModel

class EditTodoFragment : Fragment(), RadioClickListener, TodoEditClickListener {
    private lateinit var binding: FragmentEditTodoBinding
    private lateinit var viewModel: TodoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTodoBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TodoDetailViewModel::class.java)
        binding.txtJudulTodo.text = "Edit Todo"
        binding.btnAdd.text = "Save Changes"
        binding.radioListener = this
        binding.saveListener = this
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            binding.todo = it
            binding.txtTitle.setText(it.title)
            binding.txtNotes.setText(it.notes)
            when (it.priority) {
                1 -> binding.radioLow.isChecked = true
                2 -> binding.radioMedium.isChecked = true
                else -> binding.radioHigh.isChecked = true
            }
        })
    }

    override fun onRadioClick(v: View, priority: Int, obj: Todo) {
        binding.todo!!.priority = priority
    }

    override fun onTodoEditClicks(v: View, obj: Todo) {
        viewModel.update(obj.title, obj.notes, obj.priority, obj.uuid)
        Toast.makeText(v.context, "Todo Updated", Toast.LENGTH_SHORT).show()
    }


}