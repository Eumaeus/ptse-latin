package edu.holycross.shot
import java.text.Normalizer



/** Package for validating and reporting on Plain Text Scholarly Editions in Latin
*
*  ==Overview==
*
*  ==JVM vs. Javascript implementation==
*
*/
package object ptselat {


  def  normalizeLatin (s: String): String = {
      val normalString:String =  Normalizer.normalize(s, Normalizer.Form.NFC)
      normalString
  }


}
