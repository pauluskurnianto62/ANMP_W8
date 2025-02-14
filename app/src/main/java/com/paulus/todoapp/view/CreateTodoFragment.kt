package com.paulus.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.paulus.todoapp.R
import com.paulus.todoapp.databinding.FragmentCreateTodoBinding
import com.paulus.todoapp.model.Todo
import com.paulus.todoapp.viewmodel.TodoDetailViewModel

class CreateTodoFragment : Fragment() {
    private lateinit var viewModel: TodoDetailViewModel
    private lateinit var binding: FragmentCreateTodoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this).get(TodoDetailViewModel::class.java)

        binding.btnAdd.setOnClickListener {
            var radio = view.findViewById<RadioButton>(binding.radioGroupPriority.checkedRadioButtonId)
            var todo = Todo(binding.txtTitle.text.toString(),
                binding.txtNotes.text.toString(), radio.tag.toString().toInt(), 0)
            val list = listOf(todo)
            viewModel.addTodo(list)
            Toast.makeText(view.context, "Data added", Toast.LENGTH_LONG).show()
            Navigation.findNavController(it).popBackStack()
        }

    }
}