package com.example.rickandmorty.ui.personage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.databinding.FragmentPersonageBinding
import com.example.rickandmorty.ui.personage.model.CharactersUI

class PersonageFragment : Fragment() {
    private lateinit var binding: FragmentPersonageBinding
    private var personage: CharactersUI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            personage = args.getParcelable(Args.PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.showBottomNavBar(false)
        with(binding) {
            if (personage != null) {
                Glide.with(requireContext())
                    .load(personage!!.image)
                    .centerCrop()
                    .into(ivImage)

                tvName.text = personage!!.name
                tvStatus.text = personage!!.status
                tvSpecies.text = personage!!.species
                tvGender.text = personage!!.gender
                tvLocation.text = personage!!.location.name
                tvOrigin.text = personage!!.origin.name
            }
        }

    }

    companion object {
        private object Args {
            const val PARAM1 = "param1"
        }

        fun newInstance(param1: CharactersUI): PersonageFragment =
            PersonageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Args.PARAM1, param1)
                }
            }
    }

}


//class DemoFragment : Fragment() {
//    private var param1: Int? = null
//    private var param2: String? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let { args ->
//            param1 = args.getInt(Args.PARAM1)
//            param2 = args.getString(Args.PARAM2)
//        }
//    }
//    companion object {
//        private object Args {
//            const val PARAM1 = "param1"
//            const val PARAM2 = "param2"
//        }
//        fun newInstance(param1: Int, param2: String): DemoFragment =
//            DemoFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(Args.PARAM1, param1)
//                    putString(Args.PARAM2, param2)
//                }
//            }
//    }
//}