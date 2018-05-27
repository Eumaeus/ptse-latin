package edu.holycross.shot.ptselat
import org.scalatest.FlatSpec
import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.latin._
import java.text.Normalizer


class PtseLatinSpec extends FlatSpec {

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

  "A PtseLatin text" should "be report the size of its corpus" in {
    val corp:Corpus = Corpus(corpusText,"#")
    val seText:PtseLatin = PtseLatin(corp)
    assert (seText.size == 20)
  }

  it should "tokenize a corpus" in {
    val corp:Corpus = Corpus(smallCorpus,"#")
    val seText:PtseLatin = PtseLatin(corp)
    assert (seText.tokens.size == 5)
  }

  it should "tokenize a corpus in a specified alphabet" in {
    val corp:Corpus = Corpus(smallCorpus,"#")
    val seText:PtseLatin = PtseLatin(corp, Latin24Alphabet)
    assert (seText.tokens.size == 5)
  }

  it should "to tokenize a corpus even if it does not conform to a specified alphabet" in {
    val corp:Corpus = Corpus(smallCorpus,"#")
    val seText:PtseLatin = PtseLatin(corp, Latin23Alphabet)
    assert (seText.tokens.size == 5)
  }

  it should "report when a corpus is 100% orthographically valid" in {
    val corp:Corpus = Corpus(smallCorpus,"#")
    val validText:PtseLatin = PtseLatin(corp, Latin24Alphabet)
    assert (validText.orthographicallyValid == true)
  }

  it should "report when a corpus is not 100% orthographically valid" in {
    val corp:Corpus = Corpus(smallCorpus,"#")
    val invalidText:PtseLatin = PtseLatin(corp, Latin23Alphabet)
    assert (invalidText.orthographicallyValid == false)
  }

  it should "report when a corpus is 100% orthographically valid according to more than one alphabet" in {
	val smallCorpus23:String = """urn:cts:latinLit:phi0893.phi001.fulat1:1.36.1#Et ture et fidibus iuuat"""
    val corp:Corpus = Corpus(smallCorpus23,"#")
    val validText23:PtseLatin = PtseLatin(corp, Latin23Alphabet)
    val validText24:PtseLatin = PtseLatin(corp, Latin24Alphabet)
    assert (validText23.orthographicallyValid == true)
    assert (validText24.orthographicallyValid == true)
  }

  it should "report the LatinLexicalCategories represented in the Corpus" in {
     val testCorpus:String = """urn:cts:latinLit:phi0893.phi001.fulat1:1.36.1#lexical. Ⅷ viii uiii M."""
     val corp:Corpus = Corpus(testCorpus,"#")
     val pe:PtseLatin = PtseLatin(corp, Latin23Alphabet)
     val categories:Vector[LatinLexicalCategory] = pe.tokenCategories
     println(s"${categories}")
     assert( categories.size == 5)
  }

  it should "return a histogram of codepoints, rendered as strings, for all LexicalToken tokens" in {
     val testCorpus:String = """urn:cts:latinLit:phi0893.phi001.fulat1:1.36.1#aaabbc, Ⅷ M."""
     val corp:Corpus = Corpus(testCorpus,"#")
     val pe:PtseLatin = PtseLatin(corp, Latin23Alphabet)
     val strHisto:Vector[(String,Int)] = pe.codepointHistoForTokens(LexicalToken)
     assert (strHisto.size == 3)
     assert (strHisto(0)._1 == "a")
     assert (strHisto(0)._2 == 3)
     assert (strHisto(1)._1 == "b")
     assert (strHisto(1)._2 == 2)
     assert (strHisto(2)._1 == "c")
     assert (strHisto(2)._2 == 1)
  }

  it should "return a histogram of codepoints, rendered as strings, for all Praenomen tokens" in {
     val testCorpus:String = """urn:cts:latinLit:phi0893.phi001.fulat1:1.36.1# C. aaabbc, Ⅷ M. Cn."""
     val corp:Corpus = Corpus(testCorpus,"#")
     val pe:PtseLatin = PtseLatin(corp, Latin23Alphabet)
     val strHisto:Vector[(String,Int)] = pe.codepointHistoForTokens(Praenomen)
     assert (strHisto.size == 4)
     assert (strHisto(0)._1 == ".")
     assert (strHisto(0)._2 == 3)
     assert (strHisto(1)._1 == "C")
     assert (strHisto(1)._2 == 2)
     assert (strHisto(2)._1 == "M")
     assert (strHisto(2)._2 == 1)
     assert (strHisto(3)._1 == "n")
     assert (strHisto(3)._2 == 1)
  }

  it should "return a histogram of codepoints, rendered as strings, for all NumericToken tokens" in {
     val testCorpus:String = """urn:cts:latinLit:phi0893.phi001.fulat1:1.36.1#aaabbc, Ⅷ ⅷ ⅩⅩⅩ M."""
     val corp:Corpus = Corpus(testCorpus,"#")
     val pe:PtseLatin = PtseLatin(corp, Latin23Alphabet)
     val strHisto:Vector[(String,Int)] = pe.codepointHistoForTokens(NumericToken)
     assert (strHisto.size == 2)
     assert (strHisto(0)._1 == "Ⅹ")
     assert (strHisto(0)._2 == 3)
     assert (strHisto(1)._1 == "Ⅷ")
     assert (strHisto(1)._2 == 2)
  }

  it should "return a histogram of codepoints, rendered as strings, for all Punctuation tokens" in {
     val testCorpus:String = """urn:cts:latinLit:phi0893.phi001.fulat1:1.36.1#aaabbc, Ⅷ ⅷ ⅩⅩⅩ M."""
     val corp:Corpus = Corpus(testCorpus,"#")
     val pe:PtseLatin = PtseLatin(corp, Latin23Alphabet)
     val strHisto:Vector[(String,Int)] = pe.codepointHistoForTokens(Punctuation)
     assert (strHisto.size == 1)
     assert (strHisto(0)._1 == ",")
     assert (strHisto(0)._2 == 1)
  }

  it should "return a histogram of codepoints, rendered as strings, for all Invalid tokens" in {
     val testCorpus:String = """urn:cts:latinLit:phi0893.phi001.fulat1:1.36.1#aaabbc, Ⅷ ⅷ ⅩⅩⅩ M. jjjuvvat"""
     val corp:Corpus = Corpus(testCorpus,"#")
     val pe:PtseLatin = PtseLatin(corp, Latin23Alphabet)
     val strHisto:Vector[(String,Int)] = pe.codepointHistoForTokens(InvalidToken)
     assert (strHisto.size == 5)
     assert (strHisto(0)._1 == "j")
     assert (strHisto(0)._2 == 3)
     assert (strHisto(1)._1 == "v")
     assert (strHisto(1)._2 == 2)
  }

   it should "return a histogram of codepoints, rendered as strings, for an entire corpus" in {
       val testCorpus:String = """urn:cts:latinLit:phi0893.phi001.fulat1:1.36.1#aaaaa bbbb ccc
urn:cts:latinLit:phi0893.phi001.fulat1:1.36.2#dd e"""
     val corp:Corpus = Corpus(testCorpus,"#")
     val pe:PtseLatin = PtseLatin(corp, Latin23Alphabet)
     val strHisto:Vector[(String,Int)] = pe.codepointHistoForCorpus
     assert (strHisto.size == 5)
     assert (strHisto(0)._1 == "a")
     assert (strHisto(0)._2 == 5)
     assert (strHisto(1)._1 == "b")
     assert (strHisto(1)._2 == 4)
  }


}
