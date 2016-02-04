import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext


trait DataParser {
  def sparkContext: SparkContext
  def rawData: RDD[String]
}


class UserArtistData(override val sparkContext: SparkContext, filename: String) extends DataParser {

  override lazy val rawData = sparkContext.textFile(filename)

  def getRawArtistsForUser(userId: Int): Set[Int] = {
    rawData
      .map(_.split(' '))
      .filter { case Array(user, _, _) => user.toInt == userId }
      .map { case Array(_, artist, _) => artist.toInt }
      .collect
      .toSet
  }
}


class ArtistById(override val sparkContext: SparkContext, filename: String) extends DataParser {

  override val rawData = sparkContext.textFile(filename)

  def extract(): RDD[(Int, String)] = {

    rawData.flatMap { line =>
      val (id, name) = line.span(_ != '\t')
      if (name.isEmpty) {
        None
      } else {
        try {
          Option((id.toInt, name.trim))
        } catch {
          case err: NumberFormatException => None
        }
      }
    }
  }

  def getArtistForUser(artistById: RDD[(Int, String)], rawArtistsForUser: Set[Int]) = {
    artistById.filter {
      case (id, name) => rawArtistsForUser.contains(id)
    }.values.collect
  }
}


class ArtistAlias(override val sparkContext: SparkContext, filename: String) extends DataParser {

  override val rawData = sparkContext.textFile(filename)

  def extract(): scala.collection.Map[Int,Int] = {

    rawData.flatMap { line =>
      val tokens = line.split('\t')
      if (tokens(0).isEmpty) {
        None
      } else {
        Option((tokens(0).toInt, tokens(1).toInt))
      }
    }.collectAsMap
  }
}
