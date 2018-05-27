package edu.holycross.shot.ptselat
import edu.holycross.shot.ohco2._

/** Object demonstrating build of executable main
* function in JVM branch.
*/
object TestMain {

  /** Main function.
  *
  * @param args Arguments.
  */
  def main(args: Array[String]): Unit = {


  val corpusText:String = """urn:cts:latinLit:phi0893.phi001.fulat1:1.36.1#Et ture et fidibus iuvat """

  val c:Corpus = Corpus(corpusText,"#")

  val p:PtseLatin = PtseLatin(c)



  }
}
