package com.websarva.wings.android.synthesizedspeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),View.OnClickListener,TextToSpeech.OnInitListener{
    private var tts : TextToSpeech? = null

    private var selectLang :String = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TextToSpeech初期化
        tts = TextToSpeech(this,this)

        val spinner = findViewById<Spinner>(R.id.language)

        // スピナーにリスナーを登録
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            //　アイテムが選択された時
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                selectLang = spinnerParent.selectedItem as String
            }

            //　アイテムが選択されなかった
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // DO NOTHING
            }
        }
    }

    override fun onClick(view: View){
        var text = speechText.text.toString()

        SpeechText(text)
    }

    override fun onInit(status: Int){
        if(status == TextToSpeech.SUCCESS){
            Log.d("tts","TextToSpeech初期化成功")

            val listener = object : UtteranceProgressListener(){
                var tag : String = "TTS"
                override fun onDone(utteranceId: String?) {
                    Log.d(tag,"音声再生が完了しました。")
                }
                override fun onError(utteranceId: String?) {
                    Log.d(tag,"音声再生中にエラーが発生しました。")
                }
                override fun onError(utteranceId: String?, errorCode: Int) {
                    Log.d(tag,"音声再生中にエラーが発生しました。")
                }
                override fun onStart(utteranceId: String?) {
                    Log.d(tag,"音声再生を開始します。")
                }
                override fun onStop(utteranceId: String?, interrupted: Boolean) {
                    Log.d(tag,"音声再生を中止します。")
                }
                override fun onBeginSynthesis(utteranceId: String?, sampleRateInHz: Int, audioFormat: Int, channelCount: Int) {
                    Log.d(tag,"音声の合成を開始します。")
                }
                override fun onAudioAvailable(utteranceId: String?, audio: ByteArray?) {
                    Log.d(tag,"音声が利用可能になりました。")
                }
            }
            // イベントリスナを登録
            tts?.setOnUtteranceProgressListener(listener)
        }else{
            Log.d("tts","TextToSpeech初期化失敗")
        }
    }

    private fun SpeechText(text:String){
        SetLanguage()
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"ID")
    }

    private fun SetLanguage(){
        when(selectLang){
            "日本語" -> {
                tts?.setLanguage(Locale.JAPANESE)
            }
            "英語" ->{
                tts?.setLanguage(Locale.ENGLISH)
            }
            "中国語" ->{
                tts?.setLanguage(Locale.CHINESE)
            }
            "フランス語" ->{
                tts?.setLanguage(Locale.FRENCH)
            }
            "イタリア語" ->{
                tts?.setLanguage(Locale.ITALIAN)
            }
            "ドイツ語" ->{
                tts?.setLanguage(Locale.GERMAN)
            }
            "韓国語" ->{
                tts?.setLanguage(Locale.KOREAN)
            }
        }
    }
}