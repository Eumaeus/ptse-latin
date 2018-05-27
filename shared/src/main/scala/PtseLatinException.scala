package edu.holycross.shot
package ptselat {

  case class PtseLatinException(message: String = "", cause: Option[Throwable] = None) extends Exception(message) {
    cause.foreach(initCause)
  }

}
