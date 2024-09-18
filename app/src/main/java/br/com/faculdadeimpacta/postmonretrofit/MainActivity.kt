package br.com.faculdadeimpacta.postmonretrofit

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.faculdadeimpacta.postmonretrofit.data.model.CEP
import br.com.faculdadeimpacta.postmonretrofit.data.retrofit.NetworkUtils
import br.com.faculdadeimpacta.postmonretrofit.data.retrofit.PostmonEndpoints
import br.com.faculdadeimpacta.postmonretrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var _retrofit: Retrofit? = null
    private val retrofit get() = _retrofit!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()

        _retrofit = NetworkUtils.getInstance(
            "https://api.postmon.com.br/v1/",
            GsonConverterFactory.create()
        )
        val postmonEndpoints = retrofit.create(PostmonEndpoints::class.java)

        binding.buttonConsultar.setOnClickListener {
            val cepDigitado = binding.editTextCEP.text.toString()
            binding.textViewResposta.text = "Aguardando Resposta"
            binding.buttonConsultar.isEnabled = false

            postmonEndpoints.getInformacoesCEP(cepDigitado).enqueue(object : Callback<CEP> {
                // conseguimos conectar
                override fun onResponse(p0: Call<CEP>, p1: Response<CEP>) {
                    binding.buttonConsultar.isEnabled = true
                    // tratar resposta de erro
                    p1.errorBody()?.let { body ->
                        binding.textViewResposta.text = "FALHA2\n${body.charStream().readLines()}"
                    }
                    // tratar sucesso
                    p1.body()?.let { cep ->
                        binding.textViewResposta.text = "SUCESSO\n${cep}"
                    }
                }

                // tratar falha ao conectar
                override fun onFailure(p0: Call<CEP>, p1: Throwable) {
                    binding.buttonConsultar.isEnabled = true
                    binding.textViewResposta.text = "FALHA\n${p1.printStackTrace()}"
                }

            })
        }

    }
}