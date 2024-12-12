import com.example.juegocartas.model.AdditionalEfects
import model.Carta

abstract class Artifact : Carta() {
    abstract override fun getImg(): Int
    abstract override fun efect() : AdditionalEfects
    abstract override fun target() : List<Int>?
    abstract override fun mana(): Int
    override  val tipo=0

}