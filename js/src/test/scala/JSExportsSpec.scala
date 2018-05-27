package edu.holycross.shot.ptselat
import edu.holycross.shot.ohco2._
import org.scalatest._
import edu.holycross.shot.cite._
import edu.holycross.shot.latin._

class JSExportsSpec extends FlatSpec {

	"The Scala export of the PtseLat class"  should "expose the case class and its methods" in {

		val corpusText:String = """urn:cts:latinLit:phi0893.phi001.fulat1:1.36.1#Et ture et fidibus iuvat """

		val c:Corpus = Corpus(corpusText,"#")

		val p:PtseLatin = PtseLatin(c)
		assert (p.size == 1)
	}

}
