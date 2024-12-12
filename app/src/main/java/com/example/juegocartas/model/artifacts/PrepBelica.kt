import com.example.juegocartas.R
import com.example.juegocartas.model.AdditionalEfects
import com.example.juegocartas.model.Texts

class PrepBelica : Artifact() {
    private val img= R.drawable.preparacion_belica
    private val text = Texts.PREP_BELICA

    override  fun getImg(): Int {
        return img
    }
    override fun efect(): AdditionalEfects {
        return AdditionalEfects.STEAL_2_CARDS
    }

    override fun target(): List<Int>? {
        return null
    }

    override fun mana(): Int {
        return 0
    }

    override fun getText(): String {
        return this.text
    }
    //Estas son las clases de mana 0 para robar carta o lo q sea
}