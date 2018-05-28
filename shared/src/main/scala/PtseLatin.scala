package edu.holycross.shot.ptselat
import scala.scalajs.js
import scala.scalajs.js.annotation._
import edu.holycross.shot.ohco2._
import edu.holycross.shot.cite._
import edu.holycross.shot.latin._
import java.text.Normalizer

/** Minimal case class to demonstrate building
* a project with tests and documentation.
*
* @param vocative Person to address in direct form.
*/
@JSExportAll  case class PtseLatin(corpus: Corpus, alphabet: LatinAlphabet = Latin23Alphabet) {
	/** Create greeting String. */
	def size : Int = {
		corpus.nodes.size
	}

	def tokenize(corpusToTokenize: Corpus, units: Vector[CtsUrn] = Vector.empty[CtsUrn], unitLabel: String = "poem") :  Vector[LatinToken]= {

		if (units.isEmpty) {
			LatinTextReader.corpusToTokens(corpusToTokenize, alphabet)
		} else {
			val tokensByChunk = for (chunkUrn <- units) yield {
				val chunk = corpusToTokenize  ~~ chunkUrn
				LatinTextReader.corpusToTokens(chunk, alphabet)
			}
			tokensByChunk.flatten
		}
	}


	/** A Vector of LatinTokens
	**/
	val tokens:Vector[LatinToken] = {
		try {
			tokenize(corpus)
		} catch {
			case e:Exception => throw new PtseLatinException(s"PtseLatinException:", Some(e))
		}
	}

	/** True if there are no tokesn of type InvalidToken
	**/
	val orthographicallyValid:Boolean = {
		tokens.filter(_.category == InvalidToken).size == 0
	}

	/** Reports the distinct LatinLexicalCategories represented by the tokens in the corpus
	**/
	val tokenCategories:Vector[LatinLexicalCategory] = {
		tokens.map(_.category).distinct	
	}


	/** turns a string into a vector of codepoints **/
	def strToCps(s: String, cpVector: Vector[Int] = Vector.empty[Int], idx : Int = 0) : Vector[Int] = {
		if (idx >= s.length) {
			cpVector
		} else {
			val cp = s.codePointAt(idx)
			strToCps(s, cpVector :+ cp, idx + java.lang.Character.charCount(cp))
		}
	}

	/** Generate histograph of chacters by LatinLexicalCategory 
	* N.b. for LexicalToken types, this lower-cases all characters.
	*      for NumericToken types, it upper-cases all characters.
	*      it does not change case of Praenomen, Punctuation, or Invalid tokens	
	**/
	def codepointHistoForTokens(category:LatinLexicalCategory):Vector[(String,Int)] = {
		val theseTokens:Vector[LatinToken] = tokens.filter(_.category == category)	
		val bigString:String = {
			category match {
				case LexicalToken => {
					normalizeLatin(theseTokens.view.map(_.toLowerCase).map(_.text).reduce(_ + _)	)
				}
				case NumericToken => {
					normalizeLatin(theseTokens.view.map(_.toUpperCase).map(_.text).reduce(_ + _)	)
				}
				case _ => {
					normalizeLatin(theseTokens.view.map(_.text).reduce(_ + _))
				}
			}

		}
		val codePointVec:Vector[Int] = strToCps( bigString )
		val cpHisto:Vector[(Int, Int)] = codePointVec.groupBy(s => s).map(m => (m._1, m._2.size)).toSeq.sortBy(_._2).reverse.toVector
		val stringHisto:Vector[(String, Int)] = cpHisto.map(m => ( new String(Character.toChars(m._1)), m._2))
		stringHisto
	}

/** Generate histograph of chacters for a corpus
	* N.b. case is unchanged
	**/
	def codepointHistoForCorpus:Vector[(String,Int)] = {
		val bigString:String = {
			normalizeLatin(corpus.nodes.view.map(_.text).reduce(_ + _).replaceAll("\\s", ""))
		}
		val codePointVec:Vector[Int] = strToCps( bigString )
		val cpHisto:Vector[(Int, Int)] = codePointVec.groupBy(s => s).map(m => (m._1, m._2.size)).toSeq.sortBy(_._2).reverse.toVector
		val stringHisto:Vector[(String, Int)] = cpHisto.map(m => ( new String(Character.toChars(m._1)), m._2))
		stringHisto
	}

	def codepointHistoForValidCorpus:Vector[(String,Int)] = {
		val skipCategory:Vector[LatinLexicalCategory] = Vector(InvalidToken)
		val validString:String = {
			tokenCategories.map( tc => {
				if (skipCategory.contains(tc)) {
					""
				} else {
					val theseTokens:Vector[LatinToken] = tokens.filter(_.category == tc)	
					normalizeLatin(theseTokens.view.map(_.text).reduce(_ + _))
				}
			}).reduce(_ + _)
		}
		val codePointVec:Vector[Int] = strToCps( validString )
		val cpHisto:Vector[(Int, Int)] = codePointVec.groupBy(s => s).map(m => (m._1, m._2.size)).toSeq.sortBy(_._2).reverse.toVector
		val stringHisto:Vector[(String, Int)] = cpHisto.map(m => ( new String(Character.toChars(m._1)), m._2))
		stringHisto
	}

	def tokenHisto(category:LatinLexicalCategory):Vector[(String,Int)] = {
		val toks:Vector[String] = {
			category match {
				case LexicalToken => {
					tokens.view.filter(_.category == category).map( t => normalizeLatin(t.text.toLowerCase) ).toVector
				}
				case NumericToken => {
					tokens.view.filter(_.category == category).map( t => normalizeLatin(t.text.toUpperCase) ).toVector
				}
				case _ => {
					tokens.view.filter(_.category == category).map( t =>  normalizeLatin(t.text)).toVector
				}
			}
		}
		val tokenHistograph:Vector[(String,Int)] = {
			toks.groupBy(lt => lt).map(m => (m._1, m._2.size)).toSeq.sortBy(_._2).reverse.toVector
		}
		tokenHistograph
	}


}
