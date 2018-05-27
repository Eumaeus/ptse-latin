package edu.holycross.shot.ptselat
import org.scalatest.FlatSpec
import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.latin._
import java.text.Normalizer


class PtseLatinJVMSpec extends FlatSpec {

  val corpusText:String = """
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.1#Et ture et fidibus iuvat 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.2#placare et vituli sanguine debito 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.3#custodes Numidae deos, 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.4#qui nunc Hesperia sospes ab ultima 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.5#caris multa sodalibus, 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.6#nulli plura tamen dividet oscula 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.7#quam dulci Lamiae, memor 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.8#actae non alio rege puertiae 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.9#mutataeque simul togae. 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.10#Cressa ne careat pulcra dies nota 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.11#neu promptae modus amphorae 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.12#neu morem in Salium sit requies pedum 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.13#neu multi Damalis meri 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.14#Bassum Threicia vincat amystide 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.15#neu desint epulis rosae 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.16#neu vivax apium neu breve lilium. 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.17#omnes in Damalin putris 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.18#deponent oculos nec Damalis novo 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.19#divelletur adultero 
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.20#lascivis hederis ambitiosior. 
"""

  val smallCorpus:String = """urn:cts:latinLit:phi0893.phi001.fulat1:1.36.1#Et ture et fidibus iuvat"""

  "The PtseLatin JVM build" should "distinguish between characters based on Unicode codepoints" in {
  	val letterA:String = "a"
  	val longA:String = "ā"
  	val composedLongA:String = "ā"
  	assert (letterA != longA)
  	assert (longA != composedLongA)
  }

  it should "distinguish between characters based on Unicode codepoints after NFC normalization" in {
    val letterA:String = "a"
    val longA:String = "ā"
    val composedLongA:String = "ā"
    assert (letterA != longA)
    assert (longA != composedLongA)
    assert (
      normalizeLatin(longA) == normalizeLatin(composedLongA)
    )
  }




}
