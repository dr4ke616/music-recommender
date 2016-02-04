
import org.apache.spark.Logging

import com.typesafe.config.ConfigFactory


object MusicRecommenderApp extends App with Logging {

  val config = ConfigFactory.load()

  val sc = Initialize.getSparkContext(config)

  val userArtistDataInst = new UserArtistData(sc, config.getString("app.file.user.artist.data"))
  val artistByIdInst = new ArtistById(sc, config.getString("app.file.artist.data"))
  val artistAliasInst = new ArtistAlias(sc, config.getString("app.file.artist.alias"))

  val rawUserArtistData = userArtistDataInst.rawData
  val artistById = artistByIdInst.extract()
  val artistAlias = artistAliasInst.extract()

  val model = new TrainingData(sc).train(rawUserArtistData, artistAlias)

  println("----Artists for user----")
  getArtistNamesForUser(2093760).foreach(println)

  println("----Recommended artists for user----")
  getArtistRecommendNamesForUser(2093760).foreach(println)

  private def getArtistNamesForUser(userId: Int) = {
    val rawArtistsForUser = userArtistDataInst.getRawArtistsForUser(userId)
    artistByIdInst.getArtistForUser(artistById, rawArtistsForUser)
  }

  private def getArtistRecommendNamesForUser(userId: Int) = {
    val recommendations = model.recommendProducts(userId, 5)
    val recommendedProductIds = recommendations.map(_.product).toSet

    artistById.filter {
      case (id, name) => recommendedProductIds.contains(id)
    }.values.collect
  }
}