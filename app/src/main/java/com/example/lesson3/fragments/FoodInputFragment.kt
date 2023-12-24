package com.example.lesson3.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.lesson3.R
import com.example.lesson3.data.Food
import com.example.lesson3.databinding.FragmentStudentInput2Binding
import com.example.lesson3.repository.AppRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat

private const val ARG_PARAM1 = "student_param"

class FoodInputFragment : Fragment() {
    private lateinit var food: Food
    private lateinit var _binding : FragmentStudentInput2Binding

    val binding
        get()=_binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val param1 = it.getString(ARG_PARAM1)
            if (param1==null)
                food=Food()
            else{
                val paramType= object : TypeToken<Food>(){}.type
                food = Gson().fromJson<Food>(param1, paramType)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentStudentInput2Binding.inflate(inflater,container,false)

        binding.etName.setText(food.name)
        binding.etWeight.setText(food.weight.toString())
        binding.etPrice.setText(food.price.toString())
        binding.etInfo.setText(food.info)
        binding.editCalories.setText(food.calories.toString())
        binding.btCancel.setOnClickListener{
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btSave.setOnClickListener {
            food.name = binding.etName.text.toString()
            food.weight = binding.etWeight.text.toString().toInt()
            food.price = binding.etPrice.text.toString().toInt()
            food.info = binding.etInfo.text.toString()
            food.calories = binding.editCalories.text.toString().toInt()
            AppRepository.getInstance().addFood(food)
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(food: Food) =
            FoodInputFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, Gson().toJson(food))
                }
            }
    }
}