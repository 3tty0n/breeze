package breeze.linalg.functions

import breeze.linalg.{SparseVector, DenseVector}
import org.scalatest.FunSuite

class minkowskiDistanceTest extends FunSuite {

  test("it should be correct with dense vectors") {
    val v1 = DenseVector(1.0, 1.0)
    val v2 = DenseVector(9.0, -9.0)
    assert(minkowskiDistance(v1, v2, 3.0) === 11.477587096634332)
  }

  test("it should be correct with sparse vectors") {
    val v1 = SparseVector(1.0, 1.0)
    val v2 = SparseVector(9.0, -9.0)
    assert(minkowskiDistance(v1, v2, 3.0) === 11.477587096634332)
  }

  test("it should be correct with dense vector and sparse vector") {
    val v1 = DenseVector(1, 1.0)
    val v2 = SparseVector(9.0, -9)
    assert(minkowskiDistance(v1, v2, 3.0) === 11.477587096634332)
  }

  test("big sparse vectors shouldn't be insanely inefficient") {
    val v1 = SparseVector.zeros[Double](100000)
    val v2 = SparseVector.zeros[Double](10)
    // TODO: how better to measure this
    val in = System.currentTimeMillis()
    assert(minkowskiDistance(v2, v2, 3.0) === 0.0)
    val outSmall = System.currentTimeMillis()
    assert(minkowskiDistance(v1, v1, 3.0) === 0.0)
    val outBig = System.currentTimeMillis()
    assert((outBig - outSmall) <= 2 * (outSmall - in + 1), s"too slow! ${outBig - outSmall} ms vs ${outSmall - in} ms")

  }
}
