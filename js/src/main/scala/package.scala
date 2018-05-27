package edu.holycross.shot


import scala.scalajs.js.annotation._

import scala.scalajs.js
import scala.scalajs.js.JSStringOps._
import scala.scalajs.js.UnicodeNormalizationForm._


/** Package for validating and reporting on Plain Text Scholarly Editions in Latin
*
*  ==Overview==
*
*  ==JVM vs. Javascript implementation==
*
*/
package object ptselat {


  @JSExport def  normalizeLatin (s: String): String = {
      val normalString:String =  s.normalize(scala.scalajs.js.UnicodeNormalizationForm.NFC)
      normalString 
  }





}
