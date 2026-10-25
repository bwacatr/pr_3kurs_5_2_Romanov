package com.bignerdranch.android.pr_3kurs_5_1_romanov

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.launch
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InputFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InputFragment : Fragment() {

    lateinit var IdInput: EditText
    lateinit var searchBtn: Button


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Находим кнопку по её ID из layout-файла
        searchBtn = view.findViewById(R.id.SearchBtn)

        // Обрабатываем нажатие
        searchBtn.setOnClickListener {
            // Действие при клике
            getResult(view)
        }
    }


    fun getResult(view: View) {
        Dependencies.init(requireContext())

        IdInput = view.findViewById(R.id.enterId)

        val id = IdInput.text.toString()

        if (id.isEmpty())
        {
            val alert = AlertDialog.Builder(requireContext())
                .setTitle("Ошибка")
                .setMessage("Введите название книги или id")
                .setPositiveButton("OK", null)
                .create()
                .show()
            return
        }



            val url = "https://openlibrary.org/search.json?q=$id"

            val queue = Volley.newRequestQueue(requireContext())
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                { response ->
                    lifecycleScope.launch {
                        try {
                            val obj = JSONObject(response)
                            val docs = obj.getJSONArray("docs")

                            if (docs == null || docs.length() == 0) {
                                Log.d("MyLog", "Ничего не найдено по запросу: $id")
                                return@launch
                            }

                            // чистка таблицы
                            Dependencies.bookRepository.getBooksOrderedByTitle()
                                .forEach { Dependencies.bookRepository.deleteBook(it) }
                            // сбрасывает счетчик Id


                            //  выбирает, что меньше 10 или длина списка объектов docs
                            val count = minOf(10, docs.length())

                            for (i in 0..count) {
                                val docsObj = docs.getJSONObject(i)

                                val title = docsObj.getString("title")
                                val series = docsObj.optString("series_name", "—")

                                //берёт первого автора
                                val author = docsObj.optJSONArray("author_name")
                                    ?.optString(0)
                                    ?: "Автор неизвестен"

                                val book = Book(title, series, author)
                                Dependencies.bookRepository.updateOrInsertBook(book)
                            }

                            Log.d("MyLog", "Сохранено книг: $count")

                            // 3. Только теперь переходим на OutputFragment
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container_view, OutputFragment())
                                .addToBackStack(InputFragment::class.java.simpleName)
                                .commit()

                        } catch (e: Exception) {
                            Log.d("MyLog", "JSON Parsing error: ${e.message}")
                        }
                    }
                },
                {
                    Log.d("MyLog", "Volley error: $it")

                }
            )
            queue.add(stringRequest)



        val fragmentManager: FragmentManager = parentFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()


        fragmentTransaction.replace(R.id.fragment_container_view, OutputFragment())
        fragmentTransaction.addToBackStack(InputFragment.toString())
        fragmentTransaction.commit()


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InputFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InputFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}