import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext

import org.apache.spark.mllib.recommendation._


class TrainingData(sc: SparkContext) {

  def train(rawUserArtistData: RDD[String], artistAlias: scala.collection.Map[Int,Int]): MatrixFactorizationModel = {
    val bArtistAlias = sc.broadcast(artistAlias)

    val trainData = rawUserArtistData.map { line =>
      val Array(userId, artistId, count) = line.split(' ').map(_.toInt)
      val finalArtistId = bArtistAlias.value.getOrElse(artistId, artistId)
      Rating(userId, finalArtistId, count)
    }.cache()

    ALS.trainImplicit(trainData, 10, 5, 0.01, 1.0)
  }
}
